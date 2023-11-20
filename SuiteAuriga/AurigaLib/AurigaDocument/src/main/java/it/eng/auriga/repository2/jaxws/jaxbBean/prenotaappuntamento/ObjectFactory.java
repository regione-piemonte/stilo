/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */
// Questo file è stato generato dall'architettura JavaTM per XML Binding (JAXB) Reference Implementation, v2.2.8-b130911.1802 
// Vedere <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Qualsiasi modifica a questo file andrà persa durante la ricompilazione dello schema di origine. 
// Generato il: 2018.09.19 alle 10:17:30 AM CEST 
//


package it.eng.auriga.repository2.jaxws.jaxbBean.prenotaappuntamento;

import javax.xml.bind.annotation.XmlRegistry;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the it.eng.auriga.repository2.jaxws.jaxbBean.prenotaappuntamento package. 
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
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: it.eng.auriga.repository2.jaxws.jaxbBean.prenotaappuntamento
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link ResponseAggiornaRichiesta }
     * 
     */
    public ResponseAggiornaRichiesta createResponseAggiornaRichiesta() {
        return new ResponseAggiornaRichiesta();
    }

    /**
     * Create an instance of {@link ResponseVerificaRichiesta }
     * 
     */
    public ResponseVerificaRichiesta createResponseVerificaRichiesta() {
        return new ResponseVerificaRichiesta();
    }

    /**
     * Create an instance of {@link RequestAggiornaRichiesta }
     * 
     */
    public RequestAggiornaRichiesta createRequestAggiornaRichiesta() {
        return new RequestAggiornaRichiesta();
    }

    /**
     * Create an instance of {@link RequestAggiornaRichiesta.Appuntamento }
     * 
     */
    public RequestAggiornaRichiesta.Appuntamento createRequestAggiornaRichiestaAppuntamento() {
        return new RequestAggiornaRichiesta.Appuntamento();
    }

    /**
     * Create an instance of {@link ResponseVerificaRichiesta.DatiRichiesta }
     * 
     */
    public ResponseVerificaRichiesta.DatiRichiesta createResponseVerificaRichiestaDatiRichiesta() {
        return new ResponseVerificaRichiesta.DatiRichiesta();
    }

    /**
     * Create an instance of {@link ResponseAggiornaRichiesta.Errore }
     * 
     */
    public ResponseAggiornaRichiesta.Errore createResponseAggiornaRichiestaErrore() {
        return new ResponseAggiornaRichiesta.Errore();
    }

    /**
     * Create an instance of {@link ResponseVerificaRichiesta.Errore }
     * 
     */
    public ResponseVerificaRichiesta.Errore createResponseVerificaRichiestaErrore() {
        return new ResponseVerificaRichiesta.Errore();
    }

    /**
     * Create an instance of {@link RequestVerificaRichiesta }
     * 
     */
    public RequestVerificaRichiesta createRequestVerificaRichiesta() {
        return new RequestVerificaRichiesta();
    }

    /**
     * Create an instance of {@link RequestAggiornaRichiesta.Appuntamento.Richiedente }
     * 
     */
    public RequestAggiornaRichiesta.Appuntamento.Richiedente createRequestAggiornaRichiestaAppuntamentoRichiedente() {
        return new RequestAggiornaRichiesta.Appuntamento.Richiedente();
    }

    /**
     * Create an instance of {@link RequestAggiornaRichiesta.Appuntamento.Delegante }
     * 
     */
    public RequestAggiornaRichiesta.Appuntamento.Delegante createRequestAggiornaRichiestaAppuntamentoDelegante() {
        return new RequestAggiornaRichiesta.Appuntamento.Delegante();
    }

    /**
     * Create an instance of {@link RequestAggiornaRichiesta.Appuntamento.NominativoPresenza }
     * 
     */
    public RequestAggiornaRichiesta.Appuntamento.NominativoPresenza createRequestAggiornaRichiestaAppuntamentoNominativoPresenza() {
        return new RequestAggiornaRichiesta.Appuntamento.NominativoPresenza();
    }

    /**
     * Create an instance of {@link ResponseVerificaRichiesta.DatiRichiesta.AttiRichiesti }
     * 
     */
    public ResponseVerificaRichiesta.DatiRichiesta.AttiRichiesti createResponseVerificaRichiestaDatiRichiestaAttiRichiesti() {
        return new ResponseVerificaRichiesta.DatiRichiesta.AttiRichiesti();
    }

    /**
     * Create an instance of {@link ResponseVerificaRichiesta.DatiRichiesta.SediAppuntamento }
     * 
     */
    public ResponseVerificaRichiesta.DatiRichiesta.SediAppuntamento createResponseVerificaRichiestaDatiRichiestaSediAppuntamento() {
        return new ResponseVerificaRichiesta.DatiRichiesta.SediAppuntamento();
    }

}
