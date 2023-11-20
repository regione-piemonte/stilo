/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.utility.ui.module.core.client.datasource.GWTRestDataSource;
import it.eng.utility.ui.module.layout.client.common.CustomSimpleTree;

import com.smartgwt.client.widgets.grid.CellFormatter;
import com.smartgwt.client.widgets.grid.HoverCustomizer;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.grid.ListGridRecord;
import com.smartgwt.client.widgets.grid.events.DataArrivedEvent;
import com.smartgwt.client.widgets.grid.events.RecordClickEvent;
import com.smartgwt.client.widgets.grid.events.RecordClickHandler;
import com.smartgwt.client.widgets.tree.TreeGridField;
import com.smartgwt.client.widgets.tree.TreeNode;

public class AttiInLavorazioneSimpleTree extends CustomSimpleTree {

	private TreeGridField iconMsgAcc;

	public AttiInLavorazioneSimpleTree(String nomeEntita) {
		super(nomeEntita);

		TreeGridField idFolder = new TreeGridField("idFolder");
		idFolder.setHidden(true);

		TreeGridField nome = new TreeGridField("nome");
		nome.setShowHover(true);
		nome.setHoverCustomizer(new HoverCustomizer() {

			@Override
			public String hoverHTML(Object value, ListGridRecord record, int rowNum, int colNum) {
				String nome = record.getAttribute("dettagli");
				return nome;
			}
		});

		ListGridField iconMsgAss = buildNoteButtonField();

		// iconMsgAcc = new TreeGridField("iconMessaggio", "Messaggio associato");
		// iconMsgAcc.setType(ListGridFieldType.ICON);
		// iconMsgAcc.setIconSize(16);
		// iconMsgAcc.setIconWidth(16);
		// iconMsgAcc.setIconHeight(16);
		// iconMsgAcc.setAlign(Alignment.CENTER);
		// iconMsgAcc.setPrompt("Messaggio associato");
		// iconMsgAcc.setIcon("pratiche/task/note.png");
		// iconMsgAcc.addRecordClickHandler(new RecordClickHandler() {
		//
		// @Override
		// public void onRecordClick(RecordClickEvent event) {
		// 
		// String msgAss = event.getRecord().getAttribute("messaggio");
		// showUltimaAttivita(msgAss);
		// }
		// });
		// iconMsgAcc.setHoverCustomizer(new HoverCustomizer() {
		//
		// @Override
		// public String hoverHTML(Object value, ListGridRecord record, int rowNum, int colNum) {
		// 
		// return "Messaggio associato";
		// }
		// });

		setFields(idFolder, nome, iconMsgAss);
	}

	private void showUltimaAttivita(String msgAss) {
		final MessaggioUltimaAttivitaTreeNodeWindow lMessaggioUltimaAttivitaTreeNodeWindow = new MessaggioUltimaAttivitaTreeNodeWindow(msgAss);
		lMessaggioUltimaAttivitaTreeNodeWindow.show();

	}

	public void loadTree(String idProcessIn, String codTipoEventoIn, String desEventoIn, String desUserIn, String desEsitoIn, String dettagliEventoIn,
			String idSelectedNodeIO, String flgExplSelNodeIn, String flgNextPrevNodeIn) {
		((GWTRestDataSource) instance.getDataSource()).addParam("isFirstLoad", "true");
		((GWTRestDataSource) instance.getDataSource()).addParam("idProcessIn", idProcessIn);
		((GWTRestDataSource) instance.getDataSource()).addParam("codTipoEventoIn", codTipoEventoIn);
		((GWTRestDataSource) instance.getDataSource()).addParam("desEventoIn", desEventoIn);
		((GWTRestDataSource) instance.getDataSource()).addParam("desUserIn", desUserIn);
		((GWTRestDataSource) instance.getDataSource()).addParam("desEsitoIn", desEsitoIn);
		((GWTRestDataSource) instance.getDataSource()).addParam("dettagliEventoIn", dettagliEventoIn);
		((GWTRestDataSource) instance.getDataSource()).addParam("idSelectedNodeIO", idSelectedNodeIO);
		((GWTRestDataSource) instance.getDataSource()).addParam("flgExplSelNodeIn", flgExplSelNodeIn);
		((GWTRestDataSource) instance.getDataSource()).addParam("flgNextPrevNodeIn", flgNextPrevNodeIn);

		redraw();
	}

	public void refreshTree(String idProcessIn, String codTipoEventoIn, String desEventoIn, String desUserIn, String desEsitoIn, String dettagliEventoIn,
			String idSelectedNodeIO, String flgExplSelNodeIn, String flgNextPrevNodeIn) {
		((GWTRestDataSource) instance.getDataSource()).addParam("isFirstLoad", "true");
		((GWTRestDataSource) instance.getDataSource()).addParam("idProcessIn", idProcessIn);
		((GWTRestDataSource) instance.getDataSource()).addParam("codTipoEventoIn", codTipoEventoIn);
		((GWTRestDataSource) instance.getDataSource()).addParam("desEventoIn", desEventoIn);
		((GWTRestDataSource) instance.getDataSource()).addParam("desUserIn", desUserIn);
		((GWTRestDataSource) instance.getDataSource()).addParam("desEsitoIn", desEsitoIn);
		((GWTRestDataSource) instance.getDataSource()).addParam("dettagliEventoIn", dettagliEventoIn);
		((GWTRestDataSource) instance.getDataSource()).addParam("idSelectedNodeIO", idSelectedNodeIO);
		((GWTRestDataSource) instance.getDataSource()).addParam("flgExplSelNodeIn", flgExplSelNodeIn);
		((GWTRestDataSource) instance.getDataSource()).addParam("flgNextPrevNodeIn", flgNextPrevNodeIn);

		fetchData();
	}

	@Override
	public void manageOnDataArrived(DataArrivedEvent event) {
		TreeNode[] nodes = instance.getTree().getAllNodes();
		for (int i = 0; i < nodes.length; i++) {
			TreeNode node = nodes[i];
			String flgEsplodiNodo = node.getAttributeAsString("flgEsplodiNodo");
			if ("2".equals(flgEsplodiNodo)) {
				node.setIsFolder(true);
				node.setCanExpand(true);
				instance.getTree().openFolder(node);
			} else if ("1".equals(flgEsplodiNodo)) {
				node.setIsFolder(true);
				node.setCanExpand(true);
			} else if ("0".equals(flgEsplodiNodo)) {
				node.setIsFolder(false);
				node.setCanExpand(false);
			}
			String tipo = node.getAttributeAsString("tipo");
			if ("#F".equalsIgnoreCase(tipo)) {
				node.setIcon(nomeEntita + "/F.png");
			} else {
				node.setIcon(nomeEntita + "/A.png");
			}
			String matchAttr = node.getAttributeAsString("matchaFiltro");
			if ((matchAttr != null) && ("1".equalsIgnoreCase(matchAttr))) {
				instance.getTree().openFolder(instance.getTree().getParent(node));
				node.setAttribute("nome", "<font style=\"font-weight: bold\">" + node.getAttribute("nome") + "</br>"); // colonna 4 dell'xml
			}
			node.setShowOpenIcon(false);
			node.setShowDropIcon(false);
			manageCustomTreeNode(node);
		}
	}

	protected ListGridField buildNoteButtonField() {
		ListGridField noteButtonField = new ListGridField("noteButton");
		noteButtonField.setAttribute("custom", true);
		noteButtonField.setShowHover(true);
		noteButtonField.setCanReorder(false);
		noteButtonField.setCellFormatter(new CellFormatter() {

			@Override
			public String format(Object value, ListGridRecord record, int rowNum, int colNum) {
				String msgAss = record.getAttribute("messaggio");
				String tipo = record.getAttribute("tipo");
				if (msgAss != null && tipo != null && !"".equals(msgAss) && !"#F".equalsIgnoreCase(tipo)) {
					return buildImgButtonHtml("pratiche/task/note.png");
				}
				return null;
			}
		});
		noteButtonField.setHoverCustomizer(new HoverCustomizer() {

			@Override
			public String hoverHTML(Object value, ListGridRecord record, int rowNum, int colNum) {
				
				String msgAss = record.getAttribute("messaggio");
				if (msgAss != null && !"".equals(msgAss)) {
					return "Messaggio associato";
				}
				return null;
			}
		});
		noteButtonField.addRecordClickHandler(new RecordClickHandler() {

			@Override
			public void onRecordClick(RecordClickEvent event) {
				
				event.cancel();
				ListGridRecord record = getRecord(event.getRecordNum());
				String msgAss = record.getAttribute("messaggio");
				if (msgAss != null && !"".equals(msgAss)) {
					showUltimaAttivita(msgAss);
				}
			}
		});
		return noteButtonField;
	}

	protected String buildImgButtonHtml(String src) {
		return "<div style=\"cursor:pointer\" align=\"left\"><img src=\"images/" + src + "\" height=\"16\" width=\"16\" alt=\"\" /></div>";
	}

}
