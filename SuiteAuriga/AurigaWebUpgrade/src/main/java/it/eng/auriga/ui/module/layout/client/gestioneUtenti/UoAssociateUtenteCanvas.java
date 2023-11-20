/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import com.smartgwt.client.types.TitleOrientation;
import it.eng.auriga.ui.module.layout.client.i18n.I18NUtil;
import it.eng.utility.ui.module.core.client.datasource.GWTRestDataSource;
import it.eng.utility.ui.module.layout.client.Layout;
import it.eng.utility.ui.module.layout.client.common.ReplicableCanvas;
import it.eng.utility.ui.module.layout.client.common.items.DateItem;
import it.eng.utility.ui.module.layout.client.common.items.ImgButtonItem;
import it.eng.utility.ui.module.layout.client.common.items.ImgItem;
import it.eng.utility.ui.module.layout.client.common.items.TextItem;
import it.eng.utility.ui.module.layout.client.common.items.CheckboxItem;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.FormItemIfFunction;
import com.smartgwt.client.widgets.form.fields.FormItem;
import com.smartgwt.client.widgets.form.fields.HiddenItem;
import com.smartgwt.client.widgets.form.fields.SpacerItem;
import com.smartgwt.client.widgets.form.fields.events.IconClickEvent;
import com.smartgwt.client.widgets.form.fields.events.IconClickHandler;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.data.DSCallback;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.data.RecordList;

public class UoAssociateUtenteCanvas extends ReplicableCanvas{
	
	private ReplicableCanvasForm formMain;
	
	// TextItem
	private TextItem codRapidoItem;
	private TextItem denominazioneUoItem;
	private TextItem descTipoRelazioneItem;
	private TextItem descrizioneRuoloItem;
	
	// HiddenItem
	private HiddenItem tipoRelazioneItem;
	private HiddenItem idRuoloItem;
	private HiddenItem idUoItem;
	private HiddenItem descrizioneEstesaItem;
	private HiddenItem typeNodoItem;
	private HiddenItem denominazioneScrivaniaUtenteItem;
	private HiddenItem flgUoPuntoProtocolloItem;
	private HiddenItem listaUOPuntoProtocolloEscluseItem;
	private HiddenItem listaUOPuntoProtocolloIncluseItem;
	private HiddenItem listaUOPuntoProtocolloEreditarietaAbilitataItem;
	private HiddenItem flgGestAttiTuttiItem;
	private HiddenItem listaTipiGestAttiSelezionatiItem;
	private HiddenItem flgVisPropAttiInIterTuttiItem;
	private HiddenItem listaTipiVisPropAttiInIterSelezionatiItem;
	
	// DateItem
	private DateItem dtInizioVldItem;
	private DateItem dtFineVldItem;
	
	// CheckboxItem
	private CheckboxItem flgIncluseSottoUoItem;
	private CheckboxItem flgIncluseScrivanieItem;
	
	// ImgButtonItem
	private ImgButtonItem modificaButton;
	private ImgButtonItem uoCollegatePuntoProtocolloButton;
	private ImgItem uoPuntoProtocolloIcon;
	
	private SpacerItem spacerUOPuntoProtocolloItem;
	
	// UO che ha in carico i documenti/fascicoli
	private HiddenItem flgPresentiDocFascItem;
	private HiddenItem flgTipoDestDocItem;
	private HiddenItem idUODestDocfascItem;
	private HiddenItem livelliUODestDocFascItem;
	private HiddenItem desUODestDocFascItem;
	
	// Situazione dei documenti/fascicoli assegnati alla UO
	private HiddenItem statoSpostamentoDocFascItem;
	private HiddenItem dataInizioSpostamentoDocFascItem;	
	private HiddenItem dataFineSpostamentoDocFascItem;
	private HiddenItem nrDocAssegnatiItem;
	private HiddenItem dataConteggioDocAssegnatiItem; 
	private HiddenItem nrFascAssegnatiItem;
	private HiddenItem dataConteggioFascAssegnatiItem;
	
	@Override
	public void disegna() {
		formMain = new ReplicableCanvasForm();
		formMain.setWrapItemTitles(false);		
		
		buildItems();
		
		formMain.setFields(codRapidoItem,
				               denominazioneUoItem,
				               uoPuntoProtocolloIcon,
				               spacerUOPuntoProtocolloItem,
				               descTipoRelazioneItem,
				               dtInizioVldItem,
				               dtFineVldItem,				               
				               descrizioneRuoloItem,
				               flgIncluseSottoUoItem,
				               flgIncluseScrivanieItem,
				               modificaButton,
				               uoCollegatePuntoProtocolloButton,
				               tipoRelazioneItem,
				               idRuoloItem,
				               denominazioneScrivaniaUtenteItem,				             
				               idUoItem,
				               descrizioneEstesaItem,
				               typeNodoItem,
				               flgUoPuntoProtocolloItem,				               
				               listaUOPuntoProtocolloIncluseItem,
				               listaUOPuntoProtocolloEscluseItem,
				               listaUOPuntoProtocolloEreditarietaAbilitataItem,
				               flgGestAttiTuttiItem,
				               listaTipiGestAttiSelezionatiItem,
				               flgVisPropAttiInIterTuttiItem,
				               listaTipiVisPropAttiInIterSelezionatiItem,
				               flgPresentiDocFascItem,
				               flgTipoDestDocItem,				               
				               idUODestDocfascItem,
                               livelliUODestDocFascItem,
                               desUODestDocFascItem,		
                               statoSpostamentoDocFascItem,
                               dataInizioSpostamentoDocFascItem,
                               dataFineSpostamentoDocFascItem,
                               nrDocAssegnatiItem,
                               dataConteggioDocAssegnatiItem,
                               nrFascAssegnatiItem,
                               dataConteggioFascAssegnatiItem				              
		);								   
		formMain.setNumCols(12);		
		addChild(formMain);
	}
	
	private void buildItems() {		
		tipoRelazioneItem                               = new HiddenItem("tipoRelazione");
		idRuoloItem                                     = new HiddenItem("idRuolo");
		denominazioneScrivaniaUtenteItem                = new HiddenItem("denominazioneScrivaniaUtente");
		idUoItem                                        = new HiddenItem("idUo");
		descrizioneEstesaItem                           = new HiddenItem("descrizioneEstesa");
		typeNodoItem                                    = new HiddenItem("typeNodo");
		flgUoPuntoProtocolloItem                        = new HiddenItem("flgUoPuntoProtocollo");
		listaUOPuntoProtocolloIncluseItem               = new HiddenItem("listaUOPuntoProtocolloIncluse");
		listaUOPuntoProtocolloEscluseItem               = new HiddenItem("listaUOPuntoProtocolloEscluse");
		listaUOPuntoProtocolloEreditarietaAbilitataItem = new HiddenItem("listaUOPuntoProtocolloEreditarietaAbilitata");
		flgGestAttiTuttiItem 							= new HiddenItem("flgGestAttiTutti");
		listaTipiGestAttiSelezionatiItem 				= new HiddenItem("listaTipiGestAttiSelezionati");
		flgVisPropAttiInIterTuttiItem 					= new HiddenItem("flgVisPropAttiInIterTutti");
		listaTipiVisPropAttiInIterSelezionatiItem 		= new HiddenItem("listaTipiVisPropAttiInIterSelezionati");
		
		flgPresentiDocFascItem            = new HiddenItem("flgPresentiDocFasc");
		flgTipoDestDocItem                = new HiddenItem("flgTipoDestDoc");
		idUODestDocfascItem               = new HiddenItem("idUODestDocfasc");
		livelliUODestDocFascItem          = new HiddenItem("livelliUODestDocFasc");
		desUODestDocFascItem              = new HiddenItem("desUODestDocFasc");
		
		statoSpostamentoDocFascItem       = new HiddenItem("statoSpostamentoDocFasc");
		dataInizioSpostamentoDocFascItem  = new HiddenItem("dataInizioSpostamentoDocFasc");
		dataFineSpostamentoDocFascItem    = new HiddenItem("dataFineSpostamentoDocFasc");
		nrDocAssegnatiItem                = new HiddenItem("nrDocAssegnati");
		dataConteggioDocAssegnatiItem     = new HiddenItem("dataConteggioDocAssegnati");
		nrFascAssegnatiItem               = new HiddenItem("nrFascAssegnati");
		dataConteggioFascAssegnatiItem    = new HiddenItem("dataConteggioFascAssegnati");
		
		codRapidoItem                     = new TextItem("codRapido",               I18NUtil.getMessages().gestioneutenti_uoAssociateUtente_codRapidoItem_title());            codRapidoItem.setTitleOrientation(TitleOrientation.TOP); codRapidoItem.setWidth(120);
		denominazioneUoItem               = new TextItem("denominazioneUo",         I18NUtil.getMessages().gestioneutenti_uoAssociateUtente_denominazioneUoItem_title());      denominazioneUoItem.setTitleOrientation(TitleOrientation.TOP); denominazioneUoItem.setWidth(300);
		descTipoRelazioneItem             = new TextItem("descTipoRelazione",       I18NUtil.getMessages().gestioneutenti_uoAssociateUtente_descTipoRelazioneItem_title());    descTipoRelazioneItem.setTitleOrientation(TitleOrientation.TOP); descTipoRelazioneItem.setWidth(250);
		dtInizioVldItem                   = new DateItem("dtInizioVld",             I18NUtil.getMessages().gestioneutenti_uoAssociateUtente_dtInizioVldItem_title());          dtInizioVldItem.setTitleOrientation(TitleOrientation.TOP); dtInizioVldItem.setWidth(105);
		dtFineVldItem                     = new DateItem("dtFineVld",               I18NUtil.getMessages().gestioneutenti_uoAssociateUtente_dtFineVldItem_title());            dtFineVldItem.setTitleOrientation(TitleOrientation.TOP); dtFineVldItem.setWidth(105);
		descrizioneRuoloItem              = new TextItem("descrizioneRuolo",        I18NUtil.getMessages().gestioneutenti_uoAssociateUtente_ruoloItem_title());                descrizioneRuoloItem.setTitleOrientation(TitleOrientation.TOP); descrizioneRuoloItem.setWidth(150);
		flgIncluseSottoUoItem             = new CheckboxItem("flgIncluseSottoUo",   I18NUtil.getMessages().gestioneutenti_uoAssociateUtente_flgIncluseSottoUoItem_title());    flgIncluseSottoUoItem.setTitleOrientation(TitleOrientation.TOP); flgIncluseSottoUoItem.setWidth(130);
		flgIncluseScrivanieItem           = new CheckboxItem("flgIncluseScrivanie", I18NUtil.getMessages().gestioneutenti_uoAssociateUtente_flgIncluseScrivanieItem_title());  flgIncluseScrivanieItem.setTitleOrientation(TitleOrientation.TOP); flgIncluseScrivanieItem.setWidth(130);
		
		// Bottone MODIFICA
		modificaButton = new ImgButtonItem("modifica", "buttons/modify.png", "Modifica");
		modificaButton.setAlwaysEnabled(true);
		modificaButton.setColSpan(1);
		modificaButton.addIconClickHandler(new IconClickHandler() {

			@Override
			public void onIconClick(IconClickEvent event) {
				String title = "Modifica associazione";
				boolean isVersioneNestle = isVersioneNestle();
				Record record = new Record();
				record.setAttribute("dtInizioVld",                                 formMain.getValue("dtInizioVld"));
				record.setAttribute("dtFineVld",                                   formMain.getValue("dtFineVld"));
				record.setAttribute("idRuolo",                                     formMain.getValue("idRuolo"));
				record.setAttribute("descrizioneRuolo",                            formMain.getValue("descrizioneRuolo"));
				record.setAttribute("tipoRelazione",                               formMain.getValue("tipoRelazione"));
				record.setAttribute("descTipoRelazione",                           formMain.getValue("descTipoRelazione"));
				record.setAttribute("denominazioneScrivaniaUtente",                formMain.getValue("denominazioneScrivaniaUtente"));
				record.setAttribute("idUo",                                        formMain.getValue("idUo"));
				record.setAttribute("codRapido",                                   formMain.getValue("codRapido"));
				record.setAttribute("organigramma",                                formMain.getValue("idUo"));
				record.setAttribute("descrizione",                                 formMain.getValue("denominazioneUo"));
				record.setAttribute("descrizioneEstesa",                           formMain.getValue("descrizioneEstesa"));
				record.setAttribute("typeNodo",                                    formMain.getValue("typeNodo"));
				record.setAttribute("flgIncluseSottoUo",                           formMain.getValue("flgIncluseSottoUo"));
				record.setAttribute("flgIncluseScrivanie",                         formMain.getValue("flgIncluseScrivanie"));
				record.setAttribute("flgUoPuntoProtocollo",                        formMain.getValue("flgUoPuntoProtocollo"));
				record.setAttribute("listaUOPuntoProtocolloIncluse",               formMain.getValue("listaUOPuntoProtocolloIncluse"));
				record.setAttribute("listaUOPuntoProtocolloEscluse",               formMain.getValue("listaUOPuntoProtocolloEscluse"));
				record.setAttribute("listaUOPuntoProtocolloEreditarietaAbilitata", formMain.getValue("listaUOPuntoProtocolloEreditarietaAbilitata"));
				record.setAttribute("flgGestAttiTutti", 						   formMain.getValue("flgGestAttiTutti"));
				record.setAttribute("listaTipiGestAttiSelezionati", 			   formMain.getValue("listaTipiGestAttiSelezionati"));
				record.setAttribute("flgVisPropAttiInIterTutti", 				   formMain.getValue("flgVisPropAttiInIterTutti"));
				record.setAttribute("listaTipiVisPropAttiInIterSelezionati", 	   formMain.getValue("listaTipiVisPropAttiInIterSelezionati"));
				
				/** UO per spostare doc/fasc **/
				record.setAttribute("flgPresentiDocFasc",   formMain.getValue("flgPresentiDocFasc"));
				record.setAttribute("idUODestDocfasc",      formMain.getValue("idUODestDocfasc"));
				
				if(formMain.getValueAsString("flgTipoDestDoc") != null &&  formMain.getValueAsString("idUODestDocfasc") != null) {
					record.setAttribute("organigrammaSpostaDocFasc",  formMain.getValueAsString("flgTipoDestDoc") +  formMain.getValueAsString("idUODestDocfasc"));
				}
				record.setAttribute("desUODestDocFasc",     formMain.getValue("desUODestDocFasc"));
				record.setAttribute("livelliUODestDocFasc", formMain.getValue("livelliUODestDocFasc"));
				
				
				/** Situazione documentazione in competenza alla UO **/
				record.setAttribute("statoSpostamentoDocFasc",        formMain.getValue("statoSpostamentoDocFasc"));
				record.setAttribute("dataInizioSpostamentoDocFasc",   formMain.getValue("dataInizioSpostamentoDocFasc"));
				record.setAttribute("dataFineSpostamentoDocFasc",     formMain.getValue("dataFineSpostamentoDocFasc"));
				record.setAttribute("nrDocAssegnati",                 formMain.getValue("nrDocAssegnati"));
				record.setAttribute("dataConteggioDocAssegnati",      formMain.getValue("dataConteggioDocAssegnati"));
				record.setAttribute("nrFascAssegnati",                formMain.getValue("nrFascAssegnati"));
				record.setAttribute("dataConteggioFascAssegnati",     formMain.getValue("dataConteggioFascAssegnati"));
				
				String mode = ""; //((UoAssociateUtenteItem)getItem()).getMode();				
				AgganciaUtenteUOPopup agganciaUtenteUOPopup = new AgganciaUtenteUOPopup(record, title, isVersioneNestle, mode) {
					
					@Override
					public void onClickOkButton(Record formRecord, DSCallback callback) {
						Layout.showWaitPopup("Salvataggio in corso: potrebbe richiedere qualche secondo. Attendere...");				
						Layout.hideWaitPopup();
						setFormValuesFromRecordAfterMod(formRecord);
						markForRedraw();
						markForDestroy();
					}
				};
				agganciaUtenteUOPopup.show();
			}
		});
		modificaButton.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return isAbilToModUoAssociateUtente();
			}
		});
		
		// Icona UO punto protocollo
		uoPuntoProtocolloIcon = new ImgItem("uoPuntoProtocollo", "buttons/protocollazione.png", I18NUtil.getMessages().gestioneutenti_uoAssociateUtente_uoPuntoProtocolloIcon_title());
		uoPuntoProtocolloIcon.setShowIfCondition(new FormItemIfFunction() {		
			
     		@Override
     		public boolean execute(FormItem item, Object value, DynamicForm form) {
     						
     			return isUoPuntoProtocollo();
     		}
     	});	
		
		spacerUOPuntoProtocolloItem = new SpacerItem();
		spacerUOPuntoProtocolloItem.setWidth(16);
		spacerUOPuntoProtocolloItem.setShowIfCondition(new FormItemIfFunction() {
			
     		@Override
     		public boolean execute(FormItem item, Object value, DynamicForm form) {
     						
     			return !isUoPuntoProtocollo();
     		}
     	});	
		
		//Bottone per vedere Abilitazioni vs UO collegate al punto di protocollo 
		uoCollegatePuntoProtocolloButton = new ImgButtonItem("uoCollegatePuntoProtocollo", "buttons/uoCollegatePuntoProtocollo.png", I18NUtil.getMessages().gestioneutenti_uoAssociateUtente_uoCollegatePuntoProtocolloButton_title());
		uoCollegatePuntoProtocolloButton.setAlwaysEnabled(true);
		uoCollegatePuntoProtocolloButton.setColSpan(1);
		uoCollegatePuntoProtocolloButton.addIconClickHandler(new IconClickHandler() {

			@Override
			public void onIconClick(IconClickEvent event) {
				String idUOPP = formMain.getValueAsString("idUo");
				String listaUOPuntoProtocolloEscluse = formMain.getValueAsString("listaUOPuntoProtocolloEscluse");
				String listaUOPuntoProtocolloEreditarietaAbilitata = formMain.getValueAsString("listaUOPuntoProtocolloEreditarietaAbilitata");
				Record record = new Record();
				record.setAttribute("idUOPP", idUOPP);
				record.setAttribute("listaUOPuntoProtocolloEscluse", listaUOPuntoProtocolloEscluse);				
				record.setAttribute("listaUOPuntoProtocolloEreditarietaAbilitata", listaUOPuntoProtocolloEreditarietaAbilitata);
				GWTRestDataSource lGwtRestDataSource = new GWTRestDataSource("AurigaGestioneUtentiDataSource");
				lGwtRestDataSource.executecustom("leggiUOCollegatePuntoProtocollo", record, new DSCallback() {
					@Override
					public void execute(DSResponse response, Object rawData, DSRequest request) {
						if (response.getStatus() == DSResponse.STATUS_SUCCESS){
							if (response.getData() != null) {
								Record lRecordDb    = response.getData()[0];
								RecordList listaUOCollegatePuntoProtocollo  = lRecordDb.getAttributeAsRecordList("listaUOCollegatePuntoProtocollo");								
								Record recordNew = new Record();
								recordNew.setAttribute("listaUOCollegatePuntoProtocollo", listaUOCollegatePuntoProtocollo);								
								final String denominazioneUo = formMain.getValueAsString("denominazioneUo");
								final String codRapido = formMain.getValueAsString("codRapido");								
								String title = "Abilitazioni vs UO collegate al punto di protocollo " + codRapido + "-" + denominazioneUo;								
								String mode = ""; //((UoAssociateUtenteItem)getItem()).getMode();								
								UOCollegatePuntoProtocolloPopup uoCollegatePuntoProtocolloPopup = new UOCollegatePuntoProtocolloPopup(recordNew, title, mode) {
									@Override
									public void onClickOkButton(Record formRecord, DSCallback callback) {
										Layout.showWaitPopup("Salvataggio in corso: potrebbe richiedere qualche secondo. Attendere...");				
										Layout.hideWaitPopup();
										setFormValuesFromRecordAfterAbilUOPuntoProtocollo(formRecord);
										markForDestroy();
									}
								};
								uoCollegatePuntoProtocolloPopup.show();								
							}
						}
					}						
				});				
			}
		});
		uoCollegatePuntoProtocolloButton.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return isAbilToModUoCollegatePuntoProtocollo();
			}
		});
	}
	
	public boolean isVersioneNestle() {
		
		return false; //(((UoAssociateUtenteItem)getItem()).isVersioneNestle());
	}
	
	@Override
	public ReplicableCanvasForm[] getForm() {
		
		return new ReplicableCanvasForm[]{formMain};
	}
	
	public boolean isAbilToModUoCollegatePuntoProtocollo() {
		//return Layout.isPrivilegioAttivo("SIC/SO;M") && (((UoAssociateUtenteItem)getItem()).isEditMode() && isUoPuntoProtocollo() );
		return Layout.isPrivilegioAttivo("SIC/SO;M") && isUoPuntoProtocollo();
	}
	
	public boolean isAbilToModUoAssociateUtente() {
		return Layout.isPrivilegioAttivo("SIC/SO;M"); // && (((UoAssociateUtenteItem)getItem()).isEditMode() || ((UoAssociateUtenteItem)getItem()).isNewMode());
	}
	
	public boolean isAbilToDelUoAssociateUtente() {
		return Layout.isPrivilegioAttivo("SIC/SO;FC"); // && (((UoAssociateUtenteItem)getItem()).isEditMode() || ((UoAssociateUtenteItem)getItem()).isNewMode());
	}
	
	public void setFormValuesFromRecordAfterNew(Record record) {
		formMain.setValue("idRuolo", record.getAttribute("idRuolo"));
		formMain.setValue("descrizioneRuolo", record.getAttribute("descrizioneRuolo"));
		
		if(record.getAttributeAsDate("dataDal")!=null)
        	formMain.setValue("dtInizioVld", record.getAttributeAsDate("dataDal"));
		else
	    	formMain.setValue("dtInizioVld", "");
		
        if(record.getAttributeAsDate("dataAl")!=null)
        	formMain.setValue("dtFineVld", record.getAttributeAsDate("dataAl"));
        else
     	   formMain.setValue("dtFineVld", "");
        
        formMain.setValue("tipoRelazione", record.getAttribute("tipoRelUtenteUo"));
        formMain.setValue("descTipoRelazione", record.getAttribute("descTipoRelUtenteUo"));        
        formMain.setValue("denominazioneScrivaniaUtente",      record.getAttribute("denominazioneScrivaniaUtente"));
        formMain.setValue("codRapido", record.getAttribute("codRapido"));
        String organigramma = record.getAttribute("organigramma");
        String idUo = togliPrefissoUO(organigramma);
        formMain.setValue("idUo", idUo);
        formMain.setValue("denominazioneUo", record.getAttribute("descrizione"));
        formMain.setValue("descrizioneEstesa", record.getAttribute("descrizioneEstesa"));
        formMain.setValue("typeNodo", record.getAttribute("typeNodo"));
        formMain.setValue("flgIncluseSottoUo", record.getAttributeAsBoolean("flgInclSottoUo"));
        formMain.setValue("flgIncluseScrivanie", record.getAttributeAsBoolean("flgInclScrivVirt"));
        formMain.setValue("flgUoPuntoProtocollo", record.getAttribute("flgUoPuntoProtocollo"));
        formMain.setValue("listaUOPuntoProtocolloIncluse", record.getAttribute("listaUOPuntoProtocolloIncluse"));
		formMain.setValue("listaUOPuntoProtocolloEscluse", record.getAttribute("listaUOPuntoProtocolloEscluse"));
		formMain.setValue("listaUOPuntoProtocolloEreditarietaAbilitata", record.getAttribute("listaUOPuntoProtocolloEreditarietaAbilitata"));
		formMain.setValue("flgGestAttiTutti", record.getAttribute("flgGestAttiTutti"));
		formMain.setValue("listaTipiGestAttiSelezionati", record.getAttribute("listaTipiGestAttiSelezionati"));
		formMain.setValue("flgVisPropAttiInIterTutti", record.getAttribute("flgVisPropAttiInIterTutti"));
		formMain.setValue("listaTipiVisPropAttiInIterSelezionati", record.getAttribute("listaTipiVisPropAttiInIterSelezionati"));
		
		/** UO per spostare doc/fasc **/
		formMain.setValue("flgTipoDestDoc",       record.getAttribute("typeNodoSpostaDocFasc"));
		formMain.setValue("idUODestDocfasc",      record.getAttribute("idUoSpostaDocFasc"));
		formMain.setValue("livelliUODestDocFasc", record.getAttribute("codRapidoSpostaDocFasc"));
		formMain.setValue("desUODestDocFasc",     record.getAttribute("descrizioneSpostaDocFasc"));
	}
	
	public void setFormValuesFromRecordAfterMod(Record record) {
		formMain.setValue("idRuolo", record.getAttribute("idRuolo"));
		formMain.setValue("descrizioneRuolo", record.getAttribute("descrizioneRuolo"));

		if(record.getAttributeAsDate("dataDal")!=null)
        	formMain.setValue("dtInizioVld", record.getAttributeAsDate("dataDal"));
	    else
	    	formMain.setValue("dtInizioVld", "");
	    
        if(record.getAttributeAsDate("dataAl")!=null)
         	formMain.setValue("dtFineVld", record.getAttributeAsDate("dataAl"));
        else
    	   formMain.setValue("dtFineVld", "");
        
        formMain.setValue("tipoRelazione", record.getAttribute("tipoRelUtenteUo"));
        formMain.setValue("descTipoRelazione", record.getAttribute("descTipoRelUtenteUo"));
        formMain.setValue("denominazioneScrivaniaUtente",      record.getAttribute("denominazioneScrivaniaUtente"));
        String organigramma = record.getAttribute("organigramma");
        String idUo = togliPrefissoUO(organigramma);
        formMain.setValue("idUo", idUo);
        formMain.setValue("codRapido", record.getAttribute("codRapido"));
        formMain.setValue("denominazioneUo", record.getAttribute("descrizione"));
        formMain.setValue("descrizioneEstesa", record.getAttribute("descrizioneEstesa"));
        formMain.setValue("typeNodo", record.getAttribute("typeNodo"));
        formMain.setValue("flgIncluseSottoUo", record.getAttributeAsBoolean("flgInclSottoUo"));
        formMain.setValue("flgIncluseScrivanie", record.getAttributeAsBoolean("flgInclScrivVirt"));
        formMain.setValue("flgUoPuntoProtocollo", record.getAttribute("flgUoPuntoProtocollo"));
        formMain.setValue("listaUOPuntoProtocolloIncluse", record.getAttribute("listaUOPuntoProtocolloIncluse"));
		formMain.setValue("listaUOPuntoProtocolloEscluse", record.getAttribute("listaUOPuntoProtocolloEscluse"));
		formMain.setValue("listaUOPuntoProtocolloEreditarietaAbilitata", record.getAttribute("listaUOPuntoProtocolloEreditarietaAbilitata"));
		formMain.setValue("flgGestAttiTutti", record.getAttribute("flgGestAttiTutti"));
		formMain.setValue("listaTipiGestAttiSelezionati", record.getAttribute("listaTipiGestAttiSelezionati"));
		formMain.setValue("flgVisPropAttiInIterTutti", record.getAttribute("flgVisPropAttiInIterTutti"));
		formMain.setValue("listaTipiVisPropAttiInIterSelezionati", record.getAttribute("listaTipiVisPropAttiInIterSelezionati"));
				
		/** UO per spostare doc/fasc **/
		formMain.setValue("flgTipoDestDoc",       record.getAttribute("typeNodoSpostaDocFasc"));
		formMain.setValue("idUODestDocfasc",      record.getAttribute("idUoSpostaDocFasc"));
		formMain.setValue("livelliUODestDocFasc", record.getAttribute("codRapidoSpostaDocFasc"));
		formMain.setValue("desUODestDocFasc",     record.getAttribute("descrizioneSpostaDocFasc"));
	}
	
	public void setFormValuesFromRecordAfterAbilUOPuntoProtocollo(Record record) {
		formMain.setValue("listaUOPuntoProtocolloIncluse", record.getAttribute("listaUOPuntoProtocolloIncluse"));
		formMain.setValue("listaUOPuntoProtocolloEscluse", record.getAttribute("listaUOPuntoProtocolloEscluse"));
		formMain.setValue("listaUOPuntoProtocolloEreditarietaAbilitata", record.getAttribute("listaUOPuntoProtocolloEreditarietaAbilitata"));
	}
	
	@Override
	public void setCanEdit(Boolean canEdit) {
		
		super.setCanEdit(canEdit);
		codRapidoItem.setCanEdit(false);
		denominazioneUoItem.setCanEdit(false);
		descTipoRelazioneItem.setCanEdit(false);
		dtInizioVldItem.setCanEdit(false);
		dtFineVldItem.setCanEdit(false);
		descrizioneRuoloItem.setCanEdit(false);
		flgIncluseSottoUoItem.setCanEdit(false);
		flgIncluseScrivanieItem.setCanEdit(false);
	}

	public boolean isUoPuntoProtocollo() {
		 return (flgUoPuntoProtocolloItem.getValue() != null && !flgUoPuntoProtocolloItem.getValue().toString().equalsIgnoreCase("") &&  flgUoPuntoProtocolloItem.getValue().toString().equalsIgnoreCase("true"));
	}
	
    public String togliPrefissoUO(String stringIn){
    	String out = stringIn;
    	if(stringIn!=null && !stringIn.equalsIgnoreCase("") && stringIn.indexOf("UO") != -1 ){
    		out = stringIn.replaceAll("UO", "").trim();
    	}
    	return out;
    }
}