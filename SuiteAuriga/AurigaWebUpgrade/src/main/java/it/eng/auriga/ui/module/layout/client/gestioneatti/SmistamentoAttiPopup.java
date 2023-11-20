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
import com.smartgwt.client.types.FieldType;
import com.smartgwt.client.types.Overflow;
import com.smartgwt.client.widgets.Button;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.ButtonItem;
import com.smartgwt.client.widgets.form.fields.FormItem;
import com.smartgwt.client.widgets.form.fields.HiddenItem;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.layout.HStack;
import com.smartgwt.client.widgets.layout.VLayout;

import it.eng.auriga.ui.module.layout.client.AurigaLayout;
import it.eng.auriga.ui.module.layout.client.i18n.I18NUtil;
import it.eng.auriga.ui.module.layout.client.organigramma.LegendaDinamicaPanel;
import it.eng.utility.ui.module.core.client.datasource.GWTRestDataSource;
import it.eng.utility.ui.module.core.client.datasource.SelectGWTRestDataSource;
import it.eng.utility.ui.module.core.shared.message.MessageBean;
import it.eng.utility.ui.module.core.shared.message.MessageType;
import it.eng.utility.ui.module.layout.client.Layout;
import it.eng.utility.ui.module.layout.client.common.items.SelectItemWithDisplay;
import it.eng.utility.ui.module.layout.client.common.items.StaticTextItem;
import it.eng.utility.ui.module.layout.client.portal.ModalWindow;

public abstract class SmistamentoAttiPopup extends ModalWindow {

	protected SmistamentoAttiPopup _window;
	protected DynamicForm _form;
	private DynamicForm formImage;

	public DynamicForm getForm() {
		return _form;
	}

	protected UfficioLiquidatoreItem ufficioLiquidatoreItem;
	protected SmistamentoAttiItem smistamentoItem;
	protected SelectTreeSmistamentoAttiRagioneriaItem smistamentoRagioneriaItem;
	protected HiddenItem assegnatarioSmistamentoRagioneriaItem;
	protected HiddenItem desSmistamentoRagioneriaItem;
	protected SelectItemWithDisplay smistamentoGruppiItem;

	protected ButtonItem confermaButton;
	protected ButtonItem annullaButton;

	public SmistamentoAttiPopup(Record pListRecord) {

		super("smistamento_atto", true);

		_window = this;

		String idProcedimento = pListRecord != null ? pListRecord.getAttribute("idProcedimento") : null;
		String estremiProposta = (pListRecord != null && pListRecord.getAttribute("numeroProposta") != null) ? pListRecord.getAttribute("numeroProposta") : "";
		
		String title = null;
		if (!"".equals(estremiProposta)) {
			title = "Compila dati smistamento atto " + estremiProposta;
		} else {
			title = "Compila dati smistamento atti";
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
		
		// Smistamento da organigramma
		smistamentoItem = new SmistamentoAttiItem(idProcedimento) {
			
			@Override
			public Boolean getShowRemoveButton() {
				return false;
			};
		};
		smistamentoItem.setName("listaSmistamento");
		if(AurigaLayout.getParametroDBAsBoolean("ATTIVA_ASS_RAG_A_GRUPPI")) {
			smistamentoItem.setTitle("Assegnatario (da organigramma)");
		} else {
			smistamentoItem.setTitle("Assegnatario");
		}
		smistamentoItem.setTitleStyle(it.eng.utility.Styles.formTitleBold);
		smistamentoItem.setCanEdit(true);
		smistamentoItem.setColSpan(4);
		smistamentoItem.setNotReplicable(true);
		
		// Smistamento GRUPPI
		SelectGWTRestDataSource gruppoDS = new SelectGWTRestDataSource("LoadComboGruppoSmistamentoAttiRagDataSource", "idGruppo", FieldType.TEXT, new String[] { "codiceRapidoGruppo", "nomeGruppo" }, true);		
		smistamentoGruppiItem = new SelectItemWithDisplay("smistamentoGruppi", gruppoDS) {

			@Override
			public void onOptionClick(Record record) {
				super.onOptionClick(record);
				
			}

			@Override
			protected void clearSelect() {
				super.clearSelect();
				_form.setValue("smistamentoGruppi", "");
			}
			
			@Override
			public void setValue(String value) {
				super.setValue(value);
				if (value == null || "".equals(value)) {
					_form.setValue("smistamentoGruppi", "");
				}
            }
			
			@Override
			protected ListGrid builPickListProperties() {
				// per non far vedere il filtro della select
				ListGrid gruppoItemPickListProperties = super.builPickListProperties();	
				gruppoItemPickListProperties.setShowHeader(false);
				gruppoItemPickListProperties.setShowFilterEditor(false); 
				return gruppoItemPickListProperties;				
			}
		};
		smistamentoGruppiItem.setTitle("Assegnatario (lista di distribuzione)");		
		smistamentoGruppiItem.setAutoFetchData(false);
		smistamentoGruppiItem.setFetchMissingValues(true);			
		ListGridField codiceRapidoGruppoField = new ListGridField("codiceRapidoGruppo", I18NUtil.getMessages().protocollazione_detail_codRapidoItem_title());
		codiceRapidoGruppoField.setWidth(70);
		ListGridField nomeGruppoField = new ListGridField("nomeGruppo", I18NUtil.getMessages().protocollazione_detail_nomeItem_title());
		nomeGruppoField.setWidth("*");
		smistamentoGruppiItem.setPickListFields(codiceRapidoGruppoField, nomeGruppoField);
		smistamentoGruppiItem.setValueField("idGruppo");
		smistamentoGruppiItem.setWidth(500);
		smistamentoGruppiItem.setClearable(true);
		smistamentoGruppiItem.setShowIcons(true);		
		smistamentoGruppiItem.setTitleStyle(it.eng.utility.Styles.formTitleBold);
		smistamentoGruppiItem.setCanEdit(true);
		smistamentoGruppiItem.setColSpan(4);

		// Smistamento ufficioLiquidatore
		ufficioLiquidatoreItem = new UfficioLiquidatoreItem() {
			@Override
			public Boolean getShowRemoveButton() {
				return false;
			};
		};
		ufficioLiquidatoreItem.setName("listaUfficioLiquidatore");
		ufficioLiquidatoreItem.setTitle("Ufficio liquidatore");
		ufficioLiquidatoreItem.setTitleStyle(it.eng.utility.Styles.formTitleBold);
		ufficioLiquidatoreItem.setCanEdit(true);
		ufficioLiquidatoreItem.setColSpan(5);
		ufficioLiquidatoreItem.setNotReplicable(true);
		
		List<FormItem> fields = new ArrayList<FormItem>();
				
		if(AurigaLayout.isAttivoSmistamentoAttiRagioneria()) {
				
			assegnatarioSmistamentoRagioneriaItem = new HiddenItem("assegnatarioSmistamentoRagioneria");
			desSmistamentoRagioneriaItem = new HiddenItem("desSmistamentoRagioneria");
			
			smistamentoRagioneriaItem = new SelectTreeSmistamentoAttiRagioneriaItem("LoadComboSmistamentoAttiRagioneriaDataSource"){
				
				@Override
				public void onOptionClick(Record record) {
					super.onOptionClick(record);
					_form.setValue("assegnatarioSmistamentoRagioneria", record.getAttribute("assegnatario"));	
					_form.setValue("desSmistamentoRagioneria", record.getAttribute("nome"));		
				}

				@Override
				public void setValue(String value) {
					super.setValue(value);
					if (value == null || "".equals(value)) {
						_form.clearValue("smistamentoRagioneria");
						_form.clearValue("assegnatarioSmistamentoRagioneria");	
						_form.clearValue("desSmistamentoRagioneria");		
					}
				}

				@Override
				protected void clearSelect() {
					super.clearSelect();
					_form.clearValue("smistamentoRagioneria");	
					_form.clearValue("assegnatarioSmistamentoRagioneria");
					_form.clearValue("desSmistamentoRagioneria");		
				};
			};
			smistamentoRagioneriaItem.setLeft(2);
			smistamentoRagioneriaItem.setTitleStyle(it.eng.utility.Styles.formTitleBold);
			if(AurigaLayout.isAttivoSmistamentoAttiGruppi()) {
				smistamentoRagioneriaItem.setTitle("Assegnatario (da organigramma)");
			} else {
				smistamentoRagioneriaItem.setTitle("Assegnatario");
			}
			smistamentoRagioneriaItem.setName("smistamentoRagioneria");
			smistamentoRagioneriaItem.setColSpan(5);
			smistamentoRagioneriaItem.setWidth(500);
			smistamentoRagioneriaItem.setAlign(Alignment.LEFT);
			smistamentoRagioneriaItem.setClearable(true);
			smistamentoRagioneriaItem.setCanEdit(true);// Va messo dopo setClearable(true) per poter mostrare il bottone di cancellazione
			
			if(AurigaLayout.isAttivoClienteCOTO()){
				fields.add(ufficioLiquidatoreItem);
			}
			
			fields.add(assegnatarioSmistamentoRagioneriaItem);
			fields.add(desSmistamentoRagioneriaItem);
			fields.add(smistamentoRagioneriaItem);
			
			if(AurigaLayout.isAttivoSmistamentoAttiGruppi()) {
				fields.add(smistamentoGruppiItem);
			}

		}  else {
			
			if(AurigaLayout.isAttivoClienteCOTO()){
				fields.add(ufficioLiquidatoreItem);
			}
			
			fields.add(smistamentoItem);
			
			if(AurigaLayout.isAttivoSmistamentoAttiGruppi()) {
				fields.add(smistamentoGruppiItem);
			}
		}
		
		_form.setFields(fields.toArray(new FormItem[fields.size()]));

		Button confermaButton = new Button("Smista");
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

		setIcon("archivio/assegna.png");
		
		if(pListRecord != null) {
			GWTRestDataSource lProtocolloDataSource = new GWTRestDataSource("ProtocolloDataSource");
			lProtocolloDataSource.addParam("flgSoloAbilAzioni", "1");
			lProtocolloDataSource.addParam("idProcess", idProcedimento);
			lProtocolloDataSource.addParam("taskName", "#SMISTAMENTO");
			Record lRecordToLoad = new Record();
			lRecordToLoad.setAttribute("idUd", pListRecord.getAttribute("unitaDocumentariaId"));
			lProtocolloDataSource.getData(lRecordToLoad, new DSCallback() {

				@Override
				public void execute(DSResponse response, Object rawData, DSRequest request) {
					
					if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
						final Record detailRecord = response.getData()[0];
						_window.editRecordPerModificaInvio(prepareRecordSmistamentoAtti(detailRecord));		
						_window.show();
					}
				}
			});
		} else {
			_window.editRecordPerModificaInvio(prepareRecordSmistamentoAtti(null));		
			_window.show();
		}
	}
	
	private Record prepareRecordSmistamentoAtti(Record detailRecord) {
		// Preparo il record per settare i valori a maschera
		Record lRecordSmistamentoAtti = new Record();				
		if(AurigaLayout.isAttivoClienteCOTO()) {
			/*
			#IdGruppoLiquidatori (id gruppo interno)
			#NomeGruppoLiquidatori
			#CodGruppoLiquidatori
			*/
			RecordList lRecordListUfficioLiquidatore = new RecordList();
			Record lRecordUfficioLiquidatore = new Record();
			if(detailRecord != null) {
				// se sono nello smistamento di un singolo atto
				if(detailRecord.getAttribute("idGruppoLiquidatori") != null && !"".equals(detailRecord.getAttribute("idGruppoLiquidatori"))) {
					lRecordUfficioLiquidatore.setAttribute("ufficioLiquidatore", detailRecord.getAttribute("idGruppoLiquidatori"));
					lRecordUfficioLiquidatore.setAttribute("nomeUfficioLiquidatore", detailRecord.getAttribute("nomeGruppoLiquidatori"));
					lRecordUfficioLiquidatore.setAttribute("codRapidoUfficioLiquidatore", detailRecord.getAttribute("codGruppoLiquidatori"));
				}
			} else {
				// se sono nello smistamento massivo
			}
			lRecordListUfficioLiquidatore.add(lRecordUfficioLiquidatore);
			lRecordSmistamentoAtti.setAttribute("listaUfficioLiquidatore", lRecordListUfficioLiquidatore);			
		}
		if(AurigaLayout.isAttivoSmistamentoAttiGruppi()) {
						
			/*
			#IdGruppoRagioneria (id gruppo interno)
			*/
			if(detailRecord != null) {
				// se sono nello smistamento di un singolo atto
				if(detailRecord.getAttribute("idGruppoRagioneria") != null && !"".equals(detailRecord.getAttribute("idGruppoRagioneria"))) {
					lRecordSmistamentoAtti.setAttribute("smistamentoGruppi", detailRecord.getAttribute("idGruppoRagioneria"));	
					lRecordSmistamentoAtti.setAttribute("codiceRapidoGruppo", detailRecord.getAttribute("codiceRapidoGruppoRagioneria"));	
					lRecordSmistamentoAtti.setAttribute("nomeGruppo", detailRecord.getAttribute("nomeGruppoRagioneria"));	
				}
			} else {
				// se sono nello smistamento massivo
			}
		}
		if(AurigaLayout.isAttivoSmistamentoAttiRagioneria()) {
			
			if(detailRecord != null) {
			// se sono nello smistamento di un singolo atto
				if(detailRecord.getAttribute("idAssegnatarioProcesso") != null && !"".equals(detailRecord.getAttribute("idAssegnatarioProcesso"))) {
					lRecordSmistamentoAtti.setAttribute("smistamentoRagioneria", detailRecord.getAttribute("idAssegnatarioProcesso"));			
					lRecordSmistamentoAtti.setAttribute("assegnatarioSmistamentoRagioneria", detailRecord.getAttribute("idAssegnatarioProcesso"));
					lRecordSmistamentoAtti.setAttribute("desSmistamentoRagioneria", detailRecord.getAttribute("desAssegnatarioProcesso"));
				}
			} else {
				// se sono nello smistamento massivo
			}

		} else {
			/*
			#IdAssegnatarioProcesso (SV o UO + id -> UO134, SV145 ecc)
			#DesAssegnatarioProcesso                        
			#NriLivelliAssegnatarioProcesso (codice rapido)
			 */
			RecordList lRecordListSmistamento = new RecordList();
			Record lRecordSmistamento = new Record();
			if(detailRecord != null) {				
				// se sono nello smistamento di un singolo atto
				if(detailRecord.getAttribute("idAssegnatarioProcesso") != null && !"".equals(detailRecord.getAttribute("idAssegnatarioProcesso"))) {
					lRecordSmistamento.setAttribute("codRapido", detailRecord.getAttribute("nriLivelliAssegnatarioProcesso"));
					lRecordSmistamento.setAttribute("descrizione", detailRecord.getAttribute("desAssegnatarioProcesso"));
					lRecordSmistamento.setAttribute("organigramma", detailRecord.getAttribute("idAssegnatarioProcesso"));
					if (detailRecord.getAttribute("idAssegnatarioProcesso").startsWith("UO")) {
						lRecordSmistamento.setAttribute("typeNodo", "UO");
						lRecordSmistamento.setAttribute("idUo", detailRecord.getAttribute("idAssegnatarioProcesso").substring(2));
					} else if (detailRecord.getAttribute("idAssegnatarioProcesso").startsWith("SV")){
						lRecordSmistamento.setAttribute("typeNodo", "SV");
						lRecordSmistamento.setAttribute("idUo", detailRecord.getAttribute("idAssegnatarioProcesso").substring(2));
					}
				} else if (AurigaLayout.getParametroDBAsBoolean("DIM_ORGANIGRAMMA_NONSTD")) {
					lRecordSmistamento.setAttribute("codRapido", AurigaLayout.getCodRapidoOrganigramma());
				}				
			} else {
				// se sono nello smistamento massivo
				if (AurigaLayout.getParametroDBAsBoolean("DIM_ORGANIGRAMMA_NONSTD")) {
					lRecordSmistamento.setAttribute("codRapido", AurigaLayout.getCodRapidoOrganigramma());
				}
			}
			lRecordListSmistamento.add(lRecordSmistamento);
			lRecordSmistamentoAtti.setAttribute("listaSmistamento", lRecordListSmistamento);			
		}
		return lRecordSmistamentoAtti;
	}
	
	public boolean validate() {
		
		Record recorValidate = _form.getValuesAsRecord();
		boolean isAssegnatarioNonValorizzato = false;
		Boolean valid = _form.validate();
		if(AurigaLayout.isAttivoSmistamentoAttiRagioneria()) {
			
			valid = smistamentoRagioneriaItem.validate() && valid;
			
			boolean isSmistamentoRagioneriaValid = isSmistamentoRagioneriaValorizzato(recorValidate);
			
			boolean isUfficioLiquidatoreValid = true;
			if(AurigaLayout.isAttivoClienteCOTO()){
				isUfficioLiquidatoreValid = isUfficioLiquidatoreValorizzato(recorValidate);
				valid = ufficioLiquidatoreItem.validate() && valid;
			}
		
			boolean isSmistamentoGruppiValid = true;
			if(AurigaLayout.isAttivoSmistamentoAttiGruppi()) {
				isSmistamentoGruppiValid = isSmistamentoGruppiValorizzato(recorValidate) && valid;
				valid = smistamentoGruppiItem.validate() && valid;
			} 		
			
			if(AurigaLayout.isAttivoClienteCOTO() && AurigaLayout.isAttivoSmistamentoAttiGruppi()) {
				if(!isSmistamentoRagioneriaValid && !isUfficioLiquidatoreValid && !isSmistamentoGruppiValid) {
					isAssegnatarioNonValorizzato = true;
				} 
			} else if(AurigaLayout.isAttivoClienteCOTO()) {
				if(!isSmistamentoRagioneriaValid && !isUfficioLiquidatoreValid) {
					isAssegnatarioNonValorizzato = true;
				} 
			} else if(AurigaLayout.isAttivoSmistamentoAttiGruppi()) {
				if(!isSmistamentoRagioneriaValid && !isSmistamentoGruppiValid) {
					isAssegnatarioNonValorizzato = true;
				} 
			} else {
				if(!isSmistamentoRagioneriaValid) {
					isAssegnatarioNonValorizzato = true;
				} 
			}
			
		} else {
			valid = smistamentoItem.validate() && valid;
			
			boolean isSmistamentoValid = isSmistamentoValorizzato(recorValidate);
			
			boolean isUfficioLiquidatoreValid = true;
			if(AurigaLayout.isAttivoClienteCOTO()){
				isUfficioLiquidatoreValid = isUfficioLiquidatoreValorizzato(recorValidate);
				valid = ufficioLiquidatoreItem.validate() && valid;
			}
			
			boolean isSmistamentoGruppiValid = true;
			if(AurigaLayout.isAttivoSmistamentoAttiGruppi()) {
				isSmistamentoGruppiValid = isSmistamentoGruppiValorizzato(recorValidate) && valid;
				valid = smistamentoGruppiItem.validate() && valid;
			} 
			
			if(AurigaLayout.isAttivoClienteCOTO() && AurigaLayout.isAttivoSmistamentoAttiGruppi()) {
				if(!isSmistamentoValid && !isUfficioLiquidatoreValid && !isSmistamentoGruppiValid) {
					isAssegnatarioNonValorizzato = true;
				} 
			} else if(AurigaLayout.isAttivoClienteCOTO()) {
				if(!isSmistamentoValid && !isUfficioLiquidatoreValid) {
					isAssegnatarioNonValorizzato = true;
				} 
			} else if(AurigaLayout.isAttivoSmistamentoAttiGruppi()) {
				if(!isSmistamentoValid && !isSmistamentoGruppiValid) {
					isAssegnatarioNonValorizzato = true;
				} 
			} else {
				if(!isSmistamentoValid) {
					isAssegnatarioNonValorizzato = true;
				} 
			}
		
		}
		
		if(isAssegnatarioNonValorizzato) {
			valid = false;
			Layout.addMessage(new MessageBean("Attenzione occorre valorizzare almeno un assegnatario","", MessageType.WARNING));
		}
		
		return valid;
	}
	
	
	private boolean isUfficioLiquidatoreValorizzato(Record record) {
		boolean verify = false;
		if(record != null && record.getAttributeAsRecordList("listaUfficioLiquidatore") != null &&
				!record.getAttributeAsRecordList("listaUfficioLiquidatore").isEmpty()) {
			for(int i=0; i < record.getAttributeAsRecordList("listaUfficioLiquidatore").getLength(); i++) {
				Record item = record.getAttributeAsRecordList("listaUfficioLiquidatore").get(i);
				if(item != null && item.getAttributeAsString("ufficioLiquidatore") != null && 
						!"".equalsIgnoreCase(item.getAttributeAsString("ufficioLiquidatore"))) {
					verify = true;
					return verify;
				}
			}
		}
		
		return verify;
	}
	
	private boolean isSmistamentoValorizzato(Record record) {
		boolean verify = false;
		if(record != null && record.getAttributeAsRecordList("listaSmistamento") != null &&
				!record.getAttributeAsRecordList("listaSmistamento").isEmpty()) {
			for(int i=0; i < record.getAttributeAsRecordList("listaSmistamento").getLength(); i++) {
				Record item = record.getAttributeAsRecordList("listaSmistamento").get(i);
				if(item != null && item.getAttributeAsString("idUo") != null && 
						!"".equalsIgnoreCase(item.getAttributeAsString("idUo"))) {
					verify = true;
					return verify;
				}
			}
		}		
		return verify;
	}
	
	private boolean isSmistamentoRagioneriaValorizzato(Record record) {
		boolean verify = false;
		if(record != null && record.getAttributeAsString("smistamentoRagioneria") != null &&
			!"".equalsIgnoreCase(record.getAttributeAsString("smistamentoRagioneria"))) {
				verify = true;
				return verify;
		}		
		return verify;
	}
	
	private boolean isSmistamentoGruppiValorizzato(Record record) {
		boolean verify = false;
		if(record != null && record.getAttributeAsString("smistamentoGruppi") != null &&
			!"".equalsIgnoreCase(record.getAttributeAsString("smistamentoGruppi"))) {
				verify = true;
				return verify;
		}
		return verify;
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
		manageLoadSelectInEditRecord(record, smistamentoRagioneriaItem, "smistamentoRagioneria",  new String[] {"desSmistamentoRagioneria"}, null, "idNode");
		manageLoadSelectInEditRecord(record, smistamentoGruppiItem, "smistamentoGruppi",  new String[] { "codiceRapidoGruppo", "nomeGruppo"}, "**", "idGruppo");		
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
