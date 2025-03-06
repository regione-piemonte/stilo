
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
 *         &lt;element name="signPKCS1Return" type="{http://www.w3.org/2001/XMLSchema}base64Binary"/&gt;
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
    "signPKCS1Return"
})
@XmlRootElement(name = "signPKCS1Response")
public class SignPKCS1Response {

    @XmlElement(required = true)
    protected byte[] signPKCS1Return;

    /**
     * Recupera il valore della proprietà signPKCS1Return.
     * 
     * @return
     *     possible object is
     *     byte[]
     */
    public byte[] getSignPKCS1Return() {
        return signPKCS1Return;
    }

    /**
     * Imposta il valore della proprietà signPKCS1Return.
     * 
     * @param value
     *     allowed object is
     *     byte[]
     */
    public void setSignPKCS1Return(byte[] value) {
        this.signPKCS1Return = value;
    }

}
