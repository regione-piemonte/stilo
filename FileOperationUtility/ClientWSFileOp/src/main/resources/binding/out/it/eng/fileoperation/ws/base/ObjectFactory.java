/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vJAXB 2.1.10 in JDK 6 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2013.06.27 at 05:17:41 PM CEST 
//


package it.eng.fileoperation.ws.base;

import javax.xml.bind.annotation.XmlRegistry;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the it.eng.fileoperation.ws.base package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {


    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: it.eng.fileoperation.ws.base
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link AbstractResponseOperationType.Warnings }
     * 
     */
    public AbstractResponseOperationType.Warnings createAbstractResponseOperationTypeWarnings() {
        return new AbstractResponseOperationType.Warnings();
    }

    /**
     * Create an instance of {@link AbstractResponseOperationType.ErrorsMessage }
     * 
     */
    public AbstractResponseOperationType.ErrorsMessage createAbstractResponseOperationTypeErrorsMessage() {
        return new AbstractResponseOperationType.ErrorsMessage();
    }

    /**
     * Create an instance of {@link MessageType }
     * 
     */
    public MessageType createMessageType() {
        return new MessageType();
    }

    /**
     * Create an instance of {@link AbstractInputOperationType }
     * 
     */
    public AbstractInputOperationType createAbstractInputOperationType() {
        return new AbstractInputOperationType();
    }

    /**
     * Create an instance of {@link AbstractResponseOperationType }
     * 
     */
    public AbstractResponseOperationType createAbstractResponseOperationType() {
        return new AbstractResponseOperationType();
    }

}