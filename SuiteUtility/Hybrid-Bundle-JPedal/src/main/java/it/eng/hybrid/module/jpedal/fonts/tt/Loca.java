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
 *     This library is free software; you can redistribute it and/or
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
 * Loca.java
 * ---------------
 */
package it.eng.hybrid.module.jpedal.fonts.tt;

import org.apache.log4j.Logger;

public class Loca extends Table {
    
	public final static Logger logger = Logger.getLogger(Loca.class);
	
    /**points to location of glyph programs*/
    int[] glyphIndexStart;
    
    boolean isCorrupted=false;
    
    public int format;
    public int glyphCount;
    public int glyfTableLength;
    
    public Loca(FontFile2 currentFontFile,int glyphCount,int format){
	
	//handles super call in LocaWriter
	if(currentFontFile==null)
	    return;
	
	this.format=format;
	this.glyphCount=glyphCount;
	this.glyfTableLength=currentFontFile.getOffset(FontFile2.GLYF);
	
	//LogWriter.writeMethod("{readLocaTable}", 0);
	
	//move to start and check exists
	int startPointer=currentFontFile.selectTable(FontFile2.LOCA);
	
	int i;
	
	int locaLength=currentFontFile.getOffset(FontFile2.LOCA);
	
	glyphIndexStart=new int[glyphCount+1];
	
	//read 'head' table
	if(startPointer!=0){
	    
	    glyphIndexStart[0]=0;
	    //long version
	    if(format==1){
		logger.info("Incorrect length");
		
		for(i=0;i<glyphCount;i++)
		    glyphIndexStart[i]=currentFontFile.getNextUint32();
		
	    }else{ //short
		if((locaLength/2)!=(glyphCount+1)){
		    
		    logger.info("Incorrect length");
		    
		    isCorrupted=true;
		}else{
		    for(i=0;i<glyphCount;i++)
			glyphIndexStart[i]=(currentFontFile.getNextUint16()*2);
		    
		}
	    }
	    
	    glyphIndexStart[glyphCount]=glyfTableLength;
	    
	}
    }
    
    public int[] getIndices(){
	return glyphIndexStart;
    }
    
    public boolean isCorrupted(){
	return isCorrupted;
    }
    
    public int getFormat(){
	return format;
    }

    public int getGlyphCount() {
	return glyphCount;
    }

    public int getGlyfTableLength() {
	return glyfTableLength;
    }
}
