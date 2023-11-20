/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.stilo.model.common.AbstractEntity;
import it.eng.stilo.model.converter.ZipConverter;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "SA_DOCUMENT_LOG")
public class DocumentLog extends AbstractEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "doc_log_seq")
    @SequenceGenerator(name = "doc_log_seq", sequenceName = "SA_DOC_LOG_SEQ", allocationSize = 1)
    @Column(name = "ID")
    private Long id;

    @Column(name = "VALUE")
    @Lob
   // @Convert(converter = ZipConverter.class)
    private String value;

    @Column(name = "INSERTED", nullable = false)
    @CreationTimestamp
    private LocalDateTime inserted;

    @ManyToOne
    @JoinColumn(name = "QUEUE_DOC")
    private QueueDocument queueDocument;

    @Column(name = "OUT_BOUND", nullable = false)
    private Boolean outBound;

    @Column(name = "ID_FLOW", nullable = false)
    private String flowId;

    @Override
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public LocalDateTime getInserted() {
        return inserted;
    }

    public void setInserted(LocalDateTime inserted) {
        this.inserted = inserted;
    }

    public QueueDocument getQueueDocument() {
        return queueDocument;
    }

    public void setQueueDocument(QueueDocument queueDocument) {
        this.queueDocument = queueDocument;
    }

    public Boolean getOutBound() {
        return outBound;
    }

    public void setOutBound(Boolean outBound) {
        this.outBound = outBound;
    }

    public String getFlowId() {
        return flowId;
    }

    public void setFlowId(String flowId) {
        this.flowId = flowId;
    }
}
