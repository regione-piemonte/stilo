/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.List;

public class FilterSpecBean {

	private List<FilterFieldSpecBean> fields;

	public List<FilterFieldSpecBean> getFields() {
		return fields;
	}

	public void setFields(List<FilterFieldSpecBean> fields) {
		this.fields = fields;
	}
	
}