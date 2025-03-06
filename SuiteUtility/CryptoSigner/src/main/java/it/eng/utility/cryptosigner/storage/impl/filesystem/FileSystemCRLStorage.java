package it.eng.utility.cryptosigner.storage.impl.filesystem;

import it.eng.utility.FileUtil;

import it.eng.utility.cryptosigner.exception.CryptoStorageException;
import it.eng.utility.cryptosigner.storage.ICRLStorage;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.security.cert.CertificateEncodingException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509CRL;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.security.auth.x500.X500Principal;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.bouncycastle.asn1.x500.RDN;
import org.bouncycastle.asn1.x500.X500Name;
import org.bouncycastle.asn1.x500.style.BCStyle;
import org.bouncycastle.asn1.x500.style.IETFUtils;
import org.bouncycastle.cert.jcajce.JcaX509CertificateHolder;
import org.bouncycastle.jce.X509Principal;


/**
 * Implmentazione di uno storage di CRL basato su file system
 * @author Michele Rigo
 *
 */
public class FileSystemCRLStorage implements ICRLStorage {

	static Logger log = LogManager.getLogger(FileSystemCRLStorage.class);
	
	private static final String CA_DIRECTORY = "CRL_LIST";
	private static final String FILE_CONFIG_DIRECTORY = "CONFIG";
	private static final String FILE_CONFIG_NAME = "Configuration";

	/**
	 * Directory di salvataggio dei certificati
	 */
	private String directory;
	
	
	public void insertCRL(X509CRL crl, String country, String serviceProviderName, String serviceName)throws CryptoStorageException {
		//log.info("insertCRL START");
		try{
			File dir = new File(directory+File.separator+CA_DIRECTORY);
			if(!dir.exists()){
				dir.mkdir();
			}
			
			if( serviceProviderName!=null ){
				if( serviceProviderName!=null && serviceProviderName.contains("/"))
					serviceProviderName = serviceProviderName.replace("/", "_");
				if( serviceProviderName!=null && serviceProviderName.contains("\\")){
					serviceProviderName = serviceProviderName.replace("\\\\", "_");
					serviceProviderName = serviceProviderName.replace("\\", "_");
				}
				if(serviceProviderName!=null && serviceProviderName.contains("*")){
					serviceProviderName = serviceProviderName.replace("\\*", "_");
					serviceProviderName = serviceProviderName.replace("*", "_");
				}
			}
			File dirCrl = new File(directory+File.separator+CA_DIRECTORY);
			if( serviceProviderName!=null  && country!=null ){
				dirCrl = new File(directory+File.separator+CA_DIRECTORY+ File.separator+country + File.separator + serviceProviderName.trim());
			}
			
			//Creo la directory di configurazione
			File dirConfig = new File(dir+File.separator+FILE_CONFIG_DIRECTORY);
			if(!dirConfig.exists()){
				dirConfig.mkdir();
			}
						
			//Calcolo MD5 del soggetto
			String fileName = DigestUtils.md5Hex(crl.getEncoded());
			//File file = new File(dirCrl,fileName);
			if( serviceName!=null && serviceName.contains("/"))
				serviceName = serviceName.replace("/", "_");
			if( serviceName!=null && serviceName.contains("*")){
				serviceName = serviceName.replace("\\*", "_");
				serviceName = serviceName.replace("*", "_");
			}
			if( serviceName!=null && serviceName.contains("\\")){
				serviceName = serviceName.replace("\\\\", "_");
				serviceName = serviceName.replace("\\", "_");
			}
			File file = null;
			if( serviceName!=null )
				file = new File(dirCrl,serviceName);
			else
				file = new File(dirCrl,fileName);
			//FileUtil.deleteFile(file);
			
			//Scrivo il nuovo file
			FileUtils.writeByteArrayToFile(file, crl.getEncoded());
			
			updateConfig(crl,file, country, serviceProviderName, serviceName );
		}catch(Exception e){
			log.error("Errore insertCRL!", e);
			throw new CryptoStorageException("Errore aggiunta Certificato",e);
		}	
		//log.info("insertCRL END");
	}

	
	public X509CRL retriveCRL(X509Certificate signatureCertificate) throws CryptoStorageException{
		//log.info("retriveCRL START");
		//Controllo se il certificato e' valido alla data attuale
		X509CRL crl = null;
		try{
			
			X500Principal issuerPrincipal = signatureCertificate.getIssuerX500Principal();
			String principalDN = issuerPrincipal.getName();
			
			String country = getCountryCert(signatureCertificate, false);
			log.debug("country " + country);
			
			//Recupero la configurazione per il certificato
			//log.debug("Cerco la CRL del subject " + subjectDN );
			CRLBean config = getConfig(principalDN, country);
			if(config!=null){
				String filePath = config.getFilePath();
				log.debug("FilePath della crl recuperata dallo storage: " + filePath);
				CertificateFactory cf = CertificateFactory.getInstance("X.509");
				crl = (X509CRL)cf.generateCRL(new FileInputStream(filePath));
			}else{
//				throw new CryptoStorageException("Errore recupero CRL!");
				//log.debug("CRL non trovata");
				return null;
			}
		}catch(Exception e){
			log.error("Errore retriveCRL!", e);
			throw new CryptoStorageException(e);
		}
		//log.info("retriveCRL END");
		return crl;
	}
	
	/**
	 * Recupera la directory di salvataggio dei certificati
	 * @return
	 */
	public String getDirectory() {
		return directory;
	}

	/**
	 * Definisce la directory di salvataggio dei certificati
	 * @param directory
	 */
	public void setDirectory(String directory) {
		this.directory = directory;
	}

	private CRLBean getConfig(String principalDN, String country){
		//log.info("getConfig START");
		//X509Principal subjectPrincipal = new X509Principal(subjectDN);
		//Deserializzo il file per recuperare la lista delle configurazioni
		CRLBean bean = null;
		File file = new File(directory+File.separator+CA_DIRECTORY+File.separator+FILE_CONFIG_DIRECTORY+File.separator+FILE_CONFIG_NAME);
		Map<CertBean, CRLBean> lista = new HashMap<CertBean, CRLBean>();
		if(file.exists()){
			try{
				ObjectInputStream input = new ObjectInputStream(FileUtils.openInputStream(file)); 
				lista = (Map<CertBean, CRLBean>)input.readObject();
				input.close();
			}catch(Exception e){
				log.warn("getConfig warning lettura file serializzato!",e);
				lista = new HashMap<CertBean, CRLBean>();
			}
		}
		//Ciclo le configurazioni
		//for(int i=0;i<lista.size();i++){
			
			//log.info("Cerco la chiave nella mappa delle CRL " + subjectDN );
				//X509Principal principal = new X509Principal(lista.get(i).getSubjectDN());
				//if (principal.equals(subjectPrincipal, false)){
				//bean = lista.get(i);
			CertBean subjectCertBean = CertBean.parse(principalDN, country);
			//log.debug("CertBean " + subjectCertBean);
			//log.info("lista.containsKey(subjectDN) " + lista.containsKey(subjectCertBean));
			log.debug("Cerco nelle config " + subjectCertBean );
			bean = lista.get(subjectCertBean);
				
			if( bean==null ){
				subjectCertBean = CertBean.parse(principalDN, null);
				log.debug("Cerco nelle config " + subjectCertBean );
				bean = lista.get(subjectCertBean);
			}
				//break;
			//}
		//}
		//log.info("getConfig END");
		return bean;
	}
	
	private void updateConfig(X509CRL crl,File fileCert, String country, String serviceProviderName, String serviceName){
		//log.info("updateConfig START");
		//Deserializzo il file per recuperare la lista delle configurazioni
		File file = new File(directory+File.separator+CA_DIRECTORY+File.separator+FILE_CONFIG_DIRECTORY+File.separator+FILE_CONFIG_NAME);
		Map<CertBean, CRLBean> lista = new HashMap<CertBean, CRLBean>();
		if(file.exists()){
			try{
				ObjectInputStream input = new ObjectInputStream(FileUtils.openInputStream(file)); 
				lista = (Map<CertBean, CRLBean>)input.readObject();
				input.close();
			}catch(Exception e){
				log.warn("updateConfig warning lettura file serializzato!");
				lista = new HashMap<CertBean, CRLBean>();
			}
		}
		
		boolean newfile = true;
		
		//Ciclo le configurazioni
		//for(int i=0;i<lista.size();i++){
			//if(lista.get(i).getSubjectDN().equals(crl.getIssuerX500Principal().getName())){
		
		CertBean issuerCertBean = CertBean.parse(crl.getIssuerX500Principal().getName(), country);
		//log.debug("CertBean " + issuerCertBean);
		
		if(lista.get(issuerCertBean)!=null ){
				newfile = false; 
				//CRLBean bean = lista.get(i);
				CRLBean bean = lista.get(issuerCertBean);
				bean.setCountry(country);
				bean.setServiceProviderName(serviceProviderName);
				if(fileCert!=null){
					bean.setFilePath(fileCert.getAbsolutePath());
				}
				//lista.set(i, bean);
				//log.debug("Inserisco la chiave " + crl.getIssuerX500Principal().getName() );
				lista.put(issuerCertBean, bean);
			}
		//}
		if(newfile){
			CRLBean bean = new CRLBean();
			bean.setFilePath(fileCert.getAbsolutePath());
			bean.setSubjectDN(crl.getIssuerX500Principal().getName());
			bean.setCountry(country);
			bean.setServiceProviderName(serviceProviderName);
			//log.debug("Inserisco la chiave " + crl.getIssuerX500Principal().getName() );
			lista.put(issuerCertBean, bean);
		}
		
		try{
			//Serializzo la lista
			FileOutputStream fileConfig = new FileOutputStream(file);
			ObjectOutputStream streamOut = new ObjectOutputStream(fileConfig);
			streamOut.writeObject(lista);
			streamOut.flush();
			streamOut.close();
		}catch(IOException e){
			log.warn("updateConfig warning scrittura file serializzato!",e);
		}
		//log.info("updateConfig END");
	}
	
	private String getCountryCert(X509Certificate x509Certificato, boolean isSubject){
		String cCert = null;
		X500Name x500name;
		try {
			if( isSubject ){
				x500name = new JcaX509CertificateHolder(x509Certificato).getSubject();
			} else {
				x500name = new JcaX509CertificateHolder(x509Certificato).getIssuer();
			}
			if (x500name != null) {
				RDN[] cs = x500name.getRDNs(BCStyle.C);
				if (cs != null && cs.length > 0) {
					RDN c = cs[0];
					if (c != null && c.getFirst() != null) {
						cCert = IETFUtils.valueToString(c.getFirst().getValue());
					}
				}
			}
		} catch (CertificateEncodingException e) {
			log.error("Errore nella lettura del subject del certificato ", e);
		}
		
		return cCert;
	}
}