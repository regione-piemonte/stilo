/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.stilo.model.common.AbstractEntity;

import javax.persistence.*;

@Entity
@Table(name = "UTILIZZATORI_STORAGE")
public class UserStorage extends AbstractEntity {

    @Id
    @Column(name = "ID_UTILIZZATORE", length = 150, nullable = false)
    private String user;

    @ManyToOne
    @JoinColumn(name = "ID_STORAGE", nullable = false)
    private Storage storage;

    @Override
    public String getId() {
        return user;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public Storage getStorage() {
        return storage;
    }

    public void setStorage(Storage storage) {
        this.storage = storage;
    }

}
