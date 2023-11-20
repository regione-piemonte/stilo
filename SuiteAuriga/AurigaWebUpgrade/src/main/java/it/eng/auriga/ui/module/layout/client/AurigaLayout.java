/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.google.gwt.core.client.GWT;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.i18n.client.Dictionary;
import com.smartgwt.client.data.AdvancedCriteria;
import com.smartgwt.client.data.DSCallback;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.data.RecordList;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.Cursor;
import com.smartgwt.client.types.FieldType;
import com.smartgwt.client.types.HeaderControls;
import com.smartgwt.client.types.JSONDateFormat;
import com.smartgwt.client.types.OperatorId;
import com.smartgwt.client.types.Overflow;
import com.smartgwt.client.types.Positioning;
import com.smartgwt.client.types.SelectionStyle;
import com.smartgwt.client.types.VerticalAlignment;
import com.smartgwt.client.types.Visibility;
import com.smartgwt.client.util.BooleanCallback;
import com.smartgwt.client.util.EventHandler;
import com.smartgwt.client.util.JSON;
import com.smartgwt.client.util.JSONEncoder;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.Dialog;
import com.smartgwt.client.widgets.Img;
import com.smartgwt.client.widgets.Label;
import com.smartgwt.client.widgets.Window;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.events.KeyDownEvent;
import com.smartgwt.client.widgets.events.KeyDownHandler;
import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.client.widgets.menu.Menu;
import com.smartgwt.client.widgets.menu.MenuItem;
import com.smartgwt.client.widgets.menu.events.MenuItemClickEvent;
import com.smartgwt.client.widgets.tile.TileGrid;
import com.smartgwt.client.widgets.tile.events.RecordClickEvent;
import com.smartgwt.client.widgets.tile.events.RecordClickHandler;
import com.smartgwt.client.widgets.toolbar.ToolStrip;
import com.smartgwt.client.widgets.viewer.DetailViewerField;

import it.eng.auriga.ui.module.layout.client.archivio.SceltaTipoFolderPopup;
import it.eng.auriga.ui.module.layout.client.gestioneatti.CondivisioneAttiPopup;
import it.eng.auriga.ui.module.layout.client.gestioneatti.SmistamentoAttiPopup;
import it.eng.auriga.ui.module.layout.client.gestioneatti.delibere.SceltaCommissionePopup;
import it.eng.auriga.ui.module.layout.client.gestioneatti.delibere.SceltaSedutaOrganoCollegialePopup;
import it.eng.auriga.ui.module.layout.client.i18n.I18NUtil;
import it.eng.auriga.ui.module.layout.client.istanzePortaleRiscossioneDaIstruire.SceltaTipoFlussoIstanzaPopup;
import it.eng.auriga.ui.module.layout.client.organigramma.SaveCodRapidoOrganigrammaWindow;
import it.eng.auriga.ui.module.layout.client.organigramma.SavePrefAccessibilitaWindow;
import it.eng.auriga.ui.module.layout.client.organigramma.SavePrefScrivaniaWindow;
import it.eng.auriga.ui.module.layout.client.organigramma.SaveUoLavoroWindow;
import it.eng.auriga.ui.module.layout.client.postaElettronica.FirmaEmailHtmlPopup;
import it.eng.auriga.ui.module.layout.client.postaElettronica.FirmeEmailHtmlPopup;
import it.eng.auriga.ui.module.layout.client.pratiche.ApriDettaglioPraticaLayout;
import it.eng.auriga.ui.module.layout.client.pratiche.ApriDettaglioPraticaSenzaFlussoWindow;
import it.eng.auriga.ui.module.layout.client.pratiche.AvvioPraticaLayout;
import it.eng.auriga.ui.module.layout.client.pratiche.dettaglio.frontoffice.items.CustomTaskButton;
import it.eng.auriga.ui.module.layout.client.print.PreviewControl;
import it.eng.auriga.ui.module.layout.client.protocollazione.SceltaRepertorioPopup;
import it.eng.auriga.ui.module.layout.client.protocollazione.SceltaTipoDocPopup;
import it.eng.auriga.ui.module.layout.client.quicklaunch.QuickLaunchRecord;
import it.eng.auriga.ui.module.layout.client.richiestaAccessoAtti.SceltaTipoRichiestaAccessoAttiPopup;
import it.eng.auriga.ui.module.layout.client.scrivania.ScrivaniaLayout;
import it.eng.utility.ui.module.core.client.UserInterfaceFactory;
import it.eng.utility.ui.module.core.client.callback.ServiceCallback;
import it.eng.utility.ui.module.core.client.datasource.GWTRestDataSource;
import it.eng.utility.ui.module.core.client.datasource.GWTRestService;
import it.eng.utility.ui.module.core.shared.message.MessageBean;
import it.eng.utility.ui.module.core.shared.message.MessageType;
import it.eng.utility.ui.module.layout.client.Layout;
import it.eng.utility.ui.module.layout.client.StringSplitterClient;
import it.eng.utility.ui.module.layout.client.UserUtil;
import it.eng.utility.ui.module.layout.client.UserUtilCallback;
import it.eng.utility.ui.module.layout.client.WaitingMessageCallback;
import it.eng.utility.ui.module.layout.client.common.CustomLayout;
import it.eng.utility.ui.module.layout.client.common.SavePreferenceAction;
import it.eng.utility.ui.module.layout.client.common.file.InfoFileRecord;
import it.eng.utility.ui.module.layout.client.menu.PreferenceMenuButton;
import it.eng.utility.ui.module.layout.client.message.MessageBox;
import it.eng.utility.ui.module.layout.client.portal.ModalWindow;
import it.eng.utility.ui.module.layout.client.portal.Portlet;
import it.eng.utility.ui.module.layout.shared.bean.GenericConfigBean;
import it.eng.utility.ui.module.layout.shared.bean.LoginBean;
import it.eng.utility.ui.module.layout.shared.bean.MenuBean;

public class AurigaLayout extends Layout {

	protected static Map parametriDB;

	protected static String urlEditorOrganigramma;
	
	// private static Map nomiModelliAttiMap;
	protected static Map hiddenFieldsAttiMap;

//	protected static LinkedHashMap<String, Record> mappaUoCollegateUtente;
//	protected static LinkedHashMap<String, String> uoCollegateUtenteValueMap;
	protected static Map<String, String> uoRegistrazioneValueMap;
	protected static Map<String, Record> mappaUoRegistrazione;
//	protected static LinkedHashMap<String, String> uoProponenteAttiValueMap;
	protected static LinkedHashMap<String, String> utentiAbilCPAValueMap;
	protected static Map<String, Record> mappaDestProtEntrataDefault;
	
	protected static Map<String, String> uoSpecificitaRegistrazioneEValueMap;
	protected static Map<String, String> uoSpecificitaRegistrazioneUIValueMap;
	protected static Map<String, String> uoSpecificitaAvvioIterAttiValueMap;
	protected static Map<String, String> uoSpecificitaSceltaUOLavoroValueMap;
	protected static Map<String, Record> mappaUoSpecificitaSceltaUOLavoroValueMap;

	protected static String firmaEmailHtml = "";
	protected static LinkedHashMap<String, String> firmeEmailHtml;
	protected static String firmaEmailPredefinita = "";
	protected static String firmaEmailAutoNuova = "";
	protected static String firmaEmailAutoRisposta = "";
	protected static String firmaEmailAutoInoltro = "";
	protected static String impostazioniPdf = "";

	protected static String codRapidoOrganigramma = null;
	protected static String uoLavoro = null;
	protected static Record prefScrivania = null;
	protected static Record prefNotificheMail = null;
	protected static String prefIterAtti = null;
	protected static Record impostazioniStampa = null;
	protected static Record impostazioniFirma = null;
	protected static boolean isMarkForDestroy = false;
	protected static Record impostazioniFirmaAutomatica = null;
	protected static Record impostazioniTimbro = null;
	protected static Record impostazioniDocumento = null;
	protected static Record impostazioniFascicolo = null;
	protected static Record impostazioniSceltaOrganigramma = null;
	protected static Record impostazioniSceltaAccessibilita = null;
	protected static Record impostazioniAperturaDettaglioPerRicercaPuntuale = null;
	protected static Boolean funzioneProtocollazione = false;
	protected static Boolean attivaProtOttimizzataAllegati = false;

	protected Label funzProtLabel;
	protected Img funzProtButton;
	protected static String idUOPuntoProtAttivato = null;
	protected static String descUOPuntoProtAttivato = null;
	
	protected static HashSet<Canvas> temporaryDisabledButtons = new HashSet<Canvas>();
		
	public AurigaLayout(String logo, String bkgndImage) {
		super(logo, bkgndImage);
	}

	public AurigaLayout() {
		super();
	}
	
	public static String getIdDominio() {
		if(getUtenteLoggato() != null) {
			if (getUtenteLoggato().getDominio() != null && getUtenteLoggato().getDominio().split(":").length == 2) {
				return getUtenteLoggato().getDominio().split(":")[1];
			} else {
				return getUtenteLoggato().getDominio();
			}
		}
		return null;
	}
	
	public static String getPrefKeySuffixSpecXDominio() {
		String idDominio = getIdDominio();
		return idDominio != null && !"".equals(idDominio) ? "." + idDominio : ""; 
	}

	@Override
	public MessageBox buildMessageBox() {		
		return new MessageBox() {
				
			@Override
			//protected void viewMessages(MessageBean message, final Integer waitingTime, final WaitingMessageCallback callback) {
			protected void viewMessages(MessageBean message, final Integer waitingTime, final WaitingMessageCallback waitingMessageCallback, final DSCallback dsCallback, final DSResponse dsResponse, final Object data, final DSRequest dsRequest){
				if (AurigaLayout.getIsAttivaAccessibilita() || AurigaLayout.getParametroDBAsBoolean("SHOW_POPUP_GUI_MESSAGES")) {
					if (message.getType().equals(MessageType.INFO) || message.getType().equals(MessageType.WARNING_NO_POPUP)) {
						// La waitingcallback la faccio sempre gestire dal viewmessage, in modo che venga eseguita quando il messaggio scompare
						if (dsCallback != null) {
							dsCallback.execute(dsResponse, data, dsRequest);
						}
						super.viewMessages(message, waitingTime, waitingMessageCallback, null, null, null, null);
					} else {
						final Dialog dialog = new Dialog();
//						dialog.setIsModal(true);
//						dialog.setShowModalMask(true);
//						Button okButton = new Button("Ok");
//						okButton.setAutoFit(true);
//						okButton.setMinWidth(100);				
//						dialog.setButtons(okButton);
						switch (message.getType()) {
							case ERROR:
//								dialog.setTitle("ERRORE");
								dialog.setIcon("message/error.png");	
								break;						
							case WARNING:
//								dialog.setTitle("WARNING");
								dialog.setIcon("message/warning.png");	
								break;
							default:
								break;
						}
//						dialog.setMessage(message.getMessage());
						dialog.setIconSize(32);
//						dialog.addButtonClickHandler(new ButtonClickHandler() {
//								
//							@Override
//							public void onButtonClick(ButtonClickEvent event) {
//								if(callback != null) {
//									callback.execute();
//								}
//								dialog.markForDestroy();						
//							}
//						});
//						dialog.show();	
						SC.warn(dialog.getTitle(), message.getMessage(), new BooleanCallback() {
							
							@Override
							public void execute(Boolean value) {
								if(waitingMessageCallback != null) {
									waitingMessageCallback.execute();
								}	
								if (dsCallback != null) {
									dsCallback.execute(dsResponse, data, dsRequest);
								}
							}
						}, dialog);
					}
				} else {	
					// La waitingcallback la faccio sempre gestire dal viewmessage, in modo che venga eseguita quando il messaggio scompare
					if (dsCallback != null) {
						dsCallback.execute(dsResponse, data, dsRequest);
					}
					super.viewMessages(message, waitingTime, waitingMessageCallback, null, null, null, null);
				}
			}
		};				
	}

	public void loadPreferenceUoLavoroAndUpdateWelcomeMessage() {
		if (getParametroDBAsBoolean("SHOW_UO_IN_TOOLBAR")) {			
			loadUserPreference("idUODefault" + getPrefKeySuffixSpecXDominio(), "DEFAULT", new ServiceCallback<Record>() {

				@Override
				public void execute(Record recordPrefSpec) {
					if (recordPrefSpec != null) {
						uoLavoro = recordPrefSpec.getAttributeAsString("value");
						updateWelcomeMessage();
					} else {
						loadUserPreference("idUODefault", "DEFAULT", new ServiceCallback<Record>() {

							@Override
							public void execute(Record recordPref) {
								if (recordPref != null) {
									uoLavoro = recordPref.getAttributeAsString("value");									
								} else {
									uoLavoro = null;
								}
								updateWelcomeMessage();
							}
						});
					}					
				}
			});
		} else {
			uoLavoro = null;
			updateWelcomeMessage();
		}
	}

	@Override
	public void updateWelcomeMessage() {
		if (utenteLoggato != null) {
			String denominazione = utenteLoggato.getDenominazione();
			if (uoLavoro != null && !"".equals(uoLavoro)) {
				String desUoLavoro = getUoCollegateUtenteValueMap().get(uoLavoro);
				if ((desUoLavoro != null && !"".equals(desUoLavoro))) {
					if (denominazione.contains("@")) {
						StringSplitterClient st = new StringSplitterClient(denominazione, "@");
						denominazione = st.getTokens()[0] + "@" + desUoLavoro + " | " + st.getTokens()[1];
					} else {
						denominazione += "@" + desUoLavoro;
					}
				}
			}
			if (utenteLoggato.getDelegaDenominazione() != null && !"".equals(utenteLoggato.getDelegaDenominazione())) {
				setUserLabelContents(I18NUtil.getMessages().welcomeDelega(denominazione, utenteLoggato.getDelegaDenominazione()));
			} else {
				setUserLabelContents(I18NUtil.getMessages().welcome(denominazione));
			}
		}
	}

	@Override
	protected void afterConfigure() {
		
		new GWTRestService<Record, Record>("AurigaConfigurationDataSource").call(new Record(), new ServiceCallback<Record>() {

			public void execute(Record record) {
				parametriDB = record.getAttributeAsMap("parametriDB");
//				AurigaPreferenceDataSource.cacheUserPreference = record.getAttributeAsMap("cacheUserPreference"); 
				urlEditorOrganigramma = record.getAttribute("urlEditorOrganigramma");
				// nomiModelliAttiMap = record.getAttributeAsMap("nomiModelliAtti");
				hiddenFieldsAttiMap = record.getAttributeAsMap("hiddenFieldsAtti");
				RecordList lRecordList = record.getAttributeAsRecordList("utentiAbilCPAList");
				utentiAbilCPAValueMap = new LinkedHashMap<String, String>();
				if (lRecordList != null) {
					for (int i = 0; i < lRecordList.getLength(); i++) {
						String displayValue = lRecordList.get(i).getAttribute("cognomeNome") + " ("
								+ lRecordList.get(i).getAttribute("username") + ")";
						utentiAbilCPAValueMap.put(lRecordList.get(i).getAttribute("idUtente"), displayValue);
					}
				}
				caricaDatiUtenteLavoro(new DSCallback() {

					@Override
					public void execute(DSResponse response, Object rawData, DSRequest request) {

						aggiornaUtente();
					}
				});
			}
		});
		
//		new GWTRestService<Record, Record>("ConfigAttiDataSource").call(new Record(), new ServiceCallback<Record>() {
//
//			public void execute(Record record) {
//
//				// nomiModelliAttiMap = record.getAttributeAsMap("nomiModelliAtti");
//				hiddenFieldsAttiMap = record.getAttributeAsMap("hiddenFieldsAtti");
//				new GWTRestService<Record, Record>("ParametriDBDataSource").call(new Record(), new ServiceCallback<Record>() {
//
//					public void execute(Record record) {
//
//						parametriDB = record.getAttributeAsMap("parametriDB");
//						new GWTRestService<Record, Record>("UtentiAbilCPADataSource").call(new Record(), new ServiceCallback<Record>() {
//
//							public void execute(Record record) {
//
//								RecordList lRecordList = record.getAttributeAsRecordList("utentiAbilCPAList");
//								utentiAbilCPAValueMap = new LinkedHashMap<String, String>();
//								if (lRecordList != null) {
//									for (int i = 0; i < lRecordList.getLength(); i++) {
//										String displayValue = lRecordList.get(i).getAttribute("cognomeNome") + " ("
//												+ lRecordList.get(i).getAttribute("username") + ")";
//										utentiAbilCPAValueMap.put(lRecordList.get(i).getAttribute("idUtente"), displayValue);
//									}
//								}
//								caricaDatiUtenteLavoro(new DSCallback() {
//
//									@Override
//									public void execute(DSResponse response, Object rawData, DSRequest request) {
//
//										aggiornaUtente();
//									}
//								});
//
//							}
//						});
//					}
//				});
//			}
//		});
	}

	public Layout getSuper() {
		return super.getInstance();
	}

	@Override
	public boolean isAbilMenuBarraDesktop() {
		return AurigaLayout.getParametroDBAsBoolean("ATTIVA_DESKTOP_BUTTONS");
	}

	public boolean showChatButton() {
		return AurigaLayout.getParametroDBAsBoolean("ATTIVA_CHAT");
	}

	public void manageChat() {
		HtmlFlowWindow htmlFlowWindow = new HtmlFlowWindow("chat", "Chat", "chat.png", GWT.getHostPageBaseURL() + "chat.html");
		htmlFlowWindow.show();
	}

	public void caricaDatiUtenteLavoro(final DSCallback callback) {
		new GWTRestService<Record, Record>("UoCollegateUtenteDatasource").call(new Record(), new ServiceCallback<Record>() {

			@Override
			public void execute(final Record mappaUoProtocollante) {
				String start = DateTimeFormat.getFormat("HH:mm:ss").format(new Date());
				uoRegistrazioneValueMap = new LinkedHashMap<String, String>();
				mappaUoRegistrazione = new LinkedHashMap<String, Record>();
				mappaDestProtEntrataDefault = new LinkedHashMap<String, Record>();
				if (mappaUoProtocollante != null) {
					if (mappaUoProtocollante.getAttributeAsMap("uoRegistrazioneValueMap") != null && mappaUoProtocollante.getAttributeAsMap("uoRegistrazioneValueMap").size() > 0 ) {
						uoRegistrazioneValueMap = mappaUoProtocollante.getAttributeAsMap("uoRegistrazioneValueMap");						
					}
					if (mappaUoProtocollante.getAttributeAsMap("mappaUoRegistrazione") != null && mappaUoProtocollante.getAttributeAsMap("mappaUoRegistrazione").size() > 0 ) {
						mappaUoRegistrazione = mappaUoProtocollante.getAttributeAsMap("mappaUoRegistrazione");
						for (String key : mappaUoRegistrazione.keySet()) {
							mappaUoRegistrazione.put(key, new Record((Map) mappaUoRegistrazione.get(key)));
						}
					}
					if (mappaUoProtocollante.getAttributeAsMap("mappaDestProtEntrataDefault") != null && mappaUoProtocollante.getAttributeAsMap("mappaDestProtEntrataDefault").size() > 0 ) {
						mappaDestProtEntrataDefault = mappaUoProtocollante.getAttributeAsMap("mappaDestProtEntrataDefault");
						for (String key : mappaDestProtEntrataDefault.keySet()) {
							mappaDestProtEntrataDefault.put(key, new Record((Map) mappaDestProtEntrataDefault.get(key)));
						}
					}
					GWT.log("UoCollegateUtente " + start + " ended at " + DateTimeFormat.getFormat("HH:mm:ss").format(new Date()));
				}
				
				GWTRestService<Record, Record> lUoCollegateUtenteSpecificitaDatasource = new GWTRestService<Record, Record>("UoCollegateUtenteSpecificitaDatasource");
				lUoCollegateUtenteSpecificitaDatasource.call(new Record(), new ServiceCallback<Record>() {

					@Override
					public void execute(final Record listaUoSpecificita) {
						// Creo le mappe uo per specificita
						String start = DateTimeFormat.getFormat("HH:mm:ss").format(new Date());
						uoSpecificitaRegistrazioneEValueMap = new LinkedHashMap<String, String>();
						uoSpecificitaRegistrazioneUIValueMap = new LinkedHashMap<String, String>();
						uoSpecificitaAvvioIterAttiValueMap = new LinkedHashMap<String, String>();
						uoSpecificitaSceltaUOLavoroValueMap= new LinkedHashMap<String, String>();
						mappaUoSpecificitaSceltaUOLavoroValueMap= new LinkedHashMap<String, Record>();
						if (listaUoSpecificita.getAttributeAsMap("mappaUoCollegateUtenteRegistrazioneE") != null && listaUoSpecificita.getAttributeAsMap("mappaUoCollegateUtenteRegistrazioneE").size() > 0 ) {
							uoSpecificitaRegistrazioneEValueMap = listaUoSpecificita.getAttributeAsMap("mappaUoCollegateUtenteRegistrazioneE");
						} 
						if (listaUoSpecificita.getAttributeAsMap("mappaUoCollegateUtenteRegistrazioneUI") != null && listaUoSpecificita.getAttributeAsMap("mappaUoCollegateUtenteRegistrazioneUI").size() > 0 ) {
							uoSpecificitaRegistrazioneUIValueMap = listaUoSpecificita.getAttributeAsMap("mappaUoCollegateUtenteRegistrazioneUI");
						}
						if (listaUoSpecificita.getAttributeAsMap("mappaUoCollegateUtenteAvviaIterAtto") != null && listaUoSpecificita.getAttributeAsMap("mappaUoCollegateUtenteAvviaIterAtto").size() > 0 ) {
							uoSpecificitaAvvioIterAttiValueMap = listaUoSpecificita.getAttributeAsMap("mappaUoCollegateUtenteAvviaIterAtto");
						} 
						if (listaUoSpecificita.getAttributeAsMap("mappaUoCollegateUtenteSceltaUOLavoro") != null && listaUoSpecificita.getAttributeAsMap("mappaUoCollegateUtenteSceltaUOLavoro").size() > 0 ) {
							uoSpecificitaSceltaUOLavoroValueMap = listaUoSpecificita.getAttributeAsMap("mappaUoCollegateUtenteSceltaUOLavoro");
							mappaUoSpecificitaSceltaUOLavoroValueMap = listaUoSpecificita.getAttributeAsMap("mappaUoCollegateUtenteSceltaUOLavoroValueObject");
							for (String key : mappaUoSpecificitaSceltaUOLavoroValueMap.keySet()) {
								mappaUoSpecificitaSceltaUOLavoroValueMap.put(key, new Record((Map) mappaUoSpecificitaSceltaUOLavoroValueMap.get(key)));
							}
						}
						GWT.log("UoCollegateUtenteSpecificita " + start + " ended at " + DateTimeFormat.getFormat("HH:mm:ss").format(new Date()));
						createConfigUtenteMenu(callback);
					}
				});
			}
		});
	}

	@Override
	public boolean showLogoutButton() {

		final Dictionary dictionary = Dictionary.getDictionary("params");
		String usernameSSO;
		try {
			usernameSSO = dictionary.keySet().contains("usernameSSO") && dictionary.get("usernameSSO") != null ? dictionary.get("usernameSSO") : null;
		} catch (Exception e) {
			usernameSSO = null;
		}
		return usernameSSO == null || "".equals(usernameSSO);
	}

	@Override
	public void lavoraInDelega() {
		caricaDatiUtenteLavoro(new DSCallback() {

			@Override
			public void execute(DSResponse response, Object rawData, DSRequest request) {

				UserUtil.getInfoUtente(new UserUtilCallback() {

					@Override
					public void execute(LoginBean lLoginBean) {
						utenteLoggato = lLoginBean;
						username = lLoginBean.getUserid();
						loadPreferenceUoLavoroAndUpdateWelcomeMessage();
						temporaryDisabledButtons = new HashSet<Canvas>();
						closeAllPortlets();
						createMenu();
						Layout.hideWaitPopup();
					}
				});
			}
		});
	}

	@Override
	public void terminaDelega() {
		caricaDatiUtenteLavoro(new DSCallback() {

			@Override
			public void execute(DSResponse response, Object rawData, DSRequest request) {

				UserUtil.getInfoUtente(new UserUtilCallback() {

					@Override
					public void execute(LoginBean lLoginBean) {
						utenteLoggato = lLoginBean;
						username = lLoginBean.getUserid();
						loadPreferenceUoLavoroAndUpdateWelcomeMessage();
						temporaryDisabledButtons = new HashSet<Canvas>();
						closeAllPortlets();
						createMenu();
						Layout.hideWaitPopup();
					}
				});
			}
		});
	}

	@Override
	public void aggiornaUtente() {
		UserUtil.getInfoUtente(new UserUtilCallback() {

			@Override
			public void execute(LoginBean lLoginBean) {
				Layout.hideWaitPopup();
				boolean cambiatoUtente = username == null || !username.equals(lLoginBean.getUserid());
				boolean cambiatoDominio = utenteLoggato == null || !utenteLoggato.getDominio().equals(lLoginBean.getDominio());
				boolean precInDelega = utenteLoggato != null && utenteLoggato.getDelegaDenominazione() != null
						&& !"".equals(utenteLoggato.getDelegaDenominazione());
				utenteLoggato = lLoginBean;				
				loadPreferenceUoLavoroAndUpdateWelcomeMessage();
				if (!cambiatoUtente && !cambiatoDominio && precInDelega) {
					SC.warn("La sessione precedente è terminata o scaduta. Se si stava lavorando in delega bisogna reimpostare tale modalità.");
				}
				if (cambiatoUtente || cambiatoDominio || precInDelega) {
					username = lLoginBean.getUserid();
					if (config.getMenuImage() != null && !"".equals(config.getMenuImage())) {
						menuImg.setSrc(config.getMenuImage());
					} else {
						menuImg.setSrc(defaultMenuImage);
					}
					// refreshMenuImg();
					if (logoImage != null && !"".equals(logoImage)) {
						logoImg.setSrc(logoImage);
					} else if (config.getLogoImage() != null && !"".equals(config.getLogoImage())) {
						logoImg.setSrc(config.getLogoImage());
					} else {
						logoImg.setSrc(defaultLogoImage);
					}
					if (bkgndImage != null && !"".equals(bkgndImage)) {
						portalDesktop.setBackgroundImage(bkgndImage);
					} else if (config.getBackgroundImage() != null && !"".equals(config.getBackgroundImage())) {
						portalDesktop.setBackgroundImage(config.getBackgroundImage());
					} else {
						portalDesktop.setBackgroundImage(defaultBkgndImage);
					}
					portalDesktopBackgroundImage = portalDesktop.getBackgroundImage();
					if (config.getBackgroundColor() != null && !"".equals(config.getBackgroundColor())) {
						portalDesktop.setBackgroundColor(config.getBackgroundColor());
					}
					portalDesktopContents = "<html><body><div style=\"width:99%; height:99%;\">";
					if (getParametroDB("HOMEPAGE_MESSAGE") != null && !"".equals(getParametroDB("HOMEPAGE_MESSAGE"))) {
						portalDesktopContents += "<div style=\"width:100%; position:fixed; bottom:" + (footer.getVisibleHeight() + 10) + "px; text-align:center;\">" + getParametroDB("HOMEPAGE_MESSAGE")
								+ "</div>";
					}
					String logoDominioImage = null;
					String logoUtente = null;
					if (config.getLogoDominioFolder() != null && !"".equals(config.getLogoDominioFolder())) {
						logoDominioImage = config.getLogoDominioFolder();
						if (!config.getLogoDominioFolder().endsWith("/")) {
							logoDominioImage += "/";
						}
						String idDominio = null;
						if (lLoginBean.getDominio() != null && !"".equals(lLoginBean.getDominio())) {
							if (lLoginBean.getDominio().split(":").length == 2) {
								idDominio = lLoginBean.getDominio().split(":")[1];
							}
						}
						// se l'utente ha un suo logo mostra quello altrimenti mostra il logo di default del dominio
						if (lLoginBean.getLogoUtente() != null && !"".equals(lLoginBean.getLogoUtente())) {
							logoUtente = (lLoginBean.getLogoUtente()).toString();
							logoDominioImage += logoUtente;
						} else if (idDominio != null && !"".equals(idDominio)) {
							logoDominioImage += "Logo_" + idDominio + ".png";
						} else {
							logoDominioImage = "images/blank.png";
						}
						portalDesktopContents += "<div style=\"width:100%; position:fixed; bottom:" + (footer.getVisibleHeight() + 10) + "px; text-align:right;\"><img src=\"" + logoDominioImage
								+ "\"/></div>";
					}
					portalDesktopContents += "</div></body></html>";
					portalDesktop.setContents(portalDesktopContents);
					portalDesktop.setPosition(Positioning.ABSOLUTE);			
					if (getParametroDBAsBoolean("ATTIVA_CHAT")) {						
						chatButton.show();
					} else {
						chatButton.hide();
					}
					temporaryDisabledButtons = new HashSet<Canvas>();
					closeAllPortlets();
					
					// Setto le informazioni per Shibboleth
					setShibbolethLogoutInfo();
					setShibbolethRedirectUrlAfterSessionExpired();
					
					createMenu();
				} else {
					if(temporaryDisabledButtons != null) {
						for(Canvas button : temporaryDisabledButtons) {
							button.setDisabled(false);							
						}						
					}
					temporaryDisabledButtons = new HashSet<Canvas>();
				}
			}
		});
	}

	public void afterAggiornaUtente() {

		addPortalDesktopButtons();
		
		clearUserPreferences();
		
		loadUserPreference("attivaProtOttimizzataAllegati", "DEFAULT", new ServiceCallback<Record>() {

			@Override
			public void execute(Record recordPref) {
				attivaProtOttimizzataAllegati = false;
				if (recordPref != null) {
					if(recordPref.getAttribute("value") != null && !"".equals(recordPref.getAttribute("value"))) {
						attivaProtOttimizzataAllegati = new Boolean(recordPref.getAttribute("value"));
					}
				}
			}
		});

		loadUserPreference("idUOPuntoProtAttivato" + getPrefKeySuffixSpecXDominio(), "DEFAULT", new ServiceCallback<Record>() {

			@Override
			public void execute(Record recordPrefSpec) {
				
				idUOPuntoProtAttivato = recordPrefSpec != null ? recordPrefSpec.getAttributeAsString("value") : null;
				if(isAbilAttivaFunzProtocollazione()) {
					funzProtLabel.show();
					funzProtButton.show();
				} else {
					funzProtLabel.hide();
					funzProtButton.hide();
				}
				
				if(idUOPuntoProtAttivato != null && !"".equals(idUOPuntoProtAttivato)) {
					attivaFunzProt();
				} else {
					disattivaFunzProt();
				}			
				
			}
		});	
		
		loadUserPreference("signature.email", "DEFAULT", new ServiceCallback<Record>() {

			@Override
			public void execute(Record recordPref) {
				if (recordPref != null) {
					firmaEmailHtml = recordPref.getAttributeAsString("value");
				}
			}
		});

		loadUserPreferences("signature.email", new ServiceCallback<RecordList>() {

			@Override
			public void execute(RecordList recordListPref) {
				if (recordListPref != null) {
					firmeEmailHtml = new LinkedHashMap<String, String>();
					for (int i = 0; i < recordListPref.getLength(); i++) {
						Record recordPref = recordListPref.get(i);
						firmeEmailHtml.put(recordPref.getAttributeAsString("prefName"), recordPref.getAttributeAsString("value"));
					}
				}
			}
		});

		loadUserPreference("signature.email.predefinita", "DEFAULT", new ServiceCallback<Record>() {

			@Override
			public void execute(Record recordPref) {
				if (recordPref != null) {
					firmaEmailPredefinita = recordPref.getAttributeAsString("value");
				}
			}
		});

		loadUserPreference("signature.email.nuova", "DEFAULT", new ServiceCallback<Record>() {

			@Override
			public void execute(Record recordPref) {
				if (recordPref != null) {
					firmaEmailAutoNuova = recordPref.getAttributeAsString("value");
				}
			}
		});

		loadUserPreference("signature.email.risposta", "DEFAULT", new ServiceCallback<Record>() {

			@Override
			public void execute(Record recordPref) {
				if (recordPref != null) {
					firmaEmailAutoRisposta = recordPref.getAttributeAsString("value");
				}
			}
		});

		loadUserPreference("signature.email.inoltro", "DEFAULT", new ServiceCallback<Record>() {

			@Override
			public void execute(Record recordPref) {
				if (recordPref != null) {
					firmaEmailAutoInoltro = recordPref.getAttributeAsString("value");
				}
			}
		});
		
		loadUserPreference("impostazioniPdf", "properties","DEFAULT", new ServiceCallback<Record>() {
			
			@Override
			public void execute(Record recordPref) {
				if (recordPref != null) {
					impostazioniPdf = recordPref.getAttributeAsString("value");
				}
			}
		});

		loadUserPreference("cod.organigramma" + getPrefKeySuffixSpecXDominio(), "DEFAULT", new ServiceCallback<Record>() {

			@Override
			public void execute(Record recordPrefSpec) {
				if (recordPrefSpec != null) {
					codRapidoOrganigramma = recordPrefSpec.getAttributeAsString("value");
				} else {
					loadUserPreference("cod.organigramma", "DEFAULT", new ServiceCallback<Record>() {

						@Override
						public void execute(Record recordPref) {
							if (recordPref != null) {
								codRapidoOrganigramma = recordPref.getAttributeAsString("value");
							} 
						}
					});
				}
			}
		});

		loadUserPreference("impostazioniStampa", "DEFAULT", new ServiceCallback<Record>() {

			@Override
			public void execute(Record recordPref) {
				if (recordPref != null) {
					impostazioniStampa = new Record(JSON.decode(recordPref.getAttributeAsString("value")));
				}
			}
		});

		loadUserPreference("impostazioniTimbro", "DEFAULT", new ServiceCallback<Record>() {

			@Override
			public void execute(Record recordPref) {
				if (recordPref != null) {
					impostazioniTimbro = new Record(JSON.decode(recordPref.getAttributeAsString("value")));
				}
			}
		});
		
		loadUserPreference("docScrivania", "DEFAULT", new ServiceCallback<Record>() {

			@Override
			public void execute(Record recordPref) {
				if (recordPref != null) {
					prefScrivania = new Record(JSON.decode(recordPref.getAttributeAsString("value")));
				}
			}
		});
		
		loadUserPreference("prefNotificheMail", "DEFAULT", new ServiceCallback<Record>() {

			@Override
			public void execute(Record recordPref) {
				if ((recordPref != null) && (!AurigaLayout.getParametroDBAsBoolean("IGNORE_PREF_NOTIF_EMAIL"))) {
					Record prefNotifiche = new Record(JSON.decode(recordPref.getAttributeAsString("value")));
					if (!AurigaLayout.getParametroDBAsBoolean("OBBL_MEZZO_TRASM_E")) {
						String assegnazioneCompetenzaDoc = prefNotifiche.getAttribute("assegnazioneCompetenzaDoc");
						String invioConoscenzaDoc = prefNotifiche.getAttribute("invioConoscenzaDoc");
						prefNotifiche.setAttribute("assegnazioneCompetenzaDoc", ((assegnazioneCompetenzaDoc.equals("true") || assegnazioneCompetenzaDoc.equals("false")) ? Boolean.valueOf(assegnazioneCompetenzaDoc) : false));
						prefNotifiche.setAttribute("invioConoscenzaDoc", ((invioConoscenzaDoc.equals("true") || invioConoscenzaDoc.equals("false")) ? Boolean.valueOf(invioConoscenzaDoc) : false));
					}
					prefNotificheMail = new Record(prefNotifiche.toMap());
				}
			}
		});

		loadUserPreference("preVerificaStaffInIterAtti" + getPrefKeySuffixSpecXDominio(), "DEFAULT", new ServiceCallback<Record>() {

			@Override
			public void execute(Record recordPref) {
				if (recordPref != null) {
					prefIterAtti = recordPref.getAttributeAsString("value");
				}
			}
		});
		
		/**
		 * Vengono recuperate le preference di firma dell'utente loggato, anzichè quelle dell'utente delegante.
		 */
		loadUserPreference("impostazioniFirma", "DEFAULT", utenteLoggato.getIdUtente(), new ServiceCallback<Record>() {

			@Override
			public void execute(Record recordPref) {
				if (recordPref != null) {
					impostazioniFirma = new Record(JSON.decode(recordPref.getAttributeAsString("value")));
				}
			}
		});
		
		loadUserPreference("impostazioniFirmaAutomatica", "DEFAULT", utenteLoggato.getIdUtente(), new ServiceCallback<Record>() {

			@Override
			public void execute(Record recordPref) {
				if (recordPref != null) {
					impostazioniFirmaAutomatica = new Record(JSON.decode(recordPref.getAttributeAsString("value")));
				}
			}
		});

		loadUserPreference("impostazioniDocumento", "DEFAULT", new ServiceCallback<Record>() {

			@Override
			public void execute(Record recordPref) {
				if (recordPref != null) {
					impostazioniDocumento = new Record(JSON.decode(recordPref.getAttributeAsString("value")));
				}
			}
		});

		loadUserPreference("impostazioniFascicolo", "DEFAULT", new ServiceCallback<Record>() {

			@Override
			public void execute(Record recordPref) {
				if (recordPref != null) {
					impostazioniFascicolo = new Record(JSON.decode(recordPref.getAttributeAsString("value")));
				}
			}
		});
		
		loadUserPreference("impostazioniSceltaOrganigramma", "DEFAULT", new ServiceCallback<Record>() {

			@Override
			public void execute(Record recordPref) {
				if (recordPref != null) {
					impostazioniSceltaOrganigramma = new Record(JSON.decode(recordPref.getAttributeAsString("value")));
				}
			}
		});
		
		showNewsAlert();
		
		loadUserPreference("impostazioniSceltaAccessibilita", "DEFAULT", new ServiceCallback<Record>() {
			
			@Override
			public void execute(Record recordPref) {
				if (recordPref != null) {
					impostazioniSceltaAccessibilita = new Record(JSON.decode(recordPref.getAttributeAsString("value")));
				}
			}
		});
		
		loadUserPreference("impostazioniAperturaDettaglioPerRicercaPuntuale", "DEFAULT", new ServiceCallback<Record>() {
			
			@Override
			public void execute(Record recordPref) {
				if (recordPref != null) {
					impostazioniAperturaDettaglioPerRicercaPuntuale = new Record(JSON.decode(recordPref.getAttributeAsString("value")));
				}
			}
		});
		
		if (UserInterfaceFactory.getParametroDB("URI_PDF_COOKIE_POLICY") != null && !"".equals(UserInterfaceFactory.getParametroDB("URI_PDF_COOKIE_POLICY").trim())) {
			cookiesButton.show();
		}

		super.afterAggiornaUtente();
	}

	private void clearUserPreferences() {
//		uoLavoro = null; // ATTENZIONE: questa variabile non va' resettata perchè viene letta prima che venga chiamato questo metodo
		attivaProtOttimizzataAllegati = false;
		idUOPuntoProtAttivato = null;
		descUOPuntoProtAttivato = null;
//		funzioneProtocollazione = false; // a cosa serve questa variabile? va resettata? non viene mai letta		
		firmaEmailHtml = "";
		firmeEmailHtml = new LinkedHashMap<String, String>(); 
		firmaEmailPredefinita = "";
		firmaEmailAutoNuova = "";
		firmaEmailAutoRisposta = "";
		firmaEmailAutoInoltro = "";
		impostazioniPdf = "";
		codRapidoOrganigramma = null;
		impostazioniStampa = null;
		impostazioniTimbro = null;
		prefScrivania = null;
		prefNotificheMail = null;
		prefIterAtti = null;
		impostazioniFirma = null;
		impostazioniFirmaAutomatica = null;
		impostazioniDocumento = null;
		impostazioniFascicolo = null;
		impostazioniSceltaOrganigramma = null;
		impostazioniSceltaAccessibilita = null;
		impostazioniAperturaDettaglioPerRicercaPuntuale = null;
	}

	public void addPortalDesktopButtons() {

		createRecords(new ServiceCallback<List<QuickLaunchRecord>>() {

			@Override
			public void execute(List<QuickLaunchRecord> object) {

				layoutDesktopButton.setMembers(createDesktopButtonsLayout(object));
				portalDesktop.setChildren(layoutDesktopButton);
			}
		});

	}
	
	protected void createRecords(final ServiceCallback<List<QuickLaunchRecord>> callback) {
		
		final List<QuickLaunchRecord> retValue = new ArrayList<QuickLaunchRecord>();

		for (int i = 0; i < preferences.getMembers().length; i++) {

			final String nomeEntita = ((PreferenceMenuButton) preferences.getMembers()[i]).getNomeEntita();

			final MenuBean menuBean = menu != null && menu.getVociMenu() != null ? menu.getVociMenu().get(nomeEntita) : null;

			final GenericConfigBean config = Layout.getGenericConfig();

			final boolean canAddQuickLaunch = i < config.getNumMaxShortCut();

			final Integer position = new Integer(i);
			
			String imageUrl = "blank.png";
			String description = null;
//			if(isAttivoRestyling()) {
				if(menuBean.getIcon() != null && !"".equals(menuBean.getIcon())) {
					imageUrl = menuBean.getIcon();
				}
				String title = menuBean.getTitle() != null ? menuBean.getTitle() : "";
				description = "<img src=\"images/" + imageUrl + "\" height=\"32\" width=\"32\" style=\"align:center;padding-bottom:5;\"/><br/><div class='" + it.eng.utility.Styles.portalDestopButtonsTitle + "'>" + title + "</div>";
//			} else {
//				if(menuBean.getPortalDesktopIcon() != null && !"".equals(menuBean.getPortalDesktopIcon())) {
//					imageUrl = menuBean.getPortalDesktopIcon();
//				}
//				description = "<div class='" + it.eng.utility.Styles.portalDestopButtonsTitle + "'><img src=\"images/" + imageUrl + "\" height=\"64\" width=\"64\" style=\"vertical-align:middle;padding-right:5;\"/>&nbsp;&nbsp;&nbsp;" + menuBean.getTitle() + "</div>";
//			}
			
			QuickLaunchRecord qlRecord = new QuickLaunchRecord(position, imageUrl, description, nomeEntita);

			if (canAddQuickLaunch) {
				retValue.add(qlRecord);
			}

			int length = preferences.getMembers().length;
			if (length > config.getNumMaxShortCut()) {
				length = config.getNumMaxShortCut();
			}

			if (callback != null && retValue.size() == preferences.getMembers().length) {
				callback.execute(retValue);
			}
		}
	
		/*
		 * Se arrivo a questo punto vuol dire che non ci sono elementi nella lista e quindi
		 * si deve richiamare la callback ritornando una lista vuota.
		 * In questo modo si aggiorna la grafica ottenendo una lista vuota
		 */
		if (callback != null && retValue != null) {
			callback.execute(retValue);
		}
		
	}

	/**
	 * Crea la la grid contenente le varie tile di lancio rapido delle funzionalità
	 * 
	 * @return
	 */
	public VLayout createDesktopButtonsLayout(List<QuickLaunchRecord> records) {

		Collections.sort(records);

		final VLayout layout = new VLayout();
		layout.setHeight100();
		layout.setWidth100();
		layout.setAlign(Alignment.CENTER);
		layout.setDefaultLayoutAlign(Alignment.CENTER);
		layout.setPadding(20);
		layout.setCanFocus(false);

		if (records.size() > 0) {
			if (AurigaLayout.getIsAttivaAccessibilita()) {
				for (Record singleRecord : records)
				{
					final TileGrid tileGrid = new TileGrid();
					tileGrid.setLayoutAlign(Alignment.CENTER);
	//				tileGrid.setShowAllRecords(true);
					tileGrid.setShowAllRecords(false);
					tileGrid.setCanReorderTiles(false);
					tileGrid.setSelectionType(SelectionStyle.NONE);
					tileGrid.setShowEdges(false);
	//				tileGrid.setCanFocus(true);
					tileGrid.setWrapValues(true);
					tileGrid.setOverflow(Overflow.VISIBLE);
					tileGrid.setStyleName(it.eng.utility.Styles.portalDesktopButtonsTileGrid);
					tileGrid.setTileValueStyle(it.eng.utility.Styles.portalDesktopButtonsTileValue);
					
	//				if(isAttivoRestyling()) {
						tileGrid.setHeight(10);
						tileGrid.setWidth(300);
						tileGrid.setTilesPerLine(3);
						tileGrid.setTileHeight(150);
						tileGrid.setTileWidth(300);						
	//					tileGrid.setHeight(10);
	//					tileGrid.setWidth(940);
	//					tileGrid.setTilesPerLine(3);
	//					tileGrid.setTileHeight(150);
	//					tileGrid.setTileWidth(300);						
	//				} else {
	//					tileGrid.setHeight100();
	//					tileGrid.setWidth(850);
	//					tileGrid.setTilesPerLine(1);
	//					tileGrid.setTileHeight(75);
	//					tileGrid.setTileWidth(800);								
	//				}
					
					DetailViewerField description = new DetailViewerField("description");
					
					tileGrid.setFields(new DetailViewerField[] { description });
	//				tileGrid.setData(records.toArray(new QuickLaunchRecord[records.size()]));
					Record dataRecord[] = new Record[1];
					dataRecord[0]=singleRecord;
					tileGrid.setData(dataRecord);
	
					tileGrid.addRecordClickHandler(new RecordClickHandler() {
	
						@Override
						public void onRecordClick(RecordClickEvent event) {
							Record record = event.getRecord();
							String nomeEntita = record.getAttribute("nomeEntita");
							addPortlet(nomeEntita);
						}
					});
					
					tileGrid.addKeyDownHandler(new KeyDownHandler() {
			            @Override
			            public void onKeyDown(KeyDownEvent event) {
			                if (EventHandler.getKey().equalsIgnoreCase("Enter") || EventHandler.getKey().equalsIgnoreCase("Space") ){
			                	TileGrid test = tileGrid;
			                	Record record2 = test.getData()[0];
								String nomeEntita = record2.getAttribute("nomeEntita");
								addPortlet(nomeEntita);
			                }
			            }
			        });
					
					
	
					layout.addMember(tileGrid);
					layout.redraw();
				}		
			} else {

				TileGrid tileGrid = new TileGrid();
				tileGrid.setLayoutAlign(Alignment.CENTER);
				tileGrid.setShowAllRecords(true);
				tileGrid.setCanReorderTiles(false);
				tileGrid.setSelectionType(SelectionStyle.NONE);
				tileGrid.setShowEdges(false);
				tileGrid.setCanFocus(false);
				tileGrid.setWrapValues(true);
				tileGrid.setOverflow(Overflow.VISIBLE);
				tileGrid.setStyleName(it.eng.utility.Styles.portalDesktopButtonsTileGrid);
				tileGrid.setTileValueStyle(it.eng.utility.Styles.portalDesktopButtonsTileValue);
				
	//			if(isAttivoRestyling()) {
					tileGrid.setHeight(10);
					tileGrid.setWidth(940);
					tileGrid.setTilesPerLine(3);
					tileGrid.setTileHeight(150);
					tileGrid.setTileWidth(300);						
	//			} else {
	//				tileGrid.setHeight100();
	//				tileGrid.setWidth(850);
	//				tileGrid.setTilesPerLine(1);
	//				tileGrid.setTileHeight(75);
	//				tileGrid.setTileWidth(800);								
	//			}
				
				DetailViewerField description = new DetailViewerField("description");
				
				tileGrid.setFields(new DetailViewerField[] { description });
				tileGrid.setData(records.toArray(new QuickLaunchRecord[records.size()]));
	
				tileGrid.addRecordClickHandler(new RecordClickHandler() {
	
					@Override
					public void onRecordClick(RecordClickEvent event) {
						Record record = event.getRecord();
						String nomeEntita = record.getAttribute("nomeEntita");
						addPortlet(nomeEntita);
					}
				});
	
				layout.addMember(tileGrid);
				layout.redraw();
			}
		}

		return layout;
	}

	public void addPortalDesktopButtonsOld() {

		layoutDesktopButton.setMembers(createDesktopButtonsLayoutOld());
		portalDesktop.setChildren(layoutDesktopButton);

	}

	protected QuickLaunchRecord[] createRecordsOld() {

		final List<QuickLaunchRecord> retValue = new ArrayList<QuickLaunchRecord>();

		for (int i = 0; i < preferences.getMembers().length; i++) {
			final String nomeEntita = ((PreferenceMenuButton) preferences.getMembers()[i]).getNomeEntita();

			String imageUrl = "portalDesktopButtons/" + nomeEntita + ".png";

			MenuBean menuBean = menu != null && menu.getVociMenu() != null ? menu.getVociMenu().get(nomeEntita) : null;
			String description = menuBean.getTitle() != null ? menuBean.getTitle() : "";

			QuickLaunchRecord qlRecord = new QuickLaunchRecord(imageUrl, description, nomeEntita);

			GenericConfigBean config = Layout.getGenericConfig();

			if (i < config.getNumMaxShortCut()) {
				retValue.add(qlRecord);
			}
		}

		return retValue.toArray(new QuickLaunchRecord[retValue.size()]);
	}

	public VLayout createDesktopButtonsLayoutOld() {

		final VLayout layout = new VLayout();
		layout.setHeight100();
		layout.setWidth100();
		layout.setAlign(Alignment.CENTER);
		layout.setDefaultLayoutAlign(Alignment.CENTER);

		QuickLaunchRecord[] records = createRecordsOld();
		if (records.length > 0) {

			TileGrid tileGrid = new TileGrid();
			tileGrid.setTileWidth(310);
			tileGrid.setTileHeight(225);
			tileGrid.setAlign(Alignment.CENTER);

			tileGrid.setShowAllRecords(true);
			tileGrid.setCanReorderTiles(false);
			tileGrid.setSelectionType(SelectionStyle.NONE);
			tileGrid.setShowEdges(false);

			if (records.length <= 3) {
				tileGrid.setWidth(records.length * 350);
			} else {
				tileGrid.setWidth(3 * 350);
			}

			tileGrid.setFields(createTileFields());
			tileGrid.setData(records);
			tileGrid.addRecordClickHandler(new RecordClickHandler() {

				@Override
				public void onRecordClick(RecordClickEvent event) {
					Record record = event.getRecord();
					String nomeEntita = record.getAttribute("nomeEntita");
					addPortlet(nomeEntita);
				}
			});

			layout.addMember(tileGrid);

			layout.redraw();
		}

		return layout;
	}

	/**
	 * Definisce quali campi verranno visualizzati in ciascuna tile
	 * 
	 * @return
	 */
	protected DetailViewerField[] createTileFields() {

		DetailViewerField[] fields = new DetailViewerField[2];

		DetailViewerField pictureField = new DetailViewerField("picture");
		pictureField.setType("image");
		pictureField.setImageWidth(200);
		pictureField.setImageHeight(200);
		pictureField.setCellStyle(it.eng.utility.Styles.portalDesktopButton);
		fields[0] = pictureField;

		DetailViewerField descriptionField = new DetailViewerField("description");
		descriptionField.setCellStyle(it.eng.utility.Styles.portalDesktopButton);
		descriptionField.setImageWidth(60);
		descriptionField.setImageHeight(60);
		fields[1] = descriptionField;

		return fields;
	}
	
	public static void getParametriTipoAtto(Record lRecord, final ServiceCallback<Record> callback) {
		if(isAttivaNuovaPropostaAtto2Completa()) {
			
			final String start = DateTimeFormat.getFormat("HH:mm:ss").format(new Date());
			
			GWTRestService<Record, Record> lCallExecAttDatasource = new GWTRestService<Record, Record>("GetParamTipoAttoDatasource");
			lCallExecAttDatasource.call(lRecord, new ServiceCallback<Record>() {
	
				@Override
				public void execute(final Record output) {
					
					GWT.log("getParamTipoAtto() started at " + start + " ended at " + DateTimeFormat.getFormat("HH:mm:ss").format(new Date()));
					
					if (callback != null) {
						callback.execute(output);
					}
				}
			});
		} else {
			if (callback != null) {
				callback.execute(null);
			}
		}
	}
	
	public static void showConfirmDialogWithWarning(String title, String warningMessage, final String confermaButtonTitle, String annullaButtonTitle, final BooleanCallback callback) {
		Layout.showConfirmDialogWithWarning(title, warningMessage, confermaButtonTitle, annullaButtonTitle, callback);
	}
	
	public static void avviaPratica(final Record lRecordAvvio) {
		avviaPratica(lRecordAvvio, null);
	}
	
	public static void avviaPratica(final Record lRecordAvvio, final BooleanCallback afterAvvioIterCallback) {
		Record lRecord = new Record();
		lRecord.setAttribute("idTipoDoc", lRecordAvvio.getAttribute("idTipoDocProposta"));
		getParametriTipoAtto(lRecord, new ServiceCallback<Record>() {
			
			@Override
			public void execute(Record lRecordParamTipoAtto) {
							
				lRecordAvvio.setAttribute("attributiAddDocTabs", lRecordParamTipoAtto != null ? lRecordParamTipoAtto.getAttributeAsRecordList("attributiAddDocTabs") : null);		
				lRecordAvvio.setAttribute("parametriTipoAtto", lRecordParamTipoAtto != null ? lRecordParamTipoAtto.getAttributeAsRecord("parametriTipoAtto") : null);		
				lRecordAvvio.setAttribute("flgPubblicazioneAllegatiUguale", lRecordParamTipoAtto != null ? lRecordParamTipoAtto.getAttributeAsBoolean("flgPubblicazioneAllegatiUguale") : null);
				lRecordAvvio.setAttribute("flgAvvioLiquidazioneContabilia", lRecordParamTipoAtto != null ? lRecordParamTipoAtto.getAttributeAsBoolean("flgAvvioLiquidazioneContabilia") : null);
				lRecordAvvio.setAttribute("flgNumPropostaDiffXStruttura", lRecordParamTipoAtto != null ? lRecordParamTipoAtto.getAttributeAsBoolean("flgNumPropostaDiffXStruttura") : null);
				
				ModalWindow avvioIterModalWindow = new ModalWindow("avvio_iter_atti", true, false) {

					@Override
					public void manageOnCloseClick() {
						markForDestroy();
					}
				};
				avvioIterModalWindow.setTitle("Avvio proposta di " + lRecordAvvio.getAttribute("nomeTipoProc").toLowerCase());
				avvioIterModalWindow.setIcon("menu/iter_atti.png");
				avvioIterModalWindow.setMaximized(true);
				// visto che è massimizzata la rendo non modale per far funzionare il tabulatore (Mattia Zanin)
				avvioIterModalWindow.setIsModal(false);
				avvioIterModalWindow.setHeaderControls(HeaderControls.HEADER_ICON, HeaderControls.HEADER_LABEL, HeaderControls.CLOSE_BUTTON);
				
				VLayout lVLayout = new VLayout();
				lVLayout.setHeight100();
				lVLayout.setWidth100();
				lVLayout.setTop(5);
				
				AvvioPraticaLayout avvioPraticaLayout = new AvvioPraticaLayout(lRecordAvvio, avvioIterModalWindow, afterAvvioIterCallback);
				lVLayout.setMembers(avvioPraticaLayout);

				avvioIterModalWindow.setBody(lVLayout);
				avvioIterModalWindow.show();
			}
		});		
	}
	
	public static void avviaRevocaAtto(final Record lRecordAtto) {
		Record lRecord = new Record();
		lRecord.setAttribute("idTipoDoc", lRecordAtto.getAttribute("idTipoDocPropostaRevocaAtto"));
		getParametriTipoAtto(lRecord, new ServiceCallback<Record>() {
			
			@Override
			public void execute(Record lRecordParamTipoAtto) {
				
				if (lRecordAtto.getAttribute("idTipoProcRevocaAtto") != null && !"".equals(lRecordAtto.getAttribute("idTipoProcRevocaAtto"))) {
					
					final String estremiAtto = lRecordAtto.getAttribute("siglaRegistrazione") + " " + lRecordAtto.getAttribute("numeroRegistrazione") + "/" + lRecordAtto.getAttribute("annoRegistrazione");						
					
					Record lRecordAvvio = new Record();
					lRecordAvvio.setAttribute("idTipoProc", lRecordAtto.getAttribute("idTipoProcRevocaAtto"));
					lRecordAvvio.setAttribute("nomeTipoProc", lRecordAtto.getAttribute("nomeTipoProcRevocaAtto"));
					lRecordAvvio.setAttribute("idTipoFlussoActiviti", lRecordAtto.getAttribute("idDefFlussoWFRevocaAtto"));
					lRecordAvvio.setAttribute("idTipoDocProposta", lRecordAtto.getAttribute("idTipoDocPropostaRevocaAtto"));
					lRecordAvvio.setAttribute("nomeTipoDocProposta", lRecordAtto.getAttribute("nomeTipoDocPropostaRevocaAtto"));
					lRecordAvvio.setAttribute("siglaProposta", lRecordAtto.getAttribute("siglaPropostaRevocaAtto"));
					//ci sono altri dati con cui prepopolare la maschera di revoca atto?
					Record lRecordInitialValues = new Record();
					if(AurigaLayout.isAttivaNuovaPropostaAtto2Completa()) {
						//da rivedere che valori iniziali mettere nella maschera degli atti generica (completa)						
						lRecordInitialValues.setAttribute("ufficioProponente", lRecordAtto.getAttribute("ufficioProponente"));
						lRecordInitialValues.setAttribute("codUfficioProponente", lRecordAtto.getAttribute("codUfficioProponente"));
						lRecordInitialValues.setAttribute("desUfficioProponente", lRecordAtto.getAttribute("desUfficioProponente"));
						lRecordInitialValues.setAttribute("listaUfficioProponente", lRecordAtto.getAttributeAsRecordList("listaUfficioProponente"));
						lRecordInitialValues.setAttribute("listaRdP", lRecordAtto.getAttributeAsRecordList("listaRdP"));
						lRecordInitialValues.setAttribute("listaRUP", lRecordAtto.getAttributeAsRecordList("listaRUP"));
						lRecordInitialValues.setAttribute("listaDirigentiConcerto", lRecordAtto.getAttributeAsRecordList("listaDirigentiConcerto"));
						lRecordInitialValues.setAttribute("flgSpesa", "NO");
//						lRecordInitialValues.setAttribute("idUdAttoDeterminaAContrarre", lRecordAtto.getAttribute("idUd"));
//						lRecordInitialValues.setAttribute("categoriaRegAttoDeterminaAContrarre", "R");
//						lRecordInitialValues.setAttribute("siglaAttoDeterminaAContrarre", lRecordAtto.getAttribute("siglaRegistrazione"));
//						lRecordInitialValues.setAttribute("numeroAttoDeterminaAContrarre", lRecordAtto.getAttribute("numeroRegistrazione"));
//						lRecordInitialValues.setAttribute("annoAttoDeterminaAContrarre", lRecordAtto.getAttribute("annoRegistrazione"));
						RecordList listaAttiRiferimento = new RecordList();
						Record lRecordAttoDaAnnullare = new Record();
						lRecordAttoDaAnnullare.setAttribute("flgPresenteASistema", "SI");
						lRecordAttoDaAnnullare.setAttribute("idUd", lRecordAtto.getAttribute("idUd"));
						lRecordAttoDaAnnullare.setAttribute("categoriaReg", "R");
						lRecordAttoDaAnnullare.setAttribute("sigla", lRecordAtto.getAttribute("siglaRegistrazione"));
						lRecordAttoDaAnnullare.setAttribute("numero", lRecordAtto.getAttribute("numeroRegistrazione"));
						lRecordAttoDaAnnullare.setAttribute("anno", lRecordAtto.getAttribute("annoRegistrazione"));
						listaAttiRiferimento.add(lRecordAttoDaAnnullare);
						lRecordInitialValues.setAttribute("listaAttiRiferimento", listaAttiRiferimento);
						lRecordInitialValues.setAttribute("oggetto", "Annullamento/revoca/ritiro " + lRecordAtto.getAttribute("nomeTipoDocumento") + " " + estremiAtto);
						lRecordInitialValues.setAttribute("oggettoHtml", "Annullamento/revoca/ritiro " + lRecordAtto.getAttribute("nomeTipoDocumento") + " " + estremiAtto);
					} else {
						lRecordInitialValues.setAttribute("ufficioProponente", lRecordAtto.getAttribute("ufficioProponente"));
						lRecordInitialValues.setAttribute("codUfficioProponente", lRecordAtto.getAttribute("codUfficioProponente"));
						lRecordInitialValues.setAttribute("desUfficioProponente", lRecordAtto.getAttribute("desUfficioProponente"));
						lRecordInitialValues.setAttribute("listaUfficioProponente", lRecordAtto.getAttributeAsRecordList("listaUfficioProponente"));
						lRecordInitialValues.setAttribute("listaRdP", lRecordAtto.getAttributeAsRecordList("listaRdP"));
						lRecordInitialValues.setAttribute("listaRUP", lRecordAtto.getAttributeAsRecordList("listaRUP"));
						lRecordInitialValues.setAttribute("listaDirigentiConcerto", lRecordAtto.getAttributeAsRecordList("listaDirigentiConcerto"));
						lRecordInitialValues.setAttribute("flgSpesa", "NO");
						lRecordInitialValues.setAttribute("idUdAttoDeterminaAContrarre", lRecordAtto.getAttribute("idUd"));
						lRecordInitialValues.setAttribute("categoriaRegAttoDeterminaAContrarre", "R");
						lRecordInitialValues.setAttribute("siglaAttoDeterminaAContrarre", lRecordAtto.getAttribute("siglaRegistrazione"));
						lRecordInitialValues.setAttribute("numeroAttoDeterminaAContrarre", lRecordAtto.getAttribute("numeroRegistrazione"));
						lRecordInitialValues.setAttribute("annoAttoDeterminaAContrarre", lRecordAtto.getAttribute("annoRegistrazione"));
						lRecordInitialValues.setAttribute("oggetto", "Annullamento/revoca/ritiro " + lRecordAtto.getAttribute("nomeTipoDocumento") + " " + estremiAtto);
						lRecordInitialValues.setAttribute("oggettoHtml", "Annullamento/revoca/ritiro " + lRecordAtto.getAttribute("nomeTipoDocumento") + " " + estremiAtto);						
					}
					lRecordAvvio.setAttribute("recordInitialValues", lRecordInitialValues);
					lRecordAvvio.setAttribute("attributiAddDocTabs", lRecordParamTipoAtto != null ? lRecordParamTipoAtto.getAttributeAsRecordList("attributiAddDocTabs") : null);			
					lRecordAvvio.setAttribute("flgPubblicazioneAllegatiUguale", lRecordParamTipoAtto != null ? lRecordParamTipoAtto.getAttributeAsBoolean("flgPubblicazioneAllegatiUguale") : null);											
					lRecordAvvio.setAttribute("parametriTipoAtto", lRecordParamTipoAtto != null ? lRecordParamTipoAtto.getAttributeAsRecord("parametriTipoAtto") : null);			
					
					final ModalWindow avvioRevocaAttoModalWindow = new ModalWindow("avvio_revoca_atto", true, false) {

						@Override
						public void manageOnCloseClick() {
							markForDestroy();
						}
					};
					avvioRevocaAttoModalWindow.setTitle("Avvio iter di revoca/annullamento/ritiro atto " + estremiAtto);
					avvioRevocaAttoModalWindow.setIcon("buttons/revoca_atto.png");
					avvioRevocaAttoModalWindow.setMaximized(true);
					// visto che è massimizzata la rendo non modale per far funzionare il tabulatore (Mattia Zanin)
					avvioRevocaAttoModalWindow.setIsModal(false);
					avvioRevocaAttoModalWindow.setHeaderControls(HeaderControls.HEADER_ICON, HeaderControls.HEADER_LABEL, HeaderControls.CLOSE_BUTTON);

					VLayout lVLayout = new VLayout();
					lVLayout.setHeight100();
					lVLayout.setWidth100();
					lVLayout.setTop(5);
					
					AvvioPraticaLayout avvioRevocaLayout = new AvvioPraticaLayout(lRecordAvvio, avvioRevocaAttoModalWindow) {
						
						@Override
						public boolean isAvvioIterRevocaAtto() {
							return true;
						}
						
						@Override
						protected String getIntestazioneProgetto() {
							return "Avvio iter di revoca/annullamento/ritiro atto " + estremiAtto;							
						}
					};
					lVLayout.setMembers(avvioRevocaLayout);

					avvioRevocaAttoModalWindow.setBody(lVLayout);
					avvioRevocaAttoModalWindow.show();
				} else {
					Layout.addMessage(new MessageBean("Nessun tipo di revoca/annullamento atto disponibile", "", MessageType.ERROR));
				}				
			}
		});		
	}
	
	public static void avviaEmendamentoSuAtto(final Record lRecordAtto) {
		Record lRecord = new Record();
		lRecord.setAttribute("idTipoDoc", lRecordAtto.getAttribute("idTipoDocPropostaEmendamento"));
		getParametriTipoAtto(lRecord, new ServiceCallback<Record>() {
			
			@Override
			public void execute(Record lRecordParamTipoAtto) {
				
				if (lRecordAtto.getAttribute("idTipoProcEmendamento") != null && !"".equals(lRecordAtto.getAttribute("idTipoProcEmendamento"))) {
					
					final String estremiAtto = lRecordAtto.getAttribute("siglaRegProvvisoria") + " " + lRecordAtto.getAttribute("numeroRegProvvisoria") + "/" + lRecordAtto.getAttribute("annoRegProvvisoria");						
					
					Record lRecordAvvio = new Record();
					lRecordAvvio.setAttribute("idTipoProc", lRecordAtto.getAttribute("idTipoProcEmendamento"));
					lRecordAvvio.setAttribute("nomeTipoProc", lRecordAtto.getAttribute("nomeTipoProcEmendamento"));
					lRecordAvvio.setAttribute("idTipoFlussoActiviti", lRecordAtto.getAttribute("idDefFlussoWFEmendamento"));
					lRecordAvvio.setAttribute("idTipoDocProposta", lRecordAtto.getAttribute("idTipoDocPropostaEmendamento"));
					lRecordAvvio.setAttribute("nomeTipoDocProposta", lRecordAtto.getAttribute("nomeTipoDocPropostaEmendamento"));
					lRecordAvvio.setAttribute("siglaProposta", lRecordAtto.getAttribute("siglaPropostaEmendamento"));
					Record lRecordInitialValues = new Record();
					lRecordInitialValues.setAttribute("tipoAttoEmendamento", lRecordAtto.getAttribute("tipoAttoRifEmendamento"));
					lRecordInitialValues.setAttribute("siglaAttoEmendamento", lRecordAtto.getAttribute("siglaAttoRifEmendamento"));
					lRecordInitialValues.setAttribute("numeroAttoEmendamento", lRecordAtto.getAttribute("numeroAttoRifEmendamento"));
					lRecordInitialValues.setAttribute("annoAttoEmendamento", lRecordAtto.getAttribute("annoAttoRifEmendamento"));
					lRecordInitialValues.setAttribute("idEmendamento", lRecordAtto.getAttribute("idEmendamentoRif"));
					lRecordInitialValues.setAttribute("numeroEmendamento", lRecordAtto.getAttribute("numeroEmendamentoRif"));					
					lRecordAvvio.setAttribute("recordInitialValues", lRecordInitialValues);
					lRecordAvvio.setAttribute("attributiAddDocTabs", lRecordParamTipoAtto != null ? lRecordParamTipoAtto.getAttributeAsRecordList("attributiAddDocTabs") : null);			
					lRecordAvvio.setAttribute("flgPubblicazioneAllegatiUguale", lRecordParamTipoAtto != null ? lRecordParamTipoAtto.getAttributeAsBoolean("flgPubblicazioneAllegatiUguale") : null);											
					lRecordAvvio.setAttribute("parametriTipoAtto", lRecordParamTipoAtto != null ? lRecordParamTipoAtto.getAttributeAsRecord("parametriTipoAtto") : null);			
					
					final ModalWindow avvioEmendamentoModalWindow = new ModalWindow("avvio_emendamento_atto", true, false) {

						@Override
						public void manageOnCloseClick() {
							markForDestroy();
						}
					};
					avvioEmendamentoModalWindow.setTitle("Avvio proposta emendamento su atto " + estremiAtto);
					avvioEmendamentoModalWindow.setIcon("menu/avvio_procedimento.png");
					avvioEmendamentoModalWindow.setMaximized(true);
					// visto che è massimizzata la rendo non modale per far funzionare il tabulatore (Mattia Zanin)
					avvioEmendamentoModalWindow.setIsModal(false);
					avvioEmendamentoModalWindow.setHeaderControls(HeaderControls.HEADER_ICON, HeaderControls.HEADER_LABEL, HeaderControls.CLOSE_BUTTON);

					VLayout lVLayout = new VLayout();
					lVLayout.setHeight100();
					lVLayout.setWidth100();
					lVLayout.setTop(5);
					
					AvvioPraticaLayout avvioEmendamentoLayout = new AvvioPraticaLayout(lRecordAvvio, avvioEmendamentoModalWindow) {
						
						@Override
						protected String getIntestazioneProgetto() {
							return "Avvio proposta emendamento su atto " + estremiAtto;							
						}
					};
					lVLayout.setMembers(avvioEmendamentoLayout);

					avvioEmendamentoModalWindow.setBody(lVLayout);
					avvioEmendamentoModalWindow.show();
				} else {
					Layout.addMessage(new MessageBean("Nessun tipo di emendamento disponibile", "", MessageType.ERROR));
				}				
			}
		});	
	}
	
	public static void apriDettaglioPraticaSenzaFlusso(Record lRecord, String title, boolean isEditMode) {
		new ApriDettaglioPraticaSenzaFlussoWindow(lRecord, title, isEditMode);
	}
	
	public static void apriDettaglioPraticaSenzaFlusso(Record lRecord, String title) {
		apriDettaglioPraticaSenzaFlusso(lRecord, title, false);
	}
	
	public static void apriDettaglioPratica(String idPratica, String estremi) {
		apriDettaglioPratica(idPratica, estremi, null, null);
	}

	public static void apriDettaglioPratica(String idPratica, String estremi, final BooleanCallback closeCallback) {
		apriDettaglioPratica(idPratica, estremi, null, closeCallback);
	}
	
	public static void apriDettaglioPratica(final String idPratica, final String estremi, final List<CustomTaskButton> customButtons, final BooleanCallback closeCallback) {
		Record lRecordToLoad = new Record();
		lRecordToLoad.setAttribute("idPratica", idPratica);
		
		GWTRestDataSource praticheDS = new GWTRestDataSource("PraticheDataSource", "idPratica", FieldType.TEXT);
		praticheDS.addParam("isAperturaDettaglioPratica", "true");

		final String start = DateTimeFormat.getFormat("HH:mm:ss").format(new Date());
		
		praticheDS.getData(lRecordToLoad, new DSCallback() {
			@Override
			public void execute(DSResponse response, Object rawData,
					DSRequest request) {
				
				GWT.log("loadDatiPratica() started at " + start + " ended at " + DateTimeFormat.getFormat("HH:mm:ss").format(new Date()));
				
				if(response.getStatus() == DSResponse.STATUS_SUCCESS) {						
					final Record lRecordDettaglioPratica = response.getData()[0];
					if(lRecordDettaglioPratica.getAttribute("warningConcorrenza") != null && !"".equals(lRecordDettaglioPratica.getAttribute("warningConcorrenza"))) {
						AurigaLayout.showConfirmDialogWithWarning("Attenzione!", lRecordDettaglioPratica.getAttribute("warningConcorrenza"), "Procedi", "Annulla", new BooleanCallback() {

							@Override
							public void execute(Boolean value) {
								if (value != null && value) {
									apriDettaglioPratica(idPratica, estremi, lRecordDettaglioPratica, customButtons, closeCallback);
								}
							}
						});
					} else {
						apriDettaglioPratica(idPratica, estremi, lRecordDettaglioPratica, customButtons, closeCallback);
					}
				}
			}
		});
	}
	
	public static void apriDettaglioPratica(String idPratica, String estremi, final Record recordDettaglio, final List<CustomTaskButton> customButtons, final BooleanCallback closeCallback) {		
		final ModalWindow modalWindow = new ModalWindow("dettaglio_pratica", true, false) {

			@Override
			public void manageOnCloseClick() {
				markForDestroy();
				if (closeCallback != null) {
					try {
						ApriDettaglioPraticaLayout dettaglioPratica = (ApriDettaglioPraticaLayout) ((VLayout) getBody()).getMember(0);
						closeCallback.execute(dettaglioPratica.isSaved());
					} catch(Exception e) {
						closeCallback.execute(true);
					}					
				}
			}
		};
		modalWindow.setTitle(estremi);
		modalWindow.setIcon("menu/pratiche.png");
		modalWindow.setMaximized(true);
		modalWindow.setIsModal(false); // visto che è massimizzata la rendo non modale per far funzionare il tabulatore (Mattia Zanin)
		modalWindow.setHeaderControls(HeaderControls.HEADER_ICON, HeaderControls.HEADER_LABEL, HeaderControls.CLOSE_BUTTON);

		VLayout lVLayout = new VLayout();
		lVLayout.setHeight100();
		lVLayout.setWidth100();
		lVLayout.setTop(5);
		
		ApriDettaglioPraticaLayout dettaglioPratica = new ApriDettaglioPraticaLayout(idPratica, estremi, recordDettaglio) {
			
			@Override
			public List<CustomTaskButton> getListaCustomButtons() {
				return customButtons;
			}

			@Override
			public boolean isFrontOffice() {
				return false;
			}

			@Override
			public void managePraticaConclusa() {
				modalWindow.markForDestroy();
				if (closeCallback != null) {
					closeCallback.execute(isSaved());
				}
			}
		};
		
		lVLayout.setMembers(dettaglioPratica);

		modalWindow.setBody(lVLayout);
		modalWindow.show();
	}
	
	public static void smistaAtto(final CustomLayout layoutReloadList, final Record recordAtto, final ServiceCallback<Record> afterSmistaCallback) {
		final RecordList listaRecord = new RecordList();
		listaRecord.add(recordAtto);
		new SmistamentoAttiPopup((listaRecord.getLength() == 1) ? listaRecord.get(0) : null) {

			@Override
			public void onClickOkButton(final DSCallback callback) {
				final Record record = new Record();
				record.setAttribute("listaRecord", listaRecord);

				if(AurigaLayout.isAttivoClienteCOTO()) {
					record.setAttribute("listaUfficioLiquidatore", _form.getValueAsRecordList("listaUfficioLiquidatore"));
				}
				
				if(AurigaLayout.isAttivoSmistamentoAttiGruppi()) {
					record.setAttribute("smistamentoGruppi", _form.getValue("smistamentoGruppi"));
				}
				
				if(AurigaLayout.isAttivoSmistamentoAttiRagioneria()) {
					record.setAttribute("assegnatarioSmistamentoRagioneria", _form.getValue("assegnatarioSmistamentoRagioneria"));
					record.setAttribute("smistamentoRagioneria", _form.getValue("smistamentoRagioneria"));							
				} else {
					record.setAttribute("listaSmistamento", _form.getValueAsRecordList("listaSmistamento"));
				}

				Layout.showWaitPopup("Smistamento in corso: potrebbe richiedere qualche secondo. Attendere…");
				GWTRestDataSource lGwtRestDataSource = new GWTRestDataSource("SmistamentoAttiDataSource");
				try {
					lGwtRestDataSource.addData(record, new DSCallback() {

						@Override
						public void execute(DSResponse response, Object rawData, DSRequest request) {							
							Layout.hideWaitPopup();
							if (response.getStatus() == DSResponse.STATUS_SUCCESS) {							
								Record data = response.getData()[0];
								Map errorMessages = data.getAttributeAsMap("errorMessages");
								if (errorMessages != null && errorMessages.size() == 1) {
									String errorMsg = (String) errorMessages.get(recordAtto.getAttribute("idProcedimento"));
									Layout.addMessage(new MessageBean(errorMsg, "", MessageType.ERROR));
								} else {
									if(layoutReloadList != null) {
										layoutReloadList.doSearch();
									}
									Layout.addMessage(new MessageBean("Smistamento effettuato con successo", "", MessageType.INFO));
									if(callback != null) {
										callback.execute(response, rawData, request);
									}
									if(afterSmistaCallback != null) {
										afterSmistaCallback.execute(new Record());
									}									
								}
							} 
						}
					});
				} catch (Exception e) {
					Layout.hideWaitPopup();
				}
			}
		};
	}
	
	public static void condividiAtto(final CustomLayout layoutReloadList, final Record recordAtto, final ServiceCallback<Record> afterCondivisioneCallback) {
		final RecordList listaRecord = new RecordList();
		listaRecord.add(recordAtto);
		new CondivisioneAttiPopup((listaRecord.getLength() == 1) ? listaRecord.get(0) : null) {

			@Override
			public void onClickOkButton(final DSCallback callback) {
				final Record record = new Record();
				record.setAttribute("listaRecord", listaRecord);
				record.setAttribute("listaCondivisione", _form.getValueAsRecordList("listaCondivisione"));
				
				Layout.showWaitPopup("Condivisione in corso: potrebbe richiedere qualche secondo. Attendere…");
				GWTRestDataSource lGwtRestDataSource = new GWTRestDataSource("CondivisioneAttiDataSource");
				try {
					lGwtRestDataSource.addData(record, new DSCallback() {

						@Override
						public void execute(DSResponse response, Object rawData, DSRequest request) {							
							Layout.hideWaitPopup();
							if (response.getStatus() == DSResponse.STATUS_SUCCESS) {							
								Record data = response.getData()[0];
								Map errorMessages = data.getAttributeAsMap("errorMessages");
								if (errorMessages != null && errorMessages.size() == 1) {
									String errorMsg = (String) errorMessages.get(recordAtto.getAttribute("idProcedimento"));
									Layout.addMessage(new MessageBean(errorMsg, "", MessageType.ERROR));
								} else {
									if(layoutReloadList != null) {
										layoutReloadList.doSearch();
									}
									Layout.addMessage(new MessageBean("Condivisione effettuata con successo", "", MessageType.INFO));
									if(callback != null) {
										callback.execute(response, rawData, request);
									}
									if(afterCondivisioneCallback != null) {
										afterCondivisioneCallback.execute(new Record());
									}									
								}
							} 
						}
					});
				} catch (Exception e) {
					Layout.hideWaitPopup();
				}
			}
		};
	}

	public static void apriSceltaRepertorioPopup(final String flgTipoProv, final String repertorioDefault, final ServiceCallback<Record> callback) {

		GWTRestDataSource gruppiRepertorioDS = new GWTRestDataSource("LoadComboGruppiRepertorioSource", "key", FieldType.TEXT);
		gruppiRepertorioDS.addParam("flgTipoProv", flgTipoProv);
		gruppiRepertorioDS.addParam("isAttivaAccessibilita", ""+AurigaLayout.getIsAttivaAccessibilita());
		gruppiRepertorioDS.fetchData(null, new DSCallback() {

			@Override
			public void execute(DSResponse response, Object rawData, DSRequest request) {

				if (response.getData().length == 0) {
					addMessage(new MessageBean("Nessun repertorio abilitato", "", MessageType.ERROR));
				} else if (response.getData().length == 1) {
					Record lRecord = new Record();
					lRecord.setAttribute("repertorio", response.getData()[0].getAttribute("key"));					
					if (callback != null) {
						callback.execute(lRecord);
					}
				} else {
					 new SceltaRepertorioPopup(flgTipoProv, repertorioDefault, response.getData(), callback);
				}
			}
		});
	}

	public static void apriSceltaTipoRichiestaAccessoAttiPopup(final String idTipoDocDefault, final ServiceCallback<Record> callback) {

		GWTRestDataSource idTipoRichiestaAccessoAttiDS = new GWTRestDataSource("LoadComboTipoRichiestaAccessoAttiDataSource", "idTipoDocumento", FieldType.TEXT,
				true);
		idTipoRichiestaAccessoAttiDS.fetchData(null, new DSCallback() {

			@Override
			public void execute(DSResponse response, Object rawData, DSRequest request) {

				if (response.getData().length == 0) {
					Layout.addMessage(new MessageBean("Non è definito alcun tipo di richiesta accesso atti", "", MessageType.ERROR));
				} else if (response.getData().length == 1) {
					Record lRecord = new Record();
					lRecord.setAttribute("idTipoDocumento", response.getData()[0].getAttribute("idTipoDocumento"));
					lRecord.setAttribute("descTipoDocumento", response.getData()[0].getAttribute("descTipoDocumento"));
					lRecord.setAttribute("flgTipoDocConVie", response.getData()[0].getAttributeAsBoolean("flgTipoDocConVie"));
					lRecord.setAttribute("siglaPraticaSuSistUfficioRichiedente", response.getData()[0].getAttribute("siglaPraticaSuSistUfficioRichiedente"));
					lRecord.setAttribute("idNodoDefaultRicercaAtti", response.getData()[0].getAttribute("idNodoDefaultRicercaAtti"));
					if (callback != null) {
						callback.execute(lRecord);
					}
				} else {
					new SceltaTipoRichiestaAccessoAttiPopup(idTipoDocDefault, callback);
				}
			}
		});

	}
	
	public static void apriSceltaTipoFlussoIstanzaPopup(final String tipoIstanza, final ServiceCallback<Record> callback) {
		
		GWTRestDataSource idTipoFlussoIstanzaDS = new GWTRestDataSource("LoadComboTipoFlussoIstanzaDataSource", "flowTypeId", FieldType.TEXT);
		idTipoFlussoIstanzaDS.addParam("tipoIstanza", tipoIstanza);
		
		idTipoFlussoIstanzaDS.fetchData(null, new DSCallback() {

			@Override
			public void execute(DSResponse response, Object rawData, DSRequest request) {

				if (response.getData().length == 0) {
					Layout.addMessage(new MessageBean("Non è definito alcun tipo di flusso per l'istanza " + tipoIstanza, "", MessageType.ERROR));
				} else if (response.getData().length == 1) {
					Record lRecord = new Record();
					lRecord.setAttribute("flowTypeId", response.getData()[0].getAttribute("flowTypeId"));
					lRecord.setAttribute("idProcessType", response.getData()[0].getAttribute("idProcessType"));
					lRecord.setAttribute("nomeProcessType", response.getData()[0].getAttribute("nomeProcessType"));
					lRecord.setAttribute("idDocTypeFinale", response.getData()[0].getAttribute("idDocTypeFinale"));
					lRecord.setAttribute("nomeDocTypeFinale", response.getData()[0].getAttribute("nomeDocTypeFinale"));
					if (callback != null) {
						callback.execute(lRecord);
					}
				} else {
					new SceltaTipoFlussoIstanzaPopup(tipoIstanza, callback);
				}
			}
		});

	}

	public static void apriSceltaTipoDocPopup(final boolean required, final String idTipoDocDefault, final String categoriaReg, final String siglaReg,
			final ServiceCallback<Record> callback) {

		if (AurigaLayout.getParametroDBAsBoolean("ATTIVA_ATT_CUSTOM_TIPO_GUI")) {

			final boolean isRepertorio = categoriaReg != null && "R".equals(categoriaReg);
			
			GWTRestDataSource idTipoDocumentoDS = new GWTRestDataSource("LoadComboTipoDocumentoDataSource", "idTipoDocumento", FieldType.TEXT, true);
			idTipoDocumentoDS.addParam("showErrorMsg", "true");
			idTipoDocumentoDS.addParam("categoriaReg", categoriaReg);
			idTipoDocumentoDS.addParam("siglaReg", siglaReg);
			idTipoDocumentoDS.fetchData(null, new DSCallback() {

				@Override
				public void execute(DSResponse response, Object rawData, DSRequest request) {

					if (response.getData().length == 0 || ((required || isRepertorio) && response.getData().length == 1)) {
						Record lRecord = new Record();
						if ((required || isRepertorio) && response.getData().length == 1) {
							lRecord.setAttribute("idTipoDocumento", response.getData()[0].getAttribute("idTipoDocumento"));
							lRecord.setAttribute("descTipoDocumento", response.getData()[0].getAttribute("descTipoDocumento"));
							lRecord.setAttribute("codCategoriaAltraNumerazione", response.getData()[0].getAttribute("codCategoriaAltraNumerazione"));
							lRecord.setAttribute("siglaAltraNumerazione", response.getData()[0].getAttribute("siglaAltraNumerazione"));
							lRecord.setAttribute("flgTipoDocConVie", response.getData()[0].getAttributeAsBoolean("flgTipoDocConVie"));
							lRecord.setAttribute("flgOggettoNonObblig", response.getData()[0].getAttributeAsBoolean("flgOggettoNonObblig"));							
							lRecord.setAttribute("flgTipoProtModulo", response.getData()[0].getAttribute("flgTipoProtModulo"));
						}
						if (callback != null) {
							callback.execute(lRecord);
						}
					} else {
						new SceltaTipoDocPopup(required, idTipoDocDefault,null, categoriaReg, siglaReg, null, callback);
					}
				}
			});
		} else if (callback != null) {
			callback.execute(null);
		}
	}

	public static void apriSceltaTipoFolderPopup(final boolean required, final String idFolderTypeDefault, final Record record,
			final ServiceCallback<Record> callback) {

		if (AurigaLayout.getParametroDBAsBoolean("ATTIVA_ATT_CUSTOM_TIPO_GUI")) {

			final GWTRestDataSource idFolderTypeDS = new GWTRestDataSource("LoadComboTipoFolderDataSource", "idFolderType", FieldType.TEXT);
			idFolderTypeDS.addParam("showErrorMsg", "true");
			if(record != null) {
				idFolderTypeDS.addParam("idClassifica", record.getAttribute("idClassifica"));
				idFolderTypeDS.addParam("idFolderApp", record.getAttribute("idFolderApp"));
				idFolderTypeDS.addParam("idFolderType", record.getAttribute("idFolderType"));
			}

			idFolderTypeDS.fetchData(null, new DSCallback() {

				@Override
				public void execute(DSResponse response, Object rawData, DSRequest request) {

					if (response.getData().length == 0 || (required && response.getData().length == 1)) {
						Record lRecord = new Record(record.getJsObj());
						if (required && response.getData().length == 1) {
							lRecord.setAttribute("idFolderType", response.getData()[0].getAttribute("idFolderType"));
							lRecord.setAttribute("descFolderType", response.getData()[0].getAttribute("descFolderType"));
							lRecord.setAttribute("templateNomeFolder", response.getData()[0].getAttribute("templateNomeFolder"));							
							lRecord.setAttribute("flgTipoFolderConVie", response.getData()[0].getAttributeAsBoolean("flgTipoFolderConVie"));
							lRecord.setAttribute("dictionaryEntrySezione", response.getData()[0].getAttribute("dictionaryEntrySezione"));
						}
						if (callback != null) {
							callback.execute(lRecord);
						}
					} else {
						new SceltaTipoFolderPopup(required, idFolderTypeDefault,null, record, callback);
					}
				}
			});
		} else if (callback != null) {
			callback.execute(record);
		}
	}
	
	public static void apriSceltaCommissione(final ServiceCallback<Record> callback) {
		
		GWTRestDataSource convocazioneCommissioneDS = new GWTRestDataSource("LoadComboConvocazioneCommissioneSource", "key", FieldType.TEXT);
		convocazioneCommissioneDS.fetchData(null, new DSCallback() {

			@Override
			public void execute(DSResponse response, Object rawData, DSRequest request) {

				if (response.getData().length == 0) {
					Layout.addMessage(new MessageBean("Nessuna commissione configurata", "", MessageType.ERROR));
				} else if (response.getData().length == 1) {
					Record lRecord = new Record();
					lRecord.setAttribute("listaCommissioni", response.getData()[0].getAttribute("key"));	
					if (callback != null) {
						callback.execute(lRecord);
					}
				} else {
					 new SceltaCommissionePopup(response.getData(), callback);
				}
			}
		});
	}
	
	public static void apriSceltaSedutaOrganiCollegiali(String organo,String finalita,final ServiceCallback<Record> callback) {
		
		GWTRestDataSource organiCollegialiDS = new GWTRestDataSource("LoadComboSeduteOrganiCollegialiDataSource", "key", FieldType.TEXT);
		organiCollegialiDS.addParam("organo", organo);
		organiCollegialiDS.addParam("finalita", finalita);
		organiCollegialiDS.fetchData(null, new DSCallback() {

			@Override
			public void execute(DSResponse response, Object rawData, DSRequest request) {

				if (response.getData().length == 0) {
					Record lRecord = new Record();
					if (callback != null) {
						callback.execute(lRecord);
					}
				} else if( response.getData().length == 1) {
					Record lRecord = new Record();
					lRecord.setAttribute("idSeduta", response.getData()[0].getAttribute("key"));
					lRecord.setAttribute("storico", response.getData()[0].getAttribute("storico"));					
					if (callback != null) {
						callback.execute(lRecord);
					}
				} else {
					 new SceltaSedutaOrganoCollegialePopup(response.getData(), callback);
				}
			}
		});
	}
	
	public static void apriSceltaSedutaOrganiCollegialiDiscussione(String organo,String finalita,final ServiceCallback<Record> callback) {
		
		GWTRestDataSource organiCollegialiDS = new GWTRestDataSource("LoadComboSeduteOrganiCollegialiDataSource", "key", FieldType.TEXT);
		organiCollegialiDS.addParam("organo", organo);
		organiCollegialiDS.addParam("finalita", finalita);
		organiCollegialiDS.fetchData(null, new DSCallback() {

			@Override
			public void execute(DSResponse response, Object rawData, DSRequest request) {

				if (response.getData().length == 0) {
					Layout.addMessage(new MessageBean("Nessun seduta in discussione", "", MessageType.ERROR));
				} else if( response.getData().length == 1) {
					Record lRecord = new Record();
					lRecord.setAttribute("idSeduta", response.getData()[0].getAttribute("key"));	
					if (callback != null) {
						callback.execute(lRecord);
					}
				} else {
					 new SceltaSedutaOrganoCollegialePopup(response.getData(), callback);
				}
			}
		});
	}
	
	@Override
	public List<MenuItem> buildConfigUtenteMenuItems(final Menu menuBarraDesktop) {

		List<MenuItem> listaConfigUtenteMenuItems = new ArrayList<MenuItem>();

		// PREIMPOSTAZIONE U.O. PROTOCOLLANTE
		if (getParametroDBAsBoolean("SHOW_UO_IN_TOOLBAR")) {
			MenuItem uoLavoroMenuItem = new MenuItem(I18NUtil.getMessages().configUtenteMenuUoLavoro_title(), "menu/uo.png");
			uoLavoroMenuItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {

				@Override
				public void onClick(MenuItemClickEvent event) {
					onClickConfigUtenteMenuUoLavoro();
				}
			});
			listaConfigUtenteMenuItems.add(uoLavoroMenuItem);
		}
		
		// PREFERENZA DI SCRIVANIA
		MenuItem prefScrivaniaMenuItem = new MenuItem(I18NUtil.getMessages().configUtenteMenuPreferenzaScrivania_title(), "menu/scrivania.png");
		prefScrivaniaMenuItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {

			@Override
			public void onClick(MenuItemClickEvent event) {
				onClickConfigUtenteMenuPrefScrivania();
			}
		});
		listaConfigUtenteMenuItems.add(prefScrivaniaMenuItem);
		
		MenuItem prefNotificheMailMenuItem = new MenuItem(I18NUtil.getMessages().configUtenteMenuPreferenzaNotificheMail_title(), "menu/postainarrivo.png");
		prefNotificheMailMenuItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {

			@Override
			public void onClick(MenuItemClickEvent event) {
				onClickConfigUtenteMenuPrefNotificheMail();
			}
		});
		if(!AurigaLayout.getParametroDBAsBoolean("IGNORE_PREF_NOTIF_EMAIL")) {
			listaConfigUtenteMenuItems.add(prefNotificheMailMenuItem);
		}
		
		// PREFERENZA ITER ATTI
		MenuItem prefIterAttiMenuItem = new MenuItem(I18NUtil.getMessages().configUtenteMenuPreferenzaIterAtti_title(), "menu/iter_atti.png");
		prefIterAttiMenuItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {

			@Override
			public void onClick(MenuItemClickEvent event) {
				onClickConfigUtenteMenuPrefIterAtti();
			}
		});
		if(AurigaLayout.getParametroDBAsBoolean("ATTIVATO_MODULO_ATTI")) {
			listaConfigUtenteMenuItems.add(prefIterAttiMenuItem);
		}
		
		// PREFERENZA ACCESSIBILITA
		MenuItem prefAccessibilitaMenuItem = new MenuItem(I18NUtil.getMessages().configUtenteMenuPreferenzeAccessibilita_title(), "accessibilita/accessibilita.png");
		prefAccessibilitaMenuItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {
			
			@Override
			public void onClick(MenuItemClickEvent event) {
				onClickConfigUtenteMenuPrefAccessibilita();
			}
		});
		listaConfigUtenteMenuItems.add(prefAccessibilitaMenuItem);

		// PREIMPOSTAZIONE COD. RAPIDO ORGANIGRAMMA
		if (getParametroDBAsBoolean("DIM_ORGANIGRAMMA_NONSTD")) {
			MenuItem codRapidoOrganigrammaMenuItem = new MenuItem(
					I18NUtil.getMessages().configUtenteMenuCodRapidoOrganigramma_title(), "menu/organigramma.png");
			codRapidoOrganigrammaMenuItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {

				@Override
				public void onClick(MenuItemClickEvent event) {
					onClickConfigUtenteMenuCodRapidoOrganigramma();
				}
			});
			listaConfigUtenteMenuItems.add(codRapidoOrganigrammaMenuItem);
		}
		
		// PREFERENZE DI STAMPA
		MenuItem impostazioniStampaMenuItem = new MenuItem(I18NUtil.getMessages().configUtenteMenuImpostazioniStampa_title(),
				"postaElettronica/print_file.png");
		impostazioniStampaMenuItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {

			@Override
			public void onClick(MenuItemClickEvent event) {
				onClickConfigUtenteMenuImpostazioniStampa();
			}
		});
		listaConfigUtenteMenuItems.add(impostazioniStampaMenuItem);

		// PREFERENZE DI STAMPA TIMBRO / COPERTINA
		MenuItem impostazioniCopertinaTimbroMenuItem = new MenuItem(I18NUtil.getMessages().configUtenteMenuImpostazioniCopertinaTimbro_title(),
				"file/timbra.gif");
		impostazioniCopertinaTimbroMenuItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {

			@Override
			public void onClick(MenuItemClickEvent event) {
				onClickConfigUtenteMenuImpostazioniCopertinaTimbro();
			}
		});
		listaConfigUtenteMenuItems.add(impostazioniCopertinaTimbroMenuItem);
		
		// PREFERENZE DI FIRMA
		MenuItem impostazioniFirmaMenuItem = new MenuItem(I18NUtil.getMessages().configUtenteMenuImpostazioniFirma_title(), "file/mini_sign.png");
		
		Menu submenu = new Menu();
		// SUBMENU Imposta modalità firma
		MenuItem impostaModalitaFirmaMenuItem = new MenuItem(I18NUtil.getMessages().configUtenteMenuImpostaModalitaFirma_title(), "file/mini_sign.png");
		impostaModalitaFirmaMenuItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {

			@Override
			public void onClick(MenuItemClickEvent event) {
				onClickConfigUtenteMenuImpostazioniFirma();
			}
		});
		
		submenu.addItem(impostaModalitaFirmaMenuItem);
		
		// SUBMENU Imposta credenziali di firma automatica
		MenuItem impostaCredenzialiFirmaAutomaticaItem = new MenuItem(I18NUtil.getMessages().configUtenteMenuImpostaCredenzialiFirmaAutomatica_title(), "file/mini_sign.png");
		impostaCredenzialiFirmaAutomaticaItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {

			@Override
			public void onClick(MenuItemClickEvent event) {
				onClickConfigUtenteMenuImpostaCredenzialiFirma();
			}
		});
		submenu.addItem(impostaCredenzialiFirmaAutomaticaItem);
		
		if (impostazioniFirmaAutomatica != null) {
			// SUBMENU Rimuovi credenziali di firma automatica
			MenuItem rimuoviCredenzialiFirmaAutomaticaItem = new MenuItem(
					I18NUtil.getMessages().configUtenteMenuRimuoviCredenzialiFirmaAutomatica_title(),
					"file/mini_sign.png");
			rimuoviCredenzialiFirmaAutomaticaItem
					.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {

						@Override
						public void onClick(MenuItemClickEvent event) {
							onClickRimuoviCredenzialiFirmaAutomatica();
						}
					});
			submenu.addItem(rimuoviCredenzialiFirmaAutomaticaItem);
		}
		
		impostazioniFirmaMenuItem.setSubmenu(submenu);	
		listaConfigUtenteMenuItems.add(impostazioniFirmaMenuItem);
		
		if (AurigaLayout.getParametroDB("SHOW_FIRME_IN_CALCE") == null || !AurigaLayout.getParametroDBAsBoolean("SHOW_FIRME_IN_CALCE")) {
			/*
			 * Nel caso non sia presente tale valore o abbia valore false allora dobbiamo visualizzare la firma normale
			 */
			// FIRMA IN CALCE ALLE E-MAIL
			MenuItem firmaEmailMenuItem = new MenuItem(I18NUtil.getMessages().configUtenteMenuFirmaEmail_title(), "menu/firma_email.png");
			firmaEmailMenuItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {

				@Override
				public void onClick(MenuItemClickEvent event) {
					onClickConfigUtenteMenuFirmaEmail();
				}
			});
			listaConfigUtenteMenuItems.add(firmaEmailMenuItem);
		} else if (AurigaLayout.getParametroDB("SHOW_FIRME_IN_CALCE") != null && AurigaLayout.getParametroDBAsBoolean("SHOW_FIRME_IN_CALCE")) {
			/*
			 * Altrimenti bisogna caricare le firme in calce
			 */
			// FIRME IN CALCE ALLE E-MAIL
			MenuItem firmeEmailMenuItem = new MenuItem(I18NUtil.getMessages().configUtenteMenuFirmeEmail_title(), "menu/firma_email.png");
			firmeEmailMenuItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {

				@Override
				public void onClick(MenuItemClickEvent event) {
					onClickConfigUtenteMenuFirmeEmail();
				}
			});
			listaConfigUtenteMenuItems.add(firmeEmailMenuItem);
		}
		
		if (AurigaLayout.getParametroDBAsBoolean("ATTIVA_ATT_CUSTOM_TIPO_GUI")) {

			// PREFERENZE DI REGISTRAZIONE DOCUMENTO
			MenuItem impostazioniDocumentoMenuItem = new MenuItem(I18NUtil.getMessages().configUtenteMenuImpostazioniDocumento_title(),
					"archivio/flgUdFolder/U.png");
			impostazioniDocumentoMenuItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {
	
				@Override
				public void onClick(MenuItemClickEvent event) {
					onClickConfigUtenteMenuImpostazioniPrefDocumento();
				}
			});
			listaConfigUtenteMenuItems.add(impostazioniDocumentoMenuItem);
	
			// PREFERENZE DI CREAZIONE FASCICOLO/CARTELLA
			MenuItem impostazioniFascicoloMenuItem = new MenuItem(I18NUtil.getMessages().configUtenteMenuImpostazioniFascicolo_title(),
					"archivio/flgUdFolder/F.png");
			impostazioniFascicoloMenuItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {
	
				@Override
				public void onClick(MenuItemClickEvent event) {
					onClickConfigUtenteMenuImpostazioniPrefFascicolo();
				}
			});
			listaConfigUtenteMenuItems.add(impostazioniFascicoloMenuItem);
		
		}
		
		// PREFERENZE DI SCELTA DA ORGANIGRAMMA
		MenuItem preferenzeSceltaOrganigrammaMenuItem = new MenuItem(I18NUtil.getMessages().configUtenteMenuSceltaOrganigramma_title(),
				"menu/organigramma.png");
		preferenzeSceltaOrganigrammaMenuItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {

			@Override
			public void onClick(MenuItemClickEvent event) {
				onClickConfigUtenteMenuPreferenzeSceltaOrganigramma();
			}
		});
		listaConfigUtenteMenuItems.add(preferenzeSceltaOrganigrammaMenuItem);
		
		// VISUALIZZAZIONE SHORTCUT SU BARRA O SU DESKTOP
		
		if (isAbilMenuBarraDesktop() && menuBarraDesktop != null) {
			MenuItem separatorMenuItem = new MenuItem();
			separatorMenuItem.setIsSeparator(true);
//			MenuItem visualizzaShortcutMenuItem = new MenuItem("Visualizza shortcut");
//			visualizzaShortcutMenuItem.setSubmenu(menuBarraDesktop);
//			listaConfigUtenteMenuItems.add(visualizzaShortcutMenuItem);
			listaConfigUtenteMenuItems.add(separatorMenuItem);
			listaConfigUtenteMenuItems.add(menuBarraDesktop.getItem(0));
		}
		
		if (!AurigaLayout.getParametroDBAsBoolean("ATTIVA_GRID_ALLEGATI_IN_PROT")) {
			MenuItem menuAttivaProtOttimizzataAllegatiItem = new MenuItem("Protocollazione ottimizzata per molti allegati");
			menuAttivaProtOttimizzataAllegatiItem.setChecked(attivaProtOttimizzataAllegati);		
			menuAttivaProtOttimizzataAllegatiItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {
				
				@Override
				public void onClick(MenuItemClickEvent event) {
					MenuItem menuItem = event.getItem();
					final Boolean value = !menuItem.getChecked();
		            menuItem.setChecked(value);
		        	AdvancedCriteria criteria = new AdvancedCriteria();
		    		criteria.addCriteria("prefName", OperatorId.EQUALS, "DEFAULT");
		    		saveUserPreference("attivaProtOttimizzataAllegati", "DEFAULT", value ? "true" : "false", null, new ServiceCallback<Record>() {
	
						@Override
						public void execute(Record object) {
							attivaProtOttimizzataAllegati = value;
						}
					});				    		
				}
		    });
			listaConfigUtenteMenuItems.add(menuAttivaProtOttimizzataAllegatiItem);
		}
		
		if(Layout.isPrivilegioAttivo("UT/PRF")) {
			MenuItem clearCacheUserPreferenceMenuItem = new MenuItem("Cancella cache preferenze utenti",
					"annulla.png");
			clearCacheUserPreferenceMenuItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {

				@Override
				public void onClick(MenuItemClickEvent event) {
					final GWTRestDataSource preferenceDS = UserInterfaceFactory.getPreferenceDataSource();
					preferenceDS.executecustom("clearCacheUserPreference", new Record(), new DSCallback() {

						public void execute(DSResponse response, Object rawData, DSRequest request) {
							if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
								Layout.addMessage(new MessageBean("Cancellazione cache preferenze utenti effettuata con successo", "", MessageType.INFO));
							}
						}
					});
				}
			});
			listaConfigUtenteMenuItems.add(clearCacheUserPreferenceMenuItem);
		}

		return listaConfigUtenteMenuItems;
	}
	
	public void attivaFunzProt() {
//		funzProtLabel.setContents("<span style='color:#00518C;font-size:12px;font-family:arial'>FUNZ.&nbsp;PROTOCOLLISTA</span>");
		funzProtLabel.setPrompt(I18NUtil.getMessages().funzProtButton_alert_attivata(""));
		funzProtLabel.setDisabled(false);
		funzProtButton.setSrc("on.png");		
	}
	
	public void disattivaFunzProt() {
//		funzProtLabel.setContents("<span style='color:grey;font-size:12px;font-family:arial'>FUNZ.&nbsp;PROTOCOLLISTA</span>");
		funzProtLabel.setPrompt(I18NUtil.getMessages().funzProtButton_alert_disattivata());		
		funzProtLabel.setDisabled(true);
		funzProtButton.setSrc("off.png");			
	}
	
	@Override
	protected void addHeaderButtons(ToolStrip header) {
		
		super.addHeaderButtons(header);
		
		funzProtLabel = new Label();
		funzProtLabel.setContents("<span>FUNZ.&nbsp;PROTOCOLLISTA</span>");
		funzProtLabel.setAlign(Alignment.CENTER);
		funzProtLabel.setValign(VerticalAlignment.CENTER);
		funzProtLabel.setVisibility(Visibility.HIDDEN);
		funzProtLabel.setStyleName(it.eng.utility.Styles.funzProtLabel);
		funzProtLabel.setShowDisabled(true);
		header.addMember(funzProtLabel);
		header.addSpacer(5);
		
		funzProtButton = new Img("funzione_protocollista.png", 30, 24);
		funzProtButton.setCursor(Cursor.HAND);
		funzProtButton.setVisibility(Visibility.HIDDEN);
		funzProtButton.setShowTitle(true);
		funzProtButton.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				getListaUoProtocollisti(new ServiceCallback<RecordList>() {
					
					@Override
					public void execute(final RecordList listaUoProtocollisti) {
						
						if(listaUoProtocollisti.getLength() == 1) {
							
							if(idUOPuntoProtAttivato == null || "".equals(idUOPuntoProtAttivato)) {										
								String idUoPP = listaUoProtocollisti.get(0).getAttribute("idUo");
								saveUserPreference("idUOPuntoProtAttivato" + getPrefKeySuffixSpecXDominio(),"DEFAULT", idUoPP, null, null);
								idUOPuntoProtAttivato = idUoPP;
								descUOPuntoProtAttivato = listaUoProtocollisti.get(0).getAttribute("descUo");
								attivaFunzProt();
//								Layout.addMessage(new MessageBean(I18NUtil.getMessages().funzProtButton_alert_attivata(descUOPuntoProtAttivato),"",MessageType.INFO));
							} else if(isAbilDisattivaFunzProtocollazione()) {
								saveUserPreference("idUOPuntoProtAttivato" + getPrefKeySuffixSpecXDominio(),"DEFAULT", "", null, null);
								idUOPuntoProtAttivato = null;
								descUOPuntoProtAttivato = null;
								disattivaFunzProt();
//								Layout.addMessage(new MessageBean(I18NUtil.getMessages().funzProtButton_alert_disattivata(),"",MessageType.INFO));
							}
							
						} else {
							
							Menu funzProtMenu = new Menu();
							
							MenuItem attivaMenuItem = new MenuItem("Attiva");
							attivaMenuItem.setEnabled(idUOPuntoProtAttivato == null || "".equals(idUOPuntoProtAttivato) || listaUoProtocollisti.getLength() > 1);
							if(listaUoProtocollisti.getLength() > 1) {
								Menu submenu = new Menu();
								for(int i = 0; i < listaUoProtocollisti.getLength(); i++) {
									final Record recordUoPP = listaUoProtocollisti.get(i);											
									MenuItem menuItem = new MenuItem(recordUoPP.getAttribute("descUo"));
									if(idUOPuntoProtAttivato != null && idUOPuntoProtAttivato.equals(recordUoPP.getAttribute("idUo"))) {
										menuItem.setChecked(true);											
									}
									menuItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {
										
										@Override
										public void onClick(MenuItemClickEvent event) {

											String idUoPP = recordUoPP.getAttribute("idUo");
											saveUserPreference("idUOPuntoProtAttivato" + getPrefKeySuffixSpecXDominio(),"DEFAULT", idUoPP, null, null);
											idUOPuntoProtAttivato = idUoPP;
											descUOPuntoProtAttivato = recordUoPP.getAttribute("descUo");
											attivaFunzProt();
//											Layout.addMessage(new MessageBean(I18NUtil.getMessages().funzProtButton_alert_attivata(descUOPuntoProtAttivato),"",MessageType.INFO));
										}
									});
									submenu.addItem(menuItem);
								}
								attivaMenuItem.setSubmenu(submenu);		
							} else {
								attivaMenuItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {
									
									@Override
									public void onClick(MenuItemClickEvent event) {

										String idUoPP = listaUoProtocollisti.get(0).getAttribute("idUo");
										saveUserPreference("idUOPuntoProtAttivato" + getPrefKeySuffixSpecXDominio(),"DEFAULT", idUoPP, null, null);
										idUOPuntoProtAttivato = idUoPP;
										descUOPuntoProtAttivato = listaUoProtocollisti.get(0).getAttribute("descUo");
										attivaFunzProt();
//										Layout.addMessage(new MessageBean(I18NUtil.getMessages().funzProtButton_alert_attivata(descUOPuntoProtAttivato),"",MessageType.INFO));
									}
								});
							}						
							funzProtMenu.addItem(attivaMenuItem);
							
							MenuItem disattivaMenuItem = new MenuItem("Disattiva");
							disattivaMenuItem.setEnabled(idUOPuntoProtAttivato != null && !"".equals(idUOPuntoProtAttivato) && isAbilDisattivaFunzProtocollazione());
							disattivaMenuItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {
									
								@Override
								public void onClick(MenuItemClickEvent event) {
									saveUserPreference("idUOPuntoProtAttivato" + getPrefKeySuffixSpecXDominio(),"DEFAULT", "", null, null);
									idUOPuntoProtAttivato = null;
									descUOPuntoProtAttivato = null;
									disattivaFunzProt();
//									Layout.addMessage(new MessageBean(I18NUtil.getMessages().funzProtButton_alert_disattivata(),"",MessageType.INFO));
								}
							});
							funzProtMenu.addItem(disattivaMenuItem);				
													
							funzProtMenu.showContextMenu();		
							
						}	
						markForRedraw();
					}
				});
			}
		});
		header.addMember(funzProtButton);
		header.addSpacer(10);
	}
	
	public void getListaUoProtocollisti(final ServiceCallback<RecordList> callback) {
		final GWTRestDataSource uoCollegateDataSource = new GWTRestDataSource("LoadComboUoProtocollistiDataSource");
		uoCollegateDataSource.fetchData(null, new DSCallback() {

			@Override
			public void execute(DSResponse response, Object rawData, DSRequest request) {
				if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
					if(callback != null) {
						callback.execute(response.getDataAsRecordList());
					}
				}
			}
		});
	}
	
	public boolean isAbilAttivaFunzProtocollazione(){
		return Layout.isPrivilegioAttivo("#AFP");
	}
	
	public boolean isAbilDisattivaFunzProtocollazione(){
		return Layout.isPrivilegioAttivo("#DFP");
	}
	
	public void loadUserPreference(String prefKey, String prefName, final ServiceCallback<Record> callback) {
		loadUserPreference(prefKey, prefName, false, callback);
	}
	
	private void loadUserPreference(final String prefKey, String prefName, final boolean skipPreferenceDefault, final ServiceCallback<Record> callback) {
		loadUserPreference(prefKey, prefName, null, skipPreferenceDefault, callback);
	}
	
	public void loadUserPreference(String prefKey, String prefName, String userId, final ServiceCallback<Record> callback) {
		loadUserPreference(prefKey, prefName, userId, false, callback);
	}

	private void loadUserPreference(final String prefKey, String prefName, String userId, final boolean skipPreferenceDefault, final ServiceCallback<Record> callback) {

		if (callback == null) {
			return;
		}

		final GWTRestDataSource preferenceDS = UserInterfaceFactory.getPreferenceDataSource();
		preferenceDS.addParam("prefKey", Layout.getConfiguredPrefKeyPrefix() + prefKey);
		if(userId != null && !"".equals(userId)) {
			preferenceDS.addParam("userId", userId);				
		}

		AdvancedCriteria preferenceCriteria = new AdvancedCriteria();
		preferenceCriteria.addCriteria("prefName", OperatorId.EQUALS, prefName);
		preferenceDS.fetchData(preferenceCriteria, new DSCallback() {

			@Override
			public void execute(DSResponse response, Object rawData, DSRequest request) {
				if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
					Record[] data = response.getData();
					if (data.length != 0) {
						Record record = data[0];
						callback.execute(record);
					} else if (!skipPreferenceDefault) {
						final GWTRestDataSource preferenceDefaultDS = UserInterfaceFactory.getPreferenceDataSource();
						preferenceDefaultDS.addParam("userId", "DEFAULT");
						preferenceDefaultDS.addParam("prefKey", Layout.getConfiguredPrefKeyPrefix() + prefKey);
						preferenceDefaultDS.addParam("prefName", "DEFAULT");
						preferenceDefaultDS.fetchData(null, new DSCallback() {

							@Override
							public void execute(DSResponse responseDefault, Object rawDataDefault, DSRequest requestDefault) {
								if (responseDefault.getStatus() == DSResponse.STATUS_SUCCESS) {
									Record[] dataDefault = responseDefault.getData();
									if (dataDefault.length != 0) {
										Record recordDefault = dataDefault[0];
										callback.execute(recordDefault);
									} else {
										callback.execute(null);
									}
								} else {
									callback.execute(null);
								}
							}
						});
					} else {
						callback.execute(null);
					}
				} else {
					callback.execute(null);
				}
			}
		});
	}

	public void loadUserPreferences(String prefKey, final ServiceCallback<RecordList> callback) {
		loadUserPreferences(prefKey, false, callback);
	}

	private void loadUserPreferences(final String prefKey, final boolean skipPreferenceDefault, final ServiceCallback<RecordList> callback) {

		if (callback == null) {
			return;
		}

		final GWTRestDataSource preferenceDS = UserInterfaceFactory.getPreferenceDataSource();
		preferenceDS.addParam("prefKey", Layout.getConfiguredPrefKeyPrefix() + prefKey);
		preferenceDS.addParam("includeValues", "true");

		preferenceDS.fetchData(new AdvancedCriteria(), new DSCallback() {

			@Override
			public void execute(DSResponse response, Object rawData, DSRequest request) {
				if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
					Record[] data = response.getData();
					if (data.length != 0) {
						RecordList recordList = new RecordList(data);
						callback.execute(recordList);
					} else {
						callback.execute(new RecordList());
					}
				} else {
					callback.execute(new RecordList());
				}
			}
		});
	}
	
	public void saveUserPreference(String prefKey, final String prefName, final String value, final String successMessage,
			final ServiceCallback<Record> callback) {
		saveUserPreference(prefKey, prefName, null, value, successMessage, callback);
	}

	public void saveUserPreference(final GWTRestDataSource preferenceDS, String prefKey, final String prefName, final String value, final String successMessage,
			final ServiceCallback<Record> callback) {
		saveUserPreference(preferenceDS, prefKey, prefName, null, value, successMessage, callback);
	}

	public void saveUserPreference(String prefKey, final String prefName, String userId, final String value, final String successMessage,
			final ServiceCallback<Record> callback) {

		final GWTRestDataSource preferenceDS = UserInterfaceFactory.getPreferenceDataSource();
		preferenceDS.addParam("prefKey", Layout.getConfiguredPrefKeyPrefix() + prefKey);
				
		saveUserPreference(preferenceDS, prefKey, prefName, userId, value, successMessage, callback);
	}
	
	// se ho un preferenceDS associato ad una select lo devo passare altrimenti non viene ricaricata correttamente dopo il salvataggio del valore
	public void saveUserPreference(final GWTRestDataSource preferenceDS, String prefKey, final String prefName, String userId, final String value, final String successMessage,
			final ServiceCallback<Record> callback) {
		
		if(userId != null && !"".equals(userId)) {
			preferenceDS.addParam("userId", userId);				
		}

		loadUserPreference(prefKey, prefName, userId, true, new ServiceCallback<Record>() {

			@Override
			public void execute(Record recordPref) {

				if (recordPref != null) {
					recordPref.setAttribute("value", value);
					preferenceDS.updateData(recordPref);
				} else {
					Record record = new Record();
					record.setAttribute("prefName", prefName);
					record.setAttribute("value", value);
					preferenceDS.addData(record);
				}
				if (successMessage != null && !"".equals(successMessage)) {
					AurigaLayout.addMessage(new MessageBean(successMessage, "", MessageType.INFO));
				}
				if (callback != null) {
					callback.execute(recordPref);
				}
			}
		});
	}
	
	public void removeCredenzialiFirmaAutomaticaPreference(String prefKey, final String prefName, String userId, final String value, final String successMessage,
			final ServiceCallback<Record> callback) {

		final GWTRestDataSource preferenceDS = UserInterfaceFactory.getPreferenceDataSource();
		preferenceDS.addParam("prefKey", Layout.getConfiguredPrefKeyPrefix() + prefKey);
				
		removeCredenzialiFirmaAutomaticaPreference(preferenceDS, prefKey, prefName, userId, value, successMessage, callback);
	}
	
	public void removeCredenzialiFirmaAutomaticaPreference(final GWTRestDataSource preferenceDS, String prefKey, final String prefName, String userId, final String value, final String successMessage,
			final ServiceCallback<Record> callback) {
		
		if(userId != null && !"".equals(userId)) {
			preferenceDS.addParam("userId", userId);				
		}

		loadUserPreference(prefKey, prefName, userId, true, new ServiceCallback<Record>() {

			@Override
			public void execute(Record recordPref) {

				if (recordPref != null) {
//					recordPref.setAttribute("value", value);
					preferenceDS.removeData(recordPref);
					
					if (successMessage != null && !"".equals(successMessage)) {
						AurigaLayout.addMessage(new MessageBean(successMessage, "", MessageType.INFO));
					}
				} else {
					AurigaLayout.addMessage(new MessageBean("Credenziali di firma automatica non settate", "", MessageType.INFO));
				}
				
				if (callback != null) {
					callback.execute(recordPref);
				}
			}
		});
	}

	public void onClickConfigUtenteMenuFirmaEmail() {

		FirmaEmailHtmlPopup firmaEmailHtmlPopup = new FirmaEmailHtmlPopup(firmaEmailHtml, new SavePreferenceAction() {

			@Override
			public void execute(final String value) {
				/*
				 * Se il parametro da database è impostato a true allora si controlla il body della mail. Se questo è nullo o ha solo dei tag html viene
				 * impostato come tale per il salvataggio, altrimenti viene impostato quello precedente.
				 */
				if (AurigaLayout.getParametroDBAsBoolean("CANCELLA_TAG_VUOTI_CORPO_MAIL")) {
					try {
						Record recordBody = new Record();
						recordBody.setAttribute("bodyHtml", value != null ? ((String) value) : "");
						// Chiamata al datasource per controllare il contenuto del corpo della mail
						GWTRestDataSource corpoMailDataSource = new GWTRestDataSource("CorpoMailDataSource");
						corpoMailDataSource.performCustomOperation("checkIfBodyIsEmpty", recordBody, new DSCallback() {

							@Override
							public void execute(DSResponse response, Object rawData, DSRequest request) {
								if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
									Record record = response.getData()[0];
									String signature = record.getAttribute("bodyHtml");
									saveFirma(signature);
									markForRedraw();
								}
							}
						}, new DSRequest());
					} catch (Exception e) {
						saveFirma(value);
					}
				} else {
					saveFirma(value);
				}
			}

			private void saveFirma(final String value) {
				saveUserPreference("signature.email", "DEFAULT", value, "Firma automatica in calce alle e-mail salvata con successo",
						new ServiceCallback<Record>() {

							@Override
							public void execute(Record object) {
								firmaEmailHtml = value;
							}
						});
			}
		});
		firmaEmailHtmlPopup.show();
	}

	public void onClickConfigUtenteMenuFirmeEmail() {

		FirmeEmailHtmlPopup firmeEmailHtmlPopup = new FirmeEmailHtmlPopup() {

			@Override
			public void saveFirmaEmail(final GWTRestDataSource prefDS, final String prefName, final String value, final Boolean flgPredefinita, final Boolean flgAutoNuova,
					final Boolean flgAutoRisposta, final Boolean flgAutoInoltro, final ServiceCallback<String> callback) {

				/*
				 * Se il parametro da database è impostato a true allora si controlla il body della mail. Se questo è nullo o ha solo dei tag html viene
				 * impostato come tale per il salvataggio, altrimenti viene impostato quello precedente.
				 */
				if (AurigaLayout.getParametroDBAsBoolean("CANCELLA_TAG_VUOTI_CORPO_MAIL")) {

					try {

						Record recordBody = new Record();
						recordBody.setAttribute("bodyHtml", value != null ? ((String) value) : "");

						// Chiamata al datasource per controllare il contenuto del corpo della mail
						GWTRestDataSource corpoMailDataSource = new GWTRestDataSource("CorpoMailDataSource");
						corpoMailDataSource.performCustomOperation("checkIfBodyIsEmpty", recordBody, new DSCallback() {

							@Override
							public void execute(DSResponse response, Object rawData, DSRequest request) {
								if (response.getStatus() == DSResponse.STATUS_SUCCESS) {

									Record record = response.getData()[0];

									String signature = record.getAttribute("bodyHtml");

									saveFirma(prefDS, prefName, signature, flgPredefinita, flgAutoNuova, flgAutoRisposta, flgAutoInoltro, callback);

									markForRedraw();
								}
							}
						}, new DSRequest());

					} catch (Exception e) {

						saveFirma(prefDS, prefName, value, flgPredefinita, flgAutoNuova, flgAutoRisposta, flgAutoInoltro, callback);
					}
				} else {
					saveFirma(prefDS, prefName, value, flgPredefinita, flgAutoNuova, flgAutoRisposta, flgAutoInoltro, callback);
				}
			}

			/**
			 * Metodo che esegue il salvataggio della firma inserita all'interno delle preferences dell'utente
			 * 
			 * @param prefName
			 * @param value
			 * @param flgPredefinita
			 * @param flgAutoNuova
			 * @param flgAutoRisposta
			 * @param flgAutoInoltro
			 */
			private void saveFirma(GWTRestDataSource prefDS, final String prefName, final String value, Boolean flgPredefinita, Boolean flgAutoNuova, Boolean flgAutoRisposta,
					Boolean flgAutoInoltro, final ServiceCallback<String> callback) {
				if (flgPredefinita != null && flgPredefinita) {
					saveUserPreference("signature.email.predefinita", "DEFAULT", prefName, null, new ServiceCallback<Record>() {

						@Override
						public void execute(Record object) {
							firmaEmailPredefinita = prefName;
						}
					});
				} else if(firmaEmailPredefinita != null && firmaEmailPredefinita.equals(prefName)) {
					/*
					 * Se il flag è false ma la firma salvata nel campo relativo è quella attuale allora deve essere tolta
					 * perchè non si vuole più sia tale
					 * e.g. se il flgPredefinita ha valore false ma in firmaEmailPredefinita è salvata la firma PROVA che sto 
					 * modificando allora PROVA non è più la firma predefinita e quindi deve essere tolta.
					 * Lo stesso vale anche per le situazioni automatico in nuovo mail, automatico in inoltro e automatico
					 * in risposta
					 */
					saveUserPreference("signature.email.predefinita", "DEFAULT", "", null, new ServiceCallback<Record>() {

						@Override
						public void execute(Record object) {
							firmaEmailPredefinita = "";
						}
					});
					
				}
				
				if (flgAutoNuova != null && flgAutoNuova) {
					saveUserPreference("signature.email.nuova", "DEFAULT", prefName, null, new ServiceCallback<Record>() {

						@Override
						public void execute(Record object) {
							firmaEmailAutoNuova = prefName;
						}
					});
				} else if(firmaEmailAutoNuova != null && firmaEmailAutoNuova.equals(prefName)) {
					/*
					 * Se il flag è false ma la firma salvata nel campo relativo è quella attuale allora deve essere tolta
					 * perchè non si vuole più sia tale
					 * e.g. se il flgPredefinita ha valore false ma in firmaEmailPredefinita è salvata la firma PROVA che sto 
					 * modificando allora PROVA non è più la firma predefinita e quindi deve essere tolta.
					 * Lo stesso vale anche per le situazioni automatico in nuovo mail, automatico in inoltro e automatico
					 * in risposta
					 */
					saveUserPreference("signature.email.nuova", "DEFAULT", "", null, new ServiceCallback<Record>() {

						@Override
						public void execute(Record object) {
							firmaEmailAutoNuova = "";
						}
					});
				}
				
				if (flgAutoRisposta != null && flgAutoRisposta) {
					saveUserPreference("signature.email.risposta", "DEFAULT", prefName, null, new ServiceCallback<Record>() {

						@Override
						public void execute(Record object) {
							firmaEmailAutoRisposta = prefName;
						}
					});
				} else if(firmaEmailAutoRisposta != null && firmaEmailAutoRisposta.equals(prefName)){
					/*
					 * Se il flag è false ma la firma salvata nel campo relativo è quella attuale allora deve essere tolta
					 * perchè non si vuole più sia tale
					 * e.g. se il flgPredefinita ha valore false ma in firmaEmailPredefinita è salvata la firma PROVA che sto 
					 * modificando allora PROVA non è più la firma predefinita e quindi deve essere tolta.
					 * Lo stesso vale anche per le situazioni automatico in nuovo mail, automatico in inoltro e automatico
					 * in risposta
					 */
					saveUserPreference("signature.email.risposta", "DEFAULT", "", null, new ServiceCallback<Record>() {

						@Override
						public void execute(Record object) {
							firmaEmailAutoRisposta = "";
						}
					});
				}
				
				if (flgAutoInoltro != null && flgAutoInoltro) {
					saveUserPreference("signature.email.inoltro", "DEFAULT", prefName, null, new ServiceCallback<Record>() {

						@Override
						public void execute(Record object) {
							firmaEmailAutoInoltro = prefName;
						}
					});
				} else if(firmaEmailAutoInoltro != null && firmaEmailAutoInoltro.equals(prefName)) {
					/*
					 * Se il flag è false ma la firma salvata nel campo relativo è quella attuale allora deve essere tolta
					 * perchè non si vuole più sia tale
					 * e.g. se il flgPredefinita ha valore false ma in firmaEmailPredefinita è salvata la firma PROVA che sto 
					 * modificando allora PROVA non è più la firma predefinita e quindi deve essere tolta.
					 * Lo stesso vale anche per le situazioni automatico in nuovo mail, automatico in inoltro e automatico
					 * in risposta
					 */
					saveUserPreference("signature.email.inoltro", "DEFAULT", "", null, new ServiceCallback<Record>() {

						@Override
						public void execute(Record object) {
							firmaEmailAutoInoltro = "";
						}
					});
				}
				
				saveUserPreference(prefDS, "signature.email", prefName, value, "Firma in calce alle e-mail salvata con successo", new ServiceCallback<Record>() {

					@Override
					public void execute(Record object) {
						firmaEmailHtml = value;
						firmeEmailHtml.put(prefName, value);
						if(callback != null) {
							callback.execute(prefName);
						}
					}
				});
			}
		};
		firmeEmailHtmlPopup.show();
	}

	public void onClickConfigUtenteMenuCodRapidoOrganigramma() {
		
		SaveCodRapidoOrganigrammaWindow saveCodRapidoOrganigrammaWindow = new SaveCodRapidoOrganigrammaWindow() {

			@Override
			public void manageOnOkButtonClick(final String value) {

				saveUserPreference("cod.organigramma" + getPrefKeySuffixSpecXDominio(), "DEFAULT", value, null, new ServiceCallback<Record>() {

					@Override
					public void execute(Record recordPref) {
						codRapidoOrganigramma = value;
					}
				});
			}
		};
		saveCodRapidoOrganigrammaWindow.clearValues();
		saveCodRapidoOrganigrammaWindow.setValue(codRapidoOrganigramma);
		saveCodRapidoOrganigrammaWindow.redraw();
		saveCodRapidoOrganigrammaWindow.show();
	}

	public void onClickConfigUtenteMenuUoLavoro() {
		
		LinkedHashMap<String, String> valueMap = getUoCollegateUtenteValueMap();
		
		if (valueMap != null && valueMap.size() > 0) {
			SaveUoLavoroWindow saveUoLavoroWindow = new SaveUoLavoroWindow("selezionauodilavoro",valueMap) {

				@Override
				public void manageOnOkButtonClick(final String value) {
						
					saveUserPreference("idUODefault" + getPrefKeySuffixSpecXDominio(), "DEFAULT", value, null, new ServiceCallback<Record>() {

						@Override
						public void execute(Record recordPref) {
							uoLavoro = value;
							if(isOpenedPortlet("scrivania")) {
								Portlet portlet = getOpenedPortlet("scrivania");
								if (portlet != null) {
									if(portlet.getBody() != null && 
									   portlet.getBody() instanceof ScrivaniaLayout ) {
										((ScrivaniaLayout) portlet.getBody()).doSearch();
									}
								}
							}
							updateWelcomeMessage();
						}
					});
				}
			};			
			saveUoLavoroWindow.clearValues();
			saveUoLavoroWindow.setValueMap(valueMap);
			saveUoLavoroWindow.setValue((String) uoLavoro);
			saveUoLavoroWindow.redraw();
			saveUoLavoroWindow.show();			
		} else {
			AurigaLayout.addMessage(new MessageBean("Non sei associato a nessuna U.O. in organigramma", "", MessageType.WARNING));
		}
	}
	
	public void onClickConfigUtenteMenuPrefScrivania() {
		
		final JSONEncoder encoder = new JSONEncoder();
		encoder.setDateFormat(JSONDateFormat.DATE_CONSTRUCTOR);
		
		SavePrefScrivaniaWindow savePrefScrivaniaWindow = new SavePrefScrivaniaWindow("salvaPrefScrivania") {

			@Override
			public void manageOnOkButtonClick(final Record values) {
				
				if (values != null) {
					saveUserPreference("docScrivania", "DEFAULT", JSON.encode(values.getJsObj(), encoder), null, new ServiceCallback<Record>() {

						@Override
						public void execute(Record recordPref) {
							prefScrivania = values;
						}
					});
				}
			}
		};			
		savePrefScrivaniaWindow.clearValues();
		savePrefScrivaniaWindow.setValues(prefScrivania);
		savePrefScrivaniaWindow.redraw();
		savePrefScrivaniaWindow.show();	
				
	}
	
	public void onClickConfigUtenteMenuPrefNotificheMail() {
		
		final JSONEncoder encoder = new JSONEncoder();
		encoder.setDateFormat(JSONDateFormat.DATE_CONSTRUCTOR);
		
		SavePrefNotificheMailWindow savePrefNotificheMailWindow = new SavePrefNotificheMailWindow("salvaPrefNotificheMail") {

			@Override
			public void manageOnOkButtonClick(final Record values) {
				
				if (values != null) {
					saveUserPreference("prefNotificheMail", "DEFAULT", JSON.encode(values.getJsObj(), encoder), null, new ServiceCallback<Record>() {

						@Override
						public void execute(Record recordPref) {
							prefNotificheMail = values;
						}
					});
				}
			}
		};			
		savePrefNotificheMailWindow.clearValues();
		savePrefNotificheMailWindow.setValues(prefNotificheMail);
		savePrefNotificheMailWindow.redraw();
		savePrefNotificheMailWindow.show();	
	}
	
	public void onClickConfigUtenteMenuPrefIterAtti() {
		
		final JSONEncoder encoder = new JSONEncoder();
		encoder.setDateFormat(JSONDateFormat.DATE_CONSTRUCTOR);
		
		SavePrefIterAttiWindow savePrefIterAttiWindow = new SavePrefIterAttiWindow("salvaPrefIterAtti") {

			@Override
			public void manageOnOkButtonClick(final String values) {
				
				if (values != null) {
					saveUserPreference("preVerificaStaffInIterAtti" + getPrefKeySuffixSpecXDominio(), "DEFAULT", values, null, new ServiceCallback<Record>() {

						@Override
						public void execute(Record recordPref) {
							prefIterAtti = values;
						}
					});
				}
			}
		};			
		savePrefIterAttiWindow.clearValues();
		savePrefIterAttiWindow.setValues((String)prefIterAtti);
		savePrefIterAttiWindow.redraw();
		savePrefIterAttiWindow.show();		
	}

	public void onClickConfigUtenteMenuPrefAccessibilita() {
		
		final JSONEncoder encoder = new JSONEncoder();
		encoder.setDateFormat(JSONDateFormat.DATE_CONSTRUCTOR);
		
		SavePrefAccessibilitaWindow savePrefAccessibilitaWindow = new SavePrefAccessibilitaWindow("salvaPrefAccessibilita") {
			
			@Override
			public void manageOnOkButtonClick(final Record impostazioniSceltaAccessibilitaRecord,final Record impostazioniAperturaDettaglioPerRicercaPuntualeRecord) {
				if (impostazioniSceltaAccessibilitaRecord != null) {
					saveUserPreference("impostazioniSceltaAccessibilita", "DEFAULT", JSON.encode(impostazioniSceltaAccessibilitaRecord.getJsObj(), encoder), null, new ServiceCallback<Record>() {
						
						@Override
						public void execute(Record recordPref) {
							impostazioniSceltaAccessibilita = impostazioniSceltaAccessibilitaRecord;
							SC.say(I18NUtil.getMessages().messaggioRichiestaLogin());
						}
					});
				}
				if (impostazioniAperturaDettaglioPerRicercaPuntualeRecord != null) {
					saveUserPreference("impostazioniAperturaDettaglioPerRicercaPuntuale", "DEFAULT", JSON.encode(impostazioniAperturaDettaglioPerRicercaPuntualeRecord.getJsObj(), encoder), null, new ServiceCallback<Record>() {
						
						@Override
						public void execute(Record recordPref) {
							impostazioniAperturaDettaglioPerRicercaPuntuale = impostazioniAperturaDettaglioPerRicercaPuntualeRecord;
						}
					});
				}
			}
		};	
		Record recInput = new Record ();
//		recInput.setAttribute("impostazioniSceltaAccessibilita", impostazioniSceltaAccessibilita);
//		recInput.setAttribute("impostazioniAperturaDettaglioPerRicercaPuntuale", impostazioniAperturaDettaglioPerRicercaPuntuale);
		if (impostazioniSceltaAccessibilita != null) {
			Record.copyAttributesInto(recInput, impostazioniSceltaAccessibilita, impostazioniSceltaAccessibilita.getAttributes());
		}
		if (impostazioniAperturaDettaglioPerRicercaPuntuale != null) {
			Record.copyAttributesInto(recInput, impostazioniAperturaDettaglioPerRicercaPuntuale, impostazioniAperturaDettaglioPerRicercaPuntuale.getAttributes());
		}
		savePrefAccessibilitaWindow.clearValues();
		savePrefAccessibilitaWindow.setValues(recInput);
		savePrefAccessibilitaWindow.redraw();
		savePrefAccessibilitaWindow.show();			
		
	}

	public void onClickConfigUtenteMenuImpostazioniStampa() {

		final JSONEncoder encoder = new JSONEncoder();
		encoder.setDateFormat(JSONDateFormat.DATE_CONSTRUCTOR);

		SaveImpostazioniStampaWindow saveImpostazioniStampaWindow = new SaveImpostazioniStampaWindow() {

			@Override
			public void manageOnOkButtonClick(final Record values) {
				if (values != null) {
					saveUserPreference("impostazioniStampa", "DEFAULT", JSON.encode(values.getJsObj(), encoder), null, new ServiceCallback<Record>() {

						@Override
						public void execute(Record recordPref) {
							impostazioniStampa = values;
						}
					});
				}
			}
		};
		saveImpostazioniStampaWindow.clearValues();
		saveImpostazioniStampaWindow.setValues(impostazioniStampa);
		saveImpostazioniStampaWindow.redraw();
		saveImpostazioniStampaWindow.show();
	}
	
	public void onClickConfigUtenteMenuImpostazioniFirma() {
		
		final JSONEncoder encoder = new JSONEncoder();
		encoder.setDateFormat(JSONDateFormat.DATE_CONSTRUCTOR);

		GWTRestDataSource lLoadComboValoriDizionarioDataSource = new GWTRestDataSource("LoadComboValoriDizionarioDataSource", "key", FieldType.TEXT);
		lLoadComboValoriDizionarioDataSource.addParam("dictionaryEntry", "PROVIDER_FIRMA_REMOTA");
		lLoadComboValoriDizionarioDataSource.addParam("flgSelectMulti", "");
		lLoadComboValoriDizionarioDataSource.fetchData(null, new DSCallback() {

			@Override
			public void execute(DSResponse dsResponse, Object data, DSRequest dsRequest) {
				if (dsResponse.getData() != null) {
					Map providerValueMap = new HashMap<>();
					if (dsResponse.getData().length > 0 ) {
						for (Record rec : dsResponse.getData()) {
							providerValueMap.put(rec.getAttribute("key"), rec.getAttribute("value"));
						}
					}
					final SaveImpostazioniFirmaWindow saveImpostazioniFirmaWindow = new SaveImpostazioniFirmaWindow(providerValueMap) {

						@Override
						public boolean manageOnOkButtonClick(final Record values) {
							if (values != null) {
								/**
								 * In fase di salvataggio della preference di firma, vengono salvate sempre le impostazioni dell'utente loggato,
								 * anzichè quelle dell'utente delegante.
								 */
								String preferenceTipoFirma = values.getAttribute("tipoFirma");
								
								if (preferenceTipoFirma != null && "A".equalsIgnoreCase(preferenceTipoFirma)) {
									if(impostazioniFirmaAutomatica != null) {
										String userId = AurigaLayout.getImpostazioneFirmaAutomatica("userId");
										String passwordFirmaAutomatica =  AurigaLayout.getImpostazioneFirmaAutomatica("password");
										if(userId == null || passwordFirmaAutomatica == null) {
											Layout.addMessage(new MessageBean(I18NUtil.getMessages().configUtenteMenuErroreCredenzialiFirmaAutomatica_title(), "", MessageType.ERROR));
											isMarkForDestroy = false;
										} else {
											saveUserPreference("impostazioniFirma", "DEFAULT", utenteLoggato.getIdUtente(), JSON.encode(values.getJsObj(), encoder), null, new ServiceCallback<Record>() {

												@Override
												public void execute(Record recordPref) {
													impostazioniFirma = values;
												}
											});
											isMarkForDestroy = true;
										}
									} else {
										Layout.addMessage(new MessageBean(I18NUtil.getMessages().configUtenteMenuErroreCredenzialiFirmaAutomatica_title(), "", MessageType.ERROR));
										isMarkForDestroy = false;
									}
								} else {
									saveUserPreference("impostazioniFirma", "DEFAULT", utenteLoggato.getIdUtente(), JSON.encode(values.getJsObj(), encoder), null, new ServiceCallback<Record>() {

										@Override
										public void execute(Record recordPref) {
											impostazioniFirma = values;
										}
									});
									isMarkForDestroy = true;
								}
							}
							return isMarkForDestroy;
						}
					};
					saveImpostazioniFirmaWindow.clearValues();
					if (impostazioniFirma != null && impostazioniFirma.getAttributes() != null && impostazioniFirma.getAttributes().length > 0) {
						saveImpostazioniFirmaWindow.setValues(impostazioniFirma, true);
					} else {
						Record recordImpostazioni = new Record();
						recordImpostazioni.setAttribute("tipoFirma", SaveImpostazioniFirmaWindow.getDefaultFirmaValue());
						saveImpostazioniFirmaWindow.setValues(recordImpostazioni, true);
					}
					saveImpostazioniFirmaWindow.redraw();
					saveImpostazioniFirmaWindow.show();
				}
			}

		});
	}

	public void onClickConfigUtenteMenuImpostaCredenzialiFirma() {
		
		final JSONEncoder encoder = new JSONEncoder();
		encoder.setDateFormat(JSONDateFormat.DATE_CONSTRUCTOR);

		GWTRestDataSource lLoadComboValoriDizionarioDataSource = new GWTRestDataSource("LoadComboValoriDizionarioDataSource", "key", FieldType.TEXT);
		lLoadComboValoriDizionarioDataSource.addParam("dictionaryEntry", "PROVIDER_FIRMA_REMOTA_AUTOMATICA");
		lLoadComboValoriDizionarioDataSource.addParam("flgSelectMulti", "");
		lLoadComboValoriDizionarioDataSource.fetchData(null, new DSCallback() {

			@Override
			public void execute(DSResponse dsResponse, Object data, DSRequest dsRequest) {
				if (dsResponse.getData() != null) {
					Map providerValueMap = new HashMap<>();
					if (dsResponse.getData().length > 0 ) {
						for (Record rec : dsResponse.getData()) {
							providerValueMap.put(rec.getAttribute("key"), rec.getAttribute("value"));
						}
					}
					final SaveCredenzialiFirmaAutomaticaWindow saveImpostazioniFirmaWindow = new SaveCredenzialiFirmaAutomaticaWindow(providerValueMap) {

						@Override
						public void manageOnOkButtonClick(final Record values) {
							if (values != null) {
								/**
								 * In fase di salvataggio della preference di firma, vengono salvate sempre le impostazioni dell'utente loggato,
								 * anzichè quelle dell'utente delegante.
								 */
								saveUserPreference("impostazioniFirmaAutomatica", "DEFAULT", utenteLoggato.getIdUtente(), JSON.encode(values.getJsObj(), encoder), null, new ServiceCallback<Record>() {

									@Override
									public void execute(Record recordPref) {
										impostazioniFirmaAutomatica = values;
									}
								});
							}
						}
					};
					saveImpostazioniFirmaWindow.clearValues();
					if (impostazioniFirmaAutomatica != null && impostazioniFirmaAutomatica.getAttributes() != null && impostazioniFirmaAutomatica.getAttributes().length > 0) {
						saveImpostazioniFirmaWindow.setValues(impostazioniFirmaAutomatica, true);
					} else {
						saveImpostazioniFirmaWindow.setIsInDelega();
					}
					saveImpostazioniFirmaWindow.redraw();
					saveImpostazioniFirmaWindow.show();
				}
			}

		});
	}
	
	public void onClickRimuoviCredenzialiFirmaAutomatica () {
		
		String preferenceTipoFirma = AurigaLayout.getImpostazioneFirma("tipoFirma");
		
		if (!(preferenceTipoFirma != null && "A".equalsIgnoreCase(preferenceTipoFirma))) {
			SC.ask("Confermi di voler rimuovere le credenziali di firma automatica?", new BooleanCallback() {
				@Override
				public void execute(Boolean value) {

					if (value) {
						removeCredenzialiFirmaAutomaticaPreference("impostazioniFirmaAutomatica", "DEFAULT",
								utenteLoggato.getIdUtente(), null,
								"Credenziali di firma automatica rimosse con successo", new ServiceCallback<Record>() {

									@Override
									public void execute(Record recordPref) {
										if (impostazioniFirmaAutomatica != null) {
											impostazioniFirmaAutomatica = null;
										}
									}
								});
					}
				}
			});
		} else {
			Layout.addMessage(new MessageBean(
					I18NUtil.getMessages().configUtenteMenuErroreImpostazioneFirmaAutomatica_title(), "",
					MessageType.ERROR));
		}
	}
	
	public void onClickConfigUtenteMenuImpostazioniCopertinaTimbro() {

		final JSONEncoder encoder = new JSONEncoder();
		encoder.setDateFormat(JSONDateFormat.DATE_CONSTRUCTOR);

		SaveImpostazioniCopertinaTimbroWindow saveImpostazioniCopertinaTimbroWindow = new SaveImpostazioniCopertinaTimbroWindow() {

			@Override
			public void manageOnOkButtonClick(final Record values) {
				if (values != null) {
					saveUserPreference("impostazioniTimbro", "DEFAULT", JSON.encode(values.getJsObj(), encoder), null, new ServiceCallback<Record>() {

						@Override
						public void execute(Record recordPref) {
							impostazioniTimbro = values;
						}
					});
				}
			}
		};
		saveImpostazioniCopertinaTimbroWindow.clearValues();
		saveImpostazioniCopertinaTimbroWindow.setValues(impostazioniTimbro);
		saveImpostazioniCopertinaTimbroWindow.redraw();
		saveImpostazioniCopertinaTimbroWindow.show();
	}

	public void onClickConfigUtenteMenuImpostazioniPrefDocumento() {

		final JSONEncoder encoder = new JSONEncoder();
		encoder.setDateFormat(JSONDateFormat.DATE_CONSTRUCTOR);

		SaveImpostazioniDocumentoWindow saveImpostazioniDocumentoWindow = new SaveImpostazioniDocumentoWindow() {

			@Override
			public void manageOnOkButtonClick(final Record values) {
				if (values != null) {
					saveUserPreference("impostazioniDocumento", "DEFAULT", JSON.encode(values.getJsObj(), encoder), null, new ServiceCallback<Record>() {

						@Override
						public void execute(Record recordPref) {
							impostazioniDocumento = values;
						}
					});
				}
			}
		};
		saveImpostazioniDocumentoWindow.clearValues();
		saveImpostazioniDocumentoWindow.setValues(impostazioniDocumento);
		saveImpostazioniDocumentoWindow.redraw();
		saveImpostazioniDocumentoWindow.show();
	}

	public void onClickConfigUtenteMenuImpostazioniPrefFascicolo() {

		final JSONEncoder encoder = new JSONEncoder();
		encoder.setDateFormat(JSONDateFormat.DATE_CONSTRUCTOR);

		SaveImpostazioniFascicoloWindow saveImpostazioniFascicoloWindow = new SaveImpostazioniFascicoloWindow() {

			@Override
			public void manageOnOkButtonClick(final Record values) {
				if (values != null) {
					saveUserPreference("impostazioniFascicolo", "DEFAULT", JSON.encode(values.getJsObj(), encoder), null, new ServiceCallback<Record>() {

						@Override
						public void execute(Record recordPref) {
							impostazioniFascicolo = values;
						}
					});
				}
			}
		};
		saveImpostazioniFascicoloWindow.clearValues();
		saveImpostazioniFascicoloWindow.setValues(impostazioniFascicolo);
		saveImpostazioniFascicoloWindow.redraw();
		saveImpostazioniFascicoloWindow.show();
	}

	public void onClickConfigUtenteMenuPreferenzeSceltaOrganigramma() {

		final JSONEncoder encoder = new JSONEncoder();
		encoder.setDateFormat(JSONDateFormat.DATE_CONSTRUCTOR);
		
		SaveImpostazioniSceltaOrganigrammaWindow saveImpostazioniSceltaOrganigrammaWindow = new SaveImpostazioniSceltaOrganigrammaWindow() {
			
			@Override
			public void manageOnOkButtonClick(final Record values) {
				if (values != null) {
					saveUserPreference("impostazioniSceltaOrganigramma", "DEFAULT", JSON.encode(values.getJsObj(), encoder), null, new ServiceCallback<Record>() {

						@Override
						public void execute(Record recordPref) {
							impostazioniSceltaOrganigramma = values;
						}
					});
				}
			}
		};
		
		saveImpostazioniSceltaOrganigrammaWindow.clearValues();
		saveImpostazioniSceltaOrganigrammaWindow.setValues(impostazioniSceltaOrganigramma);
		saveImpostazioniSceltaOrganigrammaWindow.redraw();
		saveImpostazioniSceltaOrganigrammaWindow.show();
		
	}
	
	public static String getUrlEditorOrganigramma() {
		return urlEditorOrganigramma;
	}
	
	// public static HashSet<String> getNomiFileModelliAtti(){
	// HashSet<String> nomiFileModelliAtti = new HashSet<String>();
	// for(Object key : nomiModelliAttiMap.keySet()) {
	// nomiFileModelliAtti.add(getNomeFileModelloAtti((String) key));
	// }
	// return nomiFileModelliAtti;
	// }
	//
	// public static String getNomeModelloAtti(String key){
	// return (String) nomiModelliAttiMap.get(key);
	// }
	//
	// public static String getNomeFileModelloAtti(String key){
	// return getNomeModelloAtti(key).replaceAll(" ", "_") + ".pdf";
	// }

	public static HashSet<String> getHiddenFieldsAtti(String tipoAtto) {
		try {
			return new HashSet<String>((ArrayList<String>) hiddenFieldsAttiMap.get(tipoAtto));
		} catch (Exception e) {
			return new HashSet<String>();
		}
	}

	public static LinkedHashMap<String, String> getUtentiAbilCPAValueMap() {
		LinkedHashMap<String, String> ret = new LinkedHashMap<String, String>();
		ret.putAll(utentiAbilCPAValueMap);
		return ret;
	}

	public static String getFirmaEmailHtml() {
		return firmaEmailHtml != null ? firmaEmailHtml : "";
	}
	
	public static Boolean getFunzioneProtocollazione() {
		return funzioneProtocollazione != null ? funzioneProtocollazione : false;
	}
	
	public static Boolean getAttivaProtOttimizzataAllegati() {
		return attivaProtOttimizzataAllegati != null ? attivaProtOttimizzataAllegati : false;
	}

	/**
	 * Metodo che ritorna le firme aggiunte dall'utente all'interno delle preferences
	 * 
	 * @return una lista con le firme presenti e il relativo codice html
	 */
	public static HashMap<String, String> getFirmeEmailHtml() {
		return firmeEmailHtml;
	}

	/**
	 * Metodo che ritorna la firma html della firma indicata con il nome prefName
	 * 
	 * @param prefName
	 *            nome della firma da ricercare
	 * @return il codice html della firma richiesta
	 */
	public static String getFirmaEmailHtml(String prefName) {
		return firmeEmailHtml != null && firmeEmailHtml.get(prefName) != null ? firmeEmailHtml.get(prefName) : "";
	}

	public static String getFirmaEmailPredefinita() {
		return firmaEmailPredefinita;
	}

	public static String getFirmaEmailAutoNuova() {
		return firmaEmailAutoNuova;
	}

	public static String getFirmaEmailAutoRisposta() {
		return firmaEmailAutoRisposta;
	}

	public static String getFirmaEmailAutoInoltro() {
		return firmaEmailAutoInoltro;
	}
	
	public static String getImpostazioniPdf() {
		return impostazioniPdf;
	}

	public static String getCodRapidoOrganigramma() {
		return codRapidoOrganigramma != null ? codRapidoOrganigramma : "";
	}
	
	public static Record getImpostazioneStampa() {
		return impostazioniStampa;
	}

	public static String getImpostazioneStampa(String name) {
		return (impostazioniStampa != null && impostazioniStampa.getAttribute(name) != null) ? impostazioniStampa.getAttribute(name) : "";
	}

	public static boolean getImpostazioneStampaAsBoolean(String name) {
		return (impostazioniStampa != null && impostazioniStampa.getAttributeAsBoolean(name) != null) ? impostazioniStampa.getAttributeAsBoolean(name) : false;
	}

	public static String getImpostazioneTimbro(String name) {
		return (impostazioniTimbro != null && impostazioniTimbro.getAttribute(name) != null) ? impostazioniTimbro.getAttribute(name) : "";
	}

	public static boolean getImpostazioneTimbroAsBoolean(String name) {
		return (impostazioniTimbro != null && impostazioniTimbro.getAttributeAsBoolean(name) != null) ? impostazioniTimbro.getAttributeAsBoolean(name) : false;
	}
	
	public static String getImpostazioneFirma(String name) {
		return (impostazioniFirma != null && impostazioniFirma.getAttribute(name) != null) ? impostazioniFirma.getAttribute(name) : "";
	}
	
	public static String getImpostazioneFirmaAutomatica(String name) {
		return (impostazioniFirmaAutomatica != null && impostazioniFirmaAutomatica.getAttribute(name) != null) ? impostazioniFirmaAutomatica.getAttribute(name) : "";
	}

	public static boolean getImpostazioneFirmaAsBoolean(String name) {
		return (impostazioniFirma != null && impostazioniFirma.getAttributeAsBoolean(name) != null) ? impostazioniFirma.getAttributeAsBoolean(name) : false;
	}

	public static String getImpostazioniDocumento(String name) {
		return (impostazioniDocumento != null && impostazioniDocumento.getAttribute(name) != null) ? impostazioniDocumento.getAttribute(name) : "";
	}

	public static boolean getImpostazioniDocumentoAsBoolean(String name) {
		return (impostazioniDocumento != null && impostazioniDocumento.getAttributeAsBoolean(name) != null) ? impostazioniDocumento.getAttributeAsBoolean(name)
				: false;
	}

	public static String getImpostazioniFascicolo(String name) {
		return (impostazioniFascicolo != null && impostazioniFascicolo.getAttribute(name) != null) ? impostazioniFascicolo.getAttribute(name) : "";
	}

	public static boolean getImpostazioniFascicoloAsBoolean(String name) {
		return (impostazioniFascicolo != null && impostazioniFascicolo.getAttributeAsBoolean(name) != null) ? impostazioniFascicolo.getAttributeAsBoolean(name)
				: false;
	}
	
	public static String getImpostazioniSceltaOrganigramma(String name) {
		return (impostazioniSceltaOrganigramma != null && impostazioniSceltaOrganigramma.getAttribute(name) != null) ? impostazioniSceltaOrganigramma.getAttribute(name) : "";
	}
	
	public static boolean getImpostazioniSceltaOrganigrammaAsBoolean(String name) {
		return (impostazioniSceltaOrganigramma != null && impostazioniSceltaOrganigramma.getAttributeAsBoolean(name) != null) ? impostazioniSceltaOrganigramma.getAttributeAsBoolean(name)
				: false;
	}

	public static boolean getImpostazioniSceltaAccessibilitaAsBoolean(String name) {
		return (impostazioniSceltaAccessibilita != null && impostazioniSceltaAccessibilita.getAttributeAsBoolean(name) != null) ? impostazioniSceltaAccessibilita.getAttributeAsBoolean(name)
				: false;
	}
	
	public static boolean getImpostazioniAperturaDettaglioPerRicercaPuntualeAsBoolean(String name) {
		return (impostazioniAperturaDettaglioPerRicercaPuntuale != null && impostazioniAperturaDettaglioPerRicercaPuntuale.getAttributeAsBoolean(name) != null) ? impostazioniAperturaDettaglioPerRicercaPuntuale.getAttributeAsBoolean(name)
				: false;
	}
	
	public static boolean showEtichettaPerBarcode(){
		return AurigaLayout.getParametroDBAsBoolean("ATTIVA_REP_ETICHETTA_SEPARATA") || AurigaLayout.getParametroDBAsBoolean("ATTIVA_BARCODE_ETICHETTA_SEPARATA");
	}
	
// INIZIO JIRA AURIGA-439
	
//	// questo metodo ritorna la UO settata come predefinita dall'utente
//	public static String getUoLavoro() {
//		if (uoLavoro != null && !"".equals(uoLavoro) && mappaUoCollegateUtente.get(uoLavoro) != null) {
//			return uoLavoro;
//		}
//		return null;
//	}
//
//	// questo metodo ritorna la UO su cui è attiva la funzione di protocollo
//	public static String getIdUOPuntoProtAttivato() {
//		return idUOPuntoProtAttivato;
//	}
//
//	// questo metodo ritorna la mappa di tutte le UO associate all'utente
//	public static LinkedHashMap<String, String> getUoCollegateUtenteValueMap() {
//		LinkedHashMap<String, String> ret = new LinkedHashMap<String, String>();
//		ret.putAll(uoCollegateUtenteValueMap);
//		return ret;
//	}
//		
//	// questo metodo ritorna la mappa di tutte le UO associate all'utente se non è settata una UO di lavoro predefinita, in tal caso ritornerà solo quella
//	// se ho un'unica UO associata all'utente il metodo ovviamente restituirà solo quella 
//	public static LinkedHashMap<String, String> getSelezioneUoCollegateUtenteValueMap() {
//		LinkedHashMap<String, String> ret = new LinkedHashMap<String, String>();
//		if (uoLavoro != null && !"".equals(uoLavoro) && uoCollegateUtenteValueMap.get(uoLavoro) != null) {
//			ret.put(uoLavoro, uoCollegateUtenteValueMap.get(uoLavoro));
//		} else {
//			ret.putAll(uoCollegateUtenteValueMap);
//		}
//		return ret;
//	}
//	
//	
//	// questo metodo ritorna la mappa di tutte le UO di registrazione se non è settata una UO su cui è attiva la funzione di protocollo oppure una UO di lavoro predefinita, in tal caso ritornerà solo quella
//	// se ho un'unica UO di registrazione il metodo ovviamente restituirà solo quella 
//	public static LinkedHashMap<String, String> getSelezioneUoRegistrazioneValueMap() {		
//		LinkedHashMap<String, String> ret = new LinkedHashMap<String, String>();
//		String uoPuntoProtAttivato = idUOPuntoProtAttivato != null && !"".equals(idUOPuntoProtAttivato) ? "UO" + idUOPuntoProtAttivato : null;
//		if(uoPuntoProtAttivato != null && !"".equals(uoPuntoProtAttivato) && uoRegistrazioneValueMap.get(uoPuntoProtAttivato) != null){
//			ret.put(uoPuntoProtAttivato, uoRegistrazioneValueMap.get(uoPuntoProtAttivato));
//		} else if (uoLavoro != null && !"".equals(uoLavoro) && uoRegistrazioneValueMap.get(uoLavoro) != null) {
//			ret.put(uoLavoro, uoRegistrazioneValueMap.get(uoLavoro));
//		} else {
//			ret.putAll(uoRegistrazioneValueMap);
//		}
//		return ret;
//	}
//	
//	// questo metodo ritorna la mappa di tutte le UO proponenti atti
//	public static LinkedHashMap<String, String> getUoProponenteAttiValueMap() {		
//		LinkedHashMap<String, String> ret = new LinkedHashMap<String, String>();
//		ret.putAll(uoProponenteAttiValueMap);
//		return ret;
//	}
//	
//	// questo metodo ritorna la mappa di tutte le UO proponente atti se non è settata una UO di lavoro predefinita, in tal caso ritornerà solo quella
//	// se ho un'unica UO proponente atti per gli atti il metodo ovviamente restituirà solo quella 
//	public static LinkedHashMap<String, String> getSelezioneUoProponenteAttiValueMap() {
//		LinkedHashMap<String, String> ret = new LinkedHashMap<String, String>();
//		if (uoLavoro != null && !"".equals(uoLavoro) && uoProponenteAttiValueMap.get(uoLavoro) != null) {
//			ret.put(uoLavoro, uoProponenteAttiValueMap.get(uoLavoro));
//		} else {
//			ret.putAll(uoProponenteAttiValueMap);
//		}
//		return ret;
//	}	
		
	// questo metodo ritorna la UO settata come predefinita dall'utente
	public static String getUoLavoro() {
		if (uoLavoro != null && !"".equals(uoLavoro) && mappaUoSpecificitaSceltaUOLavoroValueMap.get(uoLavoro) != null) {
			return uoLavoro;
		}
		return null;
	}
	
	// questo metodo ritorna la UO settata come predefinita dall'utente senza l'eventuale prefisso UO
	public static String getIdUoLavoro() {
		String uoLavoro = AurigaLayout.getUoLavoro();		
		if(uoLavoro != null && uoLavoro.startsWith("UO")) {
			return uoLavoro.substring(2);
		}
		return uoLavoro;
	}

	// questo metodo ritorna la UO su cui è attiva la funzione di protocollo
	public static String getIdUOPuntoProtAttivato() {
		return idUOPuntoProtAttivato;
	}
		
	// questo metodo ritorna la mappa di tutte le UO associate all'utente
	public static LinkedHashMap<String, String> getUoCollegateUtenteValueMap() {
		LinkedHashMap<String, String> ret = new LinkedHashMap<String, String>();
		ret.putAll(uoSpecificitaSceltaUOLavoroValueMap);
		return ret;
	}
		
	// questo metodo ritorna la mappa di tutte le UO associate all'utente se non è settata una UO di lavoro predefinita, in tal caso ritornerà solo quella
	// se ho un'unica UO associata all'utente il metodo ovviamente restituirà solo quella 
	public static LinkedHashMap<String, String> getSelezioneUoCollegateUtenteValueMap() {
		LinkedHashMap<String, String> ret = new LinkedHashMap<String, String>();
		if (uoLavoro != null && !"".equals(uoLavoro) && uoSpecificitaSceltaUOLavoroValueMap.get(uoLavoro) != null) {
			ret.put(uoLavoro, uoSpecificitaSceltaUOLavoroValueMap.get(uoLavoro));
		} else {
			ret.putAll(uoSpecificitaSceltaUOLavoroValueMap);
		}
		return ret;
	}
	
	// questo metodo ritorna la mappa di tutte le UO di registrazione protocollo, repertorio e bozze
	public static LinkedHashMap<String, String> getUoRegistrazioneValueMap() {
		LinkedHashMap<String, String> ret = new LinkedHashMap<String, String>();
		ret.putAll(uoRegistrazioneValueMap);
		return ret;
	}
	
	// Questo metodo ritorna la mappa di tutte le UO con specificità REGISTRAZIONE_E
	public static LinkedHashMap<String, String> getUoSpecificitaRegistrazioneEValueMap() {
		LinkedHashMap<String, String> ret = new LinkedHashMap<String, String>();
		ret.putAll(uoSpecificitaRegistrazioneEValueMap);
		return ret;
	}
	
	
	// Questo metodo ritorna la mappa di tutte le UO con specificità REGISTRAZIONE_UI
	public static LinkedHashMap<String, String> getUoSpecificitaRegistrazioneUIValueMap() {
		LinkedHashMap<String, String> ret = new LinkedHashMap<String, String>();
		ret.putAll(uoSpecificitaRegistrazioneUIValueMap);
		return ret;
	}
	
	// Questo metodo ritorna la mappa di tutte le UO con specificità AVVIO_ITER_ATTO
	public static LinkedHashMap<String, String> getUoSpecificitaAvvioIterAttiValueMap() {
		LinkedHashMap<String, String> ret = new LinkedHashMap<String, String>();
		ret.putAll(uoSpecificitaAvvioIterAttiValueMap);
		return ret;
	}
	
	// Questo metodo ritorna la mappa di tutte le UO con specificità SCELTA_UO_LAVORO
	public static LinkedHashMap<String, String> getUoSpecificitaSceltaUOLavoroValueMap() {
		LinkedHashMap<String, String> ret = new LinkedHashMap<String, String>();
		ret.putAll(uoSpecificitaSceltaUOLavoroValueMap);
		return ret;
	}
	
	// questo metodo ritorna la mappa di tutte le UO di registrazione se non è settata una UO su cui è attiva la funzione di protocollo oppure una UO di lavoro predefinita, in tal caso ritornerà solo quella
	// se ho un'unica UO di registrazione il metodo ovviamente restituirà solo quella 
	public static LinkedHashMap<String, String> getSelezioneUoRegistrazioneValueMap(String flgTipoProv) {		
		LinkedHashMap<String, String> ret = new LinkedHashMap<String, String>();
		if (flgTipoProv != null && "E".equalsIgnoreCase(flgTipoProv)) {
			// Provengo da una protocollazione in entrata
			String uoPuntoProtAttivato = idUOPuntoProtAttivato != null && !"".equals(idUOPuntoProtAttivato) ? "UO" + idUOPuntoProtAttivato : null;
			if(uoPuntoProtAttivato != null && !"".equals(uoPuntoProtAttivato) && uoSpecificitaRegistrazioneEValueMap.get(uoPuntoProtAttivato) != null){
				ret.put(uoPuntoProtAttivato, uoSpecificitaRegistrazioneEValueMap.get(uoPuntoProtAttivato));
			} else if (uoLavoro != null && !"".equals(uoLavoro) && uoSpecificitaRegistrazioneEValueMap.get(uoLavoro) != null) {
				ret.put(uoLavoro, uoSpecificitaRegistrazioneEValueMap.get(uoLavoro));
			} else {
				ret.putAll(uoSpecificitaRegistrazioneEValueMap);
			}
			return ret;
		} else if (flgTipoProv != null) {
			// Provengo da una protocollazione in uscita o interna
			String uoPuntoProtAttivato = idUOPuntoProtAttivato != null && !"".equals(idUOPuntoProtAttivato) ? "UO" + idUOPuntoProtAttivato : null;
			if(uoPuntoProtAttivato != null && !"".equals(uoPuntoProtAttivato) && uoSpecificitaRegistrazioneUIValueMap.get(uoPuntoProtAttivato) != null){
				ret.put(uoPuntoProtAttivato, uoSpecificitaRegistrazioneUIValueMap.get(uoPuntoProtAttivato));
			} else if (uoLavoro != null && !"".equals(uoLavoro) && uoSpecificitaRegistrazioneUIValueMap.get(uoLavoro) != null) {
				ret.put(uoLavoro, uoSpecificitaRegistrazioneUIValueMap.get(uoLavoro));
			} else {
				ret.putAll(uoSpecificitaRegistrazioneUIValueMap);
			}
			return ret;
		} else {
			// Provengo da una bozza
			String uoPuntoProtAttivato = idUOPuntoProtAttivato != null && !"".equals(idUOPuntoProtAttivato) ? "UO" + idUOPuntoProtAttivato : null;
			if(uoPuntoProtAttivato != null && !"".equals(uoPuntoProtAttivato) && uoRegistrazioneValueMap.get(uoPuntoProtAttivato) != null){
				ret.put(uoPuntoProtAttivato, uoRegistrazioneValueMap.get(uoPuntoProtAttivato));
			} else if (uoLavoro != null && !"".equals(uoLavoro) && uoRegistrazioneValueMap.get(uoLavoro) != null) {
				ret.put(uoLavoro, uoRegistrazioneValueMap.get(uoLavoro));
			} else {
				ret.putAll(uoRegistrazioneValueMap);
			}
			return ret;
		}
	}
	
	// questo metodo ritorna la mappa di tutte le UO proponenti atti
	public static LinkedHashMap<String, String> getUoProponenteAttiValueMap() {		
		LinkedHashMap<String, String> ret = new LinkedHashMap<String, String>();
		ret.putAll(uoSpecificitaAvvioIterAttiValueMap);
		return ret;
	}
	
	// questo metodo ritorna la mappa di tutte le UO proponente atti se non è settata una UO di lavoro predefinita, in tal caso ritornerà solo quella
	// se ho un'unica UO proponente atti per gli atti il metodo ovviamente restituirà solo quella 
	public static LinkedHashMap<String, String> getSelezioneUoProponenteAttiValueMap() {
		LinkedHashMap<String, String> ret = new LinkedHashMap<String, String>();
		if (uoLavoro != null && !"".equals(uoLavoro) && uoSpecificitaAvvioIterAttiValueMap.get(uoLavoro) != null) {
			ret.put(uoLavoro, uoSpecificitaAvvioIterAttiValueMap.get(uoLavoro));
		} else {
			ret.putAll(uoSpecificitaAvvioIterAttiValueMap);
		}
		return ret;
	}	
	
	// FINE JIRA AURIGA-439
	
	public static LinkedHashMap<String, Record> getUoRegistrazioneMap() {
		LinkedHashMap<String, Record> ret = new LinkedHashMap<String, Record>();
		ret.putAll(mappaUoRegistrazione);
		return ret;
	}
	
	public static LinkedHashMap<String, Record> getDestProtEntrataDefaultMap() {
		LinkedHashMap<String, Record> ret = new LinkedHashMap<String, Record>();
		ret.putAll(mappaDestProtEntrataDefault);
		return ret;
	}
	
	public static boolean isAttivaFinalitaForRestrAssCartaceo() {
//		return getParametroDBAsBoolean("ATTIVA_RESTR_ASS_CARTACEO");
		return false;
	}
	
	public static boolean isAttivoClassifSenzaFasc(String flgTipoProv) {
		//se CLASSIF_SENZA_FASC è true ignoro gli altri
		if(getParametroDBAsBoolean("CLASSIF_SENZA_FASC")) {
			return true;
		} else if(flgTipoProv != null) {
			if("E".equalsIgnoreCase(flgTipoProv)) {
				return getParametroDBAsBoolean("CLASSIF_SENZA_FASC_E");
			} else if("U".equalsIgnoreCase(flgTipoProv)) {
				return getParametroDBAsBoolean("CLASSIF_SENZA_FASC_U");
			} else if("I".equalsIgnoreCase(flgTipoProv)) {
				return getParametroDBAsBoolean("CLASSIF_SENZA_FASC_I");
			} 
		}
		return false;
	}
	
	public static boolean isAttivaRollbackNumerazoneDefAtti(String siglaReg) {
		// se è prevista la numerazione
		if(siglaReg != null && !"".equals(siglaReg)) {
			if(getParametroDBAsBoolean("ATTIVA_ROLLBACK_NUMERAZIONE_DEF_ATTI")) {
				String registriWithRollback = AurigaLayout.getParametroDB("RESTR_REGISTRI_CON_ROLLBACK");
				if(registriWithRollback != null && !"".equals(registriWithRollback)) {
					for (String siglaRegWithRollback : new StringSplitterClient(registriWithRollback, ";").getTokens()) {
						if(siglaRegWithRollback != null && !"".equals(siglaRegWithRollback) && siglaReg.equalsIgnoreCase(siglaRegWithRollback)) {
							return true;
						}
					}
					return false;				
				}
				return true;
			}
		}
		return false;
	}
	
	public static boolean isAttivaRollbackNumeerazioneDefDocFascicolo(String siglaReg) {
		// se è prevista la numerazione
		if(siglaReg != null && !"".equals(siglaReg)) {
			if(getParametroDBAsBoolean("ATTIVA_ROLLBACK_NUM_DEF_DOC_FASC")) {
				String registriWithRollback = AurigaLayout.getParametroDB("RESTR_REGISTRI_CON_ROLLBACK");
				if(registriWithRollback != null && !"".equals(registriWithRollback)) {
					for (String siglaRegWithRollback : new StringSplitterClient(registriWithRollback, ";").getTokens()) {
						if(siglaRegWithRollback != null && !"".equals(siglaRegWithRollback) && siglaReg.equalsIgnoreCase(siglaRegWithRollback)) {
							return true;
						}
					}
					return false;				
				}
				return true;
			}
		}
		return false;
	}

	public static boolean getParametroDBAsBoolean(String nome) {
		String value = parametriDB != null ? (String) parametriDB.get(nome) : null;
		return (value != null && (value.equalsIgnoreCase("true") || value.equals("1")));
	}

	public static String getParametroDB(String nome) {
		String value = parametriDB != null ? (String) parametriDB.get(nome) : null;
		return value != null ? value : "";
	}

	/**
	 * Metodo che ritorna la firma da utilizzare in base alla situazione (nuova/risposta/inoltro) in cui ci si trova. La firma può essere infatti diversa in
	 * base alla situazione in cui ci si trova (nuova mail / risposta / inoltro). In queste situazioni si deve utilizzare la firma che è stata selezionata come
	 * tale nell'apposita sezione. Se invece non è stata impostata nessuna di queste si deve utilizzare la firma indicata come predefinita.
	 * 
	 * ATTENZIONE: Questo metodo viene chiamato solamente quando il parametro da DB SHOW_FIRME_IN_CALCE è true
	 * e quindi quando si necessita di controllare quale, tra le varie firme presenti, utilizzare
	 * 
	 * @return la firma da utilizzare
	 */
	public static String getHtmlSignature(String typology) {

		String signature = "";

		if (typology.equals("nuovo") && AurigaLayout.getFirmaEmailAutoNuova() != null) {
			/*
			 * Allora nelle preference è stata impostata la firma da utilizzare automaticamente nel caso di una nuova mail
			 */
			signature += AurigaLayout.getFirmaEmailHtml(AurigaLayout.getFirmaEmailAutoNuova());
		} else if (typology.equals("risposta") && AurigaLayout.getFirmaEmailAutoRisposta() != null) {
			/*
			 * Allora nelle preference è stata impostata la firma da utilizzare automaticamente nel caso di risposta
			 */
			signature += AurigaLayout.getFirmaEmailHtml(AurigaLayout.getFirmaEmailAutoRisposta());

		} else if (typology.equals("inoltro") && AurigaLayout.getFirmaEmailAutoInoltro() != null) {
			/*
			 * Allora nelle preference è stata impostata la firma da utilizzare automaticamente nel caso di inoltro
			 */
			signature += AurigaLayout.getFirmaEmailHtml(AurigaLayout.getFirmaEmailAutoInoltro());
		}

		return signature;

	}
	
	public void showNewsAlert() {
		loadUserPreference("skipNewsAlert", "DEFAULT", new ServiceCallback<Record>() {

			@Override
			public void execute(Record recordPref) {
				boolean skipNewsAlert = false;
				if (recordPref != null) {
					if(recordPref.getAttribute("value") != null && !"".equals(recordPref.getAttribute("value"))) {
						skipNewsAlert = new Boolean(recordPref.getAttribute("value"));
					}
				}
				if ((!skipNewsAlert) && (getParametroDB("NEWS_TO_SHOW") != null && !"".equals(getParametroDB("NEWS_TO_SHOW")))) {
					new NewsDialogBox("News", getParametroDB("NEWS_TO_SHOW")) {

						@Override
						public void onClickButton(boolean flgSkipNews) {
							if (flgSkipNews) {
								saveUserPreference("skipNewsAlert", "DEFAULT", "true", null, null);
							}

						}
					};
				}
			}
		});
	}

	public static Window getParentWindow(Canvas canvas) {
		if (canvas == null) {
			return null;
		} else if (canvas instanceof Window){
			return (Window) canvas;
		} else {
			return getParentWindow(canvas.getParentElement());
		}
	}
	
	private String setTitleAlign(String title) {
		return "<span style=\"width: 190px; display: inline-block;\">" + title + "</span>";
	}
	
	public static final RecordList buildRecordListFromArrayListOfMap(ArrayList<Map> lArrayList) {
		if(lArrayList == null) {
			return null;
		}
		RecordList lRecordList = new RecordList();		
		for(int i = 0; i < lArrayList.size(); i++) {
			Record lRecord = new Record(lArrayList.get(i));
			lRecordList.add(lRecord);
		}
		return lRecordList;
	}
	
	public static void addTemporaryDisabledButtons(Canvas button) {
		if(button != null) {
			temporaryDisabledButtons.add(button);		
		}
	}
	
	public static void removeTemporaryDisabledButtons(Canvas button) {
		if(button != null) {
			temporaryDisabledButtons.remove(button);
		}
	}
	
	/**
	 * Metodo che indica se è attivo il modulo protocollo
	 * 
	 */
	public static boolean isAttivoModuloProt() {
		return getParametroDBAsBoolean("ATTIVO_MODULO_PROT");
	}
	
	public static boolean isAttivoClienteCMMI() {
		String value = AurigaLayout.getParametroDB("CLIENTE");
		return value != null && !"".equals(value) && "CMMI".equals(value);
	}	
	
	public static boolean isAttivoClienteA2A() {
		String value = AurigaLayout.getParametroDB("CLIENTE");
		return value != null && !"".equals(value) && "A2A".equals(value);
	}	
	
	public static boolean isAttivoClienteRER() {
		String value = AurigaLayout.getParametroDB("CLIENTE");
		return value != null && !"".equals(value) && "RER".equals(value);
	}	
	
	public static boolean isAttivoClienteADSP() {
		String value = AurigaLayout.getParametroDB("CLIENTE");
		return value != null && !"".equals(value) && "ADSP".equals(value);
	}
	
	public static boolean isAttivoClienteCOTO() {
		String value = AurigaLayout.getParametroDB("CLIENTE");
		return value != null && !"".equals(value) && "COTO".equals(value);
	}
	
	public static boolean isAttivoClienteCMTO() {
		String value = AurigaLayout.getParametroDB("CLIENTE");
		return value != null && !"".equals(value) && "CMTO".equals(value);
	}
	
	public static boolean isAttivaNuovaPropostaAtto2() {
		return getParametroDBAsBoolean("ATTIVA_NUOVA_PROPOSTA_ATTO_2");
	}
	
	public static boolean isAttivaNuovaPropostaAtto2Completa() {
		return isAttivaNuovaPropostaAtto2() && getParametroDBAsBoolean("GESTIONE_ATTI_COMPLETA");
	}
	
	public static boolean isAttivoSmistamentoAttiRagioneria() {	
		return getParametroDBAsBoolean("ATTIVA_SMIST_ATTI_RAG_CON_CARICO_LAV");
	}
	
	public static boolean isAttivoSmistamentoAttiGruppi() {	
		return getParametroDBAsBoolean("ATTIVA_ASS_RAG_A_GRUPPI");
	}
	
	public void setShibbolethLogoutInfo() {
		// Ricavo la url di logout
		if (getGenericConfig().getShibbolethLogoutUrlHeaderName() != null && !"".equals(getGenericConfig().getShibbolethLogoutUrlHeaderName())) {
			Record record = new Record();
			record.setAttribute("key", getGenericConfig().getShibbolethLogoutUrlHeaderName());
			GWTRestDataSource lGwtRestDataSource = new GWTRestDataSource("ServiceRestUserUtil");
			lGwtRestDataSource.executecustom("getRequestHeaderValue", record, new DSCallback() {

				@Override
				public void execute(DSResponse response, Object rawData, DSRequest request) {
					Record data = response.getData()[0];
					String urlToRedirect = data.getAttributeAsString("value");
					if (urlToRedirect != null && !"".equalsIgnoreCase(urlToRedirect)) {
						String logoutUrlSuffix = getGenericConfig().getShibbolethLogoutUrlSuffix();
						shibbolethLogoutUrl = (urlToRedirect + (logoutUrlSuffix != null && !"".equalsIgnoreCase(logoutUrlSuffix) ? logoutUrlSuffix : ""));
					} else {
						shibbolethLogoutUrl = "";
					}
				}
			});
		}
	}
	
	public void setShibbolethRedirectUrlAfterSessionExpired() {
		// Ricavo la url su cui si trova l'applicativo che gira sotto shibboleth, per fare la redirect quando la sessione scade
		if (getGenericConfig().getShibbolethRedirectUrlAfterSessionExpiredHeaderName() != null && !"".equals(getGenericConfig().getShibbolethRedirectUrlAfterSessionExpiredHeaderName())) {
			Record record = new Record();
			record.setAttribute("key", getGenericConfig().getShibbolethRedirectUrlAfterSessionExpiredHeaderName());
			GWTRestDataSource lGwtRestDataSource = new GWTRestDataSource("ServiceRestUserUtil");
			lGwtRestDataSource.executecustom("getRequestHeaderValue", record, new DSCallback() {

				@Override
				public void execute(DSResponse response, Object rawData, DSRequest request) {
					Record data = response.getData()[0];
					String urlToRedirect = data.getAttributeAsString("value");
					if (urlToRedirect != null && !"".equalsIgnoreCase(urlToRedirect)) {
						shibbolethRedirectUrlAfterSessionExpired = urlToRedirect;
					}
				}
			});
		}
	}
	
	public static boolean isDisattivaJnlp() {
		return getParametroDBAsBoolean("DISABILITA_JNLP");
	}
	
	public static String getTipoStampaEtichette() {
		return AurigaLayout.getParametroDB("MODALITA_STAMPA_ETICHETTE");
	}
	
	public static boolean isBottoniStampaEtichetteHybridDisattivi () {
		boolean result = false;
		boolean disattivaJnlp = isDisattivaJnlp();
		String tipoStampaEtichette = getTipoStampaEtichette();
		if (disattivaJnlp || (tipoStampaEtichette!=null && tipoStampaEtichette.trim().equalsIgnoreCase("PDF"))) {
			result = true;
		} 
		return result;
	}
	
	public static boolean impostazioniSceltaAccessibilitaIsNull () {
		return impostazioniSceltaAccessibilita==null;
	}
	
	public static String getSuffixFormItemTitle() {
//		return "&nbsp;:";
		return "";
	}
	
	public static boolean isNascondiFiltroFulltext() {
		return getParametroDBAsBoolean("HIDE_LUCENE_FILTER_DOC_SEARCH");
	}
	
	public static boolean isAttivaGestioneUfficioGare() {
		return AurigaLayout.getParametroDBAsBoolean("ATTIVA_GEST_UFF_GARE");
	}
	
	@Override
	public void showCookiePrivacyPreview(final String uriPreview) {
		// TODO Auto-generated method stub
		super.showCookiePrivacyPreview(uriPreview);
		Record lRecordFileVistoRegContabile = new Record();
		final String nomeFile = "Cookie Policy";
		lRecordFileVistoRegContabile.setAttribute("uri", uriPreview);
		lRecordFileVistoRegContabile.setAttribute("nomeFile", nomeFile);
		new GWTRestDataSource("ProtocolloDataSource").executecustom("getInfoFromFile", lRecordFileVistoRegContabile, new DSCallback() {
			
			@Override
			public void execute(DSResponse dsResponse, Object data, DSRequest dsRequest) {
				if (dsResponse.getStatus() == DSResponse.STATUS_SUCCESS) {
					Record result = dsResponse.getData()[0];
					InfoFileRecord infoFileVistoRegContabile = InfoFileRecord.buildInfoFileString(JSON.encode(result.getAttributeAsRecord("infoFile").getJsObj()));
					PreviewControl.switchPreview(uriPreview, false, infoFileVistoRegContabile, "FileToExtractBean", nomeFile, false);					
				}
			}
		});
	}
	
}