/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.stilo.model.common.AbstractEntity;

import javax.persistence.*;

@Entity
@Table(name = "SA_DOCUMENT_ATTRIBUTE")
public class DocumentAttribute extends AbstractEntity {

    @EmbeddedId
    private DocumentAttributeKey id;

    @ManyToOne
    @MapsId("documentTypeId")
    @JoinColumn(name = "ID_DOCUMENT_TYPE")
    private DocumentType documentType;

    @ManyToOne
    @MapsId("attributeTypeId")
    @JoinColumn(name = "ID_ATTRIBUTE_TYPE")
    private AttributeType attributeType;

    @Column(name = "VALUE", length = 100, nullable = false)
    private String attributeValue;

    @Override
    public DocumentAttributeKey getId() {
        return id;
    }

    public void setId(DocumentAttributeKey id) {
        this.id = id;
    }

    public DocumentType getDocumentType() {
        return documentType;
    }

    public void setDocumentType(DocumentType documentType) {
        this.documentType = documentType;
    }

    public AttributeType getAttributeType() {
        return attributeType;
    }

    public void setAttributeType(AttributeType attributeType) {
        this.attributeType = attributeType;
    }

    public String getAttributeValue() {
        return attributeValue;
    }

    public void setAttributeValue(String attributeValue) {
        this.attributeValue = attributeValue;
    }
}
