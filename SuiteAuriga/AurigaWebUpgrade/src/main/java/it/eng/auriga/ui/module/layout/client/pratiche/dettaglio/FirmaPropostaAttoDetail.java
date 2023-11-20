/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.Map;

import com.smartgwt.client.data.DSCallback;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.data.Record;

import it.eng.auriga.ui.module.layout.client.AurigaLayout;
import it.eng.auriga.ui.module.layout.client.FirmaUtility;
import it.eng.auriga.ui.module.layout.client.FirmaUtility.FirmaMultiplaCallback;
import it.eng.auriga.ui.module.layout.client.pratiche.DettaglioPraticaLayout;
import it.eng.utility.ui.module.core.client.callback.ServiceCallback;
import it.eng.utility.ui.module.core.client.datasource.GWTRestService;
import it.eng.utility.ui.module.core.shared.message.MessageBean;
import it.eng.utility.ui.module.core.shared.message.MessageType;
import it.eng.utility.ui.module.layout.client.Layout;

public class FirmaPropostaAttoDetail extends PropostaAttoDetail {
	
	protected FirmaPropostaAttoDetail instance;
	
	protected String tipoModAssDocTask;
	
//	private String nomeButtonSalvaDefinitivoAfterModifica = "Procedi: re-invio al proponente";
//	private boolean flgModificato = false;
	
	public FirmaPropostaAttoDetail(String nomeEntita, String idProcess, String nomeFlussoWF, String processNameWF, String idUd, Record lRecordEvento, DettaglioPraticaLayout dettaglioPraticaLayout) {
		
		super(nomeEntita, idProcess, nomeFlussoWF, processNameWF, idUd, lRecordEvento, dettaglioPraticaLayout);
		
		instance = this;
		
		this.tipoModAssDocTask = lRecordEvento != null ? lRecordEvento.getAttribute("tipoModAssDocTask") : null;
		
	}
	
//	@Override
//	public Record getRecordToSave(String motivoVarDatiReg) {
//		
//		Record lRecordToSave = super.getRecordToSave(motivoVarDatiReg);
//		lRecordToSave.setAttribute("modificatoDispositivoAtto", flgModificato ? "1" : "0");		
//		return lRecordToSave;
//	}
	
//	@Override
//	public void manageChangedDocPrimario() {
//		
//		super.manageChangedDocPrimario();
//		flgModificato = true;
//		dettaglioPraticaLayout.getButtonSalvaDefinitivo().setTitle(nomeButtonSalvaDefinitivoAfterModifica);
//	}
	
//	@Override
//	public void manageChangedAllegatiDaFirmare() {
//		
//		super.manageChangedAllegatiDaFirmare();
//		flgModificato = true;
//		dettaglioPraticaLayout.getButtonSalvaDefinitivo().setTitle(nomeButtonSalvaDefinitivoAfterModifica);
//	}
			
	@Override
	public void saveAndGo() {
		if(isEsitoTaskOk(attrEsito) /*&& !dettaglioPraticaLayout.getButtonSalvaDefinitivo().getTitle().equals(nomeButtonSalvaDefinitivoAfterModifica)*/) {
			salvaDati(true, new ServiceCallback<Record>() {			
				@Override
				public void execute(Record object) {
					idEvento = object.getAttribute("idEvento");								
					Layout.hideWaitPopup();
					loadDettUd(new ServiceCallback<Record>() {
						
						@Override
						public void execute(Record dett) {
							firmaAndReturn();
						}
					});
				}
			});		
		} else {
			super.saveAndGo();
		}
	}
	
	public void firmaAndReturn(){
		Record lRecord = getRecordToSave(null);
		lRecord.setAttribute("tipoModFilePrimario", tipoModAssDocTask);		
		GWTRestService<Record, Record> lGwtRestService = new GWTRestService<Record, Record>("FirmaDirigenteDatasource");
		lGwtRestService.executecustom("getFileDaFirmare", lRecord, new DSCallback() {
			@Override
			public void execute(DSResponse response, Object rawData, DSRequest request) {
				callbackGetFileDaFirmare(response);
			}
		});
	}

	protected void callbackGetFileDaFirmare(DSResponse response) {
		if (response.getStatus() == DSResponse.STATUS_SUCCESS){
			Record lRecord = response.getData()[0];
			Record[] files = lRecord.getAttributeAsRecordArray("files");
			if(files != null && files.length > 0) {
				// Leggo gli eventuali parametri per forzare il tipo d firma
				String appletTipoFirmaAtti = AurigaLayout.getParametroDB("APPLET_TIPO_FIRMA_ATTI");
				String hsmTipoFirmaAtti = AurigaLayout.getParametroDB("HSM_TIPO_FIRMA_ATTI");
				FirmaUtility.firmaMultipla(true, appletTipoFirmaAtti, hsmTipoFirmaAtti, files, new FirmaMultiplaCallback() {
					@Override
					public void execute(Map<String, Record> files, Record[] filesAndUd) {
						callbackFirmaMultipla(files, filesAndUd);
					}
				});
			} else {
				Layout.addMessage(new MessageBean("Non è stato firmato nessun file", "", MessageType.WARNING));
				super.saveAndGo();
			}
		}
		
	}

	protected void callbackFirmaMultipla(Map<String, Record> files,
			Record[] filesAndUd) {
		
		Record lRecordConModifiche = new Record();
		Record lRecordProtocolloOriginale = getRecordToSave(null);
		lRecordConModifiche.setAttribute("protocolloOriginale", lRecordProtocolloOriginale);
		Record lRecordFileFirmati = new Record();
		Record[] lRecordModificati = files.values().toArray(new Record[]{});
		lRecordFileFirmati.setAttribute("files", lRecordModificati);
		lRecordConModifiche.setAttribute("fileFirmati", lRecordFileFirmati);
		GWTRestService<Record, Record> lGwtRestService = new GWTRestService<Record, Record>("FirmaDirigenteDatasource");
		lGwtRestService.executecustom("aggiornaFileFirmati", lRecordConModifiche, new DSCallback() {
			@Override
			public void execute(DSResponse response, Object rawData, DSRequest request) {
				callbackAggiornaFileFirmati(response);
			}
		});
	
	}

	protected void callbackAggiornaFileFirmati(DSResponse response) {
		if (response.getStatus() == DSResponse.STATUS_SUCCESS){
			Record lRecord = response.getData()[0];
			caricaDettaglio(null, lRecord);
			setCanEdit(true);	
			salvaDati(false, new ServiceCallback<Record>() {
				@Override
				public void execute(Record object) {		
					callbackSalvaDati(object);											
				}
			});	
		}
	}
	
}
