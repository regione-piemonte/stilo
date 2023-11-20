/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.StringWriter;

import javax.servlet.ServletContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response.Status;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.sun.jersey.spi.resource.Singleton;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import it.eng.auriga.database.store.bean.SchemaBean;
import it.eng.auriga.database.store.dmpk_int_portale_crm.bean.DmpkIntPortaleCrmRichsbloccolimrichagibilitaBean;
import it.eng.auriga.database.store.dmpk_ws.bean.DmpkWsRicercaagibilitadaportaleBean;
import it.eng.auriga.database.store.dmpk_ws.bean.DmpkWsRichsbloccolimagibilitaBean;
import it.eng.auriga.database.store.dmpk_ws.store.Ricercaagibilitadaportale;
import it.eng.auriga.database.store.result.bean.StoreResultBean;
import it.eng.auriga.module.business.beans.AurigaLoginBean;
import it.eng.client.DmpkIntPortaleCrmRichsbloccolimrichagibilita;
import it.eng.client.DmpkWsRichsbloccolimagibilita;
import it.eng.core.business.subject.SubjectBean;
import it.eng.core.business.subject.SubjectUtil;
import it.eng.document.configuration.RicercaAgibilitaWSConfigBean;
import it.eng.document.function.bean.restrepresentation.input.RicercaAgibilitaRequest;
import it.eng.document.function.bean.restrepresentation.input.SbloccoLimiteRichiesteRequest;
import it.eng.document.function.bean.restrepresentation.output.SbloccoLimiteRichiesteResponse;
import it.eng.storeutil.AnalyzeResult;
import it.eng.spring.utility.SpringAppContext;

/**
 *  WS per la richiesta di sblocco delle utenze dal portale Urbanistica
 * 
 * @author Antonio Peluso
 * 
 */

@Singleton
@Api
@Path("/richieste")
public class SbloccoLimiteRichiesteResource extends RESTAurigaService{
	
	@Context
	ServletContext context;

	private static final Logger logger = Logger.getLogger(SbloccoLimiteRichiesteResource.class);
	protected static RicercaAgibilitaWSConfigBean lRicercaAgibilitaWSConfigBean = null;
	private static AurigaLoginBean lAurigaLoginBean = new AurigaLoginBean();

	
	@POST
	@Path("/sbloccoRichiesta")
	@Produces({ MediaType.APPLICATION_XML })
	@Consumes({ MediaType.APPLICATION_XML })
	public SbloccoLimiteRichiesteResponse sbloccoRichiesta(SbloccoLimiteRichiesteRequest sbloccoLimiteRichiesteRequest) throws Exception{
		
		final SbloccoLimiteRichiesteResponse response = new SbloccoLimiteRichiesteResponse();
		
		logger.debug("---------- INIZIO SERVIZIO: sbloccoRichiesta ----------");
		
		try {
			
			/*------------ BEGIN   RECUPERO I PARAMETRI DI CONFIGURAZIONE */
			lRicercaAgibilitaWSConfigBean = (RicercaAgibilitaWSConfigBean) SpringAppContext.getContext()
					.getBean("RicercaAgibilitaWSConfigBean");
	
			if (lRicercaAgibilitaWSConfigBean == null) {
				logger.error("Bean RicercaAgibilitaWSConfigBean non configurato");
				throw new Exception("Errore nella configurazione del servizio");
			}
			if (StringUtils.isBlank(lRicercaAgibilitaWSConfigBean.getDefaultSchema())) {
				logger.error("Schema non valorizzato");
				throw new Exception("Errore nella configurazione del servizio");
			}
	
			logger.debug("RicercaAgibilitaWSConfigBean recuperato (DefaultSchema: " + lRicercaAgibilitaWSConfigBean.getDefaultSchema() + ")");
			lAurigaLoginBean.setSchema(lRicercaAgibilitaWSConfigBean.getDefaultSchema());
			/*------------ END*/
			

			String xmlInputBean = convertBeanToXml(sbloccoLimiteRichiesteRequest);
			
			// creo bean connessione
			SchemaBean lSchemaBean = new SchemaBean();
			lSchemaBean.setSchema(lRicercaAgibilitaWSConfigBean.getDefaultSchema());
		
			SubjectBean subject = new SubjectBean();
			subject.setIdDominio(lSchemaBean.getSchema());
			SubjectUtil.subject.set(subject);
	
			/* Setto i parametri in input alla store */
			DmpkIntPortaleCrmRichsbloccolimrichagibilitaBean lDmpkIntPortaleCrmRichsbloccolimrichagibilitaBean = new DmpkIntPortaleCrmRichsbloccolimrichagibilitaBean();
			lDmpkIntPortaleCrmRichsbloccolimrichagibilitaBean.setDatirichiestaxmlin(xmlInputBean);
	
			// effettuo la chiamata alla store
			final DmpkIntPortaleCrmRichsbloccolimrichagibilita service = new DmpkIntPortaleCrmRichsbloccolimrichagibilita();
	
			StoreResultBean<DmpkIntPortaleCrmRichsbloccolimrichagibilitaBean> lResultStore = service.execute(null, lSchemaBean, lDmpkIntPortaleCrmRichsbloccolimrichagibilitaBean);
	
			// si è verificato un errore
			if (lResultStore.isInError()) {
				// la store ha restituito un errore, mappo il corrispondente errore con quelli previsti per il web service
				// In caso l'errore non sia fra quelli previsti si restituisce il codice AUR-999  e il dettaglio dell'errore
	
				logger.error("La richiesta di sblocco dell'utente con codicefiscale: " 
								 + sbloccoLimiteRichiesteRequest.getCodiceFiscale() + 
								 " non e' stata presa in carico");
				logger.error("ErrorStore: " + lResultStore.getDefaultMessage());
	
				response.setErrorMessage(lResultStore.getDefaultMessage());
				response.setPresaInCarico("false");
			} else {
	
				if (lResultStore.getResultBean() != null) {
					logger.debug("La richiesta di sblocco dell'utente con codicefiscale: " 
								 + sbloccoLimiteRichiesteRequest.getCodiceFiscale() + 
								 " e' stata presa in carico");
					response.setPresaInCarico("true");
				}
			}
		
		}catch (Throwable e) {

			logger.error(e.getMessage(), e);
			throw new AurigaRestServiceException(AurigaRestServiceMessages.ERROR_SERVICE + ": " + e.getMessage(),
					Status.INTERNAL_SERVER_ERROR);
		}
				
		response.setPresaInCarico("true");
	
		return response;
	}
	
	// Metodo che converte il bean in input al servizio in xml
	private String convertBeanToXml(SbloccoLimiteRichiesteRequest sbloccoLimiteRichiesteRequest) throws Exception {
		try {
			JAXBContext jaxbContext = JAXBContext.newInstance(SbloccoLimiteRichiesteRequest.class);
			// Create Marshaller
			Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
			// Required formatting??
			jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
			// Print XML String to Console
			StringWriter sw = new StringWriter();
			// Write XML to StringWriter
			jaxbMarshaller.marshal(sbloccoLimiteRichiesteRequest, sw);
			// Verify XML Content
			String xmlContent = sw.toString();

			return xmlContent;
		} catch (Exception e) {
			logger.error("Errore nella conversione dell' Bean di input in Xml: " + e.getMessage(),e);
			throw new Exception("Errore nella conversione dell' Bean di input");
		}
	}

}