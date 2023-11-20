/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import com.smartgwt.client.types.FieldType;
import com.smartgwt.client.types.TitleOrientation;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.HiddenItem;
import com.smartgwt.client.widgets.form.fields.SpacerItem;
import com.smartgwt.client.widgets.layout.VLayout;
import it.eng.auriga.ui.module.layout.client.i18n.I18NUtil;
import it.eng.utility.ui.module.core.client.datasource.GWTRestDataSource;
import it.eng.utility.ui.module.layout.client.common.CustomDetail;
import it.eng.utility.ui.module.layout.client.common.items.CheckboxItem;
import it.eng.utility.ui.module.layout.client.common.items.SelectItem;
import it.eng.utility.ui.module.layout.client.common.items.TextItem;

public class RuoliAmministrativiDetail extends CustomDetail {
		
	protected RuoliAmministrativiDetail _instance;
	protected DynamicForm mainForm;
	protected HiddenItem idRuoloItem;
	protected HiddenItem recProtettoItem;
	protected HiddenItem ciProvRuoloItem;
	protected HiddenItem flgEspletaSoloAlleUOItem;
	protected TextItem descrizioneRuoloItem;
	protected SelectItem listaRuoliInclusiItem;
	
	public RuoliAmministrativiDetail(String nomeEntita) {		
		
		super(nomeEntita);

		_instance = this;
		
		mainForm = new DynamicForm();
		mainForm.setValuesManager(vm);
		mainForm.setWidth("100%");
		mainForm.setHeight("5");
		mainForm.setPadding(5);
		mainForm.setNumCols(5);
		mainForm.setColWidths(10, 10, 10, 10, "*");
		mainForm.setWrapItemTitles(false);		

		// Hidden
		idRuoloItem              = new HiddenItem("idRuolo");
		recProtettoItem          = new HiddenItem("recProtetto");
		ciProvRuoloItem          = new HiddenItem("ciProvRuolo");
		flgEspletaSoloAlleUOItem = new HiddenItem("flgEspletaSoloAlleUO");
		
		// Visibili
		descrizioneRuoloItem = new TextItem("descrizioneRuolo", I18NUtil.getMessages().ruoli_amministrativi_detail_descrizioneRuoloItem_title());
		descrizioneRuoloItem.setWidth(300);
		descrizioneRuoloItem.setColSpan(4);
		descrizioneRuoloItem.setStartRow(true);
		descrizioneRuoloItem.setAttribute("obbligatorio", true);

		// Lista dei ruoli inclusi
		GWTRestDataSource listaRuoliInclusiDS = new GWTRestDataSource("LoadComboRuoliAmministrativiDataSource", "key", FieldType.TEXT);		
		listaRuoliInclusiItem = new SelectItem("listaRuoliInclusi", I18NUtil.getMessages().ruoli_amministrativi_detail_listaRuoliInclusiItem_title());
		listaRuoliInclusiItem.setMultiple(true);
		listaRuoliInclusiItem.setOptionDataSource(listaRuoliInclusiDS);  
		listaRuoliInclusiItem.setAutoFetchData(true);
		listaRuoliInclusiItem.setDisplayField("value");
		listaRuoliInclusiItem.setValueField("key");			
		listaRuoliInclusiItem.setWrapTitle(false);
		listaRuoliInclusiItem.setCachePickListResults(false);
		listaRuoliInclusiItem.setStartRow(true);
		listaRuoliInclusiItem.setAllowEmptyValue(true);
		listaRuoliInclusiItem.setAlwaysFetchMissingValues(true);
		listaRuoliInclusiItem.setClearable(false);
		listaRuoliInclusiItem.setRedrawOnChange(true);
		listaRuoliInclusiItem.setWidth(600);   		
		listaRuoliInclusiItem.setColSpan(4);
		listaRuoliInclusiItem.setStartRow(true);
		
		mainForm.setItems(  // Hidden
				            idRuoloItem,
				            flgEspletaSoloAlleUOItem,
				            ciProvRuoloItem,
				            recProtettoItem,
				            // Visibili
				            descrizioneRuoloItem,				            
				            listaRuoliInclusiItem
				         );
		
		VLayout lVLayout = new VLayout();
		lVLayout.setWidth100();
		
		VLayout lVLayoutSpacer = new VLayout();
		lVLayoutSpacer.setWidth100();
		lVLayoutSpacer.setHeight100();
		
		lVLayout.addMember(mainForm);
		
		addMember(lVLayout);
		addMember(lVLayoutSpacer);
	}	
}