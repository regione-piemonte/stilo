package it.eng.hybrid.module.firmaCertificato.signers;

import java.io.IOException;
import java.io.OutputStream;
import java.security.AlgorithmParameters;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.Provider;
import java.security.ProviderException;
import java.security.Security;
import java.security.Signature;
import java.security.SignatureException;
import java.security.cert.CertificateEncodingException;
import java.security.cert.X509Certificate;
import java.security.interfaces.DSAPrivateKey;
import java.security.interfaces.RSAPrivateKey;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.codec.binary.Base64;
import org.apache.log4j.Logger;
import org.bouncycastle.asn1.ASN1Encodable;
import org.bouncycastle.asn1.ASN1EncodableVector;
import org.bouncycastle.asn1.ASN1Encoding;
import org.bouncycastle.asn1.ASN1ObjectIdentifier;
import org.bouncycastle.asn1.ASN1Primitive;
import org.bouncycastle.asn1.ASN1Set;
import org.bouncycastle.asn1.DERNull;
import org.bouncycastle.asn1.DERObjectIdentifier;
import org.bouncycastle.asn1.DEROctetString;
import org.bouncycastle.asn1.DERSet;
import org.bouncycastle.asn1.cms.AttributeTable;
import org.bouncycastle.asn1.cms.CMSAttributes;
import org.bouncycastle.asn1.cms.CMSObjectIdentifiers;
import org.bouncycastle.asn1.cms.ContentInfo;
import org.bouncycastle.asn1.cms.IssuerAndSerialNumber;
import org.bouncycastle.asn1.cms.SignedData;
import org.bouncycastle.asn1.cms.SignerIdentifier;
import org.bouncycastle.asn1.cms.SignerInfo;
import org.bouncycastle.asn1.x509.AlgorithmIdentifier;
import org.bouncycastle.asn1.x509.TBSCertificateStructure;
import org.bouncycastle.asn1.x9.X9ObjectIdentifiers;
import org.bouncycastle.cms.CMSAttributeTableGenerator;
import org.bouncycastle.cms.CMSException;
import org.bouncycastle.cms.CMSSignedData;
import org.bouncycastle.cms.CMSSignedGenerator;
import org.bouncycastle.cms.SignerInformation;
import org.bouncycastle.cms.SimpleAttributeTableGenerator;
import org.bouncycastle.jce.interfaces.GOST3410PrivateKey;

import it.eng.common.bean.SignerObjectBean;
import it.eng.hybrid.module.firmaCertificato.util.CMSUtils;


public class CMSSignedHashGenerator extends CMSSignedGenerator {
	
	public final static Logger logger = Logger.getLogger( CMSSignedHashGenerator.class );
	
	//duplico la mappa poichè nella 1.48 è stato eliminato il metodo getAlgoritm
	private static final Set NO_PARAMS = new HashSet();
	private static final Map EC_ALGORITHMS = new HashMap(); 
	private static final String  ENCRYPTION_ECDSA_WITH_SHA1 = X9ObjectIdentifiers.ecdsa_with_SHA1.getId();
	private static final String  ENCRYPTION_ECDSA_WITH_SHA224 = X9ObjectIdentifiers.ecdsa_with_SHA224.getId();
	private static final String  ENCRYPTION_ECDSA_WITH_SHA256 = X9ObjectIdentifiers.ecdsa_with_SHA256.getId();
	private static final String  ENCRYPTION_ECDSA_WITH_SHA384 = X9ObjectIdentifiers.ecdsa_with_SHA384.getId();
	private static final String  ENCRYPTION_ECDSA_WITH_SHA512 = X9ObjectIdentifiers.ecdsa_with_SHA512.getId();

	static
	{
		NO_PARAMS.add(ENCRYPTION_DSA);
		NO_PARAMS.add(ENCRYPTION_ECDSA);
		NO_PARAMS.add(ENCRYPTION_ECDSA_WITH_SHA1);
		NO_PARAMS.add(ENCRYPTION_ECDSA_WITH_SHA224);
		NO_PARAMS.add(ENCRYPTION_ECDSA_WITH_SHA256);
		NO_PARAMS.add(ENCRYPTION_ECDSA_WITH_SHA384);
		NO_PARAMS.add(ENCRYPTION_ECDSA_WITH_SHA512);

		EC_ALGORITHMS.put(DIGEST_SHA1, ENCRYPTION_ECDSA_WITH_SHA1);
		EC_ALGORITHMS.put(DIGEST_SHA224, ENCRYPTION_ECDSA_WITH_SHA224);
		EC_ALGORITHMS.put(DIGEST_SHA256, ENCRYPTION_ECDSA_WITH_SHA256);
		EC_ALGORITHMS.put(DIGEST_SHA384, ENCRYPTION_ECDSA_WITH_SHA384);
		EC_ALGORITHMS.put(DIGEST_SHA512, ENCRYPTION_ECDSA_WITH_SHA512);
	}

	private List signerInfs = new ArrayList();
	private List<X509Certificate> certificates = new ArrayList<X509Certificate>();

	public CMSSignedHashGenerator(){

	}

	public void addSigner(PrivateKey key,X509Certificate cert,String digestOID){
		certificates.add(cert);
		CustomDefaultSignedAttributeTableGenerator dsatg = new CustomDefaultSignedAttributeTableGenerator();
		signerInfs.add(new SignerInf(cert, key, getSignerIdentifier(cert), digestOID, getEncOID(key, digestOID), dsatg, null, null));
	}

	public void addSigner(
			PrivateKey      key,
			X509Certificate cert,
			String          digestOID,
			AttributeTable  signedAttr,
			AttributeTable  unsignedAttr)
	throws IllegalArgumentException
	{
		certificates.add(cert);
		CustomDefaultSignedAttributeTableGenerator dsatg = new CustomDefaultSignedAttributeTableGenerator(signedAttr);
		signerInfs.add(new SignerInf(cert, key, getSignerIdentifier(cert), digestOID, 
				getEncOID(key, digestOID), dsatg, 
				new SimpleAttributeTableGenerator(unsignedAttr), signedAttr));
	}


	private class SignerInf
	{
		private final X509Certificate cert;
		private final PrivateKey                  key;
		private final SignerIdentifier            signerIdentifier;
		private final String                      digestOID;
		private final String                      encOID;
		private final CMSAttributeTableGenerator  sAttr;
		private final CMSAttributeTableGenerator  unsAttr;
		private final AttributeTable              baseSignedTable;
		
		SignerInf(
				X509Certificate cert,
				PrivateKey                 key,
				SignerIdentifier           signerIdentifier,
				String                     digestOID,
				String                     encOID,
				CMSAttributeTableGenerator sAttr,
				CMSAttributeTableGenerator unsAttr,
				AttributeTable             baseSignedTable)
				{
			this.cert  = cert;
			this.key = key;
			this.signerIdentifier = signerIdentifier;
			this.digestOID = digestOID;
			this.encOID = encOID;
			this.sAttr = sAttr;
			this.unsAttr = unsAttr;
			this.baseSignedTable = baseSignedTable;
				}

		AlgorithmIdentifier getDigestAlgorithmID()
		{
			return new AlgorithmIdentifier(new DERObjectIdentifier(digestOID), new DERNull());
		}

		SignerInfo toSignerInfo(
				ASN1ObjectIdentifier contentType,
				byte[]				hash,
				//SecureRandom        random,
				Provider            sigProvider,
				boolean             addDefaultAttributes,
				boolean             isCounterSignature)
		throws IOException, SignatureException, InvalidKeyException, NoSuchAlgorithmException, CertificateEncodingException, CMSException
		{
			AlgorithmIdentifier digAlgId = getDigestAlgorithmID();
			logger.info("digAlgId " + digAlgId);
			String digestName = CMSSignedHelper.INSTANCE.getDigestAlgName(digestOID);
			logger.info("digestName " + digestName);
			String signatureName = digestName + "with" + CMSSignedHelper.INSTANCE.getEncryptionAlgName(encOID);
			logger.info("signatureName " + signatureName + " Provider " + sigProvider);
			Signature sig = CMSSignedHelper.INSTANCE.getSignatureInstance(signatureName, sigProvider);
			logger.info("sig " + sig );
//			logger.info("I provider caricati con questo nome " + sigProvider + " " + Security.getProviders(sigProvider.getName()).length);
			MessageDigest dig = CMSSignedHelper.INSTANCE.getDigestInstance(digestName, sigProvider);
			logger.info("dig " + dig);
			AlgorithmIdentifier encAlgId = getEncAlgorithmIdentifier(encOID, sig);
			logger.info("encAlgId " + encAlgId);
			
			//test = CMSUtils.isEquivalent(paramAlgorithmIdentifier1, encAlgId);
			
			//            if (content != null)
				//            {
				//                content.write(new DigOutputStream(dig));
				//            }

			//byte[] hash = dig.digest();
			digests.put(digestOID, hash.clone());

			logger.info("addDefaultAttributes " + addDefaultAttributes);
			logger.info("isCounterSignature " + isCounterSignature);
			AttributeTable signed;
			if (addDefaultAttributes)
			{
				logger.info("contentType " + contentType );
				logger.info("digAlgId " + digAlgId );
				logger.info("hash " + hash );
				Map parameters = getBaseParameters(contentType, digAlgId, hash);
				parameters.put("signatureAlgID", digAlgId);
				logger.info("parameters " + parameters );
				logger.info("sAttr " + sAttr );
				
				logger.info("--"+(AlgorithmIdentifier)parameters.get("digestAlgID"));
				logger.info("--" + (AlgorithmIdentifier)parameters.get("signatureAlgID"));
				signed = (sAttr != null) ? sAttr.getAttributes(Collections.unmodifiableMap(parameters)) : null;
				ASN1EncodableVector cmsAlgProt = signed.getAll(CMSAttributes.cmsAlgorithmProtect);
				logger.info("--" + cmsAlgProt);
			}
			else
			{
				signed = baseSignedTable;
			}

			ASN1Set signedAttr = null;
			byte[] tmp = null;
			if (signed != null)
			{
				if (isCounterSignature)
				{
					Hashtable tmpSigned = signed.toHashtable();
					tmpSigned.remove(CMSAttributes.contentType);
					signed = new AttributeTable(tmpSigned);
				}
				
				Hashtable tmpSigned1 = signed.toHashtable();
				logger.info("tmpSigned1 " + tmpSigned1);

				// TODO Validate proposed signed attributes

				signedAttr = getAttributeSet(signed);
				Enumeration objs = signedAttr.getObjects();
				while( objs.hasMoreElements() ){
					org.bouncycastle.asn1.cms.Attribute obj = (org.bouncycastle.asn1.cms.Attribute)objs.nextElement();
					logger.info("obj.getAttrType() " + obj.getAttrType() );
					ASN1Encodable[] values = (obj.getAttributeValues());
					int i = 0;
					for(ASN1Encodable value : values ){
						logger.info("i " + i);
						logger.info(Base64.encodeBase64String(value.toASN1Primitive().getEncoded(ASN1Encoding.DER)) + "_");
						i++;
					}
				}

				// sig must be composed from the DER encoding.
				tmp = signedAttr.getEncoded(ASN1Encoding.DER);
				logger.info("\n\ntmp\n"+Base64.encodeBase64String(tmp));
			}
			//            else
			//            {
			//                // TODO Use raw signature of the hash value instead
			//                ByteArrayOutputStream bOut = new ByteArrayOutputStream();
			//                if (content != null)
			//                {
			//                    content.write(bOut);
			//                }
			//                tmp = bOut.toByteArray();
			//            }
			
			sig.initSign(key);//, random);
			sig.update(tmp);
			byte[] sigBytes = sig.sign();
			boolean ver = true;
		
			Provider bcprovider = Security.getProvider("BC");
			Signature  sig1 = CMSSignedHelper.INSTANCE.getSignatureInstance(signatureName, bcprovider);//Signature.getInstance(cert.getSigAlgName(), "BC");
			try {
								
				sig1.initVerify(cert.getPublicKey());
				sig1.update( tmp );
				ver = sig1.verify(sigBytes);
				logger.info("risultato verifica " + ver);
			} catch (Throwable e){
				logger.error(e);
				if (e instanceof ProviderException){
					ProviderException lProviderException = (ProviderException) e;
					logger.error(lProviderException);
				}
			}
			
			if (ver == false) throw new SignatureException("Verifica fallita");

				
			
			

			ASN1Set unsignedAttr = null;
			if (unsAttr != null)
			{
				Map parameters = getBaseParameters(contentType, digAlgId, hash);
				parameters.put(CMSAttributeTableGenerator.SIGNATURE, sigBytes.clone());

				AttributeTable unsigned = unsAttr.getAttributes(Collections.unmodifiableMap(parameters));

				// TODO Validate proposed unsigned attributes

				unsignedAttr = getAttributeSet(unsigned);
			}
			
			return new SignerInfo(signerIdentifier, digAlgId,
					signedAttr, encAlgId, new DEROctetString(sigBytes), unsignedAttr);
		}
	}




	/**
	 * Similar method to the other generate methods. The additional argument
	 * addDefaultAttributes indicates whether or not a default set of signed attributes
	 * need to be added automatically. If the argument is set to false, no
	 * attributes will get added at all. 
	 * @throws IOException 
	 */
	public SignerObjectBean generate(byte[] hash,Provider sigProvider, X509Certificate certificate)throws NoSuchAlgorithmException, CMSException, IOException
	{
		ASN1EncodableVector  digestAlgs = new ASN1EncodableVector();
		ASN1EncodableVector  signerInfos = new ASN1EncodableVector();

		digests.clear();  // clear the current preserved digest state

		//
		// add the precalculated SignerInfo objects.
		//
		Iterator            it = _signers.iterator();

		while (it.hasNext())
		{
			SignerInformation signer = (SignerInformation)it.next();
			digestAlgs.add(CMSSignedHelper.INSTANCE.fixAlgID(signer.getDigestAlgorithmID()));
			//signerInfos.add(signer.toSignerInfo());
			signerInfos.add(signer.toASN1Structure());
			
		}

		it = signerInfs.iterator();

		while (it.hasNext())
		{
			SignerInf signer = (SignerInf)it.next();

			try
			{
				digestAlgs.add(signer.getDigestAlgorithmID());
				SignerInfo sigInfo = signer.toSignerInfo(new ASN1ObjectIdentifier(DATA), hash, /*rand,*/ sigProvider, true, false);
				System.out.println("sigInfo:: " + sigInfo.getDigestEncryptionAlgorithm());
				signerInfos.add(sigInfo);
			}
			catch (IOException e)
			{
				throw new CMSException("encoding error.", e);
			}
			catch (InvalidKeyException e)
			{
				throw new CMSException("key inappropriate for signature.", e);
			}
			catch (SignatureException e)
			{
				throw new CMSException("error creating signature.", e);
			}
			catch (CertificateEncodingException e)
			{
				throw new CMSException("error creating sid.", e);
			}
		}

		ASN1Set certificates = null;
		if (certs.size() != 0)	{
			certificates = CMSUtils.createBerSetFromList(certs);
		}

		ASN1Set certrevlist = null;
		if (crls.size() != 0)	{
			certrevlist = CMSUtils.createBerSetFromList(crls);
		}

		//Creo l'oggetto di ritorno
		SignerObjectBean bean = new SignerObjectBean();

		if( certificates!=null ){
			String encodedCert = Base64.encodeBase64String( certificates.getEncoded() );
			bean.setCert( encodedCert.getBytes());
		}
		if( certrevlist!=null ){
			String encodedCrl = Base64.encodeBase64String( certrevlist.getEncoded() );
			bean.setCrl( encodedCrl.getBytes());
		}
		
		String encodedDigest = Base64.encodeBase64String( new DERSet(digestAlgs).getEncoded() );
		bean.setDigestAlgs(encodedDigest.getBytes());
		String encodedSignerInfo = Base64.encodeBase64String( new DERSet(signerInfos).getEncoded() );
		bean.setSignerInfo(encodedSignerInfo.getBytes());

		return bean;
	}

	/**
	 * costruisce una busta detached a partire dall'hash fornito (quindi non calcolato) il provider
	 * e i dati precalcolati caricati con addSigner e addCertificatesAndCRLs
	 * @param hash
	 * @param sigProvider
	 * @return
	 * @throws NoSuchAlgorithmException
	 * @throws CMSException
	 * @throws IOException
	 */
	public CMSSignedData generateDetached(byte[] hash,Provider sigProvider)throws NoSuchAlgorithmException, CMSException, IOException
	{


		ASN1EncodableVector  digestAlgs = new ASN1EncodableVector();
		ASN1EncodableVector  signerInfos = new ASN1EncodableVector();

		digests.clear();  // clear the current preserved digest state

		//
		// add the precalculated SignerInfo objects.
		//
		Iterator            it = _signers.iterator();

		while (it.hasNext())
		{
			SignerInformation signer = (SignerInformation)it.next();
			digestAlgs.add(CMSSignedHelper.INSTANCE.fixAlgID(signer.getDigestAlgorithmID()));
			//signerInfos.add(signer.toSignerInfo());
			signerInfos.add(signer.toASN1Structure());
		}

		it = signerInfs.iterator();
		logger.info("Ciclo sui signerInfo");
		while (it.hasNext())
		{
			SignerInf signer = (SignerInf)it.next();
			logger.info("signer " + signer + " signer.getDigestAlgorithmID() " + signer.getDigestAlgorithmID());
			
			try
			{
				digestAlgs.add(signer.getDigestAlgorithmID());
				signerInfos.add(signer.toSignerInfo(new ASN1ObjectIdentifier(DATA), hash, /*rand,*/ sigProvider, true, false));
			}
			catch (IOException e)
			{
				throw new CMSException("encoding error.", e);
			}
			catch (InvalidKeyException e)
			{
				throw new CMSException("key inappropriate for signature.", e);
			}
			catch (SignatureException e)
			{
				throw new CMSException("error creating signature.", e);
			}
			catch (CertificateEncodingException e)
			{
				throw new CMSException("error creating sid.", e);
			}
		}

		ASN1Set certificates = null;

		if (certs.size() != 0)
		{
			logger.info("certs " + certs );
			certificates = CMSUtils.createBerSetFromList(certs);
		}

		ASN1Set certrevlist = null;

		if (crls.size() != 0)
		{
			certrevlist = CMSUtils.createBerSetFromList(crls);
		}

		//Creo l'oggetto di ritorno

		ContentInfo encInfo = new ContentInfo(new ASN1ObjectIdentifier(CMSSignedGenerator.DATA), null);

		SignedData  sd = new SignedData(
				new DERSet(digestAlgs),
				encInfo, 
				certificates, 
				certrevlist, 
				new DERSet(signerInfos));

		ContentInfo contentInfo = new ContentInfo(CMSObjectIdentifiers.signedData, sd);
		return new CMSSignedData(contentInfo);
	}

	static SignerIdentifier getSignerIdentifier(X509Certificate cert)
	{
		TBSCertificateStructure tbs;        
		try
		{
			tbs = CMSUtils.getTBSCertificateStructure(cert);
		}
		catch (CertificateEncodingException e)
		{
			throw new IllegalArgumentException(
					"can't extract TBS structure from this cert");
		}

		org.bouncycastle.asn1.cms.IssuerAndSerialNumber encSid = new IssuerAndSerialNumber(tbs
				.getIssuer(), tbs.getSerialNumber().getValue());
		return new SignerIdentifier(encSid);
	}

	static SignerIdentifier getSignerIdentifier(byte[] subjectKeyIdentifier)
	{
		return new SignerIdentifier(new DEROctetString(subjectKeyIdentifier));    
	}

	static class DigOutputStream extends OutputStream
	{
		MessageDigest dig;

		public DigOutputStream(MessageDigest dig)
		{
			this.dig = dig;
		}

		public void write(byte[] b, int off, int len) throws IOException
		{
			dig.update(b, off, len);
		}

		public void write(int b) throws IOException
		{
			dig.update((byte) b);
		}
	}

	//il metodo getEncAlgorithmIdentifier è stato rimosso nella 1.48
	//dalla classe CMSSignedHashGenerator
	//per cui aggiungo la mappa e i metodo quì

	protected AlgorithmIdentifier getEncAlgorithmIdentifier(String encOid, Signature sig)
	throws IOException
	{
		if (NO_PARAMS.contains(encOid))
		{
			return new AlgorithmIdentifier(
					new DERObjectIdentifier(encOid));
		}
		else
		{
			if (encOid.equals(CMSSignedGenerator.ENCRYPTION_RSA_PSS))
			{
				AlgorithmParameters sigParams = sig.getParameters();

				return new AlgorithmIdentifier(
						new DERObjectIdentifier(encOid), ASN1Primitive.fromByteArray(sigParams.getEncoded()));
			}
			else
			{
				return new AlgorithmIdentifier(
						new DERObjectIdentifier(encOid), new DERNull());
			}
		}
	}
	
	protected ASN1Set getAttributeSet(AttributeTable paramAttributeTable)
	  {
	    if (paramAttributeTable != null)
	      return new DERSet(paramAttributeTable.toASN1EncodableVector());
	    return null;
	  }
	
	protected String getEncOID(PrivateKey paramPrivateKey, String paramString)
	  {
	    String str = null;
	    if (((paramPrivateKey instanceof RSAPrivateKey)) || ("RSA".equalsIgnoreCase(paramPrivateKey.getAlgorithm())))
	    {
	      str = ENCRYPTION_RSA;
	    }
	    else if (((paramPrivateKey instanceof DSAPrivateKey)) || ("DSA".equalsIgnoreCase(paramPrivateKey.getAlgorithm())))
	    {
	      str = ENCRYPTION_DSA;
	      if (!paramString.equals(DIGEST_SHA1))
	        throw new IllegalArgumentException("can't mix DSA with anything but SHA1");
	    }
	    else if (("ECDSA".equalsIgnoreCase(paramPrivateKey.getAlgorithm())) || ("EC".equalsIgnoreCase(paramPrivateKey.getAlgorithm())))
	    {
	      str = (String)EC_ALGORITHMS.get(paramString);
	      if (str == null)
	        throw new IllegalArgumentException("can't mix ECDSA with anything but SHA family digests");
	    }
	    else if (((paramPrivateKey instanceof GOST3410PrivateKey)) || ("GOST3410".equalsIgnoreCase(paramPrivateKey.getAlgorithm())))
	    {
	      str = ENCRYPTION_GOST3410;
	    }
	    else if ("ECGOST3410".equalsIgnoreCase(paramPrivateKey.getAlgorithm()))
	    {
	      str = ENCRYPTION_ECGOST3410;
	    }
	    return str;
	  }
}
