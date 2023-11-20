/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.ArrayList;
import java.util.List;

import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.FormItemIfFunction;
import com.smartgwt.client.widgets.form.fields.FormItem;
import com.smartgwt.client.widgets.form.fields.events.ChangedEvent;
import com.smartgwt.client.widgets.form.fields.events.ChangedHandler;

import it.eng.auriga.ui.module.layout.client.i18n.I18NUtil;
import it.eng.auriga.ui.module.layout.client.protocollazione.SelezionaScrivaniaItem;
import it.eng.utility.ui.module.layout.client.common.ReplicableCanvas;
import it.eng.utility.ui.module.layout.client.common.items.CheckboxItem;

public class ResponsabileDiProcedimentoItem extends SelezionaScrivaniaItem {
	
	@Override
	public int getSelectItemOrganigrammaWidth() {
		return 650;
	}

	@Override
	public List<FormItem> getCustomItems() {
		
		List<FormItem> customItems = new ArrayList<FormItem>();
		
		CheckboxItem flgRdPAncheRUPItem = new CheckboxItem("flgRdPAncheRUP", I18NUtil.getMessages().nuovaPropostaAtto2_detail_flgAncheRUP_title());
		flgRdPAncheRUPItem.setDefaultValue(false);
		flgRdPAncheRUPItem.setColSpan(1);
		flgRdPAncheRUPItem.setWidth("*");
		flgRdPAncheRUPItem.addChangedHandler(new ChangedHandler() {
			
			@Override
			public void onChanged(ChangedEvent event) {
				Boolean value = (Boolean) event.getValue();
				manageOnChangedFlgRdPAncheRUP(value != null && value);
			}
		});
		flgRdPAncheRUPItem.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return showFlgAncheRUP();
			}
		});
		
		customItems.add(flgRdPAncheRUPItem);
		
		return customItems;
	}

	public boolean showFlgAncheRUP() {
		return true;
	}
	
	public void clearFlgRdPAncheRUP() {
		for(ReplicableCanvas lReplicableCanvas : getAllCanvas()) {
			lReplicableCanvas.getForm()[0].setValue("flgRdPAncheRUP", false);
		}		
	}
	
	public void manageOnChangedFlgRdPAncheRUP(boolean value) {
		
	}
	
	@Override
	public void manageChangedScrivaniaSelezionata() {
		
	}
	
	@Override
	public Boolean getShowRemoveButton() {
		return false;
	};

}
