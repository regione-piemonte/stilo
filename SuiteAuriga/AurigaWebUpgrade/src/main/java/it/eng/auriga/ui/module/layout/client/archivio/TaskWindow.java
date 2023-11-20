/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.auriga.ui.module.layout.client.task.TaskDetail;
import it.eng.utility.ui.module.core.client.i18n.I18NUtil;
import it.eng.utility.ui.module.layout.client.common.DetailToolStripButton;
import it.eng.utility.ui.module.layout.client.portal.ModalWindow;

import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.client.widgets.toolbar.ToolStrip;


public class TaskWindow extends ModalWindow {

	protected TaskWindow instance;
	private TaskDetail detail;
	
	protected String nomeAttivita;

	protected ToolStrip detailToolStrip;
	protected DetailToolStripButton saveButton;
	
	public TaskWindow(String nomeEntita, String nomeAttivita, TaskDetail taskDetail) {
		
		super(nomeEntita, true);

		instance = this;
		
		this.nomeAttivita = nomeAttivita;
		
		setTitle("Task " + nomeAttivita);
		
		settingsMenu.removeItem(separatorMenuItem);
		settingsMenu.removeItem(autoSearchMenuItem);

		detail = taskDetail;	
		detail.setWindow(this);		
		
		saveButton = new DetailToolStripButton(I18NUtil.getMessages().saveButton_prompt(), "buttons/save.png");
		saveButton.addClickHandler(new ClickHandler() { 
			@Override
			public void onClick(ClickEvent event) { 
				onSaveButtonClick();				
			}   
		}); 	
		
		detailToolStrip = new ToolStrip();   
		detailToolStrip.setWidth100();       
		detailToolStrip.setHeight(30);
		detailToolStrip.setStyleName(it.eng.utility.Styles.detailToolStrip);
		detailToolStrip.addFill(); //push all buttons to the right 
		detailToolStrip.addButton(saveButton);			
		
		VLayout detailLayout = new VLayout();  		
		detailLayout.setMembers(detail, detailToolStrip);		
		
		detailLayout.setHeight100();
		detailLayout.setWidth100();		
		
		setBody(detailLayout);
				
        setIcon("blank.png");   
	}
	
	public String getNomeAttivita() {
		return nomeAttivita;
	}
	
	public void newMode() {
		detail.newMode();
		saveButton.show();
	}
	
	public void viewMode() {
		detail.viewMode();
		saveButton.hide();
	}
	
	public void onSaveButtonClick() {
		detail.salvaTask();		
	}
	
	@Override
	public void manageOnCloseClick() {
		
		if(detail.isSaved()) {
			manageOnCloseClickAfterSaved();
		}
		markForDestroy();			
	}
	
	public void manageOnCloseClickAfterSaved() {
		
	}

}
