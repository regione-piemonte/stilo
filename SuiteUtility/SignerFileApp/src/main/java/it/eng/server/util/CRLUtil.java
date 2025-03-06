package it.eng.server.util;

import it.eng.common.LogWriter;
import it.eng.proxyselector.http.ProxyDefaultHttpClient;
import it.eng.server.SignerServlet;

import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.net.URI;
import java.security.Security;
import java.security.cert.CertificateFactory;
import java.security.cert.X509CRL;
import java.security.cert.X509Certificate;
import java.util.Enumeration;
import java.util.Vector;

import javax.naming.NamingEnumeration;
import javax.naming.directory.Attribute;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;
import javax.naming.directory.SearchResult;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.methods.RequestBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.bouncycastle.asn1.ASN1InputStream;
import org.bouncycastle.asn1.ASN1Primitive;
import org.bouncycastle.asn1.DEROctetString;
import org.bouncycastle.asn1.DERSequence;
import org.bouncycastle.asn1.DERTaggedObject;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

public class CRLUtil {
 
	
	public static void main(String[] args) throws Exception {
		SignerServlet.configuration.setProxyHost("proxy.eng.it");
		SignerServlet.configuration.setProxyPort(3128);
		SignerServlet.configuration.setProxyUser("mirigo");
		SignerServlet.configuration.setProxyPassword("fv54kagz");
		Security.addProvider(new BouncyCastleProvider());
		CertificateFactory factorys = CertificateFactory.getInstance("X509", BouncyCastleProvider.PROVIDER_NAME);
		X509Certificate certificate = (X509Certificate)factorys.generateCertificate(new FileInputStream("C:\\DecretoDD-Esempio-ARUBAKEY.pdf.p7m"));
		CRLUtil util = new CRLUtil();
		String url = util.getURLCrlDistributionPoint(certificate);
		System.out.println(url);
		X509CRL crl = util.getCrlByURL(url);
		System.out.println(crl);
	}
		
	public boolean isValidCertificate(X509Certificate certificate) throws Exception{
		boolean valid = false;
		X509CRL crl = null;
		try{
			//controllo la validita' del certificato
			certificate.checkValidity();
			
			//Recupero l'URL del certificato
			String url = this.getURLCrlDistributionPoint(certificate);
			crl = this.getCrlByURL(url);
			
		}catch(Exception e){
			valid = false;
			throw new Exception("Impossibile verificare le CRL!");
		}
		//Controllo al validita' del certificato
		valid = !crl.isRevoked(certificate);
		return valid;
	}
	
	
	/**
	 * Recupera la CRL in base all'url passato in ingresso
	 * @param url
	 * @return
	 * @throws CryptoSignerException
	 */
	private X509CRL getCrlByURL(String url) throws Exception{
		LogWriter.writeLog("getCrlByURL START");
		try{
			CRLUtil util = new CRLUtil();
			X509CRL crl = null;
			url = StringUtils.trim(url);
			LogWriter.writeLog("URL:"+url);
			if(url.toUpperCase().startsWith("LDAP")){
				crl = util.searchCrlByLDAP(url);
			}else if(url.toUpperCase().startsWith("HTTP")){
				crl = util.ricercaCrlByProxyHTTP(url, SignerServlet.configuration);
			}else{
				throw new Exception("Protocollo di comunicazione non supportato!");
			}
			LogWriter.writeLog("getCrlByURL END");
			return crl;
		}catch(Exception e){
			LogWriter.writeLog("Errore getCrlByURL", e);
			throw e;		
		}
	}
	
	/**
	 * Recupera un vettore contenente i distribution point CRL del certificato passato in ingresso
	 * @param certificate
	 * @return
	 * @throws CryptoSignerException
	 */
	private String getURLCrlDistributionPoint(X509Certificate certificate)throws Exception{
        try{ 
       	 	byte[] val1 = certificate.getExtensionValue("2.5.29.31"); 
           ASN1InputStream oAsnInStream = new ASN1InputStream(new ByteArrayInputStream(val1)); 
           ASN1Primitive derObj = oAsnInStream.readObject(); 
           DEROctetString dos = (DEROctetString)derObj; 
           byte[] val2 = dos.getOctets(); 
           ASN1InputStream oAsnInStream2 = new ASN1InputStream(new ByteArrayInputStream(val2)); 
           ASN1Primitive derObj2 = oAsnInStream2.readObject(); 
           CRLUtil util = new CRLUtil();
           Vector urls = util.getDERValue(derObj2); 
           if(urls!=null && !urls.isEmpty()){
        	   return (String)urls.get(0);
           }else{
        	  throw new Exception("Lista delle distribution point vuota o nulla"); 
           }
       }catch (Exception e) { 
           throw new Exception("Errore nel recupero del distribution point della CRL",e); 
       } 
	}
	
	/**
	 * Recupera le CRL tramite il protocollo HTTP/HTTPS
	 * @param url
	 * @param auth
	 * @return
	 * @throws Exception
	 */
    private X509CRL ricercaCrlByProxyHTTP(String url,CryptoConfiguration configuration) throws Exception{
    	CloseableHttpClient lClient = ProxyDefaultHttpClient.getClientToUse();
       	HttpUriRequest upload = RequestBuilder.get()
        .setUri(new URI(url))
        .build();
       	CloseableHttpResponse lCloseableHttpResponse = lClient.execute(upload);
       	return parse(IOUtils.toByteArray(lCloseableHttpResponse.getEntity().getContent()));
    }
    
    /**
     * Recupera le CRL tramite il protocollo LDAP
     * @param url
     * @return
     * @throws Exception
     */
    private X509CRL searchCrlByLDAP(String url) throws Exception{
    	String ldapUrl = url;
    	X509CRL ret = null;
	    if (ldapUrl.toLowerCase().indexOf("?certificaterevocationlist") < 0) {
	        ldapUrl = ldapUrl + "?certificaterevocationlist";
	    }
	    DirContext ctx = new InitialDirContext();
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
	    }else{
	    	ctx.close();
	    }
	    return ret;
    }

//******************************************************************************************************************************
// Metodi di utilit�
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
    

    private Vector getDERValue(ASN1Primitive derObj){ 
        if (derObj instanceof DERSequence){ 
            Vector ret = new Vector(); 
            DERSequence seq = (DERSequence)derObj; 
            Enumeration enumra = seq.getObjects(); 
            while (enumra.hasMoreElements()){ 
            	ASN1Primitive nestedObj = (ASN1Primitive)enumra.nextElement(); 
                Vector appo = getDERValue(nestedObj); 
                if (appo != null){ 
                   ret.addAll(appo); 
                } 
            } 
            return ret; 
        } 

        if (derObj instanceof DERTaggedObject){ 
            DERTaggedObject derTag = (DERTaggedObject)derObj; 
            if(derTag.isExplicit()&&!derTag.isEmpty()){ 
            	ASN1Primitive nestedObj = derTag.getObject(); 
                Vector ret = getDERValue(nestedObj); 
                return ret; 
            } else { 
                DEROctetString derOct = (DEROctetString)derTag.getObject(); 
                String val = new String(derOct.getOctets()); 
                Vector ret = new Vector(); 
                ret.add(val); 
                return ret; 
            } 
        } 
        return null; 
    } 
	
}