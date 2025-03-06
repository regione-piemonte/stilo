package it.eng.auriga.opentext.service.cs.util;

import com.opentext.livelink.service.documentmanagement.BooleanValue;
import com.opentext.livelink.service.documentmanagement.DataValue;

public class ContentServerBooleanValueCathegory implements IContentServerCathegoryType {

	
	public void setDataValue(DataValue dataValueToUpdate, Object valueToSet) {
		((BooleanValue)dataValueToUpdate).getValues().clear();
		String valueToSetToString = valueToSet.toString();
		((BooleanValue)dataValueToUpdate).getValues().add(Boolean.valueOf(valueToSetToString));
	}

	
	public Object getDataValue(DataValue dataValueToUpdate) {
		// TODO Auto-generated method stub
		return ((BooleanValue)dataValueToUpdate).getValues().get(0);
	}

}
