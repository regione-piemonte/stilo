
package it.doqui.acta.acaris.repository;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Classe Java per ProtocolloPropertiesType complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="ProtocolloPropertiesType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{common.acaris.acta.doqui.it}CommonPropertiesType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="objectTypeId" type="{common.acaris.acta.doqui.it}enumCommonObjectType"/&gt;
 *         &lt;element name="numRegistrazioneProtocollo" type="{common.acaris.acta.doqui.it}string"/&gt;
 *         &lt;element name="dataRegistrazioneProtocollo" type="{common.acaris.acta.doqui.it}date"/&gt;
 *         &lt;element name="oggetto" type="{common.acaris.acta.doqui.it}string"/&gt;
 *         &lt;element name="entrata" type="{common.acaris.acta.doqui.it}boolean"/&gt;
 *         &lt;element name="AOOCheRegistra" type="{common.acaris.acta.doqui.it}string"/&gt;
 *         &lt;element name="EnteCheRegistra" type="{common.acaris.acta.doqui.it}string"/&gt;
 *         &lt;element name="mittente" type="{common.acaris.acta.doqui.it}string" maxOccurs="unbounded" minOccurs="0"/&gt;
 *         &lt;element name="destinatario" type="{common.acaris.acta.doqui.it}string" maxOccurs="unbounded" minOccurs="0"/&gt;
 *         &lt;element name="segnaturaInternaXML" type="{common.acaris.acta.doqui.it}XMLType"/&gt;
 *         &lt;element name="segnaturaEsternaXML" type="{common.acaris.acta.doqui.it}XMLType"/&gt;
 *         &lt;element name="riservato" type="{common.acaris.acta.doqui.it}boolean"/&gt;
 *         &lt;element name="annullato" type="{common.acaris.acta.doqui.it}boolean"/&gt;
 *         &lt;element name="uuidRegistrazioneProtocollo" type="{common.acaris.acta.doqui.it}string"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/extension&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ProtocolloPropertiesType", namespace = "common.acaris.acta.doqui.it", propOrder = {
    "objectTypeId",
    "numRegistrazioneProtocollo",
    "dataRegistrazioneProtocollo",
    "oggetto",
    "entrata",
    "aooCheRegistra",
    "enteCheRegistra",
    "mittente",
    "destinatario",
    "segnaturaInternaXML",
    "segnaturaEsternaXML",
    "riservato",
    "annullato",
    "uuidRegistrazioneProtocollo"
})
public class ProtocolloPropertiesType
    extends CommonPropertiesType
{

    @XmlElement(namespace = "", required = true)
    @XmlSchemaType(name = "string")
    protected EnumCommonObjectType objectTypeId;
    @XmlElement(namespace = "", required = true)
    protected String numRegistrazioneProtocollo;
    @XmlElement(namespace = "", required = true)
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar dataRegistrazioneProtocollo;
    @XmlElement(namespace = "", required = true)
    protected String oggetto;
    @XmlElement(namespace = "")
    protected boolean entrata;
    @XmlElement(name = "AOOCheRegistra", namespace = "", required = true)
    protected String aooCheRegistra;
    @XmlElement(name = "EnteCheRegistra", namespace = "", required = true)
    protected String enteCheRegistra;
    @XmlElement(namespace = "")
    protected List<String> mittente;
    @XmlElement(namespace = "")
    protected List<String> destinatario;
    @XmlElement(namespace = "", required = true)
    protected String segnaturaInternaXML;
    @XmlElement(namespace = "", required = true)
    protected String segnaturaEsternaXML;
    @XmlElement(namespace = "")
    protected boolean riservato;
    @XmlElement(namespace = "")
    protected boolean annullato;
    @XmlElement(namespace = "", required = true)
    protected String uuidRegistrazioneProtocollo;

    /**
     * Recupera il valore della proprietà objectTypeId.
     * 
     * @return
     *     possible object is
     *     {@link EnumCommonObjectType }
     *     
     */
    public EnumCommonObjectType getObjectTypeId() {
        return objectTypeId;
    }

    /**
     * Imposta il valore della proprietà objectTypeId.
     * 
     * @param value
     *     allowed object is
     *     {@link EnumCommonObjectType }
     *     
     */
    public void setObjectTypeId(EnumCommonObjectType value) {
        this.objectTypeId = value;
    }

    /**
     * Recupera il valore della proprietà numRegistrazioneProtocollo.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNumRegistrazioneProtocollo() {
        return numRegistrazioneProtocollo;
    }

    /**
     * Imposta il valore della proprietà numRegistrazioneProtocollo.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNumRegistrazioneProtocollo(String value) {
        this.numRegistrazioneProtocollo = value;
    }

    /**
     * Recupera il valore della proprietà dataRegistrazioneProtocollo.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDataRegistrazioneProtocollo() {
        return dataRegistrazioneProtocollo;
    }

    /**
     * Imposta il valore della proprietà dataRegistrazioneProtocollo.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDataRegistrazioneProtocollo(XMLGregorianCalendar value) {
        this.dataRegistrazioneProtocollo = value;
    }

    /**
     * Recupera il valore della proprietà oggetto.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOggetto() {
        return oggetto;
    }

    /**
     * Imposta il valore della proprietà oggetto.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOggetto(String value) {
        this.oggetto = value;
    }

    /**
     * Recupera il valore della proprietà entrata.
     * 
     */
    public boolean isEntrata() {
        return entrata;
    }

    /**
     * Imposta il valore della proprietà entrata.
     * 
     */
    public void setEntrata(boolean value) {
        this.entrata = value;
    }

    /**
     * Recupera il valore della proprietà aooCheRegistra.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAOOCheRegistra() {
        return aooCheRegistra;
    }

    /**
     * Imposta il valore della proprietà aooCheRegistra.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAOOCheRegistra(String value) {
        this.aooCheRegistra = value;
    }

    /**
     * Recupera il valore della proprietà enteCheRegistra.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEnteCheRegistra() {
        return enteCheRegistra;
    }

    /**
     * Imposta il valore della proprietà enteCheRegistra.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEnteCheRegistra(String value) {
        this.enteCheRegistra = value;
    }

    /**
     * Gets the value of the mittente property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the mittente property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getMittente().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link String }
     * 
     * 
     */
    public List<String> getMittente() {
        if (mittente == null) {
            mittente = new ArrayList<String>();
        }
        return this.mittente;
    }

    /**
     * Gets the value of the destinatario property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the destinatario property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getDestinatario().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link String }
     * 
     * 
     */
    public List<String> getDestinatario() {
        if (destinatario == null) {
            destinatario = new ArrayList<String>();
        }
        return this.destinatario;
    }

    /**
     * Recupera il valore della proprietà segnaturaInternaXML.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSegnaturaInternaXML() {
        return segnaturaInternaXML;
    }

    /**
     * Imposta il valore della proprietà segnaturaInternaXML.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSegnaturaInternaXML(String value) {
        this.segnaturaInternaXML = value;
    }

    /**
     * Recupera il valore della proprietà segnaturaEsternaXML.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSegnaturaEsternaXML() {
        return segnaturaEsternaXML;
    }

    /**
     * Imposta il valore della proprietà segnaturaEsternaXML.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSegnaturaEsternaXML(String value) {
        this.segnaturaEsternaXML = value;
    }

    /**
     * Recupera il valore della proprietà riservato.
     * 
     */
    public boolean isRiservato() {
        return riservato;
    }

    /**
     * Imposta il valore della proprietà riservato.
     * 
     */
    public void setRiservato(boolean value) {
        this.riservato = value;
    }

    /**
     * Recupera il valore della proprietà annullato.
     * 
     */
    public boolean isAnnullato() {
        return annullato;
    }

    /**
     * Imposta il valore della proprietà annullato.
     * 
     */
    public void setAnnullato(boolean value) {
        this.annullato = value;
    }

    /**
     * Recupera il valore della proprietà uuidRegistrazioneProtocollo.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUuidRegistrazioneProtocollo() {
        return uuidRegistrazioneProtocollo;
    }

    /**
     * Imposta il valore della proprietà uuidRegistrazioneProtocollo.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUuidRegistrazioneProtocollo(String value) {
        this.uuidRegistrazioneProtocollo = value;
    }

}
