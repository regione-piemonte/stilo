/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.filter.ElementFilter;
import org.jdom.input.SAXBuilder;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * This is a basic XML parser that can be used in order to parse XML contents
 * without JAXB mapping into the project. It is based on JDOM.
 */
public class XMLBasicParser {

    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * Build a JDOM document starting from a generic reader.
     *
     * @param is The reader to be used for parsing XML contents.
     * @return The JDOM document
     * @throws IOException   If a parsing exception occurs
     * @throws JDOMException If a parsing exception occurs
     */
    public final Document parse(final Reader is) throws IOException, JDOMException {
        final SAXBuilder builder = new SAXBuilder();
        builder.setValidation(false);
        builder.setFeature("http://xml.org/sax/features/validation", false);
        builder.setFeature("http://apache.org/xml/features/nonvalidating/load-dtd-grammar", false);
        builder.setFeature("http://apache.org/xml/features/nonvalidating/load-external-dtd", false);
        logger.info("[XML][Reading XML file][Validation disabled]");

        try {
            return builder.build(is);
        } catch (final IOException | JDOMException e) {
            logger.error(e.getMessage(), e);
            throw e;
        }
    }

    /**
     * Set the given text to the Element identified by given element and child.
     *
     * @param document    The JDOM document to modify
     * @param elementName The Element name to search
     * @param childName   The child Element name to search
     * @param text        The text for child Element
     */
    public final void setText(final Document document, final String elementName, final String childName,
                              final String text) {
        // Iterate over all {elementName} elements
        final Iterator<Element> iterator = document.getDescendants(new ElementFilter(elementName));

        // Snapshot these {elementName} elements before modification
        final List<Element> elements = new ArrayList<>();
        while (iterator.hasNext()) {
            final Element element = iterator.next();
            final Element child = element.getChild(childName);
            if (child != null) {
                logger.debug("ElementFound[" + element.getName() + "]-TextFound[" + child.getText() + "]");
                elements.add(element);
            }
        }

        // Set text to {elementName} elements' {childName} child
        elements.forEach(element -> element.getChild(childName).setText(text));
    }

    /**
     * Utility method formatting the given document into specified format.
     *
     * @param document The original document
     * @return The formatted xml
     */
    public final String format(final Document document, final Format format) {
        final XMLOutputter outputter = new XMLOutputter();
        outputter.setFormat(format);
        return outputter.outputString(document);

    }

}
