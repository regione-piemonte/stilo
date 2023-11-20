/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.HashMap;
import java.util.Map;

import com.smartgwt.client.data.DataSourceField;

import it.eng.utility.ui.module.layout.client.common.ConfigurableFilter;
import it.eng.utility.ui.module.layout.client.common.filter.AttributiCustomDelTipo;
import it.eng.utility.ui.module.layout.shared.bean.FilterFieldBean;

public class PubblicazioneAlboRicercaPubblicazioniFilter extends ConfigurableFilter {

	public PubblicazioneAlboRicercaPubblicazioniFilter(String lista) {
		super(lista, null);
	}
	
	@Override
	protected DataSourceField buildField(FilterFieldBean lFilterFieldBean) {
		DataSourceField lDataSourceField = null;
		if("tipoDoc".equals(lFilterFieldBean.getName())) {
			Map<String, String> lMapPropertyAttributiCustomDelTipo = new HashMap<String, String>();
			lMapPropertyAttributiCustomDelTipo.put("nomeTabella", lFilterFieldBean.getNomeTabella());
			lMapPropertyAttributiCustomDelTipo.put("isSoloDaPubblicare", "true");					
			lDataSourceField = new AttributiCustomDelTipo(lFilterFieldBean.getName(), lFilterFieldBean.getTitle(), lMapPropertyAttributiCustomDelTipo);			
		} else {
			lDataSourceField = super.buildField(lFilterFieldBean);
		}		
		return lDataSourceField;
	}
	
}
