/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;

import it.eng.aurigamailbusiness.bean.TSezioniDizBean;
import it.eng.aurigamailbusiness.converters.TSezioniDizBeanToTSezioniDiz;
import it.eng.aurigamailbusiness.converters.TSezioniDizToTSezioniDizBean;
import it.eng.aurigamailbusiness.database.mail.TSezioniDiz;
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

@Service(name = "DaoTSezioniDiz")
public class DaoTSezioniDiz extends DaoGenericOperations<TSezioniDizBean> {

	public DaoTSezioniDiz() {
	}

	private static String errorSearch = "Nessun filtro impostato. Interrotta la ricerca altrimenti sarebbero restituite tutte le sezioni del dizionario di sistema";

	@Override
	@Operation(name = "save")
	public TSezioniDizBean save(TSezioniDizBean bean) throws Exception {
		Session session = null;
		try {
			session = HibernateUtil.begin();
			Transaction transaction = session.beginTransaction();
			TSezioniDiz toInsert = (TSezioniDiz) UtilPopulate.populate(bean, TSezioniDiz.class, new TSezioniDizBeanToTSezioniDiz());
			session.save(toInsert);
			session.flush();
			bean = (TSezioniDizBean) UtilPopulate.populate(toInsert, TSezioniDizBean.class, new TSezioniDizToTSezioniDizBean());
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
	public TPagingList<TSezioniDizBean> search(TFilterFetch<TSezioniDizBean> filter) throws Exception {
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
			TPagingList<TSezioniDizBean> paginglist = new TPagingList<TSezioniDizBean>();
			paginglist.setTotalRows(count.intValue());
			paginglist.setStartRow(startRow);
			paginglist.setEndRow(endRow);
			// Recupero i record
			criteria.setProjection(null);
			criteria.setResultTransformer(Criteria.ROOT_ENTITY);
			for (Object obj : HibernateUtil.pagingByCriteria(criteria, startRow, endRow)) {
				TSezioniDizBean bean = (TSezioniDizBean) UtilPopulate.populate((TSezioniDiz) obj, TSezioniDizBean.class, new TSezioniDizToTSezioniDizBean());
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
	public TSezioniDizBean update(TSezioniDizBean bean) throws Exception {
		Session session = null;
		try {
			session = HibernateUtil.begin();
			Transaction transaction = session.beginTransaction();
			TSezioniDiz toInsert = (TSezioniDiz) UtilPopulate.populate(bean, TSezioniDiz.class, new TSezioniDizBeanToTSezioniDiz());
			session.update(toInsert);
			session.flush();
			bean = (TSezioniDizBean) UtilPopulate.populate(toInsert, TSezioniDizBean.class, new TSezioniDizToTSezioniDizBean());
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
	public void delete(TSezioniDizBean bean) throws Exception {
		Session session = null;
		try {
			session = HibernateUtil.begin();
			Transaction transaction = session.beginTransaction();
			TSezioniDiz toInsert = (TSezioniDiz) UtilPopulate.populate(bean, TSezioniDiz.class, new TSezioniDizBeanToTSezioniDiz());
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
	public void forcedelete(TSezioniDizBean bean) throws Exception {
		Session session = null;
		try {
			session = HibernateUtil.begin();
			Transaction transaction = session.beginTransaction();
			TSezioniDiz toInsert = (TSezioniDiz) UtilPopulate.populate(bean, TSezioniDiz.class, new TSezioniDizBeanToTSezioniDiz());
			session.delete(toInsert);
			session.flush();
			transaction.commit();
		} catch (Exception e) {
			throw e;
		} finally {
			HibernateUtil.release(session);
		}
	}

	private Criteria buildHibernateCriteriaByFilter(Session session, TFilterFetch<TSezioniDizBean> filter) throws AurigaMailBusinessException {
		if (filter == null || filter.getFilter() == null) {
			throw new AurigaMailBusinessException(errorSearch);
		}
		Criteria lCriteria = session.createCriteria(TSezioniDiz.class);
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