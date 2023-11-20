/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.HashMap;
import java.util.Map;

import com.google.gwt.core.client.JavaScriptObject;
import com.smartgwt.client.data.Criterion;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.widgets.Img;
import com.smartgwt.client.widgets.events.VisibilityChangedEvent;
import com.smartgwt.client.widgets.events.VisibilityChangedHandler;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.FormItemCanEditCriterionPredicate;
import com.smartgwt.client.widgets.form.FormItemHoverFormatter;
import com.smartgwt.client.widgets.form.fields.ButtonItem;
import com.smartgwt.client.widgets.form.fields.CanvasItem;
import com.smartgwt.client.widgets.form.fields.FormItem;
import com.smartgwt.client.widgets.form.fields.FormItemIcon;
import com.smartgwt.client.widgets.form.fields.PickerIcon;
import com.smartgwt.client.widgets.form.fields.StaticTextItem;
import com.smartgwt.client.widgets.form.fields.events.FormItemClickHandler;
import com.smartgwt.client.widgets.form.fields.events.FormItemIconClickEvent;
import com.smartgwt.client.widgets.form.fields.events.FormItemInitHandler;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.layout.HStack;

import it.eng.utility.ui.module.layout.client.Layout;
import it.eng.utility.ui.module.layout.client.date.WindowDateChooser;

public class SelectItemFiltrabileEditorData extends CanvasItem{

	private SelectItemFiltrabileField selectItemFiltrableField;
	private ListGrid lista;
	private SelectItemFiltrabileEditorData owner;
	private DynamicForm form;
	private HStack _layout;

	private FormItemIcon visualizzaXmlDatiEventoIcon;

	public SelectItemFiltrabileEditorData(){
		setAutoDestroy(true); // per eliminare automaticamente il canvas quando elimino il canvasItem
		setInitHandler(new FormItemInitHandler() {
			public void onInit(FormItem item) {
				//Inizializza il componente
				buildObject(item.getJsObj()).disegna();

			}
		});	
		setRedrawOnChange(true);
		setCanEditCriterionPredicate(new FormItemCanEditCriterionPredicate() {
			
			@Override
			public boolean canEditCriterion(DynamicForm form, FormItem item, Criterion criterion) {
				return true;
			}
		});
	}

	protected void disegna() {
		setItemHoverFormatter(new FormItemHoverFormatter() {

			@Override
			public String getHoverHTML(FormItem item, DynamicForm form) {
				return manageHoverHTML(item, form);
			}
		});
		_layout = new HStack(2);
		form = new DynamicForm();
		form.setWidth(20);

		final StaticTextItem lStaticTextItem = new StaticTextItem("data");
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
		PickerIcon lPickerIcon = new PickerIcon(PickerIcon.DATE, new FormItemClickHandler() {

			@Override
			public void onFormItemClick(FormItemIconClickEvent event) {
				WindowDateChooser lWindowDateChooser = new WindowDateChooser(owner.getTitle(), owner, lStaticTextItem);
				lWindowDateChooser.show();


			}
		});

		final Img img = new Img();
		img.setParentElement(_layout);
		img.setShowHover(true);
		img.setWidth(16);
		img.setHeight(16);
		img.setVisible(false);
		img.setSrc("buttons/imbuto.png");
		img.setAlign(Alignment.CENTER);

		lButtonItem.setStartRow(false);
		lButtonItem.setTitle("aa");
		lButtonItem.setVisible(false);

		lStaticTextItem.setIcons(lPickerIcon);
		form.setFields(lStaticTextItem);
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
			lRecord.setAttribute("lStrStartDate", "N");
			lRecord.setAttribute("lStrEndDate", "N");
			lCanvasItem.storeValue(lRecord);
			lCanvasItem.setAttribute("valoreMemo", lRecord);
		}
	}

	public SelectItemFiltrabileEditorData(JavaScriptObject jsObj) {
		super(jsObj);
	}

	public SelectItemFiltrabileEditorData buildObject(JavaScriptObject jsObj) {
		SelectItemFiltrabileEditorData lItem = new SelectItemFiltrabileEditorData(jsObj);
		return lItem;
	}

	protected String manageHoverHTML(FormItem item, DynamicForm form2) {
		return null;
	}
	
//	@Override
//	public Boolean canEditCriterion(Criterion criterion) {
//		return true;
//	}
	
//	@Override
//	public Criterion getCriterion() {
//		return new Criterion("prova", OperatorId.EQUALS, new Random().nextInt());
//	}

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
