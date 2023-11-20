/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.stilo.logic.service.StorageEJB;
import it.eng.stilo.model.core.UserStorage;
import it.eng.stilo.web.dto.UserStorageDTO;

import javax.ejb.EJB;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import java.util.List;

@Path("/userstorage")
public class UserStorageResource extends AbstractResource<UserStorage, UserStorageDTO> {

    private final String mappingFileBase = "user-storage-mapper.xml";

    @EJB
    private StorageEJB storageEJB;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<UserStorageDTO> getAll() {
        logger.info(getClass().getSimpleName() + "-getAll");
        final List<UserStorage> userStorageList = storageEJB.loadList(UserStorage.class);
        logger.info(getClass().getSimpleName() + "-getAll{#" + userStorageList.size() + "}");

        return mapEntity(userStorageList, UserStorageDTO.class, mappingFileBase);
    }

}
