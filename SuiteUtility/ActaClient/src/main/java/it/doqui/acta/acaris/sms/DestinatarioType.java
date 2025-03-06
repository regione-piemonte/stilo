
package it.doqui.acta.acaris.sms;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per DestinatarioType complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="DestinatarioType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="utente" type="{common.acaris.acta.doqui.it}IdUtenteType"/&gt;
 *         &lt;element name="nodoOrganizzativo" type="{common.acaris.acta.doqui.it}IdNodoType"/&gt;
 *         &lt;element name="nodoSmistamento" type="{sms.acaris.acta.doqui.it}IdNodoSmistamentoType"/&gt;
 *         &lt;element name="perConoscenza" type="{common.acaris.acta.doqui.it}boolean"/&gt;
 *         &lt;element name="idFascTempDest" type="{common.acaris.acta.doqui.it}ObjectIdType"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DestinatarioType", namespace = "sms.acaris.acta.doqui.it", propOrder = {
    "utente",
    "nodoOrganizzativo",
    "nodoSmistamento",
    "perConoscenza",
    "idFascTempDest"
})
@XmlSeeAlso({
    DestinatarioConNoteType.class
})
public class DestinatarioType {

    @XmlElement(namespace = "", required = true)
    protected IdUtenteType utente;
    @XmlElement(namespace = "", required = true)
    protected IdNodoType nodoOrganizzativo;
    @XmlElement(namespace = "", required = true)
    protected IdNodoSmistamentoType nodoSmistamento;
    @XmlElement(namespace = "")
    protected boolean perConoscenza;
    @XmlElement(namespace = "", required = true)
    protected ObjectIdType idFascTempDest;

    /**
     * Recupera il valore della proprietà utente.
     * 
     * @return
     *     possible object is
     *     {@link IdUtenteType }
     *     
     */
    public IdUtenteType getUtente() {
        return utente;
    }

    /**
     * Imposta il valore della proprietà utente.
     * 
     * @param value
     *     allowed object is
     *     {@link IdUtenteType }
     *     
     */
    public void setUtente(IdUtenteType value) {
        this.utente = value;
    }

    /**
     * Recupera il valore della proprietà nodoOrganizzativo.
     * 
     * @return
     *     possible object is
     *     {@link IdNodoType }
     *     
     */
    public IdNodoType getNodoOrganizzativo() {
        return nodoOrganizzativo;
    }

    /**
     * Imposta il valore della proprietà nodoOrganizzativo.
     * 
     * @param value
     *     allowed object is
     *     {@link IdNodoType }
     *     
     */
    public void setNodoOrganizzativo(IdNodoType value) {
        this.nodoOrganizzativo = value;
    }

    /**
     * Recupera il valore della proprietà nodoSmistamento.
     * 
     * @return
     *     possible object is
     *     {@link IdNodoSmistamentoType }
     *     
     */
    public IdNodoSmistamentoType getNodoSmistamento() {
        return nodoSmistamento;
    }

    /**
     * Imposta il valore della proprietà nodoSmistamento.
     * 
     * @param value
     *     allowed object is
     *     {@link IdNodoSmistamentoType }
     *     
     */
    public void setNodoSmistamento(IdNodoSmistamentoType value) {
        this.nodoSmistamento = value;
    }

    /**
     * Recupera il valore della proprietà perConoscenza.
     * 
     */
    public boolean isPerConoscenza() {
        return perConoscenza;
    }

    /**
     * Imposta il valore della proprietà perConoscenza.
     * 
     */
    public void setPerConoscenza(boolean value) {
        this.perConoscenza = value;
    }

    /**
     * Recupera il valore della proprietà idFascTempDest.
     * 
     * @return
     *     possible object is
     *     {@link ObjectIdType }
     *     
     */
    public ObjectIdType getIdFascTempDest() {
        return idFascTempDest;
    }

    /**
     * Imposta il valore della proprietà idFascTempDest.
     * 
     * @param value
     *     allowed object is
     *     {@link ObjectIdType }
     *     
     */
    public void setIdFascTempDest(ObjectIdType value) {
        this.idFascTempDest = value;
    }

}
