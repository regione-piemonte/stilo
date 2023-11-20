/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import com.google.gwt.core.client.JavaScriptObject;
import com.smartgwt.client.data.RecordList;
import com.smartgwt.client.types.VerticalAlignment;
import com.smartgwt.client.widgets.ImgButton;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.form.fields.CanvasItem;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.VLayout;

import it.eng.auriga.ui.module.layout.client.i18n.I18NUtil;
import it.eng.utility.ui.module.core.client.UserInterfaceFactory;
import it.eng.utility.ui.module.layout.client.common.ReplicableCanvas;
import it.eng.utility.ui.module.layout.client.common.ReplicableItem;


public class InfoStrutturaTabellaItem extends ReplicableItem{
		
	@Override
	public ReplicableCanvas getCanvasToReply() {
		return new InfoStrutturaTabellaCanvas();
	}
	
	@Override
	protected ImgButton[] createAddButtons() {
		
		ImgButton[] addButtons = new ImgButton[1];
		
		addButtons[0] = new ImgButton();   
		addButtons[0].setSrc("[SKIN]actions/add.png");   
		addButtons[0].setShowDown(false);   
		addButtons[0].setShowRollOver(false);      
		addButtons[0].setSize(16); 
		addButtons[0].setPrompt(I18NUtil.getMessages().newButton_prompt());
		addButtons[0].addClickHandler(new ClickHandler() {	
			@Override
			public void onClick(ClickEvent event) {
				onClickNewButton();   	
			}   
		});		
		
		return addButtons;		
	}
	
	/**
	 * Aggiunge al VLayout un HLAyout con un bottone di remove ed una istanza del {@link ReplicableCanvas}
	 */
	@Override
	public ReplicableCanvas onClickNewButton() {
		
		// Recupero il layout
		final VLayout lVLayout = getVLayout();
		// Recupero il ReplicableItem
		ReplicableItem lReplicableItem = (ReplicableItem) lVLayout.getCanvasItem();
		// Recupero il ReplicableCanvas
		final ReplicableCanvas lReplicableCanvas = lReplicableItem.getCanvasToReply();
 		if (UserInterfaceFactory.isAttivaAccessibilita()){
			lReplicableItem.setCanFocus(false);
			lReplicableItem.setTabIndex(-1);	
			lReplicableCanvas.setCanFocus(false);
			lReplicableCanvas.setTabIndex(-1);	
 		}
		lReplicableCanvas.bringToFront();
		lReplicableCanvas.setItem(this);
		lReplicableCanvas.setEditing(editing);
		lReplicableCanvas.setCanEdit(true);
		lReplicableCanvas.setCanFocus(true);
				
		// incremento il contatore
		count++;
		// Lo memorizzo
		lReplicableCanvas.setCounter(count);
		// Creo un HLayout
		final HLayout lHLayout = new HLayout();
		// lHLayout.setAlign(VerticalAlignment.CENTER);
		// lHLayout.setLayoutAlign(VerticalAlignment.CENTER);
		lHLayout.setDefaultLayoutAlign(VerticalAlignment.CENTER);
		lHLayout.setHeight100();
		RemoveButton removeButton = createRemoveButton(lVLayout, lHLayout);
		// Aggiungo il removeButton
		lHLayout.addMember(removeButton);
		if (ordinabile) {
			ImgButton upButton = createUpButton(lVLayout, lHLayout);
			lHLayout.addMember(upButton);
			lReplicableCanvas.setUpButton(upButton);

			ImgButton downButton = createDownButton(lVLayout, lHLayout);
			lHLayout.addMember(downButton);
			lReplicableCanvas.setDownButton(downButton);
		}
		if (showDuplicaRigaButton) {
			ImgButton duplicaRigaButton = createDuplicaRigaButton(lVLayout, lHLayout);
			lHLayout.addMember(duplicaRigaButton);
			lReplicableCanvas.setDuplicaRigaButton(duplicaRigaButton);
		}
		// Aggiungo il replicableCanvas
		lReplicableCanvas.setVLayout(lVLayout);
		lReplicableCanvas.setHLayout(lHLayout);
		lReplicableCanvas.setRemoveButton(removeButton);
		// gestisto il change handler per il canvas
		setUpChangeHandler(lReplicableCanvas);
		lHLayout.addMember(lReplicableCanvas);
		// Aggiungo l'hlayout al layout
		if (showNewButton) {
			lVLayout.addMember(lHLayout, (lVLayout.getMembers().length - 1));
		} else {
			lVLayout.addMember(lHLayout);
		}
		lVLayout.markForRedraw();
		CanvasItem lCanvasItem = lVLayout.getCanvasItem();
		// Creo un recordList
		RecordList lRecordList;
		// Se non è memorizzato
		if (lCanvasItem.getValue() == null) {
			// Lo creo nuovo
			lRecordList = new RecordList();
		} else {
			// Altrimenti lo recupero
			lRecordList = new RecordList((JavaScriptObject) lCanvasItem.getValue());
		}
		// Aggiungo un record vuoto
		if (getCanvasDefaultRecord() != null) {
			lReplicableCanvas.editRecord(getCanvasDefaultRecord());
		}
		/*
		else {
			lReplicableCanvas.editRecord(new Record()); //TODO potrebbe togliere dei valori settati nel canvas con setValue invece che con setDefaultValue
		}
		*/
		lRecordList.add(lReplicableCanvas.getFormValuesAsRecord());
		// Memorizzo il valore nel canvas
		storeValue(lRecordList);
		return lReplicableCanvas;
	}
	
}