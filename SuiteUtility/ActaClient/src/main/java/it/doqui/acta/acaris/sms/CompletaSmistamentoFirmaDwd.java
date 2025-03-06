
package it.doqui.acta.acaris.sms;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Classe Java per anonymous complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="repositoryId" type="{common.acaris.acta.doqui.it}ObjectIdType"/&gt;
 *         &lt;element name="principalId" type="{common.acaris.acta.doqui.it}PrincipalIdType" minOccurs="0"/&gt;
 *         &lt;element name="idSmistamentoMittente" type="{common.acaris.acta.doqui.it}IDDBType"/&gt;
 *         &lt;element name="idAoo" type="{common.acaris.acta.doqui.it}IDDBType"/&gt;
 *         &lt;element name="dataUltimoFirmatario" type="{common.acaris.acta.doqui.it}timeStamp"/&gt;
 *         &lt;element name="idUtenteUltimoFirmatario" type="{common.acaris.acta.doqui.it}IDDBType"/&gt;
 *         &lt;element name="idNodoUltimoDestinatario" type="{common.acaris.acta.doqui.it}IDDBType"/&gt;
 *         &lt;element name="docFisicoPrincipale" type="{documentservice.acaris.acta.doqui.it}DocumentoFisicoIRC"/&gt;
 *         &lt;element name="docFisiciAllegati" type="{documentservice.acaris.acta.doqui.it}DocumentoFisicoIRC" maxOccurs="unbounded" minOccurs="0"/&gt;
 *         &lt;element name="destinatari" type="{sms.acaris.acta.doqui.it}DestinatarioConNoteType" maxOccurs="unbounded"/&gt;
 *         &lt;element name="firmaAncheAllegati" type="{common.acaris.acta.doqui.it}boolean"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "repositoryId",
    "principalId",
    "idSmistamentoMittente",
    "idAoo",
    "dataUltimoFirmatario",
    "idUtenteUltimoFirmatario",
    "idNodoUltimoDestinatario",
    "docFisicoPrincipale",
    "docFisiciAllegati",
    "destinatari",
    "firmaAncheAllegati"
})
@XmlRootElement(name = "completaSmistamentoFirmaDwd", namespace = "sms.acaris.acta.doqui.it")
public class CompletaSmistamentoFirmaDwd {

    @XmlElement(namespace = "", required = true)
    protected ObjectIdType repositoryId;
    @XmlElement(namespace = "")
    protected PrincipalIdType principalId;
    @XmlElement(namespace = "")
    protected long idSmistamentoMittente;
    @XmlElement(namespace = "")
    protected long idAoo;
    @XmlElement(namespace = "", required = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar dataUltimoFirmatario;
    @XmlElement(namespace = "")
    protected long idUtenteUltimoFirmatario;
    @XmlElement(namespace = "")
    protected long idNodoUltimoDestinatario;
    @XmlElement(namespace = "", required = true)
    protected DocumentoFisicoIRC docFisicoPrincipale;
    @XmlElement(namespace = "")
    protected List<DocumentoFisicoIRC> docFisiciAllegati;
    @XmlElement(namespace = "", required = true)
    protected List<DestinatarioConNoteType> destinatari;
    @XmlElement(namespace = "")
    protected boolean firmaAncheAllegati;

    /**
     * Recupera il valore della proprietà repositoryId.
     * 
     * @return
     *     possible object is
     *     {@link ObjectIdType }
     *     
     */
    public ObjectIdType getRepositoryId() {
        return repositoryId;
    }

    /**
     * Imposta il valore della proprietà repositoryId.
     * 
     * @param value
     *     allowed object is
     *     {@link ObjectIdType }
     *     
     */
    public void setRepositoryId(ObjectIdType value) {
        this.repositoryId = value;
    }

    /**
     * Recupera il valore della proprietà principalId.
     * 
     * @return
     *     possible object is
     *     {@link PrincipalIdType }
     *     
     */
    public PrincipalIdType getPrincipalId() {
        return principalId;
    }

    /**
     * Imposta il valore della proprietà principalId.
     * 
     * @param value
     *     allowed object is
     *     {@link PrincipalIdType }
     *     
     */
    public void setPrincipalId(PrincipalIdType value) {
        this.principalId = value;
    }

    /**
     * Recupera il valore della proprietà idSmistamentoMittente.
     * 
     */
    public long getIdSmistamentoMittente() {
        return idSmistamentoMittente;
    }

    /**
     * Imposta il valore della proprietà idSmistamentoMittente.
     * 
     */
    public void setIdSmistamentoMittente(long value) {
        this.idSmistamentoMittente = value;
    }

    /**
     * Recupera il valore della proprietà idAoo.
     * 
     */
    public long getIdAoo() {
        return idAoo;
    }

    /**
     * Imposta il valore della proprietà idAoo.
     * 
     */
    public void setIdAoo(long value) {
        this.idAoo = value;
    }

    /**
     * Recupera il valore della proprietà dataUltimoFirmatario.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDataUltimoFirmatario() {
        return dataUltimoFirmatario;
    }

    /**
     * Imposta il valore della proprietà dataUltimoFirmatario.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDataUltimoFirmatario(XMLGregorianCalendar value) {
        this.dataUltimoFirmatario = value;
    }

    /**
     * Recupera il valore della proprietà idUtenteUltimoFirmatario.
     * 
     */
    public long getIdUtenteUltimoFirmatario() {
        return idUtenteUltimoFirmatario;
    }

    /**
     * Imposta il valore della proprietà idUtenteUltimoFirmatario.
     * 
     */
    public void setIdUtenteUltimoFirmatario(long value) {
        this.idUtenteUltimoFirmatario = value;
    }

    /**
     * Recupera il valore della proprietà idNodoUltimoDestinatario.
     * 
     */
    public long getIdNodoUltimoDestinatario() {
        return idNodoUltimoDestinatario;
    }

    /**
     * Imposta il valore della proprietà idNodoUltimoDestinatario.
     * 
     */
    public void setIdNodoUltimoDestinatario(long value) {
        this.idNodoUltimoDestinatario = value;
    }

    /**
     * Recupera il valore della proprietà docFisicoPrincipale.
     * 
     * @return
     *     possible object is
     *     {@link DocumentoFisicoIRC }
     *     
     */
    public DocumentoFisicoIRC getDocFisicoPrincipale() {
        return docFisicoPrincipale;
    }

    /**
     * Imposta il valore della proprietà docFisicoPrincipale.
     * 
     * @param value
     *     allowed object is
     *     {@link DocumentoFisicoIRC }
     *     
     */
    public void setDocFisicoPrincipale(DocumentoFisicoIRC value) {
        this.docFisicoPrincipale = value;
    }

    /**
     * Gets the value of the docFisiciAllegati property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the docFisiciAllegati property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getDocFisiciAllegati().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link DocumentoFisicoIRC }
     * 
     * 
     */
    public List<DocumentoFisicoIRC> getDocFisiciAllegati() {
        if (docFisiciAllegati == null) {
            docFisiciAllegati = new ArrayList<DocumentoFisicoIRC>();
        }
        return this.docFisiciAllegati;
    }

    /**
     * Gets the value of the destinatari property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the destinatari property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getDestinatari().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link DestinatarioConNoteType }
     * 
     * 
     */
    public List<DestinatarioConNoteType> getDestinatari() {
        if (destinatari == null) {
            destinatari = new ArrayList<DestinatarioConNoteType>();
        }
        return this.destinatari;
    }

    /**
     * Recupera il valore della proprietà firmaAncheAllegati.
     * 
     */
    public boolean isFirmaAncheAllegati() {
        return firmaAncheAllegati;
    }

    /**
     * Imposta il valore della proprietà firmaAncheAllegati.
     * 
     */
    public void setFirmaAncheAllegati(boolean value) {
        this.firmaAncheAllegati = value;
    }

}
