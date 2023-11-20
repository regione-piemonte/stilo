/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */
package it.csi.siac.ricerche.svc._1;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import it.csi.siac.integ.data._1.Impegno;
import it.csi.siac.integ.data._1.RicercaPaginataResponse;


/**
 * <p>Classe Java per ricercaImpegnoResponse complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="ricercaImpegnoResponse"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{http://siac.csi.it/integ/data/1.0}ricercaPaginataResponse"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="impegni" type="{http://siac.csi.it/integ/data/1.0}impegno" maxOccurs="unbounded" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/extension&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ricercaImpegnoResponse", propOrder = {
    "impegni"
})
public class RicercaImpegnoResponse
    extends RicercaPaginataResponse
{

    @XmlElement(nillable = true)
    protected List<Impegno> impegni;

    /**
     * Gets the value of the impegni property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the impegni property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getImpegni().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Impegno }
     * 
     * 
     */
    public List<Impegno> getImpegni() {
        if (impegni == null) {
            impegni = new ArrayList<Impegno>();
        }
        return this.impegni;
    }

}
