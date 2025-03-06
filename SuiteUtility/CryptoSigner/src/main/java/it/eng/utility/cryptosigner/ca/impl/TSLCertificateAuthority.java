package it.eng.utility.cryptosigner.ca.impl;

import it.eng.utility.cryptosigner.CryptoConfiguration;

import it.eng.utility.cryptosigner.CryptoSingleton;
import it.eng.utility.cryptosigner.FactorySigner;
import it.eng.utility.cryptosigner.exception.CryptoSignerException;

import java.security.cert.X509Certificate;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.commons.httpclient.Credentials;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpsURL;
import org.apache.commons.httpclient.NTCredentials;
import org.apache.commons.httpclient.UsernamePasswordCredentials;
import org.apache.commons.httpclient.auth.AuthScope;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.w3c.dom.Document;

import be.fedict.eid.tsl.TrustService;
import be.fedict.eid.tsl.TrustServiceList;
import be.fedict.eid.tsl.TrustServiceListFactory;
import be.fedict.eid.tsl.TrustServiceProvider;


/**
 * Estensione di una {@link DefaultCertificateAuthority}: effettua lo scaricamento
 * e il parsing della lista dei certificati attendibili a partire da una Trust Service Status List
 * @author Administrator
 *
 */
public class TSLCertificateAuthority/* extends DefaultCertificateAuthority */{
	
	private static Logger log = LogManager.getLogger( TSLCertificateAuthority.class );
	private TSLDownloadPolicy policy;
	//TODO store on file !? private static final String LAST_CA_DOWNLOAD_TIME_FILE="lastCaDownloadTime.txt";
	
	private List<String> serviceStatusFilter;
	
	/*public void updateCertificate() throws CryptoSignerException {
		//		Map<Principal, X509Certificate> qualifiedCertificates = new HashMap<Principal, X509Certificate>();
		try{
			CryptoConfiguration cryptoConfiguration = CryptoSingleton.getInstance().getConfiguration();
			//verifico se devo aggiornare la lista CA
			if(policy==null || (policy!=null && policy.needDownload())  ){

				String urlString = cryptoConfiguration.getQualifiedCertificatesURL();
				HttpsURL url = new HttpsURL(urlString);			
				GetMethod method = new GetMethod(url.toString());

				HttpClient httpclient = new HttpClient();
				if (cryptoConfiguration.isProxy()){
					httpclient.getHostConfiguration().setProxy(cryptoConfiguration.getProxyHost(), cryptoConfiguration.getProxyPort());
					log.info("isNT " + cryptoConfiguration.getNTauth());
					if( cryptoConfiguration.getNTauth()==null || !cryptoConfiguration.getNTauth() ) {
						httpclient.getState().setProxyCredentials(AuthScope.ANY,new UsernamePasswordCredentials(cryptoConfiguration.getProxyUser(), cryptoConfiguration.getProxyPassword()));
					} else {
						httpclient.getParams().setAuthenticationPreemptive(true);
						log.info("NT User " + cryptoConfiguration.getProxyUser());
						log.info("NT Password " );
						log.info("NT Host " + cryptoConfiguration.getNTHost());
						log.info("NT Dominio " + cryptoConfiguration.getDominio());
						Credentials cred = new NTCredentials(cryptoConfiguration.getProxyUser(), cryptoConfiguration.getProxyPassword(),
								cryptoConfiguration.getNTHost(), cryptoConfiguration.getDominio());
						httpclient.getState().setProxyCredentials(AuthScope.ANY, cred);
					}
				}
				httpclient.executeMethod(method);

				java.io.InputStream is = method.getResponseBodyAsStream();

				DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
				factory.setNamespaceAware(true);
				DocumentBuilder docBuilder = factory.newDocumentBuilder();

				Document doc =docBuilder.parse(is);

				method.releaseConnection();

				TrustServiceList trustServiceList = TrustServiceListFactory.newInstance(doc);
				List<TrustServiceProvider> trustServiceProviders = trustServiceList.getTrustServiceProviders();
				//si potrebbe ottimizzare conservando l'hash dei dati scaricati e se cambia allora inseriamo nello store!!
				for (TrustServiceProvider trustServiceProvider: trustServiceProviders){
					List<TrustService> trustServices = trustServiceProvider.getTrustServices();
					for (TrustService trustService: trustServices){
						//TODO check certificate is null !?
						X509Certificate certificate = trustService.getServiceDigitalIdentity();
						//dovresti prendere solo quelli attivi con status 
						//http://uri.etsi.org/TrstSvc/eSigDir-1999-93-EC-TrustedList/Svcstatus/accredited
						//se non hai configurato il filtro tutti gli stati vanno bene
						boolean statusFilterValid=serviceStatusFilter==null || serviceStatusFilter.size()==0;
						if(!statusFilterValid)
						// hai configurato il filtro per cui verifica se lo stato del servizio è fra quelli ammessi
						  statusFilterValid=serviceStatusFilter!=null && serviceStatusFilter.size()>0 && serviceStatusFilter.contains(trustService.getStatus());
							
						  if(statusFilterValid){
							  //verifico se sono attivi gli schedulatori 
							  boolean useSchedule=CryptoSingleton.getInstance().getConfiguration().isUseSchedule();
							  if(useSchedule){
								  log.debug("Notifico : " + certificate.getSubjectDN().getName());
								  // Notifico il nuovo certificato agli observer
								  this.setChanged();
								  this.notifyObservers(certificate);
							  }else{
								  log.info("schedulatore Spento inserisco nello Store");
								  //FactorySigner.getInstanceCAStorage().insertCA(certificate);
							  }
						  }else{
							  log.debug("skipping certificate "+ certificate.getSubjectDN());
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
	}*/

	 

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
 
//	public boolean needDownload() {
//		boolean ret=false;
//
//		long lastDownloadTime=CryptoSingleton.getInstance().getConfiguration().getLastTimeDownload();
//		int interval=CryptoSingleton.getInstance().getConfiguration().getDayDownloadCAList();
//		//se imposti l'intervallo zero o negativo scarica sempre
//		// se lastDownloadTime ==-1 è la prima volta e quindi devi scaricare
//		if(lastDownloadTime==-1 || interval<=0){
//			ret=true; 
//		}else{
//			Date last = new Date(lastDownloadTime); // last download date
//			Date today = new Date(); //  
//			int days = Days.daysBetween(new DateTime(last), new DateTime(today)).getDays();
//			if(days>interval){
//				ret=true;
//			}
//		}
//		return ret;
//	}
	
	
}
