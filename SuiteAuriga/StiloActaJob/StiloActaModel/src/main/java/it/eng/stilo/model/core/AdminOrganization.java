/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.stilo.model.common.AbstractEntity;

import javax.persistence.*;

@Entity
@Table(name = "T_REL_TP_ATTI_VS_NODI_ACTA")
public class AdminOrganization extends AbstractEntity {

    @EmbeddedId
    private AdminOrganizationKey id;

    @ManyToOne
    @MapsId("id")
    @JoinColumn(name = "DOCUMENT_TYPE_ID")
    private DocumentType documentType;

    @Column(name = "NODE_CODE", nullable = false, length = 50)
    private String nodeCode;

    @Column(name = "STRUCTURE_CODE", nullable = false, length = 50)
    private String structureCode;
    
    @Override
    public AdminOrganizationKey getId() {
        return id;
    }

    public void setId(AdminOrganizationKey id) {
        this.id = id;
    }

    public DocumentType getDocumentType() {
        return documentType;
    }

    public void setDocumentType(DocumentType documentType) {
        this.documentType = documentType;
    }

    public String getNodeCode() {
        return nodeCode;
    }

    public void setNodeCode(String nodeCode) {
        this.nodeCode = nodeCode;
    }

    public String getStructureCode() {
        return structureCode;
    }

    public void setStructureCode(String structureCode) {
        this.structureCode = structureCode;
    }
    
}
