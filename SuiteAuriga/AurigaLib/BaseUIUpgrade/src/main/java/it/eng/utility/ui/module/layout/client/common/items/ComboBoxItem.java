/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import com.smartgwt.client.data.Criteria;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.data.RecordList;
import com.smartgwt.client.util.JSOHelper;
import com.smartgwt.client.util.StringUtil;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.FormItemHoverFormatter;
import com.smartgwt.client.widgets.form.fields.FormItem;
import com.smartgwt.client.widgets.form.fields.FormItemIcon;
import com.smartgwt.client.widgets.form.fields.events.ChangedEvent;
import com.smartgwt.client.widgets.form.fields.events.ChangedHandler;
import com.smartgwt.client.widgets.form.fields.events.DataArrivedEvent;
import com.smartgwt.client.widgets.form.fields.events.DataArrivedHandler;
import com.smartgwt.client.widgets.form.fields.events.FormItemClickHandler;
import com.smartgwt.client.widgets.form.fields.events.FormItemIconClickEvent;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.events.CellClickEvent;
import com.smartgwt.client.widgets.grid.events.CellClickHandler;

import it.eng.utility.ui.module.core.client.UserInterfaceFactory;
import it.eng.utility.ui.module.core.client.i18n.I18NUtil;

public class ComboBoxItem extends com.smartgwt.client.widgets.form.fields.ComboBoxItem {

	ComboBoxItem instance;
	FormItemIcon clearIcon;
	boolean clearable = false;
	int widthOrig;
	
	Criteria criteria;
	
	public ComboBoxItem() {
		
		instance = this;
		
		ListGrid pickListProperties = new ListGrid();
		pickListProperties.setEmptyMessage(I18NUtil.getMessages().list_emptyMessage());
		pickListProperties.addCellClickHandler(new CellClickHandler() {			
			@Override
			public void onCellClick(CellClickEvent event) {
				onOptionClick(event.getRecord());
			}
		});	
		
		addDataArrivedHandler(new DataArrivedHandler() {
			
			@Override
			public void onDataArrived(DataArrivedEvent event) {
				RecordList data = event.getData();
				String oldValue = instance.getValue() != null ? String.valueOf(instance.getValue()) : null;
				String enteredValue = instance.getAttribute("enteredValue");	
				if(enteredValue != null && !"".equals(enteredValue)) {
					if (data != null && data.getLength() > 0){
						for(int i = 0; i < data.getLength(); i++) {
							Record record = data.get(i);
							if(enteredValue.equalsIgnoreCase(record.getAttribute(getDisplayField()))) {															
								if(oldValue == null || !oldValue.equals(record.getAttribute(getValueField()))) {
									try {
										ListGrid pickList = (ListGrid) ListGrid.getByJSObject(JSOHelper.getAttributeAsJavaScriptObject(instance.getJsObj(), "pickList"));
										pickList.selectSingleRecord(record);
									} catch(Exception e) {
									}
								}
							}	
						 }
					}
				}
			}
		});
		
		addChangedHandler(new ChangedHandler() {
				
			 @Override
			   public void onChanged(ChangedEvent event) {
			       try {
			           if(instance.getSelectedRecord() != null) {
			        	   onOptionClick(instance.getSelectedRecord());			        	  
			           } else {
			        	   if(getAddUnknownValues() != null && getAddUnknownValues()) {
			        		   String enteredValue = instance.getAttribute("enteredValue");
				        	   if(enteredValue == null || "".equals(enteredValue)) {			   				
				        		   clearSelect();
				        	   }
			        	   } else {
			        		   clearSelect();
			        	   }
			           }
			       }catch(Exception e){}
			   }
		});
		
		this.setPickListProperties(pickListProperties);
		this.setAutoFetchData(true);
		this.setAddUnknownValues(false);
		this.setCompleteOnTab(true);
		this.setShowAllOptions(true);
        
//		this.setHeight(20);
		this.setWidth(250);		
		
		clearIcon = new FormItemIcon();   
		clearIcon.setSrc("buttons/clear.png");		
		clearIcon.setHeight(16);
		clearIcon.setWidth(16);    	
		clearIcon.setPrompt(I18NUtil.getMessages().removeValoreButton_Title());
		clearIcon.setNeverDisable(true);
		clearIcon.addFormItemClickHandler(new FormItemClickHandler() {			
			@Override
			public void onFormItemClick(FormItemIconClickEvent event) {
				clearSelect();
				try {
					ChangedEvent lChangedEvent = new ChangedEvent(instance.getJsObj()){
						@Override
						public Object getValue() {
							return instance.getValue();
						}
					};
					fireEvent(lChangedEvent);
				} catch(Exception e) {
				}					
			}
		});
		clearIcon.setTabIndex(UserInterfaceFactory.isAttivaAccessibilita() ? 0 : -1);
        
		this.setIcons(clearIcon);	
		this.setCanEdit(true);
		
		setItemHoverFormatter(true, new FormItemHoverFormatter() {			
			@Override
			public String getHoverHTML(FormItem item, DynamicForm form) {
				return StringUtil.asHTML((String)item.getDisplayValue());				
			}
		});		
		
	}
	
	protected void clearSelect(){
		setValue("");
	}
	
	@Override
	public void setWidth(int width) {
		super.setWidth(width);
		widthOrig = width;
	}
	
	public ComboBoxItem(String name) {
		this();
	    setName(name);        
    }

    public ComboBoxItem(String name, String title) {
    	this();
        setName(name);
		setTitle(title);
    }
    
    @Override
    public void setCanEdit(Boolean canEdit) {
    	super.setCanEdit(canEdit);
		setTextBoxStyle(canEdit ? it.eng.utility.Styles.selectItemText : it.eng.utility.Styles.selectItemTextReadOnly);     	
    	this.setShowIcons(canEdit && clearable);
    	setShowPickerIcon(canEdit);
    	setCanFocus(canEdit ? true : false);
    }
    
    @Override
	public void setShowIcons(Boolean showIcons) {
		super.setShowIcons(showIcons);
//		if(showIcons) {
//			setAttribute("width", widthOrig + 19);
//		} else {
//			setAttribute("width", widthOrig);
//		}
	}
    
    public boolean isClearable() {    	
		return clearable;
	}
    
    public void setClearable(boolean clearable) {		
		this.clearable = clearable;		
	}
    
    public void onOptionClick(Record record) {}
    
   
}
