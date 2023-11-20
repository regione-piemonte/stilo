/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */
package it.doqui.acta.acaris.backoffice;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per EntePropertiesType complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="EntePropertiesType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{backoffice.acaris.acta.doqui.it}OrganizationUnitPropertiesType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="abilitato" type="{backoffice.acaris.acta.doqui.it}AbilitatoType"/&gt;
 *         &lt;element name="tipologiaEnte" type="{backoffice.acaris.acta.doqui.it}enumTipologiaEnteType"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/extension&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "EntePropertiesType", propOrder = {
    "abilitato",
    "tipologiaEnte"
})
public class EntePropertiesType
    extends OrganizationUnitPropertiesType
{

    protected boolean abilitato;
    @XmlElement(required = true)
    @XmlSchemaType(name = "string")
    protected EnumTipologiaEnteType tipologiaEnte;

    /**
     * Recupera il valore della proprietà abilitato.
     * 
     */
    public boolean isAbilitato() {
        return abilitato;
    }

    /**
     * Imposta il valore della proprietà abilitato.
     * 
     */
    public void setAbilitato(boolean value) {
        this.abilitato = value;
    }

    /**
     * Recupera il valore della proprietà tipologiaEnte.
     * 
     * @return
     *     possible object is
     *     {@link EnumTipologiaEnteType }
     *     
     */
    public EnumTipologiaEnteType getTipologiaEnte() {
        return tipologiaEnte;
    }

    /**
     * Imposta il valore della proprietà tipologiaEnte.
     * 
     * @param value
     *     allowed object is
     *     {@link EnumTipologiaEnteType }
     *     
     */
    public void setTipologiaEnte(EnumTipologiaEnteType value) {
        this.tipologiaEnte = value;
    }

}
