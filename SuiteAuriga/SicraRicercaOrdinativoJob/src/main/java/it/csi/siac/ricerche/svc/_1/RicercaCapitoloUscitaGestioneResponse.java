/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */
package it.csi.siac.ricerche.svc._1;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import it.csi.siac.integ.data._1.BaseRicercaResponse;
import it.csi.siac.integ.data._1.CapitoloUscitaGestione;


/**
 * <p>Classe Java per ricercaCapitoloUscitaGestioneResponse complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="ricercaCapitoloUscitaGestioneResponse"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{http://siac.csi.it/integ/data/1.0}baseRicercaResponse"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="capitoliUscitaGestione" type="{http://siac.csi.it/integ/data/1.0}capitoloUscitaGestione" maxOccurs="unbounded" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/extension&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ricercaCapitoloUscitaGestioneResponse", propOrder = {
    "capitoliUscitaGestione"
})
public class RicercaCapitoloUscitaGestioneResponse
    extends BaseRicercaResponse
{

    @XmlElement(nillable = true)
    protected List<CapitoloUscitaGestione> capitoliUscitaGestione;

    /**
     * Gets the value of the capitoliUscitaGestione property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the capitoliUscitaGestione property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getCapitoliUscitaGestione().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link CapitoloUscitaGestione }
     * 
     * 
     */
    public List<CapitoloUscitaGestione> getCapitoliUscitaGestione() {
        if (capitoliUscitaGestione == null) {
            capitoliUscitaGestione = new ArrayList<CapitoloUscitaGestione>();
        }
        return this.capitoliUscitaGestione;
    }

}
