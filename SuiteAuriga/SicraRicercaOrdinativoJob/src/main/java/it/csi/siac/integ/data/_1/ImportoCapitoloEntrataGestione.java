/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */
package it.csi.siac.integ.data._1;

import java.math.BigDecimal;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per importoCapitoloEntrataGestione complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="importoCapitoloEntrataGestione"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{http://siac.csi.it/integ/data/1.0}importo"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="disponibilitaAccertare" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/&gt;
 *         &lt;element name="stanziamento" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/&gt;
 *         &lt;element name="stanziamentoCassa" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/&gt;
 *         &lt;element name="stanziamentoResiduo" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/extension&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "importoCapitoloEntrataGestione", propOrder = {
    "disponibilitaAccertare",
    "stanziamento",
    "stanziamentoCassa",
    "stanziamentoResiduo"
})
public class ImportoCapitoloEntrataGestione
    extends Importo
{

    protected BigDecimal disponibilitaAccertare;
    protected BigDecimal stanziamento;
    protected BigDecimal stanziamentoCassa;
    protected BigDecimal stanziamentoResiduo;

    /**
     * Recupera il valore della proprietà disponibilitaAccertare.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getDisponibilitaAccertare() {
        return disponibilitaAccertare;
    }

    /**
     * Imposta il valore della proprietà disponibilitaAccertare.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setDisponibilitaAccertare(BigDecimal value) {
        this.disponibilitaAccertare = value;
    }

    /**
     * Recupera il valore della proprietà stanziamento.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getStanziamento() {
        return stanziamento;
    }

    /**
     * Imposta il valore della proprietà stanziamento.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setStanziamento(BigDecimal value) {
        this.stanziamento = value;
    }

    /**
     * Recupera il valore della proprietà stanziamentoCassa.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getStanziamentoCassa() {
        return stanziamentoCassa;
    }

    /**
     * Imposta il valore della proprietà stanziamentoCassa.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setStanziamentoCassa(BigDecimal value) {
        this.stanziamentoCassa = value;
    }

    /**
     * Recupera il valore della proprietà stanziamentoResiduo.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getStanziamentoResiduo() {
        return stanziamentoResiduo;
    }

    /**
     * Imposta il valore della proprietà stanziamentoResiduo.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setStanziamentoResiduo(BigDecimal value) {
        this.stanziamentoResiduo = value;
    }

}
