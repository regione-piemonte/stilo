/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.Map;

import com.smartgwt.client.data.DSCallback;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.FormItem;
import com.smartgwt.client.widgets.form.fields.events.ChangedEvent;
import com.smartgwt.client.widgets.form.fields.events.ChangedHandler;
import com.smartgwt.client.widgets.form.validator.RequiredIfFunction;
import com.smartgwt.client.widgets.form.validator.RequiredIfValidator;
import com.smartgwt.client.widgets.layout.VLayout;

import it.eng.auriga.ui.module.layout.client.i18n.I18NUtil;
import it.eng.utility.ui.module.core.client.callback.ServiceCallback;
import it.eng.utility.ui.module.core.client.datasource.GWTRestDataSource;
import it.eng.utility.ui.module.layout.client.common.CustomLayout;
import it.eng.utility.ui.module.layout.client.common.HeaderDetailSection;
import it.eng.utility.ui.module.layout.client.common.filter.item.AnnoItem;
import it.eng.utility.ui.module.layout.client.common.items.ExtendedTextItem;

/**
 * CustomDetail che contiene la maschera per la protocollazione di MILANO
 * 
 * @author Mattia Zanin
 *
 */

public class ProtocolloPregressoDetail extends ProtocollazioneDetailBozze {

	protected ProtocolloPregressoDetail instance;

	protected HeaderDetailSection detailSectionNumerazionePGPregresso;
	protected DynamicForm numerazionePGPregressoForm;
//	protected TextItem siglaProtocolloPregressoItem;
	protected ExtendedTextItem numProtocolloPregressoItem;
	protected AnnoItem annoProtocolloPregressoItem;

//	protected DetailSection detailSectionIdentificativoWF;
//	protected DynamicForm identificativoWFForm;
//	protected TextItem siglaIdentificativoWFItem;
//	protected TextItem numIdentificativoWFItem;
//	protected AnnoItem annoIdentificativoWFItem;
	
	public ProtocolloPregressoDetail(String nomeEntita) {
		
		super(nomeEntita);

		instance = this;
	}
	
	@Override
	public boolean isModalitaWizard() {
		return false;
	}

	@Override
	public String getFlgTipoProv() {
		return null;
	}
	
	@Override
	public boolean showModelliSelectItem() {
		return false;
	}
	
	@Override
	public boolean showUoProtocollanteSelectItem() {
		return false;
	}
	
	@Override
	public VLayout getLayoutDatiDocumento() {		
		
		VLayout layoutDatiDocumento = new VLayout(5);	
		
		createDetailSectionRegistrazione();
		detailSectionRegistrazione.setVisible(false);
		layoutDatiDocumento.addMember(detailSectionRegistrazione);		
		
		createDetailSectionNumerazionePGPregresso();
		layoutDatiDocumento.addMember(detailSectionNumerazionePGPregresso);
		
		createDetailSectionTipoDocumento();
		detailSectionTipoDocumento.setVisible(tipoDocumento != null && !"".equals(tipoDocumento));
		layoutDatiDocumento.addMember(detailSectionTipoDocumento);
		
		createDetailSectionAltreVie();
		layoutDatiDocumento.addMember(detailSectionAltreVie);
		
		createDetailSectionContenuti();
		layoutDatiDocumento.addMember(detailSectionContenuti);

		createDetailSectionAllegati();
		layoutDatiDocumento.addMember(detailSectionAllegati);
		
//		createDetailSectionIdentificativoWF();
//		layoutDatiDocumento.addMember(detailSectionIdentificativoWF);
		
		createDetailSectionAltriDati();
		layoutDatiDocumento.addMember(detailSectionAltriDati);

		return layoutDatiDocumento;
	}
	
	@Override
	public boolean showDetailSectionRegistrazione() {
		return false;
	}
	
	/**
	 * Metodo che crea la sezione "Registrazione a protocollo generale" di PG@Web
	 * 
	 */
	protected void createDetailSectionNumerazionePGPregresso() {
		
		numerazionePGPregressoForm = new DynamicForm();
		numerazionePGPregressoForm.setValuesManager(vm);
		numerazionePGPregressoForm.setWidth100();
		numerazionePGPregressoForm.setPadding(5);
		numerazionePGPregressoForm.setWrapItemTitles(false);
		numerazionePGPregressoForm.setNumCols(18);
		numerazionePGPregressoForm.setColWidths(1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, "*", "*");
		numerazionePGPregressoForm.setTabSet(tabSet);
		numerazionePGPregressoForm.setTabID("HEADER");

//		siglaProtocolloPregressoItem = new TextItem("siglaProtocolloPregresso", I18NUtil.getMessages().archivio_contratto_sigla_protocollo()) {
//		
//			@Override
//			public void setCanEdit(Boolean canEdit) {
//				super.setCanEdit(canEdit);
//				setTextBoxStyle(canEdit ? it.eng.utility.Styles.textItem : it.eng.utility.Styles.textItemBold);  
//				setTabIndex(canEdit ? 0 : -1);				
//			}
//		};
//		siglaProtocolloPregressoItem.setColSpan(1);
//		siglaProtocolloPregressoItem.setWidth(80);
//		siglaProtocolloPregressoItem.setRequired(true);
//		siglaProtocolloPregressoItem.setShowIfCondition(new FormItemIfFunction() {
//			
//			@Override
//			public boolean execute(FormItem item, Object value, DynamicForm form) {
//				item.setValue("PG");
//				item.setCanEdit(false);
//				return true;
//			}
//		});

		numProtocolloPregressoItem = new ExtendedTextItem("nroProtocolloPregresso", "PG " + I18NUtil.getMessages().archivio_contratto_numero_protocollo()) {
			
			@Override
			public void setCanEdit(Boolean canEdit) {
				super.setCanEdit(canEdit);
				setTextBoxStyle(canEdit ? it.eng.utility.Styles.textItem : it.eng.utility.Styles.textItemBold);  
				setTabIndex(canEdit ? 0 : -1);				
			}
		};
		numProtocolloPregressoItem.setWidth(100);
		numProtocolloPregressoItem.setLength(50);
		numProtocolloPregressoItem.setRequired(true);
		numProtocolloPregressoItem.addChangedBlurHandler(new ChangedHandler() {

			public void onChanged(ChangedEvent event) {
				recuperaDatiProtocolloPGWeb();
			}
		});

		annoProtocolloPregressoItem = new AnnoItem("annoProtocolloPregresso", "<b>" + I18NUtil.getMessages().archivio_contratto_anno_protocollo() + "</b>") {
			
			@Override
			public void setCanEdit(Boolean canEdit) {
				super.setCanEdit(canEdit);
				setTextBoxStyle(canEdit ? it.eng.utility.Styles.textItem : it.eng.utility.Styles.textItemBold);  
				setTabIndex(canEdit ? 0 : -1);				
			}
		};
		annoProtocolloPregressoItem.setWidth(100);
		annoProtocolloPregressoItem.setValidators(new RequiredIfValidator(new RequiredIfFunction() {
			
			@Override
			public boolean execute(FormItem formItem, Object value) {
				return true;
			}
		}));
		annoProtocolloPregressoItem.addChangedBlurHandler(new ChangedHandler() {

			public void onChanged(ChangedEvent event) {
				recuperaDatiProtocolloPGWeb();
			}
		});

		numerazionePGPregressoForm.setFields(/*siglaProtocolloPregressoItem,*/ numProtocolloPregressoItem, annoProtocolloPregressoItem);
		
		detailSectionNumerazionePGPregresso = new HeaderDetailSection("Registrazione a protocollo generale", true, true, true,	numerazionePGPregressoForm);		
	}
	
	@Override
	protected void createDetailSectionContenuti() {

		super.createDetailSectionContenuti();
		
		if(flgOriginaleCartaceoItem != null) {
			flgOriginaleCartaceoItem.setDefaultValue(true);
		}

		// Nella sezione dei contenuti tolgo la parte relativa alla riservatezza
		detailSectionContenuti = new ProtocollazioneDetailSection(I18NUtil.getMessages().protocollazione_detail_contenutiForm_title(), true, true, false, contenutiForm, filePrimarioForm);
	}
	
	/**
	 * Metodo che indica se mostrare o meno la sezione "Indirizzo di riferimento"
	 * 
	 */
	@Override
	public boolean showDetailSectionAltreVie() {
		Boolean flgTipoDocConVie = getFlgTipoDocConVie();
		return showAttributiDinamiciDoc() && flgTipoDocConVie != null && flgTipoDocConVie;
	}
	
	@Override
	public boolean showOpenDetailSectionAltreVie() {
		return true;
	}
	
	@Override
	public boolean isRequiredDetailSectionAltreVie() {
		return true;
	}
	
	@Override
	public String getTitleDetailSectionAltreVie() {
		return "Indirizzo di riferimento";
	}
	
	@Override
	public boolean showDetailSectionAltreVieAfterHeader() {
		return true;
	}
	
	public boolean showFlgFuoriComuneInAltreVie() {
		return false;
	}
	
	public boolean getFlgFuoriComuneInAltreVie() {
		return false;
	}
	
	public boolean isNotReplicableAltreVieItem() {
		return true;
	}
	
	@Override
	public Record getCanvasDefaultRecordAllegati() {		
		if(isModalitaWizard()) {
			Record lRecord = new Record();	
			lRecord.setAttribute("flgOriginaleCartaceo", true);
			return lRecord;				
		}
		return null;
	}
	
//	/**
//	 * Metodo che crea la sezione "Identificativo di workflow"
//	 * 
//	 */
//	protected void createDetailSectionIdentificativoWF() {
//		
//		identificativoWFForm = new DynamicForm();
//		identificativoWFForm.setValuesManager(vm);
//		identificativoWFForm.setWidth100();
//		identificativoWFForm.setPadding(5);
//		identificativoWFForm.setWrapItemTitles(false);
//		identificativoWFForm.setNumCols(18);
//		identificativoWFForm.setColWidths(1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, "*", "*");
//		identificativoWFForm.setTabSet(tabSet);
//		identificativoWFForm.setTabID("HEADER");
//
//		siglaIdentificativoWFItem = new TextItem("siglaIdentificativoWF", I18NUtil.getMessages().archivio_contratto_sigla_protocollo());
//		siglaIdentificativoWFItem.setColSpan(1);
//		siglaIdentificativoWFItem.setWidth(80);
//
//		numIdentificativoWFItem = new TextItem("nroIdentificativoWF", "W.F. " + I18NUtil.getMessages().archivio_contratto_numero_protocollo());
//		numIdentificativoWFItem.setWidth(100);
//		numIdentificativoWFItem.setLength(50);		
//
//		annoIdentificativoWFItem = new AnnoItem("annoIdentificativoWF", I18NUtil.getMessages().archivio_contratto_anno_protocollo());
//		annoIdentificativoWFItem.setWidth(100);
//		
//
//		identificativoWFForm.setFields(/*siglaIdentificativoWFItem,*/ numIdentificativoWFItem, annoIdentificativoWFItem);
//		
//		detailSectionIdentificativoWF = new DetailSection("Estremi pratica (W.F.)", true, true, false,	identificativoWFForm);
//	}
	
	@Override
	public String getTitleDetailSectionAltriDati() {
		return "Note";
	}

	@Override
	protected void createAltriDatiForm() {
		
		super.createAltriDatiForm();
		
		noteItem.setShowTitle(false);
		altriDatiForm.setFields(noteItem);
	}
	
	@Override
	public boolean showTabEsibentiEInteressati() {
		return false;
	}
	
	@Override
	public boolean showTabAltreVie() {
		return false;
	}

	protected void recuperaDatiProtocolloPGWeb() {	
		
		if(numProtocolloPregressoItem.getValueAsString() != null && !"".equals(numProtocolloPregressoItem.getValueAsString()) &&
			annoProtocolloPregressoItem.getValueAsString() != null && !"".equals(annoProtocolloPregressoItem.getValueAsString())) {		
			
			Record lDetailRecord = new Record(vm.getValues());	
			lDetailRecord.setAttribute("siglaProtocolloPregresso", "PG");
			GWTRestDataSource lGwtRestDataSource = new GWTRestDataSource("ProtocolloDataSource");
			lGwtRestDataSource.executecustom("recuperaDatiProtocolloPGWeb", lDetailRecord, new DSCallback() {	
				
				@Override
				public void execute(DSResponse response, Object rawData, DSRequest request) {					
					
					if (response.getStatus() == DSResponse.STATUS_SUCCESS) {						
						Record record = response.getData()[0];						
						oggettoItem.setValue(record.getAttribute("oggetto"));
						if(showDetailSectionAltreVie()) {
							Record lRecordAltreVie = new Record();
							lRecordAltreVie.setAttribute("listaAltreVie", record.getAttributeAsRecordList("listaAltreVie"));
							altreVieForm.setValues(lRecordAltreVie.toMap());
						}
					}
				}
			});		
		}
	}
	
	@Override
	public Record getRecordToSave(String motivoVarDatiReg) {	
		
		Record lRecordToSave = super.getRecordToSave(motivoVarDatiReg);		
		
		addFormValues(lRecordToSave, numerazionePGPregressoForm);
		if(showDetailSectionAltreVie()) {
			addFormValues(lRecordToSave, altreVieForm);
		}
//		addFormValues(lRecordToSave, identificativoWFForm);		
		lRecordToSave.setAttribute("flgCaricamentoPGPregressoDaGUI", true);
		
		return lRecordToSave;
	}		
	
	@Override
	public void newMode(boolean isNewProtComeCopia) {
		
		super.newMode(isNewProtComeCopia);
		
		nuovaProtButton.hide();
		nuovaProtComeCopiaButton.hide();
		detailSectionRegistrazione.hide();
	}	
	
	@Override
	public void viewMode() {
		
		super.viewMode();
		
		nuovaProtButton.hide();
		nuovaProtComeCopiaButton.hide();
		detailSectionRegistrazione.hide();
	}
	
	@Override
	public void editMode() {
		
		super.editMode();
		
		nuovaProtButton.hide();
		nuovaProtComeCopiaButton.hide();
		detailSectionRegistrazione.hide();
	}
	
	@Override
	public void modificaDatiMode(Boolean abilAggiuntaFile) {
		
		this.editMode = "modificaDati";
		setModificaDatiReg(false);
		editMode();
		if (numerazionePGPregressoForm != null) {
			setCanEdit(false, numerazionePGPregressoForm);
		}
		if (mittentiItem != null) {
			mittentiItem.setCanEdit(false);
		}
		if (destinatariItem != null) {
			destinatariItem.setCanEdit(false);			
		}
		if (codRapidoOggettoItem != null) {
			codRapidoOggettoItem.setCanEdit(false);
		}
		if (oggettoItem != null) {
			oggettoItem.setCanEdit(false);
		}
		if (lookupTemplateOggettoButton != null) {
			lookupTemplateOggettoButton.setCanEdit(false);
		}
		if (salvaComeTemplateOggettoButton != null) {
			salvaComeTemplateOggettoButton.setCanEdit(false);
		}
		if (generaDaModelloButton != null) {
			generaDaModelloButton.setCanEdit(false);
		}
		Record record = new Record(getValuesManager().getValues());
		if (abilAggiuntaFile == null) {
			abilAggiuntaFile = record.getAttributeAsBoolean("abilAggiuntaFile");
		}
		if (abilAggiuntaFile && (uriFilePrimarioItem.getValue() == null || uriFilePrimarioItem.getValue().equals(""))) {
			nomeFilePrimarioItem.setCanEdit(true);
			filePrimarioButtons.setCanEdit(true);
			uploadFilePrimarioItem.setCanEdit(true);
			if (generaDaModelloButton != null) {
				generaDaModelloButton.setCanEdit(true);
			}
		} else {
			nomeFilePrimarioItem.setCanEdit(false);
			filePrimarioButtons.setCanEdit(false);
			uploadFilePrimarioItem.setCanEdit(false);
			if (generaDaModelloButton != null) {
				generaDaModelloButton.setCanEdit(false);
			}
		}
		if (abilAggiuntaFile) {
			if(fileAllegatiItem != null) {
				if(fileAllegatiItem instanceof AllegatiGridItem) {
					((AllegatiGridItem)fileAllegatiItem).setAggiuntaFileMode();
				} else if(fileAllegatiItem instanceof AllegatiItem) {
					((AllegatiItem)fileAllegatiItem).setAggiuntaFileMode();
				}				
			}
		} else {
			fileAllegatiItem.setCanEdit(false);
		}		
	}
	
	@Override
	public void setInitialValues() {

		super.setInitialValues();
		
		if (detailSectionAltreVie != null) {
			detailSectionAltreVie.setVisible(showDetailSectionAltreVie());
		}
	}
	
	@Override
	protected void manageResponse(CustomLayout layout, Record lRecord, Map initialValues) {

		super.manageResponse(layout, lRecord, initialValues);
		
		if (detailSectionAltreVie != null) {
			detailSectionAltreVie.setVisible(showDetailSectionAltreVie());
		}
	}
	
	@Override
	public void clickNuovaProtComeCopia(ServiceCallback<Record> callback) {

		boolean flgNoEsibente = true;
		boolean flgNoMittenti = false;
		boolean flgNoDestinatari = false;
		boolean flgNoAltriAssegnatari = false;
		boolean flgNoOggetto = false;
		boolean flgNoPrimario = false;
		boolean flgNoAllegati = false;
		boolean flgNoFileAllegati = false;
		boolean flgNoDocumentiCollegati = true;
		boolean flgNoFascicolazione = false;
		boolean flgNoAttributiCustom = false;

		nuovaProtComeCopia(flgNoEsibente, flgNoMittenti, flgNoDestinatari, flgNoAltriAssegnatari, flgNoOggetto, flgNoPrimario, flgNoAllegati, flgNoFileAllegati,
				flgNoDocumentiCollegati, flgNoFascicolazione, flgNoAttributiCustom, callback);
		// rimango sulla stessa maschera quindi devo aggiornare il titolo della portlet
		redrawTitleOfPortlet();
	}
	
	public void nuovaProtComeCopia(Map values, Map attributiDinamiciDoc, final boolean flgNoEsibente, final boolean flgNoMittenti, final boolean flgNoDestinatari,
			final boolean flgNoAltriAssegnatari, final boolean flgNoOggetto, final boolean flgNoPrimario, final boolean flgNoAllegati,
			final boolean flgNoFileAllegati, final boolean flgNoDocumentiCollegati, final boolean flgNoFascicolazione, final boolean flgNoAttributiCustom, final ServiceCallback<Record> callback) {
		
		super.nuovaProtComeCopia(values, attributiDinamiciDoc, flgNoEsibente, flgNoMittenti, flgNoDestinatari, flgNoAltriAssegnatari, flgNoOggetto, flgNoPrimario, flgNoAllegati, flgNoFileAllegati, 
		flgNoDocumentiCollegati, flgNoFascicolazione, flgNoAttributiCustom, new ServiceCallback<Record>() {
			
			@Override
			public void execute(Record object) {
//				numerazionePGPregressoForm.setValue("siglaProtocolloPregresso", "");
				numerazionePGPregressoForm.setValue("nroProtocolloPregresso", "");
				numerazionePGPregressoForm.setValue("annoProtocolloPregresso", "");
				if(callback != null) {
					callback.execute(null);
				}
			}
		});
	}
	
}