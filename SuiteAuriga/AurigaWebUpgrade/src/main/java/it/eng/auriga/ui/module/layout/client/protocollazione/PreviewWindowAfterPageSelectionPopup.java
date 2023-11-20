/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.LinkedHashMap;

import com.smartgwt.client.data.DSCallback;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.Overflow;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.Button;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.ButtonItem;
import com.smartgwt.client.widgets.form.fields.RadioGroupItem;
import com.smartgwt.client.widgets.form.fields.SpacerItem;
import com.smartgwt.client.widgets.layout.HStack;
import com.smartgwt.client.widgets.layout.VLayout;

import it.eng.auriga.ui.module.layout.client.i18n.I18NUtil;
import it.eng.utility.ui.module.core.client.datasource.GWTRestService;
import it.eng.utility.ui.module.core.shared.message.MessageBean;
import it.eng.utility.ui.module.core.shared.message.MessageType;
import it.eng.utility.ui.module.layout.client.Layout;
import it.eng.utility.ui.module.layout.client.common.file.DownloadFile;
import it.eng.utility.ui.module.layout.client.common.items.StaticTextItem;
import it.eng.utility.ui.module.layout.client.portal.ModalWindow;

public class PreviewWindowAfterPageSelectionPopup extends ModalWindow {
	
	protected String uriFileOriginale;
	protected String nomeFileOriginale;
	protected String pageSelected;
	protected String jsonRitagli;
	protected Integer rotationDegree;
	protected boolean isReadOnlyMode;
	protected boolean enableOptionToSaveInOmissisForm;
	protected PreviewWindowPageSelectionCallback previewWindowPageSelectionCallback;
	
	
	protected PreviewWindowAfterPageSelectionPopup _window;
	protected DynamicForm azioneDaFareDynamicForm;
	 
	protected ButtonItem confermaButton;
	protected ButtonItem annullaButton;
	
	protected RadioGroupItem azioneDaFareItem;
	
	public PreviewWindowAfterPageSelectionPopup(String uriFileOriginale, String nomeFileOriginale, String pageSelected, boolean isReadOnlyMode, boolean enableOptionToSaveInOmissisForm, PreviewWindowPageSelectionCallback previewWindowPageSelectionCallback){
		this(uriFileOriginale, nomeFileOriginale, pageSelected, "",  isReadOnlyMode, enableOptionToSaveInOmissisForm, previewWindowPageSelectionCallback, "0");
	}

	public PreviewWindowAfterPageSelectionPopup(String uriFileOriginale, String nomeFileOriginale, String pageSelected, String jsonRitagli, boolean isReadOnlyMode, boolean enableOptionToSaveInOmissisForm, PreviewWindowPageSelectionCallback previewWindowPageSelectionCallback, String rotationDegree){
		
		super("gestisci_punto_protocollo", true);
		
		this.uriFileOriginale = uriFileOriginale;
		this.nomeFileOriginale = nomeFileOriginale;
		this.pageSelected = pageSelected;
		this.jsonRitagli = jsonRitagli;
		this.isReadOnlyMode = isReadOnlyMode;
		this.enableOptionToSaveInOmissisForm = enableOptionToSaveInOmissisForm;
		this.previewWindowPageSelectionCallback = previewWindowPageSelectionCallback;
		this.rotationDegree = new Integer(rotationDegree);
		
		_window = this;
	
		setTitle(I18NUtil.getMessages().previewWindowAfterPageSelectionPopup_title());
		
		setAutoCenter(true);
		
		setHeight(250);
		setWidth(550);

		settingsMenu.removeItem(separatorMenuItem);
		settingsMenu.removeItem(autoSearchMenuItem);
		
		SpacerItem spacerItem = new SpacerItem();
		spacerItem.setStartRow(true);
		spacerItem.setColSpan(1);
		
		azioneDaFareDynamicForm = new DynamicForm();													
		azioneDaFareDynamicForm.setKeepInParentRect(true);
		azioneDaFareDynamicForm.setNumCols(5);
		azioneDaFareDynamicForm.setColWidths(1, 1, 1, 1,"*");		
		azioneDaFareDynamicForm.setCellPadding(5);		
		azioneDaFareDynamicForm.setWrapItemTitles(false);
		azioneDaFareDynamicForm.setHeight("5");
		azioneDaFareDynamicForm.setPadding(5);

		StaticTextItem azioniDaFareTitle = new StaticTextItem();
		azioniDaFareTitle.setShowTitle(false);
		azioniDaFareTitle.setValue(I18NUtil.getMessages().previewWindowAfterPageSelectionPopup_azioneDaFare_title());
		azioniDaFareTitle.setStartRow(true);
		azioniDaFareTitle.setWrap(false);
		
		azioneDaFareItem = new RadioGroupItem();
		azioneDaFareItem.setStartRow(true);
		azioneDaFareItem.setName("azioneDaFare");
		azioneDaFareItem.setShowTitle(false);
		LinkedHashMap<String, String> valueMap = creaMappaValoriAzioniDaFare(isReadOnlyMode, enableOptionToSaveInOmissisForm);
		azioneDaFareItem.setValueMap(valueMap);
		if (!isReadOnlyMode && enableOptionToSaveInOmissisForm) {
			azioneDaFareItem.setDefaultValue(AzioniAfterPageSelectionEnum.SALVA_COME_VERS_OMISSIS.valore);		
		} else if (valueMap.entrySet() != null && valueMap.entrySet().iterator().hasNext()) {
			azioneDaFareItem.setDefaultValue(valueMap.entrySet().iterator().next().getKey());
		}
			
		azioneDaFareDynamicForm.setItems(azioniDaFareTitle, azioneDaFareItem);
			
		Button confermaButton = new Button("Ok");   
		confermaButton.setIcon("ok.png");
		confermaButton.setIconSize(16); 
		confermaButton.setAutoFit(false);
		confermaButton.addClickHandler(new com.smartgwt.client.widgets.events.ClickHandler() {			
			@Override
			public void onClick(com.smartgwt.client.widgets.events.ClickEvent event) {
				
				// Verifico se devo generare il file con omissis
				String azioneScelta = azioneDaFareItem.getValueAsString();
				if (AzioniAfterPageSelectionEnum.SALVA.valore().equalsIgnoreCase(azioneScelta) || AzioniAfterPageSelectionEnum.SALVA_COME_VERS_OMISSIS.valore().equalsIgnoreCase(azioneScelta) || AzioniAfterPageSelectionEnum.SCARICA.valore().equalsIgnoreCase(azioneScelta)) {
					creaFileConOmissis();
				} else {
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
		_buttons.setHeight(30);
		_buttons.setAlign(Alignment.CENTER);
		_buttons.setPadding(5);
        _buttons.addMember(confermaButton);
		// _buttons.addMember(annullaButton);	
		 		
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
		
		layout.addMember(azioneDaFareDynamicForm);
				
		portletLayout.addMember(layout);		
		portletLayout.addMember(_buttons);
						
		setBody(portletLayout);
				
		setIcon("archivio/assegna.png");

	}
	
	private void creaFileConOmissis() {
		Record record = new Record();
		record.setAttribute("uri", uriFileOriginale);
		record.setAttribute("fileName", nomeFileOriginale);
		record.setAttribute("pagesToRemove", pageSelected);
		record.setAttribute("ritagli", jsonRitagli);
		record.setAttribute("rotatePdf", rotationDegree);
		
		new GWTRestService<Record, Record>("PreviewFileDatasource").executecustom("creaFileConOmissis", record, new DSCallback() {
			
			@Override
			public void execute(DSResponse dsResponse, Object data, DSRequest dsRequest) {
				Record result = dsResponse.getData()[0];
				if (result.getAttribute("esitoPeccette") != null && result.getAttributeAsString("esitoPeccette").equalsIgnoreCase("ok")) {
					continuaAzioneDaFare(result);
				} else {
					SC.say(I18NUtil.getMessages().previewWindowAfterPageSelectionPopup_peccette_non_applicabile());
				}
			}
		});
	}
	
	private void continuaAzioneDaFare(Record recordFileConOmissis) {
		String azioneScelta = azioneDaFareItem.getValueAsString();
		if (AzioniAfterPageSelectionEnum.SALVA.valore().equalsIgnoreCase(azioneScelta)) {
			manageAzioneSalva(recordFileConOmissis);
			_window.markForDestroy();
		} else if (AzioniAfterPageSelectionEnum.SALVA_COME_VERS_OMISSIS.valore().equalsIgnoreCase(azioneScelta)) {
			manageAzioneSalvaVersConOmissis(recordFileConOmissis);
			_window.markForDestroy();
		} else if (AzioniAfterPageSelectionEnum.SCARICA.valore().equalsIgnoreCase(azioneScelta)) {
			manageAzioneScarica(recordFileConOmissis);
			_window.markForDestroy();
		} else if (AzioniAfterPageSelectionEnum.NESSUNA.valore().equalsIgnoreCase(azioneScelta)) {
			_window.markForDestroy();
		}
	}
	
	private LinkedHashMap<String, String> creaMappaValoriAzioniDaFare( boolean isReadOnlyMode, boolean enableOptionToSaveInOmissisForm) {
		LinkedHashMap<String, String> mappaAzioniDaFare = new LinkedHashMap<String, String>();
		if (!isReadOnlyMode) {
			mappaAzioniDaFare.put(AzioniAfterPageSelectionEnum.SALVA.valore, AzioniAfterPageSelectionEnum.SALVA.descrizione);
			if (enableOptionToSaveInOmissisForm && ((jsonRitagli != null && !jsonRitagli.trim().equals("") && !jsonRitagli.trim().equals("[]")) || (pageSelected != null && !pageSelected.trim().equals("")))) {
				mappaAzioniDaFare.put(AzioniAfterPageSelectionEnum.SALVA_COME_VERS_OMISSIS.valore, AzioniAfterPageSelectionEnum.SALVA_COME_VERS_OMISSIS.descrizione);
			}
		}
		mappaAzioniDaFare.put(AzioniAfterPageSelectionEnum.SCARICA.valore, AzioniAfterPageSelectionEnum.SCARICA.descrizione);
		mappaAzioniDaFare.put(AzioniAfterPageSelectionEnum.NESSUNA.valore, AzioniAfterPageSelectionEnum.NESSUNA.descrizione);
		return mappaAzioniDaFare;
	}
	
	public void manageAzioneSalva(Record fileConOmissis) {
		if (previewWindowPageSelectionCallback != null) {
			previewWindowPageSelectionCallback.executeSalva(fileConOmissis);
		}
	}
	
	public void manageAzioneSalvaVersConOmissis(Record fileConOmissis) {
		if (previewWindowPageSelectionCallback != null) {
			previewWindowPageSelectionCallback.executeSalvaVersConOmissis(fileConOmissis);		
		}
	}
	
	public void manageAzioneScarica(Record fileConOmissis) {
		Record lRecord = new Record();
		lRecord.setAttribute("displayFilename", fileConOmissis.getAttribute("fileName"));
		lRecord.setAttribute("uri", fileConOmissis.getAttribute("uri"));
		lRecord.setAttribute("sbustato", "false");
		lRecord.setAttribute("remoteUri", "false");
		DownloadFile.downloadFromRecord(lRecord, "FileToExtractBean");
		_window.markForDestroy();
	}

	
	public enum AzioniAfterPageSelectionEnum {
	    SALVA("SALVA", I18NUtil.getMessages().previewWindowAfterPageSelectionPopup_azioneDaFare_salva()),
	    SALVA_COME_VERS_OMISSIS("SALVA_COME_VERS_OMISSIS", I18NUtil.getMessages().previewWindowAfterPageSelectionPopup_azioneDaFare_salvaComeVersioneOmissis()),
		SCARICA("SCARICA", I18NUtil.getMessages().previewWindowAfterPageSelectionPopup_azioneDaFare_scarica()),
		NESSUNA("NESSUNA", I18NUtil.getMessages().previewWindowAfterPageSelectionPopup_azioneDaFare_nessunaAzione());

	    private String valore;
	    private String descrizione;

	    AzioniAfterPageSelectionEnum(String valore, String descrizione) {
	        this.valore = valore;
	        this.descrizione = descrizione;
	    }

	    public String valore() {
	        return valore;
	    }
	    
	    public String descrizione() {
	        return descrizione;
	    }
	}
	
}
