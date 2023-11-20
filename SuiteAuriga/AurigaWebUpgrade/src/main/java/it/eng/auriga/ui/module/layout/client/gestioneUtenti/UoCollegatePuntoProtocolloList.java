/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import com.smartgwt.client.data.Record;
import com.smartgwt.client.types.FetchMode;
import com.smartgwt.client.types.ListGridFieldType;
import com.smartgwt.client.types.SelectionStyle;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.grid.ListGridRecord;
import com.smartgwt.client.widgets.grid.events.ChangedEvent;
import com.smartgwt.client.widgets.grid.events.ChangedHandler;

public class UoCollegatePuntoProtocolloList extends ListGrid{
	
	// ListGridField
	private ListGridField idUOField;		
	private ListGridField flgAbilitazioneEreditataField;
	private ListGridField idUoAbilitazioneEreditataField;
	private ListGridField livelliUOField;
	private ListGridField denominazioneUOField;
	private ListGridField flgAbilitazioneField;  
	private ListGridField flgAbilitaEreditarietaField;

	public UoCollegatePuntoProtocolloList(String nomeEntita) {
		
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
		
		idUOField = new ListGridField("idUO", "Id. UO");                      
		idUOField.setHidden(true);
		
		flgAbilitazioneEreditataField = new ListGridField("flgAbilitazioneEreditata", "Ab. Ereditata");                      
		flgAbilitazioneEreditataField.setHidden(true);
		
		idUoAbilitazioneEreditataField = new ListGridField("idUoAbilitazioneEreditata", "Id .UO Ereditata");
		idUoAbilitazioneEreditataField.setType(ListGridFieldType.BOOLEAN);
		idUoAbilitazioneEreditataField.setHidden(true);
		
		livelliUOField = new ListGridField("livelliUO", "Codice UO");
		livelliUOField.setWidth(200);
		
		denominazioneUOField = new ListGridField("denominazioneUO", "Denominazione UO");
		
		flgAbilitazioneField = new ListGridField("flgAbilitazione", "Abilitato"); 
		flgAbilitazioneField.setType(ListGridFieldType.BOOLEAN);
		flgAbilitazioneField.addChangedHandler(new ChangedHandler() {
			
			@Override
			public void onChanged(ChangedEvent event) {
				boolean abilUoSelezionata = event.getValue() != null ? (Boolean) event.getValue() : false;
				int selectedRow = event.getRowNum();
				String livUoSelezionata = getRecord(selectedRow).getAttribute("livelliUO");
				boolean abilitaEreditarieta = Boolean.valueOf(getRecord(selectedRow).getAttribute("flgAbilitaEreditarieta"));
				if (abilitaEreditarieta) {
					// Ciclo su tutti gli elementi della lista
					for (ListGridRecord r : getRecords()) {
						if (isSottoUo(livUoSelezionata, r.getAttribute("livelliUO"))) {
							r.setAttribute("flgAbilitazione", abilUoSelezionata);
							r.setAttribute("flgAbilitaEreditarieta", false);
							// settare riga in base alla abilitazione ereditarietà
						}
					}	
				}
			}
		});
		
		flgAbilitaEreditarietaField = new ListGridField("flgAbilitaEreditarieta", "Abilita ereditarietà");
		flgAbilitaEreditarietaField.setWrap(true);
		flgAbilitaEreditarietaField.setType(ListGridFieldType.BOOLEAN);
		flgAbilitaEreditarietaField.addChangedHandler(new ChangedHandler() {
			
			@Override
			public void onChanged(ChangedEvent event) {
				boolean abilitaEreditarieta = event.getValue() != null ? (Boolean) event.getValue() : false;
				int selectedRow = event.getRowNum();
				String livUoSelezionata = getRecord(selectedRow).getAttribute("livelliUO");
				boolean abilUoSelezionata = Boolean.valueOf(getRecord(selectedRow).getAttribute("flgAbilitazione"));
				// Ciclo su tutti gli elementi della lista
				for (ListGridRecord r : getRecords()) {
					if (isSottoUo(livUoSelezionata, r.getAttribute("livelliUO"))) {
						r.setAttribute("flgAbilitazione", abilUoSelezionata);
						r.setAttribute("flgAbilitaEreditarieta", false);
						r.setAttribute("flgAbilitazioneEreditata", abilitaEreditarieta);
						// settare riga in base alla abilitazione ereditarietà
					}
				}	
			}
		});
		
		// FIXME commento la colonna fino a quando non sarà sviluppata la parte DB
		setFields(idUOField, flgAbilitazioneEreditataField, livelliUOField, denominazioneUOField, flgAbilitazioneField/**, flgAbilitaEreditarietaField*/);
	}
	
	@Override
	public boolean canEditCell(int rowNum, int colNum) {
		Record record = this.getRecord(rowNum);
		String fieldName = this.getFieldName(colNum);
		// Se l'abilitazone è ereditata da una uo padre allora non la posso modificare
		if ((fieldName.equals("flgAbilitazione") || fieldName.equals("flgAbilitaEreditarieta")) && record.getAttributeAsBoolean("flgAbilitazioneEreditata")) {
			return false;
		} else {
    	   	return super.canEditCell(rowNum, colNum);
		}
    }
	
	private static boolean isSottoUo(String uo, String sottoUo) {
		if (uo == null || "".equalsIgnoreCase(uo) || sottoUo == null || "".equalsIgnoreCase(sottoUo) || uo.equals(sottoUo)) {
			return false;
		} else {
			return sottoUo.startsWith(uo);
		}
	}

}
