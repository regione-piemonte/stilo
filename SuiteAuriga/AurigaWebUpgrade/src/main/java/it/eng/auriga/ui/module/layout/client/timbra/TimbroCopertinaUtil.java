/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import com.smartgwt.client.data.DSCallback;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.data.Record;

import it.eng.auriga.ui.module.layout.client.print.PreviewControl;
import it.eng.auriga.ui.module.layout.client.protocollazione.VisualizzaFatturaWindow;
import it.eng.utility.ui.module.core.client.callback.ServiceCallback;
import it.eng.utility.ui.module.core.client.datasource.GWTRestService;
import it.eng.utility.ui.module.core.shared.message.MessageBean;
import it.eng.utility.ui.module.core.shared.message.MessageType;
import it.eng.utility.ui.module.layout.client.Layout;
import it.eng.utility.ui.module.layout.client.common.file.InfoFileRecord;

/**
 * 
 * @author DANCRIST
 *
 */

public class TimbroCopertinaUtil {
	
	
	/**
	 * GENERAZIONE BARCODE CON SEGNATURA
	 * PRATICA PREGRESSO: DmpkRegistrazionedocGetbarcodedacapofilapratica
	 * PROTOCOLLAZIONE:  DmpkRegistrazionedocGettimbrodigreg
	 */
	public static void buildDatiSegnatura(Record lRecord) {
		Layout.showWaitPopup("Generazione copertina in corso: potrebbe richiedere qualche secondo. Attendere...");
		
		GWTRestService<Record, Record> lGwtRestService = new GWTRestService<Record, Record>("CopertinaTimbroDataSource");
		lGwtRestService.performCustomOperation("getDatiSegnatura", lRecord, new DSCallback() {
			
			@Override
			public void execute(DSResponse response, Object rawData, DSRequest request) {
				if(response.getStatus() == DSResponse.STATUS_SUCCESS) {	
					Record result = response.getData()[0];
					result.setAttribute("skipScelteOpzioniCopertina", "true");
					generaCopertina(result);
				}else{
					Layout.addMessage(new MessageBean("Non è stato possibile apporre la copertina sulla segnatura","",MessageType.WARNING));
				}
			}
		},new DSRequest());
	}
	
	/**
	 * GENERAZIONE BARCODE CON TIPOLOGIA
	 * PRATICA PREGRESSO: DmpkRegistrazionedocGettimbrospecxtipo: Flgdocfolderin  F
	 * PROTOCOLLAZIONE:  Flgdocfolderin D
	 */
	public static void buildDatiTipologia(Record lRecord) {
		Layout.showWaitPopup("Generazione copertina in corso: potrebbe richiedere qualche secondo. Attendere...");
		
		GWTRestService<Record, Record> lGwtRestService = new GWTRestService<Record, Record>("CopertinaTimbroDataSource");
		lGwtRestService.performCustomOperation("getDatiTipologia", lRecord, new DSCallback() {
			
			@Override
			public void execute(DSResponse response, Object rawData, DSRequest request) {
				if(response.getStatus() == DSResponse.STATUS_SUCCESS) {	
					Record result = response.getData()[0];
					result.setAttribute("skipScelteOpzioniCopertina", "true");
					generaCopertina(result);
				}else{
					Layout.addMessage(new MessageBean("Non è stato possibile apporre la copertina sulla timbratura","",MessageType.WARNING));
				}
			}
		},new DSRequest());
		
	}
	
	private static void generaCopertina(Record input){
		GWTRestService<Record, Record> lGwtRestService = new GWTRestService<Record, Record>("CopertinaTimbroDataSource");
		lGwtRestService.call(input, new ServiceCallback<Record>() {
			@Override
			public void execute(Record object) {
				Layout.hideWaitPopup();
				if (object.getAttributeAsBoolean("result")){								
					copertinaCallBack(object);
				} else {
					Layout.addMessage(new MessageBean(object.getAttribute("error"), object.getAttribute("error"), MessageType.ERROR));
				}
			}
		});
	}
	
	private static void copertinaCallBack(Record object) {
		String uri = object.getAttribute("uri");
		String display = "Copertina.pdf";
		InfoFileRecord infoFile = new InfoFileRecord(object.getAttributeAsRecord("infoFile"));

		managePreviewFile(uri,display,false,infoFile);
	}
	
	public static void managePreviewFile(String uriFile,String nomeFile,Boolean remote,final InfoFileRecord infoFile) {
		
		final String display = nomeFile;
		final String uri = uriFile;
		final Boolean remoteUri = remote;
		if (infoFile != null && infoFile.getMimetype() != null && infoFile.getMimetype().equals("application/xml")) {
			Record lRecordFattura = new Record();
			lRecordFattura.setAttribute("uriFile", uri);
			lRecordFattura.setAttribute("remoteUri", remoteUri);
			GWTRestService<Record, Record> lGwtRestService = new GWTRestService<Record, Record>("VisualizzaFatturaDataSource");
			if (infoFile != null && infoFile.isFirmato() && infoFile.getTipoFirma().startsWith("CAdES")) {
				lGwtRestService.addParam("sbustato", "true");
			} else {
				lGwtRestService.addParam("sbustato", "false");
			}
			lGwtRestService.call(lRecordFattura, new ServiceCallback<Record>() {

				@Override
				public void execute(Record object) {

					if (object.getAttribute("html") != null && !"".equals(object.getAttribute("html"))) {
						VisualizzaFatturaWindow lVisualizzaFatturaWindow = new VisualizzaFatturaWindow(display, object);
						lVisualizzaFatturaWindow.show();
					} else {
						preview(display, uri, remoteUri, infoFile);
					}
				}
			});
		} else {
			preview(display, uri, remoteUri, infoFile);
		}
	}
	
	public static void preview(final String display, final String uri, final Boolean remoteUri, InfoFileRecord info) {
		PreviewControl.switchPreview(uri, remoteUri, info, "FileToExtractBean", display);
	}

}
