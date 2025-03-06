package it.eng.utility.cryptosigner.storage.impl.database;

import it.eng.utility.cryptosigner.exception.CryptoStorageException;
import it.eng.utility.cryptosigner.storage.ICAStorage;
import it.eng.utility.cryptosigner.storage.impl.database.hibernate.Certificate;
import it.eng.utility.cryptosigner.storage.impl.filesystem.CACertificate;

import java.io.ByteArrayInputStream;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.security.auth.x500.X500Principal;

import org.apache.log4j.Logger;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.hibernate.SQLQuery;

/**
 * Implementazione di uno storage di certificati basato su ORM Hibernate
 * @author Michele Rigo
 *
 */
public class DBCAStorage implements ICAStorage{

	Logger log = Logger.getLogger(DBCAStorage.class);

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

	public void insertCA(X509Certificate certificate, String country,
			String serviceProviderName, String serviceName, String serviceStatus, String serviceType)throws CryptoStorageException {
		log.info("insertCA START");
		FactoryConnection factory = FactoryConnection.newInstance(hibernate_conf);
		try{
			String subjectDN = certificate.getSubjectX500Principal().getName();
			Date data = certificate.getNotAfter();
			byte[] dati = certificate.getEncoded();
			boolean active = true;
			try{
				certificate.checkValidity();
				active = true;
			}catch(CertificateException e){
				active = false;
			}
			Certificate cert = new Certificate(subjectDN,data,dati,new Boolean(active));
			factory.beginTransaction();
			
			//Cancello il certificato se esiste
			SQLQuery query = factory.sessionFactory.createSQLQuery("delete from CERTIFICATE where subjectDN = ?");
			query.setString(0, subjectDN);
			query.executeUpdate();
			
			//Inserisco il nuovo certificato 		
			factory.sessionFactory.insert(cert);
			factory.commitTransaction();
		}catch(Exception e){
			factory.roolbackTransaction();
			log.error("insertCA ERROR", e);
			throw new CryptoStorageException(e); 
		}finally{
			factory.closeConnection();
		}
		log.info("insertCA END");
	}
	
	public void updateCA(X509Certificate certificate, String country,
			String serviceProviderName, String serviceName, String serviceStatus, String serviceType, boolean active)throws CryptoStorageException {
		
	}

	public List<X509Certificate> retriveActiveCA()throws CryptoStorageException {
		log.info("retriveActiveCA START");
		FactoryConnection factory = FactoryConnection.newInstance(hibernate_conf);
		List<X509Certificate> ret = new ArrayList<X509Certificate>();
		try{
			SQLQuery query = factory.sessionFactory.createSQLQuery("select * from CERTIFICATE where active = true").addEntity(Certificate.class);
			List<Certificate> certificati = query.list();
			if(certificati!=null){
				CertificateFactory factorys = CertificateFactory.getInstance("X509", BouncyCastleProvider.PROVIDER_NAME);
				for(int i=0;i<certificati.size();i++){
					if(certificati.get(i).getCertificate()!=null){
						X509Certificate certificate = (X509Certificate)factorys.generateCertificate(new ByteArrayInputStream(certificati.get(i).getCertificate()));
						if(certificate!=null){
							ret.add(certificate);						
						}
					}
				}
			}
		}catch(Exception e){
			log.error("retriveActiveCA ERROR", e);
			throw new CryptoStorageException(e); 
		}finally{
			factory.closeConnection();
		}
		log.info("retriveActiveCA END");
		return ret;
	}
	
	public List<X509Certificate> retriveActiveCA_IT()throws CryptoStorageException {
		log.info("retriveActiveCA START");
		FactoryConnection factory = FactoryConnection.newInstance(hibernate_conf);
		List<X509Certificate> ret = new ArrayList<X509Certificate>();
		try{
			SQLQuery query = factory.sessionFactory.createSQLQuery("select * from CERTIFICATE where active = true").addEntity(Certificate.class);
			List<Certificate> certificati = query.list();
			if(certificati!=null){
				CertificateFactory factorys = CertificateFactory.getInstance("X509", BouncyCastleProvider.PROVIDER_NAME);
				for(int i=0;i<certificati.size();i++){
					if(certificati.get(i).getCertificate()!=null){
						X509Certificate certificate = (X509Certificate)factorys.generateCertificate(new ByteArrayInputStream(certificati.get(i).getCertificate()));
						if(certificate!=null){
							ret.add(certificate);						
						}
					}
				}
			}
		}catch(Exception e){
			log.error("retriveActiveCA ERROR", e);
			throw new CryptoStorageException(e); 
		}finally{
			factory.closeConnection();
		}
		log.info("retriveActiveCA END");
		return ret;
	}

	public X509Certificate retriveCA(X509Certificate x509Certificato, boolean isSubject)	throws CryptoStorageException {
		log.info("retriveCA START");
		FactoryConnection factory = FactoryConnection.newInstance(hibernate_conf);
		X509Certificate ret = null;
		
		X500Principal principal = null;
		if( isSubject)
			principal = x509Certificato.getSubjectX500Principal();
		else
			principal = x509Certificato.getIssuerX500Principal();
		
		try{
			SQLQuery query = factory.sessionFactory.createSQLQuery("select * from CERTIFICATE where subjectDN = ?").addEntity(Certificate.class);;
			query.setString(0, principal.getName());
			Certificate certificato = (Certificate)query.uniqueResult();
			if(certificato!=null){
				CertificateFactory factorys = CertificateFactory.getInstance("X509", BouncyCastleProvider.PROVIDER_NAME);
				ret = (X509Certificate)factorys.generateCertificate(new ByteArrayInputStream(certificato.getCertificate()));
			}
		}catch(Exception e){
			log.error("retriveCA ERROR", e);
			throw new CryptoStorageException(e); 
		}finally{
			factory.closeConnection();
		}
		log.info("retriveCA END");
		return ret;	
	}
	
	public CACertificate retriveValidCA(X509Certificate x509Certificato, boolean isSubject) throws CryptoStorageException {
		CACertificate caCertBean = new CACertificate();
		X509Certificate cer = retriveCA(x509Certificato, isSubject);
		caCertBean.setCertificate(cer);
		return caCertBean;
	}
	
	public CACertificate retriveValidTSA(X509Certificate x509Certificato, boolean isSubject) throws CryptoStorageException {
		CACertificate caCertBean = new CACertificate();
		X509Certificate cer = retriveCA(x509Certificato, isSubject);
		caCertBean.setCertificate(cer);
		return caCertBean;
	}

	public void revokeCA(X509Certificate certificate)throws CryptoStorageException {
		log.info("revokeCA START");
		FactoryConnection factory = FactoryConnection.newInstance(hibernate_conf);
		try{
			SQLQuery query = factory.sessionFactory.createSQLQuery("update CERTIFICATE set active = false where subjectDN = ?");
			query.setString(0, certificate.getSubjectX500Principal().getName());
			int update = query.executeUpdate();
			if(update == 0){
				throw new CryptoStorageException("Nessun certificato trovato per effettuare la revoca! (SubjectDN:"+certificate.getSubjectX500Principal().getName()+")");
			}
		}catch(Exception e){
			log.error("revokeCA ERROR", e);
			throw new CryptoStorageException(e); 
		}finally{
			factory.closeConnection();
		}
		log.info("revokeCA END");
	}

	public boolean isActive(X509Certificate certificate)throws CryptoStorageException {
		log.info("isActive START");
		FactoryConnection factory = FactoryConnection.newInstance(hibernate_conf);
		boolean ret = false;
		try{
			SQLQuery query = factory.sessionFactory.createSQLQuery("select * from CERTIFICATE where subjectDN = ? and active = true").addEntity(Certificate.class);;
			query.setString(0, certificate.getSubjectX500Principal().getName());
			Certificate certificato = (Certificate)query.uniqueResult();
			if(certificato!=null){
				ret = true;
			}
		}catch(Exception e){
			log.error("isActive ERROR", e);
			throw new CryptoStorageException(e); 
		}finally{
			factory.closeConnection();
		}
		log.info("isActive END");
		return ret;	
	}
}