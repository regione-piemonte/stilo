/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.lang.invoke.MethodHandles;
import java.math.BigDecimal;
import java.util.Locale;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import it.eng.auriga.database.store.bean.SchemaBean;
import it.eng.auriga.database.store.dmpk_login.bean.DmpkLoginLoginBean;
import it.eng.auriga.database.store.result.bean.StoreResultBean;
import it.eng.auriga.module.business.beans.AurigaLoginBean;
import it.eng.auriga.module.business.beans.SpecializzazioneBean;
import it.eng.client.DmpkLoginLogin;

/**
 * Accentra metodi di utilità per la login in auriga
 *
 */
public class AurigaLoginUtil {

	private static final Logger logger = LogManager.getLogger(MethodHandles.lookup().lookupClass());

	private SchemaBean schemaBean;
	private Locale locale;
	private AurigaLoginBean lAurigaLoginBean;

	public AurigaLoginUtil() {

	}

	public void login(BigDecimal idSpAoo, Integer tipoDominio, String username, SpecializzazioneBean specializzazioneBean, String linguaApplicazione,
			String schema) throws Exception {

		AurigaLoginBean loginBean = new AurigaLoginBean();
		loginBean.setLinguaApplicazione(linguaApplicazione);
		loginBean.setSpecializzazioneBean(specializzazioneBean);
		loginBean.setSchema(schema);

		DmpkLoginLoginBean loginLoginBean = authenticate(idSpAoo, tipoDominio, username, loginBean);

		this.schemaBean = new SchemaBean();
		schemaBean.setSchema(schema);

		this.locale = new Locale(linguaApplicazione);

		lAurigaLoginBean = new AurigaLoginBean();

		lAurigaLoginBean.setCodApplicazione(loginLoginBean.getCodapplicazioneestin());
		lAurigaLoginBean.setDominio(loginLoginBean.getDesdominioout());
		lAurigaLoginBean.setIdUser(loginLoginBean.getIduserout());
		lAurigaLoginBean.setUserid(loginLoginBean.getUsernamein());
		lAurigaLoginBean.setPassword(loginLoginBean.getPasswordin());
		lAurigaLoginBean.setToken(loginLoginBean.getCodidconnectiontokenout());

		lAurigaLoginBean.setSpecializzazioneBean(specializzazioneBean);
		lAurigaLoginBean.setLinguaApplicazione(linguaApplicazione);
		lAurigaLoginBean.setSchema(schema);

		lAurigaLoginBean.getSpecializzazioneBean().setIdDominio(idSpAoo);

	}

	public void login(BigDecimal idSpAoo, Integer tipoDominio, String token, String linguaApplicazione, String schema) throws Exception {

		this.schemaBean = new SchemaBean();
		schemaBean.setSchema(schema);

		this.locale = new Locale(linguaApplicazione);

		SpecializzazioneBean specializzazioneBean = new SpecializzazioneBean();
		specializzazioneBean.setCodIdConnectionToken(token);
		specializzazioneBean.setTipoDominio(tipoDominio);
		specializzazioneBean.setIdDominio(idSpAoo);

		lAurigaLoginBean = new AurigaLoginBean();

		lAurigaLoginBean.setToken(token);

		lAurigaLoginBean.setSpecializzazioneBean(specializzazioneBean);
		lAurigaLoginBean.setLinguaApplicazione(linguaApplicazione);
		lAurigaLoginBean.setSchema(schema);
	}

	/**
	 * Autentica senza controllo di password l'utente specificato presso il dominio fornito
	 * 
	 * @param idDominio
	 * @param tipoDominio
	 * @param username
	 * @return
	 * @throws Exception
	 */
	private DmpkLoginLoginBean authenticate(BigDecimal idDominio, int tipoDominio, String username, AurigaLoginBean loginBean) throws Exception {

		DmpkLoginLoginBean lLoginInput = new DmpkLoginLoginBean();
		lLoginInput.setUsernamein(username);
		lLoginInput.setFlgtpdominioautio(tipoDominio);
		lLoginInput.setIddominioautio(idDominio);
		lLoginInput.setCodapplicazioneestin(null);
		lLoginInput.setCodistanzaapplestin(null);
		lLoginInput.setFlgrollbckfullin(null);
		lLoginInput.setFlgautocommitin(1);
		lLoginInput.setFlgnoctrlpasswordin(1);

		DmpkLoginLogin lLogin = new DmpkLoginLogin();

		StoreResultBean<DmpkLoginLoginBean> lLoginOutput = lLogin.execute(locale, loginBean, lLoginInput);

		if (lLoginOutput.getDefaultMessage() != null) {
			throw new Exception(lLoginOutput.getErrorContext() + " - " + lLoginOutput.getErrorCode() + " - " + lLoginOutput.getDefaultMessage());
		} else {

			DmpkLoginLoginBean retValue = lLoginOutput.getResultBean();
			logger.debug(String.format("Autenticazione avvenuta con successo, token %1$s", retValue.getCodidconnectiontokenout()));

			return retValue;
		}

	}

	public SchemaBean getSchemaBean() {
		return schemaBean;
	}

	public Locale getLocale() {
		return locale;
	}

	public AurigaLoginBean getAurigaLoginBean() {
		return lAurigaLoginBean;
	}
}
