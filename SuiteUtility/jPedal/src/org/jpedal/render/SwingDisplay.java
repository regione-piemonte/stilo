/**
 * ===========================================
 * Java Pdf Extraction Decoding Access Library
 * ===========================================
 *
 * Project Info:  http://www.jpedal.org
 * (C) Copyright 1997-2011, IDRsolutions and Contributors.
 *
 * 	This file is part of JPedal
 *
     This library is free software; you can redistribute it and/or
    modify it under the terms of the GNU Lesser General Public
    License as published by the Free Software Foundation; either
    version 2.1 of the License, or (at your option) any later version.

    This library is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
    Lesser General Public License for more details.

    You should have received a copy of the GNU Lesser General Public
    License along with this library; if not, write to the Free Software
    Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA


  *
  * ---------------
  * ScreenDisplay.java
  * ---------------
 */
package org.jpedal.render;

import org.jpedal.PdfDecoder;
import org.jpedal.color.ColorSpaces;
import org.jpedal.color.PdfColor;
import org.jpedal.color.PdfPaint;
import org.jpedal.constants.PDFImageProcessing;
import org.jpedal.exception.PdfException;
import org.jpedal.external.JPedalCustomDrawObject;
import org.jpedal.fonts.PdfFont;
import org.jpedal.fonts.StandardFonts;
import org.jpedal.fonts.glyph.*;
import org.jpedal.io.ColorSpaceConvertor;
import org.jpedal.io.JAIHelper;
import org.jpedal.io.ObjectStore;
import org.jpedal.objects.GraphicsState;
import org.jpedal.parser.Cmd;
import org.jpedal.parser.DecoderOptions;
import org.jpedal.utils.LogWriter;
import org.jpedal.utils.Matrix;
import org.jpedal.utils.Messages;
import org.jpedal.utils.repositories.*;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Area;
import java.awt.geom.PathIterator;
import java.awt.image.*;
import java.io.*;
import java.util.*;

public class SwingDisplay extends BaseDisplay implements DynamicVectorRenderer{

     private static boolean drawPDFShapes = true;
     //*/


    //Flag to prevent drawing highlights too often.
    boolean ignoreHighlight = false;


    /**stop screen being cleared on next repaint*/
    private boolean noRepaint=false;

    /**track items painted to reduce unnecessary calls*/
    private int lastItemPainted=-1;


    /**tell renderer to optimise calls if possible*/
    private boolean optimsePainting=false;

    private boolean needsHorizontalInvert = false;

    private boolean needsVerticalInvert = false;

    private int pageX1=9999, pageX2=-9999, pageY1=-9999, pageY2=9999;

    //flag highlights need to be generated for page
    private boolean highlightsNeedToBeGenerated=false;


    /**used to cache single image*/
    private BufferedImage singleImage=null;

    private int imageCount=0;

    /**default array size*/
    private static final int defaultSize=5000;

    //used to track end of PDF page in display
    int endItem=-1;

    /**hint for conversion ops*/
    private static RenderingHints hints = null;

    private final Map cachedWidths=new HashMap(10);

    private final Map cachedHeights=new HashMap(10);

    private Map fonts=new HashMap(50);

    private Map fontsUsed=new HashMap(50);

    protected GlyphFactory factory=null;

    private PdfGlyphs glyphs;

    private Map imageID=new HashMap(10);

    private final Map imageIDtoName=new HashMap(10);

    private Map storedImageValues=new HashMap(10);

    /**text highlights if needed*/
    private int[] textHighlightsX,textHighlightsWidth,textHighlightsHeight;

    //allow user to diable g2 setting
    boolean stopG2setting;

   

    

    /**store x*/
    private float[] x_coord;

    /**store y*/
    private float[] y_coord;

    /**cache for images*/
    private Map largeImages=new WeakHashMap(10);

    private Vector_Object text_color;
    private Vector_Object stroke_color;
    private Vector_Object fill_color;

    private Vector_Object stroke;

    /**initial Q & D object to hold data*/
    private Vector_Object pageObjects;

    private Vector_Int shapeType;

    private Vector_Rectangle fontBounds;

    private Vector_Double af1;
    private Vector_Double af2;
    private Vector_Double af3;
    private Vector_Double af4;

    /**image options*/
    private Vector_Int imageOptions;

    /**TR for text*/
    private Vector_Int TRvalues;

    /**font sizes for text*/
    private Vector_Int fs;

    /**line widths if not 0*/
    private Vector_Int lw;

    /**holds rectangular outline to test in redraw*/
    private Vector_Shape clips;

    /**holds object type*/
    private Vector_Int objectType;

    /**holds object type*/
    private Vector_Object javaObjects;

    /**holds fill type*/
    private Vector_Int textFillType;

    /**holds object type*/
    private Vector_Float opacity;

    /**current item added to queue*/
    private int currentItem=0;

    //used to track col changes
    private int lastFillTextCol,lastFillCol,lastStrokeCol;

    /**used to track strokes*/
    private Stroke lastStroke=null;

    //trakc affine transform changes
    private double[] lastAf=new double[4];

    /**used to minimise TR and font changes by ignoring duplicates*/
    private int lastTR=2,lastFS=-1,lastLW=-1;

    /**ensure colors reset if text*/
    private boolean resetTextColors=true;

    private boolean fillSet=false,strokeSet=false;



    /**
     * If highlgihts are not null and no highlgihts are drawn
     *  then it is likely a scanned page. Treat differently.
     */
    private boolean needsHighlights = true;

    private int paintThreadCount=0;
    private int paintThreadID=0;

    /**
     * For IDR internal use only
     */
    private boolean[] drawnHighlights;

    /**
     * flag if OCR so we need to redraw at end
     */
    private boolean hasOCR=false;

    protected int type =DynamicVectorRenderer.DISPLAY_SCREEN;

    static {

        hints =new RenderingHints(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        hints.put(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
    }

    public SwingDisplay() {}

    /**
     * @param defaultSize
     */
    void setupArrays(int defaultSize) {

        x_coord=new float[defaultSize];
        y_coord=new float[defaultSize];
        text_color=new Vector_Object(defaultSize);
        textFillType=new Vector_Int(defaultSize);
        stroke_color=new Vector_Object(defaultSize);
        fill_color=new Vector_Object(defaultSize);
        stroke=new Vector_Object(defaultSize);
        pageObjects=new Vector_Object(defaultSize);
        javaObjects=new Vector_Object(defaultSize);
        shapeType=new Vector_Int(defaultSize);
        areas=new Vector_Rectangle(defaultSize);
        af1=new Vector_Double(defaultSize);
        af2=new Vector_Double(defaultSize);
        af3=new Vector_Double(defaultSize);
        af4=new Vector_Double(defaultSize);

        fontBounds=new Vector_Rectangle(defaultSize);

        clips=new Vector_Shape(defaultSize);
        objectType=new Vector_Int(defaultSize);
    }

    public SwingDisplay(int pageNumber, boolean addBackground, int defaultSize, ObjectStore newObjectRef) {

        this.pageNumber=pageNumber;
        this.objectStoreRef = newObjectRef;
        this.addBackground=addBackground;

        setupArrays(defaultSize);
    }


    public SwingDisplay(int pageNumber, ObjectStore newObjectRef, boolean isPrinting) {

        this.pageNumber=pageNumber;
        this.objectStoreRef = newObjectRef;
        this.isPrinting=isPrinting;

        setupArrays(defaultSize);

    }

    /**
     * set optimised painting as true or false and also reset if true
     * @param optimsePainting
     */
    public void setOptimsePainting(boolean optimsePainting) {
        this.optimsePainting = optimsePainting;
        lastItemPainted=-1;
    }

    private void renderHighlight(Rectangle highlight, Graphics2D g2){

        if(highlight!=null && !ignoreHighlight){
            Shape currentClip = g2.getClip();

            g2.setClip(null);

            //Backup current g2 paint and composite
            Composite comp = g2.getComposite();
            Paint p = g2.getPaint();

            //Set new values for highlight
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,PdfDecoder.highlightComposite));

            if(invertHighlight){
                g2.setColor(Color.WHITE);
                g2.setXORMode(Color.BLACK);
            }else{

                g2.setPaint(DecoderOptions.highlightColor);
            }
            //Draw highlight
            g2.fill(highlight);

            //Reset to starting values
            g2.setComposite(comp);
            g2.setPaint(p);

            needsHighlights = false;

            g2.setClip(currentClip);
        }
    }





    public void stopG2HintSetting(boolean isSet){
        stopG2setting=isSet;

    }

    /* remove all page objects and flush queue */
    public void flush() {

        singleImage=null;

        imageCount=0;

        lastFS = -1;

        if(shapeType!=null){

            shapeType.clear();
            pageObjects.clear();
            objectType.clear();
            areas.clear();
            clips.clear();
            x_coord=new float[defaultSize];
            y_coord=new float[defaultSize];
            textFillType.clear();
            text_color.clear();
            fill_color.clear();
            stroke_color.clear();
            stroke.clear();

            if(TRvalues!=null)
                TRvalues=null;

            if(imageOptions!=null)
                imageOptions=null;

            if(fs!=null)
                fs=null;

            if(lw!=null)
                lw=null;

            af1.clear();
            af2.clear();
            af3.clear();
            af4.clear();

            fontBounds.clear();

            if(opacity!=null)
                opacity=null;

            if(isPrinting)
                largeImages.clear();


            endItem=-1;
        }

        //pointer we use to flag color change
        lastFillTextCol=0;
        lastFillCol=0;
        lastStrokeCol=0;

        lastClip=null;
        hasClips=false;

        //track strokes
        lastStroke=null;

        lastAf=new double[4];

        currentItem=0;

        fillSet=false;
        strokeSet=false;

        fonts.clear();
        fontsUsed.clear();

        imageID.clear();

        pageX1=9999;
        pageX2=-9999;
        pageY1=-9999;
        pageY2=9999;

        lastScaling=0;

    }

    /* remove all page objects and flush queue */
    public void dispose() {

        singleImage=null;

        shapeType=null;
        pageObjects=null;
        objectType=null;
        areas=null;
        clips=null;
        x_coord=null;
        y_coord=null;
        textFillType=null;
        text_color=null;
        fill_color=null;
        stroke_color=null;
        stroke=null;

        TRvalues=null;

        imageOptions=null;

        fs=null;

        lw=null;

        af1=null;
        af2=null;
        af3=null;
        af4=null;

        fontBounds=null;

        opacity=null;

        largeImages=null;

        lastClip=null;

        lastStroke=null;

        lastAf=null;

        fonts=null;
        fontsUsed=null;

        imageID=null;

        storedImageValues=null;
    }

    private double minX=-1;

    private double minY=-1;

    private double maxX=-1;

    private double maxY=-1;

    private boolean renderFailed;

    /**optional frame for user to pass in - if present, error warning will be displayed*/
    private Container frame=null;

    /**make sure user only gets 1 error message a session*/
    private static boolean userAlerted=false;


    
    /*renders all the objects onto the g2 surface*/
    public Rectangle paint(Rectangle[] highlights,AffineTransform viewScaling, Rectangle userAnnot){

    	//if OCR we need to track over draw at end
    	Vector_Rectangle ocr_highlights=null;
    	HashMap ocr_used=null;
    	if(hasOCR){
    		ocr_highlights = new Vector_Rectangle(4000);
    		ocr_used=new HashMap(10);
    	}

    	//take a lock
    	int currentThreadID=++paintThreadID;
    	paintThreadCount++;

    	/**
    	 * Keep track of drawn highlights so we don't draw multiple times
    	 */
    	if(highlights!=null){
    		drawnHighlights = new boolean[highlights.length];
    		for(int i=0; i!=drawnHighlights.length; i++)
    			drawnHighlights[i]=false;
    	}

    	//ensure all other threads dead or this one killed  (screen only)
    	if(paintThreadCount>1){
    		try {
    			Thread.sleep(50);
    		} catch (InterruptedException e) {
                //tell user and log
                if(LogWriter.isOutput())
                    LogWriter.writeLog("Exception: "+e.getMessage());
    		}

    		if(currentThreadID!=paintThreadID){
    			paintThreadCount--;
    			return null;
    		}
    	}

    	final boolean debug=false;

    	//int paintedCount=0;

    	String fontUsed;
    	float a=0,b = 0,c=0,d=0;

    	Rectangle dirtyRegion=null;

    	//local copies
    	int[] objectTypes=objectType.get();
    	int[] textFill=textFillType.get();

    	//currentItem to make the code work - can you let me know what you think
    	int count=currentItem; //DO nOT CHANGE
    	Area[] pageClips=clips.get();
    	double[] afValues1=af1.get();
    	int[] fsValues=null;
    	if(fs!=null)
    		fsValues=fs.get();

    	Rectangle[] fontBounds=this.fontBounds.get();

    	int[] lwValues=null;
    	if(lw!=null)
    		lwValues=lw.get();
    	double[] afValues2=af2.get();
    	double[] afValues3=af3.get();
    	double[] afValues4=af4.get();
    	Object[] text_color=this.text_color.get();
    	Object[] fill_color=this.fill_color.get();

    	Object[] stroke_color=this.stroke_color.get();
    	Object[] pageObjects=this.pageObjects.get();

    	Object[] javaObjects=this.javaObjects.get();
    	Object[] stroke=this.stroke.get();
    	int[] fillType=this.shapeType.get();

    	float[] opacity = null;
    	if(this.opacity!=null)
    		opacity=this.opacity.get();

    	int[] TRvalues = null;
    	if(this.TRvalues!=null)
    		TRvalues=this.TRvalues.get();

    	Rectangle[] areas=null;

    	if(this.areas!=null)
    		areas=this.areas.get();

    	int[] imageOptions=null;
    	if(this.imageOptions!=null)
    		imageOptions=this.imageOptions.get();

    	Shape rawClip=g2.getClip();
    	if(rawClip!=null)
    		dirtyRegion=rawClip.getBounds();

    	boolean isInitialised=false;

    	Shape defaultClip=g2.getClip();

    	//used to optimise clipping
    	Area clipToUse=null;
    	boolean newClip=false;

    	/**/
    	if(noRepaint)
    		noRepaint=false;
    	else if(lastItemPainted==-1){
    		paintBackground(dirtyRegion);/**/
    	}
    	/**save raw scaling and apply any viewport*/
    	AffineTransform rawScaling=g2.getTransform();
    	if(viewScaling!=null){
    		g2.transform(viewScaling);
    		defaultClip=g2.getClip(); //not valid if viewport so disable
    	}

    	//reset tracking of box
    	minX=-1;
    	minY=-1;
    	maxX=-1;
    	maxY=-1;

    	Object currentObject;
    	int type,textFillType,currentTR=GraphicsState.FILL;
    	int lineWidth=0;
    	float fillOpacity=1.0f;
    	float strokeOpacity=1.0f;
    	float x,y ;
    	int iCount=0,cCount=0,sCount=0,fsCount=-1,lwCount=0,afCount=-1,tCount=0,stCount=0,
    	fillCount=0,strokeCount=0,trCount=0,opCount=0,
    	stringCount=0;//note af is 1 behind!
    	PdfPaint textStrokeCol=null,textFillCol=null,fillCol=null,strokeCol = null;
    	Stroke currentStroke=null;

    	//if we reuse image this is pointer to live image
    	int imageUsed;

        //use preset colours for T3 glyph
    	if(colorsLocked){
    		strokeCol=this.strokeCol;
    		fillCol=this.fillCol;
    	}

    	//setup first time something to highlight
    	if(highlightsNeedToBeGenerated && areas!=null && highlights!=null)
    		generateHighlights(g2,count, objectTypes,pageObjects, a,b,c,d,afValues1,afValues2,afValues3,afValues4,fsValues,fontBounds);

    	/**
    	 * now draw all shapes
    	 */
    	for(int i=0;drawPDFShapes && i<count;i++){

    		//    if(i>4800)
    		//break;

    		boolean ignoreItem=false;

    		type=objectTypes[i];

    		//ignore items flagged as deleted
    		if(type== DynamicVectorRenderer.DELETED_IMAGE)
    			continue;

    		Rectangle currentArea=null;

    		//exit if later paint recall
    		if(currentThreadID!=paintThreadID){
    			paintThreadCount--;

    			return null;
    		}


    		if(type>0){

    			x=x_coord[i];
    			y=y_coord[i];

    			currentObject=pageObjects[i];

    			//swap in replacement image
    			if(type==DynamicVectorRenderer.REUSED_IMAGE){
    				type=DynamicVectorRenderer.IMAGE;
    				imageUsed= (Integer) currentObject;
    				currentObject=pageObjects[imageUsed];
    			}else
    				imageUsed=-1;

    			/**
    			 * workout area occupied by glyf
    			 */
    			if(currentArea==null)
    				currentArea = getObjectArea(afValues1, fsValues, afValues2,afValues3, afValues4, pageObjects, areas, type, x, y,fsCount, afCount, i);

    			ignoreItem=false;

    			//see if we need to draw
    			if(currentArea!=null){

    				//was glyphArea, changed back to currentArea to fix highlighting issue in Sams files.
    				//last width test for odd print issue in phonobingo
    				if (type < 7 && (userAnnot != null) && ((!userAnnot.intersects(currentArea)))&& currentArea.width>0) {
    					ignoreItem = true;
    				}
    			}
    			//                }else if((optimiseDrawing)&&(rotation==0)&&(dirtyRegion!=null)&&(type!=DynamicVectorRenderer.STROKEOPACITY)&&
    			//                        (type!=DynamicVectorRenderer.FILLOPACITY)&&(type!=DynamicVectorRenderer.CLIP)
    			//                        &&(currentArea!=null)&&
    			//                        ((!dirtyRegion.intersects(currentArea))))
    			//                    ignoreItem=true;

    			if(ignoreItem || (lastItemPainted!=-1 && i<lastItemPainted)){
    				//keep local counts in sync
    				switch (type) {

    				case DynamicVectorRenderer.SHAPE:
    					sCount++;
    					break;
    				case DynamicVectorRenderer.IMAGE:
    					iCount++;
    					break;
    				case DynamicVectorRenderer.REUSED_IMAGE:
    					iCount++;
    					break;
    				case DynamicVectorRenderer.CLIP:
    					cCount++;
    					break;
    				case DynamicVectorRenderer.FONTSIZE:
    					fsCount++;
    					break;
    				case DynamicVectorRenderer.LINEWIDTH:
    					lwCount++;
    					break;
    				case DynamicVectorRenderer.TEXTCOLOR:
    					tCount++;
    					break;
    				case DynamicVectorRenderer.FILLCOLOR:
    					fillCount++;
    					break;
    				case DynamicVectorRenderer.STROKECOLOR:
    					strokeCount++;
    					break;
    				case DynamicVectorRenderer.STROKE:
    					stCount++;
    					break;
    				case DynamicVectorRenderer.TR:
    					trCount++;
    					break;
    				}

    			}else{

                    if(!isInitialised && !stopG2setting){

                        if(userHints!=null){
                            g2.setRenderingHints(userHints);
                        }else{
                            //set hints to produce high quality image
                            g2.setRenderingHints(hints);
                        }
                        isInitialised=true;
                    }

    				//paintedCount++;

    				if(currentTR==GraphicsState.INVISIBLE){
    					needsHighlights = true;
    				}

    				Rectangle highlight = null;

    				switch (type) {

    				case DynamicVectorRenderer.SHAPE:

    					if(debug)
    						System.out.println("Shape");

    					if(newClip){
    						RenderUtils.renderClip(clipToUse, dirtyRegion,defaultClip,g2);
    						newClip=false;
    					}

    					Shape s=null;
    					if(endItem!=-1 && endItem<i){
    						s = g2.getClip();
    						g2.setClip(defaultClip);

    					}

    					renderShape(defaultClip,fillType[sCount],strokeCol,fillCol, currentStroke,(Shape)currentObject,strokeOpacity,fillOpacity);

    					if(endItem!=-1 && endItem<i)
    						g2.setClip(s);

    					sCount++;

    					break;

    				case DynamicVectorRenderer.TEXT:

    					if(debug)
    						System.out.println("Text");

    					if(newClip){
    						RenderUtils.renderClip(clipToUse, dirtyRegion,defaultClip,g2);
    						newClip=false;
    					}

    					if(!invertHighlight)
    						highlight = setHighlightForGlyph(currentArea,highlights);

    					if(hasOCR && highlight!=null){
    						String key=highlight.x+" "+highlight.y;
    						if(ocr_used.get(key)==null){

    							ocr_used.put(key,"x"); //avoid multiple additions
    							ocr_highlights.addElement(highlight);
    						}
    					}

    					AffineTransform def=g2.getTransform();

    					renderHighlight(highlight, g2);

    					g2.transform(new AffineTransform(afValues1[afCount],afValues2[afCount],-afValues3[afCount],-afValues4[afCount],x,y));

    					renderText(x,y, currentTR,(Area)currentObject, highlight, textStrokeCol,textFillCol,strokeOpacity,fillOpacity);

    					g2.setTransform(def);

    					break;

    				case DynamicVectorRenderer.TRUETYPE:

    					if(debug)
    						System.out.println("Truetype");

    					if(newClip){
    						RenderUtils.renderClip(clipToUse, dirtyRegion,defaultClip,g2);
    						newClip=false;
    					}

    					//hack to fix exceptions in a PDF using this code to create ReadOnly image
    					if(afCount==-1)
    						break;

    					AffineTransform aff=new AffineTransform(afValues1[afCount],afValues2[afCount],afValues3[afCount],afValues4[afCount],x,y);

    					if(!invertHighlight)
    						highlight = setHighlightForGlyph(currentArea,highlights);

    					if(hasOCR && highlight!=null){

    						String key=highlight.x+" "+highlight.y;
    						if(ocr_used.get(key)==null){

    							ocr_used.put(key,"x"); //avoid multiple additions
    							ocr_highlights.addElement(highlight);
    						}
    					}

    					renderHighlight(highlight, g2);
    					renderEmbeddedText(currentTR,currentObject,DynamicVectorRenderer.TRUETYPE,aff,highlight,textStrokeCol,textFillCol,strokeOpacity,fillOpacity,lineWidth);

    					break;

    				case DynamicVectorRenderer.TYPE1C:

    					if(debug)
    						System.out.println("Type1c");

    					if(newClip){
    						RenderUtils.renderClip(clipToUse, dirtyRegion,defaultClip,g2);
    						newClip=false;
    					}

    					aff=new AffineTransform(afValues1[afCount],afValues2[afCount],afValues3[afCount],afValues4[afCount],x,y);

    					if(!invertHighlight)
    						highlight = setHighlightForGlyph(currentArea,highlights);

    					if(hasOCR && highlight!=null){
    						String key=highlight.x+" "+highlight.y;
    						if(ocr_used.get(key)==null){

    							ocr_used.put(key,"x"); //avoid multiple additions
    							ocr_highlights.addElement(highlight);

    						}
    					}

    					renderHighlight(highlight, g2);

    					renderEmbeddedText(currentTR,currentObject,DynamicVectorRenderer.TYPE1C,aff,highlight,textStrokeCol,textFillCol,strokeOpacity,fillOpacity,lineWidth);

    					break;

    				case DynamicVectorRenderer.TYPE3:

    					if(debug)
    						System.out.println("Type3");

    					if(newClip){
    						RenderUtils.renderClip(clipToUse, dirtyRegion,defaultClip,g2);
    						newClip=false;
    					}

    					aff=new AffineTransform(afValues1[afCount],afValues2[afCount],afValues3[afCount],afValues4[afCount],x,y);

    					if(!invertHighlight)
    						highlight = setHighlightForGlyph(currentArea,highlights);

    					if(hasOCR && highlight!=null){
    						String key=highlight.x+" "+highlight.y;
    						if(ocr_used.get(key)==null){

    							ocr_used.put(key,"x"); //avoid multiple additions
    							ocr_highlights.addElement(highlight);

    						}
    					}

    					renderHighlight(highlight, g2);
    					renderEmbeddedText(currentTR,currentObject,DynamicVectorRenderer.TYPE3,aff,highlight, textStrokeCol,textFillCol,strokeOpacity,fillOpacity,lineWidth);

    					break;

    				case DynamicVectorRenderer.IMAGE:

    					if(newClip){
    						RenderUtils.renderClip(clipToUse, dirtyRegion,defaultClip,g2);
    						newClip=false;
    					}

                        renderImage(afValues1, afValues2, afValues3,afValues4, pageObjects, imageOptions,currentObject, fillOpacity, x, y, iCount, afCount, imageUsed, i);

    					iCount++;

    					break;

    				case DynamicVectorRenderer.CLIP:
    					clipToUse=pageClips[cCount];
    					newClip=true;
    					cCount++;
    					break;

    				case DynamicVectorRenderer.AF:
    					afCount++;
    					break;
    				case DynamicVectorRenderer.FONTSIZE:
    					fsCount++;
    					break;
    				case DynamicVectorRenderer.LINEWIDTH:
    					lineWidth=lwValues[lwCount];
    					lwCount++;
    					break;
    				case DynamicVectorRenderer.TEXTCOLOR:

    					if(debug)
    						System.out.println("TextCOLOR");

    					textFillType=textFill[tCount];
						
    					if(textColor==null || (textColor!=null && !(i>=endItem) && !checkColorThreshold(((PdfPaint) text_color[tCount]).getRGB()))){ //Not specified an overriding color
    						
    						if(textFillType==GraphicsState.STROKE)
    							textStrokeCol=(PdfPaint) text_color[tCount];
    						else
    							textFillCol=(PdfPaint) text_color[tCount];

    					}else{ //Use specified overriding color
    						if(textFillType==GraphicsState.STROKE)
    							textStrokeCol = new PdfColor(textColor.getRed(), textColor.getGreen(), textColor.getBlue());
        					else
        						textFillCol = new PdfColor(textColor.getRed(), textColor.getGreen(), textColor.getBlue());
    					}
    					tCount++;
    					break;
    				case DynamicVectorRenderer.FILLCOLOR:

    					if(debug)
    						System.out.println("FillCOLOR");

    					if(!colorsLocked){
    						fillCol=(PdfPaint) fill_color[fillCount];
    						
    						if(textColor!=null && !(i>=endItem) && checkColorThreshold(fillCol.getRGB())){
    							fillCol = new PdfColor(textColor.getRed(), textColor.getGreen(), textColor.getBlue());
    						}
    						
    					}
    					fillCount++;

    					break;
    				case DynamicVectorRenderer.STROKECOLOR:

    					if(debug)
    						System.out.println("StrokeCOL");

    					if(!colorsLocked){
    						
    						strokeCol=(PdfPaint)stroke_color[strokeCount];
    						
    						if(textColor!=null && !(i>=endItem) && checkColorThreshold(strokeCol.getRGB())){
    							strokeCol = new PdfColor(textColor.getRed(), textColor.getGreen(), textColor.getBlue());
    						}
    						
    						if(strokeCol!=null)
    							strokeCol.setScaling(cropX,cropH,scaling,0 ,0);
    					}

    					strokeCount++;
    					break;

    				case DynamicVectorRenderer.STROKE:

    					currentStroke=(Stroke)stroke[stCount];

    					if(debug)
    						System.out.println("STROKE");

    					stCount++;
    					break;

    				case DynamicVectorRenderer.TR:

    					if(debug)
    						System.out.println("TR");

    					currentTR=TRvalues[trCount];
    					trCount++;
    					break;

    				case DynamicVectorRenderer.STROKEOPACITY:

    					if(debug)
    						System.out.println("Stroke Opacity "+opacity[opCount]+" opCount="+opCount);

    					strokeOpacity=opacity[opCount];
    					opCount++;
    					break;

    				case DynamicVectorRenderer.FILLOPACITY:

    					if(debug)
    						System.out.println("Set Fill Opacity "+opacity[opCount]+" count="+opCount);

    					fillOpacity=opacity[opCount];
    					opCount++;
    					break;

    				case DynamicVectorRenderer.STRING:

    					Shape s1 = g2.getClip();
    					g2.setClip(defaultClip);
    					AffineTransform defaultAf=g2.getTransform();
    					String displayValue=(String)currentObject;

    					double[] af=new double[6];

    					g2.getTransform().getMatrix(af);

    					if(af[2]!=0)
    						af[2]=-af[2];
    					if(af[3]!=0)
    						af[3]=-af[3];
    					g2.setTransform(new AffineTransform(af));

    					Font javaFont=(Font) javaObjects[stringCount];

    					g2.setFont(javaFont);

    					if((currentTR & GraphicsState.FILL)==GraphicsState.FILL){

    						if(textFillCol!=null)
    							textFillCol.setScaling(cropX,cropH,scaling,0 ,0);

    						if(customColorHandler !=null){
    							customColorHandler.setPaint(g2,textFillCol,pageNumber, isPrinting);
    						}else if(PdfDecoder.Helper!=null){
    							PdfDecoder.Helper.setPaint(g2, textFillCol, pageNumber, isPrinting);
    						}else
    							g2.setPaint(textFillCol);

    					}

    					if((currentTR & GraphicsState.STROKE)==GraphicsState.STROKE){

    						if(textStrokeCol!=null)
    							textStrokeCol.setScaling(cropX,cropH,scaling,0 ,0);

    						if(customColorHandler !=null){
    							customColorHandler.setPaint(g2,textFillCol,pageNumber, isPrinting);
    						}else if(PdfDecoder.Helper!=null){
    							PdfDecoder.Helper.setPaint(g2, textFillCol, pageNumber, isPrinting);
    						}else
    							g2.setPaint(textFillCol);

    					}

    					g2.drawString(displayValue,x,y);

    					//restore defaults
    					g2.setTransform(defaultAf);
    					g2.setClip(s1);

    					stringCount++;

    					break;

    				case DynamicVectorRenderer.XFORM:

    					renderXForm((DynamicVectorRenderer)currentObject,fillOpacity);
    					break;

    				case DynamicVectorRenderer.CUSTOM:

    					Shape s2 = g2.getClip();
    					g2.setClip(defaultClip);
    					AffineTransform af2 = g2.getTransform();

    					JPedalCustomDrawObject customObj=(JPedalCustomDrawObject)currentObject;
    					if(isPrinting)
    						customObj.print(g2, this.pageNumber);
    					else
    						customObj.paint(g2);

    					g2.setTransform(af2);
    					g2.setClip(s2);

    					break;

    				}
    			}
    		}
    	}

    	//needs to be before we return defualts to factor
    	//in a viewport for abacus
    	if(needsHighlights && highlights!=null){
    		for(int h=0; h!=highlights.length; h++){
    			ignoreHighlight=false;
    			renderHighlight(highlights[h], g2);
    		}
    	}

    	//draw OCR highlights at end
    	if(ocr_highlights!=null){
    		Rectangle[] highlights2 = ocr_highlights.get();

    		//Backup current g2 paint and composite
    		Composite comp = g2.getComposite();
    		Paint p = g2.getPaint();

    		for(int h=0; h!=highlights2.length; h++){
    			if(highlights2[h]!=null){

    				//Set new values for highlight
    				g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,PdfDecoder.highlightComposite));

    				g2.setPaint(DecoderOptions.highlightColor);

    				//Draw highlight
    				g2.fill(highlights2[h]);

    			}
    			//Reset to starting values
    			g2.setComposite(comp);
    			g2.setPaint(p);

    		}
    	}

    	//restore defaults
    	g2.setClip(defaultClip);

    	g2.setTransform(rawScaling);

    	//if(DynamicVectorRenderer.debugPaint)
    	//	System.err.println("Painted "+paintedCount);

    	//tell user if problem
    	if(frame!=null && renderFailed && !userAlerted){

    		userAlerted=true;

    		if(PdfDecoder.showErrorMessages){
    			String status = (Messages.getMessage("PdfViewer.ImageDisplayError")+
    					Messages.getMessage("PdfViewer.ImageDisplayError1")+
    					Messages.getMessage("PdfViewer.ImageDisplayError2")+
    					Messages.getMessage("PdfViewer.ImageDisplayError3")+
    					Messages.getMessage("PdfViewer.ImageDisplayError4")+
    					Messages.getMessage("PdfViewer.ImageDisplayError5")+
    					Messages.getMessage("PdfViewer.ImageDisplayError6")+
    					Messages.getMessage("PdfViewer.ImageDisplayError7"));

    			JOptionPane.showMessageDialog(frame,status);

    			frame.invalidate();
    			frame.repaint();
    		}
    	}

    	//reduce count
    	paintThreadCount--;

    	//track so we do not redo onto raster
    	if(optimsePainting){
    		lastItemPainted=count;
    	}else
    		lastItemPainted=-1;

    	//track
    	lastScaling=scaling;

    	//if we highlighted text return oversized
    	if(minX==-1)
    		return null;
    	else
    		return new Rectangle((int)minX,(int)minY,(int)(maxX-minX),(int)(maxY-minY));
    }

    private static Rectangle getObjectArea(double[] afValues1, int[] fsValues,
                                           double[] afValues2, double[] afValues3, double[] afValues4,
                                           Object[] pageObjects, Rectangle[] areas, int type, float x,
                                           float y, int fsCount, int afCount, int i){

    	Rectangle currentArea=null;

    	if(afValues1!=null && type==DynamicVectorRenderer.IMAGE){

    		if(areas!=null)
    			currentArea=areas[i];

    	}else if(afValues1!=null && type==DynamicVectorRenderer.SHAPE){

    		currentArea=((Shape)pageObjects[i]).getBounds();

    	}else if(type==DynamicVectorRenderer.TEXT && afCount>-1){

    		//Use on page coords to make sure the glyph needs highlighting
    		currentArea=RenderUtils.getAreaForGlyph(new float[][]{{(float)afValues1[afCount],(float)afValues2[afCount],0},
    				{(float)afValues3[afCount],(float)afValues4[afCount],0},{x,y,1}});

    	}else if(fsCount!=-1 && afValues1!=null){// && afCount>-1){

    		int realSize=fsValues[fsCount];
    		if(realSize<0) //ignore sign which is used as flag elsewhere
    			currentArea=new Rectangle((int)x+realSize,(int)y,-realSize,-realSize);
    		else
    			currentArea=new Rectangle((int)x,(int)y,realSize,realSize);
    	}
    	return currentArea;
    }

    private void renderImage(double[] afValues1, double[] afValues2,
    		double[] afValues3, double[] afValues4, Object[] pageObjects,
    		int[] imageOptions, Object currentObject, float fillOpacity,
    		float x, float y, int iCount, int afCount, int imageUsed, int i){

    	int currentImageOption=PDFImageProcessing.NOTHING;
    	if(imageOptions!=null)
    		currentImageOption=imageOptions[iCount];

    	int sampling=1,w1=0,pY=0,defaultSampling=1;

    	// generate unique value to every image on given page (no more overighting stuff in the hashmap)
    	String key = Integer.toString(this.pageNumber) + Integer.toString(iCount);

    	//useHiResImageForDisplay added back by Mark as break memory images - use customers1/annexe1.pdf to test any changes
    	if(useHiResImageForDisplay && !isType3Font  ){// && useHiResImageForDisplay){

    		float  scalingToUse=scaling;
    		int defaultX=0,pX=0,defaultY=0,colorspaceID=0,h1=0;
    		byte[] maskCol=null;
    		BufferedImage image=null;
    		
    		if( objectStoreRef.isRawImageDataSaved(key)){
    			//fix for rescaling on Enkelt-Scanning_-_Bank-10.10.115.166_-_12-12-2007_-_15-27-57jpg50-300.pdf
    			if(useHiResImageForDisplay){
    				//costa se non commento l'immagine viene tagliata
//    				    			if(scaling<1)
//    				    				scalingToUse=1f;
    			}

    			defaultX=((Integer) objectStoreRef.getRawImageDataParameter(key,ObjectStore.IMAGE_pX)).intValue();
    			pX=(int)(defaultX*scalingToUse);

    			defaultY=((Integer) objectStoreRef.getRawImageDataParameter(key,ObjectStore.IMAGE_pY)).intValue();
    			pY=(int)(defaultY*scalingToUse);

    			w1=((Integer) objectStoreRef.getRawImageDataParameter(key,ObjectStore.IMAGE_WIDTH)).intValue();
    			h1=((Integer) objectStoreRef.getRawImageDataParameter(key,ObjectStore.IMAGE_HEIGHT)).intValue();

    			maskCol=(byte[]) objectStoreRef.getRawImageDataParameter(key,ObjectStore.IMAGE_MASKCOL);

    			colorspaceID=((Integer) objectStoreRef.getRawImageDataParameter(key,ObjectStore.IMAGE_COLORSPACE)).intValue();

    		 image=null;
    		}
    		else{
    			//test costa
//    			defaultX=((BufferedImage)currentObject).getWidth();
//    			defaultX=88;
//    			pX=(int)(defaultX*scalingToUse);
//
//    			defaultY=((BufferedImage)currentObject).getHeight();
//    			defaultY=63;
//    			pY=(int)(defaultY*scalingToUse);
//
//    			w1=((BufferedImage)currentObject).getWidth();
//    			h1=((BufferedImage)currentObject).getHeight();
//
//    			maskCol=(byte[]) objectStoreRef.getRawImageDataParameter(key,ObjectStore.IMAGE_MASKCOL);

    			//colorspaceID=((Integer) objectStoreRef.getRawImageDataParameter(key,ObjectStore.IMAGE_COLORSPACE)).intValue();

    		}
    		/**
    		 * down-sample size if displaying  
    		 */
    		if(pX>0){

    			//see what we could reduce to and still be big enough for page
    			int newW=w1,newH=h1;

    			int smallestH=pY<<2; //double so comparison works
    			int smallestW=pX<<2;

    			//cannot be smaller than page
    			while(newW>smallestW && newH>smallestH){
    				sampling=sampling<<1;
    				newW=newW>>1;
    				newH=newH>>1;
    			}

    			int scaleX=w1/pX;
    			if(scaleX<1)
    				scaleX=1;

    			int scaleY=h1/pY;
    			if(scaleY<1)
    				scaleY=1;

    			//choose smaller value so at least size of page
    			sampling=scaleX;
    			if(sampling>scaleY)
    				sampling=scaleY;

    			/**
    			 * work out default as well for ratio
    			 */

    			//see what we could reduce to and still be big enough for page
    			int defnewW=w1,defnewH=h1;

    			int defsmallestH=pY<<2; //double so comparison works
    			int defsmallestW=pX<<2;

    			//cannot be smaller than page
    			while(defnewW>defsmallestW && defnewH>defsmallestH){
    				defaultSampling=defaultSampling<<1;
    				defnewW=defnewW>>1;
    				defnewH=defnewH>>1;
    			}

    			int defscaleX=w1/defaultX;
    			if(defscaleX<1)
    				defscaleX=1;

    			int defscaleY=h1/defaultY;
    			if(defscaleY<1)
    				defscaleY=1;

    			//choose smaller value so at least size of page
    			defaultSampling=defscaleX;
    			if(defaultSampling>defscaleY)
    				defaultSampling=defscaleY;

    			//rescan all pixels and down-sample image
    			if((scaling>1f || lastScaling>1f)&& sampling>=1 && (lastScaling!=scaling)){

    				newW=w1/sampling;
    				newH=h1/sampling;

    				image=resampleImageData(sampling, w1, h1, maskCol,newW, newH, key,colorspaceID);

    			}
    		}

    		/**
    		 * reset image stored by renderer
    		 */
    		if(image!=null){
    			//reset our track if only graphics
    			if(singleImage!=null)
    				singleImage=image;

    			pageObjects[i]=image;
    			currentObject=image;
    		}
    	}
    	//now draw the image (hires or downsampled)
    	if(this.useHiResImageForDisplay){

    		double aa=1;
    		if(sampling>=1 && scaling>1 && w1>0) //factor in any scaling
    			aa=((float)sampling)/defaultSampling;

    		AffineTransform imageAf=new AffineTransform(afValues1[afCount]*aa,afValues2[afCount]*aa,afValues3[afCount]*aa,afValues4[afCount]*aa,x,y);

    		//get image and reload if needed
    		BufferedImage img=null;
    		if(currentObject!=null)
    			img=(BufferedImage)currentObject;

    		if(currentObject==null)
    			img = reloadCachedImage(imageUsed, i, img);

    		if(img!=null)
    			renderImage(imageAf,img,fillOpacity,null,x,y,currentImageOption);

    	}else{

    		AffineTransform before=g2.getTransform();
    		extraRot = false;

    		if(pY>0){

    			double[] matrix=new double[6];
    			g2.getTransform().getMatrix(matrix);
    			double ratio=((float)pY)/((BufferedImage)currentObject).getHeight();

    			matrix[0]=ratio;
    			matrix[1]=0;
    			matrix[2]=0;
    			matrix[3]=-ratio;

    			g2.scale(1f/scaling,1f/scaling);

    			g2.setTransform(new AffineTransform(matrix));

    		}else{
    			extraRot = true;
    		}

    		renderImage(null,(BufferedImage)currentObject,fillOpacity,null,x,y,currentImageOption);
    		g2.setTransform(before);
    	}
    }

    private BufferedImage resampleImageData(int sampling, int w1, int h1, byte[] maskCol, int newW, int newH, String key, int ID) {

    	//get data
		byte[] data= objectStoreRef.getRawImageData(key);

		
    	//make 1 bit indexed flat
		byte[] index=null;
		if(maskCol!=null && ID!=ColorSpaces.DeviceRGB)
			index=maskCol;

		int size=newW*newH;
		if(index!=null)
			size=size*3;

    	byte[] newData=new byte[size];

    	final int[] flag={1,2,4,8,16,32,64,128};

    	int origLineLength= (w1+7)>>3;

    	int offset=0;

    	for(int y1=0;y1<newH;y1++){
    		for(int x1=0;x1<newW;x1++){

    			int bytes=0,count1=0;

    			//allow for edges in number of pixels left
    			int wCount=sampling,hCount=sampling;
    			int wGapLeft=w1-x1;
    			int hGapLeft=h1-y1;
    			if(wCount>wGapLeft)
    				wCount=wGapLeft;
    			if(hCount>hGapLeft)
    				hCount=hGapLeft;

    			//count pixels in sample we will make into a pixel (ie 2x2 is 4 pixels , 4x4 is 16 pixels)
    			int ptr;
                byte currentByte;
                for(int yy=0;yy<hCount;yy++){
    				for(int xx=0;xx<wCount;xx++){

                        ptr=((yy+(y1*sampling))*origLineLength)+(((x1*sampling)+xx)>>3);
    					if(ptr<data.length){
                            currentByte=data[ptr];
                        }else{
                            currentByte=(byte)255;
                        }

                        int bit=currentByte & flag[7-(((x1*sampling)+xx)& 7)];

    					if(bit!=0)
    						bytes++;
    					count1++;

    				}
    			}

    			//set value as white or average of pixels
    			if(count1>0){

    				if(index==null){
    					newData[x1+(newW*y1)]=(byte)((255*bytes)/count1);
    				}else{
    					for(int ii=0;ii<3;ii++){
    						if(bytes/count1<0.5f)
    							newData[offset]=(byte)((((maskCol[ii] & 255))));
    						else
    							newData[offset]=(byte) 255;

    						offset++;

    					}
    				}
    			}else{
    				if(index==null){
    					newData[x1+(newW*y1)]=(byte) 255;
    				}else{
    					for(int ii=0;ii<3;ii++){
    						newData[offset]=(byte) 255;

    						offset++;
    					}
    				}
    			}
    		}
    	}
    	
    	/**
    	 * build the image
    	 */
    	BufferedImage image=null;
    	Raster raster;
    	int type=BufferedImage.TYPE_BYTE_GRAY;
    	DataBuffer db = new DataBufferByte(newData, newData.length);
    	int[] bands = {0};
    	int count=1;
    	
    	
    	if(maskCol==null && (w1*h1*3==data.length)){// && ID!=ColorSpaces.DeviceRGB){ //use this set of values for this case
			type=BufferedImage.TYPE_INT_RGB;
			bands = new int[]{0,1,2};
			count=3;
		}
    	
    	image =new BufferedImage(newW,newH,type);
    	raster =Raster.createInterleavedRaster(db,newW,newH,newW*count,count,bands,null);
    	image.setData(raster);

    	return image;
    }

	private BufferedImage reloadCachedImage(int imageUsed, int i,
			BufferedImage img) {
		Object currentObject;
		try{

		    //cache single images in memory for speed
		    if(singleImage!=null){
		        currentObject=singleImage.getSubimage(0,0,singleImage.getWidth(),singleImage.getHeight());

		        /**
		         * load from memory or disk
		         */
		    }else if(rawKey==null)
		        currentObject=largeImages.get("HIRES_"+i);
		    else
		        currentObject=largeImages.get("HIRES_"+i+ '_' +rawKey);

		    if(currentObject==null){

		        int keyID=i;
		        if(imageUsed!=-1)
		            keyID=imageUsed;

		        if(rawKey==null)
		            currentObject=objectStoreRef.loadStoredImage(pageNumber+"_HIRES_"+keyID);
		        else
		            currentObject=objectStoreRef.loadStoredImage(pageNumber+"_HIRES_"+keyID+ '_' +rawKey);

		        //flag if problem
		        if(currentObject==null)
		            renderFailed=true;

		        //recache
		        if(!isPrinting){

		            if(rawKey==null)
		                largeImages.put("HIRES_"+i,currentObject);
		            else
		                largeImages.put("HIRES_"+i,currentObject+"_"+rawKey);
		        }
		    }

		    img=(BufferedImage)currentObject;

		}catch(Exception e){
            //tell user and log
            if(LogWriter.isOutput())
                LogWriter.writeLog("Exception: "+e.getMessage());
		}
		return img;
	}

    /**
     * allow user to set component for waring message in renderer to appear -
     * if unset no message will appear
     * @param frame
     */
    public void setMessageFrame(Container frame){
        this.frame=frame;
    }

    /**
     * highlight a glyph by reversing the display. For white text, use black
     */
    private Rectangle setHighlightForGlyph(Rectangle area,Rectangle[] highlights) {


        if (highlights == null || textHighlightsX == null)
            return null;

        ignoreHighlight = false;
        for(int j=0; j!= highlights.length; j++){
            if(highlights[j]!=null && area!=null && (highlights[j].intersects(area))){

                //Get intersection of the two areas
                Rectangle intersection = highlights[j].intersection(area);

                //Intersection area between highlight and text area
                float iArea = intersection.width*intersection.height;

                //25% of text area
                float tArea = (area.width*area.height)/ 4f;

                //Only highlight if (x.y) is with highlight and more than 25% intersects
                //or intersect is greater than 60%
                if((highlights[j].contains(area.x, area.y) && iArea>tArea) ||
                        iArea>(area.width*area.height)/ 1.667f){
                    if(!drawnHighlights[j]){
                        ignoreHighlight = false;
                        drawnHighlights[j]=true;
                        return highlights[j];
                    }else{
                        ignoreHighlight = true;
                        return highlights[j];
                    }
                }
            }
        }

        //old code not used
        return null;

    }

    /* saves text object with attributes for rendering*/
    public void drawText(float[][] Trm, String text, GraphicsState currentGraphicsState, float x, float y, Font javaFont) {

        /**
         * set color first
         */
        PdfPaint currentCol=null;

        if(Trm!=null){
            double[] nextAf=new double[]{Trm[0][0],Trm[0][1],Trm[1][0],Trm[1][1],Trm[2][0],Trm[2][1]};

            if((lastAf[0]==nextAf[0])&&(lastAf[1]==nextAf[1])&&
                    (lastAf[2]==nextAf[2])&&(lastAf[3]==nextAf[3])){
            }else{
                this.drawAffine(nextAf);
                lastAf[0]=nextAf[0];
                lastAf[1]=nextAf[1];
                lastAf[2]=nextAf[2];
                lastAf[3]=nextAf[3];
            }
        }

        int text_fill_type = currentGraphicsState.getTextRenderType();

        //for a fill
        if ((text_fill_type & GraphicsState.FILL) == GraphicsState.FILL) {
            currentCol=currentGraphicsState.getNonstrokeColor();

            if(currentCol.isPattern()){
                drawColor(currentCol,GraphicsState.FILL);
                resetTextColors=true;
            }else{

                int newCol=(currentCol).getRGB();
                if((resetTextColors)||((lastFillTextCol!=newCol))){
                    lastFillTextCol=newCol;
                    drawColor(currentCol,GraphicsState.FILL);
                }
            }
        }

        //and/or do a stroke
        if ((text_fill_type & GraphicsState.STROKE) == GraphicsState.STROKE){
            currentCol=currentGraphicsState.getStrokeColor();

            if(currentCol.isPattern()){
                drawColor(currentCol,GraphicsState.STROKE);
                resetTextColors=true;
            }else{

                int newCol=currentCol.getRGB();
                if((resetTextColors)||(lastStrokeCol!=newCol)){
                    lastStrokeCol=newCol;
                    drawColor(currentCol,GraphicsState.STROKE);
                }
            }
        }

        pageObjects.addElement(text);
        javaObjects.addElement(javaFont);

        objectType.addElement(DynamicVectorRenderer.STRING);

        //add to check for transparency if large
        int fontSize=javaFont.getSize();
        if(fontSize>100)
            areas.addElement(new Rectangle((int)x,(int)y,fontSize,fontSize));
        else
            areas.addElement(null);

        x_coord=RenderUtils.checkSize(x_coord,currentItem);
        y_coord=RenderUtils.checkSize(y_coord,currentItem);
        x_coord[currentItem]=x;
        y_coord[currentItem]=y;

        currentItem++;

        resetTextColors=false;

    }

    /**workout combined area of shapes in an area*/
    public  Rectangle getCombinedAreas(Rectangle targetRectangle,boolean justText){

        Rectangle combinedRectangle=null;

        if(areas!=null){

            //set defaults for new area
            Rectangle target = targetRectangle.getBounds();
            int x2=target.x;
            int y2=target.y;
            int x1=x2+target.width;
            int y1=y2+target.height;

            boolean matchFound=false;

            Rectangle[] currentAreas=areas.get();

            //find all items enclosed by this rectangle
            for (Rectangle currentArea : currentAreas) {
                if ((currentArea != null) && (targetRectangle.contains(currentArea))) {
                    matchFound = true;

                    int newX = currentArea.x;
                    if (x1 > newX)
                        x1 = newX;
                    newX = currentArea.x + currentArea.width;
                    if (x2 < newX)
                        x2 = newX;

                    int newY = currentArea.y;
                    if (y1 > newY)
                        y1 = newY;
                    newY = currentArea.y + currentArea.height;
                    if (y2 < newY)
                        y2 = newY;
                }
            }

            //allow margin of 1 around object
            if(matchFound){
                combinedRectangle=new Rectangle(x1-1,y1+1,(x2-x1)+2,(y2-y1)+2);

            }
        }

        return combinedRectangle;
    }

    /* save image in array to draw */
    public int drawImage(int pageNumber,BufferedImage image,
                               GraphicsState currentGraphicsState,
                               boolean alreadyCached,String name, int optionsApplied, int previousUse) {

    	if(previousUse!=-1)
    		return redrawImage(pageNumber, currentGraphicsState, name, previousUse);

        this.pageNumber=pageNumber;
        float CTM[][]=currentGraphicsState.CTM;

        float x=currentGraphicsState.x;
        float y=currentGraphicsState.y;

        double[] nextAf=new double[6];

        boolean cacheInMemory=(image.getWidth()<100 && image.getHeight()<100) || image.getHeight()==1;

        String key;
        if(rawKey==null)
            key=pageNumber+"_"+(currentItem+1);
        else
            key=rawKey+ '_' +(currentItem+1);

        if(imageOptions==null){
            imageOptions=new Vector_Int(defaultSize);
            imageOptions.setCheckpoint();
        }

        //for special case on Page 7 randomHouse/9780857510839
        boolean oddRotationCase=optionsApplied==0 && CTM[0][0]<0 && CTM[0][1]>0 && CTM[1][0]<0 && CTM[1][1]<0 && pageRotation==0 && type==1;

        //Turn image around if needed
        //(avoid if has skew on as well as currently breaks image)
        if(!alreadyCached && image.getHeight()>1 && ((optionsApplied & PDFImageProcessing.IMAGE_INVERTED) !=PDFImageProcessing.IMAGE_INVERTED)){

            boolean turnLater=(optimisedTurnCode && (CTM[0][0]*CTM[0][1]==0) && (CTM[1][1]*CTM[1][0]==0) && !RenderUtils.isRotated(CTM));
            
            if((!optimisedTurnCode || !turnLater) && pageRotation != 90 && pageRotation != 270) {
                if(type==3 || oddRotationCase)
                    image = RenderUtils.invertImage(CTM, image);
            }

            if(turnLater)
                optionsApplied=optionsApplied + PDFImageProcessing.TURN_ON_DRAW;

        }

        imageOptions.addElement(optionsApplied);

        if(useHiResImageForDisplay){

            int w,h;

            if(!alreadyCached || cachedWidths.get(key)==null){
                w = image.getWidth();
                h = image.getHeight();
            }else{
                w= (Integer) cachedWidths.get(key);
                h= (Integer) cachedHeights.get(key);
            }

            boolean isRotated=RenderUtils.isRotated(CTM);
            if(isRotated){

                if((optionsApplied & PDFImageProcessing.IMAGE_ROTATED) !=PDFImageProcessing.IMAGE_ROTATED){    //fix for odd rotated behaviour

                    AffineTransform tx = new AffineTransform();
                    tx.rotate(Math.PI/2, w/2, h/2);
                    tx.translate(-(h-tx.getTranslateX()),-tx.getTranslateY());

                    //allow for 1 pixel high
                    double[] matrix=new double[6];
                    tx.getMatrix(matrix);
                    if(matrix[4]<1){
                        matrix[4]=1;
                        tx =new AffineTransform(matrix);
                    }

                    AffineTransformOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_BILINEAR);

                    if(image!=null){

                        if(image.getHeight()>1 && image.getWidth()>1)
                            image = op.filter(image, null);

                        if(RenderUtils.isInverted(CTM) && ((optionsApplied & PDFImageProcessing.IMAGE_ROTATED) !=PDFImageProcessing.IMAGE_ROTATED)){
                            //turn upside down
                            AffineTransform image_at2 =new AffineTransform();
                            image_at2.scale(-1,1);
                            image_at2.translate(-image.getWidth(),0);

                            AffineTransformOp invert3= new AffineTransformOp(image_at2,  ColorSpaces.hints);

                            if(image.getType()==12){ //avoid turning into ARGB
                                BufferedImage source=image;
                                image =new BufferedImage(source.getWidth(),source.getHeight(),source.getType());

                                invert3.filter(source,image);
                            }else
                                image=invert3.filter(image,null);
                        }
                    }

                    float[][] scaleDown={{0,1f/h,0},{1f/w,0,0},{0,0,1}};
                    CTM=Matrix.multiply(scaleDown,CTM);

                }else{
                    float[][] scaleDown={{0,1f/w,0},{1f/h,0,0},{0,0,1}};
                    CTM=Matrix.multiply(scaleDown,CTM);
                }

            }else{
                float[][] scaleDown={{1f/w,0,0},{0,1f/h,0},{0,0,1}};
                CTM=Matrix.multiply(scaleDown,CTM);
            }

            AffineTransform upside_down=null;

            upside_down=new AffineTransform(CTM[0][0],CTM[0][1],CTM[1][0],CTM[1][1],0,0);

            upside_down.getMatrix(nextAf);

            this.drawAffine(nextAf);

            lastAf[0]=nextAf[0];
            lastAf[1]=nextAf[1];
            lastAf[2]=nextAf[2];
            lastAf[3]=nextAf[3];

            if(!alreadyCached && !cacheInMemory){

                if(!isPrinting){
                    if(rawKey==null){
                        largeImages.put("HIRES_"+currentItem,image);
                    }else
                        largeImages.put("HIRES_"+currentItem+ '_' +rawKey,image);

                    //cache PDF with single image for speed
                    if(imageCount==0){
                        singleImage=image.getSubimage(0,0,image.getWidth(),image.getHeight());

                        imageCount++;
                    }else
                        singleImage=null;
                }
                if(rawKey==null){
                    objectStoreRef.saveStoredImage(pageNumber+"_HIRES_"+currentItem,image,false,false,"tif");
                    imageIDtoName.put(currentItem,pageNumber+"_HIRES_"+currentItem);
                }else{
                    objectStoreRef.saveStoredImage(pageNumber+"_HIRES_"+currentItem+ '_' +rawKey,image,false,false,"tif");
                    imageIDtoName.put(currentItem,pageNumber+"_HIRES_"+currentItem+ '_' +rawKey);
                }

                if(rawKey==null)
                    key=pageNumber+"_"+currentItem;
                else
                    key=rawKey+ '_' +currentItem;

                cachedWidths.put(key, w);
                cachedHeights.put(key, h);
            }
        }

        x_coord=RenderUtils.checkSize(x_coord,currentItem);
        y_coord=RenderUtils.checkSize(y_coord,currentItem);
        x_coord[currentItem]=x;
        y_coord[currentItem]=y;

        objectType.addElement(DynamicVectorRenderer.IMAGE);
        float WidthModifier = 1;
        float HeightModifier = 1;

        if(useHiResImageForDisplay){
            if(!alreadyCached){
                WidthModifier = image.getWidth();
                HeightModifier = image.getHeight();
            }else{
                WidthModifier= (Integer) cachedWidths.get(key);
                HeightModifier= (Integer) cachedHeights.get(key);
            }
        }

        //ignore in this case /PDFdata/baseline_screens/customers3/1773_A2.pdf
        if(CTM[0][0]>0 && CTM[0][0]<0.05 && CTM[0][1]!=0 && CTM[1][0]!=0 && CTM[1][1]!=0){
            areas.addElement(null);
        }else{
        w=(int)(CTM[0][0]*WidthModifier);
        if(w==0)
            w=(int)(CTM[0][1]*WidthModifier);
        h=(int)(CTM[1][1]*HeightModifier);
        if(h==0)
            h=(int)(CTM[1][0]*HeightModifier);

        //fix for bug if sheered in low res
        if(!useHiResImageForDisplay && CTM[1][0]<0 && CTM[0][1]>0 && CTM[0][0]==0 && CTM[1][1]==0){
            int tmp=w;
            w=-h;
            h=tmp;
        }

        //corrected in generation
        if(h<0 && !useHiResImageForDisplay)
            h=-h;

        //fix negative height on Ghostscript image in printing
        int x1=(int)currentGraphicsState.x;
        int y1=(int)currentGraphicsState.y;
        int w1=w;
        int h1=h;
        if(h1<0){
            y1=y1+h1;
            h1=-h1;
        }

        if(h1==0)
            h1=1;

        Rectangle rect=new Rectangle(x1,y1,w1,h1);

        areas.addElement(rect);

        checkWidth(rect);
        }

        if(useHiResImageForDisplay && !cacheInMemory){
            pageObjects.addElement(null);
        }else
            pageObjects.addElement(image);

        //store id so we can get as low res image
        imageID.put(name, currentItem);

        //nore minus one as affine not yet done
        storedImageValues.put("imageOptions-"+currentItem, optionsApplied);
        storedImageValues.put("imageAff-"+currentItem,nextAf);

        currentItem++;

        return currentItem-1;
    }

    /* save image in array to draw */
    private int redrawImage(int pageNumber, GraphicsState currentGraphicsState, String name,int previousUse) {

        this.pageNumber=pageNumber;

        float x=currentGraphicsState.x;
        float y=currentGraphicsState.y;

        imageOptions.addElement((Integer) storedImageValues.get("imageOptions-" + previousUse));

        if(useHiResImageForDisplay){
        }

        x_coord=RenderUtils.checkSize(x_coord,currentItem);
        y_coord=RenderUtils.checkSize(y_coord,currentItem);
        x_coord[currentItem]=x;
        y_coord[currentItem]=y;

        objectType.addElement(DynamicVectorRenderer.REUSED_IMAGE);

        Rectangle previousRectangle=areas.elementAt(previousUse);

        Rectangle newRect=null;

        if(previousRectangle!=null)
            newRect=new Rectangle((int)x,(int)y,previousRectangle.width,previousRectangle.height);

        areas.addElement(newRect);

        if(previousRectangle!=null)
            checkWidth(newRect);

        pageObjects.addElement(previousUse);

        //store id so we can get as low res image
        imageID.put(name, previousUse);

        currentItem++;

        return currentItem-1;
    }

    /**
     * track actual size of shape
     */
    private void checkWidth(Rectangle rect) {

        int x1=rect.getBounds().x;
        int y2=rect.getBounds().y;
        int y1=y2+rect.getBounds().height;
        int x2=x1+rect.getBounds().width;

        if(x1<pageX1)
            pageX1=x1;
        if(x2>pageX2)
            pageX2=x2;

        if(y1>pageY1)
            pageY1=y1;
        if(y2<pageY2)
            pageY2=y2;
    }

    /**
     * return which part of page drawn onto
     * @return
     */
    public Rectangle getOccupiedArea(){
        return new Rectangle(pageX1,pageY1,(pageX2-pageX1),(pageY1-pageY2));
    }

    /*save shape in array to draw*/
    public void drawShape(Shape currentShape,GraphicsState currentGraphicsState,int cmd) {

        int fillType=currentGraphicsState.getFillType();
        PdfPaint currentCol;

        int newCol;

        //check for 1 by 1 complex shape and replace with dot
        if(currentShape.getBounds().getWidth()==1 &&
                currentShape.getBounds().getHeight()==1 && currentGraphicsState.getLineWidth()<1)
            currentShape=new Rectangle(currentShape.getBounds().x,currentShape.getBounds().y,1,1);

        //stroke and fill (do fill first so we don't overwrite Stroke)
        if (fillType == GraphicsState.FILL || fillType == GraphicsState.FILLSTROKE) {

            currentCol=currentGraphicsState.getNonstrokeColor();
            if(currentCol.isPattern()){

                drawFillColor(currentCol);
                fillSet=true;
            }else{
                newCol=currentCol.getRGB();
                if((!fillSet) || (lastFillCol!=newCol)){
                    lastFillCol=newCol;
                    drawFillColor(currentCol);
                    fillSet=true;
                }
            }
        }

        if ((fillType == GraphicsState.STROKE) || (fillType == GraphicsState.FILLSTROKE)) {

            currentCol=currentGraphicsState.getStrokeColor();

            if(currentCol instanceof Color){
                newCol=(currentCol).getRGB();

                if((!strokeSet) || (lastStrokeCol!=newCol)){
                    lastStrokeCol=newCol;
                    drawStrokeColor(currentCol);
                    strokeSet=true;
                }
            }else{
                drawStrokeColor(currentCol);
                strokeSet=true;
            }
        }

        Stroke newStroke=currentGraphicsState.getStroke();
        if((lastStroke!=null)&&(lastStroke.equals(newStroke))){

        }else{
            lastStroke=newStroke;
            drawStroke((newStroke));
        }

        pageObjects.addElement(currentShape);
        objectType.addElement(DynamicVectorRenderer.SHAPE);
        areas.addElement(currentShape.getBounds());

        checkWidth(currentShape.getBounds());


        x_coord=RenderUtils.checkSize(x_coord,currentItem);
        y_coord=RenderUtils.checkSize(y_coord,currentItem);
        x_coord[currentItem]=currentGraphicsState.x;
        y_coord[currentItem]=currentGraphicsState.y;

        shapeType.addElement(fillType);
        currentItem++;

        resetTextColors=true;

    }



    /*save text colour*/
    public void drawColor(PdfPaint currentCol, int type) {

        areas.addElement(null);
        pageObjects.addElement(null);
        objectType.addElement(DynamicVectorRenderer.TEXTCOLOR);
        textFillType.addElement(type); //used to flag which has changed

        text_color.addElement(currentCol);

        x_coord=RenderUtils.checkSize(x_coord,currentItem);
        y_coord=RenderUtils.checkSize(y_coord,currentItem);
        x_coord[currentItem]=0;
        y_coord[currentItem]=0;

        currentItem++;

        //ensure any shapes reset color
        strokeSet=false;
        fillSet=false;

    }

    /*add XForm object*/
    public void drawXForm(DynamicVectorRenderer dvr,GraphicsState gs) {

        areas.addElement(null);
        pageObjects.addElement(dvr);
        objectType.addElement(DynamicVectorRenderer.XFORM);

        x_coord[currentItem]=0;
        y_coord[currentItem]=0;

        currentItem++;
    }


    /**reset on colorspace change to ensure cached data up to data*/
    public void resetOnColorspaceChange(){

        fillSet=false;
        strokeSet=false;

    }

    /*save shape colour*/
    public void drawFillColor(PdfPaint currentCol) {

        pageObjects.addElement(null);
        objectType.addElement(DynamicVectorRenderer.FILLCOLOR);
        areas.addElement(null);
        
        fill_color.addElement(currentCol);

        x_coord=RenderUtils.checkSize(x_coord,currentItem);
        y_coord=RenderUtils.checkSize(y_coord,currentItem);
        x_coord[currentItem]=0;
        y_coord[currentItem]=0;

        currentItem++;

        this.lastFillCol=currentCol.getRGB();
    }

    /*save opacity settings*/
    public void setGraphicsState(int fillType,float value) {

        if(value!=1.0f || opacity!=null){

            if(opacity==null){
                opacity=new Vector_Float(defaultSize);
                opacity.setCheckpoint();
            }

            pageObjects.addElement(null);
            areas.addElement(null);

            if(fillType==GraphicsState.STROKE)
                objectType.addElement(DynamicVectorRenderer.STROKEOPACITY);
            else
                objectType.addElement(DynamicVectorRenderer.FILLOPACITY);

            opacity.addElement(value);

            x_coord=RenderUtils.checkSize(x_coord,currentItem);
            y_coord=RenderUtils.checkSize(y_coord,currentItem);
            x_coord[currentItem]=0;
            y_coord[currentItem]=0;

            currentItem++;
        }
    }

    /*Method to add Shape, Text or image to main display on page over PDF - will be flushed on redraw*/
    public void drawAdditionalObjectsOverPage(int[] type, Color[] colors, Object[] obj) throws PdfException {

        if(obj==null){
            return ;
        }

        /**
         * remember end of items from PDF page
         */
        if(endItem==-1){

            endItem=currentItem;

            objectType.setCheckpoint();

            shapeType.setCheckpoint();

            pageObjects.setCheckpoint();

            areas.setCheckpoint();

            clips.setCheckpoint();

            textFillType.setCheckpoint();

            text_color.setCheckpoint();

            fill_color.setCheckpoint();

            stroke_color.setCheckpoint();

            stroke.setCheckpoint();

            if(imageOptions==null)
                imageOptions=new Vector_Int(defaultSize);

            imageOptions.setCheckpoint();

            if(TRvalues==null)
                TRvalues=new Vector_Int(defaultSize);

            TRvalues.setCheckpoint();

            if(fs==null)
                fs=new Vector_Int(defaultSize);

            fs.setCheckpoint();

            if(lw==null)
                lw=new Vector_Int(defaultSize);

            lw.setCheckpoint();

            af1.setCheckpoint();

            af2.setCheckpoint();

            af3.setCheckpoint();

            af4.setCheckpoint();

            fontBounds.setCheckpoint();

            if(opacity!=null)
                opacity.setCheckpoint();

        }

        /**
         * cycle through items and add to display - throw exception if not valid
         */
        int count=type.length;

        final boolean debug=false;

        int currentType;

        GraphicsState gs;

        for(int i=0;i<count;i++){

            currentType=type[i];

            if(debug)
                System.out.println(i+" "+getTypeAsString(currentType)+" "+" "+obj[i]);

            switch(currentType){
                case DynamicVectorRenderer.FILLOPACITY:
                    setGraphicsState(GraphicsState.FILL, ((Float)obj[i]).floatValue());
                    break;

                case DynamicVectorRenderer.STROKEOPACITY:
                    setGraphicsState(GraphicsState.STROKE, ((Float)obj[i]).floatValue());
                    break;

                case DynamicVectorRenderer.STROKEDSHAPE:
                    gs=new GraphicsState();
                    gs.setFillType(GraphicsState.STROKE);
                    gs.setStrokeColor(new PdfColor(colors[i].getRed(),colors[i].getGreen(),colors[i].getBlue()));
                    drawShape( (Shape)obj[i],gs, Cmd.S);

                    break;

                case DynamicVectorRenderer.FILLEDSHAPE:
                    gs=new GraphicsState();
                    gs.setFillType(GraphicsState.FILL);
                    gs.setNonstrokeColor(new PdfColor(colors[i].getRed(),colors[i].getGreen(),colors[i].getBlue()));
                    drawShape( (Shape)obj[i],gs, Cmd.F);

                    break;

                case DynamicVectorRenderer.CUSTOM:
                    drawCustom(obj[i]);

                    break;

                case DynamicVectorRenderer.IMAGE:
                    ImageObject imgObj=(ImageObject)obj[i];
                    gs=new GraphicsState();

                    gs.CTM=new float[][]{ {imgObj.image.getWidth(),0,1}, {0,imgObj.image.getHeight(),1}, {0,0,0}};

                    gs.x=imgObj.x;
                    gs.y=imgObj.y;

                    drawImage(this.pageNumber,imgObj.image, gs,false,"extImg"+i, PDFImageProcessing.NOTHING, -1);

                    break;

                case DynamicVectorRenderer.STRING:
                    TextObject textObj=(TextObject)obj[i];
                    gs=new GraphicsState();
                    float fontSize=textObj.font.getSize();
                    double[] afValues={fontSize,0f,0f,fontSize,0f,0f};
                    drawAffine(afValues);

                    drawTR(GraphicsState.FILL);
                    gs.setTextRenderType(GraphicsState.FILL);
                    gs.setNonstrokeColor(new PdfColor(colors[i].getRed(),colors[i].getGreen(),colors[i].getBlue()));
                    drawText(null,textObj.text,gs,textObj.x,-textObj.y,textObj.font); //note y is negative

                    break;

                case 0:
                    break;

                default:
                    throw new PdfException("Unrecognised type "+currentType);
            }
        }
    }

    private static String getTypeAsString(int i) {

        String str = "Value Not set";

        switch(i){
            case DynamicVectorRenderer.FILLOPACITY:
                str="FILLOPACITY";
                break;

            case DynamicVectorRenderer.STROKEOPACITY:
                str="STROKEOPACITY";
                break;

            case DynamicVectorRenderer.STROKEDSHAPE:
                str="STROKEDSHAPE";
                break;

            case DynamicVectorRenderer.FILLEDSHAPE:
                str="FILLEDSHAPE";
                break;

            case DynamicVectorRenderer.CUSTOM:
                str="CUSTOM";
                break;

            case DynamicVectorRenderer.IMAGE:
                str="IMAGE";
                break;

            case DynamicVectorRenderer.STRING:
                str="String";
                break;

        }

        return str;
    }

    public void flushAdditionalObjOnPage(){
        //reset and remove all from page

        //reset pointer
        if(endItem!=-1)
            currentItem=endItem;

        endItem=-1;

        objectType.resetToCheckpoint();

        shapeType.resetToCheckpoint();

        pageObjects.resetToCheckpoint();

        areas.resetToCheckpoint();

        clips.resetToCheckpoint();

        textFillType.resetToCheckpoint();

        text_color.resetToCheckpoint();

        fill_color.resetToCheckpoint();

        stroke_color.resetToCheckpoint();

        stroke.resetToCheckpoint();

        if(imageOptions!=null)
            imageOptions.resetToCheckpoint();

        if(TRvalues!=null)
            TRvalues.resetToCheckpoint();

        if(fs!=null)
            fs.resetToCheckpoint();

        if(lw!=null)
            lw.resetToCheckpoint();

        af1.resetToCheckpoint();

        af2.resetToCheckpoint();

        af3.resetToCheckpoint();

        af4.resetToCheckpoint();

        fontBounds.resetToCheckpoint();

        if(opacity!=null)
            opacity.resetToCheckpoint();

        //reset pointers we use to flag color change
        lastFillTextCol=0;
        lastFillCol=0;
        lastStrokeCol=0;

        lastClip=null;
        hasClips=false;

        lastStroke=null;

        lastAf=new double[4];

        fillSet=false;
        strokeSet=false;

    }

    /*save shape colour*/
    public void drawStrokeColor(Paint currentCol) {

        pageObjects.addElement(null);
        objectType.addElement(DynamicVectorRenderer.STROKECOLOR);
        areas.addElement(null);

        //stroke_color.addElement(new Color (currentCol.getRed(),currentCol.getGreen(),currentCol.getBlue()));
        stroke_color.addElement(currentCol);

        x_coord=RenderUtils.checkSize(x_coord,currentItem);
        y_coord=RenderUtils.checkSize(y_coord,currentItem);
        x_coord[currentItem]=0;
        y_coord[currentItem]=0;

        currentItem++;

        strokeSet=false;
        fillSet=false;
        resetTextColors=true;

    }

    /*save custom shape*/
    public void drawCustom(Object value) {

        pageObjects.addElement(value);
        objectType.addElement(DynamicVectorRenderer.CUSTOM);
        areas.addElement(null);

        x_coord=RenderUtils.checkSize(x_coord,currentItem);
        y_coord=RenderUtils.checkSize(y_coord,currentItem);
        x_coord[currentItem]=0;
        y_coord[currentItem]=0;

        currentItem++;

    }

    /*save shape stroke*/
    public void drawTR(int value) {

        if(value!=lastTR){ //only cache if needed

            if(TRvalues==null){
                TRvalues=new Vector_Int(defaultSize);
                TRvalues.setCheckpoint();
            }

            lastTR=value;

            pageObjects.addElement(null);
            objectType.addElement(DynamicVectorRenderer.TR);
            areas.addElement(null);

            this.TRvalues.addElement(value);

            x_coord=RenderUtils.checkSize(x_coord,currentItem);
            y_coord=RenderUtils.checkSize(y_coord,currentItem);
            x_coord[currentItem]=0;
            y_coord[currentItem]=0;


            currentItem++;
        }
    }


    /*save shape stroke*/
    public void drawStroke(Stroke current) {

        pageObjects.addElement(null);
        objectType.addElement(DynamicVectorRenderer.STROKE);
        areas.addElement(null);

        this.stroke.addElement((current));

        x_coord=RenderUtils.checkSize(x_coord,currentItem);
        y_coord=RenderUtils.checkSize(y_coord,currentItem);
        x_coord[currentItem]=0;
        y_coord[currentItem]=0;

        currentItem++;

    }

    /*save clip in array to draw*/
    public void drawClip(GraphicsState currentGraphicsState, Shape defaultClip, boolean canBeCached) {

        boolean resetClip=false;

        Area clip=currentGraphicsState.getClippingShape();

        if(canBeCached && hasClips && lastClip==null&& clip==null){
        
        }else if (!canBeCached || lastClip==null || clip==null){

            resetClip=true;
        }else{

            Rectangle bounds = clip.getBounds();
            Rectangle oldBounds=lastClip.getBounds();

            //see if different size
            if(bounds.x!=oldBounds.x || bounds.y!=oldBounds.y || bounds.width!=oldBounds.width || bounds.height!=oldBounds.height){
                resetClip=true;
            }else{  //if both rectangle and same size skip

                int count = isRectangle(bounds);
                int count2 = isRectangle(oldBounds);

                if(count==6 && count2==6){

                }else if(!clip.equals(lastClip)){ //only do slow test at this point
                    resetClip=true;
                }
            }
        }

        if(resetClip){

            pageObjects.addElement(null);
            objectType.addElement(DynamicVectorRenderer.CLIP);
            areas.addElement(null);

            lastClip=clip;

            if(clip==null){
                clips.addElement(null);
            }else{
                clips.addElement((Area) clip.clone());
            }

            x_coord=RenderUtils.checkSize(x_coord,currentItem);
            y_coord=RenderUtils.checkSize(y_coord,currentItem);
            x_coord[currentItem]=currentGraphicsState.x;
            y_coord[currentItem]=currentGraphicsState.y;

            currentItem++;

            hasClips=true;
        }
    }

    private int isRectangle(Rectangle bounds) {

        int count = 0;
        PathIterator i = bounds.getPathIterator(null);

        while (!i.isDone() && count < 8) { //see if rectangle or complex clip
            i.next();
            count++;
        }

        return count;
    }

    /**
     * store glyph info
     */
    public void drawEmbeddedText(float[][] Trm, int fontSize, PdfGlyph embeddedGlyph,
                                 Object javaGlyph, int type, GraphicsState gs, AffineTransform at, String glyf, PdfFont currentFontData, float glyfWidth) {

        /**
         * set color first
         */
        PdfPaint currentCol;

        int text_fill_type = gs.getTextRenderType();

        //for a fill
        if ((text_fill_type & GraphicsState.FILL) == GraphicsState.FILL) {
            currentCol= gs.getNonstrokeColor();

            if(currentCol.isPattern()){
                drawColor(currentCol,GraphicsState.FILL);
                resetTextColors=true;
            }else{

                int newCol=(currentCol).getRGB();
                if((resetTextColors)||((lastFillTextCol!=newCol))){
                    lastFillTextCol=newCol;
                    drawColor(currentCol,GraphicsState.FILL);
                    resetTextColors=false;
                }
            }
        }

        //and/or do a stroke
        if ((text_fill_type & GraphicsState.STROKE) == GraphicsState.STROKE){
            currentCol= gs.getStrokeColor();

            if(currentCol.isPattern()){
                drawColor(currentCol,GraphicsState.STROKE);
                resetTextColors=true;
            }else{
                int newCol=currentCol.getRGB();
                if((resetTextColors)||(lastStrokeCol!=newCol)){
                    resetTextColors=false;
                    lastStrokeCol=newCol;
                    drawColor(currentCol,GraphicsState.STROKE);
                }
            }
        }

        //allow for lines as shadows
        setLineWidth((int)gs.getLineWidth());

        drawFontSize(fontSize);

        if(javaGlyph !=null){


            if(Trm!=null){
                double[] nextAf=new double[]{Trm[0][0],Trm[0][1],Trm[1][0],Trm[1][1],Trm[2][0],Trm[2][1]};

                if((lastAf[0]==nextAf[0])&&(lastAf[1]==nextAf[1])&&
                        (lastAf[2]==nextAf[2])&&(lastAf[3]==nextAf[3])){
                }else{

                    this.drawAffine(nextAf);
                    lastAf[0]=nextAf[0];
                    lastAf[1]=nextAf[1];
                    lastAf[2]=nextAf[2];
                    lastAf[3]=nextAf[3];
                }
            }

            if(!(javaGlyph instanceof Area))
                type=-type;

        }else{

            double[] nextAf=new double[6];
            at.getMatrix(nextAf);
            if((lastAf[0]==nextAf[0])&&(lastAf[1]==nextAf[1])&&
                    (lastAf[2]==nextAf[2])&&(lastAf[3]==nextAf[3])){
            }else{
                this.drawAffine(nextAf);
                lastAf[0]=nextAf[0];
                lastAf[1]=nextAf[1];
                lastAf[2]=nextAf[2];
                lastAf[3]=nextAf[3];
            }
        }

        if(embeddedGlyph==null)
            pageObjects.addElement(javaGlyph);
        else
            pageObjects.addElement(embeddedGlyph);

        objectType.addElement(type);

        if(type<0){
            areas.addElement(null);
        }else{
            if(javaGlyph!=null){

                areas.addElement(new Rectangle((int)Trm[2][0],(int)Trm[2][1],fontSize,fontSize));
                checkWidth(new Rectangle((int)Trm[2][0],(int)Trm[2][1],fontSize,fontSize));

            }else{
                /**now text*/
                int realSize=fontSize;
                if(realSize<0)
                    realSize=-realSize;
                Rectangle area=new Rectangle((int)Trm[2][0],(int)Trm[2][1],realSize,realSize);

                areas.addElement(area);
                checkWidth(area);
            }
        }

        x_coord=RenderUtils.checkSize(x_coord,currentItem);
        y_coord=RenderUtils.checkSize(y_coord,currentItem);
        x_coord[currentItem]=Trm[2][0];
        y_coord[currentItem]=Trm[2][1];


        currentItem++;

    }

    /**
     * store fontBounds info
     */
    public void drawFontBounds(Rectangle newfontBB) {

        pageObjects.addElement(null);
        objectType.addElement(DynamicVectorRenderer.fontBB);
        areas.addElement(null);

        fontBounds.addElement(newfontBB);

        x_coord=RenderUtils.checkSize(x_coord,currentItem);
        y_coord=RenderUtils.checkSize(y_coord,currentItem);
        x_coord[currentItem]=0;
        y_coord[currentItem]=0;

        currentItem++;

    }

    /**
     * store af info
     */
    public void drawAffine(double[] afValues) {

        pageObjects.addElement(null);
        objectType.addElement(DynamicVectorRenderer.AF);
        areas.addElement(null);

        af1.addElement(afValues[0]);
        af2.addElement(afValues[1]);
        af3.addElement(afValues[2]);
        af4.addElement(afValues[3]);

        x_coord=RenderUtils.checkSize(x_coord,currentItem);
        y_coord=RenderUtils.checkSize(y_coord,currentItem);
        x_coord[currentItem]=(float)afValues[4];
        y_coord[currentItem]=(float)afValues[5];

        currentItem++;

    }

    /**
     * store af info
     */
    public void drawFontSize(int fontSize) {

        int realSize=fontSize;
        if(realSize<0)
            realSize=-realSize;

        if(realSize!=lastFS){
            pageObjects.addElement(null);
            objectType.addElement(DynamicVectorRenderer.FONTSIZE);
            areas.addElement(null);

            if(fs==null){
                fs=new Vector_Int(defaultSize);
                fs.setCheckpoint();
            }

            fs.addElement(fontSize);

            x_coord=RenderUtils.checkSize(x_coord,currentItem);
            y_coord=RenderUtils.checkSize(y_coord,currentItem);
            x_coord[currentItem]=0;
            y_coord[currentItem]=0;

            currentItem++;

            lastFS=realSize;

        }
    }

    /**
     * store line width info
     */
    public void setLineWidth(int lineWidth) {

        if(lineWidth!=lastLW ){

            areas.addElement(null);
            pageObjects.addElement(null);
            objectType.addElement(DynamicVectorRenderer.LINEWIDTH);

            if(lw==null){
                lw=new Vector_Int(defaultSize);
                lw.setCheckpoint();
            }

            lw.addElement(lineWidth);

            x_coord=RenderUtils.checkSize(x_coord,currentItem);
            y_coord=RenderUtils.checkSize(y_coord,currentItem);
            x_coord[currentItem]=0;
            y_coord[currentItem]=0;

            currentItem++;

            lastLW=lineWidth;

        }
    }

    /**
     * rebuild serialised version
     *
     * NOT PART OF API and subject to change (DO NOT USE)
     * @param fonts
     *
     */
    public SwingDisplay(byte[] stream, Map fonts){

        // we use Cannoo to turn our stream back into a DynamicVectorRenderer
        try{
            this.fonts = fonts;

            ByteArrayInputStream bis=new ByteArrayInputStream(stream);

            //read version and throw error is not correct version
            int version=bis.read();
            if(version!=1)
                throw new PdfException("Unknown version in serialised object "+version);

            int isHires=bis.read(); //0=no,1=yes
            useHiResImageForDisplay = isHires == 1;

            pageNumber=bis.read();

            x_coord=(float[]) RenderUtils.restoreFromStream(bis);
            y_coord=(float[]) RenderUtils.restoreFromStream(bis);

            //read in arrays - opposite of serializeToByteArray();
            //we may need to throw an exception to allow for errors

            text_color = (Vector_Object) RenderUtils.restoreFromStream(bis);

            textFillType = (Vector_Int) RenderUtils.restoreFromStream(bis);

            stroke_color = new Vector_Object();
            stroke_color.restoreFromStream(bis);

            fill_color = new Vector_Object();
            fill_color.restoreFromStream(bis);

            stroke = new Vector_Object();
            stroke.restoreFromStream(bis);

            pageObjects = new Vector_Object();
            pageObjects.restoreFromStream(bis);

            javaObjects=(Vector_Object) RenderUtils.restoreFromStream(bis);

            shapeType = (Vector_Int) RenderUtils.restoreFromStream(bis);

            af1 = (Vector_Double) RenderUtils.restoreFromStream(bis);

            af2 = (Vector_Double) RenderUtils.restoreFromStream(bis);

            af3 = (Vector_Double) RenderUtils.restoreFromStream(bis);

            af4 = (Vector_Double) RenderUtils.restoreFromStream(bis);

            fontBounds= new Vector_Rectangle();
            fontBounds.restoreFromStream(bis);

            clips = new Vector_Shape();
            clips.restoreFromStream(bis);

            objectType = (Vector_Int) RenderUtils.restoreFromStream(bis);

            opacity=(Vector_Float) RenderUtils.restoreFromStream(bis);

            imageOptions = (Vector_Int) RenderUtils.restoreFromStream(bis);

            TRvalues = (Vector_Int) RenderUtils.restoreFromStream(bis);

            fs = (Vector_Int) RenderUtils.restoreFromStream(bis);
            lw = (Vector_Int) RenderUtils.restoreFromStream(bis);

            int fontCount= (Integer) RenderUtils.restoreFromStream(bis);
            for(int ii=0;ii<fontCount;ii++){

                Object key=RenderUtils.restoreFromStream(bis);
                Object glyphs=RenderUtils.restoreFromStream(bis);
                fonts.put(key,glyphs);
            }

            int alteredFontCount= (Integer) RenderUtils.restoreFromStream(bis);
            for(int ii=0;ii<alteredFontCount;ii++){

                Object key=RenderUtils.restoreFromStream(bis);

                PdfJavaGlyphs updatedFont=(PdfJavaGlyphs) fonts.get(key);

                updatedFont.setDisplayValues((Map) RenderUtils.restoreFromStream(bis));
                updatedFont.setCharGlyphs((Map) RenderUtils.restoreFromStream(bis));
                updatedFont.setEmbeddedEncs((Map) RenderUtils.restoreFromStream(bis));

            }

            bis.close();

        }catch(Exception e){
            //tell user and log
            if(LogWriter.isOutput())
                LogWriter.writeLog("Exception: "+e.getMessage());
        }

        //used in loop to draw so needs to be set
        currentItem=pageObjects.get().length;

    }

    /**stop screen bein cleared on repaint - used by Canoo code
     *
     * NOT PART OF API and subject to change (DO NOT USE)
     **/
    public void stopClearOnNextRepaint(boolean flag) {
        noRepaint=flag;
    }

    /**
     * turn object into byte[] so we can move across
     * this way should be much faster than the stadard Java serialise.
     *
     * NOT PART OF API and subject to change (DO NOT USE)
     *
     * @throws IOException
     */
    public byte[] serializeToByteArray(Set fontsAlreadyOnClient) throws IOException{

        ByteArrayOutputStream bos=new ByteArrayOutputStream();

        //add a version so we can flag later changes
        bos.write(1);

        //flag hires
        //0=no,1=yes
        if(useHiResImageForDisplay)
            bos.write(1);
        else
            bos.write(0);

        //save page
        bos.write(pageNumber);

        //the WeakHashMaps are local caches - we ignore

        //we do not copy across hires images

        //we need to copy these in order

        //if we write a count for each we can read the count back and know how many objects
        //to read back

        //write these values first
        //pageNumber;
        //objectStoreRef;
        //isPrinting;

        text_color.trim();
        stroke_color.trim();
        fill_color.trim();
        stroke.trim();
        pageObjects.trim();
        javaObjects.trim();
        stroke.trim();
        pageObjects.trim();
        javaObjects.trim();
        shapeType.trim();
        af1.trim();
        af2.trim();
        af3.trim();
        af4.trim();

        fontBounds.trim();

        clips.trim();
        objectType.trim();
        if(opacity!=null)
            opacity.trim();
        if(imageOptions!=null)
            imageOptions.trim();
        if(TRvalues!=null)
            TRvalues.trim();

        if(fs!=null)
            fs.trim();

        if(lw!=null)
            lw.trim();

        RenderUtils.writeToStream(bos,x_coord,"x_coord");
        RenderUtils.writeToStream(bos,y_coord,"y_coord");
        RenderUtils.writeToStream(bos,text_color,"text_color");
        RenderUtils.writeToStream(bos,textFillType,"textFillType");
        stroke_color.writeToStream(bos);
        fill_color.writeToStream(bos);

        stroke.writeToStream(bos);

        pageObjects.writeToStream(bos);

        RenderUtils.writeToStream(bos,javaObjects,"javaObjects");
        RenderUtils.writeToStream(bos,shapeType,"shapeType");

        RenderUtils.writeToStream(bos,af1,"af1");
        RenderUtils.writeToStream(bos,af2,"af2");
        RenderUtils.writeToStream(bos,af3,"af3");
        RenderUtils.writeToStream(bos,af4,"af4");

        fontBounds.writeToStream(bos);

        clips.writeToStream(bos);

        RenderUtils.writeToStream(bos,objectType,"objectType");
        RenderUtils.writeToStream(bos,opacity,"opacity");
        RenderUtils.writeToStream(bos,imageOptions,"imageOptions");
        RenderUtils.writeToStream(bos,TRvalues,"TRvalues");

        RenderUtils.writeToStream(bos,fs,"fs");
        RenderUtils.writeToStream(bos,lw,"lw");

        int fontCount=0,updateCount=0;
        Map fontsAlreadySent=new HashMap(10);
        Map newFontsToSend=new HashMap(10);

        for (Object fontUsed : fontsUsed.keySet()) {
            if (!fontsAlreadyOnClient.contains(fontUsed)) {
                fontCount++;
                newFontsToSend.put(fontUsed, "x");
            } else {
                updateCount++;
                fontsAlreadySent.put(fontUsed, "x");
            }
        }

        /**
         * new fonts
         */
        RenderUtils.writeToStream(bos, fontCount,"new Integer(fontCount)");

        Iterator keys=newFontsToSend.keySet().iterator();
        while(keys.hasNext()){
            Object key=keys.next();

            RenderUtils.writeToStream(bos,key,"key");
            RenderUtils.writeToStream(bos,fonts.get(key),"font");

            fontsAlreadyOnClient.add(key);
        }

        /**
         * new data on existing fonts
         */
        /**
         * new fonts
         */
        RenderUtils.writeToStream(bos, updateCount,"new Integer(existingfontCount)");

        keys=fontsAlreadySent.keySet().iterator();
        while(keys.hasNext()){
            Object key=keys.next();

            RenderUtils.writeToStream(bos,key,"key");
            PdfJavaGlyphs aa = (PdfJavaGlyphs) fonts.get(key);
            RenderUtils.writeToStream(bos,aa.getDisplayValues(),"display");
            RenderUtils.writeToStream(bos,aa.getCharGlyphs() ,"char");
            RenderUtils.writeToStream(bos,aa.getEmbeddedEncs() ,"emb");

        }

        bos.close();

        fontsUsed.clear();

        return bos.toByteArray();
    }

    public void setneedsVerticalInvert(boolean b) {
        needsVerticalInvert=b;
    }

    public void setneedsHorizontalInvert(boolean b) {
        needsHorizontalInvert=b;
    }

    /**
     * for font if we are generatign glyph on first render
     */
    public void checkFontSaved(Object glyph, String name, PdfFont currentFontData) {

        //save glyph at start
        /**now text*/
        pageObjects.addElement(glyph);
        objectType.addElement(DynamicVectorRenderer.MARKER);
        areas.addElement(null);
        currentItem++;

        if(fontsUsed.get(name)==null || currentFontData.isFontSubsetted()){
            fonts.put(name,currentFontData.getGlyphData());
            fontsUsed.put(name,"x");
        }
    }

    public Rectangle getArea(int i){
        return areas.elementAt(i);
    }

    /**
     * return number of image in display queue
     * or -1 if none
     * @return
     */
    public int isInsideImage(int x,int y){
        int outLine=-1;

        Rectangle[] areas=this.areas.get();
        Rectangle possArea = null;
        int count=areas.length;

        int[] types=objectType.get();
        for(int i=0;i<count;i++){
            if(areas[i]!=null){

                if(RenderUtils.rectangleContains(areas[i],x, y,i) && types[i]==DynamicVectorRenderer.IMAGE){
                    //Check for smallest image that contains this point
                    if(possArea!=null){
                        int area1 = possArea.height * possArea.width;
                        int area2 = areas[i].height * areas[i].width;
                        if(area2<area1)
                            possArea = areas[i];
                        outLine=i;
                    }else{
                        possArea = areas[i];
                        outLine=i;
                    }
                }
            }
        }
        return outLine;
    }

    public void saveImage(int id, String des, String type) {

        String name = (String)imageIDtoName.get(id);
        BufferedImage image = null;
        if(useHiResImageForDisplay){
            image=objectStoreRef.loadStoredImage(name);

            //if not stored, try in memory
            if(image==null)
                image=(BufferedImage)pageObjects.elementAt(id);
        }else
            image=(BufferedImage)pageObjects.elementAt(id);

        if(image!=null){

            if(!optimisedTurnCode)
                image = RenderUtils.invertImage(null, image);

            if(image.getType()==BufferedImage.TYPE_CUSTOM || (type.equals("jpg") && image.getType()==BufferedImage.TYPE_INT_ARGB)){
                image=ColorSpaceConvertor.convertToRGB(image);
                if(image.getType()==BufferedImage.TYPE_CUSTOM && PdfDecoder.showErrorMessages)
                    JOptionPane.showMessageDialog(null, "This is a custom Image, Java's standard libraries may not be able to save the image as a jpg correctly.\n" +
                            "Enabling JAI will ensure correct output. \n\nFor information on how to do this please go to http://www.jpedal.org/flags.php");
            }

            if(needsHorizontalInvert){
                image = RenderUtils.invertImageBeforeSave(image, true);
            }

            if(needsVerticalInvert){
                image = RenderUtils.invertImageBeforeSave(image, false);
            }

            if(JAIHelper.isJAIused() && type.toLowerCase().startsWith("tif")){
                javax.media.jai.JAI.create("filestore", image, des, type);
            }else if(type.toLowerCase().startsWith("tif")){
                    if(PdfDecoder.showErrorMessages)
                        JOptionPane.showMessageDialog(null,"Please setup JAI library for Tiffs");

                    if(LogWriter.isOutput())
                    	LogWriter.writeLog("Please setup JAI library for Tiffs");
                }else{
                    try {
                    	BufferedOutputStream bos= new BufferedOutputStream(new FileOutputStream(new File(des)));
            			ImageIO.write(image, type, bos);
            			bos.flush();
            			bos.close();
                    } catch (IOException e) {
                        //tell user and log
                        if(LogWriter.isOutput())
                            LogWriter.writeLog("Exception: "+e.getMessage());
                    }
                }
        }
    }

    /**
     * operations to do once page done
     */
    public void flagDecodingFinished() {

        highlightsNeedToBeGenerated=true;
    }

    private void generateHighlights(Graphics2D g2, int count, int[] objectTypes, Object[] pageObjects, float a, float b, float c, float d, double[] afValues1, double[] afValues2, double[] afValues3, double[] afValues4, int[] fsValues, Rectangle[] fontBounds) {

        //flag done for page
        highlightsNeedToBeGenerated=false;

        //array for text highlights
        int[] highlightIDs=new int[count];

        int fsCount=-1,fontBBCount=0;//note af is 1 behind!

        float x,y;

        Rectangle currentHighlight = null;

        float[] top=new float[count];
        float[] bottom=new float[count];
        float[] left=new float[count];
        float[] right=new float[count];
        boolean[] isFontEmbedded =new boolean[count];
        int[] fontSizes =new int[count];
        float[] w=new float[count];

        textHighlightsX=new int[count];
        int[] textHighlightsY=new int[count];
        textHighlightsWidth=new int[count];
        textHighlightsHeight=new int[count];

        /**
         * get highlights
         */
        //fontBoundsX=0,
        int fontBoundsY=0,fontBoundsH=1000,fontBoundsW=1000,fontSize=1,realSize=1;

        double matrix[]=new double[6];
        g2.getTransform().getMatrix(matrix);

        //see if rotated
        int pageRotation=0;
        if(matrix[1]<0 && matrix[2]<0)
            pageRotation=270;

        for(int i=0;i<count;i++){

            type=objectTypes[i];


            if(type>0){

                x=x_coord[i];
                y=y_coord[i];

                //put in displacement if text moved up by inversion
                if(realSize<0)
                    x=x+realSize;

                Object currentObject=pageObjects[i];

                if(type==DynamicVectorRenderer.fontBB){

                    currentHighlight=fontBounds[fontBBCount];

                    fontBoundsH=currentHighlight.height;
                    //fontBoundsX=currentHighlight.x;
                    fontBoundsY=currentHighlight.y;
                    fontBoundsW=currentHighlight.width;
                    fontBBCount++;

                }else if(type==DynamicVectorRenderer.FONTSIZE){
                    fsCount++;
                    realSize=fsValues[fsCount];
                    if(realSize<0)
                        fontSize=-realSize;
                    else
                        fontSize=realSize;

                }else if(type==DynamicVectorRenderer.TRUETYPE || type==DynamicVectorRenderer.TYPE1C || type==DynamicVectorRenderer.TEXT){

                    //this works in 2 different unit spaces for embedded and non-embedded hence flags
                    float scaling=1f;

                    if(type==DynamicVectorRenderer.TRUETYPE || type==DynamicVectorRenderer.TYPE1C){
                        PdfGlyph raw=((PdfGlyph)currentObject);

                        scaling=fontSize/1000f;

                        textHighlightsX[i]=raw.getFontBB(PdfGlyph.FontBB_X);
                        textHighlightsY[i]=fontBoundsY;
                        textHighlightsWidth[i]=raw.getFontBB(PdfGlyph.FontBB_WIDTH);
                        textHighlightsHeight[i]=fontBoundsH;

                        isFontEmbedded[i]=true;

                        if(pageRotation==90){
                            bottom[i]=-((textHighlightsY[i]*scaling))+x;
                            left[i]=(textHighlightsX[i]*scaling)+y;
                        }else if(pageRotation==270){
                            bottom[i]=((textHighlightsY[i]*scaling))+x;
                            left[i]=-((textHighlightsX[i]*scaling)+y);
                        }else{ //0 and 180 work the same way
                            bottom[i]=((textHighlightsY[i]*scaling))+y;
                            left[i]=((textHighlightsX[i]*scaling))+x;
                        }

                        top[i]=bottom[i]+(textHighlightsHeight[i]*scaling);
                        right[i]=left[i]+(textHighlightsWidth[i]*scaling);

                        w[i]=10; //any non zero number
                        fontSizes[i]=fontSize;

                    }else{
                        scaling=1f;

                        float scale=1000f/fontSize;
                        textHighlightsX[i]=(int)x;
                        textHighlightsY[i]=(int)(y+(fontBoundsY/scale));
                        textHighlightsWidth[i]=(int)((fontBoundsW)/scale);
                        textHighlightsHeight[i]=(int)((fontBoundsH-fontBoundsY)/scale);


                        if(pageRotation==90){
                            bottom[i]=-textHighlightsY[i];
                            left[i]=textHighlightsX[i];
                        }else if(pageRotation==270){
                            bottom[i]=(textHighlightsY[i]);
                            left[i]=-textHighlightsX[i];
                        }else{ //0 and 180 work the same way
                            bottom[i]=textHighlightsY[i];
                            left[i]=textHighlightsX[i];
                        }

                        top[i]=bottom[i]+textHighlightsHeight[i];
                        right[i]=left[i]+textHighlightsWidth[i];

                        w[i]=((Area)currentObject).getBounds().width;

                        fontSizes[i]=fontSize;

                    }
                    highlightIDs[i]=i;

                }
            }
        }

        //sort highlights
        //highlightIDs=Sorts.quicksort(left,bottom,highlightIDs);

        int zz=-31;
        //scan each and adjust so it touches next
        //if(1==2)
        for(int aa=0;aa<count-1;aa++){

            int ptr=highlightIDs[aa];

            {//if(textHighlights[ptr]!=null){

                if(ptr==zz)
                    System.out.println("*"+ptr+" = "+" left="+left[ptr]+
                            " bottom="+bottom[ptr]+" right="+right[ptr]+" top="+top[ptr]);

                int gap=0;
                for(int next=aa+1;next<count;next++){
                    int nextPtr=highlightIDs[next];

                    //skip empty
                    if(isFontEmbedded[nextPtr]!=isFontEmbedded[ptr] || w[nextPtr]<1)
                        continue;

                    if(ptr==zz)
                        System.out.println("compare with="+nextPtr+" left="+left[nextPtr]+" right="+right[nextPtr]+ ' '+(left[nextPtr]>left[ptr] && left[nextPtr]<right[ptr]));

                    //find glyph on right
                    if((left[nextPtr]>left[ptr] && left[nextPtr]<right[ptr])||(left[nextPtr]>((left[ptr]+right[ptr])/2) && right[ptr]<right[nextPtr])){

                        int currentW=textHighlightsWidth[ptr];
                        //int currentH=textHighlightsHeight[ptr];
                        int currentX=textHighlightsX[ptr];
                        //int currentY=textHighlightsY[ptr];

                        if(isFontEmbedded[nextPtr]){
                            float diff=left[nextPtr]-right[ptr];

                            if(diff>0)
                                diff=diff+.5f;
                            else
                                diff=diff+.5f;

                            gap=(int)(((diff*1000f/fontSizes[ptr])));

                            if(textHighlightsX[nextPtr]>0)
                                gap=gap+textHighlightsX[nextPtr];

                        }else
                            gap=(int)(left[nextPtr]-right[ptr]);

                        if(ptr==zz)
                            System.out.println((left[nextPtr]-right[ptr])+" gap="+gap+ ' '+(((left[nextPtr]-right[ptr])*1000f/fontSizes[ptr]))+" currentX="+currentX+" scaling="+scaling+ ' '+fontBoundsW);

                        boolean isCorrectLocation=(gap>0 ||(gap<0 && left[ptr]<left[nextPtr] && right[ptr]>left[nextPtr] && right[ptr]<right[nextPtr] && left[ptr]<right[ptr] &&
                                ( (-gap< fontSizes[ptr] && !isFontEmbedded[ptr])|| (-gap< fontBoundsW && isFontEmbedded[ptr]))));
                        if(bottom[ptr]<top[nextPtr] && bottom[nextPtr]<top[ptr] && (gap>0 || isCorrectLocation)){
                            if(isCorrectLocation &&   ((!isFontEmbedded[ptr] && gap<fontSizes[ptr] && currentW+gap<fontSizes[ptr]) || (isFontEmbedded[ptr] && gap<fontBoundsW))){



                                if(ptr==zz)
                                    System.out.println(nextPtr+" = "+" left="+left[nextPtr]+
                                            " bottom="+bottom[nextPtr]+" right="+right[nextPtr]+" top="+top[nextPtr]);

                                if(isFontEmbedded[ptr]){

                                    if(gap>0){
                                        textHighlightsWidth[ptr]=currentW+gap;
                                        //textHighlightsX[nextPtr]=textHighlightsX[nextPtr]-half-1;
                                    }else{
                                        textHighlightsWidth[ptr]=currentW-gap;
                                    }

                                }else if(gap>0)
                                    textHighlightsWidth[ptr]=gap;
                                else
                                    textHighlightsWidth[ptr]=currentW+gap;

                                if(ptr==zz){
                                    System.out.println("new="+textHighlightsWidth[ptr]);
                                }

                                next=count;
                            }else if(gap>fontBoundsW){ //off line so exit
                                //next=count;
                            }
                        }
                    }
                }
            }
        }
    }



    public void setPrintPage(int currentPrintPage) {
        pageNumber = currentPrintPage;
    }

    /**
     * return number of image in display queue
     * or -1 if none
     * @return
     */
    public int getObjectUnderneath(int x,int y){
        int typeFound=-1;
        Rectangle[] areas=this.areas.get();
        //Rectangle possArea = null;
        int count=areas.length;

        int[] types=objectType.get();
        boolean nothing = true;
        for(int i=count-1;i>-1;i--){
            if(areas[i]!=null){
                if(RenderUtils.rectangleContains(areas[i],x, y,i)){
                    if(types[i] != DynamicVectorRenderer.SHAPE && types[i] != DynamicVectorRenderer.CLIP){
                        nothing = false;
                        typeFound = types[i];
                        i=-1;
                    }
                }
            }
        }

        if(nothing)
            return -1;


        return typeFound;
    }

    public void flagImageDeleted(int i) {
        objectType.setElementAt(DynamicVectorRenderer.DELETED_IMAGE,i);
    }

    public void setOCR(boolean isOCR) {
        this.hasOCR=isOCR;
    }

}

