/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.StringReader;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import it.eng.auriga.database.store.dmpk_load_combo.bean.DmpkLoadComboDmfn_load_comboBean;
import it.eng.auriga.database.store.result.bean.StoreResultBean;
import it.eng.client.DmpkLoadComboDmfn_load_combo;
import it.eng.jaxb.context.SingletonJAXBContext;
import it.eng.jaxb.variabili.Lista;
import it.eng.utility.XmlUtility;
import it.eng.utility.ui.module.core.server.bean.AdvancedCriteria;
import it.eng.utility.ui.module.core.server.bean.OrderByBean;
import it.eng.utility.ui.module.core.server.bean.PaginatorBean;
import it.eng.utility.ui.module.core.server.datasource.AbstractFetchDataSource;
import it.eng.utility.ui.module.core.server.datasource.annotation.Datasource;
import it.eng.utility.ui.module.layout.shared.bean.SimpleKeyValueBean;
import it.eng.utility.ui.user.AurigaUserUtil;

/**
 * 
 * @author DANCRIST
 *
 */
@Datasource(id="LoadComboPreferitiAssegnazioneCondivisioneDataSource")
public class LoadComboPreferitiAssegnazioneCondivisioneDataSource extends AbstractFetchDataSource<SimpleKeyValueBean> {
	
	private static Logger mLogger = Logger.getLogger(LoadComboPreferitiAssegnazioneCondivisioneDataSource.class);

	@Override
	public PaginatorBean<SimpleKeyValueBean> fetch(AdvancedCriteria criteria,
			Integer startRow, Integer endRow, List<OrderByBean> orderby)
			throws Exception {
		
		String tipoAssegnatari = StringUtils.isNotBlank(getExtraparams().get("tipoAssegnatari")) ? getExtraparams().get("tipoAssegnatari") : "UO;SV";
		String finalita = StringUtils.isNotBlank(getExtraparams().get("finalita")) ? getExtraparams().get("finalita") : "";
		String idEmail = StringUtils.isNotBlank(getExtraparams().get("idEmail")) ? getExtraparams().get("idEmail") : "";
				
		PaginatorBean<SimpleKeyValueBean> lPaginatorBean = new PaginatorBean<SimpleKeyValueBean>();
		List<SimpleKeyValueBean> data = new ArrayList<SimpleKeyValueBean>();
		
		String idUserLavoro = AurigaUserUtil.getLoginInfo(getSession()).getIdUserLavoro() != null
				? AurigaUserUtil.getLoginInfo(getSession()).getIdUserLavoro() : "";
	
		DmpkLoadComboDmfn_load_comboBean lDmpkLoadComboDmfn_load_comboBean = new DmpkLoadComboDmfn_load_comboBean();
		// Inizializzo l'INPUT
		DmpkLoadComboDmfn_load_combo lDmpkLoadComboDmfn_load_combo = new DmpkLoadComboDmfn_load_combo();
		lDmpkLoadComboDmfn_load_comboBean.setTipocomboin("DEST_PREFERITI");
		String altriParametri = "ID_USER_LAVORO|*|" + idUserLavoro + "|*|FINALITA|*|" + finalita;
		if(StringUtils.isNotBlank(idEmail)) {
			altriParametri += "|*|ID_EMAIL|*|" + idEmail; // nella protocollazione mail in entrata
		}
		lDmpkLoadComboDmfn_load_comboBean.setAltriparametriin(altriParametri);
		lDmpkLoadComboDmfn_load_comboBean.setFlgsolovldin(BigDecimal.ONE);
		StoreResultBean<DmpkLoadComboDmfn_load_comboBean> output = lDmpkLoadComboDmfn_load_combo .execute(getLocale(), AurigaUserUtil.getLoginInfo(getSession()),
						lDmpkLoadComboDmfn_load_comboBean);
		if (StringUtils.isBlank(output.getDefaultMessage())) {
			StringReader sr = new StringReader(output.getResultBean().getListaxmlout());
			Lista lista = (Lista) SingletonJAXBContext.getInstance().createUnmarshaller().unmarshal(sr);
			if (lista != null) {
				for (int i = 0; i < lista.getRiga().size(); i++) {
					Vector<String> v = new XmlUtility().getValoriRiga(lista.getRiga().get(i));
					SimpleKeyValueBean lSimpleKeyValueBean = new SimpleKeyValueBean();					
					lSimpleKeyValueBean.setKey(v.get(0) + v.get(1)); // FLGUOSVUT + IDUOSVUT
					lSimpleKeyValueBean.setValue(v.get(2)); // 
					lSimpleKeyValueBean.setDisplayValue(v.get(2)); // VALORE	
					if(tipoAssegnatari.equals("UO;SV") || tipoAssegnatari.equalsIgnoreCase(v.get(0))) {
						data.add(lSimpleKeyValueBean);
					}
				}
			}
		} else {
			mLogger.error("Errore LoadComboPreferitiAssegnaDocDataSource:" + output.getDefaultMessage());
			throw new Exception(output.getDefaultMessage());
		}
		
		lPaginatorBean.setData(data);
		lPaginatorBean.setStartRow(0);
		lPaginatorBean.setEndRow(4);
		lPaginatorBean.setTotalRows(4);			
		
		return lPaginatorBean;
	}
}