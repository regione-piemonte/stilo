/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.ArrayList;

import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.Overflow;
import com.smartgwt.client.widgets.Button;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.ValuesManager;
import com.smartgwt.client.widgets.form.fields.ButtonItem;
import com.smartgwt.client.widgets.form.fields.FormItem;
import com.smartgwt.client.widgets.form.fields.HeaderItem;
import com.smartgwt.client.widgets.layout.HStack;
import com.smartgwt.client.widgets.layout.VLayout;

import it.eng.utility.ui.module.core.client.callback.ServiceCallback;
import it.eng.utility.ui.module.layout.client.common.file.PrettyFileUploadItem;
import it.eng.utility.ui.module.layout.client.common.items.ImgButtonItem;
import it.eng.utility.ui.module.layout.client.common.items.SelectItem;
import it.eng.utility.ui.module.layout.client.common.items.TitleItem;
import it.eng.utility.ui.module.layout.client.portal.ModalWindow;

/**
 * 
 * @author matzanin
 *
 */

public class NuovoCampionamentoDaVerificaPopup extends ModalWindow {

	protected NuovoCampionamentoDaVerificaPopup _window;
	protected ServiceCallback<String> _callback = null;
	
	protected ValuesManager vm;
	
	protected DynamicForm percentualeCampionamentoForm;
	private SelectItem percentualeCampionamentoItem;
	
	protected ButtonItem confermaButton;
	protected ButtonItem annullaButton;
		
	public NuovoCampionamentoDaVerificaPopup(ServiceCallback<String> callback) {

		super("nuovo_campionamento", true);

		_window = this;
		_callback = callback;
		
		vm = new ValuesManager();		

		setTitle("Nuovo campionamento");		
		
		setAutoCenter(true);
		setHeight(120);
		setWidth(350);
		setAlign(Alignment.CENTER);
		
		settingsMenu.removeItem(separatorMenuItem);
		settingsMenu.removeItem(autoSearchMenuItem);
		
		buildPercentualeCampionamentoForm();
					
		Button confermaButton = new Button("Conferma");
		confermaButton.setIcon("ok.png");
		confermaButton.setIconSize(16);
		confermaButton.setAutoFit(false);
		confermaButton.addClickHandler(new com.smartgwt.client.widgets.events.ClickHandler() {

			@Override
			public void onClick(com.smartgwt.client.widgets.events.ClickEvent event) {
				manageOnConfirmClick();
			}
		});

		Button annullaButton = new Button("Annulla");
		annullaButton.setIcon("annulla.png");
		annullaButton.setIconSize(16);
		annullaButton.setAutoFit(false);
		annullaButton.addClickHandler(new com.smartgwt.client.widgets.events.ClickHandler() {

			@Override
			public void onClick(com.smartgwt.client.widgets.events.ClickEvent event) {
				manageOnCloseClick();
			}
		});

		HStack _buttons = new HStack(5);
		_buttons.setHeight(30);
		_buttons.setAlign(Alignment.CENTER);
		_buttons.setPadding(5);
		_buttons.addMember(confermaButton);
		_buttons.addMember(annullaButton);	
		
		VLayout layout = new VLayout();
		layout.setHeight100();
		layout.setWidth100();
		layout.setOverflow(Overflow.AUTO);
		
		// Creo il VLAYOUT e gli aggiungo il TABSET 
		VLayout portletLayout = new VLayout();  
		portletLayout.setHeight100();
		portletLayout.setWidth100();
		portletLayout.setOverflow(Overflow.VISIBLE);
		
		layout.addMember(percentualeCampionamentoForm);
			
		VLayout spacerLayout = new VLayout();
		spacerLayout.setHeight100();
		spacerLayout.setWidth100();
		layout.addMember(spacerLayout);

		portletLayout.addMember(layout);		
		portletLayout.addMember(_buttons);
						
		setBody(portletLayout);
	
		setIcon("blank.png");
		
		setCanEdit(true);
	}
	
	public void setCanEdit(boolean canEdit) {
		for (DynamicForm form : vm.getMembers()) {
			setCanEdit(canEdit, form);
		}
	}

	public void setCanEdit(boolean canEdit, DynamicForm form) {
		if (form != null) {
			form.setEditing(canEdit);
			for (FormItem item : form.getFields()) {
				if (!(item instanceof HeaderItem) && !(item instanceof ImgButtonItem) && !(item instanceof TitleItem)) {
					item.setCanEdit(canEdit);
					item.redraw();
				}
				if (item instanceof ImgButtonItem || item instanceof PrettyFileUploadItem) {
					item.setCanEdit(canEdit);
					item.redraw();
				}
			}
		}
	}

	private void buildPercentualeCampionamentoForm() {
		
		percentualeCampionamentoForm = new DynamicForm();
		percentualeCampionamentoForm.setValuesManager(vm);
		percentualeCampionamentoForm.setWidth100();
		percentualeCampionamentoForm.setPadding(5);
		percentualeCampionamentoForm.setWrapItemTitles(false);
		percentualeCampionamentoForm.setNumCols(20);
		percentualeCampionamentoForm.setColWidths(1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, "*", "*");
		
		percentualeCampionamentoItem = new SelectItem("percentualeCampionamento", "% atti estratti per controllo");
		percentualeCampionamentoItem.setStartRow(true);
		percentualeCampionamentoItem.setWidth(200);
		ArrayList<String> lListDa0A99 = new ArrayList<String>();
		for(int i = 0; i < 100; i++) {
			lListDa0A99.add("" + i);
		}
		percentualeCampionamentoItem.setValueMap(lListDa0A99.toArray(new String[lListDa0A99.size()]));
		percentualeCampionamentoItem.setRequired(true);
		
		percentualeCampionamentoForm.setFields(
			percentualeCampionamentoItem
		);
	}
	
	public Boolean validate() {
		vm.clearErrors(true);		
		Boolean valid = vm.validate();
		return valid;
	}	
	
	public void manageOnConfirmClick() {
		if (validate()) {
			if(_callback != null) {
				_callback.execute(percentualeCampionamentoItem.getValueAsString());
			}
			_window.markForDestroy();
		}
	}
	
	@Override
	public void manageOnCloseClick() {
		if(_callback != null) {
			_callback.execute(null);
		}
		_window.markForDestroy();
	};
	
	@Override
	protected void onDestroy() {
		if (vm != null) {
			try {
				vm.destroy();
			} catch (Exception e) {
			}
		}
		super.onDestroy();
	}

}
