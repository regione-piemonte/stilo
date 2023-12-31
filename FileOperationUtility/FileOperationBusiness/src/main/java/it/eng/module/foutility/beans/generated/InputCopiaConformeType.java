/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vhudson-jaxb-ri-2.1-2 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2023.01.20 at 10:39:55 AM CET 
//


package it.eng.module.foutility.beans.generated;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * input per l'operazione di apposizione timbro di copia conforme
 * 
 * <p>Java class for InputCopiaConformeType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="InputCopiaConformeType">
 *   &lt;complexContent>
 *     &lt;extension base="{it.eng.fileoperation.ws.base}AbstractInputOperationType">
 *       &lt;sequence>
 *         &lt;element name="intestazione" type="{it.eng.fileoperation.ws.timbro}testoTimbro"/>
 *         &lt;element name="testoAggiuntivo" type="{it.eng.fileoperation.ws.timbro}testoTimbro"/>
 *         &lt;element name="immagine" type="{it.eng.fileoperation.ws.timbro}ImageFile" minOccurs="0"/>
 *         &lt;element name="posizione" type="{it.eng.fileoperation.ws.timbro}posizioneTimbroNellaPagina" minOccurs="0"/>
 *         &lt;element name="rotazione" type="{it.eng.fileoperation.ws.timbro}tipoRotazione" minOccurs="0"/>
 *         &lt;element name="pagina" type="{it.eng.fileoperation.ws.timbro}paginaTimbro" minOccurs="0"/>
 *         &lt;element name="ampiezzaRiquadro" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "InputCopiaConformeType", namespace = "it.eng.fileoperation.ws.timbro", propOrder = {
    "intestazione",
    "testoAggiuntivo",
    "immagine",
    "posizione",
    "rotazione",
    "pagina",
    "ampiezzaRiquadro"
})
public class InputCopiaConformeType
    extends AbstractInputOperationType
    implements Serializable
{

    @XmlElement(required = true)
    protected TestoTimbro intestazione;
    @XmlElement(required = true)
    protected TestoTimbro testoAggiuntivo;
    protected ImageFile immagine;
    protected PosizioneTimbroNellaPagina posizione;
    protected TipoRotazione rotazione;
    protected PaginaTimbro pagina;
    protected Integer ampiezzaRiquadro;

    /**
     * Gets the value of the intestazione property.
     * 
     * @return
     *     possible object is
     *     {@link TestoTimbro }
     *     
     */
    public TestoTimbro getIntestazione() {
        return intestazione;
    }

    /**
     * Sets the value of the intestazione property.
     * 
     * @param value
     *     allowed object is
     *     {@link TestoTimbro }
     *     
     */
    public void setIntestazione(TestoTimbro value) {
        this.intestazione = value;
    }

    /**
     * Gets the value of the testoAggiuntivo property.
     * 
     * @return
     *     possible object is
     *     {@link TestoTimbro }
     *     
     */
    public TestoTimbro getTestoAggiuntivo() {
        return testoAggiuntivo;
    }

    /**
     * Sets the value of the testoAggiuntivo property.
     * 
     * @param value
     *     allowed object is
     *     {@link TestoTimbro }
     *     
     */
    public void setTestoAggiuntivo(TestoTimbro value) {
        this.testoAggiuntivo = value;
    }

    /**
     * Gets the value of the immagine property.
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
     * Sets the value of the immagine property.
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
     * Gets the value of the posizione property.
     * 
     * @return
     *     possible object is
     *     {@link PosizioneTimbroNellaPagina }
     *     
     */
    public PosizioneTimbroNellaPagina getPosizione() {
        return posizione;
    }

    /**
     * Sets the value of the posizione property.
     * 
     * @param value
     *     allowed object is
     *     {@link PosizioneTimbroNellaPagina }
     *     
     */
    public void setPosizione(PosizioneTimbroNellaPagina value) {
        this.posizione = value;
    }

    /**
     * Gets the value of the rotazione property.
     * 
     * @return
     *     possible object is
     *     {@link TipoRotazione }
     *     
     */
    public TipoRotazione getRotazione() {
        return rotazione;
    }

    /**
     * Sets the value of the rotazione property.
     * 
     * @param value
     *     allowed object is
     *     {@link TipoRotazione }
     *     
     */
    public void setRotazione(TipoRotazione value) {
        this.rotazione = value;
    }

    /**
     * Gets the value of the pagina property.
     * 
     * @return
     *     possible object is
     *     {@link PaginaTimbro }
     *     
     */
    public PaginaTimbro getPagina() {
        return pagina;
    }

    /**
     * Sets the value of the pagina property.
     * 
     * @param value
     *     allowed object is
     *     {@link PaginaTimbro }
     *     
     */
    public void setPagina(PaginaTimbro value) {
        this.pagina = value;
    }

    /**
     * Gets the value of the ampiezzaRiquadro property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getAmpiezzaRiquadro() {
        return ampiezzaRiquadro;
    }

    /**
     * Sets the value of the ampiezzaRiquadro property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setAmpiezzaRiquadro(Integer value) {
        this.ampiezzaRiquadro = value;
    }

}
