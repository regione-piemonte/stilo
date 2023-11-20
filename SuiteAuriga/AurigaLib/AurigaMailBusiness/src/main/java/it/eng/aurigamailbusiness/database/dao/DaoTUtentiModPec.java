/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import it.eng.aurigamailbusiness.bean.TUtentiModPecBean;
import it.eng.aurigamailbusiness.converters.TUtentiModPecBeanToTUtentiModPec;
import it.eng.aurigamailbusiness.converters.TUtentiModPecToTUtentiModPecBean;
import it.eng.aurigamailbusiness.database.mail.TUtentiModPec;
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

@Service(name = "DaoTUtentiModPec")
public class DaoTUtentiModPec extends DaoGenericOperations<TUtentiModPecBean> {

	public DaoTUtentiModPec() {
	}

	private static String errorSearch = "Nessun filtro impostato. Interrotta la ricerca altrimenti sarebbero restituite tutti gli utenti associati al modulo PEC";

	@Override
	@Operation(name = "save")
	public TUtentiModPecBean save(TUtentiModPecBean bean) throws Exception {
		Session session = null;
		try {
			session = HibernateUtil.begin();
			Transaction transaction = session.beginTransaction();
			TUtentiModPec toInsert = (TUtentiModPec) UtilPopulate.populate(bean, TUtentiModPec.class, new TUtentiModPecBeanToTUtentiModPec());
			session.save(toInsert);
			session.flush();
			bean = (TUtentiModPecBean) UtilPopulate.populate(toInsert, TUtentiModPecBean.class, new TUtentiModPecToTUtentiModPecBean());
			transaction.commit();
			return bean;
		} catch (Exception e) {
			throw e;
		} finally {
			HibernateUtil.release(session);
		}
	}

	@Operation(name = "get")
	public TUtentiModPecBean get(String idUtente) throws Exception {
		Session session = null;
		TUtentiModPecBean result = null;
		try {
			session = HibernateUtil.begin();
			TUtentiModPec mgo = (TUtentiModPec) session.load(TUtentiModPec.class, idUtente);
			result = (TUtentiModPecBean) UtilPopulate.populate(mgo, TUtentiModPecBean.class, new TUtentiModPecToTUtentiModPecBean());
		} catch (Exception e) {
			return null;
		} finally {
			HibernateUtil.release(session);
		}
		return result;
	}

	public TUtentiModPecBean getInSession(String idUtente, Session session) throws Exception {
		TUtentiModPecBean result = null;
		TUtentiModPec mgo = (TUtentiModPec) session.load(TUtentiModPec.class, idUtente);
		result = (TUtentiModPecBean) UtilPopulate.populate(mgo, TUtentiModPecBean.class, new TUtentiModPecToTUtentiModPecBean());
		return result;
	}

	@Override
	@Operation(name = "search")
	public TPagingList<TUtentiModPecBean> search(TFilterFetch<TUtentiModPecBean> filter) throws Exception {
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
			TPagingList<TUtentiModPecBean> paginglist = new TPagingList<TUtentiModPecBean>();
			paginglist.setTotalRows(count.intValue());
			paginglist.setStartRow(startRow);
			paginglist.setEndRow(endRow);
			// Recupero i record
			criteria.setProjection(null);
			criteria.setResultTransformer(Criteria.ROOT_ENTITY);
			for (Object obj : HibernateUtil.pagingByCriteria(criteria, startRow, endRow)) {
				TUtentiModPecBean bean = (TUtentiModPecBean) UtilPopulate.populate((TUtentiModPec) obj, TUtentiModPecBean.class,
						new TUtentiModPecToTUtentiModPecBean());
				paginglist.addData(bean);
			}
			return paginglist;
		} catch (Exception e) {
			throw e;
		} finally {
			HibernateUtil.release(session);
		}
	}

	@Override
	@Operation(name = "update")
	public TUtentiModPecBean update(TUtentiModPecBean bean) throws Exception {
		Session session = null;
		try {
			session = HibernateUtil.begin();
			Transaction transaction = session.beginTransaction();
			TUtentiModPec toInsert = (TUtentiModPec) UtilPopulate.populate(bean, TUtentiModPec.class, new TUtentiModPecBeanToTUtentiModPec());
			session.update(toInsert);
			session.flush();
			bean = (TUtentiModPecBean) UtilPopulate.populate(toInsert, TUtentiModPecBean.class, new TUtentiModPecToTUtentiModPecBean());
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
	public void delete(TUtentiModPecBean bean) throws Exception {
		Session session = null;
		try {
			session = HibernateUtil.begin();
			Transaction transaction = session.beginTransaction();
			TUtentiModPec toInsert = (TUtentiModPec) UtilPopulate.populate(bean, TUtentiModPec.class, new TUtentiModPecBeanToTUtentiModPec());
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
	public void forcedelete(TUtentiModPecBean bean) throws Exception {
		Session session = null;
		try {
			session = HibernateUtil.begin();
			Transaction transaction = session.beginTransaction();
			TUtentiModPec toInsert = (TUtentiModPec) UtilPopulate.populate(bean, TUtentiModPec.class, new TUtentiModPecBeanToTUtentiModPec());
			session.delete(toInsert);
			session.flush();
			transaction.commit();
		} catch (Exception e) {
			throw e;
		} finally {
			HibernateUtil.release(session);
		}
	}

	private Criteria buildHibernateCriteriaByFilter(Session session, TFilterFetch<TUtentiModPecBean> filter) throws AurigaMailBusinessException {
		if (filter == null || filter.getFilter() == null || filter.getFilter().getIdUtente() == null) {
			throw new AurigaMailBusinessException(errorSearch);
		}
		Criteria lCriteria = session.createCriteria(TUtentiModPec.class);
		if (filter.getFilter().getIdUtente() != null) {
			lCriteria.add(Restrictions.eq("idUtente", filter.getFilter().getIdUtente()));
		}
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