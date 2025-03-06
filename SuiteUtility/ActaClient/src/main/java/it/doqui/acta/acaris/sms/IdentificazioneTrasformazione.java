
package it.doqui.acta.acaris.sms;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per IdentificazioneTrasformazione complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="IdentificazioneTrasformazione"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="relazione" type="{archive.acaris.acta.doqui.it}RelationshipPropertiesType"/&gt;
 *         &lt;element name="dataUltimoAggiornamento" type="{common.acaris.acta.doqui.it}ChangeTokenType"/&gt;
 *         &lt;element name="failedSteps" type="{documentservice.acaris.acta.doqui.it}FailedStepsInfo" maxOccurs="unbounded" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "IdentificazioneTrasformazione", namespace = "documentservice.acaris.acta.doqui.it", propOrder = {
    "relazione",
    "dataUltimoAggiornamento",
    "failedSteps"
})
public class IdentificazioneTrasformazione {

    @XmlElement(namespace = "", required = true)
    protected RelationshipPropertiesType relazione;
    @XmlElement(namespace = "", required = true)
    protected ChangeTokenType dataUltimoAggiornamento;
    @XmlElement(namespace = "")
    protected List<FailedStepsInfo> failedSteps;

    /**
     * Recupera il valore della propriet� relazione.
     * 
     * @return
     *     possible object is
     *     {@link RelationshipPropertiesType }
     *     
     */
    public RelationshipPropertiesType getRelazione() {
        return relazione;
    }

    /**
     * Imposta il valore della propriet� relazione.
     * 
     * @param value
     *     allowed object is
     *     {@link RelationshipPropertiesType }
     *     
     */
    public void setRelazione(RelationshipPropertiesType value) {
        this.relazione = value;
    }

    /**
     * Recupera il valore della propriet� dataUltimoAggiornamento.
     * 
     * @return
     *     possible object is
     *     {@link ChangeTokenType }
     *     
     */
    public ChangeTokenType getDataUltimoAggiornamento() {
        return dataUltimoAggiornamento;
    }

    /**
     * Imposta il valore della propriet� dataUltimoAggiornamento.
     * 
     * @param value
     *     allowed object is
     *     {@link ChangeTokenType }
     *     
     */
    public void setDataUltimoAggiornamento(ChangeTokenType value) {
        this.dataUltimoAggiornamento = value;
    }

    /**
     * Gets the value of the failedSteps property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the failedSteps property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getFailedSteps().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link FailedStepsInfo }
     * 
     * 
     */
    public List<FailedStepsInfo> getFailedSteps() {
        if (failedSteps == null) {
            failedSteps = new ArrayList<FailedStepsInfo>();
        }
        return this.failedSteps;
    }

}
