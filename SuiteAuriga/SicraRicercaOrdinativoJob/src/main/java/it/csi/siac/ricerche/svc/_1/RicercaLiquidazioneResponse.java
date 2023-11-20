/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */
package it.csi.siac.ricerche.svc._1;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import it.csi.siac.integ.data._1.Liquidazione;
import it.csi.siac.integ.data._1.RicercaPaginataResponse;


/**
 * <p>Classe Java per ricercaLiquidazioneResponse complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="ricercaLiquidazioneResponse"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{http://siac.csi.it/integ/data/1.0}ricercaPaginataResponse"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="liquidazioni" type="{http://siac.csi.it/integ/data/1.0}liquidazione" maxOccurs="unbounded" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/extension&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ricercaLiquidazioneResponse", propOrder = {
    "liquidazioni"
})
public class RicercaLiquidazioneResponse
    extends RicercaPaginataResponse
{

    @XmlElement(nillable = true)
    protected List<Liquidazione> liquidazioni;

    /**
     * Gets the value of the liquidazioni property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the liquidazioni property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getLiquidazioni().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Liquidazione }
     * 
     * 
     */
    public List<Liquidazione> getLiquidazioni() {
        if (liquidazioni == null) {
            liquidazioni = new ArrayList<Liquidazione>();
        }
        return this.liquidazioni;
    }

}
