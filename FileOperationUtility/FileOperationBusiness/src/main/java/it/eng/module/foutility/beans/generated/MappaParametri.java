/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vhudson-jaxb-ri-2.1-2 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2023.01.20 at 10:39:55 AM CET 
//


package it.eng.module.foutility.beans.generated;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * tipo per specificare i valori dei parametri utilizzati nei testi parametrici
 * 
 * <p>Java class for mappaParametri complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="mappaParametri">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="parametro" maxOccurs="unbounded">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="chiave" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                   &lt;element name="valore" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "mappaParametri", namespace = "it.eng.fileoperation.ws.timbro", propOrder = {
    "parametro"
})
public class MappaParametri
    implements Serializable
{

    @XmlElement(required = true)
    protected List<MappaParametri.Parametro> parametro;

    /**
     * Gets the value of the parametro property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the parametro property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getParametro().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link MappaParametri.Parametro }
     * 
     * 
     */
    public List<MappaParametri.Parametro> getParametro() {
        if (parametro == null) {
            parametro = new ArrayList<MappaParametri.Parametro>();
        }
        return this.parametro;
    }


    /**
     * <p>Java class for anonymous complex type.
     * 
     * <p>The following schema fragment specifies the expected content contained within this class.
     * 
     * <pre>
     * &lt;complexType>
     *   &lt;complexContent>
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *       &lt;sequence>
     *         &lt;element name="chiave" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *         &lt;element name="valore" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *       &lt;/sequence>
     *     &lt;/restriction>
     *   &lt;/complexContent>
     * &lt;/complexType>
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "chiave",
        "valore"
    })
    public static class Parametro
        implements Serializable
    {

        @XmlElement(namespace = "it.eng.fileoperation.ws.timbro", required = true)
        protected String chiave;
        @XmlElement(namespace = "it.eng.fileoperation.ws.timbro", required = true)
        protected String valore;

        /**
         * Gets the value of the chiave property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getChiave() {
            return chiave;
        }

        /**
         * Sets the value of the chiave property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setChiave(String value) {
            this.chiave = value;
        }

        /**
         * Gets the value of the valore property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getValore() {
            return valore;
        }

        /**
         * Sets the value of the valore property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setValore(String value) {
            this.valore = value;
        }

    }

}
