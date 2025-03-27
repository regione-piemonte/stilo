/* * SPDX-License-Identifier: AGPL-3.0-or-later * * (C) Copyright 2023 Regione Piemonte * */
package it.eng.utility.ui.module.layout.client.common;

import com.smartgwt.client.widgets.grid.ListGridRecord;

public interface UpdateableRecordComponent {
	
	public abstract void updateComponent(ListGridRecord record);

}
