
package it.doqui.acta.acaris.object;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per CommonPropertiesType complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="CommonPropertiesType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{common.acaris.acta.doqui.it}PropertiesType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="objectId" type="{common.acaris.acta.doqui.it}ObjectIdType"/&gt;
 *         &lt;element name="changeToken" type="{common.acaris.acta.doqui.it}ChangeTokenType"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/extension&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CommonPropertiesType", namespace = "common.acaris.acta.doqui.it", propOrder = {
    "objectId",
    "changeToken"
})
@XmlSeeAlso({
    AnnotazioniPropertiesType.class,
    ProtocolloPropertiesType.class,
    ArchivePropertiesType.class
})
public abstract class CommonPropertiesType
    extends PropertiesType
{

    @XmlElement(namespace = "", required = true)
    protected ObjectIdType objectId;
    @XmlElement(namespace = "", required = true)
    protected ChangeTokenType changeToken;

    /**
     * Recupera il valore della proprietà objectId.
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
     * Imposta il valore della proprietà objectId.
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
     * Recupera il valore della proprietà changeToken.
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
     * Imposta il valore della proprietà changeToken.
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
