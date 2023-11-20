/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.LinkedHashMap;
import java.util.Map;

import com.smartgwt.client.data.DSCallback;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.types.FieldType;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.FormItemIfFunction;
import com.smartgwt.client.widgets.form.fields.FormItem;
import com.smartgwt.client.widgets.form.fields.HiddenItem;
import com.smartgwt.client.widgets.form.fields.RadioGroupItem;
import com.smartgwt.client.widgets.form.fields.events.ChangedEvent;
import com.smartgwt.client.widgets.form.fields.events.ChangedHandler;
import com.smartgwt.client.widgets.form.fields.events.IconClickEvent;
import com.smartgwt.client.widgets.form.fields.events.IconClickHandler;
import com.smartgwt.client.widgets.form.validator.CustomValidator;
import com.smartgwt.client.widgets.form.validator.RequiredIfFunction;
import com.smartgwt.client.widgets.form.validator.RequiredIfValidator;

import it.eng.auriga.ui.module.layout.client.archivio.LookupArchivioPopup;
import it.eng.auriga.ui.module.layout.client.postaElettronica.DettaglioRegProtAssociatoWindow;
import it.eng.utility.ui.module.core.client.callback.ServiceCallback;
import it.eng.utility.ui.module.core.client.datasource.GWTRestDataSource;
import it.eng.utility.ui.module.layout.client.StringSplitterClient;
import it.eng.utility.ui.module.layout.client.common.ReplicableCanvas;
import it.eng.utility.ui.module.layout.client.common.ReplicableItem;
import it.eng.utility.ui.module.layout.client.common.filter.item.AnnoItem;
import it.eng.utility.ui.module.layout.client.common.items.ExtendedNumericItem;
import it.eng.utility.ui.module.layout.client.common.items.ExtendedTextItem;
import it.eng.utility.ui.module.layout.client.common.items.ImgButtonItem;
import it.eng.utility.ui.module.layout.client.common.items.SelectItem;

public class AttiRiferimentoCanvas extends ReplicableCanvas {
	
	private RadioGroupItem flgPresenteASistemaItem;
	private HiddenItem idUdHiddenItem;
	protected SelectItem categoriaRegItem;
	protected SelectItem tipoAttoRifItem;
	private HiddenItem nomeTipoAttoRifHiddenItem;
	protected ExtendedTextItem siglaItem;
	protected ExtendedNumericItem numeroItem;
	protected AnnoItem annoItem;
	protected ImgButtonItem lookupArchivioButton;
	
	private ReplicableCanvasForm mDynamicForm;

	public AttiRiferimentoCanvas(ReplicableItem item) {
		super(item);
	}

	@Override
	public void disegna() {
		
		mDynamicForm = new ReplicableCanvasForm();
		mDynamicForm.setWrapItemTitles(false);
		mDynamicForm.setNumCols(20);
		mDynamicForm.setColWidths(1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, "*", "*");
		
		CustomValidator attoRiferimentoNonASistemaRequiredValidator = new CustomValidator() {
			
			@Override
			protected boolean condition(Object value) {
				if(!isNonPresenteASistema()) {
					return true;
				}
				if(((AttiRiferimentoItem)getItem()).isRequiredAttoRiferimento() || ((AttiRiferimentoItem)getItem()).isFromDeterminaAggiudicaProceduraGara() || ((AttiRiferimentoItem)getItem()).isFromDeterminaRimodulazioneSpesaGaraAggiudicata()) {					
					String numero = numeroItem.getValueAsString() != null ? numeroItem.getValueAsString() : "";
					return !"".equals(numero);
				}
				return true;
			}
		};
		attoRiferimentoNonASistemaRequiredValidator.setErrorMessage("Campo obbligatorio");
		
		CustomValidator attoRiferimentoASistemaRequiredValidator = new CustomValidator() {
			
			@Override
			protected boolean condition(Object value) {
				if(isNonPresenteASistema()) {
					return true;
				}
				if(((AttiRiferimentoItem)getItem()).isRequiredAttoRiferimento() || ((AttiRiferimentoItem)getItem()).isFromDeterminaAggiudicaProceduraGara() || ((AttiRiferimentoItem)getItem()).isFromDeterminaRimodulazioneSpesaGaraAggiudicata()) {					
					String categoriaReg = categoriaRegItem.getValueAsString() != null ? categoriaRegItem.getValueAsString() : "";					
					String sigla = siglaItem.getValueAsString() != null ? siglaItem.getValueAsString() : "";
					String numero = numeroItem.getValueAsString() != null ? numeroItem.getValueAsString() : "";
					String anno = annoItem.getValueAsString() != null ? annoItem.getValueAsString() : "";		
					if(((AttiRiferimentoItem)getItem()).showTipoAttoRif() && ((AttiRiferimentoItem)getItem()).isRequiredTipoAttoRif()) {
						String tipoAttoRif = tipoAttoRifItem.getValueAsString() != null ? tipoAttoRifItem.getValueAsString() : "";					
						return ("PG".equals(categoriaReg) || !"".equals(sigla)) && !"".equals(tipoAttoRif) && !"".equals(numero) && !"".equals(anno);
					} else {
						return ("PG".equals(categoriaReg) || !"".equals(sigla)) && !"".equals(numero) && !"".equals(anno);
					}
				}
				return true;
			}
		};
		attoRiferimentoASistemaRequiredValidator.setErrorMessage("Estremi di registrazione obbligatori");
		
		CustomValidator attoRiferimentoASistemaEstremiProtCompletiValidator = new CustomValidator() {
			
			@Override
			protected boolean condition(Object value) {
				if(isNonPresenteASistema()) {
					return true;
				}
				String categoriaReg = categoriaRegItem.getValueAsString() != null ? categoriaRegItem.getValueAsString() : "";
				if("PG".equals(categoriaReg)) {
					String numero = numeroItem.getValueAsString() != null ? numeroItem.getValueAsString() : "";
					String anno = annoItem.getValueAsString() != null ? annoItem.getValueAsString() : "";									
					if(((AttiRiferimentoItem)getItem()).showTipoAttoRif() && ((AttiRiferimentoItem)getItem()).isRequiredTipoAttoRif()) {
						String tipoAttoRif = tipoAttoRifItem.getValueAsString() != null ? tipoAttoRifItem.getValueAsString() : "";					
						return (!"".equals(numero) && !"".equals(anno) && !"".equals(tipoAttoRif)) || ("".equals(numero) && "".equals(anno) && "".equals(tipoAttoRif));
					} else {
						return (!"".equals(numero) && !"".equals(anno)) || ("".equals(numero) && "".equals(anno));
					}
				}
				return true;
			}
		};
		if(((AttiRiferimentoItem)getItem()).showTipoAttoRif() && ((AttiRiferimentoItem)getItem()).isRequiredTipoAttoRif()) {
			attoRiferimentoASistemaEstremiProtCompletiValidator.setErrorMessage("Estremi di protocollo incompleti: inserire tipo, numero e anno");
		} else {
			attoRiferimentoASistemaEstremiProtCompletiValidator.setErrorMessage("Estremi di protocollo incompleti: inserire numero e anno");
		}
		
		CustomValidator attoRiferimentoASistemaEstremiRegRepertorioCompletiValidator = new CustomValidator() {
			
			@Override
			protected boolean condition(Object value) {
				if(isNonPresenteASistema()) {
					return true;
				}
				String categoriaReg = categoriaRegItem.getValueAsString() != null ? categoriaRegItem.getValueAsString() : "";
				if(!"PG".equals(categoriaReg) /*&& !((AttiRiferimentoItem)getItem()).showTipoAttoRif()*/) {
					String sigla = siglaItem.getValueAsString() != null ? siglaItem.getValueAsString() : "";
					String numero = numeroItem.getValueAsString() != null ? numeroItem.getValueAsString() : "";
					String anno = annoItem.getValueAsString() != null ? annoItem.getValueAsString() : "";		
					if(((AttiRiferimentoItem)getItem()).showTipoAttoRif() && ((AttiRiferimentoItem)getItem()).isRequiredTipoAttoRif()) {
						String tipoAttoRif = tipoAttoRifItem.getValueAsString() != null ? tipoAttoRifItem.getValueAsString() : "";					
						return (!"".equals(sigla) && !"".equals(numero) && !"".equals(anno) && !"".equals(tipoAttoRif)) || ("".equals(sigla) && "".equals(numero) && "".equals(anno) && "".equals(tipoAttoRif));
					} else {
						return (!"".equals(sigla) && !"".equals(numero) && !"".equals(anno)) || ("".equals(sigla) && "".equals(numero) && "".equals(anno));
					}
				}
				return true;
			}
		};
		if(((AttiRiferimentoItem)getItem()).showTipoAttoRif() && ((AttiRiferimentoItem)getItem()).isRequiredTipoAttoRif()) {
			attoRiferimentoASistemaEstremiRegRepertorioCompletiValidator.setErrorMessage("Estremi di registrazione repertorio incompleti: inserire tipo, sigla, numero e anno");
		} else {
			attoRiferimentoASistemaEstremiRegRepertorioCompletiValidator.setErrorMessage("Estremi di registrazione repertorio incompleti: inserire sigla, numero e anno");
		}		
		
		CustomValidator attoRiferimentoASistemaEsistenteValidator = new CustomValidator() {

			@Override
			protected boolean condition(Object value) {
				if(isNonPresenteASistema()) {
					return true;
				}
				String categoriaReg = categoriaRegItem.getValueAsString() != null ? categoriaRegItem.getValueAsString() : "";
				String sigla = siglaItem.getValueAsString() != null ? siglaItem.getValueAsString() : "";
				String numero = numeroItem.getValueAsString() != null ? numeroItem.getValueAsString() : "";
				String anno = annoItem.getValueAsString() != null ? annoItem.getValueAsString() : "";					
				if(("PG".equals(categoriaReg) || !"".equals(sigla)) && !"".equals(numero) && !"".equals(anno)) {
					String idUd = idUdHiddenItem.getValue() != null ? String.valueOf(idUdHiddenItem.getValue()) : null;
					return idUd != null && !"".equals(idUd);
				}
				return true;
			}
		};
		attoRiferimentoASistemaEsistenteValidator.setErrorMessage("Atto non presente in Auriga");		
		
		CustomValidator attoRiferimentoASistemaDeliberaUrgenzaValidator = new CustomValidator() {

			@Override
			protected boolean condition(Object value) {
				if(isNonPresenteASistema()) {
					return true;
				}
				if(((AttiRiferimentoItem)getItem()).isFromRatificaDeliberaUrgenza()) {	
					//TODO devo verificare che sia una delibera di urgenza					
				}
				return true;
			}
		};
		attoRiferimentoASistemaDeliberaUrgenzaValidator.setErrorMessage("Atto non valido: deve essere una delibera di urgenza");		
		
		flgPresenteASistemaItem = new RadioGroupItem("flgPresenteASistema", ((AttiRiferimentoItem)getItem()).getTitleFlgPresentaASistemaItem());
		flgPresenteASistemaItem.setValueMap("SI", "NO");
		flgPresenteASistemaItem.setDefaultValue(((AttiRiferimentoItem)getItem()).getDefaultValueFlgPresentaASistemaItem());
		flgPresenteASistemaItem.setVertical(false);
		flgPresenteASistemaItem.setWrap(false);
		flgPresenteASistemaItem.setShowDisabled(false);
		if(((AttiRiferimentoItem)getItem()).isRequiredFlgPresentaASistemaItem()) {
			flgPresenteASistemaItem.setAttribute("obbligatorio", true);
		}
		flgPresenteASistemaItem.setValidators(new RequiredIfValidator(new RequiredIfFunction() {
			
			@Override
			public boolean execute(FormItem formItem, Object value) {
				return ((AttiRiferimentoItem)getItem()).isRequiredFlgPresentaASistemaItem();
			}
		}));
		flgPresenteASistemaItem.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return ((AttiRiferimentoItem)getItem()).showFlgPresentaASistemaItem();
			}
		});	
		flgPresenteASistemaItem.addChangedHandler(new ChangedHandler() {
			
			@Override
			public void onChanged(ChangedEvent event) {
				event.getForm().markForRedraw();
			}
		});
		
		idUdHiddenItem = new HiddenItem("idUd");
		
		categoriaRegItem = new SelectItem("categoriaReg");
		String categoriaRegTitle = ((AttiRiferimentoItem)getItem()).getTitleCategoriaReg();
		if(categoriaRegTitle != null && !"".equals(categoriaRegTitle)) {
			categoriaRegItem.setTitle(categoriaRegTitle);
		} else {
			categoriaRegItem.setShowTitle(false);				
		}
		categoriaRegItem.setValidators(attoRiferimentoASistemaRequiredValidator, attoRiferimentoASistemaEstremiProtCompletiValidator, attoRiferimentoASistemaEstremiRegRepertorioCompletiValidator, attoRiferimentoASistemaEsistenteValidator, attoRiferimentoASistemaDeliberaUrgenzaValidator);
		Map<String, String> categoriaRegValueMap = ((AttiRiferimentoItem)getItem()).getValueMapCategoriaReg();
		if(categoriaRegValueMap != null && categoriaRegValueMap.keySet().size() > 0) {
			// R = Repertorio
			// PG = Protocollo Generale
			// PP = Protocollo particolare/di settore
			// A = Altro
			categoriaRegItem.setValueMap(categoriaRegValueMap);		
		} else {			
			categoriaRegValueMap = new LinkedHashMap<String, String>();
			categoriaRegValueMap.put("PG", "Prot. Generale");
			categoriaRegValueMap.put("R", "Repertorio");
			categoriaRegItem.setValueMap(categoriaRegValueMap);
		}
		if(categoriaRegValueMap != null && categoriaRegValueMap.keySet().size() == 1) {
			categoriaRegItem.setDefaultValue(categoriaRegValueMap.keySet().iterator().next());			
		} else {
			String categoriaRegDefaultValue = ((AttiRiferimentoItem)getItem()).getDefaultValueCategoriaReg();
			categoriaRegItem.setDefaultValue(categoriaRegDefaultValue != null ? categoriaRegDefaultValue : "R");
		}
		categoriaRegItem.setWidth(150);
		categoriaRegItem.setWrapTitle(false);
		categoriaRegItem.setRowSpan(3);
		categoriaRegItem.setColSpan(1);
		categoriaRegItem.setAttribute("obbligatorio", true);
		categoriaRegItem.setAllowEmptyValue(false);
		categoriaRegItem.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				Map<String, String> categoriaRegValueMap = ((AttiRiferimentoItem)getItem()).getValueMapCategoriaReg();
				if(categoriaRegValueMap != null && categoriaRegValueMap.keySet().size() == 1) {
					return false;
				}
				if(isNonPresenteASistema()) {
					return false;
				}
				return true;
			}
		});
		categoriaRegItem.addChangedHandler(new ChangedHandler() {
			
			@Override
			public void onChanged(ChangedEvent event) {
				event.getForm().markForRedraw();
			}
		});
		
		GWTRestDataSource tipoAttoRifDS = new GWTRestDataSource("LoadComboGenericaDataSource", "key", FieldType.TEXT);		
		tipoAttoRifDS.addParam("tipoCombo", ((AttiRiferimentoItem)getItem()).getTipoLoadComboTipoAttoRif());
		tipoAttoRifDS.addParam("altriParamLoadCombo", ((AttiRiferimentoItem)getItem()).getAltriParamLoadComboTipoAttoRif());
		if(((AttiRiferimentoItem)getItem()).getFlgSoloVldLoadComboTipoAttoRif()) {
			tipoAttoRifDS.addParam("flgSoloVld", "1");
		}
				
		tipoAttoRifItem = new SelectItem("tipoAttoRif", ((AttiRiferimentoItem)getItem()).getTitleTipoAttoRif()) {
			
			@Override
			public void onOptionClick(Record record) {
				super.onOptionClick(record);
				mDynamicForm.setValue("nomeTipoAttoRif", record.getAttributeAsString("value"));				
			}

			@Override
			protected void clearSelect() {
				super.clearSelect();
				mDynamicForm.setValue("tipoAttoRif", "");
				mDynamicForm.setValue("nomeTipoAttoRif", "");				
			}
			
			@Override
			public void setValue(String value) {
				super.setValue(value);
				if (value == null || "".equals(value)) {
					mDynamicForm.setValue("tipoAttoRif", "");
					mDynamicForm.setValue("nomeTipoAttoRif", "");					
				}
			}
		};
		tipoAttoRifItem.setWidth(350);
		tipoAttoRifItem.setWrapTitle(false);
		tipoAttoRifItem.setRowSpan(3);
		tipoAttoRifItem.setColSpan(1);		
		tipoAttoRifItem.setValueField("key");
		tipoAttoRifItem.setDisplayField("value");
		tipoAttoRifItem.setOptionDataSource(tipoAttoRifDS);	
		tipoAttoRifItem.setClearable(true);		
		tipoAttoRifItem.setDefaultValue(((AttiRiferimentoItem)getItem()).getDefaultValueTipoAttoRif());
		tipoAttoRifItem.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return ((AttiRiferimentoItem)getItem()).showTipoAttoRif();
			}
		});
		
		nomeTipoAttoRifHiddenItem = new HiddenItem("nomeTipoAttoRif");
		
		siglaItem = new ExtendedTextItem("sigla", "Sigla");
		siglaItem.setWidth(100);
		siglaItem.setRowSpan(3);
		siglaItem.setColSpan(1);
		siglaItem.addChangedBlurHandler(new ChangedHandler() {

			public void onChanged(final ChangedEvent event) {
				event.getForm().clearErrors(true);
				event.getForm().setValue("idUd", "");
				recuperaIdUdAttoRiferimento(new ServiceCallback<String>() {
					
					@Override
					public void execute(String idUd) {
						if(idUd != null && !"".equals(idUd)) {
							event.getForm().setValue("idUd", idUd);							
						}
						event.getForm().markForRedraw();
					}
					
					@Override
					public void manageError() {
						event.getForm().markForRedraw();
					}
				});
			}
		});
		siglaItem.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return categoriaRegItem.getValueAsString() != null && !"PG".equals(categoriaRegItem.getValueAsString());
			}
		});
		
		numeroItem = new ExtendedNumericItem("numero", "N.", false);
		numeroItem.setValidators(attoRiferimentoNonASistemaRequiredValidator);
		numeroItem.setRowSpan(3);
		numeroItem.setColSpan(1);
		numeroItem.addChangedBlurHandler(new ChangedHandler() {

			public void onChanged(final ChangedEvent event) {
				event.getForm().clearErrors(true);
				event.getForm().setValue("idUd", "");
				recuperaIdUdAttoRiferimento(new ServiceCallback<String>() {
					
					@Override
					public void execute(String idUd) {
						if(idUd != null && !"".equals(idUd)) {
							event.getForm().setValue("idUd", idUd);							
						}
						event.getForm().markForRedraw();
					}
					
					@Override
					public void manageError() {
						event.getForm().markForRedraw();
					}
				});
			}
		});	
		
		annoItem = new AnnoItem("anno", "/");
		annoItem.setRowSpan(3);
		annoItem.setColSpan(1);
		annoItem.addChangedBlurHandler(new ChangedHandler() {

			@Override
			public void onChanged(final ChangedEvent event) {
				event.getForm().clearErrors(true);
				event.getForm().setValue("idUd", "");
				recuperaIdUdAttoRiferimento(new ServiceCallback<String>() {
					
					@Override
					public void execute(String idUd) {
						if(idUd != null && !"".equals(idUd)) {
							event.getForm().setValue("idUd", idUd);							
						}
						event.getForm().markForRedraw();
					}
					
					@Override
					public void manageError() {
						event.getForm().markForRedraw();
					}
				});
			}
		});
		
		ImgButtonItem visualizzaDettButton = new ImgButtonItem("visualizzaDettButton", "buttons/detail.png", "Visualizza dettaglio atto");
		visualizzaDettButton.setAlwaysEnabled(true);
		visualizzaDettButton.setEndRow(false);
		visualizzaDettButton.setColSpan(1);
		visualizzaDettButton.addIconClickHandler(new IconClickHandler() {

			@Override
			public void onIconClick(IconClickEvent event) {	
				String idUd = idUdHiddenItem.getValue() != null ? String.valueOf(idUdHiddenItem.getValue()) : null;				
				String categoriaReg = categoriaRegItem.getValueAsString() != null ? categoriaRegItem.getValueAsString() : "";
				String sigla = siglaItem.getValueAsString() != null ? siglaItem.getValueAsString() : "";
				String numero = numeroItem.getValueAsString() != null ? numeroItem.getValueAsString() : "";
				String anno = annoItem.getValueAsString() != null ? annoItem.getValueAsString() : "";									
				Record lRecord = new Record();
				lRecord.setAttribute("idUd", idUd);
				String title = "";
				if("PG".equals(categoriaReg)) {
					title = "Dettaglio atto Prot. " + numero + "/" + anno;
				} else {
					title = "Dettaglio atto " + sigla + " " + numero + "/" + anno;					
				}
				new DettaglioRegProtAssociatoWindow(lRecord, title);
			}
		});		
		visualizzaDettButton.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				if(isNonPresenteASistema()) {
					return false;
				}
				String idUd = idUdHiddenItem.getValue() != null ? String.valueOf(idUdHiddenItem.getValue()) : null;
				String categoriaReg = categoriaRegItem.getValueAsString() != null ? categoriaRegItem.getValueAsString() : "";
				String sigla = siglaItem.getValueAsString() != null ? siglaItem.getValueAsString() : "";
				String numero = numeroItem.getValueAsString() != null ? numeroItem.getValueAsString() : "";
				String anno = annoItem.getValueAsString() != null ? annoItem.getValueAsString() : "";									
				if (("PG".equals(categoriaReg) || !"".equals(sigla)) && !"".equals(numero) && !"".equals(anno)) {
					return idUd != null && !"".equals(idUd);	
				}
				return false;
			}
		});
		
		lookupArchivioButton = new ImgButtonItem("lookupArchivioButton", "lookup/archivio.png", "Seleziona da archivio");
		lookupArchivioButton.setEndRow(false);
		lookupArchivioButton.setColSpan(1);
		lookupArchivioButton.addIconClickHandler(new IconClickHandler() {

			@Override
			public void onIconClick(IconClickEvent event) {						
				AttoRiferimentoLookupArchivio lookupArchivioPopup = new AttoRiferimentoLookupArchivio(event.getForm().getValuesAsRecord(), "/") {
					
					@Override
					public String getFinalita() {
						String categoriaReg = categoriaRegItem.getValueAsString() != null ? categoriaRegItem.getValueAsString() : "";
						if(!"".equals(categoriaReg)) {
							return "SEL_ATTI_" + categoriaReg;
						} else {
							return "SEL_ATTI";	
						}						
					}
				};
				lookupArchivioPopup.show();
			}
		});	
		lookupArchivioButton.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				if(isNonPresenteASistema()) {
					return false;
				}
				return true;
			}
		});
		
		mDynamicForm.setFields(flgPresenteASistemaItem, idUdHiddenItem, categoriaRegItem, tipoAttoRifItem, nomeTipoAttoRifHiddenItem, siglaItem, numeroItem, annoItem, visualizzaDettButton, lookupArchivioButton);
					
		addChild(mDynamicForm);		
	}
	
	public boolean isNonPresenteASistema() {
		return ((AttiRiferimentoItem)getItem()).showFlgPresentaASistemaItem() && "NO".equalsIgnoreCase(flgPresenteASistemaItem.getValueAsString());
	}
	
	@Override
	public void setCanEdit(Boolean canEdit) {
		super.setCanEdit(canEdit);		
		flgPresenteASistemaItem.setCanEdit(((AttiRiferimentoItem)getItem()).isEditabileFlgPresentaASistemaItem() ? canEdit : false);
	}
	
	public void recuperaIdUdAttoRiferimento(final ServiceCallback<String> callback) {
		if(isNonPresenteASistema()) {
			return;
		}
		String categoriaReg = categoriaRegItem.getValueAsString() != null ? categoriaRegItem.getValueAsString() : "";
		String tipoAttoRif = tipoAttoRifItem.getValueAsString() != null ? tipoAttoRifItem.getValueAsString() : "";
		String sigla = siglaItem.getValueAsString() != null ? siglaItem.getValueAsString() : "";
		String numero = numeroItem.getValueAsString() != null ? numeroItem.getValueAsString() : "";
		String anno = annoItem.getValueAsString() != null ? annoItem.getValueAsString() : "";									
		if (("PG".equals(categoriaReg) || !"".equals(sigla)) && !"".equals(numero) && !"".equals(anno)) {
			Record lRecord = new Record();			
			lRecord.setAttribute("categoriaReg", categoriaReg);
			lRecord.setAttribute("tipoAttoRif", tipoAttoRif);
			lRecord.setAttribute("sigla", sigla);
			lRecord.setAttribute("numero", numero);
			lRecord.setAttribute("anno", anno);			
			final GWTRestDataSource lNuovaPropostaAtto2CompletaDataSource = new GWTRestDataSource("NuovaPropostaAtto2CompletaDataSource");
			lNuovaPropostaAtto2CompletaDataSource.performCustomOperation("recuperaIdUdAttoRiferimento", new Record(mDynamicForm.getValues()), new DSCallback() {							
				@Override
				public void execute(DSResponse response, Object rawData, DSRequest request) {
					if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
						if(callback != null) {
							callback.execute(response.getData()[0].getAttributeAsString("idUd"));
						} 
					} else {
						if(callback != null) {
							callback.execute(null);
						} 
					}
				}
			});
		}		
	}	
	
	private void setFormValuesFromRecordArchivio(Record record) {
		mDynamicForm.clearErrors(true);
		mDynamicForm.setValue("idUd", record.getAttribute("idUdFolder"));
		String segnaturaXOrd = record.getAttribute("segnaturaXOrd");	
		if(segnaturaXOrd != null) {
			StringSplitterClient st = new StringSplitterClient(segnaturaXOrd, "-");						
			if(st.getTokens()[0] != null) {
				if("1".equals(st.getTokens()[0])) {
					mDynamicForm.setValue("categoriaReg", "PG");							
				} else if("2".equals(st.getTokens()[0])) {
					mDynamicForm.setValue("categoriaReg", "PP");						
				} else if("4".equals(st.getTokens()[0])) {
					mDynamicForm.setValue("categoriaReg", "R");						
				} else {
					mDynamicForm.setValue("categoriaReg", "A");						
				} 
				Map<String, String> categoriaRegValueMap = ((AttiRiferimentoItem)getItem()).getValueMapCategoriaReg();
				if(categoriaRegValueMap != null && categoriaRegValueMap.keySet().size() > 0) {
					String categoriaReg = null;
					if("1".equals(st.getTokens()[0])) {
						categoriaReg = "PG";							
					} else if("2".equals(st.getTokens()[0])) {
						categoriaReg = "PP";						
					} else if("4".equals(st.getTokens()[0])) {
						categoriaReg = "R";						
					} else {
						categoriaReg = "A";						
					} 
					if(categoriaRegValueMap.containsKey(categoriaReg)) {
						mDynamicForm.setValue("categoriaReg", categoriaReg);	
					}
				} else {
					if("1".equals(st.getTokens()[0])) {
						mDynamicForm.setValue("categoriaReg", "PG");							
					} else if("4".equals(st.getTokens()[0])) {
						mDynamicForm.setValue("categoriaReg", "R");						
					}
				}
			}
			mDynamicForm.setValue("sigla", st.getTokens()[1] != null ? st.getTokens()[1].trim() : null);
			mDynamicForm.setValue("anno", st.getTokens()[2] != null ? st.getTokens()[2].trim() : null);
			mDynamicForm.setValue("numero", st.getTokens()[3] != null ? st.getTokens()[3].trim() : null);				
		}
		mDynamicForm.setValue("tipoAttoRif", record.getAttribute("idTipo"));
		mDynamicForm.setValue("nomeTipoAttoRif", record.getAttribute("tipo"));		
		mDynamicForm.markForRedraw();
	}	
	
	public class AttoRiferimentoLookupArchivio extends LookupArchivioPopup {

		public AttoRiferimentoLookupArchivio(Record record, String idRootNode) {
			super(record, idRootNode, true, true);
		}
		
		@Override
		public String getWindowTitle() {
			return "Seleziona da archivio";
		}
		
		@Override
		public String getFinalita() {
			return "SEL_ATTI";
		}

		@Override
		public void manageLookupBack(Record record) {
			setFormValuesFromRecordArchivio(record);
		}

		@Override
		public void manageMultiLookupBack(Record record) {

		}

		@Override
		public void manageMultiLookupUndo(Record record) {

		}
	}

	@Override
	public ReplicableCanvasForm[] getForm() {
		return new ReplicableCanvasForm[]{mDynamicForm};
	}
	
	@Override
	public void editRecord(Record record) {
		
		manageLoadSelectInEditRecord(record, tipoAttoRifItem, "tipoAttoRif", new String[]{"nomeTipoAttoRif"}, "key");
		super.editRecord(record);				
	}
		
}
