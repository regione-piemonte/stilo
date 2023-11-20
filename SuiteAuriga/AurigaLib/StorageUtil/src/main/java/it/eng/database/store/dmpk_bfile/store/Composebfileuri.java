/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.database.store.dmpk_bfile.bean.DmpkBfileComposebfileuriBean;
import it.eng.database.store.dmpk_bfile.store.impl.ComposebfileuriImpl;
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
public class Composebfileuri {
		
	private DmpkBfileComposebfileuriBean bean;
		  
	public void setBean(DmpkBfileComposebfileuriBean bean){
		this.bean = bean;
	}
	
	public StorageStoreResultBean<DmpkBfileComposebfileuriBean> execute(DmpkBfileComposebfileuriBean pBean, HibernateStorageConfig pHibernateStorageConfig) throws StoredProcException{
		final ComposebfileuriImpl lComposebfileuri = new ComposebfileuriImpl();
	   	setBean(pBean);
	   	lComposebfileuri.setBean(bean);
       	setCommit();
//		Session session = HibernateSessionCreator.buildSessionFactory(pHibernateStorageConfig).openSession();
       	SessionFactory sessionFactory = new HibernateSessionCreator().buildSessionFactory(pHibernateStorageConfig);
       	Session session = sessionFactory.openSession();
		try {
			session.doWork(new Work() {
				@Override
				public void execute(Connection paramConnection) throws SQLException {
					paramConnection.setAutoCommit(false);
					lComposebfileuri.execute(paramConnection);
				}
			});
			StorageStoreResultBean<DmpkBfileComposebfileuriBean> result = new StorageStoreResultBean<>();
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