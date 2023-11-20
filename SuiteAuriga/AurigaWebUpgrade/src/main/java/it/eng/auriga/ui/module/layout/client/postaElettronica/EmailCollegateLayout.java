/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.auriga.ui.module.layout.client.AurigaLayout;
import it.eng.auriga.ui.module.layout.client.i18n.I18NUtil;
import it.eng.utility.ui.module.core.client.datasource.GWTRestDataSource;
import it.eng.utility.ui.module.layout.client.common.CustomDetail;
import it.eng.utility.ui.module.layout.client.common.CustomLayout;

import com.smartgwt.client.data.Record;
import com.smartgwt.client.types.FieldType;

public class EmailCollegateLayout extends CustomLayout {

	private String idEmail;
	private String tipoRel;

	public EmailCollegateLayout(String idEmail, String tipoRel) {
		super("emailcollegate", buildEmilCollegateDatasource(idEmail, tipoRel), null, new EmailCollegateList("emailcollegate", tipoRel),
				switchDetailPostaElettronica(tipoRel));

		this.idEmail = idEmail;
		this.tipoRel = tipoRel;

		multiselectButton.hide();
		newButton.hide();
		refreshListButton.hide();
		topListToolStripSeparator.hide();

		this.setLookup(false);
	}

	public static GWTRestDataSource buildEmilCollegateDatasource(String idEmail, String tipoRel) {
		GWTRestDataSource lGWTRestDataSource = new GWTRestDataSource("EmailCollegateDataSource", "idEmail", FieldType.TEXT);
		lGWTRestDataSource.addParam("idEmail", idEmail);
		lGWTRestDataSource.addParam("tipoRel", tipoRel);
		return lGWTRestDataSource;
	}

	@Override
	public void doSearch() {
		
		((GWTRestDataSource) list.getDataSource()).addParam("idEmail", idEmail);
		((GWTRestDataSource) list.getDataSource()).addParam("tipoRel", tipoRel);
		super.doSearch();
	}

	private static CustomDetail switchDetailPostaElettronica(String tipoRel) {

		CustomDetail portletLayout = null;

		if (!AurigaLayout.getParametroDBAsBoolean("DETT_EMAIL_UNICO")) {
			portletLayout = new PostaElettronicaDetail("emailcollegate", tipoRel);
		} else if (AurigaLayout.getParametroDBAsBoolean("DETT_EMAIL_UNICO")) {
			portletLayout = new DettaglioPostaElettronica("emailcollegate", tipoRel);
		} else {
			portletLayout = new CustomDetail("emailcollegate");
		}
		return portletLayout;
	}

	@Override
	public String getNewDetailTitle() {
		return I18NUtil.getMessages().postainuscitaregistrazione_detail_new_title();
	}

	@Override
	public String getEditDetailTitle() {
		Record record = new Record(detail.getValuesManager().getValues());
		return I18NUtil.getMessages().postainuscitaregistrazione_detail_edit_title(getTipoEstremiRecord(record));
	}

	@Override
	public String getViewDetailTitle() {
		Record record = new Record(detail.getValuesManager().getValues());
		return I18NUtil.getMessages().postainuscitaregistrazione_detail_view_title(getTipoEstremiRecord(record));
	}

	@Override
	public void newMode() {
		super.newMode();
		altreOpButton.hide();
		backToListButton.hide();
	}

	@Override
	public void viewMode() {
		
		super.viewMode();
		Record record = new Record(detail.getValuesManager().getValues());
		deleteButton.hide();
		editButton.hide();
		altreOpButton.hide();
		backToListButton.hide();
	}

	@Override
	public void editMode(boolean fromViewMode) {
		
		super.editMode(fromViewMode);
		altreOpButton.hide();
		backToListButton.hide();
	}

}
