package it.eng.utility.cryptosigner.storage.impl.database;

import it.eng.utility.cryptosigner.bean.ConfigBean;
import it.eng.utility.cryptosigner.exception.CryptoStorageException;
import it.eng.utility.cryptosigner.storage.IConfigStorage;
import it.eng.utility.cryptosigner.storage.impl.database.hibernate.Config;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.SQLQuery;

/**
 * Implmentazione di uno storage di configurazioni basato su ORM Hibernate
 * @author Michele Rigo
 *
 */
public class DBConfigStorage implements IConfigStorage {

	Logger log = Logger.getLogger(DBConfigStorage.class);
	
	/**
	 * File di configurazione di hibernate
	 */
	private String hibernate_conf;
	
	/**
	 * Recupera il riferimento al file di configurazione di hibernate
	 * @return
	 */
	public String getHibernate_conf() {
		return hibernate_conf;
	}

	/**
	 * Definisce il riferimento al file di configurazione di hibernate
	 * @param hibernateConf
	 */
	public void setHibernate_conf(String hibernateConf) {
		hibernate_conf = hibernateConf;
	}
	
	
	public void deleteConfig(String subjectDN) throws CryptoStorageException {
		log.info("deleteConfig START");
		FactoryConnection factory = FactoryConnection.newInstance(hibernate_conf);
		try{
			factory.beginTransaction();
			//Cancello la configurazione se esiste
			SQLQuery query = factory.sessionFactory.createSQLQuery("delete from CONFIG where subjectDN = ?");
			query.setString(0, subjectDN);
			query.executeUpdate();
		}catch(Exception e){
			log.error("deleteConfig ERROR", e);
			throw new CryptoStorageException(e); 
		}finally{
			factory.closeConnection();
		}
		log.info("deleteConfig END");
		
	}

	
	public void insertConfig(ConfigBean config) throws CryptoStorageException {
		log.info("insertConfig START");
		FactoryConnection factory = FactoryConnection.newInstance(hibernate_conf);
		try{
			Config conf = new Config(config.getSubjectDN(), config.getCrlURL(), config.getSchedule());
			factory.beginTransaction();
			//Cancello la configurazione se esiste
			SQLQuery query = factory.sessionFactory.createSQLQuery("delete from CONFIG where subjectDN = ?");
			query.setString(0, config.getSubjectDN());
			query.executeUpdate();
			//Inserisco la nuova configurazione
			factory.sessionFactory.insert(conf);
			factory.commitTransaction();
		}catch(Exception e){
			factory.roolbackTransaction();
			log.error("insertConfig ERROR", e);
			throw new CryptoStorageException(e); 
		}finally{
			factory.closeConnection();
		}
		log.info("insertConfig END");
	}

	
	public List<ConfigBean> retriveAllConfig() throws CryptoStorageException {
		log.info("retriveAllConfig START");
		FactoryConnection factory = FactoryConnection.newInstance(hibernate_conf);
		List<ConfigBean> lista = new ArrayList<ConfigBean>();
		try{
			SQLQuery query = factory.sessionFactory.createSQLQuery("select * from CONFIG").addEntity(Config.class);
			List<Config> configurazioni = (List<Config>)query.list();
			if(configurazioni!=null){
				for(int i=0;i<configurazioni.size();i++){
					ConfigBean bean = new ConfigBean();
					bean.setCrlURL(configurazioni.get(i).getCrlurl());
					bean.setSchedule(configurazioni.get(i).getSchedule());
					bean.setSubjectDN(configurazioni.get(i).getSubjectdn());
				}
			}
		}catch(Exception e){
			log.error("retriveAllConfig ERROR", e);
			throw new CryptoStorageException(e); 
		}finally{
			factory.closeConnection();
		}
		log.info("retriveAllConfig END");
		return lista;
	}

	
	public ConfigBean retriveConfig(String subjectDN)throws CryptoStorageException {
		log.info("retriveConfig START");
		FactoryConnection factory = FactoryConnection.newInstance(hibernate_conf);
		ConfigBean bean = null;
		try{
			SQLQuery query = factory.sessionFactory.createSQLQuery("select * from CONFIG where subjectDN = ?").addEntity(Config.class);
			query.setString(0, subjectDN);
			Config configurazione = (Config)query.uniqueResult();
			if(configurazione!=null){
				bean = new ConfigBean();
				bean.setCrlURL(configurazione.getCrlurl());
				bean.setSchedule(configurazione.getSchedule());
				bean.setSubjectDN(configurazione.getSubjectdn());
			}
		}catch(Exception e){
			log.error("retriveConfig ERROR", e);
			throw new CryptoStorageException(e); 
		}finally{
			factory.closeConnection();
		}
		log.info("retriveConfig END");
		return bean;
	}
}