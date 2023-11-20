/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */
package it.csi.siac.integ.data._1;

import java.math.BigDecimal;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per liquidazione complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="liquidazione"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{http://siac.csi.it/integ/data/1.0}entitaBase"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="annoImpegno" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/&gt;
 *         &lt;element name="annoLiquidazione" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/&gt;
 *         &lt;element name="importoLiquidazione" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/&gt;
 *         &lt;element name="numeroImpegno" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/&gt;
 *         &lt;element name="numeroLiquidazione" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/&gt;
 *         &lt;element name="numeroSubImpegno" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/extension&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "liquidazione", propOrder = {
    "annoImpegno",
    "annoLiquidazione",
    "importoLiquidazione",
    "numeroImpegno",
    "numeroLiquidazione",
    "numeroSubImpegno"
})
@XmlSeeAlso({
    LiquidazioneAtti.class
})
public class Liquidazione
    extends EntitaBase
{

    protected Integer annoImpegno;
    protected Integer annoLiquidazione;
    protected BigDecimal importoLiquidazione;
    protected Integer numeroImpegno;
    protected Integer numeroLiquidazione;
    protected Integer numeroSubImpegno;

    /**
     * Recupera il valore della proprietà annoImpegno.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getAnnoImpegno() {
        return annoImpegno;
    }

    /**
     * Imposta il valore della proprietà annoImpegno.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setAnnoImpegno(Integer value) {
        this.annoImpegno = value;
    }

    /**
     * Recupera il valore della proprietà annoLiquidazione.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getAnnoLiquidazione() {
        return annoLiquidazione;
    }

    /**
     * Imposta il valore della proprietà annoLiquidazione.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setAnnoLiquidazione(Integer value) {
        this.annoLiquidazione = value;
    }

    /**
     * Recupera il valore della proprietà importoLiquidazione.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getImportoLiquidazione() {
        return importoLiquidazione;
    }

    /**
     * Imposta il valore della proprietà importoLiquidazione.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setImportoLiquidazione(BigDecimal value) {
        this.importoLiquidazione = value;
    }

    /**
     * Recupera il valore della proprietà numeroImpegno.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getNumeroImpegno() {
        return numeroImpegno;
    }

    /**
     * Imposta il valore della proprietà numeroImpegno.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setNumeroImpegno(Integer value) {
        this.numeroImpegno = value;
    }

    /**
     * Recupera il valore della proprietà numeroLiquidazione.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getNumeroLiquidazione() {
        return numeroLiquidazione;
    }

    /**
     * Imposta il valore della proprietà numeroLiquidazione.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setNumeroLiquidazione(Integer value) {
        this.numeroLiquidazione = value;
    }

    /**
     * Recupera il valore della proprietà numeroSubImpegno.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getNumeroSubImpegno() {
        return numeroSubImpegno;
    }

    /**
     * Imposta il valore della proprietà numeroSubImpegno.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setNumeroSubImpegno(Integer value) {
        this.numeroSubImpegno = value;
    }

}
