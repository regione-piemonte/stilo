/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */
package it.csi.siac.ricerche.svc._1;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import it.csi.siac.integ.data._1.OrdinativoPagamento;
import it.csi.siac.integ.data._1.RicercaPaginataResponse;


/**
 * <p>Classe Java per ricercaOrdinativoSpesaResponse complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="ricercaOrdinativoSpesaResponse"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{http://siac.csi.it/integ/data/1.0}ricercaPaginataResponse"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="ordinativiPagamento" type="{http://siac.csi.it/integ/data/1.0}ordinativoPagamento" maxOccurs="unbounded" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/extension&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ricercaOrdinativoSpesaResponse", propOrder = {
    "ordinativiPagamento"
})
public class RicercaOrdinativoSpesaResponse
    extends RicercaPaginataResponse
{

    @XmlElement(nillable = true)
    protected List<OrdinativoPagamento> ordinativiPagamento;

    /**
     * Gets the value of the ordinativiPagamento property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the ordinativiPagamento property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getOrdinativiPagamento().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link OrdinativoPagamento }
     * 
     * 
     */
    public List<OrdinativoPagamento> getOrdinativiPagamento() {
        if (ordinativiPagamento == null) {
            ordinativiPagamento = new ArrayList<OrdinativoPagamento>();
        }
        return this.ordinativiPagamento;
    }

}
