/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.auriga.database.store.dmpk_int_mgo_email.bean.DmpkIntMgoEmailGetabilazioniinteropBean;
import it.eng.auriga.database.store.result.bean.StoreResultBean;
import it.eng.auriga.exception.StoreException;
import it.eng.auriga.module.business.beans.AurigaLoginBean;
import it.eng.auriga.ui.module.layout.server.postaElettronica.datasource.bean.AbilitazioniInteroperabiliBean;
import it.eng.auriga.ui.module.layout.server.postaElettronica.datasource.bean.PostaElettronicaBean;
import it.eng.client.DmpkIntMgoEmailGetabilazioniinterop;
import it.eng.utility.ui.module.core.server.datasource.AbstractServiceDataSource;
import it.eng.utility.ui.module.core.server.datasource.annotation.Datasource;
import it.eng.utility.ui.module.core.shared.message.MessageType;
import it.eng.utility.ui.user.AurigaUserUtil;

import java.math.BigDecimal;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

@Datasource(id = "AurigaAbilitazioniInterop")
public class AurigaAbilitazioniInterop extends AbstractServiceDataSource<PostaElettronicaBean, AbilitazioniInteroperabiliBean> {

	private static Logger mLogger = Logger.getLogger(AurigaAbilitazioniInterop.class);

	@Override
	public AbilitazioniInteroperabiliBean call(PostaElettronicaBean pInBean) throws Exception {
		AurigaLoginBean loginBean = AurigaUserUtil.getLoginInfo(getSession());
		String token = loginBean.getToken();
		String idUserLavoro = loginBean.getIdUserLavoro();

		DmpkIntMgoEmailGetabilazioniinterop store = new DmpkIntMgoEmailGetabilazioniinterop();
		DmpkIntMgoEmailGetabilazioniinteropBean bean = new DmpkIntMgoEmailGetabilazioniinteropBean();
		bean.setCodidconnectiontokenin(token);
		bean.setIduserlavoroin(StringUtils.isNotBlank(idUserLavoro) ? new BigDecimal(idUserLavoro) : null);
		bean.setIdemailin(pInBean.getIdEmail());

		StoreResultBean<DmpkIntMgoEmailGetabilazioniinteropBean> lResult = store.execute(getLocale(), loginBean, bean);
		if (StringUtils.isNotBlank(lResult.getDefaultMessage())) {
			if (lResult.isInError()) {
				mLogger.error("Errore nel recupero dell'output: " + lResult.getDefaultMessage());
				throw new StoreException(lResult);
			} else {
				addMessage(lResult.getDefaultMessage(), "", MessageType.WARNING);
			}
		}
		bean = lResult.getResultBean();

		AbilitazioniInteroperabiliBean out = new AbilitazioniInteroperabiliBean();
		out.setAggiornamento(bean.getFlgabilinvioaggiornamentoout() == 1);
		out.setAnnullamento(bean.getFlgabilinvioannullamentoout() == 1);
		out.setProtocolla(bean.getFlgabilprotentrataout() == 1);
		out.setConferma(bean.getFlgabilinvioconfermaout() == 1);
		out.setEccezione(bean.getFlgabilinvioeccezioneout() == 1);
		return out;
	}

}
