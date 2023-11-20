/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.utility.ui.module.layout.client.common.CustomDetail;
import it.eng.utility.ui.module.layout.client.common.items.DateItem;
import it.eng.utility.ui.module.layout.client.common.items.SelectItem;
import it.eng.utility.ui.module.layout.client.common.items.TextItem;
import java.util.LinkedHashMap;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.FormItemIfFunction;
import com.smartgwt.client.widgets.form.fields.FormItem;
import com.smartgwt.client.widgets.form.fields.HiddenItem;
import com.smartgwt.client.widgets.form.fields.events.ChangedEvent;
import com.smartgwt.client.widgets.form.fields.events.ChangedHandler;
import com.smartgwt.client.widgets.form.validator.RequiredIfFunction;
import com.smartgwt.client.widgets.form.validator.RequiredIfValidator;
import com.smartgwt.client.widgets.layout.VLayout;
import it.eng.auriga.ui.module.layout.client.i18n.I18NUtil;

public class ScadenzeConfigurateDetail extends CustomDetail{

	protected DynamicForm form;
	private SelectItem tipo;
	private HiddenItem idProcessType;
	private TextItem descrizioneScadenze;
	private TextItem durataPeriodo;
	private DateItem validoDal;
	private DateItem validoFinoAl;

	public ScadenzeConfigurateDetail(String nomeEntita, String idProcessTypeIO) {
		super(nomeEntita);

		form = new DynamicForm();		
		form.setValuesManager(vm);
		form.setWidth("100%");  
		form.setHeight("5");  
		form.setPadding(5);
		form.setWrapItemTitles(false);
		form.setNumCols(7);	
		
		idProcessType = new HiddenItem("idProcessType");
		idProcessType.setDefaultValue(idProcessTypeIO);
		
		descrizioneScadenze = new TextItem("descrizioneScadenze", I18NUtil.getMessages().scadenze_configurate_descrizione());
		descrizioneScadenze.setRequired(true);
		descrizioneScadenze.setWidth(250);
		descrizioneScadenze.setStartRow(true);
		descrizioneScadenze.setColSpan(4);
		
		tipo = new SelectItem("tipo", I18NUtil.getMessages().scadenze_configurate_Tipo());
		LinkedHashMap<String, String> tipoValueMap = new LinkedHashMap<String, String>();
		tipoValueMap.put("P", "Relativa a durata"); 
		tipoValueMap.put("S", "Scadenza puntuale");	
		tipo.setValueMap(tipoValueMap);
		tipo.setWidth("*");
		tipo.setRequired(true);
		tipo.setAllowEmptyValue(false);
		tipo.setStartRow(true);
		tipo.addChangedHandler(new ChangedHandler() {			
			@Override
			public void onChanged(ChangedEvent event) {
				form.markForRedraw();
			}
		});
		tipo.setColSpan(4);
		
		durataPeriodo = new TextItem("durataPeriodo", I18NUtil.getMessages().scadenze_configurate_Durata());
		durataPeriodo.setWidth(100);
		durataPeriodo.setStartRow(true);
		durataPeriodo.setAttribute("obbligatorio", true);
		durataPeriodo.setShowIfCondition(new FormItemIfFunction() {			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {						
				return "P".equals(tipo.getValueAsString());
			}
		});
		durataPeriodo.setValidators(new RequiredIfValidator(new RequiredIfFunction() {			
			@Override
			public boolean execute(FormItem formItem, Object value) {
				
				return "P".equals(tipo.getValueAsString());
			}
		}));
				
		validoDal = new DateItem("validoDal", I18NUtil.getMessages().scadenze_configurate_Valida_dal());
		validoDal.setStartRow(true);
		validoDal.setColSpan(1);
		
		validoFinoAl = new DateItem("validoFinoAl", I18NUtil.getMessages().scadenze_configurate_Valida_fino_al());
		validoFinoAl.setStartRow(false);
		validoFinoAl.setColSpan(1);
		
		form.setItems(
				idProcessType,
				tipo,
				descrizioneScadenze,
				durataPeriodo,
				validoDal,
				validoFinoAl
				);

		// LAYOUT MAIN
		VLayout lVLayout = new VLayout();  
		lVLayout.setWidth100();
		lVLayout.setHeight(50);			

		VLayout lVLayoutSpacer = new VLayout();  
		lVLayoutSpacer.setWidth100();
		lVLayoutSpacer.setHeight(50);		

		lVLayout.addMember(form);

		addMember(lVLayout);
		addMember(lVLayoutSpacer);

	}
}