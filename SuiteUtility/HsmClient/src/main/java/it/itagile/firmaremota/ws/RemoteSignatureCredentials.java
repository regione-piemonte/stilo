
package it.itagile.firmaremota.ws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per RemoteSignatureCredentials complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="RemoteSignatureCredentials"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="clientCertificateAuth" type="{http://ws.firmaremota.itagile.it}ClientCertificateAuth"/&gt;
 *         &lt;element name="extAuth" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="password" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="userid" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="oneshot" type="{http://www.w3.org/2001/XMLSchema}boolean"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RemoteSignatureCredentials", propOrder = {
    "clientCertificateAuth",
    "extAuth",
    "password",
    "userid",
    "oneshot"
})
public class RemoteSignatureCredentials {

    @XmlElement(required = true, nillable = true)
    protected ClientCertificateAuth clientCertificateAuth;
    @XmlElement(required = true, nillable = true)
    protected String extAuth;
    @XmlElement(required = true, nillable = true)
    protected String password;
    @XmlElement(required = true, nillable = true)
    protected String userid;
    protected boolean oneshot;

    /**
     * Recupera il valore della proprietà clientCertificateAuth.
     * 
     * @return
     *     possible object is
     *     {@link ClientCertificateAuth }
     *     
     */
    public ClientCertificateAuth getClientCertificateAuth() {
        return clientCertificateAuth;
    }

    /**
     * Imposta il valore della proprietà clientCertificateAuth.
     * 
     * @param value
     *     allowed object is
     *     {@link ClientCertificateAuth }
     *     
     */
    public void setClientCertificateAuth(ClientCertificateAuth value) {
        this.clientCertificateAuth = value;
    }

    /**
     * Recupera il valore della proprietà extAuth.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getExtAuth() {
        return extAuth;
    }

    /**
     * Imposta il valore della proprietà extAuth.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setExtAuth(String value) {
        this.extAuth = value;
    }

    /**
     * Recupera il valore della proprietà password.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPassword() {
        return password;
    }

    /**
     * Imposta il valore della proprietà password.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPassword(String value) {
        this.password = value;
    }

    /**
     * Recupera il valore della proprietà userid.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUserid() {
        return userid;
    }

    /**
     * Imposta il valore della proprietà userid.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUserid(String value) {
        this.userid = value;
    }

    /**
     * Recupera il valore della proprietà oneshot.
     * 
     */
    public boolean isOneshot() {
        return oneshot;
    }

    /**
     * Imposta il valore della proprietà oneshot.
     * 
     */
    public void setOneshot(boolean value) {
        this.oneshot = value;
    }

}
