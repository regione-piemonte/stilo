/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.utility.ui.module.core.client.i18n.I18NUtil;
import it.eng.utility.ui.module.layout.client.common.SelectItemFiltrabileEditorNumber;

import java.util.HashMap;
import java.util.Map;

import com.google.gwt.core.client.JavaScriptObject;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.OperatorId;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.Img;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.ButtonItem;
import com.smartgwt.client.widgets.form.fields.CanvasItem;
import com.smartgwt.client.widgets.form.fields.DateItem;
import com.smartgwt.client.widgets.form.fields.FormItem;
import com.smartgwt.client.widgets.form.fields.SpacerItem;
import com.smartgwt.client.widgets.form.fields.TextItem;
import com.smartgwt.client.widgets.form.fields.events.ChangedEvent;
import com.smartgwt.client.widgets.form.fields.events.ChangedHandler;
import com.smartgwt.client.widgets.form.fields.events.ClickEvent;
import com.smartgwt.client.widgets.form.fields.events.ClickHandler;
import com.smartgwt.client.widgets.layout.HStack;

public class FormNumberChooser extends DynamicForm {

	private DynamicForm form;
	private WindowNumberChooser window; 
	private SelectItemFiltrabileEditorNumber father;
	private TextItem lTextItemStart;
	private TextItem lTextItemEnd;

	public SelectItemFiltrabileEditorNumber getFather() {
		return father;
	}

	public void setFather(SelectItemFiltrabileEditorNumber father) {
		this.father = father;
	}

	public FormNumberChooser(WindowNumberChooser pWindowDateChooser, SelectItemFiltrabileEditorNumber formItem){
		form = this;
		setOperator(OperatorId.EQUALS);
		window = pWindowDateChooser;
		setKeepInParentRect(true);

		setWidth100();
		setHeight100();
		setNumCols(4);

		setColWidths(50,75,50,75);
		//		setColWidths(80,120);
		setCellPadding(5);
		lTextItemStart = new TextItem("start", I18NUtil.getMessages().formNumberChooser_start());
		lTextItemStart.setType("int");
		lTextItemStart.setEndRow(false);
		lTextItemStart.setWidth(75);
		lTextItemStart.setAttribute("userInteraction", false);
		lTextItemStart.addChangedHandler(new ChangedHandler() {
			@Override
			public void onChanged(ChangedEvent event) {
				manageTextChanged(event);
			}
		});

		//		lTextItemStart.setColSpan(2);
		lTextItemStart.setTextAlign(Alignment.RIGHT);

		lTextItemEnd = new TextItem("end",I18NUtil.getMessages().formNumberChooser_end());
		lTextItemEnd.setType("int");
		lTextItemEnd.setStartRow(false);
		lTextItemEnd.setWidth(75);
		lTextItemEnd.setAttribute("userInteraction", false);
		lTextItemEnd.setTextAlign(Alignment.RIGHT);
		lTextItemEnd.addChangedHandler(new ChangedHandler() {
			@Override
			public void onChanged(ChangedEvent event) {
				manageTextChanged(event);
			}
		});
		//		lTextItemEnd.setColSpan(2);
		//		lTextItemEnd.setTitleAlign(Alignment.LEFT);

		lTextItemStart.setKeyPressFilter("[0-9]");
		lTextItemEnd.setKeyPressFilter("[0-9]");

		father = formItem;
		HStack lHStack = (HStack)father.getCanvas();
		CanvasItem lCanvasItem = lHStack.getCanvasItem();
		Record lRecord = lCanvasItem.getAttributeAsRecord("valoreMemo");
		if (lRecord!=null){
			String start = lRecord.getAttribute("numberStart").equals("N")?null:lRecord.getAttribute("numberStart");
			String end = lRecord.getAttribute("numberEnd").equals("N")?null:lRecord.getAttribute("numberEnd");

			lTextItemStart.setValue(start);
			lTextItemEnd.setValue(end);
		}

		HStack stack = new HStack();


		ButtonItem lButtonItemOK = buildOK();
		lButtonItemOK.setIcon("ok.png");
		lButtonItemOK.setIconHeight(16);
		lButtonItemOK.setIconWidth(16);
		lButtonItemOK.setStartRow(true);
		lButtonItemOK.setEndRow(false);
		lButtonItemOK.setAutoFit(false);		
		lButtonItemOK.setTop(20);
		lButtonItemOK.setWidth(100);

		//		
		ButtonItem lButtonItemClear = buildClear();
		lButtonItemClear.setIcon("buttons/clear.png");
		lButtonItemClear.setIconHeight(16);
		lButtonItemClear.setIconWidth(16);		
		lButtonItemClear.setStartRow(false);
		lButtonItemClear.setEndRow(false);
		lButtonItemClear.setAutoFit(false);
		lButtonItemClear.setTop(20);
		lButtonItemClear.setWidth(100);

		SpacerItem lSpacerItem = new SpacerItem();
		lSpacerItem.setStartRow(true);
		lSpacerItem.setEndRow(true);

		setAlign(Alignment.CENTER);
		setTop(50);

		setFields(new FormItem[]{lTextItemStart, lTextItemEnd, lButtonItemOK, lButtonItemClear});
		setAlign(Alignment.CENTER);

		//		setTop(50);
	}

	private ButtonItem buildClear() {
		ButtonItem lButtonItemClear = new ButtonItem();
		lButtonItemClear.setName("Clear");
		lButtonItemClear.setTitle(I18NUtil.getMessages().formChooser_clear());
		lButtonItemClear.setColSpan(2);
		lButtonItemClear.setWidth(80);
		lButtonItemClear.setTop(20);
		lButtonItemClear.setAlign(Alignment.CENTER);
		lButtonItemClear.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				form.clearValues();
			}

		});
		return lButtonItemClear;
	}

	private ButtonItem buildClose() {
		ButtonItem lButtonItemClose = new ButtonItem();
		lButtonItemClose.setName("Close");
		lButtonItemClose.setTitle(I18NUtil.getMessages().formChooser_cancel());
		//		lButtonItemClose.setColSpan(2);
		lButtonItemClose.setWidth(80);
		lButtonItemClose.setTop(20);
		lButtonItemClose.setAlign(Alignment.CENTER);
		lButtonItemClose.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				window.markForDestroy();
			}

		});
		return lButtonItemClose;
	}

	private ButtonItem buildOK() {
		ButtonItem lButtonItemOK = new ButtonItem();
		lButtonItemOK.setName("OK");
		lButtonItemOK.setTitle(I18NUtil.getMessages().formChooser_ok());
		lButtonItemOK.setColSpan(2);
		lButtonItemOK.setWidth(80);
		lButtonItemOK.setTop(20);
		lButtonItemOK.setAlign(Alignment.CENTER);
		lButtonItemOK.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				manageOkClick();
			}

		});
		return lButtonItemOK;
	}

	protected void manageOkClick() {
		Integer lIntStart=null;
		Integer lIntEnd=null;
		if (form.getValue("start")!=null){
			if (form.getValue("start") instanceof Integer){
				lIntStart = (Integer)form.getValue("start");
			} else if (form.getValue("start") instanceof String){
				lIntStart = Integer.valueOf((String)form.getValue("start"));
			}
		}
		if (form.getValue("end")!=null){
			if (form.getValue("end") instanceof Integer){
				lIntEnd = (Integer)form.getValue("end");
			} else if (form.getValue("end") instanceof String){
				lIntEnd = Integer.valueOf((String)form.getValue("end"));
			}
		}

		boolean error = false;
		if (lIntStart!=null && lIntEnd!=null){
			Map lMap = new HashMap();
			if (lIntStart>lIntEnd){
				lMap.put("start", I18NUtil.getMessages().formNumberChooser_numberError());
				lMap.put("end", I18NUtil.getMessages().formNumberChooser_numberError());
				form.setErrors(lMap);
				error = true;
			}
		}
		if (!error){
			HStack lHStack = (HStack) father.getCanvas();
			lHStack.getMember(1).setVisible(true);
			Img lImg = (Img) lHStack.getMember(1);
			CanvasItem lCanvasItem = lHStack.getCanvasItem();
			Record lRecord;
			if (lCanvasItem.getValue()==null){
				lRecord = new Record();
			} else lRecord = new Record((JavaScriptObject) lCanvasItem.getValue());
			String start = lIntStart!=null?lIntStart+"":"N";
			String end = lIntEnd!=null?lIntEnd+"":"N";
			lRecord.setAttribute("numberStart", start);
			lRecord.setAttribute("numberEnd", end);
			SC.echo(lRecord.getJsObj());
			lCanvasItem.clearValue();
			lCanvasItem.storeValue(lRecord);
			lCanvasItem.setAttribute("valoreMemo", lRecord);
			if (end.equals("N")){
				lImg.setPrompt("Numero > di " + start);
			} else if (start.equals("N")){
				lImg.setPrompt("Numero < di " + end);
			} else 
				lImg.setPrompt(start + " - " + end);
			lHStack.markForRedraw();
			window.markForDestroy();
		}
	}

	protected void manageTextChanged(ChangedEvent event) {
		TextItem lTextItem = (TextItem)event.getItem();
		lTextItem.setAttribute("userInteraction", true);
		if (lTextItem.getName().equals("start")){
			if (!lTextItemEnd.getAttributeAsBoolean("userInteraction") && lTextItem.getValue()!=null) lTextItemEnd.setValue(lTextItem.getValueAsString());
		} else {
			if (!lTextItemStart.getAttributeAsBoolean("userInteraction") && lTextItem.getValue()!=null) lTextItemStart.setValue(lTextItem.getValueAsString());
		}
	}
}
