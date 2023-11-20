/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.activation.MimeType;
import javax.activation.MimeTypeParseException;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.Hex;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.xssf.streaming.SXSSFCell;
import org.apache.poi.xssf.streaming.SXSSFRow;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFRichTextString;
import org.apache.tika.Tika;

import com.itextpdf.text.Document;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfImportedPage;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfWriter;

import it.eng.bean.ExecutionResultBean;
import it.eng.document.function.bean.FileInfoBean;
import it.eng.document.function.bean.GenericFile;
import it.eng.document.function.bean.TipoFile;

public class AurigaFileUtils {

	private static Logger logger = Logger.getLogger(AurigaFileUtils.class);

	/**
	 * Rimpiazza i . con _
	 * 
	 * @param nomeFile
	 * @return
	 */
	public static String pulisciNomeFile(String nomeFile) {
		if (StringUtils.isNotBlank(nomeFile))
			return nomeFile.replaceAll("[^a-zA-Z0-9-_\\.]", "_");
		else
			return null;
	}

	public static FileInfoBean createFileInfoBean(File currentFile, String improntaDefaultAlgoritmo,
			String improntaDefaultEncoding) throws IOException, MimeTypeParseException {
		String impronta = calcolaImpronta(currentFile, improntaDefaultAlgoritmo, improntaDefaultEncoding);

		MimeType mimeType = AurigaFileUtils.getMimeType(currentFile);

		GenericFile genericFile = new GenericFile();

		genericFile.setMimetype(mimeType.getPrimaryType() + "/" + mimeType.getSubType());
		genericFile.setDisplayFilename(currentFile.getName());
		genericFile.setImpronta(impronta);
		genericFile.setAlgoritmo(improntaDefaultAlgoritmo);
		genericFile.setEncoding(improntaDefaultEncoding);

		FileInfoBean fileInfoBean = new FileInfoBean();
		fileInfoBean.setTipo(TipoFile.PRIMARIO);
		fileInfoBean.setAllegatoRiferimento(genericFile);
		return fileInfoBean;
	}

	/**
	 * Calcola l'impronta del file specificato, utilizzando l'algoritmo e
	 * l'encoding forniti
	 * 
	 * @param file
	 * @param algoritmo
	 * @param encoding
	 * @return l'encoded hash oppure null se algoritmo od encoding non sono
	 *         validi oppure se si è verificata un'eccezione
	 * @throws Exception
	 */
	public static String calcolaImpronta(File file, String algoritmo, String encoding) {

		byte[] hash = null;

		String encodedHash = null;

		try {

			if (StringUtils.isBlank(algoritmo)) {
				logger.error("Algoritmo per il calcolo dell'impronta non valorizzato");
			} else if (algoritmo.equalsIgnoreCase("SHA-256")) {
				hash = DigestUtils.sha256(new BufferedInputStream(new FileInputStream(file)));
			} else if (algoritmo.equalsIgnoreCase("SHA-1")) {
				hash = DigestUtils.sha1(new BufferedInputStream(new FileInputStream(file)));
			} else {
				logger.error(String.format("L'algoritmo %1$s non è uno di quelli previsti", algoritmo));
			}

			if (StringUtils.isBlank(encoding)) {
				logger.error("Encoding per il calcolo dell'impronta non valorizzato");
			} else if (encoding.equalsIgnoreCase("base64")) {
				encodedHash = Base64.encodeBase64String(hash);
			} else if (encoding.equalsIgnoreCase("hex")) {
				encodedHash = Hex.encodeHexString(hash);
			} else {
				logger.error(String.format("L'encoding %1$s non è uno di quelli previsti", encoding));
			}
		} catch (Exception e) {
			logger.error("Durante il calcolo dell'impronta si è verificata la seguente eccezione", e);
		}

		return encodedHash;

	}

	/**
	 * Estrae il contenuto di un file, ritornandolo in formato stringa
	 * 
	 * @param src
	 * @return
	 * @throws IOException
	 */
	public static String readFile(File src) throws IOException {

		BufferedReader reader = new BufferedReader(new FileReader(src));
		StringBuilder builder = new StringBuilder();
		String nl = System.getProperty("line.separator");

		String readLine = null;

		while ((readLine = reader.readLine()) != null) {
			builder.append(readLine).append(nl);
		}

		reader.close();

		return builder.toString();
	}

	/**
	 * Determina il mime type del file specificato
	 * 
	 * @param src
	 * @return
	 * @throws MimeTypeParseException 
	 */
	public static MimeType getMimeType(File src) throws IOException, MimeTypeParseException {

		Tika tika = new Tika();
		String mimeType = tika.detect(src);
		
		return new MimeType(mimeType);
		
	}

	/**
	 * Effettua il merge di più file pdf in un unico documento
	 * 
	 * @param chunks
	 * @param mergedChunks
	 * @return
	 */
	public static ExecutionResultBean mergePdfs(List<File> chunks, File mergedChunks) {

		Document document = new Document();

		ExecutionResultBean retValue = new ExecutionResultBean();

		try {

			OutputStream outputStream = new BufferedOutputStream(new FileOutputStream(mergedChunks));

			PdfWriter writer = PdfWriter.getInstance(document, outputStream);

			document.open();

			PdfContentByte cb = writer.getDirectContent();

			for (File chunk : chunks) {

				InputStream currentStream = new BufferedInputStream(new FileInputStream(chunk));

				PdfReader reader = new PdfReader(currentStream);

				for (int i = 1; i <= reader.getNumberOfPages(); i++) {
					document.newPage();

					// import the page from source pdf
					PdfImportedPage page = writer.getImportedPage(reader, i);

					// add the page to the destination pdf
					cb.addTemplate(page, 0, 0);
				}

				currentStream.close();
				
				logger.info(String.format("Il file %1$s è stato mergiato", chunk.getAbsolutePath()));
			}

			outputStream.flush();
			document.close();
			outputStream.close();

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
	public static ExecutionResultBean mergeSezioneCache(List<File> chunks, File mergedChunks) {

		ExecutionResultBean retValue = new ExecutionResultBean();
		retValue.setSuccessful(true);

		String lineSeparator = System.getProperty("line.separator");

		try {

			BufferedWriter writer = new BufferedWriter(new FileWriter(mergedChunks));

			writer.write("<?xml version=\"1.0\" encoding=\"ISO-8859-1\"?>");
			writer.append(lineSeparator);

			writer.write("<Lista>");
			writer.append(lineSeparator);

			String line = null;

			for (File chunk : chunks) {

				BufferedReader reader = new BufferedReader(new FileReader(chunk));
				reader.readLine();

				while ((line = reader.readLine()) != null) {

					if (!line.contains("Lista")) {

						writer.append(line);
						writer.append(lineSeparator);

					}
				}

				reader.close();
				logger.info(String.format("Il file %1$s è stato mergiato", chunk.getAbsolutePath()));
			}

			writer.write("</Lista>");

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
	 * Determina il mime type del file specificato
	 * 
	 * @param src
	 * @return
	 * @throws MimeTypeParseException 
	 * @throws IOException 
	 */
	public synchronized static MimeType getMimeTypeSynchronized(File src) throws IOException, MimeTypeParseException {

		return getMimeType(src);
	}
}
