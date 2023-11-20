/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.smartgwt.client.data.AdvancedCriteria;
import com.smartgwt.client.data.Criterion;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.types.OperatorId;
import com.smartgwt.client.util.JSOHelper;

import it.eng.utility.ui.module.layout.client.Layout;
import it.eng.utility.ui.module.layout.client.portal.ModalWindow;

public class ArchivioPopup extends ModalWindow {
	
	private ArchivioPopup _window;
	private ArchivioLayout portletLayout;
	private String windowTitle;
	
	public ArchivioPopup(final Record filterValues, String pwindowTitle) {
		
		super("archivio", true);
		
		windowTitle = pwindowTitle;
		
		setTitle(windowTitle);  	 

		_window = this;
		
		AdvancedCriteria initialCriteria = null;
		
		if(filterValues != null) {
			settingsMenu.removeItem(separatorMenuItem);
			settingsMenu.removeItem(autoSearchMenuItem);
			
			List<Criterion> lCriterionsList = new ArrayList<Criterion>();
			
			Criterion lFulltextCriterion = new Criterion("searchFulltext", OperatorId.ALL_THE_WORDS);	
			String parole = "";
									
			Map value = new HashMap();
			value.put("parole", parole);
			int size = Layout.getAttributiValueMap(getNomeEntita()).keySet().size();
			value.put("attributi", Layout.getAttributiValueMap(getNomeEntita()).keySet().toArray(new String[size]));
			value.put("flgRicorsiva", true);
			JSOHelper.setAttribute(lFulltextCriterion.getJsObj(), "value", value);		
			lCriterionsList.add(lFulltextCriterion);
			
			String nroProt = "";
			if(filterValues.getAttribute("nroProt") != null && !"".equals(filterValues.getAttribute("nroProt"))) {
				nroProt += filterValues.getAttribute("nroProt") + " ";
			}
			String annoProt = "";
			if(filterValues.getAttribute("annoProt") != null && !"".equals(filterValues.getAttribute("annoProt"))) {
				annoProt += filterValues.getAttribute("annoProt") + " ";
			}
			lCriterionsList.add(new Criterion("nroProt", OperatorId.EQUALS, nroProt));
			lCriterionsList.add(new Criterion("annoProt", OperatorId.EQUALS, annoProt));
			
			Criterion[] lCriterions = new Criterion[lCriterionsList.size()];				
			for(int i = 0; i < lCriterionsList.size(); i++) {
				lCriterions[i] = lCriterionsList.get(i);
			}
			
			initialCriteria = new AdvancedCriteria(OperatorId.AND, lCriterions);
		}
		
		portletLayout = new ArchivioLayout(null, null, null, null, null, initialCriteria) {	
			
			@Override
			public boolean isForcedToAutoSearch() {				
				return true;
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
					_window.setTitle(windowTitle); 
				} 	
			}
		};
		portletLayout.setHeight100();
		portletLayout.setWidth100();
		
		setBody(portletLayout);
   
        setIcon("menu/archivio.png");
	}

}