/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.math.BigDecimal;

import org.apache.log4j.Logger;
import org.codehaus.plexus.util.ExceptionUtils;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import it.eng.auriga.module.business.beans.AurigaLoginBean;
import it.eng.auriga.module.business.dao.beans.JobBean;
import it.eng.auriga.module.business.dao.beans.JobParameterBean;
import it.eng.auriga.module.business.dao.beansconverters.BmtJobParametersToJobParameterBeanConverter;
import it.eng.auriga.module.business.dao.beansconverters.JobParameterBeanToBmtJobParametersConverter;

import it.eng.bean.ExecutionResultBean;
import it.eng.core.annotation.Operation;
import it.eng.core.annotation.Service;
import it.eng.core.business.DaoGenericOperations;
import it.eng.core.business.HibernateUtil;
import it.eng.core.business.TFilterFetch;
import it.eng.core.business.TPagingList;
import it.eng.core.business.converter.UtilPopulate;
import it.eng.core.business.subject.SubjectBean;
import it.eng.core.business.subject.SubjectUtil;
import it.eng.entity.BmtJobParameters;
import it.eng.entity.BmtJobParametersId;
import it.eng.entity.BmtJobs;
import it.eng.job.generaRelataPubbl.GeneraRelataPubblicazioneJob;

public class DaoBmtJobParameters extends DaoGenericOperations<JobParameterBean> {

	private static final Logger logger = Logger.getLogger(DaoBmtJobParameters.class);
	private static final String JOB_CLASS_NAME = DaoBmtJobParameters.class.getName();
	public DaoBmtJobParameters() {
	}

	@Override
	public JobParameterBean save(JobParameterBean bean) throws Exception {
		Session session = null;
		Transaction ltransaction = null;
		try {
			session = HibernateUtil.begin();
			ltransaction = session.beginTransaction();
			saveInSession(bean, session);
			ltransaction.commit();
			return bean;
		} finally {
			HibernateUtil.release(session);
		}
	}

	public JobParameterBean saveInSession(JobParameterBean bean, Session session) throws Exception {
		if (bean != null) {
			BmtJobParameters lBmtJobParameters = (BmtJobParameters) UtilPopulate.populate(bean, BmtJobParameters.class,
					new JobParameterBeanToBmtJobParametersConverter());
			session.save(lBmtJobParameters);
			session.flush();
		}
		return bean;
	}

	@Operation(name = "save")
	public JobParameterBean saveWithLogin(AurigaLoginBean pAurigaLoginBean, JobParameterBean bean) throws Exception {
		SubjectBean subject = SubjectUtil.subject.get();
		subject.setIdDominio(pAurigaLoginBean.getSchema());
		SubjectUtil.subject.set(subject);
		return this.save(bean);
	}

	@Override
	public TPagingList<JobParameterBean> search(TFilterFetch<JobParameterBean> filter) throws Exception {
		Session session = null;
		try {
			session = it.eng.database.utility.HibernateUtil.begin(JOB_CLASS_NAME);
			Criteria criteria = buildHibernateCriteriaByFilter(session, filter);

			// Creo l'oggetto paginatore
			TPagingList<JobParameterBean> paginglist = new TPagingList<JobParameterBean>();

			if (filter.getEndRow() != null && filter.getStartRow() != null) {
				paginglist.setTotalRows(filter.getEndRow() - filter.getStartRow());
				paginglist.setStartRow(filter.getStartRow());
				paginglist.setEndRow(filter.getEndRow());
			}

			criteria.setProjection(null);
			criteria.setResultTransformer(Criteria.ROOT_ENTITY);

			for (Object obj : HibernateUtil.pagingByCriteria(criteria, filter.getStartRow(), filter.getEndRow())) {
				JobParameterBean bean = (JobParameterBean) UtilPopulate.populate((BmtJobParameters) obj, JobParameterBean.class,
						new BmtJobParametersToJobParameterBeanConverter());
				paginglist.addData(bean);
			}
			return paginglist;
		} catch (Exception e) {
			logger.error("Errore " + e.getMessage(), e);
			throw e;
		} finally {
			it.eng.database.utility.HibernateUtil.release(session);
		}
	}
	public it.eng.entity.BmtJobParameters searchEntity(TFilterFetch<JobParameterBean> filter) throws Exception {
		Session session = null;
		try {
			session = it.eng.database.utility.HibernateUtil.begin(JOB_CLASS_NAME);
			Criteria criteria = buildHibernateCriteriaByFilter(session, filter);

			// Creo l'oggetto paginatore
			TPagingList<JobParameterBean> paginglist = new TPagingList<JobParameterBean>();

			if (filter.getEndRow() != null && filter.getStartRow() != null) {
				paginglist.setTotalRows(filter.getEndRow() - filter.getStartRow());
				paginglist.setStartRow(filter.getStartRow());
				paginglist.setEndRow(filter.getEndRow());
			}

			criteria.setProjection(null);
			criteria.setResultTransformer(Criteria.ROOT_ENTITY);
			it.eng.entity.BmtJobParameters obj = new BmtJobParameters();
			for (Object obj1 : HibernateUtil.pagingByCriteria(criteria, filter.getStartRow(), filter.getEndRow())) {
				obj = (it.eng.entity.BmtJobParameters) obj1;
			}
			return obj;
		} catch (Exception e) {
			logger.error("Errore " + e.getMessage(), e);
			throw e;
		} finally {
			it.eng.database.utility.HibernateUtil.release(session);
		}
	}
	public it.eng.entity.BmtJobParameters saveParameter(it.eng.entity.BmtJobParameters jb) throws Exception {
		Session session = null;
		Transaction ltransaction = null;
		try {
			session = it.eng.database.utility.HibernateUtil.begin(JOB_CLASS_NAME);
			ltransaction = session.beginTransaction();
			if (jb != null) {
				 session.save(jb);
			
				
			}
			session.flush();
			ltransaction.commit();
			return jb;
		} catch (Exception e) {
			ltransaction.rollback();
			
			throw e;
		} finally {
			HibernateUtil.release(session);
		}
	}

	/**
	 * Ritorna il numero di righe trovate utilizzando il filtro specificato
	 * 
	 * @param filter
	 * @return
	 * @throws Exception
	 */
	public ExecutionResultBean<Integer> countRows(AurigaLoginBean pAurigaLoginBean, TFilterFetch<JobParameterBean> filter) throws Exception {

		Session session = null;
		try {

			session = HibernateUtil.begin();
			Criteria criteria = buildHibernateCriteriaByFilter(session, filter);

			ExecutionResultBean<Integer> retValue = new ExecutionResultBean<Integer>();
			retValue.setSuccessful(Boolean.TRUE);
			retValue.setResult(((Long) criteria.setProjection(Projections.rowCount()).uniqueResult()).intValue());

			return retValue;

		} catch (Exception e) {

			String message = String.format("Durante il recupero del numero totale di righe si è verificata la seguente eccezione %1$s",
					ExceptionUtils.getFullStackTrace(e));

			ExecutionResultBean<Integer> retValue = new ExecutionResultBean<Integer>();
			retValue.setSuccessful(false);
			retValue.setMessage(message);

			logger.error(message);

			return retValue;

		} finally {
			HibernateUtil.release(session);
		}
	}

	@Operation(name = "search")
	public TPagingList<JobParameterBean> searchWithLogin(AurigaLoginBean pAurigaLoginBean, TFilterFetch<JobParameterBean> filter) throws Exception {
		SubjectBean subject = SubjectUtil.subject.get();
		subject.setIdDominio(pAurigaLoginBean.getSchema());
		SubjectUtil.subject.set(subject);
		return this.search(filter);
	}

	@Override
	public JobParameterBean update(JobParameterBean bean) throws Exception {
		Session session = null;
		Transaction ltransaction = null;
		try {
			session = HibernateUtil.begin();
			ltransaction = session.beginTransaction();
			if (bean != null) {
				BmtJobParameters lBmtJobParameters = (BmtJobParameters) UtilPopulate.populate(bean, BmtJobParameters.class,
						new JobParameterBeanToBmtJobParametersConverter());
				session.update(lBmtJobParameters);
			}
			session.flush();
			ltransaction.commit();
			return bean;
		} finally {
			HibernateUtil.release(session);
		}
	}

	@Operation(name = "update")
	public JobParameterBean updateWithLogin(AurigaLoginBean pAurigaLoginBean, JobParameterBean bean) throws Exception {
		SubjectBean subject = SubjectUtil.subject.get();
		subject.setIdDominio(pAurigaLoginBean.getSchema());
		SubjectUtil.subject.set(subject);
		return this.update(bean);
	}

	@Override
	public void delete(JobParameterBean bean) throws Exception {
		Session session = null;
		Transaction ltransaction = null;
		try {
			session = HibernateUtil.begin();
			ltransaction = session.beginTransaction();
			if (bean != null) {
				BmtJobParameters lBmtJobParameters = (BmtJobParameters) UtilPopulate.populate(bean, BmtJobParameters.class,
						new JobParameterBeanToBmtJobParametersConverter());
				session.delete(lBmtJobParameters);
			}
			session.flush();
			ltransaction.commit();
		} finally {
			HibernateUtil.release(session);
		}
	}

	@Operation(name = "delete")
	public void deleteWithLogin(AurigaLoginBean pAurigaLoginBean, JobParameterBean bean) throws Exception {
		SubjectBean subject = SubjectUtil.subject.get();
		subject.setIdDominio(pAurigaLoginBean.getSchema());
		SubjectUtil.subject.set(subject);
		this.delete(bean);
	}

	@Override
	public void forcedelete(JobParameterBean bean) throws Exception {
		this.delete(bean);
	}

	@Operation(name = "forcedelete")
	public void forcedeleteWithLogin(AurigaLoginBean pAurigaLoginBean, JobParameterBean bean) throws Exception {
		SubjectBean subject = SubjectUtil.subject.get();
		subject.setIdDominio(pAurigaLoginBean.getSchema());
		SubjectUtil.subject.set(subject);
		this.forcedelete(bean);
	}

	public BmtJobParameters get(JobParameterBean bean) throws Exception {
		Session session = null;
		try {
			session = it.eng.database.utility.HibernateUtil.begin(JOB_CLASS_NAME);
			BmtJobParametersId lBmtJobParametersId = new BmtJobParametersId(bean.getIdJob(), bean.getParameterId());
			BmtJobParameters lBmtJobParameters = (BmtJobParameters) session.get(BmtJobParameters.class, lBmtJobParametersId);
			return lBmtJobParameters;
			//return (JobParameterBean) UtilPopulate.populate(lBmtJobParameters, JobParameterBean.class, new BmtJobParametersToJobParameterBeanConverter());
		} catch (Exception e) {
			logger.error("Errore " + e.getMessage(), e);
			throw e;
		} finally {
			it.eng.database.utility.HibernateUtil.release(session);
		}
	}

/*	@Operation(name = "get")
	public JobParameterBean getWithLogin(AurigaLoginBean pAurigaLoginBean, JobParameterBean bean) throws Exception {
		SubjectBean subject = SubjectUtil.subject.get();
		subject.setIdDominio(pAurigaLoginBean.getSchema());
		SubjectUtil.subject.set(subject);
		return this.get(bean);
	}*/

	private Criteria buildHibernateCriteriaByFilter(Session session, TFilterFetch<JobParameterBean> filter) throws Exception {

		Criteria criteria = session.createCriteria(BmtJobParameters.class);

		if (filter != null) {

			JobParameterBean bean = filter.getFilter();

			if (bean != null) {

				if (bean.getIdJob() != null && !bean.getIdJob().equals("")) {

					String fieldValue = bean.getOperators().get("idJob");

					if (fieldValue != null && !fieldValue.isEmpty()) {

						if (fieldValue.toLowerCase().equals("equal")) {
							criteria.add(Restrictions.eq("id.idJob", bean.getIdJob()));
						} else if (fieldValue.toLowerCase().equals("notequal")) {
							criteria.add(Restrictions.not(Restrictions.eq("id.idJob", bean.getIdJob())));
						}
					} else {
						criteria.add(Restrictions.eq("id.idJob", bean.getIdJob()));
					}
				}

				if (bean.getParameterId() != null && !bean.getParameterId().equals("")) {

					String operator = bean.getOperators().get("parameterId");

					if (operator != null && !operator.isEmpty()) {

						if (operator.toLowerCase().equals("equal")) {
							criteria.add(Restrictions.eq("id.parameterId", bean.getParameterId()));
						} else if (operator.toLowerCase().equals("notequal")) {
							criteria.add(Restrictions.not(Restrictions.eq("id.parameterId", bean.getParameterId())));
						}
					} else {
						criteria.add(Restrictions.eq("id.parameterId", bean.getParameterId()));
					}
				}

				if (bean.getParameterSubtype() != null && !bean.getParameterSubtype().isEmpty()) {

					String fieldValue = bean.getOperators().get("parameterSubtype");

					if (fieldValue != null && !fieldValue.isEmpty()) {

						if (fieldValue.toLowerCase().equals("equal")) {
							criteria.add(Restrictions.eq("parameterSubtype", bean.getParameterSubtype()));
						} else if (fieldValue.toLowerCase().equals("notequal")) {
							criteria.add(Restrictions.not(Restrictions.eq("parameterSubtype", bean.getParameterSubtype())));
						}
					} else {
						criteria.add(Restrictions.eq("parameterSubtype", bean.getParameterSubtype()));
					}

				}
			}

			HibernateUtil.addOrderCriteria(criteria, filter.getOrders());
		}
		return criteria;
	}
}
