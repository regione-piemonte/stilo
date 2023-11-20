/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.core.client.JavaScriptObject;
import com.smartgwt.client.core.RefDataClass;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.data.RecordList;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.VerticalAlignment;
import com.smartgwt.client.widgets.ImgButton;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.FormItemIfFunction;
import com.smartgwt.client.widgets.form.fields.CanvasItem;
import com.smartgwt.client.widgets.form.fields.FormItem;
import com.smartgwt.client.widgets.form.fields.HiddenItem;
import com.smartgwt.client.widgets.form.fields.events.FormItemInitHandler;
import com.smartgwt.client.widgets.form.fields.events.ShowValueEvent;
import com.smartgwt.client.widgets.form.fields.events.ShowValueHandler;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.VLayout;

import it.eng.utility.ui.module.core.client.i18n.I18NUtil;
import it.eng.utility.ui.module.layout.client.common.items.StaticTextItem;

public class PostelDestinatarioItem extends CanvasItem{
	
	private RecordList lRecord;
	/**
	 * Serve per istanziare la classe tramite GWT
	 * @param jsObj
	 */
	public PostelDestinatarioItem(JavaScriptObject jsObj){
		super(jsObj);
	}

	/**
	 * Esegue il build quando l'oggetto non esiste.
	 * return PostelDestinatarioItem;
	 * @param jsObj
	 * @return
	 */
	public PostelDestinatarioItem buildObject(JavaScriptObject jsObj) {
		PostelDestinatarioItem lItem = getOrCreateRef(jsObj);
		return lItem;
	}

	/**
	 * Crea l'istanza o ne restituisce una a partire dall'oggetto Javascript
	 * @param jsObj
	 * @return
	 */
	public static PostelDestinatarioItem getOrCreateRef(JavaScriptObject jsObj) {
		if(jsObj == null) return null;
		RefDataClass obj = RefDataClass.getRef(jsObj);
		if(obj != null) {
			obj.setJsObj(jsObj);
			return (PostelDestinatarioItem) obj;
		} else {
			return null;
		}
	}

	/**
	 * Crea un PostelDestinatarioItem. In fase di init, lo disegna e ne setta
	 * lo showHandler per gestire il setValue
	 * 
	 */
	public PostelDestinatarioItem(Record record){

		if (record.getAttributeAsRecordList("listaDestinatari")!=null) {
			
			RecordList destinatari = record.getAttributeAsRecordList("listaDestinatari");
			RecordList destinatariPoste = new RecordList();
			
			for(int i = 0; i < destinatari.getLength(); i++) {
				Record destinatario = destinatari.get(i);
				if(destinatario.getAttributeAsRecord("mezzoTrasmissioneDestinatario") != null 
						&& destinatario.getAttributeAsRecord("mezzoTrasmissioneDestinatario").getAttribute("mezzoTrasmissioneDestinatario") != null 
						&& record.getAttribute("modalitaInvio") != null ) {
					
					if("raccomandata".equals(record.getAttribute("modalitaInvio")) && "R".equalsIgnoreCase(destinatario.getAttributeAsRecord("mezzoTrasmissioneDestinatario").getAttribute("mezzoTrasmissioneDestinatario")))
						destinatariPoste.add(destinatario);
					
					if("posta prioritaria".equals(record.getAttribute("modalitaInvio")) && "PP".equalsIgnoreCase(destinatario.getAttributeAsRecord("mezzoTrasmissioneDestinatario").getAttribute("mezzoTrasmissioneDestinatario")))
						destinatariPoste.add(destinatario);
				}
			}			
			this.lRecord = destinatariPoste;
		}else {
			this.lRecord = new RecordList();
		}
		

		setAutoDestroy(true); // per eliminare automaticamente il canvas quando elimino il canvasItem
		setInitHandler(new FormItemInitHandler() {
			public void onInit(FormItem item) {
				//Inizializza il componente
				buildObject(item.getJsObj()).disegna(item.getValue()); 
				//Setto lo showValue (Gestiste il setValue
				addShowValueHandler(new ShowValueHandler() {
					@Override
					public void onShowValue(ShowValueEvent event) {						
						//Recupero il valore - è sempre un RecordList
						drawAndSetValue(lRecord);					
					}
				});		
			}
		});			
	}

	protected void disegna(Object value) {
		VLayout lVLayout = new VLayout();
		lVLayout.setHeight(20);
		lVLayout.setAlign(VerticalAlignment.CENTER);
		setCanvas(lVLayout);		

		//Setto lo shouldSaveValue per gestire lo store dei valori
		setShouldSaveValue(true);	
	}

	private void drawAndSetValue(RecordList dataValueAsRecordList) {

		if (dataValueAsRecordList != null) {			

			final VLayout lVLayout = (VLayout) getCanvas();	

			List<HLayout> lHLayoutList = new ArrayList<HLayout>();

			for (int k = 0; k< dataValueAsRecordList.getLength(); k++){
				//Recupero il record
				final Record lRecord = dataValueAsRecordList.get(k);	

				//Creo l'HLayout che contiene il bottone di remove e il mio form
				final HLayout lHLayout = new HLayout();
				lHLayout.setAlign(VerticalAlignment.CENTER);

				//Creo il bottone di remove
				RemoveButton removeButton = null;
				removeButton = createRemoveButton(lVLayout, lHLayout);

				//Aggiungo il bottone
				lHLayout.addMember(removeButton);

				//Aggiungo il form
				PostelDestinatarioForm lpostelDestinatarioForm = new PostelDestinatarioForm();
				lpostelDestinatarioForm.setNumCols(8);
				lpostelDestinatarioForm.setRemoveButton(removeButton);

				HiddenItem lHiddenItem = new HiddenItem("destinatario");

				StaticTextItem lStaticTextItem = new StaticTextItem();
				lStaticTextItem.setWrap(false);
				lStaticTextItem.setWidth("*");
				lStaticTextItem.setEndRow(false);
				lStaticTextItem.setShowTitle(false);
				lStaticTextItem.setTextAlign(Alignment.LEFT);
				lStaticTextItem.setShowIfCondition(new FormItemIfFunction() {
					@Override
					public boolean execute(FormItem item, Object value, DynamicForm form) {
						return true; //form.getValueAsString("uriAttach")!=null && !form.getValueAsString("uriAttach").trim().equals("");
					}
				});
				
				if(lRecord.getAttribute("nomeDestinatario") != null && !"".equalsIgnoreCase(lRecord.getAttribute("nomeDestinatario")) && lRecord.getAttribute("cognomeDestinatario") != null && !"".equalsIgnoreCase(lRecord.getAttribute("cognomeDestinatario"))) {
					lStaticTextItem.setValue(lRecord.getAttributeAsString("nomeDestinatario") + " " + lRecord.getAttribute("cognomeDestinatario"));
				}else {
					lStaticTextItem.setValue(lRecord.getAttributeAsString("denominazioneDestinatario"));
				}

				lpostelDestinatarioForm.setFields(lHiddenItem, lStaticTextItem);
				lpostelDestinatarioForm.markForRedraw();

				lHLayout.addMember(lpostelDestinatarioForm);

				lHLayoutList.add(lHLayout);

				lVLayout.setMembers(lHLayoutList.toArray(new HLayout[lHLayoutList.size()]));
			}
		}

		//		Memorizzo la recordList
		storeValue(dataValueAsRecordList);	
	}

	protected RemoveButton createRemoveButton(final VLayout lVLayout,
			final HLayout lHLayout) {
		//Creo il bottone di remove
		RemoveButton removeButton = new RemoveButton();   	
		removeButton.setShowDown(false);   
		removeButton.setShowRollOver(false);
		removeButton.setMargin(2);		
		removeButton.setSize(20);
		removeButton.setSrc("buttons/remove.png");   
		removeButton.setPrompt(I18NUtil.getMessages().removeButton_prompt());
		removeButton.addClickHandler(new ClickHandler() {	
			@Override
			public void onClick(ClickEvent event) {
				setUpClickRemove(lVLayout, lHLayout);
			}   
		});
		return removeButton;
	}

	protected void setUpClickRemove(final VLayout lVLayout,	final HLayout lHLayout) {

		int index = -1;

		//Individuo il relativo HLayout
		for (int i=0;i<lVLayout.getMembers().length; i++){
			if (lVLayout.getMember(i).getID().equals(lHLayout.getID())){
				index = i;
			}
		}

		//Lo rimuovo
		lVLayout.removeMember(lVLayout.getMember(index));
		lVLayout.markForRedraw();

		//Recupero il VLayout aggiornato
		VLayout lVLayoutReal = (VLayout) getCanvas();

		//Recupero il relativo CanvasItem
		CanvasItem lCanvasItem = lVLayoutReal.getCanvasItem();

		//Se non il valore non era memorizzato
		RecordList lRecordList;
		if (lCanvasItem.getValue()==null){
			//Lo creo
			lRecordList = new RecordList();
		} else {
			//Altrimenti lo recupero
			lRecordList = new RecordList((JavaScriptObject) lCanvasItem.getValue());
		}
		//Rimuovo dalla lista il relativo record		
		lRecordList.removeAt(index);

		//Lo memorizzo
		storeValue(lRecordList);

	}

	protected PostelDestinatarioForm getFirstCanvas() {
		VLayout lVLayout = (VLayout) getCanvas();
		HLayout lHLayout = (HLayout) lVLayout.getMember(0);
		return (PostelDestinatarioForm) lHLayout.getMember(1);		
	}

	@Override
	public void storeValue(RecordList values) {		
		VLayout lVLayout = (VLayout) getCanvas();
		if(lVLayout != null) {
			if(lVLayout.getMembers().length == 1) {
				getFirstCanvas().getRemoveButton().setDisabled(true);
			} else if(lVLayout.getMembers().length > 1) {
				getFirstCanvas().getRemoveButton().setDisabled(false);		
			}
		}
		super.storeValue(values);
	}


	public class PostelDestinatarioForm extends DynamicForm {
		private RemoveButton removeButton;

		public RemoveButton getRemoveButton() {
			return removeButton;
		}

		public void setRemoveButton(RemoveButton removeButton) {
			this.removeButton = removeButton;
		}
	}

	public class RemoveButton extends ImgButton {
		private boolean alwaysDisabled;

		public RemoveButton() {
			setCanFocus(false);
		}

		public boolean isAlwaysDisabled() {			
			return alwaysDisabled;
		}

		public void setAlwaysDisabled(boolean alwaysDisabled) {			
			this.alwaysDisabled = alwaysDisabled;
			setDisabled(true);
		}
	}



}
