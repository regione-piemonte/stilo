/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.google.gwt.core.client.Scheduler;
import com.google.gwt.core.client.Scheduler.ScheduledCommand;
import com.smartgwt.client.data.AdvancedCriteria;
import com.smartgwt.client.data.Criteria;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.data.RecordList;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.JSONDateFormat;
import com.smartgwt.client.types.Overflow;
import com.smartgwt.client.util.JSON;
import com.smartgwt.client.util.JSONEncoder;
import com.smartgwt.client.widgets.Button;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.form.fields.FormItemCriteriaFunction;
import com.smartgwt.client.widgets.form.fields.FormItemFunctionContext;
import com.smartgwt.client.widgets.form.fields.SelectItem;
import com.smartgwt.client.widgets.layout.HStack;
import com.smartgwt.client.widgets.layout.VLayout;

import it.eng.utility.ui.module.core.client.callback.ServiceCallback;
import it.eng.utility.ui.module.layout.client.common.ConfigurableFilter;
import it.eng.utility.ui.module.layout.client.portal.ModalWindow;
import it.eng.utility.ui.module.layout.shared.bean.FilterBean;
import it.eng.utility.ui.module.layout.shared.bean.FilterFieldBean;
import it.eng.utility.ui.module.layout.shared.bean.FilterSelectBean;
import it.eng.utility.ui.module.layout.shared.bean.FilterSelectLayoutBean;
import it.eng.utility.ui.module.layout.shared.bean.FilterType;
import it.eng.utility.ui.module.layout.shared.bean.ItemFilterBean;
import it.eng.utility.ui.module.layout.shared.bean.ItemFilterType;

public class FiltriCustomDelTipoWindow extends ModalWindow {
	
	private FiltriCustomDelTipoWindow _window;
	
	private ConfigurableFilter _filter;
	
	private RecordList attributiXRicerca;
	
	private static final JSONEncoder dateConstructorJsonEncoder = buildDateConstructorJsonEncoder();
	private static final JSONEncoder xmlSchemaJsonEncoder = buildXmlSchemaJsonEncoder();
	
	public static JSONEncoder buildDateConstructorJsonEncoder() {
		final JSONEncoder dateConstructorJsonEncoder = new JSONEncoder();
		dateConstructorJsonEncoder.setDateFormat(JSONDateFormat.DATE_CONSTRUCTOR);
		dateConstructorJsonEncoder.setPrettyPrint(false);
		return dateConstructorJsonEncoder;
	}
	
	public static JSONEncoder buildXmlSchemaJsonEncoder() {
		final JSONEncoder xmlSchemaJsonEncoder = new JSONEncoder();
		xmlSchemaJsonEncoder.setDateFormat(JSONDateFormat.XML_SCHEMA);
		xmlSchemaJsonEncoder.setPrettyPrint(false);
		return xmlSchemaJsonEncoder;
	}
	
	public FiltriCustomDelTipoWindow(String nomeEntita, final RecordList attributiXRicerca, String displayFiltriCustom, final ServiceCallback<Record> callback){
		
		super(nomeEntita, true);
		
		_window = this;
		
		this.attributiXRicerca = attributiXRicerca;
		
		setWidth(700);		
		setHeight(250);
		
		settingsMenu.removeItem(separatorMenuItem);
		settingsMenu.removeItem(autoSearchMenuItem);
		
		setTitle("Imposta filtri avanzati");
		
		_filter = new ConfigurableFilter(nomeEntita) {
			
			@Override
			protected SelectItem createSelectField() {
				final SelectItem lFieldsSelectItem = new SelectItem();			
				LinkedHashMap<String, String> lFieldsSelectItemValueMap = new LinkedHashMap<String, String>();
				if(attributiXRicerca != null) { 
					for(int i = 0; i < attributiXRicerca.getLength(); i++) {
						Record attr = attributiXRicerca.get(i);
						String label = attr.getAttribute("label");
						if (label != null && label.startsWith("<span")) {
							int start = label.indexOf(">") + 1;
							int end = label.indexOf("</span>", start);
							label = label.substring(start, end);					
						}
						lFieldsSelectItemValueMap.put(attr.getAttribute("nome"), label.trim());		
					}
				}
				lFieldsSelectItem.setValueMap(lFieldsSelectItemValueMap);
//				lFieldsSelectItem.setValueField("nome");
//				lFieldsSelectItem.setDisplayField("label");
				lFieldsSelectItem.setAddUnknownValues(false);
				lFieldsSelectItem.setValue((String) null);
				lFieldsSelectItem.setRequired(false);
				lFieldsSelectItem.setDefaultValue(false);
				lFieldsSelectItem.setDefaultToFirstOption(true);
				lFieldsSelectItem.setCachePickListResults(false);			
				lFieldsSelectItem.setWidth(280);
				return lFieldsSelectItem;
			}
				
			@Override
			protected FilterBean getFilterConfigBean() {
				FilterBean lFilterBean = new FilterBean();
				FilterSelectLayoutBean lSimpleLayoutBean = getSimpleLayoutBean();
				List<FilterFieldBean> listaFields = new ArrayList<FilterFieldBean>();
				if(attributiXRicerca != null) {
					for(int i = 0; i < attributiXRicerca.getLength(); i++) {
						Record attr = attributiXRicerca.get(i);
						String tipo = attr.getAttribute("tipo");
						String nome = attr.getAttribute("nome");
						String label = attr.getAttribute("label");
						String flgFullText = attr.getAttribute("flgFullText");				
						FilterFieldBean lFilterFieldBean = new FilterFieldBean();
						lFilterFieldBean.setName(nome);
						lFilterFieldBean.setTitle(label);
						if("DATE".equals(tipo)) {
							lFilterFieldBean.setType(FilterType.data_senza_ora);
						} else if("DATETIME".equals(tipo)) {				
							lFilterFieldBean.setType(FilterType.data_e_ora);
						} else if("TEXT-BOX".equals(tipo) || "TEXT-AREA".equals(tipo)) {
							if(flgFullText != null && "1".equals(flgFullText)) {
								lFilterFieldBean.setType(FilterType.stringa_full_text_mista);
							} else {
								lFilterFieldBean.setType(FilterType.stringa_ricerca_estesa_case_insensitive_1);
							}				
						} else if("CHECK".equals(tipo)) {
							lFilterFieldBean.setType(FilterType.lista_scelta);
							FilterSelectBean lFilterSelectBean = new FilterSelectBean();
							lFilterSelectBean.setLayout(lSimpleLayoutBean);
							LinkedHashMap<String, String> valueMap = new LinkedHashMap<String, String>();
							valueMap.put("1", "Si");
							valueMap.put("0", "No");
							lFilterSelectBean.setValueMap(valueMap);
							lFilterSelectBean.setCanFilter(false);
							lFilterSelectBean.setMultiple(false);
							lFilterFieldBean.setSelect(lFilterSelectBean);			
						} else if("NUMBER".equals(tipo) || "EURO".equals(tipo)) {
							lFilterFieldBean.setType(FilterType.numero);
						} else if("COMBO-BOX".equals(tipo) || "RADIO".equals(tipo)) {
							lFilterFieldBean.setType(FilterType.lista_scelta);
							FilterSelectBean lFilterSelectBean = new FilterSelectBean();
							lFilterSelectBean.setLayout(lSimpleLayoutBean);
							lFilterSelectBean.setDatasourceName("LoadComboAttributoDinamicoFilterDataSource");
							Map<String, String> params = new HashMap<String, String>();
							params.put("nomeCombo", attr.getAttribute("nome"));
							lFilterSelectBean.setDatasourceParams(params);
							lFilterSelectBean.setCanFilter(false);
							lFilterSelectBean.setMultiple(false);
							lFilterFieldBean.setSelect(lFilterSelectBean);				
						} 
						listaFields.add(lFilterFieldBean);
					}
				}
				lFilterBean.setFields(listaFields);
				return lFilterBean;
			}
			
			private FilterSelectLayoutBean getSimpleLayoutBean() {
				FilterSelectLayoutBean lSimpleLayoutBean = new FilterSelectLayoutBean();
				lSimpleLayoutBean.setFields(new ArrayList<ItemFilterBean>());
				ItemFilterBean lKeyItemFilterBean = new ItemFilterBean();
				lKeyItemFilterBean.setName("key");
				lKeyItemFilterBean.setTitle("Key");
				lKeyItemFilterBean.setType(ItemFilterType.TEXT);
				lKeyItemFilterBean.setValue(true);
				lSimpleLayoutBean.getFields().add(lKeyItemFilterBean);
				ItemFilterBean lValueItemFilterBean = new ItemFilterBean();
				lValueItemFilterBean.setName("value");
				lValueItemFilterBean.setTitle("Value");
				lValueItemFilterBean.setType(ItemFilterType.TEXT);
				lValueItemFilterBean.setValue(false);
				lSimpleLayoutBean.getFields().add(lValueItemFilterBean);
				return lSimpleLayoutBean;
			}
			
		};				
//		_filter.setAllowEmpty(true); // commento così viene sempre mostrata almeno una riga (anche se vuota)		
		
		Button okButton = new Button();
		okButton.setTitle("Ok");
		okButton.setIcon("ok.png");
		okButton.setIconHeight(16);
		okButton.setIconWidth(16);
		okButton.setWidth(100);
		okButton.setTop(20);
		okButton.setAlign(Alignment.CENTER);
		okButton.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				Scheduler.get().scheduleDeferred(new ScheduledCommand() {

					@Override
					public void execute() {
						
						String filtriCustom = getFiltriCustom();
						String displayFiltriCustom = getDisplayFiltriCustom();
						_window.markForDestroy();
						if(callback != null) {
							Record record = new Record();
							record.setAttribute("filtriCustom", filtriCustom);
							record.setAttribute("displayFiltriCustom", displayFiltriCustom);
							callback.execute(record);				
						}
					}
				});
			}
		});
		
		HStack buttons = new HStack(1);
		buttons.setHeight(30);
		buttons.setAlign(Alignment.CENTER);
		buttons.setPadding(5);
		buttons.addMember(okButton);	
		
		VLayout layout = new VLayout();
		layout.setHeight100();
		layout.setWidth100();
		layout.setOverflow(Overflow.AUTO);
		
		VLayout spacerLayout = new VLayout();
		spacerLayout.setHeight100();
		spacerLayout.setWidth100();

		layout.addMember(_filter);
		layout.addMember(spacerLayout);

		addItem(layout);	
		addItem(buttons);	
		
		if(displayFiltriCustom != null && !"".equals(displayFiltriCustom)) {
			_filter.setCriteria(new AdvancedCriteria(JSON.decode(displayFiltriCustom)));
		} else {
			_filter.setCriteria(new AdvancedCriteria());
		}
		
		setShowTitle(true);
		setHeaderIcon("buttons/filtri_custom.png");
		
	}
	
//	protected DataSourceField[] createFilterFields(RecordList attributiXRicerca) {
//		List<DataSourceField> listaFields = new ArrayList<DataSourceField>(); 
//		if(attributiXRicerca != null) {
//			for(int i = 0; i < attributiXRicerca.getLength(); i++) {
//				Record attr = attributiXRicerca.get(i);
//				String tipo = attr.getAttribute("tipo");
//				String nome = attr.getAttribute("nome");
//				String label = attr.getAttribute("label");
//				String flgFullText = attr.getAttribute("flgFullText");				
//				DataSourceField lDataSourceField = null;
//				if("DATE".equals(tipo)) {
//					lDataSourceField = new DataSenzaOra(nome, label);
//				} else if("DATETIME".equals(tipo)) {				
//					lDataSourceField = new DataEOra(nome, label);
//				} else if("TEXT-BOX".equals(tipo) || "TEXT-AREA".equals(tipo)) {
//					if(flgFullText != null && "1".equals(flgFullText)) {
//						lDataSourceField = new StringaFullTextMista(nome, label);	
//					} else {
//						lDataSourceField = new StringaRicercaEstesaCaseInsensitive1(nome, label);
//					}				
//				} else if("CHECK".equals(tipo)) {
////					lDataSourceField = new Check(nome, label);
//					lDataSourceField = new ListaScelta(nome, label);
//					it.eng.utility.ui.module.layout.client.common.items.SelectItem lSelectItem = new it.eng.utility.ui.module.layout.client.common.items.SelectItem(nome, label);
//					LinkedHashMap<String, String> valueMap = new LinkedHashMap<String, String>();
//					valueMap.put("1", "Si");
//					valueMap.put("0", "No");
//					lSelectItem.setValueMap(valueMap);					
//					lSelectItem.setWidth(120);
//					lSelectItem.setAllowEmptyValue(true);				
//					lDataSourceField.setEditorType(lSelectItem);		
//					lDataSourceField.setFilterEditorType(SelectItem.class);					
//				} else if("NUMBER".equals(tipo) || "EURO".equals(tipo)) {
//					lDataSourceField = new Numero(nome, label);	
//				} else if("COMBO-BOX".equals(tipo) || "RADIO".equals(tipo)) {
//					lDataSourceField = new ListaScelta(nome, label);
//					it.eng.utility.ui.module.layout.client.common.items.SelectItem lSelectItem = new it.eng.utility.ui.module.layout.client.common.items.SelectItem(nome, label);
//					GWTRestDataSource lLoadComboAttributoDinamicoDataSource = new GWTRestDataSource("LoadComboAttributoDinamicoDataSource", "key", FieldType.TEXT);
//					lLoadComboAttributoDinamicoDataSource.addParam("nomeCombo", attr.getAttribute("nome"));
//					lSelectItem.setOptionDataSource(lLoadComboAttributoDinamicoDataSource);					
//					lSelectItem.setDisplayField("value");
//					lSelectItem.setValueField("key");	
//					lSelectItem.setAllowEmptyValue(true);				
//					lDataSourceField.setEditorType(lSelectItem);
//					lDataSourceField.setFilterEditorType(SelectItem.class);						
//				}  
//				if(lDataSourceField != null) {
//					listaFields.add(lDataSourceField);
//				}
//			}
//		}
//		return listaFields.toArray(new DataSourceField[0]);
//	}
	
	private String getFiltriCustom() {
		return JSON.encode(_filter.getCriteria(true).getJsObj(), xmlSchemaJsonEncoder);
	}
	
	private String getDisplayFiltriCustom() {
		return JSON.encode(_filter.getCriteria(true).getJsObj(), dateConstructorJsonEncoder);
	}
	
	@Override
	protected void onDestroy() {		
		_filter.destroy();
		attributiXRicerca.destroy();
		super.onDestroy();
	}
	
}
