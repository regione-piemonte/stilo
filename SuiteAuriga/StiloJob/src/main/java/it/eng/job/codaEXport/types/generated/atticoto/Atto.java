/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Classe Java per anonymous complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="AnnoProvv" type="{http://www.w3.org/2001/XMLSchema}gYear"/>
 *         &lt;element name="NroProvv">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}positiveInteger">
 *               &lt;minInclusive value="1"/>
 *               &lt;maxInclusive value="9999999"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="CdC" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="Oggetto">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               &lt;maxLength value="500"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="CodTipo" type="{http://www.w3.org/2001/XMLSchema}anyType"/>
 *         &lt;element name="DataSedutaGiunta" type="{http://www.w3.org/2001/XMLSchema}date" minOccurs="0"/>
 *         &lt;element name="DataSedutaConsiglio" type="{http://www.w3.org/2001/XMLSchema}date" minOccurs="0"/>
 *         &lt;element name="DataEsecDelCons" type="{http://www.w3.org/2001/XMLSchema}date" minOccurs="0"/>
 *         &lt;element name="DataInvioSegrGen" type="{http://www.w3.org/2001/XMLSchema}date" minOccurs="0"/>
 *         &lt;element name="CodEsitoGiunta" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="CodEsitoConsiglio" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="FlgIE">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}int">
 *               &lt;pattern value="1|0"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="DataEsecutivita" type="{http://www.w3.org/2001/XMLSchema}date" minOccurs="0"/>
 *         &lt;element name="testo" type="{}Testo"/>
 *         &lt;element name="allegato" type="{}Allegato" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "annoProvv",
    "nroProvv",
    "cdC",
    "oggetto",
    "codTipo",
    "dataSedutaGiunta",
    "dataSedutaConsiglio",
    "dataEsecDelCons",
    "dataInvioSegrGen",
    "codEsitoGiunta",
    "codEsitoConsiglio",
    "flgIE",
    "dataEsecutivita",
    "testo",
    "allegato"
})
@XmlRootElement(name = "Atto")
public class Atto {

    @XmlElement(name = "AnnoProvv", required = true)
    @XmlSchemaType(name = "gYear")
    protected XMLGregorianCalendar annoProvv;
    @XmlElement(name = "NroProvv")
    protected int nroProvv;
    @XmlElement(name = "CdC", required = true)
    protected String cdC;
    @XmlElement(name = "Oggetto", required = true)
    protected String oggetto;
    @XmlElement(name = "CodTipo", required = true)
    protected Object codTipo;
    @XmlElement(name = "DataSedutaGiunta")
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar dataSedutaGiunta;
    @XmlElement(name = "DataSedutaConsiglio")
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar dataSedutaConsiglio;
    @XmlElement(name = "DataEsecDelCons")
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar dataEsecDelCons;
    @XmlElement(name = "DataInvioSegrGen")
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar dataInvioSegrGen;
    @XmlElement(name = "CodEsitoGiunta")
    protected String codEsitoGiunta;
    @XmlElement(name = "CodEsitoConsiglio")
    protected String codEsitoConsiglio;
    @XmlElement(name = "FlgIE")
    protected int flgIE;
    @XmlElement(name = "DataEsecutivita")
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar dataEsecutivita;
    @XmlElement(required = true)
    protected Testo testo;
    protected List<Allegato> allegato;

    /**
     * Recupera il valore della propriet� annoProvv.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getAnnoProvv() {
        return annoProvv;
    }

    /**
     * Imposta il valore della propriet� annoProvv.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setAnnoProvv(XMLGregorianCalendar value) {
        this.annoProvv = value;
    }

    /**
     * Recupera il valore della propriet� nroProvv.
     * 
     */
    public int getNroProvv() {
        return nroProvv;
    }

    /**
     * Imposta il valore della propriet� nroProvv.
     * 
     */
    public void setNroProvv(int value) {
        this.nroProvv = value;
    }

    /**
     * Recupera il valore della propriet� cdC.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCdC() {
        return cdC;
    }

    /**
     * Imposta il valore della propriet� cdC.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCdC(String value) {
        this.cdC = value;
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
     * Recupera il valore della propriet� codTipo.
     * 
     * @return
     *     possible object is
     *     {@link Object }
     *     
     */
    public Object getCodTipo() {
        return codTipo;
    }

    /**
     * Imposta il valore della propriet� codTipo.
     * 
     * @param value
     *     allowed object is
     *     {@link Object }
     *     
     */
    public void setCodTipo(Object value) {
        this.codTipo = value;
    }

    /**
     * Recupera il valore della propriet� dataSedutaGiunta.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDataSedutaGiunta() {
        return dataSedutaGiunta;
    }

    /**
     * Imposta il valore della propriet� dataSedutaGiunta.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDataSedutaGiunta(XMLGregorianCalendar value) {
        this.dataSedutaGiunta = value;
    }

    /**
     * Recupera il valore della propriet� dataSedutaConsiglio.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDataSedutaConsiglio() {
        return dataSedutaConsiglio;
    }

    /**
     * Imposta il valore della propriet� dataSedutaConsiglio.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDataSedutaConsiglio(XMLGregorianCalendar value) {
        this.dataSedutaConsiglio = value;
    }

    /**
     * Recupera il valore della propriet� dataEsecDelCons.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDataEsecDelCons() {
        return dataEsecDelCons;
    }

    /**
     * Imposta il valore della propriet� dataEsecDelCons.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDataEsecDelCons(XMLGregorianCalendar value) {
        this.dataEsecDelCons = value;
    }

    /**
     * Recupera il valore della propriet� dataInvioSegrGen.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDataInvioSegrGen() {
        return dataInvioSegrGen;
    }

    /**
     * Imposta il valore della propriet� dataInvioSegrGen.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDataInvioSegrGen(XMLGregorianCalendar value) {
        this.dataInvioSegrGen = value;
    }

    /**
     * Recupera il valore della propriet� codEsitoGiunta.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCodEsitoGiunta() {
        return codEsitoGiunta;
    }

    /**
     * Imposta il valore della propriet� codEsitoGiunta.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCodEsitoGiunta(String value) {
        this.codEsitoGiunta = value;
    }

    /**
     * Recupera il valore della propriet� codEsitoConsiglio.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCodEsitoConsiglio() {
        return codEsitoConsiglio;
    }

    /**
     * Imposta il valore della propriet� codEsitoConsiglio.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCodEsitoConsiglio(String value) {
        this.codEsitoConsiglio = value;
    }

    /**
     * Recupera il valore della propriet� flgIE.
     * 
     */
    public int getFlgIE() {
        return flgIE;
    }

    /**
     * Imposta il valore della propriet� flgIE.
     * 
     */
    public void setFlgIE(int value) {
        this.flgIE = value;
    }

    /**
     * Recupera il valore della propriet� dataEsecutivita.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDataEsecutivita() {
        return dataEsecutivita;
    }

    /**
     * Imposta il valore della propriet� dataEsecutivita.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDataEsecutivita(XMLGregorianCalendar value) {
        this.dataEsecutivita = value;
    }

    /**
     * Recupera il valore della propriet� testo.
     * 
     * @return
     *     possible object is
     *     {@link Testo }
     *     
     */
    public Testo getTesto() {
        return testo;
    }

    /**
     * Imposta il valore della propriet� testo.
     * 
     * @param value
     *     allowed object is
     *     {@link Testo }
     *     
     */
    public void setTesto(Testo value) {
        this.testo = value;
    }

    /**
     * Gets the value of the allegato property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the allegato property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getAllegato().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Allegato }
     * 
     * 
     */
    public List<Allegato> getAllegato() {
        if (allegato == null) {
            allegato = new ArrayList<Allegato>();
        }
        return this.allegato;
    }

}
