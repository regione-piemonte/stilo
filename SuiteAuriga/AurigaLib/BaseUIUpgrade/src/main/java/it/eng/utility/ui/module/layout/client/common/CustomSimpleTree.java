/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import com.google.gwt.core.client.Scheduler;
import com.google.gwt.core.client.Scheduler.ScheduledCommand;
import com.google.gwt.user.client.Timer;
import com.smartgwt.client.data.AdvancedCriteria;
import com.smartgwt.client.data.DSCallback;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.types.DateDisplayFormat;
import com.smartgwt.client.types.FetchMode;
import com.smartgwt.client.types.OperatorId;
import com.smartgwt.client.types.Overflow;
import com.smartgwt.client.types.SelectionStyle;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.events.RightMouseDownEvent;
import com.smartgwt.client.widgets.events.RightMouseDownHandler;
import com.smartgwt.client.widgets.grid.ListGridRecord;
import com.smartgwt.client.widgets.grid.events.CellClickEvent;
import com.smartgwt.client.widgets.grid.events.CellClickHandler;
import com.smartgwt.client.widgets.grid.events.CellContextClickEvent;
import com.smartgwt.client.widgets.grid.events.CellContextClickHandler;
import com.smartgwt.client.widgets.grid.events.CellOutEvent;
import com.smartgwt.client.widgets.grid.events.CellOutHandler;
import com.smartgwt.client.widgets.grid.events.CellOverEvent;
import com.smartgwt.client.widgets.grid.events.CellOverHandler;
import com.smartgwt.client.widgets.grid.events.DataArrivedEvent;
import com.smartgwt.client.widgets.grid.events.DataArrivedHandler;
import com.smartgwt.client.widgets.menu.Menu;
import com.smartgwt.client.widgets.menu.MenuItem;
import com.smartgwt.client.widgets.menu.MenuItemIfFunction;
import com.smartgwt.client.widgets.menu.events.MenuItemClickEvent;
import com.smartgwt.client.widgets.tree.TreeGrid;
import com.smartgwt.client.widgets.tree.TreeNode;

import it.eng.utility.ui.module.core.client.UserInterfaceFactory;
import it.eng.utility.ui.module.core.client.datasource.GWTRestDataSource;
import it.eng.utility.ui.module.core.client.i18n.I18NUtil;
import it.eng.utility.ui.module.core.shared.bean.TreeNodeBean;
import it.eng.utility.ui.module.core.shared.message.MessageBean;
import it.eng.utility.ui.module.core.shared.message.MessageType;
import it.eng.utility.ui.module.layout.client.Layout;
import it.eng.utility.ui.module.layout.client.common.NavigationContextMenu.NavigationContextMenuFrom;
import it.eng.utility.ui.module.core.client.UserInterfaceFactory;

public class CustomSimpleTree extends TreeGrid {

	protected CustomSimpleTree instance;	
	protected final String nomeEntita;
	protected CustomSimpleTreeLayout layout;
	
	protected Record currentRecord = null;	
	protected ListGridRecord mouseOverRecord;
	protected Menu contextMenu;
	
	public CustomSimpleTree(final String nomeEntita) {
		
		instance = this;
		this.nomeEntita = nomeEntita;
 		if (UserInterfaceFactory.isAttivaAccessibilita()){
			setCanFocus(true);
	//		setTabIndex(-1);	
 		} else {
			setCanFocus(false);
			setTabIndex(-1);
		}
		setHeight100();
		setWidth100();			
		setAutoFetchData(true);						
		setDataFetchMode(FetchMode.BASIC);
		setShowAllRecords(false); /* false per ovviare al problema dello scroll sull'albero*/		
		setLoadDataOnDemand(false);		
		setFixedRecordHeights(false);
		setShowRecordComponents(true);           
		setShowRecordComponentsByCell(true);		
		setCanEdit(false);
		setCanAutoFitFields(false);		   
		setCanReorderRecords(false);
		setCanDragRecordsOut(true);
		setCanHover(true);
		setHoverWidth(200);		
		setWrapCells(true);
		setAutoFitFieldWidths(true);
		setAlternateRecordStyles(false);	
		setShowHeader(false);
		setShowConnectors(false);
		setShowFullConnectors(false);
		setShowOpener(true);		
		setShowOpenIcons(true);
		setShowEmptyMessage(true);
		setEmptyMessage(I18NUtil.getMessages().list_noSearchMessage());
		setShowRecordComponents(true);           
		setShowRecordComponentsByCell(true);				
		setLeaveScrollbarGap(false);		
		setKeepInParentRect(true);		
		setRecordCanSelectProperty("canSelect");		
		setDrawAheadRatio(2);
		setDrawAllMaxCells(0);				
		setDateFormatter( DateDisplayFormat.TOEUROPEANSHORTDATE); 
		setDatetimeFormatter( DateDisplayFormat.TOEUROPEANSHORTDATETIME );
		setNodeIcon("tree/file.png");
		setFolderIcon("tree/folder.png");
//		setExtraIconGap(extraIconGap)
		setNoDoubleClicks(true);
		setHoverDelay(0);		
		setSelectionType(SelectionStyle.NONE);
		
//		setManyItemsImage(manyItemsImage);
//		setNodeIcon(nodeIcon);
//		setOfflineNodeMessage(offlineNodeMessage);
//		setTreeRootValue(treeRootValue);
//		setShowPartialSelection(showPartialSelection);											
		
		addDataArrivedHandler(new DataArrivedHandler() {			
			@Override
			public void onDataArrived(DataArrivedEvent event) {
				
				manageOnDataArrived(event);			
			}
		});

		addCellClickHandler(new CellClickHandler() {			
			@Override
			public void onCellClick(CellClickEvent event) {
				event.cancel();
				manageOnCellClick(event.getRecord(), event.getRowNum());
			}
		});	
		
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
						
		addCellContextClickHandler(new CellContextClickHandler() {			
			@Override
			public void onCellContextClick(CellContextClickEvent event) {
				Record record = event.getRecord();
				event.cancel(); //per inibire il contextmenu del browser
				manageContextClick(record);	
			}
		});
		
		addRightMouseDownHandler(new RightMouseDownHandler() {			
			@Override
			public void onRightMouseDown(RightMouseDownEvent event) {
				event.cancel();	
				manageContextClick(mouseOverRecord);				
			}
		});		

										
	}	
	
	public void manageContextClick(final Record record) {
		if(record != null) {			
			String azione = record.getAttributeAsString("azione");				
			if(azione != null && !"".equals(azione)) {			
				TreeNodeBean node = new TreeNodeBean();
				node.setIdNode(record.getAttributeAsString("idNode"));				
				node.setIdFolder(record.getAttributeAsString("idFolder"));
				
				final GWTRestDataSource autoSearchDS = UserInterfaceFactory.getPreferenceDataSource();
				autoSearchDS.addParam("prefKey", layout.getPrefKeyPrefix(record) + "autosearch");	     

				final MenuItem autoSearchMenuItem = new MenuItem(I18NUtil.getMessages().autoSearchMenuItem_title());		
				autoSearchMenuItem.setCheckIfCondition(new MenuItemIfFunction() {			
					@Override
					public boolean execute(Canvas target, Menu menu, MenuItem item) {
						
						return autoSearchMenuItem.getAttributeAsBoolean("value");
					}
				});
				autoSearchMenuItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {			
					@Override
					public void onClick(MenuItemClickEvent event) {				
						
						AdvancedCriteria criteria = new AdvancedCriteria();                        
						criteria.addCriteria("prefName", OperatorId.EQUALS, "DEFAULT"); 
						autoSearchDS.fetchData(criteria, new DSCallback() {   
							@Override  
							public void execute(DSResponse response, Object rawData, DSRequest request) {   		        				        		
								Record[] data = response.getData();   		        		
															               
								final boolean checked = autoSearchMenuItem.getChecked();
								autoSearchMenuItem.setAttribute("value", !checked);
								Record record = data[0];
								record.setAttribute("value", !checked);
								autoSearchDS.updateData(record);							
								Layout.addMessage(new MessageBean(checked ? I18NUtil.getMessages().afterDisattivaAutoSearch_message() : I18NUtil.getMessages().afterAttivaAutoSearch_message(), "", MessageType.INFO));						
							}   
						});    		   
					}
				});									
				AdvancedCriteria criteriaAutoSearch = new AdvancedCriteria();                        
				criteriaAutoSearch.addCriteria("prefName", OperatorId.EQUALS, "DEFAULT"); 
				autoSearchDS.fetchData(criteriaAutoSearch, new DSCallback() {   
					@Override  
					public void execute(DSResponse response, Object rawData, DSRequest request) {   		        				        		
						Record[] data = response.getData();   		        		
													               
						if (data.length != 0) {   
							Record record = data[0];
							autoSearchDS.updateData(record);	
							autoSearchMenuItem.setAttribute("value", new Boolean(record.getAttribute("value")));								
						} else {
							Record record = new Record();
							record.setAttribute("prefName", "DEFAULT");
							record.setAttribute("value", "false");
							autoSearchDS.addData(record);		
							autoSearchMenuItem.setAttribute("value", false);			
						}									
					    contextMenu = new Menu();
						contextMenu = new Menu();		
						contextMenu.setOverflow(Overflow.VISIBLE);
						contextMenu.setShowIcons(true);
						contextMenu.setSelectionType(SelectionStyle.SINGLE);  
						contextMenu.setCanDragRecordsOut(false);
						contextMenu.setWidth("*");
						contextMenu.setHeight("*");
						contextMenu.addItem(autoSearchMenuItem);
					    Scheduler.get().scheduleDeferred(new ScheduledCommand() {
							@Override
							public void execute() {									
								contextMenu.showContextMenu();													
							}
					    });	
					}   
				});				
			}		
		}
	}
	
	public void manageOnCellClick(final Record record, final int recordNum) {			
		
	}
	
	public void manageOnDataArrived(DataArrivedEvent event) {
		TreeNode[] nodes = instance.getTree().getAllNodes();
		for(int i = 0; i < nodes.length; i++) {
			TreeNode node = nodes[i];
//			Record[] children = node.getAttributeAsRecordArray("children");
//			if(children.length > 0) {
			String flgEsplodiNodo = node.getAttributeAsString("flgEsplodiNodo"); 
			if("2".equals(flgEsplodiNodo)) {
				node.setIsFolder(true);				
				instance.getTree().openFolder(node);
			} else if("1".equals(flgEsplodiNodo)) {
				node.setIsFolder(true);
			} else if("-1".equals(flgEsplodiNodo)) {				
				node.setIsFolder(false);			
			} else if("0".equals(flgEsplodiNodo)) {
				node.setIsFolder(false);		
			}
			String azione = node.getAttributeAsString("azione");
			if(azione != null && !"".equals(azione)) {
				node.setIcon(nomeEntita+"/tipo/" + node.getAttributeAsString("tipo") + "_ricerca.png");				
			} else {
				node.setIcon(nomeEntita+"/tipo/" + node.getAttributeAsString("tipo") + ".png");			
			}
			node.setShowOpenIcon(false);
			node.setShowDropIcon(false);
			manageCustomTreeNode(node);	
		}	
	}	
	
	public void manageCustomTreeNode(TreeNode node) {
		
	}	
	
	public CustomSimpleTreeLayout getLayout() {
		return layout;
	}
	public void setLayout(CustomSimpleTreeLayout layout) {
		this.layout = layout;
	}

	public Record getCurrentRecord() {
		return currentRecord;
	}

	public void setCurrentRecord(Record currentRecord) {
		this.currentRecord = currentRecord;
	}

}
