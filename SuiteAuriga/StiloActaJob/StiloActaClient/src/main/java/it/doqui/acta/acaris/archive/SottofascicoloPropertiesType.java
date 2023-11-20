/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */
package it.doqui.acta.acaris.archive;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per SottofascicoloPropertiesType complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="SottofascicoloPropertiesType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{archive.acaris.acta.doqui.it}AggregazionePropertiesType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="creatoFascStd" type="{archive.acaris.acta.doqui.it}CreatoFascStdType"/&gt;
 *         &lt;element name="stato" type="{archive.acaris.acta.doqui.it}enumSottofascicoloStatoType"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/extension&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "SottofascicoloPropertiesType", propOrder = {
    "creatoFascStd",
    "stato"
})
public class SottofascicoloPropertiesType
    extends AggregazionePropertiesType
{

    protected boolean creatoFascStd;
    @XmlElement(required = true)
    @XmlSchemaType(name = "string")
    protected EnumSottofascicoloStatoType stato;

    /**
     * Recupera il valore della proprietà creatoFascStd.
     * 
     */
    public boolean isCreatoFascStd() {
        return creatoFascStd;
    }

    /**
     * Imposta il valore della proprietà creatoFascStd.
     * 
     */
    public void setCreatoFascStd(boolean value) {
        this.creatoFascStd = value;
    }

    /**
     * Recupera il valore della proprietà stato.
     * 
     * @return
     *     possible object is
     *     {@link EnumSottofascicoloStatoType }
     *     
     */
    public EnumSottofascicoloStatoType getStato() {
        return stato;
    }

    /**
     * Imposta il valore della proprietà stato.
     * 
     * @param value
     *     allowed object is
     *     {@link EnumSottofascicoloStatoType }
     *     
     */
    public void setStato(EnumSottofascicoloStatoType value) {
        this.stato = value;
    }

}
