/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import it.eng.aurigamailbusiness.bean.MailboxBean;
import it.eng.aurigamailbusiness.converters.MailboxBeanToMailbox;
import it.eng.aurigamailbusiness.converters.MailboxToMailboxBean;
import it.eng.aurigamailbusiness.database.mail.Mailbox;
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

@Service(name = "DaoMailbox")
public class DaoMailbox extends DaoGenericOperations<MailboxBean> {

	public DaoMailbox() {
	}

	private static String errorSearch = "Nessun filtro in input. Ricerca interrotta altrimenti sarebbero restituite tutte le mailbox configurate";

	@Override
	@Operation(name = "save")
	public MailboxBean save(MailboxBean bean) throws Exception {
		Session session = null;
		try {
			session = HibernateUtil.begin();
			Transaction transaction = session.beginTransaction();
			Mailbox toInsert = (Mailbox) UtilPopulate.populate(bean, Mailbox.class, new MailboxBeanToMailbox());
			session.save(toInsert);
			session.flush();
			bean = (MailboxBean) UtilPopulate.populate(toInsert, MailboxBean.class, new MailboxToMailboxBean());
			transaction.commit();
			return bean;
		} catch (Exception e) {
			throw e;
		} finally {
			HibernateUtil.release(session);
		}
	}

	@Operation(name = "getAllMailbox")
	public TPagingList<MailboxBean> getAllMailbox(TFilterFetch<MailboxBean> filter) throws Exception {
		Session session = null;
		try {
			session = HibernateUtil.begin();
			// Nessun criterio di ricerca, restituisco tutte le mailbox
			Criteria criteria = session.createCriteria(Mailbox.class);
			// ordinamento
			if (filter != null && ListUtil.isNotEmpty(filter.getOrders())) {
				for (TOrderBy tOrderBy : filter.getOrders()) {
					if (tOrderBy.getType().equals(OrderByType.ASCENDING)) {
						criteria.addOrder(Order.asc(tOrderBy.getPropname()));
					} else {
						criteria.addOrder(Order.desc(tOrderBy.getPropname()));
					}
				}
			} else {
				// default ordinamento per id
				criteria.addOrder(Order.asc("idMailbox"));
			}
			// Conto i record totali
			Long count = (Long) criteria.setProjection(Projections.rowCount()).uniqueResult();
			Integer startRow = filter != null && filter.getStartRow() != null ? filter.getStartRow() : 0;
			Integer endRow = filter != null && filter.getEndRow() != null ? filter.getEndRow() : startRow + count.intValue() - 1;
			// Creo l'oggetto paginatore
			TPagingList<MailboxBean> paginglist = new TPagingList<MailboxBean>();
			paginglist.setTotalRows(count.intValue());
			paginglist.setStartRow(startRow);
			paginglist.setEndRow(endRow);
			// Recupero i record
			criteria.setProjection(null);
			criteria.setResultTransformer(Criteria.ROOT_ENTITY);
			for (Object obj : HibernateUtil.pagingByCriteria(criteria, startRow, endRow)) {
				MailboxBean bean = (MailboxBean) UtilPopulate.populate((Mailbox) obj, MailboxBean.class, new MailboxToMailboxBean());
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
	@Operation(name = "search")
	public TPagingList<MailboxBean> search(TFilterFetch<MailboxBean> filter) throws Exception {
		Session session = null;
		try {
			session = HibernateUtil.begin();
			// Creo i criteri di ricerca
			if (filter == null) {
				throw new AurigaMailBusinessException(errorSearch);
			}
			Criteria criteria = this.buildHibernateCriteriaByFilter(session, filter);
			// Conto i record totali
			Long count = (Long) criteria.setProjection(Projections.rowCount()).uniqueResult();
			Integer startRow = filter.getStartRow() != null ? filter.getStartRow() : 0;
			Integer endRow = filter != null && filter.getEndRow() != null ? filter.getEndRow() : startRow + count.intValue() - 1;
			// Creo l'oggetto paginatore
			TPagingList<MailboxBean> paginglist = new TPagingList<MailboxBean>();
			paginglist.setTotalRows(count.intValue());
			paginglist.setStartRow(startRow);
			paginglist.setEndRow(endRow);
			// Recupero i record
			criteria.setProjection(null);
			criteria.setResultTransformer(Criteria.ROOT_ENTITY);
			for (Object obj : HibernateUtil.pagingByCriteria(criteria, startRow, endRow)) {
				MailboxBean bean = (MailboxBean) UtilPopulate.populate((Mailbox) obj, MailboxBean.class, new MailboxToMailboxBean());
				paginglist.addData(bean);
			}
			return paginglist;
		} catch (Exception e) {
			throw e;
		} finally {
			HibernateUtil.release(session);
		}
	}

	public TPagingList<MailboxBean> searchInSession(TFilterFetch<MailboxBean> filter, Session session) throws Exception {
		// Creo i criteri di ricerca
		if (filter == null) {
			throw new AurigaMailBusinessException(errorSearch);
		}
		Criteria criteria = this.buildHibernateCriteriaByFilter(session, filter);
		// Conto i record totali
		Long count = (Long) criteria.setProjection(Projections.rowCount()).uniqueResult();
		Integer startRow = filter.getStartRow() != null ? filter.getStartRow() : 0;
		Integer endRow = filter != null && filter.getEndRow() != null ? filter.getEndRow() : startRow + count.intValue() - 1;
		// Creo l'oggetto paginatore
		TPagingList<MailboxBean> paginglist = new TPagingList<MailboxBean>();
		paginglist.setTotalRows(count.intValue());
		paginglist.setStartRow(startRow);
		paginglist.setEndRow(endRow);
		// Recupero i record
		criteria.setProjection(null);
		criteria.setResultTransformer(Criteria.ROOT_ENTITY);
		for (Object obj : HibernateUtil.pagingByCriteria(criteria, startRow, endRow)) {
			MailboxBean bean = (MailboxBean) UtilPopulate.populate((Mailbox) obj, MailboxBean.class, new MailboxToMailboxBean());
			paginglist.addData(bean);
		}
		return paginglist;
	}

	@Override
	@Operation(name = "update")
	public MailboxBean update(MailboxBean bean) throws Exception {
		Session session = null;
		try {
			session = HibernateUtil.begin();
			Transaction transaction = session.beginTransaction();
			Mailbox toUpdate = (Mailbox) UtilPopulate.populate(bean, Mailbox.class, new MailboxBeanToMailbox());
			session.update(toUpdate);
			session.flush();
			bean = (MailboxBean) UtilPopulate.populate(toUpdate, MailboxBean.class, new MailboxToMailboxBean());
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
	public void delete(MailboxBean bean) throws Exception {
		Session session = null;
		try {
			session = HibernateUtil.begin();
			Transaction transaction = session.beginTransaction();
			Mailbox toDelete = (Mailbox) UtilPopulate.populate(bean, Mailbox.class, new MailboxBeanToMailbox());
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
	public void forcedelete(MailboxBean bean) throws Exception {
		Session session = null;
		try {
			session = HibernateUtil.begin();
			Transaction transaction = session.beginTransaction();
			Mailbox toDelete = (Mailbox) UtilPopulate.populate(bean, Mailbox.class, new MailboxBeanToMailbox());
			session.delete(toDelete);
			session.flush();
			transaction.commit();
		} catch (Exception e) {
			throw e;
		} finally {
			HibernateUtil.release(session);
		}

	}

	private Criteria buildHibernateCriteriaByFilter(Session session, TFilterFetch<MailboxBean> filter) throws AurigaMailBusinessException {

		if (filter == null || filter.getFilter() == null
				|| (filter.getFilter().getIdAccount() == null && filter.getFilter().getIdMailbox() == null && filter.getFilter().getStatus() == null)) {
			throw new AurigaMailBusinessException(errorSearch);
		}

		Criteria lCriteria = session.createCriteria(Mailbox.class);
		if (filter.getFilter().getIdMailbox() != null) {
			lCriteria.add(Restrictions.eq("idMailbox", filter.getFilter().getIdMailbox()));
		}
		if (filter.getFilter().getIdAccount() != null) {
			lCriteria.add(Restrictions.eq("mailboxAccount.idAccount", filter.getFilter().getIdAccount()));
		}
		if (filter.getFilter().getStatus() != null) {
			lCriteria.add(Restrictions.eq("status", filter.getFilter().getStatus()));
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
