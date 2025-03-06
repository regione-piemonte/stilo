package it.eng.utility.cryptosigner.data;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.Provider;
import java.security.Security;
import java.security.cert.CRL;
import java.security.cert.CertPath;
import java.security.cert.Certificate;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.xml.crypto.dom.DOMStructure;
import javax.xml.crypto.dsig.XMLSignature;
import javax.xml.crypto.dsig.XMLSignatureFactory;
import javax.xml.crypto.dsig.dom.DOMValidateContext;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.commons.io.FileUtils;
import org.bouncycastle.tsp.TimeStampRequest;
import org.bouncycastle.tsp.TimeStampRequestGenerator;
import org.bouncycastle.tsp.TimeStampToken;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.sun.org.apache.xerces.internal.jaxp.DocumentBuilderFactoryImpl;

import es.mityc.firmaJava.libreria.xades.DatosFirma;
import es.mityc.firmaJava.libreria.xades.DatosNodosFirmados;
import es.mityc.firmaJava.libreria.xades.DatosSelloTiempo;
import es.mityc.firmaJava.libreria.xades.DatosTipoFirma;
import es.mityc.firmaJava.libreria.xades.ResultadoValidacion;
import es.mityc.firmaJava.libreria.xades.ValidarFirmaXML;
import es.mityc.firmaJava.libreria.xades.errores.FirmaXMLError;
import es.mityc.javasign.EnumFormatoFirma;
import it.eng.utility.cryptosigner.controller.bean.ValidationInfos;
import it.eng.utility.cryptosigner.data.signature.ISignature;
import it.eng.utility.cryptosigner.data.signature.XaDESSignature;
import it.eng.utility.cryptosigner.data.type.SignerType;
import it.eng.utility.cryptosigner.provider.MyDOMXMLSignatureFactory;
import it.eng.utility.cryptosigner.provider.MyProvider;
import it.eng.utility.cryptosigner.utils.MessageConstants;
import it.eng.utility.cryptosigner.utils.MessageHelper;

/**
 * Implementa i controlli su firme di tipo XAdES. Il contenuto di un file � riconosciuto se implementa le specifiche ETSI TS 101 903
 * 
 * @author Stefano Zennaro
 * 
 */
public class XMLSigner extends AbstractSigner {

	private ValidarFirmaXML xmlValidator;

	/*
	 * Lista degli elementi Signature contenuti all'interno del file XML
	 */
	private List<XMLSignature> xmlSignatures = null;

	/*
	 * Il nodo signature contenente il timestamp
	 */
	private Node timeStampSignatureNode = null;

	private Node signatureValueNode = null;

	private SignerType type = null;

	// Si suppone che il metodo di canonicalizzazione del contenuto xml
	// sia lo stesso per tutte le firme
	private String canonicalizationMethod = null;

	private List<ResultadoValidacion> validationResults;

	// Il documento XML parserizzato
	private Document doc = null;

	public XMLSigner() {
		xmlValidator = new ValidarFirmaXML();
	}

	private void populateValidationResults(File file) throws FirmaXMLError {
		validationResults = null;
		timestamptokens = null;
		// FileInputStream stream = FileUtils.openInputStream(file);
		validationResults = xmlValidator.validar(file, null, null);
		ArrayList<TimeStampToken> timestamptokenList = new ArrayList<TimeStampToken>();
		if (validationResults != null) {
			for (ResultadoValidacion validationResult : validationResults) {
				DatosFirma signatureData = validationResult.getDatosFirma();
				List<DatosSelloTiempo> timeInfos = signatureData.getDatosSelloTiempo();
				if (timeInfos != null && timeInfos.size() != 0) {
					// timestamptokenList.add(timeInfos.get(0).getTst());
				}
			}
		}
		if (timestamptokenList.size() != 0)
			timestamptokens = timestamptokenList.toArray(new TimeStampToken[timestamptokenList.size()]);
	}

	public static void main(String[] args) {
		XMLSigner signer = new XMLSigner();
		File file = new File("C:/Users/Anna Tesauro/Desktop/testPDF - Copia.pdf");
		boolean isOk = signer.isSignedType(file);
		System.out.println(isOk);

		boolean esitoCancellazione = file.delete();
		System.out.println(esitoCancellazione);
	}

	/**
	 * Restituisce true se il contenuto del file rispetta lo schema XAdES e contiene almeno una firma (nodo signature) ed eventualmente un timestamp
	 */
	public boolean isSignedType(File file) {
		// Resetto il signer
		reset();

		this.type = null;
		try {
			populateValidationResults(file);
		} catch (Exception e) {
			log.info("Errore in isSignedType di XMLSigner - " + e.getMessage() + " - Il file non ha firma xml"/*, e*/);
			return false;
		}

		if (validationResults == null)
			return false;

		/*
		 * Attualmente si assume che tutte le firme (parallele) contenute all'interno dello stesso file abbiano lo stesso formato
		 */
		ResultadoValidacion result = validationResults.get(0);
		if (result.getDatosFirma() == null)
			return false;
		DatosTipoFirma tipoFirma = result.getDatosFirma().getTipoFirma();
		if (tipoFirma == null)
			return false;

		this.type = enumFormatoFirma2SignerType(tipoFirma.getTipoXAdES());

		/*
		 * Parserizza il documento per ricavare gli elementi contenenti le firme
		 */
		InputStream stream = null;
		try {
			stream = FileUtils.openInputStream(file);
			DocumentBuilderFactory dbf = DocumentBuilderFactoryImpl.newInstance();
			dbf.setNamespaceAware(true);
			doc = dbf.newDocumentBuilder().parse(stream);
			NodeList signatureNodesList = doc.getElementsByTagNameNS(XMLSignature.XMLNS, "Signature");
			if (signatureNodesList.getLength() == 0) {
				throw new Exception("Cannot find Signature element");
			}
			for (int i = 0; i < signatureNodesList.getLength(); i++) {

				Node signatureNode = signatureNodesList.item(i);

				Provider myProvider = new MyProvider();
				Security.insertProviderAt(myProvider, 1);
				// String providerName =
				// System.getProperty("jsr105Provider","org.jcp.xml.dsig.internal.dom.XMLDSigRI");
				// javax.xml.crypto.dsig.XMLSignatureFactory fac =
				// javax.xml.crypto.dsig.XMLSignatureFactory.getInstance("DOM",
				// myProvider);
				XMLSignatureFactory factory = MyDOMXMLSignatureFactory.getInstance("DOM", myProvider);
				DOMStructure struct = new DOMStructure(signatureNode);

				// System.out.println(struct);
				XMLSignature xmlSignature = factory.unmarshalXMLSignature(struct);

				/*
				 * In questo modo si ritiene che una sola firma contenga un timestamp TODO: - se pi� firme lo contengono occorrerebbe confrontarne il contenuto,
				 * per verificare che si tratta di firme orizzontali
				 */
				if (signatureNode instanceof Element) {
					Element signatureNodeElement = (Element) signatureNode;
					if (xmlSignatures == null)
						xmlSignatures = new ArrayList<XMLSignature>();
					xmlSignatures.add(xmlSignature);

					NodeList objectsList = signatureNodeElement.getElementsByTagNameNS(XMLSignature.XMLNS, "Object");
					for (int j = 0; j < objectsList.getLength(); ++j) {
						Node object = objectsList.item(j);
						if (object instanceof Element) {

							Element objectElement = (Element) object;
							String objectNameSpace = null;

							NamedNodeMap attributes = objectElement.getAttributes();
							for (int k = 0; k < attributes.getLength(); ++k) {
								Node attributeNode = attributes.item(k);
								String attributeName = attributeNode.getNodeName();
								if (attributeName.startsWith("xmlns") && attributeName.length() > 6) {
									objectNameSpace = attributeName.substring(6);
									break;
								}
							}
							if (objectNameSpace == null) {
								NodeList childList = objectElement.getChildNodes();
								if (childList != null) {
									for (int k = 0; k < childList.getLength(); ++k) {
										Node child = childList.item(k);
										if (child instanceof Element) {
											Element childElement = (Element) child;
											if (childElement.getNodeName().contains("QualifyingProperties")) {
												NamedNodeMap attributesChild = childElement.getAttributes();
												for (int l = 0; l < attributesChild.getLength(); ++l) {
													Node attributesChildNode = attributesChild.item(l);
													String attributeName = attributesChildNode.getNodeName();
													if (attributeName.startsWith("xmlns") && attributeName.length() > 6) {
														objectNameSpace = attributesChildNode.getNodeValue();// attributeName.substring(6);
														log.info("ObjectNameSpace " + objectNameSpace);
														break;
													}
												}
											}
										}
									}
								}
							}

							NodeList timeStampTokenList = objectNameSpace == null ? objectElement.getElementsByTagName("SignatureTimeStamp")
									: objectElement.getElementsByTagNameNS(objectNameSpace, "SignatureTimeStamp");
							if (timeStampTokenList.getLength() == 1) {
								timeStampSignatureNode = signatureNode;
								Node timeStampTokenNode = timeStampTokenList.item(0);
								if (timeStampTokenNode instanceof Element) {
									Element timeStampTokenElement = (Element) timeStampTokenNode;
									NodeList canonicalizationMethodList = timeStampTokenElement.getElementsByTagName("CanonicalizationMethod");
									if (canonicalizationMethodList != null && canonicalizationMethodList.getLength() != 0) {
										Node canonicalizationMethodNode = canonicalizationMethodList.item(0);
										if (canonicalizationMethodNode instanceof Element) {
											canonicalizationMethod = ((Element) canonicalizationMethodNode).getAttribute("Algorithm");
										}
									}
								}
							}
						}
					}

				}
			}
		} catch (Exception e) {
			// Nonostante sia stato generato un errore, può
			// comunque essere una firma di tipo XAdES in quanto
			// si pu� trattare di un errore di decodifica di una parte
			// nel caso il tipo di formato sia stato rilevato, restituisco
			// comunque true
			log.error("Error in isSignedType di XMLSigner ", e);
			if (this.type != null) {
				return true;
			}
			return false;
		}
		return this.type != null && xmlSignatures != null;
	}

	public TimeStampToken[] getTimeStampTokens(File file) {
		if (timestamptokens == null)
			try {
				populateValidationResults(file);
			} catch (FirmaXMLError e) {
			}
		return timestamptokens;
	}

	/**
	 * Metodo di utilit� che consente di mappare il formato {@link es.mityc.firmaJava.libreria.xades.EnumFormatoFirma} nel corrispondente tipo
	 * it.eng.utility.cryptosigner.data.type.SignerType
	 * 
	 * @param formatoFirma
	 *            formato firma in input
	 * @return formato corrispondente
	 */
	public static final SignerType enumFormatoFirma2SignerType(EnumFormatoFirma formatoFirma) {
		switch (formatoFirma) {
		case XAdES_BES:
			return SignerType.XAdES_BES;
		case XAdES_C:
			return SignerType.XAdES_C;
		case XAdES_T:
			return SignerType.XAdES_T;
		case XAdES_X:
			return SignerType.XAdES_X;
		case XAdES_XL:
			return SignerType.XAdES_XL;
		default:
			return SignerType.XAdES;
		}
	}

	private Node parseSignatureForSignatureValue(Node signatureNode) {
		Node signatureValueNode = null;
		if (signatureNode instanceof Element) {
			Element signatureNodeElement = (Element) signatureNode;
			NodeList signatureValueList = signatureNodeElement.getElementsByTagNameNS(XMLSignature.XMLNS, "SignatureValue");
			if (signatureValueList != null && signatureValueList.getLength() != 0) {
				signatureValueNode = signatureValueList.item(0);
			}
		}
		return signatureValueNode;
	}

	public ValidationInfos validateTimeStampTokensEmbedded(File file) {
		ValidationInfos validationInfos = new ValidationInfos();

		if (xmlSignatures == null || xmlSignatures.size() == 0) {
			validationInfos.addErrorWithCode(MessageConstants.SIGN_FILE_NOTSIGNED, MessageHelper.getMessage(MessageConstants.SIGN_FILE_NOTSIGNED));
			validationInfos.addError(MessageHelper.getMessage(MessageConstants.SIGN_FILE_NOTSIGNED));
			return validationInfos;
		}
		if (this.timestamptokens == null || timestamptokens.length == 0) {
			if (!this.isSignedType(file)) {
				validationInfos.addErrorWithCode(MessageConstants.FV_FILE_FORMAT_ERROR,
						MessageHelper.getMessage(MessageConstants.FV_FILE_FORMAT_ERROR, this.getFormat()));
				validationInfos.addError(MessageHelper.getMessage(MessageConstants.FV_FILE_FORMAT_ERROR, this.getFormat()));
				return validationInfos;
			} else
				getTimeStampTokens(file);
		}

		// validationInfos.setValidatedObject(timestamptoken);

		if (type == SignerType.XAdES || type == SignerType.XAdES_BES) {
			validationInfos.addErrorWithCode(MessageConstants.SIGN_FORMAT_WITHOUT_MARK,
					MessageHelper.getMessage(MessageConstants.SIGN_FORMAT_WITHOUT_MARK, this.type));
			validationInfos.addError(MessageHelper.getMessage(MessageConstants.SIGN_FORMAT_WITHOUT_MARK, this.type));
			return validationInfos;
		}

		for (TimeStampToken timestamptoken : timestamptokens) {
			TimeStampRequestGenerator gen = new TimeStampRequestGenerator();
			// MODIFICA ANNA 1.53
			// String hashAlgOID = timestamptoken.getTimeStampInfo().getMessageImprintAlgOID();
			String hashAlgOID = timestamptoken.getTimeStampInfo().getMessageImprintAlgOID().getId();
			MessageDigest digest;
			String canonicalizerID = null;
			try {

				digest = MessageDigest.getInstance(hashAlgOID);
				byte[] buffer = null;
				if (signatureValueNode == null)
					signatureValueNode = parseSignatureForSignatureValue(timeStampSignatureNode);

				/*
				 * Formatto in maniera canonica il contenuto del nodo signature
				 */
				canonicalizerID = (canonicalizationMethod == null || "".equals(canonicalizationMethod.trim()))
						? org.apache.xml.security.c14n.Canonicalizer.ALGO_ID_C14N_OMIT_COMMENTS : canonicalizationMethod;
				org.apache.xml.security.c14n.Canonicalizer canonicalizer = org.apache.xml.security.c14n.Canonicalizer.getInstance(canonicalizerID);
				// canonicalizerID = (canonicalizationMethod == null ||
				// "".equals(canonicalizationMethod.trim()) ) ?
				// com.sun.org.apache.xml.internal.security.c14n.Canonicalizer.ALGO_ID_C14N_OMIT_COMMENTS
				// : canonicalizationMethod;
				// org.apache.xml.security.c14n.Canonicalizer canonicalizer =
				// org.apache.xml.security.c14n.Canonicalizer.getInstance(canonicalizerID);

				buffer = canonicalizer.canonicalizeSubtree(signatureValueNode);

				TimeStampRequest request = gen.generate(hashAlgOID, digest.digest(buffer));
				checkTimeStampTokenOverRequest(validationInfos, timestamptoken, request);
			} catch (NoSuchAlgorithmException e) {
				validationInfos.addErrorWithCode(MessageConstants.SIGN_MARK_ALGORITHM_NOTSUPPORTED,
						MessageHelper.getMessage(MessageConstants.SIGN_MARK_ALGORITHM_NOTSUPPORTED, hashAlgOID));
				validationInfos.addError(MessageHelper.getMessage(MessageConstants.SIGN_MARK_ALGORITHM_NOTSUPPORTED, hashAlgOID));
			} catch (Exception e) {
				validationInfos.addErrorWithCode(MessageConstants.SIGN_MARK_ALGORITHM_NOTSUPPORTED,
						MessageHelper.getMessage(MessageConstants.SIGN_MARK_ALGORITHM_NOTSUPPORTED, canonicalizerID));
				validationInfos.addError(MessageHelper.getMessage(MessageConstants.SIGN_MARK_ALGORITHM_NOTSUPPORTED, canonicalizerID));
			}
		}
		return validationInfos;
	}

	public SignerType getFormat() {
		return this.type;
	}

	public InputStream getUnsignedContent(File file) {
		/*
		 * Si ritiene che tutte le firme parallele si rieferiscano allo stesso contenuto
		 */
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		ResultadoValidacion result = validationResults.get(0);

		List<DatosNodosFirmados> signedNodes = result.getDatosFirma().getDatosNodosNoSignFirmados();
		try {
			bos.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>".getBytes());
			for (DatosNodosFirmados signedData : signedNodes) {
				log.debug("ID " + signedData.getId());
				// if (!signedData.getId().startsWith("SignedProperties_"))
				if(signedData.getNodoFirmadoBytes()!=null){
					bos.write(signedData.getNodoFirmadoBytes());
					bos.flush();
				}
			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			log.error("Error getUnsignedContent", e);
			return null;
		}
		return new ByteArrayInputStream(bos.toByteArray());
	}

	public byte[] getUnsignedContentDigest(MessageDigest digestAlgorithm, File file) {
		/*
		 * Si ritiene che tutte le firme parallele si rieferiscano allo stesso contenuto
		 */
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		ResultadoValidacion result = validationResults.get(0);
		List<DatosNodosFirmados> signedNodes = result.getDatosFirma().getDatosNodosFirmados();
		for (DatosNodosFirmados signedData : signedNodes) {
			try {
				bos.write(signedData.getNodoFirmadoBytes());
			} catch (IOException e) {
				log.error("Error getUnsignedContentDigest", e);
				return null;
			}
		}
		// TODO Auto-generated method stub
		return digestAlgorithm.digest(bos.toByteArray());
	}

	/*
	 * Genera una firma (parserizzando le controfirme contenute)
	 */
	private ISignature getISignatureFromResultadoValidacionAndXMLSignature(ResultadoValidacion validationResult, XMLSignature xmlSignature) {
		XaDESSignature signature = new XaDESSignature(xmlSignature, null, null);

		signature.setValidationResult(validationResult);
		CertPath firma = validationResult.getDatosFirma().getCadenaFirma();
		if (firma != null) {
			Certificate certificate = firma.getCertificates().get(0);
			if (certificate instanceof X509Certificate) {
				DOMValidateContext context = new DOMValidateContext(certificate.getPublicKey(), validationResult.getDoc());
				signature.setCertificate((X509Certificate) certificate);
				signature.setValidateContext(context);
			}
		}
		List<ResultadoValidacion> counterSignaturesResults = validationResult.getContrafirmadoPor();
		if (counterSignaturesResults != null) {
			List<ISignature> counterSignatures = new ArrayList<ISignature>();
			for (ResultadoValidacion counterSignatureResult : counterSignaturesResults) {
				ISignature counterSignature = getISignatureFromResultadoValidacionAndXMLSignature(counterSignatureResult, xmlSignature);
				counterSignatures.add(counterSignature);
			}
			signature.setCounterSignatures(counterSignatures);
		}
		return signature;

	}

	public List<ISignature> getSignatures(File file) {

		List<ISignature> signatures = new ArrayList<ISignature>();
		int i = 0;
		if (xmlSignatures == null)
			return signatures;
		for (XMLSignature xmlSignature : xmlSignatures) {
			ResultadoValidacion validationResult = validationResults.get(i++);
			ISignature signature = getISignatureFromResultadoValidacionAndXMLSignature(validationResult, xmlSignature);
			if (signature != null)
				signatures.add(signature);
			//
			// Certificate certificate =
			// validationResult.getDatosFirma().getCadenaFirma().getCertificates().get(0);
			// if (certificate instanceof X509Certificate) {
			// DOMValidateContext context = new
			// DOMValidateContext(certificate.getPublicKey(), doc);
			// XaDESSignature xadesSignature = new XaDESSignature(xmlSignature,
			// context, (X509Certificate)certificate);
			// signatures.add(xadesSignature);
			// }
		}
		return signatures;
	}

	public boolean canContentBeSigned() {
		return false;
	}

	public Collection<CRL> getEmbeddedCRLs(File file) {
		// TODO Auto-generated method stub
		return null;
	}

	public Collection<? extends Certificate> getEmbeddedCertificates(File file) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<byte[], TimeStampToken> getMapSignatureTimeStampTokens(File file) {
		// TODO Auto-generated method stub
		return null;
	}

	// public static void main(String[] args) {
	//
	// // File fileTest = new
	// // File("C:/Users/Administrator/Desktop/IT01234567890_X1111.xml");
	// // File fileTest = new
	// // File("C:/Users/Administrator/Desktop/xmlfo/IT00967720285_00001_RC_004.xml");
	// File fileTest = new File("C:/Users/Administrator/Desktop/xmlfo/IT00967720285_00001_RC_004Mod.xml");
	//
	// XMLSigner signer = new XMLSigner();
	// boolean test = signer.isSignedType(fileTest);
	// System.out.println("E' una firma xml? " + test);
	//
	// List<ISignature> signs = signer.getSignatures();
	// for (int i = 0; i < signs.size(); i++) {
	// ISignature sign = signs.get(i);
	// if (sign.getSignerBean() != null && sign.getSignerBean().getSubject() != null)
	// System.out.println(sign.getSignerBean().getSubject().getName());
	// if (sign.getSignerBean() != null && sign.getSignerBean().getCertificate() != null) {
	// System.out.println(sign.getSignerBean().getCertificate().getNotBefore());
	// System.out.println(sign.getSignerBean().getCertificate().getNotAfter());
	// }
	// System.out.println(sign.verify().getErrorsString());
	//
	// }
	// }

}
