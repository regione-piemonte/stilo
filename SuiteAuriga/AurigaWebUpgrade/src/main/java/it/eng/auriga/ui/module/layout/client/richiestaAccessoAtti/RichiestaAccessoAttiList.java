/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.google.gwt.core.client.Scheduler;
import com.google.gwt.core.client.Scheduler.ScheduledCommand;
import com.smartgwt.client.data.DSCallback;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.data.RecordList;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.DateDisplayFormat;
import com.smartgwt.client.types.FetchMode;
import com.smartgwt.client.types.ListGridFieldType;
import com.smartgwt.client.types.SelectionStyle;
import com.smartgwt.client.util.BooleanCallback;
import com.smartgwt.client.util.JSON;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.grid.CellFormatter;
import com.smartgwt.client.widgets.grid.HoverCustomizer;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.grid.ListGridRecord;
import com.smartgwt.client.widgets.menu.Menu;
import com.smartgwt.client.widgets.menu.MenuItem;
import com.smartgwt.client.widgets.menu.events.MenuItemClickEvent;

import it.eng.auriga.ui.module.layout.client.AurigaLayout;
import it.eng.auriga.ui.module.layout.client.gestioneatti.AttiLayout;
import it.eng.auriga.ui.module.layout.client.gestioneatti.SmistamentoAttiPopup;
import it.eng.auriga.ui.module.layout.client.i18n.I18NUtil;
import it.eng.auriga.ui.module.layout.client.pratiche.dettaglio.frontoffice.items.CustomTaskButton;
import it.eng.auriga.ui.module.layout.client.pratiche.dettaglio.nuovapropostaatto2.TaskNuovaPropostaAtto2CompletaDetail;
import it.eng.auriga.ui.module.layout.client.print.PreviewControl;
import it.eng.auriga.ui.module.layout.client.scrivania.ScrivaniaLayout;
import it.eng.auriga.ui.module.layout.client.scrivania.ShowInMenuFunctionFromScrivania;
import it.eng.utility.ui.module.core.client.callback.ServiceCallback;
import it.eng.utility.ui.module.core.client.datasource.GWTRestDataSource;
import it.eng.utility.ui.module.core.client.datasource.GWTRestService;
import it.eng.utility.ui.module.core.shared.message.MessageBean;
import it.eng.utility.ui.module.core.shared.message.MessageType;
import it.eng.utility.ui.module.layout.client.Layout;
import it.eng.utility.ui.module.layout.client.common.ControlListGridField;
import it.eng.utility.ui.module.layout.client.common.CustomList;
import it.eng.utility.ui.module.layout.client.common.file.InfoFileRecord;
import it.eng.utility.ui.module.layout.client.menu.MenuUtil;
import it.eng.utility.ui.module.layout.client.menu.ShowInMenuFunction;

/**
 * 
 * @author DANCRIST
 *
 */

public class RichiestaAccessoAttiList extends CustomList { 
	
	private final int ALT_POPUP_ERR_MASS = 300;
	private final int LARG_POPUP_ERR_MASS = 600;
	
	private ListGridField idUd;
	private ListGridField protocollo;
	private ListGridField dataProtocollo;
	private ListGridField numUffRichiedente;
	private ListGridField richEsterno;
	private ListGridField indirizzo;
	private ListGridField richRegistrataDa;
	private ListGridField dataInvioApprovazione;
	private ListGridField dataApprovazione;
	private ListGridField richApprovataDa;
	private ListGridField dataEsitoCittadella;
	private ListGridField dataAppuntamento;
	private ListGridField dataPrelievo;
	private ListGridField statoPrelievo;
	private ListGridField udc;
	private ListGridField estremiAttiRichiesti;
	private ListGridField attiNonPresentiInCittadella;
	private ListGridField datiNotifica;
	
	private String idNode;
	
	public RichiestaAccessoAttiList(String nomeEntita) {
		this(nomeEntita, null);
	}

	public RichiestaAccessoAttiList(String nomeEntita, String idNode) {
		
		super(nomeEntita);
		
		setAutoFetchData(false);
		setDataFetchMode(FetchMode.BASIC);	
		setSelectionType(SelectionStyle.NONE);
		
		this.idNode = idNode;
		
		idUd = new ListGridField("idUd");
		idUd.setHidden(true);
		idUd.setCanHide(false);
		
		protocollo = new ListGridField("protocolloONumRichiesta", I18NUtil.getMessages().richiestaAccessoAtti_list_protocollo_title());
		
		dataProtocollo = new ListGridField("dataProtocolloORichiesta", I18NUtil.getMessages().richiestaAccessoAtti_list_dataProtocollo_title());
		dataProtocollo.setType(ListGridFieldType.DATE);
		dataProtocollo.setDateFormatter(DateDisplayFormat.TOEUROPEANSHORTDATE);
		dataProtocollo.setWrap(false);
				
		numUffRichiedente = new ListGridField("numUffRichiedente", I18NUtil.getMessages().richiestaAccessoAtti_list_numUffRichiedente_title());
		
		richEsterno = new ListGridField("richEsterno", I18NUtil.getMessages().richiestaAccessoAtti_list_richEsterno_title());

		indirizzo = new ListGridField("indirizzo", I18NUtil.getMessages().richiestaAccessoAtti_list_indirizzo_title());
		
		richRegistrataDa = new ListGridField("richRegistrataDa", I18NUtil.getMessages().richiestaAccessoAtti_list_richRegistrataDa_title());
		
		dataInvioApprovazione = new ListGridField("dataInvioApprovazione", I18NUtil.getMessages().richiestaAccessoAtti_list_dataInvioApprovazione_title());
		dataInvioApprovazione.setType(ListGridFieldType.DATE);
		dataInvioApprovazione.setDateFormatter(DateDisplayFormat.TOEUROPEANSHORTDATE);
		dataInvioApprovazione.setWrap(false);

		dataApprovazione = new ListGridField("dataApprovazione", I18NUtil.getMessages().richiestaAccessoAtti_list_dataApprovazione_title());
		dataApprovazione.setType(ListGridFieldType.DATE);
		dataApprovazione.setDateFormatter(DateDisplayFormat.TOEUROPEANSHORTDATE);
		dataApprovazione.setWrap(false);
		
		richApprovataDa = new ListGridField("richApprovataDa", I18NUtil.getMessages().richiestaAccessoAtti_list_richApprovataDa_title());
		
		dataEsitoCittadella = new ListGridField("dataEsitoCittadella", I18NUtil.getMessages().richiestaAccessoAtti_list_dataEsitoCittadella_title());
		dataEsitoCittadella.setType(ListGridFieldType.DATE);
		dataEsitoCittadella.setDateFormatter(DateDisplayFormat.TOEUROPEANSHORTDATE);
		dataEsitoCittadella.setWrap(false);
	
		dataAppuntamento = new ListGridField("dataAppuntamento", I18NUtil.getMessages().richiestaAccessoAtti_list_dataAppuntamento_title());
		dataAppuntamento.setType(ListGridFieldType.DATE);
		dataAppuntamento.setDateFormatter(DateDisplayFormat.TOEUROPEANSHORTDATETIME);
		dataAppuntamento.setWrap(false);

		dataPrelievo = new ListGridField("dataPrelievo", I18NUtil.getMessages().richiestaAccessoAtti_list_dataPrelievo_title());
		dataPrelievo.setType(ListGridFieldType.DATE);
		dataPrelievo.setDateFormatter(DateDisplayFormat.TOEUROPEANSHORTDATE);
		dataPrelievo.setWrap(false);

		statoPrelievo = new ListGridField("statoPrelievo", I18NUtil.getMessages().richiestaAccessoAtti_list_statoPrelievo_title());
		
		udc = new ListGridField("udc", I18NUtil.getMessages().richiestaAccessoAtti_list_udc_title());
		
		estremiAttiRichiesti = new ListGridField("elencoProtocolliAttiRich", I18NUtil.getMessages().richiestaAccessoAtti_list_elencoProtocolliAttiRich_title());
		
		// Atti non presenti in cittadellla
		attiNonPresentiInCittadella = new ListGridField("attiNonPresentiInCittadella", I18NUtil.getMessages().richiestaAccessoAtti_list_attiNonPresentiInCittadella_title());
		attiNonPresentiInCittadella.setHeaderBaseStyle(it.eng.utility.Styles.hiddenHeaderButton);
		attiNonPresentiInCittadella.setType(ListGridFieldType.ICON);
		attiNonPresentiInCittadella.setAlign(Alignment.CENTER);
		attiNonPresentiInCittadella.setWrap(false);
		attiNonPresentiInCittadella.setWidth(30);
		attiNonPresentiInCittadella.setIconWidth(16);
		attiNonPresentiInCittadella.setIconHeight(16);
		attiNonPresentiInCittadella.setCellFormatter(new CellFormatter() {

			@Override
			public String format(Object value, ListGridRecord record, int rowNum, int colNum) {

				if (value != null && "1".equalsIgnoreCase((String) value)) {
					return buildIconHtml("richiesteAccessoAtti/atti_non_presenti_in_cittadella.png");
				}

				return null;
			}
		});
		attiNonPresentiInCittadella.setHoverCustomizer(new HoverCustomizer() {

			@Override
			public String hoverHTML(Object value, ListGridRecord record, int rowNum, int colNum) {
				
				if (record != null && record.getAttribute("attiNonPresentiInCittadella") != null) {
					String attiNonPresentiInCittadella = (String) record.getAttribute("attiNonPresentiInCittadella");
					if (attiNonPresentiInCittadella != null && "1".equalsIgnoreCase(attiNonPresentiInCittadella)) {
						return I18NUtil.getMessages().richiestaAccessoAtti_list_attiNonPresentiInCittadella_warning();
					}
				}
				return null;
			}
		});
		
		datiNotifica = new ListGridField("datiNotifica", I18NUtil.getMessages().richiestaAccessoAtti_list_datiNotifica_title());
		
		setFields(
			idUd,
			protocollo,
			dataProtocollo,
			numUffRichiedente,
			richEsterno,
			indirizzo,
			richRegistrataDa,
			dataInvioApprovazione,
			dataApprovazione,
			richApprovataDa,
			dataEsitoCittadella,
			dataAppuntamento,
			dataPrelievo,
			statoPrelievo,
			udc,
			estremiAttiRichiesti,
			attiNonPresentiInCittadella,
			datiNotifica
		);
		
	}
	
	@Override
	public void setFields(ListGridField... fields) {
		for (final ListGridField field : fields) {
			String fieldName = field.getName();
			
			if (idNode == null || (!idNode.equals("D.RAA.DV") && !idNode.equals("D.RAA.ADF") && !idNode.equals("D.RAA.AF") && !idNode.equals("D.RAA.N") && !idNode.equals("D.RAA.EN"))) {
				if ("dataApprovazione".equals(fieldName)) {
					field.setHidden(true);
					field.setCanHide(false);
				}
				if ("richApprovataDa".equals(fieldName)) {
					field.setHidden(true);
					field.setCanHide(false);
				}
			}
			if (idNode == null || (!idNode.equals("D.RAA.DV") && !idNode.equals("D.RAA.ADF") && !idNode.equals("D.RAA.AF") && !idNode.equals("D.RAA.N"))) {
				if ("udc".equals(fieldName)) {
					field.setHidden(true);
					field.setCanHide(false);
				}
			}
			if (idNode == null || (!idNode.equals("D.RAA.ADF") && !idNode.equals("D.RAA.AF") && !idNode.equals("D.RAA.N") && !idNode.equals("D.RAA.EN"))) {
				if ("dataEsitoCittadella".equals(fieldName)) {
					field.setHidden(true);
					field.setCanHide(false);
				}
			}
			if (idNode == null || (!idNode.equals("D.RAA.ADF") && !idNode.equals("D.RAA.AF") && !idNode.equals("D.RAA.N"))) {
				if ("attiNonPresentiInCittadella".equals(fieldName)) {
					field.setHidden(true);
					field.setCanHide(false);
				}
			}
			if (idNode == null || !idNode.equals("D.RAA.DA")) {
				if ("dataInvioApprovazione".equals(fieldName)) {
					field.setHidden(true);
					field.setCanHide(false);
				}
			}
			if (idNode == null || !idNode.equals("D.RAA.AF")) {
				if ("statoPrelievo".equals(fieldName)) {
					field.setHidden(true);
					field.setCanHide(false);
				}
				if ("dataPrelievo".equals(fieldName)) {
					field.setHidden(true);
					field.setCanHide(false);
				}
				if ("dataAppuntamento".equals(fieldName)) {
					field.setHidden(true);
					field.setCanHide(false);
				}
			}
			if (idNode == null || !idNode.equals("D.RAA.N")) {
				if ("datiNotifica".equals(fieldName)) {
					field.setHidden(true);
					field.setCanHide(false);
				}
			}
		}
		super.setFields(fields);
		Scheduler.get().scheduleDeferred(new ScheduledCommand() {

			@Override
			public void execute() {
				try {
					refreshFields();
				} catch (Exception e) {
				}
				markForRedraw();
			}
		});
	}
	
	@Override
	protected ShowInMenuFunction[] getShowInMenuFunction() {
		
		if (getLayout() != null && (getLayout() instanceof ScrivaniaLayout)) {
			
			ShowInMenuFunctionFromScrivania lShowInMenuFunctionURBANISTICA = new ShowInMenuFunctionFromScrivania(((ScrivaniaLayout) getLayout()), new ListGridField[] { 
					protocollo,
					dataProtocollo,
					numUffRichiedente,
					richEsterno,
					indirizzo,
					richRegistrataDa,
					dataInvioApprovazione,
					dataApprovazione,
					richApprovataDa,
					dataEsitoCittadella,
					dataAppuntamento,
					dataPrelievo,
					statoPrelievo,
					udc,
					estremiAttiRichiesti,
					attiNonPresentiInCittadella					
			}, this, "idNode",  "equals", new String[] { "D.RAA.I", "D.RAA.DA", "D.RAA.DV", "D.RAA.ADF" , "D.RAA.AF" , "D.RAA.N", "D.RAA.EN" });
			
			ShowInMenuFunctionFromScrivania lShowInMenuFunctionNotifica = new ShowInMenuFunctionFromScrivania(((ScrivaniaLayout) getLayout()), new ListGridField[] { 
					datiNotifica
			}, this, "idNode",  "equals", new String[] { "D.RAA.N" });
			
			return new ShowInMenuFunction[] { lShowInMenuFunctionURBANISTICA, lShowInMenuFunctionNotifica };
		}
		
		return new ShowInMenuFunction[] { };
	}
	
	protected MenuItem[] getHeaderContextMenuItems(Integer fieldNum) {
		
		MenuItem[] lMenuItems = super.getHeaderContextMenuItems(fieldNum);
		if (lMenuItems == null)
			return null;
		// Recupero i reali menuItem[]
		MenuItem[] lMenuItemsRetrieved = MenuUtil.retrieveMenuFromJavascriptMenu(lMenuItems);
		MenuItem lMenuItemColonne = retrieveColonne(lMenuItemsRetrieved);
		if (lMenuItemColonne != null) {
			// Recupero la posizione del menu Colonne
			int posColonne = lMenuItemColonne.getAttributeAsInt("position");
			// Recupero il submenu di Colonne
			MenuItem[] lSubmenuItemsColonne = MenuUtil.retrieveSubMenuFromJavascriptMenu(lMenuItemColonne);
			List<MenuItem> lSubmenuItemsColonneList = new ArrayList<MenuItem>(lSubmenuItemsColonne.length);
			int toShow = 0;
			for (MenuItem lSubmenuItem : lSubmenuItemsColonne) {
				String name = lSubmenuItem.getTitle();				
				if (idNode != null && idNode.startsWith("D.RAA.")) {					
					if (name.equals(protocollo.getTitle()) ||
						name.equals(dataProtocollo.getTitle()) ||
						name.equals(numUffRichiedente.getTitle()) ||
						name.equals(indirizzo.getTitle()) ||
						name.equals(richEsterno.getTitle()) ||
						name.equals(richRegistrataDa.getTitle()) ||
						name.equals(estremiAttiRichiesti.getTitle())){							
						lSubmenuItemsColonneList.add(lSubmenuItem);
						toShow++;
					}
				}										
				if (idNode != null && (idNode.equals("D.RAA.DV") || idNode.equals("D.RAA.ADF") || idNode.equals("D.RAA.AF") || idNode.equals("D.RAA.N") || idNode.equals("D.RAA.EN"))) {
					if (name.equals(dataApprovazione.getTitle())){
						lSubmenuItemsColonneList.add(lSubmenuItem);
						toShow++;
					}
					if (name.equals(richApprovataDa.getTitle())){
						lSubmenuItemsColonneList.add(lSubmenuItem);
						toShow++;
					}
				}
				if (idNode != null && (idNode.equals("D.RAA.DV") || idNode.equals("D.RAA.ADF") || idNode.equals("D.RAA.AF") || idNode.equals("D.RAA.N"))) {
						if (name.equals(udc.getTitle())){
							lSubmenuItemsColonneList.add(lSubmenuItem);
							toShow++;
						}
				}
				if (idNode != null && (idNode.equals("D.RAA.ADF") || idNode.equals("D.RAA.AF") || idNode.equals("D.RAA.N") || idNode.equals("D.RAA.EN"))) {
					if (name.equals(dataEsitoCittadella.getTitle())){
						lSubmenuItemsColonneList.add(lSubmenuItem);
						toShow++;
					}
				}			
				if (idNode != null && (idNode.equals("D.RAA.ADF") || idNode.equals("D.RAA.AF") || idNode.equals("D.RAA.N"))) {
					if (name.equals(attiNonPresentiInCittadella.getTitle())){
						lSubmenuItemsColonneList.add(lSubmenuItem);
						toShow++;
					}					
				}
				if (idNode != null && idNode.equals("D.RAA.DA")) {
					if (name.equals(dataInvioApprovazione.getTitle())){
							lSubmenuItemsColonneList.add(lSubmenuItem);
							toShow++;
					}
				}
				if (idNode != null && idNode.equals("D.RAA.AF")) {
					if (name.equals(dataAppuntamento.getTitle()) ||
						name.equals(dataPrelievo.getTitle()) ||
						name.equals(statoPrelievo.getTitle())){
							lSubmenuItemsColonneList.add(lSubmenuItem);
							toShow++;
					}
				}
				if (idNode != null && idNode.equals("D.RAA.N")) {
					if (name.equals(datiNotifica.getTitle())){
							lSubmenuItemsColonneList.add(lSubmenuItem);
							toShow++;
					}
				}
			}
			MenuItem[] lSubmenuItemsColonneNew = new MenuItem[toShow];
			for (int count = 0; count < toShow; count++) {
				lSubmenuItemsColonneNew[count] = lSubmenuItemsColonneList.get(count);
			}
			Menu lSubmenuColonneNew = new Menu();
			lSubmenuColonneNew.setItems(lSubmenuItemsColonneNew);
			lMenuItems[posColonne].setSubmenu(lSubmenuColonneNew);
		}
		final ListGridField field = super.getField(fieldNum);
		if (field.getGroupingModes() != null && field.getGroupingModes().keySet().size() > 0) {
			// Recupero il menu Raggruppa per
			MenuItem lMenuItemRaggruppaPer = retrieveRaggruppaPer(lMenuItemsRetrieved);
			if (lMenuItemRaggruppaPer != null) {
				// Recupero la posizione del menu Raggruppa per
				int posRaggruppaPer = lMenuItemRaggruppaPer.getAttributeAsInt("position");
				MenuItem[] lSubmenuItemsRaggruppaPer = new MenuItem[field.getGroupingModes().keySet().size()];
				int cont = 0;
				for (Object key : field.getGroupingModes().keySet()) {
					final String groupingMode = (String) key;
					lSubmenuItemsRaggruppaPer[cont] = new MenuItem((String) field.getGroupingModes().get(key));
					lSubmenuItemsRaggruppaPer[cont].addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {

						@Override
						public void onClick(MenuItemClickEvent event) {
							field.setGroupingMode(groupingMode);
							instance.groupBy(field.getName());
						}
					});
					cont++;
				}
				Menu lSubmenuRaggruppaPer = new Menu();
				lSubmenuRaggruppaPer.setItems(lSubmenuItemsRaggruppaPer);
				lMenuItems[posRaggruppaPer].setSubmenu(lSubmenuRaggruppaPer);
			}
		}
		return lMenuItems;
	}
	
	/********************************NUOVA GESTIONE CONTROLLI BOTTONI********************************/
	@Override
	public boolean isDisableRecordComponent() {
		return true;
	};
	
	@Override
	protected List<ControlListGridField> getButtonsFields() {
		List<ControlListGridField> buttonsFields = super.getButtonsFields();
		if (altreOpButtonField == null) {
			altreOpButtonField = buildAltreOpButtonField();
		}
		buttonsFields.add(0, altreOpButtonField);
		return buttonsFields;
	}
	 
	 @Override
	protected boolean showDetailButtonField() {
		return true;
	}
	   
	
	 @Override
	protected void manageAltreOpButtonClick(final ListGridRecord record) {
		showRowContextMenu(record);
	}	
	/********************************FINE NUOVA GESTIONE CONTROLLI BOTTONI********************************/
	
	@Override
	public void manageLoadDetail(final Record record, final int recordNum, final DSCallback callback) {
		final GWTRestDataSource lGwtRestDataSource = new GWTRestDataSource("ProtocolloDataSource");
		Record lRecordToLoad = new Record();
		lRecordToLoad.setAttribute("idUd", record.getAttribute("idUd"));
		lGwtRestDataSource.getData(lRecordToLoad, new DSCallback() {

			@Override
			public void execute(DSResponse response, Object rawData, DSRequest request) {
				if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
					Record record = response.getData()[0];
					// Controllo se la richiesta accesso atti è collegata ad un flusso, e se fa ancora parte della vecchi agestione senza flusso
					if (record.getAttributeAsString("idProcess") != null && !record.getAttributeAsString("idProcess").equals("")) {
						apriDettaglioProcesso(record.getAttributeAsString("idProcess"), 
								record.getAttributeAsString("ruoloSmistamento"),
								record.getAttributeAsString("idUd"),
								record.getAttributeAsString("estremiProcess"),
					            true,
					            new BooleanCallback() {
									
									@Override
									public void execute(Boolean value) {
										// Se ho fatto modifiche ricarico la lista
										if (value && layout != null) {
											layout.reloadList();
										} 
									}
								});
					} else {
						layout.getDetail().editRecord(record, recordNum);
						layout.getDetail().getValuesManager().clearErrors(true);
						layout.getDetail().markForRedraw();
						// Questa callback mette il layout associato alla lista in viewmode, 
						// la eseguo solo se non apro il dettaglio di un processo (quindi ho idProcess a null)
						callback.execute(response, null, new DSRequest());
					}
				}				
			}
		});
	} 
	
	@Override
	public void manageContextClick(final Record record) {
		if (record != null) {
			showRowContextMenu(getRecord(getRecordIndex(record)));
		}
	}
	
	public void showRowContextMenu(final ListGridRecord record) {
		GWTRestDataSource lGwtRestDataSourceProtocollo = new GWTRestDataSource("ProtocolloDataSource");
		lGwtRestDataSourceProtocollo.addParam("flgSoloAbilAzioni", "1");
		Record lRecordToLoad = new Record();
		lRecordToLoad.setAttribute("idUd", record.getAttribute("idUd"));
		lGwtRestDataSourceProtocollo.getData(lRecordToLoad, new DSCallback() {

			@Override
			public void execute(DSResponse response, Object rawData, DSRequest request) {
				final Menu altreOpMenu = createUdAltreOpMenu(record, response.getData()[0]);
				altreOpMenu.showContextMenu();
			}
		});
	}
	
	public Menu createUdAltreOpMenu(final ListGridRecord listRecord, final Record detailRecord) {
		Menu altreOpMenu = new Menu();
		MenuItem visualizzaMenuItem = new MenuItem("Visualizza", "buttons/detail.png");
		visualizzaMenuItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {

			@Override
			public void onClick(MenuItemClickEvent event) {
				manageDetailButtonClick(listRecord);
			}
		});
		altreOpMenu.addItem(visualizzaMenuItem);
		if (detailRecord.getAttributeAsBoolean("abilModificaDati")) {
			MenuItem modificaMenuItem = new MenuItem("Modifica", "buttons/modify.png");
			modificaMenuItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {

				@Override
				public void onClick(MenuItemClickEvent event) {
					manageModifyButtonClick(listRecord); 
				}
			});
			altreOpMenu.addItem(modificaMenuItem);
		}
		
		if(detailRecord.getAttributeAsBoolean("abilInvioInApprovazione")){
			MenuItem invioInApprovazioneMenuItem = new MenuItem("Manda in approvazione", "richiesteAccessoAtti/azioni/invioInApprovazione.png");
			invioInApprovazioneMenuItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {
	
				@Override
				public void onClick(MenuItemClickEvent event) {
					manageAzioneSuRichAccessoAttiSingola("INVIO_IN_APPROVAZIONE",listRecord);
				}
			});
			altreOpMenu.addItem(invioInApprovazioneMenuItem);
		}
		if(detailRecord.getAttributeAsBoolean("abilApprovazione")){
			MenuItem approvazioneMenuItem = new MenuItem("Approva", "richiesteAccessoAtti/azioni/approvazione.png");
			approvazioneMenuItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {
	
				@Override
				public void onClick(MenuItemClickEvent event) {
					manageAzioneSuRichAccessoAttiSingola("APPROVAZIONE",listRecord);
				}
			});
			altreOpMenu.addItem(approvazioneMenuItem);
		}
		if(detailRecord.getAttributeAsBoolean("abilInvioEsitoVerificaArchivio")){
			MenuItem invioEsitoVerificaArchivioMenuItem = new MenuItem("Invia esito verifica al rich.", "richiesteAccessoAtti/azioni/invioEsitoVerificaArchivio.png");
			invioEsitoVerificaArchivioMenuItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {
	
				@Override
				public void onClick(MenuItemClickEvent event) {
					manageAzioneSuRichAccessoAttiSingola("INVIO_ESITO_VERIFICA_ARCHIVIO",listRecord);
				}
			});
			altreOpMenu.addItem(invioEsitoVerificaArchivioMenuItem);
		}
		if(detailRecord.getAttributeAsBoolean("abilAbilitaAppuntamentoDaAgenda")){
			MenuItem abilitaAppuntamentoDaAgendaMenuItem = new MenuItem("Abilita appuntamento da Agenda", "richiesteAccessoAtti/azioni/abilitaAppuntamentoDaAgenda.png");
			abilitaAppuntamentoDaAgendaMenuItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {
	
				@Override
				public void onClick(MenuItemClickEvent event) {
					manageAzioneSuRichAccessoAttiSingola("ABILITA_APPUNTAMENTO_DA_AGENDA",listRecord);
				}
			});
			altreOpMenu.addItem(abilitaAppuntamentoDaAgendaMenuItem);
		}
		if(detailRecord.getAttributeAsBoolean("abilSetAppuntamento")){
			MenuItem setAppuntamentoMenuItem = new MenuItem("Fissa appuntamento", "richiesteAccessoAtti/azioni/setAppuntamento.png");
			setAppuntamentoMenuItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {
	
				@Override
				public void onClick(MenuItemClickEvent event) {
					manageAzioneSuRichAccessoAttiSingola("SET_APPUNTAMENTO",listRecord);
				}
			});
			altreOpMenu.addItem(setAppuntamentoMenuItem);
		}
		if(detailRecord.getAttributeAsBoolean("abilAnnullamentoAppuntamento")){
			MenuItem annullamentoAppuntamentoMenuItem = new MenuItem("Annulla appuntamento", "richiesteAccessoAtti/azioni/annullamentoAppuntamento.png");
			annullamentoAppuntamentoMenuItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {
	
				@Override
				public void onClick(MenuItemClickEvent event) {
					manageAzioneSuRichAccessoAttiSingola("ANNULLAMENTO_APPUNTAMENTO",listRecord);
				}
			});
			altreOpMenu.addItem(annullamentoAppuntamentoMenuItem);
		}
		if(detailRecord.getAttributeAsBoolean("abilRegistraPrelievo")){
			MenuItem registraPrelievoMenuItem = new MenuItem("Registra prelievo effettivo", "richiesteAccessoAtti/azioni/registraPrelievo.png");
			registraPrelievoMenuItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {
	
				@Override
				public void onClick(MenuItemClickEvent event) {
					manageAzioneSuRichAccessoAttiSingola("REGISTRA_PRELIEVO",listRecord);
				}
			});
			altreOpMenu.addItem(registraPrelievoMenuItem);
		}
		if(detailRecord.getAttributeAsBoolean("abilRegistraRestituzione")){
			MenuItem registraRestituzioneMenuItem = new MenuItem("Registra restituzione", "richiesteAccessoAtti/azioni/registraRestituzione.png");
			registraRestituzioneMenuItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {
	
				@Override
				public void onClick(MenuItemClickEvent event) {
					manageAzioneSuRichAccessoAttiSingola("REGISTRA_RESTITUZIONE",listRecord);
				}
			});
			altreOpMenu.addItem(registraRestituzioneMenuItem);
		}
		if(detailRecord.getAttributeAsBoolean("abilAnnullamento")){
			MenuItem annullamentoMenuItem = new MenuItem("Elimina richiesta", "richiesteAccessoAtti/azioni/annullamento.png");
			annullamentoMenuItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {
	
				@Override
				public void onClick(MenuItemClickEvent event) {
					manageAzioneSuRichAccessoAttiSingola("ANNULLAMENTO",listRecord);
				}
			});
			altreOpMenu.addItem(annullamentoMenuItem);
		}
		if(detailRecord.getAttributeAsBoolean("abilStampaFoglioPrelievo")){
			MenuItem stampaFoglioPrelievoMenuItem = new MenuItem("Stampa foglio prelievo", "richiesteAccessoAtti/azioni/stampaFoglioPrelievo.png");
			stampaFoglioPrelievoMenuItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {
	
				@Override
				public void onClick(MenuItemClickEvent event) {
					manageAzioneStampaFoglioPrelievoSingola(listRecord);
				}
			});
			altreOpMenu.addItem(stampaFoglioPrelievoMenuItem);
		}
		
		// CHIUDI RICHIESTA
		if(detailRecord.getAttributeAsBoolean("abilRichAccessoAttiChiusura")){
			MenuItem chiudiMenuItem = new MenuItem("Chiudi richiesta", "richiesteAccessoAtti/azioni/chiudi.png");
			chiudiMenuItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {
	
				@Override
				public void onClick(MenuItemClickEvent event) {
					manageAzioneSuRichAccessoAttiSingola("CHIUSURA",listRecord);
				}
			});
			altreOpMenu.addItem(chiudiMenuItem);
		}
		
		
		// RIAPRI RICHIESTA
		if(detailRecord.getAttributeAsBoolean("abilRichAccessoAttiRiapertura")){
			MenuItem riapriMenuItem = new MenuItem("Riapri richiesta", "richiesteAccessoAtti/azioni/riapri.png");
			riapriMenuItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {
	
				@Override
				public void onClick(MenuItemClickEvent event) {
					manageAzioneSuRichAccessoAttiSingola("RIAPERTURA",listRecord);
				}
			});
			altreOpMenu.addItem(riapriMenuItem);
		}
		
		// RIPRISTINA RICHIESTA
		if(detailRecord.getAttributeAsBoolean("abilRichAccessoAttiRipristino")){
			MenuItem ripristinaMenuItem = new MenuItem("Ripristina richiesta", "richiesteAccessoAtti/azioni/ripristina.png");
			ripristinaMenuItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {
	
				@Override
				public void onClick(MenuItemClickEvent event) {
					manageAzioneSuRichAccessoAttiSingola("RIPRISTINO",listRecord);
				}
			});
			altreOpMenu.addItem(ripristinaMenuItem);
		}
		
		return altreOpMenu;
	}
	
	private void manageAzioneSuRichAccessoAttiSingola(String codOperazione,final ListGridRecord listRecord) {
		final Record record = buildRecordSingleUd(listRecord);
		record.setAttribute("codOperazione", codOperazione);
		AzioneSuRichAccessoAttiPopup lAzioneSuRichAccessoAttiPopup = new AzioneSuRichAccessoAttiPopup(record) {
			
			@Override
			public void onClickOkButton(DSCallback service) {
				record.setAttribute("dataAppuntamento", datiAppuntamentoForm != null ? datiAppuntamentoForm.getValue("dataAppuntamento") : null);
				record.setAttribute("orarioAppuntamento", datiAppuntamentoForm != null ? datiAppuntamentoForm.getValue("orarioAppuntamento") : null);
				record.setAttribute("dataPrelievo", datiPrelievoForm != null ? datiPrelievoForm.getValue("dataPrelievo") : null);
				record.setAttribute("dataRestituzione", datiRestituzioneForm != null ? datiRestituzioneForm.getValue("dataRestituzione") : null);
				record.setAttribute("motivi", motiviForm != null ? motiviForm.getValue("motivi") : null );
				GWTRestService<Record, Record> lSetAzioneSuRichAccessoAttiDataSource = new GWTRestService<Record, Record>("SetAzioneSuRichAccessoAttiDataSource");
				Layout.showWaitPopup("Operazione in corso: potrebbe richiedere qualche secondo. Attendere…");
				try{
					lSetAzioneSuRichAccessoAttiDataSource.addData(record, new DSCallback() {
	
						@Override
						public void execute(DSResponse response, Object rawData, DSRequest request) {
							Layout.hideWaitPopup();
							if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
								Record record = response.getData()[0];
								boolean success = manageErroreSingoloAzioneRichiestaAtti(record);
								layout.reloadListAndSetCurrentRecord(listRecord);
								azioneSuRichAccessoAttiPopup.markForDestroy();
							}
						}
					});
				} catch (Exception e) {
					Layout.hideWaitPopup();
				}	
			}
		};
	}
		
	private void manageAzioneStampaFoglioPrelievoSingola(final ListGridRecord listRecord) {
		final Record record = buildRecordSingleUd(listRecord);
		Layout.showWaitPopup("Operazione in corso: potrebbe richiedere qualche secondo. Attendere…");
		try{
			new GWTRestService<Record, Record>("SetAzioneSuRichAccessoAttiDataSource").executecustom("generaFoglioPrelievo", record, new DSCallback() {
	
				@Override
				public void execute(DSResponse response, Object rawData, DSRequest request) {
					Layout.hideWaitPopup();
					if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
						Record result = response.getData()[0];
						boolean inError = result.getAttributeAsBoolean("recuperoModelloInError");
						if (inError){
							Layout.addMessage(new MessageBean(result.getAttribute("recuperoModelloErrorMessage"), "", MessageType.ERROR));
						}else{
							manageErroreSingoloAzioneRichiestaAtti(result);
							String nomeFilePdf = result.getAttribute("nomeFileFoglioPrelievo");
							String uriFilePdf = result.getAttribute("uriFileFoglioPrelievo");
							if (uriFilePdf != null && !"".equalsIgnoreCase(uriFilePdf)){
								InfoFileRecord infoFilePdf = InfoFileRecord.buildInfoFileString(JSON.encode(result.getAttributeAsRecord("infoFileFoglioPrelievo").getJsObj()));	
								PreviewControl.switchPreview(uriFilePdf, false, infoFilePdf, "FileToExtractBean", nomeFilePdf);
							}
						}
					}
				}
			});
		} catch (Exception e) {
			Layout.hideWaitPopup();
		}
	}

	
	private Record buildRecordSingleUd(ListGridRecord recordUd) {
		final RecordList listaRecord = new RecordList();
		String idUd = recordUd.getAttribute("idUd");
		String elencoIdFolderAttiRich = recordUd.getAttribute("elencoIdFolderAttiRich");
		Record recordIdUd = new Record();
		recordIdUd.setAttribute("idUd", idUd);
		recordIdUd.setAttribute("elencoIdFolderAttiRich", elencoIdFolderAttiRich);
		listaRecord.add(recordIdUd);
		Record record = new Record();		
		record.setAttribute("listaRecord", listaRecord);
		return record;
	}
	
//	private boolean manageErroreSingoloAzioneRichiestaAtti(Record object) {
//		
//		boolean verify = false;
//		RecordList listaRecord = object.getAttributeAsRecordList("listaRecord");
//		RecordList listaErrori = new RecordList();
//		Map errorMessages = object.getAttributeAsMap("errorMessages");
//		String errorMsg = null;
//		if (errorMessages != null && errorMessages.size() > 0) {
//			if (listaRecord.getLength() > errorMessages.size()) {
//				errorMsg = "Alcuni dei record selezionati per l''operazione sono andati in errore!";
//			} else {
//				errorMsg = "Tutti i record selezionati per l''operazione sono andati in errore!";
//			}
//			for (int i = 0; i < listaRecord.getLength(); i++) {
//				Record record = listaRecord.get(i);
//				
//				errorMsg += "<br/>" + errorMessages.get(record.getAttribute("idUd"));
//				
//				record.setAttribute("idError", record.getAttribute("idUd"));
//				record.setAttribute("descrizione", errorMessages.get(record.getAttribute("idUd")));
//				listaErrori.add(record);
//			}
//		}
//
//		if (errorMsg != null) {
//			ErroreMassivoPopup errorePopup = new ErroreMassivoPopup("", "N* atto", listaErrori, listaRecord.getLength(),
//					LARG_POPUP_ERR_MASS, ALT_POPUP_ERR_MASS);
//			errorePopup.show();
//		} else {
//			verify = true;
//			Layout.addMessage(new MessageBean("Operazione effettuata con successo", "", MessageType.INFO));										
//		}
//		
//		return verify;
//	}
	
	private boolean manageErroreSingoloAzioneRichiestaAtti(Record object) {
		
		boolean verify = false;
		RecordList listaRecord = object.getAttributeAsRecordList("listaRecord");
		Map errorMessages = object.getAttributeAsMap("errorMessages");
		String errorMsg = null;
		if (errorMessages != null && errorMessages.size() > 0) {
			// Ho al massiamo un record in errore
			for (int i = 0; i < listaRecord.getLength(); i++) {
				Record record = listaRecord.get(i);
				String descrizione = (String) errorMessages.get(record.getAttribute("idUd"));
				errorMsg = descrizione.substring(descrizione.indexOf("@#@") + 3, descrizione.length());
				Layout.addMessage(new MessageBean(errorMsg, "", MessageType.ERROR));
			}
			
		} else {
			verify = true;
			Layout.addMessage(new MessageBean("Operazione effettuata con successo", "", MessageType.INFO));
		}
		return verify;
	}
	
	public  void apriDettaglioProcesso(final String idProcessIn, final String ruoloSmistamentoIn, final String idUdIn , final String estremiProcessIn, final boolean abilitaSmistamentoIn, BooleanCallback callback) {
		
		if (idProcessIn != null) {
			
			List<CustomTaskButton> customButtons = new ArrayList<CustomTaskButton>();
			
			if(abilitaSmistamentoIn && AttiLayout.isAttivoSmistamentoAtti()) {	
				final CustomTaskButton buttonSmistaAtto = new CustomTaskButton("Smista", "pratiche/task/buttons/smista_atto.png") {

					public boolean isToShow(Record recordEvento) {
						Boolean flgAttivaSmistamento = recordEvento != null ? recordEvento.getAttributeAsBoolean("flgAttivaSmistamento") : null;
						return flgAttivaSmistamento != null && flgAttivaSmistamento;
					}
				};
				buttonSmistaAtto.addClickHandler(new ClickHandler() {

					@Override
					public void onClick(ClickEvent event) {
						final Record recordAtto = new Record();
						recordAtto.setAttribute("idProcedimento", idProcessIn);
						recordAtto.setAttribute("ruoloSmistamento", ruoloSmistamentoIn);
						recordAtto.setAttribute("unitaDocumentariaId", idUdIn);
						if(buttonSmistaAtto.getTaskDetail() != null && buttonSmistaAtto.getTaskDetail() instanceof TaskNuovaPropostaAtto2CompletaDetail) {
							((TaskNuovaPropostaAtto2CompletaDetail) buttonSmistaAtto.getTaskDetail()).salvaBeforeSmistaAtto(new ServiceCallback<Record>() {
								
								@Override
								public void execute(Record object) {
									AurigaLayout.smistaAtto(layout, recordAtto, new ServiceCallback<Record>() {

										@Override
										public void execute(Record object) {
											((TaskNuovaPropostaAtto2CompletaDetail) buttonSmistaAtto.getTaskDetail()).chiudiAfterSmistaAtto();
										}										
									});								
								}
							});
						} else {
							AurigaLayout.smistaAtto(layout, recordAtto, null);
						}
					}
				});
				customButtons.add(buttonSmistaAtto);
			}
			AurigaLayout.apriDettaglioPratica(idProcessIn, estremiProcessIn, customButtons, callback);					
		}
	}	
	
}
