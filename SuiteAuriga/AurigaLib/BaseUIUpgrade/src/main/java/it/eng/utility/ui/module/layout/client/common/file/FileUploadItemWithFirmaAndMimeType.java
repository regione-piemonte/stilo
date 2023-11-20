/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.utility.ui.module.core.client.callback.UploadItemCallBackHandler;
import it.eng.utility.ui.module.core.client.i18n.I18NUtil;
import it.eng.utility.ui.module.layout.client.Layout;

import com.google.gwt.core.client.JavaScriptObject;
import com.smartgwt.client.util.JSON;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.FormItemHoverFormatter;
import com.smartgwt.client.widgets.form.fields.FormItem;
import it.eng.utility.ui.module.core.client.UserInterfaceFactory;

public class FileUploadItemWithFirmaAndMimeType extends PrettyFileUploadItem {

	private String smartId;
	private ManageInfoCallbackHandler manageInfoCallbackHandler;

	public FileUploadItemWithFirmaAndMimeType(UploadItemCallBackHandler pCallBackHandler, ManageInfoCallbackHandler pManageInfoCallbackHandler) {
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
	
	public FileUploadItemWithFirmaAndMimeType(UploadItemCallBackHandler pCallBackHandler, ManageInfoCallbackHandler pManageInfoCallbackHandler, CssAndDimensionFileInput pCssAndDimensionFileInput) {
		super(pCallBackHandler, true, pCssAndDimensionFileInput);
		if(pCssAndDimensionFileInput != null && pCssAndDimensionFileInput.isShowHover()) {
			setPrompt(I18NUtil.getMessages().prettyFileUploadInput_title());	
			setItemHoverFormatter(new FormItemHoverFormatter() {					
				@Override
				public String getHoverHTML(FormItem item, DynamicForm form) {
					
					return I18NUtil.getMessages().prettyFileUploadInput_title();
				}
			});
		}
		manageInfoCallbackHandler = pManageInfoCallbackHandler;
	}

	public FileUploadItemWithFirmaAndMimeType(JavaScriptObject jsObj){
		super(jsObj);
	}

	@Override
	public FileUploadItemWithFirmaAndMimeType buildObject(JavaScriptObject jsObj) {
		FileUploadItemWithFirmaAndMimeType lItem = new FileUploadItemWithFirmaAndMimeType(jsObj);
		lItem.setPrompt(I18NUtil.getMessages().prettyFileUploadInput_title());		
		lItem.setItemHoverFormatter(new FormItemHoverFormatter() {					
			@Override
			public String getHoverHTML(FormItem item, DynamicForm form) {
				
				return I18NUtil.getMessages().prettyFileUploadInput_title();
			}
		});		
		lItem.manageInfoCallbackHandler = manageInfoCallbackHandler;
		return lItem;
	}

	@Override
	protected void disegna(Object value) {
		
		super.disegna(value);
		smartId = ((PrettyFileUploadInput)getCanvas()).getSmartId();
		initUploadInfoCallback(this, "uploadInfo_" + smartId, Layout.isExternalPortlet);
	}

	private native void initUploadInfoCallback(FileUploadItemWithFirmaAndMimeType fileUploadItemWithFirmaAndMimeType, String functionName, boolean isExternalPortlet) /*-{
	 	if (isExternalPortlet){
	   $doc[functionName] = function (value) {
	       fileUploadItemWithFirmaAndMimeType.@it.eng.utility.ui.module.layout.client.common.file.FileUploadItemWithFirmaAndMimeType::uploadInfoCallback(Ljava/lang/String;)(value);
	   };
	 	} else {
	 	$wnd[functionName] = function (value) {
	       fileUploadItemWithFirmaAndMimeType.@it.eng.utility.ui.module.layout.client.common.file.FileUploadItemWithFirmaAndMimeType::uploadInfoCallback(Ljava/lang/String;)(value);
	   };
	 	}
	}-*/;

	public void uploadInfoCallback(String info){
		InfoFileRecord lRecord = new InfoFileRecord(JSON.decode(info));
		manageInfoCallbackHandler.manageInfo(lRecord);
	}

}
