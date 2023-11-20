/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.utility.ui.module.core.client.datasource.GWTRestDataSource;
import it.eng.utility.ui.module.layout.client.common.ConfigurableFilter;
import it.eng.utility.ui.module.layout.client.common.CustomDetail;
import it.eng.utility.ui.module.layout.client.common.CustomLayout;

import com.smartgwt.client.data.Record;

/**
 * 
 * @author cristiano
 *
 */
public class TrovaCiviciLayout extends CustomLayout {

	public TrovaCiviciLayout() {
		this(null, null, null, null);
	}

	public TrovaCiviciLayout(GWTRestDataSource gWTRestDataSource, String finalita, Boolean flgSelezioneSingola) {
		this(gWTRestDataSource, finalita, flgSelezioneSingola, null);
	}

	public TrovaCiviciLayout(GWTRestDataSource gWTRestDataSource, String finalita, Boolean flgSelezioneSingola, Boolean showOnlyDetail) {
		super("trova_civici", gWTRestDataSource, new ConfigurableFilter("trova_civici"), new TrovaCiviciList("trova_civici"), new CustomDetail("trova_civici"),
				finalita, flgSelezioneSingola, showOnlyDetail);

		multiselectButton.hide();
		newButton.hide();
		editButton.hide();
		deleteButton.hide();
		altreOpButton.hide();
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
	public void doLookup(Record record) {
		
		super.doLookup(record);
	}

	@Override
	public Boolean getDetailAuto() {
		
		return false;
	}

}
