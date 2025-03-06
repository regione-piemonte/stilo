
package it.eng.hsm.client.pkbox.envelope.generated;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;

import it.eng.hsm.client.pkbox.common.generated.CertificateValue;


/**
 * <p>Classe Java per anonymous complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="environment" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="headerValues" type="{http://www.w3.org/2001/XMLSchema}base64Binary"/>
 *         &lt;element name="toRecipients" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="ccRecipients" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="bccRecipients" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="subject" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="sbjCharset" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="message" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="msgCharset" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *         &lt;element name="document" type="{http://server.pkbox.it/xsd}CertificateValue" maxOccurs="unbounded"/>
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
    "environment",
    "headerValues",
    "toRecipients",
    "ccRecipients",
    "bccRecipients",
    "subject",
    "sbjCharset",
    "message",
    "msgCharset",
    "document"
})
@XmlRootElement(name = "smimesign")
public class Smimesign {

    @XmlElement(required = true, nillable = true)
    protected String environment;
    @XmlElement(required = true, nillable = true)
    protected byte[] headerValues;
    @XmlElement(required = true, nillable = true)
    protected String toRecipients;
    @XmlElement(required = true, nillable = true)
    protected String ccRecipients;
    @XmlElement(required = true, nillable = true)
    protected String bccRecipients;
    @XmlElement(required = true, nillable = true)
    protected String subject;
    protected int sbjCharset;
    @XmlElement(required = true, nillable = true)
    protected String message;
    @XmlElement(required = true, nillable = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar msgCharset;
    @XmlElement(required = true, nillable = true)
    protected List<CertificateValue> document;

    /**
     * Recupera il valore della proprietà environment.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEnvironment() {
        return environment;
    }

    /**
     * Imposta il valore della proprietà environment.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEnvironment(String value) {
        this.environment = value;
    }

    /**
     * Recupera il valore della proprietà headerValues.
     * 
     * @return
     *     possible object is
     *     byte[]
     */
    public byte[] getHeaderValues() {
        return headerValues;
    }

    /**
     * Imposta il valore della proprietà headerValues.
     * 
     * @param value
     *     allowed object is
     *     byte[]
     */
    public void setHeaderValues(byte[] value) {
        this.headerValues = value;
    }

    /**
     * Recupera il valore della proprietà toRecipients.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getToRecipients() {
        return toRecipients;
    }

    /**
     * Imposta il valore della proprietà toRecipients.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setToRecipients(String value) {
        this.toRecipients = value;
    }

    /**
     * Recupera il valore della proprietà ccRecipients.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCcRecipients() {
        return ccRecipients;
    }

    /**
     * Imposta il valore della proprietà ccRecipients.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCcRecipients(String value) {
        this.ccRecipients = value;
    }

    /**
     * Recupera il valore della proprietà bccRecipients.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBccRecipients() {
        return bccRecipients;
    }

    /**
     * Imposta il valore della proprietà bccRecipients.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBccRecipients(String value) {
        this.bccRecipients = value;
    }

    /**
     * Recupera il valore della proprietà subject.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSubject() {
        return subject;
    }

    /**
     * Imposta il valore della proprietà subject.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSubject(String value) {
        this.subject = value;
    }

    /**
     * Recupera il valore della proprietà sbjCharset.
     * 
     */
    public int getSbjCharset() {
        return sbjCharset;
    }

    /**
     * Imposta il valore della proprietà sbjCharset.
     * 
     */
    public void setSbjCharset(int value) {
        this.sbjCharset = value;
    }

    /**
     * Recupera il valore della proprietà message.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMessage() {
        return message;
    }

    /**
     * Imposta il valore della proprietà message.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMessage(String value) {
        this.message = value;
    }

    /**
     * Recupera il valore della proprietà msgCharset.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getMsgCharset() {
        return msgCharset;
    }

    /**
     * Imposta il valore della proprietà msgCharset.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setMsgCharset(XMLGregorianCalendar value) {
        this.msgCharset = value;
    }

    /**
     * Gets the value of the document property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the document property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getDocument().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link CertificateValue }
     * 
     * 
     */
    public List<CertificateValue> getDocument() {
        if (document == null) {
            document = new ArrayList<CertificateValue>();
        }
        return this.document;
    }

}
