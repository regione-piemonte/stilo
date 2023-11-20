/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.auriga.database.store.dmpk_load_combo.bean.DmpkLoadComboDmfn_load_comboBean;
import it.eng.auriga.database.store.result.bean.StoreResultBean;
import it.eng.auriga.ui.module.layout.server.protocollazione.datasource.bean.UtenteBean;
import it.eng.auriga.ui.module.layout.shared.bean.UtentiAbilCPABean;
import it.eng.client.DmpkLoadComboDmfn_load_combo;
import it.eng.utility.ui.module.core.server.datasource.AbstractServiceDataSource;
import it.eng.utility.ui.module.core.server.datasource.annotation.Datasource;
import it.eng.utility.ui.user.AurigaUserUtil;
import it.eng.xml.XmlListaUtility;

import java.math.BigDecimal;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

@Datasource(id="UtentiAbilCPADataSource")

public class UtentiAbilCPADataSource extends AbstractServiceDataSource<UtentiAbilCPABean, UtentiAbilCPABean>{

	@Override
	public UtentiAbilCPABean call(UtentiAbilCPABean bean) throws Exception {

		String idUtente = StringUtils.isNotBlank(getExtraparams().get("idUtente")) ? getExtraparams().get("idUtente") : "";
		
		DmpkLoadComboDmfn_load_comboBean lDmpkLoadComboDmfn_load_comboBean = new DmpkLoadComboDmfn_load_comboBean();
		
		// Inizializzo l'INPUT
		DmpkLoadComboDmfn_load_combo lDmpkLoadComboDmfn_load_combo = new DmpkLoadComboDmfn_load_combo();
		lDmpkLoadComboDmfn_load_comboBean.setTipocomboin("UTENTI_ABIL_CPA");
		lDmpkLoadComboDmfn_load_comboBean.setAltriparametriin("CI_TO_ADD|*|" + idUtente);	
		lDmpkLoadComboDmfn_load_comboBean.setFlgsolovldin(BigDecimal.ONE);
		StoreResultBean<DmpkLoadComboDmfn_load_comboBean> lStoreResultBean =  lDmpkLoadComboDmfn_load_combo.execute(getLocale(), AurigaUserUtil.getLoginInfo(getSession()), lDmpkLoadComboDmfn_load_comboBean);
		
		String xmlLista = lStoreResultBean.getResultBean().getListaxmlout();
		List<UtenteBean> lList = XmlListaUtility.recuperaLista(xmlLista, UtenteBean.class);
		
		UtentiAbilCPABean result = new UtentiAbilCPABean();
		result.setUtentiAbilCPAList(lList);

		return result;
	}

}
