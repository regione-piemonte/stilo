/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import it.eng.auriga.database.store.dmpk_load_combo.bean.DmpkLoadComboDmfn_load_comboBean;
import it.eng.auriga.database.store.result.bean.StoreResultBean;
import it.eng.auriga.exception.StoreException;
import it.eng.auriga.module.business.beans.AurigaLoginBean;
import it.eng.auriga.ui.module.layout.server.protocollazione.datasource.bean.MappeUoProtocollanteBean;
import it.eng.auriga.ui.module.layout.server.protocollazione.datasource.bean.UoProtocollanteBean;
import it.eng.client.DmpkLoadComboDmfn_load_combo;
import it.eng.utility.ui.module.core.server.datasource.AbstractServiceDataSource;
import it.eng.utility.ui.module.core.server.datasource.annotation.Datasource;
import it.eng.utility.ui.user.AurigaUserUtil;
import it.eng.xml.XmlListaUtility;

@Datasource(id = "UoCollegateUtenteDatasource")
public class UoCollegateUtenteDatasource extends AbstractServiceDataSource<MappeUoProtocollanteBean, MappeUoProtocollanteBean>  {

	@Override
	public MappeUoProtocollanteBean call(MappeUoProtocollanteBean lMappeUoProtocollanteBean) throws Exception {
		AurigaLoginBean lAurigaLoginBean = AurigaUserUtil.getLoginInfo(getSession());
		String idUserLavoro = lAurigaLoginBean.getIdUserLavoro() != null ? lAurigaLoginBean.getIdUserLavoro() : "";
		
		Map<String,String> uoRegistrazioneValueMap = new HashMap<String, String>();
		Map<String,UoProtocollanteBean> mappaUoRegistrazione = new HashMap<String, UoProtocollanteBean>();
		Map<String,UoProtocollanteBean> mappaDestProtEntrataDefault = new HashMap<String, UoProtocollanteBean>();
		
		DmpkLoadComboDmfn_load_comboBean bean = new DmpkLoadComboDmfn_load_comboBean();
		bean.setCodidconnectiontokenin(lAurigaLoginBean.getToken());
		bean.setTipocomboin("SOGG_INT_COLLEGATI_UTENTE");
		String altriParametri = "TIPI_SOGG_INT|*|UO|*|FLG_ANCHE_CON_DELEGA|*|1|*|ID_USER_LAVORO|*|" + idUserLavoro;
		altriParametri += "|*|FLG_INCL_SOTTOUO_SV|*|1";
//		if(isSelezioneUoLavoro) altriParametri += "|*|FLG_ANCHE_REL_TIPO_L|*|1"; // andrebbe passato solo in caso di selezione della UO di lavoro, ma per il momento lo lasciamo commentato
		bean.setAltriparametriin(altriParametri);
		DmpkLoadComboDmfn_load_combo store = new DmpkLoadComboDmfn_load_combo();
		StoreResultBean<DmpkLoadComboDmfn_load_comboBean> storeResult = store.execute(getLocale(), lAurigaLoginBean, bean);
		if (storeResult.isInError()) {
			throw new StoreException(storeResult);
		}
		bean = storeResult.getResultBean();
		String lStrXml = bean.getListaxmlout();
		List<UoProtocollanteBean> lListResult = XmlListaUtility.recuperaLista(lStrXml, UoProtocollanteBean.class);
		for(UoProtocollanteBean lUoProtocollanteBean : lListResult) {
			lUoProtocollanteBean.setFlgUoRegistrazione("1");
			lUoProtocollanteBean.setFlgUoProponenteAtti("1");
			uoRegistrazioneValueMap.put(lUoProtocollanteBean.getIdUo(), lUoProtocollanteBean.getDescrizione());
			mappaUoRegistrazione.put(lUoProtocollanteBean.getIdUo(),lUoProtocollanteBean);
			if (lUoProtocollanteBean.getFlgPreimpDestProtEntrata() != null && lUoProtocollanteBean.getFlgPreimpDestProtEntrata().equals("1")) {
				mappaDestProtEntrataDefault.put(lUoProtocollanteBean.getIdUo(),lUoProtocollanteBean);
			}
		}
		MappeUoProtocollanteBean mappe = new MappeUoProtocollanteBean();
		mappe.setMappaDestProtEntrataDefault(mappaDestProtEntrataDefault);
		mappe.setMappaUoRegistrazione(mappaUoRegistrazione);
		mappe.setUoRegistrazioneValueMap(uoRegistrazioneValueMap);
		return mappe;
	}
	
}
