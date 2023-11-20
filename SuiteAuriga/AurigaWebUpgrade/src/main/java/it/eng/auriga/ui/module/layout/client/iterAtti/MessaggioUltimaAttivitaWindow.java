/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.utility.ui.module.layout.client.common.items.TextAreaItem;

import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.Overflow;
import com.smartgwt.client.widgets.Window;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.ValuesManager;

public class MessaggioUltimaAttivitaWindow extends Window {

	private MessaggioUltimaAttivitaWindow instance;
	private DynamicForm form; 
	
	private TextAreaItem messaggioItem;
	
	public MessaggioUltimaAttivitaWindow(ValuesManager vm){
		
		instance = this;
		
		setIsModal(true);
		setModalMaskOpacity(50);
		setAutoCenter(true);
		setHeight(100);
		setWidth(450);		
		setKeepInParentRect(true);		
		setTitle("Messaggio ultima attività svolta");
		setShowModalMask(true);
		setShowCloseButton(true);
		setShowMaximizeButton(false);
		setShowMinimizeButton(false);
		setCanDragReposition(true);  
        setCanDragResize(true);   
        setRedrawOnResize(true);
        setKeepInParentRect(true);
        setAutoDraw(true);
        setShowMinimizeButton(false);
		
		form = new DynamicForm();
		form.setValuesManager(vm);
		form.setKeepInParentRect(true);
		form.setWidth100();
		form.setHeight100();
		form.setNumCols(1);
		form.setColWidths("*");
		form.setCellPadding(5);
		form.setAlign(Alignment.CENTER);
		form.setOverflow(Overflow.VISIBLE);
		form.setTop(50);
		
		messaggioItem = new TextAreaItem("messaggioUltimaAttivita");
		messaggioItem.setShowHintInField(true);
		messaggioItem.setShowTitle(false);
		messaggioItem.setColSpan(1);
		messaggioItem.setHeight("100%");
		messaggioItem.setWidth("100%");
		messaggioItem.setAlign(Alignment.CENTER);
		messaggioItem.setCanEdit(false);
		
		form.setFields(messaggioItem);
		
		addItem(form);	
		
		setShowTitle(true);
		setHeaderIcon("blank.png");
		
//		draw();
		
	}
	
}
