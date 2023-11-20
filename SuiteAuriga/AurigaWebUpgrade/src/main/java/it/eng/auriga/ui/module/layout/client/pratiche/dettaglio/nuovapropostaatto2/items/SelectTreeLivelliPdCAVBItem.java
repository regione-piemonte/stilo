/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import com.smartgwt.client.types.Overflow;
import com.smartgwt.client.types.SelectionStyle;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.events.CellClickEvent;
import com.smartgwt.client.widgets.grid.events.CellClickHandler;
import com.smartgwt.client.widgets.tree.TreeGrid;

import it.eng.utility.ui.module.core.client.i18n.I18NUtil;
import it.eng.utility.ui.module.layout.client.common.items.SelectItem;

public class SelectTreeLivelliPdCAVBItem extends SelectItem {
	
	public SelectTreeLivelliPdCAVBItem(){	
		super();
		init();
	}
	
	public SelectTreeLivelliPdCAVBItem(String name) {
		super(name);
		init();
    }  

	public SelectTreeLivelliPdCAVBItem(String name, String title) {
		super(name, title);
		init();
    }  
	
	@Override
	protected ListGrid builPickListProperties() {
		final TreeGrid pickListProperties = new TreeGrid();  
		pickListProperties.setEmptyMessage(I18NUtil.getMessages().list_emptyMessage());
		pickListProperties.setSelectionType(SelectionStyle.NONE);
		pickListProperties.setShowSelectedStyle(false);
		pickListProperties.addCellClickHandler(new CellClickHandler() {	
			
			@Override
			public void onCellClick(CellClickEvent event) {
				onOptionClick(event.getRecord());
			}
		});
		pickListProperties.setShowHeader(false);
        pickListProperties.setAutoFitFieldWidths(true); 
        pickListProperties.setShowAllRecords(true);
        pickListProperties.setBodyOverflow(Overflow.VISIBLE);
        pickListProperties.setOverflow(Overflow.VISIBLE);
        pickListProperties.setLeaveScrollbarGap(false);
        /*
         * Impedisce il ricaricamento generale dell'albero ad ogni esplosione dei nodi anche 
         * se nodi foglia
         */
        pickListProperties.setLoadDataOnDemand(false);
        pickListProperties.setNodeIcon("blank.png");
        pickListProperties.setFolderIcon("blank.png");
        return pickListProperties;
	}
	
	protected void init() {

		
	}
	
}