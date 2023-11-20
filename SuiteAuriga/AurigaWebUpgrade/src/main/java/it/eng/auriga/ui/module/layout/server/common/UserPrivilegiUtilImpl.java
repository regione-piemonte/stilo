/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.auriga.database.store.bean.SchemaBean;
import it.eng.auriga.database.store.dmpk_login.bean.DmpkLoginGetuserprivsBean;
import it.eng.auriga.database.store.result.bean.StoreResultBean;
import it.eng.auriga.module.business.beans.AurigaLoginBean;
import it.eng.client.DmpkLoginGetuserprivs;
import it.eng.utility.ui.module.layout.server.common.UserPrivilegiUtil;
import it.eng.utility.ui.user.AurigaUserUtil;
import it.eng.utility.ui.user.UserUtil;
import it.eng.xml.XmlListaUtility;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

public class UserPrivilegiUtilImpl implements UserPrivilegiUtil {

	private static Logger mLogger = Logger
			.getLogger(UserPrivilegiUtilImpl.class);
	
	@Override
	public String[] getPrivilegi(HttpSession pSession) {
		
		AurigaLoginBean lAurigaLoginBean = AurigaUserUtil.getLoginInfo(pSession);
		if ((lAurigaLoginBean != null) && (lAurigaLoginBean.getSpecializzazioneBean()!= null) && (lAurigaLoginBean.getSpecializzazioneBean().getPrivilegi() != null)) {
			mLogger.debug("Privilegi già presenti in sessione");
			return lAurigaLoginBean.getSpecializzazioneBean().getPrivilegi().toArray(new String[] {});
		} else {
			return recuperaPrivilegi(pSession); 
		}
	}

	public String[] recuperaPrivilegi(HttpSession pSession) {
		mLogger.debug("Chiamata alla store DmpkLoginGetuserprivs per il recupero dei privilegi");
		
		AurigaLoginBean lAurigaLoginBean = AurigaUserUtil
				.getLoginInfo(pSession);
		DmpkLoginGetuserprivs lDmpkLoginGetuserprivs = new DmpkLoginGetuserprivs();
		DmpkLoginGetuserprivsBean bean = new DmpkLoginGetuserprivsBean();
		if (StringUtils.isNotBlank(lAurigaLoginBean.getIdUserLavoro())) {
			mLogger.debug("IdUserLavoro: " + lAurigaLoginBean.getIdUserLavoro());
			bean.setIduserin(new BigDecimal(lAurigaLoginBean.getIdUserLavoro()));
		} else {
			bean.setIduserin(lAurigaLoginBean.getIdUser());
			mLogger.debug("IdUser: " + lAurigaLoginBean.getIdUser());
		}
		bean.setIddominioautin(lAurigaLoginBean.getSpecializzazioneBean()
				.getIdDominio());
		mLogger.debug("Iddominioautin: " + lAurigaLoginBean.getSpecializzazioneBean()
				.getIdDominio());
		
		bean.setFlgtpdominioautin(lAurigaLoginBean.getSpecializzazioneBean()
				.getTipoDominio());
		mLogger.debug("Flgtpdominioautin: " + lAurigaLoginBean.getSpecializzazioneBean()
				.getTipoDominio());
		
		StoreResultBean<DmpkLoginGetuserprivsBean> output;
		SchemaBean lSchemaBean = new SchemaBean();
		mLogger.debug("Schema: " + lAurigaLoginBean.getSchema());
		lSchemaBean.setSchema(lAurigaLoginBean.getSchema());
		try {
			output = lDmpkLoginGetuserprivs.execute(
					UserUtil.getLocale(pSession), lSchemaBean, bean);
		} catch (Exception e) {
			return new String[] {};
		}
		if (output.isInError()) {
			return new String[] {};
		} else {
			String lStringXml = output.getResultBean().getPrivslistout();
			mLogger.debug("Privslistout:/n" + lStringXml);
			try {
				List<PrivilegioBean> lList = XmlListaUtility.recuperaLista(
						lStringXml, PrivilegioBean.class);
				List<String> lString = new ArrayList<String>(lList.size());
				for (PrivilegioBean lPrivilegioBean : lList) {
					lString.add(lPrivilegioBean.getPrivilegio());
				}
				lAurigaLoginBean.getSpecializzazioneBean()
						.setPrivilegi(lString);
				// AurigaUserUtil.setLoginInfo(pSession, lAurigaLoginBean);
				return lString.toArray(new String[] {});

			} catch (Exception e) {
				return new String[] {};
			}
		}
	}

}
