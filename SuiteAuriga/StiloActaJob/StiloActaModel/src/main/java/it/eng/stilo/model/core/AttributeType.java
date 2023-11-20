/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.stilo.model.common.AbstractEntity;

import javax.persistence.*;

import java.util.Set;

@Entity
@Table(name = "SA_ATTRIBUTE_TYPE")
public class AttributeType extends AbstractEntity {

    @Id
    @Column(name = "ID", length = 20)
    private String id;

    @Column(name = "NAME", length = 100)
    private String name;

    @Column(name = "DESCRIPTION", length = 100)
    private String description;

    @OneToMany(mappedBy = "attributeType")
    private Set<DocumentAttribute> documentsAttribute;

    @Override
    public String getId() {
        return this.id;
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

    public Set<DocumentAttribute> getDocumentsAttribute() {
        return documentsAttribute;
    }

    public void setDocumentsAttribute(Set<DocumentAttribute> documentsAttribute) {
        this.documentsAttribute = documentsAttribute;
    }

}
