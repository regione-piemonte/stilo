/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vJAXB 2.1.10 in JDK 6 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2012.05.07 at 02:48:35 PM CEST 
//


package it.eng.aurigamailbusiness.daticert;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "gestoreEmittente",
    "data",
    "identificativo",
    "msgid",
    "ricevuta",
    "consegna",
    "ricezione",
    "erroreEsteso"
})
@XmlRootElement(name = "dati")
public class Dati {

    @XmlElement(name = "gestore-emittente", required = true)
    protected String gestoreEmittente;
    @XmlElement(required = true)
    protected Data data;
    @XmlElement(required = true)
    protected String identificativo;
    protected String msgid;
    protected Ricevuta ricevuta;
    protected String consegna;
    protected List<Ricezione> ricezione;
    @XmlElement(name = "errore-esteso")
    protected String erroreEsteso;

    /**
     * Gets the value of the gestoreEmittente property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getGestoreEmittente() {
        return gestoreEmittente;
    }

    /**
     * Sets the value of the gestoreEmittente property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setGestoreEmittente(String value) {
        this.gestoreEmittente = value;
    }

    /**
     * Gets the value of the data property.
     * 
     * @return
     *     possible object is
     *     {@link Data }
     *     
     */
    public Data getData() {
        return data;
    }

    /**
     * Sets the value of the data property.
     * 
     * @param value
     *     allowed object is
     *     {@link Data }
     *     
     */
    public void setData(Data value) {
        this.data = value;
    }

    /**
     * Gets the value of the identificativo property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIdentificativo() {
        return identificativo;
    }

    /**
     * Sets the value of the identificativo property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIdentificativo(String value) {
        this.identificativo = value;
    }

    /**
     * Gets the value of the msgid property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMsgid() {
        return msgid;
    }

    /**
     * Sets the value of the msgid property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMsgid(String value) {
        this.msgid = value;
    }

    /**
     * Gets the value of the ricevuta property.
     * 
     * @return
     *     possible object is
     *     {@link Ricevuta }
     *     
     */
    public Ricevuta getRicevuta() {
        return ricevuta;
    }

    /**
     * Sets the value of the ricevuta property.
     * 
     * @param value
     *     allowed object is
     *     {@link Ricevuta }
     *     
     */
    public void setRicevuta(Ricevuta value) {
        this.ricevuta = value;
    }

    /**
     * Gets the value of the consegna property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getConsegna() {
        return consegna;
    }

    /**
     * Sets the value of the consegna property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setConsegna(String value) {
        this.consegna = value;
    }

    /**
     * Gets the value of the ricezione property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the ricezione property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getRicezione().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Ricezione }
     * 
     * 
     */
    public List<Ricezione> getRicezione() {
        if (ricezione == null) {
            ricezione = new ArrayList<Ricezione>();
        }
        return this.ricezione;
    }

    /**
     * Gets the value of the erroreEsteso property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getErroreEsteso() {
        return erroreEsteso;
    }

    /**
     * Sets the value of the erroreEsteso property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setErroreEsteso(String value) {
        this.erroreEsteso = value;
    }

}
