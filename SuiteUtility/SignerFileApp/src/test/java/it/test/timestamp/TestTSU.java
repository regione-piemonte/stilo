package it.test.timestamp;

import it.eng.client.applet.operation.ts.TimeStamperUtility;
import it.eng.client.applet.util.PreferenceManager;
import it.eng.common.CMSUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.security.Security;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import javax.swing.JApplet;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.bouncycastle.asn1.ASN1InputStream;
import org.bouncycastle.asn1.ASN1ObjectIdentifier;
import org.bouncycastle.asn1.DERSet;
import org.bouncycastle.asn1.cms.CMSObjectIdentifiers;
import org.bouncycastle.asn1.cms.ContentInfo;
import org.bouncycastle.asn1.cms.SignedData;
import org.bouncycastle.asn1.cms.SignerInfo;
import org.bouncycastle.asn1.util.ASN1Dump;
import org.bouncycastle.cert.X509CertificateHolder;
import org.bouncycastle.cert.jcajce.JcaX509CertificateConverter;
import org.bouncycastle.cms.CMSSignedData;
import org.bouncycastle.cms.CMSSignedDataParser;
import org.bouncycastle.cms.CMSSignedGenerator;
import org.bouncycastle.cms.DummySignerInformationUtil;
import org.bouncycastle.cms.SignerInformation;
import org.bouncycastle.cms.SignerInformationStore;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.tsp.TimeStampToken;

/**
 * test classe di utilità per le marche
 * @author Russo
 *
 */
public class TestTSU {
	public static void main(String[] args)throws Exception {
		Security.addProvider(new BouncyCastleProvider());
		PreferenceManager.initConfig((JApplet)null);
		//File f = new File("c:/test2.txt_firmemar.p7m");
		File f = new File("c:/testoodt.odt_ok.p7m");
		//Base64InputStream streambase64 = new Base64InputStream(FileUtils.openInputStream(f));
		CMSSignedDataParser cmsSignedData = new CMSSignedDataParser(FileUtils.openInputStream(f));
		cmsSignedData.getSignedContent().drain();
		SignerInformationStore signersStore = cmsSignedData.getSignerInfos();
		Collection<? extends SignerInformation> signers = signersStore.getSigners();

		ArrayList<SignerInformation>  newSigners=new ArrayList<SignerInformation>();
		//test certificati
		Collection certificati=cmsSignedData.getCertificates().getMatches(null);
		for (Iterator iterator = certificati.iterator(); iterator.hasNext();) {
			X509CertificateHolder xhol = (X509CertificateHolder) iterator.next();
			X509Certificate cert= new JcaX509CertificateConverter().setProvider( "BC" )
			  .getCertificate( (X509CertificateHolder)xhol );
			 cmsSignedData.getAttributeCertificates();
			testX509CertRead(cert);
		}
		

		if (signers != null) {
			for (SignerInformation signer: signers) {
				 
				//test build signerInfo
				buildSignerInformation(signer.toASN1Structure());
				// test(f,signer);
				//extract TimeStamp for debug
				List<TimeStampToken> timestamps=TimeStamperUtility.extractTimeStamps(signer);
				for (TimeStampToken timeStampToken : timestamps) {
					System.out.println("ASN1Dump Token:"+ASN1Dump.dumpAsString(timeStampToken));
				}
				//aggiungi una marca al signer  
				newSigners.add(TimeStamperUtility.addMarca(signer));



			}//end for
			OutputStream stream=cmsSignedData.replaceSigners(FileUtils.openInputStream(f), new SignerInformationStore(newSigners), new FileOutputStream(new File("c:/output2.p7m")));
			IOUtils.closeQuietly(stream);
		}
	}
	
	private static void buildSignerInformation(SignerInfo info)throws Exception{
		DERSet digestAlg= new DERSet(info.getDigestAlgorithm());
		DERSet sinfos= new DERSet(info );
		 
		DERSet digest = (DERSet)new ASN1InputStream(digestAlg.getEncoded()).readObject();
	    DERSet signerInfos = (DERSet)new ASN1InputStream(sinfos.getEncoded()).readObject();
	    ContentInfo encInfo = new ContentInfo(new ASN1ObjectIdentifier(CMSSignedGenerator.DATA), null); 
	        
	        SignedData  sd = new SignedData(
	        						 digest,
	                                 encInfo, 
	                                 null, 
	                                 null, 
	                                 signerInfos);
	        
	        
	        //System.out.println(sd.getDEREncoded().length);

	        ContentInfo contentInfo = new ContentInfo(CMSObjectIdentifiers.signedData, sd);
	        CMSSignedData data = new CMSSignedData( contentInfo);
	         
	        data.getSignerInfos();
	}
	
	private static void testX509CertRead(X509Certificate x509cert) throws Exception{
		X509CertificateHolder holder= new X509CertificateHolder(x509cert.getEncoded());
		System.out.println("test "+holder);
	}
	
	//test aggiunta di una firma congiunta mancano i cert!!
	private static void test(File file,SignerInformation sinfo)throws Exception{
//		 DERSet digest = (DERSet)new ASN1InputStream( new DERSet(sinfo.getDigestAlgorithmID()).getEncoded()).readObject();
//         DERSet signerInfos = (DERSet)new ASN1InputStream( new DERSet(sinfo.toASN1Structure()).getEncoded()).readObject();
//         
//         
//         SignedData  sd = new SignedData(
//         						 digest,
//                                  null, 
//                                  null, 
//                                  null, 
//                                  signerInfos);
//         CMSSignedData data = new CMSSignedData( sd.getEncoded());
		
		CMSSignedDataParser cmsSignedData= new CMSSignedDataParser(FileUtils.openInputStream(file));
    	cmsSignedData.getSignedContent().drain();
    	Collection  signers = (Collection )cmsSignedData.getSignerInfos().getSigners();
    	
//    	for (Object signer: signers) {
//			if(signer instanceof SignerInformation){
//				
//			}
//    	}
    	//test la costruzione di un SignerInformation a partire da un signerInfo
    	signers.add(DummySignerInformationUtil.buildSignerInformation( sinfo.toSignerInfo()) );
    	OutputStream stream=CMSSignedDataParser.replaceSigners(FileUtils.openInputStream(file), new SignerInformationStore(signers), new FileOutputStream(new File(file.getAbsolutePath()+"x.p7m")));
    	IOUtils.closeQuietly(stream);
		
	}
}
