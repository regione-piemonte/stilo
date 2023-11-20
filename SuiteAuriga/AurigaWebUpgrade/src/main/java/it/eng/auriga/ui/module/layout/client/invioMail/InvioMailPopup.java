/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.Map;

import com.smartgwt.client.data.Record;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.Overflow;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.ButtonItem;
import com.smartgwt.client.widgets.form.fields.events.ClickEvent;
import com.smartgwt.client.widgets.form.fields.events.ClickHandler;
import com.smartgwt.client.widgets.layout.VLayout;

import it.eng.auriga.ui.module.layout.client.AurigaLayout;
import it.eng.auriga.ui.module.layout.client.i18n.I18NUtil;
import it.eng.utility.ui.module.layout.client.common.CustomDetail;
import it.eng.utility.ui.module.layout.client.common.items.StaticTextItem;
import it.eng.utility.ui.module.layout.client.portal.ModalWindow;

public abstract class InvioMailPopup extends ModalWindow {
	
	private InvioMailPopup window;
	
	// DynamicForm
	private DynamicForm formMessage;
	private DynamicForm formButton;
	
	//Bottoni
	private ButtonItem chiudiMailECompletaAzioneButton;
	private ButtonItem chiudiMailEAnnullaAzioneButton;
	private ButtonItem nonChiudereMailButton;
	private ButtonItem chiudiMailButton;
	
	/**
	 * Metodo costruttore
	 * @param mappaParametri La mappa dei parametri tramite i quali generare il messaggio e abilitare i pulsanti
	 */
	public InvioMailPopup(Map<String, String> mappaParametri){
		
		super("invio_mail_popup", true);
		
		window = this;
		
		initConfigPopup();
				
		creaFormMessage(mappaParametri);
		
		createFormButton(mappaParametri);
		
		VLayout layout = createMainLayout();
		layout.addMember(formMessage);
		layout.addMember(formButton);
		
		//Aggiungo gli elementi rendendoli visibili
		addMember(layout);
	}

	/**
	 * Metodo che crea il layout della finestra da usare
	 * @return
	 */
	private VLayout createMainLayout() {
		VLayout layout = new VLayout();
		layout.setHeight100();
		layout.setWidth100();
		layout.setOverflow(Overflow.AUTO);
		return layout;
	}
	
	/**
	 * Metodo che crea il form contenente il messaggio da visualizzare
	 * @param message messaggio da visualizzare nel mezzo del popup che viene presentato
	 */
	private void creaFormMessage(Map<String, String> mappaParametri) {
		formMessage = new DynamicForm();
		formMessage.setKeepInParentRect(true);
		formMessage.setWidth100();
		formMessage.setHeight100();
		formMessage.setPadding(10);
		formMessage.setAlign(Alignment.CENTER);
//		formMessage.setOverflow(Overflow.VISIBLE);
		formMessage.setOverflow(Overflow.HIDDEN);
		formMessage.setTop(50);
		formMessage.setWrapItemTitles(false);
		formMessage.setNumCols(1);
		formMessage.setColWidths("*");
		
		StaticTextItem messageItem = new StaticTextItem();
		messageItem.setTextAlign(Alignment.CENTER);
		messageItem.setValue("<span style=\"font-size:12;\" \"text-align:center\"> "+ generaMessaggioFormMessage(mappaParametri) +" </span>");
		messageItem.setWidth(100);
		messageItem.setWrap(false);
		messageItem.setShowTitle(false);
		
		formMessage.setItems(messageItem);
		
		
	}
	
	/**
	 * Metodo che genera il messaggio da visualizzare
	 * @param mappaParametri I parametri tramite i quali generare il messaggio
	 * @return Il messaggio da visualizzare nella popup
	 */
	private String generaMessaggioFormMessage(Map<String, String> mappaParametri){
		// Creo il messaggio da visualizzare nella popup
		String testoPopup = "";
		String labelArchiviazione = (AurigaLayout.getParametroDB("LABEL_ARCHIVIAZIONE_EMAIL") != null && !"".equalsIgnoreCase(AurigaLayout.getParametroDB("LABEL_ARCHIVIAZIONE_EMAIL"))) ? AurigaLayout.getParametroDB("LABEL_ARCHIVIAZIONE_EMAIL") : "Chiudi";
		
		boolean isOperazioneMassiva = Boolean.valueOf(mappaParametri.get("isOperazioneMassiva"));
		String nomeOperazione = mappaParametri.get("nomeOperazione"); 
		boolean isAzioniDaFarePresenti = Boolean.valueOf(mappaParametri.get("isAzioniDaFarePresenti"));
		String nomeAzioneDaFare = mappaParametri.get("nomeAzioneDaFare");
		
		if(isAzioniDaFarePresenti){
			if (!isOperazioneMassiva){
				if ("risposta".equalsIgnoreCase(nomeOperazione)){
					testoPopup = I18NUtil.getMessages().invio_mail_popup_messaggio_rispondi_azione_singola(labelArchiviazione, nomeAzioneDaFare);
				}else{
					testoPopup = I18NUtil.getMessages().invio_mail_popup_messaggio_inoltro_singolo_azione_singola(labelArchiviazione, nomeAzioneDaFare);
				}
			}else{
				if (nomeAzioneDaFare != null && !"".equalsIgnoreCase(nomeAzioneDaFare)){
					testoPopup = I18NUtil.getMessages().invio_mail_popup_messaggio_inoltro_multiplo_azione_singola(labelArchiviazione, nomeAzioneDaFare);
				}else{
					testoPopup = I18NUtil.getMessages().invio_mail_popup_messaggio_inoltro_multiplo_azione_multipla(labelArchiviazione);
				}
			}
		}else{
			if (!isOperazioneMassiva){
				if ("risposta".equalsIgnoreCase(nomeOperazione)){
					testoPopup = I18NUtil.getMessages().invio_mail_popup_messaggio_rispondi(labelArchiviazione);
				}else{
					testoPopup = I18NUtil.getMessages().invio_mail_popup_messaggio_inoltro_singolo(labelArchiviazione);
				}
			}else{
				testoPopup = I18NUtil.getMessages().invio_mail_popup_messaggio_inoltro_multiplo(labelArchiviazione);
			}
		}
		return testoPopup;
	}

	/**
	 * Metodo che crea il form contenente i bottono con le azioni da fare
	 * @param mappaParametri I parametri tramite i quali visulizzare i bottoni
	 */
	private void createFormButton(Map<String, String> mappaParametri) {
		
		//Creo la strip per i pulsanti
		formButton = new DynamicForm();
		formButton.setWidth100();
		formButton.setHeight(30);
		formButton.setPadding(5);
		formButton.setAutoFocus(true);
		
		boolean isAzioniDaFarePresenti = Boolean.valueOf(mappaParametri.get("isAzioniDaFarePresenti"));
		
		if(isAzioniDaFarePresenti){
			// Nel caso in cui ci siano delle azioni da fare allora bisogna abilitare i che gestiscono le azioni da fare
			formButton.setNumCols(3);
			formButton.setColWidths(1,1,1);
			creaChiudiMailECompletaAzioneButton();
			creaChiudiMailEAnnullaAzioneButton();
			creaNonChiudereMailButton();
			formButton.setItems(chiudiMailECompletaAzioneButton, chiudiMailEAnnullaAzioneButton, nonChiudereMailButton);
		}else{
			// Se non ho azioni da fare abilito solo i pulsanti senza gestione della azioni da fare
			formButton.setNumCols(3);
			formButton.setColWidths(1,1,1);
			creaChiudiMailButton();
			creaNonChiudereMailButton();
			formButton.setItems(chiudiMailButton, nonChiudereMailButton);
		}
		
	}
	
	private void creaChiudiMailButton() {
		chiudiMailButton = new ButtonItem("chiudiMailButton", I18NUtil.getMessages().invio_mail_popup_chiudiMailButton_title());   
		chiudiMailButton.setIcon("ok.png");
		chiudiMailButton.setAutoFit(false);
		chiudiMailButton.setWidth(200);
		chiudiMailButton.setTabIndex(1);
		chiudiMailButton.setStartRow(true);
		chiudiMailButton.setEndRow(false);
		chiudiMailButton.setAlign(Alignment.CENTER);
		chiudiMailButton.addClickHandler(new ClickHandler() {			
			@Override
			public void onClick(ClickEvent arg0) {
				if(formMessage.validate()) {
					final Record formRecord = new Record(formMessage.getValues());
					onClickChiudiMailButton(formRecord);							
				}
			}
		});
	}

	private void creaChiudiMailECompletaAzioneButton() {
		chiudiMailECompletaAzioneButton = new ButtonItem("chiudiMailECompletaAzioneButton", I18NUtil.getMessages().invio_mail_popup_chiudiMailECompletaAzioneButton_title());   
		chiudiMailECompletaAzioneButton.setIcon("ok.png");
		chiudiMailECompletaAzioneButton.setAutoFit(false);
		chiudiMailECompletaAzioneButton.setWidth(200);
		chiudiMailECompletaAzioneButton.setTabIndex(2);
		chiudiMailECompletaAzioneButton.setStartRow(true);
		chiudiMailECompletaAzioneButton.setEndRow(false);
		chiudiMailECompletaAzioneButton.setAlign(Alignment.CENTER);
		chiudiMailECompletaAzioneButton.addClickHandler(new ClickHandler() {			
			@Override
			public void onClick(ClickEvent arg0) {
				if(formMessage.validate()) {
					final Record formRecord = new Record(formMessage.getValues());
					onClickChiudiMailECompletaAzioneButton(formRecord);							
				}
			}
		});
	}

	private void creaChiudiMailEAnnullaAzioneButton() {
		chiudiMailEAnnullaAzioneButton = new ButtonItem("chiudiMailEAnnullaAzioneButton", I18NUtil.getMessages().invio_mail_popup_chiudiMailEAnnullaAzioneButton_title());   
		chiudiMailEAnnullaAzioneButton.setIcon("ok.png");
		chiudiMailEAnnullaAzioneButton.setAutoFit(false);
		chiudiMailEAnnullaAzioneButton.setWidth(200);
		chiudiMailEAnnullaAzioneButton.setTabIndex(3);
		chiudiMailEAnnullaAzioneButton.setStartRow(false);
		chiudiMailEAnnullaAzioneButton.setEndRow(false);
		chiudiMailEAnnullaAzioneButton.setAlign(Alignment.CENTER);
		chiudiMailEAnnullaAzioneButton.addClickHandler(new ClickHandler() {			
			@Override
			public void onClick(ClickEvent arg0) {
				if(formMessage.validate()) {
					final Record formRecord = new Record(formMessage.getValues());
					onClickChiudiMailEAnnullaAzioneButton(formRecord);							
				}
			}
		});
	}		
	
	private void creaNonChiudereMailButton() {
		nonChiudereMailButton = new ButtonItem("nonChiudereMailButton", I18NUtil.getMessages().invio_mail_popup_nonChiudereMailButton_title());   
		nonChiudereMailButton.setIcon("annulla.png");
		nonChiudereMailButton.setAutoFit(false);
		nonChiudereMailButton.setWidth(200);
		nonChiudereMailButton.setTabIndex(4);
		nonChiudereMailButton.setStartRow(false);
		nonChiudereMailButton.setEndRow(true);
		nonChiudereMailButton.setAlign(Alignment.CENTER);
		nonChiudereMailButton.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent arg0) {
				if(formMessage.validate()) {
					final Record formRecord = new Record(formMessage.getValues());
					onClickNonChiudereMailButton(formRecord);							
				}				
			}
		});
	}

	/**
	 * Metodo per settare le configurazioni iniziali della finestra
	 */
	private void initConfigPopup() {
		
		//Settaggio del titolo della finestra
		setTitle(I18NUtil.getMessages().invio_mail_popup_form_title());
		setIcon("warning.png");
		
		//Settaggio delle dimensioni della finestra
		setHeight(115);
		setWidth(660);
		
		hideContextMenu();
	}

	/*
	 * Metodi che definiscono il comportamento alla pressione dei tre tasti
	 * Se si vuole fornire un comportamento a riguardo bisogna eseguirne l'override
	 */
	
	/**
	 * Metodo che definisce il comportamento alla pressione del tasto relativo
	 * @param formRecord
	 */
	public abstract void onClickChiudiMailECompletaAzioneButton(Record formRecord);
	
	/**
	 * Metodo che definisce il comportamento alla pressione del tasto relativo
	 * @param formRecord
	 */
	public abstract void onClickChiudiMailEAnnullaAzioneButton(Record formRecord);
	
	/**
	 * Metodo che definisce il comportamento alla pressione del tasto relativo
	 * @param formRecord
	 */
	public abstract void onClickChiudiMailButton(Record formRecord);
	
	/**
	 * Metodo che definisce il comportamento alla pressione del tasto relativo
	 * @param formRecord
	 */
	public abstract void onClickNonChiudereMailButton(Record formRecord);
	
}