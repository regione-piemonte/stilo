/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import it.eng.aurigamailbusiness.bean.TFolderCaselleBean;
import it.eng.aurigamailbusiness.converters.TFolderCaselleBeanToTFolderCaselle;
import it.eng.aurigamailbusiness.converters.TFolderCaselleToTFolderCaselleBean;
import it.eng.aurigamailbusiness.database.mail.TFolderCaselle;
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

@Service(name = "DaoTFolderCaselle")
public class DaoTFolderCaselle extends DaoGenericOperations<TFolderCaselleBean> {

	public DaoTFolderCaselle() {
	}

	private static String errorSearch = "Nessun filtro impostato. Interrotta la ricerca altrimenti sarebbero restituite tutte le folder associate alle caselle";

	@Override
	@Operation(name = "save")
	public TFolderCaselleBean save(TFolderCaselleBean bean) throws Exception {
		Session session = null;
		try {
			session = HibernateUtil.begin();
			Transaction transaction = session.beginTransaction();
			TFolderCaselle toInsert = (TFolderCaselle) UtilPopulate.populate(bean, TFolderCaselle.class, new TFolderCaselleBeanToTFolderCaselle());
			session.save(toInsert);
			session.flush();
			bean = (TFolderCaselleBean) UtilPopulate.populate(toInsert, TFolderCaselleBean.class, new TFolderCaselleToTFolderCaselleBean());
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
	public TPagingList<TFolderCaselleBean> search(TFilterFetch<TFolderCaselleBean> filter) throws Exception {
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
			TPagingList<TFolderCaselleBean> paginglist = new TPagingList<TFolderCaselleBean>();
			paginglist.setTotalRows(count.intValue());
			paginglist.setStartRow(startRow);
			paginglist.setEndRow(endRow);
			// Recupero i record
			criteria.setProjection(null);
			criteria.setResultTransformer(Criteria.ROOT_ENTITY);
			for (Object obj : HibernateUtil.pagingByCriteria(criteria, startRow, endRow)) {
				TFolderCaselleBean bean = (TFolderCaselleBean) UtilPopulate.populate((TFolderCaselle) obj, TFolderCaselleBean.class,
						new TFolderCaselleToTFolderCaselleBean());
				paginglist.addData(bean);
			}
			return paginglist;
		} catch (Exception e) {
			throw e;
		} finally {
			HibernateUtil.release(session);
		}
	}

	public TPagingList<TFolderCaselleBean> searchInSession(TFilterFetch<TFolderCaselleBean> filter, Session session) throws Exception {
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
		TPagingList<TFolderCaselleBean> paginglist = new TPagingList<TFolderCaselleBean>();
		paginglist.setTotalRows(count.intValue());
		paginglist.setStartRow(startRow);
		paginglist.setEndRow(endRow);
		// Recupero i record
		criteria.setProjection(null);
		criteria.setResultTransformer(Criteria.ROOT_ENTITY);
		for (Object obj : HibernateUtil.pagingByCriteria(criteria, startRow, endRow)) {
			TFolderCaselleBean bean = (TFolderCaselleBean) UtilPopulate.populate((TFolderCaselle) obj, TFolderCaselleBean.class,
					new TFolderCaselleToTFolderCaselleBean());
			paginglist.addData(bean);
		}
		return paginglist;
	}

	@Operation(name = "get")
	public TFolderCaselleBean get(String idFolderCasella) throws Exception {
		Session session = null;
		TFolderCaselleBean result = null;
		try {
			session = HibernateUtil.begin();
			result = getInSession(idFolderCasella, session);
		} catch (Exception e) {
			throw e;
		} finally {
			HibernateUtil.release(session);
		}
		return result;
	}

	public TFolderCaselleBean getInSession(String idFolderCasella, Session session) throws Exception {
		TFolderCaselle folderCasella = (TFolderCaselle) session.load(TFolderCaselle.class, idFolderCasella);
		return (TFolderCaselleBean) UtilPopulate.populate(folderCasella, TFolderCaselleBean.class, new TFolderCaselleToTFolderCaselleBean());
	}

	@Override
	@Operation(name = "update")
	public TFolderCaselleBean update(TFolderCaselleBean bean) throws Exception {
		Session session = null;
		try {
			session = HibernateUtil.begin();
			Transaction transaction = session.beginTransaction();
			TFolderCaselle toInsert = (TFolderCaselle) UtilPopulate.populate(bean, TFolderCaselle.class, new TFolderCaselleBeanToTFolderCaselle());
			session.update(toInsert);
			session.flush();
			bean = (TFolderCaselleBean) UtilPopulate.populate(toInsert, TFolderCaselleBean.class, new TFolderCaselleToTFolderCaselleBean());
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
	public void delete(TFolderCaselleBean bean) throws Exception {
		Session session = null;
		try {
			session = HibernateUtil.begin();
			Transaction transaction = session.beginTransaction();
			TFolderCaselle toInsert = (TFolderCaselle) UtilPopulate.populate(bean, TFolderCaselle.class, new TFolderCaselleBeanToTFolderCaselle());
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
	public void forcedelete(TFolderCaselleBean bean) throws Exception {
		Session session = null;
		try {
			session = HibernateUtil.begin();
			Transaction transaction = session.beginTransaction();
			TFolderCaselle toInsert = (TFolderCaselle) UtilPopulate.populate(bean, TFolderCaselle.class, new TFolderCaselleBeanToTFolderCaselle());
			session.delete(toInsert);
			session.flush();
			transaction.commit();
		} catch (Exception e) {
			throw e;
		} finally {
			HibernateUtil.release(session);
		}
	}

	private Criteria buildHibernateCriteriaByFilter(Session session, TFilterFetch<TFolderCaselleBean> filter) throws AurigaMailBusinessException {
		if (filter == null || filter.getFilter() == null || (filter.getFilter().getClassificazione() == null && filter.getFilter().getIdCasella() == null
				&& filter.getFilter().getIdFolderCasella() == null)) {
			throw new AurigaMailBusinessException(errorSearch);
		}
		Criteria lCriteria = session.createCriteria(TFolderCaselle.class);
		if (filter.getFilter().getClassificazione() != null) {
			lCriteria.add(Restrictions.eq("classificazione", filter.getFilter().getClassificazione()));
		}
		if (filter.getFilter().getIdCasella() != null) {
			lCriteria.add(Restrictions.eq("mailboxAccount.idAccount", filter.getFilter().getIdCasella()));
		}
		if (filter.getFilter().getIdFolderCasella() != null) {
			lCriteria.add(Restrictions.eq("idFolderCasella", filter.getFilter().getIdFolderCasella()));
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