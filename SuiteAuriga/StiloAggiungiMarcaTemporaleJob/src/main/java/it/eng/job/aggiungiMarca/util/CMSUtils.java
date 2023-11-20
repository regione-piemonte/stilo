/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.Security;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.codec.binary.Base64InputStream;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.io.LineIterator;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.bouncycastle.asn1.ASN1Encodable;
import org.bouncycastle.asn1.ASN1EncodableVector;
import org.bouncycastle.asn1.ASN1Generator;
import org.bouncycastle.asn1.ASN1OctetStringParser;
import org.bouncycastle.asn1.ASN1SequenceParser;
import org.bouncycastle.asn1.ASN1Set;
import org.bouncycastle.asn1.ASN1SetParser;
import org.bouncycastle.asn1.ASN1StreamParser;
import org.bouncycastle.asn1.ASN1TaggedObject;
import org.bouncycastle.asn1.BEROctetStringGenerator;
import org.bouncycastle.asn1.BERSequenceGenerator;
import org.bouncycastle.asn1.BERSet;
import org.bouncycastle.asn1.BERSetParser;
import org.bouncycastle.asn1.BERTaggedObject;
import org.bouncycastle.asn1.DERSet;
import org.bouncycastle.asn1.DERTaggedObject;
import org.bouncycastle.asn1.DERTags;
import org.bouncycastle.asn1.cms.CMSObjectIdentifiers;
import org.bouncycastle.asn1.cms.ContentInfoParser;
import org.bouncycastle.asn1.cms.SignedDataParser;
import org.bouncycastle.cert.X509AttributeCertificateHolder;
import org.bouncycastle.cert.X509CRLHolder;
import org.bouncycastle.cert.X509CertificateHolder;
import org.bouncycastle.cms.CMSException;
import org.bouncycastle.cms.CMSSignedData;
import org.bouncycastle.cms.CMSSignedDataParser;
import org.bouncycastle.cms.SignerInformation;
import org.bouncycastle.cms.SignerInformationStore;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.operator.jcajce.JcaDigestCalculatorProviderBuilder;
import org.bouncycastle.util.Store;
import org.bouncycastle.util.io.Streams;

public class CMSUtils {

	private static Logger mLogger = Logger.getLogger(CMSUtils.class);

	public static int maxByteRead = 100 * 1000 * 1000;
	
	public static void writeSetToGeneratorTagged(ASN1Generator asn1Gen, ASN1SetParser asn1SetParser, int tagNo)
			throws IOException {
		ASN1Set asn1Set = getASN1Set(asn1SetParser);

		if (asn1Set != null) {
			ASN1TaggedObject taggedObj = (asn1SetParser instanceof BERSetParser)
					? new BERTaggedObject(false, tagNo, asn1Set) : new DERTaggedObject(false, tagNo, asn1Set);

			asn1Gen.getRawOutputStream().write(taggedObj.getEncoded());
		}
	}

	public static ASN1Set getASN1Set(ASN1SetParser asn1SetParser) {
		return asn1SetParser == null ? null : ASN1Set.getInstance(asn1SetParser.toASN1Primitive());
	}

	public static OutputStream createBEROctetOutputStream(OutputStream s, int tagNo, boolean isExplicit, int bufferSize)
			throws IOException {
		BEROctetStringGenerator octGen = new BEROctetStringGenerator(s, tagNo, isExplicit);

		if (bufferSize != 0) {
			return octGen.getOctetOutputStream(new byte[bufferSize]);
		}

		return octGen.getOctetOutputStream();
	}

	public static ASN1Set createBerSetFromList(List derObjects) {
		ASN1EncodableVector v = new ASN1EncodableVector();

		for (Iterator it = derObjects.iterator(); it.hasNext();) {
			v.add((ASN1Encodable) it.next());
		}

		return new BERSet(v);
	}

	public static List getAttributeCertificatesFromStore(Store attrStore) throws CMSException {
		List certs = new ArrayList();

		try {
			for (Iterator it = attrStore.getMatches(null).iterator(); it.hasNext();) {
				X509AttributeCertificateHolder attrCert = (X509AttributeCertificateHolder) it.next();

				certs.add(new DERTaggedObject(false, 2, attrCert.toASN1Structure()));
			}

			return certs;
		} catch (ClassCastException e) {
			throw new CMSException("error processing certs", e);
		}
	}

	public static List getCertificatesFromStore(Store certStore) throws CMSException {
		List certs = new ArrayList();

		try {
			for (Iterator it = certStore.getMatches(null).iterator(); it.hasNext();) {
				X509CertificateHolder c = (X509CertificateHolder) it.next();

				certs.add(c.toASN1Structure());
			}

			return certs;
		} catch (ClassCastException e) {
			throw new CMSException("error processing certs", e);
		}
	}

	public static void pipeEncapsulatedOctetString(ContentInfoParser encapContentInfo, OutputStream rawOutputStream)
			throws IOException {
		ASN1OctetStringParser octs = (ASN1OctetStringParser) encapContentInfo.getContent(DERTags.OCTET_STRING);

		if (octs != null) {
			pipeOctetString(octs, rawOutputStream);
		}
	}

	private static void pipeOctetString(ASN1OctetStringParser octs, OutputStream output) throws IOException {
		// TODO Allow specification of a specific fragment size?
		OutputStream outOctets = createBEROctetOutputStream(output, 0, true, 0);
		Streams.pipeAll(octs.getOctetStream(), outOctets);
		outOctets.close();
	}

	public static List getCRLsFromStore(Store crlStore) throws CMSException {
		List certs = new ArrayList();

		try {
			for (Iterator it = crlStore.getMatches(null).iterator(); it.hasNext();) {
				X509CRLHolder c = (X509CRLHolder) it.next();

				certs.add(c.toASN1Structure());
			}

			return certs;
		} catch (ClassCastException e) {
			throw new CMSException("error processing certs", e);
		}
	}

	public static OutputStream replaceSignerAndCertificatesAndCRLs(byte[] originalData,
			SignerInformationStore signerInformationStore, Store certs, Store crls, Store attrCerts, OutputStream out)
			throws CMSException, IOException {
		
		ContentInfoParser contentInfo = null;
		try {
			ASN1StreamParser in = new ASN1StreamParser(originalData);			
			contentInfo = new ContentInfoParser((ASN1SequenceParser) in.readObject());
		} catch(Exception e) {
			Base64InputStream streambase64 = getBase64InputStreamWithoutBeginEndLines(originalData);												
			ASN1StreamParser in = new ASN1StreamParser(streambase64);			
			contentInfo = new ContentInfoParser((ASN1SequenceParser) in.readObject());				
		}
		SignedDataParser signedData = SignedDataParser.getInstance(contentInfo.getContent(DERTags.SEQUENCE));
		BERSequenceGenerator sGen = new BERSequenceGenerator(out);
		sGen.addObject(CMSObjectIdentifiers.signedData);
		BERSequenceGenerator sigGen = new BERSequenceGenerator(sGen.getRawOutputStream(), 0, true);
		// version number
		sigGen.addObject(signedData.getVersion());
		// digests
		// sigGen.getRawOutputStream().write(signedData.getDigestAlgorithms().getDERObject().getEncoded());
		// digests
		signedData.getDigestAlgorithms().toASN1Primitive(); // skip old ones
		ASN1EncodableVector digestAlgs = new ASN1EncodableVector();
		for (Iterator it = signerInformationStore.getSigners().iterator(); it.hasNext();) {
			SignerInformation signer = (SignerInformation) it.next();
			digestAlgs.add(CMSSignedHelper.INSTANCE.fixAlgID(signer.getDigestAlgorithmID()));
		}
		sigGen.getRawOutputStream().write(new DERSet(digestAlgs).getEncoded());
		// encap content info
		ContentInfoParser encapContentInfo = signedData.getEncapContentInfo();
		BERSequenceGenerator eiGen = new BERSequenceGenerator(sigGen.getRawOutputStream());
		eiGen.addObject(encapContentInfo.getContentType());
		CMSUtils.pipeEncapsulatedOctetString(encapContentInfo, eiGen.getRawOutputStream());
		eiGen.close();
		//
		// skip existing certs and CRLs
		//
		CMSUtils.getASN1Set(signedData.getCertificates());
		CMSUtils.getASN1Set(signedData.getCrls());
		//
		// replace the certs and crls in the SignedData object
		//
		// System.out.println("cert "+certs);
		// System.out.println("attrCerts "+attrCerts);
		if (certs != null || attrCerts != null) {
			List certificates = new ArrayList();

			if (certs != null) {
				certificates.addAll(CMSUtils.getCertificatesFromStore(certs));
			}
			if (attrCerts != null) {
				certificates.addAll(CMSUtils.getAttributeCertificatesFromStore(attrCerts));
			}

			ASN1Set asn1Certs = CMSUtils.createBerSetFromList(certificates);

			if (asn1Certs.size() > 0) {
				sigGen.getRawOutputStream().write(new DERTaggedObject(false, 0, asn1Certs).getEncoded());
			}
		}

		if (crls != null) {
			ASN1Set asn1Crls = CMSUtils.createBerSetFromList(CMSUtils.getCRLsFromStore(crls));

			if (asn1Crls.size() > 0) {
				sigGen.getRawOutputStream().write(new DERTaggedObject(false, 1, asn1Crls).getEncoded());
			}
		}

		// sigGen.getRawOutputStream().write(signedData.getSignerInfos().getDERObject().getEncoded());

		// aggiungo i nuovi signerinfo
		ASN1EncodableVector signerInfos = new ASN1EncodableVector();
		for (Iterator it = signerInformationStore.getSigners().iterator(); it.hasNext();) {
			SignerInformation signer = (SignerInformation) it.next();

			signerInfos.add(signer.toSignerInfo());
		}

		sigGen.getRawOutputStream().write(new DERSet(signerInfos).getEncoded());

		sigGen.close();

		sGen.close();

		return out;
	}
	
	public static LineIterator lineIterator(byte[] bytes) throws IOException {
		InputStream in = null;
        try {
        	in = new ByteArrayInputStream(bytes);
			return IOUtils.lineIterator(in, null);
	    } catch (IOException ex) {
	    	IOUtils.closeQuietly(in);
	        throw ex;
	    } catch (RuntimeException ex) {
	    	IOUtils.closeQuietly(in);
	        throw ex;
	    }
	}	
	
	public static Base64InputStream getBase64InputStreamWithoutBeginEndLines(byte[] signedData) throws IOException {
		// Controllo se il file comincia per -----BEGIN
		LineIterator iterator = lineIterator(signedData);				
		String firstline = iterator.nextLine();
		mLogger.debug("first line " + firstline );
		if (StringUtils.containsIgnoreCase(firstline, "-----BEGIN")) {
			// Riscrivo il file
			File tmpFile = File.createTempFile("tmp", ".tmp");
			tmpFile.deleteOnExit();
			mLogger.debug(tmpFile.getAbsolutePath());
			BufferedWriter writer = new BufferedWriter(new FileWriter(tmpFile));
			while (iterator.hasNext()) {
				String line = iterator.nextLine();
				if (!StringUtils.containsIgnoreCase(line, "-----END")) {
					writer.write(line);
					writer.newLine();
					writer.flush();
				} else {
					writer.close();
				}
			}				
			if (Security.getProvider(BouncyCastleProvider.PROVIDER_NAME)==null){
				Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
			}						
			// tento una lettura con max bytes se va in errore non lo leggo
			new ASN1StreamParser(new Base64InputStream(FileUtils.openInputStream(tmpFile)), CMSUtils.maxByteRead).readObject();						
			FileInputStream fisTmp =  FileUtils.openInputStream(tmpFile);						
			Base64InputStream streambase64 = new Base64InputStream(fisTmp);												
			return streambase64;					
		}
		return null;
	}
	
	public static CMSSignedDataParser getCMSSignedDataParser(File file) throws Exception {
		byte[] signedData = IOUtils.toByteArray(FileUtils.openInputStream(file));
		//return getCMSSignedDataParser(signedData);		
		CMSSignedDataParser cmsSignedDataParser = null;
		InputStream stream = null;
		File tmp = null;
		LineIterator iterator = null;
		FileInputStream fisTmp = null;
		FileInputStream fis2 = null;
		try {
			stream = FileUtils.openInputStream(file);
			cmsSignedDataParser = InstanceCMSSignedDataParser.getCMSSignedDataParser(stream, false);
			//cmsSignedDataParser = new CMSSignedDataParser(signedData);
		} catch (CMSException cmse) {
			cmse.printStackTrace();
			// Controllo se il file comincia per -----BEGIN
			iterator = FileUtils.lineIterator(file);
			String firstline = iterator.nextLine();
			System.out.println("firstline " + firstline );
			if (StringUtils.containsIgnoreCase(firstline, "-----BEGIN")) {
				tmp = File.createTempFile("tmp", ".tmp");
				BufferedWriter writer = new BufferedWriter(new FileWriter(tmp));
				while (iterator.hasNext()) {
					String line = iterator.nextLine();
					if (!StringUtils.containsIgnoreCase(line, "-----END")) {
						writer.write(line);
						writer.newLine();
						writer.flush();
					} else {
						writer.close();
					}
				}
				// tento una lettura con max bytes se va in errore non lo leggo
				new ASN1StreamParser(new Base64InputStream(FileUtils.openInputStream(tmp)), maxByteRead).readObject();
				// Base64InputStream streambase64 = new Base64InputStream(FileUtils.openInputStream(tmp));

				cmsSignedDataParser = InstanceCMSSignedDataParser.getCMSSignedDataParser(FileUtils.openInputStream(tmp), true);
				

			} else {
				// tento una lettura con max bytes se va in errore non lo leggo
				fisTmp = FileUtils.openInputStream(file);
				// log.info("Apro lo stream " + fisTmp + " sul file " + file );
				new ASN1StreamParser(new Base64InputStream(fisTmp), maxByteRead).readObject();

				// Base64InputStream streambase64 = new Base64InputStream(FileUtils.openInputStream(file));
				fis2 = FileUtils.openInputStream(file);
				// log.info("Apro lo stream " + fis2 + " sul file " + file );
				cmsSignedDataParser = InstanceCMSSignedDataParser.getCMSSignedDataParser(fis2, true);
				// new CMSSignedDataParser(streambase64);

				
			}
			
			//Base64InputStream streambase64 = getBase64InputStreamWithoutBeginEndLines(signedData);												
			//cmsSignedDataParser = new CMSSignedDataParser(/*new JcaDigestCalculatorProviderBuilder().setProvider("BC").build(), */streambase64);								
		}
		return cmsSignedDataParser;
	}
	
	/*public static CMSSignedDataParser getCMSSignedDataParser(InputStream in) throws Exception {
		byte[] signedData = IOUtils.toByteArray(in);
		return getCMSSignedDataParser(signedData);		
	}
	
	public static CMSSignedDataParser getCMSSignedDataParser(byte[] signedData) throws Exception {
		CMSSignedDataParser cmsSignedDataParser = null;
		try {
			cmsSignedDataParser = InstanceCMSSignedDataParser.getCMSSignedDataParser(stream, false);
			cmsSignedDataParser = new CMSSignedDataParser(signedData);
		} catch (CMSException cmse) {
			cmse.printStackTrace();
			Base64InputStream streambase64 = getBase64InputStreamWithoutBeginEndLines(signedData);												
			cmsSignedDataParser = new CMSSignedDataParser(new JcaDigestCalculatorProviderBuilder().setProvider("BC").build(), streambase64);								
		}
		return cmsSignedDataParser;
	}*/
	
	public static CMSSignedData getCMSSignedData(File file) throws Exception {
		byte[] signedData = IOUtils.toByteArray(FileUtils.openInputStream(file));
		return getCMSSignedData(signedData);
	}		
		
	public static CMSSignedData getCMSSignedData(InputStream in) throws Exception {
		byte[] signedData = IOUtils.toByteArray(in);
		return getCMSSignedData(signedData);
	}		
	
	public static CMSSignedData getCMSSignedData(byte[] signedData) throws Exception {
		CMSSignedData cmsSignedData = null;
		try {
			cmsSignedData = new CMSSignedData(signedData);
		} catch (CMSException e) {
			Base64InputStream streambase64 = getBase64InputStreamWithoutBeginEndLines(signedData);												
			cmsSignedData = new CMSSignedData(streambase64);							
		}
		return cmsSignedData;
	}

}
