/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import com.smartgwt.client.data.DSCallback;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.FormItemIfFunction;
import com.smartgwt.client.widgets.form.fields.ButtonItem;
import com.smartgwt.client.widgets.form.fields.FormItem;
import com.smartgwt.client.widgets.form.fields.HiddenItem;
import com.smartgwt.client.widgets.form.fields.RadioGroupItem;
import com.smartgwt.client.widgets.form.fields.events.ChangedEvent;
import com.smartgwt.client.widgets.form.fields.events.ChangedHandler;
import com.smartgwt.client.widgets.form.fields.events.ClickEvent;
import com.smartgwt.client.widgets.form.fields.events.ClickHandler;
import com.smartgwt.client.widgets.form.fields.events.IconClickEvent;
import com.smartgwt.client.widgets.form.fields.events.IconClickHandler;

import it.eng.auriga.ui.module.layout.client.archivio.LookupArchivioPopup;
import it.eng.utility.ui.module.core.client.callback.ServiceCallback;
import it.eng.utility.ui.module.core.client.datasource.GWTRestDataSource;
import it.eng.utility.ui.module.core.shared.message.MessageBean;
import it.eng.utility.ui.module.core.shared.message.MessageType;
import it.eng.utility.ui.module.layout.client.Layout;
import it.eng.utility.ui.module.layout.client.StringSplitterClient;
import it.eng.utility.ui.module.layout.client.common.items.ImgButtonItem;
import it.eng.utility.ui.module.layout.client.common.items.NumericItem;

/**
 * 
 * @author DANCRIST
 *
 */

public class SceltaTipoProtoIstanzaForm extends DynamicForm {
	
	private SceltaTipoProtoIstanzaPopup window;
	private DynamicForm instance;
	private RadioGroupItem radioGroupItem;

	private HiddenItem idUdItem;
	private HiddenItem tipoItem;
	private NumericItem numeroItem;
	private NumericItem annoItem;
	private ImgButtonItem lookupArchivioButton;
	
	private static String daProtocollare  = "da protocollare";
	private static String giaProtocollata = "già protocollata";
	
	public SceltaTipoProtoIstanzaForm(final SceltaTipoProtoIstanzaPopup pWindow, final ServiceCallback<Record> callback) {
		
		instance = this;

		window = pWindow;

		setKeepInParentRect(true);
		setWidth100();
		setHeight100();
		setNumCols(5);
		setColWidths(1, 1, 1 , 1, "*");
		setCellPadding(5);
		setAlign(Alignment.CENTER);
		setTop(50);
		
		idUdItem = new HiddenItem("idUd");
		tipoItem = new HiddenItem("tipo");
		
		radioGroupItem = new RadioGroupItem("tipoProtocolloIstanza");
		radioGroupItem.setTitle("Istanza");
		radioGroupItem.setVertical(false);
		radioGroupItem.setStartRow(true);
		radioGroupItem.setValueMap(daProtocollare, giaProtocollata);
		radioGroupItem.setDefaultValue(daProtocollare);
		radioGroupItem.setWrap(false);
		radioGroupItem.setColSpan(3);
		radioGroupItem.addChangedHandler(new ChangedHandler() {
			
			@Override
			public void onChanged(ChangedEvent event) {
				markForRedraw();
			}
		});
		
		numeroItem = new NumericItem("numero", "Prot. N°");
		numeroItem.setWrapTitle(false);
		numeroItem.setStartRow(true);
		numeroItem.setEndRow(false);
		numeroItem.setColSpan(1);
		numeroItem.setWidth(100);
		numeroItem.setLength(7);
		numeroItem.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				
				if(giaProtocollata.equals(radioGroupItem.getValueAsString())) {
					numeroItem.setRequired(true);
					return true;
				} return false;
			}
		});
		
		annoItem = new NumericItem("anno", "Anno");
		annoItem.setStartRow(false);
		annoItem.setEndRow(false);
		annoItem.setColSpan(1);
		annoItem.setWidth(80);
		annoItem.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				if(giaProtocollata.equals(radioGroupItem.getValueAsString())) {
					annoItem.setRequired(true);
					return true;
				} return false;
			}
		});
		
		lookupArchivioButton = new ImgButtonItem("lookupArchivioButton", "lookup/archivio.png", "Seleziona dall'archivio");	
		lookupArchivioButton.setEndRow(true);
		lookupArchivioButton.setColSpan(1);		
		lookupArchivioButton.addIconClickHandler(new IconClickHandler() {	
			
			@Override
			public void onIconClick(IconClickEvent event) {
									
				DocumentoCollegatoLookupArchivio lookupArchivioPopup = new DocumentoCollegatoLookupArchivio(getValuesAsRecord());
				lookupArchivioPopup.show();				
			}
		});	  
		lookupArchivioButton.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return giaProtocollata.equals(radioGroupItem.getValueAsString());
			}
		});
		
		ButtonItem okButton = new ButtonItem("okButton", "Ok");
		okButton.setColSpan(5);
		okButton.setWidth(100);
		okButton.setTop(20);
		okButton.setAlign(Alignment.CENTER);
		okButton.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				if(validate()) {
					manageOnClick(callback);
				}
			}
		});
		
		setFields(radioGroupItem, idUdItem, tipoItem, numeroItem, annoItem, lookupArchivioButton, okButton);
	
	}

	protected void manageOnClick(final ServiceCallback<Record> callback) {
	
		if(giaProtocollata.equals(radioGroupItem.getValueAsString())) {
			
			if( (numeroItem.getValueAsString() != null && !"".equals(numeroItem.getValueAsString())) &&
				(annoItem.getValueAsString() != null && !"".equals(annoItem.getValueAsString()))
			) {	
				Record lRecord = new Record();
				lRecord.setAttribute("nroProtocollo", numeroItem.getValueAsString());
				lRecord.setAttribute("annoProtocollo", annoItem.getValueAsString());
				GWTRestDataSource lGwtRestDataSource = new GWTRestDataSource("ProtocolloDataSource");
				lGwtRestDataSource.executecustom("recuperaIdUd", lRecord, new DSCallback() {
	
					@Override
					public void execute(DSResponse response, Object rawData, DSRequest request) {
	
						if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
							Record record = response.getData()[0];
							String idUd = record.getAttributeAsString("idUd");
							if ((idUd != null) && (!"".equalsIgnoreCase(idUd))){
								if (callback != null) {
									setValue("idUd", idUd);
									Record recordAbil = new Record();
									recordAbil.setAttribute("idUd", idUd);
									final GWTRestDataSource lGwtRestDataSource = new GWTRestDataSource("ProtocolloDataSource");
									lGwtRestDataSource.addParam("flgSoloAbilAzioni", "1");
									lGwtRestDataSource.performCustomOperation("get", recordAbil, new DSCallback() {
										
										@Override
										public void execute(DSResponse response, Object rawData, DSRequest request) {
													
											if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
												final Record recordDettaglio = response.getData()[0];
												Boolean abilModificaDati = recordDettaglio.getAttributeAsBoolean("abilModificaDati") != null &&
														recordDettaglio.getAttributeAsBoolean("abilModificaDati");
												if(abilModificaDati) {											
													Boolean isISTRDAVV = recordDettaglio.getAttribute("codStatoDett") != null &&
															"ISTRDAVV".equals(recordDettaglio.getAttribute("codStatoDett"));
													if(!isISTRDAVV) {
														Boolean hasIdProcess = recordDettaglio.getAttribute("idProcess") != null &&
																!"".equals(recordDettaglio.getAttribute("idProcess"));
														if(!hasIdProcess) {
															callback.execute(getValuesAsRecord());
															window.markForDestroy();
														} else {
															Layout.addMessage(new MessageBean("Protocollo già inserito in un procedimento", "", MessageType.ERROR));
														}
													}  else {
														Layout.addMessage(new MessageBean("Protocollo già importato nella lista delle istanze da istruire", "", MessageType.ERROR));
													}
												} else {
													Layout.addMessage(new MessageBean("Non sei abilitato a lavorare su questo documento", "", MessageType.ERROR));
												}
											}
										}
									});
								}							
							}					
						} else {
							Layout.addMessage(new MessageBean("Protocollo inesistente","",MessageType.ERROR));
						}
					}
				});
			}
		} else {
			callback.execute(getValuesAsRecord());
			window.markForDestroy();	
		}
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
			return "SEL_X_ISTANZA_E";
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
	
	public void setFormValuesFromRecordArchivio(Record record) {
		
		clearErrors(true);						
		String segnaturaXOrd = record.getAttribute("segnaturaXOrd");		
		StringSplitterClient st = new StringSplitterClient(segnaturaXOrd, "-");		

		setValue("tipo", "PG");
		setValue("anno", st.getTokens()[2]);
		setValue("numero", st.getTokens()[3]);
		setValue("idUd", record.getAttribute("idUdFolder"));

		markForRedraw();		
	}	
	
}