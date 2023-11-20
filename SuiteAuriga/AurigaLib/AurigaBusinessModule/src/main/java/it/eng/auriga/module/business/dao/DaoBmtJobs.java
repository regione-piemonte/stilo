/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.math.BigDecimal;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.type.StringType;
import org.hibernate.type.Type;

import it.eng.auriga.module.business.beans.AurigaLoginBean;
import it.eng.auriga.module.business.dao.beans.JobBean;
import it.eng.auriga.module.business.entity.BmtJobs;
import it.eng.core.annotation.Operation;
import it.eng.core.annotation.Service;
import it.eng.core.business.DaoGenericOperations;
import it.eng.core.business.HibernateUtil;
import it.eng.core.business.TFilterFetch;
import it.eng.core.business.TPagingList;
import it.eng.core.business.converter.UtilPopulate;
import it.eng.core.business.subject.SubjectBean;
import it.eng.core.business.subject.SubjectUtil;

@Service(name = "DaoBmtJobs")
public class DaoBmtJobs extends DaoGenericOperations<JobBean> {

	private static final Logger logger = Logger.getLogger(DaoBmtJobs.class);

	public DaoBmtJobs() {

	}

	@Override
	public JobBean save(JobBean bean) throws Exception {
		Session session = null;
		Transaction ltransaction = null;
		try {
			session = HibernateUtil.begin();
			ltransaction = session.beginTransaction();
			if (bean != null) {
				BmtJobs lBmtJobs = (BmtJobs) UtilPopulate.populate(bean, BmtJobs.class, null);
				BigDecimal jobId = (BigDecimal) session.save(lBmtJobs);
				bean.setIdJob(jobId);
			}
			session.flush();
			ltransaction.commit();
			return bean;
		} catch (Exception e) {
			throw e;
		} finally {
			HibernateUtil.release(session);
		}
	}

	@Operation(name = "save")
	public JobBean saveWithLogin(AurigaLoginBean pAurigaLoginBean, JobBean bean) throws Exception {
		SubjectBean subject = SubjectUtil.subject.get();
		subject.setIdDominio(pAurigaLoginBean.getSchema());
		SubjectUtil.subject.set(subject);
		return this.save(bean);
	}

	@Override
	public TPagingList<JobBean> search(TFilterFetch<JobBean> filter) throws Exception {
		Session session = null;
		try {
			session = HibernateUtil.begin();
			Criteria criteria = this.buildHibernateCriteriaByFilter(session, filter);
			Long count = (Long) criteria.setProjection(Projections.rowCount()).uniqueResult();
			Integer startRow = (filter != null && filter.getStartRow() != null) ? filter.getStartRow() : 0;
			Integer endRow = (filter != null && filter.getEndRow() != null) ? filter.getEndRow() : startRow + count.intValue() - 1;
			// Creo l'oggetto paginatore
			TPagingList<JobBean> paginglist = new TPagingList<JobBean>();
			paginglist.setTotalRows(count.intValue());
			paginglist.setStartRow(startRow);
			paginglist.setEndRow(endRow);
			criteria.setProjection(null);
			criteria.setResultTransformer(Criteria.ROOT_ENTITY);
			for (Object obj : HibernateUtil.pagingByCriteria(criteria, filter.getStartRow(), filter.getEndRow())) {
				JobBean bean = (JobBean) UtilPopulate.populate((BmtJobs) obj, JobBean.class, null);
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

	@Operation(name = "search")
	public TPagingList<JobBean> searchWithLogin(AurigaLoginBean pAurigaLoginBean, TFilterFetch<JobBean> filter) throws Exception {
		SubjectBean subject = SubjectUtil.subject.get();
		subject.setIdDominio(pAurigaLoginBean.getSchema());
		SubjectUtil.subject.set(subject);
		return this.search(filter);
	}

	@Override
	public JobBean update(JobBean bean) throws Exception {
		Session session = null;
		Transaction ltransaction = null;
		try {
			session = HibernateUtil.begin();
			ltransaction = session.beginTransaction();
			if (bean != null) {
				BmtJobs lBmtJobs = (BmtJobs) UtilPopulate.populate(bean, BmtJobs.class, null);
				session.update(lBmtJobs);
			}
			session.flush();
			ltransaction.commit();
			return bean;
		} catch (Exception e) {
			throw e;
		} finally {
			HibernateUtil.release(session);
		}
	}

	@Operation(name = "update")
	public JobBean updateWithLogin(AurigaLoginBean pAurigaLoginBean, JobBean bean) throws Exception {
		SubjectBean subject = SubjectUtil.subject.get();
		subject.setIdDominio(pAurigaLoginBean.getSchema());
		SubjectUtil.subject.set(subject);
		return this.update(bean);
	}

	@Override
	public void delete(JobBean bean) throws Exception {
		Session session = null;
		Transaction ltransaction = null;
		try {
			session = HibernateUtil.begin();
			ltransaction = session.beginTransaction();
			if (bean != null) {
				BmtJobs lBmtJobs = (BmtJobs) UtilPopulate.populate(bean, BmtJobs.class, null);
				session.delete(lBmtJobs);
			}
			session.flush();
			ltransaction.commit();
		} catch (Exception e) {
			throw e;
		} finally {
			HibernateUtil.release(session);
		}
	}

	@Operation(name = "delete")
	public void deleteWithLogin(AurigaLoginBean pAurigaLoginBean, JobBean bean) throws Exception {
		SubjectBean subject = SubjectUtil.subject.get();
		subject.setIdDominio(pAurigaLoginBean.getSchema());
		SubjectUtil.subject.set(subject);
		this.delete(bean);
	}

	@Override
	public void forcedelete(JobBean bean) throws Exception {
		this.delete(bean);
	}

	@Operation(name = "forcedelete")
	public void forcedeleteWithLogin(AurigaLoginBean pAurigaLoginBean, JobBean bean) throws Exception {
		SubjectBean subject = SubjectUtil.subject.get();
		subject.setIdDominio(pAurigaLoginBean.getSchema());
		SubjectUtil.subject.set(subject);
		this.forcedelete(bean);
	}

	public JobBean get(JobBean bean) throws Exception {
		Session session = null;
		try {
			session = HibernateUtil.begin();
			BmtJobs lBmtJobs = (BmtJobs) session.get(BmtJobs.class, bean.getIdJob());
			return (JobBean) UtilPopulate.populate(lBmtJobs, JobBean.class, null);
		} catch (Exception e) {
			logger.error("Errore " + e.getMessage(), e);
			throw e;
		} finally {
			HibernateUtil.release(session);
		}
	}

	@Operation(name = "get")
	public JobBean getWithLogin(AurigaLoginBean pAurigaLoginBean, JobBean bean) throws Exception {
		SubjectBean subject = SubjectUtil.subject.get();
		subject.setIdDominio(pAurigaLoginBean.getSchema());
		SubjectUtil.subject.set(subject);
		return this.get(bean);
	}

	private Criteria buildHibernateCriteriaByFilter(Session session, TFilterFetch<JobBean> filter) throws Exception {
		Criteria criteria = session.createCriteria(BmtJobs.class);
		if (filter != null) {
			JobBean bean = filter.getFilter();
			if (bean != null) {
				if (bean.getIdJob() != null) {
					criteria.add(Restrictions.eq("idJob", bean.getIdJob()));
				}
				if (bean.getTipo() != null && !bean.getTipo().equals("")) {
					criteria.add(Restrictions.eq("tipo", bean.getTipo()));
				}
				if (bean.getParametri() != null && !bean.getParametri().equals("")) {
					// criteria.add(Restrictions.eq("parametri", bean.getParametri()));
					criteria.add(Restrictions.sqlRestriction("DBMS_LOB.COMPARE(PARAMETRI, ?) = 0", new Object[] { bean.getParametri() },
							new Type[] { StringType.INSTANCE }));
				}
				if (bean.getStatus() != null && !bean.getStatus().equals("")) {
					if (bean.getStatus().equalsIgnoreCase("#NULL")) {
						criteria.add(Restrictions.isNull("status"));
					} else {
						criteria.add(Restrictions.eq("status", bean.getStatus()));
					}
				}
			}
			HibernateUtil.addOrderCriteria(criteria, filter.getOrders());
		}
		return criteria;
	}

}
