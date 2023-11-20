/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gwt.user.client.Timer;
import com.smartgwt.client.data.DSCallback;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.data.RecordList;
import com.smartgwt.client.data.SortSpecifier;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.DragDataAction;
import com.smartgwt.client.types.ListGridEditEvent;
import com.smartgwt.client.types.ListGridFieldType;
import com.smartgwt.client.types.SortDirection;
import com.smartgwt.client.util.BooleanCallback;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.ImgButton;
import com.smartgwt.client.widgets.events.DropCompleteEvent;
import com.smartgwt.client.widgets.events.DropCompleteHandler;
import com.smartgwt.client.widgets.grid.CellFormatter;
import com.smartgwt.client.widgets.grid.HoverCustomizer;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.grid.ListGridRecord;
import com.smartgwt.client.widgets.grid.events.RecordClickEvent;
import com.smartgwt.client.widgets.grid.events.RecordClickHandler;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.menu.Menu;
import com.smartgwt.client.widgets.menu.MenuItem;
import com.smartgwt.client.widgets.menu.events.ClickHandler;
import com.smartgwt.client.widgets.menu.events.MenuItemClickEvent;

import it.eng.auriga.ui.module.layout.client.i18n.I18NUtil;
import it.eng.utility.ui.module.core.client.callback.ServiceCallback;
import it.eng.utility.ui.module.core.client.datasource.GWTRestDataSource;
import it.eng.utility.ui.module.core.shared.message.MessageBean;
import it.eng.utility.ui.module.core.shared.message.MessageType;
import it.eng.utility.ui.module.layout.client.Layout;
import it.eng.utility.ui.module.layout.client.common.ControlListGridField;
import it.eng.utility.ui.module.layout.client.common.CustomList;


public class ContenutiAmmTraspList extends CustomList {

	private ListGridField idSezioneField;
	private ListGridField idContenutoField;
	private ListGridField htmlContenutoField;
	private ListGridField flgValidoField;
	private ListGridField tipoContenutoField;
	private ListGridField titoloContenutoField;
	private ListGridField nroOrdineInSezFiled;
	private ListGridField statoRichiestaPubblicazioneField;
	private ListGridField flgContenutoEliminato;
	
	private ControlListGridField contenutiTabellaField;
	
	private static final String TIPO_CONTENUTO_FINE_SEZIONE = "fine_sezione";
	private static final String TIPO_CONTENUTO_PARAGRAFO = "paragrafo";
	private static final String TIPO_CONTENUTO_FILE_SEMPLICE = "file_semplice";
	private static final String TIPO_CONTENUTO_DOCUMENTO_COMPLESSO = "documento_complesso";
	private static final String TIPO_CONTENUTO_TABELLA = "tabella";
	private static final String TIPO_CONTENUTO_TITOLO_SEZIONE = "titolo_sezione";
	private static final String TIPO_CONTENUTO_HEADER = "header";
	private static final String TIPO_CONTENUTO_RIF_NORMATIVI = "rif_norm";
	
	private static final String STATO_RICHIESTA_PUBBL_DA_AUTORIZZARE = "da_autorizzare";
	private static final String STATO_RICHIESTA_PUBBL_AUTORIZZATA = "autorizzata";
	private static final String STATO_RICHIESTA_PUBBL_RESPINTA = "respinta";
	
	
	private Boolean showRifNormativiButton;
	private Boolean showHeaderButton;
	private Boolean showTitoloSezioneNewButton; 
	private Boolean showFineSezioneNewButton;
	private Boolean showParagrafoNewButton;
	private Boolean showDocumentoSempliceNewButton;
	private Boolean showDocumentoConAllegatiNewButton;
	private Boolean showTabellaNewButton;
	private Boolean showContenutiTabellaButton;
	
	public ContenutiAmmTraspList(final String nomeEntita) {
		this(nomeEntita, true, true, true, true, true, true, true, true,  false);
	}

	public ContenutiAmmTraspList(final String nomeEntita, 
			                     Boolean showRifNormativiButton,
			                     Boolean showHeaderButton,
			                     Boolean showTitoloSezioneNewButton, 
			                     Boolean showFineSezioneNewButton,
			                     Boolean showParagrafoNewButton,
			                     Boolean showDocumentoSempliceNewButton,
			                     Boolean showDocumentoConAllegatiNewButton,
			                     Boolean showTabellaNewButton,
			                     Boolean showContenutiTabellaButton) {

		super(nomeEntita);
		
		this.showRifNormativiButton = showRifNormativiButton; 
		this.showHeaderButton = showHeaderButton; 
		this.showTitoloSezioneNewButton = showTitoloSezioneNewButton;  
		this.showFineSezioneNewButton = showFineSezioneNewButton; 
		this.showParagrafoNewButton = showParagrafoNewButton; 
		this.showDocumentoSempliceNewButton = showDocumentoSempliceNewButton; 
		this.showDocumentoConAllegatiNewButton = showDocumentoConAllegatiNewButton; 
		this.showTabellaNewButton = showTabellaNewButton; 
		this.showContenutiTabellaButton = showContenutiTabellaButton;
		
		setCanReorderRecords(true);
		setShowAllRecords(true);
		setCanDragRecordsOut(true);  
		setCanAcceptDroppedRecords(true);  
		setDragDataAction(DragDataAction.MOVE); 
		setCanResizeFields(true);
		setEditEvent(ListGridEditEvent.CLICK);

		// hidden 
		idSezioneField     = new ListGridField("idSezione");      idSezioneField.setHidden(true);      idSezioneField.setCanHide(false);
		idContenutoField   = new ListGridField("idContenuto");    idContenutoField.setHidden(true);    idContenutoField.setCanHide(false);
		htmlContenutoField = new ListGridField("htmlContenuto");  htmlContenutoField.setHidden(true);  htmlContenutoField.setCanHide(false);
		flgValidoField     = new ListGridField("flgValido");      flgValidoField.setHidden(true);      flgValidoField.setCanHide(false);
		nroOrdineInSezFiled= new ListGridField("nroOrdineInSez"); nroOrdineInSezFiled.setHidden(true); nroOrdineInSezFiled.setCanHide(false);
		
		// visibili		
		tipoContenutoField = new ListGridField("tipoContenuto", I18NUtil.getMessages().contenuti_amministrazione_trasparente_list_tipoContenutoField_title());	
		tipoContenutoField.setType(ListGridFieldType.ICON);
		tipoContenutoField.setWidth(150);
		tipoContenutoField.setIconWidth(16);
		tipoContenutoField.setIconHeight(16);
		Map<String, String> flgValidoValueIcons = new HashMap<String, String>();		
		flgValidoValueIcons.put(TIPO_CONTENUTO_DOCUMENTO_COMPLESSO, "buttons/documentoConAllegati.png");
		flgValidoValueIcons.put(TIPO_CONTENUTO_FILE_SEMPLICE, "buttons/documentoSemplice.png");
		flgValidoValueIcons.put(TIPO_CONTENUTO_FINE_SEZIONE, "buttons/fineSezione.png");
		flgValidoValueIcons.put(TIPO_CONTENUTO_PARAGRAFO, "buttons/paragrafo.png");
		flgValidoValueIcons.put(TIPO_CONTENUTO_TABELLA, "buttons/tabella.png");
		flgValidoValueIcons.put(TIPO_CONTENUTO_TITOLO_SEZIONE, "buttons/titoloSezione.png");
		flgValidoValueIcons.put("", "blank.png");
		tipoContenutoField.setValueIcons(flgValidoValueIcons);		
		tipoContenutoField.setHoverCustomizer(new HoverCustomizer() {
			
			@Override
			public String hoverHTML(Object value, ListGridRecord record, int rowNum, int colNum) {

				if(TIPO_CONTENUTO_DOCUMENTO_COMPLESSO.equals(record.getAttribute("tipoContenuto"))) {
					return I18NUtil.getMessages().contenuti_amministrazione_trasparente_documentoConAllegatiMenuItem_title();
				}				
				else if(TIPO_CONTENUTO_FILE_SEMPLICE.equals(record.getAttribute("tipoContenuto"))) {
					return I18NUtil.getMessages().contenuti_amministrazione_trasparente_documentoSempliceMenuItem_title();
				}				
				else if(TIPO_CONTENUTO_FINE_SEZIONE.equals(record.getAttribute("tipoContenuto"))) {
					return I18NUtil.getMessages().contenuti_amministrazione_trasparente_fineSezioneMenuItem_title();
				}				
				else if(TIPO_CONTENUTO_PARAGRAFO.equals(record.getAttribute("tipoContenuto"))) {
					return I18NUtil.getMessages().contenuti_amministrazione_trasparente_paragrafoMenuItem_title();
				}				
				else if(TIPO_CONTENUTO_TABELLA.equals(record.getAttribute("tipoContenuto"))) {
					return I18NUtil.getMessages().contenuti_amministrazione_trasparente_tabellaMenuItem_title();
				}				
				else if(TIPO_CONTENUTO_TITOLO_SEZIONE.equals(record.getAttribute("tipoContenuto"))) {
					return I18NUtil.getMessages().contenuti_amministrazione_trasparente_titoloSezioneMenuItem_title();
				}				
				return null;
			}
		});	
		
		
		// Stato richiesta pubblicazione
		statoRichiestaPubblicazioneField = new ListGridField("statoRichiestaPubblicazione", I18NUtil.getMessages().contenuti_amministrazione_trasparente_list_statoRichiestaPubblicazioneField_title());	
		statoRichiestaPubblicazioneField.setType(ListGridFieldType.ICON);
		statoRichiestaPubblicazioneField.setWidth(150);
		statoRichiestaPubblicazioneField.setIconWidth(16);
		statoRichiestaPubblicazioneField.setIconHeight(16);
		
		Map<String, String> flgStatoRichiestaPubblicazioneValueIcons = new HashMap<String, String>();		
		flgStatoRichiestaPubblicazioneValueIcons.put(STATO_RICHIESTA_PUBBL_DA_AUTORIZZARE, "richiesteAccessoAtti/canalearrivo_vuoto.png");
		flgStatoRichiestaPubblicazioneValueIcons.put(STATO_RICHIESTA_PUBBL_AUTORIZZATA, "agibilita/accolta.png");
		flgStatoRichiestaPubblicazioneValueIcons.put(STATO_RICHIESTA_PUBBL_RESPINTA, "agibilita/respinta.png");
		flgStatoRichiestaPubblicazioneValueIcons.put("", "blank.png");
		statoRichiestaPubblicazioneField.setValueIcons(flgStatoRichiestaPubblicazioneValueIcons);		
		
		statoRichiestaPubblicazioneField.setHoverCustomizer(new HoverCustomizer() {
			
			@Override
			public String hoverHTML(Object value, ListGridRecord record, int rowNum, int colNum) {

				if(STATO_RICHIESTA_PUBBL_DA_AUTORIZZARE.equals(record.getAttribute("statoRichiestaPubblicazione"))) {
					return I18NUtil.getMessages().contenuti_amministrazione_trasparente_statoRichiestaPubblicazioneDaAutorizzareIcon_title();
				}				
				else if(STATO_RICHIESTA_PUBBL_AUTORIZZATA.equals(record.getAttribute("statoRichiestaPubblicazione"))) {
					return I18NUtil.getMessages().contenuti_amministrazione_trasparente_statoRichiestaPubblicazioneAutorizzataIcon_title();
				}				
				else if(STATO_RICHIESTA_PUBBL_RESPINTA.equals(record.getAttribute("statoRichiestaPubblicazione"))) {
					return I18NUtil.getMessages().contenuti_amministrazione_trasparente_statoRichiestaPubblicazioneRespintaIcon_title();
				}				
				return null;
			}
		});	

		
		titoloContenutoField = new ListGridField("titoloContenuto", I18NUtil.getMessages().contenuti_amministrazione_trasparente_list_titoloContenutoField_title());
		titoloContenutoField.setAttribute("custom", true);
		titoloContenutoField.setCellAlign(Alignment.LEFT);
		titoloContenutoField.setCellFormatter(new CellFormatter() {
			
			@Override
			public String format(Object value, ListGridRecord record, int rowNum, int colNum) {
				String titoloContenuto = (String) record.getAttribute("titoloContenuto");
				if (titoloContenuto == null) titoloContenuto="";
				if (titoloContenuto.equalsIgnoreCase("")){
					String htmlContenuto = (String) record.getAttribute("htmlContenuto");
					if (htmlContenuto == null) htmlContenuto="";
					if(!htmlContenuto.equalsIgnoreCase("")){
						if (htmlContenuto.length()>1000){
							htmlContenuto = htmlContenuto.substring(0,1000) + "...";
						}
					}
					titoloContenuto = htmlContenuto;
				}
                return 	titoloContenuto;			
			}
		});
		
		flgContenutoEliminato = new ListGridField("flgContenutoEliminato");
		flgContenutoEliminato.setType(ListGridFieldType.ICON);
		flgContenutoEliminato.setIconWidth(16);
		flgContenutoEliminato.setIconHeight(16);
		flgContenutoEliminato.setWidth(30);
		Map<String, String> flgContenutoEliminatoValueIcons = new HashMap<String, String>();
		flgContenutoEliminatoValueIcons.put("1", "ko.png");
//		flgContenutoEliminatoValueIcons.put("0", "blank.png");
		flgContenutoEliminato.setValueIcons(flgContenutoEliminatoValueIcons);
		flgContenutoEliminato.setHoverCustomizer(new HoverCustomizer() {

			@Override
			public String hoverHTML(Object value, ListGridRecord record, int rowNum, int colNum) {
				if ("1".equals(record.getAttribute("flgContenutoEliminato"))) {
					return "contenuto eliminato";
				}
				return null;
			}
		});
		
		setFields(// Hidden
				  idSezioneField,
				  idContenutoField,
				  flgValidoField,
				  nroOrdineInSezFiled,
				  
				  // Visibili
				  tipoContenutoField,
				  statoRichiestaPubblicazioneField,
				  titoloContenutoField,
				  flgContenutoEliminato
				  );
		
		addDropCompleteHandler(new DropCompleteHandler() {
			
			@Override
			public void onDropComplete(DropCompleteEvent event) {
				
				ListGridRecord[] listGrid = getRecords();
				
				Record recordToOrd = new Record();
				RecordList listContenuti = new RecordList();
				String idSezione = null;
				for(int i=0; i < listGrid.length; i++) {
					Record item = listGrid[i];
					idSezione = item.getAttributeAsString("idSezione");
					Record recordContenuto = new Record();
					recordContenuto.setAttribute("idContenuto", item.getAttributeAsString("idContenuto"));
					recordContenuto.setAttribute("nrOrdine", i);
					
					listContenuti.add(recordContenuto);
				}
				
				recordToOrd.setAttribute("idSezione", idSezione);
				recordToOrd.setAttribute("listContenuti", listContenuti);
				
				((ContenutiAmmTraspLayout) layout).setRecordToOrd(recordToOrd);
			}
		});
		
	}

	@Override  
	protected int getButtonsFieldWidth() {
		return 100;
	}

	@Override
	public void manageContextClick(final Record record) {
		if (record != null) {
			
			boolean flgAbilAutorizzarePubblicazione = record.getAttribute("flgAbilAutorizzarePubblicazione") != null && record.getAttributeAsBoolean("flgAbilAutorizzarePubblicazione");
			boolean flgAbilRespingerePubblicazione  = record.getAttribute("flgAbilRespingerePubblicazione") != null  && record.getAttributeAsBoolean("flgAbilRespingerePubblicazione");
		
			if( flgAbilAutorizzarePubblicazione ||
				flgAbilRespingerePubblicazione  ||
					((ContenutiAmmTraspLayout) layout).isShowRifNormativiButton() != null            && ((ContenutiAmmTraspLayout) layout).isShowRifNormativiButton() ||					
				    ((ContenutiAmmTraspLayout) layout).isShowHeaderButton() != null                  && ((ContenutiAmmTraspLayout) layout).isShowHeaderButton() ||
				    ((ContenutiAmmTraspLayout) layout).isShowTitoloSezioneNewButton() != null        && ((ContenutiAmmTraspLayout) layout).isShowTitoloSezioneNewButton() ||
				    ((ContenutiAmmTraspLayout) layout).isShowFineSezioneNewButton() != null          && ((ContenutiAmmTraspLayout) layout).isShowFineSezioneNewButton() ||
				    ((ContenutiAmmTraspLayout) layout).isShowParagrafoNewButton() != null            && ((ContenutiAmmTraspLayout) layout).isShowParagrafoNewButton() ||
				    ((ContenutiAmmTraspLayout) layout).isShowDocumentoSempliceNewButton() != null    && ((ContenutiAmmTraspLayout) layout).isShowDocumentoSempliceNewButton() ||
				    ((ContenutiAmmTraspLayout) layout).isShowDocumentoConAllegatiNewButton() != null && ((ContenutiAmmTraspLayout) layout).isShowDocumentoConAllegatiNewButton() ||
				    ((ContenutiAmmTraspLayout) layout).isShowTabellaNewButton() != null              && ((ContenutiAmmTraspLayout) layout).isShowTabellaNewButton() 
			   ) {
				showRowContextMenu(getRecord(getRecordIndex(record)));
			}
		}
	}

	public void showRowContextMenu(final ListGridRecord record) {
		final Menu contextMenu = createContextMenu(record);
		contextMenu.showContextMenu();
	}
	
	public Menu createContextMenu(final ListGridRecord listRecord) {

		Menu contextMenu = new Menu();
		
		// Creo le voci del sub menu' "Aggiungi contenuto successivo"
		final Integer numOrdine = getRowNum(listRecord)+1;

		// -> Titolo sezione 
		MenuItem titoloSezioneMenuItem = new MenuItem(I18NUtil.getMessages().contenuti_amministrazione_trasparente_titoloSezioneMenuItem_title(), "buttons/titoloSezione.png");		
		titoloSezioneMenuItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {

			@Override
			public void onClick(MenuItemClickEvent event) {
				manageSezioneClick(TIPO_CONTENUTO_TITOLO_SEZIONE, numOrdine, false, null, null);
			}
		});
		
		// -> Fine sezione
		MenuItem fineSezioneMenuItem = new MenuItem(I18NUtil.getMessages().contenuti_amministrazione_trasparente_fineSezioneMenuItem_title(),"buttons/fineSezione.png");
		fineSezioneMenuItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {

			@Override
			public void onClick(MenuItemClickEvent event) {
				manageFineSezioneClick(numOrdine, false, null, null);
			}
		});
		
		// -> Paragrafo		
		MenuItem paragrafoMenuItem = new MenuItem(I18NUtil.getMessages().contenuti_amministrazione_trasparente_paragrafoMenuItem_title(), "buttons/paragrafo.png");
		paragrafoMenuItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {

			@Override
			public void onClick(MenuItemClickEvent event) {
				manageSezioneClick(TIPO_CONTENUTO_PARAGRAFO, numOrdine, false, null, null);
			}
		});
		
		// -> Documento semplice
		MenuItem documentoSempliceMenuItem = new MenuItem(I18NUtil.getMessages().contenuti_amministrazione_trasparente_documentoSempliceMenuItem_title(), "buttons/documentoSemplice.png");
		documentoSempliceMenuItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {

			@Override
			public void onClick(MenuItemClickEvent event) {
				manageSezioneClick(TIPO_CONTENUTO_FILE_SEMPLICE, numOrdine, false, null, null);
			}
		});
		
		// -> Documento con allegati
		MenuItem documentoConAllegatiMenuItem = new MenuItem(I18NUtil.getMessages().contenuti_amministrazione_trasparente_documentoConAllegatiMenuItem_title(), "buttons/documentoConAllegati.png");
		documentoConAllegatiMenuItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {

			@Override
			public void onClick(MenuItemClickEvent event) {
				manageSezioneClick(TIPO_CONTENUTO_DOCUMENTO_COMPLESSO, numOrdine, false, null, null);
			}
		});

		// -> Tabella 
		MenuItem tabellaMenuItem = new MenuItem(I18NUtil.getMessages().contenuti_amministrazione_trasparente_tabellaMenuItem_title(), "buttons/tabella.png");		
		tabellaMenuItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {

			@Override
			public void onClick(MenuItemClickEvent event) {
				manageSezioneClick(TIPO_CONTENUTO_TABELLA, numOrdine, false, null, null);
			}
		});
		
		// Creo il sub menu' "Aggiungi contenuto successivo"
		Menu aggiungiContenutoSuccessivoSubMenuItem = new Menu();
				
		// Inserisco nel sub menu' le voci
		if ((showTitoloSezioneNewButton != null && showTitoloSezioneNewButton))
			aggiungiContenutoSuccessivoSubMenuItem.addItem(titoloSezioneMenuItem);
		
		if ((showFineSezioneNewButton != null && showFineSezioneNewButton))
			aggiungiContenutoSuccessivoSubMenuItem.addItem(fineSezioneMenuItem);
		
		if ((showParagrafoNewButton != null && showParagrafoNewButton))
			aggiungiContenutoSuccessivoSubMenuItem.addItem(paragrafoMenuItem);
		
		if ((showDocumentoSempliceNewButton != null && showDocumentoSempliceNewButton))
			aggiungiContenutoSuccessivoSubMenuItem.addItem(documentoSempliceMenuItem);
		
		if ((showDocumentoConAllegatiNewButton != null && showDocumentoConAllegatiNewButton))
			aggiungiContenutoSuccessivoSubMenuItem.addItem(documentoConAllegatiMenuItem);
		
		if ((showTabellaNewButton !=null && showTabellaNewButton))
			aggiungiContenutoSuccessivoSubMenuItem.addItem(tabellaMenuItem);
		
		// Creo la voce di menu' "Aggiungi contenuto successivo"
		MenuItem aggiungiContenutoSuccessivoMenuItem = new MenuItem(I18NUtil.getMessages().contenuti_amministrazione_trasparente_aggiungiContenutoSuccessivoMenuItem_title(), "buttons/add.png");
		
		// Inserisco il sub menu' nel menu' "Aggiungi contenuto successivo"
		aggiungiContenutoSuccessivoMenuItem.setSubmenu(aggiungiContenutoSuccessivoSubMenuItem);
		
		// Inserisco il menu' "Aggiungi contenuto successivo" nel context-menu'
		
		//if(aggiungiContenutoSuccessivoMenuItem)
		contextMenu.addItem(aggiungiContenutoSuccessivoMenuItem);
		
		
		boolean flgAbilAutorizzarePubblicazione = listRecord.getAttribute("flgAbilAutorizzarePubblicazione") != null && listRecord.getAttributeAsBoolean("flgAbilAutorizzarePubblicazione");
		boolean flgAbilRespingerePubblicazione  = listRecord.getAttribute("flgAbilRespingerePubblicazione") != null  && listRecord.getAttributeAsBoolean("flgAbilRespingerePubblicazione");
		boolean flgAbilModificaContenuto        = listRecord.getAttribute("flgAbilModificaContenuto") != null  && listRecord.getAttributeAsBoolean("flgAbilModificaContenuto");
		
		// Creo la voce di menu' "Autorizza richiesta pubbl."
		MenuItem autorizzaRichiestaPubblicazioneMenuItem = new MenuItem(I18NUtil.getMessages().contenuti_amministrazione_trasparente_autorizzaRichiestaPubblicazioneMenuItem_title(), "attiInLavorazione/azioni/rilascioVisto.png");
		autorizzaRichiestaPubblicazioneMenuItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {

			@Override
			public void onClick(MenuItemClickEvent event) {
				Record record = new Record();
				record.setAttribute("idContenuto", listRecord.getAttribute("idContenuto"));
				
				final GWTRestDataSource lDataSource = new GWTRestDataSource("ContenutiAmmTraspDatasource");
				lDataSource.addParam("statoAuotorizzRich", "autorizzo");
				lDataSource.getData(record, new DSCallback() {
					
					@Override
					public void execute(DSResponse response, Object rawData, DSRequest request) {
						if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
							Record recordDettaglio = response.getData()[0];
							String tipoContenuto = recordDettaglio.getAttribute("tipoContenuto");	
							Integer nroOrdineInSez = getRowNum(listRecord);
							((ContenutiAmmTraspLayout) layout).manageSezioneClick(tipoContenuto, nroOrdineInSez, true, recordDettaglio, listRecord);
						}
					}
				});
			}
		});
		
		// Inserisco il menu' "Autorizza richiesta pubbl." nel context-menu'
		if (flgAbilAutorizzarePubblicazione)
			contextMenu.addItem(autorizzaRichiestaPubblicazioneMenuItem);
				
						
		// Creo il menu' principale "Respingi richiesta pubbl."
		MenuItem respingiRichiestaPubblicazioneMenuItem = new MenuItem(I18NUtil.getMessages().contenuti_amministrazione_trasparente_respingiRichiestaPubblicazioneMenuItem_title(), "agibilita/respinta.png");
		respingiRichiestaPubblicazioneMenuItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {

			@Override
			public void onClick(MenuItemClickEvent event) {
				Record record = new Record();
				record.setAttribute("idContenuto", listRecord.getAttribute("idContenuto"));
				
				final GWTRestDataSource lDataSource = new GWTRestDataSource("ContenutiAmmTraspDatasource");
				lDataSource.addParam("statoAuotorizzRich", "respingo");
				lDataSource.getData(record, new DSCallback() {
					
					@Override
					public void execute(DSResponse response, Object rawData, DSRequest request) {
						if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
							Record recordDettaglio = response.getData()[0];
							String tipoContenuto = recordDettaglio.getAttribute("tipoContenuto");	
							Integer nroOrdineInSez = getRowNum(listRecord);
							((ContenutiAmmTraspLayout) layout).manageSezioneClick(tipoContenuto, nroOrdineInSez, true, recordDettaglio, listRecord);
						}
					}
				});
			}
		});

		// Inserisco il menu' "Respingi richiesta pubbl." nel context-menu'
		if (flgAbilRespingerePubblicazione)
			contextMenu.addItem(respingiRichiestaPubblicazioneMenuItem);
		
		
		// Creo il menu' principale "Taglia"
		MenuItem tagliaContenutoMenuItem = new MenuItem(I18NUtil.getMessages().contenuti_amministrazione_trasparente_tagliaContenutoMenuItem_title(), "buttons/cut.png");
		tagliaContenutoMenuItem.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(MenuItemClickEvent event) {
				Record cutRecord = new Record(listRecord.toMap());
				((ContenutiAmmTraspLayout) layout).setCutNodeContenuto(cutRecord);
			}
		});
				
		// Inserisco il menu' "Taglia" nel context-menu'
		if (flgAbilModificaContenuto)
			contextMenu.addItem(tagliaContenutoMenuItem);
		
		contextMenu.addSort(new SortSpecifier("title", SortDirection.ASCENDING));
		return contextMenu;
	}

	protected void manageFineSezioneClick(Integer numOrdine, boolean modify, final Record record, final ListGridRecord listRecord) {
		((ContenutiAmmTraspLayout) layout).manageFineSezioneClick(numOrdine, listRecord);
	}

	public void manageSezioneClick(String tipoContenuto, Integer numOrdine, boolean modify, final Record record, final ListGridRecord listRecord){
		((ContenutiAmmTraspLayout) layout).manageSezioneClick(tipoContenuto, numOrdine, modify, record, listRecord);
	}
	
	// Definisco i bottoni DETTAGLIO/MODIFICA/CANCELLA/SELEZIONA
	@Override  
	protected Canvas createFieldCanvas(String fieldName, final ListGridRecord record) {
								
		Canvas lCanvasReturn = null;
		
		if (fieldName.equals("buttons")) {	
								
			ImgButton detailButton = buildDetailButton(record);  
			ImgButton modifyButton = buildModifyButton(record);  
			ImgButton deleteButton = buildDeleteButton(record);  
			ImgButton lookupButton = buildLookupButton(record);						
			
			if(!isRecordAbilToMod(record)) {	
				modifyButton.disable();			
			}			
			
			if(!isRecordAbilToDel(record)) {	
				deleteButton.disable();			
			}
			
			// creo la colonna BUTTON
			HLayout recordCanvas = new HLayout(3);
			recordCanvas.setHeight(22);   
			recordCanvas.setWidth(getButtonsFieldWidth());
			recordCanvas.setAlign(Alignment.RIGHT);
			recordCanvas.setLayoutRightMargin(3);   
			recordCanvas.setMembersMargin(7);
			
			recordCanvas.addMember(detailButton);			
			recordCanvas.addMember(modifyButton);
			recordCanvas.addMember(deleteButton);		
			
			if(layout.isLookup()) {
				if(!isRecordSelezionabileForLookup(record)) {
					lookupButton.disable();
				}
				recordCanvas.addMember(lookupButton);		// aggiungo il bottone SELEZIONA				
			}
			lCanvasReturn = recordCanvas;	
		}			
		return lCanvasReturn;
	}
	
	@Override
	protected void manageModifyButtonClick(final ListGridRecord listRecord) {
		Record record = new Record();
		record.setAttribute("idContenuto", listRecord.getAttribute("idContenuto"));
		
		final GWTRestDataSource lDataSource = new GWTRestDataSource("ContenutiAmmTraspDatasource");
		lDataSource.getData(record, new DSCallback() {
			
			@Override
			public void execute(DSResponse response, Object rawData, DSRequest request) {
				if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
					Record recordDettaglio = response.getData()[0];
					String tipoContenuto = recordDettaglio.getAttribute("tipoContenuto");	
					Integer nroOrdineInSez = getRowNum(listRecord);
					((ContenutiAmmTraspLayout) layout).manageSezioneClick(tipoContenuto, nroOrdineInSez, true, recordDettaglio, listRecord);
				}
			}
		});
	}
	
	@Override
	protected void manageDetailButtonClick(final ListGridRecord listRecord) {

		Record record = new Record();
		record.setAttribute("idContenuto", listRecord.getAttribute("idContenuto"));
		record.setAttribute("idSezione", listRecord.getAttributeAsString("idSezione"));
		
		final GWTRestDataSource lDataSource = new GWTRestDataSource("ContenutiAmmTraspDatasource");
		lDataSource.getData(record, new DSCallback() {
			
			@Override
			public void execute(DSResponse response, Object rawData, DSRequest request) {
				if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
					Record recordDettaglio = response.getData()[0];
					String tipoContenuto = recordDettaglio.getAttribute("tipoContenuto");
					recordDettaglio.setAttribute("abilitazioni_modifica", isRecordAbilToMod(listRecord));
					Integer nroOrdineInSez = getRowNum(listRecord);
					((ContenutiAmmTraspLayout) layout).manageSezioneClick(tipoContenuto, nroOrdineInSez, false, recordDettaglio, listRecord);
				}
			}
		});	
	}
	
	@Override
	protected void manageDeleteButtonClick(final ListGridRecord record) {
		
		SC.ask("Sei sicuro di voler procedere all'eliminazione del contenuto ?" , new BooleanCallback() {		
			
			@Override
			public void execute(Boolean value) {				
				if(value) {	
					
					MotivazioneCancellazionePopup motivazioneCancellazionePopup = new MotivazioneCancellazionePopup("Motivo cancellazione") {
						
						@Override
						public void onClickOkButton(Record object, final DSCallback callback) {
							record.setAttribute("motivoCancellazione", object.getAttribute("motivoCancellazione"));	
							removeData(record, new DSCallback() {
								
								@Override
								public void execute(final DSResponse response,Object rawData, DSRequest request) {
									if(response.getStatus() == DSResponse.STATUS_SUCCESS) {
										if(record.getAttributeAsString("tipoContenuto") != null && 
												"paragrafo".equalsIgnoreCase(record.getAttributeAsString("tipoContenuto"))) {
											Layout.addMessage(new MessageBean("Cancellazione paragrafo avvenuta con successo", "", MessageType.INFO));
										} else {
											Layout.addMessage(new MessageBean(I18NUtil.getMessages().afterDelete_message(layout.getTipoEstremiRecord(record)), "", MessageType.INFO));
										}									
									} 					
									Timer t1 = new Timer() {
										public void run() {
											layout.doSearch();
											callback.execute(response, null, new DSRequest());			
										}
									};
									t1.schedule(1000);														
								}
							});	
						}
					};
					motivazioneCancellazionePopup.show();	
				}
			}
		});  
	}
	
	/********************************NUOVA GESTIONE CONTROLLI BOTTONI********************************/	
	@Override
	protected List<ControlListGridField> getButtonsFields() {
		List<ControlListGridField> buttonsFields = super.getButtonsFields();

		if (showContenutiTabellaField()){
			if (contenutiTabellaField == null) {
				contenutiTabellaField = buildContenutiTabellaField();
			}
			buttonsFields.add(contenutiTabellaField);
		}

		return buttonsFields;
	}
	
	protected boolean showContenutiTabellaField() {
		return ContenutiAmmTraspLayout.isAbilToContenutiTabella();
	}	
	
	@Override
	public boolean isDisableRecordComponent() {
		return true;
	}
	
	@Override
	protected boolean showDetailButtonField() {
		return true;
	}

	@Override
	protected boolean showModifyButtonField() {
		boolean flgToAbil = (showRifNormativiButton != null && showRifNormativiButton) ||
		                    (showHeaderButton != null && showHeaderButton) ||
		                    (showTitoloSezioneNewButton != null && showTitoloSezioneNewButton) ||  
		                    (showFineSezioneNewButton != null && showFineSezioneNewButton) ||
		                    (showParagrafoNewButton != null && showParagrafoNewButton) ||
		                    (showDocumentoSempliceNewButton != null && showDocumentoSempliceNewButton) || 
		                    (showDocumentoConAllegatiNewButton != null && showDocumentoConAllegatiNewButton) || 
		                    (showTabellaNewButton !=null && showTabellaNewButton); 
		
		return ContenutiAmmTraspLayout.isAbilToMod() && (flgToAbil);
	}

	@Override
	protected boolean showDeleteButtonField() {
		boolean flgToAbil = (showRifNormativiButton != null && showRifNormativiButton) ||
                            (showHeaderButton != null && showHeaderButton) ||
                            (showTitoloSezioneNewButton != null && showTitoloSezioneNewButton) ||  
                            (showFineSezioneNewButton != null && showFineSezioneNewButton) ||
                            (showParagrafoNewButton != null && showParagrafoNewButton) ||
                            (showDocumentoSempliceNewButton != null && showDocumentoSempliceNewButton) || 
                            (showDocumentoConAllegatiNewButton != null && showDocumentoConAllegatiNewButton) || 
                            (showTabellaNewButton !=null && showTabellaNewButton);
		
		return ContenutiAmmTraspLayout.isAbilToDel() && (flgToAbil);
	}
	
	@Override
	protected boolean isRecordAbilToView(ListGridRecord record) {
		final boolean flgTipoContenuto  = record.getAttribute("tipoContenuto") != null && !record.getAttributeAsString("tipoContenuto").equalsIgnoreCase("fine_sezione");
		return flgTipoContenuto;
	}
	
	@Override
	protected boolean isRecordAbilToMod(ListGridRecord record) {
		
		final boolean flgAbilModificaContenuto  = record.getAttribute("flgAbilModificaContenuto") != null  && record.getAttributeAsBoolean("flgAbilModificaContenuto");
		
		boolean result = false;
			
		// Se e' abilitato mostro il contenuto TIPO_CONTENUTO_FINE_SEZIONE
		if (record.getAttribute("tipoContenuto") != null && TIPO_CONTENUTO_FINE_SEZIONE.equals(record.getAttribute("tipoContenuto"))){
			result = false;
		}
	
		// Se e' abilitato mostro il contenuto TIPO_CONTENUTO_PARAGRAFO
		if (record.getAttribute("tipoContenuto") != null && TIPO_CONTENUTO_PARAGRAFO.equals(record.getAttribute("tipoContenuto"))){
			if ((showParagrafoNewButton != null && showParagrafoNewButton))
				result = true;
		}

		// Se e' abilitato mostro il contenuto TIPO_CONTENUTO_FILE_SEMPLICE
		if (record.getAttribute("tipoContenuto") != null && TIPO_CONTENUTO_FILE_SEMPLICE.equals(record.getAttribute("tipoContenuto"))){
			if ((showDocumentoSempliceNewButton != null && showDocumentoSempliceNewButton))
				result = true;
		}

		// Se e' abilitato mostro il contenuto TIPO_CONTENUTO_DOCUMENTO_COMPLESSO
		if (record.getAttribute("tipoContenuto") != null && TIPO_CONTENUTO_DOCUMENTO_COMPLESSO.equals(record.getAttribute("tipoContenuto"))){
			if ((showDocumentoConAllegatiNewButton != null && showDocumentoConAllegatiNewButton))
				result = true;
		}

		// Se e' abilitato mostro il contenuto TIPO_CONTENUTO_TABELLA
		if (record.getAttribute("tipoContenuto") != null && TIPO_CONTENUTO_TABELLA.equals(record.getAttribute("tipoContenuto"))){
			if ((showTabellaNewButton != null && showTabellaNewButton))
				result = true;
		}

		// Se e' abilitato mostro il contenuto TIPO_CONTENUTO_TITOLO_SEZIONE
		if (record.getAttribute("tipoContenuto") != null && TIPO_CONTENUTO_TITOLO_SEZIONE.equals(record.getAttribute("tipoContenuto"))){
			if ((showTitoloSezioneNewButton != null && showTitoloSezioneNewButton))
				result = true;
		}

		// Se e' abilitato mostro il contenuto TIPO_CONTENUTO_HEADER
		if (record.getAttribute("tipoContenuto") != null && TIPO_CONTENUTO_HEADER.equals(record.getAttribute("tipoContenuto"))){
			if ((showHeaderButton != null && showHeaderButton))
				result = true;
		}
		
		// Se e' abilitato mostro il contenuto TIPO_CONTENUTO_RIF_NORMATIVI
		if (record.getAttribute("tipoContenuto") != null && TIPO_CONTENUTO_RIF_NORMATIVI.equals(record.getAttribute("tipoContenuto"))){
			if ((showRifNormativiButton != null && showRifNormativiButton))
				result = true;
		}
			
		return ContenutiAmmTraspLayout.isRecordAbilToMod(flgAbilModificaContenuto) && result;		
	}

	@Override
	protected boolean isRecordAbilToDel(ListGridRecord record) {
				
		final boolean flgValido  = record.getAttribute("flgValido") != null && record.getAttributeAsString("flgValido").equalsIgnoreCase("1");
		final boolean flgAbilEliminaContenuto  = record.getAttribute("flgAbilEliminaContenuto") != null  && record.getAttributeAsBoolean("flgAbilEliminaContenuto");				
		
		boolean result = false;
		
		// Il contenuto TIPO_CONTENUTO_FINE_SEZIONE non puo' essere cancellato
		if (record.getAttribute("tipoContenuto") != null && TIPO_CONTENUTO_FINE_SEZIONE.equals(record.getAttribute("tipoContenuto"))){
			result = false;
		}
	
		// Se il bottone PARAGRAFO e' attivo, mostro il bottone di cancellazione
		if (record.getAttribute("tipoContenuto") != null && TIPO_CONTENUTO_PARAGRAFO.equals(record.getAttribute("tipoContenuto"))){
			if ((showParagrafoNewButton != null && showParagrafoNewButton))
				result = true;
		}

		// Se il bottone CONTENUTO DOCUMENTO SEMPLICE e' attivo, mostro il bottone di cancellazione
		if (record.getAttribute("tipoContenuto") != null && TIPO_CONTENUTO_FILE_SEMPLICE.equals(record.getAttribute("tipoContenuto"))){
			if ((showDocumentoSempliceNewButton != null && showDocumentoSempliceNewButton))
				result = true;
		}

		// Se il bottone CONTENUTO DOCUMENTO COMPLESSO e' attivo, mostro il bottone di cancellazione
		if (record.getAttribute("tipoContenuto") != null && TIPO_CONTENUTO_DOCUMENTO_COMPLESSO.equals(record.getAttribute("tipoContenuto"))){
			if ((showDocumentoConAllegatiNewButton != null && showDocumentoConAllegatiNewButton))
				result = true;
		}

		// Se il bottone TABELLA e' attivo, mostro il bottone di cancellazione
		if (record.getAttribute("tipoContenuto") != null && TIPO_CONTENUTO_TABELLA.equals(record.getAttribute("tipoContenuto"))){
			if ((showTabellaNewButton != null && showTabellaNewButton))
				result = true;
		}

		// Se il bottone TITOLO SEZIONE e' attivo, mostro il bottone di cancellazione		
		if (record.getAttribute("tipoContenuto") != null && TIPO_CONTENUTO_TITOLO_SEZIONE.equals(record.getAttribute("tipoContenuto"))){
			if ((showTitoloSezioneNewButton != null && showTitoloSezioneNewButton))
				result = true;
		}

		// Se il bottone HEADER e' attivo, mostro il bottone di cancellazione
		if (record.getAttribute("tipoContenuto") != null && TIPO_CONTENUTO_HEADER.equals(record.getAttribute("tipoContenuto"))){
			if ((showHeaderButton != null && showHeaderButton))
				result = true;
		}
		

		// Se il bottone RIF.NORMATIVI e' attivo, mostro il bottone di cancellazione
		if (record.getAttribute("tipoContenuto") != null && TIPO_CONTENUTO_RIF_NORMATIVI.equals(record.getAttribute("tipoContenuto"))){
			if ((showRifNormativiButton != null && showRifNormativiButton))
				result = true;
		}
		
		return ContenutiAmmTraspLayout.isRecordAbilToDel(flgValido, flgAbilEliminaContenuto) && result;
	}	
	
	
	/********************************FINE NUOVA GESTIONE CONTROLLI BOTTONI********************************/
	
	protected void manageContenutiTabellaButtonClick(final ListGridRecord lRecord) {		
		
		new SceltaContenutiDaVisualizzarePopup() {
			
			@Override
			public void onClickOkButton(Record object) {
				
				String flgRecordFuoriPubbl = null;
				String statoContenuti = object.getAttribute("statoContenuti");
				if (statoContenuti!=null && statoContenuti.equalsIgnoreCase("anche_fuori_corso")){
					flgRecordFuoriPubbl = "1";
				}

				lRecord.setAttribute("flgRecordFuoriPubbl", flgRecordFuoriPubbl);
				
				loadContenutiDaVisualizzare(lRecord, new ServiceCallback<Record>() {
					@Override
					public void execute(final Record recordContenutiDaVisualizzare) {
						if (recordContenutiDaVisualizzare != null) {
							
							// Leggo il valore  FlgAbilAuotorizzRichOut
							getFlgAbilAutorizzRich(lRecord, new ServiceCallback<Record>() {
								@Override
								public void execute(Record recordFlgAbilAutorizzRich) {
									if (recordFlgAbilAutorizzRich != null) {
										String flgAbilAutorizzRich = recordFlgAbilAutorizzRich.getAttribute("flgAbilAuotorizzRich");
										// se FlgAbilAuotorizzRichOut=1 => mode = "edit".
										// se FlgAbilAuotorizzRichOut=0 => mode = "view" 
										String mode = "";
										if (flgAbilAutorizzRich!=null && flgAbilAutorizzRich.equalsIgnoreCase("1"))
											mode = "edit";
										else
											mode = "view";
										    
										previewContenutiDaVisualizzare(recordContenutiDaVisualizzare, lRecord, mode);	
									}
								}
							});
						}
					}
				});
			}
		};
	}
	
	
	private void getFlgAbilAutorizzRich(final Record lRecord, final ServiceCallback<Record> callback) {
		
		final GWTRestDataSource lGWTRestDataSource = new GWTRestDataSource("ContenutiAmmTraspDatasource");
		lGWTRestDataSource.executecustom("leggiFlgAbilAutorizzRich", lRecord, new DSCallback() {						
			@Override
			public void execute(DSResponse response, Object rawData, DSRequest request) {
				if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
					if (response.getData() != null) {
						Record infoDatiContTabella = response.getData()[0];
						if (infoDatiContTabella != null) {
							if(callback != null) {
								Record result = response.getData()[0];
								callback.execute(result);
							}	
						}
					} 
				}
			}
		});
		
	}
	
	private void loadContenutiDaVisualizzare(final Record lRecord, final ServiceCallback<Record> callback) {
		
		final GWTRestDataSource lGWTRestDataSource = new GWTRestDataSource("ContenutiAmmTraspDatasource");
		lGWTRestDataSource.executecustom("leggiDatiContTabella", lRecord, new DSCallback() {						
			@Override
			public void execute(DSResponse response, Object rawData, DSRequest request) {
				if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
					if (response.getData() != null) {
						Record infoDatiContTabella = response.getData()[0];
						if (infoDatiContTabella != null) {
							if(callback != null) {
								Record result = response.getData()[0];
								if (result != null) {
									result.setAttribute("flgRecordFuoriPubbl", lRecord.getAttribute("flgRecordFuoriPubbl"));
								}
								callback.execute(result);
							}	
						}
					} 
				}
			}
		});
	}
	
	private void previewContenutiDaVisualizzare(Record recordContenutiDaVisualizzare, ListGridRecord listRecord, String mode){
		String idContenuto = listRecord.getAttribute("idContenuto");
		String flgRecordFuoriPubbl = recordContenutiDaVisualizzare.getAttribute("flgRecordFuoriPubbl");
		
		Record attr = new Record();
		attr.setAttribute("nome", "tabellaAmmTrasparente");
		attr.setAttribute("flgRecordFuoriPubbl", flgRecordFuoriPubbl);
		attr.setAttribute("idContenuto", idContenuto);
		
		RecordList datiDettLista = recordContenutiDaVisualizzare.getAttributeAsRecordList("listaDettColonnaAttributo");
		RecordList valoriAttrLista = recordContenutiDaVisualizzare.getAttributeAsRecordList("valoriAttrLista");
		String nroRecTotali = recordContenutiDaVisualizzare.getAttribute("nroRecTotali");
		
		Record values = new Record();
		values.setAttribute("tabellaAmmTrasparente", valoriAttrLista);
				
		DatiContenutiTabellaPopup popup = new DatiContenutiTabellaPopup(attr, datiDettLista, null, mode, nroRecTotali);
		popup.setValues(values);
		popup.show();
	}

	protected ControlListGridField buildContenutiTabellaField() {
		ControlListGridField contenutiTabellaButtonField = new ControlListGridField("contenutiTabellaButton");  
		contenutiTabellaButtonField.setAttribute("custom", true);	
		contenutiTabellaButtonField.setShowHover(true);		
		contenutiTabellaButtonField.setCanReorder(false);
		contenutiTabellaButtonField.setCellFormatter(new CellFormatter() {			
			@Override
			public String format(Object value, ListGridRecord record, int rowNum, int colNum) {			
				if(isRecordAbilToContenutiTabella(record)) {
					return buildImgButtonHtml("visualizzaDati.png");			
				} 
				return null;				
			}
		});
		contenutiTabellaButtonField.setHoverCustomizer(new HoverCustomizer() {				
			@Override
			public String hoverHTML(Object value, ListGridRecord record, int rowNum, int colNum) {
				if(isRecordAbilToContenutiTabella(record)) {
					return getContenutiTabellaButtonPrompt();
				}
				return null;
			}
		});		
		contenutiTabellaButtonField.addRecordClickHandler(new RecordClickHandler() {				
			@Override
			public void onRecordClick(RecordClickEvent event) {
				event.cancel();
				ListGridRecord record = getRecord(event.getRecordNum());
				if(isRecordAbilToContenutiTabella(record)) {
					manageContenutiTabellaButtonClick(record);
				}
			}
		});		
		return contenutiTabellaButtonField;
	}
	
	protected boolean isRecordAbilToContenutiTabella(ListGridRecord record) {
		final boolean showContenutiTabellaButton  = this.showContenutiTabellaButton && TIPO_CONTENUTO_TABELLA.equals(record.getAttribute("tipoContenuto"));
		boolean result = ContenutiAmmTraspLayout.isRecordAbilToContenutiTabella(showContenutiTabellaButton);
		return result;
	}
	
	protected String getContenutiTabellaButtonPrompt() {
		return "Accedi ai contenuti tabella";
	}
}