/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.auriga.module.business.beans.AurigaLoginBean;
import it.eng.auriga.module.business.dao.beans.UdDaInviareConservBean;
import it.eng.auriga.module.business.entity.TUdDaInviareConserv;
import it.eng.core.annotation.Operation;
import it.eng.core.annotation.Service;
import it.eng.core.business.DaoGenericOperations;
import it.eng.core.business.HibernateUtil;
import it.eng.core.business.TFilterFetch;
import it.eng.core.business.TPagingList;
import it.eng.core.business.converter.UtilPopulate;
import it.eng.core.business.subject.SubjectBean;
import it.eng.core.business.subject.SubjectUtil;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

@Service(name = "DaoTUdDaInviareConserv")
public class DaoTUdDaInviareConserv extends DaoGenericOperations<UdDaInviareConservBean> {

	private static final Logger logger = Logger.getLogger(DaoTUdDaInviareConserv.class.getName());

	@Operation(name = "search")
	public TPagingList<UdDaInviareConservBean> searchWithLogin(AurigaLoginBean loginBean, TFilterFetch<UdDaInviareConservBean> filter)
			throws Exception {
		SubjectBean subject = SubjectUtil.subject.get();
		subject.setIdDominio(loginBean.getSchema());
		SubjectUtil.subject.set(subject);
		return this.search(filter);
	}

	private Criteria creaCriteri(Session session, TFilterFetch<UdDaInviareConservBean> filter) throws Exception {
		Criteria crit = session.createCriteria(TUdDaInviareConserv.class);
		if (filter.getFilter().getIdSoggVers() != null) {
			crit.add(Restrictions.eq("idSoggVers", filter.getFilter().getIdSoggVers()));
		}
		if (filter.getFilter().getTsInvioConserv() != null) {
			crit.add(Restrictions.eq("tsInvioConserv", filter.getFilter().getTsInvioConserv()));
		} else {
			crit.add(Restrictions.isNull("tsInvioConserv"));
		}
		crit.addOrder(Order.asc("useridAppl"));
		crit.addOrder(Order.asc("idSoggVers"));
		if (StringUtils.isNotEmpty(filter.getFilter().getFetchSize())) {
			crit.setMaxResults(Integer.parseInt(filter.getFilter().getFetchSize()));
		}
		return crit;
	}

	@Override
	public TPagingList<UdDaInviareConservBean> search(TFilterFetch<UdDaInviareConservBean> filter) throws Exception {
		Session session = null;
		try {
			session = HibernateUtil.begin();
			// Creo i criteri di ricerca
			Criteria criteria = creaCriteri(session, filter);
			// Conto i record totali
			Long count = (Long) criteria.setProjection(Projections.rowCount()).uniqueResult();
			Integer startRow = (filter != null && filter.getStartRow() != null) ? filter.getStartRow() : 0;
			Integer endRow = (filter != null && filter.getEndRow() != null) ? filter.getEndRow() : startRow + count.intValue() - 1;
			// Creo l'oggetto paginatore
			TPagingList<UdDaInviareConservBean> paginglist = new TPagingList<UdDaInviareConservBean>();
			paginglist.setTotalRows(count.intValue());
			paginglist.setStartRow(startRow);
			paginglist.setEndRow(endRow);
			// Recupero i record
			criteria.setProjection(null);
			criteria.setResultTransformer(Criteria.ROOT_ENTITY);
			for (Object obj : HibernateUtil.pagingByCriteria(criteria, filter.getStartRow(), filter.getEndRow())) {
				UdDaInviareConservBean bean = (UdDaInviareConservBean) UtilPopulate.populate((TUdDaInviareConserv) obj,
						UdDaInviareConservBean.class);
				paginglist.addData(bean);
			}
			return paginglist;
		} catch (Exception e) {
			logger.error("Errore " + e.getMessage(), e);
			throw e;
		} finally {
			HibernateUtil.release(session);
		}
	}

	@Operation(name = "save")
	public UdDaInviareConservBean saveWithLogin(AurigaLoginBean loginBean, UdDaInviareConservBean bean) throws Exception {
		SubjectBean subject = SubjectUtil.subject.get();
		subject.setIdDominio(loginBean.getSchema());
		SubjectUtil.subject.set(subject);
		return this.save(bean);
	}

	@Override
	public UdDaInviareConservBean save(UdDaInviareConservBean bean) throws Exception {
		Session session = null;
		Transaction lTransaction = null;
		try {
			session = HibernateUtil.begin();
			lTransaction = session.beginTransaction();
			TUdDaInviareConserv ud = (TUdDaInviareConserv) UtilPopulate.populate(bean, TUdDaInviareConserv.class);
			session.save(ud);
			session.flush();
			lTransaction.commit();
			return bean;
		} catch (Exception e) {
			logger.error("Errore " + e.getMessage(), e);
			throw e;
		} finally {
			HibernateUtil.release(session);
		}
	}

	@Operation(name = "update")
	public UdDaInviareConservBean updateWithLogin(AurigaLoginBean loginBean, UdDaInviareConservBean bean) throws Exception {
		SubjectBean subject = SubjectUtil.subject.get();
		subject.setIdDominio(loginBean.getSchema());
		SubjectUtil.subject.set(subject);
		return this.update(bean);
	}

	@Override
	public UdDaInviareConservBean update(UdDaInviareConservBean bean) throws Exception {
		Session session = null;
		Transaction lTransaction = null;
		try {
			session = HibernateUtil.begin();
			lTransaction = session.beginTransaction();
			if (bean != null) {
				TUdDaInviareConserv ud = (TUdDaInviareConserv) UtilPopulate.populateForUpdate(session, bean, TUdDaInviareConserv.class);
				session.update(ud);
			}
			session.flush();
			lTransaction.commit();
			return bean;
		} catch (Exception e) {
			throw e;
		} finally {
			HibernateUtil.release(session);
		}
	}

	@Operation(name = "delete")
	public void deleteWithLogin(AurigaLoginBean loginBean, UdDaInviareConservBean bean) throws Exception {
		SubjectBean subject = SubjectUtil.subject.get();
		subject.setIdDominio(loginBean.getSchema());
		SubjectUtil.subject.set(subject);
		this.delete(bean);
	}

	@Override
	public void delete(UdDaInviareConservBean bean) throws Exception {
		Session session = null;
		Transaction lTransaction = null;
		try {
			session = HibernateUtil.begin();
			lTransaction = session.beginTransaction();
			TUdDaInviareConserv ud = (TUdDaInviareConserv) session.get(TUdDaInviareConserv.class, bean.getIdUd());
			if (ud != null) {
				session.delete(ud);
			}
			session.flush();
			lTransaction.commit();
		} catch (Exception e) {
			throw e;
		} finally {
			HibernateUtil.release(session);
		}
	}

	@Override
	public void forcedelete(UdDaInviareConservBean bean) throws Exception {
		return;
	}

}
