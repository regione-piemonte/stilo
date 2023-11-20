/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */
package it.csi.siac.integ.data._1;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per capitoloEntrataGestione complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="capitoloEntrataGestione"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{http://siac.csi.it/integ/data/1.0}capitolo"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="categoria" type="{http://siac.csi.it/integ/data/1.0}categoria" minOccurs="0"/&gt;
 *         &lt;element name="listaImportiCapitoloEG" type="{http://siac.csi.it/integ/data/1.0}importoCapitoloEntrataGestione" maxOccurs="unbounded" minOccurs="0"/&gt;
 *         &lt;element name="tipologia" type="{http://siac.csi.it/integ/data/1.0}tipologia" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/extension&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "capitoloEntrataGestione", propOrder = {
    "categoria",
    "listaImportiCapitoloEG",
    "tipologia"
})
public class CapitoloEntrataGestione
    extends Capitolo
{

    protected Categoria categoria;
    @XmlElement(nillable = true)
    protected List<ImportoCapitoloEntrataGestione> listaImportiCapitoloEG;
    protected Tipologia tipologia;

    /**
     * Recupera il valore della proprietà categoria.
     * 
     * @return
     *     possible object is
     *     {@link Categoria }
     *     
     */
    public Categoria getCategoria() {
        return categoria;
    }

    /**
     * Imposta il valore della proprietà categoria.
     * 
     * @param value
     *     allowed object is
     *     {@link Categoria }
     *     
     */
    public void setCategoria(Categoria value) {
        this.categoria = value;
    }

    /**
     * Gets the value of the listaImportiCapitoloEG property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the listaImportiCapitoloEG property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getListaImportiCapitoloEG().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ImportoCapitoloEntrataGestione }
     * 
     * 
     */
    public List<ImportoCapitoloEntrataGestione> getListaImportiCapitoloEG() {
        if (listaImportiCapitoloEG == null) {
            listaImportiCapitoloEG = new ArrayList<ImportoCapitoloEntrataGestione>();
        }
        return this.listaImportiCapitoloEG;
    }

    /**
     * Recupera il valore della proprietà tipologia.
     * 
     * @return
     *     possible object is
     *     {@link Tipologia }
     *     
     */
    public Tipologia getTipologia() {
        return tipologia;
    }

    /**
     * Imposta il valore della proprietà tipologia.
     * 
     * @param value
     *     allowed object is
     *     {@link Tipologia }
     *     
     */
    public void setTipologia(Tipologia value) {
        this.tipologia = value;
    }

}
