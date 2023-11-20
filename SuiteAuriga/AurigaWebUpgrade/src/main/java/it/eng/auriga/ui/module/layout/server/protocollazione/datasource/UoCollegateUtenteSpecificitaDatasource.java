/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import it.eng.auriga.database.store.dmpk_load_combo.bean.DmpkLoadComboDmfn_load_comboBean;
import it.eng.auriga.database.store.result.bean.StoreResultBean;
import it.eng.auriga.exception.StoreException;
import it.eng.auriga.module.business.beans.AurigaLoginBean;
import it.eng.auriga.ui.module.layout.server.protocollazione.datasource.bean.ListaUoProtocollanteSpecificitaBean;
import it.eng.auriga.ui.module.layout.server.protocollazione.datasource.bean.UoProtocollanteBean;
import it.eng.client.DmpkLoadComboDmfn_load_combo;
import it.eng.utility.ui.module.core.server.datasource.AbstractServiceDataSource;
import it.eng.utility.ui.module.core.server.datasource.annotation.Datasource;
import it.eng.utility.ui.user.AurigaUserUtil;
import it.eng.xml.XmlListaUtility;

@Datasource(id = "UoCollegateUtenteSpecificitaDatasource")
public class UoCollegateUtenteSpecificitaDatasource extends AbstractServiceDataSource<ListaUoProtocollanteSpecificitaBean, ListaUoProtocollanteSpecificitaBean> {

	private static final Logger log = Logger.getLogger(UoCollegateUtenteSpecificitaDatasource.class);

	@Override
	public ListaUoProtocollanteSpecificitaBean call(ListaUoProtocollanteSpecificitaBean lListaUoProtocollanteSpecificitaBean) throws Exception {
		AurigaLoginBean lAurigaLoginBean = AurigaUserUtil.getLoginInfo(getSession());
		String idUserLavoro = lAurigaLoginBean.getIdUserLavoro() != null ? lAurigaLoginBean.getIdUserLavoro() : "";
		
		DmpkLoadComboDmfn_load_comboBean bean;
		DmpkLoadComboDmfn_load_combo store;
		String altriParametri;
		String lStrXml;
		StoreResultBean<DmpkLoadComboDmfn_load_comboBean> storeResult;
		
		Map<String, String> mappaUoCollegateUtenteRegistrazioneE = new LinkedHashMap<String, String>();
		Map<String, String> mappaUoCollegateUtenteRegistrazioneUI = new LinkedHashMap<String, String>();
		Map<String, String> mappaUoCollegateUtenteAvviaIterAtto = new LinkedHashMap<String, String>();
		Map<String, String> mappaUoCollegateUtenteSceltaUOLavoro = new LinkedHashMap<String, String>();
		Map<String, UoProtocollanteBean> mappaUoCollegateUtenteSceltaUOLavoroValueObject = new LinkedHashMap<String, UoProtocollanteBean>();
		
		// SPECIFICITA= REGISTRAZIONE_E
		bean = new DmpkLoadComboDmfn_load_comboBean();
		bean.setCodidconnectiontokenin(lAurigaLoginBean.getToken());
		bean.setTipocomboin("SOGG_INT_COLLEGATI_UTENTE");
		altriParametri = "TIPI_SOGG_INT|*|UO|*|FLG_ANCHE_CON_DELEGA|*|1|*|ID_USER_LAVORO|*|" + idUserLavoro;
		altriParametri += "|*|FLG_INCL_SOTTOUO_SV|*|1";
		altriParametri += "|*|SPECIFICITA|*|REGISTRAZIONE_E";
//		if(isSelezioneUoLavoro) altriParametri += "|*|FLG_ANCHE_REL_TIPO_L|*|1"; // andrebbe passato solo in caso di selezione della UO di lavoro, ma per il momento lo lasciamo commentato
		bean.setAltriparametriin(altriParametri);
		store = new DmpkLoadComboDmfn_load_combo();
		storeResult = store.execute(getLocale(), lAurigaLoginBean, bean);
		if (storeResult.isInError()) {
			throw new StoreException(storeResult);
		}
		bean = storeResult.getResultBean();
		lStrXml = bean.getListaxmlout();
		List<UoProtocollanteBean> listaUORegistrazioneE = XmlListaUtility.recuperaLista(lStrXml, UoProtocollanteBean.class);
		for(UoProtocollanteBean lUoProtocollanteBean : listaUORegistrazioneE) {
			lUoProtocollanteBean.setFlgUoRegistrazione("1");
			lUoProtocollanteBean.setFlgUoProponenteAtti("1");
			mappaUoCollegateUtenteRegistrazioneE.put(lUoProtocollanteBean.getIdUo(), lUoProtocollanteBean.getDescrizione());
		}
		
		// SPECIFICITA= REGISTRAZIONE_UI
		bean = new DmpkLoadComboDmfn_load_comboBean();
		bean.setCodidconnectiontokenin(lAurigaLoginBean.getToken());
		bean.setTipocomboin("SOGG_INT_COLLEGATI_UTENTE");
		altriParametri = "TIPI_SOGG_INT|*|UO|*|FLG_ANCHE_CON_DELEGA|*|1|*|ID_USER_LAVORO|*|" + idUserLavoro;
		altriParametri += "|*|FLG_INCL_SOTTOUO_SV|*|1";
		altriParametri += "|*|SPECIFICITA|*|REGISTRAZIONE_UI";
//		if(isSelezioneUoLavoro) altriParametri += "|*|FLG_ANCHE_REL_TIPO_L|*|1"; // andrebbe passato solo in caso di selezione della UO di lavoro, ma per il momento lo lasciamo commentato
		bean.setAltriparametriin(altriParametri);
		store = new DmpkLoadComboDmfn_load_combo();
		storeResult = store.execute(getLocale(), lAurigaLoginBean, bean);
		if (storeResult.isInError()) {
			throw new StoreException(storeResult);
		}
		bean = storeResult.getResultBean();
		lStrXml = bean.getListaxmlout();
		List<UoProtocollanteBean> listaUORegistrazioneUI = XmlListaUtility.recuperaLista(lStrXml, UoProtocollanteBean.class);
		for(UoProtocollanteBean lUoProtocollanteBean : listaUORegistrazioneUI) {
			lUoProtocollanteBean.setFlgUoRegistrazione("1");
			lUoProtocollanteBean.setFlgUoProponenteAtti("1");
			mappaUoCollegateUtenteRegistrazioneUI.put(lUoProtocollanteBean.getIdUo(), lUoProtocollanteBean.getDescrizione());
		}
		
		// SPECIFICITA= AVVIO_ITER_ATTO
		bean = new DmpkLoadComboDmfn_load_comboBean();
		bean.setCodidconnectiontokenin(lAurigaLoginBean.getToken());
		bean.setTipocomboin("SOGG_INT_COLLEGATI_UTENTE");
		altriParametri = "TIPI_SOGG_INT|*|UO|*|FLG_ANCHE_CON_DELEGA|*|1|*|ID_USER_LAVORO|*|" + idUserLavoro;
		altriParametri += "|*|FLG_INCL_SOTTOUO_SV|*|1";
		altriParametri += "|*|SPECIFICITA|*|AVVIO_ITER_ATTO";
//		if(isSelezioneUoLavoro) altriParametri += "|*|FLG_ANCHE_REL_TIPO_L|*|1"; // andrebbe passato solo in caso di selezione della UO di lavoro, ma per il momento lo lasciamo commentato
		bean.setAltriparametriin(altriParametri);
		store = new DmpkLoadComboDmfn_load_combo();
		storeResult = store.execute(getLocale(), lAurigaLoginBean, bean);
		if (storeResult.isInError()) {
			throw new StoreException(storeResult);
		}
		bean = storeResult.getResultBean();
		lStrXml = bean.getListaxmlout();
		List<UoProtocollanteBean> listaUOAvvioIterAtto = XmlListaUtility.recuperaLista(lStrXml, UoProtocollanteBean.class);
		for(UoProtocollanteBean lUoProtocollanteBean : listaUOAvvioIterAtto) {
			lUoProtocollanteBean.setFlgUoRegistrazione("1");
			lUoProtocollanteBean.setFlgUoProponenteAtti("1");
			mappaUoCollegateUtenteAvviaIterAtto.put(lUoProtocollanteBean.getIdUo(), lUoProtocollanteBean.getDescrizione());
		}
		
		// SPECIFICITA= SCELTA_UO_LAVORO
		bean = new DmpkLoadComboDmfn_load_comboBean();
		bean.setCodidconnectiontokenin(lAurigaLoginBean.getToken());
		bean.setTipocomboin("SOGG_INT_COLLEGATI_UTENTE");
		altriParametri = "TIPI_SOGG_INT|*|UO|*|FLG_ANCHE_CON_DELEGA|*|1|*|ID_USER_LAVORO|*|" + idUserLavoro;
		altriParametri += "|*|FLG_INCL_SOTTOUO_SV|*|1";
		altriParametri += "|*|SPECIFICITA|*|SCELTA_UO_LAVORO";
//		if(isSelezioneUoLavoro) altriParametri += "|*|FLG_ANCHE_REL_TIPO_L|*|1"; // andrebbe passato solo in caso di selezione della UO di lavoro, ma per il momento lo lasciamo commentato
		bean.setAltriparametriin(altriParametri);
		store = new DmpkLoadComboDmfn_load_combo();
		storeResult = store.execute(getLocale(), lAurigaLoginBean, bean);
		if (storeResult.isInError()) {
			throw new StoreException(storeResult);
		}
		bean = storeResult.getResultBean();
		lStrXml = bean.getListaxmlout();
		List<UoProtocollanteBean> listaUOSceltaUOLavoro = XmlListaUtility.recuperaLista(lStrXml, UoProtocollanteBean.class);
		for(UoProtocollanteBean lUoProtocollanteBean : listaUOSceltaUOLavoro) {
			lUoProtocollanteBean.setFlgUoRegistrazione("1");
			lUoProtocollanteBean.setFlgUoProponenteAtti("1");
			mappaUoCollegateUtenteSceltaUOLavoro.put(lUoProtocollanteBean.getIdUo(), lUoProtocollanteBean.getDescrizione());
			mappaUoCollegateUtenteSceltaUOLavoroValueObject.put(lUoProtocollanteBean.getIdUo(), lUoProtocollanteBean);
		}
		
		ListaUoProtocollanteSpecificitaBean result = new ListaUoProtocollanteSpecificitaBean();
//		result.setUoCollegateUtenteRegistrazioneE(listaUORegistrazioneE);
//		result.setUoCollegateUtenteRegistrazioneUI(listaUORegistrazioneUI);
//		result.setUoCollegateUtenteAvviaIterAtto(listaUOAvvioIterAtto);
//		result.setUoCollegateUtenteSceltaUOLavoro(listaUOSceltaUOLavoro);
		result.setMappaUoCollegateUtenteRegistrazioneE(mappaUoCollegateUtenteRegistrazioneE);
		result.setMappaUoCollegateUtenteRegistrazioneUI(mappaUoCollegateUtenteRegistrazioneUI);
		result.setMappaUoCollegateUtenteAvviaIterAtto(mappaUoCollegateUtenteAvviaIterAtto);
		result.setMappaUoCollegateUtenteSceltaUOLavoro(mappaUoCollegateUtenteSceltaUOLavoro);
		result.setMappaUoCollegateUtenteSceltaUOLavoroValueObject(mappaUoCollegateUtenteSceltaUOLavoroValueObject);
		return result;
				
	}
	
}