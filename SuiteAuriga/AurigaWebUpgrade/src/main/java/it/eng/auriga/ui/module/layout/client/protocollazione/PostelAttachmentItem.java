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
import com.smartgwt.client.widgets.form.fields.events.IconClickEvent;
import com.smartgwt.client.widgets.form.fields.events.IconClickHandler;
import com.smartgwt.client.widgets.form.fields.events.ShowValueEvent;
import com.smartgwt.client.widgets.form.fields.events.ShowValueHandler;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.VLayout;

import it.eng.auriga.ui.module.layout.client.invioUD.AttachmentDownloadAction;
import it.eng.utility.ui.module.core.client.i18n.I18NUtil;
import it.eng.utility.ui.module.layout.client.common.file.DownloadFile;
import it.eng.utility.ui.module.layout.client.common.items.ImgButtonItem;
import it.eng.utility.ui.module.layout.client.common.items.StaticTextItem;

public class PostelAttachmentItem extends CanvasItem{

	private AttachmentDownloadAction attachmentDownloadAction;
	private RecordList lRecord;
	/**
	 * Serve per istanziare la classe tramite GWT
	 * @param jsObj
	 */
	public PostelAttachmentItem(JavaScriptObject jsObj){
		super(jsObj);
	}

	/**
	 * Esegue il build quando l'oggetto non esiste.
	 * return PostelAttachmentItem;
	 * @param jsObj
	 * @return
	 */
	public PostelAttachmentItem buildObject(JavaScriptObject jsObj, AttachmentDownloadAction lAttachmentDownloadAction) {
		PostelAttachmentItem lItem = getOrCreateRef(jsObj);
		lItem.attachmentDownloadAction = lAttachmentDownloadAction;
		return lItem;
	}

	/**
	 * Crea l'istanza o ne restituisce una a partire dall'oggetto Javascript
	 * @param jsObj
	 * @return
	 */
	public static PostelAttachmentItem getOrCreateRef(JavaScriptObject jsObj) {
		if(jsObj == null) return null;
		RefDataClass obj = RefDataClass.getRef(jsObj);
		if(obj != null) {
			obj.setJsObj(jsObj);
			return (PostelAttachmentItem) obj;
		} else {
			return null;
		}
	}

	/**
	 * Crea un PostelAttachmentItem. In fase di init, lo disegna e ne setta
	 * lo showHandler per gestire il setValue
	 * 
	 */
	public PostelAttachmentItem(Record record, final AttachmentDownloadAction pDownloadAction){

		if (record.getAttributeAsRecordList("listaAllegati")!=null) {
			this.lRecord = record.getAttributeAsRecordList("listaAllegati");
		}else {
			this.lRecord = new RecordList();
		}
		
		if  (record.getAttribute("uriFilePrimario") != null && !"".equals(record.getAttribute("uriFilePrimario"))) {
			Record lRecFilePrim = new Record();
			lRecFilePrim.setAttribute("uriAttach", record.getAttribute("uriFilePrimario"));
			lRecFilePrim.setAttribute("infoFile", record.getAttributeAsRecord("infoFile"));; 
			lRecFilePrim.setAttribute("nomeFileAllegato", record.getAttribute("nomeFilePrimario"));
			lRecFilePrim.setAttribute("uriFileAllegato", record.getAttribute("uriFilePrimario"));
			lRecFilePrim.setAttribute("remoteUri", record.getAttribute("remoteUriFilePrimario"));
			this.lRecord.addAt(lRecFilePrim, 0);
		}

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
				PostelAttachmentForm lpostelAttachmentForm = new PostelAttachmentForm();
				lpostelAttachmentForm.setNumCols(8);
				lpostelAttachmentForm.setRemoveButton(removeButton);

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
				lStaticTextItem.setValue(lRecord.getAttributeAsString("nomeFileAllegato"));

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
							attachmentDownloadAction.manageIconClick(lRecord.getAttributeAsString("nomeFileAllegato"), lRecord.getAttributeAsString("uriFileAllegato"),	lRecord.getAttributeAsBoolean("remoteUri"));
						else manageIconClick(lRecord.getAttributeAsString("nomeFileAllegato"), lRecord.getAttributeAsString("uriFileAllegato"),
								lRecord.getAttributeAsBoolean("remoteUri"));
					}
				});
				lDownloadButton.setShowIfCondition(new FormItemIfFunction() {
					@Override
					public boolean execute(FormItem item, Object value, DynamicForm form) {
						return true; //form.getValueAsString("uriAttach")!=null && !form.getValueAsString("uriAttach").trim().equals("");
					}
				});

				lpostelAttachmentForm.setFields(lHiddenItem, lStaticTextItem, lDownloadButton, lHiddenItemFirmato, lHiddenItemMimetype);
				lpostelAttachmentForm.markForRedraw();

				lHLayout.addMember(lpostelAttachmentForm);

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

	protected PostelAttachmentForm getFirstCanvas() {
		VLayout lVLayout = (VLayout) getCanvas();
		HLayout lHLayout = (HLayout) lVLayout.getMember(0);
		return (PostelAttachmentForm) lHLayout.getMember(1);		
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

	protected void manageIconClick(String display, String uri, boolean remoteUri) {

		Record lRecord = new Record();
		lRecord.setAttribute("displayFilename", display);
		lRecord.setAttribute("uri", uri);
		lRecord.setAttribute("sbustato", "false");
		lRecord.setAttribute("remoteUri", remoteUri + "");

		DownloadFile.downloadFromRecord(lRecord, "FileToExtractBean");

	}

	public class PostelAttachmentForm extends DynamicForm {
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
