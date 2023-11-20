/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.validator.ValidatorException;
import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.google.gwt.regexp.shared.RegExp;

import it.eng.auriga.ui.module.layout.server.pratiche.nuovapropostaatto2.datasource.bean.ConstantsCampiExcelLiquidazioneAVB;
import it.eng.auriga.ui.module.layout.server.pratiche.nuovapropostaatto2.datasource.bean.DatiLiquidazioneAVBBean;
import it.eng.auriga.ui.module.layout.server.pratiche.nuovapropostaatto2.datasource.bean.DisimpegnoAVBBean;
import it.eng.auriga.ui.module.layout.server.pratiche.nuovapropostaatto2.datasource.bean.ImputazioneContabileAVBBean;
import it.eng.auriga.ui.module.layout.server.pratiche.nuovapropostaatto2.datasource.bean.RifSpesaEntrataAVBBean;
import it.eng.utility.module.config.StorageImplementation;
import it.eng.utility.ui.user.ParametriDBUtil;

public class LiquidazioniAVBExcelUtility {

	private static final Logger logger = Logger.getLogger(LiquidazioniAVBExcelUtility.class);

	private static final int INDICE_COLONNA_TIPO_RIGA = 4;
	private static final int INDICE_COLONNA_VALORE_RIGA = 5;
	private static final String IMPORTO_PATTERN = "#,##0.00";
	private static final String TIPO_IMPORTO = "Importo";
	private static final String TIPO_DATA = "Data";
	private static final String TIPO_INTERO = "Numerico intero";
	private static final String TIPO_IVA = "Formato Partita IVA";
	private static final String TIPO_SI_NO = "SI/NO";
	private static final String TIPO_COD_FISCALE = "Formato Codice Fiscale";
	private static final String TIPO_IBAN = "Testo libero (formato IBAN)";
	private static final String TIPO_CIG = "Testo libero (controllo CIG)";
	private static final String TIPO_SMART_CIG = "Testo libero (controllo smart CIG)";
	private static final String TIPO_CUP = "Testo libero (formato CUP?)";
	private static final String NO_REGEX = "#NO_REGEX";
	private static final String TIPO_ATTIVITA_COMMERCIALE = "Attività Commerciale";
	
	private HttpSession session;

//	public static void main(String args[]) throws Exception {
//		caricaDatiFromXls(null, null);
//	}
	
	public DatiLiquidazioneAVBBean caricaDatiFromXls(String uriExcel, HttpSession httpSession) throws Exception {

		BufferedInputStream documentStream = null;
		InputStream is = null;
		
		DatiLiquidazioneAVBBean liquidazioneBean = new DatiLiquidazioneAVBBean();
		
		session = httpSession;
		try {
			File document = StorageImplementation.getStorage().getRealFile(uriExcel);
			//			File document = new File("C:\\Users\\micrega\\Downloads\\Modello per più fatture 1.2021_FV (1).xls");

			is = new FileInputStream(document);
			documentStream = new BufferedInputStream(is);
			liquidazioneBean.setInError(false);
			HSSFWorkbook wb = new HSSFWorkbook(documentStream);
			HSSFSheet sheet = wb.getSheetAt(0);

			int rows; // No of rows
			rows = sheet.getPhysicalNumberOfRows();
			try {
				for (int r = 2; r < rows + 1; r++) {

					HSSFRow row = sheet.getRow(r);
					if (row != null && !isRowEmpty(row)) {
						//					try {
						if (isCampoOggettoDellaSpesa(r)) {
							manageCampiOggettoDellaSpesa(liquidazioneBean, r, wb.getCreationHelper().createFormulaEvaluator(),null , row, null, sheet);
						}

						else if (isCampoBeneficiarioPagamento(r)) {
							manageCampiBeneficiarioPagamento(liquidazioneBean, r, wb.getCreationHelper().createFormulaEvaluator(),null , row, null, sheet);
						}

						else if (isCampoRegimeIvaBollo(r)) {
							manageCampiRegimeIvaBollo(liquidazioneBean, r, wb.getCreationHelper().createFormulaEvaluator(),null , row, null, sheet);
						}

						else if (isCampoDURC(r)) {
							manageCampiDURC(liquidazioneBean, r, wb.getCreationHelper().createFormulaEvaluator(),null , row, null, sheet);
						}

						else if (isCampoVerificheEquitaliaAntiriciclaggio(r)) {
							manageCampiVerificheEquitaliaAntiriciclaggio(liquidazioneBean, r, wb.getCreationHelper().createFormulaEvaluator(),null , row, null, sheet);
						}

						else if (isCampoCigCup(r)) {
							manageCampiCigCup(liquidazioneBean, r, wb.getCreationHelper().createFormulaEvaluator(),null , row, null, sheet);
						}

						else if (isCampoProgressivoRegistrazione(r)) {
							manageCampiProgressivoRegistrazione(liquidazioneBean, r, wb.getCreationHelper().createFormulaEvaluator(),null , row);
						}

						else if (isCampoTerminiPagamento(r)) {
							manageCampiTerminiPagamento(liquidazioneBean, r, wb.getCreationHelper().createFormulaEvaluator(),null , row, null, sheet);
						}

						else if (isCampoImputazioneContabile(r)) {
							manageCampiImputazioneContabile(liquidazioneBean, wb.getCreationHelper().createFormulaEvaluator(), null, sheet, r);
						}

						else if (isCampoSpesaEntrata(r)) {
							manageCampiSpesaEntrata(liquidazioneBean, r, wb.getCreationHelper().createFormulaEvaluator(),null , row);
						}

						else if (isCampoRifSpesaEntrata(r)) {
							manageCampiRifSpesaEntrata(liquidazioneBean, wb.getCreationHelper().createFormulaEvaluator(), null, sheet, r);
						}

						else if (isCampoDisimpegno(r)) {
							manageCampiDisimpegno(liquidazioneBean, wb.getCreationHelper().createFormulaEvaluator(), null, sheet, r);
						}

						else if (isCampoLiquidazioniCompensiProgettualita(r)) {
							manageCampiLiquidazioniCompensiProgettualita(liquidazioneBean, r, wb.getCreationHelper().createFormulaEvaluator(),null , row);
						}

						else if (isCampoSpesaTitoloBilancio(r)) {
							manageCampiSpesaTitoloBilancio(liquidazioneBean, r, wb.getCreationHelper().createFormulaEvaluator(),null , row);
						}
						//					} catch (Exception e) {
						//						liquidazioneBean.setInError(true);
						//						liquidazioneBean.setErrorMessage(e.getMessage());
						//					}
					}

				}
			} catch (Exception e) {
				liquidazioneBean.setInError(true);
				liquidazioneBean.setErrorMessage(e.getMessage());
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
		liquidazioneBean.setUriExcel(uriExcel);
		liquidazioneBean.setUploaded(true);
		return liquidazioneBean;
	}

	public DatiLiquidazioneAVBBean caricaDatiFromXlsx(String uriExcel, HttpSession httpSession) throws Exception {

		FileInputStream fis = null;

		DatiLiquidazioneAVBBean liquidazioneBean = new DatiLiquidazioneAVBBean();
		
		session = httpSession;
		
		try {
			File document = StorageImplementation.getStorage().getRealFile(uriExcel);
//			File document = new File("C:\\Users\\fabcasta\\Downloads\\Modello per più fatture 1.2021_FV (1).xlsx");
			fis = new FileInputStream(document);
			XSSFWorkbook wb = new XSSFWorkbook(fis);
			XSSFSheet sheet = wb.getSheetAt(0);
			liquidazioneBean.setInError(false);
			int rows; // No of rows
			rows = sheet.getPhysicalNumberOfRows();
			for (int r = 2; r < rows + 1; r++) {
				XSSFRow row = sheet.getRow(r);
				if (row != null && !isRowEmpty(row)) {
					try {
						if (isCampoOggettoDellaSpesa(r)) {
							manageCampiOggettoDellaSpesa(liquidazioneBean, r, wb.getCreationHelper().createFormulaEvaluator(), row, null, sheet, null);
						}

						else if (isCampoBeneficiarioPagamento(r)) {
							manageCampiBeneficiarioPagamento(liquidazioneBean, r, wb.getCreationHelper().createFormulaEvaluator(), row, null, sheet, null);
						}

						else if (isCampoRegimeIvaBollo(r)) {
							manageCampiRegimeIvaBollo(liquidazioneBean, r, wb.getCreationHelper().createFormulaEvaluator(), row, null, sheet, null);
						}

						else if (isCampoDURC(r)) {
							manageCampiDURC(liquidazioneBean, r, wb.getCreationHelper().createFormulaEvaluator(), row, null, sheet, null);
						}

						else if (isCampoVerificheEquitaliaAntiriciclaggio(r)) {
							manageCampiVerificheEquitaliaAntiriciclaggio(liquidazioneBean, r, wb.getCreationHelper().createFormulaEvaluator(), row, null, sheet, null);
						}

						else if (isCampoCigCup(r)) {
							manageCampiCigCup(liquidazioneBean, r, wb.getCreationHelper().createFormulaEvaluator(), row, null, sheet, null);
						}

						else if (isCampoProgressivoRegistrazione(r)) {
							manageCampiProgressivoRegistrazione(liquidazioneBean, r, wb.getCreationHelper().createFormulaEvaluator(), row, null);
						}

						else if (isCampoTerminiPagamento(r)) {
							manageCampiTerminiPagamento(liquidazioneBean, r, wb.getCreationHelper().createFormulaEvaluator(), row, null, sheet, null);
						}

						else if (isCampoImputazioneContabile(r)) {
							manageCampiImputazioneContabile(liquidazioneBean, wb.getCreationHelper().createFormulaEvaluator(), sheet, null, r);
						}

						else if (isCampoSpesaEntrata(r)) {
							manageCampiSpesaEntrata(liquidazioneBean, r, wb.getCreationHelper().createFormulaEvaluator(), row, null);
						}

						else if (isCampoRifSpesaEntrata(r)) {
							manageCampiRifSpesaEntrata(liquidazioneBean, wb.getCreationHelper().createFormulaEvaluator(), sheet, null, r);
						}

						else if (isCampoDisimpegno(r)) {
							manageCampiDisimpegno(liquidazioneBean, wb.getCreationHelper().createFormulaEvaluator(), sheet, null, r);
						}

						else if (isCampoLiquidazioniCompensiProgettualita(r)) {
							manageCampiLiquidazioniCompensiProgettualita(liquidazioneBean, r, wb.getCreationHelper().createFormulaEvaluator(), row, null);
						}

						else if (isCampoSpesaTitoloBilancio(r)) {
							manageCampiSpesaTitoloBilancio(liquidazioneBean, r, wb.getCreationHelper().createFormulaEvaluator(), row, null);
						}
					} catch (Exception e) {
						liquidazioneBean.setInError(true);
						liquidazioneBean.setErrorMessage(e.getMessage());
					}
				}
			}
		}  
		catch(Exception e) { 
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
		liquidazioneBean.setUriExcel(uriExcel);
		liquidazioneBean.setUploaded(true);
		return liquidazioneBean;
	}

	private void manageCampiSpesaEntrata(DatiLiquidazioneAVBBean liquidazioneBean, int campo, FormulaEvaluator formulaEvaluator, XSSFRow row, HSSFRow rowHssf) throws Exception {
		String nomeSezione = "Spesa Correlata all'Entrata";
		String valueCell;

		switch (campo) {

		case ConstantsCampiExcelLiquidazioneAVB.SPESAENTRATA_FLG_SPESA_CORR_ENTRATA:
			valueCell = getStandardValueCell(campo, formulaEvaluator, row, rowHssf, nomeSezione);
			if (StringUtils.isNotBlank(valueCell)) {
				liquidazioneBean.setSpesaEntrataFlgSpesaCorrEntrata(valueCell);
			}
			break;
		case ConstantsCampiExcelLiquidazioneAVB.SPESAENTRATA_FLG_RISP_CRONOPROGRAMMA:
			valueCell = getStandardValueCell(campo, formulaEvaluator, row, rowHssf, nomeSezione);
			if (StringUtils.isNotBlank(valueCell)) {
				liquidazioneBean.setSpesaEntrataFlgRispCronoprogramma(valueCell);
			}
			break;	
		}				
	}
	
	private void manageCampiRifSpesaEntrata(DatiLiquidazioneAVBBean liquidazioneBean, FormulaEvaluator formulaEvaluator, XSSFSheet sheet, HSSFSheet sheetHssf, int campo) throws Exception {
		String nomeSezione = "Spesa Correlata all'Entrata";
		int numColonne = 0;
		if (sheet != null) {
			XSSFRow initialRow = sheet.getRow(ConstantsCampiExcelLiquidazioneAVB.SPESAENTRATA_DET_ACCERTAMENTO);
			numColonne = initialRow.getLastCellNum();
		} else {
			HSSFRow row = sheetHssf.getRow(campo);
			numColonne = row.getLastCellNum();
		}
		List<RifSpesaEntrataAVBBean> listaRifSpesaEntrata = new ArrayList<RifSpesaEntrataAVBBean>();

		for (int indiceColonna = 5; indiceColonna < numColonne; indiceColonna++) {	
			RifSpesaEntrataAVBBean rifSpesaEntrata = new RifSpesaEntrataAVBBean();
			
//			if(manageIsEndOfColumn(sheet, sheetHssf, indiceColonna, ConstantsCampiExcelLiquidazioneAVB.SPESAENTRATA_DET_NUM)) {
//				break;
//			}

			String valueCell = "";
			boolean isObbligatorioNull = false;
			
			valueCell = getValueCell(ConstantsCampiExcelLiquidazioneAVB.SPESAENTRATA_DET_ACCERTAMENTO, formulaEvaluator, sheet != null ? sheet.getRow(ConstantsCampiExcelLiquidazioneAVB.SPESAENTRATA_DET_ACCERTAMENTO) : null, sheetHssf != null ? sheetHssf.getRow(ConstantsCampiExcelLiquidazioneAVB.SPESAENTRATA_DET_ACCERTAMENTO) : null, indiceColonna, nomeSezione);
			if (StringUtils.isNotBlank(valueCell)) {
				rifSpesaEntrata.setSpesaEntrataDetAccertamento(valueCell);
			} else {
				isObbligatorioNull = true;
			}
			rifSpesaEntrata.setSpesaEntrataDetNum(getValueCell(ConstantsCampiExcelLiquidazioneAVB.SPESAENTRATA_DET_NUM, formulaEvaluator, sheet != null ? sheet.getRow(ConstantsCampiExcelLiquidazioneAVB.SPESAENTRATA_DET_NUM) : null, sheetHssf != null ? sheetHssf.getRow(ConstantsCampiExcelLiquidazioneAVB.SPESAENTRATA_DET_NUM) : null, indiceColonna, nomeSezione));
			rifSpesaEntrata.setSpesaEntrataCapitolo(getValueCell(ConstantsCampiExcelLiquidazioneAVB.SPESAENTRATA_CAPITOLO, formulaEvaluator, sheet != null ? sheet.getRow(ConstantsCampiExcelLiquidazioneAVB.SPESAENTRATA_CAPITOLO) : null, sheetHssf != null ? sheetHssf.getRow(ConstantsCampiExcelLiquidazioneAVB.SPESAENTRATA_CAPITOLO) : null, indiceColonna, nomeSezione));
			rifSpesaEntrata.setSpesaEntrataReversale(getValueCell(ConstantsCampiExcelLiquidazioneAVB.SPESAENTRATA_REVERSALE, formulaEvaluator, sheet != null ? sheet.getRow(ConstantsCampiExcelLiquidazioneAVB.SPESAENTRATA_REVERSALE) : null, sheetHssf != null ? sheetHssf.getRow(ConstantsCampiExcelLiquidazioneAVB.SPESAENTRATA_REVERSALE) : null, indiceColonna, nomeSezione));
			rifSpesaEntrata.setSpesaEntrataImportoSomministrato(getValueCell(ConstantsCampiExcelLiquidazioneAVB.SPESAENTRATA_IMPORTO_SOMMINISTRATO, formulaEvaluator, sheet != null ? sheet.getRow(ConstantsCampiExcelLiquidazioneAVB.SPESAENTRATA_IMPORTO_SOMMINISTRATO) : null, sheetHssf != null ? sheetHssf.getRow(ConstantsCampiExcelLiquidazioneAVB.SPESAENTRATA_IMPORTO_SOMMINISTRATO) : null, indiceColonna, nomeSezione));
			rifSpesaEntrata.setSpesaEntrataImportDaSomministrare(getValueCell(ConstantsCampiExcelLiquidazioneAVB.SPESAENTRATA_IMPORTO_DA_SOMMINISTARE, formulaEvaluator, sheet != null ? sheet.getRow(ConstantsCampiExcelLiquidazioneAVB.SPESAENTRATA_IMPORTO_DA_SOMMINISTARE) : null, sheetHssf != null ? sheetHssf.getRow(ConstantsCampiExcelLiquidazioneAVB.SPESAENTRATA_IMPORTO_DA_SOMMINISTARE) : null, indiceColonna, nomeSezione));
			rifSpesaEntrata.setSpesaEntrataModTempOp(getValueCell(ConstantsCampiExcelLiquidazioneAVB.SPESAENTRATA_MOD_TEMP_OP, formulaEvaluator, sheet != null ? sheet.getRow(ConstantsCampiExcelLiquidazioneAVB.SPESAENTRATA_MOD_TEMP_OP) : null, sheetHssf != null ? sheetHssf.getRow(ConstantsCampiExcelLiquidazioneAVB.SPESAENTRATA_MOD_TEMP_OP) : null, indiceColonna, nomeSezione));
			
			if (checkAllColumnNull(rifSpesaEntrata) && isObbligatorioNull) {
				break;
			} else if (!checkAllColumnNull(rifSpesaEntrata) && isObbligatorioNull){
				throw new ValidatorException("Il dato \"N. ACCERTAMENTO/ANNO\" della sezione " + nomeSezione + ", in riga " + (ConstantsCampiExcelLiquidazioneAVB.SPESAENTRATA_DET_ACCERTAMENTO+1) + " e colonna " + (indiceColonna+1) + ", è obbligatorio");
			} else {
				listaRifSpesaEntrata.add(rifSpesaEntrata);
			}
			
		}
		liquidazioneBean.setListaRifSpesaEntrata(listaRifSpesaEntrata);
	}

	private void manageCampiImputazioneContabile(DatiLiquidazioneAVBBean liquidazioneBean, FormulaEvaluator formulaEvaluator, XSSFSheet sheet, HSSFSheet sheetHssf, int campo) throws Exception {
		String nomeSezione = "Imputazione Contabile";
		int numColonne = 0;
		if (sheet != null) {
			XSSFRow initialRow = sheet.getRow(ConstantsCampiExcelLiquidazioneAVB.IMPUTAZIONECONTABILE_IMPORTO);
			numColonne = initialRow.getLastCellNum();
		} else {
			HSSFRow row = sheetHssf.getRow(campo);
			numColonne = row.getLastCellNum();
		}

		List<ImputazioneContabileAVBBean> listaImputazioniContabili = new ArrayList<ImputazioneContabileAVBBean>();
		
		
		String valueCellAlmenoUnRecord = "";
		
		valueCellAlmenoUnRecord = getValueCell(ConstantsCampiExcelLiquidazioneAVB.IMPUTAZIONECONTABILE_IMPORTO, formulaEvaluator, sheet != null ? sheet.getRow(ConstantsCampiExcelLiquidazioneAVB.IMPUTAZIONECONTABILE_IMPORTO) : null, sheetHssf != null ? sheetHssf.getRow(ConstantsCampiExcelLiquidazioneAVB.IMPUTAZIONECONTABILE_IMPORTO) : null, INDICE_COLONNA_VALORE_RIGA, nomeSezione);
		if (StringUtils.isBlank(valueCellAlmenoUnRecord)) {
			throw new ValidatorException("Per la sezione " + nomeSezione + ", è obbligatorio inserire almeno un record");
		} 

		for (int indiceColonna = 5; indiceColonna < numColonne; indiceColonna++) {	
			ImputazioneContabileAVBBean imputazioneContabile = new ImputazioneContabileAVBBean();

//			if(manageIsEndOfColumn(sheet, sheetHssf, indiceColonna, ConstantsCampiExcelLiquidazioneAVB.IMPUTAZIONECONTABILE_CDC)) {
//				break;
//			}
			
			String valueCell = "";
			boolean isObbligatorioNull = false;
			
			valueCell = getValueCell(ConstantsCampiExcelLiquidazioneAVB.IMPUTAZIONECONTABILE_IMPORTO, formulaEvaluator, sheet != null ? sheet.getRow(ConstantsCampiExcelLiquidazioneAVB.IMPUTAZIONECONTABILE_IMPORTO) : null, sheetHssf != null ? sheetHssf.getRow(ConstantsCampiExcelLiquidazioneAVB.IMPUTAZIONECONTABILE_IMPORTO) : null, indiceColonna, nomeSezione);
			if (StringUtils.isNotBlank(valueCell)) {
				imputazioneContabile.setImputazioneContabileImporto(valueCell);
			} else {
				isObbligatorioNull = true;
			}

			imputazioneContabile.setImputazioneContabileCdc(getValueCell(ConstantsCampiExcelLiquidazioneAVB.IMPUTAZIONECONTABILE_CDC, formulaEvaluator, sheet != null ? sheet.getRow(ConstantsCampiExcelLiquidazioneAVB.IMPUTAZIONECONTABILE_CDC) : null, sheetHssf != null ? sheetHssf.getRow(ConstantsCampiExcelLiquidazioneAVB.IMPUTAZIONECONTABILE_CDC) : null, indiceColonna, nomeSezione));
			imputazioneContabile.setImputazioneContabileMissione(getValueCell(ConstantsCampiExcelLiquidazioneAVB.IMPUTAZIONECONTABILE_MISSIONE, formulaEvaluator, sheet != null ? sheet.getRow(ConstantsCampiExcelLiquidazioneAVB.IMPUTAZIONECONTABILE_MISSIONE) : null, sheetHssf != null ? sheetHssf.getRow(ConstantsCampiExcelLiquidazioneAVB.IMPUTAZIONECONTABILE_MISSIONE) : null, indiceColonna, nomeSezione));
			imputazioneContabile.setImputazioneContabileProgramma(getValueCell(ConstantsCampiExcelLiquidazioneAVB.IMPUTAZIONECONTABILE_PROGRAMMA, formulaEvaluator, sheet != null ? sheet.getRow(ConstantsCampiExcelLiquidazioneAVB.IMPUTAZIONECONTABILE_PROGRAMMA) : null, sheetHssf != null ? sheetHssf.getRow(ConstantsCampiExcelLiquidazioneAVB.IMPUTAZIONECONTABILE_PROGRAMMA) : null, indiceColonna, nomeSezione));
			imputazioneContabile.setImputazioneContabileTitolo(getValueCell(ConstantsCampiExcelLiquidazioneAVB.IMPUTAZIONECONTABILE_TITOLO, formulaEvaluator, sheet != null ? sheet.getRow(ConstantsCampiExcelLiquidazioneAVB.IMPUTAZIONECONTABILE_TITOLO) : null, sheetHssf != null ? sheetHssf.getRow(ConstantsCampiExcelLiquidazioneAVB.IMPUTAZIONECONTABILE_TITOLO) : null, indiceColonna, nomeSezione));
			imputazioneContabile.setImputazioneContabileRifBilancio(getValueCell(ConstantsCampiExcelLiquidazioneAVB.IMPUTAZIONECONTABILE_RIF_BILANCIO, formulaEvaluator, sheet != null ? sheet.getRow(ConstantsCampiExcelLiquidazioneAVB.IMPUTAZIONECONTABILE_RIF_BILANCIO) : null, sheetHssf != null ? sheetHssf.getRow(ConstantsCampiExcelLiquidazioneAVB.IMPUTAZIONECONTABILE_RIF_BILANCIO) : null, indiceColonna, nomeSezione));
			imputazioneContabile.setImputazioneContabileMacroaggregato(getValueCell(ConstantsCampiExcelLiquidazioneAVB.IMPUTAZIONECONTABILE_MACROAGGREGATO, formulaEvaluator, sheet != null ? sheet.getRow(ConstantsCampiExcelLiquidazioneAVB.IMPUTAZIONECONTABILE_MACROAGGREGATO) : null, sheetHssf != null ? sheetHssf.getRow(ConstantsCampiExcelLiquidazioneAVB.IMPUTAZIONECONTABILE_MACROAGGREGATO) : null, indiceColonna, nomeSezione));
			imputazioneContabile.setImputazioneContabileCapitolo(getValueCell(ConstantsCampiExcelLiquidazioneAVB.IMPUTAZIONECONTABILE_CAPITOLO, formulaEvaluator, sheet != null ? sheet.getRow(ConstantsCampiExcelLiquidazioneAVB.IMPUTAZIONECONTABILE_CAPITOLO) : null, sheetHssf != null ? sheetHssf.getRow(ConstantsCampiExcelLiquidazioneAVB.IMPUTAZIONECONTABILE_CAPITOLO) : null, indiceColonna, nomeSezione));
			imputazioneContabile.setImputazioneContabileImpegno(getValueCell(ConstantsCampiExcelLiquidazioneAVB.IMPUTAZIONECONTABILE_IMPEGNO, formulaEvaluator, sheet != null ? sheet.getRow(ConstantsCampiExcelLiquidazioneAVB.IMPUTAZIONECONTABILE_IMPEGNO) : null, sheetHssf != null ? sheetHssf.getRow(ConstantsCampiExcelLiquidazioneAVB.IMPUTAZIONECONTABILE_IMPEGNO) : null, indiceColonna, nomeSezione));
			imputazioneContabile.setImputazioneContabileCodSiope(getValueCell(ConstantsCampiExcelLiquidazioneAVB.IMPUTAZIONECONTABILE_COD_SIOPE, formulaEvaluator, sheet != null ? sheet.getRow(ConstantsCampiExcelLiquidazioneAVB.IMPUTAZIONECONTABILE_COD_SIOPE) : null, sheetHssf != null ? sheetHssf.getRow(ConstantsCampiExcelLiquidazioneAVB.IMPUTAZIONECONTABILE_COD_SIOPE) : null, indiceColonna, nomeSezione));

			if (checkAllColumnNull(imputazioneContabile) && isObbligatorioNull) {
				break;
			} else if (!checkAllColumnNull(imputazioneContabile) && isObbligatorioNull){
				throw new ValidatorException("Il dato \"IMPORTO\" della sezione " + nomeSezione + ", in riga " + (ConstantsCampiExcelLiquidazioneAVB.IMPUTAZIONECONTABILE_IMPORTO+1) + " e colonna " + (indiceColonna+1) + ", è obbligatorio");
			} else {
				listaImputazioniContabili.add(imputazioneContabile);
			}
		}
		liquidazioneBean.setListaImputazioneContabile(listaImputazioniContabili);
	}

	private <T> boolean checkAllColumnNull(T object) throws IllegalArgumentException, IllegalAccessException, InvocationTargetException, IntrospectionException {
		BeanInfo beanInfoForTitle = Introspector.getBeanInfo(object.getClass());
		for (PropertyDescriptor propertyDesc : beanInfoForTitle.getPropertyDescriptors()) {
			String propertyName = propertyDesc.getName();
			Object value = propertyDesc.getReadMethod().invoke(object); 
			if (!propertyName.equalsIgnoreCase("class")) {
				if(value!=null) {
					return false;
				}	
			}
		}
		return true;
	}

	private void manageCampiProgressivoRegistrazione(DatiLiquidazioneAVBBean liquidazioneBean, int campo, FormulaEvaluator formulaEvaluator, XSSFRow row, HSSFRow rowHssf) throws Exception {
		String valueCell = getStandardValueCell(campo, formulaEvaluator, row, rowHssf, null );
		if (StringUtils.isNotBlank(valueCell)) {
			liquidazioneBean.setProgressivoRegPcc(valueCell);
		}
	}

	private void manageCampiTerminiPagamento(DatiLiquidazioneAVBBean liquidazioneBean, int campo, FormulaEvaluator formulaEvaluator, XSSFRow row, HSSFRow rowHssf, XSSFSheet sheet, HSSFSheet sheetHssf) throws Exception {
		String valueCell;
		String nomeSezione = "Termini di pagamento";

		switch (campo) {

		case ConstantsCampiExcelLiquidazioneAVB.TERMINIPAGAMENTO_GG_SCADENZA:
			valueCell = getStandardValueCell(campo, formulaEvaluator, row, rowHssf, nomeSezione );
			if (StringUtils.isNotBlank(valueCell)) {
				if (sheet!=null) {
					if (StringUtils.isNotBlank(getStandardValueCell(ConstantsCampiExcelLiquidazioneAVB.TERMINIPAGAMENTO_DATA_DECORRENZA,
							formulaEvaluator, sheet.getRow(ConstantsCampiExcelLiquidazioneAVB.TERMINIPAGAMENTO_DATA_DECORRENZA), null, nomeSezione))
							&& StringUtils.isBlank(getStandardValueCell(ConstantsCampiExcelLiquidazioneAVB.TERMINIPAGAMENTO_DATA_SCADENZA, formulaEvaluator,
									sheet.getRow(ConstantsCampiExcelLiquidazioneAVB.TERMINIPAGAMENTO_DATA_SCADENZA), null, nomeSezione))) {
						liquidazioneBean.setTerminiPagamentoGgScadenza(valueCell);
						liquidazioneBean.setTerminiPagamentoDataDecorrenza(getStandardValueCell(ConstantsCampiExcelLiquidazioneAVB.TERMINIPAGAMENTO_DATA_DECORRENZA,
								formulaEvaluator, sheet.getRow(ConstantsCampiExcelLiquidazioneAVB.TERMINIPAGAMENTO_DATA_DECORRENZA), null, nomeSezione));
					} else if (StringUtils.isBlank(getStandardValueCell(ConstantsCampiExcelLiquidazioneAVB.TERMINIPAGAMENTO_DATA_DECORRENZA,
							formulaEvaluator, sheet.getRow(ConstantsCampiExcelLiquidazioneAVB.TERMINIPAGAMENTO_DATA_DECORRENZA), null, nomeSezione))) {
						throw new ValidatorException(
								"La \"Data di decorrenza\" della sezione " + nomeSezione + ", in riga " + (ConstantsCampiExcelLiquidazioneAVB.TERMINIPAGAMENTO_DATA_DECORRENZA+1) + " e colonna " + (INDICE_COLONNA_VALORE_RIGA+1) + ", è obbligatoria");
					} else if (StringUtils.isNotBlank(getStandardValueCell(ConstantsCampiExcelLiquidazioneAVB.TERMINIPAGAMENTO_DATA_DECORRENZA,
							formulaEvaluator, sheet.getRow(ConstantsCampiExcelLiquidazioneAVB.TERMINIPAGAMENTO_DATA_DECORRENZA), null, nomeSezione))) {
						throw new ValidatorException(
								"Solo uno tra l'insieme di \"Giorni di scadenza\" e \"Data di decorrenza\" oppure \"Data di scadenza\" della sezione " + nomeSezione + " è obbligatorio");
					}
				} else if (sheetHssf!=null) {
					if (StringUtils.isNotBlank(getStandardValueCell(ConstantsCampiExcelLiquidazioneAVB.TERMINIPAGAMENTO_DATA_DECORRENZA,
							formulaEvaluator, null, sheetHssf.getRow(ConstantsCampiExcelLiquidazioneAVB.TERMINIPAGAMENTO_DATA_DECORRENZA), nomeSezione))
							&& StringUtils.isBlank(getStandardValueCell(ConstantsCampiExcelLiquidazioneAVB.TERMINIPAGAMENTO_DATA_SCADENZA, formulaEvaluator,
									null, sheetHssf.getRow(ConstantsCampiExcelLiquidazioneAVB.TERMINIPAGAMENTO_DATA_SCADENZA), nomeSezione))) {
						liquidazioneBean.setTerminiPagamentoGgScadenza(valueCell);
						liquidazioneBean.setTerminiPagamentoDataDecorrenza(getStandardValueCell(ConstantsCampiExcelLiquidazioneAVB.TERMINIPAGAMENTO_DATA_DECORRENZA,
								formulaEvaluator, null, sheetHssf.getRow(ConstantsCampiExcelLiquidazioneAVB.TERMINIPAGAMENTO_DATA_DECORRENZA), nomeSezione));
					} else if (StringUtils.isBlank(getStandardValueCell(ConstantsCampiExcelLiquidazioneAVB.TERMINIPAGAMENTO_DATA_DECORRENZA,
							formulaEvaluator, null, sheetHssf.getRow(ConstantsCampiExcelLiquidazioneAVB.TERMINIPAGAMENTO_DATA_DECORRENZA), nomeSezione))) {
						throw new ValidatorException(
								"La \"Data di decorrenza\" della sezione " + nomeSezione + ", in riga " + (ConstantsCampiExcelLiquidazioneAVB.TERMINIPAGAMENTO_DATA_DECORRENZA+1) + " e colonna " + (INDICE_COLONNA_VALORE_RIGA+1) + ", è obbligatoria");
					} else if (StringUtils.isNotBlank(getStandardValueCell(ConstantsCampiExcelLiquidazioneAVB.TERMINIPAGAMENTO_DATA_DECORRENZA,
							formulaEvaluator, null, sheetHssf.getRow(ConstantsCampiExcelLiquidazioneAVB.TERMINIPAGAMENTO_DATA_SCADENZA), nomeSezione))) {
						throw new ValidatorException(
								"Solo uno tra l'insieme di \"Giorni di scadenza\" e \"Data di decorrenza\" oppure \"Data di scadenza\" della sezione " + nomeSezione + " è obbligatorio");
					} 
				}
			} else {
				if (sheet!=null) {
					if (StringUtils.isBlank(getStandardValueCell(ConstantsCampiExcelLiquidazioneAVB.TERMINIPAGAMENTO_DATA_DECORRENZA,
							formulaEvaluator, sheet.getRow(ConstantsCampiExcelLiquidazioneAVB.TERMINIPAGAMENTO_DATA_DECORRENZA), null, nomeSezione))
							&& StringUtils.isBlank(getStandardValueCell(ConstantsCampiExcelLiquidazioneAVB.TERMINIPAGAMENTO_DATA_SCADENZA, formulaEvaluator,
									sheet.getRow(ConstantsCampiExcelLiquidazioneAVB.TERMINIPAGAMENTO_DATA_SCADENZA), null, nomeSezione))) {
						throw new ValidatorException(
								"Uno tra l'insieme di \"Giorni di scadenza\" e \"Data di decorrenza\" oppure \"Data di scadenza\" della sezione " + nomeSezione + ", in riga " + (campo+1) + " e colonna " + (INDICE_COLONNA_VALORE_RIGA+1) + ", è obbligatorio");
					} else if (StringUtils.isNotBlank(getStandardValueCell(ConstantsCampiExcelLiquidazioneAVB.TERMINIPAGAMENTO_DATA_DECORRENZA,
							formulaEvaluator, sheet.getRow(ConstantsCampiExcelLiquidazioneAVB.TERMINIPAGAMENTO_DATA_DECORRENZA), null, nomeSezione))) {
						throw new ValidatorException(
								"Il dato \"Giorni di scadenza\" della sezione " + nomeSezione + ", in riga " + (campo+1) + " e colonna " + (INDICE_COLONNA_VALORE_RIGA+1) + ", è obbligatorio");
					} else if (StringUtils.isNotBlank(getStandardValueCell(ConstantsCampiExcelLiquidazioneAVB.TERMINIPAGAMENTO_DATA_SCADENZA,
							formulaEvaluator, sheet.getRow(ConstantsCampiExcelLiquidazioneAVB.TERMINIPAGAMENTO_DATA_SCADENZA), null, nomeSezione))) {
						liquidazioneBean.setTerminiPagamentoDataScadenza(getStandardValueCell(ConstantsCampiExcelLiquidazioneAVB.TERMINIPAGAMENTO_DATA_SCADENZA,
								formulaEvaluator, sheet.getRow(ConstantsCampiExcelLiquidazioneAVB.TERMINIPAGAMENTO_DATA_SCADENZA), null, nomeSezione));
					}
				} else if (sheetHssf!=null) {
					if (StringUtils.isBlank(getStandardValueCell(ConstantsCampiExcelLiquidazioneAVB.TERMINIPAGAMENTO_DATA_DECORRENZA,
							formulaEvaluator, null, sheetHssf.getRow(ConstantsCampiExcelLiquidazioneAVB.TERMINIPAGAMENTO_DATA_DECORRENZA), nomeSezione))
							&& StringUtils.isBlank(getStandardValueCell(ConstantsCampiExcelLiquidazioneAVB.TERMINIPAGAMENTO_DATA_SCADENZA, formulaEvaluator,
									null, sheetHssf.getRow(ConstantsCampiExcelLiquidazioneAVB.TERMINIPAGAMENTO_DATA_SCADENZA), nomeSezione))) {
						throw new ValidatorException(
								"Uno tra l'insieme di \"Giorni di scadenza\" e \"Data di decorrenza\" oppure \"Data di scadenza\" della sezione " + nomeSezione + ", in riga " + (campo+1) + " e colonna " + (INDICE_COLONNA_VALORE_RIGA+1) + ", è obbligatorio");
					} else if (StringUtils.isNotBlank(getStandardValueCell(ConstantsCampiExcelLiquidazioneAVB.TERMINIPAGAMENTO_DATA_DECORRENZA,
							formulaEvaluator, null, sheetHssf.getRow(ConstantsCampiExcelLiquidazioneAVB.TERMINIPAGAMENTO_DATA_DECORRENZA), nomeSezione))) {
						throw new ValidatorException(
								"Il dato \"Giorni di scadenza\" della sezione " + nomeSezione + ", in riga " + (campo+1) + " e colonna " + (INDICE_COLONNA_VALORE_RIGA+1) + ", è obbligatorio");
					} else if (StringUtils.isNotBlank(getStandardValueCell(ConstantsCampiExcelLiquidazioneAVB.TERMINIPAGAMENTO_DATA_SCADENZA,
							formulaEvaluator, null, sheetHssf.getRow(ConstantsCampiExcelLiquidazioneAVB.TERMINIPAGAMENTO_DATA_SCADENZA), nomeSezione))) {
						liquidazioneBean.setTerminiPagamentoDataScadenza(getStandardValueCell(ConstantsCampiExcelLiquidazioneAVB.TERMINIPAGAMENTO_DATA_SCADENZA,
								formulaEvaluator, null, sheetHssf.getRow(ConstantsCampiExcelLiquidazioneAVB.TERMINIPAGAMENTO_DATA_SCADENZA), nomeSezione));
					}
				}
			}
			break;
//		case ConstantsCampiExcelLiquidazioneAVB.TERMINIPAGAMENTO_DATA_DECORRENZA:
//			valueCell = getStandardValueCell(campo, formulaEvaluator, row, rowHssf, nomeSezione );
//			if (StringUtils.isNotBlank(valueCell)) {
//				liquidazioneBean.setTerminiPagamentoDataDecorrenza(valueCell);
//			} else {
//				if (sheet!=null) {
//					if (StringUtils.isBlank(getStandardValueCell(ConstantsCampiExcelLiquidazioneAVB.TERMINIPAGAMENTO_GG_SCADENZA, formulaEvaluator, sheet.getRow(ConstantsCampiExcelLiquidazioneAVB.TERMINIPAGAMENTO_GG_SCADENZA), null, nomeSezione))
//							|| StringUtils.isBlank(getStandardValueCell(ConstantsCampiExcelLiquidazioneAVB.TERMINIPAGAMENTO_DATA_SCADENZA, formulaEvaluator, sheet.getRow(ConstantsCampiExcelLiquidazioneAVB.TERMINIPAGAMENTO_DATA_SCADENZA), null, nomeSezione))) {
//						throw new ValidatorException(
//								"Uno tra l'insieme di \"Giorni di scadenza\" e \"Data di decorrenza\" oppure \"Data di scadenza\" della sezione " + nomeSezione + ", in riga " + (campo+1) + " e colonna " + (INDICE_COLONNA_VALORE_RIGA+1) + ", è obbligatorio");
//					} 
//				} else if (sheetHssf!=null) {
//					if (StringUtils.isBlank(getStandardValueCell(ConstantsCampiExcelLiquidazioneAVB.TERMINIPAGAMENTO_GG_SCADENZA, formulaEvaluator, null, sheetHssf.getRow(ConstantsCampiExcelLiquidazioneAVB.TERMINIPAGAMENTO_GG_SCADENZA), nomeSezione))
//							|| StringUtils.isBlank(getStandardValueCell(ConstantsCampiExcelLiquidazioneAVB.TERMINIPAGAMENTO_DATA_SCADENZA, formulaEvaluator, null, sheetHssf.getRow(ConstantsCampiExcelLiquidazioneAVB.TERMINIPAGAMENTO_DATA_SCADENZA), nomeSezione))) {
//						throw new ValidatorException(
//								"Uno tra l'insieme di \"Giorni di scadenza\" e \"Data di decorrenza\" oppure \"Data di scadenza\" della sezione " + nomeSezione + ", in riga " + (campo+1) + " e colonna " + (INDICE_COLONNA_VALORE_RIGA+1) + ", è obbligatorio");
//					} 
//				}
//			}
//			break;
//		case ConstantsCampiExcelLiquidazioneAVB.TERMINIPAGAMENTO_DATA_SCADENZA:
//			valueCell = getStandardValueCell(campo, formulaEvaluator, row, rowHssf, nomeSezione );
//			if (StringUtils.isNotBlank(valueCell)) {
//				liquidazioneBean.setTerminiPagamentoDataScadenza(valueCell);
//			} else {
//				if (sheet!=null) {
//					if (StringUtils.isBlank(getStandardValueCell(ConstantsCampiExcelLiquidazioneAVB.TERMINIPAGAMENTO_GG_SCADENZA, formulaEvaluator, sheet.getRow(ConstantsCampiExcelLiquidazioneAVB.TERMINIPAGAMENTO_GG_SCADENZA), null, nomeSezione))
//							|| StringUtils.isBlank(getStandardValueCell(ConstantsCampiExcelLiquidazioneAVB.TERMINIPAGAMENTO_DATA_DECORRENZA, formulaEvaluator, sheet.getRow(ConstantsCampiExcelLiquidazioneAVB.TERMINIPAGAMENTO_DATA_DECORRENZA), null, nomeSezione))) {
//						throw new ValidatorException(
//								"Uno tra l'insieme di \"Giorni di scadenza\" e \"Data di decorrenza\" oppure \"Data di scadenza\" della sezione " + nomeSezione + ", in riga " + (campo+1) + " e colonna " + (INDICE_COLONNA_VALORE_RIGA+1) + ", è obbligatorio");
//					} 
//				} else if (sheetHssf!=null) {
//					if (StringUtils.isBlank(getStandardValueCell(ConstantsCampiExcelLiquidazioneAVB.TERMINIPAGAMENTO_GG_SCADENZA, formulaEvaluator, null, sheetHssf.getRow(ConstantsCampiExcelLiquidazioneAVB.TERMINIPAGAMENTO_GG_SCADENZA), nomeSezione))
//							|| StringUtils.isBlank(getStandardValueCell(ConstantsCampiExcelLiquidazioneAVB.TERMINIPAGAMENTO_DATA_DECORRENZA, formulaEvaluator, null, sheetHssf.getRow(ConstantsCampiExcelLiquidazioneAVB.TERMINIPAGAMENTO_DATA_DECORRENZA), nomeSezione))) {
//						throw new ValidatorException(
//								"Uno tra l'insieme di \"Giorni di scadenza\" e \"Data di decorrenza\" oppure \"Data di scadenza\" della sezione " + nomeSezione + ", in riga " + (campo+1) + " e colonna " + (INDICE_COLONNA_VALORE_RIGA+1) + ", è obbligatorio");
//					} 
//				}
//			}
//			break;
		case ConstantsCampiExcelLiquidazioneAVB.TERMINIPAGAMENTO_MOTIVI_INTERRUZIONE_TERMINI:
			valueCell = getStandardValueCell(campo, formulaEvaluator, row, rowHssf, nomeSezione );
			if (StringUtils.isNotBlank(valueCell)) {
				liquidazioneBean.setTerminiPagamentoMotiviInterruzioneTermini(valueCell);
			}
			break;
		case ConstantsCampiExcelLiquidazioneAVB.TERMINIPAGAMENTO_DATA_INIZIO_SOSPENSIONE:
			valueCell = getStandardValueCell(campo, formulaEvaluator, row, rowHssf, nomeSezione );
			if (StringUtils.isNotBlank(valueCell)) {
				liquidazioneBean.setTerminiPagamentoDataInizioSospensione(valueCell);
			}
			break;
		case ConstantsCampiExcelLiquidazioneAVB.TERMINIPAGAMENTO_MOTIVI_ESCLUSIONE_CALCOLO_TMP:
			valueCell = getStandardValueCell(campo, formulaEvaluator, row, rowHssf, nomeSezione );
			if (StringUtils.isNotBlank(valueCell)) {
				liquidazioneBean.setTerminiPagamentoMotiviEsclusioneCalcoloTmp(valueCell);
			}
			break;
		}
	}

	private void manageCampiCigCup(DatiLiquidazioneAVBBean liquidazioneBean, int campo, FormulaEvaluator formulaEvaluator, XSSFRow row, HSSFRow rowHssf, XSSFSheet sheet, HSSFSheet sheetHssf) throws Exception {
		String valueCell;
		String nomeSezione = "CIG/CUP";
		switch (campo) {

		case ConstantsCampiExcelLiquidazioneAVB.CIGCUP_FLG_APPLICABILITA:
			valueCell = getStandardValueCell(campo, formulaEvaluator, row, rowHssf, nomeSezione );
			if (StringUtils.isNotBlank(valueCell)) {
				liquidazioneBean.setCigCupFlgApplicabillita(valueCell);
			} else {
				throw new ValidatorException("Il dato \"Applicabilità della L. 136/2010 in materia di tracciabilità dei flussi finanziari\" della sezione " + nomeSezione + ", in riga " + (campo+1) + " e colonna " + (INDICE_COLONNA_VALORE_RIGA+1) + ", è obbligatorio");
			}
			break;
		case ConstantsCampiExcelLiquidazioneAVB.CIGCUP_SMART_CIG:
			valueCell = getStandardValueCell(campo, formulaEvaluator, row, rowHssf, nomeSezione );
			if (StringUtils.isNotBlank(valueCell)) {
				if (sheet!=null) {
					if (StringUtils.isBlank(getStandardValueCell(ConstantsCampiExcelLiquidazioneAVB.CIGCUP_CIG,
							formulaEvaluator, sheet.getRow(ConstantsCampiExcelLiquidazioneAVB.CIGCUP_CIG), null, nomeSezione))) {
						liquidazioneBean.setCigCupSmartCig(valueCell);
					} else {
						throw new ValidatorException(
								"Solo uno tra i dati \"Smart CIG\" e \"CIG\" della sezione " + nomeSezione + " deve essere obbligatorio");
					}
				} else if(sheetHssf!=null) {
					if (StringUtils.isBlank(getStandardValueCell(ConstantsCampiExcelLiquidazioneAVB.CIGCUP_CIG,
							formulaEvaluator, null, sheetHssf.getRow(ConstantsCampiExcelLiquidazioneAVB.CIGCUP_CIG), nomeSezione))) {
						liquidazioneBean.setCigCupSmartCig(valueCell);
					} else {
						throw new ValidatorException(
								"Solo uno tra i dati \"Smart CIG\" e \"CIG\" della sezione " + nomeSezione + " deve essere obbligatorio");
					}
				}
			} else {
				if (sheet!=null) {
					if (StringUtils.isBlank(getStandardValueCell(ConstantsCampiExcelLiquidazioneAVB.CIGCUP_CIG,
							formulaEvaluator, sheet.getRow(ConstantsCampiExcelLiquidazioneAVB.CIGCUP_CIG), null, nomeSezione))) {
						throw new ValidatorException(
								"Uno tra i dati \"Smart CIG\" e \"CIG\" della sezione " + nomeSezione + ", in riga " + (campo+1) + " e colonna " + (INDICE_COLONNA_VALORE_RIGA+1) + ", deve essere obbligatorio");
					} else {
						liquidazioneBean.setCigCupCig(getStandardValueCell(ConstantsCampiExcelLiquidazioneAVB.CIGCUP_CIG,
								formulaEvaluator, sheet.getRow(ConstantsCampiExcelLiquidazioneAVB.CIGCUP_CIG), null, nomeSezione));
					}
				} else if(sheetHssf!=null) {
					if (StringUtils.isBlank(getStandardValueCell(ConstantsCampiExcelLiquidazioneAVB.CIGCUP_CIG,
							formulaEvaluator, null, sheetHssf.getRow(ConstantsCampiExcelLiquidazioneAVB.CIGCUP_CIG), nomeSezione))) {
						throw new ValidatorException(
								"Uno tra i dati \"Smart CIG\" e \"CIG\" della sezione " + nomeSezione + ", in riga " + (campo+1) + " e colonna " + (INDICE_COLONNA_VALORE_RIGA+1) + ", deve essere obbligatorio");
					} else {
						liquidazioneBean.setCigCupCig(getStandardValueCell(ConstantsCampiExcelLiquidazioneAVB.CIGCUP_CIG,
								formulaEvaluator, null, sheetHssf.getRow(ConstantsCampiExcelLiquidazioneAVB.CIGCUP_CIG), nomeSezione));
					}
				}
			}
			break;
//		case ConstantsCampiExcelLiquidazioneAVB.CIGCUP_CIG:
//			valueCell = getStandardValueCell(campo, formulaEvaluator, row, rowHssf, nomeSezione );
//			if (StringUtils.isNotBlank(valueCell)) {
//				liquidazioneBean.setCigCupCig(valueCell);
//			} else {
//				if (sheet!=null) {
//					if (StringUtils.isBlank(getStandardValueCell(ConstantsCampiExcelLiquidazioneAVB.CIGCUP_SMART_CIG,
//							formulaEvaluator, sheet.getRow(ConstantsCampiExcelLiquidazioneAVB.CIGCUP_SMART_CIG), null, nomeSezione))) {
//						throw new ValidatorException(
//								"Uno tra i dati \"Smart CIG\" e \"CIG\" della sezione " + nomeSezione + ", in riga " + (campo+1) + " e colonna " + (INDICE_COLONNA_VALORE_RIGA+1) + ", deve essere obbligatorio");
//					} 
//				} else if (sheetHssf!=null) {
//					if (StringUtils.isBlank(getStandardValueCell(ConstantsCampiExcelLiquidazioneAVB.CIGCUP_SMART_CIG,
//							formulaEvaluator, null, sheetHssf.getRow(ConstantsCampiExcelLiquidazioneAVB.CIGCUP_SMART_CIG), nomeSezione))) {
//						throw new ValidatorException(
//								"Uno tra i dati \"Smart CIG\" e \"CIG\" della sezione " + nomeSezione + ", in riga " + (campo+1) + " e colonna " + (INDICE_COLONNA_VALORE_RIGA+1) + ", deve essere obbligatorio");
//					} 
//				}
//			}
//			break;
		case ConstantsCampiExcelLiquidazioneAVB.CIGCUP_FLG_ACQUISITO_PERFEZIONATO:
			valueCell = getStandardValueCell(campo, formulaEvaluator, row, rowHssf, nomeSezione );
			if (StringUtils.isNotBlank(valueCell)) {
				liquidazioneBean.setCigCupFlgAcquisitoPerfezionato(valueCell);
			}
			break;
		case ConstantsCampiExcelLiquidazioneAVB.CIGCUP_CUP:
			valueCell = getStandardValueCell(campo, formulaEvaluator, row, rowHssf, nomeSezione );
			if (StringUtils.isNotBlank(valueCell)) {
				liquidazioneBean.setCigCupCup(valueCell);
			}
			break;
		case ConstantsCampiExcelLiquidazioneAVB.CIGCUP_MOTIVI_ESCLUSIONE:
			valueCell = getStandardValueCell(campo, formulaEvaluator, row, rowHssf, nomeSezione );
			if (StringUtils.isNotBlank(valueCell)) {
				liquidazioneBean.setCigCupMotiviEsclusione(valueCell);
			} else {
				if (sheet!=null) {
					if ("SI".equalsIgnoreCase(
							getStandardValueCell(ConstantsCampiExcelLiquidazioneAVB.OGGETTOSPESA_FLG_DEBITO_COMMERCIALE,
									formulaEvaluator, sheet.getRow(ConstantsCampiExcelLiquidazioneAVB.OGGETTOSPESA_FLG_DEBITO_COMMERCIALE), null, nomeSezione))) {
						throw new ValidatorException(
								"Il campo in cui esplicitare i motivi di esclusione dalla disciplina sulla tracciabilità della sezione " + nomeSezione + ", in riga " + (campo+1) + " e colonna " + (INDICE_COLONNA_VALORE_RIGA+1) + ", è obbligatorio in caso di debito commerciale");
					} 
				} else if (sheetHssf!=null) {
					if ("SI".equalsIgnoreCase(
							getStandardValueCell(ConstantsCampiExcelLiquidazioneAVB.OGGETTOSPESA_FLG_DEBITO_COMMERCIALE,
									formulaEvaluator, null, sheetHssf.getRow(ConstantsCampiExcelLiquidazioneAVB.OGGETTOSPESA_FLG_DEBITO_COMMERCIALE), nomeSezione))) {
						throw new ValidatorException(
								"Il campo in cui esplicitare i motivi di esclusione dalla disciplina sulla tracciabilità della sezione " + nomeSezione + ", in riga " + (campo+1) + " e colonna " + (INDICE_COLONNA_VALORE_RIGA+1) + ", è obbligatorio in caso di debito commerciale");
					} 
				}
			}
			break;
		case ConstantsCampiExcelLiquidazioneAVB.CIGCUP_ULT_INF:
			valueCell = getStandardValueCell(campo, formulaEvaluator, row, rowHssf, nomeSezione );
			if (StringUtils.isNotBlank(valueCell)) {
				liquidazioneBean.setCigCupUltInf(valueCell);
			}
			break;
		}
	}

	private void manageCampiVerificheEquitaliaAntiriciclaggio(DatiLiquidazioneAVBBean liquidazioneBean, int campo, FormulaEvaluator formulaEvaluator, XSSFRow row, HSSFRow rowHssf, XSSFSheet sheet, HSSFSheet sheetHssf) throws Exception {

		String valueCell;
		String nomeSezione = "Verifiche ex Equitalia- Antiriciclaggio";

		switch (campo) {

		case ConstantsCampiExcelLiquidazioneAVB.ANTIRICICLAGGIO_FLG_VERIFICA_NON_INADEMPIENZA:
			valueCell = getStandardValueCell(campo, formulaEvaluator, row, rowHssf, nomeSezione);
			if (StringUtils.isNotBlank(valueCell)) {
				liquidazioneBean.setAntiriciclaggioFlgVerificaNonInadempienza(valueCell);
			} else {
				throw new ValidatorException("Il dato \"Assoggettabilità alla verifica della situazione di non inadempienza...\" della sezione " + nomeSezione + ", in riga " + (campo+1) + " e colonna " + (INDICE_COLONNA_VALORE_RIGA+1) + ", è obbligatorio");
			}
			break;
		case ConstantsCampiExcelLiquidazioneAVB.ANTIRICICLAGGIO_FLG_VERIFICA_DISCIPLINA_PRASSI_SETTORE:
			valueCell = getStandardValueCell(campo, formulaEvaluator, row, rowHssf, nomeSezione);
			if (StringUtils.isNotBlank(valueCell)) {
				liquidazioneBean.setAntiriciclaggioFlgVerificaDisciplinaPrassiSettore(valueCell);
			} else {
				throw new ValidatorException("Il dato \"Verifiche compiute in attuazione del D.Lgs. 231/2007...\" della sezione " + nomeSezione + ", in riga " + (campo+1) + " e colonna " + (INDICE_COLONNA_VALORE_RIGA+1) + ", è obbligatorio");
			}
			break;
		case ConstantsCampiExcelLiquidazioneAVB.ANTIRICICLAGGIO_FLG_RISULTATO_NON_INADEMPIENTE:
			valueCell = getStandardValueCell(campo, formulaEvaluator, row, rowHssf, nomeSezione);
			if (StringUtils.isNotBlank(valueCell)) {
				liquidazioneBean.setAntiriciclaggioFlgRisultatoNonInadempiente(valueCell);
			}else {
				if (sheet!=null) {
					if ("SI".equalsIgnoreCase(getStandardValueCell(
							ConstantsCampiExcelLiquidazioneAVB.ANTIRICICLAGGIO_FLG_VERIFICA_NON_INADEMPIENZA,
							formulaEvaluator, sheet.getRow(ConstantsCampiExcelLiquidazioneAVB.ANTIRICICLAGGIO_FLG_VERIFICA_NON_INADEMPIENZA), null, nomeSezione))) {
						throw new ValidatorException(
								"Il dato \"Risultato non inadempiente alla suddetta verifica\" della sezione Verifiche ex Equitalia- Antiriciclaggio, in riga " + (campo+1) + " e colonna " + (INDICE_COLONNA_VALORE_RIGA+1) + ", è obbligatorio se risulta assoggettabile alla verifica della situazione di non inadempienza");
					} 
				} else if (sheetHssf!=null) {
					if ("SI".equalsIgnoreCase(getStandardValueCell(
							ConstantsCampiExcelLiquidazioneAVB.ANTIRICICLAGGIO_FLG_VERIFICA_NON_INADEMPIENZA,
							formulaEvaluator, null, sheetHssf.getRow(ConstantsCampiExcelLiquidazioneAVB.ANTIRICICLAGGIO_FLG_VERIFICA_NON_INADEMPIENZA), nomeSezione))) {
						throw new ValidatorException(
								"Il dato \"Risultato non inadempiente alla suddetta verifica\" della sezione Verifiche ex Equitalia- Antiriciclaggio, in riga " + (campo+1) + " e colonna " + (INDICE_COLONNA_VALORE_RIGA+1) + ", è obbligatorio se risulta assoggettabile alla verifica della situazione di non inadempienza");
					} 
				}
			}
			break;		
		}				
	}

	private void manageCampiDURC(DatiLiquidazioneAVBBean liquidazioneBean, int campo, FormulaEvaluator formulaEvaluator, XSSFRow row, HSSFRow rowHssf, XSSFSheet sheet, HSSFSheet sheetHssf) throws Exception {
		String valueCell;
		String nomeSezione = "DURC";

		switch (campo) {

		case ConstantsCampiExcelLiquidazioneAVB.DURC_FLG_VERIFICA_REG_CONTRIBUTIVA:
			valueCell = getStandardValueCell(campo, formulaEvaluator, row, rowHssf, nomeSezione);
			if (StringUtils.isNotBlank(valueCell)) {
				liquidazioneBean.setRegimeIvaBolloAttivitaIva(valueCell);
			}else {
				throw new ValidatorException("Il dato Assoggettabilità alla verifica della regolarità contributiva della sezione " + nomeSezione + ", in riga " + (campo+1) + " e colonna " + (INDICE_COLONNA_VALORE_RIGA+1) + ", è obbligatorio");
			}
			break;
		case ConstantsCampiExcelLiquidazioneAVB.DURC_FLG_REGOLARE_DURC:
			valueCell = getStandardValueCell(campo, formulaEvaluator, row, rowHssf, nomeSezione);
			if (StringUtils.isNotBlank(valueCell)) {
				liquidazioneBean.setRegimeIvaBolloTipoAttivita(valueCell);
			}else {
				if (sheet!=null) {
					if ("SI".equalsIgnoreCase(
							getStandardValueCell(ConstantsCampiExcelLiquidazioneAVB.DURC_FLG_VERIFICA_REG_CONTRIBUTIVA,
									formulaEvaluator, sheet.getRow(ConstantsCampiExcelLiquidazioneAVB.DURC_FLG_VERIFICA_REG_CONTRIBUTIVA), null, nomeSezione))) {
						throw new ValidatorException("Il dato Regolare ai fini DURC della sezione " + nomeSezione + ", in riga " + (campo+1) + " e colonna " + (INDICE_COLONNA_VALORE_RIGA+1) + ", è obbligatorio");
					} 
				} else if (sheetHssf!=null) {
					if ("SI".equalsIgnoreCase(
							getStandardValueCell(ConstantsCampiExcelLiquidazioneAVB.DURC_FLG_VERIFICA_REG_CONTRIBUTIVA,
									formulaEvaluator, null, sheetHssf.getRow(ConstantsCampiExcelLiquidazioneAVB.DURC_FLG_VERIFICA_REG_CONTRIBUTIVA), nomeSezione))) {
						throw new ValidatorException("Il dato Regolare ai fini DURC della sezione " + nomeSezione + ", in riga " + (campo+1) + " e colonna " + (INDICE_COLONNA_VALORE_RIGA+1) + ", è obbligatorio");
					}
				}
			}
			break;
		case ConstantsCampiExcelLiquidazioneAVB.DURC_FLG_ULTERIORE_VERIFICA:
			valueCell = getStandardValueCell(campo, formulaEvaluator, row, rowHssf, nomeSezione);
			if (StringUtils.isNotBlank(valueCell)) {
				liquidazioneBean.setRegimeIvaBolloRegimeIva(valueCell);
			}
			break;
		case ConstantsCampiExcelLiquidazioneAVB.DURC_INTERVENTO_SOSTITUTIVO:
			valueCell = getStandardValueCell(campo, formulaEvaluator, row, rowHssf, nomeSezione);
			if (StringUtils.isNotBlank(valueCell)) {
				liquidazioneBean.setRegimeIvaBolloAltro(valueCell);
			}else {
				if (sheet!=null) {
					if ("NO".equalsIgnoreCase(
							getStandardValueCell(ConstantsCampiExcelLiquidazioneAVB.DURC_FLG_ULTERIORE_VERIFICA,
									formulaEvaluator, sheet.getRow(ConstantsCampiExcelLiquidazioneAVB.DURC_FLG_ULTERIORE_VERIFICA), null, nomeSezione))) {
						throw new ValidatorException("Il dato Regolare ai fini DURC della sezione " + nomeSezione + ", in riga " + (campo+1) + " e colonna " + (INDICE_COLONNA_VALORE_RIGA+1) + ", è obbligatorio");
					} 
				} else if (sheetHssf!=null) {
					if ("NO".equalsIgnoreCase(
							getStandardValueCell(ConstantsCampiExcelLiquidazioneAVB.DURC_FLG_ULTERIORE_VERIFICA,
									formulaEvaluator, null, sheetHssf.getRow(ConstantsCampiExcelLiquidazioneAVB.DURC_FLG_ULTERIORE_VERIFICA), nomeSezione))) {
						throw new ValidatorException("Il dato Regolare ai fini DURC della sezione " + nomeSezione + ", in riga " + (campo+1) + " e colonna " + (INDICE_COLONNA_VALORE_RIGA+1) + ", è obbligatorio");
					} 
				}
			}
			break;				
		}		
	}

	private void manageCampiRegimeIvaBollo(DatiLiquidazioneAVBBean liquidazioneBean, int campo, FormulaEvaluator formulaEvaluator, XSSFRow row, HSSFRow rowHssf, XSSFSheet sheet, HSSFSheet sheetHssf) throws Exception {

		String valueCell;
		String nomeSezione = "Regime Iva\\Bollo";

		switch (campo) {

		case ConstantsCampiExcelLiquidazioneAVB.REGIMEIVABOLLO_ATTIVITA_IVA:
			valueCell = getStandardValueCell(campo, formulaEvaluator, row, rowHssf, nomeSezione);
			if (StringUtils.isNotBlank(valueCell)) {
				liquidazioneBean.setRegimeIvaBolloAttivitaIva(valueCell);
			}
			break;
		case ConstantsCampiExcelLiquidazioneAVB.REGIMEIVABOLLO_TIPO_ATTIVITA:
			if (sheet!=null) {
				valueCell = getStandardValueCell(campo, formulaEvaluator, row, null, nomeSezione);
				if (StringUtils.isNotBlank(valueCell) && TIPO_ATTIVITA_COMMERCIALE.equalsIgnoreCase(getStandardValueCell(ConstantsCampiExcelLiquidazioneAVB.REGIMEIVABOLLO_ATTIVITA_IVA,
						formulaEvaluator, sheet.getRow(ConstantsCampiExcelLiquidazioneAVB.REGIMEIVABOLLO_ATTIVITA_IVA), null, nomeSezione))) {
					liquidazioneBean.setRegimeIvaBolloTipoAttivita(valueCell);
				} else if (StringUtils.isNotBlank(valueCell) && !TIPO_ATTIVITA_COMMERCIALE.equalsIgnoreCase(getStandardValueCell(ConstantsCampiExcelLiquidazioneAVB.REGIMEIVABOLLO_ATTIVITA_IVA,
						formulaEvaluator, sheet.getRow(ConstantsCampiExcelLiquidazioneAVB.REGIMEIVABOLLO_ATTIVITA_IVA), null, nomeSezione))) {
					throw new ValidatorException("Indicare la tipologia commerciale della sezione " + nomeSezione + ", in riga " + (campo+1) + " e colonna " + (INDICE_COLONNA_VALORE_RIGA+1) + ", solo se Attività Commerciale");
				}
			} else if (sheetHssf!=null) {
				valueCell = getStandardValueCell(campo, formulaEvaluator, null, rowHssf, nomeSezione);
				if (StringUtils.isNotBlank(valueCell) && TIPO_ATTIVITA_COMMERCIALE.equalsIgnoreCase(getStandardValueCell(ConstantsCampiExcelLiquidazioneAVB.REGIMEIVABOLLO_ATTIVITA_IVA,
						formulaEvaluator, null, sheetHssf.getRow(ConstantsCampiExcelLiquidazioneAVB.REGIMEIVABOLLO_ATTIVITA_IVA), nomeSezione))) {
					liquidazioneBean.setRegimeIvaBolloTipoAttivita(valueCell);
				} else if (StringUtils.isNotBlank(valueCell) && !TIPO_ATTIVITA_COMMERCIALE.equalsIgnoreCase(getStandardValueCell(ConstantsCampiExcelLiquidazioneAVB.REGIMEIVABOLLO_ATTIVITA_IVA,
						formulaEvaluator, null, sheetHssf.getRow(ConstantsCampiExcelLiquidazioneAVB.REGIMEIVABOLLO_ATTIVITA_IVA), nomeSezione))) {
					throw new ValidatorException("Indicare la tipologia commerciale della sezione " + nomeSezione + ", in riga " + (campo+1) + " e colonna " + (INDICE_COLONNA_VALORE_RIGA+1) + ", solo se Attività Commerciale");
				}
			}
			break;
		case ConstantsCampiExcelLiquidazioneAVB.REGIMEIVABOLLO_REGIME_IVA:
			valueCell = getStandardValueCell(campo, formulaEvaluator, row, rowHssf, nomeSezione);
			if (StringUtils.isNotBlank(valueCell)) {
				liquidazioneBean.setRegimeIvaBolloRegimeIva(valueCell);
			}else {
				throw new ValidatorException("Il dato regime Iva della sezione " + nomeSezione + ", in riga " + (campo+1) + " e colonna " + (INDICE_COLONNA_VALORE_RIGA+1) + ", è obbligatorio");
			}
			break;
		case ConstantsCampiExcelLiquidazioneAVB.REGIMEIVABOLLO_ALTRO:
			valueCell = getStandardValueCell(campo, formulaEvaluator, row, rowHssf, nomeSezione);
			if (StringUtils.isNotBlank(valueCell)) {
				liquidazioneBean.setRegimeIvaBolloAltro(valueCell);
			}
			break;				
		}		
	}

	private void manageCampiDisimpegno(DatiLiquidazioneAVBBean liquidazioneBean, FormulaEvaluator formulaEvaluator, XSSFSheet sheet, HSSFSheet sheetHssf, int campo) throws Exception {
		String nomeSezione = "Disimpegno";
		int numColonne = 0;
		if (sheet != null) {
			XSSFRow initialRow = sheet.getRow(campo);
			numColonne = initialRow.getLastCellNum();
		} else {
			HSSFRow row = sheetHssf.getRow(campo);
			numColonne = row.getLastCellNum();
		}

		List<DisimpegnoAVBBean> listaDisimpegni = new ArrayList<DisimpegnoAVBBean>();

		for (int indiceColonna = 5; indiceColonna < numColonne; indiceColonna++) {	
			DisimpegnoAVBBean disimpegno = new DisimpegnoAVBBean();

//			if(manageIsEndOfColumn(sheet, sheetHssf, indiceColonna, ConstantsCampiExcelLiquidazioneAVB.DISIMPEGNO_IMPORTO)) {
//				break;
//			}
			
			String valueCell = "";
			boolean isObbligatorioNull = false;
			
			valueCell = getValueCell(ConstantsCampiExcelLiquidazioneAVB.DISIMPEGNO_IMPORTO, formulaEvaluator, sheet != null ? sheet.getRow(ConstantsCampiExcelLiquidazioneAVB.DISIMPEGNO_IMPORTO) : null, sheetHssf != null ? sheetHssf.getRow(ConstantsCampiExcelLiquidazioneAVB.DISIMPEGNO_IMPORTO) : null, indiceColonna, nomeSezione);
			if (StringUtils.isNotBlank(valueCell)) {
				disimpegno.setDisimpegnoImporto(valueCell);
			} else {
				isObbligatorioNull = true;
			}

			disimpegno.setDisimpegnoImpegnoAnno(getValueCell(ConstantsCampiExcelLiquidazioneAVB.DISIMPEGNO_IMPEGNO_ANNO, formulaEvaluator, sheet != null ? sheet.getRow(ConstantsCampiExcelLiquidazioneAVB.DISIMPEGNO_IMPEGNO_ANNO) : null, sheetHssf != null ? sheetHssf.getRow(ConstantsCampiExcelLiquidazioneAVB.DISIMPEGNO_IMPEGNO_ANNO) : null, indiceColonna, nomeSezione));
			disimpegno.setDisimpegnoSub(getValueCell(ConstantsCampiExcelLiquidazioneAVB.DISIMPEGNO_SUB, formulaEvaluator, sheet != null ? sheet.getRow(ConstantsCampiExcelLiquidazioneAVB.DISIMPEGNO_SUB) : null, sheetHssf != null ? sheetHssf.getRow(ConstantsCampiExcelLiquidazioneAVB.DISIMPEGNO_SUB) : null, indiceColonna, nomeSezione));
			disimpegno.setDisimpegnoCapitolo(getValueCell(ConstantsCampiExcelLiquidazioneAVB.DISIMPEGNO_CAPITOLO, formulaEvaluator, sheet != null ? sheet.getRow(ConstantsCampiExcelLiquidazioneAVB.DISIMPEGNO_CAPITOLO) : null, sheetHssf != null ? sheetHssf.getRow(ConstantsCampiExcelLiquidazioneAVB.DISIMPEGNO_CAPITOLO) : null, indiceColonna, nomeSezione));
			
			if (checkAllColumnNull(disimpegno) && isObbligatorioNull) {
				break;
			} else if (!checkAllColumnNull(disimpegno) && isObbligatorioNull){
				throw new ValidatorException("Il dato \"IMPORTO\" della sezione " + nomeSezione + ", in riga " + (ConstantsCampiExcelLiquidazioneAVB.DISIMPEGNO_IMPORTO+1) + " e colonna " + (indiceColonna+1) + ", è obbligatorio");
			} else {
				listaDisimpegni.add(disimpegno);
			}

		}
		liquidazioneBean.setListaDisimpegni(listaDisimpegni);
	}

	private void manageCampiLiquidazioniCompensiProgettualita(DatiLiquidazioneAVBBean liquidazioneBean, int campo, FormulaEvaluator formulaEvaluator, XSSFRow row, HSSFRow rowHssf) throws Exception {
		String valueCell;
		String nomeSezione = "Liquidazioni di compensi legati a progettualità";

		switch (campo) {

		case ConstantsCampiExcelLiquidazioneAVB.LIQUIDAZIONICOMPENSIPROGETTUALITA_FLG_LIQ:
			valueCell = getStandardValueCell(campo, formulaEvaluator, row, rowHssf, nomeSezione);
			if (StringUtils.isNotBlank(valueCell)) {
				liquidazioneBean.setLiquidazioneCompensiProgettualitaFlgLiq(valueCell);
			}
			break;
		case ConstantsCampiExcelLiquidazioneAVB.LIQUIDAZIONICOMPENSIPROGETTUALITA_FLG_VER_ODV:
			valueCell = getStandardValueCell(campo, formulaEvaluator, row, rowHssf, nomeSezione);
			if (StringUtils.isNotBlank(valueCell)) {
				liquidazioneBean.setLiquidazioneCompensiProgettualitaFlgVerOdv(valueCell);
			} else {
				throw new ValidatorException("Il dato Verifica dall'O.d.V. dei livelli di performance raggiunti, della sezione " + nomeSezione + ", in riga " + (campo+1) + " e colonna " + (INDICE_COLONNA_VALORE_RIGA+1) + ", è obbligatorio");
			}
			break;
		}
	}

	private void manageCampiSpesaTitoloBilancio(DatiLiquidazioneAVBBean liquidazioneBean, int campo, FormulaEvaluator formulaEvaluator, XSSFRow row, HSSFRow rowHssf) throws Exception {
		String valueCell;
		String nomeSezione = "Liquidazione Spesa sul Titolo 2° del bilancio";

		switch (campo) {

		case ConstantsCampiExcelLiquidazioneAVB.SPESATITOLOBILANCIO_TIPO_FINANZIAENTO:
			valueCell = getStandardValueCell(campo, formulaEvaluator, row, rowHssf, nomeSezione );
			if (StringUtils.isNotBlank(valueCell)) {
				liquidazioneBean.setSpesaTitoloBilancioTipoFinanziamento(valueCell);
			}
			break;
		case ConstantsCampiExcelLiquidazioneAVB.SPESATITOLOBILANCIO_BUONO_CARICO:
			valueCell = getStandardValueCell(campo, formulaEvaluator, row, rowHssf, nomeSezione );
			if (StringUtils.isNotBlank(valueCell)) {
				liquidazioneBean.setSpesaTitoloBilancioBuonoCarico(valueCell);
			} 
			break;
		case ConstantsCampiExcelLiquidazioneAVB.SPESATITOLOBILANCIO_RITENUTA:
			valueCell = getStandardValueCell(campo, formulaEvaluator, row, rowHssf, nomeSezione );
			if (StringUtils.isNotBlank(valueCell)) {
				liquidazioneBean.setSpesaTitoloBilancioRitenuta(valueCell);
			}
			break;
		case ConstantsCampiExcelLiquidazioneAVB.SPESATITOLOBILANCIO_SVINCOLO:
			valueCell = getStandardValueCell(campo, formulaEvaluator, row, rowHssf, nomeSezione );
			if (StringUtils.isNotBlank(valueCell)) {
				liquidazioneBean.setSpesaTitoloBilancioSvincolo(valueCell);
			}
			break;
		case ConstantsCampiExcelLiquidazioneAVB.SPESATITOLOBILANCIO_FLG_EFF_PATR_IMM_ENTE:
			valueCell = getStandardValueCell(campo, formulaEvaluator, row, rowHssf, nomeSezione );
			if (StringUtils.isNotBlank(valueCell)) {
				liquidazioneBean.setSpesaTitoloBilancioFlgEffPatrImmEnte(valueCell);
			} else {
				throw new ValidatorException("Il dato Spesa che esplica effetti sul Patrimonio Immobiliare dell'Ente, della sezione " + nomeSezione + ", in riga " + (campo+1) + " e colonna " + (INDICE_COLONNA_VALORE_RIGA+1) + ", è obbligatorio");
			}
			break;
		case ConstantsCampiExcelLiquidazioneAVB.SPESATITOLOBILANCIO_CERT_PAG:
			valueCell = getStandardValueCell(campo, formulaEvaluator, row, rowHssf, nomeSezione );
			if (StringUtils.isNotBlank(valueCell)) {
				liquidazioneBean.setSpesaTitoloBilancioCertPag(valueCell);
			} 
			break;
		case ConstantsCampiExcelLiquidazioneAVB.SPESATITOLOBILANCIO_ALTRO:
			valueCell = getStandardValueCell(campo, formulaEvaluator, row, rowHssf, nomeSezione );
			if (StringUtils.isNotBlank(valueCell)) {
				liquidazioneBean.setSpesaTitoloBilancioAltro(valueCell);
			} 
			break;
		}

	}

	private void manageCampiBeneficiarioPagamento(DatiLiquidazioneAVBBean liquidazioneBean, int campo, FormulaEvaluator formulaEvaluator, XSSFRow row, HSSFRow rowHssf, XSSFSheet sheet, HSSFSheet sheetHssf) throws Exception {
		String valueCell;
		String nomeSezione = "Beneficiario\\Pagamento";

		switch (campo) {

		case ConstantsCampiExcelLiquidazioneAVB.BENEFICIARIOPAGAMENTO_BENEFICIARIO:
			valueCell = getStandardValueCell(campo, formulaEvaluator, row, rowHssf, nomeSezione);
			if (StringUtils.isNotBlank(valueCell)) {
				liquidazioneBean.setBeneficiarioPagamentoBeneficiario(valueCell);
			}else {
				throw new ValidatorException("Il dato Beneficiario della sezione " + nomeSezione + ", in riga " + (campo+1) + " e colonna " + (INDICE_COLONNA_VALORE_RIGA+1) + ", è obbligatorio");
			}
			break;
		case ConstantsCampiExcelLiquidazioneAVB.BENEFICIARIOPAGAMENTO_SEDE:
			valueCell = getStandardValueCell(campo, formulaEvaluator, row, rowHssf, nomeSezione);
			if (StringUtils.isNotBlank(valueCell)) {
				liquidazioneBean.setBeneficiarioPagamentoSede(valueCell);
			}else {
				throw new ValidatorException("Il dato Sede della sezione " + nomeSezione + ", in riga " + (campo+1) + " e colonna " + (INDICE_COLONNA_VALORE_RIGA+1) + ", è obbligatorio");
			}
			break;
		case ConstantsCampiExcelLiquidazioneAVB.BENEFICIARIOPAGAMENTO_IVA:
			valueCell = getStandardValueCell(campo, formulaEvaluator, row, rowHssf, nomeSezione);
			if (StringUtils.isNotBlank(valueCell)) {
				if (sheet!=null) {
					if (StringUtils.isBlank(
							getStandardValueCell(ConstantsCampiExcelLiquidazioneAVB.BENEFICIARIOPAGAMENTO_COD_FISCALE,
									formulaEvaluator, sheet.getRow(ConstantsCampiExcelLiquidazioneAVB.BENEFICIARIOPAGAMENTO_COD_FISCALE), null, nomeSezione))) {
						liquidazioneBean.setBeneficiarioPagamentoIva(valueCell);
					} else {
						throw new ValidatorException(
								"Solo uno tra i dati \"Partita Iva\" o \"Codice fiscale\" della sezione " + nomeSezione + " deve essere obbligatorio");
					}
				} else if (sheetHssf!=null) {
					if (StringUtils.isBlank(
							getStandardValueCell(ConstantsCampiExcelLiquidazioneAVB.BENEFICIARIOPAGAMENTO_COD_FISCALE,
									formulaEvaluator, null, sheetHssf.getRow(ConstantsCampiExcelLiquidazioneAVB.BENEFICIARIOPAGAMENTO_COD_FISCALE), nomeSezione))) {
						liquidazioneBean.setBeneficiarioPagamentoIva(valueCell);
					} else {
						throw new ValidatorException(
								"Solo uno tra i dati \"Partita Iva\" o \"Codice fiscale\" della sezione " + nomeSezione + " deve essere obbligatorio");
					} 
				}
			} else {
				if (sheet!=null) {
					if (StringUtils.isBlank(
							getStandardValueCell(ConstantsCampiExcelLiquidazioneAVB.BENEFICIARIOPAGAMENTO_COD_FISCALE,
									formulaEvaluator, sheet.getRow(ConstantsCampiExcelLiquidazioneAVB.BENEFICIARIOPAGAMENTO_COD_FISCALE), null, nomeSezione))) {
						throw new ValidatorException(
								"Uno tra i dati \"Partita Iva\" o \"Codice fiscale\" della sezione " + nomeSezione + ", in riga " + (campo+1) + " e colonna " + (INDICE_COLONNA_VALORE_RIGA+1) + ", è obbligatorio");
					} else {
						liquidazioneBean.setBeneficiarioPagamentoCodFiscale(getStandardValueCell(ConstantsCampiExcelLiquidazioneAVB.BENEFICIARIOPAGAMENTO_COD_FISCALE,
								formulaEvaluator, sheet.getRow(ConstantsCampiExcelLiquidazioneAVB.BENEFICIARIOPAGAMENTO_COD_FISCALE), null, nomeSezione));
					}
				} else if (sheetHssf!=null) {
					if (StringUtils.isBlank(
							getStandardValueCell(ConstantsCampiExcelLiquidazioneAVB.BENEFICIARIOPAGAMENTO_COD_FISCALE,
									formulaEvaluator, null, sheetHssf.getRow(ConstantsCampiExcelLiquidazioneAVB.BENEFICIARIOPAGAMENTO_COD_FISCALE), nomeSezione))) {
						throw new ValidatorException(
								"Uno tra i dati \"Partita Iva\" o \"Codice fiscale\" della sezione " + nomeSezione + ", in riga " + (campo+1) + " e colonna " + (INDICE_COLONNA_VALORE_RIGA+1) + ", è obbligatorio");
					} else {
						liquidazioneBean.setBeneficiarioPagamentoCodFiscale(getStandardValueCell(ConstantsCampiExcelLiquidazioneAVB.BENEFICIARIOPAGAMENTO_COD_FISCALE,
								formulaEvaluator, null, sheetHssf.getRow(ConstantsCampiExcelLiquidazioneAVB.BENEFICIARIOPAGAMENTO_COD_FISCALE), nomeSezione));
					} 
				}
			}
			break;
//		case ConstantsCampiExcelLiquidazioneAVB.BENEFICIARIOPAGAMENTO_COD_FISCALE:
//			valueCell = getStandardValueCell(campo, formulaEvaluator, row, rowHssf, nomeSezione);
//			if (StringUtils.isNotBlank(valueCell)) {
//				liquidazioneBean.setBeneficiarioPagamentoCodFiscale(valueCell);
//			} else {
//				if (sheet!=null) {
//					if (StringUtils.isBlank(getStandardValueCell(ConstantsCampiExcelLiquidazioneAVB.BENEFICIARIOPAGAMENTO_IVA,
//									formulaEvaluator, sheet.getRow(ConstantsCampiExcelLiquidazioneAVB.BENEFICIARIOPAGAMENTO_IVA), null, nomeSezione))) {
//						throw new ValidatorException(
//								"Uno tra i dati \"Partita Iva\" o \"Codice fiscale\" della sezione " + nomeSezione + ", in riga " + (campo+1) + " e colonna " + (INDICE_COLONNA_VALORE_RIGA+1) + ", è obbligatorio");
//					} 
//				} else if (sheetHssf!=null) {
//					if (StringUtils.isBlank(getStandardValueCell(ConstantsCampiExcelLiquidazioneAVB.BENEFICIARIOPAGAMENTO_IVA,
//							formulaEvaluator, null, sheetHssf.getRow(ConstantsCampiExcelLiquidazioneAVB.BENEFICIARIOPAGAMENTO_IVA), nomeSezione))) {
//				throw new ValidatorException(
//						"Uno tra i dati \"Partita Iva\" o \"Codice fiscale\" della sezione " + nomeSezione + ", in riga " + (campo+1) + " e colonna " + (INDICE_COLONNA_VALORE_RIGA+1) + ", è obbligatorio");
//			} 
//				}
//			}
//			break;
		case ConstantsCampiExcelLiquidazioneAVB.BENEFICIARIOPAGAMENTO_FLG_CREDITO_CERTIFICATO:
			valueCell = getStandardValueCell(campo, formulaEvaluator, row, rowHssf, nomeSezione);
			if (StringUtils.isNotBlank(valueCell)) {
				liquidazioneBean.setBeneficiarioPagamentoFlgCreditoCertificato(valueCell);
			} else {
				throw new ValidatorException("Il dato Credito certificato della sezione " + nomeSezione + ", in riga " + (campo+1) + " e colonna " + (INDICE_COLONNA_VALORE_RIGA+1) + ", è obbligatorio");
			}
			break;
		case ConstantsCampiExcelLiquidazioneAVB.BENEFICIARIOPAGAMENTO_FLG_CREDITO_CEDUTO:
			valueCell = getStandardValueCell(campo, formulaEvaluator, row, rowHssf, nomeSezione);
			if (StringUtils.isNotBlank(valueCell)) {
				liquidazioneBean.setBeneficiarioPagamentoFlgCreditoCeduto(valueCell);
			} else {
				throw new ValidatorException("Il dato Credito ceduto della sezione " + nomeSezione + ", in riga " + (campo+1) + " e colonna " + (INDICE_COLONNA_VALORE_RIGA+1) + ", è obbligatorio");
			}
			break;
		case ConstantsCampiExcelLiquidazioneAVB.BENEFICIARIOPAGAMENTO_SOGGETTO_QUIETANZA:
			valueCell = getStandardValueCell(campo, formulaEvaluator, row, rowHssf, nomeSezione);
			if (StringUtils.isNotBlank(valueCell)) {
				liquidazioneBean.setBeneficiarioPagamentoSoggettoQuietanza(valueCell);
			}
			break;
		case ConstantsCampiExcelLiquidazioneAVB.BENEFICIARIOPAGAMENTO_DATI_DOC_DEBITO:
			valueCell = getStandardValueCell(campo, formulaEvaluator, row, rowHssf, nomeSezione);
			if (StringUtils.isNotBlank(valueCell)) {
				liquidazioneBean.setBeneficiarioPagamentoDatiDocDebito(valueCell);
			} else {
				throw new ValidatorException("Il dato Dati del documento di debito, della sezione " + nomeSezione + ", in riga " + (campo+1) + " e colonna " + (INDICE_COLONNA_VALORE_RIGA+1) + ", è obbligatorio");
			}
			break;
		case ConstantsCampiExcelLiquidazioneAVB.BENEFICIARIOPAGAMENTO_NUMERO:
			valueCell = getStandardValueCell(campo, formulaEvaluator, row, rowHssf, nomeSezione);
			if (StringUtils.isNotBlank(valueCell)) {
				liquidazioneBean.setBeneficiarioPagamentoNumero(valueCell);
			} else {
				throw new ValidatorException("Il dato Numero della sezione " + nomeSezione + ", in riga " + (campo+1) + " e colonna " + (INDICE_COLONNA_VALORE_RIGA+1) + ", è obbligatorio");
			}
			break;
		case ConstantsCampiExcelLiquidazioneAVB.BENEFICIARIOPAGAMENTO_DATA:
			valueCell = getStandardValueCell(campo, formulaEvaluator, row, rowHssf, nomeSezione);
			if (StringUtils.isNotBlank(valueCell)) {
				liquidazioneBean.setBeneficiarioPagamentoData(valueCell);
			} else {
				throw new ValidatorException("Il dato Data della sezione " + nomeSezione + ", in riga " + (campo+1) + " e colonna " + (INDICE_COLONNA_VALORE_RIGA+1) + ", è obbligatorio");
			}
			break;
		case ConstantsCampiExcelLiquidazioneAVB.BENEFICIARIOPAGAMENTO_IMPORTO:
			valueCell = getStandardValueCell(campo, formulaEvaluator, row, rowHssf, nomeSezione);
			if (StringUtils.isNotBlank(valueCell)) {
				liquidazioneBean.setBeneficiarioPagamentoImporto(valueCell);
			} else {
				throw new ValidatorException("Il dato Importo della sezione " + nomeSezione + ", in riga " + (campo+1) + " e colonna " + (INDICE_COLONNA_VALORE_RIGA+1) + ", è obbligatorio");
			}
			break;
		case ConstantsCampiExcelLiquidazioneAVB.BENEFICIARIOPAGAMENTO_IMPONIBILE:
			valueCell = getStandardValueCell(campo, formulaEvaluator, row, rowHssf, nomeSezione);
			if (StringUtils.isNotBlank(valueCell)) {
				liquidazioneBean.setBeneficiarioPagamentoImponibile(valueCell);
			} else {
				throw new ValidatorException("Il dato Imponibile della sezione " + nomeSezione + ", in riga " + (campo+1) + " e colonna " + (INDICE_COLONNA_VALORE_RIGA+1) + ", è obbligatorio");
			}
			break;
		case ConstantsCampiExcelLiquidazioneAVB.BENEFICIARIOPAGAMENTO_IVA_IMPORTO:
			valueCell = getStandardValueCell(campo, formulaEvaluator, row, rowHssf, nomeSezione);
			if (StringUtils.isNotBlank(valueCell)) {
				liquidazioneBean.setBeneficiarioPagamentoIvaImporto(valueCell);
			} else {
				throw new ValidatorException("Il dato Iva della sezione " + nomeSezione + ", in riga " + (campo+1) + " e colonna " + (INDICE_COLONNA_VALORE_RIGA+1) + ", è obbligatorio");
			}
			break;
		case ConstantsCampiExcelLiquidazioneAVB.BENEFICIARIOPAGAMENTO_FLG_RITENUTA_FISCALE:
			valueCell = getStandardValueCell(campo, formulaEvaluator, row, rowHssf, nomeSezione);
			if (StringUtils.isNotBlank(valueCell)) {
				liquidazioneBean.setBeneficiarioPagamentoFlgRitenutaFiscale(valueCell);
			}else {
				throw new ValidatorException("Il dato Ritenuta Fiscale della sezione " + nomeSezione + ", in riga " + (campo+1) + " e colonna " + (INDICE_COLONNA_VALORE_RIGA+1) + ", è obbligatorio");
			}
			break;
		case ConstantsCampiExcelLiquidazioneAVB.BENEFICIARIOPAGAMENTO_IMPORTO_RITENUTA_FISCALE:
			valueCell = getStandardValueCell(campo, formulaEvaluator, row, rowHssf, nomeSezione);
			if (StringUtils.isNotBlank(valueCell)) {
				liquidazioneBean.setBeneficiarioPagamentoImportoRitenutaFiscale(valueCell);
			}else {
				if (sheet!=null) {
					if ("SI".equalsIgnoreCase(getStandardValueCell(ConstantsCampiExcelLiquidazioneAVB.BENEFICIARIOPAGAMENTO_FLG_RITENUTA_FISCALE,
							formulaEvaluator, sheet.getRow(ConstantsCampiExcelLiquidazioneAVB.BENEFICIARIOPAGAMENTO_FLG_RITENUTA_FISCALE), null, nomeSezione))) {
						throw new ValidatorException("Il dato Importo Fiscale della sezione " + nomeSezione + ", in riga " + (campo+1) + " e colonna " + (INDICE_COLONNA_VALORE_RIGA+1) + ", è obbligatorio");
					} 
				} else if (sheetHssf!=null) {
					if ("SI".equalsIgnoreCase(getStandardValueCell(ConstantsCampiExcelLiquidazioneAVB.BENEFICIARIOPAGAMENTO_FLG_RITENUTA_FISCALE,
							formulaEvaluator, null, sheetHssf.getRow(ConstantsCampiExcelLiquidazioneAVB.BENEFICIARIOPAGAMENTO_FLG_RITENUTA_FISCALE), nomeSezione))) {
						throw new ValidatorException("Il dato Importo Fiscale della sezione " + nomeSezione + ", in riga " + (campo+1) + " e colonna " + (INDICE_COLONNA_VALORE_RIGA+1) + ", è obbligatorio");
					} 
				}
			}
			break;
		case ConstantsCampiExcelLiquidazioneAVB.BENEFICIARIOPAGAMENTO_FLG_REGOLARITA:
			valueCell = getStandardValueCell(campo, formulaEvaluator, row, rowHssf, nomeSezione);
			if (StringUtils.isNotBlank(valueCell)) {
				liquidazioneBean.setBeneficiarioPagamentoFlgRegolarita(valueCell);
			} else {
				throw new ValidatorException("Il dato regolarità della fornitura/prestazione e del documento giustificativo/addebito della sezione " + nomeSezione + ", in riga " + (campo+1) + " e colonna " + (INDICE_COLONNA_VALORE_RIGA+1) + ", è obbligatorio");
			}
			break;
		case ConstantsCampiExcelLiquidazioneAVB.BENEFICIARIOPAGAMENTO_MODALITA_PAGAMENTO:
			valueCell = getStandardValueCell(campo, formulaEvaluator, row, rowHssf, nomeSezione);
			if (StringUtils.isNotBlank(valueCell)) {
				liquidazioneBean.setBeneficiarioPagamentoModalitaPagamento(valueCell);
			} else {
				throw new ValidatorException("Il dato Modalità di pagamento della sezione " + nomeSezione + ", in riga " + (campo+1) + " e colonna " + (INDICE_COLONNA_VALORE_RIGA+1) + ", è obbligatorio");
			}
			break;
		case ConstantsCampiExcelLiquidazioneAVB.BENEFICIARIOPAGAMENTO_BANCA:
			valueCell = getStandardValueCell(campo, formulaEvaluator, row, rowHssf, nomeSezione);
			if (StringUtils.isNotBlank(valueCell)) {
				liquidazioneBean.setBeneficiarioPagamentoBanca(valueCell);
			} else {
				throw new ValidatorException("Il dato Banca della sezione " + nomeSezione + ", in riga " + (campo+1) + " e colonna " + (INDICE_COLONNA_VALORE_RIGA+1) + ", è obbligatorio");
			}
			break;
		case ConstantsCampiExcelLiquidazioneAVB.BENEFICIARIOPAGAMENTO_IBAN:
			valueCell = getStandardValueCell(campo, formulaEvaluator, row, rowHssf, nomeSezione);
			if (StringUtils.isNotBlank(valueCell)) {
				liquidazioneBean.setBeneficiarioPagamentoIban(valueCell);
			}
			break;
		case ConstantsCampiExcelLiquidazioneAVB.BENEFICIARIOPAGAMENTO_RIF_VERBALE_ECONOMO:
			valueCell = getStandardValueCell(campo, formulaEvaluator, row, rowHssf, nomeSezione);
			if (StringUtils.isNotBlank(valueCell)) {
				liquidazioneBean.setBeneficiarioPagamentoRifVerbaleEconomo(valueCell);
			}
			break;
		case ConstantsCampiExcelLiquidazioneAVB.BENEFICIARIOPAGAMENTO_NOTA_CREDITO:
			valueCell = getStandardValueCell(campo, formulaEvaluator, row, rowHssf, nomeSezione);
			if (StringUtils.isNotBlank(valueCell)) {
				liquidazioneBean.setBeneficiarioPagamentoPresenteNotaCredito(valueCell);
			} else {
				throw new ValidatorException("Il dato Nota di credito della sezione " + nomeSezione + ", in riga " + (campo+1) + " e colonna " + (INDICE_COLONNA_VALORE_RIGA+1) + ", è obbligatorio");
			}
			break;
		case ConstantsCampiExcelLiquidazioneAVB.BENEFICIARIOPAGAMENTO_TESTO_NOTA_CREDITO:
			valueCell = getStandardValueCell(campo, formulaEvaluator, row, rowHssf, nomeSezione);
			if (StringUtils.isNotBlank(valueCell)) {
				if (sheet!=null) {
					if ("SI".equalsIgnoreCase(getStandardValueCell(ConstantsCampiExcelLiquidazioneAVB.BENEFICIARIOPAGAMENTO_NOTA_CREDITO,
							formulaEvaluator, sheet.getRow(ConstantsCampiExcelLiquidazioneAVB.BENEFICIARIOPAGAMENTO_NOTA_CREDITO), null, nomeSezione))) {
						liquidazioneBean.setBeneficiarioPagamentoTestoNotaCredito(valueCell);
					} 
				} else if (sheetHssf!=null) {
					if ("SI".equalsIgnoreCase(getStandardValueCell(ConstantsCampiExcelLiquidazioneAVB.BENEFICIARIOPAGAMENTO_NOTA_CREDITO,
							formulaEvaluator, null, sheetHssf.getRow(ConstantsCampiExcelLiquidazioneAVB.BENEFICIARIOPAGAMENTO_NOTA_CREDITO), nomeSezione))) {
						liquidazioneBean.setBeneficiarioPagamentoTestoNotaCredito(valueCell);
					} 
				}
			}
			break;
		}
		liquidazioneBean.setAttoImpegno(liquidazioneBean.getOggettoSpesaNumDetImpegno()!=null && !"".equals(liquidazioneBean.getOggettoSpesaNumDetImpegno())  ? liquidazioneBean.getOggettoSpesaNumDetImpegno() : liquidazioneBean.getOggettoSpesaAltroAtto());
		liquidazioneBean.setBeneficiarioSede(liquidazioneBean.getBeneficiarioPagamentoBeneficiario());
		String documentoDiDebito = liquidazioneBean.getBeneficiarioPagamentoDatiDocDebito() + " " + liquidazioneBean.getBeneficiarioPagamentoNumero() + " del " + liquidazioneBean.getBeneficiarioPagamentoData();
		liquidazioneBean.setDocumentoDiDebito(documentoDiDebito);
	}

	private void manageCampiOggettoDellaSpesa(DatiLiquidazioneAVBBean liquidazioneBean, int campo, FormulaEvaluator formulaEvaluator, XSSFRow row, HSSFRow rowHssf, XSSFSheet sheet, HSSFSheet sheetHssf) throws Exception {
		String valueCell;
		String nomeSezione = "Oggetto della spesa";

		switch (campo) {

		case ConstantsCampiExcelLiquidazioneAVB.OGGETTOSPESA_NUM_DET_IMPEGNO:
			valueCell = getStandardValueCell(campo, formulaEvaluator, row, rowHssf, nomeSezione);
			if (StringUtils.isNotBlank(valueCell)) {
				if (sheet!=null) {
					if (StringUtils.isBlank(getStandardValueCell(ConstantsCampiExcelLiquidazioneAVB.OGGETTOSPESA_ALTRO_ATTO,
							formulaEvaluator, sheet.getRow(ConstantsCampiExcelLiquidazioneAVB.OGGETTOSPESA_ALTRO_ATTO), null, nomeSezione))) {
						liquidazioneBean.setOggettoSpesaNumDetImpegno(valueCell);
					} else {
						throw new ValidatorException(
								"Solo uno tra i dati \"N. determina impegno\" o \"Altro atto\" della sezione " + nomeSezione + " deve essere obbligatorio");
					}
				} else if (sheetHssf!=null) {
					if (StringUtils.isBlank(getStandardValueCell(ConstantsCampiExcelLiquidazioneAVB.OGGETTOSPESA_ALTRO_ATTO,
							formulaEvaluator, null, sheetHssf.getRow(ConstantsCampiExcelLiquidazioneAVB.OGGETTOSPESA_ALTRO_ATTO), nomeSezione))) {
						liquidazioneBean.setOggettoSpesaNumDetImpegno(valueCell);
					} else {
						throw new ValidatorException(
								"Solo uno tra i dati \"N. determina impegno\" o \"Altro atto\" della sezione " + nomeSezione + " deve essere obbligatorio");
					}
				} 
			} else {
				if (sheet!=null) {
					if (StringUtils.isBlank(getStandardValueCell(ConstantsCampiExcelLiquidazioneAVB.OGGETTOSPESA_ALTRO_ATTO,
							formulaEvaluator, sheet.getRow(ConstantsCampiExcelLiquidazioneAVB.OGGETTOSPESA_ALTRO_ATTO), null, nomeSezione))) {
						throw new ValidatorException(
								"Uno tra i dati \"N. determina impegno\" o \"Altro atto\" della sezione " + nomeSezione + ", in riga " + (campo+1) + " e colonna " + (INDICE_COLONNA_VALORE_RIGA+1) + ", deve essere obbligatorio");
					} else {
						liquidazioneBean.setOggettoSpesaAltroAtto(getStandardValueCell(ConstantsCampiExcelLiquidazioneAVB.OGGETTOSPESA_ALTRO_ATTO,
								formulaEvaluator, sheet.getRow(ConstantsCampiExcelLiquidazioneAVB.OGGETTOSPESA_ALTRO_ATTO), null, nomeSezione));
					}
				} else if (sheetHssf!=null) {
					if (StringUtils.isBlank(getStandardValueCell(ConstantsCampiExcelLiquidazioneAVB.OGGETTOSPESA_ALTRO_ATTO,
							formulaEvaluator, null, sheetHssf.getRow(ConstantsCampiExcelLiquidazioneAVB.OGGETTOSPESA_ALTRO_ATTO), nomeSezione))) {
						throw new ValidatorException(
								"Uno tra i dati \"N. determina impegno\" o \"Altro atto\" della sezione " + nomeSezione + ", in riga " + (campo+1) + " e colonna " + (INDICE_COLONNA_VALORE_RIGA+1) + ", deve essere obbligatorio");
					} else {
						liquidazioneBean.setOggettoSpesaAltroAtto(getStandardValueCell(ConstantsCampiExcelLiquidazioneAVB.OGGETTOSPESA_ALTRO_ATTO,
								formulaEvaluator, null, sheetHssf.getRow(ConstantsCampiExcelLiquidazioneAVB.OGGETTOSPESA_ALTRO_ATTO), nomeSezione));
					}
				}
			}
			break;
//		case ConstantsCampiExcelLiquidazioneAVB.OGGETTOSPESA_ALTRO_ATTO:
//			valueCell = getStandardValueCell(campo, formulaEvaluator, row, rowHssf, nomeSezione);
//			if (StringUtils.isNotBlank(valueCell)) {
//				liquidazioneBean.setOggettoSpesaAltroAtto(valueCell);
//			} else {
//				if (sheet!=null) {
//					if (StringUtils.isBlank(getStandardValueCell(ConstantsCampiExcelLiquidazioneAVB.OGGETTOSPESA_NUM_DET_IMPEGNO,
//							formulaEvaluator, sheet.getRow(ConstantsCampiExcelLiquidazioneAVB.OGGETTOSPESA_NUM_DET_IMPEGNO), null, nomeSezione))) {
//						throw new ValidatorException(
//								"Uno tra i dati \"N. determina impegno\" o \"Altro atto\" della sezione " + nomeSezione + ", in riga " + (campo+1) + " e colonna " + (INDICE_COLONNA_VALORE_RIGA+1) + ", deve essere obbligatorio");
//					} 
//				} else if (sheetHssf!=null) {
//					if (StringUtils.isBlank(getStandardValueCell(ConstantsCampiExcelLiquidazioneAVB.OGGETTOSPESA_NUM_DET_IMPEGNO,
//							formulaEvaluator, null, sheetHssf.getRow(ConstantsCampiExcelLiquidazioneAVB.OGGETTOSPESA_NUM_DET_IMPEGNO), nomeSezione))) {
//						throw new ValidatorException(
//								"Uno tra i dati \"N. determina impegno\" o \"Altro atto\" della sezione " + nomeSezione + ", in riga " + (campo+1) + " e colonna " + (INDICE_COLONNA_VALORE_RIGA+1) + ", deve essere obbligatorio");
//					} 
//				}
//			}
//			break;
		case ConstantsCampiExcelLiquidazioneAVB.OGGETTOSPESA_FLG_ESECUTIVO:
			valueCell = getStandardValueCell(campo, formulaEvaluator, row, rowHssf, nomeSezione);
			if (StringUtils.isNotBlank(valueCell)) {
				liquidazioneBean.setOggettoSpesaFlgEsecutivo(valueCell);
			} else {
				throw new ValidatorException("Il dato Esecutivo della sezione " + nomeSezione + ", in riga " + (campo+1) + " e colonna " + (INDICE_COLONNA_VALORE_RIGA+1) + ", è obbligatorio");
			}
			break;
		case ConstantsCampiExcelLiquidazioneAVB.OGGETTOSPESA_OGGETTO:
			valueCell = getStandardValueCell(campo, formulaEvaluator, row, rowHssf, nomeSezione);
			if (StringUtils.isNotBlank(valueCell)) {
				liquidazioneBean.setOggettoSpesaOggetto(valueCell);
			} else {
				throw new ValidatorException("Il dato Oggetto della sezione " + nomeSezione + ", in riga " + (campo+1) + " e colonna " + (INDICE_COLONNA_VALORE_RIGA+1) + ", è obbligatorio");
			}
			break;
		case ConstantsCampiExcelLiquidazioneAVB.OGGETTOSPESA_FLG_CONFORME_REG_CONT:
			valueCell = getStandardValueCell(campo, formulaEvaluator, row, rowHssf, nomeSezione);
			if (StringUtils.isNotBlank(valueCell)) {
				liquidazioneBean.setOggettoSpesaFlgConformeRegCont(valueCell);
			} else {
				throw new ValidatorException("Il dato Conforme al regolamento di contabilita della sezione " + nomeSezione + ", in riga " + (campo+1) + " e colonna " + (INDICE_COLONNA_VALORE_RIGA+1) + ", è obbligatorio");
			}
			break;
		case ConstantsCampiExcelLiquidazioneAVB.OGGETTOSPESA_FLG_DEBITO_COMMERCIALE:
			valueCell = getStandardValueCell(campo, formulaEvaluator, row, rowHssf, nomeSezione);
			if (StringUtils.isNotBlank(valueCell)) {
				liquidazioneBean.setOggettoSpesaFlgDebitoCommerciale(valueCell);
			} else {
				throw new ValidatorException("Il dato Debito commerciale della sezione " + nomeSezione + ", in riga " + (campo+1) + " e colonna " + (INDICE_COLONNA_VALORE_RIGA+1) + ", è obbligatorio");
			}
			break;
		case ConstantsCampiExcelLiquidazioneAVB.OGGETTOSPESA_FLG_PAGAMENTO_SOGG_ART_22_DLGS_33_2013:
			valueCell = getStandardValueCell(campo, formulaEvaluator, row, rowHssf, nomeSezione);
			if (StringUtils.isNotBlank(valueCell)) {
				liquidazioneBean.setOggettoSpesaFlgPagamentoSoggArt22Dlgs33_2013(valueCell);
			} else {
				throw new ValidatorException("Il dato Pagamenti a soggetti art.22 della sezione " + nomeSezione + ", in riga " + (campo+1) + " e colonna " + (INDICE_COLONNA_VALORE_RIGA+1) + ", è obbligatorio");
			}
			break;
		case ConstantsCampiExcelLiquidazioneAVB.OGGETTOSPESA_FLG_VERIFICA_ANAC:
			valueCell = getStandardValueCell(campo, formulaEvaluator, row, rowHssf, nomeSezione);
			if (StringUtils.isNotBlank(valueCell)) {
				liquidazioneBean.setOggettoSpesaFlgVerificaAnac(valueCell);
			} else {
				if (sheet!=null) {
					if ("SI".equalsIgnoreCase(getStandardValueCell(ConstantsCampiExcelLiquidazioneAVB.OGGETTOSPESA_FLG_PAGAMENTO_SOGG_ART_22_DLGS_33_2013,
							formulaEvaluator, sheet.getRow(ConstantsCampiExcelLiquidazioneAVB.OGGETTOSPESA_FLG_PAGAMENTO_SOGG_ART_22_DLGS_33_2013), null, nomeSezione))) {
						throw new ValidatorException(
								"Il dato Verifica rispetto cominucato ANAC della sezione " + nomeSezione + ", in riga " + (campo+1) + " e colonna " + (INDICE_COLONNA_VALORE_RIGA+1) + ", è obbligatorio");
					} 
				} else if (sheetHssf!=null) {
					if ("SI".equalsIgnoreCase(getStandardValueCell(ConstantsCampiExcelLiquidazioneAVB.OGGETTOSPESA_FLG_PAGAMENTO_SOGG_ART_22_DLGS_33_2013,
							formulaEvaluator, null, sheetHssf.getRow(ConstantsCampiExcelLiquidazioneAVB.OGGETTOSPESA_FLG_PAGAMENTO_SOGG_ART_22_DLGS_33_2013), nomeSezione))) {
						throw new ValidatorException(
								"Il dato Verifica rispetto cominucato ANAC della sezione " + nomeSezione + ", in riga " + (campo+1) + " e colonna " + (INDICE_COLONNA_VALORE_RIGA+1) + ", è obbligatorio");
					} 
				}
			}
			break;
		case ConstantsCampiExcelLiquidazioneAVB.OGGETTOSPESA_FLG_PAGAMENTO_SOGG_ART_1_CC_125_129_L_124_2017:
			valueCell = getStandardValueCell(campo, formulaEvaluator, row, rowHssf, nomeSezione);
			if (StringUtils.isNotBlank(valueCell)) {
				liquidazioneBean.setOggettoSpesaFlgPagamentoSoggArt1Cc125_129L124_2017(valueCell);
			} else {
				throw new ValidatorException("Il dato Pagamenti a soggetti art.1, cc. 125-129 della sezione " + nomeSezione + ", in riga " + (campo+1) + " e colonna " + (INDICE_COLONNA_VALORE_RIGA+1) + ", è obbligatorio");
			}
			break;
		case ConstantsCampiExcelLiquidazioneAVB.OGGETTOSPESA_FLG_SOGLIA_SUPERATA:
			valueCell = getStandardValueCell(campo, formulaEvaluator, row, rowHssf, nomeSezione);
			if (StringUtils.isNotBlank(valueCell)) {
				liquidazioneBean.setOggettoSpesaFlhSogliaSuperata(valueCell);
			} else {
				throw new ValidatorException("Il dato Obblighi di pubblicazione della sezione " + nomeSezione + ", in riga " + (campo+1) + " e colonna " + (INDICE_COLONNA_VALORE_RIGA+1) + ", è obbligatorio");
			}
			break;
		case ConstantsCampiExcelLiquidazioneAVB.OGGETTOSPESA_FLG_DEBITO_FUORI_BILANCIO:
			valueCell = getStandardValueCell(campo, formulaEvaluator, row, rowHssf, nomeSezione);
			if (StringUtils.isNotBlank(valueCell)) {
				liquidazioneBean.setOggettoSpesaFlgDebitoFuoriBilancio(valueCell);
			} else {
				throw new ValidatorException("Il dato Debito fuori bilancio della sezione " + nomeSezione + ", in riga " + (campo+1) + " e colonna " + (INDICE_COLONNA_VALORE_RIGA+1) + ", è obbligatorio");
			}
			break;			
		}

	}

	private String getValueCell(int campo, FormulaEvaluator formulaEvaluator, XSSFRow row, HSSFRow rowHssf, int indiceColonna, String nomeSezione) throws Exception {
		String nomeCampo = "";
		String valueCell = null;
		Cell cell = null;
		boolean isImporto = false;
		boolean isData = false;
		boolean isIntero = false;
		boolean isIva = false;
		boolean isSiNo = false;
		boolean isCodFiscale = false;
		boolean isIban = false;
		boolean isCig = false;
		boolean isSmartCig = false;
		boolean isCup = false;
		if (row != null) {
			cell = row.getCell(indiceColonna);
			if(row.getCell(INDICE_COLONNA_TIPO_RIGA).getStringCellValue()!=null && TIPO_IMPORTO.equals(row.getCell(INDICE_COLONNA_TIPO_RIGA).getStringCellValue())) {
				isImporto = true; 
			} else if (row.getCell(INDICE_COLONNA_TIPO_RIGA).getStringCellValue()!=null && TIPO_DATA.equals(row.getCell(INDICE_COLONNA_TIPO_RIGA).getStringCellValue())) {
				isData = true;
			} else if (row.getCell(INDICE_COLONNA_TIPO_RIGA).getStringCellValue()!=null && TIPO_INTERO.equals(row.getCell(INDICE_COLONNA_TIPO_RIGA).getStringCellValue())) {
				isIntero = true;
			} else if (row.getCell(INDICE_COLONNA_TIPO_RIGA).getStringCellValue()!=null && TIPO_IVA.equals(row.getCell(INDICE_COLONNA_TIPO_RIGA).getStringCellValue())) {
				isIva = true;
			} else if (row.getCell(INDICE_COLONNA_TIPO_RIGA).getStringCellValue()!=null && TIPO_COD_FISCALE.equals(row.getCell(INDICE_COLONNA_TIPO_RIGA).getStringCellValue())) {
				isCodFiscale = true;
			} else if (row.getCell(INDICE_COLONNA_TIPO_RIGA).getStringCellValue()!=null && TIPO_IBAN.equals(row.getCell(INDICE_COLONNA_TIPO_RIGA).getStringCellValue())) {
				isIban = true;
			} else if (row.getCell(INDICE_COLONNA_TIPO_RIGA).getStringCellValue()!=null && TIPO_CIG.equals(row.getCell(INDICE_COLONNA_TIPO_RIGA).getStringCellValue())) {
				isCig = true;
			} else if (row.getCell(INDICE_COLONNA_TIPO_RIGA).getStringCellValue()!=null && TIPO_SMART_CIG.equals(row.getCell(INDICE_COLONNA_TIPO_RIGA).getStringCellValue())) {
				isSmartCig = true;
			} else if (row.getCell(INDICE_COLONNA_TIPO_RIGA).getStringCellValue()!=null && TIPO_CUP.equals(row.getCell(INDICE_COLONNA_TIPO_RIGA).getStringCellValue())) {
				isCup = true;
			} else if (row.getCell(INDICE_COLONNA_TIPO_RIGA).getStringCellValue()!=null && TIPO_SI_NO.equals(row.getCell(INDICE_COLONNA_TIPO_RIGA).getStringCellValue())) {
				isSiNo = true;
			}
			nomeCampo = row.getCell(2).getStringCellValue();
		} else {
			cell = rowHssf.getCell(indiceColonna);
			if(rowHssf.getCell(INDICE_COLONNA_TIPO_RIGA).getStringCellValue()!=null && TIPO_IMPORTO.equals(rowHssf.getCell(INDICE_COLONNA_TIPO_RIGA).getStringCellValue())) {
				isImporto = true; 
			} else if (rowHssf.getCell(INDICE_COLONNA_TIPO_RIGA).getStringCellValue()!=null && TIPO_DATA.equals(rowHssf.getCell(INDICE_COLONNA_TIPO_RIGA).getStringCellValue())) {
				isData = true;
			} else if (rowHssf.getCell(INDICE_COLONNA_TIPO_RIGA).getStringCellValue()!=null && TIPO_INTERO.equals(rowHssf.getCell(INDICE_COLONNA_TIPO_RIGA).getStringCellValue())) {
				isIntero = true;
			} else if (rowHssf.getCell(INDICE_COLONNA_TIPO_RIGA).getStringCellValue()!=null && TIPO_IVA.equals(rowHssf.getCell(INDICE_COLONNA_TIPO_RIGA).getStringCellValue())) {
				isIva = true;
			} else if (rowHssf.getCell(INDICE_COLONNA_TIPO_RIGA).getStringCellValue()!=null && TIPO_COD_FISCALE.equals(rowHssf.getCell(INDICE_COLONNA_TIPO_RIGA).getStringCellValue())) {
				isCodFiscale = true;
			} else if (rowHssf.getCell(INDICE_COLONNA_TIPO_RIGA).getStringCellValue()!=null && TIPO_IBAN.equals(rowHssf.getCell(INDICE_COLONNA_TIPO_RIGA).getStringCellValue())) {
				isIban = true;
			} else if (rowHssf.getCell(INDICE_COLONNA_TIPO_RIGA).getStringCellValue()!=null && TIPO_CIG.equals(rowHssf.getCell(INDICE_COLONNA_TIPO_RIGA).getStringCellValue())) {
				isCig = true;
			} else if (rowHssf.getCell(INDICE_COLONNA_TIPO_RIGA).getStringCellValue()!=null && TIPO_SMART_CIG.equals(rowHssf.getCell(INDICE_COLONNA_TIPO_RIGA).getStringCellValue())) {
				isSmartCig = true;
			} else if (rowHssf.getCell(INDICE_COLONNA_TIPO_RIGA).getStringCellValue()!=null && TIPO_CUP.equals(rowHssf.getCell(INDICE_COLONNA_TIPO_RIGA).getStringCellValue())) {
				isCup = true;
			} else if (rowHssf.getCell(INDICE_COLONNA_TIPO_RIGA).getStringCellValue()!=null && TIPO_SI_NO.equals(rowHssf.getCell(INDICE_COLONNA_TIPO_RIGA).getStringCellValue())) {
				isSiNo = true;
			}
			nomeCampo = rowHssf.getCell(2).getStringCellValue();
		}

		if (cell != null && formulaEvaluator.evaluateInCell(cell).getCellType() != XSSFCell.CELL_TYPE_BLANK) {
			if (isImporto) {
				valueCell = normalizeImporto(formulaEvaluator, cell, campo+1, indiceColonna+1, nomeSezione, nomeCampo);
			} else if (isData) {
				valueCell = normalizeData(cell, campo+1, indiceColonna+1, nomeSezione, nomeCampo);
			} else if (isIntero) {
				valueCell = normalizeNumericoIntero(cell, campo+1, indiceColonna+1, nomeSezione, nomeCampo);
			} else if (isIva) {
				valueCell = checkPartitaIva(cell, campo+1, indiceColonna+1, formulaEvaluator.evaluateInCell(cell).getCellType()==XSSFCell.CELL_TYPE_NUMERIC, nomeSezione, nomeCampo);
			} else if (isSiNo) {
				valueCell = checkSiNo(cell, campo+1, indiceColonna+1, formulaEvaluator.evaluateInCell(cell).getCellType()==XSSFCell.CELL_TYPE_NUMERIC, nomeSezione, nomeCampo);
			} else if (isCodFiscale) {
				if(checkPartitaCodFiscale(cell, campo+1, indiceColonna+1, nomeSezione, nomeCampo)) {
					valueCell = cell.getStringCellValue();
				}
			} else if (isIban) {
				if(checkIban(cell, campo+1, indiceColonna+1, nomeSezione, nomeCampo)) {
					valueCell = cell.getStringCellValue();
				}
			} else if (isCig) {
				if(checkCig(cell, campo+1, indiceColonna+1, nomeSezione, nomeCampo)) {
					valueCell = formulaEvaluator.evaluateInCell(cell).getCellType()==XSSFCell.CELL_TYPE_NUMERIC ? String.valueOf((int) cell.getNumericCellValue()) : cell.getStringCellValue();
				}
			} else if (isSmartCig) {
				if(checkSmartCig(cell, campo+1, indiceColonna+1, nomeSezione, nomeCampo)) {
					valueCell = formulaEvaluator.evaluateInCell(cell).getCellType()==XSSFCell.CELL_TYPE_NUMERIC ? String.valueOf((int) cell.getNumericCellValue()) : cell.getStringCellValue();
				}
			} else if (isCup) {
				if(checkCup(cell, campo+1, indiceColonna+1, nomeSezione, nomeCampo)) {
					valueCell = formulaEvaluator.evaluateInCell(cell).getCellType()==XSSFCell.CELL_TYPE_NUMERIC ? String.valueOf((int) cell.getNumericCellValue()) : cell.getStringCellValue();
				}
			}
			else {
				if(formulaEvaluator.evaluateInCell(cell).getCellType()==XSSFCell.CELL_TYPE_NUMERIC) {
					valueCell = (String.valueOf((int) cell.getNumericCellValue()));
				} else {
					valueCell = cell.getStringCellValue();
					if(valueCell.equals("Selezionare")) {
						valueCell = null;
					} 
//					else if (valueCell.equals("Si")) {
//						valueCell="SI";
//					} else if(valueCell.equals("No")) {
//						valueCell = "NO";
//					}
				}
			}
		}
		return valueCell;
	}

	private String getStandardValueCell(int campo, FormulaEvaluator formulaEvaluator, XSSFRow row, HSSFRow rowHssf, String nomeSezione) throws Exception {

		String valueCell = null;
		String nomeCampo = "";
		Cell cell = null;
		boolean isImporto = false;
		boolean isData = false;
		boolean isIntero = false;
		boolean isIva = false;
		boolean isSiNo = false;
		boolean isCodFiscale = false;
		boolean isIban = false;
		boolean isCig = false;
		boolean isSmartCig = false;
		boolean isCup = false;
		if (row != null) {
			cell = row.getCell(INDICE_COLONNA_VALORE_RIGA);
			if(row.getCell(INDICE_COLONNA_TIPO_RIGA).getStringCellValue()!=null && TIPO_IMPORTO.equals(row.getCell(INDICE_COLONNA_TIPO_RIGA).getStringCellValue())) {
				isImporto = true; 
			} else if (row.getCell(INDICE_COLONNA_TIPO_RIGA).getStringCellValue()!=null && TIPO_DATA.equals(row.getCell(INDICE_COLONNA_TIPO_RIGA).getStringCellValue())) {
				isData = true;
			} else if (row.getCell(INDICE_COLONNA_TIPO_RIGA).getStringCellValue()!=null && TIPO_INTERO.equals(row.getCell(INDICE_COLONNA_TIPO_RIGA).getStringCellValue())) {
				isIntero = true;
			} else if (row.getCell(INDICE_COLONNA_TIPO_RIGA).getStringCellValue()!=null && TIPO_IVA.equals(row.getCell(INDICE_COLONNA_TIPO_RIGA).getStringCellValue())) {
				isIva = true;
			} else if (row.getCell(INDICE_COLONNA_TIPO_RIGA).getStringCellValue()!=null && TIPO_COD_FISCALE.equals(row.getCell(INDICE_COLONNA_TIPO_RIGA).getStringCellValue())) {
				isCodFiscale = true;
			} else if (row.getCell(INDICE_COLONNA_TIPO_RIGA).getStringCellValue()!=null && TIPO_IBAN.equals(row.getCell(INDICE_COLONNA_TIPO_RIGA).getStringCellValue())) {
				isIban = true;
			} else if (row.getCell(INDICE_COLONNA_TIPO_RIGA).getStringCellValue()!=null && TIPO_CIG.equals(row.getCell(INDICE_COLONNA_TIPO_RIGA).getStringCellValue())) {
				isCig = true;
			} else if (row.getCell(INDICE_COLONNA_TIPO_RIGA).getStringCellValue()!=null && TIPO_SMART_CIG.equals(row.getCell(INDICE_COLONNA_TIPO_RIGA).getStringCellValue())) {
				isSmartCig = true;
			} else if (row.getCell(INDICE_COLONNA_TIPO_RIGA).getStringCellValue()!=null && TIPO_CUP.equals(row.getCell(INDICE_COLONNA_TIPO_RIGA).getStringCellValue())) {
				isCup = true;
			} else if (row.getCell(INDICE_COLONNA_TIPO_RIGA).getStringCellValue()!=null && TIPO_SI_NO.equals(row.getCell(INDICE_COLONNA_TIPO_RIGA).getStringCellValue())) {
				isSiNo = true;
			}
			nomeCampo = row.getCell(2).getStringCellValue();
		} else {
			cell = rowHssf.getCell(INDICE_COLONNA_VALORE_RIGA);
			if(rowHssf.getCell(INDICE_COLONNA_TIPO_RIGA).getStringCellValue()!=null && TIPO_IMPORTO.equals(rowHssf.getCell(INDICE_COLONNA_TIPO_RIGA).getStringCellValue())) {
				isImporto = true; 
			} else if (rowHssf.getCell(INDICE_COLONNA_TIPO_RIGA).getStringCellValue()!=null && TIPO_DATA.equals(rowHssf.getCell(INDICE_COLONNA_TIPO_RIGA).getStringCellValue())) {
				isData = true;
			} else if (rowHssf.getCell(INDICE_COLONNA_TIPO_RIGA).getStringCellValue()!=null && TIPO_INTERO.equals(rowHssf.getCell(INDICE_COLONNA_TIPO_RIGA).getStringCellValue())) {
				isIntero = true;
			} else if (rowHssf.getCell(INDICE_COLONNA_TIPO_RIGA).getStringCellValue()!=null && TIPO_IVA.equals(rowHssf.getCell(INDICE_COLONNA_TIPO_RIGA).getStringCellValue())) {
				isIva = true;
			} else if (rowHssf.getCell(INDICE_COLONNA_TIPO_RIGA).getStringCellValue()!=null && TIPO_COD_FISCALE.equals(rowHssf.getCell(INDICE_COLONNA_TIPO_RIGA).getStringCellValue())) {
				isCodFiscale = true;
			} else if (rowHssf.getCell(INDICE_COLONNA_TIPO_RIGA).getStringCellValue()!=null && TIPO_IBAN.equals(rowHssf.getCell(INDICE_COLONNA_TIPO_RIGA).getStringCellValue())) {
				isIban = true;
			} else if (rowHssf.getCell(INDICE_COLONNA_TIPO_RIGA).getStringCellValue()!=null && TIPO_CIG.equals(rowHssf.getCell(INDICE_COLONNA_TIPO_RIGA).getStringCellValue())) {
				isCig = true;
			} else if (rowHssf.getCell(INDICE_COLONNA_TIPO_RIGA).getStringCellValue()!=null && TIPO_SMART_CIG.equals(rowHssf.getCell(INDICE_COLONNA_TIPO_RIGA).getStringCellValue())) {
				isSmartCig = true;
			} else if (rowHssf.getCell(INDICE_COLONNA_TIPO_RIGA).getStringCellValue()!=null && TIPO_CUP.equals(rowHssf.getCell(INDICE_COLONNA_TIPO_RIGA).getStringCellValue())) {
				isCup = true;
			} else if (rowHssf.getCell(INDICE_COLONNA_TIPO_RIGA).getStringCellValue()!=null && TIPO_SI_NO.equals(rowHssf.getCell(INDICE_COLONNA_TIPO_RIGA).getStringCellValue())) {
				isSiNo = true;
			}
			nomeCampo = rowHssf.getCell(2).getStringCellValue();
		}

		if (cell != null && formulaEvaluator.evaluateInCell(cell).getCellType() != XSSFCell.CELL_TYPE_BLANK) {
			if (isImporto) {
				valueCell = normalizeImporto(formulaEvaluator, cell, campo+1, INDICE_COLONNA_VALORE_RIGA+1, nomeSezione, nomeCampo);
			} else if (isData) {
				valueCell = normalizeData(cell, campo+1, INDICE_COLONNA_VALORE_RIGA+1, nomeSezione, nomeCampo);
			} else if (isIntero) {
				valueCell = normalizeNumericoIntero(cell, campo+1, INDICE_COLONNA_VALORE_RIGA+1, nomeSezione, nomeCampo);
			} else if (isIva) {
				valueCell = checkPartitaIva(cell, campo+1, INDICE_COLONNA_VALORE_RIGA+1, formulaEvaluator.evaluateInCell(cell).getCellType()==XSSFCell.CELL_TYPE_NUMERIC, nomeSezione, nomeCampo);
			} else if (isSiNo) {
				valueCell = checkSiNo(cell, campo+1, INDICE_COLONNA_VALORE_RIGA+1, formulaEvaluator.evaluateInCell(cell).getCellType()==XSSFCell.CELL_TYPE_NUMERIC, nomeSezione, nomeCampo);
			} else if (isCodFiscale) {
				if(checkPartitaCodFiscale(cell, campo+1, INDICE_COLONNA_VALORE_RIGA+1, nomeSezione, nomeCampo)) {
					valueCell = cell.getStringCellValue();
				}
			} else if (isIban) {
				if(checkIban(cell, campo+1, INDICE_COLONNA_VALORE_RIGA+1, nomeSezione, nomeCampo)) {
					valueCell = cell.getStringCellValue();
				}
			} else if (isCig) {
				if(checkCig(cell, campo+1, INDICE_COLONNA_VALORE_RIGA+1, nomeSezione, nomeCampo)) {
					valueCell = formulaEvaluator.evaluateInCell(cell).getCellType()==XSSFCell.CELL_TYPE_NUMERIC ? String.valueOf((int) cell.getNumericCellValue()) : cell.getStringCellValue();
				}
			} else if (isSmartCig) {
				if(checkSmartCig(cell, campo+1, INDICE_COLONNA_VALORE_RIGA+1, nomeSezione, nomeCampo)) {
					valueCell = formulaEvaluator.evaluateInCell(cell).getCellType()==XSSFCell.CELL_TYPE_NUMERIC ? String.valueOf((int) cell.getNumericCellValue()) : cell.getStringCellValue();
				}
			} else if (isCup) {
				if(checkCup(cell, campo+1, INDICE_COLONNA_VALORE_RIGA+1, nomeSezione, nomeCampo)) {
					valueCell = formulaEvaluator.evaluateInCell(cell).getCellType()==XSSFCell.CELL_TYPE_NUMERIC ? String.valueOf((int) cell.getNumericCellValue()) : cell.getStringCellValue();
				}
			}
			else {
				if(formulaEvaluator.evaluateInCell(cell).getCellType()==XSSFCell.CELL_TYPE_NUMERIC) {
					valueCell = (String.valueOf((int) cell.getNumericCellValue()));
				} else {
					valueCell = cell.getStringCellValue();
					if(valueCell.equals("Selezionare")) {
						valueCell = null;
					} 
//					else if (valueCell.equals("Si")) {
//						valueCell="SI";
//					} else if(valueCell.equals("No")) {
//						valueCell = "NO";
//					}
				}
				
			}
		}
		return valueCell;
	}

	private boolean isCampoSpesaTitoloBilancio(int r) {
		if(r>85 && r<93) {
			return true;
		}else {
			return false;
		}
	}

	private boolean isCampoLiquidazioniCompensiProgettualita(int r) {
		if(r>83 && r<86) {
			return true;
		}else {
			return false;
		}
	}

	private boolean isCampoDisimpegno(int r) {
		return r == 80;
		//		if(r>79 && r<84) {
		//			return true;
		//		}else {
		//			return false;
		//		}
	}

	private boolean isCampoSpesaEntrata(int r) {
		return r==70 || r==71;
	}
	
	private boolean isCampoRifSpesaEntrata(int r) {
		return r==73;
		//		if(r>72 && r<79) {
		//			return true;
		//		}else {
		//			return false;
		//		}
	}

	private boolean isCampoImputazioneContabile(int r) {
		return r==60;
		//		if(r>59 && r<70) {
		//			return true;
		//		}else {
		//			return false;
		//		}
	}

	private boolean isCampoTerminiPagamento(int r) {
		if(r>53 && r<60) {
			return true;
		}else {
			return false;
		}
	}

	private boolean isCampoProgressivoRegistrazione(int r) {
		if(r==53) {
			return true;
		}else {
			return false;
		}
	}

	private boolean isCampoCigCup(int r) {
		if(r>45 && r<53) {
			return true;
		}else {
			return false;
		}
	}

	private boolean isCampoVerificheEquitaliaAntiriciclaggio(int r) {
		if(r>42 && r<46) {
			return true;
		}else {
			return false;
		}
	}

	private boolean isCampoDURC(int r) {
		if(r>38 && r<43) {
			return true;
		}else {
			return false;
		}			
	}

	private boolean isCampoRegimeIvaBollo(int r) {
		if(r>34 && r<39) {
			return true;
		}else {
			return false;
		}		
	}

	private boolean isCampoBeneficiarioPagamento(int r) {
		if(r>12 && r<35) {
			return true;
		}else {
			return false;
		}	
	}

	private boolean isCampoOggettoDellaSpesa(int r) {
		if(r>1 && r<13) {
			return true;
		}else {
			return false;
		}
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
	
	private static boolean isRowEmpty(XSSFRow row) {
		for (int c = row.getFirstCellNum(); c < row.getLastCellNum(); c++) {
			XSSFCell cell = row.getCell(c);
			if (cell != null && cell.getCellType() != XSSFCell.CELL_TYPE_BLANK && cell.getCellType() == HSSFCell.CELL_TYPE_STRING && cell.getStringCellValue() != null && !cell.getStringCellValue().trim().equals("")) {
				return false;				
			}
		}
		return true;
	}

	private boolean manageIsEndOfColumn(XSSFSheet sheet, HSSFSheet sheetHssf, int indiceColonna, int constantColumn) {
		if (sheet != null) {
			return isEndOfColumn (sheet.getRow(constantColumn).getCell(indiceColonna));
		} else {
			return isEndOfColumn (sheetHssf.getRow(constantColumn).getCell(indiceColonna));
		}
	}
	
	private boolean isEndOfColumn(XSSFCell cell) {
		XSSFColor fillFGColor = cell.getCellStyle().getFillForegroundColorColor();
		//	    FFFF0000 riempimento cella rosso su excel
		if(fillFGColor!=null && fillFGColor.getARGBHex() != null && fillFGColor.getARGBHex().equals("FFFF0000")) {
			return true;
		}
		return false;
	}
	
	private boolean isEndOfColumn(HSSFCell cell) {
		HSSFColor fillFGColor = cell.getCellStyle().getFillForegroundColorColor();
		//	    FFFF0000 riempimento cella rosso su excel
		if(fillFGColor!=null && fillFGColor.getHexString() != null && fillFGColor.getHexString().equals("FFFF0000")) {
			return true;
		}
		return false;
	}

	
	public String normalizeImporto(FormulaEvaluator formulaEvaluator, Cell cell, int riga, int colonna, String nomeSezione, String nomeCampo) throws Exception {
		try {
			DecimalFormat format = (DecimalFormat)NumberFormat.getNumberInstance(Locale.ITALIAN);
			format.applyPattern(IMPORTO_PATTERN);
			if(formulaEvaluator.evaluateInCell(cell).getCellType()==XSSFCell.CELL_TYPE_NUMERIC) {
				double valoreImportoDouble = cell.getNumericCellValue();
				return String.valueOf(format.format(new Double(valoreImportoDouble)));	
			} else {
				if (cell.getStringCellValue()!=null && !"".equals(cell.getStringCellValue())) {
					String valoreImportoString = cell.getStringCellValue();
					String importoDaValutare = valoreImportoString.replaceAll("\\.", "");
					if (importoDaValutare.contains(",")) {
						importoDaValutare = importoDaValutare.replaceAll(",", ".");
					}
					double parseDoubleImporto = Double.parseDouble(importoDaValutare);
					return format.format(parseDoubleImporto);
				} else {
					throw new ValidatorException("Il valore del dato " + nomeCampo + ", di tipo importo, della sezione " + nomeSezione + ", inserito in riga " + riga + " e colonna " + colonna + " non risulta essere valido" );
				}
			}
		} catch (NumberFormatException e) {
			logger.error(e);
			throw new ValidatorException("Il valore del dato " + nomeCampo + ", di tipo importo, della sezione " + nomeSezione + ", inserito in riga " + riga + " e colonna " + colonna + " non risulta essere valido" );
		}
	}
	
	public String normalizeData(Cell cell, int riga, int colonna, String nomeSezione, String nomeCampo) throws Exception {
		try {
			if (HSSFDateUtil.isCellDateFormatted(cell)) {
				String pattern = "dd/MM/yyyy";
				SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
				return simpleDateFormat.format(cell.getDateCellValue());
			} else {
				throw new ValidatorException("Il valore del dato " + nomeCampo + ", di tipo data, della sezione " + nomeSezione + ", inserito in riga " + riga + " e colonna " + colonna + " non risulta essere valido");
			}
		} catch (Exception e) {
			logger.error(e);
			throw new ValidatorException("Il valore del dato " + nomeCampo + ", di tipo data, della sezione " + nomeSezione + ", inserito in riga " + riga + " e colonna " + colonna + " non risulta essere valido");
		}
	}
	
	public String normalizeNumericoIntero(Cell cell, int riga, int colonna, String nomeSezione, String nomeCampo) throws Exception {
		try {
			return (String.valueOf((int) cell.getNumericCellValue()));
		} catch (Exception e) {
			logger.error(e);
			throw new ValidatorException("Il valore del dato " + nomeCampo + ", di tipo numerico intero, della sezione " + nomeSezione + ", inserito in riga " + riga + " e colonna " + colonna + " non risulta essere valido" );
		}
	}
	
	public String checkPartitaIva(Cell cell, int riga, int colonna, boolean isNumericValue, String nomeSezione, String nomeCampo) throws Exception {
		try {
			String regExpIVA = ParametriDBUtil.getParametroDB(session, "REGEXP_PIVA") != null && !ParametriDBUtil.getParametroDB(session, "REGEXP_PIVA").equals("") ? ParametriDBUtil.getParametroDB(session, "REGEXP_PIVA") : NO_REGEX;
			RegExp regExp = RegExp.compile(regExpIVA);
			if (isNumericValue) {
				double valueNumeric = (double) cell.getNumericCellValue();
				String valueOf = String.valueOf(valueNumeric);
				valueOf = valueOf.replaceAll("\\.", "");
				valueOf = valueOf.substring(0,valueOf.lastIndexOf("E"));
				if (regExpIVA.equals(NO_REGEX)) {
					return valueOf;
				}
				if (regExp.test(valueOf)) {
					return valueOf;
				} else {
					throw new ValidatorException("Il valore del dato " + nomeCampo + ", di tipo partita iva, della sezione " + nomeSezione + ", inserito in riga " + riga + " e colonna " + colonna + " non risulta essere valido" );
				}
			} else {
				if (regExpIVA.equals(NO_REGEX)) {
					return (String) cell.getStringCellValue();
				}
				if (regExp.test((String) cell.getStringCellValue())) {
					return (String) cell.getStringCellValue();
				} else {
					throw new ValidatorException("Il valore del dato " + nomeCampo + ", di tipo partita iva, della sezione " + nomeSezione + ", inserito in riga " + riga + " e colonna " + colonna + " non risulta essere valido" );
				}
			}
		} catch (Exception e) {
			throw new ValidatorException("Il valore del dato " + nomeCampo + ", di tipo partita iva, della sezione " + nomeSezione + ", inserito in riga " + riga + " e colonna " + colonna + " non risulta essere valido" );
		}
	}
	
	public String checkSiNo(Cell cell, int riga, int colonna, boolean isNumericValue, String nomeSezione, String nomeCampo) throws Exception {
		try {
			
			if (isNumericValue) {
				throw new ValidatorException("Il valore del dato " + nomeCampo + ", di tipo SI/NO, della sezione " + nomeSezione + ", inserito in riga " + riga + " e colonna " + colonna + " non risulta essere valido" );
			} else {
				String valueCell = (String) cell.getStringCellValue();

				if (valueCell.equals("Si")) {
					valueCell="SI";
				} else if(valueCell.equals("No")) {
					valueCell = "NO";
				} else if(valueCell.equals("Selezionare")) {
					valueCell = null;
				} else {
					throw new ValidatorException("Il valore del dato " + nomeCampo + ", di tipo SI/NO, della sezione " + nomeSezione + ", inserito in riga " + riga + " e colonna " + colonna + " non risulta essere valido" );
				}
				return valueCell;
			}
		} catch (Exception e) {
			throw new ValidatorException("Il valore del dato " + nomeCampo + ", di tipo SI/NO, della sezione " + nomeSezione + ", inserito in riga " + riga + " e colonna " + colonna + " non risulta essere valido" );
		}
	}
	
	public boolean checkPartitaCodFiscale(Cell cell, int riga, int colonna, String nomeSezione, String nomeCampo) throws Exception {
		try {
			String regExpCF = ParametriDBUtil.getParametroDB(session, "REGEXP_CF_PERS_FISICA") != null && !ParametriDBUtil.getParametroDB(session, "REGEXP_CF_PERS_FISICA").equals("") ? ParametriDBUtil.getParametroDB(session, "REGEXP_CF_PERS_FISICA") : NO_REGEX;
			if (regExpCF.equals(NO_REGEX)) {
				return true;
			}
			RegExp regExp = RegExp.compile(regExpCF);
			if (regExp.test((String) cell.getStringCellValue())) {
				return true;
			} else {
				throw new ValidatorException("Il valore del dato " + nomeCampo + ", di tipo codice fiscale, della sezione " + nomeSezione + ", inserito in riga " + riga + " e colonna " + colonna + " non risulta essere valido" );
			}
		} catch (Exception e) {
			throw new ValidatorException("Il valore del dato " + nomeCampo + ", di tipo codice fiscale, della sezione " + nomeSezione + ", inserito in riga " + riga + " e colonna " + colonna + " non risulta essere valido" );
		}
	}
	
	public boolean checkIban(Cell cell, int riga, int colonna, String nomeSezione, String nomeCampo) throws Exception {
		try {
			String regExpIban = ParametriDBUtil.getParametroDB(session, "REGEXP_IBAN") != null && !ParametriDBUtil.getParametroDB(session, "REGEXP_IBAN").equals("") ? ParametriDBUtil.getParametroDB(session, "REGEXP_IBAN") : NO_REGEX;
			if (regExpIban.equals(NO_REGEX)) {
				return true;
			}
			RegExp regExp = RegExp.compile(regExpIban);
			if (regExp.test((String) cell.getStringCellValue())) {
				return true;
			} else {
				throw new ValidatorException("Il valore del dato " + nomeCampo + ", di tipo IBAN, della sezione " + nomeSezione + ", inserito in riga " + riga + " e colonna " + colonna + " non risulta essere valido" );
			}
		} catch (Exception e) {
			throw new ValidatorException("Il valore del dato " + nomeCampo + ", di tipo IBAN, della sezione " + nomeSezione + ", inserito in riga " + riga + " e colonna " + colonna + " non risulta essere valido" );
		}
	}
	
	public boolean checkCig(Cell cell, int riga, int colonna, String nomeSezione, String nomeCampo) throws Exception {
		try {
			String regExpCig = ParametriDBUtil.getParametroDB(session, "REGEXP_CIG") != null && !ParametriDBUtil.getParametroDB(session, "REGEXP_CIG").equals("") ? ParametriDBUtil.getParametroDB(session, "REGEXP_CIG") : NO_REGEX;
			if (regExpCig.equals(NO_REGEX)) {
				return true;
			}
			RegExp regExp = RegExp.compile(regExpCig);
			if (regExp.test((String) cell.getStringCellValue())) {
				return true;
			} else {
				throw new ValidatorException("Il valore del dato " + nomeCampo + ", di tipo CIG, della sezione " + nomeSezione + ", inserito in riga " + riga + " e colonna " + colonna + " non risulta essere valido" );
			}
		} catch (Exception e) {
			throw new ValidatorException("Il valore del dato " + nomeCampo + ", di tipo CIG, della sezione " + nomeSezione + ", inserito in riga " + riga + " e colonna " + colonna + " non risulta essere valido" );
		}
	}
	
	public boolean checkSmartCig(Cell cell, int riga, int colonna, String nomeSezione, String nomeCampo) throws Exception {
		try {
			String regExpsMARTCig = ParametriDBUtil.getParametroDB(session, "REGEXP_SMART_CIG") != null && !ParametriDBUtil.getParametroDB(session, "REGEXP_SMART_CIG").equals("") ? ParametriDBUtil.getParametroDB(session, "REGEXP_SMART_CIG") : NO_REGEX;
			if (regExpsMARTCig.equals(NO_REGEX)) {
				return true;
			}
			RegExp regExp = RegExp.compile(regExpsMARTCig);
			if (regExp.test((String) cell.getStringCellValue())) {
				return true;
			} else {
				throw new ValidatorException("Il valore del dato " + nomeCampo + ", di tipo SMART CIG, della sezione " + nomeSezione + ", inserito in riga " + riga + " e colonna " + colonna + " non risulta essere valido" );
			}
		} catch (Exception e) {
			throw new ValidatorException("Il valore del dato " + nomeCampo + ", di tipo SMART CIG, della sezione " + nomeSezione + ", inserito in riga " + riga + " e colonna " + colonna + " non risulta essere valido" );
		}
	}
	
	public boolean checkCup(Cell cell, int riga, int colonna, String nomeSezione, String nomeCampo) throws Exception {
		try {
			String regExpCup = ParametriDBUtil.getParametroDB(session, "REGEXP_CUP") != null && !ParametriDBUtil.getParametroDB(session, "REGEXP_CUP").equals("") ? ParametriDBUtil.getParametroDB(session, "REGEXP_CUP") : NO_REGEX;
			if (regExpCup.equals(NO_REGEX)) {
				return true;
			}
			RegExp regExp = RegExp.compile(regExpCup);
			if (regExp.test((String) cell.getStringCellValue())) {
				return true;
			} else {
				throw new ValidatorException("Il valore del dato " + nomeCampo + ", di tipo CUP, della sezione " + nomeSezione + ", inserito in riga " + riga + " e colonna " + colonna + " non risulta essere valido" );
			}
		} catch (Exception e) {
			throw new ValidatorException("Il valore del dato " + nomeCampo + ", di tipo CUP, della sezione " + nomeSezione + ", inserito in riga " + riga + " e colonna " + colonna + " non risulta essere valido" );
		}
	}
	
}
