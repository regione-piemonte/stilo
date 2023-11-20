/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.auriga.module.business.beans.AurigaLoginBean;
import it.eng.auriga.module.business.dao.beans.OperRichEmailToSendBean;
import it.eng.auriga.module.business.dao.beansconverters.OperRichEmailToSendBeanToTOperRichEmailToSendConverter;
import it.eng.auriga.module.business.dao.beansconverters.TOperRichEmailToSendToOperRichEmailToSendBeanConverter;
import it.eng.auriga.module.business.entity.TOperRichEmailToSend;
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

@Service(name = "DaoTOperRichEmailToSend")
public class DaoTOperRichEmailToSend extends DaoGenericOperations<OperRichEmailToSendBean> {

	private static final Logger logger = Logger.getLogger(DaoTOperRichEmailToSend.class);

	public DaoTOperRichEmailToSend() {
	}

	@Override
	public OperRichEmailToSendBean save(OperRichEmailToSendBean bean) throws Exception {

		Session session = null;
		Transaction ltransaction = null;
		
		try {
			
			session = HibernateUtil.begin();
			ltransaction = session.beginTransaction();
			if (bean != null) {
				
				// imposta la chiave se non già impostata
			    if (bean.getIdOperRichEmailToSend() == null || bean.getIdOperRichEmailToSend().isEmpty()) {
			    	bean.setIdOperRichEmailToSend(KeyGenerator.gen());
			    }
				
				TOperRichEmailToSend lTOperRichEmailToSend = (TOperRichEmailToSend) UtilPopulate.populate(bean, TOperRichEmailToSend.class,
						new OperRichEmailToSendBeanToTOperRichEmailToSendConverter(session));
				session.save(lTOperRichEmailToSend);
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
	public OperRichEmailToSendBean saveWithLogin(AurigaLoginBean pAurigaLoginBean, OperRichEmailToSendBean bean) throws Exception {
		SubjectBean subject = SubjectUtil.subject.get();
		subject.setIdDominio(pAurigaLoginBean.getSchema());
		SubjectUtil.subject.set(subject);
		return this.save(bean);
	}

	@Override
	public TPagingList<OperRichEmailToSendBean> search(TFilterFetch<OperRichEmailToSendBean> filter) throws Exception {

		Session session = null;
		try {

			session = HibernateUtil.begin();

			Criteria criteria = this.buildHibernateCriteriaByFilter(session, filter);

			Long count = (Long) criteria.setProjection(Projections.rowCount()).uniqueResult();
			Integer startRow = (filter != null && filter.getStartRow() != null) ? filter.getStartRow() : 0;
			Integer endRow = (filter != null && filter.getEndRow() != null) ? filter.getEndRow() : startRow + count.intValue() - 1;

			// Creo l'oggetto paginatore
			TPagingList<OperRichEmailToSendBean> paginglist = new TPagingList<OperRichEmailToSendBean>();
			paginglist.setTotalRows(count.intValue());
			paginglist.setStartRow(startRow);
			paginglist.setEndRow(endRow);

			criteria.setProjection(null);
			criteria.setResultTransformer(Criteria.ROOT_ENTITY);
			for (Object obj : HibernateUtil.pagingByCriteria(criteria, filter.getStartRow(), filter.getEndRow())) {
				OperRichEmailToSendBean bean = (OperRichEmailToSendBean) UtilPopulate.populate((TOperRichEmailToSend) obj,
						OperRichEmailToSendBean.class, new TOperRichEmailToSendToOperRichEmailToSendBeanConverter(session));
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
	public TPagingList<OperRichEmailToSendBean> searchWithLogin(AurigaLoginBean pAurigaLoginBean,
			TFilterFetch<OperRichEmailToSendBean> filter) throws Exception {
		SubjectBean subject = SubjectUtil.subject.get();
		subject.setIdDominio(pAurigaLoginBean.getSchema());
		SubjectUtil.subject.set(subject);
		return this.search(filter);
	}

	@Override
	public OperRichEmailToSendBean update(OperRichEmailToSendBean bean) throws Exception {
		Session session = null;
		Transaction ltransaction = null;
		try {
			session = HibernateUtil.begin();
			ltransaction = session.beginTransaction();
			if (bean != null) {
				TOperRichEmailToSend lTOperRichEmailToSend = (TOperRichEmailToSend) UtilPopulate.populateForUpdate(session, bean,
						TOperRichEmailToSend.class, new OperRichEmailToSendBeanToTOperRichEmailToSendConverter(session));
				session.update(lTOperRichEmailToSend);
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
	public OperRichEmailToSendBean updateWithLogin(AurigaLoginBean pAurigaLoginBean, OperRichEmailToSendBean bean) throws Exception {
		SubjectBean subject = SubjectUtil.subject.get();
		subject.setIdDominio(pAurigaLoginBean.getSchema());
		SubjectUtil.subject.set(subject);
		return this.update(bean);
	}

	@Override
	public void delete(OperRichEmailToSendBean bean) throws Exception {
		Session session = null;
		Transaction ltransaction = null;
		try {
			session = HibernateUtil.begin();
			ltransaction = session.beginTransaction();
			if (bean != null) {
				TOperRichEmailToSend lTOperRichEmailToSend = (TOperRichEmailToSend) UtilPopulate.populate(bean, TOperRichEmailToSend.class,
						new OperRichEmailToSendBeanToTOperRichEmailToSendConverter(session));
				session.delete(lTOperRichEmailToSend);
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
	public void deleteWithLogin(AurigaLoginBean pAurigaLoginBean, OperRichEmailToSendBean bean) throws Exception {
		SubjectBean subject = SubjectUtil.subject.get();
		subject.setIdDominio(pAurigaLoginBean.getSchema());
		SubjectUtil.subject.set(subject);
		this.delete(bean);
	}

	@Override
	public void forcedelete(OperRichEmailToSendBean bean) throws Exception {

		this.delete(bean);

	}

	@Operation(name = "forcedelete")
	public void forcedeleteWithLogin(AurigaLoginBean pAurigaLoginBean, OperRichEmailToSendBean bean) throws Exception {
		SubjectBean subject = SubjectUtil.subject.get();
		subject.setIdDominio(pAurigaLoginBean.getSchema());
		SubjectUtil.subject.set(subject);
		this.forcedelete(bean);
	}

	public OperRichEmailToSendBean get(OperRichEmailToSendBean bean) throws Exception {
		Session session = null;
		try {
			session = HibernateUtil.begin();

			String id = bean.getIdOperRichEmailToSend();

			TOperRichEmailToSend lTOperRichEmailToSend = (TOperRichEmailToSend) session.get(TOperRichEmailToSend.class, id);
			return (OperRichEmailToSendBean) UtilPopulate.populate(lTOperRichEmailToSend, OperRichEmailToSendBean.class,
					new TOperRichEmailToSendToOperRichEmailToSendBeanConverter(session));

		} catch (Exception e) {
			logger.error("Errore " + e.getMessage(), e);
			throw e;
		} finally {
			HibernateUtil.release(session);
		}
	}

	@Operation(name = "get")
	public OperRichEmailToSendBean getWithLogin(AurigaLoginBean pAurigaLoginBean, OperRichEmailToSendBean bean) throws Exception {
		SubjectBean subject = SubjectUtil.subject.get();
		subject.setIdDominio(pAurigaLoginBean.getSchema());
		SubjectUtil.subject.set(subject);
		return this.get(bean);
	}

	private Criteria buildHibernateCriteriaByFilter(Session session, TFilterFetch<OperRichEmailToSendBean> filter) throws Exception {

		Criteria criteria = session.createCriteria(TOperRichEmailToSend.class);
		if (filter != null) {
			OperRichEmailToSendBean bean = filter.getFilter();
			if (bean != null) {
				if (bean.getIdOperRichEmailToSend() != null && !bean.getIdOperRichEmailToSend().equals(""))
					criteria.add(Restrictions.eq("idOperRichEmailToSend", bean.getIdOperRichEmailToSend()));

			}
			HibernateUtil.addOrderCriteria(criteria, filter.getOrders());
		}
		return criteria;

	}

}
