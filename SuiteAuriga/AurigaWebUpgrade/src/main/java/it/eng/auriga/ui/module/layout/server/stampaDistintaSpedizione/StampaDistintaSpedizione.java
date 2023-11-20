/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.List;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.context.support.ClassPathXmlApplicationContext;

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
import com.itextpdf.text.pdf.ColumnText;
import com.itextpdf.text.pdf.PdfCopy;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfPageEventHelper;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfWriter;

import it.eng.auriga.ui.module.layout.server.stampaDistintaSpedizione.bean.DatiDistintaSpedizioneOut;
import it.eng.auriga.ui.module.layout.shared.bean.StampaDistintaSpedizioneConfig;
import it.eng.document.NumeroColonna;
import it.eng.spring.utility.SpringAppContext;
import it.eng.utility.springBeanWrapper.BeanPropertyUtility;

public class StampaDistintaSpedizione {
	
	private int FonSizeTable = 15;
	
	private static StampaDistintaSpedizione instance;
	
	public static StampaDistintaSpedizione getInstance(){
		if (instance == null){
			instance = new StampaDistintaSpedizione();
		}
		return instance;
	}
	
	public void stampaDistintaSpedizione(File file, List<DatiDistintaSpedizioneOut> datiRegistrazioniList, String header1, String header2, String header3, String footer) throws Exception {
		stampaDistintaSpedizione(file, datiRegistrazioniList, header1, header2, header3, footer, 0);
	}
		
	public void stampaDistintaSpedizione(File file, List<DatiDistintaSpedizioneOut> datiRegistrazioniList, String header1, String header2, String header3, String footer, int startPageNumber) throws Exception {
		
		// Creo il layout centrale
		PdfPTable table = createTableDatiRegistrazioni(datiRegistrazioniList);
		
		//Document document = new Document(PageSize.A3.rotate(), 10, 10, 25, 25);		
		Document document = new Document(PageSize.A3.rotate(), 10, 10, 70, 25);
		
		PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(file));
		writer.setPDFXConformance(PdfWriter.PDFXNONE);
		
		// Creo HEADER e FOOTER	
		HeaderFooter event = new HeaderFooter(header1, header2, header3, footer, startPageNumber);
		writer.setPageEvent(event);
		document.open();
		
	    document.add(table);		   
        
	    writer.createXmpMetadata();
		document.close();								
	}
	
	
	public PdfPTable createTableDatiRegistrazioni(List<DatiDistintaSpedizioneOut> datiRegistrazioniList) throws Exception {
		return createTableDatiRegistrazioni(datiRegistrazioniList, false);
	}

	public PdfPTable createTableDatiRegistrazioni(List<DatiDistintaSpedizioneOut> datiRegistrazioniList, boolean skipFirstHeader) throws Exception {

		StampaDistintaSpedizioneConfig config = (StampaDistintaSpedizioneConfig)SpringAppContext.getContext().getBean("StampaDistintaSpedizioneConfig");
		
//		int[] columnWidth = new int[config.getColsToPrint().length];
		float[] configPercentageWidth = new float[config.getColsToPrint().length];
		
		PdfPTable table = new PdfPTable(config.getColsToPrint().length);
		table.setWidthPercentage(100);			

		
		//Setto le intestazioni
		for(int c = 0;  c < config.getColsToPrint().length; c++){
			PdfPCell cell = new PdfPCell(new Paragraph(StringEscapeUtils.unescapeHtml(config.getColsToPrint()[c].getIntestazione()), new Font(FontFamily.TIMES_ROMAN, FonSizeTable, Font.NORMAL)));
			cell.setBackgroundColor(new BaseColor(0xC0, 0xC0, 0xC0));
			table.addCell(cell);			
//			columnWidth[c] = config.getColsToPrint()[c].getIntestazione() != null ? config.getColsToPrint()[c].getIntestazione().length() : 0;
			configPercentageWidth[c] = config.getColsToPrint()[c].getPercentageWidth();
		}		
		table.setHeaderRows(1);		
		table.setSkipFirstHeader(skipFirstHeader);		
		
		if(datiRegistrazioniList != null && datiRegistrazioniList.size() > 0) {
			//Scrivo le righe
			BeanWrapperImpl wrapperDatiReg = BeanPropertyUtility.getBeanWrapper();
			for (DatiDistintaSpedizioneOut datiReg : datiRegistrazioniList) {		
				wrapperDatiReg = BeanPropertyUtility.updateBeanWrapper(wrapperDatiReg, datiReg);
				 for (int c = 0; c < config.getColsToPrint().length; c++) {				    	 
					String value = null;
					for (Field lField : DatiDistintaSpedizioneOut.class.getDeclaredFields()){
						NumeroColonna lNumeroColonna = lField.getAnnotation(NumeroColonna.class);
						if (lNumeroColonna!=null){
							int numCol = new Integer(lNumeroColonna.numero()).intValue();
							if(numCol == config.getColsToPrint()[c].getNroColonna().intValue()) {
								value = BeanPropertyUtility.getPropertyValueAsString(datiReg, wrapperDatiReg, lField.getName());
								// value = BeanUtilsBean2.getInstance().getProperty(datiReg, lField.getName());
							}						
						}
					}		    	 				
					if(value == null){
						value = "";
					}
//					int length = value.length();
//					int previusLength = columnWidth[c];
//					if(previusLength<=length){
//						columnWidth[c] = new Integer(length).intValue();
//					}							
					if(config.getColsToPrint()[c].getNroColonna().intValue() == 10){						
						PdfPCell lPdfPCell = new PdfPCell();
						if(StringUtils.isNotBlank(value)) {
							BarcodePDF417 barcode = new BarcodePDF417();
							barcode.setText(value);
							Image img = barcode.getImage();										
							lPdfPCell.addElement(img);
						}
						lPdfPCell.setPadding(10);					
						table.addCell(lPdfPCell);
//						columnWidth[c] = 120;
					} else	{										
						table.addCell(new PdfPCell(new Paragraph(StringEscapeUtils.unescapeHtml(value), new Font(FontFamily.TIMES_ROMAN, FonSizeTable, Font.NORMAL))));
					}						
				}				
			}
		}
		
//		//Calcolo le larghezze
//		int maxLength = 0;
//		for(int i = 0; i < columnWidth.length; i++){
//			maxLength += columnWidth[i];
//		}
//		float[] percentageColumnWidth = new float[columnWidth.length]; 
//		for(int i = 0; i < columnWidth.length; i++){
//			percentageColumnWidth[i] = (new Float(((double)columnWidth[i]/(double)maxLength)*100)).floatValue();
//		}
//		table.setTotalWidth(percentageColumnWidth);	
		
		//Calcolo le larghezze
		table.setTotalWidth(configPercentageWidth);
		
		return table;
	}

	static class HeaderFooter extends PdfPageEventHelper {

		private int pageNumber;
		
		private String header1;
		private String header2;
		private String header3;
		private String footer;
		private int AltezzaRigaHeader = 18;
		
		private int FonSizeHeaderFooter = 12;
		
		public HeaderFooter(String pHeader1, String pHeader2, String pHeader3, String pFooter, int pStartPageNumber){
			pageNumber = pStartPageNumber > 0 ? pStartPageNumber : 0;
			header1 = pHeader1;
			header2 = pHeader2;
			header3 = pHeader3;
			footer  = pFooter;
		}		
		
		@Override
		public void onEndPage (PdfWriter writer, Document document) {

            pageNumber++;
            Font font = new Font(FontFamily.COURIER, FonSizeHeaderFooter, Font.NORMAL);
            
            Rectangle lRectangle = document.getPageSize();
            float posXheader2 = lRectangle.getWidth()/2;
            
            ColumnText.showTextAligned(writer.getDirectContent(), Element.ALIGN_LEFT,   new Phrase(header1, font), 10,          document.top() + (AltezzaRigaHeader * 3)  , 0);   // Header Riga 1
            ColumnText.showTextAligned(writer.getDirectContent(), Element.ALIGN_CENTER, new Phrase(header2, font), posXheader2, document.top() + (AltezzaRigaHeader * 2)  , 0);   // Header Riga 2
            ColumnText.showTextAligned(writer.getDirectContent(), Element.ALIGN_LEFT,   new Phrase(header3, font),    10,       document.top() + (AltezzaRigaHeader * 1)  , 0);   // Header Riga 3

            ColumnText.showTextAligned(writer.getDirectContent(), Element.ALIGN_RIGHT, new Phrase(String.format("Pag. %d", pageNumber), font), document.left() + document.right() - 10, document.bottom() - 15, 0);
        	
            ColumnText.showTextAligned(writer.getDirectContent(), Element.ALIGN_LEFT, new Phrase(footer, font), 10, document.bottom() - 15, 0);                      
        }
                
    }

	public static void concatFiles(List<File> fileList, File fileOutput) throws Exception {
		if (fileList.size() > 0) {
			InputStream isReader = null;
			FileOutputStream osReader = null;
			try {
				isReader = new FileInputStream(fileList.get(0));
				PdfReader reader = new PdfReader(isReader);
				Document document = new Document(reader.getPageSizeWithRotation(1));
				osReader = new FileOutputStream(fileOutput);
				PdfCopy cp = new PdfCopy(document,osReader);
				document.open();
				for (File file : fileList) {
					InputStream isReaderItem = new FileInputStream(file);
		            PdfReader prItem = new PdfReader(isReaderItem);
		            for (int k = 1; k <= prItem.getNumberOfPages(); ++k) {
		                cp.addPage(cp.getImportedPage(prItem, k));
		            }
		            cp.freeReader(prItem);
		            if(isReaderItem != null) {
		            	try {
		            		isReaderItem.close(); 
						} catch (Exception e) {}
		            }
				}
			    cp.close();
			    document.close();
			} finally {
				if(osReader != null) {
					try {
						osReader.close(); 
					} catch (Exception e) {}
				}
				if(isReader != null) {
					try {
						isReader.close(); 
					} catch (Exception e) {}
				}
			}
		} else{             
		    throw new Exception("La lista dei pdf da concatenare è vuota");        
		}	
	}
	
	public static void main (String[] args) {
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("config.xml");				
		SpringAppContext.setContext(context);		
		//testStampa();
	}

}