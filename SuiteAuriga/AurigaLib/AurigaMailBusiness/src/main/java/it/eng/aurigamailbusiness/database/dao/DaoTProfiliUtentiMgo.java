/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;

import it.eng.aurigamailbusiness.bean.TProfiliUtentiMgoBean;
import it.eng.aurigamailbusiness.converters.TProfiliUtentiMgoBeanToTProfiliUtentiMgo;
import it.eng.aurigamailbusiness.converters.TProfiliUtentiMgoToTProfiliUtentiMgoBean;
import it.eng.aurigamailbusiness.database.mail.TProfiliUtentiMgo;
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

@Service(name = "DaoTProfiliUtentiMgo")
public class DaoTProfiliUtentiMgo extends DaoGenericOperations<TProfiliUtentiMgoBean> {

	public DaoTProfiliUtentiMgo() {
	}

	private static String errorSearch = "Nessun filtro impostato. Interrotta la ricerca altrimenti sarebbero restituite tutte le abilitazioni associate agli utenti";

	@Override
	@Operation(name = "save")
	public TProfiliUtentiMgoBean save(TProfiliUtentiMgoBean bean) throws Exception {
		Session session = null;
		try {
			session = HibernateUtil.begin();
			Transaction transaction = session.beginTransaction();
			TProfiliUtentiMgo toInsert = (TProfiliUtentiMgo) UtilPopulate.populate(bean, TProfiliUtentiMgo.class,
					new TProfiliUtentiMgoBeanToTProfiliUtentiMgo());
			session.save(toInsert);
			session.flush();
			bean = (TProfiliUtentiMgoBean) UtilPopulate.populate(toInsert, TProfiliUtentiMgoBean.class, new TProfiliUtentiMgoToTProfiliUtentiMgoBean());
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
	public TPagingList<TProfiliUtentiMgoBean> search(TFilterFetch<TProfiliUtentiMgoBean> filter) throws Exception {
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
			TPagingList<TProfiliUtentiMgoBean> paginglist = new TPagingList<TProfiliUtentiMgoBean>();
			paginglist.setTotalRows(count.intValue());
			paginglist.setStartRow(startRow);
			paginglist.setEndRow(endRow);
			// Recupero i record
			criteria.setProjection(null);
			criteria.setResultTransformer(Criteria.ROOT_ENTITY);
			for (Object obj : HibernateUtil.pagingByCriteria(criteria, startRow, endRow)) {
				TProfiliUtentiMgoBean bean = (TProfiliUtentiMgoBean) UtilPopulate.populate((TProfiliUtentiMgo) obj, TProfiliUtentiMgoBean.class,
						new TProfiliUtentiMgoToTProfiliUtentiMgoBean());
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
	public TProfiliUtentiMgoBean update(TProfiliUtentiMgoBean bean) throws Exception {
		Session session = null;
		try {
			session = HibernateUtil.begin();
			Transaction transaction = session.beginTransaction();
			TProfiliUtentiMgo toInsert = (TProfiliUtentiMgo) UtilPopulate.populate(bean, TProfiliUtentiMgo.class,
					new TProfiliUtentiMgoBeanToTProfiliUtentiMgo());
			session.update(toInsert);
			session.flush();
			bean = (TProfiliUtentiMgoBean) UtilPopulate.populate(toInsert, TProfiliUtentiMgoBean.class, new TProfiliUtentiMgoToTProfiliUtentiMgoBean());
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
	public void delete(TProfiliUtentiMgoBean bean) throws Exception {
		Session session = null;
		try {
			session = HibernateUtil.begin();
			Transaction transaction = session.beginTransaction();
			TProfiliUtentiMgo toInsert = (TProfiliUtentiMgo) UtilPopulate.populate(bean, TProfiliUtentiMgo.class,
					new TProfiliUtentiMgoBeanToTProfiliUtentiMgo());
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
	public void forcedelete(TProfiliUtentiMgoBean bean) throws Exception {
		Session session = null;
		try {
			session = HibernateUtil.begin();
			Transaction transaction = session.beginTransaction();
			TProfiliUtentiMgo toInsert = (TProfiliUtentiMgo) UtilPopulate.populate(bean, TProfiliUtentiMgo.class,
					new TProfiliUtentiMgoBeanToTProfiliUtentiMgo());
			session.delete(toInsert);
			session.flush();
			transaction.commit();
		} catch (Exception e) {
			throw e;
		} finally {
			HibernateUtil.release(session);
		}
	}

	private Criteria buildHibernateCriteriaByFilter(Session session, TFilterFetch<TProfiliUtentiMgoBean> filter) throws AurigaMailBusinessException {
		if (filter == null || filter.getFilter() == null) {
			throw new AurigaMailBusinessException(errorSearch);
		}
		Criteria lCriteria = session.createCriteria(TProfiliUtentiMgo.class);
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