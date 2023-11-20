/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.stilo.model.common.AbstractEntity;
import it.eng.stilo.model.converter.ClassificationStatusConverter;
import it.eng.stilo.model.converter.DocumentStatusConverter;
import it.eng.stilo.model.util.EClassificationStatus;
import it.eng.stilo.model.util.EDocumentStatus;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;

import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Table(name = "SA_QUEUE_DOCUMENT")
public class QueueDocument extends AbstractEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "queue_doc_seq")
	@SequenceGenerator(name = "queue_doc_seq", sequenceName = "SA_QUEUE_DOC_SEQ", allocationSize = 1)
	@Column(name = "ID")
	private Long id;

	@Column(name = "NAME", unique = true)
	private String name;

	@Column(name = "PATH", nullable = false)
	private String path;

	@Column(name = "EXTENSION", nullable = false)
	private String extension;

	@Column(name = "MIME_TYPE", nullable = false)
	private String mimeType;

	@Column(name = "INSERTED", columnDefinition = "timestamp default sysdate")
	@CreationTimestamp
	private LocalDateTime inserted;

	@Column(name = "MODIFIED")
	@CreationTimestamp
	private LocalDateTime modified;

	@ManyToOne
	@JoinColumn(name = "DOCUMENT_TYPE_ID", nullable = false)
	private DocumentType documentType;

	@OneToMany(mappedBy = "queueDocument")
	private Set<DocumentLog> documentLogs;

	@Column(name = "STATUS", columnDefinition = "varchar(10) default 'R'")
	@Convert(converter = DocumentStatusConverter.class)
	private EDocumentStatus status;

	@Column(name = "ATTEMPTS", columnDefinition = "integer default 0")
	private int attempts;

	@Column(name = "COMMUNICATED")
	private LocalDateTime communicated;

	@Column(name = "AOO_CODE", nullable = false, length = 50)
	private String aooCode;

	@Column(name = "STRUCTURE_CODE", nullable = false, length = 50)
	private String structureCode;

	@Column(name = "NODE_CODE", nullable = false, length = 50)
	private String nodeCode;

	@Column(name = "DOSSIER_CODE")
	private String dossierCode;

	@Column(name = "DOCUMENT_ID")
	private String documentId;

	@Column(name = "CLASSIFICATION_ID")
	private String classificationId;

	@ManyToOne
	@JoinColumn(name = "PARENT_ID")
	private QueueDocument mainDocument;

	@OneToMany(mappedBy = "mainDocument", targetEntity = QueueDocument.class, orphanRemoval = true, cascade =
			CascadeType.ALL, fetch = FetchType.EAGER)
	@OrderBy("inserted ASC")
	private Set<QueueDocument> attachments;

	@Column(name = "CLASSIFIED", columnDefinition = "varchar(10) default 'I'")
	@Convert(converter = ClassificationStatusConverter.class)
	private EClassificationStatus classified;

	@Column(name = "SW_STRUCTURE_CODE")
	private String swStructureCode;

	@Override
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getExtension() {
		return extension;
	}

	public void setExtension(String extension) {
		this.extension = extension;
	}

	public String getMimeType() {
		return mimeType;
	}

	public void setMimeType(String mimeType) {
		this.mimeType = mimeType;
	}

	public DocumentType getDocumentType() {
		return documentType;
	}

	public void setDocumentType(DocumentType documentType) {
		this.documentType = documentType;
	}

	public Set<DocumentLog> getDocumentLogs() {
		return documentLogs;
	}

	public void setDocumentLogs(Set<DocumentLog> documentLogs) {
		this.documentLogs = documentLogs;
	}

	public EDocumentStatus getStatus() {
		return status;
	}

	public void setStatus(EDocumentStatus status) {
		this.status = status;
	}

	public LocalDateTime getInserted() {
		return inserted;
	}

	public void setInserted(LocalDateTime inserted) {
		this.inserted = inserted;
	}

	public LocalDateTime getModified() {
		return modified;
	}

	public void setModified(LocalDateTime modified) {
		this.modified = modified;
	}

	public LocalDateTime getCommunicated() {
		return communicated;
	}

	public void setCommunicated(LocalDateTime communicated) {
		this.communicated = communicated;
	}

	public int getAttempts() {
		return attempts;
	}

	public void setAttempts(int attempts) {
		this.attempts = attempts;
	}

	public String getDossierCode() {
		return dossierCode;
	}

	public void setDossierCode(String dossierCode) {
		this.dossierCode = dossierCode;
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

	public String getAooCode() {
		return aooCode;
	}

	public void setAooCode(String aooCode) {
		this.aooCode = aooCode;
	}

	public String getDocumentId() {
		return documentId;
	}

	public void setDocumentId(String documentId) {
		this.documentId = documentId;
	}

	public String getClassificationId() {
		return classificationId;
	}

	public void setClassificationId(String classificationId) {
		this.classificationId = classificationId;
	}

	public QueueDocument getMainDocument() {
		return mainDocument;
	}

	public void setMainDocument(QueueDocument mainDocument) {
		this.mainDocument = mainDocument;
	}

	public Set<QueueDocument> getAttachments() {
		return attachments;
	}

	public void setAttachments(Set<QueueDocument> attachments) {
		this.attachments = attachments;
	}

	public EClassificationStatus getClassified() {
		return classified;
	}

	public void setClassified(EClassificationStatus classified) {
		this.classified = classified;
	}

	public String getSwStructureCode() {
		return swStructureCode;
	}

	public void setSwStructureCode(String swStructureCode) {
		this.swStructureCode = swStructureCode;
	}

}
