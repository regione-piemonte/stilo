/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import com.google.gwt.core.client.Scheduler;
import com.google.gwt.core.client.Scheduler.ScheduledCommand;
import com.smartgwt.client.data.DSCallback;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.data.SortSpecifier;
import com.smartgwt.client.types.ListGridFieldType;
import com.smartgwt.client.types.SortDirection;
import com.smartgwt.client.widgets.grid.CellFormatter;
import com.smartgwt.client.widgets.grid.HoverCustomizer;
import com.smartgwt.client.widgets.grid.ListGridRecord;
import com.smartgwt.client.widgets.menu.Menu;
import com.smartgwt.client.widgets.menu.MenuItem;
import com.smartgwt.client.widgets.menu.events.ClickHandler;
import com.smartgwt.client.widgets.menu.events.MenuItemClickEvent;
import com.smartgwt.client.widgets.tree.TreeGridField;
import com.smartgwt.client.widgets.tree.TreeNode;

import it.eng.utility.ui.module.core.shared.bean.TreeNodeBean;
import it.eng.utility.ui.module.layout.client.common.CustomAdvancedTree;
import it.eng.utility.ui.module.layout.client.common.NavigationContextMenu;
import it.eng.utility.ui.module.layout.client.common.NavigationContextMenu.NavigationContextMenuFrom;

public class TitolarioTree extends CustomAdvancedTree {

	public TitolarioTree(final String nomeEntita) {

		super(nomeEntita);

		TreeGridField idFolder = new TreeGridField("idFolder");
		idFolder.setHidden(true);

		TreeGridField nroLivello = new TreeGridField("nroLivello");
		nroLivello.setType(ListGridFieldType.INTEGER);
		nroLivello.setHidden(true);

		TreeGridField tipo = new TreeGridField("tipo");
		tipo.setHidden(true);

		TreeGridField nroProgr = new TreeGridField("nroProgr");
		nroProgr.setType(ListGridFieldType.INTEGER);
		nroProgr.setHidden(true);

		TreeGridField nome = new TreeGridField("nome");
		nome.setShowHover(true);
		nome.setCellFormatter(new CellFormatter() {							
			@Override
			public String format(Object value, ListGridRecord record, int rowNum,
					int colNum) {
				if(record != null) {
					record.setAttribute("realValue" + colNum, value);
				}
				if (value == null) return null;
				String lStringValue = value.toString();
				if (lStringValue.length() > 50){
					return lStringValue.substring(0, 50) + "...";
				} else return lStringValue;
			}
		});
		nome.setHoverCustomizer(new HoverCustomizer() {
			@Override
			public String hoverHTML(Object value, ListGridRecord record, int rowNum,
					int colNum) {
				Object realValue = record != null ? record.getAttribute("realValue" + colNum) : null;								
				String nome = (realValue != null) ? (String) realValue : (String) value;
				return "<b>" + nome + "</b><br/>" + record.getAttributeAsString("dettagli");
			}
		});

		TreeGridField indice = new TreeGridField("indice");
		indice.setHidden(true);
		
		TreeGridField iconaFlgClassificaChiusa = new TreeGridField("iconaFlgClassificaChiusa");
		iconaFlgClassificaChiusa.setWidth(60);
		iconaFlgClassificaChiusa.setCellFormatter(new CellFormatter() {

			@Override
			public String format(Object value, ListGridRecord record, int rowNum, int colNum) {
				if(record.getAttributeAsString("flgAttiva")!=null && "0".equals(record.getAttributeAsString("flgAttiva"))) {
					return "<div style=\"margin-left:10px;\" align=\"left\"><img src=\"images/" + nomeEntita + "/tipo/classificaChiusa.png\" height=\"16\" width=\"16\" alt=\"\" /></div>";
				} 
				return null;
			}
		});
		iconaFlgClassificaChiusa.setHoverCustomizer(new HoverCustomizer() {

			@Override
			public String hoverHTML(Object value, ListGridRecord record, int rowNum, int colNum) {
				if(record.getAttributeAsString("flgAttiva")!=null && "0".equals(record.getAttributeAsString("flgAttiva"))) {
					return "Classifica chiusa";
				}
				return null;
			}
		});

		setFields(idFolder, tipo, nroLivello, nroProgr, nome, iconaFlgClassificaChiusa);
	}

	@Override
	protected String getCellCSSText(ListGridRecord record, int rowNum, int colNum) {
		if (layout.isLookup() && record != null) {
			if (record.getAttributeAsBoolean("flgSelXFinalita")) {
				return "font-weight:bold; color:#1D66B2";
			} else {
				return "font-weight:normal; color:gray";
			}
		}
		return super.getCellCSSText(record, rowNum, colNum);
	}

	public void manageCustomTreeNode(TreeNode node) {
		String nroLivello = node.getAttributeAsString("nroLivello");
		Boolean flgEsplodibile = node.getAttributeAsBoolean("flgEsplodibile");
		String suffix = !flgEsplodibile ? "_NonEsplodibile" : "";
		if (nroLivello != null && !"".equals(nroLivello)) {
			node.setIcon(nomeEntita + "/tipo/" + nroLivello + suffix + ".png");
		} else {
			node.setIcon(nomeEntita + "/tipo/TITOLARIO.png");
		}
		node.setShowOpenIcon(false);
		node.setShowDropIcon(false);
	}

	@Override
	public void manageContextClick(final Record record) {

		if (record != null) {

			TreeNodeBean node = new TreeNodeBean();
			node.setIdNode(record.getAttributeAsString("idNode"));
			node.setIdFolder(record.getAttributeAsString("idFolder"));
			node.setIdLibreria(record.getAttributeAsString("idLibreria"));
			final Menu navigationContextMenu = new NavigationContextMenu(layout, node, NavigationContextMenuFrom.TREE_NODE, getRecordIndex(record));
			Scheduler.get().scheduleDeferred(new ScheduledCommand() {

				@Override
				public void execute() {
					navigationContextMenu.showContextMenu();
					navigationContextMenu.hide();
					showNodeContextMenu(getRecord(getRecordIndex(record)), navigationContextMenu);
				}
			});

		}
	}

	public void showNodeContextMenu(final Record record, Menu navigationContextMenu) {

		final Menu contextMenu = new Menu();
		final Menu altreOpMenu = createAltreOpMenu(record);
		for (int i = 0; i < altreOpMenu.getItems().length; i++) {
			contextMenu.addItem(altreOpMenu.getItems()[i], i);
		}

		if (navigationContextMenu != null) {
			for (int i = 0; i < navigationContextMenu.getItems().length; i++) {
				contextMenu.addItem(navigationContextMenu.getItems()[i], i);
			}
			if (contextMenu.getItems().length > navigationContextMenu.getItems().length) {
				MenuItem separatorMenuItem = new MenuItem();
				separatorMenuItem.setIsSeparator(true);
				contextMenu.addItem(separatorMenuItem, navigationContextMenu.getItems().length);
			}
		}

		contextMenu.showContextMenu();
	}

	public Menu createAltreOpMenu(final Record record) {

		Menu altreOpMenu = new Menu();

		final String idFolder = record.getAttribute("idFolder");
		final String nroLivello = record.getAttribute("nroLivello");
		final String indice = record.getAttribute("indice");

		if (idFolder != null && !"".equals(idFolder)) {

			MenuItem visualizzaMenuItem = new MenuItem("Visualizza", "buttons/detail.png");
			visualizzaMenuItem.addClickHandler(new ClickHandler() {

				@Override
				public void onClick(MenuItemClickEvent event) {
					Record recordToLoad = new Record();
					recordToLoad.setAttribute("idClassificazione", idFolder);
					getLayout().getDetail().markForRedraw();
					getLayout().getList().manageLoadDetail(recordToLoad, -1, new DSCallback() {

						@Override
						public void execute(DSResponse response, Object rawData, DSRequest request) {
							getLayout().viewMode();
							((TitolarioLayout) layout).setIdNodeToOpen(record.getAttribute("parentId"));
						}
					});
				}
			});
			altreOpMenu.addItem(visualizzaMenuItem);

			if (TitolarioLayout.isAbilToMod()) {
				MenuItem modificaMenuItem = new MenuItem("Modifica", "buttons/modify.png");
				modificaMenuItem.addClickHandler(new ClickHandler() {

					@Override
					public void onClick(MenuItemClickEvent event) {
						Record recordToLoad = new Record();
						recordToLoad.setAttribute("idClassificazione", idFolder);
						getLayout().getDetail().markForRedraw();
						getLayout().getList().manageLoadDetail(recordToLoad, -1, new DSCallback() {

							@Override
							public void execute(DSResponse response, Object rawData, DSRequest request) {
								getLayout().editMode();
								((TitolarioLayout) layout).setIdNodeToOpen(record.getAttribute("parentId"));
							}
						});
					}
				});
				altreOpMenu.addItem(modificaMenuItem);
			}

		}

		if (((TitolarioLayout) getLayout()).isAbilToInsInLivello(nroLivello != null ? new BigDecimal(nroLivello) : new BigDecimal(0))) {
			MenuItem nuovaClassificaItem = new MenuItem((idFolder != null && !"".equals(idFolder)) ? "Nuova sotto-classifica" : "Nuova classifica",
					"buttons/new.png");
			nuovaClassificaItem.addClickHandler(new ClickHandler() {

				@Override
				public void onClick(MenuItemClickEvent event) {
					Map<String, String> mappa = new HashMap<String, String>();
					mappa.put("nroLivello", nroLivello);
					mappa.put("idPianoClassif", ((TitolarioLayout) getLayout()).getIdPianoClassif());
					mappa.put("idClassificaSup", idFolder);
					mappa.put("livelloCorrente", indice);
					getLayout().getDetail().editNewRecord(mappa);
					getLayout().getDetail().getValuesManager().clearErrors(true);
					getLayout().newMode();
					((TitolarioLayout) layout).setIdNodeToOpen(record.getAttribute("idNode"));
				}
			});
			altreOpMenu.addItem(nuovaClassificaItem);
		}

		altreOpMenu.addSort(new SortSpecifier("title", SortDirection.ASCENDING));

		return altreOpMenu;
	}

	@Override
	protected boolean canToFirstLoadTree() {
		return (((TitolarioLayout) getLayout()).getIdPianoClassif() != null && !"".equals(((TitolarioLayout) getLayout()).getIdPianoClassif()));
	}

}