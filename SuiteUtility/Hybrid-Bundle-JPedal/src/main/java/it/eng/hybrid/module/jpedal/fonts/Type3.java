/**
* ===========================================
* Java Pdf Extraction Decoding Access Library
* ===========================================
*
* Project Info:  http://www.jpedal.org
* (C) Copyright 1997-2008, IDRsolutions and Contributors.
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
* Type3.java
* ---------------
*/
package it.eng.hybrid.module.jpedal.fonts;

import it.eng.hybrid.module.jpedal.exception.PdfException;
import it.eng.hybrid.module.jpedal.fonts.glyph.T3Glyph;
import it.eng.hybrid.module.jpedal.fonts.glyph.T3Glyphs;
import it.eng.hybrid.module.jpedal.fonts.glyph.T3Size;
import it.eng.hybrid.module.jpedal.io.ObjectStore;
import it.eng.hybrid.module.jpedal.io.PdfObjectReader;
import it.eng.hybrid.module.jpedal.objects.GraphicsState;
import it.eng.hybrid.module.jpedal.objects.raw.PdfDictionary;
import it.eng.hybrid.module.jpedal.objects.raw.PdfKeyPairsIterator;
import it.eng.hybrid.module.jpedal.objects.raw.PdfObject;
import it.eng.hybrid.module.jpedal.parser.T3StreamDecoder;
import it.eng.hybrid.module.jpedal.render.T3Display;
import it.eng.hybrid.module.jpedal.render.T3Renderer;
import it.eng.hybrid.module.jpedal.viewer.ValueTypes;

import java.util.Map;

import org.apache.log4j.Logger;

/**
 * handles type1 specifics
 */
public class Type3 extends PdfFont {

	public final static Logger logger = Logger.getLogger(Type3.class);
	
	/**handle onto GS so we can read color*/
	private GraphicsState currentGraphicsState=new GraphicsState();

    /**allow us to track if user is printing this font - needed for type3*/
    private boolean isPrinting;

    /** get handles onto Reader so we can access the file
     * @param current_pdf_file - handle to PDF file
     **/
	public Type3(PdfObjectReader current_pdf_file, boolean isPrinting) {

			glyphs=new T3Glyphs();

            this.isPrinting=isPrinting;

			init(current_pdf_file);

	}


	/**read in a font and its details from the pdf file*/
	final public void createFont(PdfObject pdfObject, String fontID, boolean renderPage, ObjectStore objectStore, Map substitutedFonts) throws Exception{

		fontTypes=StandardFonts.TYPE3;
		
		//generic setup
		init(fontID, renderPage);
		
		/**
		 * get FontDescriptor object - if present contains metrics on glyphs
		 */
		PdfObject pdfFontDescriptor=pdfObject.getDictionary(PdfDictionary.FontDescriptor);
		
		// get any dimensions if present (note FBoundBox if in pdfObject not Descriptor)
		setBoundsAndMatrix(pdfObject);
		
		setName(pdfObject, fontID);
		setEncoding(pdfObject, pdfFontDescriptor);
		
		readWidths(pdfObject,false);

		readEmbeddedFont(pdfObject,objectStore);

		//make sure a font set
		if (renderPage)
			setFont(getBaseFontName(), 1);

	}


    private void readEmbeddedFont(PdfObject pdfObject, ObjectStore objectStore) {

        final boolean hires=true;
        
        int key,otherKey;

        PdfObject CharProcs=pdfObject.getDictionary(PdfDictionary.CharProcs);

        //handle type 3 charProcs and store for later lookup
        if(CharProcs!=null){

            T3StreamDecoder glyphDecoder=new T3StreamDecoder(currentPdfFile,hires, isPrinting);
            glyphDecoder.setParameters(false,true,7,0);

            glyphDecoder.setObjectValue(ValueTypes.ObjectStore, objectStore);

            PdfObject Resources=pdfObject.getDictionary(PdfDictionary.Resources);
            if(Resources!=null){
                try {
                    glyphDecoder.readResources(Resources,false);
                } catch (PdfException e) {
                       logger.info("Exception: "+e.getMessage());
                }
            }

            /**
             * read all the key pairs for Glyphs
             */
            PdfKeyPairsIterator keyPairs=CharProcs.getKeyPairsIterator();

            String glyphKey,pKey;
            PdfObject glyphObj;
            
            while(keyPairs.hasMorePairs()){

                glyphKey=keyPairs.getNextKeyAsString();                
                glyphObj=keyPairs.getNextValueAsDictionary();

                Object diffValue=null;
                if(diffLookup!=null){
                	pKey= StandardFonts.convertNumberToGlyph(glyphKey, containsHexNumbers, allNumbers);
                    diffValue=diffLookup.get(pKey);
                }

                //decode and store in array
                if(glyphObj!=null && renderPage){

                    //decode and create graphic of glyph
                    T3Renderer glyphDisplay=new T3Display(0,false,20,objectStore);

                    glyphDisplay.setHiResImageForDisplayMode(hires);
                    glyphDisplay.setType3Glyph(glyphKey);
                    
                    try{
                        glyphDecoder.setObjectValue(ValueTypes.DynamicVectorRenderer,glyphDisplay);
                        glyphDecoder.setDefaultColors(currentGraphicsState.getNonstrokeColor(),currentGraphicsState.getNonstrokeColor());
                        int  renderX,renderY;
                    

                        //if size is 1 we need to scale up so we can see
                        int factor=1;
                        double[] fontMatrix=pdfObject.getDoubleArray(PdfDictionary.FontMatrix);
                        if(fontMatrix!=null && fontMatrix[0]==1 && (fontMatrix[3]==1 || fontMatrix[3]==-1))
                        factor=10;
                        
                        GraphicsState gs=new GraphicsState(0,0);
                        gs.CTM = new float[][]{{factor,0,0},
                                {0,factor,0},{0,0,1}
                        };

                        T3Size t3 = glyphDecoder.decodePageContent(glyphObj, gs);


                        renderX=t3.x;
                        renderY=t3.y;

                        //allow for rotated on page in case swapped
                        if(renderX==0 && renderY!=0){
                            renderX=t3.y;
                            renderY=t3.x;                                
                        }

                        T3Glyph glyph=new T3Glyph(glyphDisplay, renderX,renderY,glyphDecoder.ignoreColors,glyphKey);
                        glyph.setScaling(1f/factor);

                        otherKey=-1;
                        if(diffValue!=null){
                        	key= (Integer) diffValue;
                        	
                        	if(keyPairs.isNextKeyANumber())
                        	otherKey=keyPairs.getNextKeyAsNumber();
                        }else
                        	key=keyPairs.getNextKeyAsNumber();

                        glyphs.setT3Glyph(key,otherKey, glyph);

                    }catch(Exception e){
                    	e.printStackTrace();
                    	
                    	//if(logger.info.isOutput())
                    		logger.info("Exception "+e+" is Type3 font code");
                    }
                } 
                
              //roll on
              keyPairs.nextPair();    
            }
            isFontEmbedded = true;
        }
    }
}
