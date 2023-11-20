/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.stilo.logic.exception.DatabaseException;
import it.eng.stilo.logic.jpa.SingleRootCriteriaQuery;
import it.eng.stilo.logic.search.QueryAttributes;
import it.eng.stilo.model.core.DocumentType;
import it.eng.stilo.model.core.QueueDocument;
import it.eng.stilo.model.core.QueueDocument_;
import it.eng.stilo.model.util.EDocumentStatus;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.PersistenceException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Predicate;
import java.time.LocalDateTime;
import java.util.List;

@Stateless
public class QueueDocumentsEJB extends GenericDataAccessEJB<QueueDocument> {

    /**
     * Loads a list of {@link QueueDocument} having the given {@link DocumentType}.
     *
     * @param example         The entity to be used as example in the query building.
     * @param queryAttributes The query attributes container.
     * @param documentTypes   The filtering document types.
     * @return The list of found entities.
     * @throws DatabaseException if an exception occurs.
     */
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public List<QueueDocument> findAttachment(final QueueDocument example, final QueryAttributes queryAttributes,
            final List<DocumentType> documentTypes) throws DatabaseException {
        final CriteriaBuilder cb = em.getCriteriaBuilder();
        final SingleRootCriteriaQuery<QueueDocument> container = helper.criteria(cb, QueueDocument.class);
        final List<Predicate> predicates = helper.qbe(container.getRoot(), cb, example);
        //final List<Predicate> predicates = helper.(container.getRoot(), cb, example);

        predicates.add(container.getRoot().get(QueueDocument_.documentType).in(documentTypes));
        container.getCriteria().where(predicates.toArray(new Predicate[predicates.size()]));
        container.getRoot().fetch(QueueDocument_.attachments, JoinType.LEFT);

        try {
            return this.executeFind(container, queryAttributes).getEntries();
        } catch (final PersistenceException e) {
            throw new DatabaseException(e);
        }
    }

    /**
     * Loads a list of {@link QueueDocument} having at least one fixable attachment.
     *
     * @param queryAttributes The query attributes container.
     * @return The list of found entities.
     * @throws DatabaseException if an exception occurs.
     */
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public List<QueueDocument> findFixableAttachment(final QueryAttributes queryAttributes) throws DatabaseException {
        final CriteriaBuilder cb = em.getCriteriaBuilder();
        final SingleRootCriteriaQuery<QueueDocument> container = helper.criteria(cb, QueueDocument.class);
        final Join<QueueDocument, QueueDocument> join = container.getRoot().join(QueueDocument_.attachments,
                JoinType.INNER);
        final Predicate pAttachmentStatus = cb.equal(join.get(QueueDocument_.status), EDocumentStatus.FIXABLE);
        container.getCriteria().where(pAttachmentStatus);
        container.getRoot().fetch(QueueDocument_.attachments, JoinType.INNER);

        try {
            return this.executeFind(container, queryAttributes).getEntries();
        } catch (final PersistenceException e) {
            throw new DatabaseException(e);
        }
    }

    /**
     * Updates a list of {@link QueueDocument}.
     *
     * @param queueDocuments The list of entity to update.
     * @throws DatabaseException if an exception occurs.
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public void update(final List<QueueDocument> queueDocuments) throws DatabaseException {
        logger.info(getClass().getSimpleName() + "-update");

        for (final QueueDocument queueDocument : queueDocuments) {
            merge(queueDocument);
        }
    }


    /**
     * Updates a {@link QueueDocument} after successful communicating.
     *
     * @param object The entity to update.
     * @throws DatabaseException if an exception occurs.
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    @Deprecated
    public void updateSuccessCommunication(final QueueDocument object) throws DatabaseException {
        logger.info(getClass().getSimpleName() + "-updateSuccessCommunication");

        object.setCommunicated(LocalDateTime.now());
        object.setAttempts(object.getAttempts() + 1);
        merge(object);
    }

    /**
     * Updates a {@link QueueDocument} after failed communicating.
     *
     * @param object The entity to update.
     * @throws DatabaseException if an exception occurs.
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    @Deprecated
    public void updateFailedCommunication(final QueueDocument object) throws DatabaseException {
        logger.info(getClass().getSimpleName() + "-updateFailedCommunication");

        object.setAttempts(object.getAttempts() + 1);
        merge(object);
    }

    /**
     * Updates a list of {@link QueueDocument} after communicating.
     *
     * @param objects The list of entity to update.
     * @throws DatabaseException if an exception occurs.
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    @Deprecated
    public void updateCommunication(final List<QueueDocument> objects) throws DatabaseException {
        logger.info(getClass().getSimpleName() + "-updateCommunication");

        final LocalDateTime now = LocalDateTime.now();
        for (final QueueDocument queueDocument : objects) {
            if (queueDocument.getStatus().equals(EDocumentStatus.CONSUMED) || queueDocument.getStatus().equals(EDocumentStatus.FIXABLE)) {
                queueDocument.setCommunicated(now);
            }
            queueDocument.setAttempts(queueDocument.getAttempts() + 1);
            merge(queueDocument);
        }
    }

    /**
     * Updates a list of {@link QueueDocument} after a modification.
     *
     * @param objects The list of entity to update.
     * @throws DatabaseException if an exception occurs.
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    @Deprecated
    public void updateModification(final List<QueueDocument> objects) throws DatabaseException {
        logger.info(getClass().getSimpleName() + "-updateModification");

        final LocalDateTime now = LocalDateTime.now();
        for (final QueueDocument queueDocuments : objects) {
            queueDocuments.setModified(now);
            merge(queueDocuments);
        }
    }

}
