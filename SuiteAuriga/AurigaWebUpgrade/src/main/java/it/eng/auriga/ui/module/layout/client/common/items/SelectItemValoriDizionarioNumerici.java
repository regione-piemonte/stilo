/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.grid.ListGridRecord;
import com.smartgwt.client.widgets.grid.SortNormalizer;

public class SelectItemValoriDizionarioNumerici extends SelectItemValoriDizionario {
	
	public SelectItemValoriDizionarioNumerici(String name, String title, String dictionaryEntry) {
		this(name, title, dictionaryEntry, false, false);
	}
	
	public SelectItemValoriDizionarioNumerici(String name, String title, String dictionaryEntry, boolean multiple) {
		this(name, title, dictionaryEntry, multiple, false);
	}
	
	public SelectItemValoriDizionarioNumerici(String name, String title, String dictionaryEntry, boolean multiple, boolean requiredStrInDes) {
	
		super(name, title, dictionaryEntry, multiple, requiredStrInDes);
		
		ListGridField keyField = new ListGridField("key");
		keyField.setHidden(true);
		
		ListGridField valueField = new ListGridField("value");
		valueField.setSortNormalizer(new SortNormalizer() {
			
			@Override
			public Object normalize(ListGridRecord record, String fieldName) {
				return record.getAttribute("key") != null && !"".equals(record.getAttribute("key").trim()) ? Long.valueOf(record.getAttribute("key").trim().replace(".", "")).longValue() : -1;
			}
		});
		setPickListFields(keyField, valueField);
		
		setSortField("value");
	}
	
}