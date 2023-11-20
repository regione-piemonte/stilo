/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */
package it.csi.siac.integ.data._1;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per liquidazioneAtti complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="liquidazioneAtti"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{http://siac.csi.it/integ/data/1.0}liquidazione"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="annoEsercizio" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/&gt;
 *         &lt;element name="atto" type="{http://siac.csi.it/integ/data/1.0}provvedimento" minOccurs="0"/&gt;
 *         &lt;element name="beneficiario" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="numeroArticolo" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/&gt;
 *         &lt;element name="numeroCapitolo" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/&gt;
 *         &lt;element name="numeroLiquidazionePrecedente" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/extension&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "liquidazioneAtti", propOrder = {
    "annoEsercizio",
    "atto",
    "beneficiario",
    "numeroArticolo",
    "numeroCapitolo",
    "numeroLiquidazionePrecedente"
})
public class LiquidazioneAtti
    extends Liquidazione
{

    protected Integer annoEsercizio;
    protected Provvedimento atto;
    protected String beneficiario;
    protected Integer numeroArticolo;
    protected Integer numeroCapitolo;
    protected Integer numeroLiquidazionePrecedente;

    /**
     * Recupera il valore della proprietà annoEsercizio.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getAnnoEsercizio() {
        return annoEsercizio;
    }

    /**
     * Imposta il valore della proprietà annoEsercizio.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setAnnoEsercizio(Integer value) {
        this.annoEsercizio = value;
    }

    /**
     * Recupera il valore della proprietà atto.
     * 
     * @return
     *     possible object is
     *     {@link Provvedimento }
     *     
     */
    public Provvedimento getAtto() {
        return atto;
    }

    /**
     * Imposta il valore della proprietà atto.
     * 
     * @param value
     *     allowed object is
     *     {@link Provvedimento }
     *     
     */
    public void setAtto(Provvedimento value) {
        this.atto = value;
    }

    /**
     * Recupera il valore della proprietà beneficiario.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBeneficiario() {
        return beneficiario;
    }

    /**
     * Imposta il valore della proprietà beneficiario.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBeneficiario(String value) {
        this.beneficiario = value;
    }

    /**
     * Recupera il valore della proprietà numeroArticolo.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getNumeroArticolo() {
        return numeroArticolo;
    }

    /**
     * Imposta il valore della proprietà numeroArticolo.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setNumeroArticolo(Integer value) {
        this.numeroArticolo = value;
    }

    /**
     * Recupera il valore della proprietà numeroCapitolo.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getNumeroCapitolo() {
        return numeroCapitolo;
    }

    /**
     * Imposta il valore della proprietà numeroCapitolo.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setNumeroCapitolo(Integer value) {
        this.numeroCapitolo = value;
    }

    /**
     * Recupera il valore della proprietà numeroLiquidazionePrecedente.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getNumeroLiquidazionePrecedente() {
        return numeroLiquidazionePrecedente;
    }

    /**
     * Imposta il valore della proprietà numeroLiquidazionePrecedente.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setNumeroLiquidazionePrecedente(Integer value) {
        this.numeroLiquidazionePrecedente = value;
    }

}
