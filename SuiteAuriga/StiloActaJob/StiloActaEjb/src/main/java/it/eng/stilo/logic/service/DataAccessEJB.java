/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.stilo.logic.exception.DatabaseException;
import it.eng.stilo.logic.jpa.CriteriaHelper;
import it.eng.stilo.logic.jpa.SingleRootCriteriaQuery;
import it.eng.stilo.logic.search.LimitMode;
import it.eng.stilo.logic.search.QueryAttributes;
import it.eng.stilo.logic.search.Result;
import it.eng.stilo.model.common.AbstractEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import javax.persistence.*;
import javax.persistence.criteria.*;
import javax.persistence.metamodel.SingularAttribute;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

/**
 * This class is a basic EJB that act also as data access object. It provides a list of methods for accessing the
 * database through the entity manager.
 */
public abstract class DataAccessEJB implements Serializable {

    /**
     * Constant for the magic number 100.
     */
    public static final Integer INT_100 = 100;
    protected final Logger logger = LoggerFactory.getLogger(this.getClass());
    /**
     * The entity manager defined into the JPA context.
     */
    @PersistenceContext
    protected EntityManager em;
    /**
     * The reference to the criteria helper that provides convenience methods for creating criteria.
     */
    @Inject
    protected CriteriaHelper helper;

    /**
     * Simply print out the entity class that need to be loaded.
     *
     * @param clazz The class to be loaded.
     */
    private void loadingMessage(final Class<? extends Object> clazz) {
        logger.debug(String.format("[Loading Entity] %s", clazz));
    }

    /**
     * Internal method for rebuilding the joins from an existing CriteriaQuery. It is used for rebuilding count queries
     * from an existing query.
     *
     * @param from The source join list.
     * @param to   The destination join list.
     */
    @SuppressWarnings("rawtypes")
    private void loadJoins(final Join<?, ?> from, final Join<?, ?> to) {
        for (final Join join : from.getJoins()) {
            final Join innerjoin = to.join(join.getAttribute().getName(), join.getJoinType());
            loadJoins(join, innerjoin);
        }
    }

    /**
     * This method simply rebuild a new query starting from an original one. Using JPA the queries cannot be cloned as
     * in the case of Hibernate.
     *
     * @param originalCriteria The original criteria to be rebuild.
     * @param entityRoot       The entity root on which the criteria query will be based.
     */
    protected void rebuildQuery(final CriteriaQuery<?> originalCriteria, final Root<?> entityRoot) {
        final Set<Root<?>> roots = originalCriteria.getRoots();

        for (final Root<?> entity : roots) {
            for (final Join<?, ?> join : entity.getJoins()) {
                final Join<?, ?> rjoin = entityRoot.join(join.getAttribute().getName(), join.getJoinType());
                loadJoins(join, rjoin);
            }
        }
    }

    /**
     * This method build the count criteria starting from an original criteria. The count will be rebuilt using the
     * rebuild and loadJoins methods. The count criteria is the basis for the pagination in the web app context.
     * Pagination can be used also as convenient method for scrolling dataset in the EJB components even if the
     * scrollable results are preferrable.
     *
     * @param <T>      The type of the objects to be counted.
     * @param criteria The original criteria to be "counted".
     * @return The count of the items that are defined in the original query.
     */
    protected <T> Long findCountByCriteria(final CriteriaQuery<?> criteria) {
        final CriteriaBuilder builder = em.getCriteriaBuilder();
        final CriteriaQuery<Long> countCriteria = builder.createQuery(Long.class);
        final Root<?> entityRoot = countCriteria.from(criteria.getResultType());

        rebuildQuery(criteria, entityRoot);
        countCriteria.select(builder.count(entityRoot));

        if (criteria.getRestriction() != null) {
            countCriteria.where(criteria.getRestriction());
        }

        return em.createQuery(countCriteria).getSingleResult();
    }

    /**
     * This method simply execute a criteria query using the offset and results attributes. This will limit the number
     * of results required.
     *
     * @param <T>     The type of JPA object to be queried.
     * @param dc      The criteria to be executed
     * @param offset  The offset to be used as first result
     * @param results The number of results to be retrieved
     * @return The List of JPA objectes queried.
     */
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public <T> List<T> executeCriteria(final CriteriaQuery<T> dc, final Integer offset, final Integer results) {
        try {
            final Query query = em.createQuery(dc);
            query.setFirstResult(offset);
            query.setMaxResults(results);
            return query.getResultList();
        } catch (final Exception e) {
            logger.error("Error executing criteria", e);
            throw new PersistenceException(e);
        }
    }

    /**
     * This method execute a generic query based on a specified criteria passed as argument and the query attributes
     * for
     * paging and sorting. If the attributes are null then no paging and/or sorting different from the ones defined in
     * the query itself will be applied.
     *
     * @param <T>        The type of results to be returned.
     * @param container  The singled root criteria query to be executed.
     * @param attributes The paging and sorting attributes to be applied.
     * @return The paged and sorted results.
     * @throws PersistenceException If some exception occurs during the method execution.
     */
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public <T> Result<T> executeFind(final SingleRootCriteriaQuery<T> container, final QueryAttributes attributes)
            throws PersistenceException {
        Result<T> results = null;
        try {
            final long start = System.currentTimeMillis();
            helper.applySortAttributes(em.getCriteriaBuilder(), container, attributes);
            final Query query = em.createQuery(container.getCriteria());

            if (attributes != null) {
                Number size = Long.MAX_VALUE;
                if (attributes.getLimitMode() == LimitMode.LIMITED) {
                    size = findCountByCriteria(container.getCriteria());
                }

                query.setFirstResult(attributes.getOffset());
                if (attributes.getLimitMode() == LimitMode.LIMITED) {
                    query.setMaxResults(attributes.getMax());
                }

                final List<T> list = query.getResultList();

                results = new Result<T>(attributes);
                results.setCriteriaQuery(container.getCriteria());
                results.setEntries(list);
                results.setSize(size.intValue());
            } else {
                final List<T> list = query.getResultList();

                results = new Result<T>(null);
                results.setEntries(list);
                results.setSize(list.size());
            }

            final long end = System.currentTimeMillis();
            final long elapsed = end - start;
            results.setElapsed(elapsed);

            // Long queries must be logged.
            if (elapsed > INT_100) {
                logger.info("[Query Database][Root]" + container.getCriteria().getResultType().getSimpleName()
                        + "[Search][Items]" + results.getEntries().size() + "[Over]" + results.getSize() + " [Time]"
                        + (end - start) + " ms");
            }
        } catch (final Exception e) {
            logger.error("Error executing find", e);
            throw new PersistenceException(e);
        }
        return results;
    }

    /**
     * This method load an instance of a persistent object from the database using its unique ID. It takes the class to
     * be used for the query and the entity ID.
     *
     * @param <T>   The type expected as result defined by the class argument.
     * @param clazz The class to be used for the query.
     * @param id    The ID of the entity to be retrieved.
     * @return The persisted entity.
     * @throws DatabaseException
     */
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public <T> T load(final Class<T> clazz, final Object id) throws DatabaseException {
        return this.load(clazz, id, (SingularAttribute<T, ?>[]) null);
    }

    /**
     * This method load an instance of a persistent object from the database using its unique ID. It takes the class to
     * be used for the query and the entity ID. The method accept also a list of fetches to be retrieved along with the
     * entity found.
     *
     * @param <T>     The type expected as result defined by the class argument.
     * @param clazz   The class to be used for the query.
     * @param id      The ID of the entity to be retrieved.
     * @param fetches The fetches
     * @return The persisted entity.
     * @throws DatabaseException
     */
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public <T> T load(final Class<T> clazz, final Object id, final SingularAttribute<T, ?>... fetches)
            throws DatabaseException {
        loadingMessage(clazz);

        final CriteriaBuilder cb = em.getCriteriaBuilder();
        final SingleRootCriteriaQuery<T> container = helper.criteria(cb, clazz, fetches);
        container.getCriteria().where(cb.equal(container.getRoot().get("id"), id));

        try {
            return em.createQuery(container.getCriteria()).getSingleResult();
        } catch (final NoResultException e) {
            logger.error("No results:" + e.getMessage());
            return null;
        } catch (final PersistenceException e) {
            logger.error("Database exception", e);
            throw new DatabaseException(e);
        }
    }

    /**
     * This method load an instance of a persistent object from the database using a specified attribute. It takes the
     * class to be used for the query and the entity singular attribute. The value of the singular attribute specified
     * should match the value provided as third argument.
     *
     * @param <T>       The type expected as result defined by the class argument.
     * @param clazz     The class to be used for the query.
     * @param attribute The singular attribute to be used for the query
     * @param value     The value to be used for matching.
     * @param fetches   The fetches
     * @return The persisted entity.
     * @throws DatabaseException
     */
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public <T> T load(final Class<T> clazz, final SingularAttribute<T, ?> attribute, final Object value,
                      final SingularAttribute<T, ?>... fetches) throws DatabaseException {
        loadingMessage(clazz);

        final CriteriaBuilder cb = em.getCriteriaBuilder();
        final SingleRootCriteriaQuery<T> container = helper.criteria(cb, clazz, fetches);
        if (attribute != null && value != null) {
            container.getCriteria().where(cb.equal(container.getRoot().get(attribute), value));
        }

        try {
            return em.createQuery(container.getCriteria()).getSingleResult();
        } catch (final NoResultException e) {
            return null;
        } catch (final PersistenceException e) {
            throw new DatabaseException(e);
        }
    }

    /**
     * This method load an instance of a persistent object from the database using a specified attribute. It takes the
     * class to be used for the query and the entity singular attribute. The value of the singular attribute specified
     * should match the value provided as third argument.
     *
     * @param <T>       The type expected as result defined by the class argument.
     * @param clazz     The class to be used for the query.
     * @param attribute The singular attribute to be used for the query
     * @param value     The value to be used for matching.
     * @return The persisted entity.
     * @throws DatabaseException
     */
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public <T> T load(final Class<T> clazz, final SingularAttribute<T, ?> attribute, final Object value)
            throws DatabaseException {
        return this.load(clazz, attribute, value, (SingularAttribute<T, ?>[]) null);
    }

    /**
     * This method load an instance of a persistent object from the database using all specified attributes. It takes
     * the class to be used for the query and the entity singular attributes. The values of all singular attributes
     * specified should match the value provided as map's values.
     *
     * @param <T>              The type expected as result defined by the class argument.
     * @param clazz            The class to be used for the query.
     * @param attributesValues The map containing attribute-value couples to be used for the query
     * @return The persisted entity.
     * @throws DatabaseException
     */
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public <T> T load(final Class<T> clazz, final Map<SingularAttribute<T, ?>, Object> attributesValues)
            throws DatabaseException {
        return this.load(clazz, attributesValues, (SingularAttribute<T, ?>[]) null);
    }

    /**
     * This method load an instance of a persistent object from the database using all specified attributes. It takes
     * the class to be used for the query and the entity singular attributes. The values of all singular attributes
     * specified should match the value provided as map's values.
     *
     * @param <T>              The type expected as result defined by the class argument.
     * @param clazz            The class to be used for the query.
     * @param attributesValues The map containing attribute-value couples to be used for the query
     * @param fetches          The fetches
     * @return The persisted entity.
     * @throws DatabaseException
     */
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public <T> T load(final Class<T> clazz, final Map<SingularAttribute<T, ?>, Object> attributesValues,
                      final SingularAttribute<T, ?>... fetches) throws DatabaseException {
        loadingMessage(clazz);

        final CriteriaBuilder cb = em.getCriteriaBuilder();
        final SingleRootCriteriaQuery<T> container = helper.criteria(cb, clazz, fetches);

        final Predicate[] predicates = new Predicate[attributesValues.size()];
        short counter = 0;
        for (Entry<SingularAttribute<T, ?>, Object> attributeValue : attributesValues.entrySet()) {
            predicates[counter++] = cb.equal(container.getRoot().get(attributeValue.getKey()),
                    attributeValue.getValue());
        }
        container.getCriteria().where(predicates);

        try {
            return em.createQuery(container.getCriteria()).getSingleResult();
        } catch (final NoResultException e) {
            return null;
        } catch (final PersistenceException e) {
            throw new DatabaseException(e);
        }
    }

    /**
     * This method simply load all the entity for a given class. It can be used only when few entities are in the
     * database.
     *
     * @param <T>   The return entity type.
     * @param clazz The class of the entities to be load.
     * @return The list of entities.
     */
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public <T> List<T> loadList(final Class<T> clazz) {
        loadingMessage(clazz);

        final CriteriaBuilder cb = em.getCriteriaBuilder();
        final SingleRootCriteriaQuery<T> container = helper.criteria(cb, clazz);

        try {
            return em.createQuery(container.getCriteria()).getResultList();
        } catch (final NoResultException e) {
            return new ArrayList<T>();
        } catch (final PersistenceException e) {
            return new ArrayList<T>();
        }
    }
    
    

    /**
     * This method simply load all the entity for a given class. It can be used only when few entities are in the
     * database.
     *
     * @param <T>       The return entity type.
     * @param clazz     The class of the entities to be load.
     * @param attribute The singular attribute to be used for the query
     * @param value     The value to be used for matching.
     * @return The list of entities.
     */
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public <T> List<T> loadList(final Class<T> clazz, final SingularAttribute<T, ?> attribute, final Object value) {
        return loadList(clazz, attribute, value, (SingularAttribute<T, ?>) null);
    }

    /**
     * This method simply load all the entity for a given class. It can be used only when few entities are in the
     * database.
     *
     * @param <T>       The return entity type.
     * @param clazz     The class of the entities to be load.
     * @param attribute The singular attribute to be used for the query
     * @param value     The value to be used for matching.
     * @param fetches   The fetches
     * @return The list of entities.
     */
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public <T> List<T> loadList(final Class<T> clazz, final SingularAttribute<T, ?> attribute, final Object value,
                                final SingularAttribute<T, ?>... fetches) {
        loadingMessage(clazz);

        final CriteriaBuilder cb = em.getCriteriaBuilder();

        final SingleRootCriteriaQuery<T> container = helper.criteria(cb, clazz, fetches);
        if (attribute != null && value != null) {
            container.getCriteria().where(cb.equal(container.getRoot().get(attribute), value));
        }

        try {
            return em.createQuery(container.getCriteria()).getResultList();
        } catch (final NoResultException e) {
            return new ArrayList<T>();
        } catch (final PersistenceException e) {
            return new ArrayList<T>();
        }
    }

    /**
     * This method simply load distinct values for a given class and attribute.
     *
     * @param <T>       The entity type to be load.
     * @param clazz     The class of the entities to be load.
     * @param attribute The attribute on which distinct is requested.
     * @return The list of attribute values.
     */
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public <T> List<Object> loadDistinctList(final Class<T> clazz, final String attribute) {
        loadingMessage(clazz);

        final CriteriaBuilder cb = em.getCriteriaBuilder();
        final CriteriaQuery<Object> criteriaQuery = cb.createQuery(Object.class);
        final Root<T> root = criteriaQuery.from(clazz);
        criteriaQuery.select(root.get(attribute)).distinct(true);

        try {
            return em.createQuery(criteriaQuery).getResultList();
        } catch (final NoResultException e) {
            return new ArrayList<>();
        } catch (final PersistenceException e) {
            return new ArrayList<>();
        }
    }

    /**
     * This method simply persists the provided abstract entity using the entity manager.
     *
     * @param object The object to be persisted.
     * @throws DatabaseException If an exception occurs.
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void persist(final AbstractEntity object) throws DatabaseException {
        try {
            em.persist(object);
        } catch (final Exception e) {
            logger.error("Database exception", e);
            throw new DatabaseException(e);
        }
    }

    /**
     * This method updates the provided abstract entity using the entity manager.
     *
     * @param object
     * @throws DatabaseException
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void merge(final AbstractEntity object) throws DatabaseException {
        try {
            em.merge(object);
        } catch (final Exception e) {
            logger.error("Database exception", e);
            throw new DatabaseException(e);
        }
    }

    /**
     * This method removes the provided abstract entity using the entity manager.
     * It properly works only on entities which are managed in the current transaction/context.
     *
     * @param object
     * @throws DatabaseException
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void remove(final AbstractEntity object) throws DatabaseException {
        try {
            final AbstractEntity deletingObject = em.merge(object);
            em.remove(deletingObject);
        } catch (final Exception e) {
            logger.error("Database exception", e);
            throw new DatabaseException(e);
        }
    }

    /*    *//**
     * This method save/persist an object and logger the information of the user that is requiring the action. If the
     * object has a not null id member, then it merge the entity with its persistent representation.
     *
     * @param user   The user that requires the save/update
     * @param object The object to be persisted
     * @return The object saved/updated
     * @throws DatabaseException if an exception arise during save.
     *//*
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public AbstractEntity save(final IUser user, final AbstractEntity object) throws DatabaseException {
        if (user != null) {
            logger.info("[User requiring save]" + user.getUsername());
        }

        final AbstractEntity retval;
        try {
            if (object.getId() != null) {
                if (user != null) {
                    logger.info("[Merging object]" + object.getClass() + "[ID]" + object.getId());
                }
                retval = em.merge(object);
            } else {
                if (user != null) {
                    logger.info("[Persisting object]" + object.getClass() + "[ID]" + object.getId());
                }
                em.persist(object);
                retval = object;
            }

            return retval;
        } catch (final Exception e) {
            logger.error("Database exception", e);
            throw new DatabaseException(e);
        }
    }

    *//**
     * This method simply deletes a persistent object. It firstly merge the object in order to have its persistent
     * reference and then deletes it. It logs also the user that requires the deletion.
     *
     * @param user   The user requiring the deletion
     * @param object The object to be deleted.
     * @throws DatabaseException if an exception occurs during deletion.
     *//*
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void delete(final IUser user, final AbstractEntity object) throws DatabaseException {
        try {
            if (user == null) {
                logger.info("[System requiring delete]");
            } else {
                logger.info("[User requiring delete]" + user);
            }
            logger.info("[Deleting object]" + object.getClass() + "[ID]" + object.getId());
            if (object.getId() != null) {
                final AbstractEntity tobedeleted = em.merge(object);
                em.remove(tobedeleted);
            }
        } catch (final Exception e) {
            logger.error("Database exception", e);
            throw new DatabaseException(e);
        }
    }*/

    /**
     * This method simply count the instances of specified class in the database.
     *
     * @param <T>   The generic type.
     * @param clazz The class to be counted.
     * @return The number of class instances.
     */
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public <T> Long count(final Class<T> clazz) {
        final CriteriaBuilder builder = em.getCriteriaBuilder();

        final CriteriaQuery<Long> countCriteria = builder.createQuery(Long.class);
        final Root<T> entityRoot = countCriteria.from(clazz);
        countCriteria.select(builder.count(entityRoot));

        return em.createQuery(countCriteria).getSingleResult();
    }

    /**
     * This method load a generic AbstractEntity from the class specified and unique id.
     *
     * @param clazz The class of the abstract entity to be load
     * @param id    The id of the entity to be load
     * @return The matching abstract entity
     */
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public AbstractEntity loadAbstractEntity(final Class<? extends AbstractEntity> clazz, final Object id) {
        loadingMessage(clazz);

        final CriteriaBuilder cb = em.getCriteriaBuilder();
        final CriteriaQuery<? extends AbstractEntity> criteria = cb.createQuery(clazz);
        final Root<? extends AbstractEntity> root = criteria.from(clazz);
        criteria.where(cb.equal(root.get("id"), id));

        try {
            return em.createQuery(criteria).getSingleResult();
        } catch (final NoResultException e) {
            return null;
        } catch (final PersistenceException e) {
            return null;
        }
    }

}
