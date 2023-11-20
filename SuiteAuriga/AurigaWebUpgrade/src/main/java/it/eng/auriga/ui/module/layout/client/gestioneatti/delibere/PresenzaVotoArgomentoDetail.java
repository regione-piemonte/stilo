/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import com.smartgwt.client.types.Overflow;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.HiddenItem;
import com.smartgwt.client.widgets.layout.VLayout;

import it.eng.auriga.ui.module.layout.client.AurigaLayout;
import it.eng.utility.ui.module.layout.client.common.CustomDetail;
import it.eng.utility.ui.module.layout.client.common.DetailSection;
import it.eng.utility.ui.module.layout.client.common.items.NumericItem;

/**
 * 
 * @author DANCRIST
 *
 */

public class PresenzaVotoArgomentoDetail extends CustomDetail {
	
	protected VLayout layoutTabPresenzaVotoArgomento;
	
	protected DetailSection sectionPresenzeVoti;
	protected DynamicForm formListaVotiPresenzeArgomenti;
	protected ListaPresenzeVotiItem listaPresenzeVotiItem;
	
	protected DetailSection sectionAltriPresenti;
	protected DynamicForm formListaAltriPresentiForm;
	protected AltriPresentiItem listaAltriPresentiItem;
	
	protected DetailSection sectionPresenzaVotoArgomento;
	protected DynamicForm formDatiPresenzaVotoArgomento;
	protected NumericItem totaliAstenutiVotoItem;
	protected NumericItem totaleVotiEspressiItem;
	protected NumericItem totaleVotiFavorevoliItem;
	protected NumericItem totaleVotiContrariItem;
	
	protected DynamicForm hiddenForm;
	protected HiddenItem idSedutaItem;
	protected HiddenItem idUdArgomentoOdGItem;
	
	protected String tipologiaSessione;
	protected String codCircoscrizione;

	public PresenzaVotoArgomentoDetail(String nomeEntita, String tipologiaSessione, String codCircoscrizione) {
		super(nomeEntita);
		
		this.tipologiaSessione = tipologiaSessione;
		this.codCircoscrizione = codCircoscrizione;

		buildHiddenForm();
		
		buildDatiListaVotiPresenze();
		
		buildDatiListaAltriPresenti();
		
		buildDatiVotiEspressi();
		
		String titleSectionPresenzeVoti = null;
		if(isAbilHideVotazioneOrgColl()){
			titleSectionPresenzeVoti = "Presenze";
		} else {
			titleSectionPresenzeVoti = "Presenze e voti";
		}
		sectionPresenzeVoti = new DetailSection(titleSectionPresenzeVoti, false, true, false, formListaVotiPresenzeArgomenti);
		
		sectionAltriPresenti = new DetailSection("Altri presenti", false, true, false, formListaAltriPresentiForm);
		
		sectionPresenzaVotoArgomento = new DetailSection("Totali voto", false, true, false, formDatiPresenzaVotoArgomento);
		
		VLayout lVLayout = new VLayout();
		lVLayout.setWidth100();
		lVLayout.setHeight(100);
		lVLayout.addMember(hiddenForm);
		lVLayout.addMember(sectionPresenzeVoti);
		lVLayout.addMember(sectionAltriPresenti);
		if(!isAbilHideVotazioneOrgColl()){
			lVLayout.addMember(sectionPresenzaVotoArgomento);
		}
		
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
	
	private void buildDatiListaVotiPresenze() {
		
		formListaVotiPresenzeArgomenti = new DynamicForm();
		formListaVotiPresenzeArgomenti.setValuesManager(vm);
		formListaVotiPresenzeArgomenti.setWidth("100%");
		formListaVotiPresenzeArgomenti.setHeight("5");
		formListaVotiPresenzeArgomenti.setPadding(5);
		formListaVotiPresenzeArgomenti.setWrapItemTitles(false);
		formListaVotiPresenzeArgomenti.setNumCols(12);
		formListaVotiPresenzeArgomenti.setColWidths(1, 1, 1, 1, 1, 1, 1, 1, "*", "*");
		
		listaPresenzeVotiItem = new ListaPresenzeVotiItem("listaPresenzeVoti", tipologiaSessione, codCircoscrizione);
		listaPresenzeVotiItem.setStartRow(true);
		listaPresenzeVotiItem.setShowTitle(false);
		listaPresenzeVotiItem.setHeight(245);
		
		formListaVotiPresenzeArgomenti.setItems(listaPresenzeVotiItem);
	}
	
	private void buildDatiVotiEspressi() {
		
		formDatiPresenzaVotoArgomento = new DynamicForm();
		formDatiPresenzaVotoArgomento.setValuesManager(vm);
		formDatiPresenzaVotoArgomento.setWidth("100%");
		formDatiPresenzaVotoArgomento.setHeight("5");
		formDatiPresenzaVotoArgomento.setPadding(5);
		formDatiPresenzaVotoArgomento.setWrapItemTitles(false);
		formDatiPresenzaVotoArgomento.setNumCols(12);
		formDatiPresenzaVotoArgomento.setColWidths(1, 1, 1, 1, 1, 1, 1, 1, "*", "*");
		
		totaliAstenutiVotoItem = new NumericItem("totaliAstenutiVoto", "Astenuti");
		totaliAstenutiVotoItem.setColSpan(1);
		totaliAstenutiVotoItem.setWidth(100);
		
		totaleVotiEspressiItem = new NumericItem("totaleVotiEspressi", "Espressi");
		totaleVotiEspressiItem.setColSpan(1);
		totaleVotiEspressiItem.setWidth(100);
		totaleVotiEspressiItem.setStartRow(true);
		
		totaleVotiFavorevoliItem = new NumericItem("totaleVotiFavorevoli", "Favorevoli");
		totaleVotiFavorevoliItem.setColSpan(1);
		totaleVotiFavorevoliItem.setWidth(100);
		totaleVotiFavorevoliItem.setStartRow(true);
		
		totaleVotiContrariItem = new NumericItem("totaleVotiContrari", "Contrari");
		totaleVotiContrariItem.setColSpan(1);
		totaleVotiContrariItem.setWidth(100);
		totaleVotiContrariItem.setStartRow(true);
		
		formDatiPresenzaVotoArgomento.setItems(totaliAstenutiVotoItem,totaleVotiEspressiItem,totaleVotiFavorevoliItem,totaleVotiContrariItem);
	}
	
	private void buildDatiListaAltriPresenti() {
		
		formListaAltriPresentiForm = new DynamicForm();
		formListaAltriPresentiForm.setValuesManager(vm);
		formListaAltriPresentiForm.setWidth("100%");
		formListaAltriPresentiForm.setHeight("5");
		formListaAltriPresentiForm.setPadding(5);
		formListaAltriPresentiForm.setWrapItemTitles(false);
		formListaAltriPresentiForm.setNumCols(12);
		formListaAltriPresentiForm.setColWidths(1, 1, 1, 1, 1, 1, 1, 1, "*", "*");
		
		listaAltriPresentiItem = new AltriPresentiItem() {
			
			 @Override
			 public String getTipologiaSessione() {
				 return tipologiaSessione;
			 }
		};
		listaAltriPresentiItem.setName("listaAltriPresenti");
		listaAltriPresentiItem.setStartRow(true);
		listaAltriPresentiItem.setShowTitle(false);
		listaAltriPresentiItem.setHeight(245);
		
		formListaAltriPresentiForm.setItems(listaAltriPresentiItem);
	}
	
	private Boolean isAbilHideVotazioneOrgColl() {
		String param = null;
		if(codCircoscrizione != null && !"".equalsIgnoreCase(codCircoscrizione)) {
			param = "HIDE_VOTAZIONE_ORG_COLL_" + tipologiaSessione + "_CIRC";
		} else {
			param = "HIDE_VOTAZIONE_ORG_COLL_" + tipologiaSessione;
		}
		return AurigaLayout.getParametroDBAsBoolean(param);
	}

}