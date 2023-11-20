/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.i18n.client.NumberFormat;
import com.smartgwt.client.data.DSCallback;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.data.RecordList;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.FormItemIfFunction;
import com.smartgwt.client.widgets.form.fields.FormItem;
import com.smartgwt.client.widgets.form.fields.HiddenItem;
import com.smartgwt.client.widgets.form.fields.events.ChangedEvent;
import com.smartgwt.client.widgets.form.fields.events.ChangedHandler;
import com.smartgwt.client.widgets.form.validator.CustomValidator;
import com.smartgwt.client.widgets.form.validator.RegExpValidator;
import com.smartgwt.client.widgets.form.validator.RequiredIfFunction;
import com.smartgwt.client.widgets.form.validator.RequiredIfValidator;
import com.smartgwt.client.widgets.layout.VLayout;

import it.eng.auriga.ui.module.layout.client.AurigaLayout;
import it.eng.auriga.ui.module.layout.client.NumberFormatUtility;
import it.eng.auriga.ui.module.layout.client.i18n.I18NUtil;
import it.eng.utility.ui.module.core.client.datasource.GWTRestDataSource;
import it.eng.utility.ui.module.layout.client.Layout;
import it.eng.utility.ui.module.layout.client.common.CustomDetail;
import it.eng.utility.ui.module.layout.client.common.filter.item.AnnoItem;
import it.eng.utility.ui.module.layout.client.common.items.ExtendedNumericItem;
import it.eng.utility.ui.module.layout.client.common.items.ImgButtonItem;
import it.eng.utility.ui.module.layout.client.common.items.NumericItem;
import it.eng.utility.ui.module.layout.client.common.items.SelectItem;
import it.eng.utility.ui.module.layout.client.common.items.TextAreaItem;
import it.eng.utility.ui.module.layout.client.common.items.TextItem;
import it.eng.utility.ui.module.layout.shared.util.FrontendUtil;

public class DatiContabiliADSPDetail extends CustomDetail {
	
	protected ListaDatiContabiliADSPItem gridItem;
	protected Record gridRecord;
	protected DynamicForm mDynamicForm;
	
	protected AnnoItem annoEsercizioItem;
	protected SelectItem flgEntrataUscitaItem;
	protected SelectItem codiceCIGItem;
	protected SelectItem codiceCUPItem;
	protected ExtendedNumericItem importoItem;	
	protected ExtendedNumericItem imponibileItem;		
	protected SelectItem operaItem;
	protected HiddenItem desOperaItem;
	protected TextItem capitoloItem;
	protected NumericItem contoItem;
	protected TextAreaItem noteItem;
	protected ListaCapitoliADSPItem listaCapitoliADSPItem;
	protected ImgButtonItem capitoloValidoItem;
	protected HiddenItem statoSistemaContabileItem;
	protected HiddenItem operazioneSistemaContabileItem;
	protected HiddenItem erroreSistemaContabileItem;
	protected HiddenItem keyCapitoloItem;
	protected HiddenItem idItem;
	
	public DatiContabiliADSPDetail(final ListaDatiContabiliADSPItem gridItem, String nomeEntita, Record record, boolean canEdit, final String cdrUOCompetente) {
		
		super(nomeEntita);
		
		this.gridItem = gridItem;
		this.gridRecord = record;
		
		mDynamicForm = new DynamicForm();
		mDynamicForm.setWidth100();
		mDynamicForm.setNumCols(18);
		mDynamicForm.setColWidths(1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, "*");
		mDynamicForm.setValuesManager(vm);
		mDynamicForm.setWrapItemTitles(false);
//		mDynamicForm.setCellBorder(1);
				
		flgEntrataUscitaItem = new SelectItem("flgEntrataUscita", "Entrata/Uscita") {
			
			@Override
			public void setCanEdit(Boolean canEdit) {
				super.setCanEdit(gridItem.isDisattivaIntegrazioneSistemaContabile() ? false : canEdit);
			}
		};
		flgEntrataUscitaItem.setStartRow(true);
		LinkedHashMap<String, String> flgEntrataUscitaValueMap = new LinkedHashMap<String, String>();
		flgEntrataUscitaValueMap.put("E", "Entrata");
		flgEntrataUscitaValueMap.put("U", "Uscita");
		flgEntrataUscitaItem.setValueMap(flgEntrataUscitaValueMap);
//		if(gridItem.isDisattivaIntegrazioneSistemaContabile()) {
//			flgEntrataUscitaItem.setDefaultValue("U");
//		} else {
		flgEntrataUscitaItem.setDefaultValue("U");
//		}
		flgEntrataUscitaItem.setAllowEmptyValue(false);
		flgEntrataUscitaItem.setWidth(245);
		flgEntrataUscitaItem.setColSpan(4);
		flgEntrataUscitaItem.setRequired(true);
		flgEntrataUscitaItem.addChangedHandler(new ChangedHandler() {
			
			@Override
			public void onChanged(ChangedEvent event) {
				mDynamicForm.redraw();
			}
		});
		
		annoEsercizioItem = new AnnoItem("annoEsercizio", gridItem.getTitleEsercizioDatiContabiliADSP());
		annoEsercizioItem.setWidth(100);
		annoEsercizioItem.setColSpan(5);
		annoEsercizioItem.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				if(gridItem.isRequiredEsercizioDatiContabiliADSP()) {
					annoEsercizioItem.setAttribute("obbligatorio", true);
					annoEsercizioItem.setTitle(FrontendUtil.getRequiredFormItemTitle(gridItem.getTitleEsercizioDatiContabiliADSP()));
				} else {					
					annoEsercizioItem.setAttribute("obbligatorio", false);
					annoEsercizioItem.setTitle(gridItem.getTitleEsercizioDatiContabiliADSP());					
				}				
				return gridItem.showEsercizioDatiContabiliADSP();
			}
		});
		annoEsercizioItem.setValidators(new RequiredIfValidator(new RequiredIfFunction() {
			
			@Override
			public boolean execute(FormItem formItem, Object value) {
				return gridItem.isRequiredEsercizioDatiContabiliADSP();
			}
		}));
		
		String annoCorrente = getAnnoCorrente();
		
		annoEsercizioItem.setDefaultValue(annoCorrente);
		
		CustomValidator codiceCIGEsistenteValidator = new CustomValidator() {
			
			@Override
			protected boolean condition(Object value) {
				if(value != null && !"".equals(value)) {					
					String[] lCIGValueMap = gridItem.getCIGValueMap();
					boolean trovato = false;					
					if(lCIGValueMap != null) {
						for(int i = 0; i < lCIGValueMap.length; i++) {
							if(lCIGValueMap[i] != null && lCIGValueMap[i].equals(value)) {
								trovato = true;
								break;
							}
						}
					}
					return trovato;									
				}
				return true;
			}
		};
		codiceCIGEsistenteValidator.setErrorMessage("Valore non valido: non è uno di quelli previsti");
		
		String[] lCIGValueMap = getCIGValueMap();			
						
		codiceCIGItem = new SelectItem("codiceCIG", gridItem.getTitleDecretoCIGDatiContabiliADSP());
		codiceCIGItem.setStartRow(true);
		codiceCIGItem.setWidth(245);
		codiceCIGItem.setColSpan(4);
		codiceCIGItem.setAddUnknownValues(false);
		codiceCIGItem.setValueMap(lCIGValueMap);
		if(gridItem.isRequiredDecretoCIGDatiContabiliADSP()) {
			if(lCIGValueMap != null && lCIGValueMap.length == 1) {
				codiceCIGItem.setDefaultValue(lCIGValueMap[0]);
			}
		} else {
			codiceCIGItem.setAllowEmptyValue(true);
		}
		codiceCIGItem.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				if(gridItem.isRequiredDecretoCIGDatiContabiliADSP()) {
					codiceCIGItem.setAttribute("obbligatorio", true);
					codiceCIGItem.setTitle(FrontendUtil.getRequiredFormItemTitle(gridItem.getTitleDecretoCIGDatiContabiliADSP()));
				} else {					
					codiceCIGItem.setAttribute("obbligatorio", false);
					codiceCIGItem.setTitle(gridItem.getTitleDecretoCIGDatiContabiliADSP());					
				}					
				return gridItem.showDecretoCIGDatiContabiliADSP();
			}
		});
		codiceCIGItem.setValidators(new RequiredIfValidator(new RequiredIfFunction() {
			
			@Override
			public boolean execute(FormItem formItem, Object value) {
				return gridItem.isRequiredDecretoCIGDatiContabiliADSP();
			}
		}), codiceCIGEsistenteValidator);	
		codiceCIGItem.addChangedHandler(new ChangedHandler() {
			
			@Override
			public void onChanged(ChangedEvent event) {
				reloadCUPValueMap((String) event.getValue());
			}
		});
		
		CustomValidator codiceCUPEsistenteValidator = new CustomValidator() {
			
			@Override
			protected boolean condition(Object value) {
				if(value != null && !"".equals(value)) {	
					String codiceCIG = codiceCIGItem != null ? codiceCIGItem.getValueAsString() : null;
					RecordList lCIGCUPRecordList = gridItem.getCIGCUPRecordList();	
					boolean trovato = false;					
					if(lCIGCUPRecordList != null) {
						for(int i = 0; i < lCIGCUPRecordList.getLength(); i++) {
							if(codiceCIG != null && lCIGCUPRecordList.get(i).getAttribute("codiceCIG") != null && codiceCIG.equals(lCIGCUPRecordList.get(i).getAttribute("codiceCIG")) &&
								lCIGCUPRecordList.get(i).getAttribute("codiceCUP") != null && value.equals(lCIGCUPRecordList.get(i).getAttribute("codiceCUP"))) {
								trovato = true;
							}
						}						
					}
					return trovato;									
				}
				return true;
			}
		};
		codiceCUPEsistenteValidator.setErrorMessage("Valore non valido: non è uno di quelli previsti");
		
		codiceCUPItem = new SelectItem("codiceCUP", gridItem.getTitleDecretoCUPDatiContabiliADSP());
		codiceCUPItem.setStartRow(true);			
		codiceCUPItem.setWidth(245);
		codiceCUPItem.setColSpan(4);
		codiceCUPItem.setAddUnknownValues(false);
		codiceCUPItem.setValueMap();
		if(!gridItem.isRequiredDecretoCUPDatiContabiliADSP()) {
			codiceCUPItem.setAllowEmptyValue(true);
		}
		codiceCUPItem.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				if(gridItem.isRequiredDecretoCUPDatiContabiliADSP()) {
					codiceCUPItem.setAttribute("obbligatorio", true);
					codiceCUPItem.setTitle(FrontendUtil.getRequiredFormItemTitle(gridItem.getTitleDecretoCUPDatiContabiliADSP()));
				} else {					
					codiceCUPItem.setAttribute("obbligatorio", false);
					codiceCUPItem.setTitle(gridItem.getTitleDecretoCUPDatiContabiliADSP());					
				}					
				return gridItem.showDecretoCUPDatiContabiliADSP();
			}
		});
		codiceCUPItem.setValidators(new RequiredIfValidator(new RequiredIfFunction() {
			
			@Override
			public boolean execute(FormItem formItem, Object value) {
				return gridItem.isRequiredDecretoCUPDatiContabiliADSP();
			}
		}), codiceCUPEsistenteValidator);					

		boolean flgDisattivaIntegrazioneSistContabile = gridItem.isDisattivaIntegrazioneSistemaContabile();
		
		final String labelImporto = flgDisattivaIntegrazioneSistContabile ? gridItem.getTitleDecretoImportoDatiContabiliADSP() + " (con IVA)" : gridItem.getTitleDecretoImportoDatiContabiliADSP();
		
		importoItem = new ExtendedNumericItem("importo", labelImporto);
		importoItem.setStartRow(true);
		importoItem.setKeyPressFilter("[0-9.,]");
		importoItem.setWidth(245);
		importoItem.setColSpan(4);		
		importoItem.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				if(gridItem.isRequiredDecretoImportoDatiContabiliADSP()) {
					importoItem.setAttribute("obbligatorio", true);
					importoItem.setTitle(FrontendUtil.getRequiredFormItemTitle(labelImporto));
				} else {					
					importoItem.setAttribute("obbligatorio", false);
					importoItem.setTitle(labelImporto);					
				}				
				importoItem.setValue(NumberFormatUtility.getFormattedValue(importoItem.getValueAsString()));
				return gridItem.showDecretoImportoDatiContabiliADSP();
			}
		});
		RegExpValidator importoPrecisionValidator = new RegExpValidator();
		importoPrecisionValidator.setExpression("^([0-9]{1,3}((\\.)?[0-9]{3})*(,[0-9]{1,2})?)$");
		importoPrecisionValidator.setErrorMessage("Valore non valido o superato il limite di 2 cifre decimali");
		CustomValidator importoMaggioreDiZeroValidator = new CustomValidator() {
			
			@Override
			protected boolean condition(Object value) {
				if(!gridItem.isSkipControlloImportoMaggioreDiZeroValidator()) {
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
				return gridItem.isRequiredDecretoImportoDatiContabiliADSP();
			}
		}), importoPrecisionValidator, importoMaggioreDiZeroValidator);
		importoItem.addChangedBlurHandler(new ChangedHandler() {
			
			@Override
			public void onChanged(ChangedEvent event) {				
				importoItem.setValue(NumberFormatUtility.getFormattedValue((String) event.getValue()));
			}
		});
		
		final String labelImponibile = flgDisattivaIntegrazioneSistContabile ? gridItem.getTitleDecretoImponibileDatiContabiliADSP() + " (senza IVA)" : gridItem.getTitleDecretoImponibileDatiContabiliADSP();

		
		imponibileItem = new ExtendedNumericItem("imponibile", labelImponibile);
		imponibileItem.setStartRow(true);
		imponibileItem.setKeyPressFilter("[0-9.,]");
		imponibileItem.setWidth(245);
		imponibileItem.setColSpan(4);		
		imponibileItem.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				if(gridItem.isRequiredDecretoImponibileDatiContabiliADSP()) {
					imponibileItem.setAttribute("obbligatorio", true);
					imponibileItem.setTitle(FrontendUtil.getRequiredFormItemTitle(labelImponibile));
				} else {					
					imponibileItem.setAttribute("obbligatorio", false);
					imponibileItem.setTitle(labelImponibile);					
				}				
				imponibileItem.setValue(NumberFormatUtility.getFormattedValue(imponibileItem.getValueAsString()));
				return gridItem.showDecretoImponibileDatiContabiliADSP();
			}
		});
		RegExpValidator imponibilePrecisionValidator = new RegExpValidator();
		imponibilePrecisionValidator.setExpression("^([0-9]{1,3}((\\.)?[0-9]{3})*(,[0-9]{1,2})?)$");
		imponibilePrecisionValidator.setErrorMessage("Valore non valido o superato il limite di 2 cifre decimali");
		CustomValidator imponibileMaggioreDiZeroValidator = new CustomValidator() {
			
			@Override
			protected boolean condition(Object value) {
				if(!gridItem.isSkipControlloImponibileMaggioreDiZeroValidator()) {
					String pattern = "#,##0.00";
					double imponibile = 0;
					if(value != null && !"".equals(value)) {
						imponibile = new Double(NumberFormat.getFormat(pattern).parse((String) value)).doubleValue();			
					}
					return imponibile > 0;
				}
				return true;
			}
		};
		imponibileMaggioreDiZeroValidator.setErrorMessage("Valore non valido: l'imponibile deve essere maggiore di zero");
		CustomValidator imponibileMinoreOUgualeDiImportoValidator = new CustomValidator() {
			
			@Override
			protected boolean condition(Object value) {
				String pattern = "#,##0.00";
				double imponibile = 0;
				if(value != null && !"".equals(value)) {
					imponibile = new Double(NumberFormat.getFormat(pattern).parse((String) value)).doubleValue();			
				}
				double importo = 0;
				if(importoItem.getValue() != null && !"".equals(importoItem.getValue())) {
					importo = new Double(NumberFormat.getFormat(pattern).parse((String) importoItem.getValue())).doubleValue();			
				}
				return imponibile <= importo;	
			}
		};
		imponibileMinoreOUgualeDiImportoValidator.setErrorMessage("Valore non valido: l'imponibile deve essere minore o uguale dell'importo");
		imponibileItem.setValidators(new RequiredIfValidator(new RequiredIfFunction() {
			
			@Override
			public boolean execute(FormItem formItem, Object value) {
				return gridItem.isRequiredDecretoImponibileDatiContabiliADSP();
			}
		}), imponibilePrecisionValidator, imponibileMaggioreDiZeroValidator, imponibileMinoreOUgualeDiImportoValidator);
		imponibileItem.addChangedBlurHandler(new ChangedHandler() {
			
			@Override
			public void onChanged(ChangedEvent event) {				
				imponibileItem.setValue(NumberFormatUtility.getFormattedValue((String) event.getValue()));
			}
		});
		
		CustomValidator operaEsistenteValidator = new CustomValidator() {
			
			@Override
			protected boolean condition(Object value) {
				if(value != null && !"".equals(value)) {					
					LinkedHashMap<String, String> lOpereADSPValueMap = gridItem.getOpereADSPValueMap();
					return lOpereADSPValueMap != null && lOpereADSPValueMap.containsKey(value);														
				}
				return true;
			}
		};
		operaEsistenteValidator.setErrorMessage("Valore non valido: non è uno di quelli previsti");
				
		final LinkedHashMap<String, String> lOpereADSPValueMap = getOpereADSPValueMap();		
		
		operaItem = new SelectItem("opera", gridItem.getTitleDecretoOperaDatiContabiliADSP());
		operaItem.setStartRow(true);
		operaItem.setWidth(496);
		operaItem.setColSpan(17);
		operaItem.setAddUnknownValues(false);
//		operaItem.setOptionDataSource(opereDataSource);
		operaItem.setValueMap(lOpereADSPValueMap);	
		if(gridItem.isRequiredDecretoOperaDatiContabiliADSP()) {
			if(lOpereADSPValueMap != null && lOpereADSPValueMap.size() == 1) {
				operaItem.setDefaultValue(lOpereADSPValueMap.keySet().iterator().next());
			}
		} else {
			operaItem.setAllowEmptyValue(true);
		}
		operaItem.addChangedHandler(new ChangedHandler() {
			
			@Override
			public void onChanged(ChangedEvent event) {	
				if(event.getValue() != null && !"".equals(event.getValue())) {
					mDynamicForm.setValue("desOpera", lOpereADSPValueMap != null ? lOpereADSPValueMap.get(event.getValue()) : "");
				} else {
					mDynamicForm.setValue("desOpera", "");
				}
			}
		});
		operaItem.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				if(gridItem.isRequiredDecretoOperaDatiContabiliADSP()) {
					operaItem.setAttribute("obbligatorio", true);
					operaItem.setTitle(FrontendUtil.getRequiredFormItemTitle(gridItem.getTitleDecretoOperaDatiContabiliADSP()));
				} else {					
					operaItem.setAttribute("obbligatorio", false);
					operaItem.setTitle(gridItem.getTitleDecretoOperaDatiContabiliADSP());					
				}				
				return gridItem.showDecretoOperaDatiContabiliADSP();
			}
		});
		operaItem.setValidators(new RequiredIfValidator(new RequiredIfFunction() {
			
			@Override
			public boolean execute(FormItem formItem, Object value) {
				return gridItem.isRequiredDecretoOperaDatiContabiliADSP();
			}
		}), operaEsistenteValidator);	
		
		desOperaItem = new HiddenItem("desOpera");
		if(gridItem.isRequiredDecretoOperaDatiContabiliADSP()) {
			if(lOpereADSPValueMap != null && lOpereADSPValueMap.size() == 1) {
				desOperaItem.setDefaultValue(lOpereADSPValueMap.values().iterator().next());
			}
		}
		
		capitoloItem = new TextItem("capitolo", gridItem.getTitleCapitoloDatiContabiliADSP());
		capitoloItem.setWidth(90);
//		capitoloItem.setColSpan(1);
		capitoloItem.setStartRow(true);
		capitoloItem.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				if(gridItem.isRequiredCapitoloDatiContabiliADSP()) {
					capitoloItem.setAttribute("obbligatorio", true);
					capitoloItem.setTitle(FrontendUtil.getRequiredFormItemTitle(gridItem.getTitleCapitoloDatiContabiliADSP()));
				} else {					
					capitoloItem.setAttribute("obbligatorio", false);
					capitoloItem.setTitle(gridItem.getTitleCapitoloDatiContabiliADSP());					
				}				
				return gridItem.showCapitoloDatiContabiliADSP();
			}
		});
		CustomValidator capitoloValidoValidator = new CustomValidator() {
			
			@Override
			protected boolean condition(Object value) {
				
				return (capitoloItem!= null && capitoloItem.getValueAsString()!=null && !"".equals(capitoloItem.getValueAsString()) && 
						contoItem!= null && contoItem.getValueAsString()!=null && !"".equals(contoItem.getValueAsString()));
			}
		};
		capitoloValidoValidator.setErrorMessage("Valorizzare i campi capitolo e conto e/o selezionarli dalla lista");
		RequiredIfValidator requiredValidatorCapitolo = new RequiredIfValidator(new RequiredIfFunction() {
			
			@Override
			public boolean execute(FormItem formItem, Object value) {
				return gridItem.isRequiredCapitoloDatiContabiliADSP();
			}
		});
		capitoloItem.setValidators(requiredValidatorCapitolo, capitoloValidoValidator);
		capitoloItem.addChangedHandler(new ChangedHandler() {
			
			@Override
			public void onChanged(ChangedEvent event) {
				keyCapitoloItem.setValue("");
				mDynamicForm.redraw();
			}
		});
		
		contoItem = new NumericItem("conto", gridItem.getTitleContoDatiContabiliADSP(), false);
		contoItem.setWidth(120);
//		contoItem.setColSpan(1);
		contoItem.setRequired(true);
		contoItem.setKeyPressFilter("[0-9.]");
		contoItem.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				if(gridItem.isRequiredContoDatiContabiliADSP()) {
					contoItem.setAttribute("obbligatorio", true);
					contoItem.setTitle(FrontendUtil.getRequiredFormItemTitle(gridItem.getTitleContoDatiContabiliADSP()));
				} else {					
					contoItem.setAttribute("obbligatorio", false);
					contoItem.setTitle(gridItem.getTitleContoDatiContabiliADSP());					
				}				
				return gridItem.showContoDatiContabiliADSP();
			}
		});
		RequiredIfValidator requiredIfValidatorConto = new RequiredIfValidator(new RequiredIfFunction() {
			
			@Override
			public boolean execute(FormItem formItem, Object value) {
//				return gridItem.isRequiredContoDatiContabiliADSP();
				return true;
			}
		});
		contoItem.setValidators(requiredIfValidatorConto, capitoloValidoValidator);
		contoItem.addChangedHandler(new ChangedHandler() {
			
			@Override
			public void onChanged(ChangedEvent event) {
				keyCapitoloItem.setValue("");
				mDynamicForm.redraw();
			}
		});
		
		capitoloValidoItem = new ImgButtonItem("capitoloValido", "postaElettronica/statoConsolidamento/consegnata.png", "Capitolo valido");
		capitoloValidoItem.setColSpan(4);
		capitoloValidoItem.setShowIfCondition(new FormItemIfFunction() {

			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return (keyCapitoloItem!= null && keyCapitoloItem.getValue()!=null && !"".equals(keyCapitoloItem.getValue()));
			}
		});
		
		noteItem = new TextAreaItem("note", I18NUtil.getMessages().protocollazione_detail_noteItem_title());
		noteItem.setHeight(65);
		noteItem.setWidth(496);
		noteItem.setStartRow(true);
		noteItem.setColSpan(17);		
		
		listaCapitoliADSPItem = new ListaCapitoliADSPItem("listaCapitoliADSP") {
			
			@Override
			void onRecordSelected(Record record) {
				capitoloItem.setValue(record.getAttribute("capitolo1"));
				contoItem.setValue(record.getAttribute("capitolo2"));
				keyCapitoloItem.setValue(record.getAttribute("keyCapitolo"));
				mDynamicForm.redraw();
				
			}

			@Override
			protected void onClickCercaCapitoliButton() {
				if(!annoEsercizioItem.validate()) {
					return;
				}
								
				Layout.showWaitPopup("Ricerca capitoli in corso...");
				Record record = new Record();
				record.setAttribute("capitolo1", capitoloItem.getValueAsString());
				record.setAttribute("capitolo2", contoItem.getValueAsString());
				record.setAttribute("annoEsercizio", annoEsercizioItem.getValueAsString());
				record.setAttribute("flgEntrataUscita", flgEntrataUscitaItem.getValueAsString());
				final GWTRestDataSource lContabilitaADSPDataSource = new GWTRestDataSource("ContabilitaADSPDataSource");
				lContabilitaADSPDataSource.extraparam.put("cdrUOCompetente", cdrUOCompetente);
				lContabilitaADSPDataSource.performCustomOperation("getCapitoli", record, new DSCallback() {							
					@Override
					public void execute(DSResponse response, Object rawData, DSRequest request) {
						Layout.hideWaitPopup();
						if(response.getStatus() == DSResponse.STATUS_SUCCESS) {
							Record record = response.getData()[0];
							mDynamicForm.setValue("listaCapitoliADSP", record.getAttributeAsRecordArray("listaCapitoliADSP"));
							mDynamicForm.redraw();
						} 				
					}
				}, new DSRequest());
				
				Layout.hideWaitPopup();
				
			}
		};
		listaCapitoliADSPItem.setStartRow(true);
		listaCapitoliADSPItem.setShowTitle(false);
		listaCapitoliADSPItem.setHeight(345);
		
		statoSistemaContabileItem = new HiddenItem("statoSistemaContabile");
		operazioneSistemaContabileItem = new HiddenItem("operazioneSistemaContabile");
		erroreSistemaContabileItem = new HiddenItem("erroreSistemaContabile");
		keyCapitoloItem = new HiddenItem("keyCapitolo");
		idItem = new HiddenItem("id");
		
		mDynamicForm.setFields(
			flgEntrataUscitaItem, 
			annoEsercizioItem,  
			codiceCIGItem, 
			codiceCUPItem, 
			importoItem, 
			imponibileItem,
			operaItem,
			desOperaItem,
			capitoloItem, 
			contoItem,
			capitoloValidoItem,
			noteItem,
			listaCapitoliADSPItem,
			statoSistemaContabileItem,
			operazioneSistemaContabileItem,
			erroreSistemaContabileItem,
			keyCapitoloItem,
			idItem
		);
				
		VLayout lVLayout = new VLayout();
		lVLayout.setWidth100();

		VLayout lVLayoutSpacer = new VLayout();
		lVLayoutSpacer.setWidth100();
		lVLayoutSpacer.setHeight100();

		lVLayout.addMember(mDynamicForm);

		addMember(lVLayout);
		addMember(lVLayoutSpacer);
		
		if(record != null) {
			editRecord(record);
		} else {
			HashMap<String, Object> initialValues = new HashMap<String, Object>();
			initialValues.put("annoEsercizio", DateTimeFormat.getFormat("yyyy").format(new Date()));
			editNewRecord(initialValues);
		}
		
		setCanEdit(canEdit);				
	}
	
	private String getAnnoCorrente() {
		Date date = new Date();
		String anno = String.valueOf(date.getYear());
		
		return anno;
	}

	public void reloadCUPValueMap(String codiceCIG)  {	
		RecordList lCIGCUPRecordList = getCIGCUPRecordList();
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
			if(gridItem.isRequiredDecretoCUPDatiContabiliADSP()) {
				if(listaCodCUP != null && listaCodCUP.size() == 1) {
					mDynamicForm.setValue("codiceCUP", listaCodCUP.get(0));
				}
			}
		}
	}
	
	public String[] getCIGValueMap() {
		String[] lCIGValueMap = gridItem.getCIGValueMap();
		if(lCIGValueMap != null) {
			if(gridRecord != null && gridRecord.getAttribute("codiceCIG") != null && !"".equals(gridRecord.getAttribute("codiceCIG"))) {
				boolean trovato = false;
				for(int i = 0; i < lCIGValueMap.length; i++) {
					if(lCIGValueMap[i] != null && lCIGValueMap[i].equals(gridRecord.getAttribute("codiceCIG"))) {
						trovato = true;
						break;
					}
				}
				if(!trovato) {
					String[] lCIGValueMapNew = new String[lCIGValueMap.length + 1];
					lCIGValueMapNew[0] = gridRecord.getAttribute("codiceCIG");
					for(int i = 0; i < lCIGValueMap.length; i++) {
						lCIGValueMapNew[i+1] = lCIGValueMap[i];
					}
					return lCIGValueMapNew;
				}			
			}
		}
		return lCIGValueMap;
	}
	
	public RecordList getCIGCUPRecordList() {
		RecordList lCIGCUPRecordList = gridItem.getCIGCUPRecordList();		
		if(lCIGCUPRecordList != null) {
			if(gridRecord != null && gridRecord.getAttribute("codiceCIG") != null && !"".equals(gridRecord.getAttribute("codiceCIG")) &&
				gridRecord.getAttribute("codiceCUP") != null && !"".equals(gridRecord.getAttribute("codiceCUP"))) {				
				boolean trovato = false;				
				for(int i = 0; i < lCIGCUPRecordList.getLength(); i++) {
					if(lCIGCUPRecordList.get(i).getAttribute("codiceCIG") != null && gridRecord.getAttribute("codiceCIG").equals(lCIGCUPRecordList.get(i).getAttribute("codiceCIG")) &&
						lCIGCUPRecordList.get(i).getAttribute("codiceCUP") != null && gridRecord.getAttribute("codiceCUP").equals(lCIGCUPRecordList.get(i).getAttribute("codiceCUP"))) {
						trovato = true;
					}
				}
				if(!trovato) {
					RecordList lCIGCUPRecordListNew = new RecordList();		
					Record lRecord = new Record();
					lRecord.setAttribute("codiceCIG", gridRecord.getAttribute("codiceCIG"));
					lRecord.setAttribute("codiceCUP", gridRecord.getAttribute("codiceCUP"));
					lCIGCUPRecordListNew.add(lRecord);
					lCIGCUPRecordListNew.addList(lCIGCUPRecordList.toArray());
					return lCIGCUPRecordListNew;
				}								
			}
		}
		return lCIGCUPRecordList;
	}
	
	public LinkedHashMap<String, String> getOpereADSPValueMap() {
		LinkedHashMap<String, String> lOpereADSPValueMap = gridItem.getOpereADSPValueMap();
		if(lOpereADSPValueMap != null) {
			if(gridRecord != null && gridRecord.getAttribute("opera") != null && !"".equals(gridRecord.getAttribute("opera"))) {
				if(!lOpereADSPValueMap.containsKey(gridRecord.getAttribute("opera"))) {
					LinkedHashMap<String, String> lOpereADSPValueMapNew = new LinkedHashMap<String, String>();					
					if(gridRecord.getAttribute("desOpera") != null && !"".equals(gridRecord.getAttribute("desOpera"))) {
						lOpereADSPValueMapNew.put(gridRecord.getAttribute("opera"), gridRecord.getAttribute("desOpera"));
					} else {
						lOpereADSPValueMapNew.put(gridRecord.getAttribute("opera"), gridRecord.getAttribute("opera"));	
					}	
					lOpereADSPValueMapNew.putAll(lOpereADSPValueMap);
					return lOpereADSPValueMapNew; 
				}
			}
		}
		return lOpereADSPValueMap;
	}
	
	@Override
	public void setCanEdit(boolean canEdit) {
		super.setCanEdit(canEdit);	
		String attivaBloccoAnnoMovCont = AurigaLayout.getParametroDB("ATTIVA_BLOCCO_ANNO_MOV_CONT");
		if(attivaBloccoAnnoMovCont!=null && !attivaBloccoAnnoMovCont.equalsIgnoreCase("") && "true".equalsIgnoreCase(attivaBloccoAnnoMovCont)) {
			annoEsercizioItem.setCanEdit(false);
		}
	}
	
	@Override
	public void editNewRecord() {		
		reloadCUPValueMap(null);
		super.editNewRecord();
	}
	
	@Override
	public void editNewRecord(Map initialValues) {
		reloadCUPValueMap((String) initialValues.get("codiceCIG"));
		super.editNewRecord(initialValues);
	}
	
	@Override
	public void editRecord(Record record) {		
		reloadCUPValueMap(record.getAttribute("codiceCIG"));
		super.editRecord(record);
	}
	
	public Record getRecordToSave() {
		Record lRecordToSave = super.getRecordToSave();	
		lRecordToSave.setAttribute("flgDisattivaIntegrazioneSistemaContabile", this.gridItem.isDisattivaIntegrazioneSistemaContabile() ? "true" : "false");
		return lRecordToSave;
	}
	
}