
package it.doqui.acta.acaris.management;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per SimpleResponseType complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="SimpleResponseType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="objectId" type="{common.acaris.acta.doqui.it}ObjectIdType"/&gt;
 *         &lt;element name="changeToken" type="{common.acaris.acta.doqui.it}ChangeTokenType" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "SimpleResponseType", namespace = "common.acaris.acta.doqui.it", propOrder = {
    "objectId",
    "changeToken"
})
public class SimpleResponseType {

    @XmlElement(namespace = "", required = true)
    protected ObjectIdType objectId;
    @XmlElement(namespace = "")
    protected ChangeTokenType changeToken;

    /**
     * Recupera il valore della proprietÓ objectId.
     * 
     * @return
     *     possible object is
     *     {@link ObjectIdType }
     *     
     */
    public ObjectIdType getObjectId() {
        return objectId;
    }

    /**
     * Imposta il valore della proprietÓ objectId.
     * 
     * @param value
     *     allowed object is
     *     {@link ObjectIdType }
     *     
     */
    public void setObjectId(ObjectIdType value) {
        this.objectId = value;
    }

    /**
     * Recupera il valore della proprietÓ changeToken.
     * 
     * @return
     *     possible object is
     *     {@link ChangeTokenType }
     *     
     */
    public ChangeTokenType getChangeToken() {
        return changeToken;
    }

    /**
     * Imposta il valore della proprietÓ changeToken.
     * 
     * @param value
     *     allowed object is
     *     {@link ChangeTokenType }
     *     
     */
    public void setChangeToken(ChangeTokenType value) {
        this.changeToken = value;
    }

}
