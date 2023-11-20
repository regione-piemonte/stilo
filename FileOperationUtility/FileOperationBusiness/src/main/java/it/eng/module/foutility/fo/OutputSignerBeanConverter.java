/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.security.cert.CertificateEncodingException;
import java.security.cert.X509Certificate;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.bouncycastle.asn1.ASN1InputStream;
import org.bouncycastle.asn1.ASN1OctetString;
import org.bouncycastle.asn1.ASN1Primitive;
import org.bouncycastle.asn1.x500.RDN;
import org.bouncycastle.asn1.x500.X500Name;
import org.bouncycastle.asn1.x500.style.BCStyle;
import org.bouncycastle.asn1.x500.style.IETFUtils;
import org.bouncycastle.cert.jcajce.JcaX509CertificateHolder;
import org.bouncycastle.tsp.TimeStampToken;

import it.eng.module.foutility.beans.OutputOperations;
import it.eng.module.foutility.beans.generated.AbstractResponseOperationType;
import it.eng.module.foutility.beans.generated.DnType;
import it.eng.module.foutility.beans.generated.KeyUsages;
import it.eng.module.foutility.beans.generated.QcStatements;
import it.eng.module.foutility.beans.generated.ResponseSigVerify;
import it.eng.module.foutility.beans.generated.SigVerifyResultType;
import it.eng.module.foutility.beans.generated.SigVerifyResultType.SigVerifyResult;
import it.eng.module.foutility.beans.generated.SigVerifyResultType.SigVerifyResult.FormatResult;
import it.eng.module.foutility.beans.generated.SigVerifyResultType.SigVerifyResult.SignerInformations;
import it.eng.module.foutility.beans.generated.SigVerifyResultType.SigVerifyResult.TimestampVerificationResult;
import it.eng.module.foutility.beans.generated.SignerInformationType;
import it.eng.module.foutility.beans.generated.SignerInformationType.Certificato;
import it.eng.module.foutility.beans.generated.SignerInformationType.Marca;
import it.eng.module.foutility.beans.generated.TimeStampInfotype;
import it.eng.module.foutility.beans.generated.TipoFirmaQAType;
import it.eng.module.foutility.beans.generated.VerificationStatusType;
import it.eng.module.foutility.util.CertificateUtil;
import it.eng.module.foutility.util.FileOpMessage;
import it.eng.utility.cryptosigner.bean.SignerBean;
import it.eng.utility.cryptosigner.controller.bean.DocumentAndTimeStampInfoBean;
import it.eng.utility.cryptosigner.controller.bean.ErrorBean;
import it.eng.utility.cryptosigner.controller.bean.OutputSignerBean;
import it.eng.utility.cryptosigner.controller.bean.ValidationInfos;
import it.eng.utility.cryptosigner.data.signature.ISignature;

/**
 * converte l'output del cryptosigner nei bean jaxb per la response
 * 
 * @author Russo
 *
 */
public class OutputSignerBeanConverter {

	public static final Logger log = LogManager.getLogger(OutputSignerBeanConverter.class);

	/**
	 * popola il bean jaxb per il risultato delle verifiche a partire dai dati del signerOutput
	 * 
	 * @param verifyResult
	 *            bean da popolare
	 * @param bean
	 *            bean prodotto dal cryptosigner
	 * @param output
	 */
	public static void populateVerifyResult(ResponseSigVerify response, SigVerifyResultType verifyResult, OutputSignerBean bean, boolean firmaInterna,
			boolean childValidation, boolean tsaReliability) {
		// output per validità formato
		// log.debug("Metodo populateVerifyResult: firmaInterna " + firmaInterna + " childValidation " + childValidation);
		boolean allValid = true;// think positive
		FormatResult fr = new FormatResult();
		ValidationInfos vinfos = null;
		// if(!firmaInterna || (firmaInterna && childValidation) ){
		log.debug("Validazione formato");
		vinfos = bean.getPropOfType(OutputSignerBean.FORMAT_VALIDITY_PROPERTY, ValidationInfos.class);
		populateResponseFromValidationInfo(fr, vinfos);
		// }
		String format = bean.getPropOfType(OutputSignerBean.ENVELOPE_FORMAT_PROPERTY, java.lang.String.class);
		if (format != null) {
			log.debug("Formato busta " + format);
			fr.setEnvelopeFormat(format);
		}
		allValid &= (vinfos != null && vinfos.isValid()) || vinfos == null;

		// prendo il risultato della verifica della firma
		AbstractResponseOperationType sigValResult = null;
		ValidationInfos vinfossigValid = null;
		if (!firmaInterna || (firmaInterna && childValidation)) {
			log.debug("Validazione firma");
			sigValResult = new AbstractResponseOperationType();
			vinfossigValid = getMapValidationInfos(bean, OutputSignerBean.SIGNATURE_VALIDATION_PROPERTY);
			populateResponseFromValidationInfo(sigValResult, vinfossigValid);
		}
		allValid &= (vinfossigValid != null && vinfossigValid.isValid()) || vinfossigValid == null;

		// risultato della verifica di expiration sui certificati
		AbstractResponseOperationType certExpired = null;
		ValidationInfos vinfoscertExp = null;
		if (!firmaInterna || (firmaInterna && childValidation)) {
			log.debug("Validazione certificato");
			certExpired = new AbstractResponseOperationType();
			vinfoscertExp = getMapValidationInfos(bean, OutputSignerBean.CERTIFICATE_EXPIRATION_PROPERTY);
			populateResponseFromValidationInfo(certExpired, vinfoscertExp);
		}
		allValid &= (vinfoscertExp != null && vinfoscertExp.isValid()) || vinfoscertExp == null;

		// risultato della verifica per le CRL
		AbstractResponseOperationType crlCheck = null;
		ValidationInfos vinfoscrl = null;
		if (!firmaInterna || (firmaInterna && childValidation)) {
			log.debug("Validazione crl");
			crlCheck = new AbstractResponseOperationType();
			vinfoscrl = getMapValidationInfos(bean, OutputSignerBean.CRL_VALIDATION_PROPERTY);
			populateResponseFromValidationInfo(crlCheck, vinfoscrl);
		}
		allValid &= (vinfoscrl != null && vinfoscrl.isValid()) || vinfoscrl == null;

		// risultato della verifica di affidabilita'
		AbstractResponseOperationType caCheck = null;
		ValidationInfos vinfoCa = null;
		if (!firmaInterna || (firmaInterna && childValidation)) {
			log.debug("Validazione ca");
			caCheck = new AbstractResponseOperationType();
			vinfoCa = getMapValidationInfos(bean, OutputSignerBean.CERTIFICATE_UNQUALIFIED_PROPERTY);
			populateResponseFromValidationInfo(caCheck, vinfoCa);
		}
		allValid &= (vinfoCa != null && vinfoCa.isValid()) || vinfoCa == null;

		// risultato della verifica detection code
		AbstractResponseOperationType detectCodeCheck = null;
		ValidationInfos vinfoDetectCode = null;
		if (!firmaInterna || (firmaInterna && childValidation)) {
			log.debug("Validazione codice eseguibile");
			detectCodeCheck = new AbstractResponseOperationType();
			vinfoDetectCode = bean.getPropOfType(OutputSignerBean.CODE_DETECTION_VALIDATION_PRIOPERTY, ValidationInfos.class);
			populateResponseFromValidationInfo(detectCodeCheck, vinfoDetectCode);
		}
		allValid &= (vinfoDetectCode != null && vinfoDetectCode.isValid()) || vinfoDetectCode == null;

		if (!firmaInterna || (firmaInterna && childValidation)) {
			log.debug("Setto il verification status parziale - allValid " + allValid);
			if (allValid) {
				verifyResult.setVerificationStatus(VerificationStatusType.OK);
			} else {
				verifyResult.setVerificationStatus(VerificationStatusType.KO);
			}
		} else {
			verifyResult.setVerificationStatus(VerificationStatusType.SKIPPED);
		}

		SigVerifyResult sigVeryResult = new SigVerifyResult();

		if (fr != null)
			sigVeryResult.setFormatResult(fr);
		if (sigValResult != null)
			sigVeryResult.setSignatureValResult(sigValResult);
		if (certExpired != null)
			sigVeryResult.setCertExpirationResult(certExpired);
		if (crlCheck != null)
			sigVeryResult.setCRLResult(crlCheck);
		if (caCheck != null)
			sigVeryResult.setCAReliabilityResult(caCheck);
		if (detectCodeCheck != null)
			sigVeryResult.setDetectionCodeResult(detectCodeCheck);

		sigVeryResult.setSignerInformations(addSignInformations(bean, firmaInterna, childValidation, tsaReliability ));
		verifyResult.setSigVerifyResult(sigVeryResult);

		if (!firmaInterna) {
			log.debug("Setto il verification status globale - allValid " + allValid);
			if (allValid) {
				response.setVerificationStatus(VerificationStatusType.OK);
			} else {
				response.setVerificationStatus(VerificationStatusType.KO);
				// alcune verifiche sono fallite
			}
		}

		// popolo il timestamp
		List<DocumentAndTimeStampInfoBean> tsinfos = (List<DocumentAndTimeStampInfoBean>) bean.getProperty(OutputSignerBean.TIME_STAMP_INFO_PROPERTY);
		if (tsinfos != null && tsinfos.size() > 0) {
			TimestampVerificationResult timeStampVerificationResult = new TimestampVerificationResult();
			for (DocumentAndTimeStampInfoBean documentAndTimeStampInfoBean : tsinfos) {
				log.debug("Validazione timestamp");
				TimeStampInfotype tst = new TimeStampInfotype();
				OutputTimeStampBeanConverter.populateTimeStampInfo(tst, documentAndTimeStampInfoBean, tsaReliability);
				timeStampVerificationResult.getTimeStampInfo().add(tst);
			}
			sigVeryResult.setTimestampVerificationResult(timeStampVerificationResult);
		}
	}

	/**
	 * ritorna un oggetto validationinfo relativo ad una prop che è una mappa di validaioninfo associate alle varie firme: ad esempio la prop
	 * SIGNATURE_VALIDATION_PROPERTY contiene la mappa con le info di validità per ogni firma, in tal caso il metodo ritorna un oggetto validationInfo che
	 * contien gli errori e i warning di tutte le validationinfo
	 * 
	 * @param outputSignerBean
	 * @param property
	 * @return
	 */
	public static ValidationInfos getMapValidationInfos(OutputSignerBean outputSignerBean, String property) {
		OutputSignerBean currentOutput = outputSignerBean;
		ValidationInfos result = null;

		Map<ISignature, ValidationInfos> validations = (Map<ISignature, ValidationInfos>) currentOutput.getProperty(property);
		if (validations != null) {
			result = new ValidationInfos();
			Set<ISignature> crlValidationSet = validations.keySet();
			for (ISignature crlInfo : crlValidationSet) {
				String subject = "";
				if( crlInfo.getSignerBean()!=null && crlInfo.getSignerBean().getSubject()!=null ){
					subject = crlInfo.getSignerBean().getSubject().getName();
					subject = bonificaSubject(subject);
				}
				ValidationInfos crlValidationInfos = validations.get(crlInfo);
				if (!crlValidationInfos.isValid()) {
					List<ErrorBean> listaErrori = crlValidationInfos.getErrorsBean();
					for (ErrorBean error : listaErrori) {
						result.addErrorWithCode(error.getCode(), subject + ": " + error.getDescription());
						result.addError(subject + ": " + error.getDescription());
					}
				}
				// result.addError( subject + ": " + crlValidationInfos.getErrorsBeanString());
				if (crlValidationInfos.getWarnings() != null) {
					List<ErrorBean> listaWarning = crlValidationInfos.getWarningsBean();
					for (ErrorBean error : listaWarning) {
						result.addWarningWithCode(error.getCode(), subject + ": " + error.getDescription());
						result.addWarning(subject + ": " + error.getDescription());
					}
				}
				
			}
		}
		return result;
	}

	/**
	 * metodo che bonifica il subject che potrebbe contenere caratteri strani che mandano in bomba l'xml di risposta
	 * 
	 * @param subject
	 * @return
	 */
	private static String bonificaSubject(String subject) {

		if (subject == null)
			return "";

		StringBuilder sb = new StringBuilder();
		for (byte b : subject.getBytes()) {
			if (b < 32)
				sb.append((char) ' ');
			else
				sb.append((char) b);
		}

		return sb.toString();

	}

	public static ValidationInfos getMapValidationInfos(OutputSignerBean outputSignerBean, String property, ISignature firma) {
		OutputSignerBean currentOutput = outputSignerBean;
		ValidationInfos result = null;

		Map<ISignature, ValidationInfos> validations = (Map<ISignature, ValidationInfos>) currentOutput.getProperty(property);
		if (validations != null) {
			result = new ValidationInfos();
			Set<ISignature> crlValidationSet = validations.keySet();
			for (ISignature crlInfo : crlValidationSet) {
				if (crlInfo.equals(firma)) {
					ValidationInfos crlValidationInfos = validations.get(crlInfo);
					if (!crlValidationInfos.isValid()) {
						List<ErrorBean> listaErrori = crlValidationInfos.getErrorsBean();
						for (ErrorBean error : listaErrori) {
							result.addErrorWithCode(error.getCode(), error.getDescription());
							result.addError(error.getDescription());
						}
						// result.addError( crlValidationInfos.getErrorsBeanString());
						// result.addError( subject + ": " + crlValidationInfos.getErrorsString());
						if (crlValidationInfos.getWarnings() != null) {
							List<ErrorBean> listaWarning = crlValidationInfos.getWarningsBean();
							for (ErrorBean warning : listaWarning) {
								result.addErrorWithCode(warning.getCode(), warning.getDescription());
								result.addError(warning.getDescription());
							}
							// result.addWarning( crlValidationInfos.getWarningsString());
							// result.addWarning( subject + ": " + crlValidationInfos.getWarningsString());
						}
					}
				}
			}
		}
		return result;
	}

	public static SignerInformations addSignInformations(OutputSignerBean outputSignerBean, boolean firmaInterna, boolean childValidation, boolean tsaReliability) {
		log.debug("Aggiungo le informazioni sulla singola firma - firmaInterna " + firmaInterna + " childValidation " + childValidation);
		OutputSignerBean currentOutput = outputSignerBean;

		SignerInformations signerInformations = new SignerInformations();

		List<ISignature> listaFirme = (List<ISignature>) currentOutput.getProperty(OutputSignerBean.SIGNATURE_PROPERTY);
		Map<ISignature, ValidationInfos> validationInfosMap = (Map<ISignature, ValidationInfos>) currentOutput
				.getProperty(OutputSignerBean.SIGNATURE_VALIDATION_PROPERTY);
		Map<byte[], TimeStampToken> mapSignatureTimeStampToken = (Map<byte[], TimeStampToken>) currentOutput
				.getProperty(OutputSignerBean.MAP_SIGNATURE_TIME_STAMP_PROPERTY);
		List<DocumentAndTimeStampInfoBean> timeStampInfos = (List<DocumentAndTimeStampInfoBean>) currentOutput
				.getProperty(OutputSignerBean.TIME_STAMP_INFO_PROPERTY);

		if (listaFirme != null) {
			boolean allValid = true;
			for (ISignature firma : listaFirme) {
				allValid = true;
				SignerInformationType signerInformation = new SignerInformationType();
				SignerBean signerBean = firma.getSignerBean();
				if (signerBean != null) {
					if (signerBean.getSigningTime() != null) {
						log.debug("signingTime " + signerBean.getSigningTime());
						XMLGregorianCalendar signingTime = getXMLGregorianCalendarDate(signerBean.getSigningTime());
						signerInformation.setSigningTime(signingTime);
					}

					SigVerifyResult sigVerifyResult = new SigVerifyResult();
					signerInformation.setSigVerifyResult(sigVerifyResult);

					ValidationInfos vinfossigValid = null;
					if (!firmaInterna || (firmaInterna && childValidation)) {
						AbstractResponseOperationType sigValResult = new AbstractResponseOperationType();
						log.info("Validazione firma");
						vinfossigValid = getMapValidationInfos(currentOutput, OutputSignerBean.SIGNATURE_VALIDATION_PROPERTY, firma);
						populateResponseFromValidationInfo(sigValResult, vinfossigValid);
						sigVerifyResult.setSignatureValResult(sigValResult);
					}
					allValid &= (vinfossigValid != null && vinfossigValid.isValid()) || vinfossigValid == null;

					// risultato della verifica di expiration sui certificati
					ValidationInfos vinfoscertExp = null;
					if (!firmaInterna || (firmaInterna && childValidation)) {
						vinfoscertExp = getMapValidationInfos(currentOutput, OutputSignerBean.CERTIFICATE_EXPIRATION_PROPERTY, firma);
						AbstractResponseOperationType certExpired = new AbstractResponseOperationType();
						log.debug("Validazione certificato");
						populateResponseFromValidationInfo(certExpired, vinfoscertExp);
						sigVerifyResult.setCertExpirationResult(certExpired);
					}
					allValid &= (vinfoscertExp != null && vinfoscertExp.isValid()) || vinfoscertExp == null;

					// risultato della verifica per le CRL
					ValidationInfos vinfoscrl = null;
					if (!firmaInterna || (firmaInterna && childValidation)) {
						vinfoscrl = getMapValidationInfos(currentOutput, OutputSignerBean.CRL_VALIDATION_PROPERTY, firma);
						AbstractResponseOperationType crlCheck = new AbstractResponseOperationType();
						log.debug("Validazione crl");
						populateResponseFromValidationInfo(crlCheck, vinfoscrl);
						sigVerifyResult.setCRLResult(crlCheck);
					}
					allValid &= (vinfoscrl != null && vinfoscrl.isValid()) || vinfoscrl == null;

					// risultato della verifica di affidabilita'
					ValidationInfos vinfoCa = null;
					if (!firmaInterna || (firmaInterna && childValidation)) {
						vinfoCa = getMapValidationInfos(currentOutput, OutputSignerBean.CERTIFICATE_UNQUALIFIED_PROPERTY, firma);
						AbstractResponseOperationType caCheck = new AbstractResponseOperationType();
						log.debug("Validazione ca");
						populateResponseFromValidationInfo(caCheck, vinfoCa);
						sigVerifyResult.setCAReliabilityResult(caCheck);
					}
					allValid &= (vinfoCa != null && vinfoCa.isValid()) || vinfoCa == null;

					//
					// ValidationInfos validazioneFirma = validationInfosMap.get( firma );
					// if(validazioneFirma!=null){
					// if(validazioneFirma.isValid()){
					// signerInformation.setVerificationStatus(VerificationStatusType.OK);
					// }else{
					// OutputOperations.addErrors(signerInformation, FileOpMessage.SIGN_OP_ERROR, validazioneFirma.getErrors(), VerificationStatusType.KO );
					// OutputOperations.addWarnings(signerInformation, FileOpMessage.SIGN_OP_WARNING, validazioneFirma.getWarnings() );
					// }
					// }
					X509Certificate x509Certificato = signerBean.getCertificate();
					if (x509Certificato != null) {
						try {
							// try {
							// CertificateUtil.printCert( x509Certificato );
							// } catch (IOException e) {
							// // TODO Auto-generated catch block
							// e.printStackTrace();
							// }

							Certificato certificato = new Certificato();
							signerInformation.setCertificato(certificato);

							certificato.setContenuto(x509Certificato.getEncoded());

							certificato.setSerialNumber(CertificateUtil.getCertificateSerialNumber(x509Certificato));

							Date dataDecorrenza = x509Certificato.getNotBefore();
							XMLGregorianCalendar xmlDataDecorrenza = getXMLGregorianCalendarDate(dataDecorrenza);
							if (xmlDataDecorrenza != null)
								certificato.setDataDecorrenza(xmlDataDecorrenza);
							Date dataScadenza = x509Certificato.getNotAfter();
							XMLGregorianCalendar xmlDataScadenza = getXMLGregorianCalendarDate(dataScadenza);
							if (xmlDataScadenza != null)
								certificato.setDataScadenza(xmlDataScadenza);

							X500Name x500name = new JcaX509CertificateHolder(x509Certificato).getSubject();
							if (x500name != null) {
								Map<String, String> x509NameSubject = CertificateUtil.getX509Name(x509Certificato, "Subject");
								DnType subjectDn = new DnType();
								certificato.setSubject(subjectDn);
								if (signerBean.getSubject() != null)
									subjectDn.setName(bonificaSubject(signerBean.getSubject().getName()));

								RDN[] cns = x500name.getRDNs(BCStyle.CN);
								if (cns != null && cns.length > 0) {
									RDN cn = cns[0];
									if (cn != null && cn.getFirst() != null) {
										subjectDn.setCn(IETFUtils.valueToString(cn.getFirst().getValue()));
									} else {
										subjectDn.setCn(x509NameSubject.get("CN"));
									}
								}
								
								RDN[] serialNumbers = x500name.getRDNs(BCStyle.SERIALNUMBER);
								if (serialNumbers != null && serialNumbers.length > 0) {
									RDN serialNumber = serialNumbers[0];
									if (serialNumber != null && serialNumber.getFirst() != null) {
										subjectDn.setSerialNumber(IETFUtils.valueToString(serialNumber.getFirst().getValue()));
									} else {
										subjectDn.setSerialNumber(x509NameSubject.get("SERIALNUMBER"));
									}
								}
								RDN[] givenNames = x500name.getRDNs(BCStyle.GIVENNAME);
								if (givenNames != null && givenNames.length > 0) {
									RDN givenName = givenNames[0];
									if (givenName != null && givenName.getFirst() != null) {
										subjectDn.setGivenName(IETFUtils.valueToString(givenName.getFirst().getValue()));
									} else {
										subjectDn.setGivenName(x509NameSubject.get("GIVENNAME"));
									}
								}
								RDN[] dns = x500name.getRDNs(BCStyle.DN_QUALIFIER);
								if (dns != null && dns.length > 0) {
									RDN dn = dns[0];
									if (dn != null && dn.getFirst() != null) {
										subjectDn.setDn(IETFUtils.valueToString(dn.getFirst().getValue()));
									} else {
										subjectDn.setDn(x509NameSubject.get("DN"));
									}
								}
								RDN[] surnames = x500name.getRDNs(BCStyle.SURNAME);
								if (surnames != null && surnames.length > 0) {
									RDN surname = surnames[0];
									if (surname != null && surname.getFirst() != null) {
										subjectDn.setSurname(IETFUtils.valueToString(surname.getFirst().getValue()));
									} else {
										subjectDn.setSurname(x509NameSubject.get("SURNAME"));
									}
								}
								//subjectDn.setGivenName(x509NameSubject.get("GIVENNAME"));
								//subjectDn.setSerialNumber(x509NameSubject.get("SERIALNUMBER"));
								//subjectDn.setSurname(x509NameSubject.get("SURNAME"));
								//subjectDn.setDn(x509NameSubject.get("DN"));

								RDN[] ous = x500name.getRDNs(BCStyle.OU);
								if (ous != null && ous.length > 0) {
									RDN ou = ous[0];
									if (ou != null && ou.getFirst() != null) {
										subjectDn.setOu(IETFUtils.valueToString(ou.getFirst().getValue()));
									}
								}
								RDN[] os = x500name.getRDNs(BCStyle.O);
								if (os != null && os.length > 0) {
									RDN o = os[0];
									if (o != null && o.getFirst() != null) {
										subjectDn.setO(IETFUtils.valueToString(o.getFirst().getValue()));
									}
								}
								RDN[] cs = x500name.getRDNs(BCStyle.C);
								if (cs != null && cs.length > 0) {
									RDN c = cs[0];
									if (c != null && c.getFirst() != null) {
										subjectDn.setC(IETFUtils.valueToString(c.getFirst().getValue()));
									}
								}
							}

							X500Name x500nameIssuer = new JcaX509CertificateHolder(x509Certificato).getIssuer();
							if (x500nameIssuer != null) {
								Map<String, String> x509NameIssuer = CertificateUtil.getX509Name(x509Certificato, "Issuer");
								DnType issuerDn = new DnType();
								certificato.setIssuer(issuerDn);
								if (signerBean.getIusser() != null)
									issuerDn.setName(bonificaSubject(signerBean.getIusser().getName()));

								RDN[] cns = x500nameIssuer.getRDNs(BCStyle.CN);
								if (cns != null && cns.length > 0) {
									RDN cn = cns[0];
									if (cn != null && cn.getFirst() != null) {
										issuerDn.setCn(IETFUtils.valueToString(cn.getFirst().getValue()));
									} else {
										issuerDn.setCn(x509NameIssuer.get("CN"));
									}
								}
								
								RDN[] serialNumbers = x500nameIssuer.getRDNs(BCStyle.SERIALNUMBER);
								if (serialNumbers != null && serialNumbers.length > 0) {
									RDN serialNumber = serialNumbers[0];
									if (serialNumber != null && serialNumber.getFirst() != null) {
										issuerDn.setSerialNumber(IETFUtils.valueToString(serialNumber.getFirst().getValue()));
									} else {
										issuerDn.setSerialNumber(x509NameIssuer.get("SERIALNUMBER"));
									}
								}
								RDN[] givenNames = x500nameIssuer.getRDNs(BCStyle.GIVENNAME);
								if (givenNames != null && givenNames.length > 0) {
									RDN givenName = givenNames[0];
									if (givenName != null && givenName.getFirst() != null) {
										issuerDn.setGivenName(IETFUtils.valueToString(givenName.getFirst().getValue()));
									} else {
										issuerDn.setGivenName(x509NameIssuer.get("GIVENNAME"));
									}
								}
								RDN[] dns = x500nameIssuer.getRDNs(BCStyle.DN_QUALIFIER);
								if (dns != null && dns.length > 0) {
									RDN dn = dns[0];
									if (dn != null && dn.getFirst() != null) {
										issuerDn.setDn(IETFUtils.valueToString(dn.getFirst().getValue()));
									} else {
										issuerDn.setDn(x509NameIssuer.get("DN"));
									}
								}
								RDN[] surnames = x500nameIssuer.getRDNs(BCStyle.SURNAME);
								if (surnames != null && surnames.length > 0) {
									RDN surname = surnames[0];
									if (surname != null && surname.getFirst() != null) {
										issuerDn.setSurname(IETFUtils.valueToString(surname.getFirst().getValue()));
									} else {
										issuerDn.setSurname(x509NameIssuer.get("SURNAME"));
									}
								}
								//issuerDn.setGivenName(x509NameIssuer.get("GIVENNAME"));
								//issuerDn.setSerialNumber(x509NameIssuer.get("SERIALNUMBER"));
								//issuerDn.setSurname(x509NameIssuer.get("SURNAME"));
								//issuerDn.setDn(x509NameIssuer.get("DN"));

								RDN[] ous = x500nameIssuer.getRDNs(BCStyle.OU);
								if (ous != null && ous.length > 0) {
									RDN ou = ous[0];
									if (ou != null && ou.getFirst() != null) {
										issuerDn.setOu(IETFUtils.valueToString(ou.getFirst().getValue()));
									}
								}
								RDN[] os = x500nameIssuer.getRDNs(BCStyle.O);
								if (os != null && os.length > 0) {
									RDN o = os[0];
									if (o != null && o.getFirst() != null) {
										issuerDn.setO(IETFUtils.valueToString(o.getFirst().getValue()));
									}
								}
								RDN[] cs = x500nameIssuer.getRDNs(BCStyle.C);
								if (cs != null && cs.length > 0) {
									RDN c = cs[0];
									if (c != null && c.getFirst() != null) {
										issuerDn.setC(IETFUtils.valueToString(c.getFirst().getValue()));
									}
								}
							}

							QcStatements qcStatements = new QcStatements();
							List<String> qcs = CertificateUtil.getQCStatements(x509Certificato);
							boolean isFirmaQaulificata = true;
							for (String qc : qcs){
								qcStatements.getQcStatement().add(qc);
								if( qc.contains("sigillo elettronico"))
									isFirmaQaulificata = false;
							}
							certificato.setQcStatements(qcStatements);

							KeyUsages keyUsages = new KeyUsages();
							List<String> kus = CertificateUtil.getKeyUsage(x509Certificato);
							for (String ku : kus)
								keyUsages.getKeyUsage().add(ku);
							certificato.setKeyUsages(keyUsages);

							if( isFirmaQaulificata )
								signerInformation.setTipoFirmaQA( TipoFirmaQAType.Q );
							else
								signerInformation.setTipoFirmaQA( TipoFirmaQAType.A );
						} catch (CertificateEncodingException e) {
							log.error("Eccezione addSignInformations", e);
						}

						
					}
					
					
				}

				if (mapSignatureTimeStampToken != null && !mapSignatureTimeStampToken.isEmpty()) {
					Iterator<byte[]> itr = mapSignatureTimeStampToken.keySet().iterator();
					byte[] firmaBytes = firma.getSignatureBytes();
					String firmaString = new String();
					if (firmaBytes != null) {
						firmaString = new String(firmaBytes);
					}
					TimeStampToken timeStampToken = null;
					while (itr.hasNext()) {
						byte[] chiave = itr.next();
						String firmaString1 = new String(chiave);
						if (firmaString.equalsIgnoreCase(firmaString1))
							timeStampToken = mapSignatureTimeStampToken.get(chiave);
					}
					if (timeStampToken != null && timeStampToken.getTimeStampInfo() != null) {
						Marca marcaType = new Marca();
						for (DocumentAndTimeStampInfoBean timeStampInfo : timeStampInfos) {
							TimeStampToken t = timeStampInfo.getTimeStampToken();

							try {
								if (new String(t.getEncoded()).equals(new String(timeStampToken.getEncoded()))) {
									ValidationInfos validazioneMarca = timeStampInfo.getValidationInfos();
									if ( /*tsaReliability &&*/ validazioneMarca != null) {
										if (validazioneMarca.isValid()) {
											marcaType.setVerificationStatus(VerificationStatusType.OK);
										} else {
											OutputOperations.addErrors(marcaType, validazioneMarca.getErrorsBean(), VerificationStatusType.KO);
											OutputOperations.addWarnings(marcaType, FileOpMessage.SIGN_OP_WARNING, validazioneMarca.getWarnings());
										}
									}/* else {
										marcaType.setVerificationStatus(VerificationStatusType.SKIPPED);
									}*/
								}
							} catch (IOException e) {
								log.error("Eccezione addSignInformations", e);
							}
						}

						try {
							marcaType.setContenuto(timeStampToken.getEncoded());
						} catch (IOException e1) {
							log.error("Eccezione addSignInformations", e1);
						}
						if (timeStampToken.getTimeStampInfo().getTsa() != null)
							marcaType.setTsaName(timeStampToken.getTimeStampInfo().getTsa().getName().toString());
						if (timeStampToken.getTimeStampInfo().getGenTime() != null) {
							XMLGregorianCalendar dataTimeStamp = getXMLGregorianCalendarDate(timeStampToken.getTimeStampInfo().getGenTime());
							if (dataTimeStamp != null)
								marcaType.setDate(dataTimeStamp);
						}
						// MODIFICA ANNA 1.53
						// marcaType.setPolicy( timeStampToken.getTimeStampInfo().getPolicy() );
						marcaType.setPolicy(timeStampToken.getTimeStampInfo().getPolicy().getId());
						marcaType.setSerialNumber("" + timeStampToken.getTimeStampInfo().getSerialNumber());
						signerInformation.setMarca(marcaType);
					}
				}

				List<ISignature> listaControFirme = firma.getCounterSignatures();
				for (ISignature controFirma : listaControFirme) {
					SignerInformationType signerInformationControFirma = new SignerInformationType();
					SignerBean signerBeanControFirma = controFirma.getSignerBean();
					if (signerBeanControFirma != null) {

						if (signerBeanControFirma.getSigningTime() != null) {
							log.debug("signingTime controfirma " + signerBeanControFirma.getSigningTime());
							XMLGregorianCalendar signingTime = getXMLGregorianCalendarDate(signerBeanControFirma.getSigningTime());
							signerInformationControFirma.setSigningTime(signingTime);
						}
						
						X509Certificate x509Certificato = signerBeanControFirma.getCertificate();
						if (x509Certificato != null) {
							try {
								Certificato certificato = new Certificato();
								signerInformationControFirma.setCertificato(certificato);

								certificato.setContenuto(x509Certificato.getEncoded());

								Date dataDecorrenza = x509Certificato.getNotBefore();
								XMLGregorianCalendar xmlDataDecorrenza = getXMLGregorianCalendarDate(dataDecorrenza);
								if (xmlDataDecorrenza != null)
									certificato.setDataDecorrenza(xmlDataDecorrenza);
								Date dataScadenza = x509Certificato.getNotAfter();
								XMLGregorianCalendar xmlDataScadenza = getXMLGregorianCalendarDate(dataScadenza);
								if (xmlDataScadenza != null)
									certificato.setDataScadenza(xmlDataScadenza);

								X500Name x500name = new JcaX509CertificateHolder(x509Certificato).getSubject();

								if (x500name != null) {
									DnType subjectDn = new DnType();
									certificato.setSubject(subjectDn);
									if (signerBean.getSubject() != null)
										subjectDn.setName(signerBeanControFirma.getSubject().getName());

									RDN[] cns = x500name.getRDNs(BCStyle.CN);
									if (cns != null && cns.length > 0) {
										RDN cn = cns[0];
										if (cn != null && cn.getFirst() != null) {
											subjectDn.setCn(IETFUtils.valueToString(cn.getFirst().getValue()));
										}
									}
									RDN[] ous = x500name.getRDNs(BCStyle.OU);
									if (ous != null && ous.length > 0) {
										RDN ou = ous[0];
										if (ou != null && ou.getFirst() != null) {
											subjectDn.setOu(IETFUtils.valueToString(ou.getFirst().getValue()));
										}
									}
									RDN[] os = x500name.getRDNs(BCStyle.O);
									if (os != null && os.length > 0) {
										RDN o = os[0];
										if (o != null && o.getFirst() != null) {
											subjectDn.setO(IETFUtils.valueToString(o.getFirst().getValue()));
										}
									}
									RDN[] cs = x500name.getRDNs(BCStyle.C);
									if (cs != null && cs.length > 0) {
										RDN c = cs[0];
										if (c != null && c.getFirst() != null) {
											subjectDn.setC(IETFUtils.valueToString(c.getFirst().getValue()));
										}
									}
								}

								X500Name x500nameIssuer = new JcaX509CertificateHolder(x509Certificato).getIssuer();
								if (x500nameIssuer != null) {
									DnType issuerDn = new DnType();
									certificato.setIssuer(issuerDn);
									if (signerBean.getIusser() != null)
										issuerDn.setName(signerBeanControFirma.getIusser().getName());

									RDN[] cns = x500nameIssuer.getRDNs(BCStyle.CN);
									if (cns != null && cns.length > 0) {
										RDN cn = cns[0];
										if (cn != null && cn.getFirst() != null) {
											issuerDn.setCn(IETFUtils.valueToString(cn.getFirst().getValue()));
										}
									}
									RDN[] ous = x500nameIssuer.getRDNs(BCStyle.OU);
									if (ous != null && ous.length > 0) {
										RDN ou = ous[0];
										if (ou != null && ou.getFirst() != null) {
											issuerDn.setOu(IETFUtils.valueToString(ou.getFirst().getValue()));
										}
									}
									RDN[] os = x500nameIssuer.getRDNs(BCStyle.O);
									if (os != null && os.length > 0) {
										RDN o = os[0];
										if (o != null && o.getFirst() != null) {
											issuerDn.setO(IETFUtils.valueToString(o.getFirst().getValue()));
										}
									}
									RDN[] cs = x500nameIssuer.getRDNs(BCStyle.C);
									if (cs != null && cs.length > 0) {
										RDN c = cs[0];
										if (c != null && c.getFirst() != null) {
											issuerDn.setC(IETFUtils.valueToString(c.getFirst().getValue()));
										}
									}
								}

							} catch (CertificateEncodingException e) {
								log.error("Eccezione addSignInformations", e);
							}
						}

					}
					signerInformation.setControFirma(signerInformationControFirma);
				}

				if (!firmaInterna || (firmaInterna && childValidation)) {
					if (allValid) {
						signerInformation.setVerificationStatus(VerificationStatusType.OK);
					} else {
						signerInformation.setVerificationStatus(VerificationStatusType.KO);
					}
				} else {
					signerInformation.setVerificationStatus(VerificationStatusType.SKIPPED);
				}

				signerInformations.getSignerInformation().add(signerInformation);
			}
		}

		return signerInformations;
	}

	// https://joinup.ec.europa.eu/svn/sd-dss/trunk/apps/dss/dss-document/src/main/java/eu/europa/ec/markt/dss/validation/SignedDocumentValidator.java

	protected static ASN1Primitive getExtensionValue(X509Certificate cert, String oid) {
		if (cert == null) {
			return null;
		}
		byte[] bytes = cert.getExtensionValue(oid);
		return getDerObjectFromByteArray(bytes);

	}

	private static ASN1Primitive getDerObjectFromByteArray(byte[] bytes) {
		if (bytes == null) {
			return null;
		}
		ASN1InputStream aIn = new ASN1InputStream(new ByteArrayInputStream(bytes));
		try {
			ASN1OctetString octs = (ASN1OctetString) aIn.readObject();
			aIn = new ASN1InputStream(new ByteArrayInputStream(octs.getOctets()));
			return aIn.readObject();
		} catch (IOException e) {
			throw new RuntimeException("Caught an unexected IOException", e);
		}
	}

	// costruisce la risposta in base alla validationinfo legata alal prop passata
	private static void populateResponseFromValidationInfo(AbstractResponseOperationType response, ValidationInfos vinfos) {

		if (vinfos != null) {
			if (vinfos.isValid()) {
				log.debug("Is valid");
				response.setVerificationStatus(VerificationStatusType.OK);
			} else {
				log.debug("Is not valid");
				VerificationStatusType status = VerificationStatusType.KO;
				// se non puoi eseguire il controllo ritorna un error
				if (vinfos.isCannotExecute()) {
					status = VerificationStatusType.ERROR;
				}
				OutputOperations.addErrors(response, vinfos.getErrorsBean(), status);
				
			}
			log.debug("WARNING " +vinfos.getWarnings() );
			if (vinfos.getWarnings() != null && vinfos.getWarnings().length > 0)
				OutputOperations.addWarnings(response, vinfos.getWarningsBean());
		} else {
			response.setVerificationStatus(VerificationStatusType.SKIPPED);
		}
	}

	private static XMLGregorianCalendar getXMLGregorianCalendarDate(Date data) {
		GregorianCalendar c = new GregorianCalendar();
		c.setTime(data);
		XMLGregorianCalendar date2 = null;
		try {
			date2 = DatatypeFactory.newInstance().newXMLGregorianCalendar(c);

		} catch (DatatypeConfigurationException e) {
		}
		return date2;
	}

	// private static ASN1Primitive getSingleValuedSignedAttribute( AttributeTable unsignedAttrTable, AttributeTable signedAttrTable,
	// ASN1ObjectIdentifier attrOID, String printableName)
	// throws CMSException
	// {
	// if (unsignedAttrTable != null
	// && unsignedAttrTable.getAll(attrOID).size() > 0)
	// {
	// throw new CMSException("The " + printableName
	// + " attribute MUST NOT be an unsigned attribute");
	// }
	//
	// if (signedAttrTable == null)
	// {
	// return null;
	// }
	//
	// ASN1EncodableVector v = signedAttrTable.getAll(attrOID);
	// switch (v.size())
	// {
	// case 0:
	// return null;
	// case 1:
	// {
	// Attribute t = (Attribute)v.get(0);
	// ASN1Set attrValues = t.getAttrValues();
	// if (attrValues.size() != 1)
	// {
	// throw new CMSException("A " + printableName
	// + " attribute MUST have a single attribute value");
	// }
	//
	// return attrValues.getObjectAt(0).toASN1Primitive();
	// }
	// default:
	// throw new CMSException("The SignedAttributes in a signerInfo MUST NOT include multiple instances of the "
	// + printableName + " attribute");
	// }
	// }
}
