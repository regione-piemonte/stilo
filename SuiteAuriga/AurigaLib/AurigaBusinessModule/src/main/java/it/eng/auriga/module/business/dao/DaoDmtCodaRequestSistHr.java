/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.Transaction;

import it.eng.auriga.module.business.entity.DmtCodaRequestSistHr;
import it.eng.core.annotation.Service;
import it.eng.core.business.DaoGenericOperations;
import it.eng.core.business.HibernateUtil;
import it.eng.core.business.TFilterFetch;
import it.eng.core.business.TPagingList;

@Service(name = "DaoDmtCodaRequestSistHr")
public class DaoDmtCodaRequestSistHr extends DaoGenericOperations<DmtCodaRequestSistHr> {

	private static final Logger logger = Logger.getLogger(DaoDmtCodaRequestSistHr.class);

	public DaoDmtCodaRequestSistHr() {
	}

	@Override
	public DmtCodaRequestSistHr save(DmtCodaRequestSistHr bean) throws Exception {

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
			throw e;
		}finally{
			HibernateUtil.release(session);
		}		
	}

	@Override
	public TPagingList<DmtCodaRequestSistHr> search(TFilterFetch<DmtCodaRequestSistHr> filter) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DmtCodaRequestSistHr update(DmtCodaRequestSistHr bean) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void delete(DmtCodaRequestSistHr bean) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void forcedelete(DmtCodaRequestSistHr bean) throws Exception {
		// TODO Auto-generated method stub
		
	}

	
}
