package it.eng.client.data;

import it.eng.client.applet.operation.detector.CadesDetector;
import it.eng.common.CMSUtils;
import it.eng.common.bean.HashFileBean;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.Security;
import java.util.Arrays;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.bouncycastle.asn1.ASN1Encoding;
import org.bouncycastle.asn1.ASN1InputStream;
import org.bouncycastle.asn1.ASN1SequenceParser;
import org.bouncycastle.asn1.ASN1StreamParser;
import org.bouncycastle.asn1.BERConstructedOctetString;
import org.bouncycastle.asn1.BERSequenceGenerator;
import org.bouncycastle.asn1.DERSequence;
import org.bouncycastle.asn1.DERTags;
import org.bouncycastle.asn1.cms.CMSObjectIdentifiers;
import org.bouncycastle.asn1.cms.ContentInfo;
import org.bouncycastle.asn1.cms.ContentInfoParser;
import org.bouncycastle.asn1.cms.SignedData;
import org.bouncycastle.asn1.cms.SignedDataParser;
import org.bouncycastle.asn1.pkcs.PKCSObjectIdentifiers;
import org.bouncycastle.cms.CMSSignedData;
import org.bouncycastle.jce.provider.BouncyCastleProvider;



public class SignerTypeUtil {

	public static boolean controlFile(File signerfile,HashFileBean bean)throws Exception{

		CadesDetector signercades = new CadesDetector();
		if(signercades.isSignedType(signerfile)!=null){


			byte[] sign1 = signercades.getHash();
			byte[] sign2 = bean.getHash();

			boolean isequals = Arrays.equals(sign1, sign2);

			return isequals;	
		}else{
			throw new Exception("File non firmato correttamente!");
		}


	}


	public static byte[] attach(byte[] originalData, byte[] detached) { 
		try { 
			ASN1InputStream in = new ASN1InputStream(detached); 
			DERSequence seqContentData = (DERSequence) in.readObject(); 
			ContentInfo contentInfo = new ContentInfo(seqContentData); 
			//SignedData sdDetached = new SignedData((ASN1Sequence) contentInfo.getContent()); 
			//TODO da verificare se devi passare seqContentData
			SignedData sdDetached = SignedData.getInstance(contentInfo.getContent());
			ContentInfo encapContentInfo = new ContentInfo(CMSObjectIdentifiers.data, new BERConstructedOctetString(originalData)); 
			SignedData sdAttached = new SignedData(sdDetached.getDigestAlgorithms(), encapContentInfo, sdDetached.getCertificates(), sdDetached.getCRLs(), sdDetached.getSignerInfos()); 
			ContentInfo contentInfoAttached = new ContentInfo(PKCSObjectIdentifiers.signedData, sdAttached); 
			return contentInfoAttached.getEncoded(ASN1Encoding.DER); 
		} catch (Exception e) { 
			return null; 
		} 
	}
	
	public static  OutputStream attach(InputStream originalData, InputStream detached,OutputStream out) { 
		try { 
			ASN1StreamParser in = new ASN1StreamParser(detached, Integer.MAX_VALUE);
			ContentInfoParser contentInfo = new ContentInfoParser((ASN1SequenceParser)in.readObject());
			SignedDataParser signedData = SignedDataParser.getInstance(contentInfo.getContent(DERTags.SEQUENCE));
			////////////////
			BERSequenceGenerator sGen = new BERSequenceGenerator(out);
			sGen.addObject(CMSObjectIdentifiers.signedData);
			BERSequenceGenerator sigGen = new BERSequenceGenerator(sGen.getRawOutputStream(), 0, true);
			// version number
			sigGen.addObject(signedData.getVersion());
			//digest alg
			sigGen.addObject(signedData.getDigestAlgorithms());
			// encap content info
			ContentInfoParser encapContentInfo = signedData.getEncapContentInfo();
			BERSequenceGenerator eiGen = new BERSequenceGenerator(sigGen.getRawOutputStream());
			eiGen.addObject(encapContentInfo.getContentType());
			//TODO tagnuber
			OutputStream encapStream=CMSUtils.createBEROctetOutputStream(eiGen.getRawOutputStream(), 0, true, 1024);
			IOUtils.copyLarge(originalData, encapStream);
			encapStream.close();
			eiGen.close();
			CMSUtils.writeSetToGeneratorTagged(sigGen, signedData.getCertificates(), 0);
			CMSUtils.writeSetToGeneratorTagged(sigGen, signedData.getCrls(), 1);
			sigGen.getRawOutputStream().write(signedData.getSignerInfos().toASN1Primitive().getEncoded());
			sigGen.close();
			sGen.close();
			return out;
		} catch (Exception e) { 
			e.printStackTrace();
			return null; 
		} 
	}

	public static  OutputStream attach(InputStream originalData, CMSSignedData signedData,OutputStream out) { 
		try { 
			 ////////////////
			 ByteArrayInputStream bis= new ByteArrayInputStream(signedData.getEncoded());
			 return attach(originalData, bis, out);
		} catch (Exception e) { 
			e.printStackTrace();
			return null; 
		} 
	}

	//	public static void main(String[] args) throws Exception {
	//		
	//		Security.addProvider(new BouncyCastleProvider());
	//		
	//		SignerTypeUtil util = new SignerTypeUtil();
	//		
	//		HashFileBean bean = new HashFileBean();
	//		bean.setHash(DigestUtils.sha256(FileUtils.readFileToByteArray(new File("C:/test.pdf"))));
	//		
	//		util.controlFile(new File("C:/test.pdf.p7m"), bean);
	//		
	//	}

	public static void main(String[] args) throws Exception {

		Security.addProvider(new BouncyCastleProvider());

		SignerTypeUtil util = new SignerTypeUtil();
		File detached=new File("c:/prova/test2.doc.p7m");
		File content=new File("c:/prova/test2.doc");
		File output= new File("c:/prova/output.doc.p7m");
		OutputStream result=util.attach(FileUtils.openInputStream(content), FileUtils.openInputStream(detached), FileUtils.openOutputStream(output));
		IOUtils.closeQuietly(result);

	}



}