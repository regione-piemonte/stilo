/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.stilo.logic.exception.DatabaseException;
import it.eng.stilo.logic.jpa.SingleRootCriteriaQuery;
import it.eng.stilo.model.core.DocumentLog;
import it.eng.stilo.model.core.DocumentLog_;

import javax.ejb.Stateless;
import javax.persistence.PersistenceException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Stateless
public class DocumentLogEJB extends GenericDataAccessEJB<DocumentLog> {

    public List<DocumentLog> find(final LocalDateTime timeFrom, final LocalDateTime timeTo) throws DatabaseException {
        logger.info(getClass().getSimpleName() + "-find[from:" + timeFrom + "][to:" + timeTo + "]");

        final CriteriaBuilder cb = em.getCriteriaBuilder();
        final SingleRootCriteriaQuery<DocumentLog> container = helper.criteria(cb, DocumentLog.class);

        final List<Predicate> predicates = new ArrayList<>();
        if (timeFrom != null) {
            predicates.add(cb.greaterThanOrEqualTo(container.getRoot().get(DocumentLog_.inserted), timeFrom));
        }
        if (timeTo != null) {
            predicates.add(cb.lessThan(container.getRoot().get(DocumentLog_.inserted), timeTo));
        }

        container.getCriteria().where(predicates.stream().toArray(Predicate[]::new));
        container.getCriteria().orderBy(cb.desc(container.getRoot().get(DocumentLog_.inserted)));
        try {
            return this.executeFind(container, null).getEntries();
        } catch (final PersistenceException e) {
            throw new DatabaseException(e);
        }
    }

}
