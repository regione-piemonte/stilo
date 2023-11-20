/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import com.smartgwt.client.widgets.grid.ListGridRecord;

public interface UpdateableRecordComponent {
	
	public abstract void updateComponent(ListGridRecord record);

}
