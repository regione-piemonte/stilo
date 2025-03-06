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
* Glyf.java
* ---------------
*/
package it.eng.hybrid.module.jpedal.fonts.tt;

import java.util.HashMap;
import java.util.Map;


public class Glyf extends Table {
	
	/**holds mappings for drawing the glpyhs*/
	private Map charStrings=new HashMap();

    private int glyfCount=0;

	/**holds list of empty glyphs*/
	private Map emptyCharStrings=new HashMap();
    private byte[] glyphTable;
	
	public Glyf(FontFile2 currentFontFile,int glyphCount,int[] glyphIndexStart){
	
        //save so we can access
        this.glyfCount=glyphCount;

		//move to start and check exists
		int startPointer=currentFontFile.selectTable(FontFile2.LOCA);
		
		//read  table
		if(startPointer!=0){
			
			//read each gyf
			for(int i=0;i<glyphCount;i++){
				
				//just store in lookup table or flag as zero length
				if((glyphIndexStart[i]==glyphIndexStart[i+1])){
					charStrings.put(i, -1);
					emptyCharStrings.put(i,"x");
				}else{
					charStrings.put(i, glyphIndexStart[i]);
				}
			}
            
            //read the actual glyph data
            glyphTable=currentFontFile.getTableBytes(FontFile2.GLYF);
                    
		}
	}

	public boolean isPresent(int glyph){
		
		Integer key= glyph;
		
        Object value=charStrings.get(key);

        return value!=null && emptyCharStrings.get(key)==null;
	}
	
	public int getCharString(int glyph){
		
        Object value=charStrings.get(glyph);


        if(value==null)
			return glyph;
		else
			return (Integer) value;
	}
	
    public byte[] getTableData() {
        return glyphTable;
    }

    public int getGlypfCount() {
        return glyfCount;
    }

    /**assume identify and build data needed for our OTF converter*/
    public Map buildCharStringTable(int enc) {

        Map returnStrings=new HashMap();

        for (Object key : charStrings.keySet()) {
            if (!emptyCharStrings.containsKey(key)) {
                returnStrings.put(key, key);
            }
        }
        return returnStrings;
    }
}
