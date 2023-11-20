/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.SAXException;

import javax.xml.XMLConstants;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import java.io.StringWriter;
import java.net.URL;

/**
 * This abstract class encapsulate the most used feature of the JAXB parsing utilities.
 * It is bound to a specific class of type T. It gives the reference to the marshaller/unmarshaller instances.
 *
 * @param <T> The bound class.
 */
public abstract class XMLGenericHandler<T> {

    protected final Logger logger = LoggerFactory.getLogger(this.getClass());
    private Marshaller marshaller = null;
    private Unmarshaller unmarshaller = null;

    /**
     * Default constructor that takes the bound class of type T as argument.
     *
     * @param boundClass The bound class of type T.
     */
    public XMLGenericHandler(final Class<T> boundClass) {
        super();
        try {
            final JAXBContext jc = JAXBContext.newInstance(boundClass);
            marshaller = jc.createMarshaller();
            unmarshaller = jc.createUnmarshaller();
        } catch (final JAXBException e) {
            logger.error("Error in jaxb marshalling " + boundClass.getName(), e);
        }
    }

    /**
     * Standard getter for the marshaller.
     *
     * @return the marshaller reference.
     */
    public final Marshaller getMarshaller() {
        return marshaller;
    }

    /**
     * Standard getter for the unmarshaller.
     *
     * @return the unmarshaller reference.
     */
    public final Unmarshaller getUnmarshaller() {
        return unmarshaller;
    }

    /**
     * The concrete implementations should gives the XML string representation
     * of the bound object.
     *
     * @param element The XML fragment to be formatted.
     * @return The XML string formatted.
     * @throws JAXBException If some error occurs during marshalling.
     */
    public abstract String toXML(final T element) throws JAXBException;

    /**
     * This method validates a T element using the provided schema URL.
     *
     * @param element   The element to be validated
     * @param schemaUrl The URL of the schema
     * @throws JAXBException If an exception occurs.
     */
    public final void validate(final T element, final URL schemaUrl) throws JAXBException {
        final SchemaFactory sf = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
        try {
            final Schema schema = sf.newSchema(schemaUrl);
            marshaller.setSchema(schema);
            final StringWriter writer = new StringWriter();
            marshaller.marshal(element, writer);
        } catch (final SAXException e) {
            logger.error(e.getMessage(), e);
            throw new JAXBException(e);
        }
    }

}

