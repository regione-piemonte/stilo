/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.auriga.ui.module.layout.client.attributiDinamici.AttributiDinamiciList;
import it.eng.auriga.ui.module.layout.client.i18n.I18NUtil;
import it.eng.utility.ui.module.core.shared.message.MessageBean;
import it.eng.utility.ui.module.core.shared.message.MessageType;
import it.eng.utility.ui.module.layout.client.Layout;
import it.eng.utility.ui.module.layout.client.common.CustomLayout;

import com.smartgwt.client.data.DSCallback;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.data.RecordList;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.util.BooleanCallback;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.ImgButton;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.grid.ListGridRecord;
import com.smartgwt.client.widgets.layout.HLayout;

public abstract class ElenchiAlbiList extends AttributiDinamiciList {
	
//	private ListGridField idElencoAlbo;
//	private ListGridField rowId;
//	private ListGridField denomSogg;
//	private ListGridField idSogg;
//	private ListGridField targaProvLuogo;
//	private ListGridField localitaLuogo;
//	private ListGridField comuneLuogo;
//	private ListGridField indirizzoLuogo;
//	private ListGridField tipoAtt;
//	private ListGridField nroAut;
//	private ListGridField dataRilascioAut;
//	private ListGridField durataAut;

	public ElenchiAlbiList(String nomeEntita, RecordList attributiAdd) {
		
		super(nomeEntita, attributiAdd);
	
//		idElencoAlbo = new ListGridField("idElencoAlbo");
//		idElencoAlbo.setHidden(true);						
//		idElencoAlbo.setCanHide(false);
//		
//		rowId = new ListGridField("rowId");
//		rowId.setHidden(true);						
//		rowId.setCanHide(false);
//		
//		denomSogg = new ListGridField("denomSogg", "Denominazione / Cognome e nome");
//				
//		idSogg = new ListGridField("idSogg", "Id. in repertorio");
//		
//		targaProvLuogo = new ListGridField("targaProvLuogo", "Prov.");
//		
//		localitaLuogo = new ListGridField("localitaLuogo", "Località");
//		
//		comuneLuogo = new ListGridField("comuneLuogo", "Comune");
//		
//		indirizzoLuogo = new ListGridField("indirizzoLuogo", "Indirizzo");
//		
//		tipoAtt = new ListGridField("tipoAtt", "Tipo attività");
//		
//		nroAut = new ListGridField("nroAut", "Autorizzazione");
//		
//		dataRilascioAut = new ListGridField("dataRilascioAut", "Rilasciata il");
//		dataRilascioAut.setType(ListGridFieldType.DATE);
//		dataRilascioAut.setDateFormatter(DateDisplayFormat.TOEUROPEANSHORTDATE);
//		
//		durataAut = new ListGridField("durataAut", "Durata (mesi)");
//		
//		setFields(new ListGridField[] {
//				idElencoAlbo,
//				rowId,
//				denomSogg,
//				idSogg,
//				targaProvLuogo,
//				localitaLuogo,
//				comuneLuogo,
//				indirizzoLuogo,
//				tipoAtt,
//				nroAut,
//				dataRilascioAut,
//				durataAut
//		});  
		
	}
	
	@Override
	public ListGridField[] getOtherHiddenFields() {
		
		ListGridField idElencoAlbo = new ListGridField("idElencoAlbo");
		idElencoAlbo.setHidden(true);						
		idElencoAlbo.setCanHide(false);
		
		return new ListGridField[] { idElencoAlbo };
	}
		
	@Override  
	public void manageLoadDetail(final Record record, final int recordNum, final DSCallback callback) {
		((ElenchiAlbiLayout)layout).loadElenchiAlbiDetail(record, callback);		
	}
	
	// Definisco i bottoni DETTAGLIO/MODIFICA/CANCELLA/SELEZIONA
	@Override  
	protected Canvas createFieldCanvas(String fieldName, final ListGridRecord record) {  
		
		Canvas lCanvasReturn = null;
		
		if (fieldName.equals("buttons")) {	
											
			ImgButton detailButton = buildDetailButton(record);
			ImgButton modifyButton = buildModifyButton(record);
			ImgButton deleteButton = buildDeleteButton(record);
			
			// creo la colonna BUTTON
			HLayout recordCanvas = new HLayout(3);
			recordCanvas.setHeight(22);   
			recordCanvas.setWidth(getButtonsFieldWidth());
			recordCanvas.setAlign(Alignment.RIGHT);
			recordCanvas.setLayoutRightMargin(3);   
			recordCanvas.setMembersMargin(7);
				
			recordCanvas.addMember(detailButton);	
						
			if(isAbilToInsModDel()) {
				recordCanvas.addMember(modifyButton);			
				recordCanvas.addMember(deleteButton);		
			}
			
			lCanvasReturn = recordCanvas;
		
		}			
		
		return lCanvasReturn;
	}
		
	@Override
	protected void manageDeleteButtonClick(final ListGridRecord record) {
		
		SC.ask("Cancellare l'elenco?", new BooleanCallback() {					
			@Override
			public void execute(Boolean value) {
				
				if(value) {		
					removeData(record, new DSCallback() {																
						@Override
						public void execute(DSResponse response,
								Object rawData, DSRequest request) {
							
							if(response.getStatus() == DSResponse.STATUS_SUCCESS) {
								Layout.addMessage(new MessageBean(I18NUtil.getMessages().afterDelete_message(layout.getTipoEstremiRecord(record)), "", MessageType.INFO));
								layout.hideDetail(true);																						
							} else {
								Layout.addMessage(new MessageBean(I18NUtil.getMessages().deleteError_message(layout.getTipoEstremiRecord(record)), "", MessageType.ERROR));										
							}		
						}
					});													
				}
			}
		});               
	}
	
	protected abstract boolean isAbilToInsModDel();	
	
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
	protected boolean showModifyButtonField() {
		return isAbilToInsModDel();
	}
	
	@Override
	protected boolean showDeleteButtonField() {
		return isAbilToInsModDel();
	}	
    /********************************FINE NUOVA GESTIONE CONTROLLI BOTTONI********************************/
}
