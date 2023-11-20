/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.auriga.ui.module.layout.client.attributiCustom.AttributiCustomLayout;
import it.eng.utility.ui.module.layout.client.portal.ModalWindow;

import java.util.LinkedHashMap;

import com.smartgwt.client.data.AdvancedCriteria;
import com.smartgwt.client.data.Criterion;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.types.OperatorId;

public abstract class LookupAttributiCustomPopup extends ModalWindow{

	private LookupAttributiCustomPopup _window;

	private AttributiCustomLayout portletLayout;

	public LookupAttributiCustomPopup(final Record filterValues, final String nomeAttributo,String finalita,boolean flgSelezioneSingola) {

		super("attributi_custom", true);

		setTitle("Attributi custom");  

		_window = this;

		if(filterValues != null) {
			settingsMenu.removeItem(separatorMenuItem);
			settingsMenu.removeItem(autoSearchMenuItem);
		}
		
		portletLayout = new AttributiCustomLayout(finalita, flgSelezioneSingola) {
			@Override
			public void lookupBack(Record selectedRecord) {
				manageLookupBack(selectedRecord);
				_window.markForDestroy();
			}
			
			@Override
			public void multiLookupBack(Record record) {
				manageMultiLookupBack(record);
				
			}
			
			@Override
			public void multiLookupUndo(Record record) {
				manageMultiLookupUndo(record);
			}
			
			@Override
			public void showDetail() {
				super.showDetail();
				if(fullScreenDetail) {	
					String title = "";
					if(mode != null) {
						if(mode.equals("new")) {				
							title = getNewDetailTitle();
						} else if(mode.equals("edit")) {
							title = getEditDetailTitle();		
						} else if(mode.equals("view")) {
							title = getViewDetailTitle();
						}
					}
					_window.setTitle(title);											
				}
			}
			
			@Override
			public void hideDetail(boolean reloadList) {
				super.hideDetail(reloadList);
				if(fullScreenDetail) {			
					_window.setTitle("Attributi custom"); 
				} 	
			}
			
			@Override
			protected boolean isRecordSelezionabileForLookup(Record record) {
				String appartenenza = getNomeAttributoComplexAppartenenza();
				if(appartenenza != null && !"".equals(appartenenza)) {
					return record.getAttributeAsString("appartenenza") != null && appartenenza.equalsIgnoreCase(record.getAttributeAsString("appartenenza"));
				} 
				return super.isRecordSelezionabileForLookup(record);
			}
			
			@Override
			public void setCriteriaAndFirstSearch(AdvancedCriteria criteria, boolean autoSearch) {
				LinkedHashMap<String, Criterion> mapCriterion = new LinkedHashMap<String, Criterion>();		
				if(criteria != null && criteria.getCriteria() != null) {
					for (int i = 0; i < criteria.getCriteria().length; i++) {
						Criterion criterion = criteria.getCriteria()[i];
						mapCriterion.put(criterion.getFieldName(), criterion);
					}				
				}
				if(filterValues != null) {	
					if(filterValues.getAttribute("appartenenza") != null && !"".equals(filterValues.getAttribute("appartenenza"))) {
						mapCriterion.put("appartenenza", new Criterion("appartenenza", OperatorId.IEQUALS, filterValues.getAttribute("appartenenza")));
					}
					if(filterValues.getAttribute("tipo") != null && !"".equals(filterValues.getAttribute("tipo"))) {
						mapCriterion.put("tipo", new Criterion("tipo", OperatorId.EQUALS, filterValues.getAttribute("tipo")));
					}
				}				
				super.setCriteriaAndFirstSearch(new AdvancedCriteria(OperatorId.AND, mapCriterion.values().toArray(new Criterion[0])), (filterValues != null || autoSearch));
			}
			
			@Override
			public void setDefaultCriteriaAndFirstSearch(boolean autosearch) {
				if(filterValues != null) {					
					setCriteriaAndFirstSearch(new AdvancedCriteria(), autosearch);
				} else {
					super.setDefaultCriteriaAndFirstSearch(autosearch);
				}				
			}
			
		};
		
		portletLayout.setLookup(true);
		
		portletLayout.setHeight100();
		portletLayout.setWidth100();
		
		setBody(portletLayout);        
         
        setIcon("menu/attributi_custom.png");
	}
	
	public String getNomeAttributoComplexAppartenenza() {
		return null;
	}
	
	public abstract void manageLookupBack(Record record);	
	public abstract void manageMultiLookupBack(Record record);
	public abstract void manageMultiLookupUndo(Record record);


}