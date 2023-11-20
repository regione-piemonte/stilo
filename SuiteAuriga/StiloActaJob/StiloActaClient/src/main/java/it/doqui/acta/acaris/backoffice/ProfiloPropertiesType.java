/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */
package it.doqui.acta.acaris.backoffice;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import it.doqui.acta.acaris.common.IdProfiloType;


/**
 * <p>Classe Java per ProfiloPropertiesType complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="ProfiloPropertiesType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="idProfilo" type="{common.acaris.acta.doqui.it}IdProfiloType"/&gt;
 *         &lt;element name="codice" type="{common.acaris.acta.doqui.it}string"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ProfiloPropertiesType", propOrder = {
    "idProfilo",
    "codice"
})
public class ProfiloPropertiesType {

    @XmlElement(required = true)
    protected IdProfiloType idProfilo;
    @XmlElement(required = true)
    protected String codice;

    /**
     * Recupera il valore della proprietà idProfilo.
     * 
     * @return
     *     possible object is
     *     {@link IdProfiloType }
     *     
     */
    public IdProfiloType getIdProfilo() {
        return idProfilo;
    }

    /**
     * Imposta il valore della proprietà idProfilo.
     * 
     * @param value
     *     allowed object is
     *     {@link IdProfiloType }
     *     
     */
    public void setIdProfilo(IdProfiloType value) {
        this.idProfilo = value;
    }

    /**
     * Recupera il valore della proprietà codice.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCodice() {
        return codice;
    }

    /**
     * Imposta il valore della proprietà codice.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCodice(String value) {
        this.codice = value;
    }

}
