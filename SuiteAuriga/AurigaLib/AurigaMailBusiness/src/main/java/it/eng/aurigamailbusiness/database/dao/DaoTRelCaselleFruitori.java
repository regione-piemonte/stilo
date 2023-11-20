/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import it.eng.aurigamailbusiness.bean.TRelCaselleFruitoriBean;
import it.eng.aurigamailbusiness.converters.TRelCaselleFruitoriBeanToTRelCaselleFruitori;
import it.eng.aurigamailbusiness.converters.TRelCaselleFruitoriToTRelCaselleFruitoriBean;
import it.eng.aurigamailbusiness.database.mail.TRelCaselleFruitori;
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

@Service(name = "DaoTRelCaselleFruitori")
public class DaoTRelCaselleFruitori extends DaoGenericOperations<TRelCaselleFruitoriBean> {

	public DaoTRelCaselleFruitori() {
	}

	private static String errorSearch = "Nessun filtro impostato. Interrotta la ricerca altrimenti sarebbero restituite tutte le relazioni fra caselle e fruitori";

	@Override
	@Operation(name = "save")
	public TRelCaselleFruitoriBean save(TRelCaselleFruitoriBean bean) throws Exception {
		Session session = null;
		try {
			session = HibernateUtil.begin();
			Transaction transaction = session.beginTransaction();
			TRelCaselleFruitori toInsert = (TRelCaselleFruitori) UtilPopulate.populate(bean, TRelCaselleFruitori.class,
					new TRelCaselleFruitoriBeanToTRelCaselleFruitori());
			session.save(toInsert);
			session.flush();
			bean = (TRelCaselleFruitoriBean) UtilPopulate.populate(toInsert, TRelCaselleFruitoriBean.class, new TRelCaselleFruitoriToTRelCaselleFruitoriBean());
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
	public TPagingList<TRelCaselleFruitoriBean> search(TFilterFetch<TRelCaselleFruitoriBean> filter) throws Exception {
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
			TPagingList<TRelCaselleFruitoriBean> paginglist = new TPagingList<TRelCaselleFruitoriBean>();
			paginglist.setTotalRows(count.intValue());
			paginglist.setStartRow(startRow);
			paginglist.setEndRow(endRow);
			// Recupero i record
			criteria.setProjection(null);
			criteria.setResultTransformer(Criteria.ROOT_ENTITY);
			for (Object obj : HibernateUtil.pagingByCriteria(criteria, startRow, endRow)) {
				TRelCaselleFruitoriBean bean = (TRelCaselleFruitoriBean) UtilPopulate.populate((TRelCaselleFruitori) obj, TRelCaselleFruitoriBean.class,
						new TRelCaselleFruitoriToTRelCaselleFruitoriBean());
				paginglist.addData(bean);
			}
			return paginglist;
		} catch (Exception e) {
			throw e;
		} finally {
			HibernateUtil.release(session);
		}
	}

	public TPagingList<TRelCaselleFruitoriBean> searchInSession(TFilterFetch<TRelCaselleFruitoriBean> filter, Session session) throws Exception {
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
		TPagingList<TRelCaselleFruitoriBean> paginglist = new TPagingList<TRelCaselleFruitoriBean>();
		paginglist.setTotalRows(count.intValue());
		paginglist.setStartRow(startRow);
		paginglist.setEndRow(endRow);
		// Recupero i record
		criteria.setProjection(null);
		criteria.setResultTransformer(Criteria.ROOT_ENTITY);
		for (Object obj : HibernateUtil.pagingByCriteria(criteria, startRow, endRow)) {
			TRelCaselleFruitoriBean bean = (TRelCaselleFruitoriBean) UtilPopulate.populate((TRelCaselleFruitori) obj, TRelCaselleFruitoriBean.class,
					new TRelCaselleFruitoriToTRelCaselleFruitoriBean());
			paginglist.addData(bean);
		}
		return paginglist;
	}

	@Override
	@Operation(name = "update")
	public TRelCaselleFruitoriBean update(TRelCaselleFruitoriBean bean) throws Exception {
		Session session = null;
		try {
			session = HibernateUtil.begin();
			Transaction transaction = session.beginTransaction();
			TRelCaselleFruitori toInsert = (TRelCaselleFruitori) UtilPopulate.populate(bean, TRelCaselleFruitori.class,
					new TRelCaselleFruitoriBeanToTRelCaselleFruitori());
			session.update(toInsert);
			session.flush();
			bean = (TRelCaselleFruitoriBean) UtilPopulate.populate(toInsert, TRelCaselleFruitoriBean.class, new TRelCaselleFruitoriToTRelCaselleFruitoriBean());
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
	public void delete(TRelCaselleFruitoriBean bean) throws Exception {
		Session session = null;
		try {
			session = HibernateUtil.begin();
			Transaction transaction = session.beginTransaction();
			TRelCaselleFruitori toInsert = (TRelCaselleFruitori) UtilPopulate.populate(bean, TRelCaselleFruitori.class,
					new TRelCaselleFruitoriBeanToTRelCaselleFruitori());
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
	public void forcedelete(TRelCaselleFruitoriBean bean) throws Exception {
		Session session = null;
		try {
			session = HibernateUtil.begin();
			Transaction transaction = session.beginTransaction();
			TRelCaselleFruitori toInsert = (TRelCaselleFruitori) UtilPopulate.populate(bean, TRelCaselleFruitori.class,
					new TRelCaselleFruitoriBeanToTRelCaselleFruitori());
			session.delete(toInsert);
			session.flush();
			transaction.commit();
		} catch (Exception e) {
			throw e;
		} finally {
			HibernateUtil.release(session);
		}
	}

	private Criteria buildHibernateCriteriaByFilter(Session session, TFilterFetch<TRelCaselleFruitoriBean> filter) throws AurigaMailBusinessException {
		if (filter == null || filter.getFilter() == null || filter.getFilter().getIdCasella() == null) {
			throw new AurigaMailBusinessException(errorSearch);
		}
		Criteria lCriteria = session.createCriteria(TRelCaselleFruitori.class);
		lCriteria.add(Restrictions.eq("mailboxAccount.idAccount", filter.getFilter().getIdCasella()));
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