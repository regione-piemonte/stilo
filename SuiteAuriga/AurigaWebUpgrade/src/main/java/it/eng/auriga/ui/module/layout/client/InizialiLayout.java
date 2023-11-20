/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.auriga.ui.module.layout.client.anagrafiche.InizialeButton;
import it.eng.utility.ui.module.core.client.datasource.GWTRestDataSource;
import it.eng.utility.ui.module.layout.client.common.ConfigurableFilter;
import it.eng.utility.ui.module.layout.client.common.CustomDetail;
import it.eng.utility.ui.module.layout.client.common.CustomLayout;
import it.eng.utility.ui.module.layout.client.common.CustomList;

import java.util.ArrayList;
import java.util.List;

import com.smartgwt.client.data.AdvancedCriteria;
import com.smartgwt.client.data.Criterion;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.BackgroundRepeat;

import com.smartgwt.client.types.OperatorId;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.toolbar.ToolStrip;

public abstract class InizialiLayout  extends CustomLayout  {
	
	private String iniziale = null;
	private ToolStrip inizialiToolStrip;
	
	public InizialiLayout(String nomeEntita, final GWTRestDataSource pDatasource, ConfigurableFilter pFilter, CustomList pList, CustomDetail pDetail) {			
		super(nomeEntita, nomeEntita, pDatasource, pFilter, pList, pDetail, null, null, null);
		buildInizialiToolStrip();
	}
	
	public InizialiLayout(String nomeEntita, final GWTRestDataSource pDatasource, ConfigurableFilter pFilter, CustomList pList, CustomDetail pDetail, Boolean pShowOnlyDetail) {			
		super(nomeEntita, nomeEntita, pDatasource, pFilter, pList, pDetail, null, null, pShowOnlyDetail);
		buildInizialiToolStrip();
	}
	
	public InizialiLayout(String nomeEntita, final GWTRestDataSource pDatasource, ConfigurableFilter pFilter, CustomList pList, CustomDetail pDetail, String pFinalita, Boolean pFlgSelezioneSingola) {		
		super(nomeEntita, nomeEntita, pDatasource, pFilter, pList, pDetail, pFinalita, pFlgSelezioneSingola, null);
		buildInizialiToolStrip();
	}
	
	public InizialiLayout(String nomeEntita, final GWTRestDataSource pDatasource, ConfigurableFilter pFilter, CustomList pList, CustomDetail pDetail, String pFinalita, Boolean pFlgSelezioneSingola, Boolean pShowOnlyDetail) {		
		super(nomeEntita, nomeEntita, pDatasource, pFilter, pList, pDetail, pFinalita, pFlgSelezioneSingola, pShowOnlyDetail);
		buildInizialiToolStrip();
	}
	
	public InizialiLayout(String nomePortlet, String nomeEntita, final GWTRestDataSource pDatasource, ConfigurableFilter pFilter, CustomList pList, CustomDetail pDetail) {			
		super(nomePortlet, nomeEntita, pDatasource, pFilter, pList, pDetail, null, null, null);
		buildInizialiToolStrip();
	}
	
	public InizialiLayout(String nomePortlet, String nomeEntita, final GWTRestDataSource pDatasource, ConfigurableFilter pFilter, CustomList pList, CustomDetail pDetail, Boolean pShowOnlyDetail) {			
		super(nomePortlet, nomeEntita, pDatasource, pFilter, pList, pDetail, null, null, pShowOnlyDetail);
		buildInizialiToolStrip();
	}
	
	public InizialiLayout(String nomePortlet, String nomeEntita, final GWTRestDataSource pDatasource, ConfigurableFilter pFilter, CustomList pList, CustomDetail pDetail, String pFinalita, Boolean pFlgSelezioneSingola) {		
		super(nomePortlet, nomeEntita, pDatasource, pFilter, pList, pDetail, pFinalita, pFlgSelezioneSingola, null);
		buildInizialiToolStrip();
	}
	
	public InizialiLayout(String nomePortlet, String nomeEntita, final GWTRestDataSource pDatasource, ConfigurableFilter pFilter, CustomList pList, CustomDetail pDetail, String pFinalita, Boolean pFlgSelezioneSingola, Boolean pShowOnlyDetail) {		
		super(nomePortlet, nomeEntita, pDatasource, pFilter, pList, pDetail, pFinalita, pFlgSelezioneSingola, pShowOnlyDetail);
		buildInizialiToolStrip();
	}
	
	public void buildInizialiToolStrip() {
		inizialiToolStrip = new ToolStrip();		
		inizialiToolStrip.setWidth100();
		inizialiToolStrip.setHeight(30);
		inizialiToolStrip.setBackgroundColor("transparent");
		inizialiToolStrip.setBackgroundImage("blank.png");
		inizialiToolStrip.setBackgroundRepeat(BackgroundRepeat.REPEAT_X);
		inizialiToolStrip.setBorder("0px");
		inizialiToolStrip.setPadding(5);
		if (AurigaLayout.getIsAttivaAccessibilita()) {
			inizialiToolStrip.setTabIndex(-1);
			inizialiToolStrip.setCanFocus(false);		
		}
		
		inizialiToolStrip.addButton(createInizialeButton("A"));
		inizialiToolStrip.addButton(createInizialeButton("B"));
		inizialiToolStrip.addButton(createInizialeButton("C"));
		inizialiToolStrip.addButton(createInizialeButton("D"));
		inizialiToolStrip.addButton(createInizialeButton("E"));
		inizialiToolStrip.addButton(createInizialeButton("F"));
		inizialiToolStrip.addButton(createInizialeButton("G"));
		inizialiToolStrip.addButton(createInizialeButton("H"));
		inizialiToolStrip.addButton(createInizialeButton("I"));
		inizialiToolStrip.addButton(createInizialeButton("J"));
		inizialiToolStrip.addButton(createInizialeButton("K"));
		inizialiToolStrip.addButton(createInizialeButton("L"));
		inizialiToolStrip.addButton(createInizialeButton("M"));
		inizialiToolStrip.addButton(createInizialeButton("N"));
		inizialiToolStrip.addButton(createInizialeButton("O"));
		inizialiToolStrip.addButton(createInizialeButton("P"));
		inizialiToolStrip.addButton(createInizialeButton("Q"));
		inizialiToolStrip.addButton(createInizialeButton("R"));
		inizialiToolStrip.addButton(createInizialeButton("S"));
		inizialiToolStrip.addButton(createInizialeButton("T"));
		inizialiToolStrip.addButton(createInizialeButton("U"));
		inizialiToolStrip.addButton(createInizialeButton("V"));
		inizialiToolStrip.addButton(createInizialeButton("W"));
		inizialiToolStrip.addButton(createInizialeButton("X"));
		inizialiToolStrip.addButton(createInizialeButton("Y"));
		inizialiToolStrip.addButton(createInizialeButton("Z"));
		inizialiToolStrip.setHeight(20);
		inizialiToolStrip.setWidth100();
		inizialiToolStrip.setAlign(Alignment.CENTER);
		
		filterLayout.setMembers(filterToolStrip, inizialiToolStrip, filter, filterButtons);  			
	}
	
	public InizialeButton createInizialeButton(final String value) {
		InizialeButton button = new InizialeButton(value, this);
		button.addClickHandler(new ClickHandler() {			
			@Override
			public void onClick(ClickEvent event) {
				manageOnClickInizialeButton(event, value);
			}
		});
		return button;							
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
					
		if(iniziale != null) {
			criterionList.add(new Criterion("iniziale", OperatorId.EQUALS, iniziale));			
		}							
		
		Criterion[] criterias = new Criterion[criterionList.size()];
		for(int i = 0; i < criterionList.size(); i++) {
			criterias[i] = criterionList.get(i);
		}
		return super.buildSearchCriteria(new AdvancedCriteria(OperatorId.AND, criterias));
	}
	
	public String getIniziale() {
		return iniziale;
	}

	public void setIniziale(String iniziale) {
		this.iniziale = iniziale;		
		for(Canvas member : inizialiToolStrip.getMembers()) {
			if(member instanceof InizialeButton) {
				InizialeButton button = (InizialeButton) member;
				if(iniziale != null && iniziale.equals(button.getValue())) {
					button.setBorder("1px solid grey");
				} else {
					button.setBorder("0px");
				}
			}
		}
	}
	
	@Override
	public void changeDetail(GWTRestDataSource datasource, CustomDetail pDetail) {
		
		super.changeDetail(datasource, pDetail);
		buildInizialiToolStrip();
	}
	
	public abstract void manageOnClickInizialeButton(ClickEvent event, String iniziale);
	
}
