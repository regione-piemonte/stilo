package it.eng.simplesendermail.service;

import it.eng.simplesendermail.service.annotation.SmtpAnnotation;
import it.eng.simplesendermail.service.bean.AttachmentMailToSendBean;
import it.eng.simplesendermail.service.bean.ConfiguredAccount;
import it.eng.simplesendermail.service.bean.DummyMailToSendBean;
import it.eng.simplesendermail.service.bean.MailToSendBean;
import it.eng.simplesendermail.service.bean.ResultSendMail;
import it.eng.simplesendermail.service.bean.SmtpSenderBean;
import it.eng.spring.utility.SpringAppContext;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import javax.mail.Message.RecipientType;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.apache.commons.beanutils.BeanUtilsBean2;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;

public class SimpleSenderMail {

	private static Logger mLogger = Logger.getLogger(SimpleSenderMail.class);

	/**
	 * Invia una mail a partire dal {@link MailToSendBean} ricevuto in ingresso
	 * da cui ricava tutte le informazioni
	 * @param pMailToSendBean
	 * @return
	 */
	public ResultSendMail simpleSendMail(MailToSendBean pMailToSendBean){
		mLogger.debug("Start simpleSendMail");
		Properties propertiesaccount = null;
		try {
			mLogger.debug("Start createPropertiesAccount");
			propertiesaccount = createPropertiesAccount(pMailToSendBean.getSmptSenderBean());
		} catch (Exception e) {
			String error = "Errore nella creazione dell'account smtp: " + e.getMessage();
			mLogger.error(error, e);
			return manageError(error);
		}
		Session session = null;
		String user = null;
		String psw = null;
		if (StringUtils.isNotEmpty(pMailToSendBean.getSmptSenderBean().getUsernameAccount())) {
			user=pMailToSendBean.getSmptSenderBean().getUsernameAccount();
		}
		if (StringUtils.isNotEmpty(pMailToSendBean.getSmptSenderBean().getUsernamePassword())) {
			psw=pMailToSendBean.getSmptSenderBean().getUsernamePassword();
		} 
		
		//JavaMailSenderImpl mailsender = createJavaMailSender(pMailToSendBean,propertiesaccount);
		if(user!=null && psw !=null)
		{	
		final String username = user;
        final String password = psw;
		session = Session.getInstance(propertiesaccount,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                    }
                });
		}
		else
		{	
		 session = Session.getDefaultInstance(propertiesaccount);
		}
		MimeMessage lMimeMessage = null;
		try {
			lMimeMessage = creaMimeMessageSession(session,pMailToSendBean);//creaMimeMessage(mailsender, pMailToSendBean);
		} catch (Exception e) {
			String error = "Impossibile creare il messaggio: " + e.getMessage();
			mLogger.error(error, e);
			return manageError(error);
		}
		String messageId;
		try {
			Transport.send(lMimeMessage);
			//mailsender.send(lMimeMessage);
			messageId = getMessageID(lMimeMessage);
		} catch (Exception e) {
			String error = "Impossibile inviare il messaggio: " + e.getMessage();
			mLogger.error(error, e);
			return manageError(error);
		}
		if (messageId == null || messageId.trim().equals("")){
			String error = "Impossibile inviare il messaggio: il messageId ritornato � nullo";
			return manageError(error);
		}
		ResultSendMail lResultSendMail = new ResultSendMail();
		lResultSendMail.setInviata(true);
		return lResultSendMail;
	}

	protected JavaMailSenderImpl createJavaMailSender(
			MailToSendBean pMailToSendBean, Properties propertiesaccount) {
		JavaMailSenderImpl mailsender = new JavaMailSenderImpl();
		mailsender.setJavaMailProperties(propertiesaccount);
		mailsender.setHost(pMailToSendBean.getSmptSenderBean().getSmtpEndpoint());
		mailsender.setPort(pMailToSendBean.getSmptSenderBean().getSmtpPort());
		if (StringUtils.isNotEmpty(pMailToSendBean.getSmptSenderBean().getUsernameAccount())) {
			mailsender.setUsername(pMailToSendBean.getSmptSenderBean().getUsernameAccount());
		}
		if (StringUtils.isNotEmpty(pMailToSendBean.getSmptSenderBean().getUsernamePassword())) {
			mailsender.setPassword(pMailToSendBean.getSmptSenderBean().getUsernamePassword());
		}
		return mailsender;
	}
	
	public ResultSendMail asyncSimpleSendMail(MailToSendBean pMailToSendBean){
		mLogger.debug("Start simpleSendMail");
		Properties propertiesaccount = null;
		try {
			mLogger.debug("Start createPropertiesAccount");
			propertiesaccount = createPropertiesAccount(pMailToSendBean.getSmptSenderBean());
		} catch (Exception e) {
			String error = "Errore nella creazione dell'account smtp: " + e.getMessage();
			mLogger.error(error, e);
			return manageError(error);
		}
		JavaMailSenderImpl mailsender = createJavaMailSender(pMailToSendBean,
				propertiesaccount);
		MimeMessage lMimeMessage = null;
		try {
			lMimeMessage = creaMimeMessage(mailsender, pMailToSendBean);
		} catch (Exception e) {
			String error = "Impossibile creare il messaggio: " + e.getMessage();
			mLogger.error(error, e);
			return manageError(error);
		}
		ThreadSimpleSenderMail lThreadSimpleSenderMail = new ThreadSimpleSenderMail(mailsender, lMimeMessage);
		Thread lThread = new Thread(lThreadSimpleSenderMail);
		lThread.start();
		ResultSendMail lResultSendMail = new ResultSendMail();
		lResultSendMail.setInviata(true);
		return lResultSendMail;
	}

	public static String getMessageID(MimeMessage lMimeMessage) throws MessagingException {
		if (lMimeMessage.getMessageID() == null) {
			return null;
		} else {
			return (StringUtils.remove(StringUtils.remove(lMimeMessage.getMessageID(), "<"), ">"));
		}
	}
	private MimeMessage creaMimeMessageSession(Session session,
			MailToSendBean pMailToSendBean) throws Exception {
		MimeMessage message = new MimeMessage(session);
		// VERIFICO CHE BODY e SUBJECT NON SIANO NULLI
		if (pMailToSendBean.getSubject() == null) {
			pMailToSendBean.setSubject("");
		}
		if (pMailToSendBean.getBody() == null) {
			pMailToSendBean.setBody("");
		}
		MimeMessageHelper helper;
		if (pMailToSendBean.getSmptSenderBean().getIsPec()) {
			message.setHeader("X-TipoRicevuta", "completa");
		}
		// se desidero la ricevuta di ritorno la inserisco
		if (pMailToSendBean.isConfermaLettura()) {
			message.setHeader("Return-Receipt-To", pMailToSendBean.getFrom());
			message.setHeader("Disposition-Notification-To", pMailToSendBean.getFrom());
		}
		helper = new MimeMessageHelper(message, true, "UTF-8");
		// Setto l'indirizzo from
		helper.setFrom(pMailToSendBean.getFrom());
		// Setto il subject
		helper.setSubject(pMailToSendBean.getSubject());
		// Data di invio
		helper.setSentDate(new Date());
		// Setto il corpo della mail
		boolean ishtml = false;
		helper.setText(pMailToSendBean.getBody(), pMailToSendBean.isHtml());
		// Setto gli attachments
		if (pMailToSendBean.getAttachmentMailToSendBeans() != null) {
			for (AttachmentMailToSendBean attachment : pMailToSendBean.getAttachmentMailToSendBeans()) {
				helper.addAttachment(attachment.getFilename(), new ByteArrayResource(attachment.getContent()));
			}

		}
		List<InternetAddress> internetAddressTo = new ArrayList<InternetAddress>();

		List<InternetAddress> internetAddressCc = new ArrayList<InternetAddress>();

		List<InternetAddress> internetAddressBcc = new ArrayList<InternetAddress>();

		if (pMailToSendBean.getAddressTo() != null) {
			for (String addressto : pMailToSendBean.getAddressTo()) {
				internetAddressTo.add(new InternetAddress(addressto));
			}
		}
		if (pMailToSendBean.getAddressCc() != null) {
			for (String addresscc : pMailToSendBean.getAddressCc()) {
				internetAddressCc.add(new InternetAddress(addresscc));
			}
		}
		if (pMailToSendBean.getAddressBcc() != null) {
			for (String addressbcc : pMailToSendBean.getAddressBcc()) {
				internetAddressBcc.add(new InternetAddress(addressbcc));
			}
		}
		message.setRecipients(RecipientType.TO, internetAddressTo.toArray(new InternetAddress[0]));
		message.setRecipients(RecipientType.CC, internetAddressCc.toArray(new InternetAddress[0]));
		message.setRecipients(RecipientType.BCC, internetAddressBcc.toArray(new InternetAddress[0]));
		return message;
	}
	private MimeMessage creaMimeMessage(JavaMailSenderImpl mailsender,
			MailToSendBean pMailToSendBean) throws Exception {
		MimeMessage message = mailsender.createMimeMessage();
		// VERIFICO CHE BODY e SUBJECT NON SIANO NULLI
		if (pMailToSendBean.getSubject() == null) {
			pMailToSendBean.setSubject("");
		}
		if (pMailToSendBean.getBody() == null) {
			pMailToSendBean.setBody("");
		}
		MimeMessageHelper helper;
		if (pMailToSendBean.getSmptSenderBean().getIsPec()) {
			message.setHeader("X-TipoRicevuta", "completa");
		}
		// se desidero la ricevuta di ritorno la inserisco
		if (pMailToSendBean.isConfermaLettura()) {
			message.setHeader("Return-Receipt-To", pMailToSendBean.getFrom());
			message.setHeader("Disposition-Notification-To", pMailToSendBean.getFrom());
		}
		helper = new MimeMessageHelper(message, true, "UTF-8");
		// Setto l'indirizzo from
		helper.setFrom(pMailToSendBean.getFrom());
		// Setto il subject
		helper.setSubject(pMailToSendBean.getSubject());
		// Data di invio
		helper.setSentDate(new Date());
		// Setto il corpo della mail
		boolean ishtml = false;
		helper.setText(pMailToSendBean.getBody(), pMailToSendBean.isHtml());
		// Setto gli attachments
		if (pMailToSendBean.getAttachmentMailToSendBeans() != null) {
			for (AttachmentMailToSendBean attachment : pMailToSendBean.getAttachmentMailToSendBeans()) {
				helper.addAttachment(attachment.getFilename(), new ByteArrayResource(attachment.getContent()));
			}

		}
		List<InternetAddress> internetAddressTo = new ArrayList<InternetAddress>();

		List<InternetAddress> internetAddressCc = new ArrayList<InternetAddress>();

		List<InternetAddress> internetAddressBcc = new ArrayList<InternetAddress>();

		if (pMailToSendBean.getAddressTo() != null) {
			for (String addressto : pMailToSendBean.getAddressTo()) {
				internetAddressTo.add(new InternetAddress(addressto));
			}
		}
		if (pMailToSendBean.getAddressCc() != null) {
			for (String addresscc : pMailToSendBean.getAddressCc()) {
				internetAddressCc.add(new InternetAddress(addresscc));
			}
		}
		if (pMailToSendBean.getAddressBcc() != null) {
			for (String addressbcc : pMailToSendBean.getAddressBcc()) {
				internetAddressBcc.add(new InternetAddress(addressbcc));
			}
		}
		message.setRecipients(RecipientType.TO, internetAddressTo.toArray(new InternetAddress[0]));
		message.setRecipients(RecipientType.CC, internetAddressCc.toArray(new InternetAddress[0]));
		message.setRecipients(RecipientType.BCC, internetAddressBcc.toArray(new InternetAddress[0]));
		return message;
	}

	private ResultSendMail manageError(String error) {
		ResultSendMail lResultSendMail = new ResultSendMail();
		lResultSendMail.setInviata(false);
		lResultSendMail.setErrori(Arrays.asList(new String[]{error}));
		return lResultSendMail;
	}

	//Crea il properties con le informazioni dell'account
	private Properties createPropertiesAccount(SmtpSenderBean smptSenderBean) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
		Properties propertiesaccount = new Properties();
		Field[] lFields = SmtpSenderBean.class.getDeclaredFields();
		for (Field lField : lFields){
			SmtpAnnotation lSmtpAnnotation = lField.getAnnotation(SmtpAnnotation.class);
			String lStrProperty = BeanUtilsBean2.getInstance().getProperty(smptSenderBean, lField.getName());
			mLogger.debug(lSmtpAnnotation.smptPropertyName()+"--"+lStrProperty);
			propertiesaccount.setProperty(lSmtpAnnotation.smptPropertyName(), lStrProperty);

		}
		return propertiesaccount;
	}
	
	public ResultSendMail sendFromConfigured(DummyMailToSendBean lDummyMailToSendBean, String pStringStrConfiguredAccount) throws IllegalAccessException, InvocationTargetException{
		ConfiguredAccount lConfiguredAccount = SpringAppContext.getContext().getBean(ConfiguredAccount.class);
		SmtpSenderBean lSenderBean = lConfiguredAccount.getAccounts().get(pStringStrConfiguredAccount);
		MailToSendBean lMailToSendBean = new MailToSendBean();
		BeanUtilsBean2.getInstance().copyProperties(lMailToSendBean, lDummyMailToSendBean);
		
		lMailToSendBean.setSmptSenderBean(lSenderBean);
		return simpleSendMail(lMailToSendBean);
		
	} 
	
	public ResultSendMail asyncSendFromConfigured(DummyMailToSendBean lDummyMailToSendBean, String pStringStrConfiguredAccount) throws IllegalAccessException, InvocationTargetException{
		ConfiguredAccount lConfiguredAccount = SpringAppContext.getContext().getBean(ConfiguredAccount.class);
		SmtpSenderBean lSenderBean = lConfiguredAccount.getAccounts().get(pStringStrConfiguredAccount);
		MailToSendBean lMailToSendBean = new MailToSendBean();
		BeanUtilsBean2.getInstance().copyProperties(lMailToSendBean, lDummyMailToSendBean);
		
		lMailToSendBean.setSmptSenderBean(lSenderBean);
		return asyncSimpleSendMail(lMailToSendBean);
		
	} 
}
