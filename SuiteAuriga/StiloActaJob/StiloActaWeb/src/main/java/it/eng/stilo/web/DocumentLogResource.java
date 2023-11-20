/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.stilo.logic.exception.DatabaseException;
import it.eng.stilo.logic.service.DocumentLogEJB;
import it.eng.stilo.model.core.DocumentLog;
import it.eng.stilo.web.dto.DocumentLogDTO;

import javax.ejb.EJB;
import javax.validation.constraints.NotNull;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

import java.time.LocalDateTime;
import java.util.List;

@Path("/log")
public class DocumentLogResource extends AbstractResource<DocumentLog, DocumentLogDTO> {

	private final String mappingFileBase = "documentlog-mapper.xml";

	@EJB
	private DocumentLogEJB documentLogEJB;

	@GET
	@Path("/{dd}/{mm}/{yyyy}")
	@Produces(MediaType.APPLICATION_JSON)
	public List<DocumentLogDTO> get(@PathParam("dd") int day, @PathParam("mm") int month, @PathParam("yyyy") int year)
			throws DatabaseException {
		return get(day, month, year, 0, 0, 0);
	}

	@GET
	@Path("/{dd}/{mm}/{yyyy}/{hh24}/{mi}/{sec}")
	@Produces(MediaType.APPLICATION_JSON)
	public List<DocumentLogDTO> get(@PathParam("dd") int day, @PathParam("mm") int month, @PathParam("yyyy") int year,
			@PathParam("hh24") int hour, @PathParam("mi") int minute, @PathParam("sec") int second)
			throws DatabaseException {
		logger.info(getClass().getSimpleName() + "-get");
		final List<DocumentLog> documentLogList = documentLogEJB.find(LocalDateTime.of(year, month, day, hour, minute,
				second), null);
		logger.info(getClass().getSimpleName() + "-get{#" + documentLogList.size() + "}");

		return mapEntity(documentLogList, DocumentLogDTO.class, mappingFileBase);
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<DocumentLogDTO> get(@NotNull @QueryParam("from") String dateTimeFrom,
			@QueryParam("to") String dateTimeTo) throws DatabaseException {
		logger.info(getClass().getSimpleName() + "-get");
		final List<DocumentLog> documentLogList = documentLogEJB.find(LocalDateTime.parse(dateTimeFrom),
				dateTimeTo != null ? LocalDateTime.parse(dateTimeTo) : null);
		logger.info(getClass().getSimpleName() + "-get{#" + documentLogList.size() + "}");

		return mapEntity(documentLogList, DocumentLogDTO.class, mappingFileBase);
	}

	@GET
	@Path("/{key}")
	@Produces(MediaType.APPLICATION_JSON)
	public List<DocumentLogDTO> get(@PathParam("key") String key) throws DatabaseException {
		logger.info(getClass().getSimpleName() + "-get");
		final DocumentLog example = new DocumentLog();
		example.setFlowId(key);
		final List<DocumentLog> documentLogList = documentLogEJB.find(example, DocumentLog.class);
		logger.info(getClass().getSimpleName() + "-get{#" + documentLogList.size() + "}");

		return mapEntity(documentLogList, DocumentLogDTO.class, mappingFileBase);
	}

}
