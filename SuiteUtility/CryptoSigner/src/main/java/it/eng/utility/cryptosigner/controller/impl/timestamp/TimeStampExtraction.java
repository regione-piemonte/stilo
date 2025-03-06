package it.eng.utility.cryptosigner.controller.impl.timestamp;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.security.Security;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.bouncycastle.cert.X509CertificateHolder;
import org.bouncycastle.cms.CMSException;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.tsp.GenTimeAccuracy;
import org.bouncycastle.tsp.TimeStampToken;
import org.bouncycastle.tsp.TimeStampTokenInfo;
import org.bouncycastle.util.Store;

import it.eng.utility.cryptosigner.controller.bean.DocumentAndTimeStampInfoBean;
import it.eng.utility.cryptosigner.controller.bean.InputTimeStampBean;
import it.eng.utility.cryptosigner.controller.bean.OutputTimeStampBean;
import it.eng.utility.cryptosigner.controller.bean.ValidationInfos;
import it.eng.utility.cryptosigner.controller.exception.ExceptionController;
import it.eng.utility.cryptosigner.data.AbstractSigner;
import it.eng.utility.cryptosigner.data.SignerUtil;
import it.eng.utility.cryptosigner.exception.CryptoSignerException;
import it.eng.utility.cryptosigner.exception.NoSignerException;

public class TimeStampExtraction extends AbstractTimeStampController {

	private static Logger log = LogManager.getLogger(TimeStampExtraction.class);

	@Override
	public boolean execute(InputTimeStampBean input, OutputTimeStampBean output, boolean eseguiValidazioni) throws ExceptionController {
		List<DocumentAndTimeStampInfoBean> documentAndTimeStampInfoList = new ArrayList<DocumentAndTimeStampInfoBean>();
		AbstractSigner signer = null;
		TimeStampToken[] timeStampTokens;
		boolean executeCurrentDateValidation = false;
		boolean result = true;
		try {
			if (input.getTimeStampWithContentFile() != null) {
				if (input.getSigner() == null && input.getIsSigned() == null) {
					log.debug("Verifico se il file " + input.getTimeStampWithContentFile() + " deve essere sbustato. fileName " + input.getFileName());
					if (input.getFileName() != null) {
						String fileName = input.getFileName();
						if (!StringUtils.isBlank(fileName) && fileName.contains(".")) {
							String estensioneFile = FilenameUtils.getExtension(fileName);
							if (estensioneFile != null && !StringUtils.isBlank(fileName))
								signer = SignerUtil.newInstance().getSignerManager(input.getTimeStampWithContentFile(), estensioneFile);

							else
								signer = SignerUtil.newInstance().getSignerManager(input.getTimeStampWithContentFile());
						} else {
							signer = signerUtil.getSignerManager(input.getTimeStampWithContentFile());
						}
					} else {
						signer = signerUtil.getSignerManager(input.getTimeStampWithContentFile());
					}
					log.info("Tipo di signer individuato: " + signer);
				} else {
					signer = input.getSigner();
					if (signer == null) {
						log.info("Il file " + input.getTimeStampWithContentFile() + " non e' firmato ");
						//throw new ExceptionController("Nessun Manager Signer Trovato per il file specificato: " + input.getTimeStampWithContentFile());
					} else {
						log.info("Tipo di signer individuato: " + signer);
					}
				}

				if( signer!=null ){
				timeStampTokens = signer.getTimeStampTokens(input.getTimeStampWithContentFile());
				if (timeStampTokens != null) {

					for (TimeStampToken timeStampToken : timeStampTokens) {

						DocumentAndTimeStampInfoBean documentAndTimeStampInfo = new DocumentAndTimeStampInfoBean();
						documentAndTimeStampInfo.setAssociatedFile(input.getTimeStampWithContentFile());
						documentAndTimeStampInfo.setTimeStampToken(timeStampToken);

						/*
						 * Formato della marca temporale
						 */
						documentAndTimeStampInfo.setProperty(DocumentAndTimeStampInfoBean.PROP_TIMESTAMP_FORMAT, signer.getFormat());

						/*
						 * Tipo di marca (EMBEDDED)
						 */
						documentAndTimeStampInfo.setTimeStampTokenType(DocumentAndTimeStampInfoBean.TimeStampTokenType.EMBEDDED);

						/*
						 * Verifica che la marca temporale corrisponda al file di appartenenza
						 */
						ValidationInfos infos = null;
						try {
							log.debug("Chiamo il metodo di validazione del timestamp");
							infos = signer.validateTimeStampTokensEmbedded();
						} catch (CMSException e) {
							log.warn("CMSException", e);
						}
						if (infos == null) {
							infos = new ValidationInfos();
							infos.addWarning(signer.getClass().getName() + " non ha potuto completare la validazione oppure la marca non e di tipo: "
									+ documentAndTimeStampInfo.getTimeStampTokenType());
						}

						/*
						 * Genera gli attributi comuni per tutte le marche temporali
						 */
						populateCommonAttributes(documentAndTimeStampInfo, infos, executeCurrentDateValidation);
						documentAndTimeStampInfo.setValidationInfos(infos);
						documentAndTimeStampInfoList.add(documentAndTimeStampInfo);

						// result &= infos.isValid();
					}
				} else {
					log.info("Il file non contiene nessuna marca");
				}
				}
			} else {
				signer = signerUtil.getSignerManager(input.getTimeStampFile());
				timeStampTokens = signer.getTimeStampTokens(input.getTimeStampFile());

				for (TimeStampToken timeStampToken : timeStampTokens) {

					DocumentAndTimeStampInfoBean documentAndTimeStampInfo = new DocumentAndTimeStampInfoBean();

					documentAndTimeStampInfo.setAssociatedFile(input.getContentFile());
					documentAndTimeStampInfo.setTimeStampToken(timeStampToken);

					/*
					 * Formato della marca temporale
					 */
					documentAndTimeStampInfo.setProperty(DocumentAndTimeStampInfoBean.PROP_TIMESTAMP_FORMAT, signer.getFormat());

					/*
					 * Tipo di marca (DETACHED)
					 */
					documentAndTimeStampInfo.setTimeStampTokenType(DocumentAndTimeStampInfoBean.TimeStampTokenType.DETACHED);

					/*
					 * Verifica che la marca temporale corrisponda al file di appartenenza
					 */
					ValidationInfos infos = signer.validateTimeStampTokensDetached(input.getContentFile());

					if (infos == null) {
						infos = new ValidationInfos();
						infos.addWarning("Il signer non ha effettuato la validazione oppure la marca non e di tipo: "
								+ documentAndTimeStampInfo.getTimeStampTokenType());
					}
					/*
					 * Genera gli attributi comuni per tutte le marche temporali
					 */
					populateCommonAttributes(documentAndTimeStampInfo, infos, executeCurrentDateValidation);

					documentAndTimeStampInfo.setValidationInfos(infos);
					documentAndTimeStampInfoList.add(documentAndTimeStampInfo);

					// result &= infos.isValid();
				}

			}
		} catch (NoSignerException e) {
			log.info("Il file " + input.getTimeStampWithContentFile() + " non e' firmato ");
			//throw new ExceptionController(e);
		}

		if( signer!=null ){
			Map<byte[], TimeStampToken> mapSignatureTimeStampToken = null;
			if (input.getTimeStampWithContentFile() != null) {
				mapSignatureTimeStampToken = signer.getMapSignatureTimeStampTokens(input.getTimeStampWithContentFile());
			} else {
				mapSignatureTimeStampToken = signer.getMapSignatureTimeStampTokens(input.getTimeStampFile());
			}
			
			output.setMapSignatureTimeStampTokens(mapSignatureTimeStampToken);
	
			output.setDocumentAndTimeStampInfos(documentAndTimeStampInfoList);
			output.setSigner(signer);
		}
		
		return result;
	}

	private void populateCommonAttributes(DocumentAndTimeStampInfoBean documentAndTimeStampInfo, ValidationInfos validationInfos,
			boolean executeCurrentDateValidation) {
		TimeStampToken timeStampToken = documentAndTimeStampInfo.getTimeStampToken();

		/*
		 * Tipo di algorimto utilizzato durante la generazione dell'hash del messaggio - ' l'algoritmo impiegato per effettuare l'impronta del file marcato
		 */
		if (timeStampToken != null) {
			TimeStampTokenInfo tokenInfo = timeStampToken.getTimeStampInfo();
			documentAndTimeStampInfo.setProperty(DocumentAndTimeStampInfoBean.PROP_HASH_ALGORITHM, tokenInfo.getMessageImprintAlgOID());
			log.info("MessageImprintAlgOID:: " + tokenInfo.getMessageImprintAlgOID() );
			/*
			 * Riferimento temporale in millisecondi (se disponibili) della marca
			 */
			GenTimeAccuracy accuracy = tokenInfo.getGenTimeAccuracy();
			log.info("accuracy " + accuracy);
			Long millis = accuracy != null ? tokenInfo.getGenTime().getTime() + tokenInfo.getGenTimeAccuracy().getMillis() : tokenInfo.getGenTime().getTime();
			log.info("millis " + millis);
			documentAndTimeStampInfo.setProperty(DocumentAndTimeStampInfoBean.PROP_MILLISECS, millis.toString());

			/*
			 * Data del riferimento temporale
			 */
			Date timestampDate = new Date(tokenInfo.getGenTime().getTime());
			documentAndTimeStampInfo.setProperty(DocumentAndTimeStampInfoBean.PROP_DATE, timestampDate);
			log.info("Data della marca:: " + timestampDate );
			
			BigInteger tsaSerial = timeStampToken.getSID().getSerialNumber();
			
			Store certStore = timeStampToken.toCMSSignedData().getCertificates();
			Collection<X509CertificateHolder> saCertificates = certStore.getMatches(null);
			try {
				for (X509CertificateHolder saCertificateHolder : saCertificates) {
					
					// Controllo se il certificato corrisponde a quello della TSA
					if (saCertificateHolder.getSerialNumber().equals(tsaSerial)) {
						CertificateFactory certFactory = CertificateFactory.getInstance("X.509");
						
						if (Security.getProvider(BouncyCastleProvider.PROVIDER_NAME) == null) {
							log.info("-----aggiungo il provider ");
							Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
						}
						
						X509Certificate saX509Certificate = (X509Certificate) certFactory
								.generateCertificate(new ByteArrayInputStream(saCertificateHolder.getEncoded()));
						
						log.info("TSA subject Name = " + saX509Certificate.getSubjectX500Principal().getName());
						documentAndTimeStampInfo.setProperty(DocumentAndTimeStampInfoBean.PROP_SID, saX509Certificate.getSubjectX500Principal().getName());
					}
					
				}
			} catch (CertificateException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
	}

}
