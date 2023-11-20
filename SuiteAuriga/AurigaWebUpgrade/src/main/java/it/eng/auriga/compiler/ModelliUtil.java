/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;
import java.io.StringReader;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpSession;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import it.eng.auriga.compiler.docx.CompositeCompiler;
import it.eng.auriga.compiler.docx.DocxCompiler;
import it.eng.auriga.compiler.docx.FormDocxCompiler;
import it.eng.auriga.compiler.odt.OdtCompiler;
import it.eng.auriga.database.store.dmpk_processes.bean.DmpkProcessesGetdatixmodellipraticaBean;
import it.eng.auriga.database.store.result.bean.StoreResultBean;
import it.eng.auriga.exception.StoreException;
import it.eng.auriga.module.business.beans.AurigaLoginBean;
import it.eng.auriga.ui.module.layout.server.attributiDinamici.datasource.bean.AttributiDinamiciOutputBean;
import it.eng.auriga.ui.module.layout.server.attributiDinamici.datasource.bean.AttributoBean;
import it.eng.auriga.ui.module.layout.server.attributiDinamici.datasource.bean.DettColonnaAttributoListaBean;
import it.eng.auriga.ui.module.layout.server.modelliDoc.datasource.ModelliDocDatasource;
import it.eng.auriga.ui.module.layout.server.modelliDoc.datasource.bean.AssociazioniAttributiCustomBean;
import it.eng.auriga.ui.module.layout.server.modelliDoc.datasource.bean.ModelliDocBean;
import it.eng.auriga.ui.module.layout.shared.util.TipoModelloDoc;
import it.eng.client.DmpkProcessesGetdatixmodellipratica;
import it.eng.core.business.FileUtil;
import it.eng.jaxb.context.SingletonJAXBContext;
import it.eng.jaxb.variabili.SezioneCache;
import it.eng.jaxb.variabili.SezioneCache.Variabile;
import it.eng.jaxb.variabili.SezioneCache.Variabile.Lista.Riga;
import it.eng.jaxb.variabili.SezioneCache.Variabile.Lista.Riga.Colonna;
import it.eng.services.fileop.InfoFileUtility;
import it.eng.utility.ConvertToPdfUtil;
import it.eng.utility.module.config.StorageImplementation;
import it.eng.utility.oomanager.OpenOfficeConverter;
import it.eng.utility.ui.module.layout.shared.bean.FileDaFirmareBean;
import it.eng.utility.ui.user.AurigaUserUtil;
import it.eng.utility.ui.user.ParametriDBUtil;
import it.eng.utility.ui.user.UserUtil;

/**
 * Centralizza la generazione di template con dati iniettati
 * 
 * @author mattia zanin
 * 
 *         IMPORTANTE: SE LA CLASSE VIENE MODIFICATA VERIFICARE SE DEVE ESSERE MODIFICATA ANCHE L'ANALOGA CLASSE IN AURIGADOCUMENT
 *
 */
public class ModelliUtil {

	private static Logger logger = Logger.getLogger(ModelliUtil.class);

	/**
	 * Elenca i tipi di modello compatibili
	 */
	public enum TipoModello {

		ODT_FREEMARKERS, DOCX, DOCX_FORM, ODT, COMPOSITE 
	}

	/****************************************************/
	/**           INIZIO METODI PUBBLICI               **/
	/****************************************************/
	
	/**
	 * Inietta i dati a partire da un idModello e un sezione cache contenente i dati  da iniettare
	 * 
	 * @param idProcess: identificativo dell'istanza di processo cui il modello è associato
	 * @param idModello: modello su cui iniettare i dati
	 * @param templateValues: sezione cache con i valori da iniettare
	 * @param flgConvertToPdf: per convertire in pdf oppure no (se è null viene letto flgGeneraPdf del modello in DB)
	 * @param flgMostaDatiSensibili: per mostrare i dati sensibili oppure no
	 * @param locale
	 * @param loginBean
	 * @return File generato da modello con i valori iniettati
	 * @throws Exception
	 */	
	public static FileDaFirmareBean fillTemplate(String idProcess, String idModello, String templateValues, HttpSession session) throws Exception {
		return fillTemplate(idProcess, idModello, templateValues, null, true, session);
	}
	
	public static FileDaFirmareBean fillTemplate(String idProcess, String idModello, String templateValues, Boolean flgConvertToPdf, HttpSession session) throws Exception {
		return fillTemplate(idProcess, idModello, templateValues, flgConvertToPdf, true, session);
	}
	
	public static FileDaFirmareBean fillTemplate(String idProcess, String idModello, String templateValues, Boolean flgConvertToPdf, Boolean flgMostaDatiSensibili, HttpSession session) throws Exception {
		// Estraggo il modello
		ModelliDocBean modello = getModello(session, idModello);
		modello.setFlgMostraDatiSensibili(flgMostaDatiSensibili != null ? flgMostaDatiSensibili : true);
		if(flgConvertToPdf != null) {
			modello.setFlgGeneraPdf(flgConvertToPdf);
		}
		// Estraggo il tipo di modello
		String tipoModello = modello.getTipoModello();
		if (!TipoModelloDoc.ODT_CON_FREEMARKERS.getValue().equals(tipoModello)) {
			String uriModello = "";
			if(modello.getListaModelli() != null && modello.getListaModelli().size() > 0) {			
				uriModello = modello.getListaModelli().get(0).getUriFileAllegato();
			}				
			if(flgConvertToPdf != null && flgConvertToPdf) {
				FileDaFirmareBean templateCompilatoBean = new FileDaFirmareBean();
				File templateCompilatoFile = fillTemplateAndConvertToPdf(idProcess, uriModello, tipoModello, templateValues, session);
				templateCompilatoBean.setNomeFile(templateCompilatoFile.getName());
				InfoFileUtility lFileUtility = new InfoFileUtility();
				templateCompilatoBean.setInfoFile(lFileUtility.getInfoFromFile(templateCompilatoFile.toURI().toString(), templateCompilatoFile.getName(), false, null));
				templateCompilatoBean.setUri(StorageImplementation.getStorage().store(templateCompilatoFile));
				return templateCompilatoBean;				
			} else {
				FileDaFirmareBean templateCompilatoBean = new FileDaFirmareBean();
				File templateCompilatoFile = fillTemplate(idProcess, uriModello, tipoModello, templateValues, session);
				templateCompilatoBean.setNomeFile(templateCompilatoFile.getName());
				InfoFileUtility lFileUtility = new InfoFileUtility();
				templateCompilatoBean.setInfoFile(lFileUtility.getInfoFromFile(templateCompilatoFile.toURI().toString(), templateCompilatoFile.getName(), false, null));
				templateCompilatoBean.setUri(StorageImplementation.getStorage().store(templateCompilatoFile));
				return templateCompilatoBean;
			}
		}
		return fillFreeMarkerTemplate(session, templateValues, modello);
	}
	
	public static FileDaFirmareBean fillFreeMarkerTemplateWithModel(String idProcess, String idModello, Map<String, Object> model, Boolean flgConvertToPdf, Boolean flgMostaDatiSensibili, HttpSession session) throws Exception {
		return fillFreeMarkerTemplateWithModel(idProcess, idModello, model, flgConvertToPdf, flgMostaDatiSensibili, false, null, session);
	}
	
	public static FileDaFirmareBean fillFreeMarkerTemplateWithModel(String idProcess, String idModello, Map<String, Object> model, Boolean flgConvertToPdf, Boolean flgMostaDatiSensibili, Boolean flgMostraOmissisBarrati, List<String> elencoCampiConGestioneOmissisDaIgnorare, HttpSession session) throws Exception {
		AurigaLoginBean loginBean = AurigaUserUtil.getLoginInfo(session);
		// Estraggo il modello
		ModelliDocBean modello = getModello(session, idModello);
		modello.setValori(model);
		modello.setFlgMostraDatiSensibili(flgMostaDatiSensibili != null ? flgMostaDatiSensibili : true);
		modello.setFlgMostraOmissisBarrati(flgMostraOmissisBarrati != null ? flgMostraOmissisBarrati : false);
		modello.setElencoCampiConGestioneOmissisDaIgnorare(elencoCampiConGestioneOmissisDaIgnorare);
		if(flgConvertToPdf != null) {
			modello.setFlgGeneraPdf(flgConvertToPdf);
		}
		// Estraggo l'uri del modello
		ModelliDocDatasource lModelliDocDatasource = new ModelliDocDatasource();
		lModelliDocDatasource.setLoginBean(loginBean);
		lModelliDocDatasource.setSession(session);
		return lModelliDocDatasource.generaDocDaModello(modello);
	}
	
	/**
	 * 
	 * @param idProcess: identificativo dell'istanza di processo cui il modello è associato
	 * @param uriModello: uri del modello su cui iniettare i dati
	 * @param tipoModello: tipo del modello su cui iniettare i dati
	 * @param templateValues: sezione cache con i valori da iniettare
	 * @param locale
	 * @param loginBean
	 * @return File generato da modello con i valori iniettati
	 * @throws Exception
	 */	
	public static File fillTemplate(String idProcess, String uriModello, String tipoModello, String templateValues, HttpSession session) throws Exception {
		return fillTemplate(idProcess, null, uriModello, recuperaTipoModello(tipoModello), templateValues, session);
	}

	/**
	 * Inietta i dati nel documento di modello specificato e genera la versione pdf
	 * 
	 * @param idProcess
	 * @param uriModello
	 * @param tipoModello
	 * @param templateValues
	 * @param locale
	 * @param loginBean
	 * @return
	 * @throws Exception
	 */	
	public static File fillTemplateAndConvertToPdf(String idProcess, String uriModello, String tipoModello, HttpSession session) throws Exception {
		return fillTemplateAndConvertToPdf(idProcess, uriModello, tipoModello, null, session);
	}
			
	public static File fillTemplateAndConvertToPdf(String idProcess, String uriModello, String tipoModello, String templateValues, HttpSession session) throws Exception {
		File filledTemplatePdf = null;
		if (StringUtils.isNotBlank(uriModello)) {
			File filledTemplate = fillTemplate(idProcess, uriModello, tipoModello, templateValues, session);
			if (filledTemplate != null) {
				// salvo il file in storage
				String filledTemplateStorageUri = StorageImplementation.getStorage().store(filledTemplate);
				// converto il modello con i valori iniettati in pdf
				boolean generaPdfa = ParametriDBUtil.getParametroDBAsBoolean(session, "GENERAZIONE_DA_MODELLO_ABILITA_PDFA");
				filledTemplatePdf = convertToPdf(filledTemplateStorageUri, tipoModello, generaPdfa);
			}
		}
		return filledTemplatePdf;
	}

	/**
	 * Inietta i dati nel documento di modello specificato e lo trasforma in un doc
	 * 
	 * @param idProcess
	 * @param uriModello
	 * @param tipoModello
	 * @param templateValues
	 * @param locale
	 * @param loginBean
	 * @return
	 * @throws Exception
	 */	
	public static File fillTemplateAndConvertToDoc(String idProcess, String uriModello, String tipoModello, HttpSession session) throws Exception {
		return fillTemplateAndConvertToDoc(idProcess, uriModello, tipoModello, null, session);
	}
			
	public static File fillTemplateAndConvertToDoc(String idProcess, String uriModello, String tipoModello, String templateValues, HttpSession session) throws Exception {
		if (StringUtils.isNotBlank(uriModello)) {
			File filledTemplate = fillTemplate(idProcess, uriModello, tipoModello, templateValues, session);
			if (filledTemplate != null) {
				return convertToDoc(filledTemplate);
			}
		}
		return null;
	}
	
	/**
	 * Converte il documento in pdf
	 * 
	 * @param uri
	 * @param tipoModello
	 * @return
	 * @throws Exception
	 */	
	public static File convertToPdf(String uri, String tipoModello, boolean pdfa) throws Exception {
		return convertToPdf(uri, recuperaTipoModello(tipoModello), pdfa);
	}
	
	/**
	 * Converte il documento odt in doc
	 * 
	 * @param file del documento odt da convertire
	 * @return
	 * @throws Exception
	 */	
	public static File convertToDoc(File file) throws Exception {
		File filledTemplateDoc = File.createTempFile("doc", ".doc");
		try {
			OpenOfficeConverter.newInstance().convertByOutExt(file, "application/vnd.oasis.opendocument.text", filledTemplateDoc, "doc");
		} catch (Exception e) {
			logger.error("Errore durante la conversione con OpenOffice", e);
 			throw new StoreException("Il servizio di conversione da odt in doc è momentaneamente non disponibile. Se il problema persiste contattare l'assistenza");
		}		
		return filledTemplateDoc;
	}
	
	/****************************************************/
	/**             FINE METODI PUBBLICI                */
	/****************************************************/
	
	/**
	 * Inietta i dati all'interno del template specificato
	 * 
	 * @param idProcess Identificativo dell'istanza di processo cui il modello è associato
	 * @param uriModello Uri del documento di modello
	 * @param tipoModello
	 * @param locale
	 * @param loginBean
	 * @return Modello coi valori iniettati
	 * @throws Exception
	 */	
	private static File fillTemplate(String idProcess, ModelliDocBean modello, String uriModello, TipoModello tipoModello, String templateValues, HttpSession session) throws Exception {
		File filledTemplate = null;
		if (StringUtils.isBlank(templateValues)) {
			templateValues = getTemplateValues(idProcess, session);
		}

		logger.debug("idProcess: " + idProcess);
		logger.debug("uriModello: " + uriModello);
		logger.debug("tipoModello: " + tipoModello);
		logger.debug("templateValues: " + templateValues);

		if (StringUtils.isNotBlank(uriModello)) {
			if (tipoModello != null) {
				try {
					switch (tipoModello) {
					case ODT_FREEMARKERS:
						FileDaFirmareBean filledTemplateBean = fillFreeMarkerTemplate(session, templateValues, modello);
						filledTemplate = StorageImplementation.getStorage().extractFile(filledTemplateBean.getUri());
						break;
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
					logger.error("Durante l'iniezione dei dati si è verificata la seguente eccezione " + ExceptionUtils.getFullStackTrace(e));
					logger.error("idProcess: " + idProcess);
					logger.error("uriModello: " + uriModello);
					logger.error("tipoModello: " + tipoModello);
					logger.error("templateValues: " + templateValues);
					throw new Exception("Il modello non è nel formato previsto (" + tipoModello + ")");
				}

			} else {
				// le eseguo tutte a cascata finchè non richiamo il tipo
				// corretto
				try {
					filledTemplate = fillTemplate(idProcess, modello, uriModello, TipoModello.ODT_FREEMARKERS, templateValues, session);
				} catch (Throwable e1) {
					try {
						filledTemplate = fillTemplate(idProcess, null, uriModello, TipoModello.DOCX, templateValues, session);
					} catch (Throwable e2) {
						logger.debug("fillTemplate DOCX fallito, tento DOCX_FORM: ", e1);
						try {
							filledTemplate = fillTemplate(idProcess, null, uriModello, TipoModello.DOCX_FORM, templateValues, session);
						} catch (Throwable e3) {
							logger.debug("fillTemplate DOCX_FORM fallito, tento ODT: ", e2);
							try {
								filledTemplate = fillTemplate(idProcess, null,uriModello, TipoModello.ODT, templateValues, session);
							} catch (Throwable e4) {
								logger.debug("fillTemplate ODT fallito, tento COMPOSITE: ", e3);
								try {
									filledTemplate = fillTemplate(idProcess, null, uriModello, TipoModello.COMPOSITE, templateValues, session);
								} catch (Throwable e5) {
									logger.debug("fillTemplate COMPOSITE fallito: ", e4);
									logger.error("Sono state tentate tutte le tipologie di iniezione per il modello");
									logger.error("idProcess: " + idProcess);
									logger.error("uriModello: " + uriModello);
									logger.error("tipoModello: " + tipoModello);
									logger.error("templateValues: " + templateValues);
									logger.error("StackTrace dei vari errori di iniezione: ");
									logger.error(e1.getStackTrace() != null ? e1.getStackTrace().toString() : "");
									logger.error(e2.getStackTrace() != null ? e2.getStackTrace().toString() : "");
									logger.error(e3.getStackTrace() != null ? e3.getStackTrace().toString() : "");
									logger.error(e4.getStackTrace() != null ? e4.getStackTrace().toString() : "");
									logger.error(e5.getStackTrace() != null ? e5.getStackTrace().toString() : "");
								}
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

	private static File convertToPdf(String uri, TipoModello tipoModello, boolean pdfa) throws Exception {
		File filePdf = null;
		InputStream is = null;
		if (tipoModello != null) {
			is = ConvertToPdfUtil.convertToPdf(uri, tipoModello == TipoModello.ODT, pdfa);
		} else {
			try {
				is = ConvertToPdfUtil.convertToPdf(uri, false, pdfa);
			} catch (Exception e1) {
				try {
					is = ConvertToPdfUtil.convertToPdf(uri, true, pdfa);
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

	private static TipoModello recuperaTipoModello(String tipoModello) {
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
			// d'ora in poi utilizzeremo solo i tipi modelli qui sotto, quelli sopra non verranno più utilizzati
			else if(TipoModelloDoc.DOCX_CON_PLACEHOLDER.getValue().equals(tipoModello)) {
				return TipoModello.DOCX;
			} else if(TipoModelloDoc.DOCX_CON_CAMPI_CONTROLLO.getValue().equals(tipoModello)) {
				return TipoModello.DOCX_FORM;
			} else if(TipoModelloDoc.ODT_CON_FREEMARKERS.getValue().equals(tipoModello)) {
				return TipoModello.ODT_FREEMARKERS;
			}
		}
		return null;
	}

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
	private static String getTemplateValues(String idProcess, HttpSession session) throws Exception {
		Locale locale =  UserUtil.getLocale(session);
		AurigaLoginBean loginBean = AurigaUserUtil.getLoginInfo(session);
		DmpkProcessesGetdatixmodellipratica dmpkProcessesGetdatixmodellipratica = new DmpkProcessesGetdatixmodellipratica();
		DmpkProcessesGetdatixmodellipraticaBean dmpkProcessesGetdatixmodellipraticaBean = new DmpkProcessesGetdatixmodellipraticaBean();
		dmpkProcessesGetdatixmodellipraticaBean.setCodidconnectiontokenin(loginBean.getToken());
		dmpkProcessesGetdatixmodellipraticaBean
				.setIduserlavoroin(StringUtils.isNotBlank(loginBean.getIdUserLavoro()) ? new BigDecimal(loginBean.getIdUserLavoro()) : null);
		dmpkProcessesGetdatixmodellipraticaBean.setIdprocessin(BigDecimal.valueOf(Long.valueOf(idProcess)));
		StoreResultBean<DmpkProcessesGetdatixmodellipraticaBean> result = dmpkProcessesGetdatixmodellipratica.execute(locale, loginBean,
				dmpkProcessesGetdatixmodellipraticaBean);
		if (result.isInError()) {
			throw new StoreException(result);
		}
		return result.getResultBean().getDatimodellixmlout();
	}
	
	/**
	 * Metodo per estreee il modello a partire dall'id
	 * 
	 * @param loginBean
	 * @param idModello
	 * @return Il bean del modello
	 * @throws Exception
	 */	
	public static ModelliDocBean getModello(HttpSession session, String idModello) throws Exception {
		AurigaLoginBean loginBean = AurigaUserUtil.getLoginInfo(session);
		// Creo il datasource settando la login bean
		ModelliDocDatasource lModelliDocDatasource = new ModelliDocDatasource();
		lModelliDocDatasource.setLoginBean(loginBean);
		lModelliDocDatasource.setSession(session);
		// Creo il bean per invocare la chiamata
		ModelliDocBean lModelliDocBean = new ModelliDocBean();
		lModelliDocBean.setIdModello(idModello);		
		// Carico il modello
		lModelliDocBean = lModelliDocDatasource.get(lModelliDocBean);		
		return lModelliDocBean;
	}
	
	private static FileDaFirmareBean fillFreeMarkerTemplate(HttpSession session, String templateValues, ModelliDocBean modello) throws Exception {
		Map<String, Object> mappaValori = createMapToFillTemplateFromSezioneCache(templateValues);
		modello.setValori(mappaValori);
		AurigaLoginBean loginBean = AurigaUserUtil.getLoginInfo(session);
		// Creo il datasource settando la login bean
		ModelliDocDatasource lModelliDocDatasource = new ModelliDocDatasource();
		lModelliDocDatasource.setLoginBean(loginBean);
		lModelliDocDatasource.setSession(session);
		// Salto la generazione del file html
		Map<String, String> extraParams = new LinkedHashMap<String, String>();
		extraParams.put("skipGenerazioneHtml", "true");
		lModelliDocDatasource.setExtraparams(extraParams);		
		return lModelliDocDatasource.generaDocDaModello(modello);
	}
	
	public static Map<String, String> getMappaTipiValoriFromModello(ModelliDocBean lModelliDocBean) {
		Map<String, String> mappaTipiValori = new LinkedHashMap<String, String>();
		// Lista delgi attributi associati
		List<AssociazioniAttributiCustomBean> associazioni = lModelliDocBean.getListaAssociazioniAttributiCustom();
		// Creo una mappa per avere il nome dell'attributo superiore
		if (associazioni != null) {
			for (int i = 0; i < associazioni.size(); i++) {
				AssociazioniAttributiCustomBean ass = associazioni.get(i);
				String nomeAttributo = StringUtils.isNotBlank(ass.getNomeAttributoCustom()) ? ass.getNomeAttributoCustom() : ass.getNomeVariabileModello();
				if (ass.getFlgComplex() != null && ass.getFlgComplex()) {
					mappaTipiValori.put(nomeAttributo, "LISTA");
					List<AssociazioniAttributiCustomBean> listaSottoAttributi = ass.getListaAssociazioniSottoAttributiComplex();
					if (listaSottoAttributi != null) {
						for (AssociazioniAttributiCustomBean sottoAttributo : listaSottoAttributi) {
							String numeroColonna = sottoAttributo.getNroColonna() + "";
							mappaTipiValori.put(nomeAttributo + "." + numeroColonna, StringUtils.isNotBlank(sottoAttributo.getTipoAttributo()) ? sottoAttributo.getTipoAttributo() : "TEXT-BOX");
						}
					}
				} else {
					mappaTipiValori.put(nomeAttributo, StringUtils.isNotBlank(ass.getTipoAttributo()) ? ass.getTipoAttributo() : "TEXT-BOX");
				}
			}
		}
		return mappaTipiValori;
	}
	
	public static Map<String, String> getMappaColonneListeFromModello(ModelliDocBean lModelliDocBean) {
		Map<String, String> mappaColonneListe = new LinkedHashMap<String, String>();
		// Lista delgi attributi associati
		List<AssociazioniAttributiCustomBean> associazioni = lModelliDocBean.getListaAssociazioniAttributiCustom();
		// Creo una mappa per avere il nome dell'attributo superiore
		if (associazioni != null) {
			for (int i = 0; i < associazioni.size(); i++) {
				AssociazioniAttributiCustomBean ass = associazioni.get(i);
				String nomeAttributoComplesso = StringUtils.isNotBlank(ass.getNomeAttributoCustom()) ? ass.getNomeAttributoCustom() : ass.getNomeAttributoLibero();
				if (ass.getFlgComplex() != null && ass.getFlgComplex()) {
					List<AssociazioniAttributiCustomBean> listaSottoAttributi = ass.getListaAssociazioniSottoAttributiComplex();
					if (listaSottoAttributi != null) {
						for (AssociazioniAttributiCustomBean sottoAttributo : listaSottoAttributi) {
							String numeroColonna = sottoAttributo.getNroColonna() + "";
							String nomeAttributoCustom;
							if ("attributoCustom".equalsIgnoreCase(sottoAttributo.getTipoAssociazioneVariabileModello())){
								nomeAttributoCustom = sottoAttributo.getNomeAttributoCustom();
							} else {
								nomeAttributoCustom = sottoAttributo.getNomeAttributoLibero();
							}
							mappaColonneListe.put(nomeAttributoComplesso + "." + numeroColonna, nomeAttributoCustom);
						}
					}
				}
			}
		}
		return mappaColonneListe;
	}
	
	public static Map<String, String> getMappaTipiValoriFromAttrDinamici(AttributiDinamiciOutputBean lAttributiDinamiciOutputBean) {
		// Lista delgi attributi associati
		List<AttributoBean> attributiAdd = lAttributiDinamiciOutputBean.getAttributiAdd();
		// Mappa dei dettagli di tutti gli attributi lista
		Map<String,List<DettColonnaAttributoListaBean>> mappaDettAttrLista = lAttributiDinamiciOutputBean.getMappaDettAttrLista();
		// Mappa dove viene salvata la mappatura
		Map<String, String> tipiAttributiDinamici = new LinkedHashMap<String, String>();
		if (attributiAdd != null) {
			// Ciclo sugli attributi associati al modello
			for (int i = 0; i < attributiAdd.size(); i++) {
				AttributoBean attr = attributiAdd.get(i);
				if (!"CUSTOM".equals(attr.getTipo())) {
					// Verifico il tipo di attributo
					if ("LISTA".equals(attr.getTipo())) {
						// E' una lista, devo ricavare il tipo di tutti i sotto attributi
						List<DettColonnaAttributoListaBean> dettAttrLista = mappaDettAttrLista.get(attr.getNome());
						// Ciclo su ogni dettaglio della lista
						for (DettColonnaAttributoListaBean dettAttr : dettAttrLista) {
							if (!"CUSTOM".equals(dettAttr.getTipo())) {
								if (!tipiAttributiDinamici.containsKey(attr.getNome() + "." + dettAttr.getNumero())) {
									tipiAttributiDinamici.put(attr.getNome() + "." + dettAttr.getNumero(), (String) dettAttr.getTipo());
								}
							}
						}
					}
					tipiAttributiDinamici.put(attr.getNome(), attr.getTipo());
				}
			}
		}
		return tipiAttributiDinamici;
	}
	
	public static Map<String, String> getMappaColonneListeFromAttrDinamici(AttributiDinamiciOutputBean lAttributiDinamiciOutputBean) {
		// Lista delgi attributi associati
		List<AttributoBean> attributiAdd = lAttributiDinamiciOutputBean.getAttributiAdd();
		// Mappatura dei dettagli di tutti gli attributi lista
		Map<String,List<DettColonnaAttributoListaBean>> mappaDettAttrLista = lAttributiDinamiciOutputBean.getMappaDettAttrLista();
		// Mappa dove viene salvata la mappatura
		Map<String, String> tipiAttributiDinamici = new LinkedHashMap<String, String>();
		if (attributiAdd != null) {
			// Ciclo sugli attributi associati al modello
			for (int i = 0; i < attributiAdd.size(); i++) {
				AttributoBean attr = attributiAdd.get(i);
				if (!"CUSTOM".equals(attr.getTipo())) {
					// Verifico il tipo di attributo
					if ("LISTA".equals(attr.getTipo())) {
						// E' una lista, devo ricavare il tipo di tutti i sotto attributi
						List<DettColonnaAttributoListaBean> dettAttrLista = mappaDettAttrLista.get(attr.getNome());
						// Ciclo su ogni dettaglio della lista
						for (DettColonnaAttributoListaBean dettAttr : dettAttrLista) {
							if (!"CUSTOM".equals(dettAttr.getTipo())) {
								if (!tipiAttributiDinamici.containsKey(attr.getNome() + "." + dettAttr.getNumero())) {
									tipiAttributiDinamici.put(attr.getNome() + "." + dettAttr.getNumero(), (String) dettAttr.getNome());
								}
							}
						}
					}
				}
			}
		}
		return tipiAttributiDinamici;
	}
	
	/************************************************************************************************/
	/** INIZIO METODI DI UTILITY DEI MODELLI FREEMARKER PER LA CREAZIONE DEL MODEL DA SEZIONE CACHE */
	/************************************************************************************************/
	
	/**
	 * Questi metodi di utility sono uguali a quelli contenuti nella classe it.eng.auriga.compiler.utility.FreeMarkerUtility di TemplateCompiler
	 * In caso di modifiche la logica va mentenuta uguale in entrambe le classi
	 */
	
	/**
	 * Crea la mappa dei valori da iniettare
	 * 
	 * @param xml Sezione cache contenente i dati da iniettare
	 * @return Mappa contenente la mappatura dei valori da iniettare
	 * @throws Exception
	 */
	public static Map<String, Object> createMapToFillTemplateFromSezioneCache(String xml) throws Exception {
		return createMapToFillTemplateFromSezioneCache(xml, false);
	}
	
	/**
	 * Crea la mappa dei valori da iniettare
	 * 
	 * @param xml Sezione cache contenente i dati da iniettare
	 * @param creaMappaPerContext se è true le colonne hanno indice col1, col2, col3 invece di 1, 2, 3
	 * @return Mappa contenente la mappatura dei valori da iniettare
	 * @throws Exception
	 */
	public static Map<String, Object> createMapToFillTemplateFromSezioneCache_old(String xml, boolean creaMappaPerContext) throws Exception {
        InputStream is = new ByteArrayInputStream(xml.getBytes());
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        dbf.setNamespaceAware(true);
        DocumentBuilder db = dbf.newDocumentBuilder();
        Document document = db.parse(is);
        document.getDocumentElement().normalize();
        Node sezioneCacheNode = document.getFirstChild();
    	NodeList figliSezioneCache = sezioneCacheNode.getChildNodes();
    	return createMapToFillTemplateFromNodeList(figliSezioneCache, creaMappaPerContext);
    }
	
	/**
	 * Crea la mappa dei valori da iniettare
	 * 
	 * @param xml Sezione cache contenente i dati da iniettare
	 * @param creaMappaPerContext se è true le colonne hanno indice col1, col2, col3 invece di 1, 2, 3
	 * @return Mappa contenente la mappatura dei valori da iniettare
	 * @throws Exception
	 */
	public static Map<String, Object> createMapToFillTemplateFromSezioneCache(String xmlSezioneCache, boolean creaMappaPerContext) throws Exception {
		SezioneCache sezioneCache = (SezioneCache) SingletonJAXBContext.getInstance().createUnmarshaller().unmarshal(new StringReader(xmlSezioneCache));
    	return createMapToFillTemplateFromNodeList(sezioneCache.getVariabile(), creaMappaPerContext);
    }
	
	/**
	 * 
	 * @param listaVariabili Lista dei nodi Varibile della sezione cache
	 * @param creaMappaPerContext se è true le colonne hanno indice col1, col2, col3 invece di 1, 2, 3
	 * @return Mappa contenente la mappatura dei valori da iniettare
	 */
    private static Map<String, Object> createMapToFillTemplateFromNodeList(List<Variabile> listaVariabili, boolean creaMappaPerContext) {
    	Map<String, Object> mappaToReturn = new LinkedHashMap<String, Object>();
    	
    	// Scorro tutti i nodi <Variaible>
    	for (Variabile variabile : listaVariabili) {
    		// Estraggo il nome della variabile
    		String nomeVariabile = togliSuffisso(variabile.getNome());
    		
    		// Estraggo il tipo della variabile
    		String tipoVariabile = getTipoVariabileSezioneCache(variabile);
    		if ("ValoreSemplice".equalsIgnoreCase(tipoVariabile)) {
    			// E' una variabile semplice, estraggo il valore e lo inserisco nella mappa
    			String valoreVariabile = variabile.getValoreSemplice();
    			if (creaMappaPerContext) {
    				mappaToReturn.put(nomeVariabile, (FreeMarkerModelliUtil.getTextModelValue(valoreVariabile)));
    			}else {
    				mappaToReturn.put(nomeVariabile, valoreVariabile);
    			}
    		} else if ("Lista".equalsIgnoreCase(tipoVariabile)) {
    			// E' una variabile lista, genero la lista da iniettare
    			List<Object> listaValori = getListaSezioneCache(variabile, nomeVariabile, creaMappaPerContext);
    			mappaToReturn.put(nomeVariabile, listaValori);
    		}
    	}
    	return mappaToReturn;
    }

	/**
	 * Crea la mappa dei valori da iniettare
	 * 
	 * @param listaNodi Lista dei nodi Varibile della sezione cache
	 * @param creaMappaPerContext se è true le colonne hanno indice col1, col2, col3 invece di 1, 2, 3
	 * @return Mappa contenente la mappatura dei valori da iniettare
	 */
    private static Map<String, Object> createMapToFillTemplateFromNodeList(NodeList listaNodi, boolean creaMappaPerContext) {
    	Map<String, Object> mappaToReturn = new LinkedHashMap<String, Object>();
    	// Scorro tutti i nodi <Variaible>
    	for (int i = 0; i < listaNodi.getLength(); i++){
    		Node node = listaNodi.item(i);
    		// Estraggo il nome della variabile
    		String nomeVariabile = togliSuffisso(getChildNodeValue(node, "Nome"));
    		
    		// Estraggo il tipo della variabile
    		String tipoVariabile = getTipoVariabileSezioneCache(node);
    		if ("ValoreSemplice".equalsIgnoreCase(tipoVariabile)) {
    			// E' una variabile semplice, estraggo il valore e lo inserisco nella mappa
    			String valoreVariabile = getChildNodeValue(node, "ValoreSemplice");
    			if (creaMappaPerContext) {
    				mappaToReturn.put(nomeVariabile, (FreeMarkerModelliUtil.getTextModelValue(valoreVariabile)));
    			}else {
    				mappaToReturn.put(nomeVariabile, valoreVariabile);
    			}
    		} else if ("Lista".equalsIgnoreCase(tipoVariabile)) {
    			// E' una variabile lista, genero la lista da iniettare
    			List<Object> listaValori = getListaSezioneCache(node, nomeVariabile, creaMappaPerContext);
    			mappaToReturn.put(nomeVariabile, listaValori);
    		}
    	}
    	return mappaToReturn;
    }
    
    /**
     * Restituisci il tipo di variabile di un nodo Variabile
     * 
     * @param nodo Il nodo Variabile da cui estrarre il tipo
     * @return Il tipo del nodo Variabile
     */
    private static String getTipoVariabileSezioneCache(Node nodo) {
    	NodeList figli = nodo.getChildNodes();
    	for (int i = 0; i < figli.getLength(); i++){
    		if ("ValoreSemplice".equalsIgnoreCase(figli.item(i).getNodeName())) {
    			return "ValoreSemplice";
    		}else if ("Lista".equalsIgnoreCase(figli.item(i).getNodeName())) {
    			return "Lista";
    		}
		}
    	return "";
    }
    
    /**
     * Restituisci il tipo di variabile di un nodo Variabile
     * 
     * @param nodo Il nodo Variabile di cui ricavare il tipo
     * @return Il tipo del nodo Variabile
     */
    private static String getTipoVariabileSezioneCache(Variabile variabile) {
    	if (variabile.getLista() == null) {
    		return "ValoreSemplice";
    	} else {
    		return "Lista";
    	}
    }
    
    /**
     * Restituisce il node value di un nodo figlio
     * 
     * @param nodo Il nodo da esaminare
     * @param childName Il nome del figlio da cui estrarre il node value
     * @return Il node value del figlio childName del node node
     */
    private static String getChildNodeValue(Node nodo, String childName) {
    	// Estraggo tutti i figli
    	NodeList figli = nodo.getChildNodes();
    	// Scorro i figli
    	for (int i = 0; i < figli.getLength(); i++){
    		// Verifico se è il figlio cercato
    		if (childName.equalsIgnoreCase(figli.item(i).getNodeName())) {
    			if (figli.item(i).getFirstChild() != null) {
    				// Estraggo il node value del nodo
    				return figli.item(i).getFirstChild().getNodeValue();
    			}
    		}
		}
    	return null;
    }
    
    /**
     * Trasforma un nodo Variabile di tipo Lista nella struttura List da iniettare nel modello
     * 
     * @param nodo Nodo Variabile di tipo lista
     * @param modello Bean del modello su cui iniettare i dati
     * @param nomeAttributoStrutturato Nome dell'attributo lista
     * @param creaMappaPerContext se è true le colonne hanno indice col1, col2, col3 invece di 1, 2, 3
     * @return List che rapprensta i dati da iniettare
     */
    private static List<Object> getListaSezioneCache(Node nodo, String nomeAttributoStrutturato, boolean creaMappaPerContext) {
    	List<Object> listaToReturn = new LinkedList<Object>();
    	// Verifico che il nodo abbia dei figli
    	if (nodo.getChildNodes() != null && nodo.getChildNodes().getLength() >= 1) {
    		// Estraggo il nodo Lista
    		Node nodoLista = getChildNodeByName(nodo, "Lista");
    		if (nodoLista != null) {
    			// Estraggo Tutte le Rige della Lista
    			NodeList lista = nodoLista.getChildNodes();
    			if (lista != null) {
    				if (lista.getLength() > 0) {
    					for (int i = 0; i < lista.getLength(); i++) {
    						if(lista.item(i) != null) {
	    						// La lista è composta da righe
	    	    				if ("Riga".equalsIgnoreCase(lista.item(i).getNodeName())) {
	    							// Per ogni riga creo la mappa dei valori Riga
	    							Map<String, Object> mappaValoriRiga = new LinkedHashMap<String, Object>(); 
									Node riga = lista.item(i);
									// Inserisco nella mappa tutti gli attributi della riga
									NodeList listaColonne = riga.getChildNodes();
									if (listaColonne != null && listaColonne.getLength() > 0) {
										for (int indiceColonna = 0; indiceColonna < listaColonne.getLength(); indiceColonna ++) {
				    						Node nodoColonna = listaColonne.item(indiceColonna);
				    						String numeroColonna = getAttributeValue(nodoColonna, "Nro");
				    						String valoreColonna = nodoColonna.getFirstChild() != null ? nodoColonna.getFirstChild().getNodeValue() : null;
				    						if (StringUtils.isNotBlank(numeroColonna)) {
				    							if (creaMappaPerContext) {
				    								mappaValoriRiga.put(numeroColonna, FreeMarkerModelliUtil.getTextModelValue(valoreColonna));
				    							} else {
				    								mappaValoriRiga.put(numeroColonna, valoreColonna);
				    							}
				    						}
				    					}
					    				listaToReturn.add(mappaValoriRiga);
				    				}
	    						}     						
	    						// La lista è composta da valori semplici
	    						else if ("ValoreSemplice".equalsIgnoreCase(lista.item(i).getNodeName())) {
	    							if (lista.item(i).getNodeType() == 1) {
										if(creaMappaPerContext) {
											listaToReturn.add(FreeMarkerModelliUtil.getTextModelValue(lista.item(i).getTextContent()));
										} else {
											listaToReturn.add(lista.item(i).getTextContent());
										}
									}
	    						}
    						}
    					}
    				}
    			}
    		}
		}
    	return listaToReturn;
    }
    
    /**
     * Trasforma un nodo Variabile di tipo Lista nella struttura List da iniettare nel modello
     * 
     * @param nodo Nodo Variabile di tipo lista
     * @param modello Bean del modello su cui iniettare i dati
     * @param nomeAttributoStrutturato Nome dell'attributo lista
     * @param creaMappaPerContext se è true le colonne hanno indice col1, col2, col3 invece di 1, 2, 3
     * @return List che rapprensta i dati da iniettare
     */
    private static List<Object> getListaSezioneCache(Variabile variabile, String nomeAttributoStrutturato, boolean creaMappaPerContext) {
    	List<Object> listaToReturn = new LinkedList<Object>();
    	// Verifico che il nodo abbia dei figli
    	if (variabile.getLista() != null && variabile.getLista().getRiga() != null && variabile.getLista().getRiga().size() >= 1) {
    		List<Riga> listaRighe = variabile.getLista().getRiga();
    		if (listaRighe != null) {
    			// Per ogni riga creo la mappa dei valori Riga
	    		for (Riga riga : listaRighe) {
					Map<String, Object> mappaValoriRiga = new LinkedHashMap<String, Object>(); 
					List<Colonna> listaColonne = riga.getColonna();
					// Inserisco nella mappa tutti gli attributi della riga
					if (listaColonne != null && listaColonne.size() > 0) {
						for (Colonna colonna : listaColonne) {
    						BigInteger numeroColonna = colonna.getNro();
    						String valoreColonna = colonna.getContent();
							if (creaMappaPerContext) {
								mappaValoriRiga.put(String.valueOf(numeroColonna.longValue()), FreeMarkerModelliUtil.getTextModelValue(valoreColonna));
							} else {
								mappaValoriRiga.put(String.valueOf(numeroColonna.longValue()), valoreColonna);
							}
    						
    					}
	    				listaToReturn.add(mappaValoriRiga);
    				}
				}
			}
		}
    	return listaToReturn;
    }
    
    /**
     * Retituisce un nodo figlio con un determinato node name
     * 
     * @param nodo Il nodo su cui ricercare il figlio
     * @param nodeName Il node name del figlio da ricercare
     * @return Il nodo figlio con il node name ricercato
     */
    private static Node getChildNodeByName(Node nodo, String nodeName) {
    	if (nodo.getChildNodes() != null){
    		for (int i = 0; i < nodo.getChildNodes().getLength(); i++) {
    			if (nodo.getChildNodes().item(i) != null && nodeName.equalsIgnoreCase(nodo.getChildNodes().item(i).getNodeName())){
    				return nodo.getChildNodes().item(i);
    			}
    		}
    	}
    	return null;
    }
    
    /**
     * Restitituisce il valore di un attributo del nodo
     * 
     * @param nodo Il nodo da cui estrarre lìattributo
     * @param attributeName Il nome dell'attributo da estrarre
     * @return Il valore dell'attributo cercato
     */
    private static String getAttributeValue(Node nodo, String attributeName){
    	if (nodo != null && nodo.getAttributes() != null && nodo.getAttributes().getNamedItem(attributeName) != null) {
    		return nodo.getAttributes().getNamedItem(attributeName).getNodeValue();
    	}
    	return null;
    }
    
    /**
     * Metodo che elimina il suffisso _Doc o _Ud dal una string
     * 
     * @param lString La stringa da elaborare 
     * @return La stringa ricevuta in ingresso privata del suffisso _Doc o _Ud
     */
    private static String togliSuffisso(String lString) {
    	if (StringUtils.isNotBlank(lString)) {
    		if (lString.endsWith("_Ud")) {
    			return lString.substring(0, lString.lastIndexOf("_Ud"));
    		}
    		if (lString.endsWith("_Doc")) {
    			return lString.substring(0, lString.lastIndexOf("_Doc"));
    		}
    	}
    	return lString;
    }
    
    /**
     * Dato un attributo complesso e un numero di colonna, restituisce il nome della variabile dell'attributo complesso associato a quella colonna
     *  
     * @param modello Bean del modello su cui iniettare i dati
     * @param nomeAttributoStrutturato Il nome dell'attributo strutturato di cui si vuole sapere l'associazione
     * @param numeroColonna Il numero della colonna
     * @return Il nome dell'attributo corrispondente alla data colonna
     */
//    private static String getNomeAttributoDaColonna (ModelliDocBean modello, String nomeAttributoStrutturato, String numeroColonna) {
//    	// Estraggo la mappa degli attributi complessi
//    	 Map<String, Map<String, String>> mappaAttributiDinamici = modello.getMappaAttributiDimanici();
//    	 if (mappaAttributiDinamici != null) {
//    		 // Estraggo l'attributo desiderato
//    		 Map<String, String> mappaCampiAttributoStrutturato = mappaAttributiDinamici.get(nomeAttributoStrutturato);
//    		 if (mappaCampiAttributoStrutturato != null && mappaCampiAttributoStrutturato.containsKey(numeroColonna)) {
//    			 // Estraggo il nome dell'attributo associato alla colonna
//    			 return mappaCampiAttributoStrutturato.get(numeroColonna);
//    		 }
//    	 }
//    	 return numeroColonna + "";
//    }
    
    /**********************************************************************************************/
	/** FINE METODI DI UTILITY DEI MODELLI FREEMARKER PER LA CREAZIONE DEL MODEL DA SEZIONE CACHE */
	/**********************************************************************************************/

}