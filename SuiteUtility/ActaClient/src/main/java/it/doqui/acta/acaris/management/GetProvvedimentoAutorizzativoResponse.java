
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
 *         &lt;element name="provvedimentoAutorizzat" type="{management.acaris.acta.doqui.it}ProvvedimentoAutorizzatType" minOccurs="0"/&gt;
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
    "provvedimentoAutorizzat"
})
@XmlRootElement(name = "getProvvedimentoAutorizzativoResponse", namespace = "management.acaris.acta.doqui.it")
public class GetProvvedimentoAutorizzativoResponse {

    protected ProvvedimentoAutorizzatType provvedimentoAutorizzat;

    /**
     * Recupera il valore della propriet� provvedimentoAutorizzat.
     * 
     * @return
     *     possible object is
     *     {@link ProvvedimentoAutorizzatType }
     *     
     */
    public ProvvedimentoAutorizzatType getProvvedimentoAutorizzat() {
        return provvedimentoAutorizzat;
    }

    /**
     * Imposta il valore della propriet� provvedimentoAutorizzat.
     * 
     * @param value
     *     allowed object is
     *     {@link ProvvedimentoAutorizzatType }
     *     
     */
    public void setProvvedimentoAutorizzat(ProvvedimentoAutorizzatType value) {
        this.provvedimentoAutorizzat = value;
    }

}
