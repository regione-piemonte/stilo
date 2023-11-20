/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import com.smartgwt.client.data.DSCallback;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.data.RecordList;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.FieldType;
import com.smartgwt.client.types.Overflow;
import com.smartgwt.client.util.BooleanCallback;
import com.smartgwt.client.widgets.Button;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.FormItemHoverFormatter;
import com.smartgwt.client.widgets.form.ValuesManager;
import com.smartgwt.client.widgets.form.fields.ButtonItem;
import com.smartgwt.client.widgets.form.fields.FormItem;
import com.smartgwt.client.widgets.form.fields.HeaderItem;
import com.smartgwt.client.widgets.layout.HStack;
import com.smartgwt.client.widgets.layout.VLayout;

import it.eng.auriga.ui.module.layout.client.common.items.SelectItemValoriDizionario;
import it.eng.auriga.ui.module.layout.client.protocollazione.SelezionaUOItem;
import it.eng.utility.ui.module.core.client.datasource.GWTRestDataSource;
import it.eng.utility.ui.module.core.shared.message.MessageBean;
import it.eng.utility.ui.module.core.shared.message.MessageType;
import it.eng.utility.ui.module.layout.client.Layout;
import it.eng.utility.ui.module.layout.client.common.file.PrettyFileUploadItem;
import it.eng.utility.ui.module.layout.client.common.items.DateItem;
import it.eng.utility.ui.module.layout.client.common.items.ImgButtonItem;
import it.eng.utility.ui.module.layout.client.common.items.SelectItem;
import it.eng.utility.ui.module.layout.client.common.items.TitleItem;
import it.eng.utility.ui.module.layout.client.portal.ModalWindow;

/**
 * 
 * @author matzanin
 *
 */

public class RichiestaCampionePopup extends ModalWindow {

	protected RichiestaCampionePopup _window;
	protected BooleanCallback _callback = null;
	
	protected ValuesManager vm;
	
	protected DynamicForm richiestaCampioneForm;
	protected SelectItem tipologiaAttoItem;
	protected SelectItemValoriDizionario codiceAttoItem;
	protected SelectItem flgDeterminaAContrarreItem;
	protected SelezionaUOItem listaStrutturaProponenteItem;
	protected SelectItemValoriDizionario rangeImportoItem;
	protected DateItem dataInizioPeriodoRifItem;
	protected DateItem dataFinePeriodoRifItem;
	
	protected ButtonItem confermaButton;
	protected ButtonItem annullaButton;
		
	public RichiestaCampionePopup(Record record, BooleanCallback callback) {

		super("richiesta_campione", true);

		_window = this;
		_callback = callback;
		
		vm = new ValuesManager();		

		setTitle("Richiesta controllo a campione");		
		
		setAutoCenter(true);
		setHeight(300);
		setWidth(1000);
		setAlign(Alignment.CENTER);
		
		settingsMenu.removeItem(separatorMenuItem);
		settingsMenu.removeItem(autoSearchMenuItem);
		
		buildRichiestaCampioneForm();
					
		Button confermaButton = new Button("Conferma");
		confermaButton.setIcon("ok.png");
		confermaButton.setIconSize(16);
		confermaButton.setAutoFit(false);
		confermaButton.addClickHandler(new com.smartgwt.client.widgets.events.ClickHandler() {

			@Override
			public void onClick(com.smartgwt.client.widgets.events.ClickEvent event) {
				manageOnConfirmClick();
			}
		});

		Button annullaButton = new Button("Annulla");
		annullaButton.setIcon("annulla.png");
		annullaButton.setIconSize(16);
		annullaButton.setAutoFit(false);
		annullaButton.addClickHandler(new com.smartgwt.client.widgets.events.ClickHandler() {

			@Override
			public void onClick(com.smartgwt.client.widgets.events.ClickEvent event) {
				manageOnCloseClick();
			}
		});

		HStack _buttons = new HStack(5);
		_buttons.setHeight(30);
		_buttons.setAlign(Alignment.CENTER);
		_buttons.setPadding(5);
		_buttons.addMember(confermaButton);
		_buttons.addMember(annullaButton);	
		
		VLayout layout = new VLayout();
		layout.setHeight100();
		layout.setWidth100();
		layout.setOverflow(Overflow.AUTO);
		
		// Creo il VLAYOUT e gli aggiungo il TABSET 
		VLayout portletLayout = new VLayout();  
		portletLayout.setHeight100();
		portletLayout.setWidth100();
		portletLayout.setOverflow(Overflow.VISIBLE);
		
		layout.addMember(richiestaCampioneForm);
			
		VLayout spacerLayout = new VLayout();
		spacerLayout.setHeight100();
		spacerLayout.setWidth100();
		layout.addMember(spacerLayout);

		portletLayout.addMember(layout);		
		portletLayout.addMember(_buttons);
						
		setBody(portletLayout);
	
		setIcon("blank.png");
		
		if(record != null) {
			vm.editRecord(record);			
		}				
		
		setCanEdit(true);
	}
	
	public void setCanEdit(boolean canEdit) {
		for (DynamicForm form : vm.getMembers()) {
			setCanEdit(canEdit, form);
		}
	}

	public void setCanEdit(boolean canEdit, DynamicForm form) {
		if (form != null) {
			form.setEditing(canEdit);
			for (FormItem item : form.getFields()) {
				if (!(item instanceof HeaderItem) && !(item instanceof ImgButtonItem) && !(item instanceof TitleItem)) {
					item.setCanEdit(canEdit);
					item.redraw();
				}
				if (item instanceof ImgButtonItem || item instanceof PrettyFileUploadItem) {
					item.setCanEdit(canEdit);
					item.redraw();
				}
			}
		}
	}

	private void buildRichiestaCampioneForm() {
		
		richiestaCampioneForm = new DynamicForm();
		richiestaCampioneForm.setValuesManager(vm);
		richiestaCampioneForm.setWidth100();
		richiestaCampioneForm.setPadding(5);
		richiestaCampioneForm.setWrapItemTitles(false);
		richiestaCampioneForm.setNumCols(20);
		richiestaCampioneForm.setColWidths(1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, "*", "*");
		
	 	GWTRestDataSource tipologiaAttoDS = new GWTRestDataSource("LoadComboRicercaTipiAttiDataSource", "idTipoDocumento", FieldType.TEXT, true);		
	 	tipologiaAttoItem = new SelectItem("idTipoAtto", "Tipologia atto");
	 	tipologiaAttoItem.setStartRow(true);
		tipologiaAttoItem.setWidth(400);
		tipologiaAttoItem.setColSpan(8);
		tipologiaAttoItem.setPickListWidth(450);
		tipologiaAttoItem.setClearable(true);				
		tipologiaAttoItem.setOptionDataSource(tipologiaAttoDS);					
		tipologiaAttoItem.setDisplayField("descTipoDocumento");
		tipologiaAttoItem.setValueField("idTipoDocumento");	
		
		//TODO
		codiceAttoItem = new SelectItemValoriDizionario("codiceAtto", "Cod. atto", "MATERIA_NATURA_ATTO", true);
		codiceAttoItem.setStartRow(true);
		codiceAttoItem.setWidth(800);
		codiceAttoItem.setColSpan(16);
		codiceAttoItem.setHoverWidth(500);
		codiceAttoItem.setItemHoverFormatter(new FormItemHoverFormatter() {

			@Override
			public String getHoverHTML(FormItem item, DynamicForm form) {
				String itemValue = ((SelectItem) item).getValueAsString();
				String[] valori = itemValue != null ? itemValue.split(",") : new String[0];
				String hover = null;
				for(int i = 0; i < valori.length; i++) {
					if(hover == null) {
						hover = "";
					} else {
						hover += "<br/>";
					}
					hover += item.getDisplayValue(valori[i]);
				}
				return hover;
			}
		});
		codiceAttoItem.setDefaultToFirstOption(true);
		codiceAttoItem.setClearable(true);
		
		flgDeterminaAContrarreItem = new SelectItem("flgDeterminaAContrarre", "Determina a contrarre");
		flgDeterminaAContrarreItem.setStartRow(true);
		flgDeterminaAContrarreItem.setWidth(200);
		flgDeterminaAContrarreItem.setColSpan(4);
		flgDeterminaAContrarreItem.setAllowEmptyValue(true);
		flgDeterminaAContrarreItem.setValueMap("Si","No");
		
		//TODO da togliere?
		listaStrutturaProponenteItem = new SelezionaUOItem() {
			
			@Override
			public int getSelectItemOrganigrammaWidth() {
				return 596;
			}
			
			@Override
			protected VLayout creaVLayout() {
				VLayout lVLayout = super.creaVLayout();
				lVLayout.setWidth100();
				lVLayout.setPadding(11);
				lVLayout.setBorder("1px solid #a0a0a0");
//				lVLayout.setIsGroup(true);
//				lVLayout.setGroupTitle("<span class=\"" + it.eng.utility.Styles.formTitleBold + "\">&nbsp;&nbsp;Struttura proponente&nbsp;&nbsp;</span>");
//				lVLayout.setGroupLabelStyleName(null);
				return lVLayout;
			}
			
			@Override
			public Boolean getShowRemoveButton() {
				return true;
			}
		};
		listaStrutturaProponenteItem.setName("listaStrutturaProponente");
		listaStrutturaProponenteItem.setTitle("<span class=\"" + it.eng.utility.Styles.formTitleBold + "\">Struttura proponente</span>");
		listaStrutturaProponenteItem.setShowTitle(true);
		listaStrutturaProponenteItem.setColSpan(18);
		listaStrutturaProponenteItem.setNotReplicable(true);
			
		rangeImportoItem = new SelectItemValoriDizionario("rangeImporto", "Importo", "RANGE_IMPORTI_X_CAMPIONAMENTO");
		rangeImportoItem.setStartRow(true);
		rangeImportoItem.setWidth(200);
		rangeImportoItem.setColSpan(4);
		rangeImportoItem.setAllowEmptyValue(true);
		rangeImportoItem.setDefaultToFirstOption(false);
		
		dataInizioPeriodoRifItem = new DateItem("dataInizioPeriodoRif", "Periodo di riferimento dal");
		dataInizioPeriodoRifItem.setStartRow(true);
		dataInizioPeriodoRifItem.setColSpan(1);
		dataInizioPeriodoRifItem.setRequired(true);
		
		dataFinePeriodoRifItem = new DateItem("dataFinePeriodoRif", "al");
		dataFinePeriodoRifItem.setColSpan(1);
			
		richiestaCampioneForm.setFields(
			tipologiaAttoItem,
			codiceAttoItem,
			flgDeterminaAContrarreItem,
			listaStrutturaProponenteItem,
			rangeImportoItem,
			dataInizioPeriodoRifItem, dataFinePeriodoRifItem 
		);
	}
	
	public String getIdUoProponente() {
		RecordList listaStrutturaProponente = richiestaCampioneForm.getValueAsRecordList("listaStrutturaProponente");
		if(listaStrutturaProponente != null && listaStrutturaProponente.getLength() > 0) {
			return listaStrutturaProponente.get(0).getAttribute("idUo");
		}
		return null;
	}

	public Boolean validate() {
		vm.clearErrors(true);		
		Boolean valid = vm.validate();
		return valid;
	}	
	
	public void manageOnConfirmClick() {
		if (validate()) {
			final Record recordToSave = new Record(vm.getValues());	
			recordToSave.setAttribute("codiceAtto", codiceAttoItem.getValueAsString() != null ? codiceAttoItem.getValueAsString() : null);			
			recordToSave.setAttribute("idUoProponente", getIdUoProponente());
			GWTRestDataSource lRichiesteCampioneAttiDataSource = new GWTRestDataSource("RichiesteCampioneAttiDataSource");
			lRichiesteCampioneAttiDataSource.addData(recordToSave, new DSCallback() {

				@Override
				public void execute(DSResponse response, Object rawData, DSRequest request) {
					if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
						Record recordNew = response.getData()[0];						
						Layout.addMessage(new MessageBean("Richiesta di controllo a campione " + recordNew.getAttribute("idCampione") + " effettuata con successo", "", MessageType.INFO));
						_window.markForDestroy();
						if(_callback != null) {
							_callback.execute(true);
						}						
					}
				}
			});
		}
	}
	
	@Override
	public void manageOnCloseClick() {
		if(_callback != null) {
			_callback.execute(false);
		}
		_window.markForDestroy();
	};
	
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
