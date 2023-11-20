/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import com.smartgwt.client.types.Overflow;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.HiddenItem;
import com.smartgwt.client.widgets.layout.VLayout;

import it.eng.utility.ui.module.layout.client.common.CustomDetail;
import it.eng.utility.ui.module.layout.client.common.DetailSection;


public class DichiarazioneDiVotoArgomentoDetail extends CustomDetail {
	
	protected DetailSection sectionDichiarazionidiVoto;
	protected DynamicForm formListaDichiarazioniDiVotoArgomenti;
	protected ListaDichiarazioniDiVotoItem listaDichiarazioniDiVotoItem;
	
	protected DynamicForm hiddenForm;
	protected HiddenItem idSedutaItem;
	protected HiddenItem idUdArgomentoOdGItem;
	
	protected String tipologiaSessione;
	protected String idSeduta;

	public DichiarazioneDiVotoArgomentoDetail(String nomeEntita, String tipologiaSessione, String idSeduta) {
		super(nomeEntita);
		
		this.tipologiaSessione = tipologiaSessione;
		this.idSeduta = idSeduta;

		buildHiddenForm();
		
		buildDatiListaDichiarazioniDiVoto();
				
		sectionDichiarazionidiVoto = new DetailSection("Dichiarazioni di voto", false, true, false, formListaDichiarazioniDiVotoArgomenti);
		
		VLayout lVLayout = new VLayout();
		lVLayout.setWidth100();
		lVLayout.setHeight(100);
		lVLayout.addMember(hiddenForm);
		lVLayout.addMember(sectionDichiarazionidiVoto);
		
		addMembers(lVLayout);
	}
	
	private void buildHiddenForm() {
		
		hiddenForm = new DynamicForm();
		hiddenForm.setValuesManager(vm);
//		hiddenForm.setHeight(0);
		hiddenForm.setOverflow(Overflow.HIDDEN);
		
		idSedutaItem = new HiddenItem("idSeduta");
		
		idUdArgomentoOdGItem = new HiddenItem("idUdArgomentoOdG");
		
		hiddenForm.setItems(idSedutaItem,idUdArgomentoOdGItem);
	}
	
	private void buildDatiListaDichiarazioniDiVoto() {
		
		formListaDichiarazioniDiVotoArgomenti = new DynamicForm();
		formListaDichiarazioniDiVotoArgomenti.setValuesManager(vm);
		formListaDichiarazioniDiVotoArgomenti.setWidth("100%");
		formListaDichiarazioniDiVotoArgomenti.setHeight("5");
		formListaDichiarazioniDiVotoArgomenti.setPadding(5);
		formListaDichiarazioniDiVotoArgomenti.setWrapItemTitles(false);
		formListaDichiarazioniDiVotoArgomenti.setNumCols(12);
		formListaDichiarazioniDiVotoArgomenti.setColWidths(1, 1, 1, 1, 1, 1, 1, 1, "*", "*");
		
		listaDichiarazioniDiVotoItem = new ListaDichiarazioniDiVotoItem("listaPresenzeDichiarazioniVoti", tipologiaSessione, idSeduta);
		listaDichiarazioniDiVotoItem.setStartRow(true);
		listaDichiarazioniDiVotoItem.setShowTitle(false);
		listaDichiarazioniDiVotoItem.setHeight(245);
		
		formListaDichiarazioniDiVotoArgomenti.setItems(listaDichiarazioniDiVotoItem);
	}

}