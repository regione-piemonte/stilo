/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;

import com.smartgwt.client.data.AdvancedCriteria;
import com.smartgwt.client.data.Criteria;
import com.smartgwt.client.data.Criterion;
import com.smartgwt.client.data.DSCallback;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.data.RecordList;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.FieldType;
import com.smartgwt.client.types.HeaderControls;
import com.smartgwt.client.types.OperatorId;
import com.smartgwt.client.types.Overflow;
import com.smartgwt.client.widgets.Button;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.ValuesManager;
import com.smartgwt.client.widgets.form.fields.ButtonItem;
import com.smartgwt.client.widgets.form.fields.FormItemCriteriaFunction;
import com.smartgwt.client.widgets.form.fields.FormItemFunctionContext;
import com.smartgwt.client.widgets.form.fields.SelectItem;
import com.smartgwt.client.widgets.form.fields.SpacerItem;
import com.smartgwt.client.widgets.form.fields.TextItem;
import com.smartgwt.client.widgets.form.validator.CustomValidator;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.layout.HStack;
import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.client.widgets.toolbar.ToolStrip;
import com.sun.xml.bind.v2.runtime.unmarshaller.XsiNilLoader.Array;

import it.eng.auriga.ui.module.layout.client.AurigaLayout;
import it.eng.auriga.ui.module.layout.client.i18n.I18NUtil;
import it.eng.utility.ui.module.core.client.datasource.GWTRestDataSource;
import it.eng.utility.ui.module.core.shared.message.MessageBean;
import it.eng.utility.ui.module.core.shared.message.MessageType;
import it.eng.utility.ui.module.layout.client.Layout;
import it.eng.utility.ui.module.layout.client.common.DetailSection;
import it.eng.utility.ui.module.layout.client.common.items.ComboBoxItem;
import it.eng.utility.ui.module.layout.client.common.items.ExtendedTextItem;
import it.eng.utility.ui.module.layout.client.common.items.FilteredSelectItem;
import it.eng.utility.ui.module.layout.client.portal.ModalWindow;

public class PostalizzaPopup extends ModalWindow{

	protected PostalizzaPopup _window;
	
	protected ProtocollazioneDetail protInstance;

	protected ValuesManager vm;
	protected Record recordProtocollazione;

	protected DetailSection detailSectionMittentiPostel;
	protected DetailSection detailSectionDestinatariPostel;
	protected DynamicForm destinatariPostelForm;
	protected DynamicForm mittentePostelForm;
	protected DynamicForm mittentePostelForm2;
	protected DynamicForm mittentePostelForm3;
	protected DynamicForm allegatiPostelForm;
	
	
	protected ToolStrip mainToolStrip;

	protected ButtonItem confermaButton;
	protected ButtonItem annullaButton;

	protected TextItem gruppoProtocollantePostelItem;

	protected SelectItem tipoToponimoMittentePostelItem;
	protected ExtendedTextItem toponimoMittentePostelItem;
	protected ExtendedTextItem civicoMittentePostelItem;
	protected FilteredSelectItem comuneMittentePostelItem;
	protected TextItem provinciaMittentePostelItem;
	protected ComboBoxItem capMittentePostelItem;
	

	
	public PostalizzaPopup(Record record, ProtocollazioneDetail instance){
		this(record, null, instance);
	}

	public PostalizzaPopup(Record record, DSCallback callback, ProtocollazioneDetail instance) {

		super("postalizzazione", true);

		_window = this;
		
		this.protInstance = instance;

		this.vm = new ValuesManager();

		this.recordProtocollazione = record;

		setTitle("Dati per la postalizzazione");
		setShowTitle(true);		
		setIsModal(true);
		setModalMaskOpacity(50);
		setAutoCenter(true);
		setKeepInParentRect(true);
		setShowModalMask(true);
		setShowMaximizeButton(true);
		setShowMinimizeButton(true);
		setWidth(700);
		setHeight(450);

		settingsMenu.removeItem(separatorMenuItem);
		settingsMenu.removeItem(autoSearchMenuItem);



		
		
		Button confermaButton = new Button("Ok");   
		confermaButton.setIcon("ok.png");
		confermaButton.setIconSize(16); 
		confermaButton.setAutoFit(false);
		confermaButton.addClickHandler(new com.smartgwt.client.widgets.events.ClickHandler() {			
			@Override
			public void onClick(com.smartgwt.client.widgets.events.ClickEvent event) {
				if(mittentePostelForm.validate() && mittentePostelForm2.validate() && mittentePostelForm3.validate()) {
					if (gruppoProtocollantePostelItem.getValueAsString().length()>44) 
						Layout.addMessage(new MessageBean("La lunghezza del nominativo supera i 44 caratteri, si provvederà a troncarlo", "", MessageType.WARNING));
					
					recordProtocollazione.setAttribute("gruppoProtocollantePostelMittente", gruppoProtocollantePostelItem.getValue());
					recordProtocollazione.setAttribute("tipoToponimoMittentePostel", tipoToponimoMittentePostelItem.getValue());
					recordProtocollazione.setAttribute("toponimoMittentePostel", toponimoMittentePostelItem.getValue());
					recordProtocollazione.setAttribute("civicoMittentePostel", civicoMittentePostelItem.getValue());
					recordProtocollazione.setAttribute("comuneMittentePostel", comuneMittentePostelItem.getDisplayValue());
					recordProtocollazione.setAttribute("provinciaMittentePostel", provinciaMittentePostelItem.getValue());
					recordProtocollazione.setAttribute("capMittentePostel", capMittentePostelItem.getValue());
					recordProtocollazione.setAttribute("listaAllegati", allegatiPostelForm.getRecordList().get(0).getAttributeAsRecordList("attach"));
					recordProtocollazione.setAttribute("listaDestinatari", destinatariPostelForm.getRecordList().get(0).getAttributeAsRecordList("listaDestinatari"));
					protInstance.clickInviaPostel(recordProtocollazione);
					_window.markForDestroy();
					}					
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
		_buttons.setAlign(Alignment.CENTER);
		_buttons.setPadding(5);
        _buttons.addMember(confermaButton);
		_buttons.addMember(annullaButton);	

		setAlign(Alignment.CENTER);
		setTop(50);
		


		//		################################################### LAYOUT ###################################################



		VLayout layout = new VLayout();
		layout.setHeight100();
		layout.setWidth100();
		layout.setOverflow(Overflow.AUTO);

		// Creo il VLAYOUT e gli aggiungo il TABSET
		VLayout portletLayout = new VLayout();
		portletLayout.setHeight100();
		portletLayout.setWidth100();
		portletLayout.setOverflow(Overflow.VISIBLE);


		layout.addMember(createDetailSectionMittentePostalizzazione());
		VLayout lspacerLayout = new VLayout();
		lspacerLayout.setHeight100();
		lspacerLayout.setWidth100();
		layout.addMember(createDetailSectionDestinatariPostalizzazione());
		layout.addMember(createDynamicFormAllegati());
		layout.setHeight100();
		layout.setWidth100();


		portletLayout.addMember(layout);
		portletLayout.addMember(_buttons);

		setBody(portletLayout);

		setIcon("archivio/assegna.png");
		
		setShowTitle(true);
		setHeaderControls(HeaderControls.HEADER_LABEL, HeaderControls.CLOSE_BUTTON);	
		
		vm = new ValuesManager();
		setValuesManager(vm);
	}
	
	
	
	
	
	/** DESTINATARI **/
	
	
	protected DetailSection createDetailSectionDestinatariPostalizzazione() {
		
		detailSectionDestinatariPostel = new DetailSection("Destinatari postalizzazione", true, true, true, createDestinatariForm()){
			@Override
			public boolean showFirstCanvasWhenEmptyAfterOpen() {return true;}

		};
		
		return detailSectionDestinatariPostel;
	}
	
	protected DynamicForm createDestinatariForm() {
		
		destinatariPostelForm = new PostelDestinatarioForm(recordProtocollazione);
		destinatariPostelForm.setMargin(10);
		return destinatariPostelForm; 
	}
	
	
	
	/** MITTENTE **/
	
	
	protected DetailSection createDetailSectionMittentePostalizzazione() {
		
		String uoLavoro = AurigaLayout.getUoLavoro();
		if(uoLavoro != null) {
			uoLavoro = uoLavoro.substring(2);
		}
		String[] uoMittenti = AurigaLayout.getParametroDB("UO_MITTENTI_POSTALIZZAZIONE").split(",");
		ArrayList<String> listaUoMittenti = new ArrayList<>(Arrays.asList(uoMittenti));
		if(uoLavoro != null && !listaUoMittenti.contains(uoLavoro)) {
			uoLavoro = "DEFAULT";
		}
		
		String mittentePostalizzazione = AurigaLayout.getParametroDB("MITTENTE_POSTALIZZAZIONE_" + uoLavoro);
		String[] sezioniMittente = mittentePostalizzazione.split("\\|");
		
		

		gruppoProtocollantePostelItem = new TextItem("gruppoProtocollantePostelMittente", "Mittente");
		gruppoProtocollantePostelItem.setWidth(200);
		gruppoProtocollantePostelItem.setShowTitle(true);
		gruppoProtocollantePostelItem.setValue(sezioniMittente[0]);
		gruppoProtocollantePostelItem.setCanEdit(true);
		CustomValidator lRequiredValidator = new CustomValidator() {

			@Override
			protected boolean condition(Object value) {
				String valueStr = (String) value;
				return valueStr != null && !"".equals(valueStr.trim());
			}
		};
		lRequiredValidator.setErrorMessage("Campo obbligatorio");		
		gruppoProtocollantePostelItem.setValidators(lRequiredValidator);

		tipoToponimoMittentePostelItem = new FilteredSelectItem("tipoToponimoMittentePostel");
		tipoToponimoMittentePostelItem.setValueField("key");
		tipoToponimoMittentePostelItem.setDisplayField("displayValue");
		ListGridField tipoToponimoField = new ListGridField("value", "Tipo");
		tipoToponimoMittentePostelItem.setPickListFields(tipoToponimoField);
		tipoToponimoMittentePostelItem.setTitle("Indirizzo");
		tipoToponimoMittentePostelItem.setShowTitle(true);
		tipoToponimoMittentePostelItem.setWidth(80);
		tipoToponimoMittentePostelItem.setAllowEmptyValue(false);
		tipoToponimoMittentePostelItem.setOptionDataSource(new GWTRestDataSource("TipoToponimoDataSource", "key", FieldType.TEXT, true));
		tipoToponimoMittentePostelItem.setAutoFetchData(false);
		tipoToponimoMittentePostelItem.setAlwaysFetchMissingValues(true);
		tipoToponimoMittentePostelItem.setFetchMissingValues(true);
		tipoToponimoMittentePostelItem.setDefaultValue("VIA");
		LinkedHashMap<String, String> tipoToponimoValueMap = new LinkedHashMap<String, String>();
		tipoToponimoValueMap.put("VIA", "VIA");
		tipoToponimoMittentePostelItem.setValueMap(tipoToponimoValueMap);
		tipoToponimoMittentePostelItem.setValidators(lRequiredValidator);

		toponimoMittentePostelItem = new ExtendedTextItem("toponimoMittentePostel");
		toponimoMittentePostelItem.setShowTitle(false);
		toponimoMittentePostelItem.setValue(sezioniMittente[1]);
		toponimoMittentePostelItem.setCanEdit(true);
		toponimoMittentePostelItem.setWidth(200);
		toponimoMittentePostelItem.setLength(30);
		toponimoMittentePostelItem.setValidators(lRequiredValidator);


		civicoMittentePostelItem = new ExtendedTextItem("civicoMittentePostel", I18NUtil.getMessages().soggetti_detail_indirizzi_civicoItem_title());
		civicoMittentePostelItem.setValue(sezioniMittente[2]);
		civicoMittentePostelItem.setCanEdit(true);
		civicoMittentePostelItem.setWidth(50);
		civicoMittentePostelItem.setLength(5);
		civicoMittentePostelItem.setValidators(lRequiredValidator);

		comuneMittentePostelItem = new FilteredSelectItem("comuneMittentePostel", I18NUtil.getMessages().soggetti_detail_indirizzi_comuneItem_title()) {

			@Override
			public void onOptionClick(Record record) {

				super.onOptionClick(record);

				mittentePostelForm3.setValue("nomeComune", record.getAttribute("nomeComune"));

				GWTRestDataSource comuneDS = (GWTRestDataSource) comuneMittentePostelItem.getOptionDataSource();
				comuneDS.addParam("nomeComune", record.getAttribute("nomeComune"));
				comuneMittentePostelItem.setOptionDataSource(comuneDS);
				if (record.getAttribute("nomeComune") != null && !"".equals(record.getAttributeAsString("nomeComune"))) {
					final String codIstatComune = record.getAttributeAsString("codIstatComune");
					Criterion[] criterias = new Criterion[1];
					criterias[0] = new Criterion("codIstatComune", OperatorId.IEQUALS, codIstatComune);
					comuneMittentePostelItem.getOptionDataSource().fetchData(new AdvancedCriteria(OperatorId.AND, criterias), new DSCallback() {

						@Override
						public void execute(DSResponse response, Object rawData, DSRequest request) {

							RecordList data = response.getDataAsRecordList();
							if (data.getLength() > 0) {
								for (int i = 0; i < data.getLength(); i++) {
									if (data.get(i).getAttribute("codIstatComune").equals(codIstatComune)) {
										provinciaMittentePostelItem.setValue(data.get(i).getAttribute("targaProvincia"));
										break;
									}
								}
							}
						}
					});
				}
				mittentePostelForm3.setValue("prov.", record.getAttribute("targaProvincia"));
				Criterion[] criterias = new Criterion[1];
				String value = record.getAttribute(comuneMittentePostelItem.getValueField());
				if (value != null && !"".equals(value)) {
					criterias[0] = new Criterion("codIstatComune", OperatorId.IEQUALS, value);
				}
			}

			@Override
			protected void clearSelect() {

				super.clearSelect();

				mittentePostelForm3.setValue("nomeComune", "");
				GWTRestDataSource comuneDS = (GWTRestDataSource) comuneMittentePostelItem.getOptionDataSource();
				comuneDS.addParam("nomeComune", null);
				comuneMittentePostelItem.setOptionDataSource(comuneDS);
				mittentePostelForm3.setValue("provincia", "");
			};

			@Override
			public void setValue(String value) {

				super.setValue(value);			

				if (value == null || "".equals(value)) {
					mittentePostelForm3.setValue("nomeComune", "");
					GWTRestDataSource comuneDS = (GWTRestDataSource) comuneMittentePostelItem.getOptionDataSource();
					comuneDS.addParam("nomeComune", null);
					comuneMittentePostelItem.setOptionDataSource(comuneDS);
					mittentePostelForm3.setValue("provincia", "");
				}
			}
		};
		ListGridField codIstatComuneField = new ListGridField("codIstatComune", "Cod. Istat");
		codIstatComuneField.setHidden(true);
		ListGridField nomeComuneField = new ListGridField("nomeComune", "Comune");
		ListGridField targaProvinciaField = new ListGridField("targaProvincia", "Prov.");
		targaProvinciaField.setWidth(50);
		comuneMittentePostelItem.setPickListFields(codIstatComuneField, nomeComuneField, targaProvinciaField);
		comuneMittentePostelItem.setEmptyPickListMessage(I18NUtil.getMessages().soggetti_detail_indirizzi_comuneItem_noSearchOrEmptyMessage());
		comuneMittentePostelItem.setValueField("codIstatComune");
		comuneMittentePostelItem.setDisplayField("nomeComune");
		comuneMittentePostelItem.setOptionDataSource(new GWTRestDataSource("ComuneDataSource", "codIstatComune", FieldType.TEXT, true));
		comuneMittentePostelItem.setWidth(220);
		comuneMittentePostelItem.setAutoFetchData(false);		
		comuneMittentePostelItem.setAlwaysFetchMissingValues(true);
		comuneMittentePostelItem.setFetchMissingValues(true);
		comuneMittentePostelItem.setValue(sezioniMittente[4]);
		comuneMittentePostelItem.setCanEdit(true);
		comuneMittentePostelItem.setValidators(lRequiredValidator);


		provinciaMittentePostelItem = new TextItem("provinciaMittentePostel", I18NUtil.getMessages().soggetti_detail_indirizzi_provinciaItem_title());
		provinciaMittentePostelItem.setWidth(50);
		provinciaMittentePostelItem.setValue(sezioniMittente[5]);
		provinciaMittentePostelItem.setCanEdit(false);
		provinciaMittentePostelItem.setLength(2);
		provinciaMittentePostelItem.setKeyPressFilter("[a-z]");
		provinciaMittentePostelItem.setValidators(lRequiredValidator);

		capMittentePostelItem = new ComboBoxItem("capMittentePostel", I18NUtil.getMessages().soggetti_detail_indirizzi_capItem_title());
		ListGridField capField = new ListGridField("cap", "Cap");
		capMittentePostelItem.setPickListFields(capField);
		capMittentePostelItem.setValueField("cap");
		capMittentePostelItem.setDisplayField("cap");
		capMittentePostelItem.setOptionDataSource(new GWTRestDataSource("CapDataSource", "cap", FieldType.TEXT));
		capMittentePostelItem.setWidth(80);
		capMittentePostelItem.setAllowEmptyValue(true);
		capMittentePostelItem.setCachePickListResults(false);
		capMittentePostelItem.setAutoFetchData(false);
		capMittentePostelItem.setLength(5);
		capMittentePostelItem.setKeyPressFilter("[0-9]");
		capMittentePostelItem.setAlwaysFetchMissingValues(true);
		capMittentePostelItem.setFetchMissingValues(true);
		capMittentePostelItem.setAddUnknownValues(true);
		capMittentePostelItem.setCompleteOnTab(false);
		capMittentePostelItem.setValue(sezioniMittente[3]);
		capMittentePostelItem.setCanEdit(true);
		capMittentePostelItem.setPickListFilterCriteriaFunction(new FormItemCriteriaFunction() {

			@Override
			public Criteria getCriteria(FormItemFunctionContext itemContext) {

				Criterion[] criterias = new Criterion[1];

				String comune = null;
				if (comuneMittentePostelItem.getValue() != null && !"".equals(comuneMittentePostelItem.getValueAsString())) {
					comune = comuneMittentePostelItem.getValueAsString();
				}
				if (comune != null) {
					criterias[0] = new Criterion("codIstatComune", OperatorId.IEQUALS, comune);
				} else {
					criterias[0] = new Criterion("cap", OperatorId.IS_NULL);
				}
				return new AdvancedCriteria(OperatorId.AND, criterias);
			}
		});
		capMittentePostelItem.setValidators(lRequiredValidator);




		detailSectionMittentiPostel = new DetailSection("Mittente postalizzazione", true, true, true, createDynamicFormGruppoProtocollante(), createDynamicFormIndirizzoParte1(), createDynamicFormIndirizzoParte2()){
			@Override
			public boolean showFirstCanvasWhenEmptyAfterOpen() {return true;}

		};

		detailSectionMittentiPostel.setViewReplicableItemHeight(450);

		return detailSectionMittentiPostel;
	}
	
	
	protected DynamicForm createDynamicFormGruppoProtocollante() {
		
		mittentePostelForm = new DynamicForm();
		mittentePostelForm.setValuesManager(vm);
		mittentePostelForm.setWidth("100%");
		mittentePostelForm.setPadding(5);
		mittentePostelForm.setNumCols(6);
		mittentePostelForm.setColWidths(10, 10 ,10 ,10 ,10 ,10);
		mittentePostelForm.setFields(gruppoProtocollantePostelItem);
		
		return mittentePostelForm;
	}
	
	protected DynamicForm createDynamicFormIndirizzoParte1() {
		
		mittentePostelForm2 = new DynamicForm();
		mittentePostelForm2.setValuesManager(vm);
		mittentePostelForm2.setWidth("100%");
		mittentePostelForm2.setNumCols(6);
		mittentePostelForm2.setColWidths(10, 10 ,10 ,10 ,10 ,10);
		mittentePostelForm2.setFields(tipoToponimoMittentePostelItem, toponimoMittentePostelItem, civicoMittentePostelItem);
		return mittentePostelForm2;
	}
	
	protected DynamicForm createDynamicFormIndirizzoParte2() {
		
		mittentePostelForm3 = new DynamicForm();
		mittentePostelForm3.setValuesManager(vm);
		mittentePostelForm3.setWidth("100%");
		mittentePostelForm3.setNumCols(6);
		mittentePostelForm3.setColWidths(10, 10 ,10 ,10 ,10 ,10);
		mittentePostelForm3.setFields(comuneMittentePostelItem, provinciaMittentePostelItem, capMittentePostelItem);
		return mittentePostelForm3;
		
	}
	
	
	/** ALLEGATI **/
	
	
	protected DynamicForm createDynamicFormAllegati() {
		
		allegatiPostelForm = new PostelAttachmentForm(recordProtocollazione);
		allegatiPostelForm.setMargin(10);
		return allegatiPostelForm;
	}


	
}


