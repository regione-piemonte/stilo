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
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.toolbar.ToolStrip;

import it.eng.auriga.ui.module.layout.client.AurigaLayout;
import it.eng.auriga.ui.module.layout.client.i18n.I18NUtil;
import it.eng.utility.ui.module.layout.client.Layout;
import it.eng.utility.ui.module.layout.client.common.DetailToolStripButton;
import it.eng.utility.ui.module.layout.client.portal.ModalWindow;

public abstract class LookupOrganigrammaPopup extends ModalWindow {
	
	private LookupOrganigrammaPopup _window;
	
	private OrganigrammaLayout portletLayout;
	

	protected ToolStrip detailToolStrip;
	
	public LookupOrganigrammaPopup(final Record filterValues, boolean flgSelezioneSingola) {
		this(filterValues, flgSelezioneSingola, null);
	}
	
	public LookupOrganigrammaPopup(final Record filterValues, boolean flgSelezioneSingola, Integer flgIncludiUtenti) {
		
		super("organigramma", true);
		
		setTitle(getWindowTitle());
		
		_window = this;
				
		String idRootNode = null;
		
		AdvancedCriteria initialCriteria = null;
		
		if(filterValues != null) {
			settingsMenu.removeItem(separatorMenuItem);
			settingsMenu.removeItem(autoSearchMenuItem);
			idRootNode = "/";			
			
			List<Criterion> lCriterionsList = new ArrayList<Criterion>();
			
			Criterion lFulltextCriterion = new Criterion("searchFulltext", OperatorId.ALL_THE_WORDS);	
			String parole = "";
//			if(filterValues.getAttribute("denominazioneSoggetto") != null && !"".equals(filterValues.getAttribute("denominazioneSoggetto"))) {
//				parole += filterValues.getAttribute("denominazioneSoggetto") + " ";
//			}
//			if(filterValues.getAttribute("cognomeSoggetto") != null && !"".equals(filterValues.getAttribute("cognomeSoggetto"))) {
//				parole += filterValues.getAttribute("cognomeSoggetto") + " ";
//			}
//			if(filterValues.getAttribute("nomeSoggetto") != null && !"".equals(filterValues.getAttribute("nomeSoggetto"))) {
//				parole += filterValues.getAttribute("nomeSoggetto") + " ";
//			}
//			if(filterValues.getAttribute("codfiscaleSoggetto") != null && !"".equals(filterValues.getAttribute("codfiscaleSoggetto"))) {
//				parole += filterValues.getAttribute("codfiscaleSoggetto") + " ";
//			}		
//			parole = parole.trim();										
			Map value = new HashMap();
			value.put("parole", parole);
			int size = Layout.getAttributiValueMap(getNomeEntita()).keySet().size();
			value.put("attributi", Layout.getAttributiValueMap(getNomeEntita()).keySet().toArray(new String[size]));
			value.put("flgRicorsiva", true);
			JSOHelper.setAttribute(lFulltextCriterion.getJsObj(), "value", value);		
			lCriterionsList.add(lFulltextCriterion);
			
			String strInDenominazione = "";
			if(filterValues.getAttribute("denominazioneSoggetto") != null && !"".equals(filterValues.getAttribute("denominazioneSoggetto"))) {
				strInDenominazione += filterValues.getAttribute("denominazioneSoggetto") + " ";
			}
			if(filterValues.getAttribute("cognomeSoggetto") != null && !"".equals(filterValues.getAttribute("cognomeSoggetto"))) {
				strInDenominazione += filterValues.getAttribute("cognomeSoggetto") + " ";
			}
			if(filterValues.getAttribute("nomeSoggetto") != null && !"".equals(filterValues.getAttribute("nomeSoggetto"))) {
				strInDenominazione += filterValues.getAttribute("nomeSoggetto") + " ";
			}
//			if(filterValues.getAttribute("codfiscaleSoggetto") != null && !"".equals(filterValues.getAttribute("codfiscaleSoggetto"))) {
//				strInDenominazione += filterValues.getAttribute("codfiscaleSoggetto") + " ";
//			}		
			strInDenominazione = strInDenominazione.trim();
			lCriterionsList.add(new Criterion("strInDenominazione", OperatorId.WORDS_START_WITH, strInDenominazione));
			
			Criterion[] lCriterions = new Criterion[lCriterionsList.size()];				
			for(int i = 0; i < lCriterionsList.size(); i++) {
				lCriterions[i] = lCriterionsList.get(i);
			}
			
			initialCriteria = new AdvancedCriteria(OperatorId.AND, lCriterions);
		}
		
		portletLayout = new OrganigrammaLayout(getFinalita(), flgSelezioneSingola, true, idRootNode, flgIncludiUtenti, null, null, getFlgSoloAttive(), null, initialCriteria, getIdUd(), getIdEmail()) {
			
			//TODO PER APRIRE LA LOOKUP ORGANIGRAMMA IN MODALITA CERCA MA SENZA FARE LA FETCH (SE NON HO SALVATA NESSUNA PREF RELATIVA AI FILTRI) 
			/*
			@Override
			public void cercaModeIniziale() {
				if(initialCriteria != null) {
					cercaMode(initialCriteria, true, initialCriteria.getCriteria().length > 0);
				} else if (defaultCriteria != null && !skipRicercaPreferitaIniziale()) {
					if (idRootNode != null && !"".equals(idRootNode)) {
						cercaMode(defaultCriteria, true, !skipAutoSearchInizialeWithIdRootNode());
					} else { 
						cercaMode(defaultCriteria, false, false);
					}
				} else {
					cercaMode();
				}		
			}
			*/
			
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
			public boolean getFlgMostraSVDefaultValueLookup() {
	
				return getFlgMostraSVDefaultValue();
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
					_window.setTitle(getWindowTitle()); 
				} 	
			}
			
		};
		portletLayout.setLookup(true);
		
		portletLayout.setHeight100();
		portletLayout.setWidth100();
		if (AurigaLayout.getIsAttivaAccessibilita()) {
			addBackButton();
			portletLayout.setHeight(450);
			portletLayout.addMember(detailToolStrip);		
		} 
		
		setBody(portletLayout);             
         
        setIcon("menu/organigramma.png");
                
	}
	
	private void addBackButton() {
		detailToolStrip = new ToolStrip();
		detailToolStrip.setWidth100();
		detailToolStrip.setHeight(30);
		detailToolStrip.setStyleName(it.eng.utility.Styles.detailToolStrip);
//		detailToolStrip.addFill(); // push all buttons to the right
		
		DetailToolStripButton closeModalWindow = new DetailToolStripButton(I18NUtil.getMessages().backButton_prompt(), "buttons/back.png");
		closeModalWindow.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				_window.close();
			}
		});
		
		detailToolStrip.addButton(closeModalWindow);
	}
	
	public boolean getFlgMostraSVDefaultValue() {
		return true;
	}
	
	public String getWindowTitle() {
		return I18NUtil.getMessages().protocollazione_detail_lookupOrganigrammaButton_prompt();
	};
	
	public String getFinalita() {
		return null;
	};
	
	public Boolean getFlgSoloAttive() {
		return true;
	};
	
	public String getIdUd() {
		return null;
	}
	
	public String getIdEmail() {
		return null;
	}
	
	public abstract void manageLookupBack(Record record);	
	public abstract void manageMultiLookupBack(Record record);
	public abstract void manageMultiLookupUndo(Record record);
	
}