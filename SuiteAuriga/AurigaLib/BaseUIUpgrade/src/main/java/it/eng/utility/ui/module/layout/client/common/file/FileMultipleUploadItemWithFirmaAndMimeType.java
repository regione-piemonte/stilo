/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */


import it.eng.utility.ui.module.core.client.callback.UploadMultipleItemCallBackHandler;
import it.eng.utility.ui.module.core.client.i18n.I18NUtil;
import it.eng.utility.ui.module.layout.client.Layout;

import com.google.gwt.core.client.JavaScriptObject;
import com.smartgwt.client.util.JSON;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.FormItemHoverFormatter;
import com.smartgwt.client.widgets.form.fields.FormItem;
import it.eng.utility.ui.module.core.client.UserInterfaceFactory;

/**
 * 
 * @author CRISTIANO
 *
 */

public class FileMultipleUploadItemWithFirmaAndMimeType extends PrettyFileMultipleUploadItem {

	private String smartId;
	private ManageInfoCallbackHandler manageInfoCallbackHandler;

	public FileMultipleUploadItemWithFirmaAndMimeType(
			UploadMultipleItemCallBackHandler pCallBackHandler, ManageInfoCallbackHandler pManageInfoCallbackHandler) {
		super(pCallBackHandler);
		if (UserInterfaceFactory.isAttivaAccessibilita()){
			setCanFocus(true); 			
 		}
		setPrompt(I18NUtil.getMessages().prettyFileUploadInput_title());	
		setItemHoverFormatter(new FormItemHoverFormatter() {					
			@Override
			public String getHoverHTML(FormItem item, DynamicForm form) {
				
				return I18NUtil.getMessages().prettyFileUploadInput_title();
			}
		});
		manageInfoCallbackHandler = pManageInfoCallbackHandler;

		// TODO Auto-generated constructor stub
	}

	public FileMultipleUploadItemWithFirmaAndMimeType(
			UploadMultipleItemCallBackHandler pCallBackHandler, ManageInfoCallbackHandler pManageInfoCallbackHandler, CssAndDimensionFileInput pCssAndDimensionFileInput) {
		super(pCallBackHandler, true, pCssAndDimensionFileInput);
		setPrompt(I18NUtil.getMessages().prettyFileUploadInput_title());	
		if (UserInterfaceFactory.isAttivaAccessibilita()){
			setCanFocus(true); 			
 		}
		setItemHoverFormatter(new FormItemHoverFormatter() {					
			@Override
			public String getHoverHTML(FormItem item, DynamicForm form) {
				
				return I18NUtil.getMessages().prettyFileUploadInput_title();
			}
		});
		manageInfoCallbackHandler = pManageInfoCallbackHandler;

		// TODO Auto-generated constructor stub
	}

	public FileMultipleUploadItemWithFirmaAndMimeType(JavaScriptObject jsObj){
		super(jsObj);
		if (UserInterfaceFactory.isAttivaAccessibilita()){
			setCanFocus(true); 			
 		}
	}

	@Override
	public FileMultipleUploadItemWithFirmaAndMimeType buildObject(JavaScriptObject jsObj) {
		FileMultipleUploadItemWithFirmaAndMimeType lItem = new FileMultipleUploadItemWithFirmaAndMimeType(jsObj);
		lItem.setPrompt(I18NUtil.getMessages().prettyFileUploadInput_title());		
		lItem.setItemHoverFormatter(new FormItemHoverFormatter() {					
			@Override
			public String getHoverHTML(FormItem item, DynamicForm form) {
				
				return I18NUtil.getMessages().prettyFileUploadInput_title();
			}
		});		
		if (UserInterfaceFactory.isAttivaAccessibilita()){
			lItem.setCanFocus(true); 			
 		}
		lItem.manageInfoCallbackHandler = manageInfoCallbackHandler;
		return lItem;
	}

	@Override
	protected void disegna(Object value) {
		
		super.disegna(value);
		smartId = ((PrettyFileMultipleUploadInput)getCanvas()).getSmartId();
		initUploadInfoCallback(this, "uploadInfo_" + smartId, Layout.isExternalPortlet);
	}

	private native void initUploadInfoCallback(FileMultipleUploadItemWithFirmaAndMimeType fileMultipleUploadItemWithFirmaAndMimeType, String functionName, boolean isExternalPortlet) /*-{
	 	if (isExternalPortlet){
	   $doc[functionName] = function (value) {
	       fileMultipleUploadItemWithFirmaAndMimeType.@it.eng.utility.ui.module.layout.client.common.file.FileMultipleUploadItemWithFirmaAndMimeType::uploadInfoCallback(Ljava/lang/String;)(value);
	   };
	 	} else {
	 	$wnd[functionName] = function (value) {
	       fileMultipleUploadItemWithFirmaAndMimeType.@it.eng.utility.ui.module.layout.client.common.file.FileMultipleUploadItemWithFirmaAndMimeType::uploadInfoCallback(Ljava/lang/String;)(value);
	   };
	 	}
	}-*/;

	public void uploadInfoCallback(String info){
		PrettyFileMultipleUploadInput lPrettyFileMultipleUploadInput = (PrettyFileMultipleUploadInput) getCanvas();
		if (!lPrettyFileMultipleUploadInput.isCancelUpload()) {
			InfoFileRecord lRecord = new InfoFileRecord(JSON.decode(info));
			manageInfoCallbackHandler.manageInfo(lRecord);
		}
	}

}
