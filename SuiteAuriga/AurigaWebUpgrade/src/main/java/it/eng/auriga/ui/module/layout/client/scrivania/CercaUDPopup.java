/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.core.client.Scheduler;
import com.google.gwt.core.client.Scheduler.ScheduledCommand;
import com.smartgwt.client.data.AdvancedCriteria;
import com.smartgwt.client.data.Criteria;
import com.smartgwt.client.data.Criterion;
import com.smartgwt.client.data.DSCallback;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.FieldType;
import com.smartgwt.client.types.OperatorId;
import com.smartgwt.client.types.Overflow;
import com.smartgwt.client.types.VerticalAlignment;
import com.smartgwt.client.widgets.Button;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.FormItemHoverFormatter;
import com.smartgwt.client.widgets.form.FormItemIfFunction;
import com.smartgwt.client.widgets.form.ValuesManager;
import com.smartgwt.client.widgets.form.fields.FormItem;
import com.smartgwt.client.widgets.form.fields.FormItemCriteriaFunction;
import com.smartgwt.client.widgets.form.fields.FormItemFunctionContext;
import com.smartgwt.client.widgets.form.fields.HiddenItem;
import com.smartgwt.client.widgets.form.fields.SpacerItem;
import com.smartgwt.client.widgets.form.fields.TextItem;
import com.smartgwt.client.widgets.form.fields.events.ChangeEvent;
import com.smartgwt.client.widgets.form.fields.events.ChangeHandler;
import com.smartgwt.client.widgets.form.fields.events.ChangedEvent;
import com.smartgwt.client.widgets.form.fields.events.ChangedHandler;
import com.smartgwt.client.widgets.form.fields.events.IconClickEvent;
import com.smartgwt.client.widgets.form.fields.events.IconClickHandler;
import com.smartgwt.client.widgets.form.validator.CustomValidator;
import com.smartgwt.client.widgets.form.validator.RequiredIfFunction;
import com.smartgwt.client.widgets.form.validator.RequiredIfValidator;
import com.smartgwt.client.widgets.layout.HStack;
import com.smartgwt.client.widgets.layout.VLayout;

import it.eng.auriga.ui.module.layout.client.AurigaLayout;
import it.eng.auriga.ui.module.layout.client.archivio.LookupArchivioPopup;
import it.eng.auriga.ui.module.layout.client.i18n.I18NUtil;
import it.eng.auriga.ui.module.layout.client.postaElettronica.DettaglioRegProtAssociatoWindow;
import it.eng.utility.ui.module.core.client.callback.ServiceCallback;
import it.eng.utility.ui.module.core.client.datasource.GWTRestDataSource;
import it.eng.utility.ui.module.layout.client.Layout;
import it.eng.utility.ui.module.layout.client.common.DetailSection;
import it.eng.utility.ui.module.layout.client.common.items.ExtendedNumericItem;
import it.eng.utility.ui.module.layout.client.common.items.ExtendedTextItem;
import it.eng.utility.ui.module.layout.client.common.items.ImgButtonItem;
import it.eng.utility.ui.module.layout.client.common.items.SelectItem;
import it.eng.utility.ui.module.layout.client.portal.ModalWindow;

public abstract class CercaUDPopup extends ModalWindow {

	protected CercaUDPopup _window;
	
	protected ValuesManager vm;
		
	protected DetailSection detailSectionDocumento;
	protected DynamicForm documentoForm;
	protected ImgButtonItem showDettUdPresenteItem;
	protected SelectItem tipoRegNumItem;
	protected ExtendedTextItem siglaRegNumItem;
	protected ExtendedNumericItem annoRegNumItem;
	protected ExtendedNumericItem nroRegNumItem;
	protected ImgButtonItem lookupArchivioButton;
	protected HiddenItem idUdHiddenItem;

	protected Button confermaButton;
	protected Button annullaButton;
	
	public CercaUDPopup() {

		super("cercaUDPopup", true);
		
		_window = this;
		
		vm = new ValuesManager();
		setTitle("Seleziona protocollo a cui associare le immagini");
		setIcon("postaElettronica/associaProtocolloPratica.png");
		
		setAutoCenter(true);
		setHeight(200);
		setWidth(700);

		settingsMenu.removeItem(separatorMenuItem);
		settingsMenu.removeItem(autoSearchMenuItem);
		
		createDetailSectionDocumento();
		  			
		SpacerItem spacer = new SpacerItem();
		spacer.setWidth("100%");
		spacer.setStartRow(true);
			
		confermaButton = new Button("Procedi");
		confermaButton.setIcon("ok.png");
		confermaButton.setIconSize(16);
		confermaButton.setAutoFit(false);
		confermaButton.addClickHandler(new com.smartgwt.client.widgets.events.ClickHandler() {

			@Override
			public void onClick(com.smartgwt.client.widgets.events.ClickEvent event) {
				onClickButton();	
			}
		});
		
		annullaButton = new Button("Annulla");
		annullaButton.setIcon("buttons/undo.png");
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
		
		// Creo il VLAYOUT e gli aggiungo il TABSET 
		VLayout portletLayout = new VLayout();  
		portletLayout.setHeight100();
		portletLayout.setWidth100();
		portletLayout.setOverflow(Overflow.AUTO);
		
		VLayout spacerLayout = new VLayout();
		spacerLayout.setHeight100();
		spacerLayout.setWidth100();
						
		portletLayout.addMember(detailSectionDocumento);		
		
		portletLayout.addMember(spacerLayout);
		portletLayout.addMember(_buttons);
						
		setBody(portletLayout);
	}
		

	public abstract void onClickButton();


	protected Record getRecordToSave() {
		Record record = new Record(vm.getValues());
		
		return record;
	}


	private void createDetailSectionDocumento() {
		documentoForm = new DynamicForm();
		documentoForm.setValuesManager(vm);
		documentoForm.setHeight("5");
		documentoForm.setPadding(5);
		documentoForm.setWrapItemTitles(false);
		documentoForm.setNumCols(15);
		documentoForm.setColWidths(1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, "*");

		CustomValidator attoPresenteValidator = new CustomValidator() {

			@Override
			protected boolean condition(Object value) {
				String tipoReg = tipoRegNumItem != null && tipoRegNumItem.getValueAsString() != null ? tipoRegNumItem.getValueAsString() : "";
				String sigla = "";
				if (tipoReg.equals("R") || tipoReg.equals("PP")) {
					sigla = siglaRegNumItem != null && siglaRegNumItem.getValue() != null ? (String) siglaRegNumItem.getValue() : "";
				} else {
					sigla = tipoReg;
				}
				String numero = nroRegNumItem != null && nroRegNumItem.getValueAsString() != null ? nroRegNumItem.getValueAsString() : "";
				String anno = annoRegNumItem != null && annoRegNumItem.getValueAsString() != null ? annoRegNumItem.getValueAsString() : "";
				if (("PG".equals(tipoReg) || !"".equals(sigla)) && !"".equals(numero) && !"".equals(anno)) {
					return idUdHiddenItem.getValue() != null && !"".equals(idUdHiddenItem.getValue());
				}
				
				return true;
			}
		};
		attoPresenteValidator.setErrorMessage("Documento non presente a sistema");

		
		showDettUdPresenteItem = new ImgButtonItem("showDettUdPresente", "buttons/detail.png", "Dettaglio Documento");
		showDettUdPresenteItem.setColSpan(1);
		showDettUdPresenteItem.setIconWidth(16);
		showDettUdPresenteItem.setIconHeight(16);
		showDettUdPresenteItem.setIconVAlign(VerticalAlignment.BOTTOM);
		showDettUdPresenteItem.setAlign(Alignment.LEFT);
		showDettUdPresenteItem.setWidth(16);
		showDettUdPresenteItem.setRedrawOnChange(true);
		showDettUdPresenteItem.setAlwaysEnabled(true);
		showDettUdPresenteItem.addIconClickHandler(new IconClickHandler() {

			@Override
			public void onIconClick(IconClickEvent event) {
				manageDettaglioUD((String)idUdHiddenItem.getValue());
			}
		});
		showDettUdPresenteItem.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {								
				return idUdHiddenItem.getValue() != null && !"".equalsIgnoreCase((String) idUdHiddenItem.getValue());
			}
		});
				
		
		// **********************************************
		// Estremi di registrazione
		// **********************************************
		// Tipo
		GWTRestDataSource tipoDS = new GWTRestDataSource("LoadComboTipoDocCollegatoDataSource", "key", FieldType.TEXT);
		tipoDS.extraparam.put("finalita", "PUBBLICAZIONE");
		tipoRegNumItem = new SelectItem("tipoRegNum",
				I18NUtil.getMessages().pubblicazione_albo_consultazione_richieste_detail_tipoRegistrazioneItem_title());
		tipoRegNumItem.setOptionDataSource(tipoDS);
		tipoRegNumItem.setPickListFilterCriteriaFunction(new FormItemCriteriaFunction() {
			
			@Override
			public Criteria getCriteria(FormItemFunctionContext itemContext) {
				Criterion[] criterias = new Criterion[1];
				if (AurigaLayout.isAttivoModuloProt()) {
					criterias[0] = new Criterion("key", OperatorId.IEQUALS, "R");
				}
				return new AdvancedCriteria(OperatorId.AND, criterias);
			}
		});
		tipoRegNumItem.setAutoFetchData(true);
		tipoRegNumItem.setDisplayField("value");
		tipoRegNumItem.setValueField("key");
		tipoRegNumItem.setDefaultValue("PG");
		tipoRegNumItem.setWidth(110);
		tipoRegNumItem.setWrapTitle(false);
		tipoRegNumItem.setColSpan(1);
		tipoRegNumItem.setStartRow(true);
		tipoRegNumItem.setAttribute("obbligatorio", true);
		tipoRegNumItem.addChangedHandler(new ChangedHandler() {

			@Override
			public void onChanged(final ChangedEvent event) {
				cercaProtocolloEvent(null);				
			}
		});
		tipoRegNumItem.addChangeHandler(new ChangeHandler() {
			
			@Override
			public void onChange(ChangeEvent event) {
				documentoForm.setValue("idUd", (String) null);				
			}
		});
		tipoRegNumItem.setItemHoverFormatter(true, new FormItemHoverFormatter() {
			@Override
			public String getHoverHTML(FormItem item, DynamicForm form) {
				String estremiReg = form.getValueAsString("estremiReg") != null ? form.getValueAsString("estremiReg") : "";
				if (!"".equals(estremiReg)) {
					return estremiReg;
				}
				return null;
			}
		});

        // Sigla Registro
		siglaRegNumItem = new ExtendedTextItem("siglaRegNum", I18NUtil.getMessages().pubblicazione_albo_consultazione_richieste_detail_siglaRegistrazioneItem_title());
		siglaRegNumItem.setWidth(110);
		siglaRegNumItem.setColSpan(1);
		siglaRegNumItem.setValidators(new RequiredIfValidator(new RequiredIfFunction() {
			@Override
			public boolean execute(FormItem formItem, Object value) {
				return tipoRegNumItem.getValueAsString() != null
						&& ("R".equalsIgnoreCase(tipoRegNumItem.getValueAsString())
								|| "PP".equalsIgnoreCase(tipoRegNumItem.getValueAsString()));
			}
		}));
		siglaRegNumItem.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return (tipoRegNumItem.getValueAsString() != null) &&
								("R".equalsIgnoreCase(tipoRegNumItem.getValueAsString())
								|| "PP".equalsIgnoreCase(tipoRegNumItem.getValueAsString()));
			}
		});
		siglaRegNumItem.addChangedBlurHandler(new ChangedHandler() {

			@Override
			public void onChanged(final ChangedEvent event) {
				cercaProtocolloEvent(null);
			}
		});
		siglaRegNumItem.addChangeHandler(new ChangeHandler() {
			
			@Override
			public void onChange(ChangeEvent event) {
				documentoForm.setValue("idUd", (String) null);				
			}
		});

		// Anno
		annoRegNumItem = new ExtendedNumericItem("annoRegNum",I18NUtil.getMessages().pubblicazione_albo_consultazione_richieste_detail_annoRegistrazioneItem_title());
		annoRegNumItem.setColSpan(1);
		annoRegNumItem.setLength(4);
		annoRegNumItem.setWidth(70);
		annoRegNumItem.setAttribute("obbligatorio", true);
		annoRegNumItem.addChangedBlurHandler(new ChangedHandler() {

			@Override
			public void onChanged(final ChangedEvent event) {
				cercaProtocolloEvent(null);
			}
		});
		annoRegNumItem.setItemHoverFormatter(true, new FormItemHoverFormatter() {
			@Override
			public String getHoverHTML(FormItem item, DynamicForm form) {
				String estremiReg = form.getValueAsString("estremiReg") != null ? form.getValueAsString("estremiReg"): "";
				if (!"".equals(estremiReg)) {
					return estremiReg;
				}
				return null;
			}
		});
		annoRegNumItem.addChangeHandler(new ChangeHandler() {
			
			@Override
			public void onChange(ChangeEvent event) {
				documentoForm.setValue("idUd", (String) null);				
			}
		});

		// Numero
		nroRegNumItem = new ExtendedNumericItem("nroRegNum", I18NUtil.getMessages().pubblicazione_albo_consultazione_richieste_detail_numeroRegistrazioneItem_title(), false);
		nroRegNumItem.setColSpan(1);
		nroRegNumItem.setLength(7);
		nroRegNumItem.setWidth(110);
		nroRegNumItem.setAttribute("obbligatorio", true);
		nroRegNumItem.addChangedBlurHandler(new ChangedHandler() {

			@Override
			public void onChanged(final ChangedEvent event) {
				cercaProtocolloEvent(null);
			}
		});
		nroRegNumItem.setItemHoverFormatter(true, new FormItemHoverFormatter() {
			@Override
			public String getHoverHTML(FormItem item, DynamicForm form) {

				String estremiReg = form.getValueAsString("estremiReg") != null ? form.getValueAsString("estremiReg"): "";
				if (!"".equals(estremiReg)) {
					return estremiReg;
				}
				return null;
			}
		});
		nroRegNumItem.addChangeHandler(new ChangeHandler() {
			
			@Override
			public void onChange(ChangeEvent event) {
				documentoForm.setValue("idUd", (String) null);				
			}
		});

		// BOTTONI : seleziona dall'archivio
		lookupArchivioButton = new ImgButtonItem("lookupArchivioButton", "lookup/archivio.png", "Seleziona dall'archivio");
		lookupArchivioButton.setEndRow(false);
		lookupArchivioButton.setColSpan(1);
		lookupArchivioButton.addIconClickHandler(new IconClickHandler() {
			@Override
			public void onIconClick(IconClickEvent event) {

				DocumentoCollegatoLookupArchivio lookupArchivioPopup = new DocumentoCollegatoLookupArchivio(documentoForm.getValuesAsRecord());
				lookupArchivioPopup.show();
			}
		});

		idUdHiddenItem = new HiddenItem("idUd");
		
		List<FormItem> items = new ArrayList<FormItem>();		
		
		items.add(tipoRegNumItem); 
		items.add(siglaRegNumItem); 
		items.add(annoRegNumItem); 
		items.add(nroRegNumItem); 
		items.add(lookupArchivioButton); 
		items.add(showDettUdPresenteItem);		
		items.add(idUdHiddenItem); 
			
		documentoForm.setFields(items.toArray(new FormItem[items.size()]));
		
		detailSectionDocumento = new DetailSection("DATI DOCUMENTO", false, true, false, documentoForm);		
	}

	private void manageDettaglioUD(String idUd) {
		Record lRecord = new Record();
		lRecord.setAttribute("idUd", idUd);
		new DettaglioRegProtAssociatoWindow(lRecord, "Dettaglio prot. NÂ° " + nroRegNumItem.getValueAsString() + "/" + annoRegNumItem.getValueAsString());
	}

	public void editRecordEstremi(Record record) {
		tipoRegNumItem.setValue(record.getAttribute("tipoRegNum"));
		siglaRegNumItem.setValue(record.getAttribute("siglaRegNum"));
		nroRegNumItem.setValue(record.getAttribute("nroRegNum"));
		annoRegNumItem.setValue(record.getAttribute("annoRegNum"));
		idUdHiddenItem.setValue(record.getAttribute("idUd"));
		markForRedraw();
	}
	
	public void loadDettaglio(String idUd, final ServiceCallback<Record> callback) {
		loadDettaglio(idUd, null, false, callback);
	}
	
	public void loadDettaglioAfterSave(String idUd, String idRichPubbl, final ServiceCallback<Record> callback) {
		loadDettaglio(idUd, idRichPubbl, true, callback);
	}
	
	private void loadDettaglio(String idUd, String idRichPubbl, final Boolean afterSave, final ServiceCallback<Record> callback) {
		Record lRecord = new Record();
		lRecord.setAttribute("idUd", idUd);
		final GWTRestDataSource lNuovaPropostaAtto2CompletaDataSource = new GWTRestDataSource("ContenutiAmmTraspDatasource");
		lNuovaPropostaAtto2CompletaDataSource.executecustom("caricaInfoDocumento", lRecord, new DSCallback() {	
			
			@Override
			public void execute(DSResponse response, Object rawData, DSRequest request) {
				if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
					if (response.getData() != null) {
						Record infoDocumento = response.getData()[0];
						if (infoDocumento != null) {
							if(callback != null) {
								callback.execute(response.getData()[0]);
							}	
						}
					} 
				}
			}
		});
	}
	
	
	public void recuperaIdUd(final ServiceCallback<String> callback) {
		String tipoReg = tipoRegNumItem != null && tipoRegNumItem.getValueAsString() != null ? tipoRegNumItem.getValueAsString() : "";
		String sigla = "";
		if (tipoReg.equals("R") || tipoReg.equals("PP")) {
			sigla = siglaRegNumItem != null && siglaRegNumItem.getValue() != null ? (String) siglaRegNumItem.getValue() : "";
		}
		String numero = nroRegNumItem != null && nroRegNumItem.getValueAsString() != null ? nroRegNumItem.getValueAsString() : "";
		String anno = annoRegNumItem != null && annoRegNumItem.getValueAsString() != null ? annoRegNumItem.getValueAsString() : "";									
		if (("PG".equals(tipoReg) || !"".equals(sigla)) && !"".equals(numero) && !"".equals(anno)) {
			Record lRecord = new Record();			
			lRecord.setAttribute("tipoRegNum", tipoReg);
			lRecord.setAttribute("siglaRegNum", sigla);
			lRecord.setAttribute("nroRegNum", numero);
			lRecord.setAttribute("annoRegNum", anno);	
			final GWTRestDataSource lPubblicazioneAlboConsultazioneRichiesteDataSource = new GWTRestDataSource("PubblicazioneAlboConsultazioneRichiesteDataSource");
			lPubblicazioneAlboConsultazioneRichiesteDataSource.extraparam.put("hideMessageError", "true");
			lPubblicazioneAlboConsultazioneRichiesteDataSource.performCustomOperation("recuperaIdUdAttoDaPubblicare", lRecord, new DSCallback() {							
				
				@Override
				public void execute(DSResponse response, Object rawData, DSRequest request) {
					if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
						if(callback != null) {
							String idUd = response.getData()[0].getAttributeAsString("idUdFolder");
							idUdHiddenItem.setValue(idUd);
							callback.execute(idUd);
						} 
					} else {
						if(callback != null) {
							callback.execute(null);
						} 
					}
				}
			});
		}		
	}
	
	@Override
	protected void onDestroy() {
		if (vm != null) {
			try {
				vm.destroy();
			} catch (Exception e) {}
		}
		super.onDestroy();
	}
	
	public class DocumentoCollegatoLookupArchivio extends LookupArchivioPopup {

		public DocumentoCollegatoLookupArchivio(Record record) {
			super(record, true);			
		}

		public DocumentoCollegatoLookupArchivio(Record record, String idRootNode) {
			super(record, idRootNode, true);			
		}

		@Override
		public String getWindowTitle() {
			return "Seleziona documento";
		}
		
		@Override
		public void manageLookupBack(Record record) {
			setFormValuesFromRecordArchivio(record);						
		}

		@Override
		public void manageMultiLookupBack(Record record) {

		}	

		@Override
		public void manageMultiLookupUndo(Record record) {	

		}			
	}
	
	protected String setTitleAlign(String title, int width, boolean required) {
		if (title != null && title.startsWith("<span")) {
			return title;
		}
		return "<span style=\"width: " + (required ? width : width + 1) + "px; display: inline-block;\">" + title + "</span>";
	}
	
	protected void changedEventAfterUpload(final String displayFileName, final String uri, final DynamicForm formFile, final TextItem nomeFileItem,
			final HiddenItem uriItem) {
		ChangedEvent lChangedEventDisplay = new ChangedEvent(nomeFileItem.getJsObj()) {

			@Override
			public DynamicForm getForm() {
				return formFile;
			};

			@Override
			public FormItem getItem() {
				return nomeFileItem;
			}

			@Override
			public Object getValue() {
				return displayFileName;
			}
		};
		ChangedEvent lChangedEventUri = new ChangedEvent(uriItem.getJsObj()) {

			@Override
			public DynamicForm getForm() {
				return formFile;
			};

			@Override
			public FormItem getItem() {
				return uriItem;
			}

			@Override
			public Object getValue() {
				return uri;
			}
		};
		nomeFileItem.fireEvent(lChangedEventDisplay);
		uriItem.fireEvent(lChangedEventUri);
	}

	public void setFormValuesFromRecordArchivio(Record record) {
		documentoForm.clearErrors(true);	
		String idUd = (String) record.getAttribute("idUdFolder");
		if (idUd != null && !"".equals(idUd)) {
			loadDettaglio(idUd, new ServiceCallback<Record>() {

				@Override
				public void execute(Record recordEstremiDocumento) {
					if (recordEstremiDocumento != null) {
						editRecordEstremi(recordEstremiDocumento);
						markForRedraw();
					}
				}
			});
		} else {
			markForRedraw();
		}
	}	
	
	public String getIdUd() {
		return (String) idUdHiddenItem.getValue();
	}
	
	public String getNumProt() {
		return nroRegNumItem.getValueAsString();
	}
	
	public String getAnnoProt() {
		return annoRegNumItem.getValueAsString();
	}


	/**
	 * @param event
	 */
	public void cercaProtocolloEvent(final ServiceCallback<Record> callback) {
		documentoForm.clearErrors(true);
		documentoForm.setValue("idUd", (String) null);
		recuperaIdUd(new ServiceCallback<String>() {

			@Override
			public void execute(String idUd) {
				if (idUd != null && !"".equals(idUd)) {
					documentoForm.setValue("idUd", idUd);
					documentoForm.markForRedraw();
					Record record = new Record();
					record.setAttribute("idUd", idUd);
					if(callback!=null) {
						callback.execute(record);
					}					
				} else {
					if(callback!=null) {
						callback.execute(new Record());
					}
				}	
			}
		});
	}
	
	
	
	
}