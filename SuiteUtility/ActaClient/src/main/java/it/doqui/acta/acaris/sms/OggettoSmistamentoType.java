
package it.doqui.acta.acaris.sms;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per OggettoSmistamentoType complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="OggettoSmistamentoType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="classificazione" type="{common.acaris.acta.doqui.it}ObjectIdType"/&gt;
 *         &lt;element name="registrazione" type="{common.acaris.acta.doqui.it}IdRegistrazioneType"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "OggettoSmistamentoType", namespace = "sms.acaris.acta.doqui.it", propOrder = {
    "classificazione",
    "registrazione"
})
public class OggettoSmistamentoType {

    @XmlElement(namespace = "", required = true)
    protected ObjectIdType classificazione;
    @XmlElement(namespace = "", required = true)
    protected IdRegistrazioneType registrazione;

    /**
     * Recupera il valore della proprietà classificazione.
     * 
     * @return
     *     possible object is
     *     {@link ObjectIdType }
     *     
     */
    public ObjectIdType getClassificazione() {
        return classificazione;
    }

    /**
     * Imposta il valore della proprietà classificazione.
     * 
     * @param value
     *     allowed object is
     *     {@link ObjectIdType }
     *     
     */
    public void setClassificazione(ObjectIdType value) {
        this.classificazione = value;
    }

    /**
     * Recupera il valore della proprietà registrazione.
     * 
     * @return
     *     possible object is
     *     {@link IdRegistrazioneType }
     *     
     */
    public IdRegistrazioneType getRegistrazione() {
        return registrazione;
    }

    /**
     * Imposta il valore della proprietà registrazione.
     * 
     * @param value
     *     allowed object is
     *     {@link IdRegistrazioneType }
     *     
     */
    public void setRegistrazione(IdRegistrazioneType value) {
        this.registrazione = value;
    }

}
