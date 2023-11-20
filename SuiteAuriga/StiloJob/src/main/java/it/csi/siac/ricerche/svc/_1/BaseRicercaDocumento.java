/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */
package it.csi.siac.ricerche.svc._1;

import java.util.Date;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import it.csi.siac.integ.data._1.RicercaPaginataRequest;
import org.w3._2001.xmlschema.Adapter1;


/**
 * <p>Classe Java per baseRicercaDocumento complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="baseRicercaDocumento"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{http://siac.csi.it/integ/data/1.0}ricercaPaginataRequest"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="annoDocumento" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/&gt;
 *         &lt;element name="annoRepertorio" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/&gt;
 *         &lt;element name="codiceSoggetto" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="dataRepertorio" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/&gt;
 *         &lt;element name="numeroDocumento" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="numeroRepertorio" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="registroRepertorio" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="tipoDocumento" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/extension&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "baseRicercaDocumento", propOrder = {
    "annoDocumento",
    "annoRepertorio",
    "codiceSoggetto",
    "dataRepertorio",
    "numeroDocumento",
    "numeroRepertorio",
    "registroRepertorio",
    "tipoDocumento"
})
@XmlSeeAlso({
    RicercaDocumentoSpesa.class,
    RicercaDocumentoEntrata.class
})
public abstract class BaseRicercaDocumento
    extends RicercaPaginataRequest
{

    protected Integer annoDocumento;
    protected Integer annoRepertorio;
    protected String codiceSoggetto;
    @XmlElement(type = String.class)
    @XmlJavaTypeAdapter(Adapter1 .class)
    @XmlSchemaType(name = "dateTime")
    protected Date dataRepertorio;
    protected String numeroDocumento;
    protected String numeroRepertorio;
    protected String registroRepertorio;
    protected String tipoDocumento;

    /**
     * Recupera il valore della proprietà annoDocumento.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getAnnoDocumento() {
        return annoDocumento;
    }

    /**
     * Imposta il valore della proprietà annoDocumento.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setAnnoDocumento(Integer value) {
        this.annoDocumento = value;
    }

    /**
     * Recupera il valore della proprietà annoRepertorio.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getAnnoRepertorio() {
        return annoRepertorio;
    }

    /**
     * Imposta il valore della proprietà annoRepertorio.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setAnnoRepertorio(Integer value) {
        this.annoRepertorio = value;
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
     * Recupera il valore della proprietà dataRepertorio.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public Date getDataRepertorio() {
        return dataRepertorio;
    }

    /**
     * Imposta il valore della proprietà dataRepertorio.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDataRepertorio(Date value) {
        this.dataRepertorio = value;
    }

    /**
     * Recupera il valore della proprietà numeroDocumento.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNumeroDocumento() {
        return numeroDocumento;
    }

    /**
     * Imposta il valore della proprietà numeroDocumento.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNumeroDocumento(String value) {
        this.numeroDocumento = value;
    }

    /**
     * Recupera il valore della proprietà numeroRepertorio.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNumeroRepertorio() {
        return numeroRepertorio;
    }

    /**
     * Imposta il valore della proprietà numeroRepertorio.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNumeroRepertorio(String value) {
        this.numeroRepertorio = value;
    }

    /**
     * Recupera il valore della proprietà registroRepertorio.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRegistroRepertorio() {
        return registroRepertorio;
    }

    /**
     * Imposta il valore della proprietà registroRepertorio.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRegistroRepertorio(String value) {
        this.registroRepertorio = value;
    }

    /**
     * Recupera il valore della proprietà tipoDocumento.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTipoDocumento() {
        return tipoDocumento;
    }

    /**
     * Imposta il valore della proprietà tipoDocumento.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTipoDocumento(String value) {
        this.tipoDocumento = value;
    }

}
