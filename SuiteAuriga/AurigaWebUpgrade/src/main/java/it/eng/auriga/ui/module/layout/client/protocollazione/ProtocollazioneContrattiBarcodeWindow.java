/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.LinkedHashMap;

import com.smartgwt.client.data.DSCallback;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.Overflow;
import com.smartgwt.client.widgets.Button;
import com.smartgwt.client.widgets.events.CloseClickEvent;
import com.smartgwt.client.widgets.events.CloseClickHandler;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.FormItemIfFunction;
import com.smartgwt.client.widgets.form.ValuesManager;
import com.smartgwt.client.widgets.form.fields.FormItem;
import com.smartgwt.client.widgets.form.fields.HiddenItem;
import com.smartgwt.client.widgets.layout.HStack;
import com.smartgwt.client.widgets.layout.VLayout;

import it.eng.auriga.ui.module.layout.client.AurigaLayout;
import it.eng.auriga.ui.module.layout.client.PortScannerUtility;
import it.eng.auriga.ui.module.layout.client.PrinterScannerUtility;
import it.eng.auriga.ui.module.layout.client.PrinterScannerUtility.PrinterScannerCallback;
import it.eng.auriga.ui.module.layout.client.StampaEtichettaUtility;
import it.eng.auriga.ui.module.layout.client.PortScannerUtility.PortScannerCallback;
import it.eng.auriga.ui.module.layout.client.StampaEtichettaUtility.StampaEtichettaCallback;
import it.eng.auriga.ui.module.layout.client.i18n.I18NUtil;
import it.eng.utility.ui.module.core.client.callback.ServiceCallback;
import it.eng.utility.ui.module.core.client.datasource.GWTRestDataSource;
import it.eng.utility.ui.module.core.client.datasource.GWTRestService;
import it.eng.utility.ui.module.core.shared.message.MessageBean;
import it.eng.utility.ui.module.core.shared.message.MessageType;
import it.eng.utility.ui.module.layout.client.Layout;
import it.eng.utility.ui.module.layout.client.common.DetailSection;
import it.eng.utility.ui.module.layout.client.common.items.DateTimeItem;
import it.eng.utility.ui.module.layout.client.common.items.NumericItem;
import it.eng.utility.ui.module.layout.client.common.items.SelectItem;
import it.eng.utility.ui.module.layout.client.common.items.TextItem;
import it.eng.utility.ui.module.layout.client.portal.ModalWindow;

/**
 * 
 * @author dbe4235
 *
 */

public class ProtocollazioneContrattiBarcodeWindow extends ModalWindow {
	
	protected ProtocollazioneContrattiBarcodeWindow _window;
	
	private ValuesManager vm;
	
	protected DynamicForm formUoProtocollante;
	protected SelectItem uoProtocollanteSelectItem;
	
	protected DetailSection sectionEstremi;
	protected DynamicForm formEstremi;
	protected HiddenItem idUdItem;
	protected NumericItem nroProtocolloItem;
	protected DateTimeItem dataProtocolloItem;
	protected TextItem societaItem;
	protected TextItem desUOProtocolloItem;
	
	private HStack buttons;
	private Button generaNroProtButton;
	private Button timbraButton;
	
	public ProtocollazioneContrattiBarcodeWindow() {
		super("protocollazione_contratti_barcode", true);
		
		_window = this;
		
		vm = new ValuesManager();
		
		setTitle("Protocollazione in entrata doc. cartacea contratti con barcode");
		setIcon("menu/protocollazione_contratti_barcode.png");

		setAutoCenter(true);
		setAlign(Alignment.CENTER);
		setTop(50);
		setHeight(150);
		
		//Per la rimozione degli elementi dal pulsante con la rotellina in alto a destra
		settingsMenu.removeItem(separatorMenuItem);
		settingsMenu.removeItem(autoSearchMenuItem);
		
		addCloseClickHandler(new CloseClickHandler() {
			
			@Override
			public void onCloseClick(CloseClickEvent event) {
				markForDestroy();
			}
		});
		
		createUoProtocollanteForm();
		
		createProtocolloForm();

		generaNroProtButton = new Button("Genera nuovo N° di protocollo");
		generaNroProtButton.setIcon("ok.png");
		generaNroProtButton.setIconSize(16);
		generaNroProtButton.setAutoFit(true);
		generaNroProtButton.addClickHandler(new com.smartgwt.client.widgets.events.ClickHandler() {

			@Override
			public void onClick(com.smartgwt.client.widgets.events.ClickEvent event) {
				if(formEstremi.validate()) {
					Layout.showWaitPopup("Registrazione in corso: potrebbe richiedere qualche secondo. Attendere...");
					
					Record recordToSave = new Record();
					recordToSave.setAttribute("flgTipoProv", "E");
					if (uoProtocollanteSelectItem != null) {
						if (uoProtocollanteSelectItem.getValueAsString() != null && !"".equals(uoProtocollanteSelectItem.getValueAsString())) {
							recordToSave.setAttribute("uoProtocollante", uoProtocollanteSelectItem.getValueAsString());
						} else if (getSelezioneUoProtocollanteValueMap().size() == 1) {
							recordToSave.setAttribute("uoProtocollante", getSelezioneUoProtocollanteValueMap().keySet().toArray(new String[1])[0]);
						}
					} 				
					final GWTRestDataSource protocolloDS = new GWTRestDataSource("ProtocolloDataSource");
					protocolloDS.executecustom("generaNroProtContratti", recordToSave, new DSCallback() {

						@Override
						public void execute(DSResponse response, Object rawData, DSRequest request) {
							if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
								
								Record record = response.getData()[0];
								
								final GWTRestDataSource lGwtRestDataSourceProtocollo = new GWTRestDataSource("ProtocolloDataSource");
								Record lRecordToLoad = new Record();
								lRecordToLoad.setAttribute("idUd", record.getAttribute("idUd"));
								lGwtRestDataSourceProtocollo.getData(lRecordToLoad, new DSCallback() {

									@Override
									public void execute(DSResponse response, Object rawData, DSRequest request) {
										if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
											Record lRecord = response.getData()[0];
											if(lRecord.getAttribute("idUd") != null && !"".equalsIgnoreCase(lRecord.getAttribute("idUd"))){
												editRecordEstremi(lRecord);
												timbraMode();
												markForRedraw();
											}
										}
									}
								});
							}
							Layout.hideWaitPopup();
						}
					});				
				}
			}
		});
		
		timbraButton = new Button("Timbra");
		timbraButton.setIcon("file/timbra.gif");
		timbraButton.setIconSize(16);
		timbraButton.setAutoFit(true);
		timbraButton.addClickHandler(new com.smartgwt.client.widgets.events.ClickHandler() {

			@Override
			public void onClick(com.smartgwt.client.widgets.events.ClickEvent event) {
				if(formEstremi.validate()) {
					
					// Stampa etichetta
					if(AurigaLayout.getParametroDBAsBoolean("ATTIVA_TIMBRATURA_CARTACEO")) {
						clickStampaEtichetta(new DSCallback() {	
							
							@Override
							public void execute(DSResponse response, Object rawData, DSRequest request) {
								
								newMode();
							}																				
						});
					}
						
				}
			}
		});
		
		buttons = new HStack(5);
		buttons.setHeight(30);
		buttons.setAlign(Alignment.CENTER);
		buttons.setPadding(5);
		buttons.addMember(generaNroProtButton);
		buttons.addMember(timbraButton);
		
		VLayout layout = new VLayout();
		layout.setHeight100();
		layout.setWidth100();
		layout.setOverflow(Overflow.AUTO);
		
		VLayout portletLayout = new VLayout();
		portletLayout.setHeight100();
		portletLayout.setWidth100();
		portletLayout.setOverflow(Overflow.VISIBLE);
		
		layout.addMember(formUoProtocollante);
		layout.addMember(sectionEstremi);

		portletLayout.addMember(layout);
		portletLayout.addMember(buttons);

		setBody(portletLayout);
		
		newMode();		
	}
	
	private void editRecordEstremi(Record record) {
		idUdItem.setValue(record.getAttribute("idUd"));
		nroProtocolloItem.setValue(record.getAttribute("nroProtocollo"));
		dataProtocolloItem.setValue(record.getAttribute("dataProtocollo"));
		desUOProtocolloItem.setValue(record.getAttribute("desUOProtocollo"));
		societaItem.setValue(record.getAttribute("societa"));
	}
	
	private void newMode() {
		uoProtocollanteSelectItem.show();
		sectionEstremi.hide();
		generaNroProtButton.show();
		timbraButton.hide();
	}
	
	private void timbraMode() {
		uoProtocollanteSelectItem.hide();
		sectionEstremi.show();
		generaNroProtButton.hide();
		timbraButton.show();
	}

	private void createProtocolloForm() {
		
		formEstremi = new DynamicForm();
		formEstremi.setWidth100();
		formEstremi.setHeight100();
		formEstremi.setNumCols(8);
		formEstremi.setColWidths("1","1","1","1","1","1","1","*");
		formEstremi.setCellPadding(5);
		formEstremi.setWrapItemTitles(false);
		formEstremi.setValuesManager(vm);
		
		idUdItem = new HiddenItem("idUd");
		
		nroProtocolloItem = new NumericItem("nroProtocollo", "Protocollo N°");
		nroProtocolloItem.setLength(7);
		nroProtocolloItem.setColSpan(1);
		nroProtocolloItem.setCanEdit(false);
		nroProtocolloItem.setTextBoxStyle(it.eng.utility.Styles.textItemBold);
		nroProtocolloItem.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				
				return idUdItem.getValue() != null && !"".equals(idUdItem.getValue());
			}
		});
		
		dataProtocolloItem = new DateTimeItem("dataProtocollo", "del");
		dataProtocolloItem.setCanEdit(false);
		dataProtocolloItem.setColSpan(1);
		dataProtocolloItem.setTextBoxStyle(it.eng.utility.Styles.textItemBold);
		dataProtocolloItem.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				
				return idUdItem.getValue() != null && !"".equals(idUdItem.getValue());
			}
		});
		
		societaItem = new TextItem("societa", I18NUtil.getMessages().protocollazione_detail_societaItem_title());
		societaItem.setWidth(150);
		societaItem.setColSpan(1);
		societaItem.setTextBoxStyle(it.eng.utility.Styles.textItemBold);
		societaItem.setShowIfCondition(new FormItemIfFunction() {

			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return showSocietaItem();
			}
		});
		
		desUOProtocolloItem = new TextItem("desUOProtocollo", I18NUtil.getMessages().protocollazione_detail_desUOProtocolloItem_title());
		desUOProtocolloItem.setWidth(300);
		desUOProtocolloItem.setColSpan(1);
		desUOProtocolloItem.setTextBoxStyle(it.eng.utility.Styles.textItemBold);
		if (AurigaLayout.getIsAttivaAccessibilita()) {
			desUOProtocolloItem.setCanFocus(true);				
		}	
		
		formEstremi.setFields(idUdItem,nroProtocolloItem,dataProtocolloItem,societaItem,desUOProtocolloItem);
		
		sectionEstremi = new DetailSection("Registrazione", false, true, false, formEstremi);
	}
	
	/**
	 * Metodo per costruire la select della U.O. protocollante
	 * 
	 */
	protected void createUoProtocollanteForm() {
		
		formUoProtocollante = new DynamicForm();
		formUoProtocollante.setWidth100();
		formUoProtocollante.setHeight100();
		formUoProtocollante.setNumCols(3);
		formUoProtocollante.setColWidths("1","1","*");
		formUoProtocollante.setCellPadding(5);
		formUoProtocollante.setWrapItemTitles(false);
		formUoProtocollante.setValuesManager(vm);

		uoProtocollanteSelectItem = new SelectItem("uoProtocollante", getTitleUoProtocollanteSelectItem());
		uoProtocollanteSelectItem.setWidth((showModelliSelectItem() && showUoProtocollanteSelectItem()) ? 350 : 600);
		if (AurigaLayout.getIsAttivaAccessibilita()) {
			uoProtocollanteSelectItem.setCanFocus(true);		
		} 
		uoProtocollanteSelectItem.setWrapTitle(false);
		uoProtocollanteSelectItem.setDisplayField("descrizione");
		uoProtocollanteSelectItem.setValueField("idUo");
		uoProtocollanteSelectItem.setAllowEmptyValue(true);
		uoProtocollanteSelectItem.setRequired(true);
		uoProtocollanteSelectItem.setValueMap(getUoProtocollanteValueMap());
		uoProtocollanteSelectItem.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				
				return showUoProtocollanteSelectItem();
			}
		});

		if (getSelezioneUoProtocollanteValueMap().size() == 1) {
			String uoProtocollante = getSelezioneUoProtocollanteValueMap().keySet().toArray(new String[1])[0];
			if (getUoProtocollanteValueMap() != null && getUoProtocollanteValueMap().containsKey(uoProtocollante)) {
				uoProtocollanteSelectItem.setValue(uoProtocollante);
			}
		}	
		
		formUoProtocollante.setFields(uoProtocollanteSelectItem);
	}
	
	
	/**
	 * Metodo che implementa l'azione del bottone "Stampa etichetta"
	 * 
	 */
	public void clickStampaEtichetta(final DSCallback callback) {
		
		Record detailRecord = new Record(vm.getValues());

		manageStampaEtichettaTimbraturaCartaceo(detailRecord);
		
		callback.execute(new DSResponse(), null, new DSRequest());
	}
	
	/**
	 * Gestione di stampa delle etichetta bypassando la scelta delle opzioni di stampa
	 */
	protected void manageStampaEtichettaTimbraturaCartaceo(final Record record) {
		
		if (AurigaLayout.getParametroDBAsBoolean("ATTIVA_TIMBRATURA_CARTACEO")) {
			// Stampa con stampigliatrice
			/**
			 * Viene verificato che sia stata selezionata una porta in precedenza
			 */
			if(AurigaLayout.getImpostazioneStampa("portaStampanteTimbraturaCartaceo") != null && !"".equals(AurigaLayout.getImpostazioneStampa("portaStampanteTimbraturaCartaceo"))){
				buildStampaEtichettaTimbraturaCartaceo(record, null, null);
			} else {
				PortScannerUtility.portScanner("", new PortScannerCallback() {
	
					@Override
					public void execute(String portaStampanteTimbraturaCartaceo) {
						record.setAttribute("portaStampanteTimbraturaCartaceo", portaStampanteTimbraturaCartaceo);
						buildStampaEtichettaTimbraturaCartaceo(record, "", portaStampanteTimbraturaCartaceo);
					}
				}, new PortScannerCallback() {
					
					@Override
					public void execute(String nomeStampante) {
						Layout.addMessage(new MessageBean(I18NUtil.getMessages().protocollazione_detail_stampaEtichettaPostRegSenzaOpzStampa_errorMessage(), "", MessageType.ERROR));
					}
				});
			}	
		} else {
			/**
			 * Viene verificato che sia stata selezionata una stampante in precedenza
			 */
			if(AurigaLayout.getImpostazioneStampa("stampanteEtichette") != null && !"".equals(AurigaLayout.getImpostazioneStampa("stampanteEtichette"))){
				buildStampaEtichettaTimbraturaCartaceo(record, null, null);
			} else {
				PrinterScannerUtility.printerScanner("", new PrinterScannerCallback() {

					@Override
					public void execute(String nomeStampante) {
						record.setAttribute("nomeStampante", nomeStampante);
						buildStampaEtichettaTimbraturaCartaceo(record, nomeStampante, "");
					}
				}, new PrinterScannerCallback() {
					
					@Override
					public void execute(String nomeStampante) {
						Layout.addMessage(new MessageBean(I18NUtil.getMessages().protocollazione_detail_stampaEtichettaPostRegSenzaOpzStampa_errorMessage(), "", MessageType.ERROR));
					}
				});
			}
		}
	}
	
	/**
	 * Stampa dell'etichetta post-registrazione
	 */
	private void buildStampaEtichettaTimbraturaCartaceo(Record record, String nomeStampante, String portaStampanteTimbraturaCartaceo) {
		
		Layout.showWaitPopup("Stampa etichetta in corso...");
		Record lRecord = new Record();
		lRecord.setAttribute("idUd", record.getAttribute("idUd"));
		//lRecord.setAttribute("listaAllegati", record.getAttributeAsRecordList("listaAllegati"));
		if(nomeStampante == null || "".equals(nomeStampante)) {
			nomeStampante = AurigaLayout.getImpostazioneStampa("stampanteEtichette");
		}
		lRecord.setAttribute("nomeStampante", nomeStampante);
		if(portaStampanteTimbraturaCartaceo == null || "".equals(portaStampanteTimbraturaCartaceo)) {
			portaStampanteTimbraturaCartaceo = AurigaLayout.getImpostazioneStampa("portaStampanteTimbraturaCartaceo");
		}
		lRecord.setAttribute("portaStampanteTimbraturaCartaceo", portaStampanteTimbraturaCartaceo);
		lRecord.setAttribute("nroEtichette",  "1");		
		
		Record impostazioniStampa = AurigaLayout.getImpostazioneStampa();
		lRecord.setAttribute("flgPrimario", impostazioniStampa != null ? AurigaLayout.getImpostazioneStampaAsBoolean("flgPrimario") : true);
//		lRecord.setAttribute("flgAllegati", impostazioniStampa != null ? AurigaLayout.getImpostazioneStampaAsBoolean("flgAllegati") : true);
		lRecord.setAttribute("flgSegnRegPrincipale", true);
//		lRecord.setAttribute("flgSegnRegSecondaria", impostazioniStampa != null ? AurigaLayout.getImpostazioneStampaAsBoolean("flgSegnRegSecondaria") : true);
//		lRecord.setAttribute("flgHideBarcode", !AurigaLayout.getImpostazioneStampaAsBoolean("stampaBarcode"));
//		if(AurigaLayout.getParametroDBAsBoolean("ATTIVA_ETICHETTA_ORIG_COPIA")){			
//			lRecord.setAttribute("notazioneCopiaOriginale",  AurigaLayout.getImpostazioneStampa("notazioneCopiaOriginale"));
//		}
		
		GWTRestService<Record, Record> lGwtRestService = new GWTRestService<Record, Record>("PreparaValoriEtichettaDatasource");
		lGwtRestService.call(lRecord, new ServiceCallback<Record>() {

			@Override
			public void execute(Record object) {
				Layout.hideWaitPopup();
				final String nomeStampante = object.getAttribute("nomeStampante");
				final String portaStampanteTimbraturaCartaceo = object.getAttribute("portaStampanteTimbraturaCartaceo");
				final Record[] etichette = object.getAttributeAsRecordArray("etichette");
				final String numCopie = object.getAttribute("nroEtichette");
				StampaEtichettaUtility.stampaEtichetta("", "", nomeStampante, portaStampanteTimbraturaCartaceo, etichette, numCopie, new StampaEtichettaCallback() {

					@Override
					public void execute() {
					
					}
				});
			}

			@Override
			public void manageError() {
				Layout.hideWaitPopup();
				Layout.addMessage(new MessageBean("Impossibile stampare l'etichetta", "", MessageType.ERROR));
			}
		});
	}
	
	
	/**
	 * Metodo che restituisce la label della select U.O. protocollante
	 * 
	 */
	public String getTitleUoProtocollanteSelectItem() {
		return "<b>U.O. protocollante</b>";
	}
	
	/**
	 * Metodo che indica se mostrare o meno la select dei modelli
	 * 
	 */
	public boolean showModelliSelectItem() {
		return true;
	}
	
	/**
	 * Metodo che indica se mostrare o meno la select della U.O. protocollante
	 * 
	 */
	public boolean showUoProtocollanteSelectItem() {
		return getUoProtocollanteValueMap().size() > 1;
	}
	
	/**
	 * Metodo che restituisce la mappa di tutte le UO di registrazione
	 * 
	 */
	protected LinkedHashMap<String, String> getUoProtocollanteValueMap() {
		return AurigaLayout.getUoSpecificitaRegistrazioneEValueMap();
	}
	
	/**
	 * Metodo che ritorna la mappa di tutte le UO di registrazione se non è settata una UO su cui è attiva la funzione di protocollo oppure una UO di lavoro predefinita, in tal caso ritornerà solo quella
	 * se ho un'unica UO di registrazione il metodo ovviamente restituirà solo quella
	 * 
	 */
	public LinkedHashMap<String, String> getSelezioneUoProtocollanteValueMap() {		
		return AurigaLayout.getSelezioneUoRegistrazioneValueMap("E");
	}
	
	/**
	 * Metodo che indica se mostrare o meno la Società
	 */
	public boolean showSocietaItem() {
		return AurigaLayout.getParametroDBAsBoolean("ATTIVA_MULTISOCIETA");
	}

}