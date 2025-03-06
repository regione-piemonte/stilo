/**
 * ===========================================
 * Java Pdf Extraction Decoding Access Library
 * ===========================================
 *
 * Project Info:  http://www.jpedal.org
 *
 * (C) Copyright 2007, IDRsolutions and Contributors.
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

  * CMAPWriter.java
  * ---------------
  * (C) Copyright 2007, by IDRsolutions and Contributors.
  *
  *
  * --------------------------
 */
package org.jpedal.fonts.tt.conversion;

import org.jpedal.fonts.glyph.PdfJavaGlyphs;
import org.jpedal.fonts.tt.Maxp;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class
        MAXPWriter extends Maxp implements FontTableWriter{

    int glyphCount=0;


    /**
     * used to turn Ps into OTF
     * @param pdfFont
     */
    public MAXPWriter(PdfJavaGlyphs glyphs) {

        glyphCount=glyphs.getGlyphCount();
    }


    public byte[] writeTable() throws IOException {

        ByteArrayOutputStream bos=new ByteArrayOutputStream();
        
        bos.write(TTFontWriter.setNextUint32(20480)); //revision 5000 hex
        bos.write(TTFontWriter.setNextUint16(glyphCount)); //number of glyphs

        bos.flush();
        bos.close();

        return bos.toByteArray();
    }

    public int getIntValue(int key) {
        return 0;
    }
}
