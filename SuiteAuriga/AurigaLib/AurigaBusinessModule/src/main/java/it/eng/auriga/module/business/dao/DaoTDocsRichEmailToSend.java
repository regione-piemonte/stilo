/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.auriga.module.business.beans.AurigaLoginBean;
import it.eng.auriga.module.business.dao.beans.DocsRichEmailToSendBean;
import it.eng.auriga.module.business.dao.beansconverters.DocsRichEmailToSendBeanToTDocsRichEmailToSendConverter;
import it.eng.auriga.module.business.dao.beansconverters.TDocsRichEmailToSendToDocsRichEmailToSendBeanConverter;
import it.eng.auriga.module.business.entity.TDocsRichEmailToSend;
import it.eng.core.annotation.Operation;
import it.eng.core.annotation.Service;
import it.eng.core.business.DaoGenericOperations;
import it.eng.core.business.HibernateUtil;
import it.eng.core.business.KeyGenerator;
import it.eng.core.business.TFilterFetch;
import it.eng.core.business.TPagingList;
import it.eng.core.business.converter.UtilPopulate;
import it.eng.core.business.subject.SubjectBean;
import it.eng.core.business.subject.SubjectUtil;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

@Service(name = "DaoTDocsRichEmailToSend")
public class DaoTDocsRichEmailToSend extends DaoGenericOperations<DocsRichEmailToSendBean> {

	private static final Logger logger = Logger.getLogger(DaoTDocsRichEmailToSend.class);

	public DaoTDocsRichEmailToSend() {
	}

	@Override
	public DocsRichEmailToSendBean save(DocsRichEmailToSendBean bean) throws Exception {

		Session session = null;
		Transaction ltransaction = null;
		try {
			session = HibernateUtil.begin();
			ltransaction = session.beginTransaction();
			if (bean != null) {
				
				// imposta la chiave se non già impostata
			    if (bean.getIdDocRichEmailToSend() == null || bean.getIdDocRichEmailToSend().isEmpty()) {
			    	bean.setIdDocRichEmailToSend(KeyGenerator.gen());
			    }
				
				TDocsRichEmailToSend lTDocsRichEmailToSend = (TDocsRichEmailToSend) UtilPopulate.populate(bean, TDocsRichEmailToSend.class,
						new DocsRichEmailToSendBeanToTDocsRichEmailToSendConverter(session));
				session.save(lTDocsRichEmailToSend);
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
	public DocsRichEmailToSendBean saveWithLogin(AurigaLoginBean pAurigaLoginBean, DocsRichEmailToSendBean bean) throws Exception {
		SubjectBean subject = SubjectUtil.subject.get();
		subject.setIdDominio(pAurigaLoginBean.getSchema());
		SubjectUtil.subject.set(subject);
		return this.save(bean);
	}

	@Override
	public TPagingList<DocsRichEmailToSendBean> search(TFilterFetch<DocsRichEmailToSendBean> filter) throws Exception {

		Session session = null;
		try {

			session = HibernateUtil.begin();

			Criteria criteria = this.buildHibernateCriteriaByFilter(session, filter);

			Long count = (Long) criteria.setProjection(Projections.rowCount()).uniqueResult();
			Integer startRow = (filter != null && filter.getStartRow() != null) ? filter.getStartRow() : 0;
			Integer endRow = (filter != null && filter.getEndRow() != null) ? filter.getEndRow() : startRow + count.intValue() - 1;

			// Creo l'oggetto paginatore
			TPagingList<DocsRichEmailToSendBean> paginglist = new TPagingList<DocsRichEmailToSendBean>();
			paginglist.setTotalRows(count.intValue());
			paginglist.setStartRow(startRow);
			paginglist.setEndRow(endRow);

			criteria.setProjection(null);
			criteria.setResultTransformer(Criteria.ROOT_ENTITY);
			for (Object obj : HibernateUtil.pagingByCriteria(criteria, filter.getStartRow(), filter.getEndRow())) {
				DocsRichEmailToSendBean bean = (DocsRichEmailToSendBean) UtilPopulate.populate((TDocsRichEmailToSend) obj,
						DocsRichEmailToSendBean.class, new TDocsRichEmailToSendToDocsRichEmailToSendBeanConverter(session));
				paginglist.addData(bean);
			}

			return paginglist;

		}

		catch (Exception e) {
			logger.error("Errore " + e.getMessage(), e);
			throw e;
		} finally {
			HibernateUtil.release(session);
		}

	}

	@Operation(name = "search")
	public TPagingList<DocsRichEmailToSendBean> searchWithLogin(AurigaLoginBean pAurigaLoginBean,
			TFilterFetch<DocsRichEmailToSendBean> filter) throws Exception {
		SubjectBean subject = SubjectUtil.subject.get();
		subject.setIdDominio(pAurigaLoginBean.getSchema());
		SubjectUtil.subject.set(subject);
		return this.search(filter);
	}

	@Override
	public DocsRichEmailToSendBean update(DocsRichEmailToSendBean bean) throws Exception {
		Session session = null;
		Transaction ltransaction = null;
		try {
			session = HibernateUtil.begin();
			ltransaction = session.beginTransaction();
			if (bean != null) {
				TDocsRichEmailToSend lDocsRichEmailToSend = (TDocsRichEmailToSend) UtilPopulate.populateForUpdate(session, bean,
						TDocsRichEmailToSend.class, new DocsRichEmailToSendBeanToTDocsRichEmailToSendConverter(session));
				session.update(lDocsRichEmailToSend);
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
	public DocsRichEmailToSendBean updateWithLogin(AurigaLoginBean pAurigaLoginBean, DocsRichEmailToSendBean bean) throws Exception {
		SubjectBean subject = SubjectUtil.subject.get();
		subject.setIdDominio(pAurigaLoginBean.getSchema());
		SubjectUtil.subject.set(subject);
		return this.update(bean);
	}

	@Override
	public void delete(DocsRichEmailToSendBean bean) throws Exception {
		Session session = null;
		Transaction ltransaction = null;
		try {
			session = HibernateUtil.begin();
			ltransaction = session.beginTransaction();
			if (bean != null) {
				TDocsRichEmailToSend lTDocsRichEmailToSend = (TDocsRichEmailToSend) UtilPopulate.populate(bean, TDocsRichEmailToSend.class,
						new DocsRichEmailToSendBeanToTDocsRichEmailToSendConverter(session));
				session.delete(lTDocsRichEmailToSend);
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
	public void deleteWithLogin(AurigaLoginBean pAurigaLoginBean, DocsRichEmailToSendBean bean) throws Exception {
		SubjectBean subject = SubjectUtil.subject.get();
		subject.setIdDominio(pAurigaLoginBean.getSchema());
		SubjectUtil.subject.set(subject);
		this.delete(bean);
	}

	@Override
	public void forcedelete(DocsRichEmailToSendBean bean) throws Exception {

		this.delete(bean);

	}

	@Operation(name = "forcedelete")
	public void forcedeleteWithLogin(AurigaLoginBean pAurigaLoginBean, DocsRichEmailToSendBean bean) throws Exception {
		SubjectBean subject = SubjectUtil.subject.get();
		subject.setIdDominio(pAurigaLoginBean.getSchema());
		SubjectUtil.subject.set(subject);
		this.forcedelete(bean);
	}

	public DocsRichEmailToSendBean get(DocsRichEmailToSendBean bean) throws Exception {
		Session session = null;
		try {
			session = HibernateUtil.begin();

			String id = bean.getIdDocRichEmailToSend();

			TDocsRichEmailToSend lTDocsRichEmailToSend = (TDocsRichEmailToSend) session.get(TDocsRichEmailToSend.class, id);
			return (DocsRichEmailToSendBean) UtilPopulate.populate(lTDocsRichEmailToSend, DocsRichEmailToSendBean.class,
					new TDocsRichEmailToSendToDocsRichEmailToSendBeanConverter(session));

		} catch (Exception e) {
			logger.error("Errore " + e.getMessage(), e);
			throw e;
		} finally {
			HibernateUtil.release(session);
		}
	}

	@Operation(name = "get")
	public DocsRichEmailToSendBean getWithLogin(AurigaLoginBean pAurigaLoginBean, DocsRichEmailToSendBean bean) throws Exception {
		SubjectBean subject = SubjectUtil.subject.get();
		subject.setIdDominio(pAurigaLoginBean.getSchema());
		SubjectUtil.subject.set(subject);
		return this.get(bean);
	}

	private Criteria buildHibernateCriteriaByFilter(Session session, TFilterFetch<DocsRichEmailToSendBean> filter) throws Exception {

		Criteria criteria = session.createCriteria(TDocsRichEmailToSend.class);
		if (filter != null) {
			DocsRichEmailToSendBean bean = filter.getFilter();
			if (bean != null) {
				if (bean.getIdDocRichEmailToSend() != null && !bean.getIdDocRichEmailToSend().equals(""))
					criteria.add(Restrictions.eq("idDocRichEmailToSend", bean.getIdDocRichEmailToSend()));

			}
			HibernateUtil.addOrderCriteria(criteria, filter.getOrders());
		}
		return criteria;

	}

}
