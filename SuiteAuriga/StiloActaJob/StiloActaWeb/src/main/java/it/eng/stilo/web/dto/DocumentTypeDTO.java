/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.Set;

public class DocumentTypeDTO extends AbstractDTO {

    private String typeCode;

    private String typeName;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String typeDesc;

    @JsonIgnore
    private Set<QueueDocumentDTO> queueDocumentsDTO;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Set<DocumentAttributeDTO> documentAttributesDTO;

    public String getTypeCode() {
        return typeCode;
    }

    public void setTypeCode(String typeCode) {
        this.typeCode = typeCode;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public String getTypeDesc() {
        return typeDesc;
    }

    public void setTypeDesc(String typeDesc) {
        this.typeDesc = typeDesc;
    }

    public Set<DocumentAttributeDTO> getDocumentAttributesDTO() {
        return documentAttributesDTO;
    }

    public void setDocumentAttributesDTO(Set<DocumentAttributeDTO> documentAttributesDTO) {
        this.documentAttributesDTO = documentAttributesDTO;
    }


}
