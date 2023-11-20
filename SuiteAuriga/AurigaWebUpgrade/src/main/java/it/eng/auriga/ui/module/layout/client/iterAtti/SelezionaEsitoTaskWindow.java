/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.HashMap;
import java.util.Set;

import com.smartgwt.client.data.Record;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.FieldType;
import com.smartgwt.client.types.Overflow;
import com.smartgwt.client.types.TitleOrientation;
import com.smartgwt.client.types.VerticalAlignment;
import com.smartgwt.client.util.BooleanCallback;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.Button;
import com.smartgwt.client.widgets.Window;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.ValuesManager;
import com.smartgwt.client.widgets.form.fields.FormItem;
import com.smartgwt.client.widgets.form.fields.RadioGroupItem;
import com.smartgwt.client.widgets.layout.HStack;

import it.eng.auriga.ui.module.layout.client.AurigaLayout;
import it.eng.utility.ui.module.core.client.callback.ServiceCallback;
import it.eng.utility.ui.module.core.client.datasource.GWTRestDataSource;
import it.eng.utility.ui.module.layout.client.StringSplitterClient;
import it.eng.utility.ui.module.layout.client.common.items.CheckboxItem;
import it.eng.utility.ui.module.layout.client.common.items.SelectItem;
import it.eng.utility.ui.module.layout.client.common.items.TextAreaItem;
import it.eng.utility.ui.module.layout.client.common.items.TextItem;

public class SelezionaEsitoTaskWindow extends Window {
	
	private ValuesManager vm = new ValuesManager();

	private SelezionaEsitoTaskWindow instance;
	
	private DynamicForm esitoForm; 
	private FormItem esitoItem;
	
	private DynamicForm messaggioForm;
	private TextAreaItem messaggioItem;
	
	protected Set<String> esitiTaskOk;
	protected Set<String> valoriEsito;
	protected HashMap<String, String> mappaWarningMsgXEsitoTask;
	protected String esitoTaskDaPreimpostare;
	protected String msgTaskDaPreimpostare;
	 
	public SelezionaEsitoTaskWindow(final Record attr, boolean flgMessaggio, final ServiceCallback<Record> callback){
		this(attr, flgMessaggio, null, null, null, null, null, callback);
	}
	
	public SelezionaEsitoTaskWindow(final Record attr, final boolean flgMessaggio, Set<String> esitiTaskOk, final ServiceCallback<Record> callback){
		this(attr, flgMessaggio, esitiTaskOk, null, null, null, null, callback);
	}
	
	public SelezionaEsitoTaskWindow(final Record attr, final boolean flgMessaggio, Set<String> esitiTaskOk, Set<String> valoriEsito, final ServiceCallback<Record> callback){
		this(attr, flgMessaggio, esitiTaskOk, valoriEsito, null, null, null, callback);
	}
	
	public SelezionaEsitoTaskWindow(final Record attr, final boolean flgMessaggio, Set<String> esitiTaskOk, Set<String> valoriEsito, HashMap<String, String> mappaWarningMsgXEsitoTask, String esitoTaskDaPreimpostare, String msgTaskDaPreimpostare, final ServiceCallback<Record> callback){
		
		instance = this;
		
		this.esitiTaskOk = esitiTaskOk;
		this.valoriEsito = valoriEsito;
		this.mappaWarningMsgXEsitoTask = mappaWarningMsgXEsitoTask;
		this.esitoTaskDaPreimpostare = esitoTaskDaPreimpostare;
		this.msgTaskDaPreimpostare = msgTaskDaPreimpostare;
		
		setIsModal(true);
		setModalMaskOpacity(50);
		setAutoCenter(true);
		setHeight(100);
		setWidth(450);	
		setKeepInParentRect(true);				
		String title = null;
		if(flgMessaggio) {
			title = "Esito e messaggio di completamento attività";
		} else {
			title = attr != null ? attr.getAttribute("label") : null;
			if(attr != null) {
				attr.setAttribute("label", (String) null);
			}			
		}
		setTitle(title);
		setShowModalMask(true);
		setShowCloseButton(true);
		setShowMaximizeButton(false);
		setShowMinimizeButton(false);
		setOverflow(Overflow.VISIBLE);
		setAutoSize(true);
		setAutoDraw(false);
		
		esitoForm = new DynamicForm();
		esitoForm.setValuesManager(vm);
		esitoForm.setWidth100();
		esitoForm.setHeight100();
		esitoForm.setOverflow(Overflow.VISIBLE);
		esitoForm.setKeepInParentRect(true);
		esitoForm.setNumCols(1);
		esitoForm.setColWidths(1);
		esitoForm.setCellPadding(5);
		esitoForm.setTop(50);
		
		esitoItem = buildEsitoItem(attr);		
		esitoItem.setColSpan(1);
		
		esitoForm.setFields(esitoItem);
		
		addItem(esitoForm);	
		
		if(flgMessaggio) {
		
			messaggioForm = new DynamicForm();
			messaggioForm.setValuesManager(vm);
			messaggioForm.setKeepInParentRect(true);
			messaggioForm.setWidth100();
			messaggioForm.setHeight100();
			messaggioForm.setNumCols(1);
			messaggioForm.setColWidths("*");
			messaggioForm.setCellPadding(5);
			messaggioForm.setOverflow(Overflow.VISIBLE);
			
			messaggioItem = new TextAreaItem("messaggio");
			messaggioItem.setHint("Compila eventuale messaggio di completamento attività");
			messaggioItem.setStartRow(true);
			messaggioItem.setShowHintInField(true);
			messaggioItem.setShowTitle(false);
			messaggioItem.setColSpan(1);
			messaggioItem.setHeight(100);
			messaggioItem.setWidth(450);
			if(msgTaskDaPreimpostare != null && !"".equals(msgTaskDaPreimpostare)) {
				messaggioItem.setValue(msgTaskDaPreimpostare);
			}
			
			messaggioForm.setFields(messaggioItem);
			
			addItem(messaggioForm);	
			
		} 
		
		Button confermaButton = new Button("Ok");   
		confermaButton.setIcon("ok.png");
		confermaButton.setIconSize(16); 
		confermaButton.setAutoFit(false);
		confermaButton.addClickHandler(new com.smartgwt.client.widgets.events.ClickHandler() {			
			@Override
			public void onClick(com.smartgwt.client.widgets.events.ClickEvent event) {
				if(vm.validate()) {					
					final Record esitoRecord = new Record(vm.getValues());					
					final String esitoAttrName = attr.getAttribute("nome");					
					String esito = esitoRecord.getAttribute(esitoAttrName);
					if(AurigaLayout.getParametroDBAsBoolean("ATTIVA_OBBL_MSG_PASSI_WF_NON_OK") && flgMessaggio && !isEsitoTaskOk(esito) && (esitoRecord.getAttribute("messaggio") == null || "".equals(esitoRecord.getAttribute("messaggio").trim()))) {
						messaggioForm.setFieldErrors("messaggio", "Per l'esito scelto è obbligatorio compilare il messaggio");
					} else if(isForzataModificaAttoWithEsitoKo(esito)) {
						SC.ask("Hai sbloccato la modifica dei dati e/o allegati; sei sicuro di voler completare il passo con questo esito?", new BooleanCallback() {
							
							@Override
							public void execute(Boolean value) {
								if (value != null && value) {
									selezionaEsitoEChiudiWithWarning(esitoRecord, esitoAttrName, callback);
								}
							}
						});
					} else {
						selezionaEsitoEChiudiWithWarning(esitoRecord, esitoAttrName, callback);
					}					
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
				
				markForDestroy();	
			}
		});
		
		HStack _buttons = new HStack(5);
		_buttons.setWidth100();
		_buttons.setHeight(30);
		_buttons.setAlign(Alignment.CENTER);
		_buttons.setPadding(5);
        _buttons.addMember(confermaButton);
		_buttons.addMember(annullaButton);	
		_buttons.setAutoDraw(false);
		
		addItem(_buttons);
		
		setShowTitle(true);
		setHeaderIcon("blank.png");
		
		draw();
		
	}
	
	public boolean isForzataModificaAttoWithEsitoKo(String esito) {
		return false;
	}

	public void selezionaEsitoEChiudiWithWarning(final Record esitoRecord, final String esitoAttrName, final ServiceCallback<Record> callback) {
		String esito = esitoRecord.getAttribute(esitoAttrName);
		String warningMsg = getWarningMsgXEsitoTask(esito);
		if(warningMsg != null && !"".equals(warningMsg)) {
			AurigaLayout.showConfirmDialogWithWarning("Attenzione!", warningMsg, "Conferma", "Annulla", new BooleanCallback() {

				@Override
				public void execute(Boolean value) {
					if (value != null && value) {
						selezionaEsitoEChiudi(esitoRecord, esitoAttrName, callback);
					}
				}
			});
		} else {
			selezionaEsitoEChiudi(esitoRecord, esitoAttrName, callback);
		}
	}

	public void selezionaEsitoEChiudi(final Record esitoRecord, String esitoAttrName, final ServiceCallback<Record> callback) {
		String esito = esitoRecord.getAttribute(esitoAttrName);
		if(esito != null && esito.toLowerCase().contains("invio ad altro")) {
			SelezionaUoInvioWindow selezionaUoInvioWindow = new SelezionaUoInvioWindow(new ServiceCallback<Record>() {						
				@Override
				public void execute(Record object) {														
					esitoRecord.setAttribute("assegnatario", object.getAttribute("assegnatario"));
					if(callback != null) {
						callback.execute(esitoRecord);
					}		
				}
			});		
			selezionaUoInvioWindow.show();
		} else if(callback != null) {
			callback.execute(esitoRecord);
		}							
		markForDestroy();
	}
	
	public boolean isEsitoTaskOk(String esito) {
		return (esito == null || esitiTaskOk == null || (esito != null && esitiTaskOk != null && esitiTaskOk.contains(esito)));		
	}
	
	public String getWarningMsgXEsitoTask(String esito) {		
		if(esito != null && !"".equals(esito)) {
			if(mappaWarningMsgXEsitoTask != null && mappaWarningMsgXEsitoTask.containsKey(esito)) {
				return mappaWarningMsgXEsitoTask.get(esito);							
			}		
		} 
		return null;
	}
	
	private FormItem buildEsitoItem(Record attr) {	
		
		FormItem item = null;
		
		if("COMBO-BOX".equals(attr.getAttribute("tipo"))) {						
			item = buildComboBoxItem(attr);				
		} else if("CHECK".equals(attr.getAttribute("tipo"))) {
			item = buildCheckItem(attr);			
		}else if("RADIO".equals(attr.getAttribute("tipo"))) {						
			item = buildRadioItem(attr);				
		} else {
			item = buildTextItem(attr);
		}
		
		item.setRequired(true);
		item.setAttribute("obbligatorio", true);					
			
		if(attr.getAttribute("larghezza") != null && !"".equals(attr.getAttribute("larghezza"))) {
			item.setWidth(new Integer(attr.getAttribute("larghezza")));
		} else {
			item.setWidth("*");
		}																						
		
		if(item.getTitle() == null || "".equals(item.getTitle())) {
			item.setShowTitle(false);			
			item.setAlign(Alignment.CENTER);
		} else {
			item.setShowTitle(true);
		}
		
		return item;
		
	}
		
	private SelectItem buildComboBoxItem(Record attr) {		
		SelectItem item = new SelectItem(attr.getAttribute("nome"), attr.getAttribute("label"));
		item.setColSpan(1);		
		if(valoriEsito != null) {
			item.setValueMap(valoriEsito.toArray(new String[valoriEsito.size()]));			
			if(esitoTaskDaPreimpostare != null && !"".equals(esitoTaskDaPreimpostare)) {
				if(valoriEsito.contains(esitoTaskDaPreimpostare)) {
					item.setValue(esitoTaskDaPreimpostare);				
				}
			}
		} else {
			GWTRestDataSource loadComboDS = new GWTRestDataSource("LoadComboAttributoDinamicoDataSource", "key", FieldType.TEXT);
			loadComboDS.addParam("nomeCombo", attr.getAttribute("nome"));
			item.setOptionDataSource(loadComboDS);					
			item.setDisplayField("value");
			item.setValueField("key");	
			if(esitoTaskDaPreimpostare != null && !"".equals(esitoTaskDaPreimpostare)) {
				item.setAddUnknownValues(false);
				item.setValue(esitoTaskDaPreimpostare);	
			}
		}
		return item;
	}
	
	private CheckboxItem buildCheckItem(Record attr) {
		CheckboxItem item = new CheckboxItem(attr.getAttribute("nome"), attr.getAttribute("label"));
		item.setColSpan(1);
//		item.setLabelAsTitle(true);
		item.setShowTitle(true);
		item.setTitleOrientation(TitleOrientation.RIGHT);
		item.setTitleVAlign(VerticalAlignment.BOTTOM);
		if(esitoTaskDaPreimpostare != null && !"".equals(esitoTaskDaPreimpostare)) {
			item.setValue("1".equals(esitoTaskDaPreimpostare) || "true".equalsIgnoreCase(esitoTaskDaPreimpostare));
		}
		return item;
	}
		
	private RadioGroupItem buildRadioItem(Record attr) {		
		RadioGroupItem item = new RadioGroupItem(attr.getAttribute("nome"), attr.getAttribute("label"));			
		item.setVertical(false);
		item.setColSpan(1);	
		item.setWrap(false);
		item.setStartRow(true);
		item.setEndRow(true);				
		String[] valueMap = null;
		if(valoriEsito != null) {
			valueMap = valoriEsito.toArray(new String[valoriEsito.size()]);			
		} else {
			valueMap = new StringSplitterClient(attr.getAttribute("valueMap"), "|*|").getTokens();
		}			
		item.setValueMap(valueMap);		
		if(esitoTaskDaPreimpostare != null && !"".equals(esitoTaskDaPreimpostare)) {
			if(valueMap != null) {
				for(int i = 0; i < valueMap.length; i++) {
					if(valueMap[i] != null && valueMap[i].equals(esitoTaskDaPreimpostare)) {
						item.setValue(esitoTaskDaPreimpostare);
						break;
					}
				}
			}
		}
		return item;
	}

	private TextItem buildTextItem(Record attr) {
		TextItem item = new TextItem(attr.getAttribute("nome"), attr.getAttribute("label"));
		if(esitoTaskDaPreimpostare != null && !"".equals(esitoTaskDaPreimpostare)) {
			item.setValue(esitoTaskDaPreimpostare);
		}
		return item;
	}
	
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
