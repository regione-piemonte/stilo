/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.axis.message.SOAPEnvelope;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.jdbc.Work;

import it.eng.auriga.database.store.dmpk_core.bean.DmpkCoreUpddocudBean;
import it.eng.auriga.database.store.dmpk_core.store.impl.UpddocudImpl;
import it.eng.auriga.database.store.result.bean.StoreResultBean;
import it.eng.auriga.module.business.beans.AurigaLoginBean;
import it.eng.document.function.StoreException;
import it.eng.document.function.bean.CreaModDocumentoInBean;
import it.eng.jaxb.variabili.SezioneCache;
import it.eng.jaxb.variabili.SezioneCache.Variabile;
import it.eng.job.avanzamento.albopretorio.bean.AlboPretorioAttachBean;
import it.eng.job.avanzamento.albopretorio.bean.CaricaDocumentoBean;
import it.eng.job.avanzamento.albopretorio.bean.FTPUploadFileBean;
import it.eng.job.avanzamento.albopretorio.bean.ProxyBean;
import it.eng.job.avanzamento.albopretorio.protocollo.CaricaDocumento;
import it.eng.job.avanzamento.albopretorio.protocollo.ElaboraResponseWS;
import it.eng.job.avanzamento.albopretorio.protocollo.FTPUploadFile;
import it.eng.job.avanzamento.albopretorio.protocollo.SetSystemProxy;
import it.eng.job.avanzamento.albopretorio.ws.DocumentoType;
import it.eng.storeutil.AnalyzeResult;
import it.eng.utility.jobmanager.SpringAppContext;
import it.eng.xml.XmlUtilitySerializer;

public class WebServicesUtils {

	Logger logger = Logger.getLogger(getClass());

	public void aggiornaDatiSIB(Session sessionJob, String schema, String token, String idDocPrimario, String idPropostAMC, String eventoSIB, String esitoEventoSIB, Date dataEventoSIB, String errMsgEventoSIB, AurigaLoginBean loginBean)
			throws Exception {

		{
			try {
				SezioneCache sezioneCacheAttributiDinamici = new SezioneCache();

				if (StringUtils.isNotBlank(idPropostAMC)) {
					putVariabileSempliceSezioneCache(sezioneCacheAttributiDinamici, "COD_PROPOSTA_SIST_CONT_Doc",
							idPropostAMC != null ? idPropostAMC : "");
				}

				 if(StringUtils.isNotBlank(eventoSIB) && !"aggiornamento".equals(eventoSIB)) {
					 putVariabileSempliceSezioneCache(sezioneCacheAttributiDinamici, "TIPO_EVENTO_SIB_Doc", eventoSIB != null ? eventoSIB : "");
					 putVariabileSempliceSezioneCache(sezioneCacheAttributiDinamici, "ESITO_EVENTO_SIB_Doc", esitoEventoSIB != null ? esitoEventoSIB : "");
					 putVariabileSempliceSezioneCache(sezioneCacheAttributiDinamici, "TS_EVENTO_SIB_Doc", dataEventoSIB != null ? new SimpleDateFormat("dd/MM/yyyy HH:mm").format(dataEventoSIB) : "");
					 putVariabileSempliceSezioneCache(sezioneCacheAttributiDinamici, "ERR_MSG_EVENTO_SIB_Doc", errMsgEventoSIB != null ? errMsgEventoSIB : "");
				 }

				if (sezioneCacheAttributiDinamici.getVariabile().size() > 0) {

//					AurigaLoginBean loginBean = new AurigaLoginBean();
//					loginBean.setToken(token);
//					
//					logger.info("schema " + schema);
//					loginBean.setSchema(schema);
//					SpecializzazioneBean spec = new SpecializzazioneBean();
//					spec.setIdDominio(idDominio);
//					loginBean.setSpecializzazioneBean(spec);
					UpddocudImpl lDmpkCoreUpddocud = new UpddocudImpl();
					DmpkCoreUpddocudBean bean = new DmpkCoreUpddocudBean();
					bean.setCodidconnectiontokenin(token);
					bean.setIduddocin(StringUtils.isNotBlank(idDocPrimario) ? new BigDecimal(idDocPrimario) : null);
					bean.setFlgtipotargetin("D");
					
					CreaModDocumentoInBean lCreaModDocumentoInBean = new CreaModDocumentoInBean();
					lCreaModDocumentoInBean.setSezioneCacheAttributiDinamici(sezioneCacheAttributiDinamici);

					XmlUtilitySerializer lXmlUtilitySerializer = new XmlUtilitySerializer();
					bean.setAttributiuddocxmlin(lXmlUtilitySerializer.bindXml(lCreaModDocumentoInBean));
					lDmpkCoreUpddocud.setBean(bean);
					sessionJob.doWork(new Work() {

						@Override
						public void execute(Connection paramConnection) throws SQLException {
							paramConnection.setAutoCommit(false);
							lDmpkCoreUpddocud.execute(paramConnection);
						}
					});
					StoreResultBean<DmpkCoreUpddocudBean> out = new StoreResultBean<DmpkCoreUpddocudBean>();
					AnalyzeResult.analyze(bean, out);
					out.setResultBean(bean);
					
					

					if (out.isInError()) {
						throw new StoreException(out);
					}
				}
			} catch (Exception e) {
				throw new StoreException(e.getMessage());
			}
		}
	}

	private void putVariabileSempliceSezioneCache(SezioneCache sezioneCache, String nomeVariabile,
			String valoreSemplice) {
		int pos = getPosVariabileSezioneCache(sezioneCache, nomeVariabile);
		if (pos != -1) {
			sezioneCache.getVariabile().get(pos).setValoreSemplice(valoreSemplice);
		} else {
			sezioneCache.getVariabile().add(createVariabileSemplice(nomeVariabile, valoreSemplice));
		}
	}

	private int getPosVariabileSezioneCache(SezioneCache sezioneCache, String nomeVariabile) {
		if (sezioneCache != null && sezioneCache.getVariabile() != null) {
			for (int i = 0; i < sezioneCache.getVariabile().size(); i++) {
				Variabile var = sezioneCache.getVariabile().get(i);
				if (var.getNome().equals(nomeVariabile)) {
					return i;
				}
			}
		}
		return -1;
	}

	private Variabile createVariabileSemplice(String nome, String valoreSemplice) {
		Variabile var = new Variabile();
		var.setNome(nome);
		var.setValoreSemplice(valoreSemplice);
		return var;
	}

	public void pubblicaSuAlboPretorio(Session sessionJob, AurigaLoginBean loginBean, List<AlboPretorioAttachBean> listaFiles, DocumentoType documentoType,
			String idUd) throws Exception {

		logger.info("Inizio della procedura di collegamento all'Albo Pretorio");

		// Setto il proxy se necessario
		ProxyBean impostazioniProxy = (ProxyBean)SpringAppContext.getContext().getBean("proxyBean");//= new ProxyBean();
		boolean settoIlProxy = new SetSystemProxy().impostaProxy(impostazioniProxy);

		if (settoIlProxy) {
			logger.debug("Proxy settato");
		} else {
			logger.debug("Proxy non settato");
		}

		// Carico il file in FTP
		if (listaFiles != null && !listaFiles.isEmpty()) {
			for (int i = 0; i < listaFiles.size(); i++) {
				AlboPretorioAttachBean lAlboPretorioAttachBean = listaFiles.get(i);
				FTPUploadFileBean impostazioniUploadBean = (FTPUploadFileBean)SpringAppContext.getContext().getBean("ftpUploadFileBean");
				impostazioniUploadBean.setFileRequest(lAlboPretorioAttachBean.getUri());
				impostazioniUploadBean.setNomeFileRemoto(lAlboPretorioAttachBean.getFileName());

				logger.info("File request: " + lAlboPretorioAttachBean.getUri());
				logger.info("Nome file: " + lAlboPretorioAttachBean.getFileName());

				boolean uploadFile = false;
				try {
					uploadFile = new FTPUploadFile().uploadFTP(impostazioniUploadBean);
					logger.info("upload effettuato per file: " + lAlboPretorioAttachBean.getFileName());
				} catch (Exception e) {
					logger.error("Errore durante l'upload del file su FTP: " + e.getMessage(), e);
					throw new Exception(
							"Errore durante l'upload del file su FTP: " + e.getMessage(),e);
				}

				if (!uploadFile) {
					throw new Exception(
							"Errore durante l'upload del file su FTP. Impossibile procedere all'invocazione del servizio per la pubblicazione all'Albo Pretorio");
				}
			}
		}

		// Invoco il WS Albo Pretorio con il file primario e gli allegati
		try {
			CaricaDocumentoBean caricaDocumentoBean = (CaricaDocumentoBean)SpringAppContext.getContext().getBean("caricaDocumentoBean");//= new ProxyBean();
			
			SOAPEnvelope responseDocument = new CaricaDocumento().caricaDocumento(caricaDocumentoBean,
					documentoType);
			if (responseDocument != null) {
				String respString = responseDocument.getAsString();
				logger.debug("CaricaDocumento response: " + respString);
				// Elaboro la response del WS
				String idAlbo = new ElaboraResponseWS().elaboraResponse(responseDocument);
				if (idAlbo != null && !"".equals(idAlbo)) {
					aggiornaIdAlbo(sessionJob,idUd, idAlbo, loginBean);
				} else {
					logger.error(
							"Errore nell'invocazione del servizio per la pubblicazione all'Albo Pretorio: nessun idAlbo restituito");
					throw new Exception("Errore nell'invocazione del servizio per la pubblicazione all'Albo Pretorio");
				}
			}
		} catch (Throwable e) {
			logger.error(
					"Errore nell'invocazione del servizio per la pubblicazione all'Albo Pretorio: " + e.getMessage(),
					e);
			throw new Exception("Errore nell'invocazione del servizio per la pubblicazione all'Albo Pretorio");
		}

		// addMessage("Pubblicazione all'Albo Pretorio avvenuta con successo",
		// "",
		// it.eng.utility.ui.module.core.shared.message.MessageType.INFO);

		logger.debug("Fine della procedura di collegamento all'Albo Pretorio");
	}

	private void aggiornaIdAlbo(Session sessionJob, String idUd, String idAlbo,AurigaLoginBean loginBean) throws Exception {

		UpddocudImpl lDmpkCoreUpddocud = new UpddocudImpl();
		
		DmpkCoreUpddocudBean bean = new DmpkCoreUpddocudBean();
		bean.setCodidconnectiontokenin(loginBean.getToken());
		bean.setIduserlavoroin(StringUtils.isNotBlank(loginBean.getIdUserLavoro())
				? new BigDecimal(loginBean.getIdUserLavoro()) : null);

		bean.setIduddocin(new BigDecimal(idUd));
		bean.setFlgtipotargetin("U");

		SezioneCache sezioneCacheAttributiDinamici = new SezioneCache();
		sezioneCacheAttributiDinamici.getVariabile().add(createVariabileSemplice("ID_PUBBL_ALBO_Ud", idAlbo));

		CreaModDocumentoInBean lCreaModDocumentoInBean = new CreaModDocumentoInBean();
		lCreaModDocumentoInBean.setSezioneCacheAttributiDinamici(sezioneCacheAttributiDinamici);

		XmlUtilitySerializer lXmlUtilitySerializer = new XmlUtilitySerializer();
		bean.setAttributiuddocxmlin(lXmlUtilitySerializer.bindXml(lCreaModDocumentoInBean));

		lDmpkCoreUpddocud.setBean(bean);
		sessionJob.doWork(new Work() {

			@Override
			public void execute(Connection paramConnection) throws SQLException {
				paramConnection.setAutoCommit(false);
				lDmpkCoreUpddocud.execute(paramConnection);
			}
		});
		StoreResultBean<DmpkCoreUpddocudBean> out = new StoreResultBean<DmpkCoreUpddocudBean>();
		AnalyzeResult.analyze(bean, out);
		out.setResultBean(bean);
		if (out.isInError()) {
			throw new StoreException(out);
		}
		
	}

}
