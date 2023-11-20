/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.utility.ui.module.core.client.i18n.I18NUtil;
import it.eng.utility.ui.module.layout.shared.bean.FilterSelectBean;
import it.eng.utility.ui.module.layout.shared.bean.ItemFilterBean;
import it.eng.utility.ui.module.layout.shared.bean.ItemFilterType;

import com.smartgwt.client.data.DataSource;
import com.smartgwt.client.data.fields.DataSourceTextField;
import com.smartgwt.client.widgets.Button;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.IconButton;
import com.smartgwt.client.widgets.grid.CellFormatter;
import com.smartgwt.client.widgets.grid.HoverCustomizer;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.grid.ListGridRecord;

public class SelectItemListGrid extends ListGrid {

	private Button mButton;
	private SelectItemListGrid owner;
	private FilterSelectBean configuration;

	public SelectItemListGrid(FilterSelectBean config) {
		configuration = config;
		owner = this;
		setWidth100();
		setDataPageSize(25);
		setHeight100();
		setAlternateRecordStyles(true);
		setWrapCells(true);
		setShowEmptyMessage(true);
		setEmptyMessage(I18NUtil.getMessages().list_emptyMessage());
		setLeaveScrollbarGap(false);
		setFixedRecordHeights(false);
		setCanReorderFields(true);
		setCanResizeFields(true);
		setCanReorderRecords(false);
		setCanHover(true);
		setAutoFetchData(true);
		setShowRecordComponents(true);
		setShowRecordComponentsByCell(true);
		setShowAllRecords(true);
		// setDataFetchMode(FetchMode.PAGED);
		setCanAutoFitFields(false);
		setNoDoubleClicks(true);
		setCanEdit(false);
		setCanDragRecordsOut(true);
		// setShowHoverComponents(true);
		setShowHeaderContextMenu(true);

		Button lButton = new Button();
		mButton = lButton;

		setFilterButtonProperties(lButton);

		setHoverCustomizer(new HoverCustomizer() {

			@Override
			public String hoverHTML(Object value, ListGridRecord record, int rowNum, int colNum) {
				String lString = "";
				for (ItemFilterBean lItemFilterBean : configuration.getLayout().getFields()) {
					lString += record.getAttribute(lItemFilterBean.getName()) + " ";
				}

				return lString;
			}
		});

		setCellFormatter(new CellFormatter() {

			@Override
			public String format(Object value, ListGridRecord record, int rowNum, int colNum) {
				String fieldName = owner.getFieldName(colNum);
				for (ItemFilterBean lItemFilterBean : configuration.getLayout().getFields()) {
					if (lItemFilterBean.getName().equals(fieldName) && lItemFilterBean.getType().equals(ItemFilterType.BOOLEAN)) {
						if (record.getAttributeAsBoolean(fieldName)) {
							return "<img src=\"http://www.istruzione.it/alfresco/d/d/workspace/SpacesStore/37432b54-f3f8-440f-9c97-af225e854548/freccina_grigiochiaro.png\">";
						}
					}
				}
				return null;
			}
		});

		ListGridField[] fields = new ListGridField[configuration.getLayout().getFields().size()];
		DataSourceTextField[] fieldsData = new DataSourceTextField[configuration.getLayout().getFields().size()];

		int count = 0;
		for (ItemFilterBean lItemFilterBean : configuration.getLayout().getFields()) {
			SelectItemFiltrabileField field = new SelectItemFiltrabileField(lItemFilterBean);
			field.setParent(this);
			DataSourceTextField lDataSourceTextField = new DataSourceTextField(field.getName());
			fields[count] = field;
			fieldsData[count] = lDataSourceTextField;
			count++;
		}
		DataSource lDataSource = new DataSource();
		lDataSource.setFields(fieldsData);
		setDataSource(lDataSource);
		// pickListProperties.setDataSource(lDataSource, fields);
		setDataPageSize(20);
		setFields(fields);
		setUseAllDataSourceFields(true);
	}

	@Override
	protected Canvas createRecordComponent(ListGridRecord record, Integer colNum) {
		String fieldName = this.getFieldName(colNum);
		for (ItemFilterBean lItemFilterBean : configuration.getLayout().getFields()) {
			if (lItemFilterBean.getName().equals(fieldName) && lItemFilterBean.getType().equals(ItemFilterType.BOOLEAN)) {
				if (record.getAttributeAsBoolean(fieldName)) {
					IconButton lIconButton = new IconButton();
					lIconButton.setIcon("check.png");
					return lIconButton;
				}
			}
		}
		return null;
	}
}
