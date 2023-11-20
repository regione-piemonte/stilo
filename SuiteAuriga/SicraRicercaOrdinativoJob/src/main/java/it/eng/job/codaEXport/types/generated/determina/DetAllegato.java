/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

package it.eng.job.codaEXport.types.generated.determina;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per DetAllegato complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="DetAllegato">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="nomeFile">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               &lt;maxLength value="255"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="progressivo" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="uuidFile" type="{http:/www.csi.it/atm2bu/stiloatti/xmlatti}UUIDFile"/>
 *         &lt;element name="estensione" minOccurs="0">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               &lt;enumeration value="D"/>
 *               &lt;enumeration value="E"/>
 *               &lt;enumeration value="W"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="tipo" minOccurs="0">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               &lt;enumeration value="0"/>
 *               &lt;enumeration value="D"/>
 *               &lt;enumeration value="E"/>
 *               &lt;enumeration value="I"/>
 *               &lt;enumeration value="P"/>
 *               &lt;enumeration value="T"/>
 *               &lt;enumeration value="W"/>
 *               &lt;enumeration value="Z"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="pubblicaBollettino" type="{http:/www.csi.it/atm2bu/stiloatti/xmlatti}FlagNumerico"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DetAllegato", propOrder = {
    "nomeFile",
    "progressivo",
    "uuidFile",
    "estensione",
    "tipo",
    "pubblicaBollettino"
})
public class DetAllegato {

    @XmlElement(required = true)
    protected String nomeFile;
    protected Integer progressivo;
    @XmlElement(required = true)
    protected String uuidFile;
    protected String estensione;
    protected String tipo;
    protected int pubblicaBollettino;

    /**
     * Recupera il valore della propriet� nomeFile.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNomeFile() {
        return nomeFile;
    }

    /**
     * Imposta il valore della propriet� nomeFile.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNomeFile(String value) {
        this.nomeFile = value;
    }

    /**
     * Recupera il valore della propriet� progressivo.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getProgressivo() {
        return progressivo;
    }

    /**
     * Imposta il valore della propriet� progressivo.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setProgressivo(Integer value) {
        this.progressivo = value;
    }

    /**
     * Recupera il valore della propriet� uuidFile.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUuidFile() {
        return uuidFile;
    }

    /**
     * Imposta il valore della propriet� uuidFile.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUuidFile(String value) {
        this.uuidFile = value;
    }

    /**
     * Recupera il valore della propriet� estensione.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEstensione() {
        return estensione;
    }

    /**
     * Imposta il valore della propriet� estensione.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEstensione(String value) {
        this.estensione = value;
    }

    /**
     * Recupera il valore della propriet� tipo.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTipo() {
        return tipo;
    }

    /**
     * Imposta il valore della propriet� tipo.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTipo(String value) {
        this.tipo = value;
    }

    /**
     * Recupera il valore della propriet� pubblicaBollettino.
     * 
     */
    public int getPubblicaBollettino() {
        return pubblicaBollettino;
    }

    /**
     * Imposta il valore della propriet� pubblicaBollettino.
     * 
     */
    public void setPubblicaBollettino(int value) {
        this.pubblicaBollettino = value;
    }

}
