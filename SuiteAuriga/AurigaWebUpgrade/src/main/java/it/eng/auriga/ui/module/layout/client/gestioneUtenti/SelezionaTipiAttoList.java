/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import com.smartgwt.client.types.FetchMode;
import com.smartgwt.client.types.ListGridFieldType;
import com.smartgwt.client.types.SelectionStyle;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridField;

public class SelezionaTipiAttoList extends ListGrid {

	private ListGridField idtipoAttoField;		
	private ListGridField denominazioneAttoField;
	private ListGridField flgAbilitazioneField;  
	
	public SelezionaTipiAttoList(String nomeEntita) {
		
		super();
		
		setWidth100();
		setHeight100();  
		setShowHeader(true);
		setAlternateRecordStyles(true);
		setLeaveScrollbarGap(false);		
		setKeepInParentRect(true);
		setLoadingMessage(null);
		setDataFetchMode(FetchMode.LOCAL);		
		setShowEmptyMessage(false);
		setShowRollOver(false);		
		setCanReorderFields(false);
		setCanResizeFields(true);
		setCanReorderRecords(false);
		setCanHover(true);		
		setCanGroupBy(false);	
		setCanSort(false);
		setShowHeaderContextMenu(false);
		setNoDoubleClicks(true); 
		setVirtualScrolling(true);      
		setSelectionType(SelectionStyle.NONE);
		setCanAdaptWidth(true);
		
		idtipoAttoField = new ListGridField("idtipoAtto", "Id. UO");                      
		idtipoAttoField.setHidden(true);
		
		denominazioneAttoField = new ListGridField("denominazioneAtto", "Denominazione");
		
		flgAbilitazioneField = new ListGridField("flgAbilitazione", "Abilitato"); 
		flgAbilitazioneField.setType(ListGridFieldType.BOOLEAN);
		
		setFields(idtipoAttoField, denominazioneAttoField, flgAbilitazioneField);
	}
}
