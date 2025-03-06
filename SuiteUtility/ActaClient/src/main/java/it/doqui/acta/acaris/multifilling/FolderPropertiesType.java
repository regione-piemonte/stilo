
package it.doqui.acta.acaris.multifilling;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per FolderPropertiesType complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="FolderPropertiesType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{archive.acaris.acta.doqui.it}ArchivePropertiesType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="objectTypeId" type="{archive.acaris.acta.doqui.it}enumArchiveObjectType"/&gt;
 *         &lt;element name="parentId" type="{common.acaris.acta.doqui.it}ObjectIdType"/&gt;
 *         &lt;element name="allowedChildObjectTypeIds" type="{common.acaris.acta.doqui.it}AllowedChildObjectTypeIdsType"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/extension&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "FolderPropertiesType", propOrder = {
    "objectTypeId",
    "parentId",
    "allowedChildObjectTypeIds"
})
@XmlSeeAlso({
    TitolarioPropertiesType.class,
    ClassificazionePropertiesType.class,
    VocePropertiesType.class,
    FascicoloTemporaneoPropertiesType.class,
    DocumentoFisicoPropertiesType.class,
    GruppoAllegatiPropertiesType.class,
    FascicoloStandardType.class,
    AggregazionePropertiesType.class
})
public abstract class FolderPropertiesType
    extends ArchivePropertiesType
{

    @XmlElement(required = true)
    @XmlSchemaType(name = "string")
    protected EnumArchiveObjectType objectTypeId;
    @XmlElement(required = true)
    protected ObjectIdType parentId;
    @XmlElement(required = true)
    protected AllowedChildObjectTypeIdsType allowedChildObjectTypeIds;

    /**
     * Recupera il valore della proprietà objectTypeId.
     * 
     * @return
     *     possible object is
     *     {@link EnumArchiveObjectType }
     *     
     */
    public EnumArchiveObjectType getObjectTypeId() {
        return objectTypeId;
    }

    /**
     * Imposta il valore della proprietà objectTypeId.
     * 
     * @param value
     *     allowed object is
     *     {@link EnumArchiveObjectType }
     *     
     */
    public void setObjectTypeId(EnumArchiveObjectType value) {
        this.objectTypeId = value;
    }

    /**
     * Recupera il valore della proprietà parentId.
     * 
     * @return
     *     possible object is
     *     {@link ObjectIdType }
     *     
     */
    public ObjectIdType getParentId() {
        return parentId;
    }

    /**
     * Imposta il valore della proprietà parentId.
     * 
     * @param value
     *     allowed object is
     *     {@link ObjectIdType }
     *     
     */
    public void setParentId(ObjectIdType value) {
        this.parentId = value;
    }

    /**
     * Recupera il valore della proprietà allowedChildObjectTypeIds.
     * 
     * @return
     *     possible object is
     *     {@link AllowedChildObjectTypeIdsType }
     *     
     */
    public AllowedChildObjectTypeIdsType getAllowedChildObjectTypeIds() {
        return allowedChildObjectTypeIds;
    }

    /**
     * Imposta il valore della proprietà allowedChildObjectTypeIds.
     * 
     * @param value
     *     allowed object is
     *     {@link AllowedChildObjectTypeIdsType }
     *     
     */
    public void setAllowedChildObjectTypeIds(AllowedChildObjectTypeIdsType value) {
        this.allowedChildObjectTypeIds = value;
    }

}
