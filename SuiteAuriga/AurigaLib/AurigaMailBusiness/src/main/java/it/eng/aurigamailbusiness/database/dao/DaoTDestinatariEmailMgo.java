/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import it.eng.aurigamailbusiness.bean.TDestinatariEmailMgoBean;
import it.eng.aurigamailbusiness.converters.TDestinatariEmailMgoBeanToTDestinatariEmailMgo;
import it.eng.aurigamailbusiness.converters.TDestinatariEmailMgoToTDestinatariEmailMgoBean;
import it.eng.aurigamailbusiness.database.mail.TDestinatariEmailMgo;
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

@Service(name = "DaoTDestinatariEmailMgo")
public class DaoTDestinatariEmailMgo extends DaoGenericOperations<TDestinatariEmailMgoBean> {

	public DaoTDestinatariEmailMgo() {
		// TODO Auto-generated constructor stub
	}

	private static String errorSearch = "Nessun filtro impostato. Interrotta la ricerca altrimenti sarebbero restituiti tutti gli allegati delle mail";

	@Override
	@Operation(name = "save")
	public TDestinatariEmailMgoBean save(TDestinatariEmailMgoBean bean) throws Exception {
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

	public TDestinatariEmailMgoBean saveInSession(TDestinatariEmailMgoBean bean, Session session) throws Exception {
		TDestinatariEmailMgo toInsert = (TDestinatariEmailMgo) UtilPopulate.populate(bean, TDestinatariEmailMgo.class,
				new TDestinatariEmailMgoBeanToTDestinatariEmailMgo());
		session.save(toInsert);
		session.flush();
		bean = (TDestinatariEmailMgoBean) UtilPopulate.populate(toInsert, TDestinatariEmailMgoBean.class, new TDestinatariEmailMgoToTDestinatariEmailMgoBean());
		return bean;
	}

	@Override
	@Operation(name = "search")
	public TPagingList<TDestinatariEmailMgoBean> search(TFilterFetch<TDestinatariEmailMgoBean> filter) throws Exception {
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
			TPagingList<TDestinatariEmailMgoBean> paginglist = new TPagingList<>();
			paginglist.setTotalRows(count.intValue());
			paginglist.setStartRow(startRow);
			paginglist.setEndRow(endRow);
			// Recupero i record
			criteria.setProjection(null);
			criteria.setResultTransformer(Criteria.ROOT_ENTITY);
			for (Object obj : HibernateUtil.pagingByCriteria(criteria, startRow, endRow)) {
				TDestinatariEmailMgoBean bean = (TDestinatariEmailMgoBean) UtilPopulate.populate((TDestinatariEmailMgo) obj, TDestinatariEmailMgoBean.class,
						new TDestinatariEmailMgoToTDestinatariEmailMgoBean());
				paginglist.addData(bean);
			}
			return paginglist;
		} catch (Exception e) {
			throw e;
		} finally {
			HibernateUtil.release(session);
		}
	}

	public TPagingList<TDestinatariEmailMgoBean> searchInSession(TFilterFetch<TDestinatariEmailMgoBean> filter, Session session) throws Exception {
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
		TPagingList<TDestinatariEmailMgoBean> paginglist = new TPagingList<TDestinatariEmailMgoBean>();
		paginglist.setTotalRows(count.intValue());
		paginglist.setStartRow(startRow);
		paginglist.setEndRow(endRow);
		// Recupero i record
		criteria.setProjection(null);
		criteria.setResultTransformer(Criteria.ROOT_ENTITY);
		for (Object obj : HibernateUtil.pagingByCriteria(criteria, startRow, endRow)) {
			TDestinatariEmailMgoBean bean = (TDestinatariEmailMgoBean) UtilPopulate.populate((TDestinatariEmailMgo) obj, TDestinatariEmailMgoBean.class,
					new TDestinatariEmailMgoToTDestinatariEmailMgoBean());
			paginglist.addData(bean);
		}
		return paginglist;
	}

	@Override
	@Operation(name = "update")
	public TDestinatariEmailMgoBean update(TDestinatariEmailMgoBean bean) throws Exception {
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

	public TDestinatariEmailMgoBean updateInSession(TDestinatariEmailMgoBean bean, Session session) throws Exception {
		TDestinatariEmailMgo toInsert = (TDestinatariEmailMgo) UtilPopulate.populate(bean, TDestinatariEmailMgo.class,
				new TDestinatariEmailMgoBeanToTDestinatariEmailMgo());
		session.update(toInsert);
		session.flush();
		bean = (TDestinatariEmailMgoBean) UtilPopulate.populate(toInsert, TDestinatariEmailMgoBean.class, new TDestinatariEmailMgoToTDestinatariEmailMgoBean());
		return bean;
	}

	@Override
	@Operation(name = "delete")
	public void delete(TDestinatariEmailMgoBean bean) throws Exception {
		Session session = null;
		try {
			session = HibernateUtil.begin();
			Transaction transaction = session.beginTransaction();
			TDestinatariEmailMgo toInsert = (TDestinatariEmailMgo) UtilPopulate.populate(bean, TDestinatariEmailMgo.class,
					new TDestinatariEmailMgoBeanToTDestinatariEmailMgo());
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
	public void forcedelete(TDestinatariEmailMgoBean bean) throws Exception {
		Session session = null;
		try {
			session = HibernateUtil.begin();
			Transaction transaction = session.beginTransaction();
			TDestinatariEmailMgo toInsert = (TDestinatariEmailMgo) UtilPopulate.populate(bean, TDestinatariEmailMgo.class,
					new TDestinatariEmailMgoBeanToTDestinatariEmailMgo());
			session.delete(toInsert);
			session.flush();
			transaction.commit();
		} catch (Exception e) {
			throw e;
		} finally {
			HibernateUtil.release(session);
		}
	}

	private Criteria buildHibernateCriteriaByFilter(Session session, TFilterFetch<TDestinatariEmailMgoBean> filter) throws AurigaMailBusinessException {
		if (filter == null || filter.getFilter() == null || (filter.getFilter().getIdEmail() == null && filter.getFilter().getAccountDestinatario() == null)) {
			throw new AurigaMailBusinessException(errorSearch);
		}
		Criteria lCriteria = session.createCriteria(TDestinatariEmailMgo.class);

		if (filter.getFilter().getIdEmail() != null) {
			lCriteria.add(Restrictions.eq("TEmailMgo.idEmail", filter.getFilter().getIdEmail()));
		}
		if (filter.getFilter().getAccountDestinatario() != null) {
			lCriteria.add(Restrictions.eq("accountDestinatario", filter.getFilter().getAccountDestinatario()));
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