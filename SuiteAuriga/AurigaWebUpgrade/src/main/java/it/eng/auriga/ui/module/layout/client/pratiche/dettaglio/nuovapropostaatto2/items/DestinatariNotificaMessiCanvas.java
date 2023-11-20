/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.Map;

import com.google.gwt.regexp.shared.RegExp;
import com.smartgwt.client.data.DSCallback;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.DateDisplayFormat;
import com.smartgwt.client.types.FieldType;
import com.smartgwt.client.types.VerticalAlignment;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.FormItemIfFunction;
import com.smartgwt.client.widgets.form.fields.FormItem;
import com.smartgwt.client.widgets.form.fields.events.ChangedEvent;
import com.smartgwt.client.widgets.form.fields.events.ChangedHandler;
import com.smartgwt.client.widgets.form.fields.events.IconClickEvent;
import com.smartgwt.client.widgets.form.fields.events.IconClickHandler;
import com.smartgwt.client.widgets.form.validator.CustomValidator;
import com.smartgwt.client.widgets.form.validator.RequiredIfFunction;
import com.smartgwt.client.widgets.form.validator.RequiredIfValidator;

import it.eng.auriga.ui.module.layout.client.RegExpUtility;
import it.eng.auriga.ui.module.layout.client.i18n.I18NUtil;
import it.eng.utility.ui.module.core.client.callback.ServiceCallback;
import it.eng.utility.ui.module.core.client.datasource.GWTRestDataSource;
import it.eng.utility.ui.module.layout.client.Layout;
import it.eng.utility.ui.module.layout.client.common.ReplicableCanvas;
import it.eng.utility.ui.module.layout.client.common.ReplicableItem;
import it.eng.utility.ui.module.layout.client.common.items.DateItem;
import it.eng.utility.ui.module.layout.client.common.items.ImgButtonItem;
import it.eng.utility.ui.module.layout.client.common.items.SelectItem;
import it.eng.utility.ui.module.layout.client.common.items.TextItem;
import it.eng.utility.ui.module.layout.shared.util.FrontendUtil;

public class DestinatariNotificaMessiCanvas extends ReplicableCanvas {
	
	public static final String _POSTA = "POSTA";
	public static final String _MESSI = "MESSI";
	
	protected TextItem descrizioneItem;
	protected TextItem emailItem;
	protected TextItem indirizzoItem;
	protected TextItem altriDatiItem;
	protected TextItem numeroNotificaItem;
	protected DateItem dataNotificaItem;
	protected SelectItem mezzoNotificaItem;
	
	private ReplicableCanvasForm mDynamicForm;

	public DestinatariNotificaMessiCanvas(ReplicableItem item) {
		super(item);
	}

	@Override
	public void disegna() {
		
		mDynamicForm = new ReplicableCanvasForm();
		mDynamicForm.setWrapItemTitles(false);
		mDynamicForm.setNumCols(20);
		mDynamicForm.setColWidths(1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, "*", "*");
		
		descrizioneItem = new TextItem("descrizione", ((DestinatariNotificaMessiItem) getItem()).getTitleDescrizioneDestinatariNotificaMessi());
		descrizioneItem.setWidth(200);
		descrizioneItem.setColSpan(1);
		descrizioneItem.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				if(((DestinatariNotificaMessiItem)getItem()).isRequiredDescrizioneDestinatariNotificaMessi()) {
					descrizioneItem.setAttribute("obbligatorio", true);
					descrizioneItem.setTitle(FrontendUtil.getRequiredFormItemTitle(((DestinatariNotificaMessiItem)getItem()).getTitleDescrizioneDestinatariNotificaMessi()));
				} else {					
					descrizioneItem.setAttribute("obbligatorio", false);
					descrizioneItem.setTitle(((DestinatariNotificaMessiItem)getItem()).getTitleDescrizioneDestinatariNotificaMessi());					
				}								
				return ((DestinatariNotificaMessiItem) getItem()).showDescrizioneDestinatariNotificaMessi();
			}
		});
		descrizioneItem.setValidators(new RequiredIfValidator(new RequiredIfFunction() {
			
			@Override
			public boolean execute(FormItem formItem, Object value) {
				return ((DestinatariNotificaMessiItem) getItem()).isRequiredDescrizioneDestinatariNotificaMessi();
			}
		}));	
		
		emailItem = new TextItem("email", ((DestinatariNotificaMessiItem) getItem()).getTitleEmailDestinatariNotificaMessi());
		emailItem.setWidth(200);
		emailItem.setColSpan(1);
		emailItem.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				if(((DestinatariNotificaMessiItem)getItem()).isRequiredEmailDestinatariNotificaMessi()) {
					emailItem.setAttribute("obbligatorio", true);
					emailItem.setTitle(FrontendUtil.getRequiredFormItemTitle(((DestinatariNotificaMessiItem)getItem()).getTitleEmailDestinatariNotificaMessi()));
				} else {					
					emailItem.setAttribute("obbligatorio", false);
					emailItem.setTitle(((DestinatariNotificaMessiItem)getItem()).getTitleEmailDestinatariNotificaMessi());					
				}								
				return ((DestinatariNotificaMessiItem) getItem()).showEmailDestinatariNotificaMessi();
			}
		});
		emailItem.setValidators(new RequiredIfValidator(new RequiredIfFunction() {
			
			@Override
			public boolean execute(FormItem formItem, Object value) {
				return ((DestinatariNotificaMessiItem) getItem()).isRequiredEmailDestinatariNotificaMessi();
			}
		}), new CustomValidator() {			
			@Override
			protected boolean condition(Object value) {			
				if(((DestinatariNotificaMessiItem) getItem()).showEmailDestinatariNotificaMessi()) {
					if(value == null || "".equals((String) value)) return true;
					RegExp regExp = RegExp.compile(RegExpUtility.indirizzoEmailRegExp());
					return regExp.test((String) value);		
				}
				return true;
			}
		});
		
		indirizzoItem = new TextItem("indirizzo", ((DestinatariNotificaMessiItem) getItem()).getTitleIndirizzoDestinatariNotificaMessi());
		indirizzoItem.setWidth(280);
		indirizzoItem.setColSpan(1);
		indirizzoItem.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				if(((DestinatariNotificaMessiItem)getItem()).isRequiredIndirizzoDestinatariNotificaMessi()) {
					indirizzoItem.setAttribute("obbligatorio", true);
					indirizzoItem.setTitle(FrontendUtil.getRequiredFormItemTitle(((DestinatariNotificaMessiItem)getItem()).getTitleIndirizzoDestinatariNotificaMessi()));
				} else {					
					indirizzoItem.setAttribute("obbligatorio", false);
					indirizzoItem.setTitle(((DestinatariNotificaMessiItem)getItem()).getTitleIndirizzoDestinatariNotificaMessi());					
				}								
				return ((DestinatariNotificaMessiItem) getItem()).showIndirizzoDestinatariNotificaMessi();
			}
		});
		indirizzoItem.setValidators(new RequiredIfValidator(new RequiredIfFunction() {
			
			@Override
			public boolean execute(FormItem formItem, Object value) {
				return ((DestinatariNotificaMessiItem) getItem()).isRequiredIndirizzoDestinatariNotificaMessi();
			}
		}));	
		
		altriDatiItem = new TextItem("altriDati", ((DestinatariNotificaMessiItem) getItem()).getTitleAltriDatiDestinatariNotificaMessi());
		altriDatiItem.setWidth(280);
		altriDatiItem.setColSpan(1);
		altriDatiItem.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				if(((DestinatariNotificaMessiItem)getItem()).isRequiredAltriDatiDestinatariNotificaMessi()) {
					altriDatiItem.setAttribute("obbligatorio", true);
					altriDatiItem.setTitle(FrontendUtil.getRequiredFormItemTitle(((DestinatariNotificaMessiItem)getItem()).getTitleAltriDatiDestinatariNotificaMessi()));
				} else {					
					altriDatiItem.setAttribute("obbligatorio", false);
					altriDatiItem.setTitle(((DestinatariNotificaMessiItem)getItem()).getTitleAltriDatiDestinatariNotificaMessi());					
				}								
				return ((DestinatariNotificaMessiItem) getItem()).showAltriDatiDestinatariNotificaMessi();
			}
		});
		altriDatiItem.setValidators(new RequiredIfValidator(new RequiredIfFunction() {
			
			@Override
			public boolean execute(FormItem formItem, Object value) {
				return ((DestinatariNotificaMessiItem) getItem()).isRequiredAltriDatiDestinatariNotificaMessi();
			}
		}));	
		
		numeroNotificaItem = new TextItem("numeroNotifica", ((DestinatariNotificaMessiItem) getItem()).getTitleNumeroNotificaDestinatariNotificaMessi());
		numeroNotificaItem.setWidth(100);
		numeroNotificaItem.setColSpan(1);
		numeroNotificaItem.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				if(((DestinatariNotificaMessiItem)getItem()).isRequiredNumeroNotificaDestinatariNotificaMessi()) {
					numeroNotificaItem.setAttribute("obbligatorio", true);
					numeroNotificaItem.setTitle(FrontendUtil.getRequiredFormItemTitle(((DestinatariNotificaMessiItem)getItem()).getTitleNumeroNotificaDestinatariNotificaMessi()));
				} else {					
					numeroNotificaItem.setAttribute("obbligatorio", false);
					numeroNotificaItem.setTitle(((DestinatariNotificaMessiItem)getItem()).getTitleNumeroNotificaDestinatariNotificaMessi());					
				}								
				return ((DestinatariNotificaMessiItem) getItem()).showNumeroNotificaDestinatariNotificaMessi();
			}
		});
		numeroNotificaItem.setValidators(new RequiredIfValidator(new RequiredIfFunction() {
			
			@Override
			public boolean execute(FormItem formItem, Object value) {
				return ((DestinatariNotificaMessiItem) getItem()).isRequiredNumeroNotificaDestinatariNotificaMessi();
			}
		}));			
		
		dataNotificaItem = new DateItem("dataNotifica", ((DestinatariNotificaMessiItem) getItem()).getTitleDataNotificaDestinatariNotificaMessi());		
		dataNotificaItem.setDateFormatter(DateDisplayFormat.TOEUROPEANSHORTDATE);
		dataNotificaItem.setColSpan(1);
		dataNotificaItem.setStartRow(true);
		dataNotificaItem.setDefaultValue(((DestinatariNotificaMessiItem) getItem()).getDefaultValueDataNotificaDestinatariNotificaMessi());			
		if(((DestinatariNotificaMessiItem) getItem()).isRequiredDataNotificaDestinatariNotificaMessi()) {
			dataNotificaItem.setAttribute("obbligatorio", true);
		}
		dataNotificaItem.setValidators(new RequiredIfValidator(new RequiredIfFunction() {
			
			@Override
			public boolean execute(FormItem formItem, Object value) {
				return ((DestinatariNotificaMessiItem) getItem()).showDataNotificaDestinatariNotificaMessi() && ((DestinatariNotificaMessiItem) getItem()).isRequiredDataNotificaDestinatariNotificaMessi();
			}
		}));
		dataNotificaItem.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return ((DestinatariNotificaMessiItem) getItem()).showDataNotificaDestinatariNotificaMessi();
			}
		});
		
		mezzoNotificaItem = new SelectItem("mezzoNotifica");
		mezzoNotificaItem.setWidth(200);
		String titleMezzoNotifica = ((DestinatariNotificaMessiItem) getItem()).getTitleMezzoNotificaDestinatariNotificaMessi();
		if(titleMezzoNotifica != null) {
			mezzoNotificaItem.setTitle(titleMezzoNotifica);
		} else {			
			mezzoNotificaItem.setShowTitle(false);
		}
		Map<String, String> mezzoNotificaValueMap = ((DestinatariNotificaMessiItem) getItem()).getValueMapMezzoNotificaDestinatariNotificaMessi();
		if(mezzoNotificaValueMap != null && mezzoNotificaValueMap.keySet().size() > 0) {
			mezzoNotificaItem.setValueMap(mezzoNotificaValueMap);			
		} else {			
			mezzoNotificaItem.setValueMap(_POSTA, _MESSI);	
		}		
		mezzoNotificaItem.setDefaultValue(((DestinatariNotificaMessiItem) getItem()).getDefaultValueMezzoNotificaDestinatariNotificaMessi());		
		if(((DestinatariNotificaMessiItem) getItem()).isRequiredMezzoNotificaDestinatariNotificaMessi()) {
			mezzoNotificaItem.setAttribute("obbligatorio", true);
		} else {
			mezzoNotificaItem.setAllowEmptyValue(true);			
		}
		mezzoNotificaItem.setValidators(new RequiredIfValidator(new RequiredIfFunction() {
			
			@Override
			public boolean execute(FormItem formItem, Object value) {
				return ((DestinatariNotificaMessiItem) getItem()).isRequiredMezzoNotificaDestinatariNotificaMessi();
			}
		}));
		mezzoNotificaItem.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return ((DestinatariNotificaMessiItem) getItem()).showMezzoNotificaDestinatariNotificaMessi();
			}
		});
		
		ImgButtonItem generaModuloNotificaButton = new ImgButtonItem("generaModuloNotificaButton", "protocollazione/generaDaModello.png", "Genera modulo notifica");
		generaModuloNotificaButton.setAlwaysEnabled(false);
		generaModuloNotificaButton.setColSpan(1);
		generaModuloNotificaButton.setIconWidth(16);
		generaModuloNotificaButton.setIconHeight(16);
		generaModuloNotificaButton.setIconVAlign(VerticalAlignment.BOTTOM);
		generaModuloNotificaButton.setAlign(Alignment.LEFT);
		generaModuloNotificaButton.setWidth(16);
		generaModuloNotificaButton.setShowIfCondition(new FormItemIfFunction() {

			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return ((DestinatariNotificaMessiItem) getItem()).showNumeroNotificaDestinatariNotificaMessi() && (numeroNotificaItem.getCanEdit() != null && numeroNotificaItem.getCanEdit());
			}
		});
		generaModuloNotificaButton.addIconClickHandler(new IconClickHandler() {

			@Override
			public void onIconClick(IconClickEvent event) {
				Layout.showWaitPopup("Generazione modulo notifica in corso...");				
				final GWTRestDataSource lNuovaPropostaAtto2CompletaDataSource = new GWTRestDataSource("NuovaPropostaAtto2CompletaDataSource");
				lNuovaPropostaAtto2CompletaDataSource.addParam("idUdAtto", ((DestinatariNotificaMessiItem)getItem()).getIdUdAtto());
				lNuovaPropostaAtto2CompletaDataSource.executecustom("generaModuloNotifica", new Record(mDynamicForm.getValues()), new DSCallback() {
					@Override
					public void execute(DSResponse response, Object rawData, DSRequest request) {
						Layout.hideWaitPopup();
						if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
							final Record recordModuloNotifica = response.getData()[0];
							((DestinatariNotificaMessiItem)getItem()).afterGeneraModuloNotifica(recordModuloNotifica);							
						}				
					}		
				});
			}
		});
		
		mDynamicForm.setFields(descrizioneItem, emailItem, indirizzoItem, altriDatiItem, numeroNotificaItem, dataNotificaItem, mezzoNotificaItem, generaModuloNotificaButton);
					
		addChild(mDynamicForm);		
	}
	
	@Override
	public ReplicableCanvasForm[] getForm() {
		return new ReplicableCanvasForm[]{mDynamicForm};
	}
	
	@Override
	public void setCanEdit(Boolean canEdit) {
		super.setCanEdit(canEdit);
		descrizioneItem.setCanEdit(((DestinatariNotificaMessiItem)getItem()).isEditableDescrizioneDestinatariNotificaMessi() ? canEdit : false);
		emailItem.setCanEdit(((DestinatariNotificaMessiItem)getItem()).isEditableEmailDestinatariNotificaMessi() ? canEdit : false);
		indirizzoItem.setCanEdit(((DestinatariNotificaMessiItem)getItem()).isEditableIndirizzoDestinatariNotificaMessi() ? canEdit : false);
		altriDatiItem.setCanEdit(((DestinatariNotificaMessiItem)getItem()).isEditableAltriDatiDestinatariNotificaMessi() ? canEdit : false);
		numeroNotificaItem.setCanEdit(((DestinatariNotificaMessiItem)getItem()).isEditableNumeroNotificaDestinatariNotificaMessi() ? canEdit : false);	
		dataNotificaItem.setCanEdit(((DestinatariNotificaMessiItem)getItem()).isEditableDataNotificaDestinatariNotificaMessi() ? canEdit : false);	
		mezzoNotificaItem.setCanEdit(((DestinatariNotificaMessiItem)getItem()).isEditableMezzoNotificaDestinatariNotificaMessi() ? canEdit : false);	
		mDynamicForm.markForRedraw();
	}
	
}
