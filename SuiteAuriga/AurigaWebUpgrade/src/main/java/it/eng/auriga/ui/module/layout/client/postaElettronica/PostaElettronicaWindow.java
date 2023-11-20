/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import com.google.gwt.core.client.Scheduler;
import com.google.gwt.core.client.Scheduler.ScheduledCommand;
import com.smartgwt.client.data.DSCallback;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.types.FieldType;
import com.smartgwt.client.types.Side;
import com.smartgwt.client.types.Visibility;
import com.smartgwt.client.widgets.form.ValuesManager;
import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.client.widgets.tab.Tab;
import com.smartgwt.client.widgets.tab.TabSet;
import com.smartgwt.client.widgets.toolbar.ToolStrip;

import it.eng.auriga.ui.module.layout.client.AurigaLayout;
import it.eng.auriga.ui.module.layout.client.i18n.I18NUtil;
import it.eng.auriga.ui.module.layout.client.invioMail.TipologiaMail;
import it.eng.utility.ui.module.core.client.callback.ServiceCallback;
import it.eng.utility.ui.module.core.client.datasource.GWTRestDataSource;
import it.eng.utility.ui.module.layout.client.Layout;
import it.eng.utility.ui.module.layout.client.common.CustomDetail;
import it.eng.utility.ui.module.layout.client.common.CustomLayout;
import it.eng.utility.ui.module.layout.client.common.DetailToolStripButton;
import it.eng.utility.ui.module.layout.client.portal.ModalWindow;

/**
 * 
 * @author cristiano
 *
 */

public abstract class PostaElettronicaWindow extends ModalWindow {

	protected CustomLayout customLayoutToDoSearch;
	protected CustomDetail customDetailToReload;

	protected PostaElettronicaWindow _instance;
	protected ValuesManager vm;

	protected TabSet tabSetGenerale;
	protected Tab tabWindowPrincipale;
//	protected Tab tabWindowIterBozza;
	private String titleTab;
	protected Record recordPrincipale;
	protected DSCallback sendCallback;
	protected String casellaRicezione;
	protected boolean flgSoloAlMitt;
	protected String titleWindow;
	protected String tipoInvio;
	protected String tipoRel;
	protected TipologiaMail tipologiaMailGestioneModelli;

//	protected ItemLavorazioneMailLayout layoutIterBozza;

	protected ToolStrip detailToolStrip;
	protected DetailToolStripButton confermaButton;
	protected DetailToolStripButton salvaBozzaButton;

	public PostaElettronicaWindow(String nomeEntita, Boolean justWindow, String tipoRel, Record recordPrincipale, String casellaRicezione,
			boolean flgSoloAlMitt, String title, String tipoInvio, String titleWindow, CustomLayout customLayoutToDoSearch, final DSCallback sendCallback) {
		this(nomeEntita, justWindow, tipoRel, recordPrincipale, casellaRicezione, flgSoloAlMitt, title, tipoInvio, titleWindow, sendCallback);

		this.customLayoutToDoSearch = customLayoutToDoSearch;
	}

	public PostaElettronicaWindow(String nomeEntita, Boolean justWindow, String tipoRel, Record recordPrincipale, String casellaRicezione,
			boolean flgSoloAlMitt, String title, String tipoInvio, String titleWindow, CustomDetail customDetailToReload, final DSCallback sendCallback) {
		this(nomeEntita, justWindow, tipoRel, recordPrincipale, casellaRicezione, flgSoloAlMitt, title, tipoInvio, titleWindow, sendCallback);

		this.customDetailToReload = customDetailToReload;
	}

	public PostaElettronicaWindow(String nomeEntita, Boolean justWindow, String tipoRel, Record recordPrincipale, String casellaRicezione,
			boolean flgSoloAlMitt, String title, String tipoInvio, String titleWindow, final DSCallback sendCallback) {
		
		this(nomeEntita, justWindow, tipoRel, recordPrincipale, casellaRicezione, flgSoloAlMitt, title, tipoInvio, titleWindow, sendCallback, null);
	}
	
	public PostaElettronicaWindow(String nomeEntita, Boolean justWindow, String tipoRel, Record recordPrincipale, String casellaRicezione,
			boolean flgSoloAlMitt, String title, String tipoInvio, String titleWindow, final DSCallback sendCallback, TipologiaMail tipologiaMailGestioneModelli) {
		super(nomeEntita, justWindow);

		_instance = this;
		
		this.vm = new ValuesManager();
		this.recordPrincipale = recordPrincipale;
		this.sendCallback = sendCallback;
		this.casellaRicezione = casellaRicezione;
		this.flgSoloAlMitt = flgSoloAlMitt;
		this.titleWindow = titleWindow;
		this.tipoInvio = tipoInvio;
		this.tipoRel = tipoRel;
		this.tipologiaMailGestioneModelli = tipologiaMailGestioneModelli;

		init();
	}

	public void init() {

		setTitle(getTitleWindow());

		setIcon(getIconWindow());

		createTabset();

		insertTabs();

		//Per creare il layout principale
		VLayout mainLayout = createMainLayout();
		mainLayout.setStyleName(it.eng.utility.Styles.detailLayoutWithTabSet);

		addMember(mainLayout);

		// La finestra non si deve aprire subito, ma solamente al termine del caricamento dei dati
		// show();
	}

	protected VLayout createMainLayout() {

		createDetailToolstrip();

		VLayout mainLayout = new VLayout();
		mainLayout.setHeight100();
		mainLayout.setWidth100();
		
		mainLayout.addMember(tabSetGenerale);
		mainLayout.addMember(detailToolStrip);

		return mainLayout;

	}

	private void createDetailToolstrip() {
//		INVIO
		confermaButton = new DetailToolStripButton(I18NUtil.getMessages().invionotificainterop_invioButton_title(), "buttons/send.png");
		confermaButton.addClickHandler(new com.smartgwt.client.widgets.events.ClickHandler() {

			@Override
			public void onClick(com.smartgwt.client.widgets.events.ClickEvent event) {
				confermaButton.focusAfterGroup(); //Aggiunto per guadagnare tempo prima del deferred (problema incolla comboboxitem)

				Scheduler.get().scheduleDeferred(new ScheduledCommand() {

					@Override
					public void execute() {
						inviaMail(sendCallback);
					}
				});
			}
		});

		salvaBozzaButton = new DetailToolStripButton("Salva bozza", "buttons/save.png");
		if(isHideSalvaBozzaButton()) {
			salvaBozzaButton.setVisibility(Visibility.HIDDEN);
		}
		salvaBozzaButton.addClickHandler(new com.smartgwt.client.widgets.events.ClickHandler() {

			@Override
			public void onClick(com.smartgwt.client.widgets.events.ClickEvent event) {
				confermaButton.focusAfterGroup(); //Aggiunto per guadagnare tempo prima del deferred (problema incolla comboboxitem)

				Scheduler.get().scheduleDeferred(new ScheduledCommand() {

					@Override
					public void execute() {
						salvaBozza(new DSCallback() {

							@Override
							public void execute(DSResponse response, Object rawData, DSRequest request) {
								
								//Apro un popup per avvertire del caricamento del dettaglio di una bozza
								Layout.showWaitPopup(I18NUtil.getMessages().posta_elettronica_detail_caricamento_dettaglio_mail());
								
								if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
									Record record = response.getData()[0];
									caricaDettaglioEmail(record.getAttributeAsString("idEmailPrincipale"), new ServiceCallback<Record>() {

										@Override
										public void execute(Record object) {

											_instance._instance.markForDestroy();
										}
									});
								}
							}
						});
					}
				});
			}
		});

		detailToolStrip = new ToolStrip();
		detailToolStrip.setWidth100();
		detailToolStrip.setHeight(30);
		detailToolStrip.setStyleName(it.eng.utility.Styles.detailToolStrip);
		detailToolStrip.addFill(); // push all buttons to the right
		detailToolStrip.addButton(confermaButton);
		detailToolStrip.addButton(salvaBozzaButton);
	}

	protected void createTabset() throws IllegalStateException {
		tabSetGenerale = new TabSet();
		tabSetGenerale.setTabBarPosition(Side.TOP);
		tabSetGenerale.setTabBarAlign(Side.LEFT);
		tabSetGenerale.setWidth100();
		tabSetGenerale.setBorder("0px");
		tabSetGenerale.setCanFocus(false);
		tabSetGenerale.setTabIndex(-1);
		tabSetGenerale.setPaneMargin(0);
	}

	/**
	 * 
	 * @throws IllegalStateException
	 */
	protected void insertTabs() throws IllegalStateException {
		// creazione tab
		createWindow();

		// Aggiungo i tab al tabset
		tabSetGenerale.addTab(tabWindowPrincipale);
		// Il tab degli item in lavorazione si vede solamente se è attibo
		if (AurigaLayout.getParametroDBAsBoolean("ATTIVA_ITEM_LAV_EMAIL")) {
//			tabSetGenerale.addTab(tabWindowIterBozza);
		}
	}

	protected void createWindow() {

		// inizializzazione tab principale
		tabWindowPrincipale = new Tab("<b>" + I18NUtil.getMessages().posta_elettronica_detail_section_note_datiprincipali() + "</b>");
		tabWindowPrincipale.setPrompt(titleTab);

		// inizializzazione tab secondario
//		tabWindowIterBozza = new Tab("<b>" + I18NUtil.getMessages().posta_elettronica_detail_section_note_appunti() + "</b>");
//		tabWindowIterBozza.setPrompt(I18NUtil.getMessages().posta_elettronica_detail_section_note_appunti());
//		tabWindowIterBozza.addTabSelectedHandler(new TabSelectedHandler() {
//
//			@Override
//			public void onTabSelected(TabSelectedEvent arg0) {
//				if ((layoutIterBozza != null) && (layoutIterBozza.getItemLavorazioneMailItem() != null)
//						&& (layoutIterBozza.getItemLavorazioneMailItem().getEditing())) {
//					int count = layoutIterBozza.getItemLavorazioneMailItem().getTotalMembers();
//					if (count == 0) {
//						layoutIterBozza.getItemLavorazioneMailItem().onClickNewButton();
//					}
//				}
//			}
//		});

		tabWindowPrincipale.setPane(createLayoutTab(getLayoutDatiWindow()));
//		tabWindowIterBozza.setPane(createLayoutTab(getLayoutIterBozzaWindow()));

	}

	protected VLayout createLayoutTab(VLayout layoutPrincipale) {

		VLayout layoutTabPrincipale = new VLayout();
		layoutTabPrincipale.setWidth100();
		layoutTabPrincipale.setHeight100();
		layoutTabPrincipale.addMember(layoutPrincipale);
		layoutTabPrincipale.setRedrawOnResize(true);

		return layoutTabPrincipale;
	}

//	protected VLayout getLayoutIterBozzaWindow() {
//
//		layoutIterBozza = new ItemLavorazioneMailLayout(finalita, vm) {
//
//			@Override
//			public void trasformaInAllegatoEmailFromItemLavorazione(Record record) {
//				setFileAsAllegato(record);
//			};
//		};
//
//		layoutIterBozza.setHeight100();
//		layoutIterBozza.setWidth100();
//
//		return layoutIterBozza;
//
//	}

	public abstract VLayout getLayoutDatiWindow();

	public abstract String getTitleWindow();

	public abstract String getIconWindow();

	public abstract void setFileAsAllegato(Record record);

	public abstract void inviaMail(DSCallback callback);

	public abstract void salvaBozza(DSCallback callback);

	public boolean isBozza() {
		return (recordPrincipale != null && recordPrincipale.getAttribute("id") != null && recordPrincipale.getAttribute("id").toUpperCase().endsWith(".B"));
	}

	public void caricaDettaglioEmail(String idEmail, final ServiceCallback<Record> callback) {
		Record lRecord = new Record();
		lRecord.setAttribute("idEmail", idEmail);
		GWTRestDataSource lGwtRestDataSource = new GWTRestDataSource("AurigaGetDettaglioPostaElettronicaDataSource", "idEmail", FieldType.TEXT);
		lGwtRestDataSource.performCustomOperation("get", lRecord, new DSCallback() {

			@Override
			public void execute(DSResponse response, Object rawData, DSRequest request) {
				if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
					Record record = response.getData()[0];
					String flgIo = record.getAttributeAsString("flgIo");
					final String idEmail = record.getAttributeAsString("idEmail");
					final String tipoRel = record.getAttributeAsString("tipoRel");
					final String id = record.getAttribute("id");
					final Record lRecord = new Record();
					lRecord.setAttribute("idEmail", idEmail);
					lRecord.setAttribute("flgIo", flgIo);
					lRecord.setAttribute("id", id);

					if (customLayoutToDoSearch != null) {
						new DettaglioEmailWindow(idEmail, tipoRel, null, lRecord, null, customLayoutToDoSearch);
					} else if (customDetailToReload != null) {
						new DettaglioEmailWindow(idEmail, tipoRel, null, lRecord, null, customDetailToReload);
					} else {
						new DettaglioEmailWindow(idEmail, tipoRel, null, lRecord, null);
					}
					if (callback != null) {
						callback.execute(record);
					}
				}
			}
		}, new DSRequest());
	}

	public CustomDetail getCustomDetailToReload() {
		return customDetailToReload;
	}

	public void setCustomDetailToReload(CustomDetail customDetailToReload) {
		this.customDetailToReload = customDetailToReload;
	}

	public CustomLayout getCustomLayoutToDoSearch() {
		return customLayoutToDoSearch;
	}

	public void setCustomLayoutToDoSearch(CustomLayout customLayoutToDoSearch) {
		this.customLayoutToDoSearch = customLayoutToDoSearch;
	}
	
	public Record getRecordPrincipale() {
		return recordPrincipale;
	}

	public void setRecordPrincipale(Record recordPrincipale) {
		this.recordPrincipale = recordPrincipale;
	}
	
	/**
	 * Controlla se ci sono mail aperte tra quella a cui si sta riposndendo o quelle 
	 * di cui si sta facendo l'inoltro
	 * 
	 * @return true se ci sono mail aperte
	 */
	protected boolean controllaMailAperte(){
		// Controllo se sono in un invio singolo o massivo
		if (recordPrincipale != null && recordPrincipale.getAttributeAsRecordArray("listaRecord") != null && recordPrincipale.getAttributeAsRecordArray("listaRecord").length > 0){
			// Sto facendo un inoltro massivo
			Record[] listaMailInoltrate = recordPrincipale.getAttributeAsRecordArray("listaRecord");
			for (Record mail : listaMailInoltrate) {
				if ("aperta".equalsIgnoreCase(mail.getAttribute("statoLavorazione"))){
					return true;
				}
			}
			return false;
		}else{
			// Sto rispondendo o inoltrando una singola mail
			return (recordPrincipale != null && "aperta".equalsIgnoreCase(recordPrincipale.getAttribute("statoLavorazione")));
		}	
	}
	
	public boolean isHideSalvaBozzaButton() {
		return false;
	}
	
	@Override
	protected void onDestroy() {
		if (vm != null) {
			try {
				vm.destroy();
			} catch (Exception e) {
			}
		}
		super.onDestroy();
	}

}
