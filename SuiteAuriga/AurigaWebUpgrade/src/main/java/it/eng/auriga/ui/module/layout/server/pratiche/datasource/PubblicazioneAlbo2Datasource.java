/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.albopretorio.bean.FTPUploadFileBean;
import it.eng.albopretorio.bean.ProxyBean;
import it.eng.albopretorio.protocollo.CaricaDocumento;
import it.eng.albopretorio.protocollo.ElaboraResponseWS;
import it.eng.albopretorio.protocollo.FTPUploadFile;
import it.eng.albopretorio.protocollo.SetSystemProxy;
import it.eng.albopretorio.ws.DocumentoType;
import it.eng.auriga.exception.StoreException;
import it.eng.auriga.module.business.beans.AurigaLoginBean;
import it.eng.auriga.ui.module.layout.server.iterAtti.datasource.bean.AttiModelliCustomBean;
import it.eng.auriga.ui.module.layout.server.protocollazione.datasource.bean.AllegatoProtocolloBean;
import it.eng.auriga.ui.module.layout.server.protocollazione.datasource.bean.ProtocollazioneBean;
import it.eng.spring.utility.SpringAppContext;
import it.eng.utility.module.config.StorageImplementation;
import it.eng.utility.ui.module.core.server.datasource.AbstractServiceDataSource;
import it.eng.utility.ui.module.core.server.datasource.annotation.Datasource;
import it.eng.utility.ui.user.AurigaUserUtil;
import it.eng.utility.ui.user.ParametriDBUtil;

import java.io.File;
import java.util.Calendar;
import java.util.GregorianCalendar;

import org.apache.axis.message.SOAPEnvelope;
import org.apache.log4j.Logger;

import com.ibm.icu.text.SimpleDateFormat;

@Datasource(id = "PubblicazioneAlbo2Datasource")
public class PubblicazioneAlbo2Datasource extends AbstractServiceDataSource<ProtocollazioneBean, ProtocollazioneBean> {

	private static Logger logger = Logger.getLogger(PubblicazioneAlbo2Datasource.class);

	@Override
	public ProtocollazioneBean call(ProtocollazioneBean bean) throws Exception {

		AurigaLoginBean loginBean = AurigaUserUtil.getLoginInfo(getSession());

		try {
			AttiModelliCustomBean attiModelliCustom = (AttiModelliCustomBean) SpringAppContext.getContext().getBean("attiModelliCustom");
			String descrizioneFileAllegato = attiModelliCustom.getConfigMap().get("modello_pubblicazione");

			File returnFile = null;
			if (bean.getListaAllegati() != null && bean.getListaAllegati().size() > 0) {
				for (AllegatoProtocolloBean allegato : bean.getListaAllegati()) {
					if (allegato.getDescrizioneFileAllegato() != null && allegato.getDescrizioneFileAllegato().equals(descrizioneFileAllegato)) {
						returnFile = StorageImplementation.getStorage().extractFile(allegato.getUriFileAllegato());
					}
				}
			}

			String fileRequest = returnFile.getPath();

			// Setto i parametri della richiesta
			DocumentoType documentoType = new DocumentoType();

			documentoType.setProtocollo(bean.getSiglaProtocollo());
			documentoType.setNumeroDocumento(bean.getNroProtocollo() != null ? String.valueOf(bean.getNroProtocollo().intValue()) : null);
			documentoType.setAnnoDocumento(bean.getDataProtocollo() != null ? new Integer(new SimpleDateFormat(FMT_STD_DATA).format(bean.getDataProtocollo())
					.substring(6)) : null);
			documentoType.setDataDocumento(bean.getDataProtocollo());
			documentoType.setOggetto(bean.getOggetto());
			documentoType.setSettore(bean.getDesUOProtocollo());

			GregorianCalendar cal = new GregorianCalendar();
			documentoType.setDataInizioEsposizione(cal.getTime());

			cal.add(Calendar.DAY_OF_YEAR, 15);
			documentoType.setDataFineEsposizione(cal.getTime());

			String nomeFileRemoto = documentoType.getProtocollo() + "_" + documentoType.getAnnoDocumento() + "_" + documentoType.getNumeroDocumento() + ".pdf";

			documentoType.setNomeFile(nomeFileRemoto);
			documentoType.setUsername(loginBean.getUserid());
			String tipoDocumento = ParametriDBUtil.getParametroDB(getSession(), "TIPO_DOCUMENTO_ALBO_PRETORIO");
			documentoType.setTipoDocumento(tipoDocumento != null && !"".equals(tipoDocumento) ? Integer.parseInt(tipoDocumento) : 9050);
			String enteProvenienza = ParametriDBUtil.getParametroDB(getSession(), "ENTE_PROVENIENZA_ALBO_PRETORIO");
			documentoType.setEnteProvenienza(enteProvenienza != null && !"".equals(enteProvenienza) ? enteProvenienza : "Comune Milano");
			documentoType.setNote(bean.getNote());

			pubblicaSuAlboPretorio(fileRequest, nomeFileRemoto, documentoType);

		} catch (Exception e) {
			throw new StoreException(e.getMessage());
		}

		return bean;
	}

	public void pubblicaSuAlboPretorio(String fileRequest, String nomeFileRemoto, DocumentoType documentoType) throws Exception {

		logger.debug("Inizio della procedura di collegamento all'Albo Pretorio");

		// Setto il proxy se necessario
		ProxyBean impostazioniProxy = new ProxyBean();
		boolean settoIlProxy = new SetSystemProxy().impostaProxy(impostazioniProxy);

		if (settoIlProxy) {
			logger.debug("Proxy settato");
		} else {
			logger.debug("Proxy non settato");
		}

		// Carico il file in FTP

		FTPUploadFileBean impostazioniUploadBean = new FTPUploadFileBean();
		impostazioniUploadBean.setFileRequest(fileRequest);
		impostazioniUploadBean.setNomeFileRemoto(nomeFileRemoto);

		boolean uploadFile = false;

		try {
			uploadFile = new FTPUploadFile().uploadFTP(impostazioniUploadBean);
		} catch (Exception e) {
			logger.error("Errore durante l'upload del file su FTP: " + e.getMessage());
		}

		if (!uploadFile) {
			throw new Exception(
					"Errore durante l'upload del file su FTP. Impossibile procedere all'invocazione del servizio per la pubblicazione all'Albo Pretorio");
		}

		// Invoco il WS Albo Pretorio con un solo file primario senza allegati
		try {
			SOAPEnvelope responseDocument = new CaricaDocumento().caricaDocumento(null, documentoType);
			if (responseDocument != null) {
				String respString = responseDocument.getAsString();
				logger.debug("CaricaDocumento response: " + respString);
				// Elaboro la response del WS
				new ElaboraResponseWS().elaboraResponse(responseDocument);
			}
		} catch (Exception e) {
			logger.error("Errore nell'invocazione del servizio per la pubblicazione all'Albo Pretorio: " + e.getMessage());
			throw new Exception("Errore nell'invocazione del servizio per la pubblicazione all'Albo Pretorio");
		}

		addMessage("Pubblicazione all'Albo Pretorio avvenuta con successo", "", it.eng.utility.ui.module.core.shared.message.MessageType.INFO);

		logger.debug("Fine della procedura di collegamento all'Albo Pretorio");
	}

}