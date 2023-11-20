/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.core.business.HibernateUtil;

import java.sql.Connection;
import java.sql.SQLException;

import org.hibernate.Session;
import org.hibernate.jdbc.Work;

public class ConnectionWrapper {

	private Connection mConnection;
	private Session mSession;

	public void setConnection(Connection mConnection) {
		this.mConnection = mConnection;
	}

	public Connection getConnection() {
		return mConnection;
	}

	public void setSession(Session mSession) {
		this.mSession = mSession;
	}

	public Session getSession() {
		return mSession;
	}

	public void openConnection() throws Exception{
		Session session = null;
		try {
			session = HibernateUtil.begin();
			session.doWork(new Work() {
				@Override
				public void execute(Connection paramConnection) throws SQLException {
					mConnection = paramConnection;
				}
			});
		} catch(Exception e){
			throw e;
		}finally{
			HibernateUtil.release(session);
		}
	}


}
