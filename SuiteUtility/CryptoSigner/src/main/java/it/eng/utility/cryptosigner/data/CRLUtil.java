package it.eng.utility.cryptosigner.data;

import it.eng.utility.cryptosigner.CryptoConfiguration;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.security.cert.CertificateFactory;
import java.security.cert.X509CRL;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;

import javax.naming.NamingEnumeration;
import javax.naming.TimeLimitExceededException;
import javax.naming.directory.Attribute;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;
import javax.naming.directory.SearchResult;

import org.apache.commons.httpclient.Credentials;
import org.apache.commons.httpclient.HostConfiguration;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpMethodBase;
import org.apache.commons.httpclient.NTCredentials;
import org.apache.commons.httpclient.UsernamePasswordCredentials;
import org.apache.commons.httpclient.auth.AuthScope;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.bouncycastle.asn1.ASN1Primitive;
import org.bouncycastle.asn1.DEROctetString;
import org.bouncycastle.asn1.DERSequence;
import org.bouncycastle.asn1.DERTaggedObject;
import org.bouncycastle.asn1.DLSequence;

class CRLUtil {
	
	public static final  Logger log = LogManager.getLogger(CRLUtil.class);
	
	private String timeout = null;
	/**
	 * Recupera le CRL tramite il protocollo HTTP/HTTPS
	 * @param url
	 * @param auth
	 * @return crl
	 * @throws Exception
	 */
    X509CRL ricercaCrlByProxyHTTP(String url, CryptoConfiguration configuration ) throws Exception {
    	HostConfiguration config = new HostConfiguration();
    	HttpClient http_client = new HttpClient();
    	log.debug("Scarico http da " + url);
    	 
        if (configuration!=null && StringUtils.isNotEmpty(configuration.getProxyHost()) ) {
    		
    		if (StringUtils.isNotEmpty(configuration.getProxyUser()) ) {
    			Credentials credential = null;
    			if( configuration.getNTauth()==null || !configuration.getNTauth() ) {
    				credential = new UsernamePasswordCredentials(configuration.getProxyUser(),configuration.getProxyPassword());
    		    } else {
    		    	http_client.getParams().setAuthenticationPreemptive(true);
					credential = new NTCredentials(configuration.getProxyUser(), configuration.getProxyPassword(),
    		    			configuration.getNTHost(), configuration.getDominio());
    			}
    			
    			AuthScope scope = new AuthScope(configuration.getProxyHost(), configuration.getProxyPort());
		        http_client.getState().setProxyCredentials(scope, credential);
    		}
	        
	        config.setProxy(configuration.getProxyHost(), configuration.getProxyPort());
    	}
    	 
    	//http_client.getHttpConnectionManager().getParams().setConnectionTimeout(60000);
    	 if( timeout!=null && !timeout.equalsIgnoreCase("")){
         	try {
 	        	Integer timeoutInt = Integer.parseInt(timeout);
 		        //log.debug("Imposto il timeout a " + timeout +" msec");
 		    	http_client.getHttpConnectionManager().getParams().setConnectionTimeout(timeoutInt);
 		    	http_client.getHttpConnectionManager().getParams().setSoTimeout(timeoutInt);
         	} catch(Exception e){
         		log.error("Errore nel settaggio del timeout " + timeout + " - " + e.getMessage() );
         	}
         }else {
         	log.debug("Timeout non impostato");
         }
    	    	
    	 HttpMethodBase method = new PostMethod(url);
         try {
         	long start = System.currentTimeMillis();
         	http_client.executeMethod(config, method);
         	long elapsedTimeMillis = System.currentTimeMillis()-start;
 			log.debug("<<<<<<< executeMethod eseguito in " + elapsedTimeMillis + "ms");
 		} catch (HttpException e) {
 			log.error("", e);
 			throw e;
 		} catch (IOException e) {
 			log.error("", e);
 			throw e;
 		}
         
         if (method.getStatusCode() !=HttpURLConnection.HTTP_OK || 
	        		(method.getResponseBodyAsString()==null || method.getResponseBodyAsString().equalsIgnoreCase(""))) {
	         method = new GetMethod(url);
         	http_client.executeMethod(config, method);
         }
         if (method.getStatusCode() !=HttpURLConnection.HTTP_OK || 
	        		(method.getResponseBodyAsString()==null || method.getResponseBodyAsString().equalsIgnoreCase(""))) {
        	 log.error("Errore nel download crl, Response code " + method.getStatusCode() + " body " + method.getResponseBodyAsString());
        	 return null;
         }
         java.io.InputStream in = method.getResponseBodyAsStream();
         long start = System.currentTimeMillis();
         X509CRL x509crl = parse(IOUtils.toByteArray(in));
     	//long elapsedTimeMillis = System.currentTimeMillis()-start;
 		//log.info("<<<<<<< parse eseguito in " + elapsedTimeMillis + "ms");
         
         return x509crl;
    }
    
//    X509CRL ricercaCrlByProxyHTTP(String url, CryptoConfiguration configuration ) throws Exception {
//        // HostConfiguration config = new HostConfiguration();
//         //HttpClient http_client = new HttpClient();
//         
//         final HttpParams httpParams = new BasicHttpParams();
//         HttpConnectionParams.setConnectionTimeout(httpParams, 30000);
//         HttpClient http_client = new DefaultHttpClient(httpParams);
//         
//         
//     	if (configuration!=null && StringUtils.isNotEmpty(configuration.getProxyHost()) ) {
//     		
//     		if (StringUtils.isNotEmpty(configuration.getProxyUser()) ) {
// 		        //Credentials credential = new UsernamePasswordCredentials(configuration.getProxyUser(),configuration.getProxyPassword());
// 		       // AuthScope scope = new AuthScope(configuration.getProxyHost(), configuration.getProxyPort());
// 		       // http_client.getState().setProxyCredentials(scope, credential);
//     			//HttpHost proxy = new HttpHost(configuration.getProxyHost(), configuration.getProxyPort()); 
//     			//http_client.getParams().setParameter(ConnRoutePNames.DEFAULT_PROXY,proxy);
//// 		        http_client.getHostConfiguration().setProxy(configuration.getProxyHost(), configuration.getProxyPort());
//// 	        	http_client.getState().setProxyCredentials(AuthScope.ANY,new UsernamePasswordCredentials(configuration.getProxyUser(), configuration.getProxyPassword()));
//     			
//     			
//     			List<String> authpref = new ArrayList<String>();
//     			authpref.add(AuthPolicy.BASIC);
//     			httpClient.getParams().setParameter(AuthPNames.PROXY_AUTH_PREF, authpref);
//     			
//     			CredentialsProvider credsProvider = http_client.getCredentialsProvider();
// 				http_client.setCredentialsProvider(credsProvider);
//     			
//     		}
// 	        
// 	        //config.setProxy(configuration.getProxyHost(), configuration.getProxyPort());
//     	}
//     	    	
//        // HttpMethodBase method = new PostMethod(url);
//         //http_client.executeMethod(config, method);
//         HttpPost httpost = new HttpPost(url);
//         http_client.execute( httpost);
//         
//         //if (method.getStatusCode() !=HttpURLConnection.HTTP_OK) {
//         if (httpost.getStatusCode() !=HttpURLConnection.HTTP_OK) {
//         	//method = new GetMethod(url);
//         	//http_client.executeMethod(config, method);
//         	HttpGet httget = new HttpGet(url);
//         	http_client.execute( httget);
//         }
//         java.io.InputStream in = method.getResponseBodyAsStream();
//         return parse(IOUtils.toByteArray(in));
//     }
    
    /**
     * Recupera le CRL tramite il protocollo LDAP
     * @param url
     * @return
     * @throws Exception
     */
    X509CRL searchCrlByLDAP(String url) throws Exception {
    	String ldapUrl = url;
    	X509CRL ret = null;
	    log.debug("Scarico ldap da " + url);
    	if (ldapUrl.toLowerCase().indexOf("?certificaterevocationlist") < 0) {
	        ldapUrl = ldapUrl + "?certificaterevocationlist";
	    }
	    try {
	    	Hashtable<String, String> environment = new Hashtable<String, String>();
	    	if (timeout != null && !timeout.equalsIgnoreCase("")) {
	    		try {
		        	Integer timeoutInt = Integer.parseInt(timeout);
			        //log.debug("Imposto il timeout a " + timeout +" msec");
			        environment.put("com.sun.jndi.ldap.connect.timeout", ""+timeoutInt);
	        	} catch(Exception e){
	        		log.error("Errore nel settaggio del timeout " + timeout + " - " + e.getMessage() );
	        	}
			}
	    	DirContext ctx = new InitialDirContext(environment);
		    NamingEnumeration ne = ctx.search(ldapUrl, "", null);
		    if (ne.hasMore()) {
		        ctx.close();
			    javax.naming.directory.Attributes attribs = ((SearchResult) ne.next()).getAttributes();
			    Attribute a = null;
			    for (NamingEnumeration ae = attribs.getAll(); ae.hasMore(); ) {
			       a = (Attribute) ae.next();
			       if (a.getID() != null && a.getID().toLowerCase().indexOf("certificaterevocationlist") != -1) {
			    	   ret =  parse((byte[]) a.get(0));
			    	   break;
			       }	    
			    }
		    } else {
		    	ctx.close();
		    }
	    } catch (TimeLimitExceededException e) {
	        log.error("Abbiamo superato il timeout massimo necessario a scaricare la CRL.");
	    	throw new Exception("Abbiamo superato il timeout massimo necessario a scaricare la CRL.");
	    } catch (Exception e) {
	    	log.error("Abbiamo superato il timeout massimo necessario a scaricare la CRL.");
	    	throw new Exception("Abbiamo superato il timeout massimo necessario a scaricare la CRL.");
	    } catch (Throwable e) {
	    	log.error("Abbiamo superato il timeout massimo necessario a scaricare la CRL.");
	    	throw new Exception("Abbiamo superato il timeout massimo necessario a scaricare la CRL.");
	    }
	    return ret;
    }

//******************************************************************************************************************************
// Metodi di utilita
//******************************************************************************************************************************
    
    /**
     * Parserizza l'array di byte in ingresso per recuperare la CRL
     */
    private X509CRL parse(byte[] crlEnc) throws Exception {
        if (crlEnc == null) {
            return null;
        }
        byte[] crlData;
        try {
     	   org.bouncycastle.util.encoders.Base64 dec = new org.bouncycastle.util.encoders.Base64();
     	   crlData = dec.decode(crlEnc);
        } catch (Throwable e) {
            crlData = crlEnc;
        }
        CertificateFactory cf = CertificateFactory.getInstance("X.509");
        return (X509CRL) cf.generateCRL(new ByteArrayInputStream(crlData));
    } 
    
    Vector getDERValue(ASN1Primitive derObj) { 
        if (derObj instanceof DERSequence) { 
            Vector ret = new Vector(); 
            DERSequence seq = (DERSequence)derObj; 
            Enumeration enumra = seq.getObjects(); 
            while (enumra.hasMoreElements()) {
            	ASN1Primitive nestedObj = (ASN1Primitive)enumra.nextElement(); 
                Vector appo = getDERValue(nestedObj); 
                if (appo != null) { 
                   ret.addAll(appo); 
                } 
            } 
            return ret; 
        }
    	if (derObj instanceof DLSequence) { 
            Vector ret = new Vector(); 
            DLSequence seq = (DLSequence)derObj; 
            Enumeration enumra = seq.getObjects(); 
            while (enumra.hasMoreElements()) {
            	ASN1Primitive nestedObj = (ASN1Primitive)enumra.nextElement(); 
                Vector appo = getDERValue(nestedObj); 
                if (appo != null) { 
                   ret.addAll(appo); 
                } 
            } 
            return ret; 
        } 

        if (derObj instanceof DERTaggedObject) { 
            DERTaggedObject derTag = (DERTaggedObject)derObj;
            ASN1Primitive object = derTag.getObject();
            if (derTag.isExplicit()&&!derTag.isEmpty() || ! (object instanceof DEROctetString) ) { 
                Vector ret = getDERValue(object); 
                return ret; 
            } else {
                DEROctetString derOct = (DEROctetString)object; 
                String val = new String(derOct.getOctets()); 
                Vector ret = new Vector(); 
                ret.add(val); 
                return ret; 
            } 
        } 
        return null; 
    }

	public String getTimeout() {
		return timeout;
	}

	public void setTimeout(String timeout) {
		this.timeout = timeout;
	} 

    
}
