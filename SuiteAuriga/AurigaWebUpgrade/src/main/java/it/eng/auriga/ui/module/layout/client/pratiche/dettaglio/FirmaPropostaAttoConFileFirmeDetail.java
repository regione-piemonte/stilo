/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import com.smartgwt.client.data.Record;
import com.smartgwt.client.data.RecordList;
import com.smartgwt.client.util.JSON;

import it.eng.auriga.ui.module.layout.client.AurigaLayout;
import it.eng.auriga.ui.module.layout.client.FirmaUtility;
import it.eng.auriga.ui.module.layout.client.FirmaUtility.FirmaCallback;
import it.eng.auriga.ui.module.layout.client.pratiche.DettaglioPraticaLayout;
import it.eng.auriga.ui.module.layout.client.protocollazione.AllegatiItem;
import it.eng.utility.ui.module.core.client.callback.ServiceCallback;
import it.eng.utility.ui.module.core.client.datasource.GWTRestService;
import it.eng.utility.ui.module.layout.client.common.file.InfoFileRecord;

public class FirmaPropostaAttoConFileFirmeDetail extends FirmaPropostaAttoDetail {
	
	protected FirmaPropostaAttoConFileFirmeDetail instance;
	
	private String idModello;
	private String nomeModello;
	private String uriModello;
	private String tipoModello;
	private String nomeFileModello;
	
	public FirmaPropostaAttoConFileFirmeDetail(String nomeEntita, String idProcess, String nomeFlussoWF, String processNameWF, String idUd, Record lRecordEvento, DettaglioPraticaLayout dettaglioPraticaLayout) {
		
		super(nomeEntita, idProcess, nomeFlussoWF, processNameWF, idUd, lRecordEvento, dettaglioPraticaLayout);
		
		instance = this;
		
		idModello = recordEvento != null ? recordEvento.getAttribute("idModAssDocTask") : null;
		nomeModello = recordEvento != null ? recordEvento.getAttribute("nomeModAssDocTask") : null;
		uriModello = recordEvento != null ? recordEvento.getAttribute("uriModAssDocTask") : null;
		tipoModello = recordEvento != null ? recordEvento.getAttribute("tipoModAssDocTask") : null;
		nomeFileModello = nomeModello != null ? nomeModello.replaceAll(" ", "_") + ".pdf" : null;
		
	}
	
	public void firmaAndReturn(){
		compilazioneAutomaticaModelloPdf(new ServiceCallback<Record>() {										
			@Override
			public void execute(Record object) {
				
				salvaDati(false, new ServiceCallback<Record>() {
					@Override
					public void execute(Record object) {		
						callbackSalvaDati(object);								
					}
				});
			}
		});
	}
	
	public void compilazioneAutomaticaModelloPdf(final ServiceCallback<Record> callback) {
		Record lRecordCompilaModello = new Record();
		lRecordCompilaModello.setAttribute("processId", idProcess);
		lRecordCompilaModello.setAttribute("idUd", idUd);
		lRecordCompilaModello.setAttribute("idModello", idModello);
		lRecordCompilaModello.setAttribute("nomeModello", nomeModello);
		lRecordCompilaModello.setAttribute("uriModello", uriModello);
		lRecordCompilaModello.setAttribute("tipoModello", tipoModello);
		lRecordCompilaModello.setAttribute("nomeFile", nomeFileModello);
		lRecordCompilaModello.setAttribute("valori", getAttributiDinamiciDoc());
		lRecordCompilaModello.setAttribute("tipiValori", getTipiAttributiDinamiciDoc());

		GWTRestService<Record, Record> compilaModelloRestService = new GWTRestService<Record, Record>("CompilaModelloDatasource");					
		compilaModelloRestService.call(lRecordCompilaModello, new ServiceCallback<Record>() {				
			@Override
			public void execute(Record result) {							
				String uri = result.getAttribute("uri");							
				InfoFileRecord infoFile = InfoFileRecord.buildInfoFileString(JSON.encode(result.getAttributeAsRecord("infoFile").getJsObj()));
				// Leggo gli eventuali parametri per forzare il tipo d firma
				String appletTipoFirmaAtti = AurigaLayout.getParametroDB("APPLET_TIPO_FIRMA_ATTI");
				String hsmTipoFirmaAtti = AurigaLayout.getParametroDB("HSM_TIPO_FIRMA_ATTI");
				FirmaUtility.firmaMultipla(true, false, appletTipoFirmaAtti, hsmTipoFirmaAtti, uri, nomeFileModello, infoFile, new FirmaCallback() {								
					@Override
					public void execute(String nomeFileFirmato, String uriFileFirmato, InfoFileRecord info) {
						aggiungiModelloAdAllegati(uriFileFirmato, nomeFileFirmato, info, callback);
					}
				});							
			}
		});		
	}
	
	/**
	 * Aggiunge il modello in cui sono stati iniettati i dati alla lista degli allegati
	 * @param uriFileAllegato
	 * @param nomeFileAllegato
	 * @param infoAllegato
	 * @param callback
	 */
	protected void aggiungiModelloAdAllegati(String uriFileAllegato, String nomeFileAllegato, InfoFileRecord infoAllegato, ServiceCallback<Record> callback) {		
		if(fileAllegatiForm != null) {			
			RecordList allegati = fileAllegatiForm.getValuesAsRecord().getAttributeAsRecordList("listaAllegati");
			if(allegati == null || allegati.getLength() == 0) { 
				allegati = new RecordList();	
			}			
			Record lRecordModello = new Record();
			lRecordModello.setAttribute("nomeFileAllegato", nomeFileAllegato);
			lRecordModello.setAttribute("uriFileAllegato", uriFileAllegato);					
			lRecordModello.setAttribute("descrizioneFileAllegato",  nomeModello);																		
			lRecordModello.setAttribute("nomeFileAllegatoTif", "");
			lRecordModello.setAttribute("uriFileAllegatoTif", "");				
			lRecordModello.setAttribute("remoteUri", false);				
			lRecordModello.setAttribute("nomeFileVerPreFirma", nomeFileAllegato);
			lRecordModello.setAttribute("uriFileVerPreFirma", uriFileAllegato);				
			lRecordModello.setAttribute("infoFile", infoAllegato);
			lRecordModello.setAttribute("improntaVerPreFirma",  infoAllegato.getImpronta());
			lRecordModello.setAttribute("flgParteDispositivo",  true);
			allegati.addAt(lRecordModello, 0);
			Record lRecordForm = new Record();
			lRecordForm.setAttribute("listaAllegati", allegati);
			fileAllegatiForm.setValues(lRecordForm.toMap());
			if(fileAllegatiItem != null && (fileAllegatiItem instanceof AllegatiItem)) {
				((AllegatiItem)fileAllegatiItem).resetCanvasChanged();
			}
			if(detailSectionAllegati != null) {
				detailSectionAllegati.openIfhasValue();
			}							
		}		
		if(callback != null) {
			callback.execute(new Record());
		}		
	}
	
}
