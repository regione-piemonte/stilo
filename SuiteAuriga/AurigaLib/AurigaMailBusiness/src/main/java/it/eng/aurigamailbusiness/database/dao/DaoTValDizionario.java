/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import it.eng.aurigamailbusiness.bean.TValDizionarioBean;
import it.eng.aurigamailbusiness.converters.TValDizionarioBeanToTValDizionario;
import it.eng.aurigamailbusiness.converters.TValDizionarioToTValDizionarioBean;
import it.eng.aurigamailbusiness.database.mail.TValDizionario;
import it.eng.aurigamailbusiness.exception.AurigaMailBusinessException;
import it.eng.core.annotation.Operation;
import it.eng.core.annotation.Service;
import it.eng.core.business.DaoGenericOperations;
import it.eng.core.business.HibernateUtil;
import it.eng.core.business.TFilterFetch;
import it.eng.core.business.TOrderBy;
import it.eng.core.business.TOrderBy.OrderByType;
import it.eng.core.business.TPagingList;
import it.eng.core.business.converter.UtilPopulate;
import it.eng.util.ListUtil;

@Service(name = "DaoTValDizionario")
public class DaoTValDizionario extends DaoGenericOperations<TValDizionarioBean> {

	public DaoTValDizionario() {
	}

	private static String errorSearch = "Nessun filtro impostato. Interrotta la ricerca altrimenti sarebbero restituite tutte le voci del dizionario di sistema";

	@Override
	@Operation(name = "save")
	public TValDizionarioBean save(TValDizionarioBean bean) throws Exception {
		Session session = null;
		try {
			session = HibernateUtil.begin();
			Transaction transaction = session.beginTransaction();
			TValDizionario toInsert = (TValDizionario) UtilPopulate.populate(bean, TValDizionario.class, new TValDizionarioBeanToTValDizionario());
			session.save(toInsert);
			session.flush();
			bean = (TValDizionarioBean) UtilPopulate.populate(toInsert, TValDizionarioBean.class, new TValDizionarioToTValDizionarioBean());
			transaction.commit();
			return bean;
		} catch (Exception e) {
			throw e;
		} finally {
			HibernateUtil.release(session);
		}
	}

	@Override
	@Operation(name = "search")
	public TPagingList<TValDizionarioBean> search(TFilterFetch<TValDizionarioBean> filter) throws Exception {
		Session session = null;
		try {
			if (filter == null) {
				throw new AurigaMailBusinessException(errorSearch);
			}
			session = HibernateUtil.begin();
			// Creo i criteri di ricerca
			Criteria criteria = this.buildHibernateCriteriaByFilter(session, filter);
			// Conto i record totali
			Long count = (Long) criteria.setProjection(Projections.rowCount()).uniqueResult();
			Integer startRow = filter.getStartRow() != null ? filter.getStartRow() : 0;
			Integer endRow = filter != null && filter.getEndRow() != null ? filter.getEndRow() : startRow + count.intValue() - 1;
			// Creo l'oggetto paginatore
			TPagingList<TValDizionarioBean> paginglist = new TPagingList<TValDizionarioBean>();
			paginglist.setTotalRows(count.intValue());
			paginglist.setStartRow(startRow);
			paginglist.setEndRow(endRow);
			// Recupero i record
			criteria.setProjection(null);
			criteria.setResultTransformer(Criteria.ROOT_ENTITY);
			for (Object obj : HibernateUtil.pagingByCriteria(criteria, startRow, endRow)) {
				TValDizionarioBean bean = (TValDizionarioBean) UtilPopulate.populate((TValDizionario) obj, TValDizionarioBean.class,
						new TValDizionarioToTValDizionarioBean());
				paginglist.addData(bean);
			}
			return paginglist;
		} catch (Exception e) {
			throw e;
		} finally {
			HibernateUtil.release(session);
		}
	}

	public TPagingList<TValDizionarioBean> searchInSession(TFilterFetch<TValDizionarioBean> filter, Session session) throws Exception {
		if (filter == null) {
			throw new AurigaMailBusinessException(errorSearch);
		}
		// Creo i criteri di ricerca
		Criteria criteria = this.buildHibernateCriteriaByFilter(session, filter);
		// Conto i record totali
		Long count = (Long) criteria.setProjection(Projections.rowCount()).uniqueResult();
		Integer startRow = filter.getStartRow() != null ? filter.getStartRow() : 0;
		Integer endRow = filter != null && filter.getEndRow() != null ? filter.getEndRow() : startRow + count.intValue() - 1;
		// Creo l'oggetto paginatore
		TPagingList<TValDizionarioBean> paginglist = new TPagingList<TValDizionarioBean>();
		paginglist.setTotalRows(count.intValue());
		paginglist.setStartRow(startRow);
		paginglist.setEndRow(endRow);
		// Recupero i record
		criteria.setProjection(null);
		criteria.setResultTransformer(Criteria.ROOT_ENTITY);
		for (Object obj : HibernateUtil.pagingByCriteria(criteria, startRow, endRow)) {
			TValDizionarioBean bean = (TValDizionarioBean) UtilPopulate.populate((TValDizionario) obj, TValDizionarioBean.class,
					new TValDizionarioToTValDizionarioBean());
			paginglist.addData(bean);
		}
		return paginglist;
	}

	@Override
	@Operation(name = "update")
	public TValDizionarioBean update(TValDizionarioBean bean) throws Exception {
		Session session = null;
		try {
			session = HibernateUtil.begin();
			Transaction transaction = session.beginTransaction();
			TValDizionario toInsert = (TValDizionario) UtilPopulate.populate(bean, TValDizionario.class, new TValDizionarioBeanToTValDizionario());
			session.update(toInsert);
			session.flush();
			bean = (TValDizionarioBean) UtilPopulate.populate(toInsert, TValDizionarioBean.class, new TValDizionarioToTValDizionarioBean());
			transaction.commit();
			return bean;
		} catch (Exception e) {
			throw e;
		} finally {
			HibernateUtil.release(session);
		}
	}

	@Override
	@Operation(name = "delete")
	public void delete(TValDizionarioBean bean) throws Exception {
		Session session = null;
		try {
			session = HibernateUtil.begin();
			Transaction transaction = session.beginTransaction();
			TValDizionario toInsert = (TValDizionario) UtilPopulate.populate(bean, TValDizionario.class, new TValDizionarioBeanToTValDizionario());
			session.delete(toInsert);
			session.flush();
			transaction.commit();
		} catch (Exception e) {
			throw e;
		} finally {
			HibernateUtil.release(session);
		}

	}

	@Override
	@Operation(name = "forcedelete")
	public void forcedelete(TValDizionarioBean bean) throws Exception {
		Session session = null;
		try {
			session = HibernateUtil.begin();
			Transaction transaction = session.beginTransaction();
			TValDizionario toInsert = (TValDizionario) UtilPopulate.populate(bean, TValDizionario.class, new TValDizionarioBeanToTValDizionario());
			session.delete(toInsert);
			session.flush();
			transaction.commit();
		} catch (Exception e) {
			throw e;
		} finally {
			HibernateUtil.release(session);
		}
	}

	private Criteria buildHibernateCriteriaByFilter(Session session, TFilterFetch<TValDizionarioBean> filter) throws AurigaMailBusinessException {
		if (filter == null || filter.getFilter() == null || filter.getFilter().getValore() == null) {
			throw new AurigaMailBusinessException(errorSearch);
		}
		Criteria lCriteria = session.createCriteria(TValDizionario.class);
		lCriteria.add(Restrictions.eq("valore", filter.getFilter().getValore()));
		if (ListUtil.isNotEmpty(filter.getOrders())) {
			for (TOrderBy tOrderBy : filter.getOrders()) {
				if (tOrderBy.getType().equals(OrderByType.ASCENDING)) {
					lCriteria.addOrder(Order.asc(tOrderBy.getPropname()));
				} else {
					lCriteria.addOrder(Order.desc(tOrderBy.getPropname()));
				}
			}
		}
		return lCriteria;
	}
}