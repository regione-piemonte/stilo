
package it.eng.hsm.client.pkbox.envelope.generated;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import it.eng.hsm.client.pkbox.common.generated.PKBoxException2;


/**
 * <p>Classe Java per anonymous complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="PKBoxException" type="{http://soap.remote.pkserver.it/xsd}PKBoxException"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "pkBoxException"
})
@XmlRootElement(name = "PKBoxException")
public class PKBoxException {

    @XmlElement(name = "PKBoxException", required = true, nillable = true)
    protected PKBoxException2 pkBoxException;

    /**
     * Recupera il valore della proprietà pkBoxException.
     * 
     * @return
     *     possible object is
     *     {@link PKBoxException2 }
     *     
     */
    public PKBoxException2 getPKBoxException() {
        return pkBoxException;
    }

    /**
     * Imposta il valore della proprietà pkBoxException.
     * 
     * @param value
     *     allowed object is
     *     {@link PKBoxException2 }
     *     
     */
    public void setPKBoxException(PKBoxException2 value) {
        this.pkBoxException = value;
    }

}
