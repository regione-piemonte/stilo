/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import com.smartgwt.client.data.AdvancedCriteria;
import com.smartgwt.client.data.Criterion;
import com.smartgwt.client.data.DataSourceField;
import com.smartgwt.client.types.DateDisplayFormat;
import com.smartgwt.client.types.OperatorId;
import com.smartgwt.client.util.JSON;
import com.smartgwt.client.widgets.form.fields.SelectItem;

import it.eng.utility.ui.module.layout.client.common.ConfigurableFilter;
import it.eng.utility.ui.module.layout.client.common.filter.DataSenzaOra;
import it.eng.utility.ui.module.layout.client.common.filter.ListaScelta;
import it.eng.utility.ui.module.layout.client.common.filter.NumeroRicercaEsatta;
import it.eng.utility.ui.module.layout.client.common.items.FilterDateItem;
import it.eng.utility.ui.module.layout.client.common.items.NumericItem;
import it.eng.utility.ui.module.layout.shared.bean.FilterFieldBean;
import it.eng.utility.ui.module.layout.shared.util.FrontendUtil;

public class VerificaRaffinamentoCampioniFilter extends ConfigurableFilter {

	public VerificaRaffinamentoCampioniFilter(String lista) {
		super(lista, null);
		setCriteria(getDefaultCriteria());
	}
	
	public AdvancedCriteria getDefaultCriteria() {
		List<Criterion> listaCriterion = new ArrayList<Criterion>();
		listaCriterion.add(new Criterion("periodoVerifica", OperatorId.BETWEEN_INCLUSIVE));	
		listaCriterion.add(new Criterion("tipoRaggruppamento", OperatorId.EQUALS));
		return new AdvancedCriteria(OperatorId.AND, listaCriterion.toArray(new Criterion[listaCriterion.size()]));
	}
	
	@Override
	protected DataSourceField buildField(FilterFieldBean lFilterFieldBean) {
		DataSourceField lDataSourceField = null;
		switch (lFilterFieldBean.getName()) {
		case "periodoVerifica":
			lDataSourceField = new DataSenzaOra(lFilterFieldBean.getName(), FrontendUtil.getRequiredFormItemTitle(lFilterFieldBean.getTitle()));	
			FilterDateItem filterDateItem = new FilterDateItem();
			filterDateItem.setDateFormatter(DateDisplayFormat.TOEUROPEANSHORTDATE);
			lDataSourceField.setEditorType(filterDateItem);
			lDataSourceField.setRequired(true);
			lDataSourceField.setValidOperators(OperatorId.BETWEEN_INCLUSIVE);			
			break;
		case "tipoRaggruppamento":
			lDataSourceField = new ListaScelta(lFilterFieldBean.getName(), FrontendUtil.getRequiredFormItemTitle(lFilterFieldBean.getTitle()));
			it.eng.utility.ui.module.layout.client.common.items.SelectItem lSelectItemTipoRaggruppamento = new it.eng.utility.ui.module.layout.client.common.items.SelectItem(lFilterFieldBean.getName(), lFilterFieldBean.getTitle());
			lSelectItemTipoRaggruppamento.setWidth(200);
			LinkedHashMap<String, String> lTipoRaggruppamentoValueMap = new LinkedHashMap<String, String>();
			lTipoRaggruppamentoValueMap.put("CODICE_ATTO", "Cod. atto");	
			lTipoRaggruppamentoValueMap.put("STRUTTURA", "Struttura"); 
			lSelectItemTipoRaggruppamento.setValueMap(lTipoRaggruppamentoValueMap);
			lDataSourceField.setEditorType(lSelectItemTipoRaggruppamento);
			lDataSourceField.setFilterEditorType(SelectItem.class);	
			lDataSourceField.setRequired(true);
			lDataSourceField.setValidOperators(OperatorId.EQUALS);
			break;
		case "nroSogliaAttiAdottati":
			lDataSourceField = new NumeroRicercaEsatta(lFilterFieldBean.getName(), lFilterFieldBean.getTitle());	
			NumericItem lNumericItemIntero = new NumericItem(false);   
			lDataSourceField.setEditorType(lNumericItemIntero);
			lDataSourceField.setValidOperators(OperatorId.EQUALS);
			break;
		case "sogliaImporto":
			lDataSourceField = new NumeroRicercaEsatta(lFilterFieldBean.getName(), lFilterFieldBean.getTitle());			
			lDataSourceField.setValidOperators(OperatorId.EQUALS);
			break;			
		}
		return lDataSourceField;
	}
	
}
