/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.ByteArrayInputStream;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigInteger;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.SocketAddress;
import java.net.URL;
import java.net.URLConnection;
import java.security.GeneralSecurityException;
import java.security.MessageDigest;
import java.security.Provider;
import java.security.Security;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.bouncycastle.asn1.ASN1EncodableVector;
import org.bouncycastle.asn1.ASN1InputStream;
import org.bouncycastle.asn1.ASN1ObjectIdentifier;
import org.bouncycastle.asn1.cmp.PKIFailureInfo;
import org.bouncycastle.asn1.cms.AttributeTable;
import org.bouncycastle.cms.CMSSignedDataParser;
import org.bouncycastle.cms.SignerInformation;
import org.bouncycastle.cms.SignerInformationStore;
import org.bouncycastle.tsp.TSPException;
import org.bouncycastle.tsp.TimeStampRequest;
import org.bouncycastle.tsp.TimeStampRequestGenerator;
import org.bouncycastle.tsp.TimeStampResponse;
import org.bouncycastle.tsp.TimeStampToken;
import org.bouncycastle.tsp.TimeStampTokenInfo;

import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.error_messages.MessageLocalization;
import com.itextpdf.text.pdf.AcroFields;
import com.itextpdf.text.pdf.PdfDictionary;
import com.itextpdf.text.pdf.PdfName;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfSignature;
import com.itextpdf.text.pdf.PdfSignatureAppearance;
import com.itextpdf.text.pdf.PdfStamper;
import com.itextpdf.text.pdf.PdfString;
import com.itextpdf.text.pdf.codec.Base64;
import com.itextpdf.text.pdf.security.CrlClient;
import com.itextpdf.text.pdf.security.CrlClientOnline;
import com.itextpdf.text.pdf.security.DigestAlgorithms;
import com.itextpdf.text.pdf.security.LtvVerification;
import com.itextpdf.text.pdf.security.OcspClient;
import com.itextpdf.text.pdf.security.OcspClientBouncyCastle;
import com.itextpdf.text.pdf.security.PdfPKCS7;
import com.itextpdf.text.pdf.security.TSAClient;
import com.itextpdf.text.pdf.security.TSAClientBouncyCastle;

import it.eng.job.AurigaSpringContext;
import it.eng.job.SpringHelper;
import it.eng.job.aggiungiMarca.bean.MarcaConfigBean;
import it.eng.spring.utility.SpringAppContext;
import it.eng.utility.proxy.ProxyManager;


public class TsrGenerator extends TSAClientBouncyCastle{
	
	private static final Logger log = Logger.getLogger(TsrGenerator.class);
	private String url;
	private boolean useAuth;
	private String username;
	private String password;
	
	private Proxy proxy = null;
	private String encodedUserPwd = null;
 
	/**
	 * @param url
	 * @param useAuth
	 * @param username
	 * @param password
	 */
	public TsrGenerator(String url, String username, String password) {
		
		super(url,username, password);
		
		this.url = url;
		this.useAuth = true;
		this.username = username;
		this.password = password;
	}
	
	/**
	 * @param url
	 * @param useAuth
	 * @param username
	 * @param password
	 */
	public TsrGenerator(String url, String username, String password, boolean useAuth) {
		
		super(url,username, password);
		
		this.url = url;
		this.useAuth = useAuth;
		this.username = username;
		this.password = password;
	}

	/**
	 * @return the url
	 */
	public String getUrl() {
		return url;
	}

	/**
	 * @param url the url to set
	 */
	public void setUrl(String url) {
		this.url = url;
	}

	/**
	 * @return the useAuth
	 */
	public boolean isUseAuth() {
		return useAuth;
	}

	/**
	 * @param useAuth the useAuth to set
	 */
	public void setUseAuth(boolean useAuth) {
		this.useAuth = useAuth;
	}

	/**
	 * @return the username
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * @param username the username to set
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	private TimeStampToken getMarca(byte[] digest)throws Exception{
		log.info("ask mark to:"+url);
		
		TimeStampToken tst;
		try {
			tst = generateTsrData(digest );
		} catch (Exception e) {
			log.error("generazione marca fallita" );
			return null;
			//throw new Exception("generazione marca fallita",e);
		}
		return tst;
	}
	
	private TimeStampToken generateTsrData(byte[] fingerprint)throws Exception
	{
		TimeStampToken tst=null;
		log.info("Url server marca  " + url);
		TSAClientBouncyCastle tsc = null;
		if( useAuth ){
			log.info("tsaUser: " + username + " tsaPass:" +password);
			tsc = new TSAClientBouncyCastle(url, username, password);
		} else {
			tsc = new TSAClientBouncyCastle(url);
		}
		
		ProxyManager proxyManager = SpringHelper.getSpecializedBean("aurigaProxyManager", null, ProxyManager.class);
		log.info("proxyManager.needProxy() " + proxyManager.needProxy());
        if(proxyManager.needProxy()){
			SocketAddress socketAddress = new InetSocketAddress(proxyManager.getProxySetter().getHost(), Integer.valueOf(proxyManager.getProxySetter().getPort()));
			proxy = new Proxy(Proxy.Type.HTTP, socketAddress);
			//encodedUserPwd = new String (Base64.encode(new String(proxyManager.getProxySetter().getUser() + ":" + proxyManager.getProxySetter().getPassword()).getBytes()));
			if(proxyManager.getProxySetter().getUser()!=null && !proxyManager.getProxySetter().getUser().equalsIgnoreCase(""))
				encodedUserPwd = Base64.encodeBytes(new String(proxyManager.getProxySetter().getUser() + ":" + proxyManager.getProxySetter().getPassword()).getBytes());
			else 
				encodedUserPwd = null;
			log.info("encodedUserPwd " + encodedUserPwd);
		}
		
		//ProxyManager proxyManager = (ProxyManager) SpringAppContext.getContext().getBean("aurigaProxyManager");
		/*if(proxyManager.needProxy()){
			SocketAddress socketAddress = 	 new InetSocketAddress(proxyManager.getProxySetter().getHost(), Integer.valueOf(proxyManager.getProxySetter().getPort()));
			proxy = new Proxy(Proxy.Type.HTTP, socketAddress);
			//encodedUserPwd = new String (Base64.encode(new String(proxyManager.getProxySetter().getUser() + ":" + proxyManager.getProxySetter().getPassword()).getBytes()));
			
			encodedUserPwd = Base64.encodeBytes(new String(proxyManager.getProxySetter().getUser() + ":" + proxyManager.getProxySetter().getPassword()).getBytes());
			
		}*/
		
		byte[] timeStampTokenEncoded=getTimeStampToken(fingerprint);
		tst=new TimeStampToken(CMSUtils.getCMSSignedData(timeStampTokenEncoded));
        return tst;
	}
	
	@Override
	public byte[] getTimeStampToken(byte[] imprint) throws IOException,	TSPException {
		
		byte[] respBytes = null;
        // Setup the time stamp request
        TimeStampRequestGenerator tsqGenerator = new TimeStampRequestGenerator();
        tsqGenerator.setCertReq(true);
        // tsqGenerator.setReqPolicy("1.3.6.1.4.1.601.10.3.1");
        BigInteger nonce = BigInteger.valueOf(System.currentTimeMillis());
        TimeStampRequest request = tsqGenerator.generate(new ASN1ObjectIdentifier(DigestAlgorithms.getAllowedDigests(digestAlgorithm)), imprint, nonce);
        byte[] requestBytes = request.getEncoded();
        
        // Call the communications layer
        respBytes = getTSAResponse(requestBytes);
        
        // Handle the TSA response
        TimeStampResponse response = new TimeStampResponse(respBytes);
        
        // validate communication level attributes (RFC 3161 PKIStatus)
        response.validate(request);
        PKIFailureInfo failure = response.getFailInfo();
        int value = (failure == null) ? 0 : failure.intValue();
        if (value != 0) {
            // @todo: Translate value of 15 error codes defined by PKIFailureInfo to string
            throw new IOException(MessageLocalization.getComposedMessage("invalid.tsa.1.response.code.2", tsaURL, String.valueOf(value)));
        }
        // @todo: validate the time stap certificate chain (if we want
        //        assure we do not sign using an invalid timestamp).
        
        // extract just the time stamp token (removes communication status info)
        TimeStampToken  tsToken = response.getTimeStampToken();
        if (tsToken == null) {
            throw new IOException(MessageLocalization.getComposedMessage("tsa.1.failed.to.return.time.stamp.token.2", tsaURL, response.getStatusString()));
        }
        TimeStampTokenInfo tsTokenInfo = tsToken.getTimeStampInfo(); // to view details
        byte[] encoded = tsToken.getEncoded();

        if (tsaInfo != null) {
        	tsaInfo.inspectTimeStampTokenInfo(tsTokenInfo);
        }
        // Update our token size estimate for the next call (padded to be safe)
        this.tokenSizeEstimate = encoded.length + 32;
        return encoded;
		
	}
	
	@Override
	protected byte[] getTSAResponse(byte[] requestBytes) throws IOException {
			
        // Setup the TSA connection
        URL url = new URL(tsaURL);
        URLConnection tsaConnection = null ;
        
        //ProxyManager proxyManager = (ProxyManager) SpringAppContext.getContext().getBean("aurigaProxyManager");
        ProxyManager proxyManager = SpringHelper.getSpecializedBean("aurigaProxyManager", null, ProxyManager.class);
        log.info("proxyManager.needProxy() " + proxyManager.needProxy());
        if(proxyManager.needProxy()){
			SocketAddress socketAddress = new InetSocketAddress(proxyManager.getProxySetter().getHost(), Integer.valueOf(proxyManager.getProxySetter().getPort()));
			proxy = new Proxy(Proxy.Type.HTTP, socketAddress);
			//encodedUserPwd = new String (Base64.encode(new String(proxyManager.getProxySetter().getUser() + ":" + proxyManager.getProxySetter().getPassword()).getBytes()));
			if(proxyManager.getProxySetter().getUser()!=null && !proxyManager.getProxySetter().getUser().equalsIgnoreCase(""))
				encodedUserPwd = Base64.encodeBytes(new String(proxyManager.getProxySetter().getUser() + ":" + proxyManager.getProxySetter().getPassword()).getBytes());
			else 
				encodedUserPwd = null;
			log.info("encodedUserPwd " + encodedUserPwd);
		}
        
        
        if (proxy == null)
        	
        	tsaConnection = (URLConnection) url.openConnection();
        else{
        	tsaConnection = (URLConnection) url.openConnection(proxy);
        	if(encodedUserPwd!=null)
        		tsaConnection.setRequestProperty("Proxy-Authorization", "Basic "+ encodedUserPwd);
        }
        tsaConnection.setDoInput(true);
        tsaConnection.setDoOutput(true);
        tsaConnection.setUseCaches(false);
        tsaConnection.setRequestProperty("Content-Type", "application/timestamp-query");
        //tsaConnection.setRequestProperty("Content-Transfer-Encoding", "base64");
        tsaConnection.setRequestProperty("Content-Transfer-Encoding", "binary");


        if ((tsaUsername != null) && !tsaUsername.equals("") ) {
            String userPassword = tsaUsername + ":" + tsaPassword;
            //tsaConnection.setRequestProperty("Authorization", "Basic " +
            //    new String(new sun.misc.BASE64Encoder().encode(userPassword.getBytes())));
            String encuserPassword =  Base64.encodeBytes(userPassword.getBytes());
            tsaConnection.setRequestProperty("Authorization", "Basic " +
					encuserPassword );
        };

        OutputStream out = tsaConnection.getOutputStream();
        out.write(requestBytes);
        out.close();

        // Get TSA response as a byte array
        InputStream inp = tsaConnection.getInputStream();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int bytesRead = 0;
        while ((bytesRead = inp.read(buffer, 0, buffer.length)) >= 0) {
            baos.write(buffer, 0, bytesRead);
        }

        byte[] respBytes = baos.toByteArray();

        String encoding = tsaConnection.getContentEncoding();
        if (encoding != null && encoding.equalsIgnoreCase("base64")) {
            respBytes = Base64.decode(new String(respBytes));
	     	//sun.misc.BASE64Decoder dec = new sun.misc.BASE64Decoder();
			//respBytes = dec.decodeBuffer(new String(respBytes));
        }
        return respBytes;

    }
		
	
	public byte[] addMarca(File fileDaMarcare) throws Exception{		
		Provider provider = Security.getProvider("BC");
		log.info("Prima lettura provider: " + provider);

		//if( provider==null )
			java.security.Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
		provider = Security.getProvider("BC");
		log.info("Seconda lettura provider: " + provider);
		
		//TODO non generare una marca per ogni signerinfo
		log.info("addMarca START file:"+fileDaMarcare);
		//ProxyUtil.initProxyConnection();
		CMSSignedDataParser cmsSignedData= CMSUtils.getCMSSignedDataParser(fileDaMarcare);
    	cmsSignedData.getSignedContent().drain();
    	Collection  signers = (Collection )cmsSignedData.getSignerInfos().getSigners();
    	List<SignerInformation> newSigners=new ArrayList<SignerInformation>();
    	
    	for (Object signer: signers) {
			if(signer instanceof SignerInformation){
				SignerInformation marca = addMarca((SignerInformation)signer);
					if( marca==null )
						return null;
					newSigners.add( marca );				
			}
    	}    
    	
    	File fileMarcato = File.createTempFile("tempMarca", "");
    	FileOutputStream fos = new FileOutputStream(fileMarcato);
    	OutputStream stream=CMSSignedDataParser.replaceSigners(FileUtils.openInputStream(fileDaMarcare), new SignerInformationStore(newSigners), fos);
    	//TODO CMSSignedDataParser.replaceCertificatesAndCRLs(original, certs, crls, attrCerts, out)
    	IOUtils.closeQuietly(stream);
    	log.info("addMarca END file:"+fileDaMarcare);
    	
    	byte[] byteFileMarcato = getFileBytes(fileMarcato);
    	
    	fileMarcato.delete();
    	
    	if(byteFileMarcato == null) {
    		throw new Exception("Il servizio per la creazione della marca temporale non è al momento disponibile");
    	}
    	
    	return byteFileMarcato; 
	}
	
	public byte[] addMarcaPdf(File fileDaMarcare) throws Exception {
		Provider provider = Security.getProvider("BC");
		log.info("Prima lettura provider: " + provider);

		//if( provider==null )
			java.security.Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
		provider = Security.getProvider("BC");
		log.info("Seconda lettura provider: " + provider);
		
		log.info("fileDaMarcare " + fileDaMarcare);
		PdfReader r = new PdfReader(FileUtils.openInputStream(fileDaMarcare));
		File fileMarcato = File.createTempFile("tmpMARCA.pdf", "");
		log.info("fileMarcato " + fileMarcato);
		    
		FileOutputStream fos = new FileOutputStream(fileMarcato);
		log.info("creo lo stream sl marcato " );
		
		PdfStamper stp = PdfStamper.createSignature(r, fos, '\0', null, true);
		log.info("creo stp " +stp);
		
		LtvVerification v = stp.getLtvVerification();
		log.info("getLtvVerification " +v);
		
		AcroFields fields = stp.getAcroFields();
		List<String> names = fields.getSignatureNames();
		
		PdfPKCS7 pkcs7 = null;
		String sigName = null;
		try{
			if( names==null || names.size()==0){
				log.info("Il file non e' firmato");
				return null;
			}  else {
				for(String name : names){
					log.info("name:: " + name);
				}
			    sigName = names.get(names.size() - 1);
			    log.info("sigName " + sigName);
				
			    pkcs7 = fields.verifySignature(sigName);
			    log.info("dopo la verifica  " + pkcs7);
			}
		} catch(Throwable e){
			log.error("Errore durante la verifica ",e);
			//e.printStackTrace();
		}
	    
	    OcspClient ocsp = new OcspClientBouncyCastle();
	    CrlClient crl = new CrlClientOnline();
	    log.info("crl  " + crl);
		
	    if( pkcs7!=null) {
	    	log.info("pkcs7.isTsp()  " + pkcs7.isTsp());
			if (pkcs7!=null && pkcs7.isTsp()) 
		      	v.addVerification(sigName, ocsp, crl, LtvVerification.CertificateOption.SIGNING_CERTIFICATE, 
		       			LtvVerification.Level.OCSP_CRL, 
		       			LtvVerification.CertificateInclusion.NO);
		    else {
		       	for (String name : names) {
		       		v.addVerification(name, ocsp, crl, 
		       				LtvVerification.CertificateOption.WHOLE_CHAIN, 
		       				LtvVerification.Level.OCSP_CRL, 
		       				LtvVerification.CertificateInclusion.NO);
		       	}
		    }
		}/**/
	    PdfSignatureAppearance sap = stp.getSignatureAppearance();
	        
	    log.info("Invoco la url " + url + " username " + username + " password "+ password)  ;
	    TSAClient tsa = new TSAClientBouncyCastle(url, username, password, /*6500*/4096, "SHA-256");//"SHA512");
	    
	    timestamp(sap, tsa, "marca"); 
	    //LtvTimestamp.timestamp(sap, tsa, "marca"); 
	        
	    byte[] byteFileMarcato = getFileBytes(fileMarcato);
		fileMarcato.delete();

		return byteFileMarcato;
	    
	}
	
	public  void timestamp(PdfSignatureAppearance sap, TSAClient tsa, String signatureName) throws IOException, DocumentException, GeneralSecurityException {
		int contentEstimated = tsa.getTokenSizeEstimate();
		log.info("signatureName " + signatureName);
		sap.setVisibleSignature(new Rectangle(0,0,0,0), 1, signatureName);

	    PdfSignature dic = new PdfSignature(PdfName.ADOBE_PPKLITE, PdfName.ETSI_RFC3161);
	    dic.put(PdfName.TYPE, PdfName.DOCTIMESTAMP);
	    sap.setCryptoDictionary(dic);

	    HashMap<PdfName,Integer> exc = new HashMap<PdfName,Integer>();
	    exc.put(PdfName.CONTENTS, new Integer(contentEstimated * 2 + 2));
	    sap.preClose(exc);
	    InputStream data = sap.getRangeStream();
	    MessageDigest messageDigest = tsa.getMessageDigest();
	    byte[] buf = new byte[4096];
	    int n;
	    while ((n = data.read(buf)) > 0) {
	    	messageDigest.update(buf, 0, n);
	    }
	    byte[] tsImprint = messageDigest.digest();
	    
	    byte[] tsToken;
	    try {
	    	//tsToken = tsa.getTimeStampToken(tsImprint);
	        tsToken = getTimeStampToken(tsImprint);
	    }
	    catch(Exception e) {
	    	throw new GeneralSecurityException(e);
	    }

	    if (contentEstimated + 2 < tsToken.length)
	    throw new IOException("Not enough space");

	    byte[] paddedSig = new byte[contentEstimated];
	    System.arraycopy(tsToken, 0, paddedSig, 0, tsToken.length);

	    PdfDictionary dic2 = new PdfDictionary();
	    dic2.put(PdfName.CONTENTS, new PdfString(paddedSig).setHexWriting(true));
	    sap.close(dic2);
	}
	
	private static byte[] getFileBytes( File file  ){
		byte[] bFile = new byte[(int) file.length()];

		try {
			//convert file into array of bytes
			FileInputStream fileInputStream = new FileInputStream(file);
			fileInputStream.read(bFile);
			fileInputStream.close();

		}catch(Exception e){
			log.warn(e);
		}
		return bFile;
	}
	
	private SignerInformation addMarca(SignerInformation input )throws Exception{
		AttributeTable table  = input.getUnsignedAttributes();
		if (table==null)
			table= new AttributeTable(new ASN1EncodableVector());
		//lista degli attuali attributi non firmati
		//ASN1EncodableVector vect=table.toASN1EncodableVector();
		//lista delle attuali marche
		//ASN1EncodableVector marche=table.getAll(new DERObjectIdentifier("1.2.840.113549.1.9.16.2.14"));
		//crea una nuova marca per la firma specificata	 
		TimeStampToken tst=getMarca(DigestUtils.sha256(input.getSignature()));
		if( tst==null ){
			return null;
		}
		ByteArrayInputStream bytearrayinputstream = new ByteArrayInputStream(tst.getEncoded());
		ASN1InputStream asn1inputstream = new ASN1InputStream(bytearrayinputstream);
		//Attribute marca= new Attribute(new DERObjectIdentifier("1.2.840.113549.1.9.16.2.14"),new DERSet( asn1inputstream.readObject()    ));
		AttributeTable replaced=table.add( new ASN1ObjectIdentifier("1.2.840.113549.1.9.16.2.14"),  asn1inputstream.readObject()     );

		//  	        marche.add(marca);
		//  	        //aggiungo le marche agli attributi non firmati
		//  	       for (int i = 0; i < marche.size(); i++) {
		//  	    	 vect.add(marche.get(i));
		//  	       }
		return SignerInformation.replaceUnsignedAttributes(input, replaced);
	}
	
	public static void main(String[] args) {
		File file = new File("C:/Users/Anna Tresauro/Desktop/csi/cmto/file1.2_repaired.pdf");
		
		try {
			PDDocument document = PDDocument.load(file);
			
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		
		try {
			PdfReader r = new PdfReader(FileUtils.openInputStream(file));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		/*TsrGenerator g = new TsrGenerator("", "", "");
		try {
			g.addMarca(file);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		
		
	}
}

