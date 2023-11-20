/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import com.smartgwt.client.data.Record;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.Overflow;
import com.smartgwt.client.widgets.Button;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.ValuesManager;
import com.smartgwt.client.widgets.grid.ListGridRecord;
import com.smartgwt.client.widgets.layout.HStack;

import it.eng.utility.ui.module.layout.client.portal.ModalWindow;

public class FiltraDatiContenutiTabellaPopup extends ModalWindow {
	
	private FiltraDatiContenutiTabellaPopup window;	
	private DynamicForm form; 
	private ValuesManager vm;
//	private FiltraDatiContenutiTabellaList listaFiltraDatiContenutiTabellaItem;
	private FiltraDatiContenutiTabellaItem listaFiltraDatiContenutiTabellaItem;
	protected Button chiudiButton;
	protected Button applicaFiltriButton;
	protected Button rimuoviFiltriButton;
	
	public FiltraDatiContenutiTabellaPopup() {
		super("filtra_dati_contenuti_tabella");
		
		window = this;
		this.vm = new ValuesManager();
		
		setIsModal(true);
		setModalMaskOpacity(50);
		setAutoCenter(true);
		setKeepInParentRect(true);		
		setTitle("Filtri sulle colonne");
		setShowModalMask(true);
		setShowMaximizeButton(false);
		setShowMinimizeButton(false);
		setShowCloseButton(false);
		setOverflow(Overflow.VISIBLE);
		setAutoDraw(false);
		setShowTitle(true);
		setHeaderIcon("buttons/imbuto.png");
		
		form = new DynamicForm();
		form.setKeepInParentRect(true);
		form.setWidth100();
		form.setHeight100();
		form.setNumCols(2);
		form.setColWidths(100, "*");
		form.setCellPadding(5);
		form.setAlign(Alignment.CENTER);
		form.setOverflow(Overflow.VISIBLE);
		form.setTop(50);
		
		form.setValuesManager(vm);
				
//		listaFiltraDatiContenutiTabellaItem = new FiltraDatiContenutiTabellaList("listaFiltraDatiContenutiTabella");
		
		listaFiltraDatiContenutiTabellaItem = new FiltraDatiContenutiTabellaItem();
		listaFiltraDatiContenutiTabellaItem.setName("listaFiltraDatiContenutiTabella");
		listaFiltraDatiContenutiTabellaItem.setShowTitle(false);
		listaFiltraDatiContenutiTabellaItem.setNotReplicable(true);
		
		form.setFields(listaFiltraDatiContenutiTabellaItem);	
		
		// Bottoni		
		chiudiButton = new Button("Chiudi");   
		chiudiButton.setIconSize(16); 
		chiudiButton.setAutoFit(false);
		chiudiButton.addClickHandler(new com.smartgwt.client.widgets.events.ClickHandler() {			
			@Override
			public void onClick(com.smartgwt.client.widgets.events.ClickEvent event) {				
				actionChiudiButton();
			}
		});
		
		applicaFiltriButton = new Button("Applica filtri");   
		applicaFiltriButton.setIconSize(16); 
		applicaFiltriButton.setAutoFit(false);
		applicaFiltriButton.addClickHandler(new com.smartgwt.client.widgets.events.ClickHandler() {			
			@Override
			public void onClick(com.smartgwt.client.widgets.events.ClickEvent event) {		
				actionApplicaFiltriButton(form.getValuesAsRecord());
			}
		});
		
		rimuoviFiltriButton = new Button("Rimuovi filtri");   
		rimuoviFiltriButton.setIconSize(16); 
		rimuoviFiltriButton.setAutoFit(false);
		rimuoviFiltriButton.addClickHandler(new com.smartgwt.client.widgets.events.ClickHandler() {			
			@Override
			public void onClick(com.smartgwt.client.widgets.events.ClickEvent event) {				
				actionRimuoviFiltriButton();
			}
		});
		
		
		HStack _buttons = new HStack(5);
		_buttons.setWidth100();
		_buttons.setHeight(30);
		_buttons.setAlign(Alignment.CENTER);
		_buttons.setPadding(5);
		_buttons.addMember(applicaFiltriButton);	
		_buttons.addMember(rimuoviFiltriButton);	
		_buttons.addMember(chiudiButton);	
		_buttons.setAutoDraw(false);
		addItem(form);
		addItem(_buttons);
	}
	
	public void setValues(Record values) {
		form.editRecord(values);
	}
	
	public void actionChiudiButton() {
		window.markForDestroy();
	}
	
	public void actionRimuoviFiltriButton() {
		window.markForDestroy();
	}
	
	public void actionApplicaFiltriButton(Record values) {
		window.markForDestroy();
	}

}
