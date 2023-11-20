/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */
package it.eng.fileOperation.clientws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per InformazioniFirmaGraficaType complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="InformazioniFirmaGraficaType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="testo" type="{http://www.w3.org/2001/XMLSchema}string" form="qualified"/&gt;
 *         &lt;element name="immagine" type="{it.eng.fileoperation.ws.timbro}ImageFile" minOccurs="0" form="qualified"/&gt;
 *         &lt;element name="coordinataXRiquadroFirma" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0" form="qualified"/&gt;
 *         &lt;element name="coordinataYRiquadroFirma" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0" form="qualified"/&gt;
 *         &lt;element name="areaOrizzontale" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0" form="qualified"/&gt;
 *         &lt;element name="areaVerticale" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0" form="qualified"/&gt;
 *         &lt;element name="ampiezzaRiquadroFirma" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0" form="qualified"/&gt;
 *         &lt;element name="altezzaRiquadroFirma" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0" form="qualified"/&gt;
 *         &lt;element name="pagina" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0" form="qualified"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "InformazioniFirmaGraficaType", namespace = "it.eng.fileoperation.ws.timbro", propOrder = {
    "testo",
    "immagine",
    "coordinataXRiquadroFirma",
    "coordinataYRiquadroFirma",
    "areaOrizzontale",
    "areaVerticale",
    "ampiezzaRiquadroFirma",
    "altezzaRiquadroFirma",
    "pagina"
})
public class InformazioniFirmaGraficaType {

    @XmlElement(required = true)
    protected String testo;
    protected ImageFile immagine;
    protected Integer coordinataXRiquadroFirma;
    protected Integer coordinataYRiquadroFirma;
    protected Integer areaOrizzontale;
    protected Integer areaVerticale;
    protected Integer ampiezzaRiquadroFirma;
    protected Integer altezzaRiquadroFirma;
    protected Integer pagina;

    /**
     * Recupera il valore della proprietà testo.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTesto() {
        return testo;
    }

    /**
     * Imposta il valore della proprietà testo.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTesto(String value) {
        this.testo = value;
    }

    /**
     * Recupera il valore della proprietà immagine.
     * 
     * @return
     *     possible object is
     *     {@link ImageFile }
     *     
     */
    public ImageFile getImmagine() {
        return immagine;
    }

    /**
     * Imposta il valore della proprietà immagine.
     * 
     * @param value
     *     allowed object is
     *     {@link ImageFile }
     *     
     */
    public void setImmagine(ImageFile value) {
        this.immagine = value;
    }

    /**
     * Recupera il valore della proprietà coordinataXRiquadroFirma.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getCoordinataXRiquadroFirma() {
        return coordinataXRiquadroFirma;
    }

    /**
     * Imposta il valore della proprietà coordinataXRiquadroFirma.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setCoordinataXRiquadroFirma(Integer value) {
        this.coordinataXRiquadroFirma = value;
    }

    /**
     * Recupera il valore della proprietà coordinataYRiquadroFirma.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getCoordinataYRiquadroFirma() {
        return coordinataYRiquadroFirma;
    }

    /**
     * Imposta il valore della proprietà coordinataYRiquadroFirma.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setCoordinataYRiquadroFirma(Integer value) {
        this.coordinataYRiquadroFirma = value;
    }

    /**
     * Recupera il valore della proprietà areaOrizzontale.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getAreaOrizzontale() {
        return areaOrizzontale;
    }

    /**
     * Imposta il valore della proprietà areaOrizzontale.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setAreaOrizzontale(Integer value) {
        this.areaOrizzontale = value;
    }

    /**
     * Recupera il valore della proprietà areaVerticale.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getAreaVerticale() {
        return areaVerticale;
    }

    /**
     * Imposta il valore della proprietà areaVerticale.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setAreaVerticale(Integer value) {
        this.areaVerticale = value;
    }

    /**
     * Recupera il valore della proprietà ampiezzaRiquadroFirma.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getAmpiezzaRiquadroFirma() {
        return ampiezzaRiquadroFirma;
    }

    /**
     * Imposta il valore della proprietà ampiezzaRiquadroFirma.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setAmpiezzaRiquadroFirma(Integer value) {
        this.ampiezzaRiquadroFirma = value;
    }

    /**
     * Recupera il valore della proprietà altezzaRiquadroFirma.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getAltezzaRiquadroFirma() {
        return altezzaRiquadroFirma;
    }

    /**
     * Imposta il valore della proprietà altezzaRiquadroFirma.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setAltezzaRiquadroFirma(Integer value) {
        this.altezzaRiquadroFirma = value;
    }

    /**
     * Recupera il valore della proprietà pagina.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getPagina() {
        return pagina;
    }

    /**
     * Imposta il valore della proprietà pagina.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setPagina(Integer value) {
        this.pagina = value;
    }

}
