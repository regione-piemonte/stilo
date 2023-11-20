/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */
import java.util.LinkedHashMap;

import com.google.gwt.regexp.shared.RegExp;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.FormItemIfFunction;
import com.smartgwt.client.widgets.form.FormItemValueFormatter;
import com.smartgwt.client.widgets.form.fields.FormItem;
import com.smartgwt.client.widgets.form.fields.HiddenItem;
import com.smartgwt.client.widgets.form.fields.events.ChangedEvent;
import com.smartgwt.client.widgets.form.fields.events.ChangedHandler;
import com.smartgwt.client.widgets.form.validator.CustomValidator;
import com.smartgwt.client.widgets.layout.VLayout;

import it.eng.auriga.ui.module.layout.client.RegExpUtility;
import it.eng.auriga.ui.module.layout.client.i18n.I18NUtil;
import it.eng.utility.ui.module.layout.client.common.CustomDetail;
import it.eng.utility.ui.module.layout.client.common.items.CheckboxItem;
import it.eng.utility.ui.module.layout.client.common.items.ExtendedTextItem;
import it.eng.utility.ui.module.layout.client.common.items.ImgItem;
import it.eng.utility.ui.module.layout.client.common.items.SelectItem;
import it.eng.utility.ui.module.layout.client.common.items.TextItem;

public class RubricaEmailDetail extends CustomDetail { 
		
	private RubricaEmailDetail _instance;
	
	private DynamicForm mDynamicForm;
	
	private HiddenItem idVoceRubricaItem;
	private HiddenItem tipoIndirizzoItem;
	private HiddenItem idProvSoggRifItem;
	private HiddenItem flgValidoItem;
	private HiddenItem idUtenteInsItem;
	private HiddenItem dataInsItem;
	private HiddenItem idUtenteUltModItem;
	private HiddenItem dataUltModItem;		    
	
	private TextItem nomeItem;					    
	private ExtendedTextItem indirizzoEmailItem;              		
	private TextItem tipoSoggettoRifItem;
	private TextItem codAmministrazioneItem;
	private TextItem codAooItem;	
	
	private CheckboxItem flgPECverificataItem;
	private CheckboxItem flgPresenteInIPAItem;
	
	private ImgItem flgAnnullatoImgItem;
	
	private SelectItem 	tipoAccountItem;
	
	protected boolean editing;
	
	public RubricaEmailDetail(String nomeEntita) {		
		super(nomeEntita);
		
		_instance = this;	
				
        //******************************************************************************************
    	// Sezione DATI PRINCIPALI
        //******************************************************************************************
		
		// Creo il form
        mDynamicForm = new DynamicForm();
        mDynamicForm.setValuesManager(vm);
        mDynamicForm.setWidth("100%"); 
        mDynamicForm.setHeight(10);  
        mDynamicForm.setPadding(5);
        mDynamicForm.setNumCols(6);
        mDynamicForm.setWrapItemTitles(false);        
        
        // Creo gli item
        idVoceRubricaItem = new HiddenItem("idVoceRubrica");
	    idProvSoggRifItem = new HiddenItem("idProvSoggRif");
	    
	    tipoIndirizzoItem = new HiddenItem("tipoIndirizzo");
	    tipoIndirizzoItem.setValue("S");
	    
		flgValidoItem = new HiddenItem("flgValido");
		flgValidoItem.setDefaultValue(""); 	
		
		idUtenteInsItem = new HiddenItem("idUtenteIns");
		dataInsItem = new HiddenItem("dataIns");
		idUtenteUltModItem = new HiddenItem("idUtenteUltMod");
		dataUltModItem = new HiddenItem("dataUltMod");		
        
        // Nome
		nomeItem = new TextItem("nome", I18NUtil.getMessages().rubricaemail_detail_nomeItem_title());
        nomeItem.setRequired(true);
        nomeItem.setWidth(400);
        nomeItem.setStartRow(true);
        nomeItem.setEndRow(false);		
            
     	// icona annullato
     	flgAnnullatoImgItem = new ImgItem("flgAnnullatoImg", "ko.png", I18NUtil.getMessages().rubricaemail_flgValido_0_value());
     	flgAnnullatoImgItem.setRedrawOnChange(true);
     	flgAnnullatoImgItem.setStartRow(false);
     	flgAnnullatoImgItem.setEndRow(false);
     	flgAnnullatoImgItem.setShowIfCondition(new FormItemIfFunction() {			
     		@Override
     		public boolean execute(FormItem item, Object value, DynamicForm form) {
			
     			return (!flgValidoItem.getValue().toString().equalsIgnoreCase("") &&  flgValidoItem.getValue().toString().equalsIgnoreCase("false"));
     		}
     	});		
        
        // Indirizzo email
        indirizzoEmailItem = new ExtendedTextItem("indirizzoEmail", I18NUtil.getMessages().rubricaemail_detail_indirizzoEmailItem_title());
        indirizzoEmailItem.setRequired(false);
        indirizzoEmailItem.setWidth(400);
        indirizzoEmailItem.setStartRow(true);
        indirizzoEmailItem.setEndRow(false);
        indirizzoEmailItem.setValidators(new CustomValidator(){
			@Override
			protected boolean condition(Object value) {
				if (value == null || value.equals("")) return true; 
				RegExp regExp = RegExp.compile(RegExpUtility.indirizzoEmailRegExp());
				String lString = (String)value;
				String[] values = lString.split(";");
				boolean res = true;
				for (String val : values){
					if (val == null || val.equals("")) res = res && true;
					else res = res && regExp.test(val);
				}
				return res;
			}
		});
        indirizzoEmailItem.addChangedBlurHandler(new ChangedHandler() {
			@Override
			public void onChanged(ChangedEvent event) {
				indirizzoEmailItem.validate();				
			}
		});
        
		// Tipo account ( C=casella PEC, O=casella ordinaria, null) 
        tipoAccountItem = new SelectItem("tipoAccount", I18NUtil.getMessages().rubricaemail_detail_tipoAccountItem_title());
        tipoAccountItem.setStartRow(true);
        tipoAccountItem.setEndRow(false);       
        tipoAccountItem.setWidth(400);
        tipoAccountItem.setAllowEmptyValue(true);        
        LinkedHashMap<String, String> tipoAccountValueMap = new LinkedHashMap<String, String>();		
		tipoAccountValueMap.put("C", I18NUtil.getMessages().rubricaemail_tipoAccount_C_value()); 
		tipoAccountValueMap.put("O", I18NUtil.getMessages().rubricaemail_tipoAccount_O_value());
		tipoAccountItem.setValueMap(tipoAccountValueMap);  
		tipoAccountItem.setDefaultValue((String) null);	
         
        // Flag PEC verificata  ( 1 = si, 0/null = no)
        flgPECverificataItem = new CheckboxItem("flgPECverificata", I18NUtil.getMessages().rubricaemail_detail_flgPECverificataItem_title());
        flgPECverificataItem.setWidth("*");
        flgPECverificataItem.setStartRow(false);
        flgPECverificataItem.setEndRow(false);
		
        // Flag email presente in IPA ( 1=si, 0/null=no)
		flgPresenteInIPAItem = new CheckboxItem("flgPresenteInIPA", I18NUtil.getMessages().rubricaemail_detail_flgPresenteInIPAItem_title());
		flgPresenteInIPAItem.setWidth("*");
		flgPresenteInIPAItem.setStartRow(false);
		flgPresenteInIPAItem.setEndRow(false);
		
        // Tipo Soggetto di riferimento ( UO=uo/ufficio interno, UT=utente di sistema, C=casella gestita dal sistema, AOOI=aoo interna, AOOE=aoo esterna, A=altro soggetto)
		tipoSoggettoRifItem = new TextItem("tipoSoggettoRif", I18NUtil.getMessages().rubricaemail_detail_tipoSoggettoRifItem_title());
		tipoSoggettoRifItem.setRequired(false);
		tipoSoggettoRifItem.setWidth(400);
		tipoSoggettoRifItem.setStartRow(true);
		tipoSoggettoRifItem.setEndRow(false);
		tipoSoggettoRifItem.setEditorValueFormatter(new FormItemValueFormatter() {
		    public String formatValue(Object value, Record record, DynamicForm form, FormItem item) {
		    	String desctipo = value != null && !"".equals(String.valueOf(value)) ? new String(String.valueOf(value)) : null;
		    	if("UO".equals(desctipo)) {
					return I18NUtil.getMessages().rubricaemail_tipoSoggettoRif_UO_Alt_value();
				}	
				else if("UT".equals(desctipo)) {
					return I18NUtil.getMessages().rubricaemail_tipoSoggettoRif_UT_Alt_value();
				}
				else if("C".equals(desctipo)) {
					return I18NUtil.getMessages().rubricaemail_tipoSoggettoRif_C_Alt_value();
				}
				else if("AOOI".equals(desctipo)) {
					return I18NUtil.getMessages().rubricaemail_tipoSoggettoRif_AOOI_Alt_value();
				}
				else if("AOOE".equals(desctipo)) {
					return I18NUtil.getMessages().rubricaemail_tipoSoggettoRif_AOOE_Alt_value();
				}
				else if("A".equals(desctipo)) {
					return I18NUtil.getMessages().rubricaemail_tipoSoggettoRif_A_Alt_value();
				}
				else
					return null;
		    }
		});
        		        
		 // Codice Amministrazione
		codAmministrazioneItem = new TextItem("codAmministrazione", I18NUtil.getMessages().rubricaemail_detail_codAmministrazioneItem_title());
		codAmministrazioneItem.setRequired(false);
		codAmministrazioneItem.setWidth(200);
		codAmministrazioneItem.setStartRow(true);
		codAmministrazioneItem.setEndRow(false);
		
		// Codice AOO
		codAooItem = new TextItem("codAoo", I18NUtil.getMessages().rubricaemail_detail_codAooItem_title());
		codAooItem.setRequired(false);
		codAooItem.setWidth(200);
		codAooItem.setStartRow(true);
		codAooItem.setEndRow(false);     
		
		// Aggiungo gli item al form
		mDynamicForm.setItems(
				idVoceRubricaItem,
				idProvSoggRifItem,	
				tipoIndirizzoItem,
				idUtenteInsItem,
				dataInsItem,
				idUtenteUltModItem,
				dataUltModItem,
				nomeItem,
        		flgValidoItem, 
//        		flgValidoImgItem, 
        		flgAnnullatoImgItem,
        		indirizzoEmailItem,
        		tipoAccountItem,
        		flgPECverificataItem,
        		flgPresenteInIPAItem,
	        	tipoSoggettoRifItem,
	        	codAmministrazioneItem,
        		codAooItem
        );
        
		// Creo il VLAYOUT MAIN
		VLayout lVLayout = new VLayout();  
		lVLayout.setWidth100();
		lVLayout.setHeight(30);			
		
		lVLayout.addMember(mDynamicForm);	

		addMember(lVLayout);
		
		setCanEdit(true);
	}
	

	public void setCanEdit(boolean canEdit) {
		editing = canEdit;
		super.setCanEdit(canEdit);
		tipoSoggettoRifItem.setCanEdit(false);	
		flgPECverificataItem.setCanEdit(false);
		if(flgPECverificataItem.getValueAsBoolean() != null && flgPECverificataItem.getValueAsBoolean()) {
			tipoAccountItem.setCanEdit(false);
		}
		flgPresenteInIPAItem.setCanEdit(false);
		flgValidoItem.setCanEdit(false);
		codAmministrazioneItem.setCanEdit(false);
		codAooItem.setCanEdit(false);
	}		
}