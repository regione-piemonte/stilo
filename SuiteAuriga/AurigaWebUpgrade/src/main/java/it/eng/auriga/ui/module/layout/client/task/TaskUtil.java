/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.ArrayList;
import java.util.Map;

import com.smartgwt.client.data.Record;
import com.smartgwt.client.data.RecordList;
import com.smartgwt.client.util.BooleanCallback;

import it.eng.auriga.ui.module.layout.client.AurigaLayout;
import it.eng.utility.ui.module.core.client.callback.ServiceCallback;
import it.eng.utility.ui.module.core.client.datasource.GWTRestService;

/**
 * Classe di utilità per il recupero dei messaggi internazionalizzati
 * 
 * @author michele
 *
 */
public abstract class TaskUtil {

	/**
	 * Ritorna i messaggi internazionalizzati
	 * 
	 * @return
	 */
	public static void createTaskDetail(final String instanceId, final String idProcess, final String activityName, final boolean isExec,
			final TaskUtilCallback callback) {
		GWTRestService<Record, Record> lAurigaTaskDataSource = new GWTRestService<Record, Record>("AurigaTaskDataSource");
		Record lCallExecAttRecord = new Record();
		lCallExecAttRecord.setAttribute("instanceId", instanceId);
		lCallExecAttRecord.setAttribute("idProcess", idProcess);
		lCallExecAttRecord.setAttribute("activityName", activityName);
		lAurigaTaskDataSource.call(lCallExecAttRecord, new ServiceCallback<Record>() {

			@Override
			public void execute(final Record record) {
				
				final String tipoEntita = record.getAttribute("idEventType");
				final String rowId = record.getAttribute("rowIdEvento");
				final String idGUIEvento = record.getAttribute("idGUIEvento");

				if (record.getAttribute("warningMsg") != null && !"".equals(record.getAttribute("warningMsg"))) {
					AurigaLayout.showConfirmDialogWithWarning("Attenzione!", record.getAttribute("warningMsg"), "Procedi", "Annulla", new BooleanCallback() {

						@Override
						public void execute(Boolean value) {
							
							if (value != null && value) {
								if (idGUIEvento != null && (idGUIEvento.equals("MSG_ATTIV") || idGUIEvento.equals("SCELTA_ESITO"))) {
									loadTaskDetail(idGUIEvento, record, isExec, callback);
								} else {
									GWTRestService<Record, Record> lAttributiDinamiciDatasource = new GWTRestService<Record, Record>(
											"AttributiDinamiciDatasource");
									Record lAttributiDinamiciRecord = new Record();
									lAttributiDinamiciRecord.setAttribute("nomeTabella", "DMT_PROCESS_HISTORY");
									lAttributiDinamiciRecord.setAttribute("rowId", rowId);
									lAttributiDinamiciRecord.setAttribute("tipoEntita", tipoEntita);
									lAttributiDinamiciDatasource.call(lAttributiDinamiciRecord, new ServiceCallback<Record>() {

										@Override
										public void execute(final Record object) {
											
											loadTaskDetail(idGUIEvento, record, isExec, object.getAttributeAsRecordList("attributiAdd"),
													object.getAttributeAsMap("mappaDettAttrLista"), object.getAttributeAsMap("mappaValoriAttrLista"),
													object.getAttributeAsMap("mappaVariazioniAttrLista"), object.getAttributeAsMap("mappaDocumenti"),
													record.getAttributeAsMap("mappaAttrToShow"), callback);
										}
									});
								}
							}
						}
					});
				} else {
					if (idGUIEvento != null && (idGUIEvento.equals("MSG_ATTIV") || idGUIEvento.equals("SCELTA_ESITO"))) {
						loadTaskDetail(idGUIEvento, record, isExec, callback);
					} else {
						GWTRestService<Record, Record> lAttributiDinamiciDatasource = new GWTRestService<Record, Record>("AttributiDinamiciDatasource");
						Record lAttributiDinamiciRecord = new Record();
						lAttributiDinamiciRecord.setAttribute("nomeTabella", "DMT_PROCESS_HISTORY");
						lAttributiDinamiciRecord.setAttribute("rowId", rowId);
						lAttributiDinamiciRecord.setAttribute("tipoEntita", tipoEntita);
						lAttributiDinamiciDatasource.call(lAttributiDinamiciRecord, new ServiceCallback<Record>() {

							@Override
							public void execute(final Record object) {
								
								loadTaskDetail(idGUIEvento, record, isExec, object.getAttributeAsRecordList("attributiAdd"),
										object.getAttributeAsMap("mappaDettAttrLista"), object.getAttributeAsMap("mappaValoriAttrLista"),
										object.getAttributeAsMap("mappaVariazioniAttrLista"), object.getAttributeAsMap("mappaDocumenti"),
										record.getAttributeAsMap("mappaAttrToShow"), callback);
							}
						});
					}
				}
			}
		});
	}

	public static void loadTaskDetail(String idGUIEvento, Record record, boolean isExec, TaskUtilCallback callback) {
		loadTaskDetail(idGUIEvento, record, isExec, null, null, null, null, null, null, callback);
	}

	public static void loadTaskDetail(String idGUIEvento, Record record, boolean isExec, RecordList attributiAdd, Map mappaDettAttrLista,
			Map mappaValoriAttrLista, Map mappaVariazioniAttrLista, Map mappaDocumenti, Map mappaAttrToShow, TaskUtilCallback callback) {

		if (mappaAttrToShow != null) {
			RecordList attributiAddToShow = new RecordList();
			if (attributiAdd != null && attributiAdd.getLength() > 0) {
				for (int i = 0; i < attributiAdd.getLength(); i++) {
					Record attr = attributiAdd.get(i);
					if (mappaAttrToShow.containsKey(attr.getAttribute("nome"))) {
						Map<String, String> params = (Map<String, String>) mappaAttrToShow.get(attr.getAttribute("nome"));
						if (params != null) {
							for (String key : params.keySet()) {
								Record customAttr = new Record();
								customAttr.setAttribute("nome", key);
								customAttr.setAttribute("tipo", "CUSTOM");
								customAttr.setAttribute("valore", params.get(key));
								customAttr.setAttribute("numero", attr.getAttribute("numero"));
								attributiAddToShow.add(customAttr);
							}
						}
						attributiAddToShow.add(attr);
					}
				}
			}
			attributiAdd = attributiAddToShow;
		}

		TaskDetail taskDetail = null;
		if (idGUIEvento == null || idGUIEvento.equals("") || idGUIEvento.equals("DETT_EVENTO")) {
			taskDetail = new TaskStandardDetail("task_dett_evento", attributiAdd, mappaDettAttrLista, mappaValoriAttrLista, mappaVariazioniAttrLista,
					mappaDocumenti);
		} else if (idGUIEvento.equals("MSG_ATTIV")) {
			taskDetail = new TaskMsgAttivoDetail("task_msg_attiv");
		} else if (idGUIEvento.equals("SCELTA_ESITO")) {
			taskDetail = new TaskSceltaEsitoDetail("task_scelta_esito");
		}

		if (attributiAdd != null) {
			for (int i = 0; i < attributiAdd.getLength(); i++) {
				Record attr = attributiAdd.get(i);
				if ("LISTA".equals(attr.getAttribute("tipo"))) {
					if (mappaValoriAttrLista.get(attr.getAttribute("nome")) != null) {
						RecordList valoriLista = new RecordList();
						for (Map mapValori : (ArrayList<Map>) mappaValoriAttrLista.get(attr.getAttribute("nome"))) {
							Record valori = new Record(mapValori);
							for (Map dett : (ArrayList<Map>) mappaDettAttrLista.get(attr.getAttribute("nome"))) {
								if ("CHECK".equals(dett.get("tipo"))) {
									if (valori.getAttribute((String) dett.get("nome")) != null && !"".equals(valori.getAttribute((String) dett.get("nome")))) {
										valori.setAttribute((String) dett.get("nome"), new Boolean(valori.getAttribute((String) dett.get("nome"))));
									}
								}
							}
							valoriLista.add(valori);
						}
						record.setAttribute(attr.getAttribute("nome"), valoriLista);
					}
				} else {
					if (attr.getAttribute("valore") != null && !"".equals(attr.getAttribute("valore"))) {
						if ("CHECK".equals(attr.getAttribute("tipo"))) {
							record.setAttribute(attr.getAttribute("nome"), new Boolean(attr.getAttribute("valore")));
						} else
							record.setAttribute(attr.getAttribute("nome"), attr.getAttribute("valore"));
					}
				}
			}
		}

		if (isExec) {
			taskDetail.editNewRecord(record.toMap());
		} else {
			taskDetail.editRecord(record);
		}
		callback.execute(taskDetail);
	}

}
