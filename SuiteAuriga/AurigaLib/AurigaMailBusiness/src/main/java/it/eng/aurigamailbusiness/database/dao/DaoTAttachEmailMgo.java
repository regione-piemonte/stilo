/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import it.eng.aurigamailbusiness.bean.TAttachEmailMgoBean;
import it.eng.aurigamailbusiness.converters.TAttachEmailMgoBeanToTAttachEmailMgo;
import it.eng.aurigamailbusiness.converters.TAttachEmailMgoToTAttachEmailMgoBean;
import it.eng.aurigamailbusiness.database.mail.TAttachEmailMgo;
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

@Service(name = "DaoTAttachEmailMgo")
public class DaoTAttachEmailMgo extends DaoGenericOperations<TAttachEmailMgoBean> {

	public DaoTAttachEmailMgo() {
	}

	private static String errorSearch = "Nessun filtro impostato. Interrotta la ricerca altrimenti sarebbero restituiti tutti gli allegati delle mail";

	@Override
	@Operation(name = "save")
	public TAttachEmailMgoBean save(TAttachEmailMgoBean bean) throws Exception {
		Session session = null;
		try {
			session = HibernateUtil.begin();
			Transaction transaction = session.beginTransaction();
			bean = saveInSession(bean, session);
			transaction.commit();
			return bean;
		} catch (Exception e) {
			throw e;
		} finally {
			HibernateUtil.release(session);
		}
	}

	@Operation(name = "get")
	public TAttachEmailMgoBean get(String idAttach) throws Exception {
		Session session = null;
		TAttachEmailMgoBean result = null;
		try {
			session = HibernateUtil.begin();
			TAttachEmailMgo mgo = (TAttachEmailMgo) session.load(TAttachEmailMgo.class, idAttach);
			result = (TAttachEmailMgoBean) UtilPopulate.populate(mgo, TAttachEmailMgoBean.class, new TAttachEmailMgoToTAttachEmailMgoBean());
		} catch (Exception e) {
			throw e;
		} finally {
			HibernateUtil.release(session);
		}
		return result;
	}

	public TAttachEmailMgoBean saveInSession(TAttachEmailMgoBean bean, Session session) throws Exception {
		TAttachEmailMgo toInsert = (TAttachEmailMgo) UtilPopulate.populate(bean, TAttachEmailMgo.class, new TAttachEmailMgoBeanToTAttachEmailMgo());
		session.save(toInsert);
		session.flush();
		bean = (TAttachEmailMgoBean) UtilPopulate.populate(toInsert, TAttachEmailMgoBean.class, new TAttachEmailMgoToTAttachEmailMgoBean());
		return bean;
	}

	@Override
	@Operation(name = "search")
	public TPagingList<TAttachEmailMgoBean> search(TFilterFetch<TAttachEmailMgoBean> filter) throws Exception {
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
			TPagingList<TAttachEmailMgoBean> paginglist = new TPagingList<TAttachEmailMgoBean>();
			paginglist.setTotalRows(count.intValue());
			paginglist.setStartRow(startRow);
			paginglist.setEndRow(endRow);
			// Recupero i record
			criteria.setProjection(null);
			criteria.setResultTransformer(Criteria.ROOT_ENTITY);
			for (Object obj : HibernateUtil.pagingByCriteria(criteria, startRow, endRow)) {
				TAttachEmailMgoBean bean = (TAttachEmailMgoBean) UtilPopulate.populate((TAttachEmailMgo) obj, TAttachEmailMgoBean.class,
						new TAttachEmailMgoToTAttachEmailMgoBean());
				paginglist.addData(bean);
			}
			return paginglist;
		} catch (Exception e) {
			throw e;
		} finally {
			HibernateUtil.release(session);
		}
	}

	public TPagingList<TAttachEmailMgoBean> searchInSession(TFilterFetch<TAttachEmailMgoBean> filter, Session session) throws Exception {
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
		TPagingList<TAttachEmailMgoBean> paginglist = new TPagingList<TAttachEmailMgoBean>();
		paginglist.setTotalRows(count.intValue());
		paginglist.setStartRow(startRow);
		paginglist.setEndRow(endRow);
		// Recupero i record
		criteria.setProjection(null);
		criteria.setResultTransformer(Criteria.ROOT_ENTITY);
		for (Object obj : HibernateUtil.pagingByCriteria(criteria, startRow, endRow)) {
			TAttachEmailMgoBean bean = (TAttachEmailMgoBean) UtilPopulate.populate((TAttachEmailMgo) obj, TAttachEmailMgoBean.class,
					new TAttachEmailMgoToTAttachEmailMgoBean());
			paginglist.addData(bean);
		}
		return paginglist;
	}

	@Override
	@Operation(name = "update")
	public TAttachEmailMgoBean update(TAttachEmailMgoBean bean) throws Exception {
		Session session = null;
		try {
			session = HibernateUtil.begin();
			Transaction transaction = session.beginTransaction();
			bean = updateInSession(bean, session);
			transaction.commit();
			return bean;
		} catch (Exception e) {
			throw e;
		} finally {
			HibernateUtil.release(session);
		}
	}

	public TAttachEmailMgoBean updateInSession(TAttachEmailMgoBean bean, Session session) throws Exception {
		TAttachEmailMgo toUpdate = (TAttachEmailMgo) UtilPopulate.populate(bean, TAttachEmailMgo.class, new TAttachEmailMgoBeanToTAttachEmailMgo());
		session.update(toUpdate);
		session.flush();
		bean = (TAttachEmailMgoBean) UtilPopulate.populate(toUpdate, TAttachEmailMgoBean.class, new TAttachEmailMgoToTAttachEmailMgoBean());
		return bean;
	}

	@Override
	@Operation(name = "delete")
	public void delete(TAttachEmailMgoBean bean) throws Exception {
		Session session = null;
		try {
			session = HibernateUtil.begin();
			Transaction transaction = session.beginTransaction();
			deleteInSession(bean, session);
			transaction.commit();
		} catch (Exception e) {
			throw e;
		} finally {
			HibernateUtil.release(session);
		}

	}

	public void deleteInSession(TAttachEmailMgoBean bean, Session session) throws Exception {
		TAttachEmailMgo toDelete = (TAttachEmailMgo) UtilPopulate.populate(bean, TAttachEmailMgo.class, new TAttachEmailMgoBeanToTAttachEmailMgo());
		session.delete(toDelete);
		session.flush();
	}

	@Override
	@Operation(name = "forcedelete")
	public void forcedelete(TAttachEmailMgoBean bean) throws Exception {
		Session session = null;
		try {
			session = HibernateUtil.begin();
			Transaction transaction = session.beginTransaction();
			TAttachEmailMgo toDelete = (TAttachEmailMgo) UtilPopulate.populate(bean, TAttachEmailMgo.class, new TAttachEmailMgoBeanToTAttachEmailMgo());
			session.delete(toDelete);
			session.flush();
			transaction.commit();
		} catch (Exception e) {
			throw e;
		} finally {
			HibernateUtil.release(session);
		}
	}

	private Criteria buildHibernateCriteriaByFilter(Session session, TFilterFetch<TAttachEmailMgoBean> filter) throws AurigaMailBusinessException {
		if (filter == null || filter.getFilter() == null || filter.getFilter().getIdEmail() == null) {
			throw new AurigaMailBusinessException(errorSearch);
		}
		Criteria lCriteria = session.createCriteria(TAttachEmailMgo.class);
		lCriteria.add(Restrictions.eq("TEmailMgo.idEmail", filter.getFilter().getIdEmail()));
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