/**
 * ===========================================
 * Java Pdf Extraction Decoding Access Library
 * ===========================================
 *
 * Project Info:  http://www.jpedal.org
 *
 * (C) Copyright 2011, IDRsolutions and Contributors.
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

  * TTFontWriter.java
  * ---------------
  * (C) Copyright 2011, by IDRsolutions and Contributors.
  *
  *
  * --------------------------
 */
package it.eng.hybrid.module.jpedal.fonts.tt;

import java.io.IOException;

import org.apache.log4j.Logger;

public class TTFontWriter extends FontWriter{

	public final static Logger logger = Logger.getLogger(TTFontWriter.class);
	
    byte[] rawFontData;
    byte[] cmap=null;


    public TTFontWriter(byte[] rawFontData) {

        super(rawFontData);

        //get number of glyphs
        Maxp currentMaxp =new Maxp(new FontFile2(rawFontData));
        glyphCount= currentMaxp.getGlyphCount();

        this.rawFontData=rawFontData;

    }

    void readTables() {

        cmap=null;
        TTFontWriter fontData=new TTFontWriter(rawFontData);
        CMAPWriter orginalCMAP=new CMAPWriter(fontData,fontData.selectTable(FontFile2.CMAP),null);

        try {
            cmap=orginalCMAP.writeTable();
        } catch (IOException e) {
            //tell user and log
            logger.info("Exception: "+e.getMessage());
        }
    }

    public byte[] getTableBytes(int tableID){


        if(tableID==CMAP){

            return cmap;

        }else
            return super.getTableBytes(tableID);
    }
}
