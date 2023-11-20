/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.smartgwt.client.data.AdvancedCriteria;
import com.smartgwt.client.data.Criteria;
import com.smartgwt.client.data.Criterion;
import com.smartgwt.client.data.DataSourceField;
import com.smartgwt.client.types.OperatorId;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.events.FilterChangedEvent;
import com.smartgwt.client.widgets.form.events.FilterChangedHandler;
import com.smartgwt.client.widgets.form.fields.FormItem;
import com.smartgwt.client.widgets.form.fields.FormItemFunctionContext;
import com.smartgwt.client.widgets.form.fields.SelectItem;
import com.smartgwt.client.widgets.form.fields.events.ChangedEvent;
import com.smartgwt.client.widgets.form.fields.events.ChangedHandler;

import it.eng.utility.ui.module.layout.client.Layout;
import it.eng.utility.ui.module.layout.client.common.ConfigurableFilter;
import it.eng.utility.ui.module.layout.client.common.SelectItemFiltrabile;
import it.eng.utility.ui.module.layout.client.common.filter.item.ScadenzaItem;
import it.eng.utility.ui.module.layout.shared.bean.FilterFieldBean;

/**
 * Renderizza il filtro che viene visualizzato sopra la lista e che permette di filtrarla
 * @author Dancrist
 *
 */
public class AttiCompletiPersonaliFilter extends ConfigurableFilter {

	private Boolean showFilterSmistamentoAtti;
	private Boolean showFilterCentroDiCosto;
	private Boolean showFilterDataScadenza;
	
	private Boolean showFilterDeterminaContrarreConGara;
	private Boolean showFilterTipoAffidamento;
	private Boolean showFilterDeterminaRimodulazioneSpesaPostAggiudica;
	private Boolean showFilterMaterieTipiAtto;
	private Boolean showFilterLiquidazioneContestualeImpegni;
	private Boolean showFilterLiquidazioneContestualeAltriAspettiCont;
	private Boolean showFilterDeterminaAggiudicaGara;
	private Boolean showFilterUoCompetente;
	private Boolean showFilterPresenzaOpere;
	private Boolean showFilterOpera;
	private Boolean showFilterSottoTipologiaAtto;
	private Boolean showFilterProgrammazioneAcquisti;
	private Boolean showFilterCui;
	
	public AttiCompletiPersonaliFilter(String lista,  Map<String, String> extraparam) {
	
		super(lista, extraparam);
		
		updateShowFilter(extraparam);
		
		addFilterChangedHandler(new FilterChangedHandler() {
			
			@Override
			public void onFilterChanged(FilterChangedEvent event) {
				
				// Filtri relativi al protocollo
				int posAssegnatario = getClausePosition("assegnatario");				
				if(posAssegnatario != -1) {
					if (!showFilterSmistamentoAtti){						
						removeClause(getClause(posAssegnatario));	
					}
				}	
				
				int posCentroDiCosto = getClausePosition("centroDiCosto");				
				if(posCentroDiCosto != -1) {
					if (!showFilterCentroDiCosto){						
						removeClause(getClause(posCentroDiCosto));	
					}
				}
				
				int posDataScadenza = getClausePosition("dataScadenza");				
				if(posDataScadenza != -1) {
					if (!showFilterDataScadenza){						
						removeClause(getClause(posDataScadenza));	
					}
				}
				
				int posDeterminaContrarreConGara = getClausePosition("determinaContrarreConGara");				
				if(posDeterminaContrarreConGara != -1) {
					if (!showFilterDeterminaContrarreConGara){						
						removeClause(getClause(posDeterminaContrarreConGara));	
					}
				}
				
				int posTipoAffidamento = getClausePosition("tipoAffidamento");				
				if(posTipoAffidamento != -1) {
					if (!showFilterTipoAffidamento){						
						removeClause(getClause(posTipoAffidamento));	
					}
				}

				int posDeterminaRimodulazioneSpesaPostAggiudica = getClausePosition("determinaRimodulazioneSpesaPostAggiudica");				
				if(posDeterminaRimodulazioneSpesaPostAggiudica != -1) {
					if (!showFilterDeterminaRimodulazioneSpesaPostAggiudica){						
						removeClause(getClause(posDeterminaRimodulazioneSpesaPostAggiudica));	
					}
				}
				
				int posMaterieTipiAtto = getClausePosition("materieTipiAtto");				
				if(posMaterieTipiAtto != -1) {
					if (!showFilterMaterieTipiAtto){						
						removeClause(getClause(posMaterieTipiAtto));	
					}
				}
				
				int posLiquidazioneContestualeImpegni = getClausePosition("liquidazioneContestualeImpegni");				
				if(posLiquidazioneContestualeImpegni != -1) {
					if (!showFilterLiquidazioneContestualeImpegni){						
						removeClause(getClause(posLiquidazioneContestualeImpegni));	
					}
				}
				
				int posLiquidazioneContestualeAltriAspettiCont = getClausePosition("liquidazioneContestualeAltriAspettiCont");				
				if(posLiquidazioneContestualeAltriAspettiCont != -1) {
					if (!showFilterLiquidazioneContestualeAltriAspettiCont){						
						removeClause(getClause(posLiquidazioneContestualeAltriAspettiCont));	
					}
				}
				
				int posDeterminaAggiudicaGara = getClausePosition("determinaAggiudicaGara");				
				if(posDeterminaAggiudicaGara != -1) {
					if (!showFilterDeterminaAggiudicaGara){						
						removeClause(getClause(posDeterminaAggiudicaGara));	
					}
				}
				
				int posUoCompetente = getClausePosition("uoCompetente");
				if(posUoCompetente != -1) {
					if (!showFilterUoCompetente){						
						removeClause(getClause(posUoCompetente));	
					}
				}
				
				int posPresenzaOpere  = getClausePosition("presenzaOpere");
				if(posPresenzaOpere != -1) {
					if (!showFilterPresenzaOpere){						
						removeClause(getClause(posPresenzaOpere));	
					}
				}
				
				int posOpera  = getClausePosition("opera");
				if(posOpera != -1) {
					if (!showFilterOpera){						
						removeClause(getClause(posOpera));	
					}
				}
				
				int posSottoTipologiaAtto  = getClausePosition("sottoTipologiaAtto");
				if(posSottoTipologiaAtto != -1) {
					if (!showFilterSottoTipologiaAtto){						
						removeClause(getClause(posSottoTipologiaAtto));	
					}
				}
				
				int posProgrammazioneAcquistiAtto  = getClausePosition("programmazioneAcquisti");
				if(posProgrammazioneAcquistiAtto != -1) {
					if (!showFilterProgrammazioneAcquisti){						
						removeClause(getClause(posProgrammazioneAcquistiAtto));	
					}
				}
				
				int posCuiAtto  = getClausePosition("cui");
				if(posCuiAtto != -1) {
					if (!showFilterCui){						
						removeClause(getClause(posCuiAtto));	
					}
				}
			}
		});
		
		setWidth(750);
	}
	
	public void updateShowFilter(Map<String, String> extraparam) {
		
		setExtraParam(extraparam);	
		
		showFilterSmistamentoAtti = getExtraParam().get("showFilterSmistamentoAtti") != null ? Boolean.valueOf(getExtraParam().get("showFilterSmistamentoAtti")) : false;		
		showFilterCentroDiCosto = getExtraParam().get("showFilterCentroDiCosto") != null ? Boolean.valueOf(getExtraParam().get("showFilterCentroDiCosto")) : false;
		showFilterDataScadenza = getExtraParam().get("showFilterDataScadenza") != null ? Boolean.valueOf(getExtraParam().get("showFilterDataScadenza")) : false;
		
		showFilterDeterminaContrarreConGara = getExtraParam().get("showFilterDeterminaContrarreConGara") != null ? Boolean.valueOf(getExtraParam().get("showFilterDeterminaContrarreConGara")) : false;
		showFilterTipoAffidamento = getExtraParam().get("showFilterTipoAffidamento") != null ? Boolean.valueOf(getExtraParam().get("showFilterTipoAffidamento")) : false;
		showFilterDeterminaRimodulazioneSpesaPostAggiudica = getExtraParam().get("showFilterDeterminaRimodulazioneSpesaPostAggiudica") != null ? Boolean.valueOf(getExtraParam().get("showFilterDeterminaRimodulazioneSpesaPostAggiudica")) : false;
		showFilterMaterieTipiAtto = getExtraParam().get("showFilterMaterieTipiAtto") != null ? Boolean.valueOf(getExtraParam().get("showFilterMaterieTipiAtto")) : false;
		showFilterLiquidazioneContestualeImpegni = getExtraParam().get("showFilterLiquidazioneContestualeImpegni") != null ? Boolean.valueOf(getExtraParam().get("showFilterLiquidazioneContestualeImpegni")) : false;
		showFilterLiquidazioneContestualeAltriAspettiCont = getExtraParam().get("showFilterLiquidazioneContestualeAltriAspettiCont") != null ? Boolean.valueOf(getExtraParam().get("showFilterLiquidazioneContestualeAltriAspettiCont")) : false;
		showFilterDeterminaAggiudicaGara = getExtraParam().get("showFilterDeterminaAggiudicaGara") != null ? Boolean.valueOf(getExtraParam().get("showFilterDeterminaAggiudicaGara")) : false;
		showFilterUoCompetente = getExtraParam().get("showFilterUoCompetente") != null ? Boolean.valueOf(getExtraParam().get("showFilterUoCompetente")) : false;
		showFilterPresenzaOpere = getExtraParam().get("showFilterPresenzaOpere") != null ? Boolean.valueOf(getExtraParam().get("showFilterPresenzaOpere")) : false;
		showFilterOpera = getExtraParam().get("showFilterOpera") != null ? Boolean.valueOf(getExtraParam().get("showFilterOpera")) : false;
		showFilterSottoTipologiaAtto = getExtraParam().get("showFilterSottoTipologiaAtto") != null ? Boolean.valueOf(getExtraParam().get("showFilterSottoTipologiaAtto")) : false;
		showFilterProgrammazioneAcquisti = getExtraParam().get("showFilterProgrammazioneAcquisti") != null ? Boolean.valueOf(getExtraParam().get("showFilterProgrammazioneAcquisti")) : false;
		showFilterCui = getExtraParam().get("showFilterCui") != null ? Boolean.valueOf(getExtraParam().get("showFilterCui")) : false;
	}
	
	@Override
	public LinkedHashMap<String, String> getMappaFiltriToShow(LinkedHashMap<String, String> lMap) {
		
		if(!showFilterSmistamentoAtti) {
			lMap.remove("assegnatario");					
		}
		
		if(!showFilterCentroDiCosto) {
			lMap.remove("centroDiCosto");					
		}
		
		if(!showFilterDataScadenza) {
			lMap.remove("dataScadenza");					
		}
		
		if(!showFilterDeterminaContrarreConGara) {
			lMap.remove("determinaContrarreConGara");					
		}
		
		if(!showFilterTipoAffidamento) {
			lMap.remove("tipoAffidamento");					
		}
		
		if(!showFilterDeterminaRimodulazioneSpesaPostAggiudica) {
			lMap.remove("determinaRimodulazioneSpesaPostAggiudica");					
		}
		
		if(!showFilterMaterieTipiAtto) {
			lMap.remove("materieTipiAtto");					
		}
		
		if(!showFilterLiquidazioneContestualeImpegni) {
			lMap.remove("liquidazioneContestualeImpegni");					
		}
		
		if(!showFilterLiquidazioneContestualeAltriAspettiCont) {
			lMap.remove("liquidazioneContestualeAltriAspettiCont");					
		}
		
		if(!showFilterDeterminaAggiudicaGara) {
			lMap.remove("determinaAggiudicaGara");					
		}
		
		if(!showFilterUoCompetente) {
			lMap.remove("uoCompetente");					
		}
		
		if(!showFilterPresenzaOpere){
			lMap.remove("presenzaOpere");
		}
		
		if(!showFilterOpera){
			lMap.remove("opera");
		}
		
		if(!showFilterSottoTipologiaAtto) {
			lMap.remove("sottoTipologiaAtto");
		}
		
		if(!showFilterProgrammazioneAcquisti) {
			lMap.remove("programmazioneAcquisti");
		}
		
		if(!showFilterCui) {
			lMap.remove("cui");
		}
		
		return lMap;
	}
	
	@Override
	protected Criteria createCriteria(FormItemFunctionContext itemContext) {

		SelectItem lSelectItem = (SelectItem) itemContext.getFormItem();
		AdvancedCriteria lAdvancedCriteria = getCriteria(true);
		Criterion[] lCriterions = lAdvancedCriteria.getCriteria();
		String selected = "";
		
		if (!showFilterSmistamentoAtti){
			selected = selected  + "assegnatario,";						
		}
		
		if(!showFilterCentroDiCosto) {
			selected = selected  + "centroDiCosto,";						
		}
		
		if(!showFilterDataScadenza) {
			selected = selected  + "dataScadenza,";						
		}
		
		if(!showFilterDeterminaContrarreConGara) {
			selected = selected  + "determinaContrarreConGara,";						
		}
		
		if(!showFilterTipoAffidamento) {
			selected = selected  + "tipoAffidamento,";						
		}

		if(!showFilterDeterminaRimodulazioneSpesaPostAggiudica) {
			selected = selected  + "determinaRimodulazioneSpesaPostAggiudica,";						
		}
		
		if(!showFilterMaterieTipiAtto) {
			selected = selected  + "materieTipiAtto,";						
		}

		if(!showFilterLiquidazioneContestualeImpegni) {
			selected = selected  + "liquidazioneContestualeImpegni,";						
		}

		if(!showFilterLiquidazioneContestualeAltriAspettiCont) {
			selected = selected  + "liquidazioneContestualeAltriAspettiCont,";						
		}
		
		if(!showFilterDeterminaAggiudicaGara) {
			selected = selected  + "determinaAggiudicaGara,";						
		}
		
		if(!showFilterUoCompetente) {
			selected = selected  + "uoCompetente,";						
		}
		
		if(!showFilterPresenzaOpere) {
			selected = selected + "presenzaOpere,";
		}
		
		if(!showFilterOpera) {
			selected = selected + "opera,";
		}
		
		if(!showFilterSottoTipologiaAtto) {
			selected = selected + "sottoTipologiaAtto,";
		}
		
		if(!showFilterProgrammazioneAcquisti) {
			selected = selected + "programmazioneAcquisti,";
		}
		
		if(!showFilterCui) {
			selected = selected + "cui,";
		}
		
		for (Criterion lCriterion : lCriterions){
			if (lCriterion.getFieldName() != null && !lCriterion.getFieldName().equals(lSelectItem.getValue())){
				selected += lCriterion.getFieldName() + ",";
			}
		}	
		
		return new AdvancedCriteria("escludi", OperatorId.EQUALS, selected + ";" + new Date().toString());
	}

	@Override
	public void createFilteredSelectItem(final FilterFieldBean filterFieldBean, DataSourceField dataSourceField) {
		
		SelectItemFiltrabile lSelectItem = new SelectItemFiltrabile(filter, filterFieldBean, dataSourceField);
		
		final List<String> filterFieldsClearList =  new ArrayList<String>();
		
		for (FilterFieldBean currentFieldFilterBean : Layout.getFilterConfig("atti_completi_personali").getFields()){
			
			if (currentFieldFilterBean.getDependsFrom()!=null && currentFieldFilterBean.getDependsFrom().contains(filterFieldBean.getName())){
				filterFieldsClearList.add(currentFieldFilterBean.getName());
			}
		}
		
		if (filterFieldsClearList != null && !filterFieldsClearList.isEmpty()){
			
			lSelectItem.addChangedHandler(new ChangedHandler() {
				
				@Override
				public void onChanged(ChangedEvent event) {
					
					for (String lString : filterFieldsClearList){
						int position = getClausePosition(lString);
						if (position != -1){
							FormItem lClauseValueItem = (FormItem) getClauseValueItem(position);										
							SelectItem lClauseValueSelectItem = (SelectItem) lClauseValueItem;
							lClauseValueSelectItem.clearValue();
						}
					}
					
					if (filterFieldBean.getName().equals("idProcessType")){
						int position = getClausePosition("scadenza");
						if (position != -1){
							FormItem lClauseValueItem = (FormItem) getClauseValueItem(position);
							ScadenzaItem lClauseValueSelectItem = (ScadenzaItem) lClauseValueItem;
							DynamicForm lDynamicForm = (DynamicForm) lClauseValueSelectItem.getCanvas();
							SelectItem lSelectItem = (SelectItem) lDynamicForm.getFields()[0];
							lSelectItem.clearValue();
						}
					}
					
				}
			});
		} 
		dataSourceField.setEditorType(lSelectItem);
		dataSourceField.setFilterEditorType(SelectItem.class);
		mappaSelects.put(filterFieldBean.getName(), lSelectItem);
	}	
}