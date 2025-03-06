
package it.doqui.acta.acaris.sms;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per StepErrorAction complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="StepErrorAction"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="step" type="{common.acaris.acta.doqui.it}integer"/&gt;
 *         &lt;element name="action" type="{documentservice.acaris.acta.doqui.it}enumStepErrorAction"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "StepErrorAction", namespace = "documentservice.acaris.acta.doqui.it", propOrder = {
    "step",
    "action"
})
public class StepErrorAction {

    @XmlElement(namespace = "")
    protected int step;
    @XmlElement(namespace = "", required = true)
    @XmlSchemaType(name = "string")
    protected EnumStepErrorAction action;

    /**
     * Recupera il valore della proprietà step.
     * 
     */
    public int getStep() {
        return step;
    }

    /**
     * Imposta il valore della proprietà step.
     * 
     */
    public void setStep(int value) {
        this.step = value;
    }

    /**
     * Recupera il valore della proprietà action.
     * 
     * @return
     *     possible object is
     *     {@link EnumStepErrorAction }
     *     
     */
    public EnumStepErrorAction getAction() {
        return action;
    }

    /**
     * Imposta il valore della proprietà action.
     * 
     * @param value
     *     allowed object is
     *     {@link EnumStepErrorAction }
     *     
     */
    public void setAction(EnumStepErrorAction value) {
        this.action = value;
    }

}
