/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import it.eng.auriga.database.store.dmpk_int_mgo_email.bean.DmpkIntMgoEmailSetazionedafaresuemailBean;
import it.eng.auriga.database.store.result.bean.StoreResultBean;
import it.eng.auriga.module.business.beans.AurigaLoginBean;
import it.eng.auriga.ui.module.layout.server.postaElettronica.datasource.bean.AzioneDaFareBean;
import it.eng.auriga.ui.module.layout.server.postaElettronica.datasource.bean.PostaElettronicaBean;
import it.eng.client.DmpkIntMgoEmailSetazionedafaresuemail;
import it.eng.utility.ui.module.core.server.bean.AdvancedCriteria;
import it.eng.utility.ui.module.core.server.bean.OrderByBean;
import it.eng.utility.ui.module.core.server.bean.PaginatorBean;
import it.eng.utility.ui.module.core.server.datasource.AbstractDataSource;
import it.eng.utility.ui.module.core.server.datasource.annotation.Datasource;
import it.eng.utility.ui.module.core.server.service.ErrorBean;
import it.eng.utility.ui.user.AurigaUserUtil;

@Datasource(id = "AzioneDaFareDataSource")
public class AzioneDaFareDataSource extends AbstractDataSource<AzioneDaFareBean, AzioneDaFareBean> {

	private static Logger mLogger = Logger.getLogger(AzioneDaFareDataSource.class);

	public AzioneDaFareBean aggiornaAzioneDaFare(AzioneDaFareBean bean) throws Exception {

		AurigaLoginBean loginBean = AurigaUserUtil.getLoginInfo(getSession());
		String token = loginBean.getToken();
		String idUserLavoro = loginBean.getIdUserLavoro();

		HashMap<String, String> errorMessages = new HashMap<String, String>();

		DmpkIntMgoEmailSetazionedafaresuemailBean inputBean = null;

		for (PostaElettronicaBean email : bean.getListaRecord()) {

			if (email.getStatoLavorazione() != null && email.getStatoLavorazione().equalsIgnoreCase("lavorazione conclusa")) {
				errorMessages.put(email.getIdEmail(), "E-mail già archiviata: operazione non consentita");
			} else {
				inputBean = new DmpkIntMgoEmailSetazionedafaresuemailBean();
				inputBean.setIdemailin(email.getIdEmail());
				inputBean.setCodazionedafarein(bean.getCodAzioneDaFare());
				inputBean.setDettazionedafarein(bean.getDettaglioAzioneDaFare());
				inputBean.setCodidconnectiontokenin(token);
				inputBean.setIduserlavoroin(StringUtils.isNotBlank(idUserLavoro) ? new BigDecimal(idUserLavoro) : null);
				inputBean.setFlgrilasciolockin(bean.getFlgRilascia());

				DmpkIntMgoEmailSetazionedafaresuemail dmpkIntMgoEmailSetazionedafaresuemail = new DmpkIntMgoEmailSetazionedafaresuemail();
				StoreResultBean<DmpkIntMgoEmailSetazionedafaresuemailBean> output = dmpkIntMgoEmailSetazionedafaresuemail.execute(getLocale(), loginBean,
						inputBean);
				if (output.isInError()){
					if (output.getDefaultMessage() != null && StringUtils.isNotBlank(output.getDefaultMessage())) {
						errorMessages.put(email.getIdEmail(), output.getDefaultMessage());
					}else{
						errorMessages.put(email.getIdEmail(), "Si è verificato un errore");
					}
				}
			}
		}

		bean.setErrorMessages(errorMessages);

		return bean;
	}

	@Override
	public AzioneDaFareBean get(AzioneDaFareBean bean) throws Exception {
		return null;
	}

	@Override
	public AzioneDaFareBean add(AzioneDaFareBean bean) throws Exception {
		return null;
	}

	@Override
	public AzioneDaFareBean remove(AzioneDaFareBean bean) throws Exception {
		return null;
	}

	@Override
	public AzioneDaFareBean update(AzioneDaFareBean bean, AzioneDaFareBean oldvalue) throws Exception {
		return null;
	}

	@Override
	public PaginatorBean<AzioneDaFareBean> fetch(AdvancedCriteria criteria, Integer startRow, Integer endRow, List<OrderByBean> orderby) throws Exception {
		return null;
	}

	@Override
	public Map<String, ErrorBean> validate(AzioneDaFareBean bean) throws Exception {
		return null;
	}

}