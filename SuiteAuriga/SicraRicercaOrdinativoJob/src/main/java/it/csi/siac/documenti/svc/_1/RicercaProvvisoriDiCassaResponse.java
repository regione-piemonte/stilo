/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */
package it.csi.siac.documenti.svc._1;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import it.csi.siac.integ.data._1.RicercaPaginataResponse;
import it.csi.siac.ricerche.data._1.ProvvisorioDiCassa;


/**
 * <p>Classe Java per ricercaProvvisoriDiCassaResponse complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="ricercaProvvisoriDiCassaResponse"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{http://siac.csi.it/integ/data/1.0}ricercaPaginataResponse"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="provvisoriDiCassa" type="{http://siac.csi.it/ricerche/data/1.0}provvisorioDiCassa" maxOccurs="unbounded" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/extension&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ricercaProvvisoriDiCassaResponse", propOrder = {
    "provvisoriDiCassa"
})
public class RicercaProvvisoriDiCassaResponse
    extends RicercaPaginataResponse
{

    @XmlElement(nillable = true)
    protected List<ProvvisorioDiCassa> provvisoriDiCassa;

    /**
     * Gets the value of the provvisoriDiCassa property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the provvisoriDiCassa property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getProvvisoriDiCassa().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ProvvisorioDiCassa }
     * 
     * 
     */
    public List<ProvvisorioDiCassa> getProvvisoriDiCassa() {
        if (provvisoriDiCassa == null) {
            provvisoriDiCassa = new ArrayList<ProvvisorioDiCassa>();
        }
        return this.provvisoriDiCassa;
    }

}
