/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import com.smartgwt.client.data.Record;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.FormItemHoverFormatter;
import com.smartgwt.client.widgets.form.fields.FormItem;
import com.smartgwt.client.widgets.form.fields.FormItemIcon;
import com.smartgwt.client.widgets.form.fields.events.FormItemClickHandler;
import com.smartgwt.client.widgets.form.fields.events.FormItemIconClickEvent;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.events.CellClickEvent;
import com.smartgwt.client.widgets.grid.events.CellClickHandler;
import com.smartgwt.client.widgets.grid.events.ChangedEvent;

import it.eng.utility.ui.module.core.client.i18n.I18NUtil;
import it.eng.utility.ui.module.layout.client.common.ReplicableCanvas.ReplicableCanvasForm;
import it.eng.utility.ui.module.core.client.UserInterfaceFactory;

public class SelectItem extends com.smartgwt.client.widgets.form.fields.SelectItem {

	SelectItem instance;
	FormItemIcon clearIcon;
	boolean clearable = false;
	int widthOrig;
	ListGrid _pickListProperties;
					
	public SelectItem() {
		
		instance = this;
		
		this.setPickListProperties(builPickListProperties());
		this.setAutoFetchData(true);
		
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
			}
		});
		clearIcon.setTabIndex(UserInterfaceFactory.isAttivaAccessibilita() ? 0 : -1);
        
		this.setIcons(clearIcon);	
		this.setCanEdit(true);
		
		setItemHoverFormatter(new FormItemHoverFormatter() {			
			@Override
			public String getHoverHTML(FormItem item, DynamicForm form) {
				return (String)item.getDisplayValue();				
			}
		});		
	}
	
	protected ListGrid builPickListProperties() {
		ListGrid pickListProperties = new ListGrid(); 		
		pickListProperties.setEmptyMessage(I18NUtil.getMessages().list_emptyMessage());
		pickListProperties.addCellClickHandler(new CellClickHandler() {			
			@Override
			public void onCellClick(CellClickEvent event) {
				onOptionClick(event.getRecord());
			}
		});
		return pickListProperties;
	}
	
	protected void clearSelect(){
		if(instance.isClearable()) {
			instance.setValue("");	
			if(instance.getForm() != null && instance.getForm() instanceof ReplicableCanvasForm) {
				// per le ReplicableItem altrimenti non si salva il valore
				((ReplicableCanvasForm)instance.getForm()).setValue(instance.getName(), "");
			}
			ChangedEvent event = new ChangedEvent(instance.getJsObj()){
				@Override
				public Object getValue() {
					return instance.getValue();
				}
			};
			fireEvent(event);			
		}
	}
	
	@Override
	public void setWidth(int width) {
		super.setWidth(width);
		setPickListWidth(width + 50); // per togliere lo scroll orrizontale che compare con lo zoom al 125% su Chrome e che da problemi nella selezione del valore
		widthOrig = width;
	}
	
	public SelectItem(String name) {
		this();
	    setName(name);        
    }

    public SelectItem(String name, String title) {
    	this();
        setName(name);
		setTitle(title);
    }
    
    @Override
    public void setCanEdit(Boolean canEdit) {
    	super.setCanEdit(canEdit);
    	setTextBoxStyle(canEdit ? it.eng.utility.Styles.selectItemText : it.eng.utility.Styles.selectItemTextReadOnly);
    	// Con l'attule versione di SmartGWT la larghezza delle SelectItem è costante, indipendentemente dal fatto che vengano
    	// mostrate o no pickerIcon e clearable. 
    	// Quindi non serve più modificare la larghezza in base a se vengano visualizzate o no
    	if(!canEdit) {
    		setShowPickerIcon(false);
//    		super.setWidth(widthOrig + 2);
    		
    	} else {
    		setShowPickerIcon(true);
//    		super.setWidth(widthOrig);    		
    	}
    	this.setShowIcons(canEdit && clearable);    	   
    	if (UserInterfaceFactory.isAttivaAccessibilita()){
 	//    	setCanFocus(canEdit ? true : false); 			
    		setCanFocus(true);
 		} else {
 			setCanFocus(canEdit ? true : false);
 		}  
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
    
    public void setPickListProperties(ListGrid pickListProperties) {
    	this._pickListProperties = pickListProperties;
    	super.setPickListProperties(_pickListProperties);
    }   
    
    public ListGrid getPickListProperties() {
    	return _pickListProperties;
    }
    
    public void onOptionClick(Record record) {}
   
}