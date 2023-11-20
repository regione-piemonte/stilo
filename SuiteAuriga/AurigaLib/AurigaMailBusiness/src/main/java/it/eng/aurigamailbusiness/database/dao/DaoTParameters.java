/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import it.eng.aurigamailbusiness.bean.TParametersBean;
import it.eng.aurigamailbusiness.converters.TParametersBeanToTParameters;
import it.eng.aurigamailbusiness.converters.TParametersToTParametersBean;
import it.eng.aurigamailbusiness.database.mail.TParameters;
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

@Service(name = "DaoTParameters")
public class DaoTParameters extends DaoGenericOperations<TParametersBean> {

	public DaoTParameters() {
	}

	private static String errorSearch = "Nessun filtro impostato. Interrotta la ricerca altrimenti sarebbero restituiti tutti i parametri";

	@Override
	@Operation(name = "save")
	public TParametersBean save(TParametersBean bean) throws Exception {
		Session session = null;
		try {
			session = HibernateUtil.begin();
			Transaction transaction = session.beginTransaction();
			TParameters toInsert = (TParameters) UtilPopulate.populate(bean, TParameters.class, new TParametersBeanToTParameters());
			session.save(toInsert);
			session.flush();
			bean = (TParametersBean) UtilPopulate.populate(toInsert, TParametersBean.class, new TParametersToTParametersBean());
			transaction.commit();
			return bean;
		} catch (Exception e) {
			throw e;
		} finally {
			HibernateUtil.release(session);
		}
	}

	public TParametersBean getInSession(String idParameter, Session session) throws Exception {
		TParameters mgo = (TParameters) session.load(TParameters.class, idParameter);
		return (TParametersBean) UtilPopulate.populate(mgo, TParametersBean.class, new TParametersToTParametersBean());

	}

	@Operation(name = "get")
	public TParametersBean get(String idParameter) throws Exception {
		Session session = null;
		TParametersBean result = null;
		try {
			session = HibernateUtil.begin();
			result = getInSession(idParameter, session);
		} catch (Exception e) {
			throw e;
		} finally {
			HibernateUtil.release(session);
		}
		return result;
	}

	@Operation(name = "getAllParameters")
	public TPagingList<TParametersBean> getAllParameters(TFilterFetch<TParametersBean> filter) throws Exception {
		Session session = null;
		try {
			session = HibernateUtil.begin();
			// Nessun criterio di ricerca, restituisco tutte le mailbox
			Criteria criteria = session.createCriteria(TParameters.class);
			if (ListUtil.isNotEmpty(filter.getOrders())) {
				for (TOrderBy tOrderBy : filter.getOrders()) {
					if (tOrderBy.getType().equals(OrderByType.ASCENDING)) {
						criteria.addOrder(Order.asc(tOrderBy.getPropname()));
					} else {
						criteria.addOrder(Order.desc(tOrderBy.getPropname()));
					}
				}
			} else {
				// default ordinamento per id
				criteria.addOrder(Order.asc("parKey"));
			}
			// Conto i record totali
			Long count = (Long) criteria.setProjection(Projections.rowCount()).uniqueResult();
			Integer startRow = filter.getStartRow() != null ? filter.getStartRow() : 0;
			Integer endRow = filter != null && filter.getEndRow() != null ? filter.getEndRow() : startRow + count.intValue() - 1;
			// Creo l'oggetto paginatore
			TPagingList<TParametersBean> paginglist = new TPagingList<TParametersBean>();
			paginglist.setTotalRows(count.intValue());
			paginglist.setStartRow(startRow);
			paginglist.setEndRow(endRow);
			// Recupero i record
			criteria.setProjection(null);
			criteria.setResultTransformer(Criteria.ROOT_ENTITY);
			for (Object obj : HibernateUtil.pagingByCriteria(criteria, startRow, endRow)) {
				TParametersBean bean = (TParametersBean) UtilPopulate.populate((TParameters) obj, TParametersBean.class, new TParametersToTParametersBean());
				paginglist.addData(bean);
			}
			return paginglist;
		} catch (Exception e) {
			throw e;
		} finally {
			HibernateUtil.release(session);
		}
	}

	public TPagingList<TParametersBean> getAllParametersInSession(TFilterFetch<TParametersBean> filter, Session session) throws Exception {
		session = HibernateUtil.begin();
		// Nessun criterio di ricerca, restituisco tutte le mailbox
		Criteria criteria = session.createCriteria(TParameters.class);
		if (ListUtil.isNotEmpty(filter.getOrders())) {
			for (TOrderBy tOrderBy : filter.getOrders()) {
				if (tOrderBy.getType().equals(OrderByType.ASCENDING)) {
					criteria.addOrder(Order.asc(tOrderBy.getPropname()));
				} else {
					criteria.addOrder(Order.desc(tOrderBy.getPropname()));
				}
			}
		} else {
			// default ordinamento per id
			criteria.addOrder(Order.asc("parKey"));
		}
		// Conto i record totali
		Long count = (Long) criteria.setProjection(Projections.rowCount()).uniqueResult();
		Integer startRow = filter.getStartRow() != null ? filter.getStartRow() : 0;
		Integer endRow = filter != null && filter.getEndRow() != null ? filter.getEndRow() : startRow + count.intValue() - 1;
		// Creo l'oggetto paginatore
		TPagingList<TParametersBean> paginglist = new TPagingList<TParametersBean>();
		paginglist.setTotalRows(count.intValue());
		paginglist.setStartRow(startRow);
		paginglist.setEndRow(endRow);
		// Recupero i record
		criteria.setProjection(null);
		criteria.setResultTransformer(Criteria.ROOT_ENTITY);
		for (Object obj : HibernateUtil.pagingByCriteria(criteria, startRow, endRow)) {
			TParametersBean bean = (TParametersBean) UtilPopulate.populate((TParameters) obj, TParametersBean.class, new TParametersToTParametersBean());
			paginglist.addData(bean);
		}
		return paginglist;
	}

	@Override
	@Operation(name = "search")
	public TPagingList<TParametersBean> search(TFilterFetch<TParametersBean> filter) throws Exception {
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
			TPagingList<TParametersBean> paginglist = new TPagingList<TParametersBean>();
			paginglist.setTotalRows(count.intValue());
			paginglist.setStartRow(startRow);
			paginglist.setEndRow(endRow);
			// Recupero i record
			criteria.setProjection(null);
			criteria.setResultTransformer(Criteria.ROOT_ENTITY);
			for (Object obj : HibernateUtil.pagingByCriteria(criteria, startRow, endRow)) {
				TParametersBean bean = (TParametersBean) UtilPopulate.populate((TParameters) obj, TParametersBean.class, new TParametersToTParametersBean());
				paginglist.addData(bean);
			}
			return paginglist;
		} catch (Exception e) {
			throw e;
		} finally {
			HibernateUtil.release(session);
		}
	}

	public TPagingList<TParametersBean> searchInSession(TFilterFetch<TParametersBean> filter, Session session) throws Exception {
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
		TPagingList<TParametersBean> paginglist = new TPagingList<TParametersBean>();
		paginglist.setTotalRows(count.intValue());
		paginglist.setStartRow(startRow);
		paginglist.setEndRow(endRow);
		// Recupero i record
		criteria.setProjection(null);
		criteria.setResultTransformer(Criteria.ROOT_ENTITY);
		for (Object obj : HibernateUtil.pagingByCriteria(criteria, startRow, endRow)) {
			TParametersBean bean = (TParametersBean) UtilPopulate.populate((TParameters) obj, TParametersBean.class, new TParametersToTParametersBean());
			paginglist.addData(bean);
		}
		return paginglist;
	}

	@Override
	@Operation(name = "update")
	public TParametersBean update(TParametersBean bean) throws Exception {
		Session session = null;
		try {
			session = HibernateUtil.begin();
			Transaction transaction = session.beginTransaction();
			TParameters toInsert = (TParameters) UtilPopulate.populate(bean, TParameters.class, new TParametersBeanToTParameters());
			session.update(toInsert);
			session.flush();
			bean = (TParametersBean) UtilPopulate.populate(toInsert, TParametersBean.class, new TParametersToTParametersBean());
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
	public void delete(TParametersBean bean) throws Exception {
		Session session = null;
		try {
			session = HibernateUtil.begin();
			Transaction transaction = session.beginTransaction();
			TParameters toInsert = (TParameters) UtilPopulate.populate(bean, TParameters.class, new TParametersBeanToTParameters());
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
	public void forcedelete(TParametersBean bean) throws Exception {
		Session session = null;
		try {
			session = HibernateUtil.begin();
			Transaction transaction = session.beginTransaction();
			TParameters toInsert = (TParameters) UtilPopulate.populate(bean, TParameters.class, new TParametersBeanToTParameters());
			session.delete(toInsert);
			session.flush();
			transaction.commit();
		} catch (Exception e) {
			throw e;
		} finally {
			HibernateUtil.release(session);
		}
	}

	private Criteria buildHibernateCriteriaByFilter(Session session, TFilterFetch<TParametersBean> filter) throws AurigaMailBusinessException {
		if (filter == null || filter.getFilter() == null || filter.getFilter().getParKey() == null) {
			throw new AurigaMailBusinessException(errorSearch);
		}
		Criteria lCriteria = session.createCriteria(TParameters.class);
		lCriteria.add(Restrictions.eq("parKey", filter.getFilter().getParKey()));
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