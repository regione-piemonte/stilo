/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */
package it.eng.fileOperation.clientws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per InputCompletaFirmaPadesType complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="InputCompletaFirmaPadesType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{it.eng.fileoperation.ws.base}AbstractInputOperationType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="encodedSignedHash" type="{http://www.w3.org/2001/XMLSchema}string" form="qualified"/&gt;
 *         &lt;element name="nomeCampoFirma" type="{http://www.w3.org/2001/XMLSchema}string" form="qualified"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/extension&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "InputCompletaFirmaPadesType", namespace = "it.eng.fileoperation.ws.timbro", propOrder = {
    "encodedSignedHash",
    "nomeCampoFirma"
})
public class InputCompletaFirmaPadesType
    extends AbstractInputOperationType
{

    @XmlElement(required = true)
    protected String encodedSignedHash;
    @XmlElement(required = true)
    protected String nomeCampoFirma;

    /**
     * Recupera il valore della proprietà encodedSignedHash.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEncodedSignedHash() {
        return encodedSignedHash;
    }

    /**
     * Imposta il valore della proprietà encodedSignedHash.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEncodedSignedHash(String value) {
        this.encodedSignedHash = value;
    }

    /**
     * Recupera il valore della proprietà nomeCampoFirma.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNomeCampoFirma() {
        return nomeCampoFirma;
    }

    /**
     * Imposta il valore della proprietà nomeCampoFirma.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNomeCampoFirma(String value) {
        this.nomeCampoFirma = value;
    }

}
