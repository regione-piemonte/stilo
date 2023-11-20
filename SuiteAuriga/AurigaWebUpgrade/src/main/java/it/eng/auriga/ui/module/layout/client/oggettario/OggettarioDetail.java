/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.LinkedHashMap;

import com.smartgwt.client.data.Record;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.FormItemIfFunction;
import com.smartgwt.client.widgets.form.fields.FormItem;
import com.smartgwt.client.widgets.form.fields.HiddenItem;
import com.smartgwt.client.widgets.form.fields.RadioGroupItem;
import com.smartgwt.client.widgets.form.fields.SpacerItem;
import com.smartgwt.client.widgets.form.fields.events.ChangedEvent;
import com.smartgwt.client.widgets.form.fields.events.ChangedHandler;
import com.smartgwt.client.widgets.form.validator.CustomValidator;
import com.smartgwt.client.widgets.layout.VLayout;

import it.eng.auriga.ui.module.layout.client.anagrafiche.AssegnazioneUoForms;
import it.eng.auriga.ui.module.layout.client.i18n.I18NUtil;
import it.eng.utility.ui.module.layout.client.common.CustomDetail;
import it.eng.utility.ui.module.layout.client.common.DetailSection;
import it.eng.utility.ui.module.layout.client.common.items.CheckboxItem;
import it.eng.utility.ui.module.layout.client.common.items.StaticTextItem;
import it.eng.utility.ui.module.layout.client.common.items.TextAreaItem;
import it.eng.utility.ui.module.layout.client.common.items.TextItem;
import it.eng.utility.ui.module.layout.client.common.items.TitleItem;
import it.eng.utility.ui.module.layout.shared.util.FrontendUtil;

public class OggettarioDetail extends CustomDetail {
	
	private VLayout lVLayoutMain;
		
	protected DetailSection visOggettarioSection;
	protected DetailSection datiModelloSection;
		
	protected DynamicForm visOggettarioForm;
	protected AssegnazioneUoForms assegnaAdUoForms;
	protected DynamicForm datiModelloform;
	protected DynamicForm registrazioniModelloform;
	
	protected RadioGroupItem visOggettarioItem;
	protected HiddenItem idModelloItem;		
	protected TextItem nomeItem;
	protected StaticTextItem utenteInserimentoItem;
	protected TextAreaItem oggettoItem;
	protected TextAreaItem noteItem;
	protected TitleItem flgXRegTitleItem;
	protected CheckboxItem flgXRegInEntrataItem;
	protected CheckboxItem flgXRegInUscitaItem;
	protected CheckboxItem flgXRegInterneItem;		
	private String flgTipoProv;	
	
	public OggettarioDetail(String nomeEntita, String flgTipoProv) {		
		
		super(nomeEntita);
		
		this.flgTipoProv = flgTipoProv; 
		
		// LAYOUT MAIN
		lVLayoutMain = new VLayout();
		lVLayoutMain.setWidth100();
		lVLayoutMain.setHeight(50);
		
		buildVisOggettarioSection();
		buildDatiModelloSection();

		VLayout lVLayoutSpacer = new VLayout();
		lVLayoutSpacer.setWidth100();
		lVLayoutSpacer.setHeight(50);

		addMember(lVLayoutMain);
		addMember(lVLayoutSpacer);

	}	
	
	
	private void buildVisOggettarioSection() {
		
		visOggettarioForm = new DynamicForm();
		visOggettarioForm.setValuesManager(vm);
		visOggettarioForm.setKeepInParentRect(true);		
		visOggettarioForm.setPadding(0);
		visOggettarioForm.setNumCols(12);
		visOggettarioForm.setColWidths(120,1,1,1,1,1,1,1,1,1,"*","*");
		
		LinkedHashMap<String, String> visOggettarioMap = new LinkedHashMap<String, String>();
		if (OggettarioLayout.isAbilInserireInUo()) {
			visOggettarioMap.put(VisOggettario.UO.getValue(), VisOggettario.UO.getDescrizione());
		}
		if (OggettarioLayout.isAbilInserireInPubblico()) {
			visOggettarioMap.put(VisOggettario.PUBBLICO.getValue(), VisOggettario.PUBBLICO.getDescrizione());
		}
		visOggettarioMap.put(VisOggettario.PERSONALE.getValue(), VisOggettario.PERSONALE.getDescrizione());
		visOggettarioItem = new RadioGroupItem("flgTipoModello", "della UO");
		visOggettarioItem.setValueMap(visOggettarioMap);
		visOggettarioItem.setEndRow(false);
		visOggettarioItem.setVertical(false);
		visOggettarioItem.setWrap(false);
		visOggettarioItem.setRequired(true);
		visOggettarioItem.setShowTitle(false);
		visOggettarioItem.addChangedHandler(new ChangedHandler() {

			@Override
			public void onChanged(ChangedEvent event) {
				boolean visible = false;
				if (VisOggettario.UO.value.equalsIgnoreCase(visOggettarioItem.getValueAsString())){
					visible = true;
				}
				assegnaAdUoForms.redrawForms();
			}
		});
		
		utenteInserimentoItem = new StaticTextItem() {
			@Override
			public void setCanEdit(Boolean canEdit) {
				super.setCanEdit(canEdit);
				setTextBoxStyle(it.eng.utility.Styles.formTitle);
			}
		};
		utenteInserimentoItem.setName("descrizioneUtenteInserimento");
		utenteInserimentoItem.setShowTitle(false);
		utenteInserimentoItem.setStartRow(false);
		utenteInserimentoItem.setWrap(false);
		
		SpacerItem verticalSpacer = new SpacerItem();
		verticalSpacer.setHeight(0);
		verticalSpacer.setStartRow(true);
		
		visOggettarioForm.setItems(visOggettarioItem, utenteInserimentoItem, verticalSpacer);
		
		assegnaAdUoForms = new AssegnazioneUoForms("", vm) {

			@Override
			public String getFinalitaForLookupOrganigramma() {
				return null;
			}

			@Override
			public String getFinalitaForComboOrganigramma() {
				return null;
			}

			@Override
			public boolean isPartizionamentoRubricaAbilitato() {
				return OggettarioLayout.isPartizionamentoRubricaAbilitato();
			}

			@Override
			public boolean isAbilInserireModificareSoggInQualsiasiUo() {
				if (mode != null && mode.equalsIgnoreCase("edit")) {
					return OggettarioLayout.isAbilModificareSoggInQualsiasiUo();
				} else {
					return OggettarioLayout.isAbilInserireSoggInQualsiasiUo();
				}
			}
			
			@Override
			public String getInRubricaDiMessage() {
				return "In oggettario di";
			}
			
			@Override
			public String getInRubricaCondivisaMessage() {
				return "In oggettario condiviso";
			}
			
			@Override
			public int getFormMargin() {
				return 0;
			}
			
			@Override
			public int getFormPadding() {
				return 0;
			}
			
			@Override
			public boolean mostraFormDiAssegnazione() {
				String tipoModello = visOggettarioForm.getValueAsString("flgTipoModello");
				return tipoModello != null && "UO".equalsIgnoreCase(tipoModello);
			}
			
		};			
	
		DynamicForm[] forms = new DynamicForm[ assegnaAdUoForms.getForms().length + 1];
		System.arraycopy(assegnaAdUoForms.getForms(), 0, forms, 1, assegnaAdUoForms.getForms().length );
		forms[0] = visOggettarioForm;
		
		visOggettarioSection = new DetailSection("Nell'oggettario", true, true, true, forms);
		
		lVLayoutMain.addMember(visOggettarioSection);
	//}
	}
	
	private void buildDatiModelloSection() {
		datiModelloform = new DynamicForm();
		datiModelloform.setValuesManager(vm);
		datiModelloform.setKeepInParentRect(true);		
		datiModelloform.setPadding(5);
		datiModelloform.setNumCols(12);
		datiModelloform.setColWidths(120,1,1,1,1,1,1,1,1,1,"*","*");
		
		idModelloItem = new HiddenItem("idModello");			
		
		nomeItem = new TextItem("nome", I18NUtil.getMessages().oggettario_detail_nomeItem_title());
		nomeItem.setWidth(250);
		nomeItem.setColSpan(8);
		nomeItem.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return true;
			}
		});
		
		oggettoItem = new TextAreaItem("oggetto", I18NUtil.getMessages().oggettario_detail_oggettoItem_title());
		oggettoItem.setColSpan(8);
		oggettoItem.setRequired(true);
		oggettoItem.setLength(4000);
		oggettoItem.setHeight(40);
		oggettoItem.setWidth(650);			
		oggettoItem.setStartRow(true);
		
		noteItem = new TextAreaItem("note", I18NUtil.getMessages().oggettario_detail_noteItem_title());
		noteItem.setColSpan(8);
		noteItem.setLength(4000);
		noteItem.setHeight(40);
		noteItem.setWidth(650);			
		noteItem.setStartRow(true);
		
		datiModelloform.setItems(idModelloItem, nomeItem, oggettoItem, noteItem);
		
		registrazioniModelloform = new DynamicForm();
		registrazioniModelloform.setValuesManager(vm);
		registrazioniModelloform.setKeepInParentRect(true);		
		registrazioniModelloform.setPadding(5);
		registrazioniModelloform.setNumCols(12);
		registrazioniModelloform.setColWidths(120,1,1,1,1,1,1,1,1,1,"*","*");
		
		flgXRegTitleItem = new TitleItem(I18NUtil.getMessages().oggettario_detail_flgXRegTitleItem_title(), true);
		flgXRegTitleItem.setColSpan(1);	
		flgXRegTitleItem.setWidth(10);
		flgXRegTitleItem.setStartRow(true);
		
		CustomValidator regValidator = new CustomValidator() {

			@Override
			protected boolean condition(Object value) {
				Boolean flgXRegInEntrata = registrazioniModelloform.getValue("flgXRegInEntrata") != null ? (Boolean) registrazioniModelloform.getValue("flgXRegInEntrata") : false; 
				Boolean flgXRegInUscita = registrazioniModelloform.getValue("flgXRegInUscita")  != null ? (Boolean) registrazioniModelloform.getValue("flgXRegInUscita") : false;
				Boolean flgXRegInterne = registrazioniModelloform.getValue("flgXRegInterne")  != null ? (Boolean) registrazioniModelloform.getValue("flgXRegInterne") : false;
				return flgXRegInEntrata || flgXRegInUscita || flgXRegInterne;
			}
		};
		regValidator.setErrorMessage("Selezionare almeno un verso di registrazione");
		flgXRegTitleItem.setValidators(regValidator);
		
		flgXRegInEntrataItem = new CheckboxItem("flgXRegInEntrata", I18NUtil.getMessages().oggettario_detail_flgXRegInEntrataItem_title());
		flgXRegInEntrataItem.setColSpan(1);
		flgXRegInEntrataItem.setWidth(10);
		
		flgXRegInUscitaItem = new CheckboxItem("flgXRegInUscita", I18NUtil.getMessages().oggettario_detail_flgXRegInUscitaItem_title());
		flgXRegInUscitaItem.setColSpan(1);
		flgXRegInUscitaItem.setWidth(10);
		
		flgXRegInterneItem = new CheckboxItem("flgXRegInterne", I18NUtil.getMessages().oggettario_detail_flgXRegInterneItem_title());
		flgXRegInterneItem.setColSpan(1);
		flgXRegInterneItem.setWidth(10);

		if(flgTipoProv != null) {
			flgXRegInEntrataItem.setDefaultValue("E".equals(flgTipoProv));
			flgXRegInUscitaItem.setDefaultValue("U".equals(flgTipoProv));
			flgXRegInterneItem.setDefaultValue("I".equals(flgTipoProv));			
		}
		
		registrazioniModelloform.setItems(flgXRegTitleItem, flgXRegInEntrataItem, flgXRegInUscitaItem,
				flgXRegInterneItem);
		
		datiModelloSection = new DetailSection("Dati modello", true, true, true, datiModelloform, registrazioniModelloform);
		
		lVLayoutMain.addMember(datiModelloSection);
	}
	
	public void setCanEdit(boolean canEdit) {
		super.setCanEdit(canEdit);
		if(flgTipoProv != null) {
			flgXRegInEntrataItem.setCanEdit(!"E".equals(flgTipoProv));
			flgXRegInUscitaItem.setCanEdit(!"U".equals(flgTipoProv));
			flgXRegInterneItem.setCanEdit(!"I".equals(flgTipoProv));			
		}
	}

	
	@Override
	public void newMode() {
		super.newMode();
		setVisOggettarioMap(true);
		nomeItem.setRequired(false);
		nomeItem.setAttribute("obbligatorio", false);
		nomeItem.setTitle(I18NUtil.getMessages().oggettario_detail_nomeItem_title());
		assegnaAdUoForms.newMode();
	}
	
	@Override
	public void viewMode() {
		super.viewMode();
		setVisOggettarioMap(true);
		nomeItem.setRequired(true);
		nomeItem.setAttribute("obbligatorio", true);
		nomeItem.setTitle(FrontendUtil.getRequiredFormItemTitle(I18NUtil.getMessages().oggettario_detail_nomeItem_title()));
		nomeItem.setTitleStyle(it.eng.utility.Styles.formTitleBold);
		assegnaAdUoForms.viewMode();
	}
	
	@Override
	public void editMode() {
		super.editMode();
		setVisOggettarioMap(false);
		nomeItem.setRequired(true);
		nomeItem.setAttribute("obbligatorio", true);
		nomeItem.setTitle(FrontendUtil.getRequiredFormItemTitle(I18NUtil.getMessages().oggettario_detail_nomeItem_title()));
		nomeItem.setTitleStyle(it.eng.utility.Styles.formTitleBold);
		assegnaAdUoForms.editMode();
		assegnaAdUoForms.redrawForms();
	}
	
	@Override
	public void editRecord(Record record) {
		if (record.getAttributeAsString("uteIns") != null && !"".equalsIgnoreCase(record.getAttributeAsString("uteIns"))){
			record.setAttribute("descrizioneUtenteInserimento", "di " + record.getAttributeAsString("uteIns").replaceAll("\\|", "").replaceAll("  ", " "));
		}
		super.editRecord(record);
		Record recordAssegnaAdUoForms = new Record();
		recordAssegnaAdUoForms.setAttribute("tipo", "UO");
		recordAssegnaAdUoForms.setAttribute("idUoSvUt", vm.getValues().get("idUoAssociata"));
		recordAssegnaAdUoForms.setAttribute("codRapidoUo", vm.getValues().get("numeroLivelli"));
		recordAssegnaAdUoForms.setAttribute("flgVisibileDaSottoUo", vm.getValues().get("flgVisibileDaSottoUo"));
		recordAssegnaAdUoForms.setAttribute("flgModificabileDaSottoUo", vm.getValues().get("flgModificabileDaSottoUo"));
		
		//recordAssegnaAdUoForms.setAttribute("idUoAssociata", vm.getValues().get("idUoAssociata"));
		assegnaAdUoForms.editMode();
		assegnaAdUoForms.setFormValuesFromRecord(recordAssegnaAdUoForms);
		assegnaAdUoForms.redrawForms();
		
		
	}
	
	private void setVisOggettarioMap (boolean newMode){
		visOggettarioItem.setValueDisabled(VisOggettario.UO.getValue(), true);
		visOggettarioItem.setValueDisabled(VisOggettario.PUBBLICO.getValue(), true);
		visOggettarioItem.setValueDisabled(VisOggettario.PERSONALE.getValue(), false);
		if (newMode) {
			if (OggettarioLayout.isAbilInserireInUo()) {
				visOggettarioItem.setValueDisabled(VisOggettario.UO.getValue(), false);
			}
			if (OggettarioLayout.isAbilInserireInPubblico()) {
				visOggettarioItem.setValueDisabled(VisOggettario.PUBBLICO.getValue(), false);
			}
		} else {
			if (OggettarioLayout.isAbilModificareInUo()) {
				visOggettarioItem.setValueDisabled(VisOggettario.UO.getValue(), false);
			}
			if (OggettarioLayout.isAbilModificareInPubblico()) {
				visOggettarioItem.setValueDisabled(VisOggettario.PUBBLICO.getValue(), false);
			}
		}
	}
	
	public enum VisOggettario {
		
		PUBBLICO("PB", "pubblico"),
		PERSONALE("PR", "personale"),
		UO("UO", "della UO");
		
		private String value;
		private String descrizione;
		
		private VisOggettario(String value, String descrizione) {
			this.value = value;
			this.descrizione = descrizione;
		}
		
		public String getValue() {
			return value;
		}
		
		public void setValue(String value) {
			this.value = value;
		}
		
		public String getDescrizione() {
			return descrizione;
		}

		public void setDescrizione(String descrizione) {
			this.descrizione = descrizione;
		}
			
	}

}