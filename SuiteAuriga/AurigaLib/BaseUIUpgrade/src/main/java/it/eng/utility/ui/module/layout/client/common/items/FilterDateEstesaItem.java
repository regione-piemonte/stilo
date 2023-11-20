/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.Date;
import java.util.Map;

import com.google.gwt.core.client.JavaScriptObject;
import com.smartgwt.client.types.OperatorId;

import it.eng.utility.ui.module.layout.client.Layout;
import it.eng.utility.ui.module.layout.shared.bean.GenericConfigBean;

public class FilterDateEstesaItem extends CustomItem {

	public FilterDateEstesaItem(JavaScriptObject jsObject){
		super(jsObject);
	}
	
	public FilterDateEstesaItem(){
		super();
	}
		
	public FilterDateEstesaItem(final Map<String, String> property){
		super(property);
	}
		
	@Override
	public CustomItem buildObject(JavaScriptObject jsObj) {
		
		CustomItem lCustomItem = new FilterDateEstesaItem(jsObj);
		return lCustomItem;
	}
	
	@Override
	public CustomItemFormField[] getFormFields() {

		GenericConfigBean config = Layout.getGenericConfig();
		CustomItemFormField data = new CustomItemFormField("data", this);
		DateItem lDateItem = new DateItem();
		//		lDateItem.setRequired(true);

		if(config!=null) {
			if(config.getMinAnno()!=null && !"".equals(config.getMinAnno())) {
				int minAnno = new Integer(config.getMinAnno()) - 1900;
				Date startDate = new Date(minAnno, 11, 31);
				lDateItem.setStartDate(startDate);
			}
			if(config.getMaxAnno()!=null && !"".equals(config.getMaxAnno())) {
				int maxAnno = new Integer(config.getMaxAnno()) - 1900;
				Date endDate = new Date(maxAnno, 11, 31);
				lDateItem.setEndDate(endDate);
			}
		}
		data.setEditorType(lDateItem);
		data.setHidden(getOperator() != null && getOperator().equals(OperatorId.LAST_N_DAYS));

		CustomItemFormField nGiorni = new CustomItemFormField("nGiorni", this);
		NumericItem lNumericItemNGiorni = new NumericItem(false);
		lNumericItemNGiorni.setWidth(80);
		lNumericItemNGiorni.setLength(5);
		nGiorni.setEditorType(lNumericItemNGiorni);
		nGiorni.setHidden(getOperator() != null && !getOperator().equals(OperatorId.LAST_N_DAYS));

		return new CustomItemFormField[]{data, nGiorni};
	}

	@Override
	public void setCanEdit(Boolean canEdit) {
		super.setCanEdit(canEdit);
		setTextBoxStyle(canEdit ? it.eng.utility.Styles.textItem : it.eng.utility.Styles.textItemReadonly);
    	setCanFocus(canEdit ? true : false);
	}
	    
}
