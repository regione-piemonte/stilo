/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.auriga.ui.module.layout.client.attributiDinamici.AttributiDinamiciFilter;
import it.eng.auriga.ui.module.layout.client.i18n.I18NUtil;
import it.eng.utility.ui.module.core.client.callback.ServiceCallback;
import it.eng.utility.ui.module.core.client.datasource.GWTRestDataSource;
import it.eng.utility.ui.module.core.client.datasource.GWTRestService;
import it.eng.utility.ui.module.core.shared.message.MessageBean;
import it.eng.utility.ui.module.core.shared.message.MessageType;
import it.eng.utility.ui.module.layout.client.Layout;
import it.eng.utility.ui.module.layout.client.common.CustomLayout;
import it.eng.utility.ui.module.layout.client.common.MapCreator;

import java.util.ArrayList;
import java.util.Map;

import com.smartgwt.client.data.DSCallback;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.data.RecordList;
import com.smartgwt.client.types.DSOperationType;
import com.smartgwt.client.types.FieldType;

public abstract class ElenchiAlbiLayout extends CustomLayout {

	private String idTipo;
	private String nomeTipo;
	private boolean abilToInsModDel;

	private RecordList attributiAdd;
	private Map mappaDettAttrLista;

	// private ToolStrip tipoToolStrip;
	// private Label label;
	// private Label tipoLabel;

	public ElenchiAlbiLayout(String idTipo, String nomeTipo, RecordList attributiAdd, Map mappaDettAttrLista, Map mappaValoriAttrLista,
			Map mappaVariazioniAttrLista, Map mappaDocumenti, final boolean abilToInsModDel) {

		super("elenchi_albi." + idTipo, "elenchi_albi", getElenchiAlbiDataSource(idTipo), new AttributiDinamiciFilter("elenchi_albi", new MapCreator(
				"nomeTabella|*|tipoEntita", "DMT_ELENCHI_ALBI_CONTENTS|*|" + idTipo, "|*|").getProperties()),
				new ElenchiAlbiList("elenchi_albi", attributiAdd) {

					@Override
					protected boolean isAbilToInsModDel() {
						
						return abilToInsModDel;
					}
				}, new ElenchiAlbiDetail("elenchi_albi", attributiAdd, mappaDettAttrLista, mappaValoriAttrLista, mappaVariazioniAttrLista, mappaDocumenti));

		this.idTipo = idTipo;
		this.nomeTipo = nomeTipo;
		this.abilToInsModDel = abilToInsModDel;

		this.attributiAdd = attributiAdd;
		this.mappaDettAttrLista = mappaDettAttrLista;

		// tipoToolStrip = new ToolStrip();
		// tipoToolStrip.setBackgroundColor("transparent");		
		// tipoToolStrip.setBackgroundImage("blank.png");
		// tipoToolStrip.setBackgroundRepeat(BkgndRepeat.REPEAT_X);
		// tipoToolStrip.setBorder("0px");
		// tipoToolStrip.setHeight(20);
		// tipoToolStrip.setWidth100();
		// tipoToolStrip.setAlign(Alignment.CENTER);
		//
		// label = new Label("&nbsp;<b>Tipo<b/>&nbsp;:&nbsp;&nbsp;&nbsp;");
		// label.setStyleName(it.eng.utility.Styles.formTitle);
		// label.setAutoFit(true);
		// label.setWrap(false);
		// label.setAlign(Alignment.CENTER);
		//
		// tipoLabel = new Label(nomeTipo);
		// tipoLabel.setAutoFit(true);
		// tipoLabel.setWrap(false);
		//
		// tipoToolStrip.setMembers(label, tipoLabel);
		//
		// filterLayout.setMembers(filterToolStrip, tipoToolStrip, filter, filterButtons);

		multiselectButton.hide();

		if (!isAbilToInsModDel()) {
			newButton.hide();
		}
	}

	public abstract String getIdTipoForPref();

	public static GWTRestDataSource getElenchiAlbiDataSource(String idTipo) {
		GWTRestDataSource datasource = new GWTRestDataSource("ElenchiAlbiDataSource", "idElencoAlbo", FieldType.TEXT);
		datasource.addParam("idTipo", idTipo);
		return datasource;
	}

	@Override
	public String getPrefKeyPrefix() {
		
		return Layout.getConfiguredPrefKeyPrefix() + this.nomeEntita + "." + getIdTipoForPref() + ".";
	}

	@Override
	public boolean getCanSetAsHomepage() {
		return false;
	}

	@Override
	public String getTipoEstremiRecord(Record record) {
		
		String estremi = record.getAttributeAsString("denomSogg");
		if (estremi == null || "".equals(estremi)) {
			estremi = record.getAttributeAsString("ELENCO_ALBO_DENOM_SOGG");
		}
		return estremi;
	}

	public void reload(final DSCallback callback) {
		if (this.mode.equals("new")) {
			detail.editNewRecord();
			detail.getValuesManager().clearErrors(true);
		} else {
			Record detailRecord = new Record(detail.getValuesManager().getValues());
			loadElenchiAlbiDetail(detailRecord, callback);
		}
	}

	public void loadElenchiAlbiDetail(Record record, final DSCallback callback) {
		final String idElencoAlbo = record.getAttribute("idElencoAlbo");
		final String rowId = record.getAttribute("rowId");
		GWTRestService<Record, Record> lGwtRestService = new GWTRestService<Record, Record>("AttributiDinamiciDatasource");
		Record recordToLoad = new Record();
		recordToLoad.setAttribute("nomeTabella", "DMT_ELENCHI_ALBI_CONTENTS");
		recordToLoad.setAttribute("rowId", rowId);
		recordToLoad.setAttribute("tipoEntita", getIdTipo());
		lGwtRestService.call(recordToLoad, new ServiceCallback<Record>() {

			@Override
			public void execute(Record object) {
				
				RecordList attributiAdd = object.getAttributeAsRecordList("attributiAdd");
				Map mappaDettAttrLista = object.getAttributeAsMap("mappaDettAttrLista");
				Map mappaValoriAttrLista = object.getAttributeAsMap("mappaValoriAttrLista");
				Record values = new Record();
				values.setAttribute("idElencoAlbo", idElencoAlbo);
				values.setAttribute("rowId", rowId);
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
											if (valori.getAttribute((String) dett.get("nome")) != null
													&& !"".equals(valori.getAttribute((String) dett.get("nome")))) {
												valori.setAttribute((String) dett.get("nome"), new Boolean(valori.getAttribute((String) dett.get("nome"))));
											}
										}
									}
									valoriLista.add(valori);
								}
								values.setAttribute(attr.getAttribute("nome"), valoriLista);
							}
						} else {
							if (attr.getAttribute("valore") != null && !"".equals(attr.getAttribute("valore"))) {
								if ("CHECK".equals(attr.getAttribute("tipo"))) {
									values.setAttribute(attr.getAttribute("nome"), new Boolean(attr.getAttribute("valore")));
								} else
									values.setAttribute(attr.getAttribute("nome"), attr.getAttribute("valore"));
							}
						}
					}
				}
				detail.editRecord(values);
				detail.getValuesManager().clearErrors(true);
				DSResponse response = new DSResponse();
				response.setData(new Record[] { object });
				callback.execute(response, null, new DSRequest());
			}
		});
	}

	@Override
	public void onSaveButtonClick() {
		if (detail.validate()) {
			DSOperationType saveOperationType = detail.getValuesManager().getSaveOperationType();
			Layout.showWaitPopup("Salvataggio in corso: potrebbe richiedere qualche secondo. Attendere...");
			DSCallback callback = new DSCallback() {

				@Override
				public void execute(DSResponse response, Object rawData, DSRequest request) {
					
					if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
						final Record savedRecord = response.getData()[0];
						try {
							list.manageLoadDetail(savedRecord, detail.getRecordNum(), new DSCallback() {

								@Override
								public void execute(DSResponse response, Object rawData, DSRequest request) {
									viewMode();
									Layout.hideWaitPopup();
									Layout.addMessage(new MessageBean(I18NUtil.getMessages().afterSave_message(getTipoEstremiRecord(response.getData()[0])),
											"", MessageType.INFO));
								}
							});
						} catch (Exception e) {
							Layout.hideWaitPopup();
						}
						if (!fullScreenDetail) {
							reloadListAndSetCurrentRecord(savedRecord);
						}
					} else {
						Layout.hideWaitPopup();
					}
				}
			};
			try {
				final Record detailRecord = ((ElenchiAlbiDetail) detail).getDetailRecord();
				if ((saveOperationType != null && saveOperationType.equals(DSOperationType.ADD)) || detailRecord.getAttribute("idElencoAlbo") == null
						|| detailRecord.getAttribute("idElencoAlbo").equals("")) {
					detail.getDataSource().addData(detailRecord, callback);
				} else {
					detail.getDataSource().updateData(detailRecord, callback);
				}
			} catch (Exception e) {
				Layout.hideWaitPopup();
			}
		} else {
			Layout.addMessage(new MessageBean(I18NUtil.getMessages().validateError_message(), "", MessageType.ERROR));
		}
	}

	@Override
	public String getNewDetailTitle() {
		return "Nuovo elenco / albo - " + nomeTipo;
	}

	@Override
	public String getViewDetailTitle() {
		Record record = new Record(detail.getValuesManager().getValues());
		return "Dettaglio elenco / albo " + getTipoEstremiRecord(record) + " - " + nomeTipo;
	}

	@Override
	public String getEditDetailTitle() {
		Record record = new Record(detail.getValuesManager().getValues());
		return "Modifica elenco / albo " + getTipoEstremiRecord(record) + " - " + nomeTipo;
	}

	@Override
	public void newMode() {
		super.newMode();
		altreOpButton.hide();
	}

	@Override
	public void viewMode() {
		
		super.viewMode();
		if (isAbilToInsModDel()) {
			deleteButton.show();
			editButton.show();
		} else {
			deleteButton.hide();
			editButton.hide();
		}
		altreOpButton.hide();
	}

	@Override
	public void editMode(boolean fromViewMode) {
		
		super.editMode(fromViewMode);
		altreOpButton.hide();
	}

	public String getIdTipo() {
		return idTipo;
	}

	public String getNomeTipo() {
		return nomeTipo;
	}

	protected boolean isAbilToInsModDel() {
		
		return abilToInsModDel;
	}

}
