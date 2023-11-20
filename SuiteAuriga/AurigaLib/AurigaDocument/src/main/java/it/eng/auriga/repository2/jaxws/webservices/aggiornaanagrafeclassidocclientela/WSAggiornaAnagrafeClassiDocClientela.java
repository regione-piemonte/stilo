/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */
package it.eng.auriga.repository2.jaxws.webservices.aggiornaanagrafeclassidocclientela;

import java.io.StringReader;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.jws.WebService;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.ws.WebServiceContext;
import javax.xml.ws.handler.MessageContext;
import javax.xml.ws.soap.MTOM;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import com.sun.xml.ws.developer.SchemaValidation;

import it.eng.auriga.database.store.bean.SchemaBean;
import it.eng.auriga.database.store.dmpk_gae.bean.DmpkGaeAggiornaanagtipidocclientelaBean;
import it.eng.auriga.database.store.dmpk_gae.store.Aggiornaanagtipidocclientela;
import it.eng.auriga.database.store.result.bean.StoreResultBean;
import it.eng.auriga.module.business.dao.DaoBnlConsumerWS;
import it.eng.auriga.repository2.jaxws.jaxbBean.aggiornaanagrafeclassidoc.RequestAggiornaAnagrafeClassiDoc;
import it.eng.auriga.repository2.jaxws.jaxbBean.aggiornaanagrafeclassidocclientela.ErrorType;
import it.eng.auriga.repository2.jaxws.jaxbBean.aggiornaanagrafeclassidocclientela.RequestAggiornaAnagrafeClassiDocClientela;
import it.eng.auriga.repository2.jaxws.jaxbBean.aggiornaanagrafeclassidocclientela.ResponseAggiornaAnagrafeClassiDocClientela;
import it.eng.auriga.repository2.jaxws.jaxbBean.aggiornaanagrafeclassidocclientela.ResponseAggiornaAnagrafeClassiDocClientela.Errori;
import it.eng.auriga.repository2.jaxws.jaxbBean.aggiornaanagrafeclassidocclientela.ResultType;
import it.eng.core.business.subject.SubjectBean;
import it.eng.core.business.subject.SubjectUtil;
import it.eng.document.configuration.AggiornaAnagrafeClassiDocConfigBean;
import it.eng.spring.utility.SpringAppContext;
import it.eng.storeutil.AnalyzeResult;

@WebService(targetNamespace = "http://aggiornaanagrafeclassidocclientela.webservices.repository2.auriga.eng.it", endpointInterface = "it.eng.auriga.repository2.jaxws.webservices.aggiornaanagrafeclassidocclientela.WSIAggiornaAnagrafeClassiDocClientela", name = "WSAggiornaAnagrafeClassiDocClientela")
@SchemaValidation
@MTOM(enabled = true, threshold = 0)

public class WSAggiornaAnagrafeClassiDocClientela implements WSIAggiornaAnagrafeClassiDocClientela {

	@Resource
	private WebServiceContext context;

	static Logger aLogger = Logger.getLogger(WSAggiornaAnagrafeClassiDocClientela.class.getName());

	@Override
	public ResponseAggiornaAnagrafeClassiDocClientela aggiornaAnagrafeClassiDocClientela(RequestAggiornaAnagrafeClassiDocClientela parameter) {

		aLogger.debug("WS aggiornaAnagrafeClassiDocClientela - request: " + parameter);

		// la validazione è già effettuata da XMLValidation sullo schema associato al wsdl
		// verifico comunque che i dati in input siano valorizzati

		ResponseAggiornaAnagrafeClassiDocClientela response = new ResponseAggiornaAnagrafeClassiDocClientela();

		try {
			
			if (parameter == null) {
				throw new Exception("Request non valorizzata correttamente");
			}
			
			// verifico username e password in input alla richiesta SOAP
			MessageContext lMessageContext = context.getMessageContext();
			String username = (String) lMessageContext.get("username");
			String password = (String) lMessageContext.get("password");
			if (StringUtils.isBlank(username) || StringUtils.isBlank(password)) {
				throw new Exception("Credenziali non presenti o incomplete");
			}

			AggiornaAnagrafeClassiDocConfigBean lAggiornaAnagrafeClassiDocConfigBean = null;
			try {
				// recupero il token per effettuare la chiamata alla store e il relativo schema dal bean di configurazione
				lAggiornaAnagrafeClassiDocConfigBean = (AggiornaAnagrafeClassiDocConfigBean) SpringAppContext.getContext()
						.getBean("AggiornaAnagrafeClassiDocClientelaConfigBean");
			} catch (Exception e) {
			}
			if (lAggiornaAnagrafeClassiDocConfigBean == null) {
				throw new Exception("Bean AggiornaAnagrafeClassiDocClientelaConfigBean non configurato");
			}
			if (StringUtils.isBlank(lAggiornaAnagrafeClassiDocConfigBean.getDefaultSchema())) {
				throw new Exception("Schema non valorizzato");
			}

			// creo bean connessione
			SchemaBean lSchemaBean = new SchemaBean();
			lSchemaBean.setSchema(lAggiornaAnagrafeClassiDocConfigBean.getDefaultSchema());

			SubjectBean subject = new SubjectBean();
			subject.setIdDominio(lSchemaBean.getSchema());
			SubjectUtil.subject.set(subject);

			// verifico le credenziali in database
			DaoBnlConsumerWS lDaoBnlConsumerWS = new DaoBnlConsumerWS();
			Boolean authentication = lDaoBnlConsumerWS.verificaCredenziali(lSchemaBean, username, password);
			if(!authentication){
				throw new Exception("Credenziali non valide");
			}

			JAXBContext lJAXBContextMarshaller = JAXBContext.newInstance(RequestAggiornaAnagrafeClassiDocClientela.class);
			Marshaller marshaller = lJAXBContextMarshaller.createMarshaller();

			JAXBContext lJAXBContextUnmarshaller = JAXBContext.newInstance(ResponseAggiornaAnagrafeClassiDocClientela.class);
			Unmarshaller unmarshaller = lJAXBContextUnmarshaller.createUnmarshaller();

			StringWriter stringWriter = new StringWriter();
			marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			marshaller.marshal(parameter, stringWriter);

			final Aggiornaanagtipidocclientela service = new Aggiornaanagtipidocclientela();
			DmpkGaeAggiornaanagtipidocclientelaBean lDmpkGaeAggiornaanagtipidocclientelaBean = new DmpkGaeAggiornaanagtipidocclientelaBean();
			lDmpkGaeAggiornaanagtipidocclientelaBean.setXmlrequestin(stringWriter.toString());

			service.setBean(lDmpkGaeAggiornaanagtipidocclientelaBean);

			// effettuo chiamata alla store
			StoreResultBean<DmpkGaeAggiornaanagtipidocclientelaBean> lResultStore = service.execute(lSchemaBean, lDmpkGaeAggiornaanagtipidocclientelaBean);

			AnalyzeResult.analyze(lDmpkGaeAggiornaanagtipidocclientelaBean, lResultStore);
			lResultStore.setResultBean(lDmpkGaeAggiornaanagtipidocclientelaBean);

			// si è verificato un errore
			if (lResultStore.isInError()) {

				// la store ha restituito un errore, mappo il corrispondente errore con quelli previsti per il web service
				// In caso l'errore non sia fra quelli previsti si restituisce il codice AUR-999 e il dettaglio dell'errore

				aLogger.debug(lResultStore.getDefaultMessage());
				aLogger.debug(lResultStore.getErrorContext());
				aLogger.debug(lResultStore.getErrorCode());

			} else {
				if (lResultStore.getResultBean() != null && StringUtils.isNotBlank(lResultStore.getResultBean().getXmlresponseout())) {
					// restituisco xml in output dalla store
					StringReader sr = new StringReader(lResultStore.getResultBean().getXmlresponseout());
					response = (ResponseAggiornaAnagrafeClassiDocClientela) unmarshaller.unmarshal(sr);
				} else {
					throw new Exception("Nessun risultato restituito dalla store");
				}
			}

		} catch (Exception e) {
			aLogger.error("Errore WS aggiornaAnagrafeClassiDocClientela", e);
			// in caso di errore restituisco KO e il codice di errore GAE-999
			response.setEsito(ResultType.KO);
			ResponseAggiornaAnagrafeClassiDocClientela.Errori errori = new Errori();
			List<ResponseAggiornaAnagrafeClassiDocClientela.Errori.Errore> listaErrori = new ArrayList<ResponseAggiornaAnagrafeClassiDocClientela.Errori.Errore>();
			ResponseAggiornaAnagrafeClassiDocClientela.Errori.Errore errore = new ResponseAggiornaAnagrafeClassiDocClientela.Errori.Errore();
			errore.setCodice(ErrorType.GAE_999);
			String messaggio = "Errore generico";
			if (StringUtils.isNotBlank(e.getMessage())) {
				messaggio = e.getMessage();
			}
			errore.setValue(messaggio);
			listaErrori.add(errore);
			errori.setErrore(listaErrori);
			response.setErrori(errori);
		}

		aLogger.debug("WS aggiornaAnagrafeClassiDocClientela - response: " + response);

		return response;
	}

	/**
	 * Metodo di utilità che restituisce il corrispondente codice di errore a partire dall'errore restituito dalla store
	 * 
	 * @param storeError
	 * @return
	 */

	private ErrorType getErrorCode(Integer storeError) {

		ErrorType result = ErrorType.GAE_999;

		if (storeError != null) {

			if (storeError == 1) {
				result = ErrorType.GAE_001;
			} else if (storeError == 2) {
				result = ErrorType.GAE_002;
			} else if (storeError == 3) {
				result = ErrorType.GAE_003;
			}

		}

		return result;

	}

}
