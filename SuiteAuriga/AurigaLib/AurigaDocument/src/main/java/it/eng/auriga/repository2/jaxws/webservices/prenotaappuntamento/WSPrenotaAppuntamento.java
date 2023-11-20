/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.jws.WebService;
import javax.xml.ws.soap.MTOM;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import com.sun.xml.ws.developer.SchemaValidation;

import it.eng.auriga.database.store.dmpk_int_portale_crm.bean.DmpkIntPortaleCrmAggiornarichiestaaccessoattiBean;
import it.eng.auriga.database.store.dmpk_int_portale_crm.bean.DmpkIntPortaleCrmVerificarichiestaaccessoattiBean;
import it.eng.auriga.database.store.dmpk_int_portale_crm.store.Aggiornarichiestaaccessoatti;
import it.eng.auriga.database.store.dmpk_int_portale_crm.store.Verificarichiestaaccessoatti;
import it.eng.auriga.database.store.result.bean.StoreResultBean;
import it.eng.auriga.module.business.beans.AurigaLoginBean;
import it.eng.auriga.module.business.beans.SpecializzazioneBean;
import it.eng.auriga.repository2.jaxws.jaxbBean.prenotaappuntamento.ErrorType;
import it.eng.auriga.repository2.jaxws.jaxbBean.prenotaappuntamento.RequestAggiornaRichiesta;
import it.eng.auriga.repository2.jaxws.jaxbBean.prenotaappuntamento.RequestVerificaRichiesta;
import it.eng.auriga.repository2.jaxws.jaxbBean.prenotaappuntamento.ResponseAggiornaRichiesta;
import it.eng.auriga.repository2.jaxws.jaxbBean.prenotaappuntamento.ResponseVerificaRichiesta;
import it.eng.auriga.repository2.jaxws.jaxbBean.prenotaappuntamento.ResponseVerificaRichiesta.DatiRichiesta.AttiRichiesti;
import it.eng.auriga.repository2.jaxws.jaxbBean.prenotaappuntamento.ResponseVerificaRichiesta.DatiRichiesta.SediAppuntamento;
import it.eng.auriga.repository2.jaxws.jaxbBean.prenotaappuntamento.ResultType;
import it.eng.core.business.subject.SubjectBean;
import it.eng.core.business.subject.SubjectUtil;
import it.eng.document.configuration.PrenotazioneAppuntamentoConfigBean;
import it.eng.document.function.bean.AppuntamentoAggiornaRichiestaAttoXmlBean;
import it.eng.document.function.bean.AttoRichiestaXmlBean;
import it.eng.document.function.bean.SedeAppuntamentoXmlBean;
import it.eng.spring.utility.SpringAppContext;
import it.eng.storeutil.AnalyzeResult;
import it.eng.xml.XmlListaUtility;
import it.eng.xml.XmlUtilitySerializer;

/**
 * @author Mattia Zanetti
 */

@WebService(targetNamespace = "http://prenotaappuntamento.webservices.repository2.auriga.eng.it", endpointInterface = "it.eng.auriga.repository2.jaxws.webservices.prenotaappuntamento.WSIPrenotaAppuntamento", name = "WSPrenotaAppuntamento")
@SchemaValidation
@MTOM(enabled = true, threshold = 0)

public class WSPrenotaAppuntamento implements WSIPrenotaAppuntamento {

	static Logger aLogger = Logger.getLogger(WSPrenotaAppuntamento.class.getName());

	public WSPrenotaAppuntamento() {
		super();

	}

	@Override
	public ResponseVerificaRichiesta verificaRichiesta(RequestVerificaRichiesta parameter) {

		aLogger.debug("WS verificaRichiesta - request: " + parameter);

		// la validazione è già effettuata da XMLValidation sullo schema associato al wsdl
		// verifico comunque che i dati in input siano valorizzati

		ResponseVerificaRichiesta response = new ResponseVerificaRichiesta();

		try {

			PrenotazioneAppuntamentoConfigBean lPrenotazioneAppuntamentoConfigBean = null;
			try {
				// recupero il token per effettuare la chiamata alla store e il relativo schema dal bean di configurazione
				lPrenotazioneAppuntamentoConfigBean = (PrenotazioneAppuntamentoConfigBean) SpringAppContext.getContext()
						.getBean("PrenotazioneAppuntamentoConfigBean");
			} catch (Exception e) {
			}
			if (lPrenotazioneAppuntamentoConfigBean == null) {
				throw new Exception("Bean PrenotazioneAppuntamentoConfigBean non configurato");
			}
			if (StringUtils.isBlank(lPrenotazioneAppuntamentoConfigBean.getCodIdConnectionToken())) {
				throw new Exception("CodIdConnectionToken non valorizzato");
			}
			if (StringUtils.isBlank(lPrenotazioneAppuntamentoConfigBean.getSchema())) {
				throw new Exception("Schema non valorizzato");
			}

			// creo bean connessione
			AurigaLoginBean loginBean = new AurigaLoginBean();
			loginBean.setToken(lPrenotazioneAppuntamentoConfigBean.getCodIdConnectionToken());
			loginBean.setSchema(lPrenotazioneAppuntamentoConfigBean.getSchema());

			SpecializzazioneBean lspecializzazioneBean = new SpecializzazioneBean();
			lspecializzazioneBean.setCodIdConnectionToken(lPrenotazioneAppuntamentoConfigBean.getCodIdConnectionToken());

			loginBean.setSpecializzazioneBean(lspecializzazioneBean);

			SubjectBean subject = new SubjectBean();
			subject.setIdDominio(lPrenotazioneAppuntamentoConfigBean.getSchema());
			SubjectUtil.subject.set(subject);

			if (parameter == null) {
				throw new Exception("Request non valorizzata correttamente");
			}
			if (parameter.getAnno() == null) {
				throw new Exception("Anno richiesta non valorizzato");
			}
			if (parameter.getNro() == null) {
				throw new Exception("Numero richiesta non valorizzato");
			}

			DmpkIntPortaleCrmVerificarichiestaaccessoattiBean lDmpkIntPortaleCrmVerificarichiestaaccessoattiBean = new DmpkIntPortaleCrmVerificarichiestaaccessoattiBean();
			lDmpkIntPortaleCrmVerificarichiestaaccessoattiBean.setCodidconnectiontokenin(lPrenotazioneAppuntamentoConfigBean.getCodIdConnectionToken());

			// anno e numero protocollo
			lDmpkIntPortaleCrmVerificarichiestaaccessoattiBean.setAnnoprotgenin(parameter.getAnno().getYear());
			lDmpkIntPortaleCrmVerificarichiestaaccessoattiBean.setNroprotgenin(parameter.getNro().intValue());

			// effettuo chiamata alla store
			Verificarichiestaaccessoatti service = new Verificarichiestaaccessoatti();
			service.setBean(lDmpkIntPortaleCrmVerificarichiestaaccessoattiBean);
			StoreResultBean<DmpkIntPortaleCrmVerificarichiestaaccessoattiBean> lResultStore = service.execute(loginBean,
					lDmpkIntPortaleCrmVerificarichiestaaccessoattiBean);

			AnalyzeResult.analyze(lDmpkIntPortaleCrmVerificarichiestaaccessoattiBean, lResultStore);
			lResultStore.setResultBean(lDmpkIntPortaleCrmVerificarichiestaaccessoattiBean);

			if (lResultStore.isInError()) {

				// la store ha restituito un errore, mappo il corrispondente errore con quelli previsti per il web service
				// In caso l'errore non sia fra quelli previsti si restituisce il codice AUR-999 e il dettaglio dell'errore

				aLogger.debug(lResultStore.getDefaultMessage());
				aLogger.debug(lResultStore.getErrorContext());
				aLogger.debug(lResultStore.getErrorCode());

				response.setEsitoVerifica(ResultType.KO);
				ResponseVerificaRichiesta.Errore errore = new ResponseVerificaRichiesta.Errore();
				errore.setCodice(getErrorCode(lResultStore.getErrorCode()));
				errore.setMessaggio(lResultStore.getDefaultMessage());
				response.setErrore(errore);

			} else {

				DmpkIntPortaleCrmVerificarichiestaaccessoattiBean lResultStoreBean = lResultStore.getResultBean();

				if (lResultStoreBean == null || lResultStoreBean.getIdrichiestaout() == null) {
					throw new Exception("Errore nell'eleaborazione del risultato della verifica");
				}

				// restituisco risultato della store
				response.setEsitoVerifica(ResultType.OK);
				ResponseVerificaRichiesta.DatiRichiesta dati = new ResponseVerificaRichiesta.DatiRichiesta();

				setOggettoAtto(lResultStoreBean, dati);

				setListaEstremiAtto(lResultStoreBean, dati);

				setListaSediAppuntamento(lResultStoreBean, dati);

				response.setDatiRichiesta(dati);
			}

		}

		catch (Exception e) {
			aLogger.error("Errore WS verificaRichiesta", e);
			// in caso di errore restituisco KO e il codice di errore AUR-999
			response.setEsitoVerifica(ResultType.KO);
			ResponseVerificaRichiesta.Errore errore = new ResponseVerificaRichiesta.Errore();
			errore.setCodice(ErrorType.AUR_999);
			String messaggio = "Errore generico";
			if (StringUtils.isNotBlank(e.getMessage())) {
				messaggio = e.getMessage();
			}
			errore.setMessaggio(messaggio);
			response.setErrore(errore);
		}

		aLogger.debug("WS verificaRichiesta - response: " + response);

		return response;

	}

	/**
	 * @param lResultStoreBean
	 * @param dati
	 */
	private void setOggettoAtto(DmpkIntPortaleCrmVerificarichiestaaccessoattiBean lResultStoreBean, ResponseVerificaRichiesta.DatiRichiesta dati) {
		dati.setID(lResultStoreBean.getIdrichiestaout().toString());
		if (StringUtils.isNotBlank(lResultStoreBean.getOggettorichiestaout())) {
			dati.setOggetto(lResultStoreBean.getOggettorichiestaout());
		}
	}

	/**
	 * @param lResultStoreBean
	 * @param dati
	 * @throws Exception
	 */
	private void setListaSediAppuntamento(DmpkIntPortaleCrmVerificarichiestaaccessoattiBean lResultStoreBean, ResponseVerificaRichiesta.DatiRichiesta dati)
			throws Exception {

		ArrayList<String> listaSediAppuntamento = new ArrayList<>();
		// recupero lista sedi deserializzando l'xml
		if (StringUtils.isNotBlank(lResultStoreBean.getSediappuntamentoout())) {
			List<SedeAppuntamentoXmlBean> data = XmlListaUtility.recuperaLista(lResultStoreBean.getSediappuntamentoout(), SedeAppuntamentoXmlBean.class);
			if (data != null) {
				for (Iterator<SedeAppuntamentoXmlBean> iterator = data.iterator(); iterator.hasNext();) {
					SedeAppuntamentoXmlBean sedeAppuntamentoXmlBean = iterator.next();
					if (sedeAppuntamentoXmlBean != null && StringUtils.isNotBlank(sedeAppuntamentoXmlBean.getSedeAppuntamento())) {
						listaSediAppuntamento.add(sedeAppuntamentoXmlBean.getSedeAppuntamento());
					}
				}
			}
		}

		SediAppuntamento sediAppuntamento = new SediAppuntamento(listaSediAppuntamento);
		dati.setSediAppuntamento(sediAppuntamento);
	}

	/**
	 * @param lResultStoreBean
	 * @param dati
	 * @throws Exception
	 */
	private void setListaEstremiAtto(DmpkIntPortaleCrmVerificarichiestaaccessoattiBean lResultStoreBean, ResponseVerificaRichiesta.DatiRichiesta dati)
			throws Exception {

		ArrayList<String> listaAttiString = new ArrayList<>();
		// recupero gli atti della richiesta deserializzando l'xml
		if (StringUtils.isNotBlank(lResultStoreBean.getAttirichiestilistaout())) {
			List<AttoRichiestaXmlBean> data = XmlListaUtility.recuperaLista(lResultStoreBean.getAttirichiestilistaout(), AttoRichiestaXmlBean.class);
			if (data != null) {
				for (Iterator<AttoRichiestaXmlBean> iterator = data.iterator(); iterator.hasNext();) {
					AttoRichiestaXmlBean attoRichiestaXmlBean = iterator.next();
					if (attoRichiestaXmlBean != null && StringUtils.isNotBlank(attoRichiestaXmlBean.getEstremiRichiesta())) {
						listaAttiString.add(attoRichiestaXmlBean.getEstremiRichiesta());
					}
				}
			}

			if (!listaAttiString.isEmpty()) {
				AttiRichiesti atti = new AttiRichiesti(listaAttiString);
				dati.setAttiRichiesti(atti);
			} else {
				throw new Exception("Errore nel recupero degli estremi degli atti associati alla richiesta");
			}
		}
	}

	@Override
	public ResponseAggiornaRichiesta aggiornaRichiesta(RequestAggiornaRichiesta parameter) {

		ResponseAggiornaRichiesta response = new ResponseAggiornaRichiesta();

		aLogger.debug("WS aggiornaRichiesta - request: " + parameter);

		// la validazione è già effettuata da XMLValidation sullo schema associato al wsdl
		// verifico comunque che i dati in input siano valorizzati

		try {

			PrenotazioneAppuntamentoConfigBean lPrenotazioneAppuntamentoConfigBean = null;
			try {
				// recupero il token per effettuare la chiamata alla store e il relativo schema dal bean di configurazione
				lPrenotazioneAppuntamentoConfigBean = (PrenotazioneAppuntamentoConfigBean) SpringAppContext.getContext()
						.getBean("PrenotazioneAppuntamentoConfigBean");
			} catch (Exception e) {
			}
			if (lPrenotazioneAppuntamentoConfigBean == null) {
				throw new Exception("Bean PrenotazioneAppuntamentoConfigBean non configurato");
			}
			if (StringUtils.isBlank(lPrenotazioneAppuntamentoConfigBean.getCodIdConnectionToken())) {
				throw new Exception("CodIdConnectionToken non valorizzato");
			}
			if (StringUtils.isBlank(lPrenotazioneAppuntamentoConfigBean.getSchema())) {
				throw new Exception("Schema non valorizzato");
			}

			// creo bean connessione
			AurigaLoginBean loginBean = new AurigaLoginBean();
			loginBean.setToken(lPrenotazioneAppuntamentoConfigBean.getCodIdConnectionToken());
			loginBean.setSchema(lPrenotazioneAppuntamentoConfigBean.getSchema());

			SpecializzazioneBean lspecializzazioneBean = new SpecializzazioneBean();
			lspecializzazioneBean.setCodIdConnectionToken(lPrenotazioneAppuntamentoConfigBean.getCodIdConnectionToken());

			loginBean.setSpecializzazioneBean(lspecializzazioneBean);

			SubjectBean subject = new SubjectBean();
			subject.setIdDominio(lPrenotazioneAppuntamentoConfigBean.getSchema());
			SubjectUtil.subject.set(subject);

			if (parameter == null) {
				throw new Exception("Request non valorizzata correttamente");
			}
			if (parameter.getAnno() == null) {
				throw new Exception("Anno richiesta non valorizzato");
			}
			if (parameter.getNro() == null) {
				throw new Exception("Numero richiesta non valorizzato");
			}
			if (parameter.getAppuntamento() == null) {
				throw new Exception("Appuntamento non valorizzato correttamente");
			}
			if (parameter.getAppuntamento().getRichiedente() == null || StringUtils.isBlank(parameter.getAppuntamento().getRichiedente().getCognome())
					|| StringUtils.isBlank(parameter.getAppuntamento().getRichiedente().getNome())) {
				throw new Exception("Richiedente appuntamento non valorizzato correttamente");
			}

			DmpkIntPortaleCrmAggiornarichiestaaccessoattiBean lDmpkIntPortaleCrmAggiornarichiestaaccessoattiBean = new DmpkIntPortaleCrmAggiornarichiestaaccessoattiBean();
			lDmpkIntPortaleCrmAggiornarichiestaaccessoattiBean.setCodidconnectiontokenin(lPrenotazioneAppuntamentoConfigBean.getCodIdConnectionToken());

			// anno e numero protocollo dell'atto
			lDmpkIntPortaleCrmAggiornarichiestaaccessoattiBean.setAnnoprotgenin(parameter.getAnno().getYear());
			lDmpkIntPortaleCrmAggiornarichiestaaccessoattiBean.setNroprotgenin(parameter.getNro().intValue());

			// popolo i dati dell'appuntamento
			AppuntamentoAggiornaRichiestaAttoXmlBean lAppuntamentoAggiornaRichiestaAttoXmlBean = new AppuntamentoAggiornaRichiestaAttoXmlBean();
			setDatiAppuntamento(parameter, lAppuntamentoAggiornaRichiestaAttoXmlBean);
			// dati del richiedente
			setDatiRichiedente(parameter, lAppuntamentoAggiornaRichiestaAttoXmlBean);
			// dati del delegante
			setDatiDelegante(parameter, lAppuntamentoAggiornaRichiestaAttoXmlBean);
			// dati del nominativo
			setDatiNominativo(parameter, lAppuntamentoAggiornaRichiestaAttoXmlBean);

			// serializzazione appuntamento
			XmlUtilitySerializer lXmlUtilitySerializer = new XmlUtilitySerializer();
			lDmpkIntPortaleCrmAggiornarichiestaaccessoattiBean
					.setDatiappuntamentoxmlin(lXmlUtilitySerializer.bindXml(lAppuntamentoAggiornaRichiestaAttoXmlBean));

			final Aggiornarichiestaaccessoatti service = new Aggiornarichiestaaccessoatti();
			service.setBean(lDmpkIntPortaleCrmAggiornarichiestaaccessoattiBean);

			// effettuo chiamata alla store
			StoreResultBean<DmpkIntPortaleCrmAggiornarichiestaaccessoattiBean> lResultStore = service.execute(loginBean,
					lDmpkIntPortaleCrmAggiornarichiestaaccessoattiBean);

			AnalyzeResult.analyze(lDmpkIntPortaleCrmAggiornarichiestaaccessoattiBean, lResultStore);
			lResultStore.setResultBean(lDmpkIntPortaleCrmAggiornarichiestaaccessoattiBean);

			// si è verificato un errore
			if (lResultStore.isInError()) {

				// la store ha restituito un errore, mappo il corrispondente errore con quelli previsti per il web service
				// In caso l'errore non sia fra quelli previsti si restituisce il codice AUR-999 e il dettaglio dell'errore

				aLogger.debug(lResultStore.getDefaultMessage());
				aLogger.debug(lResultStore.getErrorContext());
				aLogger.debug(lResultStore.getErrorCode());

				response.setEsitoAggiornamento(ResultType.KO);
				ResponseAggiornaRichiesta.Errore errore = new ResponseAggiornaRichiesta.Errore();
				errore.setCodice(getErrorCode(lResultStore.getErrorCode()));
				errore.setMessaggio(lResultStore.getDefaultMessage());
				response.setErrore(errore);

			} else {
				// restituisco risultato positivo, il servizio non restituisce nessun'altra informazione
				response.setEsitoAggiornamento(ResultType.OK);
			}

		}

		catch (Exception e) {
			aLogger.error("Errore WS aggiornaRichiesta", e);
			// in caso di errore restituisco KO
			response.setEsitoAggiornamento(ResultType.KO);
			ResponseAggiornaRichiesta.Errore errore = new ResponseAggiornaRichiesta.Errore();
			errore.setCodice(ErrorType.AUR_999);
			String messaggio = "Errore generico";
			if (StringUtils.isNotBlank(e.getMessage())) {
				messaggio = e.getMessage();
			}
			errore.setMessaggio(messaggio);
			response.setErrore(errore);
		}

		aLogger.debug("WS aggiornaRichiesta - response: " + response);

		return response;

	}

	/**
	 * @param parameter
	 * @param lAppuntamentoAggiornaRichiestaAttoXmlBean
	 * @throws Exception
	 */
	private void setDatiNominativo(RequestAggiornaRichiesta parameter, AppuntamentoAggiornaRichiestaAttoXmlBean lAppuntamentoAggiornaRichiestaAttoXmlBean)
			throws Exception {
		if (parameter.getAppuntamento().getNominativoPresenza() != null) {
			if (StringUtils.isNotBlank(parameter.getAppuntamento().getNominativoPresenza().getCognome())) {
				lAppuntamentoAggiornaRichiestaAttoXmlBean.setCognomeNominativoPresenza(parameter.getAppuntamento().getNominativoPresenza().getCognome());
			} else {
				throw new Exception("Cognome del nominativo presenza non valorizzato");
			}
			if (StringUtils.isNotBlank(parameter.getAppuntamento().getNominativoPresenza().getNome())) {
				lAppuntamentoAggiornaRichiestaAttoXmlBean.setNomeNominativoPresenza(parameter.getAppuntamento().getNominativoPresenza().getNome());
			} else {
				throw new Exception("Nome del nominativo presenza non valorizzato");
			}
			if (StringUtils.isNotBlank(parameter.getAppuntamento().getNominativoPresenza().getEmail())) {
				lAppuntamentoAggiornaRichiestaAttoXmlBean.setEmailNominativoPresenza(parameter.getAppuntamento().getNominativoPresenza().getEmail());
			}
			if (StringUtils.isNotBlank(parameter.getAppuntamento().getNominativoPresenza().getCodFiscale())) {
				lAppuntamentoAggiornaRichiestaAttoXmlBean
						.setCodiceFiscaleNominativoPresenza(parameter.getAppuntamento().getNominativoPresenza().getCodFiscale());
			}
			if (StringUtils.isNotBlank(parameter.getAppuntamento().getNominativoPresenza().getTel())) {
				lAppuntamentoAggiornaRichiestaAttoXmlBean.setTelefonoNominativoPresenza(parameter.getAppuntamento().getNominativoPresenza().getTel());
			}
		}
	}

	/**
	 * @param parameter
	 * @param lAppuntamentoAggiornaRichiestaAttoXmlBean
	 */
	private void setDatiDelegante(RequestAggiornaRichiesta parameter, AppuntamentoAggiornaRichiestaAttoXmlBean lAppuntamentoAggiornaRichiestaAttoXmlBean) {
		if (parameter.getAppuntamento().getDelegante() != null) {
			if (StringUtils.isNotBlank(parameter.getAppuntamento().getDelegante().getCognomeDenominazione())) {
				lAppuntamentoAggiornaRichiestaAttoXmlBean
						.setCognomeDenominazioneDelegante(parameter.getAppuntamento().getDelegante().getCognomeDenominazione());
			}
			if (StringUtils.isNotBlank(parameter.getAppuntamento().getDelegante().getNome())) {
				lAppuntamentoAggiornaRichiestaAttoXmlBean.setNomeDelegante(parameter.getAppuntamento().getDelegante().getNome());
			}
			if (StringUtils.isNotBlank(parameter.getAppuntamento().getDelegante().getEmail())) {
				lAppuntamentoAggiornaRichiestaAttoXmlBean.setEmailDelegante(parameter.getAppuntamento().getDelegante().getEmail());
			}
			if (StringUtils.isNotBlank(parameter.getAppuntamento().getDelegante().getCodFiscale())) {
				lAppuntamentoAggiornaRichiestaAttoXmlBean.setCodiceFiscaleDelegante(parameter.getAppuntamento().getDelegante().getCodFiscale());
			}
		}
	}

	/**
	 * @param parameter
	 * @param lAppuntamentoAggiornaRichiestaAttoXmlBean
	 * @throws Exception
	 */
	private void setDatiRichiedente(RequestAggiornaRichiesta parameter, AppuntamentoAggiornaRichiestaAttoXmlBean lAppuntamentoAggiornaRichiestaAttoXmlBean)
			throws Exception {
		if (parameter.getAppuntamento().getRichiedente() != null) {
			if (StringUtils.isNotBlank(parameter.getAppuntamento().getRichiedente().getCognome())) {
				lAppuntamentoAggiornaRichiestaAttoXmlBean.setCognomeRichiedente(parameter.getAppuntamento().getRichiedente().getCognome());
			} else {
				throw new Exception("Cognome del richiedente non valorizzato");
			}
			if (StringUtils.isNotBlank(parameter.getAppuntamento().getRichiedente().getNome())) {
				lAppuntamentoAggiornaRichiestaAttoXmlBean.setNomeRichiedente(parameter.getAppuntamento().getRichiedente().getNome());
			} else {
				throw new Exception("Nome del richiedente non valorizzato");
			}
			if (StringUtils.isNotBlank(parameter.getAppuntamento().getRichiedente().getUserID())) {
				lAppuntamentoAggiornaRichiestaAttoXmlBean.setUserIDRichiedente(parameter.getAppuntamento().getRichiedente().getUserID());
			}
			if (StringUtils.isNotBlank(parameter.getAppuntamento().getRichiedente().getCodFiscale())) {
				lAppuntamentoAggiornaRichiestaAttoXmlBean.setCodiceFiscaleRichiedente(parameter.getAppuntamento().getRichiedente().getCodFiscale());
			}
			if (StringUtils.isNotBlank(parameter.getAppuntamento().getRichiedente().getEmail())) {
				lAppuntamentoAggiornaRichiestaAttoXmlBean.setEmailRichiedente(parameter.getAppuntamento().getRichiedente().getEmail());
			}
			if (StringUtils.isNotBlank(parameter.getAppuntamento().getRichiedente().getTel())) {
				lAppuntamentoAggiornaRichiestaAttoXmlBean.setTelefonoRichiedente(parameter.getAppuntamento().getRichiedente().getTel());
			}
		}
	}

	/**
	 * @param parameter
	 * @param lAppuntamentoAggiornaRichiestaAttoXmlBean
	 * @throws Exception
	 */
	private void setDatiAppuntamento(RequestAggiornaRichiesta parameter, AppuntamentoAggiornaRichiestaAttoXmlBean lAppuntamentoAggiornaRichiestaAttoXmlBean)
			throws Exception {
		if (StringUtils.isNotBlank(parameter.getAppuntamento().getAppCode())) {
			lAppuntamentoAggiornaRichiestaAttoXmlBean.setAppCode(parameter.getAppuntamento().getAppCode());
		}
		if (StringUtils.isNotBlank(parameter.getAppuntamento().getUID())) {
			lAppuntamentoAggiornaRichiestaAttoXmlBean.setUid(parameter.getAppuntamento().getUID());
		}
		if (StringUtils.isNotBlank(parameter.getAppuntamento().getSedeAppuntamento())) {
			lAppuntamentoAggiornaRichiestaAttoXmlBean.setSedeAppuntamento(parameter.getAppuntamento().getSedeAppuntamento());
		}
		lAppuntamentoAggiornaRichiestaAttoXmlBean.setDaAnnullare(parameter.getAppuntamento().isDaAnnullare());
		if (parameter.getAppuntamento().getDataOra() != null) {
			lAppuntamentoAggiornaRichiestaAttoXmlBean.setDataOra(parameter.getAppuntamento().getDataOra());
		} else if (!parameter.getAppuntamento().isDaAnnullare()) {
			throw new Exception("Data appuntamento è obbligatoria se DaAnnullare vale false");
		}
	}

	/**
	 * Metodo di utilità che restituisce il corrispondente codice di errore a partire dall'errore restituito dalla store
	 * 
	 * @param storeError
	 * @return
	 */

	private ErrorType getErrorCode(Integer storeError) {

		ErrorType result = ErrorType.AUR_999;

		if (storeError != null) {

			if (storeError == 1) {
				result = ErrorType.AUR_001;
			} else if (storeError == 2) {
				result = ErrorType.AUR_002;
			} else if (storeError == 3) {
				result = ErrorType.AUR_003;
			} else if (storeError == 4) {
				result = ErrorType.AUR_004;
			} else if (storeError == 5) {
				result = ErrorType.AUR_005;
			} else if (storeError == 6) {
				result = ErrorType.AUR_006;
			}

		}

		return result;

	}

}
