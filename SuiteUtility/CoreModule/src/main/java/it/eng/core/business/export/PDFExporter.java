package it.eng.core.business.export;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtilsBean2;
import org.apache.log4j.Logger;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.Font;
import com.itextpdf.text.Font.FontFamily;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import it.eng.core.business.export.utility.ExportInfo;

public class PDFExporter extends AbstractExportListener {

	Logger log = Logger.getLogger(PDFExporter.class);

	private PdfPTable table;// dynamic data
	/* info di export */
	private ExportInfo info;
	private Document document = new Document();
	/* size colonne **/
	int[] columnWidth;
	// conterrà le prop da inserire nell'export
	private ArrayList<String> includeProperties = new ArrayList<String>();

	private static final Font font_10 = new Font(FontFamily.TIMES_ROMAN, 8, Font.NORMAL);

	public PDFExporter(ExportInfo info) throws Exception {
		this.info = info;

	}

	public void onInit(ExportEvent ee) throws Exception {

		new File(info.getBaseFolder()).mkdirs();
		// creo un solo file invece di uno per ogni tabella
		PdfWriter.getInstance(document, new FileOutputStream(info.getBaseFolder() + File.separator + info.getFileName() + ".pdf"));
		document.open();
		// aggiungo i metadati
		document.addTitle(info.getFileName());
		document.addSubject("export iris " + new Date());
		document.addKeywords("export, iris");
		document.addAuthor("export module");
		document.addCreator("export Module");
		Paragraph preface = new Paragraph();
		preface.add(new Paragraph("Export data " + new Date(), font_10));
		addEmptyLine(preface, 1);
		document.add(preface);

	}

	@Override
	public void onData(ExportEvent ee) throws Exception {
		if (ee.getNumRec() == 0) {
			boolean useCustomTitle = false;
			Map<String, Object> map = BeanUtilsBean2.getInstance().getPropertyUtils().describe(ee.getRecord());
			for (String propName : map.keySet()) {
				// alway esclude class prop
				if (!propName.equals("class")) {
					// calcola le prop da inserire nell'export
					if (info.getColumMapping() == null || info.getColumMapping().size() == 0) {
						includeProperties.add(propName);
					} else if (info.getColumMapping().containsKey(propName)) {
						useCustomTitle = true;
						includeProperties.add(propName);
					}
				}
			}
			// TODO use column with per formattare la pagina
			columnWidth = new int[includeProperties.size()];
			// crea l'intestazione
			int columSize = includeProperties.size();
			table = new PdfPTable(columSize);
			table.setWidthPercentage(100);
			table.setComplete(false);
			for (int i = 0; i < includeProperties.size(); i++) {
				// prendi la colonna alla posizione specificata per l'export
				// prendi il titolo passato non il nome della prop
				String columnName = includeProperties.get(i);
				String title = columnName;
				// prendo il titolo
				if (useCustomTitle) {
					title = info.getColumMapping().get(title);
				}
				PdfPCell c1 = new PdfPCell(new Paragraph(title, font_10));
				// c1.setHorizontalAlignment(Element.ALIGN_CENTER);
				c1.setBackgroundColor(new BaseColor(0xC0, 0xC0, 0xC0));
				table.addCell(c1);
				columnWidth[i] = columnName.length();
			}

			table.setHeaderRows(1);
		}
		// record da scrivere nel file
		Object record = ee.getRecord();
		// SerializationHelper.serialize((Serializable)record );

		for (String propName : includeProperties) {
			// String value=Stringifier.stringify(BeanUtilsBean2.getInstance().getProperty(record,propName));
			Object objValue = BeanUtilsBean2.getInstance().getPropertyUtils().getProperty(record, propName);
			String value = Stringifier.stringify(objValue);
			if (value != null)// stampa soo quelli stringabili
				table.addCell(new Paragraph(value, font_10));
		}
		// if(numRec %5000 ==0) ;
		document.add(table);

	}

	public void onFinish(ExportEvent ee) throws Exception {

		table.setComplete(true);
		// add the table to the document
		document.add(table);
		document.close();
	}

	@Override
	public void onRunning(ExportEvent ee) throws Exception {
		log.info("Elaborato record " + ee.getNumRec());

	}

	private static void addEmptyLine(Paragraph paragraph, int number) {
		for (int i = 0; i < number; i++) {
			paragraph.add(new Paragraph(" "));
		}
	}
}
