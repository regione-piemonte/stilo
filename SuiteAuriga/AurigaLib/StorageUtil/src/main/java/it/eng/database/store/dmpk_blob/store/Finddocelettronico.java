/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.sql.Connection;
import java.sql.SQLException;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.jdbc.Work;

import it.eng.database.store.dmpk_blob.bean.DmpkBlobFinddocelettronicoBean;
import it.eng.database.store.dmpk_blob.store.impl.FinddocelettronicoImpl;
import it.eng.database.store.exception.StoredProcException;
import it.eng.database.store.result.bean.StorageStoreResultBean;
import it.eng.storeutil.HibernateSessionCreator;
import it.eng.storeutil.StorageAnalyzeResult;
import it.eng.utility.storageutil.HibernateStorageConfig;

/**
 * @author Procedure Wrapper 1.0
 * Classe generata per la chiamata alla store procedure 
 */
public class Finddocelettronico {
		
	private DmpkBlobFinddocelettronicoBean bean;
		  
	public void setBean(DmpkBlobFinddocelettronicoBean bean){
		this.bean = bean;
	}
	
	public StorageStoreResultBean<DmpkBlobFinddocelettronicoBean> execute(DmpkBlobFinddocelettronicoBean pBean, HibernateStorageConfig pBlobStorageConfig) throws StoredProcException{
		final FinddocelettronicoImpl lFinddocelettronico = new FinddocelettronicoImpl();
	   	setBean(pBean);
	   	lFinddocelettronico.setBean(bean);
       	setCommit();
//		Session session = HibernateSessionCreator.buildSessionFactory(pBlobStorageConfig).openSession();
       	SessionFactory sessionFactory = new HibernateSessionCreator().buildSessionFactory(pBlobStorageConfig);
       	Session session = sessionFactory.openSession();

		try {
			session.doWork(new Work() {
				@Override
				public void execute(Connection paramConnection) throws SQLException {
					paramConnection.setAutoCommit(false);
					lFinddocelettronico.execute(paramConnection);
				}
			});
			StorageStoreResultBean<DmpkBlobFinddocelettronicoBean> result = new StorageStoreResultBean<>();
			StorageAnalyzeResult.analyze(bean, result);
			result.setResultBean(bean);
			return result;
		}catch(Exception e){
			throw new StoredProcException(e);
		}finally{
			if (session != null){
				session.flush();
				session.close();
			}
			if (sessionFactory != null){
				sessionFactory.close();
			}
		}
		
	}
	
	protected void setCommit() {
		//NON UTILIZZATO
	}
}