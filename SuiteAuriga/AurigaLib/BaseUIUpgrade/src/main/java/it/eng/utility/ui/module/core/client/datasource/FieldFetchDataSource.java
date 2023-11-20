/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.Date;
import java.util.HashMap;

import it.eng.utility.ui.module.layout.shared.bean.FilterBean;

import com.smartgwt.client.data.AdvancedCriteria;
import com.smartgwt.client.data.Criterion;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.types.FieldType;
import com.smartgwt.client.types.OperatorId;
import com.smartgwt.client.util.JSOHelper;
import com.smartgwt.client.widgets.form.FilterBuilder;

public class FieldFetchDataSource extends GWTRestDataSource{

	private FilterBuilder filter;
	
	public FieldFetchDataSource(String tableName){
		this("ListFieldDataSource", "name", tableName);
	}
	
	public FieldFetchDataSource(String serverid, String keyproperty, String tableName){
		super(serverid, keyproperty, FieldType.TEXT);
		setDataURL("rest/datasourceservice/all");		
		addParam("tableName", tableName);		
	}

	protected Object transformRequest(DSRequest dsRequest) {    	
		dsRequest.setUseSimpleHttp(true);
		dsRequest.setExportResults(true);
		dsRequest.setContentType("application/json; charset=utf-8");
		AdvancedCriteria lAdvancedCriteria = filter.getCriteria(true);
		Criterion[] lCriterions = lAdvancedCriteria.getCriteria();		
		if (lCriterions == null) return null;
		String lStringValue = "";
		boolean first = true;
		for (Criterion lCriterion : lCriterions){
			if (first) first = false;
			else lStringValue += ",";
			lStringValue += lCriterion.getFieldName();
		}
		if (lStringValue!=null){
			extraparam.put("selectedCriteria", lStringValue);
		}
		extraparam.put("token", new Date().toString());
		if(!extraparam.isEmpty()){
			if(dsRequest.getData() == null) {
				dsRequest.setData(new HashMap());
			}
			//Setto gli attributi			
			JSOHelper.setAttribute(dsRequest.getData(), "EXTRA_PARAM", extraparam);			
		}				
		return super.transformRequest(dsRequest); //è una stringa
	}
	
	public void setFilter(FilterBuilder filter) {
		this.filter = filter;
	}

	public FilterBuilder getFilter() {
		return filter;
	}
}
