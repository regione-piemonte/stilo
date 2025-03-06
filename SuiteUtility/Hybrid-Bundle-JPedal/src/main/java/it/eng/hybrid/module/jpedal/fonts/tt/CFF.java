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
* CFF.java
* ---------------
*/
package it.eng.hybrid.module.jpedal.fonts.tt;

import org.apache.log4j.Logger;

import it.eng.hybrid.module.jpedal.fonts.Type1C;
import it.eng.hybrid.module.jpedal.fonts.glyph.GlyphFactory;
import it.eng.hybrid.module.jpedal.fonts.glyph.PdfGlyph;
import it.eng.hybrid.module.jpedal.fonts.glyph.PdfJavaGlyphs;
import it.eng.hybrid.module.jpedal.fonts.glyph.T1Glyphs;
import it.eng.hybrid.module.jpedal.ui.JPedalApplication;

public class CFF extends Table {

	public final static Logger logger = Logger.getLogger(CFF.class);
	
    PdfJavaGlyphs glyphs;

    boolean hasCFFdata=false;

    public CFF(FontFile2 currentFontFile,boolean isCID){

        glyphs=new T1Glyphs(isCID);
        if(isCID)
        glyphs.init(65536,true);
        
        //move to start and check exists
		int startPointer=currentFontFile.selectTable(FontFile2.CFF);

        //read 'cff' table
		if(startPointer!=0){

            try {
                int length=currentFontFile.getTableSize(FontFile2.CFF);

                byte[] data=currentFontFile.readBytes(startPointer, length) ;

                //initialise glyphs
                new Type1C(data,null,glyphs);

                hasCFFdata=true;
            } catch (Exception e) {
                //tell user and log
                logger.info("Exception: "+e.getMessage());
            }
		}
    }

    public boolean hasCFFData() {
        return hasCFFdata;
    }


    public PdfGlyph getCFFGlyph(GlyphFactory factory,String glyph, float[][] Trm,int rawInt, String displayValue, float currentWidth,String key) {

        return glyphs.getEmbeddedGlyph(factory, glyph, Trm, rawInt, displayValue, currentWidth, key);

    }
}
