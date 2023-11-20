/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.stilo.logic.OperationResult;
import it.eng.stilo.logic.exception.DatabaseException;
import it.eng.stilo.logic.service.*;
import it.eng.stilo.logic.type.ActaEnte;
import it.eng.stilo.model.common.AbstractEntity;
import it.eng.stilo.model.core.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.xml.bind.JAXBException;

import java.time.LocalDateTime;
import java.util.List;

@Stateless
public class StiloFacadeEJB implements RemoteStiloFacadeEJB {

	protected final Logger logger = LoggerFactory.getLogger(this.getClass());

	@EJB
	private ActaIntegrationEJB actaIntegrationEJB;

	@EJB
	private QueueDocumentsEJB queueDocumentsEJB;

	@EJB
	private StorageEJB storageEJB;

	@EJB
	private DocumentLogEJB documentLogEJB;

	@EJB(beanName = "genericDataAccessEJB")
	private GenericDataAccessEJB<DocumentType> genericDataAccessEJB;

	@Override
	public List<QueueDocument> find(final QueueDocument example) throws DatabaseException {
		logger.info(getClass().getSimpleName() + "-find");
		return queueDocumentsEJB.find(example, QueueDocument.class, QueueDocument_.attachments, null);
	}

	@Override
	public void updateSuccessCommunication(final QueueDocument object) throws DatabaseException {
		logger.info(getClass().getSimpleName() + "-updateSuccessCommunication");
		queueDocumentsEJB.updateSuccessCommunication(object);
	}

	@Override
	public void updateFailedCommunication(final QueueDocument object) throws DatabaseException {
		logger.info(getClass().getSimpleName() + "-updateFailedCommunication");
		queueDocumentsEJB.updateFailedCommunication(object);
	}

	@Override
	public void updateCommunication(final List<QueueDocument> objects) throws DatabaseException {
		logger.info(getClass().getSimpleName() + "-updateCommunication");
		queueDocumentsEJB.updateCommunication(objects);
	}

	@Override
	public OperationResult sendDocument(final QueueDocument object) {
		logger.info(getClass().getSimpleName() + "-sendDocument");
		try {
			// Get attributes list for current document type
			object.setDocumentType(genericDataAccessEJB.findUnique(object.getDocumentType(),
					DocumentType.class, DocumentType_.documentsAttribute));
			return actaIntegrationEJB.createDocument(object);
		} catch (DatabaseException e) {
			return new OperationResult(null, OperationResult.ResultType.FAILED);
		}
	}

	@Override
	public OperationResult createVolume(final AdminOrganization organization) {
		logger.info(getClass().getSimpleName() + "-createVolume");
		return actaIntegrationEJB.createFolder(organization, null);
	}

	@Override
	public OperationResult closeVolume(final AdminOrganization organization) {
		logger.info(getClass().getSimpleName() + "-closeVolume");
		return actaIntegrationEJB.closeFolder(organization, null);
	}

	@Override
	public String retrieveVolumeId(final AdminOrganization organization) {
		logger.info(getClass().getSimpleName() + "-retrieveVolumeId");
		
		ActaEnte actaEnte = new ActaEnte();
		String ente = actaEnte.getEnte();
		logger.debug("ente " + ente);
		
		String dbKeyTitolario = actaEnte.getDbKeyTitolario();
		logger.debug("dbKeyTitolario " + dbKeyTitolario);
		
		return actaIntegrationEJB.findVolume(organization, ente, dbKeyTitolario);
	}

	@Override
	public byte[] find(final String uri) throws DatabaseException, JAXBException {
		logger.info(getClass().getSimpleName() + "-find");
		return storageEJB.find(uri);
	}

	@Override
	public List<DocumentLog> find(final LocalDateTime logTimeFrom, final LocalDateTime logTimeTo)
			throws DatabaseException {
		logger.info(getClass().getSimpleName() + "-find");
		return documentLogEJB.find(logTimeFrom, logTimeTo);
	}

	@Override
	public <T extends AbstractEntity> T load(final Class<T> clazz, final Object id) throws DatabaseException {
		return storageEJB.load(clazz, id);
	}

	@Override
	public <T extends AbstractEntity> void persist(final T entity) throws DatabaseException {
		storageEJB.persist(entity);
	}

	@Override
	public <T extends AbstractEntity> void merge(final T entity) throws DatabaseException {
		storageEJB.merge(entity);
	}

	@Override
	public <T extends AbstractEntity> void remove(final T entity) throws DatabaseException {
		storageEJB.remove(entity);
	}

}
