
package it.doqui.acta.acaris.sms;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Classe Java per ClassificazionePropertiesType complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="ClassificazionePropertiesType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{archive.acaris.acta.doqui.it}FolderPropertiesType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="numero" type="{archive.acaris.acta.doqui.it}NumeroType"/&gt;
 *         &lt;element name="codice" type="{archive.acaris.acta.doqui.it}CodiceType"/&gt;
 *         &lt;element name="numeroInput" type="{common.acaris.acta.doqui.it}string"/&gt;
 *         &lt;element name="stato" type="{archive.acaris.acta.doqui.it}enumClassificazioneStatoType"/&gt;
 *         &lt;element name="dataInizio" type="{archive.acaris.acta.doqui.it}DataInizioType"/&gt;
 *         &lt;element name="dataFine" type="{archive.acaris.acta.doqui.it}DataFineType"/&gt;
 *         &lt;element name="utenteCreazione" type="{common.acaris.acta.doqui.it}CodiceFiscaleType"/&gt;
 *         &lt;element name="collocazioneCartacea" type="{archive.acaris.acta.doqui.it}CollocazioneCartaceaType"/&gt;
 *         &lt;element name="copiaCartacea" type="{archive.acaris.acta.doqui.it}CopiaCartaceaType"/&gt;
 *         &lt;element name="cartaceo" type="{archive.acaris.acta.doqui.it}CartaceoType"/&gt;
 *         &lt;element name="legislatura" type="{archive.acaris.acta.doqui.it}LegislaturaType"/&gt;
 *         &lt;element name="idAnnotazioniList" type="{common.acaris.acta.doqui.it}IdAnnotazioniType" maxOccurs="unbounded" minOccurs="0"/&gt;
 *         &lt;element name="idMovimentazioneList" type="{common.acaris.acta.doqui.it}IdMovimentazioneType" maxOccurs="unbounded" minOccurs="0"/&gt;
 *         &lt;element name="idStatoDocumento" type="{archive.acaris.acta.doqui.it}IdStatoDocumentoType"/&gt;
 *         &lt;element name="idTipoClasse" type="{archive.acaris.acta.doqui.it}IdTipoClasseType"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/extension&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ClassificazionePropertiesType", propOrder = {
    "numero",
    "codice",
    "numeroInput",
    "stato",
    "dataInizio",
    "dataFine",
    "utenteCreazione",
    "collocazioneCartacea",
    "copiaCartacea",
    "cartaceo",
    "legislatura",
    "idAnnotazioniList",
    "idMovimentazioneList",
    "idStatoDocumento",
    "idTipoClasse"
})
public class ClassificazionePropertiesType
    extends FolderPropertiesType
{

    protected int numero;
    @XmlElement(required = true)
    protected String codice;
    @XmlElement(required = true)
    protected String numeroInput;
    @XmlElement(required = true)
    @XmlSchemaType(name = "string")
    protected EnumClassificazioneStatoType stato;
    @XmlElement(required = true)
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar dataInizio;
    @XmlElement(required = true)
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar dataFine;
    @XmlElement(required = true)
    protected CodiceFiscaleType utenteCreazione;
    @XmlElement(required = true)
    protected String collocazioneCartacea;
    protected boolean copiaCartacea;
    protected boolean cartaceo;
    @XmlElement(required = true)
    protected String legislatura;
    protected List<IdAnnotazioniType> idAnnotazioniList;
    protected List<IdMovimentazioneType> idMovimentazioneList;
    protected int idStatoDocumento;
    protected int idTipoClasse;

    /**
     * Recupera il valore della proprietÓ numero.
     * 
     */
    public int getNumero() {
        return numero;
    }

    /**
     * Imposta il valore della proprietÓ numero.
     * 
     */
    public void setNumero(int value) {
        this.numero = value;
    }

    /**
     * Recupera il valore della proprietÓ codice.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCodice() {
        return codice;
    }

    /**
     * Imposta il valore della proprietÓ codice.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCodice(String value) {
        this.codice = value;
    }

    /**
     * Recupera il valore della proprietÓ numeroInput.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNumeroInput() {
        return numeroInput;
    }

    /**
     * Imposta il valore della proprietÓ numeroInput.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNumeroInput(String value) {
        this.numeroInput = value;
    }

    /**
     * Recupera il valore della proprietÓ stato.
     * 
     * @return
     *     possible object is
     *     {@link EnumClassificazioneStatoType }
     *     
     */
    public EnumClassificazioneStatoType getStato() {
        return stato;
    }

    /**
     * Imposta il valore della proprietÓ stato.
     * 
     * @param value
     *     allowed object is
     *     {@link EnumClassificazioneStatoType }
     *     
     */
    public void setStato(EnumClassificazioneStatoType value) {
        this.stato = value;
    }

    /**
     * Recupera il valore della proprietÓ dataInizio.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDataInizio() {
        return dataInizio;
    }

    /**
     * Imposta il valore della proprietÓ dataInizio.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDataInizio(XMLGregorianCalendar value) {
        this.dataInizio = value;
    }

    /**
     * Recupera il valore della proprietÓ dataFine.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDataFine() {
        return dataFine;
    }

    /**
     * Imposta il valore della proprietÓ dataFine.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDataFine(XMLGregorianCalendar value) {
        this.dataFine = value;
    }

    /**
     * Recupera il valore della proprietÓ utenteCreazione.
     * 
     * @return
     *     possible object is
     *     {@link CodiceFiscaleType }
     *     
     */
    public CodiceFiscaleType getUtenteCreazione() {
        return utenteCreazione;
    }

    /**
     * Imposta il valore della proprietÓ utenteCreazione.
     * 
     * @param value
     *     allowed object is
     *     {@link CodiceFiscaleType }
     *     
     */
    public void setUtenteCreazione(CodiceFiscaleType value) {
        this.utenteCreazione = value;
    }

    /**
     * Recupera il valore della proprietÓ collocazioneCartacea.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCollocazioneCartacea() {
        return collocazioneCartacea;
    }

    /**
     * Imposta il valore della proprietÓ collocazioneCartacea.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCollocazioneCartacea(String value) {
        this.collocazioneCartacea = value;
    }

    /**
     * Recupera il valore della proprietÓ copiaCartacea.
     * 
     */
    public boolean isCopiaCartacea() {
        return copiaCartacea;
    }

    /**
     * Imposta il valore della proprietÓ copiaCartacea.
     * 
     */
    public void setCopiaCartacea(boolean value) {
        this.copiaCartacea = value;
    }

    /**
     * Recupera il valore della proprietÓ cartaceo.
     * 
     */
    public boolean isCartaceo() {
        return cartaceo;
    }

    /**
     * Imposta il valore della proprietÓ cartaceo.
     * 
     */
    public void setCartaceo(boolean value) {
        this.cartaceo = value;
    }

    /**
     * Recupera il valore della proprietÓ legislatura.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLegislatura() {
        return legislatura;
    }

    /**
     * Imposta il valore della proprietÓ legislatura.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLegislatura(String value) {
        this.legislatura = value;
    }

    /**
     * Gets the value of the idAnnotazioniList property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the idAnnotazioniList property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getIdAnnotazioniList().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link IdAnnotazioniType }
     * 
     * 
     */
    public List<IdAnnotazioniType> getIdAnnotazioniList() {
        if (idAnnotazioniList == null) {
            idAnnotazioniList = new ArrayList<IdAnnotazioniType>();
        }
        return this.idAnnotazioniList;
    }

    /**
     * Gets the value of the idMovimentazioneList property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the idMovimentazioneList property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getIdMovimentazioneList().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link IdMovimentazioneType }
     * 
     * 
     */
    public List<IdMovimentazioneType> getIdMovimentazioneList() {
        if (idMovimentazioneList == null) {
            idMovimentazioneList = new ArrayList<IdMovimentazioneType>();
        }
        return this.idMovimentazioneList;
    }

    /**
     * Recupera il valore della proprietÓ idStatoDocumento.
     * 
     */
    public int getIdStatoDocumento() {
        return idStatoDocumento;
    }

    /**
     * Imposta il valore della proprietÓ idStatoDocumento.
     * 
     */
    public void setIdStatoDocumento(int value) {
        this.idStatoDocumento = value;
    }

    /**
     * Recupera il valore della proprietÓ idTipoClasse.
     * 
     */
    public int getIdTipoClasse() {
        return idTipoClasse;
    }

    /**
     * Imposta il valore della proprietÓ idTipoClasse.
     * 
     */
    public void setIdTipoClasse(int value) {
        this.idTipoClasse = value;
    }

}
