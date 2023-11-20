/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.stilo.logic.exception.DatabaseException;
import it.eng.stilo.logic.jpa.CriteriaHelper;
import it.eng.stilo.logic.jpa.SingleRootCriteriaQuery;
import it.eng.stilo.logic.search.QueryAttributes;
import it.eng.stilo.model.common.AbstractEntity;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.PersistenceException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import javax.persistence.metamodel.SetAttribute;
import java.util.List;

@Stateless(name = "genericDataAccessEJB")
public class GenericDataAccessEJB<T extends AbstractEntity> extends DataAccessEJB {

    /**
     * Loads a list of generic {@link T} matching the given example.
     *
     * @param example     The entity to be used as example in the query building.
     * @param entityClass The entity class.
     * @return The list of matching entities.
     * @throws DatabaseException if an exception occurs.
     */
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public List<T> find(final T example, final Class<T> entityClass) throws DatabaseException {
        logger.info(getClass().getSimpleName() + "-find");
        return find(example, entityClass, null, false);
    }

    /**
     * Loads a list of generic {@link T} matching the given example.
     *
     * @param example         The entity to be used as example in the query building.
     * @param entityClass     The entity class.
     * @param queryAttributes The query attributes container.
     * @return The list of matching entities.
     * @throws DatabaseException if an exception occurs.
     */
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public List<T> find(final T example, final Class<T> entityClass, final QueryAttributes queryAttributes) throws DatabaseException {
        logger.info(getClass().getSimpleName() + "-find");
        return find(example, entityClass, queryAttributes, false);
    }

    /**
     * Loads a list of generic {@link T} matching the given example and optionally using 'like' operator.
     *
     * @param example         The entity to be used as example in the query building.
     * @param entityClass     The entity class.
     * @param queryAttributes The query attributes container.
     * @param like            Find by like or not.
     * @return The list of matching entities.
     * @throws DatabaseException if an exception occurs.
     */
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    private List<T> find(final T example, final Class<T> entityClass, final QueryAttributes queryAttributes,
                         final boolean like) throws DatabaseException {
        logger.info(getClass().getSimpleName() + "-find[like=" + like + "]");

        final CriteriaBuilder cb = em.getCriteriaBuilder();
        final SingleRootCriteriaQuery<T> container = helper.criteria(cb, entityClass);

        final CriteriaHelper helper = new CriteriaHelper();
        final List<Predicate> predicates = like ? helper.qbel(container.getRoot(), cb, example) :
                helper.qbe(container.getRoot(), cb, example);
        container.getCriteria().where(predicates.toArray(new Predicate[]{}));

        try {
            return this.executeFind(container, queryAttributes).getEntries();
        } catch (final PersistenceException e) {
            throw new DatabaseException(e);
        }
    }

    /**
     * Loads a list of generic {@link T} matching the given example and fetching the result on the given attribute.
     *
     * @param example     The entity to be used as example in the query building.
     * @param entityClass The entity class.
     * @param attribute   The attribute on which making fetch
     * @return The list of matching entities.
     * @throws DatabaseException if an exception occurs.
     */
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public List<T> find(final T example, final Class<T> entityClass, final SetAttribute<T, ?> attribute) throws DatabaseException {
        logger.info(getClass().getSimpleName() + "-find");
        return find(example, entityClass, attribute, null);
    }

    /**
     * Loads a list of generic {@link T} matching the given example and fetching the result on the given attribute.
     *
     * @param example     The entity to be used as example in the query building.
     * @param entityClass The entity class.
     * @param attribute   The attribute on which making fetch.
     * @param queryAttributes The query attributes container.
     * @return The list of matching entities.
     * @throws DatabaseException if an exception occurs.
     */
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public List<T> find(final T example, final Class<T> entityClass, final SetAttribute<T, ?> attribute, final QueryAttributes queryAttributes) throws DatabaseException {
        logger.info(getClass().getSimpleName() + "-find");

        final CriteriaBuilder cb = em.getCriteriaBuilder();
        final SingleRootCriteriaQuery<T> container = helper.criteria(cb, entityClass);

        final CriteriaHelper helper = new CriteriaHelper();
        final List<Predicate> predicates = helper.qbe(container.getRoot(), cb, example);
        container.getRoot().fetch(attribute, JoinType.LEFT);
        container.getCriteria().where(predicates.toArray(new Predicate[]{}));

        try {
            return this.executeFind(container, queryAttributes).getEntries();
        } catch (final PersistenceException e) {
            throw new DatabaseException(e);
        }
    }

    /**
     * Loads the unique generic {@link T} matching the given example and fetching the result on the given attribute.
     *
     * @param example     The entity to be used as example in the query building.
     * @param entityClass The entity class.
     * @param attribute   The attribute on which making fetch
     * @return The unique matching entity.
     * @throws DatabaseException if an exception occurs.
     */
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public T findUnique(final T example, final Class<T> entityClass, final SetAttribute<T, ?> attribute) throws DatabaseException {
        logger.info(getClass().getSimpleName() + "-findUnique");

        final List<T> entries = find(example, entityClass, attribute);
        if (entries.size() == 1) {
            return entries.get(0);
        }
        throw new DatabaseException("NoUniqueResult-[" + entityClass + "]");
    }

}
