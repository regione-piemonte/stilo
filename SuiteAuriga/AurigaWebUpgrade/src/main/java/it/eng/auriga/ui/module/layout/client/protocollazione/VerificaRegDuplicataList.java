/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.auriga.ui.module.layout.client.i18n.I18NUtil;
import it.eng.utility.ui.module.core.client.datasource.GWTRestDataSource;
import it.eng.utility.ui.module.layout.client.common.CustomList;

import java.util.HashMap;
import java.util.Map;

import com.smartgwt.client.data.DSCallback;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.DateDisplayFormat;
import com.smartgwt.client.types.ListGridFieldType;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.ImgButton;
import com.smartgwt.client.widgets.grid.CellFormatter;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.grid.ListGridRecord;
import com.smartgwt.client.widgets.layout.HLayout;

public class VerificaRegDuplicataList extends CustomList {

	private ListGridField idUd; 
	private ListGridField score;	
	private ListGridField estremiRegistrazione;
	private ListGridField tsRegistrazione;
	private ListGridField flgVersoRegistrazione;
	private ListGridField mittenti;
	private ListGridField destinatari;
	private ListGridField rifRegProvenienza;
	private ListGridField nroRegProvenienza;
	private ListGridField dtRegProvenienza;
	private ListGridField oggetto;
	private ListGridField nomeFilePrimario;
	private ListGridField nomiFileCoincidenti;	
	private ListGridField nroRaccomandata;
	private ListGridField dtRaccomandata;
	private ListGridField comparators;

	public VerificaRegDuplicataList(String nomeEntita) {

		super(nomeEntita);

		idUd = new ListGridField("idUd");
		idUd.setHidden(true);
		idUd.setCanHide(false);

		score = new ListGridField("score", "Grado di somiglianza");	
		score.setType(ListGridFieldType.INTEGER);
		score.setSortByDisplayField(false);
		score.setCellFormatter(new CellFormatter() {			
			@Override
			public String format(Object value, ListGridRecord record, int rowNum,
					int colNum) {
				
				Integer score = value != null && !"".equals(String.valueOf(value)) ? new Integer(String.valueOf(value)) : null;
				if(score != null) {
					String res = "";
					for(int i = 0; i < score; i++) {
						res += "<img src=\"images/score.png\" size=\"10\"/>";
					}
					return res;
				}
				return null;
			}
		});

		estremiRegistrazione = new ListGridField("estremiRegistrazione", "Segnatura");	

		tsRegistrazione = new ListGridField("tsRegistrazione", "Data protocollo");	
		tsRegistrazione.setType(ListGridFieldType.DATE);
		tsRegistrazione.setDateFormatter(DateDisplayFormat.TOEUROPEANSHORTDATETIME);		
		tsRegistrazione.setWrap(false);	

		flgVersoRegistrazione = new ListGridField("flgVersoRegistrazione", "Tipo protocollo");
		flgVersoRegistrazione.setType(ListGridFieldType.ICON);
		flgVersoRegistrazione.setWidth(30);
		flgVersoRegistrazione.setIconWidth(16);
		flgVersoRegistrazione.setIconHeight(16);
		Map<String, String> flgVersoRegistrazioneValueIcons = new HashMap<String, String>();
		flgVersoRegistrazioneValueIcons.put("E", "menu/protocollazione_entrata.png");
		flgVersoRegistrazioneValueIcons.put("U", "menu/protocollazione_uscita.png");
		flgVersoRegistrazioneValueIcons.put("I", "menu/protocollazione_interna.png");
		flgVersoRegistrazioneValueIcons.put("" , "blank.png");
		flgVersoRegistrazione.setValueIcons(flgVersoRegistrazioneValueIcons);
		Map<String, String> flgVersoRegistrazioneValueHovers = new HashMap<String, String>();
		flgVersoRegistrazioneValueHovers.put("E", I18NUtil.getMessages().archivio_list_tipoProtocolloInEntrataAlt_value());
		flgVersoRegistrazioneValueHovers.put("U", I18NUtil.getMessages().archivio_list_tipoProtocolloInUscitaAlt_value());
		flgVersoRegistrazioneValueHovers.put("I", I18NUtil.getMessages().archivio_list_tipoProtocolloInternoAlt_value());
		flgVersoRegistrazioneValueHovers.put("",  "");
		flgVersoRegistrazione.setAttribute("valueHovers", flgVersoRegistrazioneValueHovers);

		mittenti = new ListGridField("mittenti", "Mittente/i");	

		destinatari = new ListGridField("destinatari", "Destinatari");

		rifRegProvenienza = new ListGridField("rifRegProvenienza", "Rif. prot. ricevuto");	

		nroRegProvenienza = new ListGridField("nroRegProvenienza", "N.ro prot. ricevuto");	

		dtRegProvenienza = new ListGridField("dtRegProvenienza", "Data prot. ricevuto");	
		dtRegProvenienza.setType(ListGridFieldType.DATE);
		dtRegProvenienza.setDateFormatter(DateDisplayFormat.TOEUROPEANSHORTDATE);		
		dtRegProvenienza.setWrap(false);	

		oggetto = new ListGridField("oggetto", "Oggetto");	

		nomeFilePrimario = new ListGridField("nomeFilePrimario", "File primario");	

		nomiFileCoincidenti = new ListGridField("nomiFileCoincidenti", "File coincidenti");		

		nroRaccomandata = new ListGridField("nroRaccomandata", "N.ro raccomandata");	

		dtRaccomandata = new ListGridField("dtRaccomandata", "Data raccomandata");					
		dtRaccomandata.setType(ListGridFieldType.DATE);
		dtRaccomandata.setDateFormatter(DateDisplayFormat.TOEUROPEANSHORTDATE);		
		dtRaccomandata.setWrap(false);	

		comparators = new ListGridField("comparators");
		comparators.setHidden(true);
		comparators.setCanHide(false);

		setFields(new ListGridField[] {
				idUd,
				score,	
				estremiRegistrazione,
				tsRegistrazione,
				flgVersoRegistrazione,
				mittenti,
				destinatari,
				rifRegProvenienza,
				nroRegProvenienza,
				dtRegProvenienza,
				oggetto,
				nomeFilePrimario,
				nomiFileCoincidenti,	
				nroRaccomandata,
				dtRaccomandata,
				comparators
		});  

	}

	@Override  
	protected int getButtonsFieldWidth() {
		return 50;
	}

	@Override  
	protected String getCellCSSText(ListGridRecord record, int rowNum, int colNum) {
		Map comparators = record.getAttributeAsMap("comparators");
		String fieldName = getFields()[colNum].getName();				
		String comparator = comparators != null ? (String) comparators.get(fieldName) : null;
		if(comparator != null) {
			if("0".equals(comparator)) {
				return "color: black";
			} else if("1".equals(comparator)) {
				return "color: orange";
			} else if("2".equals(comparator)) {
				return "color: red";
			} else if("-1".equals(comparator)) {
				return "color: grey";
			}
		} 
		return super.getCellCSSText(record, rowNum, colNum);
	} 

	// Metodo per visualizzare il DETTAGLIO ( eseguito quando si clicca sul bottone )
	@Override  
	public void manageLoadDetail(final Record record, final int recordNum, final DSCallback callback) {
		final GWTRestDataSource lGwtRestDataSource = new GWTRestDataSource("ProtocolloDataSource");
		Record lRecordToLoad = new Record();
		lRecordToLoad.setAttribute("idUd", record.getAttribute("idUd"));
		lGwtRestDataSource.getData(lRecordToLoad, new DSCallback() {
			@Override
			public void execute(DSResponse response, Object rawData, DSRequest request) {
				if(response.getStatus() == DSResponse.STATUS_SUCCESS) {						
					Record lRecord = response.getData()[0];
					/*
					String lStringTipoProtocollo = lRecord.getAttribute("flgTipoProv") != null ? lRecord.getAttribute("flgTipoProv") : "";
					if (lStringTipoProtocollo.equals("E")){
						ProtocollazioneDetailEntrata protocollazioneDetailEntrata = ProtocollazioneUtil.buildProtocollazioneDetailEntrata(lRecord);
						protocollazioneDetailEntrata.caricaDettaglio(layout,lRecord);
						layout.changeDetail(lGwtRestDataSource, protocollazioneDetailEntrata);	
					} else if (lStringTipoProtocollo.equals("U")){
						ProtocollazioneDetailUscita protocollazioneDetailUscita = ProtocollazioneUtil.buildProtocollazioneDetailUscita(lRecord);	
						protocollazioneDetailUscita.caricaDettaglio(layout,lRecord);
						layout.changeDetail(lGwtRestDataSource, protocollazioneDetailUscita);		
					} else if (lStringTipoProtocollo.equals("I")){
						ProtocollazioneDetailInterna protocollazioneDetailInterna = ProtocollazioneUtil.buildProtocollazioneDetailInterna(lRecord);	
						protocollazioneDetailInterna.caricaDettaglio(layout,lRecord);
						layout.changeDetail(lGwtRestDataSource, protocollazioneDetailInterna);		
					} else {
						ProtocollazioneDetailBozze protocollazioneDetailBozze = ProtocollazioneUtil.buildProtocollazioneDetailBozze(lRecord);
						protocollazioneDetailBozze.caricaDettaglio(layout,lRecord);
						layout.changeDetail(lGwtRestDataSource, protocollazioneDetailBozze);	
					}
					*/
					ProtocollazioneDetail  protocollazioneDetail = ProtocollazioneDetail.getInstance(lRecord);
					protocollazioneDetail.caricaDettaglio(layout, lRecord);
					layout.changeDetail(lGwtRestDataSource, protocollazioneDetail);
					callback.execute(new DSResponse(), null, new DSRequest());
				}
			}
		});					
	}			

	// Definisco i bottoni DETTAGLIO/MODIFICA/CANCELLA/SELEZIONA
	@Override  
	protected Canvas createFieldCanvas(String fieldName, final ListGridRecord record) {  

		Canvas lCanvasReturn = null;

		if (fieldName.equals("buttons")) {	

			ImgButton detailButton = buildDetailButton(record);  
			ImgButton lookupButton = buildLookupButton(record);	

			if(!isRecordAbilToView(record)) {	
				detailButton.disable();								
			}			

			// creo la colonna BUTTON
			HLayout recordCanvas = new HLayout(3);
			recordCanvas.setHeight(22);   
			recordCanvas.setWidth(getButtonsFieldWidth());
			recordCanvas.setAlign(Alignment.RIGHT);
			recordCanvas.setLayoutRightMargin(3);   
			recordCanvas.setMembersMargin(7);

			recordCanvas.addMember(detailButton);	

			if(layout.isLookup()) {
				if(!isRecordSelezionabileForLookup(record)) {
					lookupButton.disable();
				}
				recordCanvas.addMember(lookupButton);		// aggiungo il bottone SELEZIONA				
			}

			lCanvasReturn = recordCanvas;

		}	

		return lCanvasReturn;
	}

	/********************************NUOVA GESTIONE CONTROLLI BOTTONI********************************/
	@Override
	public boolean isDisableRecordComponent() {
		
		return true;
	};
	
	@Override
	protected boolean showDetailButtonField() {
		return true;
	}
	
	@Override
	protected boolean isRecordAbilToView(ListGridRecord record) {
		String idUd = record.getAttribute("idUd");
		return (idUd != null && !"".equals(idUd));
	}
    /********************************FINE NUOVA GESTIONE CONTROLLI BOTTONI********************************/
}
