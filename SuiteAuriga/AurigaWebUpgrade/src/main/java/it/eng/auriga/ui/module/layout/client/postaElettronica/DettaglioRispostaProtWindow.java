/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import com.smartgwt.client.data.DSCallback;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.data.Record;

import it.eng.auriga.ui.module.layout.client.protocollazione.ProtocollazioneDetail;
import it.eng.auriga.ui.module.layout.client.protocollazione.ProtocollazioneUtil;
import it.eng.utility.ui.module.core.client.datasource.GWTRestDataSource;
import it.eng.utility.ui.module.layout.client.portal.ModalWindow;

public class DettaglioRispostaProtWindow extends ModalWindow {

	private DettaglioRispostaProtWindow _window;

	private ProtocollazioneDetail portletLayout;
	private static String repertorioInUscitaTitle = "Repertorio in uscita";
	private static String repertorioInternoTitle = "Repertorio interno";
	private static String protocollazioneInUscitaTitle = "Protocollazione in uscita";
	private static String protocollazioneInternaTitle = "Protocollazione interna";

	public DettaglioRispostaProtWindow(Record record) {

		super("dettagliorispostaprot", true);
		
		_window = this;

		settingsMenu.removeItem(separatorMenuItem);
		settingsMenu.removeItem(autoSearchMenuItem);
				
		loadGetDatiUDRisposta(record);
		
		setIcon("blank.png");
	}
	
	private void loadGetDatiUDRisposta(final Record record){
		
		GWTRestDataSource lGwtRestDataSource = new GWTRestDataSource("GetDatiUDRispostaDataSource");
		Record lRecordToLoad = new Record();
		lRecordToLoad.setAttribute("idUd", record.getAttribute("idUd"));
		lGwtRestDataSource.getData(lRecordToLoad, new DSCallback() {

			@Override
			public void execute(DSResponse response, Object rawData, DSRequest request) {
				if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
					
					Record recordRisposta = response.getData()[0];
					// questo è PG o R o PI (protocollo interno)
					String codCategoriaRegPrimariaRisposta = recordRisposta.getAttributeAsString("codCategoriaRegPrimariaRisposta");
					String siglaRegistroRegPrimariaRisposta = recordRisposta.getAttributeAsString("siglaRegistroRegPrimariaRisposta");
					String desRegistroRegPrimariaRisposta = recordRisposta.getAttributeAsString("desRegistroRegPrimariaRisposta");
					String categoriaRepertorioRegPrimariaRisposta = recordRisposta.getAttributeAsString("categoriaRepertorioRegPrimariaRisposta");
					String codCategoriaRegSecondariaRisposta = recordRisposta.getAttributeAsString("codCategoriaRegSecondariaRisposta");
					String siglaRegistroRegSecondariaRisposta = recordRisposta.getAttributeAsString("siglaRegistroRegSecondariaRisposta");
					String desRegistroRegSecondariaRisposta = recordRisposta.getAttributeAsString("desRegistroRegSecondariaRisposta");
					String categoriaRepertorioRegSecondariaRisposta = recordRisposta.getAttributeAsString("categoriaRepertorioRegSecondariaRisposta");
					
					/**
					    ***************************************************************************************** 
						*Codice categoria	Primaria		|R			|R			|PG			|PG             |
						*					Secondaria		|/			|PG			|R			|/              |
						*****************************************************************************************					
						*Istanza							 Repertorio	 Repertorio	 Protocollo	 Protocollo 	|
						*Repertorio							 SIGLA1		 SIGLA1		 SIGLA2		 /				|
						*Protocollo generale			     NO			 SI			 NO			 NO				|
						*****************************************************************************************
						*siglaRegistroRegPrimariaRispost  = SIGLA1												*
						*siglaRegistroRegSecondariaRisposta = SIGLA2											*
						*****************************************************************************************		
					*/
				
					if("R".equalsIgnoreCase(codCategoriaRegPrimariaRisposta)) {						
						if("PG".equalsIgnoreCase(codCategoriaRegSecondariaRisposta)) {
							if("E".equals(record.getAttribute("tipoProt"))) {
								recordRisposta.setAttribute("repertorio", desRegistroRegPrimariaRisposta);
								recordRisposta.setAttribute("protocolloGenerale", true);
								setTitle(repertorioInUscitaTitle);
								portletLayout = ProtocollazioneUtil.buildRepertorioDetail("U", recordRisposta);
							} else if("I".equals(record.getAttribute("tipoProt"))) {
								recordRisposta.setAttribute("repertorio", desRegistroRegPrimariaRisposta);
								recordRisposta.setAttribute("protocolloGenerale", true);
								setTitle(repertorioInternoTitle);
								portletLayout = ProtocollazioneUtil.buildRepertorioDetail("I", recordRisposta);
							}							
						} else {						
							if("E".equals(record.getAttribute("tipoProt"))) {
								recordRisposta.setAttribute("repertorio", desRegistroRegPrimariaRisposta);
								recordRisposta.setAttribute("protocolloGenerale", false);	
								setTitle(repertorioInUscitaTitle);
								portletLayout = ProtocollazioneUtil.buildRepertorioDetail("U", recordRisposta);
							} else if("I".equals(record.getAttribute("tipoProt"))) {
								recordRisposta.setAttribute("repertorio", desRegistroRegPrimariaRisposta);
								recordRisposta.setAttribute("protocolloGenerale", false);
								setTitle(repertorioInternoTitle);
								portletLayout = ProtocollazioneUtil.buildRepertorioDetail("I", recordRisposta);
							}						
						}
					} else {
						if("R".equalsIgnoreCase(codCategoriaRegSecondariaRisposta)) {							
							if("E".equals(record.getAttribute("tipoProt"))) {
								recordRisposta.setAttribute("repertorio", desRegistroRegSecondariaRisposta);
								recordRisposta.setAttribute("protocolloGenerale", false);	
								setTitle(protocollazioneInUscitaTitle);
								portletLayout = ProtocollazioneUtil.buildProtocollazioneDetailUscita(recordRisposta);
							} else if("I".equals(record.getAttribute("tipoProt"))) {
								recordRisposta.setAttribute("repertorio", desRegistroRegSecondariaRisposta);
								recordRisposta.setAttribute("protocolloGenerale", false);	
								setTitle(protocollazioneInternaTitle);
								portletLayout = ProtocollazioneUtil.buildProtocollazioneDetailInterna(recordRisposta);						
							}								
						} else {							
							if("E".equals(record.getAttribute("tipoProt"))) {								
								recordRisposta.setAttribute("protocolloGenerale", false);
								setTitle(protocollazioneInUscitaTitle);
								portletLayout = ProtocollazioneUtil.buildProtocollazioneDetailUscita(recordRisposta);
							} else if("I".equals(record.getAttribute("tipoProt"))) {								
								recordRisposta.setAttribute("protocolloGenerale", false);	
								setTitle(protocollazioneInternaTitle);
								portletLayout = ProtocollazioneUtil.buildProtocollazioneDetailInterna(recordRisposta);						
							}	
						}
					}
		
					portletLayout.caricaDettaglio(null, recordRisposta);
					portletLayout.getValuesManager().clearErrors(true);
					portletLayout.setHeight100();
					portletLayout.setWidth100();
					portletLayout.newMode(true);				
					setBody(portletLayout);
					_window.show();
				}
			}
		});
	}
}