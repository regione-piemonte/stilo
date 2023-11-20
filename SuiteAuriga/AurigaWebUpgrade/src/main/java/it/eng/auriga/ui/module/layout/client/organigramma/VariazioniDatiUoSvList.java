/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.HashMap;
import java.util.Map;

import com.smartgwt.client.data.DSCallback;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.DateDisplayFormat;
import com.smartgwt.client.types.FieldType;
import com.smartgwt.client.types.ListGridFieldType;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.ImgButton;
import com.smartgwt.client.widgets.grid.HoverCustomizer;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.grid.ListGridRecord;
import com.smartgwt.client.widgets.layout.HLayout;

import it.eng.utility.ui.module.core.client.datasource.GWTRestDataSource;
import it.eng.utility.ui.module.layout.client.common.CustomList;

public class VariazioniDatiUoSvList extends CustomList {

	private ListGridField idUoSvField;
	private ListGridField flgUoSvField;
	private ListGridField dataVariazioneField;
	private ListGridField codiceUoField;
	private ListGridField denominazioneUoField;
	
	// Solo per la postazione
	private ListGridField utenteField;
	private ListGridField nomePostazioneField;
	private ListGridField ruoloUtenteInUoField;
	private ListGridField flgIncludiUoSubordinateField;
	private ListGridField flgIncludiPostazioniSubordinateField;

	public VariazioniDatiUoSvList(String nomeEntita, String flgUoSv) {

		super(nomeEntita);
		
		idUoSvField = new ListGridField("idUoSv");
		idUoSvField.setHidden(true);
		idUoSvField.setCanHide(false);

		flgUoSvField = new ListGridField("flgUoSv");
		flgUoSvField.setHidden(true);
		flgUoSvField.setCanHide(false);

		dataVariazioneField = new ListGridField("dataVariazione", "Variazione del");
		dataVariazioneField.setType(ListGridFieldType.DATE);
		dataVariazioneField.setDateFormatter(DateDisplayFormat.TOEUROPEANSHORTDATE);
		
		codiceUoField = new ListGridField("codiceUo", "Codice UO");
		
		denominazioneUoField = new ListGridField("denominazioneUo", "Denominazione UO");
		
		if(flgUoSv != null && "SV".equals(flgUoSv)) {
		
			utenteField = new ListGridField("utente", "Utente");
			
			nomePostazioneField = new ListGridField("nomePostazione", "Nome postazione");
			
			ruoloUtenteInUoField = new ListGridField("ruoloUtenteInUo", "Ruolo utente in UO");
			
			flgIncludiUoSubordinateField = new ListGridField("flgIncludiUoSubordinate", "Incl. UO subordinate");
			flgIncludiUoSubordinateField.setType(ListGridFieldType.ICON);
			flgIncludiUoSubordinateField.setWidth(30);
			flgIncludiUoSubordinateField.setIconWidth(16);
			flgIncludiUoSubordinateField.setIconHeight(16);
			Map<String, String> flgIncludiUoSubordinateValueIcons = new HashMap<String, String>();		
			flgIncludiUoSubordinateValueIcons.put("1", "ok.png");
			flgIncludiUoSubordinateValueIcons.put("0", "blank.png");
			flgIncludiUoSubordinateValueIcons.put("", "blank.png");
			flgIncludiUoSubordinateField.setValueIcons(flgIncludiUoSubordinateValueIcons);
			flgIncludiUoSubordinateField.setHoverCustomizer(new HoverCustomizer() {
	
				@Override
				public String hoverHTML(Object value, ListGridRecord record, int rowNum, int colNum) {
					if("1".equals(record.getAttribute("flgIncludiUoSubordinate"))) {
						return "Incl. UO subordinate";
					}
					return null;
				}
			});
			
			flgIncludiPostazioniSubordinateField = new ListGridField("flgIncludiPostazioniSubordinate", "Incl. postazioni subordinate");
			flgIncludiPostazioniSubordinateField.setType(ListGridFieldType.ICON);
			flgIncludiPostazioniSubordinateField.setWidth(30);
			flgIncludiPostazioniSubordinateField.setIconWidth(16);
			flgIncludiPostazioniSubordinateField.setIconHeight(16);
			Map<String, String> flgIncludiPostazioniSubordinateValueIcons = new HashMap<String, String>();		
			flgIncludiPostazioniSubordinateValueIcons.put("1", "ok.png");
			flgIncludiPostazioniSubordinateValueIcons.put("0", "blank.png");
			flgIncludiPostazioniSubordinateValueIcons.put("", "blank.png");
			flgIncludiPostazioniSubordinateField.setValueIcons(flgIncludiPostazioniSubordinateValueIcons);
			flgIncludiPostazioniSubordinateField.setHoverCustomizer(new HoverCustomizer() {
	
				@Override
				public String hoverHTML(Object value, ListGridRecord record, int rowNum, int colNum) {
					if("1".equals(record.getAttribute("flgIncludiPostazioniSubordinate"))) {
						return "Incl. postazioni subordinate";
					}
					return null;
				}
			});
			
			setFields(new ListGridField[] { 
				idUoSvField, 
				flgUoSvField, 
				dataVariazioneField, 
				codiceUoField, 
				denominazioneUoField, 
				utenteField, 
				nomePostazioneField, 
				ruoloUtenteInUoField, 
				flgIncludiUoSubordinateField, 
				flgIncludiPostazioniSubordinateField 
			});
		} else {
			setFields(new ListGridField[] { 
					idUoSvField, 
					flgUoSvField, 
					dataVariazioneField,
					codiceUoField, 
					denominazioneUoField
				});
		}

	}

	@Override
	protected int getButtonsFieldWidth() {
		return 25;
	}

	@Override
	public void manageLoadDetail(final Record record, final int recordNum, final DSCallback callback) {
		
		String idUoSv = record.getAttribute("idUoSv");
		String flgUoSv = record.getAttribute("flgUoSv");
		
		Record lRecordToLoad = new Record();
		lRecordToLoad.setAttribute("idUoSvUt", idUoSv);
		
		if(flgUoSv != null && "SV".equals(flgUoSv)) {	
			final GWTRestDataSource lGwtRestDataSource = new GWTRestDataSource("PostazioneDatasource", "ciRelUserUo", FieldType.TEXT);
			layout.changeDetail(lGwtRestDataSource, new PostazioneDetail("organigramma", false));
			layout.getDetail().getDataSource().performCustomOperation("get", record, new DSCallback() {

				@Override
				public void execute(final DSResponse response, Object rawData, DSRequest request) {
					if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
						final Record detailRecord = response.getData()[0];
						layout.getDetail().editRecord(detailRecord, recordNum);				
						layout.getDetail().getValuesManager().clearErrors(true);
						callback.execute(response, null, new DSRequest());
					}
				}
			}, new DSRequest());			
		} else {
			final GWTRestDataSource lGwtRestDataSource = new GWTRestDataSource("OrganigrammaDataSource", "idUoSvUt", FieldType.TEXT);
			layout.changeDetail(lGwtRestDataSource, new OrganigrammaDetail("organigramma"));
			layout.getDetail().getDataSource().performCustomOperation("get", record, new DSCallback() {

				@Override
				public void execute(final DSResponse response, Object rawData, DSRequest request) {
					if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
						final Record detailRecord = response.getData()[0];
						layout.getDetail().editRecord(detailRecord, recordNum);
						layout.getDetail().getValuesManager().clearErrors(true);
						callback.execute(new DSResponse(), null, new DSRequest());
					}
				}
			}, new DSRequest());
		}
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
		if(layout.getDetail() instanceof PostazioneDetail) {
			return "Visualizza dati della postazione prima della variazione";
		} else if(layout.getDetail() instanceof OrganigrammaDetail) {
			return "Visualizza dati della UO prima della variazione";
		}
		return "Visualizza dati prima della variazione";
	}

	/******************************** NUOVA GESTIONE CONTROLLI BOTTONI ********************************/
	@Override
	public boolean isDisableRecordComponent() {
		return true;
	};

	@Override
	protected boolean showDetailButtonField() {
		return false;
	}
	/******************************** FINE NUOVA GESTIONE CONTROLLI BOTTONI ********************************/
}