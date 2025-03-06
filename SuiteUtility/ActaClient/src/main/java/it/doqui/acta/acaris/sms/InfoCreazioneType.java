
package it.doqui.acta.acaris.sms;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per InfoCreazioneType complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="InfoCreazioneType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="tipoSmistamento" type="{sms.acaris.acta.doqui.it}IdTipoSmistamentoType"/&gt;
 *         &lt;element name="visibileATutti" type="{common.acaris.acta.doqui.it}boolean"/&gt;
 *         &lt;element name="note" type="{common.acaris.acta.doqui.it}string"/&gt;
 *         &lt;element name="modificaDoc" type="{common.acaris.acta.doqui.it}boolean"/&gt;
 *         &lt;element name="modificaConVersionamento" type="{common.acaris.acta.doqui.it}boolean"/&gt;
 *         &lt;element name="smistamentoPadre" type="{common.acaris.acta.doqui.it}IdSmistamentoType"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "InfoCreazioneType", namespace = "sms.acaris.acta.doqui.it", propOrder = {
    "tipoSmistamento",
    "visibileATutti",
    "note",
    "modificaDoc",
    "modificaConVersionamento",
    "smistamentoPadre"
})
public class InfoCreazioneType {

    @XmlElement(namespace = "", required = true)
    protected IdTipoSmistamentoType tipoSmistamento;
    @XmlElement(namespace = "")
    protected boolean visibileATutti;
    @XmlElement(namespace = "", required = true)
    protected String note;
    @XmlElement(namespace = "")
    protected boolean modificaDoc;
    @XmlElement(namespace = "")
    protected boolean modificaConVersionamento;
    @XmlElement(namespace = "", required = true)
    protected IdSmistamentoType smistamentoPadre;

    /**
     * Recupera il valore della proprietà tipoSmistamento.
     * 
     * @return
     *     possible object is
     *     {@link IdTipoSmistamentoType }
     *     
     */
    public IdTipoSmistamentoType getTipoSmistamento() {
        return tipoSmistamento;
    }

    /**
     * Imposta il valore della proprietà tipoSmistamento.
     * 
     * @param value
     *     allowed object is
     *     {@link IdTipoSmistamentoType }
     *     
     */
    public void setTipoSmistamento(IdTipoSmistamentoType value) {
        this.tipoSmistamento = value;
    }

    /**
     * Recupera il valore della proprietà visibileATutti.
     * 
     */
    public boolean isVisibileATutti() {
        return visibileATutti;
    }

    /**
     * Imposta il valore della proprietà visibileATutti.
     * 
     */
    public void setVisibileATutti(boolean value) {
        this.visibileATutti = value;
    }

    /**
     * Recupera il valore della proprietà note.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNote() {
        return note;
    }

    /**
     * Imposta il valore della proprietà note.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNote(String value) {
        this.note = value;
    }

    /**
     * Recupera il valore della proprietà modificaDoc.
     * 
     */
    public boolean isModificaDoc() {
        return modificaDoc;
    }

    /**
     * Imposta il valore della proprietà modificaDoc.
     * 
     */
    public void setModificaDoc(boolean value) {
        this.modificaDoc = value;
    }

    /**
     * Recupera il valore della proprietà modificaConVersionamento.
     * 
     */
    public boolean isModificaConVersionamento() {
        return modificaConVersionamento;
    }

    /**
     * Imposta il valore della proprietà modificaConVersionamento.
     * 
     */
    public void setModificaConVersionamento(boolean value) {
        this.modificaConVersionamento = value;
    }

    /**
     * Recupera il valore della proprietà smistamentoPadre.
     * 
     * @return
     *     possible object is
     *     {@link IdSmistamentoType }
     *     
     */
    public IdSmistamentoType getSmistamentoPadre() {
        return smistamentoPadre;
    }

    /**
     * Imposta il valore della proprietà smistamentoPadre.
     * 
     * @param value
     *     allowed object is
     *     {@link IdSmistamentoType }
     *     
     */
    public void setSmistamentoPadre(IdSmistamentoType value) {
        this.smistamentoPadre = value;
    }

}
