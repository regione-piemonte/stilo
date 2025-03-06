
package it.doqui.acta.acaris.repository;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per ComplexPropertyType complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="ComplexPropertyType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{common.acaris.acta.doqui.it}PropertyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="nested" type="{common.acaris.acta.doqui.it}ComplexPropertyType"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/extension&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ComplexPropertyType", namespace = "common.acaris.acta.doqui.it", propOrder = {
    "nested"
})
public class ComplexPropertyType
    extends PropertyType
{

    @XmlElement(namespace = "", required = true)
    protected ComplexPropertyType nested;

    /**
     * Recupera il valore della proprietà nested.
     * 
     * @return
     *     possible object is
     *     {@link ComplexPropertyType }
     *     
     */
    public ComplexPropertyType getNested() {
        return nested;
    }

    /**
     * Imposta il valore della proprietà nested.
     * 
     * @param value
     *     allowed object is
     *     {@link ComplexPropertyType }
     *     
     */
    public void setNested(ComplexPropertyType value) {
        this.nested = value;
    }

}
