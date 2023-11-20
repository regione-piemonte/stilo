/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.LogicalExpression;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import it.eng.bean.NuovaPropostaAtto2CompletaBean;
import it.eng.database.utility.HibernateUtil;
import it.eng.entity.RegNum;
import it.eng.entity.TCodaOperConWf;
import it.eng.entity.TCodaXExport;
import it.eng.job.exception.AurigaDAOException;
import it.eng.utils.Contabilia;

public class TCodaOperConWfDAOImpl {

	private static final Logger logger = Logger.getLogger(TCodaOperConWfDAOImpl.class.getName());

	public void saveAvanzamento(TCodaOperConWf bean,Session sessionJob) throws AurigaDAOException {
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
	public void updateAvanzamento(TCodaOperConWf bean,Session sessionJob) throws AurigaDAOException {
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
	
public Map<String, List<TCodaOperConWf>> searchAvanzamentoVerifica(int numTry, Session sessionJob) throws AurigaDAOException {

		
		List<TCodaOperConWf> listaOperazioniWf = new ArrayList<TCodaOperConWf>();
		List<TCodaOperConWf> listaOperazioniRel = new ArrayList<TCodaOperConWf>();
		Map<String, List<TCodaOperConWf>> littleMap = new HashMap<String, List<TCodaOperConWf>>();
		
		try {
			logger.debug("searchAvanzamento");

			
            String hqlStrWfOnly = " FROM TCodaOperConWf ct WHERE (ct.esitoElab is null or ct.esitoElab = 'KO') AND ct.numTry < :numTry  "
					+ " AND ct.dipendeDaOper is null "
					+ " ORDER BY ct.tsIns,ct.idOperazione";


			Query queryWf = sessionJob.createQuery(hqlStrWfOnly);

			queryWf.setInteger("numTry", numTry);
			listaOperazioniWf = queryWf.list();
			logger.debug("listaOperazioni " + listaOperazioniWf.size());

			for (Iterator iterator = listaOperazioniWf.iterator(); iterator.hasNext();) {
				Object obj = iterator.next();
				logger.debug("obj " + obj);
				Object tCodaOperConWfObj = (Object) obj;
				listaOperazioniRel = new ArrayList<TCodaOperConWf>();
                listaOperazioniRel.add((TCodaOperConWf) tCodaOperConWfObj);
                littleMap.put(listaOperazioniRel.get(0).getIdOperazione(), listaOperazioniRel);
                
                
			}

		} catch (Exception e) {
			logger.error("Errore in saveNotifica: ", e);
			throw new AurigaDAOException(e);
		} 
		if (littleMap != null)
			logger.info("listaOperazioni " + littleMap.size());
		return littleMap;
	}

	public Map<String, List<TCodaOperConWf>> searchAvanzamento(int numTry, Session sessionJob) throws AurigaDAOException {

		
		List<TCodaOperConWf> listaOperazioniWf = new ArrayList<TCodaOperConWf>();
		List<TCodaOperConWf> listaOperazioniRel = new ArrayList<TCodaOperConWf>();
		Map<String, List<TCodaOperConWf>> littleMap = new HashMap<String, List<TCodaOperConWf>>();
		
		try {
			logger.debug("searchAvanzamento");

			
            String hqlStrWfOnly = " FROM TCodaOperConWf ct WHERE (ct.esitoElab is null or ct.esitoElab = 'KO') AND ct.numTry < :numTry  "
					+ " AND ct.dipendeDaOper is null "
					+ " ORDER BY ct.tsIns,ct.idOperazione";


			Query queryWf = sessionJob.createQuery(hqlStrWfOnly);

			queryWf.setInteger("numTry", numTry);
			queryWf.setMaxResults(10);
			listaOperazioniWf = queryWf.list();
			logger.debug("listaOperazioni " + listaOperazioniWf.size());

			for (Iterator iterator = listaOperazioniWf.iterator(); iterator.hasNext();) {
				Object obj = iterator.next();
				logger.debug("obj " + obj);
				Object tCodaOperConWfObj = (Object) obj;
				listaOperazioniRel = new ArrayList<TCodaOperConWf>();
                listaOperazioniRel.add((TCodaOperConWf) tCodaOperConWfObj);
                littleMap.put(listaOperazioniRel.get(0).getIdOperazione(), listaOperazioniRel);
                
                TCodaOperConWf tp = (TCodaOperConWf) tCodaOperConWfObj;
                
                TCodaOperConWf ts = new TCodaOperConWf();
                ts = tp;
                ts.setEsitoElab("EL");
                ts.setTsLastTry(new Date());
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
public Map<String, List<TCodaOperConWf>> searchAvanzamentoEsito(String esito, Session sessionJob) throws AurigaDAOException {

		
		List<TCodaOperConWf> listaOperazioniWf = new ArrayList<TCodaOperConWf>();
		List<TCodaOperConWf> listaOperazioniRel = new ArrayList<TCodaOperConWf>();
		Map<String, List<TCodaOperConWf>> littleMap = new HashMap<String, List<TCodaOperConWf>>();
		
		try {
			logger.debug("searchAvanzamento");

			
            String hqlStrWfOnly = " FROM TCodaOperConWf ct WHERE ct.esitoElab = :esitoElab  "
					+ " AND (( trunc(sysdate,  'MI') - trunc(tsLastTry,   'MI')) * 24*60) > 10 "
					+ " ORDER BY ct.tsIns,ct.idOperazione";


			Query queryWf = sessionJob.createQuery(hqlStrWfOnly);

			queryWf.setString("esitoElab", esito);
			queryWf.setMaxResults(10);
			listaOperazioniWf = queryWf.list();
			logger.debug("listaOperazioni " + listaOperazioniWf.size());

			for (Iterator iterator = listaOperazioniWf.iterator(); iterator.hasNext();) {
				Object obj = iterator.next();
				logger.debug("obj " + obj);
				Object tCodaOperConWfObj = (Object) obj;
				listaOperazioniRel = new ArrayList<TCodaOperConWf>();
                listaOperazioniRel.add((TCodaOperConWf) tCodaOperConWfObj);
                littleMap.put(listaOperazioniRel.get(0).getIdOperazione(), listaOperazioniRel);
                
                TCodaOperConWf tp = (TCodaOperConWf) tCodaOperConWfObj;
                
                TCodaOperConWf ts = new TCodaOperConWf();
                ts = tp;
                ts.setEsitoElab(null);
                ts.setTsLastTry(new Date());
                ts.setNumTry(new BigDecimal("0"));
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
public Map<String, List<TCodaOperConWf>> searchAvanzamentoEsitoVerifica(String esito, Session sessionJob) throws AurigaDAOException {

	
	List<TCodaOperConWf> listaOperazioniWf = new ArrayList<TCodaOperConWf>();
	List<TCodaOperConWf> listaOperazioniRel = new ArrayList<TCodaOperConWf>();
	Map<String, List<TCodaOperConWf>> littleMap = new HashMap<String, List<TCodaOperConWf>>();
	
	try {
		logger.debug("searchAvanzamento");

		
        String hqlStrWfOnly = " FROM TCodaOperConWf ct WHERE ct.esitoElab = :esitoElab  "
				+ " AND (( trunc(sysdate,  'MI') - trunc(tsLastTry,   'MI')) * 24*60) > 10 "
				+ " ORDER BY ct.tsIns,ct.idOperazione";


		Query queryWf = sessionJob.createQuery(hqlStrWfOnly);

		queryWf.setString("esitoElab", esito);
		listaOperazioniWf = queryWf.list();
		logger.debug("listaOperazioni " + listaOperazioniWf.size());

		for (Iterator iterator = listaOperazioniWf.iterator(); iterator.hasNext();) {
			Object obj = iterator.next();
			logger.debug("obj " + obj);
			Object tCodaOperConWfObj = (Object) obj;
			listaOperazioniRel = new ArrayList<TCodaOperConWf>();
            listaOperazioniRel.add((TCodaOperConWf) tCodaOperConWfObj);
            littleMap.put(listaOperazioniRel.get(0).getIdOperazione(), listaOperazioniRel);
            
            
		}

	} catch (Exception e) {
		logger.error("Errore in saveNotifica: ", e);
		throw new AurigaDAOException(e);
	} 
	if (littleMap != null)
		logger.info("listaOperazioni " + littleMap.size());
	return littleMap;
}
	public NuovaPropostaAtto2CompletaBean getRegistroContabilia(String idJobConf,NuovaPropostaAtto2CompletaBean lNuovaPropostaAtto2CompletaBean )  {
		logger.debug("Effettuo la query sulla tabella  table(U.REG_NUM)  ");
		
	    Session session = null;
		try {
			Contabilia cont = new Contabilia();
			
			session = HibernateUtil.begin(idJobConf);
			
			SQLQuery qryRegNum = session.createSQLQuery(" select "
			+" nt.sigla,nt.numero,nt.anno,nt.ts  "
			+" from dmt_unita_doc u, table(U.REG_NUM ) nt where U.ID_UD = :idUd ");
			
			qryRegNum.setBigDecimal("idUd", new BigDecimal(lNuovaPropostaAtto2CompletaBean.getIdUd()));
            
			
			logger.info("QUERY " + qryRegNum);
			
			List<Object> queryResult = (List<Object>) qryRegNum.list();
			Iterator itQryRes = queryResult.iterator();
			while (itQryRes.hasNext()) {
				Object[] obj = (Object[]) itQryRes.next();
				SimpleDateFormat textFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				String paramDateAsString = String.valueOf(obj[3]);
				
				RegNum reg= new RegNum(String.valueOf(obj[0]), 
						               new BigDecimal(String.valueOf(obj[1])),
						               new BigDecimal(String.valueOf(obj[2])), 
						               textFormat.parse(paramDateAsString));
				
				String sigla = cont.getTipoAttoContabiliaFromSiglaReg(reg.getSigla());
				
				logger.info("RegNum " + reg.toString());
				
				if (sigla.equals("PDCC") || sigla.equals("PDCS") || sigla.equals("PDD")  )
				{
					lNuovaPropostaAtto2CompletaBean.setSiglaRegProvvisoria(sigla);
					lNuovaPropostaAtto2CompletaBean.setNumeroRegProvvisoria(reg.getNumero().toString());
					lNuovaPropostaAtto2CompletaBean.setDataRegProvvisoria(reg.getTs());
					lNuovaPropostaAtto2CompletaBean.setAnnoRegProvvisoria(reg.getAnno().toString());
				}
				if (sigla.equals("DCC") || sigla.equals("DCS"))
				{
					lNuovaPropostaAtto2CompletaBean.setSiglaRegistrazione(sigla);
					lNuovaPropostaAtto2CompletaBean.setNumeroRegistrazione(reg.getNumero().toString());
					lNuovaPropostaAtto2CompletaBean.setDataRegistrazione(reg.getTs());
					lNuovaPropostaAtto2CompletaBean.setAnnoRegistrazione(reg.getAnno().toString());
				}
			}
			
		} catch (Exception e) {
			logger.error("Errore in table(U.REG_NUM): ", e);
		} finally {
			HibernateUtil.closeSession(session);
		}
		return lNuovaPropostaAtto2CompletaBean;
	}
	
	public List<TCodaXExport> getAllTCodaXExport(String JOB_CLASS_NAME,String esitoElab,String fruitore)  {
		logger.debug("Effettuo la query sulla tabella TCodaXExport.class");
		
		List<TCodaXExport> result = null;
		Session session = null;
		try {
			
			session = HibernateUtil.openSession(JOB_CLASS_NAME);
			Criteria criteria = session.createCriteria(TCodaXExport.class);
			Criterion esitoElabFalse = Restrictions.eq("esitoElab", esitoElab);
			Criterion esitoElabNull =  Restrictions.isNull("esitoElab");
	        LogicalExpression orExp = Restrictions.or(esitoElabFalse,esitoElabNull);
	        criteria.add(orExp);
			criteria.add(Restrictions.eq("fruitore", fruitore));
			criteria.addOrder(Order.asc("idOggetto"));

			result = criteria.list();
			
/*			 String hqlStrWfOnly = " FROM TCodaXExport ct WHERE ct.fruitore = :fruitore  "
						+ " AND (esitoElab is null OR esitoElab='KO' ) "
						+ " ORDER BY ct.idOggetto ";


				Query queryWf = session.createQuery(hqlStrWfOnly);

				queryWf.setString("fruitore", fruitore);
				result = queryWf.list();*/
			
		} catch (Exception e) {
			logger.error("Errore in getAllTCodaXExport(): ", e);
		} finally {
			HibernateUtil.closeSession(session);
		}
		return result;
	}
	

}
