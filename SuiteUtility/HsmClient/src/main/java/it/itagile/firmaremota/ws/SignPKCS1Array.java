
package it.itagile.firmaremota.ws;

import java.util.ArrayList;
import java.util.List;
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
 *         &lt;element name="digest" type="{http://www.w3.org/2001/XMLSchema}base64Binary" maxOccurs="unbounded"/&gt;
 *         &lt;element name="digestType" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="X509certificate" type="{http://www.w3.org/2001/XMLSchema}base64Binary"/&gt;
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
    "digestType",
    "x509Certificate"
})
@XmlRootElement(name = "signPKCS1Array")
public class SignPKCS1Array {

    @XmlElement(required = true)
    protected RemoteSignatureCredentials cred;
    @XmlElement(required = true)
    protected List<byte[]> digest;
    @XmlElement(required = true)
    protected String digestType;
    @XmlElement(name = "X509certificate", required = true)
    protected byte[] x509Certificate;

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
     * Gets the value of the digest property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the digest property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getDigest().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * byte[]
     * 
     */
    public List<byte[]> getDigest() {
        if (digest == null) {
            digest = new ArrayList<byte[]>();
        }
        return this.digest;
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

}
