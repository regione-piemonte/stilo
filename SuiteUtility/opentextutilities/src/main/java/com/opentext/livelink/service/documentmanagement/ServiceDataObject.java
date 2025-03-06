
package com.opentext.livelink.service.documentmanagement;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per ServiceDataObject complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="ServiceDataObject">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ServiceDataObject")
@XmlSeeAlso({
    Node.class,
    MoveOptions.class,
    NodePageSpecification.class,
    NodePageResult.class,
    Version.class,
    AttributeGroupDefinition.class,
    CategoryInheritance.class,
    NodeRight.class,
    Metadata.class,
    MultilingualMetadata.class,
    NodeRights.class,
    CategoryItemsUpgradeInfo.class,
    AttributeGroup.class,
    NodeAuditRecord.class,
    MetadataLanguage.class,
    CollectionItem.class,
    CopyOptions.class,
    PagedNodeAuditData.class,
    ReportResult.class,
    CompoundDocRelease.class,
    GetNodesInContainerOptions.class,
    NodePosition.class,
    NodeRightUpdateInfo.class,
    NodeContainerInfo.class,
    NodePermissions.class,
    NodeFeature.class,
    NodeVersionInfo.class,
    NodeReservationInfo.class,
    NodeReferenceInfo.class,
    Attribute.class
})
public abstract class ServiceDataObject {


}
