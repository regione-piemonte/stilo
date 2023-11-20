/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.core.client.JavaScriptObject;
import com.smartgwt.client.core.RefDataClass;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.data.RecordList;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.Cursor;
import com.smartgwt.client.types.VerticalAlignment;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.ImgButton;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.FormItemIfFunction;
import com.smartgwt.client.widgets.form.fields.CanvasItem;
import com.smartgwt.client.widgets.form.fields.FormItem;
import com.smartgwt.client.widgets.form.fields.HiddenItem;
import com.smartgwt.client.widgets.form.fields.events.FormItemInitHandler;
import com.smartgwt.client.widgets.form.fields.events.IconClickEvent;
import com.smartgwt.client.widgets.form.fields.events.IconClickHandler;
import com.smartgwt.client.widgets.form.fields.events.ShowValueEvent;
import com.smartgwt.client.widgets.form.fields.events.ShowValueHandler;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.VLayout;

import it.eng.utility.ui.module.core.client.i18n.I18NUtil;
import it.eng.utility.ui.module.layout.client.common.file.DownloadFile;
import it.eng.utility.ui.module.layout.client.common.items.ImgButtonItem;
import it.eng.utility.ui.module.layout.client.common.items.StaticTextItem;

public class InvioUDAttachmentItem extends CanvasItem {

	private String tipoMail;
	private AttachmentDownloadAction attachmentDownloadAction;
	/**
	 * Serve per istanziare la classe tramite GWT
	 * @param jsObj
	 */
	public InvioUDAttachmentItem(JavaScriptObject jsObj){
		super(jsObj);
	}

	/**
	 * Esegue il build quando l'oggetto non esiste.
	 * return InvioUDAttachmentItem;
	 * @param jsObj
	 * @return
	 */
	public InvioUDAttachmentItem buildObject(JavaScriptObject jsObj, AttachmentDownloadAction lAttachmentDownloadAction) {
		InvioUDAttachmentItem lItem = getOrCreateRef(jsObj);
		lItem.attachmentDownloadAction = lAttachmentDownloadAction;
		return lItem;
	}

	/**
	 * Crea l'istanza o ne restituisce una a partire dall'oggetto Javascript
	 * @param jsObj
	 * @return
	 */
	public static InvioUDAttachmentItem getOrCreateRef(JavaScriptObject jsObj) {
		if(jsObj == null) return null;
		RefDataClass obj = RefDataClass.getRef(jsObj);
		if(obj != null) {
			obj.setJsObj(jsObj);
			return (InvioUDAttachmentItem) obj;
		} else {
			return null;
		}
	}

	/**
	 * Crea un InvioUDAttachmentItem. In fase di init, lo disegna e ne setta
	 * lo showHandler per gestire il setValue
	 * 
	 */
	public InvioUDAttachmentItem(String pTipoMail, final AttachmentDownloadAction pDownloadAction){
		this.tipoMail = pTipoMail;
		setAutoDestroy(true); // per eliminare automaticamente il canvas quando elimino il canvasItem
		setInitHandler(new FormItemInitHandler() {
			public void onInit(FormItem item) {
				//Inizializza il componente
				buildObject(item.getJsObj(), pDownloadAction).disegna(item.getValue()); 
				//Setto lo showValue (Gestiste il setValue
				addShowValueHandler(new ShowValueHandler() {
					
					@Override
					public void onShowValue(ShowValueEvent event) {						
						//Recupero il valore - è sempre un RecordList
						drawAndSetValue(event.getDataValueAsRecordList());					
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
				if(tipoMail != null && "PEO".equals(tipoMail)) {
					removeButton = createRemoveButton(lVLayout, lHLayout);
				} else {
					removeButton = new RemoveButton();   
					removeButton.setSrc("blank.png");   
					removeButton.setShowDown(false);   
					removeButton.setShowRollOver(false);      
					removeButton.setMargin(2);		
					removeButton.setSize(20); 
					removeButton.setCursor(Cursor.ARROW);
				}
				
				//Aggiungo il bottone
				lHLayout.addMember(removeButton);
				
				//Aggiungo il form
				InvioUDAttachmentForm lInvioUDAttachmentForm = new InvioUDAttachmentForm();
				lInvioUDAttachmentForm.setNumCols(8);
				lInvioUDAttachmentForm.setRemoveButton(removeButton);
				
				HiddenItem lHiddenItem = new HiddenItem("uriAttach");
				HiddenItem lHiddenItemFirmato = new HiddenItem("firmato");
				HiddenItem lHiddenItemMimetype = new HiddenItem("mimetype");
				
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
				lStaticTextItem.setValue(lRecord.getAttributeAsString("fileNameAttach"));
				lInvioUDAttachmentForm.setFileNameAttachTextItem(lStaticTextItem);
							
				ImgButtonItem lDownloadButton = new ImgButtonItem("downloadButton", "file/download_manager.png", "Scarica");
				lDownloadButton.setAlwaysEnabled(true);
				lDownloadButton.setColSpan(1);
				lDownloadButton.setIconWidth(16);
				lDownloadButton.setIconHeight(16);
				lDownloadButton.setIconVAlign(VerticalAlignment.BOTTOM);
				lDownloadButton.setAlign(Alignment.LEFT);
				lDownloadButton.setWidth(16);	
				lDownloadButton.setEndRow(true);
				lDownloadButton.addIconClickHandler(new IconClickHandler() {
					
					@Override
					public void onIconClick(IconClickEvent event) {
						if (attachmentDownloadAction!=null)
							attachmentDownloadAction.manageIconClick(lRecord.getAttributeAsString("fileNameAttach"), lRecord.getAttributeAsString("uriAttach"),	lRecord.getAttributeAsBoolean("remoteUri"));
						else manageIconClick(lRecord.getAttributeAsString("fileNameAttach"), lRecord.getAttributeAsString("uriAttach"),
							lRecord.getAttributeAsBoolean("remoteUri"));
					}
				});
				lDownloadButton.setShowIfCondition(new FormItemIfFunction() {
					
					@Override
					public boolean execute(FormItem item, Object value, DynamicForm form) {
						return true; //form.getValueAsString("uriAttach")!=null && !form.getValueAsString("uriAttach").trim().equals("");
					}
				});
				
				lInvioUDAttachmentForm.setFields(lHiddenItem, lStaticTextItem, lDownloadButton, lHiddenItemFirmato, lHiddenItemMimetype);
				lInvioUDAttachmentForm.markForRedraw();
				
				lHLayout.addMember(lInvioUDAttachmentForm);
				
				lHLayoutList.add(lHLayout);
			}
			
			lVLayout.setMembers(lHLayoutList.toArray(new HLayout[lHLayoutList.size()]));
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
	
	protected InvioUDAttachmentForm getFirstCanvas() {
		VLayout lVLayout = (VLayout) getCanvas();
		HLayout lHLayout = (HLayout) lVLayout.getMember(0);
		return (InvioUDAttachmentForm) lHLayout.getMember(1);		
	}
	
	protected List<InvioUDAttachmentForm> getAllInvioUDAttachmentForm() {
		VLayout lVLayout = (VLayout) getCanvas();
		List<InvioUDAttachmentForm> InvioUDAttachmentFormList = new ArrayList<InvioUDAttachmentForm>();
		if (lVLayout.getMembers() != null){
			// Ciclo su tutte le canvas degli attach
			for (Canvas canvas : lVLayout.getMembers()) {
				// Vedo se la canvas è un HLayout
				if (canvas != null && canvas instanceof HLayout) {
					HLayout lHLayout = (HLayout) canvas;
					// Il form degli attach è alla posizione 1
					if (lHLayout.getMembersLength() > 1 && lHLayout.getMember(1) instanceof InvioUDAttachmentForm) {
						InvioUDAttachmentFormList.add((InvioUDAttachmentForm) lHLayout.getMember(1));
					}
				}
			}
		}
		return InvioUDAttachmentFormList;		
	}
	
	protected InvioUDAttachmentForm getCanvas(int pos) {
		VLayout lVLayout = (VLayout) getCanvas();
		HLayout lHLayout = (HLayout) lVLayout.getMember(0);
		return (InvioUDAttachmentForm) lHLayout.getMember(pos);		
	}
	
	@Override
	public void storeValue(RecordList values) {		
			
		VLayout lVLayout = (VLayout) getCanvas();
		if(lVLayout != null) {
			if(tipoMail != null && "PEO".equals(tipoMail)) {
				// Se sono in una peo con il file Segnatura.xml allegato allora devo inibile la cancellazione degli altri allegati
				// Se elimino Segnatura.xml allora anche gli allegati possono essere eliminati
				boolean segnaturaPresente = false;
				if (values != null) {
					// Verifico se ho la segnatura in allegato
					for (int i = 0; i < values.getLength(); i++) {
						Record attachRecord = values.get(i);
						String attachName = attachRecord.getAttribute("fileNameAttach");
						if ("Segnatura.xml".equalsIgnoreCase(attachName)) {
							segnaturaPresente = true;
						}
					}
				}
				if (segnaturaPresente) {
					for (InvioUDAttachmentForm invioUDAttachmentForm : getAllInvioUDAttachmentForm()) {
						if (invioUDAttachmentForm.getFileNameAttachTextItem() != null && invioUDAttachmentForm.getRemoveButton() != null){
							if (!"Segnatura.xml".equals(invioUDAttachmentForm.getFileNameAttachTextItem().getValue())) {
								invioUDAttachmentForm.getRemoveButton().setDisabled(true);
							} else {
								invioUDAttachmentForm.getRemoveButton().setDisabled(false);
							}
						}
					}
				} else {
					for (InvioUDAttachmentForm invioUDAttachmentForm : getAllInvioUDAttachmentForm()) {
						if (invioUDAttachmentForm.getRemoveButton() != null){
							invioUDAttachmentForm.getRemoveButton().setDisabled(false);
						}
					}
				}
				// Devo tenere almeno un allegato, altrimenti ho delgi errori nella interoperabilità
				if(lVLayout.getMembers().length == 1) {
					getFirstCanvas().getRemoveButton().setDisabled(true);
				} else if(lVLayout.getMembers().length > 1) {
					getFirstCanvas().getRemoveButton().setDisabled(false);		
				}
			}
		}
		
		super.storeValue(values);
		
	}

	protected void manageIconClick(String display, String uri, boolean remoteUri) {
		
		Record lRecord = new Record();
		lRecord.setAttribute("displayFilename", display);
		lRecord.setAttribute("uri", uri);
		lRecord.setAttribute("sbustato", "false");
		lRecord.setAttribute("remoteUri", remoteUri + "");
		
		DownloadFile.downloadFromRecord(lRecord, "FileToExtractBean");
		
	}
	
	public class InvioUDAttachmentForm extends DynamicForm {
		private RemoveButton removeButton;
		private StaticTextItem fileNameAttachTextItem;

		public RemoveButton getRemoveButton() {
			return removeButton;
		}

		public void setRemoveButton(RemoveButton removeButton) {
			this.removeButton = removeButton;
		}

		public StaticTextItem getFileNameAttachTextItem() {
			return fileNameAttachTextItem;
		}
		
		public void setFileNameAttachTextItem(StaticTextItem fileNameAttachTextItem) {
			this.fileNameAttachTextItem = fileNameAttachTextItem;
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