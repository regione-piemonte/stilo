/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.math.BigDecimal;
import java.util.Locale;

import org.apache.log4j.Logger;

import it.eng.auriga.database.store.dmpk_login.bean.DmpkLoginLoginapplicazioneBean;
import it.eng.auriga.database.store.result.bean.StoreResultBean;
import it.eng.auriga.module.business.beans.AurigaLoginBean;
import it.eng.client.DmpkLoginLoginapplicazione;
import it.eng.xml.XmlUtilitySerializer;

public class AurigaDocumentFunctionCallHelper {

	private static final Logger logger = Logger.getLogger(AurigaDocumentFunctionCallHelper.class);

	private final DmpkLoginLoginapplicazione client_LOGIN_APPLICAZIONE;

	private final XmlUtilitySerializer xmlUtilitySerializer;

	public AurigaDocumentFunctionCallHelper() {
		client_LOGIN_APPLICAZIONE = new DmpkLoginLoginapplicazione();
		xmlUtilitySerializer = new XmlUtilitySerializer();
	}

	/**
	 * Chiama la function ORACLE DMPK_LOGIN.LOGINAPPLICAZIONE()
	 * 
	 * @param userId
	 * @param schema
	 * @return
	 * @throws Exception
	 */
	public StoreResultBean<DmpkLoginLoginapplicazioneBean> callLoginApplicazioneFunc(String userId, String schema) throws Exception {
		final AurigaLoginBean aurigaLoginBean = new AurigaLoginBean();
		aurigaLoginBean.setSchema(schema);

		final DmpkLoginLoginapplicazioneBean params = new DmpkLoginLoginapplicazioneBean();
		params.setUseridapplicazionein(userId);
		params.setFlgnoctrlpasswordin(1);

		StoreResultBean<DmpkLoginLoginapplicazioneBean> storeResultBean = null;
		storeResultBean = client_LOGIN_APPLICAZIONE.execute(Locale.ITALIAN, aurigaLoginBean, params);

		return storeResultBean;
	}// callLoginApplicazioneFunc

	private BigDecimal getIdUserLavoro(Number id) {
		return id != null ? new BigDecimal(id.toString()) : null;
	}

}// AurigaMailFunctionCallHelper
