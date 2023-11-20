/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.openxml4j.opc.PackageAccess;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.eventusermodel.XSSFReader;
import org.apache.poi.xssf.model.SharedStringsTable;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.hibernate.Session;
import org.xml.sax.ContentHandler;
import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLReaderFactory;

import it.eng.auriga.module.business.beans.AurigaLoginBean;
import it.eng.auriga.module.business.dao.beans.JobBean;
import it.eng.bean.ExecutionResultBean;
import it.eng.core.business.HibernateUtil;
import it.eng.document.function.bean.RigaEstrazioneDocumento;
import it.eng.document.function.excel.DDTSheetHandler;

public class DocumentExporterManager {

	private static final Logger log = Logger.getLogger(DocumentExporterManager.class);
	
	public static final String PATH_DIR_EXPORT = "PATH_DIR_EXPORT";
	public static final String COUNT_OK = "COUNT_OK";
	public static final String COUNT_KO = "COUNT_KO";
	public static final String LAST_ROW_ELAB = "LAST_ROW_ELAB";
	
	private AurigaLoginBean aurigaLoginBean;
	private JobBean currentJob;
	private ExecutorService executorService;
	private List<Future<ExecutionResultBean<File>>> futures = new ArrayList<Future<ExecutionResultBean<File>>>();
	private List<ExecutionResultBean<File>> fatalErrors = new ArrayList<ExecutionResultBean<File>>();
	private File expDir;
	private Map<Integer, String> titoli = new HashMap<Integer, String>();

	private Sheet foglioOut;

	private ExecutionResultBean<File> elaboraFoglioResult;

	
	public DocumentExporterManager(AurigaLoginBean aurigaLoginBean, JobBean currentJob, ExecutorService executorService, String tempPath) throws IOException {
		this.aurigaLoginBean = aurigaLoginBean;
		this.currentJob = currentJob;
		this.executorService = executorService;
		this.expDir = new File(tempPath, "DOCEXPMGR_J" + currentJob.getIdJob() + "_" + System.currentTimeMillis());
		FileUtils.forceMkdir(expDir);
	}
	
	
	public ExecutionResultBean<File> elaboraFoglio(AurigaLoginBean aurigaLoginBean, JobBean currentJob, File inputFile) {
		elaboraFoglioResult = new ExecutionResultBean<File>();

		OPCPackage pkg = null;
		InputStream sheet = null;
		String sheetName = null;
		try {
			pkg = OPCPackage.open(inputFile.getCanonicalPath(), PackageAccess.READ);
			XSSFReader r = new XSSFReader( pkg );
			SharedStringsTable sst = r.getSharedStringsTable();
			XMLReader parser =
					XMLReaderFactory.createXMLReader(
							"org.apache.xerces.parsers.SAXParser"
					);
			ContentHandler handler = new DDTSheetHandler(sst, this);
			parser.setContentHandler(handler);
			//recupera il primo foglio disponibile
			XSSFReader.SheetIterator itSheets = (XSSFReader.SheetIterator) r.getSheetsData();
			while(itSheets.hasNext()) {
				sheet = itSheets.next();
	            sheetName = itSheets.getSheetName();
				break;
			}
			InputSource sheetSource = new InputSource(sheet);
			//parser.parse(sheetSource);
			
			//ExecutionResultBean<File> exportResult = getRisultato();
			SXSSFWorkbook wb = null;
			try {
				wb = new SXSSFWorkbook(100);
				foglioOut = wb.createSheet();
				
				elaboraFoglioResult.addAdditionalInformation(COUNT_OK, BigInteger.ZERO);
				elaboraFoglioResult.addAdditionalInformation(COUNT_KO, BigInteger.ZERO);

				//leggo il doc excel
				parser.parse(sheetSource);
		        
				File expResultFile = new File(this.expDir, this.expDir.getName() + ".xlsx");
				FileOutputStream out = new FileOutputStream(expResultFile);
				elaboraFoglioResult.addAdditionalInformation("CESTINO", expResultFile);
		        wb.write(out);
		        out.close();
		        wb.dispose();
				
		        elaboraFoglioResult.getAdditionalInformations().remove("CESTINO");
		        elaboraFoglioResult.setResult(expResultFile);
		        elaboraFoglioResult.addAdditionalInformation(PATH_DIR_EXPORT, expDir);
				
				elaboraFoglioResult.setSuccessful(true);
				
			} catch (Exception e) {
				log.error(String.format("Durante l'estrazione dei documenti legati al job %s, si è verificata la seguente eccezione",
							currentJob.getIdJob()), e);
				elaboraFoglioResult.addAdditionalInformation("EXCEPTION", e);
				elaboraFoglioResult.setSuccessful(false);
			} finally {
				if (wb != null) try {wb.close();} catch(Exception e) {};
			}
				
		} catch (Exception e) {
			String message = String.format(
					"Durante l'elaborazione del file excel %2$s per il job %1$s si è verificata la seguente eccezione",
					currentJob.getIdJob(),
					sheetName != null ? "[" + sheetName + "]" : "");

			log.error(message, e);

			elaboraFoglioResult.setSuccessful(false);
			elaboraFoglioResult.addAdditionalInformation("EXCEPTION", e);
		} finally {
			if (sheet != null) try {sheet.close();} catch(Exception e){};
			if (pkg != null) try {pkg.revert();} catch(Exception e){};
		}
		
		return elaboraFoglioResult;
	}

	
	/**
	 * Esporta i documenti cui una riga fa riferimento
	 * 
	 * @param rigaIndice
	 * @throws Exception 
	 */
	public void exportDDT(RigaEstrazioneDocumento rigaIndice) {
		ExecutionResultBean<File> exportDDTResult = new ExecutionResultBean<File>();
		exportDDTResult.addAdditionalInformation(DDTExporter.INFO_RIGA_INDICE, rigaIndice);;
		try {
			//Session session = HibernateUtil.begin();
			DDTExporter ddtExporter = new DDTExporter(aurigaLoginBean, currentJob, rigaIndice, this.expDir);
			//futures.add(executorService.submit(ddtExporter));
			exportDDTResult = ddtExporter.call();
		} catch (Exception e) {
			log.error(String.format(
					"Durante l'estrazione del documento alla riga %1$s, job %2$s, si è verificata la seguente eccezione",
					rigaIndice.getNumeroRiga(), currentJob.getIdJob()), e);
			exportDDTResult.setSuccessful(false);
			exportDDTResult.setMessage(String.format("%s%n%s", ExceptionUtils.getMessage(e), ExceptionUtils.getRootCauseMessage(e)));
			//fatalErrors.add(result);
		} finally {
			generaRigaExcel(exportDDTResult);
		}
	}

	/**
	 * Aggiunge il titolo di una colonna, utile in fase di scrittura del file excel risultato
	 * 
	 * @param column
	 * @param lastContents
	 */
	public void addColonnaTitolo(int column, String lastContents) {
		titoli.put(column, lastContents);
	}
	
	
	/**
	 * Genera il file excel di risultato
	 * 
	 * @return
	 */
	/*private ExecutionResultBean<File> getRisultato() {
		ExecutionResultBean<File> result = new ExecutionResultBean<File>();
		
		SXSSFWorkbook wb = null;
		try {
			wb = new SXSSFWorkbook(100);
			Sheet sh = wb.createSheet();
			
			result.addAdditionalInformation(COUNT_OK, BigInteger.ZERO);
			result.addAdditionalInformation(COUNT_KO, BigInteger.ZERO);
			
			Row rowTitolo = sh.createRow(0);
			for (Entry<Integer, String> entry : titoli.entrySet()) {
				rowTitolo.createCell(entry.getKey()).setCellValue(entry.getValue());
			}
			rowTitolo.createCell(DDTSheetHandler.COL_ESITO_ESTRAZIONE).setCellValue("ESITO");
			rowTitolo.createCell(DDTSheetHandler.COL_NOME_FILE).setCellValue("NOME FILE");
			
			// parsing dei risultati delle estrazioni
			for (Future<ExecutionResultBean<File>> future : futures) {
				ExecutionResultBean<File> rowExportResult = future.get();
				
				generaRigaExcel(result, sh, rowExportResult); 
			}
			// parsing dei risultati delle eccezioni gravi
			for (ExecutionResultBean<File> fatalErr : fatalErrors) {
				generaRigaExcel(result, sh, fatalErr); 
			}
	        File expResultFile = new File(this.expDir, this.expDir.getName() + ".xlsx");
			FileOutputStream out = new FileOutputStream(expResultFile);
			result.addAdditionalInformation("CESTINO", expResultFile);
	        wb.write(out);
	        out.close();
	        wb.dispose();
			
	        result.getAdditionalInformations().remove("CESTINO");
	        result.setResult(expResultFile);
	        result.addAdditionalInformation(PATH_DIR_EXPORT, expDir);
			
			result.setSuccessful(true);
			
		} catch (Exception e) {
			String message = String.format(
					"Durante l'estrazione dei documenti legati al job %1$s, si è verificata la seguente eccezione %2$s",
					currentJob.getIdJob(), ExceptionUtils.getFullStackTrace(e));
			
			log.error(message);
			result.setMessage(message);
			result.setSuccessful(false);
		} finally {
			if (wb != null) try {wb.close();} catch(Exception e) {};
		}
		
		return result;
	}*/


	public void generaRigaExcel(ExecutionResultBean<File> result) {
		RigaEstrazioneDocumento rigaIndice = (RigaEstrazioneDocumento) result.getAdditionalInformation(DDTExporter.INFO_RIGA_INDICE);
		
		int numeroRiga = rigaIndice.getNumeroRiga();
		elaboraFoglioResult.addAdditionalInformation(LAST_ROW_ELAB, numeroRiga);
		
		Row row = foglioOut.createRow(numeroRiga - 1);
		
		Cell cell;
		
		cell = row.createCell(DDTSheetHandler.COL_COD_SOCIETA);
		cell.setCellValue(rigaIndice.getSocieta());

		cell = row.createCell(DDTSheetHandler.COL_TIPO_DOC);
		cell.setCellValue(rigaIndice.getTipoDocumento());

		cell = row.createCell(DDTSheetHandler.COL_NUM_DOC);
		cell.setCellValue(rigaIndice.getNumeroDocumento());

		cell = row.createCell(DDTSheetHandler.COL_ANNO_DOC);
		cell.setCellValue(rigaIndice.getAnnoDocumento());
		
		cell = row.createCell(DDTSheetHandler.COL_ESITO_ESTRAZIONE);
		cell.setCellValue(result.isSuccessful() ? "OK" : "KO");
		
		if (result.isSuccessful()) {
			cell = row.createCell(DDTSheetHandler.COL_NOME_FILE);
			cell.setCellValue(result.getResult().getName());
			
			BigInteger countOK = (BigInteger) elaboraFoglioResult.getAdditionalInformation(COUNT_OK);
			elaboraFoglioResult.addAdditionalInformation(COUNT_OK, countOK.add(BigInteger.ONE));
		} else {
			cell = row.createCell(DDTSheetHandler.COL_ESITO_MESSAGGIO);
			cell.setCellValue(result.getMessage());
			BigInteger countKO = (BigInteger) elaboraFoglioResult.getAdditionalInformation(COUNT_KO);
			elaboraFoglioResult.addAdditionalInformation(COUNT_KO, countKO.add(BigInteger.ONE));
		}
	}
	
	
	public void verificaColonne(int column, boolean atLeastOneCellOk) throws Exception {
		if (column != 3 || !atLeastOneCellOk) {
			throw new Exception("Numero colonne errato");
		}
	}


	public void generaIntestazione() {
		Row rowTitolo = foglioOut.createRow(0);
		for (Entry<Integer, String> entry : titoli.entrySet()) {
			rowTitolo.createCell(entry.getKey()).setCellValue(entry.getValue());
		}
		rowTitolo.createCell(DDTSheetHandler.COL_NOME_FILE).setCellValue("NOME FILE");
		rowTitolo.createCell(DDTSheetHandler.COL_ESITO_ESTRAZIONE).setCellValue("ESITO");
		rowTitolo.createCell(DDTSheetHandler.COL_ESITO_MESSAGGIO).setCellValue("MESSAGGIO");
	}
}
