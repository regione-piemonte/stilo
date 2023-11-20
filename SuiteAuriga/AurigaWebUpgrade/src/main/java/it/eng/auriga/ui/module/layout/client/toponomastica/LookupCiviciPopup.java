/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import com.smartgwt.client.data.Record;
import com.smartgwt.client.types.FieldType;

import it.eng.utility.ui.module.core.client.datasource.GWTRestDataSource;
import it.eng.utility.ui.module.layout.client.portal.ModalWindow;

public abstract class LookupCiviciPopup extends ModalWindow {

	private LookupCiviciPopup _window;

	private TrovaCiviciLayout portletLayout;

	public LookupCiviciPopup(final String codToponimoFilter, String finalita, boolean flgSelezioneSingola) {
		super("trova_civici", true);

		setTitle("Civici");

		_window = this;

		GWTRestDataSource gWTRestDataSource = new GWTRestDataSource("TrovaCiviciDataSource", "idCivico", FieldType.TEXT);
		gWTRestDataSource.addParam("codToponimo", codToponimoFilter);

		portletLayout = new TrovaCiviciLayout(gWTRestDataSource, finalita, flgSelezioneSingola) {

			@Override
			public void lookupBack(Record selectedRecord) {
				
				manageLookupBack(selectedRecord);
				_window.markForDestroy();
			}

			@Override
			public void multiLookupBack(Record record) {
				
				manageMultiLookupBack(record);
			}

			@Override
			public void multiLookupUndo(Record record) {
				
				manageMultiLookupUndo(record);
			}

		};

		portletLayout.setLookup(true);

		portletLayout.setHeight100();
		portletLayout.setWidth100();

		setBody(portletLayout);

		setIcon("lookup/civicosearch.png");
	}

	public abstract void manageLookupBack(Record record);

	public abstract void manageMultiLookupBack(Record record);

	public abstract void manageMultiLookupUndo(Record record);

}
