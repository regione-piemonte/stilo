/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.HashMap;
import java.util.Map;

import com.smartgwt.client.data.Record;
import com.smartgwt.client.widgets.form.fields.RadioGroupItem;

import it.eng.auriga.ui.module.layout.client.i18n.I18NUtil;
import it.eng.utility.ui.module.layout.client.common.ReplicableCanvas;
import it.eng.utility.ui.module.layout.client.common.items.ExtendedTextItem;

public class EmailDestinatariRegoleRegAutoCaselleCanvas extends ReplicableCanvas {

	private ReplicableCanvasForm mDynamicForm;
	private ExtendedTextItem indirizzoDestinatarioItem;
	private RadioGroupItem flgTipoEmailDestinatarioItem;
	
	@Override
	public void disegna() {
		mDynamicForm = new ReplicableCanvasForm();
		mDynamicForm.setWrapItemTitles(false);
		
		indirizzoDestinatarioItem = new ExtendedTextItem("indirizzoDestinatario", I18NUtil.getMessages().regole_protocollazione_automatica_caselle_pec_peo_indirizzoDestinatarioItem_title());
		indirizzoDestinatarioItem.setWidth(200);
		indirizzoDestinatarioItem.setColSpan(1);
		
		flgTipoEmailDestinatarioItem = new RadioGroupItem("flgTipoEmailDestinatario", I18NUtil.getMessages().regole_protocollazione_automatica_caselle_pec_peo_flgTipoEmailItem_title());
		Map<String, String> flgTipoEmailDestinatarioItemValueMap = new HashMap<String, String>();
		flgTipoEmailDestinatarioItemValueMap.put("p", "principale");
		flgTipoEmailDestinatarioItemValueMap.put("cc", "in conoscenza");
		flgTipoEmailDestinatarioItem.setValueMap(flgTipoEmailDestinatarioItemValueMap);
		flgTipoEmailDestinatarioItem.setVertical(false);
		flgTipoEmailDestinatarioItem.setWrap(false);
		flgTipoEmailDestinatarioItem.setShowDisabled(false);
		
		mDynamicForm.setFields(
				indirizzoDestinatarioItem,
				flgTipoEmailDestinatarioItem
				);
		
		mDynamicForm.setNumCols(1);
		mDynamicForm.setColWidths("*");

		addChild(mDynamicForm);
	}

	@Override
	public ReplicableCanvasForm[] getForm() {
		return new ReplicableCanvasForm[] { mDynamicForm };
	}
	
	public Record getFormValuesAsRecord() {
		return mDynamicForm.getValuesAsRecord();
	}

}
