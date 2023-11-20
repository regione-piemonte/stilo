/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import com.smartgwt.client.data.Record;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.FieldType;
import com.smartgwt.client.types.Overflow;
import com.smartgwt.client.util.StringUtil;
import com.smartgwt.client.widgets.Button;
import com.smartgwt.client.widgets.events.FetchDataEvent;
import com.smartgwt.client.widgets.events.FetchDataHandler;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.FormItemHoverFormatter;
import com.smartgwt.client.widgets.form.FormItemIfFunction;
import com.smartgwt.client.widgets.form.ValuesManager;
import com.smartgwt.client.widgets.form.fields.ComboBoxItem;
import com.smartgwt.client.widgets.form.fields.FormItem;
import com.smartgwt.client.widgets.form.fields.events.ChangedEvent;
import com.smartgwt.client.widgets.form.fields.events.ChangedHandler;
import com.smartgwt.client.widgets.grid.CellFormatter;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.grid.ListGridRecord;
import com.smartgwt.client.widgets.grid.events.CellClickEvent;
import com.smartgwt.client.widgets.grid.events.CellClickHandler;
import com.smartgwt.client.widgets.layout.HStack;
import com.smartgwt.client.widgets.layout.VLayout;

import it.eng.auriga.ui.module.layout.client.AurigaLayout;
import it.eng.auriga.ui.module.layout.client.anagrafiche.AssegnazioneUoForms;
import it.eng.utility.ui.module.core.client.datasource.GWTRestDataSource;
import it.eng.utility.ui.module.core.client.i18n.I18NUtil;
import it.eng.utility.ui.module.layout.client.common.DetailSection;
import it.eng.utility.ui.module.layout.client.common.items.CheckboxItem;
import it.eng.utility.ui.module.layout.client.portal.ModalWindow;

public abstract class SaveModelloWindow extends ModalWindow {

	private ValuesManager vm;
	private SaveModelloAction saveModelloAction;
	private GWTRestDataSource modelliDS;
	private ComboBoxItem nomeComboBoxItem;
	private CheckboxItem flgPubblicoCheckboxItem;
	private CheckboxItem flgUoCheckboxItem;
	private DynamicForm form;
	private AssegnazioneUoForms assegnazioneUoForms;
	private DynamicForm[] forms;
	private DetailSection detailSectionSceltaUO;
	private DetailSection detailSectionModello;
	
	private Button okButton;
	
	public SaveModelloWindow(String title, String nomeEntitaFrom, SaveModelloAction action) {

		super("salva_modello_reg", true);

		vm = new ValuesManager();

		saveModelloAction = action;

		setTitle(title);

		setAutoCenter(true);
		setWidth(650);
		setHeight(170);

		settingsMenu.removeItem(separatorMenuItem);
		settingsMenu.removeItem(autoSearchMenuItem);

		createModelliDataSource(nomeEntitaFrom);

		createSaveModelloForms();

		okButton = new Button("Ok");
		okButton.setIcon("ok.png");
		okButton.setIconSize(16);
		okButton.setAutoFit(false);
		okButton.addClickHandler(new com.smartgwt.client.widgets.events.ClickHandler() {

			@Override
			public void onClick(com.smartgwt.client.widgets.events.ClickEvent event) {
				okButton.focusAfterGroup();
				if (vm != null && vm.validate()) {
					saveModelloAction(new Record(vm.getValues()));
				}
			}
		});

		HStack _buttons = new HStack(5);
		_buttons.setHeight(30);
		_buttons.setAlign(Alignment.CENTER);
		_buttons.setPadding(5);
		_buttons.addMember(okButton);
		
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
		
		VLayout spacerLayout = new VLayout();
		spacerLayout.setHeight100();
		spacerLayout.setWidth100();

		detailSectionModello = new DetailSection("Nome modello ", true, true, false, form);
		
		detailSectionSceltaUO = new DetailSection("Scelta U.O. ", true, true, false, forms);
		detailSectionSceltaUO.setVisible(false);

		detailSectionModello.getLayout().setPadding(4);
		detailSectionModello.getLayout().setMembersMargin(-4);
		
		detailSectionSceltaUO.getLayout().setPadding(3);
		detailSectionSceltaUO.getLayout().setMembersMargin(-3);

		layout.addMember(detailSectionModello);
		layout.addMember(detailSectionSceltaUO);
		layout.addMember(spacerLayout);

		portletLayout.addMember(layout);
		portletLayout.addMember(_buttons);

		setBody(portletLayout);
	}

	public void createModelliDataSource(String nomeEntitaFrom) {
		modelliDS = new GWTRestDataSource("ModelliDataSource", "key", FieldType.TEXT);
		modelliDS.addParam("prefKey", nomeEntitaFrom + ".modelli");
	}

	public void createSaveModelloForms() {

		form = new DynamicForm();
		form.setValuesManager(vm);
		form.setKeepInParentRect(true);
		form.setWidth100();
		form.setHeight100();
		form.setNumCols(5);
		form.setColWidths(10, 10, 10, 10, "*");
		form.setCellPadding(7);

		form.setCanSubmit(true);
		form.setAlign(Alignment.LEFT);
		form.setTop(50);

		nomeComboBoxItem = new ComboBoxItem("nome", "Nome");
		nomeComboBoxItem.setKeyPressFilter("[0-9a-zA-Z ,;.:!?|_-]");		
		nomeComboBoxItem.setValueField("prefName");
		nomeComboBoxItem.setDisplayField("prefName");
		nomeComboBoxItem.setCanEdit(true);
		nomeComboBoxItem.setWidth(300);
		nomeComboBoxItem.setAlign(Alignment.LEFT);
		nomeComboBoxItem.setRequired(true);
		nomeComboBoxItem.setDefaultValue(isTrasmissioneAtti() ? "DEFAULT" : "");
		nomeComboBoxItem.setItemHoverFormatter(true, new FormItemHoverFormatter() {

			@Override
			public String getHoverHTML(FormItem item, DynamicForm form) {
				return StringUtil.asHTML((String) nomeComboBoxItem.getValue());
			}
		});
		nomeComboBoxItem.setOptionDataSource(modelliDS);
		ListGridField modelliPrefNameField = new ListGridField("prefName");
		modelliPrefNameField.setWidth("100%");
		modelliPrefNameField.setCellFormatter(new CellFormatter() {

			@Override
			public String format(Object value, ListGridRecord record, int rowNum, int colNum) {
				if(record != null) {
					String res = record.getAttribute("prefName") != null ? record.getAttributeAsString("prefName") : null;
					if (res != null && record.getAttribute("userid") != null) {
						if (record.getAttributeAsString("userid").startsWith("PUBLIC.")) {
							res += "&nbsp;<img src=\"images/public.png\" height=\"12\" width=\"12\" align=MIDDLE/>";
						} else if (record.getAttributeAsString("userid").startsWith("UO.")) {
							res += "&nbsp;<img src=\"images/organigramma/tipo/UO.png\" height=\"12\" width=\"12\" align=MIDDLE/>";
						}
					}
					return res;
				}
				return null;
			}
		});
		nomeComboBoxItem.setPickListFields(modelliPrefNameField);

		ListGrid modelliPickListProperties = new ListGrid();
		modelliPickListProperties.setEmptyMessage(I18NUtil.getMessages().list_emptyMessage());
		modelliPickListProperties.setShowHeader(false);
		modelliPickListProperties.addCellClickHandler(new CellClickHandler() {

			@Override
			public void onCellClick(CellClickEvent event) {

				selezionaModello(event.getRecord());
			}
		});
		modelliPickListProperties.addFetchDataHandler(new FetchDataHandler() {

			@Override
			public void onFilterData(FetchDataEvent event) {

				GWTRestDataSource modelliDS = (GWTRestDataSource) nomeComboBoxItem.getOptionDataSource();
				String nome = form.getValueAsString("nome");
				modelliDS.addParam("strInNome", nome);
				nomeComboBoxItem.setOptionDataSource(modelliDS);
				nomeComboBoxItem.invalidateDisplayValueCache();
			}
		});
		nomeComboBoxItem.setPickListProperties(modelliPickListProperties);

		flgPubblicoCheckboxItem = new CheckboxItem("flgPubblico");
		flgPubblicoCheckboxItem.setTitle("pubblico");
		flgPubblicoCheckboxItem.setShowTitle(false);
		flgPubblicoCheckboxItem.setWidth("*");
		flgPubblicoCheckboxItem.setAlign(Alignment.CENTER);
		flgPubblicoCheckboxItem.setShowIfCondition(new FormItemIfFunction() {

			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {

				return !isTrasmissioneAtti() && isAbilToSavePublic();
			}
		});
		flgPubblicoCheckboxItem.addChangedHandler(new ChangedHandler() {

			@Override
			public void onChanged(ChangedEvent event) {

				redrawForms();

			}
		});

		flgUoCheckboxItem = new CheckboxItem("flgUo");
		flgUoCheckboxItem.setTitle("valido per UO");
		flgUoCheckboxItem.setShowTitle(false);
		flgUoCheckboxItem.setWidth("*");
		flgUoCheckboxItem.setAlign(Alignment.CENTER);
		flgUoCheckboxItem.setShowIfCondition(new FormItemIfFunction() {

			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {

				return !isTrasmissioneAtti() && isFlgPubblicoNotChecked() && AurigaLayout.getParametroDBAsBoolean("ATTIVA_PARTIZ_MODELLI_X_UO");
			}
		});
		flgUoCheckboxItem.addChangedHandler(new ChangedHandler() {

			@Override
			public void onChanged(ChangedEvent event) {

				detailSectionSceltaUO.setVisible(isFlgUoChecked() && assegnazioneUoForms.isOrganigrammaFormVisible());
				
				redrawForms();

			}
		});

		form.setFields(new FormItem[] { nomeComboBoxItem, flgPubblicoCheckboxItem, flgUoCheckboxItem });

		assegnazioneUoForms = new AssegnazioneUoForms("", vm) {

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
				return isFlgUoChecked();
			}

			@Override
			public boolean isAbilInserireModificareSoggInQualsiasiUo() {
				return isAbilToSavePublic();
			}
			
			@Override
			public int getOrganigrammaItemWidth() {
				return 700;
			}
			
			@Override
			public int getUoCollegateItemWidth() {
				return 700;
			}
		};

		forms = new DynamicForm[assegnazioneUoForms.getForms().length];
		for (int i = 0; i < assegnazioneUoForms.getForms().length; i++) {
			forms[i] = assegnazioneUoForms.getForms()[i];
			forms[i].setCellPadding(7);
		}

	}

	public void clearValues() {
		if(form != null) {
			form.clearValues();
		}
		for (DynamicForm form : forms) {
			form.clearValues();
		}
	}

	public void redrawForms() {
		if(form != null) {
			form.markForRedraw();
		}
		for (DynamicForm form : forms) {
			form.markForRedraw();
		}
		if (assegnazioneUoForms != null) {
			assegnazioneUoForms.redrawForms();
		}
	}

	public boolean isFlgPubblicoNotChecked() {
		return !isAbilToSavePublic() || (flgPubblicoCheckboxItem.getValueAsBoolean() == null || !flgPubblicoCheckboxItem.getValueAsBoolean());
	}

	public boolean isFlgUoChecked() {
		return isFlgPubblicoNotChecked() && AurigaLayout.getParametroDBAsBoolean("ATTIVA_PARTIZ_MODELLI_X_UO")
				&& (flgUoCheckboxItem.getValueAsBoolean() != null && flgUoCheckboxItem.getValueAsBoolean());
	}

	public void setModelloName(String prefName) {
		nomeComboBoxItem.setValue(prefName);
	}

	public void selezionaModello(Record record) {
		if(record != null){
			String userid = (String) record.getAttribute("userid");
			String idUo = (String) record.getAttribute("idUo");
			nomeComboBoxItem.setValue(record.getAttribute("prefName"));
			flgPubblicoCheckboxItem.setValue(false);
			flgUoCheckboxItem.setValue(false);
			assegnazioneUoForms.setFormValuesFromRecord(new Record());
			if (userid != null && !"".equals(userid)) {
				if (userid.startsWith("PUBLIC.") && isAbilToSavePublic()) {
					flgPubblicoCheckboxItem.setValue(true);
				} else if (userid.startsWith("UO.") && idUo != null && !"".equals(idUo)) {
					flgUoCheckboxItem.setValue(true);
					String codRapidoUo = (String) record.getAttribute("codRapidoUo");						
					Boolean flgVisibSottoUo = record.getAttributeAsBoolean("flgVisibSottoUo");
					Boolean flgGestSottoUo = record.getAttributeAsBoolean("flgGestSottoUo");
					Record lRecordAsseganzioneUo = new Record();
					lRecordAsseganzioneUo.setAttribute("tipo", "UO");
					lRecordAsseganzioneUo.setAttribute("idUoSvUt", idUo);
					lRecordAsseganzioneUo.setAttribute("codRapidoUo", codRapidoUo);
					lRecordAsseganzioneUo.setAttribute("flgVisibileDaSottoUo", flgVisibSottoUo != null && flgVisibSottoUo);
					lRecordAsseganzioneUo.setAttribute("flgModificabileDaSottoUo", flgGestSottoUo != null && flgGestSottoUo);
					assegnazioneUoForms.setFormValuesFromRecord(lRecordAsseganzioneUo); 
				}
			}
			detailSectionSceltaUO.setVisible(isFlgUoChecked() && assegnazioneUoForms.isOrganigrammaFormVisible());	
			assegnazioneUoForms.editMode();
			redrawForms();
		}
	}

	public void saveModelloAction(Record record) {
		if(isTrasmissioneAtti()) {
			record.setAttribute("flgPubblico", true);
		}
		saveModelloAction.execute(record);
		hide();
	}

	@Override
	public void manageOnCloseClick() {
		hide();
	}

	public abstract boolean isAbilToSavePublic();
	
	public abstract boolean isTrasmissioneAtti();
	
	@Override
	protected void onDestroy() {
		if (vm != null) {
			try {
				vm.destroy();
			} catch (Exception e) {
			}
		}
		super.onDestroy();
	}
}