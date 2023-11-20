/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */
package it.eng.job.codaEXport.types.generated.determina;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per ChiaveDetermina complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="ChiaveDetermina">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;attribute name="anno">
 *         &lt;simpleType>
 *           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}int">
 *             &lt;pattern value="\d{4}"/>
 *           &lt;/restriction>
 *         &lt;/simpleType>
 *       &lt;/attribute>
 *       &lt;attribute name="numDeterm">
 *         &lt;simpleType>
 *           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}int">
 *             &lt;pattern value="\d{1,5}"/>
 *           &lt;/restriction>
 *         &lt;/simpleType>
 *       &lt;/attribute>
 *       &lt;attribute name="codDir">
 *         &lt;simpleType>
 *           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *             &lt;pattern value="[\w.-]{2,10}"/>
 *           &lt;/restriction>
 *         &lt;/simpleType>
 *       &lt;/attribute>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ChiaveDetermina")
public class ChiaveDetermina {

    @XmlAttribute(name = "anno")
    protected Integer anno;
    @XmlAttribute(name = "numDeterm")
    protected Integer numDeterm;
    @XmlAttribute(name = "codDir")
    protected String codDir;

    /**
     * Recupera il valore della propriet� anno.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getAnno() {
        return anno;
    }

    /**
     * Imposta il valore della propriet� anno.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setAnno(Integer value) {
        this.anno = value;
    }

    /**
     * Recupera il valore della propriet� numDeterm.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getNumDeterm() {
        return numDeterm;
    }

    /**
     * Imposta il valore della propriet� numDeterm.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setNumDeterm(Integer value) {
        this.numDeterm = value;
    }

    /**
     * Recupera il valore della propriet� codDir.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCodDir() {
        return codDir;
    }

    /**
     * Imposta il valore della propriet� codDir.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCodDir(String value) {
        this.codDir = value;
    }

}
