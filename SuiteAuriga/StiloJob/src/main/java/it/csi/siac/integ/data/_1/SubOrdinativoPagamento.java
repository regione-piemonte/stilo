/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */
package it.csi.siac.integ.data._1;

import java.math.BigDecimal;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per subOrdinativoPagamento complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="subOrdinativoPagamento"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{http://siac.csi.it/integ/data/1.0}ordinativo"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="annoImpegno" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/&gt;
 *         &lt;element name="annoLiquidazione" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/&gt;
 *         &lt;element name="importo" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/&gt;
 *         &lt;element name="numeroImpegno" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/&gt;
 *         &lt;element name="numeroLiquidazione" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/extension&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "subOrdinativoPagamento", propOrder = {
    "annoImpegno",
    "annoLiquidazione",
    "importo",
    "numeroImpegno",
    "numeroLiquidazione"
})
public class SubOrdinativoPagamento
    extends Ordinativo
{

    protected Integer annoImpegno;
    protected Integer annoLiquidazione;
    protected BigDecimal importo;
    protected Integer numeroImpegno;
    protected Integer numeroLiquidazione;

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

}
