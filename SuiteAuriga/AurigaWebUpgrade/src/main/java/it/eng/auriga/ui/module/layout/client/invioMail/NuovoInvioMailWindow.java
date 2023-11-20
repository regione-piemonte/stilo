/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.Map;

import com.smartgwt.client.data.AdvancedCriteria;
import com.smartgwt.client.data.DSCallback;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.data.RecordList;
import com.smartgwt.client.types.BackgroundRepeat;
import com.smartgwt.client.types.FieldType;
import com.smartgwt.client.types.OperatorId;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.events.CellClickEvent;
import com.smartgwt.client.widgets.grid.events.CellClickHandler;
import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.client.widgets.toolbar.ToolStrip;
import com.smartgwt.client.widgets.toolbar.ToolStripButton;

import it.eng.auriga.ui.module.layout.client.AurigaLayout;
import it.eng.auriga.ui.module.layout.client.i18n.I18NUtil;
import it.eng.auriga.ui.module.layout.client.postaElettronica.PostaElettronicaWindow;
import it.eng.utility.ui.module.core.client.UserInterfaceFactory;
import it.eng.utility.ui.module.core.client.datasource.GWTRestDataSource;
import it.eng.utility.ui.module.core.shared.message.MessageBean;
import it.eng.utility.ui.module.core.shared.message.MessageType;
import it.eng.utility.ui.module.layout.client.Layout;
import it.eng.utility.ui.module.layout.client.common.CustomDetail;
import it.eng.utility.ui.module.layout.client.common.CustomLayout;
import it.eng.utility.ui.module.layout.client.common.items.SelectItem;

public class NuovoInvioMailWindow extends PostaElettronicaWindow {

	private NuovoInvioMailWindow _window;
	private InvioMailLayout _layout;
	
	protected ToolStrip mainToolStrip;

	protected SelectItem firmaInCalceSelectItem;
	protected ListGrid firmaInCalcePickListProperties;
	protected ToolStripButton firmaPredefinitaButton;
	
	protected GWTRestDataSource firmeDS;

	/**
	 * Può essere: 1) Nuovo Invio Copia Email (codice NIC)
	 * 
	 * é l'invio di una mail copia a quella originale di di uscita, che però la sua email predecessore non sarà l'email originale da cui faccio copia ma l'email
	 * di entrata se c'è.
	 * 
	 * 2) Re-Invio Email (codice RI)
	 * 
	 * @param record
	 * @param tipoRel
	 * @param callback
	 */

	public NuovoInvioMailWindow(String tipoRel, Record record, String title, String tipoInvio, DSCallback callback) {
		super("nuovoinvioemail", false, tipoRel, record, null, false, title, tipoInvio, title, callback);

		_window = this;
	}

	public NuovoInvioMailWindow(String tipoRel, Record record, String title, String tipoInvio, CustomLayout customLayoutToDoSearch, DSCallback callback) {
		super("nuovoinvioemail", false, tipoRel, record, null, false, title, tipoInvio, title, customLayoutToDoSearch, callback);

		_window = this;
	}

	public NuovoInvioMailWindow(String tipoRel, Record record, String title, String tipoInvio, CustomDetail customDetailToReload, DSCallback callback) {
		super("nuovoinvioemail", false, tipoRel, record, null, false, null, tipoInvio, null, customDetailToReload, callback);

		setTitle(title);
		_window = this;
	}

	@Override
	public VLayout getLayoutDatiWindow() {
		Layout.showWaitPopup(I18NUtil.getMessages().posta_elettronica_detail_caricamento_mail());

		setAutoCenter(true);

		settingsMenu.removeItem(separatorMenuItem);
		settingsMenu.removeItem(autoSearchMenuItem);

		buildNuovoInvioMail();

		return _layout;
	}

	private void buildNuovoInvioMail() {

		final String allegaEmlSbustato = recordPrincipale.getAttribute("allegaEmlSbustato");
		final String aliasAccountMittente = recordPrincipale.getAttribute("aliasAccountMittente");

		String _salvaInviati = null;

		RecordList emailPredIdEmail = null;

		// if ("NIC".equals(tipoInvio)
		// && ("I".equals(recordPrincipale.getAttribute("emailPredecessoreFlgIO")) || "O".equals(recordPrincipale.getAttribute("emailPredecessoreFlgIO")))
		// && (recordPrincipale.getAttribute("emailPredecessoreCategoria") != null
		// && (recordPrincipale.getAttribute("emailPredecessoreCategoria").equalsIgnoreCase("INTEROP_SEGN")
		// || recordPrincipale.getAttribute("emailPredecessoreCategoria").equalsIgnoreCase("PEC")
		// || recordPrincipale.getAttribute("emailPredecessoreCategoria").equalsIgnoreCase("ANOMALIA")
		// || recordPrincipale.getAttribute("emailPredecessoreCategoria").equalsIgnoreCase("ALTRO")))) {
		// emailPredIdEmail = recordPrincipale.getAttribute("emailPredecessoreIdEmail");
		//
		// if (emailPredIdEmail == null)
		// emailPredIdEmail = recordPrincipale.getAttribute("idEmailCollegata");
		//
		// } else {
		// // tipoRelCopia = "invioNuovoMessaggio";
		// // se non c'è nessuna email precedente è una nuova email , idEmail è null ma deve essere nuovo invio
		// _salvaInviati = "true";
		// }

		// Verifico se sono in un inoltro come copia.
		boolean predecessoriMultipli = false;
		if ("NIC".equals(tipoInvio)) {
			// Devo verificare se ho il predecessore. Se ho un unico predecessore lo trovo in emailPredecessoreIdEmail, altrimenti
			// se ho una lista di predecessori (caso di un nuovo invio come copia di un inoltro multiplo) li trovo nella
			// lista di stringhe idEmailInoltrate.

			// Verifico se ho un predecessore singolo
			String predecessoreSingolo = recordPrincipale.getAttribute("emailPredecessoreIdEmail");
			RecordList listaPredecessori = recordPrincipale.getAttributeAsRecordList("idEmailInoltrate");
			boolean predecessoreTrovato = false;

			if ((listaPredecessori != null) && (listaPredecessori.getLength() > 0)) {
				// Ho una lista di predecessori
				emailPredIdEmail = listaPredecessori;
				predecessoreTrovato = true;
				if (listaPredecessori.getLength() > 1) {
					predecessoriMultipli = true;
				}
			} else if ((predecessoreSingolo != null) && (predecessoreSingolo.length() > 0)) {
				// Ho un solo predecessore
				Record predecessore = new Record();
				predecessore.setAttribute("idMailInoltrata", predecessoreSingolo);
				emailPredIdEmail = new RecordList();
				emailPredIdEmail.add(predecessore);
				predecessoreTrovato = true;
			}
			// Se non ho predecessori, utilizzo l'id della mail collegata (sempre che sia presente)
			if (!predecessoreTrovato) {
				String idEmailCollegata = recordPrincipale.getAttribute("idEmailCollegata");
				if ((idEmailCollegata != null) && (idEmailCollegata.length() > 0)) {
					Record predecessore = new Record();
					predecessore.setAttribute("idMailInoltrata", idEmailCollegata);
					emailPredIdEmail = new RecordList();
					emailPredIdEmail.add(predecessore);
				}
			}
		} else {
			// tipoRelCopia = "invioNuovoMessaggio";
			// se non c'è nessuna email precedente è una nuova email , idEmail è null ma deve essere nuovo invio
			_salvaInviati = "true";
		}

		String sottotipoEmail = recordPrincipale.getAttribute("sottotipo");

		String tipoRelCopia = "";
		if (predecessoriMultipli) {
			// Ho una lista di precessori, possibile solamente se ho fatto un nuovo come copia a partire da una lista di inoltri
			// Quindi il tipoRelCopia è sicuramente inoltro
			tipoRelCopia = "inoltro";
		} else if (sottotipoEmail != null) {
			if (sottotipoEmail.toLowerCase().startsWith("inoltro"))
				tipoRelCopia = "inoltro";
			else if (sottotipoEmail.toLowerCase().startsWith("risposta"))
				tipoRelCopia = "risposta";
			else if (sottotipoEmail.toLowerCase().startsWith("notifica") && sottotipoEmail.toLowerCase().contains("eccezione"))
				tipoRelCopia = "notifica_eccezione";
			else if (sottotipoEmail.toLowerCase().contains("conferma"))
				tipoRelCopia = "notifica_conferma";
		}

		final RecordList emailPredecessoreIdEmail = emailPredIdEmail;
		final String salvaInviati = _salvaInviati;
		final String _tipoInvio = tipoInvio;
		final String _tipoRelCopia = tipoRelCopia;

		if (recordPrincipale != null && recordPrincipale.getAttribute("idEmail") != null && !"".equals(recordPrincipale.getAttribute("idEmail"))) {

			_layout = new InvioMailLayout(this, tipoRel, vm, null);

			Record lDetailRecord = new Record();
			lDetailRecord.setAttribute("id", recordPrincipale.getAttribute("id"));
			lDetailRecord.setAttribute("idEmail", recordPrincipale.getAttribute("idEmail"));
			lDetailRecord.setAttribute("oggetto", recordPrincipale.getAttribute("oggetto"));
			lDetailRecord.setAttribute("accountMittente", recordPrincipale.getAttribute("accountMittente"));
			lDetailRecord.setAttribute("tsInvio", recordPrincipale.getAttribute("tsInvio"));
			lDetailRecord.setAttribute("progrDownloadStampa", recordPrincipale.getAttribute("progrDownloadStampa"));
			lDetailRecord.setAttribute("aliasAccountMittente", aliasAccountMittente);
			
			String ccnTemp = "";
			if(recordPrincipale != null && recordPrincipale.getAttributeAsRecordList("listaDestinatariCCN") != null
					&& !recordPrincipale.getAttributeAsRecordList("listaDestinatariCCN").isEmpty()){
				for(int i=0; i<recordPrincipale.getAttributeAsRecordList("listaDestinatariCCN").getLength(); i++){
					Record item = recordPrincipale.getAttributeAsRecordList("listaDestinatariCCN").get(i);
					ccnTemp += item.getAttribute("indirizzo") + " ";
				}
			}
			final String ccn = ccnTemp;
		
			GWTRestDataSource lGwtRestDataSource = new GWTRestDataSource("AurigaLoadDettaglioEmailDataSource", "idEmail", FieldType.TEXT);
			lGwtRestDataSource.performCustomOperation("get", lDetailRecord, new DSCallback() {

				@Override
				public void execute(DSResponse response, Object rawData, DSRequest request) {
					if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
						final Record record = response.getData()[0];

						final Record lRecordNuovoInvio = new Record();

						lRecordNuovoInvio.setAttribute("idEmailUD", record.getAttribute("idEmail"));
						lRecordNuovoInvio.setAttribute("id", record.getAttribute("id"));
						lRecordNuovoInvio.setAttribute("idEmailPrincipale", record.getAttribute("idEmail"));
						lRecordNuovoInvio.setAttribute("uriMail", record.getAttribute("uriEmail"));
						lRecordNuovoInvio.setAttribute("oggetto", record.getAttribute("oggetto"));
						lRecordNuovoInvio.setAttribute("accountMittente", record.getAttribute("accountMittente"));
						lRecordNuovoInvio.setAttribute("tsInvio", record.getAttribute("tsInvio"));
						lRecordNuovoInvio.setAttribute("allegaEmlSbustato", allegaEmlSbustato);
						lRecordNuovoInvio.setAttribute("progrDownloadStampa", record.getAttribute("progrDownloadStampa"));
						lRecordNuovoInvio.setAttribute("oggetto", record.getAttribute("oggetto"));

						String dp = getAccount(record.getAttributeAsRecordList("destinatariPrincipali"));
						String dcc = getAccount(record.getAttributeAsRecordList("destinatariCC"));

						lRecordNuovoInvio.setAttribute("destinatari", dp);
						lRecordNuovoInvio.setAttribute("destinatariCC", dcc);
						lRecordNuovoInvio.setAttribute("destinatariCCN", ccn);

						RecordList listaAllegati = record.getAttributeAsRecordList("allegati");
						if (listaAllegati != null) {
							RecordList attachList = new RecordList();
							for (int i = 0; i < listaAllegati.getLength(); i++) {
								Record attach = new Record();
								attach.setAttribute("fileNameAttach", listaAllegati.get(i).getAttribute("nomeFileAllegato"));
								attach.setAttribute("infoFileAttach", listaAllegati.get(i).getAttributeAsRecord("infoFile"));
								attach.setAttribute("uriAttach", listaAllegati.get(i).getAttribute("uriFileAllegato"));
								attachList.add(attach);
							}
							lRecordNuovoInvio.setAttribute("attach", attachList);
						}

						if ("NIC".equals(_tipoInvio)) {
							// N.B idEmail non è di quella originale da cui faccio copia ma quella predecessoria di entrata se c'è
							lRecordNuovoInvio.setAttribute("listaIdEmailPredecessore", emailPredecessoreIdEmail);
						} else if ("RI".equals(_tipoInvio))
							lRecordNuovoInvio.setAttribute("idEmail", record.getAttribute("idEmail"));

						lRecordNuovoInvio.setAttribute("salvaInviati", "true");

						lRecordNuovoInvio.setAttribute("tipoRelCopia", _tipoRelCopia);

						lRecordNuovoInvio.setAttribute("aliasAccountMittente", record.getAttribute("aliasAccountMittente"));
						lRecordNuovoInvio.setAttribute("mittenteIniziale", record.getAttribute("accountMittente"));
						
						String corpoHtml = record.getAttribute("corpoHtml");
						String textBody = record.getAttribute("corpo");

						// qui non devo aggiungere la firma in calce perchè è un invio come copia
						if (corpoHtml != null && !"".equals(corpoHtml)) {
							lRecordNuovoInvio.setAttribute("bodyHtml", corpoHtml + "<br><br>");
						} else {
							lRecordNuovoInvio.setAttribute("bodyHtml", textBody + "<br><br>");
						}

						lRecordNuovoInvio.setAttribute("bodyText", textBody + "<br><br>");
						
						if(AurigaLayout.getParametroDBAsBoolean("SHOW_FIRME_IN_CALCE")){
							//Se sono nella modalità per cui devo visualizzare le firme in calce
							
							GWTRestDataSource corpoMailDataSource = new GWTRestDataSource("CorpoMailDataSource");
							//Pongo un extraparam per il valore della nuova firma e per la modalita
							corpoMailDataSource.extraparam.put("newSignature", AurigaLayout.getHtmlSignature("nuovo"));
							corpoMailDataSource.extraparam.put("modalita", "NUOVO_COME_COPIA");
							
							corpoMailDataSource.performCustomOperation("addSignature", lRecordNuovoInvio, new DSCallback() {

								@Override
								public void execute(DSResponse response, Object rawData, DSRequest request) {
									if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
										Record record = response.getData()[0];

										lRecordNuovoInvio.setAttribute("bodyHtml", record.getAttribute("bodyHtml"));
										
										// Altrimenti viene visualizzata una schermata vuota se si chiude prima che siano caricati i dati nelle rispettive text
										// if (_window.isVisible()) {
										_layout.getForm().caricaMail(lRecordNuovoInvio);
										_layout.getForm().clearErrors(true);
										_window.show();
										// }
									}
								}
							}, new DSRequest());
						} else{
							//Se sono in modalità per cui devo visualizzare la firma normale
							// Altrimenti viene visualizzata una schermata vuota se si chiude prima che siano caricati i dati nelle rispettive text
							// if (_window.isVisible()) {
							_layout.getForm().caricaMail(lRecordNuovoInvio);
							_layout.getForm().clearErrors(true);
							_window.show();
							// }
						}
					}
					// Chiusura della finestra di popup
					Layout.hideWaitPopup();
				}
			}, new DSRequest());
		}

		_layout.setHeight100();
		_layout.setWidth100();

	}

	public static String normalize(String fileName) {
		String newFileName = fileName;
		newFileName = newFileName.replace(" ", "_");
		newFileName = newFileName.replace("\\", "_");
		newFileName = newFileName.replace("/", "_");
		newFileName = newFileName.replace(":", "_");
		newFileName = newFileName.replace("*", "_");
		newFileName = newFileName.replace("?", "_");
		newFileName = newFileName.replace("<", "_");
		newFileName = newFileName.replace(">", "_");
		newFileName = newFileName.replace("|", "_");
		newFileName = newFileName.replace("\"", "_");
		return newFileName;
	}

	public String getAccount(RecordList destinatari) {
		String dp = "";

		if (destinatari != null) {
			for (int i = 0; i < destinatari.getLength(); i++) {
				dp += destinatari.get(i).getAttribute("account") + ";";
			}
		}
		return dp;
	}

	@Override
	public String getTitleWindow() {
		return titleWindow;
	}

	@Override
	public String getIconWindow() {
		return "postaElettronica/inoltro.png";
	}

	@Override
	public void setFileAsAllegato(Record record) {
	}

	@Override
	public void inviaMail(DSCallback callback) {
		_layout.inviaMail(callback, false);
	}

	@Override
	public void salvaBozza(DSCallback callback) {
		_layout.salvaBozza(callback, "N");
	}
	
	@Override
	protected VLayout createMainLayout() {

		VLayout mainLayout = super.createMainLayout();
		
		createMainToolstrip();

		mainLayout = new VLayout();
		mainLayout.setHeight100();
		mainLayout.setWidth100();
		
		mainLayout.addMember(mainToolStrip, 0);
		mainLayout.addMember(tabSetGenerale);
		mainLayout.addMember(detailToolStrip);

		return mainLayout;
	}

	private void createMainToolstrip() {

		if(AurigaLayout.getParametroDBAsBoolean("SHOW_FIRME_IN_CALCE")){
			//Creo la select relativa alla firma in calce
			createSelectFirmaInCalce();
		}
		// Creo la mainToolstrip e aggiungo le select impostate
		mainToolStrip = new ToolStrip();
		mainToolStrip.setBackgroundColor("transparent");
		mainToolStrip.setBackgroundImage("blank.png");
		mainToolStrip.setBackgroundRepeat(BackgroundRepeat.REPEAT_X);
		mainToolStrip.setBorder("0px");
		mainToolStrip.setWidth100();
		mainToolStrip.setHeight(30);

//		mainToolStrip.addFill(); // Per spostare a destra la nuova select

		if(AurigaLayout.getParametroDBAsBoolean("SHOW_FIRME_IN_CALCE")){
			mainToolStrip.addFormItem(firmaInCalceSelectItem);
			mainToolStrip.addButton(firmaPredefinitaButton);
		}
	}

	private void createSelectFirmaInCalce() {
		
		createFirmeDatasource();

		//Creo il pulsante per selezionare la firma predefinita
		firmaPredefinitaButton = new ToolStripButton("", "menu/firma_email.png");
		firmaPredefinitaButton.setPrompt(I18NUtil.getMessages().firme_in_calce_firmaPredefinita_title());
		/*
		 * L'immagine di background normalmente è già impostata.
		 * Ne setto una vuota così da non avere lo stile precedente ma per vedere in trasparenza 
		 * lo sfondo della finestra
		 */
//		firmaPredefinitaButton.setBackgroundImage("");
		firmaPredefinitaButton.addClickHandler(new com.smartgwt.client.widgets.events.ClickHandler() {

			@Override
			public void onClick(com.smartgwt.client.widgets.events.ClickEvent event) {
				replaceSignature();
			}
			
			private void replaceSignature() {

				if(_layout != null && _layout.getForm() != null){
					if(_layout.getForm().getStyleText().equals("text")){
						//Si sta considerando la modalità text
						
						Layout.addMessage(new MessageBean(I18NUtil.getMessages().firme_in_calce_modificaInTestoSemplice_warningMessage(), "", MessageType.WARNING));
						
					}else{
						// Ottengo la firma impostata come predefinita
						final String nomeFirmaPredefinita = AurigaLayout.getFirmaEmailPredefinita();
						String firmaPredefinita = nomeFirmaPredefinita != null && !nomeFirmaPredefinita.equals("") ? AurigaLayout.getFirmaEmailHtml(nomeFirmaPredefinita) : null;
		
						if (firmaPredefinita != null && !firmaPredefinita.equals("")) {
							// Prelevo i valori del form
							Map formValues = getMapValues();
		
							GWTRestDataSource corpoMailDataSource = new GWTRestDataSource("CorpoMailDataSource");
							// Pongo un extraparam per il valore della nuova firma
							corpoMailDataSource.extraparam.put("newSignature", firmaPredefinita);
							corpoMailDataSource.extraparam.put("modalita", "nuovaMail");
							corpoMailDataSource.performCustomOperation("replaceSignature", new Record(formValues), new DSCallback() {
		
								@Override
								public void execute(DSResponse response, Object rawData, DSRequest request) {
									if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
										Record record = response.getData()[0];
										editNewRecord(record.toMap());
										firmaInCalceSelectItem.setValue(nomeFirmaPredefinita);
									}
								}
							}, new DSRequest());
						}else{
							Layout.addMessage(new MessageBean(I18NUtil.getMessages().firme_in_calce_firmaPredefinitaNonPresente_warningMessage(), "", MessageType.WARNING));
						}
					}
				}
			}
		});
		
		// Creo la select
		firmaInCalceSelectItem = new SelectItem("firmaInCalce");
		firmaInCalceSelectItem.setValueField("prefName");
		firmaInCalceSelectItem.setDisplayField("prefName");
		firmaInCalceSelectItem.setTitle("<b>" + I18NUtil.getMessages().firme_in_calce_modelliSelectItem_title() + "</b>");
		firmaInCalceSelectItem.setWrapTitle(false); // In questo modo il titolo non viene mandato a capo
		firmaInCalceSelectItem.setCachePickListResults(false);
		firmaInCalceSelectItem.setRedrawOnChange(true);
		firmaInCalceSelectItem.setShowOptionsFromDataSource(true);
		firmaInCalceSelectItem.setOptionDataSource(firmeDS);
		firmaInCalceSelectItem.setAllowEmptyValue(true);

		firmaInCalcePickListProperties = new ListGrid();
		firmaInCalcePickListProperties.setEmptyMessage(I18NUtil.getMessages().list_emptyMessage());
		firmaInCalcePickListProperties.setShowHeader(false);
		// apply the selected preference from the SelectItem
		firmaInCalcePickListProperties.addCellClickHandler(new CellClickHandler() {

			@Override
			public void onCellClick(CellClickEvent event) {

				if(_layout != null && _layout.getForm() != null){
					if(_layout.getForm().getStyleText().equals("text")){
						//Si sta considerando la modalità text
						
						Layout.addMessage(new MessageBean(I18NUtil.getMessages().firme_in_calce_modificaInTestoSemplice_warningMessage(), "", MessageType.WARNING));
						
					}else{
						final String preferenceName = event.getRecord().getAttribute("prefName");
						if (preferenceName != null && !"".equals(preferenceName)) {
							AdvancedCriteria criteria = new AdvancedCriteria();
							criteria.addCriteria("prefName", OperatorId.EQUALS, preferenceName);
							firmeDS.fetchData(criteria, new DSCallback() {
		
								@Override
								public void execute(DSResponse response, Object rawData, DSRequest request) {
									Record[] data = response.getData();
									if (data.length != 0) {
										Record record = data[0];
										
										//Prelevo i valori del form
										Map formValues = getMapValues();
										
										GWTRestDataSource corpoMailDataSource = new GWTRestDataSource("CorpoMailDataSource");
										//Pongo un extraparam per il valore della nuova firma
										corpoMailDataSource.extraparam.put("newSignature", record.getAttributeAsString("value"));
										corpoMailDataSource.extraparam.put("modalita", "nuovaMail");
										corpoMailDataSource.performCustomOperation("replaceSignature", new Record(formValues), new DSCallback() {
		
											@Override
											public void execute(DSResponse response, Object rawData, DSRequest request) {
												if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
													Record record = response.getData()[0];
													
													editNewRecord(record.toMap());
												}
											}
										}, new DSRequest());
									}
								}
							});
						}
					}
				}
			}
		});
		// Inserisco la picklist
		firmaInCalceSelectItem.setPickListProperties(firmaInCalcePickListProperties);
	}

	private void createFirmeDatasource() {
		firmeDS = UserInterfaceFactory.getPreferenceDataSource();
		firmeDS.addParam("prefKey", Layout.getConfiguredPrefKeyPrefix() + "signature.email");
	}
	
	private Map getMapValues() {
		return vm.getValues();
	}
	
	private void editNewRecord(Map map) {
		vm.editRecord(new Record(map));
		_layout.getForm().filtroPresenteFromModello(map);
	}
}