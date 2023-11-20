/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.Reader;
import java.io.StringReader;
import java.io.Writer;

import javax.persistence.Entity;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.OfficeXmlFileException;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;

import it.eng.gd.lucenemanager.bean.FileVO;
import it.eng.gd.lucenemanager.document.AbstractDocumentReader;

/**
 * Excel document Reader
 * 
 * @author Administrator
 * 
 */
@Entity
public class ExcelDocumentReader extends AbstractDocumentReader {

	static Logger aLogger = Logger.getLogger(ExcelDocumentReader.class);

	public ExcelDocumentReader() {
		super();
	}

	public Reader getContent(File file) throws Exception {
		Writer wr = null;
		try {
			POIFSFileSystem fs = new POIFSFileSystem(new FileInputStream(file));
			HSSFWorkbook wb = (fs != null) ? new HSSFWorkbook(fs) : null;
			int numFogli = wb.getNumberOfSheets();

			// istanzio il file e il relativo writer
			File fi = getRandomFile();
			wr = new FileWriter(fi);

			for (int f = 0; f < numFogli; f++) {
				HSSFSheet foglio = wb.getSheetAt(f);
				HSSFRow riga;
				HSSFCell cella;
				int righe; // n. di righe
				righe = foglio.getPhysicalNumberOfRows();
				int cols = 0; // n. di colonne
				for (int r = 0; r < righe; r++) {
					riga = foglio.getRow(r);
					if (riga != null) {
						cols = riga.getPhysicalNumberOfCells();
						for (int c = 0; c < cols; c++) {
							cella = riga.getCell(c);
							if (cella != null && cella.getCellType() == HSSFCell.CELL_TYPE_STRING) {
								wr.write(cella.getRichStringCellValue() + " ");
							}
						}
					}
				}
			}
			return new FSTemporaryFileReader(fi);
		} catch (OfficeXmlFileException e) {
			// si tratta di un docx e devo convertirlo come tale
			// XSSFExcelExtractor ex = new XSSFExcelExtractor(file.getAbsolutePath());
			String str = "";// ex.getText();

			return new StringReader(str);
		} catch (Exception e) {
			aLogger.error(e.getMessage(), e);
			throw e;
		} finally {
			if (wr != null)
				try {
					wr.close();
				} catch (Exception e) {
				}
		}
	}

	@Override
	public FileVO[] getContents(File file, String mimetype) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

}
