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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.xml.bind.JAXBException;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.commons.lang3.StringUtils;
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

import it.eng.auriga.database.store.dmpk_bmanager.bean.DmpkBmanagerCaricacontfoglioximportBean;
import it.eng.auriga.database.store.dmpk_bmanager.bean.DmpkBmanagerIufoglioximportBean;
import it.eng.auriga.database.store.result.bean.StoreResultBean;
import it.eng.auriga.module.business.beans.AurigaLoginBean;
import it.eng.auriga.module.business.dao.beans.PreferenceBean;
import it.eng.auriga.ui.module.layout.server.caricamentorubriche.datasource.bean.CampoCaricamentoBean;
import it.eng.auriga.ui.module.layout.server.caricamentorubriche.datasource.bean.CaricamentoRubricaBean;
import it.eng.auriga.ui.module.layout.server.caricamentorubriche.datasource.bean.CaricamentoRubricaMappingBean;
import it.eng.auriga.ui.module.layout.server.caricamentorubriche.datasource.bean.CaricamentoRubricaMappingsBean;
import it.eng.auriga.ui.module.layout.server.caricamentorubriche.datasource.bean.XlsColumnRemapping;
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

/**
 * Permette di aggiornare la rubrica clienti in maniera automatica mediante il
 * caricamento di un file xls/xls
 * 
 * @author massimo malvestio
 *
 */
@Datasource(id = "CaricamentoRubricaClientiDatasource")
public class CaricamentoRubricaClientiDatasource
		extends AbstractDataSource<CaricamentoRubricaBean, CaricamentoRubricaBean> {

	protected Logger logger = Logger.getLogger(CaricamentoRubricaClientiDatasource.class);

	protected Gson gson = new Gson();

	protected SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yy HH:mm:ss");

	@Override
	public CaricamentoRubricaBean get(CaricamentoRubricaBean bean) throws Exception {

		return null;
	}

	@Override
	public CaricamentoRubricaBean add(CaricamentoRubricaBean data) throws Exception {

		ExecutionResultBean result = saveDocument(data);

		if (result.isSuccessful()) {

			result = saveDocumentRows(data, ((DmpkBmanagerIufoglioximportBean) result.getResult()).getIdfoglioio());

		}

		CaricamentoRubricaBean retValue = new CaricamentoRubricaBean();
		retValue.setErrorMessage(result.getMessage());

		return retValue;
	}

	private ExecutionResultBean saveDocumentRows(CaricamentoRubricaBean data, String documentId) {

		ExecutionResultBean retValue = new ExecutionResultBean();

		try {

			String mimeType = data.getInfoFile().get("mimetype");

			boolean isXls = mimeType.equals("application/excel");
			boolean isXlsx = mimeType.equals("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");

			if (isXls) {

				retValue = saveXls(data, documentId);

			} else if (isXlsx) {

				retValue = saveXlsx(data, documentId);

			} else {

				String message = String.format(
						"Il formato %1$s del documento %2$s non è supportato, solo xls e xlsx sono ammessi come documenti validi",
						data.getInfoFile().get("mimeType"), documentId);
				logger.error(message);

				retValue.setSuccessful(false);
				retValue.setMessage(message);

				updateDocumentStateError(data.getInfoFile(), documentId, message);

			}
		} catch (Exception e) {

			String errorMessage = e.getMessage() != null ? e.getMessage()
					: e.getCause() != null ? e.getCause().getMessage() : null;

			String message = "Durante il caricamento delle righe del file %1$s, si è verificata la seguente eccezione %2$s";
			logger.error(String.format(message, documentId, ExceptionUtils.getFullStackTrace(e)));

			retValue.setSuccessful(false);
			retValue.setMessage(String.format(message, documentId, errorMessage));

			updateDocumentStateError(data.getInfoFile(), documentId,
					String.format(message, documentId, ExceptionUtils.getFullStackTrace(e)));

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
	private ExecutionResultBean saveXlsx(CaricamentoRubricaBean data, String documentId) {

		ExecutionResultBean retValue = new ExecutionResultBean();

		BufferedInputStream documentStream = null;
		InputStream is = null;
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

			badRow: {

				for (int sheetIndex = 0; sheetIndex < wb.getNumberOfSheets(); sheetIndex++) {

					XSSFSheet currentSheet = wb.getSheetAt(sheetIndex);

					// verifico se ci sono righe con celle valorizzate
					int nonEmptyRows = currentSheet.getPhysicalNumberOfRows();

					if (nonEmptyRows > 0) {

						// la prima riga è di intestazione
						// la condizione su nonEmptyRows mi evita di scansionare
						// il numero massimo di righe che possono essere
						// presenti
						// nel foglio
						for (int rowIndex = currentSheet.getFirstRowNum(); rowIndex <= currentSheet.getLastRowNum()
								&& nonEmptyRows > 0; rowIndex++) {

							Row row = currentSheet.getRow(rowIndex);

							if (row != null) {

								retValue = populateRiga(cellReferences, xmlContenuti, nonEmptyRows, row);

								if (retValue.isSuccessful()) {

									Riga currentRiga = (Riga) retValue.getResult();

									if (currentRiga != null) {
										xmlContenuti.getRiga().add(currentRiga);
									}

								} else {

									updateDocumentStateError(data.getInfoFile(), documentId, retValue.getMessage());

									break badRow;
								}
							}
						}
					}
				}

				retValue = caricaFoglio(xmlContenuti, dettagliColonne, documentId);
			}

		} catch (Exception e) {

			String message = "Durante il caricamento delle righe del documento %1$s si è verificata la seguente eccezione %2$s";

			String exceptionMessage = e.getMessage() != null ? e.getMessage() : e.toString();

			String infoMessage = String.format(message, documentId, exceptionMessage);

			logger.error(String.format(message, documentId, ExceptionUtils.getFullStackTrace(e)));

			updateDocumentStateError(data.getInfoFile(), documentId, infoMessage);

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
	protected ExecutionResultBean populateRiga(List<XlsColumnRemapping> cellReferences, Lista xmlContenuti,
			int nonEmptyRows, Row row) throws NumberFormatException, IndexOutOfBoundsException {

		ExecutionResultBean retValue = new ExecutionResultBean();
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

				int cellColumnIndex = cellReference.getCol();
				Cell cell = row.getCell(cellColumnIndex);

				String value = "";

				if (cell != null) {

					// estraggo il tipo di valore, Cell.getCellType() non mi
					// ritorna il valore corretto per quanto riguarda i
					// campi di
					// tipo data, i quali vengono visti come numerici
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

									logger.error(message, e);
								}
							} else {
								/**
								 * VERIFICA SE E' UN NUMERO
								 */
								try {
									value = Double.toString(cell.getNumericCellValue());
									value = value.replace(".", ",");
								} catch (Exception e1) {
		
									value = cell.getStringCellValue();
		
									// ho notato che utilizzando "," come separatore
									// delle migliaia, non viene considerato come
									// valore numerico valido. Allora rimpiazzo la
									// virgola e tento una conversione numerica. Se
									// la conversione funziona, allora vuol dire che
									// è effettivamente un numero, altrimenti che si
									// tratta di un numero scritto in mniera errata,
									// e deve essere notificato
									value = value.replace(".", ",");
								}		
							}								
						} else if (row.getCell(cellColumnIndex).getCellType() == Cell.CELL_TYPE_STRING) {							
							value = cell.getStringCellValue();
						} else if (row.getCell(cellColumnIndex).getCellType() == Cell.CELL_TYPE_BLANK) {							
							value = cell.getStringCellValue();						
						}					
//					try {
//						if (fieldType.equals("D")) {
//
//							try {
//
//								Date dateValue = cell.getDateCellValue();
//
//								if (dateValue != null) {
//									value = dateFormat.format(dateValue);
//								}
//
//							} catch (NumberFormatException e) {
//								String message = String.format(
//										"Data non valida per la colonna %1$s, nome campo %2$s, riga numero %3$s",
//										cellColumnIndex, cellFieldName, rowNum);
//
//								logger.error(message, e);
//
//								retValue.setMessage(message);
//								retValue.setSuccessful(false);
//								break;
//
//							}
//
//						} else if (fieldType.equals("N")) {
//
//							try {
//								value = Double.toString(cell.getNumericCellValue());
//								value = value.replace(".", ",");
//							} catch (Exception e) {
//
//								value = cell.getStringCellValue();
//
//								// ho notato che utilizzando "," come separatore
//								// delle migliaia, non viene considerato come
//								// valore numerico valido. Allora rimpiazzo la
//								// virgola e tento una conversione numerica. Se
//								// la conversione funziona, allora vuol dire che
//								// è effettivamente un numero, altrimenti che si
//								// tratta di un numero scritto in mniera errata,
//								// e deve essere notificato
//								value = value.replace(".", ",");
//
//							}
//						} else {
//							try{
//								value = cell.getStringCellValue();
//							} catch (Exception e) {
//								logger.equals(e);
//							}
//							
//							value = cell.getStringCellValue();
//							
//
//						}
					} catch (Exception e) {

						String message = String.format(
								"Dato non valido per la colonna %1$s, nome campo %2$s, riga numero %3$s, tipo %4$s, controllare il formato della cella",
								cellColumnIndex, cellFieldName, rowNum, fieldType);

						retValue.setMessage(message);
						retValue.setSuccessful(false);

						logger.error(message, e);

						break;
					}

					if (StringUtils.isNotBlank(value)) {
						saveRiga = true;
					}
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
	private ExecutionResultBean saveXls(CaricamentoRubricaBean data, String documentId) {

		ExecutionResultBean retValue = new ExecutionResultBean();

		BufferedInputStream documentStream = null;
		InputStream is = null;
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

					if (nonEmptyRows > 0) {

						// la prima riga è di intestazione
						// la condizione su nonEmptyRows mi evita di scansionare
						// il numero massimo di righe che possono essere
						// presenti
						// nel foglio
						for (int rowIndex = currentSheet.getFirstRowNum(); rowIndex <= currentSheet.getLastRowNum()
								&& nonEmptyRows > 0; rowIndex++) {

							Row row = currentSheet.getRow(rowIndex);

							if (row != null) {

								retValue = populateRiga(cellReferences, xmlContenuti, nonEmptyRows, row);

								if (retValue.isSuccessful()) {

									Riga currentRiga = (Riga) retValue.getResult();

									if (currentRiga != null) {
										xmlContenuti.getRiga().add(currentRiga);
									}

								} else {

									updateDocumentStateError(data.getInfoFile(), documentId, retValue.getMessage());

									break badRow;
								}
							}
						}
					}
				}

				retValue = caricaFoglio(xmlContenuti, dettagliColonne, documentId);

			}
		} catch (Exception e) {

			String message = "Durante il caricamento delle righe del documento %1$s si è verificata la seguente eccezione %2$s";

			String exceptionMessage = e.getMessage() != null ? e.getMessage() : e.getCause().getMessage();

			String infoMessage = String.format(message, documentId, exceptionMessage);

			logger.error(String.format(message, documentId, ExceptionUtils.getFullStackTrace(e)));

			updateDocumentStateError(data.getInfoFile(), documentId, infoMessage);

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
	private void updateDocumentStateError(Map<String, String> infoFile, String documentId, String format) {

		DmpkBmanagerIufoglioximport client = new DmpkBmanagerIufoglioximport();

		DmpkBmanagerIufoglioximportBean updateBean = new DmpkBmanagerIufoglioximportBean();

		updateBean.setCodidconnectiontokenin(AurigaUserUtil.getLoginInfo(getSession()).getToken());
		updateBean.setIdfoglioio(documentId);

		String idUserLavoro = AurigaUserUtil.getLoginInfo(getSession()).getIdUserLavoro();

		if (idUserLavoro != null) {
			updateBean.setIduserlavoroin(new BigDecimal(idUserLavoro));
		}

		updateBean.setStatoin("elaborato_con_errori");
		updateBean.setTipocontenutoin(infoFile.get("mimeType"));

		try {
			client.execute(getLocale(), AurigaUserUtil.getLoginInfo(getSession()), updateBean);
		} catch (Exception e) {

			logger.error(String.format(
					"Durante l'aggiornamento del record del documento %1$s, si è verificata la seguente eccezione %2$s",
					documentId, ExceptionUtils.getFullStackTrace(e)));

		}
	}

	private ExecutionResultBean caricaFoglio(Lista xmlContenuti, Lista dettagliColonne, String documentId) {

		ExecutionResultBean retValue = new ExecutionResultBean();
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
	protected ExecutionResultBean populateDettagliColonne(CaricamentoRubricaBean data) {

		ExecutionResultBean result = new ExecutionResultBean();

		int colIndex = 1;

		Lista dettagliColonne = new Lista();

		List<XlsColumnRemapping> cellReferences = new ArrayList<XlsColumnRemapping>();

		for (CampoCaricamentoBean currentCampoCaricamento : data.getMappings()) {

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

	/**
	 * Crea un record nella tabella Iufolglioximport relativo al documento da
	 * analizzare
	 * 
	 * @param data
	 * @return
	 */
	private ExecutionResultBean saveDocument(CaricamentoRubricaBean data) {

		String baseErrorMessage = "Durante la registrazione del documento %1$s - %2$s, si è verificata la seguente eccezione %3$s";

		Map<String, String> infoFile = data.getInfoFile();

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
			importBean.setTipocontenutoin(data.getCompanyId());
			importBean.setUriin(savedFileInRepo.getUri());

			StoreResultBean<DmpkBmanagerIufoglioximportBean> result = documentImport.execute(getLocale(),
					AurigaUserUtil.getLoginInfo(getSession()), importBean);

			retValue.setSuccessful(!result.isInError());

			if (result.isInError()) {

				String message = String.format(baseErrorMessage, data.getUri(), infoFile.get("name"),
						result.getDefaultMessage());

				retValue.setMessage(result.getDefaultMessage());

				logger.error(message);

			} else {

				retValue.setSuccessful(true);
				retValue.setResult(result.getResultBean());

			}

		} catch (Exception e) {

			String errorMessage = e.getMessage() != null ? e.getMessage() : e.getCause().getMessage();

			String message = String.format(baseErrorMessage, data.getUri(), infoFile.get("name"), errorMessage);
			retValue.setMessage(message);
			retValue.setSuccessful(false);

			logger.error(String.format(baseErrorMessage, data.getUri(), infoFile.get("name"),
					ExceptionUtils.getFullStackTrace(e)));
		}

		return retValue;
	}

	/**
	 * @param map
	 * @throws Exception
	 * @throws BeansException
	 */
	@SuppressWarnings("unchecked")
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
	public CaricamentoRubricaBean remove(CaricamentoRubricaBean bean) throws Exception {

		return null;
	}

	@Override
	public CaricamentoRubricaBean update(CaricamentoRubricaBean bean, CaricamentoRubricaBean oldvalue)
			throws Exception {

		return null;
	}

	@Override
	public PaginatorBean<CaricamentoRubricaBean> fetch(AdvancedCriteria criteria, Integer startRow, Integer endRow,
			List<OrderByBean> orderby) throws Exception {

		return null;
	}

	@Override
	public Map<String, ErrorBean> validate(CaricamentoRubricaBean bean) throws Exception {

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