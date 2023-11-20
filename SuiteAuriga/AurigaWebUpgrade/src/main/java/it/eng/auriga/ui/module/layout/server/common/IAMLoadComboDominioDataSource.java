/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import it.eng.auriga.database.store.dmpk_load_combo.bean.DmpkLoadComboDmfn_load_comboBean;
import it.eng.auriga.database.store.result.bean.StoreResultBean;
import it.eng.auriga.module.business.beans.AurigaLoginBean;
import it.eng.client.DmpkLoadComboDmfn_load_combo;
import it.eng.utility.ui.module.core.server.datasource.AbstractServiceDataSource;
import it.eng.utility.ui.module.core.server.datasource.annotation.Datasource;
import it.eng.xml.XmlListaUtility;

@Datasource(id = "IAMLoadComboDominioDataSource")
public class IAMLoadComboDominioDataSource extends AbstractServiceDataSource<AurigaLoginBean, IAMLoadComboDominioBean> {
	
	private static Logger mLogger = Logger.getLogger(IAMLoadComboDominioDataSource.class);
	
	@Override
	public IAMLoadComboDominioBean call(AurigaLoginBean bean) throws Exception {

		DmpkLoadComboDmfn_load_combo load_combo = new DmpkLoadComboDmfn_load_combo();
		DmpkLoadComboDmfn_load_comboBean lBean = new DmpkLoadComboDmfn_load_comboBean();
		lBean.setTipocomboin("DOMINI_ACCRED");
		lBean.setFlgsolovldin(BigDecimal.ONE);
		String iamUsername = getSession().getAttribute("IAM_USERNAME") != null ? getSession().getAttribute("IAM_USERNAME").toString() : null;
		String iamMatricola = getSession().getAttribute("IAM_MATRICOLA") != null ? getSession().getAttribute("IAM_MATRICOLA").toString() : null;
		if (StringUtils.isBlank(iamUsername) && StringUtils.isBlank(iamMatricola)) {
			iamUsername = getSession().getAttribute("USERNAME_SSO") != null ? getSession().getAttribute("USERNAME_SSO").toString() : null;
		}

		List<DominioBean> lista = null;
		
		boolean dominiTrovati = false;
		try {
			lBean.setAltriparametriin("USERNAME|*|" + iamUsername);
			bean.setUserid(iamUsername);
			mLogger.debug("chiamo DmpkLoadComboDmfn_load_combo con USERNAME|*|" + iamUsername);
			StoreResultBean<DmpkLoadComboDmfn_load_comboBean> lStoreResultBean = load_combo.execute(getLocale(), bean, lBean);
			if (!lStoreResultBean.isInError()){
				String xmlLista = load_combo.execute(getLocale(), bean, lBean).getResultBean().getListaxmlout();
				mLogger.debug("Domini trovati: " + xmlLista);
				lista = XmlListaUtility.recuperaLista(xmlLista, DominioBean.class);
				dominiTrovati = !lista.isEmpty();
				mLogger.debug("dominiTrovati: " + dominiTrovati);
			}
		} catch (Exception e) {
			mLogger.error(e);
		}
		
		if (!dominiTrovati) {
			try{	
				mLogger.debug("Non ho trovato nessun dominio");
				lBean.setAltriparametriin("USERNAME|*|" + iamMatricola);
				bean.setUserid(iamMatricola);
				mLogger.debug("chiamo DmpkLoadComboDmfn_load_combo con USERNAME|*|" + iamMatricola);
				StoreResultBean<DmpkLoadComboDmfn_load_comboBean> lStoreResultBean = load_combo.execute(getLocale(), bean, lBean);
				if (!lStoreResultBean.isInError()){
					String xmlLista = load_combo.execute(getLocale(), bean, lBean).getResultBean().getListaxmlout();
					mLogger.debug("Domini trovati: " + xmlLista);
					lista = XmlListaUtility.recuperaLista(xmlLista, DominioBean.class);
					dominiTrovati = !lista.isEmpty();
					mLogger.debug("dominiTrovati: " + dominiTrovati);
					if (!dominiTrovati) {
						mLogger.error("Non ho trovato nessun dominio associato a iamUsername " + iamUsername  + " o iamMatricola " + iamMatricola);
					}
				}
			} catch (Exception e) {
				mLogger.warn(e);
			}
		}
		
		if (lista == null) {
			lista = new ArrayList<DominioBean>();
		}

		IAMLoadComboDominioBean lDominioComboBean = new IAMLoadComboDominioBean();
		lDominioComboBean.setDomini(lista);
		lDominioComboBean.setIamUsername(iamUsername);
		lDominioComboBean.setIamMatricola(iamMatricola);

		return lDominioComboBean;
	}

}
