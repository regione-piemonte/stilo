/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import com.google.gwt.core.client.JavaScriptObject;
import com.smartgwt.client.core.RefDataClass;
import com.smartgwt.client.widgets.Img;
import com.smartgwt.client.widgets.form.fields.CanvasItem;
import com.smartgwt.client.widgets.form.fields.FormItem;
import com.smartgwt.client.widgets.form.fields.events.FormItemInitHandler;

public class ImgItem extends CanvasItem {

	public ImgItem(JavaScriptObject jsObj){
		super(jsObj);		
	}

	public ImgItem buildObject(JavaScriptObject jsObj){
		ImgItem item = new ImgItem(jsObj); 
		return item;
	}

	public static ImgItem getOrCreateRef(JavaScriptObject jsObj) {
		if(jsObj == null) return null;
		RefDataClass obj = RefDataClass.getRef(jsObj);
		if(obj != null) {
			obj.setJsObj(jsObj);
			return (ImgItem) obj;
		} else {
			return null;
		}
	}

	public ImgItem(String name, final String src, String prompt){		
		setName(name);
		setPrompt(prompt);		
		setAttribute("src", src);
		setAutoDestroy(true); // per eliminare automaticamente il canvas quando elimino il canvasItem
		setInitHandler(new FormItemInitHandler() {
			public void onInit(FormItem item) {
				//Inizializza il componente
				ImgItem lImgStaticTextItem = buildObject(item.getJsObj());
				lImgStaticTextItem.disegna(src);
			}
		});
	}

	// Per cambiare l'immagine anche dopo aver creato l'item
	public void setSrc(String src) {
		String srcOrig = getAttribute("src");
		if(src != null && (srcOrig == null || !srcOrig.equals(src))) {
			setAttribute("src", src);
			ImgItem lImgStaticTextItem = buildObject(this.getJsObj());
			lImgStaticTextItem.disegna(src);    
			redraw();
		}		
	}
	
	protected void disegna(String src) {
		if(getCanvas() != null) {
			getCanvas().destroy();
		}
		setShowTitle(false);		
		setCanFocus(false);
		setTabIndex(-1);
		Img image = new Img(src, 16, 16);
		setCanvas(image);
		setWidth(22);
		setHeight(22);
	}
	
	 @Override
	 public void setCanEdit(Boolean canEdit) {
	    super.setCanEdit(true);
	    setTextBoxStyle(it.eng.utility.Styles.noBorderItem);    	 			
	 }

}
