/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.HashMap;
import java.util.Map;

import com.google.gwt.core.client.JavaScriptObject;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.widgets.Img;
import com.smartgwt.client.widgets.events.VisibilityChangedEvent;
import com.smartgwt.client.widgets.events.VisibilityChangedHandler;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.ButtonItem;
import com.smartgwt.client.widgets.form.fields.CanvasItem;
import com.smartgwt.client.widgets.form.fields.FormItem;
import com.smartgwt.client.widgets.form.fields.PickerIcon;
import com.smartgwt.client.widgets.form.fields.StaticTextItem;
import com.smartgwt.client.widgets.form.fields.events.FormItemClickHandler;
import com.smartgwt.client.widgets.form.fields.events.FormItemIconClickEvent;
import com.smartgwt.client.widgets.form.fields.events.FormItemInitHandler;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.layout.HStack;

import it.eng.utility.ui.module.layout.client.Layout;
import it.eng.utility.ui.module.layout.client.date.WindowNumberChooser;

public class SelectItemFiltrabileEditorNumber extends CanvasItem{

	private SelectItemFiltrabileField selectItemFiltrableField;
	private ListGrid lista;
	static SelectItemFiltrabileEditorNumber _instance;
	private SelectItemFiltrabileEditorNumber owner;
	private DynamicForm form;
	private HStack _layout;

	public SelectItemFiltrabileEditorNumber(){
		setAutoDestroy(true); // per eliminare automaticamente il canvas quando elimino il canvasItem
		setInitHandler(new FormItemInitHandler() {
			public void onInit(FormItem item) {
				//Inizializza il componente
				buildObject(item.getJsObj()).disegna();

			}
		});	
	}	

	public SelectItemFiltrabileEditorNumber(JavaScriptObject jsObj) {
		super(jsObj);// TODO Auto-generated constructor stub
	}

	public SelectItemFiltrabileEditorNumber buildObject(JavaScriptObject jsObj) {
		
		SelectItemFiltrabileEditorNumber lItem = new SelectItemFiltrabileEditorNumber(jsObj);
		return lItem;
	}


	protected void disegna() {
		_layout = new HStack(2);
		form = new DynamicForm();
		form.setWidth(20);

		final StaticTextItem lStaticTextItem = new StaticTextItem("number");
		ButtonItem lButtonItem = new ButtonItem("bottone");
		lButtonItem.setIcon(Layout.getGenericConfig().getFilterAppliedIcon());
		lStaticTextItem.setShowTitle(false);
		Map<String, String> lMap = new HashMap<String, String>();
		lMap.put("1", Layout.getGenericConfig().getFilterAppliedIcon());
		lStaticTextItem.setValueIcons(lMap);
		lStaticTextItem.setShowValueIconOnly(false);
		setShouldSaveValue(true);
		owner = this;
		setEditCriteriaInInnerForm(false);
		PickerIcon lPickerIcon = new PickerIcon(PickerIcon.SEARCH, new FormItemClickHandler() {

			@Override
			public void onFormItemClick(FormItemIconClickEvent event) {
				WindowNumberChooser lWindowNumberChooser = new WindowNumberChooser(owner.getTitle(), owner);
				lWindowNumberChooser.show();
				lWindowNumberChooser.getForm().focusInItem("start");


			}
		});

		final Img img = new Img();
		img.setParentElement(_layout);
		img.setShowHover(true);

		//        img.setID("imbutoImage");
		img.setWidth(16);
		img.setHeight(16);
		img.setVisible(false);
		img.setSrc("buttons/imbuto.png");
		img.setAlign(Alignment.CENTER);

		//        img.draw();
		lButtonItem.setStartRow(false);
		lButtonItem.setTitle("aa");
		lButtonItem.setVisible(false);

		lStaticTextItem.setIcons(lPickerIcon);
		//		form.setFields(lStaticTextItem,lButtonItem);
		form.setFields(lStaticTextItem);
		//		img.draw();
		_layout.addMember(form);
		_layout.addMember(img);
		_layout.setCanHover(true);
		_layout.setPrompt("Bellaaaaaa");
		_layout.addVisibilityChangedHandler(new VisibilityChangedHandler() {

			@Override
			public void onVisibilityChanged(VisibilityChangedEvent event) {
				manageChangedVisibility(event);

			}
		});
		setCanvas(_layout);

	}




	protected void manageChangedVisibility(VisibilityChangedEvent event) {
		if (event.getIsVisible()){
			HStack lHStack = (HStack)event.getSource();
			Img lImg = (Img) lHStack.getMember(1);
			lImg.setVisible(false);
			CanvasItem lCanvasItem = lHStack.getCanvasItem();
			Record lRecord= new Record();
			lRecord.setAttribute("numberStart", "N");
			lRecord.setAttribute("numberEnd", "N");
			lCanvasItem.storeValue(lRecord);
			lCanvasItem.setAttribute("valoreMemo", lRecord);
		}
		
	}

	public void setSelectItemFiltrableField(SelectItemFiltrabileField selectItemFiltrableField) {
		this.selectItemFiltrableField = selectItemFiltrableField;
	}

	public SelectItemFiltrabileField getSelectItemFiltrableField() {
		return selectItemFiltrableField;
	}

	public void setLista(ListGrid lista) {
		this.lista = lista;
	}

	public ListGrid getLista() {
		return lista;
	}
}

