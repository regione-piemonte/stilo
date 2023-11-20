/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.auriga.ui.module.layout.client.i18n.I18NUtil;
import it.eng.utility.ui.module.core.client.datasource.GWTRestDataSource;
import it.eng.utility.ui.module.layout.client.common.ConfigurableFilter;
import it.eng.utility.ui.module.layout.client.common.CustomLayout;

import com.smartgwt.client.data.Record;
import com.smartgwt.client.types.FieldType;

public class RichiesteDocViaPecLayout extends CustomLayout {

	public RichiesteDocViaPecLayout() {
		this(null, null, null);
	}

	public RichiesteDocViaPecLayout(String finalita, Boolean flgSelezioneSingola) {
		this(finalita, flgSelezioneSingola, null);
	}

	public RichiesteDocViaPecLayout(String finalita, Boolean flgSelezioneSingola, Boolean showOnlyDetail) {
		super("invio_documentazione_via_pec", new GWTRestDataSource("RichiesteDocViaPecDataSource", "id", FieldType.TEXT), new ConfigurableFilter(
				"invio_documentazione_via_pec"), new RichiesteDocViaPecList("invio_documentazione_via_pec"), new RichiesteDocViaPecDetail(
				"invio_documentazione_via_pec"), finalita, flgSelezioneSingola, showOnlyDetail);

		multiselectButton.hide();
		newButton.hide();

	}

	public static boolean isAbilToIns() {
		return false;
	}

	public static boolean isAbilToMod() {
		return false;
	}

	public static boolean isAbilToDel() {
		return false;
	}

	@Override
	public String getViewDetailTitle() {
		Record record = new Record(detail.getValuesManager().getValues());
		return I18NUtil.getMessages().invio_documentazione_via_pec_view_detail(getTipoEstremiRecord(record));
	}

	public String getTipoEstremiRecord(Record record) {
		return record.getAttribute("codApplRich");
	}

	@Override
	public boolean getDefaultDetailAuto() {
		return false;
	}

	@Override
	public void viewMode() {
		super.viewMode();

		deleteButton.hide();
		editButton.hide();
		altreOpButton.hide();
	}
}
