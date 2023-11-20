/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.bind.JAXBException;

import org.apache.commons.beanutils.BeanUtilsBean2;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.beans.BeansException;

import it.eng.auriga.database.store.dmpk_core.bean.DmpkCoreAdddocBean;
import it.eng.auriga.database.store.dmpk_core.bean.DmpkCoreUpddocudBean;
import it.eng.auriga.module.business.beans.AurigaLoginBean;
import it.eng.core.annotation.Operation;
import it.eng.core.annotation.Service;
import it.eng.core.business.HibernateUtil;
import it.eng.core.business.subject.SubjectBean;
import it.eng.core.business.subject.SubjectUtil;
import it.eng.document.function.bean.AllegatiBean;
import it.eng.document.function.bean.CreaModDocumentoInBean;
import it.eng.document.function.bean.CreaModDocumentoOutBean;
import it.eng.document.function.bean.DocumentoXmlOutBean;
import it.eng.document.function.bean.FilePrimarioBean;
import it.eng.document.function.bean.ListAllegatiBean;
import it.eng.document.function.bean.ListCreaModDocumentoInBean;
import it.eng.document.function.bean.ListCreaModDocumentoInBean.TipoOperazione;
import it.eng.document.function.bean.ListCreaModDocumentoOutBean;
import it.eng.document.function.bean.ListPrimariBean;
import it.eng.document.function.bean.RebuildedFile;
import it.eng.document.function.bean.RecuperaDocumentoInBean;
import it.eng.document.function.bean.RecuperaDocumentoOutBean;
import it.eng.document.function.bean.TipoFile;
import it.eng.document.function.bean.UrlVersione;
import it.eng.document.function.bean.VersionaDocumentoInBean;
import it.eng.document.function.bean.VersionaDocumentoOutBean;
import it.eng.document.sharepoint.SharePointUtil;
//import it.eng.service.CallServicesPGWeb;
//import it.eng.service.bean.ProtocolloBean;
import it.eng.spring.utility.SpringAppContext;
import it.eng.xml.XmlUtilitySerializer;

@Service(name = "GestioneMultiplaDocumenti")
public class GestioneMultiplaDocumenti extends GestioneDocumenti {

	private static Logger mLogger = Logger.getLogger(GestioneMultiplaDocumenti.class);

	@Operation(name = "creaModificaDocumento")
	public ListCreaModDocumentoOutBean creaModificaDocumento(AurigaLoginBean pAurigaLoginBean, ListCreaModDocumentoInBean pListCreaModDocumentoInBean,
			ListPrimariBean pListPrimariBean, ListAllegatiBean pListAllegatiBean) throws Exception {
		ListCreaModDocumentoOutBean lListCreaModDocumentoOutBean = new ListCreaModDocumentoOutBean();
		List<CreaModDocumentoOutBean> lListResult = new ArrayList<CreaModDocumentoOutBean>();
		List<CreaModDocumentoOutBean> lListResultFinale = new ArrayList<CreaModDocumentoOutBean>();
		SubjectBean subject = SubjectUtil.subject.get();

		subject.setIdDominio(pAurigaLoginBean.getSchema());
		SubjectUtil.subject.set(subject);
		Session session = HibernateUtil.begin();
		Transaction lTransaction = session.beginTransaction();
		Map<String, List<RebuildedFile>> versioniTotali = new HashMap<String, List<RebuildedFile>>();
		Map<String, List<RebuildedFile>> versioniDaRimuovereTotali = new HashMap<String, List<RebuildedFile>>();
		Map<String, List<RebuildedFile>> allegatiDaRimuovereTotali = new HashMap<String, List<RebuildedFile>>();

		Map<String, CreaModDocumentoInBean> documenti = new HashMap<String, CreaModDocumentoInBean>();
		try {

			int count = 0;
			for (CreaModDocumentoInBean lCreaModDocumentoInBean : pListCreaModDocumentoInBean.getDocumenti()) {
				CreaModDocumentoOutBean lCreaModDocumentoOutBean = null;
				if (pListCreaModDocumentoInBean.getOperazioni().get(count) == TipoOperazione.CREA) {
					lCreaModDocumentoOutBean = gestisciCreaDocumento(session, lCreaModDocumentoInBean, pAurigaLoginBean,
							pListPrimariBean.getPrimari().get(count), pListAllegatiBean.getAllegati().get(count), versioniTotali);
				} else if (pListCreaModDocumentoInBean.getOperazioni().get(count) == TipoOperazione.CREA) {
					lCreaModDocumentoOutBean = gestisciModificaDocumento(session, pAurigaLoginBean, pListCreaModDocumentoInBean.getIdUds().get(count),
							pListCreaModDocumentoInBean.getIdDocPrimari().get(count), lCreaModDocumentoInBean, pListPrimariBean.getPrimari().get(count),
							pListAllegatiBean.getAllegati().get(count), versioniTotali, versioniDaRimuovereTotali, allegatiDaRimuovereTotali);
				}
				lListResult.add(lCreaModDocumentoOutBean);
				if (StringUtils.isNotEmpty(lCreaModDocumentoOutBean.getDefaultMessage())) {
					throw new Exception(lCreaModDocumentoOutBean.getDefaultMessage());
				}
				documenti.put(lCreaModDocumentoOutBean.getIdUd().longValue() + "", lCreaModDocumentoInBean);
				count++;
			}

			lTransaction.commit();

			for (CreaModDocumentoOutBean lCreaModDocumentoOutBean : lListResult) {
				CreaModDocumentoOutBean lCreaModDocumentoOutBeanNew = new CreaModDocumentoOutBean();
				lCreaModDocumentoOutBeanNew.setIdUd(lCreaModDocumentoOutBean.getIdUd());
				Map<String, String> fileErrors = aggiungiFiles(pAurigaLoginBean, versioniTotali.get(lCreaModDocumentoOutBeanNew.getIdUd().longValue() + ""));
				rimuoviFiles(pAurigaLoginBean, versioniDaRimuovereTotali.get(lCreaModDocumentoOutBeanNew.getIdUd().longValue() + ""),
						allegatiDaRimuovereTotali.get(lCreaModDocumentoOutBean.getIdUd().longValue() + ""));
				lCreaModDocumentoOutBean.setFileInErrors(fileErrors);
				recuperaEstremi(pAurigaLoginBean, documenti.get(lCreaModDocumentoOutBeanNew.getIdUd().longValue() + ""), lCreaModDocumentoOutBeanNew);

				if (SharePointUtil.isIntegratoConSharePoint(pAurigaLoginBean)) {
					SharePointUtil shUtil = (SharePointUtil) SpringAppContext.getContext().getBean("sharePointUtil");
					shUtil.aggiornaMetadati(pAurigaLoginBean, lCreaModDocumentoOutBeanNew);
				}
				lListResultFinale.add(lCreaModDocumentoOutBeanNew);
			}
			lListCreaModDocumentoOutBean.setRisultati(lListResultFinale);
		} catch (Exception e) {
			lListCreaModDocumentoOutBean.setRisultati(lListResult);
			return lListCreaModDocumentoOutBean;
		} finally {
			HibernateUtil.release(session);
		}
		return lListCreaModDocumentoOutBean;
	}

	private CreaModDocumentoOutBean gestisciCreaDocumento(Session session, CreaModDocumentoInBean pCreaDocumentoInBean, AurigaLoginBean pAurigaLoginBean,
			FilePrimarioBean pFilePrimarioBean, AllegatiBean pAllegatiBean, Map<String, List<RebuildedFile>> versioniTotali) throws JAXBException,
			IllegalAccessException, InvocationTargetException, NoSuchMethodException {

		DmpkCoreAdddocBean lAdddocBean = new DmpkCoreAdddocBean();
		lAdddocBean.setCodidconnectiontokenin(pAurigaLoginBean.getToken());
		lAdddocBean.setIduserlavoroin(getIdUserLavoro(pAurigaLoginBean));

		XmlUtilitySerializer lXmlUtilitySerializer = new XmlUtilitySerializer();
		lAdddocBean.setAttributiuddocxmlin(lXmlUtilitySerializer.bindXml(pCreaDocumentoInBean));

		lAdddocBean.setFlgautocommitin(0); // blocco l'autocommit

		// Preparo la risposta
		BigDecimal idUdResult = null;
		// Preparo i files
		List<RebuildedFile> versioni = new ArrayList<RebuildedFile>();

		CreaModDocumentoOutBean lCreaDocumentoOutBean = new CreaModDocumentoOutBean();
		try {
			try {
				idUdResult = manageAddDocs(pAurigaLoginBean, pFilePrimarioBean, pAllegatiBean, lAdddocBean, lXmlUtilitySerializer, versioni, session);
				/*
				if (pCreaDocumentoInBean.getTipoNumerazioni() != null) {
					TipoNumerazioneBean tipoNumerazione = pCreaDocumentoInBean.getTipoNumerazioni().get(0);

					// AGGIUNTA GESTIONE PROTOCOLLAZIONE MILANO
					if (tipoNumerazione.getSigla() != null && "PGWEB".equals(tipoNumerazione.getSigla())) {
						mLogger.error("PGWEB - RECUPERO I PARAMETRI NECESSARI PER LA PROTOCOLLAZIONE");
						PGWebUtil pgWebUtil = (PGWebUtil) SpringAppContext.getContext().getBean("pgWebUtil");
						ProtocolloBean protocolloBean = null;

						String idCls = null;
						if (pCreaDocumentoInBean.getClassifichefascicoli() != null && !pCreaDocumentoInBean.getClassifichefascicoli().isEmpty())
							if (pCreaDocumentoInBean.getClassifichefascicoli().get(0) != null)
								idCls = pCreaDocumentoInBean.getClassifichefascicoli().get(0).getProvCIClassif();

						// PROTOCOLLAZIONE IN USCITA
						if (pCreaDocumentoInBean.getFlgTipoProv() != null && "U".equals(pCreaDocumentoInBean.getFlgTipoProv().toString())) {

							String id_struttCom = null;
							if (pCreaDocumentoInBean.getMittenti() != null && !pCreaDocumentoInBean.getMittenti().isEmpty())
								if (pCreaDocumentoInBean.getMittenti().get(0) != null)
									id_struttCom = pCreaDocumentoInBean.getMittenti().get(0).getProvCIUo();

							// CREO LA PROTOCOLLAZIONE IN USCITA
							mLogger.error("PGWEB - CREO LA PROTOCOLLAZIONE IN USCITA");
							protocolloBean = CallServicesPGWeb.creaProtocolloDocInUscita(pgWebUtil.getWsUrlDocumento(), pgWebUtil.getUtenteProtocollazione(),
									pgWebUtil.getDominioProtocolloazione(), pgWebUtil.getPasswordProtocolloazione(), pCreaDocumentoInBean.getOggetto(), idCls,
									"C", id_struttCom);

							// MOVIMENTO LA PROTOCOLLAZIONE IN USCITA
							mLogger.error("PGWEB - SETTO IL DESTINATARIO");

							String destinatario = null;
							if (pCreaDocumentoInBean.getMittenti() != null && !pCreaDocumentoInBean.getMittenti().isEmpty())
								if (pCreaDocumentoInBean.getMittenti().get(0) != null)
									destinatario = pCreaDocumentoInBean.getMittenti().get(0).getDenominazioneCognome() + " "
											+ pCreaDocumentoInBean.getMittenti().get(0).getNome();

							mLogger.error("PGWEB - CHIAMO IL SERVIZIO DI MOVIMENTAZIONE");
							CallServicesPGWeb.movimentoProtocollo(pgWebUtil.getWsUrlMovimento(), protocolloBean.getIdDocumento(), pgWebUtil.getIdStruttCom(),
									pgWebUtil.getUtenteProtocollazione(), pgWebUtil.getDominioProtocolloazione(), pgWebUtil.getPasswordProtocolloazione(),
									destinatario, "S");
						}
						// PROTOCOLLAZIONE IN ENTRATA
						if (pCreaDocumentoInBean.getFlgTipoProv() != null && "E".equals(pCreaDocumentoInBean.getFlgTipoProv().toString())) {

							String denomCogn = null;
							String nome = null;
							if (pCreaDocumentoInBean.getMittenti() != null && !pCreaDocumentoInBean.getMittenti().isEmpty())
								if (pCreaDocumentoInBean.getMittenti().get(0) != null) {
									denomCogn = pCreaDocumentoInBean.getMittenti().get(0).getDenominazioneCognome();
									nome = pCreaDocumentoInBean.getMittenti().get(0).getNome();
								}

							// CREO LA PROTOCOLLAZIONE IN ENTRATA
							mLogger.error("PGWEB - CREO LA PROTOCOLLAZIONE IN ENTRATA");
							protocolloBean = CallServicesPGWeb.creaProtocolloDocInEntrata(pgWebUtil.getWsUrlDocumento(), pgWebUtil.getUtenteProtocollazione(),
									pgWebUtil.getDominioProtocolloazione(), pgWebUtil.getPasswordProtocolloazione(), pCreaDocumentoInBean.getOggetto(), idCls,
									"P", denomCogn, nome, null, null, null, null, null);// "piva", "cf", "pg_mittNum", "pg_mittAnno", "pg_mittData");
						}

						// ProtocolloRicevuto
						ProtocolloRicevuto protocolloRicevuto = new ProtocolloRicevuto();
						protocolloRicevuto.setNumero(protocolloBean.getNumProt());
						protocolloRicevuto.setAnno(protocolloBean.getAnnoProt());
						protocolloRicevuto
								.setData(protocolloBean.getDataProt() != null ? new SimpleDateFormat("dd/MM/yyyy").parse(protocolloBean.getDataProt()) : null);
						pCreaDocumentoInBean.setProtocolloRicevuto(protocolloRicevuto);

						// DmpkCoreUpddocudBean
						DmpkCoreUpddocudBean lUpddocudBean = new DmpkCoreUpddocudBean();
						lUpddocudBean.setCodidconnectiontokenin(pAurigaLoginBean.getToken());
						lUpddocudBean.setIduserlavoroin(getIdUserLavoro(pAurigaLoginBean));
						lUpddocudBean.setAttributiuddocxmlin(lXmlUtilitySerializer.bindXmlCompleta(pCreaDocumentoInBean));
						lUpddocudBean.setFlgtipotargetin("U");
						lUpddocudBean.setIduddocin(idUdResult);

						final UpddocudImpl store = new UpddocudImpl();
						store.setBean(lUpddocudBean);

						LoginService lLoginService = new LoginService();
						lLoginService.login(pAurigaLoginBean);
						session.doWork(new Work() {

							@Override
							public void execute(Connection paramConnection) throws SQLException {
								paramConnection.setAutoCommit(false);
								store.execute(paramConnection);
							}
						});

						StoreResultBean<DmpkCoreUpddocudBean> result = new StoreResultBean<DmpkCoreUpddocudBean>();
						AnalyzeResult.analyze(lUpddocudBean, result);
						result.setResultBean(lUpddocudBean);

						if (result.isInError()) {
							throw new StoreException(result);
						}
					}
					// FINE AGGIUNTA GESTIONE PROTOCOLLAZIONE MILANO
				}
				*/
			} catch (Exception e) {
				mLogger.error("Errore " + e.getMessage(), e);
				if (e instanceof StoreException) {
					BeanUtilsBean2.getInstance().copyProperties(lCreaDocumentoOutBean, ((StoreException) e).getError());
					return lCreaDocumentoOutBean;
				} else {
					lCreaDocumentoOutBean.setDefaultMessage(e.getMessage());
					return lCreaDocumentoOutBean;
				}
			}
			lCreaDocumentoOutBean.setIdUd(idUdResult);
			// Parte di versionamento
			versioniTotali.put(idUdResult.longValue() + "", versioni);
		} catch (Exception e) {
			if (e instanceof StoreException) {
				mLogger.error("Errore " + e.getMessage(), e);
				BeanUtilsBean2.getInstance().copyProperties(lCreaDocumentoOutBean, ((StoreException) e).getError());
				return lCreaDocumentoOutBean;
			} else {
				mLogger.error("Errore " + e.getMessage(), e);
				lCreaDocumentoOutBean.setDefaultMessage(e.getMessage() != null ? e.getMessage() : "Errore generico");
				return lCreaDocumentoOutBean;
			}
		}

		return lCreaDocumentoOutBean;
	}

	public CreaModDocumentoOutBean gestisciModificaDocumento(Session session, AurigaLoginBean pAurigaLoginBean, BigDecimal pIdUd, BigDecimal pIdDocPrimario,
			CreaModDocumentoInBean pModificaDocumentoInBean, FilePrimarioBean pFilePrimarioBean, AllegatiBean pAllegatiBean,
			Map<String, List<RebuildedFile>> versioniTotali, Map<String, List<RebuildedFile>> versioniDaRimuovereTotali,
			Map<String, List<RebuildedFile>> allegatiDaRimuovereTotali) throws JAXBException, IllegalAccessException, InvocationTargetException,
			NoSuchMethodException {

		CreaModDocumentoOutBean lModificaDocumentoOutBean = new CreaModDocumentoOutBean();

		BigDecimal idUd = (pIdUd != null) ? new BigDecimal(pIdUd.longValue() + "") : null;
		BigDecimal idDocPrimario = (pIdDocPrimario != null) ? new BigDecimal(pIdDocPrimario.longValue() + "") : null;

		RecuperaDocumentoInBean lRecuperaDocumentoInBean = new RecuperaDocumentoInBean();
		lRecuperaDocumentoInBean.setIdUd(idUd);
		RecuperoDocumenti lRecuperoDocumenti = new RecuperoDocumenti();
		RecuperaDocumentoOutBean lRecuperaDocumentoOutBean;

		DocumentoXmlOutBean lDocumentoXmlOutBean = null;
		try {
			lRecuperaDocumentoOutBean = lRecuperoDocumenti.loadDocumento(pAurigaLoginBean, lRecuperaDocumentoInBean);
			lDocumentoXmlOutBean = lRecuperaDocumentoOutBean.getDocumento();
		} catch (Exception e) {
			mLogger.error("Errore " + e.getMessage(), e);
			lModificaDocumentoOutBean.setDefaultMessage("Il documento da aggiornare è inesistente");
			return lModificaDocumentoOutBean;
		}

		DmpkCoreUpddocudBean lUpddocudBean = new DmpkCoreUpddocudBean();
		lUpddocudBean.setCodidconnectiontokenin(pAurigaLoginBean.getToken());
		lUpddocudBean.setIduserlavoroin(getIdUserLavoro(pAurigaLoginBean));

		XmlUtilitySerializer lXmlUtilitySerializer = new XmlUtilitySerializer();
		try {
			if (SharePointUtil.isIntegratoConSharePoint(pAurigaLoginBean)) {
				if (lDocumentoXmlOutBean.getFilePrimario() != null && lDocumentoXmlOutBean.getFilePrimario().getUri() != null) {
					SharePointUtil shUtil = (SharePointUtil) SpringAppContext.getContext().getBean("sharePointUtil");
					List<UrlVersione> uriList = shUtil.getUriPrecentiVersioni(pAurigaLoginBean, lDocumentoXmlOutBean.getFilePrimario().getUri());
					pModificaDocumentoInBean.setUriVerPrecSuSharePoint_Ver(uriList);
				}
			}
		} catch (BeansException e1) {
			mLogger.warn(e1);
		} catch (Exception e1) {
			mLogger.warn(e1);

		}
		lUpddocudBean.setAttributiuddocxmlin(lXmlUtilitySerializer.bindXmlCompleta(pModificaDocumentoInBean));

		lUpddocudBean.setFlgtipotargetin("D");
		lUpddocudBean.setIduddocin(idDocPrimario);
		lUpddocudBean.setFlgautocommitin(0); // blocco l'autocommit

		// Preparo i files
		List<RebuildedFile> versioni = new ArrayList<RebuildedFile>();
		List<RebuildedFile> versioniDaRimuovere = new ArrayList<RebuildedFile>();
		List<RebuildedFile> allegatiDaRimuovere = new ArrayList<RebuildedFile>();

		try {
			try {
				manageAddUpdDocs(pAurigaLoginBean, pFilePrimarioBean, pAllegatiBean, lUpddocudBean, lXmlUtilitySerializer, idUd, idDocPrimario,
						lDocumentoXmlOutBean, versioni, versioniDaRimuovere, allegatiDaRimuovere, session);
			} catch (Exception e) {
				mLogger.error("Errore " + e.getMessage(), e);
				if (e instanceof StoreException) {
					BeanUtilsBean2.getInstance().copyProperties(lModificaDocumentoOutBean, ((StoreException) e).getError());
					return lModificaDocumentoOutBean;
				} else {
					lModificaDocumentoOutBean.setDefaultMessage(e.getMessage());
					return lModificaDocumentoOutBean;
				}
			}
			lModificaDocumentoOutBean.setIdUd(idUd);
			// Parte di versionamento
			// Map<String, String> fileErrors = new HashMap<String, String>();
			versioniTotali.put(idUd.longValue() + "", versioni);
			versioniDaRimuovereTotali.put(idUd.longValue() + "", versioniDaRimuovere);
			allegatiDaRimuovereTotali.put(idUd.longValue() + "", allegatiDaRimuovere);
			// fileErrors.putAll(aggiungiFiles(pAurigaLoginBean, versioni));
			// fileErrors.putAll(rimuoviFiles(pAurigaLoginBean, versioniDaRimuovere, allegatiDaRimuovere));
			// lModificaDocumentoOutBean.setFileInErrors(fileErrors);
			// recuperaEstremi(pAurigaLoginBean, pModificaDocumentoInBean, lModificaDocumentoOutBean);

			if (SharePointUtil.isIntegratoConSharePoint(pAurigaLoginBean)) {
				SharePointUtil shUtil = (SharePointUtil) SpringAppContext.getContext().getBean("sharePointUtil");
				shUtil.aggiornaMetadati(pAurigaLoginBean, lModificaDocumentoOutBean);
			}

		} catch (Exception e) {
			if (e instanceof StoreException) {
				mLogger.error("Errore " + e.getMessage(), e);
				BeanUtilsBean2.getInstance().copyProperties(lModificaDocumentoOutBean, ((StoreException) e).getError());
				return lModificaDocumentoOutBean;
			} else {
				mLogger.error("Errore " + e.getMessage(), e);
				lModificaDocumentoOutBean.setDefaultMessage(e.getMessage() != null ? e.getMessage() : "Errore generico");
				return lModificaDocumentoOutBean;
			}
		}

		return lModificaDocumentoOutBean;
	}

	protected BigDecimal getIdUserLavoro(AurigaLoginBean pAurigaLoginBean) {
		return StringUtils.isNotBlank(pAurigaLoginBean.getIdUserLavoro()) ? new BigDecimal(pAurigaLoginBean.getIdUserLavoro()) : null;
	}

	protected Map<String, String> aggiungiFiles(AurigaLoginBean pAurigaLoginBean, List<RebuildedFile> versioni) {

		Map<String, String> fileErrors = new HashMap<String, String>();

		for (RebuildedFile lRebuildedFile : versioni) {
			try {
				VersionaDocumentoInBean lVersionaDocumentoInBean = new VersionaDocumentoInBean();
				BeanUtilsBean2.getInstance().getPropertyUtils().copyProperties(lVersionaDocumentoInBean, lRebuildedFile);
				VersionaDocumentoOutBean lVersionaDocumentoOutBean = versionaDocumento(pAurigaLoginBean, lVersionaDocumentoInBean);
				if (lVersionaDocumentoOutBean.getDefaultMessage() != null) {
					throw new Exception(lVersionaDocumentoOutBean.getDefaultMessage());
				}
			} catch (Exception e) {
				mLogger.error("Errore " + e.getMessage(), e);
				if (lRebuildedFile.getInfo().getTipo() == TipoFile.PRIMARIO) {
					fileErrors.put("0", "Il file primario " + lRebuildedFile.getInfo().getAllegatoRiferimento().getDisplayFilename()
							+ " non è stato salvato correttamente." + (StringUtils.isNotBlank(e.getMessage()) ? " Motivo: " + e.getMessage() : ""));
				} else if (lRebuildedFile.getInfo().getTipo() == TipoFile.ALLEGATO) {
					fileErrors.put("" + lRebuildedFile.getInfo().getPosizione(), "Il file allegato "
							+ lRebuildedFile.getInfo().getAllegatoRiferimento().getDisplayFilename() + " in posizione "
							+ lRebuildedFile.getInfo().getPosizione() + " non è stato salvato correttamente."
							+ (StringUtils.isNotBlank(e.getMessage()) ? " Motivo: " + e.getMessage() : ""));
				}

			}
		}

		return fileErrors;
	}
}
