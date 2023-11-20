/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.LinkedHashMap;

import com.smartgwt.client.data.AdvancedCriteria;
import com.smartgwt.client.data.Criterion;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.types.OperatorId;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.toolbar.ToolStrip;

import it.eng.auriga.ui.module.layout.client.i18n.I18NUtil;
import it.eng.utility.ui.module.layout.client.common.DetailToolStripButton;
import it.eng.utility.ui.module.layout.client.portal.ModalWindow;
import it.eng.auriga.ui.module.layout.client.AurigaLayout;

public abstract class LookupSoggettiPopup extends ModalWindow {

	private LookupSoggettiPopup _window;
	
	private SoggettiLayout portletLayout;

	protected ToolStrip detailToolStrip;
	
	public LookupSoggettiPopup(final Record filterValues, final String tipoSoggetto, boolean flgSelezioneSingola) {
		
		super("anagrafiche_soggetti", true);
		
		setTitle(getWindowTitle());  	
		
		_window = this;
			
		if(filterValues != null) {
			settingsMenu.removeItem(separatorMenuItem);
			settingsMenu.removeItem(autoSearchMenuItem);
		}
				
		portletLayout = new SoggettiLayout(getFinalita(), flgSelezioneSingola) {
			
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
					_window.setTitle(getWindowTitle()); 
				} 	
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
				if(filterValues != null || (tipoSoggetto != null && !"".equals(tipoSoggetto)) || (getTipiAmmessi() != null && getTipiAmmessi().length > 0)) {					
					if(filterValues != null) {	
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
						strInDenominazione = strInDenominazione.trim();
						
						if(!strInDenominazione.equals(""))
							mapCriterion.put("strInDenominazione", new Criterion("strInDenominazione", OperatorId.WORDS_START_WITH, strInDenominazione));	
					}	
					
					if(tipoSoggetto != null && !"".equals(tipoSoggetto)) {
						mapCriterion.put("tipologia", new Criterion("tipologia", OperatorId.EQUALS, getTipologiaFromTipoSoggetto(tipoSoggetto)));													
					} 
					else if(getTipiAmmessi() != null) {
						String[] tipologieAmmesse = new String[getTipiAmmessi().length];
						for(int i = 0; i < getTipiAmmessi().length; i++) {
							tipologieAmmesse[i] = getTipologiaFromTipoSoggetto(getTipiAmmessi()[i]);
						} 
						mapCriterion.put("tipologia", new Criterion("tipologia", OperatorId.EQUALS, tipologieAmmesse));
					} 
					else {
						mapCriterion.put("tipologia", new Criterion("tipologia", OperatorId.EQUALS, new String[0]));
					}
				}				
				super.setCriteriaAndFirstSearch(new AdvancedCriteria(OperatorId.AND, mapCriterion.values().toArray(new Criterion[0])), (filterValues != null || autoSearch));
			}
			
			@Override
			public void setDefaultCriteriaAndFirstSearch(boolean autosearch) {
				if(filterValues != null || (tipoSoggetto != null && !"".equals(tipoSoggetto)) || (getTipiAmmessi() != null && getTipiAmmessi().length > 0)) {					
					setCriteriaAndFirstSearch(new AdvancedCriteria(), autosearch);
				} else {
					super.setDefaultCriteriaAndFirstSearch(autosearch);
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
         
        setIcon("menu/soggetti.png");
                
	}
	
	public String getTipologiaFromTipoSoggetto(String tipoSoggetto) {
		if("PA".equals(tipoSoggetto)) {
			return "#APA";							
		} else if("AOOI".equals(tipoSoggetto)) {
			return "#IAMM";							
		} else if("UOI".equals(tipoSoggetto)) {
			return "UO;UOI";							
		} else if("UP".equals(tipoSoggetto)) {
			return "UP";							
		} else if("PF".equals(tipoSoggetto)) {
			return "#AF";		
		} else if("PG".equals(tipoSoggetto)) {
			return "#AG";		
		}   
		return null;
	}
	
	public String[] getTipiAmmessi() {
		return null;
	}
	
	public String getWindowTitle() {
		return I18NUtil.getMessages().soggetti_lookupSoggettiPopup_title();
	};
	
	public String getFinalita() {
		return null;
	};
 
	public abstract void manageLookupBack(Record record);	
	public abstract void manageMultiLookupBack(Record record);
	public abstract void manageMultiLookupUndo(Record record);
	
	private void addBackButton() {
		detailToolStrip = new ToolStrip();
		detailToolStrip.setWidth100();
		detailToolStrip.setHeight(30);
		detailToolStrip.setStyleName(it.eng.utility.Styles.detailToolStrip);
		detailToolStrip.addFill(); // push all buttons to the right
		
		DetailToolStripButton closeModalWindow = new DetailToolStripButton(I18NUtil.getMessages().backButton_prompt(), "buttons/back.png");
		closeModalWindow.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				_window.close();
			}
		});
		
		detailToolStrip.addButton(closeModalWindow);
	}
	
}
