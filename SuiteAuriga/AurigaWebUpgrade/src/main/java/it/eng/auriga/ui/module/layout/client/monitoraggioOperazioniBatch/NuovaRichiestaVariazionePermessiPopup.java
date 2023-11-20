/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;

import com.google.gwt.core.client.Scheduler;
import com.google.gwt.core.client.Scheduler.ScheduledCommand;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.smartgwt.client.data.DSCallback;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.Overflow;
import com.smartgwt.client.widgets.Button;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.FormItemIfFunction;
import com.smartgwt.client.widgets.form.ValuesManager;
import com.smartgwt.client.widgets.form.fields.ButtonItem;
import com.smartgwt.client.widgets.form.fields.FormItem;
import com.smartgwt.client.widgets.form.fields.StaticTextItem;
import com.smartgwt.client.widgets.form.fields.events.ChangedEvent;
import com.smartgwt.client.widgets.form.fields.events.ChangedHandler;
import com.smartgwt.client.widgets.layout.HStack;
import com.smartgwt.client.widgets.layout.VLayout;

import it.eng.auriga.ui.module.layout.client.richiestaAccessoAtti.SelezionaUfficioItems;
import it.eng.utility.ui.module.layout.client.Layout;
import it.eng.utility.ui.module.layout.client.common.items.DateTimeItem;
import it.eng.utility.ui.module.layout.client.common.items.SelectItem;
import it.eng.utility.ui.module.layout.client.common.items.TextAreaItem;
import it.eng.utility.ui.module.layout.client.portal.ModalWindow;

public abstract class NuovaRichiestaVariazionePermessiPopup extends ModalWindow {


	protected NuovaRichiestaVariazionePermessiPopup _window;
	
	protected ValuesManager vm;
	
	protected DynamicForm formAssegnazione;
	
	protected SelectItem tipoOggettiRiassegnareItem;
	protected DateTimeItem dataeOraOperazioneItem;
	
	protected StaticTextItem titoloPostazioneDaItem;
	protected SelezionaUfficioItems ufficioPostazioneDaItems;
	
	protected StaticTextItem titoloPostazioneVsItem;
	protected SelezionaUfficioItems ufficioPostazioneVsItems;
	
	protected TextAreaItem motivazioneRichiestaItem;
	protected TextAreaItem noteRichiestaItem;

	protected ButtonItem confermaButton;
	protected ButtonItem annullaButton;
	
	public NuovaRichiestaVariazionePermessiPopup() {

		super("nuova_richiesta_variazione_permessi", true);

		_window = this;
		
		vm = new ValuesManager();		

		setTitle("Nuova richiesta di variazione permessi");

		setAutoCenter(true);
		setHeight(440);
		setWidth(850);
		setAlign(Alignment.CENTER);
		setTop(50);
		
		settingsMenu.removeItem(separatorMenuItem);
		settingsMenu.removeItem(autoSearchMenuItem);

		formAssegnazione = new DynamicForm();
		formAssegnazione.setValuesManager(vm);
		formAssegnazione.setKeepInParentRect(true);
		formAssegnazione.setWidth100();
		formAssegnazione.setHeight100();
		formAssegnazione.setNumCols(10);
		formAssegnazione.setColWidths(1, 1, 1, 1, 1, 1, 1, 1, 1, "*");
		formAssegnazione.setCellPadding(5);
		formAssegnazione.setWrapItemTitles(false);

		tipoOggettiRiassegnareItem = new SelectItem("tipoOggettoRiassegnare", setTitleAlign("Tipo di oggetti per cui variare permessi", 240, true));		
		LinkedHashMap<String, String> tipiOggRiassegnareValueMap = new LinkedHashMap<String, String>();
		
		if(Layout.isPrivilegioAttivo("ROM/M/VPF")) {
			tipiOggRiassegnareValueMap.put("F", "Fascicoli");
		}
		if (Layout.isPrivilegioAttivo("ROM/M/VPD")) {
			tipiOggRiassegnareValueMap.put("D", "Documenti");

			if (Layout.isPrivilegioAttivo("ROM/M/VPF")) {
				tipiOggRiassegnareValueMap.put("DF", "Documenti e fascicoli");
			}
		}

		tipoOggettiRiassegnareItem.setValueMap(tipiOggRiassegnareValueMap);
		tipoOggettiRiassegnareItem.setStartRow(true);
		tipoOggettiRiassegnareItem.setDisplayField("value");
		tipoOggettiRiassegnareItem.setValueField("key");
		tipoOggettiRiassegnareItem.setWrapTitle(false);
		tipoOggettiRiassegnareItem.setColSpan(8);
		tipoOggettiRiassegnareItem.setTitleColSpan(1);
		tipoOggettiRiassegnareItem.setAllowEmptyValue(true);
		tipoOggettiRiassegnareItem.setRequired(true);
		tipoOggettiRiassegnareItem.addChangedHandler(new ChangedHandler() {
			
			@Override
			public void onChanged(ChangedEvent event) {
				formAssegnazione.markForRedraw();
			}
		});
		
		dataeOraOperazioneItem = new DateTimeItem("dataeOraOperazione","Data e ora a cui effettuare l’operazione");
		dataeOraOperazioneItem.setColSpan(6);
		dataeOraOperazioneItem.setWidth(150);
		dataeOraOperazioneItem.setTitleColSpan(1);
		dataeOraOperazioneItem.setStartRow(true);
		dataeOraOperazioneItem.setRequired(true);
		String data = DateTimeFormat.getFormat("dd/MM/yyyy").format(new Date()) + " 23:59";
		dataeOraOperazioneItem.setDefaultValue(data);
		
		titoloPostazioneDaItem = new StaticTextItem("titoloPostazioneDa");
		titoloPostazioneDaItem.setValue("<span style=\"color:#37505F\"><b> Da UO/postazione </span></b>");
		titoloPostazioneDaItem.setShowTitle(false);
		titoloPostazioneDaItem.setStartRow(true);
		titoloPostazioneDaItem.setAlign(Alignment.RIGHT);
		titoloPostazioneDaItem.setColSpan(1);
		titoloPostazioneDaItem.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem arg0, Object arg1, DynamicForm arg2) {
				return tipoOggettiRiassegnareItem.getValueAsString()!=null && !"".equals(tipoOggettiRiassegnareItem.getValueAsString());
			}
		});
		
		ufficioPostazioneDaItems = new SelezionaUfficioItems(formAssegnazione, "idUoPostazioneDa", "desUoPostazioneDa", "codUoPostazioneDa", "organigrammaUoPostazioneDa"){
			
			@Override
			protected boolean codRapidoItemShowIfCondition() {
				return tipoOggettiRiassegnareItem.getValueAsString()!=null && !"".equals(tipoOggettiRiassegnareItem.getValueAsString());
			}
			
			@Override
			protected boolean lookupOrganigrammaButtonShowIfCondition() {
				return tipoOggettiRiassegnareItem.getValueAsString()!=null && !"".equals(tipoOggettiRiassegnareItem.getValueAsString());
			}
			
			@Override
			protected boolean organigrammaItemShowIfCondition() {
				return tipoOggettiRiassegnareItem.getValueAsString()!=null && !"".equals(tipoOggettiRiassegnareItem.getValueAsString());
			}
			
			@Override
			public boolean isRequiredOrganigramma() {
				return true;
			}
			
			@Override
			public String getTipoAssegnatari() {
				return "UO;SV";
			}
					
		};
		
		titoloPostazioneVsItem = new StaticTextItem("titoloPostazioneVs");
		titoloPostazioneVsItem.setStartRow(true);
		titoloPostazioneVsItem.setShowTitle(false);
		titoloPostazioneVsItem.setValue("<span style=\"color:#37505F\"><b> Vs UO/postazione </span></b>");
		titoloPostazioneVsItem.setAlign(Alignment.RIGHT);
		titoloPostazioneVsItem.setColSpan(1);
		titoloPostazioneVsItem.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem arg0, Object arg1, DynamicForm arg2) {
				return tipoOggettiRiassegnareItem.getValueAsString()!=null && !"".equals(tipoOggettiRiassegnareItem.getValueAsString());
			}
		});
		
		ufficioPostazioneVsItems = new SelezionaUfficioItems(formAssegnazione, "idUoPostazioneVS", "desUoPostazioneVs", "codUoPostazioneVs", "organigrammaUoPostazioneVs"){
			@Override
			protected boolean codRapidoItemShowIfCondition() {
				return tipoOggettiRiassegnareItem.getValueAsString()!=null && !"".equals(tipoOggettiRiassegnareItem.getValueAsString());
			}
			
			@Override
			protected boolean lookupOrganigrammaButtonShowIfCondition() {
				return tipoOggettiRiassegnareItem.getValueAsString()!=null && !"".equals(tipoOggettiRiassegnareItem.getValueAsString());
			}
			
			@Override
			protected boolean organigrammaItemShowIfCondition() {
				return tipoOggettiRiassegnareItem.getValueAsString()!=null && !"".equals(tipoOggettiRiassegnareItem.getValueAsString());
			}
			
			@Override
			public boolean isRequiredOrganigramma() {
				return true;
			}
			
			@Override
			public String getFlgSoloValide() {
				return "true";
			}
			
			@Override
			public String getTipoAssegnatari() {
				return "UO;SV";
			}
			
		};
		
		motivazioneRichiestaItem = new TextAreaItem("motivazioneRichiesta", "Motivazioni richiesta");
		motivazioneRichiestaItem.setShowTitle(true);
		motivazioneRichiestaItem.setStartRow(true);
		motivazioneRichiestaItem.setLength(4000);
		motivazioneRichiestaItem.setHeight(70);
		motivazioneRichiestaItem.setWidth(350);
		motivazioneRichiestaItem.setColSpan(9);
		
		noteRichiestaItem = new TextAreaItem("noteRichiesta", "Note richiesta");
		noteRichiestaItem.setShowTitle(true);
		noteRichiestaItem.setStartRow(true);
		noteRichiestaItem.setLength(4000);
		noteRichiestaItem.setHeight(70);
		noteRichiestaItem.setColSpan(9);			
		noteRichiestaItem.setWidth(350);
		
		List<FormItem> itemsRichiestaRiassegnazione = new ArrayList<FormItem>();
		itemsRichiestaRiassegnazione.add(tipoOggettiRiassegnareItem);
		itemsRichiestaRiassegnazione.add(dataeOraOperazioneItem);
		itemsRichiestaRiassegnazione.add(titoloPostazioneDaItem);
		itemsRichiestaRiassegnazione.addAll(ufficioPostazioneDaItems);
		itemsRichiestaRiassegnazione.add(titoloPostazioneVsItem);
		itemsRichiestaRiassegnazione.addAll(ufficioPostazioneVsItems);
		itemsRichiestaRiassegnazione.add(motivazioneRichiestaItem);
		itemsRichiestaRiassegnazione.add(noteRichiestaItem);
		
		formAssegnazione.setFields(itemsRichiestaRiassegnazione.toArray(new FormItem[itemsRichiestaRiassegnazione.size()]));
	
		Button confermaButton = new Button("Ok");
		confermaButton.setIcon("ok.png");
		confermaButton.setIconSize(16);
		confermaButton.setAutoFit(false);
		confermaButton.addClickHandler(new com.smartgwt.client.widgets.events.ClickHandler() {

			@Override
			public void onClick(com.smartgwt.client.widgets.events.ClickEvent event) {
				
				Scheduler.get().scheduleDeferred(new ScheduledCommand() {

					@Override
					public void execute() {
						if(vm.validate()) {
							onClickOkButton(new Record(vm.getValues()), new DSCallback() {			
								@Override
								public void execute(DSResponse response, Object rawData, DSRequest request) {
									
								_window.markForDestroy();
								}
							});			
						}
					}
				});			
			}
		});

		Button annullaButton = new Button("Annulla");
		annullaButton.setIcon("annulla.png");
		annullaButton.setIconSize(16);
		annullaButton.setAutoFit(false);
		annullaButton.addClickHandler(new com.smartgwt.client.widgets.events.ClickHandler() {

			@Override
			public void onClick(com.smartgwt.client.widgets.events.ClickEvent event) {
				_window.markForDestroy();
			}
		});

		HStack _buttons = new HStack(5);
		_buttons.setHeight(30);
		_buttons.setAlign(Alignment.CENTER);
		_buttons.setPadding(5);
		_buttons.addMember(confermaButton);
		_buttons.addMember(annullaButton);

		VLayout layout = new VLayout();
		layout.setOverflow(Overflow.AUTO);
		layout.setHeight100();
		layout.setWidth100();
		layout.setAlign(Alignment.CENTER);
		
		layout.addMember(formAssegnazione);
		layout.addMember(_buttons);
		
		setBody(layout);

		setIcon("archivio/assegna.png");
	}
	

	public abstract void onClickOkButton(Record record, DSCallback callback);
		
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
	
	protected String setTitleAlign(String title, int width, boolean required) {
		if (title != null && title.startsWith("<span")) {
			return title;
		}
		return "<span style=\"width: " + (required ? width : width + 1) + "px; display: inline-block;\">" + title + "</span>";
	}

}
