/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.BufferedInputStream;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.StringWriter;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.xml.bind.JAXBException;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.validator.ValidatorException;
import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.BeansException;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.xedge.jquery.client.handlers.ReturnIntegerGetIndexValueHandler;

import it.eng.auriga.database.store.dmpk_bmanager.bean.DmpkBmanagerCaricacontfoglioximportBean;
import it.eng.auriga.database.store.dmpk_bmanager.bean.DmpkBmanagerIufoglioximportBean;
import it.eng.auriga.database.store.result.bean.StoreResultBean;
import it.eng.auriga.module.business.beans.AurigaLoginBean;
import it.eng.auriga.module.business.dao.beans.PreferenceBean;
import it.eng.auriga.ui.module.layout.server.RegExpUtility;
import it.eng.auriga.ui.module.layout.server.caricamentorubriche.datasource.bean.CampoCaricamentoBean;
import it.eng.auriga.ui.module.layout.server.caricamentorubriche.datasource.bean.CaricamentoRubricaMappingBean;
import it.eng.auriga.ui.module.layout.server.caricamentorubriche.datasource.bean.CaricamentoRubricaMappingsBean;
import it.eng.auriga.ui.module.layout.server.caricamentorubriche.datasource.bean.XlsColumnRemapping;
import it.eng.auriga.ui.module.layout.server.pratiche.nuovapropostaatto2.datasource.bean.ErroreRigaExcelBean;
import it.eng.auriga.ui.module.layout.server.protocollazione.datasource.bean.CaricamentoDestinatariExcelBean;
import it.eng.bean.ExecutionResultBean;
import it.eng.client.DaoTUserPreferences;
import it.eng.client.DmpkBmanagerCaricacontfoglioximport;
import it.eng.client.DmpkBmanagerIufoglioximport;
import it.eng.client.SalvataggioFile;
import it.eng.core.business.TFilterFetch;
import it.eng.core.business.TPagingList;
import it.eng.document.function.bean.FileSavedIn;
import it.eng.document.function.bean.FileSavedOut;
import it.eng.jaxb.context.SingletonJAXBContext;
import it.eng.jaxb.variabili.Lista;
import it.eng.jaxb.variabili.Lista.Riga;
import it.eng.jaxb.variabili.Lista.Riga.Colonna;
import it.eng.spring.utility.SpringAppContext;
import it.eng.utility.module.config.StorageImplementation;
import it.eng.utility.ui.module.core.server.bean.AdvancedCriteria;
import it.eng.utility.ui.module.core.server.bean.OrderByBean;
import it.eng.utility.ui.module.core.server.bean.PaginatorBean;
import it.eng.utility.ui.module.core.server.datasource.AbstractDataSource;
import it.eng.utility.ui.module.core.server.datasource.annotation.Datasource;
import it.eng.utility.ui.module.core.server.service.ErrorBean;
import it.eng.utility.ui.user.AurigaUserUtil;


@Datasource(id = "CaricamentoDestinatariExcelDatasource")
public class CaricamentoDestinatariExcelDatasource
		extends AbstractDataSource<CaricamentoDestinatariExcelBean, CaricamentoDestinatariExcelBean> {

	protected Logger logger = Logger.getLogger(CaricamentoDestinatariExcelDatasource.class);

	protected Gson gson = new Gson();

	protected SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yy HH:mm:ss");
	
	private static final int CELLA_TIPO = 0;
	private static final int CELLA_DENOMINAZIONE_COGNOME = 1;
	private static final int CELLA_NOME = 2;
	private static final int CELLA_COD_FISCALE = 3;
	private static final int CELLA_PIVA = 4;
	private static final int CELLA_MEZZO_TRASMISSIONE = 6;
	private static final int CELLA_EMAIL = 7;
	private static final int CELLA_CAP = 13;
	private static final int CELLA_INDIRIZZO_RUBRICA = 16;
	private static final int CELLA_ASSEGNAZIONE_CC = 17;

	
	

	@Override
	public CaricamentoDestinatariExcelBean get(CaricamentoDestinatariExcelBean bean) throws Exception {

		return null;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public CaricamentoDestinatariExcelBean add(CaricamentoDestinatariExcelBean data) throws Exception {

		ExecutionResultBean result = saveDocument(data);
		
		CaricamentoDestinatariExcelBean retValue = new CaricamentoDestinatariExcelBean();
		retValue.setMessage(result.getMessage());
		
		if (result.isSuccessful()) {			
			retValue = saveDocumentRows(data, ((DmpkBmanagerIufoglioximportBean) result.getResult()).getIdfoglioio());
			retValue.setIdFoglio(((DmpkBmanagerIufoglioximportBean) result.getResult()).getIdfoglioio());
		}

		return retValue;
	}

	@SuppressWarnings("rawtypes")
	private CaricamentoDestinatariExcelBean saveDocumentRows(CaricamentoDestinatariExcelBean data, String documentId) {

		CaricamentoDestinatariExcelBean retValue = new CaricamentoDestinatariExcelBean();
		retValue.setSuccessful(true);

		String mimeType = data.getMimetype();

		boolean isXls = mimeType.equals("application/excel");
		boolean isXlsx = mimeType.equals("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");

		if (isXls) {

			retValue = saveXls(data, documentId);

		} else if (isXlsx) {

			retValue = saveXlsx(data, documentId);

		} else {

			String message = String.format(
					"Il formato %1$s del documento %2$s non è supportato, solo xls e xlsx sono ammessi come documenti validi",
					data.getMimetype(), documentId);
			logger.error(message);

			retValue.setSuccessful(false);
			retValue.setMessage(message);

			updateDocumentStateError(data.getMimetype(), documentId, message);

		}

		return retValue;
	}

	/**
	 * Salvataggio in db di tutte le righe aventi contenuto non nullo e non
	 * vuoto, almeno una cella della riga viene valorizzata, a partire dal
	 * documento xlsx di cui è stato fornito l'identificativo
	 * 
	 * @param data
	 *            mappature campi/colonne
	 * @param documentId
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private CaricamentoDestinatariExcelBean saveXlsx(CaricamentoDestinatariExcelBean data, String documentId) {

		CaricamentoDestinatariExcelBean retValue = new CaricamentoDestinatariExcelBean();
		List<ErroreRigaExcelBean> listaRigheInErrore = new ArrayList<ErroreRigaExcelBean>();

		BufferedInputStream documentStream = null;
		InputStream is = null;
		int numRigheDestinatari = 0;
		
		
		try {

			File document = StorageImplementation.getStorage().getRealFile(data.getUri());

			is = new FileInputStream(document);
			documentStream = new BufferedInputStream(is);

			XSSFWorkbook wb = new XSSFWorkbook(documentStream);

			ExecutionResultBean result = populateDettagliColonne(data);

			@SuppressWarnings("unchecked")
			List<XlsColumnRemapping> cellReferences = (List<XlsColumnRemapping>) result
					.getAdditionalInformation("cellReferences");

			Lista dettagliColonne = (Lista) result.getAdditionalInformation("dettagliColonne");

			Lista xmlContenuti = new Lista();

			for (int sheetIndex = 0; sheetIndex < wb.getNumberOfSheets(); sheetIndex++) {

					XSSFSheet currentSheet = wb.getSheetAt(sheetIndex);

					// verifico se ci sono righe con celle valorizzate
					int nonEmptyRows = currentSheet.getPhysicalNumberOfRows();
					
					numRigheDestinatari = nonEmptyRows;

					if (nonEmptyRows > 0) {

						// la prima riga è di intestazione
						// la condizione su nonEmptyRows mi evita di scansionare
						// il numero massimo di righe che possono essere
						// presenti
						// nel foglio
						for (int rowIndex = currentSheet.getFirstRowNum(); rowIndex <= currentSheet.getLastRowNum()
								&& nonEmptyRows > 0; rowIndex++) {

							Row row = currentSheet.getRow(rowIndex);
							
							if (row.getCell(0) != null
									&& row.getCell(0).getCellType()!=Cell.CELL_TYPE_BLANK
									&& row.getCell(0).getStringCellValue().equalsIgnoreCase("Tipo")) {
								
								numRigheDestinatari = nonEmptyRows-1;
								
								continue;
							}

							if (row != null) {

								retValue = populateRiga(cellReferences, xmlContenuti, nonEmptyRows, row);

								if (retValue.isSuccessful()) {

									Riga currentRiga = (Riga) retValue.getResult();

									if (currentRiga != null) {
										xmlContenuti.getRiga().add(currentRiga);
									}

								} else {
									ErroreRigaExcelBean erroreRiga = new ErroreRigaExcelBean();
									erroreRiga.setNumeroRiga(String.valueOf(rowIndex + 1));
									erroreRiga.setMotivo(retValue.getMessage());

									listaRigheInErrore.add(erroreRiga);
								}
							}
						}
					}
				}
				
				if(listaRigheInErrore!=null && listaRigheInErrore.size()>0) {
					
					retValue.setMessage("Errore durante l'elaborazione del file Excel, dati in formato non valido");
					retValue.setSuccessful(false);
					retValue.setNumRigheDestinatari(String.valueOf(numRigheDestinatari));
					retValue.setListaExcelDatiInError(listaRigheInErrore);
					
					updateDocumentStateError(data.getMimetype(), documentId, "Errore durante l'elaborazione del file Excel, dati in formato non valido");
				}else {
					retValue = caricaFoglio(xmlContenuti, dettagliColonne, documentId);
				}		

		} catch (Exception e) {

			String message = "Durante il caricamento delle righe del documento %1$s si è verificata la seguente eccezione %2$s";

			String exceptionMessage = e.getMessage() != null ? e.getMessage() : e.toString();

			String infoMessage = String.format(message, documentId, exceptionMessage);

			logger.error(String.format(message, documentId, ExceptionUtils.getFullStackTrace(e)));

			updateDocumentStateError(data.getMimetype(), documentId, infoMessage);

			retValue.setSuccessful(false);
			retValue.setMessage(infoMessage);

		} finally {
			if(is != null) {
				try {
					is.close();
				} catch (Exception e) {}
			}
			if(documentStream != null) {
				try {
					documentStream.close();
				} catch (Exception e) {
					logger.error(String.format(
							"Impossibile chiudere lo stream legato al documento %1$s a causa della seguente eccezione ",
							documentId), e);
				}
			}
		}

		return retValue;
	}

	/**
	 * @param cellReferences
	 * @param xmlContenuti
	 * @param nonEmptyRows
	 * @param row
	 * @throws NumberFormatException
	 * @throws IndexOutOfBoundsException
	 */
	@SuppressWarnings("rawtypes")
	protected CaricamentoDestinatariExcelBean populateRiga(List<XlsColumnRemapping> cellReferences, Lista xmlContenuti,
			int nonEmptyRows, Row row) throws NumberFormatException, IndexOutOfBoundsException {

		CaricamentoDestinatariExcelBean retValue = new CaricamentoDestinatariExcelBean();
		retValue.setSuccessful(Boolean.TRUE);

		Riga riga = new Riga();

		// mi permette di determinare se almeno una delle
		// colonne della riga è valorizzata. Questo perchè le
		// righe
		// potrebbero essere valorizzate a scacchiera, oppure
		// valorizzate e poi cancellate (selezionando una riga e
		// premendo canc il contenuto viene valorizzzato con "")
		boolean saveRiga = false;

		// verifico se la riga ha celle valorizzate
		if (row.getPhysicalNumberOfCells() > 0) {

			for (XlsColumnRemapping cellReference : cellReferences) {

				int cellColumnIndex = Integer.valueOf(cellReference.getIndex().getContent()) - 1;
				Cell cell = row.getCell(cellColumnIndex);
				
				String value = "";

				if (cell != null) {
					String cellFieldName = cellReference.getFieldName();
					int rowNum = row.getRowNum();
					String fieldType = cellReference.getFieldType().getContent();
					
					try {
						/**
						 * IL TIPO DATA E NUMERO VENGONO RICONOSCIUTI COME CELL_TYPE_NUMERIC
						 */
						if (row.getCell(cellColumnIndex).getCellType() == Cell.CELL_TYPE_NUMERIC) {
							/**
							 * VERIFICA SE E' UNA DATA VALIDA 
							 */
							if (fieldType.equals("D")) {
								try {																				
									Date dateValue = cell.getDateCellValue();

									if (dateValue != null) {
										value = dateFormat.format(dateValue);
									}														    
								} catch (Exception e) {
									String message = String.format(
									"Data non valida per la colonna %1$s, nome campo %2$s, riga numero %3$s",
									cellColumnIndex, cellFieldName, rowNum);
								}
							} else {
								/**
								 * VERIFICA SE E' UN NUMERO
								 */
								try {
//									value = String.valueOf(cell.getNumericCellValue());
									value = new DecimalFormat("#").format(cell.getNumericCellValue());
									value = value.replace(".", ",");
								} catch (Exception e1) {
		
									value = cell.getStringCellValue();
									value = value.replace(".", ",");
								}		
							}								
						} else if (row.getCell(cellColumnIndex).getCellType() == Cell.CELL_TYPE_STRING) {							
							value = cell.getStringCellValue();
						} else if (row.getCell(cellColumnIndex).getCellType() == Cell.CELL_TYPE_BLANK) {							
							value = cell.getStringCellValue();						
						}					
					} catch (Exception e) {

						String message = String.format(
								"Dato non valido per la colonna %1$s, nome campo %2$s, tipo %4$s, controllare il formato della cella",
								cellColumnIndex, cellFieldName, rowNum, fieldType);

						retValue.setMessage(message);
						retValue.setSuccessful(false);

						break;
					}

					if (StringUtils.isNotBlank(value)) {
						saveRiga = true;
					}
				}
				
				
				try {
					checkVincoli(cellColumnIndex, row);
				} catch (ValidatorException e) {
					
					retValue.setMessage(e.getMessage());
					retValue.setSuccessful(false);

					break;
				}
				

				Colonna colValue = new Colonna();
				colValue.setNro(new BigInteger(cellReference.getIndex().getContent()));
				colValue.setContent(value);
				riga.getColonna().add(colValue);

			}
		}

		if (saveRiga) {

			retValue.setResult(riga);
		}
		--nonEmptyRows;

		return retValue;

	}
	
	private void checkVincoli(int cellColumnIndex, Row row) throws ValidatorException {
		
		try {
			Cell cell = row.getCell(cellColumnIndex);
			
			switch (cellColumnIndex) {

			case CELLA_TIPO:
				if (cell != null && cell.getCellType()!=Cell.CELL_TYPE_BLANK) {
					if(cell.getStringCellValue().equalsIgnoreCase("Persona fisica") ||
						cell.getStringCellValue().equalsIgnoreCase("Persona giuridica") ||	
						cell.getStringCellValue().equalsIgnoreCase("PA o sua articolazione") ||
						cell.getStringCellValue().equalsIgnoreCase("U.O. interna") ||
						cell.getStringCellValue().equalsIgnoreCase("Unità di personale")) {
						
					}else {
						throw new ValidatorException("Il dato Tipo corrispondente alla colonna " + (cellColumnIndex + 1)
								+ " deve essere uguale ad uno dei seguenti valori: "
								+ "Persona fisica, "
								+ "Persona giuridica, "
								+ "PA o sua articolazione, "
								+ "U.O. interna, "
								+ "Unità di personale"						
								);
					}
				}
				break;
			case CELLA_DENOMINAZIONE_COGNOME:
				if (cell != null && cell.getCellType()!=Cell.CELL_TYPE_BLANK) {
				} else {
					throw new ValidatorException("Il dato Denominazione/Cognome corrispondente alla colonna " + (cellColumnIndex + 1)
							+ " è obbligatorio");
				}
				break;
			case CELLA_NOME:
				Cell cellTipo = row.getCell(CELLA_TIPO);
				if (cellTipo != null && cellTipo.getCellType()!=Cell.CELL_TYPE_BLANK && "Persona fisica".equalsIgnoreCase(cellTipo.getStringCellValue())) {
					if (cell != null && cell.getCellType()!=Cell.CELL_TYPE_BLANK) {
					} else {
						throw new ValidatorException("Il dato Nome corrispondente alla colonna " + (cellColumnIndex + 1)
								+ " è obbligatorio");
					}
				}
				break;
			case CELLA_COD_FISCALE:
				if (cell != null && cell.getCellType()!=Cell.CELL_TYPE_BLANK) {
					Pattern pattern = Pattern.compile(RegExpUtility.codiceFiscaleRegExp(getSession()));
					Matcher matcher = pattern.matcher(cell.getStringCellValue());

					if(!matcher.matches()) {
						throw new ValidatorException("Il dato Cod. fiscale corrispondente alla colonna " + (cellColumnIndex + 1)
								+ " non è nella forma corretta");
					}
				}
				break;
			case CELLA_PIVA:
				if (cell != null && cell.getCellType()!=Cell.CELL_TYPE_BLANK) {
					Pattern pattern = Pattern.compile(RegExpUtility.partitaIvaRegExp(getSession()));					
					String value = "";
					if(row.getCell(cellColumnIndex).getCellType() == Cell.CELL_TYPE_NUMERIC) {
						value = new DecimalFormat("#").format(cell.getNumericCellValue());					
					}else {
						value = cell.getStringCellValue();
					}
					
					Matcher matcher = pattern.matcher(value);

					if(!matcher.matches()) {
						throw new ValidatorException("Il dato P. IVA corrispondente alla colonna " + (cellColumnIndex + 1)
								+ " non è nella forma corretta");
					}
				}
				break;
			case CELLA_MEZZO_TRASMISSIONE:
				
				/*TODO CAPIRE VALORI AMMESSI DA MEZZO TRASMISSIONE*/
//			if (cell != null && cell.getCellType()!=Cell.CELL_TYPE_BLANK) {
//			} else {
//				throw new ValidatorException("Il dato Denominazione/Cognome corrispondente alla colonna " + (cellColumnIndex + 1)
//						+ " è obbligatorio");
//			}
				break;
			case CELLA_EMAIL:
//				if (cell != null && cell.getCellType()!=Cell.CELL_TYPE_BLANK) {
//					Pattern pattern = Pattern.compile(RegExpUtility.indirizzoEmailRegExp(getSession()));
//					Matcher matcher = pattern.matcher(cell.getStringCellValue());
//	
//					if(!matcher.matches()) {
//						throw new ValidatorException("Il dato e-mail corrispondente alla colonna " + (cellColumnIndex + 1)
//								+ " non è nella forma corretta");
//					}
//				}
				break;
			case CELLA_CAP:
				if (cell != null && cell.getCellType()!=Cell.CELL_TYPE_BLANK) {
					Pattern pattern = Pattern.compile("[0-9]{5}");
					String value = "";
					if(row.getCell(cellColumnIndex).getCellType() == Cell.CELL_TYPE_NUMERIC) {
						value = new DecimalFormat("#").format(cell.getNumericCellValue());					
					}else {
						value = cell.getStringCellValue();
					}
					Matcher matcher = pattern.matcher(value);

					if(!matcher.matches()) {
						throw new ValidatorException("Il dato CAP corrispondente alla colonna " + (cellColumnIndex + 1)
								+ " non è nella forma corretta");
					}
				}
				break;
			case CELLA_INDIRIZZO_RUBRICA:
				if (cell != null && cell.getCellType()!=Cell.CELL_TYPE_BLANK) {
					if(cell.getStringCellValue().equalsIgnoreCase("Sede legale") ||
						cell.getStringCellValue().equalsIgnoreCase("Residenza") ||	
						cell.getStringCellValue().equalsIgnoreCase("Domicilio") ||
						cell.getStringCellValue().equalsIgnoreCase("Recapito") ||
						cell.getStringCellValue().equalsIgnoreCase("Sede Operativa")) {
						
					}else {
						throw new ValidatorException("Il dato Indirizzo in rubrica corrispondente alla colonna " + (cellColumnIndex + 1)
								+ " deve essere uguale ad uno dei seguenti valori: "
								+ "Sede legale, "
								+ "Residenza, "
								+ "Domicilio, "
								+ "Recapito, "
								+ "Sede Operativa"						
								);
					}
				}
				break;
			case CELLA_ASSEGNAZIONE_CC:
				if (cell != null && cell.getCellType()!=Cell.CELL_TYPE_BLANK) {
					if(cell.getStringCellValue().equalsIgnoreCase("effettua assegnazione") ||
						cell.getStringCellValue().equalsIgnoreCase("c.c.")) {					
					}else {
						throw new ValidatorException("Il dato Effettua assegnazione/c.c. corrispondente alla colonna " + (cellColumnIndex + 1)
								+ " deve essere uguale ad uno dei seguenti valori: "
								+ "effettua assegnazione, "
								+ "c.c."
								);
					}
				}
				break;
			}
		} catch (ValidatorException e) {
			throw e;
		} catch (Exception ex) {
			throw new ValidatorException("Errore durante la verifica dei vincoli: " + ex.getMessage());
		}
	}
	
	
	
	public boolean isValid(String dateStr) {
        DateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        sdf.setLenient(false);
        try {
            sdf.parse(dateStr);
        } catch (ParseException e) {
            return false;
        }
        return true;
    }

	/**
	 * Salvataggio in db di tutte le righe aventi contenuto non nullo e non
	 * vuoto, almeno una cella della riga viene valorizzata, a partire dal
	 * documento xls di cui è stato fornito l'identificativo
	 * 
	 * @param data
	 *            mappature campi/colonne
	 * @param documentId
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private CaricamentoDestinatariExcelBean saveXls(CaricamentoDestinatariExcelBean data, String documentId) {

		CaricamentoDestinatariExcelBean retValue = new CaricamentoDestinatariExcelBean();
		List<ErroreRigaExcelBean> listaRigheInErrore = new ArrayList<ErroreRigaExcelBean>();
		
		BufferedInputStream documentStream = null;
		InputStream is = null;
		
		int numRigheDestinatari = 0;
		try {

			File document = StorageImplementation.getStorage().getRealFile(data.getUri());

			is = new FileInputStream(document);
			documentStream = new BufferedInputStream(is);

			HSSFWorkbook wb = new HSSFWorkbook(documentStream);

			ExecutionResultBean result = populateDettagliColonne(data);

			@SuppressWarnings("unchecked")
			List<XlsColumnRemapping> cellReferences = (List<XlsColumnRemapping>) result
					.getAdditionalInformation("cellReferences");

			Lista dettagliColonne = (Lista) result.getAdditionalInformation("dettagliColonne");

			Lista xmlContenuti = new Lista();

			badRow: {

				for (int sheetIndex = 0; sheetIndex < wb.getNumberOfSheets(); sheetIndex++) {

					HSSFSheet currentSheet = wb.getSheetAt(sheetIndex);

					// verifico se ci sono righe con celle valorizzate
					int nonEmptyRows = currentSheet.getPhysicalNumberOfRows();
					
					numRigheDestinatari = nonEmptyRows;

					if (nonEmptyRows > 0) {
				
						for (int rowIndex = currentSheet.getFirstRowNum(); rowIndex <= currentSheet.getLastRowNum()
								&& nonEmptyRows > 0; rowIndex++) {

							Row row = currentSheet.getRow(rowIndex);
							
							if (row.getCell(0) != null
									&& row.getCell(0).getCellType()!=Cell.CELL_TYPE_BLANK
									&& row.getCell(0).getStringCellValue().equalsIgnoreCase("Tipo")) {
								
								numRigheDestinatari = nonEmptyRows-1;
								
								continue;
							}

							if (row != null) {

								retValue = populateRiga(cellReferences, xmlContenuti, nonEmptyRows, row);

								if (retValue.isSuccessful()) {

									Riga currentRiga = (Riga) retValue.getResult();

									if (currentRiga != null) {
										xmlContenuti.getRiga().add(currentRiga);
									}

								} else {
									ErroreRigaExcelBean erroreRiga = new ErroreRigaExcelBean();
									erroreRiga.setNumeroRiga(String.valueOf(rowIndex + 1));
									erroreRiga.setMotivo(retValue.getMessage());

									listaRigheInErrore.add(erroreRiga);
								}
							}
						}
					}
				}

				if(listaRigheInErrore!=null && listaRigheInErrore.size()>0) {
					
					retValue.setMessage("Errore durante l'elaborazione del file Excel, dati in formato non valido");
					retValue.setNumRigheDestinatari(String.valueOf(numRigheDestinatari));
					retValue.setSuccessful(false);
					retValue.setListaExcelDatiInError(listaRigheInErrore);
					
					updateDocumentStateError(data.getMimetype(), documentId, "Errore durante l'elaborazione del file Excel, dati in formato non valido");
				}else {
					retValue = caricaFoglio(xmlContenuti, dettagliColonne, documentId);
				}

			}
		} catch (Exception e) {

			String message = "Durante il caricamento delle righe del documento %1$s si è verificata la seguente eccezione %2$s";

			String exceptionMessage = e.getMessage() != null ? e.getMessage() : e.getCause().getMessage();

			String infoMessage = String.format(message, documentId, exceptionMessage);

			logger.error(String.format(message, documentId, ExceptionUtils.getFullStackTrace(e)));

			updateDocumentStateError(data.getMimetype(), documentId, infoMessage);

			retValue.setSuccessful(false);
			retValue.setMessage(infoMessage);

		} finally {
			if(is != null) {
				try {
					is.close();
				} catch (Exception e) {}
			}
			if(documentStream != null) {
				try {
					documentStream.close();
				} catch (Exception e) {
					logger.error(String.format(
							"Impossibile chiudere lo stream legato al documento %1$s a causa della seguente eccezione ",
							documentId), e);
				}
			}
		}

		return retValue;
	}

	/**
	 * Aggiorna il documento con lo stato "Elaborato con errori"
	 * 
	 * @param documentId
	 * @param format
	 */
	private void updateDocumentStateError(String mimeType, String documentId, String format) {

		DmpkBmanagerIufoglioximport client = new DmpkBmanagerIufoglioximport();

		DmpkBmanagerIufoglioximportBean updateBean = new DmpkBmanagerIufoglioximportBean();

		updateBean.setCodidconnectiontokenin(AurigaUserUtil.getLoginInfo(getSession()).getToken());
		updateBean.setIdfoglioio(documentId);

		String idUserLavoro = AurigaUserUtil.getLoginInfo(getSession()).getIdUserLavoro();

		if (idUserLavoro != null) {
			updateBean.setIduserlavoroin(new BigDecimal(idUserLavoro));
		}

		updateBean.setStatoin("elaborato_con_errori");
		updateBean.setTipocontenutoin(mimeType);

		try {
			client.execute(getLocale(), AurigaUserUtil.getLoginInfo(getSession()), updateBean);
		} catch (Exception e) {

			logger.error(String.format(
					"Durante l'aggiornamento del record del documento %1$s, si è verificata la seguente eccezione %2$s",
					documentId, ExceptionUtils.getFullStackTrace(e)));

		}
	}

	@SuppressWarnings("rawtypes")
	private CaricamentoDestinatariExcelBean caricaFoglio(Lista xmlContenuti, Lista dettagliColonne, String documentId) {

		CaricamentoDestinatariExcelBean retValue = new CaricamentoDestinatariExcelBean();
		retValue.setSuccessful(true);

		DmpkBmanagerCaricacontfoglioximport caricaFoglio = new DmpkBmanagerCaricacontfoglioximport();

		DmpkBmanagerCaricacontfoglioximportBean caricaBean = new DmpkBmanagerCaricacontfoglioximportBean();

		try {

			BigDecimal idUserLavoro = AurigaUserUtil.getLoginInfo(getSession()).getIdUserLavoro() != null
					? new BigDecimal(AurigaUserUtil.getLoginInfo(getSession()).getIdUserLavoro()) : null;

			caricaBean.setCodidconnectiontokenin(AurigaUserUtil.getLoginInfo(getSession()).getToken());

			caricaBean.setIduserlavoroin(idUserLavoro);

			caricaBean.setIdfoglioin(documentId);

			caricaBean.setXmlcontenutiin(marshal(xmlContenuti));

			caricaBean.setXmldettcolonnein(marshal(dettagliColonne));

			StoreResultBean<DmpkBmanagerCaricacontfoglioximportBean> result = caricaFoglio.execute(getLocale(),
					AurigaUserUtil.getLoginInfo(getSession()), caricaBean);

			if (result.getDefaultMessage() != null && !result.getDefaultMessage().isEmpty()) {

				String message = String.format(
						"Durante il caricamento delle righe relative al documento %1$s, si è verificata la seguente eccezione %2$s",
						documentId, result.getDefaultMessage());

				retValue.setMessage(message);
				retValue.setSuccessful(false);

				logger.error(message);
			}

		} catch (Exception e) {

			String message = "Durante il salvataggio dei dati del documento %1$s, si è verificata la seguente eccezione %2$s";

			String cause = e.getMessage() != null ? e.getMessage() : e.getCause().getMessage();

			logger.error(String.format(message, documentId, ExceptionUtils.getFullStackTrace(e)));

			retValue.setMessage(cause);
			retValue.setSuccessful(false);
		}

		return retValue;
	}

	protected String marshal(Lista objectsList) throws JAXBException {

		StringWriter stringWriter = new StringWriter();

		BufferedWriter bufferedWriter = new BufferedWriter(stringWriter);

		SingletonJAXBContext.getInstance().createMarshaller().marshal(objectsList, bufferedWriter);

		return stringWriter.toString();
	}

	/**
	 * @param data
	 * @param cellReferences
	 * @throws IndexOutOfBoundsException
	 */
	@SuppressWarnings("rawtypes")
	protected ExecutionResultBean populateDettagliColonne(CaricamentoDestinatariExcelBean data) {

		ExecutionResultBean result = new ExecutionResultBean();

		int colIndex = 1;

		Lista dettagliColonne = new Lista();

		List<XlsColumnRemapping> cellReferences = new ArrayList<XlsColumnRemapping>();
		
		List<CampoCaricamentoBean> listaCampiDestinatariCaricamento = getListaCampiDestinatariCaricamento();

		for (CampoCaricamentoBean currentCampoCaricamento : listaCampiDestinatariCaricamento) {

			Riga riga = new Riga();
			Colonna[] cols = new Colonna[3];

			// identificativo della colonna che permette di referenziare gli
			// oggetti presenti nelle due Lista, quella che identifica le
			// colonne e quella che contiene i dati
			Colonna index = new Colonna();
			index.setContent(String.valueOf(colIndex));
			index.setNro(new BigInteger("1"));
			cols[0] = index;
			colIndex++;
			riga.getColonna().add(index);

			// colonna associata al campo
			Colonna field = new Colonna();
			cols[1] = field;
			field.setContent(currentCampoCaricamento.getNomeCampo());
			field.setNro(new BigInteger("2"));
			riga.getColonna().add(field);

			// tipo di campo, "S"tringa, "N"umerico o "D"ata, viene popolato a
			// posteriori durante la scansione delle righe
			Colonna fieldType = new Colonna();
			cols[2] = fieldType;
			fieldType.setContent(currentCampoCaricamento.getType());
			fieldType.setNro(new BigInteger("3"));
			riga.getColonna().add(fieldType);

			dettagliColonne.getRiga().add(riga);

			cellReferences.add(new XlsColumnRemapping(currentCampoCaricamento.getNomeCampo(),
					currentCampoCaricamento.getColonnaRif(), index, fieldType));
		}

		result.setSuccessful(true);
		result.addAdditionalInformation("dettagliColonne", dettagliColonne);
		result.addAdditionalInformation("cellReferences", cellReferences);

		return result;
	}

	private List<CampoCaricamentoBean> getListaCampiDestinatariCaricamento() {
		
		List<CampoCaricamentoBean> listaCampoCaricamentoBean = new ArrayList<CampoCaricamentoBean>();
		
		CampoCaricamentoBean campoCaricamentoBean1 = new CampoCaricamentoBean();
		campoCaricamentoBean1.setColonnaRif("A");
		campoCaricamentoBean1.setNomeCampo("Tipo");
		campoCaricamentoBean1.setType("S");
		listaCampoCaricamentoBean.add(campoCaricamentoBean1);		
		
		CampoCaricamentoBean campoCaricamentoBean2 = new CampoCaricamentoBean();
		campoCaricamentoBean2.setColonnaRif("B");
		campoCaricamentoBean2.setNomeCampo("Denominazone_Cogome");
		campoCaricamentoBean2.setType("S");
		listaCampoCaricamentoBean.add(campoCaricamentoBean2);
		
		CampoCaricamentoBean campoCaricamentoBean3 = new CampoCaricamentoBean();
		campoCaricamentoBean3.setColonnaRif("C");
		campoCaricamentoBean3.setNomeCampo("Nome");
		campoCaricamentoBean3.setType("S");
		listaCampoCaricamentoBean.add(campoCaricamentoBean3);
		
		CampoCaricamentoBean campoCaricamentoBean4 = new CampoCaricamentoBean();
		campoCaricamentoBean4.setColonnaRif("D");
		campoCaricamentoBean4.setNomeCampo("CodFiscale");
		campoCaricamentoBean4.setType("S");
		listaCampoCaricamentoBean.add(campoCaricamentoBean4);
		
		CampoCaricamentoBean campoCaricamentoBean5 = new CampoCaricamentoBean();
		campoCaricamentoBean5.setColonnaRif("E");
		campoCaricamentoBean5.setNomeCampo("PIVA");
		campoCaricamentoBean5.setType("S");
		listaCampoCaricamentoBean.add(campoCaricamentoBean5);
		
		CampoCaricamentoBean campoCaricamentoBean6 = new CampoCaricamentoBean();
		campoCaricamentoBean6.setColonnaRif("F");
		campoCaricamentoBean6.setNomeCampo("CodRapido");
		campoCaricamentoBean6.setType("S");
		listaCampoCaricamentoBean.add(campoCaricamentoBean6);
		
		CampoCaricamentoBean campoCaricamentoBean7 = new CampoCaricamentoBean();
		campoCaricamentoBean7.setColonnaRif("G");
		campoCaricamentoBean7.setNomeCampo("MezzoTrasmissione");
		campoCaricamentoBean7.setType("S");
		listaCampoCaricamentoBean.add(campoCaricamentoBean7);
		
		CampoCaricamentoBean campoCaricamentoBean8 = new CampoCaricamentoBean();
		campoCaricamentoBean8.setColonnaRif("H");
		campoCaricamentoBean8.setNomeCampo("Email");
		campoCaricamentoBean8.setType("S");
		listaCampoCaricamentoBean.add(campoCaricamentoBean8);
		
		CampoCaricamentoBean campoCaricamentoBean9 = new CampoCaricamentoBean();
		campoCaricamentoBean9.setColonnaRif("I");
		campoCaricamentoBean9.setNomeCampo("ToponimoIndirizzo");
		campoCaricamentoBean9.setType("S");
		listaCampoCaricamentoBean.add(campoCaricamentoBean9);
		
		CampoCaricamentoBean campoCaricamentoBean10 = new CampoCaricamentoBean();
		campoCaricamentoBean10.setColonnaRif("L");
		campoCaricamentoBean10.setNomeCampo("Indirizzo");
		campoCaricamentoBean10.setType("S");
		listaCampoCaricamentoBean.add(campoCaricamentoBean10);
		
		CampoCaricamentoBean campoCaricamentoBean11 = new CampoCaricamentoBean();
		campoCaricamentoBean11.setColonnaRif("M");
		campoCaricamentoBean11.setNomeCampo("NCivico");
		campoCaricamentoBean11.setType("N");
		listaCampoCaricamentoBean.add(campoCaricamentoBean11);
		
		CampoCaricamentoBean campoCaricamentoBean12 = new CampoCaricamentoBean();
		campoCaricamentoBean12.setColonnaRif("N");
		campoCaricamentoBean12.setNomeCampo("AppendiceCivico");
		campoCaricamentoBean12.setType("S");
		listaCampoCaricamentoBean.add(campoCaricamentoBean12);
		
		CampoCaricamentoBean campoCaricamentoBean13 = new CampoCaricamentoBean();
		campoCaricamentoBean13.setColonnaRif("O");
		campoCaricamentoBean13.setNomeCampo("ComuneCitta");
		campoCaricamentoBean13.setType("S");
		listaCampoCaricamentoBean.add(campoCaricamentoBean13);
		
		CampoCaricamentoBean campoCaricamentoBean14 = new CampoCaricamentoBean();
		campoCaricamentoBean14.setColonnaRif("P");
		campoCaricamentoBean14.setNomeCampo("CAP");
		campoCaricamentoBean14.setType("S");
		listaCampoCaricamentoBean.add(campoCaricamentoBean14);
		
		CampoCaricamentoBean campoCaricamentoBean15 = new CampoCaricamentoBean();
		campoCaricamentoBean15.setColonnaRif("Q");
		campoCaricamentoBean15.setNomeCampo("StatoEstero");
		campoCaricamentoBean15.setType("S");
		listaCampoCaricamentoBean.add(campoCaricamentoBean15);
		
		CampoCaricamentoBean campoCaricamentoBean16 = new CampoCaricamentoBean();
		campoCaricamentoBean16.setColonnaRif("R");
		campoCaricamentoBean16.setNomeCampo("Localita");
		campoCaricamentoBean16.setType("S");
		listaCampoCaricamentoBean.add(campoCaricamentoBean16);
		
		CampoCaricamentoBean campoCaricamentoBean17 = new CampoCaricamentoBean();
		campoCaricamentoBean17.setColonnaRif("S");
		campoCaricamentoBean17.setNomeCampo("TipoIndirizzoInRubrica");
		campoCaricamentoBean17.setType("S");
		listaCampoCaricamentoBean.add(campoCaricamentoBean17);
		
		CampoCaricamentoBean campoCaricamentoBean18 = new CampoCaricamentoBean();
		campoCaricamentoBean18.setColonnaRif("T");
		campoCaricamentoBean18.setNomeCampo("Assegnazione_cc");
		campoCaricamentoBean18.setType("S");
		listaCampoCaricamentoBean.add(campoCaricamentoBean18);
			
		
		return listaCampoCaricamentoBean;
		
	}

	/**
	 * Crea un record nella tabella Iufolglioximport relativo al documento da
	 * analizzare
	 * 
	 * @param data
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	private ExecutionResultBean saveDocument(CaricamentoDestinatariExcelBean data) {

		String baseErrorMessage = "Durante la registrazione del documento %1$s - %2$s, si è verificata la seguente eccezione %3$s";

		DmpkBmanagerIufoglioximport documentImport = new DmpkBmanagerIufoglioximport();

		ExecutionResultBean retValue = new ExecutionResultBean();

		try {

			BigDecimal idUserLavoro = AurigaUserUtil.getLoginInfo(getSession()).getIdUserLavoro() != null
					? new BigDecimal(AurigaUserUtil.getLoginInfo(getSession()).getIdUserLavoro()) : null;

			// Recuper il file temp e lo salvo sul repository
			File realFile = StorageImplementation.getStorage().getRealFile(data.getUri());
			FileSavedIn lFileSavedIn = new FileSavedIn();
			lFileSavedIn.setSaved(realFile);
			SalvataggioFile lSalvataggioFile = new SalvataggioFile();
			FileSavedOut savedFileInRepo = lSalvataggioFile.savefile(getLocale(), AurigaUserUtil.getLoginInfo(getSession()), lFileSavedIn);
					
			DmpkBmanagerIufoglioximportBean importBean = new DmpkBmanagerIufoglioximportBean();
			importBean.setCodidconnectiontokenin(AurigaUserUtil.getLoginInfo(getSession()).getToken());

			importBean.setIduserlavoroin(idUserLavoro);
			importBean.setStatoin("da_elaborare");
			importBean.setTipocontenutoin("DESTINATARI_REGISTRAZIONE"); 
			importBean.setUriin(savedFileInRepo.getUri());

			StoreResultBean<DmpkBmanagerIufoglioximportBean> result = documentImport.execute(getLocale(),
					AurigaUserUtil.getLoginInfo(getSession()), importBean);

			retValue.setSuccessful(!result.isInError());

			if (result.isInError()) {

				String message = String.format(baseErrorMessage, data.getUri(), data.getDisplayFileName(),
						result.getDefaultMessage());

				retValue.setMessage(result.getDefaultMessage());

				logger.error(message);

			} else {

				retValue.setSuccessful(true);
				retValue.setResult(result.getResultBean());

			}

		} catch (Exception e) {

			String errorMessage = e.getMessage() != null ? e.getMessage() : e.getCause().getMessage();

			String message = String.format(baseErrorMessage, data.getUri(), data.getDisplayFileName(), errorMessage);
			retValue.setMessage(message);
			retValue.setSuccessful(false);

			logger.error(String.format(baseErrorMessage, data.getUri(), data.getDisplayFileName(),
					ExceptionUtils.getFullStackTrace(e)));
		}

		return retValue;
	}

	/**
	 * @param map
	 * @throws Exception
	 * @throws BeansException
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	protected void saveMappings(Map<String, String> map) throws Exception, BeansException {
		ExecutionResultBean execResult = retrieveMappingsDb();

		PreferenceBean preferenceBean = null;

		if (execResult.isSuccessful()) {

			DaoTUserPreferences daoTUserPreference = new DaoTUserPreferences();

			List<CaricamentoRubricaMappingBean> mappings = (List<CaricamentoRubricaMappingBean>) execResult.getResult();

			if (mappings != null) {

				for (CaricamentoRubricaMappingBean mapping : mappings) {

					mapping.setValue((String) map.get(mapping.getName()));

				}

				preferenceBean = (PreferenceBean) execResult.getAdditionalInformation("preferenceBean");
				preferenceBean.setValue(gson.toJson(mappings, new TypeToken<List<CaricamentoRubricaMappingBean>>() {
				}.getType()));
				daoTUserPreference.update(getLocale(), AurigaUserUtil.getLoginInfo(getSession()), preferenceBean);

			} else {

				mappings = (List<CaricamentoRubricaMappingBean>) SpringAppContext.getContext()
						.getBean("defaultMapping");

				preferenceBean = new PreferenceBean();
				preferenceBean.setPrefKey("CaricamentoRubricaMappings");
				preferenceBean.setPrefName("DEFAULT");
				preferenceBean.setValue(gson.toJson(mappings, new TypeToken<List<CaricamentoRubricaMappingBean>>() {
				}.getType()));
				preferenceBean.setUserid(AurigaUserUtil.getLoginInfo(getSession()).getUserid());
				preferenceBean.setSettingTime(new java.util.Date());

				daoTUserPreference.save(getLocale(), AurigaUserUtil.getLoginInfo(getSession()), preferenceBean);
			}

		}

	}

	@Override
	public CaricamentoDestinatariExcelBean remove(CaricamentoDestinatariExcelBean bean) throws Exception {

		return null;
	}

	@Override
	public CaricamentoDestinatariExcelBean update(CaricamentoDestinatariExcelBean bean, CaricamentoDestinatariExcelBean oldvalue)
			throws Exception {

		return null;
	}

	@Override
	public PaginatorBean<CaricamentoDestinatariExcelBean> fetch(AdvancedCriteria criteria, Integer startRow, Integer endRow,
			List<OrderByBean> orderby) throws Exception {

		return null;
	}

	@Override
	public Map<String, ErrorBean> validate(CaricamentoDestinatariExcelBean bean) throws Exception {

		return null;
	}

	@SuppressWarnings("unchecked")
	public CaricamentoRubricaMappingsBean retrieveMappings() {

		ExecutionResultBean result = retrieveMappingsDb();

		List<CaricamentoRubricaMappingBean> mappings = null;

		if (result.isSuccessful()) {

			mappings = (List<CaricamentoRubricaMappingBean>) result.getResult();

			if (mappings == null) {

				mappings = (List<CaricamentoRubricaMappingBean>) SpringAppContext.getContext()
						.getBean("defaultMapping");

			}
		}

		CaricamentoRubricaMappingsBean retValue = new CaricamentoRubricaMappingsBean();
		retValue.setMappings(mappings);

		return retValue;
	}

	/**
	 * Recupera la mappatura nomeCampo/Colonna dalle preferenze dell'utente
	 * corrente
	 * 
	 * @return null se non è stata trovata alcuna preferenza precedentemente
	 *         salvata
	 */
	protected ExecutionResultBean retrieveMappingsDb() {

		ExecutionResultBean result = retrievePreference();

		if (result.isSuccessful()) {

			PreferenceBean preferenceBean = (PreferenceBean) result.getResult();

			List<CaricamentoRubricaMappingBean> mappings = null;

			if (preferenceBean != null) {

				String encodedPrefs = preferenceBean.getValue();
				mappings = gson.fromJson(encodedPrefs, new TypeToken<List<CaricamentoRubricaMappingBean>>() {
				}.getType());
				result.setResult(mappings);
				result.addAdditionalInformation("preferenceBean", preferenceBean);
			}
		}
		return result;

	}

	/**
	 * @return
	 */
	protected ExecutionResultBean retrievePreference() {

		AurigaLoginBean loginBean = AurigaUserUtil.getLoginInfo(getSession());

		PreferenceBean prefsResult = null;

		ExecutionResultBean retValue = new ExecutionResultBean();

		List<CaricamentoRubricaMappingBean> mappings = null;

		TFilterFetch<PreferenceBean> filterFetch = new TFilterFetch<PreferenceBean>();
		PreferenceBean filterBean = new PreferenceBean();
		filterBean.setPrefKey("CaricamentoRubricaMappings");
		filterBean.setUserid(loginBean.getUserid());

		filterFetch.setFilter(filterBean);

		DaoTUserPreferences daoTUserPreference = new DaoTUserPreferences();

		// recupero la configurazione dei campi. Se non trovo nulla tra le
		// preferenze utente, allora utilizzo quella di default estratta dal
		// file di configurazione

		try {

			TPagingList<PreferenceBean> pagedResult = daoTUserPreference.search(getLocale(), loginBean, filterFetch);

			if (pagedResult != null && pagedResult.getData() != null && !pagedResult.getData().isEmpty()) {
				prefsResult = pagedResult.getData().get(0);
				retValue.setResult(prefsResult);
			}
			retValue.setSuccessful(true);

		} catch (Exception e) {

			String message = String.format(
					"Durante il recupero delle mappature per l'utente %1$s, si è verificata la seguente eccezione %2$s",
					loginBean.getIdUser(), ExceptionUtils.getFullStackTrace(e));
			logger.error(message);
			retValue.setSuccessful(false);
			retValue.setMessage(message);

		}

		return retValue;
	}
}