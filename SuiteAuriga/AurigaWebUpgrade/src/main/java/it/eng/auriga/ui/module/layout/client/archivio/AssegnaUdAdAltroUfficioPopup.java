/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.HashMap;
import java.util.Map;

import com.google.gwt.core.client.Scheduler;
import com.google.gwt.core.client.Scheduler.ScheduledCommand;
import com.smartgwt.client.data.DSCallback;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.Overflow;
import com.smartgwt.client.widgets.Button;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.ValuesManager;
import com.smartgwt.client.widgets.form.fields.ButtonItem;
import com.smartgwt.client.widgets.layout.HStack;
import com.smartgwt.client.widgets.layout.VLayout;

import it.eng.auriga.ui.module.layout.client.AurigaLayout;
import it.eng.auriga.ui.module.layout.client.organigramma.LegendaDinamicaPanel;
import it.eng.auriga.ui.module.layout.client.protocollazione.AssegnazioneItem;
import it.eng.utility.ui.module.layout.client.common.DetailSection;
import it.eng.utility.ui.module.layout.client.common.items.StaticTextItem;
import it.eng.utility.ui.module.layout.client.portal.ModalWindow;

public abstract class AssegnaUdAdAltroUfficioPopup extends ModalWindow {

	protected AssegnaUdAdAltroUfficioPopup _window;
	
	protected ValuesManager vm;

	protected DynamicForm formLegenda;
	protected DynamicForm formAssegnazione;
	
	protected AssegnazioneItem assegnazioneItem;
		
	protected ButtonItem confermaButton;
	protected ButtonItem annullaButton;

	public AssegnaUdAdAltroUfficioPopup(final Record pListRecord) {
			
		super("assegnazione", true);

		_window = this;
		
		vm = new ValuesManager();

		setTitle("Assegnazione ad altro ufficio");
		
		setAutoCenter(true);

		settingsMenu.removeItem(separatorMenuItem);
		settingsMenu.removeItem(autoSearchMenuItem);

		formAssegnazione = new DynamicForm();
		formAssegnazione.setValuesManager(vm);
		formAssegnazione.setKeepInParentRect(true);
		formAssegnazione.setWidth100();
		formAssegnazione.setHeight100();
		formAssegnazione.setNumCols(7);
		formAssegnazione.setColWidths(10, 10, 10, 10, 10, "*", "*");
		formAssegnazione.setCellPadding(2);
		formAssegnazione.setWrapItemTitles(false);
		
		assegnazioneItem = new AssegnazioneItem() {
			
			@Override
			public String getTipoAssegnatari() {
				return "UO";
			}
			
			@Override
			public String getFlgSoloValide() {
				return "1";
			}
			
			@Override
			public String getIdUdProtocollo() {
				return pListRecord != null ? pListRecord.getAttribute("idUdFolder") : null;				
			}
			
			@Override
			public String getSupportoOriginaleProt() {
				if(getCodSupportoOrig() != null) {
					if("C".equals(getCodSupportoOrig())) return "cartaceo";
					if("D".equals(getCodSupportoOrig())) return "digitale";
					if("M".equals(getCodSupportoOrig())) return "misto";
				}
				return null;
			}
			
		};		
		assegnazioneItem.setName("listaAssegnazioni");
		assegnazioneItem.setShowTitle(false);
		assegnazioneItem.setTitleStyle(it.eng.utility.Styles.formTitleBold);
		assegnazioneItem.setCanEdit(true);
		assegnazioneItem.setColSpan(5);
		assegnazioneItem.setFlgUdFolder("U");
		assegnazioneItem.setNotReplicable(true);
		assegnazioneItem.setFlgSenzaLD(true);
		assegnazioneItem.setAttribute("obbligatorio", true);
		
		formAssegnazione.setFields(assegnazioneItem);	
		
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
						
						if (validate()) {
							Record record = new Record(vm.getValues());
							record.setAttribute("listaAssegnazioni", formAssegnazione.getValueAsRecordList("listaAssegnazioni"));
							onClickOkButton(record, new DSCallback() {
	
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
		
		if (AurigaLayout.getParametroDBAsBoolean("ATTIVA_VIS_ICONA_TIPO_UO")) {
			if (AurigaLayout.getParametroDBAsBoolean("ATTIVA_LEGENDA_DIN_TIPO_UO")) {
				LegendaDinamicaPanel legendaPanel = new LegendaDinamicaPanel();
				layout.addMember(legendaPanel);
			} else {
				buildFormLegenda();
				layout.addMember(formLegenda);
			}
		}

		DetailSection detailSectionAssegnazione = new DetailSection("Destinatario", true, true, true, formAssegnazione);
		
		layout.addMember(detailSectionAssegnazione);
		
		VLayout spacerLayout = new VLayout();
		spacerLayout.setHeight100();
		spacerLayout.setWidth100();
		layout.addMember(spacerLayout);

		portletLayout.addMember(layout);
		portletLayout.addMember(_buttons);

		setBody(portletLayout);

		setIcon("archivio/assegna.png");
	}

	private void buildFormLegenda() {
		
		formLegenda = new DynamicForm();
		formLegenda.setKeepInParentRect(true);
		formLegenda.setCellPadding(5);
		formLegenda.setWrapItemTitles(false);

		StaticTextItem tipoUOImage = new StaticTextItem("iconaStatoConsolidamento");
		tipoUOImage.setShowValueIconOnly(true);
		tipoUOImage.setShowTitle(false);
		tipoUOImage.setValueIconWidth(600);
		tipoUOImage.setValueIconHeight(60);
		tipoUOImage.setAlign(Alignment.LEFT);
		Map<String, String> valueIcons = new HashMap<String, String>();
		valueIcons.put("1", "organigramma/legenda_uo.png");
		tipoUOImage.setValueIcons(valueIcons);
		tipoUOImage.setDefaultValue("1");
		tipoUOImage.setDefaultIconSrc("organigramma/legenda_uo.png");

		formLegenda.setItems(tipoUOImage);
	}
	
	public boolean validate() {
		return assegnazioneItem.validate();
	}	
	
	public boolean isDocCartaceo() {
		return getCodSupportoOrig() != null && "C".equals(getCodSupportoOrig());		
	}
	
	public String getCodSupportoOrig() {
		return null;
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
	
}