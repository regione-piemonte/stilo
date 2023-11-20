/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import com.smartgwt.client.data.Record;
import com.smartgwt.client.types.AutoFitWidthApproach;
import com.smartgwt.client.types.DateDisplayFormat;
import com.smartgwt.client.types.FetchMode;
import com.smartgwt.client.types.Overflow;
import com.smartgwt.client.util.StringUtil;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.FormItemHoverFormatter;
import com.smartgwt.client.widgets.form.fields.FormItem;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.events.CellClickEvent;
import com.smartgwt.client.widgets.grid.events.CellClickHandler;

import it.eng.utility.ui.module.core.client.i18n.I18NUtil;
import it.eng.utility.ui.module.layout.client.Layout;

public class FilteredComboBoxItem extends it.eng.utility.ui.module.layout.client.common.items.ComboBoxItem {
	
	ListGrid _pickListProperties;
	FilteredComboBoxItem _instance;

	public FilteredComboBoxItem() {
		
		_instance = this;
		setPickListProperties(builPickListProperties());
		setAutoFetchData(true);
		setDateFormatter(DateDisplayFormat.TOEUROPEANSHORTDATE);
		setRedrawOnChange(true);
		setCachePickListResults(false);		
		setFetchMissingValues(true);
		setAlwaysFetchMissingValues(true);		
		setItemHoverFormatter(true, new FormItemHoverFormatter() {
			@Override
			public String getHoverHTML(FormItem item, DynamicForm form) {
				return manageHover(item);
			}
		});
	}
	
	protected ListGrid builPickListProperties() {
		ListGrid pickListProperties = new ListGrid(); 		
		pickListProperties.setEmptyMessage(I18NUtil.getMessages().list_emptyMessage());
//		pickListProperties.setGridComponents(new ListGridComponent[]{ListGridComponent.HEADER, ListGridComponent.FILTER_EDITOR, ListGridComponent.BODY});
		pickListProperties.setCanHover(true);
		pickListProperties.setShowAllRecords(false);
		pickListProperties.setLeaveScrollbarGap(false);
		pickListProperties.setFixedRecordHeights(true); 
//		pickListProperties.setAutoFetchData(true);
		pickListProperties.setAutoFetchDisplayMap(true);
		pickListProperties.setDataFetchMode(FetchMode.PAGED);
		pickListProperties.setOverflow(Overflow.HIDDEN);
		pickListProperties.setCellHeight(Layout.getGenericConfig().getRecordHeightDefault());
//		pickListProperties.setShowFilterEditor(true); 
//		pickListProperties.setShowHeader(getShowPickListHeader());
//		pickListProperties.setFilterOnKeypress(true);
//		pickListProperties.setFetchDelay(1000);		
//		Button filterButton = new Button();
//		filterButton.setIcon("buttons/search.png");
//		filterButton.setShowRollOverIcon(false);		
//		pickListProperties.setFilterButtonProperties(filterButton);
//		pickListProperties.setFilterButtonPrompt(I18NUtil.getMessages().filteredSelectItem_filterButton_prompt());		
		pickListProperties.setAutoFitWidthApproach(AutoFitWidthApproach.BOTH);
		pickListProperties.setCanResizeFields(true);						
		pickListProperties.setDataPageSize(Layout.getGenericConfig().getFiltrableSelectPageSize());		
		pickListProperties.addCellClickHandler(new CellClickHandler() {			
			@Override
			public void onCellClick(CellClickEvent event) {
				manageOnCellClick(event);				
			}			
		});					
		return pickListProperties;
	}
	
	public void manageOnCellClick(CellClickEvent event) {
		onOptionClick(event.getRecord());
	}
	
	public String manageHover(FormItem item) {
		String lString = (String)item.getValue();
		if (lString==null || lString.isEmpty()) return null;
		return StringUtil.asHTML((String)item.getDisplayValue());
	}
	
	public FilteredComboBoxItem(String name) {
		this();
	    setName(name);        
    }

    public FilteredComboBoxItem(String name, String title) {
    	this();
        setName(name);
		setTitle(title);
    }
    
    public boolean getShowPickListHeader() {
    	return true;
    }
    
    public void onOptionClick(Record record) {}
    
    public void setPickListProperties(ListGrid pickListProperties) {
    	this._pickListProperties = pickListProperties;
    	super.setPickListProperties(_pickListProperties);
    }   
    
    public ListGrid getPickListProperties() {
    	return _pickListProperties;
    }
   
}
