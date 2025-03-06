package it.eng.utility.cryptosigner.storage.impl.filesystem;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.security.cert.CertificateEncodingException;
import java.security.cert.CertificateExpiredException;
import java.security.cert.CertificateFactory;
import java.security.cert.CertificateNotYetValidException;
import java.security.cert.X509CRL;
import java.security.cert.X509Certificate;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import javax.security.auth.x500.X500Principal;

import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.bouncycastle.asn1.x500.RDN;
import org.bouncycastle.asn1.x500.X500Name;
import org.bouncycastle.asn1.x500.style.BCStyle;
import org.bouncycastle.asn1.x500.style.IETFUtils;
import org.bouncycastle.cert.jcajce.JcaX509CertificateHolder;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

import it.eng.utility.cryptosigner.FactorySigner;
import it.eng.utility.cryptosigner.data.SignerUtil;
import it.eng.utility.cryptosigner.exception.CryptoSignerException;
import it.eng.utility.cryptosigner.exception.CryptoStorageException;
import it.eng.utility.cryptosigner.storage.ICAStorage;
import it.eng.utility.cryptosigner.storage.ICRLStorage;
import it.eng.utility.cryptosigner.utils.CrlUtility;

/**
 * Implementazione di uno storage di certificati basato su fileSystem
 * @author Michele Rigo
 *
 */
public class FileSystemCAStorage implements ICAStorage, Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	static Logger log = LogManager.getLogger(FileSystemCAStorage.class);
	
	private static final String CA_DIRECTORY = "CA_VALID_CERTIFICATE";
	private static final String FILE_CONFIG_DIRECTORY = "CONFIG";
	private static final String FILE_CONFIG_NAME = "Configuration";
		
	private List<String> serviceStatusList = new ArrayList<String>();
	private List<String> caTypeList = new ArrayList<String>();
	private List<String> tsaTypeList = new ArrayList<String>();
	
	/**
	 * Directory di salvataggio dei certificati
	 */
	private String directory;
	
	/**
	 * Recupera il riferimento alla directory di salvataggio dei certificati
	 * @return
	 */
	public String getDirectory() {
		return directory;
	}

	/**
	 * Definisce il riferimento alla directory di salvataggio dei certificati
	 * @param directory
	 */
	public void setDirectory(String directory) {
		this.directory = directory;
	}

	
	public void insertCA(X509Certificate certificate, String country, String serviceProviderName, String serviceName, String serviceStatus, String serviceType) throws CryptoStorageException {
		//log.info("insertCA START");
		//log.info("inserisco nello Store " + certificate.getSubjectDN());
		
		ICRLStorage crlStorage = FactorySigner.getInstanceCRLStorage();
		Date referenceDate = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
		SimpleDateFormat formatter1 = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		SignerUtil signerUtil = SignerUtil.newInstance();
		try{
			File dir = new File(directory+File.separator+CA_DIRECTORY);
			if(!dir.exists()){
				dir.mkdir();
			}
			
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
			File dirCert = new File(directory+File.separator+CA_DIRECTORY+ File.separator+country + File.separator + serviceProviderName.trim());
			if(!dirCert.exists()){
				//log.debug("Creo la directory " + dirCert);
				dir.mkdir();
			}
			
			//Creo la directory di configurazione
			File dirConfig = new File(dir+File.separator+FILE_CONFIG_DIRECTORY);
			if(!dirConfig.exists()){
				dirConfig.mkdir();
			}
						
			//Calcolo MD5 del soggetto
			//String fileName = DigestUtils.md5Hex(certificate.getEncoded());
			
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
			
			File file = new File(dirCert,/*fileName*/serviceName);
			//FileUtil.deleteFile(file);
			
			//Scrivo il nuovo file
			FileUtils.writeByteArrayToFile(file, certificate.getEncoded());
			
			//Inserisco la configurazione
			boolean active = true;
			try{
				log.debug("ServiceName: " + serviceName + " - ServiceProviderName: " + serviceProviderName + " - serviceStatus: " + serviceStatus);
				log.debug("Data attivazione certificato: " + formatter.format(certificate.getNotBefore()));
				log.debug("Data scadenza certificato: " + formatter.format(certificate.getNotAfter()));
				certificate.checkValidity();
				active = true;
				log.warn("Certificato in corso di validità!");
			}catch(CertificateExpiredException e){
				log.warn("Certificato scaduto!");
				active = false;
			}catch(CertificateNotYetValidException e){
				log.warn("Certificato non ancora valido!");
				active = false;
			}
			
			Date dataRevoca = null;
			if( active) {
				/// controllo la revoca
				// provo a vedere se nello storage ho la crl della ca emittente 
				X509CRL issuerCRL = null;
				try {
					issuerCRL = crlStorage.retriveCRL( certificate );
				} catch (CryptoStorageException e) {
					// Si e verificato un errore durante il recupero della CRL storicizzata
					log.error("CryptoStorageException", e);
	
				}
				boolean isQualified = true;
				if (issuerCRL != null && issuerCRL.getNextUpdate().after(referenceDate)) {
					log.debug("Data di prossimo aggiornamento della CRL " + formatter1.format(issuerCRL.getNextUpdate()));
					//log.debug("Verifico la crl storica");
					
					crlStorage.insertCRL(issuerCRL, country, serviceProviderName, serviceName );
					
					isQualified = CrlUtility.checkCRL( certificate, issuerCRL, referenceDate);
					log.debug("Esito verifica CRL " + isQualified);
					
					boolean certRevoked = issuerCRL.isRevoked(certificate);
					log.debug("Il certificato è revocato? " + certRevoked);
					
					if( certRevoked ){
						dataRevoca = CrlUtility.getDataRevoca(certificate, issuerCRL);
						active = false;
					}
				} else {
					log.debug("La CRL storica non e' stata trovata oppure il suo periodo di validite non e' applicabile");
					if (issuerCRL != null && issuerCRL.getNextUpdate().after(referenceDate)) {
						log.debug("Data di prossimo aggiornamento della CRL " + formatter1.format(issuerCRL.getNextUpdate()));
					}
					
					//log.debug(" cerco di scaricare la CRL");
					Vector<String> urlCRLDistributionPoints = null;
					try {
						urlCRLDistributionPoints = signerUtil.getURLCrlDistributionPoint(certificate);
					} catch (CryptoSignerException e) {
						// Non e stato possibile validare il certificato di certificazione rispetto alle CRL
						// tengo traccia dell'errore ma considero il certificato comunque accreditato
						// (poiche presente nella lista del CNIPA e attivo al riferimento temporale)
						//log.error("Errore nella determinazione dei punti di distribuzione della crl");
						//log.info("Scarico CRL END");
						//throw new CryptoSignerException(e.getMessage());
					} catch (Exception e) {
						// Non e stato possibile validare il certificato di certificazione rispetto alle CRL
						// tengo traccia dell'errore ma considero il certificato comunque accreditato
						// (poiche presente nella lista del CNIPA e attivo al riferimento temporale)
						log.error("Errore nella determinazione dei punti di distribuzione della crl");
						
						//log.info("Scarico CRL END");
						//throw new CryptoSignerException(e.getMessage());
					}
					
					if( urlCRLDistributionPoints!=null ) {
						log.info("Questo certificato ha: " + urlCRLDistributionPoints.size() + " punti di distribuzione");
					
						//int contaCRLScaricate = 0;
						//boolean ciclaCRL = true;
						boolean crlIndividuata = false;
						for (int k = 0; k < urlCRLDistributionPoints.size(); k++) {
							try {
								if (!crlIndividuata) {
									String urlCRLDistributionPoint = urlCRLDistributionPoints.get(k);
									log.info("Si esegue lo scarico della CRL per il seguente punto di distribuzione: " + urlCRLDistributionPoint);
	
									//X509CRL envelopeCrl = null;
									if (urlCRLDistributionPoint != null) {
										try {
											issuerCRL = signerUtil.getCrlByURL(urlCRLDistributionPoint);
										} catch (Exception e) {
											log.error("",e);
										}
										if (issuerCRL != null) {
											//contaCRLScaricate++;
											crlIndividuata = true;
											// La CRL deve essere storicizzata
											//log.debug("Storicizzo la crl");
											crlStorage.insertCRL(issuerCRL, country, serviceProviderName, serviceName );
													
											isQualified = CrlUtility.checkCRL( certificate, issuerCRL, referenceDate);
											log.debug("Esito verifica CRL " + isQualified);
													
											boolean certRevoked = issuerCRL.isRevoked( certificate);
											log.debug("Il certificato è revocato? " + certRevoked);
													
											if( certRevoked ) {
												dataRevoca = CrlUtility.getDataRevoca(certificate, issuerCRL);
												active = false;
											}
											
										} else {
											log.debug("scarico crl non riuscito");
											crlIndividuata = false;
										}
										
									}
									
								}
							} catch (Exception e) {
								// Non e stato possibile validare il certificato di certificazione rispetto alle CRL
								// tengo traccia dell'errore ma considero il certificato comunque accreditato
								// (poiche presente nella lista del CNIPA e attivo al riferimento temporale)
								log.error("",e);
								//log.info("Scarico CRL END");
							}
						}
						
						//log.info("Scarico CRL END");
					}
						
				}
			}
			
			
			updateConfig(certificate,active,file, serviceStatus, country, serviceProviderName, serviceName, serviceType, dataRevoca );
			//updateConfig(certificate,active,file);
			//log.info("insertCA END");
		}catch(Exception e){
			log.error("Errore inserimento/update Certificato di certificazione!",e);
			throw new CryptoStorageException("Errore inserimento/update Certificato di certificazione!",e);
		}
	}
	
	public void updateCA(X509Certificate certificate, String country, String serviceProviderName, String serviceName, String serviceStatus, String serviceType, 
			boolean active) throws CryptoStorageException {
		//log.info("insertCA START");
		//log.info("inserisco nello Store " + certificate.getSubjectDN());
		try{
			File dir = new File(directory+File.separator+CA_DIRECTORY);
			
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
			File dirCert = new File(directory+File.separator+CA_DIRECTORY+ File.separator+country + File.separator + serviceProviderName.trim());
			
			//Creo la directory di configurazione
			File dirConfig = new File(dir+File.separator+FILE_CONFIG_DIRECTORY);
						
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
			
			File file = new File(dirCert,/*fileName*/serviceName);
			
			updateConfig(certificate,active,file, serviceStatus, country, serviceProviderName, serviceName, serviceType, null );
		}catch(Exception e){
			log.error("Errore inserimento/update Certificato di certificazione!",e);
			throw new CryptoStorageException("Errore inserimento/update Certificato di certificazione!",e);
		}
	}

	
	public List<X509Certificate> retriveActiveCA() throws CryptoStorageException {
		//log.info("retriveActiveCA START");
		List<X509Certificate> activeCertificates = new ArrayList<X509Certificate>();
		try{
			//Recupero tutte le configurazioni del certificato
			//List<CABean> configs = getAllConfig();
			Map<String, CABean> configs = getAllConfig();
			CertificateFactory factorys = CertificateFactory.getInstance("X509", BouncyCastleProvider.PROVIDER_NAME);
			Iterator<String> subjectDnItr = configs.keySet().iterator(); 
			while( subjectDnItr.hasNext() ){
			//for(int i=0;i<configs.size();i++){
				//CABean bean = configs.get(i);
				String subjectDn = subjectDnItr.next();
				CABean bean = configs.get(subjectDn);
				//System.out.println("bean ca:"+bean.getSubjectDN()+"\n path:"+bean.getFilePath());
				if(bean.isActive()){
					activeCertificates.add((X509Certificate)factorys.generateCertificate(FileUtils.openInputStream(new File(bean.getFilePath()))));
				}
			}
		}catch(Exception e){
			log.error("Errore recupero certificati attivi!",e);
			throw new CryptoStorageException("Errore recupero certificati attivi!",e);
		}	
		//log.info("retriveActiveCA END");
		return activeCertificates;
	}
	
	public List<X509Certificate> retriveActiveCA_IT() throws CryptoStorageException {
		//log.info("retriveActiveCA START");
		List<X509Certificate> activeCertificates = new ArrayList<X509Certificate>();
		try{
			//Recupero tutte le configurazioni del certificato
			//List<CABean> configs = getAllConfig();
			Map<String, CABean> configs = getAllConfig();
			CertificateFactory factorys = CertificateFactory.getInstance("X509", BouncyCastleProvider.PROVIDER_NAME);
			Iterator<String> subjectDnItr = configs.keySet().iterator(); 
			while( subjectDnItr.hasNext() ){
			//for(int i=0;i<configs.size();i++){
				//CABean bean = configs.get(i);
				String subjectDn = subjectDnItr.next();
				CABean bean = configs.get(subjectDn);
				//System.out.println("bean ca:"+bean.getSubjectDN()+"\n path:"+bean.getFilePath());
				if(bean.isActive() && bean.getCountry()!=null && bean.getCountry().equalsIgnoreCase("IT")){
					activeCertificates.add((X509Certificate)factorys.generateCertificate(FileUtils.openInputStream(new File(bean.getFilePath()))));
				}
			}
		}catch(Exception e){
			log.error("Errore recupero certificati attivi!",e);
			throw new CryptoStorageException("Errore recupero certificati attivi!",e);
		}	
		//log.info("retriveActiveCA END");
		return activeCertificates;
	}

	
	public X509Certificate retriveCA(X509Certificate x509Certificato, boolean isSubject)  throws CryptoStorageException {
		//log.info("retriveCA START");
		X500Principal principal = null;
		if( isSubject)
			principal = x509Certificato.getSubjectX500Principal();
		else
			principal = x509Certificato.getIssuerX500Principal();
			
		String countryCert = getCountryCert(x509Certificato, isSubject);
		log.debug("countryCert " + countryCert);
		
		X509Certificate ret = null;
		CABean bean = getConfig(countryCert, principal);
		if(bean!=null){
			File file = new File(bean.getFilePath());
			//log.info("caFile " + file );
			if(file.exists()){
				try{
					CertificateFactory factorys = CertificateFactory.getInstance("X509", BouncyCastleProvider.PROVIDER_NAME);
					ret = (X509Certificate)factorys.generateCertificate(FileUtils.openInputStream(file));
					//log.info("Ret "+ ret);
				}catch(Exception e){
					log.error("Errore recupero certificato per X500Principal:"+principal.getName(),e);
					throw new CryptoStorageException(e);
				}
			}
		}
		//log.info("retriveCA END");
		return ret;
	}
	
	public CACertificate retriveValidCA(X509Certificate x509Certificato, boolean isSubject) throws CryptoStorageException {
		
		CACertificate caCertificateBean = new CACertificate();
		if( serviceStatusList==null ){
			log.error("ServiceStatusList non configurata, ignoro il controllo sulle CA valide");
			X509Certificate caCertificate = retriveCA(x509Certificato, isSubject);
			caCertificateBean.setCertificate(caCertificate);
			return caCertificateBean;
		}
		
		X500Principal principal = null;
		if( isSubject)
			principal = x509Certificato.getSubjectX500Principal();
		else
			principal = x509Certificato.getIssuerX500Principal();
		
		String countryCert = getCountryCert(x509Certificato, isSubject);
		log.debug("countryCert " + countryCert);
		
		log.debug("Recupero il certicato del principal:  " + principal);
		X509Certificate ret = null;
		CABean bean = getConfig(countryCert, principal);
		if(bean!=null ){
			log.debug( " stato: " + bean.getServiceStatus() );
			if( serviceStatusList.contains(bean.getServiceStatus()) ){
				File file = new File(bean.getFilePath());
				log.debug("Path del file del certificato della CA " + file.getAbsolutePath() + " stato: " + bean.getServiceStatus() + " tipo: " + bean.getServiceType());
				if(file.exists() && caTypeList.contains(bean.getServiceType())){
					try{
						CertificateFactory factorys = CertificateFactory.getInstance("X509", BouncyCastleProvider.PROVIDER_NAME);
						ret = (X509Certificate)factorys.generateCertificate(FileUtils.openInputStream(file));
						caCertificateBean.setCertificate(ret);
						caCertificateBean.setCountry( bean.getCountry() );
						caCertificateBean.setServiceProviderName( bean.getServiceProviderName() );
						caCertificateBean.setServiceName( bean.getServiceName() );
						caCertificateBean.setServiceStatus(  bean.getServiceStatus());
						caCertificateBean.setServiceType(  bean.getServiceType());
						caCertificateBean.setDataAttivazioneCertificato( bean.getDataAttivazioneCertificato() );
						caCertificateBean.setDataScadenzaCertificato( bean.getDataScadenzaCertificato() );
						caCertificateBean.setDataRevocaCertificato( bean.getDataRevocaCertificato() );
						
						//log.info("Ret "+ ret);
					} catch(Exception e){
						log.error("Errore recupero certificato per X500Principal:"+principal.getName(),e);
						throw new CryptoStorageException(e);
					}
				} else {
					log.debug("Il tipo di ca non corrisponde a quelli configurati");
				}
			}
		} else {
			log.error("CA non trovata o con stato non valido");
		}
		
		//log.info("retriveCA END");
		return caCertificateBean;
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
	
	public CACertificate retriveValidTSA(X509Certificate x509Certificato, boolean isSubject) throws CryptoStorageException {
		
		CACertificate caCertificateBean = new CACertificate();
		if( serviceStatusList==null ){
			log.error("ServiceStatusList non configurata, ignoro il controllo sulle TSA valide");
			X509Certificate caCertificate = retriveCA(x509Certificato, isSubject);
			caCertificateBean.setCertificate(caCertificate);
			return caCertificateBean;
		}
		
		X500Principal principal = null;
		if( isSubject)
			principal = x509Certificato.getSubjectX500Principal();
		else
			principal = x509Certificato.getIssuerX500Principal();
		
		String countryCert = getCountryCert(x509Certificato, isSubject);
		log.debug("countryCert " + countryCert);
		
		log.debug("Recupero il certicato del principal " + principal);
		X509Certificate ret = null;
		CABean bean = getConfig(countryCert, principal);
		if(bean!=null  ){
			log.debug( " stato: " + bean.getServiceStatus() );
			if( serviceStatusList.contains(bean.getServiceStatus() ) ){
				File file = new File(bean.getFilePath());
				log.debug("Path del file del certificato della TSA " + file.getAbsolutePath() + " stato: " + bean.getServiceStatus() + " tipo: " + bean.getServiceType());
				if(file.exists() && tsaTypeList.contains(bean.getServiceType())){
					try{
						CertificateFactory factorys = CertificateFactory.getInstance("X509", BouncyCastleProvider.PROVIDER_NAME);
						ret = (X509Certificate)factorys.generateCertificate(FileUtils.openInputStream(file));
						caCertificateBean.setCertificate(ret);
						caCertificateBean.setCountry( bean.getCountry() );
						caCertificateBean.setServiceProviderName( bean.getServiceProviderName() );
						caCertificateBean.setServiceName( bean.getServiceName() );
						//log.info("Ret "+ ret);
					}catch(Exception e){
						log.error("Errore recupero certificato per X500Principal:"+principal.getName(),e);
						throw new CryptoStorageException(e);
					}
				} else {
					log.debug("Il tipo di tsa non corrisponde a quelli configurati");
				}
			} else {
				log.debug("Stato non censito tra quelli validi");
			}
		} else {
			log.error("TSA non trovata o con stato non valido");
		}
		
		//log.info("retriveCA END");
		return caCertificateBean;
	}
	
	public void revokeCA(X509Certificate certificate) throws CryptoStorageException {
		//log.info("revokeCA START");
		updateConfig(certificate,false,null, null, null, null, null, null, null);
		//log.info("revokeCA END");
	}
	
	private CABean getConfig(String countryCert, X500Principal principal){
		//log.info("getConfig START");
		//Deserializzo il file per recuperare la lista delle configurazioni
		CABean bean = null;
		File file = new File(directory+File.separator+CA_DIRECTORY+File.separator+FILE_CONFIG_DIRECTORY+File.separator+FILE_CONFIG_NAME);
		//List<CABean> lista = new ArrayList<CABean>();
		Map<String, CABean> lista = new HashMap<String, CABean>();
		if(file.exists()){
			try{
				ObjectInputStream input = new ObjectInputStream(FileUtils.openInputStream(file)); 
				lista = (Map<String, CABean>)input.readObject();
				//lista = (List<CABean>)input.readObject();
				input.close();
			}catch(Exception e){
				log.warn("getConfig ",e);
				//lista = new ArrayList<CABean>();
				lista = new HashMap<String, CABean>();
			}
		}
		
		//log.info("principal.getName() " + principal.getName());
		//Ciclo le configurazioni
		log.info("lista size " + lista.size() );
		/*Iterator<String> chiaveItr = lista.keySet().iterator();
		while(chiaveItr.hasNext() ){
			String chiave = chiaveItr.next();
			log.info("Chiave " + chiave);
			CABean el = lista.get(chiave);
			if( el!=null)
			log.info("El "+ el.getServiceName() + " " + el.getServiceProviderName() + " " + el.getServiceType() + " " + el.getSubjectDN() );
			
		}*/
		//for(int i=0;i<lista.size();i++){
			//log.info("lista.get(i).getSubjectDN() " + lista.get(i).getSubjectDN());//+" principal.getName() " + principal.getName());
			//if(lista.get(i).getSubjectDN().equals(principal.getName())){
				//log.info("lista.containsKey(principal.getName()) " + lista.containsKey(principal.getName()));
				//bean = lista.get(principal.getName());
				log.info("Cerco la chiave nella mappa delle CA " + countryCert + "_" + principal.getName());
				bean = lista.get(countryCert + "_" + principal.getName());
				if( bean==null ){
					log.info("Cerco la chiave nella mappa delle CA " + principal.getName());
					bean = lista.get( principal.getName());
				}
				
				/*List<CABean> listaCA = getByEnd( lista, principal.getName());
				if( listaCA.size()==1){
					bean = listaCA.get(0);
				} else {
					bean = listaCA.get(0);
				}*/
					
				//break;
			//}
		//}
		//log.info("getConfig END");
		return bean;
	}
	private List<CABean> getByEnd( Map<String, CABean> map, String key_endString ) {
		List<CABean> listCa = new ArrayList<CABean>();
		Iterator<String> keyItr = map.keySet().iterator();
    	while( keyItr.hasNext()){
    		String key = keyItr.next();
    		if( key!=null && key.endsWith( key_endString ) ){
    			listCa.add( map.get(key));
    		}
    	}
    	return listCa;
    }
	
	//private List<CABean> getAllConfig(){
	private Map<String, CABean> getAllConfig(){
		//log.info("getAllConfig START");
		//Deserializzo il file per recuperare la lista delle configurazioni
		CABean bean = null;
		File file = new File(directory+File.separator+CA_DIRECTORY+File.separator+FILE_CONFIG_DIRECTORY+File.separator+FILE_CONFIG_NAME);
		//List<CABean> lista = new ArrayList<CABean>();
		Map<String, CABean> lista = new HashMap<String, CABean>();
		if(file.exists()){
			try{
				ObjectInputStream input = new ObjectInputStream(FileUtils.openInputStream(file)); 
				lista = (Map<String, CABean>)input.readObject();
				input.close();
			}catch(Exception e){
				log.warn("File non inizializzato, warning di lettura");
				//lista = new ArrayList<CABean>();
				lista = new HashMap<String, CABean>();
			}
		}
		//log.info("getAllConfig END");		
		return lista;
	}
	
	
	private void updateConfig(X509Certificate certificate,boolean  active,File fileCert, String serviceStatus, String country, String serviceProviderName, 
			String serviceName, String serviceType, Date dataRevoca){
		//log.info("updateConfig START");
		//Deserializzo il file per recuperare la lista delle configurazioni
		File file = new File(directory+File.separator+CA_DIRECTORY+File.separator+FILE_CONFIG_DIRECTORY+File.separator+FILE_CONFIG_NAME);
		//List<CABean> lista = new ArrayList<CABean>();
		Map<String, CABean> lista = new HashMap<String, CABean>();
		if(file.exists()){
			try{
				ObjectInputStream input = new ObjectInputStream(FileUtils.openInputStream(file)); 
				//lista = (List<CABean>)input.readObject();
				lista = (Map<String, CABean>)input.readObject();
				input.close();
			}catch(Exception e){
				log.warn("File non inizializzato, warning di lettura");
				lista = new HashMap<String, CABean>();
				//lista = new ArrayList<CABean>();
			}
		}
		
		boolean newfile = true;
		
		//Ciclo le configurazioni
		//for(int i=0;i<lista.size();i++){
//			if(lista.get(i).getSubjectDN().equals(certificate.getIssuerX500Principal().getName())){
			if(lista.get(country + "_" + certificate.getSubjectX500Principal().getName())!=null){
				newfile = false; 
				//CABean bean = lista.get(i);
				CABean bean = lista.get(country + "_" + certificate.getSubjectX500Principal().getName());
				bean.setActive(active);
				bean.setServiceStatus(serviceStatus);
				bean.setCountry(country);
				bean.setServiceName(serviceName);
				bean.setServiceType(serviceType);
				bean.setServiceProviderName(serviceProviderName);
				bean.setDataAttivazioneCertificato( certificate.getNotBefore());
				bean.setDataScadenzaCertificato(certificate.getNotAfter());
				if(fileCert!=null){
					bean.setFilePath(fileCert.getAbsolutePath());
				}
				if( dataRevoca!=null){
					bean.setDataRevocaCertificato( dataRevoca );
				}
				//lista.set(i, bean);
				lista.put(country + "_" + certificate.getSubjectX500Principal().getName(), bean);
				
			}
		//}
		if(newfile){
			CABean bean = new CABean();
			bean.setActive(active);
			bean.setFilePath(fileCert.getAbsolutePath());
			bean.setServiceStatus(serviceStatus);
			bean.setCountry(country);
			bean.setServiceProviderName(serviceProviderName);
			bean.setServiceName(serviceName);
			bean.setServiceType(serviceType);
//			bean.setSubjectDN(certificate.getIssuerX500Principal().getName());
			bean.setSubjectDN(certificate.getSubjectX500Principal().getName());
			lista.put(country + "_" + certificate.getSubjectX500Principal().getName(), bean);
			//lista.add(bean);
		}
		
//		int count= 0;
//		Iterator<String> k = lista.keySet().iterator();
//		while(k.hasNext()){
//			count++;
//			k.next();		
//		}
//		log.info("Numero chiavi " + count);
		
		try{
			//Serializzo la lista
			//log.debug("Serializzo la lista nel file " + file);
			FileOutputStream fileConfig = new FileOutputStream(file);
			ObjectOutputStream streamOut = new ObjectOutputStream(fileConfig);
			streamOut.writeObject(lista);
			streamOut.flush();
			streamOut.close();
		}catch(IOException e){
			log.warn("File non inizializzato, warning di scrittura");
		}
		//log.info("updateConfig END");
	}

	
	public boolean isActive(X509Certificate certificate)throws CryptoStorageException {
		//log.info("isActive START");
		//Deserializzo il file per recuperare la lista delle configurazioni
		File file = new File(directory+File.separator+CA_DIRECTORY+File.separator+FILE_CONFIG_DIRECTORY+File.separator+FILE_CONFIG_NAME);
		boolean active = false;
		if(file.exists()){
			try{
				ObjectInputStream input = new ObjectInputStream(FileUtils.openInputStream(file)); 
				//List<CABean> lista = (List<CABean>)input.readObject();
				Map<String, CABean> lista = (Map<String, CABean>)input.readObject();
				input.close();
				//Ciclo le configurazioni
				//for(int i=0;i<lista.size();i++){
					//if(lista.get(i).getSubjectDN().equals(certificate.getSubjectX500Principal().getName())){
					if(lista.get(certificate.getSubjectX500Principal().getName())!=null){
						//CABean bean = lista.get(i);
						CABean bean = lista.get(certificate.getSubjectX500Principal().getName());
						active = bean.isActive();
						//break;				
					} else {
						active = false;
					}
				//}
			}catch(Exception e){
				log.warn("Errore controllo dello stato del certificato");
			}
		}
		//log.info("isActive END");
		return active;
	}
	
	public List<String> getServiceStatusList() {
		return serviceStatusList;
	}

	public void setServiceStatusList(List<String> serviceStatusList) {
		this.serviceStatusList = serviceStatusList;
	}

	public List<String> getCaTypeList() {
		return caTypeList;
	}

	public void setCaTypeList(List<String> caTypeList) {
		this.caTypeList = caTypeList;
	}

	public List<String> getTsaTypeList() {
		return tsaTypeList;
	}

	public void setTsaTypeList(List<String> tsaTypeList) {
		this.tsaTypeList = tsaTypeList;
	}

	
	
}