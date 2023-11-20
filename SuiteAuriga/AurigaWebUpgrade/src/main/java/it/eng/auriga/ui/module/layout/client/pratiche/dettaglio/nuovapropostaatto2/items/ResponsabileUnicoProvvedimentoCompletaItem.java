/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.ArrayList;
import java.util.List;

import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.FormItemIfFunction;
import com.smartgwt.client.widgets.form.fields.FormItem;
import com.smartgwt.client.widgets.form.fields.events.ChangedEvent;
import com.smartgwt.client.widgets.form.fields.events.ChangedHandler;

import it.eng.auriga.ui.module.layout.client.i18n.I18NUtil;
import it.eng.utility.ui.module.layout.client.common.ReplicableCanvas;
import it.eng.utility.ui.module.layout.client.common.items.CheckboxItem;

public class ResponsabileUnicoProvvedimentoCompletaItem extends RuoliScrivaniaAttiCompletaItem {

	public ResponsabileUnicoProvvedimentoCompletaItem() {
		super("responsabileUnicoProvvedimento", "responsabileUnicoProvvedimentoFromLoadDett", "codUoResponsabileUnicoProvvedimento", "desResponsabileUnicoProvvedimento", null, null, null, "codFiscaleResponsabileUnicoProvvedimento");
	}
	
	@Override
	public List<FormItem> getCustomItems(ReplicableCanvas canv) {

		List<FormItem> customItems = new ArrayList<FormItem>();

		final CheckboxItem flgRUPAncheAdottanteItem = new CheckboxItem("flgRUPAncheAdottante",
				I18NUtil.getMessages().nuovaPropostaAtto2_detail_flgAncheAdottante_title());
		flgRUPAncheAdottanteItem.setDefaultValue(false);
		flgRUPAncheAdottanteItem.setColSpan(1);
		flgRUPAncheAdottanteItem.setWidth("*");
		flgRUPAncheAdottanteItem.addChangedHandler(new ChangedHandler() {

			@Override
			public void onChanged(ChangedEvent event) {
				Boolean value = (Boolean) event.getValue();
				manageOnChangedFlgRUPAncheAdottante(value != null && value);
			}
		});
		flgRUPAncheAdottanteItem.setShowIfCondition(new FormItemIfFunction() {

			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return showFlgAncheAdottante() && getIdUdAttoDaAnn() == null;
			}
		});
		customItems.add(flgRUPAncheAdottanteItem);

		return customItems;
	}
	
	public void manageOnChangedFlgRUPAncheAdottante(boolean value) {

	}
	
	public boolean showFlgAncheAdottante() {
		return false;
	}

	@Override
	public void manageChangedScrivaniaSelezionata() {
		
	}
	
	@Override
	public Boolean getShowRemoveButton() {
		return false;
	};
	
	public String getAltriParamLoadCombo() {
		return null;
	}
	
	public String getUoProponenteCorrente() {
		return null;
	}	
	
}
