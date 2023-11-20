/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.Transaction;

import it.eng.auriga.module.business.entity.WSTrace;
import it.eng.core.business.DaoGenericOperations;
import it.eng.core.business.HibernateUtil;
import it.eng.core.business.TFilterFetch;
import it.eng.core.business.TPagingList;

public class DaoWSTrace extends DaoGenericOperations<WSTrace>{

	private static final Logger logger = Logger.getLogger(DaoWSTrace.class);
	
	@Override
	public WSTrace save(WSTrace bean) throws Exception {

		Session session = null;     
		Transaction ltransaction = null;
		try {			
			session = HibernateUtil.begin();
			ltransaction = session.beginTransaction();
			if(bean != null) {					
				session.save(bean);				
			}							
			session.flush();
			ltransaction.commit();
			return bean;
		} catch(Exception e){
			logger.error("Errore " + e.getMessage(), e);
			throw e;
		}finally{
			HibernateUtil.release(session);   
		}		
	}

	@Override
	public TPagingList<WSTrace> search(TFilterFetch<WSTrace> filter) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public WSTrace update(WSTrace bean) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void delete(WSTrace bean) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void forcedelete(WSTrace bean) throws Exception {
		// TODO Auto-generated method stub
		
	}

	

	
	
}
