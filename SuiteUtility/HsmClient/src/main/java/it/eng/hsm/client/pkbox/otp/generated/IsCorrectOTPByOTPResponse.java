
package it.eng.hsm.client.pkbox.otp.generated;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per isCorrectOTPByOTPResponse complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="isCorrectOTPByOTPResponse">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="return" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "isCorrectOTPByOTPResponse", propOrder = {
    "_return"
})
public class IsCorrectOTPByOTPResponse {

    @XmlElement(name = "return")
    protected boolean _return;

    /**
     * Recupera il valore della proprietà return.
     * 
     */
    public boolean isReturn() {
        return _return;
    }

    /**
     * Imposta il valore della proprietà return.
     * 
     */
    public void setReturn(boolean value) {
        this._return = value;
    }

}
