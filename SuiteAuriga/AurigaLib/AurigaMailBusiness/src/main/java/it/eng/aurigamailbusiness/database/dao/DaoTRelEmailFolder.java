/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import it.eng.aurigamailbusiness.bean.TRelEmailFolderBean;
import it.eng.aurigamailbusiness.converters.TRelEmailFolderBeanToTRelEmailFolder;
import it.eng.aurigamailbusiness.converters.TRelEmailFolderToTRelEmailFolderBean;
import it.eng.aurigamailbusiness.database.mail.TRelEmailFolder;
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

@Service(name = "DaoTRelEmailFolder")
public class DaoTRelEmailFolder extends DaoGenericOperations<TRelEmailFolderBean> {

	public DaoTRelEmailFolder() {
	}

	private static String errorSearch = "Nessun filtro impostato. Interrotta la ricerca altrimenti sarebbero restituite tutte le relazioni fra caselle e mail";

	@Override
	@Operation(name = "save")
	public TRelEmailFolderBean save(TRelEmailFolderBean bean) throws Exception {
		Session session = null;
		try {
			session = HibernateUtil.begin();
			Transaction transaction = session.beginTransaction();
			bean = saveInSession(bean, session);
			session.flush();
			transaction.commit();
			return bean;
		} catch (Exception e) {
			throw e;
		} finally {
			HibernateUtil.release(session);
		}
	}

	public TRelEmailFolderBean saveInSession(TRelEmailFolderBean bean, Session session) throws Exception {
		TRelEmailFolder toInsert = (TRelEmailFolder) UtilPopulate.populate(bean, TRelEmailFolder.class, new TRelEmailFolderBeanToTRelEmailFolder());
		session.save(toInsert);
		session.flush();
		bean = (TRelEmailFolderBean) UtilPopulate.populate(toInsert, TRelEmailFolderBean.class, new TRelEmailFolderToTRelEmailFolderBean());
		return bean;
	}

	@Override
	@Operation(name = "search")
	public TPagingList<TRelEmailFolderBean> search(TFilterFetch<TRelEmailFolderBean> filter) throws Exception {
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
			TPagingList<TRelEmailFolderBean> paginglist = new TPagingList<TRelEmailFolderBean>();
			paginglist.setTotalRows(count.intValue());
			paginglist.setStartRow(startRow);
			paginglist.setEndRow(endRow);
			// Recupero i record
			criteria.setProjection(null);
			criteria.setResultTransformer(Criteria.ROOT_ENTITY);
			for (Object obj : HibernateUtil.pagingByCriteria(criteria, startRow, endRow)) {
				TRelEmailFolderBean bean = (TRelEmailFolderBean) UtilPopulate.populate((TRelEmailFolder) obj, TRelEmailFolderBean.class,
						new TRelEmailFolderToTRelEmailFolderBean());
				paginglist.addData(bean);
			}
			return paginglist;
		} catch (Exception e) {
			throw e;
		} finally {
			HibernateUtil.release(session);
		}
	}

	public TPagingList<TRelEmailFolderBean> searchInSession(TFilterFetch<TRelEmailFolderBean> filter, Session session) throws Exception {
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
		TPagingList<TRelEmailFolderBean> paginglist = new TPagingList<TRelEmailFolderBean>();
		paginglist.setTotalRows(count.intValue());
		paginglist.setStartRow(startRow);
		paginglist.setEndRow(endRow);
		// Recupero i record
		criteria.setProjection(null);
		criteria.setResultTransformer(Criteria.ROOT_ENTITY);
		for (Object obj : HibernateUtil.pagingByCriteria(criteria, startRow, endRow)) {
			TRelEmailFolderBean bean = (TRelEmailFolderBean) UtilPopulate.populate((TRelEmailFolder) obj, TRelEmailFolderBean.class,
					new TRelEmailFolderToTRelEmailFolderBean());
			paginglist.addData(bean);
		}
		return paginglist;
	}

	@Override
	@Operation(name = "update")
	public TRelEmailFolderBean update(TRelEmailFolderBean bean) throws Exception {
		Session session = null;
		try {
			session = HibernateUtil.begin();
			Transaction transaction = session.beginTransaction();
			updateInSession(bean, session);
			transaction.commit();
			return bean;
		} catch (Exception e) {
			throw e;
		} finally {
			HibernateUtil.release(session);
		}
	}

	public TRelEmailFolderBean updateInSession(TRelEmailFolderBean bean, Session session) throws Exception {
		TRelEmailFolder toInsert = (TRelEmailFolder) UtilPopulate.populate(bean, TRelEmailFolder.class, new TRelEmailFolderBeanToTRelEmailFolder());
		session.update(toInsert);
		session.flush();
		bean = (TRelEmailFolderBean) UtilPopulate.populate(toInsert, TRelEmailFolderBean.class, new TRelEmailFolderToTRelEmailFolderBean());
		return bean;
	}

	@Override
	@Operation(name = "delete")
	public void delete(TRelEmailFolderBean bean) throws Exception {
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

	public void deleteInSession(TRelEmailFolderBean bean, Session session) throws Exception {
		TRelEmailFolder toInsert = (TRelEmailFolder) UtilPopulate.populate(bean, TRelEmailFolder.class, new TRelEmailFolderBeanToTRelEmailFolder());
		session.delete(toInsert);
		session.flush();
	}

	@Override
	@Operation(name = "forcedelete")
	public void forcedelete(TRelEmailFolderBean bean) throws Exception {
		Session session = null;
		try {
			session = HibernateUtil.begin();
			Transaction transaction = session.beginTransaction();
			TRelEmailFolder toInsert = (TRelEmailFolder) UtilPopulate.populate(bean, TRelEmailFolder.class, new TRelEmailFolderBeanToTRelEmailFolder());
			session.delete(toInsert);
			session.flush();
			transaction.commit();
		} catch (Exception e) {
			throw e;
		} finally {
			HibernateUtil.release(session);
		}
	}

	private Criteria buildHibernateCriteriaByFilter(Session session, TFilterFetch<TRelEmailFolderBean> filter) throws AurigaMailBusinessException {
		if (filter == null || filter.getFilter() == null || (filter.getFilter().getIdEmail() == null && filter.getFilter().getIdFolderCasella() == null)) {
			throw new AurigaMailBusinessException(errorSearch);
		}
		Criteria lCriteria = session.createCriteria(TRelEmailFolder.class);
		if (filter.getFilter().getIdEmail() != null) {
			lCriteria.add(Restrictions.eq("id.idEmail", filter.getFilter().getIdEmail()));
		}
		if (filter.getFilter().getIdFolderCasella() != null) {
			lCriteria.add(Restrictions.eq("TFolderCaselle.idFolderCasella", filter.getFilter().getIdFolderCasella()));
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