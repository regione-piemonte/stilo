/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRichTextString;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import com.google.gwt.thirdparty.guava.common.xml.XmlEscapers;
import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.poi.common.usermodel.Hyperlink;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Sheet;

public class RichTextStringToHtm {
	private final Workbook wb;
	private StringBuilder sb;
    private boolean is07;
    private Cell cell;
    private boolean fontBoldInsert;
    
    public RichTextStringToHtm(Cell c) {
       
    	sb = new StringBuilder();
    	
    	cell = c;

	    Sheet sheet = cell.getSheet();
	   
	    wb = sheet.getWorkbook();
	   
        if (wb instanceof HSSFWorkbook)
            is07 = false;
        else if (wb instanceof XSSFWorkbook) {
            is07 = true;
        } else {
            throw new IllegalArgumentException("workbook sconosciuto: " + wb.getClass().getSimpleName());
		}	
    }

    public String getHtmlValue() {
	
    	fontBoldInsert = false;
    	String htmlValue = "";
		if (is07) {
			htmlValue = getXSSFRichString(cell);
		} else {
			htmlValue = getHSSFRichString(cell);
		}
        return htmlValue;		        
    }

   
    private String getXSSFRichString(Cell cell) {
    	
    	XSSFRichTextString rich = (XSSFRichTextString) cell.getRichStringCellValue();
    	
    	String baseString = rich.getString();
    	
        int numFormattingRuns = rich.numFormattingRuns();
    	if (numFormattingRuns == 0){
    		sb.append("<span style=").append('"');
    		CellStyle cs = cell.getCellStyle();
			fontStyleXSSF((XSSFFont) wb.getFontAt(cs.getFontIndex()));
			sb.append('"').append(">");
			sb.append(StringEscapeUtils.escapeHtml4(baseString));
			sb.append("</span>");
			if (fontBoldInsert){
	           	sb.append("</b>");
	           	fontBoldInsert = false;
	        }
			
			insertHyperLink(cell);
    	}
        else {
    		for (int i = 0; i < numFormattingRuns; i++) {        		
        		sb.append("<span style=").append('"');        
        		if (i==0){
        			CellStyle cs = cell.getCellStyle();
        			fontStyleXSSF((XSSFFont) wb.getFontAt(cs.getFontIndex()));
        		}
        		else{
                    try {
                    	fontStyleXSSF((XSSFFont) rich.getFontOfFormattingRun(i));
                	} 
                    catch (NullPointerException ex) {}        			
        		}
                sb.append('"').append(">");                
        	    int indexStart = rich.getIndexOfFormattingRun(i);
                int indexEnd = indexStart + rich.getLengthOfFormattingRun(i);                 
                sb.append(XmlEscapers.xmlContentEscaper().escape(baseString.substring(indexStart, indexEnd)));
                sb.append("</span>");
                if (fontBoldInsert){
                	sb.append("</b>");
                	fontBoldInsert = false;
                }
                
                insertHyperLink(cell);
            }
    	}    	
		return sb.toString();
    }
    
        
    private String getHSSFRichString(Cell cell) {
	    	
    	HSSFRichTextString rich = (HSSFRichTextString) cell.getRichStringCellValue();
    	
        String baseString = rich.getString();
        
        int numFormattingRuns = rich.numFormattingRuns();
        if (numFormattingRuns == 0){
        	CellStyle cs = cell.getCellStyle();        
            sb.append("<span style=").append('"');
            fontStyleHSSF((HSSFFont) wb.getFontAt(cs.getFontIndex()));
            sb.append('"').append(">");
            sb.append(StringEscapeUtils.escapeHtml4(baseString));
            sb.append("</span>");
            if (fontBoldInsert){
            	sb.append("</b>");
            	fontBoldInsert = false;
            }
            
            insertHyperLink(cell);
        }
        else{
        	CellStyle cs = cell.getCellStyle();            
            sb.append("<span style=").append('"');
            fontStyleHSSF((HSSFFont) wb.getFontAt(cs.getFontIndex()));
            sb.append('"').append(">");
            
            // no formatting so just copy in the string
        	int firstIndex = rich.getIndexOfFormattingRun(0);
            int currOffset = 0;

            while (currOffset < firstIndex) {
                sb.append(escapeHtml(baseString.charAt(currOffset)));
                currOffset++;
            }
            for (int fmtIdx = 0; fmtIdx < numFormattingRuns; fmtIdx++) {
                int begin = rich.getIndexOfFormattingRun(fmtIdx);
                short fontIndex = rich.getFontOfFormattingRun(fmtIdx);
                sb.append("</span>");
                if (fontBoldInsert){
                	sb.append("</b>");
                	fontBoldInsert = false;
                }
                
                insertHyperLink(cell);
                
                sb.append("<span style=").append('"');
                fontStyleHSSF((HSSFFont) wb.getFontAt(fontIndex));
                sb.append('"').append(">");
                for (int j = begin; j < rich.length(); j++) {
                    short currFontIndex = rich.getFontAtIndex(j);
                    if (currFontIndex == fontIndex) {
                        sb.append(escapeHtml(baseString.charAt(currOffset)));
                        currOffset++;
                    } else {
                        break;
                    }
                }
            }
            sb.append("</span>");
            if (fontBoldInsert){
            	sb.append("</b>");
            	fontBoldInsert = false;
            }
            
            insertHyperLink(cell); 
        }
		return sb.toString();
    }

    private void fontStyleXSSF(XSSFFont font) {
    	
    	// bold
    	if (font.getBold()) {
    		// inserisco il <b> prima del tag <span style=
    		int lensb = sb.length();
        	sb.insert(lensb-13, "<b>");
        	fontBoldInsert = true;
    	}
    	
    	// underline
    	if (font.getUnderline()==1 ) {
    		sb.append(" text-decoration:underline;");	
    	}
    	
    	// italic
    	if (font.getItalic() ) {
    		sb.append(" font-style: italic;");
    	}
    	 // height
        int fontheight = font.getFontHeightInPoints();
        if (fontheight == 9) {
            //fix for stupid ol Windows
            fontheight = 10;
        }
        sb.append(" font-size: ").append(fontheight).append("pt;");
    }
    
    
    
    private void fontStyleHSSF(HSSFFont font) {
    	
    	// bold
        if (font.getBoldweight() > HSSFFont.BOLDWEIGHT_NORMAL) {
        	// inserisco il <b> prima del tag <span style=
        	int lensb = sb.length();
        	sb.insert(lensb-13, "<b>");
        	fontBoldInsert = true;        	
        }
        
    	// underline
    	if (font.getUnderline()==1) {
        	sb.append(" text-decoration:underline;");
        }
    	
        // italic
        if (font.getItalic()) {
            sb.append(" font-style: italic;");
        }
        // height
        int fontheight = font.getFontHeightInPoints();
        if (fontheight == 9) {
            //fix for stupid ol Windows
            fontheight = 10;
        }
        sb.append("  font-size: ").append(fontheight).append("pt;");
    }

    
    private void insertHyperLink(Cell cell){
    	
    	String baseString = "";
    	
    	Hyperlink hyperlink = cell.getHyperlink();
    	
    	if (cell.getHyperlink()!=null) {
    		if (is07) {
        		XSSFRichTextString rich = (XSSFRichTextString) cell.getRichStringCellValue();
        		baseString = rich.getString();
        	}
        	else{
        		HSSFRichTextString rich = (HSSFRichTextString) cell.getRichStringCellValue();
                baseString = rich.getString();
        	}    		
    		if (cell.getHyperlink().getType() ==  hyperlink.LINK_URL || cell.getHyperlink().getType() ==  hyperlink.LINK_DOCUMENT || cell.getHyperlink().getType() ==  hyperlink.LINK_FILE){
    			String href = "<a href=" + '"' + baseString + '"' + ">";
    			sb.insert(0, href);
    			sb.append("</a>");
    		}
    		
    		else if (cell.getHyperlink().getType() ==  hyperlink.LINK_EMAIL ){
    			String href = "<a href=" + '"' + "mailto:" + baseString + '"' + ">";
    			sb.insert(0, href);
    			sb.append("</a>");
    		}
    	}
    }
    
	public String escapeHtml(char in) {
        switch (in) {
            case '\n':
            case '\r':
                return "<br/>";

        }
        return StringEscapeUtils.escapeHtml4("" + in);
    }	
}