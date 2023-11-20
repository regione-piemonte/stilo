/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.Scheduler;
import com.google.gwt.core.client.Scheduler.ScheduledCommand;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.CheckBox;
import com.smartgwt.client.data.AdvancedCriteria;
import com.smartgwt.client.data.Criterion;
import com.smartgwt.client.data.DSCallback;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.data.RecordList;
import com.smartgwt.client.data.ResultSet;
import com.smartgwt.client.data.SortSpecifier;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.DateDisplayFormat;
import com.smartgwt.client.types.FetchMode;
import com.smartgwt.client.types.GroupStartOpen;
import com.smartgwt.client.types.ListGridFieldType;
import com.smartgwt.client.types.Overflow;
import com.smartgwt.client.types.SortDirection;
import com.smartgwt.client.util.BooleanCallback;
import com.smartgwt.client.util.DateUtil;
import com.smartgwt.client.util.JSOHelper;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.IButton;
import com.smartgwt.client.widgets.ImgButton;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.events.RightMouseDownEvent;
import com.smartgwt.client.widgets.events.RightMouseDownHandler;
import com.smartgwt.client.widgets.grid.CellFormatter;
import com.smartgwt.client.widgets.grid.GroupValueFunction;
import com.smartgwt.client.widgets.grid.HeaderSpan;
import com.smartgwt.client.widgets.grid.HoverCustomizer;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.grid.ListGridFieldIfFunction;
import com.smartgwt.client.widgets.grid.ListGridRecord;
import com.smartgwt.client.widgets.grid.SortNormalizer;
import com.smartgwt.client.widgets.grid.events.CellContextClickEvent;
import com.smartgwt.client.widgets.grid.events.CellContextClickHandler;
import com.smartgwt.client.widgets.grid.events.CellOutEvent;
import com.smartgwt.client.widgets.grid.events.CellOutHandler;
import com.smartgwt.client.widgets.grid.events.CellOverEvent;
import com.smartgwt.client.widgets.grid.events.CellOverHandler;
import com.smartgwt.client.widgets.grid.events.DataArrivedEvent;
import com.smartgwt.client.widgets.grid.events.DataArrivedHandler;
import com.smartgwt.client.widgets.grid.events.HeaderClickEvent;
import com.smartgwt.client.widgets.grid.events.HeaderClickHandler;
import com.smartgwt.client.widgets.grid.events.RecordClickEvent;
import com.smartgwt.client.widgets.grid.events.RecordClickHandler;
import com.smartgwt.client.widgets.grid.events.SortChangedHandler;
import com.smartgwt.client.widgets.grid.events.SortEvent;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.client.widgets.menu.Menu;
import com.smartgwt.client.widgets.menu.MenuItem;
import com.smartgwt.client.widgets.menu.events.MenuItemClickEvent;
import com.smartgwt.client.widgets.toolbar.ToolStrip;
import com.smartgwt.client.widgets.toolbar.ToolStripButton;

import it.eng.utility.ui.module.core.client.UserInterfaceFactory;
import it.eng.utility.ui.module.core.client.datasource.GWTRestDataSource;
import it.eng.utility.ui.module.core.client.i18n.I18NUtil;
import it.eng.utility.ui.module.core.shared.message.MessageBean;
import it.eng.utility.ui.module.core.shared.message.MessageType;
import it.eng.utility.ui.module.layout.client.Layout;
import it.eng.utility.ui.module.layout.client.menu.MenuUtil;
import it.eng.utility.ui.module.layout.client.menu.ShowInMenuFunction;
import it.eng.utility.ui.module.layout.client.portal.ModalWindow;
import it.eng.utility.ui.module.layout.client.portal.Portlet;
import it.eng.utility.ui.module.layout.shared.bean.ListaBean;

public class CustomList extends ListGrid {

	protected Portlet window;

	protected CustomLayout layout;
	protected CustomList instance;	

	protected final String nomeEntita;
	protected boolean showButtons;

	protected ControlListGridField checkboxField;
	protected ControlListGridField buttonsField;
	protected ControlListGridField detailButtonField;
	protected ControlListGridField modifyButtonField;
	protected ControlListGridField deleteButtonField;
	protected ControlListGridField altreOpButtonField;
	protected ControlListGridField lookupButtonField;
	protected boolean[] abilButtons = {true, true, true, true};

	protected ListGridRecord currentRecord;
	protected ListGridRecord mouseOverRecord;

	protected int[] recordsToSelect;	
	
	protected boolean selectionUpdated;

	protected HandlerRegistration recordClickHandler;

	protected HashMap<Integer,ListGridRecord> selezione = new HashMap<Integer, ListGridRecord>();
	protected HashMap<Integer, CheckBox> checkboxList = new HashMap<Integer, CheckBox>();

	boolean canResizeReorder = true;

	protected ListGridField[] formattedFields = null;
	private String smartId = "";
	
	private boolean showMultiSelect = true;
	
	protected DettaglioWindow dettaglioWindow = null;
	protected List<String> controlFields;

//	private static Hilite[] hilites = new Hilite[] {         
//        new Hilite() {{  
//            setFieldNames("area", "gdp");  
//            setCriteria(new AdvancedCriteria(OperatorId.AND, new Criterion[] {  
//                            new Criterion("gdp", OperatorId.GREATER_THAN, 1000000),  
//                            new Criterion("area", OperatorId.LESS_THAN, 500000)}));  
//            setCssText("color:#3333FF;background-color:#CDEB8B;");  
//            setHtmlAfter(" " + Canvas.imgHTML("[SKIN]/actions/back.png"));  
//            setHtmlBefore("");
//            setHtmlValue("");
//        }}  
//    };  


	public boolean isShowMultiSelect() {
		return showMultiSelect;
	}

	public void setShowMultiSelect(boolean showMultiSelect) {
		this.showMultiSelect = showMultiSelect;
	}

	public CustomList(String nomeEntita) {
		this(nomeEntita, true);
	}

	public CustomList(final String nomeEntita, boolean showButtons) {
		instance = this;

		this.nomeEntita = nomeEntita;
		this.showButtons = showButtons;
		
		if(!isDisableRecordComponent()) {
			checkboxField = new ControlListGridField("checkboxField",  "<img src=\"images/buttons/checkboxField.png\" height=\"16\" width=\"16\" />");	
			checkboxField.setPrompt("Seleziona");
			checkboxField.setHeaderBaseStyle(it.eng.utility.Styles.hiddenHeaderButton);
			checkboxField.setWidth(25);		
			checkboxField.setHidden(true);
			checkboxField.setCanHide(false);
			checkboxField.setCanReorder(false);	
			checkboxField.setShowIfCondition(new ListGridFieldIfFunction() {				
				@Override
				public boolean execute(ListGrid grid, ListGridField field, int fieldNum) {
					return layout != null && layout.getMultiselect();
				}
			});			
			checkboxField.addRecordClickHandler(new RecordClickHandler() {				
				@Override
				public void onRecordClick(RecordClickEvent event) {
					event.cancel();
				}
			});
		}
				
		if(/*layout != null && layout.isLookup() && */isDisableRecordComponent()) {
			lookupButtonField = buildLookupButtonField();
		}
 		if (UserInterfaceFactory.isAttivaAccessibilita()){
 			smartId = SC.generateID();
 			setAriaRole("table");
 			this.initOpenDetailByRowNum(this, "openDetailByRowNum_" + smartId, Layout.isExternalPortlet);
 			this.initChangeCheckBoxRole(this, "selectRowByRowNum_" + smartId, Layout.isExternalPortlet);
 			this.initDeselectRow(this, "deselectRowByRowNum_" + smartId, Layout.isExternalPortlet);
 			setCanFocus(true);
 		} else {
		setCanFocus(false);
		setTabIndex(-1);
		}		
		setWidth100();
		setDataPageSize(25);
		setHeight100();
		setFastCellUpdates(true);
		setAlternateRecordStyles(true);		
		setWrapCells(true);
		setShowEmptyMessage(true);
		setEmptyMessage(I18NUtil.getMessages().list_noSearchMessage());
		setRecordCanSelectProperty("canSelect");
		setLeaveScrollbarGap(true);		
		setBodyOverflow(Overflow.SCROLL);
		//		setCanFreezeFields(true);		
		//		setOverflow(Overflow.VISIBLE);
		setFixedRecordHeights(false);
		setCanReorderFields(true);
		setCanResizeFields(true);
		setKeepInParentRect(true);
		setCanReorderRecords(false);
		setCanHover(true);		
		setAutoFetchData(false);
		if (isDisableRecordComponent()){
			setShowRecordComponents(false);     
			setShowRecordComponentsByCell(false);
			setRecordComponentPoolingMode(null);
			setPoolComponentsPerColumn(null);						
		} else {
			setShowRecordComponents(true);
			setShowRecordComponentsByCell(true);
//			setRecordComponentPoolingMode(RecordComponentPoolingMode.RECYCLE);
			setPoolComponentsPerColumn(true);						
		}
		
		setShowAllRecords(false);		
		setDataFetchMode(FetchMode.BASIC);
		setDrawAheadRatio(2);
		setDrawAllMaxCells(0);
		setCanAutoFitFields(false);		
		setCanEdit(false);   
//		setAlwaysShowEditors(true);		    
		setCanDragRecordsOut(true);      
		//setShowHoverComponents(true);
		setShowHeaderContextMenu(true);		
		setDateFormatter( DateDisplayFormat.TOEUROPEANSHORTDATE); 
		setDatetimeFormatter( DateDisplayFormat.TOEUROPEANSHORTDATETIME );		
		setGroupStartOpen(GroupStartOpen.values()); 
		setCanGroupBy(true);       
		setNoDoubleClicks(true); 
		setVirtualScrolling(true);      
		ResultSet resultSetProperties = new ResultSet();
		resultSetProperties.setNeverDropUpdatedRows(true);  
		resultSetProperties.setUpdatePartialCache(false);
		setDataProperties(resultSetProperties);     

		if(Layout.isFirefoxBrowser()) {
			
			addCellOverHandler(new CellOverHandler() {			
				@Override
				public void onCellOver(CellOverEvent event) {
					mouseOverRecord = event.getRecord();
					event.cancel();				
				}
			});
	
			addCellOutHandler(new CellOutHandler() {			
				@Override
				public void onCellOut(CellOutEvent event) {
					mouseOverRecord = null;
					event.cancel();				
				}
			});
			
			addRightMouseDownHandler(new RightMouseDownHandler() {			
				@Override
				public void onRightMouseDown(RightMouseDownEvent event) {
					event.cancel(); //per inibire il contextmenu del browser	
					if(layout != null && layout.getMultiselect() && getSelectedRecords().length > 0) {								
						if(getSelectedRecords().length == 1) {
							manageContextClick(getSelectedRecords()[0]);
						} else {
							manageMultiSelectContextClick();									
						}					
					} else {
						manageContextClick(mouseOverRecord);
					}
				}
			});		
			
		} else {

			addCellContextClickHandler(new CellContextClickHandler() {			
				@Override
				public void onCellContextClick(CellContextClickEvent event) {
					ListGridRecord record = event.getRecord();
					event.cancel(); //per inibire il contextmenu del browser
					if(layout != null && layout.getMultiselect() && getSelectedRecords().length > 0) {								
						if(getSelectedRecords().length == 1) {
							manageContextClick(getSelectedRecords()[0]);
						} else {
							manageMultiSelectContextClick();									
						}  	
					} else {
						manageContextClick(record);
					}							
				}
			});

		}	

		recordClickHandler = addRecordClickHandler(new RecordClickHandler() {			
			@Override
			public void onRecordClick(RecordClickEvent event) {				
				String rowClickEventSource = event.getRecord().getAttribute("rowClickEventSource") != null ? event.getRecord().getAttribute("rowClickEventSource") : "";
				if(getCanExpandRecords() == null || !getCanExpandRecords() || !(event.getFieldNum() == 0)) {
					if((!"_checkboxField".equals(event.getField().getName()) && 
						!"checkboxField".equals(event.getField().getName()) && 
						!"buttons".equals(event.getField().getName()) && 
						!"lookupButton".equals(event.getField().getName())) || !"".equals(rowClickEventSource)) {
						// se non è il check di selezione del record (prima colonna)					
						manageRecordClick(event.getRecord(), event.getRecordNum());
					}
				}
			}
		});		
		
		addDataArrivedHandler(new DataArrivedHandler() {
			
			@Override
			public void onDataArrived(DataArrivedEvent event) {
				
				Boolean isCMMI = UserInterfaceFactory.getParametroDB("CLIENTE") != null && "CMMI".equals(UserInterfaceFactory.getParametroDB("CLIENTE"))
						? true : false;
				if(event.getStartRow() == -1) {
					setEmptyMessage(I18NUtil.getMessages().list_noSearchMessage());
					if(layout != null) layout.hideFiltroAttivoImg();	
				} else {					
					if(layout != null && layout.getSearchCriteria() != null) {
						AdvancedCriteria lAdvancedCriteria = layout.getSearchCriteria();
						if (lAdvancedCriteria != null) {
							for (Criterion crit : lAdvancedCriteria.getCriteria()) {
								if(crit.getFieldName() != null && "ricercaMailArchiviate".equalsIgnoreCase(crit.getFieldName())) {
									if("solo_archiviate".equals(crit.getValueAsString())) {
										if(isCMMI) {
											setEmptyMessage(I18NUtil.getMessages().list_emptyMessage_ricercaMailArchiviateFilter_CMMI());
										} else {
											setEmptyMessage(I18NUtil.getMessages().list_emptyMessage_ricercaMailArchiviateFilter());
										}
									} else {
										if(isCMMI) {
											setEmptyMessage(I18NUtil.getMessages().list_emptyMessage_ricercaMailArchiviate_corrente_FilterCMMI());
										} else {
											setEmptyMessage(I18NUtil.getMessages().list_emptyMessage_ricercaMailArchiviate_corrente_Filter());
										}
									}
								}
							}
							
						}
					}
				}					
				manageDataArrived();				
			}
		});	

		addHeaderClickHandler(new HeaderClickHandler() {			
			@Override
			public void onHeaderClick(HeaderClickEvent event) {
				final ListGridField field = getField(event.getFieldNum());	
				if(!isDisableRecordComponent() && "checkboxField".equals(field.getName())) {
					Menu menu = new Menu();
					MenuItem selezionaTuttiMenuItem = new MenuItem("Seleziona tutti");
					selezionaTuttiMenuItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {						
						@Override
						public void onClick(MenuItemClickEvent event) {
							selezionaTutti();
						}
					});
					MenuItem deselezionaTuttiMenuItem = new MenuItem("Deseleziona tutti");
					deselezionaTuttiMenuItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {						
						@Override
						public void onClick(MenuItemClickEvent event) {
							deselezionaTutti();
						}
					});
					menu.setItems(selezionaTuttiMenuItem, deselezionaTuttiMenuItem);
					menu.showContextMenu();
				}
			}
		});
		
		addSortChangedHandler(new SortChangedHandler() {
			
			@Override
			public void onSortChanged(SortEvent event) {
				if(getDataSource() != null && getDataSource() instanceof GWTRestDataSource) {
					String orderBy = null;
					SortSpecifier[] sortBy = event.getSortSpecifiers();
					if(sortBy != null) {
						for(int i = 0; i < sortBy.length; i++) {
							boolean desc = (sortBy[i].getSortDirection() != null && sortBy[i].getSortDirection() == SortDirection.DESCENDING) ? true : false;
							String fieldOrd = (desc ? "-" : "+") + sortBy[i].getField();
							if(orderBy == null) {
								orderBy = fieldOrd;
							} else {
								orderBy += ";" + fieldOrd;
							}
						}
					}
					((GWTRestDataSource)getDataSource()).addParam("orderBy", orderBy);
				}
			}
		});

	}
	
	protected int getButtonsFieldWidth() {
		return 100;
	}
	
	protected boolean isRecordAbilToView(ListGridRecord record) {
		return true;
	}
	
	protected boolean isRecordAbilToMod(ListGridRecord record) {
		return true;
	}

	protected boolean isRecordAbilToDel(ListGridRecord record) {
		return true;
	}	
	
	protected boolean isRecordAbilToAltreOp(ListGridRecord record) {
		return true;
	}	
	
	public void clearSelezione() {
		selezione = new HashMap<Integer, ListGridRecord>();	
	}

	public void clearCheckBoxList() {
		checkboxList = new HashMap<Integer, CheckBox>();		
	}

	@Override
	public void setHeaderSpans(final HeaderSpan... headerSpans)
	throws IllegalStateException {
//		setHeaderHeight(40);          
		Scheduler.get().scheduleDeferred(new ScheduledCommand() {
			@Override
			public void execute() {	
				for (int i = 0; i < headerSpans.length; i++) {
					for(String fieldName : headerSpans[i].getFields()) {
						setFieldTitle(fieldName, headerSpans[i].getTitle() + " - " + getField(fieldName).getTitle());
					}
				}	
				refreshFields();	
				markForRedraw();
			}
		});	
	}

	public HeaderSpan[] getHeaderSpans() throws IllegalStateException {
		return convertToHeaderSpanArray(getAttributeAsJavaScriptObject("headerSpans"));
	}

	private static HeaderSpan[] convertToHeaderSpanArray(JavaScriptObject nativeArray) {
		if (nativeArray == null) {
			return new HeaderSpan[]{};
		}
		JavaScriptObject[] componentsj = JSOHelper.toArray(nativeArray);
		HeaderSpan[] objects = new HeaderSpan[componentsj.length];
		for (int i = 0; i < componentsj.length; i++) {
			JavaScriptObject componentJS = componentsj[i];
			HeaderSpan obj = new HeaderSpan(componentJS);
			objects[i] = obj;
		}
		return objects;
	}

	@Override
	protected String getCellStyle(ListGridRecord record, int rowNum, int colNum) {
		return super.getCellStyle(record, rowNum, colNum);
	}

	public void reloadFieldsFromCriteria(AdvancedCriteria criteria) {		
//		Scheduler.get().scheduleDeferred(new ScheduledCommand() {
//			@Override
//			public void execute() {		
//				try {
//					refreshFields();
//				} catch(Exception e) {}
//				markForRedraw();
//			}
//		});	
	}

	public void manageDataArrived() {
		if(recordsToSelect != null) {
//			for(int i = 0; i < recordsToSelect.length; i++) {
//				selectRecord(recordsToSelect[i]);
//			}
			selectRecords(recordsToSelect);
			recordsToSelect = null;					
		} else if(currentRecord != null) {
			if(layout != null && layout.getMultiselect()) {
				// se sono nella modalità di seleziona multipla non funziona perchè la selezione della riga prevede la spunta sulla casella
			} else {			
				int[] recordsToSelect = new int[] {getRecordIndex(currentRecord)};
				selectRecords(recordsToSelect);
				currentRecord = null;
			}
		}
		/*
		for(ListGridRecord record : getRecords()) {
			if(layout != null && layout.getMultiselect() && isSelected(record)) {
//				record.set_baseStyle(it.eng.utility.Styles.cellSelected);
			} else if(currentRecord != null) {
				String pkField = getDataSource().getPrimaryKeyFieldName();
				if(pkField != null && !"".equals(pkField) && record.getAttribute(pkField).equals(currentRecord.getAttribute(pkField))) {
					record.set_baseStyle(it.eng.utility.Styles.cellSelected);																			 
				} else {
					record.set_baseStyle(null);	
				}
			} else {
				record.set_baseStyle(null);
			}
		}
		*/
		if(layout != null) {
			layout.refreshNroRecord();	
			if(layout.isStoppableSearch()) {
				layout.hideStopSearchButton();
			}
			if((layout.overflow || (layout.showPaginazioneItems() && layout.isPaginazioneAttiva())) && layout.overflowSortField != null && !"".equals(layout.overflowSortField)) {
				setSort(new SortSpecifier(layout.overflowSortField, layout.overflowSortDesc ? SortDirection.DESCENDING : SortDirection.ASCENDING));
			}
		}
		String pkField = getDataSource().getPrimaryKeyFieldName();									
		String pkFieldValue = ((GWTRestDataSource) getDataSource()).extraparam.get(pkField);
		if(pkFieldValue != null) {
			((GWTRestDataSource) getDataSource()).extraparam.remove(pkField);
			final int index = getDataAsRecordList().findIndex(pkField, pkFieldValue);								
			if(index >= 0) {													
				Scheduler.get().scheduleDeferred(new ScheduledCommand() {
					@Override
					public void execute() {	
						try {
							scrollToRow(index);
						} catch(Exception e) {
						}
					}
				});													
			}																 										
		}				
		if (UserInterfaceFactory.isAttivaAccessibilita() && getRecords().length > 0) {
			mutationHandlerUpdateRoleTable();
		}
	}

	public void manageContextClick(Record record) {}

	public void manageMultiSelectContextClick() {		
		if(layout != null && layout.getMultiselectButtons().length > 0) {
			final Menu multiSelectContextMenu = new Menu();
			for(final MultiToolStripButton multiSelectButton : layout.getMultiselectButtons()) {
				if(multiSelectButton.toShow()) {
					MenuItem menuItem = new MenuItem(multiSelectButton.getTitle(), multiSelectButton.getIcon());
					menuItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {					
						@Override
						public void onClick(MenuItemClickEvent event) {
							multiSelectButton.doSomething();	
						}
					});
					multiSelectContextMenu.addItem(menuItem);
				}
			}
			Scheduler.get().scheduleDeferred(new ScheduledCommand() {
				@Override
				public void execute() {		
					multiSelectContextMenu.showContextMenu();
				}
			});
		}					
	}	

	public void manageRecordClick(final Record record, final int recordNum) {
		Timer t1 = new Timer() {			
			public void run() {
				instance.setAttribute("rowClicked", -1, true);
			}
		};	   
		Timer t2 = new Timer() {			
			public void run() {	
				if(instance.getAttribute("rowDoubleClicked")==null || !(Integer.valueOf(instance.getAttribute("rowDoubleClicked")).intValue()==getRecordIndex(record))) {
					String rowClickEventSource = record.getAttribute("rowClickEventSource") != null ? record.getAttribute("rowClickEventSource") : "";
					record.setAttribute("rowClickEventSource", "");
					//					if(layout != null && layout.getMultiselect()) {
					//						setCurrentRecord(getRecord(getRecordIndex(record)));						
					//					} else {
					//						setCurrentRecord(null);
					//						selectSingleRecord(getRecordIndex(record));
					//					}
					if("modifyButton".equals(rowClickEventSource)) {							
						manageModifyButtonClick(getRecord(getRecordIndex(record)));
					} else if("detailButton".equals(rowClickEventSource) || (/*(layout != null && !layout.getFullScreenDetail()) && */(rowClickEventSource == null || "".equals(rowClickEventSource)) && (layout != null && layout.getDetailAuto()))) {
						manageDetailButtonClick(getRecord(getRecordIndex(record)));					
					} else if(!"".equals(rowClickEventSource)) {
						manageCustomRowClickEvent(rowClickEventSource, record, recordNum);	
					} else {
						if(layout != null) layout.hideDetail();	
					}
				} else {
					if(layout != null && layout.isLookup()) {
						record.setAttribute("from", "L");
						layout.doLookup(record);											
					}
				}
				instance.setAttribute("rowDoubleClicked", -1, true);
			}
		};	 	    
		if(getAttribute("rowClicked")!=null && Integer.valueOf(getAttribute("rowClicked")).intValue()==getRecordIndex(record)){
			instance.setAttribute("rowDoubleClicked", getRecordIndex(record), true);	 		    	    	    
		} else {
			instance.setAttribute("rowClicked", getRecordIndex(record), true);	 
			t1.schedule(Layout.getGenericConfig().getDelayTimeForDoubleClick());
			t2.schedule(Layout.getGenericConfig().getDelayTimeForDoubleClick()+100);
		}	    	       	
	}

	public void manageCustomRowClickEvent(String rowClickEventSource, Record record, int recordNum) {

	}

	public void manageLoadDetail(Record record, int recordNum, DSCallback callback) {
		if(layout != null && layout.getDetail() != null) {
			layout.getDetail().editRecord(record, recordNum);
			layout.getDetail().getValuesManager().clearErrors(true);
		}
		DSResponse lDsResponse = new DSResponse();
		lDsResponse.setData(new Record[]{record});
		callback.execute(lDsResponse, null, new DSRequest());
	}

	public List<String> getControlFields() {
		return controlFields;
	}

	/**
	 * ATTENZIONE: sovrascrive impostazioni sulle colonne della lista settate in precedenza
	 */
	@Override
	public void setFields(ListGridField... fields) {

		if (!Layout.isDisableSetFields()){
			
			int length = fields.length;
			
			if(!isDisableRecordComponent()) {
				
				//checkboxField
				length += 1; 
			}
			
			ListaBean configLista = Layout.getListConfig(nomeEntita);
			
			if(showButtons) {
				length += getButtonsFields() != null ? getButtonsFields().size() : 0; //buttons			
			}
			
			if(/*layout != null && layout.isLookup() && */isDisableRecordComponent()) length += 1; //lookup	
			
			formattedFields = new ListGridField[length];
			controlFields = new ArrayList<String>();

			int count = 0;
			
			if(!isDisableRecordComponent()) {
				controlFields.add(checkboxField.getName());
				formattedFields[count] = checkboxField;
				count++;
			}
			
			if(/*layout != null && layout.isLookup() && */isDisableRecordComponent()) {
				controlFields.add(lookupButtonField.getName());
				formattedFields[count] = lookupButtonField;
				count++;
			}
			
			for (final ListGridField field : fields){	

				String fieldName = field.getName();
				
				boolean skip = false;
				if(fieldName.equals("_checkboxField") || fieldName.equals("checkboxField") || fieldName.equals("lookupField")) {
					skip = true;
				} else {
					for(ControlListGridField buttonField : getButtonsFields()) {
						if(fieldName.equals(buttonField.getName())) {
							skip = true;
							break;
						}
					}
				}
								
				if(!skip) {

					try {
						
						field.setCanEdit(false);

						if(field instanceof ControlListGridField) {
							controlFields.add(fieldName);
						}

						field.setAlign(Alignment.CENTER);					
						if(field.getTitle() != null && !"".equals(field.getTitle().trim())) {
							field.setPrompt(field.getTitle());					
						}		
						
						//fare un controllo di non nullità sul bean non ha senso, Layout ritorna sempre un bean
						if(configLista.getColonneOrdinabili() != null) {
							//					if(configLista.getColonneDefault().contains(fieldName)) {
							//						field.setHidden(false);
							//					} else {
							//						field.setHidden(true);
							//					}
							if(configLista.getColonneOrdinabili().contains(fieldName)) {
								field.setCanSort(true);
							} else {
								field.setCanSort(false);
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
								field.setHoverCustomizer(new HoverCustomizer() {
									@Override
									public String hoverHTML(Object value, ListGridRecord record, int rowNum,
											int colNum) {
										Object realValue = record != null ? record.getAttribute("realValue"+colNum) : null;								
										return (realValue != null) ? (String) realValue : (String) value;
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
			
			if(showButtons) {
				if(getButtonsFields() != null) {
					for(int i = 0; i < getButtonsFields().size(); i++) {
						controlFields.add(getButtonsFields().get(i).getName());
						formattedFields[count] = getButtonsFields().get(i);
						count++;
					}					
				}				
			}			
			
//			if(/*layout != null && layout.isLookup() && */isDisableRecordComponent()) {
//				controlFields.add(lookupButtonField.getName());
//				formattedFields[count] = lookupButtonField;
//				count++;
//			}
			
			super.setFields(formattedFields);
		} else {
			
			int length = fields.length;

			if(!isDisableRecordComponent()) length += 1; //checkboxField			
			if(showButtons) {
				length += getButtonsFields() != null ? getButtonsFields().size() : 0; //buttons				
			}
			if(/*layout != null && layout.isLookup() && */isDisableRecordComponent()) length += 1; //lookup	
			
			formattedFields = new ListGridField[length];
			controlFields = new ArrayList<String>();

			int count = 0;
			
			if(!isDisableRecordComponent()) {
				controlFields.add(checkboxField.getName());
				formattedFields[count] = checkboxField;
				count++;
			}
			
			if(/*layout != null && layout.isLookup() && */isDisableRecordComponent()) {
				controlFields.add(lookupButtonField.getName());
				formattedFields[count] = lookupButtonField;
				count++;
			}
			
			for (final ListGridField field : fields){	
				String fieldName = field.getName();
				if(field instanceof ControlListGridField) {
					controlFields.add(fieldName);
						
				} 
				formattedFields[count] = field;
				count++;
			}
			
			if(showButtons) {
				if(getButtonsFields() != null) {
					for(int i = 0; i < getButtonsFields().size(); i++) {
						controlFields.add(getButtonsFields().get(i).getName());
						formattedFields[count] = getButtonsFields().get(i);
						count++;
					}					
				}			
			}	
			
//			if(/*layout != null && layout.isLookup() && */isDisableRecordComponent()) {
//				controlFields.add(lookupButtonField.getName());
//				formattedFields[count] = lookupButtonField;
//				count++;
//			}
			
			super.setFields(formattedFields);			
		}

	}	
	
	protected List<ControlListGridField> getButtonsFields() {
		List<ControlListGridField> buttonsList = new ArrayList<ControlListGridField>();			
		if(!isDisableRecordComponent() && !UserInterfaceFactory.isAttivaAccessibilita()) {
			if(buttonsField == null) {
				buttonsField = new ControlListGridField("buttons");		
				buttonsField.setWidth(getButtonsFieldWidth());
				buttonsField.setCanReorder(false);	
				buttonsField.addRecordClickHandler(new RecordClickHandler() {					
					@Override
					public void onRecordClick(RecordClickEvent event) {
						String rowClickEventSource = event.getRecord().getAttribute("rowClickEventSource");  
						if(rowClickEventSource == null || "".equals(rowClickEventSource)) {
							event.cancel();
						}
					}
				});				
			}
			buttonsList.add(buttonsField);
		} else {
			if(showDetailButtonField() && !UserInterfaceFactory.isAttivaAccessibilita()) {
				if(detailButtonField == null) {
					detailButtonField = buildDetailButtonField();						
				}
				buttonsList.add(detailButtonField);
			}
			if(showModifyButtonField()) {
				if(modifyButtonField == null) {
					modifyButtonField = buildModifyButtonField();					
				}
				buttonsList.add(modifyButtonField);
			}
			if(showDeleteButtonField()) {
				if(deleteButtonField == null) {
					deleteButtonField = buildDeleteButtonField();					
				}
				buttonsList.add(deleteButtonField);
			}
			if(showAltreOpButtonField() && !UserInterfaceFactory.isAttivaAccessibilita()) {
				if(altreOpButtonField == null) {
					altreOpButtonField = buildAltreOpButtonField();					
				}	
				buttonsList.add(altreOpButtonField);
			}						
		}	
		return buttonsList;
	}
	
	protected boolean showDetailButtonField() {
		return false;
	}
	
	protected boolean showModifyButtonField() {
		return false;
	}
	
	protected boolean showDeleteButtonField() {
		return false;
	}
	
	protected boolean showAltreOpButtonField() {
		return false;
	}
	
	protected String getDetailButtonPrompt() {
		return I18NUtil.getMessages().detailButton_prompt();
	}
	
	protected String getModifyButtonPrompt() {
		return I18NUtil.getMessages().modifyButton_prompt();
	}
	
	protected String getDeleteButtonPrompt() {
		return I18NUtil.getMessages().deleteButton_prompt();
	}

	protected String getAltreOpButtonPrompt() {
		return I18NUtil.getMessages().altreOpButton_prompt();
	}	
	
	protected ControlListGridField buildDetailButtonField() {
		ControlListGridField detailButtonField = new ControlListGridField("detailButton");  
		detailButtonField.setAttribute("custom", true);	
		detailButtonField.setShowHover(true);		
		detailButtonField.setCanReorder(false);
		detailButtonField.setCellFormatter(new CellFormatter() {			
			@Override
			public String format(Object value, ListGridRecord record, int rowNum, int colNum) {			
				if(isRecordAbilToView(record)) {
					return buildImgButtonHtml("buttons/detail.png","Visualizza dettaglio");			
				} 
//				else {
//					return buildIconHtml("buttons/detail_Disabled.png");
//				}	
				return null;
			}
		});
		detailButtonField.setHoverCustomizer(new HoverCustomizer() {				
			@Override
			public String hoverHTML(Object value, ListGridRecord record, int rowNum, int colNum) {
				if(isRecordAbilToView(record)) {
					return getDetailButtonPrompt();
				}
				return null;
			}
		});		
		detailButtonField.addRecordClickHandler(new RecordClickHandler() {				
			@Override
			public void onRecordClick(RecordClickEvent event) {
				event.cancel();
				ListGridRecord record = getRecord(event.getRecordNum());
				if(isRecordAbilToView(record)) {
					manageDetailButtonClick(record);							
				}
			}
		});
		return detailButtonField;
	}
	
	protected ControlListGridField buildModifyButtonField() {
		ControlListGridField modifyButtonField = new ControlListGridField("modifyButton");  
		modifyButtonField.setAttribute("custom", true);	
		modifyButtonField.setShowHover(true);		
		modifyButtonField.setCanReorder(false);
		modifyButtonField.setCellFormatter(new CellFormatter() {			
			@Override
			public String format(Object value, ListGridRecord record, int rowNum, int colNum) {			
				if(isRecordAbilToMod(record)) {
					return buildImgButtonHtml("buttons/modify.png");			
				} 
//				else {
//					return buildIconHtml("buttons/modify_Disabled.png");
//				}	
				return null;				
			}
		});
		modifyButtonField.setHoverCustomizer(new HoverCustomizer() {				
			@Override
			public String hoverHTML(Object value, ListGridRecord record, int rowNum, int colNum) {
				if(isRecordAbilToMod(record)) {
					return getModifyButtonPrompt();
				}
				return null;
			}
		});		
		modifyButtonField.addRecordClickHandler(new RecordClickHandler() {				
			@Override
			public void onRecordClick(RecordClickEvent event) {
				event.cancel();
				ListGridRecord record = getRecord(event.getRecordNum());
				if(isRecordAbilToMod(record)) {
					manageModifyButtonClick(record);
				}
			}
		});		
		return modifyButtonField;
	}
	
	public ControlListGridField buildDeleteButtonField() {
		ControlListGridField deleteButtonField = new ControlListGridField("deleteButton");  
		deleteButtonField.setAttribute("custom", true);	
		deleteButtonField.setShowHover(true);		
		deleteButtonField.setCanReorder(false);
		deleteButtonField.setCellFormatter(new CellFormatter() {			
			@Override
			public String format(Object value, ListGridRecord record, int rowNum, int colNum) {			
				if(isRecordAbilToDel(record)) {
					return buildImgButtonHtml("buttons/delete.png");			
				} 
//				else {
//					return buildIconHtml("buttons/delete_Disabled.png");
//				}	
				return null;				
			}
		});
		deleteButtonField.setHoverCustomizer(new HoverCustomizer() {				
			@Override
			public String hoverHTML(Object value, ListGridRecord record, int rowNum, int colNum) {
				if(isRecordAbilToDel(record)) {
					return getDeleteButtonPrompt();
				}
				return null;
			}
		});		
		deleteButtonField.addRecordClickHandler(new RecordClickHandler() {				
			@Override
			public void onRecordClick(RecordClickEvent event) {
				event.cancel();
				final ListGridRecord record = getRecord(event.getRecordNum());
				if(isRecordAbilToDel(record)) {
					manageDeleteButtonClick(record);
				}
			}
		});			 
		return deleteButtonField;
	}
	
	public boolean getCanHideAltreOpButtonField() {
		return false;
	}
	
	public ControlListGridField buildAltreOpButtonField() {
		ControlListGridField altreOpButtonField = new ControlListGridField("altreOpButton", getCanHideAltreOpButtonField() ? getAltreOpButtonPrompt() : null);  
		altreOpButtonField.setAttribute("custom", true);	
		altreOpButtonField.setShowHover(true);	
		altreOpButtonField.setCanReorder(false);		
		altreOpButtonField.setCellFormatter(new CellFormatter() {			
			@Override
			public String format(Object value, ListGridRecord record, int rowNum,
					int colNum) {										
				if(isRecordAbilToAltreOp(record)) {
					return buildImgButtonHtml("buttons/altreOp.png","Altre operazioni");			
				} 
//				else {
//					return buildIconHtml("buttons/altreOp_Disabled.png");
//				}	
				return null;							
			}
		});
 		if (UserInterfaceFactory.isAttivaAccessibilita()){
 			altreOpButtonField.setShowFocusedIcon(true);	
 		} 
		altreOpButtonField.setHoverCustomizer(new HoverCustomizer() {				
			@Override
			public String hoverHTML(Object value, ListGridRecord record, int rowNum,
					int colNum) {
				if(isRecordAbilToAltreOp(record)) {
					return getAltreOpButtonPrompt();
				}
				return null;
			}
		});		
		altreOpButtonField.addRecordClickHandler(new RecordClickHandler() {				
			@Override
			public void onRecordClick(RecordClickEvent event) {
				event.cancel();
				final ListGridRecord record = getRecord(event.getRecordNum());
				if(isRecordAbilToAltreOp(record)) {
					manageAltreOpButtonClick(getRecord(event.getRecordNum()));
				}
			}
		});					
		return altreOpButtonField;
	}
	
	public ControlListGridField buildLookupButtonField() {
		ControlListGridField lookupButtonField = new ControlListGridField("lookupButton");  
		lookupButtonField.setAttribute("custom", true);	
		lookupButtonField.setShowHover(true);	
		lookupButtonField.setCanReorder(false);
		lookupButtonField.setCellFormatter(new CellFormatter() {			
			@Override
			public String format(Object value, ListGridRecord record, int rowNum, int colNum) {			
				if(isRecordSelezionabileForLookup(record)) {
					return buildImgButtonHtml(layout != null ? layout.getLookupButtonImage() : "buttons/seleziona.png");			
				} else {
					return buildIconHtml(layout != null ? layout.getLookupButtonImageDisabled() : "buttons/seleziona_Disabled.png");
				}	
			}
		});
		lookupButtonField.setHoverCustomizer(new HoverCustomizer() {				
			@Override
			public String hoverHTML(Object value, ListGridRecord record, int rowNum, int colNum) {
				if(isRecordSelezionabileForLookup(record)) {
					return layout != null ? layout.getLookupButtonPrompt() : I18NUtil.getMessages().selezionaButton_prompt();
				}
				return null;
			}
		});		
		lookupButtonField.addRecordClickHandler(new RecordClickHandler() {				
			@Override
			public void onRecordClick(RecordClickEvent event) {
				event.cancel();
				ListGridRecord record = getRecord(event.getRecordNum());
				if(isRecordSelezionabileForLookup(record)) {
					manageLookupButtonClick(record);
				}
			}
		});	
		lookupButtonField.setShowIfCondition(new ListGridFieldIfFunction() {				
			@Override
			public boolean execute(ListGrid grid, ListGridField field, int fieldNum) {
				return layout != null && layout.isLookup();
			}
		});
		return lookupButtonField;
	}
	
	protected Canvas createFieldCanvas(String fieldName, final ListGridRecord record) {

		Canvas lCanvasReturn = null;

		if (fieldName.equals("buttons")) {	

			ImgButton detailButton = buildDetailButton(record);  
			ImgButton modifyButton = buildModifyButton(record);  
			ImgButton deleteButton = buildDeleteButton(record);  			
			ImgButton altreOpButton = buildAltreOpButton(record);			
			ImgButton lookupButton = buildLookupButton(record);			
			
			if(!abilButtons[0] || !isRecordAbilToView(record)) {
				detailButton.disable();
			}
			if(!abilButtons[1] || !isRecordAbilToMod(record)) {
				modifyButton.disable();
			}
			if(!abilButtons[2] || !isRecordAbilToDel(record)) {
				deleteButton.disable();
			}
			if(!abilButtons[3] || !isRecordAbilToAltreOp(record)) {
				altreOpButton.disable();
			}

			HLayout recordCanvas = new HLayout(3);
			recordCanvas.setHeight(22);   
			recordCanvas.setWidth(getButtonsFieldWidth());
			recordCanvas.setAlign(Alignment.RIGHT);
			recordCanvas.setLayoutRightMargin(3);   
			recordCanvas.setMembersMargin(7);
			
			recordCanvas.addMember(detailButton);			
			recordCanvas.addMember(modifyButton);
			recordCanvas.addMember(deleteButton);
			recordCanvas.addMember(altreOpButton);

			if(layout != null && layout.isLookup()) {
				if(!isRecordSelezionabileForLookup(record)) {
					lookupButton.disable();
				}
				recordCanvas.addMember(lookupButton);		// aggiungo il bottone SELEZIONA				
			}
			
			lCanvasReturn = recordCanvas;
		}	

		return lCanvasReturn;
	}	
	
	protected ImgButton buildDetailButton(final ListGridRecord record) {
		ImgButton detailButton = new ImgButton();   
		detailButton.setShowDown(false);   
		detailButton.setShowRollOver(false);      
		detailButton.setLayoutAlign(Alignment.CENTER);   
		detailButton.setSrc("buttons/detail.png");   
		detailButton.setPrompt(getDetailButtonPrompt());   
		detailButton.setSize(16);  
		detailButton.addClickHandler(new ClickHandler() {   
			public void onClick(ClickEvent event) {      
				manageDetailButtonClick(record);        	
			}  
		});
		return detailButton;
	}
	
	protected ImgButton buildModifyButton(final ListGridRecord record) {
		ImgButton modifyButton = new ImgButton();   
		modifyButton.setShowDown(false);   
		modifyButton.setShowRollOver(false);      
		modifyButton.setLayoutAlign(Alignment.CENTER);   
		modifyButton.setSrc("buttons/modify.png");   
		modifyButton.setPrompt(getModifyButtonPrompt());   
		modifyButton.setSize(16); 
		modifyButton.addClickHandler(new ClickHandler() {   
			public void onClick(ClickEvent event) {   
				manageModifyButtonClick(record);                  	
			}   
		});   
		return modifyButton;
	}
	
	public ImgButton buildDeleteButton(final ListGridRecord record) {
		ImgButton deleteButton = new ImgButton();   
		deleteButton.setShowDown(false);   
		deleteButton.setShowRollOver(false);      
		deleteButton.setLayoutAlign(Alignment.CENTER);   
		deleteButton.setSrc("buttons/delete.png");   
		deleteButton.setPrompt(getDeleteButtonPrompt());   
		deleteButton.setSize(16);  
		deleteButton.addClickHandler(new ClickHandler() {   
			public void onClick(ClickEvent event) {
				manageDeleteButtonClick(record);				                     
			}   
		});  
		return deleteButton;
	}
	
	public ImgButton buildAltreOpButton(final ListGridRecord record) {
		ImgButton altreOpButton = new ImgButton();   
		altreOpButton.setShowDown(false);   
		altreOpButton.setShowRollOver(false);   
		altreOpButton.setLayoutAlign(Alignment.CENTER);   
		altreOpButton.setSrc("buttons/altreOp.png");   
		altreOpButton.setPrompt(getAltreOpButtonPrompt());   
		altreOpButton.setSize(16);  
		altreOpButton.addClickHandler(new ClickHandler() {				
			@Override
			public void onClick(ClickEvent event) {
				manageAltreOpButtonClick(record);
			}
		});
		return altreOpButton;
	}
	
	public ImgButton buildLookupButton(final ListGridRecord record) {
		ImgButton lookupButton = new ImgButton();   
		lookupButton.setShowDown(false);   
		lookupButton.setShowRollOver(false);      
		lookupButton.setLayoutAlign(Alignment.CENTER);   
		lookupButton.setSrc("buttons/seleziona.png");   
		lookupButton.setPrompt(I18NUtil.getMessages().selezionaButton_prompt());   
		lookupButton.setSize(16);  
		lookupButton.addClickHandler(new ClickHandler() {				
			@Override
			public void onClick(ClickEvent event) {
				manageLookupButtonClick(record);
			}
		});
		return lookupButton;
	}
	
	protected void manageDetailButtonClick(ListGridRecord record) {
		detailClick(record, getRecordIndex(record));      
	}
	
	public void publicManageDetailButtonClick(Record record, int recordIndex) {
		detailClick(record, recordIndex);
	}
	
	protected void manageModifyButtonClick(ListGridRecord record) {		
		modifyClick(record, getRecordIndex(record));				
	}
	
	protected void manageDeleteButtonClick(final ListGridRecord record) {
		SC.ask(I18NUtil.getMessages().deleteButtonAsk_message(), new BooleanCallback() {					
			@Override
			public void execute(Boolean value) {
				if(value) {							
					removeData(record, new DSCallback() {								
						@Override
						public void execute(DSResponse response, Object rawData, DSRequest request) {
							if(response.getStatus() == DSResponse.STATUS_SUCCESS) {
								Layout.addMessage(new MessageBean(I18NUtil.getMessages().afterDelete_message(layout != null ? layout.getTipoEstremiRecord(record) : ""), "", MessageType.INFO));
								if(layout != null) layout.hideDetail(true);
							} else {
								Layout.addMessage(new MessageBean(I18NUtil.getMessages().deleteError_message(layout != null ? layout.getTipoEstremiRecord(record) : ""), "", MessageType.ERROR));										
							}																																	
						}
					});													
				}
			}
		});     	
	}
	
	protected void manageAltreOpButtonClick(ListGridRecord record) {
		
	}
	
	protected void manageLookupButtonClick(final ListGridRecord record) {
		record.setAttribute("from", "L");
		getLayout().doLookup(record);
	}
	
	protected boolean isRecordSelezionabileForLookup(ListGridRecord record) {
		return true;
	}

	@Override  
	protected Canvas createRecordComponent(final ListGridRecord record, Integer colNum) {   
		String fieldName = this.getFieldName(colNum);   		

		Canvas lCanvasReturn = null;

		if (fieldName.equals("checkboxField")) {	
			CheckBox lCheckBox = new CheckBox();	
			lCheckBox.setName("checkbox_" + getRecordIndex(record));
			lCheckBox.setValue(selezione.containsKey(getRecordIndex(record)));		
			lCheckBox.setWidth("20");
			lCheckBox.addValueChangeHandler(new ValueChangeHandler<Boolean>() {			
				@Override
				public void onValueChange(final ValueChangeEvent<Boolean> event) {
					Scheduler.get().scheduleDeferred(new ScheduledCommand() {
						@Override
						public void execute() {		
							if(event.getValue() != null && event.getValue()){
								instance.selezionaRecord(record);								
							} else {
								instance.deselezionaRecord(record);						
							}
						}
					});	
				}
			});			

			lCheckBox.setValue(isSelected(record));			
			checkboxList.put(getRecordIndex(record), lCheckBox);

			HLayout recordCanvas = new HLayout(1);
			recordCanvas.setHeight(22);   
			recordCanvas.setWidth(25);
			recordCanvas.setAlign(Alignment.CENTER);
			recordCanvas.addMember(lCheckBox);			
			lCanvasReturn = recordCanvas;			
		} else {
			lCanvasReturn = createFieldCanvas(fieldName, record);			
		}

		if (lCanvasReturn!=null) {
			lCanvasReturn.setAutoHeight();
			lCanvasReturn.markForRedraw();
		}

		if(layout != null && layout.getMultiselect() && isSelected(record)) {
//			record.set_baseStyle(it.eng.utility.Styles.cellSelected);
		} else if(currentRecord != null) {
			String pkField = getDataSource().getPrimaryKeyFieldName();
			if(pkField != null && !"".equals(pkField) && record.getAttribute(pkField).equals(currentRecord.getAttribute(pkField))) {
				record.set_baseStyle(it.eng.utility.Styles.cellSelected);																			 
			} else {
				record.set_baseStyle(null);	
			}
		} else {
			record.set_baseStyle(null);
		}

		return lCanvasReturn;
	}		

	public void selezionaTutti() {		
//		selectAllRecords();
//		hideField("checkboxField");
//		showField("checkboxField");
//		markForRedraw();		
		if(!isDisableRecordComponent()) {
			for(int i=0; i < getRecordList().getLength(); i++) {
				Record record = getRecordList().get(i);
				selezionaRecord(getRecord(getRecordIndex(record)));	
				if(checkboxList.get(getRecordIndex(record)) != null) {
					checkboxList.get(getRecordIndex(record)).setValue(true);
				}
			}		
		} else {
			int i = 0;
			for (ListGridRecord lListGridRecord : getRecords()){
				lListGridRecord.setAttribute("checkboxField", "true");
				selectRecord(i);
				i++;
			}
		}
	}

	public void selezionaRecord(RecordList daSelezionare) {		
		for(int i=0; i < daSelezionare.getLength(); i++) {
			Record record = daSelezionare.get(i);
			selezionaRecord(getRecord(getRecordIndex(record)));
			if(!isDisableRecordComponent()) {
				if(checkboxList.get(getRecordIndex(record)) != null) {
					checkboxList.get(getRecordIndex(record)).setValue(true);
				}
			} else {
				record.setAttribute("checkboxField", true + "");
				refreshCell(i, 0);
			}
		}		
	}

	public void selezionaRecord(ListGridRecord record) {
//		record.set_baseStyle(it.eng.utility.Styles.cellSelected);
//		refreshRow(getRecordIndex(record));
		selectRecord(record);
	}
	
	@Override
	public void selectSingleRecord(int rowNum) {
		if(!isDisableRecordComponent() && layout != null && layout.getMultiselect() != null && layout.getMultiselect()) {
			selezione = new HashMap<Integer, ListGridRecord>();
			selezione.put(rowNum, getRecord(rowNum));
		} else super.selectSingleRecord(rowNum);
	}
	
	@Override
	public void selectSingleRecord(Record record) {
		if(!isDisableRecordComponent() && layout != null && layout.getMultiselect() != null && layout.getMultiselect()) {
			selezione = new HashMap<Integer, ListGridRecord>();
			selezione.put(getRecordIndex(record), getRecord(getRecordIndex(record)));
		} else super.selectSingleRecord(record);
	}

	@Override
	public void selectRecord(int record) {
		if(!isDisableRecordComponent() && layout != null && layout.getMultiselect() != null && layout.getMultiselect()) {
			selezione.put(record, getRecord(record));
		} else super.selectRecord(record);
	}

	@Override
	public void selectRecord(Record record) {
		if(!isDisableRecordComponent() && layout != null && layout.getMultiselect() != null && layout.getMultiselect()) {
			selezione.put(getRecordIndex(record), getRecord(getRecordIndex(record)));
		} else super.selectRecord(record);
	}	
	
	@Override
	public void selectAllRecords() {
		if(!isDisableRecordComponent() && layout != null && layout.getMultiselect() != null && layout.getMultiselect()) {
			for(int i=0; i < getRecordList().getLength(); i++) {
				Record record = getRecordList().get(i);
				selezione.put(getRecordIndex(record), getRecord(getRecordIndex(record)));				
			}			
		} else super.selectAllRecords();
	}

	public void deselezionaTutti() {		
//		deselectAllRecords();
//		hideField("checkboxField");
//		showField("checkboxField");
//		markForRedraw();	
		for(int i=0; i < getRecordList().getLength(); i++) {
			Record record = getRecordList().get(i);
			deselezionaRecord(getRecord(getRecordIndex(record)));	
			if(!isDisableRecordComponent()) {
				if(checkboxList.get(getRecordIndex(record)) != null) {
					checkboxList.get(getRecordIndex(record)).setValue(false);
				}
			} else {
				record.setAttribute("checkboxField", true + "");
			}
		}			
	}

	public void deselezionaRecord(RecordList daDeselezionare) {		
		for(int i=0; i < daDeselezionare.getLength(); i++) {
			Record record = daDeselezionare.get(i);
			deselezionaRecord(getRecord(getRecordIndex(record)));	
			if(!isDisableRecordComponent()) {
				if(checkboxList.get(getRecordIndex(record)) != null) {
					checkboxList.get(getRecordIndex(record)).setValue(false);
				}
			} else {
				record.setAttribute("checkboxField", true + "");
			}
		}		
	}

	public void deselezionaRecord(ListGridRecord record) {
//		record.set_baseStyle(null);
//		refreshRow(getRecordIndex(record));			
		deselectRecord(record);
	}

	@Override
	public void deselectRecord(int record) {
		if(!isDisableRecordComponent() && layout != null && layout.getMultiselect() != null && layout.getMultiselect()) {
			selezione.remove(record);
		} else super.deselectRecord(record);
	}

	@Override
	public void deselectRecord(Record record) {
		if(!isDisableRecordComponent() && layout != null && layout.getMultiselect() != null && layout.getMultiselect()) {
			selezione.remove(getRecordIndex(record));
		} else super.deselectRecord(record);
	}

	@Override
	public void deselectAllRecords() {
		if(!isDisableRecordComponent() && layout != null && layout.getMultiselect() != null && layout.getMultiselect()) {
			selezione = new HashMap<Integer, ListGridRecord>();
		} else super.deselectAllRecords();
	}

	@Override
	public ListGridRecord getSelectedRecord() {
		if(!isDisableRecordComponent() && layout != null && layout.getMultiselect() != null && layout.getMultiselect()) {
			return null;
		} else return super.getSelectedRecord();
	}

	@Override
	public ListGridRecord[] getSelectedRecords() {
		if(!isDisableRecordComponent() && layout != null && layout.getMultiselect() != null && layout.getMultiselect()) {
			return selezione.values().toArray(new ListGridRecord[selezione.values().size()]);
		} else return super.getSelectedRecords();
	}

	@Override
	public Boolean isSelected(ListGridRecord record) {
		if(!isDisableRecordComponent() && layout != null && layout.getMultiselect() != null && layout.getMultiselect()) {
			return selezione.containsKey(getRecordIndex(record));
		} else return super.isSelected(record);
	}

	@Override
	public String getViewState() {
		String viewState = super.getViewState();		
//		if(viewState.contains(",{name:\\\"checkboxField\\\"")) {
//			int beginIndex = viewState.indexOf(",{name:\\\"checkboxField\\\"");
//			int endIndex = viewState.indexOf("}", beginIndex) + 1;
//			String target = viewState.substring(beginIndex, endIndex);
//			viewState = viewState.replace(target, "");
//		}	
//		if(viewState.contains("{name:\\\"checkboxField\\\"")) {
//			int beginIndex = viewState.indexOf("{name:\\\"checkboxField\\\"");
//			int endIndex = viewState.indexOf("},", beginIndex) + 2;
//			String target = viewState.substring(beginIndex, endIndex);
//			viewState = viewState.replace(target, "");
//		}	
		if(viewState.contains(",{name:\\\"lookupButton\\\"")) {
			int beginIndex = viewState.indexOf(",{name:\\\"lookupButton\\\"");
			int endIndex = viewState.indexOf("}", beginIndex) + 1;
			String target = viewState.substring(beginIndex, endIndex);
			viewState = viewState.replace(target, "");
		}	
		if(viewState.contains("{name:\\\"lookupButton\\\"")) {
			int beginIndex = viewState.indexOf("{name:\\\"lookupButton\\\"");
			int endIndex = viewState.indexOf("},", beginIndex) + 2;
			String target = viewState.substring(beginIndex, endIndex);
			viewState = viewState.replace(target, "");
		}				
		return viewState;
	}	

	@Override
	public void setViewState(String viewState) {			
//		if(viewState.contains(",{name:\\\"checkboxField\\\"")) {
//			int beginIndex = viewState.indexOf(",{name:\\\"checkboxField\\\"");
//			int endIndex = viewState.indexOf("}", beginIndex) + 1;
//			String target = viewState.substring(beginIndex, endIndex);
//			viewState = viewState.replace(target, "");
//		}	
//		if(viewState.contains("{name:\\\"checkboxField\\\"")) {
//			int beginIndex = viewState.indexOf("{name:\\\"checkboxField\\\"");
//			int endIndex = viewState.indexOf("},", beginIndex) + 2;
//			String target = viewState.substring(beginIndex, endIndex);
//			viewState = viewState.replace(target, "");
//		}
		if(viewState.contains(",{name:\\\"lookupButton\\\"")) {
			int beginIndex = viewState.indexOf(",{name:\\\"lookupButton\\\"");
			int endIndex = viewState.indexOf("}", beginIndex) + 1;
			String target = viewState.substring(beginIndex, endIndex);
			viewState = viewState.replace(target, "");
		}	
		if(viewState.contains("{name:\\\"lookupButton\\\"")) {
			int beginIndex = viewState.indexOf("{name:\\\"lookupButton\\\"");
			int endIndex = viewState.indexOf("},", beginIndex) + 2;
			String target = viewState.substring(beginIndex, endIndex);
			viewState = viewState.replace(target, "");
		}
		super.setViewState(viewState);
		if(layout != null && layout.isLookup() && isDisableRecordComponent()) {
			showField("lookupButton");
			// sposto il bottone di lookup sempre tutto a sinistra nella lista
			reorderField(getFieldNum("lookupButton"), 0);
		} else {
			hideField("lookupButton");
		}
		if(getSort() != null) {
			ListaBean configLista = Layout.getListConfig(nomeEntita);
			List<SortSpecifier> listaSortSpecifiers = new ArrayList<SortSpecifier>();
			for(SortSpecifier lSortSpecifier : getSort()) {
				if(configLista.getColonneOrdinabili() != null && configLista.getColonneOrdinabili().contains(lSortSpecifier.getField())) {
					listaSortSpecifiers.add(lSortSpecifier);							
				}
			}
			setSort(listaSortSpecifiers.toArray(new SortSpecifier[listaSortSpecifiers.size()]));
		}	
		markForRedraw();
	}
	
	public String manageDateCellFormat(Object value, ListGridField field, ListGridRecord record) {
		if (value==null) return null;
		String lStringValue = value.toString();	
		if(field.getDateFormatter() != null && field.getDateFormatter().equals(DateDisplayFormat.TOEUROPEANSHORTDATE)) {									
			lStringValue = (lStringValue != null && !"".equals(lStringValue)) ? lStringValue.substring(0, 10) : "";
		}
		return lStringValue;
	}
	
	@Override
	public void refreshFields() {
		super.refreshFields();
//		super.setFields(formattedFields);
	}
	
	public void refresh() {
		final DSRequest req = new DSRequest();
		req.setStartRow(0);
		req.setEndRow(null);
		req.setSortBy(getSort());
		getDataSource().fetchData(getCriteria(), new DSCallback() {			
			@Override
			public void execute(DSResponse response, Object rawData, DSRequest request) {
				final ResultSet resultset = new ResultSet();
				resultset.setDataSource(getDataSource());
				resultset.setCriteria(getCriteria());
				resultset.setFetchMode(FetchMode.BASIC);
				resultset.setInitialLength(response.getTotalRows());
				//				resultset.setUseClientFiltering(true);
				//				resultset.setUseClientSorting(true);
				Record[] responseData = response.getData();
				Record[] initialData = new Record[response.getEndRow()+1];
				for (int i = 0; i < response.getData().length; i++) {
					if (i < response.getStartRow()) initialData[i] = null;
					else initialData[i] = responseData[i-response.getStartRow()];
				}
				resultset.setInitialData(initialData);
				resultset.setInitialSort(request.getSortBy());
				setData(resultset);
			}
		}, req);
	}

	protected MenuItem[] getHeaderContextMenuItems(Integer fieldNum) {
		try {
			final ListGridField field = super.getField(fieldNum);			
			MenuItem[] lMenuItems = super.getHeaderContextMenuItems(fieldNum);				
			if (lMenuItems==null) return null; 
			//Recupero i reali menuItem[]
			MenuItem[] lMenuItemsRetrieved = MenuUtil.retrieveMenuFromJavascriptMenu(lMenuItems);	

			MenuItem lMenuItemColonne = retrieveColonne(lMenuItemsRetrieved);
			if(lMenuItemColonne != null) {
				//Recupero la posizione del menu Colonne
				int posColonne = lMenuItemColonne.getAttributeAsInt("position");
				//Recupero il submenu di Colonne
//				lMenuItems[posColonne].setEnabled(canResizeReorder);
				if (getShowInMenuFunction()!=null) { 
					//Recupero il submenu di Colonne
					MenuItem[] lSubmenuItemsColonne = MenuUtil.retrieveSubMenuFromJavascriptMenu(lMenuItemColonne);
					List<MenuItem> lSubmenuItemsColonneList = new ArrayList<MenuItem>(lSubmenuItemsColonne.length);
					int toShow = 0;
					for (MenuItem lSubmenuItem : lSubmenuItemsColonne){
						String name = lSubmenuItem.getTitle();
						ShowInMenuFunction lShowInMenuFunction = getRelatedShowFunction(name);
						if (lShowInMenuFunction != null){
							boolean show = lShowInMenuFunction.mustBeShown();
							if (show) {
								lSubmenuItemsColonneList.add(lSubmenuItem);
								toShow++;
							}
						} else {
							lSubmenuItemsColonneList.add(lSubmenuItem);
							toShow++;
						}
					}				
					MenuItem[] lSubmenuItemsColonneNew = new MenuItem[toShow];
					for(int count = 0; count<toShow; count++){
						lSubmenuItemsColonneNew[count] = lSubmenuItemsColonneList.get(count);
					}
					Menu lSubmenuColonneNew = new Menu();
					lSubmenuColonneNew.setItems(lSubmenuItemsColonneNew);
					lMenuItems[posColonne].setSubmenu(lSubmenuColonneNew);					
				}
			}		

			if(field.getGroupingModes() != null && field.getGroupingModes().keySet().size() > 0) {
				//Recupero il menu Raggruppa per
				MenuItem lMenuItemRaggruppaPer = retrieveRaggruppaPer(lMenuItemsRetrieved);
				if(lMenuItemRaggruppaPer != null) {
					//Recupero la posizione del menu Raggruppa per
					int posRaggruppaPer = lMenuItemRaggruppaPer.getAttributeAsInt("position");					
					MenuItem[] lSubmenuItemsRaggruppaPer =  new MenuItem[field.getGroupingModes().keySet().size()];
					int cont = 0;
					for(Object key : field.getGroupingModes().keySet()) {
						final String groupingMode = (String) key;
						lSubmenuItemsRaggruppaPer[cont] = new MenuItem((String) field.getGroupingModes().get(key));
						lSubmenuItemsRaggruppaPer[cont].addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {				
							@Override
							public void onClick(MenuItemClickEvent event) {
								field.setGroupingMode(groupingMode);		
								instance.groupBy(field.getName());
							}
						});				
						cont++;
					}
					Menu lSubmenuRaggruppaPer = new Menu();
					lSubmenuRaggruppaPer.setItems(lSubmenuItemsRaggruppaPer);
					lMenuItems[posRaggruppaPer].setSubmenu(lSubmenuRaggruppaPer);
				}
			}		
			return lMenuItems; 
		} catch(Exception e) {}
		return null;
	}

	protected MenuItem retrieveColonne(MenuItem[] lMenuItemsRetrieved) {
		int position = 0;
		int k = 0;
		for (MenuItem lMenuItemToAnalyze : lMenuItemsRetrieved){
			//			System.out.println(SC.echo(lMenuItemToAnalyze.getJsObj()));
			if (!lMenuItemToAnalyze.getIsSeparator() && lMenuItemToAnalyze.getTitle().equals(I18NUtil.getMessages().colonneMenuItem_title())){
				//Recupero quello delle colonne
				position = k;
				lMenuItemToAnalyze.setAttribute("position", position);
				return lMenuItemToAnalyze;
			}
			k++;
		}
		return null;
	}

	protected MenuItem retrieveRaggruppaPer(MenuItem[] lMenuItemsRetrieved) {
		int position = 0;
		int k = 0;
		for (MenuItem lMenuItemToAnalyze : lMenuItemsRetrieved){
			//			System.out.println(SC.echo(lMenuItemToAnalyze.getJsObj()));
			if (!lMenuItemToAnalyze.getIsSeparator() && lMenuItemToAnalyze.getTitle().startsWith(I18NUtil.getMessages().raggruppaPerMenuItem_titlePrefix())){
				//Recupero quello delle colonne
				position = k;
				lMenuItemToAnalyze.setAttribute("position", position);
				return lMenuItemToAnalyze;
			}
			k++;
		}
		return null;
	}

	protected ShowInMenuFunction getRelatedShowFunction(String name) {
		ShowInMenuFunction[] lShowInMenuFunctions = getShowInMenuFunction();
		if (lShowInMenuFunctions == null) return null;
		else {
			for (ShowInMenuFunction function : lShowInMenuFunctions){
				if (function.contains(name)){
					return function;
				}
			}
			return null;
		}
	}

	protected ShowInMenuFunction[] getShowInMenuFunction() {
		return null;
	}

	public CustomLayout getLayout() {
		return layout;
	}

	public void setLayout(CustomLayout layout) {
		this.layout = layout;
	}

	public boolean[] getAbilButtons() {
		return abilButtons;
	}

	public void setAbilButtons(boolean[] abilButtons) {
		this.abilButtons = abilButtons;		
	}
	
	public ListGridRecord getCurrentRecord() {
		return currentRecord;
	}

	public void setCurrentRecord(ListGridRecord currentRecord) {
		if(this.currentRecord != null) this.currentRecord.set_baseStyle(null);
		if(currentRecord != null) currentRecord.set_baseStyle(it.eng.utility.Styles.cellSelected);
		this.currentRecord = currentRecord;		
	}

	@Override
	public native void scrollToRow(int rowNum) /*-{
		var self = this.@com.smartgwt.client.widgets.BaseWidget::getOrCreateJsObj()();
		self.scrollToRow(rowNum);
	}-*/;

	protected void detailClick(final Record record, final int recordNum) {
		if(layout != null) {
			layout.getDetail().markForRedraw();
			manageLoadDetail(record, recordNum, new DSCallback() {							
				@Override
				public void execute(DSResponse response, Object rawData, DSRequest request) {
					layout.viewMode();		
				}
			}); 
		}
	}

	protected void modifyClick(final Record record, final int recordNum) {
		if(layout != null) {
			layout.getDetail().markForRedraw();
			manageLoadDetail(record, recordNum, new DSCallback() {							
				@Override
				public void execute(DSResponse response, Object rawData, DSRequest request) {
					layout.editMode();		
				}
			});
		}
	}

	public int[] getRecordsToSelect() {
		return recordsToSelect;
	}

	public void setRecordsToSelect(int[] recordsToSelect) {
		this.recordsToSelect = recordsToSelect;
	}

	public HandlerRegistration getRecordClickHandler() {
		return recordClickHandler;
	}

	public void setRecordClickHandler(HandlerRegistration recordClickHandler) {
		this.recordClickHandler = recordClickHandler;
	}

	public Portlet getWindow() {
		return window;
	}

	public void setWindow(Portlet window) {
		this.window = window;
	}
	
	public boolean isDisableRecordComponent() {
		return Layout.isDisableRecordComponent();
	}
	
	protected String buildImgButtonHtml(String src) {
 		if (UserInterfaceFactory.isAttivaAccessibilita()){
 			return "<div style=\"cursor:pointer\" align=\"center\" role=\"button\" aria-label=\"\"><img src=\"images/" + src + "\" height=\"16\" width=\"16\" alt=\"\" /></div>";		
 		} else {
		return "<div style=\"cursor:pointer\" align=\"center\"><img src=\"images/" + src + "\" height=\"16\" width=\"16\" alt=\"\" /></div>";		
	}
	}

	protected String buildImgButtonHtml(String src, String label) {
 		if (UserInterfaceFactory.isAttivaAccessibilita()){
 			return "<div style=\"cursor:pointer\" align=\"center\" aria-label=\"" + label + "\"><img src=\"images/" + src + "\" height=\"16\" width=\"16\" alt=\""+label+"\" /></div>";		
 		} else {
		return "<div style=\"cursor:pointer\",\"canFocus:true\" align=\"center\" role=\"button\" aria-label=\"" + label + "\"><img src=\"images/" + src + "\" height=\"16\" width=\"16\" alt=\"\" /></div>";		
	}
	}
	
	protected String buildButtonActionJsByRowNumHtml(String src, String label, int rowNum, String functionJs, String nameImgTag) {
		if (UserInterfaceFactory.isAttivaAccessibilita()){
			return "<div style=\"cursor:pointer\" align=\"center\" ><button tabindex=\"-1\" aria-label=\"" + label + "\" role=\"button\" onclick=\" "+(Layout.isExternalPortlet?"document":"window.top")+ "."+ functionJs+"_" + smartId + "(\'"+ rowNum +"\')\">"
					+ "<img name=\""+nameImgTag+"img_"+smartId+"_"+rowNum+"\" src=\"images/" + src + "\" height=\"16\" width=\"16\" alt=\"\" /></button></div>";		
		} else {
			return "<div style=\"cursor:pointer\",\"canFocus:true\" align=\"center\" role=\"button\" aria-label=\"" + label + "\"><img src=\"images/" + src + "\" height=\"16\" width=\"16\" alt=\"\" /></div>";		
		}
	}
	
	protected String buildButtonSelectJsByRowNumHtml(String src, String label, int rowNum, String functionJs, String nameImgTag) {
		if (UserInterfaceFactory.isAttivaAccessibilita()){
			return "<input type=\"checkbox\" "+nameImgTag+" onclick=\" "+(Layout.isExternalPortlet?"document":"window.top")+ "."+ functionJs+"_" + smartId + "(\'"+ rowNum +"\',\'"+ nameImgTag +"\')\">";		
		} else {
			return "<div style=\"cursor:pointer\",\"canFocus:true\" align=\"center\" role=\"button\" aria-label=\"" + label + "\"><img src=\"images/" + src + "\" height=\"16\" width=\"16\" alt=\"\" /></div>";		
		}
	}
//	protected String buildButtonSelectJsByRowNumHtml(String src, String label, int rowNum, String functionJs, String nameImgTag) {
//		if (UserInterfaceFactory.isAttivaAccessibilita()){
//			return "<div style=\"cursor:pointer\" align=\"center\" ><button name=\""+nameImgTag+"btn_"+smartId+"_"+rowNum+"\" tabindex=\"-1\"  aria-label=\"" + label + "\" role=\"button\" onclick=\" "+(Layout.isExternalPortlet?"document":"window.top")+ "."+ functionJs+"_" + smartId + "(\'"+ rowNum +"\',\'"+ nameImgTag +"\')\">"
//					+ "<img name=\""+nameImgTag+"img_"+smartId+"_"+rowNum+"\" src=\"images/" + src + "\" height=\"16\" width=\"16\" alt=\"\" /></button></div>";		
//		} else {
//			return "<div style=\"cursor:pointer\",\"canFocus:true\" align=\"center\" role=\"button\" aria-label=\"" + label + "\"><img src=\"images/" + src + "\" height=\"16\" width=\"16\" alt=\"\" /></div>";		
//		}
//	}
	protected String buildButtonSelectJsByRowNumHtml(String src, String label, int rowNum, String nameImgTag) {
		if (UserInterfaceFactory.isAttivaAccessibilita()){
			return "<div style=\"cursor:pointer\" align=\"center\" ><button aria-label=\"" + label + "\" role=\"button\" >"
					+ "<img name=\""+nameImgTag+"img_"+smartId+"_"+rowNum+"\" src=\"images/" + src + "\" height=\"16\" width=\"16\" alt=\"\" /></button></div>";		
		} else {
			return "<div style=\"cursor:pointer\",\"canFocus:true\" align=\"center\" role=\"button\" aria-label=\"" + label + "\"><img src=\"images/" + src + "\" height=\"16\" width=\"16\" alt=\"\" /></div>";		
		}
	}
	
//	protected String buildButtonSelectJsByRowNumHtml(String src, String label, int rowNum, String functionJs, String nameImgTag) {
//		if (UserInterfaceFactory.isAttivaAccessibilita()){
//			return "<div style=\"cursor:pointer\" align=\"center\" ><button name=\""+nameImgTag+"btn_"+smartId+"_"+rowNum+"\" aria-label=\"" + label + "\" role=\"button\" onclick=\" "+(Layout.isExternalPortlet?"document":"window.top")+ "."+ functionJs+"_" + smartId + "(\'"+ rowNum +"\',\'"+ nameImgTag +"\')\">"
//					+ "<img name=\""+nameImgTag+"img_"+smartId+"_"+rowNum+"\" src=\"images/" + src + "\" height=\"16\" width=\"16\" alt=\"\" /></button></div>";		
//		} else {
//			return "<div style=\"cursor:pointer\",\"canFocus:true\" align=\"center\" role=\"button\" aria-label=\"" + label + "\"><img src=\"images/" + src + "\" height=\"16\" width=\"16\" alt=\"\" /></div>";		
//		}
//	}
//	
	protected String buildIconHtml(String src) {
		return "<div align=\"center\"><img src=\"images/" + src + "\" height=\"16\" width=\"16\" alt=\"\" /></div>";
	}

	protected String buildIconHtml(String src, String label) {
		return "<div align=\"center\"><img src=\"images/" + src + "\" height=\"16\" width=\"16\" alt=\""+label+"\" /></div>";
	}
	
	protected Object formatDateForSorting(ListGridRecord record, String fieldName) {
		String value = record != null ? record.getAttributeAsString(fieldName) : null;
		return value != null && !"".equals(value) ? DateUtil.parseInput(value) : null;
	}
	
	public CustomList getCustomListInstance() {
		return instance;
	}
	
	public String getNomeEntita() {
		return nomeEntita;
	}
	
	/*
	 * Metodo di cui fare l'override per indicare la logica di apertura del dettaglio del record.
	 * Utilizzato per l'accessibilità delle tabelle
	 */
	public void openDetailFromButton (String value) {
		ListGridRecord record = getRecord(Integer.valueOf(value));
		manageDetailButtonClick(record);
	}
	
	/*
	 * Metodo per indicare la logica di selezione del record.
	 * Utilizzato per l'accessibilità delle tabelle
	 */
	public void selectRowByRowNum (String rowNum, String nameImgTag) {
		if (isSelected(getRecord(Integer.valueOf(rowNum)))) {
			deselectRecord(Integer.valueOf(rowNum));
			String nameImg = nameImgTag+"img_"+smartId+"_"+rowNum;
			String btnImg = nameImgTag+"btn_"+smartId+"_"+rowNum;
			String functionDeselectRow = (Layout.isExternalPortlet?"document":"window.top")+ ".deselectRowByRowNum_"+smartId+ "(\'"+ rowNum +"\',\'" + nameImgTag +"\')";
			String srcImg = "Seleziona riga";
//			String srcImg = "images/buttons/delete2.png";
//			this.changeIconButton(rowNum,nameImg,btnImg,functionDeselectRow,srcImg);
		} else {
			selectRecord(Integer.valueOf(rowNum));
			String nameImg = nameImgTag+"img_"+smartId+"_"+rowNum;
			String btnImg = nameImgTag+"btn_"+smartId+"_"+rowNum;
			String functionDeselectRow = (Layout.isExternalPortlet?"document":"window.top")+ ".deselectRowByRowNum_"+smartId+ "(\'"+ rowNum +"\',\'" + nameImgTag +"\')";
			String srcImg = "Deseleziona riga";
//			String srcImg = "images/buttons/delete2.png";
//			this.changeIconButton(rowNum,nameImg,btnImg,functionDeselectRow,srcImg);
		}
//		refreshRow(Integer.valueOf(rowNum));
//		focus();
//		focusInRow(Integer.valueOf(4));
		
	}
	
	/*
	 * Metodo per indicare la logica di selezione del record.
	 * Utilizzato per l'accessibilità delle tabelle
	 */
	public void deselectRowByRowNum (String rowNum, String nameImgTag) {
		deselectRecord(Integer.valueOf(rowNum));
		refreshRow(Integer.valueOf(rowNum));
		String nameImg = nameImgTag+"img_"+smartId+"_"+rowNum;
		String btnImg = nameImgTag+"btn_"+smartId+"_"+rowNum;
		String functionDeselectRow = (Layout.isExternalPortlet?"document":"window.top")+ ".selectRowByRowNum_"+smartId+ "(\'"+ rowNum +"\',\'" + nameImgTag +"\')";
		String srcImg = "images/buttons/new2.png";
//		this.changeIconButton(rowNum,nameImg,btnImg,functionDeselectRow,srcImg);
	}
	
	public native void changeIconButton (String id, String nameImg, String nameBtn, String functionDeselectRow, String srcImg) /*-{
		console.info(functionDeselectRow);
		var imgBtn = $doc.querySelector('button[name='+nameBtn+'');
//		var imgItem = $doc.querySelector('img[name='+nameImg+'');
 		imgBtn.setAttribute("aria-label",srcImg);
// 		imgItem.setAttribute("aria-label",srcImg);
	}-*/;
	
	private native void initChangeCheckBoxRole(CustomList customList, String functionName, boolean isExternalPortlet) /*-{
	   if (isExternalPortlet){	
		   $doc[functionName] = function (value, value2) {
		       customList.@it.eng.utility.ui.module.layout.client.common.CustomList::selectRowByRowNum(Ljava/lang/String;Ljava/lang/String;)(value,value2);
		   };
	   } else {
	   	   $wnd[functionName] = function (value, value2) {
		       customList.@it.eng.utility.ui.module.layout.client.common.CustomList::selectRowByRowNum(Ljava/lang/String;Ljava/lang/String;)(value,value2);
		   };
	   }
	}-*/;
	
	private native void initDeselectRow(CustomList customList, String functionName, boolean isExternalPortlet) /*-{
	   if (isExternalPortlet){	
		   $doc[functionName] = function (value, value2) {
		       customList.@it.eng.utility.ui.module.layout.client.common.CustomList::deselectRowByRowNum(Ljava/lang/String;Ljava/lang/String;)(value,value2);
		   };
	   } else {
	   	   $wnd[functionName] = function (value, value2) {
		       customList.@it.eng.utility.ui.module.layout.client.common.CustomList::deselectRowByRowNum(Ljava/lang/String;Ljava/lang/String;)(value,value2);
		   };
	   }
	}-*/;
	
	private native void initOpenDetailByRowNum(CustomList customList, String functionName, boolean isExternalPortlet) /*-{
	   if (isExternalPortlet){	
		   $doc[functionName] = function (value) {
		       customList.@it.eng.utility.ui.module.layout.client.common.CustomList::openDetailFromButton(Ljava/lang/String;)(value);
		   };
	   } else {
	   	   $wnd[functionName] = function (value) {
		       customList.@it.eng.utility.ui.module.layout.client.common.CustomList::openDetailFromButton(Ljava/lang/String;)(value);
		   };
	   }
	}-*/;
	
	
	public native void mutationHandlerUpdateRoleTable()/*-{
	var targetNodes         = $doc.querySelectorAll('div[role=table]');
	var MutationObserver    = window.MutationObserver || window.WebKitMutationObserver;
	var myObserver          = new MutationObserver (mutationHandler);
	var obsConfig           = { childList: true, characterData: true, attributes: true, subtree: true };
	var smartId				= this.@it.eng.utility.ui.module.layout.client.common.CustomList::getSmartId()();

	//--- Add a target node to the observer. Can only add one node at a time.
	for (var i = 0; i < targetNodes.length; i++) {
//		alert('observer');
	  myObserver.observe (targetNodes[i], obsConfig);
	}

	function mutationHandler (mutationRecords) {
//	    console.info ("mutationHandler:");
//		alert('test');
		if (targetNodes !== undefined && targetNodes.length > 0) {
			for (var i = 0; i < targetNodes.length; i++) {
	//			alert('for righe');
				var rows = targetNodes[i].querySelectorAll('tr[role=listitem]');
				if (rows !== undefined && rows.length>0) {
//					var intestazioniCorette = targetNodes[i].querySelectorAll('td[role=columnheader]');
					var rowheader = targetNodes[i].querySelectorAll('div[role=rowheader]');
	//				console.info ("nell'if. righe : "+rows.length);
					if (rowheader.length > 0)
					{
						for (var a=0; a<rowheader.length; a++) {
							var intestazioneSingola = rowheader[a].querySelectorAll('td');
		//					alert('if intestazioni');
							for (var m=0; m<intestazioneSingola.length; m++){
		//						console.log('for intestazioni');
								intestazioneSingola[m].setAttribute("role","columnheader");
							}
						}
					}
					var intestazioniCorette = targetNodes[i].querySelectorAll('td[role=columnheader]');
				    for (var a = 0; a < rows.length; a++) {
					  rows[a].removeAttribute("role");
					  rows[a].setAttribute("role","row");
					  var celle = rows[a].querySelectorAll('td');
					  for (var j=0; j < celle.length; j++) {
					  	celle[j].setAttribute("role","cell");
					  	var divs = celle[j].querySelectorAll('div[role=presentation]');
					  	var bottone = celle[j].querySelectorAll('button');
					  	var divTd = celle[j].querySelectorAll('div');
					  	if (bottone.length === 0) {
						  	for (var k=0; k < divs.length; k++) {
						  		divs[k].removeAttribute("role");
							  	var divElement = document.createElement("span");   // Create a <button> element
						  		if (divs[k].innerHTML === '&nbsp;') {
//						  			divTd[0].setAttribute("aria-label",intestazioniCorette[j].innerText+" nessun valore");
									divElement.innerHTML = intestazioniCorette[j].innerText+" nessun valore";  
						  		} else {
//						  			divTd[0].setAttribute("aria-label",intestazioniCorette[j].innerText+" "+divTd[0].innerText);
									divElement.innerHTML = intestazioniCorette[j].innerText+" ";  
						  		}
								divElement.classList.add("SRonly");
								celle[j].insertBefore(divElement, celle[j].firstChild);
						  	}
					  	}
					  }
						
					}
				}
			}
		}
			
	}
}-*/;

	public String getSmartId() {
		return smartId;
	}

	public void setSmartId(String smartId) {
		this.smartId = smartId;
	}
	
	public DettaglioWindow getDettaglioWindow() {
		return dettaglioWindow;
	}

	public class DettaglioWindow extends ModalWindow {

		private DettaglioWindow _window;

		/**
		 * Layout & Detail della maschera da cui provengo
		 */
		private CustomLayout _layoutArchivio;

		/**
		 * Detail della maschera corrente
		 */
		private CustomDetail portletLayout;
		
		protected ToolStrip detailToolStrip;
		private boolean isOpen;
		
		public DettaglioWindow(final Record record,final CustomDetail detail,String title,List<ToolStripButton> customBottomListButtons) {
			super("dettagliodocumento", true);
//			SC.setScreenReaderMode(true);
			isOpen = true;
			setTitle("Dettaglio documento");

			_window = this;

			settingsMenu.removeItem(separatorMenuItem);
			settingsMenu.removeItem(autoSearchMenuItem);
			loadPortletLayout(record, detail, customBottomListButtons);
			setBody(portletLayout);
			_window.setTitle(title);
			_window.setIcon("menu/archivio.png");
			_window.show();
			
			Layout.hideWaitPopup();
			
		}
		
		public void updateContentDocumentWindow ( Record record, CustomDetail detail,String title,List<ToolStripButton> customBottomListButtons) {
			loadPortletLayout(record, detail, customBottomListButtons);
			setBody(portletLayout);
			_window.markForRedraw();
			Layout.hideWaitPopup();
		}
		
		private void loadPortletLayout (final Record record,final CustomDetail detail,List<ToolStripButton> customBottomListButtons) {
			portletLayout=detail;
			portletLayout.setLayout(detail.getLayout());
			portletLayout.setHeight100();
			portletLayout.setWidth100();
//			portletLayout.editRecord(record);
			createMainLayout(customBottomListButtons);
			portletLayout.addMember(detailToolStrip);
		}
		
		protected VLayout createMainLayout(List<ToolStripButton> customBottomListButtons) {

			createDetailToolstrip(customBottomListButtons);

			VLayout mainLayout = new VLayout();
			mainLayout.setHeight100();
			mainLayout.setWidth100();
//			mainLayout.addMember(group);
			return mainLayout;

		}

		private void createDetailToolstrip(List<ToolStripButton> customBottomListButtons) {
			detailToolStrip = new ToolStrip();
			detailToolStrip.setWidth100();
			detailToolStrip.setHeight(30);
			detailToolStrip.setStyleName(it.eng.utility.Styles.detailToolStrip);
			DetailToolStripButton backToListButton = portletLayout.getLayout().getBackToListButton();
			detailToolStrip.addButton(backToListButton);

			detailToolStrip.addFill(); // push all buttons to the right
			for (ToolStripButton singleButton: customBottomListButtons)
			{
				detailToolStrip.addButton(singleButton);
			}

		}
		
		@Override
		public void manageOnCloseClick() {
			super.manageOnCloseClick();
			isOpen = false;
		}

		public boolean isOpen() {
			return isOpen;
		}
		
		public void setOpen(boolean isOpen) {
			this.isOpen = isOpen;
		}
		
	}
}
