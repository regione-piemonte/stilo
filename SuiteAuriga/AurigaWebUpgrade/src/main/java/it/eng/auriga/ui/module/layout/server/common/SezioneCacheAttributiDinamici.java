/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.auriga.database.store.bean.SchemaBean;
import it.eng.auriga.database.store.dmpk_core.bean.DmpkCoreAdddocBean;
import it.eng.auriga.database.store.dmpk_core.bean.DmpkCoreDel_ud_doc_verBean;
import it.eng.auriga.database.store.dmpk_processes.bean.DmpkProcessesGetdatiprocxatt_jBean;
import it.eng.auriga.database.store.result.bean.StoreResultBean;
import it.eng.auriga.exception.StoreException;
import it.eng.auriga.module.business.beans.AurigaLoginBean;
import it.eng.auriga.ui.module.layout.server.attributiDinamici.datasource.bean.DocumentBean;
import it.eng.auriga.ui.module.layout.server.pratiche.datasource.bean.XmlDatiProcOutBean;
import it.eng.client.DmpkCoreAdddoc;
import it.eng.client.DmpkCoreDel_ud_doc_ver;
import it.eng.client.DmpkProcessesGetdatiprocxatt_j;
import it.eng.client.GestioneDocumenti;
import it.eng.document.function.bean.CreaModDocumentoInBean;
import it.eng.document.function.bean.FileInfoBean;
import it.eng.document.function.bean.FolderCustom;
import it.eng.document.function.bean.GenericFile;
import it.eng.document.function.bean.RebuildedFile;
import it.eng.document.function.bean.TipoFile;
import it.eng.document.function.bean.VersionaDocumentoInBean;
import it.eng.document.function.bean.VersionaDocumentoOutBean;
import it.eng.jaxb.variabili.SezioneCache;
import it.eng.jaxb.variabili.SezioneCache.Variabile;
import it.eng.jaxb.variabili.SezioneCache.Variabile.Lista;
import it.eng.services.fileop.InfoFileUtility;
import it.eng.spring.utility.SpringAppContext;
import it.eng.utility.DocumentConfiguration;
import it.eng.utility.module.config.ModuleConfig;
import it.eng.utility.storageutil.GenericStorageInfo;
import it.eng.utility.storageutil.StorageService;
import it.eng.utility.storageutil.impl.StorageServiceImpl;
import it.eng.utility.ui.module.core.server.datasource.AbstractDataSource;
import it.eng.utility.ui.servlet.bean.MimeTypeFirmaBean;
import it.eng.utility.ui.user.AurigaUserUtil;
import it.eng.utility.ui.user.UserUtil;
import it.eng.xml.XmlUtilityDeserializer;
import it.eng.xml.XmlUtilitySerializer;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.apache.commons.beanutils.BeanUtilsBean2;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

public class SezioneCacheAttributiDinamici {

	private static Logger mLogger = Logger.getLogger(SezioneCacheAttributiDinamici.class);
	private static StorageService storageService;

	public static SezioneCache createSezioneCacheAttributiDinamici(String idProcess, Map<String, Object> values, Map<String, String> types, HttpSession session)
			throws Exception {

		// Inizializzo lo storage
		initStorageService();

		SezioneCache scAttributi = new SezioneCache();
		if (values != null && types != null) {
			for (String attrName : types.keySet()) {
				// se contiene il punto è l'attributo relativo alla colonna di un attributo lista, quindi non lo considero
				if(!attrName.contains(".")) {
					Object attrValue = values.get(attrName);
					String tipo = types.get(attrName);
					Variabile var = new Variabile();
					var.setNome(attrName);
					if (attrValue != null) {
						if ("LISTA".equals(tipo)) {
							Lista listaValori = new Lista();
							for (HashMap<String, Object> map : (List<HashMap<String, Object>>) attrValue) {
								it.eng.jaxb.variabili.SezioneCache.Variabile.Lista.Riga riga = new it.eng.jaxb.variabili.SezioneCache.Variabile.Lista.Riga();
								for (String nro : map.keySet()) {
									it.eng.jaxb.variabili.SezioneCache.Variabile.Lista.Riga.Colonna col = new it.eng.jaxb.variabili.SezioneCache.Variabile.Lista.Riga.Colonna();
									col.setNro(BigInteger.valueOf(Integer.parseInt(nro)));
									String tipoCol = types.get(attrName + "." + nro);
									if (map.get(nro) != null) {
										if ("DATE".equals(tipoCol)) {
											String valueStr = String.valueOf(map.get(nro));
											try {
												col.setContent(StringUtils.isNotBlank(valueStr) ? new SimpleDateFormat(AbstractDataSource.FMT_STD_DATA).format(new SimpleDateFormat(
													AbstractDataSource.DATE_ATTR_FORMAT).parse(valueStr)) : "");												
											} catch(Exception e) {
												col.setContent(valueStr);
											}
										} else if ("DATETIME".equals(tipoCol)) {
											String valueStr = String.valueOf(map.get(nro));
											try {
												col.setContent(StringUtils.isNotBlank(valueStr) ? new SimpleDateFormat(AbstractDataSource.FMT_STD_TIMESTAMP).format(new SimpleDateFormat(
													AbstractDataSource.DATETIME_ATTR_FORMAT).parse(valueStr)) : "");
											} catch(Exception e) {
												col.setContent(valueStr);
											}
										} else if ("CHECK".equals(tipoCol)) {
											boolean valueBool = map.get(nro) != null ? (Boolean) map.get(nro) : false;
											col.setContent(valueBool ? "1" : "0");
										} else if ("DOCUMENT".equals(tipoCol)) {
											Map valueMap = (Map) map.get(nro);
											col.setContent(insertOrUpdateDocument(idProcess, valueMap, session));
										} else {
											col.setContent(String.valueOf(map.get(nro)));
										}
									} else {
										col.setContent("");
									}
									riga.getColonna().add(col);
								}
								listaValori.getRiga().add(riga);
							}
							var.setLista(listaValori);
						} else if ("DATE".equals(tipo)) {
							String valueStr = String.valueOf(attrValue);
							try {
								var.setValoreSemplice(StringUtils.isNotBlank(valueStr) ? new SimpleDateFormat(AbstractDataSource.FMT_STD_DATA).format(new SimpleDateFormat(
									AbstractDataSource.DATE_ATTR_FORMAT).parse(valueStr)) : "");
							} catch(Exception e) {
								var.setValoreSemplice(valueStr);
							}
						} else if ("DATETIME".equals(tipo)) {
							String valueStr = String.valueOf(attrValue);
							try {
								var.setValoreSemplice(StringUtils.isNotBlank(valueStr) ? new SimpleDateFormat(AbstractDataSource.FMT_STD_TIMESTAMP).format(new SimpleDateFormat(
									AbstractDataSource.DATETIME_ATTR_FORMAT).parse(valueStr)) : "");
							} catch(Exception e) {
								var.setValoreSemplice(valueStr);
							}
						} else if ("CHECK".equals(tipo)) {
							boolean valueBool = (Boolean) attrValue;
							var.setValoreSemplice(valueBool ? "1" : "0");
						} else if ("DOCUMENT".equals(tipo)) {
							Map valueMap = (Map) attrValue;
							var.setValoreSemplice(insertOrUpdateDocument(idProcess, valueMap, session));
						} else {
							var.setValoreSemplice(String.valueOf(attrValue));
						}
					} else {
						var.setValoreSemplice("");
					}
					if (!var.getNome().toUpperCase().equals("__GWT_OBJECTID")) {
						scAttributi.getVariabile().add(var);
					}
				}
			}
		}
		return scAttributi;
	}

	public static Variabile createVariabileSemplice(String nome, String valoreSemplice) {
		Variabile var = new Variabile();
		var.setNome(nome);
		var.setValoreSemplice(valoreSemplice);
		return var;
	}

	public static Variabile createVariabileLista(String nome, Lista lista) {
		Variabile var = new Variabile();
		var.setNome(nome);
		var.setLista(lista);
		return var;
	}
	
	public static String insertOrUpdateDocument(String idProcess, Map lMap, HttpSession session) throws Exception {
		String idUd = lMap != null ? (String) lMap.get("idUd") : null;
		String idDoc = lMap != null ? (String) lMap.get("idDoc") : null;
		String uriFile = lMap != null ? (String) lMap.get("uriFile") : null;
		String nomeFile = lMap != null ? (String) lMap.get("nomeFile") : null;
		return insertOrUpdateDocument(idProcess, idUd, idDoc, uriFile, nomeFile, session);
	}
	
	public static String insertOrUpdateDocument(String idProcess, DocumentBean lDocumentBean, HttpSession session) throws Exception {
		String idUd = lDocumentBean != null ? lDocumentBean.getIdUd() : null;
		String idDoc = lDocumentBean != null ? lDocumentBean.getIdDoc() : null;
		String uriFile = lDocumentBean != null ? lDocumentBean.getUriFile() : null;
		String nomeFile = lDocumentBean != null ? lDocumentBean.getNomeFile() : null;
		return insertOrUpdateDocument(idProcess, idUd, idDoc, uriFile, nomeFile, session);
	}

	public static String insertOrUpdateDocument(String idProcess, String idUd, String idDoc, String uriFile, String nomeFile, HttpSession session) throws Exception {

		AurigaLoginBean loginBean = AurigaUserUtil.getLoginInfo(session);

		String token = loginBean.getToken();
		String idUserLavoro = loginBean.getIdUserLavoro();

		if (StringUtils.isNotBlank(uriFile)) {

			if (StringUtils.isBlank(idUd)) {

				DmpkCoreAdddocBean lAdddocInput = new DmpkCoreAdddocBean();
				lAdddocInput.setCodidconnectiontokenin(token);
				lAdddocInput.setIduserlavoroin(StringUtils.isNotBlank(idUserLavoro) ? new BigDecimal(idUserLavoro) : null);

				CreaModDocumentoInBean attributiUdDoc = new CreaModDocumentoInBean();
				attributiUdDoc.setOggetto(null);
				attributiUdDoc.setNomeDocType(null);

				if (StringUtils.isNotBlank(idProcess)) {

					SchemaBean schemaBean = new SchemaBean();
					schemaBean.setSchema(loginBean.getSchema());

					DmpkProcessesGetdatiprocxatt_jBean input = new DmpkProcessesGetdatiprocxatt_jBean();
					input.setIdprocessin(new BigDecimal(idProcess));
					input.setIduserin(StringUtils.isNotBlank(loginBean.getIdUserLavoro()) ? new BigDecimal(loginBean.getIdUserLavoro()) : loginBean.getIdUser());

					DmpkProcessesGetdatiprocxatt_j dmpkProcessesGetdatiprocxatt_j = new DmpkProcessesGetdatiprocxatt_j();
					StoreResultBean<DmpkProcessesGetdatiprocxatt_jBean> output = dmpkProcessesGetdatiprocxatt_j.execute(UserUtil.getLocale(session),
							schemaBean, input);

					if (output.isInError()) {
						throw new StoreException(output);
					}

					if (StringUtils.isNotBlank(output.getResultBean().getXmldatiprocout())) {
						XmlDatiProcOutBean scXmlDatiProc = new XmlDatiProcOutBean();
						XmlUtilityDeserializer lXmlUtilityDeserializer = new XmlUtilityDeserializer();
						scXmlDatiProc = lXmlUtilityDeserializer.unbindXml(output.getResultBean().getXmldatiprocout(), XmlDatiProcOutBean.class);
						if (scXmlDatiProc != null) {
							List<FolderCustom> listaFolderCustom = new ArrayList<FolderCustom>();
							FolderCustom folderCustom = new FolderCustom();
							folderCustom.setId(scXmlDatiProc.getIdFolder());
							listaFolderCustom.add(folderCustom);
							attributiUdDoc.setFolderCustom(listaFolderCustom);
						}
					}

				}

				XmlUtilitySerializer lXmlUtilitySerializer = new XmlUtilitySerializer();
				lAdddocInput.setAttributiuddocxmlin(lXmlUtilitySerializer.bindXml(attributiUdDoc));

				DmpkCoreAdddoc lAdddoc = new DmpkCoreAdddoc();
				StoreResultBean<DmpkCoreAdddocBean> lAdddocOutput = lAdddoc.execute(UserUtil.getLocale(session), loginBean, lAdddocInput);

				if (StringUtils.isNotBlank(lAdddocOutput.getDefaultMessage())) {
					String message = "DMPK_CORE.AddDoc: " + lAdddocOutput.getDefaultMessage();
					if (lAdddocOutput.isInError()) {
						throw new Exception(message);
					} else {
						mLogger.warn(message);
					}
				}

				idUd = String.valueOf(lAdddocOutput.getResultBean().getIdudout());
				idDoc = String.valueOf(lAdddocOutput.getResultBean().getIddocout());

			}

			RebuildedFile lRebuildedFile = new RebuildedFile();
			lRebuildedFile.setIdDocumento(new BigDecimal(idDoc));
			lRebuildedFile.setFile(storageService.extractFile(uriFile));

			MimeTypeFirmaBean lMimeTypeFirmaBean = new MimeTypeFirmaBean();
			InfoFileUtility lFileUtility = new InfoFileUtility();
			lMimeTypeFirmaBean = lFileUtility.getInfoFromFile(lRebuildedFile.getFile().toURI().toString(), lRebuildedFile.getFile().getName(), false, null);
			lMimeTypeFirmaBean.setFirmato(true);
			lMimeTypeFirmaBean.setFirmaValida(true);
			lMimeTypeFirmaBean.setConvertibile(false);
			lMimeTypeFirmaBean.setDaScansione(false);

			FileInfoBean lFileInfoBean = new FileInfoBean();
			lFileInfoBean.setTipo(TipoFile.PRIMARIO);
			GenericFile lGenericFile = new GenericFile();
			lGenericFile.setMimetype(lMimeTypeFirmaBean.getMimetype());
			lGenericFile.setDisplayFilename(nomeFile);
			lGenericFile.setImpronta(lMimeTypeFirmaBean.getImpronta());
			DocumentConfiguration lDocumentConfiguration = (DocumentConfiguration) SpringAppContext.getContext().getBean("DocumentConfiguration");
			lGenericFile.setAlgoritmo(lDocumentConfiguration.getAlgoritmo().value());
			lGenericFile.setEncoding(lDocumentConfiguration.getEncoding().value());
			lFileInfoBean.setAllegatoRiferimento(lGenericFile);

			lRebuildedFile.setInfo(lFileInfoBean);

			VersionaDocumentoInBean lVersionaDocumentoInBean = new VersionaDocumentoInBean();
			BeanUtilsBean2.getInstance().getPropertyUtils().copyProperties(lVersionaDocumentoInBean, lRebuildedFile);

			GestioneDocumenti lGestioneDocumenti = new GestioneDocumenti();
			VersionaDocumentoOutBean lVersionaDocumentoOutput = lGestioneDocumenti.versionadocumento(UserUtil.getLocale(session), loginBean,
					lVersionaDocumentoInBean);

			if (lVersionaDocumentoOutput.getDefaultMessage() != null) {
				mLogger.error("VersionaDocumento: " + lVersionaDocumentoOutput.getDefaultMessage());
				throw new StoreException(lVersionaDocumentoOutput);
			}

		} else if (StringUtils.isNotBlank(idUd)) {

			DmpkCoreDel_ud_doc_verBean lDelUdDocVerInput = new DmpkCoreDel_ud_doc_verBean();
			lDelUdDocVerInput.setFlgtipotargetin("U");
			lDelUdDocVerInput.setIduddocin(new BigDecimal(idUd));
			lDelUdDocVerInput.setFlgcancfisicain(new Integer(1));

			DmpkCoreDel_ud_doc_ver lDelUdDocVer = new DmpkCoreDel_ud_doc_ver();
			StoreResultBean<DmpkCoreDel_ud_doc_verBean> lDelUdDocVerOutput = lDelUdDocVer.execute(UserUtil.getLocale(session), loginBean, lDelUdDocVerInput);

			if (StringUtils.isNotBlank(lDelUdDocVerOutput.getDefaultMessage())) {
				String message = "DMPK_CORE.DEL_UD_DOC: " + lDelUdDocVerOutput.getDefaultMessage();
				if (lDelUdDocVerOutput.isInError()) {
					throw new StoreException(message);
				} else {
					mLogger.warn(message);
				}
			}

			idUd = null;

		}

		return idUd != null ? idUd : "";
	}

	private static void initStorageService() {
		if (storageService == null) {
			storageService = StorageServiceImpl.newInstance(new GenericStorageInfo() {

				public String getUtilizzatoreStorageId() {
					ModuleConfig mc = (ModuleConfig) SpringAppContext.getContext().getBean("moduleConfig");
					mLogger.debug("Recuperato module config");
					if (mc != null && StringUtils.isNotBlank(mc.getIdDbStorage())) {
						mLogger.debug("Id Storage vale " + mc.getIdDbStorage());
						return mc.getIdDbStorage();
					} else {
						mLogger.error("L'identificativo del DB di storage non è correttamente configurato, "
								+ "controllare il file di configurazione del modulo.");
						return null;
					}
				}
			});
		}
	}

}
