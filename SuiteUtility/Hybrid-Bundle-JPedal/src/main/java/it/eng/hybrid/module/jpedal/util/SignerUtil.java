package it.eng.hybrid.module.jpedal.util;

import java.io.File;
import java.security.cert.CertificateExpiredException;
import java.security.cert.CertificateNotYetValidException;
import java.security.cert.X509CRL;

import net.sf.jsignpdf.AbstractSigner;
import net.sf.jsignpdf.DataSigner;

public class SignerUtil {
	
	private DataSigner dataSigner;
	
	private SignerUtil() {
		dataSigner = new DataSigner();
	}
	
	
	/**
	 * Crea una nuova istanza della classe
	 * @return nuova istanza della classe
	 */
	public static SignerUtil newInstance() {
		return new SignerUtil();
	}
	
	/**
	 * Recupera l'{@link it.eng.utility.cryptosigner.data.AbstractSigner}
	 * preposto al riconoscimento del file firmato in input
	 * @param file il file firmato di cui ricavare il signer
	 * @return l'{@link it.eng.utility.cryptosigner.data.AbstractSigner} da utilizzare
	 * @throws CryptoSignerException
	 */
	public AbstractSigner getSignerManager(File file) throws Exception {
		//Controllo che tipo di Signer Utilizzare
		for (AbstractSigner signer: dataSigner.getSignersManager()) {
			try {
				AbstractSigner newSigner = signer.getClass().newInstance();
				if (newSigner.isSignedType(file)) {
					newSigner.setFile(file);
					return newSigner;
				}
			} catch (InstantiationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		//Se sono arrivato fino a qui lancio una eccezione;
		throw new Exception("Nessun Manager Signer Trovato per il file specificato: " + file);
		
	}
	
	/**
	 * Controlla se il certificato passato in ingresso � valido per la CRL associata
	 * @param certificate
	 * @param crl
	 * @return
	 * @throws CryptoSignerException
	 */
	public boolean validCertificateWithCRL(java.security.cert.X509Certificate certificate, X509CRL crl)throws Exception {
		boolean isValid = false;
		try {
			certificate.checkValidity();
			if (!crl.isRevoked(certificate)) {
				isValid = true;
			}
		} catch (CertificateExpiredException e) {
			e.printStackTrace();
		} catch (CertificateNotYetValidException e) {
			e.printStackTrace();
		}
		return isValid;
	}

	/**
	 * Recupera la CRL in base all'url passato in ingresso
	 * @param url
	 * @return
	 * @throws CryptoSignerException
	 */
//	public X509CRL getCrlByURL(String url) throws CryptoSignerException {
//		try {
//			CRLUtil util = new CRLUtil();
//			X509CRL crl = null;
//			url = StringUtils.trim(url);
//			log.debug("URL:"+url);
//			if (url.toUpperCase().startsWith("LDAP")) {
//				crl = util.searchCrlByLDAP(url);
//			} else if (url.toUpperCase().startsWith("HTTP")) {
//				crl = util.ricercaCrlByProxyHTTP(url, CryptoSingleton.getInstance().getConfiguration());
//			} else {
//				throw new CryptoSignerException("Protocollo di comunicazione non supportato!");
//			}
//			return crl;
//		}catch(CryptoSignerException e) {
//			throw e;		
//		}catch(Exception e) {
//			throw new CryptoSignerException("Errore recupero CRL ",e);			
//		}
//	}
	
	/**
	 * Recupera un vettore contenente i distribution point CRL del certificato passato in ingresso
	 * @param certificate
	 * @return
	 * @throws CryptoSignerException
	 */
//	public String getURLCrlDistributionPoint(X509Certificate certificate)throws CryptoSignerException {
//        try { 
//       	 	byte[] val1 = certificate.getExtensionValue("2.5.29.31"); 
//           ASN1InputStream oAsnInStream = new ASN1InputStream(new ByteArrayInputStream(val1)); 
//           DERObject derObj = oAsnInStream.readObject(); 
//           DEROctetString dos = (DEROctetString)derObj; 
//           byte[] val2 = dos.getOctets(); 
//           ASN1InputStream oAsnInStream2 = new ASN1InputStream(new ByteArrayInputStream(val2)); 
//           DERObject derObj2 = oAsnInStream2.readObject(); 
//           CRLUtil util = new CRLUtil();
//           Vector<String> urls = (Vector<String>)util.getDERValue(derObj2); 
//           if (urls!=null && !urls.isEmpty()) {
//        	   return (String)urls.get(0);
//           } else {
//        	  throw new Exception("Lista delle distribution point vuota o nulla"); 
//           }
//       }catch (Exception e) { 
//           throw new CryptoSignerException("Errore nel recupero del distribution point della CRL",e); 
//       } 
//	}
	
	/**
	 * Recupera la lista dei certificati accreditati dalla Trust Service Status List
	 * (ETSI TS 102 231) configurata nel contesto Spring all'interno del bean
	 * "CryptoConfiguration" come attributo QualifiedCertificatesURL. 
	 * @return la mappa delle corrispondenze tra Principal (ente) e certificato
	 * @throws CryptoSignerException
	 */
//	public Map<Principal, X509Certificate> getQualifiedPrincipalsAndX509Certificates() throws CryptoSignerException {
//		Map<Principal, X509Certificate> qualifiedCertificates = new HashMap<Principal, X509Certificate>();
//		try {
//			CryptoConfiguration cryptoConfiguration = (CryptoConfiguration)context.getBean(CryptoConstants.CRYPTO_CONFIGURATION);
//			
//			String urlString = cryptoConfiguration.getQualifiedCertificatesURL();
//			HttpsURL url = new HttpsURL(urlString);			
//		    GetMethod method = new GetMethod(url.toString());
//		    		
//	        HttpClient httpclient = new HttpClient();
//	        if (cryptoConfiguration.isProxy()) {
//	        	httpclient.getHostConfiguration().setProxy(cryptoConfiguration.getProxyHost(), cryptoConfiguration.getProxyPort());
//	        	httpclient.getState().setProxyCredentials(AuthScope.ANY,new UsernamePasswordCredentials(cryptoConfiguration.getProxyUser(), cryptoConfiguration.getProxyPassword()));
//	        }
//	        httpclient.executeMethod(method);
//	        
//			java.io.InputStream is = method.getResponseBodyAsStream();
//			
//			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
//			factory.setNamespaceAware(true);
//			DocumentBuilder docBuilder = factory.newDocumentBuilder();
//			
//			Document doc =docBuilder.parse(is);
//
//			method.releaseConnection();
//
//			TrustServiceList trustServiceList = TrustServiceListFactory.newInstance(doc);
//			List<TrustServiceProvider> trustServiceProviders = trustServiceList.getTrustServiceProviders();
//			
//			for (TrustServiceProvider trustServiceProvider: trustServiceProviders) {
//				List<TrustService> trustServices = trustServiceProvider.getTrustServices();
//				for (TrustService trustService: trustServices) {
//					X509Certificate certificate = trustService.getServiceDigitalIdentity();
//					qualifiedCertificates.put(certificate.getSubjectDN(), certificate);
//				}
//			}
//		}catch(Exception e) {
//			throw new CryptoSignerException("Errore nel recupero dei certificati accreditati: ", e);
//		}
//		return qualifiedCertificates;
//	}
	
	/**
	 * Metodo di utilit� che consente di trasformare il contenuto
	 * in byte in input nella corrispondente stringa esadecimale
	 * @param buf contenuto in byte
	 * @return la stringa esadecimale corrispondente al contenuto
	 */
//	public static String asHex(byte buf[])
//	{
//	        StringBuffer strbuf = new StringBuffer(buf.length * 2);
//	
//	        for (int i=0; i< buf.length; i++)
//	        {
//	                if (((int) buf[i] & 0xff) < 0x10)
//	                        strbuf.append("0");
//	                strbuf.append(Long.toString((int) buf[i] & 0xff, 16));
//	        }
//	        return strbuf.toString();
//	}
	
//	public static X509Certificate getCertificateFromCollection(Principal issuerPrincipal, Collection<? extends Certificate> certificates) {
//		X509Name x509Name = new X509Name(issuerPrincipal.getName());
//		synchronized (x509Name) {
//			if (certificates!=null) {
//				for (Certificate qualifiedCertificate: certificates) {
//					if (qualifiedCertificate instanceof X509Certificate) {
//						X509Certificate x509Certificate = (X509Certificate)qualifiedCertificate;
//						Principal principal = x509Certificate.getSubjectDN();
//						if (principal instanceof X509Principal) {
//							X509Principal x509Principal = (X509Principal)principal;
//							if (x509Principal.equals(x509Name))
//								return x509Certificate;
//						}
//					}
//				}
//			}
//		}
//		return null;
//	}

}