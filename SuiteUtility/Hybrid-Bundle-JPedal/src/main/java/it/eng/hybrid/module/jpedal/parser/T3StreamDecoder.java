package it.eng.hybrid.module.jpedal.parser;

import it.eng.hybrid.module.jpedal.exception.PdfException;
import it.eng.hybrid.module.jpedal.fonts.glyph.T3Size;
import it.eng.hybrid.module.jpedal.io.PdfObjectReader;
import it.eng.hybrid.module.jpedal.objects.GraphicsState;
import it.eng.hybrid.module.jpedal.objects.raw.PdfObject;

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
  * T3StreamDecoder.java
  * ---------------
 */
public class T3StreamDecoder extends PdfStreamDecoder {

    public T3StreamDecoder(PdfObjectReader currentPdfFile, boolean useHires,boolean isPrinting) {

        super(currentPdfFile, useHires,null);

        isType3Font=true;

        this.isPrinting=isPrinting;
    }

    public T3Size decodePageContent(PdfObject glyphObj, GraphicsState newGS) throws PdfException {

        this.newGS=newGS;

        return decodePageContent(glyphObj);
    }
}
