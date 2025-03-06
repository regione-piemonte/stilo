
package it.doqui.acta.acaris.backoffice;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per CollocazioneUtente complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="CollocazioneUtente"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="aoo" type="{common.acaris.acta.doqui.it}DecodificaExtType"/&gt;
 *         &lt;element name="struttura" type="{common.acaris.acta.doqui.it}DecodificaExtType"/&gt;
 *         &lt;element name="nodo" type="{common.acaris.acta.doqui.it}DecodificaExtType"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CollocazioneUtente", namespace = "backoffice.acaris.acta.doqui.it", propOrder = {
    "aoo",
    "struttura",
    "nodo"
})
public class CollocazioneUtente {

    @XmlElement(required = true)
    protected DecodificaExtType aoo;
    @XmlElement(required = true)
    protected DecodificaExtType struttura;
    @XmlElement(required = true)
    protected DecodificaExtType nodo;

    /**
     * Recupera il valore della proprietà aoo.
     * 
     * @return
     *     possible object is
     *     {@link DecodificaExtType }
     *     
     */
    public DecodificaExtType getAoo() {
        return aoo;
    }

    /**
     * Imposta il valore della proprietà aoo.
     * 
     * @param value
     *     allowed object is
     *     {@link DecodificaExtType }
     *     
     */
    public void setAoo(DecodificaExtType value) {
        this.aoo = value;
    }

    /**
     * Recupera il valore della proprietà struttura.
     * 
     * @return
     *     possible object is
     *     {@link DecodificaExtType }
     *     
     */
    public DecodificaExtType getStruttura() {
        return struttura;
    }

    /**
     * Imposta il valore della proprietà struttura.
     * 
     * @param value
     *     allowed object is
     *     {@link DecodificaExtType }
     *     
     */
    public void setStruttura(DecodificaExtType value) {
        this.struttura = value;
    }

    /**
     * Recupera il valore della proprietà nodo.
     * 
     * @return
     *     possible object is
     *     {@link DecodificaExtType }
     *     
     */
    public DecodificaExtType getNodo() {
        return nodo;
    }

    /**
     * Imposta il valore della proprietà nodo.
     * 
     * @param value
     *     allowed object is
     *     {@link DecodificaExtType }
     *     
     */
    public void setNodo(DecodificaExtType value) {
        this.nodo = value;
    }

}
