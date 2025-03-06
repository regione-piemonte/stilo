/**
 * ===========================================
 * Java Pdf Extraction Decoding Access Library
 * ===========================================
 *
 * Project Info:  http://www.jpedal.org
 *
 * (C) Copyright 2012, IDRsolutions and Contributors.
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

  * OutputDisplay.java
  * ---------------
  * (C) Copyright 2007, by IDRsolutions and Contributors.
  *
  *
  * --------------------------
 */
package it.eng.hybrid.module.jpedal.render.output;

import it.eng.hybrid.module.jpedal.color.ColorSpaces;
import it.eng.hybrid.module.jpedal.color.PdfPaint;
import it.eng.hybrid.module.jpedal.fonts.HTMLFontUtils;
import it.eng.hybrid.module.jpedal.fonts.PdfFont;
import it.eng.hybrid.module.jpedal.fonts.StandardFonts;
import it.eng.hybrid.module.jpedal.fonts.glyph.PdfGlyph;
import it.eng.hybrid.module.jpedal.io.ObjectStore;
import it.eng.hybrid.module.jpedal.objects.GraphicsState;
import it.eng.hybrid.module.jpedal.objects.PdfPageData;
import it.eng.hybrid.module.jpedal.objects.TextState;
import it.eng.hybrid.module.jpedal.parser.Cmd;
import it.eng.hybrid.module.jpedal.render.BaseDisplay;
import it.eng.hybrid.module.jpedal.render.DynamicVectorRenderer;
import it.eng.hybrid.module.jpedal.ui.JPedalApplication;
import it.eng.hybrid.module.jpedal.util.Matrix;
import it.eng.hybrid.module.jpedal.util.StringUtils;
import it.eng.hybrid.module.jpedal.util.repositories.Vector_Rectangle;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Area;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

public class OutputDisplay extends BaseDisplay{

	public final static Logger logger = Logger.getLogger(OutputDisplay.class);
	
    public static boolean convertT1Fonts = true;

    protected String packageName = "",javaFxFileName="";

    protected HashMap<String, Object[]> fontsToConvert = new HashMap<String, Object[]>();

    protected HashMap<String,HashMap<String,Integer>> widths = new HashMap<String, HashMap<String, Integer>>();

    protected static boolean enableOTFConversion = false;

    //flag to show embedded fonts present so need to use full name
    protected boolean hasEmbeddedFonts=false;

    /**track the embedded fonts we might embed*/
    protected Map embeddedFonts=new HashMap();

    /**track by full name as well*/
    protected Map baseFontNames=new HashMap();

    /**show if any embedded fonts in Map*/
    protected boolean hasEmbededFonts=false;

    protected int offset=0;

    protected int[] pageoffsets;

    /**track images written out to reduce IO if already drawn*/
    private Map imagesAlreadyWritten=new HashMap();


    //offset in single page mode
    protected double newTotalHeight = 0;
    
    //page range
    protected int startPage, endPage;

    protected int pageGap=50;
    
    String lastGlyf="";

    protected boolean requiresTransform = false;
    protected boolean requiresTransformGlobal = false;
    protected boolean requiresTextGlobal = false;

    final public static int TEXT_AS_TEXT =-1;

    final public static int TEXT_AS_SHAPE =1;
    final public static int INVISIBLE_TEXT_ON_IMAGE=2;
    final public static int VISIBLE_TEXT_ON_IMAGE=3;

    protected String clip=null;

    //provides common static functions
    static protected OutputHelper Helper=null;

    FontMapper fontMapper=null;
    String lastFontUsed="";
    
    private Map usedFontIDs = new HashMap();

    protected boolean includeClip=false;

    protected int textMode =-1;

    protected Map embeddedFontsByFontID=new HashMap();

    /**track if JS for glyf already inserted*/
    private Map glyfsRasterized=new HashMap();

    final public static int MaxNumberOfDecimalPlaces=0;
    final public static int FontMode=1;
    final public static int PercentageScaling=2;
    //final public static int SpacingPercentage=3;
    final public static int IncludeJSFontResizingCode=4;

    final public static int ExcludeMetadata=6;
    final public static int EmbedImageAsBase64Stream =7;
    final public static int AddNavBar =8;
    final public static int SingleFileOutput =9;
    final public static int UseCharSpacing =10;
    final public static int UseWordSpacing =11;
    final public static int UseFontResizing =12;
    final public static int HasJavascript =13;
    final public static int ConvertSpacesTonbsp =14;
    final public static int EncloseContentInDiv =15;
    final public static int IncludeClip =16;
    final public static int UseImagesOnNavBar =17;
    final public static int TextMode =18;
    final public static int CustomSinglePageHeader = 19;
    final public static int CustomSingleFileName = 20;
    final public static int CustomIO = 21;
    final public static int StartOfDecode = 22;
    final public static int EndOfDecode = 23;
    final public static int EmulateEvenOdd = 24;
    final public static int RequireBrowserCheck = 25;
    final public static int HTMLImageMode = 26;    

    

    //hold a default value set via JVM flag for font mapping
    protected static int defaultMode= FontMapper.DEFAULT_ON_UNMAPPED;

    protected int fontMode=FontMapper.DEFAULT_ON_UNMAPPED;

    //only output to 1 page
    protected boolean isSingleFileOutput =false;
    
    //visible or invisible text on image
    protected boolean htmlImageMode = false;

    //give user option to embed image inside HTML5
    protected boolean embedImageAsBase64=false;

    private boolean groupGlyphsInTJ=true;

    //allow us to position every glyf on its own and not try to merge into strings*/
    protected boolean writeEveryGlyf=false;

    //control whether CSS inlined in HTML file or in own css file
    public boolean inlineCSS=true;

    /** debug flags */
    static final public boolean debugForms = false;
    final private static boolean DISABLE_IMAGES = false;
    final private static boolean DISABLE_SHAPE = false;
    final private static boolean DISABLE_TEXT = false;
    protected final static boolean DEBUG_TEXT_AREA = false;
    protected final static boolean DEBUG_DRAW_PAGE_BORDER = false;

    public final static int TOFILE = 0;
    public final static int TOP_SECTION = 1;
    public final static int SCRIPT = 2;
    public final static int FORM = 3;
    public final static int CSS = 4;
    public final static int IMAGE = 5;
    public final static int TEXT = 6;
    public final static int KEEP_GLYFS_SEPARATE = 7;
    public final static int SET_ENCODING_USED = 8;
    public final static int JSIMAGESPECIAL = 9;
    public final static int SAVE_EMBEDDED_FONT = 10;
    public final static int PAGEDATA = 11;
    public final static int IMAGE_CONTROLLER = 12;
    public final static int FXMLPDFSTAGE = 13;
    public final static int FONT_AS_SHAPE = 14;
    public final static int FXMLTEXT = 15;
    public final static int FORMJS = 16;
    public final static int FORMJS_ONLOAD = 17;
    

    protected OutputImageController imageController=null;

    protected StringBuilder script = new StringBuilder(10000);
    protected ArrayList<String> fxScript = new ArrayList<String>();
    protected ArrayList<String> fxmlText = new ArrayList<String>();

    protected StringBuilder fonts_as_shapes = new StringBuilder(10000);
    protected StringBuilder formJS = new StringBuilder(10000);
    protected StringBuilder formJSOnLoad = new StringBuilder(10000);
    protected StringBuilder form = new StringBuilder(10000);
    protected StringBuilder testDivs = new StringBuilder(10000);
    protected StringBuilder images = new StringBuilder(10000);
    protected StringBuilder css = new StringBuilder(10000);
    protected StringBuilder topSection = new StringBuilder(10000);
    protected StringBuilder fxmlPDFStage = new StringBuilder(10000);

    /**allow user to control scaling of images*/
    protected boolean userControlledImageScaling=false;
    protected boolean emulateEvenOdd=false;

    /**current text element number if using Divs. Used as link to CSS*/
    protected int textID=1;
    
    protected int shadeId = 0;

    /**number of decimal places on numbers*/
    protected int dpCount=0;

    public String rootDir=null, fileName=null;

    protected int dx;
    protected int dy;
    protected int dr;

    protected boolean excludeMetadata=false;

    private boolean convertSpacesTonbsp=false;

    /**include a user nav tollbar in output*/
    protected boolean addNavBar=false,useImagesOnNavBar=false;

    /** Controls blocks of text so they can be joined up*/
    protected TextBlock currentTextBlock;
    protected TextBlock previousTextBlock;

    protected TextPosition currentTextPosition;

    protected Rectangle2D cropBox;
    protected Point2D midPoint;

    /** Used to reduce canvas color javascript calls between text and shapes*/
    protected int currentColor = 0;

    /**control for encodings Java/CSS*/
    protected String[] encodingType=new String[]{"UTF-8","utf-8"};
    protected static final int JAVA_TYPE=0;
    protected static final int OUTPUT_TYPE =1;
    public static final int FORM_TAG = 0;

    protected float scaling = 1.0f; //Scale to large and images may be lost due to lack of memory.

    //amount of font change needed
    protected int fontChangeNeeded=-1;

    //protected float spacingNeeded=1.0f; //amount of space needed

    float w;
    protected float h; //override int super.w and super.h for better accuracy.  @Shit

    protected String[] tag={"<form>"};

    //flag to say if JS has been added to allow images to work for checkboxes and radio buttons.
    protected boolean jsImagesAdded = false;

    protected String pageNumberAsString=null;
    protected PdfPageData pageData;
    private int currentTokenNumber=-1,lastTokenNumber=496; //496 is impossible value
    protected boolean includeJSFontResizingCode =true;

    protected String imageName,id;
    protected float iw,ih;
    protected double[] coords;    
    
    //Change header name in single page output
    protected String customSinglePageHeaderName = System.getProperty("org.jpedal.pdf2html.customSinglePageHeader");
  
    //Change file name in single page output
    protected String customSingleFileOutputName = System.getProperty("org.jpedal.pdf2html.customSingleFileName");
    
    
    //used to eliminate duplicate glyfs
    protected float[][] lastTrm;

    /**handles IO so user can override*/
    protected CustomIO customIO=new DefaultIO();

    protected String imageArray=null;
    
    protected int[] currentPatternedShape;
    protected String currentPatternedShapeID;
    protected String currentPatternedShapeImageSetting;
    protected String currentPatternedShapeName;

    public OutputDisplay(int pageNumber, Point2D midPoint, Rectangle2D cropBox, boolean addBackground, int defaultSize, ObjectStore newObjectRef) {

        super();

        //setup FontMode with any value passed in via JVM or default
        fontMode=defaultMode;

        type = DynamicVectorRenderer.CREATE_HTML;

        this.pageNumber = pageNumber;
        this.objectStoreRef = newObjectRef;
        this.addBackground = addBackground;
        this.cropBox = cropBox;
        this.midPoint = midPoint;

        //setupArrays(defaultSize);
        areas = new Vector_Rectangle(defaultSize);

    }

    //allow user to control various values
    public void setValue(int key,int value){

        switch(key){
            case MaxNumberOfDecimalPlaces:
                this.dpCount=value;
                break;


            case FontMode:
                //<start-std>
                /**
                 //<end-std>
                 if(value==org.jpedal.render.output.GenericFontMapper.EMBED_ALL || value==org.jpedal.render.output.GenericFontMapper.EMBED_ALL_EXCEPT_BASE_FAMILIES){
                 break;
                 }
                 /**/

                this.fontMode=value;
                break;

            case PercentageScaling:
                this.scaling=value/100f;

                //adjust for single page
                if(isSingleFileOutput)
                	newTotalHeight = ((cropBox.getBounds2D().getHeight()*scaling) + pageGap) * (pageNumber-1) ; //How far down the page the content of next page starts in single file output
                
                break;

            case TextMode:
                this.textMode =value;
                break;

            case UseFontResizing:
                this.fontChangeNeeded=value;
                break;
                
            case StartOfDecode:
            	this.startPage = value;
            	break;
            	
            case EndOfDecode:
            	this.endPage = value;
            	break;
            	          	
                

           //case SpacingPercentage:
            //    this.spacingNeeded=value/100f;
            //    break;

        }
    }

    //allow user to control various values
    public void setBooleanValue(int key,boolean value){

        switch(key){


            case AddNavBar:
                this.addNavBar=value;
                break;

            case ConvertSpacesTonbsp:
                convertSpacesTonbsp=value;
                break;

            case ExcludeMetadata:
                excludeMetadata=value;
                break;

            case IncludeClip:
                includeClip=value;
                break;

            case UseImagesOnNavBar:
                this.useImagesOnNavBar=value;
                break;
                
            case HTMLImageMode:
            	htmlImageMode=value;
            	break;
        }
    }

    //allow user to control various values
    public int getValue(int key){

        int value=-1;

        switch(key){

            case FontMode:
                value=fontMode;
                break;
                
            case PercentageScaling:
                value=(int) (scaling*100f);
                break;

            case TextMode:
                value= textMode;
                break;

        }

        return value;
    }

    /**
     * allow user to set own value for certain tags
     * Throws RuntimeException
     * @param type
     * @param value
     */
    public void setTag(int type,String value){
    	

//		switch (type) {
//
//		case FORM_TAG:
//			tag[FORM_TAG]=value;
//			break;
//
//			default:
        throw new RuntimeException("Unknown tag value "+type);
//		}
    }

    /*setup renderer*/
    public void init(int width, int height, int rawRotation, Color backgroundColor) {

        if(rawRotation==90 || rawRotation==270){
            h = width * scaling;
            w = height * scaling;
        }else{
            w = width * scaling;
            h = height * scaling;
        }

        this.pageRotation = rawRotation;
        this.backgroundColor = backgroundColor;
        shadeId = 0;

        currentTextBlock = new TextBlock();
        previousTextBlock = new TextBlock();

    }

    /**
     * Add output to correct area so we can assemble later.
     * Can also be used for any specific code features (ie setting a value)
     */
    public synchronized void writeCustom(int section, Object str) {

        switch(section){

        case PAGEDATA:

            this.pageData=(PdfPageData)str;

            //any offset
            dx=pageData.getCropBoxX(pageNumber);
            dy=pageData.getCropBoxY(pageNumber);
            dr=pageData.getRotation(pageNumber);


            
            if(isSingleFileOutput && pageData.getPageCount() != 1){
                int pageCount = pageData.getPageCount()+1;

                //work out cumulative page heights so far for offset (ie not this page but all previous)
                pageoffsets = new int[pageCount];
                for(int ii = 2; ii < pageCount; ii++){ //so if page 4 we get page 1,2,3
                	pageoffsets[ii] = pageoffsets[ii-1] + (int) (pageData.getCropBoxHeight2D(pageNumber)*scaling) + pageGap;
                }
            }

            break;

        case IMAGE_CONTROLLER:

            this.imageController=(OutputImageController)str;
            this.userControlledImageScaling=imageController!=null;
            break;

        case CustomIO:

            this.customIO=(CustomIO)str;
            break;

            //<start-std>
            //special case used from PdfStreamDecoder to get font data
            case SAVE_EMBEDDED_FONT:

                //save font data as file
                Object[] fontData= (Object[]) str;
                PdfFont pdfFont=(PdfFont)fontData[0];
                String fontName=pdfFont.getFontName();
                String fileType=(String)fontData[2];
                String fontID=(String)fontData[3];

                //make sure Dir exists
                String fontPath;

                fontPath = rootDir + fileName + "/fonts/";

                File cssDir = new File(fontPath);
                if (!cssDir.exists()) {
                    cssDir.mkdirs();
                }

                byte[] rawFontData= (byte[]) fontData[1];
                fontName = fontName.replaceAll("[.,*#]", "-");

                //the fonts with , in name from Ghostscript do not work so ignore
                //add 1==2 && to line below and it will now cascade into Sam's font handler
                if(fileType.equals("ttf") && !fontName.contains(",")){  //truetype tidied up to TT

                    /**ensure unique if embedded by adding fontID and size of data*/
                    /**second text is becuase if both start with ABCDEF+ we can safely merge-
                     * example /PDFdata/sample_pdfs_html/thoughtcorp/SDM Code Audit RFP Proposal Feb 2010 v6 */
                    if(embeddedFonts.containsKey(fontName) && !baseFontNames.containsKey(pdfFont.getBaseFontName())){

                        //flag before we change it
                        baseFontNames.put(pdfFont.getBaseFontName(),"x");

                        fontName=fontName+ '_' +fontID+ '_' +rawFontData.length;
                        pdfFont.resetNameForHTML(fontName);  //and write back to font object so we use this in CSS tags

                    }

                    try {
                        rawFontData=HTMLFontUtils.convertTTForHTML(pdfFont,fontName,rawFontData);

                        if(enableOTFConversion){
                            rawFontData=HTMLFontUtils.convertPSForHTMLOTF(pdfFont,fontName,rawFontData,fileType, widths.get(fontName));
                            fileType = "otf";
                        }
                        else{
                            rawFontData=HTMLFontUtils.convertPSForHTMLWOFF(pdfFont,fontName,rawFontData,fileType, widths.get(fontName));
                            fileType = "woff";
                        }

                        if(rawFontData!=null){
                            customIO.writeFont(fontPath +fontName+ '.' +fileType, rawFontData);
                        }
                    } catch (Exception e) {
                        //tell user and log
                        logger.info("Exception: "+e.getMessage());
                    }
                }else if((fileType.equals("t1") && convertT1Fonts) || fileType.equals("cff") || (fileType.equals("ttf")&& !fontName.contains(","))){ // postscript to otf

                    if(fileType.equals("ttf")){
                        fontName=pdfFont.getBaseFontName().substring(7);
                    }else
                        fontName=pdfFont.getBaseFontName().replace('+','-');


                    /**ensure unique if embedded by adding fontID and size of data*/
                    if(embeddedFonts.containsKey(fontName)){
                        fontName=fontName+ '_' +fontID+ '_' +rawFontData.length;
                        pdfFont.resetNameForHTML(fontName);  //and write back to font object so we use this in CSS tags
                    }



                    //tell software name needs to be unique
                    hasEmbeddedFonts=true;
                    fontsToConvert.put(fontName,fontData);

                    if(enableOTFConversion){
                        fileType="otf";
                    }
                    else{
                        fileType = "woff";
                    }

                }

                if(rawFontData!=null){
                    //save details into CSS so we can put in HTML
                    StringBuilder fontTag=new StringBuilder();
                    if(type==CREATE_JAVAFX){

                        fontTag.append("Font.loadFont("); //indent
                        fontTag.append(javaFxFileName + ".class.getResource(\"").append(fileName).append("/fonts/").append(fontName).append('.').append(fileType).append("\").toExternalForm(),10);\n");

                    }else{
                        fontTag.append("@font-face {\n"); //indent
                        fontTag.append("\tfont-family: ").append(fontName).append(";\n");
                        fontTag.append("\tsrc: url(\"").append(fileName).append("/fonts/").append(fontName).append('.').append(fileType).append("\");\n");
                        fontTag.append("}\n");
                    }
                    //save font details to use later
                    embeddedFonts.put(fontName,fontTag);

                    hasEmbededFonts=true;

                    //save font id so we can see how many fonts mapped onto this name
                    String value= (String) embeddedFontsByFontID.get(fontName);
                    if(value==null)
                        embeddedFontsByFontID.put(fontName,fontID);
                    else
                        embeddedFontsByFontID.put(fontName,value+ ',' +fontID);
                }


                break;

            //<end-std>

        default:
            throw new RuntimeException("Option "+section+" not recognised");
        }

    }

    public synchronized void flagDecodingFinished(){

        if(customIO !=null && customIO.isOutputOpen()){
            completeOutput();
        }

        Object[] fontNames = fontsToConvert.keySet().toArray();
        for (Object fontName1 : fontNames) {
            Object[] fontData = fontsToConvert.get(fontName1);

            PdfFont pdfFont = (PdfFont) fontData[0];
            String fontName;// = pdfFont.getFontName();
            String fileType = (String) fontData[2];
            String fontID = (String) fontData[3];

            //make sure Dir exists
            String fontPath = rootDir + fileName + "/fonts/";
            File cssDir = new File(fontPath);
            if (!cssDir.exists()) {
                cssDir.mkdirs();
            }

            byte[] rawFontData = (byte[]) fontData[1];

            if (fileType.equals("ttf"))
                fontName = pdfFont.getBaseFontName().replace('+', '-').substring(7);
            else
                fontName = pdfFont.getBaseFontName().replace('+', '-');

            //tell software name needs to be unique
            hasEmbeddedFonts = true;

            boolean saveRawFonts = false;

            if (saveRawFonts) {
                customIO.writeFont(fontPath + fontName + ".cff",rawFontData);
            }

            try {
                if(enableOTFConversion){
                    rawFontData=HTMLFontUtils.convertPSForHTMLOTF(pdfFont,fontName,rawFontData,fileType, widths.get(fontName));
                    fileType = "otf";
                }else{
                    rawFontData=HTMLFontUtils.convertPSForHTMLWOFF(pdfFont,fontName,rawFontData,fileType, widths.get(fontName));
                    fileType = "woff";
                }
            } catch (Exception e) {
                //tell user and log
                logger.info("Exception: "+e.getMessage());
            }

            /**/
            fontName = fontName.replaceAll("[.,*#]", "-");

            if (rawFontData != null) {

                customIO.writeFont(fontPath + fontName + '.' + fileType,rawFontData);

                //save details into CSS so we can put in HTML
                StringBuilder fontTag = new StringBuilder();
                fontTag.append("@font-face {\n"); //indent
                fontTag.append("\tfont-family: ").append(fontName).append(";\n");
                fontTag.append("\tsrc: url(\"").append(fileName).append("/fonts/").append(fontName).append('.').append(fileType).append("\");\n");
                fontTag.append("}\n");

                //save font details to use later
                embeddedFonts.put(fontName, fontTag);

                //also include so we can see if it is embedded or needs replacing
                embeddedFonts.put(fontID, fontName);

                hasEmbededFonts = true;

                //save font id so we can see how many fonts mapped onto this name
                String value = (String) embeddedFontsByFontID.get(fontName);
                if (value == null)
                    embeddedFontsByFontID.put(fontName, fontID);
                else
                    embeddedFontsByFontID.put(fontName, value + ',' + fontID);
            }
        }
    }
    
    protected String round2DP (double d) {
    	//return d;
    	return "" + ((float)((int)((d*100) + 0.5)))/100;
    }
    
    protected String roundUp (double d) {
    	return "" + (int)(d + 0.99);
    }

    protected String svgShapeName;

    // save image in array to draw
    public int drawImage(int pageNumber, BufferedImage image, GraphicsState gs, boolean alreadyCached, String name, int optionsApplied, int previousUse) {

    	if (emulateEvenOdd)
    		svgShapeName = name;

        /**
         * ensure if image is reused we do not write out again
         * (assumes name is unique for key on page)
         * If first time, flag as used.
         * We only use if no clip as I have seen examples of images with diff
         * clips This would be slow to track and null works on target files.
         */
        String cacheKey=pageNumber+'-'+name;
        boolean imagesWritten=gs.getClippingShape()==null && imagesAlreadyWritten.get(cacheKey)!=null;
        if(!imagesWritten){
            imagesAlreadyWritten.put(cacheKey,"x");
        }else if(!embedImageAsBase64){ //except in this case we can ignore image so set to null and just calc coords
            image=null;
        }
    	
    	//flush any cached text before we write out
    	flushText();


    	//show if image is upside down
    	boolean needsflipping=false;//pageRotation==180 && gs.CTM[0][0]>0 && gs.CTM[1][1]>0;

    	//figure out co-ords
    	float x = (gs.CTM[2][0] * scaling);
    	float y = (gs.CTM[2][1] * scaling);

    	iw = (gs.CTM[0][0]+Math.abs(gs.CTM[1][0])) * scaling;
    	ih = (gs.CTM[1][1]+Math.abs(gs.CTM[0][1])) * scaling;

    	//value can also be set this way but we need to adjust the x coordinate
    	if (iw == 0){
    		iw = gs.CTM[1][0] * scaling;

    		if (iw < 0)
    			iw =- iw;
    	}

    	//again value can be set this way
    	if (ih == 0){
    		ih = gs.CTM[0][1] * scaling;
    	}

    	//Reset with to positive if negative
    	if(iw<0)
    		iw = iw*-1;

    	if(iw < 1)
    		iw = 1;

    	if(ih == 0) {
    		ih = 1;
    	}

    	//Account for negative widths (ie ficha_acceso_a_ofimatica_e-portafirma.pdf)
    	if(ih < 1) {
    		y += ih;
    		ih = Math.abs(ih);
    	}

    	//add negative width value to of the x coord problem_document2.pdf page 3
    	if(gs.CTM[0][0] < 0){
    		x = x - iw;
    	}

    	//adjust in this case so in correct place
    	if(gs.CTM[0][0]==0 && gs.CTM[1][1]==0 && gs.CTM[0][1]>0 && gs.CTM[1][0]<0){
    		//  x=x-iw;
    		needsflipping = true;
    	}

    	//factor in non-zero offset for cropBox
    	//for the moment lets limit it to just this case of page cropping/4.pdf
    	if(gs.CTM[0][0]==0 && gs.CTM[1][1]==0 && gs.CTM[0][1]>0 && gs.CTM[1][0]>0){
    		x=x-(pageData.getCropBoxX2D(pageNumber)/2);
    		y=y-pageData.getCropBoxY2D(pageNumber);
    	}

    	Graphics2D g2savedImage = null;
    	//scale image
    	BufferedImage savedImage = image;
    	//Image img=null;

    	if(!userControlledImageScaling && !emulateEvenOdd && !htmlImageMode && image!=null){ //default scaling option

    		//we use image type if no clip but use transparent if clipped
    		int imageMode=image.getType();

    		//see if clip and apply if needed
    		Rectangle clip=null;
    		Area clipShape = gs.getClippingShape();
    		if(clipShape!=null) {

    			//scale if needed
    			if(scaling!=1)
    				clipShape.transform(AffineTransform.getScaleInstance(scaling, scaling));

    			clip=clipShape.getBounds();

    		}
    		boolean applyClip=false;


    		//-1 to allow for decimal rounding error
    		if(clip!=null && (clip.x>x-1 || clip.y>y-1 || clip.getMaxX()<(x+iw-1) || clip.getMaxY()<(y+ih-1))){
    			applyClip=true;
    			imageMode=BufferedImage.TYPE_INT_ARGB;
    		}

    		savedImage = new BufferedImage((int)iw == 0 ? 1 : (int) iw, (int)ih == 0 ? 1 : (int) ih, imageMode);
    		g2savedImage = (Graphics2D) savedImage.getGraphics();

    		if(applyClip){ //add the clip onto image

    			AffineTransform before=g2savedImage.getTransform();

    			//apply transforms to invert
    			//we built the clip in Java2d (which has origin top left, and image in PDF coords)
    			//so we need to flip it.
    			AffineTransform invert=new AffineTransform();
    			invert.scale(1, -1);
    			invert.translate(0, -ih);

    			g2savedImage.transform(invert);

    			float dx=x;
    			float dy=y;

    			if(gs.CTM[0][1]!=0 && gs.CTM[0][1]<0){
    				dy=dy+(gs.CTM[0][1]*scaling);
    			}

    			if(gs.CTM[1][0]!=0 && gs.CTM[1][0]<0){
    				dx=dx+(gs.CTM[1][0]*scaling);
    			}

    			g2savedImage.transform(AffineTransform.getTranslateInstance(-dx, -dy));

    			//set the actual clip
    			g2savedImage.setClip(gs.getClippingShape());

    			//restore
    			g2savedImage.setTransform(before);

    		}

    		//add transform to flip if needed
    		if(needsflipping){
    			AffineTransform aff=new AffineTransform();
    			aff.scale(scaling, -scaling);
    			aff.translate(0, -ih);
    			g2savedImage.setTransform(aff);
    		}
    	}

    	//factor in offset
    	//(guess for bottom right image on general/forum/2.html)
    	if(gs.CTM[0][1]<0 && gs.CTM[1][1]!=0 ){// && gs.CTM[0][0]<gs.CTM[1][0]){
    		y=y+(gs.CTM[0][1]*scaling);

    	}

    	if(gs.CTM[1][0]<0 && gs.CTM[0][0]!=0){
    		x=x+(gs.CTM[1][0]*scaling);
    	}

    	if(gs.CTM[1][0]<0 && gs.CTM[0][0]==0){
    		x=x-iw;
    	}

    	if(gs.CTM[0][1]<0 && gs.CTM[1][1]==0){
    		y=y-ih;
    	}


    	if (htmlImageMode) {
    		if (pageRotation == 90 || pageRotation == 270) {
    			coords = new double[] {y,x};
    		} else {
    			coords = new double[] {x,y};
    		}
    	} else {
    		
    		// Covert PDF coords (factor out scaling in calc)
    		switch(pageRotation){

    		//adjust co-ords
    		//image actually rotated in rotateImage()

    		case 180:
    			coords =new double[]{cropBox.getWidth()-((iw+x)/scaling),cropBox.getHeight()-((ih+y)/scaling)};
    			break;

    			//
    		case 270:
    			// System.out.println(gs.CTM[0][0]+" "+gs.CTM[1][0]+" "+" "+gs.CTM[0][1]+" "+gs.CTM[1][1]);

    			//special case /PDFdata/sample_pdfs_html/samsung/rotate270.pdf
    			if(gs.CTM[0][0]>0 && gs.CTM[1][1]>0 && gs.CTM[1][0]==0 && gs.CTM[0][1]==0){
    				coords =new double[]{cropBox.getWidth()-(x/scaling)-image.getWidth(),y/scaling};
    			}else{
    				coords =new double[]{x/scaling,y/scaling};
    			}
    			break;

    		default:
    			coords =new double[]{x/scaling,y/scaling};
    			break;
    		}

    	}

    	correctCoords(coords);

    	//add back in scaling
    	coords[0]=coords[0]*scaling;
    	coords[1]=coords[1]*scaling;

    	//subtract image height as y co-ordinate inverted
    	coords[1] -= ih;

    	//we need to factor in scaling as cropBox not scaled and co-ords are!
    	//Rectangle rect = new Rectangle((int) (coords[0]/scaling),(int)(coords[1]/scaling),(int)(iw/scaling),(int)(ih/scaling));
    	// Bug here - images will disappear if 1px wide.

    	Rectangle2D rect = new Rectangle2D.Double(coords[0], coords[1], iw, ih);
    	Rectangle2D cropBoxScaled = new Rectangle2D.Double(cropBox.getX()*scaling, cropBox.getY()*scaling,
    			cropBox.getWidth()*scaling, cropBox.getHeight()*scaling);


    	if(cropBoxScaled.intersects(rect)) {

    		if(!userControlledImageScaling && !emulateEvenOdd && !htmlImageMode && image!=null){ //default values

    			g2savedImage.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
    			g2savedImage.drawImage(image,0, 0, (int)iw, (int)ih, null);

    		}

    		if(!userControlledImageScaling && !htmlImageMode && image!=null){
    			/**
    			 * rotate if needed
    			 * (done separately at moment to keep it simple and separate)
    			 */
    			if(pageRotation!=0){
    				savedImage = rotateImage(savedImage,pageRotation);
    			}
    		}

    		/**
    		 * store image
    		 */

    		id=name+'_'+pageNumber;

    		if(embedImageAsBase64){
    			//convert image into base64 content
    			try {
    				ByteArrayOutputStream bos=new ByteArrayOutputStream();
    				javax.imageio.ImageIO.write(savedImage,"PNG",bos);
    				bos.close();
    				imageArray=new sun.misc.BASE64Encoder().encode(bos.toByteArray());
    			} catch (IOException e) {
                    //tell user and log
                    logger.info("Exception: "+e.getMessage());
    			}
    		}else{

    			String imageDir= fileName+"/img/";

                if(!imagesWritten){  //make sure image Dir exists and write out as first time
                    File imgDir=new File(rootDir +imageDir);
                    if(!imgDir.exists())
                        imgDir.mkdirs();

                    imageName=customIO.writeImage(rootDir, imageDir+name,savedImage);
                }else{ //repeat image so just set name
                    imageName=imageDir+name+customIO.getImageTypeUsed();
                }
            }

    		return -2;
    	}else
    		return -1;
    }

    protected BufferedImage rotateImage(BufferedImage savedImage, int angle) {

        BufferedImage rotatedImage;

        if(angle==180){

            AffineTransform tx = AffineTransform.getScaleInstance(-1, -1);
            tx.translate(-savedImage.getWidth(null), -savedImage.getHeight(null));
            AffineTransformOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_NEAREST_NEIGHBOR);
            rotatedImage = op.filter(savedImage, null);

        }else{

            int w = savedImage.getWidth();
            int h = savedImage.getHeight();
            rotatedImage = new BufferedImage(h,w,savedImage.getType());
            Graphics2D g2 = rotatedImage.createGraphics();

            g2.rotate(Math.toRadians(pageRotation), w/2, h/2);

            int diff=(w-h)/2;

            if(angle==90){
                g2.drawImage(savedImage,diff,diff,null);

            }else if(angle==270){
                g2.drawImage(savedImage,-diff,-diff,null);

            }else{
            }
        }

        return rotatedImage;
    }

    /**
     * trim if needed
     * @param i
     * @return
     * * (note duplicate in OutputShape)
     */
    protected String setPrecision(double i) {


        String value=String.valueOf(i);

        int ptr=value.indexOf('.');
        int len=value.length();
        int decimals=len-ptr-1;

        if(ptr>0 && decimals> this.dpCount){
            if(dpCount==0)
                value=value.substring(0,ptr+dpCount);
            else
                value=value.substring(0,ptr+dpCount+1);

        }

        return removeEmptyDecimals(value);
    }

    /**
     * trim if needed
     * @param i
     * @return
     * * (note duplicate in OutputShape)
     */
    protected static String setPrecision(double i, int dpCount) {


        String value=String.valueOf(i);

        int ptr=value.indexOf('.');
        int len=value.length();
        int decimals=len-ptr-1;

        if(ptr>0 && decimals> dpCount){
            if(dpCount==0)
                value=value.substring(0,ptr+dpCount);
            else
                value=value.substring(0,ptr+dpCount+1);

        }

        return value;
    }

    /*save clip in array to draw*/
    public void drawClip(GraphicsState currentGraphicsState, Shape defaultClip, boolean canBeCached) {
        //RenderUtils.renderClip(currentGraphicsState.getClippingShape(), null, defaultClip, g2);
    }

    //For debugging text
//	static int textShown = 0;
//	static int amount = 7;

    public void drawEmbeddedText(float[][] Trm, int fontSize, PdfGlyph embeddedGlyph,
                                 Object javaGlyph, int type, GraphicsState gs,
                                 AffineTransform at, String glyf, PdfFont currentFontData, float glyfWidth){

        /**
         * type 3 are a special case and need to be rendered as images in all modes
         * So we will need to detect and render here for both
         */
//        if(currentFontData.getFontType()==StandardFonts.TYPE3){
//
//        }

        float[][] rawTrm=new float[3][3];
        rawTrm[0][0]=Trm[0][0];
        rawTrm[0][1]=Trm[0][1];
        rawTrm[0][2]=Trm[0][2];
        rawTrm[1][0]=Trm[1][0];
        rawTrm[1][1]=Trm[1][1];
        rawTrm[1][2]=Trm[1][2];
        rawTrm[2][0]=Trm[2][0];
        rawTrm[2][1]=Trm[2][1];
        rawTrm[2][2]=Trm[2][2];

        //ignore blocks of multiple spaces
        if(currentTokenNumber==lastTokenNumber && glyf.equals(" ") && lastGlyf.equals(" ")){
            flushText();
            return;
        }
        //trap any non-standard glyfs
        if(OutputDisplay.Helper!=null && glyf.length()>3 && !StandardFonts.isValidGlyphName(glyf)){
            glyf= OutputDisplay.Helper.mapNonstandardGlyfName(glyf,currentFontData);
        }

        //if its not visible, ignore it
        Area clip=gs.getClippingShape();
        if(clip!=null && !clip.getBounds().contains(new Point((int)Trm[2][0]+1,(int) Trm[2][1]+1)) && !clip.getBounds().contains(new Point((int)Trm[2][0]+fontSize/2,(int) Trm[2][1]+fontSize/2))){
            return;
        }

        /**
         * workout location
         */
        double[] coords = {Trm[2][0], Trm[2][1]};

        correctCoords(coords);

        if(textMode == TEXT_AS_SHAPE ||
                textMode == INVISIBLE_TEXT_ON_IMAGE && getType()== DynamicVectorRenderer.CREATE_SVG ){ //option to convert to shapes
            currentTextPosition=new TextPosition(coords,new float[]{rawTrm[0][0],rawTrm[0][1],rawTrm[1][0],rawTrm[1][1],rawTrm[2][0],rawTrm[2][1]});
            rasterizeTextAsShape(embeddedGlyph, gs, currentFontData, glyf);
            return;
        }

        //if(!glyf.equals("5") || fontSize<40)
         //   return;

        /**
         * factor in page rotation to Trm (not on canvas)
         */
        switch(this.pageRotation){

            case 90:
            {

                float x=Trm[2][0],y=Trm[2][1];
                Trm=Matrix.multiply(Trm,new float[][]{{0,-1,0},{1,0,0},{0,0,1}});
                if(Trm[0][0]==0 && Trm[1][1]==0 && Trm[0][1]*Trm[1][0]<0 && gs.CTM[0][0]>0 && gs.CTM[1][1]>0){ //ie /Users/markee/PDFdata/sample_pdfs_html/abacus/6208_Test%20PDF.pdf
                    Trm[2][0]=y;
                    Trm[2][1]=(float) (cropBox.getHeight()-x);//cropBox.height-x;//-(fontSize/2);

                }else {   //ie /Users/markee/PDFdata/sample_pdfs_html/abacus/6208_Test%20PDF.pdf
                    Trm[2][0]=x;//y;
                    Trm[2][1]=y;//cropBox.height-x;//-(fontSize/2);
                }
            }
            break;

            case 270:
            {

                float x=Trm[2][0],y=Trm[2][1];
                if(Trm[0][0]>0 && Trm[1][1]>0 && Trm[0][1]==0 && Trm[1][0]==0){
                }else{
                    Trm=Matrix.multiply(Trm,new float[][]{{0,1,0},{-1,0,0},{0,0,1}});
                }

                break;
            }
        }

        //if -100 we get value - allows user to override
        if(glyfWidth==-100){
            glyfWidth=currentFontData.getWidth(-1);
        }

        /**
        * optimisations to choose best fontsize for different cases of FontSize, Tc and Tw
        */
        int altFontSize=-1,fontCondition=-1;
        TextState currentTextState=gs.getTextState();
        if(currentTextState!=null){
            float rawFontSize=Trm[0][0];
            float tc=currentTextState.getCharacterSpacing();
            float tw=currentTextState.getWordSpacing();

            //do not use rounded up fontSize - we generate value again
            float diff=(rawFontSize-(int)rawFontSize);

            if(rawFontSize>1){
                //case 1 font sixe 8.5 or above and negative Tc
                if(fontSize==9  && diff==0.5f && tc>-0.2 && tw==0 && fontSize!=(int)rawFontSize){
                    altFontSize=(int)rawFontSize;

                    //identify type
                    fontCondition=1;

                }else if(fontSize==8  && rawFontSize>8 && tc>0 && diff<0.3f && tw==0){
                    altFontSize=fontSize+1;
                    fontCondition=2;
                }
            }
        }

        //code assumes Trm is square - if not alter font size
        if(Trm[0][0]!=Trm[1][1] && Trm[1][0]==0 && Trm[0][1]==0){
            fontSize= (int) Trm[0][0];
        }

        //Ignore empty or crappy characters
        if(glyf.length()==0 || TextBlock.ignoreGlyf(glyf)) {
            return;
        }

        //text size sometimes negative as a flag so ensure always positive
        if(fontSize<0)
            fontSize=-fontSize;

        float charWidth = fontSize * glyfWidth;
        
        //get font name  and convert if needed for output and changed
        if(!currentFontData.getBaseFontName().equals(lastFontUsed)){
            fontMapper= getFontMapper(currentFontData);
            lastFontUsed=currentFontData.getBaseFontName();

            //save font id so we can see how many fonts mapped onto this name
            String value= (String) embeddedFontsByFontID.get(lastFontUsed);
            if(value==null)
                embeddedFontsByFontID.put(lastFontUsed,"browser");
            else if(!value.contains("browser"))
                embeddedFontsByFontID.put(lastFontUsed,value+ ',' +"browser");
        }

        int textFillType = gs.getTextRenderType();
        int color = textFillType == GraphicsState.STROKE ? gs.getStrokeColor().getRGB(): gs.getNonstrokeColor().getRGB();

        //reject duplicate text used to create text bold by creating slighlty offset second character
        if(lastTrm!=null  &&
                Trm[0][0]==lastTrm[0][0] && Trm[0][1]==lastTrm[0][1] && Trm[1][0]==lastTrm[1][0] && Trm[1][1]==lastTrm[1][1]
                && glyf.equals(lastGlyf)){

            //work out absolute diffs
            float xDiff= Math.abs(Trm[2][0]-lastTrm[2][0]);
            float yDiff= Math.abs(Trm[2][1]-lastTrm[2][1]);

            //if does not look like new char, ignore
            float fontDiffAllowed=1;
            if(xDiff<fontDiffAllowed && yDiff<fontDiffAllowed){
                return;
            }
        }

        float x=(float) coords[0];
        float y=(float) coords[1];
        float rotX=x;
        float rotY=y;

        //needed for this case to ensure text runs across page
        if(this.pageRotation==90){
            rotX=Trm[2][1];
            rotY=Trm[2][0];
        }


        //Append new glyf to text block if we can otherwise flush it
        if(writeEveryGlyf || !currentTextBlock.isSameFont(fontSize, fontMapper, Trm, color)
        		|| !currentTextBlock.appendText(glyf, charWidth, rotX, rotY , groupGlyphsInTJ, (!groupGlyphsInTJ || currentTokenNumber!=lastTokenNumber),x,y)) {

        	flushText();
            currentTextPosition=new TextPosition(coords,new float[]{rawTrm[0][0],rawTrm[0][1],rawTrm[1][0],rawTrm[1][1],rawTrm[2][0],rawTrm[2][1]});

            //Set up new block, if it just a space disregard it.
        	if(!glyf.equals(" ")) {
        		float spaceWidth = fontSize * currentFontData.getCurrentFontSpaceWidth();

        		currentTextBlock = new TextBlock(glyf, fontSize, fontMapper, Trm, x, y,
        				charWidth, color, spaceWidth, cropBox.getBounds(), Trm, altFontSize,fontCondition,rotX,rotY,pageRotation);

        		if(convertSpacesTonbsp)
        			currentTextBlock.convertSpacesTonbsp(true);

        		if(currentTextBlock.getRotationAngle() == 0) {
        			currentTextBlock.adjustY(-fontSize);
        		}
            }else {
        		currentTextBlock = new TextBlock();
            }
        }

        //update incase changed
        lastTokenNumber=currentTokenNumber;
        lastGlyf=glyf;
        lastTrm=Trm;

    }

    private void rasterizeTextAsShape(PdfGlyph embeddedGlyph, GraphicsState gs, PdfFont currentFontData, String glyf) {

        //if(currentFontData.getBaseFontName().contains("FFAAHC+MSTT31ca9d00")){
        //    System.out.println(fontSize+" "+glyf+" "+currentFontData.getBaseFontName()+" "+currentFontData);
        //}

        /**
         * convert text to shape and draw shape instead
         * Generally text at 1000x1000 matrix so we scale down by 1000 and then up by fontsize
         */
        if(embeddedGlyph!=null && embeddedGlyph.getShape()!=null && !glyf.equals(" ") ){

            GraphicsState TextGs=gs;//new GraphicsState();

            //name we will store draw code under as routine
            String JSRoutineName;

            //check all chars letters or numbers and use int value if invalid
            boolean isInvalid=false;
            for(int aa=0;aa<glyf.length();aa++){
                if(!Character.isLetterOrDigit(glyf.charAt(aa))){
                    isInvalid=true;
                }

                //exit if wrong
                if(isInvalid)
                    break;
            }
            
            // fix for the occurance of . in fontIDs
            String safeFontID = (String) currentFontData.getFontID();
            safeFontID = StringUtils.makeMethodSafe(safeFontID);
            if(usedFontIDs.containsKey(safeFontID) && usedFontIDs.get(safeFontID) != currentFontData.getBaseFontName()) {
            	// add extra fontID stuff
            	safeFontID += StringUtils.makeMethodSafe(currentFontData.getBaseFontName());
            }
            else {
            	usedFontIDs.put(safeFontID, currentFontData.getBaseFontName());
            }

            if(isInvalid)
                JSRoutineName=safeFontID+Integer.toHexString((int)glyf.charAt(0));
            else if(glyf.length()==0){
                JSRoutineName=safeFontID+'_'+embeddedGlyph.getID();
            }else{
                JSRoutineName=safeFontID+glyf;
            }

//            System.out.println(currentFontData.getBaseFontName() + " " + JSRoutineName + "  fontID= " + safeFontID);

            //ensure starts with letter
            if(!Character.isLetter(JSRoutineName.charAt(0)))
                JSRoutineName= 's' +JSRoutineName;

            //flag to show if generated
            String cacheKey= currentFontData.getBaseFontName() + '.' + JSRoutineName;

            //see if we have already decoded glyph and use that data to reduce filesize
            boolean isAlreadyDecoded=glyfsRasterized.containsKey(cacheKey);

            //get the glyph as textGlyf shape (which we already have to render)
            Area textGlyf = (Area) embeddedGlyph.getShape().clone();

            //useful to debug
            //textGlyf=new Area(new Rectangle(0,0,1000,1000));

            //adjust GS to work correctly
            TextGs.setClippingShape(null);
            TextGs.setFillType(gs.getTextRenderType());

            /**
             *  adjust scaling to factor in font size
             */
            float d= (float) (1f/currentFontData.FontMatrix[0]);

            //allow for rescaled TT which work on a much larger grid
            if(textGlyf.getBounds().height>2000){
                d=d*100;
            }

            //do the actual rendering
            writeCustom(SCRIPT, "pdf.save();");

            /**
             * adjust placing
             */
            writePosition(JSRoutineName, true,d);

            completeTextShape(gs, JSRoutineName);

            //generate the JS ONCE for each glyf
            if(!isAlreadyDecoded){

                drawNonPatternedShape(textGlyf, TextGs, Cmd.Tj, JSRoutineName, null, null);
                glyfsRasterized.put(cacheKey,"x"); //flag it as now in file
            }

            completeRasterizedText();
        }
    }

    protected void completeTextShape(GraphicsState gs, String jsRoutineName) {
        throw new RuntimeException("method root completeTextShape(GraphicsState gs, String JSRoutineName) should not be called");

    }

    protected FontMapper getFontMapper(PdfFont currentFontData) {
        return null;
    }

    /*save shape in array to draw*/

    public void drawShape(Shape currentShape, GraphicsState gs, int cmd) {
    	
        //fix for missing lines on /sample_pdfs_html/general/mmv6.pdf
        if(currentShape.getBounds().height<1 && (gs.getLineWidth()*gs.CTM[0][0]>0.5f)) {
            currentShape=new Rectangle(currentShape.getBounds().x,currentShape.getBounds().y,currentShape.getBounds().width,1);
        }

        if(!isObjectVisible(currentShape.getBounds(),gs.getClippingShape())) {
            return;
        }

        //flush any cached text before we write out
        flushText();


        //turn pattern into an image
        if(gs.getNonstrokeColor().isPattern() || gs.nonstrokeColorSpace.getID()== ColorSpaces.Pattern) {  //complex stuff

            drawPatternedShape(currentShape, gs);

        }
        else {  //standard shapes

            drawNonPatternedShape(currentShape, gs, cmd,null, cropBox, midPoint);

        }
    }

    protected void drawNonPatternedShape(Shape currentShape, GraphicsState gs, int cmd, String name,Rectangle2D cropBox,Point2D midPoint) {}

    protected void drawPatternedShape(Shape currentShape, GraphicsState gs) {
    	// This is how I have interpreted what the PDF spec says about shapes. Leon

    	//Add offset with the specific page height taken to consideration
        if(pageoffsets!=null)
            offset=pageoffsets[pageNumber];

        double iw = currentShape.getBounds2D().getWidth();
        double ih = currentShape.getBounds2D().getHeight();
        double ix = currentShape.getBounds2D().getX();
        double iy = currentShape.getBounds2D().getY();

        coords = new double[]{ix,iy};
        correctCoords(coords);

        int ixScaled = (int)(coords[0] * scaling);
        // The x plus width is the unrounded x position plus the width, then everything rounded up.
        int iwScaled = (int)((((coords[0] + iw) - (int)coords[0]) * scaling) + 1);
        int iyScaled = (int)((coords[1] - ih) * scaling);
        int ihScaled = (int)((((coords[1] + ih) - (int)coords[1]) * scaling) + 1);

        if (iwScaled < 1 || ihScaled <1) {
            return; // Invisible shading - THIS SHOULD BE IMPOSSIBLE GIVEN THE ABOVE CALCULATIONS.
        }

        BufferedImage img = new BufferedImage(iwScaled, ihScaled, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2=img.createGraphics();
        AffineTransform aff=new AffineTransform();
        PdfPaint col = gs.getNonstrokeColor();

        aff.scale(scaling,scaling);
        aff.scale(1,-1);
        aff.translate(0, -ih);
        aff.translate(-ix, -iy);

        int pageH=pageData.getCropBoxHeight(pageNumber), pageY=0;
        col.setScaling(0,pageH,scaling,0,pageY);
        col.setRenderingType(DynamicVectorRenderer.CREATE_HTML);

        g2.setTransform(aff);
        g2.setPaint(col);
        g2.fill(currentShape);
        g2.draw(currentShape);

        Rectangle2D shading = new Rectangle(ixScaled, iyScaled, iwScaled, ihScaled);
        Rectangle2D cropBoxScaled = new Rectangle.Double(cropBox.getX()*scaling, cropBox.getY()*scaling,
                cropBox.getWidth()*scaling, cropBox.getHeight()*scaling);

        // Check shade will be drawn on page.
        if (cropBoxScaled.intersects(shading)) {

            /* Crop image if it goes off page.
            boolean crop = false;
            int xxx = 0;
            int yyy = 0;
            if (ixScaled<0) { // Image goes off left
                xxx = ixScaled * -1;
                iwScaled = iwScaled+ixScaled;
                ixScaled = 0;
                crop = true;
            }
            if (ixScaled+iwScaled > cropBoxScaled.getWidth()) { // Image goes off right
                iwScaled = round(cropBoxScaled.getWidth() - ixScaledUnrounded, 0.5);
                crop = true;
            }
            if (ihScaled<0) { // Image goes off top
                yyy = iyScaled * -1;
                ihScaled = ihScaled+iyScaled;
                iyScaled = 0;
                crop = true;
            }
            if (iyScaled+ihScaled > cropBoxScaled.getHeight()) { // Image goes off bottom
                ihScaled = round(cropBoxScaled.getHeight() - iyScaledUnrounded, 0.5);
                crop = true;
            }
            if (crop) {
                if (xxx + iwScaled > img.getWidth()) {
                    iwScaled = img.getWidth() - xxx;
                }
                if (yyy + ihScaled > img.getHeight()) {
                    ihScaled = img.getHeight() - yyy;
                }
                img = img.getSubimage(xxx, yyy, iwScaled, ihScaled);
            }
            /**/

            if (pageRotation == 90 || pageRotation == 270) {
            	
            	iyScaled = (int) (cropBoxScaled.getHeight() - iyScaled - ihScaled);
            	// This needs to be calculated way up there. These X,Y,W,H values were calculated based on the rotation at 0.
                
                int tmp;
                tmp=iwScaled;
                iwScaled=ihScaled;
                ihScaled=tmp;

                tmp=ixScaled;
                ixScaled=iyScaled;
                iyScaled=tmp;

                img = rotateImage(img,pageRotation);
            }

            currentPatternedShapeID="shade" + (shadeId++)+ '_' +pageNumber;

            if(embedImageAsBase64){
                //convert image into base64 content
                try {
                    ByteArrayOutputStream bos=new ByteArrayOutputStream();
                    javax.imageio.ImageIO.write(img,"PNG",bos);
                    bos.close();
                    currentPatternedShapeImageSetting="\" src=\"data:image/png;base64,"+new sun.misc.BASE64Encoder().encode(bos.toByteArray())+ '"';
                } catch (IOException e) {
                    //tell user and log
                    logger.info("Exception: "+e.getMessage());
                }
            }else{
                //make sure image Dir exists
            	if (type == CREATE_JAVAFX){
                    fileName = "page" + pageNumberAsString;
                }
                String imageDir= fileName + "/shade/";
                File imgDir=new File(rootDir +imageDir);
                if(!imgDir.exists())
                    imgDir.mkdirs();

                // Store image.
                currentPatternedShapeName = customIO.writeImage(rootDir,imageDir+currentPatternedShapeID,img);

                currentPatternedShapeImageSetting="\" src=\"" + currentPatternedShapeName + '"';

            }
            
            currentPatternedShape = new int[]{ixScaled, iyScaled, iwScaled, ihScaled};
        } else {
        	currentPatternedShape = new int[]{-1,-1,-1,-1};
        }
    	
    }

    private boolean isObjectVisible(Rectangle bounds, Area clip) {

        boolean fixShape=true;

        if(fixShape && dx==0 && dy==0 && dr==0){
        //get any Clip (only rectangle outline)
        Rectangle clipBox;

            //if(dx!=0 || dy!=0) //factor in offset on crop
            //	bounds.translate(-dx, -dy);

        if(clip!=null)
            clipBox=clip.getBounds();
        else
            clipBox=null;


        //additional tests to allow for being on edge
        if(cropBox!=null && !cropBox.intersects(bounds) && Math.abs(cropBox.getMaxX()-bounds.getMaxX())>1 ||
                ( clipBox!=null && !clipBox.intersects(bounds)&& Math.abs(clipBox.getMaxX()-bounds.getMaxX())>1)){
            return false;
            }
        }

        return true;
    }

    /*add XForm object*/

    final public void drawXForm(DynamicVectorRenderer dvr, GraphicsState gs) {

        //flush any cached text before we write out
        flushText();

        //renderXForm(dvr, gs.getStrokeAlpha());
    }


    /**
     * add footer and other material to complete
     */
    protected void completeOutput() {}


    public void setOutputDir(String outputDir,String outputFilename, String pageNumberAsString) {
        rootDir=outputDir;
        fileName=outputFilename;

        this.pageNumberAsString=pageNumberAsString;
    }

    /**
     * Coverts an array of numbers to a String for JavaScript parameters.
     *
     * @param coords Numbers to change
     * @param count Use up to count doubles from coords array
     * @return String Bracketed stringified version of coords
     */
    protected String coordsToStringParam(double[] coords, int count)
    {
        String result = "";

        for(int i = 0; i<count; i++) {
            if(i!=0) {
                result += ",";
            }

            result += setPrecision(coords[i]);
        }

        return result;
    }

    /**
     * Converts coords from Pdf system to java.
     */
    protected void correctCoords(double[] coords)
    {
        coords[0] = coords[0] - midPoint.getX();
        coords[0] += cropBox.getWidth() / 2;

        coords[1] = coords[1] - midPoint.getY();
        coords[1] = 0 - coords[1];
        coords[1] += cropBox.getHeight() / 2;
    }


    /**
     * Formats an int to CSS rgb(r,g,b) string
     *
     */
    protected static String rgbToColor(int raw)
    {
        int r = (raw>>16) & 255;
        int g = (raw>>8) & 255;
        int b = raw & 255;

        return "rgb(" + r + ',' + g + ',' + b + ')';
    }
    
	/**
	 * Used in FXML and SVG for now.	 * 
	 * 
	 * @param rgb - The raw RGB values
	 * @return The Hex equivalents
	 */
	//RGB converted to Hex color
	public static String hexColor(int rgb) {

		String hexColor;
		
		hexColor = Integer.toHexString(rgb);
		hexColor = hexColor.substring(2,8);
		hexColor = '#' +hexColor;
				
//		System.out.println(currentTextBlock.getColor()+" "+Integer.toHexString(currentTextBlock.getColor())+" "+hexColor);
		
		return hexColor;
	}

	/**
	 * Add current date
	 * 
	 * @return returns date as string in 21 - November - 2011 format
	 */

	public static String getDate(){

		DateFormat dateFormat = new SimpleDateFormat("dd - MMMMM - yyyy"); // get current date

		Date date = new Date();
		//System.out.println(dateFormat.format(date));
		return dateFormat.format(date);
	}

    /**
     * Draws boxes around where the text should be.
     */
    protected void drawTextArea() {}

    /**
     * Draw a debug area around border of page.
     */
    protected void drawPageBorder() {}

    /**
     * allow tracking of specific commands
     **/
    public void flagCommand(int commandID, int tokenNumber){

        switch(commandID){

            case Cmd.BT:

                //reset to will be rest for text
//			lastR=-1;
//			lastG=-1;
//			lastB=-1;
                break;

            case Cmd.Tj:
                this.currentTokenNumber=tokenNumber;
                break;
        }
    }


	protected String replaceTokenValues(String customSingleFileName, int type) {
		
		//Strip the file name from the root dir
		String nameOfPDF = rootDir.substring(0, rootDir.length()-1); //strip off last / or \
		
		//find end (which could be after \ or /)
		int pt = nameOfPDF.lastIndexOf('\\');
		int fowardSlash = nameOfPDF.lastIndexOf('/');
		if(fowardSlash>pt)
			pt=fowardSlash;
		
		//and remove path from filename
		nameOfPDF = nameOfPDF.substring(pt+1);
		
		//all tokens start $ so ignore if not present
		if(customSingleFileName!=null && customSingleFileName.contains("$")){
			
			//possible replacement values
			String fileName = nameOfPDF;
			String pageNumber = pageNumberAsString;
			String pageCount = String.valueOf(pageData.getPageCount());


			//do the replacements
			customSingleFileName=customSingleFileName.replace("$fileName$", fileName);
			
			//prevent "$currentPageNumber$" being used in filename as several files gets produced instead of one
			if(type==OutputDisplay.CustomSingleFileName && customSingleFileName.contains("$currentPageNumber$"))
				throw new RuntimeException("$currentPageNumber$ - is an in valid token for file name. Use $fileName$ to get the file name or $totalPageNumber$ to get the total amount of pages ");
			
			customSingleFileName=customSingleFileName.replace("$currentPageNumber$", pageNumber);
			customSingleFileName=customSingleFileName.replace("$totalPageNumber$", pageCount);
			
		}
		
		return customSingleFileName;
		
	}
    public boolean isScalingControlledByUser(){
        return userControlledImageScaling;
    }
    
    /**
     * Used in JavaFx and FXML to convert pdf font weight to the JavaFX/FXML equivalent
     *  
     * @param weight - pdf weight that needs converting to javaFx 
     * @return JavaFx / FXML equivalent weight
     */
	protected static String setJavaFxWeight(String weight) {
		String javaFxWeight = "";
		if(weight.equals("normal"))
			javaFxWeight="NORMAL";
		else if(weight.equals("bold"))
			javaFxWeight="BOLD";
		else if(weight.equals("bolder"))
			javaFxWeight="BLACK";
		else if(weight.equals("lighter"))
			javaFxWeight="EXTRA_LIGHT";
		else if(weight.equals("100"))
			javaFxWeight="THIN";
		else if(weight.equals("900"))
			javaFxWeight="BLACK";
		
		return javaFxWeight;
	}

    /**
     * Write out text buffer in correct format
     */
    protected void flushText()
    {
        if(DEBUG_TEXT_AREA) {
            drawTextArea();
        }

        if(currentTextBlock==null || currentTextBlock.isEmpty()) {
            return;
        }

        writeoutTextAsDiv(getFontScaling());

        //Reset current block and store the last one so we dont have to repeat javascript code later
        if(!currentTextBlock.isEmpty()) {
            previousTextBlock = currentTextBlock;
        }
        currentTextBlock = new TextBlock();

    }

    private float getFontScaling() {

        float fontScaling=0;
        /**
         * work out font scaling needed
         */
        float[] aff=currentTextPosition.getRawAffine();

        if(aff[2]==0){
            fontScaling=aff[3];
        }else if(aff[3]==0){
            fontScaling=aff[2];
        }else{
            fontScaling= (float) Math.sqrt((aff[2]*aff[2])+(aff[3]*aff[3]));
        }
        fontScaling=Math.abs(fontScaling);

        return fontScaling;  //To change body of created methods use File | Settings | File Templates.
    }

    protected void writeoutTextAsDiv(float fontScaling) {

    }

    protected void writePosition(String JSRoutineName, boolean isShape,float fontScaling) {

        float[] aff=new float[4]; //will hold matrix

        double[] coords=currentTextPosition.getCoords();

        //calc text co-ords
        double tx,ty;

        //////
        /**factor in page rotation here and adjust txt,ty
         *
         * see
         * http://en.wikipedia.org/wiki/Rotation_matrix
         * https://groups.google.com/forum/?fromgroups=#!topic/libharu/CPZy7kT9PQI
         * for explanation of rotations
         *
         * The div and rast methods work slightly differently, hence 2 methods
         */

        float[][] rotatedTrm=new float[3][3];

        //if(currentTextBlock.getOutputString(true).contains("Revenue Growth"))
        //System.out.println(currentTextBlock.getOutputString(true));

        switch(pageRotation){
            default:
                rotatedTrm=currentTextPosition.getTrm();

                tx= (coords[0]*scaling);
                ty= (coords[1]*scaling);

                aff[0]=rotatedTrm[0][0];
                aff[1]=rotatedTrm[0][1];
                aff[2]=rotatedTrm[1][0];
                aff[3]=rotatedTrm[1][1];

                /**
                 * cords work slightly differently for canvas and text
                 * (canvas is from orgin, text is corner of text div)
                 * so routines not the same
                 */
                if(isShape){ //simple version used for shape, no scaling
                    if(aff[3]!=0){
                        aff[3]=-aff[3];
                    }
                }else{   //text div version


                    if(type!=CREATE_SVG && type != CREATE_JAVAFX){
                        tx=  (tx-(aff[1]*scaling));
                        ty=  (ty-(aff[3]*scaling));
                    }

                    if(aff[2]!=0){
                        aff[2]=-aff[2];
                    }
                }

                //both versions need this tweak
                if(aff[1]!=1){
                    aff[1]=-aff[1];
                }

                break;

            case 90:

                ty=  (coords[0]*scaling);
                tx=((cropBox.getHeight()-coords[1])*scaling);

                float[][] rotated;
                if(isShape){
                    rotated=Matrix.multiply(currentTextPosition.getTrm(),new float[][]{{-1,0,0},{0,1,0},{0,0,1}});
                }else{
                    rotated=Matrix.multiply(new float[][]{{0,1,0},{-1,0,0},{0,0,1}},currentTextPosition.getTrm());
                }

                aff[0]=rotated[0][0];
                aff[1]=rotated[0][1];
                aff[2]=rotated[1][0];
                aff[3]=rotated[1][1];

                if(!isShape){

                    if(aff[0]<0 && aff[3]<0){
                        aff[0]=-aff[0];
                        aff[3]=-aff[3];
                    }

                    tx= (tx+(aff[1]*scaling));

                    ty= (ty-(aff[3]*scaling));
                }

                break;

            case 180:

                tx= ((cropBox.getWidth()-coords[0])*scaling);
                ty=((cropBox.getHeight()-coords[1])*scaling);

                rotatedTrm=Matrix.multiply(currentTextPosition.getTrm(),new float[][]{{1,0,0},{0,-1,0},{0,0,1}});
                aff[0]=rotatedTrm[0][0];
                aff[1]=rotatedTrm[0][1];
                aff[2]=rotatedTrm[1][0];
                aff[3]=rotatedTrm[1][1];

                //text needs turning upside down
                if(!isShape){
                    aff[0]=-aff[0];
                    ty= (ty-(aff[3]*scaling));
                }
                break;

            case 270:

                ty=((cropBox.getWidth()-coords[0])*scaling);
                tx=((coords[1])*scaling);

                if(isShape){
                    rotated=Matrix.multiply(currentTextPosition.getTrm(),new float[][]{{-1,0,0},{0,1,0},{0,0,1}});
                }else{
                    rotated=Matrix.multiply(currentTextPosition.getTrm(),new float[][]{{0,-1,0},{1,0,0},{0,0,1}});
                }

                aff[0]=rotated[0][0];
                aff[1]=rotated[0][1];
                aff[2]=rotated[1][0];
                aff[3]=rotated[1][1];

                
                if(!isShape){
                    if(aff[0]<0 && aff[3]<0){
                        aff[0]=-aff[0];
                        aff[3]=-aff[3];
                    }
                    tx= (tx+(aff[1]*scaling));
                }

                break;
        }

        for(int ii=0;ii<4;ii++){
            if(aff[ii]==-0.0){
                aff[ii]=0.0f;
            }
        }

        //useful debug code
         //  System.out.println(fontScaling+" "+pageRotation+" "+" "+aff[0]+" "+aff[1]+" "+aff[2]+" "+aff[3]+" "+" "+currentTextBlock.getOutputString(true));

        /**
         * in both cases we have a simple version for left to write text and a transform for anything more complicated
         */
        if(isShape){
            writeRasterizedTextPosition(JSRoutineName, aff, (int)tx, (int)ty,fontScaling);
        }else{
            writeTextPosition(aff, (int)tx, (int)ty, fontScaling);
        }
    }


    protected void writeTextPosition(float[] aff, int tx, int ty, float scaling) {
        throw new RuntimeException("writeTextPosition(float[] aff, int tx, int ty, int scaling)");
    }

    protected void writeRasterizedTextPosition(String JSRoutineName, float[] aff, int tx, int ty, float fontScaling) {
        throw new RuntimeException("method root writeRasterizedTextPosition(String JSRoutineName, float[] aff, int tx, int ty) should not be called");
    }

    protected void completeRasterizedText() {
        throw new RuntimeException("completeRasterizedText");
    }

    protected static String tidy(float val) {

        return removeEmptyDecimals(String.valueOf(val));

    }

    private static String removeEmptyDecimals(String numberValue) {
        //remove any .0000)
        int ptr=numberValue.indexOf('.');
        if(ptr>-1){
            boolean onlyZeros=true;
            int len=numberValue.length();
            for(int ii=ptr+1;ii<len;ii++){ //test if . followed by just zeros
                if(numberValue.charAt(ii)!='0'){
                    onlyZeros=false;
                    ii=len;
                }
            }

            //if so remove
            if(onlyZeros){
                numberValue=numberValue.substring(0,ptr);
            }
        }

        return numberValue;
    }

    public void saveAdvanceWidth(String fontName, String glyphName, int width) {

        fontName = fontName.replace('+', '-');

        HashMap<String,Integer> font = widths.get(fontName);

        if (font == null) {
            font = new HashMap<String, Integer>();
            widths.put(fontName, font);
        }

        font.put(glyphName, width);
    }


}
