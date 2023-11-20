/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.sql.Connection;
import java.sql.SQLException;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.jdbc.Work;

import it.eng.database.store.dmpk_bfile.bean.DmpkBfileGetrelativepathBean;
import it.eng.database.store.dmpk_bfile.store.impl.GetrelativepathImpl;
import it.eng.database.store.exception.StoredProcException;
import it.eng.database.store.result.bean.StorageStoreResultBean;
import it.eng.storeutil.HibernateSessionCreator;
import it.eng.storeutil.StorageAnalyzeResult;
import it.eng.utility.storageutil.HibernateStorageConfig;

/**
 * @author Procedure Wrapper 1.0
 * Classe generata per la chiamata alla store procedure 
 */
public class Getrelativepath {
		
	private DmpkBfileGetrelativepathBean bean;
		  
	public void setBean(DmpkBfileGetrelativepathBean bean){
		this.bean = bean;
	}
	
	public StorageStoreResultBean<DmpkBfileGetrelativepathBean> execute(DmpkBfileGetrelativepathBean pBean, HibernateStorageConfig pHibernateStorageConfig) throws StoredProcException {
		final GetrelativepathImpl lGetrelativepath = new GetrelativepathImpl();
	   	setBean(pBean);
	   	lGetrelativepath.setBean(bean);
       	setCommit();
//		Session session = HibernateSessionCreator.buildSessionFactory(pHibernateStorageConfig).openSession();
       	SessionFactory sessionFactory = new HibernateSessionCreator().buildSessionFactory(pHibernateStorageConfig);
       	Session session = sessionFactory.openSession();
		try {
			session.doWork(new Work() {
				@Override
				public void execute(Connection paramConnection) throws SQLException {
					paramConnection.setAutoCommit(false);
					lGetrelativepath.execute(paramConnection);
				}
			});
			StorageStoreResultBean<DmpkBfileGetrelativepathBean> result = new StorageStoreResultBean<DmpkBfileGetrelativepathBean>();
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