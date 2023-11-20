/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.HashMap;
import java.util.Map;

import com.smartgwt.client.data.DSCallback;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.data.Record;

import it.eng.auriga.ui.module.layout.client.protocollazione.IstanzeDetail;
import it.eng.utility.ui.module.core.client.datasource.GWTRestDataSource;
import it.eng.utility.ui.module.layout.client.Layout;
import it.eng.utility.ui.module.layout.client.portal.ModalWindow;

public abstract class IstanzeWindow extends ModalWindow {

	protected IstanzeWindow _window;
	protected IstanzeDetail portletLayout;

	protected Record record;

	public IstanzeWindow(final String nomeEntita, Record pRecord) {

		super(nomeEntita, true);

		_window = this;

		this.record = pRecord;

		settingsMenu.removeItem(separatorMenuItem);
		settingsMenu.removeItem(autoSearchMenuItem);

		setTitle("");

		portletLayout = new IstanzeDetail(nomeEntita);

		manageOperationOnLayout(record);

		portletLayout.setCanEdit(false);

		portletLayout.setHeight100();
		portletLayout.setWidth100();

		setBody(portletLayout);
		setIcon("menu/istanze.png");
	}

	protected void manageOperationOnLayout(Record lRecord) {
	}

	@Override
	public void manageOnCloseClick() {
		
		if (portletLayout.isSaved()) {
			if (getIsModal()) {
				markForDestroy();
			} else {
				Layout.removePortlet(getNomeEntita());
			}
			manageAfterCloseWindow();
		} else {
			markForDestroy();
		}
	}

	public String getTipoIstanza() {
		if (nomeEntita.equalsIgnoreCase("istanze_ced")) {
			return "CED";
		} else if (nomeEntita.equalsIgnoreCase("istanze_autotutela")) {
			return "AUTOTUTELA";
		}
		return null;
	}

	public abstract void manageAfterCloseWindow();

	public void newRecord(final String descTipoDocumento) {
		Record lRecord = new Record();
		lRecord.setAttribute("descTipoDocumento", descTipoDocumento);
		GWTRestDataSource lGwtRestDataSource = new GWTRestDataSource("ProtocolloDataSource");
		lGwtRestDataSource.executecustom("getIdDocType", lRecord, new DSCallback() {

			@Override
			public void execute(DSResponse response, Object rawData, DSRequest request) {
				if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
					Record data = response.getData()[0];
					String idTipoDocumento = data.getAttribute("idTipoDocumento");
					Map<String, Object> initialValues = null;
					if (idTipoDocumento != null && !"".equals(idTipoDocumento)) {
						initialValues = new HashMap<String, Object>();
						initialValues.put("tipoDocumento", idTipoDocumento);
					}
					portletLayout.editNewRecord(initialValues);
					portletLayout.setInitialValues();
					portletLayout.newMode();
					_window.setTitle("Nuova istanza " + descTipoDocumento);
					_window.show();
				}
			}
		});
	}

	public void viewRecord(String idUd) {
		Record lRecord = new Record();
		lRecord.setAttribute("idUd", idUd);
		GWTRestDataSource lGwtRestDataSource = new GWTRestDataSource("ProtocolloDataSource");
		lGwtRestDataSource.performCustomOperation("get", lRecord, new DSCallback() {

			@Override
			public void execute(DSResponse response, Object rawData, DSRequest request) {
				if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
					Record record = response.getData()[0];
					portletLayout.editRecord(record);
					portletLayout.viewMode();
					portletLayout.getValuesManager().clearErrors(true);
					_window.setTitle("Dettaglio istanza " + portletLayout.getTipoEstremiDocumento());
					_window.show();
				}
			}
		}, new DSRequest());

	}

	public void editRecord(String idUd) {
		Record lRecord = new Record();
		lRecord.setAttribute("idUd", idUd);
		GWTRestDataSource lGwtRestDataSource = new GWTRestDataSource("ProtocolloDataSource");
		lGwtRestDataSource.performCustomOperation("get", lRecord, new DSCallback() {

			@Override
			public void execute(DSResponse response, Object rawData, DSRequest request) {
				if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
					Record record = response.getData()[0];
					portletLayout.editRecord(record);
					String tipoProtocollo = record.getAttribute("tipoProtocollo");
					if (tipoProtocollo != null && tipoProtocollo.equalsIgnoreCase("PG")) {
						portletLayout.modificaDatiMode();
					} else {
						portletLayout.editMode();
					}
					portletLayout.getValuesManager().clearErrors(true);
					_window.setTitle("Modifica istanza " + portletLayout.getTipoEstremiDocumento());
					_window.show();
				}
			}
		}, new DSRequest());
	}
	
	public void editRecord(final Record record, String descTipoDocumento) {
		
		Record lRecord = new Record();
		lRecord.setAttribute("descTipoDocumento", descTipoDocumento);
		GWTRestDataSource lGwtRestDataSource = new GWTRestDataSource("ProtocolloDataSource");
		lGwtRestDataSource.executecustom("getIdDocType", lRecord, new DSCallback() {

			@Override
			public void execute(DSResponse response, Object rawData, DSRequest request) {
				if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
					Record data = response.getData()[0];
					String idTipoDocumento = data.getAttribute("idTipoDocumento");
					if (idTipoDocumento != null && !"".equals(idTipoDocumento)) {
						record.setAttribute("tipoDocumento", idTipoDocumento);
					}
					portletLayout.editRecord(record);
					String tipoProtocollo = record.getAttribute("tipoProtocollo");
					if (tipoProtocollo != null && tipoProtocollo.equalsIgnoreCase("PG")) {
						portletLayout.modificaDatiMode();
					} else {
						portletLayout.editMode();
					}
					portletLayout.getValuesManager().clearErrors(true);
					_window.setTitle("Modifica istanza " + portletLayout.getTipoEstremiDocumento());
					_window.show();
				}
			}
		});
	}
	
	public void editNewRecordFromEmail(final Record lRecordEmail) {

		final String descTipoDocumento = getTipoIstanza();

		// Cerco l'id tipo doc
		Record lRecordTipoDoc = new Record();
		lRecordTipoDoc.setAttribute("descTipoDocumento", descTipoDocumento);
		GWTRestDataSource lGwtRestDataSource = new GWTRestDataSource("ProtocolloDataSource");
		lGwtRestDataSource.executecustom("getIdDocType", lRecordTipoDoc, new DSCallback() {

			@Override
			public void execute(DSResponse response, Object rawData, DSRequest request) {
				if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
					Record data = response.getData()[0];
					String idTipoDocumento = data.getAttribute("idTipoDocumento");
					lRecordEmail.setAttribute("tipoDocumento", idTipoDocumento);							
					if(lRecordEmail.getAttributeAsBoolean("flgPEC") != null && lRecordEmail.getAttributeAsBoolean("flgPEC")) {
						lRecordEmail.setAttribute("mezzoTrasmissione", "PEC");	
					} else {
						lRecordEmail.setAttribute("mezzoTrasmissione", "PEO");	
					}
					lRecordEmail.setAttribute("supportoOriginale", "digitale");		
					portletLayout.editNewRecord(lRecordEmail.toMap());
					portletLayout.setInitialValues();
					// portletLayout.setTitle("Nuova istanza " + descTipoDocumento);
					_window.setTitle("Nuova istanza " + descTipoDocumento);
					portletLayout.newMode();
				}
			}
		});

	}

}