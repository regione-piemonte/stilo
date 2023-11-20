/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.lang.reflect.Field;
import java.net.URL;
import java.util.List;

import org.apache.commons.beanutils.BeanUtilsBean2;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import com.itextpdf.text.Anchor;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Font.FontFamily;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.BarcodePDF417;
import com.itextpdf.text.pdf.ColumnText;
import com.itextpdf.text.pdf.PdfCopy;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfPageEventHelper;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfWriter;

import it.eng.document.NumeroColonna;
import it.eng.job.registri.bean.DatiRegistrazioniOut;
import it.eng.job.registri.bean.StampaRegistriProtocollazioneConfig;
import it.eng.xml.XmlListaUtility;

/* Il TipoReport puo' essere :
      -- PROTOCOLLO se sono registrazioni di protocollo generale.
	  -- REPERTORIO_ATTI se sono sono registrazioni di repertori atti.
	  -- REPERTORIO se sono sono registrazioni di qualsiasi altro repertorio.
	  -- PROPOSTE_ATTO se sono sono registrazioni di proposte atti.																	
 */

public class StampaRegProt {

	private static StampaRegProt instance;
    
	private org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(StampaRegProt.class);
	
	public static StampaRegProt getInstance() {
		if (instance == null) {
			instance = new StampaRegProt();
		}
		return instance;
	}

	public void stampaRegProt(File file, List<DatiRegistrazioniOut> datiRegistrazioniList, String header, String footer, StampaRegistriProtocollazioneConfig config)
			throws Exception {
		stampaRegProt(file, datiRegistrazioniList, header, footer, 0, "PROTOCOLLO", config);
	}

	public void stampaRegProt(File file, List<DatiRegistrazioniOut> datiRegistrazioniList, String header, String footer,
			int startPageNumber, StampaRegistriProtocollazioneConfig config) throws Exception {
		stampaRegProt(file, datiRegistrazioniList, header, footer, startPageNumber, "PROTOCOLLO", config);
	}

	public void stampaRegProt(File file, List<DatiRegistrazioniOut> datiRegistrazioniList, String header, String footer,
			int startPageNumber, String tipoReport, StampaRegistriProtocollazioneConfig config) throws Exception {
        
		logger.info("stampaRegProt - INIZIO");
		PdfPTable table = createTableDatiRegistrazioni(datiRegistrazioniList, tipoReport, config);

		Document document = new Document(PageSize.A3.rotate(), 10, 10, 25, 25);

		PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(file));
		writer.setPDFXConformance(PdfWriter.PDFXNONE);
		HeaderFooter event = new HeaderFooter(header, footer, startPageNumber);
		writer.setPageEvent(event);

		document.open();

		document.add(table);

		writer.createXmpMetadata();
		document.close();
		logger.info("stampaRegProt - FINE");
	}

	public void stampaRegProtMassive(File file, List<File> fileDatiRegList, String header, String footer,
			String tipoReport, StampaRegistriProtocollazioneConfig config) throws Exception {
        
		logger.info("stampaRegProtMassive - INIZIO");
		
		Document document = new Document(PageSize.A3.rotate(), 10, 10, 25, 25);
        
		Class klass = Document.class;
		URL location = klass.getResource('/' + klass.getName().replace('.', '/') + ".class");
		logger.info("location file: "+location.getFile());
		logger.info("location path: "+location.getPath());
		
		logger.info("document: "+document.getPageNumber()); 
		
		
		PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(file));
		writer.setPDFXConformance(PdfWriter.PDFXNONE);
		HeaderFooter event = new HeaderFooter(header, footer, 0);
		writer.setPageEvent(event);

		document.open();

		boolean skipFirstHeader = false;
		if (fileDatiRegList != null && fileDatiRegList.size() > 0) {
			for (int i = 0; i < fileDatiRegList.size(); i++) {
				String datiRegistrazioniXml = FileUtils.readFileToString(fileDatiRegList.get(i));
				logger.info("datiRegistrazioniXml "+datiRegistrazioniXml);
				List<DatiRegistrazioniOut> datiRegistrazioniList = XmlListaUtility.recuperaLista(datiRegistrazioniXml,
						DatiRegistrazioniOut.class);
				logger.info("datiRegistrazioniList "+datiRegistrazioniList.size());
				document.add(createTableDatiRegistrazioni(datiRegistrazioniList, skipFirstHeader, tipoReport, config));
				skipFirstHeader = writer.getVerticalPosition(false) < document.top();
			}
		} else {
			document.add(createTableDatiRegistrazioniEmpty(config));
		}
		writer.createXmpMetadata();
		document.close();
		logger.info("stampaRegProtMassive - FINE");
	}

	public PdfPTable createTableDatiRegistrazioni(List<DatiRegistrazioniOut> datiRegistrazioniList, StampaRegistriProtocollazioneConfig config) throws Exception {
		return createTableDatiRegistrazioni(datiRegistrazioniList, false, "PROTOCOLLO", config);
	}

	public PdfPTable createTableDatiRegistrazioni(List<DatiRegistrazioniOut> datiRegistrazioniList, String tipoReport, StampaRegistriProtocollazioneConfig config)
			throws Exception {
		return createTableDatiRegistrazioni(datiRegistrazioniList, false, tipoReport, config);
	}

	public PdfPTable createTableDatiRegistrazioni(List<DatiRegistrazioniOut> datiRegistrazioniList,
			boolean skipFirstHeader, String tipoReport, StampaRegistriProtocollazioneConfig config) throws Exception {
         
		logger.info("createTableDatiRegistrazioni - INIZIO");
		
		
		float[] configPercentageWidth = new float[config.getColsToPrint().length];

		PdfPTable table = new PdfPTable(config.getColsToPrint().length);
		table.setWidthPercentage(100);
        
		logger.info("table " +table.getSummary());
		
		// Setto le intestazioni
		for (int c = 0; c < config.getColsToPrint().length; c++) {
			PdfPCell cell = new PdfPCell(
					new Paragraph(StringEscapeUtils.unescapeHtml(config.getColsToPrint()[c].getIntestazione()),
							new Font(FontFamily.TIMES_ROMAN, 8, Font.NORMAL)));
			cell.setBackgroundColor(new BaseColor(0xC0, 0xC0, 0xC0));
			table.addCell(cell);

			configPercentageWidth[c] = config.getColsToPrint()[c].getPercentageWidth();
		}
		
		
		table.setHeaderRows(1);
		table.setSkipFirstHeader(skipFirstHeader);
        
		logger.info("table.getHeaderRows() " +table.getHeaderRows());
		
		if (datiRegistrazioniList != null && datiRegistrazioniList.size() > 0) {
			// Scrivo le righe
			for (DatiRegistrazioniOut datiReg : datiRegistrazioniList) {
				for (int c = 0; c < config.getColsToPrint().length; c++) {
					String value = null;
					for (Field lField : DatiRegistrazioniOut.class.getDeclaredFields()) {
						NumeroColonna lNumeroColonna = lField.getAnnotation(NumeroColonna.class);
						if (lNumeroColonna != null) {
							int numCol = new Integer(lNumeroColonna.numero()).intValue();
							if (numCol == config.getColsToPrint()[c].getNroColonna().intValue()) {
								value = BeanUtilsBean2.getInstance().getProperty(datiReg, lField.getName());
								logger.info("value " +value);
							}
						}
					}
					if (value == null) {
						value = "";
					}

					if (config.getColsToPrint()[c].getNroColonna().intValue() == 10) {
						PdfPCell lPdfPCell = new PdfPCell();
						if (StringUtils.isNotBlank(value)) {
							BarcodePDF417 barcode = new BarcodePDF417();
							barcode.setText(value);
							Image img = barcode.getImage();
							lPdfPCell.addElement(img);
						}
						lPdfPCell.setPadding(10);
						table.addCell(lPdfPCell);
						// columnWidth[c] = 120;
					} else {
						table.addCell(new PdfPCell(new Paragraph(StringEscapeUtils.unescapeHtml(value),
								new Font(FontFamily.TIMES_ROMAN, 8, Font.NORMAL))));
					}
				}
			}
		}

		// Calcolo le larghezze
		table.setTotalWidth(configPercentageWidth);
		logger.info("createTableDatiRegistrazioni - FINE");
		return table;
	}
	
	public PdfPTable createTableDatiRegistrazioniEmpty(StampaRegistriProtocollazioneConfig config) throws Exception {

		  float[] configPercentageWidth = new float[config.getColsToPrint().length];

		  PdfPTable table = new PdfPTable(config.getColsToPrint().length);
		  table.setWidthPercentage(100);

		  // Setto le intestazioni
		  for (int c = 0; c < config.getColsToPrint().length; c++) {
		   PdfPCell cell = new PdfPCell(
		     new Paragraph(StringEscapeUtils.unescapeHtml(config.getColsToPrint()[c].getIntestazione()),
		       new Font(FontFamily.TIMES_ROMAN, 8, Font.NORMAL)));
		   cell.setBackgroundColor(new BaseColor(0xC0, 0xC0, 0xC0));
		   table.addCell(cell);

		   configPercentageWidth[c] = config.getColsToPrint()[c].getPercentageWidth();
		  }
		  table.setHeaderRows(1);
		  

		  for (int c = 0; c < config.getColsToPrint().length; c++) {
				String value = "";

				if (config.getColsToPrint()[c].getNroColonna().intValue() == 10) {
					PdfPCell lPdfPCell = new PdfPCell();					
					lPdfPCell.setPadding(10);
					table.addCell(lPdfPCell);
					// columnWidth[c] = 120;
				} else {
					table.addCell(new PdfPCell(new Paragraph(StringEscapeUtils.unescapeHtml(value),
							new Font(FontFamily.TIMES_ROMAN, 8, Font.NORMAL))));
				}
			}
		  
		  // Calcolo le larghezze
		  table.setTotalWidth(configPercentageWidth);

		  return table;
	}

	static class HeaderFooter extends PdfPageEventHelper {

		private int pageNumber;
		private String header;
		private String footer;

		public HeaderFooter(String pHeader, String pFooter, int pStartPageNumber) {
			pageNumber = pStartPageNumber > 0 ? pStartPageNumber : 0;
			header = pHeader;
			footer = pFooter;
		}

		@Override
		public void onEndPage(PdfWriter writer, Document document) {
			pageNumber++;
			Font font = new Font(FontFamily.COURIER, 8, Font.NORMAL);
			ColumnText.showTextAligned(writer.getDirectContent(), Element.ALIGN_LEFT, new Phrase(header, font), 10,
					document.top() + 10, 0);
			ColumnText.showTextAligned(writer.getDirectContent(), Element.ALIGN_RIGHT,
					new Phrase(String.format("Pag. %d", pageNumber), font), document.left() + document.right() - 10,
					document.bottom() - 15, 0);
			ColumnText.showTextAligned(writer.getDirectContent(), Element.ALIGN_LEFT, new Phrase(footer, font), 10,
					document.bottom() - 15, 0);
		}

	}

	public static void concatFiles(List<File> fileList, File fileOutput) throws Exception {
		if (fileList.size() > 0) {
			PdfReader reader = new PdfReader(new FileInputStream(fileList.get(0)));
			Document document = new Document(reader.getPageSizeWithRotation(1));
			PdfCopy cp = new PdfCopy(document, new FileOutputStream(fileOutput));
			document.open();
			for (File file : fileList) {
				PdfReader r = new PdfReader(new FileInputStream(file));
				for (int k = 1; k <= r.getNumberOfPages(); ++k) {
					cp.addPage(cp.getImportedPage(r, k));
				}
				cp.freeReader(r);
			}
			cp.close();
			document.close();
		} else {
			throw new Exception("La lista dei pdf da concatenare è vuota");
		}
	}
}