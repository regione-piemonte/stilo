/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import com.google.gwt.core.client.JavaScriptObject;
import com.smartgwt.client.core.RefDataClass;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.docs.TabOrderOverview;
import com.smartgwt.client.util.EventHandler;
import com.smartgwt.client.widgets.TabIndexManager;
import com.smartgwt.client.widgets.events.FocusChangedEvent;
import com.smartgwt.client.widgets.events.FocusChangedHandler;
import com.smartgwt.client.widgets.events.KeyDownEvent;
import com.smartgwt.client.widgets.events.KeyDownHandler;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.FormItemHoverFormatter;
import com.smartgwt.client.widgets.form.fields.CanvasItem;
import com.smartgwt.client.widgets.form.fields.FormItem;
import com.smartgwt.client.widgets.form.fields.events.FormItemInitHandler;
import com.smartgwt.client.widgets.form.fields.events.ShowValueEvent;
import com.smartgwt.client.widgets.form.fields.events.ShowValueHandler;
import com.smartgwt.client.widgets.tile.TileGrid;

import it.eng.utility.ui.module.core.client.callback.UploadItemCallBackHandler;
import it.eng.utility.ui.module.core.client.i18n.I18NUtil;
import it.eng.utility.ui.module.core.client.UserInterfaceFactory;

/**
 * CanvasItem che disegna al suo interno il {@link PrettyFileUploadInput}
 * @author Rametta
 *
 */
public class PrettyFileUploadItem extends CanvasItem {

	private PrettyFileUploadItem _instance;
	private UploadItemCallBackHandler _callback;
	private boolean _showProgressBar = true;
	private CssAndDimensionFileInput _cssAndDimensionFileInput;
	private String _action;

	public PrettyFileUploadItem(JavaScriptObject jsObj){
		super(jsObj);
	}

	public PrettyFileUploadItem buildObject(JavaScriptObject jsObj){
		PrettyFileUploadItem lItem = new PrettyFileUploadItem(jsObj); return lItem;
	}

	public static PrettyFileUploadItem getOrCreateRef(JavaScriptObject jsObj) {
		if(jsObj == null) return null;
		RefDataClass obj = RefDataClass.getRef(jsObj);
		if(obj != null) {
			obj.setJsObj(jsObj);
			return (PrettyFileUploadItem) obj;
		} else {
			return null;
		}
	}
	
	

	/**
	 * Crea un {@link PrettyFileUploadItem} che quindi contiene al suo interno un {@link PrettyFileUploadInput}
	 * che avrà come callback la {@link UploadItemCallBackHandler} passata come parametro
	 * 
	 * @param pCallBackHandler
	 */
	public PrettyFileUploadItem(final UploadItemCallBackHandler pCallBackHandler){
		setAutoDestroy(true); // per eliminare automaticamente il canvas quando elimino il canvasItem	
		setInitHandler(new FormItemInitHandler() {
			public void onInit(FormItem item) {
				//Inizializza il componente
				PrettyFileUploadItem lPrettyFileUploadItem = buildObject(item.getJsObj());
				lPrettyFileUploadItem._instance = lPrettyFileUploadItem;
				lPrettyFileUploadItem._callback = pCallBackHandler;
				lPrettyFileUploadItem.disegna(item.getValue());
				lPrettyFileUploadItem.setPrompt(I18NUtil.getMessages().prettyFileUploadInput_title());
				lPrettyFileUploadItem.setItemHoverFormatter(new FormItemHoverFormatter() {					
					@Override
					public String getHoverHTML(FormItem item, DynamicForm form) {
						
						return I18NUtil.getMessages().prettyFileUploadInput_title();
					}
				});
				lPrettyFileUploadItem.addShowValueHandler(new ShowValueHandler() {					
					@Override
					public void onShowValue(ShowValueEvent event) {
						
						redrawPrettyFileUploadInput();
					}
				});
			}
		});	
	}
	
	/**
	 * Crea un {@link PrettyFileUploadItem} che quindi contiene al suo interno un {@link PrettyFileUploadInput}
	 * che avrà come callback la {@link UploadItemCallBackHandler} passata come parametro
	 * 
	 * @param pCallBackHandler
	 */
	public PrettyFileUploadItem(final UploadItemCallBackHandler pCallBackHandler, final boolean showProgressBar){
		setAutoDestroy(true); // per eliminare automaticamente il canvas quando elimino il canvasItem
		setInitHandler(new FormItemInitHandler() {
			public void onInit(FormItem item) {
				//Inizializza il componente
				PrettyFileUploadItem lPrettyFileUploadItem = buildObject(item.getJsObj());
				lPrettyFileUploadItem._instance = lPrettyFileUploadItem;
				lPrettyFileUploadItem._callback = pCallBackHandler;
				lPrettyFileUploadItem._showProgressBar = showProgressBar;
				lPrettyFileUploadItem.disegna(item.getValue());
				lPrettyFileUploadItem.setPrompt(I18NUtil.getMessages().prettyFileUploadInput_title());
				lPrettyFileUploadItem.setItemHoverFormatter(new FormItemHoverFormatter() {					
					@Override
					public String getHoverHTML(FormItem item, DynamicForm form) {
						
						return I18NUtil.getMessages().prettyFileUploadInput_title();
					}
				});
				lPrettyFileUploadItem.addShowValueHandler(new ShowValueHandler() {					
					@Override
					public void onShowValue(ShowValueEvent event) {
						
						redrawPrettyFileUploadInput();
					}
				});
			}
		});	
	}
	
	/**
	 * Crea un {@link PrettyFileUploadItem} che quindi contiene al suo interno un {@link PrettyFileUploadInput}
	 * che avrà come callback la {@link UploadItemCallBackHandler} passata come parametro
	 * 
	 * @param pCallBackHandler
	 */
	public PrettyFileUploadItem(final UploadItemCallBackHandler pCallBackHandler, final boolean showProgressBar, final String action){
		setAutoDestroy(true); // per eliminare automaticamente il canvas quando elimino il canvasItem
		setInitHandler(new FormItemInitHandler() {
			public void onInit(FormItem item) {
				//Inizializza il componente
				PrettyFileUploadItem lPrettyFileUploadItem = buildObject(item.getJsObj());
				lPrettyFileUploadItem._instance = lPrettyFileUploadItem;
				lPrettyFileUploadItem._callback = pCallBackHandler;
				lPrettyFileUploadItem._showProgressBar = showProgressBar;
				lPrettyFileUploadItem._action = action;
				lPrettyFileUploadItem.disegna(item.getValue());
				lPrettyFileUploadItem.setPrompt(I18NUtil.getMessages().prettyFileUploadInput_title());
				lPrettyFileUploadItem.setItemHoverFormatter(new FormItemHoverFormatter() {					
					@Override
					public String getHoverHTML(FormItem item, DynamicForm form) {
						
						return I18NUtil.getMessages().prettyFileUploadInput_title();
					}
				});
				lPrettyFileUploadItem.addShowValueHandler(new ShowValueHandler() {					
					@Override
					public void onShowValue(ShowValueEvent event) {
						
						redrawPrettyFileUploadInput();
					}
				});
			}
		});	
	}
	
	/**
	 * Crea un {@link PrettyFileUploadItem} che quindi contiene al suo interno un {@link PrettyFileUploadInput}
	 * che avrà come callback la {@link UploadItemCallBackHandler} passata come parametro
	 * 
	 * @param pCallBackHandler
	 */
	public PrettyFileUploadItem(final UploadItemCallBackHandler pCallBackHandler, final boolean showProgressBar, final CssAndDimensionFileInput pCssAndDimensionFileInput){
		setAutoDestroy(true); // per eliminare automaticamente il canvas quando elimino il canvasItem
		setInitHandler(new FormItemInitHandler() {
			public void onInit(FormItem item) {
				//Inizializza il componente
				PrettyFileUploadItem lPrettyFileUploadItem = buildObject(item.getJsObj());
				if (UserInterfaceFactory.isAttivaAccessibilita()){
 					lPrettyFileUploadItem.setTabIndex(null);
					lPrettyFileUploadItem.setCanFocus(true);
 				}
				lPrettyFileUploadItem._instance = lPrettyFileUploadItem;
				lPrettyFileUploadItem._callback = pCallBackHandler;
				lPrettyFileUploadItem._showProgressBar = showProgressBar;
				lPrettyFileUploadItem._cssAndDimensionFileInput = pCssAndDimensionFileInput;
				lPrettyFileUploadItem.disegna(item.getValue());
				if(pCssAndDimensionFileInput != null && pCssAndDimensionFileInput.isShowHover()) {					
					lPrettyFileUploadItem.setPrompt(I18NUtil.getMessages().prettyFileUploadInput_title());
					lPrettyFileUploadItem.setItemHoverFormatter(new FormItemHoverFormatter() {					
						@Override
						public String getHoverHTML(FormItem item, DynamicForm form) {
							
							return I18NUtil.getMessages().prettyFileUploadInput_title();
						}
					});
				}
				lPrettyFileUploadItem.addShowValueHandler(new ShowValueHandler() {					
					@Override
					public void onShowValue(ShowValueEvent event) {
						
						redrawPrettyFileUploadInput();
					}
				});
			}
		});	
	}

	protected void disegna(Object value) {
		setShowTitle(false);
		final PrettyFileUploadInput lPrettyFileUploadInput = new PrettyFileUploadInput(_callback, _cssAndDimensionFileInput);
		if (UserInterfaceFactory.isAttivaAccessibilita()){
			lPrettyFileUploadInput.setCanFocus(true);
	//		lPrettyFileUploadInput.addFocusChangedHandler(new FocusChangedHandler() {
	//			
	//			@Override
	//			public void onFocusChanged(FocusChangedEvent event) {
	//				// TODO Auto-generated method stub
	//				if (event.getHasFocus()) {
	//					lPrettyFileUploadInput.focusOnInputFile(lPrettyFileUploadInput.getIndiceTab());
	//				}
	//			}
	//		});
			
			lPrettyFileUploadInput.addKeyDownHandler(new KeyDownHandler() {
				
				@Override
				public void onKeyDown(KeyDownEvent event) {
					if (EventHandler.getKey().equalsIgnoreCase("Enter") || EventHandler.getKey().equalsIgnoreCase("Space")){
						lPrettyFileUploadInput.focusOnInputFile(lPrettyFileUploadInput.getIndiceTab());
	                }
				}
			});
			lPrettyFileUploadInput.setName("upload_file_input");	
 		}
		lPrettyFileUploadInput.setShowProgressWindow(_showProgressBar);
		if (_action!=null && !_action.trim().equals("")){
			lPrettyFileUploadInput.setAction(_action);
		}	
		if(_cssAndDimensionFileInput != null && _cssAndDimensionFileInput.isShowHover()) {
			lPrettyFileUploadInput.setPrompt(I18NUtil.getMessages().prettyFileUploadInput_title());		
			lPrettyFileUploadInput.setShowHover(true);	
		}
		setCanvas(lPrettyFileUploadInput);
		setWidth(22);
		setHeight(22);
	}
	
	public void redrawPrettyFileUploadInput() {
		PrettyFileUploadInput lPrettyFileUploadInput = (PrettyFileUploadInput) getCanvas();
		lPrettyFileUploadInput.redraw();
	}

	public PrettyFileUploadItem getInstance() {
		return _instance;
	}
	
	@Override
    public void setCanEdit(Boolean canEdit) {
    	
    	if(canEdit) {
    		if(getAttributeAsBoolean("nascosto") != null && getAttributeAsBoolean("nascosto")) {
    			try{
					hide(); 
				}catch(Exception e){
					setVisible(false);
				}
			} else {				
				try{
					show(); 
				}catch(Exception e){
					setVisible(true);
				}
			}
    	} else {
    		try{
				hide(); 
			}catch(Exception e){
				setVisible(false);
			}
    	}
    }
	
//	public native void focusOnInputFile (Integer tabIndex)/*-{
//    $("#fileUploadAttr").focus();
//	}-*/;
	

}
