/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.StringReader;
import java.io.StringWriter;

/**
 * This class encapsulate the most used feature of the JAXB parsing utilities.
 * It is bound to a specific class of type T. It simply gives the reference to the marshaller/unmarshaller instances.
 *
 * @param <T> The bound class.
 */
public class XMLHandler<T> extends XMLGenericHandler<T> {

    /**
     * Default constructor that takes the bound class of type T as argument.
     *
     * @param boundClass The bound class of type T.
     */
    public XMLHandler(final Class<T> boundClass) {
        super(boundClass);
    }

    /**
     * It simply gives the XML string representation using the formatted output.
     *
     * @param element The XML fragment to be formatted.
     * @return The XML string formatted.
     * @throws JAXBException If some error occurs during marshalling.
     */
    @Override
    public final String toXML(final T element) throws JAXBException {
        final StringWriter sw = new StringWriter();
        getMarshaller().setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
        getMarshaller().marshal(element, sw);
        return sw.toString();
    }

    /**
     * It transforms a given XML in the XML fragment element associated to its
     * bound class.
     *
     * @param xml The XML to be parsed.
     * @return The XML fragment object.
     * @throws JAXBException If some error occurs during unmarshalling.
     */
    @SuppressWarnings("unchecked")
    public final T toElement(final String xml) throws JAXBException {
        return (T) getUnmarshaller().unmarshal(new StringReader(xml));
    }

}
