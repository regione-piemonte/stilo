 package it.eng.server;

import it.eng.client.applet.operation.jsignpdf.HashAlgorithm;
import it.eng.common.CMSUtils;
import it.eng.common.bean.SignerObjectBean;
import it.eng.common.type.SignerType;
import it.eng.common.type.TabType;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.Security;
import java.security.cert.CertStore;
import java.security.cert.Certificate;
import java.security.cert.CollectionCertStoreParameters;
import java.security.cert.X509CRL;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.bouncycastle.asn1.ASN1Encoding;
import org.bouncycastle.asn1.ASN1InputStream;
import org.bouncycastle.asn1.ASN1ObjectIdentifier;
import org.bouncycastle.asn1.ASN1OctetString;
import org.bouncycastle.asn1.ASN1Set;
import org.bouncycastle.asn1.BERConstructedOctetString;
import org.bouncycastle.asn1.DERSet;
import org.bouncycastle.asn1.cms.CMSObjectIdentifiers;
import org.bouncycastle.asn1.cms.ContentInfo;
import org.bouncycastle.asn1.cms.SignedData;
import org.bouncycastle.cms.CMSProcessable;
import org.bouncycastle.cms.CMSProcessableFile;
import org.bouncycastle.cms.CMSSignedData;
import org.bouncycastle.cms.CMSSignedGenerator;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.AcroFields;
import com.itextpdf.text.pdf.PdfDate;
import com.itextpdf.text.pdf.PdfDictionary;
import com.itextpdf.text.pdf.PdfName;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfSignatureAppearance;
import com.itextpdf.text.pdf.PdfStamper;
import com.itextpdf.text.pdf.PdfString;

public class SignerUtil {

	//public static final String FILE_UNSIGNED = "@p378FileSigner";
	//public static final String DIGEST_SIGNER = "@p379DigestSigner";
	//public static final String TYPE_SIGNER = "@p380TypeSigner";
	//public static final String TYPE_SIGNERS_LIST = "@p380TypesSigners";
		
	public static final String SESSION_SIGNER = "@p379SessionSigner937733";
	
	protected static MessageBean messagebean = new MessageBean();

	private HttpSession session;
	
	public SignerUtil(HttpSession session,HashAlgorithm digest,boolean clearOld){
		this.session = session;
		if(clearOld){
			clear();
		}
		SignerSessionBean bean = (SignerSessionBean)this.session.getAttribute(SESSION_SIGNER);
		if(bean == null){
			bean = new SignerSessionBean();
		}
				
		bean.setDigest(digest);
		bean.setSignerType(SignerType.P7M);
		bean.setSignerTypes(new SignerType[]{SignerType.P7M});
		this.session.setAttribute(SESSION_SIGNER, bean);
	}
	
	public void clear(){
		this.session.removeAttribute(SESSION_SIGNER);
	}
	
	public boolean isVerifyCRL(){
		SignerSessionBean bean = (SignerSessionBean)this.session.getAttribute(SESSION_SIGNER);
		return bean.isVerifyCRL();
	}
	
	public void verifyCRL(boolean verify){
		SignerSessionBean bean = (SignerSessionBean)this.session.getAttribute(SESSION_SIGNER);
		bean.setVerifyCRL(verify);
		session.setAttribute(SESSION_SIGNER,bean);
	}
	
	
	private SignerUtil(){}
	
	public static SignerUtil getSignerUtil(HttpSession session){
		SignerUtil util = new SignerUtil();
		util.session = session;
		Security.addProvider(new BouncyCastleProvider());
		return util;
	}

	protected void signerP7M(SignerObjectBean bean)throws Exception{
		ASN1Set certificates = null;
		
		
		List _certs = new ArrayList();
		List certList = new ArrayList();
		List _crls = new ArrayList();
		List _crlsList = new ArrayList();
				
        for(X509Certificate certificate:bean.getCertificates()){
			certList.add(certificate);
		}
	
        CertStore certStore = CertStore.getInstance("Collection", new CollectionCertStoreParameters(certList), "BC");
		_certs.addAll(CMSUtils.getCertificatesFromStore(certStore));
		_crls.addAll(CMSUtils.getCRLsFromStore(certStore));
        
		if (_certs.size() != 0)
        {
            certificates = CMSUtils.createBerSetFromList(_certs);
        }
        
        ASN1Set certrevlist = null;
      
        if (_crls.size() != 0)
        {
            certrevlist = CMSUtils.createBerSetFromList(_crls);
        }
                     
        ByteArrayOutputStream bOut = new ByteArrayOutputStream();
        
        //Cerco il contenuto del file
        List<FileElaborate> files = ((SignerSessionBean)session.getAttribute(SESSION_SIGNER)).getFiles();
        
        FileElaborate ret = null;
        for(FileElaborate file:files){
        	if(file.getId().equals(bean.getId())){
        		ret = file;
        		break;
        	}
        }
               
        CMSProcessable content = new CMSProcessableFile(ret.getUnsigned());
        ASN1OctetString octs = new BERConstructedOctetString(FileUtils.readFileToByteArray(ret.getUnsigned()));
               
        ContentInfo encInfo = new ContentInfo(new ASN1ObjectIdentifier(CMSSignedGenerator.DATA), octs);
        
        DERSet digest = (DERSet)new ASN1InputStream(bean.getDigestAlgs()).readObject();
        DERSet signerInfos = (DERSet)new ASN1InputStream(bean.getSignerInfo()).readObject();
        
        
        SignedData  sd = new SignedData(
        						 digest,
                                 encInfo, 
                                 certificates, 
                                 certrevlist, 
                                 signerInfos);
        
        
        System.out.println(sd.getEncoded(ASN1Encoding.DER).length);

        ContentInfo contentInfo = new ContentInfo(CMSObjectIdentifiers.signedData, sd);
        CMSSignedData data = new CMSSignedData(content, contentInfo);
        
        //Aggiugno il certificato alle buste
        File firmato = new File(ret.getUnsigned().getAbsolutePath()+".p7m");
        
        FileUtils.writeByteArrayToFile(firmato, data.getEncoded());
        
        if(ret.getSigned()!=null){
        	ret.getSigned().delete();
        }
        
        ret.setSigned(firmato);
        
        SignerSessionBean signersessionbean = (SignerSessionBean)session.getAttribute(SESSION_SIGNER);
        signersessionbean.setFiles(files);
        session.setAttribute(SESSION_SIGNER,signersessionbean);
	}
	
	protected void signerPDF(SignerObjectBean bean)throws Exception{
		
		//Cerco il contenuto del file
        List<FileElaborate> files = ((SignerSessionBean)session.getAttribute(SESSION_SIGNER)).getFiles();
        
        FileElaborate ret = null;
        for(FileElaborate file:files){
        	if(file.getId().equals(bean.getId())){
        		ret = file;
        		break;
        	}
        }
        PdfSignatureAppearance sap = ret.getPdfsignature();
        
        
        
        //Setto i certificati e le CRL
        bean.getCertificates().toArray(new Certificate[0]);
        
        List<X509Certificate> certificates = bean.getCertificates();
        List<X509CRL> crls = new ArrayList<X509CRL>();
              
        //TODO sap.setCrypto(null, (Certificate[])certificates.toArray(new Certificate[0]), (CRL[])crls.toArray(new CRL[0]), null);
              
        byte[] sg = bean.getSignerInfo();
		PdfDictionary dic2 = new PdfDictionary();
		byte out[] = new byte[0x2500 / 2];
		System.arraycopy(sg, 0, out, 0, sg.length);
		dic2.put(PdfName.CONTENTS, new PdfString(out).setHexWriting(true));
		sap.close(dic2);
		
		File firmato = new File(FilenameUtils.removeExtension(ret.getUnsigned().getAbsolutePath())+"_signed.pdf");
		
		FileUtils.writeByteArrayToFile(firmato, ret.getOutStream().toByteArray());
		
		ret.setSigned(firmato);
		
        SignerSessionBean signersessionbean = (SignerSessionBean)session.getAttribute(SESSION_SIGNER);
        signersessionbean.setFiles(files);
        session.setAttribute(SESSION_SIGNER,signersessionbean);
	}
	
	
	protected byte[] getHash(File file) throws IOException{
		//Recupero il digest
		HashAlgorithm digest = ((SignerSessionBean)this.session.getAttribute(SESSION_SIGNER)).getDigest();
		if(digest.equals(HashAlgorithm.SHA256)){
			InputStream stream = FileUtils.openInputStream(file);
			byte[] hash = DigestUtils.sha256(stream);
			stream.close();			
			return hash;
		}else{
			InputStream stream = FileUtils.openInputStream(file);
			byte[] hash = DigestUtils.sha(stream);
			stream.close();			
			return hash;
		}
	}
	
	protected byte[] getHashPdf(File file) throws Exception{
		List<FileElaborate> files = ((SignerSessionBean)session.getAttribute(SESSION_SIGNER)).getFiles();
		
	    FileElaborate ret = null;
	    for(FileElaborate fileE:files){
	      	if(fileE.getUnsigned().getName().equals(file.getName())){
	       		ret = fileE;
	       		break;
	       	}
	    }
		
		InputStream stream = new FileInputStream(file);
		PdfReader reader = new PdfReader(stream);
		
        AcroFields af = reader.getAcroFields();
        ArrayList<String> names = af.getSignatureNames();
    	ByteArrayOutputStream fout = new ByteArrayOutputStream();
    	PdfStamper stp = null;
    	int plot = 0;
        if(names.size()==0){
        	stp = PdfStamper.createSignature(reader, fout, '\0');
        }else{
        	stp = PdfStamper.createSignature(reader, fout, '\0',null,true);
        	plot = 120 * names.size(); 
        }
        PdfSignatureAppearance sap = stp.getSignatureAppearance();
		Rectangle rect = reader.getPageSize(1);
		float w = rect.getWidth();
		float h = rect.getHeight();
		
		
		
		// comment next line to have an invisible signature
		sap.setVisibleSignature(new Rectangle(w-20,h-20-plot,w-120,h-120-plot), 1, null);
		sap.setLayer2Text("Firma Digitale.");
		Calendar cal = Calendar.getInstance();
		PdfDictionary dic = new PdfDictionary();
		dic.put(PdfName.FT, PdfName.SIG);
		dic.put(PdfName.FILTER,PdfName.ADOBE_PPKLITE);
		dic.put(PdfName.SUBFILTER, PdfName.ADBE_PKCS7_DETACHED);
		dic.put(PdfName.M, new PdfDate(cal));
		sap.setCryptoDictionary(dic);
		HashMap exc = new HashMap();
		exc.put(PdfName.CONTENTS, new Integer(0x2502));
		sap.preClose(exc);
		HashAlgorithm digest = ((SignerSessionBean)this.session.getAttribute(SESSION_SIGNER)).getDigest();	
				
		ret.setPdfsignature(sap);
		ret.setOutStream(fout);
        SignerSessionBean signersessionbean = (SignerSessionBean)session.getAttribute(SESSION_SIGNER);
        signersessionbean.setFiles(files);
        session.setAttribute(SESSION_SIGNER,signersessionbean);
		
		if(digest.equals(HashAlgorithm.SHA256)){
			byte[] hash = DigestUtils.sha256(sap.getRangeStream());
			fout.close();
			return hash;
		}else{
			byte[] hash = DigestUtils.sha(sap.getRangeStream());
			fout.close();
			return hash;
		}
	}
	
	protected boolean isPDF(File file){
		try {
			new PdfReader(new FileInputStream(file));
			return true;
		} catch (Exception e) {
			return false;
		} 
	}
	
	public void addFile(File file,String id){
		try{
			if(!file.exists()){
				throw new Exception("il file "+file.getAbsolutePath()+" non esiste!");
			}
			
			List<FileElaborate> files = ((SignerSessionBean)session.getAttribute(SESSION_SIGNER)).getFiles();
			if(files==null){
				files = new ArrayList<FileElaborate>();
			}
			FileElaborate elaborate = new FileElaborate();
			elaborate.setUnsigned(file);
			elaborate.setId(id);
			files.add(elaborate);
	        SignerSessionBean signersessionbean = (SignerSessionBean)session.getAttribute(SESSION_SIGNER);
	        signersessionbean.setFiles(files);
	        session.setAttribute(SESSION_SIGNER,signersessionbean);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	protected void addSignerFile(InputStream stream,String id)throws Exception{
		List<FileElaborate> files = ((SignerSessionBean)session.getAttribute(SESSION_SIGNER)).getFiles();
		if(files!=null){
			for(FileElaborate elaborate:files){
				if(elaborate.getId().equals(id)){
					
					//Recupero il path del file non firmato
					String path = FilenameUtils.getFullPath(elaborate.getUnsigned().getAbsolutePath());
					File file = new File(path,elaborate.getUnsigned().getName()+".p7m");
					FileUtils.writeByteArrayToFile(file, IOUtils.toByteArray(stream));
											
					elaborate.setSigned(file);
					SignerSessionBean signersessionbean = (SignerSessionBean)session.getAttribute(SESSION_SIGNER);
			        signersessionbean.setFiles(files);
			        session.setAttribute(SESSION_SIGNER,signersessionbean);
				}
			}
		}else{
			throw new Exception("La lista dei file non esite!");
		}
	}
	
	protected File getUnsignerFile(String id)throws Exception{
		List<FileElaborate> files = ((SignerSessionBean)session.getAttribute(SESSION_SIGNER)).getFiles();
		File file = null;
		if(files!=null){
			for(FileElaborate elaborate:files){
				if(elaborate.getId().equals(id)){
					file = elaborate.getUnsigned();				
				}
			}
		}else{
			throw new Exception("La lista dei file non esite!");
		}
		if(file!=null){
			return file;
		}else{
			throw new Exception("Il file da firmare non esiste!");
		}
		
	}
	
		
	public void setTabToView(TabType[] tabsToView){
        SignerSessionBean signersessionbean = (SignerSessionBean)session.getAttribute(SESSION_SIGNER);
        signersessionbean.setTabsView(tabsToView);
        session.setAttribute(SESSION_SIGNER,signersessionbean);
	}
		
	public void addFile(File file){
		try{
			addFile(file, java.util.UUID.randomUUID().toString());
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	public void setDebug(boolean debug){
        SignerSessionBean signersessionbean = (SignerSessionBean)session.getAttribute(SESSION_SIGNER);
        signersessionbean.setDebug(debug);
        session.setAttribute(SESSION_SIGNER,signersessionbean);
	}
	
	public String getDebug(){
        SignerSessionBean signersessionbean = (SignerSessionBean)session.getAttribute(SESSION_SIGNER);
        if(signersessionbean==null){
        	return "0";
        }else{
        	return signersessionbean.getDebug();	
        }
        
	}

	public List<FileElaborate> getFilesSigned(){
		return ((SignerSessionBean)session.getAttribute(SESSION_SIGNER)).getFiles();
	}
		
	public void setDigest(HashAlgorithm digest){
		SignerSessionBean bean = (SignerSessionBean)this.session.getAttribute(SESSION_SIGNER);
		bean.setDigest(digest);
		this.session.setAttribute(SESSION_SIGNER, bean);
	}
	
	public void setDefaultType(SignerType type){
		SignerSessionBean bean = (SignerSessionBean)this.session.getAttribute(SESSION_SIGNER);
		bean.setSignerType(type);
		this.session.setAttribute(SESSION_SIGNER, bean);
	}
	
	public void setTypeList(SignerType[] types){
		SignerSessionBean bean = (SignerSessionBean)this.session.getAttribute(SESSION_SIGNER);
		bean.setSignerTypes(types);
		this.session.setAttribute(SESSION_SIGNER, bean);
	}
	
	
	
		
}