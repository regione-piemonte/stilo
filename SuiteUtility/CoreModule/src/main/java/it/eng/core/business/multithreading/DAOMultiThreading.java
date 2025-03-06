package it.eng.core.business.multithreading;

import it.eng.core.annotation.Operation;
import it.eng.core.annotation.Service;
import it.eng.core.business.HibernateUtil;
import it.eng.core.business.subject.SubjectUtil;

import java.util.UUID;

import org.hibernate.Session;

/**
 * DAO di gestione delle Session hibernate in modalità multithreading
 */
@Service(name="BusinessTransaction")
public class DAOMultiThreading {

	/**
	 * Effettua l'apertura di una nuova transazione con hibernate in modalità multithreading
	 * @return ritorna l'uuid associato alla transazione
	 * @throws Exception 
	 */
	@Operation(name="begin")
	public String begin() throws Exception{
		String uuidtransaction = SubjectUtil.subject.get().getUuidtransaction();
		if(uuidtransaction==null){
			Session session = HibernateUtil.openSession(SubjectUtil.subject.get().getIdDominio());
			String uuid = UUID.randomUUID().toString();
			HibernateUtil.addSession(uuid, session);
			return uuid;
		}else{
			throw new Exception("UUID della trabsazione già associato per la stessa business!");
		}
	}
	
	/**
	 * Effettua la commit della transazione multithreading passata in ingresso
	 * @param uuid
	 * @throws Exception 
	 */
	@Operation(name="commit")
	public void commit() throws Exception{
		Session session= null; 
		try {		
			session = HibernateUtil.begin();
			//In questo particolare caso forzo la commit
			session.getTransaction().commit();
		} catch (Exception e) {
			throw e;
		} finally {
			HibernateUtil.release(session);
		}
	}	
	
	/**
	 * Effettua una close della transazione passata in ingresso
	 * @param uuid
	 * @throws Exception 
	 */
	@Operation(name="close")
	public void close() throws Exception{
		Session session= null; 
		try {		
			session = HibernateUtil.begin();
			//In questo particolare caso forzo la close della session
			session.close();
		} catch (Exception e) {
			throw e;
		} finally {
			//Rimuovo la sessione dal singleton
			HibernateUtil.deleteSession();
		}
	}
	
	
	/**
	 * Metodo di template sull'utilizzo delle chiamate in transazione 
	 * Da utilizzare sempre
	 * @throws Exception
	 */
	public void example() throws Exception {		
		Session session= null; 
		try {		
			session = HibernateUtil.begin();
			
			//....work
			
//			HibernateUtil.commit(session);
		} catch (Exception e) {
			throw e;
		} finally {
			HibernateUtil.release(session);
		}	
	}
	
}
