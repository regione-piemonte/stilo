/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

package it.eng.job.codaEXport.types.generated.determina;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Classe Java per Determina complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="Determina">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="chiaveDetermina" type="{http:/www.csi.it/atm2bu/stiloatti/xmlatti}ChiaveDetermina"/>
 *         &lt;element name="testo" type="{http:/www.csi.it/atm2bu/stiloatti/xmlatti}DetTesto"/>
 *         &lt;element name="allegati" type="{http:/www.csi.it/atm2bu/stiloatti/xmlatti}DetAllegato" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="allegatiCartacei" type="{http:/www.csi.it/atm2bu/stiloatti/xmlatti}DetAllegatoCartaceo" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="detVariate" type="{http:/www.csi.it/atm2bu/stiloatti/xmlatti}DetVariata" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="delibereRiferimento" type="{http:/www.csi.it/atm2bu/stiloatti/xmlatti}DeliberaRiferimento" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="identificativo">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               &lt;maxLength value="8"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="oggetto">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               &lt;maxLength value="420"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="codiceSettore">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               &lt;maxLength value="24"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="numeroLegislatura">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}int">
 *               &lt;pattern value="\d{1,3}"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="dataDetermina" type="{http://www.w3.org/2001/XMLSchema}date"/>
 *         &lt;element name="matricolaUtente">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               &lt;maxLength value="10"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="flagImpegnoSpesa">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               &lt;enumeration value="0"/>
 *               &lt;enumeration value="A"/>
 *               &lt;enumeration value="C"/>
 *               &lt;enumeration value="G"/>
 *               &lt;enumeration value="I"/>
 *               &lt;enumeration value="M"/>
 *               &lt;enumeration value="N"/>
 *               &lt;enumeration value="P"/>
 *               &lt;enumeration value="R"/>
 *               &lt;enumeration value="X"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="sensibile" type="{http:/www.csi.it/atm2bu/stiloatti/xmlatti}FlagNumerico"/>
 *         &lt;element name="dataCancellazione" type="{http://www.w3.org/2001/XMLSchema}date" minOccurs="0"/>
 *         &lt;element name="dataEstrazione" type="{http://www.w3.org/2001/XMLSchema}date" minOccurs="0"/>
 *         &lt;element name="dataVisto" type="{http://www.w3.org/2001/XMLSchema}date" minOccurs="0"/>
 *         &lt;element name="flgPubblAll" type="{http:/www.csi.it/atm2bu/stiloatti/xmlatti}FlagNumerico" minOccurs="0"/>
 *         &lt;element name="personale" type="{http:/www.csi.it/atm2bu/stiloatti/xmlatti}FlagNumerico"/>
 *         &lt;element name="codPubblicazione" minOccurs="0">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}int">
 *               &lt;enumeration value="1"/>
 *               &lt;enumeration value="2"/>
 *               &lt;enumeration value="3"/>
 *               &lt;enumeration value="4"/>
 *               &lt;enumeration value="5"/>
 *               &lt;enumeration value="6"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="notePubblicazione" minOccurs="0">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               &lt;maxLength value="500"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="urgenza" type="{http:/www.csi.it/atm2bu/stiloatti/xmlatti}FlagNumerico" minOccurs="0"/>
 *         &lt;element name="dataValidazione" type="{http://www.w3.org/2001/XMLSchema}date" minOccurs="0"/>
 *         &lt;element name="valutazioniConcPersone" type="{http:/www.csi.it/atm2bu/stiloatti/xmlatti}FlagNumerico"/>
 *         &lt;element name="avvenutoImpegno" type="{http:/www.csi.it/atm2bu/stiloatti/xmlatti}FlagNumerico" minOccurs="0"/>
 *         &lt;element name="pubblicazioneNotiziario" type="{http:/www.csi.it/atm2bu/stiloatti/xmlatti}FlagNumerico"/>
 *         &lt;element name="referenteNotiziario" minOccurs="0">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               &lt;maxLength value="10"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="noteNotiziario" minOccurs="0">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               &lt;maxLength value="500"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="modificaDiImpegno" type="{http:/www.csi.it/atm2bu/stiloatti/xmlatti}FlagNumerico" minOccurs="0"/>
 *         &lt;element name="impegniMultipli" type="{http:/www.csi.it/atm2bu/stiloatti/xmlatti}FlagNumerico" minOccurs="0"/>
 *         &lt;element name="impegnoDelegato" type="{http:/www.csi.it/atm2bu/stiloatti/xmlatti}FlagNumerico" minOccurs="0"/>
 *         &lt;element name="dirigenteResponsabile">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               &lt;maxLength value="100"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="trasparenza">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               &lt;maxLength value="180"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="importo" minOccurs="0">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               &lt;maxLength value="150"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Determina", propOrder = {
    "chiaveDetermina",
    "testo",
    "allegati",
    "allegatiCartacei",
    "detVariate",
    "delibereRiferimento",
    "identificativo",
    "oggetto",
    "codiceSettore",
    "numeroLegislatura",
    "dataDetermina",
    "matricolaUtente",
    "flagImpegnoSpesa",
    "sensibile",
    "dataCancellazione",
    "dataEstrazione",
    "dataVisto",
    "flgPubblAll",
    "personale",
    "codPubblicazione",
    "notePubblicazione",
    "urgenza",
    "dataValidazione",
    "valutazioniConcPersone",
    "avvenutoImpegno",
    "pubblicazioneNotiziario",
    "referenteNotiziario",
    "noteNotiziario",
    "modificaDiImpegno",
    "impegniMultipli",
    "impegnoDelegato",
    "dirigenteResponsabile",
    "trasparenza",
    "importo"
})
public class Determina {

    @XmlElement(required = true)
    protected ChiaveDetermina chiaveDetermina;
    @XmlElement(required = true)
    protected DetTesto testo;
    protected List<DetAllegato> allegati;
    protected List<DetAllegatoCartaceo> allegatiCartacei;
    protected List<DetVariata> detVariate;
    protected List<DeliberaRiferimento> delibereRiferimento;
    @XmlElement(required = true)
    protected String identificativo;
    @XmlElement(required = true)
    protected String oggetto;
    @XmlElement(required = true)
    protected String codiceSettore;
    protected int numeroLegislatura;
    @XmlElement(required = true)
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar dataDetermina;
    @XmlElement(required = true)
    protected String matricolaUtente;
    @XmlElement(required = true)
    protected String flagImpegnoSpesa;
    protected int sensibile;
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar dataCancellazione;
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar dataEstrazione;
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar dataVisto;
    protected Integer flgPubblAll;
    protected int personale;
    protected Integer codPubblicazione;
    protected String notePubblicazione;
    protected Integer urgenza;
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar dataValidazione;
    protected int valutazioniConcPersone;
    protected Integer avvenutoImpegno;
    protected int pubblicazioneNotiziario;
    protected String referenteNotiziario;
    protected String noteNotiziario;
    protected Integer modificaDiImpegno;
    protected Integer impegniMultipli;
    protected Integer impegnoDelegato;
    @XmlElement(required = true)
    protected String dirigenteResponsabile;
    @XmlElement(required = true)
    protected String trasparenza;
    protected String importo;

    /**
     * Recupera il valore della propriet� chiaveDetermina.
     * 
     * @return
     *     possible object is
     *     {@link ChiaveDetermina }
     *     
     */
    public ChiaveDetermina getChiaveDetermina() {
        return chiaveDetermina;
    }

    /**
     * Imposta il valore della propriet� chiaveDetermina.
     * 
     * @param value
     *     allowed object is
     *     {@link ChiaveDetermina }
     *     
     */
    public void setChiaveDetermina(ChiaveDetermina value) {
        this.chiaveDetermina = value;
    }

    /**
     * Recupera il valore della propriet� testo.
     * 
     * @return
     *     possible object is
     *     {@link DetTesto }
     *     
     */
    public DetTesto getTesto() {
        return testo;
    }

    /**
     * Imposta il valore della propriet� testo.
     * 
     * @param value
     *     allowed object is
     *     {@link DetTesto }
     *     
     */
    public void setTesto(DetTesto value) {
        this.testo = value;
    }

    /**
     * Gets the value of the allegati property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the allegati property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getAllegati().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link DetAllegato }
     * 
     * 
     */
    public List<DetAllegato> getAllegati() {
        if (allegati == null) {
            allegati = new ArrayList<DetAllegato>();
        }
        return this.allegati;
    }

    /**
     * Gets the value of the allegatiCartacei property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the allegatiCartacei property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getAllegatiCartacei().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link DetAllegatoCartaceo }
     * 
     * 
     */
    public List<DetAllegatoCartaceo> getAllegatiCartacei() {
        if (allegatiCartacei == null) {
            allegatiCartacei = new ArrayList<DetAllegatoCartaceo>();
        }
        return this.allegatiCartacei;
    }

    /**
     * Gets the value of the detVariate property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the detVariate property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getDetVariate().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link DetVariata }
     * 
     * 
     */
    public List<DetVariata> getDetVariate() {
        if (detVariate == null) {
            detVariate = new ArrayList<DetVariata>();
        }
        return this.detVariate;
    }

    /**
     * Gets the value of the delibereRiferimento property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the delibereRiferimento property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getDelibereRiferimento().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link DeliberaRiferimento }
     * 
     * 
     */
    public List<DeliberaRiferimento> getDelibereRiferimento() {
        if (delibereRiferimento == null) {
            delibereRiferimento = new ArrayList<DeliberaRiferimento>();
        }
        return this.delibereRiferimento;
    }

    /**
     * Recupera il valore della propriet� identificativo.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIdentificativo() {
        return identificativo;
    }

    /**
     * Imposta il valore della propriet� identificativo.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIdentificativo(String value) {
        this.identificativo = value;
    }

    /**
     * Recupera il valore della propriet� oggetto.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOggetto() {
        return oggetto;
    }

    /**
     * Imposta il valore della propriet� oggetto.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOggetto(String value) {
        this.oggetto = value;
    }

    /**
     * Recupera il valore della propriet� codiceSettore.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCodiceSettore() {
        return codiceSettore;
    }

    /**
     * Imposta il valore della propriet� codiceSettore.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCodiceSettore(String value) {
        this.codiceSettore = value;
    }

    /**
     * Recupera il valore della propriet� numeroLegislatura.
     * 
     */
    public int getNumeroLegislatura() {
        return numeroLegislatura;
    }

    /**
     * Imposta il valore della propriet� numeroLegislatura.
     * 
     */
    public void setNumeroLegislatura(int value) {
        this.numeroLegislatura = value;
    }

    /**
     * Recupera il valore della propriet� dataDetermina.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDataDetermina() {
        return dataDetermina;
    }

    /**
     * Imposta il valore della propriet� dataDetermina.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDataDetermina(XMLGregorianCalendar value) {
        this.dataDetermina = value;
    }

    /**
     * Recupera il valore della propriet� matricolaUtente.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMatricolaUtente() {
        return matricolaUtente;
    }

    /**
     * Imposta il valore della propriet� matricolaUtente.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMatricolaUtente(String value) {
        this.matricolaUtente = value;
    }

    /**
     * Recupera il valore della propriet� flagImpegnoSpesa.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFlagImpegnoSpesa() {
        return flagImpegnoSpesa;
    }

    /**
     * Imposta il valore della propriet� flagImpegnoSpesa.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFlagImpegnoSpesa(String value) {
        this.flagImpegnoSpesa = value;
    }

    /**
     * Recupera il valore della propriet� sensibile.
     * 
     */
    public int getSensibile() {
        return sensibile;
    }

    /**
     * Imposta il valore della propriet� sensibile.
     * 
     */
    public void setSensibile(int value) {
        this.sensibile = value;
    }

    /**
     * Recupera il valore della propriet� dataCancellazione.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDataCancellazione() {
        return dataCancellazione;
    }

    /**
     * Imposta il valore della propriet� dataCancellazione.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDataCancellazione(XMLGregorianCalendar value) {
        this.dataCancellazione = value;
    }

    /**
     * Recupera il valore della propriet� dataEstrazione.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDataEstrazione() {
        return dataEstrazione;
    }

    /**
     * Imposta il valore della propriet� dataEstrazione.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDataEstrazione(XMLGregorianCalendar value) {
        this.dataEstrazione = value;
    }

    /**
     * Recupera il valore della propriet� dataVisto.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDataVisto() {
        return dataVisto;
    }

    /**
     * Imposta il valore della propriet� dataVisto.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDataVisto(XMLGregorianCalendar value) {
        this.dataVisto = value;
    }

    /**
     * Recupera il valore della propriet� flgPubblAll.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getFlgPubblAll() {
        return flgPubblAll;
    }

    /**
     * Imposta il valore della propriet� flgPubblAll.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setFlgPubblAll(Integer value) {
        this.flgPubblAll = value;
    }

    /**
     * Recupera il valore della propriet� personale.
     * 
     */
    public int getPersonale() {
        return personale;
    }

    /**
     * Imposta il valore della propriet� personale.
     * 
     */
    public void setPersonale(int value) {
        this.personale = value;
    }

    /**
     * Recupera il valore della propriet� codPubblicazione.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getCodPubblicazione() {
        return codPubblicazione;
    }

    /**
     * Imposta il valore della propriet� codPubblicazione.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setCodPubblicazione(Integer value) {
        this.codPubblicazione = value;
    }

    /**
     * Recupera il valore della propriet� notePubblicazione.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNotePubblicazione() {
        return notePubblicazione;
    }

    /**
     * Imposta il valore della propriet� notePubblicazione.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNotePubblicazione(String value) {
        this.notePubblicazione = value;
    }

    /**
     * Recupera il valore della propriet� urgenza.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getUrgenza() {
        return urgenza;
    }

    /**
     * Imposta il valore della propriet� urgenza.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setUrgenza(Integer value) {
        this.urgenza = value;
    }

    /**
     * Recupera il valore della propriet� dataValidazione.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDataValidazione() {
        return dataValidazione;
    }

    /**
     * Imposta il valore della propriet� dataValidazione.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDataValidazione(XMLGregorianCalendar value) {
        this.dataValidazione = value;
    }

    /**
     * Recupera il valore della propriet� valutazioniConcPersone.
     * 
     */
    public int getValutazioniConcPersone() {
        return valutazioniConcPersone;
    }

    /**
     * Imposta il valore della propriet� valutazioniConcPersone.
     * 
     */
    public void setValutazioniConcPersone(int value) {
        this.valutazioniConcPersone = value;
    }

    /**
     * Recupera il valore della propriet� avvenutoImpegno.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getAvvenutoImpegno() {
        return avvenutoImpegno;
    }

    /**
     * Imposta il valore della propriet� avvenutoImpegno.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setAvvenutoImpegno(Integer value) {
        this.avvenutoImpegno = value;
    }

    /**
     * Recupera il valore della propriet� pubblicazioneNotiziario.
     * 
     */
    public int getPubblicazioneNotiziario() {
        return pubblicazioneNotiziario;
    }

    /**
     * Imposta il valore della propriet� pubblicazioneNotiziario.
     * 
     */
    public void setPubblicazioneNotiziario(int value) {
        this.pubblicazioneNotiziario = value;
    }

    /**
     * Recupera il valore della propriet� referenteNotiziario.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getReferenteNotiziario() {
        return referenteNotiziario;
    }

    /**
     * Imposta il valore della propriet� referenteNotiziario.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setReferenteNotiziario(String value) {
        this.referenteNotiziario = value;
    }

    /**
     * Recupera il valore della propriet� noteNotiziario.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNoteNotiziario() {
        return noteNotiziario;
    }

    /**
     * Imposta il valore della propriet� noteNotiziario.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNoteNotiziario(String value) {
        this.noteNotiziario = value;
    }

    /**
     * Recupera il valore della propriet� modificaDiImpegno.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getModificaDiImpegno() {
        return modificaDiImpegno;
    }

    /**
     * Imposta il valore della propriet� modificaDiImpegno.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setModificaDiImpegno(Integer value) {
        this.modificaDiImpegno = value;
    }

    /**
     * Recupera il valore della propriet� impegniMultipli.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getImpegniMultipli() {
        return impegniMultipli;
    }

    /**
     * Imposta il valore della propriet� impegniMultipli.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setImpegniMultipli(Integer value) {
        this.impegniMultipli = value;
    }

    /**
     * Recupera il valore della propriet� impegnoDelegato.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getImpegnoDelegato() {
        return impegnoDelegato;
    }

    /**
     * Imposta il valore della propriet� impegnoDelegato.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setImpegnoDelegato(Integer value) {
        this.impegnoDelegato = value;
    }

    /**
     * Recupera il valore della propriet� dirigenteResponsabile.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDirigenteResponsabile() {
        return dirigenteResponsabile;
    }

    /**
     * Imposta il valore della propriet� dirigenteResponsabile.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDirigenteResponsabile(String value) {
        this.dirigenteResponsabile = value;
    }

    /**
     * Recupera il valore della propriet� trasparenza.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTrasparenza() {
        return trasparenza;
    }

    /**
     * Imposta il valore della propriet� trasparenza.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTrasparenza(String value) {
        this.trasparenza = value;
    }

    /**
     * Recupera il valore della propriet� importo.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getImporto() {
        return importo;
    }

    /**
     * Imposta il valore della propriet� importo.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setImporto(String value) {
        this.importo = value;
    }

}
