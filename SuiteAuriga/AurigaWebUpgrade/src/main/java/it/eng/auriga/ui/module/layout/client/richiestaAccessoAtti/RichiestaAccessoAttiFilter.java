/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

import com.smartgwt.client.data.AdvancedCriteria;
import com.smartgwt.client.data.Criteria;
import com.smartgwt.client.data.Criterion;
import com.smartgwt.client.types.OperatorId;
import com.smartgwt.client.util.JSOHelper;
import com.smartgwt.client.widgets.form.FilterBuilder;
import com.smartgwt.client.widgets.form.events.FilterChangedEvent;
import com.smartgwt.client.widgets.form.events.FilterChangedHandler;
import com.smartgwt.client.widgets.form.fields.FormItem;
import com.smartgwt.client.widgets.form.fields.FormItemFunctionContext;
import com.smartgwt.client.widgets.form.fields.SelectItem;
import com.smartgwt.client.widgets.layout.VStack;

import it.eng.utility.ui.module.layout.client.common.ConfigurableFilter;

public class RichiestaAccessoAttiFilter extends ConfigurableFilter {

	private String showFilterEstremiRegistrazione;
	private String showFilterDataApprovazione;
	private String showFilterRichApprovataDa;
	private String showFilterUnitaDiConservazione;
	private String showFilterDataEsitoCittadella;
	private String showFilterRichiesteDaApprovare;
	private String showFilterRichiesteDaVerificare;
	private String showFilterRichiesteAppuntamento;
	private String showFilterAppuntamentiDaFissare;
	private String showFilterRichiesteNotifica;
	
	public RichiestaAccessoAttiFilter(String lista, Map<String, String> extraparam) {
		// TODO Auto-generated constructor stub
		super(lista, extraparam);

		updateShowFilter(extraparam);
		
		addFilterChangedHandler(new FilterChangedHandler() {

			@Override
			public void onFilterChanged(FilterChangedEvent event) {
				
				//------------------FILTRI COMUNI------------------//
				int nroProtocollo = getClausePosition("nroProtocollo");
				if (nroProtocollo != -1) {
					if ((showFilterEstremiRegistrazione.equalsIgnoreCase("N"))) {								
						removeClause(getClause(nroProtocollo));
					}
				}
				int annoProtocollo = getClausePosition("annoProtocollo");
				if (annoProtocollo != -1) {
					if ((showFilterEstremiRegistrazione.equalsIgnoreCase("N"))) {								
						removeClause(getClause(annoProtocollo));
					}
				}				
				int dataProtocollo = getClausePosition("dataProtocollo");
				if (dataProtocollo != -1) {
					if ((showFilterEstremiRegistrazione.equalsIgnoreCase("N"))) {								
						removeClause(getClause(dataProtocollo));
					}
				}
				int nroAltraNumerazione = getClausePosition("nroAltraNumerazione");
				if (nroAltraNumerazione != -1) {
					if ((showFilterEstremiRegistrazione.equalsIgnoreCase("N"))) {								
						removeClause(getClause(nroAltraNumerazione));
					}
				}
				int annoAltraNumerazione = getClausePosition("annoAltraNumerazione");
				if (annoAltraNumerazione != -1) {
					if ((showFilterEstremiRegistrazione.equalsIgnoreCase("N"))) {								
						removeClause(getClause(annoAltraNumerazione));
					}
				}
				int indirizzo = getClausePosition("indirizzo");
				if (indirizzo != -1) {
					if ((showFilterEstremiRegistrazione.equalsIgnoreCase("N"))) {								
						removeClause(getClause(indirizzo));
					}
				}
				int richRegistrataDa = getClausePosition("richRegistrataDa");
				if (richRegistrataDa != -1) {
					if ((showFilterEstremiRegistrazione.equalsIgnoreCase("N"))) {								
						removeClause(getClause(richRegistrataDa));
					}
				}
				//------------------FILTRI comuni ad IdNode { D.RAA.DV - D.RAA.ADF - D.RAA.AF - D.RAA.EN }------------------//
				int dataApprovazione = getClausePosition("dataApprovazione");
				if (dataApprovazione != -1) {
					if ((showFilterDataApprovazione.equalsIgnoreCase("N"))) {								
						removeClause(getClause(dataApprovazione));
					}
				}
				
				int richApprovataDa = getClausePosition("richApprovataDa");
				if (richApprovataDa != -1) {
					if ((showFilterRichApprovataDa.equalsIgnoreCase("N"))) {								
						removeClause(getClause(richApprovataDa));
					}
				}
				//------------------FILTRI comuni ad IdNode { D.RAA.DV - D.RAA.ADF - D.RAA.AF }------------------//
				int dataEsitoCittadella = getClausePosition("dataEsitoCittadella");
				if (dataEsitoCittadella != -1) {
					if ((showFilterDataEsitoCittadella.equalsIgnoreCase("N"))) {								
						removeClause(getClause(dataEsitoCittadella));
					}
				}
				//------------------FILTRI comuni ad IdNode { D.RAA.ADF - D.RAA.AF - D.RAA.EN }------------------//
				int unitaDiConservazione = getClausePosition("unitaDiConservazione");
				if (unitaDiConservazione != -1) {
					if ((showFilterUnitaDiConservazione.equalsIgnoreCase("N"))) {								
						removeClause(getClause(unitaDiConservazione));
					}
				}
				//------------------FILTRI Richieste da approvare------------------//
				int dataInvioApprovazione = getClausePosition("dataInvioApprovazione");
				if (dataInvioApprovazione != -1) {
					if ((showFilterRichiesteDaApprovare.equalsIgnoreCase("N"))) {								
						removeClause(getClause(dataInvioApprovazione));
					}
				}
				//------------------FILTRI Richieste da verificare------------------//
				int verificaCompletata = getClausePosition("verificaCompletata");
				if (verificaCompletata != -1) {
					if ((showFilterRichiesteDaVerificare.equalsIgnoreCase("N"))) {								
						removeClause(getClause(verificaCompletata));
					}
				}
				//------------------FILTRI Richieste di appuntamento ------------------//
				int dataAppuntamento = getClausePosition("dataAppuntamento");
				if (dataAppuntamento != -1) {
					if ((showFilterRichiesteAppuntamento.equalsIgnoreCase("N"))) {								
						removeClause(getClause(dataAppuntamento));
					}
				}
				int dataPrelievo = getClausePosition("dataPrelievo");
				if (dataPrelievo != -1) {
					if ((showFilterRichiesteAppuntamento.equalsIgnoreCase("N"))) {								
						removeClause(getClause(dataPrelievo));
					}
				}
				int prelievoEffettuato = getClausePosition("prelievoEffettuato");
				if (prelievoEffettuato != -1) {
					if ((showFilterRichiesteAppuntamento.equalsIgnoreCase("N"))) {								
						removeClause(getClause(prelievoEffettuato));
					}
				}
				//------------------FILTRI Appuntamenti da fissare ------------------//
				int richPrenotabiliDaAgendaDigitale = getClausePosition("richPrenotabiliDaAgendaDigitale");
				if (richPrenotabiliDaAgendaDigitale != -1) {
					if ((showFilterAppuntamentiDaFissare.equalsIgnoreCase("N"))) {								
						removeClause(getClause(richPrenotabiliDaAgendaDigitale));
					}
				}
				//------------------FILTRI Richieste con notifiche ------------------//
				int tsNotifica = getClausePosition("tsNotifica");
				if (tsNotifica != -1) {
					if ((showFilterRichiesteNotifica.equalsIgnoreCase("N"))) {								
						removeClause(getClause(tsNotifica));
					}
				}			
			}
		});
	}
	
	public void updateShowFilter(Map<String, String> extraparam) {
		setExtraParam(extraparam);
		showFilterEstremiRegistrazione = getExtraParam().get("showFilterEstremiRegistrazione") != null ? getExtraParam().get("showFilterEstremiRegistrazione") : "N";
		showFilterRichApprovataDa = getExtraParam().get("showFilterRichApprovataDa") != null ? getExtraParam().get("showFilterRichApprovataDa") : "N";
		showFilterDataApprovazione = getExtraParam().get("showFilterDataApprovazione") != null ? getExtraParam().get("showFilterDataApprovazione") : "N";
		showFilterUnitaDiConservazione= getExtraParam().get("showFilterUnitaDiConservazione") != null ? getExtraParam().get("showFilterUnitaDiConservazione") : "N";
		showFilterDataEsitoCittadella = getExtraParam().get("showFilterDataEsitoCittadella") != null ? getExtraParam().get("showFilterDataEsitoCittadella") : "N";
		showFilterRichiesteDaApprovare = getExtraParam().get("showFilterRichiesteDaApprovare") != null ? getExtraParam().get("showFilterRichiesteDaApprovare") : "N";
		showFilterRichiesteDaVerificare = getExtraParam().get("showFilterRichiesteDaVerificare") != null ? getExtraParam().get("showFilterRichiesteDaVerificare") : "N";
		showFilterRichiesteAppuntamento = getExtraParam().get("showFilterRichiesteAppuntamento") != null ? getExtraParam().get("showFilterRichiesteAppuntamento") : "N";
		showFilterAppuntamentiDaFissare = getExtraParam().get("showFilterAppuntamentiDaFissare") != null ? getExtraParam().get("showFilterAppuntamentiDaFissare") : "N";
		showFilterRichiesteNotifica = getExtraParam().get("showFilterRichiesteNotifica") != null ? getExtraParam().get("showFilterRichiesteNotifica") : "N";
	}
	
	@Override
	public LinkedHashMap<String, String> getMappaFiltriToShow(LinkedHashMap<String, String> lMap) {
	
		//Filtri Comuni		
		if (lMap.containsKey("nroProtocollo") && (showFilterEstremiRegistrazione.equalsIgnoreCase("N"))) {		
			lMap.remove("nroProtocollo");
		}
		if (lMap.containsKey("annoProtocollo") && (showFilterEstremiRegistrazione.equalsIgnoreCase("N"))) {		
			lMap.remove("annoProtocollo");
		}
		if (lMap.containsKey("dataProtocollo") && (showFilterEstremiRegistrazione.equalsIgnoreCase("N"))) {		
			lMap.remove("dataProtocollo");
		}
		if (lMap.containsKey("nroAltraNumerazione") && (showFilterEstremiRegistrazione.equalsIgnoreCase("N"))) {		
			lMap.remove("nroAltraNumerazione");
		}
		if (lMap.containsKey("annoAltraNumerazione") && (showFilterEstremiRegistrazione.equalsIgnoreCase("N"))) {		
			lMap.remove("annoAltraNumerazione");
		}
		if (lMap.containsKey("indirizzo") && (showFilterEstremiRegistrazione.equalsIgnoreCase("N"))) {		
			lMap.remove("indirizzo");
		}
		if (lMap.containsKey("richRegistrataDa") && (showFilterEstremiRegistrazione.equalsIgnoreCase("N"))) {		
			lMap.remove("richRegistrataDa");
		}
		//FILTRI comuni ad IdNode { D.RAA.DV - D.RAA.ADF - D.RAA.AF - D.RAA.EN }
		if (lMap.containsKey("dataApprovazione") && (showFilterDataApprovazione.equalsIgnoreCase("N"))) {		
			lMap.remove("dataApprovazione");
		}
		if (lMap.containsKey("richApprovataDa") && (showFilterRichApprovataDa.equalsIgnoreCase("N"))) {		
			lMap.remove("richApprovataDa");
		}
		//FILTRI comuni ad IdNode { D.RAA.DV - D.RAA.ADF - D.RAA.AF }
		if (lMap.containsKey("unitaDiConservazione") && (showFilterUnitaDiConservazione.equalsIgnoreCase("N"))) {		
			lMap.remove("unitaDiConservazione");
		}
		//FILTRI comuni ad IdNode { D.RAA.ADF - D.RAA.AF - D.RAA.EN }
		if (lMap.containsKey("dataEsitoCittadella") && (showFilterDataEsitoCittadella.equalsIgnoreCase("N"))) {		
			lMap.remove("dataEsitoCittadella");
		}
		//Filtri Richieste da approvare
		if (lMap.containsKey("dataInvioApprovazione") && (showFilterRichiesteDaApprovare.equalsIgnoreCase("N"))) {		
			lMap.remove("dataInvioApprovazione");
		}
		//Filtri Richieste da verificare
		if (lMap.containsKey("verificaCompletata") && (showFilterRichiesteDaVerificare.equalsIgnoreCase("N"))) {		
			lMap.remove("verificaCompletata");
		}
		//Filtri Richieste di appuntamento
		if (lMap.containsKey("dataAppuntamento") && (showFilterRichiesteAppuntamento.equalsIgnoreCase("N"))) {		
			lMap.remove("dataAppuntamento");
		}
		if (lMap.containsKey("dataPrelievo") && (showFilterRichiesteAppuntamento.equalsIgnoreCase("N"))) {		
			lMap.remove("dataPrelievo");
		}
		if (lMap.containsKey("prelievoEffettuato") && (showFilterRichiesteAppuntamento.equalsIgnoreCase("N"))) {		
			lMap.remove("prelievoEffettuato");
		}
		//Filtri Appuntamenti da fissare
		if (lMap.containsKey("richPrenotabiliDaAgendaDigitale") && (showFilterAppuntamentiDaFissare.equalsIgnoreCase("N"))) {		
			lMap.remove("richPrenotabiliDaAgendaDigitale");
		}
		//Filtri Richieste con notifiche
		if (lMap.containsKey("tsNotifica") && (showFilterRichiesteNotifica.equalsIgnoreCase("N"))) {		
			lMap.remove("tsNotifica");
		}
		
		return lMap;
	}
	
	@Override
	protected Criteria createCriteria(FormItemFunctionContext itemContext) {

		SelectItem lSelectItem = (SelectItem) itemContext.getFormItem();
		AdvancedCriteria lAdvancedCriteria = getCriteria(true);
		Criterion[] lCriterions = lAdvancedCriteria.getCriteria();
		String selected = "";
		
		// Filtri Comuni
		if(showFilterEstremiRegistrazione.equalsIgnoreCase("N")){
			selected = selected + "nroProtocollo,annoProtocollo,dataProtocollo,nroAltraNumerazione,annoAltraNumerazione,indirizzo,richRegistrataDa";
		}
		//FILTRI comuni ad IdNode { D.RAA.DV - D.RAA.ADF - D.RAA.AF - D.RAA.EN }
		if(showFilterDataApprovazione.equalsIgnoreCase("N")){
			selected = selected + "dataApprovazione,";
		}
		if(showFilterRichApprovataDa.equalsIgnoreCase("N")){
			selected = selected + "richApprovataDa,";
		}
		//FILTRI comuni ad IdNode {  D.RAA.DV - D.RAA.ADF - D.RAA.AF }
		if(showFilterUnitaDiConservazione.equalsIgnoreCase("N")){
			selected = selected + "unitaDiConservazione,";
		}
		//FILTRI comuni ad IdNode {   D.RAA.ADF - D.RAA.AF - D.RAA.EN }
		if(showFilterDataEsitoCittadella.equalsIgnoreCase("N")){
			selected = selected + "dataEsitoCittadella,";
		}
		//Filtri Richieste da approvare
		if(showFilterRichiesteDaApprovare.equalsIgnoreCase("N")){
			selected = selected + "dataInvioApprovazione,";
		}
		//Filtri Richieste da verificare
		if(showFilterRichiesteDaVerificare.equalsIgnoreCase("N")){
			selected = selected + "verificaCompletata,";
		}
		//Filtri Richieste di appuntamento
		if(showFilterRichiesteAppuntamento.equalsIgnoreCase("N")){
			selected = selected + "dataAppuntamento,dataPrelievo,prelievoEffettuato,";
		}
		//Filtri Appuntamenti da fissare
		if(showFilterAppuntamentiDaFissare.equalsIgnoreCase("N")){
			selected = selected + "richPrenotabiliDaAgendaDigitale,";
		}
		//Filtri Richieste con notifiche
		if(showFilterRichiesteNotifica.equalsIgnoreCase("N")){
			selected = selected + "tsNotifica,";
		}
		
		for (Criterion lCriterion : lCriterions) {
			if (lCriterion.getFieldName() != null && !lCriterion.getFieldName().equals(lSelectItem.getValue())) {
				selected += lCriterion.getFieldName() + ",";
			}
		}
		return new AdvancedCriteria("escludi", OperatorId.EQUALS, selected + ";" + new Date().toString());
	}

}
