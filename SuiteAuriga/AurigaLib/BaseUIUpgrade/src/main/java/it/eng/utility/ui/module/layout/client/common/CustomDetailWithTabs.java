/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.client.widgets.tab.Tab;
import com.smartgwt.client.widgets.tab.TabSet;

public class CustomDetailWithTabs extends CustomDetail {
	
	protected final TabSet tabSet;
		
	public CustomDetailWithTabs(String nomeEntita, String... tabs) {		
		
		super(nomeEntita);		
		
		setStyleName(it.eng.utility.Styles.detailLayoutWithTabSet);
		
		tabSet = new TabSet();	
		tabSet.setCanFocus(false);
		tabSet.setTabIndex(-1);
		tabSet.setHeight100();
		tabSet.setWidth100();
		for(int i = 0; i < tabs.length; i++) {
			Tab tab = new Tab(tabs[i]);
			VLayout layout = new VLayout();
			layout.setWidth100();
			layout.setHeight100();
			tab.setPane(layout);
			tabSet.addTab(tab);
		}
		addMember(tabSet);	
		
	}
	
	@Override
	public void addSubForm(DynamicForm form) {
		
//		for(FormItem item : form.getFields()) {
//			boolean required = (item.getRequired() != null ? item.getRequired() : false)  || 
//					   		   (item.getAttributeAsBoolean("obbligatorio") != null ? item.getAttributeAsBoolean("obbligatorio") : false);			
//			if(required) {
//				item.setTitle(FrontendUtil.getRequiredFormItemTitle(item.getTitle()));
//				item.setTitleStyle(it.eng.utility.Styles.formTitleBold);
//			}
//		}
				
		form.setBorder("0px solid grey");
		form.setColWidths(4);
		form.setColWidths("120","*","*","*");
		form.setValuesManager(vm);		
	}
	
	public void addSubFormOnTab(DynamicForm form, int tabIndex) {
		this.addSubForm(form);	
		if(tabSet != null && tabSet.getTab(tabIndex) != null) {
			((VLayout)((Tab)tabSet.getTab(tabIndex)).getPane()).addMember(form);
		}
	}
	
	public void disableTab(int tabIndex) {
		if(tabSet != null && tabSet.getTab(tabIndex) != null) {
			tabSet.disableTab(tabIndex);
		}
	}
	
	public void enableTab(int tabIndex) {
		if(tabSet != null && tabSet.getTab(tabIndex) != null) {
			tabSet.enableTab(tabIndex);
		}
	}
	
	public TabSet getTabSet() {
		return this.tabSet;
	}
}
