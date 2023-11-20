/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */
package it.csi.siac.integ.data._1;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per provvedimento complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="provvedimento"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{http://siac.csi.it/integ/data/1.0}entitaBase"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="annoProvvedimento" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/&gt;
 *         &lt;element name="codiceTipoProvvedimento" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="numeroProvvedimento" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/&gt;
 *         &lt;element name="sac" type="{http://siac.csi.it/integ/data/1.0}strutturaAmministrativa" minOccurs="0"/&gt;
 *         &lt;element name="statoProvvedimento" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/extension&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "provvedimento", propOrder = {
    "annoProvvedimento",
    "codiceTipoProvvedimento",
    "numeroProvvedimento",
    "sac",
    "statoProvvedimento"
})
public class Provvedimento
    extends EntitaBase
{

    protected Integer annoProvvedimento;
    protected String codiceTipoProvvedimento;
    protected Integer numeroProvvedimento;
    protected StrutturaAmministrativa sac;
    protected String statoProvvedimento;

    /**
     * Recupera il valore della proprietà annoProvvedimento.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getAnnoProvvedimento() {
        return annoProvvedimento;
    }

    /**
     * Imposta il valore della proprietà annoProvvedimento.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setAnnoProvvedimento(Integer value) {
        this.annoProvvedimento = value;
    }

    /**
     * Recupera il valore della proprietà codiceTipoProvvedimento.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCodiceTipoProvvedimento() {
        return codiceTipoProvvedimento;
    }

    /**
     * Imposta il valore della proprietà codiceTipoProvvedimento.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCodiceTipoProvvedimento(String value) {
        this.codiceTipoProvvedimento = value;
    }

    /**
     * Recupera il valore della proprietà numeroProvvedimento.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getNumeroProvvedimento() {
        return numeroProvvedimento;
    }

    /**
     * Imposta il valore della proprietà numeroProvvedimento.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setNumeroProvvedimento(Integer value) {
        this.numeroProvvedimento = value;
    }

    /**
     * Recupera il valore della proprietà sac.
     * 
     * @return
     *     possible object is
     *     {@link StrutturaAmministrativa }
     *     
     */
    public StrutturaAmministrativa getSac() {
        return sac;
    }

    /**
     * Imposta il valore della proprietà sac.
     * 
     * @param value
     *     allowed object is
     *     {@link StrutturaAmministrativa }
     *     
     */
    public void setSac(StrutturaAmministrativa value) {
        this.sac = value;
    }

    /**
     * Recupera il valore della proprietà statoProvvedimento.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStatoProvvedimento() {
        return statoProvvedimento;
    }

    /**
     * Imposta il valore della proprietà statoProvvedimento.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStatoProvvedimento(String value) {
        this.statoProvvedimento = value;
    }

}
