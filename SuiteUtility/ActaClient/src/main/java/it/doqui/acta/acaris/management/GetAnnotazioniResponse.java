
package it.doqui.acta.acaris.management;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
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
 *         &lt;element name="annotazioni" type="{common.acaris.acta.doqui.it}AnnotazioniPropertiesType" minOccurs="0"/&gt;
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
    "annotazioni"
})
@XmlRootElement(name = "getAnnotazioniResponse", namespace = "management.acaris.acta.doqui.it")
public class GetAnnotazioniResponse {

    protected AnnotazioniPropertiesType annotazioni;

    /**
     * Recupera il valore della propriet� annotazioni.
     * 
     * @return
     *     possible object is
     *     {@link AnnotazioniPropertiesType }
     *     
     */
    public AnnotazioniPropertiesType getAnnotazioni() {
        return annotazioni;
    }

    /**
     * Imposta il valore della propriet� annotazioni.
     * 
     * @param value
     *     allowed object is
     *     {@link AnnotazioniPropertiesType }
     *     
     */
    public void setAnnotazioni(AnnotazioniPropertiesType value) {
        this.annotazioni = value;
    }

}
