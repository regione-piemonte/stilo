/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */
package it.csi.siac.ricerche.svc._1;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;
import it.csi.siac.integ.data._1.Stato;


/**
 * <p>Classe Java per ricercaSinteticaSoggetti complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="ricercaSinteticaSoggetti"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{http://siac.csi.it/ricerche/svc/1.0}baseRicercaSoggetti"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="denominazione" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="stato" type="{http://siac.csi.it/integ/data/1.0}stato" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/extension&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ricercaSinteticaSoggetti", propOrder = {
    "denominazione",
    "stato"
})
public class RicercaSinteticaSoggetti
    extends BaseRicercaSoggetti
{

    protected String denominazione;
    protected Stato stato;

    /**
     * Recupera il valore della proprietà denominazione.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDenominazione() {
        return denominazione;
    }

    /**
     * Imposta il valore della proprietà denominazione.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDenominazione(String value) {
        this.denominazione = value;
    }

    /**
     * Recupera il valore della proprietà stato.
     * 
     * @return
     *     possible object is
     *     {@link Stato }
     *     
     */
    public Stato getStato() {
        return stato;
    }

    /**
     * Imposta il valore della proprietà stato.
     * 
     * @param value
     *     allowed object is
     *     {@link Stato }
     *     
     */
    public void setStato(Stato value) {
        this.stato = value;
    }

}
