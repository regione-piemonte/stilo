/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import it.eng.entity.TCodaXExportTrasp;
import it.eng.job.exception.AurigaDAOException;

public class TCodaTrasparenzaDAOImpl {
	
	private static final Logger logger = Logger.getLogger(TCodaTrasparenzaDAOImpl.class.getName());
	
	public void saveTrasparenza(TCodaXExportTrasp bean, Session sessionJob) throws AurigaDAOException {
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
			if (transaction != null) {	
				transaction.rollback();
			}
			throw new AurigaDAOException(e);
		}
	}
	
	public void updateTrasparenza(TCodaXExportTrasp bean, Session sessionJob) throws AurigaDAOException {
		logger.debug("inizio metoddo updateTrasparenza");
		
		Transaction transaction = null;
		
		try {
			logger.debug("update: " + bean);
			transaction = sessionJob.beginTransaction();
			sessionJob.update(bean);
			sessionJob.flush();
			transaction.commit();
			logger.debug("dati aggiornati...");
		} catch (Exception e) {
			logger.error("Errore in updateTrasparenza: ", e);
			if (transaction != null) {	
				transaction.rollback();
			}
			throw new AurigaDAOException(e);
		}
		
		logger.debug("inizio metoddo updateTrasparenza");
	}
	
	public List<TCodaXExportTrasp> getCodaExportTrasparenza(String fruitore, int numTry, Session sessionJob) throws AurigaDAOException {
		logger.debug("inizio metoddo getCodaExportTrasparenza");
		
		List<TCodaXExportTrasp> codaExporTraspList = new ArrayList<TCodaXExportTrasp>();
		
		try {
            String sql = " FROM TCodaXExportTrasp cet WHERE (cet.esitoElab is null or cet.esitoElab = 'KO') AND cet.numTry < :numTry  "
					+ " AND cet.fruitore = '" + fruitore + "'";

			Query query = sessionJob.createQuery(sql);
			query.setInteger("numTry", numTry);
			//query.setString(fruitore, fruitore);
			
			codaExporTraspList = query.list();
			
			if (codaExporTraspList.size() > 0) {
				for (TCodaXExportTrasp item : codaExporTraspList) {
					TCodaXExportTrasp upd = new TCodaXExportTrasp();
					upd = item;
					upd.setEsitoElab("EL");
					upd.setTsLastTry(new Date());
					
					// aggiornamento stato record
					updateTrasparenza(upd, sessionJob);
					logger.debug("Record aggiornato con stato EL");
				}
			}
			
			logger.debug("lista record da elaborare " + codaExporTraspList.size());
		} catch (Exception e) {
			logger.error("Errore in saveNotifica: ", e);
			throw new AurigaDAOException(e);
		}
		
		logger.debug("fine metoddo getCodaExportTrasparenza");
		
		return codaExporTraspList;
	}
	
}
