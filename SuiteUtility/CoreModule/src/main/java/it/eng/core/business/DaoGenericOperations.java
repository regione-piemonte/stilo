package it.eng.core.business;

import java.util.List;

import org.apache.commons.beanutils.BeanUtilsBean2;
import org.apache.commons.beanutils.PropertyUtilsBean;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Disjunction;
import org.hibernate.criterion.Junction;
import org.hibernate.criterion.Restrictions;

import it.eng.core.business.beans.AbstractBean;
import it.eng.core.business.beans.EntityConstraint;
import it.eng.core.business.beans.PropValue;
import it.eng.core.business.converter.UtilPopulate;

/**
 * Interfaccia generica delle CRUD di un Dao.
 * 
 * @author upescato
 *
 * @param <E>
 */

public abstract class DaoGenericOperations<E> {

	private static final Logger log = Logger.getLogger(DaoGenericOperations.class);

	/**
	 * Salvataggio di un'entita'
	 * 
	 * @param bean
	 * @return TODO
	 * @throws Exception
	 */
	public abstract E save(E bean) throws Exception;

	/**
	 * Ricerca di una lista di entita' a partire da parametri in input contenuti in un bean (searchBean)
	 * 
	 * @param filter
	 * @return
	 * @throws Exception
	 */
	public abstract TPagingList<E> search(TFilterFetch<E> filter) throws Exception;

	/**
	 * Update di un'entita'
	 * 
	 * @param bean
	 * @throws Exception
	 */
	public abstract E update(E bean) throws Exception;

	/**
	 * Cancellazione logica di un'entita'
	 * 
	 * @param bean
	 * @throws Exception
	 */
	public abstract void delete(E bean) throws Exception;

	/**
	 * Cancellazione fisica di un'entità
	 * 
	 * @param bean
	 * @throws Exception
	 */
	public abstract void forcedelete(E bean) throws Exception;

	protected void preInsert(AbstractBean bean, Class<?> classe, Exception costrainViolationException) throws Exception {
		Session session = null;
		try {
			session = HibernateUtil.begin();
			session.beginTransaction();
			preInsertWithSession(session, bean, classe, costrainViolationException);
			HibernateUtil.commit(session);
		} finally {
			HibernateUtil.release(session);
		}
	}

	protected void preInsertWithSession(Session session, AbstractBean bean, Class<?> classe, Exception costrainViolationException) throws Exception {
		PropertyUtilsBean util = BeanUtilsBean2.getInstance().getPropertyUtils();
		Object entity = UtilPopulate.populate(bean, classe);
		List<?> lista = checkConstraintViolation(session, entity, classe, util);
		if (lista != null && lista.size() > 0) {
			Object result = lista.get(0);
			if ((util.getProperty(result, "flgAnn") != null && util.getProperty(result, "flgAnn").toString().equalsIgnoreCase("true"))
					&& (util.getProperty(entity, "flgAnn") == null || !util.getProperty(entity, "flgAnn").toString().equalsIgnoreCase("true"))) {
				session.delete(HibernateConstants.Delete.FORCE, lista.get(0));
				session.flush();
			} else {
				throw costrainViolationException;
			}
		}
	}

	protected void preUpdate(AbstractBean bean, Class<?> classe, Exception costrainViolationException) throws Exception {
		Session session = null;
		try {
			session = HibernateUtil.begin();
			session.beginTransaction();
			preUpdateWithSession(session, bean, classe, costrainViolationException);
			HibernateUtil.commit(session);
		} finally {
			HibernateUtil.release(session);
		}
	}

	protected void preUpdateWithSession(Session session, AbstractBean bean, Class<?> classe, Exception costrainViolationException) throws Exception {
		PropertyUtilsBean util = BeanUtilsBean2.getInstance().getPropertyUtils();
		Object entity = UtilPopulate.populate(bean, classe);
		List<?> lista = checkConstraintViolation(session, entity, classe, util);
		if (lista != null && lista.size() > 0) {
			Object result = lista.get(0);
			if ((util.getProperty(result, "flgAnn") != null && util.getProperty(result, "flgAnn").toString().equalsIgnoreCase("true"))
					&& (util.getProperty(entity, "flgAnn") == null || !util.getProperty(entity, "flgAnn").toString().equalsIgnoreCase("true"))) {
				session.delete(HibernateConstants.Delete.FORCE, lista.get(0));
				session.flush();
			} else {
				String pkField = HibernateUtil.getPrimaryKey(classe);
				if (!util.getProperty(result, pkField).toString().equalsIgnoreCase(util.getProperty(entity, pkField).toString())) {
					throw costrainViolationException;
				}
			}
		}
	}

	private List<?> checkConstraintViolation(Session session, Object entity, Class<?> classe, PropertyUtilsBean util) throws Exception {
		try {
			Criteria crit = session.createCriteria(classe);
			// Prepara la query
			Disjunction dj = Restrictions.disjunction(); // Ogni constraint va in or
			List<EntityConstraint> constraints = UniqueConstraintsUtil.getEntityUniqueContraintFields(classe);
			for (EntityConstraint ec : constraints) {
				Junction jc = Restrictions.conjunction(); // Faccio una and degli attributi che costituiscono una singola constraint
				for (PropValue prop : ec.getPropertyNames()) {
					// Se la property si riferisce ad una chiave esterna allora entro in questo if:
					if (StringUtils.isNotBlank(prop.getPropertyPKName())) {
						String nomeCampo = prop.getPropertyName(); // nome della property
						String nomeProperty = prop.getPropertyPKName(); // nome della pk della property
						crit.createAlias(nomeCampo, nomeCampo); // creo l'alias
						String aliasCond = nomeCampo + "." + nomeProperty; // alias.nomeProperty
						Object relatedBean = util.getProperty(entity, nomeCampo); // recupero l'istanza della property
						jc.add(Restrictions.eq(aliasCond, util.getProperty(relatedBean, nomeProperty)).ignoreCase()); // creo la condizione della select
					} else {
						jc.add(Restrictions.eq(prop.getPropertyName(), util.getProperty(entity, prop.getPropertyName())).ignoreCase());
					}
				}
				dj.add(jc); // Ogni constraint � messa in or con le altre
			}
			crit.add(dj);
			return crit.list();
		} catch (Exception e) {
			log.warn("CheckConstraintViolation: ", e);
		}
		return null;
	}

}
