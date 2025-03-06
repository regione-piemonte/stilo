package org.bouncycastle.cms;

import it.eng.common.CMSUtils;
import it.eng.common.bean.SignerObjectBean;

import java.io.ByteArrayOutputStream;
import java.security.cert.CertStore;
import java.security.cert.CollectionCertStoreParameters;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.bouncycastle.asn1.ASN1Encoding;
import org.bouncycastle.asn1.ASN1InputStream;
import org.bouncycastle.asn1.ASN1ObjectIdentifier;
import org.bouncycastle.asn1.ASN1Set;
import org.bouncycastle.asn1.DERSet;
import org.bouncycastle.asn1.cms.CMSObjectIdentifiers;
import org.bouncycastle.asn1.cms.ContentInfo;
import org.bouncycastle.asn1.cms.SignedData;
import org.bouncycastle.asn1.cms.SignerInfo;
 
public class DummySignerInformationUtil  {

	 

	public static SignerInformation buildSignerInformation(SignerInfo info) {
		return new SignerInformation(info, null, null, null);
	}
	
	 
	public static List<SignerInformation> buildSignerInformationFromSignerObjectBean(SignerObjectBean bean)throws Exception{
		List<SignerInformation> ret= new ArrayList<SignerInformation>();
		
		//		DERSet digestAlg= new DERSet(info.getDigestAlgorithm());
//		DERSet sinfos= new DERSet(info );

//		DERSet digest = (DERSet)new ASN1InputStream(digestAlg.getEncoded()).readObject();
//		DERSet signerInfos = (DERSet)new ASN1InputStream(sinfos.getEncoded()).readObject();
		DERSet digest = (DERSet)new ASN1InputStream(bean.getDigestAlgs()).readObject();
	    DERSet signerInfos = (DERSet)new ASN1InputStream(bean.getSignerInfo()).readObject();
	    //come se fosse una firma detached in realtà mi serve solo epr costruire gli oggeti signerInformation
		ContentInfo encInfo = new ContentInfo(new ASN1ObjectIdentifier(CMSSignedGenerator.DATA), null); 

		SignedData  sd = new SignedData(
				digest,
				encInfo, 
				null, 
				null, 
				signerInfos);


		System.out.println(sd.getEncoded(ASN1Encoding.DER).length);

		ContentInfo contentInfo = new ContentInfo(CMSObjectIdentifiers.signedData, sd);
		CMSSignedData data = new CMSSignedData( contentInfo);
		Collection  signers = (Collection )data.getSignerInfos().getSigners();
		for (Object signer : signers) {
			if(signer instanceof SignerInformation){
				ret.add((SignerInformation)signer);
			}
		}
		 
		return ret;
	}
	
	/**
	 * costruisce una busta detached a partire da un signerObjectBean che contiene le seguenti info:
	 * certificati, algoritmo di digest e signerinfo.
	 * Il contenuto non viene incluso, in tal modo il metodo può essere utile anche in molti casi dove occorre
	 * calcolare delle buste fittizie. 
	 *	FIXME mancano le CRL
	 * @param bean
	 * @return
	 * @throws Exception
	 */
	public static  CMSSignedData buildCMSSigneDataFromSignerObjectBean(SignerObjectBean bean )throws Exception{
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
 
		ContentInfo encInfo = new ContentInfo(new ASN1ObjectIdentifier(CMSSignedGenerator.DATA), null);

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
		CMSSignedData data = new CMSSignedData(contentInfo);
		return data;
		
	}
 	
}
