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
* TrueType.java
* ---------------
*/
package it.eng.hybrid.module.jpedal.fonts;

import it.eng.hybrid.module.jpedal.exception.PdfFontException;
import it.eng.hybrid.module.jpedal.external.ExternalHandlers;
import it.eng.hybrid.module.jpedal.fonts.glyph.PdfJavaGlyphs;
import it.eng.hybrid.module.jpedal.fonts.tt.TTGlyphs;
import it.eng.hybrid.module.jpedal.io.ObjectStore;
import it.eng.hybrid.module.jpedal.io.PdfObjectReader;
import it.eng.hybrid.module.jpedal.objects.raw.PdfDictionary;
import it.eng.hybrid.module.jpedal.objects.raw.PdfObject;
import it.eng.hybrid.module.jpedal.ui.JPedalApplication;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.awt.*;

import org.apache.log4j.Logger;

/**
 * handles truetype specifics
 *  */
public class  TrueType extends PdfFont {

	public final static Logger logger = Logger.getLogger(TrueType.class);
	
    private boolean subfontAlreadyLoaded;
    private Map fontsLoaded;

    private Rectangle BBox=null;

	TrueType(){

	}

    private void readFontData(byte[] fontDataAsArray, FontData fontData){

        if(subfontAlreadyLoaded){
            glyphs= (PdfJavaGlyphs) fontsLoaded.get(substituteFont+'_'+glyphs.getBaseFontName()+' '+fontDataAsArray.length);

            fontTypes=glyphs.getType();
        }else{

            if(!isCIDFont){
                if(fontDataAsArray!=null){
                    fontsLoaded.put(substituteFont+'_'+glyphs.getBaseFontName()+' '+fontDataAsArray.length,glyphs);
                }
            }

		fontTypes=glyphs.readEmbeddedFont(TTstreamisCID,fontDataAsArray, fontData);
        }
        //does not see to be accurate on all PDFs tested
        //this.FontBBox=glyphs.getFontBoundingBox();

    }

	/**allows us to substitute a font to use for display
	 * @throws PdfFontException */
	protected void substituteFontUsed(String substituteFontFile) throws PdfFontException{

		InputStream from=null;

		//process the font data
		try {

            //try in jar first
            from =loader.getResourceAsStream("it/eng/hybrid/module/jpedal/fonts/res/" + substituteFontFile);

            //try as straight file
            if(from==null)
            from =new FileInputStream(substituteFontFile);

        } catch (Exception e) {
			System.err.println("Exception " + e + " reading "+substituteFontFile+" Check cid  jar installed");
			logger.info("Exception " + e + " reading "+substituteFontFile+" Check cid  jar installed");

            if(ExternalHandlers.throwMissingCIDError && e.getMessage().contains("kochi"))
                throw new Error(e);
		}

		if(from==null)
			throw new PdfFontException("Unable to load font "+substituteFontFile);

		try{

			//create streams
			ByteArrayOutputStream to = new ByteArrayOutputStream();

			//write
			byte[] buffer = new byte[65535];
			int bytes_read;
			while ((bytes_read = from.read(buffer)) != -1)
				to.write(buffer, 0, bytes_read);

			to.close();
			from.close();

            FontData fontData=null;//new FontData(to.toByteArray());

            readFontData(to.toByteArray(),fontData);

            glyphs.setEncodingToUse(hasEncoding,this.getFontEncoding(false),true,isCIDFont);

			isFontEmbedded=true;

		} catch (Exception e) {
			System.err.println("Exception " + e + " reading "+substituteFontFile+" Check cid  jar installed");
			
			logger.info("Exception " + e + " reading "+substituteFontFile+" Check cid  jar installed");

            if(ExternalHandlers.throwMissingCIDError && e.getMessage().contains("kochi"))
                throw new Error(e);
		}

	}

    public TrueType(byte[] rawFontData, PdfJavaGlyphs glyphs) {


        fontsLoaded=new HashMap();

        init(null);

        //this.substituteFont=substituteFont;

        //this.rawFontData=rawFontData;
    }


    /**entry point when using generic renderer*/
	public TrueType(String substituteFont) {

		glyphs=new TTGlyphs();
		
		fontsLoaded=new HashMap();

        init(null);

        this.substituteFont=substituteFont;

	}

    /**get handles onto Reader so we can access the file*/
	public TrueType(PdfObjectReader current_pdf_file,String substituteFont) {

        glyphs=new TTGlyphs();

			init(current_pdf_file);
			this.substituteFont=substituteFont;

	}

	/**read in a font and its details from the pdf file*/
	public void createFont(PdfObject pdfObject, String fontID, boolean renderPage, ObjectStore objectStore, Map substitutedFonts) throws Exception{

		fontTypes=StandardFonts.TRUETYPE;

        this.fontsLoaded=substitutedFonts;

		//generic setup
		init(fontID, renderPage);

		/**
		 * get FontDescriptor object - if present contains metrics on glyphs
		 */
		PdfObject pdfFontDescriptor=pdfObject.getDictionary(PdfDictionary.FontDescriptor);

		setBoundsAndMatrix(pdfFontDescriptor);

		setName(pdfObject, fontID);
		setEncoding(pdfObject, pdfFontDescriptor);

		if(renderPage){

            if (pdfFontDescriptor!= null && substituteFont==null) {

                byte[] stream=null;
                PdfObject FontFile2=pdfFontDescriptor.getDictionary(PdfDictionary.FontFile2);

                //allow for wrong types used (Acrobat does not care so neither do we)
                if(FontFile2==null){
                    FontFile2=pdfFontDescriptor.getDictionary(PdfDictionary.FontFile);

                    if(FontFile2==null){
                        FontFile2=pdfFontDescriptor.getDictionary(PdfDictionary.FontFile3);
                    }
                }

                if(FontFile2!=null){
                    stream=currentPdfFile.readStream(FontFile2,true,true,false, false,false, FontFile2.getCacheName(currentPdfFile.getObjectReader()));
                }

                if(stream!=null){
                	readEmbeddedFont(stream,null,hasEncoding, false);
                }
			}

			if(!isFontEmbedded && substituteFont!=null){

				//over-ride font remapping if substituted
				if(glyphs.remapFont)
					glyphs.remapFont=false;

                subfontAlreadyLoaded= !isCIDFont && fontsLoaded.containsKey(substituteFont+'_'+glyphs.getBaseFontName());

                File fontFile;
                FontData fontData=null;
                int objSize=0;

				/**
				 * see if we cache or read
				 */
                if(!subfontAlreadyLoaded){
                    fontFile=new File(substituteFont);

				    objSize=(int)fontFile.length();
                }

				if(FontData.maxSizeAllowedInMemory>=0 && objSize>FontData.maxSizeAllowedInMemory){

                    if(!subfontAlreadyLoaded)
                    fontData=new FontData(substituteFont);

					readEmbeddedFont(null,fontData,false,true);
				}else if(subfontAlreadyLoaded){
                    readEmbeddedFont(null,null,false,true);
				}else{

					//read details
					BufferedInputStream from;

					InputStream jarFile = null;
                    try{
                        if(substituteFont.startsWith("jar:")|| substituteFont.startsWith("http:"))
					        jarFile = loader.getResourceAsStream(substituteFont);
                        else
					        jarFile = loader.getResourceAsStream("file:///"+substituteFont);

                    }catch(Exception e){
                    	logger.info("1.Unable to open "+substituteFont);
                    }catch(Error err){
                    	logger.info("1.Unable to open "+substituteFont);
                    }
                    
					if(jarFile==null)
						from=new BufferedInputStream(new FileInputStream(substituteFont));
					else
						from= new BufferedInputStream(jarFile);

					//create streams
					ByteArrayOutputStream to = new ByteArrayOutputStream();

					//write
					byte[] buffer = new byte[65535];
					int bytes_read;
					while ((bytes_read = from.read(buffer)) != -1)
						to.write(buffer, 0, bytes_read);

					to.close();
					from.close();

					readEmbeddedFont(to.toByteArray(),null,false,true);
				}

				isFontSubstituted=true;

			}
		}

		readWidths(pdfObject,true);
		
		//make sure a font set
		if (renderPage)
			setFont(glyphs.fontName, 1);

        glyphs.setDiffValues(diffTable);

	}
    
    /**read in a font and its details for generic usage*/
	public void createFont(String fontName) throws Exception{

		fontTypes=StandardFonts.TRUETYPE;

		setBaseFontName(fontName);
		
		 /**
         * see if we cache or read
         */
        File fontFile=new File(substituteFont);
        
        int objSize=(int)fontFile.length();

		if(FontData.maxSizeAllowedInMemory>=0 && objSize>FontData.maxSizeAllowedInMemory){
        	FontData fontData=new FontData(substituteFont);

            readEmbeddedFont(null,fontData,false,true);
        }else{
			//read details
			BufferedInputStream from;
	
			InputStream jarFile = null;
            try{
                if(substituteFont.startsWith("jar:")|| substituteFont.startsWith("http:"))                        
                    jarFile = loader.getResourceAsStream(substituteFont);
                else
                    jarFile = loader.getResourceAsStream("file:///"+substituteFont);

            }catch(Exception e){
            	logger.info("2.Unable to open "+substituteFont);
            }catch(Error err){
            	logger.info("2.Unable to open "+substituteFont);
            }

			if(jarFile==null)
				from=new BufferedInputStream(new FileInputStream(substituteFont));
			else
				from= new BufferedInputStream(jarFile);
	
			//create streams
			ByteArrayOutputStream to = new ByteArrayOutputStream();
	
			//write
			byte[] buffer = new byte[65535];
			int bytes_read;
			while ((bytes_read = from.read(buffer)) != -1)
				to.write(buffer, 0, bytes_read);
	
			to.close();
			from.close();
	
	        readEmbeddedFont(to.toByteArray(),null,false,true);
        }
		
		isFontSubstituted=true;

        glyphs.setDiffValues(diffTable);

	}

	/**
	 * read truetype font data and also install font onto System
	 * so we can use
	 */
	final protected void readEmbeddedFont(byte[] fontDataAsArray, FontData fontDataAsObject,boolean hasEncoding,boolean isSubstituted) {

		//process the font data
		try {

			logger.info("Embedded TrueType font used");

			readFontData(fontDataAsArray,fontDataAsObject);

			isFontEmbedded=true;

            glyphs.setFontEmbedded(true);

            glyphs.setEncodingToUse(hasEncoding,this.getFontEncoding(false),isSubstituted,TTstreamisCID);

		} catch (Exception e) {

            isFontEmbedded=false;

            logger.info("Exception " + e + " processing TrueType font");
        }
	}

    /**
         * get bounding box to highlight
         * @return
         */
        public Rectangle getBoundingBox() {

            if(BBox==null){
                if(isFontEmbedded && !isFontSubstituted)
                    BBox=new Rectangle((int)FontBBox[0], (int)FontBBox[1], (int)(FontBBox[2]-FontBBox[0]), (int)(FontBBox[3]-FontBBox[1]));  //To change body of created methods use File | Settings | File Templates.
                else
                    BBox=super.getBoundingBox();
            }

            return BBox;
        }


}
