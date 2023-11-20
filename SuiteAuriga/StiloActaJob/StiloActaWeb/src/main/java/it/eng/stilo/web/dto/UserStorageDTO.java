/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import com.fasterxml.jackson.annotation.JsonInclude;

public class UserStorageDTO extends AbstractDTO {

    private String user;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private StorageDTO storage;

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public StorageDTO getStorage() {
        return storage;
    }

    public void setStorage(StorageDTO storage) {
        this.storage = storage;
    }

}
