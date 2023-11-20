/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import com.smartgwt.client.data.Record;
import com.smartgwt.client.data.RecordList;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.Overflow;
import com.smartgwt.client.widgets.Button;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.ValuesManager;
import com.smartgwt.client.widgets.layout.HStack;
import com.smartgwt.client.widgets.layout.VLayout;

import it.eng.utility.ui.module.layout.client.common.CustomDetail;
import it.eng.utility.ui.module.layout.client.portal.ModalWindow;

public class AssociaAttributoCustomPopup extends ModalWindow {

	protected AssociaAttributoCustomPopup _window;
	
	private ValuesManager vm;
	
	protected Record record;
	
	protected String nomeTabella;
	protected String idEntitaAssociata;
	protected String nomeEntitaAssociata;
	
	private DynamicForm mDynamicForm;
	protected AssociazioniAttributiCustomItem associazioniAttributiCustomItem;
	
	public AssociaAttributoCustomPopup(String title, final String nomeTabella, final String idEntitaAssociata, Record record) {

		super("associa_attributo_custom", true);

		_window = this;
		
		this.record = record;		
		
		this.nomeTabella = nomeTabella;		
		this.idEntitaAssociata = idEntitaAssociata;		

		vm = new ValuesManager();

		setTitle(title);

		setAutoCenter(true);
		setWidth(950);
		setHeight(170);

		settingsMenu.removeItem(separatorMenuItem);
		settingsMenu.removeItem(autoSearchMenuItem);

		mDynamicForm = new DynamicForm();
		mDynamicForm.setValuesManager(vm);
		mDynamicForm.setWidth100();
		mDynamicForm.setPadding(5);
		mDynamicForm.setWrapItemTitles(false);
		mDynamicForm.setNumCols(10);
		mDynamicForm.setColWidths(120, 1, 1, 1, 1, 1, 1, 1, "*", "*");
		
		associazioniAttributiCustomItem = new AssociazioniAttributiCustomItem() {
			
			@Override
			public String getNomeTabella() {
				return nomeTabella;
			};	
			
			@Override
			public String getIdEntitaAssociata() {
				return idEntitaAssociata;
			}

			@Override
			public boolean isAssociazioneSottoAttributo() {
				// TODO Auto-generated method stub
				return false;
			};			
		};
		associazioniAttributiCustomItem.setName("listaAssociazioniAttributiCustom");
		associazioniAttributiCustomItem.setShowTitle(false);
		associazioniAttributiCustomItem.setNotReplicable(true);
		associazioniAttributiCustomItem.setAttribute("obbligatorio", true);
		
		mDynamicForm.setFields(associazioniAttributiCustomItem);

		Button okButton = new Button("Ok");
		okButton.setIcon("ok.png");
		okButton.setIconSize(16);
		okButton.setAutoFit(false);
		okButton.addClickHandler(new com.smartgwt.client.widgets.events.ClickHandler() {

			@Override
			public void onClick(com.smartgwt.client.widgets.events.ClickEvent event) {
				onOkButtonClick(mDynamicForm.getValuesAsRecord().getAttributeAsRecordList("listaAssociazioniAttributiCustom"));	
				_window.markForDestroy();				
			}
		});

		HStack _buttons = new HStack(5);
		_buttons.setHeight(30);
		_buttons.setAlign(Alignment.CENTER);
		_buttons.setPadding(5);
		_buttons.addMember(okButton);
		
		setAlign(Alignment.CENTER);
		setTop(50);

		VLayout layout = new VLayout();
		layout.setHeight100();
		layout.setWidth100();
		layout.setOverflow(Overflow.AUTO);
		
		// Creo il VLAYOUT e gli aggiungo il TABSET
		VLayout portletLayout = new VLayout();
		portletLayout.setHeight100();
		portletLayout.setWidth100();
		portletLayout.setOverflow(Overflow.VISIBLE);
		
		VLayout spacerLayout = new VLayout();
		spacerLayout.setHeight100();
		spacerLayout.setWidth100();

		layout.addMember(mDynamicForm);
		layout.addMember(spacerLayout);

		portletLayout.addMember(layout);
		portletLayout.addMember(_buttons);

		setBody(portletLayout);

		mDynamicForm.editRecord(record);				
		_window.show();
	}
	
	public void onOkButtonClick(RecordList listaAssociazioniAttributiCustom) {
			
	}

	@Override
	public void manageOnCloseClick() {
		markForDestroy();
	}
	
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