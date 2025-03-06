package it.eng.common;


import it.eng.client.applet.operation.CMSSignedHelper;
import it.eng.common.bean.SignerObjectBean;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.NoSuchProviderException;
import java.security.Provider;
import java.security.Security;
import java.security.cert.CRLException;
import java.security.cert.CertStore;
import java.security.cert.CertStoreException;
import java.security.cert.CertificateEncodingException;
import java.security.cert.CollectionCertStoreParameters;
import java.security.cert.X509CRL;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.bouncycastle.asn1.ASN1Encodable;
import org.bouncycastle.asn1.ASN1EncodableVector;
import org.bouncycastle.asn1.ASN1Encoding;
import org.bouncycastle.asn1.ASN1Generator;
import org.bouncycastle.asn1.ASN1InputStream;
import org.bouncycastle.asn1.ASN1ObjectIdentifier;
import org.bouncycastle.asn1.ASN1OctetString;
import org.bouncycastle.asn1.ASN1OctetStringParser;
import org.bouncycastle.asn1.ASN1Primitive;
import org.bouncycastle.asn1.ASN1SequenceParser;
import org.bouncycastle.asn1.ASN1Set;
import org.bouncycastle.asn1.ASN1SetParser;
import org.bouncycastle.asn1.ASN1StreamParser;
import org.bouncycastle.asn1.ASN1TaggedObject;
import org.bouncycastle.asn1.BERConstructedOctetString;
import org.bouncycastle.asn1.BEROctetStringGenerator;
import org.bouncycastle.asn1.BERSequenceGenerator;
import org.bouncycastle.asn1.BERSet;
import org.bouncycastle.asn1.BERSetParser;
import org.bouncycastle.asn1.BERTaggedObject;
import org.bouncycastle.asn1.DERSet;
import org.bouncycastle.asn1.DERTaggedObject;
import org.bouncycastle.asn1.DERTags;
import org.bouncycastle.asn1.cms.CMSObjectIdentifiers;
import org.bouncycastle.asn1.cms.ContentInfo;
import org.bouncycastle.asn1.cms.ContentInfoParser;
import org.bouncycastle.asn1.cms.SignedData;
import org.bouncycastle.asn1.cms.SignedDataParser;
import org.bouncycastle.asn1.cms.SignerInfo;
import org.bouncycastle.asn1.x509.CertificateList;
import org.bouncycastle.asn1.x509.TBSCertificateStructure;
import org.bouncycastle.asn1.x509.X509CertificateStructure;
import org.bouncycastle.cert.X509AttributeCertificateHolder;
import org.bouncycastle.cert.X509CRLHolder;
import org.bouncycastle.cert.X509CertificateHolder;
import org.bouncycastle.cms.CMSException;
import org.bouncycastle.cms.CMSProcessable;
import org.bouncycastle.cms.CMSProcessableFile;
import org.bouncycastle.cms.CMSSignedData;
import org.bouncycastle.cms.CMSSignedDataParser;
import org.bouncycastle.cms.CMSSignedGenerator;
import org.bouncycastle.cms.DummySignerInformationUtil;
import org.bouncycastle.cms.SignerInformation;
import org.bouncycastle.cms.SignerInformationStore;
import org.bouncycastle.util.CollectionStore;
import org.bouncycastle.util.Store;
import org.bouncycastle.util.io.Streams;

public class CMSUtils
{
	private static final Runtime RUNTIME = Runtime.getRuntime();

	public static int getMaximumMemory()
	{
		long maxMem = RUNTIME.maxMemory();

		if (maxMem > Integer.MAX_VALUE)
		{
			return Integer.MAX_VALUE;
		}

		return (int)maxMem;
	}

	static ContentInfo readContentInfo(
			byte[] input)
	throws CMSException
	{
		// enforce limit checking as from a byte array
		return readContentInfo(new ASN1InputStream(input));
	}

	static ContentInfo readContentInfo(
			InputStream input)
	throws CMSException
	{
		// enforce some limit checking
		return readContentInfo(new ASN1InputStream(input, getMaximumMemory()));
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
	public static List getCertificatesFromStore(CertStore certStore)
	throws CertStoreException, CMSException
	{
		List certs = new ArrayList();

		try
		{
			for (Iterator it = certStore.getCertificates(null).iterator(); it.hasNext();)
			{
				X509Certificate c = (X509Certificate)it.next();

				certs.add(X509CertificateStructure.getInstance(
						ASN1Primitive.fromByteArray(c.getEncoded())));
			}

			return certs;
		}
		catch (IllegalArgumentException e)
		{
			throw new CMSException("error processing certs", e);
		}
		catch (IOException e)
		{
			throw new CMSException("error processing certs", e);
		}
		catch (CertificateEncodingException e)
		{
			throw new CMSException("error encoding certs", e);
		}
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

	public static List getCRLsFromStore(CertStore certStore)
	throws CertStoreException, CMSException
	{
		List crls = new ArrayList();

		try
		{
			for (Iterator it = certStore.getCRLs(null).iterator(); it.hasNext();)
			{
				X509CRL c = (X509CRL)it.next();

				crls.add(CertificateList.getInstance(ASN1Primitive.fromByteArray(c.getEncoded())));
			}

			return crls;
		}
		catch (IllegalArgumentException e)
		{
			throw new CMSException("error processing crls", e);
		}
		catch (IOException e)
		{
			throw new CMSException("error processing crls", e);
		}
		catch (CRLException e)
		{
			throw new CMSException("error encoding crls", e);
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

	static ASN1Set createDerSetFromList(List derObjects)
	{
		ASN1EncodableVector v = new ASN1EncodableVector();

		for (Iterator it = derObjects.iterator(); it.hasNext();)
		{
			v.add((ASN1Encodable)it.next());
		}

		return new DERSet(v);
	}

	public  static OutputStream createBEROctetOutputStream(OutputStream s,
			int tagNo, boolean isExplicit, int bufferSize) throws IOException
			{
		BEROctetStringGenerator octGen = new BEROctetStringGenerator(s, tagNo, isExplicit);

		if (bufferSize != 0)
		{
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

	private static ContentInfo readContentInfo(
			ASN1InputStream in)
	throws CMSException
	{
		try
		{
			return ContentInfo.getInstance(in.readObject());
		}
		catch (IOException e)
		{
			throw new CMSException("IOException reading content.", e);
		}
		catch (ClassCastException e)
		{
			throw new CMSException("Malformed content.", e);
		}
		catch (IllegalArgumentException e)
		{
			throw new CMSException("Malformed content.", e);
		}
	}

	public static byte[] streamToByteArray(
			InputStream in) 
	throws IOException
	{
		return Streams.readAll(in);
	}

	public static byte[] streamToByteArray(
			InputStream in,
			int         limit)
	throws IOException
	{
		return Streams.readAllLimited(in, limit);
	}

	public static Provider getProvider(String providerName)
	throws NoSuchProviderException
	{
		if (providerName != null)
		{
			Provider prov = Security.getProvider(providerName);

			if (prov != null)
			{
				return prov;
			}

			throw new NoSuchProviderException("provider " + providerName + " not found.");
		}

		return null; 
	}

	/**
	 * Crea un file firmato (CADES) a partire dai dati del signerObjectBean e dal contenuto da firmare
	 * SignerObjectBean contiene i certificati da inserire nel file firmato , il digest e  i signerInfos
	 * FIXME non funziona in Stream
	 * @param bean
	 * @return CMSSignedData
	 * @throws Exception
	 */
	public static  CMSSignedData buildFileSigned(SignerObjectBean bean,File sbustato )throws Exception{
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
		;

		CMSProcessable content = new CMSProcessableFile(sbustato);
		ASN1OctetString octs = new BERConstructedOctetString(FileUtils.readFileToByteArray(sbustato));

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
		return data;
		//Aggiugno il certificato alle buste
		//File firmato = new File(sbustato.getAbsolutePath()+".p7m");
		//FileUtils.writeByteArrayToFile(firmato, data.getEncoded());
	}
	/**
	 * aggiunge le firme contenute nell'oggetto signerObjectBean ad uno stream  Firmato di tipo cades
	 * TODO devi aggiungere anche le CRL  ora mancano quelle dei nuovi signe info!!!!
	 * @param file
	 * @param bean
	 * @param outputStream stream dove scrivere la risposta
	 * @throws Exception
	 */
	public static void addSignerInfo(File inputFile,SignerObjectBean bean,OutputStream outputStream)throws Exception{

		//DERSet digest = (DERSet)new ASN1InputStream(bean.getDigestAlgs()).readObject();
		DERSet signerInfos = (DERSet)new ASN1InputStream(bean.getSignerInfo()).readObject();
		ArrayList<SignerInfo> listSinfos= new ArrayList<SignerInfo>();
		for (int i = 0; i != signerInfos.size(); i++)
		{
			SignerInfo info = SignerInfo.getInstance(signerInfos.getObjectAt(i));
			listSinfos.add(info);
		}
		addSignerInfo(  inputFile, listSinfos,bean.getCertificates(),outputStream);
	}

	 /**
	  * aggiunge i signer info ed i certificati ad un file già firmato
	  * @param cmsSignedData CmsSignedData a cui aggiungere
	  * @param inputFile lavoro sui file poichè devo rileggerli dall'inizio, TODO usa ResettableInputStream
	  * @param signerInfos
	  * @param certificates
	  * @param outputStream
	  * @return
	  * @throws Exception
	  */
	public static OutputStream addSignerInfo(File inputFile,
			List<SignerInfo> signerInfos,
			List<X509Certificate> certificates,
			OutputStream outputStream)throws Exception{
		//
		//apro il file firmato
		//
		CMSSignedDataParser cmsSignedData= new CMSSignedDataParser(FileUtils.openInputStream(inputFile));
		cmsSignedData.getSignedContent().drain();
		Collection  signers = (Collection )cmsSignedData.getSignerInfos().getSigners();

		List certList = new ArrayList();
		List _crls = new ArrayList();
		//prendo i cert inviati e li trasformo in x509CertHolder 		
		for(X509Certificate certificate:certificates){
			X509CertificateHolder holder= new X509CertificateHolder(certificate.getEncoded());
			certList.add(holder);
		}
		//aggiungo i cert attuali  alla lista
		Store store=cmsSignedData.getCertificates();
		Collection coll=store.getMatches(null);
		//   	for (Iterator iterator = coll.iterator(); iterator.hasNext();) {
		//			Object certh = (Object) iterator.next();
		//			if(certh instanceof X509CertificateHolder){
		//				
		////				 X509Certificate cert= new JcaX509CertificateConverter().setProvider( "BC" )
		////				  .getCertificate( (X509CertificateHolder)certh );
		////				 certList.add(cert);
		////				return new X509V2AttributeCertificate( attributeCertificateHolder.getEncoded() );
		//				certList.add(certh)
		//			}
		//		}

		certList.addAll(coll);
		//TODO evitare i duplicati
		//ricostruico lo store con i nuovi cert
		CollectionStore newCertstores= new CollectionStore(certList);

		//aggiungo i signerinfo a quelli attuali
		for (int i = 0; i != signerInfos.size(); i++)
		{
			// SignerInfo info = SignerInfo.getInstance(signerInfos.getObjectAt(i));
			SignerInfo info = signerInfos.get(i);
			signers.add(DummySignerInformationUtil.buildSignerInformation (info) );
		}

		//sostiutisco le firme 
		//   	OutputStream stream=CMSSignedDataParser.replaceSigners(FileUtils.openInputStream(file), new SignerInformationStore(signers), new FileOutputStream(new File(file.getAbsolutePath()+".temp")));
		//   	IOUtils.closeQuietly(stream);
		//   	//sostituisco i certificati 
		//   	stream=CMSSignedDataParser.replaceCertificatesAndCRLs(
		//   			FileUtils.openInputStream(new File(file.getAbsolutePath()+".temp")), newCertstores, null, null, new FileOutputStream(new File(file.getAbsolutePath()+".p7m")));
		OutputStream stream=replaceSignerAndCertificatesAndCRLs(FileUtils.openInputStream(inputFile), new SignerInformationStore(signers), newCertstores, null, null, outputStream);
		return stream;
	}
	
	
	public static OutputStream addSignerInfo(File inputFile,
			CMSSignedData otherCmsSignedData,
			OutputStream outputStream)throws Exception{
		//
		//apro il file firmato
		//
		CMSSignedDataParser cmsSignedData= new CMSSignedDataParser(FileUtils.openInputStream(inputFile));
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
	/**
	 * aggunge un certificato rappresentato da un oggetto X509CertificateHolder 
	 * ad una collection verificando che non sia presente
	 * @param store
	 * @param certHolder
	 * @throws Exception
	 */
	private void addCertInStore(Collection store,X509CertificateHolder certHolder)throws Exception{
		boolean found=false; 
		for (Iterator iterator = store.iterator(); iterator.hasNext();) {
			Object object = (Object) iterator.next();
			if(object instanceof X509CertificateHolder){
				found = Arrays.equals(
						((X509CertificateHolder) object).getEncoded(),
						certHolder.getEncoded());
				if(found) break;
			}
		}
		if(!found){
			store.add(certHolder);
		}
	}
	//marca temporalmente i signerInfo passati 
	//    public void addTimeStamp(SignerObjectBean bean)throws Exception{
	//    	 DERSet digest = (DERSet)new ASN1InputStream(bean.getDigestAlgs()).readObject();
	//         DERSet signerInfos = (DERSet)new ASN1InputStream(bean.getSignerInfo()).readObject();
	//        
	//         for (int i = 0; i != signerInfos.size(); i++)
	//         {
	//             SignerInfo info = SignerInfo.getInstance(signerInfos.getObjectAt(i));
	//               TimeStamperUtility.addMarca(info)
	//         }
	//    }

	/**
	 * metodi di utilità per fare il replace di signer e certificati su una busta cades
	 * @param original stream di input in cui fare il replace
	 * @param signerInformationStore signerinfo da sostituire
	 * @param certs certificati da sostituire
	 * @param crls crl da sostituire
	 * @param attrCerts attr cert da sostituire
	 * @param out stream di output 
	 * @return
	 * @throws CMSException
	 * @throws IOException
	 */
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

			signerInfos.add(signer.toSignerInfo());
		}

		sigGen.getRawOutputStream().write(new DERSet(signerInfos).getEncoded());

		sigGen.close();

		sGen.close();

		return out;
	}

	private static ASN1Set getASN1Set(
			ASN1SetParser asn1SetParser)
	{
		return asn1SetParser == null
		?   null
				:   ASN1Set.getInstance(asn1SetParser.toASN1Primitive());
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
				OutputStream outOctets = it.eng.common.CMSUtils.createBEROctetOutputStream(
						output, 0, true, 0);
				Streams.pipeAll(octs.getOctetStream(), outOctets);
				outOctets.close();
	}

	public static void writeSetToGeneratorTagged(
			ASN1Generator asn1Gen,
			ASN1SetParser asn1SetParser,
			int           tagNo)
	throws IOException
	{
		ASN1Set asn1Set = getASN1Set(asn1SetParser);

		if (asn1Set != null)
		{
			ASN1TaggedObject taggedObj = (asn1SetParser instanceof BERSetParser)
			?   new BERTaggedObject(false, tagNo, asn1Set)
			:   new DERTaggedObject(false, tagNo, asn1Set);

			asn1Gen.getRawOutputStream().write(taggedObj.getEncoded());                
		}
	}
	
	///////////////////////
	
}

