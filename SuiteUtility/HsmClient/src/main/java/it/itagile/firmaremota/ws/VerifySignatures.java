
package it.itagile.firmaremota.ws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


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
 *         &lt;element name="signedDocument" type="{http://www.w3.org/2001/XMLSchema}base64Binary"/&gt;
 *         &lt;element name="docsInAttachment" type="{http://www.w3.org/2001/XMLSchema}boolean"/&gt;
 *         &lt;element name="mimeType" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
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
    "signedDocument",
    "docsInAttachment",
    "mimeType"
})
@XmlRootElement(name = "verifySignatures")
public class VerifySignatures {

    @XmlElement(required = true)
    protected byte[] signedDocument;
    protected boolean docsInAttachment;
    @XmlElement(required = true)
    protected String mimeType;

    /**
     * Recupera il valore della proprietà signedDocument.
     * 
     * @return
     *     possible object is
     *     byte[]
     */
    public byte[] getSignedDocument() {
        return signedDocument;
    }

    /**
     * Imposta il valore della proprietà signedDocument.
     * 
     * @param value
     *     allowed object is
     *     byte[]
     */
    public void setSignedDocument(byte[] value) {
        this.signedDocument = value;
    }

    /**
     * Recupera il valore della proprietà docsInAttachment.
     * 
     */
    public boolean isDocsInAttachment() {
        return docsInAttachment;
    }

    /**
     * Imposta il valore della proprietà docsInAttachment.
     * 
     */
    public void setDocsInAttachment(boolean value) {
        this.docsInAttachment = value;
    }

    /**
     * Recupera il valore della proprietà mimeType.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMimeType() {
        return mimeType;
    }

    /**
     * Imposta il valore della proprietà mimeType.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMimeType(String value) {
        this.mimeType = value;
    }

}
