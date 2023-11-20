/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.Transaction;

import it.eng.auriga.module.business.dao.beans.TRichiestaProtocollazioneBean;
import it.eng.auriga.module.business.entity.TRichiestaProtocollazione;
import it.eng.core.annotation.Service;
import it.eng.core.business.DaoGenericOperations;
import it.eng.core.business.HibernateUtil;
import it.eng.core.business.TFilterFetch;
import it.eng.core.business.TPagingList;
import it.eng.core.business.converter.UtilPopulate;

@Service(name = "DaoTRichiestaProtocollazione")
public class DaoTRichiestaProtocollazione extends DaoGenericOperations<TRichiestaProtocollazioneBean> {
	
	private static final Logger logger = Logger.getLogger(DaoTRichiestaProtocollazione.class);

	@Override
	public TRichiestaProtocollazioneBean save(TRichiestaProtocollazioneBean bean) throws Exception {
		Session session = null;
		Transaction lTransaction = null;
		
		try {
			session = HibernateUtil.begin();
			lTransaction = session.beginTransaction();
			
			TRichiestaProtocollazione tRichiestaProtocollazione = (TRichiestaProtocollazione) UtilPopulate.populate(bean, TRichiestaProtocollazione.class);
			String id = (String) session.save(tRichiestaProtocollazione);
			session.flush();
			lTransaction.commit();
			
			bean.setIdRichiesta(id);
			
			return bean;
		} catch(Exception e) {
			logger.error("Errore " + e.getMessage(), e);
			throw e;
		} finally {
			HibernateUtil.release(session);
		}
	}

	@Override
	public TPagingList<TRichiestaProtocollazioneBean> search(TFilterFetch<TRichiestaProtocollazioneBean> filter)
			throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public TRichiestaProtocollazioneBean update(TRichiestaProtocollazioneBean bean) throws Exception {
		Session session = null;
		Transaction ltransaction = null;
		try {			
			session = HibernateUtil.begin();
			ltransaction = session.beginTransaction();
			if(bean != null) {					
				TRichiestaProtocollazione lTRichiestaProtocollazioneBean = (TRichiestaProtocollazione) UtilPopulate.populate(bean, TRichiestaProtocollazione.class, null);			
				session.update(lTRichiestaProtocollazioneBean);				
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
	public void delete(TRichiestaProtocollazioneBean bean) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void forcedelete(TRichiestaProtocollazioneBean bean) throws Exception {
		// TODO Auto-generated method stub
		
	}
	
}
