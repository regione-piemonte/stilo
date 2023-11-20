/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import it.eng.aurigamailbusiness.bean.MailboxAccountConfigBean;
import it.eng.aurigamailbusiness.converters.MailboxAccountConfigBeanToMailboxAccountConfig;
import it.eng.aurigamailbusiness.converters.MailboxAccountConfigToMailboxAccountConfigBean;
import it.eng.aurigamailbusiness.database.mail.MailboxAccountConfig;
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

@Service(name = "DaoMailboxAccountConfig")
public class DaoMailboxAccountConfig extends DaoGenericOperations<MailboxAccountConfigBean> {

	public DaoMailboxAccountConfig() {
	}

	private static String errorSearch = "Nessun filtro impostato. Interrotta la ricerca altrimenti sarebbero restituite tutte le configurazioni associate agli account";

	@Override
	@Operation(name = "save")
	public MailboxAccountConfigBean save(MailboxAccountConfigBean bean) throws Exception {
		Session session = null;
		try {
			session = HibernateUtil.begin();
			Transaction transaction = session.beginTransaction();
			MailboxAccountConfig toInsert = (MailboxAccountConfig) UtilPopulate.populate(bean, MailboxAccountConfig.class,
					new MailboxAccountConfigBeanToMailboxAccountConfig());
			session.save(toInsert);
			session.flush();
			bean = (MailboxAccountConfigBean) UtilPopulate.populate(toInsert, MailboxAccountConfigBean.class,
					new MailboxAccountConfigToMailboxAccountConfigBean());
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
	public TPagingList<MailboxAccountConfigBean> search(TFilterFetch<MailboxAccountConfigBean> filter) throws Exception {
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
			TPagingList<MailboxAccountConfigBean> paginglist = new TPagingList<MailboxAccountConfigBean>();
			paginglist.setTotalRows(count.intValue());
			paginglist.setStartRow(startRow);
			paginglist.setEndRow(endRow);
			// Recupero i record
			criteria.setProjection(null);
			criteria.setResultTransformer(Criteria.ROOT_ENTITY);
			for (Object obj : HibernateUtil.pagingByCriteria(criteria, startRow, endRow)) {
				MailboxAccountConfigBean bean = (MailboxAccountConfigBean) UtilPopulate.populate((MailboxAccountConfig) obj, MailboxAccountConfigBean.class,
						new MailboxAccountConfigToMailboxAccountConfigBean());
				paginglist.addData(bean);
			}
			return paginglist;
		} catch (Exception e) {
			throw e;
		} finally {
			HibernateUtil.release(session);
		}
	}

	public TPagingList<MailboxAccountConfigBean> searchInSession(TFilterFetch<MailboxAccountConfigBean> filter, Session session) throws Exception {
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
		TPagingList<MailboxAccountConfigBean> paginglist = new TPagingList<MailboxAccountConfigBean>();
		paginglist.setTotalRows(count.intValue());
		paginglist.setStartRow(startRow);
		paginglist.setEndRow(endRow);
		// Recupero i record
		criteria.setProjection(null);
		criteria.setResultTransformer(Criteria.ROOT_ENTITY);
		for (Object obj : HibernateUtil.pagingByCriteria(criteria, startRow, endRow)) {
			MailboxAccountConfigBean bean = (MailboxAccountConfigBean) UtilPopulate.populate((MailboxAccountConfig) obj, MailboxAccountConfigBean.class,
					new MailboxAccountConfigToMailboxAccountConfigBean());
			paginglist.addData(bean);
		}
		return paginglist;
	}

	@Override
	@Operation(name = "update")
	public MailboxAccountConfigBean update(MailboxAccountConfigBean bean) throws Exception {
		Session session = null;
		try {
			session = HibernateUtil.begin();
			Transaction transaction = session.beginTransaction();
			MailboxAccountConfig toUpdate = (MailboxAccountConfig) UtilPopulate.populate(bean, MailboxAccountConfig.class,
					new MailboxAccountConfigBeanToMailboxAccountConfig());
			session.update(toUpdate);
			session.flush();
			bean = (MailboxAccountConfigBean) UtilPopulate.populate(toUpdate, MailboxAccountConfigBean.class,
					new MailboxAccountConfigToMailboxAccountConfigBean());
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
	public void delete(MailboxAccountConfigBean bean) throws Exception {
		Session session = null;
		try {
			session = HibernateUtil.begin();
			Transaction transaction = session.beginTransaction();
			MailboxAccountConfig toDelete = (MailboxAccountConfig) UtilPopulate.populate(bean, MailboxAccountConfig.class,
					new MailboxAccountConfigBeanToMailboxAccountConfig());
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
	public void forcedelete(MailboxAccountConfigBean bean) throws Exception {
		Session session = null;
		try {
			session = HibernateUtil.begin();
			Transaction transaction = session.beginTransaction();
			MailboxAccountConfig toDelete = (MailboxAccountConfig) UtilPopulate.populate(bean, MailboxAccountConfig.class,
					new MailboxAccountConfigBeanToMailboxAccountConfig());
			session.delete(toDelete);
			session.flush();
			transaction.commit();
		} catch (Exception e) {
			throw e;
		} finally {
			HibernateUtil.release(session);
		}

	}

	private Criteria buildHibernateCriteriaByFilter(Session session, TFilterFetch<MailboxAccountConfigBean> filter) throws AurigaMailBusinessException {
		Criteria lCriteria = session.createCriteria(MailboxAccountConfig.class);
		if (filter != null && filter.getFilter() != null && filter.getFilter().getIdAccount() != null) {
			lCriteria.createAlias("mailboxAccount", "mailboxAccount");
			lCriteria.add(Restrictions.eq("mailboxAccount.idAccount", filter.getFilter().getIdAccount()));
		} else {
			throw new AurigaMailBusinessException(errorSearch);
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
