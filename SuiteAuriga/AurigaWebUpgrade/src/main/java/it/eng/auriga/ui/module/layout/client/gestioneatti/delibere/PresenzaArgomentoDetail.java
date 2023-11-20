/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import com.smartgwt.client.types.Overflow;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.HiddenItem;
import com.smartgwt.client.widgets.layout.VLayout;

import it.eng.utility.ui.module.layout.client.common.CustomDetail;
import it.eng.utility.ui.module.layout.client.common.DetailSection;

/**
 * 
 * @author DANCRIST
 *
 */

public class PresenzaArgomentoDetail extends CustomDetail {
		
	protected DetailSection sectionPresenze;
	protected DynamicForm formListaPresenzeArgomenti;
	protected ListaPresenzeItem listaPresenzeItem;
	
	protected DynamicForm hiddenForm;
	protected HiddenItem idSedutaItem;
	protected HiddenItem idUdArgomentoOdGItem;
	
	protected String tipologiaSessione;
	protected String idSeduta;

	public PresenzaArgomentoDetail(String nomeEntita, String tipologiaSessione, String idSeduta) {
		super(nomeEntita);
		
		this.tipologiaSessione = tipologiaSessione;
		this.idSeduta = idSeduta;

		buildHiddenForm();
		
		buildDatiListaPresenze();		
		
		sectionPresenze = new DetailSection("Presenze", false, true, false, formListaPresenzeArgomenti);
		
		VLayout lVLayout = new VLayout();
		lVLayout.setWidth100();
		lVLayout.setHeight(100);
		lVLayout.addMember(hiddenForm);
		lVLayout.addMember(sectionPresenze);
		
		addMembers(lVLayout);
	}
	
	private void buildHiddenForm() {
		
		hiddenForm = new DynamicForm();
		hiddenForm.setValuesManager(vm);
		hiddenForm.setOverflow(Overflow.HIDDEN);
		
		idSedutaItem = new HiddenItem("idSeduta");
		
		idUdArgomentoOdGItem = new HiddenItem("idUdArgomentoOdG");
		
		hiddenForm.setItems(idSedutaItem,idUdArgomentoOdGItem);
	}
	
	private void buildDatiListaPresenze() {
		
		formListaPresenzeArgomenti = new DynamicForm();
		formListaPresenzeArgomenti.setValuesManager(vm);
		formListaPresenzeArgomenti.setWidth("100%");
		formListaPresenzeArgomenti.setHeight("5");
		formListaPresenzeArgomenti.setPadding(5);
		formListaPresenzeArgomenti.setWrapItemTitles(false);
		formListaPresenzeArgomenti.setNumCols(12);
		formListaPresenzeArgomenti.setColWidths(1, 1, 1, 1, 1, 1, 1, 1, "*", "*");
		
		listaPresenzeItem = new ListaPresenzeItem("listaPresenzeDichiarazioniVoti", tipologiaSessione, idSeduta);
		listaPresenzeItem.setStartRow(true);
		listaPresenzeItem.setShowTitle(false);
		listaPresenzeItem.setHeight(245);
		
		formListaPresenzeArgomenti.setItems(listaPresenzeItem);
	}

}