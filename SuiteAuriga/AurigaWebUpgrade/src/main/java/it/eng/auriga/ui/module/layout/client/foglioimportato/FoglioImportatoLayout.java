/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import com.smartgwt.client.data.Record;
import com.smartgwt.client.types.FieldType;

import it.eng.utility.ui.module.core.client.datasource.GWTRestDataSource;
import it.eng.utility.ui.module.layout.client.common.ConfigurableFilter;
import it.eng.utility.ui.module.layout.client.common.CustomLayout;

/**
 * 
 * @author DANCRIST
 *
 */

public class FoglioImportatoLayout extends CustomLayout {

	public FoglioImportatoLayout() {
		this(null, null, null);
	}

	public FoglioImportatoLayout(String finalita, Boolean flgSelezioneSingola) {
		this(finalita, flgSelezioneSingola, null);
	}
	
	public FoglioImportatoLayout(String finalita, Boolean flgSelezioneSingola, Boolean showOnlyDetail) {
		super("foglio_importato", 
				new GWTRestDataSource("FoglioImportatoDataSource", "idFoglio", FieldType.TEXT), 
				new ConfigurableFilter("foglio_importato"),
				new FoglioImportatoList("foglio_importato"), 
				new FoglioImportatoDetail("foglio_importato"), 
				finalita, flgSelezioneSingola, showOnlyDetail
		);
	
		multiselectButton.hide();
	
		if (!isAbilToIns()) {
			newButton.hide();
		}
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