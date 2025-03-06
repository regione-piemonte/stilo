/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.eng.hybrid.module.jpedal.fonts.tt;

import it.eng.hybrid.module.jpedal.fonts.PdfFont;
import it.eng.hybrid.module.jpedal.fonts.glyph.PdfJavaGlyphs;

import java.io.IOException;

/**
 *
 * @author markee
 */
class LocaWriter extends Loca implements FontTableWriter{

    String fontName;
    private PdfFont originalFont;
    FontFile2 orginTTTables;

    LocaWriter(String name, PdfFont pdfFont, PdfFont originalFont, PdfJavaGlyphs glyphs, String[] glyphList) {
	super(null, 0, 0);
	
	this.originalFont=originalFont;
	
    }


    public byte[] writeTable() throws IOException {
	
	Loca origTable=(Loca)originalFont.getGlyphData().getTable(FontFile2.LOCA);
	
	/**
	 * work out length
	 */
	this.format=origTable.getFormat();
	this.glyphCount=origTable.getGlyphCount();
	this.glyfTableLength=origTable.getGlyfTableLength();
	
	return null;
	
	
    }

    public int getIntValue(int key) {
	throw new UnsupportedOperationException("Not supported yet.");
    }
    
}
