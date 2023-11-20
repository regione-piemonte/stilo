/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

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

import java.util.List;

import org.apache.commons.lang3.StringUtils;

@Datasource(id = "UoCollegateUtenteApplicazioneEstDatasource")
public class UoCollegateUtenteApplicazioneEstDatasource extends AbstractFetchDataSource<UoProtocollanteBean> {

	@Override
	public PaginatorBean<UoProtocollanteBean> fetch(AdvancedCriteria criteria, Integer startRow, Integer endRow, List<OrderByBean> orderby) throws Exception {
		AurigaLoginBean lAurigaLoginBean = AurigaUserUtil.getLoginInfo(getSession());
		DmpkLoadComboDmfn_load_comboBean bean = new DmpkLoadComboDmfn_load_comboBean();
		bean.setCodidconnectiontokenin(lAurigaLoginBean.getToken());
		
		
		String idUser = StringUtils.isNotBlank(getExtraparams().get("idUser")) ? getExtraparams().get("idUser") : "";
		
		
		bean.setTipocomboin("SOGG_INT_COLLEGATI_UTENTE");
		
		String altriParametri = "TIPI_SOGG_INT|*|UO|*|FLG_ANCHE_CON_DELEGA|*|1|*|ID_USER_LAVORO|*|" + (idUser != null ? idUser : "");
		
		bean.setAltriparametriin(altriParametri);
		
		
		DmpkLoadComboDmfn_load_combo store = new DmpkLoadComboDmfn_load_combo();
		StoreResultBean<DmpkLoadComboDmfn_load_comboBean> storeResult = store.execute(getLocale(), lAurigaLoginBean, bean);
		if (storeResult.isInError()) {
			throw new StoreException(storeResult);
		}
		bean = storeResult.getResultBean();
		String lStrXml = bean.getListaxmlout();
		List<UoProtocollanteBean> lListResult = XmlListaUtility.recuperaLista(lStrXml, UoProtocollanteBean.class);
		PaginatorBean<UoProtocollanteBean> result = new PaginatorBean<UoProtocollanteBean>(lListResult);
		return result;
	}

}
