/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.HashMap;
import java.util.Map;

import com.smartgwt.client.data.DSCallback;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.data.RecordList;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.Overflow;
import com.smartgwt.client.util.JSON;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.layout.VLayout;

import it.eng.auriga.ui.module.layout.client.FirmaUtility.FirmaCallback;
import it.eng.auriga.ui.module.layout.client.FirmaUtility.FirmaMultiplaCallback;
import it.eng.auriga.ui.module.layout.client.protocollazione.AllegatiItem;
import it.eng.utility.ui.module.core.client.callback.ServiceCallback;
import it.eng.utility.ui.module.core.client.datasource.GWTRestService;
import it.eng.utility.ui.module.layout.client.common.file.InfoFileRecord;
import it.eng.utility.ui.module.layout.client.common.items.CheckboxItem;
import it.eng.utility.ui.module.layout.client.common.items.SelectItem;

public class TestFirmaMultiplaHashLayout extends VLayout {

	public TestFirmaMultiplaHashLayout() {

		setWidth100();
		setHeight100();
		setOverflow(Overflow.VISIBLE);
		
		final SelectItem lSelectModalitaFirmaItem = new SelectItem("modalitaFirma");
		lSelectModalitaFirmaItem.setValueMap("APPLET", "JNLP");
		lSelectModalitaFirmaItem.setDefaultValue("APPLET");
		lSelectModalitaFirmaItem.setShowTitle(false);
		lSelectModalitaFirmaItem.setStartRow(true);

		final SelectItem lSelectTipoFirmaItem = new SelectItem("tipoFirma");
		lSelectTipoFirmaItem.setValueMap("CAdES", "PAdES");
		lSelectTipoFirmaItem.setDefaultValue("CAdES");
		lSelectTipoFirmaItem.setShowTitle(false);
		lSelectTipoFirmaItem.setStartRow(true);

		final SelectItem lSelectCongiuntaVerticaleItem = new SelectItem("congiuntaVerticale");
		lSelectCongiuntaVerticaleItem.setValueMap("congiunta", "verticale");
		lSelectCongiuntaVerticaleItem.setDefaultValue("congiunta");
		lSelectCongiuntaVerticaleItem.setShowTitle(false);
		lSelectCongiuntaVerticaleItem.setStartRow(true);

		final CheckboxItem lCheckSignMarkEnabledItem = new CheckboxItem("signMarkEnabled", "abilita marca temporale");
		lCheckSignMarkEnabledItem.setStartRow(true);

		AllegatiItem lFileItem = new AllegatiItem() {

			@Override
			public boolean showNumeroAllegato() {
				return false;
			};

			@Override
			public boolean showTipoAllegato() {
				return false;
			}

			@Override
			public boolean showDescrizioneFileAllegato() {
				return false;
			}

			@Override
			public void manageOnClickFirma(String uri, String display, InfoFileRecord infoFile, final FirmaCallback firmaCallback) {
				
				Record lRecordFile = new Record();
				lRecordFile.setAttribute("idFile", uri);
				lRecordFile.setAttribute("uri", uri);
				lRecordFile.setAttribute("nomeFile", display);
				lRecordFile.setAttribute("infoFile", infoFile);
				final FirmaMultiplaCallback firmaMultiplaCallback = new FirmaMultiplaCallback() {

					@Override
					public void execute(Map<String, Record> files, Record[] filesAndUd) {
						
						Record lRecordFileFirmato = files.values().iterator().next();
						String nomeFileFirmato = lRecordFileFirmato.getAttribute("nomeFile");
						String uriFileFirmato = lRecordFileFirmato.getAttribute("uri");
						String record = JSON.encode(lRecordFileFirmato.getAttributeAsRecord("infoFile").getJsObj());
						InfoFileRecord infoFileFirmato = InfoFileRecord.buildInfoFileString(record);
						firmaCallback.execute(nomeFileFirmato, uriFileFirmato, infoFileFirmato);
					}
				};
				final Record[] filesAndUd = new Record[1];
				filesAndUd[0] = lRecordFile;
				Record lRecordFiles = new Record();
				lRecordFiles.setAttribute("files", new RecordList(filesAndUd));
				GWTRestService<Record, Record> lCalcolaImpronteService = new GWTRestService<Record, Record>("CalcolaImpronteService");
				if (lSelectCongiuntaVerticaleItem.getValueAsString() != null && lSelectCongiuntaVerticaleItem.getValueAsString().equals("congiunta")) {
					lCalcolaImpronteService.addParam("firmaCongiunta", "true");
				} else {
					lCalcolaImpronteService.addParam("firmaCongiunta", "false");
				}
				lCalcolaImpronteService.executecustom("aggiungiEventualeRettangoloFirmaECalcolaImpronta", lRecordFiles, new DSCallback() {

					@Override
					public void execute(DSResponse response, Object rawData, DSRequest request) {
						Record object = response.getData()[0];
						
						Map<String, Record> lMap = new HashMap<String, Record>();
						for (Record lRecordFile : object.getAttributeAsRecordList("files").toArray()) {
							lMap.put(lRecordFile.getAttribute("idFile"), lRecordFile);
						}
						if (lSelectModalitaFirmaItem.getValueAsString() == null || "".equalsIgnoreCase(lSelectModalitaFirmaItem.getValueAsString()) || "APPLET".equalsIgnoreCase(lSelectModalitaFirmaItem.getValueAsString())) {

							/*
							 * Metto come primo parametro per la chiamata di FirmaMultiplaHashWindow il valore null perchè corrisponde alla forzatura del caso
							 * degli atti
							 */
							FirmaMultiplaHashWindow lFirmaMultiplaHashWindow = new FirmaMultiplaHashWindow(null, false, false, lMap, false) {

								@Override
								public void firmaCallBack(Map<String, Record> files, String commonName) {
									if (firmaMultiplaCallback != null) {
										firmaMultiplaCallback.execute(files, filesAndUd);
									}
								}

								@Override
								public String getAppletTipoFirma() {
									
									return lSelectTipoFirmaItem.getValueAsString();
								}

								@Override
								public boolean isFirmaCongiunta() {
									
									return lSelectCongiuntaVerticaleItem.getValueAsString() != null
											&& lSelectCongiuntaVerticaleItem.getValueAsString().equals("congiunta");
								}

								@Override
								public boolean isAppletSignMarkEnabled() {
									
									return lCheckSignMarkEnabledItem.getValueAsBoolean() != null && lCheckSignMarkEnabledItem.getValueAsBoolean();
								}

							};
							lFirmaMultiplaHashWindow.show();
						} else {
							/*
							 * Passiamo null come primo parametro perchè corrisponde alla forzatura della tipologia dell'applet. Utilizzata soltanto nel caso
							 * degli atti
							 */
							new FirmaMultiplaHashHybridWindow(null, false, false, lMap, false, object.getAttributeAsString("jSessionId")) {

								@Override
								public void firmaCallBack(Map<String, Record> files, String commonName) {
									if (firmaMultiplaCallback != null) {
										firmaMultiplaCallback.execute(files, filesAndUd);
									}
								}

								@Override
								public String getAppletTipoFirma() {
									
									return lSelectTipoFirmaItem.getValueAsString();
								}

								@Override
								public boolean isFirmaCongiunta() {
									
									return lSelectCongiuntaVerticaleItem.getValueAsString() != null
											&& lSelectCongiuntaVerticaleItem.getValueAsString().equals("congiunta");
								}

								@Override
								public boolean isAppletSignMarkEnabled() {
									
									return lCheckSignMarkEnabledItem.getValueAsBoolean() != null && lCheckSignMarkEnabledItem.getValueAsBoolean();
								}

							};
						}
					}
				});
			}
			
			@Override
			public boolean getShowVersioneOmissis() {
				return true;
			}
		};
		lFileItem.setShowTitle(false);
		lFileItem.setNotReplicable(true);

		DynamicForm lForm1 = new DynamicForm();
		lForm1.setWrapItemTitles(false);
		lForm1.setMargin(10);
		lForm1.setNumCols(10);
		lForm1.setColWidths(1, 1, 1, 1, 1, 1, 1, 1, "*", "*");
		lForm1.setItems(lSelectModalitaFirmaItem, lSelectTipoFirmaItem, lSelectCongiuntaVerticaleItem, lCheckSignMarkEnabledItem);

		DynamicForm lForm2 = new DynamicForm();
		lForm2.setWrapItemTitles(false);
		lForm2.setMargin(10);
		lForm2.setNumCols(10);
		lForm2.setColWidths(1, 1, 1, 1, 1, 1, 1, 1, "*", "*");
		lForm2.setItems(lFileItem);

		setAlign(Alignment.CENTER);
		setTop(50);

		VLayout layout = new VLayout();
		layout.setHeight100();
		layout.setWidth100();
		layout.setOverflow(Overflow.AUTO);
		
		layout.addMember(lForm1);
		layout.addMember(lForm2);

		addMember(layout);

	}
}
