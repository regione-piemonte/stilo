/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */
package it.csi.siac.ricerche.svc._1;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;
import it.csi.siac.documenti.svc._1.RicercaDocumentoEntrataResponse;
import it.csi.siac.documenti.svc._1.RicercaDocumentoSpesaResponse;
import it.csi.siac.documenti.svc._1.RicercaProvvisoriDiCassaResponse;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the it.csi.siac.ricerche.svc._1 package. 
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

    private final static QName _RicercaOrdinativoIncasso_QNAME = new QName("http://siac.csi.it/ricerche/svc/1.0", "ricercaOrdinativoIncasso");
    private final static QName _RicercaOrdinativoIncassoResponse_QNAME = new QName("http://siac.csi.it/ricerche/svc/1.0", "ricercaOrdinativoIncassoResponse");
    private final static QName _RicercaCapitoloEntrataGestione_QNAME = new QName("http://siac.csi.it/ricerche/svc/1.0", "ricercaCapitoloEntrataGestione");
    private final static QName _RicercaCapitoloEntrataGestioneResponse_QNAME = new QName("http://siac.csi.it/ricerche/svc/1.0", "ricercaCapitoloEntrataGestioneResponse");
    private final static QName _RicercaSinteticaSoggetti_QNAME = new QName("http://siac.csi.it/ricerche/svc/1.0", "ricercaSinteticaSoggetti");
    private final static QName _RicercaSinteticaSoggettiResponse_QNAME = new QName("http://siac.csi.it/ricerche/svc/1.0", "ricercaSinteticaSoggettiResponse");
    private final static QName _RicercaDocumentoSpesa_QNAME = new QName("http://siac.csi.it/ricerche/svc/1.0", "ricercaDocumentoSpesa");
    private final static QName _RicercaDocumentoSpesaResponse_QNAME = new QName("http://siac.csi.it/ricerche/svc/1.0", "ricercaDocumentoSpesaResponse");
    private final static QName _RicercaEstesaOrdinativiSpesa_QNAME = new QName("http://siac.csi.it/ricerche/svc/1.0", "ricercaEstesaOrdinativiSpesa");
    private final static QName _RicercaEstesaOrdinativiSpesaResponse_QNAME = new QName("http://siac.csi.it/ricerche/svc/1.0", "ricercaEstesaOrdinativiSpesaResponse");
    private final static QName _RicercaImpegno_QNAME = new QName("http://siac.csi.it/ricerche/svc/1.0", "ricercaImpegno");
    private final static QName _RicercaImpegnoResponse_QNAME = new QName("http://siac.csi.it/ricerche/svc/1.0", "ricercaImpegnoResponse");
    private final static QName _RicercaOrdinativoSpesa_QNAME = new QName("http://siac.csi.it/ricerche/svc/1.0", "ricercaOrdinativoSpesa");
    private final static QName _RicercaOrdinativoSpesaResponse_QNAME = new QName("http://siac.csi.it/ricerche/svc/1.0", "ricercaOrdinativoSpesaResponse");
    private final static QName _RicercaDettaglioSoggetto_QNAME = new QName("http://siac.csi.it/ricerche/svc/1.0", "ricercaDettaglioSoggetto");
    private final static QName _RicercaDettaglioSoggettoResponse_QNAME = new QName("http://siac.csi.it/ricerche/svc/1.0", "ricercaDettaglioSoggettoResponse");
    private final static QName _RicercaProvvisoriDiCassa_QNAME = new QName("http://siac.csi.it/ricerche/svc/1.0", "ricercaProvvisoriDiCassa");
    private final static QName _RicercaProvvisoriDiCassaResponse_QNAME = new QName("http://siac.csi.it/ricerche/svc/1.0", "ricercaProvvisoriDiCassaResponse");
    private final static QName _RicercaCapitoloUscitaGestione_QNAME = new QName("http://siac.csi.it/ricerche/svc/1.0", "ricercaCapitoloUscitaGestione");
    private final static QName _RicercaCapitoloUscitaGestioneResponse_QNAME = new QName("http://siac.csi.it/ricerche/svc/1.0", "ricercaCapitoloUscitaGestioneResponse");
    private final static QName _RicercaAccertamento_QNAME = new QName("http://siac.csi.it/ricerche/svc/1.0", "ricercaAccertamento");
    private final static QName _RicercaAccertamentoResponse_QNAME = new QName("http://siac.csi.it/ricerche/svc/1.0", "ricercaAccertamentoResponse");
    private final static QName _RicercaLiquidazione_QNAME = new QName("http://siac.csi.it/ricerche/svc/1.0", "ricercaLiquidazione");
    private final static QName _RicercaLiquidazioneResponse_QNAME = new QName("http://siac.csi.it/ricerche/svc/1.0", "ricercaLiquidazioneResponse");
    private final static QName _RicercaEstesaLiquidazioni_QNAME = new QName("http://siac.csi.it/ricerche/svc/1.0", "ricercaEstesaLiquidazioni");
    private final static QName _RicercaEstesaLiquidazioniResponse_QNAME = new QName("http://siac.csi.it/ricerche/svc/1.0", "ricercaEstesaLiquidazioniResponse");
    private final static QName _RicercaDocumentoEntrata_QNAME = new QName("http://siac.csi.it/ricerche/svc/1.0", "ricercaDocumentoEntrata");
    private final static QName _RicercaDocumentoEntrataResponse_QNAME = new QName("http://siac.csi.it/ricerche/svc/1.0", "ricercaDocumentoEntrataResponse");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: it.csi.siac.ricerche.svc._1
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link RicercaOrdinativoIncasso }
     * 
     */
    public RicercaOrdinativoIncasso createRicercaOrdinativoIncasso() {
        return new RicercaOrdinativoIncasso();
    }

    /**
     * Create an instance of {@link RicercaOrdinativoIncassoResponse }
     * 
     */
    public RicercaOrdinativoIncassoResponse createRicercaOrdinativoIncassoResponse() {
        return new RicercaOrdinativoIncassoResponse();
    }

    /**
     * Create an instance of {@link RicercaCapitoloEntrataGestione }
     * 
     */
    public RicercaCapitoloEntrataGestione createRicercaCapitoloEntrataGestione() {
        return new RicercaCapitoloEntrataGestione();
    }

    /**
     * Create an instance of {@link RicercaCapitoloEntrataGestioneResponse }
     * 
     */
    public RicercaCapitoloEntrataGestioneResponse createRicercaCapitoloEntrataGestioneResponse() {
        return new RicercaCapitoloEntrataGestioneResponse();
    }

    /**
     * Create an instance of {@link RicercaSinteticaSoggetti }
     * 
     */
    public RicercaSinteticaSoggetti createRicercaSinteticaSoggetti() {
        return new RicercaSinteticaSoggetti();
    }

    /**
     * Create an instance of {@link RicercaSinteticaSoggettiResponse }
     * 
     */
    public RicercaSinteticaSoggettiResponse createRicercaSinteticaSoggettiResponse() {
        return new RicercaSinteticaSoggettiResponse();
    }

    /**
     * Create an instance of {@link RicercaDocumentoSpesa }
     * 
     */
    public RicercaDocumentoSpesa createRicercaDocumentoSpesa() {
        return new RicercaDocumentoSpesa();
    }

    /**
     * Create an instance of {@link RicercaEstesaOrdinativiSpesa }
     * 
     */
    public RicercaEstesaOrdinativiSpesa createRicercaEstesaOrdinativiSpesa() {
        return new RicercaEstesaOrdinativiSpesa();
    }

    /**
     * Create an instance of {@link RicercaEstesaOrdinativiSpesaResponse }
     * 
     */
    public RicercaEstesaOrdinativiSpesaResponse createRicercaEstesaOrdinativiSpesaResponse() {
        return new RicercaEstesaOrdinativiSpesaResponse();
    }

    /**
     * Create an instance of {@link RicercaImpegno }
     * 
     */
    public RicercaImpegno createRicercaImpegno() {
        return new RicercaImpegno();
    }

    /**
     * Create an instance of {@link RicercaImpegnoResponse }
     * 
     */
    public RicercaImpegnoResponse createRicercaImpegnoResponse() {
        return new RicercaImpegnoResponse();
    }

    /**
     * Create an instance of {@link RicercaOrdinativoSpesa }
     * 
     */
    public RicercaOrdinativoSpesa createRicercaOrdinativoSpesa() {
        return new RicercaOrdinativoSpesa();
    }

    /**
     * Create an instance of {@link RicercaOrdinativoSpesaResponse }
     * 
     */
    public RicercaOrdinativoSpesaResponse createRicercaOrdinativoSpesaResponse() {
        return new RicercaOrdinativoSpesaResponse();
    }

    /**
     * Create an instance of {@link RicercaDettaglioSoggetti }
     * 
     */
    public RicercaDettaglioSoggetti createRicercaDettaglioSoggetti() {
        return new RicercaDettaglioSoggetti();
    }

    /**
     * Create an instance of {@link RicercaDettaglioSoggettiResponse }
     * 
     */
    public RicercaDettaglioSoggettiResponse createRicercaDettaglioSoggettiResponse() {
        return new RicercaDettaglioSoggettiResponse();
    }

    /**
     * Create an instance of {@link RicercaProvvisoriDiCassa }
     * 
     */
    public RicercaProvvisoriDiCassa createRicercaProvvisoriDiCassa() {
        return new RicercaProvvisoriDiCassa();
    }

    /**
     * Create an instance of {@link RicercaCapitoloUscitaGestione }
     * 
     */
    public RicercaCapitoloUscitaGestione createRicercaCapitoloUscitaGestione() {
        return new RicercaCapitoloUscitaGestione();
    }

    /**
     * Create an instance of {@link RicercaCapitoloUscitaGestioneResponse }
     * 
     */
    public RicercaCapitoloUscitaGestioneResponse createRicercaCapitoloUscitaGestioneResponse() {
        return new RicercaCapitoloUscitaGestioneResponse();
    }

    /**
     * Create an instance of {@link RicercaAccertamento }
     * 
     */
    public RicercaAccertamento createRicercaAccertamento() {
        return new RicercaAccertamento();
    }

    /**
     * Create an instance of {@link RicercaAccertamentoResponse }
     * 
     */
    public RicercaAccertamentoResponse createRicercaAccertamentoResponse() {
        return new RicercaAccertamentoResponse();
    }

    /**
     * Create an instance of {@link RicercaLiquidazione }
     * 
     */
    public RicercaLiquidazione createRicercaLiquidazione() {
        return new RicercaLiquidazione();
    }

    /**
     * Create an instance of {@link RicercaLiquidazioneResponse }
     * 
     */
    public RicercaLiquidazioneResponse createRicercaLiquidazioneResponse() {
        return new RicercaLiquidazioneResponse();
    }

    /**
     * Create an instance of {@link RicercaEstesaLiquidazioni }
     * 
     */
    public RicercaEstesaLiquidazioni createRicercaEstesaLiquidazioni() {
        return new RicercaEstesaLiquidazioni();
    }

    /**
     * Create an instance of {@link RicercaEstesaLiquidazioniResponse }
     * 
     */
    public RicercaEstesaLiquidazioniResponse createRicercaEstesaLiquidazioniResponse() {
        return new RicercaEstesaLiquidazioniResponse();
    }

    /**
     * Create an instance of {@link RicercaDocumentoEntrata }
     * 
     */
    public RicercaDocumentoEntrata createRicercaDocumentoEntrata() {
        return new RicercaDocumentoEntrata();
    }

    /**
     * Create an instance of {@link RicercaCapitolo }
     * 
     */
    public RicercaCapitolo createRicercaCapitolo() {
        return new RicercaCapitolo();
    }

    /**
     * Create an instance of {@link RicercaMovimentoGestione }
     * 
     */
    public RicercaMovimentoGestione createRicercaMovimentoGestione() {
        return new RicercaMovimentoGestione();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link RicercaOrdinativoIncasso }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://siac.csi.it/ricerche/svc/1.0", name = "ricercaOrdinativoIncasso")
    public JAXBElement<RicercaOrdinativoIncasso> createRicercaOrdinativoIncasso(RicercaOrdinativoIncasso value) {
        return new JAXBElement<RicercaOrdinativoIncasso>(_RicercaOrdinativoIncasso_QNAME, RicercaOrdinativoIncasso.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link RicercaOrdinativoIncassoResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://siac.csi.it/ricerche/svc/1.0", name = "ricercaOrdinativoIncassoResponse")
    public JAXBElement<RicercaOrdinativoIncassoResponse> createRicercaOrdinativoIncassoResponse(RicercaOrdinativoIncassoResponse value) {
        return new JAXBElement<RicercaOrdinativoIncassoResponse>(_RicercaOrdinativoIncassoResponse_QNAME, RicercaOrdinativoIncassoResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link RicercaCapitoloEntrataGestione }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://siac.csi.it/ricerche/svc/1.0", name = "ricercaCapitoloEntrataGestione")
    public JAXBElement<RicercaCapitoloEntrataGestione> createRicercaCapitoloEntrataGestione(RicercaCapitoloEntrataGestione value) {
        return new JAXBElement<RicercaCapitoloEntrataGestione>(_RicercaCapitoloEntrataGestione_QNAME, RicercaCapitoloEntrataGestione.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link RicercaCapitoloEntrataGestioneResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://siac.csi.it/ricerche/svc/1.0", name = "ricercaCapitoloEntrataGestioneResponse")
    public JAXBElement<RicercaCapitoloEntrataGestioneResponse> createRicercaCapitoloEntrataGestioneResponse(RicercaCapitoloEntrataGestioneResponse value) {
        return new JAXBElement<RicercaCapitoloEntrataGestioneResponse>(_RicercaCapitoloEntrataGestioneResponse_QNAME, RicercaCapitoloEntrataGestioneResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link RicercaSinteticaSoggetti }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://siac.csi.it/ricerche/svc/1.0", name = "ricercaSinteticaSoggetti")
    public JAXBElement<RicercaSinteticaSoggetti> createRicercaSinteticaSoggetti(RicercaSinteticaSoggetti value) {
        return new JAXBElement<RicercaSinteticaSoggetti>(_RicercaSinteticaSoggetti_QNAME, RicercaSinteticaSoggetti.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link RicercaSinteticaSoggettiResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://siac.csi.it/ricerche/svc/1.0", name = "ricercaSinteticaSoggettiResponse")
    public JAXBElement<RicercaSinteticaSoggettiResponse> createRicercaSinteticaSoggettiResponse(RicercaSinteticaSoggettiResponse value) {
        return new JAXBElement<RicercaSinteticaSoggettiResponse>(_RicercaSinteticaSoggettiResponse_QNAME, RicercaSinteticaSoggettiResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link RicercaDocumentoSpesa }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://siac.csi.it/ricerche/svc/1.0", name = "ricercaDocumentoSpesa")
    public JAXBElement<RicercaDocumentoSpesa> createRicercaDocumentoSpesa(RicercaDocumentoSpesa value) {
        return new JAXBElement<RicercaDocumentoSpesa>(_RicercaDocumentoSpesa_QNAME, RicercaDocumentoSpesa.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link RicercaDocumentoSpesaResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://siac.csi.it/ricerche/svc/1.0", name = "ricercaDocumentoSpesaResponse")
    public JAXBElement<RicercaDocumentoSpesaResponse> createRicercaDocumentoSpesaResponse(RicercaDocumentoSpesaResponse value) {
        return new JAXBElement<RicercaDocumentoSpesaResponse>(_RicercaDocumentoSpesaResponse_QNAME, RicercaDocumentoSpesaResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link RicercaEstesaOrdinativiSpesa }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://siac.csi.it/ricerche/svc/1.0", name = "ricercaEstesaOrdinativiSpesa")
    public JAXBElement<RicercaEstesaOrdinativiSpesa> createRicercaEstesaOrdinativiSpesa(RicercaEstesaOrdinativiSpesa value) {
        return new JAXBElement<RicercaEstesaOrdinativiSpesa>(_RicercaEstesaOrdinativiSpesa_QNAME, RicercaEstesaOrdinativiSpesa.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link RicercaEstesaOrdinativiSpesaResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://siac.csi.it/ricerche/svc/1.0", name = "ricercaEstesaOrdinativiSpesaResponse")
    public JAXBElement<RicercaEstesaOrdinativiSpesaResponse> createRicercaEstesaOrdinativiSpesaResponse(RicercaEstesaOrdinativiSpesaResponse value) {
        return new JAXBElement<RicercaEstesaOrdinativiSpesaResponse>(_RicercaEstesaOrdinativiSpesaResponse_QNAME, RicercaEstesaOrdinativiSpesaResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link RicercaImpegno }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://siac.csi.it/ricerche/svc/1.0", name = "ricercaImpegno")
    public JAXBElement<RicercaImpegno> createRicercaImpegno(RicercaImpegno value) {
        return new JAXBElement<RicercaImpegno>(_RicercaImpegno_QNAME, RicercaImpegno.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link RicercaImpegnoResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://siac.csi.it/ricerche/svc/1.0", name = "ricercaImpegnoResponse")
    public JAXBElement<RicercaImpegnoResponse> createRicercaImpegnoResponse(RicercaImpegnoResponse value) {
        return new JAXBElement<RicercaImpegnoResponse>(_RicercaImpegnoResponse_QNAME, RicercaImpegnoResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link RicercaOrdinativoSpesa }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://siac.csi.it/ricerche/svc/1.0", name = "ricercaOrdinativoSpesa")
    public JAXBElement<RicercaOrdinativoSpesa> createRicercaOrdinativoSpesa(RicercaOrdinativoSpesa value) {
        return new JAXBElement<RicercaOrdinativoSpesa>(_RicercaOrdinativoSpesa_QNAME, RicercaOrdinativoSpesa.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link RicercaOrdinativoSpesaResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://siac.csi.it/ricerche/svc/1.0", name = "ricercaOrdinativoSpesaResponse")
    public JAXBElement<RicercaOrdinativoSpesaResponse> createRicercaOrdinativoSpesaResponse(RicercaOrdinativoSpesaResponse value) {
        return new JAXBElement<RicercaOrdinativoSpesaResponse>(_RicercaOrdinativoSpesaResponse_QNAME, RicercaOrdinativoSpesaResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link RicercaDettaglioSoggetti }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://siac.csi.it/ricerche/svc/1.0", name = "ricercaDettaglioSoggetto")
    public JAXBElement<RicercaDettaglioSoggetti> createRicercaDettaglioSoggetto(RicercaDettaglioSoggetti value) {
        return new JAXBElement<RicercaDettaglioSoggetti>(_RicercaDettaglioSoggetto_QNAME, RicercaDettaglioSoggetti.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link RicercaDettaglioSoggettiResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://siac.csi.it/ricerche/svc/1.0", name = "ricercaDettaglioSoggettoResponse")
    public JAXBElement<RicercaDettaglioSoggettiResponse> createRicercaDettaglioSoggettoResponse(RicercaDettaglioSoggettiResponse value) {
        return new JAXBElement<RicercaDettaglioSoggettiResponse>(_RicercaDettaglioSoggettoResponse_QNAME, RicercaDettaglioSoggettiResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link RicercaProvvisoriDiCassa }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://siac.csi.it/ricerche/svc/1.0", name = "ricercaProvvisoriDiCassa")
    public JAXBElement<RicercaProvvisoriDiCassa> createRicercaProvvisoriDiCassa(RicercaProvvisoriDiCassa value) {
        return new JAXBElement<RicercaProvvisoriDiCassa>(_RicercaProvvisoriDiCassa_QNAME, RicercaProvvisoriDiCassa.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link RicercaProvvisoriDiCassaResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://siac.csi.it/ricerche/svc/1.0", name = "ricercaProvvisoriDiCassaResponse")
    public JAXBElement<RicercaProvvisoriDiCassaResponse> createRicercaProvvisoriDiCassaResponse(RicercaProvvisoriDiCassaResponse value) {
        return new JAXBElement<RicercaProvvisoriDiCassaResponse>(_RicercaProvvisoriDiCassaResponse_QNAME, RicercaProvvisoriDiCassaResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link RicercaCapitoloUscitaGestione }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://siac.csi.it/ricerche/svc/1.0", name = "ricercaCapitoloUscitaGestione")
    public JAXBElement<RicercaCapitoloUscitaGestione> createRicercaCapitoloUscitaGestione(RicercaCapitoloUscitaGestione value) {
        return new JAXBElement<RicercaCapitoloUscitaGestione>(_RicercaCapitoloUscitaGestione_QNAME, RicercaCapitoloUscitaGestione.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link RicercaCapitoloUscitaGestioneResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://siac.csi.it/ricerche/svc/1.0", name = "ricercaCapitoloUscitaGestioneResponse")
    public JAXBElement<RicercaCapitoloUscitaGestioneResponse> createRicercaCapitoloUscitaGestioneResponse(RicercaCapitoloUscitaGestioneResponse value) {
        return new JAXBElement<RicercaCapitoloUscitaGestioneResponse>(_RicercaCapitoloUscitaGestioneResponse_QNAME, RicercaCapitoloUscitaGestioneResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link RicercaAccertamento }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://siac.csi.it/ricerche/svc/1.0", name = "ricercaAccertamento")
    public JAXBElement<RicercaAccertamento> createRicercaAccertamento(RicercaAccertamento value) {
        return new JAXBElement<RicercaAccertamento>(_RicercaAccertamento_QNAME, RicercaAccertamento.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link RicercaAccertamentoResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://siac.csi.it/ricerche/svc/1.0", name = "ricercaAccertamentoResponse")
    public JAXBElement<RicercaAccertamentoResponse> createRicercaAccertamentoResponse(RicercaAccertamentoResponse value) {
        return new JAXBElement<RicercaAccertamentoResponse>(_RicercaAccertamentoResponse_QNAME, RicercaAccertamentoResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link RicercaLiquidazione }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://siac.csi.it/ricerche/svc/1.0", name = "ricercaLiquidazione")
    public JAXBElement<RicercaLiquidazione> createRicercaLiquidazione(RicercaLiquidazione value) {
        return new JAXBElement<RicercaLiquidazione>(_RicercaLiquidazione_QNAME, RicercaLiquidazione.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link RicercaLiquidazioneResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://siac.csi.it/ricerche/svc/1.0", name = "ricercaLiquidazioneResponse")
    public JAXBElement<RicercaLiquidazioneResponse> createRicercaLiquidazioneResponse(RicercaLiquidazioneResponse value) {
        return new JAXBElement<RicercaLiquidazioneResponse>(_RicercaLiquidazioneResponse_QNAME, RicercaLiquidazioneResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link RicercaEstesaLiquidazioni }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://siac.csi.it/ricerche/svc/1.0", name = "ricercaEstesaLiquidazioni")
    public JAXBElement<RicercaEstesaLiquidazioni> createRicercaEstesaLiquidazioni(RicercaEstesaLiquidazioni value) {
        return new JAXBElement<RicercaEstesaLiquidazioni>(_RicercaEstesaLiquidazioni_QNAME, RicercaEstesaLiquidazioni.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link RicercaEstesaLiquidazioniResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://siac.csi.it/ricerche/svc/1.0", name = "ricercaEstesaLiquidazioniResponse")
    public JAXBElement<RicercaEstesaLiquidazioniResponse> createRicercaEstesaLiquidazioniResponse(RicercaEstesaLiquidazioniResponse value) {
        return new JAXBElement<RicercaEstesaLiquidazioniResponse>(_RicercaEstesaLiquidazioniResponse_QNAME, RicercaEstesaLiquidazioniResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link RicercaDocumentoEntrata }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://siac.csi.it/ricerche/svc/1.0", name = "ricercaDocumentoEntrata")
    public JAXBElement<RicercaDocumentoEntrata> createRicercaDocumentoEntrata(RicercaDocumentoEntrata value) {
        return new JAXBElement<RicercaDocumentoEntrata>(_RicercaDocumentoEntrata_QNAME, RicercaDocumentoEntrata.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link RicercaDocumentoEntrataResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://siac.csi.it/ricerche/svc/1.0", name = "ricercaDocumentoEntrataResponse")
    public JAXBElement<RicercaDocumentoEntrataResponse> createRicercaDocumentoEntrataResponse(RicercaDocumentoEntrataResponse value) {
        return new JAXBElement<RicercaDocumentoEntrataResponse>(_RicercaDocumentoEntrataResponse_QNAME, RicercaDocumentoEntrataResponse.class, null, value);
    }

}
