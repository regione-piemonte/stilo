/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.Map;

import com.google.gwt.core.client.JavaScriptObject;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.TextItem;

import it.eng.utility.ui.module.core.client.i18n.I18NUtil;
import it.eng.utility.ui.module.layout.client.common.SelectItemFiltrabile;
import it.eng.utility.ui.module.layout.client.common.items.CustomItem;
import it.eng.utility.ui.module.layout.client.common.items.CustomItemFormField;

public class SegnaturaFascicoloItem extends CustomItem {

	public SegnaturaFascicoloItem(JavaScriptObject jsObject){
		super(jsObject);
	}
	
	public SegnaturaFascicoloItem(){
		super();
	}
		
	public SegnaturaFascicoloItem(final Map<String, String> property){
		super(property);
	}
		
	@Override
	public CustomItem buildObject(JavaScriptObject jsObj) {
		
		CustomItem lCustomItem = new SegnaturaFascicoloItem(jsObj);
		return lCustomItem;
	}
	
	protected DynamicForm getFormToDraw(){
		DynamicForm lFormToDraw = new DynamicForm();
		lFormToDraw.setNumCols(6);
		lFormToDraw.setColWidths(50,100,2,50,2,50);
		return lFormToDraw;
	}

	@Override
	public CustomItemFormField[] getFormFields() {
		CustomItemFormField anno = new CustomItemFormField("anno", I18NUtil.getMessages().estremiRegistrazioneItem_anno_title(), this);
		anno.setShowTitle(false);
		anno.setEditorType(new AnnoItem());
		anno.setValidators(new AllOrNothingValidator(new String[]{"anno","titolazione","numero", "sottofasc"}));
		CustomItemFormField numero =  new CustomItemFormField("numero", ".", this);
		numero.setShowTitle(false);
		numero.setWidth("*");
		TextItem lTextItemNumero = new TextItem();
		lTextItemNumero.setKeyPressFilter("[0-9]");
		numero.setEditorType(lTextItemNumero);
		numero.setTextAlign(Alignment.RIGHT);
		numero.setValidators(new AllOrNothingValidator(new String[]{"anno","titolazione","numero", "sottofasc"}));
		CustomItemFormField slash = new CustomItemFormField("slash","/", this);
		slash.setWidth("*");
		slash.setValue("/");
		slash.setShowTitle(false);
		slash.setAttribute("editorType", "StaticTextItem");
		CustomItemFormField punto = new CustomItemFormField("punto","/", this);
		punto.setWidth("*");
		punto.setValue(".");
		punto.setShowTitle(false);
		punto.setAttribute("editorType", "StaticTextItem");
		
		CustomItemFormField sottoFasc =  new CustomItemFormField("sottofasc", "/", this);
		sottoFasc.setEditorType(lTextItemNumero);
		sottoFasc.setShowTitle(false);
		sottoFasc.setWidth("*");
		sottoFasc.setTextAlign(Alignment.RIGHT);
		CustomItemFormField titolazione =  new CustomItemFormField("titolazione", ".", this);
		titolazione.setShowTitle(false);
		SelectItemFiltrabile lSelectItemFiltrabile = new SelectItemFiltrabile("ClassificaSelect", null);
		lSelectItemFiltrabile.setWidth("*");
		lSelectItemFiltrabile.setHoverField("displayValue");
		lSelectItemFiltrabile.setDisplayField("livelli");
		titolazione.setEditorType(lSelectItemFiltrabile);
		titolazione.setWidth("*");
		titolazione.setValidators(new AllOrNothingValidator(new String[]{"anno","titolazione","numero", "sottofasc"}));
		return new CustomItemFormField[]{anno,titolazione, punto, numero, slash, sottoFasc};
	}

}
