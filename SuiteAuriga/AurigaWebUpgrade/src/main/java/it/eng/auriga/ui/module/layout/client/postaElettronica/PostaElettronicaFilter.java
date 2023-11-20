/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

import com.smartgwt.client.data.AdvancedCriteria;
import com.smartgwt.client.data.Criteria;
import com.smartgwt.client.data.Criterion;
import com.smartgwt.client.data.DataSourceField;
import com.smartgwt.client.types.OperatorId;
import com.smartgwt.client.widgets.events.KeyPressEvent;
import com.smartgwt.client.widgets.events.KeyPressHandler;
import com.smartgwt.client.widgets.form.PickListMenu;
import com.smartgwt.client.widgets.form.events.FilterChangedEvent;
import com.smartgwt.client.widgets.form.events.FilterChangedHandler;
import com.smartgwt.client.widgets.form.fields.FormItem;
import com.smartgwt.client.widgets.form.fields.FormItemFunctionContext;
import com.smartgwt.client.widgets.form.fields.SelectItem;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridRecord;

import it.eng.auriga.ui.module.layout.client.AurigaLayout;
import it.eng.utility.ui.module.core.client.datasource.FieldFetchDataSource;
import it.eng.utility.ui.module.core.client.datasource.GWTRestDataSource;
import it.eng.utility.ui.module.layout.client.common.ConfigurableFilter;
import it.eng.utility.ui.module.layout.client.common.SelectItemFiltrabile;
import it.eng.utility.ui.module.layout.shared.bean.FilterFieldBean;

public class PostaElettronicaFilter extends ConfigurableFilter {

	private String classifica;
	private String idUtenteModPec;
	// private String showFilterCategoria;
	private String showFilterDataRicezione;
	private String showFilterDataInvio;
	private String showFilterFlgAssegnazioneEffettuata;
	private String showFilterFlgIncludiAssegnateAdAltri;
	private String showFilterInviataRisposta;
	private String showFilterInoltrata;
	private String showFilterProgressivo;

	public PostaElettronicaFilter(String lista) {
		this(lista, null);
	}

	public PostaElettronicaFilter(String lista, Map<String, String> extraparam) {

		super(lista, extraparam);

		updateShowFilter(extraparam);

		addFilterChangedHandler(new FilterChangedHandler() {

			@Override
			public void onFilterChanged(FilterChangedEvent event) {

				// int posCategoria = getClausePosition("categoria");
				// if(posCategoria != -1) {
				// if(showFilterCategoria.equalsIgnoreCase("N")) {
				// removeClause(getClause(posCategoria));
				// } else {
				// FormItem lClauseValueItem = (FormItem) getClauseValueItem(posCategoria);
				// SelectItem lClauseValueSelectItem = (SelectItem) lClauseValueItem;
				// GWTRestDataSource datasource = (GWTRestDataSource) (lClauseValueSelectItem).getOptionDataSource();
				// datasource.addParam("classifica", classifica);
				// lClauseValueSelectItem.setOptionDataSource(datasource);
				// lClauseValueSelectItem.redraw();
				// }
				// }

				int posCasellaRicezione = getClausePosition("casellaRicezione");
				if (posCasellaRicezione != -1) {
					FormItem lClauseValueItem = (FormItem) getClauseValueItem(posCasellaRicezione);
					SelectItem lClauseValueSelectItem = (SelectItem) lClauseValueItem;
					GWTRestDataSource datasource = (GWTRestDataSource) (lClauseValueSelectItem).getOptionDataSource();
					datasource.addParam("idUtenteModPec", idUtenteModPec);
					lClauseValueSelectItem.setOptionDataSource(datasource);
					lClauseValueSelectItem.redraw();
				}

				// Filtro "Data di ricezione"
				int posDataRicezione = getClausePosition("dataRicezione");
				if (posDataRicezione != -1) {
					if (showFilterDataRicezione.equalsIgnoreCase("N")) {
						removeClause(getClause(posDataRicezione));
					}
				}

				// Filtro "Inviata Risposta"
				int posInviataRisposta = getClausePosition("inviataRisposta");
				if (posInviataRisposta != -1) {
					if (showFilterInviataRisposta.equalsIgnoreCase("N")) {
						removeClause(getClause(posInviataRisposta));
					}
				}

				// Filtro "Inoltrata"
				int posInoltrata = getClausePosition("inoltrata");
				if (posInoltrata != -1) {
					if (showFilterInoltrata.equalsIgnoreCase("N")) {
						removeClause(getClause(posInoltrata));
					}
				}

				// Filtro "Data di invio"
				int posDataInvio = getClausePosition("dataInvio");
				if (posDataInvio != -1) {
					if (showFilterDataInvio.equalsIgnoreCase("N")) {
						removeClause(getClause(posDataInvio));
					}
				}

				// Filtro "Assegnazione effettuata"
				int posFlgAssegnazioneEffettuata = getClausePosition("flgAssegnazioneEffettuata");
				if (posFlgAssegnazioneEffettuata != -1) {
					if (showFilterFlgAssegnazioneEffettuata.equalsIgnoreCase("N")) {
						removeClause(getClause(posFlgAssegnazioneEffettuata));
					}
				}

				// Filtro "Includi assegnate ad altri"
				int posFlgIncludiAssegnateAdAltri = getClausePosition("flgIncludiAssegnateAdAltri");
				if (posFlgIncludiAssegnateAdAltri != -1) {
					if (showFilterFlgIncludiAssegnateAdAltri.equalsIgnoreCase("N")) {
						removeClause(getClause(posFlgIncludiAssegnateAdAltri));
					}
				}

				int posflgProgressivo = getClausePosition("flgProgressivo");
				if (posflgProgressivo != -1) {
					if (showFilterProgressivo.equalsIgnoreCase("N")) {
						removeClause(getClause(posflgProgressivo));
					}
				}

			}
		});
	}

	public void updateShowFilter(Map<String, String> extraparam) {
		setExtraParam(extraparam);
		this.classifica = getExtraParam().get("classifica");
		this.idUtenteModPec = getExtraParam().get("idUtenteModPec");
		if (classifica != null) {
			// showFilterCategoria = classifica != null && ("standard.arrivo".equals(classifica) ||
			// "standard.arrivo.ricevute_notifiche_non_associate".equals(classifica)) ? "S" : "N";
			showFilterDataRicezione = classifica != null && (classifica.startsWith("standard.arrivo") || classifica.startsWith("standard.archiviata.arrivo")) ? "S"
					: "N";
			// Il filtro della data d'invio va sempre mostrato. Fix Ticket AURIGA-52 
//			showFilterDataInvio = classifica != null
//					&& (classifica.contains(".invio") || (classifica.contains(".arrivo") && (classifica.contains(".pec") || classifica
//							.contains(".interoperabili")))) ? "S" : "N";
			showFilterDataInvio = "S";
			showFilterFlgAssegnazioneEffettuata = classifica != null && (classifica.startsWith("standard.arrivo")) ? "S" : "N";
			showFilterFlgIncludiAssegnateAdAltri = classifica != null && (classifica.startsWith("standard.arrivo")) ? "S" : "N";
			showFilterInviataRisposta = classifica != null && (classifica.startsWith("standard.arrivo") || classifica.startsWith("standard.archiviata.arrivo")) ? "S"
					: "N";
			showFilterInoltrata = classifica != null && (classifica.startsWith("standard.arrivo") || classifica.startsWith("standard.archiviata.arrivo")) ? "S"
					: "N";
			// Il filtro del numero progressivo va sempre mostrato
			// showFilterProgressivo = classifica != null && (classifica.startsWith("standard.arrivo") || classifica.startsWith("standard.archiviata.arrivo")) ?
			// "S" : "N";
			showFilterProgressivo = "S";
		} else {
			showFilterDataRicezione = "S";
			showFilterDataInvio = "S";
			showFilterFlgAssegnazioneEffettuata = "S";
			showFilterFlgIncludiAssegnateAdAltri = "S";
			showFilterInviataRisposta = "S";
			showFilterInoltrata = "S";
			showFilterProgressivo = "S";
		}
	}

	@Override
	public LinkedHashMap<String, String> getMappaFiltriToShow(LinkedHashMap<String, String> lMap) {
	
		// if(lMap.containsKey("categoria") && showFilterCategoria.equalsIgnoreCase("N")) {
		// lMap.remove("categoria");
		// }

		if (lMap.containsKey("dataRicezione") && showFilterDataRicezione.equalsIgnoreCase("N")) {
			lMap.remove("dataRicezione");
		}

		if (lMap.containsKey("inoltrata") && showFilterInoltrata.equalsIgnoreCase("N")) {
			lMap.remove("inoltrata");
		}

		if (lMap.containsKey("inviataRisposta") && showFilterInviataRisposta.equalsIgnoreCase("N")) {
			lMap.remove("inviataRisposta");
		}

		if (lMap.containsKey("dataInvio") && showFilterDataInvio.equalsIgnoreCase("N")) {
			lMap.remove("dataInvio");
		}

		if (lMap.containsKey("flgAssegnazioneEffettuata") && showFilterFlgAssegnazioneEffettuata.equalsIgnoreCase("N")) {
			lMap.remove("flgAssegnazioneEffettuata");
		}

		if (lMap.containsKey("flgIncludiAssegnateAdAltri") && showFilterFlgIncludiAssegnateAdAltri.equalsIgnoreCase("N")) {
			lMap.remove("flgIncludiAssegnateAdAltri");
		}

		if (lMap.containsKey("progressivo") && showFilterProgressivo.equalsIgnoreCase("N")) {
			lMap.remove("progressivo");
		}

		return lMap;
	}

	@Override
	public void createFilteredSelectItem(FilterFieldBean pFilterFieldBean, DataSourceField pDataSourceField) {
		final SelectItemFiltrabile lSelectItem = new SelectItemFiltrabile(filter, pFilterFieldBean, pDataSourceField);
		if ("casellaRicezione".equals(pDataSourceField.getName())) {
			GWTRestDataSource datasource = (GWTRestDataSource) lSelectItem.getOptionDataSource();
			datasource.addParam("classifica", getExtraParam().get("classifica"));
			// datasource.addParam("idUtenteModPec", getExtraParam().get("idUtenteModPec"));
			if (AurigaLayout.getIsAttivaAccessibilita()) {
				ListGrid pickListProperties = new ListGrid();
				pickListProperties.addKeyPressHandler(new KeyPressHandler() {
					
					@Override
					public void onKeyPress(KeyPressEvent event) {
						if (event.getKeyName().equalsIgnoreCase("Space")) {
							event.cancel();
							boolean isValueToRemove = false;
							int posCasellaRicezione = getClausePosition("casellaRicezione");
							if (posCasellaRicezione != -1) {
								FormItem lClauseValueItem = (FormItem) getClauseValueItem(posCasellaRicezione);
								SelectItem lClauseValueSelectItem = (SelectItem) lClauseValueItem;
								ArrayList<String> valuesList = (ArrayList<String>) lClauseValueSelectItem.getValue() != null ? (ArrayList<String>) lClauseValueSelectItem.getValue() : new ArrayList<String>();
								PickListMenu pickListMenu = (PickListMenu) event.getFiringCanvas();
								ListGridRecord[] listGridRecord = pickListMenu.getRecords();
								Integer focusRow = pickListMenu.getFocusRow();
								if (focusRow > 0) {
									focusRow--;
								} else {
									focusRow = listGridRecord.length-1;
								}
								pickListMenu.focusInRow(focusRow);
								if (pickListMenu.isSelected(listGridRecord[focusRow])) {
									pickListMenu.deselectRecord(focusRow);
									isValueToRemove = true;
								} else {
									pickListMenu.selectRecord(focusRow);
								}
								if (valuesList.size() > 0 && isValueToRemove) {
									for (String value : valuesList) {
										if (value.equals(listGridRecord[focusRow].getAttribute("key"))) {
											valuesList.remove(value);
											break;
										}
									}
								} else {
									valuesList.add(listGridRecord[focusRow].getAttribute("key"));
								}
								Object[] array = valuesList.toArray();
								String[] arrayString = new String[array.length];
								for (int i=0; i < array.length; i++) {
									arrayString[i] = (String) array[i];
								}
								lClauseValueSelectItem.setValues(arrayString);
								pickListMenu.focusInRow(focusRow);
							}
						}
					}
				});
				lSelectItem.setPickListProperties(pickListProperties);
			}
			lSelectItem.setOptionDataSource(datasource);
		}
		// else if("categoria".equals(pDataSourceField.getName())) {
		// GWTRestDataSource datasource = (GWTRestDataSource) lSelectItem.getOptionDataSource();
		// datasource.addParam("classifica", getExtraParam().get("classifica"));
		// lSelectItem.setOptionDataSource(datasource);
		// }
		if (pFilterFieldBean != null && pFilterFieldBean.getSelect() != null && pFilterFieldBean.getSelect().getEmptyPickListMessage() != null
				&& !"".equals(pFilterFieldBean.getSelect().getEmptyPickListMessage())) {
			lSelectItem.setEmptyPickListMessage(pFilterFieldBean.getSelect().getEmptyPickListMessage());
		}
		pDataSourceField.setEditorType(lSelectItem);
		pDataSourceField.setFilterEditorType(SelectItem.class);
		mappaSelects.put(pFilterFieldBean.getName(), lSelectItem);
	}

	@Override
	protected Criteria createCriteria(FormItemFunctionContext itemContext) {
		
		SelectItem lSelectItem = (SelectItem) itemContext.getFormItem();
		AdvancedCriteria lAdvancedCriteria = getCriteria(true);
		Criterion[] lCriterions = lAdvancedCriteria.getCriteria();
		String selected = "";

		// if(showFilterCategoria.equalsIgnoreCase("N")) {
		// selected = "categoria,";
		// }

		if (showFilterDataRicezione.equalsIgnoreCase("N")) {
			selected = selected + "dataRicezione,";
		}

		if (showFilterDataInvio.equalsIgnoreCase("N")) {
			selected = selected + "dataInvio,";
		}

		if (showFilterInoltrata.equalsIgnoreCase("N")) {
			selected = selected + "inoltrata,";
		}

		if (showFilterInviataRisposta.equalsIgnoreCase("N")) {
			selected = selected + "inviataRisposta,";
		}

		if (showFilterFlgAssegnazioneEffettuata.equalsIgnoreCase("N")) {
			selected = selected + "flgAssegnazioneEffettuata,";
		}

		if (showFilterFlgIncludiAssegnateAdAltri.equalsIgnoreCase("N")) {
			selected = selected + "flgIncludiAssegnateAdAltri,";
		}

		if (showFilterProgressivo.equalsIgnoreCase("N")) {
			selected = selected + "progressivo,";
		}

		for (Criterion lCriterion : lCriterions) {
			if (lCriterion.getFieldName() != null && !lCriterion.getFieldName().equals(lSelectItem.getValue())) {
				selected += lCriterion.getFieldName() + ",";
			}
		}
		return new AdvancedCriteria("escludi", OperatorId.EQUALS, selected + ";" + new Date().toString());
	}

	@Override
	protected FieldFetchDataSource getFieldFetchDataSource() {
		FieldFetchDataSource lFieldFetchDataSource = new FieldFetchDataSource("PostaElettronicaListFieldDataSource", "name", nomeEntita);
		lFieldFetchDataSource.addParam("classifica", getExtraParam().get("classifica"));
		lFieldFetchDataSource.addParam("token", "");
		lFieldFetchDataSource.setCacheAllData(false);
		lFieldFetchDataSource.setPreventHTTPCaching(false);
		lFieldFetchDataSource.setFilter(filter);
		lFieldFetchDataSource.setCacheAllData(false);
		return lFieldFetchDataSource;
	}

}
