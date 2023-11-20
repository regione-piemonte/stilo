/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.GeneralSecurityException;
import java.security.MessageDigest;
import java.security.PrivateKey;
import java.security.cert.Certificate;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.Hex;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;
import org.bouncycastle.asn1.ASN1InputStream;
import org.bouncycastle.asn1.ASN1ObjectIdentifier;
import org.bouncycastle.asn1.ASN1SequenceParser;
import org.bouncycastle.asn1.ASN1Set;
import org.bouncycastle.asn1.ASN1StreamParser;
import org.bouncycastle.asn1.BERSequenceGenerator;
import org.bouncycastle.asn1.BERTags;
import org.bouncycastle.asn1.DLSet;
import org.bouncycastle.asn1.cms.CMSObjectIdentifiers;
import org.bouncycastle.asn1.cms.ContentInfo;
import org.bouncycastle.asn1.cms.ContentInfoParser;
import org.bouncycastle.asn1.cms.SignedData;
import org.bouncycastle.asn1.cms.SignedDataParser;
import org.bouncycastle.cms.CMSProcessable;
import org.bouncycastle.cms.CMSProcessableByteArray;
import org.bouncycastle.cms.CMSSignedData;
import org.bouncycastle.cms.CMSSignedDataParser;
import org.bouncycastle.cms.CMSSignedGenerator;
import org.bouncycastle.cms.SignerInformationStore;
import org.bouncycastle.util.CollectionStore;
import org.bouncycastle.util.Store;

import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.AcroFields;
import com.itextpdf.text.pdf.PdfDictionary;
import com.itextpdf.text.pdf.PdfName;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfSignature;
import com.itextpdf.text.pdf.PdfSignatureAppearance;
import com.itextpdf.text.pdf.PdfStamper;
import com.itextpdf.text.pdf.PdfString;
import com.itextpdf.text.pdf.security.DigestAlgorithms;
import com.itextpdf.text.pdf.security.ExternalDigest;
import com.itextpdf.text.pdf.security.MakeSignature.CryptoStandard;
import com.itextpdf.text.pdf.security.PdfPKCS7;

import it.eng.common.bean.SignerObjectBean;
import it.eng.fileOperation.clientws.DigestAlgID;
import it.eng.fileOperation.clientws.DigestEncID;

public class SignerHashUtil {

	private static Logger mLogger = Logger.getLogger(SignerHashUtil.class);

	public static OutputStream attachP7M(byte[] originalData, InputStream detached, OutputStream out) {
		try {
			ASN1StreamParser in = new ASN1StreamParser(detached, Integer.MAX_VALUE);
			ContentInfoParser contentInfo = new ContentInfoParser((ASN1SequenceParser) in.readObject());
			SignedDataParser signedData = SignedDataParser.getInstance(contentInfo.getContent(BERTags.SEQUENCE));
			BERSequenceGenerator sGen = new BERSequenceGenerator(out);
			sGen.addObject(CMSObjectIdentifiers.signedData);
			BERSequenceGenerator sigGen = new BERSequenceGenerator(sGen.getRawOutputStream(), 0, true);
			// version number
			sigGen.addObject(signedData.getVersion());
			// digest alg
			sigGen.addObject(signedData.getDigestAlgorithms());
			// encap content info
			ContentInfoParser encapContentInfo = signedData.getEncapContentInfo();
			BERSequenceGenerator eiGen = new BERSequenceGenerator(sigGen.getRawOutputStream());
			eiGen.addObject(encapContentInfo.getContentType());
			// TODO tagnuber
			OutputStream encapStream = CMSUtils.createBEROctetOutputStream(eiGen.getRawOutputStream(), 0, true, 1024);
			// IOUtils.copy(new
			// ByteArrayInputStream(IOUtils.toByteArray(originalData)),
			// encapStream);
			IOUtils.copy(new ByteArrayInputStream(originalData), encapStream);
			encapStream.close();
			eiGen.close();
			CMSUtils.writeSetToGeneratorTagged(sigGen, signedData.getCertificates(), 0);
			CMSUtils.writeSetToGeneratorTagged(sigGen, signedData.getCrls(), 1);
			sigGen.getRawOutputStream().write(signedData.getSignerInfos().toASN1Primitive().getEncoded());
			sigGen.close();
			sGen.close();
			return out;
		} catch (Throwable e) {
			mLogger.error(e.getMessage(), e);
			return null;
		}
	}

	public static CMSSignedData signerP7M(SignerObjectBean bean, byte[] originalData) throws Exception {
		DLSet digest = (DLSet) new ASN1InputStream(Base64.decodeBase64(bean.getDigestAlgs())).readObject();
		DLSet signerInfos = (DLSet) new ASN1InputStream(Base64.decodeBase64(bean.getSignerInfo())).readObject();
		ASN1Set certificates = (ASN1Set) new ASN1InputStream(Base64.decodeBase64(bean.getCert())).readObject();
		ASN1Set certrevlist = null;
		if (bean.getCrl() != null) {
			certrevlist = (ASN1Set) new ASN1InputStream(Base64.decodeBase64(bean.getCrl())).readObject();
		}
		ContentInfo encInfo = new ContentInfo(new ASN1ObjectIdentifier(CMSSignedGenerator.DATA), null);
		SignedData sd = new SignedData(digest, encInfo, certificates, certrevlist, signerInfos);
		ContentInfo contentInfo = new ContentInfo(CMSObjectIdentifiers.signedData, sd);
		CMSProcessable content = new CMSProcessableByteArray(originalData);
		CMSSignedData data = new CMSSignedData(content, contentInfo);
		return data;
	}

	public static void attachPdf(byte[] signedData, FileElaborate fileElaborate) throws Exception {
		try {
			PdfDictionary dictionary = new PdfDictionary();
			int contentEstimated = (int) 15000L;
			byte[] paddedSig = new byte[contentEstimated];
			System.arraycopy(signedData, 0, paddedSig, 0, signedData.length);
			dictionary.put(PdfName.CONTENTS, new PdfString(paddedSig).setHexWriting(true));
			fileElaborate.getPdfsignature().close(dictionary);
			FileUtils.writeByteArrayToFile(fileElaborate.getSigned(), fileElaborate.getOutStream().toByteArray());
		} catch (Throwable e) {
			mLogger.error(e.getMessage(), e);
			throw new Exception(e);
		}
	}

	public static SignerObjectBean signerPDF(PrivateKey key, X509Certificate certificate, byte[] pdfHash)
			throws Exception {
		try {
			SignerObjectBean bean = new SignerObjectBean();
			ExternalDigest externalDigest = new ExternalDigest() {
				public MessageDigest getMessageDigest(String hashAlgorithm) throws GeneralSecurityException {
					return DigestAlgorithms.getMessageDigest(hashAlgorithm, null);
				}
			};
			HashAlgorithm digest = HashAlgorithm.SHA256;
			// System.out.println("digest " + digest);
			// System.out.println("hash " + pdfHash );
			PdfPKCS7 pk7 = new PdfPKCS7(key, new Certificate[] { certificate }, digest.getAlgorithmName(), null,
					externalDigest, false);
			// PdfPKCS7 pk7 = new PdfPKCS7(key, new Certificate[]{certificate},
			// null, digest.name(), provider.getName(), false);
			Calendar cal = GregorianCalendar.getInstance();
			byte sh[] = pk7.getAuthenticatedAttributeBytes(pdfHash, cal, null, null, CryptoStandard.CADES);
			// byte sh[] = pk7.getAuthenticatedAttributeBytes(pdfHash, cal,
			// null);
			pk7.update(sh, 0, sh.length);
			byte sg[] = pk7.getEncodedPKCS7(pdfHash, cal, null, null, null, CryptoStandard.CADES);
			// byte sg[] = pk7.getEncodedPKCS7(hash, cal, null);
			bean.setSignerInfo(sg);
			bean.getCertificates().add(certificate);
			bean.setDigestAlgs(pdfHash);
			return bean;
		} catch (Throwable e) {
			mLogger.error(e.getMessage(), e);
			throw new Exception(e);
		}
	}

	public static void calcolaHashPdf(FileElaborate fileElaborate, DigestAlgID algId, DigestEncID encId) throws Exception {
		InputStream streamDaFirmare = new FileInputStream(fileElaborate.getUnsigned());
		PdfReader pdfReader = new PdfReader(streamDaFirmare);
		AcroFields af = pdfReader.getAcroFields();
		ArrayList<String> names = af.getSignatureNames();
		ByteArrayOutputStream fout = new ByteArrayOutputStream();
		PdfStamper stamper = null;
		HashAlgorithm algorithm;
		if (algId.equals(DigestAlgID.SHA_256)) {
			algorithm = HashAlgorithm.SHA256;
		} else {
			algorithm = HashAlgorithm.SHA1;
		}
		if (names.size() == 0) {
			stamper = PdfStamper.createSignature(pdfReader, fout, algorithm.getPdfVersion());
		} else {
			stamper = PdfStamper.createSignature(pdfReader, fout, algorithm.getPdfVersion(), null, true);
		}
		PdfSignatureAppearance appearance = stamper.getSignatureAppearance();
		appearance.setCertificationLevel(CertificationLevel.NOT_CERTIFIED.getLevel());
		Rectangle rect = pdfReader.getPageSize(1);
		float w = rect.getWidth();
		float h = rect.getHeight();
		appearance.setVisibleSignature(new Rectangle(w - 200, h - 200, w - 200, h - 200), 1, null);
		appearance.setLayer2Text("Firma Digitale...");
		PdfSignature dic = new PdfSignature(PdfName.ADOBE_PPKLITE, PdfName.ETSI_CADES_DETACHED);
		// dic.put(PdfName.FT, PdfName.SIG);
		// Calendar cal = Calendar.getInstance();
		// dic.put(PdfName.M, new PdfDate(cal));
		// dic.setDate(new PdfDate(appearance.getSignDate()));
		appearance.setCryptoDictionary(dic);
		HashMap<PdfName, Integer> exc = new HashMap<PdfName, Integer>();
		int contentEstimated = (int) 15000L;
		exc.put(PdfName.CONTENTS, new Integer((contentEstimated * 2) + 2));
		appearance.preClose(exc);
		byte[] hash = null;
		if (algId.equals(DigestAlgID.SHA_256)) {
			hash = DigestUtils.sha256(appearance.getRangeStream());
		} else {
			hash = DigestUtils.sha(appearance.getRangeStream());
		}
		String encodedHash = null;
		if (encId.equals(DigestEncID.BASE_64)) {
			encodedHash = Base64.encodeBase64String(hash);
		} else if (encId.equals(DigestEncID.HEX)) {
			encodedHash = Hex.encodeHexString(hash);
		}
		fileElaborate.setPdfsignature(appearance);
		fileElaborate.setHash(hash);
		fileElaborate.setEncodedHash(encodedHash);
		fileElaborate.setOutStream(fout);
	}

	public static void setSignatureAppearance(FileElaborate fileElaborate, DigestAlgID algId) throws Exception {
		InputStream streamDaFirmare = new FileInputStream(fileElaborate.getUnsigned());
		PdfReader pdfReader = new PdfReader(streamDaFirmare);
		AcroFields af = pdfReader.getAcroFields();
		ArrayList<String> names = af.getSignatureNames();
		ByteArrayOutputStream fout = new ByteArrayOutputStream();
		PdfStamper stamper = null;
		HashAlgorithm algorithm;
		if (algId.equals(DigestAlgID.SHA_256)) {
			algorithm = HashAlgorithm.SHA256;
		} else {
			algorithm = HashAlgorithm.SHA1;
		}
		if (names.size() == 0) {
			stamper = PdfStamper.createSignature(pdfReader, fout, algorithm.getPdfVersion());
		} else {
			stamper = PdfStamper.createSignature(pdfReader, fout, algorithm.getPdfVersion(), null, true);
		}
		PdfSignatureAppearance appearance = stamper.getSignatureAppearance();
		appearance.setCertificationLevel(CertificationLevel.NOT_CERTIFIED.getLevel());
		Rectangle rect = pdfReader.getPageSize(1);
		float w = rect.getWidth();
		float h = rect.getHeight();
		appearance.setVisibleSignature(new Rectangle(w - 200, h - 200, w - 200, h - 200), 1, null);
		appearance.setLayer2Text("Firma Digitale...");
		PdfSignature dic = new PdfSignature(PdfName.ADOBE_PPKLITE, PdfName.ETSI_CADES_DETACHED);
		// dic.put(PdfName.FT, PdfName.SIG);
		// Calendar cal = Calendar.getInstance();
		// dic.put(PdfName.M, new PdfDate(cal));
		// dic.setDate(new PdfDate(appearance.getSignDate()));
		appearance.setCryptoDictionary(dic);
		HashMap<PdfName, Integer> exc = new HashMap<PdfName, Integer>();
		int contentEstimated = (int) 15000L;
		exc.put(PdfName.CONTENTS, new Integer((contentEstimated * 2) + 2));
		appearance.preClose(exc);
		fileElaborate.setOutStream(fout);
		fileElaborate.setPdfsignature(appearance);
	}

	public static CMSSignedData createSignedData(SignerObjectBean bean, byte[] originalData) throws Exception {
		DLSet digest = null;
		if (bean.getDigestAlgs() != null) {
			byte[] digestAlg = Base64.decodeBase64(bean.getDigestAlgs());
			mLogger.info("digestAlg " + new String(digestAlg));
			digest = (DLSet) new ASN1InputStream(digestAlg).readObject();
		}
		DLSet signerInfos = (DLSet) new ASN1InputStream(Base64.decodeBase64(bean.getSignerInfo())).readObject();
		ASN1Set certificates = null;
		if (bean.getCert() != null)
			certificates = (ASN1Set) new ASN1InputStream(Base64.decodeBase64(bean.getCert())).readObject();
		ASN1Set certrevlist = null;
		if (bean.getCrl() != null)
			certrevlist = (ASN1Set) new ASN1InputStream(Base64.decodeBase64(bean.getCrl())).readObject();

		ContentInfo encInfo = new ContentInfo(new ASN1ObjectIdentifier(CMSSignedGenerator.DATA), null);
		SignedData sd = new SignedData(digest, encInfo, certificates, certrevlist, signerInfos);

		ContentInfo contentInfo = new ContentInfo(CMSObjectIdentifiers.signedData, sd);

		CMSProcessable content = new CMSProcessableByteArray(originalData);
		CMSSignedData data = new CMSSignedData(content, contentInfo);

		return data;
	}

	public static OutputStream addSignerInfo(byte[] originalData, CMSSignedData otherCmsSignedData,
			OutputStream outputStream) throws Exception {
		//
		// apro il file firmato
		//
		CMSSignedDataParser cmsSignedDataParser = CMSUtils.getCMSSignedDataParser(originalData);
		cmsSignedDataParser.getSignedContent().drain();
		Collection signers = (Collection) cmsSignedDataParser.getSignerInfos().getSigners();
		List certList = new ArrayList();
		// aggiungo i cert attuali alla lista
		Store store = cmsSignedDataParser.getCertificates();
		Collection coll = store.getMatches(null);
		Store store2 = otherCmsSignedData.getCertificates();
		Collection coll2 = store2.getMatches(null);
		certList.addAll(coll);
		certList.addAll(coll2);
		// TODO evitare i duplicati!?
		// ricostruico lo store con i nuovi cert
		CollectionStore newCertstores = new CollectionStore(certList);
		// aggiungo i signerinfo a quelli attuali
		signers.addAll(otherCmsSignedData.getSignerInfos().getSigners());
		cmsSignedDataParser.close();
		OutputStream stream = CMSUtils.replaceSignerAndCertificatesAndCRLs(originalData,
				new SignerInformationStore(signers), newCertstores, null, null, outputStream);
		return stream;
	}

}
