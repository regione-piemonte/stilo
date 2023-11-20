/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import com.smartgwt.client.data.AdvancedCriteria;
import com.smartgwt.client.data.Criterion;
import com.smartgwt.client.data.DSCallback;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.types.FieldType;
import com.smartgwt.client.util.BooleanCallback;
import com.smartgwt.client.widgets.Button;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;

import it.eng.utility.ui.module.core.client.datasource.GWTRestDataSource;
import it.eng.utility.ui.module.layout.client.Layout;
import it.eng.utility.ui.module.layout.client.common.CustomDetail;
import it.eng.utility.ui.module.layout.client.common.CustomLayout;

/**
 * 
 * @author matzanin
 *
 */

public class RegoleCampionamentoLayout extends CustomLayout {
	
	protected Button applicaNuovaRegolaButton;

	public RegoleCampionamentoLayout() {
		
		super("regoleCampionamentoAtti", 
			  new GWTRestDataSource("RegoleCampionamentoAttiDataSource", "idRegola", FieldType.TEXT), 
			  new RegoleCampionamentoFilter("regoleCampionamentoAtti"),
			  new RegoleCampionamentoList("regoleCampionamentoAtti"), 
			  new CustomDetail("regoleCampionamentoAtti"), 
			  null,
			  null, 
			  null);
		
		applicaNuovaRegolaButton = new Button("Applica nuova regola");
		applicaNuovaRegolaButton.setIcon("buttons/new.png");
		applicaNuovaRegolaButton.setIconSize(16);
		applicaNuovaRegolaButton.setAutoFit(true);
		applicaNuovaRegolaButton.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				manageNewClick();
			}
		});
		
		filterButtons.addMember(applicaNuovaRegolaButton);
		
		multiselectButton.hide();
		newButton.hide();			
	}
	
	@Override
	public void manageNewClick() {
		list.deselectAllRecords();
		AdvancedCriteria criteria = filter.getCriteria(true);
		String idTipoAtto = null;
//		String codiceAtto = null;
		String flgCodiceAttoParticolare = null;
		String flgDeterminaAContrarre = null;
//		String idUoProponente = null;
		String rangeImporto = null;
		if (criteria != null && criteria.getCriteria() != null){
			for (Criterion lCriterion : criteria.getCriteria()) {	
				if("idTipoAtto".equals(lCriterion.getFieldName())) {     
					idTipoAtto = lCriterion.getValueAsString();
				} 
//				else if("codiceAtto".equals(lCriterion.getFieldName())) {    
//					codiceAtto = lCriterion.getValueAsString();
//				} 
				else if("flgCodiceAttoParticolare".equals(lCriterion.getFieldName())) {     
					flgCodiceAttoParticolare = lCriterion.getValueAsString();
				} 
				else if("flgDeterminaAContrarre".equals(lCriterion.getFieldName())) {     
					flgDeterminaAContrarre = lCriterion.getValueAsString();
				} 
//				else if("idUoProponente".equals(lCriterion.getFieldName())) {
//					idUoProponente = lCriterion.getValueAsString();
//				} 
				else if("rangeImporto".equals(lCriterion.getFieldName())) {     
					rangeImporto = lCriterion.getValueAsString();
				}
			}
		}	
		final Record nuovaRegolaRecord = new Record();
		nuovaRegolaRecord.setAttribute("idTipoAtto", idTipoAtto);
//		nuovaRegolaRecord.setAttribute("codiceAtto", codiceAtto);
		nuovaRegolaRecord.setAttribute("flgCodiceAttoParticolare", flgCodiceAttoParticolare);		
		nuovaRegolaRecord.setAttribute("flgDeterminaAContrarre", flgDeterminaAContrarre);
//		nuovaRegolaRecord.setAttribute("idUoProponente", idUoProponente);
		nuovaRegolaRecord.setAttribute("rangeImporto", rangeImporto);
		GWTRestDataSource lRegoleCampionamentoAttiDataSource = new GWTRestDataSource("RegoleCampionamentoAttiDataSource");
		lRegoleCampionamentoAttiDataSource.addParam("isNuovaRegola", "true");
		Layout.showWaitPopup("Caricamento dati in corso...");
		lRegoleCampionamentoAttiDataSource.fetchData(criteria, new DSCallback() {

			@Override
			public void execute(DSResponse response, Object rawData, DSRequest request) {
				Layout.hideWaitPopup();
				if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
					nuovaRegolaRecord.setAttribute("listaGruppiAttiCampionamento", response.getDataAsRecordList());
					NuovaRegolaCampionamentoPopup lNuovaRegolaCampionamentoPopup = new NuovaRegolaCampionamentoPopup(nuovaRegolaRecord, new BooleanCallback() {
						
						@Override
						public void execute(Boolean value) {
							if(value) {
								doSearch();
							}
						}
					});
					lNuovaRegolaCampionamentoPopup.show();
				}
			}
		});
	}
	
}
