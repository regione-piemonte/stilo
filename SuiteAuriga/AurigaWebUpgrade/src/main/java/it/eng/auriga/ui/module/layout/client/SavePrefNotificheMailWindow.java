/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import com.smartgwt.client.data.Record;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.Overflow;
import com.smartgwt.client.types.TitleOrientation;
import com.smartgwt.client.widgets.Button;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.FormItemIfFunction;
import com.smartgwt.client.widgets.form.ValuesManager;
import com.smartgwt.client.widgets.form.fields.FormItem;
import com.smartgwt.client.widgets.form.fields.RadioGroupItem;
import com.smartgwt.client.widgets.form.fields.SpacerItem;
import com.smartgwt.client.widgets.form.fields.events.ChangedEvent;
import com.smartgwt.client.widgets.form.fields.events.ChangedHandler;
import com.smartgwt.client.widgets.layout.HStack;
import com.smartgwt.client.widgets.layout.VLayout;

import it.eng.auriga.ui.module.layout.client.i18n.I18NUtil;
import it.eng.utility.ui.module.layout.client.common.DetailSection;
import it.eng.utility.ui.module.layout.client.common.items.CheckboxItem;
import it.eng.utility.ui.module.layout.client.common.items.TextItem;
import it.eng.utility.ui.module.layout.client.portal.ModalWindow;

/**
 * 
 * @author dbe4235
 *
 */

public class SavePrefNotificheMailWindow extends ModalWindow {

	private ValuesManager vm;
	
	private DetailSection detailSectionEventi;
	private DynamicForm eventiForm;
	
	private CheckboxItem assegnazioneCompetenzaDocItem;
	private CheckboxItem assegnazionePersonaleItem;
	private TextItem indirizzoAssPersItem;
	private CheckboxItem assegnazioneUOItem;
	private TextItem indirizzoAssUOItem;
	
	private CheckboxItem invioConoscenzaDocItem;
	private CheckboxItem invioCCPersonaleItem;
	private TextItem indirizzoInvioCCItem;
	private CheckboxItem invioCCUOItem;
	private TextItem indirizzoInvioCCUOItem;
	
	private CheckboxItem trasmissioneFascicoloItem;
	private CheckboxItem trasmFascPersonaleItem;
	private TextItem indirizzoTrasmFascItem;
	private CheckboxItem trasmFascUOItem;
	private TextItem indirizzoTrasmFascUOItem;
	
	private CheckboxItem trasmissioneFascicoloCCItem;
	private CheckboxItem trasmFascCCPersonaleItem;
	private TextItem indirizzoTrasmFascCCItem;
	private CheckboxItem trasmFascCCUOItem;
	private TextItem indirizzoTrasmFascCCUOItem;
	
	private CheckboxItem completamentoPassiIterItem;
	private TextItem indirizzoComplPassIterItem;
	private CheckboxItem ricezioneEmailCompetenzaItem;
	private TextItem indirizzoRiceEmailCompetItem;
	private CheckboxItem assegnazioneEmailItem;
	private TextItem indirizzoAssegnazioneEmailItem;
	
	private DetailSection detailSectionIndirizzi;
	private DynamicForm indirizziEmailForm;
	private TextItem indirizzoItem;
	private RadioGroupItem assegnazioneCompetenzaDocRadioItem;
	private RadioGroupItem invioConoscenzaDocRadioItem;
	
	private Button okButton;

	public SavePrefNotificheMailWindow(String nomeEntita) {
		
		super(nomeEntita, true);

		setTitle(I18NUtil.getMessages().configUtenteMenuPreferenzaNotificheMail_title());
		setHeaderIcon("blank.png");

		setAutoCenter(true);
		setWidth(700);
		setHeight(390);

		settingsMenu.removeItem(separatorMenuItem);
		settingsMenu.removeItem(autoSearchMenuItem);

		this.vm = new ValuesManager();
		
		createEventiDaNotificare();
		
		createIndirizzi();
		
		detailSectionEventi = new DetailSection ("Eventi da notificare", true, true, false, eventiForm);
		
		detailSectionIndirizzi = new DetailSection ("Indirizzo/i mail a cui inviare la notifica (salvo dove specificamente indicato altro)", true, true, false, indirizziEmailForm);

		okButton = new Button("Ok");
		okButton.setIcon("ok.png");
		okButton.setIconSize(16);
		okButton.setAutoFit(false);
		okButton.addClickHandler(new com.smartgwt.client.widgets.events.ClickHandler() {

			@Override
			public void onClick(com.smartgwt.client.widgets.events.ClickEvent event) {
				if(vm.validate()) {
					vm.clearErrors(true);				
					Record record = new Record(vm.getValues());
					manageOnOkButtonClick(record);
					markForDestroy();
				}
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

		layout.addMember(detailSectionEventi);
		layout.addMember(detailSectionIndirizzi);
		layout.addMember(spacerLayout);

		portletLayout.addMember(layout);
		portletLayout.addMember(_buttons);

		setBody(portletLayout);
	}

	private void createEventiDaNotificare() {
		
		eventiForm = new DynamicForm();
		eventiForm.setKeepInParentRect(true);
		eventiForm.setWrapItemTitles(false);
		eventiForm.setWidth100();
		eventiForm.setHeight100();
		eventiForm.setNumCols(5);
		eventiForm.setColWidths(10, 10, 10, 10, "*");
		eventiForm.setCellPadding(7);
		eventiForm.setCanSubmit(true);
		eventiForm.setAlign(Alignment.LEFT);
		eventiForm.setTop(50);
		eventiForm.setValuesManager(vm);
		
		if (AurigaLayout.getParametroDBAsBoolean("OBBL_MEZZO_TRASM_E")) {
			LinkedHashMap<String, String> assegnazioneCompetenzaDocMap = new LinkedHashMap<String, String>();
			assegnazioneCompetenzaDocMap.put("a", "solo documenti ricevuti via PEC/mail");
			assegnazioneCompetenzaDocMap.put("b", "tutti i documenti");
			assegnazioneCompetenzaDocMap.put("c", "nessun documento");
			
			assegnazioneCompetenzaDocRadioItem = new RadioGroupItem("assegnazioneCompetenzaDoc");
			assegnazioneCompetenzaDocRadioItem.setTitle("assegnazione per competenza di documenti");
			assegnazioneCompetenzaDocRadioItem.setTitleOrientation(TitleOrientation.TOP);
			assegnazioneCompetenzaDocRadioItem.setValueMap(assegnazioneCompetenzaDocMap);
			assegnazioneCompetenzaDocRadioItem.setVertical(false);
			assegnazioneCompetenzaDocRadioItem.setWrap(false);
			assegnazioneCompetenzaDocRadioItem.setShowTitle(true);
			assegnazioneCompetenzaDocRadioItem.setWidth(120);
			assegnazioneCompetenzaDocRadioItem.setColSpan(5);
			assegnazioneCompetenzaDocRadioItem.setStartRow(true);
			assegnazioneCompetenzaDocRadioItem.setDefaultValue("c");
			assegnazioneCompetenzaDocRadioItem.addChangedHandler(new ChangedHandler() {
				
				@Override
				public void onChanged(ChangedEvent event) {
					if (!assegnazioneCompetenzaDocRadioItem.getValueAsString().equals("c")) {
						if ((assegnazionePersonaleItem != null && assegnazionePersonaleItem.getValueAsBoolean() != null
								&& !assegnazionePersonaleItem.getValueAsBoolean())
								&& (assegnazioneUOItem != null && assegnazioneUOItem.getValueAsBoolean() != null
										&& !assegnazioneUOItem.getValueAsBoolean())) {
							assegnazionePersonaleItem.setValue(true);
							assegnazioneUOItem.setValue(true);
						} 
					}
					eventiForm.markForRedraw();
				}
			});
			
			LinkedHashMap<String, String> invioConoscenzaDocMap = new LinkedHashMap<String, String>();
			invioConoscenzaDocMap.put("a", "solo documenti ricevuti via PEC/mail");
			invioConoscenzaDocMap.put("b", "tutti i documenti");
			invioConoscenzaDocMap.put("c", "nessun documento");
			
			invioConoscenzaDocRadioItem = new RadioGroupItem("invioConoscenzaDoc");
			invioConoscenzaDocRadioItem.setTitle("invio per conoscenza di documenti");
			invioConoscenzaDocRadioItem.setTitleOrientation(TitleOrientation.TOP);
			invioConoscenzaDocRadioItem.setValueMap(invioConoscenzaDocMap);
			invioConoscenzaDocRadioItem.setVertical(false);
			invioConoscenzaDocRadioItem.setWrap(false);
			invioConoscenzaDocRadioItem.setShowTitle(true);
			invioConoscenzaDocRadioItem.setWidth(120);
			invioConoscenzaDocRadioItem.setColSpan(5);
			invioConoscenzaDocRadioItem.setStartRow(true);
			invioConoscenzaDocRadioItem.setDefaultValue("c");
			invioConoscenzaDocRadioItem.addChangedHandler(new ChangedHandler() {
				
				@Override
				public void onChanged(ChangedEvent event) {
					if (!invioConoscenzaDocRadioItem.getValueAsString().equals("c")) {
						if ((invioCCPersonaleItem != null && invioCCPersonaleItem.getValueAsBoolean() != null
								&& !invioCCPersonaleItem.getValueAsBoolean())
								&& (invioCCUOItem != null && invioCCUOItem.getValueAsBoolean() != null
										&& !invioCCUOItem.getValueAsBoolean())) {
							invioCCPersonaleItem.setValue(true);
							invioCCUOItem.setValue(true);
						} 
					}
					eventiForm.markForRedraw();
				}
			});
		} else {
			assegnazioneCompetenzaDocItem = new CheckboxItem("assegnazioneCompetenzaDoc", "assegnazione per competenza di documenti");
			assegnazioneCompetenzaDocItem.setStartRow(true);
			assegnazioneCompetenzaDocItem.setColSpan(5);
			assegnazioneCompetenzaDocItem.addChangedHandler(new ChangedHandler() {
				
				@Override
				public void onChanged(ChangedEvent event) {
					if ((assegnazionePersonaleItem != null && assegnazionePersonaleItem.getValueAsBoolean() != null
							&& !assegnazionePersonaleItem.getValueAsBoolean())
							&& (assegnazioneUOItem != null && assegnazioneUOItem.getValueAsBoolean() != null
									&& !assegnazioneUOItem.getValueAsBoolean())) {
						assegnazionePersonaleItem.setValue(true);
						assegnazioneUOItem.setValue(true);
					} 
					eventiForm.markForRedraw();
				}
			});
			
			invioConoscenzaDocItem = new CheckboxItem("invioConoscenzaDoc", "invio per conoscenza di documenti");
			invioConoscenzaDocItem.setStartRow(true);	
			invioConoscenzaDocItem.setColSpan(5);
			invioConoscenzaDocItem.addChangedHandler(new ChangedHandler() {
				
				@Override
				public void onChanged(ChangedEvent event) {
					if ((invioCCPersonaleItem != null && invioCCPersonaleItem.getValueAsBoolean() != null
							&& !invioCCPersonaleItem.getValueAsBoolean())
							&& (invioCCUOItem != null && invioCCUOItem.getValueAsBoolean() != null
									&& !invioCCUOItem.getValueAsBoolean())) {
						invioCCPersonaleItem.setValue(true);
						invioCCUOItem.setValue(true);
					} 
					eventiForm.markForRedraw();
				}
			});
		}
						
		// ASSEGNAZIONE DOC 
		
		SpacerItem spacerAssDoc = new SpacerItem();
		spacerAssDoc.setColSpan(1);
		spacerAssDoc.setStartRow(true);
		spacerAssDoc.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {					
				return showCheckAssegnazioneDoc();
			}
		});
		
		assegnazionePersonaleItem = new CheckboxItem("assegnazionePersonale", "a me personalmente");
		assegnazionePersonaleItem.setStartRow(false);
		assegnazionePersonaleItem.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				if(showCheckAssegnazioneDoc() &&
				   (assegnazionePersonaleItem != null && assegnazionePersonaleItem.getValueAsBoolean() != null && assegnazionePersonaleItem.getValueAsBoolean())) {
					assegnazionePersonaleItem.setValue(true);
				} else {
					assegnazionePersonaleItem.setValue(false);
				}
				return showCheckAssegnazioneDoc();
			}
		});
		
		indirizzoAssPersItem = new TextItem("indirizzoAssPers","Indirizzo/i a cui inviare la notifica");
		indirizzoAssPersItem.setColSpan(1);
		indirizzoAssPersItem.setEndRow(true);
		indirizzoAssPersItem.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return showCheckAssegnazioneDoc();
			}
		});
		
		assegnazioneUOItem = new CheckboxItem("assegnazioneUO", "alla/e mie strutture");
		assegnazioneUOItem.setStartRow(false);
		assegnazioneUOItem.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				if(showCheckAssegnazioneDoc() &&
				   (assegnazioneUOItem != null && assegnazioneUOItem.getValueAsBoolean() != null && assegnazioneUOItem.getValueAsBoolean())) {
					assegnazioneUOItem.setValue(true);
				} else {
					assegnazioneUOItem.setValue(false);
				}
				return showCheckAssegnazioneDoc();
			}
		});
		
		indirizzoAssUOItem = new TextItem("indirizzoAssUO","Indirizzo/i a cui inviare la notifica");
		indirizzoAssUOItem.setColSpan(1);
		indirizzoAssUOItem.setEndRow(true);
		indirizzoAssUOItem.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return showCheckAssegnazioneDoc();
			}
		});
		
		// INVIO CC
		SpacerItem spacerInvioCC = new SpacerItem();
		spacerInvioCC.setColSpan(1);
		spacerInvioCC.setStartRow(true);
		spacerInvioCC.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {					
				return showCheckInvioCC();
			}
		});
		
		invioCCPersonaleItem = new CheckboxItem("invioCCPersonale", "a me personalmente");
		invioCCPersonaleItem.setStartRow(false);
		invioCCPersonaleItem.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				if(showCheckInvioCC() &&
				  (invioCCPersonaleItem != null && invioCCPersonaleItem.getValueAsBoolean() != null && invioCCPersonaleItem.getValueAsBoolean())) {
					invioCCPersonaleItem.setValue(true);
				} else {
					invioCCPersonaleItem.setValue(false);
				}
				return showCheckInvioCC();
			}
		});
		
		indirizzoInvioCCItem = new TextItem("indirizzoInvioCC","Indirizzo/i a cui inviare la notifica");
		indirizzoInvioCCItem.setColSpan(1);
		indirizzoInvioCCItem.setEndRow(true);
		indirizzoInvioCCItem.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return showCheckInvioCC();
			}
		});
		
		invioCCUOItem = new CheckboxItem("invioCCUO", "alla/e mie strutture");
		invioCCUOItem.setStartRow(false);
		invioCCUOItem.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				if(showCheckInvioCC() &&
				  (invioCCUOItem != null && invioCCUOItem.getValueAsBoolean() != null && invioCCUOItem.getValueAsBoolean())) {
					invioCCUOItem.setValue(true);
				} else {
					invioCCUOItem.setValue(false);
				}
				return showCheckInvioCC();
			}
		});
		
		indirizzoInvioCCUOItem = new TextItem("indirizzoInvioCCUO","Indirizzo/i a cui inviare la notifica");
		indirizzoInvioCCUOItem.setColSpan(1);
		indirizzoInvioCCUOItem.setEndRow(true);
		indirizzoInvioCCUOItem.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return showCheckInvioCC();
			}
		});
		
		// TRASMISSIONE FASCICOLO PER COMPETENZA
			
		trasmissioneFascicoloItem = new CheckboxItem("trasmissioneFascicolo", "assegnazione per competenza di fascicoli");
		trasmissioneFascicoloItem.setStartRow(true);
		trasmissioneFascicoloItem.setColSpan(5);
		trasmissioneFascicoloItem.addChangedHandler(new ChangedHandler() {
			
			@Override
			public void onChanged(ChangedEvent event) {
				if ((trasmFascPersonaleItem != null && trasmFascPersonaleItem.getValueAsBoolean() != null
						&& !trasmFascPersonaleItem.getValueAsBoolean())
						&& (trasmFascUOItem != null && trasmFascUOItem.getValueAsBoolean() != null
								&& !trasmFascUOItem.getValueAsBoolean())) {
					trasmFascPersonaleItem.setValue(true);
					trasmFascUOItem.setValue(true);
				} 
				eventiForm.markForRedraw();
			}
		});
		
		SpacerItem spacerTrasmFasc = new SpacerItem();
		spacerTrasmFasc.setColSpan(1);
		spacerTrasmFasc.setStartRow(true);
		spacerTrasmFasc.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {					
				return showCheckTrasmissioneFascicolo();
			}
		});
		
		trasmFascPersonaleItem = new CheckboxItem("trasmFascPersonale", "a me personalmente");
		trasmFascPersonaleItem.setStartRow(false);
		trasmFascPersonaleItem.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				if(showCheckTrasmissioneFascicolo() &&
				  (trasmFascPersonaleItem != null && trasmFascPersonaleItem.getValueAsBoolean() != null && trasmFascPersonaleItem.getValueAsBoolean())) {
					trasmFascPersonaleItem.setValue(true);
				} else {
					trasmFascPersonaleItem.setValue(false);
				}
				return showCheckTrasmissioneFascicolo();
			}
		});
		
		indirizzoTrasmFascItem = new TextItem("indirizzoTrasmFasc","Indirizzo/i a cui inviare la notifica");
		indirizzoTrasmFascItem.setColSpan(1);
		indirizzoTrasmFascItem.setEndRow(true);
		indirizzoTrasmFascItem.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return showCheckTrasmissioneFascicolo();
			}
		});
		
		trasmFascUOItem = new CheckboxItem("trasmFascUO", "alla/e mie strutture");
		trasmFascUOItem.setStartRow(false);
		trasmFascUOItem.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				if(showCheckTrasmissioneFascicolo() &&
				  (trasmFascUOItem != null && trasmFascUOItem.getValueAsBoolean() != null && trasmFascUOItem.getValueAsBoolean())) {
					trasmFascUOItem.setValue(true);
				} else {
					trasmFascUOItem.setValue(false);
				}
				return showCheckTrasmissioneFascicolo();
			}
		});
		
		indirizzoTrasmFascUOItem = new TextItem("indirizzoTrasmFascUO","Indirizzo/i a cui inviare la notifica");
		indirizzoTrasmFascUOItem.setColSpan(1);
		indirizzoTrasmFascUOItem.setEndRow(true);
		indirizzoTrasmFascUOItem.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return showCheckTrasmissioneFascicolo();
			}
		});
		
		// TRASMISSIONE FASCICOLO PER CONOSCENZA

		trasmissioneFascicoloCCItem = new CheckboxItem("trasmissioneFascicoloCC", "invio per conoscenza di fascicoli");
		trasmissioneFascicoloCCItem.setStartRow(true);
		trasmissioneFascicoloCCItem.setColSpan(5);
		trasmissioneFascicoloCCItem.addChangedHandler(new ChangedHandler() {
			
			@Override
			public void onChanged(ChangedEvent event) {
				if ((trasmFascCCPersonaleItem != null && trasmFascCCPersonaleItem.getValueAsBoolean() != null
						&& !trasmFascCCPersonaleItem.getValueAsBoolean())
						&& (trasmFascCCUOItem != null && trasmFascCCUOItem.getValueAsBoolean() != null
								&& !trasmFascCCUOItem.getValueAsBoolean())) {
					trasmFascCCPersonaleItem.setValue(true);
					trasmFascCCUOItem.setValue(true);
				} 
				eventiForm.markForRedraw();
			}
		});
		
		SpacerItem spacerTrasmFascCC = new SpacerItem();
		spacerTrasmFascCC.setColSpan(1);
		spacerTrasmFascCC.setStartRow(true);
		spacerTrasmFascCC.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {					
				return showCheckTrasmissioneFascicoloCC();
			}
		});
		
		trasmFascCCPersonaleItem = new CheckboxItem("trasmFascCCPersonale", "a me personalmente");
		trasmFascCCPersonaleItem.setStartRow(false);
		trasmFascCCPersonaleItem.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				if(showCheckTrasmissioneFascicoloCC() &&
				  (trasmFascCCPersonaleItem != null && trasmFascCCPersonaleItem.getValueAsBoolean() != null && trasmFascCCPersonaleItem.getValueAsBoolean())) {
					trasmFascCCPersonaleItem.setValue(true);
				} else {
					trasmFascCCPersonaleItem.setValue(false);
				}
				return showCheckTrasmissioneFascicoloCC();
			}
		});
		
		indirizzoTrasmFascCCItem = new TextItem("indirizzoTrasmFascCC","Indirizzo/i a cui inviare la notifica");
		indirizzoTrasmFascCCItem.setColSpan(1);
		indirizzoTrasmFascCCItem.setEndRow(true);
		indirizzoTrasmFascCCItem.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return showCheckTrasmissioneFascicoloCC();
			}
		});
		
		trasmFascCCUOItem = new CheckboxItem("trasmFascCCUO", "alla/e mie strutture");
		trasmFascCCUOItem.setStartRow(false);
		trasmFascCCUOItem.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				if(showCheckTrasmissioneFascicoloCC() &&
				  (trasmFascCCUOItem != null && trasmFascCCUOItem.getValueAsBoolean() != null && trasmFascCCUOItem.getValueAsBoolean())) {
					trasmFascCCUOItem.setValue(true);
				} else {
					trasmFascCCUOItem.setValue(false);
				}
				return showCheckTrasmissioneFascicoloCC();
			}
		});
		
		indirizzoTrasmFascCCUOItem = new TextItem("indirizzoTrasmFascCCUO","Indirizzo/i a cui inviare la notifica");
		indirizzoTrasmFascCCUOItem.setColSpan(1);
		indirizzoTrasmFascCCUOItem.setEndRow(true);
		indirizzoTrasmFascCCUOItem.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return showCheckTrasmissioneFascicoloCC();
			}
		});
		
		// Completamento passi in iter
		
		completamentoPassiIterItem = new CheckboxItem("completamentoPassiIter", "completamento di passi iter atti/procedimenti");
		completamentoPassiIterItem.setStartRow(true);
		completamentoPassiIterItem.setColSpan(2);
		completamentoPassiIterItem.addChangedHandler(new ChangedHandler() {
			
			@Override
			public void onChanged(ChangedEvent event) {
				eventiForm.markForRedraw();
			}
		});
		
		indirizzoComplPassIterItem = new TextItem("indirizzoComplPassIter","Indirizzo/i a cui inviare la notifica");
		indirizzoComplPassIterItem.setEndRow(true);
		indirizzoComplPassIterItem.setColSpan(1);
		indirizzoComplPassIterItem.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				
				return showComplPassIter();
			}
		});
		
		// Ricezione email competenza
		
		ricezioneEmailCompetenzaItem = new CheckboxItem("ricezioneEmailCompetenza", "ricezione mail su casella PEC di competenza");
		ricezioneEmailCompetenzaItem.setStartRow(true);
		ricezioneEmailCompetenzaItem.setColSpan(2);
		ricezioneEmailCompetenzaItem.addChangedHandler(new ChangedHandler() {
			
			@Override
			public void onChanged(ChangedEvent event) {
				eventiForm.markForRedraw();
			}
		});
		
		indirizzoRiceEmailCompetItem = new TextItem("indirizzoRiceEmailCompet","Indirizzo/i a cui inviare la notifica");
		indirizzoRiceEmailCompetItem.setEndRow(true);
		indirizzoRiceEmailCompetItem.setColSpan(1);
		indirizzoRiceEmailCompetItem.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				
				return showIndRiceEmailComp();
			}
		});
		
		assegnazioneEmailItem = new CheckboxItem("assegnazioneEmail", "assegnazione per competenza di e-mail");
		assegnazioneEmailItem.setStartRow(true);
		assegnazioneEmailItem.setColSpan(2);
		assegnazioneEmailItem.addChangedHandler(new ChangedHandler() {
			
			@Override
			public void onChanged(ChangedEvent event) {
				eventiForm.markForRedraw();
			}
		});
		
		indirizzoAssegnazioneEmailItem = new TextItem("indirizzoAssegnazioneEmail","Indirizzo/i a cui inviare la notifica");
		indirizzoAssegnazioneEmailItem.setEndRow(true);
		indirizzoAssegnazioneEmailItem.setColSpan(1);
		indirizzoAssegnazioneEmailItem.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				
				return showAssegnEmail();
			}
		});
		
		List<FormItem> listItems = new ArrayList<FormItem>();
		
		if (AurigaLayout.getParametroDBAsBoolean("OBBL_MEZZO_TRASM_E")) {	
			listItems.add(assegnazioneCompetenzaDocRadioItem);
		} else {
			listItems.add(assegnazioneCompetenzaDocItem);
		}
		listItems.add(spacerAssDoc);
		listItems.add(assegnazionePersonaleItem);
		listItems.add(indirizzoAssPersItem);
		listItems.add(spacerAssDoc);
		listItems.add(assegnazioneUOItem);
		listItems.add(indirizzoAssUOItem);
		if (AurigaLayout.getParametroDBAsBoolean("OBBL_MEZZO_TRASM_E")) {	
			listItems.add(invioConoscenzaDocRadioItem);
		} else {
			listItems.add(invioConoscenzaDocItem);
		}
		listItems.add(spacerInvioCC);
		listItems.add(invioCCPersonaleItem);
		listItems.add(indirizzoInvioCCItem);
		listItems.add(spacerInvioCC);
		listItems.add(invioCCUOItem);
		listItems.add(indirizzoInvioCCUOItem);                    	
		listItems.add(trasmissioneFascicoloItem);
		listItems.add(spacerTrasmFasc);
		listItems.add(trasmFascPersonaleItem);
		listItems.add(indirizzoTrasmFascItem);
		listItems.add(spacerTrasmFasc);
		listItems.add(trasmFascUOItem);
		listItems.add(indirizzoTrasmFascUOItem);
		listItems.add(trasmissioneFascicoloCCItem);
		listItems.add(spacerTrasmFascCC);
		listItems.add(trasmFascCCPersonaleItem);
		listItems.add(indirizzoTrasmFascCCItem);
		listItems.add(spacerTrasmFascCC);
		listItems.add(trasmFascCCUOItem);
		listItems.add(indirizzoTrasmFascCCUOItem);	                              
		listItems.add(completamentoPassiIterItem);
		listItems.add(indirizzoComplPassIterItem);
		if(AurigaLayout.getParametroDBAsBoolean("ATTIVA_NOT_EMAIL_RICEZIONE_PEC")) {
			listItems.add(ricezioneEmailCompetenzaItem);
			listItems.add(indirizzoRiceEmailCompetItem);
		}
		if(!AurigaLayout.getParametroDBAsBoolean("HIDE_PREF_NOTIFICA_ASS_MAIL")) {
			listItems.add(assegnazioneEmailItem);
			listItems.add(indirizzoAssegnazioneEmailItem);
		}
		
		eventiForm.setFields(listItems.toArray(new FormItem[listItems.size()]));
	}
	
	private Boolean showCheckAssegnazioneDoc() {
		Boolean visible = false;		
		if(!AurigaLayout.getParametroDBAsBoolean("ESCLUDI_SV_IN_ORG_X_SEL_ASS")) {
			if(assegnazioneCompetenzaDocItem != null && assegnazioneCompetenzaDocItem.getValueAsBoolean() != null && assegnazioneCompetenzaDocItem.getValueAsBoolean()) {
				visible = true;
			} else if (assegnazioneCompetenzaDocRadioItem != null && assegnazioneCompetenzaDocRadioItem.getValueAsString() != null &&
					("a".equals(assegnazioneCompetenzaDocRadioItem.getValueAsString()) || "b".equals(assegnazioneCompetenzaDocRadioItem.getValueAsString()))) {
				visible = true;
			}
		}
		return visible;
	}
	
	private Boolean showCheckInvioCC() {
		Boolean visible = false;		
		if(!AurigaLayout.getParametroDBAsBoolean("ESCLUDI_SV_IN_ORG_X_SEL_ASS")) {
			if(invioConoscenzaDocItem != null && invioConoscenzaDocItem.getValueAsBoolean() != null && invioConoscenzaDocItem.getValueAsBoolean()) {
				visible = true;
			} else if (invioConoscenzaDocRadioItem != null && invioConoscenzaDocRadioItem.getValueAsString() != null &&
					("a".equals(invioConoscenzaDocRadioItem.getValueAsString()) || "b".equals(invioConoscenzaDocRadioItem.getValueAsString()))) {
				visible = true;
			}
		}
		return visible;
	}
	
	private Boolean showCheckTrasmissioneFascicolo() {
		Boolean visible = false;		
		if(!AurigaLayout.getParametroDBAsBoolean("ESCLUDI_SV_IN_ORG_X_SEL_ASS")) {
			if(trasmissioneFascicoloItem != null && trasmissioneFascicoloItem.getValueAsBoolean() != null && trasmissioneFascicoloItem.getValueAsBoolean()) {
				visible = true;
			}
		}
		return visible;
	}
	
	private Boolean showCheckTrasmissioneFascicoloCC() {
		Boolean visible = false;		
		if(!AurigaLayout.getParametroDBAsBoolean("ESCLUDI_SV_IN_ORG_X_SEL_ASS")) {
			if(trasmissioneFascicoloCCItem != null && trasmissioneFascicoloCCItem.getValueAsBoolean() != null && trasmissioneFascicoloCCItem.getValueAsBoolean()) {
				visible = true;
			}
		}
		return visible;
	}
	
	private Boolean showComplPassIter() {
		return completamentoPassiIterItem != null && completamentoPassiIterItem.getValueAsBoolean() != null &&
				completamentoPassiIterItem.getValueAsBoolean();
	}
	
	private Boolean showIndRiceEmailComp() {
		return ricezioneEmailCompetenzaItem != null && ricezioneEmailCompetenzaItem.getValueAsBoolean() != null &&
				ricezioneEmailCompetenzaItem.getValueAsBoolean();
	}
	
	private Boolean showAssegnEmail() {
		return assegnazioneEmailItem != null && assegnazioneEmailItem.getValueAsBoolean() != null &&
				assegnazioneEmailItem.getValueAsBoolean();
	}
	
	private void createIndirizzi() {
		
		indirizziEmailForm = new DynamicForm();
		indirizziEmailForm.setKeepInParentRect(true);
		indirizziEmailForm.setWrapItemTitles(false);
		indirizziEmailForm.setWidth100();
		indirizziEmailForm.setHeight100();
		indirizziEmailForm.setNumCols(5);
		indirizziEmailForm.setColWidths(10, 10, 10, 10, "*");
		indirizziEmailForm.setCellPadding(7);
		indirizziEmailForm.setCanSubmit(true);
		indirizziEmailForm.setAlign(Alignment.LEFT);
		indirizziEmailForm.setTop(50);
		indirizziEmailForm.setValuesManager(vm);
		
		indirizzoItem = new TextItem("indirizzoEmail","E-mail");
		indirizzoItem.setWidth(500);
		indirizzoItem.setPrompt("puoi specificare più indirizzi separandoli con ; o ,");
		
		indirizziEmailForm.setItems(indirizzoItem);
	}

	public void clearValues() {
		if(eventiForm != null) {
			eventiForm.clearValues();
		}
		if(indirizziEmailForm != null) {
			indirizziEmailForm.clearValues();
		}
	}
	
	public void setValues(Record values) {
		if (values != null) {	
			vm.editRecord(values);
		} else {
			vm.editNewRecord();
		}
		vm.clearErrors(true);
	}
	
	public void manageOnOkButtonClick(Record values) {

	}

}