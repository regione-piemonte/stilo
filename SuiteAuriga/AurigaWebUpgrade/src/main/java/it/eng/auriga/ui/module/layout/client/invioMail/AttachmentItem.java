/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.core.client.JavaScriptObject;
import com.smartgwt.client.core.RefDataClass;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.data.RecordList;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.Overflow;
import com.smartgwt.client.types.VerticalAlignment;
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
import com.smartgwt.client.widgets.layout.VLayout;

import it.eng.utility.ui.module.layout.client.common.file.DownloadFile;
import it.eng.utility.ui.module.layout.client.common.items.ImgButtonItem;
import it.eng.utility.ui.module.layout.client.common.items.StaticTextItem;

public class AttachmentItem extends CanvasItem {

	/**
	 * Serve per istanziare la classe tramite GWT
	 * 
	 * @param jsObj
	 */
	public AttachmentItem(JavaScriptObject jsObj) {
		super(jsObj);
	}

	/**
	 * Esegue il build quando l'oggetto non esiste. return AttachmentItem;
	 * 
	 * @param jsObj
	 * @return
	 */
	public AttachmentItem buildObject(JavaScriptObject jsObj) {
		
		AttachmentItem lItem = getOrCreateRef(jsObj);
		return lItem;
	}

	/**
	 * Crea l'istanza o ne restituisce una a partire dall'oggetto Javascript
	 * 
	 * @param jsObj
	 * @return
	 */
	public static AttachmentItem getOrCreateRef(JavaScriptObject jsObj) {
		if (jsObj == null)
			return null;
		RefDataClass obj = RefDataClass.getRef(jsObj);
		if (obj != null) {
			obj.setJsObj(jsObj);
			return (AttachmentItem) obj;
		} else {
			return null;
		}
	}

	/**
	 * Crea un AttachmentItem. In fase di init, lo disegna e ne setta lo showHandler per gestire il setValue
	 * 
	 */
	public AttachmentItem() {
		setAutoDestroy(true); // per eliminare automaticamente il canvas quando elimino il canvasItem
		setInitHandler(new FormItemInitHandler() {

			public void onInit(FormItem item) {
				// Inizializza il componente
				buildObject(item.getJsObj()).disegna(item.getValue());
				// Setto lo showValue (Gestiste il setValue
				addShowValueHandler(new ShowValueHandler() {

					@Override
					public void onShowValue(ShowValueEvent event) {
						// Recupero il valore - è sempre un RecordList
						drawAndSetValue(event.getDataValueAsRecordList());
					}
				});
			}
		});
	}

	protected void disegna(Object value) {
		
		VLayout lVLayout = new VLayout();
		lVLayout.setOverflow(Overflow.AUTO);
		lVLayout.setHeight(30);
		lVLayout.setWidth("*");
		// lVLayout.setBorder("1px solid gray");
		setCanvas(lVLayout);
	}

	private void drawAndSetValue(RecordList dataValueAsRecordList) {

		if (dataValueAsRecordList == null || dataValueAsRecordList.getLength() == 0) {
			setVisible(false);
			return;
		} else {
			setVisible(true);
		}

		VLayout lVLayout = (VLayout) getCanvas();

		List<DynamicForm> lForms = new ArrayList<DynamicForm>(dataValueAsRecordList.getLength());
		for (int i = 0; i < dataValueAsRecordList.getLength(); i++) {

			final Record lRecord = dataValueAsRecordList.get(i);

			DynamicForm lDynamicForm = new DynamicForm();
			lDynamicForm.setWidth100();
			lDynamicForm.setNumCols(4);
			lDynamicForm.setColWidths("1", "1", "1", "*");

			HiddenItem lHiddenItem = new HiddenItem("uriAttach");
			HiddenItem lHiddenItemInfo = new HiddenItem("infoFileAttach");

			StaticTextItem lStaticTextItem = new StaticTextItem();
			lStaticTextItem.setWidth("*");
			lStaticTextItem.setWrap(false);
			lStaticTextItem.setEndRow(false);
			lStaticTextItem.setShowTitle(false);
			lStaticTextItem.setTextAlign(Alignment.LEFT);
			lStaticTextItem.setShowIfCondition(new FormItemIfFunction() {

				@Override
				public boolean execute(FormItem item, Object value, DynamicForm form) {
					return true;// form.getValueAsString("uriAttach")!=null && !form.getValueAsString("uriAttach").trim().equals("");
				}
			});
			lStaticTextItem.setValue(lRecord.getAttributeAsString("fileNameAttach"));

			ImgButtonItem lDownloadButton = new ImgButtonItem("downloadButton", "file/download_manager.png", "Scarica");
			lDownloadButton.setAlwaysEnabled(true);
			lDownloadButton.setColSpan(1);
			lDownloadButton.setIconWidth(16);
			lDownloadButton.setIconHeight(16);
			lDownloadButton.setIconVAlign(VerticalAlignment.CENTER);
			lDownloadButton.setAlign(Alignment.CENTER);
			lDownloadButton.setWidth(16);
			lDownloadButton.setHeight(16);
			lDownloadButton.setStartRow(false);
			lDownloadButton.addIconClickHandler(new IconClickHandler() {

				@Override
				public void onIconClick(IconClickEvent event) {
					manageIconClick(lRecord.getAttributeAsString("fileNameAttach"), lRecord.getAttributeAsString("uriAttach"));
				}
			});
			lDownloadButton.setShowIfCondition(new FormItemIfFunction() {

				@Override
				public boolean execute(FormItem item, Object value, DynamicForm form) {
					return true;// form.getValueAsString("uriAttach")!=null && !form.getValueAsString("uriAttach").trim().equals("");
				}
			});

			lDynamicForm.setItems(lHiddenItem, lHiddenItemInfo, lStaticTextItem, lDownloadButton);
			lDynamicForm.markForRedraw();

			lForms.add(lDynamicForm);
		}

		lVLayout.setHeight(23 * lForms.size());

		lVLayout.setMembers(lForms.toArray(new DynamicForm[lForms.size()]));
		lVLayout.markForRedraw();

	}

	protected void manageIconClick(String display, String uri) {
		Record lRecord = new Record();
		lRecord.setAttribute("displayFilename", display);
		lRecord.setAttribute("uri", uri);
		lRecord.setAttribute("sbustato", "false");
		lRecord.setAttribute("remoteUri", "false");
		DownloadFile.downloadFromRecord(lRecord, "FileToExtractBean");
	}

}
