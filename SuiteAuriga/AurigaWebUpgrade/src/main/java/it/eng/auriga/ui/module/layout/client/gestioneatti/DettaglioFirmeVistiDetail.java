/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import com.smartgwt.client.data.Record;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.layout.VLayout;

import it.eng.utility.ui.module.layout.client.common.CustomDetail;

/**
 * 
 * @author dbe4235
 *
 */

public class DettaglioFirmeVistiDetail extends CustomDetail {
	
	private DynamicForm formListaFirmeVisti;	
	private ListaDettaglioFirmeVistiItem listaFirmeVistiItem;

	public DettaglioFirmeVistiDetail() {
		super("dettaglio_firme_visti");
		
		createFormFirmeVisti();
				
		VLayout lVLayout = new VLayout();
		lVLayout.setHeight100();
		lVLayout.setWidth100();

		lVLayout.addMember(formListaFirmeVisti);

		setMembers(lVLayout);		
	}
	
	public void createFormFirmeVisti() {
		
		formListaFirmeVisti = new DynamicForm();
		formListaFirmeVisti.setTabID("HEADER");		
		formListaFirmeVisti.setValuesManager(vm);
		formListaFirmeVisti.setWidth("100%");
		formListaFirmeVisti.setHeight("100%");
		formListaFirmeVisti.setPadding(5);
		formListaFirmeVisti.setWrapItemTitles(false);
		formListaFirmeVisti.setNumCols(10);
		formListaFirmeVisti.setColWidths(1, 1, 1, 1, 1, 1, 1, 1, 1, "*");
		formListaFirmeVisti.setValuesManager(vm);
		
		listaFirmeVistiItem = new ListaDettaglioFirmeVistiItem("listaFirmeVisti");
		listaFirmeVistiItem.setStartRow(true);
		listaFirmeVistiItem.setShowTitle(false);
		listaFirmeVistiItem.setHeight("95%");
		
		formListaFirmeVisti.setItems(listaFirmeVistiItem);
	}
	
	public void caricaDettaglio(Record record) {

		editRecord(record);
		
		if(listaFirmeVistiItem != null) {
			listaFirmeVistiItem.setCanEdit(false);
		}
		
		markForRedraw();
	}

}