
package it.doqui.acta.acaris.repository;

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
 *         &lt;element name="principalId" type="{common.acaris.acta.doqui.it}PrincipalIdType"/&gt;
 *         &lt;element name="classificazioneDiPartenzaId" type="{common.acaris.acta.doqui.it}ObjectIdType"/&gt;
 *         &lt;element name="folderId" type="{common.acaris.acta.doqui.it}ObjectIdType"/&gt;
 *         &lt;element name="params" type="{common.acaris.acta.doqui.it}VarargsType"/&gt;
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
    "classificazioneDiPartenzaId",
    "folderId",
    "params"
})
@XmlRootElement(name = "aggiungiClassificazione")
public class AggiungiClassificazione {

    @XmlElement(required = true)
    protected ObjectIdType repositoryId;
    @XmlElement(required = true)
    protected PrincipalIdType principalId;
    @XmlElement(required = true)
    protected ObjectIdType classificazioneDiPartenzaId;
    @XmlElement(required = true)
    protected ObjectIdType folderId;
    @XmlElement(required = true)
    protected VarargsType params;

    /**
     * Recupera il valore della propriet� repositoryId.
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
     * Imposta il valore della propriet� repositoryId.
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
     * Recupera il valore della propriet� principalId.
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
     * Imposta il valore della propriet� principalId.
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
     * Recupera il valore della propriet� classificazioneDiPartenzaId.
     * 
     * @return
     *     possible object is
     *     {@link ObjectIdType }
     *     
     */
    public ObjectIdType getClassificazioneDiPartenzaId() {
        return classificazioneDiPartenzaId;
    }

    /**
     * Imposta il valore della propriet� classificazioneDiPartenzaId.
     * 
     * @param value
     *     allowed object is
     *     {@link ObjectIdType }
     *     
     */
    public void setClassificazioneDiPartenzaId(ObjectIdType value) {
        this.classificazioneDiPartenzaId = value;
    }

    /**
     * Recupera il valore della propriet� folderId.
     * 
     * @return
     *     possible object is
     *     {@link ObjectIdType }
     *     
     */
    public ObjectIdType getFolderId() {
        return folderId;
    }

    /**
     * Imposta il valore della propriet� folderId.
     * 
     * @param value
     *     allowed object is
     *     {@link ObjectIdType }
     *     
     */
    public void setFolderId(ObjectIdType value) {
        this.folderId = value;
    }

    /**
     * Recupera il valore della propriet� params.
     * 
     * @return
     *     possible object is
     *     {@link VarargsType }
     *     
     */
    public VarargsType getParams() {
        return params;
    }

    /**
     * Imposta il valore della propriet� params.
     * 
     * @param value
     *     allowed object is
     *     {@link VarargsType }
     *     
     */
    public void setParams(VarargsType value) {
        this.params = value;
    }

}
