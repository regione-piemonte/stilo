/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */
package it.csi.siac.ricerche.svc._1;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import it.csi.siac.integ.data._1.OrdinativoIncasso;
import it.csi.siac.integ.data._1.RicercaPaginataResponse;


/**
 * <p>Classe Java per ricercaOrdinativoIncassoResponse complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="ricercaOrdinativoIncassoResponse"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{http://siac.csi.it/integ/data/1.0}ricercaPaginataResponse"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="ordinativiIncasso" type="{http://siac.csi.it/integ/data/1.0}ordinativoIncasso" maxOccurs="unbounded" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/extension&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ricercaOrdinativoIncassoResponse", propOrder = {
    "ordinativiIncasso"
})
public class RicercaOrdinativoIncassoResponse
    extends RicercaPaginataResponse
{

    @XmlElement(nillable = true)
    protected List<OrdinativoIncasso> ordinativiIncasso;

    /**
     * Gets the value of the ordinativiIncasso property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the ordinativiIncasso property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getOrdinativiIncasso().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link OrdinativoIncasso }
     * 
     * 
     */
    public List<OrdinativoIncasso> getOrdinativiIncasso() {
        if (ordinativiIncasso == null) {
            ordinativiIncasso = new ArrayList<OrdinativoIncasso>();
        }
        return this.ordinativiIncasso;
    }

}
