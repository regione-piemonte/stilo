/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.utility.ui.module.core.client.callback.UploadMultipleItemCallBackHandler;
import it.eng.utility.ui.module.core.client.i18n.I18NUtil;

import com.google.gwt.core.client.JavaScriptObject;
import com.smartgwt.client.core.RefDataClass;
import com.smartgwt.client.util.EventHandler;
import com.smartgwt.client.widgets.events.KeyDownEvent;
import com.smartgwt.client.widgets.events.KeyDownHandler;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.FormItemHoverFormatter;
import com.smartgwt.client.widgets.form.fields.CanvasItem;
import com.smartgwt.client.widgets.form.fields.FormItem;
import com.smartgwt.client.widgets.form.fields.events.FormItemInitHandler;
import com.smartgwt.client.widgets.form.fields.events.ShowValueEvent;
import com.smartgwt.client.widgets.form.fields.events.ShowValueHandler;

import it.eng.utility.ui.module.core.client.callback.UploadMultipleItemCallBackHandler;
import it.eng.utility.ui.module.core.client.i18n.I18NUtil;
import it.eng.utility.ui.module.core.client.UserInterfaceFactory;

/**
 * CanvasItem che disegna al suo interno il {@link PrettyFileMultipleUploadInput}
 * @author Cristiano
 *
 */

public class PrettyFileMultipleUploadItem extends CanvasItem {
	
	private PrettyFileMultipleUploadItem _instance;
	private UploadMultipleItemCallBackHandler _callback;
	private boolean _showProgressBar = true;
	private CssAndDimensionFileInput _cssAndDimensionFileInput;
	
	public PrettyFileMultipleUploadItem(JavaScriptObject jsObj){
		super(jsObj);
		if (UserInterfaceFactory.isAttivaAccessibilita()){
			setCanFocus(true);	
 		}
	}

	public PrettyFileMultipleUploadItem buildObject(JavaScriptObject jsObj){
		PrettyFileMultipleUploadItem lItem = new PrettyFileMultipleUploadItem(jsObj); return lItem;
	}

	public static PrettyFileMultipleUploadItem getOrCreateRef(JavaScriptObject jsObj) {
		if(jsObj == null) return null;
		RefDataClass obj = RefDataClass.getRef(jsObj);
		if(obj != null) {
			obj.setJsObj(jsObj);
			return (PrettyFileMultipleUploadItem) obj;
		} else {
			return null;
		}
	}
	
	/**
	 * Crea un {@link PrettyFileMultipleUploadItem} che quindi contiene al suo interno un {@link PrettyFileMultipleUploadInput}
	 * che avrà come callback la {@link UploadMultipleItemCallBackHandler} passata come parametro
	 * 
	 * @param pCallBackHandler
	 */
	public PrettyFileMultipleUploadItem(final UploadMultipleItemCallBackHandler pCallBackHandler){
		setAutoDestroy(true); // per eliminare automaticamente il canvas quando elimino il canvasItem		
		if (UserInterfaceFactory.isAttivaAccessibilita()){
			setCanFocus(true);	
 		}
		setInitHandler(new FormItemInitHandler() {
			public void onInit(FormItem item) {
				//Inizializza il componente
				PrettyFileMultipleUploadItem lPrettyFileMultipleUploadItem = buildObject(item.getJsObj());
				if (UserInterfaceFactory.isAttivaAccessibilita()){
					lPrettyFileMultipleUploadItem.setCanFocus(true);
		 		}
				lPrettyFileMultipleUploadItem._instance = lPrettyFileMultipleUploadItem;
				lPrettyFileMultipleUploadItem._callback = pCallBackHandler;
				lPrettyFileMultipleUploadItem.disegna(item.getValue());
				lPrettyFileMultipleUploadItem.setPrompt(I18NUtil.getMessages().prettyFileUploadInput_title());
				lPrettyFileMultipleUploadItem.setItemHoverFormatter(new FormItemHoverFormatter() {					
					@Override
					public String getHoverHTML(FormItem item, DynamicForm form) {
						
						return I18NUtil.getMessages().prettyFileUploadInput_title();
					}
				});
				lPrettyFileMultipleUploadItem.addShowValueHandler(new ShowValueHandler() {					
					@Override
					public void onShowValue(ShowValueEvent event) {
						
						redrawPrettyFileMultipleUploadInput();
					}
				});
			}
		});	
	}
	
	/**
	 * Crea un {@link PrettyFileMultipleUploadItem} che quindi contiene al suo interno un {@link PrettyFileMultipleUploadInput}
	 * che avrà come callback la {@link UploadMultipleItemCallBackHandler} passata come parametro
	 * 
	 * @param pCallBackHandler
	 */
	public PrettyFileMultipleUploadItem(final UploadMultipleItemCallBackHandler pCallBackHandler, final boolean showProgressBar){
		setAutoDestroy(true); // per eliminare automaticamente il canvas quando elimino il canvasItem
		if (UserInterfaceFactory.isAttivaAccessibilita()){
			setCanFocus(true);	
 		}
		setInitHandler(new FormItemInitHandler() {
			public void onInit(FormItem item) {
				//Inizializza il componente
				PrettyFileMultipleUploadItem lPrettyFileMultipleUploadItem = buildObject(item.getJsObj());
		 		if (UserInterfaceFactory.isAttivaAccessibilita()){
		 			lPrettyFileMultipleUploadItem.setCanFocus(true);
		 		}
				lPrettyFileMultipleUploadItem._instance = lPrettyFileMultipleUploadItem;
				lPrettyFileMultipleUploadItem._callback = pCallBackHandler;
				lPrettyFileMultipleUploadItem._showProgressBar = showProgressBar;
				lPrettyFileMultipleUploadItem.disegna(item.getValue());
				lPrettyFileMultipleUploadItem.setPrompt(I18NUtil.getMessages().prettyFileUploadInput_title());
				lPrettyFileMultipleUploadItem.setItemHoverFormatter(new FormItemHoverFormatter() {					
					@Override
					public String getHoverHTML(FormItem item, DynamicForm form) {
						
						return I18NUtil.getMessages().prettyFileUploadInput_title();
					}
				});
				lPrettyFileMultipleUploadItem.addShowValueHandler(new ShowValueHandler() {					
					@Override
					public void onShowValue(ShowValueEvent event) {
						
						redrawPrettyFileMultipleUploadInput();
					}
				});
			}
		});	
	}
	
	/**
	 * Crea un {@link PrettyFileMultipleUploadItem} che quindi contiene al suo interno un {@link PrettyFileMultipleUploadInput}
	 * che avrà come callback la {@link UploadMultipleItemCallBackHandler} passata come parametro
	 * 
	 * @param pCallBackHandler
	 */
	public PrettyFileMultipleUploadItem(final UploadMultipleItemCallBackHandler pCallBackHandler, final boolean showProgressBar, final CssAndDimensionFileInput pCssAndDimensionFileInput){
		setAutoDestroy(true); // per eliminare automaticamente il canvas quando elimino il canvasItem
		if (UserInterfaceFactory.isAttivaAccessibilita()){
			setCanFocus(true);	
 		}
		setInitHandler(new FormItemInitHandler() {
			public void onInit(FormItem item) {
				//Inizializza il componente
				PrettyFileMultipleUploadItem lPrettyFileMultipleUploadItem = buildObject(item.getJsObj());
				if (UserInterfaceFactory.isAttivaAccessibilita()){
 					lPrettyFileMultipleUploadItem.setCanFocus(true);
 				}	
				lPrettyFileMultipleUploadItem._instance = lPrettyFileMultipleUploadItem;
				lPrettyFileMultipleUploadItem._callback = pCallBackHandler;
				lPrettyFileMultipleUploadItem._showProgressBar = showProgressBar;
				lPrettyFileMultipleUploadItem._cssAndDimensionFileInput = pCssAndDimensionFileInput;
				lPrettyFileMultipleUploadItem.disegna(item.getValue());
				lPrettyFileMultipleUploadItem.setPrompt(I18NUtil.getMessages().prettyFileUploadInput_title());
				lPrettyFileMultipleUploadItem.setItemHoverFormatter(new FormItemHoverFormatter() {					
					@Override
					public String getHoverHTML(FormItem item, DynamicForm form) {
						
						return I18NUtil.getMessages().prettyFileUploadInput_title();
					}
				});
				lPrettyFileMultipleUploadItem.addShowValueHandler(new ShowValueHandler() {					
					@Override
					public void onShowValue(ShowValueEvent event) {
						
						redrawPrettyFileMultipleUploadInput();
					}
				});
			}
		});	
	}

	protected void disegna(Object value) {
		setShowTitle(false);
		final PrettyFileMultipleUploadInput lPrettyFileMultipleUploadInput = new PrettyFileMultipleUploadInput(_callback, _cssAndDimensionFileInput);

		
		if (UserInterfaceFactory.isAttivaAccessibilita()){
 			lPrettyFileMultipleUploadInput.setCanFocus(true);
			lPrettyFileMultipleUploadInput.addKeyDownHandler(new KeyDownHandler() {
				@Override
				public void onKeyDown(KeyDownEvent event) {
					if (EventHandler.getKey().equalsIgnoreCase("Enter") || EventHandler.getKey().equalsIgnoreCase("Space")){
						lPrettyFileMultipleUploadInput.focusOnInputFile(lPrettyFileMultipleUploadInput.getIndiceTab());
					}
				}
			});
 		}
		lPrettyFileMultipleUploadInput.setShowProgressWindow(_showProgressBar);
		lPrettyFileMultipleUploadInput.setPrompt(I18NUtil.getMessages().prettyFileUploadInput_title());		
		lPrettyFileMultipleUploadInput.setShowHover(true);		
		setCanvas(lPrettyFileMultipleUploadInput);
		setWidth(22);
		setHeight(22);
	}
	
	public void redrawPrettyFileMultipleUploadInput() {
		PrettyFileMultipleUploadInput lPrettyFileMultipleUploadInput = (PrettyFileMultipleUploadInput) getCanvas();
		lPrettyFileMultipleUploadInput.redraw();
	}

	public PrettyFileMultipleUploadItem getInstance() {
		return _instance;
	}
	
	@Override
    public void setCanEdit(Boolean canEdit) {
    	
    	if(canEdit) {
    		show();
    	} else {
    		hide();
    	}
    }

}
