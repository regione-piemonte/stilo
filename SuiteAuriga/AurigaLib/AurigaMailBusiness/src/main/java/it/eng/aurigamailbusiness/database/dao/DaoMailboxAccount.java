/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import it.eng.aurigamailbusiness.bean.MailboxAccountBean;
import it.eng.aurigamailbusiness.converters.MailboxAccountBeanToMailboxAccount;
import it.eng.aurigamailbusiness.converters.MailboxAccountToMailboxAccountBean;
import it.eng.aurigamailbusiness.database.mail.MailboxAccount;
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

@Service(name = "DaoMailboxAccount")
public class DaoMailboxAccount extends DaoGenericOperations<MailboxAccountBean> {

	public DaoMailboxAccount() {
	}

	private static String errorSearch = "Nessun filtro impostato. Interrotta la ricerca altrimenti sarebbero restituiti tutti gli account configurati";

	@Override
	@Operation(name = "save")
	public MailboxAccountBean save(MailboxAccountBean bean) throws Exception {
		Session session = null;
		try {
			session = HibernateUtil.begin();
			Transaction transaction = session.beginTransaction();
			MailboxAccount toInsert = (MailboxAccount) UtilPopulate.populate(bean, MailboxAccount.class,
					new MailboxAccountBeanToMailboxAccount());
			session.save(toInsert);
			session.flush();
			bean = (MailboxAccountBean) UtilPopulate.populate(toInsert, MailboxAccountBean.class,
					new MailboxAccountToMailboxAccountBean());
			transaction.commit();
			return bean;
		} catch (Exception e) {
			throw e;
		} finally {
			HibernateUtil.release(session);
		}
	}

	public String getRelatedAccount(String idAccount) throws Exception {
		Session session = null;
		try {
			session = HibernateUtil.begin();
			MailboxAccount lMailboxAccount = (MailboxAccount) session.get(MailboxAccount.class, idAccount);
			session.flush();
			return lMailboxAccount.getAccount();
		} catch (Exception e) {
			throw e;
		} finally {
			HibernateUtil.release(session);
		}
	}

	@Override
	@Operation(name = "search")
	public TPagingList<MailboxAccountBean> search(TFilterFetch<MailboxAccountBean> filter) throws Exception {
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
			Integer endRow = filter != null && filter.getEndRow() != null ? filter.getEndRow()
					: startRow + count.intValue() - 1;
			// Creo l'oggetto paginatore
			TPagingList<MailboxAccountBean> paginglist = new TPagingList<MailboxAccountBean>();
			paginglist.setTotalRows(count.intValue());
			paginglist.setStartRow(startRow);
			paginglist.setEndRow(endRow);
			// Recupero i record
			criteria.setProjection(null);
			criteria.setResultTransformer(Criteria.ROOT_ENTITY);
			for (Object obj : HibernateUtil.pagingByCriteria(criteria, startRow, endRow)) {
				MailboxAccountBean bean = (MailboxAccountBean) UtilPopulate.populate((MailboxAccount) obj,
						MailboxAccountBean.class, new MailboxAccountToMailboxAccountBean());
				paginglist.addData(bean);
			}
			return paginglist;
		} catch (Exception e) {
			throw e;
		} finally {
			HibernateUtil.release(session);
		}
	}

	public TPagingList<MailboxAccountBean> searchInSession(TFilterFetch<MailboxAccountBean> filter, Session session)
			throws Exception {
		if (filter == null) {
			throw new AurigaMailBusinessException(errorSearch);
		}
		// Creo i criteri di ricerca
		Criteria criteria = this.buildHibernateCriteriaByFilter(session, filter);
		// Conto i record totali
		Long count = (Long) criteria.setProjection(Projections.rowCount()).uniqueResult();
		Integer startRow = filter.getStartRow() != null ? filter.getStartRow() : 0;
		Integer endRow = filter != null && filter.getEndRow() != null ? filter.getEndRow()
				: startRow + count.intValue() - 1;
		// Creo l'oggetto paginatore
		TPagingList<MailboxAccountBean> paginglist = new TPagingList<MailboxAccountBean>();
		paginglist.setTotalRows(count.intValue());
		paginglist.setStartRow(startRow);
		paginglist.setEndRow(endRow);
		// Recupero i record
		criteria.setProjection(null);
		criteria.setResultTransformer(Criteria.ROOT_ENTITY);
		for (Object obj : HibernateUtil.pagingByCriteria(criteria, startRow, endRow)) {
			MailboxAccountBean bean = (MailboxAccountBean) UtilPopulate.populate((MailboxAccount) obj,
					MailboxAccountBean.class, new MailboxAccountToMailboxAccountBean());
			paginglist.addData(bean);
		}
		return paginglist;
	}

	@Operation(name = "get")
	public MailboxAccountBean get(String idAccount) throws Exception {
		Session session = null;
		MailboxAccountBean result = null;
		try {
			session = HibernateUtil.begin();
			MailboxAccount macc = (MailboxAccount) session.load(MailboxAccount.class, idAccount);
			result = (MailboxAccountBean) UtilPopulate.populate(macc, MailboxAccountBean.class,
					new MailboxAccountToMailboxAccountBean());
		} catch (Exception e) {
			throw e;
		} finally {
			HibernateUtil.release(session);
		}
		return result;
	}

	@Override
	@Operation(name = "update")
	public MailboxAccountBean update(MailboxAccountBean bean) throws Exception {
		Session session = null;
		try {
			session = HibernateUtil.begin();
			Transaction transaction = session.beginTransaction();
			MailboxAccount toUpdate = (MailboxAccount) UtilPopulate.populate(bean, MailboxAccount.class,
					new MailboxAccountBeanToMailboxAccount());
			session.update(toUpdate);
			session.flush();
			bean = (MailboxAccountBean) UtilPopulate.populate(toUpdate, MailboxAccountBean.class,
					new MailboxAccountToMailboxAccountBean());
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
	public void delete(MailboxAccountBean bean) throws Exception {
		Session session = null;
		try {
			session = HibernateUtil.begin();
			Transaction transaction = session.beginTransaction();
			MailboxAccount toDelete = (MailboxAccount) UtilPopulate.populate(bean, MailboxAccount.class,
					new MailboxAccountBeanToMailboxAccount());
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
	public void forcedelete(MailboxAccountBean bean) throws Exception {
		Session session = null;
		try {
			session = HibernateUtil.begin();
			Transaction transaction = session.beginTransaction();
			MailboxAccount toDelete = (MailboxAccount) UtilPopulate.populate(bean, MailboxAccount.class,
					new MailboxAccountBeanToMailboxAccount());
			session.delete(toDelete);
			session.flush();
			transaction.commit();
		} catch (Exception e) {
			throw e;
		} finally {
			HibernateUtil.release(session);
		}

	}

	private Criteria buildHibernateCriteriaByFilter(Session session, TFilterFetch<MailboxAccountBean> filter)
			throws AurigaMailBusinessException {
		Criteria lCriteria = session.createCriteria(MailboxAccount.class);

		if (filter == null || filter.getFilter() == null
				|| (filter.getFilter().getIdAccount() == null && filter.getFilter().getAccount() == null)) {
			throw new AurigaMailBusinessException(errorSearch);
		}

		if (filter.getFilter().getIdAccount() != null) {
			lCriteria.add(Restrictions.eq("idAccount", filter.getFilter().getIdAccount()));
		}
		if (filter.getFilter().getAccount() != null) {
			lCriteria.add(Restrictions.eq("account", filter.getFilter().getAccount().toLowerCase()).ignoreCase());
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
