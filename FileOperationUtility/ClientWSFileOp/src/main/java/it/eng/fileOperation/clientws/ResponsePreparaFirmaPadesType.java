/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */
package it.eng.fileOperation.clientws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per ResponsePreparaFirmaPadesType complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="ResponsePreparaFirmaPadesType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{it.eng.fileoperation.ws.base}AbstractResponseOperationType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="encodedHash" type="{http://www.w3.org/2001/XMLSchema}string" form="qualified"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/extension&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ResponsePreparaFirmaPadesType", namespace = "it.eng.fileoperation.ws.timbro", propOrder = {
    "encodedHash"
})
public class ResponsePreparaFirmaPadesType
    extends AbstractResponseOperationType
{

    @XmlElement(required = true)
    protected String encodedHash;

    /**
     * Recupera il valore della proprietà encodedHash.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEncodedHash() {
        return encodedHash;
    }

    /**
     * Imposta il valore della proprietà encodedHash.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEncodedHash(String value) {
        this.encodedHash = value;
    }

}
