/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.HashMap;
import java.util.Map;

import com.smartgwt.client.types.ListGridFieldType;
import com.smartgwt.client.widgets.grid.HoverCustomizer;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.grid.ListGridRecord;

import it.eng.utility.ui.module.layout.client.common.GridItem;

public class ListaDettaglioFirmeVistiItem extends GridItem {
	
	private ListaDettaglioFirmeVistiItem instance = this;
	
	protected ListGridField idUd;
	protected ListGridField dataFirma;
	protected ListGridField tipoFirma;
	protected ListGridField firmatario;
	protected ListGridField ruolo;

	public ListaDettaglioFirmeVistiItem(String name) {
		super(name, "lista_dettaglio_firme_visti");
		
		setGridPkField("idUd");
		setShowPreference(true);
		setShowNewButton(false);
		setShowModifyButton(false);
		setShowDeleteButton(false);
		setCanEdit(false);
		
		idUd = new ListGridField("idUd");
		idUd.setHidden(true);
		idUd.setCanHide(false); 
		idUd.setCanSort(false);
		
		dataFirma = new ListGridField("dataFirma", "Firma del");
		dataFirma.setType(ListGridFieldType.DATE);
		dataFirma.setCanSort(true);
		
		tipoFirma = new ListGridField("tipoFirma", "Tipo");
		tipoFirma.setType(ListGridFieldType.ICON);
		tipoFirma.setWidth(30);
		tipoFirma.setIconWidth(16);
		tipoFirma.setIconHeight(16);
		Map<String, String> flgTipoFirmaIcons = new HashMap<String, String>();		
		flgTipoFirmaIcons.put("E", "lettere/lettera_E_nera.png");
		flgTipoFirmaIcons.put("D", "lettere/lettera_D_nera.png");
		tipoFirma.setValueIcons(flgTipoFirmaIcons);
		tipoFirma.setHoverCustomizer(new HoverCustomizer() {
			
			@Override
			public String hoverHTML(Object value, ListGridRecord record, int rowNum, int colNum) {
				if(record.getAttribute("tipoFirma") != null && "E".equals(record.getAttribute("tipoFirma"))) {
					return "Elettronica";
				} else {
					return "Digitale";
				}
			}
		});
		tipoFirma.setCanSort(true);
		
		firmatario = new ListGridField("firmatario", "Nominativo firmatario");
		firmatario.setCanSort(true);
		
		ruolo = new ListGridField("ruolo", "Ruolo con cui ha firmato/vistato");
		ruolo.setCanSort(true);
		
		setGridFields(idUd,dataFirma,tipoFirma,firmatario,ruolo);
	
	}

}