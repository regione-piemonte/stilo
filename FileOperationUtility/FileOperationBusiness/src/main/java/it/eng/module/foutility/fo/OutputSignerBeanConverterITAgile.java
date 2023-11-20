/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.security.cert.CertificateEncodingException;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Map;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import org.apache.commons.codec.binary.Base64;
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

import it.eng.module.foutility.beans.SignerInformationsItagileBean;
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
import it.eng.utility.cryptosigner.controller.bean.OutputSignerBean;
import it.eng.utility.cryptosigner.data.AbstractSigner;

/**
 * 
 * @author DANCRIST
 *
 */

public class OutputSignerBeanConverterITAgile {

	public static final Logger log = LogManager.getLogger(OutputSignerBeanConverterITAgile.class);

	/**
	 * popola il bean jaxb per il risultato delle verifiche a partire dai dati del signerOutput
	 * 
	 * @param verifyResult
	 *            bean da popolare
	 * @param bean
	 *            bean prodotto dal cryptosigner
	 * @param output
	 * @throws CertificateException 
	 */
	public static void populateVerifyResult(ResponseSigVerify response, AbstractSigner signer, List<SigVerifyResultType> listaSigVerifyResultType, List<SigVerifyResultType> listaSigVerifyControfirmeResultType,
			SigVerifyResultType currentSigVerifyResultType, OutputSignerBean bean,
			boolean firmaInterna, boolean childValidation, String tipoFirma) throws CertificateException {
		
		FormatResult fr = new FormatResult();
		fr.setEnvelopeFormat(tipoFirma);

		SigVerifyResult sigVeryResultGLOBALE = new SigVerifyResult();
		if (fr != null)
			sigVeryResultGLOBALE.setFormatResult(fr);

		SignerInformationsItagileBean sitb = addSignInformations(sigVeryResultGLOBALE, bean, listaSigVerifyResultType, listaSigVerifyControfirmeResultType, firmaInterna, childValidation);
		sigVeryResultGLOBALE.setSignerInformations(sitb.getSignerInformations());
		currentSigVerifyResultType.setSigVerifyResult(sigVeryResultGLOBALE);
		
		log.debug("Setto il verification status parziale - allValid " + sitb.isAllValid());
		if (sitb.isAllValid()) {
			currentSigVerifyResultType.setVerificationStatus(VerificationStatusType.OK);
		} else {
			currentSigVerifyResultType.setVerificationStatus(VerificationStatusType.KO);
		}

		if (!firmaInterna) {
			log.debug("Setto il verification status globale - allValid " + sitb.isAllValid());
			if (sitb.isAllValid()) {
				response.setVerificationStatus(VerificationStatusType.OK);
			} else {
				response.setVerificationStatus(VerificationStatusType.KO);
				// alcune verifiche sono fallite
			}
		}
		
		// popolo il timestamp
		TimestampVerificationResult timeStampVerificationResult = new TimestampVerificationResult();
		TimeStampInfotype tst = new TimeStampInfotype();
		// TODO OutputTimeStampBeanConverter.populateTimeStampInfo(tst, documentAndTimeStampInfoBean);
		timeStampVerificationResult.getTimeStampInfo().add(tst);
		sigVeryResultGLOBALE.setTimestampVerificationResult(timeStampVerificationResult);
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

	public static SignerInformationsItagileBean addSignInformations(SigVerifyResult sigVeryResultGLOBALE, OutputSignerBean outputSignerBean, 
			List<SigVerifyResultType> listaSigVerifyResultType, List<SigVerifyResultType> listaSigVerifyControfirmeResultType, boolean firmaInterna, boolean childValidation) {
		
		log.debug("Aggiungo le informazioni sulla singola firma - firmaInterna " + firmaInterna + " childValidation " + childValidation);
		SignerInformationsItagileBean lSignerInformationsItagileBean = new SignerInformationsItagileBean();
		SignerInformations signerInformations = new SignerInformations();
		boolean allValid = true;

		if (listaSigVerifyResultType != null) {
			
			log.debug("Numero firme " + listaSigVerifyResultType.size());
			for (SigVerifyResultType firma : listaSigVerifyResultType) {
				SignerInformationType signerInformation = new SignerInformationType();
				if (firma.getSigVerifyResult().getSignerInformations().getSignerInformation().get(0) != null) {
					
					if (firma.getSigVerifyResult().getSignerInformations().getSignerInformation().get(0).getSigningTime() != null) {
						log.debug("signingTime " + firma.getSigVerifyResult().getSignerInformations().getSignerInformation().get(0).getSigningTime());
						XMLGregorianCalendar signingTime = firma.getSigVerifyResult().getSignerInformations().getSignerInformation().get(0).getSigningTime();
						signerInformation.setSigningTime(signingTime);
					}
					
					SigVerifyResult sigVerifyResult = new SigVerifyResult();
					
					// risultato della verifica di expiration sulla FIRMA
					if (!firmaInterna || (firmaInterna && childValidation)) {
						log.debug("Validazione firma");
						if(firma.getSigVerifyResult().getSignatureValResult().getVerificationStatus().equals(VerificationStatusType.KO)) {
							allValid = false;
						}
						sigVerifyResult.setSignatureValResult(firma.getSigVerifyResult().getSignatureValResult());
					}

					// risultato della verifica di expiration sul CERTIFICATO
					if (!firmaInterna || (firmaInterna && childValidation)) {
						log.debug("Validazione certificati");
						if(firma.getSigVerifyResult().getCertExpirationResult().getVerificationStatus().equals(VerificationStatusType.KO)) {
							allValid = false;
						}
						sigVerifyResult.setCertExpirationResult(firma.getSigVerifyResult().getCertExpirationResult());
					}

					// risultato della verifica per le CRL ( cert revocation list )
					if (!firmaInterna || (firmaInterna && childValidation)) {
						log.debug("Validazione CRL");
						if(firma.getSigVerifyResult().getCRLResult().getVerificationStatus().equals(VerificationStatusType.KO)) {
							allValid = false;
						}
						sigVerifyResult.setCRLResult(firma.getSigVerifyResult().getCRLResult());
					}
					
					// risultato della verifica delle CA
					if (!firmaInterna || (firmaInterna && childValidation)) {
						log.debug("Validazione CA");
						if(firma.getSigVerifyResult().getCAReliabilityResult().getVerificationStatus().equals(VerificationStatusType.KO)) {
							allValid = false;
						}
						sigVerifyResult.setCAReliabilityResult(firma.getSigVerifyResult().getCAReliabilityResult());
					}
					signerInformation.setSigVerifyResult(sigVerifyResult);
	

					X509Certificate x509Certificato = null;
					try {
						byte[] byteResult = Base64.decodeBase64(firma.getSigVerifyResult().getSignerInformations().getSignerInformation().get(0).getCertificato().getContenuto());
						x509Certificato = (X509Certificate) CertificateFactory.getInstance("X.509").generateCertificate(new ByteArrayInputStream(byteResult));
						//log.debug("Certificato firma " + x509Certificato );
					} catch (CertificateException e1) {
						log.error("Controfirma: Eccezione costruzione x509Certificato", e1);
					}
					
					if (x509Certificato != null) {
						try {
							Certificato certificato = new Certificato();
							signerInformation.setCertificato(certificato);
							certificato.setContenuto(firma.getSigVerifyResult().getSignerInformations().getSignerInformation().get(0).getCertificato().getContenuto());
							certificato.setSerialNumber(firma.getSigVerifyResult().getSignerInformations().getSignerInformation().get(0).getCertificato().getSerialNumber());

							XMLGregorianCalendar xmlDataDecorrenza = firma.getSigVerifyResult().getSignerInformations().getSignerInformation().get(0).getCertificato().getDataDecorrenza();
							if (xmlDataDecorrenza != null)
								certificato.setDataDecorrenza(xmlDataDecorrenza);
							
							XMLGregorianCalendar xmlDataScadenza = firma.getSigVerifyResult().getSignerInformations().getSignerInformation().get(0).getCertificato().getDataScadenza();
							if (xmlDataScadenza != null)
								certificato.setDataScadenza(xmlDataScadenza);

							X500Name x500name = new JcaX509CertificateHolder(x509Certificato).getSubject();
							if (x500name != null) {
								Map<String, String> x509NameSubject = CertificateUtil.getX509Name(x509Certificato, "Subject");
								DnType subjectDn = new DnType();
								certificato.setSubject(firma.getSigVerifyResult().getSignerInformations().getSignerInformation().get(0).getCertificato().getSubject());
								if (firma.getSigVerifyResult().getSignerInformations().getSignerInformation().get(0).getCertificato().getSubject() != null)
									subjectDn.setName(bonificaSubject(firma.getSigVerifyResult().getSignerInformations().getSignerInformation().get(0).getCertificato().getSubject().getName()));

								RDN[] cns = x500name.getRDNs(BCStyle.CN);
								if (cns != null && cns.length > 0) {
									RDN cn = cns[0];
									if (cn != null && cn.getFirst() != null) {
										subjectDn.setCn(IETFUtils.valueToString(cn.getFirst().getValue()));
									} else {
										subjectDn.setCn(x509NameSubject.get("CN"));
									}
								}
								subjectDn.setGivenName(x509NameSubject.get("GIVENNAME"));
								subjectDn.setSerialNumber(x509NameSubject.get("SERIALNUMBER"));
								subjectDn.setSurname(x509NameSubject.get("SURNAME"));
								subjectDn.setDn(x509NameSubject.get("DN"));

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
								if (firma.getSigVerifyResult().getSignerInformations().getSignerInformation().get(0).getCertificato().getIssuer() != null)
									issuerDn.setName(bonificaSubject(firma.getSigVerifyResult().getSignerInformations().getSignerInformation().get(0).getCertificato().getIssuer().getName()));

								RDN[] cns = x500nameIssuer.getRDNs(BCStyle.CN);
								if (cns != null && cns.length > 0) {
									RDN cn = cns[0];
									if (cn != null && cn.getFirst() != null) {
										issuerDn.setCn(IETFUtils.valueToString(cn.getFirst().getValue()));
									} else {
										issuerDn.setCn(x509NameIssuer.get("CN"));
									}
								}
								issuerDn.setGivenName(x509NameIssuer.get("GIVENNAME"));
								issuerDn.setSerialNumber(x509NameIssuer.get("SERIALNUMBER"));
								issuerDn.setSurname(x509NameIssuer.get("SURNAME"));
								issuerDn.setDn(x509NameIssuer.get("DN"));

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

				// MARCHE PER FIRMA
				if (firma.getSigVerifyResult().getSignerInformations().getSignerInformation().get(0).getMarca() != null) {
					
					Marca marcaType = new Marca();
					marcaType.setContenuto(firma.getSigVerifyResult().getSignerInformations().getSignerInformation().get(0).getMarca().getContenuto());
					marcaType.setTsaName(firma.getSigVerifyResult().getSignerInformations().getSignerInformation().get(0).getMarca().getTsaName());
					marcaType.setDate(firma.getSigVerifyResult().getSignerInformations().getSignerInformation().get(0).getMarca().getDate());
					marcaType.setSerialNumber(firma.getSigVerifyResult().getSignerInformations().getSignerInformation().get(0).getMarca().getSerialNumber());
					marcaType.setPolicy(firma.getSigVerifyResult().getSignerInformations().getSignerInformation().get(0).getMarca().getPolicy());
					marcaType.setVerificationStatus(firma.getSigVerifyResult().getSignerInformations().getSignerInformation().get(0).getMarca().getVerificationStatus());
					
					signerInformation.setMarca(marcaType);
				}
				
				
				// CONTROFIRMA
				//if(firma.getSigVerifyResult().getSignerInformations().getSignerInformation().get(0).getControFirma() != null) {
				if(listaSigVerifyControfirmeResultType != null && !listaSigVerifyControfirmeResultType.isEmpty()) {
					
					log.debug("numero controfirme " + listaSigVerifyControfirmeResultType.size() );
					for( SigVerifyResultType controfirmaItem  : listaSigVerifyControfirmeResultType){
					SignerInformationType signerInformationControFirma = new SignerInformationType();

					SignerInformationType el = controfirmaItem.getSigVerifyResult().getSignerInformations().getSignerInformation().get(0);
					X509Certificate x509Certificato = null;
					try {
						byte[] byteResult = Base64.decodeBase64(el.getCertificato().getContenuto());
						x509Certificato = (X509Certificate) CertificateFactory.getInstance("X509").generateCertificate(new ByteArrayInputStream(byteResult));
						//log.debug("Certificato controfirma " + x509Certificato );
					} catch (CertificateException e1) {
						log.error("Controfirma: Eccezione costruzione x509Certificato", e1);
					}
					
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
								if (el.getCertificato().getSubject() != null)
									subjectDn.setName(el.getCertificato().getSubject().getName());

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
								if (el.getCertificato().getIssuer() != null)
									issuerDn.setName(el.getCertificato().getIssuer().getName());

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
					signerInformation.setControFirma(signerInformationControFirma);
					}
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
		
		if (!firmaInterna || (firmaInterna && childValidation)) {
			if (allValid) {
				AbstractResponseOperationType arotOK = new AbstractResponseOperationType();
				arotOK.setVerificationStatus(VerificationStatusType.OK);
				sigVeryResultGLOBALE.setSignatureValResult(arotOK);
				sigVeryResultGLOBALE.setCertExpirationResult(arotOK);
				sigVeryResultGLOBALE.setCRLResult(arotOK);
				sigVeryResultGLOBALE.setCAReliabilityResult(arotOK);
				sigVeryResultGLOBALE.setDetectionCodeResult(arotOK);
			} else {
				AbstractResponseOperationType arotKO = new AbstractResponseOperationType();
				arotKO.setVerificationStatus(VerificationStatusType.KO);
				sigVeryResultGLOBALE.setSignatureValResult(arotKO);
				sigVeryResultGLOBALE.setCertExpirationResult(arotKO);
				sigVeryResultGLOBALE.setCRLResult(arotKO);
				sigVeryResultGLOBALE.setCAReliabilityResult(arotKO);
				sigVeryResultGLOBALE.setDetectionCodeResult(arotKO);
			}
		} else {
			AbstractResponseOperationType arotSKIPPER = new AbstractResponseOperationType();
			arotSKIPPER.setVerificationStatus(VerificationStatusType.SKIPPED);
			sigVeryResultGLOBALE.setSignatureValResult(arotSKIPPER);
			sigVeryResultGLOBALE.setCertExpirationResult(arotSKIPPER);
			sigVeryResultGLOBALE.setCRLResult(arotSKIPPER);
			sigVeryResultGLOBALE.setCAReliabilityResult(arotSKIPPER);
			sigVeryResultGLOBALE.setDetectionCodeResult(arotSKIPPER);
		}
		
		lSignerInformationsItagileBean.setSignerInformations(signerInformations);
		lSignerInformationsItagileBean.setAllValid(allValid);
		
		return lSignerInformationsItagileBean;
	}

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

	private static XMLGregorianCalendar getXMLGregorianCalendarDate(Date data) {
		GregorianCalendar c = new GregorianCalendar();
		c.setTime(data);
		XMLGregorianCalendar date2 = null;
		try {
			date2 = DatatypeFactory.newInstance().newXMLGregorianCalendar(c);

		} catch (DatatypeConfigurationException e) {
			log.error(e);
		}
		return date2;
	}
}