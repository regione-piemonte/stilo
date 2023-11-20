/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */
package it.csi.siac.ricerche.svc._1;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;
import it.csi.siac.integ.data._1.BaseRicercaResponse;
import it.csi.siac.integ.data._1.Soggetto;


/**
 * <p>Classe Java per baseRicercaSoggettiResponse complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="baseRicercaSoggettiResponse"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{http://siac.csi.it/integ/data/1.0}baseRicercaResponse"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="soggetti" type="{http://siac.csi.it/integ/data/1.0}soggetto" maxOccurs="unbounded" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/extension&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "baseRicercaSoggettiResponse", propOrder = {
    "soggetti"
})
@XmlSeeAlso({
    RicercaSinteticaSoggettiResponse.class,
    RicercaDettaglioSoggettiResponse.class
})
public abstract class BaseRicercaSoggettiResponse
    extends BaseRicercaResponse
{

    @XmlElement(nillable = true)
    protected List<Soggetto> soggetti;

    /**
     * Gets the value of the soggetti property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the soggetti property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getSoggetti().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Soggetto }
     * 
     * 
     */
    public List<Soggetto> getSoggetti() {
        if (soggetti == null) {
            soggetti = new ArrayList<Soggetto>();
        }
        return this.soggetti;
    }

}
