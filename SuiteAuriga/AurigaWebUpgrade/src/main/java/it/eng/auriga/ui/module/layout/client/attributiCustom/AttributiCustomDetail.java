/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.auriga.ui.module.layout.client.editor.CKEditorItem;
import it.eng.auriga.ui.module.layout.client.i18n.I18NUtil;
import it.eng.utility.ui.module.core.client.datasource.GWTRestDataSource;
import it.eng.utility.ui.module.layout.client.common.CustomDetail;
import it.eng.utility.ui.module.layout.client.common.items.CheckboxItem;
import it.eng.utility.ui.module.layout.client.common.items.DateItem;
import it.eng.utility.ui.module.layout.client.common.items.DateTimeItem;
import it.eng.utility.ui.module.layout.client.common.items.FilteredSelectItem;
import it.eng.utility.ui.module.layout.client.common.items.NumericItem;
import it.eng.utility.ui.module.layout.client.common.items.SelectItem;
import it.eng.utility.ui.module.layout.client.common.items.TextAreaItem;
import it.eng.utility.ui.module.layout.client.common.items.TextItem;

import java.util.LinkedHashMap;
import java.util.Map;

import com.google.gwt.regexp.shared.RegExp;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.types.CharacterCasing;
import com.smartgwt.client.types.FieldType;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.FormItemHoverFormatter;
import com.smartgwt.client.widgets.form.FormItemIfFunction;
import com.smartgwt.client.widgets.form.fields.FormItem;
import com.smartgwt.client.widgets.form.fields.HiddenItem;
import com.smartgwt.client.widgets.form.fields.RadioGroupItem;
import com.smartgwt.client.widgets.form.fields.SpacerItem;
import com.smartgwt.client.widgets.form.fields.events.ChangedEvent;
import com.smartgwt.client.widgets.form.fields.events.ChangedHandler;
import com.smartgwt.client.widgets.form.validator.CustomValidator;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.layout.VLayout;

/**
 * 
 * @author DANIELE CRISTIANO
 *
 */

public class AttributiCustomDetail extends CustomDetail {

	protected DynamicForm formDescrizioneAttributo;
	protected DynamicForm formTipoAttributo;
	protected DynamicForm formSottoAttributo;
	protected DynamicForm formDettaglioAttributo;
	protected DynamicForm formDefaultValueCKEditor;

	private HiddenItem rowidItem;
	private TextItem nomeItem;
	private TextItem etichettaItem;
	private SelectItem tipoItem;
	private TextAreaItem descrizioneItem;
	private FilteredSelectItem appartenenzaItem;
	private CheckboxItem flgDaIndicizzareItem;
	private NumericItem maxNumeroCaratteriItem;
	private NumericItem larghezzaVideoCaratteriItem;
	private NumericItem altezzaVideoCaratteriItem;
	private TextItem defaultValueItem;
	private TextAreaItem defaultValueTextAreaItem;
	private CKEditorItem defaultValueCKEditorItem;
	private TextItem formatNumberItem;
	private NumericItem nrDecimaleItem;
	private TextItem maxNumeroCifreItem;
	private TextItem numMinValue;
	private SelectItem caseItem;
	private TextItem espressioneRegolareItem;
	private TextAreaItem attrQueryXValues;
	private DateItem defaultDateValue;
	private DateItem maxDateItem;
	private DateItem minDateItem;
	private DateTimeItem defaultDateTimeItem;
	private DateTimeItem maxDateTimeItem;
	private DateTimeItem minDateTimeItem;
	private NumericItem nroOrdineItem;
	private NumericItem nroRigaInAttrAppInItem;
	private CheckboxItem flgProtectedInItem;
	private CheckboxItem flgValoriUnivociInItem;
	private TextItem uRLWSValoriPossibiliInItem;
	private TextItem xMLInWSValoriPossibiliInItem;
	private OpzioniListaSceltaAttributiCustomItem lOpzioniListaSceltaAttributiCustomItem;
	private RadioGroupItem radioGroupItem;
	private RadioGroupItem tipoEditorHtmlRadioGroupItem;
	private CheckboxItem flgValoreObbligatorioItem;
	
	private SpacerItem spacer1Item;

	protected String mode;

	public AttributiCustomDetail(String nomeEntita) {
		super(nomeEntita);

		setInitializeForms();

		rowidItem = new HiddenItem("rowid");

		nomeItem = new TextItem("nome", setTitleAlign(I18NUtil.getMessages().attributi_custom_nome()));
		nomeItem.setWidth(250);
		nomeItem.setRequired(true);
		nomeItem.setStartRow(true);
		nomeItem.setColSpan(14);
		nomeItem.setCharacterCasing(CharacterCasing.UPPER);

		etichettaItem = new TextItem("etichetta", setTitleAlign(I18NUtil.getMessages().attributi_custom_etichetta()));
		etichettaItem.setStartRow(true);
		etichettaItem.setRequired(true);
		etichettaItem.setWidth(650);
		etichettaItem.setColSpan(14);

		tipoItem = new SelectItem("tipo", setTitleAlign(I18NUtil.getMessages().attributi_custom_tipo()));
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
		tipoItem.setValueMap(tipoValueMap);
		tipoItem.setRequired(true);
		tipoItem.setColSpan(1);
		tipoItem.setAllowEmptyValue(false);
		tipoItem.setStartRow(true);
		tipoItem.addChangedHandler(new ChangedHandler() {

			@Override
			public void onChanged(ChangedEvent event) {
				markForRedraw();
				defaultValueCKEditorItem.redraw();
				if("CKEDITOR".equals(tipoItem.getValueAsString())) {
					buildDefaultValueCKEditorItem();
					formDefaultValueCKEditor.setItems(spacer1Item, defaultValueCKEditorItem);
					formDefaultValueCKEditor.show();
				} else {
					formDefaultValueCKEditor.setItems(new FormItem());
					formDefaultValueCKEditor.hide();
				}
			}
		});
		
		tipoEditorHtmlRadioGroupItem = new RadioGroupItem("tipoEditorHtml");
		tipoEditorHtmlRadioGroupItem.setTitle(I18NUtil.getMessages().attributi_custom_scelta_tipologia_editorHtml());
		tipoEditorHtmlRadioGroupItem.setVertical(false);
		tipoEditorHtmlRadioGroupItem.setStartRow(true);
		Map<String, String> valueMap = new LinkedHashMap<>();
		valueMap.put("RESTRICTED", "Ristretta");
		valueMap.put("STANDARD", "Standard");
		valueMap.put("EXTENDED", "Estesa");
		tipoEditorHtmlRadioGroupItem.setValueMap(valueMap);
		tipoEditorHtmlRadioGroupItem.setDefaultValue("STANDARD");
		tipoEditorHtmlRadioGroupItem.setShowIfCondition(new FormItemIfFunction() {

			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return "CKEDITOR".equals(tipoItem.getValueAsString());
			}
		});
		tipoEditorHtmlRadioGroupItem.addChangedHandler(new ChangedHandler() {

			@Override
			public void onChanged(ChangedEvent event) {
				markForRedraw();
			}
		});
		
		radioGroupItem = new RadioGroupItem("tipoLista");
		radioGroupItem.setTitle(I18NUtil.getMessages().attributi_custom_scelta_tipologia_lista());
		radioGroupItem.setVertical(false);
		radioGroupItem.setStartRow(true);
		radioGroupItem.setValueMap("Opzione", "Query", "WebService");
		radioGroupItem.setDefaultValue("Opzione");
		radioGroupItem.setShowIfCondition(new FormItemIfFunction() {

			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return "COMBO-BOX".equals(tipoItem.getValueAsString());
			}
		});
		radioGroupItem.addChangedHandler(new ChangedHandler() {

			@Override
			public void onChanged(ChangedEvent event) {
				markForRedraw();
			}
		});

		lOpzioniListaSceltaAttributiCustomItem = new OpzioniListaSceltaAttributiCustomItem() {

			@Override
			public boolean isNewMode() {
				return (getLayout().getMode() != null && "new".equals(getLayout().getMode()));
			}
		};
		lOpzioniListaSceltaAttributiCustomItem.setName("xmlValoriPossibiliIn");
		lOpzioniListaSceltaAttributiCustomItem.setTitle(I18NUtil.getMessages().attributi_custom_opzioni_possibili());
		lOpzioniListaSceltaAttributiCustomItem.setStartRow(true);
		lOpzioniListaSceltaAttributiCustomItem.setShowIfCondition(new FormItemIfFunction() {

			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return ((("COMBO-BOX".equals(tipoItem.getValueAsString()) && radioGroupItem.getValueAsString().equals("Opzione"))) || (("RADIO".equals(tipoItem
						.getValueAsString()))));

			}
		});
		lOpzioniListaSceltaAttributiCustomItem.addChangedHandler(new ChangedHandler() {

			@Override
			public void onChanged(ChangedEvent event) {
				markForRedraw();
			}
		});

		attrQueryXValues = new TextAreaItem("attrQueryXValues", I18NUtil.getMessages().attributi_custom_query_valori_possibili());
		attrQueryXValues.setStartRow(true);
		attrQueryXValues.setLength(4000);
		attrQueryXValues.setHeight(40);
		attrQueryXValues.setWidth(650);
		attrQueryXValues.setColSpan(14);
		attrQueryXValues.setShowIfCondition(new FormItemIfFunction() {

			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return "COMBO-BOX".equals(tipoItem.getValueAsString()) && radioGroupItem.getValueAsString().equals("Query")
						&& !"RADIO".equals(tipoItem.getValueAsString());
			}
		});
		attrQueryXValues.addChangedHandler(new ChangedHandler() {

			@Override
			public void onChanged(ChangedEvent event) {
				markForRedraw();
			}
		});

		uRLWSValoriPossibiliInItem = new TextItem("uRLWSValoriPossibiliIn", "End-point web-service");
		uRLWSValoriPossibiliInItem.setWidth(250);
		uRLWSValoriPossibiliInItem.setStartRow(true);
		uRLWSValoriPossibiliInItem.setColSpan(13);
		uRLWSValoriPossibiliInItem.setRequired(false);
		uRLWSValoriPossibiliInItem.setShowIfCondition(new FormItemIfFunction() {

			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return "COMBO-BOX".equals(tipoItem.getValueAsString()) && radioGroupItem.getValueAsString().equals("WebService")
						&& !"RADIO".equals(tipoItem.getValueAsString());
			}
		});
		uRLWSValoriPossibiliInItem.addChangedHandler(new ChangedHandler() {

			@Override
			public void onChanged(ChangedEvent event) {
				markForRedraw();
			}
		});

		xMLInWSValoriPossibiliInItem = new TextItem("xMLInWSValoriPossibiliIn", "Input web-service");
		xMLInWSValoriPossibiliInItem.setWidth(250);
		xMLInWSValoriPossibiliInItem.setStartRow(true);
		xMLInWSValoriPossibiliInItem.setColSpan(13);
		xMLInWSValoriPossibiliInItem.setShowIfCondition(new FormItemIfFunction() {

			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return "COMBO-BOX".equals(tipoItem.getValueAsString()) && radioGroupItem.getValueAsString().equals("WebService")
						&& !"RADIO".equals(tipoItem.getValueAsString());
			}
		});

		descrizioneItem = new TextAreaItem("descrizione", setTitleAlign(I18NUtil.getMessages().attributi_custom_descrizione()));
		descrizioneItem.setStartRow(true);
		descrizioneItem.setLength(4000);
		descrizioneItem.setHeight(60);
		descrizioneItem.setWidth(650);
		descrizioneItem.setColSpan(14);

		nroOrdineItem = new NumericItem("nroOrdine", setTitleAlign(I18NUtil.getMessages().attributi_custom_ordine_num()));
		nroOrdineItem.setColSpan(1);
		nroOrdineItem.setStartRow(true);
		nroOrdineItem.setShowIfCondition(new FormItemIfFunction() {

			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return !"".equals(appartenenzaItem.getValueAsString()) && appartenenzaItem.getValueAsString() != null;
			}
		});

		nroRigaInAttrAppInItem = new NumericItem("nroRigaInAttrAppIn", "Riga N°");
		nroRigaInAttrAppInItem.setColSpan(1);
		nroRigaInAttrAppInItem.setShowIfCondition(new FormItemIfFunction() {

			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				Record record = new Record(vm.getValues());
				if (record.getAttribute("nroOrdine") != null && !"".equals(record.getAttribute("nroOrdine"))) {
					nroRigaInAttrAppInItem.setRequired(true);
				} else {
					nroRigaInAttrAppInItem.setRequired(false);
				}
				return !"".equals(appartenenzaItem.getValueAsString()) && appartenenzaItem.getValueAsString() != null;
			}
		});

		GWTRestDataSource attrCustomComplexDS = new GWTRestDataSource("AttributiCustomComplexDataSource", "key", FieldType.TEXT, true);
		ListGridField nomeField = new ListGridField("key", I18NUtil.getMessages().attributi_custom_nome());
		ListGridField etichettaField = new ListGridField("value", I18NUtil.getMessages().attributi_custom_etichetta());
		appartenenzaItem = new FilteredSelectItem("appartenenza", setTitleAlign(I18NUtil.getMessages().attributi_custom_sotto_attributo_di())) {

			@Override
			public void onOptionClick(Record record) {
				super.onOptionClick(record);
				markForRedraw();
			}

			@Override
			public void setValue(String value) {
				super.setValue(value);
				markForRedraw();
			}

			@Override
			protected void clearSelect() {
				super.clearSelect();
				markForRedraw();
			};
		};
		appartenenzaItem.setPickListFields(nomeField, etichettaField);
		appartenenzaItem.setWidth(450);
		appartenenzaItem.setStartRow(true);
		appartenenzaItem.setClearable(true);
		appartenenzaItem.setValueField("key");
		appartenenzaItem.setDisplayField("value");
		appartenenzaItem.setColSpan(1);
		appartenenzaItem.setOptionDataSource(attrCustomComplexDS);
		appartenenzaItem.setShowIfCondition(new FormItemIfFunction() {

			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return !"COMPLEX".equals(tipoItem.getValueAsString());
			}
		});
		appartenenzaItem.addChangedHandler(new ChangedHandler() {

			@Override
			public void onChanged(ChangedEvent event) {
				markForRedraw();
			}
		});

		flgProtectedInItem = new CheckboxItem("flgProtectedIn", I18NUtil.getMessages().attributi_custom_dati_sensibili());
		flgProtectedInItem.setStartRow(false);
		flgProtectedInItem.setColSpan(1);
		flgProtectedInItem.setWidth(10);
		flgProtectedInItem.addChangedHandler(new ChangedHandler() {

			@Override
			public void onChanged(ChangedEvent event) {
				markForRedraw();
			}
		});

		flgValoriUnivociInItem = new CheckboxItem("flgValoriUnivociIn", I18NUtil.getMessages().attributi_custom_valori_unici());
		flgValoriUnivociInItem.setStartRow(false);
		flgValoriUnivociInItem.setColSpan(1);
		flgValoriUnivociInItem.setWidth(10);
		flgValoriUnivociInItem.addChangedHandler(new ChangedHandler() {

			@Override
			public void onChanged(ChangedEvent event) {
				markForRedraw();
			}
		});

		flgValoreObbligatorioItem = new CheckboxItem("flgValoreObbligatorio", I18NUtil.getMessages().attributi_custom_valore_obbligatorio());
		flgValoreObbligatorioItem.setStartRow(false);
		flgValoreObbligatorioItem.setColSpan(1);
		flgValoreObbligatorioItem.setWidth(10);
		flgValoreObbligatorioItem.setShowIfCondition(new FormItemIfFunction() {

			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				
				return !"".equals(appartenenzaItem.getValueAsString()) && appartenenzaItem.getValueAsString() != null;
			}
		});
		flgValoreObbligatorioItem.addChangedHandler(new ChangedHandler() {

			@Override
			public void onChanged(ChangedEvent event) {
				markForRedraw();
			}
		});

		flgDaIndicizzareItem = new CheckboxItem("flgDaIndicizzare", I18NUtil.getMessages().attributi_custom_da_indicizzare());
		flgDaIndicizzareItem.setColSpan(1);
		flgDaIndicizzareItem.addChangedHandler(new ChangedHandler() {

			@Override
			public void onChanged(ChangedEvent event) {
				markForRedraw();
			}
		});
		flgDaIndicizzareItem.setShowIfCondition(new FormItemIfFunction() {

			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return "TEXT-BOX".equals(tipoItem.getValueAsString()) || "TEXT-AREA".equals(tipoItem.getValueAsString())
						|| "COMBO-BOX".equals(tipoItem.getValueAsString()) || "CKEDITOR".equals(tipoItem.getValueAsString());
			}
		});

		maxNumeroCaratteriItem = new NumericItem("maxNumeroCaratteriItem", setTitleAlign(I18NUtil.getMessages().attributi_custom_nro_cifre_caratteri()));
		maxNumeroCaratteriItem.setWidth(100);
		maxNumeroCaratteriItem.setStartRow(true);
		maxNumeroCaratteriItem.setShowIfCondition(new FormItemIfFunction() {

			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return "TEXT-BOX".equals(tipoItem.getValueAsString()) || "TEXT-AREA".equals(tipoItem.getValueAsString())
						|| "NUMBER".equals(tipoItem.getValueAsString()) || "EURO".equals(tipoItem.getValueAsString()) || "CKEDITOR".equals(tipoItem.getValueAsString());
			}
		});

		larghezzaVideoCaratteriItem = new NumericItem("larghezzaVideoCaratteriItem", I18NUtil.getMessages().attributi_custom_largh_video());
		larghezzaVideoCaratteriItem.setWidth(100);
		larghezzaVideoCaratteriItem.setColSpan(1);
		larghezzaVideoCaratteriItem.setShowIfCondition(new FormItemIfFunction() {

			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return "TEXT-BOX".equals(tipoItem.getValueAsString()) || "TEXT-AREA".equals(tipoItem.getValueAsString())
						|| "EURO".equals(tipoItem.getValueAsString()) || "NUMBER".equals(tipoItem.getValueAsString()) || "COMBO-BOX".equals(tipoItem.getValueAsString()) || "CKEDITOR".equals(tipoItem.getValueAsString());
			}
		});

		altezzaVideoCaratteriItem = new NumericItem("altezzaVideoCaratteriItem", I18NUtil.getMessages().attributi_custom_altezza_video());
		altezzaVideoCaratteriItem.setWidth(100);
		altezzaVideoCaratteriItem.setColSpan(1);
		altezzaVideoCaratteriItem.setStartRow(true);
		altezzaVideoCaratteriItem.setShowIfCondition(new FormItemIfFunction() {

			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return "TEXT-AREA".equals(tipoItem.getValueAsString()) || "CKEDITOR".equals(tipoItem.getValueAsString());
			}
		});

		caseItem = new SelectItem("caseItem", setTitleAlign(I18NUtil.getMessages().attributi_custom_case()));
		LinkedHashMap<String, String> caseValueMap = new LinkedHashMap<String, String>();
		caseValueMap.put("UPPER", I18NUtil.getMessages().attributi_custom_tutto_maiuscolo());
		caseValueMap.put("LOWER", I18NUtil.getMessages().attributi_custom_tutto_minuscolo());
		caseItem.setValueMap(caseValueMap);
		caseItem.setAllowEmptyValue(true);
		caseItem.setWidth(250);
		caseItem.setStartRow(true);
		caseItem.setShowIfCondition(new FormItemIfFunction() {

			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return "TEXT-BOX".equals(tipoItem.getValueAsString()) || "TEXT-AREA".equals(tipoItem.getValueAsString());
			}
		});

		espressioneRegolareItem = new TextItem("espressioneRegolareItem", setTitleAlign(I18NUtil.getMessages().attributi_custom_espressione_regolare()));
		espressioneRegolareItem.setWidth(400);
		espressioneRegolareItem.setColSpan(12);
		espressioneRegolareItem.setStartRow(true);
		espressioneRegolareItem.setShowIfCondition(new FormItemIfFunction() {

			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return "TEXT-BOX".equals(tipoItem.getValueAsString());
			}
		});

		formatNumberItem = new TextItem("formatNumberItem", setTitleAlign(I18NUtil.getMessages().attributi_custom_formato()));
		formatNumberItem.setWidth(100);
		formatNumberItem.setStartRow(true);
		formatNumberItem.setShowIfCondition(new FormItemIfFunction() {

			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return "NUMBER".equals(tipoItem.getValueAsString());
			}
		});
		formatNumberItem.setItemHoverFormatter(true, new FormItemHoverFormatter() {

			@Override
			public String getHoverHTML(FormItem item, DynamicForm form) {

				String hover1 = "'-.'&nbsp;Nessun&nbsp;separatore&nbsp;per&nbsp;gli&nbsp;interi&nbsp;/&nbsp;punto&nbsp;per&nbsp;i&nbsp;decimali&nbsp;&nbsp;<br/>";
				String hover2 = "'-,'&nbsp;Nessun&nbsp;separatore&nbsp;per&nbsp;gli&nbsp;interi&nbsp;/&nbsp;virgola&nbsp;per&nbsp;i&nbsp;decimali<br/>";
				String hover3 = "'.-,'&nbsp;Punto&nbsp;per&nbsp;le&nbsp;migliaia&nbsp;/&nbsp;virgola&nbsp;per&nbsp;i&nbsp;decimali<br/>";
				String hover4 = "',-.'&nbsp;Virgola&nbsp;per&nbsp;le&nbsp;migliaia&nbsp;/&nbsp;punto&nbsp;per&nbsp;i&nbsp;decimali<br/>";
				return hover1 + "\n" + hover2 + "\n" + hover3 + "\n" + hover4;
			}
		});

		nrDecimaleItem = new NumericItem("nrDecimaleItem", I18NUtil.getMessages().attributi_custom_nro_decimali());
		nrDecimaleItem.setWidth(100);
		nrDecimaleItem.setColSpan(1);
		nrDecimaleItem.setShowIfCondition(new FormItemIfFunction() {

			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return "NUMBER".equals(tipoItem.getValueAsString()) || "EURO".equals(tipoItem.getValueAsString());
			}
		});

		defaultValueItem = new TextItem("defaultValue", setTitleAlign(I18NUtil.getMessages().attributi_custom_valore_default()));
		defaultValueItem.setWidth(100);
		defaultValueItem.setStartRow(true);
		defaultValueItem.setColSpan(1);
		defaultValueItem.setShowIfCondition(new FormItemIfFunction() {

			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				if ("NUMBER".equals(tipoItem.getValueAsString()) || "EURO".equals(tipoItem.getValueAsString())) {
					defaultValueItem.setWidth(100);
					return true;
				} else if ("TEXT-BOX".equals(tipoItem.getValueAsString())) {
					defaultValueItem.setWidth(300);
					return true;
				/*} else if ("TEXT-AREA".equals(tipoItem.getValueAsString())) {
					defaultValueItem.setWidth(300);
					return true;
				} else if ("CKEDITOR".equals(tipoItem.getValueAsString())) {
					defaultValueItem.setWidth(300);
					return true;*/
				}
				return false;
			}
		});
		CustomValidator defaultValueValidator = new CustomValidator() {
			
			@Override
			protected boolean condition(Object value) {
				if("EURO".equals(tipoItem.getValueAsString())) {
					String regExpImporto = "^([0-9]{1,3}((\\.)?[0-9]{3})*(,[0-9]{1,2})?)$";
					RegExp regExp = RegExp.compile(regExpImporto);
					if(value != null && !"".equals(value)) {
						return regExp.test((String) value);
					} else {
						return true;
					}
				} else if ("NUMBER".equals(tipoItem.getValueAsString())) {
					String regExpDouble = "[0-9]{1,13}(\\\\\\\\.[0-9]*)?";
					RegExp regExp = RegExp.compile(regExpDouble);
					if(value != null && !"".equals(value)) {
						return 	regExp.test((String) value);
					}
					return true;
				}
				return true;
			}
		};
		defaultValueValidator.setErrorMessage(getDefaultValueValidatorError());
		defaultValueItem.setValidators(defaultValueValidator);
		defaultValueItem.setValidateOnChange(true);

		defaultValueTextAreaItem = new TextAreaItem("defaultTextAreaValue", setTitleAlign(I18NUtil.getMessages().attributi_custom_valore_default()));
		defaultValueTextAreaItem.setStartRow(true);
		defaultValueTextAreaItem.setLength(4000);
		defaultValueTextAreaItem.setHeight(260);
		defaultValueTextAreaItem.setWidth(650);
		defaultValueTextAreaItem.setColSpan(14);
		defaultValueTextAreaItem.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				if ("TEXT-AREA".equals(tipoItem.getValueAsString())) {
					return true;
				} else 
					return false;
			}
		});
		
		buildDefaultValueCKEditorItem();
		
		spacer1Item = new SpacerItem();
		spacer1Item.setWidth(100);
		spacer1Item.setColSpan(2);
		spacer1Item.setStartRow(true);
		
		maxNumeroCifreItem = new TextItem("maxNumeroCifreItem", I18NUtil.getMessages().attributi_custom_max_valore());
		maxNumeroCifreItem.setWidth(100);
		maxNumeroCifreItem.setColSpan(1);
		maxNumeroCifreItem.setShowIfCondition(new FormItemIfFunction() {

			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return "EURO".equals(tipoItem.getValueAsString()) || "NUMBER".equals(tipoItem.getValueAsString());
			}
		});
		CustomValidator maxNumeroCifreValidator = new CustomValidator() {
			
			@Override
			protected boolean condition(Object value) {
				if("EURO".equals(tipoItem.getValueAsString())) {
					String regExpImporto = "^([0-9]{1,3}((\\.)?[0-9]{3})*(,[0-9]{1,2})?)$";
					RegExp regExp = RegExp.compile(regExpImporto);
					if(value != null && !"".equals(value)) {
						return regExp.test((String) value);
					} else {
						return true;
					}
				} else if ("NUMBER".equals(tipoItem.getValueAsString())) {
					String regExpDouble = "[0-9]{1,13}(\\\\\\\\.[0-9]*)?";
					RegExp regExp = RegExp.compile(regExpDouble);
					if(value != null && !"".equals(value)) {
						return 	regExp.test((String) value);
					}
					return true;
				}
				return true;
			}
		};
		maxNumeroCifreValidator.setErrorMessage(getDefaultValueValidatorError());
		maxNumeroCifreItem.setValidators(maxNumeroCifreValidator);
		maxNumeroCifreItem.setValidateOnChange(true);

		numMinValue = new TextItem("numMinValue", I18NUtil.getMessages().attributi_custom_min_valore());
		numMinValue.setWidth(100);
		numMinValue.setColSpan(1);
		numMinValue.setShowIfCondition(new FormItemIfFunction() {

			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return "NUMBER".equals(tipoItem.getValueAsString()) || "EURO".equals(tipoItem.getValueAsString());
			}
		});
		CustomValidator numMinValueValidator = new CustomValidator() {
			
			@Override
			protected boolean condition(Object value) {
				if("EURO".equals(tipoItem.getValueAsString())) {
					String regExpImporto = "^([0-9]{1,3}((\\.)?[0-9]{3})*(,[0-9]{1,2})?)$";
					RegExp regExp = RegExp.compile(regExpImporto);
					if(value != null && !"".equals(value)) {
						return regExp.test((String) value);
					} else {
						return true;
					}
				} else if ("NUMBER".equals(tipoItem.getValueAsString())) {
					String regExpDouble = "[0-9]{1,13}(\\\\\\\\.[0-9]*)?";
					RegExp regExp = RegExp.compile(regExpDouble);
					if(value != null && !"".equals(value)) {
						return 	regExp.test((String) value);
					}
					return true;
				}
				return true;
			}
		};
		numMinValueValidator.setErrorMessage(getDefaultValueValidatorError());
		numMinValue.setValidators(numMinValueValidator);
		numMinValue.setValidateOnChange(true);

		/*
		 * DATA SENZA ORA
		 */

		defaultDateValue = new DateItem("defaultDateValueItem", setTitleAlign(I18NUtil.getMessages().attributi_custom_valore_default()));
		defaultDateValue.setStartRow(true);
		defaultDateValue.setShowIfCondition(new FormItemIfFunction() {

			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return "DATE".equals(tipoItem.getValueAsString());

			}
		});

		minDateItem = new DateItem("minDateValueItem", I18NUtil.getMessages().attributi_custom_min_valore());
		minDateItem.setShowIfCondition(new FormItemIfFunction() {

			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return "DATE".equals(tipoItem.getValueAsString());
			}
		});

		maxDateItem = new DateItem("maxDateValueItem", I18NUtil.getMessages().attributi_custom_max_valore());
		maxDateItem.setShowIfCondition(new FormItemIfFunction() {

			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return "DATE".equals(tipoItem.getValueAsString());
			}
		});

		/*
		 * DATA CON ORA
		 */

		defaultDateTimeItem = new DateTimeItem("defaultDateTimeValueItem", setTitleAlign(I18NUtil.getMessages().attributi_custom_valore_default()));
		defaultDateTimeItem.setStartRow(true);
		defaultDateTimeItem.setShowIfCondition(new FormItemIfFunction() {

			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return "DATETIME".equals(tipoItem.getValueAsString());
			}
		});

		minDateTimeItem = new DateTimeItem("minDateTimeValueItem", I18NUtil.getMessages().attributi_custom_min_valore());
		minDateTimeItem.setShowIfCondition(new FormItemIfFunction() {

			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return "DATETIME".equals(tipoItem.getValueAsString());
			}
		});

		maxDateTimeItem = new DateTimeItem("maxDateTimeValueItem", I18NUtil.getMessages().attributi_custom_max_valore());
		maxDateTimeItem.setShowIfCondition(new FormItemIfFunction() {

			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return "DATETIME".equals(tipoItem.getValueAsString());
			}
		});

		formDescrizioneAttributo.setItems(rowidItem, nomeItem, etichettaItem, descrizioneItem);

		formTipoAttributo.setItems(tipoItem, flgProtectedInItem, flgValoriUnivociInItem, flgValoreObbligatorioItem);

		formSottoAttributo.setItems(appartenenzaItem, flgDaIndicizzareItem);

		formDefaultValueCKEditor.setFields(spacer1Item, defaultValueCKEditorItem);
		
		formDettaglioAttributo.setItems(nroOrdineItem, nroRigaInAttrAppInItem, maxNumeroCaratteriItem, larghezzaVideoCaratteriItem, formatNumberItem,
				nrDecimaleItem, altezzaVideoCaratteriItem, numMinValue, maxNumeroCifreItem, defaultDateValue, minDateItem, maxDateItem,
				defaultDateTimeItem, minDateTimeItem, maxDateTimeItem, caseItem, espressioneRegolareItem, tipoEditorHtmlRadioGroupItem, radioGroupItem, attrQueryXValues,
				uRLWSValoriPossibiliInItem, xMLInWSValoriPossibiliInItem, lOpzioniListaSceltaAttributiCustomItem, defaultValueItem, defaultValueTextAreaItem);

		// LAYOUT MAIN
		VLayout lVLayout = new VLayout();
		lVLayout.setWidth100();
		lVLayout.setHeight(50);
		
		VLayout lVLayoutSpacer = new VLayout();
		lVLayoutSpacer.setWidth100();
		lVLayoutSpacer.setHeight(50);

		lVLayout.addMember(formDescrizioneAttributo);
		lVLayout.addMember(formTipoAttributo);
		lVLayout.addMember(formSottoAttributo);
		lVLayout.addMember(formDettaglioAttributo);
		lVLayout.addMember(formDefaultValueCKEditor);

		addMember(lVLayout);
		addMember(lVLayoutSpacer);

	}

	private void setInitializeForms() {
		formDescrizioneAttributo = new DynamicForm();
		formDescrizioneAttributo.setValuesManager(vm);
		formDescrizioneAttributo.setHeight("5");
		formDescrizioneAttributo.setPadding(5);
		formDescrizioneAttributo.setWrapItemTitles(false);
		formDescrizioneAttributo.setNumCols(15);
		formDescrizioneAttributo.setColWidths("1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "*", "*");

		formDettaglioAttributo = new DynamicForm();
		formDettaglioAttributo.setValuesManager(vm);
		formDettaglioAttributo.setHeight("5");
		formDettaglioAttributo.setPadding(5);
		formDettaglioAttributo.setWrapItemTitles(false);
		formDettaglioAttributo.setNumCols(15);
		formDettaglioAttributo.setColWidths("1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "*", "*");
		
		formDefaultValueCKEditor = new DynamicForm();
		formDefaultValueCKEditor.setValuesManager(vm);
		formDefaultValueCKEditor.setHeight("5");
		formDefaultValueCKEditor.setPadding(5);
		formDefaultValueCKEditor.setWrapItemTitles(false);
		formDefaultValueCKEditor.setNumCols(15);
		formDefaultValueCKEditor.setColWidths("1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "*", "*");

		formTipoAttributo = new DynamicForm();
		formTipoAttributo.setValuesManager(vm);
		formTipoAttributo.setHeight("5");
		formTipoAttributo.setPadding(5);
		formTipoAttributo.setWrapItemTitles(false);
		formTipoAttributo.setNumCols(15);
		formTipoAttributo.setColWidths("1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "*", "*");

		formSottoAttributo = new DynamicForm();
		formSottoAttributo.setValuesManager(vm);
		formSottoAttributo.setHeight("5");
		formSottoAttributo.setPadding(5);
		formSottoAttributo.setWrapItemTitles(false);
		formSottoAttributo.setNumCols(15);
		formSottoAttributo.setColWidths("1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "*", "*");
	}

	@Override
	public void editNewRecord(Map initialValues) {
		super.editNewRecord(initialValues);
		comboAppartenenzaEditNewRecord(initialValues);
	}

	@Override
	public void editRecord(Record record) {
		super.editRecord(record);
		comboAppartenenzaEditRecord(record);
	}

	private void comboAppartenenzaEditNewRecord(Map initialValues) {
		GWTRestDataSource listaDataDS = (GWTRestDataSource) appartenenzaItem.getOptionDataSource();
		if (initialValues.get("nome") != null && !"".equals(initialValues.get("nome"))) {
			listaDataDS.addParam("nomeAttrApp", (String) initialValues.get("nome"));
		} else {
			listaDataDS.addParam("nomeAttrApp", null);
		}
		appartenenzaItem.setOptionDataSource(listaDataDS);
		appartenenzaItem.fetchData();
	}

	private void comboAppartenenzaEditRecord(Record record) {
		GWTRestDataSource listaDataDS = (GWTRestDataSource) appartenenzaItem.getOptionDataSource();
		if (record.getAttribute("nome") != null && !"".equals(record.getAttributeAsString("nome"))) {
			listaDataDS.addParam("nomeAttrApp", record.getAttributeAsString("nome"));
		} else {
			listaDataDS.addParam("nomeAttrApp", null);
		}
		appartenenzaItem.setOptionDataSource(listaDataDS);
		appartenenzaItem.fetchData();
	}

	private String setTitleAlign(String title) {
		return "<span style=\"width: 150px; display: inline-block;\">" + title + "</span>";
	}

	private void buildDefaultValueCKEditorItem() {
		defaultValueCKEditorItem = new CKEditorItem("defaultCKeditorValue", 4000, "STANDARD", 10);
//		defaultValueCKEditorItem.setStartRow(true);
		defaultValueCKEditorItem.setColSpan(14);
		defaultValueCKEditorItem.setTitle(I18NUtil.getMessages().attributi_custom_valore_default());
		defaultValueCKEditorItem.setShowTitle(true);
		defaultValueCKEditorItem.setWidth(650);
//		defaultValueCKEditorItem.setVisible("CKEDITOR".equals(tipoItem.getValueAsString()));
		defaultValueCKEditorItem.setShowIfCondition(new FormItemIfFunction() {

			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				if ("CKEDITOR".equals(tipoItem.getValueAsString())) {
					return true;
				} else 
					return false;
			}
		});
	}
	
	@Override
	public Record getRecordToSave() {
		Record lRecord = new Record(vm.getValues());
		if(defaultValueCKEditorItem != null && defaultValueCKEditorItem.getValue() != null) {
			String defaultCKeditorValue = defaultValueCKEditorItem.getValue();
			lRecord.setAttribute("defaultCKeditorValue", defaultCKeditorValue);
		}
		return lRecord;
	}
	
	public String getDefaultValueValidatorError() {
//		if("EURO".equals(tipoItem.getValueAsString())) {
//			return "Valore importo non valido";
//		} else if("NUMBER".equals(tipoItem.getValueAsString())) {
//			return "Valore numerico non valido";
//		}
		return "Valore non valido";
	}
}
