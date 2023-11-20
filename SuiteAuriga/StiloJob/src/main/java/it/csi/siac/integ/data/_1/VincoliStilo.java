/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */
package it.csi.siac.integ.data._1;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per vincoliStilo complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="vincoliStilo"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="vincolo" type="{http://siac.csi.it/integ/data/1.0}vincoloImpegnoStilo" maxOccurs="unbounded" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "vincoliStilo", propOrder = {
    "vincolo"
})
public class VincoliStilo {

    @XmlElement(nillable = true)
    protected List<VincoloImpegnoStilo> vincolo;

    /**
     * Gets the value of the vincolo property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the vincolo property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getVincolo().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link VincoloImpegnoStilo }
     * 
     * 
     */
    public List<VincoloImpegnoStilo> getVincolo() {
        if (vincolo == null) {
            vincolo = new ArrayList<VincoloImpegnoStilo>();
        }
        return this.vincolo;
    }

}
