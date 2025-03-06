package it.eng.auriga.opentext.service.cs.util;

import com.opentext.livelink.service.documentmanagement.DataValue;
import com.opentext.livelink.service.documentmanagement.StringValue;

public class ContentServerStringValueCathegory implements IContentServerCathegoryType {

	public void setDataValue(DataValue dataValueToUpdate, Object valueToSet) {

		((StringValue) dataValueToUpdate).getValues().clear();
		if (valueToSet instanceof StringValue)
			valueToSet = ((StringValue) valueToSet).getValues().size() > 0
					? ((StringValue) valueToSet).getValues().get(0)
					: null;

		if (valueToSet != null)
			((StringValue) dataValueToUpdate).getValues().add(valueToSet.toString());
		else
			((StringValue) dataValueToUpdate).getValues().add(null);

	}

	public String getDataValue(DataValue dataValue) {
		String valueToRetrieve = null;
		if (dataValue instanceof StringValue)
			valueToRetrieve = ((StringValue) dataValue).getValues().size() > 0
					? ((StringValue) dataValue).getValues().get(0)
					: null;

		return valueToRetrieve;
	}

}
