/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */
package it.csi.siac.stilo.svc._1;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;
import it.csi.siac.documenti.svc._1.ElaboraAttiAmministrativi;
import it.csi.siac.documenti.svc._1.ElaboraAttiAmministrativiAsyncResponse;
import it.csi.siac.documenti.svc._1.ElaboraAttiAmministrativiResponse;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the it.csi.siac.stilo.svc._1 package. 
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

    private final static QName _ElaboraAttiAmministrativiAsync_QNAME = new QName("http://siac.csi.it/stilo/svc/1.0", "elaboraAttiAmministrativiAsync");
    private final static QName _ElaboraAttiAmministrativiAsyncResponse_QNAME = new QName("http://siac.csi.it/stilo/svc/1.0", "elaboraAttiAmministrativiAsyncResponse");
    private final static QName _ElaboraAttiAmministrativi_QNAME = new QName("http://siac.csi.it/stilo/svc/1.0", "elaboraAttiAmministrativi");
    private final static QName _ElaboraAttiAmministrativiResponse_QNAME = new QName("http://siac.csi.it/stilo/svc/1.0", "elaboraAttiAmministrativiResponse");
    private final static QName _RicercaMovimentoGestione_QNAME = new QName("http://siac.csi.it/stilo/svc/1.0", "ricercaMovimentoGestione");
    private final static QName _RicercaMovimentoGestioneResponse_QNAME = new QName("http://siac.csi.it/stilo/svc/1.0", "ricercaMovimentoGestioneResponse");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: it.csi.siac.stilo.svc._1
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link RicercaMovimentoGestioneStilo }
     * 
     */
    public RicercaMovimentoGestioneStilo createRicercaMovimentoGestioneStilo() {
        return new RicercaMovimentoGestioneStilo();
    }

    /**
     * Create an instance of {@link RicercaMovimentoGestioneStiloResponse }
     * 
     */
    public RicercaMovimentoGestioneStiloResponse createRicercaMovimentoGestioneStiloResponse() {
        return new RicercaMovimentoGestioneStiloResponse();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ElaboraAttiAmministrativi }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://siac.csi.it/stilo/svc/1.0", name = "elaboraAttiAmministrativiAsync")
    public JAXBElement<ElaboraAttiAmministrativi> createElaboraAttiAmministrativiAsync(ElaboraAttiAmministrativi value) {
        return new JAXBElement<ElaboraAttiAmministrativi>(_ElaboraAttiAmministrativiAsync_QNAME, ElaboraAttiAmministrativi.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ElaboraAttiAmministrativiAsyncResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://siac.csi.it/stilo/svc/1.0", name = "elaboraAttiAmministrativiAsyncResponse")
    public JAXBElement<ElaboraAttiAmministrativiAsyncResponse> createElaboraAttiAmministrativiAsyncResponse(ElaboraAttiAmministrativiAsyncResponse value) {
        return new JAXBElement<ElaboraAttiAmministrativiAsyncResponse>(_ElaboraAttiAmministrativiAsyncResponse_QNAME, ElaboraAttiAmministrativiAsyncResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ElaboraAttiAmministrativi }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://siac.csi.it/stilo/svc/1.0", name = "elaboraAttiAmministrativi")
    public JAXBElement<ElaboraAttiAmministrativi> createElaboraAttiAmministrativi(ElaboraAttiAmministrativi value) {
        return new JAXBElement<ElaboraAttiAmministrativi>(_ElaboraAttiAmministrativi_QNAME, ElaboraAttiAmministrativi.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ElaboraAttiAmministrativiResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://siac.csi.it/stilo/svc/1.0", name = "elaboraAttiAmministrativiResponse")
    public JAXBElement<ElaboraAttiAmministrativiResponse> createElaboraAttiAmministrativiResponse(ElaboraAttiAmministrativiResponse value) {
        return new JAXBElement<ElaboraAttiAmministrativiResponse>(_ElaboraAttiAmministrativiResponse_QNAME, ElaboraAttiAmministrativiResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link RicercaMovimentoGestioneStilo }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://siac.csi.it/stilo/svc/1.0", name = "ricercaMovimentoGestione")
    public JAXBElement<RicercaMovimentoGestioneStilo> createRicercaMovimentoGestione(RicercaMovimentoGestioneStilo value) {
        return new JAXBElement<RicercaMovimentoGestioneStilo>(_RicercaMovimentoGestione_QNAME, RicercaMovimentoGestioneStilo.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link RicercaMovimentoGestioneStiloResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://siac.csi.it/stilo/svc/1.0", name = "ricercaMovimentoGestioneResponse")
    public JAXBElement<RicercaMovimentoGestioneStiloResponse> createRicercaMovimentoGestioneResponse(RicercaMovimentoGestioneStiloResponse value) {
        return new JAXBElement<RicercaMovimentoGestioneStiloResponse>(_RicercaMovimentoGestioneResponse_QNAME, RicercaMovimentoGestioneStiloResponse.class, null, value);
    }

}
