/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.auriga.ui.module.layout.client.task.TaskDetail;
import it.eng.auriga.ui.module.layout.client.task.TaskUtil;
import it.eng.auriga.ui.module.layout.client.task.TaskUtilCallback;
import it.eng.utility.ui.module.layout.client.common.ReplicableCanvas;
import it.eng.utility.ui.module.layout.client.common.items.ImgItem;
import it.eng.utility.ui.module.layout.client.common.items.StaticTextItem;

import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.FormItemIfFunction;
import com.smartgwt.client.widgets.form.fields.FormItem;
import com.smartgwt.client.widgets.form.fields.HiddenItem;
import com.smartgwt.client.widgets.form.fields.events.ClickEvent;
import com.smartgwt.client.widgets.form.fields.events.ClickHandler;

public class TaskCanvas extends ReplicableCanvas{	

	private HiddenItem instanceIdHiddenItem;
	private HiddenItem instanceLabelHiddenItem;			
	private HiddenItem activityNameHiddenItem;									
	private HiddenItem displayNameHiddenItem;
	private HiddenItem flgEseguibileHiddenItem;
	private HiddenItem motiviNonEsegHiddenItem;
	private HiddenItem flgDatiVisibiliHiddenItem;
	private HiddenItem flgFattaHiddenItem;	
	private HiddenItem idChildProcessHiddenItem;	
	private HiddenItem estremiChildProcessHiddenItem;			
	private HiddenItem rowIdEventoHiddenItem;
	private HiddenItem idProcessHiddenItem;
	
	private ImgItem iconaFlgFatta;
	private ImgItem iconaBlank;
	private StaticTextItem staticTextItem;
	private StaticTextItem eseguiAttivitaItem;
	
	private ReplicableCanvasForm mDynamicForm;
	
	@Override
	public void disegna() {		
		
		mDynamicForm = new ReplicableCanvasForm();
		mDynamicForm.setWrapItemTitles(false);	
		mDynamicForm.setMargin(2);
		
		instanceIdHiddenItem = new HiddenItem("instanceId");
		instanceLabelHiddenItem = new HiddenItem("instanceLabel");			
		activityNameHiddenItem = new HiddenItem("activityName");									
		displayNameHiddenItem = new HiddenItem("displayName");	
		flgEseguibileHiddenItem = new HiddenItem("flgEseguibile");
		motiviNonEsegHiddenItem = new HiddenItem("motiviNonEseg");
		flgDatiVisibiliHiddenItem = new HiddenItem("flgDatiVisibili");
		flgFattaHiddenItem = new HiddenItem("flgFatta");	
		idChildProcessHiddenItem = new HiddenItem("idChildProcess");	
		estremiChildProcessHiddenItem = new HiddenItem("estremiChildProcess");				
		rowIdEventoHiddenItem = new HiddenItem("rowIdEvento");		
		idProcessHiddenItem = new HiddenItem("idProcessHidden");
								
		staticTextItem = new StaticTextItem() {
			@Override
			public void setCanEdit(Boolean canEdit) {
				setTextBoxStyle(it.eng.utility.Styles.formTitle);
			}
		};	
		staticTextItem.setWidth("*");
		staticTextItem.setWrap(false);
		staticTextItem.setEndRow(false);
		staticTextItem.setShowTitle(false);	
		staticTextItem.setTextAlign(Alignment.LEFT);	
		staticTextItem.addClickHandler(new ClickHandler() {			
			@Override
			public void onClick(ClickEvent event) {
				if(event.getForm().getValueAsString("flgDatiVisibili")!=null && event.getForm().getValueAsString("flgDatiVisibili").equalsIgnoreCase("true")) {
					visualizzaAttivita();
				}
			}
		});		
		staticTextItem.setShowIfCondition(new FormItemIfFunction() {
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				if(form.getValueAsString("flgDatiVisibili")!=null && form.getValueAsString("flgDatiVisibili").equalsIgnoreCase("true")) {
					item.setValue("<div style=\"cursor:pointer\"><u>" + form.getValueAsString("displayName") + "</u></div>");
				} else {
					item.setValue("<div style=\"cursor:default\">" + form.getValueAsString("displayName") + "</div>");
				}
				return true;						   						  
			}
		});	
		
		iconaFlgFatta = new ImgItem("iconaFlgFatta", "archivio/flgFatta.png", "Attività eseguita");
		iconaFlgFatta.setWidth(30);
		iconaFlgFatta.setAlign(Alignment.LEFT);
		iconaFlgFatta.setShowIfCondition(new FormItemIfFunction() {
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return (form.getValueAsString("flgFatta")!=null && form.getValueAsString("flgFatta").equalsIgnoreCase("true"));						   						  
			}
		});		
		
		iconaBlank = new ImgItem("iconaBlank", "archivio/point.png", null);
		iconaBlank.setWidth(30);
		iconaBlank.setAlign(Alignment.LEFT);
		iconaBlank.setShowIfCondition(new FormItemIfFunction() {
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return (form.getValueAsString("flgFatta")==null || !form.getValueAsString("flgFatta").equalsIgnoreCase("true"));						   						  
			}
		});		
		
		eseguiAttivitaItem = new StaticTextItem() {
			@Override
			public void setCanEdit(Boolean canEdit) {
				setTextBoxStyle(it.eng.utility.Styles.staticTextItem);   
			}
		};	
		eseguiAttivitaItem.setWrap(false);
		eseguiAttivitaItem.setEndRow(false);
		eseguiAttivitaItem.setShowTitle(false);	
		eseguiAttivitaItem.setWidth(30);
		eseguiAttivitaItem.setTextAlign(Alignment.CENTER);	
		eseguiAttivitaItem.addClickHandler(new ClickHandler() {			
			@Override
			public void onClick(ClickEvent event) {
				if(event.getForm().getValueAsString("flgEseguibile")!=null && event.getForm().getValueAsString("flgEseguibile").equalsIgnoreCase("true")) {
					eseguiAttivita();
				}
			}
		});		
		eseguiAttivitaItem.setShowIfCondition(new FormItemIfFunction() {
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				if(form.getValueAsString("flgEseguibile")!=null && form.getValueAsString("flgEseguibile").equalsIgnoreCase("true")) {
					item.setPrompt("Esegui attività");
					item.setValue("<img src=\"images/buttons/gear.png\" width=\"16\" height=\"16\" style=\"vertical-align:bottom;cursor:pointer\"/>");
				} else {
					if(form.getValueAsString("motiviNonEseg") != null && !"".equals(form.getValueAsString("motiviNonEseg"))) {
						item.setPrompt("Attività non disponibile: " + form.getValueAsString("motiviNonEseg"));
					} else {
						item.setPrompt("Attività non disponibile");	
					}
					item.setValue("<img src=\"images/buttons/gear_Disabled.png\" width=\"16\" height=\"16\" style=\"vertical-align:bottom;cursor:default\"/>");
				}
				return true;						   						  
			}
		});	
				
		mDynamicForm.setFields(
			instanceIdHiddenItem,
			instanceLabelHiddenItem,			
			activityNameHiddenItem,	
			displayNameHiddenItem,
			flgEseguibileHiddenItem,
			motiviNonEsegHiddenItem,
			flgDatiVisibiliHiddenItem,
			flgFattaHiddenItem,	
			idChildProcessHiddenItem,	
			estremiChildProcessHiddenItem,	
			rowIdEventoHiddenItem,				
			idProcessHiddenItem,
			iconaBlank,
			iconaFlgFatta,					
			staticTextItem,
			eseguiAttivitaItem
		);
			
		mDynamicForm.setNumCols(4);		
		mDynamicForm.setColWidths("30","1","30","*");		
		
		addChild(mDynamicForm);
	}
	
	//visualizza l'ultimo evento
	public void visualizzaAttivita() {		
		TaskUtil.createTaskDetail(mDynamicForm.getValueAsString("instanceId"), mDynamicForm.getValueAsString("idProcess"), mDynamicForm.getValueAsString("activityName"), false, new TaskUtilCallback() {
			@Override
			public void execute(TaskDetail taskDetail) {
				TaskWindow window = new TaskWindow(taskDetail.getNomeEntita(), mDynamicForm.getValueAsString("displayName"), taskDetail);
				window.viewMode();
				window.show();
			}			
		});
	}
	
	//crea un nuovo evento
	public void eseguiAttivita() {
		TaskUtil.createTaskDetail(mDynamicForm.getValueAsString("instanceId"), mDynamicForm.getValueAsString("idProcess"), mDynamicForm.getValueAsString("activityName"), true, new TaskUtilCallback() {
			@Override
			public void execute(TaskDetail taskDetail) {
				TaskWindow window = new TaskWindow(taskDetail.getNomeEntita(), mDynamicForm.getValueAsString("displayName"), taskDetail) {
					@Override
					public void manageOnCloseClickAfterSaved() {
						((TaskItem)getItem()).reloadTask();
					}
				};
				window.newMode();
				window.show();
			}			
		});
	}

	@Override
	public ReplicableCanvasForm[] getForm() {
		return new ReplicableCanvasForm[]{mDynamicForm};
	}
	
}
