/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;

import it.eng.auriga.database.store.dmpk_load_combo.bean.DmpkLoadComboDmfn_load_comboBean;
import it.eng.auriga.database.store.result.bean.StoreResultBean;
import it.eng.auriga.module.business.beans.AurigaLoginBean;
import it.eng.auriga.ui.module.layout.server.iterAtti.datasource.bean.AttiModelliCustomBean;
import it.eng.auriga.ui.module.layout.server.iterAtti.datasource.bean.AvvioIterAttiFieldsVisibilityBean;
import it.eng.auriga.ui.module.layout.server.protocollazione.datasource.bean.UtenteBean;
import it.eng.auriga.ui.module.layout.shared.bean.SchemaSelector;
import it.eng.auriga.ui.module.layout.shared.util.SharedConstants;
import it.eng.client.DmpkLoadComboDmfn_load_combo;
import it.eng.config.EditorOrganigrammaConfig;
import it.eng.spring.utility.SpringAppContext;
import it.eng.utility.formati.FormatiUtil;
import it.eng.utility.ui.module.core.server.datasource.AbstractServiceDataSource;
import it.eng.utility.ui.module.core.server.datasource.annotation.Datasource;
import it.eng.utility.ui.module.layout.shared.bean.LoginBean;
import it.eng.utility.ui.user.AurigaUserUtil;
import it.eng.utility.ui.user.ParametriDBUtil;
import it.eng.xml.XmlListaUtility;

@Datasource(id="AurigaConfigurationDataSource")
public class AurigaConfigurationDataSource extends AbstractServiceDataSource<LoginBean, AurigaConfigurationBean>{
	
	@Override
	public AurigaConfigurationBean call(LoginBean bean) throws Exception {
		
		SchemaSelector lSchemaSelector = (SchemaSelector) SpringAppContext.getContext().getBean("SchemaConfigurator");

		AurigaLoginBean loginBean = new AurigaLoginBean();
		loginBean.setLinguaApplicazione(SharedConstants.DEFAUL_LANGUAGE);
		// Verifico se ho lo schema selezionato in sessione
		if (getSession() != null && getSession().getAttribute("LOGIN_INFO") != null && StringUtils.isNotBlank(((LoginBean) getSession().getAttribute("LOGIN_INFO")).getSchema())) {
			loginBean.setSchema(((LoginBean) getSession().getAttribute("LOGIN_INFO")).getSchema());
		} else {
			loginBean.setSchema(lSchemaSelector.getSchemi().get(0).getName());
		}
		
		// Inizializzo i formati convertibili
		try { FormatiUtil.init(loginBean); } catch(Exception e) {}
				
		AurigaConfigurationBean result = new AurigaConfigurationBean();
		
		// Recupero i parametri DB
		result.setParametriDB(ParametriDBUtil.getParametriDB(getSession()).getParametriDB());
		
		// Recupero i modelli associati agli atti
		try {
			AttiModelliCustomBean lAttiModelliCustomBean = (AttiModelliCustomBean)SpringAppContext.getContext().getBean("attiModelliCustom");
			result.setNomiModelliAtti(lAttiModelliCustomBean.getConfigMap());
		} catch(Exception e ) {
			result.setNomiModelliAtti(new HashMap<String, String>());
		}
		
		// Recupero l'url dell'editor organigramma
		try {
			EditorOrganigrammaConfig lEditorOrganigrammaConfig = (EditorOrganigrammaConfig)SpringAppContext.getContext().getBean("EditorOrganigrammaConfigurator");
			result.setUrlEditorOrganigramma(lEditorOrganigrammaConfig.getUrl());
		} catch(Exception e ) {
			result.setUrlEditorOrganigramma(null);
		}
		
		// Recupero i campi da nascondere nella maschera di iter atto
		try {		
			AvvioIterAttiFieldsVisibilityBean lAvvioIterAttiFieldsVisibilityBean = (AvvioIterAttiFieldsVisibilityBean)SpringAppContext.getContext().getBean("avvioIterAttiFieldsVisibilityBean");
			Map<Integer,Set<String>> hiddenFieldsAtti = new HashMap<Integer, Set<String>>();
			hiddenFieldsAtti.put(lAvvioIterAttiFieldsVisibilityBean.getTipoAttoId(), lAvvioIterAttiFieldsVisibilityBean.getHiddenFields());
			result.setHiddenFieldsAtti(hiddenFieldsAtti);
		} catch(Exception e ) {
			result.setHiddenFieldsAtti(new HashMap<Integer, Set<String>>());
		}	
		
		// Recupero gli utenti abilitati CPA (conferma pre-assegnazione)
		try {
			String idUtente = StringUtils.isNotBlank(getExtraparams().get("idUtente")) ? getExtraparams().get("idUtente") : "";
			DmpkLoadComboDmfn_load_comboBean lDmpkLoadComboDmfn_load_comboBean = new DmpkLoadComboDmfn_load_comboBean();
			DmpkLoadComboDmfn_load_combo lDmpkLoadComboDmfn_load_combo = new DmpkLoadComboDmfn_load_combo();
			lDmpkLoadComboDmfn_load_comboBean.setTipocomboin("UTENTI_ABIL_CPA");
			lDmpkLoadComboDmfn_load_comboBean.setAltriparametriin("CI_TO_ADD|*|" + idUtente);	
			lDmpkLoadComboDmfn_load_comboBean.setFlgsolovldin(BigDecimal.ONE);
			StoreResultBean<DmpkLoadComboDmfn_load_comboBean> lStoreResultBean =  lDmpkLoadComboDmfn_load_combo.execute(getLocale(), AurigaUserUtil.getLoginInfo(getSession()), lDmpkLoadComboDmfn_load_comboBean);
			String xmlLista = lStoreResultBean.getResultBean().getListaxmlout();
			List<UtenteBean> lList = XmlListaUtility.recuperaLista(xmlLista, UtenteBean.class);
			result.setUtentiAbilCPAList(lList);
		} catch(Exception e ) {
			result.setUtentiAbilCPAList(new ArrayList<UtenteBean>());
		}
		
		return result;
	}

}