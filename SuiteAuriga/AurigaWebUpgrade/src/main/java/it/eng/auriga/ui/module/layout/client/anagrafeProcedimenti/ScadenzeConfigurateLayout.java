/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.ArrayList;
import java.util.List;

import it.eng.utility.ui.module.core.client.datasource.GWTRestDataSource;
import it.eng.utility.ui.module.layout.client.common.ConfigurableFilter;
import it.eng.utility.ui.module.layout.client.common.CustomLayout;

import com.smartgwt.client.data.AdvancedCriteria;
import com.smartgwt.client.data.Criterion;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.types.OperatorId;

public class ScadenzeConfigurateLayout extends CustomLayout {

	private String idProcessType;
	
	public ScadenzeConfigurateLayout(GWTRestDataSource pGWTRestDataSource, String idProcessType) {
		super("scadenze_configurate", 
				pGWTRestDataSource,
				new ConfigurableFilter("scadenze_configurate"),
				new ScadenzeConfigurateList("scadenze_configurate"),
				new ScadenzeConfigurateDetail("scadenze_configurate",idProcessType)
				);
		
		this.idProcessType = idProcessType;		
	}

	@Override
	public AdvancedCriteria buildSearchCriteria(AdvancedCriteria criteria) {
		List<Criterion> criterionList = new ArrayList<Criterion>();
		if(criteria == null) {
			criteria = new AdvancedCriteria();
		} else {
			for(Criterion crit : criteria.getCriteria()) {
				criterionList.add(crit);
			}
		}
		if(idProcessType != null && !idProcessType.equals("")) {
			criterionList.add(new Criterion("idProcessTypeIO", OperatorId.EQUALS, idProcessType));
		}						
		Criterion[] criterias = new Criterion[criterionList.size()];
		for(int i = 0; i < criterionList.size(); i++) {
			criterias[i] = criterionList.get(i);
		}
		return super.buildSearchCriteria(new AdvancedCriteria(OperatorId.AND, criterias));
	}		
	
	@Override
	public String getNewDetailTitle() {
		return "Nuova scadenza";
	}

	@Override
	public String getEditDetailTitle() {
		Record record = new Record(detail.getValuesManager().getValues());
		return "Modifica scadenza " + (getTipoEstremiRecord(record));		
	}

	@Override
	public String getViewDetailTitle() {
		Record record = new Record(detail.getValuesManager().getValues());
		return "Dettaglio scadenza " + (getTipoEstremiRecord(record));		
	}

	public String getTipoEstremiRecord(Record record) {		
		return record.getAttribute("descrizioneScadenze");
	}	
	
	@Override
	public void newMode() {
		super.newMode();
		deleteButton.hide();
		altreOpButton.hide();
	}

	@Override
	public void viewMode() {
		super.viewMode();
		deleteButton.hide();
		altreOpButton.hide();
	}

	@Override
	public void editMode(boolean fromViewMode) {
		super.editMode(fromViewMode);
		deleteButton.hide();
		altreOpButton.hide();
	}

	public String getIdProcessType() {
		return idProcessType;
	}

}