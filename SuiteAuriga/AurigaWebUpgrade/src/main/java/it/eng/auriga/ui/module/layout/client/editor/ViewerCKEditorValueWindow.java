/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import com.smartgwt.client.data.Record;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.Overflow;
import com.smartgwt.client.widgets.Button;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.layout.HStack;
import com.smartgwt.client.widgets.layout.VLayout;

import it.eng.utility.ui.module.layout.client.portal.ModalWindow;

public class ViewerCKEditorValueWindow extends ModalWindow {
	
	protected DynamicForm form;
	protected CKEditorItem ckEditorValueItem;
	protected Button okButton;
	
	public ViewerCKEditorValueWindow(final CKEditorItem ckEditorItemRif) {
		
		super("visualizza_ckeditor_value", true);
		
		String title = ckEditorItemRif.getTitle() != null ? ckEditorItemRif.getTitle() : "";
		setTitle("Visualizza contenuti " + title);	
		
		settingsMenu.removeItem(separatorMenuItem);
		settingsMenu.removeItem(autoSearchMenuItem);
		
		setWidth(800);
		setHeight(580);
		
		final VLayout layout = new VLayout();
		layout.setWidth100();
		layout.setHeight100();
		layout.setLayoutLeftMargin(5);
		layout.setLayoutRightMargin(5);
		layout.setLayoutTopMargin(5);
		layout.setLayoutBottomMargin(5);

		form = new DynamicForm();
		form.setWidth("100%");  
		form.setHeight("100%");  
		form.setNumCols(15);
		form.setColWidths(1,1,1,1,1,1,1,1,1,1,1,1,1,"*","*");
//		form.setStyleName(it.eng.utility.Styles.formWithCKEditor);

		Record params = ckEditorItemRif.getParamsViewerCKEditor();
		int numMaxCaratteri = params != null ? params.getAttributeAsInt("numMaxCaratteri") : 0;
		String tipoEditor = params != null ? params.getAttribute("tipoEditor") : null;
		boolean upperCase = params != null ? params.getAttributeAsBoolean("upperCase") : false;
		boolean hideBorder = params != null ? params.getAttributeAsBoolean("hideBorder") : false;
		
		ckEditorValueItem = new CKEditorItem("ckEditorValue", numMaxCaratteri, tipoEditor, "100%", -1, "", upperCase, hideBorder) {
			
			@Override
			 public boolean showViewer() {
		    	return false;
		    }
		};
		ckEditorValueItem.setShowTitle(false);			
		ckEditorValueItem.setColSpan(15);
		
		form.setFields(ckEditorValueItem);
		
		layout.setMembers(form);		
		
		okButton = new Button("Ok");
		okButton.setIcon("ok.png");
		okButton.setIconSize(16);
		okButton.setAutoFit(false);
		okButton.addClickHandler(new com.smartgwt.client.widgets.events.ClickHandler() {

			@Override
			public void onClick(com.smartgwt.client.widgets.events.ClickEvent event) {
				if(ckEditorItemRif.getCanEdit()) {
					ckEditorItemRif.setValue(ckEditorValueItem.getValue());
				}
				markForDestroy();
			}
		});
		
		HStack _buttons = new HStack(5);
		_buttons.setHeight(30);
		_buttons.setAlign(Alignment.CENTER);
		_buttons.setPadding(5);
		_buttons.addMember(okButton);
		
		VLayout portletLayout = new VLayout();
		portletLayout.setHeight100();
		portletLayout.setWidth100();
		portletLayout.setOverflow(Overflow.VISIBLE);
		portletLayout.addMember(layout);
		portletLayout.addMember(_buttons);
		
		setBody(portletLayout);

		Record record = new Record();
		record.setAttribute("ckEditorValue", ckEditorItemRif.getValue());
		form.editRecord(record);
		
		ckEditorValueItem.setValue(ckEditorItemRif.getValue());
		ckEditorValueItem.setCanEdit(ckEditorItemRif.getCanEdit());
	}
	
}
