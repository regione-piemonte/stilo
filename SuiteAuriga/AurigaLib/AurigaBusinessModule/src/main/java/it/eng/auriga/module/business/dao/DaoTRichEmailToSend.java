/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.exception.SQLGrammarException;

import it.eng.auriga.module.business.beans.AurigaLoginBean;
import it.eng.auriga.module.business.dao.beans.RichEmailToSendBean;
import it.eng.auriga.module.business.dao.beansconverters.RichEmailToSendBeanToTRichEmailToSendConverter;
import it.eng.auriga.module.business.dao.beansconverters.TRichEmailToSendToRichEmailToSendBeanConverter;
import it.eng.auriga.module.business.entity.TRichEmailToSend;
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

@Service(name = "DaoTRichEmailToSend")
public class DaoTRichEmailToSend extends DaoGenericOperations<RichEmailToSendBean> {

	private static final Logger logger = Logger.getLogger(DaoTRichEmailToSend.class);

	public DaoTRichEmailToSend() {
	}

	@Override
	public RichEmailToSendBean save(RichEmailToSendBean bean) throws Exception {

		Session session = null;
		Transaction ltransaction = null;
		try {
			
			session = HibernateUtil.begin();
			ltransaction = session.beginTransaction();
			
			if (bean != null) {
				
				// imposta la chiave se non già impostata
			    if (bean.getIdRichiesta() == null || bean.getIdRichiesta().isEmpty()) {
			    	bean.setIdRichiesta(KeyGenerator.gen());
			    }
				
				TRichEmailToSend lTRichEmailToSend = (TRichEmailToSend) UtilPopulate.populate(bean, TRichEmailToSend.class,
						new RichEmailToSendBeanToTRichEmailToSendConverter(session));
				session.save(lTRichEmailToSend);
			}
			session.flush();
			ltransaction.commit();
			return bean;
			
		} catch(HibernateException e) {
			
			logger.error("Durante il salvataggio della richiesta si è verificata la seguente eccezione", e);
			
			RuntimeException ex = new RuntimeException(e.getCause().getMessage(), e);
			throw ex;
			
		} catch (Exception e) {
			
			logger.error("Durante il salvataggio della richiesta si è verificata la seguente eccezione", e);
			
			throw e;
		} finally {
			HibernateUtil.release(session);
		}

	}

	@Operation(name = "save")
	public RichEmailToSendBean saveWithLogin(AurigaLoginBean pAurigaLoginBean, RichEmailToSendBean bean) throws Exception {
		SubjectBean subject = SubjectUtil.subject.get();
		subject.setIdDominio(pAurigaLoginBean.getSchema());
		SubjectUtil.subject.set(subject);
		return this.save(bean);
	}

	@Override
	public TPagingList<RichEmailToSendBean> search(TFilterFetch<RichEmailToSendBean> filter) throws Exception {

		Session session = null;
		try {

			session = HibernateUtil.begin();

			Criteria criteria = this.buildHibernateCriteriaByFilter(session, filter);

			Long count = (Long) criteria.setProjection(Projections.rowCount()).uniqueResult();
			Integer startRow = (filter != null && filter.getStartRow() != null) ? filter.getStartRow() : 0;
			Integer endRow = (filter != null && filter.getEndRow() != null) ? filter.getEndRow() : startRow + count.intValue() - 1;

			// Creo l'oggetto paginatore
			TPagingList<RichEmailToSendBean> paginglist = new TPagingList<RichEmailToSendBean>();
			paginglist.setTotalRows(count.intValue());
			paginglist.setStartRow(startRow);
			paginglist.setEndRow(endRow);

			criteria.setProjection(null);
			criteria.setResultTransformer(Criteria.ROOT_ENTITY);
			for (Object obj : HibernateUtil.pagingByCriteria(criteria, filter.getStartRow(), filter.getEndRow())) {
				RichEmailToSendBean bean = (RichEmailToSendBean) UtilPopulate.populate((TRichEmailToSend) obj, RichEmailToSendBean.class,
						new TRichEmailToSendToRichEmailToSendBeanConverter(session));
				paginglist.addData(bean);
			}

			return paginglist;

		}
		catch(SQLGrammarException e) {
			logger.error(String.format("Durante l'esecuzione della query %1$s, si è verificata la seguente eccezione", e.getSQL()), e);
			throw e;
		}
		catch (Exception e) {
			logger.error("Errore " + e.getMessage(), e);
			throw e;
		} finally {
			HibernateUtil.release(session);
		}

	}

	@Operation(name = "search")
	public TPagingList<RichEmailToSendBean> searchWithLogin(AurigaLoginBean pAurigaLoginBean, TFilterFetch<RichEmailToSendBean> filter)
			throws Exception {
		SubjectBean subject = SubjectUtil.subject.get();
		subject.setIdDominio(pAurigaLoginBean.getSchema());
		SubjectUtil.subject.set(subject);
		return this.search(filter);
	}

	@Override
	public RichEmailToSendBean update(RichEmailToSendBean bean) throws Exception {
		Session session = null;
		Transaction ltransaction = null;
		try {
			session = HibernateUtil.begin();
			ltransaction = session.beginTransaction();
			if (bean != null) {
				TRichEmailToSend lTRichEmailToSend = (TRichEmailToSend) UtilPopulate.populateForUpdate(session, bean,
						TRichEmailToSend.class, new RichEmailToSendBeanToTRichEmailToSendConverter(session));
				session.update(lTRichEmailToSend);
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
	public RichEmailToSendBean updateWithLogin(AurigaLoginBean pAurigaLoginBean, RichEmailToSendBean bean) throws Exception {
		SubjectBean subject = SubjectUtil.subject.get();
		subject.setIdDominio(pAurigaLoginBean.getSchema());
		SubjectUtil.subject.set(subject);
		return this.update(bean);
	}

	@Override
	public void delete(RichEmailToSendBean bean) throws Exception {
		Session session = null;
		Transaction ltransaction = null;
		try {
			session = HibernateUtil.begin();
			ltransaction = session.beginTransaction();
			if (bean != null) {
				TRichEmailToSend lTRichEmailToSend = (TRichEmailToSend) UtilPopulate.populate(bean, TRichEmailToSend.class,
						new RichEmailToSendBeanToTRichEmailToSendConverter(session));
				session.delete(lTRichEmailToSend);
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
	public void deleteWithLogin(AurigaLoginBean pAurigaLoginBean, RichEmailToSendBean bean) throws Exception {
		SubjectBean subject = SubjectUtil.subject.get();
		subject.setIdDominio(pAurigaLoginBean.getSchema());
		SubjectUtil.subject.set(subject);
		this.delete(bean);
	}

	@Override
	public void forcedelete(RichEmailToSendBean bean) throws Exception {

		this.delete(bean);

	}

	@Operation(name = "forcedelete")
	public void forcedeleteWithLogin(AurigaLoginBean pAurigaLoginBean, RichEmailToSendBean bean) throws Exception {
		SubjectBean subject = SubjectUtil.subject.get();
		subject.setIdDominio(pAurigaLoginBean.getSchema());
		SubjectUtil.subject.set(subject);
		this.forcedelete(bean);
	}

	public RichEmailToSendBean get(RichEmailToSendBean bean) throws Exception {
		Session session = null;
		try {
			session = HibernateUtil.begin();

			String id = bean.getIdRichiesta();

			TRichEmailToSend lTRichEmailToSend = (TRichEmailToSend) session.get(TRichEmailToSend.class, id);
			return (RichEmailToSendBean) UtilPopulate.populate(lTRichEmailToSend, RichEmailToSendBean.class,
					new TRichEmailToSendToRichEmailToSendBeanConverter(session));

		} catch (Exception e) {
			logger.error("Errore " + e.getMessage(), e);
			throw e;
		} finally {
			HibernateUtil.release(session);
		}
	}

	@Operation(name = "get")
	public RichEmailToSendBean getWithLogin(AurigaLoginBean pAurigaLoginBean, RichEmailToSendBean bean) throws Exception {
		SubjectBean subject = SubjectUtil.subject.get();
		subject.setIdDominio(pAurigaLoginBean.getSchema());
		SubjectUtil.subject.set(subject);
		return this.get(bean);
	}

	private Criteria buildHibernateCriteriaByFilter(Session session, TFilterFetch<RichEmailToSendBean> filter) throws Exception {

		Criteria criteria = session.createCriteria(TRichEmailToSend.class);
		
		if (filter != null) {
			
			RichEmailToSendBean bean = filter.getFilter();
			
			if (bean != null) {
				
				if (bean.getIdRichiesta() != null && !bean.getIdRichiesta().isEmpty()) {
					criteria.add(Restrictions.eq("idRichiesta", bean.getIdRichiesta()));
				}

				if (bean.getFlgPec() != null) {
					criteria.add(Restrictions.eq("flgPec", bean.getFlgPec()));
				}
				
				if (bean.getCodSenderAppl() != null && !bean.getCodSenderAppl().isEmpty()) {
					criteria.add(Restrictions.eq("codSenderAppl", bean.getCodSenderAppl()));
				}
				
				if (bean.getDoctype() != null && !bean.getDoctype().isEmpty()) {
					criteria.add(Restrictions.eq("doctype", bean.getDoctype()));
				}
				
				if (bean.getIdEmail() != null && !bean.getIdEmail().isEmpty()) {
					criteria.add(Restrictions.eq("idEmail", bean.getIdEmail()));
				}
				
				if (bean.getIdSpAoo() != null) {
					criteria.add(Restrictions.eq("IdSpAoo", bean.getIdSpAoo()));
				}
				
				if (bean.getProvIdRichiesta() != null && !bean.getProvIdRichiesta().isEmpty()) {
					criteria.add(Restrictions.eq("provIdRichiesta", bean.getProvIdRichiesta()));
				}
				
				if (bean.getStato() != null && !bean.getStato().isEmpty()) {
					criteria.add(Restrictions.eq("stato", bean.getStato()));
				}
				
				if (bean.getTsIns() != null) {
					criteria.add(Restrictions.eq("tsIns", bean.getTsIns()));
				}
				
				if (bean.getTsUltimoAgg() != null) {
					criteria.add(Restrictions.eq("tsUltimoAgg", bean.getTsUltimoAgg()));
				}
				
				if (bean.getXmlRequest() != null && !bean.getXmlRequest().isEmpty()) {
					criteria.add(Restrictions.eq("xmlRequest", bean.getXmlRequest()));
				}
				
			}
			HibernateUtil.addOrderCriteria(criteria, filter.getOrders());
		}
		return criteria;

	}

}
