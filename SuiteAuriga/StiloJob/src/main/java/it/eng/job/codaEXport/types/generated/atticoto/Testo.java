/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */


package it.eng.job.codaEXport.types.generated.atticoto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * File con il testo dell'atto
 * 
 * <p>Classe Java per Testo complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="Testo">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="nomeFile">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               &lt;maxLength value="255"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="uuidFile" type="{}UUIDFile"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Testo", propOrder = {
    "nomeFile",
    "uuidFile"
})
public class Testo {

    @XmlElement(required = true)
    protected String nomeFile;
    @XmlElement(required = true)
    protected String uuidFile;

    /**
     * Recupera il valore della propriet� nomeFile.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNomeFile() {
        return nomeFile;
    }

    /**
     * Imposta il valore della propriet� nomeFile.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNomeFile(String value) {
        this.nomeFile = value;
    }

    /**
     * Recupera il valore della propriet� uuidFile.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUuidFile() {
        return uuidFile;
    }

    /**
     * Imposta il valore della propriet� uuidFile.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUuidFile(String value) {
        this.uuidFile = value;
    }

}
