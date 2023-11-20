/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public class AdminOrganizationKey implements Serializable {

    @Column(name = "AOO_CODE", nullable = false, length = 50)
    private String aooCode;

    @Column(name = "DOCUMENT_TYPE_ID")
    private String documentTypeId;

    public String getAooCode() {
        return aooCode;
    }

    public void setAooCode(String aooCode) {
        this.aooCode = aooCode;
    }

    public String getDocumentTypeId() {
        return documentTypeId;
    }

    public void setDocumentTypeId(String documentTypeId) {
        this.documentTypeId = documentTypeId;
    }
}
