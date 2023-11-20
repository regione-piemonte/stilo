/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.auriga.ui.module.layout.client.pratiche.DettaglioPraticaLayout;
import it.eng.auriga.ui.module.layout.client.pratiche.dettaglio.AllegatoLabel;
import it.eng.utility.ui.module.core.client.datasource.GWTRestDataSource;
import it.eng.utility.ui.module.layout.client.common.CustomDetail;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.smartgwt.client.data.DSCallback;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.data.RecordList;
import com.smartgwt.client.types.Visibility;
import com.smartgwt.client.widgets.Label;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.layout.VLayout;

public class AllegaDocumentazioneDetail extends CustomDetail {
	
	protected String idProcess;	
	protected Record recordEvento;
	protected String idTipoEvento;
	protected String nome;
	protected String idUd;
	
	protected DettaglioPraticaLayout dettaglioPraticaLayout;
	
	public AllegaDocumentazioneDetail(String nomeEntita, String idProcess, Record pRecordEvento, String idUd, DettaglioPraticaLayout dettaglioPraticaLayout) {
	
		super(nomeEntita);
		
		this.idProcess = idProcess;
		this.recordEvento = pRecordEvento;
		this.idTipoEvento = pRecordEvento != null ? pRecordEvento.getAttribute("idTipoEvento") : null;		
		this.nome = pRecordEvento != null ? pRecordEvento.getAttribute("nome") : null;
		this.idUd = idUd;
		this.dettaglioPraticaLayout = dettaglioPraticaLayout;
		
		VLayout lVLayout = new VLayout();
		lVLayout.setHeight100();
		lVLayout.setWidth100();
		
		VLayout lVLayoutTask = new VLayout();
		lVLayoutTask.setMembersMargin(10);
		
		buildSottomenu(lVLayoutTask);
		
		lVLayout.addMember(lVLayoutTask);
				
		VLayout spacer = new VLayout();
		spacer.setHeight100();
		spacer.setWidth100();						
		lVLayout.addMember(spacer);		
		
		addMember(lVLayout);			
	}
	
	protected void buildListaAllegati(RecordList listaAllegati, VLayout lVLayout)
	{
		final HashMap<String, List<Record>> mappaAllegatiProcSaved = new HashMap<String, List<Record>>();
		for(int i = 0; i<listaAllegati.getLength();i++)
		{
			final Record allegato = listaAllegati.get(i);
				
			final String livello = allegato.getAttribute("livello");
			final String idTipoAllegato = allegato.getAttribute("idTipoAllegato");
			final String nomeTipoAllegato = allegato.getAttribute("nomeTipoAllegato");
			final String descrizioneTipoAllegato = allegato.getAttribute("descrizioneTipoAllegato");
			final String flgContenuto = allegato.getAttribute("flgContenuto");
			final String nroFile = allegato.getAttribute("nroFile");
				
			if(livello != null && "1".equals(livello)) {
				final Label allegatoLabel = new AllegatoLabel(nomeTipoAllegato, descrizioneTipoAllegato, flgContenuto, nroFile);
				allegatoLabel.addClickHandler(new ClickHandler() {
					@Override
					public void onClick(ClickEvent event) {
						String titolo = dettaglioPraticaLayout.buildTitoloEvento(nome) + " / " + nomeTipoAllegato;
						RecordList listaAllegatiProcSaved = mappaAllegatiProcSaved.get(idTipoAllegato) != null ? new RecordList(mappaAllegatiProcSaved.get(idTipoAllegato).toArray(new Record[0])) : null;
						AllegatoProcDetail allegatoProcDetail = new AllegatoProcDetail("allegato_proc_" + idTipoAllegato, idProcess, idTipoAllegato, "A", "ALLIST", null, recordEvento, listaAllegatiProcSaved, dettaglioPraticaLayout) {							
							@Override
							public void back() {
								
								dettaglioPraticaLayout.caricaDettaglioEvento(nome);
							}							
						};	
						dettaglioPraticaLayout.caricaDettaglioEvento(nome, titolo, descrizioneTipoAllegato, allegatoProcDetail);
						allegatoProcDetail.setCanEdit(editing);						
					}
				});
				lVLayout.addMember(allegatoLabel);
			} else if(livello != null && "2".equals(livello)) {
				if(!mappaAllegatiProcSaved.containsKey(idTipoAllegato)) {
					mappaAllegatiProcSaved.put(idTipoAllegato, new ArrayList<Record>());
				} 
				mappaAllegatiProcSaved.get(idTipoAllegato).add(allegato);
			}			
		}
	}
	
	public void buildSottomenu(final VLayout lVLayout) {		
		GWTRestDataSource lGwtRestDataSource = new GWTRestDataSource("GetListaAllegatiProcDatasource");
		lGwtRestDataSource.addParam("idProcess", idProcess);
		lGwtRestDataSource.addParam("allegatiDi", "ISTANZA");		
		lGwtRestDataSource.fetchData(null, new DSCallback() {
			@Override
			public void execute(DSResponse response, Object rawData, DSRequest request) {
				if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
					RecordList listaAllegati = response.getDataAsRecordList();
					buildListaAllegati(listaAllegati, lVLayout);
				}
			}
		});		
	}

}