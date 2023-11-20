/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.Provider;
import java.security.Security;
import java.security.cert.Certificate;
import java.security.cert.X509Certificate;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;

import javax.mail.Address;
import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.MimeUtility;
import javax.mail.internet.MimeMessage.RecipientType;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.xml.security.Init;
import org.bouncycastle.cert.X509CertificateHolder;
import org.bouncycastle.cert.jcajce.JcaX509CertificateConverter;
import org.bouncycastle.cms.SignerInformation;
import org.bouncycastle.cms.SignerInformationStore;
import org.bouncycastle.cms.jcajce.JcaSimpleSignerInfoVerifierBuilder;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.mail.smime.SMIMESignedParser;
import org.bouncycastle.operator.DigestCalculatorProvider;
import org.bouncycastle.operator.bc.BcDigestCalculatorProvider;

import com.sun.mail.dsn.DeliveryStatus;
import com.sun.mail.dsn.DispositionNotification;
import com.sun.mail.dsn.MultipartReport;

import it.eng.aurigamailbusiness.bean.MailAttachmentsBean;
import it.eng.aurigamailbusiness.fileoperation.FileUtilities;
import it.eng.aurigamailbusiness.structure.AttachmentInfo;
import it.eng.aurigamailbusiness.structure.AttachmentMailInfo;
import it.eng.aurigamailbusiness.structure.MailBodyParts;
import it.eng.aurigamailbusiness.structure.MessageInfos;
import it.eng.aurigamailbusiness.utility.MailUtil;

/**
 * classe di verifica del messaggio email
 * 
 * @author jravagnan
 *
 */

public class MailVerifier {

	private static Logger logger = LogManager.getLogger(MailVerifier.class);

	private Provider bcprov;
	private JcaSimpleSignerInfoVerifierBuilder verifier;
	private JcaX509CertificateConverter jcaX509CertificateConverter;
	private DigestCalculatorProvider digestCalculatorProvider = new BcDigestCalculatorProvider();
	public static final String REGULAR_EXPRESSION_ADDRESS = "^([_a-zA-Z0-9-]+(\\.[_a-zA-Z0-9-]+)*@[a-zA-Z0-9-]+(\\.[a-zA-Z0-9-]+)*(\\.[a-zA-Z]{2,9}))?$";
	public MailVerifier() {

		this.bcprov = Security.getProvider(BouncyCastleProvider.PROVIDER_NAME);
		if (this.bcprov == null) {
			this.bcprov = new BouncyCastleProvider();
			Security.addProvider(bcprov);
		}

		this.verifier = new JcaSimpleSignerInfoVerifierBuilder();
		this.verifier.setProvider(bcprov);

		this.jcaX509CertificateConverter = new JcaX509CertificateConverter();
		this.jcaX509CertificateConverter.setProvider(bcprov);

		if (!Init.isInitialized()) {
			Init.init();
		}

	}

	@SuppressWarnings("unchecked")
	protected Set<Certificate> verify(final SMIMESignedParser parserSMIMES) throws Exception {
		final Set<Certificate> certificates = new HashSet<Certificate>();
		final org.bouncycastle.util.Store certs = parserSMIMES.getCertificates();
		final SignerInformationStore signers = parserSMIMES.getSignerInfos();
		final Collection<SignerInformation> collection = signers.getSigners();
		for (SignerInformation signer : collection) {
			final Collection<X509Certificate> certCollection = (Collection<X509Certificate>) certs
					.getMatches(signer.getSID());
			final Object cert = certCollection.iterator().next();
			try {
				if (cert instanceof X509Certificate) {
					if (!signer.verify(this.verifier.build((X509Certificate) cert))) {
						throw new Exception("signature invalid");
					}
					certificates.add((Certificate) cert);
				} else if (cert instanceof X509CertificateHolder) {
					X509Certificate certx509 = this.jcaX509CertificateConverter
							.getCertificate((X509CertificateHolder) cert);

					if (!signer.verify(this.verifier.build(certx509))) {
						throw new Exception("signature invalid");
					}
				}
			} catch (Exception e) {
				logger.error("Impossibile verificare il certificato ", e);
				throw e;
			}
		}
		return certificates;
	}

	/**
	 * Metodo di verifica del MimeMessage in input. <br>
	 * Restituisce oggetto MessageInfos con le informazioni sulla mail. <br>
	 * Salva i temporanei prodotti nella directory di default
	 * 
	 * @param msg
	 * @param tempDir
	 * @param isSpam
	 * @return
	 * @throws Exception
	 */

	public MessageInfos verifyAnalize(MimeMessage msg, boolean isSpam) throws Exception {
		return this.verifyAnalize(msg, null, isSpam);
	}

	public MessageInfos verifyAnalize(MimeMessage msg) throws Exception {
		return this.verifyAnalize(msg, null, false);
	}

	public MessageInfos verifyAnalize(MimeMessage msg, File tempDir) throws Exception {
		return this.verifyAnalize(msg, tempDir, false);
	}

	/**
	 * Metodo di verifica del MimeMessage in input Restituisce oggetto
	 * MessageInfos con le informazioni sulla mail Salva i temporanei prodotti
	 * nella cartella in input
	 * 
	 * @param msg
	 * @param tempDir
	 * @param isSpam
	 * @return
	 * @throws Exception
	 */
	public String getAddressesReplyTo(MimeMessage msg) throws MessagingException {
		
		if (msg.getReplyTo() != null) {
			logger.info("ReplyTo valorizzato INIZIO ");

			Address[] addressarray = msg.getReplyTo();
			if (addressarray != null) {

				logger.info("ReplyTo addressarray lenght: " + addressarray.length);
				for (Address address : addressarray) {
					if (address != null) {
						String value = null;
						try {
							value = ((InternetAddress) address).getAddress();
							if (StringUtils.isNotBlank(value) && !Pattern.matches(REGULAR_EXPRESSION_ADDRESS, value)) {
								// l'indirizzo non è valido o è un alias provo ad
								// utilizzare il nome visualizzato, se presente
								if (StringUtils.isNotBlank(((InternetAddress) address).getPersonal())) {
									value = ((InternetAddress) address).getPersonal();
								}
							}
						} catch (Exception e) {
							logger.warn("Destinatario non valido: " + address);
						}
						if (StringUtils.isBlank(value)) {
							// provo a utilizzare l'indirizzo direttamente come
							// stringa, può essere un alias
							value = (StringUtils.remove(StringUtils.remove(address.toString(), "<"), ">"));
							value = (StringUtils.remove(StringUtils.remove(address.toString(), "\\\""), "\\\""));
						}
						if (StringUtils.isNotBlank(value)) {

							logger.info("ReplyTo value: " + value);
							return value;
						}
					}
				}
			}
			return null;
		} else {

			logger.info("ReplyTo valorizzato INIZIO ");
			return null;
		}
	}
	
	
	public MessageInfos verifyAnalize(MimeMessage msg, File tempDir, boolean isSpam) throws Exception {

		MailBodyParts bodyMessage = new MailBodyParts();
		Set<Certificate> signatures = null;

		final MessageInfos messageinfo = new MessageInfos();
		MailUtil mailutil = MailUtil.getInstance(msg);

		if (logger.isDebugEnabled()) {
			logger.debug("Verifico messaggio con id: " + msg.getMessageID());
		}
		if (tempDir != null) {
			if (logger.isDebugEnabled()) {
				logger.debug("Temp dir: " + tempDir.getAbsolutePath());
			}
		}

		// Recupero e setto i valori dell'header della mail
		messageinfo.getHeaderinfo().setMessageid(mailutil.getMessageID());
		messageinfo.getHeaderinfo().setMittente(mailutil.getXMittente());
		messageinfo.getHeaderinfo().setRicevuta(mailutil.getXRicevuta());
		messageinfo.getHeaderinfo().setDestinataricc(mailutil.getAddressesCc());
		messageinfo.getHeaderinfo().setDestinatarito(mailutil.getAddressesTo());
		messageinfo.getHeaderinfo().setDestinataribcc(mailutil.getAddressesBcc());
		messageinfo.getHeaderinfo().setRiferimentomessageid(mailutil.getXRiferimentoMessageID());
		messageinfo.getHeaderinfo().setTiporicevuta(mailutil.getXTipoRicevuta());
		messageinfo.getHeaderinfo().setTrasporto(mailutil.getXTrasporto());
		messageinfo.getHeaderinfo().setVerficasicurezza(mailutil.getXVerificaSicurezza());
		messageinfo.getHeaderinfo().setSendDate(mailutil.getSendDate());
		messageinfo.getHeaderinfo().setRetrieveDate(mailutil.getReceiveDate());
		messageinfo.getHeaderinfo().setSubject(mailutil.getSubject());
		messageinfo.getHeaderinfo().setRichiestaLettura(mailutil.getRichiestaAvvenutaLettura() == null ? false : true);
		messageinfo.getHeaderinfo().setRichiestaRicevuta(mailutil.getRichiestaRicevuta() == null ? false : true);
		messageinfo.getHeaderinfo().setPriority(mailutil.getPriority());
		messageinfo.getHeaderinfo().setMessageidInReplyTo(mailutil.getMessageIDInRepyTo());
		messageinfo.setIspam(isSpam);
		//Aggiunta gestione error
		//Local address contains control or whitespace in string Saved by Blink
		try
		{
		if (msg.getReplyTo() != null) {
			String replyTO = getAddressesReplyTo(msg);
			if (replyTO != null) {
				logger.info("replyTO ", replyTO);
				messageinfo.getHeaderinfo().setReplyTO(replyTO);
			} else {
				logger.info("replyTO NULL");
			}
		}
		}catch (Exception e) {
			logger.error("replyTO: "+e.getMessage());
		}

		boolean isMultipartSigned = false;

		if (msg.isMimeType("multipart/signed") && !msg.getContentType().contains("application/pgp-signature")) {

			SMIMESignedParser parserSMIME = null;
			try {
				parserSMIME = new SMIMESignedParser(this.digestCalculatorProvider, (MimeMultipart) msg.getContent());
			} catch (Throwable t) {
				// non lancio l'eccezione, provo a verificare comunque la mail
				logger.warn("Errore creazione SMIMESignedParser", t);
			}
			if (parserSMIME != null) {
				isMultipartSigned = true;
				// estraggo le parti della mail
				// l'eventuale daticert.xml sarà estratto nel metodo
				// setBodyPartsMime
				bodyMessage.extractPart(messageinfo, parserSMIME.getContent());
				// verifico se la firma è abilitata, potrebbe essere onerosa dal
				// punto di vista delle risorse di sistema
				// Verifico la firma
				try {
					signatures = verify(parserSMIME);
					messageinfo.setSignatures(signatures);
				} catch (Exception e) {
					logger.error("Errore nel controllo della firma del messaggio email " + msg.getMessageID(), e);
					messageinfo
							.setErrorString("Errore nel controllo della firma del messaggio email: " + e.getMessage());
				}
			}
		}

		if (!isMultipartSigned) {
			// estraggo le parti della mail
			bodyMessage.extractPart(messageinfo, msg);
		}

		// processo l'eventuale mail postacert

		MimeMessage mimeMessaggePostaCert = null;

		if (bodyMessage.getPostaCertMimeMessage() != null) {
			// Verifico la postecert
			mimeMessaggePostaCert = bodyMessage.getPostaCertMimeMessage();
			messageinfo.setPostecerteml(verifyAnalize(mimeMessaggePostaCert, tempDir));
		}

		// processo gli allegati trovati

		List<AttachmentInfo> attachments = bodyMessage.getAttachments();
		int attachmentNum = 1;

		for (AttachmentInfo attachmentInfo : attachments) {

			MailAttachmentsBean attachmentBean = new MailAttachmentsBean();
			if (attachmentInfo.getMimeType() != null) {
				attachmentBean.setMimetype(attachmentInfo.getMimeType());
			}
			attachmentBean.setFilename(attachmentInfo.getName());

			// Ricontrollo il nome
			if (StringUtils.contains(attachmentBean.getFilename(), "{0}")) {
				attachmentBean.setFilename(MessageFormat.format(attachmentBean.getFilename(), (attachmentNum++) + ""));
			}

			attachmentBean.setDisposition(attachmentInfo.getDisposition());

			InputStream attachmentStream = null;

			try {

				attachmentStream = attachmentInfo.getDataHandler().getInputStream();
				if (attachmentInfo instanceof AttachmentMailInfo) {
					if (attachmentInfo.getContentTransferEncoding() != null) {
						// se è diverso da null significa che occorre prima
						// decodificare la mail prima di salvarla in file system
						attachmentStream = MimeUtility.decode(attachmentInfo.getDataHandler().getInputStream(),
								attachmentInfo.getContentTransferEncoding());
					}
				}
				// salvataggio del file temporaneo
				attachmentBean = processAttachment(attachmentStream, tempDir, attachmentBean);

				attachmentBean.setMessageid(messageinfo.getHeaderinfo().getMessageid());

				// aggiungo alla lista di allegati
				messageinfo.getAttachments().add(attachmentBean);

				if (attachmentInfo instanceof AttachmentMailInfo) {
					// è una mail innestata
					MimeMessage nestedMessage = ((AttachmentMailInfo) attachmentInfo).getMimeMessage();
					// verifico ricorsivamente la mail innestata, tranne quella
					// della postacert, che viene analizzata a parte
					if (mimeMessaggePostaCert == null || !nestedMessage.equals(mimeMessaggePostaCert)) {
						messageinfo.getSubinfos().add(verifyAnalize(nestedMessage, tempDir));
					} else {
						// la inserisco comunque nei subinfos
						messageinfo.getSubinfos().add(messageinfo.getPostecerteml());
					}
				}
			} finally {
				if (attachmentStream != null) {
					try {
						attachmentStream.close();
					} catch (Exception e) {
						logger.warn("Errore nella chiusura dello stream", e);
					}
				}
			}

		}

		// verifiche sulla tipologia di mail

		// verifico se il mime type della mail è multipart/report o una
		// disposition o una delivery
		if (msg.isMimeType("multipart/report") && msg.getContent() instanceof MultipartReport) {
			// in questo caso salvo il contenuto per elaborare correttamente la
			// gestione del report
			messageinfo.setMultipartReport((MultipartReport) msg.getContent());
		} else if (msg.isMimeType("message/disposition-notification")
				&& msg.getContent() instanceof DispositionNotification) {
			messageinfo.setDispositionNotification((DispositionNotification) msg.getContent());
		} else if (msg.isMimeType("message/delivery-status") && msg.getContent() instanceof DeliveryStatus) {
			messageinfo.setDeliveryStatusNotification((DeliveryStatus) msg.getContent());
		} else if (bodyMessage.getAttachmentDeliveryStatusNotification() != null) {
			// in questo caso abbiamo un'allegato di tipo delivery status
			// notification
			messageinfo.setAttachmentDeliveryStatusNotification(bodyMessage.getAttachmentDeliveryStatusNotification());
		}

		if (messageinfo.isDeliveryStatusNotification()) {
			// è una delivery quindi recupero tutti gli id messaggi
			messageinfo.setAllMessageIds(mailutil.getAllMessageId());
		}

		return messageinfo;
	}

	/**
	 * Copia l'attachment su un file temporaneo e ne assegna la dimensione
	 * 
	 * @param stream
	 * @param attachmentBean
	 * @return
	 * @throws IOException
	 */
	
	protected MailAttachmentsBean processAttachment(InputStream stream, File tempDir, MailAttachmentsBean attachmentBean) throws Exception {
		FileOutputStream output = null;
		try {
			File temp = File.createTempFile("Attachments", ".tmp", tempDir);
			output = new FileOutputStream(temp);
			IOUtils.copy(stream, output);
			output.flush();
			attachmentBean.setSize(temp.length());
			attachmentBean.setFile(temp);
		} finally {
			if (output != null) {
				try {
					output.close();
				} catch (Exception e) {
					logger.warn("Errore nella chiusura dello stream", e);
				}
			}
		}
		return attachmentBean;
	}
}
