/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import com.google.gwt.regexp.shared.RegExp;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.widgets.events.FetchDataEvent;
import com.smartgwt.client.widgets.events.FetchDataHandler;
import com.smartgwt.client.widgets.form.fields.HiddenItem;
import com.smartgwt.client.widgets.form.fields.events.ChangeEvent;
import com.smartgwt.client.widgets.form.fields.events.ChangeHandler;
import com.smartgwt.client.widgets.form.fields.events.ChangedEvent;
import com.smartgwt.client.widgets.form.fields.events.ChangedHandler;
import com.smartgwt.client.widgets.form.validator.CustomValidator;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.grid.ListGridRecord;

import it.eng.utility.ui.module.core.client.datasource.GWTRestDataSource;
import it.eng.utility.ui.module.layout.client.common.ReplicableCanvas;
import it.eng.utility.ui.module.layout.client.common.items.SelectItem;
import it.eng.utility.ui.module.layout.client.common.items.TextItem;

/**
 * 
 * @author Cristiano Daniele
 *
 */

public class CampoCaricaRubricaClientiCanvas extends ReplicableCanvas {

	protected ReplicableCanvasForm replicableCanvasForm;
	protected SelectItem attributoSelectItem;
	protected TextItem colonnaItem;
	private GWTRestDataSource attributoDatasource;
	private HiddenItem typeItem;
	private HiddenItem descrizioneNomeCampoItem;

	public CampoCaricaRubricaClientiCanvas(CampoCaricaRubricaClientiItem item) {
		super(item);
	}

	@Override
	public void disegna() {

		replicableCanvasForm = new ReplicableCanvasForm();
		replicableCanvasForm.setWrapItemTitles(false);
		replicableCanvasForm.setNumCols(5);
		replicableCanvasForm.setColWidths("1", "1", "1", "*", "*");

		typeItem = new HiddenItem("type");
		descrizioneNomeCampoItem = new HiddenItem("descrizioneNomeCampo");

		attributoDatasource = new GWTRestDataSource("LoadComboTipiAttributoCampoRubricaDataSource");
		attributoSelectItem = new SelectItem("nomeCampo");
		attributoSelectItem.setShowTitle(false);
		attributoSelectItem.setStartRow(true);
		attributoSelectItem.setOptionDataSource(attributoDatasource);
		attributoSelectItem.setDisplayField("value");
		attributoSelectItem.setValueField("key");
		attributoSelectItem.setWidth(350);
		attributoSelectItem.setWrapTitle(false);
		attributoSelectItem.setAllowEmptyValue(false);
		attributoSelectItem.setAutoFetchData(false);
		attributoSelectItem.setColSpan(1);
		
		attributoSelectItem.setAlwaysFetchMissingValues(true);
		attributoSelectItem.setFetchMissingValues(true);
		
		ListGrid pickListProperties = new ListGrid();
		pickListProperties.addFetchDataHandler(new FetchDataHandler() {

			@Override
			public void onFilterData(FetchDataEvent event) {
				String companyID = ((CampoCaricaRubricaClientiItem) getItem()).getCompanyId() != null ? ((CampoCaricaRubricaClientiItem) getItem())
						.getCompanyId() : "";
				// descrizioneCompanyId
				if (companyID.equals("")) {
					companyID = ((CampoCaricaRubricaClientiItem) getItem()).getAttribute("companyId");
				}
				GWTRestDataSource organigrammaDS = (GWTRestDataSource) attributoSelectItem.getOptionDataSource();
				organigrammaDS.addParam("codTipo", companyID);
				attributoSelectItem.setOptionDataSource(organigrammaDS);
				attributoSelectItem.invalidateDisplayValueCache();
			}
		});
		attributoSelectItem.setPickListProperties(pickListProperties);
//		attributoSelectItem.addChangeHandler(new ChangeHandler() {
//
//			@Override
//			public void onChange(ChangeEvent event) {
//				ListGridRecord selectedRecord = event.getItem().getSelectedRecord();
//				if (event.getItem().getSelectedRecord().getAttribute("type") != null) {
//					replicableCanvasForm.setValue("type", event.getItem().getSelectedRecord().getAttribute("type"));
//				}
//				if (event.getItem().getSelectedRecord().getAttribute("value") != null) {
//					replicableCanvasForm.setValue("descrizioneNomeCampo", event.getItem().getSelectedRecord().getAttribute("value"));
//				}
//				markForRedraw();
//			}
//		});

		attributoSelectItem.addChangedHandler(new ChangedHandler() {

			@Override
			public void onChanged(ChangedEvent event) {
				ListGridRecord selectedRecord = event.getItem().getSelectedRecord();
				if (event.getItem().getSelectedRecord().getAttribute("type") != null) {
					replicableCanvasForm.setValue("type", event.getItem().getSelectedRecord().getAttribute("type"));
				}
				if (event.getItem().getSelectedRecord().getAttribute("value") != null) {
					replicableCanvasForm.setValue("descrizioneNomeCampo", event.getItem().getSelectedRecord().getAttribute("value"));
				}
				markForRedraw();
			}
		});

		colonnaItem = new TextItem("colonnaRif", "Da colonna xls (lettera; A, B, C..)");
		colonnaItem.setColSpan(1);
		colonnaItem.setWidth(100);
		CustomValidator lCustomValidator = new CustomValidator() {

			@Override
			protected boolean condition(Object value) {
				if (value == null || value.toString().equals(""))
					return true;
				RegExp regExp1 = RegExp.compile("[a-zA-Z]{1}");
				RegExp regExp2 = RegExp.compile("[a-zA-Z]{1}\\d+");
				String lString = (String) value;
				String[] values = lString.split(";");
				boolean res = true;
				for (String val : values) {
					if (val == null || val.equals(""))
						res = res && true;
					else
						res = res && (regExp1.test(val.trim()) || regExp2.test(val.trim()));
				}
				return res;
			}
		};
		lCustomValidator.setErrorMessage("Valore non corretto, inserire lettera singola (es: A ) oppure lettera più numero/i (es: A10,A101");
		colonnaItem.setValidators(lCustomValidator);
		colonnaItem.addChangedHandler(new ChangedHandler() {

			@Override
			public void onChanged(ChangedEvent event) {
				colonnaItem.validate();
			}
		});

		replicableCanvasForm.setFields(typeItem, descrizioneNomeCampoItem, attributoSelectItem, colonnaItem);

		addChild(replicableCanvasForm);
	}

	@Override
	public ReplicableCanvasForm[] getForm() {
		return new ReplicableCanvasForm[] { replicableCanvasForm };
	}
	
	
	@Override
	public void editRecord(Record record) {
		
		manageLoadSelectInEditRecord(record, attributoSelectItem, "nomeCampo", new String[]{"descrizioneNomeCampo"}, "key");
		super.editRecord(record);					
	}	

}
