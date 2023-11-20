/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.doqui.acta.acaris.archive.IdFormaDocumentariaType;
import it.doqui.acta.acaris.archive.IdStatoDiEfficaciaType;
import it.doqui.acta.acaris.common.AcarisContentStreamType;
import it.doqui.acta.acaris.common.IdVitalRecordCodeType;
import it.doqui.acta.acaris.common.ObjectIdType;

import java.io.Serializable;
import java.util.Map;

public class DocumentRequestBean implements Serializable {

    private AcarisContentStreamType contentStream;
    private Map<String, String> attributesMap;
    private Map<String, String> dynamicAttributesMap;
    private Map<String, IdVitalRecordCodeType> vitalRecordsMap;
    private IdStatoDiEfficaciaType efficacyTypeId;
    private IdStatoDiEfficaciaType efficacyNonFirmatoTypeId;
    private IdFormaDocumentariaType formatTypeId;
    private ObjectIdType classificationId;
    private String aooCode;
    private String nomeFile;
    private String estensioneFile;
    private String mimetype;
    private boolean isPrimario = true;

    public AcarisContentStreamType getContentStream() {
        return contentStream;
    }

    public void setContentStream(AcarisContentStreamType contentStream) {
        this.contentStream = contentStream;
    }

    public Map<String, String> getAttributesMap() {
        return attributesMap;
    }

    public void setAttributesMap(Map<String, String> attributesMap) {
        this.attributesMap = attributesMap;
    }

    public Map<String, IdVitalRecordCodeType> getVitalRecordsMap() {
        return vitalRecordsMap;
    }

    public void setVitalRecordsMap(Map<String, IdVitalRecordCodeType> vitalRecordsMap) {
        this.vitalRecordsMap = vitalRecordsMap;
    }

    public IdStatoDiEfficaciaType getEfficacyTypeId() {
        return efficacyTypeId;
    }

    public void setEfficacyTypeId(IdStatoDiEfficaciaType efficacyTypeId) {
        this.efficacyTypeId = efficacyTypeId;
    }
    
    public IdStatoDiEfficaciaType getEfficacyNonFirmatoTypeId() {
		return efficacyNonFirmatoTypeId;
	}

	public void setEfficacyNonFirmatoTypeId(IdStatoDiEfficaciaType efficacyNonFirmatoTypeId) {
		this.efficacyNonFirmatoTypeId = efficacyNonFirmatoTypeId;
	}

	public IdFormaDocumentariaType getFormatTypeId() {
        return formatTypeId;
    }

    public void setFormatTypeId(IdFormaDocumentariaType formatTypeId) {
        this.formatTypeId = formatTypeId;
    }

	public ObjectIdType getClassificationId() {
		return classificationId;
	}

	public void setClassificationId(ObjectIdType classificationId) {
		this.classificationId = classificationId;
	}

    public String getAooCode() {
        return aooCode;
    }

    public void setAooCode(String aooCode) {
        this.aooCode = aooCode;
    }

	public Map<String, String> getDynamicAttributesMap() {
		return dynamicAttributesMap;
	}

	public void setDynamicAttributesMap(Map<String, String> dynamicAttributesMap) {
		this.dynamicAttributesMap = dynamicAttributesMap;
	}

	public String getNomeFile() {
		return nomeFile;
	}

	public void setNomeFile(String nomeFile) {
		this.nomeFile = nomeFile;
	}

	public String getEstensioneFile() {
		return estensioneFile;
	}

	public void setEstensioneFile(String estensioneFile) {
		this.estensioneFile = estensioneFile;
	}

	public String getMimetype() {
		return mimetype;
	}

	public void setMimetype(String mimetype) {
		this.mimetype = mimetype;
	}

	public boolean isPrimario() {
		return isPrimario;
	}

	public void setPrimario(boolean isPrimario) {
		this.isPrimario = isPrimario;
	}
	
	
    
    
}
