/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.utility.ui.module.core.client.datasource.GWTRestDataSource;
import it.eng.utility.ui.module.layout.client.common.CustomDetail;
import it.eng.utility.ui.module.layout.client.common.CustomLayout;

import java.util.Date;

import com.smartgwt.client.data.Record;
import com.smartgwt.client.util.DateUtil;

public class VerificaRegDuplicataLayout extends CustomLayout {

	public VerificaRegDuplicataLayout(GWTRestDataSource pGWTRestDataSource) {
		super("verifica_reg_duplicata", 
				pGWTRestDataSource,
				null,
				new VerificaRegDuplicataList("verifica_reg_duplicata"),
				new CustomDetail("verifica_reg_duplicata")
		);

		multiselectButton.hide();	
		newButton.hide();		
		refreshListButton.hide();
		topListToolStripSeparator.hide();
		
		this.setLookup(false);
	}	
	
	@Override
	public boolean getDefaultDetailAuto() {
		return false;
	}
	
	@Override
	public String getViewDetailTitle() {
		Record record = new Record(detail.getValuesManager().getValues());
		String estremi = "";
		if (record.getAttributeAsString("tipoProtocollo") != null && !"".equals(record.getAttributeAsString("tipoProtocollo"))) {
			if ("NI".equals(record.getAttributeAsString("tipoProtocollo"))) {
				estremi += "bozza ";
			} else if ("PP".equals(record.getAttributeAsString("tipoProtocollo"))) {
				estremi += "Prot. ";
			} else {
				estremi += record.getAttributeAsString("tipoProtocollo") + " ";
			}
		}
		if (record.getAttributeAsString("siglaProtocollo") != null && !"".equals(record.getAttributeAsString("siglaProtocollo"))) {
			estremi += record.getAttributeAsString("siglaProtocollo") + " ";
		}
		if(record.getAttributeAsString("nroProtocollo") != null && !"".equals(record.getAttributeAsString("nroProtocollo"))) {
			estremi += record.getAttributeAsString("nroProtocollo") + " ";
		}
		if (record.getAttributeAsString("subProtocollo") != null && !"".equals(record.getAttributeAsString("subProtocollo"))) {
			estremi += "sub " + record.getAttributeAsString("subProtocollo") + " ";
		}
		if(record.getAttributeAsDate("dataProtocollo") != null) {
			estremi += "del " + DateUtil.format((Date) record.getAttributeAsDate("dataProtocollo"));
		}		
		return "Dettaglio registrazione " + estremi;		
	}

	@Override
	public void newMode() {
		super.newMode();
		altreOpButton.hide();
	}

	@Override
	public void viewMode() {
		
		super.viewMode();		
		deleteButton.hide();
		editButton.hide();
		altreOpButton.hide();		
	}

	@Override
	public void editMode(boolean fromViewMode) {
		
		super.editMode(fromViewMode);
		altreOpButton.hide();		
	}

}
