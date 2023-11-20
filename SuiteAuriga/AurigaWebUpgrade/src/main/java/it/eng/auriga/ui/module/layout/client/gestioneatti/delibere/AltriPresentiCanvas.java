/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.utility.ui.module.core.client.datasource.GWTRestDataSource;
import it.eng.utility.ui.module.layout.client.common.ReplicableCanvas;
import it.eng.utility.ui.module.layout.client.common.items.SelectItem;

/**
 * 
 * @author DANCRIST
 *
 */

public class AltriPresentiCanvas extends ReplicableCanvas {
	
	private ReplicableCanvasForm mDynamicForm;
	protected SelectItem denominazioneItem;
	
	public AltriPresentiCanvas(AltriPresentiItem item) {
		super(item);
	}

	@Override
	public void disegna() {
		
		mDynamicForm = new ReplicableCanvasForm();
		mDynamicForm.setNumCols(9);
		mDynamicForm.setColWidths(80, 1, 1, 1, 1, 1, 1, "*", "*");
		
		GWTRestDataSource altriPresentiSedutaDS = new GWTRestDataSource("LoadComboAltriPresentiSedutaDataSource");
		altriPresentiSedutaDS.addParam("tipo_sessione", ((AltriPresentiItem)getItem()).getTipologiaSessione()); 
		
		denominazioneItem = new SelectItem("idUser","Ruolo e/o nominativo");
		denominazioneItem.setValueField("key");
		denominazioneItem.setDisplayField("value");
		denominazioneItem.setOptionDataSource(altriPresentiSedutaDS);
		denominazioneItem.setAutoFetchData(false);
		denominazioneItem.setAlwaysFetchMissingValues(true);
		denominazioneItem.setFetchMissingValues(true);
		denominazioneItem.setStartRow(false);
		denominazioneItem.setColSpan(7);
		denominazioneItem.setWidth(250);
		
		mDynamicForm.setFields(denominazioneItem);

		addChild(mDynamicForm);
		setHeight(26);
	}

	@Override
	public ReplicableCanvasForm[] getForm() {
		return new ReplicableCanvasForm[] { mDynamicForm };
	}

}