/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */
package it.csi.siac.integ.data._1;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per ordinativoIncasso complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="ordinativoIncasso"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{http://siac.csi.it/integ/data/1.0}ordinativo"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="quoteIncasso" type="{http://siac.csi.it/integ/data/1.0}subOrdinativoIncasso" maxOccurs="unbounded" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/extension&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ordinativoIncasso", propOrder = {
    "quoteIncasso"
})
public class OrdinativoIncasso
    extends Ordinativo
{

    @XmlElement(nillable = true)
    protected List<SubOrdinativoIncasso> quoteIncasso;

    /**
     * Gets the value of the quoteIncasso property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the quoteIncasso property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getQuoteIncasso().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link SubOrdinativoIncasso }
     * 
     * 
     */
    public List<SubOrdinativoIncasso> getQuoteIncasso() {
        if (quoteIncasso == null) {
            quoteIncasso = new ArrayList<SubOrdinativoIncasso>();
        }
        return this.quoteIncasso;
    }

}
