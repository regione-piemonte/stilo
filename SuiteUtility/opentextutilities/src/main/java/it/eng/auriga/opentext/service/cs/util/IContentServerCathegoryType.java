package it.eng.auriga.opentext.service.cs.util;

import com.opentext.livelink.service.documentmanagement.DataValue;

public interface IContentServerCathegoryType {
	
	public void setDataValue(DataValue dataValueToUpdate, Object valueToSet);

	Object getDataValue(DataValue dataValueToUpdate);

}
