/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.Set;

public class StorageDTO extends AbstractDTO {

    private String id;

    private Boolean disabled;

    private String type;

    private String configuration;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Set<UserStorageDTO> userStorages;

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

    public Set<UserStorageDTO> getUserStorages() {
        return userStorages;
    }

    public void setUserStorages(Set<UserStorageDTO> userStorages) {
        this.userStorages = userStorages;
    }
}
