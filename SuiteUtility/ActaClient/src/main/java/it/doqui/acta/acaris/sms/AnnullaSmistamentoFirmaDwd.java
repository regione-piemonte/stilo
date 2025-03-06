
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
 *         &lt;element name="dataRifiuto" type="{common.acaris.acta.doqui.it}timeStamp"/&gt;
 *         &lt;element name="idUtenteRifiuto" type="{common.acaris.acta.doqui.it}IDDBType"/&gt;
 *         &lt;element name="destinatariCompletati" type="{sms.acaris.acta.doqui.it}DestinatarioConNoteType" maxOccurs="unbounded"/&gt;
 *         &lt;element name="destinatarioRifiuta" type="{sms.acaris.acta.doqui.it}DestinatarioConNoteType"/&gt;
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
    "dataRifiuto",
    "idUtenteRifiuto",
    "destinatariCompletati",
    "destinatarioRifiuta"
})
@XmlRootElement(name = "annullaSmistamentoFirmaDwd", namespace = "sms.acaris.acta.doqui.it")
public class AnnullaSmistamentoFirmaDwd {

    @XmlElement(namespace = "", required = true)
    protected ObjectIdType repositoryId;
    @XmlElement(namespace = "")
    protected PrincipalIdType principalId;
    @XmlElement(namespace = "")
    protected long idSmistamentoMittente;
    @XmlElement(namespace = "", required = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar dataRifiuto;
    @XmlElement(namespace = "")
    protected long idUtenteRifiuto;
    @XmlElement(namespace = "", required = true)
    protected List<DestinatarioConNoteType> destinatariCompletati;
    @XmlElement(namespace = "", required = true)
    protected DestinatarioConNoteType destinatarioRifiuta;

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
     * Recupera il valore della proprietà dataRifiuto.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDataRifiuto() {
        return dataRifiuto;
    }

    /**
     * Imposta il valore della proprietà dataRifiuto.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDataRifiuto(XMLGregorianCalendar value) {
        this.dataRifiuto = value;
    }

    /**
     * Recupera il valore della proprietà idUtenteRifiuto.
     * 
     */
    public long getIdUtenteRifiuto() {
        return idUtenteRifiuto;
    }

    /**
     * Imposta il valore della proprietà idUtenteRifiuto.
     * 
     */
    public void setIdUtenteRifiuto(long value) {
        this.idUtenteRifiuto = value;
    }

    /**
     * Gets the value of the destinatariCompletati property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the destinatariCompletati property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getDestinatariCompletati().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link DestinatarioConNoteType }
     * 
     * 
     */
    public List<DestinatarioConNoteType> getDestinatariCompletati() {
        if (destinatariCompletati == null) {
            destinatariCompletati = new ArrayList<DestinatarioConNoteType>();
        }
        return this.destinatariCompletati;
    }

    /**
     * Recupera il valore della proprietà destinatarioRifiuta.
     * 
     * @return
     *     possible object is
     *     {@link DestinatarioConNoteType }
     *     
     */
    public DestinatarioConNoteType getDestinatarioRifiuta() {
        return destinatarioRifiuta;
    }

    /**
     * Imposta il valore della proprietà destinatarioRifiuta.
     * 
     * @param value
     *     allowed object is
     *     {@link DestinatarioConNoteType }
     *     
     */
    public void setDestinatarioRifiuta(DestinatarioConNoteType value) {
        this.destinatarioRifiuta = value;
    }

}
