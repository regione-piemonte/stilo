/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.LinkedHashMap;

import com.smartgwt.client.data.Record;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.FieldType;
import com.smartgwt.client.types.Overflow;
import com.smartgwt.client.util.BooleanCallback;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.Button;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.FormItemHoverFormatter;
import com.smartgwt.client.widgets.form.FormItemIfFunction;
import com.smartgwt.client.widgets.form.ValuesManager;
import com.smartgwt.client.widgets.form.fields.ButtonItem;
import com.smartgwt.client.widgets.form.fields.FormItem;
import com.smartgwt.client.widgets.form.fields.HiddenItem;
import com.smartgwt.client.widgets.form.fields.RadioGroupItem;
import com.smartgwt.client.widgets.form.fields.events.ChangeEvent;
import com.smartgwt.client.widgets.form.fields.events.ChangeHandler;
import com.smartgwt.client.widgets.form.fields.events.ChangedEvent;
import com.smartgwt.client.widgets.form.fields.events.ChangedHandler;
import com.smartgwt.client.widgets.form.validator.CustomValidator;
import com.smartgwt.client.widgets.form.validator.RequiredIfFunction;
import com.smartgwt.client.widgets.form.validator.RequiredIfValidator;
import com.smartgwt.client.widgets.layout.HStack;
import com.smartgwt.client.widgets.layout.VLayout;

import it.eng.auriga.ui.module.layout.client.postaElettronica.DettaglioRegProtAssociatoWindow;
import it.eng.utility.ui.module.core.client.callback.ServiceCallback;
import it.eng.utility.ui.module.core.client.datasource.GWTRestDataSource;
import it.eng.utility.ui.module.core.client.datasource.GWTRestService;
import it.eng.utility.ui.module.core.shared.message.MessageBean;
import it.eng.utility.ui.module.core.shared.message.MessageType;
import it.eng.utility.ui.module.layout.client.Layout;
import it.eng.utility.ui.module.layout.client.common.DetailSection;
import it.eng.utility.ui.module.layout.client.common.filter.item.AnnoItem;
import it.eng.utility.ui.module.layout.client.common.items.ExtendedNumericItem;
import it.eng.utility.ui.module.layout.client.common.items.NumericItem;
import it.eng.utility.ui.module.layout.client.common.items.SelectItem;
import it.eng.utility.ui.module.layout.client.common.items.TextAreaItem;
import it.eng.utility.ui.module.layout.client.common.items.TextItem;
import it.eng.utility.ui.module.layout.client.portal.ModalWindow;

public class RichiestaAccessoDocumentoPopUp extends ModalWindow {


	protected RichiestaAccessoDocumentoPopUp _window;
	
	protected ValuesManager vm;
	
	protected DetailSection detailSectionEstremiDocumento;
	protected DetailSection detailSectionAccessoPer;
	protected DetailSection detailSectionMotivazioni;
	
	protected DynamicForm estremiDocumentoForm;
	protected DynamicForm accessoPerForm;
	protected DynamicForm motivazioniForm;
	
	//ESTREMI DOCUMENTO
	private SelectItem tipoItem;
	private TextItem siglaRegistroItem;
	private AnnoItem annoItem;
	private NumericItem numeroItem;
	private NumericItem subItem;
	
	//MOTIVAZIONI
	private TextAreaItem motivazioniItem;
	
	//ACCESSO PER
	protected RadioGroupItem tipoAccessoItem;
	
	protected ButtonItem confermaButton;
	protected ButtonItem annullaButton;
	
	
	public RichiestaAccessoDocumentoPopUp() {

		super("richiesta_accesso_documento", true);

		_window = this;
		
		vm = new ValuesManager();		

		setTitle("Seleziona documento per cui richiedere accesso");

		setAutoCenter(true);
		setHeight(480);
		setWidth(780);
		setAlign(Alignment.CENTER);
		
		settingsMenu.removeItem(separatorMenuItem);
		settingsMenu.removeItem(autoSearchMenuItem);
		
		buildEstremiDocumentoForm();
		
		detailSectionEstremiDocumento =  new DetailSection("Estremi documento", false, true, true, estremiDocumentoForm);
		
		buildAccessoPerForm();
		
		detailSectionAccessoPer = new DetailSection("Accesso per", false, true, true, accessoPerForm);
		
		buildMotivazioniForm();
		
		detailSectionMotivazioni = new DetailSection("Motivazioni", false, true, true, motivazioniForm);

		Button confermaButton = new Button("Procedi");
		confermaButton.setIcon("ok.png");
		confermaButton.setIconSize(16);
		confermaButton.setAutoFit(false);
		confermaButton.addClickHandler(new com.smartgwt.client.widgets.events.ClickHandler() {

			@Override
			public void onClick(com.smartgwt.client.widgets.events.ClickEvent event) {
				manageOnClickButton();
			}
		});

		Button annullaButton = new Button("Annulla");
		annullaButton.setIcon("annulla.png");
		annullaButton.setIconSize(16);
		annullaButton.setAutoFit(false);
		annullaButton.addClickHandler(new com.smartgwt.client.widgets.events.ClickHandler() {

			@Override
			public void onClick(com.smartgwt.client.widgets.events.ClickEvent event) {
				_window.markForDestroy();
			}
		});

		HStack _buttons = new HStack(5);
		_buttons.setHeight(30);
		_buttons.setAlign(Alignment.CENTER);
		_buttons.setPadding(5);
		_buttons.addMember(confermaButton);
		_buttons.addMember(annullaButton);	
		
		VLayout layout = new VLayout();
		layout.setHeight100();
		layout.setWidth100();
		layout.setOverflow(Overflow.AUTO);
		
		// Creo il VLAYOUT e gli aggiungo il TABSET 
		VLayout portletLayout = new VLayout();  
		portletLayout.setHeight100();
		portletLayout.setWidth100();
		portletLayout.setOverflow(Overflow.VISIBLE);
		
		layout.addMember(detailSectionEstremiDocumento);
		layout.addMember(detailSectionAccessoPer);
		layout.addMember(detailSectionMotivazioni);
			
		VLayout spacerLayout = new VLayout();
		spacerLayout.setHeight100();
		spacerLayout.setWidth100();
		layout.addMember(spacerLayout);

		portletLayout.addMember(layout);		
		portletLayout.addMember(_buttons);
						
		setBody(portletLayout);
	
		setIcon("blank.png");
	}


	private void buildAccessoPerForm() {
		accessoPerForm = new DynamicForm();
		accessoPerForm.setValuesManager(vm);
		accessoPerForm.setKeepInParentRect(true);
		accessoPerForm.setWidth100();
		accessoPerForm.setHeight100();
		accessoPerForm.setNumCols(10);
		accessoPerForm.setColWidths(1, 1, 1, 1, 1, 1, 1, 1, 1, "*");
		accessoPerForm.setCellPadding(5);
		accessoPerForm.setWrapItemTitles(false);
		
		
		LinkedHashMap<String, String> flgUtenteInternoEsternoMap = new LinkedHashMap<String, String>();  
		flgUtenteInternoEsternoMap.put("C", "consultazione");  
		flgUtenteInternoEsternoMap.put("T", "trattazione");  
		tipoAccessoItem = new RadioGroupItem("tipoAccesso", "Accesso per"); 
		tipoAccessoItem.setShowTitle(false);
		tipoAccessoItem.setValueMap(flgUtenteInternoEsternoMap); 
		tipoAccessoItem.setVertical(false);
		tipoAccessoItem.setWrap(false);	
//		tipologiaAccessoItem.setTitleOrientation(TitleOrientation.LEFT);
		tipoAccessoItem.setWidth(120);
		tipoAccessoItem.setColSpan(9);
		tipoAccessoItem.setAttribute("obbligatorio", true);
		tipoAccessoItem.setRequired(true);
		tipoAccessoItem.setStartRow(true);
		
		accessoPerForm.setFields(tipoAccessoItem);
		
		accessoPerForm.setNumCols(30);
		accessoPerForm.setColWidths("50", "100","50", "100","50", "100","50", "100","50", "100","50", "100","50", "100","50", "100", "50", "100", "50", "100", "50", "100", "50", "100", "50", "100", "50", "100", "50", "100");	
	}


	private void buildMotivazioniForm() {
		
		motivazioniForm = new DynamicForm();
		motivazioniForm.setValuesManager(vm);
		motivazioniForm.setKeepInParentRect(true);
		motivazioniForm.setWidth100();
		motivazioniForm.setHeight100();
		motivazioniForm.setNumCols(10);
		motivazioniForm.setColWidths(1, 1, 1, 1, 1, 1, 1, 1, 1, "*");
//		motivazioniForm.setCellPadding(5);
		motivazioniForm.setWrapItemTitles(false);
		
		motivazioniItem = new TextAreaItem("motivazioni", "Motivazioni");
		motivazioniItem.setShowTitle(false);
		motivazioniItem.setHeight(80);
		motivazioniItem.setWidth(650);
		motivazioniItem.setStartRow(true);
		motivazioniItem.setColSpan(20);
		motivazioniItem.setRequired(true);
		motivazioniItem.setAttribute("obbligatorio", true);
		CustomValidator lRequiredValidator = new CustomValidator() {
			
			@Override
			protected boolean condition(Object value) {
				String valueStr = (String) value;
				return valueStr != null && !"".equals(valueStr.trim());
			}
		};
		lRequiredValidator.setErrorMessage("Campo obbligatorio");		
		motivazioniItem.setValidators(lRequiredValidator);
		
		motivazioniForm.setFields(motivazioniItem);
		
		motivazioniForm.setNumCols(30);
		motivazioniForm.setColWidths("50", "100","50", "100","50", "100","50", "100","50", "100","50", "100","50", "100","50", "100", "50", "100", "50", "100", "50", "100", "50", "100", "50", "100", "50", "100", "50", "100");	
	
	}


	private void buildEstremiDocumentoForm() {
		
		estremiDocumentoForm = new DynamicForm();
		estremiDocumentoForm.setValuesManager(vm);
		estremiDocumentoForm.setKeepInParentRect(true);
		estremiDocumentoForm.setWidth100();
		estremiDocumentoForm.setHeight100();
		estremiDocumentoForm.setNumCols(10);
		estremiDocumentoForm.setColWidths(1, 1, 1, 1, 1, 1, 1, 1, 1, "*");
		estremiDocumentoForm.setCellPadding(5);
		estremiDocumentoForm.setWrapItemTitles(false);
		
		GWTRestDataSource tipoDS = new GWTRestDataSource("LoadComboTipoDocCollegatoDataSource", "key", FieldType.TEXT);

		tipoItem = new SelectItem("tipo", "Tipo");
		tipoItem.setRequired(true);		
//		LinkedHashMap<String, String> tipoValueMap = new LinkedHashMap<String, String>();
//		tipoValueMap.put("PG", "Prot. Generale");
//		tipoValueMap.put("PI", "Prot. Interno");
//		tipoValueMap.put("NI", "Bozza");
//		tipoValueMap.put("PP", "Protocollo Particolare");
//		tipoValueMap.put("R", "Repertorio");
//		tipoItem.setValueMap(tipoValueMap);		
		tipoItem.setOptionDataSource(tipoDS);
		tipoItem.setAutoFetchData(true);
		tipoItem.setDisplayField("value");
		tipoItem.setValueField("key");
		tipoItem.setDefaultValue("PG");
		tipoItem.setWidth(120);
		tipoItem.setWrapTitle(false);
		tipoItem.setColSpan(1);
		tipoItem.setStartRow(true);
		tipoItem.addChangedHandler(new ChangedHandler() {
			@Override
			public void onChanged(ChangedEvent event) {
				estremiDocumentoForm.markForRedraw();
			}
		});
			
		siglaRegistroItem = new TextItem("siglaRegistro", "Registro (sigla)");
		siglaRegistroItem.setWidth(100);
		siglaRegistroItem.setColSpan(1);
		siglaRegistroItem.setAttribute("obbligatorio", true);
		siglaRegistroItem.setValidators(new RequiredIfValidator(new RequiredIfFunction() {
				
			@Override
			public boolean execute(FormItem formItem, Object value) {
				return tipoItem.getValueAsString() != null && ("R".equalsIgnoreCase(tipoItem.getValueAsString()) || "PP".equalsIgnoreCase(tipoItem.getValueAsString()));
			}
		}));	
		siglaRegistroItem.setShowIfCondition(new FormItemIfFunction() {
				
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return tipoItem.getValueAsString() != null && ("R".equalsIgnoreCase(tipoItem.getValueAsString()) || "PP".equalsIgnoreCase(tipoItem.getValueAsString()));
			}
		});
				
		annoItem = new AnnoItem("anno", "Anno");
		annoItem.setRequired(true);		
		annoItem.setColSpan(1);
		
		numeroItem = new NumericItem("numero", "N.ro", false);		
		numeroItem.setRequired(true);
		numeroItem.setColSpan(1);
		numeroItem.setLength(7);
		
		subItem = new NumericItem("sub", "Sub", false);
		subItem.setWidth(80);
		subItem.setColSpan(1);
		subItem.setLength(3);
		subItem.setShowIfCondition(new FormItemIfFunction() {

			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {				
				return tipoItem.getValueAsString() != null && "PP".equalsIgnoreCase(tipoItem.getValueAsString());				
			}
		});
		
		estremiDocumentoForm.setFields(
				tipoItem, 
				siglaRegistroItem,
				annoItem,
				numeroItem,
				subItem
		);

		estremiDocumentoForm.setNumCols(30);
		estremiDocumentoForm.setColWidths("50", "100","50", "100","50", "100","50", "100","50", "100","50", "100","50", "100","50", "100", "50", "100", "50", "100", "50", "100", "50", "100", "50", "100", "50", "100", "50", "100");	
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
	
	protected String setTitleAlign(String title, int width, boolean required) {
		if (title != null && title.startsWith("<span")) {
			return title;
		}
		return "<span style=\"width: " + (required ? width : width + 1) + "px; display: inline-block;\">" + title + "</span>";
	}
	
	private void manageOnClickButton() {

		if (vm.validate()) {
			final Record record = new Record(vm.getValues());
			GWTRestService<Record, Record> lGWTRestService = new GWTRestService<Record, Record>("RichiestaAccessoDocDatasource");
			lGWTRestService.call(record, new ServiceCallback<Record>() {
				@Override
				public void execute(final Record object) {
					if (object.getAttributeAsString("storeErrorMessage") != null && !"".equals(object.getAttributeAsString("storeErrorMessage"))) {
						Layout.addMessage(new MessageBean(object.getAttributeAsString("storeErrorMessage"), "",MessageType.ERROR));
					} else {
						SC.ask("Accesso consentito. Vuoi accedere al dettaglio del documento ?",new BooleanCallback() {

							@Override
							public void execute(Boolean value) {
								if (value) {
									String idUd = object.getAttributeAsString("idUd");
									Record recordForDetail = new Record();
									recordForDetail.setAttribute("idUd", idUd);
									new DettaglioRegProtAssociatoWindow(recordForDetail,"Dettaglio documento "+ record.getAttributeAsString("siglaRegistro") + " "+ record.getAttributeAsString("numero") + "/"+ record.getAttributeAsString("anno"));
								}
							}
						});
					}
				}
			});
		}

	}

}
