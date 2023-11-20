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

import it.eng.auriga.database.store.dmpk_int_mgo_email.bean.DmpkIntMgoEmailCtrllockemailBean;
import it.eng.auriga.database.store.dmpk_int_mgo_email.bean.DmpkIntMgoEmailLockemailBean;
import it.eng.auriga.database.store.result.bean.StoreResultBean;
import it.eng.auriga.exception.StoreException;
import it.eng.auriga.module.business.beans.AurigaLoginBean;
import it.eng.auriga.ui.module.layout.server.postaElettronica.datasource.bean.ControlloLockEmailBean;
import it.eng.auriga.ui.module.layout.server.postaElettronica.datasource.bean.LockEmailBean;
import it.eng.auriga.ui.module.layout.server.postaElettronica.datasource.bean.LockEmailXmlBean;
import it.eng.auriga.ui.module.layout.server.postaElettronica.datasource.bean.PostaElettronicaBean;
import it.eng.client.DmpkIntMgoEmailCtrllockemail;
import it.eng.client.DmpkIntMgoEmailLockemail;
import it.eng.jaxb.context.SingletonJAXBContext;
import it.eng.jaxb.variabili.Lista;
import it.eng.utility.XmlUtility;
import it.eng.utility.ui.module.core.server.bean.AdvancedCriteria;
import it.eng.utility.ui.module.core.server.bean.OrderByBean;
import it.eng.utility.ui.module.core.server.bean.PaginatorBean;
import it.eng.utility.ui.module.core.server.datasource.AbstractDataSource;
import it.eng.utility.ui.module.core.server.datasource.annotation.Datasource;
import it.eng.utility.ui.module.core.server.service.ErrorBean;
import it.eng.utility.ui.module.core.shared.message.MessageType;
import it.eng.utility.ui.user.AurigaUserUtil;
import it.eng.xml.XmlUtilitySerializer;

@Datasource(id = "LockEmailDataSource")
public class LockEmailDataSource extends AbstractDataSource<LockEmailBean, LockEmailBean> {

	private static Logger mLogger = Logger.getLogger(LockEmailDataSource.class);

	@Override
	public LockEmailBean add(LockEmailBean bean) throws Exception {

		int isMassivo = getExtraparams().get("isMassivo") != null ? 1 : 0;
		HashMap<String, String> errorMessages = null;
		AurigaLoginBean aurigaLoginBean = AurigaUserUtil.getLoginInfo(getSession());

		List<LockEmailXmlBean> listaLockEmail = new ArrayList<LockEmailXmlBean>();
		LockEmailXmlBean lockEmailXmlBean = null;

		String token = aurigaLoginBean.getToken();
		String idUserLavoro = AurigaUserUtil.getLoginInfo(getSession()).getIdUserLavoro();
		String idUserLockFor = bean.getIduserlockfor();

		DmpkIntMgoEmailLockemailBean input = new DmpkIntMgoEmailLockemailBean();

		input.setCodidconnectiontokenin(token);
		input.setIduserlavoroin(StringUtils.isNotBlank(idUserLavoro) ? new BigDecimal(idUserLavoro) : null);
		input.setIduserlockforin(StringUtils.isNotBlank(idUserLockFor) ? idUserLockFor : null);
		input.setFlglockimplin(isMassivo);

		for (PostaElettronicaBean email : bean.getListaRecord()) {
			lockEmailXmlBean = new LockEmailXmlBean();
			lockEmailXmlBean.setIdEmail(email.getIdEmail());
			listaLockEmail.add(lockEmailXmlBean);
		}

		XmlUtilitySerializer lXmlUtilitySerializer = new XmlUtilitySerializer();
		String xmlInput = lXmlUtilitySerializer.bindXmlList(listaLockEmail);
		input.setListaidemailin(xmlInput);
		input.setMotiviin(bean.getMotivi());

		DmpkIntMgoEmailLockemail lockMail = new DmpkIntMgoEmailLockemail();

		StoreResultBean<DmpkIntMgoEmailLockemailBean> output = lockMail.execute(getLocale(), aurigaLoginBean, input);

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
					mLogger.error("Errore durante il lock della mail con id " + v.get(0) + ": " + v.get(4));
					errorMessages.put(v.get(0), v.get(4));
				}
			}
		}
		bean.setErrorMessages(errorMessages);

		return bean;
	}

	@Override
	public LockEmailBean get(LockEmailBean bean) throws Exception {
		
		return null;
	}

	@Override
	public LockEmailBean remove(LockEmailBean bean) throws Exception {
		
		return null;
	}

	@Override
	public LockEmailBean update(LockEmailBean bean, LockEmailBean oldvalue) throws Exception {
		
		return bean;
	}

	@Override
	public PaginatorBean<LockEmailBean> fetch(AdvancedCriteria criteria, Integer startRow, Integer endRow, List<OrderByBean> orderby) throws Exception {
		
		return null;
	}

	@Override
	public Map<String, ErrorBean> validate(LockEmailBean bean) throws Exception {
		
		return null;
	}

	public ControlloLockEmailBean checkLockEmail(PostaElettronicaBean postaElettronicaBean) throws Exception {

		DmpkIntMgoEmailCtrllockemail checkLockMail = new DmpkIntMgoEmailCtrllockemail();

		AurigaLoginBean aurigaLoginBean = AurigaUserUtil.getLoginInfo(getSession());

		String token = aurigaLoginBean.getToken();
		String idUserLavoro = aurigaLoginBean.getIdUserLavoro();

		DmpkIntMgoEmailCtrllockemailBean checkLockBean = new DmpkIntMgoEmailCtrllockemailBean();
		checkLockBean.setIduserlavoroin(StringUtils.isNotBlank(idUserLavoro) ? new BigDecimal(idUserLavoro) : null);
		checkLockBean.setIdemailin(postaElettronicaBean.getIdEmail());
		checkLockBean.setCodidconnectiontokenin(token);

		StoreResultBean<DmpkIntMgoEmailCtrllockemailBean> output = checkLockMail.execute(getLocale(), aurigaLoginBean, checkLockBean);
		if (StringUtils.isNotBlank(output.getDefaultMessage())) {
			if (output.isInError()) {
				throw new StoreException(output);
			} else {
				addMessage(output.getDefaultMessage(), "", MessageType.WARNING);
			}
		}

		ControlloLockEmailBean controlloLockEmail = new ControlloLockEmailBean();
		controlloLockEmail.setFlagPresenzaLock(output.getResultBean().getFlgpresenzalockaltriout() == 1);
		controlloLockEmail.setFlagForzaLock(output.getResultBean().getFlglockforzabileout() == 1);
		controlloLockEmail.setErrorMessage(output.getResultBean().getDatilockaltriout());

		return controlloLockEmail;

	}

}
