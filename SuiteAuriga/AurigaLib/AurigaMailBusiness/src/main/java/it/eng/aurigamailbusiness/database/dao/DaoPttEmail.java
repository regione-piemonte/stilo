/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import it.eng.aurigamailbusiness.bean.PttEmailBean;
import it.eng.aurigamailbusiness.converters.PttEmailBeanToPttEmail;
import it.eng.aurigamailbusiness.converters.PttEmailToPttEmailBean;
import it.eng.aurigamailbusiness.database.egr.PttEmail;
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

@Service(name = "DaoPttEmail")
public class DaoPttEmail extends DaoGenericOperations<PttEmailBean> {

	public DaoPttEmail() {
	}

	private static String errorSearch = "Nessun filtro impostato. Interrotta la ricerca altrimenti sarebbero restituite tutte le mail";

	@Override
	@Operation(name = "save")
	public PttEmailBean save(PttEmailBean bean) throws Exception {
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

	public PttEmailBean saveInSession(PttEmailBean bean, Session session) throws Exception {
		PttEmail toInsert = (PttEmail) UtilPopulate.populate(bean, PttEmail.class, new PttEmailBeanToPttEmail());
		session.save(toInsert);
		session.flush();
		bean = (PttEmailBean) UtilPopulate.populate(toInsert, PttEmailBean.class, new PttEmailToPttEmailBean(session));
		return bean;
	}

	@Operation(name = "get")
	public PttEmailBean get(String idEmail) throws Exception {
		Session session = null;
		PttEmailBean result = null;
		try {
			session = HibernateUtil.begin();
			result = getInSession(idEmail, session);
		} catch (Exception e) {
			throw e;
		} finally {
			HibernateUtil.release(session);
		}
		return result;
	}

	public PttEmailBean getInSession(String idEmail, Session session) throws Exception {
		PttEmail mgo = (PttEmail) session.load(PttEmail.class, idEmail);
		return (PttEmailBean) UtilPopulate.populate(mgo, PttEmail.class, new PttEmailToPttEmailBean(session));
	}

	public PttEmail getTEmailMgoInSession(String idEmail, Session session) throws Exception {
		PttEmail email = (PttEmail) session.load(PttEmail.class, idEmail);
		return email;
	}

	@Override
	@Operation(name = "update")
	public PttEmailBean update(PttEmailBean bean) throws Exception {
		Session session = null;
		try {
			session = HibernateUtil.begin();
			Transaction transaction = session.beginTransaction();
			bean = updateInSession(bean, session);
			session.flush();
			transaction.commit();
			return bean;
		} catch (Exception e) {
			throw e;
		} finally {
			HibernateUtil.release(session);
		}
	}

	public PttEmailBean updateInSession(PttEmailBean bean, Session session) throws Exception {
		PttEmail toInsert = (PttEmail) UtilPopulate.populate(bean, PttEmail.class, new PttEmailBeanToPttEmail());
		session.update(toInsert);
		bean = (PttEmailBean) UtilPopulate.populate(toInsert, PttEmailBean.class, new PttEmailToPttEmailBean(session));
		return bean;
	}

	public PttEmail updateInSession(PttEmail bean, Session session) throws Exception {
		session.update(bean);
		return bean;
	}

	@Override
	@Operation(name = "search")
	public TPagingList<PttEmailBean> search(TFilterFetch<PttEmailBean> filter) throws Exception {
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
			TPagingList<PttEmailBean> paginglist = new TPagingList<PttEmailBean>();
			paginglist.setTotalRows(count.intValue());
			paginglist.setStartRow(startRow);
			paginglist.setEndRow(endRow);
			// Recupero i record
			criteria.setProjection(null);
			criteria.setResultTransformer(Criteria.ROOT_ENTITY);
			for (Object obj : HibernateUtil.pagingByCriteria(criteria, startRow, endRow)) {
				PttEmailBean bean = (PttEmailBean) UtilPopulate.populate((PttEmail) obj, PttEmailBean.class, new PttEmailToPttEmailBean());
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
	@Operation(name = "delete")
	public void delete(PttEmailBean bean) throws Exception {
		Session session = null;
		try {
			session = HibernateUtil.begin();
			Transaction transaction = session.beginTransaction();
			PttEmail toInsert = (PttEmail) UtilPopulate.populate(bean, PttEmail.class, new PttEmailBeanToPttEmail());
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
	public void forcedelete(PttEmailBean bean) throws Exception {
		Session session = null;
		try {
			session = HibernateUtil.begin();
			Transaction transaction = session.beginTransaction();
			PttEmail toInsert = (PttEmail) UtilPopulate.populate(bean, PttEmail.class, new PttEmailBeanToPttEmail());
			session.delete(toInsert);
			session.flush();
			transaction.commit();
		} catch (Exception e) {
			throw e;
		} finally {
			HibernateUtil.release(session);
		}
	}

	private Criteria buildHibernateCriteriaByFilter(Session session, TFilterFetch<PttEmailBean> filter) throws AurigaMailBusinessException {
		if (filter == null || filter.getFilter() == null || (filter.getFilter().getMessageId() == null && filter.getFilter().getIdEmail() == null)) {
			throw new AurigaMailBusinessException(errorSearch);
		}
		Criteria lCriteria = session.createCriteria(PttEmail.class);
		if (filter.getFilter().getMessageId() != null) {
			lCriteria.add(Restrictions.eq("messageId", filter.getFilter().getMessageId()));
		}
		if (filter.getFilter().getIdEmail() != null) {
			lCriteria.add(Restrictions.eq("idEmail", filter.getFilter().getIdEmail()));
		}
		if (filter.getFilter().getEntrataUscita() != null) {
			lCriteria.add(Restrictions.eq("EntrataUscita", filter.getFilter().getEntrataUscita()));
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