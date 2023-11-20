/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.Map;

public class AllFilterSelectBean {

	private Map<String, FilterSelectBean> selects;

	public void setSelects(Map<String, FilterSelectBean> selects) {
		this.selects = selects;
	}

	public Map<String, FilterSelectBean> getSelects() {
		return selects;
	}
}
