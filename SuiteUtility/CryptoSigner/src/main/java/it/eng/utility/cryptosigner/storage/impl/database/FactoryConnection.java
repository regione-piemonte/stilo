package it.eng.utility.cryptosigner.storage.impl.database;

import java.util.HashMap;

import org.apache.log4j.Logger;
import org.hibernate.SessionFactory;
import org.hibernate.StatelessSession;
import org.hibernate.cfg.Configuration;

/**
 * Implementa la logica di business per la creazione e gestione 
 * di una connessione all'ORM Hibernate
 * @author Michele Rigo
 *
 */
public class FactoryConnection {
	
	Logger log = Logger.getLogger(FactoryConnection.class);
		
	protected StatelessSession sessionFactory;
	
	private static HashMap<String,SessionFactory> sessionFactoryMap;
	
	
	private FactoryConnection() {
		
	}

	public void closeConnection(){
		sessionFactory.close();
	}
	
	public void beginTransaction(){
		sessionFactory.beginTransaction().begin();
	}
	
	public void roolbackTransaction(){
		sessionFactory.getTransaction().rollback();
	}
	
	public void commitTransaction(){
		sessionFactory.getTransaction().commit();
	}
	
	public static FactoryConnection newInstance(String config){
		FactoryConnection factory = new FactoryConnection();
		
		if(sessionFactoryMap==null){
			sessionFactoryMap = new HashMap<String, SessionFactory>();
		}
		
		if(!sessionFactoryMap.containsKey(config)){
			sessionFactoryMap.put(config, new Configuration().configure(config).buildSessionFactory());
		}		
		factory.sessionFactory = sessionFactoryMap.get(config).openStatelessSession();
				
		return factory;
	}
}