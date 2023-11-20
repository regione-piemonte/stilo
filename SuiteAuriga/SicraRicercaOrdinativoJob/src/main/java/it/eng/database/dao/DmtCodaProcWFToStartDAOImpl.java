/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import it.eng.database.utility.HibernateUtil;
import it.eng.entity.ActHiActinst;
import it.eng.entity.DmtCodaProcWfToStart;
import it.eng.entity.DmtCodaProcWfToStartId;
import it.eng.entity.TCodaCopyFile;
import it.eng.job.exception.AurigaDAOException;

public class DmtCodaProcWFToStartDAOImpl {

	private static final Logger logger = Logger.getLogger(DmtCodaProcWFToStartDAOImpl.class.getName());

	public void saveAvanzamento(DmtCodaProcWfToStart bean,Session sessionJob) throws AurigaDAOException {
		Transaction transaction = null;
		
		try {
			logger.debug("saving: " + bean);
			transaction = sessionJob.beginTransaction();
			sessionJob.save(bean);
			sessionJob.flush();
			transaction.commit();
			logger.debug("saved...");
		} catch (Exception e) {
			logger.error("Errore in saveNotifica: ", e);
			if (transaction != null)
			{	
				transaction.rollback();
			}
			throw new AurigaDAOException(e);
		} 

	}
	public void updateAvanzamento(DmtCodaProcWfToStart bean,Session sessionJob) throws AurigaDAOException {
		Transaction transaction = null;
		
		try {
			logger.debug("saving: " + bean);
			transaction = sessionJob.beginTransaction();
			sessionJob.update(bean);
			sessionJob.flush();
			transaction.commit();
			logger.debug("saved...");
		} catch (Exception e) {
			logger.error("Errore in saveNotifica: ", e);
			if (transaction != null)
			{	
				transaction.rollback();
			}
			throw new AurigaDAOException(e);
		} 

	}
    
	public ActHiActinst getActHiActinst(String JOB_CLASS_NAME,String id)  {
		logger.debug("Effettuo la query sulla tabella ActHiActinst");
		
		ActHiActinst result = null;
		Session session = null;
		try {
			
			session = HibernateUtil.begin(JOB_CLASS_NAME);
			
			Criteria criteria = session.createCriteria(ActHiActinst.class)
					.add(Restrictions.eq("procInstId", id));
			criteria.setMaxResults(1);
			List<ActHiActinst> results = criteria.list();
			result = results.get(0);
		} catch (Exception e) {
			logger.error("Errore in ActHiActinst: ", e);
		} finally {
			HibernateUtil.closeSession(session);
		}
		return result;
	}
	
	public Map<String, List<DmtCodaProcWfToStart>> searchAvanzamento(int numTry, Session sessionJob) throws AurigaDAOException {

		
		List<DmtCodaProcWfToStart> listaOperazioniWf = new ArrayList<DmtCodaProcWfToStart>();
		List<DmtCodaProcWfToStart> listaOperazioniRel = new ArrayList<DmtCodaProcWfToStart>();
		Map<String, List<DmtCodaProcWfToStart>> littleMap = new HashMap<String, List<DmtCodaProcWfToStart>>();
		
		try {
			logger.debug("searchAvanzamento");

			
            String hqlStrWfOnly = " FROM DmtCodaProcWfToStart ct WHERE (ct.esitoElab is null or ct.esitoElab = 'KO') AND ct.tryNum < :numTry  "
					+ " ORDER BY ct.tsIns";


			Query queryWf = sessionJob.createQuery(hqlStrWfOnly);

			queryWf.setInteger("numTry", numTry);
			listaOperazioniWf = queryWf.list();
			logger.debug("listaOperazioni " + listaOperazioniWf.size());

			for (Iterator iterator = listaOperazioniWf.iterator(); iterator.hasNext();) {
				Object obj = iterator.next();
				logger.debug("obj " + obj);
				Object tCodaOperConWfObj = (Object) obj;
				listaOperazioniRel = new ArrayList<DmtCodaProcWfToStart>();
                listaOperazioniRel.add((DmtCodaProcWfToStart) tCodaOperConWfObj);
                littleMap.put(listaOperazioniRel.get(0).getId().getIdObj().toString(), listaOperazioniRel);
                
                DmtCodaProcWfToStart tp = (DmtCodaProcWfToStart) tCodaOperConWfObj;
                
                DmtCodaProcWfToStart ts = new DmtCodaProcWfToStart();
                ts = tp;
                ts.setEsitoElab("EL");
                ts.setTsStartProc(new Date());
				updateAvanzamento(ts,sessionJob);
                
			}

		} catch (Exception e) {
			logger.error("Errore in saveNotifica: ", e);
			throw new AurigaDAOException(e);
		} 
		if (littleMap != null)
			logger.info("listaOperazioni " + littleMap.size());
		return littleMap;
	}
public Map<String, List<DmtCodaProcWfToStart>> searchAvanzamentoEsito(String esito, Session sessionJob) throws AurigaDAOException {

		
		List<DmtCodaProcWfToStart> listaOperazioniWf = new ArrayList<DmtCodaProcWfToStart>();
		List<DmtCodaProcWfToStart> listaOperazioniRel = new ArrayList<DmtCodaProcWfToStart>();
		Map<String, List<DmtCodaProcWfToStart>> littleMap = new HashMap<String, List<DmtCodaProcWfToStart>>();
		
		try {
			logger.debug("searchAvanzamento");

			
            String hqlStrWfOnly = " FROM DmtCodaProcWfToStart ct WHERE ct.esitoElab = :esitoElab  "
					+ " AND (( trunc(sysdate,  'MI') - trunc(ct.tsStartProc,   'MI')) * 24*60) > 10 "
					+ " ORDER BY ct.tsIns";


			Query queryWf = sessionJob.createQuery(hqlStrWfOnly);

			queryWf.setString("esitoElab", esito);
			listaOperazioniWf = queryWf.list();
			logger.debug("listaOperazioni " + listaOperazioniWf.size());

			for (Iterator iterator = listaOperazioniWf.iterator(); iterator.hasNext();) {
				Object obj = iterator.next();
				logger.debug("obj " + obj);
				Object tCodaOperConWfObj = (Object) obj;
				listaOperazioniRel = new ArrayList<DmtCodaProcWfToStart>();
                listaOperazioniRel.add((DmtCodaProcWfToStart) tCodaOperConWfObj);
                littleMap.put(listaOperazioniRel.get(0).getId().getIdObj().toString(), listaOperazioniRel);
                
                DmtCodaProcWfToStart tp = (DmtCodaProcWfToStart) tCodaOperConWfObj;
                
                DmtCodaProcWfToStart ts = new DmtCodaProcWfToStart();
                ts = tp;
                ts.setEsitoElab("EL");
                ts.setTsStartProc(new Date());
                ts.setTryNum(new BigDecimal("0"));
				updateAvanzamento(ts,sessionJob);
                
			}

		} catch (Exception e) {
			logger.error("Errore in saveNotifica: ", e);
			throw new AurigaDAOException(e);
		} 
		if (littleMap != null)
			logger.info("listaOperazioni " + littleMap.size());
		return littleMap;
	}
	

}
