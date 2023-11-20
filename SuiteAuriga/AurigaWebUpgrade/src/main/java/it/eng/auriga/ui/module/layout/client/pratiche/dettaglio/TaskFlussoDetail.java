/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.auriga.ui.module.layout.client.attributiDinamici.AttributiDinamiciDetail;
import it.eng.auriga.ui.module.layout.client.pratiche.DettaglioPraticaLayout;
import it.eng.auriga.ui.module.layout.client.pratiche.dettaglio.editorapplet.EditorAppletDetail;
import it.eng.utility.ui.module.core.client.callback.ServiceCallback;
import it.eng.utility.ui.module.core.client.datasource.GWTRestService;
import it.eng.utility.ui.module.core.client.i18n.I18NUtil;
import it.eng.utility.ui.module.core.shared.message.MessageBean;
import it.eng.utility.ui.module.core.shared.message.MessageType;
import it.eng.utility.ui.module.layout.client.Layout;
import it.eng.utility.ui.module.layout.client.common.DetailSection;

import java.util.Map;

import com.smartgwt.client.data.DSCallback;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.data.RecordList;
import com.smartgwt.client.util.BooleanCallback;
import com.smartgwt.client.util.SC;

public class TaskFlussoDetail extends AttributiDinamiciDetail implements TaskFlussoInterface {

	protected TaskFlussoDetail instance;

	protected Record recordEvento;

	protected String activityName;
	protected String instanceId;
	protected String idProcess;
	protected String idEvento;
	protected String idTipoEvento;
	protected String rowId;
	protected String nome;
	protected String alertConfermaSalvaDefinitivo;

	protected DettaglioPraticaLayout dettaglioPraticaLayout;

	public TaskFlussoDetail(String nomeEntita, String idProcess, Record lRecordEvento, DettaglioPraticaLayout dettaglioPraticaLayout) {

		this(nomeEntita, idProcess, lRecordEvento, dettaglioPraticaLayout, null, null, null, null, null);

	}

	public TaskFlussoDetail(String nomeEntita, String idProcess, Record lRecordEvento, DettaglioPraticaLayout dettaglioPraticaLayout, RecordList attributiAdd,
			Map mappaDettAttrLista, Map mappaValoriAttrLista, Map mappaVariazioniAttrLista, Map mappaDocumenti) {

		super(nomeEntita, attributiAdd, mappaDettAttrLista, mappaValoriAttrLista, mappaVariazioniAttrLista, mappaDocumenti, null, null, null);

		instance = this;

		this.recordEvento = lRecordEvento;

		this.idProcess = idProcess;
		this.instanceId = lRecordEvento != null ? lRecordEvento.getAttribute("instanceId") : null;
		this.activityName = lRecordEvento != null ? lRecordEvento.getAttribute("activityName") : null;
		this.idTipoEvento = lRecordEvento != null ? lRecordEvento.getAttribute("idTipoEvento") : null;
		this.idEvento = lRecordEvento != null ? lRecordEvento.getAttribute("idEvento") : null;
		this.rowId = lRecordEvento != null ? lRecordEvento.getAttribute("rowId") : null;
		this.nome = lRecordEvento != null ? lRecordEvento.getAttribute("nome") : null;

		this.alertConfermaSalvaDefinitivo = lRecordEvento != null ? lRecordEvento.getAttribute("alertConfermaSalvaDefinitivo") : null;

		this.dettaglioPraticaLayout = dettaglioPraticaLayout;

	}

	@Override
	public Record getRecordEvento() {
		return recordEvento;
	}

	@Override
	public void loadDati() {

	}

	public void salvaDati(boolean flgIgnoreObblig, final ServiceCallback<Record> callback) {
		final Record detailRecord = new Record(vm.getValues());
		detailRecord.setAttribute("valori", getMappaValori(detailRecord));
		detailRecord.setAttribute("tipiValori", getMappaTipiValori(detailRecord));
		detailRecord.setAttribute("idProcess", idProcess);
		detailRecord.setAttribute("idEvento", idEvento);
		detailRecord.setAttribute("idTipoEvento", idTipoEvento);
		detailRecord.setAttribute("desEvento", dettaglioPraticaLayout.getDisplayNameEvento(nome));
		Layout.showWaitPopup("Salvataggio in corso...");
		GWTRestService<Record, Record> lGWTRestService = new GWTRestService<Record, Record>("CustomEventDatasource");
		if (flgIgnoreObblig) {
			lGWTRestService.addParam("flgIgnoreObblig", "1");
		} else {
			lGWTRestService.addParam("skipSuccessMsg", "true");
		}
		lGWTRestService.call(detailRecord, new ServiceCallback<Record>() {

			@Override
			public void execute(Record object) {
				if (callback != null) {
					callback.execute(object);
				} else {
					Layout.hideWaitPopup();
				}
			}
		});
	}

	public void salvaDatiProvvisorio() {
		salvaDati(true, new ServiceCallback<Record>() {

			@Override
			public void execute(Record object) {
				dettaglioPraticaLayout.creaMenuGestisciIter(new ServiceCallback<Record>() {

					@Override
					public void execute(Record record) {
						Layout.hideWaitPopup();
						reload();
					}
				});
			}
		});
	}

	public void salvaDatiDefinitivo() {
		if (hasDocumento()) {
			saveAndSend();
		} else {
			saveAndGoAlert();
		}
	}

	public void saveAndSend() {
		if (vm.validate()) {
			salvaDati(false, new ServiceCallback<Record>() {

				@Override
				public void execute(Record object) {
					idEvento = object.getAttribute("idEvento");
					recordEvento.setAttribute("idEvento", idEvento);
					dettaglioPraticaLayout.creaMenuGestisciIter(new ServiceCallback<Record>() {

						@Override
						public void execute(Record record) {
							Layout.hideWaitPopup();
							final String titolo = dettaglioPraticaLayout.buildTitoloEvento(nome) + " / Anteprima documento";
							String editor = recordEvento != null ? recordEvento.getAttribute("editor") : null;
							if (editor != null && "APPLET".equals(editor)) {
								dettaglioPraticaLayout.caricaDettaglioEvento(nome, titolo, null, new EditorAppletDetail(nomeEntita, null, idProcess,
										recordEvento, dettaglioPraticaLayout) {

									@Override
									public boolean isAnteprimaDocumento() {
										return true;
									}
								});
							} else {
								dettaglioPraticaLayout.caricaDettaglioEvento(nome, titolo, null, new EditorHtmlFlussoDetail(nomeEntita, null, idProcess,
										recordEvento, dettaglioPraticaLayout) {

									@Override
									public boolean isAnteprimaDocumento() {
										return true;
									}
								});
							}
						}
					});
				}
			});
		} else {
			Layout.addMessage(new MessageBean(getValidateErrorMessage(), "", MessageType.ERROR));
		}
	}

	public String getValidateErrorMessage() {
		return I18NUtil.getMessages().validateError_message();
	}

	public void saveAndGoAlert() {
		if (vm.validate()) {
			if (alertConfermaSalvaDefinitivo != null && !"".equals(alertConfermaSalvaDefinitivo)) {
				SC.ask(alertConfermaSalvaDefinitivo, new BooleanCallback() {

					@Override
					public void execute(Boolean value) {
						if (value) {
							saveAndGo();
						}
					}
				});
			} else {
				saveAndGo();
			}
		} else {
			Layout.addMessage(new MessageBean(getValidateErrorMessage(), "", MessageType.ERROR));
		}
	}

	public void saveAndGo() {
		salvaDati(false, new ServiceCallback<Record>() {

			@Override
			public void execute(Record object) {
				idEvento = object.getAttribute("idEvento");
				Record lRecord = new Record();
				lRecord.setAttribute("instanceId", instanceId);
				lRecord.setAttribute("activityName", activityName);
				lRecord.setAttribute("idProcess", idProcess);
				lRecord.setAttribute("idEventType", idTipoEvento);
				lRecord.setAttribute("idEvento", idEvento);
				GWTRestService<Record, Record> lAurigaTaskDataSource = new GWTRestService<Record, Record>("AurigaTaskDataSource");
				lAurigaTaskDataSource.executecustom("salvaTask", lRecord, new DSCallback() {

					@Override
					public void execute(DSResponse response, Object rawData, DSRequest request) {
						if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
							dettaglioPraticaLayout.creaMenuGestisciIter(new ServiceCallback<Record>() {

								@Override
								public void execute(Record record) {
									Layout.hideWaitPopup();
									Layout.addMessage(new MessageBean("Procedimento avanzato al passo successivo", "", MessageType.INFO));
									next();
								}
							});
						}
					}
				});
			}
		});
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

	@Override
	public void addDetailMembers() {

		for (DetailSection detailSection : attributiDinamiciDetailSections) {
			addMember(detailSection);
		}

	}

	public void reload() {
		dettaglioPraticaLayout.caricaDettaglioEvento(nome);
	}
	
	@Override
	public void back() {
		dettaglioPraticaLayout.caricaDettaglioEventoAnnulla(nome);
	}

	public void next() {
		dettaglioPraticaLayout.caricaDettaglioEventoSuccessivo(nome);
	}

}
