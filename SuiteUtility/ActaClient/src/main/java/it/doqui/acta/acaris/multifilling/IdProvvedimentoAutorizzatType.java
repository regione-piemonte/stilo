
package it.doqui.acta.acaris.multifilling;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per IdProvvedimentoAutorizzatType complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="IdProvvedimentoAutorizzatType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="value" type="{common.acaris.acta.doqui.it}IDDBType"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "IdProvvedimentoAutorizzatType", namespace = "common.acaris.acta.doqui.it", propOrder = {
    "value"
})
public class IdProvvedimentoAutorizzatType {

    @XmlElement(namespace = "")
    protected long value;

    /**
     * Recupera il valore della proprietà value.
     * 
     */
    public long getValue() {
        return value;
    }

    /**
     * Imposta il valore della proprietà value.
     * 
     */
    public void setValue(long value) {
        this.value = value;
    }

}
