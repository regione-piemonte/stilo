/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import com.google.gwt.user.client.Timer;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.FormItemIfFunction;
import com.smartgwt.client.widgets.form.fields.FormItem;
import com.smartgwt.client.widgets.form.fields.HiddenItem;
import com.smartgwt.client.widgets.form.fields.SpacerItem;
import com.smartgwt.client.widgets.form.fields.events.ChangeEvent;
import com.smartgwt.client.widgets.form.fields.events.ChangeHandler;
import com.smartgwt.client.widgets.form.fields.events.ChangedEvent;
import com.smartgwt.client.widgets.form.fields.events.ChangedHandler;
import com.smartgwt.client.widgets.form.fields.events.IconClickEvent;
import com.smartgwt.client.widgets.form.fields.events.IconClickHandler;
import com.smartgwt.client.widgets.form.validator.RequiredIfFunction;
import com.smartgwt.client.widgets.form.validator.RequiredIfValidator;

import it.eng.auriga.ui.module.layout.client.AurigaLayout;
import it.eng.auriga.ui.module.layout.client.anagrafiche.LookupSoggettiPopup;
import it.eng.auriga.ui.module.layout.client.i18n.I18NUtil;
import it.eng.utility.ui.module.core.client.callback.ServiceCallback;
import it.eng.utility.ui.module.core.client.datasource.GWTRestService;
import it.eng.utility.ui.module.layout.client.common.ReplicableCanvas;
import it.eng.utility.ui.module.layout.client.common.ReplicableItem;
import it.eng.utility.ui.module.layout.client.common.items.ExtendedTextItem;
import it.eng.utility.ui.module.layout.client.common.items.ImgButtonItem;
import it.eng.utility.ui.module.layout.client.common.items.TextAreaItem;
import it.eng.utility.ui.module.layout.client.common.items.TextItem;
import it.eng.utility.ui.module.layout.shared.util.FrontendUtil;

public class DestinatariAttoCanvas extends ReplicableCanvas {
	
	private TextItem prefissoItem;
	private ExtendedTextItem denominazioneItem;
	private HiddenItem idSoggRubricaItem;
	private TextAreaItem indirizzoItem;
	private TextAreaItem corteseAttenzioneItem;
	
	private ReplicableCanvasForm mDynamicForm;

	public DestinatariAttoCanvas(ReplicableItem item) {
		super(item);
	}

	@Override
	public void disegna() {
		
		mDynamicForm = new ReplicableCanvasForm();
		mDynamicForm.setWrapItemTitles(false);
		
		idSoggRubricaItem = new HiddenItem("idSoggRubrica");
		
		prefissoItem = new TextItem("prefisso", ((DestinatariAttoItem)getItem()).getTitlePrefisso());
		prefissoItem.setWidth(350);
		prefissoItem.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				if(((DestinatariAttoItem)getItem()).isRequiredPrefisso()) {
					prefissoItem.setAttribute("obbligatorio", true);
					prefissoItem.setTitle(FrontendUtil.getRequiredFormItemTitle(((DestinatariAttoItem)getItem()).getTitlePrefisso()));
				} else {					
					prefissoItem.setAttribute("obbligatorio", false);
					prefissoItem.setTitle(((DestinatariAttoItem)getItem()).getTitlePrefisso());					
				}
				return ((DestinatariAttoItem)getItem()).showPrefisso();
			}
		});
		prefissoItem.setValidators(new RequiredIfValidator(new RequiredIfFunction() {
			
			@Override
			public boolean execute(FormItem formItem, Object value) {
				return ((DestinatariAttoItem)getItem()).isRequiredPrefisso();
			}
		}));
		
		denominazioneItem = new ExtendedTextItem("denominazione", ((DestinatariAttoItem)getItem()).getTitleDenominazione());
		denominazioneItem.setWidth(350);
		denominazioneItem.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				if(((DestinatariAttoItem)getItem()).isRequiredDenominazione()) {
					denominazioneItem.setAttribute("obbligatorio", true);
					denominazioneItem.setTitle(FrontendUtil.getRequiredFormItemTitle(((DestinatariAttoItem)getItem()).getTitleDenominazione()));
				} else {					
					denominazioneItem.setAttribute("obbligatorio", false);
					denominazioneItem.setTitle(((DestinatariAttoItem)getItem()).getTitleDenominazione());					
				}
				return true;
			}
		});
		denominazioneItem.setValidators(new RequiredIfValidator(new RequiredIfFunction() {
			
			@Override
			public boolean execute(FormItem formItem, Object value) {
				return ((DestinatariAttoItem)getItem()).isRequiredDenominazione();
			}
		}));
		denominazioneItem.addChangedBlurHandler(new ChangedHandler() {

			@Override
			public void onChanged(ChangedEvent event) {
				mDynamicForm.clearValue("idSoggRubrica");
				if(denominazioneItem.getValue() != null && !"".equals(denominazioneItem.getValue())) {
					final Record lRecord = new Record();
					lRecord.setAttribute("denominazioneSoggetto", denominazioneItem.getValue());
					cercaInRubricaAfterChangedField(lRecord);
				}
			}
		});
		denominazioneItem.addChangeHandler(new ChangeHandler() {
			
			@Override
			public void onChange(ChangeEvent event) {
				((DestinatariAttoItem) getItem()).setCercaInRubricaAfterChanged(true);
			}
		});
		
		ImgButtonItem lookupRubricaButton = new ImgButtonItem("lookupRubricaButton", "lookup/rubrica.png", I18NUtil.getMessages()
				.protocollazione_detail_lookupRubricaButton_prompt());
		lookupRubricaButton.setColSpan(1);
		lookupRubricaButton.addIconClickHandler(new IconClickHandler() {

			@Override
			public void onIconClick(IconClickEvent event) {
				((DestinatariAttoItem) getItem()).setCercaInRubricaAfterChanged(true);
				DestinatariAttoLookupSoggettiPopup lookupRubricaPopup = new DestinatariAttoLookupSoggettiPopup(null);
				lookupRubricaPopup.show();
			}
		});
		
		SpacerItem endRowItem = new SpacerItem();
		endRowItem.setColSpan(1);
		endRowItem.setEndRow(true);
		endRowItem.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return ((DestinatariAttoItem)getItem()).showIndirizzo() || ((DestinatariAttoItem)getItem()).showCorteseAttenzione();
			}
		});
		
		indirizzoItem = new TextAreaItem("indirizzo", ((DestinatariAttoItem)getItem()).getTitleIndirizzo());		
		indirizzoItem.setHeight(40);
		indirizzoItem.setWidth(350);
		indirizzoItem.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				if(((DestinatariAttoItem)getItem()).isRequiredIndirizzo()) {
					indirizzoItem.setAttribute("obbligatorio", true);
					indirizzoItem.setTitle(FrontendUtil.getRequiredFormItemTitle(((DestinatariAttoItem)getItem()).getTitleIndirizzo()));
				} else {					
					indirizzoItem.setAttribute("obbligatorio", false);
					indirizzoItem.setTitle(((DestinatariAttoItem)getItem()).getTitleIndirizzo());					
				}
				return ((DestinatariAttoItem)getItem()).showIndirizzo();
			}
		});
		indirizzoItem.setValidators(new RequiredIfValidator(new RequiredIfFunction() {
			
			@Override
			public boolean execute(FormItem formItem, Object value) {
				return ((DestinatariAttoItem)getItem()).isRequiredIndirizzo();
			}
		}));
		
		corteseAttenzioneItem = new TextAreaItem("corteseAttenzione", ((DestinatariAttoItem)getItem()).getTitleCorteseAttenzione());
		corteseAttenzioneItem.setHeight(40);
		corteseAttenzioneItem.setWidth(350);
		corteseAttenzioneItem.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				if(((DestinatariAttoItem)getItem()).isRequiredCorteseAttenzione()) {
					corteseAttenzioneItem.setAttribute("obbligatorio", true);
					corteseAttenzioneItem.setTitle(FrontendUtil.getRequiredFormItemTitle(((DestinatariAttoItem)getItem()).getTitleCorteseAttenzione()));
				} else {					
					corteseAttenzioneItem.setAttribute("obbligatorio", false);
					corteseAttenzioneItem.setTitle(((DestinatariAttoItem)getItem()).getTitleCorteseAttenzione());					
				}
				return ((DestinatariAttoItem)getItem()).showCorteseAttenzione();
			}
		});
		corteseAttenzioneItem.setValidators(new RequiredIfValidator(new RequiredIfFunction() {
			
			@Override
			public boolean execute(FormItem formItem, Object value) {
				return ((DestinatariAttoItem)getItem()).isRequiredCorteseAttenzione();
			}
		}));
					
		mDynamicForm.setFields(prefissoItem, denominazioneItem, idSoggRubricaItem, lookupRubricaButton, endRowItem, indirizzoItem, corteseAttenzioneItem);	
		
		mDynamicForm.setNumCols(7);
		
		addChild(mDynamicForm);
		
	}
	
	protected void cercaInRubricaAfterChangedField(final Record record) {
		if(AurigaLayout.getParametroDBAsBoolean("ATTIVA_CERCA_IN_RUBRICA")) {			
			Timer t1 = new Timer() {
				public void run() {
					if(((DestinatariAttoItem) getItem()).isCercaInRubricaAfterChanged()) {
						cercaInRubrica(record, false);						
					}
				}
			};
			String delay = AurigaLayout.getParametroDB("CERCA_IN_RUBRICA_DELAY");
			t1.schedule((delay != null && !"".equals(delay)) ? Integer.parseInt(delay) : 1000);		
		}
	}
	
	protected void cercaInRubrica(final Record record, final boolean showLookupWithNoResults) {
		cercaSoggetto(record, new CercaSoggettoServiceCallback() {
			
			@Override
			public void executeOnError(boolean trovatiSoggMultipliInRicerca) {
				if(showLookupWithNoResults || trovatiSoggMultipliInRicerca) {
					DestinatariAttoLookupSoggettiPopup lookupRubricaPopup = new DestinatariAttoLookupSoggettiPopup(record);
					lookupRubricaPopup.show();
				}
			}
		});
	}
	
	protected void cercaSoggetto(Record lRecord, ServiceCallback<Record> callback) {
		GWTRestService<Record, Record> lGwtRestService = new GWTRestService<Record, Record>("FindSoggettoDatasource");
		lGwtRestService.addParam("finalita", getFinalitaLookup());	
		lGwtRestService.call(lRecord, callback);
	}
	
	@Override
	public ReplicableCanvasForm[] getForm() {
		return new ReplicableCanvasForm[]{mDynamicForm};
	}
	
	@Override
	public void setCanEdit(Boolean canEdit) {
		super.setCanEdit(canEdit);
		prefissoItem.setCanEdit(((DestinatariAttoItem)getItem()).isEditablePrefisso() ? canEdit : false);
		denominazioneItem.setCanEdit(((DestinatariAttoItem)getItem()).isEditableDenominazione() ? canEdit : false);
		indirizzoItem.setCanEdit(((DestinatariAttoItem)getItem()).isEditableIndirizzo() ? canEdit : false);
		corteseAttenzioneItem.setCanEdit(((DestinatariAttoItem)getItem()).isEditableCorteseAttenzione() ? canEdit : false);
	}
	
	public String calcolaTipoSoggetto(String tipo) {
		String tipoSoggetto = null;
		if ("UO;UOI".equals(tipo)) {
			tipoSoggetto = "UOI";
		} else if ("UP".equals(tipo)) {
			tipoSoggetto = "UP";
		} else if ("#APA".equals(tipo)) {
			tipoSoggetto = "PA";
		} else if ("#IAMM".equals(tipo)) {
			tipoSoggetto = "AOOI";
		} else if ("#AF".equals(tipo)) {
			tipoSoggetto = "PF";
		} else if ("#AG".equals(tipo)) {
			tipoSoggetto = "PG";
		}
		return tipoSoggetto;
	}
	
	protected boolean isPersonaGiuridica(String tipoSoggetto) {
		if (tipoSoggetto != null) {
			if ("G".equals(tipoSoggetto) || "PA".equals(tipoSoggetto) || "PG".equals(tipoSoggetto) || "UOI".equals(tipoSoggetto) || "AOOI".equals(tipoSoggetto)
					|| "AOOE".equals(tipoSoggetto))
				return true;
		}
		return false;
	}

	protected boolean isPersonaFisica(String tipoSoggetto) {
		if (tipoSoggetto != null) {
			if ("F".equals(tipoSoggetto) || "PF".equals(tipoSoggetto) || "UP".equals(tipoSoggetto))
				return true;
		}
		return false;
	}
	
	public void setFormValuesFromRecordRubrica(Record record) {
		// Pulisco i dati del soggetto
		mDynamicForm.clearValues();
		mDynamicForm.clearErrors(true);
		mDynamicForm.setValue("idSoggRubrica", record.getAttribute("idSoggetto"));
		String tipoDestinatario = calcolaTipoSoggetto(record.getAttribute("tipo"));
		if (tipoDestinatario != null) {
			if (isPersonaGiuridica(tipoDestinatario)) {
				mDynamicForm.setValue("denominazione", record.getAttribute("denominazione"));
			} else if (isPersonaFisica(tipoDestinatario)) {
				mDynamicForm.setValue("denominazione", record.getAttribute("cognome") + " " + record.getAttribute("nome"));			
			}
			mDynamicForm.markForRedraw();
		}
	}
	
	public String getFinalitaLookup() {
		return "#DEST_ATTO_TIPO_" + ((DestinatariAttoItem)getItem()).getIdDocTypeAtto();
	}
	
	public abstract class CercaSoggettoServiceCallback extends ServiceCallback<Record> {

		public abstract void executeOnError(boolean trovatiSoggMultipliInRicerca);

		@Override
		public void execute(Record object) {
			// Pulisco i dati del soggetto
			mDynamicForm.clearValues();
			mDynamicForm.clearErrors(true);
			mDynamicForm.setValue("idSoggRubrica", object.getAttribute("idSoggetto"));
			String tipoDestinatario = calcolaTipoSoggetto(object.getAttribute("tipologiaSoggetto"));
			if (tipoDestinatario != null) {
				
				if (isPersonaGiuridica(tipoDestinatario)) {
					mDynamicForm.setValue("denominazione", object.getAttribute("denominazioneSoggetto"));
				} else if (isPersonaFisica(tipoDestinatario)) {
					mDynamicForm.setValue("denominazione", object.getAttribute("cognomeSoggetto") + " " + object.getAttribute("nomeSoggetto"));			
				}
				mDynamicForm.setValue("indirizzo", object.getAttribute("indirizzoCompleto"));
				mDynamicForm.markForRedraw();
			} else {
				executeOnError(object.getAttribute("trovatiSoggMultipliInRicerca") != null && object.getAttributeAsBoolean("trovatiSoggMultipliInRicerca"));
			}
		}
	}
	
	public class DestinatariAttoLookupSoggettiPopup extends LookupSoggettiPopup {

		public DestinatariAttoLookupSoggettiPopup(Record record) {
			super(record, null, true);
		}
		
		@Override
		public String getFinalita() {	
			return getFinalitaLookup();
		}

		@Override
		public void manageLookupBack(Record record) {
			setFormValuesFromRecordRubrica(record);
		}

		@Override
		public void manageMultiLookupBack(Record record) {

		}

		@Override
		public void manageMultiLookupUndo(Record record) {

		}

		@Override
		public String[] getTipiAmmessi() {
			return null;
		}

	}
	
}