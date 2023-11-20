/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.List;

public class FilterSelectLayoutBean {
	private List<ItemFilterBean> fields;
	//Indica quante righe è alto il record. La conversione prevede di prendere il default e moltiplicarlo per 
	//il numero espresso
	private Integer rowsHeight;
	
	public void setFields(List<ItemFilterBean> fields) {
		this.fields = fields;
	}

	public List<ItemFilterBean> getFields() {
		return fields;
	}

	public void setRowsHeight(Integer rowsHeight) {
		this.rowsHeight = rowsHeight;
	}

	public Integer getRowsHeight() {
		return rowsHeight;
	}
}
