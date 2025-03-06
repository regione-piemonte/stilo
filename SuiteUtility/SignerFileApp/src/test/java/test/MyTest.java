 package test;
import it.eng.client.applet.operation.AbstractSigner;
import it.eng.client.applet.operation.Factory;
import it.eng.client.applet.operation.jsignpdf.HashAlgorithm;
import it.eng.common.CMSUtils;
import it.eng.common.bean.HashFileBean;
import it.eng.common.bean.SignerObjectBean;
import it.eng.common.type.SignerType;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.security.Security;
import java.security.cert.CertStore;
import java.security.cert.CollectionCertStoreParameters;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.SerializationUtils;
import org.bouncycastle.asn1.ASN1InputStream;
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
import org.bouncycastle.jce.provider.BouncyCastleProvider;


public class MyTest {

	/**
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {
		
		Security.addProvider(new BouncyCastleProvider());
		
		AbstractSigner signer = null;	
		//Carico i provider
		File tmp = new File(AbstractSigner.dir);
		File directory = new File(tmp+File.separator+"dllCrypto");
		File[] dll = directory.listFiles();
		sun.security.pkcs11.SunPKCS11 provider = null;
//		for(int i=0;i<dll.length;i++){
//			if(dll[i].isFile()){	
//				try{
//					System.out.println(dll[i].getAbsolutePath());
//			    	StringBuffer cardConfig=new StringBuffer();
//			        cardConfig.append ("name=SmartCards \n");
//			        cardConfig.append ("library="+dll[i].getAbsolutePath());     
//			        ByteArrayInputStream confStream = new ByteArrayInputStream(cardConfig.toString().getBytes());
//					provider = new sun.security.pkcs11.SunPKCS11(confStream);
//					
//					
//					
//					//signer = new BaseSigner(DigestAlgorithm.SHA256,SignerType.P7M,provider);
//					//Security.addProvider(provider); 
//					//log.debug("Provider caricato!");
//					break;
//				}catch(Exception e){
//					e.printStackTrace();
//				}
//			}
//		}
//		try{	
//			String pin="12345678";
//			signer = Factory.getSigner(HashAlgorithm.SHA256,SignerType.P7M, pin.toCharArray(),0,false);
//			HashFileBean bean = new HashFileBean();
//			bean.setFileName("C:\\test.txt");
//			bean.setFileType(SignerType.P7M);
//			bean.setHash(DigestUtils.sha256(FileUtils.readFileToByteArray(new File("C:\\test.txt"))));
//			
//			//em String str = signer.signerfile(bean, "12345".toCharArray());
//			String str = signer.signerfile(bean, pin.toCharArray());
//			
//			SignerObjectBean bean2 = (SignerObjectBean)SerializationUtils.deserialize(Base64.decodeBase64(str));
//			//TODO test adding multiple mark to signed File
//			
//			signerP7MSERVER(bean2);
//		}catch(Exception e){
//			e.printStackTrace();
//			System.out.println("ERRORE:"+e.getMessage());
//		}
	}
			
	protected static void signerP7MSERVER(SignerObjectBean bean)throws Exception{
		ASN1Set certificates = null;
	
		List _certs = new ArrayList();
		List certList = new ArrayList();
		List _crls = new ArrayList();
		List _crlsList = new ArrayList();
				
        for(X509Certificate certificate:bean.getCertificates()){
			certList.add(certificate);
		}
        //BC sarebbe il nome di bouncycastle
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
        //List<FileElaborate> files = ((SignerSessionBean)session.getAttribute(SESSION_SIGNER)).getFiles();
        
//        FileElaborate ret = null;
//        for(FileElaborate file:files){
//        	if(file.getUnsigned().getName().equals(bean.getFileName())){
//        		ret = file;
//        		break;
//        	}
//        }
               
        CMSProcessable content = new CMSProcessableFile(new File("C:\\test.txt"));
        ASN1OctetString octs = null;
        
	    if (content != null)
	            {
	                try
	                {
	                    content.write(bOut);
	                }
	                catch (Exception e)
	                {
	                    throw new Exception("encapsulation error.", e);
	                }
	            }
	
	            octs = new BERConstructedOctetString(bOut.toByteArray());
        

        ContentInfo encInfo = new ContentInfo(CMSObjectIdentifiers.data, octs);
        
        DERSet digest = (DERSet)new ASN1InputStream(bean.getDigestAlgs()).readObject();
        DERSet signerInfos = (DERSet)new ASN1InputStream(bean.getSignerInfo()).readObject();
        
        
        SignedData  sd = new SignedData(
        						 digest,
                                 encInfo, 
                                 certificates, 
                                 certrevlist, 
                                 signerInfos);

        ContentInfo contentInfo = new ContentInfo(CMSObjectIdentifiers.signedData, sd);
        CMSSignedData data = new CMSSignedData(content, contentInfo);
        
        //Aggiugno il certificato alle buste
        File firmato = new File("C:\\testoodt.odt.p7m");
        
        FileUtils.writeByteArrayToFile(firmato, data.getEncoded());
        
//        if(ret.getSigned()!=null){
//        	ret.getSigned().delete();
//        }
        
        //ret.setSigned(firmato);
        
        //SignerSessionBean signersessionbean = (SignerSessionBean)session.getAttribute(SESSION_SIGNER);
        //signersessionbean.setFiles(files);
        //session.setAttribute(SESSION_SIGNER,signersessionbean);
	}

}
