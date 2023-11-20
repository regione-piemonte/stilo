/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.stilo.logic.OperationResult;
import it.eng.stilo.logic.exception.DatabaseException;
import it.eng.stilo.model.common.AbstractEntity;
import it.eng.stilo.model.core.AdminOrganization;
import it.eng.stilo.model.core.DocumentLog;
import it.eng.stilo.model.core.QueueDocument;

import javax.ejb.Remote;
import javax.xml.bind.JAXBException;

import java.time.LocalDateTime;
import java.util.List;

@Remote
public interface RemoteStiloFacadeEJB {

	/**
	 * Find a list of {@link QueueDocument}.
	 *
	 * @param example the example document
	 * @return list of documents
	 * @throws DatabaseException
	 */
	List<QueueDocument> find(final QueueDocument example) throws DatabaseException;

	/**
	 * Update a {@link QueueDocument} after a successful communication.
	 *
	 * @param object the updating document
	 * @throws DatabaseException
	 */
	void updateSuccessCommunication(final QueueDocument object) throws DatabaseException;

	/**
	 * Update a {@link QueueDocument} after a failed communication.
	 *
	 * @param object the updating document
	 * @throws DatabaseException
	 */
	void updateFailedCommunication(final QueueDocument object) throws DatabaseException;

	/**
	 * Update a list of {@link QueueDocument} after a communication.
	 *
	 * @param objects the list of updating document
	 * @throws DatabaseException
	 */
	void updateCommunication(final List<QueueDocument> objects) throws DatabaseException;

	/**
	 * Send a document.
	 *
	 * @param object The document
	 * @return The operation result.
	 */
	OperationResult sendDocument(final QueueDocument object);

	/**
	 * Create a volume.
	 *
	 * @param organization The organization whose folder is requested.
	 * @return The operation result.
	 */
	OperationResult createVolume(final AdminOrganization organization);

	/**
	 * Close a volume.
	 *
	 * @param organization The organization whose folder is requested.
	 * @return The operation result.
	 */
	OperationResult closeVolume(final AdminOrganization organization);

	/**
	 * Retrieve the volume identifier.
	 *
	 * @param organization The organization whose folder is requested.
	 * @return The volume identifier.
	 */
	String retrieveVolumeId(final AdminOrganization organization);

	/**
	 * Get a bytes array from provided uri.
	 *
	 * @param uri The input uri.
	 * @return The bytes array related to provided uri.
	 * @throws DatabaseException
	 */
	byte[] find(final String uri) throws DatabaseException, JAXBException;

	/**
	 * Find a list of {@link DocumentLog} created between the given dates.
	 *
	 * @param logTimeFrom Starting log date
	 * @param logTimeTo   Ending log date
	 * @return list of document log
	 * @throws DatabaseException
	 */
	List<DocumentLog> find(final LocalDateTime logTimeFrom, final LocalDateTime logTimeTo) throws DatabaseException;

	/**
	 * Load the entity having the given identifier.
	 *
	 * @param clazz The entity class.
	 * @param id    The entity identifier.
	 * @param <T>   The generic type.
	 * @return The loaded entity.
	 * @throws DatabaseException
	 */
	<T extends AbstractEntity> T load(final Class<T> clazz, final Object id) throws DatabaseException;

	/**
	 * Persist the given entity.
	 *
	 * @param entity The entity to persist.
	 * @param <T>    The generic type.
	 * @throws DatabaseException
	 */
	<T extends AbstractEntity> void persist(final T entity) throws DatabaseException;

	/**
	 * Merge the given entity.
	 *
	 * @param entity The entity to merge.
	 * @param <T>    The generic type.
	 * @throws DatabaseException
	 */
	<T extends AbstractEntity> void merge(final T entity) throws DatabaseException;

	/**
	 * Remove the given entity.
	 *
	 * @param entity The entity to remove.
	 * @param <T>    The generic type.
	 * @throws DatabaseException
	 */
	<T extends AbstractEntity> void remove(final T entity) throws DatabaseException;

}
