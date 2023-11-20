/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.Map;

import com.google.gwt.i18n.client.NumberFormat;
import com.google.gwt.regexp.shared.RegExp;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.types.CharacterCasing;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.FormItemIfFunction;
import com.smartgwt.client.widgets.form.fields.FormItem;
import com.smartgwt.client.widgets.form.fields.RadioGroupItem;
import com.smartgwt.client.widgets.form.fields.events.ChangedEvent;
import com.smartgwt.client.widgets.form.fields.events.ChangedHandler;
import com.smartgwt.client.widgets.form.validator.CustomValidator;
import com.smartgwt.client.widgets.form.validator.RegExpValidator;
import com.smartgwt.client.widgets.form.validator.RequiredIfFunction;
import com.smartgwt.client.widgets.form.validator.RequiredIfValidator;

import it.eng.auriga.ui.module.layout.client.NumberFormatUtility;
import it.eng.auriga.ui.module.layout.client.RegExpUtility;
import it.eng.utility.ui.module.layout.client.common.ReplicableCanvas;
import it.eng.utility.ui.module.layout.client.common.ReplicableItem;
import it.eng.utility.ui.module.layout.client.common.items.CheckboxItem;
import it.eng.utility.ui.module.layout.client.common.items.ExtendedNumericItem;
import it.eng.utility.ui.module.layout.client.common.items.TextItem;
import it.eng.utility.ui.module.layout.shared.util.FrontendUtil;

public class DestVantaggioCanvas extends ReplicableCanvas {
	
	private RadioGroupItem tipoItem;
	private RadioGroupItem tipoPersonaItem;
	private TextItem cognomeItem;
	private TextItem nomeItem;
	private TextItem ragioneSocialeItem;
	private TextItem codFiscalePIVAItem;
	private ExtendedNumericItem importoItem;
	private CheckboxItem flgPrivacyItem;
	
	private ReplicableCanvasForm mDynamicForm;

	public DestVantaggioCanvas(ReplicableItem item) {
		super(item);
	}

	@Override
	public void disegna() {
		
		mDynamicForm = new ReplicableCanvasForm();
		mDynamicForm.setWrapItemTitles(false);
		mDynamicForm.setNumCols(14);
		mDynamicForm.setColWidths(1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, "*", "*");
		
		tipoItem = new RadioGroupItem("tipo", ((DestVantaggioItem)getItem()).getTitleTipo());
		tipoItem.setShowTitle(false);
		tipoItem.setStartRow(true);
		tipoItem.setValueMap("mandatario", "mandante");
		tipoItem.setDefaultValue(((DestVantaggioItem)getItem()).getDefaultValueTipo());		
		tipoItem.setVertical(false);
		tipoItem.setWrap(false);
		tipoItem.setShowDisabled(false);
		tipoItem.setRequired(true);				
		tipoItem.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				if(((DestVantaggioItem)getItem()).isRequiredTipo()) {
					tipoItem.setAttribute("obbligatorio", true);
					tipoItem.setTitle(FrontendUtil.getRequiredFormItemTitle(((DestVantaggioItem)getItem()).getTitleTipo()));
				} else {
					tipoItem.setAttribute("obbligatorio", false);
					tipoItem.setTitle(((DestVantaggioItem)getItem()).getTitleTipo());
				}
				return ((DestVantaggioItem)getItem()).showTipo();
			}
		});
		tipoItem.setValidators(new RequiredIfValidator(new RequiredIfFunction() {
			
			@Override
			public boolean execute(FormItem formItem, Object value) {
				return ((DestVantaggioItem)getItem()).isRequiredTipo();
			}
		}));		
		tipoItem.addChangedHandler(new ChangedHandler() {
			
			@Override
			public void onChanged(ChangedEvent event) {
				mDynamicForm.markForRedraw();
			}
		});
			
		tipoPersonaItem = new RadioGroupItem("tipoPersona", ((DestVantaggioItem)getItem()).getTitleTipoPersona());
		Map<String, String> tipoPersonaValueMap = ((DestVantaggioItem)getItem()).getValueMapTipoPersona();
		if(tipoPersonaValueMap != null && tipoPersonaValueMap.size() > 0) {
			tipoPersonaItem.setValueMap(tipoPersonaValueMap);			
		} else {			
			tipoPersonaItem.setValueMap("fisica", "giuridica");
		}	
		if(tipoPersonaValueMap != null && tipoPersonaValueMap.size() == 1) {
			tipoPersonaItem.setDefaultValue(tipoPersonaValueMap.keySet().iterator().next());
		} else {
			tipoPersonaItem.setDefaultValue(((DestVantaggioItem)getItem()).getDefaultValueTipoPersona());
		}
		tipoPersonaItem.setVertical(false);
		tipoPersonaItem.setWrap(false);
		tipoPersonaItem.setShowDisabled(false);
		tipoPersonaItem.setRequired(true);				
		tipoPersonaItem.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				if(((DestVantaggioItem)getItem()).isRequiredTipoPersona()) {
					tipoPersonaItem.setAttribute("obbligatorio", true);
					tipoPersonaItem.setTitle(FrontendUtil.getRequiredFormItemTitle(((DestVantaggioItem)getItem()).getTitleTipoPersona()));
				} else {
					tipoPersonaItem.setAttribute("obbligatorio", false);
					tipoPersonaItem.setTitle(((DestVantaggioItem)getItem()).getTitleTipoPersona());
				}
				Map<String, String> tipoPersonaValueMap = ((DestVantaggioItem)getItem()).getValueMapTipoPersona();
				if(tipoPersonaValueMap != null && tipoPersonaValueMap.size() == 1) {
					return false;
				}	
				return ((DestVantaggioItem)getItem()).showTipoPersona();
			}
		});
		tipoPersonaItem.setValidators(new RequiredIfValidator(new RequiredIfFunction() {
			
			@Override
			public boolean execute(FormItem formItem, Object value) {
				return ((DestVantaggioItem)getItem()).isRequiredTipoPersona();
			}
		}));		
		tipoPersonaItem.addChangedHandler(new ChangedHandler() {
			
			@Override
			public void onChanged(ChangedEvent event) {
				mDynamicForm.markForRedraw();
			}
		});
		
		cognomeItem = new TextItem("cognome", ((DestVantaggioItem)getItem()).getTitleCognome());
		cognomeItem.setWidth(150);
		cognomeItem.setColSpan(1);
		cognomeItem.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				if(((DestVantaggioItem)getItem()).isRequiredCognome()) {
					cognomeItem.setAttribute("obbligatorio", true);
					cognomeItem.setTitle(FrontendUtil.getRequiredFormItemTitle(((DestVantaggioItem)getItem()).getTitleCognome()));
				} else {					
					cognomeItem.setAttribute("obbligatorio", false);
					cognomeItem.setTitle(((DestVantaggioItem)getItem()).getTitleCognome());					
				}
				return ((DestVantaggioItem)getItem()).showCognome() && isTipoPersonaFisica();
			}
		});
		cognomeItem.setValidators(new RequiredIfValidator(new RequiredIfFunction() {
			
			@Override
			public boolean execute(FormItem formItem, Object value) {
				return ((DestVantaggioItem)getItem()).isRequiredCognome() && isTipoPersonaFisica();
			}
		}));
		
		nomeItem = new TextItem("nome", ((DestVantaggioItem)getItem()).getTitleNome());
		nomeItem.setWidth(150);
		nomeItem.setColSpan(1);
		nomeItem.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				if(((DestVantaggioItem)getItem()).isRequiredNome()) {
					nomeItem.setAttribute("obbligatorio", true);
					nomeItem.setTitle(FrontendUtil.getRequiredFormItemTitle(((DestVantaggioItem)getItem()).getTitleNome()));
				} else {					
					nomeItem.setAttribute("obbligatorio", false);
					nomeItem.setTitle(((DestVantaggioItem)getItem()).getTitleNome());										
				}			
				return ((DestVantaggioItem)getItem()).showNome() && isTipoPersonaFisica();
			}
		});
		nomeItem.setValidators(new RequiredIfValidator(new RequiredIfFunction() {
			
			@Override
			public boolean execute(FormItem formItem, Object value) {
				return ((DestVantaggioItem)getItem()).isRequiredNome() && isTipoPersonaFisica();
			}
		}));
		
		ragioneSocialeItem = new TextItem("ragioneSociale", ((DestVantaggioItem)getItem()).getTitleRagioneSociale());
		ragioneSocialeItem.setWidth(250);
		ragioneSocialeItem.setColSpan(2);
		ragioneSocialeItem.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				if(((DestVantaggioItem)getItem()).isRequiredRagioneSociale()) {
					ragioneSocialeItem.setAttribute("obbligatorio", true);
					ragioneSocialeItem.setTitle(FrontendUtil.getRequiredFormItemTitle(((DestVantaggioItem)getItem()).getTitleRagioneSociale()));
				} else {
					ragioneSocialeItem.setAttribute("obbligatorio", false);
					ragioneSocialeItem.setTitle(((DestVantaggioItem)getItem()).getTitleRagioneSociale());					
				}
				return ((DestVantaggioItem)getItem()).showRagioneSociale() && isTipoPersonaGiuridica();
			}
		});
		ragioneSocialeItem.setValidators(new RequiredIfValidator(new RequiredIfFunction() {
			
			@Override
			public boolean execute(FormItem formItem, Object value) {
				return ((DestVantaggioItem)getItem()).isRequiredRagioneSociale() && isTipoPersonaGiuridica();
			}
		}));
		
		codFiscalePIVAItem = new TextItem("codFiscalePIVA", ((DestVantaggioItem)getItem()).getTitleCodFiscalePIVA());
		codFiscalePIVAItem.setWidth(150);
		codFiscalePIVAItem.setColSpan(1);
		codFiscalePIVAItem.setCharacterCasing(CharacterCasing.UPPER);
		codFiscalePIVAItem.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				if (isTipoPersonaFisica()) {
					codFiscalePIVAItem.setLength(16);
					if(((DestVantaggioItem)getItem()).isRequiredCodFiscalePIVA()) {
						codFiscalePIVAItem.setAttribute("obbligatorio", true);
						codFiscalePIVAItem.setTitle(FrontendUtil.getRequiredFormItemTitle(((DestVantaggioItem)getItem()).getTitleCodFiscale()));
					} else {
						codFiscalePIVAItem.setAttribute("obbligatorio", false);
						codFiscalePIVAItem.setTitle(((DestVantaggioItem)getItem()).getTitleCodFiscale());					
					}
				} else {
					codFiscalePIVAItem.setLength(28);
					if(((DestVantaggioItem)getItem()).isRequiredCodFiscalePIVA()) {
						codFiscalePIVAItem.setAttribute("obbligatorio", true);
						codFiscalePIVAItem.setTitle(FrontendUtil.getRequiredFormItemTitle(((DestVantaggioItem)getItem()).getTitleCodFiscalePIVA()));
					} else {
						codFiscalePIVAItem.setAttribute("obbligatorio", false);
						codFiscalePIVAItem.setTitle(((DestVantaggioItem)getItem()).getTitleCodFiscalePIVA());					
					}
				}				
				return ((DestVantaggioItem)getItem()).showCodFiscalePIVA() && isTipoPersonaValorizzato();
			}
		});
		codFiscalePIVAItem.setValidators(new RequiredIfValidator(new RequiredIfFunction() {
			
			@Override
			public boolean execute(FormItem formItem, Object value) {
				return ((DestVantaggioItem)getItem()).isRequiredCodFiscalePIVA() && isTipoPersonaValorizzato();
			}
		}), new CustomValidator() {

			@Override
			protected boolean condition(Object value) {
				if(value == null || "".equals(value)) {
					return true;
				}
				// quando scelgo persona giuridica se è un professionista nel CF/P. IVA devo poter mettere CF di persona
				if (isTipoPersonaFisica()) {
					RegExp regExp = RegExp.compile(RegExpUtility.codiceFiscaleRegExp());
					return regExp.test((String) value);	
				} else {
					RegExp regExp = RegExp.compile(RegExpUtility.codiceFiscalePIVARegExp());
					return regExp.test((String) value);
				}
			}
		});	
		
		RegExpValidator importoPrecisionValidator = new RegExpValidator();
		importoPrecisionValidator.setExpression("^([0-9]{1,3}((\\.)?[0-9]{3})*(,[0-9]{1,2})?)$");
		importoPrecisionValidator.setErrorMessage("Valore non valido o superato il limite di 2 cifre decimali");
				
		CustomValidator importoMaggioreOUgualeDiZeroValidator = new CustomValidator() {
			
			@Override
			protected boolean condition(Object value) {
				// se l'importo è vuoto, ma non è obbligatorio, la validazione deve andare a buon fine
				if(value == null || "".equals(value)) {
					return true;
				}
				String pattern = "#,##0.00";
				double importo = 0;
				if(value != null && !"".equals(value)) {
					importo = new Double(NumberFormat.getFormat(pattern).parse((String) value)).doubleValue();			
				}
				return importo >= 0;
			}
		};
		importoMaggioreOUgualeDiZeroValidator.setErrorMessage("Valore non valido: l'importo deve essere maggiore o uguale di zero");
		
		CustomValidator importoMaggioreDiZeroValidator = new CustomValidator() {
			
			@Override
			protected boolean condition(Object value) {
				// se l'importo è vuoto, ma non è obbligatorio, la validazione deve andare a buon fine
				if(value == null || "".equals(value)) {
					return true;
				}
				String pattern = "#,##0.00";
				double importo = 0;
				if(value != null && !"".equals(value)) {
					importo = new Double(NumberFormat.getFormat(pattern).parse((String) value)).doubleValue();			
				}
				return importo > 0;
			}
		};
		importoMaggioreDiZeroValidator.setErrorMessage("Valore non valido: l'importo deve essere maggiore di zero");
		
		importoItem = new ExtendedNumericItem("importo", ((DestVantaggioItem)getItem()).getTitleImporto()); 
		importoItem.setKeyPressFilter("[0-9.,]");
		importoItem.setColSpan(1);
		importoItem.setWidth(150);
		importoItem.addChangedBlurHandler(new ChangedHandler() {
			
			@Override
			public void onChanged(ChangedEvent event) {				
				if(((DestVantaggioItem)getItem()).isBeneficiariTrasparenza()) {
					String valueStr = (String) event.getValue();
					if(valueStr != null && !"".equals(valueStr)) {
						if(importoItem.validate()) {
							importoItem.setValue(NumberFormatUtility.getFormattedValue(valueStr));
						}
					}	
				} else {
					importoItem.setValue(NumberFormatUtility.getFormattedValue((String) event.getValue()));
				}
			}
		});		
		importoItem.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				if(((DestVantaggioItem)getItem()).isRequiredImporto()) {
					importoItem.setAttribute("obbligatorio", true);
					importoItem.setTitle(FrontendUtil.getRequiredFormItemTitle(((DestVantaggioItem)getItem()).getTitleImporto()));
				} else {
					importoItem.setAttribute("obbligatorio", false);
					importoItem.setTitle(((DestVantaggioItem)getItem()).getTitleImporto());					
				}
				if(((DestVantaggioItem)getItem()).isBeneficiariTrasparenza()) {
					String valueStr = importoItem.getValueAsString();
					if(valueStr != null && !"".equals(valueStr)) {
						if(importoItem.validate()) {
							importoItem.setValue(NumberFormatUtility.getFormattedValue(valueStr));
						}
					}
				} else {
					importoItem.setValue(NumberFormatUtility.getFormattedValue(importoItem.getValueAsString()));
				}
				return ((DestVantaggioItem)getItem()).showImporto() && isTipoPersonaValorizzato();
			}
		});
		importoItem.setValidators(new RequiredIfValidator(new RequiredIfFunction() {
			
			@Override
			public boolean execute(FormItem formItem, Object value) {
				return ((DestVantaggioItem)getItem()).isRequiredImporto() && isTipoPersonaValorizzato();
			}
		}), importoPrecisionValidator, ((DestVantaggioItem)getItem()).isBeneficiariTrasparenza() ? importoMaggioreOUgualeDiZeroValidator : importoMaggioreDiZeroValidator);
				
		flgPrivacyItem = new CheckboxItem("flgPrivacy", ((DestVantaggioItem)getItem()).getTitleFlgPrivacy());
		flgPrivacyItem.setDefaultValue(((DestVantaggioItem)getItem()).getDefaultValueAsBooleanFlgPrivacy());
		flgPrivacyItem.setColSpan(1);
		flgPrivacyItem.setWidth("*");	
		flgPrivacyItem.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {		
				if(((DestVantaggioItem)getItem()).showFlgPrivacy()) {
					return true;
				} else {
					flgPrivacyItem.setValue(false);
					return false;
				}
			}
		});

		mDynamicForm.setFields(tipoItem, tipoPersonaItem, cognomeItem, nomeItem, ragioneSocialeItem, codFiscalePIVAItem, importoItem, flgPrivacyItem);	
		
		addChild(mDynamicForm);
		
	}
	
	protected boolean isTipoMandatario() {
		boolean isTipoMandatario = tipoItem.getValueAsString() != null && "mandatario".equalsIgnoreCase(tipoItem.getValueAsString());
		return isTipoMandatario;
	}
	
	protected boolean isTipoMandante() {
		boolean isTipoMandante = tipoItem.getValueAsString() != null && "mandante".equalsIgnoreCase(tipoItem.getValueAsString());
		return isTipoMandante;
	}
	
	protected boolean isTipoPersonaFisica() {
		boolean isTipoPersonaFisica = tipoPersonaItem.getValueAsString() != null && "fisica".equalsIgnoreCase(tipoPersonaItem.getValueAsString());
		return isTipoPersonaFisica;
	}

	protected boolean isTipoPersonaGiuridica() {
		boolean isTipoPersonaGiuridica = tipoPersonaItem.getValueAsString() != null && "giuridica".equalsIgnoreCase(tipoPersonaItem.getValueAsString());
		return isTipoPersonaGiuridica;
	}
	
	protected boolean isTipoPersonaValorizzato() {
		boolean isTipoPersonaValorizzato = tipoPersonaItem.getValueAsString() != null && !"".equals(tipoPersonaItem.getValueAsString());
		return isTipoPersonaValorizzato;
	}
	
	@Override
	public ReplicableCanvasForm[] getForm() {
		return new ReplicableCanvasForm[]{mDynamicForm};
	}
	
	@Override
	public void editRecord(Record record) {
		super.editRecord(record);				
	}
	
	@Override
	public void setCanEdit(Boolean canEdit) {
		super.setCanEdit(canEdit);
		tipoItem.setCanEdit(((DestVantaggioItem)getItem()).isEditableTipo() ? canEdit : false);
		tipoPersonaItem.setCanEdit(((DestVantaggioItem)getItem()).isEditableTipoPersona() ? canEdit : false);
		cognomeItem.setCanEdit(((DestVantaggioItem)getItem()).isEditableCognome() ? canEdit : false);
		nomeItem.setCanEdit(((DestVantaggioItem)getItem()).isEditableNome() ? canEdit : false);
		ragioneSocialeItem.setCanEdit(((DestVantaggioItem)getItem()).isEditableRagioneSociale() ? canEdit : false);
		codFiscalePIVAItem.setCanEdit(((DestVantaggioItem)getItem()).isEditableCodFiscalePIVA() ? canEdit : false);
		importoItem.setCanEdit(((DestVantaggioItem)getItem()).isEditableImporto() ? canEdit : false);
		flgPrivacyItem.setCanEdit(((DestVantaggioItem)getItem()).isEditableFlgPrivacy() ? canEdit : false);		
	}
	
}
