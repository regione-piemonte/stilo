/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */
package it.csi.siac.integ.data._1;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;
import it.csi.siac.documenti.svc._1.BaseRicercaDocumentoResponse;
import it.csi.siac.documenti.svc._1.RicercaProvvisoriDiCassaResponse;
import it.csi.siac.ricerche.svc._1.RicercaAccertamentoResponse;
import it.csi.siac.ricerche.svc._1.RicercaImpegnoResponse;
import it.csi.siac.ricerche.svc._1.RicercaLiquidazioneResponse;
import it.csi.siac.ricerche.svc._1.RicercaOrdinativoIncassoResponse;
import it.csi.siac.ricerche.svc._1.RicercaOrdinativoSpesaResponse;


/**
 * <p>Classe Java per ricercaPaginataResponse complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="ricercaPaginataResponse"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{http://siac.csi.it/integ/data/1.0}baseRicercaResponse"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="totaleRisultati" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/extension&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ricercaPaginataResponse", propOrder = {
    "totaleRisultati"
})
@XmlSeeAlso({
    RicercaOrdinativoIncassoResponse.class,
    RicercaImpegnoResponse.class,
    RicercaOrdinativoSpesaResponse.class,
    RicercaProvvisoriDiCassaResponse.class,
    RicercaAccertamentoResponse.class,
    RicercaLiquidazioneResponse.class,
    BaseRicercaDocumentoResponse.class
})
public abstract class RicercaPaginataResponse
    extends BaseRicercaResponse
{

    protected Integer totaleRisultati;

    /**
     * Recupera il valore della proprietà totaleRisultati.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getTotaleRisultati() {
        return totaleRisultati;
    }

    /**
     * Imposta il valore della proprietà totaleRisultati.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setTotaleRisultati(Integer value) {
        this.totaleRisultati = value;
    }

}
