/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import it.eng.auriga.database.store.dmpk_load_combo.bean.DmpkLoadComboDmfn_load_comboBean;
import it.eng.auriga.database.store.result.bean.StoreResultBean;
import it.eng.auriga.ui.module.layout.server.foglioImportato.bean.TipoTracciatoFoglioBean;
import it.eng.client.DmpkLoadComboDmfn_load_combo;
import it.eng.utility.XmlUtility;
import it.eng.utility.ui.module.core.server.bean.AdvancedCriteria;
import it.eng.utility.ui.module.core.server.bean.OrderByBean;
import it.eng.utility.ui.module.core.server.bean.PaginatorBean;
import it.eng.utility.ui.module.core.server.datasource.OptionFetchDataSource;
import it.eng.utility.ui.module.core.server.datasource.annotation.Datasource;
import it.eng.utility.ui.module.layout.shared.bean.SimpleKeyValueBean;
import it.eng.utility.ui.user.AurigaUserUtil;

/**
 * 
 * @author OPASSALACQUA
 *
 */

@Datasource(id = "LoadComboTipoTracciatoFoglioDS")
public class LoadComboTipoTracciatoFoglioDS extends OptionFetchDataSource<TipoTracciatoFoglioBean> {

	@Override
	public PaginatorBean<TipoTracciatoFoglioBean> fetch(AdvancedCriteria criteria, Integer startRow, Integer endRow, List<OrderByBean> orderby) throws Exception {
		
		// Inizializzo l'INPUT
		DmpkLoadComboDmfn_load_comboBean lDmpkLoadComboDmfn_load_comboBean = new DmpkLoadComboDmfn_load_comboBean();
		DmpkLoadComboDmfn_load_combo lDmpkLoadComboDmfn_load_combo = new DmpkLoadComboDmfn_load_combo();
		lDmpkLoadComboDmfn_load_comboBean.setFlgsolovldin(new BigDecimal(1));
		lDmpkLoadComboDmfn_load_comboBean.setTipocomboin("VALORI_DIZIONARIO");
		lDmpkLoadComboDmfn_load_comboBean.setAltriparametriin("DICTIONARY_ENTRY|*|TIPO_FOGLI_IMPORT");

		StoreResultBean<DmpkLoadComboDmfn_load_comboBean> lStoreResultBean = lDmpkLoadComboDmfn_load_combo.execute(getLocale(), AurigaUserUtil.getLoginInfo(getSession()), lDmpkLoadComboDmfn_load_comboBean);

		String xmlLista = lStoreResultBean.getResultBean().getListaxmlout();
		
        try {
			List<SimpleKeyValueBean> lListResult = XmlUtility.recuperaListaSemplice(xmlLista);
			List<TipoTracciatoFoglioBean> lListResultTipoTracciato = new ArrayList<TipoTracciatoFoglioBean>();
			for (SimpleKeyValueBean lSimpleKeyValueBean : lListResult){
				TipoTracciatoFoglioBean lSelezionaBean = new TipoTracciatoFoglioBean();
				lSelezionaBean.setIdTipo(lSimpleKeyValueBean.getKey());
				lSelezionaBean.setNomeTipo(lSimpleKeyValueBean.getValue());
				lListResultTipoTracciato.add(lSelezionaBean);
			}
			PaginatorBean<TipoTracciatoFoglioBean> result = new PaginatorBean<TipoTracciatoFoglioBean>(lListResultTipoTracciato);
			return result;
		
		} catch (Exception e){
			return new PaginatorBean<TipoTracciatoFoglioBean>(new ArrayList<TipoTracciatoFoglioBean>());
		}
	}
}