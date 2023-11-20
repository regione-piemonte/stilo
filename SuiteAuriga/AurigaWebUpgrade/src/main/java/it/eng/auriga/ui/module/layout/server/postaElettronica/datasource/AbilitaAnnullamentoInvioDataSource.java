/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.math.BigDecimal;
import java.util.HashMap;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import it.eng.auriga.database.store.dmpk_int_mgo_email.bean.DmpkIntMgoEmailAnnullaassegnazioneemailBean;
import it.eng.auriga.database.store.result.bean.StoreResultBean;
import it.eng.auriga.module.business.beans.AurigaLoginBean;
import it.eng.auriga.ui.module.layout.server.postaElettronica.datasource.bean.AbilitaAnnullamentoInvioBean;
import it.eng.auriga.ui.module.layout.server.postaElettronica.datasource.bean.PostaElettronicaBean;
import it.eng.client.DmpkIntMgoEmailAnnullaassegnazioneemail;
import it.eng.utility.ui.module.core.server.datasource.AbstractServiceDataSource;
import it.eng.utility.ui.module.core.server.datasource.annotation.Datasource;
import it.eng.utility.ui.module.core.shared.message.MessageType;
import it.eng.utility.ui.user.AurigaUserUtil;

/**
 * 
 * @author cristiano
 *
 */

@Datasource(id = "AbilitaAnnullamentoInvioDataSource")
public class AbilitaAnnullamentoInvioDataSource extends AbstractServiceDataSource<AbilitaAnnullamentoInvioBean, AbilitaAnnullamentoInvioBean> {

	private static Logger mLogger = Logger.getLogger(AbilitaAnnullamentoInvioDataSource.class);

	@Override
	public AbilitaAnnullamentoInvioBean call(AbilitaAnnullamentoInvioBean pInBean) throws Exception {
		AurigaLoginBean loginBean = AurigaUserUtil.getLoginInfo(getSession());
		String token = loginBean.getToken();
		String idUserLavoro = loginBean.getIdUserLavoro();

		HashMap<String, String> errorMessages = null;

		if (pInBean.getListaAnnullamenti() != null && pInBean.getListaAnnullamenti().size() > 0) {
			for (PostaElettronicaBean email : pInBean.getListaAnnullamenti()) {
				DmpkIntMgoEmailAnnullaassegnazioneemailBean input = new DmpkIntMgoEmailAnnullaassegnazioneemailBean();
				input.setCodidconnectiontokenin(token);
				input.setIduserlavoroin(StringUtils.isNotBlank(idUserLavoro) ? new BigDecimal(idUserLavoro) : null);
				input.setIdassemailtoannin(null);
				input.setIdemailin(email.getIdEmail());
				input.setMotiviin(pInBean.getMessaggio());

				DmpkIntMgoEmailAnnullaassegnazioneemail annullaAssegnazione = new DmpkIntMgoEmailAnnullaassegnazioneemail();
				StoreResultBean<DmpkIntMgoEmailAnnullaassegnazioneemailBean> output = annullaAssegnazione.execute(getLocale(), loginBean, input);

				if (StringUtils.isNotBlank(output.getDefaultMessage())) {
					if (output.isInError()) {
						mLogger.error("Errore nel recupero dell'output: " + output.getDefaultMessage());
					} else {
						addMessage(output.getDefaultMessage(), "", MessageType.WARNING);
					}
					if (errorMessages == null) {
						errorMessages = new HashMap<String, String>();
						errorMessages.put(email.getIdEmail(), output.getDefaultMessage());
					}
				}
			}
		}

		pInBean.setErrorMessages(errorMessages);

		return pInBean;
	}

}
