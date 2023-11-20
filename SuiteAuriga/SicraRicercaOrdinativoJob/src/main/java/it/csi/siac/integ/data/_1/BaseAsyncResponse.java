/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */
package it.csi.siac.integ.data._1;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;
import it.csi.siac.documenti.svc._1.ElaboraDocumentoAsyncResponse;


/**
 * <p>Classe Java per baseAsyncResponse complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="baseAsyncResponse"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{http://siac.csi.it/integ/data/1.0}baseResponse"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="idOperazioneAsincrona" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/extension&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "baseAsyncResponse", propOrder = {
    "idOperazioneAsincrona"
})
@XmlSeeAlso({
    ElaboraDocumentoAsyncResponse.class
})
public abstract class BaseAsyncResponse
    extends BaseResponse
{

    protected Integer idOperazioneAsincrona;

    /**
     * Recupera il valore della proprietà idOperazioneAsincrona.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getIdOperazioneAsincrona() {
        return idOperazioneAsincrona;
    }

    /**
     * Imposta il valore della proprietà idOperazioneAsincrona.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setIdOperazioneAsincrona(Integer value) {
        this.idOperazioneAsincrona = value;
    }

}
