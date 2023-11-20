/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.List;

public class FilterBean {

	private List<FilterFieldBean> fields;
	private Boolean notAbleToManageAddFilter;

	public void setFields(List<FilterFieldBean> fields) {
		this.fields = fields;
	}

	public List<FilterFieldBean> getFields() {
		return fields;
	}

	public void setNotAbleToManageAddFilter(Boolean ableToManageAddFilter) {
		this.notAbleToManageAddFilter = ableToManageAddFilter;
	}

	public Boolean getNotAbleToManageAddFilter() {
		return notAbleToManageAddFilter;
	}
	
}
