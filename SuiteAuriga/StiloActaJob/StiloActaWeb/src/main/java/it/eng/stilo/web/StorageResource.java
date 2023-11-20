/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.stilo.logic.service.StorageEJB;
import it.eng.stilo.model.core.Storage;
import it.eng.stilo.web.dto.StorageDTO;

import javax.ejb.EJB;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

@Path("/storage")
public class StorageResource extends AbstractResource<Storage, StorageDTO> {

    private final String mappingFileBase = "storage-user-mapper.xml";

    @EJB
    private StorageEJB storageEJB;

    /**
     * Injected reference to the http servlet response.
     */
    @Context
    private HttpServletResponse response;


    @GET
    @Path("/user")
    @Produces(MediaType.APPLICATION_JSON)
    public List<StorageDTO> getAll() {
        logger.info(getClass().getSimpleName() + "-getAll");
        List<Storage> storageList = null;
        try {
            storageList = new ArrayList<>(storageEJB.loadWithUsers());
        } catch (Exception e) {
            logger.error("", e);
        }
        logger.info(getClass().getSimpleName() + "-getAll{#" + storageList.size() + "}");

        return mapEntity(storageList, StorageDTO.class, mappingFileBase);
    }

    @GET
    @Path("/file/{path}/{extension}")
    public Response getFile(@PathParam("path") String path, @PathParam("extension") String extension) {
        logger.info(getClass().getSimpleName() + "-getFile[" + path + "][" + extension + "]");

        Response.Status responseStatus = Response.Status.FOUND;
        final String outputFileName = (path.contains("/") ? path.substring(path.lastIndexOf("/") + 1) :
                path) + "." + extension;

        try {
            final byte[] bytes = storageEJB.find(path + "." + extension);
            if (bytes != null) {
                try (OutputStream stream = response.getOutputStream()) {
                    response.addHeader("Content-Disposition", "attachment; filename=" + outputFileName);
                    response.addHeader("Content-Length", String.valueOf(bytes.length));
                    response.setContentLength(bytes.length);
                    stream.write(bytes);
                    stream.flush();
                }
            } else {
                logger.warn("FileNotFound[" + path + "][" + extension + "]");
                responseStatus = Response.Status.NOT_FOUND;
            }
        } catch (Exception e) {
            logger.error("", e);
            responseStatus = Response.Status.INTERNAL_SERVER_ERROR;
        }

        return Response.status(responseStatus).build();
    }

}
