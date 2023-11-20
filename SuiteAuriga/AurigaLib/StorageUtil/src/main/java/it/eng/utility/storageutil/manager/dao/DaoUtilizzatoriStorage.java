/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.hibernate.FetchMode;
import org.hibernate.Session;
import org.hibernate.Transaction;

import it.eng.utility.storageutil.exception.DAOException;
import it.eng.utility.storageutil.manager.HibernateUtil;
import it.eng.utility.storageutil.manager.entity.UtilizzatoriStorage;
import it.eng.utility.storageutil.manager.entity.UtilizzatoriStorageH;
import it.eng.utility.storageutil.manager.entity.UtilizzatoriStorageHId;

/**
 * Classe DAO per la tabella di UTILIZZATORI_STORAGE
 * 
 * @author Mattia Zanin
 *
 */
public class DaoUtilizzatoriStorage {

	private static final Logger logger = Logger.getLogger(DaoUtilizzatoriStorage.class);

	@SuppressWarnings("unchecked")
	public List<UtilizzatoriStorage> getAll() throws DAOException {	
		Session session = null;		
		try {				
			session = HibernateUtil.getSessionFactory().openSession();
			
			return (List<UtilizzatoriStorage>) session.createCriteria(UtilizzatoriStorage.class)
					.setFetchMode("storages", FetchMode.JOIN)
					.list();
			
		}catch(Exception e){
			logger.error("", e);
			throw new DAOException(e);
		}finally{
			if(session != null) session.close();
		}		
	}

	/**
	 * Metodo che recupera l'utilizzatore dello storage tramite l'id passato in ingresso
	 * @param idUtilizzatore
	 * @throws DAOException
	 */
	public UtilizzatoriStorage get(String idUtilizzatore) throws DAOException {		
		Session session = null;		
		try {				
			session = HibernateUtil.getSessionFactory().openSession();

			UtilizzatoriStorage utilizzatoriStorage = null;
			if(StringUtils.isNotBlank(idUtilizzatore)) {
				utilizzatoriStorage = (UtilizzatoriStorage) session.get(UtilizzatoriStorage.class, idUtilizzatore);		
				if(utilizzatoriStorage != null) { 
					utilizzatoriStorage.getStorages();
				}
			} 

			return utilizzatoriStorage;
		}catch(Exception e){
			logger.error("", e);
			throw new DAOException(e);
		}finally{
			if(session != null) session.close();	
		}				
	}

	/**
	 * Metodo che salva l'utilizzatore dello storage passato in ingresso
	 * @param utilizzatoriStorage
	 * @throws DAOException
	 */
	public void save(UtilizzatoriStorage utilizzatoriStorage) throws DAOException {		
		Transaction transaction = null;
		Session session = null;		
		try {			
			session = HibernateUtil.getSessionFactory().openSession();
			transaction = session.getTransaction();
			transaction.begin();				

			if(utilizzatoriStorage != null && StringUtils.isNotBlank(utilizzatoriStorage.getIdUtilizzatore())) {
				session.save(utilizzatoriStorage);					
			}

			//Se tutto ok eseguo una commit della transazione
			transaction.commit();	
		}catch(Exception e){
			logger.error("", e);
			throw new DAOException(e);
		}finally{
			//Alla fine effettuo sempre una rollback sulla transazione, se non è stata committata
			if(transaction != null && !transaction.wasCommitted()) transaction.rollback();
			if(session != null) session.close();	
		}				
	}

	/**
	 * Metodo che aggiorna l'utilizzatore dello storage passato in ingresso
	 * @param utilizzatoriStorage
	 * @throws DAOException
	 */
	public void update(UtilizzatoriStorage utilizzatoriStorage) throws DAOException {		
		Transaction transaction = null;
		Session session = null;		
		try {			
			session = HibernateUtil.getSessionFactory().openSession();
			transaction = session.getTransaction();
			transaction.begin();			

			if(utilizzatoriStorage != null && StringUtils.isNotBlank(utilizzatoriStorage.getIdUtilizzatore())) {				

				String idStorage = (utilizzatoriStorage.getStorages() != null) ? utilizzatoriStorage.getStorages().getIdStorage() : null;
				UtilizzatoriStorage utilizzatoriStoragePrec = (UtilizzatoriStorage) this.get(utilizzatoriStorage.getIdUtilizzatore());	

				if(StringUtils.isNotBlank(idStorage) 
						&& utilizzatoriStoragePrec != null 
						&& !idStorage.equals(utilizzatoriStoragePrec.getStorages().getIdStorage())) 
				{
					UtilizzatoriStorageHId utilizzatoriStorageHId = new UtilizzatoriStorageHId();
					utilizzatoriStorageHId.setIdUtilizzatore(utilizzatoriStorage.getIdUtilizzatore());
					utilizzatoriStorageHId.setTsFinoAl(new Date());					
					UtilizzatoriStorageH utilizzatoriStorageH = new UtilizzatoriStorageH();
					utilizzatoriStorageH.setId(utilizzatoriStorageHId);
					utilizzatoriStorageH.setStorages(utilizzatoriStoragePrec.getStorages());
					// salvo nello storico il record dell'utilizzatore con lo storage precedente
					session.save(utilizzatoriStorageH);
					// aggiorno il record dell'utilizzatore con il nuovo storage
					session.update(utilizzatoriStorage);		
				}				
			}		

			//Se tutto ok eseguo una commit della transazione
			transaction.commit();	
		}catch(Exception e){
			logger.error("", e);
			throw new DAOException(e);
		}finally{
			//Alla fine effettuo sempre una rollback sulla transazione, se non è stata committata
			if(transaction != null && !transaction.wasCommitted()) transaction.rollback();
			if(session != null) session.close();	
		}						
	}			

	/**
	 * Metodo che elimina l'utilizzatore dello storage passato in ingresso
	 * @param utilizzatoriStorage
	 * @throws DAOException
	 */
	public void delete(UtilizzatoriStorage utilizzatoriStorage) throws DAOException {		
		Transaction transaction = null;
		Session session = null;		
		try {			
			session = HibernateUtil.getSessionFactory().openSession();
			transaction = session.getTransaction();
			transaction.begin();			

			if(utilizzatoriStorage != null && StringUtils.isNotBlank(utilizzatoriStorage.getIdUtilizzatore())) {
				UtilizzatoriStorage utilizzatoreStorage = this.get(utilizzatoriStorage.getIdUtilizzatore());	

				UtilizzatoriStorageHId utilizzatoreStorageHId = new UtilizzatoriStorageHId();
				utilizzatoreStorageHId.setIdUtilizzatore(utilizzatoreStorage.getIdUtilizzatore());
				utilizzatoreStorageHId.setTsFinoAl(new Date());					
				UtilizzatoriStorageH utilizzatoriStorageH = new UtilizzatoriStorageH();
				utilizzatoriStorageH.setId(utilizzatoreStorageHId);
				utilizzatoriStorageH.setStorages(utilizzatoreStorage.getStorages());
				// salvo nello storico il record dell'utilizzatore
				session.save(utilizzatoriStorageH);
				// cancello il record dell'utilizzatore
				session.delete(utilizzatoreStorage);	
			}

			//Se tutto ok eseguo una commit della transazione
			transaction.commit();	
		}catch(Exception e){
			logger.error("", e);
			throw new DAOException(e);
		}finally{
			//Alla fine effettuo sempre una rollback sulla transazione, se non è stata committata
			if(transaction != null && !transaction.wasCommitted()) transaction.rollback();
			if(session != null) session.close();	
		}				
	}

	/**
	 * Metodo statico che ritorna una nuova istanza della classe DAO
	 *
	 */
	public static DaoUtilizzatoriStorage newInstance() throws DAOException {		
		return new DaoUtilizzatoriStorage();
	}

}