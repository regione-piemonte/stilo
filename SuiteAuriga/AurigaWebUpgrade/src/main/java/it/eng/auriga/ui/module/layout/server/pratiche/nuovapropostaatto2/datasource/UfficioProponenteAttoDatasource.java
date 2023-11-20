/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import it.eng.auriga.database.store.dmpk_load_combo.bean.DmpkLoadComboDmfn_load_comboBean;
import it.eng.auriga.database.store.result.bean.StoreResultBean;
import it.eng.auriga.exception.StoreException;
import it.eng.auriga.module.business.beans.AurigaLoginBean;
import it.eng.auriga.ui.module.layout.server.protocollazione.datasource.bean.UoProtocollanteBean;
import it.eng.client.DmpkLoadComboDmfn_load_combo;
import it.eng.utility.ui.module.core.server.bean.AdvancedCriteria;
import it.eng.utility.ui.module.core.server.bean.OrderByBean;
import it.eng.utility.ui.module.core.server.bean.PaginatorBean;
import it.eng.utility.ui.module.core.server.datasource.AbstractFetchDataSource;
import it.eng.utility.ui.module.core.server.datasource.annotation.Datasource;
import it.eng.utility.ui.user.AurigaUserUtil;
import it.eng.xml.XmlListaUtility;

@Datasource(id = "UfficioProponenteAttoDatasource")
public class UfficioProponenteAttoDatasource extends AbstractFetchDataSource<UoProtocollanteBean> {

	@Override
	public PaginatorBean<UoProtocollanteBean> fetch(AdvancedCriteria criteria, Integer startRow, Integer endRow, List<OrderByBean> orderby) throws Exception {
		AurigaLoginBean lAurigaLoginBean = AurigaUserUtil.getLoginInfo(getSession());
		String idUserLavoro = lAurigaLoginBean.getIdUserLavoro() != null ? lAurigaLoginBean.getIdUserLavoro() : "";
		DmpkLoadComboDmfn_load_comboBean bean = new DmpkLoadComboDmfn_load_comboBean();
		bean.setCodidconnectiontokenin(lAurigaLoginBean.getToken());
		bean.setTipocomboin("SOGG_INT_COLLEGATI_UTENTE");
		String altriParametri = null;
		if(StringUtils.isNotBlank(getExtraparams().get("altriParamLoadCombo"))) {
			altriParametri = getExtraparams().get("altriParamLoadCombo");			
			altriParametri = altriParametri.replace("$ID_USER_LAVORO$", idUserLavoro);	
			altriParametri = altriParametri.replace("$ID_TIPO_DOC$", StringUtils.isNotBlank(getExtraparams().get("idTipoDoc")) ? getExtraparams().get("idTipoDoc") : "");			
		} else {
			altriParametri = "TIPI_SOGG_INT|*|UO|*|FLG_ANCHE_CON_DELEGA|*|1|*|FLG_INCL_SOTTOUO_SV|*|1|*|ID_USER_LAVORO|*|" + idUserLavoro;			
		}
		altriParametri = (StringUtils.isNotBlank(altriParametri)) ? (altriParametri + "|*|SPECIFICITA|*|AVVIO_ITER_ATTO") : ("|*|SPECIFICITA|*|AVVIO_ITER_ATTO");
		bean.setAltriparametriin(altriParametri);
		DmpkLoadComboDmfn_load_combo store = new DmpkLoadComboDmfn_load_combo();
		StoreResultBean<DmpkLoadComboDmfn_load_comboBean> storeResult = store.execute(getLocale(), lAurigaLoginBean, bean);
		if (storeResult.isInError()) {
			throw new StoreException(storeResult);
		}
		bean = storeResult.getResultBean();
		String lStrXml = bean.getListaxmlout();
		List<UoProtocollanteBean> lListResult = XmlListaUtility.recuperaLista(lStrXml, UoProtocollanteBean.class);
		if(lListResult != null) {
			for(int i=0; i < lListResult.size(); i++) {
				if(lListResult.get(i).getIdUo().startsWith("UO")) {
					lListResult.get(i).setIdUo(lListResult.get(i).getIdUo().substring(2));
				}
			}
		}
//		lListResult = getTestData();
		PaginatorBean<UoProtocollanteBean> result = new PaginatorBean<UoProtocollanteBean>(lListResult);
		return result;
	}
	
	public List<UoProtocollanteBean> getTestData() {
		List<UoProtocollanteBean> lListResult = new ArrayList<UoProtocollanteBean>();
		UoProtocollanteBean lUoProtocollanteBean= new UoProtocollanteBean();
		lUoProtocollanteBean.setIdUo("101472");
		lUoProtocollanteBean.setDescrizione("A00.A51 - AVVOCATURA");
		lListResult.add(lUoProtocollanteBean);
		return lListResult;
	}

}
