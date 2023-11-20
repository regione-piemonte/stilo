/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.google.gwt.core.client.Scheduler;
import com.google.gwt.core.client.Scheduler.ScheduledCommand;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.i18n.client.NumberFormat;
import com.smartgwt.client.data.AdvancedCriteria;
import com.smartgwt.client.data.DSCallback;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.data.DataSource;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.data.RecordList;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.BackgroundRepeat;
import com.smartgwt.client.types.DateDisplayFormat;
import com.smartgwt.client.types.ListGridEditEvent;
import com.smartgwt.client.types.ListGridFieldType;
import com.smartgwt.client.types.OperatorId;
import com.smartgwt.client.types.Overflow;
import com.smartgwt.client.types.SelectionAppearance;
import com.smartgwt.client.types.SelectionStyle;
import com.smartgwt.client.types.SortDirection;
import com.smartgwt.client.util.BooleanCallback;
import com.smartgwt.client.util.DateUtil;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.util.StringUtil;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.FormItemHoverFormatter;
import com.smartgwt.client.widgets.form.fields.CanvasItem;
import com.smartgwt.client.widgets.form.fields.FormItem;
import com.smartgwt.client.widgets.form.fields.SelectItem;
import com.smartgwt.client.widgets.form.fields.events.FormItemInitHandler;
import com.smartgwt.client.widgets.form.fields.events.ShowValueEvent;
import com.smartgwt.client.widgets.form.fields.events.ShowValueHandler;
import com.smartgwt.client.widgets.grid.CellFormatter;
import com.smartgwt.client.widgets.grid.GroupValueFunction;
import com.smartgwt.client.widgets.grid.HoverCustomizer;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.grid.ListGridRecord;
import com.smartgwt.client.widgets.grid.SortNormalizer;
import com.smartgwt.client.widgets.grid.events.CellClickEvent;
import com.smartgwt.client.widgets.grid.events.CellClickHandler;
import com.smartgwt.client.widgets.grid.events.EditCompleteEvent;
import com.smartgwt.client.widgets.grid.events.EditCompleteHandler;
import com.smartgwt.client.widgets.grid.events.RecordClickEvent;
import com.smartgwt.client.widgets.grid.events.RecordClickHandler;
import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.client.widgets.toolbar.ToolStrip;
import com.smartgwt.client.widgets.toolbar.ToolStripButton;

import it.eng.utility.ui.module.core.client.UserInterfaceFactory;
import it.eng.utility.ui.module.core.client.datasource.GWTRestDataSource;
import it.eng.utility.ui.module.core.client.i18n.I18NUtil;
import it.eng.utility.ui.module.layout.client.Layout;
import it.eng.utility.ui.module.layout.shared.bean.ListaBean;

public class GridItem extends CanvasItem {
	
	protected String nomeLista;
	
	protected VLayout layout;
	protected ListGrid grid; 
	protected String gridPkField; 
	protected String gridEmptyMessage; 
	
	protected ToolStrip toolStrip;
	protected ToolStripButton multiselectButton;	
	protected SelectItem layoutListaSelectItem;
	protected SavePreferenceWindow saveLayoutListaWindow;
	protected ToolStripButton saveLayoutListaButton;
	
	protected ToolStrip bottomListToolStrip;
	
	protected GWTRestDataSource layoutListaDS;
	protected GWTRestDataSource layoutListaDefaultDS;
	
	protected DataSource gridDataSource;	
	protected ListGridField[] gridFields;	
	protected String gridSortField;
	protected SortDirection gridSortDirection;	
	
	protected boolean canEdit = false;
	protected boolean expandable;
	protected boolean multiselect;
	
	protected boolean showPreference = true;
	protected boolean hideLayoutPreference = false;
	protected boolean alwaysShowDetailButtom = false;
	protected boolean showEditButtons = true;	
	protected boolean showNewButton = true;
	protected boolean showModifyButton = true;
	protected boolean showDeleteButton = true;
	
	protected ListGridField[] formattedFields = null;
	protected List<String> controlFields;
	
	public GridItem(String name, String nomeLista) {	
		super(name);
		this.setNomeLista(nomeLista);
		createComponent();
	}
	public GridItem(String name, String nomeLista, boolean editable, boolean expandable) {	
		super(name);
		this.setNomeLista(nomeLista);
		this.setEditable(editable);		
		this.setExpandable(expandable); 
		createComponent();
	}
	
	public GridItem(String name, String nomeLista, boolean editable) {
		this(name, nomeLista);
		this.setNomeLista(nomeLista);
		this.setEditable(editable);		
		createComponent();
	}

	protected void createComponent() {
		//setWidth("*");
		//setHeight("*");
		setColSpan("*");
		setEndRow(true);
		setStartRow(true);
		setShowTitle(false);
		setShowHint(false);
		setShowIcons(false);
		// this is going to be an editable data item             
		setShouldSaveValue(true);
		addShowValueHandler(new ShowValueHandler() {                 
			@Override                 
			public void onShowValue(ShowValueEvent event) {                     
				manageOnShowValue(event.getDataValueAsRecordList());
			}             
		}); 
		setAutoDestroy(true); // per eliminare automaticamente il canvas quando elimino il canvasItem
		setInitHandler(new FormItemInitHandler() {
			@Override
			public void onInit(FormItem item) {
				init(item);
			}
		});
	}
	
	protected void manageOnShowValue(RecordList data) {
		setData(data);
	}
	
	public void init(FormItem item) {
		grid = buildGrid();
		grid.setData((RecordList) item.getValue());
		
		// per evitare l'effetto blink degli scroll che compaiono e scompaiono
		grid.setSizeMayChangeOnRedraw(true);
		grid.setRedrawOnResize(true);
		grid.setLeaveScrollbarGap(true);
		grid.setBodyOverflow(Overflow.SCROLL);
//		grid.setShowCustomScrollbars(false);
		
		layoutListaDS = UserInterfaceFactory.getPreferenceDataSource();
		layoutListaDS.addParam("prefKey", Layout.getConfiguredPrefKeyPrefix() + getNomeLista() + ".layout.grid");		

		layoutListaDefaultDS = UserInterfaceFactory.getPreferenceDataSource();
		layoutListaDefaultDS.addParam("userId", "DEFAULT");
		layoutListaDefaultDS.addParam("prefKey", Layout.getConfiguredPrefKeyPrefix() + getNomeLista() + ".layout.grid");			
		layoutListaDefaultDS.addParam("prefName", "DEFAULT");
		
		saveLayoutListaWindow = new SavePreferenceWindow(I18NUtil.getMessages().saveLayoutButton_title(), layoutListaDS, true, true, new SavePreferenceRecordAction() {			
			@Override
			public void execute(Record record) {
				final String prefName = record != null ? record.getAttribute("prefName") : null;
				final boolean flgPubblica = record != null && record.getAttributeAsBoolean("flgPubblica") != null && record.getAttributeAsBoolean("flgPubblica");				
				if (prefName != null && !"".equals(prefName)) {
					AdvancedCriteria criteriaLayoutLista = new AdvancedCriteria();        
					if(flgPubblica) {
						String idDominio = Layout.getIdDominio();									
						criteriaLayoutLista.addCriteria("userId", OperatorId.EQUALS, "PUBLIC." + idDominio);
					}
					criteriaLayoutLista.addCriteria("prefName", OperatorId.EQUALS, prefName);			
					layoutListaDS.fetchData(criteriaLayoutLista, new DSCallback() {   
						@Override  
						public void execute(DSResponse response, Object rawData, DSRequest request) {   
							Record[] data = response.getData();  			                
//			                Record layoutLista = new Record();
//							layoutLista.setAttribute("viewState", list.getViewState());
//							layoutLista.setAttribute("detailAuto", getDetailAuto());																               
							if (data.length != 0) {   
								Record record = data[0];    			                	
//			                	record.setAttribute("value", JSON.encode(layoutLista.getJsObj(), encoder));		
								record.setAttribute("value", grid.getViewState());
								layoutListaDS.updateData(record, new DSCallback() {
									
									@Override
									public void execute(DSResponse response, Object rawData, DSRequest request) {
										if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
											final Record record = response.getData()[0];
											layoutListaSelectItem.fetchData(new DSCallback() {
												
												@Override
												public void execute(DSResponse response, Object rawData, DSRequest request) {
													layoutListaSelectItem.setValue(record.getAttribute("key"));								
												}
											});																				
										}
									}
								});
							} else {
								Record record = new Record();   
								if (flgPubblica) {
									String idDominio = Layout.getIdDominio();									
									record.setAttribute("userid", "PUBLIC." + idDominio);
								}
								record.setAttribute("prefName", prefName);
//			                    record.setAttribute("value", JSON.encode(layoutLista.getJsObj(), encoder));
								record.setAttribute("value", grid.getViewState());
								record.setAttribute("flgAbilDel", true);
								layoutListaDS.addData(record, new DSCallback() {
									
									@Override
									public void execute(DSResponse response, Object rawData, DSRequest request) {										
										if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
											final Record record = response.getData()[0];
											layoutListaSelectItem.fetchData(new DSCallback() {
												
												@Override
												public void execute(DSResponse response, Object rawData, DSRequest request) {
													layoutListaSelectItem.setValue(record.getAttribute("key"));								
												}
											});																				
										}														
									}
								});
							} 			                
						}   
					});     					
				}   
			}
		});				

		layoutListaSelectItem = new SelectItem("prefName");  
		layoutListaSelectItem.setValueField("key");  
		layoutListaSelectItem.setDisplayField("displayValue");      
		layoutListaSelectItem.setTitle(I18NUtil.getMessages().layoutSelectItem_title()); 
		layoutListaSelectItem.setItemHoverFormatter(new FormItemHoverFormatter() {			
			@Override
			public String getHoverHTML(FormItem item, DynamicForm form) {
				return (String) layoutListaSelectItem.getValue();
			}
		});
		
		final ListGrid layoutListaPickListProperties = new ListGrid();   
		layoutListaPickListProperties.setEmptyMessage(I18NUtil.getMessages().list_emptyMessage());
		layoutListaPickListProperties.setShowHeader(false);
//		layoutListaPickListProperties.setCanRemoveRecords(true); 		 
		//apply the selected preference from the SelectItem   				
		layoutListaPickListProperties.addCellClickHandler(new CellClickHandler() {			
			@Override
			public void onCellClick(CellClickEvent event) {
				boolean isRemoveField = isAbilToRemovePreference(event.getRecord()) && event.getColNum() == 0;
				if(!isRemoveField) {	
					String userId = (String) event.getRecord().getAttribute("userid");
					String preferenceName = (String) event.getRecord().getAttribute("prefName");      
					if(preferenceName != null && !"".equals(preferenceName)) { 
						AdvancedCriteria criteria = new AdvancedCriteria();        
						criteria.addCriteria("userId", OperatorId.EQUALS, userId);
						criteria.addCriteria("prefName", OperatorId.EQUALS, preferenceName); 
						layoutListaDS.fetchData(criteria, new DSCallback() {   
							@Override  
							public void execute(DSResponse response, Object rawData, DSRequest request) {   
								Record[] data = response.getData();   
								if (data.length > 0 && data[0].getAttribute("value") != null) {  										    
//	                        		Record layoutLista = new Record(JSON.decode(data[0].getAttributeAsString("value")));                        	
//	                        		list.setViewState(layoutLista.getAttributeAsString("viewState"));
//	                        		setDetailAuto(layoutLista.getAttributeAsBoolean("detailAuto"));
									grid.setViewState(data[0].getAttributeAsString("value"));   
									redrawRecordButtons();
								}   
							}   
						});
					} else {
						layoutListaDefaultDS.fetchData(null, new DSCallback() {   
							@Override  
							public void execute(DSResponse response, Object rawData, DSRequest request) {   
								Record[] data = response.getData();   
								if (data.length > 0 && data[0].getAttribute("value") != null) {   										    
//	                       		 	Record layoutLista = new Record(JSON.decode(data[0].getAttributeAsString("value")));                        	
//	                        		list.setViewState(layoutLista.getAttributeAsString("viewState"));
//	                        		setDetailAuto(layoutLista.getAttributeAsBoolean("detailAuto"));
									grid.setViewState(data[0].getAttributeAsString("value"));  
									redrawRecordButtons();										
								}   
							}   
						});
					}
				}
			}
		});
		layoutListaSelectItem.setPickListProperties(layoutListaPickListProperties);  
		layoutListaSelectItem.setOptionDataSource(layoutListaDS);      
//		layoutListaSelectItem.setAllowEmptyValue(true);

		ListGridField layoutListaKeyField = new ListGridField("key");
		layoutListaKeyField.setHidden(true);
		ListGridField layoutListaDisplayValueField = new ListGridField("displayValue");
		layoutListaDisplayValueField.setWidth("100%");
		ListGridField layoutListaRemoveField = new ListGridField("remove");
		layoutListaRemoveField.setType(ListGridFieldType.ICON);
		layoutListaRemoveField.setWidth(18);
		layoutListaRemoveField.setCellFormatter(new CellFormatter() {			
			@Override
			public String format(Object value, ListGridRecord record, int rowNum, int colNum) {
				if (isAbilToRemovePreference(record)) {							
					return "<img src=\"images/buttons/remove.png\" height=\"16\" width=\"16\" align=MIDDLE/>";
				} else {
					return null;
				}	
			}
		});
//		layoutListaRemoveField.setIsRemoveField(true);
		layoutListaRemoveField.addRecordClickHandler(new RecordClickHandler() {

			@Override
			public void onRecordClick(final RecordClickEvent event) {				
				if (isAbilToRemovePreference(event.getRecord())) {							
					SC.ask(I18NUtil.getMessages().askDeletePreference(), new BooleanCallback() {
						
						@Override
						public void execute(Boolean value) {
							if(value) {
								final String key = event.getRecord().getAttribute("key");
								layoutListaDS.removeData(event.getRecord(), new DSCallback() {

									@Override
									public void execute(DSResponse response, Object rawData, DSRequest request) {
										String value = (String) layoutListaSelectItem.getValue();
										if (key != null && value != null && key.equals(value)) {
											layoutListaSelectItem.setValue((String) null);
										}
									}
								});
							}
						}
					});
				}
			}
		});
		layoutListaSelectItem.setPickListFields(layoutListaKeyField, layoutListaRemoveField, layoutListaDisplayValueField);
		
		saveLayoutListaButton = new ToolStripButton(I18NUtil.getMessages().saveLayoutButton_title());   
		saveLayoutListaButton.setIcon("buttons/save.png"); 
		saveLayoutListaButton.setIconSize(16); 
		saveLayoutListaButton.setAutoFit(true);   
		saveLayoutListaButton.setPrompt(I18NUtil.getMessages().saveLayoutListaButton_prompt());       		
		saveLayoutListaButton.addClickHandler(new ClickHandler() {   
			public void onClick(ClickEvent event) {   
				if ((!saveLayoutListaWindow.isDrawn()) || (!saveLayoutListaWindow.isVisible())) {
					saveLayoutListaWindow.getForm().clearValues();
					saveLayoutListaWindow.getForm().setValue((String) layoutListaSelectItem.getValue());									
					saveLayoutListaWindow.markForRedraw();
					saveLayoutListaWindow.show();
				}   					
			}   
		});
		
		List<ToolStripButton> customEditButtons = buildCustomEditButtons();
		List<Canvas> customEditCanvas = buildCustomEditCanvas();
		List<FormItem> customFormItem = buildCustomFormItem();
		
		toolStrip = new ToolStrip();
		toolStrip.setBackgroundColor("transparent");
		toolStrip.setBackgroundImage("blank.png");
		toolStrip.setBackgroundRepeat(BackgroundRepeat.REPEAT_X);
		toolStrip.setBorder("0px");
		toolStrip.setWidth100();           
		toolStrip.setHeight(30);
		
		if(customEditButtons != null) {
			for(ToolStripButton editButton : customEditButtons) {
				toolStrip.addButton(editButton);
			}
		}
		if(customEditCanvas != null) {
			for(Canvas editCanvas : customEditCanvas) {
				toolStrip.addMember(editCanvas);
			}
		}
		if(customFormItem != null) {
			for(FormItem formItem : customFormItem) {
				toolStrip.addFormItem(formItem);
			}
		}
		
		if (showMultiselectButton()) {			
			multiselectButton = new ToolStripButton();
			multiselectButton.setIcon("buttons/multiselect.png");
			multiselectButton.setIconSize(16);
			multiselectButton.setPrompt(I18NUtil.getMessages().multiselectOnButton_prompt());
			multiselectButton.addClickHandler(new ClickHandler() {
	
				@Override
				public void onClick(ClickEvent event) {
					if (getMultiselect()) {
						setMultiselect(false);
					} else {
						setMultiselect(true);
					}
				}
			});
			toolStrip.addButton(multiselectButton);
		}
		
		toolStrip.addFill();
		toolStrip.addFormItem(layoutListaSelectItem);
		toolStrip.addButton(saveLayoutListaButton); 		
				
		layout = buildLayout();
		if(layout.getPadding() == null) {
			layout.setPadding(1); // aggiunto per evitare l'effetto blink degli scroll che compaiono e scompaiono
		}
		if((customEditButtons != null && customEditButtons.size() > 0) || 
		   (customEditCanvas != null && customEditCanvas.size() > 0) || 
		   isShowPreference()) {
			layout.addMember(toolStrip);
			if (isHideLayoutPreference()) {
				layoutListaSelectItem.hide();
				saveLayoutListaButton.hide();
			}
		}
		layout.addMember(grid);			
		if (getGridMultiselectButtons() != null && getGridMultiselectButtons().length > 0) {
			bottomListToolStrip = new ToolStrip();
			bottomListToolStrip.setWidth100();
			bottomListToolStrip.setHeight(30);
			bottomListToolStrip.setBackgroundColor("transparent");
			bottomListToolStrip.setBackgroundImage("blank.png");
			bottomListToolStrip.setBackgroundRepeat(BackgroundRepeat.REPEAT_X);
			bottomListToolStrip.setBorder("0px");
			for (GridMultiToolStripButton lToolStripButton : getGridMultiselectButtons()) {
				bottomListToolStrip.addButton(lToolStripButton);
			}
			bottomListToolStrip.addFill();
			layout.addMember(bottomListToolStrip);			
		}
		
		((CanvasItem)item).setCanvas(layout);			
		
		if (showMultiselectButton()) {
			setMultiselect(true);
		}
			
		if(isShowPreference()) {
			AdvancedCriteria criteriaLayoutLista = new AdvancedCriteria();        
			criteriaLayoutLista.addCriteria("prefName", OperatorId.EQUALS, "DEFAULT");	
			layoutListaDS.fetchData(criteriaLayoutLista, new DSCallback() {   
				@Override  
				public void execute(DSResponse response, Object rawData, DSRequest request) {   
					Record[] data = response.getData();   
					if (data.length != 0) {   
						Record record = data[0];         
						grid.setViewState(record.getAttributeAsString("value"));                	
						layoutListaSelectItem.setValue("DEFAULT");		
						redrawRecordButtons();
					} else {
						layoutListaDefaultDS.fetchData(null, new DSCallback() {      
							@Override  
							public void execute(DSResponse response, Object rawData, DSRequest request) {   
								Record[] data = response.getData();   
								if (data.length != 0) {   
									Record record = data[0];         
									grid.setViewState(record.getAttributeAsString("value"));    
									layoutListaSelectItem.setValue((String) null);		
									redrawRecordButtons();
								} 
							}   
						});                    
					}				
				}   
			});     
		}
	}
	
	public boolean isAbilToRemovePreference(Record record) {
		return (record.getAttribute("prefName") != null && !"".equals(record.getAttributeAsString("prefName")) && !"DEFAULT".equalsIgnoreCase(record.getAttributeAsString("prefName"))) && 
			   (record.getAttributeAsBoolean("flgAbilDel") != null && record.getAttributeAsBoolean("flgAbilDel"));
	}
	
	protected boolean showMultiselectButton() {
		return getGridMultiselectButtons() != null && getGridMultiselectButtons().length > 0;
	}
	
	protected GridMultiToolStripButton[] getGridMultiselectButtons() {
		return new GridMultiToolStripButton[] {};
	}
	
	public void setMultiselect(Boolean multiselect) {
		this.multiselect = multiselect;
		if (multiselect) {
			grid.deselectAllRecords();
			grid.setShowSelectedStyle(false);
			multiselectButton.setIcon("buttons/multiselect_off.png");
			multiselectButton.setPrompt(I18NUtil.getMessages().multiselectOffButton_prompt());
			if (isDisableGridRecordComponent()) {
				grid.setSelectionAppearance(SelectionAppearance.CHECKBOX);
				grid.setSelectionType(SelectionStyle.SIMPLE);
			} else {
				grid.setSelectionAppearance(SelectionAppearance.ROW_STYLE);
				grid.setSelectionType(SelectionStyle.NONE);
			}
		} else {
			grid.deselectAllRecords();
			grid.setShowSelectedStyle(true);
			multiselectButton.setIcon("buttons/multiselect.png");
			multiselectButton.setPrompt(I18NUtil.getMessages().multiselectOnButton_prompt());
			grid.setSelectionAppearance(SelectionAppearance.ROW_STYLE);
			grid.setSelectionType(SelectionStyle.SINGLE);
		}
		Scheduler.get().scheduleDeferred(new ScheduledCommand() {

			@Override
			public void execute() {
				redrawMultiselectButtons();
				grid.markForRedraw();
			}
		});
	}

	public void redrawMultiselectButtons() {
		if (multiselect) {
			for (MultiToolStripButton lToolStripButton : getGridMultiselectButtons()) {
				if (lToolStripButton.toShow()) {
					lToolStripButton.show();
				} else {
					lToolStripButton.hide();
				}
			}
		} else {
			for (MultiToolStripButton lToolStripButton : getGridMultiselectButtons()) {
				lToolStripButton.hide();
			}
		}
	}

	public Boolean getMultiselect() {
		return multiselect;
	}
	
	protected VLayout buildLayout() {
		return new VLayout();
	}
	
	public ListGrid buildGrid() {
		ListGrid grid = new ListGrid() {	
			
			@Override
			protected String getBaseStyle(ListGridRecord record, int rowNum, int colNum) {
				String baseStyle = getGridBaseStyle(record, rowNum, colNum);
				if(baseStyle != null) {
					return baseStyle;
				}
				return super.getBaseStyle(record, rowNum, colNum);
			}			
			
			@Override
			public boolean canExpandRecord(ListGridRecord record, int rowNum) {
				return canExpandGridRecord(record, rowNum, this);
			}
			
			@Override  
			protected Canvas getExpansionComponent(final ListGridRecord record) {  
				return createExpansionComponent(record);
			}
			
			@Override  
			protected Canvas createRecordComponent(ListGridRecord record, Integer colNum) {   						
				return createGridRecordComponent(this, record, colNum);
			}
			
//			@Override
//	        public Canvas updateRecordComponent(ListGridRecord record, Integer colNum, Canvas component,
//	                boolean recordChanged) {
//	            if(component != null && component instanceof UpdateableRecordComponent && recordChanged) {
//	            	((UpdateableRecordComponent) component).updateComponent(record);
//	                return component;
//	            }
//	            else {
//	                return super.updateRecordComponent(record, colNum, component, recordChanged);
//	            }
//	        }
		};		
		grid.setWidth100();				
		grid.setHeight100();
		grid.setDataPageSize(25);
		grid.setAlternateRecordStyles(true);		
		grid.setWrapCells(true);
		grid.setShowEmptyMessage(true);
		if(gridEmptyMessage != null && !"".equals(gridEmptyMessage)) {
			grid.setEmptyMessage(gridEmptyMessage);
		} else {
			grid.setEmptyMessage(I18NUtil.getMessages().list_emptyMessage());
		}
		grid.setLeaveScrollbarGap(true);	
		grid.setBodyOverflow(Overflow.SCROLL);
		grid.setFixedRecordHeights(false);
		grid.setCanReorderFields(true);
		grid.setCanResizeFields(true);
		grid.setCanReorderRecords(true);
		grid.setCanHover(true);		
		grid.setAutoFetchData(false);
		if (isDisableGridRecordComponent()){
			grid.setShowRecordComponents(false);
			grid.setShowRecordComponentsByCell(false);
			grid.setRecordComponentPoolingMode(null);
			grid.setPoolComponentsPerColumn(null);
		} else {
			grid.setShowRecordComponents(true);
			grid.setShowRecordComponentsByCell(true);
//			grid.setRecordComponentPoolingMode(RecordComponentPoolingMode.RECYCLE);
//			grid.setPoolComponentsPerColumn(true);
		}
		grid.setShowAllRecords(true);
//		grid.setAutoFitFieldWidths(true);
//		grid.setAutoFitWidthApproach(AutoFitWidthApproach.BOTH);
//		grid.setAutoFitMaxWidth(100);		
//		grid.setAutoFitMaxWidth(250);	
		if (isEditable()){
			grid.setCanEdit(true);
			grid.setEditEvent(ListGridEditEvent.CLICK);
//			grid.setEditByCell(true);					
		}
		if (isExpandable()){
			grid.setCanExpandRecords(true);
		}	
//		grid.setRowEndEditAction(com.smartgwt.client.types.ListGridEditEvent.CLICK); 
		grid.setCanAutoFitFields(false);		
//		grid.setNoDoubleClicks(true);							
		grid.setCanDragRecordsOut(true);   				
//		grid.setShowHoverComponents(true);
		grid.setShowHeaderContextMenu(true);					
		grid.setDateFormatter( DateDisplayFormat.TOEUROPEANSHORTDATE); 
		grid.setDatetimeFormatter( DateDisplayFormat.TOEUROPEANSHORTDATETIME );								
		grid.setTop(10);	
		
		if (gridDataSource != null)
			grid.setDataSource(gridDataSource);				
		if (gridFields != null)
			grid.setFields(gridFields);
		if (gridSortField != null)
			grid.setSortField(gridSortField);
		if(gridSortDirection != null) {
			grid.setSortDirection(gridSortDirection);
		} else {
			grid.setSortDirection(SortDirection.ASCENDING);
		}
		grid.setValidateOnChange(true);				
		grid.addEditCompleteHandler(new EditCompleteHandler() {
			@Override
			public void onEditComplete(EditCompleteEvent event) {
				manageControlloEditComplete(event);
			}
		});
		return grid;
	}
	
	public void setGridEmptyMessage(String emptyMessage) {
		this.gridEmptyMessage = emptyMessage;	
		if(getGrid() != null) {
			if(gridEmptyMessage != null && !"".equals(gridEmptyMessage)) {
				getGrid().setEmptyMessage(gridEmptyMessage);
			} else {
				getGrid().setEmptyMessage(I18NUtil.getMessages().list_emptyMessage());
			}
		}
	}
	
	public boolean canExpandGridRecord(ListGridRecord record, int rowNum, ListGrid listGrid) {
		return listGrid.canExpandRecord(record, rowNum);
	}
	
	public void manageControlloEditComplete(EditCompleteEvent event) {
		
	}
	
//	public void refreshGridRecordComponents() {
//		for (ListGridRecord record : grid.getRecords()) {
//			grid.refreshRecordComponent(grid.getRecordIndex(record));
//		}
//	}
	
	public void refreshRows() {
		for (ListGridRecord record : grid.getRecords()) {
			grid.refreshRow(grid.getRecordIndex(record));
		}
	}
	
	public void setData(RecordList data) {
		grid.deselectAllRecords();
		if (data != null) {
			grid.setData(data);				
		} else {
			grid.setData(new RecordList());
		}
		updateGridItemValue();  
	}
	
	public RecordList getData() {
		return grid.getDataAsRecordList();				
	}
	
	public void addData(Record record) {
		grid.addData(record);	
		grid.validateRow(grid.getDataAsRecordList().getLength()-1);
		updateGridItemValue();
	}
	
	public void addDataAndRefresh(final Record record) {
		addData(record);	
		grid.refreshRow(grid.getRecordIndex(record));
//		grid.refreshRecordComponent(grid.getRecordIndex(record));
//		grid.invalidateRecordComponents(); 
		Scheduler.get().scheduleDeferred(new ScheduledCommand() {
    		
			@Override
			public void execute() {
				grid.selectSingleRecord(record);
			}
    	});	
	}
	
	public void updateData(Record record, Record oldRecord) {		
		RecordList data = grid.getDataAsRecordList();		
		int index = data.findIndex(getGridPkField(), oldRecord.getAttribute(getGridPkField()));		
		ListGridRecord newRecord = new ListGridRecord(record.getJsObj());
		if (record.getAttributeAsBoolean("_canEdit")) 
			newRecord.setAttribute("_canEdit", true);
		data.set(index, newRecord);
		setData(data);											
	}
	
	public void updateDataAndRefresh(final Record record, Record oldRecord) {
		updateData(record, oldRecord);
		grid.refreshRow(grid.getRecordIndex(oldRecord));
//		grid.refreshRecordComponent(grid.getRecordIndex(oldRecord));
//		grid.invalidateRecordComponents();
		Scheduler.get().scheduleDeferred(new ScheduledCommand() {
    		
			@Override
			public void execute() {
				grid.selectSingleRecord(record);
			}
    	});		
	}
	
	public void clearValue() {
		grid.setData(new Record[0]);
		updateGridItemValue();
	}
	
	protected void updateGridItemValue() {
//		setHeight(((grid.getDataAsRecordList().getLength()+1)*30)+50); 
		redrawRecordButtons();
		RecordList data = grid.getDataAsRecordList();   
		CanvasItem item = layout.getCanvasItem();
		if (data != null) { 
			item.storeValue(data);                             
		} else {                                 
			item.storeValue(new Record[0]);                             
		}       
	}
	
	protected List<ControlListGridField> getButtonsFields() {
		return new ArrayList<ControlListGridField>();	
	}
	
	protected void setCanEditForEachGridField(ListGridField field) {

	}
	
	public void setGridFields(String nomeListaConfig, ListGridField... fields) {
		
		int length = fields.length;
		length += getButtonsFields() != null ? getButtonsFields().size() : 0; //buttons					
		
		ListaBean configLista = nomeListaConfig != null ? Layout.getListConfig(nomeListaConfig) : null;	
		
		formattedFields = new ListGridField[length];
		controlFields = new ArrayList<String>();
		
		int count = 0;
		
		for (final ListGridField field : fields){	
		
			String fieldName = field.getName();			
			
			boolean skip = false;
			for(ControlListGridField buttonField : getButtonsFields()) {
				if(fieldName.equals(buttonField.getName())) {
					skip = true;
					break;
				}
			}		
			
			if(!skip) {
				
				try {	
					
					setCanEditForEachGridField(field);
					
					if(field instanceof ControlListGridField) {
						controlFields.add(fieldName);
					}
					
					field.setAlign(Alignment.CENTER);					
					if(field.getTitle() != null && !"".equals(field.getTitle().trim())) {
						field.setPrompt(field.getTitle());					
					}			
					
					if(configLista != null) {
						if(configLista.getColonneOrdinabili() != null) {
//							if(configLista.getColonneDefault().contains(fieldName)) {
//								field.setHidden(false);
//							} else {
//								field.setHidden(true);
//							}
							if(configLista.getColonneOrdinabili().contains(fieldName)) {
								field.setCanSort(true);
							} else {
								field.setCanSort(false);
							}		
						}
					}
					
					//Recupero il tipo relativo
					ListGridFieldType fieldType = field.getType();
					
					if (fieldType == null || fieldType.equals(ListGridFieldType.TEXT)) {
						if(field.getAttribute("custom") == null || !field.getAttributeAsBoolean("custom")) {
							field.setCellAlign(Alignment.LEFT);	
						}
					} else if (fieldType.equals(ListGridFieldType.INTEGER) 
							|| fieldType.equals(ListGridFieldType.BINARY) 
							|| fieldType.equals(ListGridFieldType.FLOAT)) {
						field.setCellAlign(Alignment.RIGHT);
					} else if (fieldType.equals(ListGridFieldType.DATE) 
							|| fieldType.equals(ListGridFieldType.TIME)) {
						field.setCellAlign(Alignment.CENTER);										
					} else if (fieldType.equals(ListGridFieldType.IMAGE) 
							|| fieldType.equals(ListGridFieldType.IMAGEFILE) 
							|| fieldType.equals(ListGridFieldType.LINK) 
							|| fieldType.equals(ListGridFieldType.ICON)) {
						field.setCellAlign(Alignment.CENTER);
					} else {
						field.setCellAlign(Alignment.LEFT);
					}
					
					if (fieldType == null || fieldType.equals(ListGridFieldType.TEXT)) {
						if(field.getAttribute("custom") == null || !field.getAttributeAsBoolean("custom")) {
							field.setCellFormatter(new CellFormatter() {							
								@Override
								public String format(Object value, ListGridRecord record, int rowNum,
										int colNum) {
									if(record != null) {
										record.setAttribute("realValue"+colNum, value);
									}
									if (value==null) return null;
									String lStringValue = value.toString();
									if (lStringValue.length()>Layout.getGenericConfig().getMaxValueLength()){
										return lStringValue.substring(0,Layout.getGenericConfig().getMaxValueLength()) + "...";
									} else return lStringValue;
								}
							});
							if(field.getEscapeHTML() != null && field.getEscapeHTML()) {
								field.setHoverCustomizer(new HoverCustomizer() {
									@Override
									public String hoverHTML(Object value, ListGridRecord record, int rowNum,
											int colNum) {
										Object realValue = record != null ? record.getAttribute("realValue"+colNum) : null;								
										return StringUtil.asHTML((realValue != null) ? (String) realValue : (String) value);
									}
								});
							} else {
								field.setHoverCustomizer(new HoverCustomizer() {
									@Override
									public String hoverHTML(Object value, ListGridRecord record, int rowNum,
											int colNum) {
										Object realValue = record != null ? record.getAttribute("realValue"+colNum) : null;								
										return (realValue != null) ? (String) realValue : (String) value;
									}
								});
							}
						} else if(field.getEscapeHTML() != null && field.getEscapeHTML()) {
							field.setHoverCustomizer(new HoverCustomizer() {
								@Override
								public String hoverHTML(Object value, ListGridRecord record, int rowNum,
										int colNum) {
									return StringUtil.asHTML((String) value);
								}
							});
						} 
					} else if (fieldType.equals(ListGridFieldType.INTEGER)) {	
						field.setSortNormalizer(new SortNormalizer() {			
							@Override
							public Object normalize(ListGridRecord record, String fieldName) {
								String value = record.getAttribute(fieldName);
								return value != null && !"".equals(value) ? Long.parseLong(value.replace(".", "")) : 0;																						
							}
						});		
					} else if (fieldType.equals(ListGridFieldType.FLOAT)) {	
						field.setSortNormalizer(new SortNormalizer() {							
							@Override
							public Object normalize(ListGridRecord record, String fieldName) {
								String pattern = "#,##0.00";
								float floatValue = 0;
								String value = record.getAttribute(fieldName);
								if(value != null && !"".equals(value)) {
									floatValue = new Float(NumberFormat.getFormat(pattern).parse((String) value)).floatValue();			
								}
								return floatValue;
							}
						});
					} else if (fieldType.equals(ListGridFieldType.DATE)) {			
						LinkedHashMap<String, String> groupingModes = new LinkedHashMap<String, String>();
						groupingModes.put("date", I18NUtil.getMessages().groupingModePerGiorno_title()); 
						groupingModes.put("month", I18NUtil.getMessages().groupingModePerMese_title());
						groupingModes.put("year", I18NUtil.getMessages().groupingModePerAnno_title());         
						field.setGroupingModes(groupingModes);  															
						field.setGroupValueFunction(new GroupValueFunction() {             
							public Object getGroupValue(Object value, ListGridRecord record, ListGridField field, String fieldName, ListGrid grid) { 
								Date date = record != null ? DateUtil.parseInput(record.getAttributeAsString(fieldName)) : null;                  								
								DateTimeFormat dateFormat = DateTimeFormat.getFormat("dd/MM/yyyy");
								if (date == null) return " ";								
								if(field.getGroupingMode() != null) {
									DateTimeFormat dateFormatYear = DateTimeFormat.getFormat("yyyy");	
									String year = dateFormatYear.format(date);
									if(field.getGroupingMode().equals("year")) {
										return year;
									} else if(field.getGroupingMode().equals("month")) {
										DateTimeFormat dateFormatMonth = DateTimeFormat.getFormat("MM");
										switch(Integer.parseInt(dateFormatMonth.format(date))) {
										case 1: return I18NUtil.getMessages().decodificaMese_1() + " " + year;
										case 2: return I18NUtil.getMessages().decodificaMese_2() + " " + year;
										case 3: return I18NUtil.getMessages().decodificaMese_3() + " " + year;
										case 4: return I18NUtil.getMessages().decodificaMese_4() + " " + year;
										case 5: return I18NUtil.getMessages().decodificaMese_5() + " " + year;
										case 6: return I18NUtil.getMessages().decodificaMese_6() + " " + year;
										case 7: return I18NUtil.getMessages().decodificaMese_7() + " " + year;
										case 8: return I18NUtil.getMessages().decodificaMese_8() + " " + year;
										case 9: return I18NUtil.getMessages().decodificaMese_9() + " " + year;
										case 10: return I18NUtil.getMessages().decodificaMese_10() + " " + year;
										case 11: return I18NUtil.getMessages().decodificaMese_11() + " " + year;
										case 12: return I18NUtil.getMessages().decodificaMese_12() + " " + year;
										}										
									} 	
								}
								return dateFormat.format(date); 								      																																			
							}
						}); 	
						field.setSortNormalizer(new SortNormalizer() {			
							@Override
							public Object normalize(ListGridRecord record, String fieldName) {
								return formatDateForSorting(record, fieldName);																									
							}
						});		
						field.setCellFormatter(new CellFormatter() {	
							@Override
							public String format(Object value, ListGridRecord record, int rowNum,
									int colNum) {
								return manageDateCellFormat(value, field, record);
							}
						});																										
					} else if (fieldType.equals(ListGridFieldType.ICON)){						
						final Map<String, String> valueHovers = field.getAttributeAsMap("valueHovers");
						if (valueHovers != null) {
							final ListGridField iconfield = field;
							field.setHoverCustomizer(new HoverCustomizer() {
								@Override
								public String hoverHTML(Object value, ListGridRecord record, int rowNum, int colNum) {
									if(record != null) return valueHovers.get(record.getAttribute(iconfield.getName()));
									else return (String) value;
								}
							});
						}
					}
					
					formattedFields[count] = field;		
					
				} catch (Exception e) {
					formattedFields[count] = field;
				}			
				
				count++;
			}
		}	
		
		if(getButtonsFields() != null) {
			for(int i = 0; i < getButtonsFields().size(); i++) {
				controlFields.add(getButtonsFields().get(i).getName());
				formattedFields[count] = getButtonsFields().get(i);
				count++;
			}					
		}				
		
		this.gridFields = formattedFields;
	}
	
	protected Object formatDateForSorting(ListGridRecord record, String fieldName) {
		String value = record != null ? record.getAttributeAsString(fieldName) : null;
		return value != null && !"".equals(value) ? DateUtil.parseInput(value) : null;
	}
	
	public String manageDateCellFormat(Object value, ListGridField field, ListGridRecord record) {
		if (value==null) return null;
		String lStringValue = value.toString();	
		if(field.getDateFormatter() != null && field.getDateFormatter().equals(DateDisplayFormat.TOEUROPEANSHORTDATE)) {									
			lStringValue = (lStringValue != null && !"".equals(lStringValue)) ? lStringValue.substring(0, 10) : "";
		}
		return lStringValue;
	}

	protected String buildImgButtonHtml(String src, String align) {
		return "<div style=\"cursor:pointer\" align=\"" + align + "\"><img src=\"images/" + src + "\" height=\"16\" width=\"16\" alt=\"\" /></div>";		
	}
	
	protected String buildImgButtonHtml(String src) {
		return "<div style=\"cursor:pointer\" align=\"center\"><img src=\"images/" + src + "\" height=\"16\" width=\"16\" alt=\"\" /></div>";		
	}

	protected String buildIconHtml(String src) {
		return "<div align=\"center\"><img src=\"images/" + src + "\" height=\"16\" width=\"16\" alt=\"\" /></div>";
	}	
	
	public ListGrid getGrid() {
		return grid;
	}

	public void setGrid(ListGrid grid) {
		this.grid = grid;
	}
	
	public String getGridPkField() {
		return gridPkField;
	}

	public void setGridPkField(String gridPkField) {
		this.gridPkField = gridPkField;
	}

	public DataSource getGridDataSource() {
		return this.gridDataSource;
	}

	public void setGridDataSource(DataSource gridDataSource) {
		this.gridDataSource = gridDataSource;
	}
	
	public ListGridField[] getGridFields() {
		return gridFields;
	}

	public void setGridFields(ListGridField... gridFields) {
		this.gridFields = gridFields;
	}
	
	public String getGridSortField() {
		return gridSortField;
	}

	public void setGridSortField(String gridSortField) {
		this.gridSortField = gridSortField;
	}
	
	public SortDirection getGridSortDirection() {
		return gridSortDirection;
	}

	public void setGridSortDirection(SortDirection gridSortDirection) {
		this.gridSortDirection = gridSortDirection;
	}
	
	public String getGridBaseStyle(ListGridRecord record, int rowNum, int colNum) {
		return null;
	}

	public boolean isDisableGridRecordComponent() {
		return true;
	}

	public Canvas createGridRecordComponent(ListGrid grid, ListGridRecord record, Integer colNum) {
		return null;
	}

	protected Canvas createExpansionComponent(final ListGridRecord record) {  
		return null;
	}
	
	public void redrawRecordButtons() {				
		try { 
			if(grid.getFieldByName("buttons")!=null) {
				grid.hideField("buttons"); } 
			} catch(Exception e) {}
		try { grid.refreshFields(); } catch(Exception e) {}					
		try { 
			if(grid.getFieldByName("buttons")!=null) {
				grid.showField("buttons"); }		
			} catch(Exception e) {}
		grid.markForRedraw();	
	}
	
	public List<ToolStripButton> buildCustomEditButtons() {
		List<ToolStripButton> buttons = new ArrayList<ToolStripButton>();		
		ToolStripButton newButton = new ToolStripButton();   
		newButton.setIcon("buttons/new.png");   
		newButton.setIconSize(16);
		newButton.setPrefix("newButton");
		newButton.setPrompt(I18NUtil.getMessages().newButton_prompt());
		newButton.addClickHandler(new ClickHandler() {	
			@Override
			public void onClick(ClickEvent event) {
				  onClickNewButton();   	
			}   
		});  
		if (isShowNewButton())
			buttons.add(newButton);
		
		
		ToolStripButton modifyButton = new ToolStripButton();   
		modifyButton.setIcon("buttons/modify.png");   
		modifyButton.setIconSize(16);
		modifyButton.setPrefix("modifyButton");
		modifyButton.setPrompt(I18NUtil.getMessages().modifyButton_prompt());
		modifyButton.addClickHandler(new ClickHandler() {	
			@Override
			public void onClick(ClickEvent event) {
				  onClickModifyButton();   	
			}   
		});  
		if (isShowModifyButton())			
			buttons.add(modifyButton);
		
		return buttons;
	}
	
	public List<Canvas> buildCustomEditCanvas() {
		return null;
	}
	
	public List<FormItem> buildCustomFormItem() {
		return null;
	}
	
	public void onClickNewButton() {};
	public void onClickModifyButton() {};
	
	@Override
	public void setCanEdit(Boolean canEdit) {
		super.setCanEdit(true);
		if(this.getCanvas() != null) {
			for(Canvas member : toolStrip.getMembers()) {
				if(member instanceof ToolStripButton) {
					if(canEdit ) { 
						if(isShowEditButtons()) { 
							member.show();						
							if (member.getPrefix() != null && member.getPrefix().equalsIgnoreCase("newButton"))
								{
									if (isShowNewButton())								
											((ToolStripButton) member).show();
									else
										((ToolStripButton) member).hide();
								}							
							if (member.getPrefix() != null && member.getPrefix().equalsIgnoreCase("modifyButton"))
								{
									if (isShowModifyButton())								
										((ToolStripButton) member).show();
									else
										((ToolStripButton) member).hide();
								}							
						} else {
							member.hide();
						}					
					}else {
						member.hide();
					}
				}
			}	
			if (isHideLayoutPreference()) {
				layoutListaSelectItem.hide();
				saveLayoutListaButton.hide();
			} else if(isShowPreference()) {
				layoutListaSelectItem.show();
				saveLayoutListaButton.show();
			}
			grid.setCanReorderRecords(canEdit);			
			redrawRecordButtons();
//			refreshGridRecordComponents();
//			for(ListGridRecord record : grid.getRecords()) {   
//				record.setCustomStyle(canEdit ? it.eng.utility.Styles.cell : it.eng.utility.Styles.cellDisabled);
//	        }  
//			grid.markForRedraw();
		}
	}

	public void removeData(Record record) {
		grid.removeData(record);	
		updateGridItemValue();
	}
	
	public RecordList getValueAsRecordList() {
		return getData();
	}
	
	public String getNomeLista() {
		return nomeLista;
	}

	public void setNomeLista(String nomeLista) {
		this.nomeLista = nomeLista;
	}
	
	public boolean isEditable() {
		return canEdit;
	}

	public void setEditable(boolean canEdit) {
		this.canEdit = canEdit;
	}
	
	public boolean isExpandable() {
		return expandable;
	}

	public void setExpandable(boolean expandable) {
		this.expandable = expandable;
	}
	
	public boolean isShowPreference() {
		return showPreference;
	}
	
	public void setShowPreference(boolean showPreference) {
		this.showPreference = showPreference;
	}
	
	public boolean isHideLayoutPreference() {
		return hideLayoutPreference;
	}
	
	public void setHideLayoutPreference(boolean hideLayoutPreference) {
		this.hideLayoutPreference = hideLayoutPreference;
	}
	public boolean isAlwaysShowDetailButtom() {
		return alwaysShowDetailButtom;
	}
	
	public void setAlwaysShowDetailButtom(boolean alwaysShowDetailButtom) {
		this.alwaysShowDetailButtom = alwaysShowDetailButtom;
	}
	public boolean isShowEditButtons() {
		return showEditButtons;
	}

	public void setShowEditButtons(boolean showEditButtons) {
		this.showEditButtons = showEditButtons;
	}
	
	public boolean isShowNewButton() {
		return showNewButton;
	}
	
	public void setShowNewButton(boolean showNewButton) {
		this.showNewButton = showNewButton;
	}
	
	public boolean isShowModifyButton() {
		return showModifyButton;
	}
	
	public void setShowModifyButton(boolean showModifyButton) {
		this.showModifyButton = showModifyButton;
	}
	
	public boolean isShowDeleteButton() {
		return showDeleteButton;
	}
	
	public void setShowDeleteButton(boolean showDeleteButton) {
		this.showDeleteButton = showDeleteButton;
	}
	
	public void manageOnDestroy() {
		if(saveLayoutListaWindow != null) {
			saveLayoutListaWindow.destroy();
		}
		if(layoutListaDS != null) {
			layoutListaDS.destroy();
		}
		if(layoutListaDefaultDS != null) {
			layoutListaDefaultDS.destroy();
		}
	} 
	
	public abstract class GridMultiToolStripButton extends MultiToolStripButton {

		private ListGrid list;
		
		public GridMultiToolStripButton(String pIcon, ListGrid pList, String pTitle) {
			this(pIcon, pList, pTitle, false);
		}
		
		public GridMultiToolStripButton(String pIcon, ListGrid pList, String pTitle, boolean pToShowTitle) {			
			super(pIcon, null, pTitle, pToShowTitle);
			this.list = pList;
		}
		
		@Override
		public ListGrid getList() {
			return list;
		}
	}
	
};
