/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

package it.eng.job.codaEXport.types.generated.determina;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per DetVariata complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="DetVariata">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="chiaveDetermVariata" type="{http:/www.csi.it/atm2bu/stiloatti/xmlatti}ChiaveDetermina"/>
 *         &lt;element name="identificativo">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               &lt;maxLength value="8"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="codiceVariazione">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}int">
 *               &lt;enumeration value="0"/>
 *               &lt;enumeration value="1"/>
 *               &lt;enumeration value="2"/>
 *               &lt;enumeration value="3"/>
 *               &lt;enumeration value="4"/>
 *               &lt;enumeration value="5"/>
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
@XmlType(name = "DetVariata", propOrder = {
    "chiaveDetermVariata",
    "identificativo",
    "codiceVariazione"
})
public class DetVariata {

    @XmlElement(required = true)
    protected ChiaveDetermina chiaveDetermVariata;
    @XmlElement(required = true)
    protected String identificativo;
    protected int codiceVariazione;

    /**
     * Recupera il valore della propriet� chiaveDetermVariata.
     * 
     * @return
     *     possible object is
     *     {@link ChiaveDetermina }
     *     
     */
    public ChiaveDetermina getChiaveDetermVariata() {
        return chiaveDetermVariata;
    }

    /**
     * Imposta il valore della propriet� chiaveDetermVariata.
     * 
     * @param value
     *     allowed object is
     *     {@link ChiaveDetermina }
     *     
     */
    public void setChiaveDetermVariata(ChiaveDetermina value) {
        this.chiaveDetermVariata = value;
    }

    /**
     * Recupera il valore della propriet� identificativo.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIdentificativo() {
        return identificativo;
    }

    /**
     * Imposta il valore della propriet� identificativo.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIdentificativo(String value) {
        this.identificativo = value;
    }

    /**
     * Recupera il valore della propriet� codiceVariazione.
     * 
     */
    public int getCodiceVariazione() {
        return codiceVariazione;
    }

    /**
     * Imposta il valore della propriet� codiceVariazione.
     * 
     */
    public void setCodiceVariazione(int value) {
        this.codiceVariazione = value;
    }

}
