package it.eng.utility.cryptosigner.ca.impl;

import it.eng.utility.cryptosigner.CryptoConfiguration;
import it.eng.utility.cryptosigner.CryptoSingleton;
import it.eng.utility.cryptosigner.FactorySigner;
import it.eng.utility.cryptosigner.ca.ICertificateAuthority;
import it.eng.utility.cryptosigner.controller.bean.ValidationInfos;
import it.eng.utility.cryptosigner.controller.impl.cert.CertMessage;
import it.eng.utility.cryptosigner.data.SignerUtil;
import it.eng.utility.cryptosigner.exception.CryptoSignerException;
import it.eng.utility.cryptosigner.exception.CryptoStorageException;
import it.eng.utility.cryptosigner.storage.ICAStorage;
import it.eng.utility.cryptosigner.storage.ICRLStorage;
import it.eng.utility.cryptosigner.storage.impl.filesystem.CABean;
import it.eng.utility.cryptosigner.storage.impl.filesystem.CACertificate;
import it.eng.utility.cryptosigner.utils.MessageHelper;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.Writer;
import java.security.Principal;
import java.security.cert.CertificateExpiredException;
import java.security.cert.CertificateNotYetValidException;
import java.security.cert.X509CRL;
import java.security.cert.X509CRLEntry;
import java.security.cert.X509Certificate;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import javax.security.auth.x500.X500Principal;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.commons.httpclient.Credentials;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpURL;
import org.apache.commons.httpclient.HttpsURL;
import org.apache.commons.httpclient.NTCredentials;
import org.apache.commons.httpclient.UsernamePasswordCredentials;
import org.apache.commons.httpclient.auth.AuthScope;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.bouncycastle.openssl.PEMWriter;
import org.joda.time.DateTime;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import be.fedict.eid.tsl.TrustService;
import be.fedict.eid.tsl.TrustServiceList;
import be.fedict.eid.tsl.TrustServiceListFactory;
import be.fedict.eid.tsl.TrustServiceProvider;
import be.fedict.eid.tsl.jaxb.tsl.OtherTSLPointerType;


/**
 *   effettua lo scaricamento
 * e il parsing della lista dei certificati attendibili a partire da una Trust Service Status List
 * e inserisce i certificati nello store
 *
 */
public class SimpleTSLCertificateAuthority implements ICertificateAuthority{
	
	private static final Logger log = LogManager.getLogger(SimpleTSLCertificateAuthority.class);
	
	private TSLDownloadPolicy policy;
	//TODO store on file !? private static final String LAST_CA_DOWNLOAD_TIME_FILE="lastCaDownloadTime.txt";
	
	private String timeout = null;

	private List<String> serviceStatusFilter;

	public void updateCertificate() throws CryptoSignerException {
		log.info("Metodo updateCertificate");
		//		Map<Principal, X509Certificate> qualifiedCertificates = new HashMap<Principal, X509Certificate>();
		try{
			CryptoConfiguration cryptoConfiguration = CryptoSingleton.getInstance().getConfiguration();
			//verifico se devo aggiornare la lista CA
			//log.debug("policy " + policy);
			if(policy==null || (policy!=null && policy.needDownload())  ){

				String urlString = cryptoConfiguration.getQualifiedCertificatesURL();
				log.info("url per lo scarico delle ca " + urlString);
				
				GetMethod method = new GetMethod(urlString);
				
				/*if (urlString.contains("https://")) {
					log.info("l'url delle CA che state invocando e' in https.");
				} else {
					log.info("l'url delle CA che state invocando e' in http.");
				}*/

				HttpClient httpclient = new HttpClient();
				
				try {
					log.debug("idProxy " + cryptoConfiguration.isProxy());
					log.debug("proxyHost " + cryptoConfiguration.getProxyHost());
					log.debug("proxyPort " + cryptoConfiguration.getProxyPort());
					log.debug("proxyUser " + cryptoConfiguration.getProxyUser());
					log.debug("proxyPwd ************ ");
					log.debug("dominio " + cryptoConfiguration.getDominio());
					log.debug("isNT " + cryptoConfiguration.getNTauth());
					log.debug("NT Host " + cryptoConfiguration.getNTHost());
					
				} catch(Exception e){
					throw new CryptoSignerException("Ci sono problemi nelle impostazioni del proxy con questo errore: ", e);
				}
				
				if (cryptoConfiguration.isProxy()){
					httpclient.getHostConfiguration().setProxy(cryptoConfiguration.getProxyHost(), cryptoConfiguration.getProxyPort());
					if( cryptoConfiguration.getNTauth()==null || !cryptoConfiguration.getNTauth() ) {
						//log.debug("siamo nella modalita' isNT " + cryptoConfiguration.getNTauth());
						httpclient.getState().setProxyCredentials(AuthScope.ANY,new UsernamePasswordCredentials(cryptoConfiguration.getProxyUser(), cryptoConfiguration.getProxyPassword()));
					} else {
						httpclient.getParams().setAuthenticationPreemptive(true);
						Credentials cred = new NTCredentials(cryptoConfiguration.getProxyUser(), cryptoConfiguration.getProxyPassword(),
								cryptoConfiguration.getNTHost(), cryptoConfiguration.getDominio());
						httpclient.getState().setProxyCredentials(AuthScope.ANY, cred);
					}
				}
				
				Document doc = null;
				
				try {

					//log.info(System.getProperties());
					//log.info("L'URL delle CA invocato e': " + method.getURI().toString());
					
					try {
		 	        	Integer timeoutInt = Integer.parseInt(timeout);
		 		        log.debug("Imposto il timeout a " + timeout +" msec");
		 	        	httpclient.getHttpConnectionManager().getParams().setConnectionTimeout(timeoutInt);
		 	        	httpclient.getHttpConnectionManager().getParams().setSoTimeout(timeoutInt);
		         	} catch(Exception e){
		         		log.error("Errore nel settaggio del timeout " + timeout + " - " + e.getMessage() );
		         	}
					
					httpclient.executeMethod(method);
					//log.info("abbiamo eseuito con successo il collegamento all'URL delle CA: httpclient.executeMethod(method)");
					
					java.io.InputStream is = method.getResponseBodyAsStream();
					//log.info("abbiamo recuperato con successo l'input stream delle CA: method.getResponseBodyAsStream()");
					
					DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
					//log.info("abbiamo istanziato la DocumentFactory: DocumentBuilderFactory.newInstance()");
					
					factory.setNamespaceAware(true);
					//log.info("abbiamo eseguito con successo questo metodo: factory.setNamespaceAware(true)");
					
					DocumentBuilder docBuilder = factory.newDocumentBuilder();
					//log.info("abbiamo eseguito con successo questo metodo: factory.newDocumentBuilder()");
					
					doc = docBuilder.parse(is);
					//log.info("abbiamo creato con successo un Document dall'Input Stream delle CA con questo metodo: docBuilder.parse(is)");
					
					method.releaseConnection();
					//log.info("rilasciamo con successo la connessione: method.releaseConnection()");
					
				} catch(Exception e){
					log.info("Si e' verificato un errore nell'invocare l'url delle CA con questo errore: " + e.getMessage());
					log.info("Un errore a questo punto impedisce lo scarico delle CA, che quindi non vengono scaricate.");
					throw new CryptoSignerException("Errore nello Scarico delle CA: ", e);
				}
				
				TrustServiceList trustServiceList = null;
				SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
				try {
					trustServiceList = TrustServiceListFactory.newInstance(doc);
					
					//List<String> uriList = trustServiceList.getSchemeInformationUris();
					//log.debug("uriList " + uriList);
					//Date issueDate = trustServiceList.getIssueDate();
					//log.debug("issueDate " + issueDate);
					DateTime nextUpdate = trustServiceList.getNextUpdate();
					log.debug("Data prossimo aggiornamento: " + formatter.format(nextUpdate.toDate()) );
				
					//trustServiceProviders = trustServiceList.getTrustServiceProviders();
				} catch(Exception e){
					throw new CryptoSignerException("Errore nel reperire la trustServiceList oppure il trustServiceProvider con questo errore: ", e);
				}
				//log.debug("trustServiceProviders " + trustServiceProviders);
				
				List<OtherTSLPointerType> otherTSLPointerList = trustServiceList.getOtherTSLPointers();
				if( otherTSLPointerList!=null && otherTSLPointerList.size()>0){
					for(OtherTSLPointerType otherTSLPointer : otherTSLPointerList){
						String tslLocation = otherTSLPointer.getTSLLocation();
						//log.debug("TSLLocation " + tslLocation);
						List<Object> otherInfosList = otherTSLPointer.getAdditionalInformation().getTextualInformationOrOtherInformation();
						String countryCode = null;
						for(Object otherInfos : otherInfosList){
							if(otherInfos instanceof be.fedict.eid.tsl.jaxb.tsl.AnyType){
								List<Object> contentList = ((be.fedict.eid.tsl.jaxb.tsl.AnyType)otherInfos).getContent();
								for(Object content : contentList){
									if( content instanceof javax.xml.bind.JAXBElement ){
										javax.xml.bind.JAXBElement element = (javax.xml.bind.JAXBElement)content;
										if( element!=null && element.getName()!=null && element.getName().getLocalPart()!=null &&
												element.getName().getLocalPart().equalsIgnoreCase("SchemeTerritory") ){
											countryCode = (String)element.getValue();
											//log.info("countryCode " + countryCode);
										}
									}
								}
							}
						}
						//log.debug("trustServiceList.getSchemeTerritory() " + );
						try {
							if( tslLocation!=null && tslLocation.endsWith("xml"))
								elaboraTSL(tslLocation, cryptoConfiguration, countryCode);
						} catch (Exception e) {
							log.error("Errore ", e);
						}
					}
				}
				
				List<TrustServiceProvider> trustServiceProviders = trustServiceList.getTrustServiceProviders();
				if( trustServiceProviders!=null )
					log.debug("Individuati " + trustServiceProviders.size() + " providers");
				
				for (TrustServiceProvider trustServiceProvider: trustServiceProviders){
					
					if( trustServiceProvider!=null ){
						//log.debug("trustServiceProvider name: " + trustServiceProvider.getName());
						
						List<TrustService> trustServices = trustServiceProvider.getTrustServices();
						//log.debug("trustServices " + trustServices);
						
						if( trustServices!=null ) {
							log.debug("Individuati " + trustServices.size() + " trust Services");
							for (TrustService trustService: trustServices){
								
								//log.debug("trustService name: " + trustService.getName() );
							
								//TODO check certificate is null !?
								X509Certificate certificate = trustService.getServiceDigitalIdentity();
								
								if( certificate!=null ){
									//dovresti prendere solo quelli attivi con status 
									//http://uri.etsi.org/TrstSvc/eSigDir-1999-93-EC-TrustedList/Svcstatus/accredited
									boolean statusFilterValid=serviceStatusFilter==null || serviceStatusFilter.size()==0;
									//log.debug(certificate.getSubjectDN() + "::: statusFilterValid " + statusFilterValid);
									
									//se non hai configurato il filtro tutti gli stati vanno bene
									if( statusFilterValid){
										//log.debug(certificate.getSubjectDN() + " " + trustService.getType() );
										try {
											FactorySigner.getInstanceCAStorage().insertCA(certificate, urlString,trustServiceProvider.getName(), trustService.getName(), trustService.getStatus(), trustService.getType());
										} catch(Exception e){
											throw new CryptoSignerException("Errore nell'inserire nella cartella delle CA a file sistem con questo errore: ", e);
										}
									} else {
										// hai configurato il filtro per cui verifica se lo stato del servizio è fra quelli ammessi
										//log.debug("trustService.getStatus() " + trustService.getStatus());
										//log.debug("trustService.getType() " + trustService.getType());
										//log.debug("trustService.getStatusStartingTime() " + trustService.getStatusStartingTime());
										statusFilterValid= serviceStatusFilter.contains(trustService.getStatus());
										
										//log.debug("statusFilterValid " + statusFilterValid);
										if(statusFilterValid){		 
											//log.info("Inserisco nello Store " + certificate.getSubjectDN());
											FactorySigner.getInstanceCAStorage().insertCA(certificate,urlString, trustServiceProvider.getName(), trustService.getName(), trustService.getStatus(), trustService.getType());
										} else{
											log.debug("skipping certificate "+ certificate.getSubjectDN());
										}
									}
						
								}
							}
						}
					}
				}
				//aggiorno la policy
				if(policy!=null){
					policy.downloadComplete(trustServiceList);
				}
			}else{
				log.debug("skipping update due to policy rule");
			}
		}catch(Exception e){
			throw new CryptoSignerException("Errore nel recupero dei certificati accreditati: ", e);
		}
	}


	private void elaboraTSL(String urlString, CryptoConfiguration cryptoConfiguration, String countryCode) 
		throws HttpException, IOException, ParserConfigurationException, SAXException, CryptoStorageException{
		log.info("Elaborazione CA della nazione con codice: " + countryCode + " - url: " + urlString);
		String url = null;
		if( urlString!=null && urlString.startsWith("http://")){
			HttpURL urlHttp = new HttpURL(urlString);
			url = urlHttp.toString();
		} else {
			HttpsURL urlHttps = new HttpsURL(urlString);	
			url = urlHttps.toString();
		}
		GetMethod method = new GetMethod(url);

		HttpClient httpclient = new HttpClient();
		if (cryptoConfiguration.isProxy()){
			httpclient.getHostConfiguration().setProxy(cryptoConfiguration.getProxyHost(), cryptoConfiguration.getProxyPort());
			//log.debug("isNT " + cryptoConfiguration.getNTauth());
			if( cryptoConfiguration.getNTauth()==null || !cryptoConfiguration.getNTauth() ) {
				//log.debug("User " + cryptoConfiguration.getProxyUser());
				//log.debug("Password " );
				httpclient.getState().setProxyCredentials(AuthScope.ANY,new UsernamePasswordCredentials(cryptoConfiguration.getProxyUser(), cryptoConfiguration.getProxyPassword()));
			} else {
				httpclient.getParams().setAuthenticationPreemptive(true);
				//log.debug("NT User " + cryptoConfiguration.getProxyUser());
				//log.debug("NT Password " );
				//log.debug("NT Host " + cryptoConfiguration.getNTHost());
				//log.debug("NT Dominio " + cryptoConfiguration.getDominio());
				Credentials cred = new NTCredentials(cryptoConfiguration.getProxyUser(), cryptoConfiguration.getProxyPassword(),
						cryptoConfiguration.getNTHost(), cryptoConfiguration.getDominio());
				httpclient.getState().setProxyCredentials(AuthScope.ANY, cred);
			}
		}
		
		try {
        	Integer timeoutInt = Integer.parseInt(timeout);
	        //log.debug("Imposto il timeout a " + timeout +" msec");
        	httpclient.getHttpConnectionManager().getParams().setConnectionTimeout(timeoutInt);
        	httpclient.getHttpConnectionManager().getParams().setSoTimeout(timeoutInt);
     	} catch(Exception e){
     		log.error("Errore nel settaggio del timeout " + timeout + " - " + e.getMessage() );
     	}
		httpclient.executeMethod(method);
		

		//java.io.InputStream is = method.getResponseBodyAsStream();
		byte[] body = method.getResponseBody();
		//log.debug("-------------> body " + body.length );

		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		factory.setNamespaceAware(true);
		DocumentBuilder docBuilder = factory.newDocumentBuilder();

		//Document doc =docBuilder.parse(is);
		InputStream bodyStream = new ByteArrayInputStream(body);
		Document doc =docBuilder.parse( bodyStream );
		bodyStream.close();

		method.releaseConnection();
		
		TrustServiceList trustServiceList = TrustServiceListFactory.newInstance(doc);
		
		List<TrustServiceProvider> trustServiceProviders = trustServiceList.getTrustServiceProviders();
		if( trustServiceProviders!=null )
			log.debug("Individuati " + trustServiceProviders.size() + " providers");
		//log.debug("trustServiceProviders " + trustServiceProviders);
		//si potrebbe ottimizzare conservando l'hash dei dati scaricati e se cambia allora inseriamo nello store!!
		for (TrustServiceProvider trustServiceProvider: trustServiceProviders){
			if( trustServiceProvider!=null ){
				log.debug("Name del ServiceProvider trust: " + trustServiceProvider.getName());
				//log.debug("trustServiceProvider TradeName: " + trustServiceProvider.getTradeName());
				List<TrustService> trustServices = trustServiceProvider.getTrustServices();
				if( trustServices!=null )
					log.debug("Individuati " + trustServices.size() + " trust Services");
				//log.debug("trustServices " + trustServices);
				for (TrustService trustService: trustServices){
					//log.debug("trustService name: " + trustService.getName() );
					//log.debug("trustService type: " + trustService.getType() );
					if( trustService.getType()!=null 
							&& !trustService.getType().equalsIgnoreCase("http://uri.etsi.org/TrstSvc/Svctype/unspecified") 
							&& !trustService.getType().equalsIgnoreCase("http://uri.etsi.org/TrstSvc/Svctype/IdV/nothavingPKIid")
							&& trustService.getServiceDigitalIdentity()!=null) {
						try{
							X509Certificate certificate = trustService.getServiceDigitalIdentity();
							//dovresti prendere solo quelli attivi con status 
							//http://uri.etsi.org/TrstSvc/eSigDir-1999-93-EC-TrustedList/Svcstatus/accredited
							//se non hai configurato il filtro tutti gli stati vanno bene
							//boolean statusFilterValid=serviceStatusFilter==null || serviceStatusFilter.size()==0;
							//log.debug("statusFilterValid " + statusFilterValid);
							if( certificate!=null ){
								boolean statusFilterValid=serviceStatusFilter==null || serviceStatusFilter.size()==0;
								//log.debug("statusFilterValid " + statusFilterValid);
								//se nn è configurato nulla
								if(statusFilterValid){
									//log.info("Inserisco nello Store " + certificate.getSubjectDN());
									//log.debug("trustService.getStatus() " + trustService.getStatus());
									//log.debug("trustService.getType() " + trustService.getType());
									//log.debug("trustService.getStatusStartingTime() " + trustService.getStatusStartingTime());
									
									if( countryCode!=null )
										FactorySigner.getInstanceCAStorage().insertCA(certificate, countryCode,trustServiceProvider.getName(), trustService.getName(),trustService.getStatus(), trustService.getType());
									else
										FactorySigner.getInstanceCAStorage().insertCA(certificate, urlString,trustServiceProvider.getName(), trustService.getName(), trustService.getStatus(), trustService.getType());
								} else {
									// hai configurato il filtro per cui verifica se lo stato del servizio è fra quelli ammessi
									//log.debug("trustService.getStatus() " + trustService.getStatus());
									//log.debug("trustService.getType() " + trustService.getType());
									//log.debug("trustService.getStatusStartingTime() " + trustService.getStatusStartingTime());
									statusFilterValid= serviceStatusFilter.contains(trustService.getStatus());
									//log.debug("statusFilterValid " + statusFilterValid);
									if(statusFilterValid){		 
										//log.info("Inserisco nello Store " + certificate.getSubjectDN());
										if( countryCode!=null )
											FactorySigner.getInstanceCAStorage().insertCA(certificate, countryCode, trustServiceProvider.getName(), trustService.getName(), trustService.getStatus(), trustService.getType());
										else
											FactorySigner.getInstanceCAStorage().insertCA(certificate, urlString, trustServiceProvider.getName(), trustService.getName(), trustService.getStatus(), trustService.getType());
									} else{
										log.debug("skipping certificate "+ certificate.getSubjectDN());
									}
								
								}
							}
						} catch (Throwable e) {
							log.error("Errore nel recupero del certificato", e);
						}
					} else {
						
					}
				}
			}
		}
	}

	public List<String> getServiceStatusFilter() {
		return serviceStatusFilter;
	}

	public void setServiceStatusFilter(List<String> serviceStatusFilter) {
		this.serviceStatusFilter = serviceStatusFilter;
	}



	public TSLDownloadPolicy getPolicy() {
		return policy;
	}



	public void setPolicy(TSLDownloadPolicy policy) {
		this.policy = policy;
	}



	private static void saveCertInFile(X509Certificate cert, Writer wr)throws Exception{
		PEMWriter pw = new PEMWriter(wr);
		pw.writeObject(cert);
		pw.flush();
	}

	public void revokeControll() throws CryptoSignerException {
		//throw new CryptoSignerException("not implemented");
		
		log.info("Metodo revokeControll");
		ICRLStorage crlStorage = FactorySigner.getInstanceCRLStorage();
		SignerUtil signerUtil = SignerUtil.newInstance();
		
		List<X509Certificate> activeCertificateList;
		try {
			activeCertificateList = FactorySigner.getInstanceCAStorage().retriveActiveCA_IT();
			
			Date referenceDate = new Date();
			if(activeCertificateList!=null){
				log.info("Ciclo sulle CA attive");
				
				ICAStorage certificatesAuthorityStorage= FactorySigner.getInstanceCAStorage();
				
				for(int i=0;i<activeCertificateList.size();i++){
					X509Certificate activeCertificate = activeCertificateList.get(i);
					
					String subjectCAName = activeCertificate.getSubjectDN().getName();
					log.info("Analisi CA del subject: " + subjectCAName);
					
					Principal issuerDN = activeCertificate.getIssuerDN();
					log.debug("Analisi CA emessa da: " + issuerDN.getName() );
					
					//X500Principal issuerPrincipal = activeCertificate.getIssuerX500Principal();
					
					// provo a vedere se nello storage ho la crl della ca emittente 
					X509CRL issuerCRL = null;
					try {
						issuerCRL = crlStorage.retriveCRL( activeCertificate );
					} catch (CryptoStorageException e) {
						// Si e verificato un errore durante il recupero della CRL storicizzata
						log.error("CryptoStorageException", e);

					}
					
					boolean isQualified = true;
					if (issuerCRL != null && issuerCRL.getNextUpdate().after(referenceDate)) {
						log.debug("data di prossimo aggiornamento della CRL " + issuerCRL.getNextUpdate());
						log.debug("Verifico la crl storica");
						
						X509Certificate qualifiedCertificate = null;
						String caCountry = null;
						String caServiceProviderName = null;
						String caServiceName = null;
						try {
							CACertificate caCertBean = certificatesAuthorityStorage.retriveValidCA(activeCertificate, true);
							qualifiedCertificate = caCertBean.getCertificate();
							if( qualifiedCertificate==null ) {
								
							} else {
								caCountry = caCertBean.getCountry();
								caServiceProviderName = caCertBean.getServiceProviderName();
								caServiceName = caCertBean.getServiceName();
								log.debug("caCountry " + caCountry + " caServiceProviderName " + caServiceProviderName+ " caServiceName " + caServiceName );
								
								// La CRL deve essere storicizzata
								log.debug("Storicizzo la crl");
								crlStorage.insertCRL(issuerCRL, caCountry, caServiceProviderName, caServiceName );
								
								isQualified = checkCRL( activeCertificate, issuerCRL, referenceDate);
								log.debug("Verifica CRL " + isQualified);
								
								boolean certRevoked = issuerCRL.isRevoked(activeCertificate);
								log.debug("Verifica CRL certRevoked " + certRevoked);
								
								if( certRevoked ) {
									log.debug("Revoco il certificato ");
									certificatesAuthorityStorage.updateCA(qualifiedCertificate, caCountry, caServiceProviderName, caServiceName, caCertBean.getServiceStatus(), caCertBean.getServiceType(), false);
								}
							}
						} catch (CryptoStorageException e) {
							log.error("Errore nell'insermento nella cartella locale della CRL: " + e.getMessage());
						}
						
					} else {
						log.debug("La CRL storica non e' stato trovata oppure il suo periodo di validite non e' applicabile");
						if (issuerCRL != null && issuerCRL.getNextUpdate().after(referenceDate)) {
							log.debug("data di prossimo aggiornamento della CRL " + issuerCRL.getNextUpdate());
						}
						
						log.debug(" cerco di scaricare la CRL");
						Vector<String> urlCRLDistributionPoints = null;
						try {
							urlCRLDistributionPoints = signerUtil.getURLCrlDistributionPoint(activeCertificate);
						} catch (CryptoSignerException e) {
							// Non e stato possibile validare il certificato di certificazione rispetto alle CRL
							// tengo traccia dell'errore ma considero il certificato comunque accreditato
							// (poiche presente nella lista del CNIPA e attivo al riferimento temporale)
							log.error("Errore nella determinazione dei punti di distribuzione della crl");
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
							for (int k = 0; k < urlCRLDistributionPoints.size(); k++) {
								boolean ciclaCRL = true;
								try {
									while (ciclaCRL) {
										String urlCRLDistributionPoint = urlCRLDistributionPoints.get(k);
										log.info("Si esegue lo scarico della CRL per il seguente punto di distribuzione: " + urlCRLDistributionPoint);

										//X509CRL envelopeCrl = null;
										if (urlCRLDistributionPoint != null) {
											issuerCRL = signerUtil.getCrlByURL(urlCRLDistributionPoint);

											if (issuerCRL != null) {
												//contaCRLScaricate++;
												
												X509Certificate qualifiedCertificate = null;
												String caCountry = null;
												String caServiceProviderName = null;
												String caServiceName = null;
												try {
													CACertificate caCertBean = certificatesAuthorityStorage.retriveValidCA(activeCertificate, true);
													qualifiedCertificate = caCertBean.getCertificate();
													if( qualifiedCertificate==null ) {
														
													} else {
														caCountry = caCertBean.getCountry();
														caServiceProviderName = caCertBean.getServiceProviderName();
														caServiceName = caCertBean.getServiceName();
														log.debug("caCountry " + caCountry + " caServiceProviderName " + caServiceProviderName+ " caServiceName " + caServiceName );
														
														// La CRL deve essere storicizzata
														log.debug("Storicizzo la crl");
														crlStorage.insertCRL(issuerCRL, caCountry, caServiceProviderName, caServiceName );
														
														isQualified = checkCRL( activeCertificate, issuerCRL, referenceDate);
														log.debug("Verifica CRL " + isQualified);
														
														boolean certRevoked = issuerCRL.isRevoked(activeCertificate);
														log.debug("Verifica CRL certRevoked " + certRevoked);
														
														if( certRevoked ) {
															log.debug("Revoco il certificato ");
															certificatesAuthorityStorage.updateCA(qualifiedCertificate, caCountry, caServiceProviderName, caServiceName, caCertBean.getServiceStatus(), caCertBean.getServiceType(), false);
														}
													}
												} catch (CryptoStorageException e) {
													log.error("Errore nell'insermento nella cartella locale della CRL: " + e.getMessage());
												}
												
												
											}
											ciclaCRL = false;
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
			}
		} catch (CryptoStorageException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		
		

	}
	
	private boolean checkCRL( X509Certificate signatureCertificate, X509CRL crl, Date date) {
		X509CRLEntry crlEntry = crl.getRevokedCertificate(signatureCertificate);

		// il certificato e stato revocato
		if (crlEntry != null) {
			if (date != null && crlEntry.getRevocationDate().before(date)) {
				log.debug(MessageHelper.getMessage(CertMessage.CERT_CA_CRL_REVOKED_BEFORE, crlEntry.getRevocationDate(), date));
				return false;
			} else if (date == null) {
				log.debug(MessageHelper.getMessage(CertMessage.CERT_CA_CRL_REVOKED_AFTER, crlEntry.getRevocationDate()));
				return false;
			}
		} else {
			//log.debug("certificato non presente nella CRL");
		}
		return true;
	}


	public String getTimeout() {
		return timeout;
	}


	public void setTimeout(String timeout) {
		this.timeout = timeout;
	}
	
	
}
