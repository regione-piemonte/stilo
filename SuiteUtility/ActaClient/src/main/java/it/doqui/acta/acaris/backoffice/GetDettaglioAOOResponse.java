
package it.doqui.acta.acaris.backoffice;

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
 *         &lt;element name="dettaglioAOO" type="{backoffice.acaris.acta.doqui.it}DettaglioAOOType" minOccurs="0"/&gt;
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
    "dettaglioAOO"
})
@XmlRootElement(name = "getDettaglioAOOResponse", namespace = "backoffice.acaris.acta.doqui.it")
public class GetDettaglioAOOResponse {

    protected DettaglioAOOType dettaglioAOO;

    /**
     * Recupera il valore della proprietà dettaglioAOO.
     * 
     * @return
     *     possible object is
     *     {@link DettaglioAOOType }
     *     
     */
    public DettaglioAOOType getDettaglioAOO() {
        return dettaglioAOO;
    }

    /**
     * Imposta il valore della proprietà dettaglioAOO.
     * 
     * @param value
     *     allowed object is
     *     {@link DettaglioAOOType }
     *     
     */
    public void setDettaglioAOO(DettaglioAOOType value) {
        this.dettaglioAOO = value;
    }

}
