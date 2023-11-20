/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.stilo.logic.exception.DatabaseException;
import it.eng.stilo.logic.service.QueueDocumentsEJB;
import it.eng.stilo.model.core.QueueDocument;
import it.eng.stilo.model.core.QueueDocument_;
import it.eng.stilo.model.util.EClassificationStatus;
import it.eng.stilo.model.util.EDocumentStatus;
import it.eng.stilo.web.dto.QueueDocumentDTO;

import javax.ejb.EJB;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

import java.util.List;
import java.util.Optional;

@Path("/queuedocument")
public class QueueDocumentResource extends AbstractResource<QueueDocument, QueueDocumentDTO> {

	private final String mappingFileBase = "queuedocument-mapper.xml";

	@EJB
	private QueueDocumentsEJB queueDocumentsEJB;

	/**
	 * Inserts a new {@link QueueDocument} mapping the given {@link QueueDocumentDTO}.
	 *
	 * @param queueDocumentDTO The transfer object
	 * @return <code>true</code> if successfully inserted, <code>false</code> otherwise
	 */
	@POST
	public boolean add(final QueueDocumentDTO queueDocumentDTO) {
		logger.info(getClass().getSimpleName() + "-add");
		boolean added = false;
		QueueDocument queueDocument = mapTransfer(queueDocumentDTO, QueueDocument.class, mappingFileBase,
				Optional.of(D_MAPPING_ID));
		queueDocument.setAttempts(0);

		try {
			queueDocumentsEJB.persist(queueDocument);
			added = true;
		} catch (DatabaseException e) {
			logger.error("", e);
		}

		return added;
	}

	/**
	 * Updates {@link QueueDocument} with the given {@link QueueDocumentDTO}.
	 *
	 * @param queueDocumentDTO The transfer object
	 * @return <code>true</code> if successfully updated, <code>false</code> otherwise
	 */
	@PUT
	public boolean update(final QueueDocumentDTO queueDocumentDTO) {
		logger.info(getClass().getSimpleName() + "-update");
		boolean updated = false;
		QueueDocument queueDocument = mapTransfer(queueDocumentDTO, QueueDocument.class, mappingFileBase, Optional.of(
				D_MAPPING_ID));
		try {
			queueDocumentsEJB.merge(queueDocument);
			updated = true;
		} catch (DatabaseException e) {
			logger.error("", e);
		}

		return updated;
	}

	/**
	 * Returns a list of {@link QueueDocumentDTO} for all {@link QueueDocument} found.
	 *
	 * @return List of transfer objects
	 */
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<QueueDocumentDTO> getAll() {
		logger.info(getClass().getSimpleName() + "-getAll");
		final List<QueueDocument> queueDocumentList = queueDocumentsEJB.loadList(QueueDocument.class);
		logger.info(getClass().getSimpleName() + "-getAll{#" + queueDocumentList.size() + "}");

		return mapEntity(queueDocumentList, QueueDocumentDTO.class, mappingFileBase);
	}

	/**
	 * Returns a list of {@link QueueDocumentDTO} just for {@link QueueDocument} matching the given status.
	 *
	 * @return List of transfer objects
	 */
	@GET
	@Path("/status/{status}")
	@Produces(MediaType.APPLICATION_JSON)
	public List<QueueDocumentDTO> getByStatus(@PathParam("status") String statusCode) {
		logger.info(getClass().getSimpleName() + "-getByStatus");
		final QueueDocument example = new QueueDocument();
		example.setStatus(EDocumentStatus.resolve(statusCode));
		return mapEntity(getData(example), QueueDocumentDTO.class, mappingFileBase);
	}

	/**
	 * Returns a list of {@link QueueDocumentDTO} just for {@link QueueDocument} matching the given classification
	 * status.
	 *
	 * @return List of transfer objects
	 */
	@GET
	@Path("/classified/{classified}")
	@Produces(MediaType.APPLICATION_JSON)
	public List<QueueDocumentDTO> getByClassification(@PathParam("classified") String classifiedCode) {
		logger.info(getClass().getSimpleName() + "-getByClassification");
		final QueueDocument example = new QueueDocument();
		example.setClassified(EClassificationStatus.resolve(classifiedCode));
		return mapEntity(getData(example), QueueDocumentDTO.class, mappingFileBase);
	}

	/**
	 * Returns a list of {@link QueueDocumentDTO} just for {@link QueueDocument} with the given document name. Data on
	 * {@link it.eng.stilo.model.core.DocumentLog} are returned too.
	 *
	 * @param doc The document name
	 * @return List of transfer objects
	 */
	@GET
	@Path("/log/{doc}")
	@Produces(MediaType.APPLICATION_JSON)
	public List<QueueDocumentDTO> getLog(@PathParam("doc") String doc) {
		logger.info(getClass().getSimpleName() + "-getLog[" + doc + "]");
		List<QueueDocument> queueDocumentList = null;

		final QueueDocument example = new QueueDocument();
		example.setName(doc);

		try {
			queueDocumentList = queueDocumentsEJB.find(example, QueueDocument.class, QueueDocument_.documentLogs);
		} catch (DatabaseException e) {
			logger.error("", e);
		}
		return mapEntity(queueDocumentList, QueueDocumentDTO.class, mappingFileBase, Optional.of(B_MAPPING_ID));
	}

	/**
	 * Returns a list of {@link QueueDocumentDTO} just for {@link QueueDocument} with the given document name. Data on
	 * {@link it.eng.stilo.model.core.QueueDocument} are returned too.
	 *
	 * @param doc The document name
	 * @return List of attachment objects
	 */
	@GET
	@Path("/attachment/{doc}")
	@Produces(MediaType.APPLICATION_JSON)
	public List<QueueDocumentDTO> getAttachments(@PathParam("doc") String doc) {
		logger.info(getClass().getSimpleName() + "-getAttachment[" + doc + "]");
		List<QueueDocument> queueDocumentList = null;

		final QueueDocument example = new QueueDocument();
		example.setName(doc);

		try {
			queueDocumentList = queueDocumentsEJB.find(example, QueueDocument.class, QueueDocument_.attachments);
		} catch (DatabaseException e) {
			logger.error("", e);
		}
		return mapEntity(queueDocumentList, QueueDocumentDTO.class, mappingFileBase, Optional.of(C_MAPPING_ID));
	}

	private List<QueueDocument> getData(final QueueDocument example) {
		logger.info(getClass().getSimpleName() + "-getData");
		List<QueueDocument> queueDocumentList = null;
		try {
			queueDocumentList = queueDocumentsEJB.find(example, QueueDocument.class);
			logger.info(getClass().getSimpleName() + "-getData{#" + queueDocumentList.size() + "}");
		} catch (DatabaseException e) {
			logger.error("", e);
		}

		return queueDocumentList;
	}

}
