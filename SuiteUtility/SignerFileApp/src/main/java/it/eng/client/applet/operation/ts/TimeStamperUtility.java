package it.eng.client.applet.operation.ts;

import it.eng.client.applet.operation.TsrGenerator;
import it.eng.client.applet.util.PreferenceKeys;
import it.eng.client.applet.util.PreferenceManager;
import it.eng.common.LogWriter;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.bouncycastle.asn1.ASN1EncodableVector;
import org.bouncycastle.asn1.ASN1InputStream;
import org.bouncycastle.asn1.ASN1ObjectIdentifier;
import org.bouncycastle.asn1.ASN1Set;
import org.bouncycastle.asn1.DERObjectIdentifier;
import org.bouncycastle.asn1.cms.Attribute;
import org.bouncycastle.asn1.cms.AttributeTable;
import org.bouncycastle.asn1.cms.ContentInfo;
import org.bouncycastle.asn1.pkcs.PKCSObjectIdentifiers;
import org.bouncycastle.asn1.x509.AlgorithmIdentifier;
import org.bouncycastle.cms.CMSSignedDataParser;
import org.bouncycastle.cms.SignerInformation;
import org.bouncycastle.cms.SignerInformationStore;
import org.bouncycastle.operator.DigestCalculator;
import org.bouncycastle.operator.DigestCalculatorProvider;
import org.bouncycastle.operator.OperatorCreationException;
import org.bouncycastle.operator.bc.BcDigestCalculatorProvider;
import org.bouncycastle.tsp.TSPValidationException;
import org.bouncycastle.tsp.TimeStampToken;
import org.bouncycastle.tsp.TimeStampTokenInfo;
import org.bouncycastle.util.Arrays;
/**
 * utility per marcare file firmati
 * @author Russo
 *
 */
public class TimeStamperUtility {
 

	/**
	 * estrae i timestamp da un signerInfo
	 */
	public static List<TimeStampToken> extractTimeStamps(SignerInformation signerInfo){
		List<TimeStampToken> ret = new ArrayList<TimeStampToken>();
		final BcDigestCalculatorProvider digestBuilder= new BcDigestCalculatorProvider();

		DigestCalculatorProvider dcp= new DigestCalculatorProvider() {

			@Override
			public DigestCalculator get(AlgorithmIdentifier digestAlgorithmIdentifier)
			throws OperatorCreationException {
				// 
				return digestBuilder.get(digestAlgorithmIdentifier);
			}
		};
		try {
			ret=getSignatureTimestamps(signerInfo, dcp,true);
		} catch (TSPValidationException e) {
			// se trova una marca non valida fallisce il tutto
			e.printStackTrace();
		}
		return ret;
	}
	
	private static List<TimeStampToken> getSignatureTimestamps(SignerInformation signerInfo,
			DigestCalculatorProvider digCalcProvider,boolean validate)
	throws TSPValidationException
	{
		List<TimeStampToken> timestamps = new ArrayList<TimeStampToken>();

		AttributeTable unsignedAttrs = signerInfo.getUnsignedAttributes();
		if (unsignedAttrs != null)
		{
			ASN1EncodableVector allTSAttrs = unsignedAttrs.getAll(
					PKCSObjectIdentifiers.id_aa_signatureTimeStampToken);
			for (int i = 0; i < allTSAttrs.size(); ++i)
			{
				Attribute tsAttr = (Attribute)allTSAttrs.get(i);
				ASN1Set tsAttrValues = tsAttr.getAttrValues();
				for (int j = 0; j < tsAttrValues.size(); ++j)
				{
					try
					{
						ContentInfo contentInfo = ContentInfo.getInstance(tsAttrValues.getObjectAt(j).toASN1Primitive());
						TimeStampToken timeStampToken = new TimeStampToken(contentInfo);
						TimeStampTokenInfo tstInfo = timeStampToken.getTimeStampInfo();
						if(validate){
							DigestCalculator digCalc = digCalcProvider.get(tstInfo.getHashAlgorithm());

							OutputStream dOut = digCalc.getOutputStream();

							dOut.write(signerInfo.getSignature());
							dOut.close();

							byte[] expectedDigest = digCalc.getDigest();

							if (!Arrays.constantTimeAreEqual(expectedDigest, tstInfo.getMessageImprintDigest()))
							{
								throw new TSPValidationException("Incorrect digest in message imprint");
							}
						}
						timestamps.add(timeStampToken);
					}
					catch (OperatorCreationException e)
					{
						throw new TSPValidationException("Unknown hash algorithm specified in timestamp");
					}
					catch (Exception e)
					{
						throw new TSPValidationException("Timestamp could not be parsed");
					}
				}
			}
		}

		return timestamps;
	}

	/* 
	public static List<TimeStampToken> extractTimeStamps(SignerInformation sinfo  ){
		//HashMap<byte[], TimeStampToken> timestamptokensBySignature = new HashMap<byte[], TimeStampToken>();
		List<TimeStampToken> ret=new ArrayList<TimeStampToken>();
		AttributeTable table  = sinfo.getUnsignedAttributes();
		if (table==null)
			return ret;
		ASN1EncodableVector marche=table.getAll(new DERObjectIdentifier("1.2.840.113549.1.9.16.2.14"));
		for (int i = 0; i < marche.size(); i++) {
			DEREncodable attribute=marche.get(i);
			if (attribute!=null && attribute instanceof Attribute ) {
				ASN1Set set= ((Attribute)attribute).getAttrValues();
				TimeStampToken timestamptoken = null;
				try {
					System.out.println("PKCSObjectIdentifiers.id_ct_TSTInfo.getId():"+PKCSObjectIdentifiers.id_ct_TSTInfo.getId());
					System.out.println("dunp"+ASN1Dump.dumpAsString(attribute.getDERObject()));
					System.out.println("dunp"+ASN1Dump.dumpAsString(set));
					 
					Enumeration en=set.getObjects();
					if(en.hasMoreElements()){
						timestamptoken = new TimeStampToken( ContentInfo.getInstance(en.nextElement()));
					}
					System.out.println("dunp"+ASN1Dump.dumpAsString(timestamptoken));
				} catch (Exception e) {
					LogWriter.writeLog("fatal reading TimeStampToken "+e.getMessage());
					e.printStackTrace();
				}
				if (timestamptoken!=null) {
					ret.add(timestamptoken);
				}
			}
		}
		return ret;
	}*/
	/**
	 * ritorna true se il sinfo contiene marche come attributi non firmati
	 * @param sinfo
	 * @return
	 */
	public static boolean haveTimeStamp(SignerInformation sinfo){
		AttributeTable table  = sinfo.getUnsignedAttributes();
		ASN1EncodableVector marche=table.getAll(new DERObjectIdentifier("1.2.840.113549.1.9.16.2.14"));
		return marche.size()>0;
	}
	
	//aggiunge una marca a tutti i signerInfo presenti nel file CAdes passato
	public static void addMarca(File cmsFile,boolean soloNonMarcate) throws Exception{
		//TODO non generare una marca per ogni signerinfo
		LogWriter.writeLog("addMarca START file:"+cmsFile);
		CMSSignedDataParser cmsSignedData= new CMSSignedDataParser(FileUtils.openInputStream(cmsFile));
    	cmsSignedData.getSignedContent().drain();
    	Collection  signers = (Collection )cmsSignedData.getSignerInfos().getSigners();
    	List<SignerInformation> newSigners=new ArrayList<SignerInformation>();
    	for (Object signer: signers) {
			if(signer instanceof SignerInformation){
				boolean tutte=!soloNonMarcate;
				if(tutte || (soloNonMarcate && TimeStamperUtility.haveTimeStamp((SignerInformation)signer) )){
					newSigners.add(TimeStamperUtility.addMarca((SignerInformation)signer));
				}
			}
    	}
    	OutputStream stream=CMSSignedDataParser.replaceSigners(FileUtils.openInputStream(cmsFile), new SignerInformationStore(newSigners), new FileOutputStream(new File(cmsFile.getAbsolutePath()+".p7m")));
    	//TODO CMSSignedDataParser.replaceCertificatesAndCRLs(original, certs, crls, attrCerts, out)
    	IOUtils.closeQuietly(stream);
    	LogWriter.writeLog("addMarca END file:"+cmsFile);
	}
	
	/**
	 *   
	 * aggiunge una marca al signerInfo passato in input il signerInfo modificato è ritornato dal metodo.
	 * @param input
	 * @return SignerInfo 
	 * @throws Exception
	 */
	public static SignerInformation addMarca(SignerInformation input )throws Exception{
		AttributeTable table  = input.getUnsignedAttributes();
		if (table==null)
			table= new AttributeTable(new ASN1EncodableVector());
		//lista degli attuali attributi non firmati
		//ASN1EncodableVector vect=table.toASN1EncodableVector();
		//lista delle attuali marche
		//ASN1EncodableVector marche=table.getAll(new DERObjectIdentifier("1.2.840.113549.1.9.16.2.14"));
		//crea una nuova marca per la firma specificata	 
		TimeStampToken tst=getMarca(DigestUtils.sha256(input.getSignature()));
		ByteArrayInputStream bytearrayinputstream = new ByteArrayInputStream(tst.getEncoded());
		ASN1InputStream asn1inputstream = new ASN1InputStream(bytearrayinputstream);
		//Attribute marca= new Attribute(new DERObjectIdentifier("1.2.840.113549.1.9.16.2.14"),new DERSet( asn1inputstream.readObject()    ));
		AttributeTable replaced=table.add( new ASN1ObjectIdentifier("1.2.840.113549.1.9.16.2.14"),  asn1inputstream.readObject()     );

		//  	        marche.add(marca);
		//  	        //aggiungo le marche agli attributi non firmati
		//  	       for (int i = 0; i < marche.size(); i++) {
		//  	    	 vect.add(marche.get(i));
		//  	       }
		return SignerInformation.replaceUnsignedAttributes(input, replaced);
	}
	
	/**
	 * chiede una marca all'indirizzo impostato nelle preference
	 * @param digest
	 * @return
	 * @throws Exception
	 */
	private static TimeStampToken getMarca(byte[] digest)throws Exception{
		//test
		String url=PreferenceManager.getString( PreferenceKeys.PROPERTY_SIGN_TSASERVER );
		LogWriter.writeLog("ask mark to:"+url);
		
		TsrGenerator tsrgen = new TsrGenerator(url);
		TimeStampToken tst;
		try {
			tst = tsrgen.generateTsrData(digest );
		} catch (Exception e) {
			//
			throw new Exception("generazione marca fallita",e);
		}
		return tst;
	}


	 
}
