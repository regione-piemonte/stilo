/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import javax.servlet.ServletContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import org.apache.log4j.Logger;

import com.sun.jersey.spi.resource.Singleton;

import io.swagger.annotations.Api;
import it.eng.auriga.database.store.dmpk_login.bean.DmpkLoginLoginconcredenzialiesterneBean;
import it.eng.auriga.database.store.dmpk_login.store.Loginconcredenzialiesterne;
import it.eng.auriga.database.store.result.bean.StoreResultBean;
import it.eng.auriga.module.business.beans.AurigaLoginBean;
import it.eng.auriga.repository2.generic.VersionHandlerException;
import it.eng.document.configuration.DefaultConfigBean;
import it.eng.spring.utility.SpringAppContext;

@Singleton
@Api
@Path("/logIn")
public class RestAurigaLogIn {

	@Context
	ServletContext context;

	private static final Logger log = Logger.getLogger(RestAurigaLogIn.class);

	@POST
	@Produces({ MediaType.APPLICATION_XML })
	@Consumes({ MediaType.APPLICATION_XML })
	@Path("/externalLogIn")
	public AurigaLoginBean externalLogIn(AurigaLoginBean authenticationBean) {

		AurigaLoginBean resultLoginBean = new AurigaLoginBean();

		String token = null;
		String idDominioAttr = null;
		String desDominio = null;
		String desUserAttr = null;

		String userName = authenticationBean.getUserid();
		String password = authenticationBean.getPassword();
		String codApplicazione = authenticationBean.getCodApplicazione();
		String istanzaApplicazione = authenticationBean.getIdApplicazione();

		log.debug("Eseguo il WS REST di LogIn esterna.");

		/*------------ BEGIN   RECUPERO I PARAMETRI DI CONFIGURAZIONE */
		DefaultConfigBean lDefaultConfigBean = (DefaultConfigBean) SpringAppContext.getContext()
				.getBean("defaultConfigBean");

		AurigaLoginBean loginBean = new AurigaLoginBean();
		loginBean.setSchema(lDefaultConfigBean.getSchema());

		StoreResultBean<DmpkLoginLoginconcredenzialiesterneBean> output = null;
		try {
			// Inizializzo l'INPUT
			DmpkLoginLoginconcredenzialiesterneBean input = new DmpkLoginLoginconcredenzialiesterneBean();
			input.setCodapplicazionein(codApplicazione);
			input.setCodistanzaapplin(istanzaApplicazione);
			input.setPasswordin(password);
			input.setUsernamein(userName);

			// Eseguo il servizio
			Loginconcredenzialiesterne service = new Loginconcredenzialiesterne();
			output = service.execute(loginBean, input);

			if (output.isInError()) {
				throw new Exception(output.getDefaultMessage());
			}

			// restituisco IdDominioOut
			if (output.getResultBean().getIddominioout() != null) {
				idDominioAttr = output.getResultBean().getIddominioout().toString();
			}

			// restituisco CodIdConnectionTokenOut
			if (output.getResultBean().getCodidconnectiontokenout() != null) {
				token = output.getResultBean().getCodidconnectiontokenout().toString();
			}

			// restituisco DesUserOut
			if (output.getResultBean().getDesuserout() != null) {
				desDominio = output.getResultBean().getDesuserout().toString();
			}

			// restituisco DesDominioOut
			if (output.getResultBean().getDesdominioout() != null) {
				idDominioAttr = output.getResultBean().getDesdominioout().toString();
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}

		resultLoginBean.setToken(token);

		return resultLoginBean;

	}
}