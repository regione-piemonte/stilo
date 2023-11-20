/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.smartgwt.client.data.Record;
import com.smartgwt.client.data.RecordList;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.BackgroundRepeat;
import com.smartgwt.client.types.FieldType;
import com.smartgwt.client.widgets.Label;
import com.smartgwt.client.widgets.events.FetchDataEvent;
import com.smartgwt.client.widgets.events.FetchDataHandler;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.FormItemIfFunction;
import com.smartgwt.client.widgets.form.FormItemInputTransformer;
import com.smartgwt.client.widgets.form.fields.FormItem;
import com.smartgwt.client.widgets.form.fields.HiddenItem;
import com.smartgwt.client.widgets.form.fields.SpacerItem;
import com.smartgwt.client.widgets.form.fields.events.ChangedEvent;
import com.smartgwt.client.widgets.form.fields.events.ChangedHandler;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.client.widgets.tab.Tab;
import com.smartgwt.client.widgets.toolbar.ToolStrip;

import it.eng.auriga.ui.module.layout.client.AurigaLayout;
import it.eng.auriga.ui.module.layout.client.attributiDinamici.AttributiDinamiciDetail;
import it.eng.auriga.ui.module.layout.client.i18n.I18NUtil;
import it.eng.auriga.ui.module.layout.client.pratiche.dettaglio.PropostaAttoInterface;
import it.eng.utility.ui.module.core.client.callback.ServiceCallback;
import it.eng.utility.ui.module.core.client.datasource.GWTRestDataSource;
import it.eng.utility.ui.module.core.client.datasource.GWTRestService;
import it.eng.utility.ui.module.layout.client.Layout;
import it.eng.utility.ui.module.layout.client.common.CustomDetail;
import it.eng.utility.ui.module.layout.client.common.DetailSection;
import it.eng.utility.ui.module.layout.client.common.file.InfoFileRecord;
import it.eng.utility.ui.module.layout.client.common.filter.item.AnnoItem;
import it.eng.utility.ui.module.layout.client.common.items.CheckboxItem;
import it.eng.utility.ui.module.layout.client.common.items.DateItem;
import it.eng.utility.ui.module.layout.client.common.items.DateTimeItem;
import it.eng.utility.ui.module.layout.client.common.items.ExtendedTextAreaItem;
import it.eng.utility.ui.module.layout.client.common.items.NumericItem;
import it.eng.utility.ui.module.layout.client.common.items.SelectItem;
import it.eng.utility.ui.module.layout.client.common.items.TextAreaItem;
import it.eng.utility.ui.module.layout.client.common.items.TextItem;
import it.eng.utility.ui.module.layout.client.common.items.TitleItem;

/**
 * CustomDetail che contiene la maschera per la protocollazione ATTI
 * 
 * @author Mattia Zanin
 *
 */

public class ProtocollazioneDetailAtti extends ProtocollazioneDetail {

	protected DynamicForm uoProponenteForm;
	protected SelezionaUOItem uoProponenteItem;

	protected ProtocollazioneHeaderDetailSection detailSectionResponsabileProc;
	protected DynamicForm responsabileProcForm;
	protected HiddenItem idUoProponenteHiddenItem;
	protected SelectItem responsabileProcItem;	

	protected ProtocollazioneDetailSection detailSectionPregresso;
	protected DynamicForm pregressoForm;
	protected DateItem dataAvvioIterAttoItem;
	protected TextItem funzionarioIstruttoreAttoItem;
	// protected TextItem direttoreFirmatarioAttoItem;
	protected DateItem dataFirmaAttoItem;
	protected TextItem responsabileAttoItem;
	protected TextItem funzionarioFirmaAttoItem;
	protected DateItem dataPubblicazioneAttoItem;
	protected TextAreaItem logIterAttoItem;

	// Scelta iter per Milano
	protected DynamicForm sceltaIterForm;
	protected SelectItem sceltaIterItem;
	protected TitleItem titleAttoRifSubImpegnoItem;
	protected TextItem siglaAttoRifSubImpegnoItem;
	protected NumericItem numeroAttoRifSubImpegnoItem;
	protected AnnoItem annoAttoRifSubImpegnoItem;
	protected DateTimeItem dataAttoRifSubImpegnoItem;

	protected ProtocollazioneDetailSection detailSectionAltreUoCoinvolte;
	protected DynamicForm altreUoCoinvolteForm;
	protected CondivisioneItem altreUoCoinvolteItem;

	protected CheckboxItem flgRichParereRevisoriItem;

	public ProtocollazioneDetailAtti(String nomeEntita, LinkedHashMap<String, String> attributiAddDocTabs) {
		this(nomeEntita, attributiAddDocTabs, null);
	}

	public ProtocollazioneDetailAtti(String nomeEntita, LinkedHashMap<String, String> attributiAddDocTabs, String idTipoDoc) {

		super(nomeEntita);

		this.tipoDocumento = idTipoDoc;
		this.attributiAddDocTabs = attributiAddDocTabs != null ? attributiAddDocTabs : new LinkedHashMap<String, String>();
		
		if (isAvvioPropostaAtto()) {
			caricaAttributiDinamiciDoc(null, null, null, tipoDocumento, null);
		}
	}
	
	@Override
	public boolean isModalitaAllegatiGrid() {
		return false;
	}
	
	@Override
	public boolean isModalitaWizard() {
		return false;
	}

	@Override
	public String getFlgTipoProv() {
		return "I";
	}
	
	@Override
	public boolean showIconeNotificheInterop() {
		return !isTaskDetail();
	}

	@Override
	public boolean showIconaAnnullata() {
		return !isTaskDetail();
	}

	@Override
	public boolean showIconaRichAnnullamento() {
		return !isTaskDetail();
	}

	public boolean isAvvioPropostaAtto() {
		return false;
	}
	
	public static boolean isPropostaAtto2Milano() {
		return AurigaLayout.getParametroDBAsBoolean("ATTIVA_PROPOSTA_ATTO_2");
	}
	
	public boolean isVecchiaPropostaAtto2Milano() {
		//TODO da togliere tutta la parte relativa alla vecchia maschera di dettaglio proposta atto di Milano perchè non viene più usata (PropostaAtto2Detail)
		return isPropostaAtto2Milano() && !AurigaLayout.getParametroDBAsBoolean("ATTIVA_NUOVA_PROPOSTA_ATTO_2") && !AurigaLayout.getParametroDBAsBoolean("GESTIONE_ATTI_COMPLETA");
	}

	public boolean isPregresso() {
		return false;
	}

	public boolean isEseguibile() {
		return editing;
	}

	public String getMessaggioTab(String tabID) {
		return null;
	}

	@Override
	public boolean showModelliSelectItem() {
		return false;
	}

	public String getIdUd() {
		return null;
	}

	// se è abilitata la selezione estesa a tutto l'organigramma dell'ufficio proponente
	public static boolean isAbilToSelUffPropEsteso() {
		return Layout.isPrivilegioAttivo("APE");
	}

	@Override
	protected LinkedHashMap<String, String> getUoProtocollanteValueMap() {
		return AurigaLayout.getUoProponenteAttiValueMap();
	}
	
	@Override
	public LinkedHashMap<String, String> getSelezioneUoProtocollanteValueMap() {		
		return AurigaLayout.getSelezioneUoProponenteAttiValueMap();
	}
	
	@Override
	public boolean showUoProtocollanteSelectItem() {
		return /*isAbilToProt() && */(getUoProtocollanteValueMap().size() > 1 || isAbilToSelUffPropEsteso());
	}

	@Override
	public String getTitleUoProtocollanteSelectItem() {
		return "<b>U.O. proponente</b>";
	}

	@Override
	protected void createUoProtocollanteSelectItem() {
		if (!isAbilToSelUffPropEsteso()) {
			super.createUoProtocollanteSelectItem();
			uoProtocollanteSelectItem.addChangedHandler(new ChangedHandler() {

				@Override
				public void onChanged(ChangedEvent event) {
					if (responsabileProcItem != null) {
						responsabileProcItem.clearValue();
						responsabileProcItem.fetchData();
					}
				}
			});
		}
	}

	@Override
	protected void createMainToolStrip() {
		if (isAbilToSelUffPropEsteso()) {

			createModelliSelectItem();

			/*
			 * se ho l'abilitazione utente APE il proponente è sempre visibile e con la selezione su tutto l'organigramma quindi compare il cod. rapido e la
			 * select è filtrabile (come per l'assegnazione ma solo UO)
			 */
			uoProponenteForm = new DynamicForm();
			uoProponenteForm.setWidth("100%");
			uoProponenteForm.setHeight("5");
			uoProponenteForm.setPadding(5);

			uoProponenteItem = new SelezionaUOItem() {
				
				@Override
				public void manageChangedUoSelezionata() {
					if (responsabileProcItem != null) {
						responsabileProcItem.clearValue();
						responsabileProcItem.fetchData();
					}
				}
			};
			uoProponenteItem.setName("listaUoProponenti");
			uoProponenteItem.setNotReplicable(true);
			uoProponenteItem.setShowTitle(false);
			uoProponenteItem.setAttribute("obbligatorio", true);
			
			uoProponenteForm.setItems(uoProponenteItem);

			DetailSection detailSectionUoPropoenente = new DetailSection(getTitleUoProtocollanteSelectItem(), true, true, true, uoProponenteForm);

			mainToolStrip = new ToolStrip();
			mainToolStrip.setBackgroundColor("transparent");
			mainToolStrip.setBackgroundImage("blank.png");
			mainToolStrip.setBackgroundRepeat(BackgroundRepeat.REPEAT_X);
			mainToolStrip.setBorder("0px");
			mainToolStrip.setWidth100();
			mainToolStrip.setHeight(30);
			mainToolStrip.setRedrawOnResize(true);

			// se devo mostrare sia la select dei modelli che della U.O. protocollante
			if (showModelliSelectItem() && showUoProtocollanteSelectItem()) {
				mainToolStrip.addFormItem(modelliSelectItem);
				mainToolStrip.addFill();
				mainToolStrip.addMember(detailSectionUoPropoenente);
			}
			// se devo mostrare solo la select dei modelli
			else if (showModelliSelectItem()) {
				detailSectionUoPropoenente.setVisible(false);
				mainToolStrip.addFormItem(modelliSelectItem);
			}
			// se devo mostrare solo la select della U.O. protocollante
			else if (showUoProtocollanteSelectItem()) {
				modelliSelectItem.setVisible(false);
				mainToolStrip.setMembers(detailSectionUoPropoenente);
			}
			// se non devo mostrare nessuna delle due
			else {
				modelliSelectItem.setVisible(false);
				detailSectionUoPropoenente.setVisible(false);
				mainToolStrip.setVisible(false);
			}

		} else {
			super.createMainToolStrip();
		}
	}

	@Override
	public String getTitleRegistraButton() {
		return "Avvia iter";
	}

	@Override
	public String getIconRegistraButton() {
		return "buttons/gear.png";
	}

	@Override
	protected void createTabDatiDocumenti() {
		super.createTabDatiDocumenti();
		tabDatiDocumento.setTitle("<b>Dati principali</b>");
		tabDatiDocumento.setPrompt("Dati principali");
	}

	@Override
	protected void createTabAssegnazioneEClassificazione() {
		super.createTabAssegnazioneEClassificazione();
		tabAssegnazioneEClassificazione.setTitle("<b>Altre U.O. e fascicolazione</b>");
		tabAssegnazioneEClassificazione.setPrompt("Altre U.O. e fascicolazione");
	}

	public Record getRecordToSave(String motivoVarDatiReg) {
		Record lRecordToSave = super.getRecordToSave(motivoVarDatiReg);
		if (responsabileProcForm != null) {
			addFormValues(lRecordToSave, responsabileProcForm);
		}
		addFormValues(lRecordToSave, pregressoForm);
		if (isVecchiaPropostaAtto2Milano()) {
			addFormValues(lRecordToSave, sceltaIterForm);
		}
		addFormValues(lRecordToSave, altreUoCoinvolteForm);
		if (uoProponenteItem != null) {
			RecordList listaUoProponenti = uoProponenteItem.getValueAsRecordList();
			if (listaUoProponenti != null && listaUoProponenti.getLength() > 0) {
				lRecordToSave.setAttribute("uoProtocollante", "UO" + listaUoProponenti.get(0).getAttribute("idUo"));
			}
		}
		return lRecordToSave;
	}

	@Override
	public void setInitialValues() {
		super.setInitialValues();
		if (detailSectionPregresso != null) {
			detailSectionPregresso.open();
		}
		if (detailSectionAltreUoCoinvolte != null) {
			detailSectionAltreUoCoinvolte.open();
		}
	}
	
//	public void removeEmptyCanvas() {
//		super.removeEmptyCanvas();
//		try {			
//			if (attributiAddDocDetails != null) {
//				for (String key : attributiAddDocDetails.keySet()) {
//					AttributiDinamiciDetail detail = attributiAddDocDetails.get(key);
//					for (DynamicForm form : detail.getForms()) {
//						for (FormItem item : form.getFields()) {
//							if (item instanceof ReplicableItem) {
//								ReplicableItem lReplicableItem = (ReplicableItem) item;
//								if(lReplicableItem.getEditing() != null && lReplicableItem.getEditing()/* && !lReplicableItem.isObbligatorio()*/) {	
//									lReplicableItem.removeEmptyCanvas();
//								}	
//							}
//						}
//					}
//				}
//			}
//		} catch (Exception e) {
//		}
//	}

	@Override
	public void clearTabErrors() {
		clearTabErrors(tabSet);
	}
	
	@Override
	public void showTabErrors() {
		showTabErrors(tabSet);
	}
	
	@Override
	public boolean customValidate() {
		boolean valid = super.customValidate(); // perche estendo ProtocollazioneDetail
		if (isVecchiaPropostaAtto2Milano()) {
			if ((sceltaIterItem.getValue() != null && sceltaIterItem.getValueAsString().equals("sub-impegno"))) {
				boolean validSiglaAttoRifSubImpegno = siglaAttoRifSubImpegnoItem.getValue() != null
						&& !"".equals(siglaAttoRifSubImpegnoItem.getValueAsString());
				boolean validNumeroAttoRifSubImpegno = numeroAttoRifSubImpegnoItem.getValue() != null
						&& !"".equals(numeroAttoRifSubImpegnoItem.getValueAsString());
				boolean validAnnoDataAttoRifSubImpegno = (annoAttoRifSubImpegnoItem.getValue() != null && !"".equals(annoAttoRifSubImpegnoItem
						.getValueAsString())) || dataAttoRifSubImpegnoItem.getValue() != null;
				boolean validAttoRifSubImpegno = validSiglaAttoRifSubImpegno && validNumeroAttoRifSubImpegno && validAnnoDataAttoRifSubImpegno;
				if (!validAttoRifSubImpegno) {
					Map<String, String> errors = new HashMap<String, String>();
					errors.put("titleAttoRifSubImpegno",
							"I campi relativi all'atto di riferimento sono obbligatori: indicare sigla, numero e almeno uno tra anno e data");
					sceltaIterForm.setErrors(errors);
				}
				valid = valid && validAttoRifSubImpegno;
			}
		}		
		if (uoProponenteItem != null) {
			valid = (!uoProponenteItem.isVisible() || uoProponenteItem.validate()) && valid;
		}
		return valid;
	}
	
	@Override
	public void editRecord(Record record) {
		super.editRecord(record);
		if (responsabileProcItem != null) {
			if (record.getAttribute("idUserRespProc") != null && !"".equals(record.getAttributeAsString("idUserRespProc"))) {
				LinkedHashMap<String, String> valueMap = new LinkedHashMap<String, String>();
				valueMap.put(record.getAttribute("idUserRespProc"), record.getAttribute("desUserRespProc"));
				responsabileProcItem.setValueMap(valueMap);
			}
			GWTRestDataSource responsabileProcDS = (GWTRestDataSource) responsabileProcItem.getOptionDataSource();
			if (record.getAttribute("idUserRespProc") != null && !"".equals(record.getAttributeAsString("idUserRespProc"))) {
				responsabileProcDS.addParam("idUserRespProc", record.getAttributeAsString("idUserRespProc"));
			} else {
				responsabileProcDS.addParam("idUserRespProc", null);
			}
			responsabileProcItem.setOptionDataSource(responsabileProcDS);
		}
		// Se è un ATTO, e quindi ho entrambe le numerazioni
		if ((record.getAttribute("nroProtocollo") != null && !"".equals(record.getAttribute("nroProtocollo")))
				&& (record.getAttribute("numeroNumerazioneSecondaria") != null && !"".equals(record.getAttribute("numeroNumerazioneSecondaria")))) {
			iconaTipoProtocolloItem.setPrompt("Atto");
			registrazioneSecondariaForm.show();
			detailSectionRegistrazione.setTitle("Atto");
		} else {
			iconaTipoProtocolloItem.setPrompt("Proposta atto");
			registrazioneSecondariaForm.hide();
			detailSectionRegistrazione.setTitle("Proposta atto");
		}
		tipoDocumento = record.getAttribute("tipoDocumento");
	}

	@Override
	public void setCanEdit(boolean canEdit) {
		super.setCanEdit(canEdit);
		if (isVecchiaPropostaAtto2Milano()) {
			if (!isAvvioPropostaAtto()) {
				sceltaIterItem.setCanEdit(false);
			}
		}
		if (uoProponenteItem != null) {
			uoProponenteItem.setCanEdit(canEdit);
		}
		if (!isEseguibile() && !isModificaDatiExtraIter()) {
			// se il task non è eseguibile disabilito tutti gli attributi dinamici
			if (attributiAddDocDetails != null) {
				for (String key : attributiAddDocDetails.keySet()) {
					AttributiDinamiciDetail detail = attributiAddDocDetails.get(key);
					detail.setCanEdit(false);
					// for (DynamicForm form : detail.getForms()) {
					// setCanEdit(false, form);
					// }
				}
			}
		}
	}

	public boolean canEditSceltaIter() {
		return false;
	}

	public void readOnlyMode() {
		viewMode();
		if(fileAllegatiItem != null) {
			if(fileAllegatiItem instanceof AllegatiGridItem) {
				((AllegatiGridItem)fileAllegatiItem).readOnlyMode();
			} else if(fileAllegatiItem instanceof AllegatiItem) {
				((AllegatiItem)fileAllegatiItem).readOnlyMode();
			}				
		}
		noteItem.setCanEdit(true);
	}

	@Override
	public void refreshTabIndex() {
		if(it.eng.utility.ui.module.layout.client.Layout.isAttivoRefreshTabIndex()) {
			if (!isAbilToSelUffPropEsteso()) {
				super.refreshTabIndex();
			} else {
				int tabIndex = 0;
				if (getNomeEntita() != null && Layout.getOpenedPortletIndex(getNomeEntita()) > 0) {
					tabIndex = (Layout.getOpenedPortletIndex(getNomeEntita()) * 1000000);
				}
				tabIndex++;
				if (modelliSelectItem != null) {
					modelliSelectItem.setTabIndex(0);
					modelliSelectItem.setGlobalTabIndex(0);
					CustomDetail.showItemTabIndex(modelliSelectItem);
				}
				tabIndex++;
				if (uoProponenteItem != null) {
					uoProponenteItem.setTabIndex(0);
					uoProponenteItem.setGlobalTabIndex(0);
					CustomDetail.showItemTabIndex(uoProponenteItem);
				}
				refreshTabIndex(++tabIndex);
			}
		}
	}

	@Override
	public boolean showDetailSectionTipoDocumento() {
		return false;
	}

	@Override
	public VLayout getLayoutDatiDocumento() {

		VLayout layoutDatiDocumento = new VLayout(5);

		createDetailSectionRegistrazione();
		detailSectionRegistrazione.setVisible(false);
		layoutDatiDocumento.addMember(detailSectionRegistrazione);
		
		if (showDetailSectionResponsabileProc()) {
			createDetailSectionResponsabileProc();
			layoutDatiDocumento.addMember(detailSectionResponsabileProc);
		}

		createDetailSectionContenuti();
		layoutDatiDocumento.addMember(detailSectionContenuti);

		createDetailSectionAllegati();
		layoutDatiDocumento.addMember(detailSectionAllegati);

		if(showDetailSectionDocCollegato()) {
			createDetailSectionDocCollegato();
			layoutDatiDocumento.addMember(detailSectionDocCollegato);
		}
		
		if (isPregresso()) {
			createDetailSectionPregresso();
			layoutDatiDocumento.addMember(detailSectionPregresso);
		}

		createDetailSectionAltriDati();
		layoutDatiDocumento.addMember(detailSectionAltriDati);

		return layoutDatiDocumento;
	}

	@Override
	public String getTitleDesUserProtocolloItem() {
		return "redatta da";
	}

	@Override
	public boolean showDesUOProtocolloItem() {
		return true;
	}

	@Override
	public String getTitleDesUOProtocolloItem() {
		return "U.O. proponente";
	}

	public boolean showSiglaProtocolloItem() {
		return true;
	}
	
	public String getTitleNroProtocolloItem(Record record) {
		return I18NUtil.getMessages().protocollazione_detail_nroAttoItem_title();
	}

	public String getTitleDataProtocolloItem() {
		return I18NUtil.getMessages().protocollazione_detail_dataAttoItem_title();
	}

	@Override
	public String getIconAltraNumerazione() {
		return "protocollazione/provvisoria.png";
	}

	@Override
	public String getTitleAltraNumerazione() {
		return "Numerazione provvisoria di proposta atto";
	}
	
	/**
	 * Metodo che restituisce il datasource relativo alla combo del responsabile procedimento
	 * 
	 */
	protected GWTRestDataSource getResponsabileProcDatasource() {
		return new GWTRestDataSource("LoadComboResponsabileProcDataSource", "idUserRespProc", FieldType.TEXT, true);
	}
	
	/**
	 * Metodo che indica se mostrare o meno la sezione "Responsabile procedimento"
	 * 
	 */
	public boolean showDetailSectionResponsabileProc() {
		return !isPropostaAtto2Milano() && AurigaLayout.getParametroDBAsBoolean("SHOW_RESP_PROC_IN_ATTI");
	}
	
	/**
	 * Metodo che crea la sezione "Responsabile procedimento"
	 * 
	 */
	protected void createDetailSectionResponsabileProc() {

		createResponsabileProcForm();
		
		detailSectionResponsabileProc = new ProtocollazioneHeaderDetailSection("Responsabile procedimento", true, true, true, responsabileProcForm);		
	}
	
	/**
	 * Metodo che crea il form della sezione "Responsabile procedimento"
	 * 
	 */
	protected void createResponsabileProcForm() {

		responsabileProcForm = new DynamicForm();
		responsabileProcForm.setValuesManager(vm);
		responsabileProcForm.setWidth100();
		responsabileProcForm.setPadding(5);
		responsabileProcForm.setWrapItemTitles(false);
		responsabileProcForm.setNumCols(10);
		responsabileProcForm.setColWidths(1, 1, 1, 1, 1, 1, 1, 1, "*", "*");
		responsabileProcForm.setTabSet(tabSet);
		responsabileProcForm.setTabID("HEADER");

		idUoProponenteHiddenItem = new HiddenItem("idUoProponente");

		responsabileProcItem = new SelectItem("idUserRespProc", "Responsabile procedimento");
		responsabileProcItem.setValueField("idUserRespProc");
		responsabileProcItem.setDisplayField("desUserRespProc");
		responsabileProcItem.setOptionDataSource(getResponsabileProcDatasource());
		responsabileProcItem.setWidth(300);
		responsabileProcItem.setAllowEmptyValue(false);
		responsabileProcItem.setRequired(true);
		responsabileProcItem.setShowIcons(true);
		responsabileProcItem.setShowTitle(false);
		responsabileProcItem.setAutoFetchData(false);
		// responsabileProcItem.setFilterLocally(true);
		responsabileProcItem.setAlwaysFetchMissingValues(true);
		responsabileProcItem.setFetchMissingValues(true);
		responsabileProcItem.setEmptyPickListMessage("Nessun record trovato o U.O. proponente non selezionata");
		ListGrid pickListProperties = new ListGrid();
		pickListProperties.addFetchDataHandler(new FetchDataHandler() {

			@Override
			public void onFilterData(FetchDataEvent event) {
				String uoProponente = (String) idUoProponenteHiddenItem.getValue();
				if (uoProponente == null || "".equals(uoProponente)) {
					if (uoProtocollanteSelectItem != null) {
						if (uoProtocollanteSelectItem.getValueAsString() != null && !"".equals(uoProtocollanteSelectItem.getValueAsString())) {
							uoProponente = uoProtocollanteSelectItem.getValueAsString();
						} else if (getSelezioneUoProtocollanteValueMap().size() == 1) {
							uoProponente = getSelezioneUoProtocollanteValueMap().keySet().toArray(new String[1])[0];
						}
					} else if (uoProponenteItem != null) {
						RecordList listaUoProponenti = uoProponenteItem.getValueAsRecordList();
						if (listaUoProponenti != null && listaUoProponenti.getLength() > 0) {
							String value = listaUoProponenti.get(0).getAttribute("idUo");
							if(value != null && !"".equals(value)) {
								uoProponente = "UO" + value;
							}
						}
					}
				}
				GWTRestDataSource responsabileProcDS = (GWTRestDataSource) responsabileProcItem.getOptionDataSource();
				responsabileProcDS.addParam("uoProponente", uoProponente);
				responsabileProcItem.setOptionDataSource(responsabileProcDS);
				responsabileProcItem.invalidateDisplayValueCache();
			}
		});
		responsabileProcItem.setPickListProperties(pickListProperties);

		responsabileProcForm.setFields(idUoProponenteHiddenItem, responsabileProcItem);
	}

	@Override
	public boolean showFilePrimarioForm() {
		return true;
	}
	
	public boolean showUploadFilePrimario() {
		return !AurigaLayout.getParametroDBAsBoolean("HIDE_UPLOAD_DISPOSITIVO_ATTO");
	}
	
	@Override
	public boolean showGeneraDaModelloButton() {
		return true;
	}

	@Override
	protected void createDetailSectionContenuti() {

		if (isVecchiaPropostaAtto2Milano()) {

			List<DynamicForm> forms = new ArrayList<DynamicForm>();

			createContenutiForm();
			createSceltaIterForm();
			createFilePrimarioForm();
			
			forms.add(contenutiForm);

			forms.add(sceltaIterForm);

			if (showFilePrimarioForm()) {
				forms.add(filePrimarioForm);
			}

			detailSectionContenuti = new ProtocollazioneDetailSection(I18NUtil.getMessages().protocollazione_detail_contenutiForm_title(), true, true, false,
					forms.toArray(new DynamicForm[forms.size()]));
		} else {
			super.createDetailSectionContenuti();
		}

	}

	protected void createContenutiForm() throws IllegalStateException {

		if (isPropostaAtto2Milano()) {

			oggettoItem = new ExtendedTextAreaItem("oggetto", I18NUtil.getMessages().protocollazione_detail_oggettoItem_title());
			oggettoItem.setRequired(true);
			oggettoItem.setLength(4000);
			oggettoItem.setHeight(80);
			oggettoItem.setWidth("100%");
			oggettoItem.setEndRow(false);
			oggettoItem.addChangedBlurHandler(new ChangedHandler() {
				
				@Override
				public void onChanged(ChangedEvent event) {
					manageChangedContenuti();
				}
			});
			if(AurigaLayout.getParametroDBAsBoolean("UPPERCASE_OGGETTO_ATTO")) {
				oggettoItem.setInputTransformer(new FormItemInputTransformer() {
					
					@Override
					public Object transformInput(DynamicForm form, FormItem item, Object value, Object oldValue) {
						return value != null ? ((String)value).toUpperCase() : null;
					}
				});
			}

			contenutiForm = new DynamicForm();
			contenutiForm.setValuesManager(vm);
			contenutiForm.setWidth("100%");
			contenutiForm.setPadding(5);
			contenutiForm.setNumCols(2);
			contenutiForm.setColWidths(1, "*");
			contenutiForm.setWrapItemTitles(false);
			contenutiForm.setTabSet(tabSet);
			contenutiForm.setTabID("HEADER");

			contenutiForm.setFields(oggettoItem);
			
		} else {
			super.createContenutiForm();			
		}
	}

	protected void createSceltaIterForm() {

		sceltaIterForm = new DynamicForm();
		sceltaIterForm.setValuesManager(vm);
		sceltaIterForm.setWidth100();
		sceltaIterForm.setPadding(5);
		sceltaIterForm.setNumCols(13);
		sceltaIterForm.setColWidths(1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, "*", "*");
		sceltaIterForm.setWrapItemTitles(false);
		sceltaIterForm.setTabSet(tabSet);
		sceltaIterForm.setTabID("HEADER");

		GWTRestDataSource sceltaIterDS = new GWTRestDataSource("LoadComboAttributoDinamicoDataSource", "key", FieldType.TEXT);
		sceltaIterDS.addParam("nomeCombo", "TASK_RESULT_2_SCELTA_ITER");
		sceltaIterItem = new SelectItem("sceltaIter", "Determinazione dirigenziale");
		// sceltaIterItem.setWrapTitle(true);
		sceltaIterItem.setOptionDataSource(sceltaIterDS);
		sceltaIterItem.setDisplayField("value");
		sceltaIterItem.setValueField("key");
		// sceltaIterItem.setValueMap(new String[] { "senza spesa", "in conto capitale", "su parte corrente", "in conto capitale e parte corrente",
		// "sub-impegno" });
		sceltaIterItem.setWidth(200);
		sceltaIterItem.setRequired(true);
		sceltaIterItem.setAllowEmptyValue(false);
		sceltaIterItem.setStartRow(true);
		sceltaIterItem.addChangedHandler(new ChangedHandler() {

			@Override
			public void onChanged(ChangedEvent event) {
				sceltaIterForm.markForRedraw();
			}
		});

		titleAttoRifSubImpegnoItem = new TitleItem("Atto di rif.", false);
		titleAttoRifSubImpegnoItem.setName("titleAttoRifSubImpegno");
		titleAttoRifSubImpegnoItem.setColSpan(1);
		titleAttoRifSubImpegnoItem.setAttribute("obbligatorio", true);
		titleAttoRifSubImpegnoItem.setShowIfCondition(new FormItemIfFunction() {

			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return (sceltaIterItem.getValue() != null && sceltaIterItem.getValueAsString().equals("sub-impegno"));
			}
		});

		siglaAttoRifSubImpegnoItem = new TextItem("siglaAttoRifSubImpegno", "Sigla");
		siglaAttoRifSubImpegnoItem.setWidth(80);
		siglaAttoRifSubImpegnoItem.setShowIfCondition(new FormItemIfFunction() {

			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return (sceltaIterItem.getValue() != null && sceltaIterItem.getValueAsString().equals("sub-impegno"));
			}
		});
		// siglaAttoRifSubImpegnoItem.setValidators(new RequiredIfValidator(new RequiredIfFunction() {
		// @Override
		// public boolean execute(FormItem formItem, Object value) {
		// return (sceltaIterItem.getValue() != null && sceltaIterItem.getValueAsString().equals("sub-impegno"));
		// }
		// }));

		numeroAttoRifSubImpegnoItem = new NumericItem("numeroAttoRifSubImpegno", "N&#176;");
		numeroAttoRifSubImpegnoItem.setShowIfCondition(new FormItemIfFunction() {

			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return (sceltaIterItem.getValue() != null && sceltaIterItem.getValueAsString().equals("sub-impegno"));
			}
		});
		// numeroAttoRifSubImpegnoItem.setAttribute("obbligatorio", true);
		// numeroAttoRifSubImpegnoItem.setValidators(new RequiredIfValidator(new RequiredIfFunction() {
		// @Override
		// public boolean execute(FormItem formItem, Object value) {
		// return (sceltaIterItem.getValue() != null && sceltaIterItem.getValueAsString().equals("sub-impegno"));
		// }
		// }));
		numeroAttoRifSubImpegnoItem.setHint("/");

		annoAttoRifSubImpegnoItem = new AnnoItem("annoAttoRifSubImpegno", null);
		annoAttoRifSubImpegnoItem.setShowTitle(false);
		annoAttoRifSubImpegnoItem.setShowIfCondition(new FormItemIfFunction() {

			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return (sceltaIterItem.getValue() != null && sceltaIterItem.getValueAsString().equals("sub-impegno"));
			}
		});
		// annoAttoRifSubImpegnoItem.setAttribute("obbligatorio", true);
		// annoAttoRifSubImpegnoItem.setValidators(new RequiredIfValidator(new RequiredIfFunction() {
		// @Override
		// public boolean execute(FormItem formItem, Object value) {
		// return (sceltaIterItem.getValue() != null && sceltaIterItem.getValueAsString().equals("sub-impegno"));
		// }
		// }));

		dataAttoRifSubImpegnoItem = new DateTimeItem("dataAttoRifSubImpegno", "del");
		dataAttoRifSubImpegnoItem.setShowIfCondition(new FormItemIfFunction() {

			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return (sceltaIterItem.getValue() != null && sceltaIterItem.getValueAsString().equals("sub-impegno"));
			}
		});
		// dataAttoRifSubImpegnoItem.setAttribute("obbligatorio", true);
		// dataAttoRifSubImpegnoItem.setValidators(new RequiredIfValidator(new RequiredIfFunction() {
		// @Override
		// public boolean execute(FormItem formItem, Object value) {
		// return (sceltaIterItem.getValue() != null && sceltaIterItem.getValueAsString().equals("sub-impegno"));
		// }
		// }));

		sceltaIterForm.setFields(sceltaIterItem, titleAttoRifSubImpegnoItem, siglaAttoRifSubImpegnoItem, numeroAttoRifSubImpegnoItem,
				annoAttoRifSubImpegnoItem, dataAttoRifSubImpegnoItem);
	}

	@Override
	public boolean isRequiredFilePrimario() {
		return !isPropostaAtto2Milano();
	}

	@Override
	public boolean isFormatoAmmessoFilePrimario(InfoFileRecord info) {
		if(this instanceof PropostaAttoInterface) {
			if (isPropostaAtto2Milano()) {
				return true;
			}
			return ProtocollazioneAttiAttachExtensionValidator.getInstance().validate(info);
		}
		return true;
	}

	@Override
	public String getTitleNomeFilePrimario() {
		return isPropostaAtto2Milano() ? "Dispositivo" : I18NUtil.getMessages().protocollazione_detail_nomeFilePrimarioItem_title();
	}

	@Override
	public int getWidthNomeFilePrimario() {
		return isPropostaAtto2Milano() ? 250 : 320;
	}
	
	@Override
	public boolean showFlgNoPubblPrimarioItem() {
//		return hasOneOrMoreFlgNoPubblChecked();
		// per retrocompatibilità sui vecchi atti in cui era già stato salvato (a true) mostrerò il check "escludi pubbl."
		// per i nuovi atti invece no, perchè verrà utilizzato il check "dati sensibili", con la vers. con omissis
		return hasFlgNoPubblPrimarioChecked();
	}	
	
	public boolean hasFlgNoPubblPrimarioChecked() {
		boolean flgNoPubblPrimario = flgNoPubblPrimarioItem.getValueAsBoolean() != null && flgNoPubblPrimarioItem.getValueAsBoolean();
		return flgNoPubblPrimario;
	}
	
	/*
	public boolean hasOneOrMoreFlgNoPubblChecked() {
		// Per i vecchi atti che già lo avevano continuerò a mostrare il check "escludi pubbl." (sia su primario che su allegati)
		// Se nel primario è spuntato mostrerò anche la versione per pubblicazione
		boolean flgNoPubblPrimario = hasFlgNoPubblPrimarioChecked();
		boolean flgNoPubblAllegato = false;
		RecordList listaAllegati = new Record(vm.getValues()).getAttributeAsRecordList("listaAllegati");
		if (listaAllegati != null) {
			for (int i = 0; i < listaAllegati.getLength(); i++) {
				Record allegato = listaAllegati.get(i);
				if (allegato.getAttributeAsBoolean("flgNoPubblAllegato") != null && allegato.getAttributeAsBoolean("flgNoPubblAllegato")) {
					flgNoPubblAllegato = true;
					break;
				}
			}
		}
		return flgNoPubblPrimario || flgNoPubblAllegato;
	}
	*/
	
	public HashSet<String> getTipiModelliAttiInAllegati() {
		return null;
	}
	
	@Override
	public boolean isObbligatorioFileInAllegati() {
		return true;
	}

	@Override
	public boolean isFormatoAmmessoFileAllegato(InfoFileRecord info) {
		if(this instanceof PropostaAttoInterface) {
			return ProtocollazioneAttiAttachExtensionValidator.getInstance().validate(info);
		}
		return true;
	};

	@Override
	public Record getRecordCaricaModelloInAllegati(String idModello, String tipoModello) {
		final Record modelloRecord = new Record();
		modelloRecord.setAttribute("idModello", idModello);
		modelloRecord.setAttribute("tipoModello", tipoModello);
		modelloRecord.setAttribute("idUd", getIdUd());
		if (attributiAddDocDetails != null) {
			modelloRecord.setAttribute("valori", getAttributiDinamiciDoc());
			modelloRecord.setAttribute("tipiValori", getTipiAttributiDinamiciDoc());
		}
		return modelloRecord;
	}
	
	@Override
	public boolean showGeneraDaModelloInAllegatiItem() {
		return true;
	}

	@Override
	public void manageChangedFileParteDispositivoInAllegatiItem() {
		manageChangedAllegatiDaFirmare();
	}
	
	@Override
	public boolean showFlgParereInAllegatiItem() {
		return AurigaLayout.isAttivaNuovaPropostaAtto2Completa();
	}
	
	@Override
	public boolean showFlgParteDispositivoInAllegatiItem() {
		return true;
	}

	@Override
	public boolean showFlgNoPubblInAllegatiItem() {
//		return hasOneOrMoreFlgNoPubblChecked();
		return AurigaLayout.getParametroDBAsBoolean("ATTIVA_ESCL_PUBBL_FILE_IN_ATTI");
	}

	public void manageChangedAllegatiDaFirmare() {

	}

	protected void createDetailSectionPregresso() {

		createPregressoForm();

		detailSectionPregresso = new ProtocollazioneDetailSection("Iter atto", true, true, false, pregressoForm);
	}

	protected void createPregressoForm() {

		pregressoForm = new DynamicForm();
		pregressoForm.setValuesManager(vm);
		pregressoForm.setWidth100();
		pregressoForm.setPadding(5);
		pregressoForm.setWrapItemTitles(false);
		pregressoForm.setNumCols(10);
		pregressoForm.setColWidths(1, 1, 1, 1, 1, 1, 1, 1, "*", "*");
		pregressoForm.setTabSet(tabSet);
		pregressoForm.setTabID("HEADER");

		dataAvvioIterAttoItem = new DateItem("dataAvvioIterAtto", "Data avvio iter");
		dataAvvioIterAttoItem.setWrapTitle(false);
		dataAvvioIterAttoItem.setColSpan(1);
		dataAvvioIterAttoItem.setStartRow(true);
		dataAvvioIterAttoItem.setEndRow(false);

		funzionarioIstruttoreAttoItem = new TextItem("funzionarioIstruttoreAtto", "Funzionario istruttore");
		funzionarioIstruttoreAttoItem.setWidth(250);
		funzionarioIstruttoreAttoItem.setStartRow(false);
		funzionarioIstruttoreAttoItem.setEndRow(true);

		// direttoreFirmatarioAttoItem = new
		// TextItem("direttoreFirmatarioAtto", "Firma del direttore");
		// direttoreFirmatarioAttoItem.setWidth(200);

		dataFirmaAttoItem = new DateItem("dataFirmaAtto", "Firmato il");
		dataFirmaAttoItem.setWrapTitle(false);
		dataFirmaAttoItem.setColSpan(1);
		dataFirmaAttoItem.setStartRow(true);
		dataFirmaAttoItem.setEndRow(false);

		responsabileAttoItem = new TextItem("responsabileAtto", "Responsabile");
		responsabileAttoItem.setWidth(250);
		responsabileAttoItem.setStartRow(false);
		responsabileAttoItem.setEndRow(false);

		funzionarioFirmaAttoItem = new TextItem("funzionarioFirmaAtto", "Firma del funzionario");
		funzionarioFirmaAttoItem.setWidth(250);
		funzionarioFirmaAttoItem.setStartRow(false);
		funzionarioFirmaAttoItem.setEndRow(true);

		dataPubblicazioneAttoItem = new DateItem("dataPubblicazioneAtto", "Data pubblicazione");
		dataPubblicazioneAttoItem.setWrapTitle(false);
		dataPubblicazioneAttoItem.setColSpan(1);
		dataPubblicazioneAttoItem.setStartRow(true);
		dataPubblicazioneAttoItem.setEndRow(true);

		logIterAttoItem = new TextAreaItem("logIterAtto", "Dettagli");
		logIterAttoItem.setLength(4000);
		logIterAttoItem.setHeight(40);
		logIterAttoItem.setColSpan(8);
		logIterAttoItem.setWidth(750);
		logIterAttoItem.setStartRow(true);
		logIterAttoItem.setEndRow(true);

		pregressoForm.setFields(dataAvvioIterAttoItem, funzionarioIstruttoreAttoItem, /* direttoreFirmatarioAttoItem, */
				dataFirmaAttoItem, responsabileAttoItem, funzionarioFirmaAttoItem, dataPubblicazioneAttoItem, logIterAttoItem);
	}

	public boolean showFlgRichParereRevisoriItem() {
		return isVecchiaPropostaAtto2Milano();
	}

	protected void createAltriDatiForm() {

		altriDatiForm = new DynamicForm();
		altriDatiForm.setValuesManager(vm);
		altriDatiForm.setWidth("100%");
		altriDatiForm.setPadding(5);
		altriDatiForm.setWrapItemTitles(false);
		altriDatiForm.setNumCols(2);
		altriDatiForm.setColWidths(1, "*");
		altriDatiForm.setTabSet(tabSet);
		altriDatiForm.setTabID("HEADER");

		// Note
		noteItem = new TextAreaItem("note", I18NUtil.getMessages().protocollazione_detail_noteItem_title());
		noteItem.setHeight(80);
		noteItem.setWidth("100%");
		noteItem.setStartRow(true);

		SpacerItem spacerItem = new SpacerItem();
		spacerItem.setWidth("*");
		spacerItem.setColSpan(1);
		// indica che lo spacer deve iniziare una nuova riga quando aggiunto
		spacerItem.setStartRow(true);
		spacerItem.setShowIfCondition(new FormItemIfFunction() {

			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return showFlgRichParereRevisoriItem();
			}
		});

		flgRichParereRevisoriItem = new CheckboxItem("flgRichParereRevisori", "richiesto parere revisori");
		flgRichParereRevisoriItem.setWidth("*");
		flgRichParereRevisoriItem.setDefaultValue(false);
		flgRichParereRevisoriItem.setStartRow(false);
		flgRichParereRevisoriItem.setShowIfCondition(new FormItemIfFunction() {

			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return showFlgRichParereRevisoriItem();
			}
		});

		altriDatiForm.setFields(noteItem, spacerItem, flgRichParereRevisoriItem);
	}

	@Override
	public boolean showConfermaAssegnazioneForm() {
		return true;
	}

	@Override
	public boolean isRequiredDetailSectionClassificazioneFascicolazione() {
		return AurigaLayout.getParametroDBAsBoolean("OBBL_CLASSIF_PROP_ATTO");
	}

	public VLayout getLayoutAssegnazioneEClassificazione() {

		VLayout layoutAssegnazioneEClassificazione = new VLayout(5);

		createDetailSectionAssegnazione();
		detailSectionAssegnazione.setVisible(!isPropostaAtto2Milano());
		layoutAssegnazioneEClassificazione.addMember(detailSectionAssegnazione);

		createDetailSectionCondivisione();
		detailSectionCondivisione.setVisible(!isPropostaAtto2Milano());
		layoutAssegnazioneEClassificazione.addMember(detailSectionCondivisione);

		createDetailSectionAltreUoCoinvolte();
		layoutAssegnazioneEClassificazione.addMember(detailSectionAltreUoCoinvolte);

		createDetailSectionClassificazioneFascicolazione();
		layoutAssegnazioneEClassificazione.addMember(detailSectionClassificazioneFascicolazione);

		createDetailSectionFolderCustom();
		detailSectionFolderCustom.setVisible(!isPropostaAtto2Milano());
		layoutAssegnazioneEClassificazione.addMember(detailSectionFolderCustom);
		
		return layoutAssegnazioneEClassificazione;
	}

	public String getTitleDetailSectionAltreUoCoinvolte() {
		return isPropostaAtto2Milano() ? "Altre strutture coinvolte" : "Altre U.O. proponenti";
	}

	protected void createDetailSectionAltreUoCoinvolte() {

		altreUoCoinvolteForm = new DynamicForm();
		altreUoCoinvolteForm.setValuesManager(vm);
		altreUoCoinvolteForm.setWidth("100%");
		altreUoCoinvolteForm.setHeight("5");
		altreUoCoinvolteForm.setPadding(5);
		altreUoCoinvolteForm.setTabSet(tabSet);
		altreUoCoinvolteForm.setTabID("ASSEGN_CLASSIF");

		altreUoCoinvolteItem = new CondivisioneItem();
		altreUoCoinvolteItem.setName("listaUoCoinvolte");
		altreUoCoinvolteItem.setShowTitle(false);
		altreUoCoinvolteItem.setFlgSoloUO(true);

		altreUoCoinvolteForm.setFields(altreUoCoinvolteItem);

		detailSectionAltreUoCoinvolte = new ProtocollazioneDetailSection(getTitleDetailSectionAltreUoCoinvolte(), true, true, false, altreUoCoinvolteForm);
	}

	@Override
	public Record getRecordCaricaModello(String idModello, String tipoModello) {
		Record modelloRecord = super.getRecordCaricaModello(idModello, tipoModello);
		modelloRecord.setAttribute("oggetto", contenutiForm.getValue("oggetto"));
		modelloRecord.setAttribute("listaUoCoinvolte", altreUoCoinvolteForm.getValue("listaUoCoinvolte"));
		return modelloRecord;
	}
	
	@Override
	public boolean isDettaglioUdAtto() {
		return !isAvvioPropostaAtto() && !isTaskDetail();
	}

	@Override
	public boolean showAttributiDinamiciDoc() {
		// se sono nel dettaglio UD di un atto faccio in modo che carichi gli attributi dinamici
		if(isDettaglioUdAtto()) {
			return true; // in questo caso verrà chiamato il metodo caricaAttributiDinamiciDoc(idTipoDocumento, rowidDoc)
		}
		return false; // lo setto a false per inibire il caricamento degli attributi dinamici ereditato
		  			  // dalla superclasse, in quanto vengono gestiti diversamente in questa maschera
	}
	
	// UGUALE AD ATTI2
	public void caricaAttributiDinamiciDoc(String nomeFlussoWF, String processNameWF, String activityName, String idTipoDoc, String rowidDoc) {
		if(isDettaglioUdAtto()) {
			// se è il dettaglio ud di un atto
			caricaAttributiDinamiciDoc(idTipoDoc, rowidDoc);
		} else {
			// se sono in un task PropostaAttoDetail o PropostaAtto2Detail
			attributiAddDocLayouts = new HashMap<String, VLayout>();
			attributiAddDocDetails = new HashMap<String, AttributiDinamiciDetail>();
			if (attributiAddDocTabs != null && attributiAddDocTabs.size() > 0) {
				GWTRestService<Record, Record> lGwtRestService = new GWTRestService<Record, Record>("AttributiDinamiciDatasource");
				lGwtRestService.addParam("nomeFlussoWF", nomeFlussoWF);
				lGwtRestService.addParam("processNameWF", processNameWF);
				lGwtRestService.addParam("activityNameWF", activityName);
				Record lAttributiDinamiciRecord = new Record();
				lAttributiDinamiciRecord.setAttribute("nomeTabella", "DMT_DOCUMENTS");
				lAttributiDinamiciRecord.setAttribute("rowId", rowidDoc);
				lAttributiDinamiciRecord.setAttribute("tipoEntita", idTipoDoc);
				lGwtRestService.call(lAttributiDinamiciRecord, new ServiceCallback<Record>() {

					@Override
					public void execute(Record object) {
						RecordList attributiAdd = object.getAttributeAsRecordList("attributiAdd");
						if (attributiAdd != null && !attributiAdd.isEmpty()) {
							for (final String key : attributiAddDocTabs.keySet()) {
								RecordList attributiAddCategoria = new RecordList();
								for (int i = 0; i < attributiAdd.getLength(); i++) {
									Record attr = attributiAdd.get(i);
									if (attr.getAttribute("categoria") != null && attr.getAttribute("categoria").equalsIgnoreCase(key)) {
										attributiAddCategoria.add(attr);
									}
								}
								if (!attributiAddCategoria.isEmpty()) {
									AttributiDinamiciDetail detail = new AttributiDinamiciDetail("attributiDinamici", attributiAddCategoria, object
											.getAttributeAsMap("mappaDettAttrLista"), object.getAttributeAsMap("mappaValoriAttrLista"), object
											.getAttributeAsMap("mappaVariazioniAttrLista"), object.getAttributeAsMap("mappaDocumenti"), null, tabSet, key);
									detail.setCanEdit(new Boolean(isEseguibile()));
									String messaggioTab = getMessaggioTab(key);
									if (messaggioTab != null && !"".equals(messaggioTab)) {
										Label labelMessaggioTab = new Label(messaggioTab);
										labelMessaggioTab.setAlign(Alignment.LEFT);
										labelMessaggioTab.setWidth100();
										labelMessaggioTab.setHeight(2);
										labelMessaggioTab.setPadding(5);
										detail.addMember(labelMessaggioTab, 0);
									}
									attributiAddDocDetails.put(key, detail);
									VLayout layout = new VLayout();
									layout.setHeight100();
									layout.setWidth100();
									layout.setMembers(detail);
									attributiAddDocLayouts.put(key, layout);
									VLayout layoutTab = new VLayout();
									layoutTab.addMember(layout);
									Tab tab = new Tab("<b>" + attributiAddDocTabs.get(key) + "</b>");
									tab.setAttribute("tabID", key);
									tab.setPrompt(attributiAddDocTabs.get(key));
									tab.setPane(layoutTab);
									tabSet.addTab(tab);
								}
							}
							afterCaricaAttributiDinamiciDoc();
						}
					}
				});
			}
		}
	}

	// UGUALE AD ATTI2
	public void salvaAttributiDinamiciDoc(boolean flgIgnoreObblig, String rowidDoc, String activityNameWF, String esitoActivityWF,
			final ServiceCallback<Record> callback) {
		if (attributiAddDocTabs != null && attributiAddDocTabs.size() > 0) {
			Record lRecordDoc = new Record();
			lRecordDoc.setAttribute("rowId", rowidDoc);
			lRecordDoc.setAttribute("valori", getAttributiDinamiciDoc());
			lRecordDoc.setAttribute("tipiValori", getTipiAttributiDinamiciDoc());
			lRecordDoc.setAttribute("activityNameWF", activityNameWF);
			lRecordDoc.setAttribute("esitoActivityWF", esitoActivityWF);
			GWTRestService<Record, Record> lGWTRestServiceDoc = new GWTRestService<Record, Record>("AttributiDinamiciDocDatasource");
			if (flgIgnoreObblig) {
				lGWTRestServiceDoc.addParam("flgIgnoreObblig", "1");
			}
			lGWTRestServiceDoc.call(lRecordDoc, new ServiceCallback<Record>() {

				@Override
				public void execute(Record object) {
					if (callback != null) {
						callback.execute(object);
					}
				}
				
				@Override
				public void manageError() {
					if (callback != null) {
						callback.manageError();
					}
				}
			});
		} else {
			if (callback != null) {
				callback.execute(new Record());
			}
		}
	}
	
}