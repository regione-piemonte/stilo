/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.auriga.ui.module.layout.client.gestioneUtenti.GestioneUtentiLayout;
import it.eng.auriga.ui.module.layout.client.i18n.I18NUtil;
import it.eng.utility.ui.module.layout.client.common.CustomList;

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

public class ContrattiList extends CustomList{

	private ListGridField idUd;
	private ListGridField estremiProtocollo;
	private ListGridField dataProtocollo;				
	private ListGridField dataStipulaContratto;
	private ListGridField nroContratto;
	private ListGridField oggetto;
	private ListGridField contraente;
	private ListGridField pIvaCfContraente;
	private ListGridField statoConservazione;
	private ListGridField invioInConservazione;
	
	public ContrattiList(String nomeEntita) {
		
		super(nomeEntita);

		idUd = new ListGridField("idUd", "Id.");
		idUd.setHidden(true);
		idUd.setCanHide(false);
		
		invioInConservazione 	= new ListGridField("invioInConservazione", "flag invio in conservazione"); invioInConservazione.setHidden(true);invioInConservazione.setCanHide(false);		
		estremiProtocollo 		= new ListGridField("estremiProtocollo", "Estremi prot.");
		dataProtocollo 			= new ListGridField("dataProtocollo", "Data prot.");	dataProtocollo.setType(ListGridFieldType.DATE);	dataProtocollo.setDateFormatter(DateDisplayFormat.TOEUROPEANSHORTDATE);
		dataStipulaContratto 	= new ListGridField("dataStipulaContratto", "Data stipula");	dataStipulaContratto.setType(ListGridFieldType.DATE);	dataStipulaContratto.setDateFormatter(DateDisplayFormat.TOEUROPEANSHORTDATE);
		nroContratto 			= new ListGridField("nroContratto", "Contratto N°");		
		oggetto 				= new ListGridField("oggetto", "Oggetto");		
		contraente 				= new ListGridField("contraente", "Contraente");		
		pIvaCfContraente 		= new ListGridField("pIvaCfContraente", "Cod. fiscale/P.IVA contraente");		
		statoConservazione 		= new ListGridField("statoConservazione", I18NUtil.getMessages().archivio_contratto_stato_conservazione());
		
		
		setFields(
				idUd,
				invioInConservazione,
				estremiProtocollo,
				dataProtocollo,				
				dataStipulaContratto,
				nroContratto,
				oggetto,
				statoConservazione,
				contraente,
				pIvaCfContraente);
	}
	    
	@Override  
	public void manageLoadDetail(final Record record, final int recordNum, final DSCallback callback) {
		getDataSource().performCustomOperation("get", record, new DSCallback() {							
			@Override
			public void execute(DSResponse response, Object rawData, DSRequest request) {
				if(response.getStatus() == DSResponse.STATUS_SUCCESS) {
					Record record = response.getData()[0];
					layout.getDetail().editRecord(record, recordNum);	
					layout.getDetail().getValuesManager().clearErrors(true);
					callback.execute(response, null, new DSRequest());
				} 				
			}
		}, new DSRequest());	
	}
	
	@Override  
	protected Canvas createFieldCanvas(String fieldName, final ListGridRecord record) {  

		Canvas lCanvasReturn = null;

		if (fieldName.equals("buttons")) {	

			ImgButton detailButton = buildDetailButton(record);  
			ImgButton modifyButton = buildModifyButton(record);
			ImgButton deleteButton = buildDeleteButton(record);			

			if(!isRecordAbilToMod(record)) {	
				modifyButton.disable();	
			}			

			if(!isRecordAbilToDel(record)) {	
				deleteButton.disable();			
			}			

			// creo la colonna BUTTON
			HLayout recordCanvas = new HLayout(3);
			recordCanvas.setHeight(22);   
			recordCanvas.setWidth(getButtonsFieldWidth());
			recordCanvas.setAlign(Alignment.RIGHT);
			recordCanvas.setLayoutRightMargin(3);   
			recordCanvas.setMembersMargin(7);

			recordCanvas.addMember(detailButton);			
			recordCanvas.addMember(modifyButton);
			recordCanvas.addMember(deleteButton);					

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
	protected boolean showModifyButtonField() {
		
		return ContrattiLayout.isAbilToMod();
	}

	@Override
	protected boolean showDeleteButtonField() {
		return ContrattiLayout.isAbilToDel();
	}	
	
	@Override
	protected boolean isRecordAbilToMod(ListGridRecord record) {
		final boolean flgDiSistema  = record.getAttribute("flgDiSistema") != null && record.getAttributeAsString("flgDiSistema").equals("1");
		
		
		final boolean invioInConservazione  = record.getAttribute("invioInConservazione") != null && record.getAttributeAsBoolean("invioInConservazione");
		
		return ContrattiLayout.isRecordAbilToMod(flgDiSistema, invioInConservazione);
	}

	@Override
	protected boolean isRecordAbilToDel(ListGridRecord record) {
		final boolean flgDiSistema  = record.getAttribute("flgDiSistema") != null && record.getAttributeAsString("flgDiSistema").equals("1");
		final boolean flgValido  = record.getAttribute("flgValido") != null && record.getAttributeAsString("flgValido").equals("1");
		return ContrattiLayout.isRecordAbilToDel(flgValido, flgDiSistema);
	}
	
    /********************************FINE NUOVA GESTIONE CONTROLLI BOTTONI********************************/
}