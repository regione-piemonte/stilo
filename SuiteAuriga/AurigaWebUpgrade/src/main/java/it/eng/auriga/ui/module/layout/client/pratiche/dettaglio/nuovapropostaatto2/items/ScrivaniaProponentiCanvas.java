/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.ArrayList;
import java.util.List;

import com.smartgwt.client.data.DSCallback;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.data.RecordList;
import com.smartgwt.client.types.FieldType;
import com.smartgwt.client.widgets.events.FetchDataEvent;
import com.smartgwt.client.widgets.events.FetchDataHandler;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.FormItemIfFunction;
import com.smartgwt.client.widgets.form.fields.CheckboxItem;
import com.smartgwt.client.widgets.form.fields.FormItem;
import com.smartgwt.client.widgets.form.fields.HiddenItem;
import com.smartgwt.client.widgets.form.fields.events.ChangedEvent;
import com.smartgwt.client.widgets.form.validator.RequiredIfFunction;
import com.smartgwt.client.widgets.form.validator.RequiredIfValidator;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridField;

import it.eng.utility.ui.module.core.client.datasource.GWTRestDataSource;
import it.eng.utility.ui.module.core.client.datasource.SelectGWTRestDataSource;
import it.eng.utility.ui.module.layout.client.common.ReplicableCanvas;
import it.eng.utility.ui.module.layout.client.common.items.FilteredSelectItemWithDisplay;
import it.eng.utility.ui.module.layout.client.common.items.SelectItem;
import it.eng.utility.ui.module.layout.client.common.items.TextItem;
import it.eng.utility.ui.module.layout.shared.util.FrontendUtil;

public class ScrivaniaProponentiCanvas extends ReplicableCanvas {

	private ReplicableCanvasForm mDynamicForm;

	private SelectItem idScrivaniaItem;
	private HiddenItem idScrivaniaFromLoadDettHiddenItem;
	private HiddenItem codUoScrivaniaHiddenItem;
	private HiddenItem desScrivaniaHiddenItem;
	private TextItem tipoVistoScrivaniaItem;
	private CheckboxItem flgForzaDispFirmaScrivaniaItem;
	
	public ScrivaniaProponentiCanvas(ScrivaniaProponentiItem item) {
		super(item);
	}
	
	@Override
	public void disegna() {

		mDynamicForm = new ReplicableCanvasForm();
		mDynamicForm.setWrapItemTitles(false);
//		if(!((ScrivaniaProponentiItem)getItem()).getNotReplicable()) {
//			mDynamicForm.setBorder("1px solid #a7abb4");
//			mDynamicForm.setMargin(2);
//			mDynamicForm.setPadding(2);
//		}
				
		List<FormItem> items = new ArrayList<FormItem>();				
		
		SelectGWTRestDataSource idScrivaniaDS = new SelectGWTRestDataSource("LoadComboScrivanieOrganigrammaDataSource", "idSv", FieldType.TEXT, new String[]{"codUo", "descrizione"}, true);
		if(((ScrivaniaProponentiItem)getItem()).getAltriParamLoadComboIdScrivania() != null) {
			idScrivaniaDS.addParam("altriParamLoadCombo", ((ScrivaniaProponentiItem)getItem()).getAltriParamLoadComboIdScrivania());			
		}
		idScrivaniaItem = new FilteredSelectItemWithDisplay("idScrivania", idScrivaniaDS) {
			
			@Override
			protected ListGrid builPickListProperties() {
				ListGrid idScrivaniaPickListProperties = super.builPickListProperties();
				if(idScrivaniaPickListProperties == null) {
					idScrivaniaPickListProperties = new ListGrid();
				}
				if(!isCodUoFieldWithFilter() && !isDescrizioneFieldWithFilter()) {
					idScrivaniaPickListProperties.setShowFilterEditor(false); 
				}
				idScrivaniaPickListProperties.addFetchDataHandler(new FetchDataHandler() {

					@Override
					public void onFilterData(FetchDataEvent event) {
						String idUoProponente = getIdUoProponente();						
						GWTRestDataSource idScrivaniaDS = (GWTRestDataSource) idScrivaniaItem.getOptionDataSource();
						idScrivaniaDS.addParam("uoProponente", idUoProponente);
//						idScrivaniaDS.addParam("idSv", (String) idScrivaniaFromLoadDettHiddenItem.getValue());
						idScrivaniaItem.setOptionDataSource(idScrivaniaDS);
						idScrivaniaItem.invalidateDisplayValueCache();
					}
				});
				return idScrivaniaPickListProperties;				
			}
			
			@Override
			public void onOptionClick(Record record) {
				super.onOptionClick(record);				
				mDynamicForm.clearErrors(true);				
				mDynamicForm.setValue("codUoScrivania", record.getAttributeAsString("codUo"));
				mDynamicForm.setValue("desScrivania", record.getAttributeAsString("descrizione"));				
			}

			@Override
			protected void clearSelect() {
				super.clearSelect();
				mDynamicForm.clearErrors(true);				
				mDynamicForm.setValue("idScrivania", "");
				mDynamicForm.setValue("codUoScrivania", "");
				mDynamicForm.setValue("desScrivania", "");				
			};

			@Override
			public void setValue(String value) {
				super.setValue(value);
				if (value == null || "".equals(value)) {
					mDynamicForm.clearErrors(true);				
					mDynamicForm.setValue("idScrivania", "");
					mDynamicForm.setValue("codUoScrivania", "");
					mDynamicForm.setValue("desScrivania", "");					
				}
			}
		};
		idScrivaniaItem.setShowTitle(false);
//		idScrivaniaItem.setTitleOrientation(TitleOrientation.TOP);		
		idScrivaniaItem.setWidth(((ScrivaniaProponentiItem)getItem()).getSelectItemOrganigrammaWidth());
		idScrivaniaItem.setColSpan(4);
		idScrivaniaItem.setStartRow(true);		
		idScrivaniaItem.setValueField("idSv");		
		ListGridField descrizioneField = new ListGridField("descrizione", "Descrizione");
		descrizioneField.setWidth("*");
		descrizioneField.setCanFilter(isDescrizioneFieldWithFilter());
		ListGridField codUoField = new ListGridField("codUo", "Cod. U.O.");
		codUoField.setWidth(120);
		codUoField.setCanFilter(isCodUoFieldWithFilter());
		idScrivaniaItem.setPickListFields(codUoField, descrizioneField);
		idScrivaniaItem.setAllowEmptyValue(false);
		idScrivaniaItem.setAutoFetchData(false);
		idScrivaniaItem.setAlwaysFetchMissingValues(true);
		idScrivaniaItem.setFetchMissingValues(true);
		idScrivaniaItem.setValidators(new RequiredIfValidator(new RequiredIfFunction() {

			@Override
			public boolean execute(FormItem formItem, Object value) {
				Boolean notReplicable = getItem().getNotReplicable() != null ? getItem().getNotReplicable() : false;
				Boolean obbligatorio = getItem().getAttributeAsBoolean("obbligatorio") != null ? getItem().getAttributeAsBoolean("obbligatorio") : false;
				return !notReplicable || obbligatorio;
			}
		}));
		
		items.add(idScrivaniaItem);
				
		idScrivaniaFromLoadDettHiddenItem = new HiddenItem("idScrivaniaFromLoadDett");
		items.add(idScrivaniaFromLoadDettHiddenItem);
		
		codUoScrivaniaHiddenItem = new HiddenItem("codUoScrivania");
		items.add(codUoScrivaniaHiddenItem);
				
		desScrivaniaHiddenItem = new HiddenItem("desScrivania");
		items.add(desScrivaniaHiddenItem);
		
		tipoVistoScrivaniaItem = new TextItem("tipoVistoScrivania", "Tipo visto");
		tipoVistoScrivaniaItem.setWidth(220);
		tipoVistoScrivaniaItem.setColSpan(1);		
		if(((ScrivaniaProponentiItem)getItem()).isRequiredTipoVistoScrivania()) {
			tipoVistoScrivaniaItem.setAttribute("obbligatorio", true);
		}
		tipoVistoScrivaniaItem.setValidators(new RequiredIfValidator(new RequiredIfFunction() {
			
			@Override
			public boolean execute(FormItem formItem, Object value) {
				return ((ScrivaniaProponentiItem)getItem()).isRequiredTipoVistoScrivania();
			}
		}));
		tipoVistoScrivaniaItem.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return ((ScrivaniaProponentiItem)getItem()).showTipoVistoScrivania();
			}
		});
		items.add(tipoVistoScrivaniaItem);
		
		flgForzaDispFirmaScrivaniaItem = new CheckboxItem("flgForzaDispFirmaScrivania", "forza disponibilità firma");
		flgForzaDispFirmaScrivaniaItem.setDefaultValue(false);
		flgForzaDispFirmaScrivaniaItem.setColSpan(1);
		flgForzaDispFirmaScrivaniaItem.setWidth("*");
		flgForzaDispFirmaScrivaniaItem.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return ((ScrivaniaProponentiItem)getItem()).showFlgForzaDispFirmaScrivania();
			}
		});
		items.add(flgForzaDispFirmaScrivaniaItem);		
		
		mDynamicForm.setFields(items.toArray(new FormItem[items.size()]));
		
		mDynamicForm.setNumCols(20);
		mDynamicForm.setColWidths("1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "*", "*");

		addChild(mDynamicForm);

	}
	
	public String getIdUoProponente() {
		return (((ScrivaniaProponentiItem)getItem()).getIdUoProponenteIdScrivania());
	}
	
	public boolean isCodUoFieldWithFilter() {
		return isAltriParamWithNriLivelliUo(((ScrivaniaProponentiItem)getItem()).getAltriParamLoadComboIdScrivania());
	}
	
	public boolean isDescrizioneFieldWithFilter() {		
		return isAltriParamWithStrInDes(((ScrivaniaProponentiItem)getItem()).getAltriParamLoadComboIdScrivania());
	}
	
	public boolean isAltriParamWithNriLivelliUo(String altriParamLoadCombo) {
		return altriParamLoadCombo != null && altriParamLoadCombo.indexOf("NRI_LIVELLI_UO|*|") != -1;
	}
	
	public boolean isAltriParamWithStrInDes(String altriParamLoadCombo) {
		return altriParamLoadCombo != null && altriParamLoadCombo.indexOf("STR_IN_DES|*|") != -1;
	}
		
	public boolean isAltriParamWithIdUo(String altriParamLoadCombo) {
		return altriParamLoadCombo != null && altriParamLoadCombo.indexOf("ID_UO|*|") != -1;
	}

	@Override
	public void editRecord(Record record) {
		
		if(idScrivaniaItem != null) {
			manageLoadSelectInEditRecord(record, idScrivaniaItem, "idScrivania", new String[]{"codUoScrivania", "desScrivania"}, "idSv");
		}
		
		super.editRecord(record);
	}	
	
	public void manageChangedUoSelezionata() {
		if(idScrivaniaItem != null && isAltriParamWithIdUo(((ScrivaniaProponentiItem)getItem()).getAltriParamLoadComboIdScrivania())) {
			resetSelectItemAfterChangedIdUoProponente(idScrivaniaItem);
		}
	}
	
	public void resetSelectItemAfterChangedIdUoProponente(final SelectItem item) {
		if(item != null) {
			final String value = item.getValueAsString();
			item.fetchData(new DSCallback() {
	
				@Override
				public void execute(DSResponse response, Object rawData, DSRequest request) {
					RecordList data = response.getDataAsRecordList();
					boolean isObbligatorio = item.getAttributeAsBoolean("obbligatorio") != null && item.getAttributeAsBoolean("obbligatorio");
					if (isObbligatorio && data.getLength() == 1) {
						item.setValue(data.get(0).getAttribute("key"));						
						item.fireEvent(new ChangedEvent(item.getJsObj()));
					} else if(value != null && !"".equals(value)) {
						boolean trovato = false;
						if (data.getLength() > 0) {						
							for (int i = 0; i < data.getLength(); i++) {
								String key = data.get(i).getAttribute("key");
								if (value.equals(key)) {
									trovato = true;
									break;
								}
							}
						}
						if (!trovato) {
							item.setValue("");
							item.fireEvent(new ChangedEvent(item.getJsObj()));
						}
					}
				}
			});
		}
	}
	
	public Record getFormValuesAsRecord() {
		return mDynamicForm.getValuesAsRecord();
	}

	@Override
	public ReplicableCanvasForm[] getForm() {
		return new ReplicableCanvasForm[] { mDynamicForm };
	}

}