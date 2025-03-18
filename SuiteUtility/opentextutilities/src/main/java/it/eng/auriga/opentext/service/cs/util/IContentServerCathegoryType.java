/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import com.opentext.livelink.service.documentmanagement.DataValue;

public interface IContentServerCathegoryType {
	
	public void setDataValue(DataValue dataValueToUpdate, Object valueToSet);

	Object getDataValue(DataValue dataValueToUpdate);

}
