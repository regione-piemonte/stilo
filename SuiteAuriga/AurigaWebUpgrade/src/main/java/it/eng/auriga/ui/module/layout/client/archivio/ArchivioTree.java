/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import com.smartgwt.client.types.ListGridFieldType;
import com.smartgwt.client.widgets.grid.HoverCustomizer;
import com.smartgwt.client.widgets.grid.ListGridRecord;
import com.smartgwt.client.widgets.tree.TreeGridField;
import com.smartgwt.client.widgets.tree.TreeNode;

import it.eng.utility.ui.module.layout.client.StringSplitterClient;
import it.eng.utility.ui.module.layout.client.common.CustomAdvancedTree;

public class ArchivioTree extends CustomAdvancedTree {

	public ArchivioTree(String nomeEntita) {

		super(nomeEntita);

		TreeGridField idFolder = new TreeGridField("idFolder");
		idFolder.setHidden(true);

		TreeGridField nroLivello = new TreeGridField("nroLivello");
		nroLivello.setType(ListGridFieldType.INTEGER);
		nroLivello.setHidden(true);

		TreeGridField nroProgr = new TreeGridField("nroProgr");
		nroProgr.setType(ListGridFieldType.INTEGER);
		nroProgr.setHidden(true);

		TreeGridField nome = new TreeGridField("nome");
		nome.setFrozen(true);
		nome.setShowHover(true);
		nome.setHoverCustomizer(new HoverCustomizer() {

			@Override
			public String hoverHTML(Object value, ListGridRecord record, int rowNum, int colNum) {
				
				return "<b>" + record.getAttributeAsString("nome") + "</b><br/>" + record.getAttributeAsString("dettagli");
			}
		});

		setFields(idFolder, nroLivello, nroProgr, nome);
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
		String nroFascicolo = node.getAttributeAsString("nroFascicolo");
		String nroSottofascicolo = node.getAttributeAsString("nroSottofascicolo");
		String nroInserto = node.getAttributeAsString("nroInserto");
		Boolean flgEsplodibile = node.getAttributeAsBoolean("flgEsplodibile");
		String suffix = !flgEsplodibile ? "_NonEsplodibile" : "";
		if (nroInserto != null && !"".equals(nroInserto)) {
			node.setIcon(nomeEntita + "/flgUdFolder/inserto" + suffix + ".png");
		} else if (nroSottofascicolo != null && !"".equals(nroSottofascicolo)) {
			node.setIcon(nomeEntita + "/flgUdFolder/sottofascicolo" + suffix + ".png");
		} else if (nroFascicolo != null && !"".equals(nroFascicolo)) {
			node.setIcon(nomeEntita + "/flgUdFolder/fascicolo" + suffix + ".png");
		} else {
			node.setIcon(nomeEntita + "/flgUdFolder/F" + suffix + ".png");
		}
		node.setShowOpenIcon(false);
		node.setShowDropIcon(false);
	}

	@Override
	public void reloadSubTreeFrom(String idNode) {
		if (idNode != null && !"".equals(idNode)) {
			String[] idNodeArray = new StringSplitterClient(idNode, "|*|").getTokens();
			if (idNodeArray != null && idNodeArray.length > 0) {
				for (int i = 0; i < idNodeArray.length; i++) {
					super.reloadSubTreeFrom(idNodeArray[i]);
				}
			}
		}
	}
}