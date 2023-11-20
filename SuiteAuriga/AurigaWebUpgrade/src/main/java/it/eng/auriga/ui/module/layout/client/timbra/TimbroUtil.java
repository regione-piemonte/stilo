/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import com.smartgwt.client.data.DSCallback;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.types.FieldType;
import com.smartgwt.client.widgets.Button;
import com.smartgwt.client.widgets.Dialog;

import it.eng.utility.ui.module.core.client.callback.ServiceCallback;
import it.eng.utility.ui.module.core.client.datasource.GWTRestDataSource;
import it.eng.utility.ui.module.core.client.datasource.GWTRestService;
import it.eng.utility.ui.module.core.shared.message.MessageBean;
import it.eng.utility.ui.module.core.shared.message.MessageType;
import it.eng.utility.ui.module.layout.client.Layout;
import it.eng.utility.ui.module.layout.client.common.file.DownloadFile;

/**
 * 
 * @author DANCRIST
 *
 */

public class TimbroUtil {
	
	public static void buildTimbro(Record lRecord) {
		Layout.showWaitPopup("Timbratura in corso: potrebbe richiedere qualche secondo. Attendere...");
		
		GWTRestService<Record, Record> lGwtRestService = new GWTRestService<Record, Record>("TimbraDatasource");
		lGwtRestService.performCustomOperation("getTipologia", lRecord, new DSCallback() {
			
			@Override
			public void execute(DSResponse response, Object rawData, DSRequest request) {
				if(response.getStatus() == DSResponse.STATUS_SUCCESS) {			
					Record result = response.getData()[0];
					result.setAttribute("skipScelteOpzioniCopertina", "true");
					generaTimbro(result);
				}else{
					Layout.addMessage(new MessageBean("Non è stato possibile apporre la timbratura","",MessageType.WARNING));
				}
			}
				
		},new DSRequest());
	}
	
	public static void buildDatiSegnatura(Record lRecord) {
		Layout.showWaitPopup("Operazione in corso: potrebbe richiedere qualche secondo. Attendere...");
		
		GWTRestService<Record, Record> lGwtRestService = new GWTRestService<Record, Record>("TimbraDatasource");
		lGwtRestService.performCustomOperation("getSegnatura", lRecord, new DSCallback() {
			
			@Override
			public void execute(DSResponse response, Object rawData, DSRequest request) {
				if(response.getStatus() == DSResponse.STATUS_SUCCESS) {				
					Record result = response.getData()[0];
					generaTimbro(result);
				}else{
					Layout.addMessage(new MessageBean("Non è stato possibile apporre la segnatura","",MessageType.WARNING));
				}
			}
				
		},new DSRequest());
	}
	
	public static void generaTimbro(Record input){
		GWTRestService<Record, Record> lGwtRestService = new GWTRestService<Record, Record>("TimbraDatasource");
		lGwtRestService.call(input, new ServiceCallback<Record>() {
			@Override
			public void execute(Record object) {
				Layout.hideWaitPopup();
				if (object.getAttributeAsBoolean("result")){	
					timbraCallBack(object);
				} else {
					Layout.addMessage(new MessageBean(object.getAttribute("error"), object.getAttribute("error"), MessageType.ERROR));
				}
			}
		});
	}
	
	
	protected static void timbraCallBack(Record object) {
		String uri = object.getAttribute("uri");
		String display = object.getAttribute("nomeFile");
		Record lRecord = new Record();
		lRecord.setAttribute("displayFilename", display);
		lRecord.setAttribute("uri", uri);
		lRecord.setAttribute("sbustato", "false");
		lRecord.setAttribute("remoteUri", false);					
		DownloadFile.downloadFromRecord(lRecord, "FileToExtractBean");			
	}
	
	public static void callStoreLoadFilePerTimbroConBusta(final Record record) {
		
		final GWTRestDataSource lGwtRestDataSourceArchivio = new GWTRestDataSource("ArchivioDatasource", "idUdFolder", FieldType.TEXT);
		Record recordToDataSource = new Record();
		recordToDataSource.setAttribute("idUdFolder", record.getAttribute("idUd"));
		recordToDataSource.setAttribute("idDocPrimario", record.getAttribute("idDoc"));
		lGwtRestDataSourceArchivio.executecustom("recuperaFilePerTimbroConBusta", recordToDataSource, new DSCallback() {

			@Override
			public void execute(DSResponse response, Object rawData, DSRequest request) {
				if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
					Record resultRecord = response.getData()[0];
					boolean flgVersionePubblicabile = resultRecord.getAttributeAsBoolean("flgVersionePubblicabile");
					if (flgVersionePubblicabile) {						
						createDialogWindowPerCreazioneTipologiaTimbroBusta(record);
					}
					else {
						record.setAttribute("flgVersionePubblicabile", false);
						TimbroUtil.buildDatiSegnatura(record);
					}
				}				
			}
		});
	}
	
	/**
	 * @param record
	 * @throws IllegalStateException
	 */
	public static void createDialogWindowPerCreazioneTipologiaTimbroBusta(final Record record) throws IllegalStateException {
		final Dialog dialog = new Dialog();
		dialog.setMessage("Tipologia busta timbro");
		dialog.setIcon("");
		dialog.setTitle("");
		dialog.setWidth(450);
		dialog.setHeight(150);
		
		Button button1 = new Button("Versione integrale");
		button1.setWidth(180);
		button1.addClickHandler(new com.smartgwt.client.widgets.events.ClickHandler() {

			@Override
			public void onClick(com.smartgwt.client.widgets.events.ClickEvent event) {
				record.setAttribute("flgVersionePubblicabile", false);
				dialog.close();
				TimbroUtil.buildDatiSegnatura(record);
			}
		});
		Button button2 = new Button("Versione per pubblicazione");
		button2.setWidth(180);
		button2.addClickHandler(new com.smartgwt.client.widgets.events.ClickHandler() {

			@Override
			public void onClick(com.smartgwt.client.widgets.events.ClickEvent event) {
				record.setAttribute("flgVersionePubblicabile", true);
				dialog.close();
				TimbroUtil.buildDatiSegnatura(record);
			}
		});
		dialog.setButtons(button1, button2);

		dialog.draw();
	}
	
	
	

}
