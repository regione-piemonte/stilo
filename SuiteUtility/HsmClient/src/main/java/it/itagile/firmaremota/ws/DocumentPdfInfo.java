
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
 *         &lt;element name="verifySignatures" type="{http://www.w3.org/2001/XMLSchema}boolean"/&gt;
 *         &lt;element name="extractFields" type="{http://www.w3.org/2001/XMLSchema}boolean"/&gt;
 *         &lt;element name="extractX509" type="{http://www.w3.org/2001/XMLSchema}boolean"/&gt;
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
    "verifySignatures",
    "extractFields",
    "extractX509"
})
@XmlRootElement(name = "documentPdfInfo")
public class DocumentPdfInfo {

    @XmlElement(required = true)
    protected byte[] signedDocument;
    protected boolean verifySignatures;
    protected boolean extractFields;
    protected boolean extractX509;

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
     * Recupera il valore della proprietà verifySignatures.
     * 
     */
    public boolean isVerifySignatures() {
        return verifySignatures;
    }

    /**
     * Imposta il valore della proprietà verifySignatures.
     * 
     */
    public void setVerifySignatures(boolean value) {
        this.verifySignatures = value;
    }

    /**
     * Recupera il valore della proprietà extractFields.
     * 
     */
    public boolean isExtractFields() {
        return extractFields;
    }

    /**
     * Imposta il valore della proprietà extractFields.
     * 
     */
    public void setExtractFields(boolean value) {
        this.extractFields = value;
    }

    /**
     * Recupera il valore della proprietà extractX509.
     * 
     */
    public boolean isExtractX509() {
        return extractX509;
    }

    /**
     * Imposta il valore della proprietà extractX509.
     * 
     */
    public void setExtractX509(boolean value) {
        this.extractX509 = value;
    }

}
