/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import com.google.gwt.core.client.Scheduler;
import com.google.gwt.core.client.Scheduler.ScheduledCommand;
import com.google.gwt.user.client.Timer;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.types.DateDisplayFormat;
import com.smartgwt.client.types.FetchMode;
import com.smartgwt.client.types.SelectionStyle;
import com.smartgwt.client.types.SortDirection;
import com.smartgwt.client.util.EventHandler;
import com.smartgwt.client.widgets.events.RightMouseDownEvent;
import com.smartgwt.client.widgets.events.RightMouseDownHandler;
import com.smartgwt.client.widgets.grid.ListGridRecord;
import com.smartgwt.client.widgets.grid.events.BodyKeyPressEvent;
import com.smartgwt.client.widgets.grid.events.BodyKeyPressHandler;
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
import com.smartgwt.client.widgets.tree.TreeGrid;
import com.smartgwt.client.widgets.tree.TreeNode;
import com.smartgwt.client.widgets.tree.events.FolderClosedEvent;
import com.smartgwt.client.widgets.tree.events.FolderClosedHandler;
import com.smartgwt.client.widgets.tree.events.FolderOpenedEvent;
import com.smartgwt.client.widgets.tree.events.FolderOpenedHandler;

import it.eng.utility.ui.module.core.client.callback.ServiceCallback;
import it.eng.utility.ui.module.core.client.datasource.GWTRestDataSource;
import it.eng.utility.ui.module.core.client.i18n.I18NUtil;
import it.eng.utility.ui.module.core.shared.bean.TreeNodeBean;
import it.eng.utility.ui.module.core.shared.message.MessageBean;
import it.eng.utility.ui.module.core.shared.message.MessageType;
import it.eng.utility.ui.module.layout.client.Layout;
import it.eng.utility.ui.module.layout.client.common.NavigationContextMenu.NavigationContextMenuFrom;
import it.eng.utility.ui.module.core.client.UserInterfaceFactory;

public class CustomAdvancedTree extends TreeGrid {

	protected CustomAdvancedTreeLayout layout;
	protected CustomAdvancedTree instance;

	protected final String nomeEntita;
	protected ListGridRecord mouseOverRecord;
	protected NavigationContextMenu contextMenu;

	protected boolean isFirstLoad;
	// protected boolean isFirstLoadAndEsplora;
	protected boolean skipPercorsoIniziale;
	protected boolean isReload;

	// protected String idNodeToOpen;
	protected String idRootNode;

	public CustomAdvancedTree(String nomeEntita) {
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
		setAutoFetchData(false);
		setDataFetchMode(FetchMode.BASIC);
		setShowAllRecords(false); /* false per ovviare al problema dello scroll sull'albero*/
		setLoadDataOnDemand(true);
		setFixedRecordHeights(false);
		setShowRecordComponents(true);
		setShowRecordComponentsByCell(false);
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
		setDateFormatter(DateDisplayFormat.TOEUROPEANSHORTDATE);
		setDatetimeFormatter(DateDisplayFormat.TOEUROPEANSHORTDATETIME);
		setNodeIcon("tree/file.png");
		setFolderIcon("tree/folder.png");
		setNoDoubleClicks(true);
		setSortFoldersBeforeLeaves(true);
		setSortField("nroProgr");
		setSortDirection(SortDirection.ASCENDING);
		setHoverDelay(0);
		
		setSelectionType(SelectionStyle.NONE);
		setShowSelectedStyle(false);

		addFolderOpenedHandler(new FolderOpenedHandler() {

			@Override
			public void onFolderOpened(FolderOpenedEvent event) {
				manageOpenFolder(event.getNode());
			}
		});

		addFolderClosedHandler(new FolderClosedHandler() {

			@Override
			public void onFolderClosed(FolderClosedEvent event) {
				TreeNode[] nodes = instance.getTree().getAllNodes();
				String idRootNode = nodes != null && nodes.length > 0 ? nodes[0].getAttributeAsString("idNode") : "/";
				if(event.getNode().getAttributeAsString("idNode") != null && !event.getNode().getAttributeAsString("idNode").equals("/")
						&& !event.getNode().getAttributeAsString("idNode").equals(idRootNode) ) {
					event.getNode().setAttribute("flgEsplodiNodo", 1);
					getTree().unloadChildren(event.getNode());
				}
			}
		});

		addDataArrivedHandler(new DataArrivedHandler() {

			@Override
			public void onDataArrived(DataArrivedEvent event) {
				Layout.hideWaitPopup();
				manageOnDataArrived(event);
			}
		});

		addCellClickHandler(new CellClickHandler() {

			@Override
			public void onCellClick(CellClickEvent event) {
				event.cancel();
				manageOnCellClick(event);
			}
		});

		if (Layout.isFirefoxBrowser()) {

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
					event.cancel();
					manageContextClick(mouseOverRecord);
				}
			});

		} else {

			addCellContextClickHandler(new CellContextClickHandler() {

				@Override
				public void onCellContextClick(CellContextClickEvent event) {
					Record record = event.getRecord();
					event.cancel(); // per inibire il contextmenu del browser
					manageContextClick(record);
				}
			});

		}
		if (UserInterfaceFactory.isAttivaAccessibilita()){
			addBodyKeyPressHandler(new BodyKeyPressHandler() {
	            
	            @Override
	            public void onBodyKeyPress(BodyKeyPressEvent event) {
	                if (EventHandler.getKey().equalsIgnoreCase("Enter") == true) {
						Integer focusRow2 = getFocusRow();
						ListGridRecord record = getRecord(focusRow2);
						manageOnBodyPressHandler(record);
	//                    System.out.println("ENTER PRESSED !!!!" + listGrid.getSelectedRecord());
	                }
	            }
	        });
 		}
	}

	public void manageOpenFolder(TreeNode node) {
		((GWTRestDataSource) instance.getDataSource()).addParam("idRootNode", node.getAttributeAsString("idNode"));
		((GWTRestDataSource) instance.getDataSource()).addParam("nroProgrRootNode", node.getAttributeAsString("nroProgr"));
		((GWTRestDataSource) instance.getDataSource()).addParam("openRootNode", "true");
		// ((GWTRestDataSource) instance.getDataSource()).addParam("idNodeToOpen", idNodeToOpen);
	}

	public void manageReloadFolder(TreeNode node, boolean forceToOpenNode) {
		((GWTRestDataSource) instance.getDataSource()).addParam("idRootNode", node.getAttributeAsString("idNode"));
		((GWTRestDataSource) instance.getDataSource()).addParam("nroProgrRootNode", node.getAttributeAsString("nroProgr"));
		((GWTRestDataSource) instance.getDataSource()).addParam("openRootNode", forceToOpenNode || getTree().isOpen(node) ? "true" : "");
		// ((GWTRestDataSource) instance.getDataSource()).addParam("idNodeToOpen", idNodeToOpen);
	}

	public void manageContextClick(final Record record) {
		if (record != null) {
			TreeNodeBean node = new TreeNodeBean();
			node.setIdNode(record.getAttributeAsString("idNode"));
			node.setIdFolder(record.getAttributeAsString("idFolder"));
			node.setIdLibreria(record.getAttributeAsString("idLibreria"));
			contextMenu = new NavigationContextMenu(layout, node, NavigationContextMenuFrom.TREE_NODE, getRecordIndex(record));
			Scheduler.get().scheduleDeferred(new ScheduledCommand() {

				@Override
				public void execute() {
					contextMenu.showContextMenu();
				}
			});
		}
	}

	public void firstLoadTree(String idRootNode) {
		firstLoadTree(idRootNode, null);
	}

	// public void firstLoadTreeAndEsploraNodeToOpen(String idRootNode) {
	// isFirstLoadAndEsplora = true;
	// firstLoadTree(idRootNode, new ServiceCallback<String>() {
	//
	// @Override
	// public void execute(String idNodeToOpen) {
	// ((CustomAdvancedTreeLayout) getLayout()).esplora(idNodeToOpen);
	// }
	// });
	// }

	protected boolean canToFirstLoadTree() {
		return true;
	}

	private void firstLoadTree(String idRootNode, final ServiceCallback<String> callback) {
		if (canToFirstLoadTree()) {
			isFirstLoad = true;
			((GWTRestDataSource) instance.getDataSource()).addParam("isFirstLoad", "true");
			// Viene fatto solo la prima volta e setta la root dell'albero
			if (getIdRootNode() == null || getIdRootNode().equals("")) {
				setIdRootNode(idRootNode);
			}
			((GWTRestDataSource) instance.getDataSource()).addParam("idRootNode", idRootNode);
			// ((GWTRestDataSource) instance.getDataSource()).addParam("idNodeToOpen", idNodeToOpen);
			((GWTRestDataSource) instance.getDataSource()).addParam("nroProgrRootNode", "1");
			((GWTRestDataSource) instance.getDataSource()).addParam("finalita", layout.getFinalita());
			// if (idNodeToOpen != null && !idNodeToOpen.equals("")) {
			// TreeNode nodeToOpen = getTree().find("idNode", idNodeToOpen);
			// if (nodeToOpen != null && getTree().isOpen(nodeToOpen)) {
			// skipPercorsoIniziale = true;
			// fetchData(null, new DSCallback() {
			//
			// @Override
			// public void execute(DSResponse response, Object rawData, DSRequest request) {
			// if (callback != null) {
			// callback.execute(idNodeToOpen);
			// }
			// idNodeToOpen = null;
			// }
			// });
			// } else {
			// if (callback != null) {
			// callback.execute(idNodeToOpen);
			// }
			// idNodeToOpen = null;
			// }
			// } else {
			fetchData();
			// }
		}
	}

	public void reloadSubTreeFrom(String idNode) {
		reloadSubTreeFrom(idNode, false);
	}

	public void reloadSubTreeFrom(String idNode, boolean forceToOpenNode) {
		if (idNode != null && !"".equals(idNode)) {
			TreeNode node = getTree().findById(idNode);
			if (node != null) {
				try {
					TreeNode[] parents = getTree().getParents(node);
					if (parents != null && parents.length > 0) {
						if(parents.length == 1 && getTree().isRoot(parents[0])) {
							reloadTreeFrom(idNode);
							return;
						} 
						boolean reload = true;
						for (int i = 0; i < parents.length; i++) {
							if (!getTree().isOpen(parents[i])) {
								reload = false;
							}
						}
						if (reload) {
							manageReloadFolder(node, forceToOpenNode);
							getTree().reloadChildren(node);
							markForRedraw();
						}						
					} 
				} catch (Exception e) {
				}
			}
		}
	}

	public void reloadTreeFrom(String idNode) {
		isReload = true;
		((GWTRestDataSource) instance.getDataSource()).addParam("idRootNode", idNode);
		((GWTRestDataSource) instance.getDataSource()).addParam("finalita", layout.getFinalita());
		((GWTRestDataSource) instance.getDataSource()).addParam("openRootNode", "true");
		invalidateCache();
	}

	@Override
	public native void scrollToRow(int rowNum) /*-{
		var self = this.@com.smartgwt.client.widgets.BaseWidget::getOrCreateJsObj()();
		self.scrollToRow(rowNum);
	}-*/;

	public void manageOnDataArrived(DataArrivedEvent event) {
		instance.sort("nroProgr", SortDirection.ASCENDING);
		TreeNode[] nodes = instance.getTree().getAllNodes();
		for (int i = 0; i < nodes.length; i++) {
			TreeNode node = nodes[i];
			// Record[] children = node.getAttributeAsRecordArray("children");
			// if(children.length > 0) {
			String flgEsplodiNodo = node.getAttributeAsString("flgEsplodiNodo");
			node.setAttribute("flgEsplodibile", true);
			if ("2".equals(flgEsplodiNodo)) {
				node.setIsFolder(true);
				instance.getTree().openFolder(node);
			} else if ("1".equals(flgEsplodiNodo)) {
				node.setIsFolder(true);
			} else if ("-1".equals(flgEsplodiNodo)) {
				node.setIsFolder(false);
				node.setAttribute("flgEsplodibile", false);
			} else if ("0".equals(flgEsplodiNodo)) {
				node.setIsFolder(false);
			}
			node.setIcon("tree/folder.png");
			// if (idNodeToOpen != null && !idNodeToOpen.equals("")) {
			// // getTree().openFolders(getTree().getParents(getTree().find("idNode", idNodeToOpen)));
			// StringTokenizer st = new StringTokenizer(idNodeToOpen, "/");
			// String idNode = "";
			// for (int j = 0; j < st.getTokens().length; j++) {
			// String idFolder = st.getTokens()[j];
			// if (!idFolder.equals("")) {
			// idNode += "/" + idFolder;
			// if (node.getAttribute("idNode").equals(idNode)) {
			// getTree().openFolder(node);
			// }
			// }
			// }
			// try {
			// final int index = !"".equals(idNode) ? getRecordIndex(getTree().find("idNode", idNode)) : -1;
			// if (index >= 0) {
			// Scheduler.get().scheduleDeferred(new ScheduledCommand() {
			//
			// @Override
			// public void execute() {
			// try {
			// scrollToRow(index);
			// } catch (Exception e) {
			// }
			// }
			// });
			// }
			// } catch (Exception e) {
			// }
			// }
			manageCustomTreeNode(node);
		}

		if (isFirstLoad) {
			((GWTRestDataSource) instance.getDataSource()).addParam("isFirstLoad", null);
			if (!skipPercorsoIniziale/* || isFirstLoadAndEsplora */) {
				layout.setPercorsoIniziale();
			}
		}

		if (isFirstLoad || isReload) {
			if (instance.getData().getLength() > 0) {
				selectSingleRecord(0);
			}
		}

		isFirstLoad = false;
		// isFirstLoadAndEsplora = false;
		isReload = false;
		skipPercorsoIniziale = false;
	}

	public void manageCustomTreeNode(TreeNode node) {

	}

	public void manageOnCellClick(final CellClickEvent event) {
		final Record record = event.getRecord();

		Timer t1 = new Timer() {

			public void run() {
				instance.setAttribute("nodeClicked", -1, true);
			}
		};
		Timer t2 = new Timer() {

			public void run() {
				if (instance.getAttribute("nodeDoubleClicked") == null
						|| !(Integer.valueOf(instance.getAttribute("nodeDoubleClicked")).intValue() == getRecordIndex(record))) {
					if (isExplorableTreeNode(record)) {
						// selectSingleRecord(getRecordIndex(record));
						layout.esplora(record.getAttributeAsString("idNode"));
					} else {
						Layout.addMessage(new MessageBean(I18NUtil.getMessages().tree_nodoNonEsplodibile_message(), "", MessageType.ERROR));
					}
				} else {
					if (layout.isLookup()) {
						record.setAttribute("from", "T");
						layout.doLookup(record);
					}
				}
				instance.setAttribute("nodeDoubleClicked", -1, true);
			}
		};
		if (instance.getAttribute("nodeClicked") != null && Integer.valueOf(instance.getAttribute("nodeClicked")).intValue() == getRecordIndex(record)) {
			instance.setAttribute("nodeDoubleClicked", getRecordIndex(record), true);
		} else {
			instance.setAttribute("nodeClicked", getRecordIndex(record), true);
			t1.schedule(Layout.getGenericConfig().getDelayTimeForDoubleClick());
			t2.schedule(Layout.getGenericConfig().getDelayTimeForDoubleClick() + 100);
		}
	}

	public boolean isExplorableTreeNode(Record record) {
		return true;
	}

	public CustomLayout getLayout() {
		return layout;
	}

	public void setLayout(CustomAdvancedTreeLayout layout) {
		this.layout = layout;
	}

	public String getIdRootNode() {
		return idRootNode;
	}

	public void setIdRootNode(String idRootNode) {
		this.idRootNode = idRootNode;
	}

	// public String getIdNodeToOpen() {
	// return idNodeToOpen;
	// }
	//
	// public void setIdNodeToOpen(String idNodeToOpen) {
	// this.idNodeToOpen = idNodeToOpen;
	// }
	
	public void manageOnBodyPressHandler(final Record record) {

		if (layout.isLookup()) {
			record.setAttribute("from", "L");
			getLayout().doLookup(record);			
		} else {
			Timer t1 = new Timer() {
				
				public void run() {
					instance.setAttribute("nodeClicked", -1, true);
				}
			};
			Timer t2 = new Timer() {
				
				public void run() {
					if (instance.getAttribute("nodeDoubleClicked") == null
							|| !(Integer.valueOf(instance.getAttribute("nodeDoubleClicked")).intValue() == getRecordIndex(record))) {
						if (isExplorableTreeNode(record)) {
							// selectSingleRecord(getRecordIndex(record));
							layout.esplora(record.getAttributeAsString("idNode"));
						} else {
							Layout.addMessage(new MessageBean(I18NUtil.getMessages().tree_nodoNonEsplodibile_message(), "", MessageType.ERROR));
						}
					} else {
						if (layout.isLookup()) {
							record.setAttribute("from", "T");
							layout.doLookup(record);
						}
					}
					instance.setAttribute("nodeDoubleClicked", -1, true);
				}
			};
			if (instance.getAttribute("nodeClicked") != null && Integer.valueOf(instance.getAttribute("nodeClicked")).intValue() == getRecordIndex(record)) {
				instance.setAttribute("nodeDoubleClicked", getRecordIndex(record), true);
			} else {
				instance.setAttribute("nodeClicked", getRecordIndex(record), true);
				t1.schedule(Layout.getGenericConfig().getDelayTimeForDoubleClick());
				t2.schedule(Layout.getGenericConfig().getDelayTimeForDoubleClick() + 100);
			}
		}
	}
	
}
