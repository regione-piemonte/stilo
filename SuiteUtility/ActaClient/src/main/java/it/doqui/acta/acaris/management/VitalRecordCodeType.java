
package it.doqui.acta.acaris.management;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per VitalRecordCodeType complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="VitalRecordCodeType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="idVitalRecordCode" type="{common.acaris.acta.doqui.it}IdVitalRecordCodeType"/&gt;
 *         &lt;element name="descrizione" type="{common.acaris.acta.doqui.it}string"/&gt;
 *         &lt;element name="tempoDiVitalita" type="{common.acaris.acta.doqui.it}integer"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "VitalRecordCodeType", namespace = "management.acaris.acta.doqui.it", propOrder = {
    "idVitalRecordCode",
    "descrizione",
    "tempoDiVitalita"
})
public class VitalRecordCodeType {

    @XmlElement(required = true)
    protected IdVitalRecordCodeType idVitalRecordCode;
    @XmlElement(required = true)
    protected String descrizione;
    protected int tempoDiVitalita;

    /**
     * Recupera il valore della proprietà idVitalRecordCode.
     * 
     * @return
     *     possible object is
     *     {@link IdVitalRecordCodeType }
     *     
     */
    public IdVitalRecordCodeType getIdVitalRecordCode() {
        return idVitalRecordCode;
    }

    /**
     * Imposta il valore della proprietà idVitalRecordCode.
     * 
     * @param value
     *     allowed object is
     *     {@link IdVitalRecordCodeType }
     *     
     */
    public void setIdVitalRecordCode(IdVitalRecordCodeType value) {
        this.idVitalRecordCode = value;
    }

    /**
     * Recupera il valore della proprietà descrizione.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDescrizione() {
        return descrizione;
    }

    /**
     * Imposta il valore della proprietà descrizione.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDescrizione(String value) {
        this.descrizione = value;
    }

    /**
     * Recupera il valore della proprietà tempoDiVitalita.
     * 
     */
    public int getTempoDiVitalita() {
        return tempoDiVitalita;
    }

    /**
     * Imposta il valore della proprietà tempoDiVitalita.
     * 
     */
    public void setTempoDiVitalita(int value) {
        this.tempoDiVitalita = value;
    }

}
