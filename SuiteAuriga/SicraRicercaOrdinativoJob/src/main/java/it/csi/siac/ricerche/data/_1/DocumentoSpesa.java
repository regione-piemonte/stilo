/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */
package it.csi.siac.ricerche.data._1;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per documentoSpesa complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="documentoSpesa"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{http://siac.csi.it/ricerche/data/1.0}documento"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="elencoOrdini" type="{http://siac.csi.it/ricerche/data/1.0}ordine" maxOccurs="unbounded" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/extension&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "documentoSpesa", propOrder = {
    "elencoOrdini"
})
public class DocumentoSpesa
    extends Documento
{

    @XmlElement(nillable = true)
    protected List<Ordine> elencoOrdini;

    /**
     * Gets the value of the elencoOrdini property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the elencoOrdini property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getElencoOrdini().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Ordine }
     * 
     * 
     */
    public List<Ordine> getElencoOrdini() {
        if (elencoOrdini == null) {
            elencoOrdini = new ArrayList<Ordine>();
        }
        return this.elencoOrdini;
    }

}
