/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.validator.ValidatorException;
import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import it.eng.auriga.ui.module.layout.server.protocollazione.datasource.bean.DestinatariXFileXlsRegMultiplaUscitaBean;
import it.eng.auriga.ui.module.layout.server.protocollazione.datasource.bean.DestinatariRegistrazioneMultiplaUscitaXmlBean;
import it.eng.auriga.ui.module.layout.server.protocollazione.datasource.bean.DestinatarioRegistrazioneMultiplaUscitaInError;
import it.eng.utility.module.config.StorageImplementation;

public class RegistrazioneMultiplaUscitaExcelUtility {

	private final Logger logger = Logger.getLogger(RegistrazioneMultiplaUscitaExcelUtility.class);

//	public void main(String[] args) throws Exception {
//		caricaDatiFromXlsx(null, null, null);
//	}
	
	public DestinatariXFileXlsRegMultiplaUscitaBean caricaDatiFromXls(String uriExcel, HttpSession httpSession, String nomeFile) throws Exception {

		BufferedInputStream documentStream = null;
		InputStream is = null;
		DestinatariXFileXlsRegMultiplaUscitaBean elencoDestinatari = new DestinatariXFileXlsRegMultiplaUscitaBean();
		List<DestinatariRegistrazioneMultiplaUscitaXmlBean> listaDestinatari = new LinkedList<DestinatariRegistrazioneMultiplaUscitaXmlBean>();
		List<DestinatarioRegistrazioneMultiplaUscitaInError> listaDestinatariInError = new LinkedList<DestinatarioRegistrazioneMultiplaUscitaInError>();
		String errorMessages = "";
		try {
			File document = StorageImplementation.getStorage().getRealFile(uriExcel);
//			File document = new File("C:\\Users\\fabcasta\\Downloads\\Template_DestinatariRegistrazioniMultiple.xlsx");

			elencoDestinatari.setInError(false);
			is = new FileInputStream(document);
			documentStream = new BufferedInputStream(is);
			HSSFWorkbook wb = new HSSFWorkbook(documentStream);
			HSSFSheet sheet = wb.getSheetAt(0);

			int rows; // No of rows
			rows = sheet.getPhysicalNumberOfRows();
			for (int r = 1; r <= rows; r++) {
				DestinatariRegistrazioneMultiplaUscitaXmlBean destinatario = new DestinatariRegistrazioneMultiplaUscitaXmlBean();
				HSSFRow row = sheet.getRow(r);
				if (row != null && !isRowEmpty(row)) {
					try {
						destinatario = manageCaricamento(r, wb.getCreationHelper().createFormulaEvaluator(), null, row, null, sheet);
					} catch (Exception e) {
						elencoDestinatari.setInError(true);
						DestinatarioRegistrazioneMultiplaUscitaInError error = new DestinatarioRegistrazioneMultiplaUscitaInError ();
						error.setNumRiga("" + r);
						error.setErrorMessage(e.getMessage());
						error.setNomeFile(nomeFile);
						listaDestinatariInError.add(error);
						errorMessages += e.getMessage() + "\n";
					}
					destinatario.setUriExcel(uriExcel);
					listaDestinatari.add(destinatario);
				}
			}
		} catch(Exception e) { 
			if (!(e instanceof ValidatorException)) {
				logger.error("Errore durante la lettura dei dati dal foglio Excel: " + e.getMessage(),e);
			}

			throw e;

		}finally {
			if(is != null) {
				try {
					is.close();
				} catch (Exception e) {}
			}
		}
		elencoDestinatari.setErrorMessage(errorMessages);
		elencoDestinatari.setListaDestinatari(listaDestinatari);
		elencoDestinatari.setListaDestintariInError(listaDestinatariInError);
		elencoDestinatari.setUploaded(true);
		return elencoDestinatari;
	}
	

	public DestinatariXFileXlsRegMultiplaUscitaBean caricaDatiFromXlsx(String uriExcel, HttpSession httpSession, String nomeFile) throws Exception {

		FileInputStream fis = null;

		DestinatariXFileXlsRegMultiplaUscitaBean elencoDestinatari = new DestinatariXFileXlsRegMultiplaUscitaBean();
		List<DestinatariRegistrazioneMultiplaUscitaXmlBean> listaDestinatari = new LinkedList<DestinatariRegistrazioneMultiplaUscitaXmlBean>();
		List<DestinatarioRegistrazioneMultiplaUscitaInError> listaDestinatariInError = new LinkedList<DestinatarioRegistrazioneMultiplaUscitaInError>();
		String errorMessages = "";
		try {
			File document = StorageImplementation.getStorage().getRealFile(uriExcel);
//			File document = new File("C:\\Users\\fabcasta\\Downloads\\Template_DestinatariRegistrazioniMultiple.xlsx");
			fis = new FileInputStream(document);
			XSSFWorkbook wb = new XSSFWorkbook(fis);
			XSSFSheet sheet = wb.getSheetAt(0);
			elencoDestinatari.setInError(false);
			int rows; // No of rows
			rows = sheet.getPhysicalNumberOfRows();
 			for (int r = 1; r <= rows ; r++) {
				DestinatariRegistrazioneMultiplaUscitaXmlBean destinatario = new DestinatariRegistrazioneMultiplaUscitaXmlBean();
				XSSFRow row = sheet.getRow(r);
				if (row != null && !isRowEmpty(row)) {
					try {
					destinatario = manageCaricamento(r, wb.getCreationHelper().createFormulaEvaluator(), row, null, sheet, null);
					} catch (Exception e) {
						elencoDestinatari.setInError(true);
						DestinatarioRegistrazioneMultiplaUscitaInError error = new DestinatarioRegistrazioneMultiplaUscitaInError ();
						error.setNumRiga("" + r);
						error.setErrorMessage(e.getMessage());
						error.setNomeFile(nomeFile);
						listaDestinatariInError.add(error);
						errorMessages += e.getMessage() + "\n";
//						elencoDestinatari.setErrorMessage(e.getMessage());
					}
					destinatario.setUriExcel(uriExcel);
					listaDestinatari.add(destinatario);
				}
			}
		} catch(Exception e) { 
			if (!(e instanceof ValidatorException)) {
				logger.error("Errore durante la lettura dei dati dal foglio Excel: " + e.getMessage(),e);
			}

			throw e;

		}finally {
			if(fis != null) {
				try {
					fis.close();
				} catch (Exception e) {}
			}
		}
		elencoDestinatari.setErrorMessage(errorMessages);
		elencoDestinatari.setListaDestinatari(listaDestinatari);
		elencoDestinatari.setListaDestintariInError(listaDestinatariInError);
		elencoDestinatari.setUploaded(true);
		return elencoDestinatari;
	}
	
	private DestinatariRegistrazioneMultiplaUscitaXmlBean manageCaricamento (int numRiga, FormulaEvaluator formulaEvaluator, XSSFRow row, HSSFRow rowHssf, XSSFSheet sheet, HSSFSheet sheetHssf) throws ValidatorException {
		DestinatariRegistrazioneMultiplaUscitaXmlBean result = new DestinatariRegistrazioneMultiplaUscitaXmlBean ();
		int numColonne = 0;
		XSSFRow initialColumnRow = null;
		HSSFRow initialHssfColumnRow = null;
		if (sheet != null) {
			initialColumnRow = sheet.getRow(0);
			numColonne = initialColumnRow.getLastCellNum();
		} else {
			initialHssfColumnRow = sheetHssf.getRow(0);
			numColonne = initialHssfColumnRow.getLastCellNum();
		}
		
		for (int i = 0; i < numColonne; i++) {
			String valueCellAlmenoUnRecord;
			valueCellAlmenoUnRecord = getValoreCella(i, formulaEvaluator, row, rowHssf);
			
			switch (i) {
				
				case ConstantsCampiExcelDestinatariRegistrazioneMultiplaUscita.TIPO:
					if (StringUtils.isNotBlank(valueCellAlmenoUnRecord)) {
						result.setTipo(valueCellAlmenoUnRecord);
						setMappaIntestazioniColonneValore(result, valueCellAlmenoUnRecord, i, formulaEvaluator, initialColumnRow, initialHssfColumnRow);
					}
					break;
				case ConstantsCampiExcelDestinatariRegistrazioneMultiplaUscita.DENOMINAZIONE_COGNOME:
					if (StringUtils.isNotBlank(valueCellAlmenoUnRecord)) {
						result.setDenominazioneCognome(valueCellAlmenoUnRecord);
						setMappaIntestazioniColonneValore(result, valueCellAlmenoUnRecord, i, formulaEvaluator, initialColumnRow, initialHssfColumnRow);
					}
					break;
				case ConstantsCampiExcelDestinatariRegistrazioneMultiplaUscita.NOME:
					if (StringUtils.isNotBlank(valueCellAlmenoUnRecord)) {
						result.setNome(valueCellAlmenoUnRecord);
						setMappaIntestazioniColonneValore(result, valueCellAlmenoUnRecord, i, formulaEvaluator, initialColumnRow, initialHssfColumnRow);
					}
					break;
				case ConstantsCampiExcelDestinatariRegistrazioneMultiplaUscita.COD_FISCALE:
					if (StringUtils.isNotBlank(valueCellAlmenoUnRecord)) {
						result.setCodiceFiscale(valueCellAlmenoUnRecord);
						setMappaIntestazioniColonneValore(result, valueCellAlmenoUnRecord, i, formulaEvaluator, initialColumnRow, initialHssfColumnRow);
					}
					break;
				case ConstantsCampiExcelDestinatariRegistrazioneMultiplaUscita.P_IVA:
					if (StringUtils.isNotBlank(valueCellAlmenoUnRecord)) {
						result.setPiva(valueCellAlmenoUnRecord);
						setMappaIntestazioniColonneValore(result, valueCellAlmenoUnRecord, i, formulaEvaluator, initialColumnRow, initialHssfColumnRow);
					}
					break;
				case ConstantsCampiExcelDestinatariRegistrazioneMultiplaUscita.COD_RAPIDO:
					if (StringUtils.isNotBlank(valueCellAlmenoUnRecord)) {
						result.setCodRapido(valueCellAlmenoUnRecord);
						setMappaIntestazioniColonneValore(result, valueCellAlmenoUnRecord, i, formulaEvaluator, initialColumnRow, initialHssfColumnRow);
					}
					break;
				case ConstantsCampiExcelDestinatariRegistrazioneMultiplaUscita.MEZZO_TRASMISSIONE:
					if (StringUtils.isNotBlank(valueCellAlmenoUnRecord)) {
						result.setMezzoTrasmissione(valueCellAlmenoUnRecord);
						setMappaIntestazioniColonneValore(result, valueCellAlmenoUnRecord, i, formulaEvaluator, initialColumnRow, initialHssfColumnRow);
					}
					break;
				case ConstantsCampiExcelDestinatariRegistrazioneMultiplaUscita.EMAIL:
					if (StringUtils.isNotBlank(valueCellAlmenoUnRecord)) {
						// CONTROLLO CON OBBLIGATORIETA' VALORE INVIA EMAIL
						if (StringUtils.isNotBlank(getValoreCella(ConstantsCampiExcelDestinatariRegistrazioneMultiplaUscita.INVIA_EMAIL, formulaEvaluator, row, rowHssf))) {
							result.setEmail(valueCellAlmenoUnRecord);
							setMappaIntestazioniColonneValore(result, valueCellAlmenoUnRecord, i, formulaEvaluator, initialColumnRow, initialHssfColumnRow);
						} else {
							throw new ValidatorException("In riga "+ numRiga +" è obbligatorio inserire un valore nel campo \"Invia e-mail\"");
						}
					}
					break;
				case ConstantsCampiExcelDestinatariRegistrazioneMultiplaUscita.TOPONIMO_INDIRIZZO:
					if (StringUtils.isNotBlank(valueCellAlmenoUnRecord)) {
						result.setToponimo(valueCellAlmenoUnRecord);
						setMappaIntestazioniColonneValore(result, valueCellAlmenoUnRecord, i, formulaEvaluator, initialColumnRow, initialHssfColumnRow);
					}
					break;
				case ConstantsCampiExcelDestinatariRegistrazioneMultiplaUscita.INDIRIZZO:
					if (StringUtils.isNotBlank(valueCellAlmenoUnRecord)) {
						result.setIndirizzo(valueCellAlmenoUnRecord);
						setMappaIntestazioniColonneValore(result, valueCellAlmenoUnRecord, i, formulaEvaluator, initialColumnRow, initialHssfColumnRow);
					}
					break;
				case ConstantsCampiExcelDestinatariRegistrazioneMultiplaUscita.N_CIVICO:
					if (StringUtils.isNotBlank(valueCellAlmenoUnRecord)) {
						result.setNumCivico(valueCellAlmenoUnRecord);
						setMappaIntestazioniColonneValore(result, valueCellAlmenoUnRecord, i, formulaEvaluator, initialColumnRow, initialHssfColumnRow);
					}
					break;
				case ConstantsCampiExcelDestinatariRegistrazioneMultiplaUscita.APPENDICE_CIVICO:
					if (StringUtils.isNotBlank(valueCellAlmenoUnRecord)) {
						result.setAppendiceCivico(valueCellAlmenoUnRecord);
						setMappaIntestazioniColonneValore(result, valueCellAlmenoUnRecord, i, formulaEvaluator, initialColumnRow, initialHssfColumnRow);
					}
					break;
				case ConstantsCampiExcelDestinatariRegistrazioneMultiplaUscita.COMUNE_CITTA_ESTERA:
					if (StringUtils.isNotBlank(valueCellAlmenoUnRecord)) {
						result.setComuneCittaEstera(valueCellAlmenoUnRecord);
						setMappaIntestazioniColonneValore(result, valueCellAlmenoUnRecord, i, formulaEvaluator, initialColumnRow, initialHssfColumnRow);
					}
					break;
				case ConstantsCampiExcelDestinatariRegistrazioneMultiplaUscita.CAP:
					if (StringUtils.isNotBlank(valueCellAlmenoUnRecord)) {
						result.setCap(valueCellAlmenoUnRecord);
						setMappaIntestazioniColonneValore(result, valueCellAlmenoUnRecord, i, formulaEvaluator, initialColumnRow, initialHssfColumnRow);
					}
					break;
				case ConstantsCampiExcelDestinatariRegistrazioneMultiplaUscita.STATO_ESTERO:
					if (StringUtils.isNotBlank(valueCellAlmenoUnRecord)) {
						result.setStatoEstero(valueCellAlmenoUnRecord);
						setMappaIntestazioniColonneValore(result, valueCellAlmenoUnRecord, i, formulaEvaluator, initialColumnRow, initialHssfColumnRow);
					}
					break;
				case ConstantsCampiExcelDestinatariRegistrazioneMultiplaUscita.LOCALITA:
					if (StringUtils.isNotBlank(valueCellAlmenoUnRecord)) {
						result.setLocalita(valueCellAlmenoUnRecord);
						setMappaIntestazioniColonneValore(result, valueCellAlmenoUnRecord, i, formulaEvaluator, initialColumnRow, initialHssfColumnRow);
					}
					break;
				case ConstantsCampiExcelDestinatariRegistrazioneMultiplaUscita.INDIRIZZO_RUBRICA:
					if (StringUtils.isNotBlank(valueCellAlmenoUnRecord)) {
						result.setIndirizzoRubrica(valueCellAlmenoUnRecord);
						setMappaIntestazioniColonneValore(result, valueCellAlmenoUnRecord, i, formulaEvaluator, initialColumnRow, initialHssfColumnRow);
					}
					break;
				case ConstantsCampiExcelDestinatariRegistrazioneMultiplaUscita.EFFETTUA_ASSEGNAZIONE_CC:
					if (StringUtils.isNotBlank(valueCellAlmenoUnRecord)) {
						result.setEffettuaAssegnazioneCc(valueCellAlmenoUnRecord);
						setMappaIntestazioniColonneValore(result, valueCellAlmenoUnRecord, i, formulaEvaluator, initialColumnRow, initialHssfColumnRow);
					}
					break;
				case ConstantsCampiExcelDestinatariRegistrazioneMultiplaUscita.INVIA_EMAIL:
					if (StringUtils.isNotBlank(valueCellAlmenoUnRecord)) {
						// CONTROLLO CON OBBLIGATORIETA' VALORE EMAIL
						if (StringUtils.isNotBlank(getValoreCella(ConstantsCampiExcelDestinatariRegistrazioneMultiplaUscita.EMAIL, formulaEvaluator, row, rowHssf))) {
							result.setInviaEmail(valueCellAlmenoUnRecord);
							setMappaIntestazioniColonneValore(result, valueCellAlmenoUnRecord, i, formulaEvaluator, initialColumnRow, initialHssfColumnRow);
						} else {
							throw new ValidatorException("In riga "+ numRiga +" è obbligatorio inserire un valore nel campo \"e-mail\"");
						}
					}
					break;
				case ConstantsCampiExcelDestinatariRegistrazioneMultiplaUscita.NOME_FILE_PRIMARIO:
					if (StringUtils.isNotBlank(valueCellAlmenoUnRecord)) {
						result.setNomeFilePrimario(valueCellAlmenoUnRecord);
						setMappaIntestazioniColonneValore(result, valueCellAlmenoUnRecord, i, formulaEvaluator, initialColumnRow, initialHssfColumnRow);
					}
					break;
				case ConstantsCampiExcelDestinatariRegistrazioneMultiplaUscita.PERCORSO_RELATIVO_FILE_ALLEGATI:
					if (StringUtils.isNotBlank(valueCellAlmenoUnRecord)) {
						result.setPercorsoRelFileAllegati(valueCellAlmenoUnRecord);
						setMappaIntestazioniColonneValore(result, valueCellAlmenoUnRecord, i, formulaEvaluator, initialColumnRow, initialHssfColumnRow);
					}
					break;
				case ConstantsCampiExcelDestinatariRegistrazioneMultiplaUscita.NOMI_FILE_ALLEGATI:
					if (StringUtils.isNotBlank(valueCellAlmenoUnRecord)) {
						result.setNomiFileAllegati(valueCellAlmenoUnRecord);
						setMappaIntestazioniColonneValore(result, valueCellAlmenoUnRecord, i, formulaEvaluator, initialColumnRow, initialHssfColumnRow);
					}
					break;
				default:
					if (StringUtils.isNotBlank(valueCellAlmenoUnRecord)) {
						Map<String, String> valueMap = new LinkedHashMap<String, String>();
						String nomeColonna = getValoreCella(i, formulaEvaluator, initialColumnRow, initialHssfColumnRow);
						if (StringUtils.isNotBlank(nomeColonna)) {
							valueMap.put(nomeColonna, valueCellAlmenoUnRecord);
							result.getAltreColonne().putAll(valueMap);
						}
						setMappaIntestazioniColonneValore(result, valueCellAlmenoUnRecord, i, formulaEvaluator, initialColumnRow, initialHssfColumnRow);
					}
					break;
			}
		}
		return result;
	}
	
	private void setMappaIntestazioniColonneValore (DestinatariRegistrazioneMultiplaUscitaXmlBean destinatario, String valueCellAlmenoUnRecord, int numColonna, FormulaEvaluator formulaEvaluator, XSSFRow initialColumnRow, HSSFRow initialHssfColumnRow) {
		Map<String, String> valueMap = new LinkedHashMap<String, String>();
		String nomeColonna = getValoreCella(numColonna, formulaEvaluator, initialColumnRow, initialHssfColumnRow);
		if (StringUtils.isNotBlank(nomeColonna)) {
			valueMap.put(nomeColonna, valueCellAlmenoUnRecord);
			destinatario.getMappaIntestazioniColonneValore().putAll(valueMap);
		}
	}
	
	private String getValoreCella(int indiceColonna, FormulaEvaluator formulaEvaluator, XSSFRow row, HSSFRow rowHssf) {
		String result = null;
		Cell cell = null;
		
		if (row != null) {
			cell = row.getCell(indiceColonna);
		} else {
			cell = rowHssf.getCell(indiceColonna);
		}
		if (cell != null && formulaEvaluator.evaluateInCell(cell).getCellType() != XSSFCell.CELL_TYPE_BLANK) {
			cell.setCellType(Cell.CELL_TYPE_STRING);
			if(formulaEvaluator.evaluateInCell(cell).getCellType()==XSSFCell.CELL_TYPE_NUMERIC) {
				result = (String.valueOf((int) cell.getNumericCellValue()));
			} else {
				result = cell.getStringCellValue();
				if(result.equals("Selezionare")) {
					result = null;
				} 
			}
		}
		return result;
	}

	private boolean isRowEmpty(HSSFRow row) {
		for (int c = row.getFirstCellNum(); c < row.getLastCellNum(); c++) {
			HSSFCell cell = row.getCell(c);
			if (cell != null && cell.getCellType() != HSSFCell.CELL_TYPE_BLANK && cell.getCellType() == HSSFCell.CELL_TYPE_STRING && cell.getStringCellValue() != null && !cell.getStringCellValue().trim().equals("")) {
				return false;
			}
		}
		return true;
	}
	
	private boolean isRowEmpty(XSSFRow row) {
		for (int c = row.getFirstCellNum(); c < row.getLastCellNum(); c++) {
			XSSFCell cell = row.getCell(c);
			if (cell != null && cell.getCellType() != XSSFCell.CELL_TYPE_BLANK && cell.getCellType() == XSSFCell.CELL_TYPE_STRING && cell.getStringCellValue() != null && !cell.getStringCellValue().trim().equals("")) {
				return false;
			}
		}
		return true;
	}
}
