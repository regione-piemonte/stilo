
package it.itagile.firmaremota.ws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per RemoteSignatureXadesParams complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="RemoteSignatureXadesParams"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="detachedReferenceURI" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="elemenXPath" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="form" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="signatureId" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="type" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="validationData" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RemoteSignatureXadesParams", propOrder = {
    "detachedReferenceURI",
    "elemenXPath",
    "form",
    "signatureId",
    "type",
    "validationData"
})
public class RemoteSignatureXadesParams {

    @XmlElement(required = true, nillable = true)
    protected String detachedReferenceURI;
    @XmlElement(required = true, nillable = true)
    protected String elemenXPath;
    @XmlElement(required = true, nillable = true)
    protected String form;
    @XmlElement(required = true, nillable = true)
    protected String signatureId;
    @XmlElement(required = true, nillable = true)
    protected String type;
    @XmlElement(required = true, nillable = true)
    protected String validationData;

    /**
     * Recupera il valore della proprietà detachedReferenceURI.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDetachedReferenceURI() {
        return detachedReferenceURI;
    }

    /**
     * Imposta il valore della proprietà detachedReferenceURI.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDetachedReferenceURI(String value) {
        this.detachedReferenceURI = value;
    }

    /**
     * Recupera il valore della proprietà elemenXPath.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getElemenXPath() {
        return elemenXPath;
    }

    /**
     * Imposta il valore della proprietà elemenXPath.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setElemenXPath(String value) {
        this.elemenXPath = value;
    }

    /**
     * Recupera il valore della proprietà form.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getForm() {
        return form;
    }

    /**
     * Imposta il valore della proprietà form.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setForm(String value) {
        this.form = value;
    }

    /**
     * Recupera il valore della proprietà signatureId.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSignatureId() {
        return signatureId;
    }

    /**
     * Imposta il valore della proprietà signatureId.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSignatureId(String value) {
        this.signatureId = value;
    }

    /**
     * Recupera il valore della proprietà type.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getType() {
        return type;
    }

    /**
     * Imposta il valore della proprietà type.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setType(String value) {
        this.type = value;
    }

    /**
     * Recupera il valore della proprietà validationData.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getValidationData() {
        return validationData;
    }

    /**
     * Imposta il valore della proprietà validationData.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setValidationData(String value) {
        this.validationData = value;
    }

}
