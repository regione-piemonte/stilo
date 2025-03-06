package it.eng.client.applet.operation;

import it.eng.client.applet.bean.PrivateKeyAndCert;

import it.eng.client.applet.operation.ts.TimeStamperUtility;
import it.eng.client.applet.util.ProxyUtil;

import java.security.Provider;
import java.security.cert.CertStore;
import java.security.cert.Certificate;
import java.security.cert.CollectionCertStoreParameters;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.apache.commons.codec.digest.DigestUtils;
import org.bouncycastle.asn1.ASN1EncodableVector;
import org.bouncycastle.asn1.DERSet;
import org.bouncycastle.asn1.cms.Attribute;
import org.bouncycastle.asn1.cms.AttributeTable;
import org.bouncycastle.asn1.ess.ESSCertIDv2;
import org.bouncycastle.asn1.ess.SigningCertificateV2;
import org.bouncycastle.asn1.nist.NISTObjectIdentifiers;
import org.bouncycastle.asn1.pkcs.PKCSObjectIdentifiers;
import org.bouncycastle.asn1.x509.AlgorithmIdentifier;
import org.bouncycastle.cms.CMSSignedData;
import org.bouncycastle.cms.CMSSignedDataGenerator;
import org.bouncycastle.cms.SignerInformation;
import org.bouncycastle.cms.SignerInformationStore;

public class CadesSigner extends P7MSigner{
	 
	
	public CadesSigner(){
		//setto il default a quello valido per l'italia
		this.setDigestAlg(CMSSignedDataGenerator.DIGEST_SHA256);
	}
	
	public CadesSigner(String digestAlg, String signerAlg) {
		super(digestAlg, signerAlg); 
		 
	}

	public CadesSigner(String digestAlg) {
		super(digestAlg);
		
	}
	
	
	public CMSSignedData firmaDetached(byte[] hash,PrivateKeyAndCert pvc,Provider provider,boolean timemark) throws Exception{
		Certificate[] list= new Certificate[1];
		list[0]=pvc.getCertificate();
		CertStore certStore = CertStore.getInstance("Collection",new CollectionCertStoreParameters(Arrays.asList(list)));
		CMSSignedHashGenerator gen = new CMSSignedHashGenerator();
		ASN1EncodableVector unsignetAttrV = new ASN1EncodableVector();
        
	        //creo la tabella degli attr non firmati 
        //nonserve la marca si mette dopo la firma
	    AttributeTable unsignetAttrTable = new AttributeTable(unsignetAttrV);
	    if(getDigestAlg().equals(CMSSignedDataGenerator.DIGEST_SHA256)){
        	 
        	//Calcolo l'hash del certificato
        	byte[] certhash = DigestUtils.sha256(pvc.getCertificate().getEncoded());
        	//ASN1InputStream cert = new ASN1InputStream(certificate.getEncoded());
  	        //DERSet attrValuecert = new DERSet(cert.readObject());
        	AlgorithmIdentifier algcert256 = new AlgorithmIdentifier(NISTObjectIdentifiers.id_sha256);
        	ESSCertIDv2 cert = new ESSCertIDv2(algcert256, certhash);
        	ESSCertIDv2[] arraycert = new ESSCertIDv2[]{cert};
        	SigningCertificateV2 signcert = new SigningCertificateV2(arraycert);
        	Attribute attr = new Attribute(PKCSObjectIdentifiers.id_aa_signingCertificateV2,new DERSet(signcert.toASN1Primitive())); 
        	ASN1EncodableVector v = new ASN1EncodableVector();
        	v.add(attr);
  	        AttributeTable signedAttr = new AttributeTable(v);
  	        gen.addSigner(pvc.getPrivateKey(), pvc.getCertificate(), getDigestAlg(),signedAttr,null);
        }else{
        	//gen.addSigner(key, certificate, alg);
        	gen.addSigner(pvc.getPrivateKey(), pvc.getCertificate(), getDigestAlg(),null,null);
        }
	    gen.addCertificatesAndCRLs(certStore);
		
		CMSSignedData signedData= gen.generateDetached(hash, provider);
		if(timemark){
			ProxyUtil.initProxyConnection();
			//aggiungo la marca
			//TODO  non devi chedere una marca per ogni signerinfo devi usare la stessa calcolata
			//magari sulle signature totali o su una specifica
			Collection signers=signedData.getSignerInfos().getSigners();
			List<SignerInformation> newSigners=new ArrayList<SignerInformation>();
			for (Object signer: signers) {
				if(signer instanceof SignerInformation){
					newSigners.add(TimeStamperUtility.addMarca((SignerInformation)signer));
				}
			}
			signedData=CMSSignedData.replaceSigners(signedData, new SignerInformationStore(newSigners));
		}
		return signedData;
	}	
//	public   void firma(InputStream inputStream,OutputStream outputStream,PrivateKeyAndCert pvc,Provider provider)throws Exception{
//
//		try {
//			X509Certificate x509Cer = pvc.getCertificate();
//			logger.debug("x509Cer = " + x509Cer);
//
//			List<X509Certificate> certList = new ArrayList<X509Certificate>();
//			certList.add(x509Cer);
//
//			Store store = new JcaCertStore(certList);
//
//			//CMSSignedDataGenerator generator = new CMSSignedDataGenerator();
//			CMSSignedDataStreamGenerator generator = new CMSSignedDataStreamGenerator();
//			String providerName = provider.getName();
//
//			// Settaggio attributi
//			//		// 1. contentType
//			//		Attribute a1 = new Attribute(CMSAttributes.contentType, new DERSet(
//			//				new DERObjectIdentifier(SignedData.data.getId())));
//			//
//			//		// 2. Date
//			//		Date d = new Date();
//			//		Attribute a2 = new Attribute(CMSAttributes.signingTime, new DERSet(
//			//				new Time(d)));
//			//
//			//		// 3. Hash dei dati
//			//		Attribute a3 = new Attribute(
//			//				org.bouncycastle.asn1.cms.CMSAttributes.messageDigest,
//			//				new DERSet(new DEROctetString(digest)));
//
//			Attribute a4 = null;
//			IssuerSerial issSerial = new IssuerSerial(new GeneralNames(
//					new DERSequence(new GeneralName(
//							org.bouncycastle.jce.PrincipalUtil
//							.getIssuerX509Principal(x509Cer)))),
//							new DERInteger(x509Cer.getSerialNumber()));
//			//faccio solo lo sha256
//			if (getDigestAlg().equals(CMSSignedGenerator.DIGEST_SHA1)) {
//				MessageDigest msgDigest = java.security.MessageDigest
//				.getInstance(CMSSignedGenerator.DIGEST_SHA1, "BC");
//				ESSCertID essCertid = new ESSCertID(msgDigest.digest(x509Cer
//						.getEncoded()), issSerial);
//				a4 = new Attribute(SignedData.id_aa_signingCertificate,
//						new DERSet(new SigningCertificate(essCertid)));
//			} else if (getDigestAlg().equals(CMSSignedGenerator.DIGEST_SHA256)) {//faccio sha256
//				MessageDigest msgDigest = java.security.MessageDigest
//				.getInstance(CMSSignedGenerator.DIGEST_SHA256, "BC");
//				ESSCertIDv2 essCertidv2 = new ESSCertIDv2(
//						new AlgorithmIdentifier(
//								CMSSignedDataGenerator.DIGEST_SHA256),
//								msgDigest.digest(x509Cer.getEncoded()), issSerial);
//				ESSCertIDv2[] essCertArray = new ESSCertIDv2[1];
//				essCertArray[0] = essCertidv2;
//				a4 = new org.bouncycastle.asn1.cms.Attribute(
//						org.bouncycastle.asn1.pkcs.SignedData.id_aa_signingCertificateV2,
//						new org.bouncycastle.asn1.DERSet(
//								new SigningCertificateV2(essCertArray)));
//			}
//
//			ASN1EncodableVector hashTabAtt = new ASN1EncodableVector();
//			//		hashTabAtt.add(a1);
//			//		hashTabAtt.add(a2);
//			//		hashTabAtt.add(a3);
//			hashTabAtt.add(a4);
//
//			DefaultSignedAttributeTableGenerator dsat = new DefaultSignedAttributeTableGenerator(
//					new AttributeTable(hashTabAtt));
//
//			// 4. Aggiunge la firma
//			ContentSigner signer = new JcaContentSignerBuilder("SHA256WITHRSA")
//			.setProvider(providerName).build(pvc.getPrivateKey());
//			DigestCalculatorProvider dcp = new JcaDigestCalculatorProviderBuilder()
//			.setProvider("BC").build();
//			JcaSignerInfoGeneratorBuilder sigb = new JcaSignerInfoGeneratorBuilder(
//					dcp);
//			//		sigb.setDirectSignature(false); // non fa generare quelli di default
//			sigb.setDirectSignature(false);
//			sigb.setSignedAttributeGenerator(dsat);
//			SignerInfoGenerator sig = sigb.build(signer, x509Cer);
//			generator.addSignerInfoGenerator(sig);
//			generator.addCertificates(store);
//			
//			OutputStream sigOut = generator.open(outputStream,true);
//			IOUtils.copyLarge(inputStream, sigOut);
//
//			IOUtils.closeQuietly(sigOut);
//			IOUtils.closeQuietly(outputStream);
//			IOUtils.closeQuietly(inputStream);
//		} catch (Exception e) {
//			logger.error(e.getMessage(), e);
//			throw e;
//		}
//	}




}
