/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.auriga.ui.module.layout.client.i18n.I18NUtil;
import it.eng.utility.ui.module.layout.client.common.CustomDetail;
import it.eng.utility.ui.module.layout.client.common.items.SelectItem;
import it.eng.utility.ui.module.layout.client.common.items.TextItem;

import java.util.LinkedHashMap;

import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.HiddenItem;
import com.smartgwt.client.widgets.form.fields.events.ChangedEvent;
import com.smartgwt.client.widgets.form.fields.events.ChangedHandler;
import com.smartgwt.client.widgets.layout.VLayout;

public class ContenutiAmmTraspDetail extends CustomDetail {

	// DynamicForm
	private DynamicForm formContenuto;
	
	// HiddenItem
	private HiddenItem idContenutoItem;
	
	// SelectItem
	private SelectItem tipoContenutoItem;
	
	// TextItem
	private TextItem titoloContenutoItem;
	private TextItem htmlContenutoItem;

	public ContenutiAmmTraspDetail(String nomeEntita) {

		super(nomeEntita);

		formContenuto = new DynamicForm();
		formContenuto.setValuesManager(vm);
		formContenuto.setWidth("100%");
		formContenuto.setHeight("5");
		formContenuto.setPadding(5);
		formContenuto.setNumCols(5);
		formContenuto.setColWidths(10, 10, 10, 10, "*");
		formContenuto.setWrapItemTitles(false);		

		// Hidden
		idContenutoItem = new HiddenItem("idContenuto");
		
		// Visibili
		tipoContenutoItem = new SelectItem("tipoContenuto", I18NUtil.getMessages().contenuti_amministrazione_trasparente_detail_tipoContenutoItem_title());
		LinkedHashMap<String, String> tipoContenutoValueMap = new LinkedHashMap<String, String>();
		tipoContenutoValueMap.put("titolo_sezione",      "Titolo sezione");
		tipoContenutoValueMap.put("fine_sezione",        "Fine sezione");
		tipoContenutoValueMap.put("export_opendata",     "Export open data");
		tipoContenutoValueMap.put("paragrafo",           "Paragrafo");
		tipoContenutoValueMap.put("file_semplice",       "File semplice");
		tipoContenutoValueMap.put("documento_complesso", "Documento complesso");
		tipoContenutoValueMap.put("tabella",             "Tabella");
		tipoContenutoItem.setValueMap(tipoContenutoValueMap);
		tipoContenutoItem.setColSpan(1);
		tipoContenutoItem.setAllowEmptyValue(true);
		tipoContenutoItem.addChangedHandler(new ChangedHandler() {

			@Override
			public void onChanged(ChangedEvent event) {
				markForRedraw();
			}
		});
		
		titoloContenutoItem = new TextItem("titoloContenuto", I18NUtil.getMessages().contenuti_amministrazione_trasparente_detail_titoloContenutoItem_title());
		titoloContenutoItem.setWidth(300);
		titoloContenutoItem.setColSpan(4);
		titoloContenutoItem.setStartRow(true);
		
		htmlContenutoItem = new TextItem("htmlContenuto", I18NUtil.getMessages().contenuti_amministrazione_trasparente_detail_htmlContenutoItem_title());
		htmlContenutoItem.setWidth(300);
		htmlContenutoItem.setColSpan(4);
		htmlContenutoItem.setStartRow(true);
		
		formContenuto.setFields(
				                // hidden
								idContenutoItem, 
				       
								// visibili
								tipoContenutoItem, 
								titoloContenutoItem,
								htmlContenutoItem
				       		  );

		VLayout lVLayout = new VLayout();
		lVLayout.setWidth100();
		
		VLayout lVLayoutSpacer = new VLayout();
		lVLayoutSpacer.setWidth100();
		lVLayoutSpacer.setHeight100();
		
		lVLayout.addMember(formContenuto);
		
		addMember(lVLayout);
		addMember(lVLayoutSpacer);

	}
}