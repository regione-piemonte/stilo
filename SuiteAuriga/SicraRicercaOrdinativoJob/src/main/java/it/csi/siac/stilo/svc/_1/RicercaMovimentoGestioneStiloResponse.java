/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */
package it.csi.siac.stilo.svc._1;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import it.csi.siac.integ.data._1.BaseResponse;
import it.csi.siac.integ.data._1.MovimentoGestioneStilo;


/**
 * <p>Classe Java per ricercaMovimentoGestioneStiloResponse complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="ricercaMovimentoGestioneStiloResponse"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{http://siac.csi.it/integ/data/1.0}baseResponse"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="movimentiGestione" type="{http://siac.csi.it/integ/data/1.0}movimentoGestioneStilo" maxOccurs="unbounded" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/extension&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ricercaMovimentoGestioneStiloResponse", propOrder = {
    "movimentiGestione"
})
public class RicercaMovimentoGestioneStiloResponse
    extends BaseResponse
{

    @XmlElement(nillable = true)
    protected List<MovimentoGestioneStilo> movimentiGestione;

    /**
     * Gets the value of the movimentiGestione property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the movimentiGestione property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getMovimentiGestione().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link MovimentoGestioneStilo }
     * 
     * 
     */
    public List<MovimentoGestioneStilo> getMovimentiGestione() {
        if (movimentiGestione == null) {
            movimentiGestione = new ArrayList<MovimentoGestioneStilo>();
        }
        return this.movimentiGestione;
    }

}
