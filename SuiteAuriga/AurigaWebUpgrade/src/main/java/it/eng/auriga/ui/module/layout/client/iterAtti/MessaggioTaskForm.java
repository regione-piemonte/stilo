/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.utility.ui.module.core.client.datasource.GWTRestDataSource;
import it.eng.utility.ui.module.layout.client.StringSplitterClient;
import it.eng.utility.ui.module.layout.client.common.items.CheckboxItem;
import it.eng.utility.ui.module.layout.client.common.items.SelectItem;
import it.eng.utility.ui.module.layout.client.common.items.TextItem;

import com.smartgwt.client.data.Record;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.FieldType;
import com.smartgwt.client.types.TitleOrientation;
import com.smartgwt.client.types.VerticalAlignment;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.FormItem;
import com.smartgwt.client.widgets.form.fields.RadioGroupItem;

public class MessaggioTaskForm extends DynamicForm {
	
	private SelezionaEsitoTaskWindow window;
	private DynamicForm instance;

	private FormItem esitoItem;
	
	public MessaggioTaskForm(SelezionaEsitoTaskWindow window, Record attr){
		
		instance = this;
		
		this.window = window;
		
		setKeepInParentRect(true);
		setWidth100();
		setHeight100();
		setNumCols(1);
		setColWidths("*");
		setCellPadding(5);
		setAlign(Alignment.CENTER);
		setTop(50);
		
		esitoItem = buildEsitoItem(attr);		
		esitoItem.setColSpan(2);
		esitoItem.setAlign(Alignment.CENTER);
		
		setAlign(Alignment.CENTER);
		setTop(50);
		
		setFields(esitoItem);
				
	}
	
	private FormItem buildEsitoItem(Record attr) {	
		
		FormItem item = null;
		
		if("COMBO-BOX".equals(attr.getAttribute("tipo"))) {						
			item = buildComboBoxItem(attr);	
		} else if("CHECK".equals(attr.getAttribute("tipo"))) {
			item = buildCheckItem(attr);
		}else if("RADIO".equals(attr.getAttribute("tipo"))) {						
			item = buildRadioItem(attr);	
		} else {
			item = new TextItem(attr.getAttribute("nome"), attr.getAttribute("label"));
		}
		
		item.setRequired(true);
		item.setAttribute("obbligatorio", true);					
			
		if(attr.getAttribute("larghezza") != null && !"".equals(attr.getAttribute("larghezza"))) {
			item.setWidth(new Integer(attr.getAttribute("larghezza")));
		} else {
			item.setWidth("*");
		}																						
		
//		if(item.getTitle() == null || "".equals(item.getTitle())) {
			item.setShowTitle(false);
//		} else {
//			item.setShowTitle(true);
//		}
		
		return item;
		
	}
		
	private SelectItem buildComboBoxItem(Record attr) {		
		SelectItem item = new SelectItem(attr.getAttribute("nome"), attr.getAttribute("label"));		
		GWTRestDataSource loadComboDS = new GWTRestDataSource("LoadComboAttributoDinamicoDataSource", "key", FieldType.TEXT);
		loadComboDS.addParam("nomeCombo", attr.getAttribute("nome"));
		item.setOptionDataSource(loadComboDS);						
		item.setDisplayField("value");
		item.setValueField("key");					
		item.setColSpan(1);		
		return item;
	}
	
	private CheckboxItem buildCheckItem(Record attr) {
		CheckboxItem item = new CheckboxItem(attr.getAttribute("nome"), attr.getAttribute("label"));
		item.setColSpan(1);
//		item.setLabelAsTitle(true);
		item.setShowTitle(true);
		item.setTitleOrientation(TitleOrientation.RIGHT);
		item.setTitleVAlign(VerticalAlignment.BOTTOM);
		return item;
	}
		
	private RadioGroupItem buildRadioItem(Record attr) {		
		RadioGroupItem item = new RadioGroupItem(attr.getAttribute("nome"), attr.getAttribute("label"));							
		item.setValueMap(new StringSplitterClient(attr.getAttribute("valueMap"), "|*|").getTokens());  					
		item.setVertical(false);
		item.setColSpan(1);	
		item.setWrap(false);
		item.setStartRow(true);
		item.setEndRow(true);				
		return item;
	}
	
}	
