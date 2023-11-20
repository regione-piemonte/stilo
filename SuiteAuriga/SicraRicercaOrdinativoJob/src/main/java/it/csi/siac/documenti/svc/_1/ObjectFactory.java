/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */
package it.csi.siac.documenti.svc._1;

import javax.xml.bind.annotation.XmlRegistry;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the it.csi.siac.documenti.svc._1 package. 
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
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: it.csi.siac.documenti.svc._1
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link ElaboraAttiAmministrativi }
     * 
     */
    public ElaboraAttiAmministrativi createElaboraAttiAmministrativi() {
        return new ElaboraAttiAmministrativi();
    }

    /**
     * Create an instance of {@link ElaboraAttiAmministrativiAsyncResponse }
     * 
     */
    public ElaboraAttiAmministrativiAsyncResponse createElaboraAttiAmministrativiAsyncResponse() {
        return new ElaboraAttiAmministrativiAsyncResponse();
    }

    /**
     * Create an instance of {@link ElaboraAttiAmministrativiResponse }
     * 
     */
    public ElaboraAttiAmministrativiResponse createElaboraAttiAmministrativiResponse() {
        return new ElaboraAttiAmministrativiResponse();
    }

    /**
     * Create an instance of {@link ElaboraDocumentoAsyncResponse }
     * 
     */
    public ElaboraDocumentoAsyncResponse createElaboraDocumentoAsyncResponse() {
        return new ElaboraDocumentoAsyncResponse();
    }

    /**
     * Create an instance of {@link ElaboraDocumentoResponse }
     * 
     */
    public ElaboraDocumentoResponse createElaboraDocumentoResponse() {
        return new ElaboraDocumentoResponse();
    }

}
