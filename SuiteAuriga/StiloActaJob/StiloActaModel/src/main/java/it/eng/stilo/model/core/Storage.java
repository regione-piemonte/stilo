/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.stilo.model.common.AbstractEntity;

import javax.persistence.*;

import java.util.Set;

@Entity
@Table(name = "STORAGES")
public class Storage extends AbstractEntity {

    @Id
    @Column(name = "ID_STORAGE", length = 50)
    private String id;

    @Column(name = "FLG_DISATTIVO")
    private Boolean disabled;

    @Column(name = "TIPO_STORAGE", length = 20, nullable = false)
    private String type;

    @Column(name = "XML_CONFIG", length = 2048)
    private String configuration;

    @OneToMany(mappedBy = "storage")
    private Set<UserStorage> userStorages;

    @Override
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Boolean getDisabled() {
        return disabled;
    }

    public void setDisabled(Boolean disabled) {
        this.disabled = disabled;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getConfiguration() {
        return configuration;
    }

    public void setConfiguration(String configuration) {
        this.configuration = configuration;
    }

    public Set<UserStorage> getUserStorages() {
        return userStorages;
    }

    public void setUserStorages(Set<UserStorage> userStorages) {
        this.userStorages = userStorages;
    }

}
