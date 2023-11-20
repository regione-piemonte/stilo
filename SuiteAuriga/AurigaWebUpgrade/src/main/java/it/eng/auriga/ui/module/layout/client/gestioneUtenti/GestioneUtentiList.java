/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import com.google.gwt.i18n.client.Dictionary;
import com.smartgwt.client.data.DSCallback;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.data.SortSpecifier;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.DateDisplayFormat;
import com.smartgwt.client.types.ListGridFieldType;
import com.smartgwt.client.types.SortDirection;
import com.smartgwt.client.util.BooleanCallback;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.ImgButton;
import com.smartgwt.client.widgets.grid.CellFormatter;
import com.smartgwt.client.widgets.grid.HoverCustomizer;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.grid.ListGridRecord;
import com.smartgwt.client.widgets.grid.events.RecordClickEvent;
import com.smartgwt.client.widgets.grid.events.RecordClickHandler;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.menu.Menu;
import com.smartgwt.client.widgets.menu.MenuItem;
import com.smartgwt.client.widgets.menu.events.MenuItemClickEvent;

import it.eng.auriga.ui.module.layout.client.AurigaLayout;
import it.eng.auriga.ui.module.layout.client.i18n.I18NUtil;
import it.eng.utility.ui.module.core.client.datasource.GWTRestDataSource;
import it.eng.utility.ui.module.core.shared.message.MessageBean;
import it.eng.utility.ui.module.core.shared.message.MessageType;
import it.eng.utility.ui.module.layout.client.Layout;
import it.eng.utility.ui.module.layout.client.common.ControlListGridField;
import it.eng.utility.ui.module.layout.client.common.CustomList;

public class GestioneUtentiList extends CustomList {

	private ListGridField idUser;
	private ListGridField cognomeNome;
	private ListGridField username;
	private ListGridField nroMatricola;
	private ListGridField qualifica;
	private ListGridField titolo;
	private ListGridField email;
	private ListGridField dtIniVld;
	private ListGridField dtFineVld;
	private ListGridField nomeProfilo;
	private ListGridField indirizzo;
	private ListGridField idSoggRubrica;
	private ListGridField flgAccreditatiInDomIo;
	private ListGridField attivoIn;
	private ListGridField dtUltimoAccesso;
	private ListGridField codiceUO;
	private ListGridField nomeAppUO;
	private ListGridField flgUtenteBloccatoIcon;
	private ListGridField flgUtenteBloccatoMsgHover;
	
	protected String applicationName; 
	protected boolean gestioneClienti;
	protected boolean gestioneSocieta;
	
	
	public GestioneUtentiList(String nomeEntita) {

		super(nomeEntita);

		setImpostazioniGenerali();
		
		// visibili
		cognomeNome           = new ListGridField("cognomeNome",  I18NUtil.getMessages().gestioneutenti_cognomeNome());
		username              = new ListGridField("username",     I18NUtil.getMessages().gestioneutenti_username());
		nroMatricola          = new ListGridField("nroMatricola", I18NUtil.getMessages().gestioneutenti_nroMatricola());
		titolo                = new ListGridField("titolo", I18NUtil.getMessages().gestioneutenti_titolo());
		email                 = new ListGridField("email", I18NUtil.getMessages().gestioneutenti_email());
		dtIniVld              = new ListGridField("dtIniVld", I18NUtil.getMessages().gestioneutenti_dtIniVld()); dtIniVld.setType(ListGridFieldType.DATE); dtIniVld.setDateFormatter(DateDisplayFormat.TOEUROPEANSHORTDATE);
		dtFineVld             = new ListGridField("dtFineVld", I18NUtil.getMessages().gestioneutenti_dtFineVld()); dtFineVld.setType(ListGridFieldType.DATE); dtFineVld.setDateFormatter(DateDisplayFormat.TOEUROPEANSHORTDATE);
		nomeProfilo           = new ListGridField("nomeProfilo", I18NUtil.getMessages().gestioneutenti_idProfilo());
		indirizzo             = new ListGridField("indirizzo", I18NUtil.getMessages().gestioneutenti_indirizzo());
		attivoIn              = new ListGridField("attivoIn", I18NUtil.getMessages().gestioneutenti_attivoIn());
		dtUltimoAccesso       = new ListGridField("dtUltimoAccesso", I18NUtil.getMessages().gestioneutenti_dtUltimoAccesso()); dtUltimoAccesso.setType(ListGridFieldType.DATE); dtUltimoAccesso.setDateFormatter(DateDisplayFormat.TOEUROPEANSHORTDATETIME);
		codiceUO              = new ListGridField("codiceUO",I18NUtil.getMessages().gestioneutenti_codiceUO());
		nomeAppUO             = new ListGridField("nomeAppUO", I18NUtil.getMessages().gestioneutenti_nomeAppUO());
		qualifica             = new ListGridField("qualifica", I18NUtil.getMessages().gestioneutenti_qualifica());
		flgAccreditatiInDomIo = new ControlListGridField("flgAccreditatiInDomIo", I18NUtil.getMessages().gestionneutenti_associataAlDominio());
		flgAccreditatiInDomIo.setType(ListGridFieldType.ICON);
		flgAccreditatiInDomIo.setAttribute("custom", true);
		flgAccreditatiInDomIo.setHeaderBaseStyle(it.eng.utility.Styles.hiddenHeaderButton);
		flgAccreditatiInDomIo.setWrap(false);
		flgAccreditatiInDomIo.setWidth(30);
		flgAccreditatiInDomIo.setIconWidth(16);
		flgAccreditatiInDomIo.setIconHeight(16);
		flgAccreditatiInDomIo.setShowHover(true);
		flgAccreditatiInDomIo.setCellFormatter(new CellFormatter() {

			@Override
			public String format(Object value, ListGridRecord record, int rowNum, int colNum) {
				
				String flgAccreditatiInDomIo = record.getAttributeAsString("flgAccreditatiInDomIo");

				if (flgAccreditatiInDomIo == null)
					flgAccreditatiInDomIo = "";

				if (flgAccreditatiInDomIo.equals("0") || "".equals(flgAccreditatiInDomIo)) {
					return buildImgButtonHtml("buttons/add.png");
				} else if (flgAccreditatiInDomIo.equals("1")) {
					return buildImgButtonHtml("buttons/off.png");
				}

				return null;
			}
		});
		flgAccreditatiInDomIo.setHoverCustomizer(new HoverCustomizer() {

			@Override
			public String hoverHTML(Object value, ListGridRecord record, int rowNum, int colNum) {
				String flgAccreditatiInDomIo = record.getAttributeAsString("flgAccreditatiInDomIo");
				if (flgAccreditatiInDomIo == null)
					flgAccreditatiInDomIo = "";

				// Se l'utente non è associato
				if ("".equals(flgAccreditatiInDomIo) || flgAccreditatiInDomIo.equals("0")) {
					return I18NUtil.getMessages().gestioneutenti_associaaldominio();
				} else if (flgAccreditatiInDomIo.equals("1")) {
					return I18NUtil.getMessages().gestioneutenti_rimuovidaldominio();
				}

				return null;
			}
		});

		flgAccreditatiInDomIo.addRecordClickHandler(new RecordClickHandler() {
			public void onRecordClick(RecordClickEvent event) {
				event.cancel();
				final GWTRestDataSource lGwtRestDataSource = new GWTRestDataSource("AurigaGestioneUtentiDataSource");
				final Record record = event.getRecord();
				String flgAccreditatiInDomIo = record.getAttributeAsString("flgAccreditatiInDomIo");

				if (flgAccreditatiInDomIo == null)
					flgAccreditatiInDomIo = "";
				/**
				 * Associa al dominio
				 */
				if (flgAccreditatiInDomIo.equals("0") || "".equals(flgAccreditatiInDomIo)) {
					lGwtRestDataSource.executecustom("associaRimuoviDominio", record, new DSCallback() {

						public void execute(DSResponse response, Object rawData, DSRequest request) {
							if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
								Layout.addMessage(new MessageBean(I18NUtil.getMessages().gestioneutenti_afterAssociaDominio_message(), "", MessageType.INFO));
								layout.hideDetail(true);
								layout.reloadListAndSetCurrentRecord(record);
							} else {
								Layout.addMessage(new MessageBean(I18NUtil.getMessages().gestioneutenti_associaDominioError_message(), "", MessageType.ERROR));
							}
						}
					});
				} else if (flgAccreditatiInDomIo.equals("1")) {
					/**
					 * Rimuovi dal dominio
					 */
					SC.ask(I18NUtil.getMessages().gestioneutenti_confermiRimuovereDalDominio(), new BooleanCallback() {

						@Override
						public void execute(Boolean value) {
							if (value) {
								lGwtRestDataSource.executecustom("associaRimuoviDominio", record, new DSCallback() {

									public void execute(DSResponse response, Object rawData, DSRequest request) {
										if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
											Layout.addMessage(new MessageBean(I18NUtil.getMessages().gestioneutenti_afterDeleteDominio_message(), "",
													MessageType.INFO));
											layout.hideDetail(true);
											layout.reloadListAndSetCurrentRecord(record);
										} else {
											Layout.addMessage(new MessageBean(I18NUtil.getMessages().gestioneutenti_rimuoviDominioError_message(), "",
													MessageType.ERROR));
										}
									}
								});
							}
						}
					});
				}
			}
		});

		// Icona utente bloccato
		flgUtenteBloccatoIcon = new ControlListGridField("flgUtenteBloccato");
		flgUtenteBloccatoIcon.setType(ListGridFieldType.ICON);
		flgUtenteBloccatoIcon.setAttribute("custom", true);
		flgUtenteBloccatoIcon.setHeaderBaseStyle(it.eng.utility.Styles.hiddenHeaderButton);
		flgUtenteBloccatoIcon.setWrap(false);
		flgUtenteBloccatoIcon.setWidth(30);
		flgUtenteBloccatoIcon.setIconWidth(16);
		flgUtenteBloccatoIcon.setIconHeight(16);
		flgUtenteBloccatoIcon.setShowHover(true);
		flgUtenteBloccatoIcon.setShowTitle(false);
		flgUtenteBloccatoIcon.setCellFormatter(new CellFormatter() {
			@Override
			public String format(Object value, ListGridRecord record, int rowNum, int colNum) {
				String flgUtenteBloccato = record.getAttributeAsString("flgUtenteBloccato");
				if (flgUtenteBloccato == null)
					flgUtenteBloccato = "";
				if (flgUtenteBloccato.equals("1")) {
					return buildImgButtonHtml("buttons/locked.png");
				}
				return null;
			}
		});
		
		flgUtenteBloccatoIcon.setHoverCustomizer(new HoverCustomizer() {
			@Override
			public String hoverHTML(Object value, ListGridRecord record, int rowNum, int colNum) {
				String flgUtenteBloccato = record.getAttributeAsString("flgUtenteBloccato");
				if (flgUtenteBloccato == null)
					flgUtenteBloccato = "";
				if (flgUtenteBloccato.equals("1")) {
					String flgUtenteBloccatoMsgHover =   record.getAttributeAsString("flgUtenteBloccatoMsgHover");
					return flgUtenteBloccatoMsgHover;
				}
				return null;
				
			}
		});
						
		// nascoste
		idUser = new ListGridField("idUser", I18NUtil.getMessages().gestioneutenti_idUser());
		idUser.setHidden(true);
		idUser.setCanHide(false);

		idSoggRubrica = new ListGridField("idSoggRubrica");
		idSoggRubrica.setHidden(true);
		idSoggRubrica.setCanHide(false);
		
		flgUtenteBloccatoMsgHover = new ListGridField("flgUtenteBloccatoMsgHover");
		flgUtenteBloccatoMsgHover.setHidden(true);
		flgUtenteBloccatoMsgHover.setCanHide(false);
		

		setFields(idUser, cognomeNome, username, indirizzo, nomeProfilo, nroMatricola, qualifica, titolo, email, dtIniVld, dtFineVld, idSoggRubrica, flgUtenteBloccatoMsgHover, flgAccreditatiInDomIo, attivoIn, dtUltimoAccesso, codiceUO, nomeAppUO, flgUtenteBloccatoIcon);

	}

	@Override
	protected int getButtonsFieldWidth() {
		return 75;
	}

	@Override
	public void manageLoadDetail(final Record record, final int recordNum, final DSCallback callback) {
		getDataSource().performCustomOperation("get", record, new DSCallback() {

			@Override
			public void execute(DSResponse response, Object rawData, DSRequest request) {
				if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
					Record record = response.getData()[0];
					layout.getDetail().editRecord(record, recordNum);
					layout.getDetail().getValuesManager().clearErrors(true);
					callback.execute(response, null, new DSRequest());
				}
			}
		}, new DSRequest());
	}

	@Override
	protected Canvas createFieldCanvas(String fieldName, final ListGridRecord record) {

		Canvas lCanvasReturn = null;

		if (fieldName.equals("buttons")) {

			ImgButton detailButton = buildDetailButton(record);
			ImgButton modifyButton = buildModifyButton(record);
			ImgButton deleteButton = buildDeleteButton(record);

			if (!isRecordAbilToMod(record)) {
				modifyButton.disable();
			}

			if (!isRecordAbilToDel(record)) {
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

			lCanvasReturn = recordCanvas;
		}
		return lCanvasReturn;
	}

	@Override
	protected void manageDeleteButtonClick(final ListGridRecord record) {
		
		SC.ask("Sei sicuro di voler cancellare questo utente?", new BooleanCallback() {

			@Override
			public void execute(Boolean value) {
				
				if (value) {
					removeData(record, new DSCallback() {

						@Override
						public void execute(DSResponse response, Object rawData, DSRequest request) {
							if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
								Layout.addMessage(new MessageBean(I18NUtil.getMessages().afterDelete_message(layout.getTipoEstremiRecord(record)), "",
										MessageType.INFO));
								layout.hideDetail(true);
							} else {
								Layout.addMessage(new MessageBean(I18NUtil.getMessages().deleteError_message(layout.getTipoEstremiRecord(record)), "",
										MessageType.ERROR));
							}
						}
					});
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
		final Menu contextMenu = createContextMenu(record);
		contextMenu.showContextMenu();
	}

	public Menu createContextMenu(final ListGridRecord listRecord) {

		Menu contextMenu = new Menu();

		MenuItem dettaglioMenuItem = new MenuItem(I18NUtil.getMessages().detailButton_prompt(), "buttons/detail.png");
		dettaglioMenuItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {

			@Override
			public void onClick(MenuItemClickEvent event) {
				manageDetailButtonClick(listRecord);
			}
		});

		MenuItem modifyMenuItem = new MenuItem(I18NUtil.getMessages().modifyButton_prompt(), "buttons/modify.png");
		modifyMenuItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {

			@Override
			public void onClick(MenuItemClickEvent event) {
				manageModifyButtonClick(listRecord);
			}
		});

		MenuItem deleteMenuItem = new MenuItem(I18NUtil.getMessages().deleteButton_prompt(), "buttons/delete.png");
		deleteMenuItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {

			@Override
			public void onClick(MenuItemClickEvent event) {
				manageDeleteButtonClick(listRecord);
			}
		});
		

		if (isRecordAbilToView(listRecord)) {
			contextMenu.addItem(dettaglioMenuItem);				
		}
		if (isRecordAbilToMod(listRecord)) {
			contextMenu.addItem(modifyMenuItem);	
		}
		if (isRecordAbilToDel(listRecord)) {
			contextMenu.addItem(deleteMenuItem);
		}
		
		contextMenu.addSort(new SortSpecifier("title", SortDirection.ASCENDING));
		return contextMenu;
	}

	/******************************** NUOVA GESTIONE CONTROLLI BOTTONI ********************************/
	@Override
	public boolean isDisableRecordComponent() {
		
		return true;
	};

	@Override
	protected boolean showDetailButtonField() {
		return true;
	}

	@Override
	protected boolean showModifyButtonField() {
		return GestioneUtentiLayout.isAbilToMod();
	}

	@Override
	protected boolean showDeleteButtonField() {
		return GestioneUtentiLayout.isAbilToDel();
	}

	@Override
	protected boolean isRecordAbilToMod(ListGridRecord record) {
		final boolean flgDiSistema = record.getAttribute("flgDiSistema") != null && record.getAttributeAsString("flgDiSistema").equals("1");
		return GestioneUtentiLayout.isRecordAbilToMod(flgDiSistema);
	}

	@Override
	protected boolean isRecordAbilToDel(ListGridRecord record) {
		final boolean flgDiSistema = record.getAttribute("flgDiSistema") != null && record.getAttributeAsString("flgDiSistema").equals("1");
		final boolean flgValido = record.getAttribute("flgValido") != null && record.getAttributeAsString("flgValido").equals("1");
		return GestioneUtentiLayout.isRecordAbilToDel(flgValido, flgDiSistema);
	}

	/******************************** FINE NUOVA GESTIONE CONTROLLI BOTTONI ********************************/
	
	
	public void setImpostazioniGenerali() {
		String applicationName = null;
		try {
			Dictionary dictionary = Dictionary.getDictionary("params");   
			applicationName = dictionary != null && dictionary.get("applicationName") != null ? dictionary.get("applicationName") : "";
		} catch (Exception e) {
			applicationName = "";
		}
		
		setApplicationName(applicationName);
		setGestioneClienti(AurigaLayout.getParametroDBAsBoolean("GEST_USER_CLIENTI"));	
		setGestioneSocieta(AurigaLayout.getParametroDBAsBoolean("GEST_USER_SOCIETA"));
	}


	public boolean isGestioneClienti() {
		return gestioneClienti;
	}

	public void setGestioneClienti(boolean gestioneClienti) {
		this.gestioneClienti = gestioneClienti;
	}

	public boolean isGestioneSocieta() {
		return gestioneSocieta;
	}

	public void setGestioneSocieta(boolean gestioneSocieta) {
		this.gestioneSocieta = gestioneSocieta;
	}
	
	public String getApplicationName() {
		return applicationName;
	}
	public void setApplicationName(String applicationName) {
		this.applicationName = applicationName;
	}
}