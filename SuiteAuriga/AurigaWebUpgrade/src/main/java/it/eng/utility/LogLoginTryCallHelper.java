/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.math.BigDecimal;
import java.text.MessageFormat;
import java.util.Locale;

import org.apache.log4j.Logger;

import it.eng.auriga.database.store.dmpk_login.bean.DmpkLoginLoglogintryBean;
import it.eng.auriga.database.store.result.bean.StoreResultBean;
import it.eng.auriga.module.business.beans.AurigaLoginBean;
import it.eng.client.DmpkLoginLoglogintry;

public class LogLoginTryCallHelper {

	public static final String FUNCTION_NAME = "DMPK_LOGIN.LogLoginTry";
	private static final Logger logger = Logger.getLogger(LogLoginTryCallHelper.class);
	private final DmpkLoginLoglogintry client = new DmpkLoginLoglogintry();
	private final Locale locale = Locale.ITALY;

	public final boolean call(AurigaLoginBean aurigaLoginBean, DmpkLoginLoglogintryBean param) throws Exception {
		StoreResultBean<DmpkLoginLoglogintryBean> result = client.execute(locale, aurigaLoginBean, param);
		final boolean flagKO = result.isInError();
		if (flagKO) {
			final String msg = MessageFormat.format("{0} # {1} # {2}", result.getDefaultMessage(), result.getErrorContext(), result.getErrorCode());
			logger.error("L'esecuzione della function '" + FUNCTION_NAME + "' è andata in errore: " + msg);
		}
		return !flagKO;
	}

	public final boolean call(AurigaLoginBean aurigaLoginBean, String codApplicazione, String username, boolean flagKO, Integer tipoDominioAut,
			BigDecimal idDominioAut, String codIdConnectionToken, String parametriClient) throws Exception {
		final DmpkLoginLoglogintryBean param = new DmpkLoginLoglogintryBean();
		param.setCodapplicazionein(codApplicazione);
		param.setCodistanzaapplin(null);
		param.setFlgtpdominioautin(tipoDominioAut);
		param.setIddominioautin(idDominioAut);
		param.setUsernamein(username);
		param.setEsitologinin(flagKO ? 0 : 1);
		param.setCodidconnectiontokenin(codIdConnectionToken);
		param.setParametriclientin(parametriClient);
		// param.setFlgautocommitin(1);
		// param.setFlgrollbckfullin(0);
		aurigaLoginBean.setToken(codIdConnectionToken);
		return call(aurigaLoginBean, param);
	}

	public final boolean call(String schema, String codApplicazione, DmpkLoginLoglogintryBean param) throws Exception {
		final AurigaLoginBean aurigaLoginBean = new AurigaLoginBean();
		aurigaLoginBean.setCodApplicazione(codApplicazione);
		aurigaLoginBean.setLinguaApplicazione(locale.getLanguage());
		aurigaLoginBean.setSchema(schema);
		aurigaLoginBean.setToken(param.getCodidconnectiontokenin());
		return call(aurigaLoginBean, param);
	}

	public final boolean call(String schema, String codApplicazione, String username, boolean flagKO, Integer tipoDominioAut, BigDecimal idDominioAut,
			String codIdConnectionToken, String parametriClient) throws Exception {
		final AurigaLoginBean aurigaLoginBean = new AurigaLoginBean();
		aurigaLoginBean.setCodApplicazione(codApplicazione);
		aurigaLoginBean.setLinguaApplicazione(locale.getLanguage());
		aurigaLoginBean.setSchema(schema);
		return call(aurigaLoginBean, codApplicazione, username, flagKO, tipoDominioAut, idDominioAut, codIdConnectionToken, parametriClient);
	}

}// LogLoginTryCallHelper
