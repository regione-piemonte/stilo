package it.test.timestamp;


import it.eng.common.LogWriter;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.KeyStore;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.bouncycastle.asn1.ASN1EncodableVector;
import org.bouncycastle.asn1.ASN1InputStream;
import org.bouncycastle.asn1.ASN1Object;
import org.bouncycastle.asn1.ASN1ObjectIdentifier;
import org.bouncycastle.asn1.BERSequence;
import org.bouncycastle.asn1.DERInteger;
import org.bouncycastle.asn1.DERObjectIdentifier;
import org.bouncycastle.asn1.DEROctetString;
import org.bouncycastle.asn1.DERSequence;
import org.bouncycastle.asn1.DERSet;
import org.bouncycastle.asn1.cms.Attribute;
import org.bouncycastle.asn1.cms.AttributeTable;
import org.bouncycastle.asn1.cms.CMSAttributes;
import org.bouncycastle.asn1.cms.Time;
import org.bouncycastle.asn1.ess.ESSCertID;
import org.bouncycastle.asn1.ess.ESSCertIDv2;
import org.bouncycastle.asn1.ess.SigningCertificate;
import org.bouncycastle.asn1.ess.SigningCertificateV2;
import org.bouncycastle.asn1.pkcs.SignedData;
import org.bouncycastle.asn1.x509.AlgorithmIdentifier;
import org.bouncycastle.asn1.x509.GeneralName;
import org.bouncycastle.asn1.x509.GeneralNames;
import org.bouncycastle.asn1.x509.IssuerSerial;
import org.bouncycastle.cert.X509CertificateHolder;
import org.bouncycastle.cert.jcajce.JcaCertStore;
import org.bouncycastle.cert.jcajce.JcaX509CertificateConverter;
import org.bouncycastle.cms.CMSProcessableByteArray;
import org.bouncycastle.cms.CMSSignedData;
import org.bouncycastle.cms.CMSSignedDataGenerator;
import org.bouncycastle.cms.CMSSignedGenerator;
import org.bouncycastle.cms.CMSTypedData;
import org.bouncycastle.cms.DefaultSignedAttributeTableGenerator;
import org.bouncycastle.cms.SignerInfoGenerator;
import org.bouncycastle.cms.SignerInformation;
import org.bouncycastle.cms.SignerInformationStore;
import org.bouncycastle.cms.jcajce.JcaSignerInfoGeneratorBuilder;
import org.bouncycastle.operator.ContentSigner;
import org.bouncycastle.operator.DigestCalculatorProvider;
import org.bouncycastle.operator.jcajce.JcaContentSignerBuilder;
import org.bouncycastle.operator.jcajce.JcaDigestCalculatorProviderBuilder;
import org.bouncycastle.util.Store;

/**
 * Classe delegata ad implementare le funzioni di firma con le API Bouncycastle.
 * 
 * @author Francesco Barcellini
 * @version 1.0
 * @date 14/May/2012
 */
public class Signer {
//	private Log logger = LogFactory.getLog(Signer.class);
//	private ProviderResponse response = null;
//	private KeyStore p11Session;
//	private PrivateKey signatureKey;
//	private ByteArrayOutputStream contentBuffer;
//	private CertificatoImpl signerCert = null;
//	private ResettableFileInputStream bitStream = null;
//	private ASN1Object pkcs7StreamAsASN1Object;
//	private OutputStream pkcs7Stream;
//	private byte[] inputStreamByteArray;
//	private String digestAlgorithm = Costanti.SHA256;
//	private String signerAlgorithm = Costanti.SHA256WITHRSA;
//	private DERObjectIdentifier digestAlgorithmId = new DERObjectIdentifier(
//			CMSSignedGenerator.DIGEST_SHA256);

	/**
	 * Costruttore della classe
	 */
	public Signer() {
//		String defaultDigest = Config.getInstance().getProperty(
//				"mpcs.default.digest");
//		if (defaultDigest != null) {
//			if (Costanti.SHA1.equalsIgnoreCase(defaultDigest.trim())) {
//				digestAlgorithm = Costanti.SHA1;
//				signerAlgorithm = Costanti.SHA1WITHRSA;
//				digestAlgorithmId = new DERObjectIdentifier(
//						CMSSignedGenerator.DIGEST_SHA1);
//			} else {
//				digestAlgorithm = Costanti.SHA256;
//				signerAlgorithm = Costanti.SHA256WITHRSA;
//				digestAlgorithmId = new DERObjectIdentifier(
//						CMSSignedGenerator.DIGEST_SHA256);
//			}
//		}
	}

	/**
	 * Determina il contenuto del file contenuto in 'in' e in base al suo
	 * contenuto lo firma utilizzando la chiave corrispondente al certificato
	 * aCert. Crea un PKCS7 o aggiunge una firma. Tiene conto della normativa
	 * vigente per l'applicazione della firma CADES.
	 * 
	 * @param aCert
	 * @param in
	 * @param out
	 * @return ProviderResponse
//	 */
//	public ProviderResponse nonRepudiationSign(CertificatoImpl aCert,
//			ResettableFileInputStream in, OutputStream out) {
//		LogWriter.writeLog("nonRepudiationSign ");
//		this.setSignerCert(aCert);
//		this.setBitStream(in);
//		LogWriter.writeLog("Uso il certificato: " + aCert);
//		LogWriter.writeLog("token = " + aCert.getToken());
//		LogWriter.writeLog("lib = " + aCert.getToken().getPKCS11Lib());
//		response = new ProviderResponse();
//		this.openSession(aCert);
//		if (!this.getSignatureKeyForX509Cert(aCert, aCert.getToken().getPIN()))
//			return response;
//		this.setSignerCert(aCert);
//		this.setBitStream(in);
//		this.addSignatureTo(out);
//
//		return response;
//	}

	/**
	 * Determina il contenuto del file contenuto in 'in' e in base al suo
	 * contenuto lo firma utilizzando la chiave corrispondente al certificato
	 * aCert. Crea un PKCS7 o aggiunge una firma. Tiene conto della normativa
	 * vigente per l'applicazione della firma CADES.
	 * 
	 * @param aCert
	 * @param in
	 * @param out
	 * @return ProviderResponse
	 */
//	public ProviderResponse digitalSignatureSign(CertificatoImpl aCert,
//			ResettableFileInputStream in, OutputStream out) {
//		LogWriter.writeLog("digitalSignatureSign ");
//		this.setSignerCert(aCert);
//		this.setBitStream(in);
//		LogWriter.writeLog("Uso il certificato: " + aCert);
//		LogWriter.writeLog("token = " + aCert.getToken());
//		LogWriter.writeLog("lib = " + aCert.getToken().getPKCS11Lib());
//		response = new ProviderResponse();
//		this.openSession(aCert);
//		if (!this.getSignatureKeyForX509Cert(aCert, aCert.getToken().getPIN()))
//			return response;
//		this.setSignerCert(aCert);
//		this.setBitStream(in);
//		this.addSignatureTo(out);
//
//		return response;
//	}

	/**
	 * Determina il contenuto del file contenuto in bitStream e in base al suo
	 * contenuto lo firma creando un PKCS7. Tiene conto della normativa vigente
	 * per l'applicazione della firma CADES.
	 * 
	 * @param out
	 */
//	public ProviderResponse encipherOnlySign(CertificatoImpl aCert,
//			ResettableFileInputStream in, OutputStream out) {
//		LogWriter.writeLog("encipherOnlySign ");
//		this.setSignerCert(aCert);
//		this.setBitStream(in);
//		LogWriter.writeLog("Uso il certificato: " + aCert);
//		LogWriter.writeLog("token = " + aCert.getToken());
//		LogWriter.writeLog("lib = " + aCert.getToken().getPKCS11Lib());
//		response = new ProviderResponse();
//		this.openSession(aCert);
//		if (!this.getSignatureKeyForX509Cert(aCert, aCert.getToken().getPIN()))
//			return response;
//		this.setSignerCert(aCert);
//		this.setBitStream(in);
//		this.addSignatureTo(out);
//
//		return response;
//	} // End encipherOnlySign

	/**
	 * Determina il contenuto del file contenuto in bitStream e in base al suo
	 * contenuto lo firma creando un PKCS7 o aggiunge una firma. Tiene conto
	 * della normativa vigente per l'applicazione della firma CADES.
	 * 
	 * @param out
	 */
//	private void addSignatureTo(OutputStream out) {
//		LogWriter.writeLog("addSignatureTo ");
//		LogWriter.writeLog("starting");
//		this.setPKCS7Stream(out);
//		ASN1ObjectIdentifier oid = this.detectInputStreamContent();
//		if (oid == null || !SignedData.signedData.equals(oid)) {
//			LogWriter.writeLog("no oid detected");
//			if (!Boolean.valueOf(Config.getInstance().getProperty(
//					Config.PROP_CNIPA2009_ENABLED))) {
//				this.createSignature();
//			} else {
//				this.createSignatureCades();
//			}
//			return;
//		} else {
//			LogWriter.writeLog("oid detected = " + oid);
//			if (!Boolean.valueOf(Config.getInstance().getProperty(
//					Config.PROP_CNIPA2009_ENABLED))) {
//				this.addSignature();
//			} else {
//				this.addSignatureCades();
//			}
//			return;
//		}
//	} // End addSignatureTo

	/**
	 * Crea una busta firmata PKCS7 DER con firma CADES partendo dal dato
	 * contenuto in "bitStream", utilizzando l'algoritmo definito nelle
	 * variabili digestAlgorithm, signerAlgorithm, digestAlgorithmId firmandola
	 * con la chiave privata corrispondente al certificato contenuto in x509cer.
	 */
//	private void createSignatureCades() {
//		LogWriter.writeLog("createSignature ");
//		try {
//			this.getBitStream().mark(0);
//			byte[] digest = this.getDigest(this.getBitStream(),
//					getDigestAlgorithm());
//			LogWriter.writeLog("digest = " + digest);
//			this.getBitStream().reset();
//
//			X509Certificate x509Cer = (X509Certificate) this.getSignerCert()
//					.getX509cer();
//			LogWriter.writeLog("x509Cer = " + x509Cer);
//
//			List<X509Certificate> certList = new ArrayList<X509Certificate>();
//			certList.add(x509Cer);
//
//			Store store = new JcaCertStore(certList);
//
//			CMSSignedDataGenerator generator = new CMSSignedDataGenerator();
//
//			String providerName = p11Session.getProvider().getName();
//
//			// Settaggio attributi
//			// 1. contentType
//			Attribute a1 = new Attribute(CMSAttributes.contentType, new DERSet(
//					new DERObjectIdentifier(SignedData.data.getId())));
//
//			// 2. Date
//			Date d = new Date();
//			Attribute a2 = new Attribute(CMSAttributes.signingTime, new DERSet(
//					new Time(d)));
//
//			// 3. Hash dei dati
//			Attribute a3 = new Attribute(
//					org.bouncycastle.asn1.cms.CMSAttributes.messageDigest,
//					new DERSet(new DEROctetString(digest)));
//
//			Attribute a4 = null;
//			IssuerSerial issSerial = new IssuerSerial(new GeneralNames(
//					new DERSequence(new GeneralName(
//							org.bouncycastle.jce.PrincipalUtil
//									.getIssuerX509Principal(x509Cer)))),
//					new DERInteger(x509Cer.getSerialNumber()));
//			if (this.digestAlgorithmId.getId().equals(
//					org.bouncycastle.cms.CMSSignedDataGenerator.DIGEST_SHA1)) {
//				MessageDigest msgDigest = java.security.MessageDigest
//						.getInstance(CMSSignedGenerator.DIGEST_SHA1, "BC");
//				ESSCertID essCertid = new ESSCertID(msgDigest.digest(x509Cer
//						.getEncoded()), issSerial);
//				a4 = new Attribute(SignedData.id_aa_signingCertificate,
//						new DERSet(new SigningCertificate(essCertid)));
//			} else if (this.digestAlgorithmId.getId().equalsIgnoreCase(
//					org.bouncycastle.cms.CMSSignedDataGenerator.DIGEST_SHA256)) {
//				MessageDigest msgDigest = java.security.MessageDigest
//						.getInstance(CMSSignedGenerator.DIGEST_SHA256, "BC");
//				ESSCertIDv2 essCertidv2 = new ESSCertIDv2(
//						new AlgorithmIdentifier(
//								CMSSignedDataGenerator.DIGEST_SHA256),
//						msgDigest.digest(x509Cer.getEncoded()), issSerial);
//				ESSCertIDv2[] essCertArray = new ESSCertIDv2[1];
//				essCertArray[0] = essCertidv2;
//				a4 = new org.bouncycastle.asn1.cms.Attribute(
//						org.bouncycastle.asn1.pkcs.SignedData.id_aa_signingCertificateV2,
//						new org.bouncycastle.asn1.DERSet(
//								new SigningCertificateV2(essCertArray)));
//			}
//
//			ASN1EncodableVector hashTabAtt = new ASN1EncodableVector();
//			hashTabAtt.add(a1);
//			hashTabAtt.add(a2);
//			hashTabAtt.add(a3);
//			hashTabAtt.add(a4);
//
//			DefaultSignedAttributeTableGenerator dsat = new DefaultSignedAttributeTableGenerator(
//					new AttributeTable(hashTabAtt));
//
//			// 4. Aggiunge la firma
//			ContentSigner signer = new JcaContentSignerBuilder(signerAlgorithm)
//					.setProvider(providerName).build(this.signatureKey);
//			DigestCalculatorProvider dcp = new JcaDigestCalculatorProviderBuilder()
//					.setProvider("BC").build();
//			JcaSignerInfoGeneratorBuilder sigb = new JcaSignerInfoGeneratorBuilder(
//					dcp);
//			sigb.setDirectSignature(false); // include signed attributes
//			sigb.setSignedAttributeGenerator(dsat);
//			SignerInfoGenerator sig = sigb.build(signer, x509Cer);
//			generator.addSignerInfoGenerator(sig);
//			generator.addCertificates(store);
//
//			int thisLine;
//			this.getBitStream().mark(0);
//			ByteArrayOutputStream bos = new ByteArrayOutputStream();
//			while ((thisLine = this.getBitStream().read()) != -1) {
//				bos.write(thisLine);
//			}
//			bos.flush();
//			byte[] data = bos.toByteArray();
//
//			CMSTypedData message = new CMSProcessableByteArray(data);
//			CMSSignedData signedData = generator.generate(message, true);
//			this.getPKCS7Stream().write(signedData.getEncoded());
//			this.getPKCS7Stream().flush();
//			this.getPKCS7Stream().close();
//			this.getBitStream().reset();
//			response.setValue(this.getPKCS7Stream());
//		} catch (Throwable e) {
//			LogWriter.writeLog(e.getMessage(), e);
//		}
//	} // End createSignatureCades

	/**
	 * Crea una busta firmata PKCS7 DER partendo dal dato contenuto in
	 * "bitStream", utilizzando l'algoritmo definito nelle variabili
	 * digestAlgorithm, signerAlgorithm, digestAlgorithmId firmandola con la
	 * chiave privata corrispondente al certificato contenuto in x509cer.
	 */
//	private void createSignature() {
//		LogWriter.writeLog("createSignature ");
//		try {
//
//			this.getBitStream().mark(0);
//			byte[] digest = this.getDigest(this.getBitStream(),
//					getDigestAlgorithm());
//			LogWriter.writeLog("digest = " + digest);
//			this.getBitStream().reset();
//
//			X509Certificate x509Cer = (X509Certificate) this.getSignerCert()
//					.getX509cer();
//			LogWriter.writeLog("x509Cer = " + x509Cer);
//
//			List<X509Certificate> certList = new ArrayList<X509Certificate>();
//			certList.add(x509Cer);
//
//			Store store = new JcaCertStore(certList);
//
//			CMSSignedDataGenerator generator = new CMSSignedDataGenerator();
//
//			String providerName = p11Session.getProvider().getName();
//
//			// Settaggio attributi
//			// 1. contentType
//			Attribute a1 = new Attribute(CMSAttributes.contentType, new DERSet(
//					new DERObjectIdentifier(SignedData.data.getId())));
//
//			// 2. Date
//			Date d = new Date();
//			Attribute a2 = new Attribute(CMSAttributes.signingTime, new DERSet(
//					new Time(d)));
//
//			// 3. Hash dei dati
//			Attribute a3 = new Attribute(
//					org.bouncycastle.asn1.cms.CMSAttributes.messageDigest,
//					new DERSet(new DEROctetString(digest)));
//
//			ASN1EncodableVector hashTabAtt = new ASN1EncodableVector();
//			hashTabAtt.add(a1);
//			hashTabAtt.add(a2);
//			hashTabAtt.add(a3);
//
//			DefaultSignedAttributeTableGenerator dsat = new DefaultSignedAttributeTableGenerator(
//					new AttributeTable(hashTabAtt));
//
//			// 4. Aggiunge la firma
//			ContentSigner signer = new JcaContentSignerBuilder(signerAlgorithm)
//					.setProvider(providerName).build(this.signatureKey);
//			DigestCalculatorProvider dcp = new JcaDigestCalculatorProviderBuilder()
//					.setProvider("BC").build();
//			JcaSignerInfoGeneratorBuilder sigb = new JcaSignerInfoGeneratorBuilder(
//					dcp);
//			sigb.setDirectSignature(false); // include signed attributes
//			sigb.setSignedAttributeGenerator(dsat);
//			SignerInfoGenerator sig = sigb.build(signer, x509Cer);
//			generator.addSignerInfoGenerator(sig);
//			generator.addCertificates(store);
//
//			int thisLine;
//			this.getBitStream().mark(0);
//			ByteArrayOutputStream bos = new ByteArrayOutputStream();
//			while ((thisLine = this.getBitStream().read()) != -1) {
//				bos.write(thisLine);
//			}
//			bos.flush();
//			byte[] data = bos.toByteArray();
//
//			CMSTypedData message = new CMSProcessableByteArray(data);
//			CMSSignedData signedData = generator.generate(message, true);
//			this.getPKCS7Stream().write(signedData.getEncoded());
//			this.getPKCS7Stream().flush();
//			this.getPKCS7Stream().close();
//			this.getBitStream().reset();
//			response.setValue(this.getPKCS7Stream());
//		} catch (Throwable e) {
//			LogWriter.writeLog(e.getMessage(), e);
//		}
//	} // End createSignature

	/**
	 * Aggiunge una firma ad una busta firmata PKCS7 DER partendo dal dato
	 * contenuto in "bitStream", utilizzando l'algoritmo definito nelle
	 * variabili digestAlgorithm, signerAlgorithm, digestAlgorithmId firmandola
	 * con la chiave privata corrispondente al certificato contenuto in x509cer.
	 */
//	private void addSignature() {
//		LogWriter.writeLog("addSignature ");
//		LogWriter.writeLog("starting pkcs#7 object detected");
//		try {
//
//			// Non è possibile rileggere lo stream di input, se serve bisogna
//			// utilizzare un ResettableFileInputStream
//			CMSSignedData signedData = new CMSSignedData(this
//					.getPKCS7StreamAsASN1Object().getEncoded());
//			LogWriter.writeLog("SD = " + signedData);
//
//			// Verificare che il digest sia corretto!!!
//			CMSProcessableByteArray cmsProcessableByteArray = (CMSProcessableByteArray) signedData
//					.getSignedContent();
//			byte[] digest = this.getDigest(
//					(byte[]) cmsProcessableByteArray.getContent(),
//					getDigestAlgorithm());
//
//			X509Certificate x509Cer = (X509Certificate) this.getSignerCert()
//					.getX509cer();
//
//			List<X509Certificate> certList = new ArrayList<X509Certificate>();
//			certList.add(x509Cer);
//			Store store = new JcaCertStore(certList);
//
//			CMSSignedDataGenerator generator = new CMSSignedDataGenerator();
//
//			// Settaggio attributi
//			// 1. contentType
//			Attribute a1 = new Attribute(CMSAttributes.contentType, new DERSet(
//					new DERObjectIdentifier(SignedData.data.getId())));
//
//			// 2. Date
//			Date d = new Date();
//			Attribute a2 = new Attribute(CMSAttributes.signingTime, new DERSet(
//					new Time(d)));
//
//			// 3. Hash dei dati
//			Attribute a3 = new Attribute(
//					org.bouncycastle.asn1.cms.CMSAttributes.messageDigest,
//					new DERSet(new DEROctetString(digest)));
//
//			ASN1EncodableVector hashTabAtt = new ASN1EncodableVector();
//			hashTabAtt.add(a1);
//			hashTabAtt.add(a2);
//			hashTabAtt.add(a3);
//
//			DefaultSignedAttributeTableGenerator dsat = new DefaultSignedAttributeTableGenerator(
//					new AttributeTable(hashTabAtt));
//
//			// 4. Genera la firma
//			ContentSigner signer = new JcaContentSignerBuilder(signerAlgorithm)
//					.setProvider(p11Session.getProvider().getName()).build(
//							this.signatureKey);
//			DigestCalculatorProvider dcp = new JcaDigestCalculatorProviderBuilder()
//					.setProvider("BC").build();
//			JcaSignerInfoGeneratorBuilder sigb = new JcaSignerInfoGeneratorBuilder(
//					dcp);
//			sigb.setDirectSignature(false); // include signed attributes
//			sigb.setSignedAttributeGenerator(dsat);
//
//			SignerInfoGenerator sig = sigb.build(signer, x509Cer);
//			generator.addSignerInfoGenerator(sig);
//			generator.addCertificates(store);
//
//			CMSSignedData newSignedData = generator.generate(
//					cmsProcessableByteArray, false);
//
//			// Nuovo elenco di firmatari....
//			Collection<SignerInformation> newSigners = newSignedData
//					.getSignerInfos().getSigners();
//			Collection<SignerInformation> signers = signedData.getSignerInfos()
//					.getSigners();
//			signers.addAll(newSigners);
//
//			// Nuovo elenco certificati
//			Collection newCertificates = newSignedData.getCertificates()
//					.getMatches(null);
//			Collection certificates = signedData.getCertificates().getMatches(
//					null);
//			certificates.addAll(newCertificates);
//
//			SignerInformationStore sis = new SignerInformationStore(signers);
//			JcaCertStore jcs = new JcaCertStore(certificates);
//
//			signedData = CMSSignedData.replaceSigners(signedData, sis);
//			signedData = CMSSignedData.replaceCertificatesAndCRLs(signedData,
//					jcs, null, null);
//
//			this.getPKCS7Stream().write(signedData.getEncoded());
//			this.getPKCS7Stream().flush();
//			this.getPKCS7Stream().close();
//			this.getBitStream().reset();
//
//		} catch (Throwable e) {
//			LogWriter.writeLog(e.getMessage(), e);
//
//		}
//		LogWriter.writeLog("End");
//	} // End addSignature

	/**
	 * Aggiunge una firma CaDES ad una busta firmata PKCS7 DER partendo dal dato
	 * contenuto in "bitStream", utilizzando l'algoritmo definito nelle
	 * variabili digestAlgorithm, signerAlgorithm, digestAlgorithmId firmandola
	 * con la chiave privata corrispondente al certificato contenuto in x509cer.
	 */

//	private void addSignatureCades() {
//		LogWriter.writeLog("addSignature ");
//		LogWriter.writeLog("starting pkcs#7 object detected");
//		try {
//
//			// Non è possibile rileggere lo stream di input, se serve bisogna
//			// utilizzare un ResettableFileInputStream
//			CMSSignedData signedData = new CMSSignedData(this
//					.getPKCS7StreamAsASN1Object().getEncoded());
//
//			// Verificare che il digest sia corretto!!!
//			CMSProcessableByteArray cmsProcessableByteArray = (CMSProcessableByteArray) signedData
//					.getSignedContent();
//			byte[] digest = this.getDigest(
//					(byte[]) cmsProcessableByteArray.getContent(),
//					getDigestAlgorithm());
//
//			X509Certificate x509Cer = null;
//			//(X509Certificate) this.getSignerCert().getX509cer();
//
//			List<X509Certificate> certList = new ArrayList<X509Certificate>();
//			certList.add(x509Cer);
//			Store store = new JcaCertStore(certList);
//
//			CMSSignedDataGenerator generator = new CMSSignedDataGenerator();
//
//			// Settaggio attributi
//			// 1. contentType
//			Attribute a1 = new Attribute(CMSAttributes.contentType, new DERSet(
//					new DERObjectIdentifier(SignedData.data.getId())));
//
//			// 2. Date
//			Date d = new Date();
//			Attribute a2 = new Attribute(CMSAttributes.signingTime, new DERSet(
//					new Time(d)));
//
//			// 3. Hash dei dati
//			Attribute a3 = new Attribute(
//					org.bouncycastle.asn1.cms.CMSAttributes.messageDigest,
//					new DERSet(new DEROctetString(digest)));
//
//			Attribute a4 = null;
//			IssuerSerial issSerial = null;
//			new IssuerSerial(new GeneralNames(
//					new DERSequence(new GeneralName(
//							org.bouncycastle.jce.PrincipalUtil
//									.getIssuerX509Principal(x509Cer)))),
//					new DERInteger(x509Cer.getSerialNumber()));
//			if (this.digestAlgorithmId.getId().equals(
//					org.bouncycastle.cms.CMSSignedDataGenerator.DIGEST_SHA1)) {
//				MessageDigest msgDigest = java.security.MessageDigest
//						.getInstance(CMSSignedGenerator.DIGEST_SHA1, "BC");
//				ESSCertID essCertid = new ESSCertID(msgDigest.digest(x509Cer
//						.getEncoded()), issSerial);
//				a4 = new Attribute(SignedData.id_aa_signingCertificate,
//						new DERSet(new SigningCertificate(essCertid)));
//			} else if (this.digestAlgorithmId.getId().equalsIgnoreCase(
//					org.bouncycastle.cms.CMSSignedDataGenerator.DIGEST_SHA256)) {
//				MessageDigest msgDigest = java.security.MessageDigest
//						.getInstance(CMSSignedGenerator.DIGEST_SHA256, "BC");
//				ESSCertIDv2 essCertidv2 = new ESSCertIDv2(
//						new AlgorithmIdentifier(
//								CMSSignedDataGenerator.DIGEST_SHA256),
//						msgDigest.digest(x509Cer.getEncoded()), issSerial);
//				ESSCertIDv2[] essCertArray = new ESSCertIDv2[1];
//				essCertArray[0] = essCertidv2;
//				a4 = new org.bouncycastle.asn1.cms.Attribute(
//						org.bouncycastle.asn1.pkcs.SignedData.id_aa_signingCertificateV2,
//						new org.bouncycastle.asn1.DERSet(
//								new SigningCertificateV2(essCertArray)));
//			}

//			ASN1EncodableVector hashTabAtt = new ASN1EncodableVector();
//			hashTabAtt.add(a1);
//			hashTabAtt.add(a2);
//			hashTabAtt.add(a3);
//			hashTabAtt.add(a4);
//
//			DefaultSignedAttributeTableGenerator dsat = new DefaultSignedAttributeTableGenerator(
//					new AttributeTable(hashTabAtt));

			// 4. Genera la firma
//			ContentSigner signer = new JcaContentSignerBuilder(signerAlgorithm)
//					.setProvider(p11Session.getProvider().getName()).build(
//							this.signatureKey);
//			DigestCalculatorProvider dcp = new JcaDigestCalculatorProviderBuilder()
//					.setProvider("BC").build();
//			JcaSignerInfoGeneratorBuilder sigb = new JcaSignerInfoGeneratorBuilder(
//					dcp);
//			sigb.setDirectSignature(false); // include signed attributes
//			sigb.setSignedAttributeGenerator(dsat);
//
//			SignerInfoGenerator sig = sigb.build(signer, x509Cer);
//			generator.addSignerInfoGenerator(sig);
//			generator.addCertificates(store);
//
//			CMSSignedData newSignedData = generator.generate(
//					cmsProcessableByteArray, false);
//
//			// Nuovo elenco di firmatari....
//			Collection<SignerInformation> newSigners = newSignedData
//					.getSignerInfos().getSigners();
//			Collection<SignerInformation> signers = signedData.getSignerInfos()
//					.getSigners();
//			signers.addAll(newSigners);
//
//			// Nuovo elenco certificati
//			Collection newCertificates = newSignedData.getCertificates()
//					.getMatches(null);
//			Collection certificates = signedData.getCertificates().getMatches(
//					null);
//			certificates.addAll(newCertificates);
//
//			SignerInformationStore sis = new SignerInformationStore(signers);
//			JcaCertStore jcs = new JcaCertStore(certificates);
//
//			signedData = CMSSignedData.replaceSigners(signedData, sis);
//			signedData = CMSSignedData.replaceCertificatesAndCRLs(signedData,
//					jcs, null, null);
//
//			this.getPKCS7Stream().write(signedData.getEncoded());
//			this.getPKCS7Stream().flush();
//			this.getPKCS7Stream().close();
//			this.getBitStream().reset();

//		} catch (Throwable e) {
//			LogWriter.writeLog(e.getMessage(), e);
//
//		}
//		LogWriter.writeLog("End");
//	} // End addSignatureCades

	/**
	 * Aggiunge una controfirma ad un firma applicata all'oggetto ASN1 e
	 * individuabile dal certificato aCertToBeSign presente nella variabile
	 * pkcs7StreamAsASN1Object. Il dato firmato viene restituito in formato DER.
	 * 
	 * @param aCert
	 *            corrispondente alla chiave con cui apporre la controfirma.
	 * @param aCertToBeSign
	 *            corrispondente alla firma da controfirmare.
	 */
//	private void addCounterSignature(X509Certificate aCert,
//			X509Certificate aCertToBeSign) {
//		LogWriter.writeLog("Controfirma di: " + aCertToBeSign.getIssuerX500Principal());
//		LogWriter.writeLog("con:            " + aCert.getIssuerX500Principal());
//		try {
//			// Non è possibile rileggere lo stream di input, se serve bisogna
//			// utilizzare un ResettableFileInputStream
//			CMSSignedData signedData = new CMSSignedData(this
//					.getPKCS7StreamAsASN1Object().getEncoded());
//
//			SignerInformation signerInfoToBeCounterSign = this
//					.findSignerInfoFor(signedData, aCertToBeSign);
//
//			if (signerInfoToBeCounterSign == null)
//				return;
//
//			// Recupera il digest dalla firma originale
//			byte[] digest = this.getDigest(
//					signerInfoToBeCounterSign.getSignature(),
//					getDigestAlgorithm());
//
//			List<X509Certificate> certList = new ArrayList<X509Certificate>();
//			certList.add(aCert);
//			Store store = new JcaCertStore(certList);
//
//			// Settaggio attributi
//
//			// 1. Date
//			Date d = new Date();
//			Attribute a1 = new Attribute(CMSAttributes.signingTime, new DERSet(
//					new Time(d)));
//
//			// 2. Hash dei dati
//			Attribute a2 = new Attribute(
//					org.bouncycastle.asn1.cms.CMSAttributes.messageDigest,
//					new DERSet(new DEROctetString(digest)));
//
//			ASN1EncodableVector hashTabAtt = new ASN1EncodableVector();
//			hashTabAtt.add(a1);
//			hashTabAtt.add(a2);
//
//			DefaultSignedAttributeTableGenerator dsat = new DefaultSignedAttributeTableGenerator(
//					new AttributeTable(hashTabAtt));
//
//			// 4. Genera la firma
//			ContentSigner signer = new JcaContentSignerBuilder(signerAlgorithm)
//					.setProvider(p11Session.getProvider().getName()).build(
//							this.signatureKey);
//			DigestCalculatorProvider dcp = new JcaDigestCalculatorProviderBuilder()
//					.setProvider("BC").build();
//			JcaSignerInfoGeneratorBuilder sigb = new JcaSignerInfoGeneratorBuilder(
//					dcp);
//			sigb.setSignedAttributeGenerator(dsat);
//			SignerInfoGenerator sig = sigb.build(signer, aCert);
//
//			CMSSignedDataGenerator generator = new CMSSignedDataGenerator();
//			generator.addSignerInfoGenerator(sig);
//			generator.addCertificates(store);
//
//			SignerInformationStore newSignerInformationStore = generator
//					.generateCounterSigners(signerInfoToBeCounterSign);
//			SignerInformation signerInfoCounterSign = SignerInformation
//					.addCounterSigners(signerInfoToBeCounterSign,
//							newSignerInformationStore);
//
//			// Nuovo elenco di firmatari....
//			Collection<SignerInformation> signers = signedData.getSignerInfos()
//					.getSigners();
//
//			signers.remove(signerInfoToBeCounterSign);
//			signers.add(signerInfoCounterSign);
//
//			// Nuovo elenco certificati
//			Collection newCertificates = store.getMatches(null);
//			Collection certificates = signedData.getCertificates().getMatches(
//					null);
//			certificates.addAll(newCertificates);
//
//			SignerInformationStore sis = new SignerInformationStore(signers);
//			JcaCertStore jcs = new JcaCertStore(certificates);
//
//			signedData = CMSSignedData.replaceSigners(signedData, sis);
//			signedData = CMSSignedData.replaceCertificatesAndCRLs(signedData,
//					jcs, null, null);
//
//			this.getPKCS7Stream().write(signedData.getEncoded());
//			this.getPKCS7Stream().flush();
//			this.getPKCS7Stream().close();
//			this.getBitStream().reset();
//		} catch (Throwable e) {
//			LogWriter.writeLog(e.getMessage(), e);
//		}
//		LogWriter.writeLog("End");
//	} // End addCounterSignature

	/**
	 * Aggiunge una controfirma cades ad un firma applicata all'oggetto ASN1 e
	 * individuabile dal certificato aCertToBeSign presente nella variabile
	 * pkcs7StreamAsASN1Object. Il dato firmato viene restituito in formato DER.
	 * 
	 * @param aCert
	 *            corrispondente alla chiave con cui apporre la controfirma.
	 * @param aCertToBeSign
	 *            corrispondente alla firma da controfirmare.
	 */
//	private void addCounterSignatureCades(X509Certificate aCert,
//			X509Certificate aCertToBeSign) {
//		LogWriter.writeLog("Controfirma di: " + aCertToBeSign.getIssuerX500Principal());
//		LogWriter.writeLog("con:            " + aCert.getIssuerX500Principal());
//		try {
//			// Non è possibile rileggere lo stream di input, se serve bisogna
//			// utilizzare un ResettableFileInputStream
//			CMSSignedData signedData = new CMSSignedData(this
//					.getPKCS7StreamAsASN1Object().getEncoded());
//
//			SignerInformation signerInfoToBeCounterSign = this
//					.findSignerInfoFor(signedData, aCertToBeSign);
//
//			if (signerInfoToBeCounterSign == null)
//				return;
//
//			// Recupera il digest dalla firma originale
//			byte[] digest = this.getDigest(
//					signerInfoToBeCounterSign.getSignature(),
//					getDigestAlgorithm());
//
//			List<X509Certificate> certList = new ArrayList<X509Certificate>();
//			certList.add(aCert);
//			Store store = new JcaCertStore(certList);
//
//			// Settaggio attributi
//
//			// 1. Date
//			Date d = new Date();
//			Attribute a1 = new Attribute(CMSAttributes.signingTime, new DERSet(
//					new Time(d)));
//
//			// 2. Hash dei dati
//			Attribute a2 = new Attribute(
//					org.bouncycastle.asn1.cms.CMSAttributes.messageDigest,
//					new DERSet(new DEROctetString(digest)));
//
//			Attribute a3 = null;
//			IssuerSerial issSerial = new IssuerSerial(new GeneralNames(
//					new DERSequence(new GeneralName(
//							org.bouncycastle.jce.PrincipalUtil
//									.getIssuerX509Principal(aCert)))),
//					new DERInteger(aCert.getSerialNumber()));
//			if (this.digestAlgorithmId.getId().equals(
//					org.bouncycastle.cms.CMSSignedDataGenerator.DIGEST_SHA1)) {
//				MessageDigest msgDigest = java.security.MessageDigest
//						.getInstance(CMSSignedGenerator.DIGEST_SHA1, "BC");
//				ESSCertID essCertid = new ESSCertID(msgDigest.digest(aCert
//						.getEncoded()), issSerial);
//				a3 = new Attribute(SignedData.id_aa_signingCertificate,
//						new DERSet(new SigningCertificate(essCertid)));
//			} else if (this.digestAlgorithmId.getId().equalsIgnoreCase(
//					org.bouncycastle.cms.CMSSignedDataGenerator.DIGEST_SHA256)) {
//				MessageDigest msgDigest = java.security.MessageDigest
//						.getInstance(CMSSignedGenerator.DIGEST_SHA256, "BC");
//				ESSCertIDv2 essCertidv2 = new ESSCertIDv2(
//						new AlgorithmIdentifier(
//								CMSSignedDataGenerator.DIGEST_SHA256),
//						msgDigest.digest(aCert.getEncoded()), issSerial);
//				ESSCertIDv2[] essCertArray = new ESSCertIDv2[1];
//				essCertArray[0] = essCertidv2;
//				a3 = new org.bouncycastle.asn1.cms.Attribute(
//						org.bouncycastle.asn1.pkcs.SignedData.id_aa_signingCertificateV2,
//						new org.bouncycastle.asn1.DERSet(
//								new SigningCertificateV2(essCertArray)));
//			}
//
//			ASN1EncodableVector hashTabAtt = new ASN1EncodableVector();
//			hashTabAtt.add(a1);
//			hashTabAtt.add(a2);
//			hashTabAtt.add(a3);
//
//			DefaultSignedAttributeTableGenerator dsat = new DefaultSignedAttributeTableGenerator(
//					new AttributeTable(hashTabAtt));
//
//			// 4. Genera la firma
//			ContentSigner signer = new JcaContentSignerBuilder(signerAlgorithm)
//					.setProvider(p11Session.getProvider().getName()).build(
//							this.signatureKey);
//			DigestCalculatorProvider dcp = new JcaDigestCalculatorProviderBuilder()
//					.setProvider("BC").build();
//			JcaSignerInfoGeneratorBuilder sigb = new JcaSignerInfoGeneratorBuilder(
//					dcp);
//			sigb.setSignedAttributeGenerator(dsat);
//			SignerInfoGenerator sig = sigb.build(signer, aCert);
//
//			CMSSignedDataGenerator generator = new CMSSignedDataGenerator();
//			generator.addSignerInfoGenerator(sig);
//			generator.addCertificates(store);
//
//			SignerInformationStore newSignerInformationStore = generator
//					.generateCounterSigners(signerInfoToBeCounterSign);
//			SignerInformation signerInfoCounterSign = SignerInformation
//					.addCounterSigners(signerInfoToBeCounterSign,
//							newSignerInformationStore);
//
//			// Nuovo elenco di firmatari....
//			Collection<SignerInformation> signers = signedData.getSignerInfos()
//					.getSigners();
//
//			signers.remove(signerInfoToBeCounterSign);
//			signers.add(signerInfoCounterSign);
//
//			// Nuovo elenco certificati
//			Collection newCertificates = store.getMatches(null);
//			Collection certificates = signedData.getCertificates().getMatches(
//					null);
//			certificates.addAll(newCertificates);
//
//			SignerInformationStore sis = new SignerInformationStore(signers);
//			JcaCertStore jcs = new JcaCertStore(certificates);
//
//			signedData = CMSSignedData.replaceSigners(signedData, sis);
//			signedData = CMSSignedData.replaceCertificatesAndCRLs(signedData,
//					jcs, null, null);
//
//			this.getPKCS7Stream().write(signedData.getEncoded());
//			this.getPKCS7Stream().flush();
//			this.getPKCS7Stream().close();
//			this.getBitStream().reset();
//		} catch (Throwable e) {
//			LogWriter.writeLog(e.getMessage(), e);
//		}
//		LogWriter.writeLog("End");
//	} // End addCounterSignatureCades

	/**
	 * Esegue una firma semplice su uno stream di input.
	 * 
	 * @param aCert
	 *            certificato con cui firmare.
	 * @param in
	 *            stream (ResettableFileInputStream) della busta PKCS7.
	 * @param out
	 *            stream di output della busta con la controfirma.
	 * @param pin
	 *            del token.
	 * @return ProviderResponse con l'esito dell'operazione.
	 */
//	public ProviderResponse sign(CertificatoImpl aCert,
//			ResettableFileInputStream in, OutputStream out, String pin) {
//		LogWriter.writeLog("Firmo con: " + aCert.getSubject());
//		return this.nonRepudiationSign(aCert, in, out);
//	} // End sign

	/**
	 * Esegue la controfirma selezionando il tipo di firma (Cades/non Cades) in
	 * base alla data di entrata in vigore della normativa vigente.
	 * 
	 * @param aCert
	 *            certificato con cui firmare.
	 * @param in
	 *            ResettableFileInputStream stream della busta PKCS7.
	 * @param out
	 *            OutputStream stream di output della busta con la controfirma.
	 * @param aCertToCounterSign
	 *            certificato che indica quale firma controfirmare.
	 * @param pin
	 *            del token.
	 * @return ProviderResponse con l'esito dell'operazione.
	 */
//	public ProviderResponse counterSign(CertificatoImpl aCert,
//			ResettableFileInputStream in, OutputStream out,
//			CertificatoImpl aCertToCounterSign, String pin) {
//		response = new ProviderResponse();
//		if (!this.openSession(aCert))
//			return response;
//		if (!this.getSignatureKeyForX509Cert(aCert, aCert.getToken().getPIN()))
//			return response;
//		LogWriter.writeLog("counterSign ");
//		LogWriter.writeLog("starting");
//		this.setSignerCert(aCert);
//		this.setBitStream(in);
//		this.setPKCS7Stream(out);
//		ASN1ObjectIdentifier oid = this.detectInputStreamContent();
//		if (oid == null) {
//			LogWriter.writeLog("no oid detected");
//			return response;
//		}
//		if (SignedData.signedData.equals(oid)) {
//			LogWriter.writeLog("oid detected = " + oid);
//			X509Certificate xCert = (X509Certificate) aCertToCounterSign
//					.getX509cer();
//			X509Certificate xSignerCert = (X509Certificate) aCert.getX509cer();
//			// this.addCounterSignature(xSignerCert, xCert);
//			if (!Boolean.valueOf(Config.getInstance().getProperty(
//					Config.PROP_CNIPA2009_ENABLED))) {
//				this.addCounterSignature(xSignerCert, xCert);
//			} else {
//				this.addCounterSignatureCades(xSignerCert, xCert);
//			}
//			return response;
//		}
//
//		this.getResponse().setErrorCode(315, null);
//
//		return response;
//	} // End counterSign

	/**
	 * Apre la sessione PKCS11
	 * 
	 * @param aCert
	 * @return true se l'operazione è riuscita, false in caso contrario.
	 */
//	private boolean openSession(CertificatoImpl aCert) {
//		LogWriter.writeLog("openSession ");
//		if (p11Session != null)
//			return true;
//		TokenImpl aToken = (TokenImpl) aCert.getToken();
//		p11Session = (KeyStore) aToken
//				.getEngine("java.security.KeyStore");		
//		try {
//			p11Session.load(null, aCert.getToken().getPIN().toCharArray());
//			return true;
//		} catch (Throwable e) {
//			LogWriter.writeLog(e.getMessage(), e);
//			response.setValue(null);
//			this.checkException(e);
//		}
//		return false;
//	} // End openSession

	/**
	 * Recupera la chiave di firma
	 * 
	 * @param aCert
	 *            CertificatoImpl
	 * @param pin
	 *            del token
	 * @return true se l'operazione riesce, false in caso contrario
	 */
//	private boolean getSignatureKeyForX509Cert(CertificatoImpl aCert, String pin) {
//		LogWriter.writeLog("getSignatureKeyForX509Cert ");
//		try {
//			LogWriter.writeLog("Starting");
//			X509Certificate sunCertificate = (X509Certificate) aCert.getX509cer();
//
//			if (!this.sessionLogin(pin))
//				return false;
//
//			Enumeration aliasesEnum = p11Session.aliases();
//			while (aliasesEnum.hasMoreElements()) {
//				String alias = (String) aliasesEnum.nextElement();
//				X509Certificate cert = (X509Certificate) p11Session
//						.getCertificate(alias);
//				if (cert.equals(sunCertificate)) {
//					signatureKey = (PrivateKey) p11Session.getKey(alias,
//							pin.toCharArray());
//					return true;
//				}
//			}
//		} catch (Throwable e) {
//			LogWriter.writeLog(e.getMessage(), e);
//			response.setValue(null);
//			this.checkException(e);
//			return false;
//		}
//		this.getResponse().setErrorCode(312, null);
//		this.getResponse().setValue(null);
//		return false;
//	} // End getSignatureKeyForX509Cert

	/**
	 * Calcola il digest di un input stream
	 * 
	 * @param dataInputStream
	 *            dati da elaborare
	 * @param digestAlgorithm
	 *            algoritmo di digest da utilizzare
	 * @return l'hash calcolato
	 */
//	private byte[] getDigest(InputStream dataInputStream, String digestAlgorithm) {
//		LogWriter.writeLog("getDigest ");
//		try {
//			MessageDigest digestEngine = MessageDigest
//					.getInstance(digestAlgorithm);
//			contentBuffer = new ByteArrayOutputStream();
//			byte[] dataBuffer = new byte[1024];
//			int bytesRead;
//			while ((bytesRead = dataInputStream.read(dataBuffer)) >= 0) {
//				digestEngine.update(dataBuffer, 0, bytesRead);
//				contentBuffer.write(dataBuffer, 0, bytesRead);
//			}
//			byte[] contentHash = digestEngine.digest();
//			contentBuffer.close();
//
//			return contentHash;
//		} catch (Throwable e) {
//			LogWriter.writeLog(e.getMessage(), e);
//			response.setValue(null);
//			this.getResponse().setErrorCode(314, null);
//		}
//
//		return null;
//	} // End getDigest

	/**
	 * Calcola il digest di un byte array
	 * 
	 * @param aByteArray
	 *            dati da elaborare
	 * @param digestAlgorithm
	 *            algoritmo di digest da utilizzare
	 * @return l'hash calcolato
	 */
//	private byte[] getDigest(byte[] aByteArray, String digestAlgorithm) {
//		LogWriter.writeLog("getDigest ByteArray ");
//		try {
//			LogWriter.writeLog("Starting");
//			MessageDigest digestEngine = MessageDigest
//					.getInstance(digestAlgorithm);
//			contentBuffer = new ByteArrayOutputStream();
//			for (int index = 0; index < aByteArray.length; index++) {
//				digestEngine.update(aByteArray[index]);
//				contentBuffer.write(aByteArray[index]);
//			}
//			byte[] contentHash = digestEngine.digest();
//			contentBuffer.close();
//			return contentHash;
//		} catch (Throwable e) {
//			LogWriter.writeLog(e.getMessage(), e);
//			 
//		}
//		return null;
//	} // End getDigest

	/**
	 * Il contenuto dello stream "bitStream" e, nel caso in cui sia un PKCS7, lo
	 * trasforma in un oggetto ASN1 settato nella variabile
	 * pkcs7StreamAsASN1Object.
	 * 
	 * @return ASN1ObjectIdentifier OID della busta PKCS7
	 */
//	private ASN1ObjectIdentifier detectInputStreamContent() {
//		LogWriter.writeLog("detectInputStreamContent ");
//		LogWriter.writeLog("Starting");
//		ASN1ObjectIdentifier oid = null;
//		try {
//			this.getBitStream().mark(0);
//			ASN1InputStream asnInputStream = new ASN1InputStream(
//					this.getBitStream());
//			this.getBitStream().reset();
//			
//			// Se non è una sequence lancia una eccezione
//			BERSequence asn1Object = (BERSequence) asnInputStream.readObject();
//			int cN = asn1Object.size();
//			for (int index = 0; index < cN; index++)
//				LogWriter.writeLog("asn1Stream content = "
//						+ asn1Object.getObjectAt(index));
//			if (cN > 0) {
//				oid = (ASN1ObjectIdentifier) asn1Object.getObjectAt(0);
//				this.setPKCS7StreamAsASN1Object(asn1Object);
//			}
//		} catch (Throwable e) {
//			LogWriter.writeLog(e.getMessage(), e);
//		}
//		try {
//			this.getBitStream().reset();
//		} catch (IOException ioe) {
//			LogWriter.writeLog("Error reset InputStream", ioe);
//		}
//		return oid;
//	} // End detectInputStreamContent

	/**
	 * Apre la sessione PKCS11
	 * 
	 * @param pin
	 *            del token
	 * @return true se la login è andata bene, false altrimenti.
	 */
//	private boolean sessionLogin(String pin) {
//		LogWriter.writeLog("sessionLogin ");
//		try {
//			p11Session.load(null, pin.toCharArray());
//			return true;
//		} catch (Throwable e) {
//			LogWriter.writeLog(e.getMessage(), e);
//			response.setValue(null);
//			this.checkException(e);
//		}
//		return false;
//	} // sessionLogin

	/**
	 * Verifica se in una busta PKCS7 è già presente una firma effettuta con la
	 * chiave associata al certificato con cui si sta cercando di fare la firma
	 * 
	 * @return true se presente, false in caso contrario.
	 */
//	private boolean checkSignatureAlreadyPresent() {
//		LogWriter.writeLog("checkSignature ");
//		LogWriter.writeLog("starting");
//		try {
//			CMSSignedData sd = new CMSSignedData(this
//					.getPKCS7StreamAsASN1Object().getEncoded());
//			X509Certificate signerCer = null;//this.getSignerCert().getX509cer();
//
//			// Recupera tutti i firmatari della busta firmata
//			Store store = sd.getCertificates();
//			Collection c = sd.getSignerInfos().getSigners();
//			Iterator it = c.iterator();
//			JcaX509CertificateConverter converter = new JcaX509CertificateConverter();
//			while (it.hasNext()) {
//				SignerInformation signer = (SignerInformation) it.next();
//				Collection certCollection = store.getMatches(signer.getSID());
//				Iterator certIt = certCollection.iterator();
//				X509CertificateHolder bccert = (X509CertificateHolder) certIt
//						.next();
//				X509Certificate signCer = converter.setProvider("BC")
//						.getCertificate(bccert);
//				if (signCer.equals(signerCer)) {
//					System.out.println("Signature already present ["
//							+ signerCer.getIssuerX500Principal().getName()
//							+ "] [" + signerCer.getSerialNumber() + "]");
////					this.getResponse()
////							.setErrorCode(
////									316,
////									"["
////											+ signerCer
////													.getIssuerX500Principal()
////													.getName() + "] ["
////											+ signerCer.getSerialNumber() + "]");
//					return true;
//				}
//			}
//		} catch (Throwable e) {
//			//this.getResponse().setErrorCode(317, null);
//			LogWriter.writeLog(e.toString(), e);
//			return true;
//		}
//
//		return false;
//	} // End checkSignatureAlreadyPresent

	/**
	 * Verifica se in un campo SignedData di busta PKCS7 è già presente una
	 * firma effettuta con la chiave associata al certificato aCertToBeSign
	 * 
	 * @param aSignedData
	 *            CMSSignedData
	 * @param aCertToBeSign
	 *            Certificato
	 * @return SignerInformation se trova la firma, null in caso contrario.
	 */
	private SignerInformation findSignerInfoFor(CMSSignedData aSignedData,
			X509Certificate aCertToBeSign) {
		LogWriter.writeLog("existSignerInfoFor ");
		LogWriter.writeLog("starting");
		try {
			SignerInformationStore sInfos = aSignedData.getSignerInfos();
			Store store = aSignedData.getCertificates();
			Collection c = sInfos.getSigners();
			Iterator it = c.iterator();
			JcaX509CertificateConverter converter = new JcaX509CertificateConverter();
			while (it.hasNext()) {
				SignerInformation signer = (SignerInformation) it.next();
				Collection certCollection = store.getMatches(signer.getSID());
				Iterator certIt = certCollection.iterator();
				X509CertificateHolder bccert = (X509CertificateHolder) certIt
						.next();
				X509Certificate signCer = converter.setProvider("BC")
						.getCertificate(bccert);
				if (signCer.equals(aCertToBeSign)) {
					LogWriter.writeLog("Signature present ["
							+ aCertToBeSign.getIssuerX500Principal().getName()
							+ "] [" + aCertToBeSign.getSerialNumber() + "]");
					return signer;

				}
			}
			//this.getResponse().setErrorCode(319, null);
		} catch (Throwable e) {
			//this.getResponse().setErrorCode(318, aSignedData.toString());
			LogWriter.writeLog(e.toString(), e);
		}

		return null;
	} // findSignerInfoFor

	/**
	 * Verifica la tipologia di eccezione e definisce un messaggio di risposta.
	 * 
	 * @param e
	 */
	private void checkException(Throwable e) {
		if (e instanceof UnrecoverableKeyException) {
//			this.getResponse().setErrorCode(301, null);
//			this.getResponse().setCause(e);
			return;
		}
		if (e instanceof UnrecoverableKeyException) {
//			this.getResponse().setErrorCode(301, null);
//			this.getResponse().setCause(e);
			return;
		}
		if (e instanceof CertificateException) {
//			this.getResponse().setErrorCode(302, null);
//			this.getResponse().setCause(e);
			return;
		}
		// Aggiunto per BouncyCastle
		if (e instanceof NoSuchAlgorithmException) {
			NoSuchAlgorithmException te = (NoSuchAlgorithmException) e;
//			this.getResponse().setErrorCode(304, null);
//			this.getResponse().setCause(te);
			return;
		}
		// Aggiunto per BouncyCastle
		if (e instanceof IOException) {
//			this.getResponse().setErrorCode(305, null);
//			this.getResponse().setCause(e);
			return;
		}
//		this.getResponse().setErrorCode(303, null);
//		this.getResponse().setCause(e);
	} // End checkException

	// Getter and Setter
//	public ProviderResponse getResponse() {
//		if (response == null)
//			response = new ProviderResponse();
//		return response;
//	}

//	public void setBitStream(ResettableFileInputStream aStream) {
//		bitStream = aStream;
//	}
//
//	public ResettableFileInputStream getBitStream() {
//		return bitStream;
//	}
//
//	public void setPKCS7Stream(OutputStream aStream) {
//		pkcs7Stream = aStream;
//	}
//
//	public OutputStream getPKCS7Stream() {
//		return pkcs7Stream;
//	}
//
//	public void setInputStreamByteArray(byte[] anArray) {
//		inputStreamByteArray = anArray;
//	}

//	public byte[] getInputStreamByteArray() {
//		return inputStreamByteArray;
//	}
//
//	public CertificatoImpl getSignerCert() {
//		return signerCert;
//	}
//
//	public void setSignerCert(CertificatoImpl aCert) {
//		signerCert = aCert;
//	}
//
//	public ASN1Object getPKCS7StreamAsASN1Object() {
//		return pkcs7StreamAsASN1Object;
//	}
//
//	public void setPKCS7StreamAsASN1Object(ASN1Object pkcs7Object) {
//		pkcs7StreamAsASN1Object = pkcs7Object;
//	}
//
//	public String getDigestAlgorithm() {
//		return digestAlgorithm;
//	}
//
//	public void setDigestAlgorithm(String digestAlgorithm) {
//		this.digestAlgorithm = digestAlgorithm;
//	}
//
//	public DERObjectIdentifier getDigestAlgorithmId() {
//		return digestAlgorithmId;
//	}
//
//	public void setDigestAlgorithmId(DERObjectIdentifier digestAlgorithmId) {
//		this.digestAlgorithmId = digestAlgorithmId;
//	}
} // End Signer
