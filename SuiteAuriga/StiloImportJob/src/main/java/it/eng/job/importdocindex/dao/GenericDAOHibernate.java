/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

public class GenericDAOHibernate<T, ID extends Serializable> implements GenericDAO<T, ID> {

	private SessionFactory factory;

	public GenericDAOHibernate(Class<T> cl, SessionFactory sessionFactory) {
		if (sessionFactory == null)
			throw new IllegalArgumentException("Session factory is null!!!");
		this.factory = sessionFactory;
	}

	@Override
	public T get(final Class<T> cl, final ID id) {
		final Session session = factory.getCurrentSession();
		return HibernateTransactionManager.doInTransaction(session, new HibernateTransactionCallback<T>() {

			@SuppressWarnings("unchecked")
			@Override
			public T execute(Session session) {
				return (T) session.get(cl, id);
			}
		});
	}

	@Override
	public T load(final Class<T> cl, final ID id) {
		final Session session = factory.getCurrentSession();
		return HibernateTransactionManager.doInTransaction(session, new HibernateTransactionCallback<T>() {

			@SuppressWarnings("unchecked")
			@Override
			public T execute(Session session) {
				return (T) session.load(cl, id);
			}
		});
	}

	@Override
	public ID save(final T object) {
		final Session session = factory.getCurrentSession();
		return HibernateTransactionManager.doInTransaction(session, new HibernateTransactionCallback<ID>() {

			@SuppressWarnings("unchecked")
			@Override
			public ID execute(Session session) {
				return (ID) session.save(object);
			}
		});
	}

	@Override
	public void update(final T object) {
		final Session session = factory.getCurrentSession();
		HibernateTransactionManager.doInTransaction(session, new HibernateTransactionCallback<Void>() {

			@Override
			public Void execute(Session session) {
				session.update(object);
				return null;
			}
		});
	}

	@Override
	public T merge(final T object) {
		final Session session = factory.getCurrentSession();
		return HibernateTransactionManager.doInTransaction(session, new HibernateTransactionCallback<T>() {

			@SuppressWarnings("unchecked")
			@Override
			public T execute(Session session) {
				return (T) session.merge(object);
			}
		});
	}

	@Override
	public void delete(final T object) {
		final Session session = factory.getCurrentSession();
		HibernateTransactionManager.doInTransaction(session, new HibernateTransactionCallback<Void>() {

			@Override
			public Void execute(Session session) {
				session.delete(object);
				return null;
			}
		});
	}

	@Override
	public Boolean exists(final Class<T> cl, final ID id) {
		final Session session = factory.getCurrentSession();
		return HibernateTransactionManager.doInTransaction(session, new HibernateTransactionCallback<Boolean>() {

			@Override
			public Boolean execute(Session session) {
				final Object result = session.createCriteria(cl).add(Restrictions.idEq(id)).setProjection(Projections.id()).uniqueResult();
				return Boolean.valueOf(result != null);
			}
		});
	}

	@Override
	public List<T> query(final String hsql, final Map<String, Object> params) {
		final Session session = factory.getCurrentSession();
		return HibernateTransactionManager.doInTransaction(session, new HibernateTransactionCallback<List<T>>() {

			@SuppressWarnings("unchecked")
			@Override
			public List<T> execute(Session session) {
				List<T> result = null;
				final Query query = session.createQuery(hsql);
				if (params != null) {
					for (String key : params.keySet()) {
						query.setParameter(key, params.get(key));
					}
				}
				final String hsqlU = hsql.toUpperCase();
				if ((hsqlU.indexOf("DELETE") == -1) && (hsqlU.indexOf("UPDATE") == -1) && (hsqlU.indexOf("INSERT") == -1)) {
					result = query.list();
				} else {
				}
				return result;
			}
		});
	}

	@Override
	public Integer modify(final String hsql, final Map<String, Object> params) {
		final Session session = factory.getCurrentSession();
		return HibernateTransactionManager.doInTransaction(session, new HibernateTransactionCallback<Integer>() {

			@Override
			public Integer execute(Session session) {
				final Query query = session.createQuery(hsql);
				if (params != null) {
					for (String key : params.keySet()) {
						query.setParameter(key, params.get(key));
					}
				}
				return query.executeUpdate();
			}
		});
	}

}
