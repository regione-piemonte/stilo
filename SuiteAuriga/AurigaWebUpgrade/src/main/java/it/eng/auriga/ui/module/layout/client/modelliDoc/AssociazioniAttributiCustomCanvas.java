/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.HashMap;
import java.util.LinkedHashMap;

import com.smartgwt.client.data.Record;
import com.smartgwt.client.data.RecordList;
import com.smartgwt.client.widgets.events.FetchDataEvent;
import com.smartgwt.client.widgets.events.FetchDataHandler;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.FormItemIfFunction;
import com.smartgwt.client.widgets.form.fields.FormItem;
import com.smartgwt.client.widgets.form.fields.HiddenItem;
import com.smartgwt.client.widgets.form.fields.SelectItem;
import com.smartgwt.client.widgets.form.fields.events.ChangedEvent;
import com.smartgwt.client.widgets.form.fields.events.ChangedHandler;
import com.smartgwt.client.widgets.form.fields.events.IconClickEvent;
import com.smartgwt.client.widgets.form.fields.events.IconClickHandler;
import com.smartgwt.client.widgets.form.validator.RequiredIfFunction;
import com.smartgwt.client.widgets.form.validator.RequiredIfValidator;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridField;

import it.eng.auriga.ui.module.layout.client.attributiCustom.AttributiCustomWindow;
import it.eng.auriga.ui.module.layout.client.i18n.I18NUtil;
import it.eng.utility.ui.module.core.client.datasource.GWTRestDataSource;
import it.eng.utility.ui.module.layout.client.common.ReplicableCanvas;
import it.eng.utility.ui.module.layout.client.common.items.CheckboxItem;
import it.eng.utility.ui.module.layout.client.common.items.ExtendedTextItem;
import it.eng.utility.ui.module.layout.client.common.items.FilteredSelectItem;
import it.eng.utility.ui.module.layout.client.common.items.ImgButtonItem;
import it.eng.utility.ui.module.layout.client.common.items.TextItem;

public class AssociazioniAttributiCustomCanvas extends ReplicableCanvas{

	private TextItem nomeVariabileModelloItem;	
	private SelectItem tipoAssociazioneVariabileModelloItem;
	private HiddenItem aliasVariabileModelloItem;	
	private HiddenItem flgComplexItem;
	private HiddenItem flgImageItem;
	private HiddenItem flgRipetibileItem;
	private FilteredSelectItem nomeAttributoCustomItem;	
	protected ExtendedTextItem nomeAttributoLiberoItem;
	protected ExtendedTextItem numeroColonnaAttributoLiberoItem;
	private HiddenItem associazioniSottoAttributiComplexItem;
	private CheckboxItem flgBarcodeItem;
	private SelectItem tipoBarcodeItem;
	private SelectItem tipoVariabileModelloItem;
	
	private ReplicableCanvasForm mDynamicForm;
	
	public AssociazioniAttributiCustomCanvas(AssociazioniAttributiCustomItem item) {
		super(item);
	}

	public AssociazioniAttributiCustomCanvas(AssociazioniAttributiCustomItem item, HashMap<String, String> parameters) {
		super(item, parameters);
	}
	
	@Override
	public void disegna() {

		mDynamicForm = new ReplicableCanvasForm();
		mDynamicForm.setWrapItemTitles(false);
		mDynamicForm.setNumCols(10);	
		mDynamicForm.setShowInlineErrors(true);
		
		nomeVariabileModelloItem = new TextItem("nomeVariabileModello");
		nomeVariabileModelloItem.setWidth(180);
		nomeVariabileModelloItem.setShowTitle(false);
		nomeVariabileModelloItem.setCanEdit(false);
		nomeVariabileModelloItem.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				String nomeVariabileModello = mDynamicForm.getValueAsString("nomeVariabileModello");
				String aliasVariabileModello = mDynamicForm.getValueAsString("aliasVariabileModello");
				if(aliasVariabileModello != null && !"".equals(aliasVariabileModello)) {
					nomeVariabileModelloItem.setPrompt(nomeVariabileModello + " alias " + aliasVariabileModello);
				} else {
					nomeVariabileModelloItem.setPrompt(nomeVariabileModello);
				}
				return true;
			}
		});
				
		aliasVariabileModelloItem = new HiddenItem("aliasVariabileModello");
		flgComplexItem = new HiddenItem("flgComplex");
		flgRipetibileItem = new HiddenItem("flgRipetibile");
		flgImageItem = new HiddenItem("flgImage");
				
		GWTRestDataSource nomiAttributiCustomDS = new GWTRestDataSource("LoadComboNomiAttributiCustomDataSource");
		nomiAttributiCustomDS.addParam("nomeTabella", ((AssociazioniAttributiCustomItem) getItem()).getNomeTabella());
		nomiAttributiCustomDS.addParam("idEntitaAssociata", ((AssociazioniAttributiCustomItem) getItem()).getIdEntitaAssociata());
		nomiAttributiCustomDS.addParam("appartenenza", ((AssociazioniAttributiCustomItem) getItem()).getNomeAttributoComplex());
		
		tipoAssociazioneVariabileModelloItem = new SelectItem("tipoAssociazioneVariabileModello", "Associa a");
		LinkedHashMap<String, String> tipoAssociazioneValueMap = new LinkedHashMap<String, String>();
		tipoAssociazioneValueMap.put("attributoLibero", "Attributo libero");
		// Se non ho nomeTabella e idEntitaAssociata non do la possibiltà di scegliere l'attributo custom
		if (isNomeTabellaOrIdEntitaSetted()) {
			tipoAssociazioneValueMap.put("attributoCustom", "Attributo custom");
		}
		tipoAssociazioneVariabileModelloItem.setValueMap(tipoAssociazioneValueMap);
		tipoAssociazioneVariabileModelloItem.setDefaultValue("attributoLibero");
		tipoAssociazioneVariabileModelloItem.setColSpan(1);
		tipoAssociazioneVariabileModelloItem.setWidth(150);
		tipoAssociazioneVariabileModelloItem.addChangedHandler(new ChangedHandler() {
			
			@Override
			public void onChanged(ChangedEvent event) {	
				mDynamicForm.markForRedraw();
			}
		});
		
		tipoAssociazioneVariabileModelloItem.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				boolean flgComplex = mDynamicForm.getValue("flgComplex") != null && (Boolean) mDynamicForm.getValue("flgComplex");
				boolean flgImage = mDynamicForm.getValue("flgImage") != null && (Boolean) mDynamicForm.getValue("flgImage");
				if (flgComplex) {
					mDynamicForm.setValue("tipoVariabileModello", "COMPLEX");
					tipoVariabileModelloItem.setCanEdit(false);
				} else if (flgImage) {
					mDynamicForm.setValue("tipoVariabileModello", "IMAGE");
					tipoVariabileModelloItem.setCanEdit(false);
				}
				
				if (((AssociazioniAttributiCustomItem) getItem()).isAssociazioneSottoAttributo()){
					tipoAssociazioneVariabileModelloItem.setCanEdit(false);
					if (isAssociazioneSottoAttributoLibero()) {
						mDynamicForm.setValue("tipoAssociazioneVariabileModello", "attributoLibero");
					} else {
						mDynamicForm.setValue("tipoAssociazioneVariabileModello", "attributoCustom");
					}
				}
				return true;
			}
		});
		
		if (!isAssociazioneSottoAttributoLibero() && isNomeTabellaOrIdEntitaSetted()){
			nomeAttributoCustomItem = new FilteredSelectItem("nomeAttributoCustom");		
			nomeAttributoCustomItem.setWidth(300);
			nomeAttributoCustomItem.setValueField("key");
			nomeAttributoCustomItem.setDisplayField("key");
			nomeAttributoCustomItem.setOptionDataSource(nomiAttributiCustomDS);
			nomeAttributoCustomItem.setAutoFetchData(false);
			nomeAttributoCustomItem.setAlwaysFetchMissingValues(true);
			nomeAttributoCustomItem.setFetchMissingValues(true);
			nomeAttributoCustomItem.setShowTitle(false);
			nomeAttributoCustomItem.setClearable(true);					
			ListGridField nomeField = new ListGridField("key", I18NUtil.getMessages().attributi_custom_nome());
			ListGridField labelField = new ListGridField("value", I18NUtil.getMessages().attributi_custom_etichetta());
			nomeAttributoCustomItem.setPickListFields(nomeField, labelField);			
			ListGrid pickListProperties = nomeAttributoCustomItem.getPickListProperties();
			pickListProperties.addFetchDataHandler(new FetchDataHandler() {
	
				@Override
				public void onFilterData(FetchDataEvent event) {
					GWTRestDataSource nomiAttributiCustomDS = (GWTRestDataSource) nomeAttributoCustomItem.getOptionDataSource();
					nomiAttributiCustomDS.addParam("nomeTabella", ((AssociazioniAttributiCustomItem) getItem()).getNomeTabella());
					nomiAttributiCustomDS.addParam("idEntitaAssociata", ((AssociazioniAttributiCustomItem) getItem()).getIdEntitaAssociata());
					nomiAttributiCustomDS.addParam("appartenenza", ((AssociazioniAttributiCustomItem) getItem()).getNomeAttributoComplex());
					boolean flgComplex = mDynamicForm.getValue("flgComplex") != null && (Boolean) mDynamicForm.getValue("flgComplex");
					nomiAttributiCustomDS.addParam("flgComplex", flgComplex ? "1" : "");
					boolean flgRipetibile = mDynamicForm.getValue("flgRipetibile") != null && (Boolean) mDynamicForm.getValue("flgRipetibile");
					nomiAttributiCustomDS.addParam("flgRipetibile", flgRipetibile ? "1" : "");
					nomeAttributoCustomItem.setOptionDataSource(nomiAttributiCustomDS);
					nomeAttributoCustomItem.invalidateDisplayValueCache();
				}
			});
			nomeAttributoCustomItem.setPickListProperties(pickListProperties);
			RequiredIfValidator nomeAttributoCustomRequiredIfValidator = new RequiredIfValidator(new RequiredIfFunction() {
				
				@Override
				public boolean execute(FormItem formItem, Object value) {
					return true;
				}
			});
			nomeAttributoCustomRequiredIfValidator.setErrorMessage("Profilatura non completa: manca l'associazione con l'attributo");
			nomeAttributoCustomItem.setValidators(nomeAttributoCustomRequiredIfValidator);
			nomeAttributoCustomItem.setShowIfCondition(new FormItemIfFunction() {
				
				@Override
				public boolean execute(FormItem item, Object value, DynamicForm form) {
					if(nomeAttributoCustomItem.getSelectedRecord() != null) {
						String prompt = nomeAttributoCustomItem.getSelectedRecord().getAttribute("key");
						if(nomeAttributoCustomItem.getSelectedRecord().getAttribute("value") != null && !"".equals(nomeAttributoCustomItem.getSelectedRecord().getAttribute("value"))) {
							prompt += " (" + nomeAttributoCustomItem.getSelectedRecord().getAttribute("value") + ")";
						}
						nomeAttributoCustomItem.setPrompt(prompt);
					} else {
						nomeAttributoCustomItem.setPrompt((String) value);
					}
					return isTipoAttributoCustomSelected() && !isAssociazioneSottoAttributoLibero();
				}
			});
			nomeAttributoCustomItem.addChangedHandler(new ChangedHandler() {
				
				@Override
				public void onChanged(ChangedEvent event) {
					Record record = new Record(mDynamicForm.getValues());
					RecordList listaAssociazioniSottoAttributiComplex = record.getAttributeAsRecordList("listaAssociazioniSottoAttributiComplex");
					if (listaAssociazioniSottoAttributiComplex != null) {
						for(int i = 0; i < listaAssociazioniSottoAttributiComplex.getLength(); i++) {
							listaAssociazioniSottoAttributiComplex.get(i).setAttribute("nomeAttributoCustom", (String) null);
						}
					}
					record.setAttribute("listaAssociazioniSottoAttributiComplex", listaAssociazioniSottoAttributiComplex);
					editRecord(record);			
					mDynamicForm.markForRedraw();
				}
			});
		}
		
		nomeAttributoLiberoItem = new ExtendedTextItem("nomeAttributoLibero");
		nomeAttributoLiberoItem.setShowTitle(false);
		nomeAttributoLiberoItem.setLength(300);
		nomeAttributoLiberoItem.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				// Se sto associando un sotto attributo libero non è visibile
				boolean isVisible = !isTipoAttributoCustomSelected() && !isAssociazioneSottoAttributoLibero();
				if (isVisible && !((AssociazioniAttributiCustomItem) getItem()).isAssociazioneSottoAttributo()) {
//					// Se sto associando un attributo libero il nome dell'attributo libero è il nome della variabile del modello
					mDynamicForm.setValue("nomeAttributoLibero", mDynamicForm.getValueAsString("nomeVariabileModello"));
				} else if (!isTipoAttributoCustomSelected()) {
//					// Se sto associando un sotto attributo libero il nome dell'attributo libero è col + numero colonna
//					mDynamicForm.setValue("nomeAttributoLibero", "col" + mDynamicForm.getValueAsString("numeroColonnaAttributoLibero"));
					mDynamicForm.setValue("nomeAttributoCustom", "col" + mDynamicForm.getValueAsString("numeroColonnaAttributoLibero"));
				}
				return isVisible;
			}
		});
		
		numeroColonnaAttributoLiberoItem = new ExtendedTextItem("numeroColonnaAttributoLibero", "N° colonna");
		numeroColonnaAttributoLiberoItem.setWidth(50);
		numeroColonnaAttributoLiberoItem.setKeyPressFilter("[0-9]");
		numeroColonnaAttributoLiberoItem.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return isAssociazioneSottoAttributoLibero();
			}
		});
		
		ImgButtonItem dettaglioAttributoCustomButton = new ImgButtonItem("dettaglioAttributoCustomButton", "buttons/view.png", "Visualizza dettaglio attributo custom");
		dettaglioAttributoCustomButton.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				String nomeAttributoCustom = (String) mDynamicForm.getValue("nomeAttributoCustom");
				return nomeAttributoCustom != null && !"".equals(nomeAttributoCustom) && isTipoAttributoCustomSelected();
			}
		});
		dettaglioAttributoCustomButton.addIconClickHandler(new IconClickHandler() {

			@Override
			public void onIconClick(IconClickEvent event) {
				String nomeAttributoCustom = (String) mDynamicForm.getValue("nomeAttributoCustom");
				AttributiCustomWindow attributiCustomWindow = new AttributiCustomWindow(nomeAttributoCustom);
				attributiCustomWindow.show();
			}
		});
		
		
//		ImgButtonItem lookupAttributiCustomButton = new ImgButtonItem("lookupAttributiCustomButton", "lookup/attributiadd.png", "Seleziona da lista attributi custom");
//		lookupAttributiCustomButton.addIconClickHandler(new IconClickHandler() {
//			
//			@Override
//			public void onIconClick(IconClickEvent event) {
//				Record record = null;
//				boolean flgComplex = mDynamicForm.getValue("flgComplex") != null && (Boolean) mDynamicForm.getValue("flgComplex");				
//				String appartenenza = ((AssociazioniAttributiCustomItem) getItem()).getNomeAttributoComplex();
//				if(flgComplex) {
//					record = new Record();					
//					record.setAttribute("tipo", "COMPLEX"); 	
//				} else if(appartenenza != null && !"".equals(appartenenza)) {
//					record = new Record();
//					record.setAttribute("appartenenza", appartenenza);					
//				} 
//				ProfilaturaModelliDocLookupAttributiCustom lookupAttributiCustomPopup = new ProfilaturaModelliDocLookupAttributiCustom(record);
//				lookupAttributiCustomPopup.show();				
//			}
//		});
		
		associazioniSottoAttributiComplexItem = new HiddenItem("listaAssociazioniSottoAttributiComplex");
		
		ImgButtonItem associazioniSottoAttributiComplexButton = new ImgButtonItem("associazioniSottoAttributiComplexButton", "buttons/altriDati.png", "Associazioni con i sotto-attributi");
		associazioniSottoAttributiComplexButton.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				boolean flgComplex = mDynamicForm.getValue("flgComplex") != null && (Boolean) mDynamicForm.getValue("flgComplex");
				String nomeAttributoCustom = (String) mDynamicForm.getValue("nomeAttributoCustom");
				return flgComplex && ((nomeAttributoCustom != null && !"".equals(nomeAttributoCustom)) || "attributoLibero".equalsIgnoreCase(mDynamicForm.getValueAsString("tipoAssociazioneVariabileModello")));
			}
		});
		associazioniSottoAttributiComplexButton.addIconClickHandler(new IconClickHandler() {
			
			@Override
			public void onIconClick(IconClickEvent event) {
				Record record = new Record(mDynamicForm.getValues());
				String title = "";
				String nomeTabella = "";
				String idEntitaAssociata = "";
				if (isTipoAttributoCustomSelected()) {
					nomeTabella = ((AssociazioniAttributiCustomItem) getItem()).getNomeTabella();
					idEntitaAssociata = ((AssociazioniAttributiCustomItem) getItem()).getIdEntitaAssociata();
					title = "Associazioni con i sotto-attributi di "  + mDynamicForm.getValue("nomeAttributoCustom");
				} else {
					title = "Associazioni con i sotto-attributi di "  + mDynamicForm.getValue("nomeAttributoLibero");
				}
				new AssociazioniSottoAttributiComplexPopup(title, nomeTabella, idEntitaAssociata, record) {

					@Override
					public void onOkButtonClick(RecordList listaAssociazioniSottoAttributiComplex) {
						record.setAttribute("listaAssociazioniSottoAttributiComplex", listaAssociazioniSottoAttributiComplex);
						editRecord(record);						
					}
				};
			}
		});
		
		tipoVariabileModelloItem = new SelectItem("tipoAttributo", "Tipo valore");
		LinkedHashMap<String, String> tipoValueMap = new LinkedHashMap<String, String>();
		tipoValueMap.put("CHECK", I18NUtil.getMessages().attributi_custom_select_casella_di_spunta());
		tipoValueMap.put("DATE", I18NUtil.getMessages().attributi_custom_select_data());
		tipoValueMap.put("DATETIME", I18NUtil.getMessages().attributi_custom_select_data_ora());
		tipoValueMap.put("NUMBER", I18NUtil.getMessages().attributi_custom_select_numerico());
		tipoValueMap.put("EURO", I18NUtil.getMessages().attributi_custom_select_importo());
		tipoValueMap.put("TEXT-BOX", I18NUtil.getMessages().attributi_custom_select_stringa());
		tipoValueMap.put("TEXT-AREA", I18NUtil.getMessages().attributi_custom_select_area_testo());
		tipoValueMap.put("CKEDITOR", I18NUtil.getMessages().attributi_custom_select_editorHtml());
		tipoValueMap.put("COMBO-BOX", I18NUtil.getMessages().attributi_custom_select_lista_scelta_popolata());
		tipoValueMap.put("COMPLEX", I18NUtil.getMessages().attributi_custom_select_strutturato());
		tipoValueMap.put("RADIO", I18NUtil.getMessages().attributi_custom_select_radio());
		tipoValueMap.put("IMAGE", I18NUtil.getMessages().attributi_custom_select_image());
		tipoVariabileModelloItem.setValueMap(tipoValueMap);
		tipoVariabileModelloItem.setStartRow(false);
		tipoVariabileModelloItem.setColSpan(1);
		tipoVariabileModelloItem.setWidth(150);
		tipoVariabileModelloItem.setRequired(true);
		
		tipoVariabileModelloItem.setShowIfCondition(new FormItemIfFunction() {

			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return !isTipoAttributoCustomSelected();
			}
			
		});
		
		flgBarcodeItem = new CheckboxItem("flgBarcode", "barcode");
		flgBarcodeItem.setWidth(1);
		flgBarcodeItem.setColSpan(1);
		flgBarcodeItem.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				if(!showFlgBarcode()) {
					flgBarcodeItem.setValue(false);
					return false;
				}
				return true;				
			}
		});
		flgBarcodeItem.addChangedHandler(new ChangedHandler() {
			
			@Override
			public void onChanged(ChangedEvent event) {
				mDynamicForm.markForRedraw();
			}
		});
		
		tipoBarcodeItem = new SelectItem("tipoBarcode");
		LinkedHashMap<String, String> tipoBarcodeValueMap = new LinkedHashMap<String, String>();
		tipoBarcodeValueMap.put("CODE128", "Code 128");
		tipoBarcodeValueMap.put("PDF417", "Pdf417");
//		tipoBarcodeValueMap.put("DATAMATRIX", "DataMatrix");
		tipoBarcodeValueMap.put("QRCODE", "QRCode");
		tipoBarcodeItem.setValueMap(tipoBarcodeValueMap);
		tipoBarcodeItem.setAllowEmptyValue(true);
		tipoBarcodeItem.setShowTitle(false);
		tipoBarcodeItem.setWidth(120);
		tipoBarcodeItem.setColSpan(1);
		tipoBarcodeItem.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				boolean flgBarcode = mDynamicForm.getValue("flgBarcode") != null && (Boolean) mDynamicForm.getValue("flgBarcode");
				return showFlgBarcode() && flgBarcode;				
			}
		});
		
		if (!isAssociazioneSottoAttributoLibero() && isNomeTabellaOrIdEntitaSetted()){
			mDynamicForm.setFields(nomeVariabileModelloItem, aliasVariabileModelloItem, flgComplexItem, flgRipetibileItem, flgImageItem, tipoAssociazioneVariabileModelloItem, nomeAttributoCustomItem, nomeAttributoLiberoItem, numeroColonnaAttributoLiberoItem, /*lookupAttributiCustomButton,*/ dettaglioAttributoCustomButton, associazioniSottoAttributiComplexItem, associazioniSottoAttributiComplexButton, tipoVariabileModelloItem, flgBarcodeItem, tipoBarcodeItem);
		} else {
			mDynamicForm.setFields(nomeVariabileModelloItem, aliasVariabileModelloItem, flgComplexItem, flgRipetibileItem, flgImageItem, tipoAssociazioneVariabileModelloItem, nomeAttributoLiberoItem, numeroColonnaAttributoLiberoItem, /*lookupAttributiCustomButton,*/ dettaglioAttributoCustomButton, associazioniSottoAttributiComplexItem, associazioniSottoAttributiComplexButton, tipoVariabileModelloItem, flgBarcodeItem, tipoBarcodeItem);
		}
				
		addChild(mDynamicForm);
	}
	
	public boolean showFlgBarcode() {
		String nomeAttributoCustom = (String) mDynamicForm.getValue("nomeAttributoCustom");
		boolean flgComplex = mDynamicForm.getValue("flgComplex") != null && (Boolean) mDynamicForm.getValue("flgComplex");
		boolean flgImage = mDynamicForm.getValue("flgImage") != null && (Boolean) mDynamicForm.getValue("flgImage");
		return ((!isTipoAttributoCustomSelected()) || (isTipoAttributoCustomSelected() && nomeAttributoCustom != null && !"".equals(nomeAttributoCustom))) && !flgComplex && flgImage;	
	}
	
	public Record getFormValuesAsRecord() {
		return mDynamicForm.getValuesAsRecord();	
	}
		
	@Override
	public ReplicableCanvasForm[] getForm() {
		return new ReplicableCanvasForm[]{ mDynamicForm };
	}
	
	@Override
	public void editRecord(Record record) {
		super.editRecord(record);
		boolean flgAssConAttributoCustom = record.getAttributeAsBoolean("flgAssConAttributoCustom");
	}
	
	@Override
	public void setCanEdit(Boolean canEdit) {
		super.setCanEdit(canEdit);
		nomeVariabileModelloItem.setCanEdit(false);
		nomeAttributoLiberoItem.setCanEdit(false);
	}
	
	public void setFormValuesFromRecord(Record record) {		
		mDynamicForm.clearErrors(true);
		mDynamicForm.clearValue("nomeAttributoCustom");		
		if (record.getAttribute("nome") != null) {
			mDynamicForm.setValue("nomeAttributoCustom", record.getAttribute("nome"));
			nomeAttributoCustomItem.setTooltip(record.getAttribute("etichetta"));			
			mDynamicForm.markForRedraw();
		}
	}
	
	@Override
	public boolean validate() {
		boolean valid = super.validate();
		boolean flgComplex = mDynamicForm.getValue("flgComplex") != null && (Boolean) mDynamicForm.getValue("flgComplex");
		String nomeAttributoCustom = (String) mDynamicForm.getValue("nomeAttributoCustom");
		if(flgComplex && nomeAttributoCustom != null && !"".equals(nomeAttributoCustom)) {		
			RecordList listaAssociazioniSottoAttributiComplex = new Record(mDynamicForm.getValues()).getAttributeAsRecordList("listaAssociazioniSottoAttributiComplex");
			for(int i = 0; i < listaAssociazioniSottoAttributiComplex.getLength(); i++) {
				String nomeSottoAttributoCustom = listaAssociazioniSottoAttributiComplex.get(i).getAttribute("nomeAttributoCustom");
				if(nomeSottoAttributoCustom == null || "".equals(nomeSottoAttributoCustom)) {
					valid = false;
					mDynamicForm.setFieldErrors("associazioniSottoAttributiComplexButton", "Profilatura non completa: mancano alcune associazioni con i sotto-attributi di " + nomeAttributoCustom);
					break;
				}
			}				
		}
		return valid;
	}
	
	public void reloadCombo() {
		nomeAttributoCustomItem.fetchData();
	}
	
	/*
	public class ProfilaturaModelliDocLookupAttributiCustom extends LookupAttributiCustomPopup {

		public ProfilaturaModelliDocLookupAttributiCustom(Record record) {
			super(record, null, null, true);
		}

		@Override
		public void manageLookupBack(Record record) {
			setFormValuesFromRecord(record);
		}

		@Override
		public void manageMultiLookupBack(Record record) {

		}

		@Override
		public void manageMultiLookupUndo(Record record) {

		}
		
		@Override
		public String getNomeAttributoComplexAppartenenza() {
			return ((AssociazioniAttributiCustomItem) getItem()).getNomeAttributoComplex();			
		};
	}
	*/
	
	private boolean isTipoAttributoCustomSelected() {
		String tipoAssociazioneVariabileModello = mDynamicForm.getValueAsString("tipoAssociazioneVariabileModello");
		return "attributoCustom".equalsIgnoreCase(tipoAssociazioneVariabileModello);
	}
	
	private boolean isNomeTabellaOrIdEntitaSetted() {
		String nomeTabella = ((AssociazioniAttributiCustomItem) getItem()).getNomeTabella();
		String idEntitaAssociata = ((AssociazioniAttributiCustomItem) getItem()).getIdEntitaAssociata();
		return (nomeTabella != null && !"".equalsIgnoreCase(nomeTabella)) || (idEntitaAssociata != null && !"".equalsIgnoreCase(idEntitaAssociata)); 
	}
	
	private boolean isAssociazioneSottoAttributoLibero() {
		boolean isAssociazioneSottoAttributo = ((AssociazioniAttributiCustomItem) getItem()).isAssociazioneSottoAttributo();
		return !isNomeTabellaOrIdEntitaSetted() && isAssociazioneSottoAttributo; 
	}
	
}
