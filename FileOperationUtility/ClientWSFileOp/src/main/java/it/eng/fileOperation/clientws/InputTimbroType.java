/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */
package it.eng.fileOperation.clientws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per InputTimbroType complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="InputTimbroType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{it.eng.fileoperation.ws.base}AbstractInputOperationType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="codifica" type="{it.eng.fileoperation.ws.timbro}codificaTimbro" minOccurs="0" form="qualified"/&gt;
 *         &lt;element name="testoTimbro" type="{it.eng.fileoperation.ws.timbro}testoTimbro" form="qualified"/&gt;
 *         &lt;element name="rotazioneTimbro" type="{it.eng.fileoperation.ws.timbro}tipoRotazione" minOccurs="0" form="qualified"/&gt;
 *         &lt;element name="intestazioneTimbro" type="{it.eng.fileoperation.ws.timbro}testoTimbro" minOccurs="0" form="qualified"/&gt;
 *         &lt;element name="posizioneTimbro" type="{it.eng.fileoperation.ws.timbro}posizioneTimbroNellaPagina" minOccurs="0" form="qualified"/&gt;
 *         &lt;element name="posizioneTestoInChiaro" type="{it.eng.fileoperation.ws.timbro}posizioneRispettoAlTimbro" minOccurs="0" form="qualified"/&gt;
 *         &lt;element name="posizioneIntestazione" type="{it.eng.fileoperation.ws.timbro}posizioneRispettoAlTimbro" minOccurs="0" form="qualified"/&gt;
 *         &lt;element name="timbroSingolo" type="{http://www.w3.org/2001/XMLSchema}boolean" form="qualified"/&gt;
 *         &lt;element name="paginaTimbro" type="{it.eng.fileoperation.ws.timbro}paginaTimbro" minOccurs="0" form="qualified"/&gt;
 *         &lt;element name="mappaParametri" type="{it.eng.fileoperation.ws.timbro}mappaParametri" minOccurs="0" form="qualified"/&gt;
 *         &lt;element name="righeMultiple" type="{http://www.w3.org/2001/XMLSchema}boolean" form="qualified"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/extension&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "InputTimbroType", namespace = "it.eng.fileoperation.ws.timbro", propOrder = {
    "codifica",
    "testoTimbro",
    "rotazioneTimbro",
    "intestazioneTimbro",
    "posizioneTimbro",
    "posizioneTestoInChiaro",
    "posizioneIntestazione",
    "timbroSingolo",
    "paginaTimbro",
    "mappaParametri",
    "righeMultiple"
})
public class InputTimbroType
    extends AbstractInputOperationType
{

    @XmlSchemaType(name = "string")
    protected CodificaTimbro codifica;
    @XmlElement(required = true)
    protected TestoTimbro testoTimbro;
    @XmlSchemaType(name = "string")
    protected TipoRotazione rotazioneTimbro;
    protected TestoTimbro intestazioneTimbro;
    @XmlSchemaType(name = "string")
    protected PosizioneTimbroNellaPagina posizioneTimbro;
    @XmlSchemaType(name = "string")
    protected PosizioneRispettoAlTimbro posizioneTestoInChiaro;
    @XmlSchemaType(name = "string")
    protected PosizioneRispettoAlTimbro posizioneIntestazione;
    @XmlElement(defaultValue = "false")
    protected boolean timbroSingolo;
    protected PaginaTimbro paginaTimbro;
    protected MappaParametri mappaParametri;
    @XmlElement(defaultValue = "false")
    protected boolean righeMultiple;

    /**
     * Recupera il valore della proprietà codifica.
     * 
     * @return
     *     possible object is
     *     {@link CodificaTimbro }
     *     
     */
    public CodificaTimbro getCodifica() {
        return codifica;
    }

    /**
     * Imposta il valore della proprietà codifica.
     * 
     * @param value
     *     allowed object is
     *     {@link CodificaTimbro }
     *     
     */
    public void setCodifica(CodificaTimbro value) {
        this.codifica = value;
    }

    /**
     * Recupera il valore della proprietà testoTimbro.
     * 
     * @return
     *     possible object is
     *     {@link TestoTimbro }
     *     
     */
    public TestoTimbro getTestoTimbro() {
        return testoTimbro;
    }

    /**
     * Imposta il valore della proprietà testoTimbro.
     * 
     * @param value
     *     allowed object is
     *     {@link TestoTimbro }
     *     
     */
    public void setTestoTimbro(TestoTimbro value) {
        this.testoTimbro = value;
    }

    /**
     * Recupera il valore della proprietà rotazioneTimbro.
     * 
     * @return
     *     possible object is
     *     {@link TipoRotazione }
     *     
     */
    public TipoRotazione getRotazioneTimbro() {
        return rotazioneTimbro;
    }

    /**
     * Imposta il valore della proprietà rotazioneTimbro.
     * 
     * @param value
     *     allowed object is
     *     {@link TipoRotazione }
     *     
     */
    public void setRotazioneTimbro(TipoRotazione value) {
        this.rotazioneTimbro = value;
    }

    /**
     * Recupera il valore della proprietà intestazioneTimbro.
     * 
     * @return
     *     possible object is
     *     {@link TestoTimbro }
     *     
     */
    public TestoTimbro getIntestazioneTimbro() {
        return intestazioneTimbro;
    }

    /**
     * Imposta il valore della proprietà intestazioneTimbro.
     * 
     * @param value
     *     allowed object is
     *     {@link TestoTimbro }
     *     
     */
    public void setIntestazioneTimbro(TestoTimbro value) {
        this.intestazioneTimbro = value;
    }

    /**
     * Recupera il valore della proprietà posizioneTimbro.
     * 
     * @return
     *     possible object is
     *     {@link PosizioneTimbroNellaPagina }
     *     
     */
    public PosizioneTimbroNellaPagina getPosizioneTimbro() {
        return posizioneTimbro;
    }

    /**
     * Imposta il valore della proprietà posizioneTimbro.
     * 
     * @param value
     *     allowed object is
     *     {@link PosizioneTimbroNellaPagina }
     *     
     */
    public void setPosizioneTimbro(PosizioneTimbroNellaPagina value) {
        this.posizioneTimbro = value;
    }

    /**
     * Recupera il valore della proprietà posizioneTestoInChiaro.
     * 
     * @return
     *     possible object is
     *     {@link PosizioneRispettoAlTimbro }
     *     
     */
    public PosizioneRispettoAlTimbro getPosizioneTestoInChiaro() {
        return posizioneTestoInChiaro;
    }

    /**
     * Imposta il valore della proprietà posizioneTestoInChiaro.
     * 
     * @param value
     *     allowed object is
     *     {@link PosizioneRispettoAlTimbro }
     *     
     */
    public void setPosizioneTestoInChiaro(PosizioneRispettoAlTimbro value) {
        this.posizioneTestoInChiaro = value;
    }

    /**
     * Recupera il valore della proprietà posizioneIntestazione.
     * 
     * @return
     *     possible object is
     *     {@link PosizioneRispettoAlTimbro }
     *     
     */
    public PosizioneRispettoAlTimbro getPosizioneIntestazione() {
        return posizioneIntestazione;
    }

    /**
     * Imposta il valore della proprietà posizioneIntestazione.
     * 
     * @param value
     *     allowed object is
     *     {@link PosizioneRispettoAlTimbro }
     *     
     */
    public void setPosizioneIntestazione(PosizioneRispettoAlTimbro value) {
        this.posizioneIntestazione = value;
    }

    /**
     * Recupera il valore della proprietà timbroSingolo.
     * 
     */
    public boolean isTimbroSingolo() {
        return timbroSingolo;
    }

    /**
     * Imposta il valore della proprietà timbroSingolo.
     * 
     */
    public void setTimbroSingolo(boolean value) {
        this.timbroSingolo = value;
    }

    /**
     * Recupera il valore della proprietà paginaTimbro.
     * 
     * @return
     *     possible object is
     *     {@link PaginaTimbro }
     *     
     */
    public PaginaTimbro getPaginaTimbro() {
        return paginaTimbro;
    }

    /**
     * Imposta il valore della proprietà paginaTimbro.
     * 
     * @param value
     *     allowed object is
     *     {@link PaginaTimbro }
     *     
     */
    public void setPaginaTimbro(PaginaTimbro value) {
        this.paginaTimbro = value;
    }

    /**
     * Recupera il valore della proprietà mappaParametri.
     * 
     * @return
     *     possible object is
     *     {@link MappaParametri }
     *     
     */
    public MappaParametri getMappaParametri() {
        return mappaParametri;
    }

    /**
     * Imposta il valore della proprietà mappaParametri.
     * 
     * @param value
     *     allowed object is
     *     {@link MappaParametri }
     *     
     */
    public void setMappaParametri(MappaParametri value) {
        this.mappaParametri = value;
    }

    /**
     * Recupera il valore della proprietà righeMultiple.
     * 
     */
    public boolean isRigheMultiple() {
        return righeMultiple;
    }

    /**
     * Imposta il valore della proprietà righeMultiple.
     * 
     */
    public void setRigheMultiple(boolean value) {
        this.righeMultiple = value;
    }

}
