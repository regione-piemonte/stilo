
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
 *         &lt;element name="signXAdESReturn" type="{http://www.w3.org/2001/XMLSchema}base64Binary"/&gt;
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
    "signXAdESReturn"
})
@XmlRootElement(name = "signXAdESResponse")
public class SignXAdESResponse {

    @XmlElement(required = true)
    protected byte[] signXAdESReturn;

    /**
     * Recupera il valore della proprietà signXAdESReturn.
     * 
     * @return
     *     possible object is
     *     byte[]
     */
    public byte[] getSignXAdESReturn() {
        return signXAdESReturn;
    }

    /**
     * Imposta il valore della proprietà signXAdESReturn.
     * 
     * @param value
     *     allowed object is
     *     byte[]
     */
    public void setSignXAdESReturn(byte[] value) {
        this.signXAdESReturn = value;
    }

}
