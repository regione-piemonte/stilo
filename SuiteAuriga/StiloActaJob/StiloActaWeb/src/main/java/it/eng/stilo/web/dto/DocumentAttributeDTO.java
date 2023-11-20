/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import com.fasterxml.jackson.annotation.JsonInclude;

public class DocumentAttributeDTO extends AbstractDTO {

    private String value;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private DocumentTypeDTO documentTypeDTO;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private AttributeTypeDTO attributeTypeDTO;


    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public DocumentTypeDTO getDocumentTypeDTO() {
        return documentTypeDTO;
    }

    public void setDocumentTypeDTO(DocumentTypeDTO documentTypeDTO) {
        this.documentTypeDTO = documentTypeDTO;
    }

    public AttributeTypeDTO getAttributeTypeDTO() {
        return attributeTypeDTO;
    }

    public void setAttributeTypeDTO(AttributeTypeDTO attributeTypeDTO) {
        this.attributeTypeDTO = attributeTypeDTO;
    }
}
