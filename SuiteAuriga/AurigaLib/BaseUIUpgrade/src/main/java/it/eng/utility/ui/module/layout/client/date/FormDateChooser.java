/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.utility.ui.module.core.client.i18n.I18NUtil;
import it.eng.utility.ui.module.layout.client.Layout;
import it.eng.utility.ui.module.layout.client.common.SelectItemFiltrabileEditorData;

import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.smartgwt.client.data.Criterion;
import com.smartgwt.client.data.DateRange;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.DateDisplayFormat;
import com.smartgwt.client.types.OperatorId;
import com.smartgwt.client.util.DateDisplayFormatter;
import com.smartgwt.client.util.DateUtil;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.Button;
import com.smartgwt.client.widgets.Dialog;
import com.smartgwt.client.widgets.Img;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.ButtonItem;
import com.smartgwt.client.widgets.form.fields.CanvasItem;
import com.smartgwt.client.widgets.form.fields.DateItem;
import com.smartgwt.client.widgets.form.fields.DateRangeItem;
import com.smartgwt.client.widgets.form.fields.FormItem;
import com.smartgwt.client.widgets.form.fields.SpacerItem;
import com.smartgwt.client.widgets.form.fields.StaticTextItem;
import com.smartgwt.client.widgets.form.fields.TextItem;
import com.smartgwt.client.widgets.form.fields.events.ChangedEvent;
import com.smartgwt.client.widgets.form.fields.events.ChangedHandler;
import com.smartgwt.client.widgets.form.fields.events.ClickEvent;
import com.smartgwt.client.widgets.form.fields.events.ClickHandler;
import com.smartgwt.client.widgets.form.fields.events.IconClickEvent;
import com.smartgwt.client.widgets.form.fields.events.IconClickHandler;
import com.smartgwt.client.widgets.form.fields.events.KeyPressEvent;
import com.smartgwt.client.widgets.form.fields.events.KeyPressHandler;
import com.smartgwt.client.widgets.form.fields.events.KeyUpEvent;
import com.smartgwt.client.widgets.form.fields.events.KeyUpHandler;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.HStack;

public class FormDateChooser extends DynamicForm {

	private DynamicForm form;
	private WindowDateChooser window; 
	private SelectItemFiltrabileEditorData father;
	private StaticTextItem staticText;

	private DateItem dateItemStart;
	private DateItem dateItemEnd;

	private DateRange oldValue;

	public SelectItemFiltrabileEditorData getFather() {
		return father;
	}

	public void setFather(SelectItemFiltrabileEditorData father) {
		this.father = father;
	}

	public FormDateChooser(WindowDateChooser pWindowDateChooser, SelectItemFiltrabileEditorData formItem, StaticTextItem lStaticTextItem){
		form = this;
		setOperator(OperatorId.EQUALS);
		window = pWindowDateChooser;
		staticText = lStaticTextItem;
		setKeepInParentRect(true);
		setWidth100();
		setHeight100();
		setNumCols(4);

		setColWidths(75,75,75,75);
		setCellPadding(5);
		dateItemStart = new DateItem("dateStart");
		dateItemStart.setTitle(I18NUtil.getMessages().formDateChooser_start());
		dateItemStart.setDateFormatter(DateDisplayFormat.TOEUROPEANSHORTDATE);
		dateItemStart.setUseTextField(true);
		dateItemStart.setEndRow(true);
		dateItemStart.setColSpan(4);
		dateItemStart.addChangedHandler(new ChangedHandler() {

			@Override
			public void onChanged(ChangedEvent event) {
				DateItem lDateItem = (DateItem)event.getItem();

				if (dateItemEnd.getValue()==null && lDateItem.getValue()!=null) dateItemEnd.setValue(lDateItem.getValueAsDate());

			}
		});

		dateItemEnd = new DateItem("dateEnd");
		dateItemEnd.setTitle(I18NUtil.getMessages().formDateChooser_end());
		dateItemEnd.setDateFormatter(DateDisplayFormat.TOEUROPEANSHORTDATE);
		dateItemEnd.setUseTextField(true);
		dateItemEnd.setColSpan(4);
		dateItemEnd.addChangedHandler(new ChangedHandler() {

			@Override
			public void onChanged(ChangedEvent event) {
				DateItem lDateItem = (DateItem)event.getItem();

				if (dateItemStart.getValue()==null && lDateItem.getValue()!=null) dateItemStart.setValue(lDateItem.getValueAsDate());

			}
		});

		father = formItem;
		HStack lHStack = (HStack)father.getCanvas();
		CanvasItem lCanvasItem = lHStack.getCanvasItem();
		Record lRecord = lCanvasItem.getAttributeAsRecord("valoreMemo");
		if (lRecord!=null){
		String start = lRecord.getAttribute("lStrStartDate");
		String end = lRecord.getAttribute("lStrEndDate");
		Date _startDate = DateUtil.parseInput(start);
		Date _endDate = DateUtil.parseInput(end);
		dateItemStart.setValue(_startDate);
		dateItemEnd.setValue(_endDate);
		}
		

		ButtonItem lButtonItemOK = buildOK();
		lButtonItemOK.setIcon("ok.png");
		lButtonItemOK.setIconHeight(16);
		lButtonItemOK.setIconWidth(16);
		lButtonItemOK.setStartRow(true);
		lButtonItemOK.setEndRow(false);
		lButtonItemOK.setAutoFit(false);		
		lButtonItemOK.setTop(20);
		lButtonItemOK.setWidth(100);
				
		ButtonItem lButtonItemClear = buildClear();
		lButtonItemClear.setIcon("buttons/clear.png");
		lButtonItemClear.setIconHeight(16);
		lButtonItemClear.setIconWidth(16);		
		lButtonItemClear.setStartRow(false);
		lButtonItemClear.setEndRow(false);
		lButtonItemClear.setAutoFit(false);
		lButtonItemClear.setTop(20);
		lButtonItemClear.setWidth(100);
		
		setFields(new FormItem[]{dateItemStart, dateItemEnd, lButtonItemOK, lButtonItemClear});
		setAlign(Alignment.CENTER);
		setTop(50);
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
		lButtonItemClose.setColSpan(2);
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
	
	public void manageOkClick(){
//		DateUtil.setShortDateDisplayFormat(DateDisplayFormat.TOEUROPEANSHORTDATE);
		DateUtil.setShortDateDisplayFormatter(new DateDisplayFormatter() {
			
			@Override
			public String format(Date date) {
				if(date == null) return null;
                //you'll probably want to create the DateTimeFormat outside this method.
                //here for illustration purposes        
                DateTimeFormat dateFormatter = DateTimeFormat.getFormat("dd/MM/yyyy");
                String format = dateFormatter.format(date);
                return format;
			}
		});
		DateRange lDateRange = new DateRange();
		Date lDateStart = (Date) form.getValue("dateStart");
		Date lDateEnd = (Date) form.getValue("dateEnd");
		lDateRange.setStartDate(lDateStart);
		lDateRange.setEndDate(lDateEnd);
		boolean hasError = false;
		if (lDateRange.getStartDate()!=null && lDateRange.getEndDate()!=null){
			long start = lDateRange.getStartDate().getTime();
			long end = lDateRange.getEndDate().getTime();
			if (start>end){
				hasError = true;
				Map lMap = new HashMap();
				lMap.put("dateStart", I18NUtil.getMessages().formDateChooser_dateError());
				lMap.put("dateEnd", I18NUtil.getMessages().formDateChooser_dateError());
				form.setErrors(lMap);				
			}
		}
		
		if (!hasError){
		if (lDateRange.getStartDate()!=null || lDateRange.getEndDate()!=null){
			String lStrStartDate = null;
			String lStrEndDate = null;
			if (lDateRange.getStartDate()==null){
				lStrStartDate = "N";
			} else lStrStartDate = DateUtil.formatAsShortDate(lDateRange.getStartDate());
			if (lDateRange.getEndDate()==null){
				lStrEndDate = "N";
			} else lStrEndDate = DateUtil.formatAsShortDate(lDateRange.getEndDate());
			HStack lHStack = (HStack) father.getCanvas();
			lHStack.getMember(1).setVisible(true);
			Img lImg = (Img) lHStack.getMember(1);
//			final Img img = new Img();
//			img.setParentElement(lHStack);
//	        img.setID("imbutoImage");
//	        img.setWidth(16);
//	        img.setHeight(16);
//	        
//	        img.setSrc("buttons/imbuto.png");
////	        img.draw();
//	        lHStack.addMember(img);
//			lHStack.getMember("imbutoImage").setVisible(false);
//			DynamicForm lform = (DynamicForm)father.getCanvas();
//			lform.getField("bottone").setVisible(true);
//			lform.markForRedraw();
//			CanvasItem lCanvasItem = lform.getCanvasItem();
			CanvasItem lCanvasItem = lHStack.getCanvasItem();
			Record lRecord;
			if (lCanvasItem.getValue()==null){
				lRecord = new Record();
			} else lRecord = new Record((JavaScriptObject) lCanvasItem.getValue());
			lRecord.setAttribute("lStrStartDate", lStrStartDate);
			lRecord.setAttribute("lStrEndDate", lStrEndDate);
			SC.echo(lRecord.getJsObj());
			lCanvasItem.clearValue();
			lCanvasItem.storeValue(lRecord);
			lCanvasItem.setAttribute("valoreMemo", lRecord);
			if (lStrEndDate.equals("N")){
				lImg.setPrompt("Data > di " + lStrStartDate);
			} else if (lStrStartDate.equals("N")){
				lImg.setPrompt("Data < di " + lStrEndDate);
			} else 
				lImg.setPrompt(lStrStartDate + " - " + lStrEndDate);
			lHStack.markForRedraw();
		} else {
			father.setValue("");
			HStack lHStack = (HStack)father.getCanvas();
			lHStack.getMember(1).setVisible(false);
			
			CanvasItem lCanvasItem = lHStack.getCanvasItem();
			Record lRecord= new Record();
			lRecord.setAttribute("lStrStartDate", "N");
			lRecord.setAttribute("lStrEndDate", "N");
			lCanvasItem.storeValue(lRecord);
			lCanvasItem.setAttribute("valoreMemo", lRecord);
		}


		window.markForDestroy();
		}
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

	public void setOldValue(DateRange oldValue) {
		this.oldValue = oldValue;
	}

	public DateRange getOldValue() {
		return oldValue;
	}
}
