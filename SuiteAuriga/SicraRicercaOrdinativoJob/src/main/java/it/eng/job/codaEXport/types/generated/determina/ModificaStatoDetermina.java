/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

package it.eng.job.codaEXport.types.generated.determina;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per anonymous complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="chiaveDetermina" type="{http:/www.csi.it/atm2bu/stiloatti/xmlatti}ChiaveDetermina"/>
 *         &lt;element name="nuovoStato">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               &lt;enumeration value="CHIUSO"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "chiaveDetermina",
    "nuovoStato"
})
@XmlRootElement(name = "ModificaStatoDetermina")
public class ModificaStatoDetermina {

    @XmlElement(required = true)
    protected ChiaveDetermina chiaveDetermina;
    @XmlElement(required = true)
    protected String nuovoStato;

    /**
     * Recupera il valore della propriet� chiaveDetermina.
     * 
     * @return
     *     possible object is
     *     {@link ChiaveDetermina }
     *     
     */
    public ChiaveDetermina getChiaveDetermina() {
        return chiaveDetermina;
    }

    /**
     * Imposta il valore della propriet� chiaveDetermina.
     * 
     * @param value
     *     allowed object is
     *     {@link ChiaveDetermina }
     *     
     */
    public void setChiaveDetermina(ChiaveDetermina value) {
        this.chiaveDetermina = value;
    }

    /**
     * Recupera il valore della propriet� nuovoStato.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNuovoStato() {
        return nuovoStato;
    }

    /**
     * Imposta il valore della propriet� nuovoStato.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNuovoStato(String value) {
        this.nuovoStato = value;
    }

}
