/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */
package it.csi.siac.integ.data._1;

import java.util.Date;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import org.w3._2001.xmlschema.Adapter1;


/**
 * <p>Classe Java per ordinativo complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="ordinativo"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{http://siac.csi.it/integ/data/1.0}entitaBase"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="codiceSoggetto" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="dataEmissione" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/&gt;
 *         &lt;element name="dataQuietanza" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/&gt;
 *         &lt;element name="denominazioneSoggetto" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="numeroCapitolo" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/&gt;
 *         &lt;element name="numeroOrdinativo" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/extension&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ordinativo", propOrder = {
    "codiceSoggetto",
    "dataEmissione",
    "dataQuietanza",
    "denominazioneSoggetto",
    "numeroCapitolo",
    "numeroOrdinativo"
})
@XmlSeeAlso({
    OrdinativoIncasso.class,
    SubOrdinativoIncasso.class,
    OrdinativoPagamento.class,
    SubOrdinativoPagamento.class
})
public class Ordinativo
    extends EntitaBase
{

    protected String codiceSoggetto;
    @XmlElement(type = String.class)
    @XmlJavaTypeAdapter(Adapter1 .class)
    @XmlSchemaType(name = "dateTime")
    protected Date dataEmissione;
    @XmlElement(type = String.class)
    @XmlJavaTypeAdapter(Adapter1 .class)
    @XmlSchemaType(name = "dateTime")
    protected Date dataQuietanza;
    protected String denominazioneSoggetto;
    protected Integer numeroCapitolo;
    protected Integer numeroOrdinativo;

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
     * Recupera il valore della proprietà dataQuietanza.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public Date getDataQuietanza() {
        return dataQuietanza;
    }

    /**
     * Imposta il valore della proprietà dataQuietanza.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDataQuietanza(Date value) {
        this.dataQuietanza = value;
    }

    /**
     * Recupera il valore della proprietà denominazioneSoggetto.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDenominazioneSoggetto() {
        return denominazioneSoggetto;
    }

    /**
     * Imposta il valore della proprietà denominazioneSoggetto.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDenominazioneSoggetto(String value) {
        this.denominazioneSoggetto = value;
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

}
