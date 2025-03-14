
package it.doqui.acta.acaris.backoffice;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Classe Java per UtentePropertiesType complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="UtentePropertiesType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{backoffice.acaris.acta.doqui.it}BackOfficePropertiesType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="parentId" type="{common.acaris.acta.doqui.it}ObjectIdType"/&gt;
 *         &lt;element name="parentIdInChiaro" type="{common.acaris.acta.doqui.it}string"/&gt;
 *         &lt;element name="nome" type="{backoffice.acaris.acta.doqui.it}NomeType"/&gt;
 *         &lt;element name="cognome" type="{backoffice.acaris.acta.doqui.it}CognomeType"/&gt;
 *         &lt;element name="codiceFiscale" type="{common.acaris.acta.doqui.it}CodiceFiscaleType"/&gt;
 *         &lt;element name="email" type="{backoffice.acaris.acta.doqui.it}EmailType"/&gt;
 *         &lt;element name="matricola" type="{backoffice.acaris.acta.doqui.it}MatricolaType"/&gt;
 *         &lt;element name="note" type="{backoffice.acaris.acta.doqui.it}NoteType"/&gt;
 *         &lt;element name="codiceIdentitaList" type="{backoffice.acaris.acta.doqui.it}CodiceIdentitaType" maxOccurs="unbounded" minOccurs="0"/&gt;
 *         &lt;element name="dataInizioValidita" type="{backoffice.acaris.acta.doqui.it}DataValiditaType"/&gt;
 *         &lt;element name="dataFineValidita" type="{backoffice.acaris.acta.doqui.it}DataValiditaType"/&gt;
 *         &lt;element name="valido" type="{backoffice.acaris.acta.doqui.it}ValidoType"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/extension&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "UtentePropertiesType", namespace = "backoffice.acaris.acta.doqui.it", propOrder = {
    "parentId",
    "parentIdInChiaro",
    "nome",
    "cognome",
    "codiceFiscale",
    "email",
    "matricola",
    "note",
    "codiceIdentitaList",
    "dataInizioValidita",
    "dataFineValidita",
    "valido"
})
public class UtentePropertiesType
    extends BackOfficePropertiesType
{

    @XmlElement(required = true)
    protected ObjectIdType parentId;
    @XmlElement(required = true)
    protected String parentIdInChiaro;
    @XmlElement(required = true)
    protected String nome;
    @XmlElement(required = true)
    protected String cognome;
    @XmlElement(required = true)
    protected CodiceFiscaleType codiceFiscale;
    @XmlElement(required = true)
    protected String email;
    @XmlElement(required = true)
    protected String matricola;
    @XmlElement(required = true)
    protected String note;
    protected List<String> codiceIdentitaList;
    @XmlElement(required = true)
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar dataInizioValidita;
    @XmlElement(required = true)
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar dataFineValidita;
    protected boolean valido;

    /**
     * Recupera il valore della proprietÓ parentId.
     * 
     * @return
     *     possible object is
     *     {@link ObjectIdType }
     *     
     */
    public ObjectIdType getParentId() {
        return parentId;
    }

    /**
     * Imposta il valore della proprietÓ parentId.
     * 
     * @param value
     *     allowed object is
     *     {@link ObjectIdType }
     *     
     */
    public void setParentId(ObjectIdType value) {
        this.parentId = value;
    }

    /**
     * Recupera il valore della proprietÓ parentIdInChiaro.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getParentIdInChiaro() {
        return parentIdInChiaro;
    }

    /**
     * Imposta il valore della proprietÓ parentIdInChiaro.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setParentIdInChiaro(String value) {
        this.parentIdInChiaro = value;
    }

    /**
     * Recupera il valore della proprietÓ nome.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNome() {
        return nome;
    }

    /**
     * Imposta il valore della proprietÓ nome.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNome(String value) {
        this.nome = value;
    }

    /**
     * Recupera il valore della proprietÓ cognome.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCognome() {
        return cognome;
    }

    /**
     * Imposta il valore della proprietÓ cognome.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCognome(String value) {
        this.cognome = value;
    }

    /**
     * Recupera il valore della proprietÓ codiceFiscale.
     * 
     * @return
     *     possible object is
     *     {@link CodiceFiscaleType }
     *     
     */
    public CodiceFiscaleType getCodiceFiscale() {
        return codiceFiscale;
    }

    /**
     * Imposta il valore della proprietÓ codiceFiscale.
     * 
     * @param value
     *     allowed object is
     *     {@link CodiceFiscaleType }
     *     
     */
    public void setCodiceFiscale(CodiceFiscaleType value) {
        this.codiceFiscale = value;
    }

    /**
     * Recupera il valore della proprietÓ email.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEmail() {
        return email;
    }

    /**
     * Imposta il valore della proprietÓ email.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEmail(String value) {
        this.email = value;
    }

    /**
     * Recupera il valore della proprietÓ matricola.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMatricola() {
        return matricola;
    }

    /**
     * Imposta il valore della proprietÓ matricola.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMatricola(String value) {
        this.matricola = value;
    }

    /**
     * Recupera il valore della proprietÓ note.
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
     * Imposta il valore della proprietÓ note.
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
     * Gets the value of the codiceIdentitaList property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the codiceIdentitaList property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getCodiceIdentitaList().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link String }
     * 
     * 
     */
    public List<String> getCodiceIdentitaList() {
        if (codiceIdentitaList == null) {
            codiceIdentitaList = new ArrayList<String>();
        }
        return this.codiceIdentitaList;
    }

    /**
     * Recupera il valore della proprietÓ dataInizioValidita.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDataInizioValidita() {
        return dataInizioValidita;
    }

    /**
     * Imposta il valore della proprietÓ dataInizioValidita.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDataInizioValidita(XMLGregorianCalendar value) {
        this.dataInizioValidita = value;
    }

    /**
     * Recupera il valore della proprietÓ dataFineValidita.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDataFineValidita() {
        return dataFineValidita;
    }

    /**
     * Imposta il valore della proprietÓ dataFineValidita.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDataFineValidita(XMLGregorianCalendar value) {
        this.dataFineValidita = value;
    }

    /**
     * Recupera il valore della proprietÓ valido.
     * 
     */
    public boolean isValido() {
        return valido;
    }

    /**
     * Imposta il valore della proprietÓ valido.
     * 
     */
    public void setValido(boolean value) {
        this.valido = value;
    }

}
