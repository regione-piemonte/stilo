/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */
package it.csi.siac.integ.data._1;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per accertamento complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="accertamento"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{http://siac.csi.it/integ/data/1.0}movimentoGestione"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="annoAccertamento" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/&gt;
 *         &lt;element name="annoAccertamentoOrigine" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/&gt;
 *         &lt;element name="annoAccertamentoRiaccertato" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/&gt;
 *         &lt;element name="disponibilitaIncassare" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/&gt;
 *         &lt;element name="numAccertamentoOrigine" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/&gt;
 *         &lt;element name="numAccertamentoRiaccertato" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/&gt;
 *         &lt;element name="numeroAccertamento" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/&gt;
 *         &lt;element name="subAccertamenti" type="{http://siac.csi.it/integ/data/1.0}subAccertamento" maxOccurs="unbounded" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/extension&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "accertamento", propOrder = {
    "annoAccertamento",
    "annoAccertamentoOrigine",
    "annoAccertamentoRiaccertato",
    "disponibilitaIncassare",
    "numAccertamentoOrigine",
    "numAccertamentoRiaccertato",
    "numeroAccertamento",
    "subAccertamenti"
})
public class Accertamento
    extends MovimentoGestione
{

    protected Integer annoAccertamento;
    protected Integer annoAccertamentoOrigine;
    protected Integer annoAccertamentoRiaccertato;
    protected BigDecimal disponibilitaIncassare;
    protected Integer numAccertamentoOrigine;
    protected Integer numAccertamentoRiaccertato;
    protected Integer numeroAccertamento;
    @XmlElement(nillable = true)
    protected List<SubAccertamento> subAccertamenti;

    /**
     * Recupera il valore della proprietà annoAccertamento.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getAnnoAccertamento() {
        return annoAccertamento;
    }

    /**
     * Imposta il valore della proprietà annoAccertamento.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setAnnoAccertamento(Integer value) {
        this.annoAccertamento = value;
    }

    /**
     * Recupera il valore della proprietà annoAccertamentoOrigine.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getAnnoAccertamentoOrigine() {
        return annoAccertamentoOrigine;
    }

    /**
     * Imposta il valore della proprietà annoAccertamentoOrigine.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setAnnoAccertamentoOrigine(Integer value) {
        this.annoAccertamentoOrigine = value;
    }

    /**
     * Recupera il valore della proprietà annoAccertamentoRiaccertato.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getAnnoAccertamentoRiaccertato() {
        return annoAccertamentoRiaccertato;
    }

    /**
     * Imposta il valore della proprietà annoAccertamentoRiaccertato.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setAnnoAccertamentoRiaccertato(Integer value) {
        this.annoAccertamentoRiaccertato = value;
    }

    /**
     * Recupera il valore della proprietà disponibilitaIncassare.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getDisponibilitaIncassare() {
        return disponibilitaIncassare;
    }

    /**
     * Imposta il valore della proprietà disponibilitaIncassare.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setDisponibilitaIncassare(BigDecimal value) {
        this.disponibilitaIncassare = value;
    }

    /**
     * Recupera il valore della proprietà numAccertamentoOrigine.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getNumAccertamentoOrigine() {
        return numAccertamentoOrigine;
    }

    /**
     * Imposta il valore della proprietà numAccertamentoOrigine.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setNumAccertamentoOrigine(Integer value) {
        this.numAccertamentoOrigine = value;
    }

    /**
     * Recupera il valore della proprietà numAccertamentoRiaccertato.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getNumAccertamentoRiaccertato() {
        return numAccertamentoRiaccertato;
    }

    /**
     * Imposta il valore della proprietà numAccertamentoRiaccertato.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setNumAccertamentoRiaccertato(Integer value) {
        this.numAccertamentoRiaccertato = value;
    }

    /**
     * Recupera il valore della proprietà numeroAccertamento.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getNumeroAccertamento() {
        return numeroAccertamento;
    }

    /**
     * Imposta il valore della proprietà numeroAccertamento.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setNumeroAccertamento(Integer value) {
        this.numeroAccertamento = value;
    }

    /**
     * Gets the value of the subAccertamenti property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the subAccertamenti property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getSubAccertamenti().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link SubAccertamento }
     * 
     * 
     */
    public List<SubAccertamento> getSubAccertamenti() {
        if (subAccertamenti == null) {
            subAccertamenti = new ArrayList<SubAccertamento>();
        }
        return this.subAccertamenti;
    }

}
