/**
 * ===========================================
 * Java Pdf Extraction Decoding Access Library
 * ===========================================
 *
 * Project Info:  http://www.jpedal.org
 *
 * (C) Copyright 2008, IDRsolutions and Contributors.
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

  * FormStream.java
  * ---------------
  * (C) Copyright 2008, by IDRsolutions and Contributors.
  *
  *
  * --------------------------
 */
package org.jpedal.objects.raw;

import org.jpedal.external.ExternalHandlers;
import org.jpedal.io.PdfObjectReader;
import org.jpedal.io.ObjectStore;
import org.jpedal.objects.acroforms.utils.ConvertToString;
import org.jpedal.objects.acroforms.actions.ActionHandler;
import org.jpedal.objects.acroforms.formData.ComponentData;

import org.jpedal.parser.ValueTypes;
import org.jpedal.utils.LogWriter;
import org.jpedal.fonts.PdfFont;
import org.jpedal.parser.PdfStreamDecoder;
import org.jpedal.render.*;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.util.Iterator;
import java.util.Map;
import java.util.StringTokenizer;

/**
 * can object and creat images, set values in Appearances
 */
public class FormStream {

    final public static boolean debugUnimplemented = false;//to show unimplemented parts*/
    final public static boolean debug = false;//print info to screen

    //only display once
    private static boolean showFontMessage=false;

    /**
     * exit when an unimplemented feature or error has occured in form/annot code
     */
    final public static boolean exitOnError=false;

    /** handle of file reader for form streams*/
    protected PdfObjectReader currentPdfFile;

    /** if true makes the javascript define on each method call, if false it sets up all JS at creation point*/
    public static boolean marksNewJavascriptCode=false;

    /**
     * stop anyone creating empty  instance
     */
    public FormStream(){}

    /**
     * used if we want to recreate (ie for better print quality)
     */
    public FormStream(PdfObjectReader inCurrentPdfFile) {

        currentPdfFile = inCurrentPdfFile;

    }

    public static final int[] id = {PdfDictionary.A,PdfDictionary.C2,PdfDictionary.Bl,
            PdfDictionary.E, PdfDictionary.X, PdfDictionary.D, PdfDictionary.U, PdfDictionary.Fo,
            PdfDictionary.PO, PdfDictionary.PC, PdfDictionary.PV,
            PdfDictionary.PI, PdfDictionary.O, PdfDictionary.C1, PdfDictionary.K,
            PdfDictionary.F, PdfDictionary.V, PdfDictionary.C2, PdfDictionary.DC,
            PdfDictionary.WS, PdfDictionary.DS, PdfDictionary.WP, PdfDictionary.DP};

    /**
     * takes in a FormObject already populated with values for the child to overwrite
     */
    public FormObject createAppearanceString(FormObject formObj, PdfObjectReader inCurrentPdfFile) {

        currentPdfFile = inCurrentPdfFile;
        
        init(formObj);

        return formObj;
    }

    private void init(FormObject formObject) {

        final boolean debug=false;//formObject.getPDFRef().equals("68 0 R");

        if(debug)
            System.out.println("------------------------------setValues-------------------------------"+formObject+ ' ' +formObject.getObjectRefAsString());


        //set Ff flags
        int Ff=formObject.getInt(PdfDictionary.Ff);
        if(Ff!=PdfDictionary.Unknown)
            formObject.commandFf(Ff);

        //set Javascript
        if(!marksNewJavascriptCode) //lock out to test
            resolveAdditionalAction(formObject);

        if(debug)
            System.out.println("AP="+formObject.getDictionary(PdfDictionary.AP));


        //at the moment only handles static
        // (and not dynamic which are created at Runtime if
        // formObject.getBoolean(PdfDictionary.NeedAppearances) is true
        setupAPimages(formObject, debug);


        //set H
        int key = formObject.getNameAsConstant(PdfDictionary.H);
        if(key!=PdfDictionary.Unknown){
            /**
             * highlighting mode
             * done when the mouse is pressed or held down inside the fields active area
             * N nothing
             * I invert the contents
             * O invert the border
             * P display down appearance stream, or if non available offset the normal to look down
             * T same as P
             *
             * this overides the down appearance
             * Default value = I
             */
            if (key==PdfDictionary.T || key==PdfDictionary.P) {
                if (!formObject.hasDownImage())
                    formObject.setOffsetDownApp();

            } else if (key==PdfDictionary.N) {
                //do nothing on press
                formObject.setNoDownIcon();

            } else if (key==PdfDictionary.I) {
                //invert the contents colors
                formObject.setInvertForDownIcon();

            }
        }

        //set Fonts

        String textStream=formObject.getTextStreamValue(PdfDictionary.DA);

        if(textStream!=null){
            decodeFontCommandObj(textStream, formObject);

            //System.out.println("textStream="+textStream);

        }

        //code from FormStream not called but may be needed
        //this is font stream
        /*stream
        //turn into byte array and add
        // (<text>)tj
        String textString = formObject.getContents();
        if (textString != null) {
            byte[] textbytes = StringUtils.toBytes(textString);


            System.out.println("textString="+textString);
            int streamLength = stream.length;
            byte[] newbytes = new byte[streamLength + textbytes.length];
            for (int i = 0; i < newbytes.length; i++) {
                if (i < streamLength)
                    newbytes[i] = stream[i];
                else
                    newbytes[i] = textbytes[i - streamLength];
            }

            //then send into stream decoder
            PdfStreamDecoder textDecoder = new PdfStreamDecoder();
            textDecoder.decodeStreamIntoObjects(newbytes);

            StringBuilder textData = textDecoder.getlastTextStreamDecoded();
            if (textData != null)
                formObject.setTextValue(textData.toString());
        }
        /**/

    }

    /** decodes and stores the ap images */
    public void setupAPimages(FormObject formObject, boolean debug) {

        //if(formObject.getObjectRefAsString().equals("54 0 R"))
        //    System.out.println("aa");

    	
        //setup images - trickle throuh maps
        int[] values=new int[]{PdfDictionary.N, PdfDictionary.R, PdfDictionary.D}; //N must be first
        final String[] names=new String[]{"PdfDictionary.N", "PdfDictionary.R", "PdfDictionary.D"}; //N must be first

        int APcount=values.length;
        PdfObject APobj;
        BufferedImage img;
        for(int ii=0;ii<APcount;ii++){

            //debug=formObject.getPDFRef().equals("68 0 R") && values[ii]==PdfDictionary.N;

            APobj=formObject.getDictionary(PdfDictionary.AP).getDictionary(values[ii]);
            if(APobj!=null){

                if(debug && values[ii]==PdfDictionary.N)
                    System.out.println(" AP ("+names[ii]+")="+APobj+ ' ' +APobj.getObjectRefAsString()+
                            " AP="+formObject.getDictionary(PdfDictionary.AP)+
                            " form="+formObject+ ' ' +formObject.getObjectRefAsString()+" stream="+APobj.getDecodedStream()+" Off="+APobj.getDictionary(PdfDictionary.Off));


                //main
                if(APobj.getDecodedStream()!=null){
            		img=rotate(decode(currentPdfFile, APobj, formObject.getParameterConstant(PdfDictionary.Subtype)),
                        formObject.getDictionary(PdfDictionary.MK).getInt(PdfDictionary.R));
					
                    if(debug)
                            System.out.println(img);

                    if(img==null)
                        continue;

                    if(values[ii]==PdfDictionary.D){

                    	formObject.setAppearanceImage(img, PdfDictionary.D, PdfDictionary.Off);

                        if(debug){
                            System.out.println("D "+img);
                        }
                        
                    }else if(values[ii]==PdfDictionary.N && !formObject.hasNormalOff()){

                    	formObject.setAppearanceImage(img,PdfDictionary.N,PdfDictionary.Off);
                        if(debug)
                            System.out.println("N "+img);

                    }else if(values[ii]==PdfDictionary.R){
                        formObject.setAppearanceImage(img,PdfDictionary.R,PdfDictionary.Off);
                        if(debug)
                            System.out.println("R "+img);
                    }

                }else{  //Off, /On Other

                    //On
                    PdfObject OnObj=APobj.getDictionary(PdfDictionary.On);
                    if(OnObj!=null){

                        img=rotate(decode(currentPdfFile, OnObj, formObject.getParameterConstant(PdfDictionary.Subtype)),
                                formObject.getDictionary(PdfDictionary.MK).getInt(PdfDictionary.R));

                        if(debug)// && values[ii]==PdfDictionary.N)
                            System.out.println("On="+OnObj+ ' ' +img);

                        if(img==null)
                            continue;

                        if(values[ii]==PdfDictionary.D)	{

                            formObject.setAppearanceImage(img, PdfDictionary.D, PdfDictionary.On);

                            if (!formObject.hasDownOff())
                                formObject.setAppearanceImage(null, PdfDictionary.D, PdfDictionary.Off);

                        }else if(values[ii]==PdfDictionary.N){
                            formObject.setAppearanceImage(img, PdfDictionary.N, PdfDictionary.On);
                            formObject.setNormalOnState("On");

                        }else if(values[ii]==PdfDictionary.R){

                            formObject.setAppearanceImage(img,PdfDictionary.R,PdfDictionary.On);
                            if (!formObject.hasRolloverOff())
                                formObject.setAppearanceImage(null,PdfDictionary.R,PdfDictionary.Off);
                        }
                    }

                    //Off
                    PdfObject OffObj=APobj.getDictionary(PdfDictionary.Off);
                    if(OffObj!=null){

                        img=rotate(decode(currentPdfFile, OffObj, formObject.getParameterConstant(PdfDictionary.Subtype)),
                                formObject.getDictionary(PdfDictionary.MK).getInt(PdfDictionary.R));

                        if(debug)//  && values[ii]==PdfDictionary.N)
                            System.out.println("Off="+OffObj+ ' ' +OffObj.getDecodedStream()+ ' ' +img);

                        if(img==null)
                            continue;

                        if(values[ii]==PdfDictionary.D)	{
                            formObject.setAppearanceImage(img, PdfDictionary.D, PdfDictionary.Off);

                        }else if(values[ii]==PdfDictionary.N){
                            formObject.setAppearanceImage(img,PdfDictionary.N,PdfDictionary.Off);

                        }else if(values[ii]==PdfDictionary.R){
                            formObject.setAppearanceImage(img,PdfDictionary.R,PdfDictionary.Off);
                        }
                    }


                    //Other
                    Map otherValues=APobj.getOtherDictionaries();
                    if(otherValues!=null && !otherValues.isEmpty()){

                        if(debug)
                            System.out.println(formObject.getObjectRefAsString()+" AP Other="+otherValues);

                        Iterator keys=otherValues.keySet().iterator();
                        Object val;
                        String key;
                        while(keys.hasNext()){
                            key=(String)keys.next();
                            val=otherValues.get(key);

                            PdfObject otherObj=((PdfObject)val);

                            img=rotate(decode(currentPdfFile, otherObj, formObject.getParameterConstant(PdfDictionary.Subtype)),
                                    formObject.getDictionary(PdfDictionary.MK).getInt(PdfDictionary.R));

                            if(debug)
                                System.out.println("(other) "+key+ ' ' +otherObj+ ' ' +img);

                            if(img==null)
                                continue;


                            if(values[ii]==PdfDictionary.D)	{

                                if(debug)
                                    System.out.println("D(other) set="+img+ ' ' +formObject);

                                formObject.setAppearanceImage(img, PdfDictionary.D, PdfDictionary.On);

                                if (!formObject.hasDownOff())
                                    formObject.setAppearanceImage(null, PdfDictionary.D, PdfDictionary.Off);

                            }else if(values[ii]==PdfDictionary.N){

                                formObject.setAppearanceImage(img,PdfDictionary.N,PdfDictionary.On);
                                formObject.setNormalOnState(key);

                                if(debug)
                                    System.out.println("Set N (other) "+formObject);

                                if (!formObject.hasNormalOff())
                                    formObject.setAppearanceImage(null,PdfDictionary.N,PdfDictionary.Off);


                            }else if(values[ii]==PdfDictionary.R){

                                formObject.setAppearanceImage(img,PdfDictionary.R,PdfDictionary.On);

                                if (!formObject.hasRolloverOff()) {
                                    formObject.setAppearanceImage(null,PdfDictionary.R,PdfDictionary.Off);
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    /**
     * defines actions to be executed on events 'Trigger Events'
     *
     * @Action This is where the raw data is parsed and put into the FormObject
     */
    private void resolveAdditionalAction(FormObject formObject) {

        /**
         * entries NP, PP, FP, LP never used
         * A action when pressed in active area ?some others should now be ignored?
         * E action when cursor enters active area
         * X action when cursor exits active area
         * D action when cursor button pressed inside active area
         * U action when cursor button released inside active area
         * Fo action on input focus
         * BI action when input focus lost
         * PO action when page containing is opened,
         * 	actions O of pages AA dic, and OpenAction in document catalog should be done first
         * PC action when page is closed, action C from pages AA dic follows this
         * PV action on viewing containing page
         * PI action when no longer visible in viewer
         * K action on - [javascript]
         * 	keystroke in textfield or combobox
         * 	modifys the list box selection
         * 	(can access the keystroke for validity and reject or modify)
         * F the display formatting of the field (e.g 2 decimal places) [javascript]
         * V action when fields value is changed [javascript]
         * C action when another field changes (recalculate this field) [javascript]
         */
        int idValue;

        for (int anId : id) {

            //store most actions in lookup table to make code shorter/faster
            idValue = anId;


        }
    }


    public static BufferedImage decode(PdfObjectReader currentPdfFile, PdfObject XObject, int subtype){
    	return decode(currentPdfFile, XObject, subtype,0,0,0,0,1);
    }
    
    /**
     * decode appearance stream and convert into VectorRenderObject we can redraw
     * if width and height are 0 we define the size hear
     * offsetImage - 0= no change, 1= offset, 2= invert image
     * pScaling used by HTML - set to 1 otherwise
     */
    @SuppressWarnings("UnusedAssignment")
    public static BufferedImage decode(PdfObjectReader currentPdfFile, PdfObject XObject, int subtype,
    		int width, int height,int offsetImage,int iconRotation, float pageScaling){
    	//NOTE iconRotation kept to keep api but iconRotation is never used and NOT needed

        boolean useHires=true;

    	currentPdfFile.checkResolved(XObject);
    	try{

    		/**
    		 * generate local object to decode the stream
    		 */
            ObjectStore localStore = new ObjectStore();


    		/**
    		 * create renderer object
    		 */
    		T3Renderer glyphDisplay=new T3Display(0,false,20,localStore);
            
    		//fix for hires
    		if(!useHires)
    			glyphDisplay.setOptimisedRotation(false);
    		else
            //if(useHires)
                glyphDisplay.setHiResImageForDisplayMode(useHires);


            PdfStreamDecoder glyphDecoder=new PdfStreamDecoder(currentPdfFile,useHires,null); //switch to hires as well
            glyphDecoder.setParameters(false,true,15,0);

            glyphDecoder.setIntValue(ValueTypes.StreamType, ValueTypes.FORM);

            glyphDecoder.setBooleanValue(ValueTypes.XFormFlattening, true);
            glyphDecoder.setObjectValue(ValueTypes.ObjectStore,localStore);


            glyphDecoder.setObjectValue(ValueTypes.DynamicVectorRenderer,glyphDisplay);

    		/**read any resources*/
    		try{
    			PdfObject Resources =XObject.getDictionary(PdfDictionary.Resources);
    			if (Resources != null)
    				glyphDecoder.readResources(Resources,false);

    		}catch(Exception e){
    			System.out.println("Exception "+e+" reading resources in XForm");
                //tell user and log
                if(LogWriter.isOutput())
                    LogWriter.writeLog("Exception: "+e.getMessage());
    		}

            /**decode the stream*/
    		byte[] commands=XObject.getDecodedStream();

    		
            if(commands!=null)
    			glyphDecoder.decodeStreamIntoObjects(commands,false);


    		boolean ignoreColors=glyphDecoder.ignoreColors;

    		localStore.flush();

    		org.jpedal.fonts.glyph.T3Glyph form= new org.jpedal.fonts.glyph.T3Glyph(glyphDisplay, 0,0,ignoreColors,"");

    		float[] BBox=XObject.getFloatArray(PdfDictionary.BBox);
    		
            float scaling;
    		
    		float rectX1=0,rectY1=0;
    		if(BBox!=null){

                for(int ii=0;ii<4;ii++)
                    BBox[ii]=BBox[ii]*pageScaling;

    			rectX1 = (BBox[0]);
    			rectY1 = (BBox[1]);
				int boxWidth=(int) (((BBox[2]+0.5f)-BBox[0]));
				if(boxWidth<0)
					boxWidth = -boxWidth;
				
				int boxHeight=(int) (((BBox[3]+0.5f)-BBox[1]));
				if(boxHeight<0)
					boxHeight = -boxHeight;

    			if(boxWidth==0 && boxHeight>0)
    				boxWidth=1;
    			if(boxWidth>0 && boxHeight==0)
    				boxHeight=1;
    			
    			//if the width and height scaling are miles apart then the width and height 
   				//are probably the wrong way round so swap them. and recalc the scalings.
    			float ws = ((float)width)/((float)boxWidth);
				float hs = ((float)height)/((float)boxHeight);
				
				//check if dimensions are correct and alter if not
				float diff = ws-hs;
				int diffInt = (int)diff;
				if(diffInt!=0){
					//NOTE width and height sent in need to be rotated
					//as they are not as the image is drawn
					int tmpI = width;
					width = height;
					height = tmpI;
					
					ws = ((float)width)/((float)boxWidth);
					hs = ((float)height)/((float)boxHeight);
				}
				//NOTE now we re set the width and height to scaled 
				//value of Bounding box to keep the orientation
				
				//if scaling less than 1 use 1
				if(ws<1 || hs<1){
					scaling = 1;
					width = boxWidth;
					height = boxHeight;
				}else {
					//use larger scaling as will produce better image
					if(ws>hs){
						scaling = ws;
						height = (int) (boxHeight*scaling);
					}else {
						scaling = hs;
						width = (int) (boxWidth*scaling);
					}
					
					//make sure image position is scaled
					rectX1*=scaling;
					rectY1*=scaling;
				}
				
    		}else {

    			float defaultSize = 20;
    			if(height<defaultSize)
    				height = (int)defaultSize;
    			if(width<defaultSize)
    				width = (int)defaultSize;
    			
				float ws = ((float)width)/defaultSize;
				float hs = ((float)height)/defaultSize;
				if(ws>hs){
					scaling = ws;
					height=(int)(defaultSize*scaling);
				}else {
					scaling = hs;
					width=(int)(defaultSize*scaling);
				}
				
				//make sure image position is scaled
				rectX1*=scaling;
				rectY1*=scaling;
    		}
    		
    		if(width==0 || height==0)
    			return null;
    		
    		if(scaling>1){
	    		height+=2;
	    		width+=2;
	    	}
    		
    		//if offset
    		if(offsetImage==1){
	    		width+=2;
	    		height+=2;
    		}
    		
    		BufferedImage aa;
    		Graphics2D g2;
    		
            float a,b,c,d,e,f;
            int offset=height;

            float[] matrix=XObject.getFloatArray(PdfDictionary.Matrix);

            if(matrix!=null){

    			a = matrix[0];
    			b = matrix[1];
    			c = matrix[2];
    			d = matrix[3];
    			//scale so they offset correctly
    			e = matrix[4]*scaling*pageScaling;
    			f = matrix[5]*scaling*pageScaling;

    			if(c!=0){
    				aa=new BufferedImage(height,width,BufferedImage.TYPE_INT_ARGB);
    				offset=width;
    			}else{
    				aa=new BufferedImage(width,height,BufferedImage.TYPE_INT_ARGB);
    				if(b<0){
    					//already set at this
//	    				if(e!=0f)
//	    					e = matrix[4];
//	    				if(f!=0f)
//	    					f = matrix[5];
    				}else {
    					//rectX1 and rectY1 already have the scaling applied
	    				if(e!=0f)
	    					e = -rectX1;
	    				if(f!=0f)
	    					f = -rectY1;
    				}
    				
    			}
    			
    			g2=(Graphics2D) aa.getGraphics();
    		    AffineTransform flip=new AffineTransform();
    			flip.translate(0, offset);
    			flip.scale(1, -1);
    			g2.setTransform(flip);

    			if(debug)
    				System.out.println(" rectX1 = "+rectX1+" rectY1 = "+rectY1+" width = "+width+" height = "+height);

    			AffineTransform affineTransform = new AffineTransform(a,b,c,d,e,f);
//				System.out.println("BBox="+ConvertToString.convertArrayToString(BBox));
//				System.out.println("matrix="+ConvertToString.convertArrayToString(matrix));
//				System.out.println("affine="+affineTransform);
    			g2.transform(affineTransform);
    		} else {
    			aa=new BufferedImage(width,height,BufferedImage.TYPE_INT_ARGB);
    			
    			g2=(Graphics2D) aa.getGraphics();

    			AffineTransform flip=new AffineTransform();
    			flip.translate(0, offset);
    			flip.scale(1, -1);
    			g2.setTransform(flip);
    		}

            if(offsetImage==2)//invert
            	g2.scale(-1,-1);
            else if(offsetImage==1)//offset
            	g2.translate(1,1);
            
            //add transparency for highlights
            if(subtype==PdfDictionary.Highlight)
            g2.setComposite( AlphaComposite.getInstance( AlphaComposite.SRC_OVER, 0.5f ) );

            //carry the sclaing through to the render method
            form.render(0,g2, scaling*pageScaling,true);

    		g2.dispose();

            return aa;

    	}catch(Exception e){
            //tell user and log
            if(LogWriter.isOutput())
                LogWriter.writeLog("Exception: "+e.getMessage());
    		return null;
    	}catch(Error e){
            //tell user and log
            if(LogWriter.isOutput())
                LogWriter.writeLog("Error: "+e.getMessage());

            if (ExternalHandlers.throwMissingCIDError && e.getMessage().contains("kochi"))
                throw e;

    		return null;
    	}
    }
    
    


    /**
     * method to rotate an image through a given angle
     * @param src the source image
     * @param rotation the angle to rotate the image through
     * @return the rotated image
     */
    public static BufferedImage rotate(BufferedImage src, int rotation) {
        BufferedImage dst;

        if(src == null)
            return null;

        //if angle is 0 we dont need to do anything
        if(rotation==0)
        	return src;
        
        
        try{
        	double angle = rotation * Math.PI / 180;
        	
            int w = src.getWidth();
            int h = src.getHeight();
            int newW = (int)(Math.round(h * Math.abs(Math.sin(angle))+w * Math.abs(Math.cos(angle))));
            int newH = (int)(Math.round(h * Math.abs(Math.cos(angle))+w * Math.abs(Math.sin(angle))));
            AffineTransform at = AffineTransform.getTranslateInstance((newW-w)/2,(newH-h)/2);
            at.rotate(angle, w/2, h/2);

            dst = new BufferedImage(newW,newH,BufferedImage.TYPE_INT_ARGB);
            Graphics2D g2 = dst.createGraphics();
            g2.drawRenderedImage(src, at);

            g2.dispose();
        }catch(Error e){
            dst=null;
        }
        return dst;
    }

     /**/
    /**
     * join the dots and save the image for the inklist annot
     *
     * NO exmaple found i switchover so moved into here for if we need
     *
    private static void commandInkList(Object field, FormObject formObject) {
        //the ink list join the dots array
        if(debug)
            System.out.println("inklist array="+field);

        //current shape object being drawn note we pass in handle on pageLines
        PdfShape currentDrawShape=new PdfShape();
        Rectangle rect = formObject.getBoundingRectangle();

        String paths = (String)field;
        StringTokenizer tok = new StringTokenizer(paths,"[] ",true);
        int countArrays=0;
        boolean isFirstPoint = false;
        String next,first=null,second=null;
        while(tok.hasMoreTokens()){
            next = tok.nextToken();
            if(next.equals("[")){
                countArrays++;
                isFirstPoint = true;
                continue;
            }else if(next.equals("]")){
                countArrays--;
                continue;
            }else if(next.equals(" ")){
                continue;
            }else {
                if(first!=null){
                    second = next;
                }else {
                    first = next;
                    continue;
                }
            }

            if(isFirstPoint){
                currentDrawShape.moveTo(Float.parseFloat(first)-rect.x,Float.parseFloat(second)-rect.y);
                isFirstPoint = false;
            }else{
                currentDrawShape.lineTo(Float.parseFloat(first)-rect.x,Float.parseFloat(second)-rect.y);
            }

            first = null;
        }
//          close for s command
//            currentDrawShape.closeShape();

        org.jpedal.objects.GraphicsState currentGraphicsState= new org.jpedal.objects.GraphicsState();

        Shape currentShape =
                currentDrawShape.generateShapeFromPath(null,
                        currentGraphicsState.CTM,
                        false,null,false,null,currentGraphicsState.getLineWidth(),0);

        Stroke inkStroke = currentGraphicsState.getStroke();

        BufferedImage image = new BufferedImage(rect.width,rect.height,BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = (Graphics2D)image.getGraphics();
        g2.setStroke(inkStroke);
        g2.setColor(Color.red);
        g2.scale(1,-1);
        g2.translate(0,-image.getHeight());
        g2.draw(currentShape);

        g2.dispose();

        formObject.setAppearanceImage(image,PdfDictionary.N,PdfDictionary.Off);
    }/**/

    //////code from DecodeCommand in AnnotStream
    /**{
     //the ink list join the dots array

     //current shape object being drawn note we pass in handle on pageLines
     PdfShape currentDrawShape=new PdfShape();
     Rectangle rect = formObject.getBoundingRectangle();

     String paths = Strip.removeArrayDeleminators((String)field);
     StringTokenizer tok = new StringTokenizer(paths,"[] ",true);

     boolean isFirstPoint = false;
     String next,first=null,second=null;
     while(tok.hasMoreTokens()){
     next = tok.nextToken();
     if(next.equals("[")){
     isFirstPoint = true;
     continue;
     }else if(next.equals("]")){
     continue;
     }else if(next.equals(" ")){
     continue;
     }else {
     if(first!=null){
     second = next;
     }else {
     first = next;
     continue;
     }
     }

     if(isFirstPoint){
     currentDrawShape.moveTo(Float.parseFloat(first)-rect.x,Float.parseFloat(second)-rect.y);
     isFirstPoint = false;
     }else{
     currentDrawShape.lineTo(Float.parseFloat(first)-rect.x,Float.parseFloat(second)-rect.y);
     }

     first = null;
     }
     //          close for s command
     //            currentDrawShape.closeShape();

     org.jpedal.objects.GraphicsState currentGraphicsState=formObject.getGraphicsState();

     Shape currentShape =
     currentDrawShape.generateShapeFromPath(null,
     currentGraphicsState.CTM,
     false,null,false,null,currentGraphicsState.getLineWidth(),0);

     Stroke inkStroke = currentGraphicsState.getStroke();

     BufferedImage image = new BufferedImage(rect.width,rect.height,BufferedImage.TYPE_INT_ARGB);
     Graphics2D g2 = (Graphics2D)image.getGraphics();
     g2.setStroke(inkStroke);
     g2.setColor(Color.red);
     g2.scale(1,-1);
     g2.translate(0,-image.getHeight());
     g2.draw(currentShape);

     g2.dispose();

     //ShowGUIMessage.showGUIMessage("path draw",image,"path drawn");

     formObject.setNormalAppOff(image,null);

     //        }else if(command.equals("RD")){
     //            //rectangle differences left top right bottom order as recieved
     //            //the bounds of the internal object, in from the Rect
     //
     //            StringTokenizer tok = new StringTokenizer(Strip.removeArrayDeleminators((String)field));
     //            float left = Float.parseFloat(tok.nextToken());
     //            float top = Float.parseFloat(tok.nextToken());
     //            float right = Float.parseFloat(tok.nextToken());
     //            float bottom = Float.parseFloat(tok.nextToken());
     //
     //            formObject.setInternalBounds(left,top,right,bottom);
     //        }else {
     //        	notFound = true;
     }
     /**/
    public boolean hasXFADataSet() {
        return false;
    }

    /**
     * takes the PDF commands and creates a font
     */
    public static void decodeFontCommandObj(String fontStream,FormObject formObject){

        //now parse the stream into a sequence of tokens
        StringTokenizer tokens=new StringTokenizer(fontStream,"() []");
        int tokenCount=tokens.countTokens();
        String[] tokenValues=new String[tokenCount];
        int i=0;
        while(tokens.hasMoreTokens()){
            tokenValues[i]=tokens.nextToken();
            i++;
        }

        //now work out what it does and build up info
        for(i=tokenCount-1;i>-1;i--){
//			System.out.println(tokenValues[i]+" "+i);

            //look for commands
            if(tokenValues[i].equals("g")){ //set color (takes 1 values
                i--;
                float col=0;
                try{
                    col=Float.parseFloat(handleComma(tokenValues[i]));
                }catch(Exception e){
                    LogWriter.writeLog("Error in generating g value "+tokenValues[i]);
                }

                formObject.setTextColor(new float[]{col});

            }else if(tokenValues[i].equals("Tf")){ //set font (takes 2 values - size and font
                i--;
                int textSize=8;
                try{
                    textSize=(int) Float.parseFloat(handleComma(tokenValues[i]));
//					if(textSize==0)
//						textSize = 0;//TODO check for 0 sizes CHANGE size to best fit on 0
                }catch(Exception e){
                    LogWriter.writeLog("Error in generating Tf size "+tokenValues[i]);
                }

                i--;//decriment for font name
                String font=null;
                try{
                    font=tokenValues[i];
                    if(font.startsWith("/"))
                        font = font.substring(1);
                }catch(Exception e){
                    LogWriter.writeLog("Error in generating Tf font "+tokenValues[i]);
                }

                PdfFont currentFont=new PdfFont();

                formObject.setTextFont(currentFont.setFont(font, textSize));

                formObject.setTextSize(textSize);

            }else if(tokenValues[i].equals("rg") || tokenValues[i].equals("r")){
                i--;
                float b=Float.parseFloat(handleComma(tokenValues[i]));
                i--;
                float g=Float.parseFloat(handleComma(tokenValues[i]));
                i--;
                float r=Float.parseFloat(handleComma(tokenValues[i]));

                formObject.setTextColor(new float[]{r,g,b});

//				if(debug)
//					System.out.println("rgb="+r+","+g+","+b+" rg     CHECK ="+currentAt.textColor);

            }else if(tokenValues[i].equals("Sig")){
                LogWriter.writeFormLog("Sig-  UNIMPLEMENTED="+fontStream+"< "+i,debugUnimplemented);
            }else if(tokenValues[i].equals("\\n")){
                //ignore \n
                if(debug)
                    System.out.println("ignore \\n");
            }else {
                if(!showFontMessage){
                    showFontMessage=true;
                    LogWriter.writeFormLog("{stream} Unknown FONT command "+tokenValues[i]+ ' ' +i+" string="+fontStream,debugUnimplemented);

                }
            }
        }
    }

    private static String handleComma(String tokenValue) {

        //if comma used as full stop remove
        int comma=tokenValue.indexOf(',');
        if(comma!=-1)
        tokenValue=tokenValue.substring(0,comma);

        return tokenValue;
    }
}
