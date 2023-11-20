/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import com.google.gwt.i18n.client.NumberFormat;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.data.RecordList;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.FormItemIfFunction;
import com.smartgwt.client.widgets.form.FormItemInputTransformer;
import com.smartgwt.client.widgets.form.fields.FormItem;
import com.smartgwt.client.widgets.form.fields.events.ChangedEvent;
import com.smartgwt.client.widgets.form.fields.events.ChangedHandler;
import com.smartgwt.client.widgets.form.validator.CustomValidator;
import com.smartgwt.client.widgets.form.validator.RegExpValidator;
import com.smartgwt.client.widgets.form.validator.RequiredIfFunction;
import com.smartgwt.client.widgets.form.validator.RequiredIfValidator;

import it.eng.auriga.ui.module.layout.client.NumberFormatUtility;
import it.eng.utility.ui.module.layout.client.common.ReplicableCanvas;
import it.eng.utility.ui.module.layout.client.common.ReplicableItem;
import it.eng.utility.ui.module.layout.client.common.filter.item.AnnoItem;
import it.eng.utility.ui.module.layout.client.common.items.ExtendedNumericItem;
import it.eng.utility.ui.module.layout.client.common.items.NumericItem;
import it.eng.utility.ui.module.layout.client.common.items.SelectItem;
import it.eng.utility.ui.module.layout.client.common.items.TextItem;
import it.eng.utility.ui.module.layout.shared.util.FrontendUtil;

public class DatiContabiliADSPCanvas extends ReplicableCanvas {
	
	protected AnnoItem annoEsercizioItem;
	protected TextItem capitoloItem;
	protected NumericItem contoItem;
	protected FormItem codiceCIGItem;
	protected FormItem codiceCUPItem;
	protected ExtendedNumericItem importoItem;	
	protected SelectItem operaItem;
	
	private ReplicableCanvasForm mDynamicForm;

	public DatiContabiliADSPCanvas(ReplicableItem item) {
		super(item);
	}

	@Override
	public void disegna() {
		
		mDynamicForm = new ReplicableCanvasForm();
		mDynamicForm.setWrapItemTitles(false);
		mDynamicForm.setNumCols(20);
		mDynamicForm.setColWidths(1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, "*", "*");
		
		annoEsercizioItem = new AnnoItem("annoEsercizio", ((DatiContabiliADSPItem) getItem()).getTitleEsercizioDatiContabiliADSP());
		annoEsercizioItem.setWidth(60);
		annoEsercizioItem.setColSpan(1);
		annoEsercizioItem.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				if(((DatiContabiliADSPItem)getItem()).isRequiredEsercizioDatiContabiliADSP()) {
					annoEsercizioItem.setAttribute("obbligatorio", true);
					annoEsercizioItem.setTitle(FrontendUtil.getRequiredFormItemTitle(((DatiContabiliADSPItem)getItem()).getTitleEsercizioDatiContabiliADSP()));
				} else {					
					annoEsercizioItem.setAttribute("obbligatorio", false);
					annoEsercizioItem.setTitle(((DatiContabiliADSPItem)getItem()).getTitleEsercizioDatiContabiliADSP());					
				}				
				return ((DatiContabiliADSPItem) getItem()).showEsercizioDatiContabiliADSP();
			}
		});
		annoEsercizioItem.setValidators(new RequiredIfValidator(new RequiredIfFunction() {
			
			@Override
			public boolean execute(FormItem formItem, Object value) {
				return ((DatiContabiliADSPItem) getItem()).isRequiredEsercizioDatiContabiliADSP();
			}
		}));
		
		capitoloItem = new TextItem("capitolo", ((DatiContabiliADSPItem) getItem()).getTitleCapitoloDatiContabiliADSP());
		capitoloItem.setWidth(80);
		capitoloItem.setColSpan(1);
		capitoloItem.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				if(((DatiContabiliADSPItem)getItem()).isRequiredCapitoloDatiContabiliADSP()) {
					capitoloItem.setAttribute("obbligatorio", true);
					capitoloItem.setTitle(FrontendUtil.getRequiredFormItemTitle(((DatiContabiliADSPItem)getItem()).getTitleCapitoloDatiContabiliADSP()));
				} else {					
					capitoloItem.setAttribute("obbligatorio", false);
					capitoloItem.setTitle(((DatiContabiliADSPItem)getItem()).getTitleCapitoloDatiContabiliADSP());					
				}				
				return ((DatiContabiliADSPItem) getItem()).showCapitoloDatiContabiliADSP();
			}
		});
		capitoloItem.setValidators(new RequiredIfValidator(new RequiredIfFunction() {
			
			@Override
			public boolean execute(FormItem formItem, Object value) {
				return ((DatiContabiliADSPItem) getItem()).isRequiredCapitoloDatiContabiliADSP();
			}
		}));
		
		contoItem = new NumericItem("conto", ((DatiContabiliADSPItem) getItem()).getTitleContoDatiContabiliADSP(), false);
		contoItem.setWidth(80);
		contoItem.setColSpan(1);
		contoItem.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				if(((DatiContabiliADSPItem)getItem()).isRequiredContoDatiContabiliADSP()) {
					contoItem.setAttribute("obbligatorio", true);
					contoItem.setTitle(FrontendUtil.getRequiredFormItemTitle(((DatiContabiliADSPItem)getItem()).getTitleContoDatiContabiliADSP()));
				} else {					
					contoItem.setAttribute("obbligatorio", false);
					contoItem.setTitle(((DatiContabiliADSPItem)getItem()).getTitleContoDatiContabiliADSP());					
				}				
				return ((DatiContabiliADSPItem) getItem()).showContoDatiContabiliADSP();
			}
		});
		contoItem.setValidators(new RequiredIfValidator(new RequiredIfFunction() {
			
			@Override
			public boolean execute(FormItem formItem, Object value) {
				return ((DatiContabiliADSPItem) getItem()).isRequiredContoDatiContabiliADSP();
			}
		}));
		
		String[] lCIGValueMap = ((DatiContabiliADSPItem) getItem()).getCIGValueMap();
		
		if(lCIGValueMap != null && lCIGValueMap.length > 0) {				
						
			codiceCIGItem = new SelectItem("codiceCIG", ((DatiContabiliADSPItem) getItem()).getTitleDecretoCIGDatiContabiliADSP());
			codiceCIGItem.setWidth(160);
			codiceCIGItem.setColSpan(1);
			codiceCIGItem.setValueMap(lCIGValueMap);
			if(((DatiContabiliADSPItem)getItem()).isRequiredDecretoCIGDatiContabiliADSP()) {
				if(lCIGValueMap != null && lCIGValueMap.length == 1) {
					((SelectItem) codiceCIGItem).setDefaultValue(lCIGValueMap[0]);
				}		
			} else {
				((SelectItem) codiceCIGItem).setAllowEmptyValue(true);
			}
			codiceCIGItem.setShowIfCondition(new FormItemIfFunction() {
				
				@Override
				public boolean execute(FormItem item, Object value, DynamicForm form) {
					if(((DatiContabiliADSPItem)getItem()).isRequiredDecretoCIGDatiContabiliADSP()) {
						codiceCIGItem.setAttribute("obbligatorio", true);
						codiceCIGItem.setTitle(FrontendUtil.getRequiredFormItemTitle(((DatiContabiliADSPItem)getItem()).getTitleDecretoCIGDatiContabiliADSP()));
					} else {					
						codiceCIGItem.setAttribute("obbligatorio", false);
						codiceCIGItem.setTitle(((DatiContabiliADSPItem)getItem()).getTitleDecretoCIGDatiContabiliADSP());					
					}					
					return ((DatiContabiliADSPItem) getItem()).showDecretoCIGDatiContabiliADSP();
				}
			});
			codiceCIGItem.setValidators(new RequiredIfValidator(new RequiredIfFunction() {
				
				@Override
				public boolean execute(FormItem formItem, Object value) {
					return ((DatiContabiliADSPItem) getItem()).isRequiredDecretoCIGDatiContabiliADSP();
				}
			}));	
			codiceCIGItem.addChangedHandler(new ChangedHandler() {
				
				@Override
				public void onChanged(ChangedEvent event) {
					reloadCUPValueMap((String) event.getValue());
				}
			});
			
		} else {
			
			CustomValidator codiceCIGLengthValidator = new CustomValidator() {

				@Override
				protected boolean condition(Object value) {
					if (value != null && !"".equals((String) value)) {
						String valore = (String) value;
						return valore.length() == 10;
					}
					return true;
				}
			};
			codiceCIGLengthValidator.setErrorMessage("Il codice CIG, se indicato, deve essere di 10 caratteri");	
			
			codiceCIGItem = new TextItem("codiceCIG", ((DatiContabiliADSPItem) getItem()).getTitleDecretoCIGDatiContabiliADSP());
			codiceCIGItem.setWidth(160);
			codiceCIGItem.setColSpan(1);
			((TextItem) codiceCIGItem).setLength(10);
			codiceCIGItem.setInputTransformer(new FormItemInputTransformer() {
				
				@Override
				public Object transformInput(DynamicForm form, FormItem item, Object value, Object oldValue) {
					return value != null ? ((String)value).trim().replaceAll(" ", "") : null;
				}
			});
			codiceCIGItem.setShowIfCondition(new FormItemIfFunction() {
				
				@Override
				public boolean execute(FormItem item, Object value, DynamicForm form) {
					if(((DatiContabiliADSPItem)getItem()).isRequiredDecretoCIGDatiContabiliADSP()) {
						codiceCIGItem.setAttribute("obbligatorio", true);
						codiceCIGItem.setTitle(FrontendUtil.getRequiredFormItemTitle(((DatiContabiliADSPItem)getItem()).getTitleDecretoCIGDatiContabiliADSP()));
					} else {					
						codiceCIGItem.setAttribute("obbligatorio", false);
						codiceCIGItem.setTitle(((DatiContabiliADSPItem)getItem()).getTitleDecretoCIGDatiContabiliADSP());					
					}					
					return ((DatiContabiliADSPItem) getItem()).showDecretoCIGDatiContabiliADSP();
				}
			});
			codiceCIGItem.setValidators(new RequiredIfValidator(new RequiredIfFunction() {
				
				@Override
				public boolean execute(FormItem formItem, Object value) {
					return ((DatiContabiliADSPItem) getItem()).isRequiredDecretoCIGDatiContabiliADSP();
				}
			}), codiceCIGLengthValidator);	
			
		}				
		
		RecordList lCIGCUPRecordList = ((DatiContabiliADSPItem) getItem()).getCIGCUPRecordList();		
		
		if(lCIGValueMap != null && lCIGValueMap.length > 0 && 
			lCIGCUPRecordList != null && lCIGCUPRecordList.getLength() > 0) {				

			codiceCUPItem = new SelectItem("codiceCUP", ((DatiContabiliADSPItem) getItem()).getTitleDecretoCUPDatiContabiliADSP());
			codiceCUPItem.setWidth(160);
			codiceCUPItem.setColSpan(1);
			codiceCUPItem.setValueMap();
			if(!((DatiContabiliADSPItem)getItem()).isRequiredDecretoCUPDatiContabiliADSP()) {
				((SelectItem) codiceCUPItem).setAllowEmptyValue(true);
			}
			codiceCUPItem.setShowIfCondition(new FormItemIfFunction() {
				
				@Override
				public boolean execute(FormItem item, Object value, DynamicForm form) {
					if(((DatiContabiliADSPItem)getItem()).isRequiredDecretoCUPDatiContabiliADSP()) {
						codiceCUPItem.setAttribute("obbligatorio", true);
						codiceCUPItem.setTitle(FrontendUtil.getRequiredFormItemTitle(((DatiContabiliADSPItem)getItem()).getTitleDecretoCUPDatiContabiliADSP()));
					} else {					
						codiceCUPItem.setAttribute("obbligatorio", false);
						codiceCUPItem.setTitle(((DatiContabiliADSPItem)getItem()).getTitleDecretoCUPDatiContabiliADSP());					
					}					
					return ((DatiContabiliADSPItem) getItem()).showDecretoCUPDatiContabiliADSP();
				}
			});
			codiceCUPItem.setValidators(new RequiredIfValidator(new RequiredIfFunction() {
				
				@Override
				public boolean execute(FormItem formItem, Object value) {
					return ((DatiContabiliADSPItem) getItem()).isRequiredDecretoCUPDatiContabiliADSP();
				}
			}));	
			
		} else {
			
			CustomValidator codiceCUPLengthValidator = new CustomValidator() {

				@Override
				protected boolean condition(Object value) {
					if (value != null && !"".equals((String) value)) {
						String valore = (String) value;
						return valore.length() == 15;
					}
					return true;
				}
			};
			codiceCUPLengthValidator.setErrorMessage("Il codice CUP, se indicato, deve essere di 15 caratteri");
			
			codiceCUPItem = new TextItem("codiceCUP", ((DatiContabiliADSPItem) getItem()).getTitleDecretoCUPDatiContabiliADSP());
			codiceCUPItem.setWidth(160);
			codiceCUPItem.setColSpan(1);
			((TextItem) codiceCUPItem).setLength(15);
			codiceCUPItem.setInputTransformer(new FormItemInputTransformer() {
				
				@Override
				public Object transformInput(DynamicForm form, FormItem item, Object value, Object oldValue) {
					return value != null ? ((String)value).trim().replaceAll(" ", "") : null;
				}
			});
			codiceCUPItem.setShowIfCondition(new FormItemIfFunction() {
				
				@Override
				public boolean execute(FormItem item, Object value, DynamicForm form) {
					if(((DatiContabiliADSPItem)getItem()).isRequiredDecretoCUPDatiContabiliADSP()) {
						codiceCUPItem.setAttribute("obbligatorio", true);
						codiceCUPItem.setTitle(FrontendUtil.getRequiredFormItemTitle(((DatiContabiliADSPItem)getItem()).getTitleDecretoCUPDatiContabiliADSP()));
					} else {					
						codiceCUPItem.setAttribute("obbligatorio", false);
						codiceCUPItem.setTitle(((DatiContabiliADSPItem)getItem()).getTitleDecretoCUPDatiContabiliADSP());					
					}				
					return ((DatiContabiliADSPItem) getItem()).showDecretoCUPDatiContabiliADSP();
				}
			});
			codiceCUPItem.setValidators(new RequiredIfValidator(new RequiredIfFunction() {
				
				@Override
				public boolean execute(FormItem formItem, Object value) {
					return ((DatiContabiliADSPItem) getItem()).isRequiredDecretoCUPDatiContabiliADSP();
				}
			}), codiceCUPLengthValidator);	
		}				
		
		importoItem = new ExtendedNumericItem("importo", ((DatiContabiliADSPItem) getItem()).getTitleDecretoImportoDatiContabiliADSP());
		importoItem.setKeyPressFilter("[0-9.,]");
		importoItem.setWidth(160);
		importoItem.setColSpan(1);		
		importoItem.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				if(((DatiContabiliADSPItem)getItem()).isRequiredDecretoImportoDatiContabiliADSP()) {
					importoItem.setAttribute("obbligatorio", true);
					importoItem.setTitle(FrontendUtil.getRequiredFormItemTitle(((DatiContabiliADSPItem)getItem()).getTitleDecretoImportoDatiContabiliADSP()));
				} else {					
					importoItem.setAttribute("obbligatorio", false);
					importoItem.setTitle(((DatiContabiliADSPItem)getItem()).getTitleDecretoImportoDatiContabiliADSP());					
				}				
				importoItem.setValue(NumberFormatUtility.getFormattedValue(importoItem.getValueAsString()));
				return ((DatiContabiliADSPItem) getItem()).showDecretoImportoDatiContabiliADSP();
			}
		});
		RegExpValidator importoPrecisionValidator = new RegExpValidator();
		importoPrecisionValidator.setExpression("^([0-9]{1,3}((\\.)?[0-9]{3})*(,[0-9]{1,2})?)$");
		importoPrecisionValidator.setErrorMessage("Valore non valido o superato il limite di 2 cifre decimali");
		CustomValidator importoMaggioreDiZeroValidator = new CustomValidator() {
			
			@Override
			protected boolean condition(Object value) {
				if(!((DatiContabiliADSPItem) getItem()).isSkipControlloImportoMaggioreDiZeroValidator()) {
					String pattern = "#,##0.00";
					double importo = 0;
					if(value != null && !"".equals(value)) {
						importo = new Double(NumberFormat.getFormat(pattern).parse((String) value)).doubleValue();			
					}
					return importo > 0;
				}
				return true;
			}
		};
		importoMaggioreDiZeroValidator.setErrorMessage("Valore non valido: l'importo deve essere maggiore di zero");
		importoItem.setValidators(new RequiredIfValidator(new RequiredIfFunction() {
			
			@Override
			public boolean execute(FormItem formItem, Object value) {
				return ((DatiContabiliADSPItem) getItem()).isRequiredDecretoImportoDatiContabiliADSP();
			}
		}), importoPrecisionValidator, importoMaggioreDiZeroValidator);
		importoItem.addChangedBlurHandler(new ChangedHandler() {
			
			@Override
			public void onChanged(ChangedEvent event) {				
				importoItem.setValue(NumberFormatUtility.getFormattedValue((String) event.getValue()));
			}
		});

		LinkedHashMap<String, String> lOpereADSPValueMap = ((DatiContabiliADSPItem) getItem()).getOpereADSPValueMap();		
				
		operaItem = new SelectItem("opera", ((DatiContabiliADSPItem) getItem()).getTitleDecretoOperaDatiContabiliADSP());
		operaItem.setStartRow(true);
		operaItem.setWidth(496);
		operaItem.setColSpan(19);		
		operaItem.setValueMap(lOpereADSPValueMap);	
		if(((DatiContabiliADSPItem)getItem()).isRequiredDecretoOperaDatiContabiliADSP()) {
			if(lOpereADSPValueMap != null && lOpereADSPValueMap.size() == 1) {
				operaItem.setDefaultValue(lOpereADSPValueMap.keySet().iterator().next());
			}
		} else {
			operaItem.setAllowEmptyValue(true);
		}
		operaItem.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				if(((DatiContabiliADSPItem)getItem()).isRequiredDecretoOperaDatiContabiliADSP()) {
					operaItem.setAttribute("obbligatorio", true);
					operaItem.setTitle(FrontendUtil.getRequiredFormItemTitle(((DatiContabiliADSPItem)getItem()).getTitleDecretoOperaDatiContabiliADSP()));
				} else {					
					operaItem.setAttribute("obbligatorio", false);
					operaItem.setTitle(((DatiContabiliADSPItem)getItem()).getTitleDecretoOperaDatiContabiliADSP());					
				}				
				return ((DatiContabiliADSPItem) getItem()).showDecretoOperaDatiContabiliADSP();
			}
		});
		operaItem.setValidators(new RequiredIfValidator(new RequiredIfFunction() {
			
			@Override
			public boolean execute(FormItem formItem, Object value) {
				return ((DatiContabiliADSPItem) getItem()).isRequiredDecretoOperaDatiContabiliADSP();
			}
		}));	
		
		mDynamicForm.setFields(annoEsercizioItem, capitoloItem, contoItem, codiceCIGItem, codiceCUPItem, importoItem, operaItem);
					
		addChild(mDynamicForm);		
	}
	
	@Override
	public ReplicableCanvasForm[] getForm() {
		return new ReplicableCanvasForm[]{mDynamicForm};
	}
	
	@Override
	public void editRecord(Record record) {
		super.editRecord(record);
		reloadCUPValueMap(record.getAttribute("codiceCIG"));
	}
	
	public void reloadCUPValueMap(String codiceCIG)  {	
		RecordList lCIGCUPRecordList = ((DatiContabiliADSPItem) getItem()).getCIGCUPRecordList();				
		if(lCIGCUPRecordList != null && lCIGCUPRecordList.getLength() > 0) {
			List<String> listaCodCUP = new ArrayList<String>();
			if(codiceCIG != null && !"".equals(codiceCIG)) {
				for(int i=0; i < lCIGCUPRecordList.getLength(); i++) {
					if(lCIGCUPRecordList.get(i).getAttribute("codiceCIG") != null && codiceCIG.equals(lCIGCUPRecordList.get(i).getAttribute("codiceCIG"))) {
						listaCodCUP.add(lCIGCUPRecordList.get(i).getAttribute("codiceCUP"));
					}
				}
			}
			codiceCUPItem.setValueMap(listaCodCUP.toArray(new String[listaCodCUP.size()]));				
			if(codiceCUPItem.getValue() != null && !"".equals(codiceCUPItem.getValue()) && listaCodCUP != null && !listaCodCUP.contains(codiceCUPItem.getValue())) {
				mDynamicForm.clearValue("codiceCUP");
			}
			if(((DatiContabiliADSPItem)getItem()).isRequiredDecretoCUPDatiContabiliADSP()) {
				if(listaCodCUP != null && listaCodCUP.size() == 1) {
					mDynamicForm.setValue("codiceCUP", "");
				}
			}	
		}
	}
	
	public void reloadOpereADSPValueMap()  {	
		LinkedHashMap<String, String> lOpereADSPValueMap = ((DatiContabiliADSPItem) getItem()).getOpereADSPValueMap();
		operaItem.setValueMap(lOpereADSPValueMap);
		if(operaItem.getValue() != null && lOpereADSPValueMap != null && !lOpereADSPValueMap.containsKey(operaItem.getValue())) {
			mDynamicForm.clearValue("opera");
		}
		if(((DatiContabiliADSPItem)getItem()).isRequiredDecretoOperaDatiContabiliADSP()) {
			if(lOpereADSPValueMap != null && lOpereADSPValueMap.size() == 1) {
				mDynamicForm.setValue("opera", lOpereADSPValueMap.keySet().iterator().next());
			}
		}	
	}
	
}
