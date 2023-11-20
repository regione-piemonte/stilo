/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */
package it.csi.siac.ricerche.svc._1;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import it.csi.siac.integ.data._1.BaseRicercaResponse;
import it.csi.siac.integ.data._1.CapitoloEntrataGestione;


/**
 * <p>Classe Java per ricercaCapitoloEntrataGestioneResponse complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="ricercaCapitoloEntrataGestioneResponse"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{http://siac.csi.it/integ/data/1.0}baseRicercaResponse"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="capitoliEntrataGestione" type="{http://siac.csi.it/integ/data/1.0}capitoloEntrataGestione" maxOccurs="unbounded" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/extension&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ricercaCapitoloEntrataGestioneResponse", propOrder = {
    "capitoliEntrataGestione"
})
public class RicercaCapitoloEntrataGestioneResponse
    extends BaseRicercaResponse
{

    @XmlElement(nillable = true)
    protected List<CapitoloEntrataGestione> capitoliEntrataGestione;

    /**
     * Gets the value of the capitoliEntrataGestione property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the capitoliEntrataGestione property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getCapitoliEntrataGestione().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link CapitoloEntrataGestione }
     * 
     * 
     */
    public List<CapitoloEntrataGestione> getCapitoliEntrataGestione() {
        if (capitoliEntrataGestione == null) {
            capitoliEntrataGestione = new ArrayList<CapitoloEntrataGestione>();
        }
        return this.capitoliEntrataGestione;
    }

}
