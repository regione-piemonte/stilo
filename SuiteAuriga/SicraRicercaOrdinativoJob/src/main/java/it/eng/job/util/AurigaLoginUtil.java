/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.PrintWriter;
import java.io.StringWriter;
import java.math.BigDecimal;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtilsBean;
import org.apache.commons.beanutils.converters.BigDecimalConverter;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.log4j.Logger;

import it.eng.auriga.database.store.bean.SchemaBean;
import it.eng.auriga.database.store.dmpk_login.bean.DmpkLoginLoginBean;
import it.eng.auriga.database.store.dmpk_login.bean.DmpkLoginLoginapplicazioneBean;
import it.eng.auriga.database.store.result.bean.StoreResultBean;
import it.eng.auriga.module.business.beans.AurigaLoginBean;
import it.eng.auriga.module.business.beans.SpecializzazioneBean;
import it.eng.bean.Cliente;
import it.eng.bean.ClienteId;
import it.eng.client.DmpkLoginLogin;
import it.eng.client.DmpkLoginLoginapplicazione;
import it.eng.entity.ApplicazioneEsterna;
import it.eng.entity.DmvApplEfatt;
import it.eng.entity.DmvSocieta;
import it.eng.job.exception.AurigaLoginException;
import it.eng.job.exception.AurigaSPException;
/**
 * Accentra metodi di utilità per la login in auriga
 *
 */
public class AurigaLoginUtil {

	private static final Logger log = Logger.getLogger(AurigaLoginUtil.class);

	private SchemaBean schemaBean;
	private Cliente cliente;
	private Locale locale;
	private AurigaLoginBean lAurigaLoginBean;

	public AurigaLoginUtil() {

	}

	public void loginApplication(String schema, DmvSocieta societa, Locale locale2) throws AurigaLoginException {
		rimappaCliente(societa);
		loginApplication(schema, locale);
	}

	public void loginApplication(String schema, ApplicazioneEsterna cliente, Locale locale)
			throws AurigaLoginException {
		rimappaCliente(cliente);
		loginApplication(schema, locale);
	}

	public void loginApplication(String schema, DmvApplEfatt cliente, Locale locale) throws AurigaLoginException {
		rimappaCliente(cliente);
		loginApplication(schema, locale);
	}

	/**
	 * Utilizzare {@link login(BigDecimal idSpAoo, Integer idDominio, String
	 * username, SpecializzazioneBean specializzazioneBean, String
	 * linguaApplicazione, String schema)}
	 * 
	 * @param idSpAoo
	 * @param idDominio
	 * @param username
	 * @param loginBean
	 * @throws Exception
	 */
	@Deprecated
	public void login(BigDecimal idSpAoo, Integer idDominio, String username, AurigaLoginBean loginBean)
			throws Exception {

		DmpkLoginLoginBean loginLoginBean = authenticate(idSpAoo, idDominio, username, loginBean);

		lAurigaLoginBean = new AurigaLoginBean();

		lAurigaLoginBean.setSpecializzazioneBean(loginBean.getSpecializzazioneBean());
		lAurigaLoginBean.setLinguaApplicazione(loginBean.getLinguaApplicazione());
		lAurigaLoginBean.setSchema(loginBean.getSchema());

		lAurigaLoginBean.setToken(loginLoginBean.getCodidconnectiontokenout());
		lAurigaLoginBean.getSpecializzazioneBean().setIdDominio(idSpAoo);

	}

	public void login(BigDecimal idSpAoo, Integer tipoDominio, String username,
			SpecializzazioneBean specializzazioneBean, String linguaApplicazione, String schema) throws Exception {

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

	public void login(BigDecimal idSpAoo, Integer tipoDominio,
			String token, String linguaApplicazione, String schema) throws Exception {

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
	 * Autentica senza controllo di password l'utente specificato presso il
	 * dominio fornito
	 * 
	 * @param idDominio
	 * @param tipoDominio
	 * @param username
	 * @return
	 * @throws Exception
	 */
	private DmpkLoginLoginBean authenticate(BigDecimal idDominio, int tipoDominio, String username,
			AurigaLoginBean loginBean) throws Exception {

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
			throw new Exception(lLoginOutput.getErrorContext() + " - " + lLoginOutput.getErrorCode() + " - "
					+ lLoginOutput.getDefaultMessage());
		} else {

			DmpkLoginLoginBean retValue = lLoginOutput.getResultBean();
			log.debug(String.format("Autenticazione avvenuta con successo, token %1$s",
					retValue.getCodidconnectiontokenout()));

			return retValue;
		}

	}

	private void loginApplication(String schema, Locale locale) throws AurigaLoginException {

		this.locale = locale;

		schemaBean = new SchemaBean();
		schemaBean.setSchema(schema);

		lAurigaLoginBean = new AurigaLoginBean();
		lAurigaLoginBean.setSchema(schema);

		DmpkLoginLoginapplicazioneBean input = new DmpkLoginLoginapplicazioneBean();
		DmpkLoginLoginapplicazione loginApplicazione = new DmpkLoginLoginapplicazione();

		try {

			String token = cliente.getConnToken();

			lAurigaLoginBean.setUserid(cliente.getUserid());
			lAurigaLoginBean.setPassword(cliente.getPassword());
			lAurigaLoginBean.setUseridForPrefs(cliente.getUserid());

			input.setUseridapplicazionein(cliente.getUserid());
			input.setPasswordin(cliente.getPassword());

			StoreResultBean<DmpkLoginLoginapplicazioneBean> output = loginApplicazione.execute(locale, lAurigaLoginBean,
					input);
			if (output.isInError()) {
				throw new AurigaSPException(
						"Stored Proc ERROR: " + output.getErrorCode() + " - " + output.getDefaultMessage());
			} else if (output.getErrorCode() != null || StringUtils.isNotBlank(output.getDefaultMessage())) {
				log.warn("Stored Proc: " + output.getErrorCode() + " - " + output.getDefaultMessage());
			}

			DmpkLoginLoginapplicazioneBean resultBean = output.getResultBean();

			if (resultBean.getCodidconnectiontokenout() != null)
				token = resultBean.getCodidconnectiontokenout();

			lAurigaLoginBean.setIdApplicazione(cliente.getUserid());
			// lAurigaLoginBean.setUserid(cliente.getUserid());
			// lAurigaLoginBean.setPassword(cliente.getPassword());
			// lAurigaLoginBean.setSchema(schema);
			lAurigaLoginBean.setToken(token);
			lAurigaLoginBean.setIdUser(resultBean.getIduserout());
			lAurigaLoginBean.setIdUserLavoro(null);
			String dominio = (resultBean.getFlgtpdominioautout() != null) ? "" + resultBean.getFlgtpdominioautout()
					: "";
			if (resultBean.getIddominioautout() != null) {
				dominio += ":" + resultBean.getIddominioautout();
			}
			lAurigaLoginBean.setDominio(dominio);
			SpecializzazioneBean spec = new SpecializzazioneBean();
			spec.setCodIdConnectionToken(token);
			spec.setDesDominioOut(resultBean.getDesdominioout());
			spec.setDesUserOut(resultBean.getDesuserout());
			spec.setIdDominio(resultBean.getIddominioautout());
			spec.setParametriConfigOut(resultBean.getParametriconfigout());
			spec.setTipoDominio(resultBean.getFlgtpdominioautout());
			lAurigaLoginBean.setSpecializzazioneBean(spec);
			String denominazione = (StringUtils.isNotBlank(resultBean.getDesuserout())) ? resultBean.getDesuserout()
					: cliente.getUserid();
			if (StringUtils.isNotBlank(resultBean.getDesdominioout())) {
				denominazione += "@" + resultBean.getDesdominioout();
			}
			lAurigaLoginBean.setDenominazione(denominazione);
			if (log.isDebugEnabled())
				log.debug(ToStringBuilder.reflectionToString(lAurigaLoginBean));
		} catch (Exception e) {
			log.error("Errore nella chiamata ai servizi di login di AurigaBusiness", e);
			throw new AurigaLoginException(e);
		}
	}

	private void rimappaCliente(DmvSocieta dmvSocieta) throws AurigaLoginException {
		ClienteId clienteId = new ClienteId();
		this.cliente = new Cliente(clienteId);
		try {
			BeanUtilsBean beanUtils = BeanUtilsBean.getInstance();
			beanUtils.getConvertUtils().register(new BigDecimalConverter(null), BigDecimal.class);
			Map<?, ?> listaProps = beanUtils.describe(dmvSocieta);
			Iterator<?> itKeys = listaProps.keySet().iterator();
			while (itKeys.hasNext()) {
				String key = (String) itKeys.next();
				if ("id".equals(key))
					beanUtils.copyProperties(cliente.getId(), dmvSocieta.getId());
				else if ("class".equals(key))
					continue;
				else
					beanUtils.copyProperty(cliente, key, listaProps.get(key));
			}
		} catch (Exception e) {
			StringWriter messageWriter = new StringWriter();
			e.printStackTrace(new PrintWriter(messageWriter));
			log.error(messageWriter.toString());
			throw new AurigaLoginException("rimappaCliente[DmvSocieta]", e);
		}
	}

	private void rimappaCliente(DmvApplEfatt dmvApplEfatt) throws AurigaLoginException {
		ClienteId clienteId = new ClienteId();
		this.cliente = new Cliente(clienteId);
		try {
			BeanUtilsBean beanUtils = BeanUtilsBean.getInstance();
			beanUtils.getConvertUtils().register(new BigDecimalConverter(null), BigDecimal.class);
			Map<?, ?> listaProps = beanUtils.describe(dmvApplEfatt);
			Iterator<?> itKeys = listaProps.keySet().iterator();
			while (itKeys.hasNext()) {
				String key = (String) itKeys.next();
				if ("id".equals(key))
					beanUtils.copyProperties(cliente.getId(), dmvApplEfatt.getId());
				else if ("class".equals(key))
					continue;
				else
					beanUtils.copyProperty(cliente, key, listaProps.get(key));
			}
		} catch (Exception e) {
			StringWriter messageWriter = new StringWriter();
			e.printStackTrace(new PrintWriter(messageWriter));
			log.error(messageWriter.toString());
			throw new AurigaLoginException("rimappaCliente[DmvApplEfatt]", e);
		}
	}

	private void rimappaCliente(ApplicazioneEsterna applicazioneEsterna) throws AurigaLoginException {
		ClienteId clienteId = new ClienteId();
		this.cliente = new Cliente(clienteId);
		try {
			BeanUtilsBean beanUtils = BeanUtilsBean.getInstance();
			beanUtils.getConvertUtils().register(new BigDecimalConverter(null), BigDecimal.class);
			Map<?, ?> listaProps = beanUtils.describe(applicazioneEsterna);
			Iterator<?> itKeys = listaProps.keySet().iterator();
			while (itKeys.hasNext()) {
				String key = (String) itKeys.next();
				if ("id".equals(key))
					beanUtils.copyProperties(cliente.getId(), applicazioneEsterna.getId());
				else if ("class".equals(key))
					continue;
				else
					beanUtils.copyProperty(cliente, key, listaProps.get(key));
			}
		} catch (Exception e) {
			StringWriter messageWriter = new StringWriter();
			e.printStackTrace(new PrintWriter(messageWriter));
			log.error(messageWriter.toString());
			throw new AurigaLoginException("rimappaCliente[ApplicazioneEsterna]", e);
		}
	}

	public SchemaBean getSchemaBean() {
		return schemaBean;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public Locale getLocale() {
		return locale;
	}

	public AurigaLoginBean getAurigaLoginBean() {
		return lAurigaLoginBean;
	}
}
