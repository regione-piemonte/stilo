/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.stilo.logic.exception.DatabaseException;
import it.eng.stilo.logic.service.GenericDataAccessEJB;
import it.eng.stilo.model.core.DocumentType;
import it.eng.stilo.model.core.DocumentType_;
import it.eng.stilo.web.dto.DocumentTypeDTO;

import javax.ejb.EJB;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

import java.util.List;
import java.util.Optional;

@Path("/documentType")
public class DocumentTypeResource extends AbstractResource<DocumentType, DocumentTypeDTO> {

    private final String mappingFileBase = "documenttype-mapper.xml";

    @EJB
    private GenericDataAccessEJB<DocumentType> genericDataAccessEJB;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<DocumentTypeDTO> getAll() {
        logger.info(getClass().getSimpleName() + "-getAll");
        final List<DocumentType> documentTypeList = genericDataAccessEJB.loadList(DocumentType.class);
        logger.info(getClass().getSimpleName() + "-getAll{#" + documentTypeList.size() + "}");

        return mapEntity(documentTypeList, DocumentTypeDTO.class, mappingFileBase);
    }

    @GET
    @Path("/id/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public DocumentTypeDTO getId(@PathParam("id") String documentId) throws DatabaseException {
        logger.info(getClass().getSimpleName() + "-getId");
        final DocumentType documentType = genericDataAccessEJB.load(DocumentType.class, documentId);

        return documentType != null ? mapEntity(documentType, DocumentTypeDTO.class, mappingFileBase) : null;
    }

    @GET
    @Path("/name/{name}")
    @Produces(MediaType.APPLICATION_JSON)
    public DocumentTypeDTO getCode(@PathParam("name") String documentName) throws DatabaseException {
        logger.info(getClass().getSimpleName() + "-getCode");
        final DocumentType documentType = genericDataAccessEJB.load(DocumentType.class, DocumentType_.name,
                documentName);

        return documentType != null ? mapEntity(documentType, DocumentTypeDTO.class, mappingFileBase) : null;
    }

    @GET
    @Path("/{name}")
    @Produces(MediaType.APPLICATION_JSON)
    public DocumentTypeDTO getWithAttributes(@PathParam("name") String documentName) throws DatabaseException {
        logger.info(getClass().getSimpleName() + "-getWithAttributes");
        final DocumentType exampleDocType = new DocumentType();
        exampleDocType.setName(documentName);
        final DocumentType documentType = genericDataAccessEJB.findUnique(exampleDocType, DocumentType.class,
                DocumentType_.documentsAttribute);

        return documentType != null ? mapEntity(documentType, DocumentTypeDTO.class, mappingFileBase,
                Optional.of(B_MAPPING_ID)) : null;
    }

    @POST
    public boolean add(final DocumentTypeDTO documentTypeDTO) {
        logger.info(getClass().getSimpleName() + "-add");
        boolean added = false;
        DocumentType documentType = mapTransfer(documentTypeDTO, DocumentType.class, mappingFileBase);
        try {
            genericDataAccessEJB.persist(documentType);
            added = true;
        } catch (DatabaseException e) {
            logger.error("", e);
        }

        return added;
    }

    @PUT
    public boolean update(final DocumentTypeDTO documentTypeDTO) {
        logger.info(getClass().getSimpleName() + "-update");
        boolean added = false;
        DocumentType documentType = mapTransfer(documentTypeDTO, DocumentType.class, mappingFileBase,
                Optional.of(C_MAPPING_ID));
        try {
            genericDataAccessEJB.merge(documentType);
            added = true;
        } catch (DatabaseException e) {
            logger.error("", e);
        }

        return added;
    }

}
