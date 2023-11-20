/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.auriga.ui.module.layout.client.archivio.ArchivioLayout;
import it.eng.utility.ui.module.layout.client.portal.ModalWindow;

import com.smartgwt.client.data.DSCallback;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.data.RecordList;

public class FolderProcedimentoPopup extends ModalWindow {
	
	private FolderProcedimentoPopup _window;
	
	private ArchivioLayout portletLayout;
	
	public FolderProcedimentoPopup(final Record record) {
		
		super("archivio", true);
		
		final String idFolder = record.getAttributeAsString("idUdFolder");
		
		final String estremiProcedimento = record.getAttributeAsString("estremiProcedimento");
		
		setTitle("Documenti " + estremiProcedimento);
		

		_window = this;
		
		portletLayout = new ArchivioLayout(null, null, null, null) {
			@Override  
			public void setPercorsoIniziale() {		
				tree.getDataSource().performCustomOperation("getPercorsoIniziale", new Record(), new DSCallback() {							
					@Override
					public void execute(DSResponse response, Object rawData, DSRequest request) {
						if(response.getStatus() == DSResponse.STATUS_SUCCESS) {
							Record record = response.getData()[0];
							RecordList percorso = record.getAttributeAsRecordList("percorso");
				    		navigator.setPercorso(percorso);	
				    		navigator.setFlgMostraContenuti(record.getAttributeAsBoolean("flgMostraContenuti"));
				    		flgMostraContenuti = navigator.getFlgMostraContenuti();
				    		altriParametri = record.getAttributeAsMap("altriParametri");
				    		esploraFromList(idFolder);				    		
						} 				
					}
				}, new DSRequest());					
			}
		};			
		portletLayout.setLookup(false);
		
		portletLayout.setHeight100();
		portletLayout.setWidth100();
		
		setBody(portletLayout);
   
        setIcon("menu/archivio.png");            
	}	
	
}