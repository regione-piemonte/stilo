/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.Overflow;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.Button;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.layout.HStack;
import com.smartgwt.client.widgets.layout.VLayout;

import it.eng.utility.ui.module.layout.client.common.DetailSection;
import it.eng.utility.ui.module.layout.client.common.items.TextAreaItem;
import it.eng.utility.ui.module.layout.client.portal.ModalWindow;

public class CKEditorWindow extends ModalWindow {
	
	public CKEditorWindow(String title, String icon){
		this("ckEditor", title, icon);
	}
	
	public CKEditorWindow(String nomeEntita, String title, String icon){
		
		super(nomeEntita, true);
		
		setTitle(title);
		
		settingsMenu.removeItem(separatorMenuItem);
		settingsMenu.removeItem(autoSearchMenuItem);		
		
		DynamicForm form1 = new DynamicForm();
		form1.setKeepInParentRect(true);
		form1.setWrapItemTitles(false);
		form1.setWidth100();
		form1.setHeight100();
		form1.setNumCols(1);		
		
		final CKEditorItem editor1 = new CKEditorItem("editor1");
		
		form1.setFields(editor1);
		
		DetailSection detailSection1 = new DetailSection("Editor 1", true, true, false, form1);
				
		DynamicForm form2 = new DynamicForm();
		form2.setKeepInParentRect(true);
		form2.setWrapItemTitles(false);
		form2.setWidth100();
		form2.setHeight100();
		form2.setNumCols(1);		
		
		final CKEditorItem editor2 = new CKEditorItem("editor2");
		
		final TextAreaItem textArea1 = new TextAreaItem("textArea1");
		textArea1.setShowTitle(false);
		textArea1.setWidth("100%");
		textArea1.setHeight(245);		
		textArea1.setShowTitle(false);
		textArea1.setColSpan(1);		
		
		
		form2.setFields(editor2, textArea1);
		
		DetailSection detailSection2 = new DetailSection("Editor 2", true, true, false, form2);
		
		Button annullaButton = new Button("Annulla");
		annullaButton.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				editor1.setCanEdit(false);
				editor2.setCanEdit(false);
			}
		});
		
		Button editButton = new Button("Edit");
		editButton.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				editor1.setCanEdit(true);
				editor2.setCanEdit(true);
				textArea1.setCanEdit(true);
			}
		});
		
		Button salvaButton = new Button("Salva");
		salvaButton.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				editor1.setCanEdit(false);
				editor2.setCanEdit(false);
				textArea1.setCanEdit(false);
				SC.say(editor1.getValue() + "<br/>" + editor2.getValue());
			}
		});
		
		HStack _buttons = new HStack(5);
		_buttons.setHeight(30);
		_buttons.setAlign(Alignment.CENTER);
		_buttons.setPadding(5);
		_buttons.addMember(annullaButton);
		_buttons.addMember(editButton);
		_buttons.addMember(salvaButton);
		
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

		layout.addMember(detailSection1);
		layout.addMember(detailSection2);
		layout.addMember(spacerLayout);

		portletLayout.addMember(layout);
		portletLayout.addMember(_buttons);
		
		setBody(portletLayout);
		
		setIcon(icon);

	}
	
}