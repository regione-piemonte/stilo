/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.auriga.ui.module.layout.client.archivio.ArchivioLayout;
import it.eng.auriga.ui.module.layout.client.archivio.FolderCustomDetail;
import it.eng.utility.ui.module.core.client.datasource.GWTRestDataSource;
import it.eng.utility.ui.module.layout.client.common.CustomLayout;
import it.eng.utility.ui.module.layout.client.portal.ModalWindow;

import com.smartgwt.client.data.DSCallback;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.data.RecordList;
import com.smartgwt.client.types.FieldType;

public class FascicoloProcedimentoWindow extends ModalWindow {

	private FascicoloProcedimentoWindow _window;

	private ArchivioLayout portletLayout;
	
	boolean isFirstTime = true;

	public FascicoloProcedimentoWindow(final Record record) {

		super("archivio", true);

		final String idFolder = record.getAttributeAsString("idFolder");
		final String idDetail = record.getAttribute("idDetail");

		setTitle("Fascicolo del procedimento");

		_window = this;
		
		final GWTRestDataSource lGwtRestDataSource = new GWTRestDataSource("ArchivioDatasource", "idUdFolder", FieldType.TEXT);		

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
							esploraFromListFirstTime(idFolder);				    		
						} 				
					}
				}, new DSRequest());					
			}
						
			public void esploraFromListFirstTime(String idFolder) {
				isFirstTime = true;
				if(idFolder != null) {
					if(!idFolder.equals(navigator.getCurrentNode().getIdFolder())) {
						salvaLivelloCorrente();
					}
					aggiornaPercorsoFromList(idFolder, new DSCallback() {			
						@Override
						public void execute(DSResponse response, Object rawData, DSRequest request) {
							
							viewDetail(lGwtRestDataSource, idDetail);
						}
					});								
				}				
			}	
		};	
		
		portletLayout.changeDetail(lGwtRestDataSource, new FolderCustomDetail("archivio"));
		portletLayout.setLookup(false);

		portletLayout.setHeight100();
		portletLayout.setWidth100();

		setBody(portletLayout);

		setIcon("menu/archivio.png");            
	}	
	
	@Override
	public void manageOnCloseClick() {
		
		if(body instanceof ArchivioLayout && ((ArchivioLayout)body).getMode() != null && isFirstTime) {
			isFirstTime = false;
			((ArchivioLayout)body).esploraMode(null);
			((CustomLayout)body).hideDetail(true);
		}
		else super.manageOnCloseClick();
	}
	
	public void viewDetail(GWTRestDataSource lGwtRestDataSource, String idDetail) {
		Record lRecordDetail = new Record();
		lRecordDetail.setAttribute("idUdFolder", idDetail);
		lGwtRestDataSource.getData(lRecordDetail, new DSCallback() {							
			@Override
			public void execute(DSResponse response, Object rawData, DSRequest request) {
				if(response.getStatus() == DSResponse.STATUS_SUCCESS) {						
					Record record = response.getData()[0];					
					portletLayout.getDetail().editRecord(record);	
					portletLayout.viewMode();
				} 				
			}
		});	
	}

}
