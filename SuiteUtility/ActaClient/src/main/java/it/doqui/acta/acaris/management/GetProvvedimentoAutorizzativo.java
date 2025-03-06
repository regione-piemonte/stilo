
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
 *         &lt;element name="idProvvedimentoAutorizzat" type="{common.acaris.acta.doqui.it}IdProvvedimentoAutorizzatType"/&gt;
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
    "idProvvedimentoAutorizzat"
})
@XmlRootElement(name = "getProvvedimentoAutorizzativo", namespace = "management.acaris.acta.doqui.it")
public class GetProvvedimentoAutorizzativo {

    @XmlElement(required = true)
    protected ObjectIdType repositoryId;
    @XmlElement(required = true)
    protected IdProvvedimentoAutorizzatType idProvvedimentoAutorizzat;

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
     * Recupera il valore della propriet� idProvvedimentoAutorizzat.
     * 
     * @return
     *     possible object is
     *     {@link IdProvvedimentoAutorizzatType }
     *     
     */
    public IdProvvedimentoAutorizzatType getIdProvvedimentoAutorizzat() {
        return idProvvedimentoAutorizzat;
    }

    /**
     * Imposta il valore della propriet� idProvvedimentoAutorizzat.
     * 
     * @param value
     *     allowed object is
     *     {@link IdProvvedimentoAutorizzatType }
     *     
     */
    public void setIdProvvedimentoAutorizzat(IdProvvedimentoAutorizzatType value) {
        this.idProvvedimentoAutorizzat = value;
    }

}
