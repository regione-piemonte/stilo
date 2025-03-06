
package it.eng.hsm.client.pkbox.envelope.generated;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per PDFSignatureInfo complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="PDFSignatureInfo">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="blankSignature" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="contact" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="fieldCX" type="{http://www.w3.org/2001/XMLSchema}float"/>
 *         &lt;element name="fieldCY" type="{http://www.w3.org/2001/XMLSchema}float"/>
 *         &lt;element name="fieldType" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="fieldX" type="{http://www.w3.org/2001/XMLSchema}float"/>
 *         &lt;element name="fieldY" type="{http://www.w3.org/2001/XMLSchema}float"/>
 *         &lt;element name="location" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="name" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="pageCX" type="{http://www.w3.org/2001/XMLSchema}float"/>
 *         &lt;element name="pageCY" type="{http://www.w3.org/2001/XMLSchema}float"/>
 *         &lt;element name="pageNumber" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="pageRotation" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="pageX" type="{http://www.w3.org/2001/XMLSchema}float"/>
 *         &lt;element name="pageY" type="{http://www.w3.org/2001/XMLSchema}float"/>
 *         &lt;element name="reason" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="sigCertLevel" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="sigRevision" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "PDFSignatureInfo", namespace = "http://server.pkbox.it/xsd", propOrder = {
    "blankSignature",
    "contact",
    "fieldCX",
    "fieldCY",
    "fieldType",
    "fieldX",
    "fieldY",
    "location",
    "name",
    "pageCX",
    "pageCY",
    "pageNumber",
    "pageRotation",
    "pageX",
    "pageY",
    "reason",
    "sigCertLevel",
    "sigRevision"
})
public class PDFSignatureInfo {

    protected boolean blankSignature;
    @XmlElement(required = true, nillable = true)
    protected String contact;
    protected float fieldCX;
    protected float fieldCY;
    protected int fieldType;
    protected float fieldX;
    protected float fieldY;
    @XmlElement(required = true, nillable = true)
    protected String location;
    @XmlElement(required = true, nillable = true)
    protected String name;
    protected float pageCX;
    protected float pageCY;
    protected int pageNumber;
    protected int pageRotation;
    protected float pageX;
    protected float pageY;
    @XmlElement(required = true, nillable = true)
    protected String reason;
    protected int sigCertLevel;
    protected int sigRevision;

    /**
     * Recupera il valore della proprietà blankSignature.
     * 
     */
    public boolean isBlankSignature() {
        return blankSignature;
    }

    /**
     * Imposta il valore della proprietà blankSignature.
     * 
     */
    public void setBlankSignature(boolean value) {
        this.blankSignature = value;
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
     * Recupera il valore della proprietà fieldCX.
     * 
     */
    public float getFieldCX() {
        return fieldCX;
    }

    /**
     * Imposta il valore della proprietà fieldCX.
     * 
     */
    public void setFieldCX(float value) {
        this.fieldCX = value;
    }

    /**
     * Recupera il valore della proprietà fieldCY.
     * 
     */
    public float getFieldCY() {
        return fieldCY;
    }

    /**
     * Imposta il valore della proprietà fieldCY.
     * 
     */
    public void setFieldCY(float value) {
        this.fieldCY = value;
    }

    /**
     * Recupera il valore della proprietà fieldType.
     * 
     */
    public int getFieldType() {
        return fieldType;
    }

    /**
     * Imposta il valore della proprietà fieldType.
     * 
     */
    public void setFieldType(int value) {
        this.fieldType = value;
    }

    /**
     * Recupera il valore della proprietà fieldX.
     * 
     */
    public float getFieldX() {
        return fieldX;
    }

    /**
     * Imposta il valore della proprietà fieldX.
     * 
     */
    public void setFieldX(float value) {
        this.fieldX = value;
    }

    /**
     * Recupera il valore della proprietà fieldY.
     * 
     */
    public float getFieldY() {
        return fieldY;
    }

    /**
     * Imposta il valore della proprietà fieldY.
     * 
     */
    public void setFieldY(float value) {
        this.fieldY = value;
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
     * Recupera il valore della proprietà name.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getName() {
        return name;
    }

    /**
     * Imposta il valore della proprietà name.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setName(String value) {
        this.name = value;
    }

    /**
     * Recupera il valore della proprietà pageCX.
     * 
     */
    public float getPageCX() {
        return pageCX;
    }

    /**
     * Imposta il valore della proprietà pageCX.
     * 
     */
    public void setPageCX(float value) {
        this.pageCX = value;
    }

    /**
     * Recupera il valore della proprietà pageCY.
     * 
     */
    public float getPageCY() {
        return pageCY;
    }

    /**
     * Imposta il valore della proprietà pageCY.
     * 
     */
    public void setPageCY(float value) {
        this.pageCY = value;
    }

    /**
     * Recupera il valore della proprietà pageNumber.
     * 
     */
    public int getPageNumber() {
        return pageNumber;
    }

    /**
     * Imposta il valore della proprietà pageNumber.
     * 
     */
    public void setPageNumber(int value) {
        this.pageNumber = value;
    }

    /**
     * Recupera il valore della proprietà pageRotation.
     * 
     */
    public int getPageRotation() {
        return pageRotation;
    }

    /**
     * Imposta il valore della proprietà pageRotation.
     * 
     */
    public void setPageRotation(int value) {
        this.pageRotation = value;
    }

    /**
     * Recupera il valore della proprietà pageX.
     * 
     */
    public float getPageX() {
        return pageX;
    }

    /**
     * Imposta il valore della proprietà pageX.
     * 
     */
    public void setPageX(float value) {
        this.pageX = value;
    }

    /**
     * Recupera il valore della proprietà pageY.
     * 
     */
    public float getPageY() {
        return pageY;
    }

    /**
     * Imposta il valore della proprietà pageY.
     * 
     */
    public void setPageY(float value) {
        this.pageY = value;
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
     * Recupera il valore della proprietà sigCertLevel.
     * 
     */
    public int getSigCertLevel() {
        return sigCertLevel;
    }

    /**
     * Imposta il valore della proprietà sigCertLevel.
     * 
     */
    public void setSigCertLevel(int value) {
        this.sigCertLevel = value;
    }

    /**
     * Recupera il valore della proprietà sigRevision.
     * 
     */
    public int getSigRevision() {
        return sigRevision;
    }

    /**
     * Imposta il valore della proprietà sigRevision.
     * 
     */
    public void setSigRevision(int value) {
        this.sigRevision = value;
    }

}
