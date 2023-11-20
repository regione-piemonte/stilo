/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */
package it.csi.siac.integ.data._1;

import java.math.BigDecimal;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per movimentoGestione complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="movimentoGestione"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{http://siac.csi.it/integ/data/1.0}entitaCodificataBase"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="cig" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="codiceSoggetto" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="cup" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="importo" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/&gt;
 *         &lt;element name="numeroArticolo" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/&gt;
 *         &lt;element name="numeroCapitolo" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/&gt;
 *         &lt;element name="numeroUEB" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/&gt;
 *         &lt;element name="parereFinanziario" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/&gt;
 *         &lt;element name="pdc" type="{http://siac.csi.it/integ/data/1.0}pianoDeiContiFinanziario" minOccurs="0"/&gt;
 *         &lt;element name="provvedimento" type="{http://siac.csi.it/integ/data/1.0}provvedimento" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/extension&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "movimentoGestione", propOrder = {
    "cig",
    "codiceSoggetto",
    "cup",
    "importo",
    "numeroArticolo",
    "numeroCapitolo",
    "numeroUEB",
    "parereFinanziario",
    "pdc",
    "provvedimento"
})
@XmlSeeAlso({
    Impegno.class,
    SubImpegno.class,
    Accertamento.class,
    SubAccertamento.class
})
public class MovimentoGestione
    extends EntitaCodificataBase
{

    protected String cig;
    protected String codiceSoggetto;
    protected String cup;
    protected BigDecimal importo;
    protected Integer numeroArticolo;
    protected Integer numeroCapitolo;
    protected Integer numeroUEB;
    protected Boolean parereFinanziario;
    protected PianoDeiContiFinanziario pdc;
    protected Provvedimento provvedimento;

    /**
     * Recupera il valore della proprietà cig.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCig() {
        return cig;
    }

    /**
     * Imposta il valore della proprietà cig.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCig(String value) {
        this.cig = value;
    }

    /**
     * Recupera il valore della proprietà codiceSoggetto.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCodiceSoggetto() {
        return codiceSoggetto;
    }

    /**
     * Imposta il valore della proprietà codiceSoggetto.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCodiceSoggetto(String value) {
        this.codiceSoggetto = value;
    }

    /**
     * Recupera il valore della proprietà cup.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCup() {
        return cup;
    }

    /**
     * Imposta il valore della proprietà cup.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCup(String value) {
        this.cup = value;
    }

    /**
     * Recupera il valore della proprietà importo.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getImporto() {
        return importo;
    }

    /**
     * Imposta il valore della proprietà importo.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setImporto(BigDecimal value) {
        this.importo = value;
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
     * Recupera il valore della proprietà numeroUEB.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getNumeroUEB() {
        return numeroUEB;
    }

    /**
     * Imposta il valore della proprietà numeroUEB.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setNumeroUEB(Integer value) {
        this.numeroUEB = value;
    }

    /**
     * Recupera il valore della proprietà parereFinanziario.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isParereFinanziario() {
        return parereFinanziario;
    }

    /**
     * Imposta il valore della proprietà parereFinanziario.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setParereFinanziario(Boolean value) {
        this.parereFinanziario = value;
    }

    /**
     * Recupera il valore della proprietà pdc.
     * 
     * @return
     *     possible object is
     *     {@link PianoDeiContiFinanziario }
     *     
     */
    public PianoDeiContiFinanziario getPdc() {
        return pdc;
    }

    /**
     * Imposta il valore della proprietà pdc.
     * 
     * @param value
     *     allowed object is
     *     {@link PianoDeiContiFinanziario }
     *     
     */
    public void setPdc(PianoDeiContiFinanziario value) {
        this.pdc = value;
    }

    /**
     * Recupera il valore della proprietà provvedimento.
     * 
     * @return
     *     possible object is
     *     {@link Provvedimento }
     *     
     */
    public Provvedimento getProvvedimento() {
        return provvedimento;
    }

    /**
     * Imposta il valore della proprietà provvedimento.
     * 
     * @param value
     *     allowed object is
     *     {@link Provvedimento }
     *     
     */
    public void setProvvedimento(Provvedimento value) {
        this.provvedimento = value;
    }

}
