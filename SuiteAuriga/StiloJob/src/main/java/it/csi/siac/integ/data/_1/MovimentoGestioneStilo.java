/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */
package it.csi.siac.integ.data._1;

import java.math.BigDecimal;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per movimentoGestioneStilo complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="movimentoGestioneStilo"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="annoCompetenza" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/&gt;
 *         &lt;element name="annoCompetenzaModifica" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/&gt;
 *         &lt;element name="annoCompetenzaSubAccertamento" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/&gt;
 *         &lt;element name="annoCompetenzaSubImpegno" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/&gt;
 *         &lt;element name="capitolo" type="{http://siac.csi.it/integ/data/1.0}capitolo" minOccurs="0"/&gt;
 *         &lt;element name="categoria" type="{http://siac.csi.it/integ/data/1.0}categoria" minOccurs="0"/&gt;
 *         &lt;element name="cig" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="classeSoggetto" type="{http://siac.csi.it/integ/data/1.0}classeSoggettoStilo" minOccurs="0"/&gt;
 *         &lt;element name="classeSoggettoIniziale" type="{http://siac.csi.it/integ/data/1.0}classeSoggettoStilo" minOccurs="0"/&gt;
 *         &lt;element name="classseSoggettoFinale" type="{http://siac.csi.it/integ/data/1.0}classeSoggettoStilo" minOccurs="0"/&gt;
 *         &lt;element name="codUE" type="{http://siac.csi.it/integ/data/1.0}classificatoreGenerico" minOccurs="0"/&gt;
 *         &lt;element name="cofog" type="{http://siac.csi.it/integ/data/1.0}classificatoreGenerico" minOccurs="0"/&gt;
 *         &lt;element name="cup" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="descrizioneAccertamento" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="descrizioneImpegno" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="descrizioneModifica" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="descrizioneSubAccertamento" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="descrizioneSubImpegno" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="gsa" type="{http://siac.csi.it/integ/data/1.0}classificatoreGenerico" minOccurs="0"/&gt;
 *         &lt;element name="importoAttualeAccertamento" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/&gt;
 *         &lt;element name="importoAttualeImpegno" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/&gt;
 *         &lt;element name="importoAttualeSubAccertamento" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/&gt;
 *         &lt;element name="importoAttualeSubImpegno" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/&gt;
 *         &lt;element name="importoInizialeAccertamento" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/&gt;
 *         &lt;element name="importoInizialeImpegno" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/&gt;
 *         &lt;element name="importoInizialeSubAccertamento" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/&gt;
 *         &lt;element name="importoInizialeSubImpegno" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/&gt;
 *         &lt;element name="importoModifica" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/&gt;
 *         &lt;element name="macroaggregato" type="{http://siac.csi.it/integ/data/1.0}macroaggregato" minOccurs="0"/&gt;
 *         &lt;element name="missione" type="{http://siac.csi.it/integ/data/1.0}missione" minOccurs="0"/&gt;
 *         &lt;element name="motivoAssenzaCig" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="naturaRicorrente" type="{http://siac.csi.it/integ/data/1.0}classificatoreGenerico" minOccurs="0"/&gt;
 *         &lt;element name="numeroAccertamento" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/&gt;
 *         &lt;element name="numeroImpegno" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/&gt;
 *         &lt;element name="numeroModifica" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/&gt;
 *         &lt;element name="numeroSubAccertamento" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/&gt;
 *         &lt;element name="numeroSubImpegno" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/&gt;
 *         &lt;element name="pianoDeiContiFinanziario" type="{http://siac.csi.it/integ/data/1.0}pianoDeiContiFinanziario" minOccurs="0"/&gt;
 *         &lt;element name="prenotazione" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="prenotazioneLiquidabile" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="progetto" type="{http://siac.csi.it/integ/data/1.0}progettoStilo" minOccurs="0"/&gt;
 *         &lt;element name="programma" type="{http://siac.csi.it/integ/data/1.0}programma" minOccurs="0"/&gt;
 *         &lt;element name="soggetto" type="{http://siac.csi.it/integ/data/1.0}soggettoStilo" minOccurs="0"/&gt;
 *         &lt;element name="soggettoFinale" type="{http://siac.csi.it/integ/data/1.0}soggettoStilo" minOccurs="0"/&gt;
 *         &lt;element name="soggettoIniziale" type="{http://siac.csi.it/integ/data/1.0}soggettoStilo" minOccurs="0"/&gt;
 *         &lt;element name="tipoDebitoSiope" type="{http://siac.csi.it/integ/data/1.0}tipoDebitoSiopeStilo" minOccurs="0"/&gt;
 *         &lt;element name="tipoFinanziamento" type="{http://siac.csi.it/integ/data/1.0}tipoFinanziamento" minOccurs="0"/&gt;
 *         &lt;element name="tipoMovimentoGestione" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="tipologia" type="{http://siac.csi.it/integ/data/1.0}tipologia" minOccurs="0"/&gt;
 *         &lt;element name="titolo" type="{http://siac.csi.it/integ/data/1.0}titolo" minOccurs="0"/&gt;
 *         &lt;element name="vincoli" type="{http://siac.csi.it/integ/data/1.0}vincoliStilo" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "movimentoGestioneStilo", propOrder = {
    "annoCompetenza",
    "annoCompetenzaModifica",
    "annoCompetenzaSubAccertamento",
    "annoCompetenzaSubImpegno",
    "capitolo",
    "categoria",
    "cig",
    "classeSoggetto",
    "classeSoggettoIniziale",
    "classseSoggettoFinale",
    "codUE",
    "cofog",
    "cup",
    "descrizioneAccertamento",
    "descrizioneImpegno",
    "descrizioneModifica",
    "descrizioneSubAccertamento",
    "descrizioneSubImpegno",
    "gsa",
    "importoAttualeAccertamento",
    "importoAttualeImpegno",
    "importoAttualeSubAccertamento",
    "importoAttualeSubImpegno",
    "importoInizialeAccertamento",
    "importoInizialeImpegno",
    "importoInizialeSubAccertamento",
    "importoInizialeSubImpegno",
    "importoModifica",
    "macroaggregato",
    "missione",
    "motivoAssenzaCig",
    "naturaRicorrente",
    "numeroAccertamento",
    "numeroImpegno",
    "numeroModifica",
    "numeroSubAccertamento",
    "numeroSubImpegno",
    "pianoDeiContiFinanziario",
    "prenotazione",
    "prenotazioneLiquidabile",
    "progetto",
    "programma",
    "soggetto",
    "soggettoFinale",
    "soggettoIniziale",
    "tipoDebitoSiope",
    "tipoFinanziamento",
    "tipoMovimentoGestione",
    "tipologia",
    "titolo",
    "vincoli"
})
public class MovimentoGestioneStilo {

    protected Integer annoCompetenza;
    protected Integer annoCompetenzaModifica;
    protected Integer annoCompetenzaSubAccertamento;
    protected Integer annoCompetenzaSubImpegno;
    protected Capitolo capitolo;
    protected Categoria categoria;
    protected String cig;
    protected ClasseSoggettoStilo classeSoggetto;
    protected ClasseSoggettoStilo classeSoggettoIniziale;
    protected ClasseSoggettoStilo classseSoggettoFinale;
    protected ClassificatoreGenerico codUE;
    protected ClassificatoreGenerico cofog;
    protected String cup;
    protected String descrizioneAccertamento;
    protected String descrizioneImpegno;
    protected String descrizioneModifica;
    protected String descrizioneSubAccertamento;
    protected String descrizioneSubImpegno;
    protected ClassificatoreGenerico gsa;
    protected BigDecimal importoAttualeAccertamento;
    protected BigDecimal importoAttualeImpegno;
    protected BigDecimal importoAttualeSubAccertamento;
    protected BigDecimal importoAttualeSubImpegno;
    protected BigDecimal importoInizialeAccertamento;
    protected BigDecimal importoInizialeImpegno;
    protected BigDecimal importoInizialeSubAccertamento;
    protected BigDecimal importoInizialeSubImpegno;
    protected BigDecimal importoModifica;
    protected Macroaggregato macroaggregato;
    protected Missione missione;
    protected String motivoAssenzaCig;
    protected ClassificatoreGenerico naturaRicorrente;
    protected BigDecimal numeroAccertamento;
    protected BigDecimal numeroImpegno;
    protected Integer numeroModifica;
    protected BigDecimal numeroSubAccertamento;
    protected BigDecimal numeroSubImpegno;
    protected PianoDeiContiFinanziario pianoDeiContiFinanziario;
    protected String prenotazione;
    protected String prenotazioneLiquidabile;
    protected ProgettoStilo progetto;
    protected Programma programma;
    protected SoggettoStilo soggetto;
    protected SoggettoStilo soggettoFinale;
    protected SoggettoStilo soggettoIniziale;
    protected TipoDebitoSiopeStilo tipoDebitoSiope;
    protected TipoFinanziamento tipoFinanziamento;
    protected String tipoMovimentoGestione;
    protected Tipologia tipologia;
    protected Titolo titolo;
    protected VincoliStilo vincoli;

    /**
     * Recupera il valore della proprietà annoCompetenza.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getAnnoCompetenza() {
        return annoCompetenza;
    }

    /**
     * Imposta il valore della proprietà annoCompetenza.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setAnnoCompetenza(Integer value) {
        this.annoCompetenza = value;
    }

    /**
     * Recupera il valore della proprietà annoCompetenzaModifica.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getAnnoCompetenzaModifica() {
        return annoCompetenzaModifica;
    }

    /**
     * Imposta il valore della proprietà annoCompetenzaModifica.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setAnnoCompetenzaModifica(Integer value) {
        this.annoCompetenzaModifica = value;
    }

    /**
     * Recupera il valore della proprietà annoCompetenzaSubAccertamento.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getAnnoCompetenzaSubAccertamento() {
        return annoCompetenzaSubAccertamento;
    }

    /**
     * Imposta il valore della proprietà annoCompetenzaSubAccertamento.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setAnnoCompetenzaSubAccertamento(Integer value) {
        this.annoCompetenzaSubAccertamento = value;
    }

    /**
     * Recupera il valore della proprietà annoCompetenzaSubImpegno.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getAnnoCompetenzaSubImpegno() {
        return annoCompetenzaSubImpegno;
    }

    /**
     * Imposta il valore della proprietà annoCompetenzaSubImpegno.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setAnnoCompetenzaSubImpegno(Integer value) {
        this.annoCompetenzaSubImpegno = value;
    }

    /**
     * Recupera il valore della proprietà capitolo.
     * 
     * @return
     *     possible object is
     *     {@link Capitolo }
     *     
     */
    public Capitolo getCapitolo() {
        return capitolo;
    }

    /**
     * Imposta il valore della proprietà capitolo.
     * 
     * @param value
     *     allowed object is
     *     {@link Capitolo }
     *     
     */
    public void setCapitolo(Capitolo value) {
        this.capitolo = value;
    }

    /**
     * Recupera il valore della proprietà categoria.
     * 
     * @return
     *     possible object is
     *     {@link Categoria }
     *     
     */
    public Categoria getCategoria() {
        return categoria;
    }

    /**
     * Imposta il valore della proprietà categoria.
     * 
     * @param value
     *     allowed object is
     *     {@link Categoria }
     *     
     */
    public void setCategoria(Categoria value) {
        this.categoria = value;
    }

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
     * Recupera il valore della proprietà classeSoggetto.
     * 
     * @return
     *     possible object is
     *     {@link ClasseSoggettoStilo }
     *     
     */
    public ClasseSoggettoStilo getClasseSoggetto() {
        return classeSoggetto;
    }

    /**
     * Imposta il valore della proprietà classeSoggetto.
     * 
     * @param value
     *     allowed object is
     *     {@link ClasseSoggettoStilo }
     *     
     */
    public void setClasseSoggetto(ClasseSoggettoStilo value) {
        this.classeSoggetto = value;
    }

    /**
     * Recupera il valore della proprietà classeSoggettoIniziale.
     * 
     * @return
     *     possible object is
     *     {@link ClasseSoggettoStilo }
     *     
     */
    public ClasseSoggettoStilo getClasseSoggettoIniziale() {
        return classeSoggettoIniziale;
    }

    /**
     * Imposta il valore della proprietà classeSoggettoIniziale.
     * 
     * @param value
     *     allowed object is
     *     {@link ClasseSoggettoStilo }
     *     
     */
    public void setClasseSoggettoIniziale(ClasseSoggettoStilo value) {
        this.classeSoggettoIniziale = value;
    }

    /**
     * Recupera il valore della proprietà classseSoggettoFinale.
     * 
     * @return
     *     possible object is
     *     {@link ClasseSoggettoStilo }
     *     
     */
    public ClasseSoggettoStilo getClassseSoggettoFinale() {
        return classseSoggettoFinale;
    }

    /**
     * Imposta il valore della proprietà classseSoggettoFinale.
     * 
     * @param value
     *     allowed object is
     *     {@link ClasseSoggettoStilo }
     *     
     */
    public void setClassseSoggettoFinale(ClasseSoggettoStilo value) {
        this.classseSoggettoFinale = value;
    }

    /**
     * Recupera il valore della proprietà codUE.
     * 
     * @return
     *     possible object is
     *     {@link ClassificatoreGenerico }
     *     
     */
    public ClassificatoreGenerico getCodUE() {
        return codUE;
    }

    /**
     * Imposta il valore della proprietà codUE.
     * 
     * @param value
     *     allowed object is
     *     {@link ClassificatoreGenerico }
     *     
     */
    public void setCodUE(ClassificatoreGenerico value) {
        this.codUE = value;
    }

    /**
     * Recupera il valore della proprietà cofog.
     * 
     * @return
     *     possible object is
     *     {@link ClassificatoreGenerico }
     *     
     */
    public ClassificatoreGenerico getCofog() {
        return cofog;
    }

    /**
     * Imposta il valore della proprietà cofog.
     * 
     * @param value
     *     allowed object is
     *     {@link ClassificatoreGenerico }
     *     
     */
    public void setCofog(ClassificatoreGenerico value) {
        this.cofog = value;
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
     * Recupera il valore della proprietà descrizioneAccertamento.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDescrizioneAccertamento() {
        return descrizioneAccertamento;
    }

    /**
     * Imposta il valore della proprietà descrizioneAccertamento.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDescrizioneAccertamento(String value) {
        this.descrizioneAccertamento = value;
    }

    /**
     * Recupera il valore della proprietà descrizioneImpegno.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDescrizioneImpegno() {
        return descrizioneImpegno;
    }

    /**
     * Imposta il valore della proprietà descrizioneImpegno.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDescrizioneImpegno(String value) {
        this.descrizioneImpegno = value;
    }

    /**
     * Recupera il valore della proprietà descrizioneModifica.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDescrizioneModifica() {
        return descrizioneModifica;
    }

    /**
     * Imposta il valore della proprietà descrizioneModifica.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDescrizioneModifica(String value) {
        this.descrizioneModifica = value;
    }

    /**
     * Recupera il valore della proprietà descrizioneSubAccertamento.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDescrizioneSubAccertamento() {
        return descrizioneSubAccertamento;
    }

    /**
     * Imposta il valore della proprietà descrizioneSubAccertamento.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDescrizioneSubAccertamento(String value) {
        this.descrizioneSubAccertamento = value;
    }

    /**
     * Recupera il valore della proprietà descrizioneSubImpegno.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDescrizioneSubImpegno() {
        return descrizioneSubImpegno;
    }

    /**
     * Imposta il valore della proprietà descrizioneSubImpegno.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDescrizioneSubImpegno(String value) {
        this.descrizioneSubImpegno = value;
    }

    /**
     * Recupera il valore della proprietà gsa.
     * 
     * @return
     *     possible object is
     *     {@link ClassificatoreGenerico }
     *     
     */
    public ClassificatoreGenerico getGsa() {
        return gsa;
    }

    /**
     * Imposta il valore della proprietà gsa.
     * 
     * @param value
     *     allowed object is
     *     {@link ClassificatoreGenerico }
     *     
     */
    public void setGsa(ClassificatoreGenerico value) {
        this.gsa = value;
    }

    /**
     * Recupera il valore della proprietà importoAttualeAccertamento.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getImportoAttualeAccertamento() {
        return importoAttualeAccertamento;
    }

    /**
     * Imposta il valore della proprietà importoAttualeAccertamento.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setImportoAttualeAccertamento(BigDecimal value) {
        this.importoAttualeAccertamento = value;
    }

    /**
     * Recupera il valore della proprietà importoAttualeImpegno.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getImportoAttualeImpegno() {
        return importoAttualeImpegno;
    }

    /**
     * Imposta il valore della proprietà importoAttualeImpegno.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setImportoAttualeImpegno(BigDecimal value) {
        this.importoAttualeImpegno = value;
    }

    /**
     * Recupera il valore della proprietà importoAttualeSubAccertamento.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getImportoAttualeSubAccertamento() {
        return importoAttualeSubAccertamento;
    }

    /**
     * Imposta il valore della proprietà importoAttualeSubAccertamento.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setImportoAttualeSubAccertamento(BigDecimal value) {
        this.importoAttualeSubAccertamento = value;
    }

    /**
     * Recupera il valore della proprietà importoAttualeSubImpegno.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getImportoAttualeSubImpegno() {
        return importoAttualeSubImpegno;
    }

    /**
     * Imposta il valore della proprietà importoAttualeSubImpegno.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setImportoAttualeSubImpegno(BigDecimal value) {
        this.importoAttualeSubImpegno = value;
    }

    /**
     * Recupera il valore della proprietà importoInizialeAccertamento.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getImportoInizialeAccertamento() {
        return importoInizialeAccertamento;
    }

    /**
     * Imposta il valore della proprietà importoInizialeAccertamento.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setImportoInizialeAccertamento(BigDecimal value) {
        this.importoInizialeAccertamento = value;
    }

    /**
     * Recupera il valore della proprietà importoInizialeImpegno.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getImportoInizialeImpegno() {
        return importoInizialeImpegno;
    }

    /**
     * Imposta il valore della proprietà importoInizialeImpegno.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setImportoInizialeImpegno(BigDecimal value) {
        this.importoInizialeImpegno = value;
    }

    /**
     * Recupera il valore della proprietà importoInizialeSubAccertamento.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getImportoInizialeSubAccertamento() {
        return importoInizialeSubAccertamento;
    }

    /**
     * Imposta il valore della proprietà importoInizialeSubAccertamento.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setImportoInizialeSubAccertamento(BigDecimal value) {
        this.importoInizialeSubAccertamento = value;
    }

    /**
     * Recupera il valore della proprietà importoInizialeSubImpegno.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getImportoInizialeSubImpegno() {
        return importoInizialeSubImpegno;
    }

    /**
     * Imposta il valore della proprietà importoInizialeSubImpegno.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setImportoInizialeSubImpegno(BigDecimal value) {
        this.importoInizialeSubImpegno = value;
    }

    /**
     * Recupera il valore della proprietà importoModifica.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getImportoModifica() {
        return importoModifica;
    }

    /**
     * Imposta il valore della proprietà importoModifica.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setImportoModifica(BigDecimal value) {
        this.importoModifica = value;
    }

    /**
     * Recupera il valore della proprietà macroaggregato.
     * 
     * @return
     *     possible object is
     *     {@link Macroaggregato }
     *     
     */
    public Macroaggregato getMacroaggregato() {
        return macroaggregato;
    }

    /**
     * Imposta il valore della proprietà macroaggregato.
     * 
     * @param value
     *     allowed object is
     *     {@link Macroaggregato }
     *     
     */
    public void setMacroaggregato(Macroaggregato value) {
        this.macroaggregato = value;
    }

    /**
     * Recupera il valore della proprietà missione.
     * 
     * @return
     *     possible object is
     *     {@link Missione }
     *     
     */
    public Missione getMissione() {
        return missione;
    }

    /**
     * Imposta il valore della proprietà missione.
     * 
     * @param value
     *     allowed object is
     *     {@link Missione }
     *     
     */
    public void setMissione(Missione value) {
        this.missione = value;
    }

    /**
     * Recupera il valore della proprietà motivoAssenzaCig.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMotivoAssenzaCig() {
        return motivoAssenzaCig;
    }

    /**
     * Imposta il valore della proprietà motivoAssenzaCig.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMotivoAssenzaCig(String value) {
        this.motivoAssenzaCig = value;
    }

    /**
     * Recupera il valore della proprietà naturaRicorrente.
     * 
     * @return
     *     possible object is
     *     {@link ClassificatoreGenerico }
     *     
     */
    public ClassificatoreGenerico getNaturaRicorrente() {
        return naturaRicorrente;
    }

    /**
     * Imposta il valore della proprietà naturaRicorrente.
     * 
     * @param value
     *     allowed object is
     *     {@link ClassificatoreGenerico }
     *     
     */
    public void setNaturaRicorrente(ClassificatoreGenerico value) {
        this.naturaRicorrente = value;
    }

    /**
     * Recupera il valore della proprietà numeroAccertamento.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getNumeroAccertamento() {
        return numeroAccertamento;
    }

    /**
     * Imposta il valore della proprietà numeroAccertamento.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setNumeroAccertamento(BigDecimal value) {
        this.numeroAccertamento = value;
    }

    /**
     * Recupera il valore della proprietà numeroImpegno.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getNumeroImpegno() {
        return numeroImpegno;
    }

    /**
     * Imposta il valore della proprietà numeroImpegno.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setNumeroImpegno(BigDecimal value) {
        this.numeroImpegno = value;
    }

    /**
     * Recupera il valore della proprietà numeroModifica.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getNumeroModifica() {
        return numeroModifica;
    }

    /**
     * Imposta il valore della proprietà numeroModifica.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setNumeroModifica(Integer value) {
        this.numeroModifica = value;
    }

    /**
     * Recupera il valore della proprietà numeroSubAccertamento.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getNumeroSubAccertamento() {
        return numeroSubAccertamento;
    }

    /**
     * Imposta il valore della proprietà numeroSubAccertamento.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setNumeroSubAccertamento(BigDecimal value) {
        this.numeroSubAccertamento = value;
    }

    /**
     * Recupera il valore della proprietà numeroSubImpegno.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getNumeroSubImpegno() {
        return numeroSubImpegno;
    }

    /**
     * Imposta il valore della proprietà numeroSubImpegno.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setNumeroSubImpegno(BigDecimal value) {
        this.numeroSubImpegno = value;
    }

    /**
     * Recupera il valore della proprietà pianoDeiContiFinanziario.
     * 
     * @return
     *     possible object is
     *     {@link PianoDeiContiFinanziario }
     *     
     */
    public PianoDeiContiFinanziario getPianoDeiContiFinanziario() {
        return pianoDeiContiFinanziario;
    }

    /**
     * Imposta il valore della proprietà pianoDeiContiFinanziario.
     * 
     * @param value
     *     allowed object is
     *     {@link PianoDeiContiFinanziario }
     *     
     */
    public void setPianoDeiContiFinanziario(PianoDeiContiFinanziario value) {
        this.pianoDeiContiFinanziario = value;
    }

    /**
     * Recupera il valore della proprietà prenotazione.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPrenotazione() {
        return prenotazione;
    }

    /**
     * Imposta il valore della proprietà prenotazione.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPrenotazione(String value) {
        this.prenotazione = value;
    }

    /**
     * Recupera il valore della proprietà prenotazioneLiquidabile.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPrenotazioneLiquidabile() {
        return prenotazioneLiquidabile;
    }

    /**
     * Imposta il valore della proprietà prenotazioneLiquidabile.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPrenotazioneLiquidabile(String value) {
        this.prenotazioneLiquidabile = value;
    }

    /**
     * Recupera il valore della proprietà progetto.
     * 
     * @return
     *     possible object is
     *     {@link ProgettoStilo }
     *     
     */
    public ProgettoStilo getProgetto() {
        return progetto;
    }

    /**
     * Imposta il valore della proprietà progetto.
     * 
     * @param value
     *     allowed object is
     *     {@link ProgettoStilo }
     *     
     */
    public void setProgetto(ProgettoStilo value) {
        this.progetto = value;
    }

    /**
     * Recupera il valore della proprietà programma.
     * 
     * @return
     *     possible object is
     *     {@link Programma }
     *     
     */
    public Programma getProgramma() {
        return programma;
    }

    /**
     * Imposta il valore della proprietà programma.
     * 
     * @param value
     *     allowed object is
     *     {@link Programma }
     *     
     */
    public void setProgramma(Programma value) {
        this.programma = value;
    }

    /**
     * Recupera il valore della proprietà soggetto.
     * 
     * @return
     *     possible object is
     *     {@link SoggettoStilo }
     *     
     */
    public SoggettoStilo getSoggetto() {
        return soggetto;
    }

    /**
     * Imposta il valore della proprietà soggetto.
     * 
     * @param value
     *     allowed object is
     *     {@link SoggettoStilo }
     *     
     */
    public void setSoggetto(SoggettoStilo value) {
        this.soggetto = value;
    }

    /**
     * Recupera il valore della proprietà soggettoFinale.
     * 
     * @return
     *     possible object is
     *     {@link SoggettoStilo }
     *     
     */
    public SoggettoStilo getSoggettoFinale() {
        return soggettoFinale;
    }

    /**
     * Imposta il valore della proprietà soggettoFinale.
     * 
     * @param value
     *     allowed object is
     *     {@link SoggettoStilo }
     *     
     */
    public void setSoggettoFinale(SoggettoStilo value) {
        this.soggettoFinale = value;
    }

    /**
     * Recupera il valore della proprietà soggettoIniziale.
     * 
     * @return
     *     possible object is
     *     {@link SoggettoStilo }
     *     
     */
    public SoggettoStilo getSoggettoIniziale() {
        return soggettoIniziale;
    }

    /**
     * Imposta il valore della proprietà soggettoIniziale.
     * 
     * @param value
     *     allowed object is
     *     {@link SoggettoStilo }
     *     
     */
    public void setSoggettoIniziale(SoggettoStilo value) {
        this.soggettoIniziale = value;
    }

    /**
     * Recupera il valore della proprietà tipoDebitoSiope.
     * 
     * @return
     *     possible object is
     *     {@link TipoDebitoSiopeStilo }
     *     
     */
    public TipoDebitoSiopeStilo getTipoDebitoSiope() {
        return tipoDebitoSiope;
    }

    /**
     * Imposta il valore della proprietà tipoDebitoSiope.
     * 
     * @param value
     *     allowed object is
     *     {@link TipoDebitoSiopeStilo }
     *     
     */
    public void setTipoDebitoSiope(TipoDebitoSiopeStilo value) {
        this.tipoDebitoSiope = value;
    }

    /**
     * Recupera il valore della proprietà tipoFinanziamento.
     * 
     * @return
     *     possible object is
     *     {@link TipoFinanziamento }
     *     
     */
    public TipoFinanziamento getTipoFinanziamento() {
        return tipoFinanziamento;
    }

    /**
     * Imposta il valore della proprietà tipoFinanziamento.
     * 
     * @param value
     *     allowed object is
     *     {@link TipoFinanziamento }
     *     
     */
    public void setTipoFinanziamento(TipoFinanziamento value) {
        this.tipoFinanziamento = value;
    }

    /**
     * Recupera il valore della proprietà tipoMovimentoGestione.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTipoMovimentoGestione() {
        return tipoMovimentoGestione;
    }

    /**
     * Imposta il valore della proprietà tipoMovimentoGestione.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTipoMovimentoGestione(String value) {
        this.tipoMovimentoGestione = value;
    }

    /**
     * Recupera il valore della proprietà tipologia.
     * 
     * @return
     *     possible object is
     *     {@link Tipologia }
     *     
     */
    public Tipologia getTipologia() {
        return tipologia;
    }

    /**
     * Imposta il valore della proprietà tipologia.
     * 
     * @param value
     *     allowed object is
     *     {@link Tipologia }
     *     
     */
    public void setTipologia(Tipologia value) {
        this.tipologia = value;
    }

    /**
     * Recupera il valore della proprietà titolo.
     * 
     * @return
     *     possible object is
     *     {@link Titolo }
     *     
     */
    public Titolo getTitolo() {
        return titolo;
    }

    /**
     * Imposta il valore della proprietà titolo.
     * 
     * @param value
     *     allowed object is
     *     {@link Titolo }
     *     
     */
    public void setTitolo(Titolo value) {
        this.titolo = value;
    }

    /**
     * Recupera il valore della proprietà vincoli.
     * 
     * @return
     *     possible object is
     *     {@link VincoliStilo }
     *     
     */
    public VincoliStilo getVincoli() {
        return vincoli;
    }

    /**
     * Imposta il valore della proprietà vincoli.
     * 
     * @param value
     *     allowed object is
     *     {@link VincoliStilo }
     *     
     */
    public void setVincoli(VincoliStilo value) {
        this.vincoli = value;
    }

}
