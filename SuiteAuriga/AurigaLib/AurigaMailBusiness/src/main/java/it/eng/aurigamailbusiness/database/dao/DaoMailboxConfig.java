/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import it.eng.aurigamailbusiness.bean.MailboxConfigBean;
import it.eng.aurigamailbusiness.converters.MailboxConfigBeanToMailboxConfig;
import it.eng.aurigamailbusiness.converters.MailboxConfigToMailboxConfigBean;
import it.eng.aurigamailbusiness.database.mail.MailboxConfig;
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

@Service(name = "DaoMailboxConfig")
public class DaoMailboxConfig extends DaoGenericOperations<MailboxConfigBean> {

	public DaoMailboxConfig() {
	}

	private static String errorSearch = "Nessun filtro impostato. Interrotta la ricerca altrimenti sarebbero restituite tutte le configurazioni associate alle mailbox";

	@Override
	@Operation(name = "save")
	public MailboxConfigBean save(MailboxConfigBean bean) throws Exception {
		Session session = null;
		try {
			session = HibernateUtil.begin();
			Transaction transaction = session.beginTransaction();
			MailboxConfig toInsert = (MailboxConfig) UtilPopulate.populate(bean, MailboxConfig.class, new MailboxConfigBeanToMailboxConfig());
			session.save(toInsert);
			session.flush();
			bean = (MailboxConfigBean) UtilPopulate.populate(toInsert, MailboxConfigBean.class, new MailboxConfigToMailboxConfigBean());
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
	public TPagingList<MailboxConfigBean> search(TFilterFetch<MailboxConfigBean> filter) throws Exception {
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
			TPagingList<MailboxConfigBean> paginglist = new TPagingList<MailboxConfigBean>();
			paginglist.setTotalRows(count.intValue());
			paginglist.setStartRow(startRow);
			paginglist.setEndRow(endRow);
			// Recupero i record
			criteria.setProjection(null);
			criteria.setResultTransformer(Criteria.ROOT_ENTITY);
			for (Object obj : HibernateUtil.pagingByCriteria(criteria, startRow, endRow)) {
				MailboxConfigBean bean = (MailboxConfigBean) UtilPopulate.populate((MailboxConfig) obj, MailboxConfigBean.class,
						new MailboxConfigToMailboxConfigBean());
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
	public MailboxConfigBean update(MailboxConfigBean bean) throws Exception {
		Session session = null;
		try {
			session = HibernateUtil.begin();
			Transaction transaction = session.beginTransaction();
			MailboxConfig toUpdate = (MailboxConfig) UtilPopulate.populate(bean, MailboxConfig.class, new MailboxConfigBeanToMailboxConfig());
			session.update(toUpdate);
			session.flush();
			bean = (MailboxConfigBean) UtilPopulate.populate(toUpdate, MailboxConfigBean.class, new MailboxConfigToMailboxConfigBean());
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
	public void delete(MailboxConfigBean bean) throws Exception {
		Session session = null;
		try {
			session = HibernateUtil.begin();
			Transaction transaction = session.beginTransaction();
			MailboxConfig toDelete = (MailboxConfig) UtilPopulate.populate(bean, MailboxConfig.class, new MailboxConfigBeanToMailboxConfig());
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
	public void forcedelete(MailboxConfigBean bean) throws Exception {
		Session session = null;
		try {
			session = HibernateUtil.begin();
			Transaction transaction = session.beginTransaction();
			MailboxConfig toDelete = (MailboxConfig) UtilPopulate.populate(bean, MailboxConfig.class, new MailboxConfigBeanToMailboxConfig());
			session.delete(toDelete);
			session.flush();
			transaction.commit();
		} catch (Exception e) {
			throw e;
		} finally {
			HibernateUtil.release(session);
		}

	}

	private Criteria buildHibernateCriteriaByFilter(Session session, TFilterFetch<MailboxConfigBean> filter) throws AurigaMailBusinessException {
		Criteria lCriteria = session.createCriteria(MailboxConfig.class);
		if (filter.getFilter().getIdMailbox() != null) {
			lCriteria.add(Restrictions.eq("id.idMailbox", filter.getFilter().getIdMailbox()));
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
