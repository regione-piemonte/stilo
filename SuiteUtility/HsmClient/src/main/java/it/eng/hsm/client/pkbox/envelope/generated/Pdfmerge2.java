
package it.eng.hsm.client.pkbox.envelope.generated;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


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
 *         &lt;element name="document" type="{http://www.w3.org/2001/XMLSchema}base64Binary"/>
 *         &lt;element name="envelope" type="{http://www.w3.org/2001/XMLSchema}base64Binary"/>
 *         &lt;element name="accessPermissions" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="signerName" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="fieldName" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="sigLayout" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="reason" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="location" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="contact" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="date" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *         &lt;element name="image" type="{http://www.w3.org/2001/XMLSchema}base64Binary"/>
 *         &lt;element name="page" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="position" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="x" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="y" type="{http://www.w3.org/2001/XMLSchema}int"/>
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
    "document",
    "envelope",
    "accessPermissions",
    "signerName",
    "fieldName",
    "sigLayout",
    "reason",
    "location",
    "contact",
    "date",
    "image",
    "page",
    "position",
    "x",
    "y"
})
@XmlRootElement(name = "pdfmerge2")
public class Pdfmerge2 {

    @XmlElement(required = true, nillable = true)
    protected String environment;
    @XmlElement(required = true, nillable = true)
    protected byte[] document;
    @XmlElement(required = true, nillable = true)
    protected byte[] envelope;
    protected int accessPermissions;
    @XmlElement(required = true, nillable = true)
    protected String signerName;
    @XmlElement(required = true, nillable = true)
    protected String fieldName;
    @XmlElement(required = true, nillable = true)
    protected String sigLayout;
    @XmlElement(required = true, nillable = true)
    protected String reason;
    @XmlElement(required = true, nillable = true)
    protected String location;
    @XmlElement(required = true, nillable = true)
    protected String contact;
    @XmlElement(required = true, nillable = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar date;
    @XmlElement(required = true, nillable = true)
    protected byte[] image;
    protected int page;
    protected int position;
    protected int x;
    protected int y;

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
     * Recupera il valore della proprietà document.
     * 
     * @return
     *     possible object is
     *     byte[]
     */
    public byte[] getDocument() {
        return document;
    }

    /**
     * Imposta il valore della proprietà document.
     * 
     * @param value
     *     allowed object is
     *     byte[]
     */
    public void setDocument(byte[] value) {
        this.document = value;
    }

    /**
     * Recupera il valore della proprietà envelope.
     * 
     * @return
     *     possible object is
     *     byte[]
     */
    public byte[] getEnvelope() {
        return envelope;
    }

    /**
     * Imposta il valore della proprietà envelope.
     * 
     * @param value
     *     allowed object is
     *     byte[]
     */
    public void setEnvelope(byte[] value) {
        this.envelope = value;
    }

    /**
     * Recupera il valore della proprietà accessPermissions.
     * 
     */
    public int getAccessPermissions() {
        return accessPermissions;
    }

    /**
     * Imposta il valore della proprietà accessPermissions.
     * 
     */
    public void setAccessPermissions(int value) {
        this.accessPermissions = value;
    }

    /**
     * Recupera il valore della proprietà signerName.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSignerName() {
        return signerName;
    }

    /**
     * Imposta il valore della proprietà signerName.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSignerName(String value) {
        this.signerName = value;
    }

    /**
     * Recupera il valore della proprietà fieldName.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFieldName() {
        return fieldName;
    }

    /**
     * Imposta il valore della proprietà fieldName.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFieldName(String value) {
        this.fieldName = value;
    }

    /**
     * Recupera il valore della proprietà sigLayout.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSigLayout() {
        return sigLayout;
    }

    /**
     * Imposta il valore della proprietà sigLayout.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSigLayout(String value) {
        this.sigLayout = value;
    }

    /**
     * Recupera il valore della proprietà reason.
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
     * Imposta il valore della proprietà reason.
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
     * Recupera il valore della proprietà location.
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
     * Imposta il valore della proprietà location.
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
     * Recupera il valore della proprietà contact.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getContact() {
        return contact;
    }

    /**
     * Imposta il valore della proprietà contact.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setContact(String value) {
        this.contact = value;
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
     * Recupera il valore della proprietà image.
     * 
     * @return
     *     possible object is
     *     byte[]
     */
    public byte[] getImage() {
        return image;
    }

    /**
     * Imposta il valore della proprietà image.
     * 
     * @param value
     *     allowed object is
     *     byte[]
     */
    public void setImage(byte[] value) {
        this.image = value;
    }

    /**
     * Recupera il valore della proprietà page.
     * 
     */
    public int getPage() {
        return page;
    }

    /**
     * Imposta il valore della proprietà page.
     * 
     */
    public void setPage(int value) {
        this.page = value;
    }

    /**
     * Recupera il valore della proprietà position.
     * 
     */
    public int getPosition() {
        return position;
    }

    /**
     * Imposta il valore della proprietà position.
     * 
     */
    public void setPosition(int value) {
        this.position = value;
    }

    /**
     * Recupera il valore della proprietà x.
     * 
     */
    public int getX() {
        return x;
    }

    /**
     * Imposta il valore della proprietà x.
     * 
     */
    public void setX(int value) {
        this.x = value;
    }

    /**
     * Recupera il valore della proprietà y.
     * 
     */
    public int getY() {
        return y;
    }

    /**
     * Imposta il valore della proprietà y.
     * 
     */
    public void setY(int value) {
        this.y = value;
    }

}
