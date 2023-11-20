/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.Session;
import org.hibernate.Transaction;

import it.eng.utility.storageutil.exception.DAOException;
import it.eng.utility.storageutil.manager.HibernateUtil;
import it.eng.utility.storageutil.manager.entity.Storages;

/**
 * Classe DAO per la tabella STORAGES
 * 
 * @author Mattia Zanin
 *
 */
public class DaoStorages {

	private static final Logger logger = Logger.getLogger(DaoStorages.class);

	@SuppressWarnings("unchecked")
	public List<Storages> getAll() throws DAOException {
		Session session = null;		
		try {				
			session = HibernateUtil.getSessionFactory().openSession();
			
			return (List<Storages>) session.createCriteria(Storages.class)
					.setFetchMode("utilizzatoriStorages", FetchMode.JOIN)
					.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY)
					.list();
			
		}catch(Exception e){
			logger.error("", e);
			throw new DAOException(e);
		}finally{
			//Alla fine effettuo sempre una rollback sulla transazione, se non è stata committata
			if(session != null) session.close();	
		}		
	}
	
	/**
	 * Metodo che recupera lo storage tramite l'id passato in ingresso
	 * @param idStorage
	 * @throws Exception
	 */
	public Storages get(String idStorage) throws DAOException {		
		Transaction transaction = null;
		Session session = null;		
		try {				
			session = HibernateUtil.getSessionFactory().openSession();
			transaction = session.getTransaction();
			transaction.begin();				
			
			Storages storages = null;
			if(StringUtils.isNotBlank(idStorage)) {
				storages = (Storages) session.get(Storages.class, idStorage);					
			} 
			
			//Se tutto ok eseguo una commit della transazione
			transaction.commit();	
			
			return storages;
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
	 * Metodo che salva lo storage passato in ingresso
	 * @param storages
	 * @throws Exception
	 */
	public void save(Storages storages) throws DAOException {		
		Transaction transaction = null;
		Session session = null;		
		try {			
			session = HibernateUtil.getSessionFactory().openSession();
			transaction = session.getTransaction();
			transaction.begin();				
			
			if(storages != null && StringUtils.isNotBlank(storages.getIdStorage())) {
				session.save(storages);									
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
	 * Metodo che aggiorna lo storage passato in ingresso
	 * @param storages
	 * @throws Exception
	 */
	public void update(Storages storages) throws DAOException {		
		Transaction transaction = null;
		Session session = null;		
		try {			
			session = HibernateUtil.getSessionFactory().openSession();
			transaction = session.getTransaction();
			transaction.begin();			
			
			if(storages != null && StringUtils.isNotBlank(storages.getIdStorage())) {
				session.update(storages);
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
	 * Metodo che elimina lo storage passato in ingresso
	 * @param storages
	 * @throws Exception
	 */
	public void delete(Storages storage) throws DAOException {		
		Transaction transaction = null;
		Session session = null;		
		try {			
			session = HibernateUtil.getSessionFactory().openSession();
			transaction = session.getTransaction();
			transaction.begin();			
			
			if(storage != null && StringUtils.isNotBlank(storage.getIdStorage())) {
				Storages storage2del = (Storages) session.get(Storages.class, storage.getIdStorage());	
				//non cancello mai lo storage, solo logicamente
				storage2del.setFlgDisattivo(true);
				session.update(storage2del);				
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
	public static DaoStorages newInstance() {		
		return new DaoStorages();
	}
	
}