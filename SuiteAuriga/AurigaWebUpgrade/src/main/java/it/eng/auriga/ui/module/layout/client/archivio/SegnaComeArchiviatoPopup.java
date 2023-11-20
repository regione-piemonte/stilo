/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.ArrayList;
import java.util.List;

import com.smartgwt.client.data.DSCallback;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.data.RecordList;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.Overflow;
import com.smartgwt.client.widgets.Button;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.FormItemIfFunction;
import com.smartgwt.client.widgets.form.fields.FormItem;
import com.smartgwt.client.widgets.form.fields.RadioGroupItem;
import com.smartgwt.client.widgets.form.fields.events.ChangeEvent;
import com.smartgwt.client.widgets.form.fields.events.ChangeHandler;
import com.smartgwt.client.widgets.form.fields.events.ChangedEvent;
import com.smartgwt.client.widgets.form.fields.events.ChangedHandler;
import com.smartgwt.client.widgets.form.validator.RequiredIfFunction;
import com.smartgwt.client.widgets.form.validator.RequiredIfValidator;
import com.smartgwt.client.widgets.layout.HStack;
import com.smartgwt.client.widgets.layout.VLayout;

import it.eng.auriga.ui.module.layout.client.AurigaLayout;
import it.eng.utility.ui.module.layout.client.common.items.CheckboxItem;
import it.eng.utility.ui.module.layout.client.common.items.TextAreaItem;
import it.eng.utility.ui.module.layout.client.portal.ModalWindow;

public abstract class SegnaComeArchiviatoPopup extends ModalWindow {
	
	public static final String _SOLO_PER_ME = "Solo per me";
	public static final String _PER_TUTTA_LA_STRUTTURA = "Per tutta la struttura";		
	public static final String _PER_ENTRAMBI = "Entrambi";
	
	private SegnaComeArchiviatoPopup window;
	private VLayout mainLayout;
	
	private DynamicForm form; 
	private RadioGroupItem flgDestinatarioItem;
	private CheckboxItem flgTutteItem;
	private List<CheckboxItem> listaCheckbox;
	private TextAreaItem noteItem;
	
	RecordList listaUoDestNotifiche;
	
	public SegnaComeArchiviatoPopup(String titolo, String noteOld, final RecordList listaUoDestNotifiche) {
		
		super("segnaComehiviatoPopup", true);
		
		window = this;
		
		this.listaUoDestNotifiche = listaUoDestNotifiche;
		
		mainLayout = createMainLayout();
		
		if(listaUoDestNotifiche != null && listaUoDestNotifiche.getLength() > 1) {
			setHeight(350);
		} else if(listaUoDestNotifiche != null && listaUoDestNotifiche.getLength() == 1) {
			setHeight(200);
		} else {
			setHeight(100);
		}
		
		setWidth(600);
		setTitle(titolo);
		
		form = new DynamicForm();
		form.setKeepInParentRect(true);
		form.setWidth100();
		form.setHeight100();
		form.setNumCols(2);
		form.setColWidths(100, "*");
		form.setCellPadding(5);
		form.setAlign(Alignment.CENTER);
		form.setOverflow(Overflow.VISIBLE);
		form.setTop(50);
		
		List<FormItem> items = new ArrayList<FormItem>();		
		
		flgDestinatarioItem = new RadioGroupItem("flgDestinatario", "Segna come archiviato");   
		flgDestinatarioItem.setValueMap(_SOLO_PER_ME, _PER_TUTTA_LA_STRUTTURA, _PER_ENTRAMBI);
		flgDestinatarioItem.setShowTitle(false);
		flgDestinatarioItem.setVertical(false);
		flgDestinatarioItem.setWrap(false);	

		if("UT".equalsIgnoreCase(AurigaLayout.getParametroDB("DEFAULT_OPZ_ARCHIVIAZIONE"))) {
			flgDestinatarioItem.setDefaultValue(_SOLO_PER_ME);
		} else {
			if(listaUoDestNotifiche != null && listaUoDestNotifiche.getLength() > 0) {
				if("UOUT".equalsIgnoreCase(AurigaLayout.getParametroDB("DEFAULT_OPZ_ARCHIVIAZIONE"))) {
					flgDestinatarioItem.setDefaultValue(_PER_ENTRAMBI);
				} else {
					flgDestinatarioItem.setDefaultValue(_PER_TUTTA_LA_STRUTTURA);
				}
			} else {
				flgDestinatarioItem.setDefaultValue(_SOLO_PER_ME);
			}
		}		
		flgDestinatarioItem.setAttribute("obbligatorio", true);
		flgDestinatarioItem.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return listaUoDestNotifiche != null && listaUoDestNotifiche.getLength() > 0;
			}
		});
		flgDestinatarioItem.setValidators(new RequiredIfValidator(new RequiredIfFunction() {
			
			@Override
			public boolean execute(FormItem formItem, Object value) {
				return listaUoDestNotifiche != null && listaUoDestNotifiche.getLength() > 0;
			}
		}));	
		flgDestinatarioItem.addChangedHandler(new ChangedHandler() {
			
			@Override
			public void onChanged(ChangedEvent event) {
				form.markForRedraw();				
			}
		});
    	
		items.add(flgDestinatarioItem);			
		
		if(listaUoDestNotifiche != null && listaUoDestNotifiche.getLength() > 0) {
			
			listaCheckbox = new ArrayList<CheckboxItem>();							
			
			flgTutteItem = new CheckboxItem("#ALL", "<i>Tutte</i>");
			flgTutteItem.setDefaultValue(true);
			flgTutteItem.setStartRow(true);
			flgTutteItem.setEndRow(false);			
			flgTutteItem.setColSpan(1);
			flgTutteItem.setShowIfCondition(new FormItemIfFunction() {
				
				@Override
				public boolean execute(FormItem item, Object value, DynamicForm form) {
					return listaUoDestNotifiche != null && listaUoDestNotifiche.getLength() > 1 && form.getValueAsString("flgDestinatario") != null && (_PER_TUTTA_LA_STRUTTURA.equalsIgnoreCase(form.getValueAsString("flgDestinatario")) || _PER_ENTRAMBI.equalsIgnoreCase(form.getValueAsString("flgDestinatario")));
				}
			});
			
			items.add(flgTutteItem);			
			listaCheckbox.add(flgTutteItem);
			
			for(int i = 0; i < listaUoDestNotifiche.getLength(); i++) {
				
				CheckboxItem flgUoDestNotItem = new CheckboxItem(listaUoDestNotifiche.get(i).getAttribute("key"), listaUoDestNotifiche.get(i).getAttribute("value"));
				flgUoDestNotItem.setStartRow(true);
				flgUoDestNotItem.setEndRow(false);			
				flgUoDestNotItem.setColSpan(1);		
				flgUoDestNotItem.setShowIfCondition(new FormItemIfFunction() {
					
					@Override
					public boolean execute(FormItem item, Object value, DynamicForm form) {
						return listaUoDestNotifiche != null && listaUoDestNotifiche.getLength() > 1 && form.getValueAsString("flgDestinatario") != null && (_PER_TUTTA_LA_STRUTTURA.equalsIgnoreCase(form.getValueAsString("flgDestinatario")) || _PER_ENTRAMBI.equalsIgnoreCase(form.getValueAsString("flgDestinatario")));
					}
				});
				items.add(flgUoDestNotItem);
				listaCheckbox.add(flgUoDestNotItem);			
			}
				
			for(CheckboxItem check : listaCheckbox){
				check.addChangeHandler(new ChangeHandler() {
					
					@Override
					public void onChange(ChangeEvent event) {						
						String nameCheckboxClicked = event.getItem().getName();
						for(CheckboxItem check : listaCheckbox){
							if(nameCheckboxClicked.equals(check.getName())){
								check.setValue(true);
							} else {
								check.setValue(false);
							}	
							form.clearErrors(true);							
						}				
					}
				});
			}	
		}
		
		// Note
		noteItem = new TextAreaItem("note");
		noteItem.setHint("Annotazioni/commenti");
		noteItem.setShowHintInField(true);
		noteItem.setShowTitle(false);
		noteItem.setColSpan(2);
		noteItem.setHeight(100);
		noteItem.setWidth(450);
		noteItem.setAlign(Alignment.CENTER);
		noteItem.setValue(noteOld);
		noteItem.setStartRow(true);
		
		items.add(noteItem);
		
		form.setFields(items.toArray(new FormItem[items.size()]));

		mainLayout.addMember(form);
				
		Button confermaButton = new Button("Ok");   
		confermaButton.setIcon("ok.png");
		confermaButton.setIconSize(16); 
		confermaButton.setAutoFit(false);
		confermaButton.addClickHandler(new com.smartgwt.client.widgets.events.ClickHandler() {	
			
			@Override
			public void onClick(com.smartgwt.client.widgets.events.ClickEvent event) {
				if(validate()) {					
					final Record formRecord = new Record(form.getValues());
					
					RecordList listaUoSelezionate = new RecordList();
					if(listaUoDestNotifiche != null && listaUoDestNotifiche.getLength() > 0 && form.getValueAsString("flgDestinatario") != null && (_PER_TUTTA_LA_STRUTTURA.equalsIgnoreCase(form.getValueAsString("flgDestinatario"))|| _PER_ENTRAMBI.equalsIgnoreCase(form.getValueAsString("flgDestinatario")))) {
						if(listaUoDestNotifiche != null && listaUoDestNotifiche.getLength() == 1) {
							listaUoSelezionate.add(listaUoDestNotifiche.get(0));
						} else {
							if(form.getValue("#ALL") != null && (Boolean) form.getValue("#ALL")) {
								for(int i = 0; i < listaUoDestNotifiche.getLength(); i++){				
									listaUoSelezionate.add(listaUoDestNotifiche.get(i));									
								}
							} else {
								for(int i = 0; i < listaUoDestNotifiche.getLength(); i++){				
									if(form.getValue(listaUoDestNotifiche.get(i).getAttribute("key")) != null && (Boolean) form.getValue(listaUoDestNotifiche.get(i).getAttribute("key"))){
										listaUoSelezionate.add(listaUoDestNotifiche.get(i));	
										break;
									}
								}
							}							
						}
						if (_PER_ENTRAMBI.equalsIgnoreCase(form.getValueAsString("flgDestinatario"))) {
							formRecord.setAttribute("flgAnchePerUtente", "1");
						}
					}
					formRecord.setAttribute("listaUoSelezionate", listaUoSelezionate);
					
					onClickOkButton(formRecord, new DSCallback() {			
						@Override
						public void execute(DSResponse response, Object rawData, DSRequest request) {							
							window.markForDestroy();
						}
					});					
				}
			}
		});		
		
		Button annullaButton = new Button("Annulla");   
		annullaButton.setIcon("annulla.png");
		annullaButton.setIconSize(16); 
		annullaButton.setAutoFit(false);
		annullaButton.addClickHandler(new com.smartgwt.client.widgets.events.ClickHandler() {		
			
			@Override
			public void onClick(com.smartgwt.client.widgets.events.ClickEvent event) {				
				window.markForDestroy();	
			}
		});
		
		VLayout layout = new VLayout();
		layout.setHeight100();
		layout.setWidth100();
		layout.setOverflow(Overflow.AUTO);
		
		mainLayout.addMember(layout);
		
		HStack _buttons = new HStack(5);
		_buttons.setWidth100();
		_buttons.setHeight(30);
		_buttons.setAlign(Alignment.CENTER);
		_buttons.setPadding(5);
        _buttons.addMember(confermaButton);
		_buttons.addMember(annullaButton);	
		_buttons.setAutoDraw(false);
			
		VLayout portletLayout = new VLayout();
		portletLayout.setHeight100();
		portletLayout.setWidth100();
		portletLayout.setOverflow(Overflow.VISIBLE);
		
		portletLayout.setMembers(mainLayout, _buttons);
		setBody(portletLayout);
		
		setShowTitle(true);
		setHeaderIcon("blank.png");
	}
	
	private VLayout createMainLayout() {
		VLayout layout = new VLayout();
		layout.setHeight100();
		layout.setWidth100();
		layout.setOverflow(Overflow.AUTO);
		return layout;
	}
	
	public boolean validate() {
		boolean valid = form.validate();
		if(listaUoDestNotifiche != null && listaUoDestNotifiche.getLength() > 1 && form.getValueAsString("flgDestinatario") != null && (_PER_TUTTA_LA_STRUTTURA.equalsIgnoreCase(form.getValueAsString("flgDestinatario"))|| _PER_ENTRAMBI.equalsIgnoreCase(form.getValueAsString("flgDestinatario")))) {
			boolean isValorizzato = form.getValue("#ALL") != null && (Boolean) form.getValue("#ALL");
			if(!isValorizzato) {
				for(int i = 0; i < listaUoDestNotifiche.getLength(); i++){				
					if(form.getValue(listaUoDestNotifiche.get(i).getAttribute("key")) != null && (Boolean) form.getValue(listaUoDestNotifiche.get(i).getAttribute("key"))){
						isValorizzato = true;
						break;
					}
				}
			}
			if(!isValorizzato) {
				form.setFieldErrors("#ALL", "Obbligatorio selezionare almeno una struttura");
				valid = false;
			}
		}		
		return valid;
	}	
	
	public abstract void onClickOkButton(Record object, DSCallback callback);
	
}