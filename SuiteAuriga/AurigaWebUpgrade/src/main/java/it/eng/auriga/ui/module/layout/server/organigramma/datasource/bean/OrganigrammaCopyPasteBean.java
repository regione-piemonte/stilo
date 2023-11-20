/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

public class OrganigrammaCopyPasteBean {

	private OrganigrammaTreeNodeBean copyNode;
	private OrganigrammaTreeNodeBean pasteNode;
	public OrganigrammaTreeNodeBean getCopyNode() {
		return copyNode;
	}
	public void setCopyNode(OrganigrammaTreeNodeBean copyNode) {
		this.copyNode = copyNode;
	}
	public OrganigrammaTreeNodeBean getPasteNode() {
		return pasteNode;
	}
	public void setPasteNode(OrganigrammaTreeNodeBean pasteNode) {
		this.pasteNode = pasteNode;
	}
	
}
