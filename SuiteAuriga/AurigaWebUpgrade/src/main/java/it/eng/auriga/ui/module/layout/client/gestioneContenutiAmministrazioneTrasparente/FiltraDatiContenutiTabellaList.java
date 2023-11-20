/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.widgets.grid.CellFormatter;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.grid.ListGridRecord;

import it.eng.utility.ui.module.layout.client.common.GridItem;

public class FiltraDatiContenutiTabellaList extends GridItem {
	
	private ListGridField inColonnaField;
	private ListGridField cercaField;
	private ListGridField nomeField;
	
	public FiltraDatiContenutiTabellaList (String entita) {
		super(entita,"listaFiltrata");
		
//		setShowAllRecords(true);
		
		setHeight("*");
		setGridPkField("inColonna");
		setShowPreference(false);
		setShowEditButtons(false);
		setShowNewButton(false);
		setShowModifyButton(false);
		setShowDeleteButton(false);
		
		// nascosti
		nomeField = new ListGridField("nome", "Nome colonna");
		nomeField.setHidden(true);
		
		// visibili		
		inColonnaField = new ListGridField("inColonna", "In colonna");
		inColonnaField.setAttribute("custom", true);
		inColonnaField.setCellAlign(Alignment.LEFT);
		inColonnaField.setShowTitle(true);
		inColonnaField.setCellFormatter(new CellFormatter() {
			
			@Override
			public String format(Object value, ListGridRecord record, int rowNum, int colNum) {
                return 	record.getAttribute("inColonna");			
			}
			
		});
		
		cercaField = new ListGridField("cerca", "Cerca");
		cercaField.setAttribute("custom", true);
		cercaField.setCellAlign(Alignment.LEFT);
		cercaField.setShowTitle(true);
		cercaField.setCanEdit(true);
		cercaField.setCellFormatter(new CellFormatter() {
			
			@Override
			public String format(Object value, ListGridRecord record, int rowNum, int colNum) {
				return 	record.getAttribute("cerca");	
			}
			
		});
		
		
		setGridFields(nomeField, inColonnaField, cercaField);
		
	}
	
	
	@Override
	public void setGridFields(ListGridField... fields) {		
		setGridFields("listaFiltraDatiContenutiTabella", fields);
	}	

}
