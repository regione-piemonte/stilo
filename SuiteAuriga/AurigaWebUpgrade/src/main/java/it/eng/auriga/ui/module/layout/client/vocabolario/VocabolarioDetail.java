/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.Map;

import com.smartgwt.client.data.AdvancedCriteria;
import com.smartgwt.client.data.Criteria;
import com.smartgwt.client.data.Criterion;
import com.smartgwt.client.data.DSCallback;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.types.FieldType;
import com.smartgwt.client.types.OperatorId;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.FormItemIfFunction;
import com.smartgwt.client.widgets.form.fields.FormItem;
import com.smartgwt.client.widgets.form.fields.FormItemCriteriaFunction;
import com.smartgwt.client.widgets.form.fields.FormItemFunctionContext;
import com.smartgwt.client.widgets.form.fields.HiddenItem;
import com.smartgwt.client.widgets.form.fields.events.ChangedEvent;
import com.smartgwt.client.widgets.form.fields.events.ChangedHandler;
import com.smartgwt.client.widgets.form.validator.RequiredIfFunction;
import com.smartgwt.client.widgets.form.validator.RequiredIfValidator;
import com.smartgwt.client.widgets.grid.ListGridRecord;
import com.smartgwt.client.widgets.layout.VLayout;

import it.eng.auriga.ui.module.layout.client.anagrafiche.AssegnazioneUoForms;
import it.eng.auriga.ui.module.layout.client.editor.CKEditorItem;
import it.eng.auriga.ui.module.layout.client.i18n.I18NUtil;
import it.eng.utility.ui.module.core.client.datasource.GWTRestDataSource;
import it.eng.utility.ui.module.layout.client.common.CustomDetail;
import it.eng.utility.ui.module.layout.client.common.DetailSection;
import it.eng.utility.ui.module.layout.client.common.items.DateItem;
import it.eng.utility.ui.module.layout.client.common.items.SelectItem;
import it.eng.utility.ui.module.layout.client.common.items.TextAreaItem;
import it.eng.utility.ui.module.layout.client.common.items.TextItem;
import it.eng.utility.ui.module.layout.shared.util.FrontendUtil;

public class VocabolarioDetail extends CustomDetail {

	protected DynamicForm mDynamicForm;
	private SelectItem dictionaryEntryItem;
	private HiddenItem dictionaryEntryVincoloItem;
	private SelectItem valueGenVincoloItem;
	private TextItem valoreItem;
	private CKEditorItem valoreCkeditorItem;
	private HiddenItem valoreOldItem;
	private HiddenItem flgCodObbligatorioItem;
	private HiddenItem flgValoriCkeditorItem;
	
	private TextItem codiceValoreItem;
	private TextAreaItem significatoValoreItem;
	private DateItem dtInizioValiditaItem;
	private DateItem dtFineValiditaItem;

	protected AssegnazioneUoForms assegnaAdUoForms;
	protected DetailSection assegnaAdUOSection;

	public VocabolarioDetail(String nomeEntita) {
		
		super(nomeEntita);
		
		// Verifico se è abiliato il partizionamento della rubrica
		if (VocabolarioLayout.isPartizionamentoVocabolarioAbilitato()) {
			assegnaAdUoForms = new AssegnazioneUoForms("", vm) {

				public String getFinalitaForLookupOrganigramma() {
					return "ALTRO";
				}

				public String getFinalitaForComboOrganigramma() {
					return "ALTRO";
				}

				public boolean isPartizionamentoRubricaAbilitato() {
					return VocabolarioLayout.isPartizionamentoVocabolarioAbilitato();
				}

				public boolean isAbilInserireModificareSoggInQualsiasiUo() {
					return VocabolarioLayout.isAbilInserireModificareSoggInQualsiasiUo();
				}
				
				@Override
				public String getInRubricaDiMessage() {
					return "In vocabolario di";
				}
				
				@Override
				public String getInRubricaCondivisaMessage() {
					return "In vocabolario condiviso";
				}
			};			
			assegnaAdUOSection = new DetailSection("Nel vocabolario di", true, true, true,	assegnaAdUoForms.getForms());
			assegnaAdUOSection.setVisible(assegnaAdUoForms.isOrganigrammaFormVisible());			
		}
				
		mDynamicForm = new DynamicForm();
		mDynamicForm.setValuesManager(vm);
		mDynamicForm.setWidth("100%");
		mDynamicForm.setHeight("5");
		mDynamicForm.setPadding(5);
		mDynamicForm.setWrapItemTitles(false);
		mDynamicForm.setNumCols(15);
		mDynamicForm.setColWidths(1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, "*", "*");

		// hidden 
		valoreOldItem              = new HiddenItem("valoreOld");
		flgCodObbligatorioItem     = new HiddenItem("flgCodObbligatorio");
		flgValoriCkeditorItem      = new HiddenItem("flgValoriCkeditor");
		dictionaryEntryVincoloItem = new HiddenItem("dictionaryEntryVincolo");
		
		// visibili
		GWTRestDataSource dictionearyEntryDS = new GWTRestDataSource("DictionaryEntryDataSource", "key", FieldType.TEXT);
		GWTRestDataSource valoriDizionarioDS = new GWTRestDataSource("ValoriDizionarioDataSource", "key", FieldType.TEXT);

		// Voce dizionario
		dictionaryEntryItem = new SelectItem("dictionaryEntry", I18NUtil.getMessages().vocabolario_dictionaryEntry()) {

			@Override
			public void onOptionClick(Record record) {

				String dictionaryEntryVincolo = record.getAttribute("dictionaryEntryVincolo");
				dictionaryEntryVincoloItem.setValue(dictionaryEntryVincolo);

				flgCodObbligatorioItem.setValue(record.getAttribute("flgCodObbligatorio"));
				
				flgValoriCkeditorItem.setValue(record.getAttribute("flgValoriCkeditor"));
				
				GWTRestDataSource valoriDizionarioDS = (GWTRestDataSource) valueGenVincoloItem.getOptionDataSource();
				if (record.getAttribute("dictionaryEntryVincolo") != null && !"".equals(record.getAttributeAsString("dictionaryEntryVincolo"))) {
					valoriDizionarioDS.addParam("dictionaryEntryVincolo", record.getAttributeAsString("dictionaryEntryVincolo"));
				} else {
					valoriDizionarioDS.addParam("dictionaryEntryVincolo", null);
				}
				valueGenVincoloItem.setOptionDataSource(valoriDizionarioDS);
				valueGenVincoloItem.fetchData(new DSCallback() {

					@Override
					public void execute(DSResponse response, Object rawData, DSRequest request) {
						Map valueGenVincoloValueMap = response.getDataAsRecordList().getValueMap(valueGenVincoloItem.getValueField(),
								valueGenVincoloItem.getDisplayField());
						if (valueGenVincoloItem.getValue() != null && !"".equals(valueGenVincoloItem.getValueAsString())
								&& !valueGenVincoloValueMap.containsKey(valueGenVincoloItem.getValueAsString())) {
							valueGenVincoloItem.setValue("");
						}
					}
				});
				
				mDynamicForm.markForRedraw();
			}

			@Override
			public void setValue(String value) {
				super.setValue(value);
				if (value == null || "".equals(value)) {
					dictionaryEntryVincoloItem.setValue("");
					valueGenVincoloItem.setValue("");
					flgCodObbligatorioItem.setValue("");
					flgValoriCkeditorItem.setValue("");
				}
			}
		};
		dictionaryEntryItem.setWidth(800);
		dictionaryEntryItem.setStartRow(true);
		dictionaryEntryItem.setRequired(true);
		dictionaryEntryItem.setDefaultToFirstOption(true);
		dictionaryEntryItem.setOptionDataSource(dictionearyEntryDS);
		dictionaryEntryItem.setValueField("key");
		dictionaryEntryItem.setDisplayField("value");
		dictionaryEntryItem.setColSpan(14);
		dictionaryEntryItem.addChangedHandler(new ChangedHandler() {

			@Override
			public void onChanged(ChangedEvent event) {
				valoreItem.setValue("");
				valoreCkeditorItem.setValue("");
				mDynamicForm.markForRedraw();
			}
		});

		// Dettaglio della voce
		valueGenVincoloItem = new SelectItem("valueGenVincolo", I18NUtil.getMessages().vocabolario_valueGenVincolo());
		valueGenVincoloItem.setWidth(800);
		valueGenVincoloItem.setStartRow(true);
		valueGenVincoloItem.setOptionDataSource(valoriDizionarioDS);
		valueGenVincoloItem.setValueField("key");
		valueGenVincoloItem.setDisplayField("value");
		valueGenVincoloItem.setAllowEmptyValue(true);
		valueGenVincoloItem.setColSpan(14);
		valueGenVincoloItem.setCachePickListResults(false);
		valueGenVincoloItem.setShowIfCondition(new FormItemIfFunction() {

			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return (dictionaryEntryVincoloItem.getValue() != null && !"".equals(dictionaryEntryVincoloItem.getValue()));
			}
		});
		valueGenVincoloItem.setPickListFilterCriteriaFunction(new FormItemCriteriaFunction() {

			@Override
			public Criteria getCriteria(FormItemFunctionContext itemContext) {
				if (dictionaryEntryVincoloItem.getValue() != null && !"".equals(dictionaryEntryVincoloItem.getValue())) {
					Criterion[] criterias = new Criterion[1];
					criterias[0] = new Criterion("dictionaryEntryVincolo", OperatorId.IEQUALS, (String) dictionaryEntryVincoloItem.getValue());
					return new AdvancedCriteria(OperatorId.AND, criterias);
				}
				return null;
			}
		});

		// Valore
		valoreItem = new TextItem("valore", I18NUtil.getMessages().vocabolario_valore());
		valoreItem.setWidth(250);
		valoreItem.setColSpan(14);
		valoreItem.setStartRow(true);
		valoreItem.setAttribute("obbligatorio", true);
		valoreItem.setShowIfCondition(new FormItemIfFunction() {

			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				// Se flgValoriCkeditor=1 il campo NON deve essere visibile
				return !verificaShowCkeditor();
			}
		});
		
		// Valore (Ckeditor)
		valoreCkeditorItem = new CKEditorItem("valoreCkeditor", -1, "STANDARD", 4, -1, "", false, true);
		valoreCkeditorItem.setTitle(I18NUtil.getMessages().vocabolario_valore());
		valoreCkeditorItem.setShowTitle(true);
		valoreCkeditorItem.setWidth(795);
		valoreCkeditorItem.setHeight(145);
		valoreCkeditorItem.setColSpan(14);
		valoreCkeditorItem.setStartRow(true);
		valoreCkeditorItem.setAttribute("obbligatorio", true);
		
		// Codice
		codiceValoreItem = new TextItem("codiceValore", I18NUtil.getMessages().vocabolario_codiceValore());
		codiceValoreItem.setWidth(100);
		codiceValoreItem.setStartRow(true);
		codiceValoreItem.setRequired(false);
		codiceValoreItem.setColSpan(14);
		codiceValoreItem.setShowIfCondition(new FormItemIfFunction() {

			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				if (verificaCodFlgObbl()) {
					item.setAttribute("obbligatorio", true);
					item.setTitle(FrontendUtil.getRequiredFormItemTitle(I18NUtil.getMessages().vocabolario_codiceValore()));
					item.setTitleStyle(it.eng.utility.Styles.formTitleBold);
				} else {
					item.setAttribute("obbligatorio", false);
					item.setTitle(I18NUtil.getMessages().vocabolario_codiceValore());
					item.setTitleStyle(it.eng.utility.Styles.formTitle);
				}
				return true;
			}
		});
		codiceValoreItem.setValidators(new RequiredIfValidator(new RequiredIfFunction() {

			@Override
			public boolean execute(FormItem formItem, Object value) {
				return verificaCodFlgObbl();
			}
		}));

		// Significato
		significatoValoreItem = new TextAreaItem("significatoValore", I18NUtil.getMessages().vocabolario_significatoValore());
		significatoValoreItem.setStartRow(true);
		significatoValoreItem.setLength(4000);
		significatoValoreItem.setWidth(800);
		significatoValoreItem.setColSpan(14);

		// Validita' (inizio)
		dtInizioValiditaItem = new DateItem("dtInizioValidita", I18NUtil.getMessages().vocabolario_titlePeriodoValidita() + "   dal");
		dtInizioValiditaItem.setStartRow(true);
		dtInizioValiditaItem.setColSpan(1);

		// Validita' (fine)
		dtFineValiditaItem = new DateItem("dtFineValidita", "al");
		dtFineValiditaItem.setStartRow(false);
		dtFineValiditaItem.setColSpan(1);

		mDynamicForm.setItems(dictionaryEntryItem, 
				              dictionaryEntryVincoloItem, 
				              valueGenVincoloItem, 
				              valoreItem, 
				              valoreCkeditorItem,
				              valoreOldItem, 
				              flgCodObbligatorioItem,
				              flgValoriCkeditorItem,
				              codiceValoreItem,
				              significatoValoreItem, 
				              dtInizioValiditaItem, 
				              dtFineValiditaItem);

		// LAYOUT MAIN
		VLayout lVLayout = new VLayout();
		lVLayout.setWidth100();
		lVLayout.setHeight(50);
		
		VLayout lVLayoutSpacer = new VLayout();
		lVLayoutSpacer.setWidth100();
		lVLayoutSpacer.setHeight(50);

		if (assegnaAdUOSection != null) {
			// Inserisco la section solamente se è stata creata
			// (la creo solamente se il partizionamento del vocabolario è abilitato)
			lVLayout.addMember(assegnaAdUOSection);
		}
		lVLayout.addMember(mDynamicForm);

		addMember(lVLayout);
		addMember(lVLayoutSpacer);

	}

	@Override
	public void editNewRecord(Map initialValues) {
		super.editNewRecord(initialValues);
		valoreOldItem.setValue("");
		loadComboVoceDizionario(new Record(initialValues));
	}
	
	@Override
	public void editRecord(Record record) {
		super.editRecord(record);
		valoreOldItem.setValue(record.getAttribute("valore"));
		loadComboVoceDizionario(record);
	}

	private void loadComboVoceDizionario(Record record) {
		GWTRestDataSource valoriDizionarioDS = (GWTRestDataSource) valueGenVincoloItem.getOptionDataSource();
		if (record.getAttribute("dictionaryEntryVincolo") != null && !"".equals(record.getAttributeAsString("dictionaryEntryVincolo"))) {
			valoriDizionarioDS.addParam("dictionaryEntryVincolo", record.getAttributeAsString("dictionaryEntryVincolo"));
		} else {
			valoriDizionarioDS.addParam("dictionaryEntryVincolo", null);
		}
		if (record.getAttribute("valueGenVincolo") != null && !"".equals(record.getAttributeAsString("valueGenVincolo"))) {
			valoriDizionarioDS.addParam("valueGenVincolo", record.getAttributeAsString("valueGenVincolo"));
		} else {
			valoriDizionarioDS.addParam("valueGenVincolo", null);
		}
		valueGenVincoloItem.setOptionDataSource(valoriDizionarioDS);
		valueGenVincoloItem.fetchData();
	}

	@Override
	public void setCanEdit(boolean canEdit) {
		super.setCanEdit(canEdit);
		dictionaryEntryItem.setCanEdit(false);
		if (verificaFlgValueReferenced()) {
			if(valoreItem != null) {
				valoreItem.setCanEdit(false);
			}
		}
		if (verificaFlgCodValReferenced()) {
			if(codiceValoreItem != null) {
				codiceValoreItem.setCanEdit(false);
			}
		}
	}

	// Se flgValueReferenced = 1 il valore non è modificabile;
	private boolean verificaFlgValueReferenced() {
		Record record = new Record(vm.getValues());
		return (record.getAttribute("flgValueReferenced") != null && record.getAttribute("flgValueReferenced").equals("1") ? true : false);
	}

	// Se flgCodValReferenced = 1 il codice non è modificabile
	private boolean verificaFlgCodValReferenced() {
		Record record = new Record(vm.getValues());
		return (record.getAttribute("flgCodValReferenced") != null && record.getAttribute("flgCodValReferenced").equals("1") ? true : false);
	}

	// Se flgCodObbligatorio=1 il campo del codice deve essere obbligatorio
	private boolean verificaCodFlgObbl() {
		Record record = new Record(vm.getValues());
		return (record.getAttribute("flgCodObbligatorio") != null && record.getAttribute("flgCodObbligatorio").equals("1") ? true : false);
	}

	// Se flgValoriCkeditor=1 il campo deve essere visibile
	private boolean verificaShowCkeditor() {
		Record record = new Record(vm.getValues());
		return (record.getAttribute("flgValoriCkeditor") != null && record.getAttribute("flgValoriCkeditor").equals("1") ? true : false);
	}

	@Override
	public void newMode() {
		super.newMode();
		if (VocabolarioLayout.isPartizionamentoVocabolarioAbilitato()) {
			assegnaAdUoForms.newMode();
		}
		
		Criteria[] lCriteriaArray = getLayout().getFilter().getCriteria().getCriteria();
		String valoreFiltroVoceDiz = "";
		if (lCriteriaArray.length > 0) {
			for (Criteria lCritetria : lCriteriaArray) {
				String fieldName = lCritetria.getAttributeAsString("fieldName");
				if (fieldName.equalsIgnoreCase("dictionaryEntry")) {
					valoreFiltroVoceDiz = lCritetria.getAttributeAsString("value");
				}
			}
		}
		
		settaValoreSelect(valoreFiltroVoceDiz);
		
		if(valoreCkeditorItem != null) {
			if(verificaShowCkeditor()) {
				valoreCkeditorItem.setAttribute("obbligatorio", true);
				valoreCkeditorItem.setVisible(true);
				valoreItem.setAttribute("obbligatorio", false);
			} else {
				valoreCkeditorItem.setAttribute("obbligatorio", false);
				// Forzo l'init del canvas perchè, quando viene nascosto dopo essere stato iniziallizato, GWT tronca l'html e restituisce errore quando viene chiamato l'editNewRecord
				valoreCkeditorItem.setVisibleAndForceInit(false, true);
				valoreItem.setAttribute("obbligatorio", false);
			}
		}
		mDynamicForm.markForRedraw();
	}
	
	@Override
	public void viewMode() {
		super.viewMode();
		if (VocabolarioLayout.isPartizionamentoVocabolarioAbilitato()) {
			// Il partizionamento della rubrica è attivo
			Record record = new Record();
			record.setAttribute("tipo", "UO");
			record.setAttribute("idUoSvUt", vm.getValues().get("idUoAssociata"));
			record.setAttribute("codRapidoUo", vm.getValues().get("numeroLivelli"));
			record.setAttribute("flgVisibileDaSottoUo", vm.getValues().get("flgVisibileDaSottoUo"));
			record.setAttribute("flgModificabileDaSottoUo", vm.getValues().get("flgModificabileDaSottoUo"));
			// Verifico che sia presente il form del'assegnazione del vocabolario alla UO
			if (assegnaAdUOSection != null) {
				// Setto i valori del form per l'assegnazione della UO al vocabolario
				assegnaAdUoForms.viewMode();
				assegnaAdUoForms.setFormValuesFromRecord(record);
			}
		}
		
		if(valoreCkeditorItem != null) {
			if(verificaShowCkeditor()) {
				valoreCkeditorItem.setAttribute("obbligatorio", true);
				valoreCkeditorItem.setVisible(true);
				valoreItem.setAttribute("obbligatorio", false);
			} else {
				valoreCkeditorItem.setAttribute("obbligatorio", false);
				// Forzo l'init del canvas perchè, quando viene nascosto dopo essere stato iniziallizato, GWT tronca l'html e restituisce errore quando viene chiamato l'editNewRecord
				valoreCkeditorItem.setVisibleAndForceInit(false, true);
				valoreItem.setAttribute("obbligatorio", false);
			}
		}
		mDynamicForm.markForRedraw();
	}

	@Override
	public void editMode() {
		super.editMode();
		if (VocabolarioLayout.isPartizionamentoVocabolarioAbilitato()) {
			// Il partizionamento della rubrica è attivo
			Record record = new Record();
			record.setAttribute("tipo", "UO");
			record.setAttribute("idUoSvUt", vm.getValues().get("idUoAssociata"));
			record.setAttribute("codRapidoUo", vm.getValues().get("numeroLivelli"));
			record.setAttribute("flgVisibileDaSottoUo", vm.getValues().get("flgVisibileDaSottoUo"));
			record.setAttribute("flgModificabileDaSottoUo", vm.getValues().get("flgModificabileDaSottoUo"));
			if (assegnaAdUOSection != null) {
				assegnaAdUoForms.editMode();
				assegnaAdUoForms.setFormValuesFromRecord(record);
			}
			
		}
		
		if(valoreCkeditorItem != null) {
			if(verificaShowCkeditor()) {
				valoreCkeditorItem.setAttribute("obbligatorio", true);
				valoreCkeditorItem.setVisible(true);
				valoreItem.setAttribute("obbligatorio", false);
			} else {
				valoreCkeditorItem.setAttribute("obbligatorio", false);
				// Forzo l'init del canvas perchè, quando viene nascosto dopo essere stato iniziallizato, GWT tronca l'html e restituisce errore quando viene chiamato l'editNewRecord
				valoreCkeditorItem.setVisibleAndForceInit(false, true);
				valoreItem.setAttribute("obbligatorio", false);
			}
		}
		mDynamicForm.markForRedraw();
	}
	
	private void settaValoreSelect(String value) {
		dictionaryEntryItem.setValue(value);
		ListGridRecord lListGridRecord = dictionaryEntryItem.getSelectedRecord();
		if (lListGridRecord != null) {
			dictionaryEntryItem.onOptionClick(lListGridRecord);
		}
	}
	
	@Override
	public Record getRecordToSave() {
		final Record lRecordToSave = new Record(vm.getValues());
		if (lRecordToSave.getAttribute("flgValoriCkeditor") != null && lRecordToSave.getAttribute("flgValoriCkeditor").equals("1") && valoreCkeditorItem.isVisible()){
			lRecordToSave.setAttribute("valore", valoreCkeditorItem.getValue());
		}
		return lRecordToSave;
	}
}
