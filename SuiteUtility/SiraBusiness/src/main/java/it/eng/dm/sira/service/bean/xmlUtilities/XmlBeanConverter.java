package it.eng.dm.sira.service.bean.xmlUtilities;

import it.eng.core.business.beans.AbstractBean;

import java.io.StringReader;
import java.io.StringWriter;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

public class XmlBeanConverter {

	private JAXBContext context = null;

	public XmlBeanConverter() {
	}

	public String convertBeanToXml(AbstractBean bean) throws JAXBException {
		context = JAXBContext.newInstance(bean.getClass());
		final Marshaller marshaller = context.createMarshaller();
		// Create a stringWriter to hold the XML
		final StringWriter stringWriter = new StringWriter();
		marshaller.marshal(bean, stringWriter);
		return stringWriter.toString();
	}
	
	public AbstractBean convertXmlToBean(String xml,Class<? extends AbstractBean> beanClass) throws JAXBException{
		context = JAXBContext.newInstance(beanClass);
		final Unmarshaller unmarshaller = context.createUnmarshaller();
		return (AbstractBean) unmarshaller.unmarshal(new StringReader(xml)); 
		}

}
