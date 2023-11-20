/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.Scheduler;
import com.google.gwt.core.client.Scheduler.ScheduledCommand;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.smartgwt.client.data.DSCallback;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.data.RecordList;
import com.smartgwt.client.types.FieldType;
import com.smartgwt.client.widgets.events.FetchDataEvent;
import com.smartgwt.client.widgets.events.FetchDataHandler;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.FormItemIfFunction;
import com.smartgwt.client.widgets.form.fields.FormItem;
import com.smartgwt.client.widgets.form.fields.HiddenItem;
import com.smartgwt.client.widgets.form.fields.events.ChangedEvent;
import com.smartgwt.client.widgets.form.fields.events.ChangedHandler;
import com.smartgwt.client.widgets.form.fields.events.DataArrivedEvent;
import com.smartgwt.client.widgets.form.fields.events.DataArrivedHandler;
import com.smartgwt.client.widgets.form.validator.RequiredIfFunction;
import com.smartgwt.client.widgets.form.validator.RequiredIfValidator;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridField;

import it.eng.auriga.ui.module.layout.client.AurigaLayout;
import it.eng.auriga.ui.module.layout.client.i18n.I18NUtil;
import it.eng.utility.ui.module.core.client.datasource.GWTRestDataSource;
import it.eng.utility.ui.module.core.client.datasource.SelectGWTRestDataSource;
import it.eng.utility.ui.module.layout.client.common.ReplicableCanvas;
import it.eng.utility.ui.module.layout.client.common.ReplicableItem;
import it.eng.utility.ui.module.layout.client.common.items.CheckboxItem;
import it.eng.utility.ui.module.layout.client.common.items.ExtendedTextItem;
import it.eng.utility.ui.module.layout.client.common.items.FilteredSelectItemWithDisplay;
import it.eng.utility.ui.module.layout.client.common.items.TextAreaItem;

public class RuoliScrivaniaAttiCompletaCanvas extends ReplicableCanvas {
	
	private HiddenItem codUoHiddenItem;
	private ExtendedTextItem codUoItem;
	private FilteredSelectItemWithDisplay idSvItem;
	private HiddenItem idSvFromLoadDettHiddenItem;
	private HiddenItem descrizioneHiddenItem;
	private CheckboxItem flgFirmatarioItem;
	private CheckboxItem flgRiacqVistoInRitornoIterItem;
	private TextAreaItem motiviItem;
	private HiddenItem codFiscaleHiddenItem;
	
	private ReplicableCanvasForm mDynamicForm;
	
	private String start;

	public RuoliScrivaniaAttiCompletaCanvas(ReplicableItem item) {
		super(item);
	}
	
	@Override
	public void disegna() {
		
		mDynamicForm = new ReplicableCanvasForm();
		mDynamicForm.setWrapItemTitles(false);
		mDynamicForm.setNumCols(5);
		mDynamicForm.setColWidths(1, 1, 1, "*", "*");
		
		List<FormItem> listaItems = new ArrayList<FormItem>();
		
		if (AurigaLayout.getParametroDBAsBoolean("DIM_ORGANIGRAMMA_NONSTD") && !isAltriParamWithDesRuoloOrIdUo() && isAltriParamWithNriLivelliUo()) {
			codUoItem = new ExtendedTextItem(((RuoliScrivaniaAttiCompletaItem)getItem()).getCodUoFieldName(), I18NUtil.getMessages().protocollazione_detail_codRapidoItem_title());
			codUoItem.setWidth(120);
			codUoItem.setColSpan(1);
			codUoItem.addChangedBlurHandler(new ChangedHandler() {
	
				@Override
				public void onChanged(ChangedEvent event) {
					mDynamicForm.setValue(((RuoliScrivaniaAttiCompletaItem)getItem()).getIdSvFieldName(), (String) null);
					mDynamicForm.setValue(((RuoliScrivaniaAttiCompletaItem)getItem()).getDescrizioneFieldName(), (String) null);
//					mDynamicForm.clearFieldErrors(((RuoliScrivaniaAttiCompletaItem)getItem()).getCodUoFieldName(), true);
					final String value = codUoItem.getValueAsString();
					if (value != null && !"".equals(value)) {
						idSvItem.fetchData(new DSCallback() {
	
							@Override
							public void execute(DSResponse response, Object rawData, DSRequest request) {
								RecordList data = response.getDataAsRecordList();
//								if (data.getLength() == 0) {
//									mDynamicForm.setFieldErrors(((RuoliScrivaniaAttiCompletaItem)getItem()).getCodUoFieldName(), "Valore non valido");
//								}
								if (data.getLength() == 1) {
									if (value.equals(data.get(0).getAttribute("codUo"))) {
										mDynamicForm.setValue(((RuoliScrivaniaAttiCompletaItem)getItem()).getIdSvFieldName(), data.get(0).getAttribute("idSv"));
										mDynamicForm.setValue(((RuoliScrivaniaAttiCompletaItem)getItem()).getDescrizioneFieldName(), data.get(0).getAttribute("descrizione"));
										if (((RuoliScrivaniaAttiCompletaItem)getItem()).getCodiceFiscaleFieldName() != null && !"".equalsIgnoreCase(((RuoliScrivaniaAttiCompletaItem)getItem()).getCodiceFiscaleFieldName())){
											mDynamicForm.setValue(((RuoliScrivaniaAttiCompletaItem)getItem()).getCodiceFiscaleFieldName(), data.get(0).getAttribute("codiceFiscale"));
										}
									}
								}
								/*
								if (data.getLength() > 0) {
									for (int i = 0; i < data.getLength(); i++) {
										if (value.equals(data.get(i).getAttribute("codUo"))) {
											mDynamicForm.setValue(((RuoliScrivaniaAttiCompletaItem)getItem()).getIdSvFieldName(), data.get(i).getAttribute("idSv"));
											mDynamicForm.setValue(((RuoliScrivaniaAttiCompletaItem)getItem()).getDescrizioneFieldName(), data.get(i).getAttribute("descrizione"));
											break;
										}
									}
								}*/
								((RuoliScrivaniaAttiCompletaItem)getItem()).manageChangedScrivaniaSelezionata();								
							}
						});
					} else {
						idSvItem.fetchData();
						((RuoliScrivaniaAttiCompletaItem)getItem()).manageChangedScrivaniaSelezionata();
					}
				}
			});			
			listaItems.add(codUoItem);		
		} else {
			codUoHiddenItem = new HiddenItem(((RuoliScrivaniaAttiCompletaItem)getItem()).getCodUoFieldName());
			listaItems.add(codUoHiddenItem);
		}
		
		String[] valorDaMostrare = new String[]{"codUo", "descrizione"};
		if(((RuoliScrivaniaAttiCompletaItem)getItem()).hideNriLivelliUo()) {
			valorDaMostrare = new String[]{"descrizione"};
		}
		SelectGWTRestDataSource ruoliScrivaniaAttiDS = new SelectGWTRestDataSource("LoadComboRuoliScrivaniaAttiDataSource", "idSv", FieldType.TEXT, valorDaMostrare, true);
		if(((RuoliScrivaniaAttiCompletaItem)getItem()).getIdUdAtto() != null) {
			ruoliScrivaniaAttiDS.addParam("idUdAtto", ((RuoliScrivaniaAttiCompletaItem)getItem()).getIdUdAtto());
		}
		if(((RuoliScrivaniaAttiCompletaItem)getItem()).getIdUdAttoDaAnn() != null) {
			ruoliScrivaniaAttiDS.addParam("idUdAttoDaAnn", ((RuoliScrivaniaAttiCompletaItem)getItem()).getIdUdAttoDaAnn());
		}
		if(((RuoliScrivaniaAttiCompletaItem)getItem()).getAltriParamLoadCombo() != null) {
			ruoliScrivaniaAttiDS.addParam("altriParamLoadCombo", ((RuoliScrivaniaAttiCompletaItem)getItem()).getAltriParamLoadCombo());
		}		
		idSvItem = new FilteredSelectItemWithDisplay(((RuoliScrivaniaAttiCompletaItem)getItem()).getIdSvFieldName(), ruoliScrivaniaAttiDS) {
			
			@Override
			protected ListGrid builPickListProperties() {
				ListGrid idSvPickListProperties = super.builPickListProperties();	
				if(!isCodUoFieldWithFilter() && !isDescrizioneFieldWithFilter()) {
					idSvPickListProperties.setShowFilterEditor(false); 
				}
				idSvPickListProperties.addFetchDataHandler(new FetchDataHandler() {

					@Override
					public void onFilterData(FetchDataEvent event) {
						
						start = DateTimeFormat.getFormat("HH:mm:ss").format(new Date());
						
						GWTRestDataSource ruoliScrivaniaAttiDS = (GWTRestDataSource) idSvItem.getOptionDataSource();		
						boolean isRequiredFilterCodUo = AurigaLayout.getParametroDBAsBoolean("DIM_ORGANIGRAMMA_NONSTD") && !isAltriParamWithDesRuoloOrIdUo() && isAltriParamWithNriLivelliUo();
						if(isRequiredFilterCodUo) {
							ruoliScrivaniaAttiDS.addParam("codUo", codUoItem.getValueAsString());
						}
						String idSvSelezionata = (String) mDynamicForm.getValue(((RuoliScrivaniaAttiCompletaItem)getItem()).getIdSvFieldName());
						String idSvFromLoadDett = (String) mDynamicForm.getValue(((RuoliScrivaniaAttiCompletaItem)getItem()).getIdSvFromLoadDettFieldName());
						ruoliScrivaniaAttiDS.addParam("idSvSelezionata", idSvSelezionata);
						ruoliScrivaniaAttiDS.addParam("idSvFromLoadDett", idSvFromLoadDett);
						ruoliScrivaniaAttiDS.addParam("uoProponente", ((RuoliScrivaniaAttiCompletaItem)getItem()).getUoProponenteCorrente());
						ruoliScrivaniaAttiDS.addParam("uoCompetente", ((RuoliScrivaniaAttiCompletaItem)getItem()).getUoCompetenteCorrente());						
						Boolean flgAttoMeroIndirizzo = ((RuoliScrivaniaAttiCompletaItem)getItem()).getFlgAttoMeroIndirizzo();
						ruoliScrivaniaAttiDS.addParam("flgAttoMeroIndirizzo", flgAttoMeroIndirizzo != null && flgAttoMeroIndirizzo ? "1" : "");					
						idSvItem.setOptionDataSource(ruoliScrivaniaAttiDS);
						idSvItem.invalidateDisplayValueCache();
					}
				});
				return idSvPickListProperties;
			}
			
			@Override
			public void onOptionClick(Record record) {
				super.onOptionClick(record);				
				mDynamicForm.clearErrors(true);				
				mDynamicForm.setValue(((RuoliScrivaniaAttiCompletaItem)getItem()).getCodUoFieldName(), record.getAttributeAsString("codUo"));
				mDynamicForm.setValue(((RuoliScrivaniaAttiCompletaItem)getItem()).getDescrizioneFieldName(), record.getAttributeAsString("descrizione"));
				if (((RuoliScrivaniaAttiCompletaItem)getItem()).getCodiceFiscaleFieldName() != null && !"".equalsIgnoreCase(((RuoliScrivaniaAttiCompletaItem)getItem()).getCodiceFiscaleFieldName())){
					mDynamicForm.setValue(((RuoliScrivaniaAttiCompletaItem)getItem()).getCodiceFiscaleFieldName(), record.getAttributeAsString("codiceFiscale"));
				}
				Scheduler.get().scheduleDeferred(new ScheduledCommand() {

					@Override
					public void execute() {
						((RuoliScrivaniaAttiCompletaItem)getItem()).manageChangedScrivaniaSelezionata();
					}
				});
			}

			@Override
			protected void clearSelect() {
				super.clearSelect();
				mDynamicForm.clearErrors(true);				
				mDynamicForm.setValue(((RuoliScrivaniaAttiCompletaItem)getItem()).getIdSvFieldName(), "");
				mDynamicForm.setValue(((RuoliScrivaniaAttiCompletaItem)getItem()).getCodUoFieldName(), "");
				mDynamicForm.setValue(((RuoliScrivaniaAttiCompletaItem)getItem()).getDescrizioneFieldName(), "");
				if (((RuoliScrivaniaAttiCompletaItem)getItem()).getCodiceFiscaleFieldName() != null && !"".equalsIgnoreCase(((RuoliScrivaniaAttiCompletaItem)getItem()).getCodiceFiscaleFieldName())){
					mDynamicForm.setValue(((RuoliScrivaniaAttiCompletaItem)getItem()).getCodiceFiscaleFieldName(), "");
				}
				Scheduler.get().scheduleDeferred(new ScheduledCommand() {

					@Override
					public void execute() {
						((RuoliScrivaniaAttiCompletaItem)getItem()).manageChangedScrivaniaSelezionata();
					}
				});
			};

			@Override
			public void setValue(String value) {
				super.setValue(value);
				if (value == null || "".equals(value)) {
					mDynamicForm.clearErrors(true);				
					mDynamicForm.setValue(((RuoliScrivaniaAttiCompletaItem)getItem()).getIdSvFieldName(), "");
					mDynamicForm.setValue(((RuoliScrivaniaAttiCompletaItem)getItem()).getCodUoFieldName(), "");
					mDynamicForm.setValue(((RuoliScrivaniaAttiCompletaItem)getItem()).getDescrizioneFieldName(), "");
					Scheduler.get().scheduleDeferred(new ScheduledCommand() {

						@Override
						public void execute() {
							((RuoliScrivaniaAttiCompletaItem)getItem()).manageChangedScrivaniaSelezionata();
						}
					});
				}
			}
			
			/*
			@Override
		    public void setCanEdit(Boolean canEdit) {
		    	super.setCanEdit(canEdit);
//		    	setTextBoxStyle(canEdit ? it.eng.utility.Styles.textItem : it.eng.utility.Styles.textItemReadonly + " " + it.eng.utility.Styles.hideItemScrollBar);
		    	setTextBoxStyle(canEdit ? it.eng.utility.Styles.selectItemText : it.eng.utility.Styles.selectItemTextReadOnly);			    	
		    }
		    */
		};
		idSvItem.setShowTitle(false);
		idSvItem.setWidth(650);
		idSvItem.setValueField("idSv");
		ListGridField descrizioneField = new ListGridField("descrizione", "Descrizione");
		descrizioneField.setWidth("*");
		descrizioneField.setCanFilter(isDescrizioneFieldWithFilter());		
		if(((RuoliScrivaniaAttiCompletaItem)getItem()).hideNriLivelliUo()) {
			idSvItem.setPickListFields(descrizioneField);
		} else {
			ListGridField codUoField = new ListGridField("codUo", "Cod. U.O.");
			codUoField.setWidth(120);		
			codUoField.setCanFilter(isCodUoFieldWithFilter());
			idSvItem.setPickListFields(codUoField, descrizioneField);
		}
		boolean isRequiredFilterCodUo = AurigaLayout.getParametroDBAsBoolean("DIM_ORGANIGRAMMA_NONSTD") && !isAltriParamWithDesRuoloOrIdUo() && isAltriParamWithNriLivelliUo();
		boolean isRequiredFilterDescrizione = !isRequiredFilterCodUo && AurigaLayout.getParametroDBAsBoolean("DIM_ORGANIGRAMMA_NONSTD") && !isAltriParamWithDesRuoloOrIdUo() && isAltriParamWithStrInDes();						
		if (isRequiredFilterCodUo) {
			idSvItem.setEmptyPickListMessage("Nessun record trovato o filtri incompleti o poco restrittivi: digitare il cod. rapido");
		} else if (isRequiredFilterDescrizione) {
			idSvItem.setEmptyPickListMessage("Nessun record trovato o filtri incompleti o poco restrittivi: filtrare per descrizione (almeno 3 caratteri)");
		} else {
			if(isCodUoFieldWithFilter() || isDescrizioneFieldWithFilter()) {
				idSvItem.setEmptyPickListMessage("Nessun record trovato o filtri incompleti o poco restrittivi: aggiungere dei filtri");
			} else {
				idSvItem.setEmptyPickListMessage("Nessun record trovato");		
			}
		}
		idSvItem.setFilterLocally(true);
		idSvItem.setAllowEmptyValue(false);
		// WORKAROUND PER IL PROBLEMA RELATIVO AL METODO resetAfterChangedParams CHE, A SEGUITO DELLA FETCH, NON ENTRA NELLA CALLBACK
		if(((RuoliScrivaniaAttiCompletaItem)getItem()).getFlgAbilitaAutoFetchDataSelectOrganigramma()) {
			idSvItem.setAutoFetchData(true);
		} else {
			idSvItem.setAutoFetchData(false);
		}
		idSvItem.setAlwaysFetchMissingValues(true);
		idSvItem.setFetchMissingValues(true);
		idSvItem.setClearable(true);
		idSvItem.addDataArrivedHandler(new DataArrivedHandler() {

			@Override
			public void onDataArrived(DataArrivedEvent event) {
				
				GWT.log("loadCombo() " + getItem().getName() + " started at " + start + " ended at " + DateTimeFormat.getFormat("HH:mm:ss").format(new Date()));
				
				// se la fetch è andata in errore mi arriverà endRow = -1
				boolean empty = event.getData().getLength() == 0 && event.getEndRow() != -1; 
				idSvItem.setAttribute("empty", empty);
				if (event.getData().getLength() == 0) { 
					mDynamicForm.setValue(((RuoliScrivaniaAttiCompletaItem)getItem()).getIdSvFieldName(), "");
					mDynamicForm.setValue(((RuoliScrivaniaAttiCompletaItem)getItem()).getCodUoFieldName(), "");
					mDynamicForm.setValue(((RuoliScrivaniaAttiCompletaItem)getItem()).getDescrizioneFieldName(), "");
					if (((RuoliScrivaniaAttiCompletaItem)getItem()).getCodiceFiscaleFieldName() != null && !"".equalsIgnoreCase(((RuoliScrivaniaAttiCompletaItem)getItem()).getCodiceFiscaleFieldName())){
						mDynamicForm.setValue(((RuoliScrivaniaAttiCompletaItem)getItem()).getCodiceFiscaleFieldName(), "");
					}								
				}
				if(((RuoliScrivaniaAttiCompletaItem)getItem()).selectUniqueValueAfterChangedParams() && event.getData().getLength() == 1) {				 
					mDynamicForm.setValue(((RuoliScrivaniaAttiCompletaItem)getItem()).getIdSvFieldName(), event.getData().get(0).getAttribute("idSv"));
					mDynamicForm.setValue(((RuoliScrivaniaAttiCompletaItem)getItem()).getCodUoFieldName(), event.getData().get(0).getAttribute("codUo"));
					mDynamicForm.setValue(((RuoliScrivaniaAttiCompletaItem)getItem()).getDescrizioneFieldName(), event.getData().get(0).getAttribute("descrizione"));
					if (((RuoliScrivaniaAttiCompletaItem)getItem()).getCodiceFiscaleFieldName() != null && !"".equalsIgnoreCase(((RuoliScrivaniaAttiCompletaItem)getItem()).getCodiceFiscaleFieldName())){
						mDynamicForm.setValue(((RuoliScrivaniaAttiCompletaItem)getItem()).getCodiceFiscaleFieldName(), event.getData().get(0).getAttribute("codiceFiscale"));
					}				
				}
				manageAfterReloadSelect(empty);										
			}
		});
		idSvItem.setValidators(new RequiredIfValidator(new RequiredIfFunction() {

			@Override
			public boolean execute(FormItem formItem, Object value) {
				if(((RuoliScrivaniaAttiCompletaItem)getItem()).skipObbligForEmptySelect()) {
					Boolean empty = idSvItem.getAttributeAsBoolean("empty") != null ? idSvItem.getAttributeAsBoolean("empty") : false;
					if(empty) {
						return false;
					}
				}
				Boolean notReplicable = getItem().getNotReplicable() != null ? getItem().getNotReplicable() : false;
				Boolean obbligatorio = getItem().getAttributeAsBoolean("obbligatorio") != null ? getItem().getAttributeAsBoolean("obbligatorio") : false;
				return !notReplicable || obbligatorio;
			}
		}));
		listaItems.add(idSvItem);
				
		idSvFromLoadDettHiddenItem = new HiddenItem(((RuoliScrivaniaAttiCompletaItem)getItem()).getIdSvFromLoadDettFieldName());
		listaItems.add(idSvFromLoadDettHiddenItem);
		
		descrizioneHiddenItem = new HiddenItem(((RuoliScrivaniaAttiCompletaItem)getItem()).getDescrizioneFieldName());
		listaItems.add(descrizioneHiddenItem);
		
		if(((RuoliScrivaniaAttiCompletaItem)getItem()).getFlgFirmatarioFieldName() != null && !"".equals(((RuoliScrivaniaAttiCompletaItem)getItem()).getFlgFirmatarioFieldName())) {
			flgFirmatarioItem = new CheckboxItem(((RuoliScrivaniaAttiCompletaItem)getItem()).getFlgFirmatarioFieldName(), ((RuoliScrivaniaAttiCompletaItem)getItem()).getTitleFlgFirmatario());
			flgFirmatarioItem.setDefaultValue(((RuoliScrivaniaAttiCompletaItem)getItem()).getDefaultValueAsBooleanFlgFirmatario());
			flgFirmatarioItem.setColSpan(1);
			flgFirmatarioItem.setWidth("*");
			flgFirmatarioItem.setShowIfCondition(new FormItemIfFunction() {
				
				@Override
				public boolean execute(FormItem item, Object value, DynamicForm form) {
					return ((RuoliScrivaniaAttiCompletaItem)getItem()).showFlgFirmatario();
				}
			});
			listaItems.add(flgFirmatarioItem);			
		}
		
		if(((RuoliScrivaniaAttiCompletaItem)getItem()).getFlgRiacqVistoInRitornoIterFieldName() != null && !"".equals(((RuoliScrivaniaAttiCompletaItem)getItem()).getFlgRiacqVistoInRitornoIterFieldName())) {
			flgRiacqVistoInRitornoIterItem = new CheckboxItem(((RuoliScrivaniaAttiCompletaItem)getItem()).getFlgRiacqVistoInRitornoIterFieldName(), "visto da ri-acquisire in caso di ritorno indietro nell'iter");
			flgRiacqVistoInRitornoIterItem.setDefaultValue(false);
			flgRiacqVistoInRitornoIterItem.setColSpan(1);
			flgRiacqVistoInRitornoIterItem.setWidth("*");
			flgRiacqVistoInRitornoIterItem.setShowIfCondition(new FormItemIfFunction() {
				
				@Override
				public boolean execute(FormItem item, Object value, DynamicForm form) {
					return ((RuoliScrivaniaAttiCompletaItem)getItem()).showFlgRiacqVistoInRitornoIter();
				}
			});
			listaItems.add(flgRiacqVistoInRitornoIterItem);			
		}
		
		if(((RuoliScrivaniaAttiCompletaItem)getItem()).getMotiviFieldName() != null && !"".equals(((RuoliScrivaniaAttiCompletaItem)getItem()).getMotiviFieldName())) {			
			motiviItem = new TextAreaItem(((RuoliScrivaniaAttiCompletaItem)getItem()).getMotiviFieldName());
			motiviItem.setShowTitle(false);
			motiviItem.setHint(((RuoliScrivaniaAttiCompletaItem)getItem()).getTitleMotivi());
			motiviItem.setShowHintInField(true);
			motiviItem.setColSpan(20);
			motiviItem.setStartRow(true);
			motiviItem.setLength(4000);
			motiviItem.setHeight(40);
			motiviItem.setWidth(631); // devo settare la larghezza delle motivazioni a 631 (650 - 19) così rimane allineato rispetto alla select sopra 
			if(((RuoliScrivaniaAttiCompletaItem)getItem()).isRequiredMotivi()) {
				motiviItem.setRequired(true);
			}
			motiviItem.setShowIfCondition(new FormItemIfFunction() {
				
				@Override
				public boolean execute(FormItem item, Object value, DynamicForm form) {
					return ((RuoliScrivaniaAttiCompletaItem)getItem()).showMotivi();
				}
			});
			listaItems.add(motiviItem);	
		}
		
		if(((RuoliScrivaniaAttiCompletaItem)getItem()).getCodiceFiscaleFieldName() != null && !"".equals(((RuoliScrivaniaAttiCompletaItem)getItem()).getCodiceFiscaleFieldName())) {	
			codFiscaleHiddenItem = new HiddenItem(((RuoliScrivaniaAttiCompletaItem)getItem()).getCodiceFiscaleFieldName());
			listaItems.add(codFiscaleHiddenItem);
		}
		
		listaItems.addAll(((RuoliScrivaniaAttiCompletaItem)getItem()).getCustomItems(this));
		
		mDynamicForm.setFields(listaItems.toArray(new FormItem[listaItems.size()]));
		
//		mDynamicForm.setNumCols(5);
		
		addChild(mDynamicForm);
		
	}
	
	public boolean isCodUoFieldWithFilter() {
		if(!isAltriParamWithNriLivelliUo()) {
			return false;		
		} else if (AurigaLayout.getParametroDBAsBoolean("DIM_ORGANIGRAMMA_NONSTD") && !isAltriParamWithDesRuoloOrIdUo()) {
			return false;			
		}
		return true;
		
	}
	
	public boolean isDescrizioneFieldWithFilter() {
		return isAltriParamWithStrInDes();
	}
	
	public boolean isAltriParamWithNriLivelliUo() {
		String altriParamLoadCombo = ((RuoliScrivaniaAttiCompletaItem)getItem()).getAltriParamLoadCombo();
		return altriParamLoadCombo != null && altriParamLoadCombo.indexOf("NRI_LIVELLI_UO|*|") != -1;
	}
	
	public boolean isAltriParamWithStrInDes() {
		String altriParamLoadCombo = ((RuoliScrivaniaAttiCompletaItem)getItem()).getAltriParamLoadCombo();
		return altriParamLoadCombo != null && altriParamLoadCombo.indexOf("STR_IN_DES|*|") != -1;
	}
	
	public boolean isAltriParamWithDesRuoloOrIdUo() {
		String altriParamLoadCombo = ((RuoliScrivaniaAttiCompletaItem)getItem()).getAltriParamLoadCombo();
		return altriParamLoadCombo != null && (altriParamLoadCombo.indexOf("DES_RUOLO|*|") != -1 || altriParamLoadCombo.indexOf("ID_UO|*|") != -1);
	}
	
	public void resetAfterChangedParams() {
		
		final String value = idSvItem.getValueAsString();
		idSvItem.fetchData(new DSCallback() {

			@Override
			public void execute(DSResponse response, Object rawData, DSRequest request) {

				RecordList data = response.getDataAsRecordList();
				if(((RuoliScrivaniaAttiCompletaItem)getItem()).selectUniqueValueAfterChangedParams() && data.getLength() == 1) {
					if(value == null || "".equals(value) || !value.equals(data.get(0).getAttributeAsString("idSv"))) {	
						mDynamicForm.setValue(((RuoliScrivaniaAttiCompletaItem)getItem()).getIdSvFieldName(), data.get(0).getAttributeAsString("idSv"));
						mDynamicForm.setValue(((RuoliScrivaniaAttiCompletaItem)getItem()).getCodUoFieldName(), data.get(0).getAttributeAsString("codUo"));
						mDynamicForm.setValue(((RuoliScrivaniaAttiCompletaItem)getItem()).getDescrizioneFieldName(), data.get(0).getAttributeAsString("descrizione"));
						Scheduler.get().scheduleDeferred(new ScheduledCommand() {
	
							@Override
							public void execute() {
								((RuoliScrivaniaAttiCompletaItem)getItem()).manageChangedScrivaniaSelezionata();
							}
						});
					}
				} else if(value != null && !"".equals(value)) {					
					boolean trovato = false;
					if (data.getLength() > 0) {
						for (int i = 0; i < data.getLength(); i++) {
							String idSv = data.get(i).getAttribute("idSv");
							if (value.equals(idSv)) {
								trovato = true;
								break;
							}
						}
					}
					if (!trovato) {
						mDynamicForm.setValue(((RuoliScrivaniaAttiCompletaItem)getItem()).getIdSvFieldName(), "");
						mDynamicForm.setValue(((RuoliScrivaniaAttiCompletaItem)getItem()).getCodUoFieldName(), "");
						mDynamicForm.setValue(((RuoliScrivaniaAttiCompletaItem)getItem()).getDescrizioneFieldName(), "");
						Scheduler.get().scheduleDeferred(new ScheduledCommand() {
	
							@Override
							public void execute() {
								((RuoliScrivaniaAttiCompletaItem)getItem()).manageChangedScrivaniaSelezionata();
							}
						});
					}
				}
			}
		});
	}

	@Override
	public ReplicableCanvasForm[] getForm() {
		return new ReplicableCanvasForm[]{mDynamicForm};
	}
	
	@Override
	public void editRecord(Record record) {
		String[] valorDaMostrare = new String[]{((RuoliScrivaniaAttiCompletaItem)getItem()).getCodUoFieldName(), ((RuoliScrivaniaAttiCompletaItem)getItem()).getDescrizioneFieldName()};
		if(((RuoliScrivaniaAttiCompletaItem)getItem()).hideNriLivelliUo()) {
			valorDaMostrare = new String[]{((RuoliScrivaniaAttiCompletaItem)getItem()).getDescrizioneFieldName()};
		}
		manageLoadSelectInEditRecord(record, idSvItem, ((RuoliScrivaniaAttiCompletaItem)getItem()).getIdSvFieldName(), valorDaMostrare, "idSv");
		super.editRecord(record);					
	}	
	
	@Override
	public void setCanEdit(Boolean canEdit) {
		super.setCanEdit(canEdit);
		if(idSvItem != null) {
			idSvItem.setCanEdit(((RuoliScrivaniaAttiCompletaItem)getItem()).isEditableScrivania() ? canEdit : false);
		}
		if(flgFirmatarioItem != null) {
			flgFirmatarioItem.setCanEdit(((RuoliScrivaniaAttiCompletaItem)getItem()).isEditableFlgFirmatario() ? canEdit : false);
		}
		if(motiviItem != null) {
			motiviItem.setCanEdit(((RuoliScrivaniaAttiCompletaItem)getItem()).isEditableMotivi() ? canEdit : false);
		}
		if (getItem() instanceof DirigenteRespRegTecnicaCompletaItem) {
			((DirigenteRespRegTecnicaCompletaItem)getItem()).manageEditCustomItems(canEdit);
		} else if (getItem() instanceof AltriDirRespRegTecnicaCompletaItem) {
			((AltriDirRespRegTecnicaCompletaItem)getItem()).manageEditCustomItems(canEdit);
		}
	}
	
	public void manageAfterReloadSelect(boolean empty) {
		Boolean notReplicable = getItem().getNotReplicable() != null ? getItem().getNotReplicable() : false;				
		if(notReplicable) {
			((RuoliScrivaniaAttiCompletaItem)getItem()).manageAfterReloadSelectInNotReplicableCanvas(empty);			
		}		
	}
	
}
