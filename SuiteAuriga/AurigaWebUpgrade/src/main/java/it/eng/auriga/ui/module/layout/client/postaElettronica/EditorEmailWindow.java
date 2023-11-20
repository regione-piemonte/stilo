/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import com.google.gwt.core.client.Scheduler;
import com.google.gwt.core.client.Scheduler.ScheduledCommand;
import com.smartgwt.client.types.HeaderControls;
import com.smartgwt.client.types.VerticalAlignment;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.FormItemIfFunction;
import com.smartgwt.client.widgets.form.fields.FormItem;
import com.smartgwt.client.widgets.form.fields.RichTextItem;

import it.eng.auriga.ui.module.layout.client.i18n.I18NUtil;
import it.eng.utility.ui.module.layout.client.portal.ModalWindow;

/**
 * 
 * @author DANCRIST
 *
 */

public class EditorEmailWindow extends ModalWindow {
	
	private String inputBody;
	
	protected DynamicForm form;
	private RichTextItem corpoMailContenutiItem;

	public EditorEmailWindow(String title,final String inputBody) {
		super("editor_bozza_email");
		
		setTitle(title);
		
		this.inputBody = inputBody;
		
		setMaximized(true);
		setIsModal(false);
		setHeaderControls(HeaderControls.HEADER_ICON, HeaderControls.HEADER_LABEL, HeaderControls.CLOSE_BUTTON);
		setAutoDraw(true);
		
		setIcon("buttons/view_editor.png");	
		
		Scheduler.get().scheduleDeferred(new ScheduledCommand() {

			@Override
			public void execute() {
				initForm(inputBody);
				addItem(form);
			}
		});
	}

	private void initForm(final String inputBody) {
		
		form = new DynamicForm();
		form.setWidth100();
		form.setHeight100();
		form.setWrapItemTitles(false);
		form.setNumCols(12);
		form.setColWidths("1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "*");
		
		corpoMailContenutiItem = new RichTextItem();
		corpoMailContenutiItem.setTitle(I18NUtil.getMessages().posta_elettronica_corpo_mail_contenuti_item());
		corpoMailContenutiItem.setWidth("1550");
		corpoMailContenutiItem.setHeight("680");
		corpoMailContenutiItem.setShowTitle(false);
		corpoMailContenutiItem.setColSpan(1);
		corpoMailContenutiItem.setStartRow(false);
		corpoMailContenutiItem.setEndRow(false);
		corpoMailContenutiItem.setCellStyle(it.eng.utility.Styles.textItem);
		corpoMailContenutiItem.setIconVAlign(VerticalAlignment.CENTER);
		corpoMailContenutiItem.setControlGroups("fontControls", "formatControls", "styleControls", "colorControls", "insertControls");
		corpoMailContenutiItem.setShowIfCondition(new FormItemIfFunction() {

			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				
				corpoMailContenutiItem.setValue(inputBody);
				return true;
			}
		});
		
		form.setFields(corpoMailContenutiItem);
	}

	public String getCurrentBody() {
		return (String) corpoMailContenutiItem.getValue();
	}
}
