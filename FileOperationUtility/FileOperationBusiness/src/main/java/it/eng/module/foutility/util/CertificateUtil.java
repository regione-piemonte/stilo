/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.security.cert.CertificateEncodingException;
import java.security.cert.X509Certificate;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Enumeration;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.IllegalFormatException;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;
import java.util.Vector;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.bouncycastle.asn1.ASN1Encodable;
import org.bouncycastle.asn1.ASN1GeneralizedTime;
import org.bouncycastle.asn1.ASN1InputStream;
import org.bouncycastle.asn1.ASN1Integer;
import org.bouncycastle.asn1.ASN1ObjectIdentifier;
import org.bouncycastle.asn1.ASN1OctetString;
import org.bouncycastle.asn1.ASN1Primitive;
import org.bouncycastle.asn1.ASN1Sequence;
import org.bouncycastle.asn1.ASN1Set;
import org.bouncycastle.asn1.ASN1TaggedObject;
import org.bouncycastle.asn1.DERBitString;
import org.bouncycastle.asn1.DERGeneralizedTime;
import org.bouncycastle.asn1.DERIA5String;
import org.bouncycastle.asn1.DEROctetString;
import org.bouncycastle.asn1.DEROutputStream;
import org.bouncycastle.asn1.DERSequence;
import org.bouncycastle.asn1.pkcs.PKCSObjectIdentifiers;
import org.bouncycastle.asn1.x500.RDN;
import org.bouncycastle.asn1.x500.X500Name;
import org.bouncycastle.asn1.x500.style.BCStyle;
import org.bouncycastle.asn1.x509.AccessDescription;
import org.bouncycastle.asn1.x509.AlgorithmIdentifier;
import org.bouncycastle.asn1.x509.Attribute;
import org.bouncycastle.asn1.x509.AuthorityInformationAccess;
import org.bouncycastle.asn1.x509.AuthorityKeyIdentifier;
import org.bouncycastle.asn1.x509.BasicConstraints;
import org.bouncycastle.asn1.x509.CRLDistPoint;
import org.bouncycastle.asn1.x509.Certificate;
import org.bouncycastle.asn1.x509.DistributionPoint;
import org.bouncycastle.asn1.x509.DistributionPointName;
import org.bouncycastle.asn1.x509.Extension;
import org.bouncycastle.asn1.x509.Extensions;
import org.bouncycastle.asn1.x509.GeneralName;
import org.bouncycastle.asn1.x509.GeneralNames;
import org.bouncycastle.asn1.x509.KeyUsage;
import org.bouncycastle.asn1.x509.PolicyInformation;
import org.bouncycastle.asn1.x509.PolicyQualifierId;
import org.bouncycastle.asn1.x509.PolicyQualifierInfo;
import org.bouncycastle.asn1.x509.PrivateKeyUsagePeriod;
import org.bouncycastle.asn1.x509.ReasonFlags;
import org.bouncycastle.asn1.x509.SubjectDirectoryAttributes;
import org.bouncycastle.asn1.x509.SubjectKeyIdentifier;
import org.bouncycastle.asn1.x509.qualified.Iso4217CurrencyCode;
import org.bouncycastle.asn1.x509.qualified.MonetaryValue;
import org.bouncycastle.asn1.x509.qualified.QCStatement;
import org.bouncycastle.crypto.digests.MD5Digest;
import org.bouncycastle.crypto.digests.SHA1Digest;
import org.bouncycastle.i18n.filter.TrustedInput;

public class CertificateUtil {

	static final String term = System.getProperty("line.separator");

	public static final Logger log = LogManager.getLogger(CertificateUtil.class);

	private static Certificate getX509CertificateStructure(X509Certificate certificato) throws CertificateEncodingException, IOException {
		byte[] derdata = certificato.getEncoded();
		ByteArrayInputStream as = new ByteArrayInputStream(derdata);
		ASN1InputStream aderin = new ASN1InputStream(as);
		ASN1Primitive ado = aderin.readObject();
		Certificate xc509 = Certificate.getInstance(ado);
		return xc509;
	}

	private static Extensions getX509Extensions(X509Certificate certificato) {
		Certificate xc509;
		try {
			xc509 = getX509CertificateStructure(certificato);
			return xc509.getTBSCertificate().getExtensions();
		} catch (CertificateEncodingException e) {
			log.error("Eccezione getX509Extensions", e);
		} catch (IOException e) {
			log.error("Eccezione getX509Extensions", e);
		}
		return null;
	}

	public static String getCertificateSerialNumber(X509Certificate certificato) {
		Certificate xc509;
		try {
			xc509 = getX509CertificateStructure(certificato);
			return "" + xc509.getSerialNumber().getValue();
		} catch (CertificateEncodingException e) {
			log.error("Eccezione getCertificateSerialNumber", e);
		} catch (IOException e) {
			log.error("Eccezione getCertificateSerialNumber", e);
		}
		return "";
	}

	protected static ASN1Primitive getExtensionValue(X509Certificate cert, String oid) {
		if (cert == null) {
			return null;
		}
		return getDerObjectFromByteArray(cert.getExtensionValue(oid));
	}

	private static ASN1Primitive getDerObjectFromByteArray(byte[] arrayBytes) {
		if (arrayBytes == null) {
			return null;
		}
		try {
			return ASN1Primitive.fromByteArray(ASN1OctetString.getInstance(arrayBytes).getOctets());
		} catch (IOException e) {
			throw new RuntimeException("Caught an unexected IOException", e);
		}
	}

	public static List<String> getQCStatements(X509Certificate certificato) {

		List<String> qCStatements = new ArrayList<String>();

		final ASN1Primitive obj = getExtensionValue(certificato, Extension.qCStatements.getId());
		if (obj == null) {
			return qCStatements;
		}

		final ASN1Sequence seq = (ASN1Sequence) obj;
		for (int i = 0; i < seq.size(); i++) {

			final QCStatement qcs = QCStatement.getInstance(seq.getObjectAt(i));

			if (QCStatement.id_etsi_qcs_QcCompliance.equals(qcs.getStatementId())) {
				// process statement - just write a notification that the certificate contains this statement
				qCStatements.add("Certificato Qualificato conforme al Regolamento EU N. 910/2014 - eIDAS ("+qcs.getStatementId()+")");
			} else if (QCStatement.id_qcs_pkixQCSyntax_v1.equals(qcs.getStatementId())) {
				qCStatements.add("id_qcs_pkixQCSyntax_v1 (OID: " + qcs.getStatementId() + ")");
			} else if (QCStatement.id_qcs_pkixQCSyntax_v2.equals(qcs.getStatementId())) {
				qCStatements.add("id_qcs_pkixQCSyntax_v2 (OID: " + qcs.getStatementId() + ")");
			} else if (QCStatement.id_etsi_qcs_QcSSCD.equals(qcs.getStatementId())) {
				qCStatements.add("La chiave privata risiede in un dispositivo sicuro conforme al Regolamento EU N. 910/2014 ("+qcs.getStatementId()+")");
			} else if (QCStatement.id_etsi_qcs_RetentionPeriod.equals(qcs.getStatementId())) {
				qCStatements.add("Periodo di conservazione delle informazioni di certificazione: " + qcs.getStatementInfo()
						+ " anni ("+qcs.getStatementId()+")");
			} else if (QCStatement.id_etsi_qcs_LimiteValue.equals(qcs.getStatementId())) {
				// FIXME: needs to be tested !
				// process statement - write a notification containing the limit value
				MonetaryValue limit = MonetaryValue.getInstance(qcs.getStatementInfo());
				Iso4217CurrencyCode currency = limit.getCurrency();
				double value = limit.getAmount().doubleValue() * Math.pow(10, limit.getExponent().doubleValue());
				/*
				 * This statement is a statement by the issuer which impose a limitation on the value of transaction for which this certificate can be used to
				 * the specified amount (MonetaryValue), according to the Directive 1999/93/EC of the European Parliament and of the Council of 13 December 1999
				 * on a Community framework for electronic signatures, as implemented in the law of the country specified in the issuer field of this
				 * certificate.
				 */
				if (currency.isAlphabetic()) {
					qCStatements.add(("QcEuLimitValue (OID: " + qcs.getStatementId() + ")"
							+ new Object[] { currency.getAlphabetic(), new TrustedInput(new Double(value)), limit }));
				} else {
					qCStatements.add((" QcLimitValueNum" + new Object[] { new Integer(currency.getNumeric()), new TrustedInput(new Double(value)), limit }));
				}
			} else {
				if( "0.4.0.1862.1.5".equals(qcs.getStatementId().getId())){
					String info = "";
					final ASN1Encodable qcStatementInfo = qcs.getStatementInfo();
					if (qcStatementInfo!= null && qcStatementInfo instanceof ASN1Sequence) {
					    final ASN1Sequence qcTypeInfo = (ASN1Sequence) qcStatementInfo;
					    if( qcTypeInfo.size()>0){
					    	info = qcTypeInfo.getObjectAt(0).toString();
					    }
					}
					qCStatements.add(("PKI Disclosure Statements (PDS) " + info  + " (" + qcs.getStatementId()+")"));
					
				} else if( "0.4.0.1862.1.6".equals(qcs.getStatementId().getId())){
					//qCStatements.add(("Certificato di sigillo elettronico " + qcs.getStatementId()));
					
					final ASN1Encodable qcStatementInfo = qcs.getStatementInfo();
					if (qcStatementInfo!= null && qcStatementInfo instanceof ASN1Sequence) {
					    final ASN1Sequence qcTypeInfo = (ASN1Sequence) qcStatementInfo;
					    if( qcTypeInfo.size()>0){
					    	if( "0.4.0.1862.1.6.2".equals(qcTypeInfo.getObjectAt(0).toString()))
					    		qCStatements.add(("Certificato di sigillo elettronico (" + qcs.getStatementId() + ")"));
					    }
					}
				} else {
					qCStatements.add((" QcUnknownStatement: " + qcs.getStatementId()));
				}
			}
		}

		return qCStatements;
	}

	public static List<String> getKeyUsage(X509Certificate certificato) {

		List<String> keyUsages = new ArrayList<String>();

		Extensions xc509Ext = getX509Extensions(certificato);

		if (xc509Ext != null) {

			KeyUsage ku = KeyUsage.fromExtensions(xc509Ext);

			if (ku != null) {
				if ((((DERBitString) ku.toASN1Primitive()).intValue() & KeyUsage.digitalSignature) != 0)
					keyUsages.add("digitalSignature");
				if ((((DERBitString) ku.toASN1Primitive()).intValue() & KeyUsage.nonRepudiation) != 0)
					keyUsages.add("nonRepudiation");
				if ((((DERBitString) ku.toASN1Primitive()).intValue() & KeyUsage.keyEncipherment) != 0)
					keyUsages.add("keyEncipherment");
				if ((((DERBitString) ku.toASN1Primitive()).intValue() & KeyUsage.dataEncipherment) != 0)
					keyUsages.add("dataEncipherment");
				if ((((DERBitString) ku.toASN1Primitive()).intValue() & KeyUsage.keyAgreement) != 0)
					keyUsages.add("keyAgreement");
				if ((((DERBitString) ku.toASN1Primitive()).intValue() & KeyUsage.keyCertSign) != 0)
					keyUsages.add("keyCertSign");
				if ((((DERBitString) ku.toASN1Primitive()).intValue() & KeyUsage.cRLSign) != 0)
					keyUsages.add("cRLSign");
				if ((((DERBitString) ku.toASN1Primitive()).intValue() & KeyUsage.encipherOnly) != 0)
					keyUsages.add("encipherOnly");
				if ((((DERBitString) ku.toASN1Primitive()).intValue() & KeyUsage.decipherOnly) != 0)
					keyUsages.add("decipherOnly");
			}
		}
		return keyUsages;
	}

	public static Map<String, String> getX509Name(X509Certificate certificato, String tipo) {

		Map<String, String> X500Name = new HashMap<String, String>();
		Certificate xc509;
		try {
			xc509 = getX509CertificateStructure(certificato);
			X500Name _aName = null;
			if (tipo != null && tipo.equalsIgnoreCase("Subject"))
				_aName = xc509.getSubject();
			if (tipo != null && tipo.equalsIgnoreCase("Issuer"))
				_aName = xc509.getIssuer();

			if (_aName != null) {

				ASN1ObjectIdentifier[] oidv = _aName.getAttributeTypes();

				RDN[] values = _aName.getRDNs();

				String sAname = "";
				for (int i = 0; i < oidv.length; i++) {

					if (i < values.length)
						sAname = values[i].toString();

					if (BCStyle.INSTANCE.oidToAttrNames(oidv[i]) != null)
						X500Name.put(BCStyle.INSTANCE.oidToAttrNames(oidv[i]).toString(), sAname);
				}
			}

		} catch (CertificateEncodingException e) {
			log.error("Eccezione getX509Name", e);
		} catch (IOException e) {
			log.error("Eccezione getX509Name", e);
		}

		return X500Name;
	}

//	private static String getGeneralNameString(int tag, DEREncodable value) throws IOException {
//		String ret = null;
//		switch (tag) {
//		// case 0:
//		// ASN1Sequence seq = getAltnameSequence(value.toASN1Object().getEncoded());
//		// String upn = getUPNStringFromSequence(seq);
//		// // OtherName can be something else besides UPN
//		// if (upn != null) {
//		// ret = CertTools.UPN + "=" + upn;
//		// } else {
//		// String permanentIdentifier = getPermanentIdentifierStringFromSequence(seq);
//		// if (permanentIdentifier != null) {
//		// ret = CertTools.PERMANENTIDENTIFIER + "=" + permanentIdentifier;
//		// } else {
//		// String krb5Principal = getKrb5PrincipalNameFromSequence(seq);
//		// if (krb5Principal != null) {
//		// ret = CertTools.KRB5PRINCIPAL + "=" + krb5Principal;
//		// }
//		// }
//		// }
//		// break;
//		case 1:
//			ret = "rfc822name" + "=" + DERIA5String.getInstance(value).getString();
//			break;
//		case 2:
//			ret = "dNSName" + "=" + DERIA5String.getInstance(value).getString();
//			break;
//		case 3: // SubjectAltName of type x400Address not supported
//			break;
//		case 4:
//			final X500Name name = X500Name.getInstance(value);
//			ret = "directoryName" + "=" + name.toString();
//			break;
//		case 5: // SubjectAltName of type ediPartyName not supported
//			break;
//		case 6:
//			ret = "uniformResourceIdentifier" + "=" + DERIA5String.getInstance(value).getString();
//			break;
//		case 7:
//			ASN1OctetString oct = ASN1OctetString.getInstance(value);
//			ret = "iPAddress" + "=" + oct.getOctets();// StringTools.ipOctetsToString(oct.getOctets());
//			break;
//		default: // SubjectAltName of unknown type
//			break;
//		}
//		return ret;
//	}

	private static void printX509Name(X500Name _aName, String tipo) {

		ASN1ObjectIdentifier[] oidv = _aName.getAttributeTypes();

		RDN[] values = _aName.getRDNs();

		HashMap<ASN1ObjectIdentifier, String> hm = new HashMap<ASN1ObjectIdentifier, String>(20);
		String sAname = "\r\n";

		for (int i = 0; i < oidv.length; i++) {
			sAname = sAname + BCStyle.INSTANCE.oidToAttrNames(oidv[i]) + "=" + values[i].toString() + " (OID: " + oidv[i].toString() + ") \r\n";
			hm.put(oidv[i], values[i].toString());
		}
		log.debug(tipo);
		log.debug(sAname);
	}

	private static Map<String, String> getX509Name(X500Name _aName) {

		ASN1ObjectIdentifier[] oidv = _aName.getAttributeTypes();
		RDN[] values = _aName.getRDNs();
		HashMap<String, String> hm = new HashMap<String, String>(20);

		for (int i = 0; i < oidv.length; i++) {
			// sAname = sAname + X500Name.DefaultSymbols.get(oidv.elementAt(i)) + "=" + values.elementAt(i).toString()
			// + " (OID: " + oidv.elementAt(i).toString() + ") \r\n";
			if (BCStyle.INSTANCE.oidToAttrNames(oidv[i]) != null) {
				hm.put(BCStyle.INSTANCE.oidToAttrNames(oidv[i]).toString(), values[i].toString());
			}
		}
		// log.debug( tipo + ":\n " + sAname);
		return hm;
	}

	private static void printSubjectNameForNode(X500Name _aName, String tipo) {

		ASN1ObjectIdentifier[] oidv = _aName.getAttributeTypes();
		RDN[] values = _aName.getRDNs();

		HashMap<ASN1ObjectIdentifier, String> hm = new HashMap<ASN1ObjectIdentifier, String>(20);

		for (int i = 0; i < oidv.length; i++) {
			hm.put(oidv[i], values[i].toString());
		}
		{
			String nome = "<no name>";
			// see BC source code for details about DefaultLookUp behaviour

			ASN1ObjectIdentifier oix = (ASN1ObjectIdentifier) BCStyle.INSTANCE.attrNameToOID("givenname");
			if (hm.containsKey(oix)) {
				nome = hm.get(oix).toString();
				oix = (ASN1ObjectIdentifier) (BCStyle.INSTANCE.attrNameToOID("surname"));
				if (hm.containsKey(oix))
					nome = nome + " " + hm.get(oix).toString();
			} else {
				// check for CN
				oix = (ASN1ObjectIdentifier) (BCStyle.INSTANCE.attrNameToOID("cn"));
				if (hm.containsKey(oix)) {
					nome = hm.get(oix).toString();
				} else {
					// if still not, check for pseudodym
					oix = (ASN1ObjectIdentifier) (BCStyle.INSTANCE.attrNameToOID("pseudonym"));
					if (hm.containsKey(oix))
						nome = hm.get(oix).toString();
				}
			}
			log.debug(tipo);
			log.debug(nome);
		}
	}

	private static String examineKeyUsage(Extension aext) {
		String st = "";

		Extensions extensions = new Extensions(aext);

		KeyUsage ku = KeyUsage.fromExtensions(extensions);

		if ((((DERBitString) ku.toASN1Primitive()).intValue() & KeyUsage.digitalSignature) != 0)
			st = st + " digitalSignature" + term;
		if ((((DERBitString) ku.toASN1Primitive()).intValue() & KeyUsage.nonRepudiation) != 0)
			st = st + " nonRepudiation" + term;
		if ((((DERBitString) ku.toASN1Primitive()).intValue() & KeyUsage.keyEncipherment) != 0)
			st = st + " keyEncipherment" + term;
		if ((((DERBitString) ku.toASN1Primitive()).intValue() & KeyUsage.dataEncipherment) != 0)
			st = st + " dataEncipherment" + term;
		if ((((DERBitString) ku.toASN1Primitive()).intValue() & KeyUsage.keyAgreement) != 0)
			st = st + " keyAgreement" + term;
		if ((((DERBitString) ku.toASN1Primitive()).intValue() & KeyUsage.keyCertSign) != 0)
			st = st + " keyCertSign" + term;
		if ((((DERBitString) ku.toASN1Primitive()).intValue() & KeyUsage.cRLSign) != 0)
			st = st + " cRLSign" + term;
		if ((((DERBitString) ku.toASN1Primitive()).intValue() & KeyUsage.encipherOnly) != 0)
			st = st + " encipherOnly" + term;
		if ((((DERBitString) ku.toASN1Primitive()).intValue() & KeyUsage.decipherOnly) != 0)
			st = st + " decipherOnly" + term;
		return st;
	}

	private static String examineCertificatePolicies(Extension aext) {
		String stx = "";

		PolicyInformation pi = new PolicyInformation(aext.getExtnId());

		ASN1ObjectIdentifier oid = pi.getPolicyIdentifier();
		if (oid.equals(PolicyQualifierId.id_qt_cps)) {
			stx = stx + "cps" + ((" (OID: " + oid.getId() + ")")) + term;
		} else if (oid.equals(PolicyQualifierId.id_qt_unotice)) {
			stx = stx + "unotice" + ((" (OID: " + oid.getId() + ")")) + term;
		} else
			stx = stx + "OID: " + oid.getId() + term;

		ASN1Sequence pqs = (ASN1Sequence) pi.getPolicyQualifiers();
		if (pqs != null) {
			for (int y = 0; y < pqs.size(); y++) {
				PolicyQualifierInfo pqi = PolicyQualifierInfo.getInstance(pqs.getObjectAt(y));
				ASN1ObjectIdentifier oidpqi = pqi.getPolicyQualifierId();
				if (oidpqi.equals(PolicyQualifierId.id_qt_cps)) {
					stx = stx + "cps" + (((" (OID: " + oid.getId() + ")"))) + term;
				} else if (oidpqi.equals(PolicyQualifierId.id_qt_unotice)) {
					stx = stx + "unotice" + ((" (OID: " + oid.getId() + ")")) + term;
				} else
					stx = stx + "OID: " + oidpqi.getId();
				stx = stx + " " + pqi.getQualifier().toString() + term;
			}
		}
		return stx;
	}

	/**
	 * @param aext
	 * @return
	 */
	private static String examineAuthorityKeyIdentifier(Extension aext) {

		Extensions extensions = new Extensions(aext);

		AuthorityKeyIdentifier aki = AuthorityKeyIdentifier.fromExtensions(extensions);
		return "AuthorityKeyIdentifier " + CertificateUtil.printHexBytes(aki.getKeyIdentifier());
	}

	/**
	 * @param aext
	 * @return
	 * @throws IOException
	 */
	private static String examineCRLDistributionPoints(X509Certificate certificato) throws IOException {
		String stx = "";

		final ASN1Primitive obj = getExtensionValue(certificato, Extension.cRLDistributionPoints.getId());

		final ASN1Sequence seq = (ASN1Sequence) obj;

		CRLDistPoint crlDistPoint = CRLDistPoint.getInstance(seq);
		DistributionPoint[] dp = crlDistPoint.getDistributionPoints();

		for (int i = 0; i < dp.length; i++) {

			DistributionPointName dpn = dp[i].getDistributionPoint();
			// custom toString
			if (dpn.getType() == DistributionPointName.FULL_NAME) {
				stx = stx + "fullName:" + term;
			} else {
				stx = stx + "nameRelativeToCRLIssuer:" + term;
			}
			GeneralNames gnx = GeneralNames.getInstance(dpn.getName());
			GeneralName[] gn = gnx.getNames();

			for (int y = 0; y < gn.length; y++) {
				stx = stx + decodeAGeneralName(gn[y]) + term;
			}
			stx = stx + term;

			GeneralNames gns = dp[i].getCRLIssuer();
			if (gns != null) {
				gn = gns.getNames();
				for (int y = 0; y < gn.length; y++) {
					stx = stx + gn[i].toString() + term;
				}
			}

			ReasonFlags rsf = dp[i].getReasons();
			if (rsf != null) {
				if ((rsf.intValue() & ReasonFlags.unused) != 0)
					stx = stx + " unused";
				if ((rsf.intValue() & ReasonFlags.keyCompromise) != 0)
					stx = stx + " keyCompromise";
				if ((rsf.intValue() & ReasonFlags.cACompromise) != 0)
					stx = stx + " cACompromise";
				if ((rsf.intValue() & ReasonFlags.affiliationChanged) != 0)
					stx = stx + " affiliationChanged";
				if ((rsf.intValue() & ReasonFlags.superseded) != 0)
					stx = stx + " superseded";
				if ((rsf.intValue() & ReasonFlags.cessationOfOperation) != 0)
					stx = stx + " cessationOfOperation";
				if ((rsf.intValue() & ReasonFlags.certificateHold) != 0)
					stx = stx + " certificateHold";
				if ((rsf.intValue() & ReasonFlags.privilegeWithdrawn) != 0)
					stx = stx + " privilegeWithdrawn";
				if ((rsf.intValue() & ReasonFlags.aACompromise) != 0)
					stx = stx + " aACompromise";
				stx = stx + term;
			}

		}

		return stx;
	}

	private static String examineExtendedKeyUsage(Extension aext) {
		String stx = "";
		// prepare a reverse lookup of keypurpose id
		// this can be static in another class
		// ExtendedKeyUsage eku = ExtendedKeyUsage.getInstance(aext);

		// Vector<?> usages = eku.getUsages();
		// for(int i = 0; i < usages.size();i++) {
		//// stx = stx+" "+m_aKeyPurposeIdReverseLookUp.get(usages.get(i))+
		//// ( (" (OID: "+usages.get(i)+")"))+term;
		// }

		return stx;
	}

	private static String examineExtension(X509Certificate certificato, Extension aext, ASN1ObjectIdentifier _aOID) {
		try {

			if (_aOID.equals(Extension.keyUsage)) {
				return examineKeyUsage(aext);
			} else if (_aOID.equals(Extension.certificatePolicies)) {
				return examineCertificatePolicies(aext);
			} else if (_aOID.equals(Extension.subjectKeyIdentifier)) {
				return examineSubjectKeyIdentifier(aext);
			} else if (_aOID.equals(Extension.subjectDirectoryAttributes)) {
				return examineSubjectDirectoryAttributes(aext);
			} else if (_aOID.equals(Extension.privateKeyUsagePeriod))
				return examinePrivateKeyUsagePeriod(aext);
			else if (_aOID.equals(Extension.subjectAlternativeName))
				return examineAlternativeName(aext);
			else if (_aOID.equals(Extension.issuerAlternativeName))
				return examineAlternativeName(aext);
			else if (_aOID.equals(Extension.qCStatements)) {
				return examineQCStatements(certificato);
			} else if (_aOID.equals(Extension.authorityInfoAccess))
				return examineAuthorityInfoAccess(aext);
			else if (_aOID.equals(Extension.authorityKeyIdentifier))
				return examineAuthorityKeyIdentifier(aext);
			else if (_aOID.equals(Extension.cRLDistributionPoints))
				return examineCRLDistributionPoints(certificato);
			else if (_aOID.equals(Extension.extendedKeyUsage))
				return examineExtendedKeyUsage(aext);
			else if (_aOID.equals(Extension.basicConstraints))
				return examineBasicConstraints(aext);
			else if (_aOID.equals(Extension.policyConstraints))
				return examinePolicyConstraints(aext);
			else {
				// throw (new java.lang.NoSuchMethodException(term+"While processing OID: " + _aOID.getId() +":"+term+
				// Helpers.printHexBytes(aext.getValue().getOctets())));
			}
		} catch (java.lang.Exception e) {
			String ret = "Exception while processing OID: " + _aOID.getId();
			// _xCert.setCertificateExtensionCommentString(_aOID.getId(), "This extension is NOT recognized.\nIssuer specific extension?");
			return ret;
		}
		return null;
	}

	private static String examineQCStatements(X509Certificate certificato) throws IOException {
		String stx = "";

		ASN1Sequence sequence = (ASN1Sequence) getExtensionValue(certificato, Extension.qCStatements.getId());

		for (int i = 0; i < sequence.size(); i++) {

			QCStatement qcs = QCStatement.getInstance(sequence.getObjectAt(i));

			if (QCStatement.id_etsi_qcs_QcCompliance.equals(qcs.getStatementId())) {
				// process statement - just write a notification that the certificate contains this statement
				stx = stx + "Certificato qualificato (OID: " + qcs.getStatementId() + ")" + term;
			} else if (QCStatement.id_qcs_pkixQCSyntax_v1.equals(qcs.getStatementId())) {
				// process statement - just recognize the statement
				stx = stx + (qcs.getStatementId() + " id_qcs_pkixQCSyntax_v1");
				stx = stx + qcs.getStatementInfo() + term;
			} else if (QCStatement.id_qcs_pkixQCSyntax_v2.equals(qcs.getStatementId())) {
				// process statement - just recognize the statement
				stx = stx + (qcs.getStatementId() + " id_qcs_pkixQCSyntax_v2");
				stx = stx + qcs.getStatementInfo() + term;
			} else if (QCStatement.id_etsi_qcs_QcSSCD.equals(qcs.getStatementId())) {
				// process statement - just write a notification that the certificate contains this statement
				stx = stx + "Dispositivo sicuro (OID: " + qcs.getStatementId() + ")" + term;
			} else if (QCStatement.id_etsi_qcs_RetentionPeriod.equals(qcs.getStatementId())) {
				stx += "Periodo conservazione informazioni relative alla emissione del certificato qualificato: " + qcs.getStatementInfo() + " anni (OID: "
						+ qcs.getStatementId() + ")" + term;
			} else if (QCStatement.id_etsi_qcs_LimiteValue.equals(qcs.getStatementId())) {
				// FIXME: needs to be tested !
				// process statement - write a notification containing the limit value
				MonetaryValue limit = MonetaryValue.getInstance(qcs.getStatementInfo());
				Iso4217CurrencyCode currency = limit.getCurrency();
				double value = limit.getAmount().doubleValue() * Math.pow(10, limit.getExponent().doubleValue());
				/*
				 * This statement is a statement by the issuer which impose a limitation on the value of transaction for which this certificate can be used to
				 * the specified amount (MonetaryValue), according to the Directive 1999/93/EC of the European Parliament and of the Council of 13 December 1999
				 * on a Community framework for electronic signatures, as implemented in the law of the country specified in the issuer field of this
				 * certificate.
				 */
				if (currency.isAlphabetic()) {
					stx = stx + ("QcEuLimitValue (OID: " + qcs.getStatementId() + ")"
							+ new Object[] { currency.getAlphabetic(), new TrustedInput(new Double(value)), limit });
				} else {
					stx = stx + (" QcLimitValueNum" + new Object[] { new Integer(currency.getNumeric()), new TrustedInput(new Double(value)), limit });
				}
			} else
				stx = stx + (" QcUnknownStatement: " + qcs.getStatementId());
		}

		return stx;
	}

	private static String examinePolicyConstraints(Extension aext) {
		String stx = "";
		try {
			ASN1Sequence pc = (ASN1Sequence) aext.toASN1Primitive();
			if (pc != null) {
				Enumeration policyConstraints = pc.getObjects();
				while (policyConstraints.hasMoreElements()) {
					ASN1TaggedObject constraint = (ASN1TaggedObject) policyConstraints.nextElement();
					int tmpInt;

					switch (constraint.getTagNo()) {
					case 0:
						tmpInt = ASN1Integer.getInstance(constraint).getValue().intValue();
						stx = stx + " requireExplicitPolicy: " + tmpInt;
						break;
					case 1:
						tmpInt = ASN1Integer.getInstance(constraint).getValue().intValue();
						stx = stx + " inhibitPolicyMapping: " + tmpInt;
						break;
					}
				}
			}
		} catch (Throwable ae) {
			log.error("Eccezione examinePolicyConstraints", ae);
		}
		return stx;
	}

	private static String examineBasicConstraints(Extension aext) {

		Extensions extensions = new Extensions(aext);

		BasicConstraints bc = BasicConstraints.fromExtensions(extensions);
		String stx = " cA = ";
		if (bc.isCA())
			stx = stx + "true";
		else
			stx = stx + "false";
		stx = stx + term + " pathLenConstraints: ";
		BigInteger pathLen = bc.getPathLenConstraint();
		if (pathLen != null) {
			stx = stx + pathLen;
		} else
			stx = stx + "'no limit'";

		return stx;
	}

	private static String examinePrivateKeyUsagePeriod(Extension aext) {
		PrivateKeyUsagePeriod pku = PrivateKeyUsagePeriod.getInstance(aext);
		ASN1GeneralizedTime from = pku.getNotBefore();
		ASN1GeneralizedTime to = pku.getNotAfter();
		String stx = "PrivateKeyUsagePeriod ";

		try {
			stx = stx + " Not Before: " + getDateStringHelper(from.getDate()) + term;
			stx = stx + " Not After: " + getDateStringHelper(to.getDate());
		} catch (ParseException e) {
			log.error("Eccezione examinePrivateKeyUsagePeriod", e);
		}
		return stx;
	}

	private static String getDateStringHelper(Date _aTime) {
		// force UTC time
		TimeZone gmt = TimeZone.getTimeZone("UTC");
		GregorianCalendar calendar = new GregorianCalendar(gmt, Locale.ITALIAN);
		calendar.setTime(_aTime);
		// string with time only
		// the locale should be the one of the extension not the Java one.
		String time = String.format(Locale.ITALIAN, "", calendar);
		return time;
	}

	private static String examineSubjectKeyIdentifier(Extension aext) {

		Extensions extensions = new Extensions(aext);

		SubjectKeyIdentifier ski = SubjectKeyIdentifier.fromExtensions(extensions);
		return "SubjectKeyIdentifier: " + CertificateUtil.printHexBytes(ski.getKeyIdentifier());
	}

	private static String examineSubjectDirectoryAttributes(Extension aext) {
		String stx = "";
		ASN1Primitive dbj = aext.toASN1Primitive();
		SubjectDirectoryAttributes sda = SubjectDirectoryAttributes.getInstance(dbj);
		/*
		 * SubjectDirectoryAttributes ::= Attributes Attributes ::= SEQUENCE SIZE (1..MAX) OF Attribute Attribute ::= SEQUENCE { type AttributeType values SET
		 * OF AttributeValue }
		 * 
		 * AttributeType ::= OBJECT IDENTIFIER AttributeValue ::= ANY DEFINED BY AttributeType
		 */
		Vector<?> attribv = sda.getAttributes();
		for (int i = 0; i < attribv.size(); i++) {
			Attribute atrb = (Attribute) attribv.get(i);
			ASN1Set asns = atrb.getAttrValues();
			for (int y = 0; y < asns.size(); y++) {
				if (atrb.getAttrType().equals(BCStyle.DATE_OF_BIRTH)) {
					DERGeneralizedTime dgt = (DERGeneralizedTime) DERGeneralizedTime.getInstance(asns.getObjectAt(y));
					// as per CNIPA print the date of birth in localized mode
					TimeZone gmt = TimeZone.getTimeZone("UTC");
					GregorianCalendar calendar = new GregorianCalendar(gmt, Locale.ITALIAN);
					try {
						calendar.setTime(dgt.getDate());
						// string with time only
						// the locale should be the one of the extension not the Java one.
						String time = String.format(Locale.ITALIAN, "", calendar);
						stx = stx + time;
						/*
						 * stx = term+stx+"Original value"+ ((m_bDisplayOID) ? " (OID: "+atrb.getAttrType().getId()+")":"")+ term+" "+dgt.getTime();
						 */
					} catch (ParseException e) {
						stx = stx + term + e.toString();
					}
				} else
					stx = stx + (atrb.getAttrType().getId() + " " + asns.getObjectAt(y).toString());
			}
		}
		return stx;
	}

	private static String examineAlternativeName(Extension aext) throws IOException {
		String stx = "AlternativeName ";
		byte[] extnValue = aext.getExtnValue().getOctets();
		Enumeration<?> it = DERSequence.getInstance(ASN1Primitive.fromByteArray(extnValue)).getObjects();
		while (it.hasMoreElements()) {
			GeneralName genName = GeneralName.getInstance(it.nextElement());
			stx = stx + decodeAGeneralName(genName);
		}
		return stx;
	}

	private static String decodeAGeneralName(GeneralName genName) throws IOException {
		String stx = "";
		switch (genName.getTagNo()) {

		case GeneralName.ediPartyName:
			stx = stx + "ediPartyName: " + genName.getName().toASN1Primitive();
			break;
		case GeneralName.x400Address:
			stx = stx + "x400Address: " + genName.getName().toASN1Primitive();
			break;
		case GeneralName.otherName:
			stx = stx + "otherName: " + genName.getName().toASN1Primitive();
			break;
		case GeneralName.directoryName:
			stx = stx + "directoryName: " + X500Name.getInstance(genName.getName()).toString();
			break;
		case GeneralName.dNSName:
			stx = stx + "dNSName: " + genName.getName();
			break;
		case GeneralName.rfc822Name:
			stx = stx + "e-mail: " + genName.getName();
			break;
		case GeneralName.uniformResourceIdentifier:
			stx = stx + "URI: " + genName.getName();
			break;
		case GeneralName.registeredID:
			stx = stx + "registeredID: " + ASN1ObjectIdentifier.getInstance(genName.getName()).getId();
			break;
		case GeneralName.iPAddress:
			stx = stx + "iPAddress: " + DEROctetString.getInstance(genName.getName()).getOctets();
			break;
		default:
			throw new IOException("Bad tag number: " + genName.getTagNo());
		}
		return stx;
	}

	private static String examineAuthorityInfoAccess(Extension aext) {

		Extensions extensions = new Extensions(aext);

		AuthorityInformationAccess aia = AuthorityInformationAccess.fromExtensions(extensions);
		AccessDescription[] aAccess = aia.getAccessDescriptions();
		String stx = "AuthorityInfoAccess ";
		for (int i = 0; i < aAccess.length; i++) {
			if (aAccess[i].getAccessMethod().equals(AccessDescription.id_ad_caIssuers))
				stx = stx + "caIssuers";
			else if (aAccess[i].getAccessMethod().equals(AccessDescription.id_ad_ocsp))
				stx = stx + "ocsp";
			stx = stx + ": " + term + "  ";
			GeneralName aName = aAccess[i].getAccessLocation();
			try {
				stx = stx + decodeAGeneralName(aName) + " " + term;
			} catch (IOException e) {
				log.error("Eccezione examineAuthorityInfoAccess", e);
			}
		}
		return stx;
	}

	public static String printCertDate(Date _aTime) {
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(_aTime);
		// string with time only
		String time = String.format("%1$tb %1$td %1$tY %1$tH:%1$tM:%1$tS (%1$tZ)", calendar);
		return time;
	}

	public static String printHexBytes(byte[] _theBytes) {
		String _sRet = "";
		for (int i = 0; i < _theBytes.length; i++) {
			if (i != 0 && i % 16 == 0)
				_sRet = _sRet + " \n";
			try {
				_sRet = _sRet + String.format(" %02X", (_theBytes[i] & 0xff));
			} catch (IllegalFormatException e) {
				log.error("Eccezione printHexBytes", e);
			}
		}
		return _sRet;
	}

	public static String printCert(X509Certificate certificato) throws CertificateEncodingException, IOException {

		log.debug("DATI CERTIFICATO");
		byte[] derdata = certificato.getEncoded();
		ByteArrayInputStream as = new ByteArrayInputStream(derdata);
		ASN1InputStream aderin = new ASN1InputStream(as);
		ASN1Primitive ado = aderin.readObject();
		Certificate xc509 = Certificate.getInstance(ado);

		log.debug("Versione certificato: V" + certificato.getVersion());
		log.debug("Serial number: " + certificato.getSerialNumber());
		log.debug("Serial number: " + xc509.getSerialNumber().getValue());
		log.debug("Certificato Valido dal: " + CertificateUtil.printCertDate(xc509.getStartDate().getDate()));
		log.debug("Certificato Valido fino al:  " + CertificateUtil.printCertDate(xc509.getEndDate().getDate()));

		// printSubjectNameForNode(xc509.getIssuer(), "Issuer");
		CertificateUtil.printX509Name(xc509.getIssuer(), "Issuer");
		log.debug("Issuer:::: " + CertificateUtil.getX509Name(xc509.getIssuer()));

		// printSubjectNameForNode( xc509.getSubject(), "Subject" );
		CertificateUtil.printX509Name(xc509.getSubject(), "Subject");
		log.debug("Subject:::: " + CertificateUtil.getX509Name(xc509.getSubject()));

		AlgorithmIdentifier aid = xc509.getSignatureAlgorithm();
		ASN1ObjectIdentifier oi = aid.getAlgorithm();

		log.debug("Subject Public Signature Algorithm: "
				+ ((xc509.getSubjectPublicKeyInfo().getAlgorithm().getAlgorithm().equals(PKCSObjectIdentifiers.rsaEncryption)) ? "pkcs-1 rsaEncryption"
						: oi.getId()));

		byte[] sbjkd = xc509.getSubjectPublicKeyInfo().getPublicKeyData().getBytes();
		log.debug("Subject Public Key Data:\n" + CertificateUtil.printHexBytes(sbjkd));

		log.debug("Signature Algorithm: " + ((xc509.getSignatureAlgorithm().getAlgorithm().equals(PKCSObjectIdentifiers.sha1WithRSAEncryption))
				? "pkcs-1 sha1WithRSAEncryption" : oi.getId()));

		// obtain a byte block of the entire certificate data
		ByteArrayOutputStream bOut = new ByteArrayOutputStream();
		DEROutputStream dOut = new DEROutputStream(bOut);
		try {
			dOut.writeObject(xc509);
			byte[] certBlock = bOut.toByteArray();

			// now compute the certificate SHA1 & MD5 digest
			SHA1Digest digsha1 = new SHA1Digest();
			digsha1.update(certBlock, 0, certBlock.length);
			byte[] hashsha1 = new byte[digsha1.getDigestSize()];
			digsha1.doFinal(hashsha1, 0);
			log.debug("Certificate SHA1 Thumbprint: " + CertificateUtil.printHexBytes(hashsha1));
			MD5Digest digmd5 = new MD5Digest();
			digmd5.update(certBlock, 0, certBlock.length);
			byte[] hashmd5 = new byte[digmd5.getDigestSize()];
			digmd5.doFinal(hashmd5, 0);
			log.debug("Certificate MD5 Thumbprint: " + CertificateUtil.printHexBytes(hashmd5));
		} catch (IOException e) {
			log.error("Eccezione printCert", e);
		}

		// print extensions
		Extensions xc509Ext = xc509.getTBSCertificate().getExtensions();

		Vector<ASN1ObjectIdentifier> extoid = new Vector();
		for (Enumeration<ASN1ObjectIdentifier> enume = xc509Ext.oids(); enume.hasMoreElements();) {
			extoid.add(enume.nextElement());
		}

		// first the critical one
		log.debug("Critical Extensions:");
		for (int i = 0; i < extoid.size(); i++) {
			Extension aext = xc509Ext.getExtension(extoid.get(i));
			if (aext.isCritical())
				try {
					log.debug(extoid.get(i).getId() + " " + CertificateUtil.examineExtension(certificato, aext, extoid.get(i)));
				} catch (Exception e) {
					log.error("Eccezione printCert", e);
				}
		}

		log.debug("Non Critical Extensions:");
		for (int i = 0; i < extoid.size(); i++) {
			Extension aext = xc509Ext.getExtension(extoid.get(i));
			if (!aext.isCritical())
				try {
					log.debug(extoid.get(i).getId() + " " + CertificateUtil.examineExtension(certificato, aext, extoid.get(i)));
				} catch (Exception e) {
					log.error("Eccezione printCert", e);
				}
		}

		return "";
	}
}
