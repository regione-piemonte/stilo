
package it.doqui.acta.acaris.management;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Classe Java per AnnotazioniPropertiesType complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="AnnotazioniPropertiesType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{common.acaris.acta.doqui.it}CommonPropertiesType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="objectTypeId" type="{common.acaris.acta.doqui.it}enumCommonObjectType"/&gt;
 *         &lt;element name="descrizione" type="{common.acaris.acta.doqui.it}string"/&gt;
 *         &lt;element name="idUtente" type="{common.acaris.acta.doqui.it}IdUtenteType"/&gt;
 *         &lt;element name="data" type="{common.acaris.acta.doqui.it}date"/&gt;
 *         &lt;element name="annotazioneFormale" type="{common.acaris.acta.doqui.it}boolean"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/extension&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "AnnotazioniPropertiesType", namespace = "common.acaris.acta.doqui.it", propOrder = {
    "objectTypeId",
    "descrizione",
    "idUtente",
    "data",
    "annotazioneFormale"
})
public class AnnotazioniPropertiesType
    extends CommonPropertiesType
{

    @XmlElement(namespace = "", required = true)
    @XmlSchemaType(name = "string")
    protected EnumCommonObjectType objectTypeId;
    @XmlElement(namespace = "", required = true)
    protected String descrizione;
    @XmlElement(namespace = "", required = true)
    protected IdUtenteType idUtente;
    @XmlElement(namespace = "", required = true)
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar data;
    @XmlElement(namespace = "")
    protected boolean annotazioneFormale;

    /**
     * Recupera il valore della proprietÓ objectTypeId.
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
     * Imposta il valore della proprietÓ objectTypeId.
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
     * Recupera il valore della proprietÓ descrizione.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDescrizione() {
        return descrizione;
    }

    /**
     * Imposta il valore della proprietÓ descrizione.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDescrizione(String value) {
        this.descrizione = value;
    }

    /**
     * Recupera il valore della proprietÓ idUtente.
     * 
     * @return
     *     possible object is
     *     {@link IdUtenteType }
     *     
     */
    public IdUtenteType getIdUtente() {
        return idUtente;
    }

    /**
     * Imposta il valore della proprietÓ idUtente.
     * 
     * @param value
     *     allowed object is
     *     {@link IdUtenteType }
     *     
     */
    public void setIdUtente(IdUtenteType value) {
        this.idUtente = value;
    }

    /**
     * Recupera il valore della proprietÓ data.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getData() {
        return data;
    }

    /**
     * Imposta il valore della proprietÓ data.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setData(XMLGregorianCalendar value) {
        this.data = value;
    }

    /**
     * Recupera il valore della proprietÓ annotazioneFormale.
     * 
     */
    public boolean isAnnotazioneFormale() {
        return annotazioneFormale;
    }

    /**
     * Imposta il valore della proprietÓ annotazioneFormale.
     * 
     */
    public void setAnnotazioneFormale(boolean value) {
        this.annotazioneFormale = value;
    }

}
