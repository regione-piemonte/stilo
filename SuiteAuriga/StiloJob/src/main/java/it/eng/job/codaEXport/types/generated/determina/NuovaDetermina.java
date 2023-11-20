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
 *         &lt;element name="determina" type="{http:/www.csi.it/atm2bu/stiloatti/xmlatti}Determina"/>
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
    "determina"
})
@XmlRootElement(name = "NuovaDetermina")
public class NuovaDetermina {

    @XmlElement(required = true)
    protected Determina determina;

    /**
     * Recupera il valore della propriet� determina.
     * 
     * @return
     *     possible object is
     *     {@link Determina }
     *     
     */
    public Determina getDetermina() {
        return determina;
    }

    /**
     * Imposta il valore della propriet� determina.
     * 
     * @param value
     *     allowed object is
     *     {@link Determina }
     *     
     */
    public void setDetermina(Determina value) {
        this.determina = value;
    }

}
