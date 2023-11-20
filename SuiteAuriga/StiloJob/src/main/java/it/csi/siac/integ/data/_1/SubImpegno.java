/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */
package it.csi.siac.integ.data._1;

import java.math.BigDecimal;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per subImpegno complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="subImpegno"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{http://siac.csi.it/integ/data/1.0}movimentoGestione"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="annoSubImpegno" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/&gt;
 *         &lt;element name="disponibilitaLiquidare" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/&gt;
 *         &lt;element name="numeroSubImpegno" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/extension&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "subImpegno", propOrder = {
    "annoSubImpegno",
    "disponibilitaLiquidare",
    "numeroSubImpegno"
})
public class SubImpegno
    extends MovimentoGestione
{

    protected Integer annoSubImpegno;
    protected BigDecimal disponibilitaLiquidare;
    protected Integer numeroSubImpegno;

    /**
     * Recupera il valore della proprietà annoSubImpegno.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getAnnoSubImpegno() {
        return annoSubImpegno;
    }

    /**
     * Imposta il valore della proprietà annoSubImpegno.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setAnnoSubImpegno(Integer value) {
        this.annoSubImpegno = value;
    }

    /**
     * Recupera il valore della proprietà disponibilitaLiquidare.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getDisponibilitaLiquidare() {
        return disponibilitaLiquidare;
    }

    /**
     * Imposta il valore della proprietà disponibilitaLiquidare.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setDisponibilitaLiquidare(BigDecimal value) {
        this.disponibilitaLiquidare = value;
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

}
