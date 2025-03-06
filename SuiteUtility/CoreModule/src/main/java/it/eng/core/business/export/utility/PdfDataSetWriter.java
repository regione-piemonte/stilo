
package it.eng.core.business.export.utility;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.dbunit.dataset.Column;
import org.dbunit.dataset.DataSetException;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.ITable;
import org.dbunit.dataset.ITableMetaData;
import org.dbunit.dataset.csv.CsvDataSet;
import org.dbunit.dataset.datatype.DataType;
import org.dbunit.dataset.datatype.TypeCastException;
import org.dbunit.dataset.stream.DataSetProducerAdapter;
import org.dbunit.dataset.stream.IDataSetConsumer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.Font.FontFamily;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

public class PdfDataSetWriter implements IDataSetConsumer {

	/**
	 * Logger for this class
	 */
	private static final Logger logger = LoggerFactory.getLogger(PdfDataSetWriter.class);

	public static final String NULL = "null";
	private static final String NONE = "none";

	private static final String QUOTE = "\"";
	private static final String ESCAPE = "\\";

	private Writer writer;
	private ITableMetaData _activeMetaData;
	private String theDirectory;
	private String fileName;
	private static char testExport;
	/** list of tables */
	private List tableList;

	/** the pdf document **/
	private Document document = new Document();
	/** the dynamic table **/
	private PdfPTable table;
	/** counter of record */
	private int numRec = 0;
	/* size colonne **/
	int[] columnWidth;
	/* colonne da inserire se null o vuoto le inserisci tutte */
	private HashMap<String, String> includeColumns = null;
	/* posizione delle colonne scelte */
	private ArrayList<Integer> includeColumnsPos = new ArrayList<Integer>();
	private static final Font font_10 = new Font(FontFamily.TIMES_ROMAN, 8, Font.NORMAL);

	public PdfDataSetWriter(String theDirectory, String fileName) {
		setTheDirectory(theDirectory);
		setFileName(fileName);
	}

	public void write(IDataSet dataSet) throws DataSetException {
		logger.debug("write(dataSet={}) - start", dataSet);

		DataSetProducerAdapter provider = new DataSetProducerAdapter(dataSet);
		provider.setConsumer(this);
		provider.produce();
	}

	private static void addEmptyLine(Paragraph paragraph, int number) {
		for (int i = 0; i < number; i++) {
			paragraph.add(new Paragraph(" "));
		}
	}

	public void startDataSet() throws DataSetException {
		logger.debug("startDataSet() - start");

		try {

			tableList = new LinkedList();
			new File(getTheDirectory()).mkdirs();
			// creo un solo file invece di uno per ogni tabella
			PdfWriter.getInstance(document, new FileOutputStream(getTheDirectory() + File.separator + getFileName() + ".pdf"));
			document.open();
			// aggiungo i metadati
			document.addTitle(getFileName());
			document.addSubject("export iris " + new Date());
			document.addKeywords("export, iris");
			document.addAuthor("export module");
			document.addCreator("export Module");
			Paragraph preface = new Paragraph();
			preface.add(new Paragraph("Export data " + new Date(), font_10));
			addEmptyLine(preface, 1);
			document.add(preface);
		} catch (Exception e) {
			throw new DataSetException("Error while creating the destination directory '" + getTheDirectory() + "'", e);
		}
	}

	public void endDataSet() throws DataSetException {
		logger.debug("endDataSet() - start");

		// write out table ordering file
		File orderingFile = new File(getTheDirectory(), CsvDataSet.TABLE_ORDERING_FILE);

		PrintWriter pw = null;
		try {
			// chiudo il document
			document.close();
			pw = new PrintWriter(new FileWriter(orderingFile));
			for (Iterator fileNames = tableList.iterator(); fileNames.hasNext();) {
				String file = (String) fileNames.next();
				pw.println(file);
			}
		} catch (IOException e) {
			throw new DataSetException("problems writing the table ordering file or closing document", e);
		} finally {
			if (pw != null) {
				pw.close();
			}
		}
	}

	public void startTable(ITableMetaData metaData) throws DataSetException {
		logger.debug("startTable(metaData={}) - start", metaData);

		try {
			_activeMetaData = metaData;
			String tableName = _activeMetaData.getTableName();
			// calcolo le colonne da inserire nell'export
			Column[] columns = _activeMetaData.getColumns();
			for (int i = 0; i < columns.length; i++) {
				// se passi null le devi inserire tutte
				if (includeColumns == null || includeColumns.size() == 0) {
					includeColumnsPos.add(i);
				} else {
					if (includeColumns.containsKey(columns[i].getColumnName())) {
						includeColumnsPos.add(i);
					}
				}
			}
			writeColumnNames();
			// getWriter().write(System.getProperty("line.separator"));
		} catch (Exception e) {
			throw new DataSetException(e);
		}

	}

	private void writeColumnNames() throws DataSetException, IOException, DocumentException {
		logger.debug("writeColumnNames() - start");

		Column[] columns = _activeMetaData.getColumns();
		columnWidth = new int[includeColumnsPos.size()];
		// create the table
		int columSize = includeColumnsPos.size();
		table = new PdfPTable(columSize);
		table.setWidthPercentage(100);
		table.setComplete(false);
		for (int i = 0; i < includeColumnsPos.size(); i++) {
			// prendi la colonna alla posizione specificata per l'export
			String columnName = columns[includeColumnsPos.get(i)].getColumnName();
			// verifica se è specificato un custom title
			String title = getCustomTitle(columnName);
			PdfPCell c1 = new PdfPCell(new Paragraph(title, font_10));
			// c1.setHorizontalAlignment(Element.ALIGN_CENTER);
			c1.setBackgroundColor(new BaseColor(0xC0, 0xC0, 0xC0));
			table.addCell(c1);
			columnWidth[i] = title.length();

		}

		table.setHeaderRows(1);
		// cerco di impostare le larghezze
		int maxLength = 0;
		for (int i = 0; i < columnWidth.length; i++) {
			maxLength += columnWidth[i];
		}
		float[] percentageColumnWidth = new float[columnWidth.length];
		for (int i = 0; i < columnWidth.length; i++) {
			percentageColumnWidth[i] = (new Float(((double) columnWidth[i] / (double) maxLength) * 100)).floatValue();
		}
		table.setTotalWidth(percentageColumnWidth);
		if (maxLength > 200) {
			document.setPageSize(PageSize.A3.rotate());

		} else if (maxLength > 140) {
			document.setPageSize(PageSize.A4.rotate());
		} else {
			document.setPageSize(PageSize.A4);
		}
	}

	public void endTable() throws DataSetException {
		logger.debug("endTable() - start");

		try {
			// cerco di impostare le larghezze
			int maxLength = 0;
			for (int i = 0; i < columnWidth.length; i++) {
				maxLength += columnWidth[i];
			}
			float[] percentageColumnWidth = new float[columnWidth.length];
			for (int i = 0; i < columnWidth.length; i++) {
				percentageColumnWidth[i] = (new Float(((double) columnWidth[i] / (double) maxLength) * 100)).floatValue();
			}
			table.setTotalWidth(percentageColumnWidth);
			document.setPageSize(PageSize.A3.rotate());
			// if(maxLength>200){
			// document.setPageSize(PageSize.A3.rotate() );
			//
			// }else if(maxLength>150){
			// document.setPageSize(PageSize.A4.rotate() );
			// }else{
			// document.setPageSize(PageSize.A4 );
			// }
			table.setComplete(true);
			document.add(table);// add last chunck

			tableList.add(_activeMetaData.getTableName());
			_activeMetaData = null;
		} catch (Exception e) {
			throw new DataSetException(e);
		}
	}

	public void row(Object[] values) throws DataSetException {
		logger.debug("row(values={}) - start", values);

		try {

			Column[] columns = _activeMetaData.getColumns();
			for (int i = 0; i < includeColumnsPos.size(); i++) {
				String columnName = columns[includeColumnsPos.get(i)].getColumnName();
				Object value = values[includeColumnsPos.get(i)];

				// null
				if (value == null) {
					table.addCell(new Paragraph(NULL, font_10));
				}
				// none
				else if (value == ITable.NO_VALUE) {
					table.addCell(new Paragraph(NONE, font_10));
				}
				// values
				else {
					try {
						String stringValue = DataType.asString(value);
						final String quoted = quote(stringValue);
						if (quoted.length() > columnWidth[i]) {
							columnWidth[i] = quoted.length();
						}
						// getWriter().write(quoted);
						table.addCell(new Paragraph(stringValue, font_10));
					} catch (TypeCastException e) {
						throw new DataSetException("table=" + _activeMetaData.getTableName() + ", row=" + i + ", column=" + columnName + ", value=" + value, e);
					}
				}
			}

			logger.info("export record ..." + numRec);
			numRec++;
			// add row to table
			if (numRec % 5000 == 0)
				;
			document.add(table);
			// getWriter().write(System.getProperty("line.separator"));
		} catch (Exception e) {
			throw new DataSetException(e);
		}
	}

	private String quote(String stringValue) {
		logger.debug("quote(stringValue={}) - start", stringValue);

		return new StringBuffer(QUOTE).append(escape(stringValue)).append(QUOTE).toString();
	}

	protected static String escape(String stringValue) {
		logger.debug("escape(stringValue={}) - start", stringValue);

		char[] array = stringValue.toCharArray();
		testExport = QUOTE.toCharArray()[0];
		final char escape = ESCAPE.toCharArray()[0];
		StringBuffer buffer = new StringBuffer();
		for (int i = 0; i < array.length; i++) {
			char c = array[i];
			if (c == testExport || c == escape) {
				buffer.append('\\');
			}
			buffer.append(c);
		}
		return buffer.toString();
	}

	public Writer getWriter() {
		logger.debug("getWriter() - start");

		return writer;
	}

	public void setWriter(Writer writer) {
		logger.debug("setWriter(writer={}) - start", writer);

		this.writer = writer;
	}

	public String getTheDirectory() {
		logger.debug("getTheDirectory() - start");

		return theDirectory;
	}

	public void setTheDirectory(String theDirectory) {
		logger.debug("setTheDirectory(theDirectory={}) - start", theDirectory);

		this.theDirectory = theDirectory;
	}

	public Document getDocument() {
		return document;
	}

	public void setDocument(Document document) {
		this.document = document;
	}

	public PdfPTable getTable() {
		return table;
	}

	public void setTable(PdfPTable table) {
		this.table = table;
	}

	public static void write(IDataSet dataset, String folder, String file, HashMap<String, String> mapping) throws DataSetException {
		logger.debug("write(dataset={}, dest={}) - start", dataset, folder);
		PdfDataSetWriter writer = new PdfDataSetWriter(folder, file);
		writer.setIncludeColumns(mapping);
		writer.write(dataset);
	}

	protected void finalize() throws Throwable {
		logger.debug("finalize() - start");

		if (getWriter() != null) {
			getWriter().close();
		}
		// if(getTable()!=null){
		// getTable().;
		// }
		if (getDocument() != null && getDocument().isOpen()) {
			getDocument().close();
		}
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public HashMap<String, String> getIncludeColumns() {
		return includeColumns;
	}

	public void setIncludeColumns(HashMap<String, String> includeColumns) {
		this.includeColumns = includeColumns;
	}

	private String getCustomTitle(String colName) {
		String ret = colName;
		if (includeColumns != null && includeColumns.size() > 0) {
			if (includeColumns.containsKey(colName)) {
				ret = includeColumns.get(colName);
			}
		}
		return ret;
	}

}
