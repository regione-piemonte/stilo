/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.smartgwt.client.data.AdvancedCriteria;
import com.smartgwt.client.data.Criterion;
import com.smartgwt.client.data.DSCallback;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.data.RecordList;
import com.smartgwt.client.types.FieldType;
import com.smartgwt.client.types.OperatorId;
import com.smartgwt.client.util.BooleanCallback;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.grid.ListGridRecord;
import com.smartgwt.client.widgets.menu.Menu;
import com.smartgwt.client.widgets.menu.MenuItem;
import com.smartgwt.client.widgets.menu.events.MenuItemClickEvent;
import com.smartgwt.client.widgets.toolbar.ToolStripButton;

import it.eng.auriga.ui.module.layout.client.AurigaLayout;
import it.eng.auriga.ui.module.layout.client.ErroreMassivoPopup;
import it.eng.auriga.ui.module.layout.client.i18n.I18NUtil;
import it.eng.auriga.ui.module.layout.client.invioMail.InoltroMailWindow;
import it.eng.auriga.ui.module.layout.shared.util.AzioniRapide;
import it.eng.utility.ui.module.core.client.callback.ServiceCallback;
import it.eng.utility.ui.module.core.client.datasource.GWTRestDataSource;
import it.eng.utility.ui.module.core.client.datasource.GWTRestService;
import it.eng.utility.ui.module.core.shared.message.MessageBean;
import it.eng.utility.ui.module.core.shared.message.MessageType;
import it.eng.utility.ui.module.layout.client.Layout;
import it.eng.utility.ui.module.layout.client.common.CustomDetail;
import it.eng.utility.ui.module.layout.client.common.CustomLayout;
import it.eng.utility.ui.module.layout.client.common.MapCreator;
import it.eng.utility.ui.module.layout.client.common.MultiToolStripButton;

public class PostaElettronicaLayout extends CustomLayout {

	private MultiToolStripButton archiviaEmailMultiButton;
	private MultiToolStripButton assegnaEmailMultiButton;
	private MultiToolStripButton annullaAssegnazioneEmailButton;
	private MultiToolStripButton inoltraEmailMultiButton;
	private MultiToolStripButton eliminaEmailMultiButton;
	private MultiToolStripButton azioneDaFareMultiButton;
	private MultiToolStripButton annullaArchiviazioneMultiButton;
	private MultiToolStripButton presaInCaricoMultiButton;
	private MultiToolStripButton messaInCaricoMultiButton;
	private MultiToolStripButton mandaInApprovazioneMultiButton;
	private MultiToolStripButton apposizioneTagCommentiMultiButton;
	private MultiToolStripButton rilasciaMultiButton;
	private ToolStripButton nuovoMessaggioButton;
	private ToolStripButton nuovoMessaggioMultiDestinatariXlsButton;
	
	// private ToolStripButton firmaEmailButton;
	protected MultiToolStripButton frecciaInoltraToolStripButton;

	private final int ALT_POPUP_ERR_MASS = 300;
	private final int LARG_POPUP_ERR_MASS = 600;

	public PostaElettronicaLayout() {

		super("posta_elettronica", getDataSource(), new PostaElettronicaFilter("posta_elettronica", new MapCreator("classifica", "", "|*|").getProperties()),
				new PostaElettronicaList("posta_elettronica"), switchDetailPostaElettronica());

		newButton.hide();
		setMultiselect(true);
	}

	@Override
	public boolean showMaxRecordVisualizzabiliItem() {
		return true;
	}
	
	@Override
	protected GWTRestDataSource createNroRecordDatasource() {

		GWTRestDataSource referenceDatasource = new GWTRestDataSource("AurigaPostaElettronicaDataSource", "idEmail", FieldType.TEXT);
		referenceDatasource.addParam("CruscottoMail", "1");
		referenceDatasource.addParam("isCruscottoMail", "true");
		// permette l'interazione utente anche durante l'elaborazione lato server
		referenceDatasource.setForceToShowPrompt(false);

		return referenceDatasource;
	}

	private static GWTRestDataSource getDataSource() {
		GWTRestDataSource datasource = new GWTRestDataSource("AurigaPostaElettronicaDataSource", "idEmail", FieldType.TEXT);
		datasource.addParam("isCruscottoMail", "true");
		return datasource;
	}
	
	@Override
	public boolean showPaginazioneItems() {
		return AurigaLayout.getParametroDBAsBoolean("ATTIVA_PAGINAZIONE_CRUSCOTTO_MAIL");
	}

	private static CustomDetail switchDetailPostaElettronica() {

		CustomDetail portletLayout = null;

		if (!AurigaLayout.getParametroDBAsBoolean("DETT_EMAIL_UNICO")) {
			portletLayout = new PostaElettronicaDetail("posta_elettronica");
		} else if (AurigaLayout.getParametroDBAsBoolean("DETT_EMAIL_UNICO")) {
			portletLayout = new DettaglioPostaElettronica("posta_elettronica");
		} else {
			portletLayout = new CustomDetail("posta_elettronica");
		}
		return portletLayout;
	}

	@Override
	public AdvancedCriteria buildSearchCriteria(AdvancedCriteria criteria) {

		List<Criterion> criterionList = new ArrayList<Criterion>();
		if (criteria == null) {
			criteria = new AdvancedCriteria();
		} else {
			for (Criterion crit : criteria.getCriteria()) {
				criterionList.add(crit);
			}
		}
		criterionList.add(new Criterion("classifica", OperatorId.EQUALS, "standard.arrivo"));
		Criterion[] criterias = new Criterion[criterionList.size()];
		for (int i = 0; i < criterionList.size(); i++) {
			criterias[i] = criterionList.get(i);
		}
		return super.buildSearchCriteria(new AdvancedCriteria(OperatorId.AND, criterias));
	}

	@Override
	protected ToolStripButton[] getCustomNewButtons() {

		if (Layout.isPrivilegioAttivo("EML/INS")) {
			nuovoMessaggioButton = new ToolStripButton();
			nuovoMessaggioButton.setIcon("mail/PEO.png");
			nuovoMessaggioButton.setIconSize(16);
			nuovoMessaggioButton.setPrompt("Nuovo messaggio");
			nuovoMessaggioButton.addClickHandler(new ClickHandler() {

				@Override
				public void onClick(ClickEvent event) {
					new NuovoMessaggioWindow("nuovo_messaggio_cruscotto","invioNuovoMessaggio", instance, new DSCallback() {

						@Override
						public void execute(DSResponse response, Object rawData, DSRequest request) {
							doSearch();
						}
					});
				}
			});
			
			nuovoMessaggioMultiDestinatariXlsButton = new ToolStripButton();
			nuovoMessaggioMultiDestinatariXlsButton.setIcon("menu/import_excel.png");
			nuovoMessaggioMultiDestinatariXlsButton.setIconSize(16);
			nuovoMessaggioMultiDestinatariXlsButton.setPrompt("Nuovo messaggio per lista destinatari da xls");
			nuovoMessaggioMultiDestinatariXlsButton.addClickHandler(new ClickHandler() {

				@Override
				public void onClick(ClickEvent event) {
					new NuovoMessaggioXlsWindow("nuovoMessaggioMultiDestinatariXls", instance, new DSCallback() {

						@Override
						public void execute(DSResponse response, Object rawData, DSRequest request) {
							doSearch();
						}
					});
				}
			});
			
			return new ToolStripButton[] { nuovoMessaggioButton , nuovoMessaggioMultiDestinatariXlsButton };
		}

		return new ToolStripButton[] {};
	}

	@Override
	protected MultiToolStripButton[] getMultiselectButtons() {

		if (archiviaEmailMultiButton == null) {

			archiviaEmailMultiButton = new MultiToolStripButton("archivio/archiviazione.png", this, AurigaLayout.getParametroDB("LABEL_ARCHIVIAZIONE_EMAIL"),
					false) {

				@Override
				public void doSomething() {
					final RecordList listaEmail = new RecordList();

					for (int i = 0; i < list.getSelectedRecords().length; i++) {
						listaEmail.add(list.getSelectedRecords()[i]);
					}

					archiviazioneMultipla(listaEmail);

				}
			};
		}
		
		// SCELTA U.O. COMPETENTE
		if (assegnaEmailMultiButton == null) {
			assegnaEmailMultiButton = new MultiToolStripButton("archivio/assegna.png", this, I18NUtil.getMessages().posta_elettronica_set_uo_competente(),
					false) {
				
				@Override
				public boolean toShow() {
					if(AurigaLayout.getParametroDBAsBoolean("DISATTIVA_ASSEGNAZIONE_EMAIL")) { 
						return false;
					}
					return true;
				}
				
				@Override
				public void doSomething() {
					
					final Menu creaAssegnaUO = new Menu(); 
					
					Record recordDestPref = new Record();						
					RecordList listaAzioniRapide = new RecordList();
					Record recordAzioneRapida = new Record();						
					recordAzioneRapida.setAttribute("azioneRapida", AzioniRapide.ASSEGNA_UO_COMPETENTE.getValue());
					listaAzioniRapide.add(recordAzioneRapida);
					recordDestPref.setAttribute("listaAzioniRapide", listaAzioniRapide);					
					
					GWTRestDataSource lGwtRestDataSource = new GWTRestDataSource("LoadComboDestinatariPreferiti");
					lGwtRestDataSource.performCustomOperation("getDestinatariPreferitiUtente", recordDestPref, new DSCallback() {
			
						@Override
						public void execute(DSResponse response, Object rawData, DSRequest request) {
							if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
								
								Record destinatariPreferiti = response.getData()[0];
//								final RecordList listaDestinatariPreferiti = destinatariPreferiti.getAttributeAsRecordList("listaUOPreferiteMail");
								final RecordList listaDestinatariPreferiti = AurigaLayout.buildRecordListFromArrayListOfMap((ArrayList<Map>) destinatariPreferiti.getAttributeAsMap("mappaUOPreferite").get(AzioniRapide.ASSEGNA_UO_COMPETENTE.getValue()));							
								
								// Scelta U.O. Standard 
								MenuItem assegnaMenuStandardItem = new MenuItem("Standard");
								assegnaMenuStandardItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {
									
									@Override
									public void onClick(MenuItemClickEvent event) {
										
										final RecordList listaEmail = new RecordList();
										for (int i = 0; i < list.getSelectedRecords().length; i++) {
											listaEmail.add(list.getSelectedRecords()[i]);
										}

										assegnazioneEmail(listaEmail, listaDestinatariPreferiti);
										
									}
								});
								creaAssegnaUO.addItem(assegnaMenuStandardItem);
								
								// Scelta U.O. Rapida
								MenuItem assegnaMenuRapidoItem = new MenuItem("Rapida");				

								Boolean success = destinatariPreferiti.getAttributeAsBoolean("success");
								
								
								if(success != null && success == true
										&& listaDestinatariPreferiti != null && !listaDestinatariPreferiti.isEmpty()){
									
									Menu scelteRapide = new Menu();

									for(int i=0; i < listaDestinatariPreferiti.getLength();i++){
										
										Record currentRecord = listaDestinatariPreferiti.get(i);
										final String idDestinatarioPreferito = currentRecord.getAttribute("idDestinatarioPreferito");
										final String tipoDestinatarioPreferito = currentRecord.getAttribute("tipoDestinatarioPreferito");
										final String descrizioneDestinatarioPreferito = currentRecord.getAttribute("descrizioneDestinatarioPreferito");
										 
										MenuItem currentRapidoItem = new MenuItem(descrizioneDestinatarioPreferito); 
										currentRapidoItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {
											
											@Override
											public void onClick(MenuItemClickEvent event) {
												
												final RecordList listaEmail = new RecordList();
												for (int i = 0; i < list.getSelectedRecords().length; i++) {
													listaEmail.add(list.getSelectedRecords()[i]);
												}
												
												RecordList listaAssegnazioni = new RecordList();
												Record recordAssegnazioni = new Record();
												recordAssegnazioni.setAttribute("idUo", idDestinatarioPreferito);
												recordAssegnazioni.setAttribute("typeNodo",tipoDestinatarioPreferito);
												listaAssegnazioni.add(recordAssegnazioni);

												Record record = new Record();
												record.setAttribute("listaRecord", listaEmail);
												record.setAttribute("listaAssegnazioni", listaAssegnazioni);
												
												Layout.showWaitPopup(I18NUtil.getMessages().posta_elettronica_assegnazione_in_corso());
												GWTRestDataSource lGwtRestDataSource = new GWTRestDataSource("AssegnazioneEmailDataSource");
												lGwtRestDataSource.addParam("isMassivo", "1");
												try {
													lGwtRestDataSource.addData(record, new DSCallback() {

														@Override
														public void execute(DSResponse response, Object rawData, DSRequest request) {
															massiveOperationCallback(response, listaEmail, "idEmail", "oggetto",
																	I18NUtil.getMessages().posta_elettronica_assegnazione_successo(),
																	I18NUtil.getMessages().posta_elettronica_assegnazione_errore_totale(),
																	I18NUtil.getMessages().posta_elettronica_assegnazione_errore_parziale(), null);
														}
													});
												} catch (Exception e) {
													Layout.hideWaitPopup();
												}
												
											}
										});
										scelteRapide.addItem(currentRapidoItem);
										
									}
									
									assegnaMenuRapidoItem.setSubmenu(scelteRapide);
								} else {
									assegnaMenuRapidoItem.setEnabled(false);
								}
								creaAssegnaUO.addItem(assegnaMenuRapidoItem);
								
								creaAssegnaUO.showContextMenu();
								}
							}
						}, new DSRequest());
				}
			};
		}

		if (annullaAssegnazioneEmailButton == null) {
			annullaAssegnazioneEmailButton = new MultiToolStripButton("archivio/annulla_uo_competente.png", this,
					I18NUtil.getMessages().posta_elettronica_annulla_uo_competente(), false) {
				
				@Override
				public boolean toShow() {
					if(AurigaLayout.getParametroDBAsBoolean("DISATTIVA_ASSEGNAZIONE_EMAIL")) { 
						return false;
					}
					return true;
				}
				
				@Override
				public void doSomething() {

					final RecordList listaEmail = new RecordList();
					for (int i = 0; i < list.getSelectedRecords().length; i++) {
						listaEmail.add(list.getSelectedRecords()[i]);
					}

					annullamentoAssegnazioneEmail(listaEmail);

				}
			};
		}

		if (inoltraEmailMultiButton == null) {
			inoltraEmailMultiButton = new MultiToolStripButton("postaElettronica/inoltro.png", this, I18NUtil.getMessages().posta_elettronica_inoltra(),
					false) {
				
				@Override
				public boolean toShow() {
					if(AurigaLayout.getParametroDBAsBoolean("DISATTIVA_FORWARD_EMAIL")) { 
						return false;
					}
					return true;
				}
				
				@Override
				public void doSomething() {

					final RecordList listaEmail = new RecordList();

					for (int i = 0; i < list.getSelectedRecords().length; i++)
						listaEmail.add(list.getSelectedRecords()[i]);

					inoltraEmail(listaEmail);

				}
			};
		}

		if (eliminaEmailMultiButton == null) {
			eliminaEmailMultiButton = new MultiToolStripButton("buttons/cestino.png", this, I18NUtil.getMessages().posta_elettronica_eliminazione(), false) {

				@Override
				public boolean toShow() {
					return Layout.isPrivilegioAttivo("EML/DEL");
				}

				@Override
				public void doSomething() {
					SC.ask("Sei sicuro ?", new BooleanCallback() {
							@Override
							public void execute(Boolean value) {								
								if(value) {	
									// TODO Auto-generated method stub
									final RecordList listaEmail = new RecordList();
									for (int i = 0; i < list.getSelectedRecords().length; i++) {
										listaEmail.add(list.getSelectedRecords()[i]);
									}
									final Record record = new Record();
									record.setAttribute("listaRecord", listaEmail);
									GWTRestDataSource lGwtRestServiceEliminaEmail = new GWTRestDataSource("EliminazioneMassivaEmailDataSource");
									try {
										lGwtRestServiceEliminaEmail.addData(record, new DSCallback() {

											@Override
											public void execute(DSResponse response, Object rawData, DSRequest request) {
												massiveOperationEliminazioneCallback(response, listaEmail, "idEmail", "oggetto",
														I18NUtil.getMessages().posta_elettronica_eliminazione_successo(),
														I18NUtil.getMessages().posta_elettronica_eliminazione_errore_totale(),
														I18NUtil.getMessages().posta_elettronica_eliminazione_errore_parziale(), null);
											}
										});
									} catch (Exception e) {
										Layout.hideWaitPopup();
									}
								}								
							}	
					});
					
					
				}
			};
		}

		if (annullaArchiviazioneMultiButton == null) {
			annullaArchiviazioneMultiButton = new MultiToolStripButton("archivio/annullaArchiviazione.png", this,
					AurigaLayout.getParametroDB("LABEL_RIAPERTURA_EMAIL"), false) {

				@Override
				public void doSomething() {

					final RecordList listaEmail = new RecordList();
					for (int i = 0; i < list.getSelectedRecords().length; i++) {
						listaEmail.add(list.getSelectedRecords()[i]);
					}

					DatiOperazioneRichiestaWindow operazioneRichiestaWindow = new DatiOperazioneRichiestaWindow(instance, listaEmail, true, false);
					operazioneRichiestaWindow.show();
				}
			};
		}

		// PRESA IN CARICO
		if (presaInCaricoMultiButton == null) {
			presaInCaricoMultiButton = new MultiToolStripButton("postaElettronica/prendiInCarico.png", this,
					I18NUtil.getMessages().prendi_in_carico_label_button(), false) {

				@Override
				public boolean toShow() {
					if(AurigaLayout.getParametroDBAsBoolean("DISATTIVA_PRESA_IN_CARICO_EMAIL")) {
						return false;
					}
					return ((Layout.isPrivilegioAttivo("EML/LK/ASS")) || (Layout.isPrivilegioAttivo("EML/PLK/ASS")));
				}

				@Override
				public void doSomething() {
					final OperazioniPerEmailPopup presaInCaricoEmailPopup = new OperazioniPerEmailPopup(TipoOperazioneMail.PRESA_IN_CARICO.getValue(), null) {

						@Override
						public void onClickOkButton(final DSCallback callback) {

							final RecordList listaEmail = new RecordList();
							for (int i = 0; i < list.getSelectedRecords().length; i++) {
								listaEmail.add(list.getSelectedRecords()[i]);
							}
							Layout.showWaitPopup(I18NUtil.getMessages().posta_elettronica_presa_in_carico_in_corso());
							buildOperazioneSuEmail(listaEmail, null, getMotivo(), true, TipoOperazioneMail.PRESA_IN_CARICO.getValue());
							markForDestroy();
						}
					};
					presaInCaricoEmailPopup.show();
				}
			};
		}

		// MESSA IN CARICO
		if (messaInCaricoMultiButton == null) {
			messaInCaricoMultiButton = new MultiToolStripButton("postaElettronica/mettiInCarico.png", this,
					I18NUtil.getMessages().metti_in_carico_label_button(), false) {

				@Override
				public boolean toShow() {
					if(AurigaLayout.getParametroDBAsBoolean("DISATTIVA_PRESA_IN_CARICO_EMAIL")) {
						return false;
					}
					return Layout.isPrivilegioAttivo("EML/PLK/ASS");
				}

				@Override
				public void doSomething() {
					
					final Menu creaInCarico = new Menu();
					
					Record recordDestPref = new Record();						
					RecordList listaAzioniRapide = new RecordList();
					Record recordAzioneRapida = new Record();						
					recordAzioneRapida.setAttribute("azioneRapida", AzioniRapide.METTI_IN_CARICO.getValue());
					listaAzioniRapide.add(recordAzioneRapida);
					recordDestPref.setAttribute("listaAzioniRapide", listaAzioniRapide);					
					
					GWTRestDataSource lGwtRestDataSource = new GWTRestDataSource("LoadComboDestinatariPreferiti");
					lGwtRestDataSource.performCustomOperation("getDestinatariPreferitiUtente", recordDestPref, new DSCallback() {
			
						@Override
						public void execute(DSResponse response, Object rawData, DSRequest request) {
							if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
								
								Record destinatariPreferiti = response.getData()[0];
//								final RecordList listaDestinatariPreferiti = destinatariPreferiti.getAttributeAsRecordList("listaUtentiPreferitiMail");
								final RecordList listaDestinatariPreferiti = AurigaLayout.buildRecordListFromArrayListOfMap((ArrayList<Map>) destinatariPreferiti.getAttributeAsMap("mappaUtentiPreferiti").get(AzioniRapide.METTI_IN_CARICO.getValue()));
								
								// Messa in carico Standard 
								MenuItem messaInCaricoMenuStandardItem = new MenuItem("Standard");
								messaInCaricoMenuStandardItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {
									
									@Override
									public void onClick(MenuItemClickEvent event) {
										final OperazioniPerEmailPopup mettiInCaricoEmailPopup = new OperazioniPerEmailPopup(TipoOperazioneMail.MESSA_IN_CARICO.getValue(), listaDestinatariPreferiti) {

											@Override
											public void onClickOkButton(final DSCallback callback) {
												final RecordList listaEmail = new RecordList();
												for (int i = 0; i < list.getSelectedRecords().length; i++) {
													listaEmail.add(list.getSelectedRecords()[i]);
												}
												Layout.showWaitPopup(I18NUtil.getMessages().posta_elettronica_messa_in_carico_in_corso());
												buildOperazioneSuEmail(listaEmail, getUtente(), getMotivo(), true, TipoOperazioneMail.MESSA_IN_CARICO.getValue());
												markForDestroy();
											}
										};
										mettiInCaricoEmailPopup.show();
									}
								});
								creaInCarico.addItem(messaInCaricoMenuStandardItem);
								
								// Messa in carico Rapida
								MenuItem messaInCaricoRapidaItem = new MenuItem("Rapida");				

								
								Boolean success = destinatariPreferiti.getAttributeAsBoolean("success");
								
								
								if(success != null && success == true
										&& listaDestinatariPreferiti != null && !listaDestinatariPreferiti.isEmpty()){
									
									Menu scelteRapide = new Menu();

									for(int i=0; i < listaDestinatariPreferiti.getLength();i++){
										
										Record currentRecord = listaDestinatariPreferiti.get(i);
										final String idDestinatarioPreferito = currentRecord.getAttribute("idDestinatarioPreferito");
//										final String tipoDestinatarioPreferito = currentRecord.getAttribute("tipoDestinatarioPreferito");
										final String descrizioneDestinatarioPreferito = currentRecord.getAttribute("descrizioneDestinatarioPreferito");
										 
										MenuItem currentRapidoItem = new MenuItem(descrizioneDestinatarioPreferito); 
										currentRapidoItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {
											
											@Override
											public void onClick(MenuItemClickEvent event) {
												
												final RecordList listaEmail = new RecordList();
												for (int i = 0; i < list.getSelectedRecords().length; i++) {
													listaEmail.add(list.getSelectedRecords()[i]);
												}
												String motivo = null;
												String userLockFor = idDestinatarioPreferito;
												Layout.showWaitPopup(I18NUtil.getMessages().posta_elettronica_messa_in_carico_in_corso());
												buildOperazioneSuEmail(listaEmail, userLockFor, motivo, true, TipoOperazioneMail.MESSA_IN_CARICO.getValue());
												
											}
										});
										scelteRapide.addItem(currentRapidoItem);
										
									}
									
									messaInCaricoRapidaItem.setSubmenu(scelteRapide);
								} else {
									messaInCaricoRapidaItem.setEnabled(false);
								}
								creaInCarico.addItem(messaInCaricoRapidaItem);
								
								creaInCarico.showContextMenu();
								}
							}
						}, new DSRequest());
					
				}
			};
		}

		// MANDA IN APPROVAZIONE
		if (mandaInApprovazioneMultiButton == null) {
			mandaInApprovazioneMultiButton = new MultiToolStripButton("postaElettronica/manda_in_approvazione.png", this,
					I18NUtil.getMessages().posta_elettronica_list_mandaInApprovazioneMenuItem(), false) {

				@Override
				public boolean toShow() {
					if(AurigaLayout.getParametroDBAsBoolean("DISATTIVA_PRESA_IN_CARICO_EMAIL")) {
						return false;
					}
					return Layout.isPrivilegioAttivo("EML/PLK/ASS");
				}

				@Override
				public void doSomething() {
					
					final Menu creaInApprovazioneMenu = new Menu();
					
					Record recordDestPref = new Record();						
					RecordList listaAzioniRapide = new RecordList();
					Record recordAzioneRapida = new Record();						
					recordAzioneRapida.setAttribute("azioneRapida", AzioniRapide.MANDA_IN_APPROVAZIONE.getValue());
					listaAzioniRapide.add(recordAzioneRapida);
					recordDestPref.setAttribute("listaAzioniRapide", listaAzioniRapide);					
					
					GWTRestDataSource lGwtRestDataSource = new GWTRestDataSource("LoadComboDestinatariPreferiti");
					lGwtRestDataSource.performCustomOperation("getDestinatariPreferitiUtente", recordDestPref, new DSCallback() {
			
						@Override
						public void execute(DSResponse response, Object rawData, DSRequest request) {
							if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
								
								Record destinatariPreferiti = response.getData()[0];
//								final RecordList listaDestinatariPreferiti = destinatariPreferiti.getAttributeAsRecordList("listaUtentiPreferitiMail");
								final RecordList listaDestinatariPreferiti = AurigaLayout.buildRecordListFromArrayListOfMap((ArrayList<Map>) destinatariPreferiti.getAttributeAsMap("mappaUtentiPreferiti").get(AzioniRapide.MANDA_IN_APPROVAZIONE.getValue()));
								
								// Manda in approvazione Standard 
								MenuItem mandaInApprovazioneStandardItem = new MenuItem("Standard");
								mandaInApprovazioneStandardItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {
									
									@Override
									public void onClick(MenuItemClickEvent event) {
										final OperazioniPerEmailPopup mandInApprovazioneEmailPopup = new OperazioniPerEmailPopup(
												TipoOperazioneMail.MANDA_IN_APPROVAZIONE.getValue(), listaDestinatariPreferiti) {

											@Override
											public void onClickOkButton(final DSCallback callback) {
												final RecordList listaEmail = new RecordList();
												for (int i = 0; i < list.getSelectedRecords().length; i++) {
													listaEmail.add(list.getSelectedRecords()[i]);
												}
												String motivo = getMotivo();
												if (motivo != null && !"".equalsIgnoreCase(motivo)) {
													motivo = "[APPROVAZIONE]" + motivo;
												} else {
													motivo = "[APPROVAZIONE]";
												}
												Layout.showWaitPopup(I18NUtil.getMessages().posta_elettronica_messa_in_approvazione_in_corso());
												buildOperazioneSuEmail(listaEmail, getUtente(), motivo, true, TipoOperazioneMail.MANDA_IN_APPROVAZIONE.getValue());
												markForDestroy();
											}
										};
										mandInApprovazioneEmailPopup.show();
										
									}
								});
								creaInApprovazioneMenu.addItem(mandaInApprovazioneStandardItem);
								
								// Manda in approvazione Rapida
								MenuItem mandaInApprovazioneRapidaItem = new MenuItem("Rapida");				

								Boolean success = destinatariPreferiti.getAttributeAsBoolean("success");
								
								
								if(success != null && success == true
										&& listaDestinatariPreferiti != null && !listaDestinatariPreferiti.isEmpty()){
									
									Menu scelteRapide = new Menu();

									for(int i=0; i < listaDestinatariPreferiti.getLength();i++){
										
										Record currentRecord = listaDestinatariPreferiti.get(i);
										final String idDestinatarioPreferito = currentRecord.getAttribute("idDestinatarioPreferito");
//										final String tipoDestinatarioPreferito = currentRecord.getAttribute("tipoDestinatarioPreferito");
										final String descrizioneDestinatarioPreferito = currentRecord.getAttribute("descrizioneDestinatarioPreferito");
										 
										MenuItem currentRapidoItem = new MenuItem(descrizioneDestinatarioPreferito); 
										currentRapidoItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {
											
											@Override
											public void onClick(MenuItemClickEvent event) {
												
												final RecordList listaEmail = new RecordList();
												for (int i = 0; i < list.getSelectedRecords().length; i++) {
													listaEmail.add(list.getSelectedRecords()[i]);
												}
												String motivo = "[APPROVAZIONE]";
												String userLockFor = idDestinatarioPreferito;
												Layout.showWaitPopup(I18NUtil.getMessages().posta_elettronica_messa_in_carico_in_corso());
												buildOperazioneSuEmail(listaEmail, userLockFor, motivo, true, TipoOperazioneMail.MANDA_IN_APPROVAZIONE.getValue());
												
											}
										});
										scelteRapide.addItem(currentRapidoItem);
										
									}
									
									mandaInApprovazioneRapidaItem.setSubmenu(scelteRapide);
								} else {
									mandaInApprovazioneRapidaItem.setEnabled(false);
								}
								creaInApprovazioneMenu.addItem(mandaInApprovazioneRapidaItem);
								
								creaInApprovazioneMenu.showContextMenu();
								}
							}
						}, new DSRequest());
				}
			};
		}

		// RILASCIA
		if (rilasciaMultiButton == null) {
			rilasciaMultiButton = new MultiToolStripButton("postaElettronica/rilascia.png", this, I18NUtil.getMessages().rilascia_label_button(), false) {

				@Override
				public boolean toShow() {
				if(AurigaLayout.getParametroDBAsBoolean("DISATTIVA_PRESA_IN_CARICO_EMAIL")) {
						return false;
					}
					return Layout.isPrivilegioAttivo("EML/ULK/ASS");
				}

				@Override
				public void doSomething() {
					final OperazioniPerEmailPopup rilascioEmailPopup = new OperazioniPerEmailPopup(TipoOperazioneMail.RILASCIA.getValue(), null) {

						@Override
						public void onClickOkButton(final DSCallback callback) {
							final RecordList listaEmail = new RecordList();
							for (int i = 0; i < list.getSelectedRecords().length; i++) {
								listaEmail.add(list.getSelectedRecords()[i]);
							}
							Layout.showWaitPopup(I18NUtil.getMessages().posta_elettronica_rilascio_in_corso());
							rilascio(listaEmail, null, getMotivo(), true);
							markForDestroy();
						}
					};
					rilascioEmailPopup.show();
				}
			};
		}

		if (azioneDaFareMultiButton == null) {
			azioneDaFareMultiButton = new MultiToolStripButton("postaElettronica/todo.png", this, "Azione da fare", false) {

				@Override
				public void doSomething() {
					final RecordList listaEmail = new RecordList();
					for (int i = 0; i < list.getSelectedRecords().length; i++)
						listaEmail.add(list.getSelectedRecords()[i]);

					SelezionaAzioneDaFareWindow azioniDaFareWindow = new SelezionaAzioneDaFareWindow(true, listaEmail, instance, null);
					azioniDaFareWindow.show();
				}
			};
		}
		
		/**
		 * Apposizione Tag & Commenti
		 */
		if (apposizioneTagCommentiMultiButton == null) {
			apposizioneTagCommentiMultiButton = new MultiToolStripButton("postaElettronica/apposizione_tag_commenti.png", this,
					I18NUtil.getMessages().posta_elettronica_layout_apposizioneTagCommenti(), false) {

				@Override
				public void doSomething() {
					final RecordList listaEmail = new RecordList();
					for (int i = 0; i < list.getSelectedRecords().length; i++){
						listaEmail.add(list.getSelectedRecords()[i]);
					}
					new ApposizioneTagCommentiMailWindow("apposizione_tag_commenti_mail",listaEmail, new DSCallback() {
								
						@Override
						public void execute(DSResponse response, Object rawData, DSRequest request) {
							
							doSearch();
						}
					});
				}
			};
		}
		
		return new MultiToolStripButton[] { archiviaEmailMultiButton, assegnaEmailMultiButton, annullaAssegnazioneEmailButton, inoltraEmailMultiButton,
				eliminaEmailMultiButton, azioneDaFareMultiButton, annullaArchiviazioneMultiButton, presaInCaricoMultiButton, messaInCaricoMultiButton,
				mandaInApprovazioneMultiButton, rilasciaMultiButton, apposizioneTagCommentiMultiButton };
	}

	protected void archiviazioneEmail(final Record record, final boolean isMassiva) {

		String classifica = getFilter().getExtraParam().get("classifica");
		record.setAttribute("classifica", classifica);
		GWTRestDataSource lGwtRestDataSource = new GWTRestDataSource("ArchiviazioneEmailDataSource");
		lGwtRestDataSource.addData(record, new DSCallback() {

			@Override
			public void execute(DSResponse response, Object rawData, DSRequest request) {

				String errorMsg = archiviaEmail(response, record, "idEmail", "id", I18NUtil.getMessages().posta_elettronica_archiviazione_errore_totale(),
						I18NUtil.getMessages().posta_elettronica_archiviazione_errore_parziale(), isMassiva);

				RecordList records = record.getAttributeAsRecordList("listaRecord");
				if (isMassiva) {
					int[] recordsToSelect = null;
					Map errorMessages = response.getData()[0].getAttributeAsMap("errorMessages");
					if (errorMessages != null && errorMessages.size() > 0) {

						recordsToSelect = new int[errorMessages.size()];
						int rec = 0;
						for (int i = 0; i < records.getLength(); i++) {
							Record record = records.get(i);
							if (errorMessages.get(record.getAttribute("idEmail")) != null) {
								recordsToSelect[rec++] = list.getRecordIndex(record);
							}
						}
					}
					doSearchAndSelectRecords(recordsToSelect);
				} else
					reloadListAndSetCurrentRecord(records.get(0));

				Layout.hideWaitPopup();

				if (errorMsg != null) {
					Layout.addMessage(new MessageBean(errorMsg, "", MessageType.ERROR));
				} else if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
					Layout.addMessage(new MessageBean(I18NUtil.getMessages().posta_elettronica_archiviazione_successo(), "", MessageType.INFO));
				}
			}
		});
	}

	public void annullaArchiviazioneEmail(final Record record, final boolean isMassiva) {

		record.setAttribute("flgInteresseCessato", "0");
		GWTRestDataSource lGwtRestDataSource = new GWTRestDataSource("AnnullaArchiviazioneEmailDataSource");
		lGwtRestDataSource.addData(record, new DSCallback() {

			@Override
			public void execute(DSResponse response, Object rawData, DSRequest request) {

				String errorMsg = annullaArchiviazione(response, record, "idEmail", "id",
						I18NUtil.getMessages().posta_elettronica_annulla_archiviazione_errore_totale(),
						I18NUtil.getMessages().posta_elettronica_annulla_archiviazione_errore_parziale(), isMassiva);

				RecordList records = record.getAttributeAsRecordList("listaRecord");
				if (isMassiva) {
					int[] recordsToSelect = null;
					Map errorMessages = response.getData()[0].getAttributeAsMap("errorMessages");
					if (errorMessages != null && errorMessages.size() > 0) {

						recordsToSelect = new int[errorMessages.size()];
						int rec = 0;
						for (int i = 0; i < records.getLength(); i++) {
							Record record = records.get(i);
							if (errorMessages.get(record.getAttribute("idEmail")) != null) {
								recordsToSelect[rec++] = list.getRecordIndex(record);
							}
						}
					}
					doSearchAndSelectRecords(recordsToSelect);
					Layout.hideWaitPopup();
				}

				reloadListAndSetCurrentRecord(records.get(0));
				Layout.hideWaitPopup();

				if (errorMsg != null) {
					Layout.addMessage(new MessageBean(errorMsg, "", MessageType.ERROR));
				} else if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
					Layout.addMessage(new MessageBean(I18NUtil.getMessages().posta_elettronica_annulla_archiviazione_successo(), "", MessageType.INFO));
				}
			}
		});
	}

	private String archiviaEmail(DSResponse response, Record record, String pkField, String nameField, String completeErrorMessage, String partialErrorMessage,
			boolean isMassiva) {

		String errorMsg = null;
		if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
			RecordList listaRecord = record.getAttributeAsRecordList("listaRecord");
			Record data = response.getData()[0];
			Map errorMessages = data.getAttributeAsMap("errorMessages");
			if (errorMessages != null && errorMessages.size() > 0) {
				if (isMassiva) {
					RecordList listaErrori = new RecordList();
					Record recordErrore = null;
					if (listaRecord.getLength() > errorMessages.size()) {
						errorMsg = partialErrorMessage != null ? partialErrorMessage : "";
					} else {
						errorMsg = completeErrorMessage != null ? completeErrorMessage : "";
					}
					for (int i = 0; i < listaRecord.getLength(); i++) {
						Record recordTmp = listaRecord.get(i);
						if (errorMessages.get(recordTmp.getAttribute(pkField)) != null) {

							recordErrore = new Record();
							recordErrore.setAttribute("idError", recordTmp.getAttribute(nameField));
							recordErrore.setAttribute("descrizione", errorMessages.get(recordTmp.getAttribute(pkField)));

							listaErrori.add(recordErrore);
						}
					}
					ErroreMassivoPopup errorePopup = new ErroreMassivoPopup(nomeEntita, "N* email", listaErrori, listaRecord.getLength(), LARG_POPUP_ERR_MASS,
							ALT_POPUP_ERR_MASS);
					errorePopup.show();
				} else {
					errorMsg = I18NUtil.getMessages().posta_elettronica_archiviazione_errore();
					String keyIdEmail = data.getAttributeAsRecordList("listaRecord").get(0).getAttributeAsString("idEmail");

					errorMsg += "<br/>" + errorMessages.get(keyIdEmail);
				}
			}
		}
		return errorMsg;
	}

	private String annullaArchiviazione(DSResponse response, Record record, String pkField, final String nameField, String completeErrorMessage,
			String partialErrorMessage, boolean isMassiva) {

		String errorMsg = null;
		if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
			Record data = response.getData()[0];
			RecordList listaRecord = record.getAttributeAsRecordList("listaRecord");
			Map errorMessages = data.getAttributeAsMap("errorMessages");
			if (errorMessages != null && errorMessages.size() > 0) {
				if (isMassiva) {
					RecordList listaErrori = new RecordList();
					Record recordErrore = null;

					if (listaRecord.getLength() > errorMessages.size()) {
						errorMsg = partialErrorMessage != null ? partialErrorMessage : "";
					} else {
						errorMsg = completeErrorMessage != null ? completeErrorMessage : "";
					}
					for (int i = 0; i < listaRecord.getLength(); i++) {
						Record recordTmp = listaRecord.get(i);
						if (errorMessages.get(recordTmp.getAttribute(pkField)) != null) {
							recordErrore = new Record();
							recordErrore.setAttribute("idError", recordTmp.getAttribute(nameField));
							recordErrore.setAttribute("descrizione", errorMessages.get(recordTmp.getAttribute(pkField)));

							listaErrori.add(recordErrore);
						}
					}
					ErroreMassivoPopup errorePopup = new ErroreMassivoPopup(nomeEntita, "N* email", listaErrori, listaRecord.getLength(), LARG_POPUP_ERR_MASS,
							ALT_POPUP_ERR_MASS);
					errorePopup.show();
				} else {
					errorMsg = I18NUtil.getMessages().posta_elettronica_annulla_archiviazione_errore();
					String keyIdEmail = data.getAttributeAsRecordList("listaRecord").get(0).getAttributeAsString("idEmail");
					String motivo = "<br/>" + errorMessages.get(keyIdEmail);
					if (motivo != null && !motivo.contains("null")) {
						errorMsg += motivo;
					}
				}
			}
		}
		return errorMsg;
	}

	public void massiveOperationCallback(DSResponse response, RecordList lista, String pkField, String nameField, String successMessage,
			String completeErrorMessage, String partialErrorMessage, DSCallback callback) {
		if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
			Record data = response.getData()[0];
			Map errorMessages = data.getAttributeAsMap("errorMessages");
			String errorMsg = null;
			int[] recordsToSelect = null;
			RecordList listaErrori = new RecordList();
			if (errorMessages != null && errorMessages.size() > 0) {
				recordsToSelect = new int[errorMessages.size()];
				if (lista.getLength() > errorMessages.size()) {
					errorMsg = partialErrorMessage != null ? partialErrorMessage : "";
				} else {
					errorMsg = completeErrorMessage != null ? completeErrorMessage : "";
				}
				int rec = 0;
				for (int i = 0; i < lista.getLength(); i++) {
					Record record = lista.get(i);
					if (errorMessages.get(record.getAttribute(pkField)) != null) {
						Record recordErrore = new Record();
						recordsToSelect[rec++] = list.getRecordIndex(record);
						errorMsg += "<br/>" + record.getAttribute(nameField) + ": " + errorMessages.get(record.getAttribute(pkField));
						recordErrore.setAttribute("idError", record.getAttribute(nameField));
						recordErrore.setAttribute("descrizione", errorMessages.get(record.getAttribute(pkField)));
						listaErrori.add(recordErrore);
					}
				}
			}
			doSearchAndSelectRecords(recordsToSelect);
			Layout.hideWaitPopup();
			if(listaErrori != null && listaErrori.getLength() > 0) {
				ErroreMassivoPopup errorePopup = new ErroreMassivoPopup(nomeEntita, "Estremi", listaErrori, lista.getLength(), 600, 300);
				errorePopup.show();
			} else if (errorMsg != null) {
				Layout.addMessage(new MessageBean(errorMsg, "", MessageType.ERROR));
			} else if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
				Layout.addMessage(new MessageBean(successMessage, "", MessageType.INFO));
				if (callback != null) {
					callback.execute(new DSResponse(), null, new DSRequest());
				}
			}
		} else {
			Layout.hideWaitPopup();
		}
	}

	public void massiveOperationEliminazioneCallback(DSResponse response, RecordList lista, String pkField, String nameField, String successMessage,
			String completeErrorMessage, String partialErrorMessage, DSCallback callback) {
		if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
			Record data = response.getData()[0];
			Map errorMessages = data.getAttributeAsMap("errorMessages");
			String errorMsg = null;
			int[] recordsToSelect = null;
			if (errorMessages != null && errorMessages.size() > 0) {
				recordsToSelect = new int[errorMessages.size()];
				if (lista.getLength() > errorMessages.size()) {
					errorMsg = partialErrorMessage != null ? partialErrorMessage : "";
				} else {
					errorMsg = completeErrorMessage != null ? completeErrorMessage : "";
				}
				int rec = 0;
				for (int i = 0; i < lista.getLength(); i++) {
					Record record = lista.get(i);
					if (errorMessages.get(record.getAttribute(pkField)) != null) {
						recordsToSelect[rec++] = list.getRecordIndex(record);
						try {
							errorMsg += "<br/>Mail inviata il "
									+ record.getAttribute("tsInvioClient") + " da " + (record.getAttribute("flgIo").equals("O")
											? record.getAttribute("casellaRicezione") : record.getAttribute("accountMittente"))
									+ ": " + errorMessages.get(record.getAttribute(pkField));
						} catch (Exception e) {
							errorMsg += "<br/>" + record.getAttribute(nameField) + ": " + errorMessages.get(record.getAttribute(pkField));
						}
					}
				}
			}
			doSearchAndSelectRecords(recordsToSelect);
			Layout.hideWaitPopup();
			if (errorMsg != null) {
				Layout.addMessage(new MessageBean(errorMsg, "", MessageType.ERROR));
			} else if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
				Layout.addMessage(new MessageBean(successMessage, "", MessageType.INFO));
				if (callback != null) {
					callback.execute(new DSResponse(), null, new DSRequest());
				}
			}
		}
	}

	protected void manageInoltraClick(Record pRecord, boolean allegaMailOrig) {
		InoltroMailWindow lInoltroEmailWindow = new InoltroMailWindow(allegaMailOrig ? "inoltroAllegaMailOrig" : "inoltro", pRecord, new DSCallback() {

			@Override
			public void execute(DSResponse response, Object rawData, DSRequest request) {

			}
		});
	}

	/**
	 * Per il cruscotto di gestione delle email non voglio che l'utente cliccando su tutta la riga visualizzi il dettaglio, ma solo sull'identificativo
	 */
	@Override
	public boolean getDefaultDetailAuto() {
		return false;
	}

	private void archiviazioneMultipla(final RecordList listaEmail) {

		Record record = new Record();
		record.setAttribute("listaRecord", listaEmail);
		record.setAttribute("classifica", "standard.arrivo");

		DatiOperazioneRichiestaWindow operazioneRichiestaWindow = new DatiOperazioneRichiestaWindow(instance, listaEmail, true, true);
		operazioneRichiestaWindow.show();
	}

	public void lockMailCallback(DSResponse response, RecordList lista, String pkField, String nameField, String successMessage, String completeErrorMessage,
			String partialErrorMessage, DSCallback callback) {
		if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
			Record data = response.getData()[0];
			Map errorMessages = data.getAttributeAsMap("errorMessages");
			String errorMsg = null;
			int[] recordsToSelect = null;
			if (errorMessages != null && errorMessages.size() > 0) {
				recordsToSelect = new int[errorMessages.size()];
				if (lista.getLength() > errorMessages.size()) {
					errorMsg = partialErrorMessage != null ? partialErrorMessage : "";
				} else {
					errorMsg = completeErrorMessage != null ? completeErrorMessage : "";
				}
				int rec = 0;

				for (int i = 0; i < lista.getLength(); i++) {
					Record record = lista.get(i);
					if (errorMessages.get(record.getAttribute(pkField)) != null) {
						recordsToSelect[rec++] = list.getRecordIndex(record);
						errorMsg += "<br/>" + record.getAttribute(nameField) + ": " + errorMessages.get(record.getAttribute(pkField));
					}
				}
			}
			doSearchAndSelectRecords(recordsToSelect);
			Layout.hideWaitPopup();
			if (errorMsg != null) {
				Layout.addMessage(new MessageBean(errorMsg, "", MessageType.ERROR));
			} else if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
				Layout.addMessage(new MessageBean(successMessage, "", MessageType.INFO));
				if (callback != null) {
					callback.execute(new DSResponse(), null, new DSRequest());
				}
			}
		}
	}

	public void buildOperazioneSuEmail(final RecordList listaMail, String idUserLockFor, String motivi, final boolean isMassiva, final String tipoOperazione) {
		Record record = new Record();
		record.setAttribute("listaRecord", listaMail);
		record.setAttribute("iduserlockfor", idUserLockFor);
		record.setAttribute("motivi", motivi);
		GWTRestDataSource lGwtRestDataSource = new GWTRestDataSource("LockEmailDataSource");
		lGwtRestDataSource.addData(record, new DSCallback() {

			@Override
			public void execute(DSResponse response, Object rawData, DSRequest request) {
				String errorMsg = "";
				if (TipoOperazioneMail.PRESA_IN_CARICO.getValue().equals(tipoOperazione)) {
					errorMsg = tipoOperazioneErrorMessage(response, listaMail, "idEmail", "id",
							I18NUtil.getMessages().posta_elettronica_presa_in_carico_errore_totale(),
							I18NUtil.getMessages().posta_elettronica_presa_in_carico_errore_parziale(), isMassiva,
							TipoOperazioneMail.PRESA_IN_CARICO.getValue());
				} else if (TipoOperazioneMail.MESSA_IN_CARICO.getValue().equals(tipoOperazione)) {
					errorMsg = tipoOperazioneErrorMessage(response, listaMail, "idEmail", "id",
							I18NUtil.getMessages().posta_elettronica_messa_in_carico_errore_parziale(),
							I18NUtil.getMessages().posta_elettronica_messa_in_carico_errore_totale(), isMassiva, TipoOperazioneMail.MESSA_IN_CARICO.getValue());
				} else if (TipoOperazioneMail.MANDA_IN_APPROVAZIONE.getValue().equals(tipoOperazione)) {
					errorMsg = tipoOperazioneErrorMessage(response, listaMail, "idEmail", "id",
							I18NUtil.getMessages().posta_elettronica_manda_in_approvazione_errore_parziale(),
							I18NUtil.getMessages().posta_elettronica_manda_in_approvazione_errore_totale(), isMassiva,
							TipoOperazioneMail.MANDA_IN_APPROVAZIONE.getValue());
				}
				if (isMassiva) {
					int[] recordsToSelect = null;
					Map errorMessages = response.getData()[0].getAttributeAsMap("errorMessages");
					if (errorMessages != null && errorMessages.size() > 0) {
						recordsToSelect = new int[errorMessages.size()];
						int rec = 0;
						for (int i = 0; i < listaMail.getLength(); i++) {
							Record record = listaMail.get(i);
							if (errorMessages.get(record.getAttribute("idEmail")) != null) {
								recordsToSelect[rec++] = list.getRecordIndex(record);
							}
						}
					}
					doSearchAndSelectRecords(recordsToSelect);
					Layout.hideWaitPopup();
				}
				reloadListAndSetCurrentRecord(listaMail.get(0));
				Layout.hideWaitPopup();
				if (errorMsg != null) {
					Layout.addMessage(new MessageBean(errorMsg, "", MessageType.ERROR));
				} else if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
					if (TipoOperazioneMail.PRESA_IN_CARICO.getValue().equals(tipoOperazione)) {
						Layout.addMessage(new MessageBean(I18NUtil.getMessages().posta_elettronica_presa_in_carico_successo(), "", MessageType.INFO));
					} else if (TipoOperazioneMail.MESSA_IN_CARICO.getValue().equals(tipoOperazione)) {
						Layout.addMessage(new MessageBean(I18NUtil.getMessages().posta_elettronica_messa_in_carico_successo(), "", MessageType.INFO));
					} else if (TipoOperazioneMail.MANDA_IN_APPROVAZIONE.getValue().equals(tipoOperazione)) {
						Layout.addMessage(new MessageBean(I18NUtil.getMessages().posta_elettronica_manda_in_approvazione_successo(), "", MessageType.INFO));
					}
				}
			}
		});
	}

	public void rilascio(final RecordList listaMail, String usreLockFor, String motivo, final boolean isMassiva) {
		Record record = new Record();
		record.setAttribute("listaRecord", listaMail);
		record.setAttribute("motivi", motivo);
		GWTRestDataSource lGwtRestDataSource = new GWTRestDataSource("UnlockEmailDataSource");
		lGwtRestDataSource.addData(record, new DSCallback() {

			@Override
			public void execute(DSResponse response, Object rawData, DSRequest request) {
				String errorMsg = rilascioErrorMessage(response, listaMail, "idEmail", "id", I18NUtil.getMessages().posta_elettronica_rilascio_errore_totale(),
						I18NUtil.getMessages().posta_elettronica_rilascio_errore_parziale(), isMassiva);
				if (isMassiva) {
					int[] recordsToSelect = null;
					Map errorMessages = response.getData()[0].getAttributeAsMap("errorMessages");
					if (errorMessages != null && errorMessages.size() > 0) {
						recordsToSelect = new int[errorMessages.size()];
						int rec = 0;
						for (int i = 0; i < listaMail.getLength(); i++) {
							Record record = listaMail.get(i);
							if (errorMessages.get(record.getAttribute("idEmail")) != null) {
								recordsToSelect[rec++] = list.getRecordIndex(record);
							}
						}
					}
					doSearchAndSelectRecords(recordsToSelect);
					Layout.hideWaitPopup();
				}
				reloadListAndSetCurrentRecord(listaMail.get(0));
				Layout.hideWaitPopup();
				if (errorMsg != null) {
					Layout.addMessage(new MessageBean(errorMsg, "", MessageType.ERROR));
				} else if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
					Layout.addMessage(new MessageBean(I18NUtil.getMessages().posta_elettronica_rilascio_successo(), "", MessageType.INFO));
				}
			}
		});
	}

	private String tipoOperazioneErrorMessage(DSResponse response, RecordList listaRecord, String pkField, String nameField, String completeErrorMessage,
			String partialErrorMessage, boolean isMassiva, String tipoOperazione) {
		String errorMsg = null;
		if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
			Record data = response.getData()[0];
			Map errorMessages = data.getAttributeAsMap("errorMessages");
			Boolean storeInError = data.getAttributeAsBoolean("storeInError");
			if ((errorMessages != null) && (errorMessages.size() > 0)) {
				if (isMassiva) {
					RecordList listaErrori = new RecordList();
					Record recordErrore = null;
					if ((listaRecord.getLength() > errorMessages.size()) && (storeInError == false)) {
						errorMsg = partialErrorMessage != null ? partialErrorMessage : "";
					} else {
						errorMsg = completeErrorMessage != null ? completeErrorMessage : "";
					}
					if (!storeInError) {
						for (int i = 0; i < listaRecord.getLength(); i++) {
							Record record = listaRecord.get(i);
							if (errorMessages.get(record.getAttribute(pkField)) != null) {
								recordErrore = new Record();
								recordErrore.setAttribute("idError", record.getAttribute(nameField));
								recordErrore.setAttribute("descrizione", errorMessages.get(record.getAttribute(pkField)));
								listaErrori.add(recordErrore);
							}
						}
					}
					if (listaErrori.getLength() > 0) {
						ErroreMassivoPopup errorePopup = new ErroreMassivoPopup(nomeEntita, "N* email", listaErrori, listaRecord.getLength(),
								LARG_POPUP_ERR_MASS, ALT_POPUP_ERR_MASS);
						errorePopup.show();
					}
				} else {
					if (TipoOperazioneMail.PRESA_IN_CARICO.getValue().equals(tipoOperazione)) {
						errorMsg = I18NUtil.getMessages().posta_elettronica_presa_in_carico_errore();
					} else if (TipoOperazioneMail.MESSA_IN_CARICO.getValue().equals(tipoOperazione)) {
						errorMsg = I18NUtil.getMessages().posta_elettronica_messa_in_carico_errore();
					} else if (TipoOperazioneMail.MANDA_IN_APPROVAZIONE.getValue().equals(tipoOperazione)) {
						errorMsg = I18NUtil.getMessages().posta_elettronica_manda_in_approvazione_errore();
					}
					errorMsg += "<br/>" + errorMessages.get(listaRecord.get(0).getAttribute(pkField));
				}
			}
		}
		return errorMsg;
	}

	private String rilascioErrorMessage(DSResponse response, RecordList listaRecord, String pkField, String nameField, String completeErrorMessage,
			String partialErrorMessage, boolean isMassiva) {
		String errorMsg = null;
		if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
			Record data = response.getData()[0];
			Map errorMessages = data.getAttributeAsMap("errorMessages");
			Boolean storeInError = data.getAttributeAsBoolean("storeInError");
			if ((errorMessages != null) && (errorMessages.size() > 0)) {
				if (isMassiva) {
					RecordList listaErrori = new RecordList();
					Record recordErrore = null;
					if ((listaRecord.getLength() > errorMessages.size()) && (storeInError == false)) {
						errorMsg = partialErrorMessage != null ? partialErrorMessage : "";
					} else {
						errorMsg = completeErrorMessage != null ? completeErrorMessage : "";
					}
					if (!storeInError) {
						for (int i = 0; i < listaRecord.getLength(); i++) {
							Record record = listaRecord.get(i);
							if (errorMessages.get(record.getAttribute(pkField)) != null) {
								recordErrore = new Record();
								recordErrore.setAttribute("idError", record.getAttribute(nameField));
								recordErrore.setAttribute("descrizione", errorMessages.get(record.getAttribute(pkField)));
								listaErrori.add(recordErrore);
							}
						}
					}
					if (listaErrori.getLength() > 0) {
						ErroreMassivoPopup errorePopup = new ErroreMassivoPopup(nomeEntita, "N* email", listaErrori, listaRecord.getLength(),
								LARG_POPUP_ERR_MASS, ALT_POPUP_ERR_MASS);
						errorePopup.show();
					}
					ErroreMassivoPopup errorePopup = new ErroreMassivoPopup(nomeEntita, "N* email", listaErrori, listaRecord.getLength(), LARG_POPUP_ERR_MASS,
							ALT_POPUP_ERR_MASS);
					errorePopup.show();
				} else {
					errorMsg = I18NUtil.getMessages().posta_elettronica_rilascio_errore();
					errorMsg += "<br/>" + errorMessages.get(listaRecord.get(0).getAttribute(pkField));
				}
			}
		}
		return errorMsg;
	}

	private void assegnazioneEmail(final RecordList listaEmail, final RecordList listaDestinatariPreferiti) {

		Record recordListaEmail = new Record();
		
		// Solo se il parametro RESTR_ASS_EMAIL_SOLO_SMISTATORI e' valorizzato passo la concatenazione delle prime 100 email 
		if (AurigaLayout.getParametroDB("RESTR_ASS_EMAIL_SOLO_SMISTATORI") != null && !AurigaLayout.getParametroDB("RESTR_ASS_EMAIL_SOLO_SMISTATORI").equalsIgnoreCase("")){	
			if (listaEmail != null && listaEmail.getLength() > 0) {
		      String listaIdEmail = "";
		      for (int i = 0; i < 100; i++) {
		        Record record = listaEmail.get(i);
		        if(record != null && record.getAttribute("idEmail") != null &&
		        		!"".equalsIgnoreCase(record.getAttribute("idEmail"))) {
			        String idEmail = record.getAttribute("idEmail");
			        if (i == 0) {
			          listaIdEmail = idEmail;
			        } else {
			          listaIdEmail = String.valueOf(listaIdEmail) + ";" + idEmail;
			        } 
		        }
		      } 
		      if (!listaIdEmail.equalsIgnoreCase(""))
		        recordListaEmail.setAttribute("idEmail", listaIdEmail); 
			} 
		}
		
		final AssegnazioneEmailPopup assegnazioneEmailPopup = new AssegnazioneEmailPopup(recordListaEmail, listaDestinatariPreferiti) {

			@Override
			public void onClickOkButton(Record record, final DSCallback callback) {
				
				record.setAttribute("listaRecord", listaEmail);
				
				Layout.showWaitPopup(I18NUtil.getMessages().posta_elettronica_assegnazione_in_corso());
				GWTRestDataSource lGwtRestDataSource = new GWTRestDataSource("AssegnazioneEmailDataSource");
				lGwtRestDataSource.addParam("isMassivo", "1");
				try {
					lGwtRestDataSource.addData(record, new DSCallback() {

						@Override
						public void execute(DSResponse response, Object rawData, DSRequest request) {
							massiveOperationCallback(response, listaEmail, "idEmail", "oggetto",
									I18NUtil.getMessages().posta_elettronica_assegnazione_successo(),
									I18NUtil.getMessages().posta_elettronica_assegnazione_errore_totale(),
									I18NUtil.getMessages().posta_elettronica_assegnazione_errore_parziale(), callback);
						}
					});
				} catch (Exception e) {
					Layout.hideWaitPopup();
				}
			}
		};
		assegnazioneEmailPopup.show();
	}

	private void annullamentoAssegnazioneEmail(final RecordList listaEmail) {

		Record record = new Record();
		record.setAttribute("listaAnnullamenti", listaEmail);
		
		final MotiviOperazionePopup motiviPopup = new MotiviOperazionePopup(record, new ServiceCallback<Record>() {
			
			@Override
			public void execute(Record object) {
				manageAnnullamentoAssegnazioneUOCompetente(object);
				
			}
		});
		
		motiviPopup.show();

	}

	private void inoltraEmail(final RecordList listaEmail) {
		Menu menu = new Menu();
		MenuItem inoltraMenuItem = new MenuItem(I18NUtil.getMessages().posta_elettronica_menu_item_inoltra());
		MenuItem inoltraMenuItemSbustato = new MenuItem(I18NUtil.getMessages().posta_elettronica_menu_item_inoltra_sbustato());

		inoltraMenuItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {

			@Override
			public void onClick(MenuItemClickEvent event) {
				final Record record = new Record();
				record.setAttribute("listaRecord", listaEmail);
				final GWTRestService<Record, Record> lGwtRestServiceInvioNotifica = new GWTRestService<Record, Record>("AurigaInvioMailDatasource");
				lGwtRestServiceInvioNotifica.call(record, new ServiceCallback<Record>() {

					@Override
					public void execute(Record object) {
						new InoltroMailWindow("inoltro", record, instance, new DSCallback() {

							@Override
							public void execute(DSResponse response, Object rawData, DSRequest request) {
								doSearch();
							}
						});
					}
				});
			}
		});

		inoltraMenuItemSbustato.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {

			@Override
			public void onClick(MenuItemClickEvent event) {
				final RecordList listaEmail = new RecordList();

				ListGridRecord[] lEmail = list.getSelectedRecords();

				for (int i = 0; i < lEmail.length; i++) {
					listaEmail.add(list.getSelectedRecords()[i]);
				}

				final Record record = new Record();
				record.setAttribute("listaRecord", listaEmail);
				record.setAttribute("allegaEmlSbustato", "true");

				final GWTRestService<Record, Record> lGwtRestServiceInvioNotifica = new GWTRestService<Record, Record>("AurigaInvioMailDatasource");
				lGwtRestServiceInvioNotifica.call(record, new ServiceCallback<Record>() {

					@Override
					public void execute(Record object) {
						InoltroMailWindow lInoltroEmailWindow = new InoltroMailWindow("inoltro", record, instance, new DSCallback() {

							@Override
							public void execute(DSResponse response, Object rawData, DSRequest request) {
								doSearch();
							}
						});
					}
				});
			}
		});
		menu.setItems(inoltraMenuItem, inoltraMenuItemSbustato);
		menu.showContextMenu();
	}

	// Controllo se la singola mail è stata presa gia in carico
	private void checkLockEmail(Record record, final ServiceCallback<Record> callback) {

		final GWTRestDataSource lockEmailDataSource = new GWTRestDataSource("LockEmailDataSource");
		lockEmailDataSource.executecustom("checkLockEmail", record, new DSCallback() {

			@Override
			public void execute(DSResponse response, Object rawData, DSRequest request) {

				Record rec = new Record();
				rec.setAttribute("esito", response.getData()[0]);
				callback.execute(rec);
			}
		});
	}

	private void lock(final RecordList listaMail, final ServiceCallback<Record> callback) {

		Record record = new Record();
		record.setAttribute("listaRecord", listaMail);

		GWTRestDataSource lGwtRestDataSource = new GWTRestDataSource("LockEmailDataSource");
		lGwtRestDataSource.addData(record, new DSCallback() {

			@Override
			public void execute(DSResponse response, Object rawData, DSRequest request) {
				String errorMsg = null;

				RecordList listalock = new RecordList();

				if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
					Record data = response.getData()[0];
					Map errorMessages = data.getAttributeAsMap("errorMessages");

					for (int i = 0; i < listaMail.getLength(); i++) {
						Record reco = listaMail.get(i);

						if (errorMessages.get(reco.getAttribute("idEmail")) != null) {
							reco.setAttribute("esitoLock", false);
							reco.setAttribute("idEmail", reco.getAttribute("idEmail"));
						} else {
							reco.setAttribute("esitoLock", true);
							reco.setAttribute("idEmail", reco.getAttribute("idEmail"));
							listalock.add(reco);
						}

					}
				}
				Record record = new Record();
				record.setAttribute("listaLock", listalock);

				callback.execute(record);
			}
		});
	}

	public void unlock(final RecordList listaMail, final boolean isMassiva, final ServiceCallback<Record> callback) {
		Record record = new Record();
		record.setAttribute("listaRecord", listaMail);

		GWTRestDataSource lGwtRestDataSource = new GWTRestDataSource("UnlockEmailDataSource");
		lGwtRestDataSource.addData(record, new DSCallback() {

			@Override
			public void execute(DSResponse response, Object rawData, DSRequest request) {
				String errorMsg = null;

				if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
					Record data = response.getData()[0];
					Map errorMessages = data.getAttributeAsMap("errorMessages");

					String key = null;

					if (listaMail.get(0) != null)
						key = listaMail.get(0).getAttribute("idEmail");

					if (errorMessages != null && errorMessages.size() > 0) {
						errorMsg = "Fallito rilascio della mail al termine dell'operazione: " + (key != null ? errorMessages.get(key) : "");
					}
				}
				Layout.hideWaitPopup();
				if (errorMsg != null) {
					Layout.addMessage(new MessageBean(errorMsg, "", MessageType.WARNING));
				} else if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
					Layout.addMessage(new MessageBean("Rilascio effettuato con successo", "", MessageType.INFO));

				}

				Record record = new Record();
				callback.execute(record);
			}
		});
	}

	private void manageAnnullamentoAssegnazioneUOCompetente(Record object) {
		Map errorMessages = object.getAttributeAsMap("errorMessages");
		if (errorMessages != null && errorMessages.size() > 0) {
			Layout.hideWaitPopup();
			Layout.addMessage(new MessageBean(I18NUtil.getMessages().posta_elettronica_annulla_uo_competente_error(), "", MessageType.ERROR));
		} else {
			Layout.hideWaitPopup();
			doSearch();
			Layout.addMessage(new MessageBean(I18NUtil.getMessages().posta_elettronica_annulla_uo_competente_success(), "", MessageType.INFO));
		}
	}

	@Override
	protected Record[] extractRecords(String[] fields) {
		// Se sono in overflow i dati verranno recuperati con il metodo asincrono,
		// altrimenti utilizzo quelli nella lista a GUI
		if (overflow){
			return new Record[0];
		}else{
			return super.extractRecords(fields);
		}
	}
}