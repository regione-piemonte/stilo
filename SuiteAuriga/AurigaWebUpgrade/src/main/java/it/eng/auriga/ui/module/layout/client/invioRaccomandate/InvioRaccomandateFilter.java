/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.jasper.runtime.JspSourceDependent;

import com.google.gwt.core.client.JsDate;
import com.smartgwt.client.data.AdvancedCriteria;
import com.smartgwt.client.data.Criterion;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.data.RecordList;
import com.smartgwt.client.types.OperatorId;
import com.smartgwt.client.util.JSOHelper;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.form.FilterClause;
import com.smartgwt.client.widgets.form.SearchForm;
import com.smartgwt.client.widgets.form.fields.DateItem;

import it.eng.utility.ui.module.layout.client.common.ConfigurableFilter;
import it.eng.utility.ui.module.layout.client.common.items.FilterDateEstesaItem;

public class InvioRaccomandateFilter extends ConfigurableFilter{

	public InvioRaccomandateFilter(String lista) {
		super(lista);
	}

//	public List<String> getCriteriaPerRaccomandate() {
//		
//		List<String> criteri = new ArrayList<>();
//		if (filter.isVisible()) {
//			filter.hide();
//			filter.show();
//		}
//		for(Canvas member : getClauseStack().getMembers()) {
//			if(member instanceof FilterClause) {
//				SearchForm clauseForm = ((FilterClause) member).getClause();
//				String crit = "";
//				crit = crit.concat((String) clauseForm.getField("fieldName").getValue());
//				crit = crit.concat("|*|");
//				crit = crit.concat(findRelativeOperatorPerRaccomandate((String) clauseForm.getField("operator").getValue()));
//				crit = crit.concat("|*|");
//				if(clauseForm.getField("value") != null)
//					crit = crit.concat((String)clauseForm.getField("value").getValue());
//				if(clauseForm.getField("start") != null) {
//					Criterion criter = new Criterion();
//					criter.setAttribute("start", clauseForm.getField("start").getValue());
//					Map<String, String> lMapValues = (Map<String, String>) criter.getValues();
//	    			String data = lMapValues.get("data");
//				}
//				if(clauseForm.getField("end") != null)
//					crit = crit.concat("$"+(String)clauseForm.getField("end").getValue());
//				criteri.add(crit);
//			}
//		}
//		return criteri;
//	}
	
	@Override
	public AdvancedCriteria getCriteria() {
		if (filter.isVisible()) {
			filter.hide();
			filter.show();
		}
		List<Criterion> criterionList = new ArrayList<Criterion>();
		for(Canvas member : getClauseStack().getMembers()) {
			if(member instanceof FilterClause) {
				SearchForm clauseForm = ((FilterClause) member).getClause();
				Criterion crit = new Criterion();
				crit.setFieldName((String) clauseForm.getField("fieldName").getValue());
				crit.setOperator(findRelativeOperator((String) clauseForm.getField("operator").getValue()));
				if(clauseForm.getField("value") != null)
					crit.setAttribute("value", clauseForm.getField("value").getValue());
				if(clauseForm.getField("start") != null)
					crit.setAttribute("start", clauseForm.getField("start").getValue());
				if(clauseForm.getField("end") != null)
					crit.setAttribute("end", clauseForm.getField("end").getValue());
				if(crit.getAttribute("value") != null || crit.getAttribute("start") != null || crit.getAttribute("end") != null) {
					criterionList.add(crit);
				}
			}
		}
		AdvancedCriteria criteria = new AdvancedCriteria(OperatorId.AND, criterionList.toArray(new Criterion[criterionList.size()]));
		return criteria;
//		return super.getCriteria();
	}
	
//	protected String findRelativeOperatorPerRaccomandate(String value) {
//		for (OperatoriPostelId lOperatorId : OperatoriPostelId.values()) {
//			if (lOperatorId.getValue().toString().toLowerCase().equals(value.toLowerCase())) {
//				return lOperatorId.getValue().toString();
//			}
//		}
//		return OperatoriPostelId.EQUALS.getValue().toString();
//	}

	
}
