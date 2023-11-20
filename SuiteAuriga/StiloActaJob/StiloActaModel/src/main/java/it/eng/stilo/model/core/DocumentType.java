/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.stilo.model.common.AbstractEntity;

import javax.persistence.*;

import java.util.Set;

@Entity
@Table(name = "SA_DOCUMENT_TYPE")
public class DocumentType extends AbstractEntity {

    @Id
    @Column(name = "ID", length = 20)
    private String id;

    @Column(name = "NAME", length = 100)
    private String name;

    @Column(name = "DESCRIPTION", length = 100)
    private String description;
    
    @Column(name = "VOCE_TITOLARIO", length = 100)
    private String voceTitolario;
    
    @Column(name = "CREA_VOLUME", nullable = true, length = 5)
    private String creaVolume;

    @OneToMany(mappedBy = "documentType")
    private Set<QueueDocument> queueDocuments;

    @OneToMany(mappedBy = "documentType", cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE},
            orphanRemoval = true)
    private Set<DocumentAttribute> documentsAttribute;

    @Override
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    
    public String getVoceTitolario() {
		return voceTitolario;
	}

	public void setVoceTitolario(String voceTitolario) {
		this.voceTitolario = voceTitolario;
	}

	public Set<QueueDocument> getQueueDocuments() {
        return queueDocuments;
    }

    public void setQueueDocuments(Set<QueueDocument> queueDocuments) {
        this.queueDocuments = queueDocuments;
    }

    public Set<DocumentAttribute> getDocumentsAttribute() {
        return documentsAttribute;
    }

    public void setDocumentsAttribute(Set<DocumentAttribute> documentsAttribute) {
        this.documentsAttribute = documentsAttribute;
    }

	public String getCreaVolume() {
		return creaVolume;
	}

	public void setCreaVolume(String creaVolume) {
		this.creaVolume = creaVolume;
	}

    
}
