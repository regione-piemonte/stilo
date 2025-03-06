package it.eng.core.business.export.utility;

import it.eng.core.business.HibernateUtil;

import org.hibernate.Session;
import org.hibernate.Transaction;

public class ExportImportUtility {

	public static void exportData(ExportInfo info) throws Exception{
		Session session = null;
		 
		try {
			session = HibernateUtil.begin();
			Transaction transaction = session.beginTransaction();
			session.doWork(new ExportWork(info));
			transaction.commit();
			//HibernateUtil.commit(session);
		}catch(Exception e){
			throw e;
		}finally{
			HibernateUtil.release(session);
		}
	}
	
	public static void importData(ImportInfo info) throws Exception {
		Session session = null;
		try {
			session = HibernateUtil.begin();
			Transaction transaction = session.beginTransaction();
			session.doWork(  new ImportWork(info) ); 
			transaction.commit();
			//HibernateUtil.commit( session);

		}catch(Exception e){
			throw e;
		}finally{
			HibernateUtil.release(  session);
		}
	}
}
