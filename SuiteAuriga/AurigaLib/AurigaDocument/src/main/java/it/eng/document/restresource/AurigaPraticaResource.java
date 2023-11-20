/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import javax.servlet.ServletContext;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;
import javax.ws.rs.core.Response.Status;

import org.apache.log4j.Logger;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.sun.jersey.spi.resource.Singleton;

import it.eng.auriga.database.store.dmpk_registrazionedoc.bean.DmpkRegistrazionedocGetprotpraticasportelloBean;
import it.eng.auriga.database.store.dmpk_registrazionedoc.store.Getprotpraticasportello;
import it.eng.auriga.database.store.result.bean.StoreResultBean;
import it.eng.auriga.module.business.beans.AurigaLoginBean;
import it.eng.core.business.subject.SubjectBean;
import it.eng.core.business.subject.SubjectUtil;
import it.eng.document.configuration.DefaultConfigBean;
import it.eng.document.function.bean.restrepresentation.Errore;
import it.eng.document.function.bean.restrepresentation.PraticaSportello;

@Singleton
@Path("/pratiche")
public class AurigaPraticaResource {

	private static final Logger logger = Logger.getLogger(AurigaPraticaResource.class);
	private static final String DEPENDENCY_BEAN_NAME1 = "defaultConfigBean";
	private static final String HEADER_TOKEN_DI_CONNESSIONE = "Auriga-Token";
	private static final String PARAMETRO_CODICE_PRATICA = "codPratica";
	private static final String PARAMETRO_ID_EVENTO = "idEvento";
	@Context
	private ServletContext context;

	public AurigaPraticaResource() throws Exception {
		logger.debug("AurigaPraticaResource creation");
	}

	@GET
	@Path("/pratica")
	@Produces({ MediaType.APPLICATION_XML })
	public Response getProtocolloPraticaSportello(@HeaderParam(HEADER_TOKEN_DI_CONNESSIONE) String token,
			@QueryParam(PARAMETRO_CODICE_PRATICA) String codicePratica, @QueryParam(PARAMETRO_ID_EVENTO) String idEvento) throws Exception {
		try {
			logger.debug("getProcolloPraticaSportello() inizio");
			logger.debug(String.format("token: %s, codicePratica: %s, idEvento: %s", token, codicePratica, idEvento));
			if (token == null || token.trim().isEmpty()) {
				final String msg = "Manca il valore di '" + HEADER_TOKEN_DI_CONNESSIONE + "' negli headers della richiesta.";
				return Response.status(Status.BAD_REQUEST).entity(getErrore(1, msg)).build();
			}
			final boolean flagCodicePraticaAssente = codicePratica == null || codicePratica.trim().isEmpty();
			final boolean flagIdEventoAssente = idEvento == null || idEvento.trim().isEmpty();
			if (flagCodicePraticaAssente && flagIdEventoAssente) {
				final String msg = "Almeno uno fra '" + PARAMETRO_CODICE_PRATICA + "' e '" + PARAMETRO_ID_EVENTO
						+ "' deve essere valorizzato nei parametri della richiesta.";
				return Response.status(Status.BAD_REQUEST).entity(getErrore(2, msg)).build();
			}
			final StoreResultBean<DmpkRegistrazionedocGetprotpraticasportelloBean> storeResultBean = getProcolloPraticaSportello(token, codicePratica,
					idEvento);
			final DmpkRegistrazionedocGetprotpraticasportelloBean resultBean = storeResultBean.getResultBean();
			ResponseBuilder responseBuilder = null;
			if (storeResultBean.isInError()) {
				final Errore errore = getErrore(resultBean.getErrcodeout(), resultBean.getErrmsgout());
				responseBuilder = Response.status(Status.BAD_REQUEST).entity(errore);
			} else {
				final PraticaSportello output = new PraticaSportello();
				output.setCodice(codicePratica);
				output.setDataOraProtocollo(resultBean.getDataoraprotocolloout());
				output.setNroProtocollo(resultBean.getNroprotocolloout());
				responseBuilder = Response.ok(output);
			}
			return responseBuilder.build();
		} catch (Exception e) {
			final String msg = "Sì è verificato un errore imprevisto durante l'esecuzione del servizio.";
			logger.error(msg, e);
			throw new AurigaRestServiceException(msg, Status.INTERNAL_SERVER_ERROR);
		}
	}

	private StoreResultBean<DmpkRegistrazionedocGetprotpraticasportelloBean> getProcolloPraticaSportello(String token, String codicePratica, String idEvento)
			throws Exception {
		final WebApplicationContext webAppCtx = WebApplicationContextUtils.getWebApplicationContext(context);
		final DefaultConfigBean defaultConfigBean = webAppCtx.getBean(DEPENDENCY_BEAN_NAME1, DefaultConfigBean.class);
		final DmpkRegistrazionedocGetprotpraticasportelloBean input = new DmpkRegistrazionedocGetprotpraticasportelloBean();
		input.setCodidconnectiontokenin(token);
		input.setCodpraticain(codicePratica);
		input.setIdeventoin(idEvento);
		final AurigaLoginBean aurigaLoginBean = new AurigaLoginBean();
		aurigaLoginBean.setToken(token);
		aurigaLoginBean.setSchema(defaultConfigBean.getSchema());
		final Getprotpraticasportello getprotpraticasportello = new Getprotpraticasportello();
		SubjectUtil.subject.set(new SubjectBean());
		return getprotpraticasportello.execute(aurigaLoginBean, input);
	}

	private Errore getErrore(Integer codice, String messaggio) {
		final Errore errore = new Errore();
		if (codice != null)
			errore.setCodice(String.valueOf(codice));
		errore.setMessaggio(messaggio);
		return errore;
	}

}// AurigaPraticaResource