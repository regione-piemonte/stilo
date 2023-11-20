/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.StringReader;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import it.eng.auriga.database.store.dmpk_int_mgo_email.bean.DmpkIntMgoEmailUnlockemailBean;
import it.eng.auriga.database.store.result.bean.StoreResultBean;
import it.eng.auriga.exception.StoreException;
import it.eng.auriga.module.business.beans.AurigaLoginBean;
import it.eng.auriga.ui.module.layout.server.postaElettronica.datasource.bean.PostaElettronicaBean;
import it.eng.auriga.ui.module.layout.server.postaElettronica.datasource.bean.UnlockEmailBean;
import it.eng.auriga.ui.module.layout.server.postaElettronica.datasource.bean.UnlockEmailXmlBean;
import it.eng.client.DmpkIntMgoEmailUnlockemail;
import it.eng.jaxb.context.SingletonJAXBContext;
import it.eng.jaxb.variabili.Lista;
import it.eng.utility.XmlUtility;
import it.eng.utility.ui.module.core.server.bean.AdvancedCriteria;
import it.eng.utility.ui.module.core.server.bean.OrderByBean;
import it.eng.utility.ui.module.core.server.bean.PaginatorBean;
import it.eng.utility.ui.module.core.server.datasource.AbstractDataSource;
import it.eng.utility.ui.module.core.server.datasource.annotation.Datasource;
import it.eng.utility.ui.module.core.server.service.ErrorBean;
import it.eng.utility.ui.user.AurigaUserUtil;
import it.eng.xml.XmlUtilitySerializer;

@Datasource(id = "UnlockEmailDataSource")
public class UnlockEmailDataSource extends AbstractDataSource<UnlockEmailBean, UnlockEmailBean> {

	private static Logger mLogger = Logger.getLogger(UnlockEmailDataSource.class);

	@Override
	public UnlockEmailBean add(UnlockEmailBean bean) throws Exception {

		HashMap<String, String> errorMessages = null;
		AurigaLoginBean aurigaLoginBean = AurigaUserUtil.getLoginInfo(getSession());

		List<UnlockEmailXmlBean> listaLockEmail = new ArrayList<UnlockEmailXmlBean>();
		UnlockEmailXmlBean unlockEmailXmlBean = null;

		String token = aurigaLoginBean.getToken();
		String idUserLavoro = aurigaLoginBean.getIdUserLavoro();

		DmpkIntMgoEmailUnlockemailBean input = new DmpkIntMgoEmailUnlockemailBean();

		input.setCodidconnectiontokenin(token);
		input.setIduserlavoroin(StringUtils.isNotBlank(idUserLavoro) ? new BigDecimal(idUserLavoro) : null);
		input.setMotiviin(bean.getMotivi());

		for (PostaElettronicaBean email : bean.getListaRecord()) {
			unlockEmailXmlBean = new UnlockEmailXmlBean();
			unlockEmailXmlBean.setIdEmail(email.getIdEmail());
			listaLockEmail.add(unlockEmailXmlBean);
		}

		XmlUtilitySerializer lXmlUtilitySerializer = new XmlUtilitySerializer();
		String xmlInput = lXmlUtilitySerializer.bindXmlList(listaLockEmail);
		input.setListaidemailin(xmlInput);

		DmpkIntMgoEmailUnlockemail unlockMail = new DmpkIntMgoEmailUnlockemail();

		StoreResultBean<DmpkIntMgoEmailUnlockemailBean> output = unlockMail.execute(getLocale(), aurigaLoginBean, input);

		bean.setStoreInError(false);
		if (output.isInError()) {
			bean.setStoreInError(true);
			new StoreException(output); // mi serve solo per stampare i log dell'errore
			errorMessages = new HashMap<String, String>();
			String idEmail = bean.getListaRecord() != null && bean.getListaRecord().size() == 1 ? bean.getListaRecord().get(0).getIdEmail() : "";
			if (StringUtils.isNotBlank(output.getDefaultMessage())) {
				errorMessages.put(idEmail, output.getDefaultMessage());				
			} else {
				errorMessages.put(idEmail, "Errore generico");
			}			
		} else if (output.getResultBean().getEsitiout() != null && output.getResultBean().getEsitiout().length() > 0) {
			errorMessages = new HashMap<String, String>();
			StringReader sr = new StringReader(output.getResultBean().getEsitiout());
			Lista lista = (Lista) SingletonJAXBContext.getInstance().createUnmarshaller().unmarshal(sr);
			for (int i = 0; i < lista.getRiga().size(); i++) {
				Vector<String> v = new XmlUtility().getValoriRiga(lista.getRiga().get(i));
				if (v.get(3).equalsIgnoreCase("KO")) {
					mLogger.error("Errore durante l'unlock della mail con id " + v.get(0) + ": " + v.get(4));
					errorMessages.put(v.get(0), v.get(4));
				}
			}
		}
		bean.setErrorMessages(errorMessages);

		return bean;
	}

	@Override
	public UnlockEmailBean get(UnlockEmailBean bean) throws Exception {
		
		return null;
	}

	@Override
	public UnlockEmailBean remove(UnlockEmailBean bean) throws Exception {
		
		return null;
	}

	@Override
	public UnlockEmailBean update(UnlockEmailBean bean, UnlockEmailBean oldvalue) throws Exception {
		
		return bean;
	}

	@Override
	public PaginatorBean<UnlockEmailBean> fetch(AdvancedCriteria criteria, Integer startRow, Integer endRow, List<OrderByBean> orderby) throws Exception {
		
		return null;
	}

	@Override
	public Map<String, ErrorBean> validate(UnlockEmailBean bean) throws Exception {
		
		return null;
	}

}
