/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import com.smartgwt.client.data.Record;
import com.smartgwt.client.types.FieldType;

import it.eng.auriga.ui.module.layout.client.i18n.I18NUtil;
import it.eng.utility.ui.module.core.client.datasource.GWTRestDataSource;
import it.eng.utility.ui.module.core.shared.message.MessageBean;
import it.eng.utility.ui.module.core.shared.message.MessageType;
import it.eng.utility.ui.module.layout.client.Layout;
import it.eng.utility.ui.module.layout.client.common.ConfigurableFilter;
import it.eng.utility.ui.module.layout.client.common.CustomLayout;

public class AttributiCustomLayout extends CustomLayout {

	public AttributiCustomLayout() {
		this(null, null, null);
	}

	public AttributiCustomLayout(String finalita, Boolean flgSelezioneSingola) {
		this(finalita, flgSelezioneSingola, null);
	}

	public AttributiCustomLayout(String finalita, Boolean flgSelezioneSingola, Boolean showOnlyDetail) {
		super("attributi_custom", new GWTRestDataSource("AttributiCustomDataSource", "nome", FieldType.TEXT), new ConfigurableFilter("attributi_custom"),
				new AttributiCustomList("attributi_custom"), new AttributiCustomDetail("attributi_custom"), finalita, flgSelezioneSingola, showOnlyDetail);

		multiselectButton.hide();

		if (!isAbilToIns()) {
			newButton.hide();
		}
	}

	public static boolean isAbilToIns() {
		return Layout.isPrivilegioAttivo("DC/ATT;I");
	}

	public static boolean isAbilToMod() {
		return Layout.isPrivilegioAttivo("DC/ATT;M");
	}

	public static boolean isAbilToDel() {
		return Layout.isPrivilegioAttivo("DC/ATT;FC");
	}

	@Override
	public String getNewDetailTitle() {
		return I18NUtil.getMessages().attributi_custom_new_title();
	}

	@Override
	public String getEditDetailTitle() {
		Record record = new Record(detail.getValuesManager().getValues());
		return I18NUtil.getMessages().attributi_custom_edit_title(getTipoEstremiRecord(record));
	}

	@Override
	public String getViewDetailTitle() {
		Record record = new Record(detail.getValuesManager().getValues());
		return I18NUtil.getMessages().attributi_custom_view_title(getTipoEstremiRecord(record));
	}

	public String getTipoEstremiRecord(Record record) {
		return record.getAttribute("nome");
	}

	@Override
	public void newMode() {
		super.newMode();
		altreOpButton.hide();
	}

	@Override
	public void viewMode() {
		super.viewMode();
		if (isAbilToDel()) {
			deleteButton.show();
		} else {
			deleteButton.hide();
		}
		if (isAbilToMod()) {
			editButton.show();
		} else {
			editButton.hide();
		}
		altreOpButton.hide();
	}

	@Override
	public void editMode(boolean fromViewMode) {
		super.editMode(fromViewMode);
		altreOpButton.hide();
	}

	@Override
	protected Record createMultiLookupRecord(Record record) {
		final Record newRecord = new Record();

		newRecord.setAttribute("id", record.getAttributeAsString("nome"));
		newRecord.setAttribute("nome", record.getAttributeAsString("nome"));
		newRecord.setAttribute("icona", "menu/attributi_custom.png");

		return newRecord;
	}

	@Override
	public void doLookup(Record record) {
		if (isRecordSelezionabileForLookup(record)) {
			super.doLookup(record);
		} else {
			Layout.addMessage(new MessageBean(I18NUtil.getMessages().recordNonSelezionabileXFinalita_message(), "", MessageType.WARNING));
		}
	}
	
	protected boolean isRecordSelezionabileForLookup(Record record) {
		return record.getAttributeAsString("appartenenza") == null || "".equalsIgnoreCase(record.getAttributeAsString("appartenenza"));
	}
	
}
