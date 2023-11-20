/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */
package it.csi.siac.ricerche.svc._1;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import it.csi.siac.integ.data._1.Accertamento;
import it.csi.siac.integ.data._1.RicercaPaginataResponse;


/**
 * <p>Classe Java per ricercaAccertamentoResponse complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="ricercaAccertamentoResponse"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{http://siac.csi.it/integ/data/1.0}ricercaPaginataResponse"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="accertamenti" type="{http://siac.csi.it/integ/data/1.0}accertamento" maxOccurs="unbounded" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/extension&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ricercaAccertamentoResponse", propOrder = {
    "accertamenti"
})
public class RicercaAccertamentoResponse
    extends RicercaPaginataResponse
{

    @XmlElement(nillable = true)
    protected List<Accertamento> accertamenti;

    /**
     * Gets the value of the accertamenti property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the accertamenti property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getAccertamenti().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Accertamento }
     * 
     * 
     */
    public List<Accertamento> getAccertamenti() {
        if (accertamenti == null) {
            accertamenti = new ArrayList<Accertamento>();
        }
        return this.accertamenti;
    }

}
