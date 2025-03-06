package it.eng.client.applet.operation;


import it.eng.common.CMSUtils;
import it.eng.common.LogWriter;
import it.eng.common.bean.SignerObjectBean;

import java.io.IOException;
import java.io.OutputStream;
import java.security.AlgorithmParameters;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.Provider;
import java.security.ProviderException;
import java.security.SecureRandom;
import java.security.Security;
import java.security.Signature;
import java.security.SignatureException;
import java.security.cert.CertificateEncodingException;
import java.security.cert.X509Certificate;
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
import org.bouncycastle.cms.DefaultSignedAttributeTableGenerator;
import org.bouncycastle.cms.SignerInformation;
import org.bouncycastle.cms.SimpleAttributeTableGenerator;



public class CMSSignedHashGenerator extends CMSSignedGenerator {
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
		signerInfs.add(new SignerInf(cert, key, getSignerIdentifier(cert), digestOID, getEncOID(key, digestOID), new DefaultSignedAttributeTableGenerator(), null, null));
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
		signerInfs.add(new SignerInf(cert, key, getSignerIdentifier(cert), digestOID, getEncOID(key, digestOID), new DefaultSignedAttributeTableGenerator(signedAttr), new SimpleAttributeTableGenerator(unsignedAttr), signedAttr));
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
				SecureRandom        random,
				Provider            sigProvider,
				boolean             addDefaultAttributes,
				boolean             isCounterSignature)
		throws IOException, SignatureException, InvalidKeyException, NoSuchAlgorithmException, CertificateEncodingException, CMSException
		{
			AlgorithmIdentifier digAlgId = getDigestAlgorithmID();
			String              digestName = CMSSignedHelper.INSTANCE.getDigestAlgName(digestOID);
			String              signatureName = digestName + "with" + CMSSignedHelper.INSTANCE.getEncryptionAlgName(encOID);
			LogWriter.writeLog("signatureName" + signatureName + " Provider " + sigProvider);
			Signature           sig = CMSSignedHelper.INSTANCE.getSignatureInstance(signatureName, sigProvider);
			MessageDigest       dig = CMSSignedHelper.INSTANCE.getDigestInstance(digestName, sigProvider);               
			AlgorithmIdentifier encAlgId = getEncAlgorithmIdentifier(encOID, sig);

			//            if (content != null)
				//            {
				//                content.write(new DigOutputStream(dig));
				//            }

			//byte[] hash = dig.digest();
			digests.put(digestOID, hash.clone());

			LogWriter.writeLog("addDefaultAttributes " + addDefaultAttributes);
			LogWriter.writeLog("isCounterSignature " + isCounterSignature);
			AttributeTable signed;
			if (addDefaultAttributes)
			{
				Map parameters = getBaseParameters(contentType, digAlgId, hash);
				signed = (sAttr != null) ? sAttr.getAttributes(Collections.unmodifiableMap(parameters)) : null;
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
				LogWriter.writeLog("tmpSigned1 " + tmpSigned1);
				
				// TODO Validate proposed signed attributes
				signedAttr = getAttributeSet(signed);
				Enumeration objs = signedAttr.getObjects();
				while( objs.hasMoreElements() ){
					org.bouncycastle.asn1.cms.Attribute obj = (org.bouncycastle.asn1.cms.Attribute)objs.nextElement();
					LogWriter.writeLog("obj.getAttrType() " + obj.getAttrType() );
					ASN1Encodable[] values = (obj.getAttributeValues());
					int i = 0;
					for(ASN1Encodable value : values ){
						LogWriter.writeLog("i " + i);
						LogWriter.writeLog(Base64.encodeBase64String(value.toASN1Primitive().getEncoded(ASN1Encoding.DER)) + "_");
						i++;
					}
				}
					
				
				// sig must be composed from the DER encoding.
				tmp = signedAttr.getEncoded(ASN1Encoding.DER);
				LogWriter.writeLog("\n\ntmp\n"+Base64.encodeBase64String(tmp));
				
				
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

			sig.initSign(key, random);
			sig.update(tmp);
			byte[] sigBytes = sig.sign();
			boolean ver = true;
			
			Provider bcprovider = Security.getProvider("BC");
			Signature  sig1 = CMSSignedHelper.INSTANCE.getSignatureInstance(signatureName, bcprovider);//Signature.getInstance(cert.getSigAlgName(), "BC");
			LogWriter.writeLog("--- " + sig1);
			try {
				LogWriter.writeLog("File Provider:"+sigProvider.getInfo().toLowerCase().trim());
				LogWriter.writeLog("BC File Provider:"+bcprovider.getInfo().toLowerCase().trim());
				
				Provider[] provs = Security.getProviders();
				for(Provider prov : provs ){
					LogWriter.writeLog(prov.getInfo());
				}
				
//				AuthProvider lAuthProvider = (AuthProvider)sigProvider;
//				lAuthProvider.logout();
//				lAuthProvider.login(new Subject(), new PasswordHandler( "12345678".toCharArray() ) );
								
				sig1.initVerify(cert.getPublicKey());
				sig1.update( tmp );
				ver = sig1.verify(sigBytes);
				LogWriter.writeLog("risultato verifica " + ver);
			} catch (Throwable e){
				e.printStackTrace();
				if (e instanceof ProviderException){
					ProviderException lProviderException = (ProviderException)e;
					lProviderException.getCause().printStackTrace();
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
	public SignerObjectBean generate(byte[] hash,Provider sigProvider)throws NoSuchAlgorithmException, CMSException, IOException
	{
		// TODO
		//        if (signerInfs.isEmpty())
		//        {
		//            /* RFC 3852 5.2
		//             * "In the degenerate case where there are no signers, the
		//             * EncapsulatedContentInfo value being "signed" is irrelevant.  In this
		//             * case, the content type within the EncapsulatedContentInfo value being
		//             * "signed" MUST be id-data (as defined in section 4), and the content
		//             * field of the EncapsulatedContentInfo value MUST be omitted."
		//             */
		//            if (encapsulate)
		//            {
		//                throw new IllegalArgumentException("no signers, encapsulate must be false");
		//            }
		//            if (!DATA.equals(eContentType))
		//            {
		//                throw new IllegalArgumentException("no signers, eContentType must be id-data");
		//            }
		//        }
		//
		//        if (!DATA.equals(eContentType))
		//        {
		//            /* RFC 3852 5.3
		//             * [The 'signedAttrs']...
		//             * field is optional, but it MUST be present if the content type of
		//             * the EncapsulatedContentInfo value being signed is not id-data.
		//             */
		//            // TODO signedAttrs must be present for all signers
		//        }

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
			signerInfos.add(signer.toSignerInfo());
		}

		it = signerInfs.iterator();

		while (it.hasNext())
		{
			SignerInf signer = (SignerInf)it.next();

			try
			{
				digestAlgs.add(signer.getDigestAlgorithmID());
				System.out.println("qui");
				signerInfos.add(signer.toSignerInfo(new ASN1ObjectIdentifier(DATA), hash, rand, sigProvider, true, false));
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
			certificates = CMSUtils.createBerSetFromList(certs);
		}

		ASN1Set certrevlist = null;

		if (crls.size() != 0)
		{
			certrevlist = CMSUtils.createBerSetFromList(crls);
		}

		//Creo l'oggetto di ritorno
		SignerObjectBean bean = new SignerObjectBean();

		bean.setCertificates(this.certificates);
		bean.setDigestAlgs(new DERSet(digestAlgs).getEncoded());
		bean.setSignerInfo(new DERSet(signerInfos).getEncoded());



		//        ASN1OctetString octs = new BERConstructedOctetString(FileUtils.readFileToByteArray(new File("C:\\testoodt.odt")));
		//        
		//        ContentInfo encInfo = new ContentInfo(contentTypeOID, octs);
		//
		//        SignedData  sd = new SignedData(
		//                                 new DERSet(digestAlgs),
		//                                 encInfo, 
		//                                 certificates, 
		//                                 certrevlist, 
		//                                 new DERSet(signerInfos));
		//
		//        
		//        
		//        ContentInfo contentInfo = new ContentInfo(CMSObjectIdentifiers.signedData, sd);
		//
		//        
		//        
		//        return new CMSSignedData(content, contentInfo);




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
			signerInfos.add(signer.toSignerInfo());
		}

		it = signerInfs.iterator();

		while (it.hasNext())
		{
			SignerInf signer = (SignerInf)it.next();

			try
			{
				digestAlgs.add(signer.getDigestAlgorithmID());
				signerInfos.add(signer.toSignerInfo(new ASN1ObjectIdentifier(DATA), hash, rand, sigProvider, true, false));
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
}
