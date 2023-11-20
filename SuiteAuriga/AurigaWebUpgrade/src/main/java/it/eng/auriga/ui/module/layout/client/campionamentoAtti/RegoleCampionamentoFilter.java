/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.ArrayList;
import java.util.Date;

import com.smartgwt.client.data.DataSourceField;
import com.smartgwt.client.types.DateDisplayFormat;
import com.smartgwt.client.types.FieldType;
import com.smartgwt.client.types.OperatorId;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.FormItemHoverFormatter;
import com.smartgwt.client.widgets.form.fields.FormItem;
import com.smartgwt.client.widgets.form.fields.SelectItem;
import com.smartgwt.client.widgets.form.fields.TextItem;

import it.eng.utility.ui.module.core.client.datasource.GWTRestDataSource;
import it.eng.utility.ui.module.layout.client.common.ConfigurableFilter;
import it.eng.utility.ui.module.layout.client.common.filter.DataSenzaOra;
import it.eng.utility.ui.module.layout.client.common.filter.ListaScelta;
import it.eng.utility.ui.module.layout.client.common.filter.StringaRicercaEstesaCaseInsensitive1;
import it.eng.utility.ui.module.layout.client.common.items.FilterDateItem;
import it.eng.utility.ui.module.layout.shared.bean.FilterFieldBean;
import it.eng.utility.ui.module.layout.shared.util.FrontendUtil;

public class RegoleCampionamentoFilter extends ConfigurableFilter {

	public RegoleCampionamentoFilter(String lista) {
		super(lista, null);
	}
	
	@Override
	protected DataSourceField buildField(FilterFieldBean lFilterFieldBean) {
		DataSourceField lDataSourceField = null;
		switch (lFilterFieldBean.getName()) {
		case "dataRiferimento":
			lDataSourceField = new DataSenzaOra(lFilterFieldBean.getName(), FrontendUtil.getRequiredFormItemTitle(lFilterFieldBean.getTitle()));	
			FilterDateItem filterDateItem = new FilterDateItem();
			filterDateItem.setDateFormatter(DateDisplayFormat.TOEUROPEANSHORTDATE);
			filterDateItem.setDefaultValue(new Date());
			lDataSourceField.setEditorType(filterDateItem);
			lDataSourceField.setRequired(true);
			break;
		case "idTipoAtto":
			lDataSourceField = new ListaScelta(lFilterFieldBean.getName(), lFilterFieldBean.getTitle());
			GWTRestDataSource lSelectItemTipologiaAttoDataSource = new GWTRestDataSource("LoadComboRicercaTipiAttiDataSource", "idTipoDocumento", FieldType.TEXT, true);
			it.eng.utility.ui.module.layout.client.common.items.SelectItem lSelectItemTipologiaAtto = new it.eng.utility.ui.module.layout.client.common.items.SelectItem(lFilterFieldBean.getName(), lFilterFieldBean.getTitle());
			lSelectItemTipologiaAtto.setWidth(400);
			lSelectItemTipologiaAtto.setPickListWidth(450);
			lSelectItemTipologiaAtto.setClearable(true);				
			lSelectItemTipologiaAtto.setOptionDataSource(lSelectItemTipologiaAttoDataSource);					
			lSelectItemTipologiaAtto.setDisplayField("descTipoDocumento");
			lSelectItemTipologiaAtto.setValueField("idTipoDocumento");	
			lDataSourceField.setEditorType(lSelectItemTipologiaAtto);
			lDataSourceField.setFilterEditorType(SelectItem.class);						
			break;
//		case "codiceAtto":
//			lDataSourceField = new ListaScelta(lFilterFieldBean.getName(), lFilterFieldBean.getTitle());
//			GWTRestDataSource lSelectItemCodiceAttoDataSource = new GWTRestDataSource("LoadComboValoriDizionarioMultiFilterDataSource", "key", FieldType.TEXT);
//			lSelectItemCodiceAttoDataSource.addParam("dictionaryEntry", "MATERIA_NATURA_ATTO");
//			it.eng.utility.ui.module.layout.client.common.items.SelectItem lSelectItemCodiceAtto = new it.eng.utility.ui.module.layout.client.common.items.SelectItem(lFilterFieldBean.getName(), lFilterFieldBean.getTitle());
//			lSelectItemCodiceAtto.setWidth(800);
//			lSelectItemCodiceAtto.setHoverWidth(500);
//			lSelectItemCodiceAtto.setItemHoverFormatter(new FormItemHoverFormatter() {
//
//				@Override
//				public String getHoverHTML(FormItem item, DynamicForm form) {
//					String itemValue = ((SelectItem) item).getValueAsString();
//					String[] valori = itemValue != null ? itemValue.split(",") : new String[0];
//					String hover = null;
//					for(int i = 0; i < valori.length; i++) {
//						if(hover == null) {
//							hover = "";
//						} else {
//							hover += "<br/>";
//						}
//						hover += item.getDisplayValue(valori[i]);
//					}
//					return hover;
//				}
//			});
//			lSelectItemCodiceAtto.setClearable(true);
//			lSelectItemCodiceAtto.setOptionDataSource(lSelectItemCodiceAttoDataSource);					
//			lSelectItemCodiceAtto.setDisplayField("value");
//			lSelectItemCodiceAtto.setValueField("key");	
//			lDataSourceField.setEditorType(lSelectItemCodiceAtto);
//			lDataSourceField.setFilterEditorType(SelectItem.class);	
//			lDataSourceField.setMultiple(true);
//			break;
		case "flgCodiceAttoParticolare":
			lDataSourceField = new ListaScelta(lFilterFieldBean.getName(), lFilterFieldBean.getTitle());
			it.eng.utility.ui.module.layout.client.common.items.SelectItem lSelectItemCodiceAttoParticolare = new it.eng.utility.ui.module.layout.client.common.items.SelectItem(lFilterFieldBean.getName(), lFilterFieldBean.getTitle());
			lSelectItemCodiceAttoParticolare.setWidth(200);
			lSelectItemCodiceAttoParticolare.setAllowEmptyValue(true);
			lSelectItemCodiceAttoParticolare.setValueMap("Si","No");
			lDataSourceField.setEditorType(lSelectItemCodiceAttoParticolare);
			lDataSourceField.setFilterEditorType(SelectItem.class);	
			break;
		case "flgDeterminaAContrarre":
			lDataSourceField = new ListaScelta(lFilterFieldBean.getName(), lFilterFieldBean.getTitle());
			it.eng.utility.ui.module.layout.client.common.items.SelectItem lSelectItemDeterminaAContrarre = new it.eng.utility.ui.module.layout.client.common.items.SelectItem(lFilterFieldBean.getName(), lFilterFieldBean.getTitle());
			lSelectItemDeterminaAContrarre.setWidth(200);
			lSelectItemDeterminaAContrarre.setAllowEmptyValue(true);
			lSelectItemDeterminaAContrarre.setValueMap("Si","No");
			lDataSourceField.setEditorType(lSelectItemDeterminaAContrarre);
			lDataSourceField.setFilterEditorType(SelectItem.class);	
			break;
//		case "idUoProponente":
//			lDataSourceField = buildListaSceltaOrganigramma(lFilterFieldBean.getTitle(), lFilterFieldBean, lFilterFieldBean.getSelect() != null ? lFilterFieldBean.getSelect().getCustomProperties() : null);
//			lDataSourceField.setMultiple(false);
//			break;
		case "rangeImporto":
			lDataSourceField = new ListaScelta(lFilterFieldBean.getName(), lFilterFieldBean.getTitle());
			GWTRestDataSource lSelectItemImportoDataSource = new GWTRestDataSource("LoadComboValoriDizionarioFilterDataSource", "key", FieldType.TEXT);
			lSelectItemImportoDataSource.addParam("dictionaryEntry", "RANGE_IMPORTI_X_CAMPIONAMENTO");
			it.eng.utility.ui.module.layout.client.common.items.SelectItem lSelectItemImporto = new it.eng.utility.ui.module.layout.client.common.items.SelectItem(lFilterFieldBean.getName(), lFilterFieldBean.getTitle());
			lSelectItemImporto.setWidth(200);
			lSelectItemImporto.setAllowEmptyValue(true);				
			lSelectItemImporto.setOptionDataSource(lSelectItemImportoDataSource);					
			lSelectItemImporto.setDisplayField("value");
			lSelectItemImporto.setValueField("key");	
			lDataSourceField.setEditorType(lSelectItemImporto);
			lDataSourceField.setFilterEditorType(SelectItem.class);	
			break;
		case "percentualeCampionamento":
			lDataSourceField = new ListaScelta(lFilterFieldBean.getName(), lFilterFieldBean.getTitle());
			it.eng.utility.ui.module.layout.client.common.items.SelectItem lSelectItemPercentualeCampionamento = new it.eng.utility.ui.module.layout.client.common.items.SelectItem(lFilterFieldBean.getName(), lFilterFieldBean.getTitle());
			lSelectItemPercentualeCampionamento.setWidth(200);
			ArrayList<String> lListDa0A99 = new ArrayList<String>();
			lListDa0A99.add("assente");
			lListDa0A99.add("presente");
			for(int i = 0; i < 100; i++) {
				lListDa0A99.add("" + i);
			}
			lSelectItemPercentualeCampionamento.setValueMap(lListDa0A99.toArray(new String[lListDa0A99.size()]));
			lDataSourceField.setEditorType(lSelectItemPercentualeCampionamento);
			lDataSourceField.setFilterEditorType(SelectItem.class);	
			break;
		case "idRegola":
			lDataSourceField = new StringaRicercaEstesaCaseInsensitive1(lFilterFieldBean.getName(), lFilterFieldBean.getTitle());
			TextItem lTextItemIdRegola = new TextItem();
			lTextItemIdRegola.setWidth(200);
			lDataSourceField.setEditorType(lTextItemIdRegola);			
			break;				
		}
		lDataSourceField.setValidOperators(OperatorId.EQUALS);
		return lDataSourceField;
	}
	
}
