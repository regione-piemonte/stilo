/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.HashMap;
import java.util.Map;

import com.smartgwt.client.data.DSCallback;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.data.SortSpecifier;
import com.smartgwt.client.types.DateDisplayFormat;
import com.smartgwt.client.types.ListGridFieldType;
import com.smartgwt.client.types.SortDirection;
import com.smartgwt.client.widgets.grid.HoverCustomizer;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.grid.ListGridRecord;
import com.smartgwt.client.widgets.grid.events.RecordClickEvent;
import com.smartgwt.client.widgets.grid.events.RecordClickHandler;
import com.smartgwt.client.widgets.menu.Menu;
import com.smartgwt.client.widgets.menu.MenuItem;
import com.smartgwt.client.widgets.menu.events.MenuItemClickEvent;

import it.eng.auriga.ui.module.layout.client.i18n.I18NUtil;
import it.eng.utility.ui.module.core.client.datasource.GWTRestDataSource;
import it.eng.utility.ui.module.layout.client.common.CustomList;

/**
 * 
 * @author DANCRIST
 *
 */

public class ContenutoFoglioImportatoList extends CustomList {
	
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
	private ListGridField nomeFile;
	private ListGridField erroreElabFoglio;
	private ListGridField nrRiga;
	private ListGridField tsInsRiga;
	private ListGridField esitoElabRiga;
	private ListGridField tsUltimaElabRiga;
	private ListGridField codErroreElabRiga;
	private ListGridField msgErroreElabRiga;
	private ListGridField valoriRiga;

	public ContenutoFoglioImportatoList(String nomeEntita) {
		super(nomeEntita);
		
		codApplicazione = new ListGridField("codApplicazione", I18NUtil.getMessages().contenuto_foglio_importato_list_codApplicazione_title());

		codIstanzaApplicazione = new ListGridField("codIstanzaApplicazione", I18NUtil.getMessages().contenuto_foglio_importato_list_codIstanzaApplicazione_title());
		
		denominazioneApplicazione = new ListGridField("denominazioneApplicazione", I18NUtil.getMessages().contenuto_foglio_importato_list_denominazioneApplicazione_title());
		
		tipologiaContenuto = new ListGridField("tipologiaContenuto", I18NUtil.getMessages().contenuto_foglio_importato_list_tipologiaContenuto_title());
		
		tsUpload = new ListGridField("tsUpload", I18NUtil.getMessages().contenuto_foglio_importato_list_tsUpload_title());
		tsUpload.setType(ListGridFieldType.DATE);
		tsUpload.setDateFormatter(DateDisplayFormat.TOEUROPEANSHORTDATETIME);
		
		denominazioneUtenteUpload = new ListGridField("denominazioneUtenteUpload", I18NUtil.getMessages().contenuto_foglio_importato_list_denominazioneUtenteUpload_title());
		
		uri = new ListGridField("uri");
		uri.setHidden(true);
		uri.setCanHide(false);
		
		stato = new ListGridField("stato", I18NUtil.getMessages().contenuto_foglio_importato_list_stato_title());
		
		tsInizioElab = new ListGridField("tsInizioElab", I18NUtil.getMessages().contenuto_foglio_importato_list_tsInizioElab_title());
		tsInizioElab.setType(ListGridFieldType.DATE);
		tsInizioElab.setDateFormatter(DateDisplayFormat.TOEUROPEANSHORTDATETIME);
		
		tsFineElab = new ListGridField("tsFineElab", I18NUtil.getMessages().contenuto_foglio_importato_list_tsFineElab_title());
		tsFineElab.setType(ListGridFieldType.DATE);
		tsFineElab.setDateFormatter(DateDisplayFormat.TOEUROPEANSHORTDATETIME);
		
		nrTotaleRighe = new ListGridField("nrTotaleRighe", I18NUtil.getMessages().contenuto_foglio_importato_list_nrTotaleRighe_title());
		nrTotaleRighe.setType(ListGridFieldType.INTEGER);
		
		nrRigheElabSuccesso = new ListGridField("nrRigheElabSuccesso", I18NUtil.getMessages().contenuto_foglio_importato_list_nrRigheElabSuccesso_title());
		nrRigheElabSuccesso.setType(ListGridFieldType.INTEGER);
		
		nrRigheInErrore = new ListGridField("nrRigheInErrore", I18NUtil.getMessages().contenuto_foglio_importato_list_nrRigheInErrore_title());
		nrRigheInErrore.setType(ListGridFieldType.INTEGER);
		
		idFoglio = new ListGridField("idFoglio", I18NUtil.getMessages().contenuto_foglio_importato_list_idFoglio_title());
		
		nomeFile = new ListGridField("nomeFile", I18NUtil.getMessages().contenuto_foglio_importato_list_nomeFile_title());
		
		erroreElabFoglio = new ListGridField("erroreElabFoglio", I18NUtil.getMessages().contenuto_foglio_importato_list_erroreElabFoglio_title());
		
		nrRiga = new ListGridField("nrRiga", I18NUtil.getMessages().contenuto_foglio_importato_list_nrRiga_title());
		nrRiga.setType(ListGridFieldType.INTEGER);		
		nrRiga.addRecordClickHandler(new RecordClickHandler() {

			@Override
			public void onRecordClick(RecordClickEvent event) {

				event.cancel();
				final ListGridRecord record = getRecord(event.getRecordNum());
				manageModifyButtonClick(record);
			}
		});
		
		tsInsRiga = new ListGridField("tsInsRiga", I18NUtil.getMessages().contenuto_foglio_importato_list_tsInsRiga_title());
		tsInsRiga.setType(ListGridFieldType.DATE);
		tsInsRiga.setDateFormatter(DateDisplayFormat.TOEUROPEANSHORTDATETIME);
		
		esitoElabRiga = new ListGridField("esitoElabRiga", I18NUtil.getMessages().contenuto_foglio_importato_list_esitoElabRiga_title());
		esitoElabRiga.setType(ListGridFieldType.ICON);
		esitoElabRiga.setWidth(30);
		esitoElabRiga.setIconWidth(16);
		esitoElabRiga.setIconHeight(16);
		Map<String, String> flgEsitoValueIcons = new HashMap<String, String>();
		flgEsitoValueIcons.put("OK", "buttons/verde.png");
		flgEsitoValueIcons.put("KO", "buttons/rosso.png");
		esitoElabRiga.setValueIcons(flgEsitoValueIcons);
		esitoElabRiga.setHoverCustomizer(new HoverCustomizer() {

			@Override
			public String hoverHTML(Object value, ListGridRecord record, int rowNum, int colNum) {
				if ("OK".equals(record.getAttribute("esitoElabRiga"))) {
					return "OK";
				} else {
					return "KO";
				}
			}
		});
		
		tsUltimaElabRiga = new ListGridField("tsUltimaElabRiga", I18NUtil.getMessages().contenuto_foglio_importato_list_tsUltimaElabRiga_title());
		tsUltimaElabRiga.setType(ListGridFieldType.DATE);
		tsUltimaElabRiga.setDateFormatter(DateDisplayFormat.TOEUROPEANSHORTDATETIME);
		
		codErroreElabRiga = new ListGridField("codErroreElabRiga", I18NUtil.getMessages().contenuto_foglio_importato_list_codErroreElabRiga_title());
		
		msgErroreElabRiga = new ListGridField("msgErroreElabRiga", I18NUtil.getMessages().contenuto_foglio_importato_list_msgErroreElabRiga_title());
		
		valoriRiga = new ListGridField("valoriRiga", I18NUtil.getMessages().contenuto_foglio_importato_list_valoriRiga_title());
		
		setFields(
				codApplicazione,
				codIstanzaApplicazione,
				denominazioneApplicazione,
				tipologiaContenuto,
				tsUpload,
				denominazioneUtenteUpload,
				uri,
				stato,
				tsInizioElab,
				tsFineElab,
				nrTotaleRighe,
				nrRigheElabSuccesso,
				nrRigheInErrore,
				idFoglio,
				nomeFile,
				erroreElabFoglio,
				nrRiga,
				tsInsRiga,
				esitoElabRiga,
				tsUltimaElabRiga,
				codErroreElabRiga,
				msgErroreElabRiga,
				valoriRiga
		);
		
	}
	/******************************** NUOVA GESTIONE CONTROLLI BOTTONI ********************************/
	
	@Override  
	public void manageLoadDetail(final Record record, final int recordNum, final DSCallback callback) {
		final GWTRestDataSource datasoruce = ((GWTRestDataSource)getDataSource());
		datasoruce.getData(record, new DSCallback() {
			@Override
			public void execute(DSResponse response, Object rawData, DSRequest request) {
				if(response.getStatus() == DSResponse.STATUS_SUCCESS) {						
					Record detailRecord = response.getData()[0];			
					ContenutoFoglioImportatoDetail contenutoFoglioImportatoDetail = new ContenutoFoglioImportatoDetail("contenuto_foglio_importato", detailRecord);
					contenutoFoglioImportatoDetail.editRecord(detailRecord);	
					layout.changeDetail(datasoruce, contenutoFoglioImportatoDetail);
					callback.execute(response, null, new DSRequest());	
				}
			}
		});
	}
	
	@Override  
	public void manageContextClick(final Record record) {	
		if(record != null) {
			showRowContextMenu(getRecord(getRecordIndex(record)));			
		}
	}
	
	public void showRowContextMenu(final ListGridRecord record) {
		
		Menu contextMenu = new Menu();	
		
		MenuItem visualizzaMenuItem = new MenuItem(I18NUtil.getMessages().detailButton_prompt(), "buttons/detail.png");   
		visualizzaMenuItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {   	      
			
			@Override
			public void onClick(MenuItemClickEvent event) {
				manageDetailButtonClick(record);				
			}   
		});   			
		contextMenu.addItem(visualizzaMenuItem);	
		
		MenuItem modificaMenuItem = new MenuItem(I18NUtil.getMessages().modifyButton_prompt(), "buttons/modify.png");   
		modificaMenuItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {   	      
			
			@Override
			public void onClick(MenuItemClickEvent event) {
				manageModifyButtonClick(record);				
			}   
		});   			
		contextMenu.addItem(modificaMenuItem);	
		
		contextMenu.addSort(new SortSpecifier("title", SortDirection.ASCENDING));			
		contextMenu.showContextMenu();
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

		return ContenutoFoglioImportatoLayout.isAbilToMod();
	}

	@Override
	protected boolean showDeleteButtonField() {

		return ContenutoFoglioImportatoLayout.isAbilToDel();
	}
	/******************************** FINE NUOVA GESTIONE CONTROLLI BOTTONI ********************************/

	@Override
	protected String getBaseStyle(ListGridRecord record, int rowNum, int colNum) {
		try {
			if (getFieldName(colNum).equals("nrRiga")) {
				if (record.getAttribute("nrRiga") != null &&
						!"".equalsIgnoreCase(record.getAttribute("nrRiga"))) {
									return it.eng.utility.Styles.cellTextBlueClickable;
				}
			} else {
				return super.getBaseStyle(record, rowNum, colNum);
			}
		} catch (Exception e) {
			return super.getBaseStyle(record, rowNum, colNum);
		}
		return super.getBaseStyle(record, rowNum, colNum);
	}
}