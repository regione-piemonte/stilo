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
* Hhea.java
* ---------------
*/
package it.eng.hybrid.module.jpedal.fonts.tt;


public class Hhea extends Table {
		
	private int numberOfHMetrics;
	
	public Hhea(FontFile2 currentFontFile){
	
		//move to start and check exists
		int startPointer=currentFontFile.selectTable(FontFile2.HHEA);
		
		//read 'head' table
		if(startPointer!=0){
			
            currentFontFile.getNextUint32(); //version 65536
            currentFontFile.getFWord();//ascender  1972
            currentFontFile.getFWord();//descender -483
            currentFontFile.getFWord();//lineGap 0
            currentFontFile.readUFWord();//advanceWidthMax  2513
            currentFontFile.getFWord();//minLeftSideBearing  -342
            currentFontFile.getFWord();//minRightSideBearing  -340
            currentFontFile.getFWord();//xMaxExtent      2454
            currentFontFile.getNextInt16();//caretSlopeRise 1
            currentFontFile.getNextInt16();//caretSlopeRun   0
            currentFontFile.getFWord();//caretOffset          0

            //reserved values
            for( int i = 0; i < 4; i++ )
                currentFontFile.getNextUint16(); //0

            currentFontFile.getNextInt16();//metricDataFormat
            numberOfHMetrics =currentFontFile.getNextUint16(); //261

		}
	}

    public Hhea() {
    }

    public int getNumberOfHMetrics(){
		return numberOfHMetrics;
	}
	
}
