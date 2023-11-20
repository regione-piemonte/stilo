/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.activation.DataHandler;
import javax.mail.Address;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.UIDFolder;
import javax.mail.internet.ContentType;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMessage.RecipientType;
import javax.mail.internet.MimeUtility;

import org.apache.axis.encoding.Base64;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.CharSetUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.xmlbeans.impl.common.XMLChar;
import org.mozilla.universalchardet.UniversalDetector;

import it.eng.aurigamailbusiness.database.utility.ParameterUtility;
import it.eng.aurigamailbusiness.structure.XRicevuta;
import it.eng.aurigamailbusiness.structure.XTipoRicevuta;
import it.eng.aurigamailbusiness.structure.XTrasporto;
import it.eng.aurigamailbusiness.structure.XVerificaSicurezza;

/**
 * Classe di utilità per la mail
 * 
 * @author jravagnan
 * 
 */
public class MailUtil {

	MimeMessage message;

	public static final String REGULAR_EXPRESSION_ADDRESS = "^([_a-zA-Z0-9-]+(\\.[_a-zA-Z0-9-]+)*@[a-zA-Z0-9-]+(\\.[a-zA-Z0-9-]+)*(\\.[a-zA-Z]{2,9}))?$";

	private static Boolean concatenateSender = null;

	static Logger log = LogManager.getLogger(MailUtil.class);

	/**
	 * Converte lo stream in stringa
	 * 
	 * @param stream
	 * @param encoding
	 * @return
	 */
	public static String streamToString(InputStream stream, String encoding) throws Exception {
		InputStream input = null;
		String ret = null;
		try {
			input = IOUtils.toBufferedInputStream(stream);
			ret = IOUtils.toString(input, encoding);
		} catch (Throwable e) {
			// Tento con il canale di default
			ret = IOUtils.toString(input, "ISO-8859-1");
		}
		return ret;
	}

	/**
	 * Ritorna gli indirizzi destinatari
	 * 
	 * @return
	 * @throws MessagingException
	 */
	public List<String> getAddressesTo() throws MessagingException {
		Address[] addressarray = getRecipients(RecipientType.TO);
		List<String> addresslist = new ArrayList<String>();
		if (addressarray != null) {
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
						log.warn("Destinatario non valido: " + address);
					}
					if (StringUtils.isBlank(value)) {
						// provo a utilizzare l'indirizzo direttamente come
						// stringa, può essere un alias
						value = (StringUtils.remove(StringUtils.remove(address.toString(), "<"), ">"));
						value = (StringUtils.remove(StringUtils.remove(address.toString(), "\\\""), "\\\""));
					}
					if (StringUtils.isNotBlank(value)) {
						addresslist.add(value);
					}
				}
			}
		}
		return addresslist;
	}

	/**
	 * Ritorna gli indirizzi destinatari in CC
	 * 
	 * @return
	 * @throws MessagingException
	 */
	public List<String> getAddressesCc() throws MessagingException {
		Address[] addressarray = getRecipients(RecipientType.CC);
		List<String> addresslist = new ArrayList<String>();
		if (addressarray != null) {
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
						log.warn("Destinatario non valido: " + address);
					}
					if (StringUtils.isBlank(value)) {
						// provo a utilizzare l'indirizzo direttamente come
						// stringa, può essere un alias
						value = (StringUtils.remove(StringUtils.remove(address.toString(), "<"), ">"));
						value = (StringUtils.remove(StringUtils.remove(address.toString(), "\\\""), "\\\""));
					}
					if (StringUtils.isNotBlank(value)) {
						addresslist.add(value);
					}
				}
			}
		}
		return addresslist;
	}

	/**
	 * Ritorna gli indirizzi destinatari in BCC
	 * 
	 * @return
	 * @throws MessagingException
	 */
	public List<String> getAddressesBcc() throws MessagingException {
		Address[] addressarray = getRecipients(RecipientType.BCC);
		List<String> addresslist = new ArrayList<String>();
		if (addressarray != null) {
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
						log.warn("Destinatario non valido: " + address);
					}
					if (StringUtils.isBlank(value)) {
						// provo a utilizzare l'indirizzo direttamente come
						// stringa, può essere un alias
						value = (StringUtils.remove(StringUtils.remove(address.toString(), "<"), ">"));
						value = (StringUtils.remove(StringUtils.remove(address.toString(), "\\\""), "\\\""));
					}
					if (StringUtils.isNotBlank(value)) {
						addresslist.add(value);
					}
				}
			}
		}
		return addresslist;
	}

	/**
	 * Recupera il charset dal datahandler
	 * 
	 * @param dataHandler
	 * @return
	 * @throws Exception
	 */
	public static String getCharset(DataHandler dataHandler) throws Exception {
		String charset = null;
		try {
			charset = new ContentType(dataHandler.getContentType()).getParameter("charset");
			if (charset == null) {
				// Provo a recuperarlo dallo stream
				byte[] buf = new byte[4096];
				UniversalDetector detector = new UniversalDetector(null);

				InputStream fis = new ByteArrayInputStream(IOUtils.toByteArray(dataHandler.getInputStream()));

				// (2)
				int nread;
				while ((nread = fis.read(buf)) > 0 && !detector.isDone()) {
					detector.handleData(buf, 0, nread);
				}
				// (3)
				detector.dataEnd();

				// (4)
				charset = detector.getDetectedCharset();

				// (5)
				detector.reset();

				if (charset == null) {
					charset = MimeUtility.getDefaultJavaCharset();
					if (charset == null) {
						charset = "ISO-8859-1";
					}
				}
			} else {
				// RM Aggiunto per rimuovere i caratteri di escape del charset
				charset = CharSetUtils.delete(charset, "_$");
				charset = StringUtils.remove(charset, "ESC");
			}
		} catch (Exception e) {
			charset = "ISO-8859-1";
		}
		return charset;
	}

	/**
	 * Ritorna un'istanza della mail
	 * 
	 * @param message
	 * @return
	 */
	public static MailUtil getInstance(MimeMessage message) {

		MailUtil util = new MailUtil();
		util.message = message;
		try {
			if (concatenateSender == null) {
				concatenateSender = ParameterUtility.isConcatenateSender();
			}
		} catch (Exception e) {
			concatenateSender = false;
		}

		return util;
	}

	/**
	 * @return il messageID ricavandolo dall'header del messaggio. Se non è presente allora viene restituito l'hash SHA1 in base 64 del messaggio
	 * @throws Exception
	 */

	public String getMessageID() throws Exception {
		return this.getMessageID(true);
	}

	/**
	 * @param message
	 * @param getDigestIfEmpty
	 * @return il messageID ricavandolo dall'header del messaggio in input. Se non è presente allora viene restituito l'hash SHA1 in base 64 del messaggio
	 * @throws Exception
	 */

	public static String getMessageID(Message message) throws Exception {
		return MailUtil.getMessageID(message, true);
	}

	/**
	 * @param getDigestIfEmpty
	 *            se impostato a true, in caso il messageID sia vuoto calcola il digest del messaggio con SHA1 in modo da avere un id univoco del messaggio
	 * @return il messageID ricavandolo dall'header del messaggio o eventualmente quello calcolato
	 * @throws MessagingException
	 */

	public static String getMessageID(Message message, Boolean getDigestIfEmpty) throws Exception {
		String messageID = null;
		try {
			if (message.getHeader("Message-ID") != null && message.getHeader("Message-ID").length > 0) {
				messageID = StringUtils.remove(StringUtils.remove(message.getHeader("Message-ID")[0], "<"), ">");
			}			
		} catch (Exception e) {
			log.warn("Errore nel recupero del message id - imposto hash del messaggio come id", e);
		}
		if (StringUtils.isBlank(messageID) && getDigestIfEmpty) {
			log.warn("Message id non presente - imposto hash del messaggio come id");
			try {
				long uid = ((UIDFolder) message.getFolder()).getUID(message);
				log.warn("Message id non presente - uid:"+uid );
			} catch (MessagingException e1) {
				log.warn("MessagingException", e1);
			}
			return MailUtil.getMessageIdBySHAMessage(message);
		} else {
			if(messageID!=null && messageID.equals("0"))
			{
				log.warn("Message id uguale a zero - imposto hash del messaggio come id");
				return getMessageIdBySHAMessage(message);	
			}
			else
			{	
			 return messageID;
			}
		}
	}

	/**
	 * @param getDigestIfEmpty
	 *            se impostato a true, in caso il messageID sia vuoto calcola il digest del messaggio con SHA1 in modo da avere un id univoco del messaggio
	 * @return il messageID ricavandolo dall'header del messaggio o eventualmente quello calcolato
	 * @throws MessagingException
	 */
	public String getMessageID(Boolean getDigestIfEmpty) throws Exception {
		String messageID = null;
		try {
			
			messageID = StringUtils.remove(StringUtils.remove(message.getMessageID(), "<"), ">");
			/*
			 * AURIGA-552
               mail con message-id salvato con spazi finali vengono riscaricate
               30.12.2022
               Il message-id non può essere alterato poichè la cancellazione viene 
               fatta direttamente sulla folder per message id
               msgToDelete = folder.search(new MessageIdSearchTerm(messageId));
			 */
			log.error("messageID [ORIG]: "+messageID);
			//messageID=messageIDOr.replaceAll("\\s+","");
			log.error("messageID [TRIM NO]: "+messageID);
		} catch (Exception e) {
			log.warn("Errore nel recupero del message id: " + message.getMessageID() + " - imposto hash del messaggio come id");
			log.warn("Errore nel recupero del message id: " + message.getSubject()+ " - imposto hash del messaggio come id");
		}
		if (StringUtils.isBlank(messageID) && getDigestIfEmpty) {
			log.warn("Message id non presente - imposto hash del messaggio come id");
			return getMessageIdBySHAMessage(message);
		} else {
			if(messageID!=null && messageID.equals("0"))
			{
				log.warn("Message id uguale a zero - imposto hash del messaggio come id");
				return getMessageIdBySHAMessage(message);	
			}
			else
			{	
			 return messageID;
			}
		}
	}

	/**
	 * Ritorna il sender della mail, cioè l'indirizzo utilizzato per contattare il server SMTP in fase di invio
	 * 
	 * @return
	 * @throws MessagingException
	 */

	public String getSender() throws MessagingException {
		if (message.getSender() != null) {
			return ((InternetAddress) message.getSender()).getAddress();
		}
		return null;
	}

	/**
	 * Ritorna il mittente della mail: restituisce il valore dell'header X-Mittente se valorizzato </br>
	 * in alternativa il valore del from. Se è presente il sender, cioè l'indirizzo usato per contattare il server SMTP allora si concatena al from
	 * 
	 * @return
	 * @throws MessagingException
	 */
	public String getXMittente() throws MessagingException {
		String addressMittente = message.getHeader("X-Mittente", null); // mittente
																		// principale
																		// restituito
																		// dalla
																		// PEC
		if (StringUtils.isBlank(addressMittente)) {
			addressMittente = message.getHeader("From", null);
			if (StringUtils.isNotBlank(addressMittente)) {
				try {
					if (addressMittente.contains("Per conto di")) {
						addressMittente = addressMittente.replaceAll("\"", "");
					} else {
						addressMittente = InternetAddress.parse(addressMittente)[0].getAddress();
						String sender = getSender();
						if (MailUtil.concatenateSender != null && MailUtil.concatenateSender && StringUtils.isNotBlank(sender)
								&& StringUtils.isNotBlank(addressMittente) && !sender.trim().equalsIgnoreCase(addressMittente.trim())) {
							addressMittente = sender + " da " + addressMittente;
						}
					}
				} catch (Exception e) {
					log.warn("Impossibile fare il parsing dell'indirizzo");
				}
			}
		}
		return addressMittente;
	}

	/**
	 * Ritorna la priorità del messaggio, dove 1 HIGH 3 NORMAL 5 LOW
	 * 
	 * @return
	 * @throws MessagingException
	 */
	public String getPriority() throws MessagingException {
		String priority = message.getHeader("X-Priority", null);
		if (priority != null) {
			priority = StringUtils.remove(StringUtils.remove(priority, "<"), ">");
		}
		return priority;
	}

	/**
	 * Ritorna la richiesta di ricevuta lettura se presente
	 * 
	 * @return
	 * @throws MessagingException
	 */
	public String getRichiestaAvvenutaLettura() throws MessagingException {
		String richiestaRicevuta = message.getHeader("Disposition-Notification-To", null);
		if (richiestaRicevuta != null) {
			richiestaRicevuta = StringUtils.remove(StringUtils.remove(richiestaRicevuta, "<"), ">");
		}
		return richiestaRicevuta;
	}

	/**
	 * Ritorna la richiesta di ricezione della mail
	 * 
	 * @return
	 * @throws MessagingException
	 */
	public String getRichiestaRicevuta() throws MessagingException {
		String richiestaRicevuta = message.getHeader("Return-Receipt-To", null);
		if (richiestaRicevuta != null) {
			richiestaRicevuta = StringUtils.remove(StringUtils.remove(richiestaRicevuta, "<"), ">");
		}
		return richiestaRicevuta;
	}

	/**
	 * Ritorna il riferimento al message-id
	 * 
	 * @return
	 * @throws MessagingException
	 */
	public String getXRiferimentoMessageID() throws MessagingException {
		String msgidrif = message.getHeader("X-Riferimento-Message-ID", null);
		if (msgidrif != null) {
			msgidrif = StringUtils.remove(StringUtils.remove(msgidrif, "<"), ">");
		}
		return msgidrif;
	}

	/**
	 * Ritorna il message-id presente nell'header In-Reply-To, identifica le email precedenti a cui questa mail è riferita
	 * 
	 * @return
	 * @throws MessagingException
	 */
	public String getMessageIDInRepyTo() throws MessagingException {
		String msgID = message.getHeader("In-Reply-To", null); // il valore
																// restituito da
																// getheader è
																// già pulito da
																// eventuali
																// spazi
		if (msgID != null) {
			msgID = StringUtils.remove(StringUtils.remove(msgID, "<"), ">");
		}
		return msgID;
	}

	/**
	 * Ritorna il tipo di trasporto
	 * 
	 * @return
	 * @throws MessagingException
	 */
	public XTrasporto getXTrasporto() throws MessagingException {
		return XTrasporto.valueOfValue(message.getHeader("X-Trasporto", null));
	}

	/**
	 * Ritorna la ricevuta
	 * 
	 * @return
	 * @throws MessagingException
	 */
	public XRicevuta getXRicevuta() throws MessagingException {
		return XRicevuta.valueOfValue(message.getHeader("X-Ricevuta", null));
	}

	/**
	 * Ritorna il tipo di ricevuta
	 * 
	 * @return
	 * @throws MessagingException
	 */
	public XTipoRicevuta getXTipoRicevuta() throws MessagingException {
		return XTipoRicevuta.valueOfValue(message.getHeader("X-TipoRicevuta", null));
	}

	/**
	 * Ritorna la verifica di sicurezza
	 * 
	 * @return
	 * @throws MessagingException
	 */
	public XVerificaSicurezza getXVerificaSicurezza() throws MessagingException {
		return XVerificaSicurezza.valueOfValue(message.getHeader("X-VerificaSicurezza", null));
	}

	/**
	 * Recupera la data di ricezione
	 * 
	 * @return
	 * @throws MessagingException
	 */
	public Date getReceiveDate() throws MessagingException {
		return message.getReceivedDate();
	}

	/**
	 * Recupera la data di invio
	 * 
	 * @return
	 * @throws MessagingException
	 */
	public Date getSendDate() throws MessagingException {
		return message.getSentDate();
	}

	/**
	 * Ritorna l'oggetto della mail
	 * 
	 * @return
	 * @throws MessagingException
	 */
	public String getSubject() throws Exception {
		if (message.getSubject() != null) {
			return MimeUtility.decodeText(encondingWordNoSpace(message.getSubject()));
		}
		return "";
	}
	
	/**
	 * Serve per rendere JavaMail più permissivo con gli spazi nella codifica sulle encodingword
	 * 
	 * Sostituisce gli spazi con _
	 * 
	 * Vedere rfc: https://tools.ietf.org/html/rfc2047#section-2
	 * 
	 * @param encodedWordString
	 * @return la stringa di encoding senza spazi per effettuare la decodifica corretta
	 */
	public static String encondingWordNoSpace(String encodedWordString) {
		   if (encodedWordString == null || encodedWordString.equalsIgnoreCase(""))
			   return "";
		   String result = encodedWordString;
		   Pattern p = Pattern.compile("(?<=\\=\\?)[\\s\\S]+?(?=\\?\\=)");
		   Matcher m = p.matcher(result);
		   while (m.find()) {
			   String match = m.group();
			   log.debug("match : " + match);
			   String matchNoSpace = match.replaceAll("\\s", "_");
			   log.debug("matchNoSpace : " + matchNoSpace);
			   result = result.replace(match, matchNoSpace);
		   }
		   return result;
		   
	   }

	/**
	 * Recupera il nome del file in attachments dal DataHandler
	 * 
	 * @param dataHandler
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	/**
	 * Recupera il nome del file in attachments dal DataHandler
	 * 
	 * @param dataHandler
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public static String getAttachmentName(final DataHandler dataHandler) throws UnsupportedEncodingException {
		String name = dataHandler.getName();
		if (name == null) {
			if (dataHandler.getDataSource() != null) {
				name = dataHandler.getDataSource().getName();
			}
		}
		
		
		if (StringUtils.isBlank(name)) {
			name = dataHandler.getContentType();
			if (StringUtils.containsIgnoreCase(name, "message/rfc822")) {
				// Controllo se riesco a recuperare il nome
				String[] splits = StringUtils.split(name, ";");
				for (String split : splits) {
					if (StringUtils.containsIgnoreCase(split, "name=")) {
						String[] token = StringUtils.split(split, "="); 
						if (token.length == 2) {
							name = token[1];
						}else {
							Date date = Calendar.getInstance().getTime();  
							DateFormat dateFormat = new SimpleDateFormat("yyyymmddhhmmss");  
							String strDate = dateFormat.format(date);  
							
							name = "senza_nome_"+strDate+".txt";
						}
						break;
					}
				}
				if (!StringUtils.containsIgnoreCase(name, "postacert.eml")) {
					// l'attachments è una mail la inserisco con un nome fisso
					name = "Attachment_{0}.eml";
				}

			} else if (StringUtils.containsIgnoreCase(name, "Message/Delivery-Status")) {
				name = "DeliveryStatus_{0}.txt";
			} else {
				String[] splits = StringUtils.split(name, ";");
				for (String split : splits) {
					if (StringUtils.containsIgnoreCase(split, "name=")) {
						String[] token = StringUtils.split(split, "="); 
						if (token.length == 2) {
							name = token[1];
						}else {
							Date date = Calendar.getInstance().getTime();  
							DateFormat dateFormat = new SimpleDateFormat("yyyymmddhhmmss");  
							String strDate = dateFormat.format(date);  
							
							name = "senza_nome_"+strDate+".txt";
						}
						break;
					}
				}
			}
		}
		name = MimeUtility.decodeText(encondingWordNoSpace(name));

		if (name == null) {
			name = "Attachments_{0}";
		}
		StringBuilder lStringBuilder = new StringBuilder();
		for (char c : name.toCharArray()) {
			if (XMLChar.isValid(c)) {
				lStringBuilder.append(c);
			}
		}
		return lStringBuilder.toString();
	}

	/**
	 * Recupera gli indirizzi in strict mode a false
	 * 
	 * @param type
	 * @return
	 * @throws MessagingException
	 */
	private Address[] getRecipients(Message.RecipientType type) throws MessagingException {
		try {
			return message.getRecipients(type);
		} catch (Exception e) {
			String s = message.getHeader(type.toString(), ",");
			return (s == null) ? null : InternetAddress.parseHeader(s, false);
		}
	}

	/**
	 * Verifica se la mail è una delivery, controlla solamente la parte dell'header
	 * 
	 * @return
	 * @throws MessagingException
	 */
	public Boolean IsDelivery() throws MessagingException {
		if (message.getContentType() != null) {
			return StringUtils.containsIgnoreCase(message.getContentType(), "report-type=delivery-status");
		} else {
			return false;
		}
	}

	/**
	 * Controllo se il messaggio è una PEC
	 * 
	 * @param message
	 * @return
	 */
	public static boolean isPec(Message message) {
		try {
			if (XTrasporto.valueOfValue(message.getHeader("X-Trasporto")[0]) == XTrasporto.POSTA_CERTIFICATA) {
				return true;
			} else {
				return false;
			}
		} catch (Throwable e) {
			return false;
		}
	}

	public List<String> getAllMessageId() throws Exception {
		List<String> linee = IOUtils.readLines(message.getInputStream());
		List<String> messageIds = new ArrayList<String>();
		for (String lString : linee) {
			if (lString.toLowerCase().startsWith("Message-Id:".toLowerCase())) {
				String messageId = lString.substring("Message-Id:".length()).trim();
				messageId = StringUtils.remove(StringUtils.remove(messageId, "<"), ">");
				messageIds.add(messageId);
			}
		}
		return messageIds;
	}

	/**
	 * Restituisce l'hash SHA1 in base 64 del MimeMessage in input
	 * 
	 * @param message
	 * @return
	 * @throws Exception
	 */

	public static String getMessageIdBySHAMessage(Message message) throws Exception {
		MessageDigest digest = MessageDigest.getInstance("SHA");
		InputStream is = message.getInputStream();
		digest.reset();
		byte[] dataBytes = new byte[1024];
		int nread = 0;
		while ((nread = is.read(dataBytes)) != -1) {
			digest.update(dataBytes, 0, nread);
		}
		byte[] mdbytes = digest.digest();
		String hash = Base64.encode(mdbytes);
		if (log.isDebugEnabled()) {
			log.debug("Message Digest SHA: " + hash);
		}
		return hash;
	}

	/**
	 * Metodo che verifica la validità del MimeMessage ricercando la presenza di header
	 * 
	 * @param mimeMessage
	 * @return
	 */

	public static boolean checkMimeMessage(MimeMessage mimeMessage) {

		Boolean result = false;

		try {

			// recupero le informazioni principali della mail
			// data di invio e mittente della mail dovrebbero essere header
			// obbligatori
			// altrimenti provo a cascata altri header
			// se non ci sono header significa che non è una mail valida

			Date sentDate = mimeMessage.getSentDate();
			if (sentDate == null) {
				String messageId = mimeMessage.getMessageID();
				if (StringUtils.isEmpty(messageId)) {
					Address sender = mimeMessage.getSender();
					if (sender == null) {
						Date receivedDate = mimeMessage.getReceivedDate();
						if (receivedDate == null) {
							String[] language = mimeMessage.getContentLanguage();
							if (language == null || language.length == 0) {
								String subject = mimeMessage.getSubject();
								if (StringUtils.isEmpty(subject)) {
									Address[] from = mimeMessage.getFrom();
									if (from == null || from.length == 0) {
										Address[] recipients = mimeMessage.getAllRecipients();
										if (recipients == null || recipients.length == 0) {
											return StringUtils.isNotEmpty(mimeMessage.getHeader("MIME-Version", null));
										} else {
											return true;
										}
									} else {
										return true;
									}
								} else {
									return true;
								}
							} else {
								return true;
							}
						} else {
							return true;
						}
					}
				} else {
					return true;
				}
			} else {
				return true;
			}
		} catch (Exception exc) {
			log.warn("Eccezione metodo checkMimeMessage", exc);
		}

		return result;

	}

}