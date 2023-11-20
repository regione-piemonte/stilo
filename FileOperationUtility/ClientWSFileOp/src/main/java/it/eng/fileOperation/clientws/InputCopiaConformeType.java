/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */
package it.eng.fileOperation.clientws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per InputCopiaConformeType complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="InputCopiaConformeType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{it.eng.fileoperation.ws.base}AbstractInputOperationType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="intestazione" type="{it.eng.fileoperation.ws.timbro}testoTimbro" form="qualified"/&gt;
 *         &lt;element name="testoAggiuntivo" type="{it.eng.fileoperation.ws.timbro}testoTimbro" form="qualified"/&gt;
 *         &lt;element name="immagine" type="{it.eng.fileoperation.ws.timbro}ImageFile" minOccurs="0" form="qualified"/&gt;
 *         &lt;element name="posizione" type="{it.eng.fileoperation.ws.timbro}posizioneTimbroNellaPagina" minOccurs="0" form="qualified"/&gt;
 *         &lt;element name="rotazione" type="{it.eng.fileoperation.ws.timbro}tipoRotazione" minOccurs="0" form="qualified"/&gt;
 *         &lt;element name="pagina" type="{it.eng.fileoperation.ws.timbro}paginaTimbro" minOccurs="0" form="qualified"/&gt;
 *         &lt;element name="ampiezzaRiquadro" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0" form="qualified"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/extension&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
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
{

    @XmlElement(required = true)
    protected TestoTimbro intestazione;
    @XmlElement(required = true)
    protected TestoTimbro testoAggiuntivo;
    protected ImageFile immagine;
    @XmlSchemaType(name = "string")
    protected PosizioneTimbroNellaPagina posizione;
    @XmlSchemaType(name = "string")
    protected TipoRotazione rotazione;
    protected PaginaTimbro pagina;
    protected Integer ampiezzaRiquadro;

    /**
     * Recupera il valore della proprietà intestazione.
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
     * Imposta il valore della proprietà intestazione.
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
     * Recupera il valore della proprietà testoAggiuntivo.
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
     * Imposta il valore della proprietà testoAggiuntivo.
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
     * Recupera il valore della proprietà posizione.
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
     * Imposta il valore della proprietà posizione.
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
     * Recupera il valore della proprietà rotazione.
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
     * Imposta il valore della proprietà rotazione.
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
     * Recupera il valore della proprietà pagina.
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
     * Imposta il valore della proprietà pagina.
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
     * Recupera il valore della proprietà ampiezzaRiquadro.
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
     * Imposta il valore della proprietà ampiezzaRiquadro.
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
