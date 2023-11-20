/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.IOException;
import java.security.cert.Certificate;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Set;

import javax.mail.MessagingException;
import javax.mail.internet.InternetHeaders;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.sun.mail.dsn.DeliveryStatus;
import com.sun.mail.dsn.DispositionNotification;
import com.sun.mail.dsn.MultipartReport;
import com.sun.mail.dsn.Report;

import it.eng.aurigamailbusiness.bean.MailAttachmentsBean;
import it.eng.aurigamailbusiness.bean.TEmailMgoBean;
import it.eng.aurigamailbusiness.database.dao.DaoTEmailMgo;
import it.eng.aurigamailbusiness.database.utility.DaoFactory;
import it.eng.aurigamailbusiness.database.utility.send.InputOutput;
import it.eng.aurigamailbusiness.daticert.Postacert;
import it.eng.aurigamailbusiness.utility.XmlFieldUtil;
import it.eng.core.business.TFilterFetch;
import it.eng.util.ListUtil;

/**
 * Bean contenente le informazioni del MimeMessage
 * 
 * @author michele
 * 
 */
public class MessageInfos {

	private Logger log = LogManager.getLogger(MessageInfos.class);

	public static final String RETURN_RECEIPT_ATTACH_THUNDERBIRD = "MDNPart2.txt";
	public static final String RETURN_RECEIPT_ATTACH_OUTLOOK = "message/disposition-notification";
	public static final String HEADER_ORIGINAL_MESSAGE_ID = "Original-Message-ID";
	public static final String HEADER_DISPOSITION = "Disposition";
	public static final String DISPOSITION_DELETED = "deleted";
	public static final String DISPOSITION_DISPLAYED = "displayed";

	/**
	 * Indica se la mail è un probabile spam
	 */
	private Boolean ispam = false;

	/**
	 * Riferimento al file del messaggio
	 */
	// Da modificare
	// Adesso il riferimento interno è l'uri dello storage
	private String uri;

	/**
	 * Validità della uid nella INBOX
	 */
	private Long uidValidity;

	/**
	 * UID del messaggio nella INBOX
	 */

	private Long uid;

	/**
	 * Certificato
	 */
	private Set<Certificate> signatures;

	/**
	 * Daticert.xml
	 */
	private Postacert daticert;

	/**
	 * Body della mail
	 */
	private List<BodyMessageBean> bodyParts;

	/**
	 * Informazioni dell'header
	 */
	private HeaderInfo headerinfo;

	/**
	 * Postercert.eml contenuto nella PEC
	 */
	private MessageInfos postecerteml;

	/**
	 * Attachments della mail
	 */
	private List<MailAttachmentsBean> attachments;

	/**
	 * Eventuali sotto mail in attachments RFC822
	 */
	private List<MessageInfos> subinfos = new ArrayList<>();

	/**
	 * Operation results
	 */
	private LinkedHashMap<String, Object> opresult = new LinkedHashMap<>();

	/**
	 * 
	 */
	private String errorString = "";

	private List<String> allMessageIds;

	/**
	 * Eventuale multipart/report della mail
	 */

	private MultipartReport multipartReport;

	/**
	 * 
	 * Eventuale disposition-notification della mail
	 */

	private DispositionNotification dispositionNotification;

	/**
	 * 
	 * Eventuale disposition-notification allegato alla mail
	 */

	private DispositionNotification attachmentDispositionNotification;

	/**
	 * Eventuale delivery-status notification della mail
	 */

	private DeliveryStatus deliveryStatusNotification;

	/**
	 * Eventuale delivery-status notification allegato alla mail
	 */

	private DeliveryStatus attachmentDeliveryStatusNotification;

	public DispositionNotification getAttachmentDispositionNotification() {
		return attachmentDispositionNotification;
	}

	public void setAttachmentDispositionNotification(DispositionNotification attachmentDispositionNotification) {
		this.attachmentDispositionNotification = attachmentDispositionNotification;
	}

	public List<String> getAllMessageIds() {
		return allMessageIds;
	}

	public void setAllMessageIds(List<String> allMessageIds) {
		this.allMessageIds = allMessageIds;
	}

	public DeliveryStatus getDeliveryStatusNotification() {
		return deliveryStatusNotification;
	}

	public void setDeliveryStatusNotification(DeliveryStatus deliveryStatusNotification) {
		this.deliveryStatusNotification = deliveryStatusNotification;
	}

	public DeliveryStatus getAttachmentDeliveryStatusNotification() {
		return attachmentDeliveryStatusNotification;
	}

	public void setAttachmentDeliveryStatusNotification(DeliveryStatus attachmentDeliveryStatusNotification) {
		this.attachmentDeliveryStatusNotification = attachmentDeliveryStatusNotification;
	}

	public Long getUidValidity() {
		return uidValidity;
	}

	public void setUidValidity(Long uidValidity) {
		this.uidValidity = uidValidity;
	}

	public Long getUid() {
		return uid;
	}

	public void setUid(Long uid) {
		this.uid = uid;
	}

	/**
	 * Ritorna il risultato delle operazioni con prefisso passato in ingresso
	 * 
	 * @param prefix
	 * @return
	 */
	public List<Object> getOpResultWithStartName(String prefix) {
		List<Object> retresult = new ArrayList<>();
		Set<String> keys = opresult.keySet();
		for (String key : keys) {
			if (StringUtils.startsWith(key, prefix)) {
				retresult.add(opresult.get(key));
			}
		}
		return retresult;
	}

	/**
	 * Ritorna le parti della mail come stringa <br>
	 * Se PEC, anomalia o una ricevuta completa restituisce le parti della mail inbustata postacert.eml
	 * 
	 * @return
	 * @throws MessagingException
	 * @throws IOException
	 */
	public String getBodyToStringWithPrincipalMail() throws Exception {
		StringBuilder ret = new StringBuilder();

		if (hasPostacertEml()) {
			if (postecerteml.getBodyParts() != null) {
				for (BodyMessageBean part : postecerteml.getBodyParts()) {
					ret.append(part.getBody());
				}
			}
		} else {
			if (getBodyParts() != null) {
				for (BodyMessageBean part : getBodyParts()) {
					ret.append(part.getBody());
				}
			}
		}
		return ret.toString();
	}

	/**
	 * Ritorna il body della mail come stringa. <br>
	 * Se PEC, anomalia o una ricevuta completa restituisce le parti della mail inbustata postacert.eml <br>
	 * Solo se il corpo è testuale e non HTML
	 * 
	 * @return
	 * @throws MessagingException
	 * @throws IOException
	 */
	public String getBodyTextPlainOnlyWithPrincipalMail() throws Exception {
		StringBuilder ret = new StringBuilder();
		if (hasPostacertEml()) {
			if (postecerteml.getBodyParts() != null) {
				for (BodyMessageBean part : postecerteml.getBodyParts()) {
					appendTextPlainPart(ret, part);
				}
			}
		} else {
			if (getBodyParts() != null) {
				for (BodyMessageBean part : getBodyParts()) {
					appendTextPlainPart(ret, part);
				}
			}
		}
		return ret.toString();
	}

	/**
	 * @param ret
	 * @param part
	 */
	protected void appendTextPlainPart(StringBuilder ret, BodyMessageBean part) {
		if (part.getType().equalsIgnoreCase("text/plain")) {
			ret.append(part.getBody());
		}
	}

	/**
	 * ritorna il body della busta più esterna, se presente e in formato text/plain
	 * 
	 * @return
	 * @throws Exception
	 */
	public String getBodyExternalPlain() throws Exception {
		StringBuilder ret = new StringBuilder();
		if (getBodyParts() != null) {
			for (BodyMessageBean part : getBodyParts()) {
				appendTextPlainPart(ret, part);
			}
		}
		return ret.toString();
	}

	/**
	 * Ritorna il body della busta più esterna, se presente e in formato HTML
	 * 
	 * @return
	 * @throws Exception
	 */
	public String getBodyExternalHtml() {
		StringBuilder ret = new StringBuilder();
		if (getBodyParts() != null) {
			for (BodyMessageBean part : getBodyParts()) {
				appendTextHtmlPart(ret, part);
			}
		}
		return ret.toString();
	}

	/**
	 * @param ret
	 * @param part
	 */
	protected void appendTextHtmlPart(StringBuilder ret, BodyMessageBean part) {
		if (part.getType().equalsIgnoreCase("text/html")) {
			ret.append(part.getBody());
		}
	}

	/**
	 * Ritorna il body della mail come stringa <br>
	 * Se PEC restituisce il body della mail inbustata postacert.eml
	 * 
	 * @return
	 * @throws MessagingException
	 * @throws IOException
	 */
	public String getBodyHtmlWithPrincipalMail() throws Exception {
		StringBuilder ret = new StringBuilder();

		if (hasPostacertEml()) {
			if (postecerteml.getBodyParts() != null) {
				for (BodyMessageBean part : postecerteml.getBodyParts()) {
					appendTextHtmlPart(ret, part);
				}
			}
		} else {
			if (getBodyParts() != null) {
				for (BodyMessageBean part : getBodyParts()) {
					appendTextHtmlPart(ret, part);
				}
			}
		}
		return ret.toString();
	}

	/**
	 * Ritorna la lista dei destinatari in un'unica stringa
	 * 
	 * @return
	 * @throws IOException
	 * @throws MessagingException
	 */
	public String getDestinatariToStringWithPrincipalMail(String separator) throws Exception {
		StringBuilder ret = new StringBuilder();

		if (hasPostacertEml()) {
			if (postecerteml.headerinfo.getDestinatarito() != null) {
				for (String dest : postecerteml.headerinfo.getDestinatarito()) {
					ret.append(StringUtils.remove(StringUtils.remove(dest, "<"), ">") + separator);
				}
			}
		} else {
			if (headerinfo.getDestinatarito() != null) {
				for (String dest : headerinfo.getDestinatarito()) {
					ret.append(StringUtils.remove(StringUtils.remove(dest, "<"), ">") + separator);
				}
			}
		}
		if (ret.length() > separator.length()) {
			return ret.substring(0, ret.length() - separator.length());
		}
		return ret.toString();
	}

	/**
	 * Ritorna la lista dei destinatari in CC in un'unica stringa
	 * 
	 * @return
	 * @throws IOException
	 * @throws MessagingException
	 */
	public String getDestinatariCCStringWithPrincipalMail(String separator) throws Exception {
		String ret = "";

		if (hasPostacertEml()) {
			if (postecerteml.headerinfo.getDestinataricc() != null) {
				for (String dest : postecerteml.headerinfo.getDestinataricc()) {
					ret += (StringUtils.remove(StringUtils.remove(dest, "<"), ">") + separator);
				}
			}
		} else {
			if (headerinfo.getDestinataricc() != null) {
				for (String dest : headerinfo.getDestinataricc()) {
					ret += (StringUtils.remove(StringUtils.remove(dest, "<"), ">") + separator);
				}
			}
		}
		if (ret.length() > separator.length()) {
			ret = ret.substring(0, ret.length() - separator.length());
		}
		return ret;
	}

	/**
	 * Ritorna il mittente della mail del messaggio principale
	 * 
	 * @return
	 */
	public String getMittenteWithPrincipalMail() {
		if (isPec() && !isRicevuta()) {
			if (getDaticert() != null) {
				return getDaticert().getIntestazione().getMittente();
			} else if (getHeaderinfo().getMittente() != null) {
				return getHeaderinfo().getMittente();
			}
		} else if (isAnomaliaPec() && getPostecerteml() != null) {
			if (getPostecerteml().getHeaderinfo().getMittente() != null) {
				return getPostecerteml().getHeaderinfo().getMittente();
			} else if (getPostecerteml().getDaticert() != null) {
				return getPostecerteml().getDaticert().getIntestazione().getMittente();
			}
		} else if (getHeaderinfo().getRicevuta() != null
				&& (getHeaderinfo().getRicevuta() == XRicevuta.AVVENUTA_CONSEGNA
						|| getHeaderinfo().getRicevuta() == XRicevuta.ERRORE_CONSEGNA
						|| getHeaderinfo().getRicevuta() == XRicevuta.PREAVVISO_ERRORE_CONSEGNA)) {
			//Su Parma sono state scaricate due ricevute di avvenuta consegna 
			//che non presentavano valorizzati i dati di certificazione aggiunto controllo puntuale
			if (getDaticert() != null && getDaticert().getDati() !=null ) {
				return XmlFieldUtil.cleanField(getDaticert().getDati().getConsegna());
			} else {
				return getHeaderinfo().getMittente();
			}
		}
		return getHeaderinfo().getMittente();
	}

	/**
	 * Ritorna la priorità della mail
	 * 
	 * @return
	 */
	public String getPriorityWithPrincipalMail() {
		String ret = null;
		if (hasPostacertEml()) {
			if (postecerteml.headerinfo.getPriority() != null) {
				ret = postecerteml.headerinfo.getPriority();
			}
		} else {
			if (headerinfo.getPriority() != null) {
				ret = headerinfo.getPriority();
			}
		}
		return ret;
	}

	/**
	 * Ritorna la richiesta di lettura della mail
	 * 
	 * @return
	 */
	public Boolean getRichiestaAvvenutaLetturaWithPrincipalMail() {
		if (hasPostacertEml()) {
			if (postecerteml.headerinfo.getRichiestaLettura() != null) {
				return postecerteml.headerinfo.getRichiestaLettura();
			}
		} else {
			if (headerinfo.getRichiestaLettura() != null) {
				return headerinfo.getRichiestaLettura();
			}
		}
		return false;
	}

	/**
	 * Indica se il messaggio è un messaggio di PEC verificando se l'header di trasporto è uguale a posta-certificata o se è una ricevuta e sono presenti i dati
	 * di certificazione
	 * 
	 * @return
	 */
	public boolean isPec() {
		if (headerinfo.getTrasporto() == XTrasporto.POSTA_CERTIFICATA || (isRicevuta() && daticert != null)) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Indica se la busta è di tipo anomalia
	 * 
	 * @return
	 */
	public boolean isAnomaliaPec() {
		if (headerinfo.getTrasporto() == XTrasporto.ERRORE) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Indica se il messaggio di PEC è una ricevuta
	 * 
	 * @return
	 */
	public boolean isRicevuta() {
		// rimosso il controllo che postecerteml sia diverso da null, non è
		// detto che il file postacert.eml sia presente nella ricevuta
		// all'invocazione del metodo si effettua il controllo della variabile
		// postecert se occorre utilizzarla
		if (headerinfo.getTrasporto() == null && (headerinfo.getRicevuta() != null || headerinfo.getTiporicevuta() != null)) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Indica se il messaggio di PEC è una ricevuta completa
	 * 
	 * @return
	 */
	public boolean isRicevutaCompleta() {
		if (headerinfo.getTrasporto() == null && (headerinfo.getTiporicevuta() != null && headerinfo.getTiporicevuta().equals(XTipoRicevuta.COMPLETA))) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Ritorna gli attachment di primo livello
	 * 
	 * @return
	 */
	public List<MailAttachmentsBean> getDirectAttachments() {
		List<MailAttachmentsBean> directAttachments = new ArrayList<>();
		extractDirectAttachments(directAttachments, this);
		return directAttachments;
	}

	/**
	 * Ritorna gli attachments della mail principale Se PEC gli attachments della mail inbustata postacert.eml Se non PEC gli attachment della mail Se è una
	 * ricevuta restituisco gli attachment della mail principale
	 * 
	 * @return
	 */
	public List<MailAttachmentsBean> getAttachmentsWithPrincipalMail() {
		List<MailAttachmentsBean> listAttachments = new ArrayList<>();

		if (hasPostacertEml() && postecerteml.getAttachments() != null) {
			// Prendo gli attachment della mail postacert.eml
			listAttachments.addAll(postecerteml.getAttachments());
		} else {
			listAttachments.addAll(getAttachments());
		}
		return listAttachments;
	}

	/**
	 * Metodo che restituisce il message id in reply to della mail principale o della mail imbustata postacert.eml se presente
	 */

	public String getMessageIdInReplyToWithPrincipalMail() {

		if (hasPostacertEml()) {
			return postecerteml.getHeaderinfo().getMessageidInReplyTo();
		} else {
			return getHeaderinfo().getMessageidInReplyTo();
		}

	}

	/**
	 * Metodo che restituisce l'oggetto MultipartReport della mail principale o della mail imbustata postacert.eml se presente
	 */

	public MultipartReport getMultipartReportWithPrincipalMail() {

		if (hasPostacertEml()) {
			return postecerteml.getMultipartReport();
		} else {
			return getMultipartReport();
		}

	}

	/**
	 * metodo che restituisce la disposition notification della mail principale o della mail imbustata postacert.eml se presente
	 */

	public DispositionNotification getDispositionNotificationWithPrincipalMail() {

		if (hasPostacertEml()) {
			return postecerteml.getDispositionNotification();
		} else {
			return getDispositionNotification();
		}

	}

	/**
	 * metodo che restituisce la disposition notification (in attachment) della mail principale o della mail imbustata postacert.eml se presente
	 */

	public DispositionNotification getAttachmentDispositionNotificationWithPrincipalMail() {

		if (hasPostacertEml()) {
			return postecerteml.getAttachmentDispositionNotification();
		} else {
			return getAttachmentDispositionNotification();
		}

	}

	/**
	 * metodo che restituisce la notification di delivery status della mail principale o della mail imbustata postacert.eml se presente
	 */

	public DeliveryStatus getDeliveryStatusNotificationWithPrincipalMail() {

		if (hasPostacertEml()) {
			return postecerteml.getDeliveryStatusNotification();
		} else {
			return getDeliveryStatusNotification();
		}

	}

	/**
	 * metodo che restituisce eventuali delivery status notification in attachment alla mail principale o della mail imbustata postacert.eml se presente
	 */

	public DeliveryStatus getAttachmentDeliveryStatusNotificationWithPrincipalMail() {

		if (hasPostacertEml()) {
			return postecerteml.getAttachmentDeliveryStatusNotification();
		} else {
			return getAttachmentDeliveryStatusNotification();
		}

	}

	/**
	 * Ritorna l'oggetto della mail principale Se PEC gli attachments della mail inbustata postacert.eml Se non PEC gli attachment della mail
	 * 
	 * @return
	 */
	public String getSubjectWithPrincipalMail() {
		if (hasPostacertEml()) {
			// Prendo gli attachment della mail postacert.eml
			return postecerteml.getHeaderinfo().getSubject();
		} else {
			// Prendo gli attachment della mail principale
			return getHeaderinfo().getSubject();
		}
	}

	private void extractDirectAttachments(List<MailAttachmentsBean> list, MessageInfos infos) {
		if (infos != null && infos.getAttachments() != null) {
			list.addAll(infos.getAttachments());
		}
	}

	/**
	 * Aggiungo un bodypart
	 * 
	 * @param bodypart
	 */
	public void addBodyPart(String body, String type) {
		if (bodyParts == null) {
			bodyParts = new ArrayList<>();
		}
		bodyParts.add(new BodyMessageBean(type, body));
	}

	public LinkedHashMap<String, Object> getOpresult() {
		return opresult;
	}

	public void setOpresult(LinkedHashMap<String, Object> opresult) {
		this.opresult = opresult;
	}

	public Set<Certificate> getSignatures() {
		return signatures;
	}

	public void setSignatures(Set<Certificate> signatures) {
		this.signatures = signatures;
	}

	public Postacert getDaticert() {
		return daticert;
	}

	public void setDaticert(Postacert daticert) {
		this.daticert = daticert;
	}

	public List<BodyMessageBean> getBodyParts() {
		return bodyParts;
	}

	public void setBodyParts(List<BodyMessageBean> bodyParts) {
		this.bodyParts = bodyParts;
	}

	public MessageInfos getPostecerteml() {
		return postecerteml;
	}

	public void setPostecerteml(MessageInfos postecerteml) {
		this.postecerteml = postecerteml;
	}

	public HeaderInfo getHeaderinfo() {
		if (headerinfo == null) {
			headerinfo = new HeaderInfo();
		}
		return headerinfo;
	}

	public void setHeaderinfo(HeaderInfo headerinfo) {
		this.headerinfo = headerinfo;
	}

	public List<MailAttachmentsBean> getAttachments() {
		if (attachments == null) {
			attachments = new ArrayList<>();
		}
		return attachments;
	}

	public void setAttachments(List<MailAttachmentsBean> attachments) {
		this.attachments = attachments;
	}

	public List<MessageInfos> getSubinfos() {
		if (subinfos == null) {
			subinfos = new ArrayList<>();
		}
		return subinfos;
	}

	public void setSubinfos(List<MessageInfos> subinfos) {
		this.subinfos = subinfos;
	}

	public void setIspam(Boolean ispam) {
		this.ispam = ispam;
	}

	public Boolean isSpam() {
		if (this.ispam == null) {
			return false;
		} else {
			return this.ispam;
		}
	}

	/**
	 * Metodo che verifica se la tipologia della mail è una delivery status notification
	 * 
	 * @return
	 */

	public Boolean isDeliveryStatusNotification() {

		Boolean result = false;

		try {
			DeliveryStatus deliveryStatus = getDeliveryStatusNotificationWithPrincipalMail();
			MultipartReport lMultipartReport = getMultipartReportWithPrincipalMail();
			DeliveryStatus attachmentDeliveryStatus = getAttachmentDeliveryStatusNotificationWithPrincipalMail();

			if (lMultipartReport != null) {
				Report report = lMultipartReport.getReport();
				if (report instanceof DeliveryStatus) {
					deliveryStatus = (DeliveryStatus) report;
				}
			} else if (attachmentDeliveryStatus != null) {
				deliveryStatus = attachmentDeliveryStatus;
			}

			return deliveryStatus != null;

		} catch (Exception e) {
			log.warn("Eccezione nel metodo isDeliveryStatusNotification", e);
		}

		return result;
	}

	public void setUri(String uri) {
		this.uri = uri;
	}

	public String getUri() {
		return uri;
	}

	public String getErrorString() {
		return errorString;
	}

	public void setErrorString(String errorString) {
		this.errorString = errorString;
	}

	public MultipartReport getMultipartReport() {
		return multipartReport;
	}

	public void setMultipartReport(MultipartReport multipartReport) {
		this.multipartReport = multipartReport;
	}

	public DispositionNotification getDispositionNotification() {
		return dispositionNotification;
	}

	public void setDispositionNotification(DispositionNotification dispositionNotification) {
		this.dispositionNotification = dispositionNotification;
	}

	/**
	 * Metodo che verifica se la mail è una notifica di cancellazione controllando la disposition-notification del messaggio o dell'oggetto multipartReport
	 * ricavato tramiti i metodi {@link #getMultipartReportWithPrincipalMail} e {@link #getDispositionNotificationWithPrincipalMail} della mail e la presenza di
	 * "deleted" nella stringa
	 * 
	 * @return True se è una notifca di cancellazione
	 */

	public Boolean isDispositionNotificationDelete() {

		Boolean result = false;

		try {
			DispositionNotification notification = getDispositionNotificationWithPrincipalMail();
			MultipartReport multipartReport = getMultipartReportWithPrincipalMail();
			// DispositionNotification attachmentNotification =
			// getAttachmentDispositionNotificationWithPrincipalMail();

			if (multipartReport != null) {
				Report report = multipartReport.getReport();
				if (report instanceof DispositionNotification) {
					notification = (DispositionNotification) report;
				}
				// } else if (attachmentNotification != null){
				// notification = attachmentNotification;
			}

			if (notification != null) {
				InternetHeaders headerNotification = notification.getNotifications();
				String dispositionHeader = headerNotification.getHeader(HEADER_DISPOSITION, null);
				if (StringUtils.isNotEmpty(dispositionHeader)) {
					// verifico se nell'header è presente "deleted"
					result = StringUtils.contains(dispositionHeader.toLowerCase(), DISPOSITION_DELETED.toLowerCase());
				}
			}

		} catch (Exception e) {
			log.warn("Eccezione nel metodo isDispositionNotificationDelete", e);
		}

		return result;
	}

	/**
	 * Metodo che verifica se la mail è una notifica di lettura controllando la disposition-notification del messaggio o dell'oggetto multipartReport ricavato
	 * tramiti i metodi {@link #getMultipartReportWithPrincipalMail} e {@link #getDispositionNotificationWithPrincipalMail} della mail e la presenza di
	 * "displayed" nella stringa e di "Original-Message-id" in esso o eventualmente dell'header "In-Reply-To" nella mail principale. Per garantire che sia
	 * effettivamente una ricevuta di lettura è necessario che sia presente in database l'id originale della mail, ricavato dagli header return True se è una
	 * notifca di lettura ed è presente l'id della mail originale nella mail
	 */

	public Boolean isDispositionNotificationDisplayed() {

		// se è una notifica di cancellazione allora non può essere una notifica
		// di lettura
		// occorre inserire questo controllo altrimenti se è una notifica di
		// cancellazione
		// il metodo potrebbe restituire true visto che trovo un
		// disposition-notification e l'header original-message-id
		if (isDispositionNotificationDelete()) {
			return false;
		}

		Boolean result = false;
		String newOriginalMessageId = null;

		try {

			DispositionNotification notification = getDispositionNotificationWithPrincipalMail();
			MultipartReport lMultipartReport = getMultipartReportWithPrincipalMail();
			// DispositionNotification attachmentNotification =
			// getAttachmentDispositionNotificationWithPrincipalMail();
			String idMessaggioInReplyTo = getMessageIdInReplyToWithPrincipalMail();

			try {
				if (lMultipartReport != null) {
					Report report = lMultipartReport.getReport();
					if (report instanceof DispositionNotification) {
						notification = (DispositionNotification) report;
					}
					// } else if (attachmentNotification != null){
					// notification = attachmentNotification;
				}

				if (notification != null) {
					InternetHeaders headerNotification = notification.getNotifications();
					String dispositionHeader = headerNotification.getHeader(HEADER_DISPOSITION, null);
					if (StringUtils.isNotEmpty(dispositionHeader)) {
						// verifico se nell'header è presente "displayed"
						if (StringUtils.contains(dispositionHeader.toLowerCase(), DISPOSITION_DISPLAYED.toLowerCase())) {
							// in questo caso deve esserci anche il tag
							// "Original-Message-id"
							String originalHeader = headerNotification.getHeader(HEADER_ORIGINAL_MESSAGE_ID, null);
							if (StringUtils.isEmpty(originalHeader)) {
								// header non presente
								// ricerco nella mail principale l'header
								// "In-reply-to", già pulito da eventuali
								// caratteri "<" e ">"
								if (StringUtils.isNotEmpty(idMessaggioInReplyTo)) {
									newOriginalMessageId = idMessaggioInReplyTo;
								}
							} else {
								// estraggo l'id dal message id dell'header
								// non serve fare il trim perchè getheader
								// restituisce già il valore pulito da spazi
								newOriginalMessageId = StringUtils.remove(StringUtils.remove(originalHeader, "<"), ">");
							}
						}
					}
				}
			} catch (Exception e) {
				log.warn("Eccezione nel metodo isDispositionNotificationDisplayed - verifica dispositionNotification", e);
			}
			// se ho individuato l'id del messaggio originale allora è una
			// notifica di lettura e si puà categorizzare come tale
			result = (StringUtils.isNotEmpty(newOriginalMessageId));
		} catch (Exception e) {
			log.warn("Eccezione nel metodo isDispositionNotificationDisplayed", e);
		}

		return result;
	}

	/**
	 * Per utilizzare questo metodo si dà per scontato che si sia precedentemente verificato che la mail sia una notifica di lettura con il metodo
	 * {@link #isDispositionNotificationDisplayed} Il metodo che restituisce l'id della mail originale per creare la relazione con la mail di notifica di
	 * lettura controllando la disposition-notification del messaggio o dell'oggetto multipartReport ricavato tramiti i metodi
	 * {@link #getMultipartReportWithPrincipalMail} e {@link #getDispositionNotificationWithPrincipalMail} della mail e la presenza di "displayed" nella stringa
	 * e di "Original-Message-id" in esso o eventualmente dell'header "In-Reply-To" nella mail principale. Se presenti si ricava l'id originale della mail, per
	 * categorizzare correttamente la notifica infatti è necessario che sia presente la relazione con la mail originale
	 * 
	 * @param idCasella:
	 *            id account della casella
	 * @return True se è una notifca di lettura ed è stato individuato in database l'id della mail originale
	 */

	public String getOriginalMessageId(String idAccountCasella) {

		String newOriginalMessageId = null;

		try {
			DispositionNotification notification = getDispositionNotificationWithPrincipalMail();
			MultipartReport lMultipartReport = getMultipartReportWithPrincipalMail();
			// DispositionNotification attachmentNotification =
			// getAttachmentDispositionNotificationWithPrincipalMail();
			String idMessaggioInReplyTo = getMessageIdInReplyToWithPrincipalMail();
			if (lMultipartReport != null) {
				Report report = lMultipartReport.getReport();
				if (report instanceof DispositionNotification) {
					notification = (DispositionNotification) report;
				}
				// } else if (attachmentNotification != null) {
				// notification = attachmentNotification;
			}

			if (notification != null) {
				InternetHeaders headerNotification = notification.getNotifications();
				String dispositionHeader = headerNotification.getHeader(HEADER_DISPOSITION, null);
				if (StringUtils.isNotEmpty(dispositionHeader)) {
					// verifico se nell'header è presente "displayed"
					if (StringUtils.contains(dispositionHeader.toLowerCase(), DISPOSITION_DISPLAYED.toLowerCase())) {
						// in questo caso deve esserci anche il tag
						// "Original-Message-id"
						String originalHeader = headerNotification.getHeader(HEADER_ORIGINAL_MESSAGE_ID, null);
						if (StringUtils.isEmpty(originalHeader)) {
							// header non presente
							// ricerco nella mail principale l'header
							// "In-reply-to", già pulito da eventuali
							// caratteri "<" e ">"
							if (StringUtils.isNotEmpty(idMessaggioInReplyTo)) {
								newOriginalMessageId = idMessaggioInReplyTo;
							}
						} else {
							// estraggo l'id dal message id dell'header
							// non serve fare il trim perchè getheader
							// restituisce già il valore pulito da spazi
							newOriginalMessageId = StringUtils.remove(StringUtils.remove(originalHeader, "<"), ">");
						}
					}
				}
			}

			// se l'id è presente verifico se esiste la mail originale per
			// permettere la creazione della relazione
			if (StringUtils.isNotEmpty(newOriginalMessageId)) {
				DaoTEmailMgo daoTEmail = (DaoTEmailMgo) DaoFactory.getDao(DaoTEmailMgo.class);
				TFilterFetch<TEmailMgoBean> filterFetch = new TFilterFetch<TEmailMgoBean>();
				TEmailMgoBean filter = new TEmailMgoBean();
				filter.setMessageId(newOriginalMessageId);
				filter.setFlgIo(InputOutput.USCITA.getValue());
				if (StringUtils.isNotEmpty(idAccountCasella)) {
					filter.setIdCasella(idAccountCasella);
				}
				filterFetch.setFilter(filter);
				List<TEmailMgoBean> listaEmails = daoTEmail.search(filterFetch).getData();
				if (ListUtil.isNotEmpty(listaEmails)) {
					// trovata una corrispondenza con la mail originale
					return newOriginalMessageId;
				} else {
					filter = new TEmailMgoBean();
					if (StringUtils.isNotEmpty(idAccountCasella)) {
						filter.setIdCasella(idAccountCasella);
					}
					filter.setFlgIo(InputOutput.USCITA.getValue());
					filter.setMessageIdTrasporto(newOriginalMessageId);
					filterFetch.setFilter(filter);
					listaEmails = daoTEmail.search(filterFetch).getData();
					if (ListUtil.isNotEmpty(listaEmails)) {
						// trovata una corrispondenza con la mail originale
						// nell'id trasporto
						return newOriginalMessageId;
					} else {
						log.warn("Mail originale associata all'id: " + newOriginalMessageId + " non trovata");
					}
				}
			}

		} catch (Exception e) {
			log.warn("Eccezione nel metodo getOriginalMessageId", e);
		}

		return null;
	}

	/**
	 * Restituisce {@link Boolean.TRUE} se la mail è una PEC, una anomalia e non è una ricevuta, {@link Boolean.FALSE} negli altri casi
	 * 
	 * @return
	 */

	private Boolean hasPostacertEml() {
		return (isPec() || isAnomaliaPec()) && !isRicevuta() && postecerteml != null;
	}

}