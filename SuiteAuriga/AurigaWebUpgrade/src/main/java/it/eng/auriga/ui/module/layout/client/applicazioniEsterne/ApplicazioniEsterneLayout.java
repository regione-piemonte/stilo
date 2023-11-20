/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.auriga.ui.module.layout.client.i18n.I18NUtil;
import it.eng.utility.ui.module.core.client.datasource.GWTRestDataSource;
import it.eng.utility.ui.module.layout.client.Layout;
import it.eng.utility.ui.module.layout.client.common.ConfigurableFilter;
import it.eng.utility.ui.module.layout.client.common.CustomLayout;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.types.FieldType;

/**
 * 
 * @author ottavio passalcqua
 *
 */

public class ApplicazioniEsterneLayout extends CustomLayout {

	
	public ApplicazioniEsterneLayout() {
		this(null, null, null);
	}

	public ApplicazioniEsterneLayout(String finalita, Boolean flgSelezioneSingola) {
		this(finalita, flgSelezioneSingola, null);
	}

	public ApplicazioniEsterneLayout(String finalita, Boolean flgSelezioneSingola, Boolean showOnlyDetail) {
		super("applicazioni_esterne", 
			  new GWTRestDataSource("ApplicazioniEsterneDataSource", "idApplEsterna", FieldType.TEXT), 
			  new ConfigurableFilter("applicazioni_esterne"), 
			  new ApplicazioniEsterneList("applicazioni_esterne"), 
			  new ApplicazioniEsterneDetail("applicazioni_esterne"),
			  finalita, 
			  flgSelezioneSingola, 
			  showOnlyDetail);

		multiselectButton.hide();

		if (!isAbilToIns()) {
			newButton.hide();
		}
	}

	public static boolean isAbilToIns() {
		return Layout.isPrivilegioAttivo("SIC/AE;I");
	}

	public static boolean isAbilToMod() {
		return Layout.isPrivilegioAttivo("SIC/AE;M");
	}

	public static boolean isAbilToDel() {
		return Layout.isPrivilegioAttivo("SIC/AE;FC");
	}

	public static boolean isRecordAbilToMod(boolean flgDiSistema) {
		return !flgDiSistema && isAbilToMod();
	}

	public static boolean isRecordAbilToDel(boolean flgValido, boolean flgDiSistema) {
		return flgValido && !flgDiSistema && isAbilToDel();
	}

	@Override
	public String getNewDetailTitle() {
		return I18NUtil.getMessages().applicazioni_esterne_new_title();
	}

	@Override
	public String getEditDetailTitle() {
		Record record = new Record(detail.getValuesManager().getValues());
		return I18NUtil.getMessages().applicazioni_esterne_edit_title(getTipoEstremiRecord(record));
	}

	@Override
	public String getViewDetailTitle() {
		Record record = new Record(detail.getValuesManager().getValues());
		return I18NUtil.getMessages().applicazioni_esterne_view_title(getTipoEstremiRecord(record));
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
	public boolean getDefaultMultiselect() {
		return false;
	}
}