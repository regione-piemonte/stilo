/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import com.google.gwt.core.client.GWT;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.Encoding;
import com.smartgwt.client.types.FormMethod;
import com.smartgwt.client.types.HeaderControls;
import com.smartgwt.client.widgets.HTMLPane;
import com.smartgwt.client.widgets.Window;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.UploadItem;
import com.smartgwt.client.widgets.form.fields.events.ChangeEvent;
import com.smartgwt.client.widgets.form.fields.events.ChangeHandler;

public class UploadFilePopup extends Window{
	
	private UploadFilePopup window; 
	private DynamicForm form; 
	
	UploadItem fileItem;
//	ButtonItem submitButton;
	
	public UploadFilePopup(){

		window = this;
		
		setIsModal(true);
		setModalMaskOpacity(50);
		setAutoCenter(true);
		setTitle("Upload");
		setWidth(350);
		setHeight(150);
		setKeepInParentRect(true);
		setShowModalMask(true);
		setShowCloseButton(true);
		setShowMaximizeButton(false);
		setShowMinimizeButton(false);
		
		initComplete(this);
		
		form = new DynamicForm();
		form.setKeepInParentRect(true);
		form.setWidth100();
		form.setHeight100();
		form.setNumCols(2);
		form.setColWidths(120, 120);
		form.setCellPadding(5);
		form.setCanSubmit(true);
		form.setTarget("uploadTarget");
		form.setAction(GWT.getHostPageBaseURL() + "upload");
		form.setMethod(FormMethod.POST);
		form.setEncoding(Encoding.MULTIPART);
		fileItem = new UploadItem();
		fileItem.setTitle("File da uploadare");
		
//		fileItem.setCellStyle("SI-FILES-STYLIZED");
		fileItem.setShowTitle(true);
//		fileItem.setCanEdit(true);
		fileItem.setColSpan(2);
		fileItem.setAlign(Alignment.CENTER);			
		fileItem.setWidth(340);
		fileItem.addChangeHandler(new ChangeHandler() {			
			@Override
			public void onChange(ChangeEvent event) {
				
				form.submitForm();
			}
		});
		
		form.setItems(fileItem);
		
//		addItem(form);
		HTMLPane hPaneObj = new HTMLPane();  
//		hPaneObj.setContents("<label class=\"cabinet\"><input type=\"file\" class=\"file\" /></label>");
		hPaneObj.setContentsURL("upload.jsp");
		addItem(hPaneObj);
		setShowTitle(true);
		setHeaderControls(HeaderControls.HEADER_LABEL, HeaderControls.CLOSE_BUTTON);        
	}
	
	public void uploadCallback(String displayFilename, String uri) {}
	
	public void uploadComplete(String file) {		
		String[] result = file.split("#");		
		String displayFilename = result[0];
		String uri = result[1];
		uploadCallback(displayFilename, uri);
		window.destroy(); 
	}
	
	private native void initComplete(UploadFilePopup upload) /*-{
	   $wnd.uploadComplete = function (file) {
	       upload.@it.eng.utility.ui.module.layout.client.common.UploadFilePopup::uploadComplete(Ljava/lang/String;)(file);
	   };
	}-*/;

}
