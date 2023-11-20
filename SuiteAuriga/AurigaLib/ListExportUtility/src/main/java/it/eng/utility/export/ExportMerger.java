/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.OutputStreamWriter;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.util.TempFile;
import org.apache.poi.xssf.streaming.SXSSFCell;
import org.apache.poi.xssf.streaming.SXSSFRow;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFRichTextString;

import it.eng.auriga.ui.module.layout.server.common.MergeDocument;
import it.eng.bean.ExecutionResultBean;

public class ExportMerger {

	private static Logger logger = Logger.getLogger(ExportMerger.class);
	
	/**
	 * Genera un unico xls a partire dalla lista di xls parziali specificata in
	 * ingresso
	 * 
	 * @param chunks
	 * @param mergedFile
	 * @return
	 */
	public static ExecutionResultBean mergeXls(List<File> chunks, File mergedFile) {

		ExecutionResultBean retValue = new ExecutionResultBean();
		retValue.setSuccessful(true);

		Map<Integer, HSSFCellStyle> styleMap = new HashMap<Integer, HSSFCellStyle>();

		try {

			HSSFWorkbook mergedWorkBook = new HSSFWorkbook();
			HSSFSheet mergedSheet = mergedWorkBook.createSheet("result");

			int rowNum = mergedSheet.getLastRowNum() + 1;

			for (File chunk : chunks) {

				HSSFWorkbook currentWorkbook = new HSSFWorkbook(new FileInputStream(chunk));

				HSSFSheet sheet = currentWorkbook.getSheetAt(0);

				int rowNums = sheet.getLastRowNum() + 1;

				for (int i = sheet.getFirstRowNum(); i < rowNums; i++) {

					HSSFRow srcRow = sheet.getRow(i);

					HSSFRow destRow = mergedSheet.createRow(rowNum++);

					if (srcRow != null) {

						copyRow(mergedWorkBook, srcRow, destRow, styleMap);

					}
				}

				currentWorkbook.close();
				logger.info(String.format("Il file %1$s è stato mergiato", chunk.getAbsolutePath()));
				
			}

			BufferedOutputStream outputStream = new BufferedOutputStream(new FileOutputStream(mergedFile));
			mergedWorkBook.write(outputStream);

			outputStream.flush();
			outputStream.close();

			retValue.setResult(mergedFile);

		} catch (Exception e) {

			String message = String.format(
					"Durante il merging dei file xls, si è verificata la seguente eccezione %1$s",
					ExceptionUtils.getFullStackTrace(e));
			logger.error(message);

			retValue.setSuccessful(false);
			retValue.setMessage(message);

		}

		return retValue;
	}
	
	public static void copyRow(HSSFWorkbook newWorkbook, HSSFRow srcRow, HSSFRow destRow,
			Map<Integer, HSSFCellStyle> styleMap) {

		destRow.setHeight(srcRow.getHeight());

		for (int j = srcRow.getFirstCellNum(); j < srcRow.getLastCellNum(); j++) {

			HSSFCell oldCell = srcRow.getCell(j);
			HSSFCell newCell = destRow.getCell(j);

			if (oldCell != null) {
				if (newCell == null) {
					newCell = destRow.createCell(j);
				}
				copyCell(newWorkbook, oldCell, newCell, styleMap);
			}
		}
	}
	
	public static void copyRow(SXSSFWorkbook newWorkbook, HSSFWorkbook parentWorkbook, HSSFRow srcRow, SXSSFRow destRow) {

		destRow.setHeight(srcRow.getHeight());

		 int minColIx = srcRow.getFirstCellNum();
		 int maxColIx = srcRow.getLastCellNum();
		 
		 for(int colIx = minColIx; colIx < maxColIx; colIx++) {
		   
			 HSSFCell oldCell = srcRow.getCell(colIx);

			if (oldCell != null) {
				
				SXSSFCell newCell = destRow.getCell(colIx);
				
				if (newCell == null) {
					newCell = destRow.createCell(colIx);
				}
				copyCell(newWorkbook, parentWorkbook, oldCell, newCell);
			}
		}
	}
	
	public static void copyCell(SXSSFWorkbook newWorkbook, HSSFWorkbook parentWorkbook, HSSFCell oldCell,
			SXSSFCell newCell) {

		//ho rimosso il codice di copia manuale dello stile in quanto corrompeva il documento

		switch (oldCell.getCellType()) {

		case HSSFCell.CELL_TYPE_STRING:

			// al momento non so come propagare tutte le formattazioni
			HSSFRichTextString richStringCellValue = oldCell.getRichStringCellValue();
			XSSFRichTextString value = new XSSFRichTextString();
			value.setString(richStringCellValue.getString());
			newCell.setCellValue(value);
			break;
		case HSSFCell.CELL_TYPE_NUMERIC:
			newCell.setCellValue(oldCell.getNumericCellValue());
			break;
		case HSSFCell.CELL_TYPE_BLANK:
			newCell.setCellType(HSSFCell.CELL_TYPE_BLANK);
			break;
		case HSSFCell.CELL_TYPE_BOOLEAN:
			newCell.setCellValue(oldCell.getBooleanCellValue());
			break;
		case HSSFCell.CELL_TYPE_ERROR:
			newCell.setCellErrorValue(oldCell.getErrorCellValue());
			break;
		case HSSFCell.CELL_TYPE_FORMULA:
			newCell.setCellFormula(oldCell.getCellFormula());
			break;
		default:
			break;
		}
	}
	
	public static void copyCell(HSSFWorkbook newWorkbook, HSSFCell oldCell, HSSFCell newCell,
			Map<Integer, HSSFCellStyle> styleMap) {

		if (styleMap != null) {

			int stHashCode = oldCell.getCellStyle().hashCode();
			HSSFCellStyle newCellStyle = styleMap.get(stHashCode);

			if (newCellStyle == null) {

				newCellStyle = newWorkbook.createCellStyle();
				newCellStyle.cloneStyleFrom(oldCell.getCellStyle());
				styleMap.put(stHashCode, newCellStyle);

			}

			newCell.setCellStyle(newCellStyle);
		}

		switch (oldCell.getCellType()) {

		case HSSFCell.CELL_TYPE_STRING:
			newCell.setCellValue(oldCell.getRichStringCellValue());
			break;
		case HSSFCell.CELL_TYPE_NUMERIC:
			newCell.setCellValue(oldCell.getNumericCellValue());
			break;
		case HSSFCell.CELL_TYPE_BLANK:
			newCell.setCellType(HSSFCell.CELL_TYPE_BLANK);
			break;
		case HSSFCell.CELL_TYPE_BOOLEAN:
			newCell.setCellValue(oldCell.getBooleanCellValue());
			break;
		case HSSFCell.CELL_TYPE_ERROR:
			newCell.setCellErrorValue(oldCell.getErrorCellValue());
			break;
		case HSSFCell.CELL_TYPE_FORMULA:
			newCell.setCellFormula(oldCell.getCellFormula());
			break;
		default:
			break;
		}
	}
	
	/**
	 * Data la lista di files in ingresso, ciascuno dei quali contenente un
	 * sezione cache, genera un file xml globale coerente contenente tutte le
	 * righe in un'unica lista
	 * 
	 * @param chunks
	 * @param mergedChunks
	 * @return
	 */
	public static ExecutionResultBean mergeDataExport(List<File> chunks, File mergedChunks, int numTotRecord) {

		ExecutionResultBean retValue = new ExecutionResultBean();
		retValue.setSuccessful(true);

		String lineSeparator = System.getProperty("line.separator");

		try {

			BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(mergedChunks), "ISO-8859-1"));

			writer.write("<?xml version=\"1.0\" encoding=\"ISO-8859-1\" standalone=\"yes\"?>");
			writer.append(lineSeparator);

			writer.write("<DataExport>");
			writer.append(lineSeparator);

			String line = null;

			boolean firstTime = true;
			
			for (File chunk : chunks) {

				BufferedReader reader = new BufferedReader(new FileReader(chunk));
				reader.readLine();

				while ((line = reader.readLine()) != null) {

					if(firstTime && line.contains("Resultset RecNum")) {
						
						int dataExportIndex = line.toLowerCase().indexOf("<dataexport>"); 
						
						if(dataExportIndex > -1) {
							line = line.substring(dataExportIndex + 12);
						}
						
						int indexOf = line.toLowerCase().indexOf("recnum=\"");
						
						String tmp = line.substring(indexOf + 9);
						
						line = line.substring(0, indexOf + 9) + Integer.toString(numTotRecord) + tmp.substring(tmp.indexOf("\""));
						
						writer.append(line);
						writer.append(lineSeparator);
						
						firstTime = false;
					}
					
					boolean canWrite = !line.contains("DataExport") && !line.contains("Resultset");
					
					if (canWrite) {

						writer.append(line);
						writer.append(lineSeparator);

					}
				}

				reader.close();
				logger.info(String.format("Il file %1$s è stato mergiato", chunk.getAbsolutePath()));
			}

			writer.write("</Resultset>");
			writer.write("</DataExport>");

			writer.flush();
			writer.close();

			retValue.setResult(mergedChunks);

		} catch (Exception e) {

			String message = String.format(
					"Durante il merge dei vari spezzoni di esportazione si è verificata la seguente eccezione %1$s",
					ExceptionUtils.getFullStackTrace(e));
			logger.error(message);

			retValue.setSuccessful(false);
			retValue.setMessage(message);
		}

		return retValue;

	}
	
	/**
	 * Riversa i contenuti dei vari spezzoni forniti in ingresso nel file
	 * specificato, appendendo i vari contenuti
	 * 
	 * @param chunks
	 * @param mergedChunks
	 */
	public static ExecutionResultBean mergeCsv(List<File> chunks, File mergedChunks) {

		ExecutionResultBean retValue = new ExecutionResultBean();
		retValue.setSuccessful(true);
		
		String lineSeparator = System.getProperty("line.separator");

		try {

			BufferedWriter writer = new BufferedWriter(new FileWriter(mergedChunks));

			String line = null;

			for (File chunk : chunks) {

				BufferedReader reader = new BufferedReader(new FileReader(chunk));

				while ((line = reader.readLine()) != null) {
					writer.append(line);
					writer.append(lineSeparator);
				}

				reader.close();
				
				logger.info(String.format("Il file %1$s è stato mergiato", chunk.getAbsolutePath()));
			}

			writer.flush();
			writer.close();

			retValue.setResult(mergedChunks);
			
		} catch (Exception e) {

			String message = String.format(
					"Durante il merge dei vari spezzoni di esportazione si è verificata la seguente eccezione %1$s",
					ExceptionUtils.getFullStackTrace(e));
			logger.error(message);

			retValue.setSuccessful(false);
			retValue.setMessage(message);
		}

		return retValue;
	}
	
	/**
	 * Genera un unico xlsx a partire dalla lista di xls parziali specificata in
	 * ingresso
	 * 
	 * @param chunks
	 * @param mergedFile
	 * @return
	 */
	public static ExecutionResultBean mergeXlsx(List<File> chunks, File mergedFile) {

		ExecutionResultBean retValue = new ExecutionResultBean();
		retValue.setSuccessful(true);

		try {

			File dir = new File(System.getProperty("java.io.tmpdir"),"poifiles");
			dir.mkdir();
			TempFile.setTempFileCreationStrategy(new TempFile.DefaultTempFileCreationStrategy(dir));
			
			SXSSFWorkbook mergedWorkBook = new SXSSFWorkbook(100);
			SXSSFSheet mergedSheet = mergedWorkBook.createSheet("result");

			int rowNum = mergedSheet.getLastRowNum() + 1;

			for (File chunk : chunks) {

				logger.debug("Merging file " + chunk.getAbsolutePath());

				HSSFWorkbook currentWorkbook = new HSSFWorkbook(new FileInputStream(chunk));

				HSSFSheet currentSheet = currentWorkbook.getSheetAt(0);

				int currentSheetRowNums = currentSheet.getLastRowNum() + 1;

				for (int i = currentSheet.getFirstRowNum(); i < currentSheetRowNums; i++) {

					HSSFRow srcRow = currentSheet.getRow(i);

					SXSSFRow destRow = mergedSheet.createRow(rowNum++);

					if (srcRow != null) {

						copyRow(mergedWorkBook, currentWorkbook, srcRow, destRow);

					}

				}
				currentWorkbook.close();
				logger.info(String.format("Il file %1$s è stato mergiato", chunk.getAbsolutePath()));
			}

			BufferedOutputStream outputStream = new BufferedOutputStream(
					new FileOutputStream(mergedFile.getAbsolutePath()));
			mergedWorkBook.write(outputStream);
			mergedWorkBook.close();

			outputStream.flush();
			outputStream.close();

			retValue.setResult(mergedFile);

		} catch (Exception e) {

			String message = String.format(
					"Durante il merging dei file xls, si è verificata la seguente eccezione %1$s",
					ExceptionUtils.getFullStackTrace(e));
			logger.error(message);

			retValue.setSuccessful(false);
			retValue.setMessage(message);

		}

		return retValue;
	}

	/**
	 * Effettua il merge di più file pdf in un unico documento
	 * 
	 * @param chunks
	 * @param mergedChunks
	 * @return
	 */
	public static ExecutionResultBean mergePdfs(List<File> chunks, File mergedChunks) {

		ExecutionResultBean retValue = new ExecutionResultBean();

		try {
            /*
             * [HELPDESK #0000019] EXPORT REPORT CRUSCOTTO MAIL
             * Tutti le liste vengono forzate con forceA4 = true
             * Questo troncava le colonne che eccedevano il formato A4
             * Modifico il metodo mettendo quello con A4 = false
             */
			//MergeDocument.newInstance().mergeDocument(chunks, new BufferedOutputStream(new FileOutputStream(mergedChunks)));
			MergeDocument.newInstance().mergeDocument(chunks, new BufferedOutputStream(new FileOutputStream(mergedChunks)),false);
			retValue.setSuccessful(true);
			retValue.setResult(mergedChunks);

		} catch (Exception e) {

			String message = String.format("Durante il merge del pdf si è verificata la seguente eccezione %1$s",
					ExceptionUtils.getFullStackTrace(e));

			logger.error(message);

			retValue.setSuccessful(false);
			retValue.setMessage(message);

		}

		return retValue;
	}
}
