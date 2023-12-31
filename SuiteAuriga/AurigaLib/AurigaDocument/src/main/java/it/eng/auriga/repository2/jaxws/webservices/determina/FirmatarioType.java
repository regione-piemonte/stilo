/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.5-b10 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2020.10.06 at 11:51:24 PM CEST 
//


package it.eng.auriga.repository2.jaxws.webservices.determina;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlValue;
import javax.xml.datatype.XMLGregorianCalendar;

/**
 * WSGetDetermina
 */
/**
 * <p>Java class for FirmatarioType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="FirmatarioType">
 *   &lt;complexContent>
 *     &lt;extension base="{}AutoreType">
 *       &lt;sequence>
 *         &lt;element name="Struttura" type="{}StrutturaType"/>
 *         &lt;element name="TsFirmaVisto" maxOccurs="unbounded" minOccurs="0">
 *           &lt;complexType>
 *             &lt;simpleContent>
 *               &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema>dateTime">
 *                 &lt;attribute name="firmaDigitale" use="required" type="{http://www.w3.org/2001/XMLSchema}boolean" />
 *               &lt;/extension>
 *             &lt;/simpleContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "FirmatarioType", propOrder = {
    "struttura",
    "tsFirmaVisto"
})
public class FirmatarioType
    extends AutoreType
{

    @XmlElement(name = "Struttura", required = true)
    protected StrutturaType struttura;
    @XmlElement(name = "TsFirmaVisto")
    protected List<FirmatarioType.TsFirmaVisto> tsFirmaVisto;
    
    
    
    public FirmatarioType() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
     * Gets the value of the struttura property.
     * 
     * @return
     *     possible object is
     *     {@link StrutturaType }
     *     
     */
    public StrutturaType getStruttura() {
        return struttura;
    }
  
    /**
     * Sets the value of the struttura property.
     * 
     * @param value
     *     allowed object is
     *     {@link StrutturaType }
     *     
     */
    public void setStruttura(StrutturaType value) {
        this.struttura = value;
    }

    /**
     * Gets the value of the tsFirmaVisto property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the tsFirmaVisto property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getTsFirmaVisto().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link FirmatarioType.TsFirmaVisto }
     * 
     * 
     */
    public List<FirmatarioType.TsFirmaVisto> getTsFirmaVisto() {
        if (tsFirmaVisto == null) {
            tsFirmaVisto = new ArrayList<FirmatarioType.TsFirmaVisto>();
        }
        return this.tsFirmaVisto;
    }


    /**
     * <p>Java class for anonymous complex type.
     * 
     * <p>The following schema fragment specifies the expected content contained within this class.
     * 
     * <pre>
     * &lt;complexType>
     *   &lt;simpleContent>
     *     &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema>dateTime">
     *       &lt;attribute name="firmaDigitale" use="required" type="{http://www.w3.org/2001/XMLSchema}boolean" />
     *     &lt;/extension>
     *   &lt;/simpleContent>
     * &lt;/complexType>
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "value"
    })
    public static class TsFirmaVisto {

        @XmlValue
        @XmlSchemaType(name = "dateTime")
        protected XMLGregorianCalendar value;
        @XmlAttribute(name = "firmaDigitale", required = true)
        protected boolean firmaDigitale;

        /**
         * Gets the value of the value property.
         * 
         * @return
         *     possible object is
         *     {@link XMLGregorianCalendar }
         *     
         */
        public XMLGregorianCalendar getValue() {
            return value;
        }

        /**
         * Sets the value of the value property.
         * 
         * @param value
         *     allowed object is
         *     {@link XMLGregorianCalendar }
         *     
         */
        public void setValue(XMLGregorianCalendar value) {
            this.value = value;
        }

        /**
         * Gets the value of the firmaDigitale property.
         * 
         */
        public boolean isFirmaDigitale() {
            return firmaDigitale;
        }

        /**
         * Sets the value of the firmaDigitale property.
         * 
         */
        public void setFirmaDigitale(boolean value) {
            this.firmaDigitale = value;
        }

    }

}
