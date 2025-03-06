
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
 *         &lt;element name="closeSignatureSessionReturn" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
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
    "closeSignatureSessionReturn"
})
@XmlRootElement(name = "closeSignatureSessionResponse")
public class CloseSignatureSessionResponse {

    @XmlElement(required = true)
    protected String closeSignatureSessionReturn;

    /**
     * Recupera il valore della proprietà closeSignatureSessionReturn.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCloseSignatureSessionReturn() {
        return closeSignatureSessionReturn;
    }

    /**
     * Imposta il valore della proprietà closeSignatureSessionReturn.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCloseSignatureSessionReturn(String value) {
        this.closeSignatureSessionReturn = value;
    }

}
