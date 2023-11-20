/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.stilo.logic.search.QueryAttributes;
import it.eng.stilo.logic.search.SortMode;
import it.eng.stilo.model.util.Mappable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.context.Dependent;
import javax.persistence.criteria.*;
import javax.persistence.metamodel.SingularAttribute;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * This class contains utility JPA methods that simplify recurrent operations of
 * queries and objects. It is an generic JPA helper.
 */
@SuppressWarnings("serial")
@Dependent
public class CriteriaHelper {

    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * Evaluate applicability of a string attribute.
     *
     * @param <T>       The type of the example
     * @param example   The example instance
     * @param attribute The attribute to be evaluated
     * @return true if applicable in search
     */
    private <T> boolean evaluateStringApplicability(final T example, final SingularAttribute<T, ?> attribute) {
        final String value = (String) getValue(example, attribute);
        if (value != null && value.length() != 0 && !("-1".equalsIgnoreCase(value))) {
            logger.info("[Applicable Attribute]" + attribute.getName() + "[Value]" + value);
            return true;
        } else {
            return false;
        }
    }

    /**
     * Evaluate applicability of a Long attribute.
     *
     * @param <T>       The type of the example
     * @param example   The example instance
     * @param attribute The attribute to be evaluated
     * @return true if applicable in search
     */
    private <T> boolean evaluateLongApplicability(final T example, final SingularAttribute<T, ?> attribute) {
        final Long value = (Long) getValue(example, attribute);
        if (value != null && value.longValue() != -1) {
            logger.info("[Applicable Attribute]" + attribute.getName() + "[Value]" + value);
            return true;
        } else {
            return false;
        }
    }

    /**
     * This method verify if a specific attribute of an object (example) can be
     * applied to a query. It simply checks if attribute of the example passed
     * as argument is not null and has a meaningful value. The current
     * implementation only provide support for String and Long. It can be easily
     * extended.
     *
     * @param <T>       The type of the example
     * @param example   The object to be verified.
     * @param attribute The attribute to be checked.
     * @return true if the attribute is not null and has a meaningful value (if
     * string type then it must be with a length different from 0 / if
     * long it must be different from -1)
     */
    public final <T> boolean isApplicable(final T example, final SingularAttribute<T, ?> attribute) {
        if (example == null) {
            return false;
        }
        try {
            if (attribute.getJavaType().isAssignableFrom(String.class)) {
                return evaluateStringApplicability(example, attribute);
            } else if (attribute.getJavaType().isAssignableFrom(Long.class)) {
                return evaluateLongApplicability(example, attribute);
            }
        } catch (final Exception e) {
            logger.error("Cannot evaluate applicability", e);
        }
        return false;
    }

    /**
     * This method simply evaluates the nullability of an example.
     *
     * @param example The example to be evaluated.
     * @param <T>     The type of the example.
     * @return false if the example is null.
     */
    public final <T> boolean isApplicable(final T example) {
		return example != null;
	}

    /**
     * This method takes a container of a CriteriaQuery already created and
     * apply all the sort attributes specified by the attribute parameter.
     *
     * @param <T>        The type of ROOT specified in the criteria query.
     * @param cb         The reference to the criteria builder already created in the
     *                   calling method.
     * @param container  The container of the criteria query.
     * @param attributes The object containing the specification of the query
     *                   attributes to be applied (sort/pager)
     */
    @SuppressWarnings("unchecked")
    public final <T> void applySortAttributes(final CriteriaBuilder cb, final SingleRootCriteriaQuery<T> container,
                                              final QueryAttributes attributes) {
        final Class<? extends Object> metamodel = loadMetamodelClass(container.getRoot().getJavaType());
        try {
            Field field = null;
            if (attributes.getOrderField() != null) {
                field = metamodel.getField(attributes.getOrderField());
            } else if (attributes.getDefaultOrderField() != null) {

                final SingularAttribute<T, ? extends Object> attr = (SingularAttribute<T, ? extends Object>) attributes
                        .getDefaultOrderField();

                if (attributes.getDefaultSortMode() == SortMode.ASCENDING) {
                    container.getCriteria().orderBy(cb.asc(container.getRoot().get(attr)));
                } else {
                    container.getCriteria().orderBy(cb.desc(container.getRoot().get(attr)));
                }
            }

            final SingularAttribute<? super T, ?> attr = (SingularAttribute<? super T, ?>) field.get(null);
            if (attributes.getDefaultSortMode() == SortMode.ASCENDING) {
                container.getCriteria().orderBy(cb.asc(container.getRoot().get(attr)));
            } else {
                container.getCriteria().orderBy(cb.desc(container.getRoot().get(attr)));
            }
        } catch (final Exception e) {
            return;
        }
    }

    /**
     * This method simply creates a SingleRootCriteriaQuery starting from the
     * class that will be the root of the criteria with no fetches.
     *
     * @param <T>   The class type of the root.
     * @param cb    The reference to the criteria builder already created in the
     *              calling method.
     * @param clazz The class of type T that defines the root class.
     * @return The created SingleRootCriteriaQuery instance of type T
     */
    public final <T> SingleRootCriteriaQuery<T> criteria(final CriteriaBuilder cb, final Class<T> clazz) {
        return criteria(cb, clazz, (SingularAttribute<T, ?>[]) null);
    }

    /**
     * This method simply creates a SingleRootCriteriaQuery starting from the
     * class that will be the root of the criteria and the list of fetches
     * defined by a list of attributes of the class.
     *
     * @param <T>     The class type of the root.
     * @param fetches The list of joins of the root to be fetched.
     * @param cb      The reference to the criteria builder already created in the
     *                calling method.
     * @param clazz   The class of type T that defines the root class.
     * @return The created SingleRootCriteriaQuery instance of type T
     */
    public final <T> SingleRootCriteriaQuery<T> criteria(final CriteriaBuilder cb, final Class<T> clazz,
                                                         final SingularAttribute<T, ?>... fetches) {
        final CriteriaQuery<T> criteria = cb.createQuery(clazz);
        final Root<T> root = criteria.from(clazz);
        if (fetches != null && fetches.length != 0) {
            for (final SingularAttribute<T, ?> attr : fetches) {
                if (attr != null) {
                    root.fetch(attr, JoinType.LEFT);
                }
            }
        }
        criteria.select(root);
        final SingleRootCriteriaQuery<T> container = new SingleRootCriteriaQuery<T>();
        container.setCriteria(criteria);
        container.setRoot(root);
        container.getCriteria().distinct(true);
        return container;
    }

    /**
     * In some implementation of the JPA specification the ilike predicate is
     * not available. Due to the fact that in Hibernate the ilike predicate is
     * really useful when creating search engine, this method simply build such
     * a predicate starting from the attribute and the filter to be applied.
     *
     * @param <T>       The type T of the root on which the predicate will be created.
     * @param root      The root on which the predicate will be applied.
     * @param cb        The criteria builder in order to access the basic predicates.
     * @param attribute The attribute on which the filter will be applied.
     * @param filter    The value of the filter to be applied to the attribute.
     * @return The created predicate
     */
    @SuppressWarnings({"rawtypes", "unchecked"})
    public final <T> Predicate ilike(final From root, final CriteriaBuilder cb,
                                     final SingularAttribute<T, String> attribute, final String filter) {
        Predicate predicate = null;
        final Expression<String> inner = cb.upper(root.get(attribute));
        predicate = cb.like(inner, "%" + filter.toUpperCase() + "%");
        return predicate;
    }

    /**
     * This method simply build such a predicate starting from the attribute and
     * the filter to be applied.
     *
     * @param <T>       The type T of the root on which the predicate will be created.
     * @param root      The root on which the predicate will be applied.
     * @param cb        The criteria builder in order to access the basic predicates.
     * @param attribute The attribute on which the filter will be applied.
     * @param filter    The value of the filter to be applied to the attribute.
     * @return The created predicate
     */
    @SuppressWarnings({"rawtypes", "unchecked"})
    public final <T> Predicate equal(final From root, final CriteriaBuilder cb,
                                     final SingularAttribute<T, String> attribute, final String filter) {
        final Expression<String> inner = cb.upper(root.get(attribute));
        return cb.equal(inner, filter.toUpperCase());
    }

    /**
     * This method simply build such a predicate starting from the attribute and
     * the filter to be applied.
     *
     * @param <T>       The type T of the root on which the predicate will be created.
     * @param root      The root on which the predicate will be applied.
     * @param cb        The criteria builder in order to access the basic predicates.
     * @param attribute The attribute on which the filter will be applied.
     * @param filter    The value of the filter to be applied to the attribute.
     * @return The created predicate
     */
    public final <T> Predicate equal(final From root, final CriteriaBuilder cb,
                                     final SingularAttribute<T, Mappable> attribute, final Mappable filter) {
        final Expression<String> inner = cb.upper(root.get(attribute));
        //final Expression<Mappable> inner = cb.upper(root.get(attribute));
        return cb.equal(inner, filter);
    }

    /**
     * In some implementation of the JPA specification the Query-By-Example
     * utility is not available. This method created a list of predicate on the
     * basis of the provided example. It checks if the String attributes can be
     * applied into the query. If the attribute in the example is not null and
     * with a valid value (length>0) then a predicate will be created using like
     * clause and added to the list.
     *
     * @param <T>     The type of the root.
     * @param root    The root on which the predicate will be applied.
     * @param cb      The criteria builder in order to access the basic predicates.
     * @param example the provided example in order to specify the values of the
     *                predicates.
     * @return The list of created predicates.
     */
    @SuppressWarnings({"unchecked", "rawtypes"})
    public final <T> List<Predicate> qbel(final From root, final CriteriaBuilder cb, final T example) {
        final List<Predicate> predicates = new ArrayList<Predicate>();
        final Class<? extends Object> metamodel = loadMetamodelClass(example.getClass());
        final Field[] fields = metamodel.getFields();
        for (final Field field : fields) {
            try {
                final SingularAttribute<?, ?> attribute = (SingularAttribute<?, ?>) field.get(null);
                if (attribute.getJavaType().isAssignableFrom(String.class)) {
                    final String value = (String) getValue(example, attribute);
                    if (value != null && value.length() != 0) {
                        logger.info("[Attribute]" + attribute.getName() + "[Value]" + value);
                        predicates.add(ilike(root, cb, (SingularAttribute<T, String>) attribute, value));
                    }
                }
                evalEnum(root, cb, example, attribute, predicates);
            } catch (final Exception e) {
                continue;
            }
        }
        return predicates;
    }

    /**
     * In some implementation of the JPA specification the Query-By-Example
     * utility is not available. This method created a list of predicate on the
     * basis of the provided example. It checks if the String attributes can be
     * applied into the query. If the attribute in the example is not null and
     * with a valid value (length>0) then a predicate will be created using equal clause
     * and added to the list.
     *
     * @param <T>     The type of the root.
     * @param root    The root on which the predicate will be applied.
     * @param cb      The criteria builder in order to access the basic predicates.
     * @param example the provided example in order to specify the values of the
     *                predicates.
     * @return The list of created predicates.
     */
    @SuppressWarnings({"unchecked", "rawtypes"})
    public final <T> List<Predicate> qbe(final From root, final CriteriaBuilder cb, final T example) {
        final List<Predicate> predicates = new ArrayList<Predicate>();
        final Class<? extends Object> metamodel = loadMetamodelClass(example.getClass());
        final Field[] fields = metamodel.getFields();
        for (final Field field : fields) {
            try {
                final SingularAttribute<?, ?> attribute = (SingularAttribute<?, ?>) field.get(null);
                if (attribute.getJavaType().isAssignableFrom(String.class)) {
                    final String value = (String) getValue(example, attribute);
                    if (value != null && value.length() != 0) {
                        logger.info("[Attribute]" + attribute.getName() + "[Value]" + value);
                        predicates.add(equal(root, cb, (SingularAttribute<T, String>) attribute, value));
                    }
                }
                evalEnum(root, cb, example, attribute, predicates);
            } catch (final Exception e) {
                continue;
            }
        }
        return predicates;
    }

    /**
     * This method checks if the EMappable attributes can be applied into the query. If the attribute in the example
     * is not null, then a predicate will be created using equal clause and added to the list.
     *
     * @param root       The root on which the predicate will be applied.
     * @param cb         The criteria builder in order to access the basic predicates.
     * @param example    The provided example in order to specify the values of the predicates.
     * @param attribute  The evaluated attribute.
     * @param predicates The list of created predicates.
     * @param <T>        The type of the root.
     */
    private <T> void evalEnum(final From root, final CriteriaBuilder cb, final T example,
                              final SingularAttribute<?, ?> attribute, final List<Predicate> predicates) {
        if (Mappable.class.isAssignableFrom(attribute.getJavaType())) {
            final Mappable value = (Mappable) getValue(example, attribute);
            if (value != null) {
                logger.info("[Attribute]" + attribute.getName() + "[Value]" + value);
                predicates.add(equal(root, cb, (SingularAttribute<T, Mappable>) attribute, value));
            }
        }
    }

    /**
     * This method simply extract the value of a specified attributes form an
     * existing object.
     *
     * @param instance  The object from which the value of the attribute must be
     *                  extracted.
     * @param attribute The attribute to be extracted.
     * @return The value of the attribute into the provided object.
     */
    private Object getValue(final Object instance, final SingularAttribute<?, ?> attribute) {
        final String methodName = "get" + attribute.getName().substring(0, 1).toUpperCase()
                + attribute.getName().substring(1);
        Method method;
        try {
            method = instance.getClass().getMethod(methodName, (Class<?>[]) null);
            return method.invoke(instance, (Object[]) null);
        } catch (final Exception e) {
            logger.error("Cannot get value from object", e);
            return null;
        }
    }

    /**
     * This method load the metamodel of an example class of type T. The *
     * metamodel is the generated class with the "_". The fields list is made of
     * Attribute objects.
     *
     * @param <T>          The type of the class to be checked.
     * @param exampleClass The class that will be used for searching the corresponding
     *                     metamodel class.
     * @return The metamodel class found.
     */
    private <T> Class<? extends Object> loadMetamodelClass(final Class<T> exampleClass) {
        final String className = exampleClass.getName() + "_";
        try {
            return Class.forName(className);
        } catch (final ClassNotFoundException e) {
            logger.error("Cannot load metamodel for class " + exampleClass.getSimpleName(), e);
        }
        return null;
    }

}
