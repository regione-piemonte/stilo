/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import it.eng.aurigamailbusiness.bean.TAnagFruitoriCaselleBean;
import it.eng.aurigamailbusiness.converters.TAnagFruitoriCaselleBeanToTAnagFruitoriCaselle;
import it.eng.aurigamailbusiness.converters.TAnagFruitoriCaselleToTAnagFruitoriCaselleBean;
import it.eng.aurigamailbusiness.database.mail.TAnagFruitoriCaselle;
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

@Service(name = "DaoTAnagFruitoriCaselle")
public class DaoTAnagFruitoriCaselle extends DaoGenericOperations<TAnagFruitoriCaselleBean> {

	public DaoTAnagFruitoriCaselle() {
	}

	private static String errorSearch = "Nessun filtro impostato. Interrotta la ricerca altrimenti sarebbero restituiti tutti i fruitori associati agli account";

	@Override
	@Operation(name = "save")
	public TAnagFruitoriCaselleBean save(TAnagFruitoriCaselleBean bean) throws Exception {
		Session session = null;
		try {
			session = HibernateUtil.begin();
			Transaction transaction = session.beginTransaction();
			TAnagFruitoriCaselle toInsert = (TAnagFruitoriCaselle) UtilPopulate.populate(bean, TAnagFruitoriCaselle.class,
					new TAnagFruitoriCaselleBeanToTAnagFruitoriCaselle());
			session.save(toInsert);
			session.flush();
			bean = (TAnagFruitoriCaselleBean) UtilPopulate.populate(toInsert, TAnagFruitoriCaselleBean.class,
					new TAnagFruitoriCaselleToTAnagFruitoriCaselleBean());
			transaction.commit();
			return bean;
		} catch (Exception e) {
			throw e;
		} finally {
			HibernateUtil.release(session);
		}
	}

	@Operation(name = "get")
	public TAnagFruitoriCaselleBean get(String idFruitoreCasella) throws Exception {
		Session session = null;
		TAnagFruitoriCaselleBean result = null;
		try {
			session = HibernateUtil.begin();
			result = getInSession(idFruitoreCasella, session);
		} catch (Exception e) {
			throw e;
		} finally {
			HibernateUtil.release(session);
		}
		return result;
	}

	public TAnagFruitoriCaselleBean getInSession(String idFruitoreCasella, Session session) throws Exception {
		TAnagFruitoriCaselle fruitore = (TAnagFruitoriCaselle) session.load(TAnagFruitoriCaselle.class, idFruitoreCasella);
		return (TAnagFruitoriCaselleBean) UtilPopulate.populate(fruitore, TAnagFruitoriCaselleBean.class, new TAnagFruitoriCaselleToTAnagFruitoriCaselleBean());
	}

	@Override
	@Operation(name = "search")
	public TPagingList<TAnagFruitoriCaselleBean> search(TFilterFetch<TAnagFruitoriCaselleBean> filter) throws Exception {
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
			TPagingList<TAnagFruitoriCaselleBean> paginglist = new TPagingList<TAnagFruitoriCaselleBean>();
			paginglist.setTotalRows(count.intValue());
			paginglist.setStartRow(startRow);
			paginglist.setEndRow(endRow);
			// Recupero i record
			criteria.setProjection(null);
			criteria.setResultTransformer(Criteria.ROOT_ENTITY);
			for (Object obj : HibernateUtil.pagingByCriteria(criteria, startRow, endRow)) {
				TAnagFruitoriCaselleBean bean = (TAnagFruitoriCaselleBean) UtilPopulate.populate((TAnagFruitoriCaselle) obj, TAnagFruitoriCaselleBean.class,
						new TAnagFruitoriCaselleToTAnagFruitoriCaselleBean());
				paginglist.addData(bean);
			}
			return paginglist;
		} catch (Exception e) {
			throw e;
		} finally {
			HibernateUtil.release(session);
		}
	}

	public TPagingList<TAnagFruitoriCaselleBean> searchInSession(TFilterFetch<TAnagFruitoriCaselleBean> filter, Session session) throws Exception {
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
		TPagingList<TAnagFruitoriCaselleBean> paginglist = new TPagingList<TAnagFruitoriCaselleBean>();
		paginglist.setTotalRows(count.intValue());
		paginglist.setStartRow(startRow);
		paginglist.setEndRow(endRow);
		// Recupero i record
		criteria.setProjection(null);
		criteria.setResultTransformer(Criteria.ROOT_ENTITY);
		for (Object obj : HibernateUtil.pagingByCriteria(criteria, startRow, endRow)) {
			TAnagFruitoriCaselleBean bean = (TAnagFruitoriCaselleBean) UtilPopulate.populate((TAnagFruitoriCaselle) obj, TAnagFruitoriCaselleBean.class,
					new TAnagFruitoriCaselleToTAnagFruitoriCaselleBean());
			paginglist.addData(bean);
		}
		return paginglist;
	}

	@Override
	@Operation(name = "update")
	public TAnagFruitoriCaselleBean update(TAnagFruitoriCaselleBean bean) throws Exception {
		Session session = null;
		try {
			session = HibernateUtil.begin();
			Transaction transaction = session.beginTransaction();
			TAnagFruitoriCaselle toUpdate = (TAnagFruitoriCaselle) UtilPopulate.populate(bean, TAnagFruitoriCaselle.class,
					new TAnagFruitoriCaselleBeanToTAnagFruitoriCaselle());
			session.update(toUpdate);
			session.flush();
			bean = (TAnagFruitoriCaselleBean) UtilPopulate.populate(toUpdate, TAnagFruitoriCaselleBean.class,
					new TAnagFruitoriCaselleToTAnagFruitoriCaselleBean());
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
	public void delete(TAnagFruitoriCaselleBean bean) throws Exception {
		Session session = null;
		try {
			session = HibernateUtil.begin();
			Transaction transaction = session.beginTransaction();
			TAnagFruitoriCaselle toDelete = (TAnagFruitoriCaselle) UtilPopulate.populate(bean, TAnagFruitoriCaselle.class,
					new TAnagFruitoriCaselleBeanToTAnagFruitoriCaselle());
			session.delete(toDelete);
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
	public void forcedelete(TAnagFruitoriCaselleBean bean) throws Exception {
		Session session = null;
		try {
			session = HibernateUtil.begin();
			Transaction transaction = session.beginTransaction();
			TAnagFruitoriCaselle toDelete = (TAnagFruitoriCaselle) UtilPopulate.populate(bean, TAnagFruitoriCaselle.class,
					new TAnagFruitoriCaselleBeanToTAnagFruitoriCaselle());
			session.delete(toDelete);
			session.flush();
			transaction.commit();
		} catch (Exception e) {
			throw e;
		} finally {
			HibernateUtil.release(session);
		}
	}

	private Criteria buildHibernateCriteriaByFilter(Session session, TFilterFetch<TAnagFruitoriCaselleBean> filter) throws AurigaMailBusinessException {
		if (filter == null || filter.getFilter() == null
				|| (filter.getFilter().getIdFruitoreCasella() == null && filter.getFilter().getIdProvFruitore() == null)) {
			throw new AurigaMailBusinessException(errorSearch);
		}
		Criteria lCriteria = session.createCriteria(TAnagFruitoriCaselle.class);
		if (filter.getFilter().getIdFruitoreCasella() != null) {
			lCriteria.add(Restrictions.eq("idFruitoreCasella", filter.getFilter().getIdFruitoreCasella()));
		}
		if (filter.getFilter().getIdProvFruitore() != null) {
			lCriteria.add(Restrictions.eq("idProvFruitore", filter.getFilter().getIdProvFruitore()));
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