package it.eng.hybrid.module.firmaCertificato.util;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.cert.CertificateEncodingException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.bouncycastle.asn1.ASN1Encodable;
import org.bouncycastle.asn1.ASN1EncodableVector;
import org.bouncycastle.asn1.ASN1Generator;
import org.bouncycastle.asn1.ASN1OctetStringParser;
import org.bouncycastle.asn1.ASN1Primitive;
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
import org.bouncycastle.asn1.x509.TBSCertificateStructure;
import org.bouncycastle.cert.X509AttributeCertificateHolder;
import org.bouncycastle.cert.X509CRLHolder;
import org.bouncycastle.cert.X509CertificateHolder;
import org.bouncycastle.cms.CMSException;
import org.bouncycastle.cms.CMSSignedData;
import org.bouncycastle.cms.CMSSignedDataParser;
import org.bouncycastle.cms.SignerInformation;
import org.bouncycastle.cms.SignerInformationStore;
import org.bouncycastle.util.CollectionStore;
import org.bouncycastle.util.Store;
import org.bouncycastle.util.io.Streams;

public class CMSUtils {

	public  static OutputStream createBEROctetOutputStream(OutputStream s,
			int tagNo, boolean isExplicit, int bufferSize) throws IOException 	{
		BEROctetStringGenerator octGen = new BEROctetStringGenerator(s, tagNo, isExplicit);

		if (bufferSize != 0) {
			return octGen.getOctetOutputStream(new byte[bufferSize]);
		}

		return octGen.getOctetOutputStream();
	}
	
	public static TBSCertificateStructure getTBSCertificateStructure(
			X509Certificate cert) throws CertificateEncodingException
			{
		try
		{
			return TBSCertificateStructure.getInstance(ASN1Primitive
					.fromByteArray(cert.getTBSCertificate()));
		}
		catch (IOException e)
		{
			throw new CertificateEncodingException(e.toString());
		}
			}
	
	public static ASN1Set createBerSetFromList(List derObjects)
	{
		ASN1EncodableVector v = new ASN1EncodableVector();

		for (Iterator it = derObjects.iterator(); it.hasNext();)
		{
			v.add((ASN1Encodable)it.next());
		}

		return new BERSet(v);
	}

	private static ASN1Set getASN1Set(
			ASN1SetParser asn1SetParser) {
		return asn1SetParser == null
				?   null :   ASN1Set.getInstance(asn1SetParser.toASN1Primitive());
	}

	public static void writeSetToGeneratorTagged(
			ASN1Generator asn1Gen,
			ASN1SetParser asn1SetParser,
			int           tagNo)
					throws IOException	{
		ASN1Set asn1Set = getASN1Set(asn1SetParser);

		if (asn1Set != null) {
			ASN1TaggedObject taggedObj = (asn1SetParser instanceof BERSetParser)
				?   new BERTaggedObject(false, tagNo, asn1Set)
				:   new DERTaggedObject(false, tagNo, asn1Set);
			asn1Gen.getRawOutputStream().write(taggedObj.getEncoded());                
		}
	}
	
	
	
	public static OutputStream addSignerInfo(File inputFile,
			CMSSignedData otherCmsSignedData,
			OutputStream outputStream)throws Exception{
		//
		//apro il file firmato
		//
		InputStream streamIn = FileUtils.openInputStream(inputFile);	
		CMSSignedDataParser cmsSignedData = InstanceCMSSignedDataParser.getCMSSignedDataParser(streamIn,false);
		//CMSSignedDataParser cmsSignedData= new CMSSignedDataParser(FileUtils.openInputStream(inputFile));
		cmsSignedData.getSignedContent().drain();
		Collection  signers = (Collection )cmsSignedData.getSignerInfos().getSigners();
		List certList = new ArrayList();
		//aggiungo i cert attuali  alla lista
		Store store=cmsSignedData.getCertificates();
		Collection coll=store.getMatches(null);
		Store store2=otherCmsSignedData.getCertificates();
		Collection coll2=store2.getMatches(null);
		certList.addAll(coll);
		certList.addAll(coll2);
		//TODO evitare i duplicati!?
		//ricostruico lo store con i nuovi cert
		CollectionStore newCertstores= new CollectionStore(certList);
		//aggiungo i signerinfo a quelli attuali
		 signers.addAll(otherCmsSignedData.getSignerInfos().getSigners());
		OutputStream stream=replaceSignerAndCertificatesAndCRLs(FileUtils.openInputStream(inputFile), new SignerInformationStore(signers), newCertstores, null, null, outputStream);
		return stream;
	}
	
	public static OutputStream replaceSignerAndCertificatesAndCRLs(
			InputStream   original,
			SignerInformationStore signerInformationStore,
			Store certs,
			Store crls,
			Store attrCerts,
			OutputStream  out)
	throws CMSException, IOException
	{
		ASN1StreamParser in = new ASN1StreamParser(original, Integer.MAX_VALUE);
		ContentInfoParser contentInfo = new ContentInfoParser((ASN1SequenceParser)in.readObject());
		SignedDataParser signedData = SignedDataParser.getInstance(contentInfo.getContent(DERTags.SEQUENCE));
		BERSequenceGenerator sGen = new BERSequenceGenerator(out);
		sGen.addObject(CMSObjectIdentifiers.signedData);
		BERSequenceGenerator sigGen = new BERSequenceGenerator(sGen.getRawOutputStream(), 0, true);
		// version number
		sigGen.addObject(signedData.getVersion());
		// digests
		//sigGen.getRawOutputStream().write(signedData.getDigestAlgorithms().getDERObject().getEncoded());
		// digests
		signedData.getDigestAlgorithms().toASN1Primitive();  // skip old ones
		ASN1EncodableVector digestAlgs = new ASN1EncodableVector();
		for (Iterator it = signerInformationStore.getSigners().iterator(); it.hasNext();)
		{
			SignerInformation signer = (SignerInformation)it.next();
			digestAlgs.add(CMSSignedHelper.INSTANCE.fixAlgID(signer.getDigestAlgorithmID()));
		}
		sigGen.getRawOutputStream().write(new DERSet(digestAlgs).getEncoded());
		// encap content info
		ContentInfoParser encapContentInfo = signedData.getEncapContentInfo();
		BERSequenceGenerator eiGen = new BERSequenceGenerator(sigGen.getRawOutputStream());
		eiGen.addObject(encapContentInfo.getContentType());
		pipeEncapsulatedOctetString(encapContentInfo, eiGen.getRawOutputStream());
		eiGen.close();
		//
		// skip existing certs and CRLs
		//
		getASN1Set(signedData.getCertificates());
		getASN1Set(signedData.getCrls());
		//
		// replace the certs and crls in the SignedData object
		//
		System.out.println("cert "+certs);
		System.out.println("attrCerts "+attrCerts);
		if (certs != null || attrCerts != null)
		{
			List certificates = new ArrayList();

			if (certs != null)
			{
				certificates.addAll(CMSUtils.getCertificatesFromStore(certs));
			}
			if (attrCerts != null)
			{
				certificates.addAll(CMSUtils.getAttributeCertificatesFromStore(attrCerts));
			}

			ASN1Set asn1Certs = CMSUtils.createBerSetFromList(certificates);

			if (asn1Certs.size() > 0)
			{
				sigGen.getRawOutputStream().write(new DERTaggedObject(false, 0, asn1Certs).getEncoded());
			}
		}

		if (crls != null)
		{
			ASN1Set asn1Crls = CMSUtils.createBerSetFromList(CMSUtils.getCRLsFromStore(crls));

			if (asn1Crls.size() > 0)
			{
				sigGen.getRawOutputStream().write(new DERTaggedObject(false, 1, asn1Crls).getEncoded());
			}
		}

		// sigGen.getRawOutputStream().write(signedData.getSignerInfos().getDERObject().getEncoded());

		//aggiungo i nuovi signerinfo
		ASN1EncodableVector signerInfos = new ASN1EncodableVector();
		for (Iterator it = signerInformationStore.getSigners().iterator(); it.hasNext();)
		{
			SignerInformation        signer = (SignerInformation)it.next();

			//signerInfos.add(signer.toSignerInfo());
			signerInfos.add(signer.toASN1Structure());
		}

		sigGen.getRawOutputStream().write(new DERSet(signerInfos).getEncoded());

		sigGen.close();

		sGen.close();

		return out;
	}
	
	public  static List getCRLsFromStore(Store crlStore)
	throws CMSException
	{
		List certs = new ArrayList();

		try
		{
			for (Iterator it = crlStore.getMatches(null).iterator(); it.hasNext();)
			{
				X509CRLHolder c = (X509CRLHolder)it.next();

				certs.add(c.toASN1Structure());
			}

			return certs;
		}
		catch (ClassCastException e)
		{
			throw new CMSException("error processing certs", e);
		}
	}
	
	public  static List getAttributeCertificatesFromStore(Store attrStore)
	throws CMSException
	{
		List certs = new ArrayList();

		try
		{
			for (Iterator it = attrStore.getMatches(null).iterator(); it.hasNext();)
			{
				X509AttributeCertificateHolder attrCert = (X509AttributeCertificateHolder)it.next();

				certs.add(new DERTaggedObject(false, 2, attrCert.toASN1Structure()));
			}

			return certs;
		}
		catch (ClassCastException e)
		{
			throw new CMSException("error processing certs", e);
		}
	}
	
	public  static List getCertificatesFromStore(Store certStore)
	throws CMSException
	{
		List certs = new ArrayList();

		try
		{
			for (Iterator it = certStore.getMatches(null).iterator(); it.hasNext();)
			{
				X509CertificateHolder c = (X509CertificateHolder)it.next();

				certs.add(c.toASN1Structure());
			}

			return certs;
		}
		catch (ClassCastException e)
		{
			throw new CMSException("error processing certs", e);
		}
	}
	
	public static void pipeEncapsulatedOctetString(ContentInfoParser encapContentInfo,
			OutputStream rawOutputStream) throws IOException
			{
		ASN1OctetStringParser octs = (ASN1OctetStringParser)
		encapContentInfo.getContent(DERTags.OCTET_STRING);

		if (octs != null)
		{
			pipeOctetString(octs, rawOutputStream);
		}
			}
	
	private static void pipeOctetString(
			ASN1OctetStringParser octs,
			OutputStream          output)
	throws IOException
	{
		// TODO Allow specification of a specific fragment size?
				OutputStream outOctets = createBEROctetOutputStream(
						output, 0, true, 0);
				Streams.pipeAll(octs.getOctetStream(), outOctets);
				outOctets.close();
	}

}


