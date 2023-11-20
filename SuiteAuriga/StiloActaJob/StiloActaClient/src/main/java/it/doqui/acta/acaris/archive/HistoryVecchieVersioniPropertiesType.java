/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */
package it.doqui.acta.acaris.archive;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Classe Java per HistoryVecchieVersioniPropertiesType complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="HistoryVecchieVersioniPropertiesType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{archive.acaris.acta.doqui.it}RelationshipPropertiesType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="numeroVersione" type="{archive.acaris.acta.doqui.it}NumeroVersioneType"/&gt;
 *         &lt;element name="dataVersione" type="{archive.acaris.acta.doqui.it}DataVersioneType"/&gt;
 *         &lt;element name="motivazioneVersione" type="{archive.acaris.acta.doqui.it}MotivazioneVersioneType"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/extension&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "HistoryVecchieVersioniPropertiesType", propOrder = {
    "numeroVersione",
    "dataVersione",
    "motivazioneVersione"
})
public class HistoryVecchieVersioniPropertiesType
    extends RelationshipPropertiesType
{

    protected int numeroVersione;
    @XmlElement(required = true)
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar dataVersione;
    @XmlElement(required = true)
    protected String motivazioneVersione;

    /**
     * Recupera il valore della proprietà numeroVersione.
     * 
     */
    public int getNumeroVersione() {
        return numeroVersione;
    }

    /**
     * Imposta il valore della proprietà numeroVersione.
     * 
     */
    public void setNumeroVersione(int value) {
        this.numeroVersione = value;
    }

    /**
     * Recupera il valore della proprietà dataVersione.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDataVersione() {
        return dataVersione;
    }

    /**
     * Imposta il valore della proprietà dataVersione.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDataVersione(XMLGregorianCalendar value) {
        this.dataVersione = value;
    }

    /**
     * Recupera il valore della proprietà motivazioneVersione.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMotivazioneVersione() {
        return motivazioneVersione;
    }

    /**
     * Imposta il valore della proprietà motivazioneVersione.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMotivazioneVersione(String value) {
        this.motivazioneVersione = value;
    }

}
