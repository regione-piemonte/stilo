
package it.doqui.acta.acaris.sms;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


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
 *         &lt;element name="mittente" type="{sms.acaris.acta.doqui.it}MittenteType"/&gt;
 *         &lt;element name="destinatari" type="{sms.acaris.acta.doqui.it}DestinatarioType" maxOccurs="unbounded"/&gt;
 *         &lt;element name="oggettiSmistati" type="{sms.acaris.acta.doqui.it}OggettoSmistamentoType" maxOccurs="unbounded"/&gt;
 *         &lt;element name="infoCreazione" type="{sms.acaris.acta.doqui.it}InfoCreazioneType"/&gt;
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
    "mittente",
    "destinatari",
    "oggettiSmistati",
    "infoCreazione"
})
@XmlRootElement(name = "creaSmistamento", namespace = "sms.acaris.acta.doqui.it")
public class CreaSmistamento {

    @XmlElement(namespace = "", required = true)
    protected ObjectIdType repositoryId;
    @XmlElement(namespace = "")
    protected PrincipalIdType principalId;
    @XmlElement(namespace = "", required = true)
    protected MittenteType mittente;
    @XmlElement(namespace = "", required = true)
    protected List<DestinatarioType> destinatari;
    @XmlElement(namespace = "", required = true)
    protected List<OggettoSmistamentoType> oggettiSmistati;
    @XmlElement(namespace = "", required = true)
    protected InfoCreazioneType infoCreazione;

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
     * Recupera il valore della proprietà mittente.
     * 
     * @return
     *     possible object is
     *     {@link MittenteType }
     *     
     */
    public MittenteType getMittente() {
        return mittente;
    }

    /**
     * Imposta il valore della proprietà mittente.
     * 
     * @param value
     *     allowed object is
     *     {@link MittenteType }
     *     
     */
    public void setMittente(MittenteType value) {
        this.mittente = value;
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
     * {@link DestinatarioType }
     * 
     * 
     */
    public List<DestinatarioType> getDestinatari() {
        if (destinatari == null) {
            destinatari = new ArrayList<DestinatarioType>();
        }
        return this.destinatari;
    }

    /**
     * Gets the value of the oggettiSmistati property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the oggettiSmistati property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getOggettiSmistati().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link OggettoSmistamentoType }
     * 
     * 
     */
    public List<OggettoSmistamentoType> getOggettiSmistati() {
        if (oggettiSmistati == null) {
            oggettiSmistati = new ArrayList<OggettoSmistamentoType>();
        }
        return this.oggettiSmistati;
    }

    /**
     * Recupera il valore della proprietà infoCreazione.
     * 
     * @return
     *     possible object is
     *     {@link InfoCreazioneType }
     *     
     */
    public InfoCreazioneType getInfoCreazione() {
        return infoCreazione;
    }

    /**
     * Imposta il valore della proprietà infoCreazione.
     * 
     * @param value
     *     allowed object is
     *     {@link InfoCreazioneType }
     *     
     */
    public void setInfoCreazione(InfoCreazioneType value) {
        this.infoCreazione = value;
    }

}
