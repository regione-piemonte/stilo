/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.HashSet;

import com.smartgwt.client.data.DSCallback;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.data.RecordList;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.FieldType;
import com.smartgwt.client.types.Overflow;
import com.smartgwt.client.widgets.Button;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.ValuesManager;
import com.smartgwt.client.widgets.form.fields.HiddenItem;
import com.smartgwt.client.widgets.form.fields.SpacerItem;
import com.smartgwt.client.widgets.layout.HStack;
import com.smartgwt.client.widgets.layout.VLayout;

import it.eng.auriga.ui.module.layout.client.AurigaLayout;
import it.eng.auriga.ui.module.layout.client.i18n.I18NUtil;
import it.eng.utility.ui.module.core.client.datasource.GWTRestDataSource;
import it.eng.utility.ui.module.core.shared.message.MessageBean;
import it.eng.utility.ui.module.core.shared.message.MessageType;
import it.eng.utility.ui.module.layout.client.Layout;
import it.eng.utility.ui.module.layout.client.StringSplitterClient;
import it.eng.utility.ui.module.layout.client.common.file.InfoFileRecord;
import it.eng.utility.ui.module.layout.client.portal.ModalWindow;

public abstract class GestSepAllegatiUDPopup extends ModalWindow {
	
	protected GestSepAllegatiUDPopup _window;
	protected Record detailRecord;
	
	protected ValuesManager vm;
	protected DynamicForm  hiddenForm;
	protected DynamicForm gestSepAllegatiUDForm;
	// protected DetailSection gestisciPuntiProtocolloDetailSection;
	
	public DynamicForm getForm() {
		return gestSepAllegatiUDForm;
	}
	 
	protected Button salvaButton;
	protected Button annullaButton;
	protected Button chiudiButton;
	
	protected HiddenItem idUdHiddenItem;
	protected AllegatiGridItem listaAllegatiItem;
	
	public GestSepAllegatiUDPopup(String title){
		
		super("gest_sep_allegati_idud_popup", true);
		
		_window = this;
	
		setTitle(title);
		
		setAutoCenter(true);
		
		setHeight(400);
		setWidth(1000);

		settingsMenu.removeItem(separatorMenuItem);
		settingsMenu.removeItem(autoSearchMenuItem);
		
		SpacerItem spacerItem = new SpacerItem();
		spacerItem.setStartRow(true);
		spacerItem.setColSpan(1);
		 		
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
		
		this.vm = new ValuesManager();
		
		createHiddenForm();
		createGestSepAllegatiUDForm();
		
		layout.addMember(hiddenForm);
		layout.addMember(gestSepAllegatiUDForm);
				
		salvaButton = new Button("Salva");   
		salvaButton.setIcon("ok.png");
		salvaButton.setIconSize(16); 
		salvaButton.setAutoFit(false);
		salvaButton.addClickHandler(new com.smartgwt.client.widgets.events.ClickHandler() {			
			@Override
			public void onClick(com.smartgwt.client.widgets.events.ClickEvent event) {
				
				onClickOkButton(new Record(vm.getValues()));
			}
		});
		
		annullaButton = new Button("Annulla");   
		annullaButton.setIcon("annulla.png");
		annullaButton.setIconSize(16); 
		annullaButton.setAutoFit(false);
		annullaButton.addClickHandler(new com.smartgwt.client.widgets.events.ClickHandler() {			
			@Override
			public void onClick(com.smartgwt.client.widgets.events.ClickEvent event) {
				_window.markForDestroy();	
			}
		});
		
		chiudiButton = new Button("Chiudi");   
		chiudiButton.setIcon("annulla.png");
		chiudiButton.setIconSize(16); 
		chiudiButton.setAutoFit(false);
		chiudiButton.addClickHandler(new com.smartgwt.client.widgets.events.ClickHandler() {			
			@Override
			public void onClick(com.smartgwt.client.widgets.events.ClickEvent event) {
				_window.markForDestroy();	
			}
		});
		
		HStack _buttons = new HStack(5);
		_buttons.setHeight(30);
		_buttons.setAlign(Alignment.CENTER);
		_buttons.setPadding(5);
        _buttons.addMember(salvaButton);
		_buttons.addMember(annullaButton);	
		_buttons.addMember(chiudiButton);
		
		portletLayout.addMember(layout);		
		portletLayout.addMember(_buttons);
						
		setBody(portletLayout);
				
		setIcon("archivio/assegna.png");

	}
	
	protected void createHiddenForm() {
		
		hiddenForm = new DynamicForm();
		hiddenForm.setValuesManager(vm);
		hiddenForm.setOverflow(Overflow.HIDDEN);
//		hiddenForm.setHeight(0);
		
		idUdHiddenItem = new HiddenItem("idUd");
		
		hiddenForm.setFields(
			idUdHiddenItem
		);
	}

	protected void createGestSepAllegatiUDForm(){
		
		gestSepAllegatiUDForm = new DynamicForm();
		gestSepAllegatiUDForm.setValuesManager(vm);
		gestSepAllegatiUDForm.setKeepInParentRect(true);
		gestSepAllegatiUDForm.setNumCols(5);
		gestSepAllegatiUDForm.setColWidths(120,"*","*","*","*");		
		gestSepAllegatiUDForm.setCellPadding(5);		
		gestSepAllegatiUDForm.setWrapItemTitles(false);
		gestSepAllegatiUDForm.setWidth("100%");
		gestSepAllegatiUDForm.setHeight100();
		gestSepAllegatiUDForm.setPadding(5);
		
		listaAllegatiItem = new AllegatiGridItem("listaAllegati", "listaAllegatiAtto") {
			
			@Override
			public GWTRestDataSource getTipiFileAllegatoDataSource() {
				String idProcess = getIdProcess();
				String idProcessType = getIdProcessType();
				if ((idProcess != null && !"".equals(idProcess)) || (idProcessType != null && !"".equals(idProcessType))) {
					GWTRestDataSource lLoadComboTipoDocInProcessDataSource = new GWTRestDataSource("LoadComboTipoDocInProcessDataSource", "idTipoDoc", FieldType.TEXT, true);
					lLoadComboTipoDocInProcessDataSource.addParam("idProcess", idProcess);
					lLoadComboTipoDocInProcessDataSource.addParam("idProcessType", idProcessType);
					return lLoadComboTipoDocInProcessDataSource;
				} else {				
					GWTRestDataSource lLoadComboTipoDocInProcessDataSource = new GWTRestDataSource("LoadComboTipoFileAllegatoDataSource", "idTipoFileAllegato", FieldType.TEXT, true);
					lLoadComboTipoDocInProcessDataSource.addParam("idDocTypePrimario", detailRecord != null ? detailRecord.getAttribute("tipoDocumento") : null);					
					return lLoadComboTipoDocInProcessDataSource;
				}
			}
			
			@Override
			public String getIdProcess() {
				return detailRecord != null ? detailRecord.getAttribute("idProcess") : null;
			}

			@Override
			public String getIdProcessType() {
				return null;
			}

			@Override
			public String getIdTaskCorrenteAllegati() {
				return null;
			}

			@Override
			public HashSet<String> getTipiModelliAttiAllegati() {
				return null;
			}
			
			@Override
			public Record getDetailRecord() {
				return detailRecord;			
			}
							
			@Override
			public boolean isObbligatorioFile() {
				return true;
			}

			@Override
			public boolean isFormatoAmmessoParteIntegrante(InfoFileRecord info) {
				String mimetype = info != null ? info.getMimetype() : "";
				String mimetypeAmmessiAllegatiParteIntegranteAtti = AurigaLayout.getParametroDB("MIMETYPE_AMM_ALL_PI_ATTO");
				if(mimetypeAmmessiAllegatiParteIntegranteAtti != null && !"".equals(mimetypeAmmessiAllegatiParteIntegranteAtti)) {
					String modalitaControlloMimetypeAllegatiParteIntegranteAtti = AurigaLayout.getParametroDB("MOD_CTRL_MIMETYPE_ALL_PI_ATTO");
					if(modalitaControlloMimetypeAllegatiParteIntegranteAtti != null && "sempre".equalsIgnoreCase(modalitaControlloMimetypeAllegatiParteIntegranteAtti)) {				
						StringSplitterClient st = new StringSplitterClient(mimetypeAmmessiAllegatiParteIntegranteAtti, ";");
						for(int i = 0; i < st.getTokens().length; i++) {
							if(mimetype.equals(st.getTokens()[i])) {
								return true;
							}
						}
						return false;			
					}
				}
				return true;
			}
			
			@Override
			public String getRifiutoAllegatiConFirmeNonValide() {
				return AurigaLayout.getParametroDB("RIFIUTO_ALLEGATI_ATTI_CON_FIRME_NON_VALIDE");
			}
			
			@Override
			public boolean isDisattivaUnioneAllegatiFirmati() {
				return AurigaLayout.getParametroDBAsBoolean("DISATTIVA_UNIONE_ALLEGATI_ATTI_FIRMATI");
			}
			
			@Override
			public boolean isPubblicazioneSeparataPdfProtetti() {
				// Non serve più controllare se un pdf è protetto dato che con la variabile unethicalreading di itext ora si riesce a manipolarli
				return false;
			}
			
			@Override
			public boolean isAttivaVociBarcode() {
				String idUd = (idUdHiddenItem.getValue() != null) ? String.valueOf(idUdHiddenItem.getValue()) : null;
				return idUd != null && !"".equals(idUd);
			}								
			
			@Override
			public boolean getShowGeneraDaModello() {
				return false; // altrimenti non so come implementare i metodi caricaModelloInAllegati e getRecordCaricaModelloInAllegati
			}
			
			@Override
			public boolean getFlgAllegAttoParteIntDefault() {
				return AurigaLayout.getParametroDBAsBoolean("FLG_ALLEG_ATTO_PARTE_INT_DEFAULT");
			}
			
			@Override
			public boolean getFlgAllegAttoPubblSepDefault() {
				return AurigaLayout.getParametroDBAsBoolean("FLG_ALLEG_ATTO_PUBBL_SEPARATA_DEFAULT");
			}
			
			@Override
			public Integer getWidthDescrizioneFileAllegato() {
				return 250;
			}
			
			@Override
			public Integer getWidthNomeFileAllegato() {
				return 250;
			}
			
			@Override
			public boolean getShowVersioneOmissis() {
				return true;
			}
			
			@Override
			public boolean getShowFlgProvEsterna() {
				return AurigaLayout.getParametroDBAsBoolean("ATTIVA_GEST_PROV_ALLEG_ATTI");
			}
			
			@Override
			public boolean getShowFlgParere() {
				return true;
			}
			
			@Override
			public boolean getSortByFlgParteDispositivo() {
				return !AurigaLayout.getParametroDBAsBoolean("DISATTIVA_ORD_PARTE_INT_IN_GRID_ALLEG_ATTI");
			}
			
			@Override
			public boolean getShowFlgParteDispositivo() {
				return true;
			}
			
			@Override
			public boolean getShowFlgNoPubblAllegato() {
				return AurigaLayout.getParametroDBAsBoolean("ATTIVA_ESCL_PUBBL_FILE_IN_ATTI");
			}
			
			@Override
			public boolean getShowFlgPubblicaSeparato() {			
				return AurigaLayout.getParametroDBAsBoolean("ATTIVA_PUBBL_SEPARATA_FILE_IN_ATTI");
			}
			
			@Override
			public boolean getShowImportaFileDaDocumenti() {
				return true;
			}
			
			@Override
			public boolean getShowCollegaDocumentiImportati() {
				return false;
			}		
			
			@Override
			public long getDimAlertAllegato() {
				return AurigaLayout.getParametroDB("DIM_ALERT_ALLEGATO_ATTO") != null && !"".equals(AurigaLayout.getParametroDB("DIM_ALERT_ALLEGATO_ATTO")) ? Long.parseLong(AurigaLayout.getParametroDB("DIM_ALERT_ALLEGATO_ATTO")) : -1;
			}		
			
			@Override
			public long getDimMaxAllegatoXPubbl() {
				return AurigaLayout.getParametroDB("MAX_DIM_ALLEG_ATTO_X_PUBBL") != null && !"".equals(AurigaLayout.getParametroDB("MAX_DIM_ALLEG_ATTO_X_PUBBL")) ? Long.parseLong(AurigaLayout.getParametroDB("MAX_DIM_ALLEG_ATTO_X_PUBBL")) : -1;
			}	

			@Override
			public boolean isGrigliaEditabile() {
				return true;
			}
			
			@Override
			public boolean getShowTimbraBarcodeMenuOmissis() {
				return true;
			}
			
			@Override
			public String getIdUd() {
				return idUdHiddenItem.getValue() != null ? String.valueOf(idUdHiddenItem.getValue()) : null;
			}
			
			@Override
			public boolean importConCtrlAllegatiXImportUnitaDoc() {
				return true;
			}

			@Override
			public void onRecordSelected(Record record) {
				// TODO Auto-generated method stub
				
			}
		};
		listaAllegatiItem.setShowTitle(false);
		listaAllegatiItem.setColSpan(20);
		listaAllegatiItem.setHeight("95%");
				
		gestSepAllegatiUDForm.setItems(listaAllegatiItem);
	}
	
	public void onClickOkButton(Record formRecord) {
		if(listaAllegatiItem.validate()) {
			String idUd = hiddenForm.getValueAsString("idUd");
			final RecordList listaAllegati = gestSepAllegatiUDForm.getValuesAsRecord().getAttributeAsRecordList("listaAllegati");
			Record lRecord = new Record(detailRecord.toMap());
			lRecord.setAttribute("idUd", idUd);
			lRecord.setAttribute("listaAllegati", listaAllegati);
			Layout.showWaitPopup("Salvataggio in corso. Attendere...");
			GWTRestDataSource lProtocolloDataSource = new GWTRestDataSource("ProtocolloDataSource");
			lProtocolloDataSource.performCustomOperation("updateAllegatiDocumento", lRecord, new DSCallback() {
	
				@Override
				public void execute(DSResponse response, Object rawData, DSRequest request) {
					Layout.hideWaitPopup();
					_window.markForDestroy();
					if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
						Record recordToPass = new Record();
						recordToPass.setAttribute("listaAllegati", listaAllegati);
						onSaveCallback(recordToPass);
					}
				}
			}, new DSRequest());
		} else {
			Layout.addMessage(new MessageBean(I18NUtil.getMessages().validateError_message(), "", MessageType.ERROR));
		}
	}
	
	public void editRecord(Record detailRecord) {
		this.detailRecord = detailRecord;
		hiddenForm.editRecord(detailRecord);
		gestSepAllegatiUDForm.editRecord(detailRecord);
		markForRedraw();
	}
	
	public void setCanEdit(boolean canEdit) {
		if (canEdit) {
			salvaButton.setVisible(true);
			annullaButton.setVisible(true);
			chiudiButton.setVisible(false);
		} else {
			salvaButton.setVisible(false);
			annullaButton.setVisible(false);
			chiudiButton.setVisible(true);
		}
		listaAllegatiItem.setCanEdit(canEdit);
	}
	
	public abstract void onSaveCallback (Record record);
	
}
