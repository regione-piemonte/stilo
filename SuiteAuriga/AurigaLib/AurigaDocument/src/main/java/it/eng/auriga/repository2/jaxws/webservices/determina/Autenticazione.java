/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */
package it.eng.auriga.repository2.jaxws.webservices.determina;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per Autenticazione complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="Autenticazione">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="User" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="Password" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="IstanzaApplicativa" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="CodiceApplicazione" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Autenticazione", propOrder = {
    "user",
    "password",
    "istanzaApplicativa",
    "codiceApplicazione"
})
public class Autenticazione{

    @XmlElement(name = "User", required = true)
    protected String user;
    @XmlElement(name = "Password", required = true)
    protected String password;
    @XmlElement(name = "IstanzaApplicativa", required = true)
    protected String istanzaApplicativa;
    @XmlElement(name = "CodiceApplicazione", required = true)
    protected String codiceApplicazione;

    /**
     * Recupera il valore della proprietà user.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUser() {
        return user;
    }

    /**
     * Imposta il valore della proprietà user.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUser(String value) {
        this.user = value;
    }

    /**
     * Recupera il valore della proprietà password.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPassword() {
        return password;
    }

    /**
     * Imposta il valore della proprietà password.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPassword(String value) {
        this.password = value;
    }

    /**
     * Recupera il valore della proprietà istanzaApplicativa.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIstanzaApplicativa() {
        return istanzaApplicativa;
    }

    /**
     * Imposta il valore della proprietà istanzaApplicativa.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIstanzaApplicativa(String value) {
        this.istanzaApplicativa = value;
    }

    /**
     * Recupera il valore della proprietà codiceApplicazione.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCodiceApplicazione() {
        return codiceApplicazione;
    }

    /**
     * Imposta il valore della proprietà codiceApplicazione.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCodiceApplicazione(String value) {
        this.codiceApplicazione = value;
    }

}
