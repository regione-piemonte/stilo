package it.eng.utility.cryptosigner.data;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.security.Principal;
import java.security.Security;
import java.security.cert.Certificate;
import java.security.cert.CertificateExpiredException;
import java.security.cert.CertificateNotYetValidException;
import java.security.cert.X509CRL;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import javax.security.auth.x500.X500Principal;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.commons.httpclient.Credentials;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpsURL;
import org.apache.commons.httpclient.NTCredentials;
import org.apache.commons.httpclient.UsernamePasswordCredentials;
import org.apache.commons.httpclient.auth.AuthScope;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.lang.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.bouncycastle.asn1.ASN1InputStream;
import org.bouncycastle.asn1.ASN1Primitive;
import org.bouncycastle.asn1.DEROctetString;
import org.bouncycastle.asn1.x500.X500Name;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.w3c.dom.Document;

import be.fedict.eid.tsl.TrustService;
import be.fedict.eid.tsl.TrustServiceList;
import be.fedict.eid.tsl.TrustServiceListFactory;
import be.fedict.eid.tsl.TrustServiceProvider;
import it.eng.utility.cryptosigner.CryptoConfiguration;
import it.eng.utility.cryptosigner.CryptoConstants;
import it.eng.utility.cryptosigner.CryptoSingleton;
import it.eng.utility.cryptosigner.context.CryptoSignerApplicationContextProvider;
import it.eng.utility.cryptosigner.exception.CryptoSignerException;
import it.eng.utility.cryptosigner.exception.NoSignerException;

public class SignerUtil {

	private static Logger log = LogManager.getLogger(SignerUtil.class);

	private DataSigner dataSigner;
	private ApplicationContext context;
	//private String signerGenerico;

	private SignerUtil(ApplicationContext applicationContext) {
		context = applicationContext;
		if (context == null) {
			context = new ClassPathXmlApplicationContext("ControllerConfig.xml");
		}
		CryptoSingleton.getInstance().setContext(context);
		dataSigner = (DataSigner) context.getBean("DataSigner");
	}

	/**
	 * Crea una nuova istanza della classe
	 * 
	 * @return nuova istanza della classe
	 */
	public static SignerUtil newInstance() {
		if (Security.getProvider(BouncyCastleProvider.PROVIDER_NAME) == null) {
			Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
		}
		return new SignerUtil(CryptoSignerApplicationContextProvider.getContext());
	}

	/**
	 * Recupera l'{@link it.eng.utility.cryptosigner.data.AbstractSigner} preposto al riconoscimento del file firmato in input
	 * 
	 * @param file
	 *            il file firmato di cui ricavare il signer
	 * @return l'{@link it.eng.utility.cryptosigner.data.AbstractSigner} da utilizzare
	 * @throws CryptoSignerException
	 */
	public AbstractSigner getSignerManager(File file) throws NoSignerException {
		log.debug("Metodo getSignerManager per file " + file);

		// Controllo che tipo di Signer Utilizzare
		List<AbstractSigner> signersManager = new ArrayList<AbstractSigner>(8);
		HashMap<Integer, AbstractSigner> signersMap = new HashMap<Integer, AbstractSigner>(8, 8);
		DataSigner dataSignerRiscritto = new DataSigner();
		log.debug("--- " + dataSigner.getSignersManager()) ;
		//for (AbstractSigner signer : dataSigner.getSignersManager()) {
			//log.debug(":: " +signer.getClass().getName() + " " + DataSignerType.getOrdineFromName(signer.getClass().getName()));
			//signersMap.put(DataSignerType.getOrdineFromName(signer.getClass().getName())-1, signer);
			/*if (signer.getClass().getName().toString().replace("it.eng.utility.cryptosigner.data.", "").equalsIgnoreCase("CAdESSigner")) {
				signersMap.put("0", signer);
			}
			if (signer.getClass().getName().toString().replace("it.eng.utility.cryptosigner.data.", "").equalsIgnoreCase("PdfSigner")) {
				signersMap.put("1", signer);
			}
			if (signer.getClass().getName().toString().replace("it.eng.utility.cryptosigner.data.", "").equalsIgnoreCase("XMLSigner")) {
				signersMap.put("2", signer);
			}
			if (signer.getClass().getName().toString().replace("it.eng.utility.cryptosigner.data.", "").equalsIgnoreCase("TsdSigner")) {
				signersMap.put("3", signer);
			}
			if (signer.getClass().getName().toString().replace("it.eng.utility.cryptosigner.data.", "").equalsIgnoreCase("P7MSigner")) {
				signersMap.put("4", signer);
			}
			if (signer.getClass().getName().toString().replace("it.eng.utility.cryptosigner.data.", "").equalsIgnoreCase("P7SSigner")) {
				signersMap.put("5", signer);
			}
			if (signer.getClass().getName().toString().replace("it.eng.utility.cryptosigner.data.", "").equalsIgnoreCase("M7MSigner")) {
				signersMap.put("6", signer);
			}
			if (signer.getClass().getName().toString().replace("it.eng.utility.cryptosigner.data.", "").equalsIgnoreCase("TsrSigner")) {
				signersMap.put("7", signer);
			}*/
			
		//}
		for (DataSignerType dataType : DataSignerType.values()) {
			AbstractSigner signer = getSignerEl(dataType.getName());
			signersMap.put(dataType.getOrdine(), signer);
		}
		log.info("signersMap " + signersMap);
		int index = 0;
		for (int i = 0; i < signersMap.size(); i++) {
			if( signersMap.get(i)!=null){
				signersManager.add(index, signersMap.get(i));
				index++;
			}
		}
		log.debug("-------------------- " + signersManager);
		dataSignerRiscritto.setSignersManager(signersManager);
		//dataSignerRiscritto=dataSigner;

		for (AbstractSigner signer : dataSignerRiscritto.getSignersManager()) {
			if (signer != null) {
				try {
					AbstractSigner newSigner = signer.getClass().newInstance();
//					if (!(getSignerGenerico() == null || getSignerGenerico().equalsIgnoreCase(""))) {
//						// log.info("getSignerGenerico() " + getSignerGenerico());
//						if (!(getSignerGenerico().equalsIgnoreCase("CAdESSigner") && (signer instanceof M7MSigner))) {
//							log.debug("newSigner " + newSigner);
//							boolean isSigned = newSigner.isSignedType(file);
//							log.debug("isSigned? " + isSigned);
//							if (isSigned) {
//								setSignerGenerico(signer.getClass().getName().toString().replace("it.eng.utility.cryptosigner.data.", ""));
//								//newSigner.setFile(file);
//								return newSigner;
//							}
//						}
//					} else {
						log.debug("newSigner " + newSigner);
						boolean isSigned = newSigner.isSignedType(file);
						log.debug("isSigned? " + isSigned);
						if (isSigned) {
							//setSignerGenerico(signer.getClass().getName().toString().replace("it.eng.utility.cryptosigner.data.", ""));
							//newSigner.setFile(file);
							return newSigner;
						}
//					}
				} catch (InstantiationException e) {
					log.warn("InstantiationException getSignerManager", e);
				} catch (IllegalAccessException e) {
					log.warn("IllegalAccessException getSignerManager", e);
				}
			}
		}
		// Se sono arrivato fino a qui lancio una eccezione;
		log.debug("Nessun Manager Signer Trovato per il file specificato: " + file);
		//return null;
		throw new NoSignerException("Nessun Manager Signer Trovato per il file specificato: " + file);

	}
	
	private AbstractSigner getSignerEl(String name){
		/*for(AbstractSigner signer : dataSigner.getSignersManager()){
			if( signer.getClass().getName().equalsIgnoreCase(name)){
				return signer;
			}
		}*/
		if( name!=null && name.equalsIgnoreCase("it.eng.utility.cryptosigner.data.CAdESSigner")){
			return new CAdESSigner();
		}
		if( name!=null && name.equalsIgnoreCase("it.eng.utility.cryptosigner.data.PdfSigner")){
			return new PdfSigner();
		}
		if( name!=null && name.equalsIgnoreCase("it.eng.utility.cryptosigner.data.XMLSigner")){
			return new XMLSigner();
		}
		/*if( name!=null && name.equalsIgnoreCase("it.eng.utility.cryptosigner.data.TsdSigner")){
			return new TsdSigner();
		}*/
		if( name!=null && name.equalsIgnoreCase("it.eng.utility.cryptosigner.data.P7MSigner")){
			return new P7MSigner();
		}
		/*if( name!=null && name.equalsIgnoreCase("it.eng.utility.cryptosigner.data.P7SSigner")){
			return new P7SSigner();
		}
		if( name!=null && name.equalsIgnoreCase("it.eng.utility.cryptosigner.data.M7MSigner")){
			return new M7MSigner();
		}
		if( name!=null && name.equalsIgnoreCase("it.eng.utility.cryptosigner.data.TsrSigner")){
			return new TsrSigner();
		}*/
		return null;
	}

	public AbstractSigner getSignerManager(File file, String estensione) throws NoSignerException {
		log.debug("Metodo getSignerManager per file " + file + " e estensione " + estensione);
		if (estensione != null) {
			if (estensione.equalsIgnoreCase("p7m")) {
				AbstractSigner newSigner = new CAdESSigner();
				boolean isCades = newSigner.isSignedType(file);
				log.debug("isSignedCades? " + isCades);
				if (isCades) {
				//	setSignerGenerico("CAdESSigner");
				//	newSigner.setFile(file);
					return newSigner;
				} else {
					newSigner = new P7MSigner();
					boolean isP7M = newSigner.isSignedType(file);
					log.debug("isSignedP7M? " + isP7M);
					if (isP7M) {
					//	setSignerGenerico("P7MSigner");
					//	newSigner.setFile(file);
						return newSigner;
					} else
						return getSignerManager(file);
				}
			} else if (estensione.equalsIgnoreCase("pdf")) {
				AbstractSigner newSigner = new PdfSigner();
				boolean isPdf = newSigner.isSignedType(file);
				log.debug("isSignedPdf? " + isPdf);
				if (isPdf) {
					//setSignerGenerico("PdfSigner");
					//newSigner.setFile(file);
					return newSigner;
				} else
					return getSignerManager(file);
			}
			if (estensione.equalsIgnoreCase("xml")) {
				AbstractSigner newSigner = new XMLSigner();
				boolean isXml = newSigner.isSignedType(file);
				log.debug("isSignedXml? " + isXml);
				if (isXml) {
					//setSignerGenerico("XMLSigner");
					//newSigner.setFile(file);
					return newSigner;
				} else
					return getSignerManager(file);
			} else {
				return getSignerManager(file);
			}
			// throw new CryptoSignerException("Nessun Manager Signer Trovato per il file specificato: " + file);
		} else
			return getSignerManager(file);
	}

	/**
	 * Recupera l'{@link it.eng.utility.cryptosigner.data.AbstractSigner} preposto al riconoscimento contenuto firmato in input
	 * 
	 * @param content
	 *            il contenuto firmato di cui ricavare il signer
	 * @return l'{@link it.eng.utility.cryptosigner.data.AbstractSigner} da utilizzare
	 * @throws CryptoSignerException
	 */
	// public AbstractSigner getSignerManager(byte[] content) throws CryptoSignerException {
	// //Controllo che tipo di Signer Utilizzare
	// for (AbstractSigner signer: dataSigner.getSignersManager()) {
	// if (signer.isSignedType(content)) {
	// //signer.setFile(file);
	// return signer;
	// }
	// }
	// //Se sono arrivato fino a qui lancio una eccezione;
	// throw new CryptoSignerException("Nessun Manager Signer Trovato per il file specificato");
	//
	// }

	/**
	 * Controlla se il certificato passato in ingresso � valido per la CRL associata
	 * 
	 * @param certificate
	 * @param crl
	 * @return
	 * @throws CryptoSignerException
	 */
	public boolean validCertificateWithCRL(java.security.cert.X509Certificate certificate, X509CRL crl) throws CryptoSignerException {
		boolean isValid = false;
		try {
			certificate.checkValidity();
			if (!crl.isRevoked(certificate)) {
				isValid = true;
			}
		} catch (CertificateExpiredException e) {
			log.warn("CertificateExpiredException validCertificateWithCRL", e);
		} catch (CertificateNotYetValidException e) {
			log.warn("CertificateNotYetValidException validCertificateWithCRL", e);
		}
		return isValid;
	}

	/**
	 * Recupera la CRL in base all'url passato in ingresso
	 * 
	 * @param url
	 * @return
	 * @throws CryptoSignerException
	 */
	public X509CRL getCrlByURL(String url) {
		// log.info("getCrlByURL START");
		X509CRL crl = null;
		url = StringUtils.trim(url);
		try {
			CRLUtil util = (CRLUtil) context.getBean(CryptoConstants.CRL_UTIL);
			//log.debug("getCrl URL:" + url);
			if (url.toUpperCase().startsWith("LDAP")) {
				crl = util.searchCrlByLDAP(url);
			} else if (url.toUpperCase().startsWith("HTTP")) {
				crl = util.ricercaCrlByProxyHTTP(url, CryptoSingleton.getInstance().getConfiguration());
			} else {
				throw new CryptoSignerException("Protocollo di comunicazione non supportato!");
			}
			// log.info("getCrlByURL END");
		} catch (CryptoSignerException e) {
			log.error("Errore CryptoSignerException SignerUtil.getCrlByURL per l'url: " + url + " : " + e.getMessage()/*, e*/);
		} catch (Exception e) {
			log.error("Eccezione SignerUtil.getCrlByURL per l'url: " + url + " : " + e.getMessage()/*, e*/);
		}
		return crl;
	}

	/**
	 * Recupera un vettore contenente i distribution point CRL del certificato passato in ingresso
	 * 
	 * @param certificate
	 * @return
	 * @throws CryptoSignerException
	 */
	public Vector<String> getURLCrlDistributionPoint(X509Certificate certificate) throws CryptoSignerException {
		try {
			byte[] val1 = certificate.getExtensionValue("2.5.29.31");
			if( val1!=null ){
				ASN1InputStream oAsnInStream = new ASN1InputStream(new ByteArrayInputStream(val1));
				ASN1Primitive derObj = oAsnInStream.readObject();
				DEROctetString dos = (DEROctetString) derObj;
				byte[] val2 = dos.getOctets();
				ASN1InputStream oAsnInStream2 = new ASN1InputStream(new ByteArrayInputStream(val2));
				ASN1Primitive derObj2 = oAsnInStream2.readObject();
				CRLUtil util = new CRLUtil();
				Vector<String> urls = (Vector<String>) util.getDERValue(derObj2);
				//log.debug("urls " + urls);
				if (urls != null && !urls.isEmpty()) {
					return urls;
				} else {
					log.error("Lista delle distribution point vuota o nulla");
					throw new Exception("Lista delle distribution point vuota o nulla");
				}
			} else {
				log.error("Lista delle distribution point vuota o nulla");
				throw new Exception("Lista delle distribution point vuota o nulla");
			}
		} catch (Exception e) {
			log.error("Errore nel recupero del distribution point della CRL"/*, e*/);
			throw new CryptoSignerException("Errore nel recupero del distribution point della CRL", e);
		}
	}

	/**
	 * Recupera la lista dei certificati accreditati dalla Trust Service Status List (ETSI TS 102 231) configurata nel contesto Spring all'interno del bean
	 * "CryptoConfiguration" come attributo QualifiedCertificatesURL.
	 * 
	 * @return la mappa delle corrispondenze tra Principal (ente) e certificato
	 * @throws CryptoSignerException
	 */
	public Map<Principal, X509Certificate> getQualifiedPrincipalsAndX509Certificates() throws CryptoSignerException {
		Map<Principal, X509Certificate> qualifiedCertificates = new HashMap<Principal, X509Certificate>();
		try {
			CryptoConfiguration cryptoConfiguration = (CryptoConfiguration) context.getBean(CryptoConstants.CRYPTO_CONFIGURATION);

			String urlString = cryptoConfiguration.getQualifiedCertificatesURL();
			HttpsURL url = new HttpsURL(urlString);
			GetMethod method = new GetMethod(url.toString());

			HttpClient httpclient = new HttpClient();
			if (cryptoConfiguration.isProxy()) {
				httpclient.getHostConfiguration().setProxy(cryptoConfiguration.getProxyHost(), cryptoConfiguration.getProxyPort());
				if (cryptoConfiguration.getNTauth() == null || !cryptoConfiguration.getNTauth()) {
					httpclient.getState().setProxyCredentials(AuthScope.ANY,
							new UsernamePasswordCredentials(cryptoConfiguration.getProxyUser(), cryptoConfiguration.getProxyPassword()));
				} else {
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

			Document doc = docBuilder.parse(is);

			method.releaseConnection();

			TrustServiceList trustServiceList = TrustServiceListFactory.newInstance(doc);
			List<TrustServiceProvider> trustServiceProviders = trustServiceList.getTrustServiceProviders();

			for (TrustServiceProvider trustServiceProvider : trustServiceProviders) {
				List<TrustService> trustServices = trustServiceProvider.getTrustServices();
				for (TrustService trustService : trustServices) {
					X509Certificate certificate = trustService.getServiceDigitalIdentity();
					qualifiedCertificates.put(certificate.getSubjectDN(), certificate);
				}
			}
		} catch (Exception e) {
			throw new CryptoSignerException("Errore nel recupero dei certificati accreditati: ", e);
		}
		return qualifiedCertificates;
	}

	/**
	 * Metodo di utilità che consente di trasformare il contenuto in byte in input nella corrispondente stringa esadecimale
	 * 
	 * @param buf
	 *            contenuto in byte
	 * @return la stringa esadecimale corrispondente al contenuto
	 */
	public static String asHex(byte buf[]) {
		StringBuffer strbuf = new StringBuffer(buf.length * 2);

		for (int i = 0; i < buf.length; i++) {
			if (((int) buf[i] & 0xff) < 0x10)
				strbuf.append("0");
			strbuf.append(Long.toString((int) buf[i] & 0xff, 16));
		}
		return strbuf.toString();
	}

	public static X509Certificate getCertificateFromCollection(Principal issuerPrincipal, Collection<? extends Certificate> certificates) {
		X500Name x509Name = new X500Name(issuerPrincipal.getName());
		synchronized (x509Name) {
			if (certificates != null) {
				for (Certificate qualifiedCertificate : certificates) {
					if (qualifiedCertificate instanceof X509Certificate) {
						X509Certificate x509Certificate = (X509Certificate) qualifiedCertificate;
						Principal principal = x509Certificate.getSubjectDN();
						if (principal instanceof X500Principal) {
							X500Principal x509Principal = (X500Principal) principal;
							if (x509Principal.equals(x509Name))
								return x509Certificate;
						}
					}
				}
			}
		}
		return null;
	}

//	public String getSignerGenerico() {
//		return signerGenerico;
//	}
//
//	public void setSignerGenerico(String signerGenerico) {
//		this.signerGenerico = signerGenerico;
//	}

}