/* * SPDX-License-Identifier: AGPL-3.0-or-later * * (C) Copyright 2023 Regione Piemonte * */
package it.eng.utility.ui.module.layout.client.common;

public interface TreeSectionVisibility {
				
	public void showTree();
	
	public void hideTree();
	
	public boolean isTreeVisible(); 
	
	public int getTreeSectionDefaultWidth();
	
	public int getTreeSectionWidth();
	
	public void setTreeSectionWidth(int width);
	
	public String getPrefKeyPrefixForPortlet();

}
