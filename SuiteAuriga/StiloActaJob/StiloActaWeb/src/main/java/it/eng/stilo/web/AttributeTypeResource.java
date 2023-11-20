/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.stilo.logic.exception.DatabaseException;
import it.eng.stilo.logic.service.GenericDataAccessEJB;
import it.eng.stilo.model.core.AttributeType;
import it.eng.stilo.model.core.AttributeType_;
import it.eng.stilo.web.dto.AttributeTypeDTO;

import javax.ejb.EJB;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

import java.util.List;
import java.util.Optional;

@Path("/attributeType")
public class AttributeTypeResource extends AbstractResource<AttributeType, AttributeTypeDTO> {

    private final String mappingFileBase = "attributetype-mapper.xml";

    @EJB
    private GenericDataAccessEJB<AttributeType> genericDataAccessEJB;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<AttributeTypeDTO> getAll() {
        logger.info(getClass().getSimpleName() + "-getAll");
        final List<AttributeType> attributeTypeList = genericDataAccessEJB.loadList(AttributeType.class);
        logger.info(getClass().getSimpleName() + "-getAll{#" + attributeTypeList.size() + "}");

        return mapEntity(attributeTypeList, AttributeTypeDTO.class, mappingFileBase);
    }

    @GET
    @Path("/{name}")
    @Produces(MediaType.APPLICATION_JSON)
    public AttributeTypeDTO get(@PathParam("name") String attributeName) throws DatabaseException {
        logger.info(getClass().getSimpleName() + "-get");
        final AttributeType exampleAttrType = new AttributeType();
        exampleAttrType.setName(attributeName);
        final AttributeType attributeType = genericDataAccessEJB.findUnique(exampleAttrType, AttributeType.class,
                AttributeType_.documentsAttribute);

        return mapEntity(attributeType, AttributeTypeDTO.class, mappingFileBase, Optional.of(B_MAPPING_ID));
    }

    @POST
    public boolean add(final AttributeTypeDTO attributeTypeDTO) {
        logger.info(getClass().getSimpleName() + "-add");
        boolean added = false;
        AttributeType attributeType = mapTransfer(attributeTypeDTO, AttributeType.class, mappingFileBase);

        try {
            genericDataAccessEJB.persist(attributeType);
            added = true;
        } catch (DatabaseException e) {
            logger.error("", e);
        }

        return added;
    }

    /**
     * Updates {@link AttributeType} with the given {@link AttributeTypeDTO}.
     *
     * @param attributeTypeDTO The transfer object
     * @return <code>true</code> if successfully updated, <code>false</code> otherwise
     */
    @PUT
    public boolean update(final AttributeTypeDTO attributeTypeDTO) {
        logger.info(getClass().getSimpleName() + "-update");
        boolean updated = false;
        AttributeType attributeType = mapTransfer(attributeTypeDTO, AttributeType.class, mappingFileBase);
        try {
            genericDataAccessEJB.merge(attributeType);
            updated = true;
        } catch (DatabaseException e) {
            logger.error("", e);
        }

        return updated;
    }

}
