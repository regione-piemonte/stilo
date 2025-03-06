package it.eng.hybrid.module.stampaEtichette.util;

import com.itextpdf.text.Document;
import com.itextpdf.text.pdf.PdfName;
import com.itextpdf.text.pdf.PdfNumber;
import com.itextpdf.text.pdf.PdfPageEventHelper;

public class DirectionEvent extends PdfPageEventHelper {
	
	protected PdfNumber orientation = null;
	protected PdfNumber rotation = null;
    
    public void setOrientation(PdfNumber orientation) {
        this.orientation = orientation;
    }
    
    public void setRotation(PdfNumber rotation) {
        this.rotation = rotation;
    }

    @Override
    public void onStartPage(com.itextpdf.text.pdf.PdfWriter writer, Document document) {
    	if (orientation != null){
    		writer.addPageDictEntry(PdfName.ROTATE,  orientation);
    	}
    }
    
    @Override
    public void onEndPage(com.itextpdf.text.pdf.PdfWriter writer, Document document) {
    	if (rotation != null){
    		writer.addPageDictEntry(PdfName.ROTATE, rotation);
    	}
    }
}
