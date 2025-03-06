package it.eng.auriga.opentext.service.cs.util;

import com.opentext.livelink.service.documentmanagement.DataValue;
import com.opentext.livelink.service.documentmanagement.IntegerValue;

public class ContentServerIntegerValueCathegory implements IContentServerCathegoryType {

	public void setDataValue(DataValue dataValueToUpdate, Object valueToSet) {
		((IntegerValue) dataValueToUpdate).getValues().clear();
		String valueToSetToString = valueToSet.toString();
		((IntegerValue) dataValueToUpdate).getValues().add(Long.valueOf(valueToSetToString));

	}

	public Long getDataValue(DataValue dataValueToUpdate) {

		return ((IntegerValue) dataValueToUpdate).getValues().get(0);

	}

}
