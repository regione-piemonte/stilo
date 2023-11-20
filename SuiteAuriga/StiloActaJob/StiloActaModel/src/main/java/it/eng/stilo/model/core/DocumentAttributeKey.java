/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public class DocumentAttributeKey implements Serializable {

    @Column(name = "ID_DOCUMENT_TYPE")
    private String documentTypeId;

    @Column(name = "ID_ATTRIBUTE_TYPE")
    private String attributeTypeId;

    public String getDocumentTypeId() {
        return documentTypeId;
    }

    public void setDocumentTypeId(String documentTypeId) {
        this.documentTypeId = documentTypeId;
    }

    public String getAttributeTypeId() {
        return attributeTypeId;
    }

    public void setAttributeTypeId(String attributeTypeId) {
        this.attributeTypeId = attributeTypeId;
    }
}
