/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.auriga.ui.module.layout.client.pratiche.DettaglioPraticaLayout;
import it.eng.utility.ui.module.core.client.callback.ServiceCallback;
import it.eng.utility.ui.module.core.client.datasource.GWTRestService;
import it.eng.utility.ui.module.layout.client.common.CustomDetail;

import com.smartgwt.client.data.Record;
import com.smartgwt.client.data.RecordList;
import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.client.widgets.tab.Tab;
import com.smartgwt.client.widgets.tab.TabSet;

public abstract class TaskFlussoDetailDinamico extends CustomDetail implements TaskFlussoInterface, DownloadInterface {

	protected TaskFlussoDetail detail;

	protected DettaglioPraticaLayout dettaglioPraticaLayout;

	protected Record recordEvento;

	protected String idProcess;
	protected String idEvento;
	protected String idTipoEvento;
	protected String rowId;
	protected String nome;

	protected VLayout lVLayoutDinamico;

	private RecordList attributiAdd;

	public TaskFlussoDetailDinamico(final String nomeEntita, final String idProcess, Record lRecordEvento, final DettaglioPraticaLayout dettaglioPraticaLayout) {

		super(nomeEntita);
		
		this.recordEvento = lRecordEvento;

		this.idProcess = idProcess;
		this.idTipoEvento = lRecordEvento != null ? lRecordEvento.getAttribute("idTipoEvento") : null;
		this.idEvento = lRecordEvento != null ? lRecordEvento.getAttribute("idEvento") : null;
		this.rowId = lRecordEvento != null ? lRecordEvento.getAttribute("rowId") : null;
		this.nome = lRecordEvento != null ? lRecordEvento.getAttribute("nome") : null;
		this.dettaglioPraticaLayout = dettaglioPraticaLayout;

		setStyleName(it.eng.utility.Styles.detailLayoutWithTabSet);

		setHeight100();
		setWidth100();

		lVLayoutDinamico = new VLayout();
		lVLayoutDinamico.setHeight100();
		lVLayoutDinamico.setWidth100();

		addMember(lVLayoutDinamico);

		GWTRestService<Record, Record> lAttributiDinamiciDatasource = new GWTRestService<Record, Record>("AttributiDinamiciDatasource");
		Record lAttributiDinamiciRecord = new Record();
		lAttributiDinamiciRecord.setAttribute("nomeTabella", "DMT_PROCESS_HISTORY");
		lAttributiDinamiciRecord.setAttribute("rowId", rowId);
		lAttributiDinamiciRecord.setAttribute("tipoEntita", idTipoEvento);
		lAttributiDinamiciDatasource.call(lAttributiDinamiciRecord, new ServiceCallback<Record>() {

			@Override
			public void execute(final Record object) {
				attributiAdd = object.getAttributeAsRecordList("attributiAdd");
				detail = new TaskFlussoDetail(nomeEntita, idProcess, recordEvento, dettaglioPraticaLayout, attributiAdd, object
						.getAttributeAsMap("mappaDettAttrLista"), object.getAttributeAsMap("mappaValoriAttrLista"), object
						.getAttributeAsMap("mappaVariazioniAttrLista"), object.getAttributeAsMap("mappaDocumenti"));
				detail.setCanEdit(editing);
				detail.loadDati();
				boolean isEseguibile = true;
				if (recordEvento != null && recordEvento.getAttribute("flgEseguibile") != null) {
					isEseguibile = "1".equals(recordEvento.getAttribute("flgEseguibile"));
				}
				if (hasDocumento() && !isEseguibile && recordEvento.getAttribute("uriFile") != null && !"".equals(recordEvento.getAttribute("uriFile"))) {
					String uriFile = recordEvento.getAttribute("uriFile");
					String nomeFile = recordEvento.getAttribute("nomeFile");
					String titolo = recordEvento.getAttribute("estremiUd");
					TabSet tabSet = new TabSet();
					tabSet.setCanFocus(false);
					tabSet.setTabIndex(-1);
					Tab tabDatiPrincipali = new Tab("<b>" + dettaglioPraticaLayout.getDisplayNameEvento(nome) + "</b>");
					tabDatiPrincipali.setPane(detail);
					Tab tabDocumento = new Tab("<b>Documento associato</b>");
					tabDocumento.setPane(new RenderingPdfDetail(nomeEntita, titolo, uriFile, nomeFile, idProcess, recordEvento) {

						@Override
						public void back() {

						}
						
						@Override
						public void afterShow(Record object) {
							afterShowRenderingPdf(object);
						}
						
						@Override
						public void download() {
							
						}
					});
					tabSet.setTabs(tabDatiPrincipali, tabDocumento);
					lVLayoutDinamico.setMembers(tabSet);
				} else {
					lVLayoutDinamico.setMembers(detail);
				}
			}
		});
	}

	@Override
	public void setCanEdit(boolean canEdit) {
		editing = canEdit;
		if (detail != null)
			detail.setCanEdit(canEdit);
	}
	
	@Override
	public Record getRecordEvento() {
		return recordEvento;
	}

	@Override
	public void loadDati() {
		if (detail != null)
			detail.loadDati();
	}

	@Override
	public void back() {
		if (detail != null)
			detail.back();
	}

	@Override
	public void salvaDatiProvvisorio() {
		if (detail != null)
			detail.salvaDatiProvvisorio();
	}

	@Override
	public void salvaDatiDefinitivo() {
		if (detail != null)
			detail.salvaDatiDefinitivo();
	}

	@Override
	public String getNomeTastoSalvaProvvisorio() {
		return recordEvento != null ? recordEvento.getAttribute("nomeTastoSalvaProvvisorio") : null;
	}

	@Override
	public String getNomeTastoSalvaDefinitivo() {
		return recordEvento != null ? recordEvento.getAttribute("nomeTastoSalvaDefinitivo") : null;
	}
	
	@Override
	public String getNomeTastoSalvaDefinitivo_2() {
		return recordEvento != null ? recordEvento.getAttribute("nomeTastoSalvaDefinitivo_2") : null;
	}

	@Override
	public boolean hasDocumento() {
		return recordEvento != null && recordEvento.getAttribute("idTipoDoc") != null && !"".equals(recordEvento.getAttribute("idTipoDoc"));
	}

	public abstract void afterShowRenderingPdf(Record object);

}
