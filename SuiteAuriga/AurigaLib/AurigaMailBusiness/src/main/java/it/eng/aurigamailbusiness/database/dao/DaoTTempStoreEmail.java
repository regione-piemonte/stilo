/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import it.eng.aurigamailbusiness.bean.TTempStoreEmailBean;
import it.eng.aurigamailbusiness.converters.TTempStoreEmailBeanToTTempStoreEmail;
import it.eng.aurigamailbusiness.converters.TTempStoreEmailToTTempStoreEmailBean;
import it.eng.aurigamailbusiness.database.mail.TTempStoreEmail;
import it.eng.core.annotation.Operation;
import it.eng.core.annotation.Service;
import it.eng.core.business.DaoGenericOperations;
import it.eng.core.business.HibernateUtil;
import it.eng.core.business.TFilterFetch;
import it.eng.core.business.TPagingList;
import it.eng.core.business.converter.UtilPopulate;

@Service(name = "DaoTTempStoreEmail")
public class DaoTTempStoreEmail extends DaoGenericOperations<TTempStoreEmailBean> {

	public DaoTTempStoreEmail() {
	}

	@Operation(name = "get")
	public TTempStoreEmailBean get(String idEmail) throws Exception {
		return null;
	}

	@Override
	@Operation(name = "save")
	public TTempStoreEmailBean save(TTempStoreEmailBean bean) throws Exception {
		Session session = null;
		Transaction transaction = null;
		try {
			session = HibernateUtil.begin();
			transaction = session.beginTransaction();
			TTempStoreEmail toInsert = (TTempStoreEmail) UtilPopulate.populate(bean, TTempStoreEmail.class,
					new TTempStoreEmailBeanToTTempStoreEmail());
			session.save(toInsert);
			session.flush();
			bean = (TTempStoreEmailBean) UtilPopulate.populate(toInsert, TTempStoreEmailBean.class,
					new TTempStoreEmailToTTempStoreEmailBean());
			transaction.commit();
			return bean;
		} catch (Exception e) {
			if (transaction != null && !transaction.wasRolledBack()) {
				transaction.rollback();
			}
			throw e;
		} finally {
			HibernateUtil.release(session);
		}

	}

	@Override
	@Operation(name = "search")
	public TPagingList<TTempStoreEmailBean> search(TFilterFetch<TTempStoreEmailBean> filter) throws Exception {
		return null;
	}

	public TPagingList<TTempStoreEmailBean> searchInSession(TFilterFetch<TTempStoreEmailBean> filter, Session session)
			throws Exception {
		return null;
	}

	@Override
	@Operation(name = "update")
	public TTempStoreEmailBean update(TTempStoreEmailBean bean) throws Exception {
		return null;
	}

	@Override
	@Operation(name = "delete")
	public void delete(TTempStoreEmailBean bean) throws Exception {
		Session session = null;
		Transaction transaction = null;
		try {
			session = HibernateUtil.begin();
			transaction = session.beginTransaction();
			Query select = session
					.createQuery("from TTempStoreEmail where messageId = :messageId and idCasella = :idCasella");
			select.setParameter("messageId", bean.getMessageId());
			select.setParameter("idCasella", bean.getIdCasella());
			TTempStoreEmail toDelete = (TTempStoreEmail) select.uniqueResult();
			if (toDelete != null) {
				session.delete(toDelete);
				session.flush();
			}
			transaction.commit();
		} catch (Exception e) {
			if (transaction != null && !transaction.wasRolledBack()) {
				transaction.rollback();
			}
			throw e;
		} finally {
			HibernateUtil.release(session);
		}

	}

	@Override
	@Operation(name = "forcedelete")
	public void forcedelete(TTempStoreEmailBean bean) throws Exception {
	}

}
