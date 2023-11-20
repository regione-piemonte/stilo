/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import com.smartgwt.client.data.AdvancedCriteria;
import com.smartgwt.client.data.Criterion;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.data.RecordList;
import com.smartgwt.client.types.OperatorId;
import com.smartgwt.client.util.JSOHelper;

import it.eng.auriga.ui.module.layout.client.i18n.I18NUtil;
import it.eng.utility.ui.module.layout.client.Layout;
import it.eng.utility.ui.module.layout.client.portal.ModalWindow;

public abstract class LookupGestioneUtentiPopup extends ModalWindow {

	private LookupGestioneUtentiPopup _window;
	
	private GestioneUtentiLayout portletLayout;
	
	public LookupGestioneUtentiPopup(final Record filterValues) {
		
		super("gestioneutenti", true);
		
		setTitle(I18NUtil.getMessages().gestioneutenti_lookupGestioneUtentiPopup_title());  	
		
		_window = this;
		
		final RecordList listaPrivilegiUOAbilitate = filterValues != null ? filterValues.getAttributeAsRecordList("listaPrivilegiUOAbilitate") : null;
		
		if(listaPrivilegiUOAbilitate != null && listaPrivilegiUOAbilitate.getLength() == 1) {
			settingsMenu.removeItem(separatorMenuItem);
			settingsMenu.removeItem(autoSearchMenuItem);
		}
			
		portletLayout = new GestioneUtentiLayout() {
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
					_window.setTitle(I18NUtil.getMessages().gestioneutenti_lookupGestioneUtentiPopup_title()); 
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
				// Se ho una sola struttura abilitata la preimposto nel filtro
				if(listaPrivilegiUOAbilitate != null && listaPrivilegiUOAbilitate.getLength() == 1) {
					Criterion lUoCollegataCriterion = new Criterion("uoCollegata", OperatorId.EQUALS);
					Record recordUoCollegata = listaPrivilegiUOAbilitate.get(0);
					if(recordUoCollegata != null) {
						Map value = new HashMap();
						value.put("idUo", recordUoCollegata.getAttribute("idOggettoPriv"));
						value.put("codRapido", recordUoCollegata.getAttribute("codiceOggettoPriv"));
						value.put("descrizione", recordUoCollegata.getAttribute("codiceOggettoPriv") + " - " + recordUoCollegata.getAttribute("denominazioneOggettoPriv"));
						JSOHelper.setAttribute(lUoCollegataCriterion.getJsObj(), "value", value);
						mapCriterion.put("uoCollegata", lUoCollegataCriterion);
					}				
				}
				super.setCriteriaAndFirstSearch(new AdvancedCriteria(OperatorId.AND, mapCriterion.values().toArray(new Criterion[0])), ((listaPrivilegiUOAbilitate != null && listaPrivilegiUOAbilitate.getLength() == 1) || autoSearch));
			}
			
			@Override
			public void setDefaultCriteriaAndFirstSearch(boolean autosearch) {
				if(listaPrivilegiUOAbilitate != null && listaPrivilegiUOAbilitate.getLength() == 1) {
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
       
        setIcon("menu/gestioneutenti.png");
                
	}

	@Override
	public void manageOnCloseClick() {
		if(getIsModal()) {
			markForDestroy();
		} else {
			Layout.removePortlet(getNomeEntita());
		}	
		
		int size = portletLayout.getMultiLookupGridSize();
		
		if (size>0)
			afterManageOnCloseClick(true);
		else
			afterManageOnCloseClick(false);
	}
	
	public abstract void manageLookupBack(Record record);	
	public abstract void manageMultiLookupBack(Record record);
	public abstract void manageMultiLookupUndo(Record record);
	public abstract void afterManageOnCloseClick(boolean flg);		
}
