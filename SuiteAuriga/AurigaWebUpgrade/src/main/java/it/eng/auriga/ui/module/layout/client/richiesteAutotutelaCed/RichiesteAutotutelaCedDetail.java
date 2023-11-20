/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import com.smartgwt.client.types.Overflow;
import com.smartgwt.client.types.Side;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.HiddenItem;
import com.smartgwt.client.widgets.form.fields.TextAreaItem;
import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.client.widgets.tab.Tab;
import com.smartgwt.client.widgets.tab.TabSet;

import it.eng.auriga.ui.module.layout.client.i18n.I18NUtil;
import it.eng.utility.ui.module.layout.client.common.CustomDetail;
import it.eng.utility.ui.module.layout.client.common.DetailSection;
import it.eng.utility.ui.module.layout.client.common.items.CheckboxItem;
import it.eng.utility.ui.module.layout.client.common.items.DateItem;
import it.eng.utility.ui.module.layout.client.common.items.NumericItem;
import it.eng.utility.ui.module.layout.client.common.items.TextItem;

public class RichiesteAutotutelaCedDetail extends CustomDetail {

	protected RichiesteAutotutelaCedDetail instance;

	private HiddenItem idRichiestaItem;
	private TextItem descrizioneTipoRichiestaItem;
	private HiddenItem codiceTipoRichiestaItem;
	private TextItem descrizioneStatoRichiestaItem;
	private HiddenItem codiceStatoRichiestaItem;
	private TextItem cfUtenteItem;
	private TextItem numeroDocumentoItem;
	private TextItem numeroProtocolloItem;
	private DateItem dataRichiestaItem;
	private DateItem dataCambioStatoItem;
	private CheckboxItem flagLettoItem;
	private TextItem idEnteItem;
	private CheckboxItem esitoItem;
	private TextItem motivazioneItem;
	private TextItem ricevutaItem;
	private AllegatiRichiestaAutotutelaCedItem listaAllegatiItem;
	private TextItem codiceAcsItem;
	private TextItem intestaDenominazioneItem;
	private TextItem intestaIndirizzoItem;
	private TextItem intestaComuneItem;
	private TextItem intestaProvinciaItem;
	private TextItem intestaCapItem;
	private NumericItem annoDocumentoItem;
	private DateItem dataDocumentoItem;
	private TextItem entrataItem;
	private TextItem entrataAliasItem;
	private TextAreaItem noteItem;
	private HiddenItem idEntrataItem;

	protected String mode;
	
	protected TabSet tabSetGenerale;
	protected Tab tabDatiPrincipali;
	protected DetailSection detailAutotutela;
	protected DetailSection detailStatoLavorazione;
	protected DetailSection detailAllegati;
	protected DetailSection detailAltro;
	protected DynamicForm dynamicFormDettaglio;
	protected DynamicForm dynamicFormStatoLavorazione;
	protected DynamicForm dynamicFormAllegati;
	protected DynamicForm dynamicFormAltro;

	public RichiesteAutotutelaCedDetail(String nomeEntita) {

		super(nomeEntita);
		
		instance = this;
		
		setStyleName(it.eng.utility.Styles.detailLayoutWithTabSet);
		
		setPaddingAsLayoutMargin(false);
		
		buildFormDettaglioAutotutela();
		buildFormStatoLavorazione();
		buildFormAllegati();
		buildFormAltro();
		
		tabSetGenerale = new TabSet();
		tabSetGenerale.setTabBarPosition(Side.TOP);
		tabSetGenerale.setTabBarAlign(Side.LEFT);
		tabSetGenerale.setWidth100();
		tabSetGenerale.setBorder("0px");
		tabSetGenerale.setCanFocus(false);
		tabSetGenerale.setTabIndex(-1);
		tabSetGenerale.setOverflow(Overflow.HIDDEN);
				
		// Creo i due Spacer
		VLayout spacer1 = new VLayout();
		spacer1.setHeight100();
		spacer1.setWidth100();
		
		
		VLayout layoutDatiPrincipaliDetailSection = createDetailSectionTabSetDatiPrincipali();
		
		tabDatiPrincipali = new Tab(I18NUtil.getMessages().richiesta_autotutela_tab_riepilogo());
		
		VLayout layoutTab1 = new VLayout();
		layoutTab1.addMember(layoutDatiPrincipaliDetailSection);
		layoutTab1.addMember(spacer1);
		
		tabDatiPrincipali.setPane(layoutTab1);
		
		tabSetGenerale.addTab(tabDatiPrincipali);
		
		// LAYOUT MAIN
		VLayout lVLayout = new VLayout();
		lVLayout.setWidth100();
		lVLayout.setHeight(500);
	
		VLayout lVLayoutSpacer = new VLayout();
		lVLayoutSpacer.setWidth100();
		lVLayoutSpacer.setHeight(50);

	//	lVLayout.addMember(tabSetGenerale);
		lVLayout.addMember(detailAutotutela);
		lVLayout.addMember(detailStatoLavorazione);
		lVLayout.addMember(detailAllegati);
		lVLayout.addMember(detailAltro);

		addMember(lVLayout);
		addMember(lVLayoutSpacer);

	}
	 
	/**
	 * Creo il layout principale in cui vengono indicate le varie sezioni che dovranno essere presenti
	 * @return il layout da assegnare alla tab di riepilogo
	 */
	private VLayout createDetailSectionTabSetDatiPrincipali() {

		VLayout layoutDatiPrincipali = new VLayout();
		layoutDatiPrincipali.setHeight(5);
		layoutDatiPrincipali.setOverflow(Overflow.VISIBLE);

		detailAutotutela = new DetailSection(I18NUtil.getMessages().richiesta_autotutela_titolo_sezione_dettaglio(), true, true, false, dynamicFormDettaglio);
		detailAutotutela.setVisible(true);
		layoutDatiPrincipali.addMember(detailAutotutela);
		
		detailStatoLavorazione = new DetailSection(I18NUtil.getMessages().richiesta_autotutela_titolo_sezione_stato_lavorazione(), true, true, false, dynamicFormStatoLavorazione);
		detailStatoLavorazione.setVisible(true);
		layoutDatiPrincipali.addMember(detailStatoLavorazione);
		
		detailAllegati = new DetailSection(I18NUtil.getMessages().richiesta_autotutela_titolo_sezione_allegati(), true, true, false, dynamicFormAllegati);
		detailAllegati.setVisible(true);
		layoutDatiPrincipali.addMember(detailAllegati);
		
		detailAltro = new DetailSection(I18NUtil.getMessages().richiesta_autotutela_titolo_sezione_altro(), true, true, false, dynamicFormAltro);
		detailAltro.setVisible(true);
		layoutDatiPrincipali.addMember(detailAltro);
		
		return layoutDatiPrincipali;
	}
	
	
	
	protected void buildFormDettaglioAutotutela() {

		//Creazione del form e delle rispettive impostazioni
		dynamicFormDettaglio = new DynamicForm();
		dynamicFormDettaglio.setValuesManager(vm);
		dynamicFormDettaglio.setWidth100();
		dynamicFormDettaglio.setHeight100();
		dynamicFormDettaglio.setWrapItemTitles(false);
		dynamicFormDettaglio.setNumCols(10);
		dynamicFormDettaglio.setColWidths("60", "1", "1", "1", "1", "1", "1", "1", "*", "*");

		idRichiestaItem = new HiddenItem("idRichiesta");

		descrizioneTipoRichiestaItem = new TextItem("descrizioneTipoRichiesta", I18NUtil.getMessages().richiesta_autotutela_label_tipo_richiesta());
		descrizioneTipoRichiestaItem.setStartRow(true);
		descrizioneTipoRichiestaItem.setColSpan(1);

		codiceTipoRichiestaItem = new HiddenItem("codiceTipoRichiesta");

		codiceStatoRichiestaItem = new HiddenItem("codiceStatoRichiesta");

		cfUtenteItem = new TextItem("codicefiscaleUtente", I18NUtil.getMessages().richiesta_autotutela_label_codice_fiscale());
		cfUtenteItem.setColSpan(1);

		numeroDocumentoItem = new TextItem("numeroDocumento", I18NUtil.getMessages().richiesta_autotutela_label_num_documento());
		numeroDocumentoItem.setStartRow(true);
		numeroDocumentoItem.setColSpan(1);

		numeroProtocolloItem = new TextItem("numeroProtocollo", I18NUtil.getMessages().richiesta_autotutela_label_num_prot());
		numeroProtocolloItem.setColSpan(1);

		idEnteItem = new TextItem("idEnte", I18NUtil.getMessages().richiesta_autotutela_label_ente());
		idEnteItem.setStartRow(true);
		idEnteItem.setColSpan(1);

		motivazioneItem = new TextItem("motivazione", I18NUtil.getMessages().richiesta_autotutela_label_motivazione());
		motivazioneItem.setColSpan(1);

		ricevutaItem = new TextItem("ricevuta", I18NUtil.getMessages().richiesta_autotutela_label_ricevuta());
		ricevutaItem.setStartRow(true);
		ricevutaItem.setColSpan(1);
		
		esitoItem = new CheckboxItem("esito", I18NUtil.getMessages().richiesta_autotutela_label_esito());
		esitoItem.setColSpan(1);
		
		//Aggiunta dei field all'interno del form in questione
		dynamicFormDettaglio.setFields(idRichiestaItem, descrizioneTipoRichiestaItem, codiceTipoRichiestaItem, codiceStatoRichiestaItem,
				cfUtenteItem, numeroDocumentoItem, numeroProtocolloItem,
				idEnteItem, motivazioneItem, ricevutaItem, esitoItem);
	}
	
	
	protected void buildFormStatoLavorazione() {

		//Creazione del form e delle rispettive impostazioni
		dynamicFormStatoLavorazione = new DynamicForm();
		dynamicFormStatoLavorazione.setValuesManager(vm);
		dynamicFormStatoLavorazione.setWidth100();
		dynamicFormStatoLavorazione.setHeight100();
		dynamicFormStatoLavorazione.setWrapItemTitles(false);
		dynamicFormStatoLavorazione.setNumCols(10);
		dynamicFormStatoLavorazione.setColWidths("60", "1", "1", "1", "1", "1", "1", "1", "*", "*");

		dataRichiestaItem = new DateItem("dataRichiesta", I18NUtil.getMessages().richiesta_autotutela_label_data_richiesta());
		dataRichiestaItem.setStartRow(true);
		dataRichiestaItem.setColSpan(4);

		dataCambioStatoItem = new DateItem("dataCambioStato", I18NUtil.getMessages().richiesta_autotutela_label_data_cambio_stato());
		dataCambioStatoItem.setStartRow(false);
		dataCambioStatoItem.setColSpan(4);
	
		
		descrizioneStatoRichiestaItem = new TextItem("descrizioneStatoRichiesta", I18NUtil.getMessages().richiesta_autotutela_label_stato());
		descrizioneStatoRichiestaItem.setStartRow(true);
		descrizioneStatoRichiestaItem.setColSpan(6);

		flagLettoItem = new CheckboxItem("flagLetto", I18NUtil.getMessages().richiesta_autotutela_label_letto());
		flagLettoItem.setStartRow(false);
		flagLettoItem.setColSpan(1);
		flagLettoItem.setWidth("*");
		
		//Aggiunta dei field all'interno del form in questione
		dynamicFormStatoLavorazione.setFields( dataRichiestaItem, dataCambioStatoItem, descrizioneStatoRichiestaItem, flagLettoItem);

	}
	
	protected void buildFormAllegati() {

		//Creazione del form e delle rispettive impostazioni
		dynamicFormAllegati = new DynamicForm();
		dynamicFormAllegati.setValuesManager(vm);
		dynamicFormAllegati.setWidth100();
		dynamicFormAllegati.setHeight100();
		dynamicFormAllegati.setWrapItemTitles(false);
		dynamicFormAllegati.setNumCols(10);
		dynamicFormAllegati.setColWidths("120", "1", "1", "1", "1", "1", "1", "1", "*", "*");


		listaAllegatiItem = new AllegatiRichiestaAutotutelaCedItem();
		listaAllegatiItem.setName("listaAllegati");
		listaAllegatiItem.setStartRow(true);
		listaAllegatiItem.setColSpan(1);
		listaAllegatiItem.setShowTitle(false);
		
		//Aggiunta dei field all'interno del form in questione
		dynamicFormAllegati.setFields(listaAllegatiItem);
	}
	
	private void buildFormAltro() {
		
		dynamicFormAltro = new DynamicForm();
		dynamicFormAltro.setValuesManager(vm);
		dynamicFormAltro.setNumCols(15);
		dynamicFormAltro.setWrapItemTitles(false);
		dynamicFormAltro.setColWidths("1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "*", "*");
        
        codiceAcsItem = new TextItem("codiceAcs", I18NUtil.getMessages().richiesta_autotutela_label_codice_acs());
        codiceAcsItem.setEndRow(true);
        
        intestaDenominazioneItem = new TextItem("intestatarioDenominazione", I18NUtil.getMessages().richiesta_autotutela_label_intestatario_denominazione());
        
        intestaIndirizzoItem = new TextItem("intestatarioIndirizzo", I18NUtil.getMessages().richiesta_autotutela_label_intestatario_indirizzo());
        intestaIndirizzoItem.setEndRow(true);
        intestaIndirizzoItem.setColSpan(10);
        
        intestaComuneItem = new TextItem("intestatarioComune", I18NUtil.getMessages().richiesta_autotutela_label_intestatario_comune());
        
        intestaProvinciaItem = new TextItem("intestatarioProvincia", I18NUtil.getMessages().richiesta_autotutela_label_intestatario_provincia());
        intestaProvinciaItem.setWidth(80);
                
        intestaCapItem = new TextItem("intestatarioCap", I18NUtil.getMessages().richiesta_autotutela_label_intestatario_cap());
        intestaCapItem.setWidth(100);
        intestaCapItem.setEndRow(true);
        
        annoDocumentoItem = new NumericItem("annoDocumento", I18NUtil.getMessages().richiesta_autotutela_label_anno_documento());
        
        dataDocumentoItem = new DateItem("dataDocumento", I18NUtil.getMessages().richiesta_autotutela_label_data_documento());
        dataDocumentoItem.setEndRow(true);
        
        idEntrataItem = new HiddenItem("idEntrata");
        
        entrataItem = new TextItem("entrata", I18NUtil.getMessages().richiesta_autotutela_label_entrata());
        
        entrataAliasItem = new TextItem("entrataAlias", I18NUtil.getMessages().richiesta_autotutela_label_entrata_alias());
        entrataAliasItem.setEndRow(true);
        entrataAliasItem.setColSpan(10);
        
        noteItem = new TextAreaItem("note", I18NUtil.getMessages().richiesta_autotutela_label_note());
        noteItem.setLength(4000);
        noteItem.setHeight(60);
        noteItem.setColSpan(9);
        noteItem.setWidth(600);
        
        dynamicFormAltro.setFields(codiceAcsItem, intestaDenominazioneItem, intestaIndirizzoItem, intestaComuneItem,
        		intestaProvinciaItem, intestaCapItem, annoDocumentoItem, dataDocumentoItem, idEntrataItem, entrataItem, entrataAliasItem, noteItem);              
	}

}
