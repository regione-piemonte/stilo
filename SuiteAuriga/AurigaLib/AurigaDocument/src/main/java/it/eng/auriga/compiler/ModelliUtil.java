/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.File;
import java.io.InputStream;
import java.lang.reflect.Constructor;
import java.math.BigDecimal;
import java.util.Locale;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import it.eng.auriga.compiler.docx.CompositeCompiler;
import it.eng.auriga.compiler.docx.DocxCompiler;
import it.eng.auriga.compiler.docx.FormDocxCompiler;
import it.eng.auriga.compiler.odt.OdtCompiler;
import it.eng.auriga.compiler.utility.TemplateStorage;
import it.eng.auriga.compiler.utility.TemplateStorageFactory;
import it.eng.auriga.database.store.dmpk_processes.bean.DmpkProcessesGetdatixmodellipraticaBean;
import it.eng.auriga.database.store.dmpk_processes.store.Getdatixmodellipratica;
import it.eng.auriga.database.store.result.bean.StoreResultBean;
import it.eng.auriga.module.business.beans.AurigaLoginBean;
import it.eng.core.business.FileUtil;
import it.eng.spring.utility.SpringAppContext;
import it.eng.utility.ConvertToPdfUtil;
//import it.eng.utility.oomanager.OpenOfficeConverter;
//import it.eng.utility.oomanager.config.OpenOfficeConfiguration;

/**
 * Centralizza la generazione di template con dati iniettati
 * 
 * @author mattia zanin
 * 
 *         IMPORTANTE: SE LA CLASSE VIENE MODIFICATA VERIFICARE SE DEVE ESSERE MODIFICATA ANCHE L'ANALOGA CLASSE IN AURIGAWEB
 *
 */
public class ModelliUtil {

	private static Logger logger = Logger.getLogger(ModelliUtil.class);

	/**
	 * Elenca i tipi di modello compatibili
	 */
	public enum TipoModello {

		DOCX, ODT, DOCX_FORM, COMPOSITE
	}

	public static TipoModello recuperaTipoModello(String tipoModello) {
		if (tipoModello != null && !"".equals(tipoModello)) {
			// try {
			// return tipoModello != null ? TipoModello.valueOf(tipoModello) :
			// null;
			// } catch(Exception e) {}
			if ("DOCX".equalsIgnoreCase(tipoModello)) {
				return TipoModello.DOCX;
			} else if ("ODT".equalsIgnoreCase(tipoModello)) {
				return TipoModello.ODT;
			} else if ("DOCX_FORM".equalsIgnoreCase(tipoModello)) {
				return TipoModello.DOCX_FORM;
			} else if ("COMPOSITE".equalsIgnoreCase(tipoModello)) {
				return TipoModello.COMPOSITE;
			}
		}
		return null;
	}

	public static synchronized File fillTemplate(String idProcess, String uriModello, String tipoModello, String templateValues, Locale locale,
			AurigaLoginBean loginBean) throws Exception {
		return fillTemplate(idProcess, uriModello, recuperaTipoModello(tipoModello), templateValues, locale, loginBean);
	}

	/**
	 * Inietta i dati all'interno del template specificato
	 * 
	 * @param idProcess
	 *            identificativo dell'istanza di processo cui il modello è associato
	 * @param uriModello
	 *            uri del documento di modello
	 * @param tipoModello
	 * @param locale
	 * @param loginBean
	 * @return
	 * @throws Exception
	 */
	private static synchronized File fillTemplate(String idProcess, String uriModello, TipoModello tipoModello, String templateValues, Locale locale,
			AurigaLoginBean loginBean) throws Exception {
		File filledTemplate = null;
		if (StringUtils.isBlank(templateValues)) {
			templateValues = getTemplateValues(idProcess, locale, loginBean);
		}

		logger.error("idProcess: " + idProcess);
		logger.error("uriModello: " + uriModello);
		logger.error("tipoModello: " + tipoModello);
		logger.error("templateValues: " + templateValues);

		if (StringUtils.isNotBlank(uriModello)) {
			if (tipoModello != null) {
				try {
					switch (tipoModello) {
					case DOCX:
						filledTemplate = new DocxCompiler(templateValues, uriModello).fillDocument();
						break;
					case DOCX_FORM:
						filledTemplate = new FormDocxCompiler(templateValues, uriModello).fillDocument();
						break;
					case ODT:
						filledTemplate = new OdtCompiler().fillOdtDocument(templateValues, uriModello);
						break;
					case COMPOSITE:
						filledTemplate = new CompositeCompiler(templateValues, uriModello).injectData();
					default:
						break;
					}
				} catch (Throwable e) {
					logger.error("Durante l'iniezione dei dati si è verificata la seguente versione " + ExceptionUtils.getFullStackTrace(e));
					throw new Exception("Il modello non è nel formato previsto (" + tipoModello + ")");
				}

			} else {
				// le eseguo tutte a cascata finchè non richiamo il tipo
				// corretto
				try {
					filledTemplate = fillTemplate(idProcess, uriModello, TipoModello.DOCX, templateValues, locale, loginBean);
				} catch (Throwable e1) {
					logger.error("fillTemplate DOCX failed: ", e1);
					try {
						filledTemplate = fillTemplate(idProcess, uriModello, TipoModello.DOCX_FORM, templateValues, locale, loginBean);
					} catch (Throwable e2) {
						logger.error("fillTemplate DOCX_FORM failed: ", e2);
						try {
							filledTemplate = fillTemplate(idProcess, uriModello, TipoModello.ODT, templateValues, locale, loginBean);
						} catch (Throwable e3) {
							logger.error("fillTemplate ODT failed: ", e3);

							try {
								filledTemplate = fillTemplate(idProcess, uriModello, TipoModello.COMPOSITE, templateValues, locale, loginBean);
							} catch (Throwable e4) {
								logger.error("fillTemplate COMPOSITE failed: ", e4);
							}
						}
					}
				}
			}
		} else {
			throw new Exception("Manca l'uri del modello associato al task");
		}
		if (filledTemplate == null) {
			throw new Exception("Si è verificato un errore durante la generazione del modello");
		}
		return filledTemplate;
	}

	/**
	 * Inietta i dati all'interno del template specificato
	 * 
	 * @param idProcess
	 *            identificativo dell'istanza di processo cui il modello è associato
	 * @param uriModello
	 *            uri del documento di modello
	 * @param tipoModello
	 * @param locale
	 * @param loginBean
	 * @return
	 * @throws Exception
	 */
	public static synchronized File fillTemplateWithCompiler(String idProcess, String uriModello, String compilerClassValue, String templateValues,
			Locale locale, AurigaLoginBean loginBean) throws Exception {
		File filledTemplate = null;
		if (StringUtils.isBlank(templateValues)) {
			templateValues = getTemplateValues(idProcess, locale, loginBean);
		}

		logger.error("idProcess: " + idProcess);
		logger.error("uriModello: " + uriModello);
		logger.error("tipoModello: " + compilerClassValue);
		logger.error("templateValues: " + templateValues);

		@SuppressWarnings("rawtypes")
		Class compilerClass = Class.forName(compilerClassValue);

		@SuppressWarnings("unchecked")
		Constructor constructor = compilerClass.getConstructor(new Class[] { String.class, String.class });

		CompositeCompiler compiler = (CompositeCompiler) constructor.newInstance(templateValues, uriModello);

		filledTemplate = compiler.injectData();

		if (filledTemplate == null) {
			throw new Exception("Si è verificato un errore durante la generazione del modello");
		}
		return filledTemplate;
	}

	/**
	 * Inietta i dati nel documento di modello specificato e genera la versione pdf
	 * 
	 * @param idProcess
	 * @param uriModello
	 * @param tipoModello
	 * @param locale
	 * @param loginBean
	 * @return
	 * @throws Exception
	 */
	public static synchronized File fillTemplateAndConvertToPdf(String idProcess, String uriModello, String tipoModello, Locale locale,
			AurigaLoginBean loginBean) throws Exception {
		return fillTemplateAndConvertToPdf(idProcess, uriModello, tipoModello, null, locale, loginBean);
	}

	public static synchronized File fillTemplateAndConvertToPdf(String idProcess, String uriModello, String tipoModello, String templateValues, Locale locale,
			AurigaLoginBean loginBean) throws Exception {
		File filledTemplatePdf = null;
		if (StringUtils.isNotBlank(uriModello)) {
			File filledTemplate = fillTemplate(idProcess, uriModello, tipoModello, templateValues, locale, loginBean);
			if (filledTemplate != null) {
				// salvo il file in storage
				TemplateStorage templateStorage = TemplateStorageFactory.getTemplateStorageImpl(); 
				String filledTemplateStorageUri = templateStorage.store(filledTemplate);
				// converto il modello con i valori iniettati in pdf
				filledTemplatePdf = convertToPdf(filledTemplateStorageUri, tipoModello);
			}
		}
		return filledTemplatePdf;
	}

	public static synchronized File convertToPdf(String uri, String tipoModello) throws Exception {
		return convertToPdf(uri, recuperaTipoModello(tipoModello));
	}

	private static synchronized File convertToPdf(String uri, TipoModello tipoModello) throws Exception {
		File filePdf = null;
		InputStream is = null;
		if (tipoModello != null) {
			is = ConvertToPdfUtil.convertToPdf(uri, tipoModello == TipoModello.ODT);
		} else {
			try {
				is = ConvertToPdfUtil.convertToPdf(uri, false);
			} catch (Exception e1) {
				try {
					is = ConvertToPdfUtil.convertToPdf(uri, true);
				} catch (Exception e2) {
				}
			}
		}
		if (is != null) {
			filePdf = convertInputStreamToFile(is, "pdf", ".pdf");
		} else {
			throw new Exception("Si è verificato un errore durante la conversione in pdf del modello generato");
		}
		return filePdf;
	}

	/**
	 * Inietta i dati nel documento di modello specificato e lo trasforma in un doc
	 * 
	 * @param idProcess
	 * @param uriModello
	 * @param tipoModello
	 * @param locale
	 * @param loginBean
	 * @return
	 * @throws Exception
	 */
	/*
	public static synchronized File fillTemplateAndConvertToDoc(String idProcess, String uriModello, String tipoModello, Locale locale,
			AurigaLoginBean loginBean) throws Exception {
		if (StringUtils.isNotBlank(uriModello)) {
			File filledTemplate = fillTemplate(idProcess, uriModello, tipoModello, null, locale, loginBean);
			if (filledTemplate != null) {
				return convertToDoc(filledTemplate);
			}
		}
		return null;
	}
	*/

	/**
	 * Converte il documento odt specificato in doc
	 * 
	 * @param file
	 *            rappresenta il documento odt da convertire
	 * @return
	 * @throws Exception
	 */
	/*
	private static File convertToDoc(File file) throws Exception {
		File filledTemplateDoc = File.createTempFile("doc", ".doc");
		OpenOfficeConverter.configure(SpringAppContext.getContext().getBean(OpenOfficeConfiguration.class));
		OpenOfficeConverter.newInstance().convert(file, filledTemplateDoc);
		return filledTemplateDoc;
	}
	*/

	/**
	 * @param pdfInputStream
	 * @return
	 * @throws Exception
	 */
	private static File convertInputStreamToFile(InputStream is, String filename, String extension) throws Exception {
		File filledTemplatePdf = File.createTempFile(filename, extension);
		FileUtil.writeStreamToFile(is, filledTemplatePdf);
		return filledTemplatePdf;
	}

	/**
	 * Recupera l'insieme dei valori da iniettare nel template
	 * 
	 * @param processId
	 * @param locale
	 * @param session
	 * @return
	 * @throws Exception
	 */
	private static String getTemplateValues(String idProcess, Locale locale, AurigaLoginBean loginBean) throws Exception {
		Getdatixmodellipratica dmpkProcessesGetdatixmodellipratica = new Getdatixmodellipratica();
		DmpkProcessesGetdatixmodellipraticaBean dmpkProcessesGetdatixmodellipraticaBean = new DmpkProcessesGetdatixmodellipraticaBean();
		dmpkProcessesGetdatixmodellipraticaBean.setCodidconnectiontokenin(loginBean.getToken());
		dmpkProcessesGetdatixmodellipraticaBean
				.setIduserlavoroin(StringUtils.isNotBlank(loginBean.getIdUserLavoro()) ? new BigDecimal(loginBean.getIdUserLavoro()) : null);
		dmpkProcessesGetdatixmodellipraticaBean.setIdprocessin(BigDecimal.valueOf(Long.valueOf(idProcess)));
		StoreResultBean<DmpkProcessesGetdatixmodellipraticaBean> result = dmpkProcessesGetdatixmodellipratica.execute(loginBean,
				dmpkProcessesGetdatixmodellipraticaBean);
		if (result.isInError()) {
			throw new Exception("Errore store " + result.getErrorContext() + " - " + result.getErrorCode() + " - " + result.getDefaultMessage());
		}
		return result.getResultBean().getDatimodellixmlout();
	}

}