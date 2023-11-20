/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.validator.ValidatorException;
import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.google.gwt.regexp.shared.RegExp;

import it.eng.auriga.exception.StoreException;
import it.eng.auriga.ui.module.layout.client.RegExpUtility;
import it.eng.auriga.ui.module.layout.server.bean.ImportExcelBean;
import it.eng.auriga.ui.module.layout.server.pratiche.nuovapropostaatto2.datasource.bean.DatiContabiliBean;
import it.eng.auriga.ui.module.layout.server.pratiche.nuovapropostaatto2.datasource.bean.DestVantaggioBean;
import it.eng.auriga.ui.module.layout.server.pratiche.nuovapropostaatto2.datasource.bean.ErroreRigaExcelBean;
import it.eng.auriga.ui.module.layout.server.pratiche.nuovapropostaatto2.datasource.bean.ListaDestVantaggioBean;
import it.eng.auriga.ui.module.layout.shared.bean.CelleExcelBeneficiariEnum;
import it.eng.utility.module.config.StorageImplementation;
import it.eng.utility.ui.module.core.server.bean.AdvancedCriteria;
import it.eng.utility.ui.module.core.server.bean.OrderByBean;
import it.eng.utility.ui.module.core.server.bean.PaginatorBean;
import it.eng.utility.ui.module.core.server.datasource.AbstractFetchDataSource;
import it.eng.utility.ui.module.core.server.datasource.annotation.Datasource;

@Datasource(id = "BeneficiariTrasparenzaDataSource")
public class BeneficiariTrasparenzaDataSource extends AbstractFetchDataSource<DatiContabiliBean> {

	private static final Logger logger = Logger.getLogger(BeneficiariTrasparenzaDataSource.class);
	
//	private static final int CELLA_TIPO_PERSONA = 0;
//	private static final int CELLA_NOME = 1;
//	private static final int CELLA_COGNOME = 2;
//	private static final int CELLA_RAGIONE_SOCIALE = 3;
//	private static final int CELLA_CF_PIVA = 4;
//	private static final int CELLA_IMPORTO = 5;
//	private static final int CELLA_PROT_PRIVACY = 6;
//	private static final int CELLA_TIPO = 7;
//	
//	/** AGGIORNARE NUMERO CAMPI TOTALI SE SI AGGIUNGE UNA COLONNA**/
//	private static final int NUMERO_CAMPI = 8;
	private static final String IMPORTO_PATTERN = "#,##0.00";

	public ListaDestVantaggioBean importaDestVantaggioFromExcel(ImportExcelBean importExcelBean) throws Exception {
		
		String uriExcel = StringUtils.isNotBlank(importExcelBean.getUriExcel()) ? importExcelBean.getUriExcel() : getExtraparams().get("uriExcel");
		String mimeType = StringUtils.isNotBlank(importExcelBean.getMimetype()) ? importExcelBean.getMimetype() : getExtraparams().get("mimetype");
		HashMap<String, String> mappaObbligatorieta = importExcelBean.getMappaObbligatorieta()!=null ? importExcelBean.getMappaObbligatorieta() : new HashMap<String, String>();

		ListaDestVantaggioBean result = new ListaDestVantaggioBean();

		try {

			boolean isXls = mimeType.equals("application/excel");
			boolean isXlsx = mimeType.equals("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");

			/**
			 * Implementazione HSSF (Horrible SpreadSheet Format): indica un'API che funziona con Excel 2003 o versioni precedenti.
			 */
			if (isXls) {
				result = caricaDatiFromXls(uriExcel, mappaObbligatorieta);
			}
			
			/**
			 * Implementazione XSSF (XML SpreadSheet Format): indica un'API che funziona con Excel 2007 o versioni successive.
			 */
			else if (isXlsx) { 
				result = caricaDatiFromXlsx(uriExcel,mappaObbligatorieta);
			} 
			
			else {
				String message = "Il formato del documento non è supportato, solo xls e xlsx sono ammessi come documenti validi";
				logger.error(message);

				throw new StoreException(message);
			}
			
		} catch (Exception e) {
			String errorMessage = e.getMessage() != null ? e.getMessage() : e.getCause() != null ? e.getCause().getMessage() : null;

			String message = "Durante il caricamento delle righe del file, si è verificata la seguente eccezione: " + errorMessage;
			logger.error(message, e);

			throw new StoreException(message); 
		}

		return result;
		
	}

	private ListaDestVantaggioBean caricaDatiFromXls(String uriExcel, HashMap<String, String> mappaObbligatorieta) throws Exception {
		BufferedInputStream documentStream = null;
		InputStream is = null;
		ListaDestVantaggioBean result= new ListaDestVantaggioBean();
		List<DestVantaggioBean> listaDestVantaggioBean = new ArrayList<DestVantaggioBean>();
		List<ErroreRigaExcelBean> listaRigheInErrore = new ArrayList<ErroreRigaExcelBean>();

		try {
			File document = StorageImplementation.getStorage().getRealFile(uriExcel);

			is = new FileInputStream(document);
			documentStream = new BufferedInputStream(is);

			HSSFWorkbook wb = new HSSFWorkbook(documentStream);
			HSSFSheet sheet = wb.getSheetAt(0);
			HSSFRow row;
			HSSFCell cell;

			int rows; // No of rows
			rows = sheet.getPhysicalNumberOfRows();

			for (int r = 0; r < rows + 1; r++) {
				row = sheet.getRow(r);
				if (row != null && !isRowEmpty(row)) {

					/** CONTROLLO SE C'E' L'INTESTAZIONE */
					FormulaEvaluator formulaEvaluator = wb.getCreationHelper().createFormulaEvaluator();
					if (row.getCell(0) != null
							&& formulaEvaluator.evaluateInCell(row.getCell(0)).getCellType() == Cell.CELL_TYPE_STRING
							&& row.getCell(0).getStringCellValue().equalsIgnoreCase("Tipo persona")) {
						continue;
					}

					try {
						DestVantaggioBean destVantaggioBean = new DestVantaggioBean();
						for (int c = 0; c < CelleExcelBeneficiariEnum.values().length; c++) {
							cell = row.getCell(c);
							switch (CelleExcelBeneficiariEnum.getCellFromIndex(c)) {

							case TIPO_PERSONA:
								if (cell != null  && formulaEvaluator.evaluateInCell(row.getCell(c)).getCellType() != Cell.CELL_TYPE_BLANK ) {
									if("fisica".equalsIgnoreCase(cell.getStringCellValue()) || "giuridica".equalsIgnoreCase(cell.getStringCellValue())) {
										destVantaggioBean.setTipoPersona(cell.getStringCellValue());
									} else {
										throw new ValidatorException("Il dato Tipo persona corrispondente alla colonna " + (c + 1)
												+ " può avere valori fisica o giuridica");
									}
								}else {
									checkObbligatorieta(CelleExcelBeneficiariEnum.TIPO_PERSONA, mappaObbligatorieta);
								}
								break;
							case NOME:
								if (destVantaggioBean.getTipoPersona().equalsIgnoreCase("fisica")) {
									if (cell != null && formulaEvaluator.evaluateInCell(row.getCell(c)).getCellType() != Cell.CELL_TYPE_BLANK) {
											destVantaggioBean.setNome(cell.getStringCellValue());									
										} else {
											checkObbligatorieta(CelleExcelBeneficiariEnum.NOME, mappaObbligatorieta);
										}		
								}															
								break;
							case COGNOME:
								if (destVantaggioBean.getTipoPersona().equalsIgnoreCase("fisica")) {
									if (cell != null && formulaEvaluator.evaluateInCell(row.getCell(c)).getCellType() != Cell.CELL_TYPE_BLANK) {
											destVantaggioBean.setCognome(cell.getStringCellValue());									
										} else {
											checkObbligatorieta(CelleExcelBeneficiariEnum.COGNOME, mappaObbligatorieta);
										}		
								}								
								break;
							case RAGIONE_SOCIALE:
								if (destVantaggioBean.getTipoPersona().equalsIgnoreCase("giuridica")) {
									if (cell != null && formulaEvaluator.evaluateInCell(row.getCell(c)).getCellType() != Cell.CELL_TYPE_BLANK) {
											destVantaggioBean.setRagioneSociale(cell.getStringCellValue());										
										} else {
											checkObbligatorieta(CelleExcelBeneficiariEnum.RAGIONE_SOCIALE, mappaObbligatorieta);
										}		
								}					
								break;
							case CF_PIVA:
								if (cell != null && formulaEvaluator.evaluateInCell(row.getCell(c)).getCellType() != Cell.CELL_TYPE_BLANK) {
									String codiceFiscalePIva ="";
									cell.setCellType(Cell.CELL_TYPE_STRING); /*forzo il valore della cella a String cosi da non avere problemi di lettura*/
									
									if(formulaEvaluator.evaluateInCell(cell).getCellType()==Cell.CELL_TYPE_NUMERIC) {
										codiceFiscalePIva = (String.valueOf((int) Math.round(cell.getNumericCellValue())));
									}else {
										codiceFiscalePIva = (cell.getStringCellValue());
									}
									
									/*L'export produceva una cella vuota che pero la condizione != Cell.CELL_TYPE_BLANK non riconosceva 
									 * quindi aggiungo questo controllo in piu */
									if(StringUtils.isNotBlank(codiceFiscalePIva)) {
										// quando scelgo persona giuridica se è un professionista nel CF/P. IVA devo poter mettere CF di persona
										if (destVantaggioBean.getTipoPersona().equalsIgnoreCase("giuridica")) {
											RegExp regExp = RegExp.compile(RegExpUtility.codiceFiscalePIVARegExp());
											
											if(regExp.test((String) codiceFiscalePIva)) {
												destVantaggioBean.setCodFiscalePIVA(codiceFiscalePIva);
											}else {
												throw new ValidatorException("Il dato C.F/P.IVA corrispondente alla colonna " + (c + 1)
														+ " non è nel formato corretto");
											}
										} else {
											RegExp regExp = RegExp.compile(RegExpUtility.codiceFiscaleRegExp());
											if(regExp.test((String) codiceFiscalePIva)) {
												destVantaggioBean.setCodFiscalePIVA(codiceFiscalePIva);
											}else {
												throw new ValidatorException("Il dato C.F/P.IVA corrispondente alla colonna " + (c + 1)
														+ " non è nel formato corretto");
											}
										}
									}									
								}else {
									checkObbligatorieta(CelleExcelBeneficiariEnum.CF_PIVA, mappaObbligatorieta);
								}
								break;
							case IMPORTO:
								if (cell != null && formulaEvaluator.evaluateInCell(row.getCell(c)).getCellType() != Cell.CELL_TYPE_BLANK) {									
									String importo = "";
									DecimalFormat format = (DecimalFormat)NumberFormat.getNumberInstance(Locale.ITALIAN);
									format.applyPattern(IMPORTO_PATTERN);
									if(formulaEvaluator.evaluateInCell(cell).getCellType()==XSSFCell.CELL_TYPE_NUMERIC) {
										double valoreImportoDouble = cell.getNumericCellValue();
										importo = String.valueOf(format.format(new Double(valoreImportoDouble)));	
									} else {
										if (cell.getStringCellValue()!=null && !"".equals(cell.getStringCellValue())) {
											String valoreImportoString = cell.getStringCellValue();
											String importoDaValutare = valoreImportoString.replaceAll("\\.", "");
											if (importoDaValutare.contains(",")) {
												importoDaValutare = importoDaValutare.replaceAll(",", ".");
											}
											double parseDoubleImporto = Double.parseDouble(importoDaValutare);
											if(parseDoubleImporto < 0) {
											throw new ValidatorException("Il dato Importo corrispondente alla colonna " + (c + 1)
													+ " deve essere maggiore o uguale di 0");
											}
											importo = format.format(parseDoubleImporto);
										}
									}
									destVantaggioBean.setImporto(importo);
								} else {
									checkObbligatorieta(CelleExcelBeneficiariEnum.IMPORTO, mappaObbligatorieta);

								}
								break;
								
							case PROT_PRIVACY:
								if (cell != null && formulaEvaluator.evaluateInCell(row.getCell(c)).getCellType() != Cell.CELL_TYPE_BLANK) {
									String valueCell = cell.getStringCellValue();
									
									if("si".equalsIgnoreCase(valueCell)) {
										destVantaggioBean.setFlgPrivacy(true);
									}else if("no".equalsIgnoreCase(valueCell)) {
										destVantaggioBean.setFlgPrivacy(false);
									}
									else {
										throw new ValidatorException("Il dato Dati protetti da privacy corrispondente alla colonna " + (c + 1)
												+ " può avere solo valori si o no");
									}								
								}else {
									destVantaggioBean.setFlgPrivacy(false);
								}
								break;
								
							case TIPO:
								if (cell != null && formulaEvaluator.evaluateInCell(row.getCell(c)).getCellType() != Cell.CELL_TYPE_BLANK) {
									cell.setCellType(Cell.CELL_TYPE_STRING); /*forzo il valore della cella a String cosi da non avere problemi di lettura */

									if (StringUtils.isNotBlank(cell.getStringCellValue())) {
										String valueCell = cell.getStringCellValue();

										if ("mandatario".equalsIgnoreCase(valueCell)
												|| "mandante".equalsIgnoreCase(valueCell)) {
											destVantaggioBean.setTipo(valueCell);
										} else {
											throw new ValidatorException("Il dato Tipo corrispondente alla colonna "
													+ (c + 1) + " può avere solo valori mandatario o mandante");
										}
									}else {
										checkObbligatorieta(CelleExcelBeneficiariEnum.TIPO, mappaObbligatorieta);
									}
								}else {
									checkObbligatorieta(CelleExcelBeneficiariEnum.TIPO, mappaObbligatorieta);
								}
								break;
							}
						}
						
						listaDestVantaggioBean.add(destVantaggioBean);
						
					} catch (Exception e) {
						ErroreRigaExcelBean erroreRiga = new ErroreRigaExcelBean();
						erroreRiga.setNumeroRiga(String.valueOf(r + 1));
						erroreRiga.setMotivo(e.getMessage());

						listaRigheInErrore.add(erroreRiga);
					}
				}			
			}
		} catch (Exception e) {
			if (!(e instanceof ValidatorException)) {
				logger.error("Errore durante la lettura dei dati dal foglio Excel: " + e.getMessage(), e);
			}

			throw e;

		} finally {
			if (is != null) {
				try {
					is.close();
				} catch (Exception e) {
				}
			}
			if (documentStream != null) {
				try {
					documentStream.close();
				} catch (Exception e) {
					logger.error("Impossibile chiudere lo stream legato al documento a causa della seguente eccezione ",
							e);
				}
			}
		}

		result.setListaDestVantaggioBean(listaDestVantaggioBean);
		result.setListaErroreRigaExcelBean(listaRigheInErrore);

		return result;
	}
	
	
	private ListaDestVantaggioBean caricaDatiFromXlsx(String uriExcel, HashMap<String, String> mappaObbligatorieta) throws Exception {
		
		FileInputStream fis = null;
		ListaDestVantaggioBean result= new ListaDestVantaggioBean();
		List<DestVantaggioBean> listaDestVantaggioBean = new ArrayList<DestVantaggioBean>();
		List<ErroreRigaExcelBean> listaRigheInErrore = new ArrayList<ErroreRigaExcelBean>();
		
		try{
			File document = StorageImplementation.getStorage().getRealFile(uriExcel);
			fis = new FileInputStream(document); 
			XSSFWorkbook wb = new XSSFWorkbook(fis);
			XSSFSheet sheet = wb.getSheetAt(0); 
			Row row;
			Cell cell;
			
			int rows; // No of rows
		    rows = sheet.getLastRowNum();

		    for (int r = 0; r < rows + 1; r++) {
				row = sheet.getRow(r);
				if (row != null && !isRowEmpty(row)) {

					/** CONTROLLO SE C'E' L'INTESTAZIONE */
					FormulaEvaluator formulaEvaluator = wb.getCreationHelper().createFormulaEvaluator();
					if (row.getCell(0) != null
							&& formulaEvaluator.evaluateInCell(row.getCell(0)).getCellType() == Cell.CELL_TYPE_STRING
							&& row.getCell(0).getStringCellValue().equalsIgnoreCase("Tipo persona")) {
						continue;
					}

					try {
						DestVantaggioBean destVantaggioBean = new DestVantaggioBean();
						for (int c = 0; c < CelleExcelBeneficiariEnum.values().length; c++) {
							cell = row.getCell(c);
							switch (CelleExcelBeneficiariEnum.getCellFromIndex(c)) {

							case TIPO_PERSONA:
								if (cell != null  && formulaEvaluator.evaluateInCell(row.getCell(c)).getCellType() != Cell.CELL_TYPE_BLANK ) {
									if("fisica".equalsIgnoreCase(cell.getStringCellValue()) || "giuridica".equalsIgnoreCase(cell.getStringCellValue())) {
										destVantaggioBean.setTipoPersona(cell.getStringCellValue());
									} else {
										throw new ValidatorException("Il dato Tipo persona corrispondente alla colonna " + (c + 1)
												+ " può avere valori fisica o giuridica");
									}
								}else {
									checkObbligatorieta(CelleExcelBeneficiariEnum.TIPO_PERSONA, mappaObbligatorieta);
								}
								break;
							case NOME:
								if (destVantaggioBean.getTipoPersona().equalsIgnoreCase("fisica")) {
									if (cell != null && formulaEvaluator.evaluateInCell(row.getCell(c)).getCellType() != Cell.CELL_TYPE_BLANK) {
											destVantaggioBean.setNome(cell.getStringCellValue());									
										} else {
											checkObbligatorieta(CelleExcelBeneficiariEnum.NOME, mappaObbligatorieta);
										}		
								}															
								break;
							case COGNOME:
								if (destVantaggioBean.getTipoPersona().equalsIgnoreCase("fisica")) {
									if (cell != null && formulaEvaluator.evaluateInCell(row.getCell(c)).getCellType() != Cell.CELL_TYPE_BLANK) {
											destVantaggioBean.setCognome(cell.getStringCellValue());									
										} else {
											checkObbligatorieta(CelleExcelBeneficiariEnum.COGNOME, mappaObbligatorieta);
										}		
								}								
								break;
							case RAGIONE_SOCIALE:
								if (destVantaggioBean.getTipoPersona().equalsIgnoreCase("giuridica")) {
									if (cell != null && formulaEvaluator.evaluateInCell(row.getCell(c)).getCellType() != Cell.CELL_TYPE_BLANK) {
											destVantaggioBean.setRagioneSociale(cell.getStringCellValue());										
										} else {
											checkObbligatorieta(CelleExcelBeneficiariEnum.RAGIONE_SOCIALE, mappaObbligatorieta);
										}		
								}					
								break;
							case CF_PIVA:
								if (cell != null && formulaEvaluator.evaluateInCell(row.getCell(c)).getCellType() != Cell.CELL_TYPE_BLANK) {
									String codiceFiscalePIva ="";
									cell.setCellType(Cell.CELL_TYPE_STRING); /*forzo il valore della cella a String cosi da non avere problemi di lettura*/
									
									if(formulaEvaluator.evaluateInCell(cell).getCellType()==Cell.CELL_TYPE_NUMERIC) {
										codiceFiscalePIva = (String.valueOf((int) Math.round(cell.getNumericCellValue())));
									}else {
										codiceFiscalePIva = (cell.getStringCellValue());
									}
									
									/*L'export produceva una cella vuota che pero la condizione != Cell.CELL_TYPE_BLANK non riconosceva 
									 * quindi aggiungo questo controllo in piu */
									if(StringUtils.isNotBlank(codiceFiscalePIva)) {
										// quando scelgo persona giuridica se è un professionista nel CF/P. IVA devo poter mettere CF di persona
										if (destVantaggioBean.getTipoPersona().equalsIgnoreCase("giuridica")) {
											RegExp regExp = RegExp.compile(RegExpUtility.codiceFiscalePIVARegExp());
											
											if(regExp.test((String) codiceFiscalePIva)) {
												destVantaggioBean.setCodFiscalePIVA(codiceFiscalePIva);
											}else {
												throw new ValidatorException("Il dato C.F/P.IVA corrispondente alla colonna " + (c + 1)
														+ " non è nel formato corretto");
											}
										} else {
											RegExp regExp = RegExp.compile(RegExpUtility.codiceFiscaleRegExp());
											if(regExp.test((String) codiceFiscalePIva)) {
												destVantaggioBean.setCodFiscalePIVA(codiceFiscalePIva);
											}else {
												throw new ValidatorException("Il dato C.F/P.IVA corrispondente alla colonna " + (c + 1)
														+ " non è nel formato corretto");
											}
										}
									}									
								}else {
									checkObbligatorieta(CelleExcelBeneficiariEnum.CF_PIVA, mappaObbligatorieta);
								}
								break;
							case IMPORTO:
								if (cell != null && formulaEvaluator.evaluateInCell(row.getCell(c)).getCellType() != Cell.CELL_TYPE_BLANK) {									
									String importo = "";
									DecimalFormat format = (DecimalFormat)NumberFormat.getNumberInstance(Locale.ITALIAN);
									format.applyPattern(IMPORTO_PATTERN);
									if(formulaEvaluator.evaluateInCell(cell).getCellType()==XSSFCell.CELL_TYPE_NUMERIC) {
										double valoreImportoDouble = cell.getNumericCellValue();
										importo = String.valueOf(format.format(new Double(valoreImportoDouble)));	
									} else {
										if (cell.getStringCellValue()!=null && !"".equals(cell.getStringCellValue())) {
											String valoreImportoString = cell.getStringCellValue();
											String importoDaValutare = valoreImportoString.replaceAll("\\.", "");
											if (importoDaValutare.contains(",")) {
												importoDaValutare = importoDaValutare.replaceAll(",", ".");
											}
											double parseDoubleImporto = Double.parseDouble(importoDaValutare);
											if(parseDoubleImporto < 0) {
												throw new ValidatorException("Il dato Importo corrispondente alla colonna " + (c + 1)
														+ " deve essere maggiore o uguale di 0");
											}
											importo = format.format(parseDoubleImporto);
										}
									}
									destVantaggioBean.setImporto(importo);
								} else {
									checkObbligatorieta(CelleExcelBeneficiariEnum.IMPORTO, mappaObbligatorieta);

								}
								break;
								
							case PROT_PRIVACY:
								if (cell != null && formulaEvaluator.evaluateInCell(row.getCell(c)).getCellType() != Cell.CELL_TYPE_BLANK) {
									String valueCell = cell.getStringCellValue();
									
									if("si".equalsIgnoreCase(valueCell)) {
										destVantaggioBean.setFlgPrivacy(true);
									}else if("no".equalsIgnoreCase(valueCell)) {
										destVantaggioBean.setFlgPrivacy(false);
									}
									else {
										throw new ValidatorException("Il dato Dati protetti da privacy corrispondente alla colonna " + (c + 1)
												+ " può avere solo valori si o no");
									}								
								}else {
									destVantaggioBean.setFlgPrivacy(false);
								}
								break;
								
							case TIPO:
								if (cell != null && formulaEvaluator.evaluateInCell(row.getCell(c)).getCellType() != Cell.CELL_TYPE_BLANK) {
									cell.setCellType(Cell.CELL_TYPE_STRING); /*forzo il valore della cella a String cosi da non avere problemi di lettura */

									if (StringUtils.isNotBlank(cell.getStringCellValue())) {
										String valueCell = cell.getStringCellValue();

										if ("mandatario".equalsIgnoreCase(valueCell)
												|| "mandante".equalsIgnoreCase(valueCell)) {
											destVantaggioBean.setTipo(valueCell);
										} else {
											throw new ValidatorException("Il dato Tipo corrispondente alla colonna "
													+ (c + 1) + " può avere solo valori mandatario o mandante");
										}
									}else {
										checkObbligatorieta(CelleExcelBeneficiariEnum.TIPO, mappaObbligatorieta);
									}
								}else {
									checkObbligatorieta(CelleExcelBeneficiariEnum.TIPO, mappaObbligatorieta);
								}
								break;
							}
						}
						
						listaDestVantaggioBean.add(destVantaggioBean);
						
					} catch (Exception e) {
						ErroreRigaExcelBean erroreRiga = new ErroreRigaExcelBean();
						erroreRiga.setNumeroRiga(String.valueOf(r + 1));
						erroreRiga.setMotivo(e.getMessage());

						listaRigheInErrore.add(erroreRiga);
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
		
		result.setListaDestVantaggioBean(listaDestVantaggioBean);
		result.setListaErroreRigaExcelBean(listaRigheInErrore);

		return result;
	}
	
	public static boolean isRowEmpty(Row row) {
	    for (int c = row.getFirstCellNum(); c < row.getLastCellNum(); c++) {
	        Cell cell = row.getCell(c);
	        if (cell != null && cell.getCellType() != Cell.CELL_TYPE_BLANK)
	            return false;
	    }
	    return true;
	}
	
	private void checkObbligatorieta(CelleExcelBeneficiariEnum cellaExcel, HashMap<String, String> mappaObbligatorieta) throws ValidatorException {
		String nomeCella = cellaExcel.getNomeCella();
		boolean flgObbligatorio =  StringUtils.isNotBlank(mappaObbligatorieta.get(nomeCella)) && "true".equalsIgnoreCase(mappaObbligatorieta.get(nomeCella)) ? true : false;
		
		if(flgObbligatorio) {
			throw new ValidatorException("Il dato " + nomeCella +" corrispondente alla colonna " + (CelleExcelBeneficiariEnum.getIndexFromCell(cellaExcel) + 1) + " è obbligatorio");
		}
	}

	@Override
	public PaginatorBean<DatiContabiliBean> fetch(AdvancedCriteria criteria, Integer startRow, Integer endRow,
			List<OrderByBean> orderby) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}
}
