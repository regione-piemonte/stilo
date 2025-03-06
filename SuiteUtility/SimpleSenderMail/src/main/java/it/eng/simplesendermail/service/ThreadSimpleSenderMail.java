package it.eng.simplesendermail.service;

import javax.mail.internet.MimeMessage;

import org.apache.log4j.Logger;
import org.springframework.mail.javamail.JavaMailSenderImpl;

public class ThreadSimpleSenderMail implements Runnable {

	private JavaMailSenderImpl mJavaMailSenderImpl;
	private MimeMessage mMimeMessage;
	private static Logger mLogger = Logger.getLogger(ThreadSimpleSenderMail.class);
	
	public ThreadSimpleSenderMail(JavaMailSenderImpl pJavaMailSenderImpl, MimeMessage pMimeMessage) {
		this.mJavaMailSenderImpl = pJavaMailSenderImpl;
		this.mMimeMessage = pMimeMessage;
	}
	
	public void run() {
		String messageId = null;
		try {
			mJavaMailSenderImpl.send(mMimeMessage);
			messageId = SimpleSenderMail.getMessageID(mMimeMessage);
			if (messageId == null || messageId.trim().equals("")){
				String error = "Impossibile inviare il messaggio: il messageId ritornato è nullo";
				throw new Exception(error);
			}
		} catch (Exception e) {
			String error = "Impossibile inviare il messaggio: " + e.getMessage();
			mLogger.error(error, e);
		}
		
		
	}

	
}
