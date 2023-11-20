/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.HeaderControls;
import com.smartgwt.client.types.Overflow;
import com.smartgwt.client.widgets.Button;
import com.smartgwt.client.widgets.Window;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.FormItemIfFunction;
import com.smartgwt.client.widgets.form.ValuesManager;
import com.smartgwt.client.widgets.form.fields.ButtonItem;
import com.smartgwt.client.widgets.form.fields.FormItem;
import com.smartgwt.client.widgets.form.fields.events.ChangedEvent;
import com.smartgwt.client.widgets.form.fields.events.ChangedHandler;
import com.smartgwt.client.widgets.layout.HStack;
import com.smartgwt.client.widgets.layout.VLayout;

import it.eng.auriga.ui.module.layout.client.i18n.I18NUtil;
import it.eng.utility.ui.module.core.client.UserInterfaceFactory;
import it.eng.utility.ui.module.layout.client.common.DetailSection;
import it.eng.utility.ui.module.layout.client.common.items.CheckboxItem;
import it.eng.utility.ui.module.layout.client.common.items.SelectItem;

public abstract class NuovaProtComeCopiaPopup extends Window {
		
	protected NuovaProtComeCopiaPopup window;
	protected ValuesManager vm;
	protected DynamicForm form;
	protected DynamicForm flgNoForm;
	protected ProtocollazioneDetail protocollazioneDetail;

	protected SelectItem flgTipoProvToItem;
	protected CheckboxItem flgNoEsibenteItem;
	protected CheckboxItem flgNoMittentiItem;
	protected CheckboxItem flgNoDestinatariItem;
	protected CheckboxItem flgNoAltriAssegnatariItem;
	protected CheckboxItem flgNoOggettoItem;
	protected CheckboxItem flgNoPrimarioItem;
	protected CheckboxItem flgNoAllegatiItem;
	protected CheckboxItem flgNoFileAllegatiItem;
	protected CheckboxItem flgNoDocumentiCollegatiItem;
	protected CheckboxItem flgNoFascicolazioneItem;
	
	protected ButtonItem okButton;
	
	protected Map valuesOld;
	
	public NuovaProtComeCopiaPopup(final ProtocollazioneDetail protocollazioneDetail){
		
		window = this;
		vm = new ValuesManager();

		this.protocollazioneDetail = protocollazioneDetail;
		
		setTitle(I18NUtil.getMessages().protocollazione_detail_nuovaProtComeCopiaButton_prompt());
		setShowTitle(true);		
		setIsModal(true);
		setModalMaskOpacity(50);
		setAutoCenter(true);
		setWidth(550);
		setHeight(225);
		setKeepInParentRect(true);
		setShowModalMask(true);
		setShowMaximizeButton(true);
		setShowMinimizeButton(true);				
		
		form = new DynamicForm();			
		form.setValuesManager(vm);
		form.setWidth("100%");
		form.setHeight("5");
		form.setPadding(5);
		form.setWrapItemTitles(false);			
		
		flgTipoProvToItem = new SelectItem("flgTipoProvTo", "Tipo protocollo");
		flgTipoProvToItem.setWidth(250);		
		flgTipoProvToItem.setEndRow(true);				
		flgTipoProvToItem.setDefaultToFirstOption(true);
		flgTipoProvToItem.setColSpan(7);				
		if(protocollazioneDetail.getFlgTipoProv() != null) {
			if("E".equals(protocollazioneDetail.getFlgTipoProv())) {		
				LinkedHashMap<String, String> flgTipoProvToValueMap = new LinkedHashMap<String, String>();	
				flgTipoProvToValueMap.put("E", I18NUtil.getMessages().archivio_list_tipoProtocolloInEntrataAlt_value());
				flgTipoProvToValueMap.put("U", I18NUtil.getMessages().archivio_list_tipoProtocolloInUscitaAlt_value());							
				flgTipoProvToItem.setValueMap(flgTipoProvToValueMap);				
			} else if("I".equals(protocollazioneDetail.getFlgTipoProv())) {
				LinkedHashMap<String, String> flgTipoProvToValueMap = new LinkedHashMap<String, String>();	
				flgTipoProvToValueMap.put("I", I18NUtil.getMessages().archivio_list_tipoProtocolloInternoAlt_value());
				flgTipoProvToValueMap.put("U", I18NUtil.getMessages().archivio_list_tipoProtocolloInUscitaAlt_value());							
				flgTipoProvToItem.setValueMap(flgTipoProvToValueMap);		
			} else if("U".equals(protocollazioneDetail.getFlgTipoProv())) {
				LinkedHashMap<String, String> flgTipoProvToValueMap = new LinkedHashMap<String, String>();	
				flgTipoProvToValueMap.put("U", I18NUtil.getMessages().archivio_list_tipoProtocolloInUscitaAlt_value());							
				flgTipoProvToItem.setValueMap(flgTipoProvToValueMap);		
			} 
		}		  
		flgTipoProvToItem.setShowIfCondition(new FormItemIfFunction() {			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				
				return showFlgTipoProvTo();
			}
		});	
		flgTipoProvToItem.addChangedHandler(new ChangedHandler() {			
			@Override
			public void onChanged(ChangedEvent event) {
				String flgTipoProvTo = flgTipoProvToItem.getValueAsString();	
				if(flgTipoProvTo != null) {
					if("U".equals(flgTipoProvTo)) {
						
						vm.rememberValues();					
						valuesOld = vm.getValues();		
						
						flgNoEsibenteItem.setValue(true);
						
						flgNoMittentiItem.setCanEdit(false);
						flgNoMittentiItem.setValue(true);
						
						flgNoDestinatariItem.setCanEdit(false);	
						flgNoDestinatariItem.setValue(true);
						
						flgNoAltriAssegnatariItem.setValue(true);						
						flgNoOggettoItem.setValue(false);
						flgNoPrimarioItem.setValue(false);
						flgNoAllegatiItem.setValue(false);
						flgNoFileAllegatiItem.setValue(false);						
						flgNoFascicolazioneItem.setValue(true);		
						
					} else {
						
						if("E".equals(flgTipoProvTo)) {
							flgNoEsibenteItem.setValue(valuesOld.get("flgNoEsibente"));							
						} else {
							flgNoEsibenteItem.setValue(true);				
						}
						
						flgNoMittentiItem.setCanEdit(true);
						flgNoMittentiItem.setValue(valuesOld.get("flgNoMittenti"));
						
						flgNoDestinatariItem.setCanEdit(true);	
						flgNoDestinatariItem.setValue(valuesOld.get("flgNoDestinatari"));
						
						flgNoAltriAssegnatariItem.setValue(valuesOld.get("flgNoAltriAssegnatari"));
						flgNoOggettoItem.setValue(valuesOld.get("flgNoOggetto"));				
						flgNoPrimarioItem.setValue(false);
						flgNoAllegatiItem.setValue(valuesOld.get("flgNoAllegati"));
						flgNoFileAllegatiItem.setValue(valuesOld.get("flgNoFileAllegati"));
						flgNoFascicolazioneItem.setValue(valuesOld.get("flgNoFascicolazione"));		
						
					}
				}
				flgNoForm.markForRedraw();
			}
		});		
		
		form.setFields(flgTipoProvToItem);
		
		flgNoForm = new DynamicForm();			
		flgNoForm.setValuesManager(vm);
		flgNoForm.setWidth(475);
		flgNoForm.setHeight("5");
		flgNoForm.setPadding(5);
		flgNoForm.setWrapItemTitles(false);
		flgNoForm.setColWidths("1","1","1","1");
		flgNoForm.setNumCols(4);
		
		List<FormItem> flgNoItems = new ArrayList<FormItem>();
			
		flgNoEsibenteItem = new CheckboxItem("flgNoEsibente", "esibente");
		flgNoEsibenteItem.setColSpan(1);
		flgNoEsibenteItem.setWidth("*");
		flgNoEsibenteItem.setDefaultValue(true); // lo escludo sempre
		flgNoEsibenteItem.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return showFlgNoEsibente();
			}
		});
		flgNoItems.add(flgNoEsibenteItem);				
		
		flgNoMittentiItem = new CheckboxItem("flgNoMittenti", "mittente/i");
		flgNoMittentiItem.setColSpan(1);
		flgNoMittentiItem.setWidth("*");
		if(protocollazioneDetail.getFlgTipoProv() != null && "E".equals(protocollazioneDetail.getFlgTipoProv())) {	
			flgNoMittentiItem.setDefaultValue(true); // lo escludo per le prot. in entrata				
		}
		flgNoItems.add(flgNoMittentiItem);
		
		flgNoDestinatariItem = new CheckboxItem("flgNoDestinatari", "destinatari");
		flgNoDestinatariItem.setColSpan(1);
		flgNoDestinatariItem.setWidth("*");		
		if(protocollazioneDetail.getFlgTipoProv() != null && ("U".equals(protocollazioneDetail.getFlgTipoProv()) || "I".equals(protocollazioneDetail.getFlgTipoProv()))) {	
			flgNoDestinatariItem.setDefaultValue(true); // lo escludo per le prot. in uscita e interna				
		}
		flgNoItems.add(flgNoDestinatariItem);
		
		flgNoAltriAssegnatariItem = new CheckboxItem("flgNoAltriAssegnatari", "altri assegnatari");
		flgNoAltriAssegnatariItem.setColSpan(1);
		flgNoAltriAssegnatariItem.setWidth("*");
		if(protocollazioneDetail.getFlgTipoProv() != null && "I".equals(protocollazioneDetail.getFlgTipoProv())) {	
			flgNoAltriAssegnatariItem.setDefaultValue(true); // lo escludo per le prot. interne				
		}
		flgNoItems.add(flgNoAltriAssegnatariItem);
		
		flgNoOggettoItem = new CheckboxItem("flgNoOggetto", "oggetto");
		flgNoOggettoItem.setColSpan(1);
		flgNoOggettoItem.setWidth("*");
		flgNoItems.add(flgNoOggettoItem);
		
		flgNoPrimarioItem = new CheckboxItem("flgNoPrimario", "file primario");
		flgNoPrimarioItem.setColSpan(1);
		flgNoPrimarioItem.setWidth("*");
		flgNoPrimarioItem.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return showFlgNoPrimario();
			}
		});
		flgNoItems.add(flgNoPrimarioItem);
		
		flgNoAllegatiItem = new CheckboxItem("flgNoAllegati", "descrizione allegati");
		flgNoAllegatiItem.setColSpan(1);
		flgNoAllegatiItem.setWidth("*");
		flgNoAllegatiItem.addChangedHandler(new ChangedHandler() {
			@Override
			public void onChanged(ChangedEvent event) {
				boolean value = flgNoAllegatiItem.getValueAsBoolean();
				if(value) {
					flgNoFileAllegatiItem.setValue(true);
					flgNoFileAllegatiItem.setCanEdit(false);
				} else {
					flgNoFileAllegatiItem.setCanEdit(true);
				}
			}
		});
		flgNoItems.add(flgNoAllegatiItem);
		
		flgNoFileAllegatiItem = new CheckboxItem("flgNoFileAllegati", "file allegati");
		flgNoFileAllegatiItem.setColSpan(1);
		flgNoFileAllegatiItem.setWidth("*");
		flgNoFileAllegatiItem.setDefaultValue(true); // lo escludo sempre
		flgNoItems.add(flgNoFileAllegatiItem);
		
		flgNoDocumentiCollegatiItem = new CheckboxItem("flgNoDocumentiCollegati", "documenti collegati");
		flgNoDocumentiCollegatiItem.setColSpan(1);
		flgNoDocumentiCollegatiItem.setWidth("*");
		flgNoDocumentiCollegatiItem.setDefaultValue(true); // lo escludo sempre
		flgNoDocumentiCollegatiItem.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return showFlgNoDocumentiCollegati();
			}
		});
		flgNoItems.add(flgNoDocumentiCollegatiItem);	
		
		flgNoFascicolazioneItem = new CheckboxItem("flgNoFascicolazione", "fascicolazione");
		flgNoFascicolazioneItem.setColSpan(1);
		flgNoFascicolazioneItem.setWidth("*");
		flgNoItems.add(flgNoFascicolazioneItem);
		
		flgNoForm.setFields(flgNoItems.toArray(new FormItem[flgNoItems.size()]));

		DetailSection flgNoSection = new DetailSection("Mantieni i dati eccetto : ", false, true, false, flgNoForm);
				
		Button okButton = new Button(I18NUtil.getMessages().protocollazione_detail_okButton_title());   
		okButton.setIcon("ok.png");
		okButton.setIconSize(16); 
		okButton.setAutoFit(false);
		okButton.addClickHandler(new com.smartgwt.client.widgets.events.ClickHandler() {			
			@Override
			public void onClick(com.smartgwt.client.widgets.events.ClickEvent event) {
				onClickOkButton( 
						flgTipoProvToItem.getValueAsString(),
						showFlgNoEsibente() ? (flgNoEsibenteItem.getValueAsBoolean() != null && flgNoEsibenteItem.getValueAsBoolean()) : true,
						flgNoMittentiItem.getValueAsBoolean() != null && flgNoMittentiItem.getValueAsBoolean(),
						flgNoDestinatariItem.getValueAsBoolean() != null && flgNoDestinatariItem.getValueAsBoolean(),
						flgNoAltriAssegnatariItem.getValueAsBoolean() != null && flgNoAltriAssegnatariItem.getValueAsBoolean(),
						flgNoOggettoItem.getValueAsBoolean() != null && flgNoOggettoItem.getValueAsBoolean(),
						showFlgNoPrimario() ? flgNoPrimarioItem.getValueAsBoolean() != null && flgNoPrimarioItem.getValueAsBoolean() : false,
						flgNoAllegatiItem.getValueAsBoolean() != null && flgNoAllegatiItem.getValueAsBoolean(),
						flgNoFileAllegatiItem.getValueAsBoolean() != null && flgNoFileAllegatiItem.getValueAsBoolean(),
						showFlgNoDocumentiCollegati() ? (flgNoDocumentiCollegatiItem.getValueAsBoolean() != null && flgNoDocumentiCollegatiItem.getValueAsBoolean()) : true,
						flgNoFascicolazioneItem.getValueAsBoolean() != null && flgNoFascicolazioneItem.getValueAsBoolean()						
					);			
				window.markForDestroy();	
			}
		});
		
		HStack _buttons = new HStack(5);
		_buttons.setHeight(30);
		_buttons.setAlign(Alignment.CENTER);
		_buttons.setPadding(5);
        _buttons.addMember(okButton);
		  
		setAlign(Alignment.CENTER);
		setTop(50);
		
		VLayout layout = new VLayout();
		layout.setHeight100();
		layout.setWidth100();
		layout.setOverflow(Overflow.AUTO);
		
		// Creo il VLAYOUT e gli aggiungo il TABSET 
		VLayout portletLayout = new VLayout();  
		portletLayout.setHeight100();
		portletLayout.setWidth100();
		portletLayout.setOverflow(Overflow.VISIBLE);
		
		VLayout spacerLayout = new VLayout();
		spacerLayout.setHeight100();
		spacerLayout.setWidth100();

		layout.addMember(form);
		layout.addMember(flgNoSection);
		layout.addMember(spacerLayout);

		portletLayout.addMember(layout);		
		portletLayout.addMember(_buttons);
				
		addItem(portletLayout);
		
		setShowTitle(true);
		setHeaderControls(HeaderControls.HEADER_LABEL, HeaderControls.CLOSE_BUTTON);		
	}
	
	public boolean showFlgTipoProvTo() {
		return protocollazioneDetail.getFlgTipoProv() != null && ("E".equals(protocollazioneDetail.getFlgTipoProv()) || "I".equals(protocollazioneDetail.getFlgTipoProv())) && 
				!(protocollazioneDetail instanceof RegistroFattureDetail ||
				  protocollazioneDetail instanceof RepertorioDetailEntrata  ||
				  protocollazioneDetail instanceof RepertorioDetailInterno  ||
				  protocollazioneDetail instanceof RepertorioDetailUscita );
	}
	
	public boolean showFlgNoEsibente() {		
		return (showFlgTipoProvTo() && flgTipoProvToItem.getValueAsString() != null && "E".equals(flgTipoProvToItem.getValueAsString())) &&
			   (protocollazioneDetail.getFlgTipoProv() != null && "E".equals(protocollazioneDetail.getFlgTipoProv())) && 
			   (protocollazioneDetail.isModalitaWizard() || ProtocollazioneUtil.isAttivoEsibenteSenzaWizard());
	}
	
	public boolean showFlgNoPrimario() {
		return showFlgTipoProvTo() && flgTipoProvToItem.getValueAsString() != null && "U".equals(flgTipoProvToItem.getValueAsString());
	}
	
	public boolean showFlgNoDocumentiCollegati() {
		return protocollazioneDetail.isModalitaWizard();
	}
	
	public abstract void onClickOkButton(String flgTipoProvTo, boolean flgNoEsibente, boolean flgNoMittenti, boolean flgNoDestinatari, boolean flgNoAltriAssegnatari, boolean flgNoOggetto, boolean flgNoPrimario, boolean flgNoAllegati, boolean flgNoFileAllegati, boolean flgNoDocumentiCollegati, boolean flgNoFascicolazione);
	
	@Override
	protected void onDestroy() {
		if (vm != null) {
			try {
				vm.destroy();
			} catch (Exception e) {
			}
		}
		super.onDestroy();
	}
	
	@Override
	public void show() {

		super.show();
		if (UserInterfaceFactory.isAttivaAccessibilita()){
			focusInNextTabElement();
		}
	}
	
}
