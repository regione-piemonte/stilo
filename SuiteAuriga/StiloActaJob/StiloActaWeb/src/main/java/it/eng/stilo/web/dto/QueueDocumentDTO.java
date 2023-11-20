/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import it.eng.stilo.web.network.LocalDateTimeDeserializer;
import it.eng.stilo.web.network.LocalDateTimeSerializer;

import java.time.LocalDateTime;
import java.util.Set;

@JsonPropertyOrder({"identifier", "docType", "title", "statusCode"/* , "statusDescription" */, "creationTime",
	"sendTime", "attempts", "logs"})
@JsonInclude(JsonInclude.Include.NON_NULL)
// @JsonAppend(attrs = {@JsonAppend.Attr(value = "statusDescription")})
public class QueueDocumentDTO extends AbstractDTO {

	private Long identifier;

	private String title;

	private String statusCode;

	private int attempts;

	@JsonSerialize(using = LocalDateTimeSerializer.class)
	@JsonDeserialize(using = LocalDateTimeDeserializer.class)
	private LocalDateTime creationTime;

	@JsonSerialize(using = LocalDateTimeSerializer.class)
	@JsonDeserialize(using = LocalDateTimeDeserializer.class)
	private LocalDateTime sendTime;

	private String aooCode;

	private String structureCode;

	private String nodeCode;
	
	private Set<DocumentLogDTO> logs;

	private String extension;

	private String mimeType;

	private String repositoryPath;

	private DocumentTypeDTO docType;

	private String dossierCode;

	@JsonSerialize(using = LocalDateTimeSerializer.class)
	@JsonDeserialize(using = LocalDateTimeDeserializer.class)
	private LocalDateTime modifiedTime;

	private String documentId;

	private String classificationId;

	private QueueDocumentDTO mainDocumentDTO;

	private Set<QueueDocumentDTO> attachmentsDTO;

	private String classified;

	private String swStructureCode;
	// Method used by @JsonAppend annotation
	/*
	 * public String getStatusDescription() { final EDocumentStatus documentStatus =
	 * EDocumentStatus.resolve(statusCode); return documentStatus != null ? documentStatus.name() : "N/A"; }
	 */

	public Long getIdentifier() {
		return identifier;
	}

	public void setIdentifier(Long identifier) {
		this.identifier = identifier;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(String statusCode) {
		this.statusCode = statusCode;
	}

	public int getAttempts() {
		return attempts;
	}

	public void setAttempts(int attempts) {
		this.attempts = attempts;
	}

	public LocalDateTime getCreationTime() {
		return creationTime;
	}

	public void setCreationTime(LocalDateTime creationTime) {
		this.creationTime = creationTime;
	}

	public LocalDateTime getSendTime() {
		return sendTime;
	}

	public void setSendTime(LocalDateTime sendTime) {
		this.sendTime = sendTime;
	}

	public String getAooCode() {
		return aooCode;
	}

	public void setAooCode(String aooCode) {
		this.aooCode = aooCode;
	}

	public String getStructureCode() {
		return structureCode;
	}

	public void setStructureCode(String structureCode) {
		this.structureCode = structureCode;
	}

	public String getNodeCode() {
		return nodeCode;
	}

	public void setNodeCode(String nodeCode) {
		this.nodeCode = nodeCode;
	}

	public String getRepositoryPath() {
		return repositoryPath;
	}

	public void setRepositoryPath(String repositoryPath) {
		this.repositoryPath = repositoryPath;
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

	public DocumentTypeDTO getDocType() {
		return docType;
	}

	public void setDocType(DocumentTypeDTO docType) {
		this.docType = docType;
	}

	public Set<DocumentLogDTO> getLogs() {
		return logs;
	}

	public void setLogs(Set<DocumentLogDTO> logs) {
		this.logs = logs;
	}

	public String getDossierCode() {
		return dossierCode;
	}

	public void setDossierCode(String dossierCode) {
		this.dossierCode = dossierCode;
	}

	public LocalDateTime getModifiedTime() {
		return modifiedTime;
	}

	public void setModifiedTime(LocalDateTime modifiedTime) {
		this.modifiedTime = modifiedTime;
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

	public QueueDocumentDTO getMainDocumentDTO() {
		return mainDocumentDTO;
	}

	public void setMainDocumentDTO(QueueDocumentDTO mainDocumentDTO) {
		this.mainDocumentDTO = mainDocumentDTO;
	}

	public Set<QueueDocumentDTO> getAttachmentsDTO() {
		return attachmentsDTO;
	}

	public void setAttachmentsDTO(Set<QueueDocumentDTO> attachmentsDTO) {
		this.attachmentsDTO = attachmentsDTO;
	}

	public String getClassified() {
		return classified;
	}

	public void setClassified(String classified) {
		this.classified = classified;
	}

	public String getSwStructureCode() {
		return swStructureCode;
	}

	public void setSwStructureCode(String swStructureCode) {
		this.swStructureCode = swStructureCode;
	}

	@Override
	public String toString() {
		return "QueueDocumentDTO{" + "identifier=" + identifier + ", title='" + title + '\'' + ", statusCode='"
				+ statusCode + '\'' + ", attempts=" + attempts + ", creationTime=" + creationTime + ", sendTime="
				+ sendTime + ", repositoryPath='" + repositoryPath + '\'' + ", dossierCode='" + dossierCode + '\''
				+ ", modifiedTime=" + modifiedTime + '\'' + ", documentId=" + documentId + '\'' + ", classificationId="
				+ classificationId + '\'' + ", mainDocument=" + mainDocumentDTO.getTitle() + '\'' + ", classified="
				+ classified + '\'' + ", swStructureCode=" + swStructureCode + '\'' + '}';
	}
}
