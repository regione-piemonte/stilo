/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.ArrayList;
import java.util.List;

import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.FormItemIfFunction;
import com.smartgwt.client.widgets.form.fields.FormItem;
import com.smartgwt.client.widgets.form.fields.events.ChangedEvent;
import com.smartgwt.client.widgets.form.fields.events.ChangedHandler;

import it.eng.auriga.ui.module.layout.client.AurigaLayout;
import it.eng.auriga.ui.module.layout.client.i18n.I18NUtil;
import it.eng.utility.ui.module.layout.client.common.ReplicableCanvas;
import it.eng.utility.ui.module.layout.client.common.items.CheckboxItem;

public class DirigenteAdottanteCompletaItem extends RuoliScrivaniaAttiCompletaItem {

	public DirigenteAdottanteCompletaItem() {
		super("dirigenteAdottante", "dirigenteAdottanteFromLoadDett", "codUoDirigenteAdottante", "desDirigenteAdottante");
	}

	@Override
	public List<FormItem> getCustomItems(ReplicableCanvas canv) {

		List<FormItem> customItems = new ArrayList<FormItem>();

		final CheckboxItem flgAdottanteAncheRdPItem = new CheckboxItem("flgAdottanteAncheRdP",
				I18NUtil.getMessages().nuovaPropostaAtto2_detail_flgAncheRdP_title());
		flgAdottanteAncheRdPItem.setDefaultValue(false);
		flgAdottanteAncheRdPItem.setColSpan(1);
		flgAdottanteAncheRdPItem.setWidth("*");
		flgAdottanteAncheRdPItem.addChangedHandler(new ChangedHandler() {

			@Override
			public void onChanged(ChangedEvent event) {
				Boolean value = (Boolean) event.getValue();
				manageOnChangedFlgAdottanteAncheRdP(value != null && value);
			}
		});
		flgAdottanteAncheRdPItem.setShowIfCondition(new FormItemIfFunction() {

			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				if (AurigaLayout.getParametroDBAsBoolean("FORZA_RDP_UGUALE_ADOTTANTE")) {
					flgAdottanteAncheRdPItem.setValue(true);
					flgAdottanteAncheRdPItem.setCanEdit(false);
				}
				return showFlgAncheRdP() && getIdUdAttoDaAnn() == null;
			}
		});
		customItems.add(flgAdottanteAncheRdPItem);

		final CheckboxItem flgAdottanteAncheRUPItem = new CheckboxItem("flgAdottanteAncheRUP",
				I18NUtil.getMessages().nuovaPropostaAtto2_detail_flgAncheRUP_title());
		flgAdottanteAncheRUPItem.setDefaultValue(false);
		flgAdottanteAncheRUPItem.setColSpan(1);
		flgAdottanteAncheRUPItem.setWidth("*");
		flgAdottanteAncheRUPItem.addChangedHandler(new ChangedHandler() {

			@Override
			public void onChanged(ChangedEvent event) {
				Boolean value = (Boolean) event.getValue();
				manageOnChangedFlgAdottanteAncheRUP(value != null && value);
			}
		});
		flgAdottanteAncheRUPItem.setShowIfCondition(new FormItemIfFunction() {

			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				if (AurigaLayout.getParametroDBAsBoolean("FORZA_RUP_UGUALE_ADOTTANTE")) {
					flgAdottanteAncheRUPItem.setValue(true);
					flgAdottanteAncheRUPItem.setCanEdit(false);
				}
				return showFlgAncheRUP() && getIdUdAttoDaAnn() == null;
			}
		});
		customItems.add(flgAdottanteAncheRUPItem);

		return customItems;
	}

	public void manageOnChangedFlgAdottanteAncheRdP(boolean value) {

	}

	public void manageOnChangedFlgAdottanteAncheRUP(boolean value) {

	}

	public boolean showFlgAncheRdP() {
		return true;
	}

	public boolean showFlgAncheRUP() {
		return true;
	}

	public void clearFlgAdottanteAncheRUP() {
		for (ReplicableCanvas lReplicableCanvas : getAllCanvas()) {
			lReplicableCanvas.getForm()[0].setValue("flgAdottanteAncheRUP", false);
		}
	}
	
	public String getIdUdAttoDaAnn() {
		return null;
	}

	public String getUoProponenteCorrente() {
		return null;
	}

	public String getAltriParamLoadCombo() {
		return null;
	}

}
