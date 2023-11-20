/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.LinkedHashMap;

import com.smartgwt.client.data.DSCallback;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.data.RecordList;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.TitleOrientation;
import com.smartgwt.client.types.VerticalAlignment;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.FormItemIfFunction;
import com.smartgwt.client.widgets.form.fields.FormItem;
import com.smartgwt.client.widgets.form.fields.HiddenItem;
import com.smartgwt.client.widgets.form.fields.events.ChangedEvent;
import com.smartgwt.client.widgets.form.fields.events.ChangedHandler;
import com.smartgwt.client.widgets.form.fields.events.IconClickEvent;
import com.smartgwt.client.widgets.form.fields.events.IconClickHandler;
import com.smartgwt.client.widgets.form.validator.CustomValidator;
import com.smartgwt.client.widgets.form.validator.Validator;
import it.eng.utility.ui.module.layout.client.Layout;
import it.eng.utility.ui.module.layout.client.StringSplitterClient;
import it.eng.utility.ui.module.layout.client.common.ReplicableCanvas;
import it.eng.utility.ui.module.layout.client.common.items.CheckboxItem;
import it.eng.utility.ui.module.layout.client.common.items.ImgButtonItem;
import it.eng.utility.ui.module.layout.client.common.items.NumericItem;
import it.eng.utility.ui.module.layout.client.common.items.SelectItem;
import it.eng.utility.ui.module.layout.client.common.items.TextItem;
import it.eng.utility.ui.module.layout.shared.util.FrontendUtil;

public class InfoStrutturaTabellaCanvas extends ReplicableCanvas {

	private HiddenItem rowIdItem;	
	private TextItem intestazioneItem;
	private SelectItem tipoItem;
	private CheckboxItem flgValoreObbligatorioItem;
	private CheckboxItem flgDettRigaItem;
	private NumericItem larghezzaItem;
	private SelectItem nrPosizColonnaOrdinamentoItem;
	private SelectItem versoOrdinamentoItem;
	private ReplicableCanvasForm mDynamicForm;
	private HiddenItem valoriAmmessiItem;
	private ImgButtonItem valoriAmmessiButton;
	
	@Override
	public void disegna() {

		mDynamicForm = new ReplicableCanvasForm();
		mDynamicForm.setWrapItemTitles(false);
		mDynamicForm.setNumCols(13);
		mDynamicForm.setValidateOnChange(false);

		rowIdItem = new HiddenItem("rowId");
		
		// Intestazione
		intestazioneItem = new TextItem("intestazione" , "Intestazione");
		intestazioneItem.setShowTitle(true); 
		intestazioneItem.setTitleOrientation(TitleOrientation.TOP);
		intestazioneItem.setWrapTitle(false); 
		intestazioneItem.setWidth(350);
		intestazioneItem.setAttribute("obbligatorio", true);
		
		// Tipo
		tipoItem = new SelectItem("tipo", "Tipo"); 
		tipoItem.setShowTitle(true);
		tipoItem.setTitleOrientation(TitleOrientation.TOP);
		tipoItem.setWrapTitle(false);
		tipoItem.setAllowEmptyValue(false);
		tipoItem.setWidth(110);
		LinkedHashMap<String, String> tipoValueMap = new LinkedHashMap<String, String>();
		tipoValueMap.put("S", "Stringa"); 
		tipoValueMap.put("N", "Intero");
		tipoValueMap.put("E", "Euro");
		tipoValueMap.put("D", "Data"); 
		tipoValueMap.put("T", "Data e ora");
		tipoValueMap.put("L", "Lista di link a file");
		tipoValueMap.put("C", "Testo HTML");
		tipoItem.setValueMap(tipoValueMap);  
		tipoItem.setDefaultValue("S");	
		tipoItem.addChangedHandler(new ChangedHandler() {

			@Override
			public void onChanged(ChangedEvent event) {
				mDynamicForm.markForRedraw();
				markForRedraw();
			}
		});
		
		valoriAmmessiItem = new HiddenItem("valoriAmmessi");
		
		valoriAmmessiButton = new ImgButtonItem("valoriAmmessiButton", "lettere/lettera_VA_nera.png", "Valori ammessi");
		valoriAmmessiButton.setAlwaysEnabled(true);
		valoriAmmessiButton.setColSpan(1);
		valoriAmmessiButton.setIconWidth(16);
		valoriAmmessiButton.setIconHeight(16);
		valoriAmmessiButton.setIconVAlign(VerticalAlignment.CENTER);
		valoriAmmessiButton.setAlign(Alignment.CENTER);
		valoriAmmessiButton.setWidth(16);
		valoriAmmessiButton.addIconClickHandler(new IconClickHandler() {
			@Override
			public void onIconClick(IconClickEvent event) {
				RecordList listaValoriAmmessi = new RecordList();
				if (valoriAmmessiItem.getValue()!=null){
					String valoriAmmessi = valoriAmmessiItem.getValue().toString(); 
					StringSplitterClient stValoriPossibili = new StringSplitterClient(valoriAmmessi, "|*|");
					for(int i = 0; i < stValoriPossibili.getTokens().length; i++) {
						String valoreAmmesso = stValoriPossibili.getTokens()[i];
						if (valoreAmmesso!=null && !valoreAmmesso.equalsIgnoreCase("")){
							Record lRecord = new Record();
							lRecord.setAttribute("valoreAmmesso", valoreAmmesso);
							listaValoriAmmessi.add(lRecord);	
						}
					}
				}
				if(getEditing() || listaValoriAmmessi.getLength() > 0) {
					final Record recordValoriAmmessi = new Record();
					recordValoriAmmessi.setAttribute("valoriAmmessi", listaValoriAmmessi);
					String title = "Valori ammessi della colonna " + (intestazioneItem.getValueAsString() != null ? intestazioneItem.getValueAsString() : "");
					ValoriAmmessiPopup valoriAmmessiPopup = new ValoriAmmessiPopup(title, recordValoriAmmessi, getEditing()) {
	
						@Override
						public void onClickOkButton(Record formRecord, DSCallback callback) {
							Layout.hideWaitPopup();
							setFormValuesFromRecordAfterModValoriAmmessi(formRecord);
							markForDestroy();
						}
					};
					valoriAmmessiPopup.show();
				}
			}
		});
		valoriAmmessiButton.setShowIfCondition(new FormItemIfFunction() {
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {		
				boolean valoriAmmessiPresenti = false;
				if (valoriAmmessiItem.getValue()!=null){
					String valoriAmmessi = valoriAmmessiItem.getValue().toString(); 
					StringSplitterClient stValoriPossibili = new StringSplitterClient(valoriAmmessi, "|*|");
					valoriAmmessiPresenti = stValoriPossibili.getTokens().length > 0;									
				}
				if(valoriAmmessiPresenti) {
					valoriAmmessiButton.setIcon("lettere/lettera_VA_nera.png");
					valoriAmmessiButton.setPrompt("Valori ammessi");
				} else {
					if(getEditing()) {
						valoriAmmessiButton.setIcon("lettere/lettera_VA_nera_aggiungi.png");
						valoriAmmessiButton.setPrompt("Aggiungi valori ammessi");
					} else {
						valoriAmmessiButton.setIcon("blank.png");
						valoriAmmessiButton.setPrompt(null);
					}
				}
				return tipoItem.getValue()!=null && !tipoItem.getValue().toString().equalsIgnoreCase("") && tipoItem.getValue().toString().equalsIgnoreCase("S");
			}
		});
		
		// Valore obbligatorio
		flgValoreObbligatorioItem = new CheckboxItem("flgValoreObbligatorio", "valore obbligatorio");
		flgValoreObbligatorioItem.setShowTitle(true);
		flgValoreObbligatorioItem.setTitleOrientation(TitleOrientation.TOP);
		flgValoreObbligatorioItem.setTitleAlign(Alignment.LEFT);
		flgValoreObbligatorioItem.setWrapTitle(true);
		flgValoreObbligatorioItem.setLabelAsTitle(true);
		flgValoreObbligatorioItem.setShowLabel(false);
		flgValoreObbligatorioItem.setDefaultValue(false);
		
		// Visibile solo da dett. riga
		flgDettRigaItem = new CheckboxItem("flgDettRiga", setTitleAlign("visibile solo da dett. riga", 100, false));
		flgDettRigaItem.setShowTitle(true);
		flgDettRigaItem.setTitleOrientation(TitleOrientation.TOP);
		flgDettRigaItem.setTitleAlign(Alignment.LEFT);
		flgDettRigaItem.setWrapTitle(false);
		flgDettRigaItem.setLabelAsTitle(true);
		flgDettRigaItem.setShowLabel(false);
		flgDettRigaItem.setDefaultValue(false);
		flgDettRigaItem.addChangedHandler(new ChangedHandler() {
			
			@Override
			public void onChanged(ChangedEvent event) {
				mDynamicForm.clearErrors(true);		
				if(mDynamicForm != null) {
					if(mDynamicForm.getValue("flgDettRiga") != null) {
						Boolean flgDettRigaVal = (Boolean) mDynamicForm.getValue("flgDettRiga");
						if(flgDettRigaVal) {
							larghezzaItem.setAttribute("obbligatorio", false);
							larghezzaItem.setCanEdit(false);
							nrPosizColonnaOrdinamentoItem.setCanEdit(false);
							mDynamicForm.clearValue("larghezza");
							mDynamicForm.clearValue("nrPosizColonnaOrdinamento");
						} else {
							larghezzaItem.setAttribute("obbligatorio", true);
							larghezzaItem.setCanEdit(true);
							nrPosizColonnaOrdinamentoItem.setCanEdit(true);
						}
					} else {
						larghezzaItem.setAttribute("obbligatorio", true);
						larghezzaItem.setCanEdit(true);
						nrPosizColonnaOrdinamentoItem.setCanEdit(true);
					}
					if(larghezzaItem.getAttributeAsBoolean("obbligatorio") != null &&
					   larghezzaItem.getAttributeAsBoolean("obbligatorio")) {
						larghezzaItem.setTitle(FrontendUtil.getRequiredFormItemTitle("Larghezza %"));
					} else {
						larghezzaItem.setTitle("Larghezza %");
					}
					mDynamicForm.markForRedraw();
					markForRedraw();
				}				
			}
		});
		
		// Larghezza %
		larghezzaItem = new NumericItem("larghezza", "Larghezza %", false);
		larghezzaItem.setShowTitle(true);
		larghezzaItem.setTitleOrientation(TitleOrientation.TOP);
		larghezzaItem.setWrapTitle(false);
		larghezzaItem.setWidth(90);
		larghezzaItem.setLength(3);
		larghezzaItem.setAttribute("obbligatorio", true);
		Validator larghezzaRangeValidator = buildLarghezzaRangeValidator();
		larghezzaItem.setValidators(larghezzaRangeValidator);
		
		// N posiz. come col. per ord
		nrPosizColonnaOrdinamentoItem= new SelectItem("nrPosizColonnaOrdinamento" , "N posiz. come col. per ord.");
		nrPosizColonnaOrdinamentoItem.setShowTitle(true); 
		nrPosizColonnaOrdinamentoItem.setTitleOrientation(TitleOrientation.TOP);
		nrPosizColonnaOrdinamentoItem.setWrapTitle(false);
		nrPosizColonnaOrdinamentoItem.setAllowEmptyValue(true);
		nrPosizColonnaOrdinamentoItem.setWidth(160);
		LinkedHashMap<String, String> nrPosizColonnaOrdinamentoValueMap = new LinkedHashMap<String, String>();
		nrPosizColonnaOrdinamentoValueMap.put("1",  "1"); 
		nrPosizColonnaOrdinamentoValueMap.put("2",  "2");
		nrPosizColonnaOrdinamentoValueMap.put("3",  "3"); 
		nrPosizColonnaOrdinamentoValueMap.put("4",  "4");
		nrPosizColonnaOrdinamentoValueMap.put("5",  "5");
		nrPosizColonnaOrdinamentoValueMap.put("6",  "6");
		nrPosizColonnaOrdinamentoValueMap.put("7",  "7");
		nrPosizColonnaOrdinamentoValueMap.put("8",  "8");
		nrPosizColonnaOrdinamentoValueMap.put("9",  "9");
		nrPosizColonnaOrdinamentoValueMap.put("10", "10");
		nrPosizColonnaOrdinamentoItem.setValueMap(nrPosizColonnaOrdinamentoValueMap);  		
		nrPosizColonnaOrdinamentoItem.addChangedHandler(new ChangedHandler() {
			
			@Override
			public void onChanged(ChangedEvent event) {
				String nrPosizColonnaOrdinamento = (String) event.getValue();
				if(nrPosizColonnaOrdinamento == null)
					nrPosizColonnaOrdinamento = "";
				
				// Se il valore e' null allora devo settare il verso ordinameneto  = "asc"
				if(nrPosizColonnaOrdinamento.equalsIgnoreCase("")) {
					String versoOrdinamento = versoOrdinamentoItem.getValueAsString();
					if (versoOrdinamento == null)
						versoOrdinamento = "";
					
					if (versoOrdinamento.equalsIgnoreCase("")){
						versoOrdinamentoItem.setValue("asc");
						mDynamicForm.setValue("versoOrdinamento" , "asc");
					}
				}
				mDynamicForm.markForRedraw();
				markForRedraw();
			}
		});
		
		// Verso ordinamento
		versoOrdinamentoItem= new SelectItem("versoOrdinamento", "Verso ordinamento");
		versoOrdinamentoItem.setShowTitle(true); 
		versoOrdinamentoItem.setTitleOrientation(TitleOrientation.TOP);
		versoOrdinamentoItem.setWrapTitle(false);
		versoOrdinamentoItem.setAllowEmptyValue(true);
		versoOrdinamentoItem.setWidth(120);
		LinkedHashMap<String, String> versoOrdinamentoValueMap = new LinkedHashMap<String, String>();
		versoOrdinamentoValueMap.put("asc", "crescente"); 
		versoOrdinamentoValueMap.put("desc", "decrescente");
		versoOrdinamentoItem.setValueMap(versoOrdinamentoValueMap);  
		versoOrdinamentoItem.setDefaultValue("asc");				
		
		mDynamicForm.setFields(
				rowIdItem, 
				intestazioneItem,
				tipoItem,
				valoriAmmessiButton,
				flgValoreObbligatorioItem,
				flgDettRigaItem,
				larghezzaItem,
				nrPosizColonnaOrdinamentoItem,
				versoOrdinamentoItem,
				
				valoriAmmessiItem
		);	
		
		addChild(mDynamicForm);
	}
	
	public Record getFormValuesAsRecord() {
		return mDynamicForm.getValuesAsRecord();	
	}
		
	@Override
	public ReplicableCanvasForm[] getForm() {
		return new ReplicableCanvasForm[]{mDynamicForm};
	}
	
	private CustomValidator buildLarghezzaRangeValidator() {		
		CustomValidator validator = new CustomValidator() {

			@Override
			protected boolean condition(Object value) {
				if (value == null) {
					return true;
				}
				Integer intValue = Integer.valueOf((String) value);					
				boolean result = intValue.intValue() >= 0 && intValue.intValue() <= 100;					
				return result;				
			}
		};
		validator.setErrorMessage("Il campo deve essere minore o uguale a 100");
		return validator;
	}

	@Override
	public boolean validate() {
		boolean valid = true;
		
		mDynamicForm.setShowInlineErrors(true);
		
		// Controllo obbligatorieta'
		
		// Intestazione
		if((intestazioneItem.getValue() == null || intestazioneItem.getValue().equals(""))) {
			mDynamicForm.setFieldErrors("intestazione", "Campo obbligatorio");
			valid = false;
		}
		
		Boolean isflgDettRiga = flgDettRigaItem != null && flgDettRigaItem.getValueAsBoolean() != null
				&& flgDettRigaItem.getValueAsBoolean();
		if(!isflgDettRiga) {
			// Larghezza
			if ((larghezzaItem.getValue() == null || larghezzaItem.getValue().equals(""))) {
				mDynamicForm.setFieldErrors("larghezza", "Campo obbligatorio");
				valid = false;
			}		
			
			// Verso ordinamento
			// Se “Posiz. come col. per ord.” è valorizzato e “Verso ordinamento” non è valorizzato diamo errore		
			if ((nrPosizColonnaOrdinamentoItem.getValue() != null && !nrPosizColonnaOrdinamentoItem.getValue().equals(""))) {
				if ((versoOrdinamentoItem.getValue() == null || versoOrdinamentoItem.getValue().equals(""))) {
					mDynamicForm.setFieldErrors("versoOrdinamento", "Campo obbligatorio");
					valid = false;
				}
			}
		}
		
		return valid;
	}
	
	protected String setTitleAlign(String title, int width, boolean required) {
		if (title != null && title.startsWith("<span")) {
			return title;
		}
		return "<span style=\"width: " + (required ? width : width + 1) + "px; display: inline-block;\">" + title + "</span>";
	}
	
	public void setFormValuesFromRecordAfterModValoriAmmessi(Record record) {
		String valoriAmmessiLista = "";
		if(record != null) {
			if(record.getAttribute("valoriAmmessi") != null) {
				RecordList listaValoriAmmessi = record.getAttributeAsRecordList("valoriAmmessi");
				if (listaValoriAmmessi != null && listaValoriAmmessi.getLength() > 0) {
					for(int i=0; i < listaValoriAmmessi.getLength(); i++) {
						Record recItem = listaValoriAmmessi.get(i);
						if(recItem != null && recItem.getAttributeAsString("valoreAmmesso") != null) {
							String valoreAmmesso = recItem.getAttributeAsString("valoreAmmesso");
							valoriAmmessiLista =  valoriAmmessiLista + valoreAmmesso +"|*|" ; 
						}
					}
				}
			}
		}	
		mDynamicForm.setValue("valoriAmmessi", valoriAmmessiLista);
		mDynamicForm.markForRedraw();
		markForRedraw();
	}	
}