/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.google.gwt.core.client.Scheduler;
import com.google.gwt.core.client.Scheduler.ScheduledCommand;
import com.google.gwt.user.client.Timer;
import com.smartgwt.client.data.DSCallback;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.data.RecordList;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.FieldType;
import com.smartgwt.client.types.VerticalAlignment;
import com.smartgwt.client.util.BooleanCallback;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.FormItemIfFunction;
import com.smartgwt.client.widgets.form.fields.FormItem;
import com.smartgwt.client.widgets.form.fields.HiddenItem;
import com.smartgwt.client.widgets.form.fields.RadioGroupItem;
import com.smartgwt.client.widgets.form.fields.events.ChangedEvent;
import com.smartgwt.client.widgets.form.fields.events.ChangedHandler;
import com.smartgwt.client.widgets.form.fields.events.IconClickEvent;
import com.smartgwt.client.widgets.form.fields.events.IconClickHandler;
import com.smartgwt.client.widgets.form.validator.CustomValidator;
import com.smartgwt.client.widgets.form.validator.RequiredIfFunction;
import com.smartgwt.client.widgets.form.validator.RequiredIfValidator;
import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.client.widgets.toolbar.ToolStrip;

import it.eng.auriga.ui.module.layout.client.AurigaLayout;
import it.eng.auriga.ui.module.layout.client.ErroreMassivoPopup;
import it.eng.auriga.ui.module.layout.client.i18n.I18NUtil;
import it.eng.utility.ui.module.core.client.callback.ServiceCallback;
import it.eng.utility.ui.module.core.client.callback.UploadItemCallBackHandler;
import it.eng.utility.ui.module.core.client.datasource.GWTRestDataSource;
import it.eng.utility.ui.module.core.shared.message.MessageBean;
import it.eng.utility.ui.module.core.shared.message.MessageType;
import it.eng.utility.ui.module.layout.client.Layout;
import it.eng.utility.ui.module.layout.client.common.CustomLayout;
import it.eng.utility.ui.module.layout.client.common.DetailToolStripButton;
import it.eng.utility.ui.module.layout.client.common.file.DownloadFile;
import it.eng.utility.ui.module.layout.client.common.file.FileUploadItemWithFirmaAndMimeType;
import it.eng.utility.ui.module.layout.client.common.file.InfoFileRecord;
import it.eng.utility.ui.module.layout.client.common.file.ManageInfoCallbackHandler;
import it.eng.utility.ui.module.layout.client.common.items.CheckboxItem;
import it.eng.utility.ui.module.layout.client.common.items.ImgButtonItem;
import it.eng.utility.ui.module.layout.client.common.items.SelectItem;
import it.eng.utility.ui.module.layout.client.common.items.TextItem;
import it.eng.utility.ui.module.layout.shared.util.FrontendUtil;

public class RegistrazioneMultiplaUscitaDetail extends ProtocollazioneDetailUscita {
	
	public static final String _TIPO_REG_PG = "Prot. generale";
	public static final String _TIPO_REG_R = "Repertorio";
	
	public static final String _FLG_SI = "SI";
	public static final String _FLG_NO = "NO";

	protected RegistrazioneMultiplaUscitaDetail _instance;
		
	protected ToolStrip detailToolStripMultipla;	
	protected DetailToolStripButton registraButton;
	
	protected ProtocollazioneHeaderDetailSection detailSectionNuovaRegistrazioneMultipla;
	protected DynamicForm nuovaRegistrazioneMultiplaForm;	
	protected SelectItem tipoRegistrazioneMultiplaItem;
		
	protected ProtocollazioneDetailSection detailSectionDestinatariDiversiXReg;
	protected DynamicForm destinatariDiversiXRegForm;
	protected TextItem nomeFileXlsDestinatariDiversiXRegItem;
	protected FileUploadItemWithFirmaAndMimeType uploadFileXlsDestinatariDiversiXRegItem;
	protected ImgButtonItem downloadFileXlsDestinatariDiversiXRegButton;
	protected ImgButtonItem downloadTemplateXlsDestinatariDiversiXRegButton;
	protected HiddenItem uriFileXlsDestinatariDiversiXRegItem;
	protected HiddenItem idFoglioXlsDestinatariDiversiXRegItem;
	protected HiddenItem destinatariDiversiXRegItem;
	
	private RecordList listaRecordCaricatiInUploadMultiplo = new RecordList();
	
	protected ProtocollazioneDetailSection detailSectionCasellaMittente;
	protected DynamicForm casellaMittenteForm;
	protected SelectItem casellaMittenteItem;
	
	protected ImgButtonItem anteprimaOggettiButton;
	
	protected DynamicForm opzioniFilePrincipaleForm;
	protected RadioGroupItem flgFilePrincipaleUgualeXTutteRegItem;
	protected TextItem percorsoFilePrimariItem;
	protected ImgButtonItem verificaFilePrimariInXlsButton;
	protected CheckboxItem flgApponiTimbroFilePrimariNonFirmatiItem;
			
	protected ProtocollazioneDetailSection detailSectionAllegatiDiversiXReg;
	protected DynamicForm allegatiDiversiXRegForm;
	protected TextItem percorsoFileAllegatiItem;		
	protected ImgButtonItem verificaFileAllegatiInXlsButton;
	protected CheckboxItem flgApponiTimbroFileAllegatiNonFirmatiItem;
	
	private final int ALT_POPUP_ERR_MASS = 375;
	private final int LARG_POPUP_ERR_MASS = 620;
			
	/**
	 * Creato questo flag come "semaforo" di accesso al pulsante di registrazione
	 * 
	 */
	private boolean abilRegistraButton = true;
	
	/**
	 * Metodo costruttore
	 * 
	 */
	public RegistrazioneMultiplaUscitaDetail(String nomeEntita) {

		super(nomeEntita);

		_instance = this;		
	}
	

	/**
	 * Metodo che indica se è attiva la modalità wizard (per la massiva non lo è mai)
	 * 
	 */
	@Override
	public boolean isModalitaWizard() {
		return false;
	}
	
	/**
	 * Metodo che indica se mostrare o meno la select dei modelli
	 * 
	 */
	@Override
	public boolean showModelliSelectItem() {
		return false;
	}
	
	/**
	 * Metodo che restituisce la label della select U.O. registrazione
	 * 
	 */
	@Override
	public String getTitleUoProtocollanteSelectItem() {
		return "<b>U.O. di registrazione</b>";
	}
	
	/**
	 * Metodo che indica se mostrare o meno la barra inferiore contenente i
	 * bottoni delle azioni da dettaglio
	 * 
	 */
	public boolean showDetailToolStrip() {
		return false;
	}
	
	/**
	 * Metodo che indica se è attiva la modalità con i dati principali del documento e di assegnazione/classificazione in un unico tab
	 * 
	 */
	@Override
	public boolean showSingleTabHeader() {
		return true;
	}
	
	/**
	 * Metodo per costruire il layout della maschera
	 * 
	 */
	protected void createMainLayout() {
		
		super.createMainLayout();

		createDetailToolStripMultipla();

		List<Canvas> members = new ArrayList<Canvas>();
		for(int i = 0; i < mainLayout.getMembers().length; i++) {
			if(mainLayout.getMembers()[i] != null) {
				members.add(mainLayout.getMembers()[i]);
				if(mainLayout.getMembers()[i].equals(detailToolStrip)) {
					members.add(detailToolStripMultipla);
				} 
			}
		}
		mainLayout.setMembers(members.toArray(new Canvas[members.size()]));
	}
	
	/**
	 * Metodo che restituisce il layout del tab "Dati principali"
	 * 
	 */	
	@Override
	public VLayout getLayoutDatiPrincipaliRER() {
		return getLayoutDatiPrincipali();
	}
		
	public VLayout getLayoutDatiPrincipali() {
		
		VLayout layoutDatiPrincipali = new VLayout(5);
		if (AurigaLayout.getIsAttivaAccessibilita()) {
			layoutDatiPrincipali.setTabIndex(-1);
			layoutDatiPrincipali.setCanFocus(false);		
		}	
		
		createDetailSectionRegistrazione();
		detailSectionRegistrazione.setVisible(false);
		layoutDatiPrincipali.addMember(detailSectionRegistrazione);

		createDetailSectionNuovaRegistrazioneMultipla();			
		layoutDatiPrincipali.addMember(detailSectionNuovaRegistrazioneMultipla);
		
		if(showDetailSectionTipoDocumento()) {
			createDetailSectionTipoDocumento();
			detailSectionTipoDocumento.setVisible(tipoDocumento != null && !"".equals(tipoDocumento));
			layoutDatiPrincipali.addMember(detailSectionTipoDocumento);
		}
		
		createDetailSectionMittenti();
		layoutDatiPrincipali.addMember(detailSectionMittenti);
		
		if (showDetailSectionDestinatari()) {
			createDetailSectionDestinatariDiversiXReg();
			layoutDatiPrincipali.addMember(detailSectionDestinatariDiversiXReg);		
			
			createDetailSectionDestinatari();
			layoutDatiPrincipali.addMember(detailSectionDestinatari);
		}
		
		createDetailSectionCasellaMittente();
		layoutDatiPrincipali.addMember(detailSectionCasellaMittente);
		
		createDetailSectionAssegnazione();
		layoutDatiPrincipali.addMember(detailSectionAssegnazione);

		createDetailSectionCondivisione();
		layoutDatiPrincipali.addMember(detailSectionCondivisione);
		
		createDetailSectionContenuti();
		layoutDatiPrincipali.addMember(detailSectionContenuti);

		createDetailSectionAllegatiDiversiXReg();
		layoutDatiPrincipali.addMember(detailSectionAllegatiDiversiXReg);
		
		createDetailSectionAllegati();
		layoutDatiPrincipali.addMember(detailSectionAllegati);
		
		createDetailSectionClassificazioneFascicolazione();
		layoutDatiPrincipali.addMember(detailSectionClassificazioneFascicolazione);
		
		createDetailSectionFolderCustom();
		layoutDatiPrincipali.addMember(detailSectionFolderCustom);		
		
		createDetailSectionAltriDati();
		layoutDatiPrincipali.addMember(detailSectionAltriDati);
		
		return layoutDatiPrincipali;
	}	
	
	/**
	 * Metodo che crea la sezione "Registrazione"
	 * 
	 */	
	protected void createDetailSectionNuovaRegistrazioneMultipla() {
		
		createNuovaRegistrazioneMultiplaForm();
		
		detailSectionNuovaRegistrazioneMultipla = new ProtocollazioneHeaderDetailSection("Registrazione", true, true, true, nuovaRegistrazioneMultiplaForm);
	}
	
	/**
	 * Metodo che crea il form della sezione "Registrazione"
	 * 
	 */	
	protected void createNuovaRegistrazioneMultiplaForm() {
		
		nuovaRegistrazioneMultiplaForm = new DynamicForm();
		nuovaRegistrazioneMultiplaForm.setValuesManager(vm);
		nuovaRegistrazioneMultiplaForm.setWidth100();
		nuovaRegistrazioneMultiplaForm.setPadding(5);
		nuovaRegistrazioneMultiplaForm.setWrapItemTitles(false);
		nuovaRegistrazioneMultiplaForm.setNumCols(18);
		nuovaRegistrazioneMultiplaForm.setColWidths(1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, "*", "*");
		nuovaRegistrazioneMultiplaForm.setTabSet(tabSet);
		nuovaRegistrazioneMultiplaForm.setTabID("HEADER");
		
		tipoRegistrazioneMultiplaItem = new SelectItem("tipoRegistrazioneMultipla", "Tipo");		
		if(Layout.isPrivilegioAttivo("PRT/U") && Layout.isPrivilegioAttivo("RPR")) {
			tipoRegistrazioneMultiplaItem.setValueMap(_TIPO_REG_PG, _TIPO_REG_R);			
			tipoRegistrazioneMultiplaItem.setDefaultValue(_TIPO_REG_PG);
		} else if(Layout.isPrivilegioAttivo("PRT/U")) {
			tipoRegistrazioneMultiplaItem.setValueMap(_TIPO_REG_PG);
			tipoRegistrazioneMultiplaItem.setDefaultValue(_TIPO_REG_PG);			
		} else if(Layout.isPrivilegioAttivo("RPR")) {
			tipoRegistrazioneMultiplaItem.setValueMap(_TIPO_REG_R);
			tipoRegistrazioneMultiplaItem.setDefaultValue(_TIPO_REG_R);			
		}
		tipoRegistrazioneMultiplaItem.setWidth(120);
		tipoRegistrazioneMultiplaItem.setWrapTitle(false);
		tipoRegistrazioneMultiplaItem.setAllowEmptyValue(true);
		tipoRegistrazioneMultiplaItem.setStartRow(true);
		tipoRegistrazioneMultiplaItem.setRequired(true);
		tipoRegistrazioneMultiplaItem.addChangedHandler(new ChangedHandler() {

			@Override
			public void onChanged(ChangedEvent event) {
				nuovaRegistrazioneMultiplaForm.markForRedraw();
			}
		});
		
		GWTRestDataSource gruppiRepertorioDS = new GWTRestDataSource("LoadComboGruppiRepertorioSource", "key", FieldType.TEXT);
		gruppiRepertorioDS.addParam("flgTipoProv", "U");

		repertorioItem = new SelectItem("repertorio", "Registro");
		repertorioItem.setValueField("key");
		repertorioItem.setDisplayField("value");
		repertorioItem.setOptionDataSource(gruppiRepertorioDS);
		repertorioItem.setWidth(200);
		repertorioItem.setClearable(true);
		repertorioItem.setCachePickListResults(false);
		repertorioItem.setAllowEmptyValue(true);
		repertorioItem.setAutoFetchData(false);
		repertorioItem.setAttribute("obbligatorio", true);
		repertorioItem.setValidators(new RequiredIfValidator(new RequiredIfFunction() {
			
			@Override
			public boolean execute(FormItem formItem, Object value) {
				return tipoRegistrazioneMultiplaItem.getValue() != null && _TIPO_REG_R.equals(tipoRegistrazioneMultiplaItem.getValue());
			}
		}));
		repertorioItem.setShowIfCondition(new FormItemIfFunction() {

			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return tipoRegistrazioneMultiplaItem.getValue() != null && _TIPO_REG_R.equals(tipoRegistrazioneMultiplaItem.getValue());
			}
		});
		
		nuovaRegistrazioneMultiplaForm.setFields(tipoRegistrazioneMultiplaItem, repertorioItem);		
	}	
		
	/**
	 * Metodo che crea la sezione "Destinatari diversi per registrazione"
	 * 
	 */	
	protected void createDetailSectionDestinatariDiversiXReg() {
		
		createDestinatariDiversiXRegForm();
		
		detailSectionDestinatariDiversiXReg = new ProtocollazioneDetailSection("Destinatari diversi per registrazione", true, true, true, destinatariDiversiXRegForm);
	}
	
	/**
	 * Metodo che crea il form della sezione "Destinatari diversi per registrazione"
	 * 
	 */	
	protected void createDestinatariDiversiXRegForm() {

		destinatariDiversiXRegForm = new DynamicForm();
		destinatariDiversiXRegForm.setValuesManager(vm);
		destinatariDiversiXRegForm.setWidth100();
		destinatariDiversiXRegForm.setPadding(5);
		destinatariDiversiXRegForm.setWrapItemTitles(false);
		destinatariDiversiXRegForm.setNumCols(18);
		destinatariDiversiXRegForm.setColWidths(1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, "*", "*");
		destinatariDiversiXRegForm.setTabSet(tabSet);
		destinatariDiversiXRegForm.setTabID("HEADER");
		
		CustomValidator lFileXlsDestinatariDiversiXRegValidator = new CustomValidator() {

			@Override
			protected boolean condition(Object value) {
				return value != null && !"".equals(value) && destinatariDiversiXRegForm.getValueAsString("uriFileXlsDestinatariDiversiXReg") != null && !"".equals(destinatariDiversiXRegForm.getValueAsString("uriFileXlsDestinatariDiversiXReg"));
			}
		};
		lFileXlsDestinatariDiversiXRegValidator.setErrorMessage("Obbligatorio caricare il file xls dei destinatari");
		
		nomeFileXlsDestinatariDiversiXRegItem = new TextItem("nomeFileXlsDestinatariDiversiXReg", "Nome file xls") {
			
			@Override
			public void setCanEdit(Boolean canEdit) {
				super.setCanEdit(false);
			}
		};
		nomeFileXlsDestinatariDiversiXRegItem.setShowTitle(true);
		nomeFileXlsDestinatariDiversiXRegItem.setWidth(280);
		nomeFileXlsDestinatariDiversiXRegItem.setColSpan(1);
		nomeFileXlsDestinatariDiversiXRegItem.setAlign(Alignment.LEFT);
		nomeFileXlsDestinatariDiversiXRegItem.setValidators(lFileXlsDestinatariDiversiXRegValidator);
		nomeFileXlsDestinatariDiversiXRegItem.setAttribute("obbligatorio", true);		

		uploadFileXlsDestinatariDiversiXRegItem = new FileUploadItemWithFirmaAndMimeType(new UploadItemCallBackHandler() {

			@Override
			public void uploadEnd(final String displayFileName, final String uri) {
				destinatariDiversiXRegForm.clearErrors(true);
				destinatariDiversiXRegForm.setValue("nomeFileXlsDestinatariDiversiXReg", displayFileName);
				destinatariDiversiXRegForm.setValue("uriFileXlsDestinatariDiversiXReg", uri);
				destinatariDiversiXRegForm.markForRedraw();
				final Record newRecord = new Record();
				newRecord.setAttribute("changed", true);
				newRecord.setAttribute("nomeFile", displayFileName);
				if(uri != null && !"".equals(uri)) {
					newRecord.setAttribute("uriFileImportExcel", uri);
				}
				listaRecordCaricatiInUploadMultiplo.add(newRecord);	
			}

			@Override
			public void manageError(String error) {								
				String errorMessage = "Errore nel caricamento del file";
				if (error != null && !"".equals(error))
					errorMessage += ": " + error;
				Layout.addMessage(new MessageBean(errorMessage, "", MessageType.ERROR));
				listaRecordCaricatiInUploadMultiplo = new RecordList();
				uploadFileXlsDestinatariDiversiXRegItem.getCanvas().redraw();
				destinatariDiversiXRegForm.setValue("nomeFileXlsDestinatariDiversiXReg", "");
				destinatariDiversiXRegForm.setValue("uriFileXlsDestinatariDiversiXReg", "");
				idFoglioXlsDestinatariDiversiXRegItem.setValue("");
				destinatariDiversiXRegItem.setValue(new RecordList());
				destinatariDiversiXRegForm.markForRedraw();
			}
		}, new ManageInfoCallbackHandler() {

			@Override
			public void manageInfo(InfoFileRecord info) {
				final Record record;
				if (info == null || info.getMimetype() == null 	|| (!info.getMimetype().equals("application/excel") 
						&& !info.getMimetype().equals("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"))) {
					GWTRestDataSource.printMessage(new MessageBean("Il file risulta in un formato non riconosciuto o non ammesso. I formati validi risultano essere xls/xlsx", "", MessageType.ERROR));
					destinatariDiversiXRegForm.setValue("nomeFileXlsDestinatariDiversiXReg", "");
					destinatariDiversiXRegForm.setValue("uriFileXlsDestinatariDiversiXReg", "");
					idFoglioXlsDestinatariDiversiXRegItem.setValue("");
					destinatariDiversiXRegItem.setValue(new RecordList());
					destinatariDiversiXRegForm.markForRedraw();														
				} else {
					record = listaRecordCaricatiInUploadMultiplo.get(listaRecordCaricatiInUploadMultiplo.getLength() - 1);
					InfoFileRecord precInfo = record.getAttribute("infoFile") != null ? new InfoFileRecord(record.getAttributeAsRecord("infoFile")) : null;
					String precImpronta = precInfo != null ? precInfo.getImpronta() : null;
					String displayName = record.getAttribute("nomeFileAllegato");
					String displayNameOrig = record.getAttribute("nomeFileAllegatoOrig");			
					final Record newRecord = new Record(record.getJsObj());
					newRecord.setAttribute("infoFile", info);
					if (precImpronta == null || !precImpronta.equals(info.getImpronta()) || 
						(displayName != null && !"".equals(displayName) && displayNameOrig != null && !"".equals(displayNameOrig) && !displayName.equals(displayNameOrig))) {
						newRecord.setAttribute("isChanged", true);
					}
					newRecord.setAttribute("improntaVerPreFirma", info.getImpronta());
					newRecord.setAttribute("infoFileVerPreFirma", info);
					listaRecordCaricatiInUploadMultiplo.set(listaRecordCaricatiInUploadMultiplo.getLength() - 1, record);
					addAllDataAndRefresh(listaRecordCaricatiInUploadMultiplo, 1);
					listaRecordCaricatiInUploadMultiplo = new RecordList();
				}				
			}
		});
		uploadFileXlsDestinatariDiversiXRegItem.setColSpan(1);
		
		downloadFileXlsDestinatariDiversiXRegButton = new ImgButtonItem("downloadFileXlsDestinatariDiversiXReg", "export/xls.png", "Scarica file xls");
		downloadFileXlsDestinatariDiversiXRegButton.setAlwaysEnabled(true);
		downloadFileXlsDestinatariDiversiXRegButton.setColSpan(5);
		downloadFileXlsDestinatariDiversiXRegButton.setIconWidth(16);
		downloadFileXlsDestinatariDiversiXRegButton.setIconHeight(16);
		downloadFileXlsDestinatariDiversiXRegButton.setIconVAlign(VerticalAlignment.BOTTOM);
		downloadFileXlsDestinatariDiversiXRegButton.setAlign(Alignment.LEFT);
		downloadFileXlsDestinatariDiversiXRegButton.setWidth(16);	
//		downloadFileXlsDestinatariDiversiXRegButton.setEndRow(true);
		downloadFileXlsDestinatariDiversiXRegButton.addIconClickHandler(new IconClickHandler() {
			
			@Override
			public void onIconClick(IconClickEvent event) {
				Record lRecord = new Record();
				lRecord.setAttribute("displayFilename", destinatariDiversiXRegForm.getValueAsString("nomeFileXlsDestinatariDiversiXReg"));
				lRecord.setAttribute("uri", destinatariDiversiXRegForm.getValueAsString("uriFileXlsDestinatariDiversiXReg"));
				lRecord.setAttribute("sbustato", "false");
				lRecord.setAttribute("remoteUri", "true");
				DownloadFile.downloadFromRecord(lRecord, "FileToExtractBean");	
			}
		});
		downloadFileXlsDestinatariDiversiXRegButton.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return destinatariDiversiXRegForm.getValueAsString("uriFileXlsDestinatariDiversiXReg") != null && !"".equals(destinatariDiversiXRegForm.getValueAsString("uriFileXlsDestinatariDiversiXReg"));
			}
		});
		
		downloadTemplateXlsDestinatariDiversiXRegButton = new ImgButtonItem("downloadTemplateXlsDestinatariDiversiXReg", "file/download_manager.png", "Scarica template");
		downloadTemplateXlsDestinatariDiversiXRegButton.setAlwaysEnabled(true);
		downloadTemplateXlsDestinatariDiversiXRegButton.setColSpan(1);
		downloadTemplateXlsDestinatariDiversiXRegButton.setIconWidth(16);
		downloadTemplateXlsDestinatariDiversiXRegButton.setIconHeight(16);
		downloadTemplateXlsDestinatariDiversiXRegButton.setIconVAlign(VerticalAlignment.BOTTOM);
		downloadTemplateXlsDestinatariDiversiXRegButton.setAlign(Alignment.LEFT);
		downloadTemplateXlsDestinatariDiversiXRegButton.setWidth(16);	
//		downloadTemplateXlsDestinatariDiversiXRegButton.setEndRow(true);
		downloadTemplateXlsDestinatariDiversiXRegButton.addIconClickHandler(new IconClickHandler() {
			
			@Override
			public void onIconClick(IconClickEvent event) {
				String uriTemplateFileExcel = AurigaLayout.getParametroDB("URI_TEMPLATE_DEST_DA_XLS");
				Record lRecord = new Record();
				lRecord.setAttribute("displayFilename", "Destinatari.xlsx");
				lRecord.setAttribute("uri", uriTemplateFileExcel);
				lRecord.setAttribute("sbustato", "false");
				lRecord.setAttribute("remoteUri", "true");
				DownloadFile.downloadFromRecord(lRecord, "FileToExtractBean");	
			}
		});
		downloadTemplateXlsDestinatariDiversiXRegButton.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				String uriTemplateFileExcel = AurigaLayout.getParametroDB("URI_TEMPLATE_DEST_DA_XLS");
				return uriTemplateFileExcel!=null && !"".equalsIgnoreCase(uriTemplateFileExcel);
			}
		});
								
		uriFileXlsDestinatariDiversiXRegItem = new HiddenItem("uriFileXlsDestinatariDiversiXReg");
		idFoglioXlsDestinatariDiversiXRegItem = new HiddenItem("idFoglioXlsDestinatariDiversiXReg");
		destinatariDiversiXRegItem = new HiddenItem("listaDestinatariDiversiXReg");			
				
		destinatariDiversiXRegForm.setFields(nomeFileXlsDestinatariDiversiXRegItem, uploadFileXlsDestinatariDiversiXRegItem, downloadFileXlsDestinatariDiversiXRegButton, downloadTemplateXlsDestinatariDiversiXRegButton, uriFileXlsDestinatariDiversiXRegItem, idFoglioXlsDestinatariDiversiXRegItem, destinatariDiversiXRegItem);
	}
	
	/**
	 * Metodi per l'import dei destinatari da xls
	 * 
	 */	
	private void addAllDataAndRefresh(final RecordList listaRecord, int numeroFileCaricati) {
		addAllDataAndRefresh(listaRecord, false, numeroFileCaricati);
	}
	
	private void addAllDataAndRefresh(final RecordList listaRecord, boolean skipRefreshNroAllegato, int numeroFileCaricati) {		
		RecordList listaFileXls = new RecordList();
		for(int i = 0; i < listaRecord.getLength(); i++) {
			Record record = listaRecord.get(i);
			Record lRecordFileXls = new Record();
			InfoFileRecord infoFile = record.getAttribute("infoFile") != null ? new InfoFileRecord(record.getAttributeAsRecord("infoFile")) : null;
			if (infoFile == null || infoFile.getMimetype() == null 	|| (!infoFile.getMimetype().equals("application/excel") 
					&& !infoFile.getMimetype().equals("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"))) {
				GWTRestDataSource.printMessage(new MessageBean("Il file risulta in un formato non riconosciuto o non ammesso. I formati validi risultano essere xls/xlsx", "", MessageType.ERROR));
			} else {
				lRecordFileXls.setAttribute("mimeType", infoFile.getMimetype());
				lRecordFileXls.setAttribute("uriExcel", record.getAttribute("uriFileImportExcel"));
				lRecordFileXls.setAttribute("nomeFile", record.getAttribute("nomeFile"));
				listaFileXls.add(lRecordFileXls);
			}
		}		
		onClickImportXlsDestinatariButton(listaFileXls, numeroFileCaricati);
	}
	
	private void onClickImportXlsDestinatariButton(final RecordList listaFileXls, final int numeroFileCaricati) {
		
		Layout.showWaitPopup("Caricamento destinatari in corso...");
		Record lRecordIn = new Record ();
		lRecordIn.setAttribute("listaFileXls", listaFileXls);
		final GWTRestDataSource lRegistrazioneMultiplaUscitaDatasource = new GWTRestDataSource("RegistrazioneMultiplaUscitaDatasource");
		lRegistrazioneMultiplaUscitaDatasource.performCustomOperation("importaDestinatariFromXls", lRecordIn, new DSCallback() {							
			@Override
			public void execute(DSResponse response, Object rawData, DSRequest request) {
				Layout.hideWaitPopup();
				if(response.getStatus() == DSResponse.STATUS_SUCCESS) {
					int numDestinatari = 0;
					RecordList listaDestinatariCaricati = new RecordList();
					RecordList listaDestinatariCaricatiInError = new RecordList();
					Record lRecordOut = response.getData()[0];
					RecordList listaDestinatariXFileXls = lRecordOut.getAttributeAsRecordList("listaDestinatariXFileXls");
					for (int i = 0; i < listaDestinatariXFileXls.getLength(); i++) {
						Record fileCaricato = listaDestinatariXFileXls.get(i);
						RecordList listaDestinatari = fileCaricato.getAttributeAsRecordList("listaDestinatari");
						RecordList listaDestintariInError = fileCaricato.getAttributeAsRecordList("listaDestintariInError");
						numDestinatari += listaDestinatari.getLength();						
						if (fileCaricato.getAttributeAsBoolean("isInError") != null && fileCaricato.getAttributeAsBoolean("isInError")) {
							for (int j = 0; j < listaDestintariInError.getLength(); j++) {
								Record lRecordInError = new Record ();
								lRecordInError.setAttribute("idError", listaDestintariInError.get(j).getAttribute("numRiga"));
								lRecordInError.setAttribute("descrizione", listaDestintariInError.get(j).getAttribute("errorMessage") != null ? listaDestintariInError.get(j).getAttribute("errorMessage") : null);
								listaDestinatariCaricatiInError.add(lRecordInError);
							}
						} else {
							for (int j = 0; j < listaDestinatari.getLength(); j++) {								
								listaDestinatariCaricati.add(listaDestinatari.get(j));
							}
						}
					}
					if (listaDestinatariCaricatiInError.getLength() > 0) {
						destinatariDiversiXRegForm.setValue("nomeFileXlsDestinatariDiversiXReg", "");
						destinatariDiversiXRegForm.setValue("uriFileXlsDestinatariDiversiXReg", "");
						idFoglioXlsDestinatariDiversiXRegItem.setValue("");
						destinatariDiversiXRegItem.setValue(new RecordList());
						ErroreMassivoPopup erroreMassivoPopup = new ErroreMassivoPopup(getName(), "Riga", listaDestinatariCaricatiInError, numDestinatari, LARG_POPUP_ERR_MASS, ALT_POPUP_ERR_MASS, "Righe in errore dell'excel importato dei destinatari" );
						erroreMassivoPopup.show();
					} else {
						idFoglioXlsDestinatariDiversiXRegItem.setValue(lRecordOut.getAttribute("idFoglioXlsDestinatariDiversiXReg"));
						destinatariDiversiXRegItem.setValue(listaDestinatariCaricati);
						Layout.addMessage(new MessageBean("Caricamento " + numDestinatari + " destinatari effettuato con successo", "", MessageType.INFO));						
					}
					destinatariDiversiXRegForm.markForRedraw();
				}
			}
		}, new DSRequest());
	}
	
	/**
	 * Metodo che indica se esistono destinatari (dell'xls o di quelli comuni) per cui è indicato l'invio mail, in quel caso la casella mittente è obbligatoria
	 * 
	 */
	public boolean hasDestinatariWithInvioEmail() {
		if(showDetailSectionDestinatari()) {
			if(destinatariDiversiXRegItem.getValueAsRecordList() != null && destinatariDiversiXRegItem.getValueAsRecordList().getLength() > 0) {
				for(int i = 0; i < destinatariDiversiXRegItem.getValueAsRecordList().getLength(); i++) {
					Record record = destinatariDiversiXRegItem.getValueAsRecordList().get(i);
					if(record.getAttribute("inviaEmail") != null && record.getAttribute("inviaEmail").startsWith("SI")) {
						return true;
					}
				}
			}
			if(destinatariItem.getValueAsRecordList() != null && destinatariItem.getValueAsRecordList().getLength() > 0) {
				for(int i = 0; i < destinatariItem.getValueAsRecordList().getLength(); i++) {
					Record record = destinatariItem.getValueAsRecordList().get(i);
					Record recordMezzoTrasmissioneDestinatario = record.getAttributeAsRecord("mezzoTrasmissioneDestinatario");
					if(recordMezzoTrasmissioneDestinatario != null && recordMezzoTrasmissioneDestinatario.getAttribute("mezzoTrasmissioneDestinatario") != null) {
						if("EMAIL".equalsIgnoreCase(recordMezzoTrasmissioneDestinatario.getAttribute("mezzoTrasmissioneDestinatario"))) {
							return recordMezzoTrasmissioneDestinatario.getAttribute("indirizzoMailDestinatario") != null && !"".equals(recordMezzoTrasmissioneDestinatario.getAttribute("indirizzoMailDestinatario"));
						} else if("PEC".equalsIgnoreCase(recordMezzoTrasmissioneDestinatario.getAttribute("mezzoTrasmissioneDestinatario"))) {
							return recordMezzoTrasmissioneDestinatario.getAttribute("indirizzoPECDestinatario") != null && !"".equals(recordMezzoTrasmissioneDestinatario.getAttribute("indirizzoPECDestinatario"));
						} else if("PEO".equalsIgnoreCase(recordMezzoTrasmissioneDestinatario.getAttribute("mezzoTrasmissioneDestinatario"))) {
							return recordMezzoTrasmissioneDestinatario.getAttribute("indirizzoPEODestinatario") != null && !"".equals(recordMezzoTrasmissioneDestinatario.getAttribute("indirizzoPEODestinatario"));
						}
					}
				}
			}
		}
		return false;
	}
	
	/**
	 * Metodo che indica se esistono destinatari dell'xls per cui sono indicati degli allegati, in quel caso il percorso principale è obbligatorio
	 * 
	 */
	public boolean hasDestinatariXlsWithAllegati() {
		if(showDetailSectionDestinatari()) {
			if(destinatariDiversiXRegItem.getValueAsRecordList() != null && destinatariDiversiXRegItem.getValueAsRecordList().getLength() > 0) {
				for(int i = 0; i < destinatariDiversiXRegItem.getValueAsRecordList().getLength(); i++) {
					Record record = destinatariDiversiXRegItem.getValueAsRecordList().get(i);
					if(record.getAttribute("nomiFileAllegati") != null && !"".equals(record.getAttribute("nomiFileAllegati"))) {
						return true;
					}
				}
			}			
		}
		return false;
	}	
	
	/**
	 * Metodo che restituisce il titolo della sezione "Altri destinatari comuni a tutte le registrazioni"
	 * 
	 */
	@Override
	public String getTitleDetailSectionDestinatari() {		
		return "Altri destinatari comuni a tutte le registrazioni";
	}	
		
	/**
	 * Metodo che indica se la sezione "Altri destinatari comuni a tutte le registrazioni" è obbligatoria
	 * 
	 */
	@Override
	public boolean isRequiredDetailSectionDestinatari() {
		return false;
	}
	
	/**
	 * Metodo che indica se mostrare già aperta la sezione "Altri destinatari comuni a tutte le registrazioni"
	 * 
	 */
	@Override
	public boolean showOpenDetailSectionDestinatari() {
		return true;
	}
	
	/**
	 * Metodo che crea la sezione "Casella mittente"
	 * 
	 */	
	protected void createDetailSectionCasellaMittente() {
		
		createCasellaMittenteForm();
		
		detailSectionCasellaMittente = new ProtocollazioneDetailSection("Casella mittente", true, true, false, casellaMittenteForm);
	}
	
	/**
	 * Metodo che crea il form della sezione "Casella mittente"
	 * 
	 */	
	protected void createCasellaMittenteForm() {
		
		casellaMittenteForm = new DynamicForm();
		casellaMittenteForm.setValuesManager(vm);
		casellaMittenteForm.setWidth100();
		casellaMittenteForm.setPadding(5);
		casellaMittenteForm.setWrapItemTitles(false);
		casellaMittenteForm.setNumCols(18);
		casellaMittenteForm.setColWidths(1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, "*", "*");
		casellaMittenteForm.setTabSet(tabSet);
		casellaMittenteForm.setTabID("HEADER");
		
		GWTRestDataSource casellaMittenteDS = new GWTRestDataSource("AccountInvioEmailDatasource");
		casellaMittenteDS.addParam("finalita", "INVIO");
//		casellaMittenteDS.addParam("tipoMail", "PEC");
		
		casellaMittenteItem = new SelectItem("casellaMittente", "Casella mittente");
		casellaMittenteItem.setShowTitle(false);
		casellaMittenteItem.setColSpan(8);
		casellaMittenteItem.setValueField("key");
		casellaMittenteItem.setDisplayField("value");
		casellaMittenteItem.setAllowEmptyValue(false);
		casellaMittenteItem.setWidth(400);
		casellaMittenteItem.setAddUnknownValues(false);
		casellaMittenteItem.setRejectInvalidValueOnChange(true);		
		casellaMittenteItem.setOptionDataSource(casellaMittenteDS);
		casellaMittenteItem.setValidators(new RequiredIfValidator(new RequiredIfFunction() {
			
			@Override
			public boolean execute(FormItem formItem, Object value) {
				return hasDestinatariWithInvioEmail();
			}
		}));
		casellaMittenteItem.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				if(hasDestinatariWithInvioEmail()) {
					casellaMittenteItem.setAttribute("obbligatorio", true);
					casellaMittenteItem.setTitle(FrontendUtil.getRequiredFormItemTitle("Casella mittente"));
					detailSectionCasellaMittente.setRequired(true);
				} else {
					casellaMittenteItem.setAttribute("obbligatorio", false);
					casellaMittenteItem.setTitle("Casella mittente");
					detailSectionCasellaMittente.setRequired(false);
				}											
				return true;
			}
		});
	
		casellaMittenteForm.setFields(casellaMittenteItem);	
	}
	
	/**
	 * Metodo che indica se è obbligatorio o meno il campo relativo al nome file primario nella sezione "Contenuti"
	 * 
	 */
	@Override
	public boolean isRequiredNomeFilePrimarioItem() {
		
		if(flgFilePrincipaleUgualeXTutteRegItem.getValue() != null && _FLG_SI.equals(flgFilePrincipaleUgualeXTutteRegItem.getValue())) {
			return super.isRequiredNomeFilePrimarioItem();
		}
		return false;
	}
	
	/**
	 * Metodo che crea la sezione "Contenuti"
	 * 
	 */
	@Override
	protected void createDetailSectionContenuti() {

		super.createDetailSectionContenuti();
		
		createOpzioniFilePrincipaleForm();
						
		List<DynamicForm> forms = new ArrayList<DynamicForm>();
		for(int i = 0; i < detailSectionContenuti.getForms().length; i++) {
			if(detailSectionContenuti.getForms()[i] != null) {
				if(detailSectionContenuti.getForms()[i].equals(filePrimarioForm)) {
					forms.add(opzioniFilePrincipaleForm);
				} 
				forms.add(detailSectionContenuti.getForms()[i]);
			}
		}
		detailSectionContenuti.setForms(forms.toArray(new DynamicForm[forms.size()]));
	}
	
	/**
	 * Metodo che crea il form dell'oggetto della sezione "Contenuti"
	 * 
	 */
	protected void createContenutiForm() {
		
		super.createContenutiForm();
		
		anteprimaOggettiButton = new ImgButtonItem("anteprimaOggetti", "file/preview.png", "Anteprima oggetti");
		anteprimaOggettiButton.setAlwaysEnabled(true);
		anteprimaOggettiButton.setColSpan(1);
		anteprimaOggettiButton.setIconWidth(16);
		anteprimaOggettiButton.setIconHeight(16);
		anteprimaOggettiButton.setIconVAlign(VerticalAlignment.BOTTOM);
		anteprimaOggettiButton.setAlign(Alignment.LEFT);
		anteprimaOggettiButton.setWidth(16);	
		anteprimaOggettiButton.addIconClickHandler(new IconClickHandler() {
			
			@Override
			public void onIconClick(IconClickEvent event) {
				clickAnteprimaOggetti();
			}
		});
		
		List<FormItem> items = new ArrayList<FormItem>();
		for(int i = 0; i < contenutiForm.getFields().length; i++) {
			if(contenutiForm.getFields()[i] != null) {
				items.add(contenutiForm.getFields()[i]);
				if(contenutiForm.getFields()[i].equals(salvaComeTemplateOggettoButton)) {
					items.add(anteprimaOggettiButton);
				} 
			}
		}
		contenutiForm.setFields(items.toArray(new FormItem[items.size()]));
	}
	
	/**
	 * Metodo che implementa l'azione del bottone "Anteprima oggetti"
	 * 
	 */
	public void clickAnteprimaOggetti() {	
		Layout.showWaitPopup("Generazione anteprima oggetti...");				
		final GWTRestDataSource lRegistrazioneMultiplaUscitaDatasource = new GWTRestDataSource("RegistrazioneMultiplaUscitaDatasource");	
		lRegistrazioneMultiplaUscitaDatasource.executecustom("generaAnteprimaOggetti", getRecordToSave(null), new DSCallback() {
			@Override
			public void execute(DSResponse response, Object rawData, DSRequest request) {
				Layout.hideWaitPopup();
				if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
					final Record recordPreview = response.getData()[0];
					preview(recordPreview);					
				}				
			}		
		});
	}
		
	/**
	 * Metodo che crea il form con l'opzione di scelta obbligatoria "File principale uguale per tutte le registrazioni" della sezione "Contenuti"
	 * 
	 */	
	protected void createOpzioniFilePrincipaleForm() {
		
		opzioniFilePrincipaleForm = new DynamicForm();
		opzioniFilePrincipaleForm.setValuesManager(vm);
		opzioniFilePrincipaleForm.setWidth100();
		opzioniFilePrincipaleForm.setPadding(5);
		opzioniFilePrincipaleForm.setNumCols(14);
		opzioniFilePrincipaleForm.setColWidths(1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, "*", "*");
		opzioniFilePrincipaleForm.setWrapItemTitles(false);
		opzioniFilePrincipaleForm.setTabSet(tabSet);
		opzioniFilePrincipaleForm.setTabID("HEADER");
		
		flgFilePrincipaleUgualeXTutteRegItem = new RadioGroupItem("flgFilePrincipaleUgualeXTutteReg", "File principale uguale per tutte le registrazioni");
		flgFilePrincipaleUgualeXTutteRegItem.setStartRow(true);
		flgFilePrincipaleUgualeXTutteRegItem.setValueMap(_FLG_SI, _FLG_NO);		
		flgFilePrincipaleUgualeXTutteRegItem.setDefaultValue(_FLG_SI);
		flgFilePrincipaleUgualeXTutteRegItem.setVertical(false);
		flgFilePrincipaleUgualeXTutteRegItem.setWrap(false);
		flgFilePrincipaleUgualeXTutteRegItem.setShowDisabled(false);
		flgFilePrincipaleUgualeXTutteRegItem.setRequired(true);
		flgFilePrincipaleUgualeXTutteRegItem.setShowIfCondition(new FormItemIfFunction() {

			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				if(flgFilePrincipaleUgualeXTutteRegItem.getValue() != null && _FLG_SI.equals(flgFilePrincipaleUgualeXTutteRegItem.getValue())) {
					filePrimarioForm.show();
				} else {
					filePrimarioForm.hide();
				}
				return true;
			}
		});
		flgFilePrincipaleUgualeXTutteRegItem.addChangedHandler(new ChangedHandler() {
			
			@Override
			public void onChanged(ChangedEvent event) {
				opzioniFilePrincipaleForm.markForRedraw();				
			}
		});
		
		percorsoFilePrimariItem = new TextItem("percorsoFilePrimari", "Percorso da cui recuperare i file primari");
		percorsoFilePrimariItem.setWidth(500);
		percorsoFilePrimariItem.setAttribute("obbligatorio", true);
		percorsoFilePrimariItem.setValidators(new RequiredIfValidator(new RequiredIfFunction() {
			
			@Override
			public boolean execute(FormItem formItem, Object value) {
				if(flgFilePrincipaleUgualeXTutteRegItem.getValue() != null && _FLG_NO.equals(flgFilePrincipaleUgualeXTutteRegItem.getValue())) {
					return true;
				}
				return false;
			}
		}));
		percorsoFilePrimariItem.setShowIfCondition(new FormItemIfFunction() {

			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return flgFilePrincipaleUgualeXTutteRegItem.getValue() != null && _FLG_NO.equals(flgFilePrincipaleUgualeXTutteRegItem.getValue());
			}
		});
		
		verificaFilePrimariInXlsButton = new ImgButtonItem("verificaFilePrimariInXls", "buttons/verificaDati.png", "Verifica file primari in xls");
		verificaFilePrimariInXlsButton.setAlwaysEnabled(true);
		verificaFilePrimariInXlsButton.setColSpan(1);
		verificaFilePrimariInXlsButton.setIconWidth(16);
		verificaFilePrimariInXlsButton.setIconHeight(16);
		verificaFilePrimariInXlsButton.setIconVAlign(VerticalAlignment.BOTTOM);
		verificaFilePrimariInXlsButton.setAlign(Alignment.LEFT);
		verificaFilePrimariInXlsButton.setWidth(16);	
		verificaFilePrimariInXlsButton.setShowIfCondition(new FormItemIfFunction() {

			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return flgFilePrincipaleUgualeXTutteRegItem.getValue() != null && _FLG_NO.equals(flgFilePrincipaleUgualeXTutteRegItem.getValue());
			}
		});
		verificaFilePrimariInXlsButton.addIconClickHandler(new IconClickHandler() {
			
			@Override
			public void onIconClick(IconClickEvent event) {
				clickVerificaFilePrimariInXls();
			}
		});		
		
		flgApponiTimbroFilePrimariNonFirmatiItem = new CheckboxItem("flgApponiTimbroFilePrimariNonFirmati", "apponi timbro sui file primari non firmati");
		flgApponiTimbroFilePrimariNonFirmatiItem.setDefaultValue(false);
		flgApponiTimbroFilePrimariNonFirmatiItem.setColSpan(1);
		flgApponiTimbroFilePrimariNonFirmatiItem.setWidth("*");
		flgApponiTimbroFilePrimariNonFirmatiItem.setShowIfCondition(new FormItemIfFunction() {

			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return flgFilePrincipaleUgualeXTutteRegItem.getValue() != null && _FLG_NO.equals(flgFilePrincipaleUgualeXTutteRegItem.getValue());
			}
		});
		
		opzioniFilePrincipaleForm.setFields(flgFilePrincipaleUgualeXTutteRegItem, percorsoFilePrimariItem, verificaFilePrimariInXlsButton, flgApponiTimbroFilePrimariNonFirmatiItem);	
	}
	
	/**
	 * Metodo che implementa l'azione del bottone "Verifica file primari in xls"
	 * 
	 */
	public void clickVerificaFilePrimariInXls() {	
		final GWTRestDataSource lRegistrazioneMultiplaUscitaDatasource = new GWTRestDataSource("RegistrazioneMultiplaUscitaDatasource");
		lRegistrazioneMultiplaUscitaDatasource.performCustomOperation("validazioneFilePrimari", getRecordToSave(null), new DSCallback() {

			@Override
			public void execute(DSResponse response, Object data, DSRequest request) {
				if(response.getStatus() == DSResponse.STATUS_SUCCESS) {
					Record lRecordValidator = response.getData()[0];
					if(lRecordValidator.getAttributeAsBoolean("esitoValidazione")) {
						Layout.addMessage(new MessageBean("Verifica presenza file primari superata con successo", "", MessageType.INFO));
					} else {
						Map<String, String> errorMessages = lRecordValidator.getAttributeAsMap("errorMessages");
						if(errorMessages != null && errorMessages.size() > 0) {
							visualizzaErroriValidazioneFile("Verifica presenza file primari", errorMessages, null);
						} else {
							Layout.addMessage(new MessageBean("Verifica presenza file primari non superata", "", MessageType.ERROR));							
						}
					}
				}
			}
		});
	}
		
	/**
	 * Metodo che crea la sezione "Allegati diversi per registrazione"
	 * 
	 */	
	protected void createDetailSectionAllegatiDiversiXReg() {
		
		createAllegatiDiversiXRegForm();
		
		detailSectionAllegatiDiversiXReg = new ProtocollazioneDetailSection("Allegati diversi per registrazione", true, true, false, allegatiDiversiXRegForm);
	}
	
	/**
	 * Metodo che crea il form della sezione "Allegati diversi per registrazione"
	 * 
	 */	
	protected void createAllegatiDiversiXRegForm() {
				
		allegatiDiversiXRegForm = new DynamicForm();
		allegatiDiversiXRegForm.setValuesManager(vm);
		allegatiDiversiXRegForm.setWidth100();
		allegatiDiversiXRegForm.setPadding(5);
		allegatiDiversiXRegForm.setWrapItemTitles(false);
		allegatiDiversiXRegForm.setNumCols(18);
		allegatiDiversiXRegForm.setColWidths(1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, "*", "*");
		allegatiDiversiXRegForm.setTabSet(tabSet);
		allegatiDiversiXRegForm.setTabID("HEADER");	
		
		percorsoFileAllegatiItem = new TextItem("percorsoFileAllegati", "Percorso principale da cui recuperare gli allegati");
		percorsoFileAllegatiItem.setWidth(500);				
		percorsoFileAllegatiItem.setValidators(new RequiredIfValidator(new RequiredIfFunction() {
			
			@Override
			public boolean execute(FormItem formItem, Object value) {
				return hasDestinatariXlsWithAllegati();
			}
		}));
		percorsoFileAllegatiItem.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				if(hasDestinatariXlsWithAllegati()) {
					percorsoFileAllegatiItem.setAttribute("obbligatorio", true);
					percorsoFileAllegatiItem.setTitle(FrontendUtil.getRequiredFormItemTitle("Percorso principale da cui recuperare gli allegati"));
					detailSectionAllegatiDiversiXReg.setRequired(true);
				} else {
					percorsoFileAllegatiItem.setAttribute("obbligatorio", false);
					percorsoFileAllegatiItem.setTitle("Percorso principale da cui recuperare gli allegati");
					detailSectionAllegatiDiversiXReg.setRequired(false);
				}											
				return true;
			}
		});

		verificaFileAllegatiInXlsButton = new ImgButtonItem("verificaFileAllegatiInXls", "buttons/verificaDati.png", "Verifica file allegati in xls");
		verificaFileAllegatiInXlsButton.setAlwaysEnabled(true);
		verificaFileAllegatiInXlsButton.setColSpan(1);
		verificaFileAllegatiInXlsButton.setIconWidth(16);
		verificaFileAllegatiInXlsButton.setIconHeight(16);
		verificaFileAllegatiInXlsButton.setIconVAlign(VerticalAlignment.BOTTOM);
		verificaFileAllegatiInXlsButton.setAlign(Alignment.LEFT);
		verificaFileAllegatiInXlsButton.setWidth(16);	
		verificaFileAllegatiInXlsButton.addIconClickHandler(new IconClickHandler() {
			
			@Override
			public void onIconClick(IconClickEvent event) {
				clickVerificaFileAllegatiInXls();
			}
		});		
		
		flgApponiTimbroFileAllegatiNonFirmatiItem = new CheckboxItem("flgApponiTimbroFileAllegatiNonFirmati", "apponi timbro sui file allegati non firmati");
		flgApponiTimbroFileAllegatiNonFirmatiItem.setDefaultValue(false);
		flgApponiTimbroFileAllegatiNonFirmatiItem.setColSpan(1);
		flgApponiTimbroFileAllegatiNonFirmatiItem.setWidth("*");
		
		allegatiDiversiXRegForm.setFields(percorsoFileAllegatiItem, verificaFileAllegatiInXlsButton, flgApponiTimbroFileAllegatiNonFirmatiItem);	
	}
	
	/**
	 * Metodo che implementa l'azione del bottone "Verifica file allegati in xls"
	 * 
	 */
	public void clickVerificaFileAllegatiInXls() {	
		final GWTRestDataSource lRegistrazioneMultiplaUscitaDatasource = new GWTRestDataSource("RegistrazioneMultiplaUscitaDatasource");
		lRegistrazioneMultiplaUscitaDatasource.performCustomOperation("validazioneFileAllegati", getRecordToSave(null), new DSCallback() {

			@Override
			public void execute(DSResponse response, Object data, DSRequest request) {
				if(response.getStatus() == DSResponse.STATUS_SUCCESS) {
					Record lRecordValidator = response.getData()[0];
					if(lRecordValidator.getAttributeAsBoolean("esitoValidazione")) {
						Layout.addMessage(new MessageBean("Verifica presenza file allegati superata con successo", "", MessageType.INFO));
					} else {
						Map<String, String> errorMessages = lRecordValidator.getAttributeAsMap("errorMessages");
						if(errorMessages != null && errorMessages.size() > 0) {
							visualizzaErroriValidazioneFile("Verifica presenza file allegati", errorMessages, null);
						} else {
							Layout.addMessage(new MessageBean("Verifica presenza file allegati non superata", "", MessageType.ERROR));
						}
					}
				}
			}
		});
	}
		
	/**
	 * Metodo che restituisce il titolo della sezione "Allegati comuni a tutte le registrazioni"
	 * 
	 */
	@Override
	public String getTitleDetailSectionAllegati() {		
		return "Allegati comuni a tutte le registrazioni";
	}		
	
	/**
	 * Metodo che indica se mostrare già aperta la sezione "Allegati comuni a tutte le registrazioni"
	 * 
	 */
	@Override
	public boolean showOpenDetailSectionAllegati() {
		return true;
	}
	
	/**
	 * Metodo per costruire la barra inferiore contenente i bottoni delle azioni per la registrazione multipla in uscita
	 * 
	 */
	protected void createDetailToolStripMultipla() {

		detailToolStripMultipla = new ToolStrip();
		detailToolStripMultipla.setName("bottoni");
		detailToolStripMultipla.setWidth100();
		detailToolStripMultipla.setHeight(30);
		detailToolStripMultipla.setStyleName(it.eng.utility.Styles.detailToolStrip);
		detailToolStripMultipla.addFill();
		if (AurigaLayout.getIsAttivaAccessibilita()) {
			detailToolStripMultipla.setCanFocus(true);		
		} 
		
		createRegistraButton();
		
		detailToolStripMultipla.addButton(registraButton);
	}
	
	/**
	 * Alla pressione del tasto "Registra" il sistema effettua preliminarmente:
	 * 1. le verifiche di obbligatorietà previste per la registrazione in uscita;
	 * 2. la verifica che sia compilata la "Casella mittente" se per uno o più destinatari dell'xls o dei destinatari comuni è indicato l'invio e-mail; 
	 * 3. la verifica di presenza dei file primari e allegati indicati nel file xls dei destinatari, e se rileva errori mostra griglia con gli errori rilevati e le registrazioni non vengono effettuate. 
	 * Man mano che i file indicati nell'xls dei destinatari vengono trovati il sistema li copia in una cartella temporanea sul server in modo che il client che ha richiesto la registrazione multipla possa rimuoverli dal percorso in cui li ha specificati dal momento in cui ottiene il "N° di operazione di registrazione multipla": tale numero – un progressivo a rinnovo annuale - viene restituito quando il controllo di presenza dei file indicati nell'xls viene superato con successo, dopo di che il controllo torna all'utente che può procedere con altre operazioni. L'operazione di registrazione multipla procede in back-ground e il suo esito si monitora attraverso la funzione "Monitoraggio registrazioni multiple in uscita" accessibile da altra nuova voce di menu'. 
	 * In automatico vengono effettuate sia le registrazioni, salvo errori, che le eventuali trasmissioni e-mail agli indirizzi e-mail specificati nell'xls destinatari (ed eventualmente nella sezione "destinatari comuni a tutte le registrazioni").
	 */
	
	/**
	 * Metodo per il recupero dei valori da salvare
	 * 
	 */
	@Override
	public Record getRecordToSave(String motivoVarDatiReg) {
		
		final Record lRecordToSave = super.getRecordToSave(motivoVarDatiReg);;
		lRecordToSave.setAttribute("flgTipoProv", "U");
		
		addFormValues(lRecordToSave, nuovaRegistrazioneMultiplaForm);
		if(showDetailSectionDestinatari()) {
			addFormValues(lRecordToSave, destinatariDiversiXRegForm);
		}
		addFormValues(lRecordToSave, casellaMittenteForm);
		addFormValues(lRecordToSave, opzioniFilePrincipaleForm);
		addFormValues(lRecordToSave, allegatiDiversiXRegForm);
		
		return lRecordToSave;
	}
	
	/**
	 * Metodo per costruire il bottone "Registra"
	 * 
	 */
	protected void createRegistraButton() {

		registraButton = new DetailToolStripButton("Registra", "buttons/save.png");
		registraButton.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {				
				Scheduler.get().scheduleDeferred(new ScheduledCommand() {

					@Override
					public void execute() {						
						if (abilRegistraButton) {
							/*
							 * 
							 * Imposto il flag per disabilitare il pulsante per il salvataggio per evitare il problema che, in alcune situazioni, può essere
							 * eseguito un doppio salvataggio/registrazione cliccando rapidamente.
							 */
							abilRegistraButton = false;
							/*
							 * Riabilitiamo il pulsante dopo che è trascorso un certo tempo. 
							 * In questo modo si evita il problema per cui, in caso di errori js o
							 * imprevisti, il pulsante rimanga disabilitato obbligando l'utente a chiudere la finestra
							 * per procedere.
							 */						
							new Timer() {
								public void run() {
									// Riabilito il pulsante dopo che è passato il tempo predefinito.
									abilRegistraButton = true;
								}
							}.schedule(5000); // Tempo dopo il quale si avvia l'esecuzione					
							clickRegistra();
						}	
					}
				});
			}
		});
	}
	
	final public void validate(final BooleanCallback callback) {
		if(validate()) {
			final GWTRestDataSource lRegistrazioneMultiplaUscitaDatasource = new GWTRestDataSource("RegistrazioneMultiplaUscitaDatasource");
			lRegistrazioneMultiplaUscitaDatasource.performCustomOperation("validazioneFile", getRecordToSave(null), new DSCallback() {
	
				@Override
				public void execute(DSResponse response, Object data, DSRequest request) {
					if(response.getStatus() == DSResponse.STATUS_SUCCESS) {
						Record lRecordValidator = response.getData()[0];
						if(lRecordValidator.getAttributeAsBoolean("esitoValidazione")) {
							if(callback != null) {
								callback.execute(true);
							}
						} else {
							Map<String, String> errorMessages = lRecordValidator.getAttributeAsMap("errorMessages");
							if(errorMessages != null && errorMessages.size() > 0) {
								visualizzaErroriValidazioneFile("Verifica presenza file", errorMessages, callback);
							} else {
								if(callback != null) {
									callback.execute(false);
								}
							}
						}
					}
				}
			});
//			if(callback != null) {
//				callback.execute(false);
//			}
		} else if(callback != null) {
			callback.execute(false);
		}
	}

	/**
	 * Metodo che implementa l'azione del bottone "Registra"
	 * 
	 */
	public void clickRegistra() {		
		validate(new BooleanCallback() {

			@Override
			public void execute(Boolean valid) {
				if (valid) {
					Record lRecordToSave = getRecordToSave(null);
					manageClickRegistra(lRecordToSave);		
				} else {
					abilRegistraButton = true; //Riabilito il pulsante di salvataggio
					Layout.addMessage(new MessageBean(I18NUtil.getMessages().validateError_message(), "", MessageType.ERROR));
				}
			}			
		});		
	}
		
	public void manageClickRegistra(Record lRecordToSave) {
		final GWTRestDataSource lRegistrazioneMultiplaUscitaDatasource = new GWTRestDataSource("RegistrazioneMultiplaUscitaDatasource");
		lRegistrazioneMultiplaUscitaDatasource.addParam("isAttivaGestioneErroriFile", "true");
		lRegistrazioneMultiplaUscitaDatasource.addParam("skipSceltaApponiTimbro", AurigaLayout.getImpostazioneTimbro("skipSceltaApponiTimbro"));
		lRegistrazioneMultiplaUscitaDatasource.addParam("rotazioneTimbroPref", AurigaLayout.getImpostazioneTimbro("rotazioneTimbro"));
		lRegistrazioneMultiplaUscitaDatasource.addParam("posizioneTimbroPref", AurigaLayout.getImpostazioneTimbro("posizioneTimbro"));
		lRegistrazioneMultiplaUscitaDatasource.addParam("tipoPaginaTimbroPref", AurigaLayout.getImpostazioneTimbro("tipoPagina"));
		Layout.showWaitPopup("Registrazione in corso: potrebbe richiedere qualche secondo. Attendere...");
		lRegistrazioneMultiplaUscitaDatasource.addData(lRecordToSave, new DSCallback() {

			@Override
			public void execute(DSResponse response, Object rawData, DSRequest request) {
				abilRegistraButton = true;
				Layout.hideWaitPopup();	
				if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
					manageErroriFile(response.getData()[0], new ServiceCallback<Record>() {

						@Override
						public void execute(Record object) {
							tabSet.selectTab(0);
							editNewRecord();
						}
					});
				}
			}
		});
	}
	
	/**************************************************************************
	 * METODI RELATIVI ALLE MODALITA' DI APERTURA DELLA MASCHERA DI DETTAGLIO *
	 **************************************************************************/

	/**
	 * Metodo di nuovo dettaglio
	 * 
	 */
	@Override
	public void nuovoDettaglio(CustomLayout layout) {
		
		manageResponse(layout, null, null);
		newMode();
	}

	/**
	 * Metodo di nuovo dettaglio con valori preimpostati
	 * 
	 */
	@Override
	public void nuovoDettaglio(CustomLayout layout, Map initialValues) {
		
		manageResponse(layout, null, initialValues);
		newMode();
	}

	/**
	 * Metodo che carica il dettaglio di un documento a maschera
	 * 
	 */
	@Override
	public void caricaDettaglio(CustomLayout layout, Record lRecord) {
		
		manageResponse(layout, lRecord, null);		
	}
	
	/**
	 * Metodo che contiene le logiche da applicare in fase di
	 * inizalizzazione/caricamento dei dati a maschera
	 * 
	 */
	@Override
	protected void manageResponse(CustomLayout layout, Record lRecord, Map initialValues) {

		this.layout = layout;
		if (lRecord != null) {
			editRecord(lRecord);
		} else {
			if (initialValues != null) {
				editNewRecord(initialValues);
			} else {
				editNewRecord();
			}
		}
		if(contenutiForm != null) {
			contenutiForm.redraw();
		}
		setCanEdit(false);		
		if(mainToolStrip != null) {
			mainToolStrip.hide();
		}
		if(layout != null) {
			if(detailToolStrip != null) {
				detailToolStrip.hide();
			}			
		}		
		if (detailSectionTipoDocumento != null) {
			detailSectionTipoDocumento.setVisible(tipoDocumento != null && !"".equals(tipoDocumento));
		}
	}
	
	/**
	 * Metodo che mostra l'anteprima di un file
	 * 
	 */
	public void preview(final Record recordPreview) {
		previewWithCallback(recordPreview, null);
	}
	
	public void previewWithCallback(final Record recordPreview, final ServiceCallback<Record> closeCallback) {
		if (recordPreview.getAttribute("nomeFile") != null && recordPreview.getAttribute("nomeFile").endsWith(".pdf")) {

			new PreviewWindow(recordPreview.getAttribute("uri"), false, new InfoFileRecord(recordPreview.getAttributeAsRecord("infoFile")), "FileToExtractBean",	recordPreview.getAttribute("nomeFile")) {
			
				@Override
				public void manageCloseClick() {
					super.manageCloseClick();
					if(closeCallback != null) {
						closeCallback.execute(recordPreview);
					}
				};
				
				@Override
				public boolean isModal() {
					return isEnablePreviewModal();
				}
				
			};
		} else {
			Record lRecord = new Record();
			lRecord.setAttribute("displayFilename", recordPreview.getAttribute("nomeFile"));
			lRecord.setAttribute("uri", recordPreview.getAttribute("uri"));
			lRecord.setAttribute("sbustato", "false");
			lRecord.setAttribute("remoteUri", "false");
			DownloadFile.downloadFromRecord(lRecord, "FileToExtractBean");
			if(closeCallback != null) {
				closeCallback.execute(recordPreview);
			}
		}
	}
	
	/**
	 * Metodo per la visualizzazione degli errori in validazione file
	 * 
	 */
	private void visualizzaErroriValidazioneFile(String nomeAzione, Map<String, String> errorMessages, final BooleanCallback callback) {
		if (errorMessages != null && errorMessages.size() > 0) {
			if (errorMessages.size() == 1) {
				Layout.addMessage(new MessageBean(errorMessages.values().iterator().next(), "", MessageType.ERROR));
				if(callback != null) {
					callback.execute(false);
				}
			} else if (errorMessages.size() > 1) {	
				RecordList listaErrori = new RecordList();
				for (String key : errorMessages.keySet()) {
					String value = errorMessages.get(key);
					if (value != null && !"".equals(value)) {
						Record recordErrore = new Record();
						recordErrore.setAttribute("idError", key);
						recordErrore.setAttribute("descrizione", value);
						listaErrori.add(recordErrore);
					}
				}
				/*
				 * Visualizzo la tabella contenente gli errori rilevati
				 */
				ErroreMassivoPopup erroreMassivoPopup = new ErroreMassivoPopup(getName(), "Riga", listaErrori, listaErrori.getLength(), LARG_POPUP_ERR_MASS, ALT_POPUP_ERR_MASS, "Righe in errore dell'excel importato dei destinatari", new ServiceCallback<Record>() {
	
					@Override
					public void execute(Record response) {
						if(callback != null) {
							callback.execute(false);
						}
					}
				});
				erroreMassivoPopup.show();
			}
		}
	}

}
