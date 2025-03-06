
package it.doqui.acta.acaris.sms;

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
 *         &lt;element name="objects" type="{common.acaris.acta.doqui.it}IdSmistamentoType"/&gt;
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
    "objects"
})
@XmlRootElement(name = "creaSmistamentoResponse", namespace = "sms.acaris.acta.doqui.it")
public class CreaSmistamentoResponse {

    @XmlElement(namespace = "", required = true)
    protected IdSmistamentoType objects;

    /**
     * Recupera il valore della proprietà objects.
     * 
     * @return
     *     possible object is
     *     {@link IdSmistamentoType }
     *     
     */
    public IdSmistamentoType getObjects() {
        return objects;
    }

    /**
     * Imposta il valore della proprietà objects.
     * 
     * @param value
     *     allowed object is
     *     {@link IdSmistamentoType }
     *     
     */
    public void setObjects(IdSmistamentoType value) {
        this.objects = value;
    }

}
