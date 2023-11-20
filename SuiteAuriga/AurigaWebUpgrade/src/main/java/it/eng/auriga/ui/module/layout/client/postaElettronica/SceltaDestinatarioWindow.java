/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.LinkedHashMap;
import java.util.Map;

import com.smartgwt.client.data.Record;
import com.smartgwt.client.data.RecordList;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.Overflow;
import com.smartgwt.client.widgets.Button;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.ValuesManager;
import com.smartgwt.client.widgets.form.fields.RadioGroupItem;
import com.smartgwt.client.widgets.form.fields.events.ChangedEvent;
import com.smartgwt.client.widgets.form.fields.events.ChangedHandler;
import com.smartgwt.client.widgets.layout.HStack;
import com.smartgwt.client.widgets.layout.VLayout;

import it.eng.auriga.ui.module.layout.client.protocollazione.MittenteProtEntrataItem;
import it.eng.utility.ui.module.core.shared.message.MessageBean;
import it.eng.utility.ui.module.core.shared.message.MessageType;
import it.eng.utility.ui.module.layout.client.Layout;
import it.eng.utility.ui.module.layout.client.common.DetailSection;
import it.eng.utility.ui.module.layout.client.portal.ModalWindow;

/**
 * 
 * @author DANCRIST
 *
 */
public abstract class SceltaDestinatarioWindow extends ModalWindow {
	
	private VLayout lVLayoutMain;
	private Button saveButton;
	private Button annullaButton;
	
	private ValuesManager vm;
	private SceltaDestinatarioWindow _window;
	private DynamicForm form;
	private RadioGroupItem tipoIndirizziItem;
	private RadioGroupItem tipoDestinatariItem;
	
	private DetailSection richiedenteEsternoSection;
	private DynamicForm richiedenteEsternoForm;
	private MittenteProtEntrataItem richiedentiEsterniItem; // Richiedente esterno

	public SceltaDestinatarioWindow(String title,Record recordInput) {
		super("scelta_destinatario_window");
		
		setTitle(title);
				
		vm = new ValuesManager();
		this._window = this;
		
		setAutoCenter(true);
		setWidth(850);
		setHeight(400);

		settingsMenu.removeItem(separatorMenuItem);
		settingsMenu.removeItem(autoSearchMenuItem);
		setIcon("postaElettronica/filtro_destinatari_on.png");	
		
		lVLayoutMain = new VLayout();
		
		createFormsRchiedenteEsterno();
		richiedenteEsternoSection = new DetailSection("Indirizzi relativi a", true, true, false, richiedenteEsternoForm);
		if(recordInput != null && recordInput.getAttribute("tipoDestinatario") != null &&
				"E".equals(recordInput.getAttribute("tipoDestinatario"))){
			richiedenteEsternoSection.show();
		} else {
			richiedenteEsternoSection.hide();
		}
		createForms();
		
		createButtons();
		
		HStack _buttons = new HStack(5);
		_buttons.setHeight(30);
		_buttons.setAlign(Alignment.CENTER);
		_buttons.setPadding(5);
		_buttons.addMember(saveButton);
		_buttons.addMember(annullaButton);
		
		setAlign(Alignment.CENTER);
		setTop(50);
		
		lVLayoutMain.setHeight100();
		lVLayoutMain.setWidth100();
		lVLayoutMain.setOverflow(Overflow.AUTO);
		lVLayoutMain.addMember(form);
		lVLayoutMain.addMember(richiedenteEsternoSection);

		// Creo il VLAYOUT e gli aggiungo il TABSET
		VLayout portletLayout = new VLayout();
		portletLayout.setHeight100();
		portletLayout.setWidth100();
		portletLayout.setOverflow(Overflow.VISIBLE);
		
		portletLayout.addMember(lVLayoutMain);
		portletLayout.addMember(_buttons);
		setBody(portletLayout);
		
		show();
		if(recordInput != null) {
			vm.editRecord(recordInput);
		}
	}
	
	private void createButtons() {
		
		saveButton = new Button("Ok");
		saveButton.setIcon("ok.png");
		saveButton.setIconSize(16);
		saveButton.setAutoFit(false);
		saveButton.addClickHandler(new com.smartgwt.client.widgets.events.ClickHandler() {

			@Override
			public void onClick(com.smartgwt.client.widgets.events.ClickEvent event) {
				
				clickSave();	
			}
		});
		
		annullaButton = new Button("Annulla");
		annullaButton.setIcon("annulla.png");
		annullaButton.setIconSize(16);
		annullaButton.setAutoFit(false);
		annullaButton.addClickHandler(new com.smartgwt.client.widgets.events.ClickHandler() {

			@Override
			public void onClick(com.smartgwt.client.widgets.events.ClickEvent event) {
				
				_window.markForDestroy();
			}
		});
	}
	
	private void createForms() {
		
		form = new DynamicForm();
		form.setValuesManager(vm);
		form.setWrapItemTitles(false);
		form.setNumCols(5);
		form.setColWidths("1", "1", "1", "1", "*");
		
		tipoIndirizziItem = new RadioGroupItem("tipoIndirizzo");
		tipoIndirizziItem.setTitle("Tipo indirizzi mail");
		tipoIndirizziItem.setVertical(false);
		tipoIndirizziItem.setStartRow(true);
		tipoIndirizziItem.setVertical(false);
		tipoIndirizziItem.setWrap(false);
		Map<String, String> valueMapTP = new LinkedHashMap<>();
		valueMapTP.put("PEC", "solo posta certificata (PEC)");
		valueMapTP.put("PEO", "solo posta ordinaria (PEO)");
		valueMapTP.put("ALL", "tutti");
		tipoIndirizziItem.setValueMap(valueMapTP);
		tipoIndirizziItem.setDefaultValue("ALL");
		tipoIndirizziItem.addChangedHandler(new ChangedHandler() {

			@Override
			public void onChanged(ChangedEvent event) {
				markForRedraw();
			}
		});
		
		tipoDestinatariItem = new RadioGroupItem("tipoDestinatario");
		tipoDestinatariItem.setTitle("Tipo destinatari");
		tipoDestinatariItem.setVertical(false);
		tipoDestinatariItem.setStartRow(true);
		Map<String, String> valueMapTD = new LinkedHashMap<>();
		valueMapTD.put("E", "solo esterni");
		valueMapTD.put("I", "solo interni");
		valueMapTD.put("ALL", "tutti");
		tipoDestinatariItem.setValueMap(valueMapTD);
		tipoDestinatariItem.setDefaultValue("ALL");
		tipoDestinatariItem.addChangedHandler(new ChangedHandler() {

			@Override
			public void onChanged(ChangedEvent event) {
				if(destinararioEsterno()) {
					richiedenteEsternoSection.show();
				} else {
					richiedenteEsternoSection.hide();
				}
				markForRedraw();
			}
		});
		
		form.setFields(tipoIndirizziItem,tipoDestinatariItem);
	}
	
	private void createFormsRchiedenteEsterno() {
		
		richiedenteEsternoForm = new DynamicForm();
		richiedenteEsternoForm.setValuesManager(vm);
		
		richiedentiEsterniItem = new MittenteProtEntrataItem() {

			@Override
			public boolean getShowItemsIndirizzo() {
				return false;
			}
		};
		richiedentiEsterniItem.setName("listaMittenti");
		richiedentiEsterniItem.setShowTitle(false);
		richiedentiEsterniItem.setShowNewButton(false);
		richiedentiEsterniItem.setNotReplicable(true);
		
		richiedenteEsternoForm.setFields(richiedentiEsterniItem);
	}
	
	private void clickSave() {
	
		if(vm.validate()) {
			Record record = new Record(vm.getValues());
			if(destinararioEsterno()){				
				record.setAttribute("listaMittenti", richiedenteEsternoForm.getValueAsRecordList("listaMittenti"));
			} else {
				record.setAttribute("listaMittenti", new RecordList());
			}
			if( destinararioEsterno() &&
				(record != null && record.getAttributeAsRecordList("listaMittenti") != null &&
					!record.getAttributeAsRecordList("listaMittenti").isEmpty()) ) {
				
				Record mittenteEsterno = record.getAttributeAsRecordList("listaMittenti").get(0);
				if(mittenteEsterno != null && mittenteEsterno.getAttribute("idSoggetto") != null &&
						!"".equalsIgnoreCase(mittenteEsterno.getAttribute("idSoggetto"))) {
					salvaFiltri(record);
					_window.markForDestroy();
				} else {
					Layout.addMessage(new MessageBean("Soggetto non trovato in rubrica; rivedi i campi compilati o seleziona da lista di scelta", "", MessageType.ERROR));
				}
			} else {
				salvaFiltri(record);
				_window.markForDestroy();
			}
		}
	}
	
	public abstract void salvaFiltri(Record record);
	
	private Boolean destinararioEsterno() {
		return tipoDestinatariItem != null && tipoDestinatariItem.getValueAsString() != null &&
				"E".equals(tipoDestinatariItem.getValueAsString());
	}

}