/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vhudson-jaxb-ri-2.1-2 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2023.01.20 at 10:39:55 AM CET 
//


package it.eng.module.foutility.beans.generated;

import java.io.Serializable;

import javax.activation.DataHandler;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * input per l'operazione di preparazione firma pades
 * 
 * <p>Java class for InputPreparaFirmaPadesType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="InputPreparaFirmaPadesType">
 *   &lt;complexContent>
 *     &lt;extension base="{it.eng.fileoperation.ws.base}AbstractInputOperationType">
 *       &lt;sequence>
 *         &lt;element name="userCertificate" type="{http://www.w3.org/2001/XMLSchema}base64Binary" minOccurs="0"/>
 *         &lt;element name="firmatario" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="nomeCampoFirma" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="reason" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="location" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="informazioniFirmaGrafica" type="{it.eng.fileoperation.ws.timbro}InformazioniFirmaGraficaType" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "InputPreparaFirmaPadesType", namespace = "it.eng.fileoperation.ws.timbro", propOrder = {
    "userCertificate",
    "firmatario",
    "nomeCampoFirma",
    "reason",
    "location",
    "informazioniFirmaGrafica"
})
public class InputPreparaFirmaPadesType
    extends AbstractInputOperationType
    implements Serializable
{

    protected DataHandler userCertificate;
    protected String firmatario;
    @XmlElement(required = true)
    protected String nomeCampoFirma;
    protected String reason;
    protected String location;
    protected InformazioniFirmaGraficaType informazioniFirmaGrafica;

    /**
     * Gets the value of the userCertificate property.
     * 
     * @return
     *     possible object is
     *     byte[]
     */
    public DataHandler getUserCertificate() {
        return userCertificate;
    }

    /**
     * Sets the value of the userCertificate property.
     * 
     * @param value
     *     allowed object is
     *     byte[]
     */
    public void setUserCertificate(DataHandler value) {
        this.userCertificate = ( value);
    }

    /**
     * Gets the value of the firmatario property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFirmatario() {
        return firmatario;
    }

    /**
     * Sets the value of the firmatario property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFirmatario(String value) {
        this.firmatario = value;
    }

    /**
     * Gets the value of the nomeCampoFirma property.
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
     * Sets the value of the nomeCampoFirma property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNomeCampoFirma(String value) {
        this.nomeCampoFirma = value;
    }

    /**
     * Gets the value of the reason property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getReason() {
        return reason;
    }

    /**
     * Sets the value of the reason property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setReason(String value) {
        this.reason = value;
    }

    /**
     * Gets the value of the location property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLocation() {
        return location;
    }

    /**
     * Sets the value of the location property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLocation(String value) {
        this.location = value;
    }

    /**
     * Gets the value of the informazioniFirmaGrafica property.
     * 
     * @return
     *     possible object is
     *     {@link InformazioniFirmaGraficaType }
     *     
     */
    public InformazioniFirmaGraficaType getInformazioniFirmaGrafica() {
        return informazioniFirmaGrafica;
    }

    /**
     * Sets the value of the informazioniFirmaGrafica property.
     * 
     * @param value
     *     allowed object is
     *     {@link InformazioniFirmaGraficaType }
     *     
     */
    public void setInformazioniFirmaGrafica(InformazioniFirmaGraficaType value) {
        this.informazioniFirmaGrafica = value;
    }

}
