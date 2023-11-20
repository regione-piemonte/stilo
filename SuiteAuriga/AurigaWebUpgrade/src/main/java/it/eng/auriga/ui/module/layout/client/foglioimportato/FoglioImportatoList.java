/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import com.smartgwt.client.data.Record;
import com.smartgwt.client.types.DateDisplayFormat;
import com.smartgwt.client.types.ListGridFieldType;
import com.smartgwt.client.widgets.grid.CellFormatter;
import com.smartgwt.client.widgets.grid.HoverCustomizer;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.grid.ListGridRecord;
import com.smartgwt.client.widgets.grid.events.RecordClickEvent;
import com.smartgwt.client.widgets.grid.events.RecordClickHandler;

import it.eng.auriga.ui.module.layout.client.i18n.I18NUtil;
import it.eng.utility.ui.module.layout.client.common.CustomList;
import it.eng.utility.ui.module.layout.client.common.file.DownloadFile;

/**
 * 
 * @author DANCRIST
 *
 */

public class FoglioImportatoList extends CustomList {
	
	private ListGridField codApplicazione;
	private ListGridField codIstanzaApplicazione;
	private ListGridField denominazioneApplicazione;
	private ListGridField tipologiaContenuto;
	private ListGridField tsUpload;
	private ListGridField denominazioneUtenteUpload;
	private ListGridField uri;
	private ListGridField stato;
	private ListGridField tsInizioElab;
	private ListGridField tsFineElab;
	private ListGridField nrTotaleRighe;
	private ListGridField nrRigheElabSuccesso;
	private ListGridField nrRigheInErrore;
	private ListGridField idFoglio;
	private ListGridField erroreElabFoglio;
	private ListGridField uriExcelRielaborato;
	private ListGridField uriFileProdotto;
	private ListGridField nomeFile;
	private ListGridField downloadFile;

	public FoglioImportatoList(String nomeEntita) {
		super(nomeEntita);
		
		codApplicazione = new ListGridField("codApplicazione", I18NUtil.getMessages().foglio_importato_list_codApplicazione_title());

		codIstanzaApplicazione = new ListGridField("codIstanzaApplicazione", I18NUtil.getMessages().foglio_importato_list_codIstanzaApplicazione_title());
		
		denominazioneApplicazione = new ListGridField("denominazioneApplicazione", I18NUtil.getMessages().foglio_importato_list_denominazioneApplicazione_title());
		
		tipologiaContenuto = new ListGridField("tipologiaContenuto", I18NUtil.getMessages().foglio_importato_list_tipologiaContenuto_title());
		
		tsUpload = new ListGridField("tsUpload", I18NUtil.getMessages().foglio_importato_list_tsUpload_title());
		tsUpload.setType(ListGridFieldType.DATE);
		tsUpload.setDateFormatter(DateDisplayFormat.TOEUROPEANSHORTDATETIME);
		
		denominazioneUtenteUpload = new ListGridField("denominazioneUtenteUpload", I18NUtil.getMessages().foglio_importato_list_denominazioneUtenteUpload_title());
		
		stato = new ListGridField("stato", I18NUtil.getMessages().foglio_importato_list_stato_title());
		
		tsInizioElab = new ListGridField("tsInizioElab", I18NUtil.getMessages().foglio_importato_list_tsInizioElab_title());
		tsInizioElab.setType(ListGridFieldType.DATE);
		tsInizioElab.setDateFormatter(DateDisplayFormat.TOEUROPEANSHORTDATETIME);
		
		tsFineElab = new ListGridField("tsFineElab", I18NUtil.getMessages().foglio_importato_list_tsFineElab_title());
		tsFineElab.setType(ListGridFieldType.DATE);
		tsFineElab.setDateFormatter(DateDisplayFormat.TOEUROPEANSHORTDATETIME);
		
		nrTotaleRighe = new ListGridField("nrTotaleRighe", I18NUtil.getMessages().foglio_importato_list_nrTotaleRighe_title());
		nrTotaleRighe.setType(ListGridFieldType.INTEGER);
		
		nrRigheElabSuccesso = new ListGridField("nrRigheElabSuccesso", I18NUtil.getMessages().foglio_importato_list_nrRigheElabSuccesso_title());
		nrRigheElabSuccesso.setType(ListGridFieldType.INTEGER);
		
		nrRigheInErrore = new ListGridField("nrRigheInErrore", I18NUtil.getMessages().foglio_importato_list_nrRigheInErrore_title());
		nrRigheInErrore.setType(ListGridFieldType.INTEGER);
		
		idFoglio = new ListGridField("idFoglio", I18NUtil.getMessages().foglio_importato_list_idFoglio_title());
		
		nomeFile = new ListGridField("nomeFile", I18NUtil.getMessages().foglio_importato_list_nomeFile_title());
		
		erroreElabFoglio = new ListGridField("erroreElabFoglio", I18NUtil.getMessages().foglio_importato_list_erroreElabFoglio_title());
		
		uriExcelRielaborato = new ListGridField("uriExcelRielaborato");
		uriExcelRielaborato.setHidden(true);
		uriExcelRielaborato.setCanHide(false);
		
		uriFileProdotto = new ListGridField("uriFileProdotto");
		uriFileProdotto.setHidden(true);
		uriFileProdotto.setCanHide(false);
		
		uri = new ListGridField("uri");
		uri.setHidden(true);
		uri.setCanHide(false);

		downloadFile = new ListGridField("downloadFile", "Scarica foglio");
		downloadFile.setHeaderBaseStyle(it.eng.utility.Styles.hiddenHeaderButton);
		downloadFile.setWrap(false);
		downloadFile.setWidth(30);
		downloadFile.setIconWidth(16);
		downloadFile.setIconHeight(16);
		downloadFile.setAttribute("custom", true);
		downloadFile.setShowHover(true);
		downloadFile.setCellFormatter(new CellFormatter() {

			@Override
			public String format(Object value, ListGridRecord record, int rowNum, int colNum) {
				String uriFile = record.getAttribute("uri");
				if (uriFile != null && !"".equals(uriFile)) {
					return buildIconHtml("file/download_manager.png");
				}
				return null;
			}
		});
		downloadFile.setHoverCustomizer(new HoverCustomizer() {

			@Override
			public String hoverHTML(Object value, ListGridRecord record, int rowNum, int colNum) {
				String uriFile = record.getAttribute("uri");
				if (uriFile != null && !"".equals(uriFile)) {					
					return "Scarica foglio";
				}
				return null;
			}
		});
		downloadFile.addRecordClickHandler(new RecordClickHandler() {

			@Override
			public void onRecordClick(RecordClickEvent event) {
				event.cancel();
				String uriFile = event.getRecord().getAttribute("uri");
				if (uriFile != null && !"".equals(uriFile)) {
					scaricaFile(event.getRecord());
				}
			}
		});
		
		setFields(
				codApplicazione,
				codIstanzaApplicazione,
				denominazioneApplicazione,
				tipologiaContenuto,
				tsUpload,
				denominazioneUtenteUpload,
				stato,
				tsInizioElab,
				tsFineElab,
				nrTotaleRighe,
				nrRigheElabSuccesso,
				nrRigheInErrore,
				idFoglio,
				nomeFile,
				erroreElabFoglio,
				uriExcelRielaborato,
				uriFileProdotto,
				downloadFile,
				uri
		);
	}
	
//	@Override
//	public void manageLoadDetail(final Record record, final int recordNum, final DSCallback callback) {
//		getDataSource().performCustomOperation("get", record, new DSCallback() {
//
//			@Override
//			public void execute(DSResponse response, Object rawData, DSRequest request) {
//				if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
//					Record record = response.getData()[0];
//					layout.getDetail().editRecord(record, recordNum);
//					layout.getDetail().getValuesManager().clearErrors(true);
//					callback.execute(response, null, new DSRequest());
//				}
//			}
//		}, new DSRequest());
//	}
	
	/******************************** NUOVA GESTIONE CONTROLLI BOTTONI ********************************/
	@Override
	public boolean isDisableRecordComponent() {
		return true;
	}

	@Override
	protected boolean showDetailButtonField() {
		return false;
	}

	@Override
	protected boolean showModifyButtonField() {

		return FoglioImportatoLayout.isAbilToMod();
	}

	@Override
	protected boolean showDeleteButtonField() {

		return FoglioImportatoLayout.isAbilToDel();
	}
	/******************************** FINE NUOVA GESTIONE CONTROLLI BOTTONI ********************************/
	
	public void scaricaFile(Record record) {
		Record lRecord = new Record();
		lRecord.setAttribute("displayFilename", record.getAttribute("nomeFile"));
		lRecord.setAttribute("uri", record.getAttribute("uri"));
		lRecord.setAttribute("sbustato", "false");
		lRecord.setAttribute("remoteUri", "true");
		DownloadFile.downloadFromRecord(lRecord, "FileToExtractBean");
	}
	
	@Override
	protected void manageDetailButtonClick(ListGridRecord record) {
		
	}
	
}