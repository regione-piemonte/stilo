/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.Map;

import com.smartgwt.client.data.Record;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.layout.VLayout;

import it.eng.auriga.ui.module.layout.client.i18n.I18NUtil;
import it.eng.utility.ui.module.core.client.callback.ServiceCallback;
import it.eng.utility.ui.module.layout.client.common.items.TextAreaItem;

/**
 * Maschera di un'ISTANZA
 * 
 * @author Mattia Zanin
 *
 */

public class IstanzeDetail extends ProtocollazioneDetailEntrata {

	protected IstanzeDetail instance;

	protected ProtocollazioneHeaderDetailSection detailSectionContribuente;
	protected DynamicForm contribuenteForm;
	protected ContribuenteItem contribuenteItem;

//	protected ProtocollazioneHeaderDetailSection detailSectionDataEOraRicezione;
//	private DateTimeItem dataEOraRicezioneItem;

	protected ProtocollazioneDetailSection detailSectionAnnotazioni;
	protected DynamicForm annotazioniForm;

	public IstanzeDetail(String nomeEntita) {
		super(nomeEntita);

		instance = this;
	}
	
	@Override
	public boolean isModalitaWizard() {
		return false;
	}

	public String getCodStatoDett() {
		return "ISTRDAVV";
	}

	@Override
	public boolean showModelliSelectItem() {
		return false;
	}

	@Override
	public boolean showUoProtocollanteSelectItem() {
		return false;
	}

	@Override
	public boolean isAltraNumerazione() {
		return true;
	}

	@Override
	public String getSiglaAltraNumerazione() {
		return "ISTANZA";
	}

	@Override
	public String getIconAltraNumerazione() {
		return "menu/istanze.png";
	}

	@Override
	public String getTitleAltraNumerazione() {
		return I18NUtil.getMessages().archivio_list_tipoProtocolloIstanzaAlt_value();
	}
	
	@Override
	public boolean showDetailSectionTipoDocumento() {
		return false;
	}

	@Override
	public boolean showTabAssegnazioneEClassificazione() {
		return false;
	}

	@Override
	protected void createTabDatiDocumenti() {
		super.createTabDatiDocumenti();
		tabDatiDocumento.setTitle("<b>" + I18NUtil.getMessages().istanze_detail_tabDatiPrincipali_title() + "</b>");
		tabDatiDocumento.setPrompt(I18NUtil.getMessages().istanze_detail_tabDatiPrincipali_prompt());
	}

	@Override
	public VLayout getLayoutDatiDocumento() {

		VLayout layoutDatiDocumento = new VLayout(5);
		
		createDetailSectionRegistrazione();
		detailSectionRegistrazione.setVisible(false);
		layoutDatiDocumento.addMember(detailSectionRegistrazione);

		createDetailSectionCanaleDataRicezione();
		layoutDatiDocumento.addMember(detailSectionCanaleDataRicezione);
				
		createDetailSectionSupportoOriginale();
		layoutDatiDocumento.addMember(detailSectionSupportoOriginale);

		createDetailSectionContribuente();
		layoutDatiDocumento.addMember(detailSectionContribuente);

//		createDetailSectionDataEOraRicezione();
//		layoutDatiDocumento.addMember(detailSectionDataEOraRicezione);

		createDetailSectionContenuti();
		layoutDatiDocumento.addMember(detailSectionContenuti);

		createDetailSectionAllegati();
		layoutDatiDocumento.addMember(detailSectionAllegati);

		if(showDetailSectionDocCollegato()) {
			createDetailSectionDocCollegato();
			layoutDatiDocumento.addMember(detailSectionDocCollegato);
		}
		
		createDetailSectionAnnotazioni();
		layoutDatiDocumento.addMember(detailSectionAnnotazioni);

		return layoutDatiDocumento;
	}

	protected void createDetailSectionContribuente() {

		createContribuenteForm();

		detailSectionContribuente = new ProtocollazioneHeaderDetailSection(I18NUtil.getMessages().protocollazione_detail_contribuenteForm_title(), true, true, false,
				contribuenteForm);
	}

	protected void createContribuenteForm() {

		contribuenteForm = new DynamicForm();
		contribuenteForm.setValuesManager(vm);
		contribuenteForm.setWidth("100%");
		contribuenteForm.setPadding(5);
		contribuenteForm.setTabSet(tabSet);
		contribuenteForm.setTabID("HEADER");

		contribuenteItem = new ContribuenteItem() {

			public String getNomeEntita() {
				return nomeEntita;
			};
		};

		contribuenteItem.setNotReplicable(true);
		contribuenteItem.setName("listaContribuenti");
		contribuenteItem.setShowTitle(false);

		contribuenteForm.setFields(contribuenteItem);
	}

//	protected void createDetailSectionDataEOraRicezione() {
//
//		createDatiRicezioneForm();
//
//		detailSectionDataEOraRicezione = new ProtocollazioneHeaderDetailSection(I18NUtil.getMessages().protocollazione_detail_datiRicezioneForm_title(), true, true, true, datiRicezioneForm1);
//	}
//
//	@Override
//	protected void createDatiRicezioneForm() {
//
//		datiRicezioneForm1 = new DynamicForm();
//		datiRicezioneForm1.setValuesManager(vm);
//		datiRicezioneForm1.setWidth("100%");
//		datiRicezioneForm1.setPadding(5);
//		datiRicezioneForm1.setNumCols(4);
//		datiRicezioneForm1.setColWidths(140, 300, "*", "*");
//		datiRicezioneForm1.setWrapItemTitles(false);
//		datiRicezioneForm1.setTabSet(tabSet);
//		datiRicezioneForm1.setTabID("HEADER");
//
//		dataEOraRicezioneItem = new DateTimeItem("dataEOraRicezione", I18NUtil.getMessages().protocollazione_detail_dataEOraRicezioneItem_title());
//		dataEOraRicezioneItem.setRequired(true);
//
//		datiRicezioneForm1.setFields(dataEOraRicezioneItem);
//	}

	@Override
	protected void createDetailSectionContenuti() {

		createContenutiForm();
		createFilePrimarioForm();

		detailSectionContenuti = new ProtocollazioneDetailSection(I18NUtil.getMessages().protocollazione_detail_contenutiForm_title(), true, true, false, contenutiForm,
				filePrimarioForm);
	}
	
	@Override
	public String getTitleOggetto() {
		return "Motivazione/i";
	}
	
	@Override
	public boolean isRequiredOggetto() {
		return false;
	}

	protected void createDetailSectionAnnotazioni() {

		createAnnotazioniForm();

		detailSectionAnnotazioni = new ProtocollazioneDetailSection(I18NUtil.getMessages().protocollazione_detail_annotazioniForm_title(), true, false, false, annotazioniForm);
	}

	protected void createAnnotazioniForm() {

		annotazioniForm = new DynamicForm();
		annotazioniForm.setValuesManager(vm);
		annotazioniForm.setWidth100();
		annotazioniForm.setPadding(5);
		annotazioniForm.setWrapItemTitles(false);
		annotazioniForm.setNumCols(10);
		annotazioniForm.setColWidths(120, 1, 1, 1, 1, 1, 1, 1, "*", "*");
		annotazioniForm.setTabSet(tabSet);
		annotazioniForm.setTabID("HEADER");

		noteItem = new TextAreaItem("note", I18NUtil.getMessages().protocollazione_detail_noteItem_title());
		noteItem.setHeight(40);
		noteItem.setWidth(650);
		noteItem.setStartRow(true);
		noteItem.setColSpan(5);
		noteItem.setShowTitle(false);

		annotazioniForm.setFields(noteItem);
	}
	
//	public DynamicForm getContribuenteForm() {
//		return contribuenteForm;
//	}
	
	@Override
	public void modificaDatiMode(Boolean abilAggiuntaFile) {
		
		super.modificaDatiMode(abilAggiuntaFile);

		if (mezzoTrasmissioneItem != null) {
			mezzoTrasmissioneItem.setCanEdit(false);
		}
		
		if (dataEOraArrivoItem != null) {
			dataEOraArrivoItem.setCanEdit(false);
		}
		
		if (supportoOriginaleItem != null) {
			supportoOriginaleItem.setCanEdit(false);
		}
		if (contribuenteItem != null) {
			contribuenteItem.setCanEdit(false);
		}
		if (contribuenteForm != null) {
			setCanEdit(true, contribuenteForm);
		}
	}

	@Override
	public Record getRecordToSave(String motivoVarDatiReg) {
		Record lRecordToSave = super.getRecordToSave(motivoVarDatiReg);
		lRecordToSave.setAttribute("tipoDocumento", tipoDocumento);
		addFormValues(lRecordToSave, canaleDataRicezioneForm);
		addFormValues(lRecordToSave, supportoOriginaleForm);
		addFormValues(lRecordToSave, contribuenteForm);
		addFormValues(lRecordToSave, annotazioniForm);
		lRecordToSave.setAttribute("codStatoDett", getCodStatoDett());
		Record numDadare = new Record();
		numDadare.setAttribute("sigla", getSiglaAltraNumerazione());
		lRecordToSave.setAttribute("tipoNumerazioneDaDare", numDadare);

		return lRecordToSave;
	}

	@Override
	public void clickNuovaProtComeCopia(ServiceCallback<Record> callback) {

		boolean flgNoEsibente = true;
		boolean flgNoMittenti = false;
		boolean flgNoDestinatari = false;
		boolean flgNoAltriAssegnatari = false;
		boolean flgNoOggetto = false;
		boolean flgNoPrimario = true;
		boolean flgNoAllegati = true;
		boolean flgNoFileAllegati = true;
		boolean flgNoDocumentiCollegati = true;
		boolean flgNoFascicolazione = false;
		boolean flgNoAttributiCustom = true;

		nuovaProtComeCopia(flgNoEsibente, flgNoMittenti, flgNoDestinatari, flgNoAltriAssegnatari, flgNoOggetto, flgNoPrimario, flgNoAllegati, flgNoFileAllegati,
				flgNoDocumentiCollegati, flgNoFascicolazione, flgNoAttributiCustom, callback);

		// rimango sulla stessa maschera quindi devo aggiornare il titolo della portlet
		redrawTitleOfPortlet();
	}
	
	@Override
	public void editNewRecord(Map initialValues) {
		super.editNewRecord(initialValues);
		this.tipoDocumento = new Record(initialValues).getAttribute("tipoDocumento");
	}
	
	@Override
	public void editRecord(Record record) {
		super.editRecord(record);
		this.tipoDocumento = record.getAttribute("tipoDocumento");
	}

}
