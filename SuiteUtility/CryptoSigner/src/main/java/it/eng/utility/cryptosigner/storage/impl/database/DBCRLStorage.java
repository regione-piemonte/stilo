package it.eng.utility.cryptosigner.storage.impl.database;

import it.eng.utility.cryptosigner.exception.CryptoStorageException;
import it.eng.utility.cryptosigner.storage.ICRLStorage;
import it.eng.utility.cryptosigner.storage.impl.database.hibernate.Crl;

import java.io.ByteArrayInputStream;
import java.security.cert.CertificateFactory;
import java.security.cert.X509CRL;
import java.security.cert.X509Certificate;
import java.util.Date;

import org.apache.log4j.Logger;
import org.hibernate.SQLQuery;
/**
 * Implmentazione di uno storage di CRL basato su ORM Hibernate
 * @author Michele Rigo
 *
 */
public class DBCRLStorage implements ICRLStorage {

	Logger log = Logger.getLogger(DBCRLStorage.class);
	
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
	
	
	public void insertCRL(X509CRL crl, String country, String serviceProviderName, String serviceName) throws CryptoStorageException {
		log.info("insertCRL START");
		FactoryConnection factory = FactoryConnection.newInstance(hibernate_conf);
		try{
			String subjectDN = crl.getIssuerX500Principal().getName();
			Date data = crl.getThisUpdate();
			byte[] dati = crl.getEncoded();
			Crl crl_hibernate = new Crl(subjectDN,data,dati);
			factory.beginTransaction();
			
			//Cancello il certificato se esiste
			SQLQuery query = factory.sessionFactory.createSQLQuery("delete from CRL where subjectDN = ?");
			query.setString(0, subjectDN);
			query.executeUpdate();
			
			//Inserisco il nuovo certificato 		
			factory.sessionFactory.insert(crl_hibernate);
			factory.commitTransaction();
		}catch(Exception e){
			factory.roolbackTransaction();
			log.error("insertCRL ERROR", e);
			throw new CryptoStorageException(e); 
		}finally{
			factory.closeConnection();
		}
		log.info("insertCA END");		
	}

	
	public X509CRL retriveCRL(X509Certificate signatureCertificate) throws CryptoStorageException {
		log.info("retriveCRL START");
		FactoryConnection factory = FactoryConnection.newInstance(hibernate_conf);
		X509CRL ret = null;
		try{
			SQLQuery query = factory.sessionFactory.createSQLQuery("select * from CRL where subjectDN = ?").addEntity(Crl.class);
			query.setString(0, signatureCertificate.getSubjectDN().getName());
			Crl crl = (Crl)query.uniqueResult();
			if(crl!=null){
				CertificateFactory cf = CertificateFactory.getInstance("X.509");
				ret = (X509CRL)cf.generateCRL(new ByteArrayInputStream(crl.getCrl()));
			}
		}catch(Exception e){
			log.error("retriveCRL ERROR", e);
			throw new CryptoStorageException(e); 
		}finally{
			factory.closeConnection();
		}
		log.info("retriveCRL END");
		return ret;	
	}
}