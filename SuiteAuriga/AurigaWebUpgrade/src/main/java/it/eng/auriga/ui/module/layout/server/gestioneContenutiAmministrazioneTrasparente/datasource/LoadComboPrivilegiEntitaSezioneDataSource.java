/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import it.eng.utility.MessageUtil;
import it.eng.utility.ui.module.core.server.bean.AdvancedCriteria;
import it.eng.utility.ui.module.core.server.bean.OrderByBean;
import it.eng.utility.ui.module.core.server.bean.PaginatorBean;
import it.eng.utility.ui.module.core.server.datasource.AbstractFetchDataSource;
import it.eng.utility.ui.module.core.server.datasource.annotation.Datasource;
import it.eng.utility.ui.module.layout.shared.bean.SimpleKeyValueBean;

/**
 * 
 * @author OTTAVIO PASSALACQUA
 *
 */

@Datasource(id="LoadComboPrivilegiEntitaSezioneDataSource")
public class LoadComboPrivilegiEntitaSezioneDataSource extends AbstractFetchDataSource<SimpleKeyValueBean> {
	
	private static Logger mLogger = Logger.getLogger(LoadComboPrivilegiEntitaSezioneDataSource.class);

	@Override
	public PaginatorBean<SimpleKeyValueBean> fetch(AdvancedCriteria criteria, Integer startRow, Integer endRow, List<OrderByBean> orderby) throws Exception {
				
		List<SimpleKeyValueBean> lListResult = new ArrayList<SimpleKeyValueBean>();
		
		// Inizializzo l'INPUT
		SimpleKeyValueBean listaPrivilegiValueMap1 = new SimpleKeyValueBean();
		listaPrivilegiValueMap1.setKey("P");
		listaPrivilegiValueMap1.setValue(MessageUtil.getValue(getLocale().getLanguage(), getSession(), "contenuti_amministrazione_trasparente_privilegi_entita_sezione_P_value"));
	
		
		SimpleKeyValueBean listaPrivilegiValueMap2 = new SimpleKeyValueBean();
		listaPrivilegiValueMap2.setKey("G");
		listaPrivilegiValueMap2.setValue(MessageUtil.getValue(getLocale().getLanguage(), getSession(), "contenuti_amministrazione_trasparente_privilegi_entita_sezione_G_value"));
		
		
		lListResult.add(listaPrivilegiValueMap1);
		lListResult.add(listaPrivilegiValueMap2);
		
		PaginatorBean<SimpleKeyValueBean> lPaginatorBean = new PaginatorBean<SimpleKeyValueBean>();
		lPaginatorBean.setData(lListResult);
		lPaginatorBean.setStartRow(0);
		lPaginatorBean.setEndRow(lListResult.size());
		lPaginatorBean.setTotalRows(lListResult.size());
		
		return lPaginatorBean;
	}
}