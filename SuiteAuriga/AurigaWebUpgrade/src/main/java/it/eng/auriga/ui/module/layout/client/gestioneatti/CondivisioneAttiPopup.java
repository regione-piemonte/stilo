/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.smartgwt.client.data.DSCallback;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.data.RecordList;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.Overflow;
import com.smartgwt.client.widgets.Button;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.ButtonItem;
import com.smartgwt.client.widgets.form.fields.FormItem;
import com.smartgwt.client.widgets.layout.HStack;
import com.smartgwt.client.widgets.layout.VLayout;

import it.eng.auriga.ui.module.layout.client.AurigaLayout;
import it.eng.auriga.ui.module.layout.client.organigramma.LegendaDinamicaPanel;
import it.eng.auriga.ui.module.layout.client.protocollazione.CondivisioneItem;
import it.eng.utility.ui.module.core.client.datasource.GWTRestDataSource;
import it.eng.utility.ui.module.core.shared.message.MessageBean;
import it.eng.utility.ui.module.core.shared.message.MessageType;
import it.eng.utility.ui.module.layout.client.Layout;
import it.eng.utility.ui.module.layout.client.common.items.StaticTextItem;
import it.eng.utility.ui.module.layout.client.portal.ModalWindow;

public abstract class CondivisioneAttiPopup extends ModalWindow {

	protected CondivisioneAttiPopup _window;
	protected DynamicForm _form;
	private DynamicForm formImage;

	public DynamicForm getForm() {
		return _form;
	}

	protected CondivisioneItem condivisioneItem;


	protected ButtonItem confermaButton;
	protected ButtonItem annullaButton;
	
	private boolean isMassiva;

	public CondivisioneAttiPopup(final Record pListRecord) {

		super("condivisione_atto", true);

		_window = this;
		
		isMassiva = pListRecord == null;
		
		String estremiProposta = (pListRecord != null && pListRecord.getAttribute("numeroProposta") != null) ? pListRecord.getAttribute("numeroProposta") : "";
		
		String title = null;
		if (!"".equals(estremiProposta)) {
			title = "Seleziona destinatari condivisione proposta / atto " + estremiProposta;
		} else {
			title = "Seleziona destinatari condivisione proposta / atto";
		}

		setTitle(title);

		setAutoCenter(true);

		settingsMenu.removeItem(separatorMenuItem);
		settingsMenu.removeItem(autoSearchMenuItem);

		_form = new DynamicForm();
		_form.setKeepInParentRect(true);
		_form.setWidth100();
		_form.setHeight100();
		_form.setNumCols(5);
		_form.setColWidths(1, 1, 1, 1, "*");
		_form.setCellPadding(5);
		_form.setWrapItemTitles(false);
		
		condivisioneItem = new CondivisioneItem() {
			
			@Override
			public String getFinalitaOrganigrammaLookup() {
				return super.getFinalitaOrganigrammaLookup();
			}
			
			@Override
			public String getFinalitaLoadComboOrganigramma() {
				return super.getFinalitaLoadComboOrganigramma();
			}
			
			@Override
			public boolean isDimOrganigrammaNonStd() {
				return super.isDimOrganigrammaNonStd();
			}
			
			@Override
			public boolean showOpzioniInvioCondivisioneButton() {
				return false;
			}
			
			@Override
			public boolean showPreferiti() {
				return false;
			}
			
			@Override
			public String getIdUdProtocollo() {
				return pListRecord != null ? pListRecord.getAttribute("unitaDocumentariaId") : null;				
			}
		};
		condivisioneItem.setName("listaCondivisione");		
		condivisioneItem.setShowTitle(false);
		condivisioneItem.setCanEdit(true);
		condivisioneItem.setColSpan(4);
		condivisioneItem.setFlgUdFolder("U");
		if(isMassiva) {
			condivisioneItem.setAttribute("obbligatorio", true);
		}

		List<FormItem> fields = new ArrayList<FormItem>();		
		fields.add(condivisioneItem);
		_form.setFields(fields.toArray(new FormItem[fields.size()]));

		Button confermaButton = new Button(isMassiva ? "Condividi" : "Salva");
		confermaButton.setIcon("ok.png");
		confermaButton.setIconSize(16);
		confermaButton.setAutoFit(false);
		confermaButton.addClickHandler(new com.smartgwt.client.widgets.events.ClickHandler() {

			@Override
			public void onClick(com.smartgwt.client.widgets.events.ClickEvent event) {
				
				if (validate()) {
					onClickOkButton(new DSCallback() {

						@Override
						public void execute(DSResponse response, Object rawData, DSRequest request) {
							
							_window.markForDestroy();
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
				LegendaDinamicaPanel leg = new LegendaDinamicaPanel();
				layout.addMember(leg);
			} else {
				buildLegendImageUO();
				layout.addMember(formImage);
			}
		}

		layout.addMember(_form);

		portletLayout.addMember(layout);
		portletLayout.addMember(_buttons);

		setBody(portletLayout);

		setIcon("archivio/condividi.png");
		
		if(pListRecord != null) {
			GWTRestDataSource lProtocolloDataSource = new GWTRestDataSource("ProtocolloDataSource");
			lProtocolloDataSource.addParam("flgSoloAbilAzioni", "1");
			lProtocolloDataSource.addParam("idProcess", pListRecord.getAttribute("idProcedimento"));
			lProtocolloDataSource.addParam("taskName", "#CONDIVISIONE");
			Record lRecordToLoad = new Record();
			lRecordToLoad.setAttribute("idUd", pListRecord.getAttribute("unitaDocumentariaId"));
			lProtocolloDataSource.getData(lRecordToLoad, new DSCallback() {

				@Override
				public void execute(DSResponse response, Object rawData, DSRequest request) {
					
					if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
						final Record detailRecord = response.getData()[0];
						_window.editRecordPerModificaInvio(prepareRecordCondivisioneAtti(detailRecord));	
						_window.show();
					}
				}
			});
		} else {
			_window.editRecordPerModificaInvio(prepareRecordCondivisioneAtti(null));
			_window.show();
		}
	}
	
	private Record prepareRecordCondivisioneAtti(Record detailRecord) {
		// Preparo il record per settare i valori a maschera
		Record lRecordCondivisioneAtti = new Record();						
		RecordList lRecordListCondivisione = new RecordList();
		if(detailRecord != null) {				
			// se sono nella condivisione di un singolo atto
			if(detailRecord.getAttributeAsRecordList("destinatariInvioCCProcesso") != null && detailRecord.getAttributeAsRecordList("destinatariInvioCCProcesso").getLength() > 0) {
				for(int i = 0; i < detailRecord.getAttributeAsRecordList("destinatariInvioCCProcesso").getLength(); i++) {		
					Record recordDestInvioCCProcesso = detailRecord.getAttributeAsRecordList("destinatariInvioCCProcesso").get(i);
					Record lRecordCondivisione = new Record();					
					if(recordDestInvioCCProcesso.getAttribute("idDestinatario") != null && !"".equals(recordDestInvioCCProcesso.getAttribute("idDestinatario"))) {
						lRecordCondivisione.setAttribute("codRapido", recordDestInvioCCProcesso.getAttribute("codDestinatario"));
						lRecordCondivisione.setAttribute("descrizione", recordDestInvioCCProcesso.getAttribute("desDestinatario"));
						if (recordDestInvioCCProcesso.getAttribute("idDestinatario").startsWith("UO")) {
							lRecordCondivisione.setAttribute("tipo", "SV;UO");
							lRecordCondivisione.setAttribute("typeNodo", "UO");
							lRecordCondivisione.setAttribute("idUo", recordDestInvioCCProcesso.getAttribute("idDestinatario").substring(2));
							lRecordCondivisione.setAttribute("organigramma", recordDestInvioCCProcesso.getAttribute("idDestinatario"));
						} else if (recordDestInvioCCProcesso.getAttribute("idDestinatario").startsWith("SV")){
							lRecordCondivisione.setAttribute("tipo", "SV;UO");
							lRecordCondivisione.setAttribute("typeNodo", "SV");
							lRecordCondivisione.setAttribute("idUo", recordDestInvioCCProcesso.getAttribute("idDestinatario").substring(2));
							lRecordCondivisione.setAttribute("organigramma", recordDestInvioCCProcesso.getAttribute("idDestinatario"));
						} else if (recordDestInvioCCProcesso.getAttribute("idDestinatario").startsWith("LD")){
							lRecordCondivisione.setAttribute("tipo", "LD");
							lRecordCondivisione.setAttribute("typeNodo", "");
							lRecordCondivisione.setAttribute("gruppo", recordDestInvioCCProcesso.getAttribute("idDestinatario").substring(2));
						}
					} else if (AurigaLayout.getParametroDBAsBoolean("DIM_ORGANIGRAMMA_NONSTD")) {
						lRecordCondivisione.setAttribute("codRapido", AurigaLayout.getCodRapidoOrganigramma());
					}	
					lRecordListCondivisione.add(lRecordCondivisione);	
				}
			} else {
				Record lRecordCondivisione = new Record();
				if (AurigaLayout.getParametroDBAsBoolean("DIM_ORGANIGRAMMA_NONSTD")) {
					lRecordCondivisione.setAttribute("codRapido", AurigaLayout.getCodRapidoOrganigramma());
				}
				lRecordListCondivisione.add(lRecordCondivisione);	
			}					
		} else {
			// se sono nella condivisione massiva
			Record lRecordCondivisione = new Record();
			if (AurigaLayout.getParametroDBAsBoolean("DIM_ORGANIGRAMMA_NONSTD")) {
				lRecordCondivisione.setAttribute("codRapido", AurigaLayout.getCodRapidoOrganigramma());
			}
			lRecordListCondivisione.add(lRecordCondivisione);			
		}
		lRecordCondivisioneAtti.setAttribute("listaCondivisione", lRecordListCondivisione);			
		
		return lRecordCondivisioneAtti;
	}
	
	public boolean validate() {
		
		Record recorValidate = _form.getValuesAsRecord();
		boolean isDestInvioCCNonValorizzato = false;
		Boolean valid = _form.validate();
		
		valid = condivisioneItem.validate() && valid;
		
		boolean isCondivisioneValid = isCondivisioneValorizzata(recorValidate);
		
		if(!isCondivisioneValid) {
			isDestInvioCCNonValorizzato = true;			
		}
		
		if(isMassiva && isDestInvioCCNonValorizzato) {
			valid = false;
			Layout.addMessage(new MessageBean("Attenzione occorre valorizzare almeno un destinatario","", MessageType.WARNING));
		}
		
		return valid;
	}
	
	private boolean isCondivisioneValorizzata(Record record) {		
		if(record != null && record.getAttributeAsRecordList("listaCondivisione") != null &&
				!record.getAttributeAsRecordList("listaCondivisione").isEmpty()) {
			for(int i=0; i < record.getAttributeAsRecordList("listaCondivisione").getLength(); i++) {
				Record item = record.getAttributeAsRecordList("listaCondivisione").get(i);
				if(item.getAttributeAsString("tipo") != null && "LD".equals(item.getAttributeAsString("tipo"))) {
					if(item.getAttributeAsString("gruppo") != null && !"".equalsIgnoreCase(item.getAttributeAsString("gruppo"))) {
						return true;
					}	
				} else if(item.getAttributeAsString("idUo") != null && !"".equalsIgnoreCase(item.getAttributeAsString("idUo"))) {
					return true;
				}
			}
		}		
		return false;
	}

	private void buildLegendImageUO() {
		formImage = new DynamicForm();
		formImage.setKeepInParentRect(true);
		formImage.setCellPadding(5);
		formImage.setWrapItemTitles(false);

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

		formImage.setItems(tipoUOImage);
	}

	public abstract void onClickOkButton(DSCallback callback);

	public void editRecordPerModificaInvio(Record record) {
		_form.editRecord(record);
		markForRedraw();
	}
	
	public void manageLoadSelectInEditRecord(Record record, FormItem item, String keyFieldName, String[] displayFieldNames, String separator, String paramCIToAdd) {
		
		if (item != null && record != null) {
			String key = keyFieldName != null ? record.getAttribute(keyFieldName) : null;
			String display = null; 
			if(displayFieldNames != null && displayFieldNames.length > 0) {
				display = displayFieldNames[0] != null ? record.getAttribute(displayFieldNames[0]) : "";
				if(displayFieldNames.length > 1) {
					for(int i = 1; i < displayFieldNames.length; i++) {
						display += separator + (displayFieldNames[i] != null ? record.getAttribute(displayFieldNames[i]) : "");	
					}
				}				
			}	
			if(paramCIToAdd != null && !"".equals(paramCIToAdd)) {
				if(item.getOptionDataSource() != null && (item.getOptionDataSource() instanceof GWTRestDataSource)) {
					GWTRestDataSource optionDS = (GWTRestDataSource) item.getOptionDataSource();
					if (key != null && !"".equals(key)) {
						optionDS.addParam(paramCIToAdd, key);
					} else {
						optionDS.addParam(paramCIToAdd, null);
					}
					item.setOptionDataSource(optionDS);
				}
			}									
			if (key != null && !"".equals(key)) {
				if (display != null && !"".equals(display)) {
					if (item.getValueMap() != null ) {
						if(!item.getValueMap().containsKey(key)){                                 
							item.getValueMap().put(key, display);
						}
					} else {
						LinkedHashMap<String, String> valueMap = new LinkedHashMap<String, String>();
						valueMap.put(key, display);
						item.setValueMap(valueMap);	
					}
				}	
				item.setValue(key); 
			}
		}
	}

}
