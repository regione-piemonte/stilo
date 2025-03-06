
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
 *         &lt;element name="signP7MReturn" type="{http://www.w3.org/2001/XMLSchema}base64Binary"/&gt;
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
    "signP7MReturn"
})
@XmlRootElement(name = "signP7MResponse")
public class SignP7MResponse {

    @XmlElement(required = true)
    protected byte[] signP7MReturn;

    /**
     * Recupera il valore della proprietà signP7MReturn.
     * 
     * @return
     *     possible object is
     *     byte[]
     */
    public byte[] getSignP7MReturn() {
        return signP7MReturn;
    }

    /**
     * Imposta il valore della proprietà signP7MReturn.
     * 
     * @param value
     *     allowed object is
     *     byte[]
     */
    public void setSignP7MReturn(byte[] value) {
        this.signP7MReturn = value;
    }

}
