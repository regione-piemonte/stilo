/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */
package it.csi.siac.integ.data._1;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per ordinativoPagamento complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="ordinativoPagamento"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{http://siac.csi.it/integ/data/1.0}ordinativo"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="quotePagamento" type="{http://siac.csi.it/integ/data/1.0}subOrdinativoPagamento" maxOccurs="unbounded" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/extension&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ordinativoPagamento", propOrder = {
    "quotePagamento"
})
public class OrdinativoPagamento
    extends Ordinativo
{

    @XmlElement(nillable = true)
    protected List<SubOrdinativoPagamento> quotePagamento;

    /**
     * Gets the value of the quotePagamento property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the quotePagamento property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getQuotePagamento().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link SubOrdinativoPagamento }
     * 
     * 
     */
    public List<SubOrdinativoPagamento> getQuotePagamento() {
        if (quotePagamento == null) {
            quotePagamento = new ArrayList<SubOrdinativoPagamento>();
        }
        return this.quotePagamento;
    }

}
