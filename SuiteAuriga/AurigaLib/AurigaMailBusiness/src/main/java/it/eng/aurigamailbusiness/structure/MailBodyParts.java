/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.activation.DataHandler;
import javax.mail.BodyPart;
import javax.mail.Part;
import javax.mail.Session;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.MimeUtility;
import javax.mail.internet.ParseException;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.sun.mail.dsn.DeliveryStatus;

import it.eng.aurigamailbusiness.daticert.Postacert;
import it.eng.aurigamailbusiness.utility.MailUtil;
import it.eng.aurigamailbusiness.utility.Util;
import it.eng.aurigamailbusiness.utility.XmlUtil;
import it.eng.aurigamailbusiness.utility.mail.MailVerifierException;
import it.eng.core.business.KeyGenerator;

public class MailBodyParts {

	private static final String MIMETYPE_APPLICATION_OCTET_STREAM = "application/octet-stream";

	private static final String ENCODING_BASE64 = "base64";

	private static final String ENCODING_QUOTED_PRINTABLE = "quoted-printable";

	private static final String ENCODING_8BIT = "8bit";

	private static final String MIMETYPE_TEXT_HTML = "text/html";

	private static final String FILENAME_DATICERT_XML = "daticert.xml";

	private static final String FILENAME_POSTACERT_EML = "postacert.eml";

	private static final String MIME_TYPE_MESSAGE_RFC822 = "message/rfc822";

	private static final String MIME_TYPE_TEXT_PLAIN = "text/plain";

	private Logger log = LogManager.getLogger(MailBodyParts.class);

	private final List<AttachmentInfo> attachments = new ArrayList<>();

	private DeliveryStatus attachmentDeliveryStatusNotification = null;

	private MimeMessage postaCertMimeMessage = null;

	public MimeMessage getPostaCertMimeMessage() {
		return postaCertMimeMessage;
	}

	public void setPostaCertMimeMessage(MimeMessage postaCertMimeMessage) {
		this.postaCertMimeMessage = postaCertMimeMessage;
	}

	public List<AttachmentInfo> getAttachments() {
		return attachments;
	}

	/**
	 * Metodo per estrarre allegati e corpo della mail
	 * 
	 * @param info
	 * @param bodyPiece
	 * @throws Exception
	 */
	public void extractPart(MessageInfos info, Part bodyPiece) throws Exception {

		// istanzio una nuova sessione con le proprietà comuni
		Session mailSession = Session.getInstance(Util.getJavaMailDefaultProperties());

		final DataHandler dataHandlerMulti = bodyPiece.getDataHandler();

		// generiamo un'id univoco per l'attachment
		final String idAttachment = KeyGenerator.gen();
		// recuperiamo il nome
		final String name = MailUtil.getAttachmentName(dataHandlerMulti);

		final String mimeType = bodyPiece.getContentType();
		final String charset = MailUtil.getCharset(dataHandlerMulti);

		String contentDisposition = BodyPart.INLINE;
		try {
			if (bodyPiece.getDisposition() != null) {
				contentDisposition = bodyPiece.getDisposition();
			}
			// si prova a ricavare manualmente la disposition cercando il
			// relativo header
		} catch (ParseException pe) {
			String contentGeneric = bodyPiece.getHeader("Content-Disposition")[0];
			// ci interessa il valore fino al primo punto e virgola
			contentDisposition = contentGeneric.substring(0, contentGeneric.indexOf(';'));
		}

		// recupero il content-transfer-encoding
		String contentTransferEncoding = null;
		try {
			contentTransferEncoding = bodyPiece.getHeader("Content-Transfer-Encoding")[0];
		} catch (Exception e) {
			if (log.isDebugEnabled()) {
				log.debug("Header 'Content-Transfer-Encoding' non valorizzato");
			}
		}

		if (StringUtils.isBlank(contentTransferEncoding)) {
			// provo a recuperare l'encoding tramite javamail
			// in alcuni casi il content transfer encoding non coincide con
			// quello recuperato da javamail
			// è preferibile utilizzare quello ricavato manualmente e impostato
			// nell'header del bodypart
			contentTransferEncoding = MimeUtility.getEncoding(dataHandlerMulti);
		}

		boolean isMultipart = false;
		if (StringUtils.containsIgnoreCase(mimeType, "multipart")) {
			isMultipart = true;
		}

		if (bodyPiece.isMimeType("multipart/*") || isMultipart) {
			Object datahandlerContent = dataHandlerMulti.getContent();

			if (datahandlerContent instanceof MimeMultipart) {
				final MimeMultipart multiPart = (MimeMultipart) datahandlerContent;
				isMultipart = true;
				int countPart = 0;
				try {
					countPart = multiPart.getCount();
				} catch (Exception exc) {
					log.warn("Eccezione missing part boundary", exc);
					
					// throw new MailVerifierException("Eccezione Multipart boundary", exc);
				}
				for (int index = 0; index < countPart; index++) {
					// estraggo ricorsivamente tutte le parti della mail
					extractPart(info, multiPart.getBodyPart(index));
				}
			}
		}

		if (!isMultipart) {

			// non è multipart significa che è una sola parte
			// nessuna gestione specifica per le immagini che sono salvate come
			// gli altri allegati
			// gestione specifica per le mail innestate

			// attenzione che alcuni allegati con estensione .mht hanno mime type message/rfc822 ma sono archivi html e non mail, vanno quindi salvati come
			// allegati

			Boolean isMail = false;

			if ((bodyPiece.isMimeType(MIME_TYPE_MESSAGE_RFC822) && !name.toLowerCase().endsWith(".mht"))
					|| (bodyPiece.isMimeType(MIMETYPE_APPLICATION_OCTET_STREAM) && name.toLowerCase().endsWith(".eml"))) {

				// verifico anche il nome dell'estensione, in alcuni casi infatti si hanno mail con mime type application/octet-stream

				// mail innestata
				// nuovo oggetto AttachmentMail
				AttachmentMailInfo attachmentMail = new AttachmentMailInfo();

				// recupero l'oggetto MimeMessagge
				// provo ad utilizzare direttamente il datahandler senza decodificarlo
				MimeMessage lMessage = new MimeMessage(mailSession, dataHandlerMulti.getInputStream());

				// verifico se il mime message è valido controllando gli header
				// se sono presenti significa che è il messaggio è valido
				if (!MailUtil.checkMimeMessage(lMessage)) {
					// altrimenti provo con la codifica recuperata, in particolare della codifica base64
					lMessage = new MimeMessage(mailSession, MimeUtility.decode(dataHandlerMulti.getInputStream(), contentTransferEncoding));
					if (!MailUtil.checkMimeMessage(lMessage)) {
						// non è possibile recuperare il messaggio mail, lancio l'eccezione se il mime type è message/rfc822
						// viceversa salvo come allegato standard

						try {
							Object obj = bodyPiece.getContent();
							if (obj instanceof Part) {
								Part contentPart = (Part) obj;
								if (contentPart.isMimeType(MIME_TYPE_TEXT_PLAIN)) {	
									// in realtà si tratta di una mail senza alcun header e con solo del testo, processo la mail come un allegato generico
									isMail = false;
								}
							} else {
								// comunque se non riesco a castare a part non è un messaggio ben formato
								isMail = false;
							}
						} catch (Exception e) {
							throw new MailVerifierException("Errore nella decodifica del messaggio rfc", e);
						}

						if (isMail && bodyPiece.isMimeType(MIME_TYPE_MESSAGE_RFC822)) {
							// se invece è effettivamente una mail malformata lancio l'eccezione
							throw new MailVerifierException("Errore nella decodifica del messaggio rfc");
						}
					} else {
						// è una mail
						isMail = true;
						// in fase di processamento della mail bisognerà decodificarla prima di procedere con il salvataggio, occorre quindi impostare il
						// corretto
						// contentTransferEncoding per farlo
						attachmentMail.setContentTransferEncoding(contentTransferEncoding);
					}
				} else {
					// è una mail
					isMail = true;
				}

				if (isMail) {

					// verifico se la mail è un vecchio formato usato spesso
					// nell'invio con Outlook o da Linux
					// in questo formato gli allegati sono compresi dentro il corpo
					// della mail, spesso in formato
					// UUEncoded e senza specificare il mime type della mail
					// I file UUEncode si riconoscono dal formato dell'header:
					// begin <mode> <file><newline>
					// e finiscono con:
					// `<newline>
					// end<newline>

					boolean isMultipartMessage = false;
					if (lMessage.getContentType() != null && StringUtils.containsIgnoreCase(lMessage.getContentType(), "multipart")) {
						isMultipartMessage = true;
					}

					if (!(lMessage.isMimeType("multipart/*") || isMultipartMessage) && MSMessage.isMSMessage(lMessage)) {
						// se il tipo contenuto è multipart/ allora il controllo del MIME-VERSION è superfluo, sono sicuramente presenti dei multipart nel
						// messaggio
						lMessage = new MSMessage(mailSession, lMessage);
					}

					attachmentMail.setDataHandler(dataHandlerMulti);
					attachmentMail.setMimeMessage(lMessage);
					attachmentMail.setDisposition(contentDisposition);
					attachmentMail.setMimeType(mimeType);
					attachmentMail.setName(name);
					attachmentMail.setId(idAttachment);

					// salvo il mime message della posta certificata
					if (FILENAME_POSTACERT_EML.equalsIgnoreCase(name) && (info.getHeaderinfo().getTrasporto() == XTrasporto.POSTA_CERTIFICATA
							|| info.getHeaderinfo().getTrasporto() == XTrasporto.ERRORE || (info.getHeaderinfo().getTrasporto() == null
									&& (info.getHeaderinfo().getRicevuta() != null || info.getHeaderinfo().getTiporicevuta() != null)))) {
						// il postacert è da considerare come mail certificata se è una PEC, una ricevuta PEC o una anomalia
						// negli altri casi si tratta di un allegato che si chiama postaCert.eml ma non è da considerare come mail certificata
						this.postaCertMimeMessage = lMessage;
					}

					// aggiungo la mail alla lista di allegati
					this.attachments.add(attachmentMail);

				}

			}

			// non è una mail, salvo come allegato semplice
			if (!isMail) {

				// al momento non ricerco fra gli allegati, in alcuni casi come
				// nei forward ci potrebbero essere delle disposition
				// che si riferiscono però al messaggio inoltrato e quindi la
				// categoria non sarebbe correttamente identificata
				// if (bodyPiece.isMimeType("message/disposition-notification"))
				// {
				// // allegato di tipo disposition/notification -> salvo
				// // l'informazione nel relativo oggetto
				// this.attachmentDispositionNotification =
				// (DispositionNotification) bodyPiece.getContent();
				// }

				// ricerco fra gli allegati eventuali delivery status
				if (bodyPiece.isMimeType("message/delivery-status")) {
					// allegato di tipo disposition/notification -> salvo
					// l'informazione nel relativo oggetto
					this.setAttachmentDeliveryStatusNotification((DeliveryStatus) bodyPiece.getContent());
				}

				// attachment daticert
				if (FILENAME_DATICERT_XML.equalsIgnoreCase(name)) {

					if (info.getHeaderinfo().getTrasporto() == XTrasporto.POSTA_CERTIFICATA || (info.getHeaderinfo().getTrasporto() == null
							&& (info.getHeaderinfo().getRicevuta() != null || info.getHeaderinfo().getTiporicevuta() != null))) {
						// Se messaggio Pec di errore non estraggo il
						// daticert.xml perchè non c'è
						// salvo l'xml nel relativo oggetto dati cert
						info.setDaticert((Postacert) XmlUtil.xmlToObject(dataHandlerMulti.getDataSource().getInputStream()));
					}

					setInfoAttachment(dataHandlerMulti, idAttachment, name, mimeType, contentDisposition, contentTransferEncoding);

				}

				else if (StringUtils.equalsIgnoreCase(BodyPart.ATTACHMENT, contentDisposition)) {

					setInfoAttachment(dataHandlerMulti, idAttachment, name, mimeType, contentDisposition, contentTransferEncoding);

				}

				// inline attachment di tipo text plain
				else if (StringUtils.containsIgnoreCase(mimeType, MIME_TYPE_TEXT_PLAIN)) {
					// decodifico la stringa e salvo il tutto come bodypart
					if (StringUtils.containsIgnoreCase(contentTransferEncoding, ENCODING_BASE64)) {
						info.addBodyPart(MailUtil.streamToString(dataHandlerMulti.getInputStream(), charset), MIME_TYPE_TEXT_PLAIN);
					} else {
						/**
						 * Se il corpo della mail ha encoding=quoted-printable allora il decode deve essere di tipo 8bit poichè lo stesso non interpreta
						 * correttamente il caso in cui è presente =[0-9]. Il tipo QPDecoderStream non converte correttamente questa casistica,quindi nel caso
						 * di email con encoding quoted-printable contenenti link cliccabili,gli stessi vengono corrotti in fase di parsing, e di coseguenza la
						 * loro visualizzazione ne viene alterata.
						 */
						if (StringUtils.containsIgnoreCase(contentTransferEncoding, ENCODING_QUOTED_PRINTABLE)) {
							InputStream is = MimeUtility.decode(dataHandlerMulti.getInputStream(), ENCODING_8BIT);
							info.addBodyPart(MailUtil.streamToString(is, charset), MIME_TYPE_TEXT_PLAIN);
						} else {
							InputStream stream = MimeUtility.decode(dataHandlerMulti.getInputStream(), contentTransferEncoding);
							info.addBodyPart(MailUtil.streamToString(stream, charset), MIME_TYPE_TEXT_PLAIN);
						}
					}
				}

				// inline attachment di tipo text plain
				else if (StringUtils.containsIgnoreCase(mimeType, MIMETYPE_TEXT_HTML)) {
					// decodifico la stringa e salvo il tutto come bodypart
					if (StringUtils.containsIgnoreCase(contentTransferEncoding, ENCODING_BASE64)) {
						info.addBodyPart(MailUtil.streamToString(dataHandlerMulti.getInputStream(), charset), MIMETYPE_TEXT_HTML);
					} else {
						/**
						 * Se il corpo della mail ha encoding=quoted-printable allora il decode deve essere di tipo 8bit poichè lo stesso non interpreta
						 * correttamente il caso in cui è presente =[0-9]. Il tipo QPDecoderStream non converte correttamente questa casistica,quindi nel caso
						 * di email con encoding quoted-printable contenenti link cliccabili,gli stessi vengono corrotti in fase di parsing, e di coseguenza la
						 * loro visualizzazione ne viene alterata.
						 */
						if (StringUtils.containsIgnoreCase(contentTransferEncoding, ENCODING_QUOTED_PRINTABLE)) {
							InputStream is = MimeUtility.decode(dataHandlerMulti.getInputStream(), ENCODING_8BIT);
							info.addBodyPart(MailUtil.streamToString(is, charset), MIMETYPE_TEXT_HTML);
						} else {
							InputStream stream = MimeUtility.decode(dataHandlerMulti.getInputStream(), contentTransferEncoding);
							info.addBodyPart(MailUtil.streamToString(stream, charset), MIMETYPE_TEXT_HTML);
						}
					}
				}

				else if (StringUtils.isNotEmpty(name)) {
					setInfoAttachment(dataHandlerMulti, idAttachment, name, mimeType, contentDisposition, contentTransferEncoding);
				}

			}

		}

	}

	/**
	 * @param dataHandlerMulti
	 * @param idAttachment
	 * @param name
	 * @param mimeType
	 * @param contentDisposition
	 * @param contentTransferEncoding
	 */
	protected void setInfoAttachment(final DataHandler dataHandlerMulti, final String idAttachment, final String name, final String mimeType,
			String contentDisposition, String contentTransferEncoding) {
		// caso base
		AttachmentInfo attachmentInfo = new AttachmentInfo();
		attachmentInfo.setDataHandler(dataHandlerMulti);
		attachmentInfo.setContentTransferEncoding(contentTransferEncoding);
		attachmentInfo.setDisposition(contentDisposition);
		attachmentInfo.setMimeType(mimeType);
		attachmentInfo.setName(name);
		attachmentInfo.setId(idAttachment);
		// aggiungo alla lista di allegati
		this.attachments.add(attachmentInfo);
	}

	public DeliveryStatus getAttachmentDeliveryStatusNotification() {
		return attachmentDeliveryStatusNotification;
	}

	public void setAttachmentDeliveryStatusNotification(DeliveryStatus attachmentDeliveryStatusNotification) {
		this.attachmentDeliveryStatusNotification = attachmentDeliveryStatusNotification;
	}

}