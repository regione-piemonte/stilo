
package it.doqui.acta.acaris.backoffice;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per BackOfficePropertiesType complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="BackOfficePropertiesType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{common.acaris.acta.doqui.it}CommonPropertiesType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="objectTypeId" type="{backoffice.acaris.acta.doqui.it}enumBackOfficeObjectType"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/extension&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "BackOfficePropertiesType", namespace = "backoffice.acaris.acta.doqui.it", propOrder = {
    "objectTypeId"
})
@XmlSeeAlso({
    UtentePropertiesType.class,
    OrganizationUnitPropertiesType.class
})
public abstract class BackOfficePropertiesType
    extends CommonPropertiesType
{

    @XmlElement(required = true)
    @XmlSchemaType(name = "string")
    protected EnumBackOfficeObjectType objectTypeId;

    /**
     * Recupera il valore della proprietà objectTypeId.
     * 
     * @return
     *     possible object is
     *     {@link EnumBackOfficeObjectType }
     *     
     */
    public EnumBackOfficeObjectType getObjectTypeId() {
        return objectTypeId;
    }

    /**
     * Imposta il valore della proprietà objectTypeId.
     * 
     * @param value
     *     allowed object is
     *     {@link EnumBackOfficeObjectType }
     *     
     */
    public void setObjectTypeId(EnumBackOfficeObjectType value) {
        this.objectTypeId = value;
    }

}
