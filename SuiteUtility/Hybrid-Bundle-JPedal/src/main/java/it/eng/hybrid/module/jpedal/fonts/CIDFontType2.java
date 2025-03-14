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
* CIDFontType2.java
* ---------------
*/
package it.eng.hybrid.module.jpedal.fonts;

import it.eng.hybrid.module.jpedal.fonts.tt.TTGlyphs;
import it.eng.hybrid.module.jpedal.io.ObjectStore;
import it.eng.hybrid.module.jpedal.io.PdfObjectReader;
import it.eng.hybrid.module.jpedal.objects.raw.PdfDictionary;
import it.eng.hybrid.module.jpedal.objects.raw.PdfObject;
import it.eng.hybrid.module.jpedal.parser.PdfFontFactory;
import it.eng.hybrid.module.jpedal.ui.JPedalApplication;

import java.util.Map;

import org.apache.log4j.Logger;


/**
 * handles truetype specifics
 *  */
public class CIDFontType2 extends TrueType {

	public final static Logger logger = Logger.getLogger(CIDFontType2.class);
	
	/**get handles onto Reader so we can access the file*/
	public CIDFontType2(PdfObjectReader currentPdfFile, String substituteFontFile) {

		isCIDFont=true;
		TTstreamisCID=true;

		glyphs=new TTGlyphs();

		init(currentPdfFile);

        this.substituteFontFile=substituteFontFile;

    }

	/**get handles onto Reader so we can access the file*/
	public CIDFontType2(PdfObjectReader currentPdfFile,boolean ttflag) {

		isCIDFont=true;
		TTstreamisCID=ttflag;

		glyphs=new TTGlyphs();
		
		init(currentPdfFile);

	}

	/**read in a font and its details from the pdf file*/
	public void createFont(PdfObject pdfObject, String fontID, boolean renderPage, ObjectStore objectStore, Map substitutedFonts) throws Exception{

		fontTypes=StandardFonts.CIDTYPE2;
		this.fontID=fontID;
		
		PdfObject Descendent=pdfObject.getDictionary(PdfDictionary.DescendantFonts);
        PdfObject pdfFontDescriptor=Descendent.getDictionary(PdfDictionary.FontDescriptor);

		createCIDFont(pdfObject,Descendent);

		if (pdfFontDescriptor!= null) {

            byte[] stream;
            PdfObject FontFile2=pdfFontDescriptor.getDictionary(PdfDictionary.FontFile2);
            if(FontFile2!=null){
                stream=currentPdfFile.readStream(FontFile2,true,true,false, false,false, FontFile2.getCacheName(currentPdfFile.getObjectReader()));

            	if(stream!=null)
            		readEmbeddedFont(stream,null,hasEncoding, false);
			}
		}


        //allow for corrupted
        boolean isCorrupt=glyphs.isCorrupted();

        if(glyphs.isCorrupted()){

            PdfFontFactory pdfFontFactory =new PdfFontFactory(currentPdfFile);
            pdfFontFactory.getFontSub(getBaseFontName());
            isFontEmbedded=false;

            substituteFontFile= pdfFontFactory.getMapFont();

        }

		//setup and substitute font
        if(renderPage && !isFontEmbedded && substituteFontFile!=null){
            this.substituteFontUsed(substituteFontFile);
			isFontSubstituted=true;
			this.isFontEmbedded=true;

            glyphs.setFontEmbedded(true);
        }

        //make sure a font set
        if (renderPage)
            setFont(getBaseFontName(), 1);

        if(!isFontEmbedded)
            selectDefaultFont();

        glyphs.setCorrupted(isCorrupt);

	}
}
