
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
 *         &lt;element name="cred" type="{http://ws.firmaremota.itagile.it}RemoteSignatureCredentials"/&gt;
 *         &lt;element name="digest" type="{http://www.w3.org/2001/XMLSchema}base64Binary"/&gt;
 *         &lt;element name="digestAlg" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
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
    "cred",
    "digest",
    "digestAlg"
})
@XmlRootElement(name = "timestampDetached")
public class TimestampDetached {

    @XmlElement(required = true)
    protected RemoteSignatureCredentials cred;
    @XmlElement(required = true)
    protected byte[] digest;
    @XmlElement(required = true)
    protected String digestAlg;

    /**
     * Recupera il valore della proprietà cred.
     * 
     * @return
     *     possible object is
     *     {@link RemoteSignatureCredentials }
     *     
     */
    public RemoteSignatureCredentials getCred() {
        return cred;
    }

    /**
     * Imposta il valore della proprietà cred.
     * 
     * @param value
     *     allowed object is
     *     {@link RemoteSignatureCredentials }
     *     
     */
    public void setCred(RemoteSignatureCredentials value) {
        this.cred = value;
    }

    /**
     * Recupera il valore della proprietà digest.
     * 
     * @return
     *     possible object is
     *     byte[]
     */
    public byte[] getDigest() {
        return digest;
    }

    /**
     * Imposta il valore della proprietà digest.
     * 
     * @param value
     *     allowed object is
     *     byte[]
     */
    public void setDigest(byte[] value) {
        this.digest = value;
    }

    /**
     * Recupera il valore della proprietà digestAlg.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDigestAlg() {
        return digestAlg;
    }

    /**
     * Imposta il valore della proprietà digestAlg.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDigestAlg(String value) {
        this.digestAlg = value;
    }

}
