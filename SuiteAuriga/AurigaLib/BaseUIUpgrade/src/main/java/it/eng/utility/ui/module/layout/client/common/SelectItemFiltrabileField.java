/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.utility.ui.module.core.client.UserInterfaceFactory;
import it.eng.utility.ui.module.layout.shared.bean.ItemFilterBean;

import com.smartgwt.client.data.Record;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.DateDisplayFormat;
import com.smartgwt.client.types.ListGridFieldType;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.FormItem;
import com.smartgwt.client.widgets.form.fields.TextItem;
import com.smartgwt.client.widgets.grid.CellFormatter;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.grid.ListGridRecord;

public class SelectItemFiltrabileField extends ListGridField {

	private FormItem formEditor;
	private ListGrid parent;
	private SelectItemFiltrabileField field;
	private SelectItemFiltrabile _owner;

	public SelectItemFiltrabileField(final ItemFilterBean lItemFilterBean) {
		field = this;
		setName(lItemFilterBean.getName());
		if (lItemFilterBean.isValue()) {
			setHidden(true);
			setCanHide(false);
			setCanFilter(false);
		}
		switch (lItemFilterBean.getType()) {
		case HIDDEN:
			setHidden(true);
			setCanHide(false);
			setCanFilter(false);
			break;
		case DATE:
			SelectItemFiltrabileEditorData lSelectItemFiltrableEditorData = new SelectItemFiltrabileEditorData();
			setType(ListGridFieldType.DATE);
			lSelectItemFiltrableEditorData.setDateFormatter(DateDisplayFormat.TOEUROPEANSHORTDATETIME);
			setDateFormatter(DateDisplayFormat.TOEUROPEANSHORTDATETIME);
			formEditor = lSelectItemFiltrableEditorData;
			setCanFilter(true);
			setFilterOnKeypress(true);
			setAlign(Alignment.CENTER);
			break;
		case TEXT:
			formEditor = new TextItem();
			setCanFilter(true);
			setFilterOnKeypress(true);
			setAlign(Alignment.LEFT);
			break;
		case NUMBER:
			formEditor = new SelectItemFiltrabileEditorNumber();
			setCanFilter(true);
			setFilterOnKeypress(true);
			setAlign(Alignment.RIGHT);
			break;
		case BOOLEAN:
			setCanFilter(false);
			setName(lItemFilterBean.getName() + "_realValue");
			setAlign(Alignment.CENTER);
			break;
		case ICON:
			setCanFilter(false);
			setHeaderBaseStyle(it.eng.utility.Styles.hiddenHeaderButton);
			if (lItemFilterBean.getParamDBShowIf() != null && !"".equals(lItemFilterBean.getParamDBShowIf())
					&& !UserInterfaceFactory.getParametroDBAsBoolean(lItemFilterBean.getParamDBShowIf())) {
				setHidden(true);
			}
			setCellFormatter(new CellFormatter() {

				@Override
				public String format(Object value, ListGridRecord record, int rowNum, int colNum) {
					if (record.getAttribute(getName()) != null && !"".equals(record.getAttributeAsString(getName()))) {
						String prefix = lItemFilterBean.getPrefix() != null ? lItemFilterBean.getPrefix() : "";
						String suffix = lItemFilterBean.getSuffix() != null ? lItemFilterBean.getSuffix() : "";
						return "<div align=\"center\"><img src=\"" + prefix + record.getAttribute(getName()) + suffix
								+ "\" height=\"16\" width=\"16\" alt=\"\" /></div>";
					}
					return null;
				}
			});
			setAlign(Alignment.CENTER);
			break;
		}
		if (formEditor != null) {
			setFilterEditorType(formEditor);
			setFilterEditorProperties(formEditor);
			formEditor.setTitle(lItemFilterBean.getTitle());
		}
		setTitle(lItemFilterBean.getTitle());
	}

	protected String manageFormatDate(Object value, Record record, DynamicForm form, FormItem item) {
		return null;
	}

	public SelectItemFiltrabileField(ItemFilterBean lItemFilterBean, SelectItemFiltrabile _instance) {
		this(lItemFilterBean);
		_owner = _instance;
	}

	public void setParent(ListGrid parent) {
		this.parent = parent;
	}

	public ListGrid getParent() {
		return parent;
	}

	public void setEditor(FormItem editor) {
		this.formEditor = editor;
	}

	public FormItem getEditor() {
		return formEditor;
	}

}
