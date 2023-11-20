/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */
package it.eng.fileOperation.clientws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Classe Java per TimeStampInfotype complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="TimeStampInfotype"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{it.eng.fileoperation.ws.base}AbstractResponseOperationType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="hashAlgOID" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="dateMillisec" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="date" type="{http://www.w3.org/2001/XMLSchema}date"/&gt;
 *         &lt;element name="format" type="{it.eng.fileoperation.ws.base}EnvelopeType"/&gt;
 *         &lt;element name="tsaInfo"&gt;
 *           &lt;complexType&gt;
 *             &lt;complexContent&gt;
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                 &lt;sequence&gt;
 *                   &lt;element name="tsaName" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                   &lt;element name="reliable" type="{http://www.w3.org/2001/XMLSchema}boolean"/&gt;
 *                 &lt;/sequence&gt;
 *               &lt;/restriction&gt;
 *             &lt;/complexContent&gt;
 *           &lt;/complexType&gt;
 *         &lt;/element&gt;
 *       &lt;/sequence&gt;
 *     &lt;/extension&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "TimeStampInfotype", propOrder = {
    "hashAlgOID",
    "dateMillisec",
    "date",
    "format",
    "tsaInfo"
})
public class TimeStampInfotype
    extends AbstractResponseOperationType
{

    @XmlElement(required = true)
    protected String hashAlgOID;
    @XmlElement(required = true)
    protected String dateMillisec;
    @XmlElement(required = true)
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar date;
    @XmlElement(required = true)
    @XmlSchemaType(name = "string")
    protected EnvelopeType format;
    @XmlElement(required = true)
    protected TimeStampInfotype.TsaInfo tsaInfo;

    /**
     * Recupera il valore della proprietà hashAlgOID.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getHashAlgOID() {
        return hashAlgOID;
    }

    /**
     * Imposta il valore della proprietà hashAlgOID.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setHashAlgOID(String value) {
        this.hashAlgOID = value;
    }

    /**
     * Recupera il valore della proprietà dateMillisec.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDateMillisec() {
        return dateMillisec;
    }

    /**
     * Imposta il valore della proprietà dateMillisec.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDateMillisec(String value) {
        this.dateMillisec = value;
    }

    /**
     * Recupera il valore della proprietà date.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDate() {
        return date;
    }

    /**
     * Imposta il valore della proprietà date.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDate(XMLGregorianCalendar value) {
        this.date = value;
    }

    /**
     * Recupera il valore della proprietà format.
     * 
     * @return
     *     possible object is
     *     {@link EnvelopeType }
     *     
     */
    public EnvelopeType getFormat() {
        return format;
    }

    /**
     * Imposta il valore della proprietà format.
     * 
     * @param value
     *     allowed object is
     *     {@link EnvelopeType }
     *     
     */
    public void setFormat(EnvelopeType value) {
        this.format = value;
    }

    /**
     * Recupera il valore della proprietà tsaInfo.
     * 
     * @return
     *     possible object is
     *     {@link TimeStampInfotype.TsaInfo }
     *     
     */
    public TimeStampInfotype.TsaInfo getTsaInfo() {
        return tsaInfo;
    }

    /**
     * Imposta il valore della proprietà tsaInfo.
     * 
     * @param value
     *     allowed object is
     *     {@link TimeStampInfotype.TsaInfo }
     *     
     */
    public void setTsaInfo(TimeStampInfotype.TsaInfo value) {
        this.tsaInfo = value;
    }


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
     *         &lt;element name="tsaName" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *         &lt;element name="reliable" type="{http://www.w3.org/2001/XMLSchema}boolean"/&gt;
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
        "tsaName",
        "reliable"
    })
    public static class TsaInfo {

        @XmlElement(required = true)
        protected String tsaName;
        protected boolean reliable;

        /**
         * Recupera il valore della proprietà tsaName.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getTsaName() {
            return tsaName;
        }

        /**
         * Imposta il valore della proprietà tsaName.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setTsaName(String value) {
            this.tsaName = value;
        }

        /**
         * Recupera il valore della proprietà reliable.
         * 
         */
        public boolean isReliable() {
            return reliable;
        }

        /**
         * Imposta il valore della proprietà reliable.
         * 
         */
        public void setReliable(boolean value) {
            this.reliable = value;
        }

    }

}
