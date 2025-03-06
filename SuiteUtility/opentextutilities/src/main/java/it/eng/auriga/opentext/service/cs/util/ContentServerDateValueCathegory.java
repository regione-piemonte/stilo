package it.eng.auriga.opentext.service.cs.util;

import java.util.Date;
import java.util.GregorianCalendar;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;

import com.opentext.livelink.service.documentmanagement.DataValue;
import com.opentext.livelink.service.documentmanagement.DateValue;

public class ContentServerDateValueCathegory implements IContentServerCathegoryType {

	public void setDataValue(DataValue dataValueToUpdate, Object valueToSet) {
		if (valueToSet != null) {
			((DateValue) dataValueToUpdate).getValues().clear();
			GregorianCalendar dateInGCalendar = new GregorianCalendar();

			if (valueToSet instanceof Date) {
				dateInGCalendar.setTimeInMillis(((Date) valueToSet).getTime());
				try {
					((DateValue) dataValueToUpdate).getValues()
							.add(DatatypeFactory.newInstance().newXMLGregorianCalendar(dateInGCalendar));
				} catch (DatatypeConfigurationException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} else if (((DateValue) valueToSet).getValues().size() > 0)
				((DateValue) dataValueToUpdate).getValues().add(((DateValue) valueToSet).getValues().get(0));

		} else
			((DateValue) dataValueToUpdate).getValues().clear();
	}

	public Date getDataValue(DataValue dataValueToUpdate) {

		return ((DateValue) dataValueToUpdate).getValues().get(0).toGregorianCalendar().getTime();
	}
}
