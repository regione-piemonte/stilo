/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */
package it.csi.siac.integ.data._1;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per importo complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="importo"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{http://siac.csi.it/integ/data/1.0}entitaBase"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="annoCompetenza" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/extension&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "importo", propOrder = {
    "annoCompetenza"
})
@XmlSeeAlso({
    ImportoCapitoloEntrataGestione.class,
    ImportoCapitoloUscitaGestione.class
})
public class Importo
    extends EntitaBase
{

    protected Integer annoCompetenza;

    /**
     * Recupera il valore della proprietà annoCompetenza.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getAnnoCompetenza() {
        return annoCompetenza;
    }

    /**
     * Imposta il valore della proprietà annoCompetenza.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setAnnoCompetenza(Integer value) {
        this.annoCompetenza = value;
    }

}
