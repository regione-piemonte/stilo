package it.eng.applet.util;

import it.eng.applet.bean.PrintBean;

import java.io.StringWriter;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

public class SingletonJaxbMarshaller {

	private static Marshaller marshaller;

	public static String transformToXml(PrintBean pPrintBean) throws JAXBException{
		if (marshaller == null){
			initMarshaller();
		}
		StringWriter lStringWriter = new StringWriter();
		marshaller.marshal(pPrintBean, lStringWriter);
		return lStringWriter.toString();

	}

	private static void initMarshaller() throws JAXBException {
		marshaller = JAXBContext.newInstance(new Class[]{PrintBean.class}).createMarshaller();
		marshaller.setProperty( Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE );
	}

}
