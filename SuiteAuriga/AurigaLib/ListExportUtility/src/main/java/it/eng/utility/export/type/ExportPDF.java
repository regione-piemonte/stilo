/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.File;
import java.io.FileOutputStream;
import java.util.Map;

import org.apache.commons.lang.StringEscapeUtils;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Font.FontFamily;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.BarcodePDF417;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.ColumnText;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfPageEventHelper;
import com.itextpdf.text.pdf.PdfWriter;

import it.eng.utility.export.IExport;
import it.eng.utility.ui.module.core.server.bean.ExportBean;
import it.eng.utility.ui.module.core.server.bean.PdfExportBean;
import it.eng.utility.ui.module.core.server.bean.PdfExportBean.Dimension;
import it.eng.utility.ui.module.core.server.bean.PdfExportBean.Orientation;

public class ExportPDF implements IExport{

	private static final Font font_10= new Font(FontFamily.TIMES_ROMAN, 8, Font.NORMAL);

	private static final Font font_report= new Font(FontFamily.TIMES_ROMAN, 10, Font.NORMAL);
	
	private static final Font font_50= new Font(FontFamily.TIMES_ROMAN, 20, Font.BOLD);

	@Override
	public void export(File file, ExportBean bean) throws Exception {

		int[] columnWidth = new int[bean.getFields().length];
		PdfPTable table = new PdfPTable(bean.getFields().length);
		table.setWidthPercentage(100);	
		
		//Setto le intestazioni
		if(bean.showHeaders()) {

			for(int c = 0;  c < bean.getHeaders().length; c++){
			
				PdfPCell cell = new PdfPCell(new Paragraph(StringEscapeUtils.unescapeHtml(bean.getHeaders()[c]), font_10));
				cell.setBackgroundColor(new BaseColor(0xC0, 0xC0, 0xC0));
				table.addCell(cell);
				columnWidth[c] = bean.getHeaders()[c] != null ? bean.getHeaders()[c].length() : 0;
				
			}
			
			table.setHeaderRows(1);
			
		}
		
		//Scrivo le righe
		for (int r = 0; r < bean.getRecords().length; r++) {
			Map record = bean.getRecords()[r];
			for (int c = 0; c < bean.getFields().length; c++) {					
				String value = (String) record.get(bean.getFields()[c]);
				if(value==null){
					value = "";
				}
				int length = value.length();
				int previusLength = columnWidth[c];
				if(previusLength<=length){
					columnWidth[c] = new Integer(length).intValue();
				}
				table.addCell(new PdfPCell(new Paragraph(StringEscapeUtils.unescapeHtml(value),font_10)));					
			}				
		}		
		
		//Calcolo le larghezze
		int maxLength = 0;
		for(int i = 0; i < columnWidth.length; i++){
			maxLength += columnWidth[i];
		}
		float[] percentageColumnWidth = new float[columnWidth.length]; 
		for(int i = 0; i < columnWidth.length; i++){
		    /*
		     * 4)	Poi c'è questo problema, che forse è generale delle liste e c'è da sempre: 
		     *      se faccio esportazione in pdf della lista "Consultazione log" (provare su Milano, 
		     *      AurigaUITest con mia utenza e layout di default) il pdf non mantiene assolutamente 
		     *      le proporzioni delle colonne. In particolare non riesco 
		     *      a far allargare nel pdf la colonna "esito" (valori successo/fallita)
                    in modo che stia in una sola riga. Come funziona il layout del pdf ?
		     */
			//	percentageColumnWidth[i] = (new Float(((double)columnWidth[i]/(double)maxLength)*100)).floatValue();
	   
			if(columnWidth[i] > 10)
			{
				percentageColumnWidth[i] = 10;	
			}else
			{
				percentageColumnWidth[i] =columnWidth[i];
			}

		}
		table.setTotalWidth(percentageColumnWidth);
		
		Document document = null;
		if(maxLength>200){
			document = new Document(PageSize.A3.rotate(),10,10,10,10);
		}else if(maxLength>150){
			document = new Document(PageSize.A4.rotate(),10,10,10,10);
		}else{
			document = new Document(PageSize.A4,10,10,10,10);
		}
		
		PdfWriter.getInstance(document, new FileOutputStream(file));
		document.open();
		document.addAuthor("Automatic Export");
		if(bean != null && bean.getIntestazioneReport() != null && 
				!"".equals(bean.getIntestazioneReport())){
			Paragraph lTitoloReport = new Paragraph(bean.getIntestazioneReport(), font_report);
			document.add(lTitoloReport);
			Paragraph lTitoloReportSpace = new Paragraph(" ", font_report);
			document.add(lTitoloReportSpace);
		}
		document.add(table);
		document.close();
		
	}

	/**
	 * Permette di creare un pdf di tipo PDF/A
	 * @param file
	 * @param data
	 * @param bean
	 * @throws Exception
	 */
	public void exportToPDFA(File file, PdfExportBean bean) throws Exception {
		//Creo un font a partire dal percorso del font presente nel ProduzioneRegistroProperty
		BaseFont bf = BaseFont.createFont(ExportPDF.class.getResource("../font/times.ttf").getFile(), BaseFont.WINANSI, true);
		//Creo il font con la relativa dimensione
		Font fontForCell = new Font(bf, 10);
		//Creo il font per il title in base al basefont iniziale
		Font fontForTitle = new Font(bf, bean.getTitleDimension(), Font.BOLD);
		
		int size = bean.getFields().length;	
		int[] columnWidth = new int[size];
		PdfPTable table = new PdfPTable(size);

		table.setWidthPercentage(100);	
		
		//Setto le intestazioni
		for(int i = 0; i < bean.getColumnHeader().size(); i++){
			PdfPCell cell = new PdfPCell(new Paragraph(StringEscapeUtils.unescapeHtml(bean.getColumnHeader().get(i)),fontForCell));
			cell.setBackgroundColor(new BaseColor(0xC0, 0xC0, 0xC0));		
			table.addCell(cell);
			columnWidth[i] = bean.getColumnHeader().get(i).length();
		}
		table.setHeaderRows(1);
		
		//Scrivo le righe
		for (int i = 0; i < bean.getRecords().length; i++) {
			Map record = bean.getRecords()[i];
			for (int j = 0; j < bean.getFields().length; j++) {	
				String value = (String) record.get(bean.getFields()[j]);
				if(value==null){
					value = "";
				}
				int length = value.length();
				int previusLength = columnWidth[j];
				if(previusLength<=length){
					columnWidth[j] = new Integer(length).intValue();
				}
				if (bean.getFields()[j].equals("impronta")){
					PdfPCell lPdfPCell = new PdfPCell();
					BarcodePDF417 barcode = new BarcodePDF417();
					barcode.setText("SHA- 1 " + value);
					Image img = barcode.getImage();
					lPdfPCell.addElement(img);
					table.addCell(lPdfPCell);
				} else
					table.addCell(new PdfPCell(new Paragraph(StringEscapeUtils.unescapeHtml(value),fontForCell)));				
			}
		}
		
		if (bean.getColumnDimension()==null){
			//Calcolo le larghezze
			int maxLength = 0;
			for(int i=0;i<columnWidth.length;i++){
				maxLength += columnWidth[i];
			}
			float[] percentageColumnWidth = new float[columnWidth.length]; 
			for(int i=0;i<columnWidth.length;i++){
				percentageColumnWidth[i] = (new Float(((double)columnWidth[i]/(double)maxLength)*100)).floatValue();
			}
			table.setTotalWidth(percentageColumnWidth);
		}
		else {
			table.setTotalWidth(bean.getColumnDimension());
		}
		
		Document document = null;
		if (bean.getDimension().equals(Dimension.A3)){
			if (bean.getOrientation().equals(Orientation.HORIZONTAL)){
				document = new Document(PageSize.A3.rotate(),10,10,10,10);
			} else if (bean.getOrientation().equals(Orientation.VERTICAL)){
				document = new Document(PageSize.A3,10,10,10,10);
			} 

		} else if (bean.getDimension().equals(Dimension.A4)){
			if (bean.getOrientation().equals(Orientation.HORIZONTAL)){
				document = new Document(PageSize.A4.rotate(),10,10,10,70);
			} else if (bean.getOrientation().equals(Orientation.VERTICAL)){
				document = new Document(PageSize.A4,10,10,10,10);
			}
		}
		
		//Per creare un PDF/A è necessario specificare la PDFXConformance come PdfWriter.PDFA1B per il writer
		//qualora si presentino dei problemi di incompatibilità, viene automaticamente rilanciata una eccezione 
		//dal writer stesso
		
		PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(file));
		writer.setPDFXConformance(PdfWriter.PDFX1A2001);
		writer.setBoxSize("art", new Rectangle(36, 54, 788, 559));
		HeaderFooter event = new HeaderFooter(fontForCell, file.getName());
		writer.setPageEvent(event);
		document.open();
		document.addAuthor("Automatic Export");
		document.setPageCount(5);
		Paragraph lParagraph = new Paragraph(bean.getTitle(), fontForTitle);
		lParagraph.setAlignment(Element.ALIGN_CENTER);

		lParagraph.setSpacingAfter(50);
		document.add(lParagraph);
		document.add(table);
				
		writer.createXmpMetadata();
		
		document.close();
		
		Document documentWithPages = null;
		if (bean.getDimension().equals(Dimension.A3)){
			if (bean.getOrientation().equals(Orientation.HORIZONTAL)){
				documentWithPages = new Document(PageSize.A3.rotate(),10,10,10,10);
			} else if (bean.getOrientation().equals(Orientation.VERTICAL)){
				documentWithPages = new Document(PageSize.A3,10,10,10,10);
			} 

		} else if (bean.getDimension().equals(Dimension.A4)){
			if (bean.getOrientation().equals(Orientation.HORIZONTAL)){
				documentWithPages = new Document(PageSize.A4.rotate(),10,10,10,70);
			} else if (bean.getOrientation().equals(Orientation.VERTICAL)){
				documentWithPages = new Document(PageSize.A4,10,10,10,10);
			}
		}
		
		writer = PdfWriter.getInstance(documentWithPages, new FileOutputStream(file));
		writer.setPDFXConformance(PdfWriter.PDFX1A2001);
		writer.setBoxSize("art", new Rectangle(36, 54, 788, 559));
		event = new HeaderFooter(fontForCell, file.getName(), event.getTotalPages());
		writer.setPageEvent(event);
		documentWithPages.open();
		documentWithPages.addAuthor("Automatic Export");
		
		lParagraph = new Paragraph(bean.getTitle(), fontForTitle);
		lParagraph.setAlignment(Element.ALIGN_CENTER);
		
		lParagraph.setSpacingAfter(50);
		documentWithPages.add(lParagraph);
		documentWithPages.add(table);
		
		writer.createXmpMetadata();
		
		documentWithPages.close();
	}
	
	static class HeaderFooter extends PdfPageEventHelper {

		Font mFont;
		private int pageNumber;
		private String nomeFile;
		private Integer totalPages;
		
		public HeaderFooter(Font pFont, String nomeFile){
			mFont = pFont;
			pageNumber = 0;
			this.nomeFile = nomeFile.substring(0, nomeFile.length()-4);
		}
		
		public HeaderFooter(Font pFont, String nomeFile, int totalPages){
			mFont = pFont;
			pageNumber = 0;
			this.nomeFile = nomeFile.substring(0, nomeFile.length()-4);
			this.totalPages = totalPages;
		}
		
        public void onEndPage (PdfWriter writer, Document document) {

            pageNumber++;
        	Rectangle rect = writer.getBoxSize("art");
        	
        	if (totalPages==null){
        		 ColumnText.showTextAligned(writer.getDirectContent(),
                         Element.ALIGN_CENTER, new Phrase(String.format("Pag. %d", pageNumber), mFont),
                         (rect.getLeft() + rect.getRight()) / 2, rect.getBottom() - 18, 0);
        	}
            
            ColumnText.showTextAligned(writer.getDirectContent(),
                    Element.ALIGN_CENTER, new Phrase(String.format("Pag. %d di %d", pageNumber, totalPages), mFont),
                    (rect.getLeft() + rect.getRight()) / 2, rect.getBottom() - 18, 0);
            ColumnText.showTextAligned(writer.getDirectContent(),
                    Element.ALIGN_LEFT, new Phrase(nomeFile, mFont),
                    10, rect.getBottom() - 18, 0);
        }
        
        public int getTotalPages(){
        	return pageNumber;
        }
    }

}