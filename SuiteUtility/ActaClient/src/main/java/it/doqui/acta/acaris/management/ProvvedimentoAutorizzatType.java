
package it.doqui.acta.acaris.management;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Classe Java per ProvvedimentoAutorizzatType complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="ProvvedimentoAutorizzatType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="idProvvedimentoAutorizzat" type="{common.acaris.acta.doqui.it}IdProvvedimentoAutorizzatType"/&gt;
 *         &lt;element name="numero" type="{common.acaris.acta.doqui.it}string"/&gt;
 *         &lt;element name="dataAppr" type="{common.acaris.acta.doqui.it}date"/&gt;
 *         &lt;element name="organo" type="{common.acaris.acta.doqui.it}string"/&gt;
 *         &lt;element name="tipoDoc" type="{management.acaris.acta.doqui.it}enumTipoDocType"/&gt;
 *         &lt;element name="descr" type="{common.acaris.acta.doqui.it}string"/&gt;
 *         &lt;element name="tipoProvv" type="{management.acaris.acta.doqui.it}enumTipoProvvType"/&gt;
 *         &lt;element name="note" type="{common.acaris.acta.doqui.it}string"/&gt;
 *         &lt;element name="indiceClassDocumento" type="{common.acaris.acta.doqui.it}string"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ProvvedimentoAutorizzatType", namespace = "management.acaris.acta.doqui.it", propOrder = {
    "idProvvedimentoAutorizzat",
    "numero",
    "dataAppr",
    "organo",
    "tipoDoc",
    "descr",
    "tipoProvv",
    "note",
    "indiceClassDocumento"
})
public class ProvvedimentoAutorizzatType {

    @XmlElement(required = true)
    protected IdProvvedimentoAutorizzatType idProvvedimentoAutorizzat;
    @XmlElement(required = true)
    protected String numero;
    @XmlElement(required = true)
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar dataAppr;
    @XmlElement(required = true)
    protected String organo;
    @XmlElement(required = true)
    @XmlSchemaType(name = "string")
    protected EnumTipoDocType tipoDoc;
    @XmlElement(required = true)
    protected String descr;
    @XmlElement(required = true)
    @XmlSchemaType(name = "string")
    protected EnumTipoProvvType tipoProvv;
    @XmlElement(required = true)
    protected String note;
    @XmlElement(required = true)
    protected String indiceClassDocumento;

    /**
     * Recupera il valore della propriet� idProvvedimentoAutorizzat.
     * 
     * @return
     *     possible object is
     *     {@link IdProvvedimentoAutorizzatType }
     *     
     */
    public IdProvvedimentoAutorizzatType getIdProvvedimentoAutorizzat() {
        return idProvvedimentoAutorizzat;
    }

    /**
     * Imposta il valore della propriet� idProvvedimentoAutorizzat.
     * 
     * @param value
     *     allowed object is
     *     {@link IdProvvedimentoAutorizzatType }
     *     
     */
    public void setIdProvvedimentoAutorizzat(IdProvvedimentoAutorizzatType value) {
        this.idProvvedimentoAutorizzat = value;
    }

    /**
     * Recupera il valore della propriet� numero.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNumero() {
        return numero;
    }

    /**
     * Imposta il valore della propriet� numero.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNumero(String value) {
        this.numero = value;
    }

    /**
     * Recupera il valore della propriet� dataAppr.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDataAppr() {
        return dataAppr;
    }

    /**
     * Imposta il valore della propriet� dataAppr.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDataAppr(XMLGregorianCalendar value) {
        this.dataAppr = value;
    }

    /**
     * Recupera il valore della propriet� organo.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOrgano() {
        return organo;
    }

    /**
     * Imposta il valore della propriet� organo.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOrgano(String value) {
        this.organo = value;
    }

    /**
     * Recupera il valore della propriet� tipoDoc.
     * 
     * @return
     *     possible object is
     *     {@link EnumTipoDocType }
     *     
     */
    public EnumTipoDocType getTipoDoc() {
        return tipoDoc;
    }

    /**
     * Imposta il valore della propriet� tipoDoc.
     * 
     * @param value
     *     allowed object is
     *     {@link EnumTipoDocType }
     *     
     */
    public void setTipoDoc(EnumTipoDocType value) {
        this.tipoDoc = value;
    }

    /**
     * Recupera il valore della propriet� descr.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDescr() {
        return descr;
    }

    /**
     * Imposta il valore della propriet� descr.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDescr(String value) {
        this.descr = value;
    }

    /**
     * Recupera il valore della propriet� tipoProvv.
     * 
     * @return
     *     possible object is
     *     {@link EnumTipoProvvType }
     *     
     */
    public EnumTipoProvvType getTipoProvv() {
        return tipoProvv;
    }

    /**
     * Imposta il valore della propriet� tipoProvv.
     * 
     * @param value
     *     allowed object is
     *     {@link EnumTipoProvvType }
     *     
     */
    public void setTipoProvv(EnumTipoProvvType value) {
        this.tipoProvv = value;
    }

    /**
     * Recupera il valore della propriet� note.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNote() {
        return note;
    }

    /**
     * Imposta il valore della propriet� note.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNote(String value) {
        this.note = value;
    }

    /**
     * Recupera il valore della propriet� indiceClassDocumento.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIndiceClassDocumento() {
        return indiceClassDocumento;
    }

    /**
     * Imposta il valore della propriet� indiceClassDocumento.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIndiceClassDocumento(String value) {
        this.indiceClassDocumento = value;
    }

}
