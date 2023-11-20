/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import org.hibernate.Session;
import org.hibernate.Transaction;

public class HibernateTransactionManager {

	public static <T> T doInTransaction(Session session, HibernateTransactionCallback<T> tc) {
		T result = null;
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			result = tc.execute(session);
			tx.commit();
		} catch (RuntimeException e) {
			if (tx != null)
				tx.rollback();
			throw e;
		} finally {
			// la sessione è automaticamente chiusa al commit o al rollbacj usando la proprietà hibernate.current_session_context_class in hibernate.cfg.xml
			if (session.isOpen()) {
				session.close();
			}
		}
		return result;
	}
}
