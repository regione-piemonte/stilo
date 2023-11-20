/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import it.eng.stilo.web.network.LocalDateTimeDeserializer;
import it.eng.stilo.web.network.LocalDateTimeSerializer;

import java.time.LocalDateTime;
import java.util.Set;

@JsonPropertyOrder({"flowIdentifier", "flowTime", "request", "text"})
public class DocumentLogDTO extends AbstractDTO {

    private String text;

    private Boolean request;

    private String flowIdentifier;

    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime flowTime;

    @JsonIgnore
    private Set<QueueDocumentDTO> queueDocumentsDTO;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Boolean getRequest() {
        return request;
    }

    public void setRequest(Boolean request) {
        this.request = request;
    }

    public String getFlowIdentifier() {
        return flowIdentifier;
    }

    public void setFlowIdentifier(String flowIdentifier) {
        this.flowIdentifier = flowIdentifier;
    }

    public LocalDateTime getFlowTime() {
        return flowTime;
    }

    public void setFlowTime(LocalDateTime flowTime) {
        this.flowTime = flowTime;
    }

    public Set<QueueDocumentDTO> getQueueDocumentsDTO() {
        return queueDocumentsDTO;
    }

    public void setQueueDocumentsDTO(Set<QueueDocumentDTO> queueDocumentsDTO) {
        this.queueDocumentsDTO = queueDocumentsDTO;
    }
}
