/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.user.client.ui.HTML;
import com.smartgwt.client.core.RefDataClass;
import com.smartgwt.client.types.VerticalAlignment;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.form.fields.CanvasItem;
import com.smartgwt.client.widgets.form.fields.FormItem;
import com.smartgwt.client.widgets.form.fields.events.FormItemInitHandler;
import com.smartgwt.client.widgets.form.fields.events.ShowValueEvent;
import com.smartgwt.client.widgets.form.fields.events.ShowValueHandler;
import com.smartgwt.client.widgets.layout.VLayout;

public class HtmlItem extends CanvasItem {

	public HtmlItem(JavaScriptObject jsObj){
		super(jsObj);
	}

	/**
	 * Esegue il build quando l'oggetto non esiste.
	 * return ReplicableItem;
	 * @param jsObj
	 * @return
	 */
	public HtmlItem buildObject(JavaScriptObject jsObj) {
		HtmlItem lItem = getOrCreateRef(jsObj); return lItem;
	}

	/**
	 * Crea l'istanza o ne restituisce una a partire dall'oggetto Javascript
	 * @param jsObj
	 * @return
	 */
	public static HtmlItem getOrCreateRef(JavaScriptObject jsObj) {
		if(jsObj == null) return null;
		RefDataClass obj = RefDataClass.getRef(jsObj);
		if(obj != null) {
			obj.setJsObj(jsObj);
			return (HtmlItem) obj;
		} else {
			return null;
		}
	}
	
	/**
	 * Crea un ReplicableItem. In fase di init, lo disegna e ne setta
	 * lo showHandler per gestire il setValue
	 * 
	 */
	public HtmlItem(){
		setAutoDestroy(true); // per eliminare automaticamente il canvas quando elimino il canvasItem
		setInitHandler(new FormItemInitHandler() {
			public void onInit(FormItem item) {
				//Inizializza il componente
				buildObject(item.getJsObj()).disegna(item.getValue());
				//Setto lo showValue (Gestiste il setValue
				addShowValueHandler(new ShowValueHandler() {
					@Override
					public void onShowValue(ShowValueEvent event) {						
						String lStringValue = (String) event.getDataValue();
						//Disegna e gestisce il valore da caricare
						drawAndSetValue(lStringValue);						
					}
				});		
			}
		});			
	}

	protected void disegna(Object value) {
		HTML lHtml = new HTML();
		VLayout lVLayout = new VLayout();
		lVLayout.setHeight100();
		lVLayout.setAlign(VerticalAlignment.CENTER);
		lVLayout.addMember(lHtml);
		lHtml.setHeight("100%");
		setCanvas(lVLayout);
	}
	
	private void drawAndSetValue(String lStringValue) {

		HTML lHtml = new HTML(lStringValue);
		
		final VLayout lVLayout = (VLayout) getCanvas();
		for (Canvas lCanvas : lVLayout.getMembers()){
				lVLayout.removeMember(lCanvas);
		}		
//		HtmlItem lHtmlItem = (HtmlItem) lVLayout.getCanvasItem();
//		lHtmlItem.setHeight(100);
		lVLayout.setHeight100();
		lVLayout.setAlign(VerticalAlignment.CENTER);
		lVLayout.addMember(lHtml);
		lHtml.setHeight("100%");
		setCanvas(lVLayout);
	}
	
}
