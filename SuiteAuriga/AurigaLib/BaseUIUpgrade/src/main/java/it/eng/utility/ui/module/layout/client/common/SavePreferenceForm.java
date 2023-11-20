/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import com.smartgwt.client.data.Criteria;
import com.smartgwt.client.data.DSCallback;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.util.StringUtil;
import com.smartgwt.client.widgets.Window;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.FormItemHoverFormatter;
import com.smartgwt.client.widgets.form.fields.ButtonItem;
import com.smartgwt.client.widgets.form.fields.ComboBoxItem;
import com.smartgwt.client.widgets.form.fields.FormItem;
import com.smartgwt.client.widgets.form.fields.events.ClickEvent;
import com.smartgwt.client.widgets.form.fields.events.ClickHandler;

import it.eng.utility.ui.module.core.client.datasource.GWTRestDataSource;
import it.eng.utility.ui.module.core.client.i18n.I18NUtil;
import it.eng.utility.ui.module.layout.client.common.items.CheckboxItem;

public class SavePreferenceForm extends DynamicForm{
	
	private final GWTRestDataSource preferenceNameDS;
	
	private SavePreferenceWindow savePreferenceWindow;			
	private ComboBoxItem preferenceNameComboBoxItem;
	private CheckboxItem flgPubblicaItem;
	private ButtonItem savePreferencButtonItem;
	
	private boolean defaultValue;
	
	public SavePreferenceForm(Window window, GWTRestDataSource datasource, boolean defaultValue){
		
		this.savePreferenceWindow = (SavePreferenceWindow) window;
		this.preferenceNameDS = datasource;
		
		this.defaultValue = defaultValue;
		
		setKeepInParentRect(true);
		setWidth100();
		setHeight100();
		if(savePreferenceWindow.isAttivaGestionePreferenzePubbliche()) {
			setNumCols(3);
			setColWidths(150, 150, 120);
		} else {
			setNumCols(2);
			setColWidths(150, 150);	
		}
		setCellPadding(5);
		setCanSubmit(true);
		
		preferenceNameComboBoxItem = new ComboBoxItem("prefName");
		preferenceNameComboBoxItem.setKeyPressFilter("[0-9a-zA-Z ,;.:!?|_-]");		
		preferenceNameComboBoxItem.setValueField("prefName");  
		preferenceNameComboBoxItem.setDisplayField("prefName");  
		preferenceNameComboBoxItem.setShowTitle(false);
		preferenceNameComboBoxItem.setCanEdit(true);
		preferenceNameComboBoxItem.setColSpan(2);
		preferenceNameComboBoxItem.setAlign(Alignment.CENTER);			
		preferenceNameComboBoxItem.setWidth(300);
		preferenceNameComboBoxItem.setRequired(true);
		preferenceNameComboBoxItem.setItemHoverFormatter(true, new FormItemHoverFormatter() {
		
			@Override
			public String getHoverHTML(FormItem item, DynamicForm form) {
				return StringUtil.asHTML((String) preferenceNameComboBoxItem.getValue());
			}
		});				
		preferenceNameComboBoxItem.setOptionDataSource(preferenceNameDS);
		
		savePreferencButtonItem = new ButtonItem();
		savePreferencButtonItem.setStartRow(true);
		savePreferencButtonItem.setTitle(I18NUtil.getMessages().savePreferenceButton_title());
		savePreferencButtonItem.setIcon("ok.png");
		savePreferencButtonItem.setIconHeight(16);
		savePreferencButtonItem.setIconWidth(16);
		if(savePreferenceWindow.isAttivaGestionePreferenzePubbliche()) {
			savePreferencButtonItem.setColSpan(3);
		} else {
			savePreferencButtonItem.setColSpan(2);
		}
		savePreferencButtonItem.setWidth(100);
		savePreferencButtonItem.setTop(20);
		savePreferencButtonItem.setAlign(Alignment.CENTER);			
		savePreferencButtonItem.addClickHandler(new ClickHandler() {			
			@Override
			public void onClick(ClickEvent event) {
				if (validate()) {
					savePreferenceWindow.savePreferenceAction(new Record(getValues()));
				}
			}
		});
		 
		if(savePreferenceWindow.isAttivaGestionePreferenzePubbliche()) {
			flgPubblicaItem = new CheckboxItem("flgPubblica", "pubblica");		
			flgPubblicaItem.setStartRow(false);
			flgPubblicaItem.setWidth(120);
			flgPubblicaItem.setColSpan(1);
			setFields(new FormItem[]{preferenceNameComboBoxItem, flgPubblicaItem, savePreferencButtonItem});
		} else {
			setFields(new FormItem[]{preferenceNameComboBoxItem, savePreferencButtonItem});
		}
		
		setAlign(Alignment.CENTER);
		setTop(50);
				
	}
	
	public void setPreferenceName(String prefName) {
		preferenceNameComboBoxItem.setValue(prefName);		
	}
	
	public void setValue(String value) {
		if(value != null && !"".equals(value)) {
			if(value.startsWith("PUBLIC.")) {
				if(value.contains("|*|")) {
					value = value.substring(value.indexOf("|*|") + 3);
				}
				if(flgPubblicaItem != null) {
					flgPubblicaItem.setValue(true);
				}
			} 
		}	
		final String prefName = value;
		preferenceNameComboBoxItem.setValue(prefName);
		preferenceNameDS.fetchData(new Criteria(), new DSCallback() {   
            @Override  
            public void execute(DSResponse response, Object rawData, DSRequest request) {   
                Record[] data = response.getData();
                if(prefName == null || "".equals(prefName)) {
	                if(defaultValue) {
	                	boolean showDefault = true;
	                    if (data != null && data.length > 0) { 
	                    	for(Record record : data) {
	                    		if(record.getAttribute("prefName").equalsIgnoreCase("DEFAULT")) {
	                    			showDefault = false;
	                    			break;
	                    		}
	                    	}
	                    }
	                    if(showDefault) {
	                    	preferenceNameComboBoxItem.setValue("DEFAULT");
	                    }
	            	}
                }
            }   
        });   				
	}
	
}
