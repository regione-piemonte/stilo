/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import com.google.gwt.regexp.shared.RegExp;
import com.smartgwt.client.data.DSCallback;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.data.RecordList;
import com.smartgwt.client.types.FieldType;
import com.smartgwt.client.util.StringUtil;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.FormItemHoverFormatter;
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
import com.smartgwt.client.widgets.form.validator.CustomValidator;
import com.smartgwt.client.widgets.form.validator.RequiredIfFunction;
import com.smartgwt.client.widgets.form.validator.RequiredIfValidator;

import it.eng.auriga.ui.module.layout.client.RegExpUtility;
import it.eng.auriga.ui.module.layout.client.anagrafiche.LookupRubricaEmailPopup;
import it.eng.auriga.ui.module.layout.client.i18n.I18NUtil;
import it.eng.auriga.ui.module.layout.client.postaElettronica.DettaglioEmailWindow;
import it.eng.utility.ui.module.core.client.datasource.GWTRestDataSource;
import it.eng.utility.ui.module.layout.client.common.ReplicableCanvas;
import it.eng.utility.ui.module.layout.client.common.items.CheckboxItem;
import it.eng.utility.ui.module.layout.client.common.items.ExtendedTextItem;
import it.eng.utility.ui.module.layout.client.common.items.ImgButtonItem;
import it.eng.utility.ui.module.layout.client.common.items.TextItem;

public class InvioUDDestinatariCanvas extends ReplicableCanvas {

	protected ReplicableCanvasForm mForm;
	protected CheckboxItem destPrimarioItem;
	protected CheckboxItem destCCItem;
	protected TextItem intestazioneItem;
	protected ExtendedTextItem indirizzoMailItem;
	protected ImgButtonItem cipaButton;
	protected ImgButtonItem pec;
	protected ImgButtonItem mailInviataButton;
	protected HiddenItem tipoDestinatarioItem;
	protected HiddenItem cipaPecItem;
	protected HiddenItem codiciIpaItem;
	protected HiddenItem idMailInviataItem;
	protected HiddenItem uriMailItem;
	protected HiddenItem dataOraMailItem;
	protected HiddenItem statoMailItem;
	protected HiddenItem idRubricaAurigaItem;
	protected HiddenItem idInRubricaEmailItem;
	
	protected String idMailingList;

	@Override
	public void disegna() {
		
		mForm = new ReplicableCanvasForm();
		
		tipoDestinatarioItem = new HiddenItem("tipoDestinatario");
		cipaPecItem          = new HiddenItem("cipaPec");
		codiciIpaItem        = new HiddenItem("codiciIpa");
		statoMailItem 	     = new HiddenItem("statoMail");
		idMailInviataItem    = new HiddenItem("idMailInviata");
		dataOraMailItem      = new HiddenItem("dataOraMail");
		uriMailItem          = new HiddenItem("uriMail");
		idRubricaAurigaItem  = new HiddenItem("idRubricaAuriga");
		idInRubricaEmailItem = new HiddenItem("idInRubricaEmail");
		
		 // Indirizzo Destinatario 
		destPrimarioItem = new CheckboxItem("destPrimario", I18NUtil.getMessages().invioudmail_detail_destPrimarioItem_title());
		destPrimarioItem.setWrapTitle(false);
		destPrimarioItem.setWidth("*");
		destPrimarioItem.setColSpan(1);
		destPrimarioItem.setEndRow(false);
		//destPrimarioItem.setDefaultValue(true);
		destPrimarioItem.addChangeHandler(new ChangeHandler() {
			
			@Override
			public void onChange(ChangeEvent event) {
				if (event.getValue()==Boolean.TRUE) {
					mForm.setValue("destPrimario", Boolean.TRUE);
					mForm.setValue("destCC", Boolean.FALSE);			
				} else {
					mForm.setValue("destPrimario", Boolean.FALSE);
					mForm.setValue("destCC", Boolean.FALSE);
				}
				mForm.clearFieldErrors("indirizzoMail", true);
				mForm.markForRedraw();
			}
		});
		
		// Indirizzo Destinatario CC
		destCCItem = new CheckboxItem("destCC", I18NUtil.getMessages().invioudmail_detail_destCCItem_title());
		destCCItem.setWrapTitle(false);
		destCCItem.setWidth("*");
		destCCItem.setColSpan(1);
		destCCItem.setEndRow(false);
		destCCItem.setStartRow(false);
		destCCItem.addChangeHandler(new ChangeHandler() {
			@Override
			public void onChange(ChangeEvent event) {
				if (event.getValue()==Boolean.TRUE) {
					mForm.setValue("destCC", Boolean.TRUE);				
					mForm.setValue("destPrimario", Boolean.FALSE);
				} else {
					mForm.setValue("destPrimario", Boolean.FALSE);
					mForm.setValue("destCC", Boolean.FALSE);
				}
				mForm.clearFieldErrors("indirizzoMail", true);
				mForm.markForRedraw();
			}
		});

		// Bottone lookup per aprire la RUBRICA EMAIL
		ImgButtonItem lookupRubricaEmailButton = new ImgButtonItem("lookupRubricaEmailButton", "lookup/rubricaemail.png", I18NUtil.getMessages().invioudmail_detail_lookupRubricaEmailItem_title());
		lookupRubricaEmailButton.setColSpan(1);
		lookupRubricaEmailButton.addIconClickHandler(new IconClickHandler() {			
			@Override
			public void onIconClick(IconClickEvent event) {				
				InvioUDDestinatariLookupRubricaEmailPopup lookupRubricaEmailPopup = new InvioUDDestinatariLookupRubricaEmailPopup();				
				lookupRubricaEmailPopup.show(); 
			}						
		}); 
		
        // Intestazione
		intestazioneItem = new TextItem("intestazione", I18NUtil.getMessages().invioudmail_detail_intestazioneItem_title());
		intestazioneItem.setWidth(300);
		intestazioneItem.setCanEdit(false);
		intestazioneItem.setEndRow(false);
		intestazioneItem.setStartRow(false);
		intestazioneItem.setItemHoverFormatter(true, new FormItemHoverFormatter() {			
			@Override
			public String getHoverHTML(FormItem item, DynamicForm form) {
				return StringUtil.asHTML(form.getValueAsString("intestazione"));
			}
		});
		
        // Bottone che indica se l'indirizzo è iscritto in IPA
		cipaButton = new ImgButtonItem("cipa", "ipa.png", null); 
		cipaButton.setWrap(true);
		cipaButton.setWidth(16);
		cipaButton.setColSpan(1);
		cipaButton.setShowIfCondition(new FormItemIfFunction() {
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return form.getValue("cipaPec")!=null && form.getValue("cipaPec").equals("CI_IPA");
			}
		});
		cipaButton.setItemHoverFormatter(new FormItemHoverFormatter() {
			@Override
			public String getHoverHTML(FormItem item, DynamicForm form) {
				return "Codici IPA: " + form.getValueAsString("codiciIpa");
			}
		});

        // Bottone che indica se l'indirizzo è PEC
		pec = new ImgButtonItem("pec", "anagrafiche/soggetti/flgEmailPecPeo/PEC.png", null);
		pec.setColSpan(1);
		pec.setShowIfCondition(new FormItemIfFunction() {
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return form.getValue("cipaPec")!=null && form.getValue("cipaPec").equals("PEC");
			}
		});		
		pec.setItemHoverFormatter(new FormItemHoverFormatter() {
			@Override
			public String getHoverHTML(FormItem item, DynamicForm form) {
				return  I18NUtil.getMessages().invioudmail_pec_Alt_value();
			}
		});
		
		final SpacerItem spacerItem = new SpacerItem(); 
		spacerItem.setColSpan(1);		
    	
		ImgButtonItem spacerFileItem = new ImgButtonItem("blank", "blank.png", "");
		spacerFileItem.setShowTitle(false);
		spacerFileItem.setAlwaysEnabled(true);
		spacerFileItem.setShowIfCondition(new FormItemIfFunction() {
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				boolean showSpacerFile = form.getValue("cipaPec")==null || form.getValue("cipaPec").equals("");
				spacerItem.setVisible(showSpacerFile);
				return showSpacerFile;
			}
		});
		
        // Indirizzo
		indirizzoMailItem = new ExtendedTextItem("indirizzoMail", I18NUtil.getMessages().invioudmail_detail_indirizzoMailItem_title());
		indirizzoMailItem.setWrapTitle(false);
		indirizzoMailItem.setWidth(265);
		indirizzoMailItem.setValidators(
			new RequiredIfValidator(new RequiredIfFunction() {			
				@Override
				public boolean execute(FormItem formItem, Object value) {
					boolean obbligatorio = (destPrimarioItem.getValue()==Boolean.TRUE || destCCItem.getValue()==Boolean.TRUE);
					return obbligatorio;
				}
			}), 
			new CustomValidator(){
				@Override
				protected boolean condition(Object value) {
					RegExp regExp = RegExp.compile(RegExpUtility.indirizzoEmailRegExp());
					return value != null && !"".equals(value.toString().trim()) ? regExp.test((String) value) : true;
				}
			}
		);	
		indirizzoMailItem.addChangedBlurHandler(new ChangedHandler() {
			@Override
			public void onChanged(ChangedEvent event) {
				indirizzoMailItem.validate();				
			}
		});
		
        // Bottone che indica lo stato della mail inviata all'indirizzo
		mailInviataButton = new ImgButtonItem("mailInviata", "mail/mail-reply.png", null);  
		mailInviataButton.setWidth(16);
		mailInviataButton.setHoverWidth(100);
		mailInviataButton.setColSpan(1);
		mailInviataButton.setShowIfCondition(new FormItemIfFunction() {
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				String statoMail = form.getValueAsString("statoMail");
				if(statoMail != null) {
					if (statoMail.equals("D")) statoMail = "Da inviare";
					else if (statoMail.equals("I")) statoMail = "Inviata";
					else if (statoMail.equals("C")) statoMail = "Consegnata";
					else if (statoMail.equals("E")) statoMail = "Errori in invio/consegna";
				}
				String dataOraMail = form.getValueAsString("dataOraMail");
				String dataMail = dataOraMail != null ? dataOraMail.substring(0, 10) : null;
				String oraMail = dataOraMail != null ? dataOraMail.substring(11, 16) : null;
				mailInviataButton.setPrompt("E-mail relativa alla registrazione già inviata al destinatario il " + dataMail + " alle " + oraMail +
											"<br>Stato di trasmissione/consegna dell'e-mail: " + statoMail);  
				return form.getValue("idMailInviata")!=null && !form.getValueAsString("idMailInviata").trim().equals("");
			}
		});		
		mailInviataButton.addIconClickHandler(new IconClickHandler() {
			@Override
			public void onIconClick(IconClickEvent event) {				
				DettaglioEmailWindow lDettaglioEmailWindow = new DettaglioEmailWindow((String) idMailInviataItem.getValue(), null);												
			}
		});
				
		mForm.setFields( 
			 destPrimarioItem, 
			 destCCItem, 
			 lookupRubricaEmailButton,
			 intestazioneItem,
			 cipaButton,
			 spacerFileItem,
			 pec, 
			 indirizzoMailItem, 
			 mailInviataButton, 						 
			 tipoDestinatarioItem,  
			 cipaPecItem, 
			 codiciIpaItem, 
			 statoMailItem, 
			 idMailInviataItem, 
			 dataOraMailItem,
			 uriMailItem, 						 
			 idRubricaAurigaItem, 
			 idInRubricaEmailItem						 
		);
			
		mForm.setNumCols(12);
		mForm.setColWidths(90, 10, 50, 10, 50, 80, 16, 16, 16, 40, 150, 16);		
		addChild(mForm);
	}
	
	@Override
	public void editRecord(Record record) {
		//Per ogni form setto il record trovato
		for (ReplicableCanvasForm lReplicableCanvasForm : getForm()){
			lReplicableCanvasForm.setValues(record.toMap());
			lReplicableCanvasForm.redraw();
		}
	}

	@Override
	public ReplicableCanvasForm[] getForm() {
		return new ReplicableCanvasForm[]{mForm};
	}

	public class InvioUDDestinatariLookupRubricaEmailPopup extends LookupRubricaEmailPopup {

		public InvioUDDestinatariLookupRubricaEmailPopup() {
			super(true);			
		}
		
		@Override
		public String getFinalita() {
			return "SEL_SOLO_SINGOLI";
		}
		
		@Override
		public void manageLookupBack(Record record) {
			if("G".equals(record.getAttributeAsString("tipoIndirizzo"))) {
				GWTRestDataSource datasource = new GWTRestDataSource("AnagraficaRubricaEmailDataSource", "idVoceRubrica", FieldType.TEXT); 
				datasource.performCustomOperation("trovaMembriGruppo", record, new DSCallback() {							
					@Override
					public void execute(DSResponse response, Object rawData, DSRequest request) {
						if(response.getStatus() == DSResponse.STATUS_SUCCESS) {						
							Record record = response.getData()[0];
							RecordList listaMembri = record.getAttributeAsRecordList("listaMembri");
							if(listaMembri != null && listaMembri.getLength() > 0) {
								for(int i = 0; i < listaMembri.getLength(); i++) {
									setFormValuesFromRecordRubrica(listaMembri.get(i));	
								}
							}
						} 				
					}
				}, new DSRequest());					
			} else {
				if (mForm.getField("intestazione").getCanEdit()){
					setFormValuesFromRecordRubrica(record);	
				} else mForm.setValue("indirizzoMail", record.getAttribute("indirizzoEmail"));						
			}
		}

		@Override
		public void manageMultiLookupBack(Record record) {

		}

		@Override
		public void manageMultiLookupUndo(Record record) {

		}

	}

	// Pulisce il record del ReplicateCanvas
	protected void  clearFormValuesRecordRubrica(Record lRecord){
		
	}

	
	public void setFormValuesFromRecordRubrica(Record record) {	
				
		mForm.clearErrors(true);								
		
		String flgPresenteInIPA = record.getAttribute("flgPresenteInIPA");
		if (flgPresenteInIPA==null) flgPresenteInIPA="";
		
		String tipoAccount = record.getAttribute("tipoAccount");
		if (tipoAccount==null) tipoAccount ="";
		
		if (!flgPresenteInIPA.equalsIgnoreCase("")){
			//Se è un indirizzo presente in IPA
			if (flgPresenteInIPA.equalsIgnoreCase("1")){
				mForm.setValue("cipaPec", "CI_IPA");
			}
			// Se non è presente in IPA verifico se è PEC o NORMALE
			else{
		    	  if (tipoAccount.equalsIgnoreCase("C"))
					  mForm.setValue("cipaPec", "PEC");
				  else
					  mForm.setValue("cipaPec", "");
			}
		}

		mForm.setValue("tipoDestinatario", record.getAttribute("tipoIndirizzo"));
		mForm.setValue("intestazione", record.getAttribute("nome"));
		mForm.setValue("indirizzoMail", record.getAttribute("indirizzoEmail"));
		mForm.setValue("codiciIpa", record.getAttribute("codiceIPA"));
		mForm.redraw();
	}
	
	public String getIdMailingList() {
		return idMailingList;
	}

	public void setIdMailingList(String idMailingList) {
		this.idMailingList = idMailingList;
	}
	
}
