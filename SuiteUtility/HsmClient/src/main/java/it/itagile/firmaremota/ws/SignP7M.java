
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
 *         &lt;element name="signatureCred" type="{http://ws.firmaremota.itagile.it}RemoteSignatureCredentials"/&gt;
 *         &lt;element name="content" type="{http://www.w3.org/2001/XMLSchema}base64Binary"/&gt;
 *         &lt;element name="contentInAttachments" type="{http://www.w3.org/2001/XMLSchema}boolean"/&gt;
 *         &lt;element name="p7m" type="{http://www.w3.org/2001/XMLSchema}boolean"/&gt;
 *         &lt;element name="digestType" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="X509certificate" type="{http://www.w3.org/2001/XMLSchema}base64Binary"/&gt;
 *         &lt;element name="flags" type="{http://ws.firmaremota.itagile.it}SignatureFlags"/&gt;
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
    "signatureCred",
    "content",
    "contentInAttachments",
    "p7M",
    "digestType",
    "x509Certificate",
    "flags"
})
@XmlRootElement(name = "signP7M")
public class SignP7M {

    @XmlElement(required = true)
    protected RemoteSignatureCredentials signatureCred;
    @XmlElement(required = true)
    protected byte[] content;
    protected boolean contentInAttachments;
    @XmlElement(name = "p7m")
    protected boolean p7M;
    @XmlElement(required = true)
    protected String digestType;
    @XmlElement(name = "X509certificate", required = true)
    protected byte[] x509Certificate;
    @XmlElement(required = true)
    protected SignatureFlags flags;

    /**
     * Recupera il valore della proprietà signatureCred.
     * 
     * @return
     *     possible object is
     *     {@link RemoteSignatureCredentials }
     *     
     */
    public RemoteSignatureCredentials getSignatureCred() {
        return signatureCred;
    }

    /**
     * Imposta il valore della proprietà signatureCred.
     * 
     * @param value
     *     allowed object is
     *     {@link RemoteSignatureCredentials }
     *     
     */
    public void setSignatureCred(RemoteSignatureCredentials value) {
        this.signatureCred = value;
    }

    /**
     * Recupera il valore della proprietà content.
     * 
     * @return
     *     possible object is
     *     byte[]
     */
    public byte[] getContent() {
        return content;
    }

    /**
     * Imposta il valore della proprietà content.
     * 
     * @param value
     *     allowed object is
     *     byte[]
     */
    public void setContent(byte[] value) {
        this.content = value;
    }

    /**
     * Recupera il valore della proprietà contentInAttachments.
     * 
     */
    public boolean isContentInAttachments() {
        return contentInAttachments;
    }

    /**
     * Imposta il valore della proprietà contentInAttachments.
     * 
     */
    public void setContentInAttachments(boolean value) {
        this.contentInAttachments = value;
    }

    /**
     * Recupera il valore della proprietà p7M.
     * 
     */
    public boolean isP7M() {
        return p7M;
    }

    /**
     * Imposta il valore della proprietà p7M.
     * 
     */
    public void setP7M(boolean value) {
        this.p7M = value;
    }

    /**
     * Recupera il valore della proprietà digestType.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDigestType() {
        return digestType;
    }

    /**
     * Imposta il valore della proprietà digestType.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDigestType(String value) {
        this.digestType = value;
    }

    /**
     * Recupera il valore della proprietà x509Certificate.
     * 
     * @return
     *     possible object is
     *     byte[]
     */
    public byte[] getX509Certificate() {
        return x509Certificate;
    }

    /**
     * Imposta il valore della proprietà x509Certificate.
     * 
     * @param value
     *     allowed object is
     *     byte[]
     */
    public void setX509Certificate(byte[] value) {
        this.x509Certificate = value;
    }

    /**
     * Recupera il valore della proprietà flags.
     * 
     * @return
     *     possible object is
     *     {@link SignatureFlags }
     *     
     */
    public SignatureFlags getFlags() {
        return flags;
    }

    /**
     * Imposta il valore della proprietà flags.
     * 
     * @param value
     *     allowed object is
     *     {@link SignatureFlags }
     *     
     */
    public void setFlags(SignatureFlags value) {
        this.flags = value;
    }

}
