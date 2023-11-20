/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.utility.ui.module.layout.client.portal.ModalWindow;

import com.smartgwt.client.data.DSCallback;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.data.RecordList;

public class ContenutiFascicoloPopup extends ModalWindow {
	
	private ContenutiFascicoloPopup _window;
	
	private ArchivioLayout portletLayout;
	
	public ContenutiFascicoloPopup(final Record record) {
		
		super("archivio", true);
		
		final String idFolder = record.getAttributeAsString("idUdFolder");
		
		if(record.getAttributeAsString("provenienza") != null && ("SUE".equalsIgnoreCase(record.getAttributeAsString("provenienza"))
				|| "ORGANI_COLLEGIALI".equalsIgnoreCase(record.getAttributeAsString("provenienza")))) {
			setTitle("Contenuti fascicolo");
		} else {
			if(record.getAttributeAsString("nroInserto") != null && !"".equals(record.getAttributeAsString("nroInserto"))) {
				setTitle("Contenuti inserto " + record.getAttribute("segnatura") + " " + record.getAttribute("nome"));
			} else if(record.getAttributeAsString("nroSottofascicolo") != null && !"".equals(record.getAttributeAsString("nroSottofascicolo"))) {
				setTitle("Contenuti sotto-fascicolo " + record.getAttribute("segnatura") + " " + record.getAttribute("nome"));
			} else {
				setTitle("Contenuti fascicolo " + record.getAttribute("segnatura") + " " + record.getAttribute("nome"));
			}
		}

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