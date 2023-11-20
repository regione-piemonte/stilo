/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */
package it.csi.siac.ricerche.svc._1;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import it.csi.siac.integ.data._1.BaseResponse;
import it.csi.siac.integ.data._1.MandatoDiPagamento;


/**
 * <p>Classe Java per ricercaEstesaOrdinativiSpesaResponse complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="ricercaEstesaOrdinativiSpesaResponse"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{http://siac.csi.it/integ/data/1.0}baseResponse"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="mandatiDiPagamento" type="{http://siac.csi.it/integ/data/1.0}mandatoDiPagamento" maxOccurs="unbounded" minOccurs="0"/&gt;
 *         &lt;element name="numeroTotaleOrdinativiSpesaTrovati" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/extension&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ricercaEstesaOrdinativiSpesaResponse", propOrder = {
    "mandatiDiPagamento",
    "numeroTotaleOrdinativiSpesaTrovati"
})
public class RicercaEstesaOrdinativiSpesaResponse
    extends BaseResponse
{

    @XmlElement(nillable = true)
    protected List<MandatoDiPagamento> mandatiDiPagamento;
    protected Integer numeroTotaleOrdinativiSpesaTrovati;

    /**
     * Gets the value of the mandatiDiPagamento property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the mandatiDiPagamento property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getMandatiDiPagamento().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link MandatoDiPagamento }
     * 
     * 
     */
    public List<MandatoDiPagamento> getMandatiDiPagamento() {
        if (mandatiDiPagamento == null) {
            mandatiDiPagamento = new ArrayList<MandatoDiPagamento>();
        }
        return this.mandatiDiPagamento;
    }

    /**
     * Recupera il valore della proprietà numeroTotaleOrdinativiSpesaTrovati.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getNumeroTotaleOrdinativiSpesaTrovati() {
        return numeroTotaleOrdinativiSpesaTrovati;
    }

    /**
     * Imposta il valore della proprietà numeroTotaleOrdinativiSpesaTrovati.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setNumeroTotaleOrdinativiSpesaTrovati(Integer value) {
        this.numeroTotaleOrdinativiSpesaTrovati = value;
    }

}
