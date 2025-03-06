
package it.doqui.acta.acaris.management;

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
 *         &lt;element name="idAnnotazioni" type="{common.acaris.acta.doqui.it}IdAnnotazioniType"/&gt;
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
    "idAnnotazioni"
})
@XmlRootElement(name = "getAnnotazioni", namespace = "management.acaris.acta.doqui.it")
public class GetAnnotazioni {

    @XmlElement(required = true)
    protected ObjectIdType repositoryId;
    @XmlElement(required = true)
    protected IdAnnotazioniType idAnnotazioni;

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
     * Recupera il valore della proprietà idAnnotazioni.
     * 
     * @return
     *     possible object is
     *     {@link IdAnnotazioniType }
     *     
     */
    public IdAnnotazioniType getIdAnnotazioni() {
        return idAnnotazioni;
    }

    /**
     * Imposta il valore della proprietà idAnnotazioni.
     * 
     * @param value
     *     allowed object is
     *     {@link IdAnnotazioniType }
     *     
     */
    public void setIdAnnotazioni(IdAnnotazioniType value) {
        this.idAnnotazioni = value;
    }

}
