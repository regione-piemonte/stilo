/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */
package it.csi.siac.integ.data._1;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per capitoloUscitaGestione complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="capitoloUscitaGestione"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{http://siac.csi.it/integ/data/1.0}capitolo"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="importiUG" type="{http://siac.csi.it/integ/data/1.0}importoCapitoloUscitaGestione" maxOccurs="unbounded" minOccurs="0"/&gt;
 *         &lt;element name="macroaggregato" type="{http://siac.csi.it/integ/data/1.0}macroaggregato" minOccurs="0"/&gt;
 *         &lt;element name="missione" type="{http://siac.csi.it/integ/data/1.0}missione" minOccurs="0"/&gt;
 *         &lt;element name="programma" type="{http://siac.csi.it/integ/data/1.0}programma" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/extension&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "capitoloUscitaGestione", propOrder = {
    "importiUG",
    "macroaggregato",
    "missione",
    "programma"
})
public class CapitoloUscitaGestione
    extends Capitolo
{

    @XmlElement(nillable = true)
    protected List<ImportoCapitoloUscitaGestione> importiUG;
    protected Macroaggregato macroaggregato;
    protected Missione missione;
    protected Programma programma;

    /**
     * Gets the value of the importiUG property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the importiUG property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getImportiUG().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ImportoCapitoloUscitaGestione }
     * 
     * 
     */
    public List<ImportoCapitoloUscitaGestione> getImportiUG() {
        if (importiUG == null) {
            importiUG = new ArrayList<ImportoCapitoloUscitaGestione>();
        }
        return this.importiUG;
    }

    /**
     * Recupera il valore della proprietà macroaggregato.
     * 
     * @return
     *     possible object is
     *     {@link Macroaggregato }
     *     
     */
    public Macroaggregato getMacroaggregato() {
        return macroaggregato;
    }

    /**
     * Imposta il valore della proprietà macroaggregato.
     * 
     * @param value
     *     allowed object is
     *     {@link Macroaggregato }
     *     
     */
    public void setMacroaggregato(Macroaggregato value) {
        this.macroaggregato = value;
    }

    /**
     * Recupera il valore della proprietà missione.
     * 
     * @return
     *     possible object is
     *     {@link Missione }
     *     
     */
    public Missione getMissione() {
        return missione;
    }

    /**
     * Imposta il valore della proprietà missione.
     * 
     * @param value
     *     allowed object is
     *     {@link Missione }
     *     
     */
    public void setMissione(Missione value) {
        this.missione = value;
    }

    /**
     * Recupera il valore della proprietà programma.
     * 
     * @return
     *     possible object is
     *     {@link Programma }
     *     
     */
    public Programma getProgramma() {
        return programma;
    }

    /**
     * Imposta il valore della proprietà programma.
     * 
     * @param value
     *     allowed object is
     *     {@link Programma }
     *     
     */
    public void setProgramma(Programma value) {
        this.programma = value;
    }

}
