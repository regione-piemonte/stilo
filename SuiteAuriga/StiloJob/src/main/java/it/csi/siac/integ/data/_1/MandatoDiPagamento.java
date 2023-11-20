/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */
package it.csi.siac.integ.data._1;

import java.math.BigDecimal;
import java.util.Date;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import org.w3._2001.xmlschema.Adapter1;


/**
 * <p>Classe Java per mandatoDiPagamento complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="mandatoDiPagamento"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{http://siac.csi.it/integ/data/1.0}entitaBase"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="annoEsercizio" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/&gt;
 *         &lt;element name="annoImpegno" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/&gt;
 *         &lt;element name="annoLiquidazione" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/&gt;
 *         &lt;element name="annoOrdinativo" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/&gt;
 *         &lt;element name="codiceSubOrdinativo" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="creditore" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="dataEmissione" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/&gt;
 *         &lt;element name="importoOrdinativo" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/&gt;
 *         &lt;element name="numeroArticolo" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/&gt;
 *         &lt;element name="numeroCapitolo" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/&gt;
 *         &lt;element name="numeroImpegno" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/&gt;
 *         &lt;element name="numeroLiquidazione" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/&gt;
 *         &lt;element name="numeroOrdinativo" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/&gt;
 *         &lt;element name="numeroSubImpegno" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/&gt;
 *         &lt;element name="provvedimento" type="{http://siac.csi.it/integ/data/1.0}provvedimento" minOccurs="0"/&gt;
 *         &lt;element name="quietanza" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/extension&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "mandatoDiPagamento", propOrder = {
    "annoEsercizio",
    "annoImpegno",
    "annoLiquidazione",
    "annoOrdinativo",
    "codiceSubOrdinativo",
    "creditore",
    "dataEmissione",
    "importoOrdinativo",
    "numeroArticolo",
    "numeroCapitolo",
    "numeroImpegno",
    "numeroLiquidazione",
    "numeroOrdinativo",
    "numeroSubImpegno",
    "provvedimento",
    "quietanza"
})
public class MandatoDiPagamento
    extends EntitaBase
{

    protected Integer annoEsercizio;
    protected Integer annoImpegno;
    protected Integer annoLiquidazione;
    protected Integer annoOrdinativo;
    protected String codiceSubOrdinativo;
    protected String creditore;
    @XmlElement(type = String.class)
    @XmlJavaTypeAdapter(Adapter1 .class)
    @XmlSchemaType(name = "dateTime")
    protected Date dataEmissione;
    protected BigDecimal importoOrdinativo;
    protected Integer numeroArticolo;
    protected Integer numeroCapitolo;
    protected Integer numeroImpegno;
    protected Integer numeroLiquidazione;
    protected Integer numeroOrdinativo;
    protected Integer numeroSubImpegno;
    protected Provvedimento provvedimento;
    protected String quietanza;

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
     * Recupera il valore della proprietà annoOrdinativo.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getAnnoOrdinativo() {
        return annoOrdinativo;
    }

    /**
     * Imposta il valore della proprietà annoOrdinativo.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setAnnoOrdinativo(Integer value) {
        this.annoOrdinativo = value;
    }

    /**
     * Recupera il valore della proprietà codiceSubOrdinativo.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCodiceSubOrdinativo() {
        return codiceSubOrdinativo;
    }

    /**
     * Imposta il valore della proprietà codiceSubOrdinativo.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCodiceSubOrdinativo(String value) {
        this.codiceSubOrdinativo = value;
    }

    /**
     * Recupera il valore della proprietà creditore.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCreditore() {
        return creditore;
    }

    /**
     * Imposta il valore della proprietà creditore.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCreditore(String value) {
        this.creditore = value;
    }

    /**
     * Recupera il valore della proprietà dataEmissione.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public Date getDataEmissione() {
        return dataEmissione;
    }

    /**
     * Imposta il valore della proprietà dataEmissione.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDataEmissione(Date value) {
        this.dataEmissione = value;
    }

    /**
     * Recupera il valore della proprietà importoOrdinativo.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getImportoOrdinativo() {
        return importoOrdinativo;
    }

    /**
     * Imposta il valore della proprietà importoOrdinativo.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setImportoOrdinativo(BigDecimal value) {
        this.importoOrdinativo = value;
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
     * Recupera il valore della proprietà numeroOrdinativo.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getNumeroOrdinativo() {
        return numeroOrdinativo;
    }

    /**
     * Imposta il valore della proprietà numeroOrdinativo.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setNumeroOrdinativo(Integer value) {
        this.numeroOrdinativo = value;
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

    /**
     * Recupera il valore della proprietà quietanza.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getQuietanza() {
        return quietanza;
    }

    /**
     * Imposta il valore della proprietà quietanza.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setQuietanza(String value) {
        this.quietanza = value;
    }

}
