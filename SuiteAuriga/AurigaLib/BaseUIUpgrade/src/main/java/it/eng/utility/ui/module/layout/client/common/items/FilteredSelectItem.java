/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import com.google.gwt.core.client.Scheduler;
import com.google.gwt.core.client.Scheduler.ScheduledCommand;
import com.smartgwt.client.data.Criteria;
import com.smartgwt.client.data.DSCallback;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.types.AutoFitWidthApproach;
import com.smartgwt.client.types.DateDisplayFormat;
import com.smartgwt.client.types.FetchMode;
import com.smartgwt.client.types.ListGridComponent;
import com.smartgwt.client.types.Overflow;
import com.smartgwt.client.util.EventHandler;
import com.smartgwt.client.widgets.Button;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.FormItemHoverFormatter;
import com.smartgwt.client.widgets.form.fields.FormItem;
import com.smartgwt.client.widgets.form.fields.SelectItem;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridRecord;
import com.smartgwt.client.widgets.grid.events.BodyKeyPressEvent;
import com.smartgwt.client.widgets.grid.events.BodyKeyPressHandler;
import com.smartgwt.client.widgets.grid.events.CellClickEvent;
import com.smartgwt.client.widgets.grid.events.CellClickHandler;
import com.smartgwt.client.widgets.grid.events.FilterEditorSubmitEvent;
import com.smartgwt.client.widgets.grid.events.FilterEditorSubmitHandler;

import it.eng.utility.ui.module.core.client.UserInterfaceFactory;
import it.eng.utility.ui.module.core.client.i18n.I18NUtil;
import it.eng.utility.ui.module.layout.client.Layout;

public class FilteredSelectItem extends it.eng.utility.ui.module.layout.client.common.items.SelectItem {
	
	FilteredSelectItem _instance;

	public FilteredSelectItem() {
		
		_instance = this;
		setPickListProperties(builPickListProperties());
		setAutoFetchData(true);
		setDateFormatter(DateDisplayFormat.TOEUROPEANSHORTDATE);
		setRedrawOnChange(true);
		setCachePickListResults(false);		
		setFetchMissingValues(true);
		setAlwaysFetchMissingValues(true);		
		setItemHoverFormatter(new FormItemHoverFormatter() {
			@Override
			public String getHoverHTML(FormItem item, DynamicForm form) {
				return manageHover(item);
			}
		});
	}
	
	@Override
	public void setWidth(int width) {
		super.setWidth(width);
		setPickListWidth(width + 50); // per togliere lo scroll orrizontale che compare con lo zoom al 125% su Chrome e che da problemi nella selezione del valore
	}
	
	protected ListGrid builPickListProperties() {
		ListGrid pickListProperties = new ListGrid(); 		
		pickListProperties.setEmptyMessage(I18NUtil.getMessages().list_emptyMessage());
		pickListProperties.setGridComponents(new ListGridComponent[]{ListGridComponent.HEADER, ListGridComponent.FILTER_EDITOR, ListGridComponent.BODY});
		pickListProperties.setCanHover(true);
		pickListProperties.setShowAllRecords(false);
		pickListProperties.setLeaveScrollbarGap(false);
		pickListProperties.setFixedRecordHeights(true); 
//		pickListProperties.setAutoFetchData(true);
		pickListProperties.setAutoFetchDisplayMap(true);
		pickListProperties.setDataFetchMode(FetchMode.PAGED);
		pickListProperties.setOverflow(Overflow.HIDDEN);
		pickListProperties.setCellHeight(Layout.getGenericConfig().getRecordHeightDefault());
		
		if(getShowPickListHeader()) {
			pickListProperties.setShowHeader(true);
			pickListProperties.setShowHeaderContextMenu(false);
			pickListProperties.setShowHeaderMenuButton(false);					
			setPickListHeaderHeight(22);
		} else {
			pickListProperties.setShowHeader(false);
			setPickListHeaderHeight(0);
		}
		
		pickListProperties.setShowFilterEditor(true); 
		pickListProperties.setFilterOnKeypress(true);
		pickListProperties.setFetchDelay(1000);		
		Button filterButton = new Button();
		filterButton.setIcon("buttons/search.png");
		filterButton.setShowRollOverIcon(false);		
		pickListProperties.setFilterButtonProperties(filterButton);
		pickListProperties.setFilterButtonPrompt(I18NUtil.getMessages().filteredSelectItem_filterButton_prompt());
		pickListProperties.addFilterEditorSubmitHandler(new FilterEditorSubmitHandler() {
			
			@Override
			public void onFilterEditorSubmit(FilterEditorSubmitEvent event) {
				doOnFilterEditorSubmit(event, _instance);
			}
		});

		pickListProperties.setAutoFitWidthApproach(AutoFitWidthApproach.BOTH);
		pickListProperties.setCanResizeFields(true);						
		pickListProperties.setDataPageSize(Layout.getGenericConfig().getFiltrableSelectPageSize());		
		pickListProperties.addCellClickHandler(new CellClickHandler() {			
			@Override
			public void onCellClick(CellClickEvent event) {
				manageOnCellClick(event);				
			}			
		});		
		if (UserInterfaceFactory.isAttivaAccessibilita()){
			pickListProperties.addBodyKeyPressHandler(new BodyKeyPressHandler() {
				
				@Override
				public void onBodyKeyPress(final BodyKeyPressEvent event) {
					// TODO Auto-generated method stub
					if (EventHandler.getKey().equalsIgnoreCase("Enter")) {
						Scheduler.get().scheduleDeferred(new ScheduledCommand() {
							@Override
							public void execute() {
								ListGridRecord selectedRecord = getSelectedRecord();
								manageOnEnterPress(event, selectedRecord);
							}
						});
					}
				}
			});
		}
		
		return pickListProperties;
	}
	
	public void doOnFilterEditorSubmit(FilterEditorSubmitEvent event) {
		doOnFilterEditorSubmit(event, _instance);
	}
	
	public void doOnFilterEditorSubmit(FilterEditorSubmitEvent event, final FilteredSelectItem item) {
		item.setPickListCriteria(event.getCriteria());
		event.cancel();
		item.fetchData(new DSCallback() {
			
			@Override
			public void execute(DSResponse dsResponse, Object data, DSRequest dsRequest) {
				item.setPickListCriteria((Criteria)null);
			}
		});
	}
	
	public void manageOnCellClick(CellClickEvent event) {
		onOptionClick(event.getRecord());
	}
	
	public void manageOnEnterPress(BodyKeyPressEvent event, Record record) {
		onOptionClick(record);
	}
	
	public String manageHover(FormItem item) {
		if (_instance.isMultiple()!=null && _instance.isMultiple()){
			SelectItem lSelectItem = (SelectItem) item;
			return lSelectItem.getDisplayValue();
		} else {
			if(item.getValue() == null) {
				return null;
			} else if(item.getValue() instanceof String) {
				String valueStr = (String) item.getValue();
				if (valueStr.trim().isEmpty()) {
					return null;
				}
			} 
			return item.getDisplayValue();
		}
	}
	
	public FilteredSelectItem(String name) {
		this();
	    setName(name);        
    }

    public FilteredSelectItem(String name, String title) {
    	this();
        setName(name);
		setTitle(title);
    }
    
    public boolean getShowPickListHeader() {
    	return true;
    }
    
    public void onOptionClick(Record record) {}
    
}
