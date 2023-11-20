/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.core.client.Scheduler;
import com.google.gwt.core.client.Scheduler.ScheduledCommand;
import com.smartgwt.client.data.DSCallback;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.data.RecordList;
import com.smartgwt.client.types.FieldType;
import com.smartgwt.client.types.TitleOrientation;
import com.smartgwt.client.widgets.events.FetchDataEvent;
import com.smartgwt.client.widgets.events.FetchDataHandler;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.FormItemIfFunction;
import com.smartgwt.client.widgets.form.fields.FormItem;
import com.smartgwt.client.widgets.form.fields.HiddenItem;
import com.smartgwt.client.widgets.form.fields.events.ChangedEvent;
import com.smartgwt.client.widgets.form.fields.events.ChangedHandler;
import com.smartgwt.client.widgets.form.validator.RequiredIfFunction;
import com.smartgwt.client.widgets.form.validator.RequiredIfValidator;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridField;

import it.eng.auriga.ui.module.layout.client.AurigaLayout;
import it.eng.auriga.ui.module.layout.client.i18n.I18NUtil;
import it.eng.utility.ui.module.core.client.datasource.GWTRestDataSource;
import it.eng.utility.ui.module.core.client.datasource.SelectGWTRestDataSource;
import it.eng.utility.ui.module.layout.client.common.ReplicableCanvas;
import it.eng.utility.ui.module.layout.client.common.ReplicableCanvas.ReplicableCanvasForm;
import it.eng.utility.ui.module.layout.client.common.items.ExtendedTextItem;
import it.eng.utility.ui.module.layout.client.common.items.FilteredSelectItemWithDisplay;
import it.eng.utility.ui.module.layout.client.common.items.TextItem;
import it.eng.utility.ui.module.layout.shared.util.FrontendUtil;

public class AltriDirRespRegTecnicaCompletaItem extends RuoliScrivaniaAttiCompletaItem {

	private FilteredSelectItemWithDisplay idSvSostitutoItem;
	private ExtendedTextItem codUoSostitutoItem;
	private HiddenItem idSvFromLoadDettSostitutoHiddenItem;
	private TextItem provvedimentoSostitutoItem;
	private HiddenItem flgModificabileSostitutoHiddenItem;
	
	
	public AltriDirRespRegTecnicaCompletaItem() {
		super("dirigenteRespRegTecnica", "dirigenteRespRegTecnicaFromLoadDett", "codUoDirigenteRespRegTecnica", "desDirigenteRespRegTecnica", "flgDirigenteRespRegTecnicaFirmatario");
	}
	
	@Override
	public List<FormItem> getCustomItems(ReplicableCanvas canv) {
		final ReplicableCanvasForm[] form = ((RuoliScrivaniaAttiCompletaCanvas)canv).getForm();
		List<FormItem> customItems = new ArrayList<FormItem>();
//		inizio sostituto item
		if (AurigaLayout.getParametroDBAsBoolean("DIM_ORGANIGRAMMA_NONSTD") && !isAltriParamWithDesRuoloOrIdUoSostituto() && isAltriParamWithNriLivelliUoSostituto()) {
			codUoSostitutoItem = new ExtendedTextItem(getCodUoFieldName(), I18NUtil.getMessages().protocollazione_detail_codRapidoItem_title());
			codUoSostitutoItem.setWidth(120);
			codUoSostitutoItem.setColSpan(1);
			codUoSostitutoItem.addChangedBlurHandler(new ChangedHandler() {
	
				@Override
				public void onChanged(ChangedEvent event) {
					form[0].setValue(getIdSvSostitutoFieldName(), (String) null);
					form[0].setValue(getDescrizioneSostitutoFieldName(), (String) null);
//					mDynamicForm.clearFieldErrors(((RuoliScrivaniaAttiCompletaItem)getItem()).getCodUoFieldName(), true);
					final String value = codUoSostitutoItem.getValueAsString();
					if (value != null && !"".equals(value)) {
						idSvSostitutoItem.fetchData(new DSCallback() {
	
							@Override
							public void execute(DSResponse response, Object rawData, DSRequest request) {
								RecordList data = response.getDataAsRecordList();
//								if (data.getLength() == 0) {
//									mDynamicForm.setFieldErrors(((RuoliScrivaniaAttiCompletaItem)getItem()).getCodUoFieldName(), "Valore non valido");
//								}
								if (data.getLength() == 1) {
									if (value.equals(data.get(0).getAttribute("codUo"))) {
										form[0].setValue(getIdSvSostitutoFieldName(), data.get(0).getAttribute("idSv"));
										form[0].setValue(getDescrizioneSostitutoFieldName(), data.get(0).getAttribute("descrizione"));
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
								manageChangedScrivaniaSelezionata();								
							}
						});
					} else {
						idSvSostitutoItem.fetchData();
						manageChangedScrivaniaSelezionata();
					}
				}
			});			
			customItems.add(codUoSostitutoItem);		
		} else {
			HiddenItem codUoSostitutoHiddenItem = new HiddenItem(getCodUoSostitutoFieldName());
			customItems.add(codUoSostitutoHiddenItem);
		}
		
		SelectGWTRestDataSource ruoliScrivaniaAttiDS = new SelectGWTRestDataSource("LoadComboRuoliScrivaniaAttiDataSource", "idSv", FieldType.TEXT, new String[]{"codUo", "descrizione"}, true);
		if(getIdUdAttoDaAnn() != null) {
			ruoliScrivaniaAttiDS.addParam("idUdAttoDaAnn", getIdUdAttoDaAnn());
		}
		if(getAltriParamLoadComboSostituto() != null) {
			ruoliScrivaniaAttiDS.addParam("altriParamLoadCombo", getAltriParamLoadComboSostituto());
		}		
		idSvSostitutoItem = new FilteredSelectItemWithDisplay(getIdSvSostitutoFieldName(), getTitleSostitutoDirRespRegTecnicaItem(), ruoliScrivaniaAttiDS) {
			
			@Override
			protected ListGrid builPickListProperties() {
				ListGrid idSvPickListProperties = super.builPickListProperties();	
				if(!isCodUoFieldWithFilterSostituto() && !isDescrizioneFieldWithFilterSostituto()) {
					idSvPickListProperties.setShowFilterEditor(false); 
				}
				idSvPickListProperties.addFetchDataHandler(new FetchDataHandler() {

					@Override
					public void onFilterData(FetchDataEvent event) {
						GWTRestDataSource ruoliScrivaniaAttiDS = (GWTRestDataSource) idSvSostitutoItem.getOptionDataSource();		
						boolean isRequiredFilterCodUo = AurigaLayout.getParametroDBAsBoolean("DIM_ORGANIGRAMMA_NONSTD") && !isAltriParamWithDesRuoloOrIdUoSostituto() && isAltriParamWithNriLivelliUoSostituto();
						if(isRequiredFilterCodUo) {
							ruoliScrivaniaAttiDS.addParam("codUo", codUoSostitutoItem.getValueAsString());
						}
						ruoliScrivaniaAttiDS.addParam("idSvFromLoadDett", (String) idSvFromLoadDettSostitutoHiddenItem.getValue());
						ruoliScrivaniaAttiDS.addParam("uoProponente", getUoProponenteCorrente());
						Boolean flgAttoMeroIndirizzo = getFlgAttoMeroIndirizzo();
						ruoliScrivaniaAttiDS.addParam("flgAttoMeroIndirizzo", flgAttoMeroIndirizzo != null && flgAttoMeroIndirizzo ? "1" : "");					
						idSvSostitutoItem.setOptionDataSource(ruoliScrivaniaAttiDS);
						idSvSostitutoItem.invalidateDisplayValueCache();
					}
				});
				return idSvPickListProperties;
			}
			
			@Override
			public void onOptionClick(Record record) {
				super.onOptionClick(record);				
				form[0].clearErrors(true);				
				form[0].setValue(getCodUoSostitutoFieldName(), record.getAttributeAsString("codUo"));
				form[0].setValue(getDescrizioneSostitutoFieldName(), record.getAttributeAsString("descrizione"));
				Scheduler.get().scheduleDeferred(new ScheduledCommand() {

					@Override
					public void execute() {
						manageChangedScrivaniaSelezionata();
					}
				});
			}

			@Override
			protected void clearSelect() {
				super.clearSelect();
				form[0].clearErrors(true);				
				form[0].setValue(getIdSvSostitutoFieldName(), "");
				form[0].setValue(getCodUoSostitutoFieldName(), "");
				form[0].setValue(getDescrizioneSostitutoFieldName(), "");
				Scheduler.get().scheduleDeferred(new ScheduledCommand() {

					@Override
					public void execute() {
						manageChangedScrivaniaSelezionata();
					}
				});
			};

			@Override
			public void setValue(String value) {
				super.setValue(value);
				if (value == null || "".equals(value)) {
					form[0].clearErrors(true);				
					form[0].setValue(getIdSvSostitutoFieldName(), "");
					form[0].setValue(getCodUoSostitutoFieldName(), "");
					form[0].setValue(getDescrizioneSostitutoFieldName(), "");
					Scheduler.get().scheduleDeferred(new ScheduledCommand() {

						@Override
						public void execute() {
							manageChangedScrivaniaSelezionata();
						}
					});
				}
			}
		};
		idSvSostitutoItem.setShowTitle(true);
		idSvSostitutoItem.setTitleOrientation(TitleOrientation.TOP);
		idSvSostitutoItem.setStartRow(true);
		idSvSostitutoItem.setWidth(669); // setto la larghezza a 650 + 19 o rimane disallineato rispetto alle motivazioni sotto
		idSvSostitutoItem.setValueField("idSv");
		
		ListGridField codUoField = new ListGridField("codUo", "Cod. U.O.");
		codUoField.setWidth(120);		
		codUoField.setCanFilter(isCodUoFieldWithFilterSostituto());				
		ListGridField descrizioneField = new ListGridField("descrizione", "Descrizione");
		descrizioneField.setWidth("*");
		descrizioneField.setCanFilter(isDescrizioneFieldWithFilterSostituto());		
		idSvSostitutoItem.setPickListFields(codUoField, descrizioneField);		
		boolean isRequiredFilterCodUo = AurigaLayout.getParametroDBAsBoolean("DIM_ORGANIGRAMMA_NONSTD") && !isAltriParamWithDesRuoloOrIdUoSostituto() && isAltriParamWithNriLivelliUoSostituto();
		boolean isRequiredFilterDescrizione = !isRequiredFilterCodUo && AurigaLayout.getParametroDBAsBoolean("DIM_ORGANIGRAMMA_NONSTD") && !isAltriParamWithDesRuoloOrIdUoSostituto() && isAltriParamWithStrInDesSostituto();						
		if (isRequiredFilterCodUo) {
			idSvSostitutoItem.setEmptyPickListMessage("Nessun record trovato o filtri incompleti o poco restrittivi: digitare il cod. rapido");
		} else if (isRequiredFilterDescrizione) {
			idSvSostitutoItem.setEmptyPickListMessage("Nessun record trovato o filtri incompleti o poco restrittivi: filtrare per descrizione (almeno 3 caratteri)");
		} else {
			idSvSostitutoItem.setEmptyPickListMessage("Nessun record trovato");		
		}
		idSvSostitutoItem.setFilterLocally(true);
		idSvSostitutoItem.setAllowEmptyValue(false);
		idSvSostitutoItem.setAutoFetchData(false);
		idSvSostitutoItem.setAlwaysFetchMissingValues(true);
		idSvSostitutoItem.setFetchMissingValues(true);
		idSvSostitutoItem.setClearable(true);
		idSvSostitutoItem.setShowIfCondition(new FormItemIfFunction() {

			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				if(isRequiredSostitutoItem()) {
					idSvSostitutoItem.setAttribute("obbligatorio", true);
					idSvSostitutoItem.setTitle(FrontendUtil.getRequiredFormItemTitle(getTitleSostitutoDirRespRegTecnicaItem()));
				} else {					
					idSvSostitutoItem.setAttribute("obbligatorio", false);
					idSvSostitutoItem.setTitle(getTitleSostitutoDirRespRegTecnicaItem());					
				}
				return showSostitutoRespRegTecnicaItem();
			}
			
		});
		idSvSostitutoItem.setValidators(new RequiredIfValidator(new RequiredIfFunction() {

			@Override
			public boolean execute(FormItem formItem, Object value) {				
				return idSvSostitutoItem.getAttributeAsBoolean("obbligatorio") != null && idSvSostitutoItem.getAttributeAsBoolean("obbligatorio");
			}
		}));
		customItems.add(idSvSostitutoItem);
				
		idSvFromLoadDettSostitutoHiddenItem = new HiddenItem(getIdSvSostitutoFromLoadDettFieldName());
		customItems.add(idSvFromLoadDettSostitutoHiddenItem);
		
		HiddenItem descrizioneSostitutoHiddenItem = new HiddenItem(getDescrizioneSostitutoFieldName());
		customItems.add(descrizioneSostitutoHiddenItem);
		
		flgModificabileSostitutoHiddenItem =  new HiddenItem(getFlgModificabileSostitutoFieldName());
		customItems.add(flgModificabileSostitutoHiddenItem);
		
		
//		fine sostituto item
		
//		inizio provvedimento sostituto item
		
		provvedimentoSostitutoItem = new TextItem("provvedimentoSostituto", getTitleProvvedimentoSostitutoDirRespRegTecnicaItem());
		provvedimentoSostitutoItem.setWidth(175);
		provvedimentoSostitutoItem.setColSpan(4);
		provvedimentoSostitutoItem.setTitleOrientation(TitleOrientation.TOP);
		provvedimentoSostitutoItem.setShowIfCondition(new FormItemIfFunction() {

			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				if(isRequiredProvvedimentoSostitutoItem()) {
					provvedimentoSostitutoItem.setAttribute("obbligatorio", true);
					provvedimentoSostitutoItem.setTitle(FrontendUtil.getRequiredFormItemTitle(getTitleProvvedimentoSostitutoDirRespRegTecnicaItem()));
				} else {					
					provvedimentoSostitutoItem.setAttribute("obbligatorio", false);
					provvedimentoSostitutoItem.setTitle(getTitleProvvedimentoSostitutoDirRespRegTecnicaItem());					
				}
				return showProvvedimentoSostitutoRespRegTecnicaItem();
			}
			
		});
		provvedimentoSostitutoItem.setValidators(new RequiredIfValidator(new RequiredIfFunction() {

			@Override
			public boolean execute(FormItem formItem, Object value) {
				return provvedimentoSostitutoItem.getAttributeAsBoolean("obbligatorio") != null && provvedimentoSostitutoItem.getAttributeAsBoolean("obbligatorio");				
			}
		}));
		customItems.add(provvedimentoSostitutoItem);
		
//		fine provvedimento sostituto item
		return customItems;
	}
	
	public void manageEditCustomItems(Boolean canEdit) {
		if (idSvSostitutoItem != null) {
			idSvSostitutoItem.setCanEdit(isEditableSostitutoItem() ? canEdit : false);
		}
		if (provvedimentoSostitutoItem != null) {
			provvedimentoSostitutoItem.setCanEdit(isEditableProvvedimentoSostitutoItem() ? canEdit : false);
		}
	}
	
	public boolean showSostitutoRespRegTecnicaItem() {
		return false;
	}
	
	public String getTitleSostitutoDirRespRegTecnicaItem() {
		return "Sostituto";
	}
	
	public boolean isEditableSostitutoItem() {
		return true;
	}
	
	public boolean isRequiredSostitutoItem() {
		return false;
	}
	
	public String getTipoLoadComboSostituto() {
		return "SCRIVANIE_ORGANIGRAMMA";
	}
	
	public String getAltriParamLoadComboSostituto() {
		return null;
	}
	
	public boolean showProvvedimentoSostitutoRespRegTecnicaItem() {
		return false;
	}
	
	public String getTitleProvvedimentoSostitutoDirRespRegTecnicaItem() {
		return "Provvedimento di sostituzione";
	}
	
	public boolean isEditableProvvedimentoSostitutoItem() {
		return true;
	}
	
	public boolean isRequiredProvvedimentoSostitutoItem() {
		return false;
	}
	
	public boolean isAltriParamWithDesRuoloOrIdUoSostituto() {
		String altriParamLoadCombo = getAltriParamLoadComboSostituto();
		return altriParamLoadCombo != null && (altriParamLoadCombo.indexOf("DES_RUOLO|*|") != -1 || altriParamLoadCombo.indexOf("ID_UO|*|") != -1);
	}
	
	public boolean isAltriParamWithNriLivelliUoSostituto() {
		String altriParamLoadCombo = getAltriParamLoadComboSostituto();
		return altriParamLoadCombo != null && altriParamLoadCombo.indexOf("NRI_LIVELLI_UO|*|") != -1;
	}
	
	public boolean isCodUoFieldWithFilterSostituto() {
		if(!isAltriParamWithNriLivelliUoSostituto()) {
			return false;		
		} else if (AurigaLayout.getParametroDBAsBoolean("DIM_ORGANIGRAMMA_NONSTD") && !isAltriParamWithDesRuoloOrIdUoSostituto()) {
			return false;			
		}
		return true;
		
	}
	
	public boolean isDescrizioneFieldWithFilterSostituto() {
		return isAltriParamWithStrInDesSostituto();
	}
	
	public boolean isAltriParamWithStrInDesSostituto() {
		String altriParamLoadCombo = getAltriParamLoadComboSostituto();
		return altriParamLoadCombo != null && altriParamLoadCombo.indexOf("STR_IN_DES|*|") != -1;
	}
	
	public String getDescrizioneSostitutoFieldName() {
		return "desDirigenteRespRegTecnicaSostituto";
	}
	
	public String getIdSvSostitutoFieldName() {
		return "dirigenteRespRegTecnicaSostituto";
	}
	
	public String getCodUoSostitutoFieldName() {
		return "codUoDirigenteRespRegTecnicaSostituto";
	}
	
	public String getIdSvSostitutoFromLoadDettFieldName() {
		return "dirigenteRespRegTecnicaFromLoadDettSostituto";
	}
	
	public String getFlgModificabileSostitutoFieldName() {
		return "flgModificabileSostituto";
	}
	
	public String getAltriParamLoadCombo() {
		return null;
	}
	
	public boolean showFlgFirmatario() {
		return false;
	}

}
