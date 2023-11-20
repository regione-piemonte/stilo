/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.utility.ui.module.core.shared.annotation.JSONBean;

import java.util.ArrayList;
import java.util.List;

@JSONBean
public class PortletHistory {
	
	
	private List<HistoryBean> portlets = new ArrayList<HistoryBean>();

	public List<HistoryBean> getPortlets() {
		return portlets;
	}

	public void setPortlets(List<HistoryBean> portlets) {
		this.portlets = portlets;
	}
	
	
	
}
