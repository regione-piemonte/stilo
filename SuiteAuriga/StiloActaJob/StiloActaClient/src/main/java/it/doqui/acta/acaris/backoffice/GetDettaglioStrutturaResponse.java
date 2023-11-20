/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */
package it.doqui.acta.acaris.backoffice;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per anonymous complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="dettaglioStruttura" type="{backoffice.acaris.acta.doqui.it}DettaglioStrutturaType" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "dettaglioStruttura"
})
@XmlRootElement(name = "getDettaglioStrutturaResponse")
public class GetDettaglioStrutturaResponse {

    protected DettaglioStrutturaType dettaglioStruttura;

    /**
     * Recupera il valore della proprietà dettaglioStruttura.
     * 
     * @return
     *     possible object is
     *     {@link DettaglioStrutturaType }
     *     
     */
    public DettaglioStrutturaType getDettaglioStruttura() {
        return dettaglioStruttura;
    }

    /**
     * Imposta il valore della proprietà dettaglioStruttura.
     * 
     * @param value
     *     allowed object is
     *     {@link DettaglioStrutturaType }
     *     
     */
    public void setDettaglioStruttura(DettaglioStrutturaType value) {
        this.dettaglioStruttura = value;
    }

}
