
package it.doqui.acta.acaris.management;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per acarisRepositoryEntryType complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="acarisRepositoryEntryType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="repositoryId" type="{common.acaris.acta.doqui.it}ObjectIdType"/&gt;
 *         &lt;element name="repositoryName" type="{common.acaris.acta.doqui.it}string"/&gt;
 *         &lt;element name="repositoryURI" type="{common.acaris.acta.doqui.it}string"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "acarisRepositoryEntryType", propOrder = {
    "repositoryId",
    "repositoryName",
    "repositoryURI"
})
public class AcarisRepositoryEntryType {

    @XmlElement(required = true)
    protected ObjectIdType repositoryId;
    @XmlElement(required = true)
    protected String repositoryName;
    @XmlElement(required = true)
    protected String repositoryURI;

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
     * Recupera il valore della propriet� repositoryName.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRepositoryName() {
        return repositoryName;
    }

    /**
     * Imposta il valore della propriet� repositoryName.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRepositoryName(String value) {
        this.repositoryName = value;
    }

    /**
     * Recupera il valore della propriet� repositoryURI.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRepositoryURI() {
        return repositoryURI;
    }

    /**
     * Imposta il valore della propriet� repositoryURI.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRepositoryURI(String value) {
        this.repositoryURI = value;
    }

}
