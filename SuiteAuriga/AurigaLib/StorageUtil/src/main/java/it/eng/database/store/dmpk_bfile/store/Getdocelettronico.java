/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.database.store.dmpk_bfile.bean.DmpkBfileGetdocelettronicoBean;
import it.eng.database.store.dmpk_bfile.store.impl.GetdocelettronicoImpl;
import it.eng.database.store.exception.StoredProcException;
import it.eng.database.store.result.bean.StorageStoreResultBean;
import it.eng.storeutil.StorageAnalyzeResult;
import it.eng.utility.storageutil.HibernateStorageConfig;
import it.eng.storeutil.HibernateSessionCreator;

import java.sql.Connection;
import java.sql.SQLException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.jdbc.Work;

/**
 * @author Procedure Wrapper 1.0
 * Classe generata per la chiamata alla store procedure 
 */
public class Getdocelettronico {
		
	private DmpkBfileGetdocelettronicoBean bean;
		  
	public void setBean(DmpkBfileGetdocelettronicoBean bean){
		this.bean = bean;
	}
	
	public StorageStoreResultBean<DmpkBfileGetdocelettronicoBean> execute(DmpkBfileGetdocelettronicoBean pBean, HibernateStorageConfig pHibernateStorageConfig) throws StoredProcException {
		final GetdocelettronicoImpl lGetdocelettronico = new GetdocelettronicoImpl();
	   	setBean(pBean);
	   	lGetdocelettronico.setBean(bean);
       	setCommit();
	//		Session session = HibernateSessionCreator.buildSessionFactory(pHibernateStorageConfig).openSession();
	       	SessionFactory sessionFactory = new HibernateSessionCreator().buildSessionFactory(pHibernateStorageConfig);
	       	Session session = sessionFactory.openSession();
	   	try {
			session.doWork(new Work() {
				@Override
				public void execute(Connection paramConnection) throws SQLException {
					paramConnection.setAutoCommit(false);
					lGetdocelettronico.execute(paramConnection);
				}
			});
			StorageStoreResultBean<DmpkBfileGetdocelettronicoBean> result = new StorageStoreResultBean<DmpkBfileGetdocelettronicoBean>();
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