/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.auriga.ui.module.layout.client.archivio.LookupArchivioPopup;
import it.eng.auriga.ui.module.layout.client.i18n.I18NUtil;
import it.eng.utility.ui.module.core.client.datasource.SelectGWTRestDataSource;
import it.eng.utility.ui.module.core.shared.message.MessageBean;
import it.eng.utility.ui.module.core.shared.message.MessageType;
import it.eng.utility.ui.module.layout.client.Layout;
import it.eng.utility.ui.module.layout.client.StringSplitterClient;
import it.eng.utility.ui.module.layout.client.common.CustomDetail;
import it.eng.utility.ui.module.layout.client.common.CustomLayout;
import it.eng.utility.ui.module.layout.client.common.DetailToolStripButton;
import it.eng.utility.ui.module.layout.client.common.filter.item.AnnoItem;
import it.eng.utility.ui.module.layout.client.common.items.ExtendedNumericItem;
import it.eng.utility.ui.module.layout.client.common.items.FilteredSelectItemWithDisplay;
import it.eng.utility.ui.module.layout.client.common.items.ImgButtonItem;
import it.eng.utility.ui.module.layout.client.common.items.SelectItem;
import it.eng.utility.ui.module.layout.client.common.items.TextAreaItem;
import it.eng.utility.ui.module.layout.client.portal.ModalWindow;

import java.util.LinkedHashMap;

import com.smartgwt.client.data.DSCallback;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.FieldType;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.FormItemHoverFormatter;
import com.smartgwt.client.widgets.form.FormItemIfFunction;
import com.smartgwt.client.widgets.form.fields.FormItem;
import com.smartgwt.client.widgets.form.fields.HiddenItem;
import com.smartgwt.client.widgets.form.fields.events.BlurEvent;
import com.smartgwt.client.widgets.form.fields.events.BlurHandler;
import com.smartgwt.client.widgets.form.fields.events.ChangeEvent;
import com.smartgwt.client.widgets.form.fields.events.ChangeHandler;
import com.smartgwt.client.widgets.form.fields.events.ClickEvent;
import com.smartgwt.client.widgets.form.fields.events.ClickHandler;
import com.smartgwt.client.widgets.form.fields.events.IconClickEvent;
import com.smartgwt.client.widgets.form.fields.events.IconClickHandler;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.toolbar.ToolStrip;

public class AssociaInvioPopup extends ModalWindow{
	
	protected Record record;
	private DynamicForm associaInvioForm;
	private SelectItem tipoDocumentoSelectItem;
	private AnnoItem annoItem;
	private ExtendedNumericItem numeroItem;
	private FilteredSelectItemWithDisplay invioSelectItem;
	private TextAreaItem noteItem;
	
	private HiddenItem idEmailRicevutaItem;
	private HiddenItem idUdCollegataHiddenItem;
	
	private DynamicForm form; 
	
	private CustomLayout postaElettonicaLayout;
	private DettaglioPostaElettronica dettaglioPostaElettronica;
	
	SelectGWTRestDataSource associaInvioDataSource = new SelectGWTRestDataSource("AssociaInvioDataSource", "idDestinatario", FieldType.TEXT, new String[]{"descrizione"}, true);
	
	public AssociaInvioPopup(CustomLayout layout, CustomDetail detail, Record record) {
		
		super("associaInvio", true);
		
		idEmailRicevutaItem = new HiddenItem("idEmailRicevuta");
		idEmailRicevutaItem.setValue(record.getAttribute("idEmail"));
		
		idUdCollegataHiddenItem = new HiddenItem("idUdCollegata");
		
		this.record = record;
		
		setIsModal(true);
		setModalMaskOpacity(50);
		setAutoCenter(true);
		setWidth(600);		
		setHeight(250);
		setKeepInParentRect(true);
		setTitle("Seleziona invio a cui collegare la notifica " + record.getAttribute("id"));
		setShowModalMask(true);
		setShowCloseButton(true);
		setShowMaximizeButton(true);
		setShowMinimizeButton(true);
		
		if (detail!=null){
			setDettaglioPostaElettronica((DettaglioPostaElettronica)detail);
	
		}else if(layout!=null){
			setPostaElettonicaLayout(layout);
		}
		
		associaInvioForm = createForm();
		associaInvioForm.setAlign(Alignment.CENTER);	
		
		DetailToolStripButton saveButton = new DetailToolStripButton(I18NUtil.getMessages().saveButton_prompt(), "buttons/save.png");
		
		saveButton.addClickHandler(new com.smartgwt.client.widgets.events.ClickHandler() {
			
			@Override
			public void onClick(com.smartgwt.client.widgets.events.ClickEvent event) {

				if(form.validate()){
					final Record record = new Record(form.getValues());
					associaInvioDataSource.addData(record, new DSCallback() {
						
						@Override
						public void execute(DSResponse response, Object rawData, DSRequest request) {
	
							if(response.getStatus() == DSResponse.STATUS_SUCCESS) {					
								Layout.addMessage(new MessageBean("Notifica associata con successo", "", MessageType.INFO));
								
								if(getPostaElettonicaLayout() != null)
									getPostaElettonicaLayout().reloadListAndSetCurrentRecord(record);
								else if (getDettaglioPostaElettronica() != null)
									getDettaglioPostaElettronica().reloadDetail();
								
								markForDestroy();
							}
							
						}
					});
					
				}
				
			}
		});
		
		// Creo la TOOLSTRIP e aggiungo i bottoni	        
		ToolStrip detailToolStrip = new ToolStrip();   
		detailToolStrip.setWidth100();       
		detailToolStrip.setHeight(30);
		detailToolStrip.setStyleName(it.eng.utility.Styles.detailToolStrip);
		//detailToolStrip.addButton(backButton);
		detailToolStrip.addFill(); //push all buttons to the right 
		detailToolStrip.addButton(saveButton);	
				
		addItem(associaInvioForm);
		addItem(detailToolStrip);
		
		settingsMenu.removeItem(separatorMenuItem);
		settingsMenu.removeItem(autoSearchMenuItem);		
		
		
		setShowTitle(true);
		
	}
	
	private DynamicForm createForm(){
		
		form = new DynamicForm();
		form.setKeepInParentRect(true);
		form.setWidth100();
		form.setHeight100();
		form.setNumCols(10);
		form.setColWidths(1,1,1,1,1,1,1,1,"*","*");		
		form.setCellPadding(5);		
		form.setWrapItemTitles(false);		
		
		tipoDocumentoSelectItem = new SelectItem("estremiDocumentoInviato", "Estremi documento inviato");
		tipoDocumentoSelectItem.setRequired(true);
		LinkedHashMap<String, String> tipoValueMap = new LinkedHashMap<String, String>();
		tipoValueMap.put("PG", "Prot. Generale");
		tipoValueMap.put("PI", "Prot. Interno");
		tipoValueMap.put("NI", "Bozza");
		tipoDocumentoSelectItem.setValueMap(tipoValueMap);
		tipoDocumentoSelectItem.setDefaultValue("PG");
		tipoDocumentoSelectItem.setWidth(120);
		tipoDocumentoSelectItem.setWrapTitle(true);
		tipoDocumentoSelectItem.setColSpan(1);
		tipoDocumentoSelectItem.setAllowEmptyValue(true);
		tipoDocumentoSelectItem.setStartRow(true);
		
		tipoDocumentoSelectItem.setItemHoverFormatter(true, new FormItemHoverFormatter() {			
			@Override
			public String getHoverHTML(FormItem item, DynamicForm form) {
				
				String estremiReg = form.getValueAsString("estremiReg") != null ? form.getValueAsString("estremiReg") : "";
				String datiCollegamento = form.getValueAsString("datiCollegamento") != null ? form.getValueAsString("datiCollegamento") : "";				
				if(!"".equals(estremiReg) || !"".equals(datiCollegamento)) {
					return estremiReg  + " " + datiCollegamento;
				}
				return null;
			}
		});
		
		tipoDocumentoSelectItem.addChangeHandler(new ChangeHandler() {
			
			@Override
			public void onChange(ChangeEvent event) {
				
				invioSelectItem.clearValue();
				
			}
		});
		
		annoItem = new AnnoItem("anno", "Anno");
		annoItem.setRequired(true);		
		annoItem.setColSpan(1);
		
		annoItem.addBlurHandler(new BlurHandler() {
			
			@Override
			public void onBlur(BlurEvent event) {
				
				invioSelectItem.clearValue();
				
			}
		});
		
		annoItem.setItemHoverFormatter(true, new FormItemHoverFormatter() {			
			@Override
			public String getHoverHTML(FormItem item, DynamicForm form) {
				
				String estremiReg = form.getValueAsString("estremiReg") != null ? form.getValueAsString("estremiReg") : "";
				String datiCollegamento = form.getValueAsString("datiCollegamento") != null ? form.getValueAsString("datiCollegamento") : "";				
				if(!"".equals(estremiReg) || !"".equals(datiCollegamento)) {
					return estremiReg  + " " + datiCollegamento;
				}
				return null;
			}
		});
		
		numeroItem = new ExtendedNumericItem("numero", "N.ro");		
		numeroItem.setRequired(true);
		numeroItem.setColSpan(1);
		numeroItem.setLength(7);
		numeroItem.setCanEdit(true);
		
		numeroItem.addChangeHandler(new ChangeHandler() {
			
			@Override
			public void onChange(ChangeEvent event) {
				
				form.getField("visualizzaDettagliButton").hide();
				invioSelectItem.clearValue();
				
			}
		});
		
		numeroItem.setItemHoverFormatter(true, new FormItemHoverFormatter() {			
			@Override
			public String getHoverHTML(FormItem item, DynamicForm form) {
				
				String estremiReg = form.getValueAsString("estremiReg") != null ? form.getValueAsString("estremiReg") : "";
				String datiCollegamento = form.getValueAsString("datiCollegamento") != null ? form.getValueAsString("datiCollegamento") : "";				
				if(!"".equals(estremiReg) || !"".equals(datiCollegamento)) {
					return estremiReg  + " " + datiCollegamento;
				}
				return null;
			}
		});
		
		// BOTTONI : seleziona dall'archivio, nuovo
		ImgButtonItem lookupArchivioButton = new ImgButtonItem("lookupArchivioButton", "lookup/archivio.png", "Seleziona dall'archivio");	
		lookupArchivioButton.setEndRow(false);
		lookupArchivioButton.setColSpan(1);		
		lookupArchivioButton.addIconClickHandler(new IconClickHandler() {			
			@Override
			public void onIconClick(IconClickEvent event) {
														
				DocumentoCollegatoLookupArchivio lookupArchivioPopup = new DocumentoCollegatoLookupArchivio(form.getValuesAsRecord());
				lookupArchivioPopup.show();	
				
				invioSelectItem.clearValue();
				
			}
		});	  
		
		ImgButtonItem visualizzaDettagliButton = new ImgButtonItem("visualizzaDettagliButton", "buttons/detail.png", "Visualizza dettagli");
		visualizzaDettagliButton.setAlwaysEnabled(true);
		visualizzaDettagliButton.setColSpan(1);		
		visualizzaDettagliButton.setShowIfCondition(new FormItemIfFunction() {			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return form.getValue("idUdCollegata") != null && !"".equals(form.getValue("idUdCollegata"));
			}
		});
		visualizzaDettagliButton.addIconClickHandler(new IconClickHandler() {			
			@Override
			public void onIconClick(IconClickEvent event) {
				Record record = new Record();
				record.setAttribute("idUd", idUdCollegataHiddenItem.getValue());					
				DettaglioRegProtAssociatoWindow dettaglioRegProtAssociatoWindow = new DettaglioRegProtAssociatoWindow(record, "Dettaglio doc. collegato");
			}
		});		
		
		invioSelectItem = new FilteredSelectItemWithDisplay("idDestinatario", associaInvioDataSource);	
		ListGridField nomeField = new ListGridField("indirizzoDestinatario", I18NUtil.getMessages().associa_invio_email_indirizzo_destinatario());
		ListGridField dataInvioField = new ListGridField("dataInvio", I18NUtil.getMessages().associa_invio_email_data_invio());
		dataInvioField.setWidth(150);
		dataInvioField.setCanFilter(false);
		invioSelectItem.setPickListFields(nomeField, dataInvioField);
		invioSelectItem.setTitle("Invio");  
		invioSelectItem.setShowTitle(true);
//		invioSelectItem.setFilterLocally(true);
		invioSelectItem.setWidth(430);
		invioSelectItem.setValueField("idDestinatario");
		invioSelectItem.setDisplayField("indirizzoDestinatario");
		invioSelectItem.setColSpan(10);
		invioSelectItem.setOptionDataSource(associaInvioDataSource);
		invioSelectItem.setCachePickListResults(false);
		invioSelectItem.setRequired(true);
		
		invioSelectItem.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {

				loadComboInvio();
				
			}
		}); 
		
		
		noteItem = new TextAreaItem("nota", "Nota");
		noteItem.setHeight(80);
		noteItem.setWidth(430);
		noteItem.setColSpan("10");
		noteItem.setStartRow(true);
		noteItem.setColSpan(20);
		
		form.setFields(
				idUdCollegataHiddenItem,
				idEmailRicevutaItem,
				tipoDocumentoSelectItem, 
				annoItem,
				numeroItem,
				lookupArchivioButton,
				visualizzaDettagliButton,
				invioSelectItem,
				noteItem
//				estremiRegHiddenItem,
//				datiCollegamentoHiddenItem,
//				flgLockedHiddenItem
		);
		
		return form;
	}
	
	public void setFormValuesFromRecordArchivio(Record record) {
		form.clearErrors(true);						
		form.setValue("idUdCollegata", record.getAttribute("idUdFolder"));
		String segnaturaXOrd = record.getAttribute("segnaturaXOrd");		
		StringSplitterClient st = new StringSplitterClient(segnaturaXOrd, "-");		
		String tipo = st.getTokens()[0];
		if(tipo != null) {
			if("1".equals(tipo)) {
				form.setValue("tipo", "PG");				
			} else if("3".equals(tipo)) {
				form.setValue("tipo", "PI");				
			} else if("7".equals(tipo)) {
				form.setValue("tipo", "NI");				
			}
		}
		form.setValue("anno", st.getTokens()[2]);
		form.setValue("numero", st.getTokens()[3]);
		form.setValue("oggetto", record.getAttribute("oggetto"));		
		form.markForRedraw();		
	}	
	
	private void loadComboInvio(){
		
		invioSelectItem.clearValue();
		
		associaInvioDataSource.addParam("estremiDocumentoInviato", form.getValueAsString("estremiDocumentoInviato"));
		associaInvioDataSource.addParam("anno", form.getValueAsString("anno"));
		associaInvioDataSource.addParam("numero", form.getValueAsString("numero"));
		
		invioSelectItem.fetchData();
		
	}
	
	public CustomLayout getPostaElettonicaLayout() {
		return postaElettonicaLayout;
	}

	public void setPostaElettonicaLayout(CustomLayout postaElettonicaLayout) {
		this.postaElettonicaLayout = postaElettonicaLayout;
	}

	public DettaglioPostaElettronica getDettaglioPostaElettronica() {
		return dettaglioPostaElettronica;
	}

	public void setDettaglioPostaElettronica(DettaglioPostaElettronica dettaglioPostaElettronica) {
		this.dettaglioPostaElettronica = dettaglioPostaElettronica;
	}

	public class DocumentoCollegatoLookupArchivio extends LookupArchivioPopup {

		public DocumentoCollegatoLookupArchivio(Record record) {
			super(record, true);			
		}

		public DocumentoCollegatoLookupArchivio(Record record, String idRootNode) {
			super(record, idRootNode, true);			
		}
		
		@Override
		public String getWindowTitle() {
			return "Seleziona documento da collegare";
		}
		
		@Override
		public String getFinalita() {
			return "COLLEGA_UD";
		}

		@Override
		public void manageLookupBack(Record record) {
			setFormValuesFromRecordArchivio(record);						
		}

		@Override
		public void manageMultiLookupBack(Record record) {

		}	

		@Override
		public void manageMultiLookupUndo(Record record) {	

		}			
	}

}
