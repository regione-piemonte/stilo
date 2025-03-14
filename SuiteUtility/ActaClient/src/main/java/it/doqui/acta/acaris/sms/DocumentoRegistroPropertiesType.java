
package it.doqui.acta.acaris.sms;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Classe Java per DocumentoRegistroPropertiesType complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="DocumentoRegistroPropertiesType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{archive.acaris.acta.doqui.it}DocumentoPropertiesType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="codiceRegistro" type="{archive.acaris.acta.doqui.it}CodiceRegistroType"/&gt;
 *         &lt;element name="annoRegistro" type="{archive.acaris.acta.doqui.it}AnnoRegistroType"/&gt;
 *         &lt;element name="numeroPrimaRegistrazione" type="{archive.acaris.acta.doqui.it}NumeroPrimaRegistrazioneType"/&gt;
 *         &lt;element name="dataPrimaRegistrazione" type="{archive.acaris.acta.doqui.it}DataPrimaRegistrazioneType"/&gt;
 *         &lt;element name="numeroUltimaRegistrazione" type="{archive.acaris.acta.doqui.it}NumeroUltimaRegistrazioneType"/&gt;
 *         &lt;element name="dataUltimaRegistrazione" type="{archive.acaris.acta.doqui.it}DataUltimaRegistrazioneType"/&gt;
 *         &lt;element name="dataApertura" type="{archive.acaris.acta.doqui.it}DataAperturaType"/&gt;
 *         &lt;element name="dataChiusura" type="{archive.acaris.acta.doqui.it}DataChiusuraType"/&gt;
 *         &lt;element name="ubicazione" type="{archive.acaris.acta.doqui.it}UbicazioneType"/&gt;
 *         &lt;element name="versioneSW" type="{archive.acaris.acta.doqui.it}VersioneSWType"/&gt;
 *         &lt;element name="tipoDocFisico" type="{archive.acaris.acta.doqui.it}enumTipoDocumentoType"/&gt;
 *         &lt;element name="composizione" type="{archive.acaris.acta.doqui.it}enumDocPrimarioType"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/extension&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DocumentoRegistroPropertiesType", propOrder = {
    "codiceRegistro",
    "annoRegistro",
    "numeroPrimaRegistrazione",
    "dataPrimaRegistrazione",
    "numeroUltimaRegistrazione",
    "dataUltimaRegistrazione",
    "dataApertura",
    "dataChiusura",
    "ubicazione",
    "versioneSW",
    "tipoDocFisico",
    "composizione"
})
public class DocumentoRegistroPropertiesType
    extends DocumentoPropertiesType
{

    @XmlElement(required = true)
    protected String codiceRegistro;
    protected int annoRegistro;
    @XmlElement(required = true)
    protected String numeroPrimaRegistrazione;
    @XmlElement(required = true)
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar dataPrimaRegistrazione;
    @XmlElement(required = true)
    protected String numeroUltimaRegistrazione;
    @XmlElement(required = true)
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar dataUltimaRegistrazione;
    @XmlElement(required = true)
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar dataApertura;
    @XmlElement(required = true)
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar dataChiusura;
    @XmlElement(required = true)
    protected String ubicazione;
    @XmlElement(required = true)
    protected String versioneSW;
    @XmlElement(required = true)
    @XmlSchemaType(name = "string")
    protected EnumTipoDocumentoType tipoDocFisico;
    @XmlElement(required = true)
    @XmlSchemaType(name = "string")
    protected EnumDocPrimarioType composizione;

    /**
     * Recupera il valore della proprietÓ codiceRegistro.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCodiceRegistro() {
        return codiceRegistro;
    }

    /**
     * Imposta il valore della proprietÓ codiceRegistro.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCodiceRegistro(String value) {
        this.codiceRegistro = value;
    }

    /**
     * Recupera il valore della proprietÓ annoRegistro.
     * 
     */
    public int getAnnoRegistro() {
        return annoRegistro;
    }

    /**
     * Imposta il valore della proprietÓ annoRegistro.
     * 
     */
    public void setAnnoRegistro(int value) {
        this.annoRegistro = value;
    }

    /**
     * Recupera il valore della proprietÓ numeroPrimaRegistrazione.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNumeroPrimaRegistrazione() {
        return numeroPrimaRegistrazione;
    }

    /**
     * Imposta il valore della proprietÓ numeroPrimaRegistrazione.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNumeroPrimaRegistrazione(String value) {
        this.numeroPrimaRegistrazione = value;
    }

    /**
     * Recupera il valore della proprietÓ dataPrimaRegistrazione.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDataPrimaRegistrazione() {
        return dataPrimaRegistrazione;
    }

    /**
     * Imposta il valore della proprietÓ dataPrimaRegistrazione.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDataPrimaRegistrazione(XMLGregorianCalendar value) {
        this.dataPrimaRegistrazione = value;
    }

    /**
     * Recupera il valore della proprietÓ numeroUltimaRegistrazione.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNumeroUltimaRegistrazione() {
        return numeroUltimaRegistrazione;
    }

    /**
     * Imposta il valore della proprietÓ numeroUltimaRegistrazione.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNumeroUltimaRegistrazione(String value) {
        this.numeroUltimaRegistrazione = value;
    }

    /**
     * Recupera il valore della proprietÓ dataUltimaRegistrazione.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDataUltimaRegistrazione() {
        return dataUltimaRegistrazione;
    }

    /**
     * Imposta il valore della proprietÓ dataUltimaRegistrazione.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDataUltimaRegistrazione(XMLGregorianCalendar value) {
        this.dataUltimaRegistrazione = value;
    }

    /**
     * Recupera il valore della proprietÓ dataApertura.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDataApertura() {
        return dataApertura;
    }

    /**
     * Imposta il valore della proprietÓ dataApertura.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDataApertura(XMLGregorianCalendar value) {
        this.dataApertura = value;
    }

    /**
     * Recupera il valore della proprietÓ dataChiusura.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDataChiusura() {
        return dataChiusura;
    }

    /**
     * Imposta il valore della proprietÓ dataChiusura.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDataChiusura(XMLGregorianCalendar value) {
        this.dataChiusura = value;
    }

    /**
     * Recupera il valore della proprietÓ ubicazione.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUbicazione() {
        return ubicazione;
    }

    /**
     * Imposta il valore della proprietÓ ubicazione.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUbicazione(String value) {
        this.ubicazione = value;
    }

    /**
     * Recupera il valore della proprietÓ versioneSW.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getVersioneSW() {
        return versioneSW;
    }

    /**
     * Imposta il valore della proprietÓ versioneSW.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setVersioneSW(String value) {
        this.versioneSW = value;
    }

    /**
     * Recupera il valore della proprietÓ tipoDocFisico.
     * 
     * @return
     *     possible object is
     *     {@link EnumTipoDocumentoType }
     *     
     */
    public EnumTipoDocumentoType getTipoDocFisico() {
        return tipoDocFisico;
    }

    /**
     * Imposta il valore della proprietÓ tipoDocFisico.
     * 
     * @param value
     *     allowed object is
     *     {@link EnumTipoDocumentoType }
     *     
     */
    public void setTipoDocFisico(EnumTipoDocumentoType value) {
        this.tipoDocFisico = value;
    }

    /**
     * Recupera il valore della proprietÓ composizione.
     * 
     * @return
     *     possible object is
     *     {@link EnumDocPrimarioType }
     *     
     */
    public EnumDocPrimarioType getComposizione() {
        return composizione;
    }

    /**
     * Imposta il valore della proprietÓ composizione.
     * 
     * @param value
     *     allowed object is
     *     {@link EnumDocPrimarioType }
     *     
     */
    public void setComposizione(EnumDocPrimarioType value) {
        this.composizione = value;
    }

}
