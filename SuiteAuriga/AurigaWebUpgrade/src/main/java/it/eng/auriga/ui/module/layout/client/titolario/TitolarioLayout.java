/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.auriga.ui.module.layout.client.i18n.I18NUtil;
import it.eng.utility.ui.module.core.client.datasource.GWTRestDataSource;
import it.eng.utility.ui.module.core.shared.bean.CacheLevelBean;
import it.eng.utility.ui.module.core.shared.message.MessageBean;
import it.eng.utility.ui.module.core.shared.message.MessageType;
import it.eng.utility.ui.module.layout.client.Layout;
import it.eng.utility.ui.module.layout.client.common.ConfigurableFilter;
import it.eng.utility.ui.module.layout.client.common.CustomAdvancedTreeLayout;
import it.eng.utility.ui.module.layout.client.common.items.SelectItem;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.smartgwt.client.data.AdvancedCriteria;
import com.smartgwt.client.data.Criterion;
import com.smartgwt.client.data.DSCallback;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.data.RecordList;
import com.smartgwt.client.types.DSOperationType;
import com.smartgwt.client.types.FieldType;
import com.smartgwt.client.types.OperatorId;
import com.smartgwt.client.util.BooleanCallback;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.events.DataArrivedEvent;
import com.smartgwt.client.widgets.form.fields.events.DataArrivedHandler;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.grid.events.CellClickEvent;
import com.smartgwt.client.widgets.grid.events.CellClickHandler;

public class TitolarioLayout extends CustomAdvancedTreeLayout {

	protected SelectItem pianiClassifItem;
	protected DynamicForm pianiClassifForm;
	protected GWTRestDataSource pianiClassifDS;

	protected String idPianoClassif;
	protected Boolean flgSoloAttive;
	protected String tsRif;
	protected String nroLivelliPiano;

	public TitolarioLayout() {
		this(null, null, null, null, null, null, null);
	}

	public TitolarioLayout(String finalita, Boolean flgSelezioneSingola, Boolean flgSoloFolder, String idRootNode, String idPianoClassif,
			Boolean flgSoloAttive, String tsRif) {

		super("titolario", new GWTRestDataSource("TitolarioTreeDatasource", true, "idNode", FieldType.TEXT), new GWTRestDataSource("TitolarioDatasource",
				"idClassificazione", FieldType.TEXT), new TitolarioTree("titolario"), new ConfigurableFilter("titolario"), new TitolarioList("titolario"),
				new TitolarioDetail("titolario"), finalita, flgSelezioneSingola, flgSoloFolder, idRootNode);

		this.idPianoClassif = idPianoClassif;
		this.flgSoloAttive = flgSoloAttive;
		this.tsRif = tsRif;

		pianiClassifDS = new GWTRestDataSource("ListaPianiClassificazioneDatasource", "idPianoClassif", FieldType.TEXT);

		if (getFinalita() != null && !"".equals(getFinalita())) {
			multiselectButton.hide();
		}

		newButton.setPrompt("Nuova classifica");

		if ((getFinalita() != null && !"".equals(getFinalita())) || !isAbilToIns()) {
			newButton.hide();
		}

		if (!isAbilToIns() || (getFinalita() != null && !"".equals(getFinalita()))) {
			pianiClassifDS.fetchData(null, new DSCallback() {

				@Override
				public void execute(DSResponse response, Object rawData, DSRequest request) {
					if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
						RecordList data = response.getDataAsRecordList();
						if (data != null && data.getLength() > 0) {
							if (getIdPianoClassif() == null) {
								Record record = data.get(0);
								for(int i = 0; i < data.getLength(); i++) {
									if(data.get(i).getAttribute("flgInVigore") != null && "1".equals(data.get(i).getAttribute("flgInVigore"))) {
										record = data.get(i);
										break;
									}
								}
								setIdPianoClassif(record.getAttribute("idPianoClassif"));
								setNroLivelliPiano(record.getAttribute("nroLivelli"));
							} else {
								for (int i = 0; i < data.getLength(); i++) {
									if (data.get(i).getAttribute("idPianoClassif").equals(getIdPianoClassif())) {
										setNroLivelliPiano(data.get(i).getAttribute("nroLivelli"));
										break;
									}
								}
							}
							((GWTRestDataSource) tree.getDataSource()).addParam("idPianoClassif", getIdPianoClassif());
							loadTreeFromDefaultIdRootNode();
						}
					}
				}
			});
		} else {
			pianiClassifItem = new SelectItem("pianiClassif", "<b>Piano di classificazione</b>");
			pianiClassifItem.setWrapTitle(false);
			pianiClassifItem.setAllowEmptyValue(false);

			ListGridField idPianoClassifField = new ListGridField("idPianoClassif", "Id.");
			idPianoClassifField.setHidden(true);
			ListGridField displayValueField = new ListGridField("displayValue");
			pianiClassifItem.setPickListFields(idPianoClassifField, displayValueField);

			// ListGridField dataInizioField = new ListGridField("dataInizio", "Dal");
			// dataInizioField.setWidth(80);
			// ListGridField dataFineField = new ListGridField("dataFine", "Al");
			// dataFineField.setWidth(80);
			// ListGridField noteField = new ListGridField("note", "Note");
			// pianiClassifItem.setPickListFields(idPianoClassifField, dataInizioField, dataFineField, noteField);

			pianiClassifItem.setValueField("idPianoClassif");
			pianiClassifItem.setDisplayField("displayValue");
			pianiClassifItem.setOptionDataSource(pianiClassifDS);

			pianiClassifItem.addDataArrivedHandler(new DataArrivedHandler() {

				@Override
				public void onDataArrived(DataArrivedEvent event) {
					RecordList data = event.getData();
					if (data != null && data.getLength() > 0) {
						if (getIdPianoClassif() == null) {
							Record record = data.get(0);
							setIdPianoClassif(record.getAttribute("idPianoClassif"));
							setNroLivelliPiano(record.getAttribute("nroLivelli"));
						} else {
							for (int i = 0; i < data.getLength(); i++) {
								if (data.get(i).getAttribute("idPianoClassif").equals(getIdPianoClassif())) {
									setNroLivelliPiano(data.get(i).getAttribute("nroLivelli"));
									break;
								}
							}
						}
						if (data.getLength() == 1) {
							pianiClassifForm.hide();
						} else {
							pianiClassifForm.show();
						}
						pianiClassifItem.setValue(getIdPianoClassif());
						((GWTRestDataSource) tree.getDataSource()).addParam("idPianoClassif", getIdPianoClassif());
						loadTreeFromDefaultIdRootNode();
					}
				}
			});

			ListGrid pianiClassifPickListProperties = new ListGrid();
			pianiClassifPickListProperties.setEmptyMessage(I18NUtil.getMessages().list_emptyMessage());
			pianiClassifPickListProperties.addCellClickHandler(new CellClickHandler() {

				@Override
				public void onCellClick(CellClickEvent event) {
					setIdPianoClassif((String) event.getRecord().getAttribute("idPianoClassif"));
					setNroLivelliPiano(event.getRecord().getAttribute("nroLivelli"));
					((GWTRestDataSource) tree.getDataSource()).addParam("idPianoClassif", getIdPianoClassif());
					loadTreeFromDefaultIdRootNode();
				}
			});
			pianiClassifItem.setPickListProperties(pianiClassifPickListProperties);

			pianiClassifForm = new DynamicForm();
			pianiClassifForm.setWidth100();
			pianiClassifForm.setNumCols(14);
			pianiClassifForm.setColWidths(1, "*", "*", "*");
			pianiClassifForm.setItems(pianiClassifItem);

			navigationLayout.setMembers(pianiClassifForm, navigationToolStrip);

		}

		((GWTRestDataSource) tree.getDataSource()).addParam("idPianoClassif", getIdPianoClassif());
		if (getFlgSoloAttive() != null && getFlgSoloAttive()) {
			((GWTRestDataSource) tree.getDataSource()).addParam("flgSoloAttive", "1");
			((GWTRestDataSource) list.getDataSource()).addParam("flgSoloAttive", "1");
		}
		((GWTRestDataSource) tree.getDataSource()).addParam("tsRif", getTsRif());

	}
	
	@Override
	public int getTreeSectionDefaultWidth() {
		return 400;
	}

	public static boolean isAbilToIns() {
		return Layout.isPrivilegioAttivo("DC/TIT;I");
	}

	public static boolean isAbilToMod() {
		return Layout.isPrivilegioAttivo("DC/TIT;M");
	}

	public static boolean isAbilToDel() {
		return Layout.isPrivilegioAttivo("DC/TIT;FC");
	}

	public boolean isAbilToInsInLivello(BigDecimal nroLivello) {
		return isAbilToIns() && (getNroLivelliPiano() == null || nroLivello.compareTo(new BigDecimal(getNroLivelliPiano())) < 0);
	}

//	@Override
//	public String getPrefKeyPrefix() {
//		return super.getPrefKeyPrefix() + (idPianoClassif != null && !"".equals(idPianoClassif) ? idPianoClassif + "." : "");
//	}

	@Override
	public String getNewDetailTitle() {
		Record record = new Record(detail.getValuesManager().getValues());
		return (record.getAttributeAsString("idClassificaSup") != null && !"".equals(record.getAttributeAsString("idClassificaSup"))) ? "Nuova sotto-classifica"
				: "Nuova classifica";
	}

	@Override
	public String getEditDetailTitle() {
		Record record = new Record(detail.getValuesManager().getValues());
		return (record.getAttributeAsString("idClassificaSup") != null && !"".equals(record.getAttributeAsString("idClassificaSup"))) ? "Modifica sotto-classifica "
				+ getTipoEstremiRecord(record)
				: "Modifica classifica " + getTipoEstremiRecord(record);
	}

	@Override
	public String getViewDetailTitle() {
		Record record = new Record(detail.getValuesManager().getValues());
		return (record.getAttributeAsString("idClassificaSup") != null && !"".equals(record.getAttributeAsString("idClassificaSup"))) ? "Dettaglio sotto-classifica "
				+ getTipoEstremiRecord(record)
				: "Dettaglio classifica " + getTipoEstremiRecord(record);
	}

	@Override
	public String getTipoEstremiRecord(Record record) {
		if (record.getAttributeAsString("livelloCorrente") != null && !"".equals(record.getAttributeAsString("livelloCorrente"))) {
			return record.getAttributeAsString("livelloCorrente") + "." + record.getAttributeAsString("livello") + " - "
					+ record.getAttributeAsString("descrizione");
		} else if (record.getAttributeAsString("livello") != null && !"".equals(record.getAttributeAsString("livello"))) {
			return record.getAttributeAsString("livello") + " - " + record.getAttributeAsString("descrizione");
		} else if (record.getAttributeAsString("indice") != null && !"".equals(record.getAttributeAsString("indice"))) {
			return record.getAttributeAsString("indice") + " - " + record.getAttributeAsString("descrizione");
		} else {
			return record.getAttributeAsString("descrizione");
		}
	}

	@Override
	public void newMode() {
		super.newMode();
		altreOpButton.hide();
	}

	@Override
	public void viewMode() {
		super.viewMode();
		if (isAbilToMod()) {
			editButton.show();
		} else {
			editButton.hide();
		}
		if (isAbilToDel()) {
			deleteButton.show();
		} else {
			deleteButton.hide();
		}
		altreOpButton.hide();
		Record record = new Record(detail.getValuesManager().getValues());
		if (isLookup() && record.getAttributeAsBoolean("flgSelXFinalita")) {
			lookupButton.show();
		} else {
			lookupButton.hide();
		}
	}

	@Override
	public void editMode(boolean fromViewMode) {
		super.editMode(fromViewMode);
		altreOpButton.hide();
	}

	public void setMultiselect(Boolean multiselect) {
		super.setMultiselect(multiselect);
		BigDecimal nroLivello = new BigDecimal(navigator.getPercorso().getLength() - 1);
		if (!isAbilToInsInLivello(nroLivello) || (getFinalita() != null && !"".equals(getFinalita()))) {
			newButton.hide();
		} else {
			String idFolder = getIdFolderCorrente();
			if (idFolder != null && !"".equals(idFolder)) {
				newButton.setPrompt("Nuova sotto-classifica");
			} else {
				newButton.setPrompt("Nuova classifica");
			}
			newButton.show();
		}
	}

	@Override
	public void cercaMode() {
		super.cercaMode();
		BigDecimal nroLivello = new BigDecimal(navigator.getPercorso().getLength() - 1);
		if (!isAbilToInsInLivello(nroLivello) || (getFinalita() != null && !"".equals(getFinalita()))) {
			newButton.hide();
		} else {
			String idFolder = getIdFolderCorrente();
			if (idFolder != null && !"".equals(idFolder)) {
				newButton.setPrompt("Nuova sotto-classifica");
			} else {
				newButton.setPrompt("Nuova classifica");
			}
			newButton.show();
		}
	}

	@Override
	public void esploraMode(CacheLevelBean cacheLevel) {
		super.esploraMode(cacheLevel);
		BigDecimal nroLivello = new BigDecimal(navigator.getPercorso().getLength() - 1);
		if (!isAbilToInsInLivello(nroLivello) || (getFinalita() != null && !"".equals(getFinalita()))) {
			newButton.hide();
		} else {
			String idFolder = getIdFolderCorrente();
			if (idFolder != null && !"".equals(idFolder)) {
				newButton.setPrompt("Nuova sotto-classifica");
			} else {
				newButton.setPrompt("Nuova classifica");
			}
			newButton.show();
		}
	}

	@Override
	public AdvancedCriteria buildSearchCriteria(AdvancedCriteria criteria) {
		List<Criterion> criterionList = new ArrayList<Criterion>();
		if (criteria == null) {
			criteria = new AdvancedCriteria();
		} else {
			for (Criterion crit : criteria.getCriteria()) {
				criterionList.add(crit);
			}
		}

		if (idPianoClassif != null && !"".equals(idPianoClassif)) {
			criterionList.add(new Criterion("idPianoClassif", OperatorId.EQUALS, idPianoClassif));
		}
		if (flgSoloAttive != null) {
			criterionList.add(new Criterion("flgSoloAttive", OperatorId.EQUALS, flgSoloAttive));
		}
		if (tsRif != null && !"".equals(tsRif)) {
			criterionList.add(new Criterion("tsRif", OperatorId.EQUALS, tsRif));
		}

		Criterion[] criterias = new Criterion[criterionList.size()];
		for (int i = 0; i < criterionList.size(); i++) {
			criterias[i] = criterionList.get(i);
		}
		return super.buildSearchCriteria(new AdvancedCriteria(OperatorId.AND, criterias));
	}

	@Override
	public void setAltriParametriPercorsoIniziale(java.util.Map<String, String> altriParametri) {
		this.altriParametri = altriParametri;
		setIdPianoClassif(altriParametri.get("idPianoClassif"));
		((GWTRestDataSource) tree.getDataSource()).addParam("idPianoClassif", getIdPianoClassif());
	};

	@Override
	public void doLookup(Record record) {
		if (record.getAttributeAsBoolean("flgSelXFinalita")) {
			super.doLookup(record);
		} else {
			Layout.addMessage(new MessageBean(I18NUtil.getMessages().recordNonSelezionabileXFinalita_message(), "", MessageType.WARNING));
		}
	}

	@Override
	protected Record createMultiLookupRecord(Record record) {
		final Record newRecord = new Record();
		if (record.getAttribute("idClassificazione") != null && !"".equals(record.getAttributeAsString("idClassificazione"))) {
			newRecord.setAttribute("id", record.getAttributeAsString("idClassificazione"));
			String indice = record.getAttributeAsString("indice");
			String descrizione = record.getAttributeAsString("descrizione");
			newRecord.setAttribute("nome", indice != null && !"".equals(indice) ? indice + " - " + descrizione : descrizione);
		} else if (record.getAttribute("idFolder") != null && !"".equals(record.getAttributeAsString("idFolder"))) {
			newRecord.setAttribute("id", record.getAttributeAsString("idFolder"));
			newRecord.setAttribute("nome", record.getAttributeAsString("nome"));
		}
		newRecord.setAttribute("icona", nomeEntita + "/tipo/" + record.getAttributeAsString("tipo") + ".png");
		return newRecord;
	}

	@Override
	public void manageNewClick() {
		detail.markForRedraw();
		String idFolder = getIdFolderCorrente();
		if (idFolder != null && !"".equals(idFolder)) {
			Record record = new Record();
			record.setAttribute("idClassificazione", idFolder);
			detail.getDataSource().performCustomOperation("get", record, new DSCallback() {

				@Override
				public void execute(DSResponse response, Object rawData, DSRequest request) {
					if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
						Record record = response.getData()[0];
						Map<String, String> mappa = new HashMap<String, String>();
						BigDecimal nroLivello = new BigDecimal(navigator.getPercorso().getLength() - 1);
						mappa.put("nroLivello", nroLivello != null ? String.valueOf(nroLivello.intValue()) : null);
						mappa.put("idPianoClassif", idPianoClassif);
						mappa.put("idClassificaSup", record.getAttribute("idClassificazione"));
						mappa.put("livelloCorrente", record.getAttribute("indice"));
						detail.editNewRecord(mappa);
						detail.getValuesManager().clearErrors(true);
						setIdNodeToOpen(getNavigator().getCurrentNode().getIdNode());
						newMode();
					}
				}
			}, new DSRequest());
		} else {
			Map<String, String> mappa = new HashMap<String, String>();
			BigDecimal nroLivello = new BigDecimal(navigator.getPercorso().getLength() - 1);
			mappa.put("nroLivello", nroLivello != null ? String.valueOf(nroLivello.intValue()) : null);
			mappa.put("idPianoClassif", idPianoClassif);
			mappa.put("idClassificaSup", null);
			mappa.put("livelloCorrente", null);
			detail.editNewRecord(mappa);
			detail.getValuesManager().clearErrors(true);
			setIdNodeToOpen(getNavigator().getCurrentNode().getIdNode());
			newMode();
		}
	}

	public void onSaveButtonClickSuper() {
		super.onSaveButtonClick();
	}

	@Override
	public void onSaveButtonClick() {
		final Record record = new Record(detail.getValuesManager().getValues());
		if (detail.validate()) {
			DSOperationType saveOperationType = detail.getValuesManager().getSaveOperationType();
			Layout.showWaitPopup("Salvataggio in corso: potrebbe richiedere qualche secondo. Attendere...");
			DSCallback callback = new DSCallback() {

				@Override
				public void execute(DSResponse response, Object rawData, DSRequest request) {
					if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
						final Record savedRecord = response.getData()[0];
						if (savedRecord.getAttributeAsBoolean("flgDatiDaStoricizzare") == null || !savedRecord.getAttributeAsBoolean("flgDatiDaStoricizzare")) {
							if(savedRecord.getAttribute("flgIgnoreWarning") == null || savedRecord.getAttributeAsInt("flgIgnoreWarning") != 1) {
								try {
									list.manageLoadDetail(savedRecord, detail.getRecordNum(), new DSCallback() {

										@Override
										public void execute(DSResponse response, Object rawData, DSRequest request) {
											viewMode();
											Layout.hideWaitPopup();
											Layout.addMessage(new MessageBean(
													I18NUtil.getMessages().afterSave_message(getTipoEstremiRecord(response.getData()[0])), "", MessageType.INFO));
										}
									});
								} catch (Exception e) {
									Layout.hideWaitPopup();
								}
							} else {
								detail.getValuesManager().setValue("flgIgnoreWarning", "1");
								Layout.hideWaitPopup();							
							}
						} else {
							Layout.hideWaitPopup();
							SC.ask("Attenzione!", "L'indice e la descrizione sono stati modificati. Vuoi storicizzare i vecchi dati?", new BooleanCallback() {

								@Override
								public void execute(Boolean value) {
									if(savedRecord.getAttribute("flgIgnoreWarning") == null || savedRecord.getAttributeAsInt("flgIgnoreWarning") != 1) {
										if (value != null && value) {
											detail.getValuesManager().setValue("flgStoricizzaDati", true);
										} else {
											detail.getValuesManager().setValue("flgStoricizzaDati", false);
										}
										onSaveButtonClickSuper();
									} else {
										detail.getValuesManager().setValue("flgIgnoreWarning", "1");
										Layout.hideWaitPopup();							
									}	
								}
							});
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
				if ((saveOperationType != null && saveOperationType.equals(DSOperationType.ADD)) || record.getAttribute("idClassificazione") == null
						|| record.getAttribute("idClassificazione").equals("")) {
					detail.getDataSource().addData(record, callback);
				} else {
					detail.getDataSource().updateData(record, callback);
				}
			} catch (Exception e) {
				Layout.hideWaitPopup();
			}
		} else {
			Layout.addMessage(new MessageBean(I18NUtil.getMessages().validateError_message(), "", MessageType.ERROR));
		}
	}

	public String getIdPianoClassif() {
		return idPianoClassif;
	}

	public void setIdPianoClassif(String idPianoClassif) {
		this.idPianoClassif = idPianoClassif;
	}

	public String getNroLivelliPiano() {
		return nroLivelliPiano;
	}

	public void setNroLivelliPiano(String nroLivelliPiano) {
		this.nroLivelliPiano = nroLivelliPiano;
	}

	public Boolean getFlgSoloAttive() {
		return flgSoloAttive;
	}

	public String getTsRif() {
		return tsRif;
	}
	
	@Override
	protected GWTRestDataSource createNroRecordDatasource() {
		
		GWTRestDataSource gWTRestDataSource = (GWTRestDataSource) getList().getDataSource();
		gWTRestDataSource.setForceToShowPrompt(false);

		return gWTRestDataSource;
	}
	
	@Override
	protected Record[] extractRecords(String[] fields) {
		// Se sono in overflow i dati verranno recuperati con il metodo asincrono,
		// altrimenti utilizzo quelli nella lista a GUI
		if (overflow){
			return new Record[0];
		} else {
			return super.extractRecords(fields);
		}
	}

}