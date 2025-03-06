
package it.itagile.firmaremota.ws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per Signatures complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="Signatures"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="signatures" type="{http://ws.firmaremota.itagile.it}ArrayOfSignature"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Signatures", propOrder = {
    "signatures"
})
public class Signatures {

    @XmlElement(required = true)
    protected ArrayOfSignature signatures;

    /**
     * Recupera il valore della proprietà signatures.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfSignature }
     *     
     */
    public ArrayOfSignature getSignatures() {
        return signatures;
    }

    /**
     * Imposta il valore della proprietà signatures.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfSignature }
     *     
     */
    public void setSignatures(ArrayOfSignature value) {
        this.signatures = value;
    }

}
