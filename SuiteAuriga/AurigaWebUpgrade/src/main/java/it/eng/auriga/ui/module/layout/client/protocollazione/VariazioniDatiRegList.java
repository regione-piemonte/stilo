/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import com.smartgwt.client.data.DSCallback;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.DateDisplayFormat;
import com.smartgwt.client.types.ListGridFieldType;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.ImgButton;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.grid.ListGridRecord;
import com.smartgwt.client.widgets.layout.HLayout;

import it.eng.auriga.ui.module.layout.client.richiestaAccessoAtti.RichiestaAccessoAttiDetail;
import it.eng.utility.ui.module.core.client.datasource.GWTRestDataSource;
import it.eng.utility.ui.module.layout.client.common.CustomList;

public class VariazioniDatiRegList extends CustomList {

	private ListGridField idUd;
	private ListGridField tsVariazione;
	private ListGridField tsVariazioneFisso;
	private ListGridField effettuatoDa;
	private ListGridField motiviVariazione;
	private ListGridField datiAnnullabiliVariati;

	public VariazioniDatiRegList(String nomeEntita) {

		super(nomeEntita);

		idUd = new ListGridField("idUd");
		idUd.setHidden(true);
		idUd.setCanHide(false);

		tsVariazione = new ListGridField("tsVariazione", "Variazione del");
		tsVariazione.setType(ListGridFieldType.DATE);
		tsVariazione.setDateFormatter(DateDisplayFormat.TOEUROPEANSHORTDATETIME);

		tsVariazioneFisso = new ListGridField("tsVariazioneFisso");
		tsVariazioneFisso.setHidden(true);
		tsVariazioneFisso.setCanHide(false);

		effettuatoDa = new ListGridField("effettuatoDa", "Effettuato da");
		effettuatoDa.setAlign(Alignment.LEFT);

		motiviVariazione = new ListGridField("motiviVariazione", "Motivo/i variazione");
		motiviVariazione.setAlign(Alignment.LEFT);

		datiAnnullabiliVariati = new ListGridField("datiAnnullabiliVariati", "Dati annullabili variati");
		datiAnnullabiliVariati.setAlign(Alignment.LEFT);

		setFields(new ListGridField[] { idUd, tsVariazione, tsVariazioneFisso, effettuatoDa, motiviVariazione, datiAnnullabiliVariati });

	}

	@Override
	protected int getButtonsFieldWidth() {
		return 25;
	}

	// Metodo per visualizzare il DETTAGLIO ( eseguito quando si clicca sul bottone )
	@Override
	public void manageLoadDetail(final Record record, final int recordNum, final DSCallback callback) {
		final GWTRestDataSource lGwtRestDataSource = new GWTRestDataSource("ProtocolloDataSource");
		String idUd = record.getAttribute("idUd");
		final String tsAnnDati = record.getAttribute("tsVariazioneFisso");
		lGwtRestDataSource.addParam("tsAnnDati", tsAnnDati);
		Record lRecordToLoad = new Record();
		lRecordToLoad.setAttribute("idUd", idUd);
		lGwtRestDataSource.getData(lRecordToLoad, new DSCallback() {

			@Override
			public void execute(DSResponse response, Object rawData, DSRequest request) {
				if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
					Record lRecord = response.getData()[0];
					boolean flgRichiestaAccessoAtti = lRecord.getAttributeAsBoolean("flgRichiestaAccessoAtti") != null && lRecord.getAttributeAsBoolean("flgRichiestaAccessoAtti");
					if (flgRichiestaAccessoAtti){
						RichiestaAccessoAttiDetail richiestaAccessoAttiDetail = new RichiestaAccessoAttiDetail("richiesta_accesso_atti") {
							@Override
							public boolean showDetailToolStrip() {
								return false;
							}
						};
						richiestaAccessoAttiDetail.editRecord(lRecord);						
						layout.changeDetail(lGwtRestDataSource, richiestaAccessoAttiDetail);
					}else{
						ProtocollazioneDetail protocollazioneDetail = ProtocollazioneDetail.getInstance(lRecord);
						protocollazioneDetail.caricaDettaglio(layout, lRecord);
						layout.changeDetail(lGwtRestDataSource, protocollazioneDetail);
					}
					((VariazioniDatiRegLayout) layout).setTsAnnDati(tsAnnDati);
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

			// bottone DETTAGLIO
			ImgButton detailButton = buildDetailButton(record);

			// creo la colonna BUTTON
			HLayout recordCanvas = new HLayout(3);
			recordCanvas.setHeight(22);
			recordCanvas.setWidth(getButtonsFieldWidth());
			recordCanvas.setAlign(Alignment.RIGHT);
			recordCanvas.setLayoutRightMargin(3);
			recordCanvas.setMembersMargin(7);

			recordCanvas.addMember(detailButton);

			lCanvasReturn = recordCanvas;
		}

		return lCanvasReturn;
	}

	@Override
	protected String getDetailButtonPrompt() {
		
		return "Visualizza dati e file della registrazione prima della variazione";
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
	/******************************** FINE NUOVA GESTIONE CONTROLLI BOTTONI ********************************/
}