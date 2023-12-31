/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */
package it.csi.siac.ricerche.data._1;

import javax.xml.bind.annotation.XmlRegistry;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the it.csi.siac.ricerche.data._1 package. 
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
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: it.csi.siac.ricerche.data._1
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link DocumentoSpesa }
     * 
     */
    public DocumentoSpesa createDocumentoSpesa() {
        return new DocumentoSpesa();
    }

    /**
     * Create an instance of {@link Documento }
     * 
     */
    public Documento createDocumento() {
        return new Documento();
    }

    /**
     * Create an instance of {@link Ordine }
     * 
     */
    public Ordine createOrdine() {
        return new Ordine();
    }

    /**
     * Create an instance of {@link ProvvisorioDiCassa }
     * 
     */
    public ProvvisorioDiCassa createProvvisorioDiCassa() {
        return new ProvvisorioDiCassa();
    }

    /**
     * Create an instance of {@link DocumentoEntrata }
     * 
     */
    public DocumentoEntrata createDocumentoEntrata() {
        return new DocumentoEntrata();
    }

}
