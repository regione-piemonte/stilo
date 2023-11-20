/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */
// Questo file è stato generato dall'architettura JavaTM per XML Binding (JAXB) Reference Implementation, v2.2.8-b130911.1802 
// Vedere <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Qualsiasi modifica a questo file andrà persa durante la ricompilazione dello schema di origine. 
// Generato il: 2018.06.06 alle 03:56:55 PM CEST 
//


package it.eng.auriga.repository2.jaxws.jaxbBean.operazioniaurigalottidoc;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the it.eng.auriga.repository2.jaxws.jaxbBean.operazioniaurigalottidoc package. 
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

    private final static QName _FaultActionsOnDocList_QNAME = new QName("http://operazioniaurigalottidoc.webservices.repository2.auriga.eng.it", "FaultActionsOnDocList");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: it.eng.auriga.repository2.jaxws.jaxbBean.operazioniaurigalottidoc
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link RequestActionsOnDocList }
     * 
     */
    public RequestActionsOnDocList createRequestActionsOnDocList() {
        return new RequestActionsOnDocList();
    }

    /**
     * Create an instance of {@link RequestActionsOnDocList.Operations }
     * 
     */
    public RequestActionsOnDocList.Operations createRequestActionsOnDocListOperations() {
        return new RequestActionsOnDocList.Operations();
    }

    /**
     * Create an instance of {@link RequestActionsOnDocList.Operations.Operation }
     * 
     */
    public RequestActionsOnDocList.Operations.Operation createRequestActionsOnDocListOperationsOperation() {
        return new RequestActionsOnDocList.Operations.Operation();
    }

    /**
     * Create an instance of {@link RequestActionsOnDocList.Operations.Operation.SignOpOption }
     * 
     */
    public RequestActionsOnDocList.Operations.Operation.SignOpOption createRequestActionsOnDocListOperationsOperationSignOpOption() {
        return new RequestActionsOnDocList.Operations.Operation.SignOpOption();
    }

    /**
     * Create an instance of {@link FaultActionsOnDocList }
     * 
     */
    public FaultActionsOnDocList createFaultActionsOnDocList() {
        return new FaultActionsOnDocList();
    }

    /**
     * Create an instance of {@link ResponseActionsOnDocList }
     * 
     */
    public ResponseActionsOnDocList createResponseActionsOnDocList() {
        return new ResponseActionsOnDocList();
    }

    /**
     * Create an instance of {@link RequestActionsOnDocList.Operations.Operation.SendEmail }
     * 
     */
    public RequestActionsOnDocList.Operations.Operation.SendEmail createRequestActionsOnDocListOperationsOperationSendEmail() {
        return new RequestActionsOnDocList.Operations.Operation.SendEmail();
    }

    /**
     * Create an instance of {@link RequestActionsOnDocList.Operations.Operation.SignOpOption.Signer }
     * 
     */
    public RequestActionsOnDocList.Operations.Operation.SignOpOption.Signer createRequestActionsOnDocListOperationsOperationSignOpOptionSigner() {
        return new RequestActionsOnDocList.Operations.Operation.SignOpOption.Signer();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link FaultActionsOnDocList }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://operazioniaurigalottidoc.webservices.repository2.auriga.eng.it", name = "FaultActionsOnDocList")
    public JAXBElement<FaultActionsOnDocList> createFaultActionsOnDocList(FaultActionsOnDocList value) {
        return new JAXBElement<FaultActionsOnDocList>(_FaultActionsOnDocList_QNAME, FaultActionsOnDocList.class, null, value);
    }

}
