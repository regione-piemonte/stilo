/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import com.smartgwt.client.data.DSCallback;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.FieldType;
import com.smartgwt.client.types.Overflow;
import com.smartgwt.client.widgets.Button;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.FormItemIfFunction;
import com.smartgwt.client.widgets.form.fields.FormItem;
import com.smartgwt.client.widgets.form.fields.HeaderItem;
import com.smartgwt.client.widgets.form.fields.HiddenItem;
import com.smartgwt.client.widgets.form.fields.RadioGroupItem;
import com.smartgwt.client.widgets.form.fields.events.ChangedEvent;
import com.smartgwt.client.widgets.form.fields.events.ChangedHandler;
import com.smartgwt.client.widgets.form.validator.RequiredIfFunction;
import com.smartgwt.client.widgets.form.validator.RequiredIfValidator;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.layout.HStack;
import com.smartgwt.client.widgets.layout.VLayout;

import it.eng.utility.ui.module.core.client.callback.ServiceCallback;
import it.eng.utility.ui.module.core.client.datasource.GWTRestDataSource;
import it.eng.utility.ui.module.core.shared.message.MessageBean;
import it.eng.utility.ui.module.core.shared.message.MessageType;
import it.eng.utility.ui.module.layout.client.Layout;
import it.eng.utility.ui.module.layout.client.common.file.PrettyFileUploadItem;
import it.eng.utility.ui.module.layout.client.common.filter.item.AnnoItem;
import it.eng.utility.ui.module.layout.client.common.items.FilteredSelectItem;
import it.eng.utility.ui.module.layout.client.common.items.ImgButtonItem;
import it.eng.utility.ui.module.layout.client.common.items.SelectItem;
import it.eng.utility.ui.module.layout.client.common.items.TextItem;
import it.eng.utility.ui.module.layout.client.common.items.TitleItem;
import it.eng.utility.ui.module.layout.client.portal.ModalWindow;
import it.eng.utility.ui.module.layout.shared.util.FrontendUtil;

public class ModificaCodAttoCMTOPopup extends ModalWindow {
	
	protected ModificaCodAttoCMTOPopup window;
	
	protected DynamicForm form;
	protected HiddenItem idDocPrimarioHiddenItem;
	protected HiddenItem tipoDocumentoHiddenItem;
	protected SelectItem tipoAffidamentoItem; 
	protected FilteredSelectItem materiaTipoAttoItem;
	protected HiddenItem desMateriaTipoAttoItem;
	protected RadioGroupItem flgLLPPItem;
	protected AnnoItem annoProgettoLLPPItem;
	protected TextItem numProgettoLLPPItem;
	protected RadioGroupItem flgBeniServiziItem;
	protected AnnoItem annoProgettoBeniServiziItem;
	protected TextItem numProgettoBeniServiziItem;
	
	public ModificaCodAttoCMTOPopup(Record record, final ServiceCallback<Record> callback){
			
		super("modifica_cod_atto", true);
		
		window = this;
		
		setTitle("Modifica cod. atto/LL.PP./beni e servizi");
		
		setAutoCenter(true);
		setHeight(300);
		setWidth(800);	

		settingsMenu.removeItem(separatorMenuItem);
		settingsMenu.removeItem(autoSearchMenuItem);			
		
		form = new DynamicForm();
		form.setKeepInParentRect(true);
		form.setWidth100();
		form.setHeight100();
		form.setNumCols(8);
		form.setColWidths(120,1,1,1,1,1,"*","*");		
		form.setCellPadding(5);
		form.setWrapItemTitles(false);				
				
		idDocPrimarioHiddenItem = new HiddenItem("idDocPrimario");
		tipoDocumentoHiddenItem = new HiddenItem("tipoDocumento");
		
		GWTRestDataSource tipoAffidamentoDS = new GWTRestDataSource("LoadComboValoriDizionarioDataSource", "key", FieldType.TEXT);
		tipoAffidamentoDS.addParam("altriParamLoadCombo", getAltriParamLoadComboTipoAffidamentoItem());
		 		
		tipoAffidamentoItem = new SelectItem("tipoAffidamento", getTitleTipoAffidamentoItem());
//		tipoAffidamentoItem.setTitleOrientation(TitleOrientation.TOP);		
		tipoAffidamentoItem.setWidth(500);
		tipoAffidamentoItem.setColSpan(18);		
		tipoAffidamentoItem.setStartRow(true);
		tipoAffidamentoItem.setValueField("key");
		tipoAffidamentoItem.setDisplayField("value");
		tipoAffidamentoItem.setOptionDataSource(tipoAffidamentoDS);	
		tipoAffidamentoItem.setClearable(true);		
		tipoAffidamentoItem.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				if(isRequiredTipoAffidamentoItem()) {
					tipoAffidamentoItem.setAttribute("obbligatorio", true);
					tipoAffidamentoItem.setTitle(FrontendUtil.getRequiredFormItemTitle(getTitleTipoAffidamentoItem()));
				} else {
					tipoAffidamentoItem.setAttribute("obbligatorio", false);
					tipoAffidamentoItem.setTitle(getTitleTipoAffidamentoItem());
				}
				return showTipoAffidamentoItem();
			}
		});
		tipoAffidamentoItem.setValidators(new RequiredIfValidator(new RequiredIfFunction() {
			
			@Override
			public boolean execute(FormItem formItem, Object value) {
				return isRequiredTipoAffidamentoItem();
			}
		}));		
		
		GWTRestDataSource materiaTipoAttoDS = new GWTRestDataSource("LoadComboValoriDizionarioDataSource", "key", FieldType.TEXT, true);
		materiaTipoAttoDS.addParam("altriParamLoadCombo", getAltriParamLoadComboMateriaTipoAttoItem());
		 		
		materiaTipoAttoItem = new FilteredSelectItem("materiaTipoAtto", getTitleMateriaTipoAttoItem()) {
			
			@Override
			public void onOptionClick(Record record) {
				super.onOptionClick(record);	
				form.setValue("desMateriaTipoAtto", record.getAttributeAsString("value"));
			}			
			
			@Override
			protected void clearSelect() {
				super.clearSelect();
				form.setValue("materiaTipoAtto", "");	
				form.setValue("desMateriaTipoAtto", "");	
			};		
			
			@Override
			public void setValue(String value) {
				super.setValue(value);
				if (value == null || "".equals(value)) {
					form.setValue("materiaTipoAtto", "");
					form.setValue("desMateriaTipoAtto", "");
				}
			}

			@Override
			protected ListGrid builPickListProperties() {
				ListGrid materiaTipoAttoItemPickListProperties = super.builPickListProperties();	
				materiaTipoAttoItemPickListProperties.setShowHeader(false);
				return materiaTipoAttoItemPickListProperties;
			}
		};
//		materiaTipoAttoItem.setTitleOrientation(TitleOrientation.TOP);
		materiaTipoAttoItem.setWidth(500);
		materiaTipoAttoItem.setColSpan(18);		
		materiaTipoAttoItem.setStartRow(true);		
		materiaTipoAttoItem.setValueField("key");
		materiaTipoAttoItem.setDisplayField("value");
		ListGridField valueField = new ListGridField("value", "Descrizione");
		valueField.setWidth("*");
		valueField.setCanFilter(true);		
		materiaTipoAttoItem.setPickListFields(valueField);
		materiaTipoAttoItem.setOptionDataSource(materiaTipoAttoDS);
		materiaTipoAttoItem.setClearable(true);		
		materiaTipoAttoItem.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return showMateriaTipoAttoItem();
			}
		});
		if(isRequiredMateriaTipoAttoItem()) {
			materiaTipoAttoItem.setAttribute("obbligatorio", true);
		}
		materiaTipoAttoItem.setValidators(new RequiredIfValidator(new RequiredIfFunction() {
			
			@Override
			public boolean execute(FormItem formItem, Object value) {
				return isRequiredMateriaTipoAttoItem();
			}
		}));
		
		desMateriaTipoAttoItem = new HiddenItem("desMateriaTipoAtto");
		
		flgLLPPItem = new RadioGroupItem("flgLLPP", getTitleFlgLLPPItem());
		flgLLPPItem.setStartRow(true);
		flgLLPPItem.setValueMap(NuovaPropostaAtto2CompletaDetail._FLG_SI, NuovaPropostaAtto2CompletaDetail._FLG_NO);		
		flgLLPPItem.setDefaultValue(getDefaultValueFlgLLPPItem());
		flgLLPPItem.setVertical(false);
		flgLLPPItem.setWrap(false);
		flgLLPPItem.setShowDisabled(false);
		if(isRequiredFlgLLPPItem()) {
			flgLLPPItem.setAttribute("obbligatorio", true);
		}
		flgLLPPItem.setValidators(new RequiredIfValidator(new RequiredIfFunction() {
			
			@Override
			public boolean execute(FormItem formItem, Object value) {
				return isRequiredFlgLLPPItem();
			}
		}));
		flgLLPPItem.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return showFlgLLPPItem();
			}
		});			
		flgLLPPItem.addChangedHandler(new ChangedHandler() {
			
			@Override
			public void onChanged(ChangedEvent event) {
				form.markForRedraw();
			}
		});
		
		annoProgettoLLPPItem = new AnnoItem("annoProgettoLLPP", getTitleAnnoProgettoLLPPItem());
		annoProgettoLLPPItem.setColSpan(1);
		if(isRequiredAnnoProgettoLLPPItem()) {
			annoProgettoLLPPItem.setAttribute("obbligatorio", true);
		}
		annoProgettoLLPPItem.setValidators(new RequiredIfValidator(new RequiredIfFunction() {
			
			@Override
			public boolean execute(FormItem formItem, Object value) {
				return isRequiredAnnoProgettoLLPPItem();
			}
		}));
		annoProgettoLLPPItem.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return showAnnoProgettoLLPPItem();
			}
		});
		
		numProgettoLLPPItem = new TextItem("numProgettoLLPP", getTitleNumProgettoLLPPItem());
		numProgettoLLPPItem.setColSpan(1);
		if(isRequiredNumProgettoLLPPItem()) {
			numProgettoLLPPItem.setAttribute("obbligatorio", true);
		}
		numProgettoLLPPItem.setValidators(new RequiredIfValidator(new RequiredIfFunction() {
			
			@Override
			public boolean execute(FormItem formItem, Object value) {
				return isRequiredNumProgettoLLPPItem();
			}
		}));
		numProgettoLLPPItem.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return showNumProgettoLLPPItem();
			}
		});
		
		flgBeniServiziItem = new RadioGroupItem("flgBeniServizi", getTitleFlgBeniServiziItem());
		flgBeniServiziItem.setStartRow(true);
		flgBeniServiziItem.setValueMap(NuovaPropostaAtto2CompletaDetail._FLG_SI, NuovaPropostaAtto2CompletaDetail._FLG_NO);		
		flgBeniServiziItem.setDefaultValue(getDefaultValueFlgBeniServiziItem());
		flgBeniServiziItem.setVertical(false);
		flgBeniServiziItem.setWrap(false);
		flgBeniServiziItem.setShowDisabled(false);
		if(isRequiredFlgBeniServiziItem()) {
			flgBeniServiziItem.setAttribute("obbligatorio", true);
		}
		flgBeniServiziItem.setValidators(new RequiredIfValidator(new RequiredIfFunction() {
			
			@Override
			public boolean execute(FormItem formItem, Object value) {
				return isRequiredFlgBeniServiziItem();
			}
		}));
		flgBeniServiziItem.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return showFlgBeniServiziItem();
			}
		});	
		flgBeniServiziItem.addChangedHandler(new ChangedHandler() {
			
			@Override
			public void onChanged(ChangedEvent event) {
				form.markForRedraw();
			}
		});
		
		annoProgettoBeniServiziItem = new AnnoItem("annoProgettoBeniServizi", getTitleAnnoProgettoBeniServiziItem());
		annoProgettoBeniServiziItem.setColSpan(1);
		if(isRequiredAnnoProgettoBeniServiziItem()) {
			annoProgettoBeniServiziItem.setAttribute("obbligatorio", true);
		}
		annoProgettoBeniServiziItem.setValidators(new RequiredIfValidator(new RequiredIfFunction() {
			
			@Override
			public boolean execute(FormItem formItem, Object value) {
				return isRequiredAnnoProgettoBeniServiziItem();
			}
		}));
		annoProgettoBeniServiziItem.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return showAnnoProgettoBeniServiziItem();
			}
		});
		
		numProgettoBeniServiziItem = new TextItem("numProgettoBeniServizi", getTitleNumProgettoBeniServiziItem());
		numProgettoBeniServiziItem.setColSpan(1);
		if(isRequiredNumProgettoBeniServiziItem()) {
			numProgettoBeniServiziItem.setAttribute("obbligatorio", true);
		}
		numProgettoBeniServiziItem.setValidators(new RequiredIfValidator(new RequiredIfFunction() {
			
			@Override
			public boolean execute(FormItem formItem, Object value) {
				return isRequiredNumProgettoBeniServiziItem();
			}
		}));
		numProgettoBeniServiziItem.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return showNumProgettoBeniServiziItem();
			}
		});
		
		form.setFields(
			idDocPrimarioHiddenItem,
			tipoDocumentoHiddenItem,
			tipoAffidamentoItem,
			materiaTipoAttoItem,
			desMateriaTipoAttoItem,
			flgLLPPItem, 
			annoProgettoLLPPItem,
			numProgettoLLPPItem,			
			flgBeniServiziItem,
			annoProgettoBeniServiziItem,
			numProgettoBeniServiziItem
		);
		
		Button confermaButton = new Button("Ok");   
		confermaButton.setIcon("ok.png");
		confermaButton.setIconSize(16); 
		confermaButton.setAutoFit(false);
		confermaButton.addClickHandler(new com.smartgwt.client.widgets.events.ClickHandler() {		
			
			@Override
			public void onClick(com.smartgwt.client.widgets.events.ClickEvent event) {
				if(form.validate()) {					
					final Record formRecord = new Record(form.getValues());
					final GWTRestDataSource lNuovaPropostaAtto2CompletaDataSource = new GWTRestDataSource("NuovaPropostaAtto2CompletaDataSource");
					lNuovaPropostaAtto2CompletaDataSource.executecustom("modificaCodAttoCMTO", formRecord, new DSCallback() {
						
						@Override
						public void execute(DSResponse response, Object rawData, DSRequest request) {
							if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
								Record result = response.getData()[0];
								Layout.addMessage(new MessageBean("Modifica cod. atto/LL.PP./beni e servizi avvenuta con successo", "", MessageType.INFO));
								if(callback != null) {
									callback.execute(result);
								}
								window.markForDestroy();
							}
						}
					});													
				}
			}
		});		
		
		Button annullaButton = new Button("Annulla");   
		annullaButton.setIcon("annulla.png");
		annullaButton.setIconSize(16); 
		annullaButton.setAutoFit(false);
		annullaButton.addClickHandler(new com.smartgwt.client.widgets.events.ClickHandler() {			
			@Override
			public void onClick(com.smartgwt.client.widgets.events.ClickEvent event) {				
				window.markForDestroy();	
			}
		});
		
		HStack buttons = new HStack(5);
		buttons.setWidth100();
		buttons.setHeight(30);
		buttons.setAlign(Alignment.CENTER);
		buttons.setPadding(5);
        buttons.addMember(confermaButton);
		buttons.addMember(annullaButton);	
		
		setAlign(Alignment.CENTER);
		setTop(50);
		
		VLayout layout = new VLayout();
		layout.setHeight100();
		layout.setWidth100();
		layout.setOverflow(Overflow.AUTO);
		
		// Creo il VLAYOUT e gli aggiungo il TABSET 
		VLayout portletLayout = new VLayout();  
		portletLayout.setHeight100();
		portletLayout.setWidth100();
		portletLayout.setOverflow(Overflow.VISIBLE);
		
		layout.addMember(form);
				
		portletLayout.addMember(layout);		
		portletLayout.addMember(buttons);
						
		setBody(portletLayout);
				
		setIcon("blank.png");
		
		if(record != null) {
			form.editRecord(record);
		} else {
			form.editNewRecord();
		}
		
		setCanEdit(true);
		
		show();
	}
	
	public void setCanEdit(boolean canEdit) {
		if (form != null) {
			form.setEditing(canEdit);
			for (FormItem item : form.getFields()) {
				if (!(item instanceof HeaderItem) && !(item instanceof ImgButtonItem) && !(item instanceof TitleItem)) {
//					if (item instanceof DateItem || item instanceof DateTimeItem) {
//						TextItem textItem = new TextItem();
//						textItem.setTextBoxStyle(canEdit ? it.eng.utility.Styles.textItem : it.eng.utility.Styles.readonlyItem);
//						if (item instanceof DateItem) {
//							((DateItem) item).setTextFieldProperties(textItem);
//						} else if (item instanceof DateTimeItem) {
//							((DateTimeItem) item).setTextFieldProperties(textItem);
//						}
//					} else {
//						item.setTextBoxStyle(canEdit ? it.eng.utility.Styles.textItem : it.eng.utility.Styles.readonlyItem);
//					}
					item.setCanEdit(canEdit);
					item.redraw();
				}
				if (item instanceof ImgButtonItem || item instanceof PrettyFileUploadItem) {
					item.setCanEdit(canEdit);
					item.redraw();
				}
			}
		}
	}
	
	public boolean showTipoAffidamentoItem() {
		return true;
	}
	
	public String getTitleTipoAffidamentoItem() {
		return "Tipo di affidamento";
	}
	
	public boolean isRequiredTipoAffidamentoItem() {
		return false;
	}
	
	public String getAltriParamLoadComboTipoAffidamentoItem() {
		return "DICTIONARY_ENTRY|*|TIPO_AFFIDAMENTO";
	}
	
	public boolean showMateriaTipoAttoItem() {
		return true;
	}
	
	public String getTitleMateriaTipoAttoItem() {
		return "Cod. atto";
	}
	
	public boolean isRequiredMateriaTipoAttoItem() {
		return true;
	}
	
	public String getAltriParamLoadComboMateriaTipoAttoItem() {
		return "DICTIONARY_ENTRY|*|MATERIA_NATURA_ATTO|*|STR_IN_DES|*|$STR_IN_DES$";
	}
	
	public boolean showFlgLLPPItem() {
		return true;
	}
	
	public String getTitleFlgLLPPItem() {
		return "LL.PP.";
	}
	
	public boolean isRequiredFlgLLPPItem() {
		return true;
	}
	
	public String getDefaultValueFlgLLPPItem() {
		return NuovaPropostaAtto2CompletaDetail._FLG_NO;
	}
	
	public boolean showAnnoProgettoLLPPItem() {
		return isLLPP();
	}
	
	public String getTitleAnnoProgettoLLPPItem() {
		return "Progetto - Anno";
	}
	
	public boolean isRequiredAnnoProgettoLLPPItem() {
		return showAnnoProgettoLLPPItem();
	}
	
	public boolean showNumProgettoLLPPItem() {
		return isLLPP();
	}
	
	public String getTitleNumProgettoLLPPItem() {
		return "N.";
	}
	
	public boolean isRequiredNumProgettoLLPPItem() {
		return showNumProgettoLLPPItem();
	}
	
	public boolean showFlgBeniServiziItem() {
		return true;
	}
	
	public String getTitleFlgBeniServiziItem() {
		return "Beni e Servizi";
	}
	
	public boolean isRequiredFlgBeniServiziItem() {
		return true;
	}
	
	public String getDefaultValueFlgBeniServiziItem() {
		return NuovaPropostaAtto2CompletaDetail._FLG_NO;
	}
	
	public boolean showAnnoProgettoBeniServiziItem() {
		return isBeniServizi();
	}
	
	public String getTitleAnnoProgettoBeniServiziItem() {
		return "Progetto - Anno";
	}
	
	public boolean isRequiredAnnoProgettoBeniServiziItem() {
		return showAnnoProgettoBeniServiziItem();
	}
	
	public boolean showNumProgettoBeniServiziItem() {
		return isBeniServizi();
	}
	
	public String getTitleNumProgettoBeniServiziItem() {
		return "N.";
	}
	
	public boolean isRequiredNumProgettoBeniServiziItem() {
		return showNumProgettoBeniServiziItem();
	}
	
	public boolean isLLPP() {
		return showFlgLLPPItem() && form.getValueAsString("flgLLPP") != null && NuovaPropostaAtto2CompletaDetail._FLG_SI.equals(form.getValueAsString("flgLLPP"));	
	}

	public boolean isBeniServizi() {
		return showFlgBeniServiziItem() && form.getValueAsString("flgBeniServizi") != null && NuovaPropostaAtto2CompletaDetail._FLG_SI.equals(form.getValueAsString("flgBeniServizi"));
	}
	
}

