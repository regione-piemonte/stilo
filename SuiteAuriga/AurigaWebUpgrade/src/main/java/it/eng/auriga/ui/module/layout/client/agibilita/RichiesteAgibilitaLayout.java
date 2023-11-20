/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.ArrayList;
import java.util.List;

import com.smartgwt.client.data.AdvancedCriteria;
import com.smartgwt.client.data.Criterion;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.types.FieldType;
import com.smartgwt.client.types.OperatorId;

import it.eng.utility.ui.module.core.client.datasource.GWTRestDataSource;
import it.eng.utility.ui.module.layout.client.common.CustomDetail;
import it.eng.utility.ui.module.layout.client.common.CustomLayout;

public class RichiesteAgibilitaLayout extends CustomLayout {

	public RichiesteAgibilitaLayout() {

		super("richieste_agib", getDataSource(), new RichiesteAgibilitaFilter("richieste_agib", null),
				new RichiesteAgibilitaList("richieste_agib"), new CustomDetail(""));

		newButton.hide();
		setMultiselect(false);
	}

	@Override
	public boolean showMaxRecordVisualizzabiliItem() {
		return true;
	}
	
	@Override
	protected GWTRestDataSource createNroRecordDatasource() {

		GWTRestDataSource referenceDatasource = new GWTRestDataSource("RichiesteAgibilitaDataSource", "idRichiesta", FieldType.TEXT);
		// permette l'interazione utente anche durante l'elaborazione lato server
		referenceDatasource.setForceToShowPrompt(false);

		return referenceDatasource;
	}

	private static GWTRestDataSource getDataSource() {
		GWTRestDataSource datasource = new GWTRestDataSource("RichiesteAgibilitaDataSource", "idRichiesta", FieldType.TEXT);
		return datasource;
	}


	@Override
	public AdvancedCriteria buildSearchCriteria(AdvancedCriteria criteria) {

		List<Criterion> criterionList = new ArrayList<Criterion>();
		if (criteria == null) {
			criteria = new AdvancedCriteria();
		} else {
			for (Criterion crit : criteria.getCriteria()) {
				criterionList.add(crit);
			}
		}
		Criterion[] criterias = new Criterion[criterionList.size()];
		for (int i = 0; i < criterionList.size(); i++) {
			criterias[i] = criterionList.get(i);
		}
		return super.buildSearchCriteria(new AdvancedCriteria(OperatorId.AND, criterias));
	}


	@Override
	protected Record[] extractRecords(String[] fields) {
		// Se sono in overflow i dati verranno recuperati con il metodo asincrono,
		// altrimenti utilizzo quelli nella lista a GUI
		if (overflow){
			return new Record[0];
		}else{
			return super.extractRecords(fields);
		}
	}
}
