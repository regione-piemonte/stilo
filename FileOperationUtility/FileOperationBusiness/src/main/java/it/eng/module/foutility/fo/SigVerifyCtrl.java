/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.NoSuchProviderException;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.activation.MimeType;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.bouncycastle.asn1.x500.RDN;
import org.bouncycastle.asn1.x500.X500Name;
import org.bouncycastle.asn1.x500.style.BCStyle;
import org.bouncycastle.asn1.x500.style.IETFUtils;
import org.bouncycastle.cert.jcajce.JcaX509CertificateHolder;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.context.ApplicationContext;

import it.eng.hsm.client.Hsm;
import it.eng.hsm.client.bean.sign.SignVerifyResponseBean;
import it.eng.hsm.client.bean.sign.SignatureBean;
import it.eng.hsm.client.config.HsmConfig;
import it.eng.hsm.client.config.HsmType;
import it.eng.hsm.client.config.ItagileConfig;
import it.eng.hsm.client.config.WSConfig;
import it.eng.hsm.client.exception.HsmClientConfigException;
import it.eng.hsm.client.exception.HsmClientSignatureVerifyException;
import it.eng.hsm.client.impl.HsmItagile;
import it.eng.module.foutility.beans.LivelloFirma;
import it.eng.module.foutility.beans.OutputOperations;
import it.eng.module.foutility.beans.ProviderFirmaBean;
import it.eng.module.foutility.beans.SbustamentoBean;
import it.eng.module.foutility.beans.generated.AbstractInputOperationType;
import it.eng.module.foutility.beans.generated.AbstractResponseOperationType;
import it.eng.module.foutility.beans.generated.DnType;
import it.eng.module.foutility.beans.generated.InputSigVerifyType;
import it.eng.module.foutility.beans.generated.MessageType;
import it.eng.module.foutility.beans.generated.ResponseSigVerify;
import it.eng.module.foutility.beans.generated.SigVerifyResultType;
import it.eng.module.foutility.beans.generated.SignerInformationType;
import it.eng.module.foutility.beans.generated.VerificationStatusType;
import it.eng.module.foutility.beans.generated.SigVerifyResultType.SigVerifyResult;
import it.eng.module.foutility.beans.generated.SigVerifyResultType.SigVerifyResult.SignerInformations;
import it.eng.module.foutility.beans.generated.SignerInformationType.Certificato;
import it.eng.module.foutility.beans.generated.SignerInformationType.Marca;
import it.eng.module.foutility.util.CertificateUtil;
import it.eng.module.foutility.util.CheckPdfCommenti;
import it.eng.module.foutility.util.CheckPdfEditabili;
import it.eng.module.foutility.util.FileOpMessage;
import it.eng.module.foutility.util.FileoperationContextProvider;
import it.eng.module.foutility.util.InputFileUtil;
import it.eng.suiteutility.mimedetector.MimeDetectorException;
import it.eng.suiteutility.mimedetector.implementations.mimeutils.MimeUtilsAdapter;
import it.eng.utility.cryptosigner.controller.bean.ContentBean;
import it.eng.utility.cryptosigner.controller.bean.ErrorBean;
import it.eng.utility.cryptosigner.controller.bean.OutputSignerBean;
import it.eng.utility.cryptosigner.controller.bean.ValidationInfos;
import it.eng.utility.cryptosigner.controller.impl.cert.CertificateReliability;
import it.eng.utility.cryptosigner.controller.impl.cert.CertificateRevocation;
import it.eng.utility.cryptosigner.controller.impl.signature.CodeDetectionValidation;
import it.eng.utility.cryptosigner.data.AbstractSigner;
import it.eng.utility.cryptosigner.data.SignerUtil;
import it.eng.utility.cryptosigner.data.type.SignerType;
import it.eng.utility.cryptosigner.exception.CryptoSignerException;
import it.eng.utility.cryptosigner.exception.NoSignerException;
import it.eng.utility.cryptosigner.manager.SignatureManager;
import it.eng.utility.cryptosigner.utils.MessageHelper;

public class SigVerifyCtrl extends AbstractFileController {

	private static Logger log = LogManager.getLogger(SigVerifyCtrl.class);

	public String operationType;
	private AbstractSigner signer = null;
	private String tipoFirma = null;
	private String tipoFirmaLivello1 = null;
	private String tipoFirmaLivello2 = null;
	private String endpoint;
	private String servicens;
	private String servicename;

	private static final String SIG_VERIFY_RESULT_BEAN = "SIG_VERIFY_RESULT_BEAN";
	
	// Rapporto verifica
	public static String RAPPORTO_VERIFICA_SigVerifyResult = "RAPPORTO_VERIFICA_SIGVERIFYRESULT";

	@Override
	public boolean execute(InputFileBean input, AbstractInputOperationType customInput, OutputOperations output, String requestKey) {

		ApplicationContext context = FileoperationContextProvider.getApplicationContext();
		try {
			ProviderFirmaBean providerFirma = (ProviderFirmaBean) context.getBean("ProviderFirma");
			if (providerFirma != null && "RER".equalsIgnoreCase(providerFirma.getTipoProvider())) {
				endpoint = providerFirma.getEndpoint();
				servicens = providerFirma.getServicens();
				servicename = providerFirma.getServicename();
				log.debug("Configurazione ITAgile - endpoint " + endpoint + ", serviceNS " + servicens + ", serviceName "+ servicename );
				return sigVerifyCtrlITAgile(input, customInput, output, requestKey);
			} else {
				return sigVerifyCtrl(input, customInput, output, requestKey);
			}
		} catch (NoSuchBeanDefinitionException e) {
			//log.error(e);
			return sigVerifyCtrl(input, customInput, output, requestKey);
		}
	}
	
	private boolean sigVerifyCtrl(InputFileBean input, AbstractInputOperationType customInput,
			OutputOperations output, String requestKey) {
		
		boolean ret = false;
		ResponseSigVerify response = new ResponseSigVerify();
		response.setSigVerifyResult(new SigVerifyResultType());

		File documentFile = input.getInputFile();
		log.debug(requestKey + " - Metodo execute di SigVerifyCtrl sul file " + documentFile);

		ApplicationContext context = FileoperationContextProvider.getApplicationContext();
		SignatureManager manager = (SignatureManager) context.getBean("SignatureManager");

		// imposta i controlli in base a quelli richiesti
		setMasterCheck(manager, customInput, output, input, requestKey);

		boolean childValidation = true;
		boolean tsaReliability = false;
		Date dateReference = new Date();
		if (customInput != null && customInput instanceof InputSigVerifyType) {
			InputSigVerifyType customSigVerifyInput = (InputSigVerifyType) customInput;
			XMLGregorianCalendar xmlgDate = customSigVerifyInput.getDataRif();
			if (xmlgDate != null) {
				dateReference = xmlgDate.toGregorianCalendar().getTime();
			}
			childValidation = customSigVerifyInput.isChildValidation();
			if( customSigVerifyInput!=null )
				tsaReliability =  ((InputSigVerifyType) customInput).getTimestampVerifiy().isTSAReliability();
		}
		log.debug("E' richiesta la validazione delle firme interne? " + childValidation);
		log.debug("Data di riferimento della verifica: " + dateReference);

		OutputSignerBean bean = null;
		try {
			String fileName = InputFileUtil.getTempFileName(input);
			if (input.getTimestamp() != null) {
				bean = manager.executeEmbedded(documentFile, input.getTimestamp(), fileName, dateReference);
			} else {
				bean = manager.executeEmbedded(documentFile, fileName, dateReference);
			}

			if (bean == null) {
				// file di firma non riconosciuto
				// TODO distinguere fra file non firmato e busta non riconosciuta!!
				log.info("formato firma non riconosciuto");
				MessageType message = new MessageType();
				message.setCode(FileOpMessage.SIGN_OP_FILENOTSIGNED);
				message.setDescription(MessageHelper.getMessage(FileOpMessage.SIGN_OP_FILENOTSIGNED));
				response.getSigVerifyResult().setMessage(message);
				output.addResult(this.getClass().getName(), response);
				return false;
			}
			// verifico se si è generata una eccezione bloccante
			if (bean.getProperty(OutputSignerBean.MASTER_SIGNER_EXCEPTION_PROPERTY) != null) {
				OutputOperations.addError(response, FileOpMessage.SIGN_OP_GENERIC_ERROR, VerificationStatusType.ERROR);
				output.addResult(this.getClass().getName(), response);
				return false;
			}
			// imposto il risultato come bean
			output.addProperty(SIG_VERIFY_RESULT_BEAN, bean);
			ret = true;

			SigVerifyResultType verifyResult = new SigVerifyResultType();

			SigVerifyResultType current = verifyResult;
			boolean firmaInterna = false;
			while (bean != null) {
				OutputSignerBeanConverter.populateVerifyResult(response, current, bean, firmaInterna, childValidation, tsaReliability);
				if (bean.getContent() != null && bean.getContent().getSigner() != null) {
					//bean.getContent().getSigner().closeFileStream();
				}
				bean = bean.getChild();
				if (bean != null) {
					firmaInterna = true;
					SigVerifyResultType child = new SigVerifyResultType();
					if (current.getSigVerifyResult() != null)
						current.getSigVerifyResult().setChild(child);
					current = child;
				}

			}
			output.addProperty(RAPPORTO_VERIFICA_SigVerifyResult, verifyResult);
			response.setSigVerifyResult(verifyResult);

		} catch (CryptoSignerException csi) {
			log.fatal("fatal sigverification ", csi);
			// FIXME non dovrebbe accadere!?
			OutputOperations.addError(response, FileOpMessage.SIGN_OP_ERROR, VerificationStatusType.KO, csi.getMessage());
		}
		
		output.addResult(this.getClass().getName(), response);
		return ret;
	}

	/**
	 * imposta i controlli in base a quelli richiesti in input
	 * 
	 * @param master
	 * @param custom
	 */
	private void setMasterCheck(SignatureManager master, AbstractInputOperationType custom, OutputOperations output, 
			InputFileBean inputFileBean, String requestKey) {

		Map<String, Boolean> checks = new HashMap<String, Boolean>();
		if (custom != null && custom instanceof InputSigVerifyType) {
			InputSigVerifyType isv = (InputSigVerifyType) custom;
			if (isv.isRecursive() != null)
				master.setSingleStep(!isv.isRecursive());
			if (isv.isChildValidation() != null)
				master.setChildValidation(isv.isChildValidation());
			else
				master.setChildValidation(false);

			log.debug("Verifica ricorsiva? " + isv.isRecursive());

			// if(isv.getDataRif()!=null){
			// master.setReferenceDate(isv.getDataRif().toGregorianCalendar().getTime());
			// }
			if (isv.getSignatureVerify() != null) {
				log.debug("E' richiesta la verifica delle CA? " + isv.getSignatureVerify().isCAReliability());
				log.debug("E' richiesta la verifica delle CRL? " + isv.getSignatureVerify().isCRLCheck());
				checks.put(CertificateReliability.CERTIFICATE_RELIABILITY_CHECK, isv.getSignatureVerify().isCAReliability());
				checks.put(CertificateRevocation.CERTIFICATE_REVOCATION_CHECK, isv.getSignatureVerify().isCRLCheck());
				// verifico se è richiesto il code detection
				log.debug("E' richiesta la verifica del codice eseguibile? " + isv.getSignatureVerify().isDetectCode());
				if (isv.getSignatureVerify().isDetectCode()) {
					// recupero il mimetype da un controllo precedente
					MimeType mimetype = output.getPropOfType(FormatRecognitionCtrl.DETECTED_MIME, MimeType.class);
					// log.debug("mimetype: " + mimetype);
					if (mimetype == null) {
						FormatRecognitionCtrl formatRecognitionCtrl = FileoperationContextProvider.getApplicationContext().getBean("FormatRecognitionCtrl",
								FormatRecognitionCtrl.class);
						UnpackCtrl unpackCtrl = FileoperationContextProvider.getApplicationContext().getBean("UnpackCtrl", UnpackCtrl.class);
						try {
							File fileSbustato = null;
							String extractedFileName = null;
							String fileName = null;
							try {
								fileName = InputFileUtil.getTempFileName(inputFileBean);
								log.debug("Nome file di input " + fileName);
								SbustamentoBean sbustamentoFileBean = unpackCtrl.extractDocumentFile(inputFileBean.getInputFile(), 
										inputFileBean.getInputFile().getParentFile(),
										fileName, true, requestKey, null);
								fileSbustato = sbustamentoFileBean.getExtracttempfile();
								log.debug("File sbustato: " + fileSbustato);
								if (!StringUtils.isBlank(fileName)) {
									// extractedFileName = unpackCtrl.rename( fileSbustato, fileName, unpackCtrl.getFormatoBusta());
									extractedFileName = sbustamentoFileBean.getNomeFileSbustato();//unpackCtrl.getNomeFileSbustato();
									log.debug("extractedFileName " + extractedFileName);
								}

							} catch (IOException e) {
								log.error("Eccezione setMasterCheck", e);
							} catch (Exception e) {
								log.error("Eccezione setMasterCheck", e);
							}

							if (fileSbustato != null) {
								MimeUtilsAdapter adapter = new MimeUtilsAdapter();
								if (extractedFileName != null) {
									String estensioneFile = FilenameUtils.getExtension(extractedFileName);
									log.debug("estensioneFile " + estensioneFile);
									if (estensioneFile != null && !estensioneFile.equalsIgnoreCase(""))
										mimetype = formatRecognitionCtrl.getMimeTypeExt(fileSbustato, estensioneFile, requestKey, adapter);
									else
										mimetype = formatRecognitionCtrl.getBestMimeType(fileSbustato, adapter);
								} else
									mimetype = formatRecognitionCtrl.getBestMimeType(fileSbustato, adapter);

							} else {
								MimeUtilsAdapter adapter = new MimeUtilsAdapter();
								if (fileName != null) {
									String estensioneFile = FilenameUtils.getExtension(fileName);
									log.debug("estensioneFile " + estensioneFile);
									if (estensioneFile != null && !estensioneFile.equalsIgnoreCase(""))
										mimetype = formatRecognitionCtrl.getMimeTypeExt(inputFileBean.getInputFile(), estensioneFile, requestKey, adapter);
									else
										mimetype = formatRecognitionCtrl.getBestMimeType(inputFileBean.getInputFile(), adapter);
								} else
									mimetype = formatRecognitionCtrl.getBestMimeType(inputFileBean.getInputFile(), adapter);
							}
							log.debug("mimetype: " + mimetype);
						} catch (MimeDetectorException e) {
							log.error("Eccezione setMasterCheck", e);
						}
					}
					CodeDetectionValidation ctrl = new CodeDetectionValidation();
					ctrl.setMimeTypeInput(mimetype);
					checks.put(CodeDetectionValidation.CODE_DETECTOR_CHECK, true);
					
					boolean isVerificaPdfCommentiAttiva = CheckPdfCommenti.isAttivaVerificaPdfCommenti();
					log.debug(requestKey + " - Verifica pdf con commenti attiva? " + isVerificaPdfCommentiAttiva);
					checks.put(CodeDetectionValidation.CODE_DETECTOR_PDF_COMMENTI_CHECK, isVerificaPdfCommentiAttiva);
					
					boolean isVerificaPdfEditabiliAttiva = CheckPdfEditabili.isAttivaVerificaPdfEditable();
					log.debug(requestKey + " - Verifica pdf editabili attiva? " + isVerificaPdfEditabiliAttiva);
					checks.put(CodeDetectionValidation.CODE_DETECTOR_PDF_EDITABILI_CHECK, isVerificaPdfEditabiliAttiva);
					
					master.getMasterSignerController().getControllers().add(ctrl);
				}
				log.debug("Aggiungo i checks " + checks);
				master.getMasterSignerController().getChecks().putAll(checks);
			}
			//if (isv.getTimestampVerifiy() != null) {
				log.debug("E' richiesta la verifica del timeStamp? " + isv.getTimestampVerifiy().isTSAReliability());
				checks.put("performTSAReliability", isv.getTimestampVerifiy().isTSAReliability());
				checks.put("performTSARevocation", isv.getTimestampVerifiy().isTSAReliability());
				//log.debug("Aggiungo i checks " + checks);
				master.getMasterTimeStampController().getChecks().putAll(checks);
			//}
		}
	}

	@Override
	public String getOperationType() {
		return operationType;
	}

	public void setOperationType(String operationType) {
		this.operationType = operationType;
	}
	
	/**
	 * Verifica firma per RER
	 */
	private boolean sigVerifyCtrlITAgile(InputFileBean input, AbstractInputOperationType customInput,
			OutputOperations output, String requestKey) {

		boolean ret = false;
		ResponseSigVerify response = new ResponseSigVerify();
		response.setSigVerifyResult(new SigVerifyResultType());

		File fileFirmatoInput = input.getInputFile();
		log.debug("Metodo execute di SigVerifyCtrl-RER sul file " + fileFirmatoInput);

		ApplicationContext context = FileoperationContextProvider.getApplicationContext();
		SignatureManager manager = (SignatureManager) context.getBean("SignatureManager");

		// imposta i controlli in base a quelli richiesti
		setMasterCheck(manager, customInput, output, input, requestKey);

		boolean childValidation = true;
		boolean isFirmaRicorsiva = false;
		Date dateReference = new Date();
		if (customInput != null && customInput instanceof InputSigVerifyType) {
			XMLGregorianCalendar xmlgDate = ((InputSigVerifyType) customInput).getDataRif();
			if (xmlgDate != null) {
				dateReference = xmlgDate.toGregorianCalendar().getTime();
			}
			childValidation = ((InputSigVerifyType) customInput).isChildValidation();
			isFirmaRicorsiva = ((InputSigVerifyType) customInput).isRecursive();
		}
		log.debug("E' richiesta la validazione delle firme interne? " + childValidation);
		log.debug("Data di riferimento della verifica: " + dateReference);

		OutputSignerBean outputSignerBean = new OutputSignerBean();
		InputStream contentstream = null;
		try {
			signer = SignerUtil.newInstance().getSignerManager(fileFirmatoInput);
			if (signer.getFormat().equals(SignerType.PAdES)) {
				tipoFirma = "PAdES";
			} else if (signer.getFormat().name().startsWith("CAdES")) {
				tipoFirma = "CAdES";
			} else if (signer.getFormat().name().startsWith("XAdES")) {
				tipoFirma = "XAdES";
			}
			try {
				contentstream = signer.getContentAsInputStream(fileFirmatoInput);
			} catch (IOException e) {
				log.error(e);
			}
		} catch (NoSignerException e) {
			// Se arriva qua non e' stato trovato alcun signer per cui o il file non è firmato o
			// è firmato in maniera ignota al cryptosigner
			log.warn("Il file " + fileFirmatoInput + " non e' firmato");
		}
		
		try {
			
			Hsm hsmITAgile = callItagileWsdl();
			SignVerifyResponseBean signVerifyResponseBean = verificaFirmaITAgile(hsmITAgile,fileFirmatoInput);//, isFirmaRicorsiva);

			if (tipoFirma==null || signVerifyResponseBean == null) {
				log.info("formato firma non riconosciuto");
				MessageType message = new MessageType();
				message.setCode(FileOpMessage.SIGN_OP_FILENOTSIGNED);
				message.setDescription(MessageHelper.getMessage(FileOpMessage.SIGN_OP_FILENOTSIGNED));
				response.getSigVerifyResult().setMessage(message);
				output.addResult(this.getClass().getName(), response);

				return false;
			}
			
			ContentBean contentBean = new ContentBean();
			contentBean.setSigner(signer);
			contentBean.setPossiblySigned(signer.canContentBeSigned());
			contentBean.setContentStream(contentstream);
			outputSignerBean.setContent(contentBean);
					
			String format = signer.getFormat().toString();
			// Setto il formato della busta
			output.addProperty(OutputSignerBean.ENVELOPE_FORMAT_PROPERTY, format);
			output.addProperty(SIG_VERIFY_RESULT_BEAN, outputSignerBean);
			ret = true;
			
			// Recupero la response del wsdl di Itagile
		/*	List<SigVerifyResultType> listaSigVerifyResultType = new ArrayList<SigVerifyResultType>();
			List<SigVerifyResultType> listaSigVerifyResultTypeInterna = new ArrayList<SigVerifyResultType>();
			List<SigVerifyResultType> listaSigVerifyResultTypeControfirme = new ArrayList<SigVerifyResultType>();
			List<SigVerifyResultType> listaSigVerifyResultTypeInterna2 = new ArrayList<SigVerifyResultType>();
			List<SigVerifyResultType> listaSigVerifyResultTypeControfirmeInterne = new ArrayList<SigVerifyResultType>();*/
			
			Map<Integer, LivelloFirma> livelliFirma = new HashMap<Integer, LivelloFirma>();
			for(int i=0; i < signVerifyResponseBean.getSignatureBean().size(); i++) {
			
				SignatureBean signatureBean = signVerifyResponseBean.getSignatureBean().get(i);
				
				elaboraRisultatiItAgile(signatureBean, livelliFirma);//, isFirmaRicorsiva);
				/*
				 * Verifica se firma interna con nrLivello = 1
				 */
				/*log.debug(" signatureBean.getNrLivello() " +  signatureBean.getNrLivello());
				if(signatureBean.getNrLivello() == 0) {
					log.debug("Firma esterna");
					log.debug("---- signatureBean.getP7mLevel() " + signatureBean.getP7mLevel() + " " + signatureBean.getCertificato().getCognomeFirmatario());
					if( signatureBean.getCertificato().getCognomeFirmatario()!=null && !signatureBean.getCertificato().getCognomeFirmatario().equalsIgnoreCase("")){
						if( signatureBean.getP7mLevel()>0 && signatureBean.getTipo()!=null && signatureBean.getTipo().equalsIgnoreCase("CADES")){
							log.debug("aggiungo una controfirma esterna");
							populateSignInfoITAgile(signatureBean, listaSigVerifyResultTypeControfirme);
						}
						else { 
							log.debug("aggiungo una firma esterna");
							populateSignInfoITAgile(signatureBean, listaSigVerifyResultType);
						}
					}
				} else if(signatureBean.getNrLivello() == 1) {
					log.debug("Firma interna");
					log.debug("---- signatureBean.getP7mLevel() " + signatureBean.getP7mLevel() + " " + signatureBean.getCertificato().getCognomeFirmatario());
					if( signatureBean.getCertificato().getCognomeFirmatario()!=null && !signatureBean.getCertificato().getCognomeFirmatario().equalsIgnoreCase("")){
						tipoFirmaLivello1 = signatureBean.getTipoFirma();
						if( signatureBean.getP7mLevel()>0 && signatureBean.getTipo()!=null && signatureBean.getTipo().equalsIgnoreCase("CADES"))
							populateSignInfoInternaITAgile(signatureBean, listaSigVerifyResultTypeControfirmeInterne);
						else
							populateSignInfoInternaITAgile(signatureBean, listaSigVerifyResultTypeInterna);
					}
				} else if(signatureBean.getNrLivello() == 2) {
					log.debug("Firma interna livello 2");
					log.debug("---- signatureBean.getP7mLevel() " + signatureBean.getP7mLevel() + " " + signatureBean.getCertificato().getCognomeFirmatario());
					if( signatureBean.getCertificato().getCognomeFirmatario()!=null && !signatureBean.getCertificato().getCognomeFirmatario().equalsIgnoreCase("")){
						tipoFirmaLivello2 = signatureBean.getTipoFirma();
						//if( signatureBean.getP7mLevel()>0 && signatureBean.getTipo()!=null && signatureBean.getTipo().equalsIgnoreCase("CADES"))
						//	populateSignInfoInternaITAgile(signatureBean, listaSigVerifyResultTypeControfirmeInterne);
						//else
							populateSignInfoInternaITAgile(signatureBean, listaSigVerifyResultTypeInterna2);
					}
				}*/
			}
			
			log.debug("mappa " + livelliFirma );
			
			/*int index = 1;
			
			List<SigVerifyResultType> listaSigVerifyResultTypeControfirmeCopia = new ArrayList<SigVerifyResultType>();
			for(SigVerifyResultType controFirma : listaSigVerifyResultTypeControfirme){
				listaSigVerifyResultTypeControfirmeCopia.add(controFirma); 
			}
			
			for(SigVerifyResultType firmaInterna : listaSigVerifyResultTypeInterna ){
				log.debug("Firma Interna num " + index + " "+firmaInterna);
				log.debug("listaSigVerifyResultTypeControfirme " + listaSigVerifyResultTypeControfirme);
				
				SignerInformationType firmaInternaSig = firmaInterna.getSigVerifyResult().getSignerInformations().getSignerInformation().get(0);
				
				for(SigVerifyResultType controFirma : listaSigVerifyResultTypeControfirmeCopia){
					SignerInformationType controFirmaSig = controFirma.getSigVerifyResult().getSignerInformations().getSignerInformation().get(0);
					XMLGregorianCalendar sigTimeControFirma = controFirmaSig.getSigningTime();
					if( sigTimeControFirma.equals( firmaInternaSig.getSigningTime() ) ){
						log.debug("Rimuovo");
						listaSigVerifyResultTypeControfirme.remove(controFirma);
					}
				}
				index++;
			}*/
			Iterator<Integer> itrLivelloFirma = livelliFirma.keySet().iterator();
			while(itrLivelloFirma.hasNext()){
				Integer numeroLivelloFirmaCorrente = itrLivelloFirma.next();
				LivelloFirma livelloFirmaCorrente = livelliFirma.get(numeroLivelloFirmaCorrente);
				
				rimuoviControfirmeErrate(numeroLivelloFirmaCorrente, livelloFirmaCorrente, livelliFirma);
			}
			
			log.debug("mappa " + livelliFirma );
			
			
			/*if(listaSigVerifyResultType!=null )
				log.debug("Numero di firme: " + listaSigVerifyResultType.size() );
			if(listaSigVerifyResultTypeControfirme!=null )
				log.debug("Numero di controfirme: " + listaSigVerifyResultTypeControfirme.size() );
			if(listaSigVerifyResultTypeInterna!=null )
				log.debug("Numero di firme interne: " + listaSigVerifyResultTypeInterna.size() );
			if(listaSigVerifyResultTypeControfirmeInterne!=null )
				log.debug("Numero di controfirme interne: " + listaSigVerifyResultTypeControfirmeInterne.size() );
			if(listaSigVerifyResultTypeInterna2!=null )
				log.debug("Numero di firme interne livello 2: " + listaSigVerifyResultTypeInterna2.size() );*/
			
			
			SigVerifyResultType current = new SigVerifyResultType();
			//SigVerifyResultType currentInterna = new SigVerifyResultType();
			//SigVerifyResultType currentInterna2 = new SigVerifyResultType();
			boolean firmaInterna = false;
			while (outputSignerBean != null) {
				//OutputSignerBeanConverterITAgile.populateVerifyResult(response, signer, listaSigVerifyResultType, listaSigVerifyResultTypeControfirme, current, 
				//		outputSignerBean, firmaInterna, childValidation, tipoFirma);
				// Quando la lista degli listaSigVerifyResultTypeInterna è piena firma interna = true
				
				itrLivelloFirma = livelliFirma.keySet().iterator();
				List<SigVerifyResultType> listaCurrent = new ArrayList<SigVerifyResultType>();
				if( isFirmaRicorsiva ){
					while(itrLivelloFirma.hasNext()){
						Integer numeroLivelloFirmaCorrente = itrLivelloFirma.next();
						log.debug("numeroLivelloFirmaCorrente " + numeroLivelloFirmaCorrente);
						LivelloFirma livelloFirmaCorrente = livelliFirma.get(numeroLivelloFirmaCorrente);
						log.debug("livelloFirmaCorrente " + livelloFirmaCorrente);
						
						if( numeroLivelloFirmaCorrente>=1 ){
							firmaInterna = true;
							SigVerifyResultType currentInterna = new SigVerifyResultType();
							listaCurrent.add(currentInterna);
							OutputSignerBeanConverterITAgile.populateVerifyResult(response, signer, 
									livelloFirmaCorrente.getListaSigVerifyResultType(), 
									livelloFirmaCorrente.getListaSigVerifyResultTypeControfirme(), 
									listaCurrent.get(numeroLivelloFirmaCorrente), outputSignerBean, firmaInterna, childValidation, livelloFirmaCorrente.getTipoFirma());
							listaCurrent.get(numeroLivelloFirmaCorrente-1).getSigVerifyResult().setChild(listaCurrent.get(numeroLivelloFirmaCorrente));
							
						} else {
							listaCurrent.add(current);
							OutputSignerBeanConverterITAgile.populateVerifyResult(response, signer, livelloFirmaCorrente.getListaSigVerifyResultType(), 
								livelloFirmaCorrente.getListaSigVerifyResultTypeControfirme(), current, 
								outputSignerBean, firmaInterna, childValidation, livelloFirmaCorrente.getTipoFirma() );
						}
						log.debug(listaCurrent);
						
							
						// Quando la lista degli listaSigVerifyResultTypeInterna è piena firma interna = true
					}
				} else {
					Integer numeroLivelloFirmaCorrente = itrLivelloFirma.next();
					log.debug("numeroLivelloFirmaCorrente " + numeroLivelloFirmaCorrente);
					LivelloFirma livelloFirmaCorrente = livelliFirma.get(numeroLivelloFirmaCorrente);
					log.debug("livelloFirmaCorrente " + livelloFirmaCorrente);
					
					listaCurrent.add(current);
					OutputSignerBeanConverterITAgile.populateVerifyResult(response, signer, livelloFirmaCorrente.getListaSigVerifyResultType(), 
							livelloFirmaCorrente.getListaSigVerifyResultTypeControfirme(), current, 
							outputSignerBean, firmaInterna, childValidation, livelloFirmaCorrente.getTipoFirma() );
					log.debug(listaCurrent);
				}
				
				/*if(listaSigVerifyResultTypeInterna != null && !listaSigVerifyResultTypeInterna.isEmpty()) {
					firmaInterna = true;
					OutputSignerBeanConverterITAgile.populateVerifyResult(response, signer, listaSigVerifyResultTypeInterna, listaSigVerifyResultTypeControfirmeInterne, currentInterna, outputSignerBean, firmaInterna, childValidation, tipoFirmaLivello1);
					current.getSigVerifyResult().setChild(currentInterna);
				}
				if(listaSigVerifyResultTypeInterna2 != null && !listaSigVerifyResultTypeInterna2.isEmpty()) {
					firmaInterna = true;
					OutputSignerBeanConverterITAgile.populateVerifyResult(response, signer, listaSigVerifyResultTypeInterna2, listaSigVerifyResultTypeControfirmeInterne, currentInterna2, outputSignerBean.getChild(), firmaInterna, childValidation, tipoFirmaLivello2);
					currentInterna.getSigVerifyResult().setChild(currentInterna2);
				}*/
				if (outputSignerBean.getContent() != null && outputSignerBean.getContent().getSigner() != null) {
					//outputSignerBean.getContent().getSigner().closeFileStream();
				}
				outputSignerBean = null;
			}
			
			response.setSigVerifyResult(current);

		} catch (Exception ex) {
			log.fatal("fatal sigverification ", ex);
			OutputOperations.addError(response, FileOpMessage.SIGN_OP_ERROR, VerificationStatusType.KO,
					ex.getMessage());
		}

		output.addResult(this.getClass().getName(), response);
		return ret;
	}
	
	private void elaboraRisultatiItAgile(SignatureBean signatureBean, Map<Integer, LivelloFirma> livelliFirma/*, boolean isRicorsiva*/){

		String cnCert=null;
		if(signatureBean.getCertificato()!=null){
			byte[] certBytes = signatureBean.getCertificato().getX509();
			try {
				byte[] byteResult = Base64.decodeBase64(certBytes);
				X509Certificate x509Certificato = (X509Certificate) CertificateFactory.getInstance("X.509").generateCertificate(new ByteArrayInputStream(byteResult));
				X500Name x500name = new JcaX509CertificateHolder(x509Certificato).getSubject();
				if (x500name != null) {
					Map<String, String> x509NameSubject = CertificateUtil.getX509Name(x509Certificato, "Subject");
					RDN[] cns = x500name.getRDNs(BCStyle.CN);
					if (cns != null && cns.length > 0) {
						RDN cn = cns[0];
						if (cn != null && cn.getFirst() != null) {
							cnCert=IETFUtils.valueToString(cn.getFirst().getValue());
						} else {
							cnCert=x509NameSubject.get("CN");
						}
					}
				}
			} catch (CertificateException e) {
				log.error("Errore nel parsing del certificato ", e);
			}
			
				
		}
		log.debug("cnCert "+cnCert);
		log.debug(" signatureBean.getNrLivello() " +  signatureBean.getNrLivello() + " " + signatureBean.getCertificato().getCognomeFirmatario() );
		if( cnCert!=null || (signatureBean.getCertificato().getCognomeFirmatario()!=null && !signatureBean.getCertificato().getCognomeFirmatario().equalsIgnoreCase(""))){
			
			LivelloFirma livelloFirma = null;
			if( livelliFirma.containsKey(signatureBean.getNrLivello())){
				livelloFirma = livelliFirma.get( signatureBean.getNrLivello() );
				livelloFirma.setTipoFirma( signatureBean.getTipoFirma() );
				//if( isRicorsiva ){
					if( signatureBean.getP7mLevel()>0 && signatureBean.getTipo()!=null && signatureBean.getTipo().equalsIgnoreCase("CADES")){
						log.debug("aggiungo una controfirma ");
						populateSignInfoITAgile(cnCert,signatureBean, livelloFirma.getListaSigVerifyResultTypeControfirme());
					}
					else { 
						log.debug("aggiungo una firma ");
						populateSignInfoITAgile(cnCert,signatureBean, livelloFirma.getListaSigVerifyResultType());
					}
				/*} else {
					if( signatureBean.getTipo().equalsIgnoreCase("CADES")){
						if(signatureBean.getP7mLevel()==0  ){
							log.debug("aggiungo una firma ma solo di livello 0 ");
							populateSignInfoITAgile(signatureBean, livelloFirma.getListaSigVerifyResultType());
						} else {
							log.debug("aggiungo una controfirma ");
							populateSignInfoITAgile(signatureBean, livelloFirma.getListaSigVerifyResultTypeControfirme());
						}
					} 
					else {
						log.debug("aggiungo una firma ");
						populateSignInfoITAgile(signatureBean, livelloFirma.getListaSigVerifyResultType());
					} 
					
				}*/
				
			} else {
				livelloFirma = new LivelloFirma();
				livelloFirma.setTipoFirma( signatureBean.getTipoFirma() );
				//if( isRicorsiva ){
					if( signatureBean.getP7mLevel()>0 && signatureBean.getTipo()!=null && signatureBean.getTipo().equalsIgnoreCase("CADES")){
						log.debug("aggiungo una controfirma ");
						populateSignInfoITAgile(cnCert, signatureBean, livelloFirma.getListaSigVerifyResultTypeControfirme());
					}
					else { 
						log.debug("aggiungo una firma ");
						populateSignInfoITAgile(cnCert, signatureBean, livelloFirma.getListaSigVerifyResultType());
					}
				/*} else {
					if( signatureBean.getTipo().equalsIgnoreCase("CADES")){
						if( signatureBean.getP7mLevel()==0 ){
							log.debug("aggiungo una firma ma solo di livello 0 ");
							populateSignInfoITAgile(signatureBean, livelloFirma.getListaSigVerifyResultType());
						}else {
							log.debug("aggiungo una controfirma ");
							populateSignInfoITAgile(signatureBean, livelloFirma.getListaSigVerifyResultTypeControfirme());
						}
					}
					else {
						log.debug("aggiungo una firma ");
						populateSignInfoITAgile(signatureBean, livelloFirma.getListaSigVerifyResultType());
					} 
				}*/
				
				livelliFirma.put( signatureBean.getNrLivello(),  livelloFirma );
			}
		}
	}
	
	private void rimuoviControfirmeErrate(int numeroLivelloFirma, LivelloFirma livelloFirma, Map<Integer, LivelloFirma> livelliFirma){
		List<SigVerifyResultType> listaSigVerifyResultTypeControfirmeCopia = new ArrayList<SigVerifyResultType>();
		for(SigVerifyResultType controFirma : livelloFirma.getListaSigVerifyResultTypeControfirme()){
			listaSigVerifyResultTypeControfirmeCopia.add(controFirma); 
		}
		
		Iterator<Integer> itrLivelloFirma = livelliFirma.keySet().iterator();
		while(itrLivelloFirma.hasNext()){
			Integer numeroLivelloFirmaCorrente = itrLivelloFirma.next();
			
			if( numeroLivelloFirmaCorrente > numeroLivelloFirma){
				int index = 1;
				for(SigVerifyResultType firmaInterna : livelliFirma.get( numeroLivelloFirmaCorrente ).getListaSigVerifyResultType() ){
					log.debug("Firma Interna num " + index + " "+firmaInterna);
					
					SignerInformationType firmaInternaSig = firmaInterna.getSigVerifyResult().getSignerInformations().getSignerInformation().get(0);
					
					for(SigVerifyResultType controFirma : listaSigVerifyResultTypeControfirmeCopia){
						SignerInformationType controFirmaSig = controFirma.getSigVerifyResult().getSignerInformations().getSignerInformation().get(0);
						XMLGregorianCalendar sigTimeControFirma = controFirmaSig.getSigningTime();
						if( sigTimeControFirma.equals( firmaInternaSig.getSigningTime() ) ){
							log.debug("Rimuovo");
							livelloFirma.getListaSigVerifyResultTypeControfirme().remove(controFirma);
						}
					}
					index++;
				}
			}
		}
	}
	
	private void populateSignInfoITAgile(String cnCert,SignatureBean signatureBean, List<SigVerifyResultType> listaSigVerifyResultType) {
		
		SigVerifyResultType lSigVerifyResultType = new SigVerifyResultType();
		SigVerifyResult sigVerifyResult = new SigVerifyResult();
		SignerInformations signerInformations = new SignerInformations();
		SignerInformationType lSignerInformationType = new SignerInformationType();
		List<SignerInformationType> listSignerInformationType = new ArrayList<SignerInformationType>();
										
		/*
		 * CERTIFICATO
		 */
		if(signatureBean.getCertificato() !=  null) {
			Certificato certificato = new Certificato();
			certificato.setContenuto(signatureBean.getCertificato().getX509());
			if(StringUtils.isNotBlank(signatureBean.getCertificato().getTsInizioValidita())) {
				Long longTsInzVld = new Long(signatureBean.getCertificato().getTsInizioValidita());
				Date dtTsInzVld = new Date(longTsInzVld);
				certificato.setDataDecorrenza(getXMLGregorianCalendarDate(dtTsInzVld));
			}
			if(StringUtils.isNotBlank(signatureBean.getCertificato().getTsFineValidita())) {
				Long longTsFineVld = new Long(signatureBean.getCertificato().getTsFineValidita());
				Date dtTsFineVld = new Date(longTsFineVld);
				certificato.setDataScadenza(getXMLGregorianCalendarDate(dtTsFineVld));
			}
			DnType dnType = new DnType();
			dnType.setGivenName(signatureBean.getCertificato().getIdentificativoFirmatario());				
			if( signatureBean.getCertificato().getCognomeFirmatario()!= null && signatureBean.getCertificato().getNomeFirmatario()!=null )
				dnType.setCn(signatureBean.getCertificato().getCognomeFirmatario() + " " + signatureBean.getCertificato().getNomeFirmatario() );
			else
				dnType.setCn(cnCert);
			dnType.setName(signatureBean.getCertificato().getNomeFirmatario());
			dnType.setOu(signatureBean.getCertificato().getUnitaOrganizzativa());
			dnType.setO(signatureBean.getCertificato().getOrganizzazione());
			dnType.setSerialNumber(signatureBean.getCertificato().getSeriale());
												
			// Verifica di expiration sui CERTIFICATI
			AbstractResponseOperationType sigValResultCERT = new AbstractResponseOperationType();
			setValidationCertificateITAgile(signatureBean, sigVerifyResult, sigValResultCERT);
			sigVerifyResult.setCertExpirationResult(sigValResultCERT);//validCert
			
			// Verifica per le CRL( cert revocation list )
			AbstractResponseOperationType sigValResultCRL = new AbstractResponseOperationType();
			setValidationCRLITAgile(signatureBean, sigVerifyResult, sigValResultCRL);
			sigVerifyResult.setCRLResult(sigValResultCRL);//validCert
			
			// Verifica per le CA
			AbstractResponseOperationType sigValResultCA = new AbstractResponseOperationType();
			setValidationCAITAgile(signatureBean, sigVerifyResult, sigValResultCA);
			sigVerifyResult.setCAReliabilityResult(sigValResultCA);//validTrust
			
			certificato.setSerialNumber(signatureBean.getCertificato().getSeriale());
			//certificato.setIssuer(dnType);			
			certificato.setSubject(dnType);
			lSignerInformationType.setCertificato(certificato);
		}
		
		/*
		 * MARCA
		 */
		
		if(signatureBean.getMarca() != null && signatureBean.getMarca().getIsPresente() != null && 
				signatureBean.getMarca().getIsPresente()) {
			Marca marca = new Marca();
			if(StringUtils.isNotBlank(signatureBean.getMarca().getTsLenght())) {
				Long longTsLenght = new Long(signatureBean.getMarca().getTsLenght());
				Date dtTsLenght = new Date(longTsLenght);
				marca.setDate(getXMLGregorianCalendarDate(dtTsLenght));
			}
			if(signatureBean.getMarca().getIsValida() != null && signatureBean.getMarca().getIsValida()) {
				marca.setVerificationStatus(VerificationStatusType.OK);
			} else {
				marca.setVerificationStatus(VerificationStatusType.KO);
			}
			marca.setTsaName(signatureBean.getMarca().getTsAuthority());
			marca.setVerificationStatus(signatureBean.getMarca().getIsValida() != null && signatureBean.getMarca().getIsValida()
					? VerificationStatusType.OK : VerificationStatusType.KO);
			lSignerInformationType.setMarca(marca);
		}
		
		/*
		 * FIRMA
		 */
		if(StringUtils.isNotBlank(signatureBean.getTimestamp())) {
			Long longTimestamp = new Long(signatureBean.getTimestamp());
			Date dtTimestamp = new Date(longTimestamp);
			lSignerInformationType.setSigningTime(getXMLGregorianCalendarDate(dtTimestamp));
		}
		/*
		 *  Verifica FIRMA
		 */
		AbstractResponseOperationType sigValResultSIGN = new AbstractResponseOperationType();
		if(setValidationSignatureITAgile(signatureBean, sigVerifyResult, sigValResultSIGN)) {
			lSignerInformationType.setVerificationStatus(VerificationStatusType.OK);
		} else {
			lSignerInformationType.setVerificationStatus(VerificationStatusType.KO);
		}
		sigVerifyResult.setSignatureValResult(sigValResultSIGN);//validTrust
		
		listSignerInformationType.add(lSignerInformationType);
		
		signerInformations.getSignerInformation().addAll(listSignerInformationType);
		sigVerifyResult.setSignerInformations(signerInformations);
		lSigVerifyResultType.setSigVerifyResult(sigVerifyResult);
		listaSigVerifyResultType.add(lSigVerifyResultType);
	}
	
	private void populateSignInfoInternaITAgile(SignatureBean signatureBean, List<SigVerifyResultType> listaSigVerifyResultTypeInterna) {
		
		SigVerifyResultType lSigVerifyResultTypeInterna = new SigVerifyResultType();
		SigVerifyResult sigVerifyResultInterna = new SigVerifyResult();
		SignerInformations signerInformationsInterna = new SignerInformations();
		SignerInformationType lSignerInformationTypeInterna = new SignerInformationType();
		List<SignerInformationType> listSignerInformationTypeInterna = new ArrayList<SignerInformationType>();
										
		/*
		 * CERTIFICATO
		 */
		if(signatureBean.getCertificato() !=  null) {
			Certificato certificato = new Certificato();
			certificato.setContenuto(signatureBean.getCertificato().getX509());
			if(StringUtils.isNotBlank(signatureBean.getCertificato().getTsInizioValidita())) {
				Long longTsInzVld = new Long(signatureBean.getCertificato().getTsInizioValidita());
				Date dtTsInzVld = new Date(longTsInzVld);
				certificato.setDataDecorrenza(getXMLGregorianCalendarDate(dtTsInzVld));
			}
			if(StringUtils.isNotBlank(signatureBean.getCertificato().getTsFineValidita())) {
				Long longTsFineVld = new Long(signatureBean.getCertificato().getTsFineValidita());
				Date dtTsFineVld = new Date(longTsFineVld);
				certificato.setDataScadenza(getXMLGregorianCalendarDate(dtTsFineVld));
			}
			DnType dnType = new DnType();
			dnType.setGivenName(signatureBean.getCertificato().getIdentificativoFirmatario());				
			dnType.setCn(signatureBean.getCertificato().getCognomeFirmatario()+ " " + signatureBean.getCertificato().getNomeFirmatario() );
			dnType.setName(signatureBean.getCertificato().getNomeFirmatario());
			dnType.setOu(signatureBean.getCertificato().getUnitaOrganizzativa());
			dnType.setO(signatureBean.getCertificato().getOrganizzazione());
			dnType.setSerialNumber(signatureBean.getCertificato().getSeriale());
												
			// Verifica di expiration sui CERTIFICATI
			AbstractResponseOperationType sigValResultCERT = new AbstractResponseOperationType();
			setValidationCertificateITAgile(signatureBean, sigVerifyResultInterna, sigValResultCERT);
			sigVerifyResultInterna.setCertExpirationResult(sigValResultCERT);//validCert
			
			// Verifica per le CRL( cert revocation list )
			AbstractResponseOperationType sigValResultCRL = new AbstractResponseOperationType();
			setValidationCRLITAgile(signatureBean, sigVerifyResultInterna, sigValResultCRL);
			sigVerifyResultInterna.setCRLResult(sigValResultCRL);//validCert
			
			// Verifica per le CA
			AbstractResponseOperationType sigValResultCA = new AbstractResponseOperationType();
			setValidationCAITAgile(signatureBean, sigVerifyResultInterna, sigValResultCA);
			sigVerifyResultInterna.setCAReliabilityResult(sigValResultCA);//validTrust
			
			certificato.setSerialNumber(signatureBean.getCertificato().getSeriale());
			//certificato.setIssuer(dnTvype);			
			certificato.setSubject(dnType);
			lSignerInformationTypeInterna.setCertificato(certificato);
		}
		
		/*
		 * MARCA
		 */					
		if(signatureBean.getMarca() != null && signatureBean.getMarca().getIsPresente() != null && 
				signatureBean.getMarca().getIsPresente()) {
			Marca marca = new Marca();
			if(StringUtils.isNotBlank(signatureBean.getMarca().getTsLenght())) {
				Long longTsLenght = new Long(signatureBean.getMarca().getTsLenght());
				Date dtTsLenght = new Date(longTsLenght);
				marca.setDate(getXMLGregorianCalendarDate(dtTsLenght));
			}
			if(signatureBean.getMarca().getIsValida() != null && signatureBean.getMarca().getIsValida()) {
				marca.setVerificationStatus(VerificationStatusType.OK);
			} else {
				marca.setVerificationStatus(VerificationStatusType.KO);
			}
			marca.setTsaName(signatureBean.getMarca().getTsAuthority());
			marca.setVerificationStatus(signatureBean.getMarca().getIsValida() != null && signatureBean.getMarca().getIsValida()
					? VerificationStatusType.OK : VerificationStatusType.KO);
			lSignerInformationTypeInterna.setMarca(marca);
		}
		
		/*
		 * FIRMA
		 */
		if(StringUtils.isNotBlank(signatureBean.getTimestamp())) {
			Long longTimestamp = new Long(signatureBean.getTimestamp());
			Date dtTimestamp = new Date(longTimestamp);
			lSignerInformationTypeInterna.setSigningTime(getXMLGregorianCalendarDate(dtTimestamp));
		}
		/*
		 *  Verifica FIRMA
		 */
		AbstractResponseOperationType sigValResultSIGN = new AbstractResponseOperationType();
		if(setValidationSignatureITAgile(signatureBean, sigVerifyResultInterna, sigValResultSIGN)) {
			lSignerInformationTypeInterna.setVerificationStatus(VerificationStatusType.OK);
		} else {
			lSignerInformationTypeInterna.setVerificationStatus(VerificationStatusType.KO);
		}
		sigVerifyResultInterna.setSignatureValResult(sigValResultSIGN);//validTrust
		
		listSignerInformationTypeInterna.add(lSignerInformationTypeInterna);
		
		signerInformationsInterna.getSignerInformation().addAll(listSignerInformationTypeInterna);
		sigVerifyResultInterna.setSignerInformations(signerInformationsInterna);
		lSigVerifyResultTypeInterna.setSigVerifyResult(sigVerifyResultInterna);
		listaSigVerifyResultTypeInterna.add(lSigVerifyResultTypeInterna);
	}

	private SignVerifyResponseBean verificaFirmaITAgile(Hsm hsmITAgile, File fileFirmatoInput)//, boolean isFirmaRicorsiva)
			throws HsmClientConfigException, HsmClientSignatureVerifyException, IOException, NoSignerException {
		
		SignVerifyResponseBean signVerifyRespBean = new SignVerifyResponseBean();
		SignVerifyResponseBean signVerifyRespSbustatoBean = null;
		
		if ("PAdES".equalsIgnoreCase(tipoFirma)) {
			log.debug("Chiamo itAgile con tipoFirma " + tipoFirma );
			signVerifyRespBean = hsmITAgile.verificaFirmePades(getFileBytes(fileFirmatoInput));
			if( signVerifyRespBean!=null && signVerifyRespBean.getSignatureBean()!=null)
				log.debug("trovate " + signVerifyRespBean.getSignatureBean().size() + " firme");
		} else if ("CAdES".equalsIgnoreCase(tipoFirma)) {
			//if(isFirmaRicorsiva) {					
				log.debug("Chiamo itAgile con tipoFirma " + tipoFirma );
				signVerifyRespBean = hsmITAgile.verificaFirmeCades(getFileBytes(fileFirmatoInput));
				if( signVerifyRespBean!=null && signVerifyRespBean.getSignatureBean()!=null)
					log.debug("trovate " + signVerifyRespBean.getSignatureBean().size() + " firme");
					for(SignatureBean firma : signVerifyRespBean.getSignatureBean()){
						if( firma!=null && firma.getCertificato()!=null)
							log.debug("firma: " + firma.getCertificato().getCognomeFirmatario() );
					}
					log.debug("La verifica è ricorsiva, provo a sbustare");
					File fileSbustatoInterno = File.createTempFile("fileSbustato", ".tmp");
					log.debug("File sbustato interno " + fileSbustatoInterno );
					FileUtils.writeByteArrayToFile(fileSbustatoInterno, signVerifyRespBean.getContent());
					
					signVerifyRespBean = invocaAgileRicorsiva(fileSbustatoInterno, signVerifyRespBean, hsmITAgile, 1);
//					try {
//						AbstractSigner signerSbustato = SignerUtil.newInstance().getSignerManager(fileSbustatoInterno);
//						log.debug("signerSbustato per la ricerca ricorsiva "+ signerSbustato);
//						signVerifyRespSbustatoBean = new SignVerifyResponseBean();
//						if (signerSbustato.getFormat().equals(SignerType.PAdES)) {
//							log.debug("Chiamo itAgile con tipoFirma " + "PAdes" );
//							signVerifyRespSbustatoBean = hsmITAgile.verificaFirmePades(getFileBytes(fileSbustatoInterno));
//							if( signVerifyRespSbustatoBean!=null && signVerifyRespSbustatoBean.getSignatureBean()!=null){
//								log.debug("trovate " + signVerifyRespSbustatoBean.getSignatureBean().size() + " firme");
//								for(SignatureBean firma : signVerifyRespSbustatoBean.getSignatureBean()){
//									if( firma!=null && firma.getCertificato()!=null)
//										log.debug("firma: " + firma.getCertificato().getCognomeFirmatario() );
//								}
//							}
//							addFirmaSbustata(signVerifyRespBean, signVerifyRespSbustatoBean, 1);
//						} else if (signerSbustato.getFormat().name().startsWith("CAdES")) {
//							log.debug("Chiamo itAgile con tipoFirma " + "CAdes" );
//							signVerifyRespSbustatoBean = hsmITAgile.verificaFirmeCades(getFileBytes(fileSbustatoInterno));
//							if( signVerifyRespSbustatoBean!=null && signVerifyRespSbustatoBean.getSignatureBean()!=null){
//								log.debug("trovate " + signVerifyRespSbustatoBean.getSignatureBean().size() + " firme");
//								for(SignatureBean firma : signVerifyRespSbustatoBean.getSignatureBean()){
//									if( firma!=null && firma.getCertificato()!=null)
//										log.debug("firma: " + firma.getCertificato().getCognomeFirmatario() );
//								}
//							}
//							addFirmaSbustata(signVerifyRespBean, signVerifyRespSbustatoBean, 1);
//
//							log.debug("La verifica è ricorsiva, provo a sbustare");
//							File fileSbustatoInterno1 = File.createTempFile("fileSbustato", ".tmp");
//							log.debug("File sbustato interno1 " + fileSbustatoInterno1 );
//							FileUtils.writeByteArrayToFile(fileSbustatoInterno1, signVerifyRespSbustatoBean.getContent());
//							try {
//								AbstractSigner signerSbustato1 = SignerUtil.newInstance().getSignerManager(fileSbustatoInterno1);
//								log.debug("signerSbustato1 per la ricerca ricorsiva "+ signerSbustato1);
//								if (signerSbustato1.getFormat().equals(SignerType.PAdES)) {
//									log.debug("Chiamo itAgile con tipoFirma " + "PAdes" );
//									SignVerifyResponseBean signVerifyRespSbustatoBean2 = hsmITAgile.verificaFirmePades(getFileBytes(fileSbustatoInterno1));			
//									if( signVerifyRespSbustatoBean2!=null && signVerifyRespSbustatoBean2.getSignatureBean()!=null){
//										log.debug("trovate " + signVerifyRespSbustatoBean2.getSignatureBean().size() + " firme");
//										for(SignatureBean firma : signVerifyRespSbustatoBean2.getSignatureBean()){
//											if( firma!=null && firma.getCertificato()!=null)
//												log.debug("firma: " + firma.getCertificato().getCognomeFirmatario() );
//										}
//									}
//									addFirmaSbustata(signVerifyRespBean, signVerifyRespSbustatoBean2, 2);
//								}
//							} catch (Exception e) {
//								log.error("Errore" , e);	
//								return signVerifyRespBean;
//							}
//
//						} else if (signerSbustato.getFormat().name().startsWith("XAdES")) {
//							log.debug("Chiamo itAgile con tipoFirma " + "XAdes" );
//							signVerifyRespSbustatoBean = hsmITAgile.verificaFirmeXades(getFileBytes(fileSbustatoInterno));
//							if( signVerifyRespBean!=null && signVerifyRespBean.getSignatureBean()!=null)
//								log.debug("trovate " + signVerifyRespBean.getSignatureBean().size() + " firme");
//						}
//						
//					} catch (Exception e) {
//						log.error("Errore" , e);	
//						return signVerifyRespBean;
//					}
				  
			/*} else {
				signVerifyRespBean = hsmITAgile.verificaFirmeCades(getFileBytes(fileFirmatoInput));
			}*/
		} else if ("XAdES".equalsIgnoreCase(tipoFirma)) {
			signVerifyRespBean = hsmITAgile.verificaFirmeXades(getFileBytes(fileFirmatoInput));
		}

		return signVerifyRespBean;
	}
	
	private SignVerifyResponseBean invocaAgileRicorsiva(File fileDaSbustare, SignVerifyResponseBean signVerifyRespBean, Hsm hsmITAgile, int livello){
		try {
			log.debug("Cerco di sbustare il file " + fileDaSbustare);
			AbstractSigner signerSbustato = SignerUtil.newInstance().getSignerManager(fileDaSbustare);
			log.debug("SignerSbustato "+ signerSbustato);
			SignVerifyResponseBean signVerifyRespSbustatoBean = new SignVerifyResponseBean();
			
			if ( signerSbustato.getFormat().name().startsWith("PAdES") ) {
				log.debug("Chiamo itAgile con tipoFirma " + SignerType.PAdES );
				signVerifyRespSbustatoBean = hsmITAgile.verificaFirmePades(getFileBytes(fileDaSbustare));
				if( signVerifyRespSbustatoBean!=null && signVerifyRespSbustatoBean.getSignatureBean()!=null){
					log.debug("Sono state trovate " + signVerifyRespSbustatoBean.getSignatureBean().size() + " firme");
					for(SignatureBean firma : signVerifyRespSbustatoBean.getSignatureBean()){
						if( firma!=null && firma.getCertificato()!=null)
							log.debug("firma: " + firma.getCertificato().getCognomeFirmatario() );
					}
				}
				addFirmaSbustata(signVerifyRespBean, signVerifyRespSbustatoBean, livello);
			} else if (signerSbustato.getFormat().name().startsWith("CAdES")) {
				log.debug("Chiamo itAgile con tipoFirma " + "CAdes" );
				signVerifyRespSbustatoBean = hsmITAgile.verificaFirmeCades(getFileBytes(fileDaSbustare));
				if( signVerifyRespSbustatoBean!=null && signVerifyRespSbustatoBean.getSignatureBean()!=null){
					log.debug("Sono state trovate " + signVerifyRespSbustatoBean.getSignatureBean().size() + " firme");
					for(SignatureBean firma : signVerifyRespSbustatoBean.getSignatureBean()){
						if( firma!=null && firma.getCertificato()!=null)
							log.debug("firma: " + firma.getCertificato().getCognomeFirmatario() );
					}
				}
				addFirmaSbustata(signVerifyRespBean, signVerifyRespSbustatoBean, livello);
				
				log.debug("La verifica è ricorsiva, provo ancora a sbustare");
				File fileSbustatoInterno = File.createTempFile("fileSbustato", ".tmp");
				FileUtils.writeByteArrayToFile(fileSbustatoInterno, signVerifyRespSbustatoBean.getContent());
				log.debug("File sbustato interno " + fileSbustatoInterno );
				
				signVerifyRespBean = invocaAgileRicorsiva(fileSbustatoInterno, signVerifyRespBean, hsmITAgile, livello+1);
				
			} else if (signerSbustato.getFormat().name().startsWith("XAdES")) {
				log.debug("Chiamo itAgile con tipoFirma " + "XAdes" );
				signVerifyRespSbustatoBean = hsmITAgile.verificaFirmeXades(getFileBytes(fileDaSbustare));
				if( signVerifyRespBean!=null && signVerifyRespBean.getSignatureBean()!=null)
					log.debug("trovate " + signVerifyRespBean.getSignatureBean().size() + " firme");
			}
			
		} catch (Exception e) {
			log.error("Errore" , e);	
			return signVerifyRespBean;
		}	
		
		return signVerifyRespBean;
	}
	
	private void addFirmaSbustata(SignVerifyResponseBean signVerify, SignVerifyResponseBean signVerifySbustato, int livello) {
		if(signVerifySbustato != null && signVerifySbustato.getSignatureBean() !=  null &&
				!signVerifySbustato.getSignatureBean().isEmpty()) {
			for(SignatureBean signItem: signVerifySbustato.getSignatureBean()) {
				signItem.setNrLivello(livello);
				log.debug("Aggiungo una firma livello " + livello + " " + signItem.getCertificato().getCognomeFirmatario() );
				signVerify.getSignatureBean().add(signItem);
			}
		} 
	}
	
	/**
	 * Restituisce un istanza Hsm di tipo HsmItagile
	 */
	private Hsm callItagileWsdl() {
		
		HsmType hsmType = HsmType.ITAGILE;
		HsmConfig hsmConfig = new HsmConfig();
		hsmConfig.setHsmType(hsmType);
		WSConfig wsSignConfig = new WSConfig();
//		wsSignConfig.setWsdlEndpoint("https://firmaremota.ente.regione.emr.it/FirmaRemota/services/RemoteSignature?wsdl");
//		wsSignConfig.setServiceNS("http://impl.ws.firmaremota.itagile.it/");
//		wsSignConfig.setServiceName("remoteSignature");
		wsSignConfig.setWsdlEndpoint(endpoint);
		wsSignConfig.setServiceNS(servicens);
		wsSignConfig.setServiceName(servicename);
		ItagileConfig itagileConfig = new ItagileConfig();
		itagileConfig.setWsSignConfig(wsSignConfig);
		hsmConfig.setClientConfig(itagileConfig);
		Hsm hsmITAgile = (HsmItagile) HsmItagile.getNewInstance(hsmConfig);

		return hsmITAgile;
	}
	
	public byte[] getFileBytes(File file) throws IOException {

		byte[] buffer = new byte[(int) file.length()];
		InputStream ios = null;
		try {
			ios = new FileInputStream(file);
			if (ios.read(buffer) == -1) {
				throw new IOException("EOF reached while trying to read the whole file");
			}
		} finally {
			try {
				if (ios != null)
					ios.close();
			} catch (IOException e) {
			}
		}
		return buffer;
	}

	public File getBytesFile(byte[] content) throws IOException {

		File outputFile = new File(System.getProperty("java.io.tmpdir"));
		FileOutputStream outputStream = null;
		try {
			outputStream = new FileOutputStream(outputFile);
			outputStream.write(content);
		} catch (Exception e) {
			log.error("Errore nel meteodo getBytesFile" + e.getMessage(), e);
		} finally {
			outputStream.close();
		}
		return outputFile;
	}

	private XMLGregorianCalendar getXMLGregorianCalendarDate(Date data) {
		GregorianCalendar c = new GregorianCalendar();
		c.setTime(data);
		XMLGregorianCalendar date2 = null;
		try {
			date2 = DatatypeFactory.newInstance().newXMLGregorianCalendar(c);

		} catch (DatatypeConfigurationException e) {
		}
		return date2;
	}
	
	//valid & validSign
	private boolean setValidationSignatureITAgile(SignatureBean signatureBean, SigVerifyResult sigVerifyResult, AbstractResponseOperationType sigValResultSIGN) {
		
		boolean isValid = false;
		ValidationInfos validationInfoSIGN = new ValidationInfos();
		/**
		 * 	Firma valida se:
		 *  1) valid – boolean indicante se la firma risulta valida in tutti i suoi aspetti
		 *  2) validSign – boolean indicante se la firma è valida ossia il documento non è stato modificato e la firma è corretta
		 */
		if((signatureBean.getIsValidaCompleta() != null && signatureBean.getIsValidaCompleta()) &&
		   (signatureBean.getIsValida() != null && signatureBean.getIsValida())) {
			sigValResultSIGN.setVerificationStatus(VerificationStatusType.OK);
			isValid = true;
		} else {
			sigValResultSIGN.setVerificationStatus(VerificationStatusType.KO);
		}
		List<ErrorBean> listaErrorBean = new ArrayList<ErrorBean>();
		ErrorBean lErrorBean = new ErrorBean();
		lErrorBean.setDescription("Errore validazione della firma: "+ signatureBean.getSignErrCode());
		lErrorBean.setCode(signatureBean.getSignErrCode());
		listaErrorBean.add(lErrorBean);
		validationInfoSIGN.setErrorsBean(listaErrorBean);
		
		populateResponseFromValidationInfoITAgile(sigValResultSIGN, validationInfoSIGN, isValid);
		
		return isValid;
	}
	
	//certErrCode 
	private void setValidationCertificateITAgile(SignatureBean signatureBean, SigVerifyResult sigVerifyResult, AbstractResponseOperationType sigValResultCERT) {
		
		boolean isValid = false;
		ValidationInfos validationInfosCERT = new ValidationInfos();
		if(signatureBean.getCertificato().getIsValido() != null && signatureBean.getCertificato().getIsValido()) {
			sigValResultCERT.setVerificationStatus(VerificationStatusType.OK);
			isValid = true;
		} else {
			sigValResultCERT.setVerificationStatus(VerificationStatusType.KO);
		}
		List<ErrorBean> listaErrorBean = new ArrayList<ErrorBean>();
		ErrorBean lErrorBean = new ErrorBean();
		lErrorBean.setDescription("Errore validazione certificato: "+ signatureBean.getCertificato().getCertErrCode());
		lErrorBean.setCode(signatureBean.getCertificato().getCertErrCode());
		listaErrorBean.add(lErrorBean);
		validationInfosCERT.setErrorsBean(listaErrorBean);
		
		populateResponseFromValidationInfoITAgile(sigValResultCERT, validationInfosCERT, isValid);
	}
	
	//certErrCode 
	private void setValidationCRLITAgile(SignatureBean signatureBean, SigVerifyResult sigVerifyResult, AbstractResponseOperationType sigValResultCRL) {
		
		boolean isValid = false;
		ValidationInfos validationInfosCRL = new ValidationInfos();
		if(signatureBean.getCertificato().getIsValido() != null && signatureBean.getCertificato().getIsValido()) {
			sigValResultCRL.setVerificationStatus(VerificationStatusType.OK);
			isValid = true;
		} else {
			sigValResultCRL.setVerificationStatus(VerificationStatusType.KO);
		}
		List<ErrorBean> listaErrorBean = new ArrayList<ErrorBean>();
		ErrorBean lErrorBean = new ErrorBean();
		lErrorBean.setDescription("Errore validazione certificato: "+signatureBean.getCertificato().getCertErrCode());
		lErrorBean.setCode(signatureBean.getCertificato().getCertErrCode());
		listaErrorBean.add(lErrorBean);
		validationInfosCRL.setErrorsBean(listaErrorBean);
		
		populateResponseFromValidationInfoITAgile(sigValResultCRL, validationInfosCRL, isValid);
	}
	
	//validTrust
	private void setValidationCAITAgile(SignatureBean signatureBean, SigVerifyResult sigVerifyResult, AbstractResponseOperationType sigValResultCA) {
		
		boolean isValid = false;
		ValidationInfos validationInfosCA = new ValidationInfos();
		if(signatureBean.getCertificato().getIsCAValida() != null && signatureBean.getCertificato().getIsCAValida()) {
			sigValResultCA.setVerificationStatus(VerificationStatusType.OK);
			isValid = true;
		} else {
			sigValResultCA.setVerificationStatus(VerificationStatusType.KO);
		}
		List<ErrorBean> listaErrorBean = new ArrayList<ErrorBean>();
		ErrorBean lErrorBean = new ErrorBean();
		lErrorBean.setDescription("Errore validazione CA che ha emesso il certificato: "+signatureBean.getCertificato().getTrustErrCode());
		lErrorBean.setCode(signatureBean.getCertificato().getTrustErrCode());
		listaErrorBean.add(lErrorBean);
		validationInfosCA.setErrorsBean(listaErrorBean);
		
		populateResponseFromValidationInfoITAgile(sigValResultCA, validationInfosCA, isValid);
	}
	
	private void populateResponseFromValidationInfoITAgile(AbstractResponseOperationType arot,
			ValidationInfos vinfos, boolean isValid) {

		if (vinfos != null) {
			if (isValid) {
				log.debug("Is valid");
				arot.setVerificationStatus(VerificationStatusType.OK);
			} else {
				log.debug("Is not valid");
				VerificationStatusType status = VerificationStatusType.KO;
				// se non puoi eseguire il controllo ritorna un error
				if (vinfos.isCannotExecute()) {
					status = VerificationStatusType.ERROR;
				}
				OutputOperations.addErrors(arot, vinfos.getErrorsBean(), status);
				if (vinfos.getWarnings() != null && vinfos.getWarnings().length > 0)
					OutputOperations.addWarnings(arot, vinfos.getWarningsBean());
			}
		} else {
			arot.setVerificationStatus(VerificationStatusType.SKIPPED);
		}
	}
}