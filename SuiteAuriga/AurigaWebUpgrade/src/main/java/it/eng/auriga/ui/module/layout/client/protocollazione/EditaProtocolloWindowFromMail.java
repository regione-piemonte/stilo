/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.Map;

import com.smartgwt.client.data.DSCallback;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.data.RecordList;
import com.smartgwt.client.util.BooleanCallback;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.client.widgets.toolbar.ToolStrip;

import it.eng.auriga.ui.module.layout.client.AurigaLayout;
import it.eng.auriga.ui.module.layout.client.i18n.I18NUtil;
import it.eng.auriga.ui.module.layout.client.postaElettronica.DatiOperazioneRichiestaWindow;
import it.eng.auriga.ui.module.layout.client.postaElettronica.DettaglioPostaElettronica;
import it.eng.auriga.ui.module.layout.client.postaElettronica.PostaElettronicaLayout;
import it.eng.auriga.ui.module.layout.client.postaElettronica.PostaElettronicaList;
import it.eng.utility.ui.module.core.client.callback.ServiceCallback;
import it.eng.utility.ui.module.core.client.datasource.GWTRestDataSource;
import it.eng.utility.ui.module.core.shared.message.MessageBean;
import it.eng.utility.ui.module.core.shared.message.MessageType;
import it.eng.utility.ui.module.layout.client.Layout;
import it.eng.utility.ui.module.layout.client.common.CustomLayout;
import it.eng.utility.ui.module.layout.client.common.CustomList;
import it.eng.utility.ui.module.layout.client.common.DetailToolStripButton;
import it.eng.utility.ui.module.layout.client.portal.ModalWindow;

public abstract class EditaProtocolloWindowFromMail extends ModalWindow {

	protected EditaProtocolloWindowFromMail _window;

	protected ProtocollazioneDetail portletLayout;
	protected Record record;

	private ToolStrip detailToolStrip;
	private ToolStrip detailToolStripRbuild;
	
	private Record recordMail;
	private DettaglioPostaElettronica instanceDettaglioPostaElettronica;
	private CustomLayout externalLayout;

	
	public EditaProtocolloWindowFromMail(String pNomeEntita, Record pRecord, String pFlgTipoProv) {
		this(pNomeEntita, pRecord, pFlgTipoProv, false, null, null, null);
	}

	public EditaProtocolloWindowFromMail(String pNomeEntita, Record pRecord, String pFlgTipoProv, boolean isThereIdUdMail, Record recordMail, DettaglioPostaElettronica instanceDettaglioPostaElettronica, CustomLayout externalLayout) {

		super(pNomeEntita, false);
		this.recordMail = recordMail;
		this.instanceDettaglioPostaElettronica = instanceDettaglioPostaElettronica;
		this.externalLayout = externalLayout;
		_window = this;
		
		setIcon("menu/protocollazione_entrata.png");

		this.record = pRecord;
		
		portletLayout = ProtocollazioneUtil.buildProtocollazioneDetailEntrata(record, null);
		portletLayout.setEditaProtocolloWindowFromMail(_window);
		portletLayout.setDettaglioPostaElettronica(instanceDettaglioPostaElettronica);
		
		if (isThereIdUdMail) {
			portletLayout.caricaDettaglio(null, record);
			portletLayout.modificaDatiMode();
			setTitle("Modifica documento "+ record.getAttribute("segnatura"));
		} else {
			if(record.getAttributeAsBoolean("flgPEC") != null && record.getAttributeAsBoolean("flgPEC")) {
				record.setAttribute("mezzoTrasmissione", "PEC");	
			} else {
				record.setAttribute("mezzoTrasmissione", "PEO");	
			}
			record.setAttribute("supportoOriginale", "digitale");
			portletLayout.nuovoDettaglioMail(record);
			portletLayout.protocollaMailMode();
			if (record != null && record.getAttributeAsBoolean("protocolloAccessoAttiSueDaEmail")) {
				setTitle("Protocollazione accesso atti SUE");
			} else {
				setTitle(I18NUtil.getMessages().edita_protocollo_window_from_mail_title());
			}
			portletLayout.manageChangedFlgAssegnaAlMittDest(new ServiceCallback<Record>() {

				@Override
				public void execute(Record object) {
					portletLayout.validate();
				}
			});		
		}
		portletLayout.setHeight100();
		portletLayout.setWidth100();

		if (AurigaLayout.getIsAttivaAccessibilita()) {
			VLayout main_layout = new VLayout ();
			main_layout = (VLayout) portletLayout.getMember("main_layout");
			
			detailToolStrip = (ToolStrip)main_layout.getMember("bottoni");
			detailToolStrip.markForDestroy();
			detailToolStrip.markForRedraw();
			addBackButton();
			
			portletLayout.setHeight(450);
			Canvas[] members = detailToolStrip.getMembers();
			for(Canvas singleButton : members) {
				singleButton.markForRedraw();
				detailToolStripRbuild.addMember(singleButton);
			}
			main_layout.addMember(detailToolStripRbuild);
			portletLayout.addMember(main_layout);		
		}

		setBody(portletLayout);
	}


	@Override
	public void manageOnCloseClick() {

		GWTRestDataSource lGWTRestDataSource = new GWTRestDataSource("AurigaProtocollaPostaElettronicaDataSource");
		lGWTRestDataSource.executecustom("sbloccaMail", record, new DSCallback() {

			@Override
			public void execute(DSResponse response, Object rawData, DSRequest request) {
				
				if (record.getAttribute("idUd") != null && !record.getAttribute("idUd").trim().equals("") && portletLayout.isSaved()) {
					SC.ask(I18NUtil.getMessages().postaElettronica__list_askChiusuraCopiaMail(), new BooleanCallback() {
						
						@Override
						public void execute(Boolean value) {
							if (value) {
								actionArchiviaMail(recordMail);
							} else {
								_window.markForDestroy();
								manageAfterCloseWindow();
							}
						}
					});
				} else {
					_window.markForDestroy();
					manageAfterCloseWindow();
				}
			}
		});
	}
	
	private void addBackButton() {
		detailToolStripRbuild = new ToolStrip();
		detailToolStripRbuild.setName("bottoni");
		DetailToolStripButton closeModalWindow = new DetailToolStripButton(I18NUtil.getMessages().backButton_prompt(), "buttons/back.png");
		closeModalWindow.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				GWTRestDataSource lGWTRestDataSource = new GWTRestDataSource("AurigaProtocollaPostaElettronicaDataSource");
				lGWTRestDataSource.executecustom("sbloccaMail", record, new DSCallback() {

					@Override
					public void execute(DSResponse response, Object rawData, DSRequest request) {

						if (record.getAttribute("idUd") != null && !record.getAttribute("idUd").trim().equals("") && portletLayout.isSaved()) {
							SC.ask(I18NUtil.getMessages().postaElettronica__list_askChiusuraCopiaMail(), new BooleanCallback() {
								
								@Override
								public void execute(Boolean value) {
									if (value) {
										actionArchiviaMail(recordMail);
									} else {
										_window.markForDestroy();
										manageAfterCloseWindow();
									}
								}
							});
						} else {
							_window.markForDestroy();
							manageAfterCloseWindow();
						}
					}
				});
			}
		});
		
		detailToolStripRbuild.addButton(closeModalWindow,0);
		detailToolStripRbuild.addFill(); // push all buttons to the right
	}
	
	protected void actionArchiviaMail (final Record pRecordMail) {
		final RecordList records = new RecordList();
		records.add(pRecordMail);

		checkLockEmail(pRecordMail, new ServiceCallback<Record>() {

			@Override
			public void execute(Record object) {

				Record esitoCheck = object.getAttributeAsRecord("esito");

				boolean isLock = esitoCheck.getAttributeAsBoolean("flagPresenzaLock");
				boolean isForzaLock = esitoCheck.getAttributeAsBoolean("flagForzaLock");

				if (isLock) {
					if (isForzaLock) {
						String messaggio = esitoCheck.getAttributeAsString("errorMessage");

						SC.ask(messaggio, new BooleanCallback() {

							@Override
							public void execute(Boolean value) {
								if (value) {

									sbloccaEmail(pRecordMail, new ServiceCallback<Record>() {

										@Override
										public void execute(Record data) {

											// Record esitoSblocco = object.getAttributeAsRecord("esito");
											Map mapErrorMessages = data.getAttributeAsMap("errorMessages");

											if (mapErrorMessages != null && mapErrorMessages.size() > 0) {
												if (data.getAttribute("id") == null || data.getAttribute("id").equalsIgnoreCase("")) {
													String id = mapErrorMessages.keySet().toString().replace("[", "").replace("]", "");
													String value = "Errore: " + mapErrorMessages.get(id).toString();

													Layout.addMessage(new MessageBean(value, "", MessageType.ERROR));
												} else {
													Layout.addMessage(new MessageBean(mapErrorMessages.get(data.getAttribute("id")).toString(), "",
															MessageType.ERROR));
												}
											}
											// mail sbloccata
											else
												archiviaMail(pRecordMail);
										}
									});
								}
							}
						});
					}
					// No force lock
					else {
						Layout.addMessage(new MessageBean(esitoCheck.getAttributeAsString("errorMessage"), "", MessageType.ERROR));
					}
				}
				// Mail noLock
				else
					archiviaMail(pRecordMail);
			}
		});
	}

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
	
	// sblocco singola mail
	private void sbloccaEmail(Record record, final ServiceCallback<Record> callback) {
		final RecordList listaEmail = new RecordList();
		listaEmail.add(record);

		Record lRecord = new Record();
		lRecord.setAttribute("listaRecord", listaEmail);

		GWTRestDataSource lGwtRestDataSource = new GWTRestDataSource("UnlockEmailDataSource");
		lGwtRestDataSource.addData(lRecord, new DSCallback() {

			@Override
			public void execute(DSResponse response, Object rawData, DSRequest request) {

				if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
					Record data = response.getData()[0];
					callback.execute(data);
				}
			}
		});
	}
	
	private void archiviaMail(Record recorMail) {

		final RecordList records = new RecordList();
		records.add(recorMail);
		
		if (instanceDettaglioPostaElettronica == null) {
			DatiOperazioneRichiestaWindow operazioneRichiestaWindow = new DatiOperazioneRichiestaWindow(externalLayout, records, false, true){
				
				@Override
				public void postArchiviaMail () {
					_window.markForDestroy();
					manageAfterCloseWindow();
				}
				
			};
			operazioneRichiestaWindow.show();
		} else {
			DatiOperazioneRichiestaWindow operazioneRichiestaWindow = new DatiOperazioneRichiestaWindow(instanceDettaglioPostaElettronica, records, true) {
				
				@Override
				public void postArchiviaMail () {
					_window.markForDestroy();
					manageAfterCloseWindow();
				}
				
			};
			operazioneRichiestaWindow.show();
		}
	}
	
	public void archiviaMailFromProtocollazioneAccessoAttiSue(final Record recordMail) {

		recordMail.setAttribute("idEmail", recordMail.getAttribute("idEmailArrivo"));
		_window.markForDestroy();
		SC.ask(I18NUtil.getMessages().postaElettronica__list_askChiusuraMailField(), new BooleanCallback() {
			
			@Override
			public void execute(Boolean value) {
				if (value) {
					if (instanceDettaglioPostaElettronica == null && externalLayout instanceof PostaElettronicaLayout) {
						CustomList lista = ((PostaElettronicaLayout) externalLayout).getList();
						if (lista != null && lista instanceof PostaElettronicaList) {
							((PostaElettronicaList) lista).actionArchiviaMail(recordMail);
						}
						manageAfterCloseWindow();
					} else {
						instanceDettaglioPostaElettronica.actionArchiviaMail();
						manageAfterCloseWindow();
					}
				} else {
					manageAfterCloseWindow();
				}
			}
		});
		
//		_window.markForDestroy();
//		manageAfterCloseWindow();
		
	}

	public abstract void manageAfterCloseWindow();

}
