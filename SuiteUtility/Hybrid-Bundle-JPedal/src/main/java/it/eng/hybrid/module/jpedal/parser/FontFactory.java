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
* FontFactory.java
* ---------------
*/
package it.eng.hybrid.module.jpedal.parser;

import it.eng.hybrid.module.jpedal.fonts.CIDFontType0;
import it.eng.hybrid.module.jpedal.fonts.CIDFontType2;
import it.eng.hybrid.module.jpedal.fonts.PdfFont;
import it.eng.hybrid.module.jpedal.fonts.StandardFonts;
import it.eng.hybrid.module.jpedal.fonts.TrueType;
import it.eng.hybrid.module.jpedal.fonts.Type1C;
import it.eng.hybrid.module.jpedal.fonts.Type3;
import it.eng.hybrid.module.jpedal.fonts.glyph.PdfGlyph;
import it.eng.hybrid.module.jpedal.fonts.glyph.T1Glyph;
import it.eng.hybrid.module.jpedal.fonts.glyph.T3Glyph;
import it.eng.hybrid.module.jpedal.fonts.tt.TTGlyph;
import it.eng.hybrid.module.jpedal.io.PdfObjectReader;
import it.eng.hybrid.module.jpedal.render.DynamicVectorRenderer;


public class FontFactory {

    public static PdfFont createFont(int fontType, PdfObjectReader currentPdfFile, String subFont, boolean isPrinting) {

        switch(fontType){
            case StandardFonts.TYPE1:
                return new Type1C(currentPdfFile,subFont);

            case StandardFonts.TRUETYPE:
                return new TrueType(currentPdfFile,subFont);

            case StandardFonts.TYPE3:
                return new Type3(currentPdfFile, isPrinting);
            
            case StandardFonts.CIDTYPE0:
                return new CIDFontType0(currentPdfFile);

            case StandardFonts.CIDTYPE2:
                return new CIDFontType2(currentPdfFile,subFont);

            default:


                //LogWriter.writeLog("Font type " + subtype + " not supported");
                return new PdfFont(currentPdfFile);
        }

    }
    
    public static PdfGlyph chooseGlyph(int glyphType, Object rawglyph) {
		
		if(glyphType==DynamicVectorRenderer.TYPE3){
			return (T3Glyph)rawglyph;
		}else if(glyphType==DynamicVectorRenderer.TYPE1C){
			return (T1Glyph)rawglyph;
		}else if(glyphType==DynamicVectorRenderer.TRUETYPE){
			return (TTGlyph)rawglyph;
		}else
			return null;
		
	}
}
