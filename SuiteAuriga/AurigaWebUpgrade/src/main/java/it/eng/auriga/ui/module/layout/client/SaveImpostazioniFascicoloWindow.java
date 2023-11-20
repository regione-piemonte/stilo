/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import com.google.gwt.core.client.Scheduler;
import com.google.gwt.core.client.Scheduler.ScheduledCommand;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.FieldType;
import com.smartgwt.client.types.Overflow;
import com.smartgwt.client.widgets.Button;
import com.smartgwt.client.widgets.events.CloseClickEvent;
import com.smartgwt.client.widgets.events.CloseClickHandler;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.ValuesManager;
import com.smartgwt.client.widgets.form.fields.HiddenItem;
import com.smartgwt.client.widgets.layout.HStack;
import com.smartgwt.client.widgets.layout.VLayout;

import it.eng.auriga.ui.module.layout.client.i18n.I18NUtil;
import it.eng.utility.ui.module.core.client.datasource.GWTRestDataSource;
import it.eng.utility.ui.module.layout.client.Layout;
import it.eng.utility.ui.module.layout.client.common.DetailSection;
import it.eng.utility.ui.module.layout.client.common.items.CheckboxItem;
import it.eng.utility.ui.module.layout.client.common.items.SelectItem;
import it.eng.utility.ui.module.layout.client.portal.ModalWindow;

/**
 * 
 * @author DANCRIST
 *
 */

public class SaveImpostazioniFascicoloWindow extends ModalWindow {
	
	protected DetailSection detailSectionFascicolo;
	private DynamicForm fascicoloForm;
	private SelectItem idFolderTypeFascicoloItem;
	private HiddenItem descFolderTypeFascicoloItem;
	private HiddenItem templateNomeFolderFascicoloItem;
	private HiddenItem flgTipoFolderConVieFascicoloItem;
	private CheckboxItem skipSceltaTipologiaFascicoloItem;
	
	protected DetailSection detailSectionCartella;
	private DynamicForm cartellaForm;
	private SelectItem idFolderTypeCartellaItem;
	private HiddenItem descFolderTypeCartellaItem;
	private HiddenItem templateNomeFolderCartellaItem;
	private HiddenItem flgTipoFolderConVieCartellaItem;
	private CheckboxItem skipSceltaTipologiaCartellaItem;
	
	protected DetailSection detailSectionPraticaPregressa;
	private DynamicForm praticaPregressaForm;
	private SelectItem idFolderTypePregressoItem;
	private HiddenItem descFolderTypePregressoItem;
	private HiddenItem templateNomeFolderPregressoItem;
	private HiddenItem flgTipoFolderConViePregressoItem;
	private HiddenItem dictionaryEntrySezionePregressoItem;
	private CheckboxItem skipSceltaTipologiaPregressoItem;
	
	private ValuesManager vm;
	
	public SaveImpostazioniFascicoloWindow() {
		super("config_utente_impostazioniFascicolo", true);
		
		vm = new ValuesManager();
		
		settingsMenu.removeItem(separatorMenuItem);
		settingsMenu.removeItem(autoSearchMenuItem);

		setTitle(I18NUtil.getMessages().configUtenteMenuImpostazioniFascicolo_title());
		setIcon("archivio/flgUdFolder/F.png");

		setWidth(1000);
		setHeight(600);
		setAlign(Alignment.CENTER);
		setTop(50);

		VLayout layout = new VLayout();
		layout.setHeight100();
		layout.setWidth100();
		layout.setOverflow(Overflow.AUTO);
		
		VLayout portletLayout = new VLayout();
		portletLayout.setHeight100();
		portletLayout.setWidth100();
		portletLayout.setOverflow(Overflow.VISIBLE);
		
		VLayout spacerLayout = new VLayout();
		spacerLayout.setHeight100();
		spacerLayout.setWidth100();

		if(Layout.isPrivilegioAttivo("GRD/FLD/I")) {						
			buildFormFascicolo();
			detailSectionFascicolo = new DetailSection(I18NUtil.getMessages().configUtenteImpostazioniFascicolo_detailSectionFascicolo_title(),
					true, true, false, fascicoloForm);		
			layout.addMember(detailSectionFascicolo);
			buildFormCartella();
			detailSectionCartella = new DetailSection(I18NUtil.getMessages().configUtenteImpostazioniFascicolo_detailSectionCartella_title(),
					true, true, false, cartellaForm);		
			layout.addMember(detailSectionCartella);
		}
		
		if(Layout.isPrivilegioAttivo("GRD/FLD/IPP")) {			
			buildFormPraticaPregressa();
			detailSectionPraticaPregressa = new DetailSection(I18NUtil.getMessages().configUtenteImpostazioniFascicolo_detailSectionPraticaPregressa_title(),
					true, true, false, praticaPregressaForm);		
			layout.addMember(detailSectionPraticaPregressa);
		}
		
		layout.addMember(spacerLayout);

		portletLayout.addMember(layout);
		
		Button okButton = new Button("Ok");
		okButton.setIcon("ok.png");
		okButton.setIconSize(16);
		okButton.setAutoFit(false);
		okButton.addClickHandler(new com.smartgwt.client.widgets.events.ClickHandler() {

			@Override
			public void onClick(com.smartgwt.client.widgets.events.ClickEvent event) {
				Scheduler.get().scheduleDeferred(new ScheduledCommand() {

					@Override
					public void execute() {
						vm.clearErrors(true);				
						Record record = new Record(vm.getValues());
						manageOnOkButtonClick(record);
						markForDestroy();
					}
				});							
			}
		});
		
		HStack _buttons = new HStack(5);
		_buttons.setHeight(30);
		_buttons.setAlign(Alignment.CENTER);
		_buttons.setPadding(5);
		_buttons.addMember(okButton);
		
		portletLayout.addMember(_buttons);

		addCloseClickHandler(new CloseClickHandler() {

			@Override
			public void onCloseClick(CloseClickEvent event) {
				markForDestroy();
			}
		});

		addItem(portletLayout);
	}
	
	private void buildFormFascicolo(){
		
		fascicoloForm = new DynamicForm();
		fascicoloForm.setKeepInParentRect(true);
		fascicoloForm.setWrapItemTitles(false);
		fascicoloForm.setNumCols(5);
		fascicoloForm.setColWidths(10, 10, 10, 10, "*");
		fascicoloForm.setPadding(5);
		fascicoloForm.setAlign(Alignment.LEFT);
		fascicoloForm.setTop(50);
		fascicoloForm.setValuesManager(vm);
		
		descFolderTypeFascicoloItem = new HiddenItem("descFolderTypeFascicolo");
		templateNomeFolderFascicoloItem = new HiddenItem("templateNomeFolderFascicolo");
		flgTipoFolderConVieFascicoloItem = new HiddenItem("flgTipoFolderConVieFascicolo");
		
		final GWTRestDataSource idFolderTypeDS = new GWTRestDataSource("LoadComboTipoFolderDataSource", "idFolderType", FieldType.TEXT);
		
		idFolderTypeFascicoloItem = new SelectItem("idFolderTypeFascicolo") {

			@Override
			public void onOptionClick(Record record) {
				idFolderTypeFascicoloItem.setValue(record.getAttribute("idFolderType"));
				descFolderTypeFascicoloItem.setValue(record.getAttribute("descFolderType"));
				templateNomeFolderFascicoloItem.setValue(record.getAttribute("templateNomeFolder"));
				flgTipoFolderConVieFascicoloItem.setValue(record.getAttributeAsBoolean("flgTipoFolderConVie"));
			}
		};
		idFolderTypeFascicoloItem.setShowTitle(true);
		idFolderTypeFascicoloItem.setTitle(setTitleAlign(I18NUtil.getMessages().configUtenteImpostazioniFascicolo_idFolderTypeFascicolo_title(),
				200, false));
		idFolderTypeFascicoloItem.setStartRow(true);
		idFolderTypeFascicoloItem.setWidth(300);
		idFolderTypeFascicoloItem.setColSpan(2);
		idFolderTypeFascicoloItem.setAlign(Alignment.CENTER);
		idFolderTypeFascicoloItem.setValueField("idFolderType");
		idFolderTypeFascicoloItem.setDisplayField("descFolderType");
		idFolderTypeFascicoloItem.setOptionDataSource(idFolderTypeDS);
		idFolderTypeFascicoloItem.setAllowEmptyValue(true);
		
		skipSceltaTipologiaFascicoloItem = new CheckboxItem("skipSceltaTipologiaFascicolo", 
				setTitleAlign(I18NUtil.getMessages().configUtenteImpostazioniFascicolo_skipSceltaTipologiaFascicolo_title(), 180, false));
		skipSceltaTipologiaFascicoloItem.setStartRow(false);
		
		fascicoloForm.setFields(
			idFolderTypeFascicoloItem,
			descFolderTypeFascicoloItem,
			templateNomeFolderFascicoloItem,
			flgTipoFolderConVieFascicoloItem,
			skipSceltaTipologiaFascicoloItem);
	}

	private void buildFormCartella(){
		
		cartellaForm = new DynamicForm();
		cartellaForm.setKeepInParentRect(true);
		cartellaForm.setWrapItemTitles(false);
		cartellaForm.setNumCols(5);
		cartellaForm.setColWidths(10, 10, 10, 10, "*");
		cartellaForm.setPadding(5);
		cartellaForm.setAlign(Alignment.LEFT);
		cartellaForm.setTop(50);
		cartellaForm.setValuesManager(vm);
		
		descFolderTypeCartellaItem = new HiddenItem("descFolderTypeCartella");
		templateNomeFolderCartellaItem = new HiddenItem("templateNomeFolderCartella");
		flgTipoFolderConVieCartellaItem = new HiddenItem("flgTipoFolderConVieCartella");
		
		final GWTRestDataSource idFolderTypeDS = new GWTRestDataSource("LoadComboTipoFolderDataSource", "idFolderType", FieldType.TEXT);
		
		idFolderTypeCartellaItem = new SelectItem("idFolderTypeCartella") {
	
			@Override
			public void onOptionClick(Record record) {
				idFolderTypeCartellaItem.setValue(record.getAttribute("idFolderType"));
				descFolderTypeCartellaItem.setValue(record.getAttribute("descFolderType"));
				templateNomeFolderCartellaItem.setValue(record.getAttribute("templateNomeFolder"));
				flgTipoFolderConVieCartellaItem.setValue(record.getAttributeAsBoolean("flgTipoFolderConVie"));
			}
		};
		idFolderTypeCartellaItem.setShowTitle(true);
		idFolderTypeCartellaItem.setTitle(setTitleAlign(I18NUtil.getMessages().configUtenteImpostazioniFascicolo_idFolderTypeCartella_title(),
				200, false));
		idFolderTypeCartellaItem.setStartRow(true);
		idFolderTypeCartellaItem.setWidth(300);
		idFolderTypeCartellaItem.setColSpan(2);
		idFolderTypeCartellaItem.setAlign(Alignment.CENTER);
		idFolderTypeCartellaItem.setValueField("idFolderType");
		idFolderTypeCartellaItem.setDisplayField("descFolderType");
		idFolderTypeCartellaItem.setOptionDataSource(idFolderTypeDS);
		idFolderTypeCartellaItem.setAllowEmptyValue(true);
		
		skipSceltaTipologiaCartellaItem = new CheckboxItem("skipSceltaTipologiaCartella", 
				setTitleAlign(I18NUtil.getMessages().configUtenteImpostazioniFascicolo_skipSceltaTipologiaCartella_title(), 180, false));
		skipSceltaTipologiaCartellaItem.setStartRow(false);
		
		cartellaForm.setFields(
			idFolderTypeCartellaItem,
			descFolderTypeCartellaItem,
			templateNomeFolderCartellaItem,
			flgTipoFolderConVieCartellaItem,
			skipSceltaTipologiaCartellaItem);
	}
	
	private void buildFormPraticaPregressa(){
		
		praticaPregressaForm = new DynamicForm();
		praticaPregressaForm.setKeepInParentRect(true);
		praticaPregressaForm.setWrapItemTitles(false);
		praticaPregressaForm.setNumCols(5);
		praticaPregressaForm.setColWidths(10, 10, 10, 10, "*");
		praticaPregressaForm.setPadding(5);
		praticaPregressaForm.setAlign(Alignment.LEFT);
		praticaPregressaForm.setTop(50);
		praticaPregressaForm.setValuesManager(vm);
		
		descFolderTypePregressoItem = new HiddenItem("descFolderTypePregresso");
		templateNomeFolderPregressoItem = new HiddenItem("templateNomeFolderPregresso");
		flgTipoFolderConViePregressoItem = new HiddenItem("flgTipoFolderConViePregresso");
		dictionaryEntrySezionePregressoItem = new HiddenItem("dictionaryEntrySezionePregresso");
		
		final GWTRestDataSource idFolderTypeDS = new GWTRestDataSource("LoadComboTipoFolderDataSource", "idFolderType", FieldType.TEXT);
		
		idFolderTypePregressoItem = new SelectItem("idFolderTypePregresso") {

			@Override
			public void onOptionClick(Record record) {
				
				idFolderTypePregressoItem.setValue(record.getAttribute("idFolderType"));
				descFolderTypePregressoItem.setValue(record.getAttribute("descFolderType"));
				templateNomeFolderPregressoItem.setValue(record.getAttribute("templateNomeFolder"));
				flgTipoFolderConViePregressoItem.setValue(record.getAttributeAsBoolean("flgTipoFolderConVie"));
				dictionaryEntrySezionePregressoItem.setValue(record.getAttribute("dictionaryEntrySezione"));
			}
		};
		idFolderTypePregressoItem.setShowTitle(true);
		idFolderTypePregressoItem.setTitle(setTitleAlign(I18NUtil.getMessages().configUtenteImpostazioniFascicolo_idFolderTypePregresso_title(),
				200, false));
		idFolderTypePregressoItem.setStartRow(true);
		idFolderTypePregressoItem.setWidth(300);
		idFolderTypePregressoItem.setColSpan(2);
		idFolderTypePregressoItem.setAlign(Alignment.CENTER);
		idFolderTypePregressoItem.setValueField("idFolderType");
		idFolderTypePregressoItem.setDisplayField("descFolderType");
		idFolderTypePregressoItem.setOptionDataSource(idFolderTypeDS);
		idFolderTypePregressoItem.setAllowEmptyValue(true);
		
		skipSceltaTipologiaPregressoItem = new CheckboxItem("skipSceltaTipologiaPregresso", 
				setTitleAlign(I18NUtil.getMessages().configUtenteImpostazioniFascicolo_skipSceltaTipologiaPregresso_title(), 180, false));
		skipSceltaTipologiaPregressoItem.setStartRow(false);
		
		praticaPregressaForm.setFields(
			idFolderTypePregressoItem,
			descFolderTypePregressoItem,
			templateNomeFolderPregressoItem,
			flgTipoFolderConViePregressoItem,
			dictionaryEntrySezionePregressoItem,
			skipSceltaTipologiaPregressoItem);
	}
	
	public void clearValues() {
		
		if(fascicoloForm != null) {
			fascicoloForm.clearValues();
		}
		if(cartellaForm != null) {
			cartellaForm.clearValues();
		}
		if(praticaPregressaForm != null) {
			praticaPregressaForm.clearValues();
		}
	}

	public void setValues(Record values) {
		if (values != null) {	
			vm.editRecord(values);
		} else {
			vm.editNewRecord();
		}
		vm.clearErrors(true);
	}
	
	protected String setTitleAlign(String title, int width, boolean required) {
		if (title != null && title.startsWith("<span")) {
			return title;
		}
		return "<span style=\"width: " + (required ? width : width + 1) + "px; display: inline-block;\">" + title + "</span>";
	}

	public void manageOnOkButtonClick(Record values) {

	}
	
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