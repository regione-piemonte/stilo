/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import it.eng.auriga.module.business.beans.AurigaLoginBean;
import it.eng.core.annotation.Operation;
import it.eng.core.annotation.Service;
import it.eng.core.business.HibernateUtil;
import it.eng.core.business.subject.SubjectBean;
import it.eng.core.business.subject.SubjectUtil;
import it.eng.document.function.bean.TestConcurrenceBean;

@Service(name = "ServiceForTestConcurrence")
public class ServiceForTestConcurrence {

	static SessionFactory sessionFactory;
	private static Logger log = Logger.getLogger(ServiceForTestConcurrence.class);
	/**
	 * E' una operation che non fa praticamente nulla.
	 * 
	 * Apre una connessione internamente in transazione e all'interno di
	 * essa apre un'altra sessione. Mi aspetto un blocco
	 * 
	 * @param pTestConcurrenceBean
	 * @return
	 * @throws Exception 
	 */
	@Operation(name = "testConcurrence")
	public TestConcurrenceBean testConcurrence(AurigaLoginBean pAurigaLoginBean, TestConcurrenceBean pTestConcurrenceBean) throws Exception{
		SubjectBean subject = SubjectUtil.subject.get();
		subject.setIdDominio(pAurigaLoginBean.getSchema());
		SubjectUtil.subject.set(subject);

		Session session = null;
		try {
//			session = buildSessionFactory().openSession();
			session = HibernateUtil.begin();
			Transaction lTransaction = session.beginTransaction();
			doSomething();
			lTransaction.commit();
		} catch (Exception e) {
			log.warn(e);
		} finally {
			HibernateUtil.release(session);
		}

		return new TestConcurrenceBean();
	}


	private void doSomething() throws Exception {
		log.debug("Test");
	}
}
