/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */
package it.csi.siac.ricerche.data._1;

import java.math.BigDecimal;
import java.util.Date;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import it.csi.siac.integ.data._1.EntitaCodificataBase;
import it.csi.siac.integ.data._1.SiNoEnum;
import it.csi.siac.integ.data._1.TipoProvvisorioDiCassa;
import org.w3._2001.xmlschema.Adapter1;


/**
 * <p>Classe Java per provvisorioDiCassa complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="provvisorioDiCassa"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{http://siac.csi.it/integ/data/1.0}entitaCodificataBase"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="annullato" type="{http://siac.csi.it/integ/data/1.0}siNoEnum" minOccurs="0"/&gt;
 *         &lt;element name="causale" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="data" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/&gt;
 *         &lt;element name="importo" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/&gt;
 *         &lt;element name="numero" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/&gt;
 *         &lt;element name="tipoProvvisorioDiCassa" type="{http://siac.csi.it/integ/data/1.0}tipoProvvisorioDiCassa" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/extension&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "provvisorioDiCassa", propOrder = {
    "annullato",
    "causale",
    "data",
    "importo",
    "numero",
    "tipoProvvisorioDiCassa"
})
public class ProvvisorioDiCassa
    extends EntitaCodificataBase
{

    @XmlSchemaType(name = "string")
    protected SiNoEnum annullato;
    protected String causale;
    @XmlElement(type = String.class)
    @XmlJavaTypeAdapter(Adapter1 .class)
    @XmlSchemaType(name = "dateTime")
    protected Date data;
    protected BigDecimal importo;
    protected Integer numero;
    @XmlSchemaType(name = "string")
    protected TipoProvvisorioDiCassa tipoProvvisorioDiCassa;

    /**
     * Recupera il valore della proprietà annullato.
     * 
     * @return
     *     possible object is
     *     {@link SiNoEnum }
     *     
     */
    public SiNoEnum getAnnullato() {
        return annullato;
    }

    /**
     * Imposta il valore della proprietà annullato.
     * 
     * @param value
     *     allowed object is
     *     {@link SiNoEnum }
     *     
     */
    public void setAnnullato(SiNoEnum value) {
        this.annullato = value;
    }

    /**
     * Recupera il valore della proprietà causale.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCausale() {
        return causale;
    }

    /**
     * Imposta il valore della proprietà causale.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCausale(String value) {
        this.causale = value;
    }

    /**
     * Recupera il valore della proprietà data.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public Date getData() {
        return data;
    }

    /**
     * Imposta il valore della proprietà data.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setData(Date value) {
        this.data = value;
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
     * Recupera il valore della proprietà numero.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getNumero() {
        return numero;
    }

    /**
     * Imposta il valore della proprietà numero.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setNumero(Integer value) {
        this.numero = value;
    }

    /**
     * Recupera il valore della proprietà tipoProvvisorioDiCassa.
     * 
     * @return
     *     possible object is
     *     {@link TipoProvvisorioDiCassa }
     *     
     */
    public TipoProvvisorioDiCassa getTipoProvvisorioDiCassa() {
        return tipoProvvisorioDiCassa;
    }

    /**
     * Imposta il valore della proprietà tipoProvvisorioDiCassa.
     * 
     * @param value
     *     allowed object is
     *     {@link TipoProvvisorioDiCassa }
     *     
     */
    public void setTipoProvvisorioDiCassa(TipoProvvisorioDiCassa value) {
        this.tipoProvvisorioDiCassa = value;
    }

}
