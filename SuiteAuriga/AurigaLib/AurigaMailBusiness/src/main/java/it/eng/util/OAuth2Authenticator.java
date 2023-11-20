/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package it.eng.util;

import com.sun.mail.imap.IMAPStore;
import com.sun.mail.imap.IMAPSSLStore;
import com.sun.mail.smtp.SMTPTransport;

import it.eng.aurigamailbusiness.sender.AccountConfigKey;
import it.eng.office365.AccessToken;
import it.eng.office365.AccessTokenBean;
import it.eng.util.oauth2.OAuth2SaslClientFactory;


import java.security.Provider;
import java.security.Security;
import java.util.Properties;
import java.util.logging.Logger;

import javax.mail.Address;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.UIDFolder;
import javax.mail.URLName;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;


/**
 * Performs OAuth2 authentication.
 *
 * <p>Before using this class, you must call {@code initialize} to install the
 * OAuth2 SASL provider.
 */
public class OAuth2Authenticator {
  private static final Logger logger =
      Logger.getLogger(OAuth2Authenticator.class.getName());
  private Folder folder;
  ;
  public static final class OAuth2Provider extends Provider {
    private static final long serialVersionUID = 1L;

    public OAuth2Provider() {
      super("Google OAuth2 Provider", 1.0,
            "Provides the XOAUTH2 SASL Mechanism");
      put("SaslClientFactory.XOAUTH2",
          "it.eng.util.oauth2.OAuth2SaslClientFactory");
    }
  }

  /**
   * Installs the OAuth2 SASL provider. This must be called exactly once before
   * calling other methods on this class.
   */
  public static void initialize() {
    Security.addProvider(new OAuth2Provider());
  }

  /**
   * Connects and authenticates to an IMAP server with OAuth2. You must have
   * called {@code initialize}.
   *
   * @param host Hostname of the imap server, for example {@code
   *     imap.googlemail.com}.
   * @param port Port of the imap server, for example 993.
   * @param userEmail Email address of the user to authenticate, for example
   *     {@code oauth@gmail.com}.
   * @param oauthToken The user's OAuth token.
   * @param debug Whether to enable debug logging on the IMAP connection.
   *
   * @return An authenticated IMAPStore that can be used for IMAP operations.
   */
  public IMAPStore connectToImap(String host,
                                        int port,
                                        final String shared,
                                        String oauthToken,
                                        boolean debug) throws Exception {
    Properties props = new Properties();
    String protocol = "imaps";
    String file = "INBOX";
    props.put(AccountConfigKey.IMAPS_SASL_ENABLE.keyname(), "true");
    props.put(AccountConfigKey.IMAPS_SASL_MECHANISM.keyname(), "XOAUTH2");
    props.put(AccountConfigKey.IMAPS_SASL_MECHANISM_OAUTH2_OAUTHTOKEN.keyname(), oauthToken);
    final String emptyPassword = "";
    
    Session session = Session.getInstance(props,
            new javax.mail.Authenticator() {
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(shared, emptyPassword);
                }
            });
    session.setDebug(debug);
    URLName url = new URLName(protocol, host, 993, file, shared, emptyPassword);
    IMAPSSLStore store  = (IMAPSSLStore) session.getStore(url);
    store.connect();
    return store;
  }

  /**
   * Connects and authenticates to an SMTP server with OAuth2. You must have
   * called {@code initialize}.
   *
   * @param host Hostname of the smtp server, for example {@code
   *     smtp.googlemail.com}.
   * @param port Port of the smtp server, for example 587.
   * @param userEmail Email address of the user to authenticate, for example
   *     {@code oauth@gmail.com}.
   * @param oauthToken The user's OAuth token.
   * @param debug Whether to enable debug logging on the connection.
   *
   * @return An authenticated SMTPTransport that can be used for SMTP
   *     operations.
   */
  public SMTPTransport connectToSmtp(String host,
                                            int port,
                                            String userEmail,
                                            String oauthToken,
                                            boolean debug) throws Exception {
    Properties props = new Properties();
    props.put("mail.smtp.starttls.enable", "true");
    props.put("mail.smtp.starttls.required", "true");
    props.put("mail.smtp.sasl.enable", "true");
    props.put("mail.smtp.sasl.mechanisms", "XOAUTH2");
    props.put(OAuth2SaslClientFactory.OAUTH_TOKEN_PROP, oauthToken);
    Session session = Session.getInstance(props);
    session.setDebug(debug);

    final URLName unusedUrlName = null;
    SMTPTransport transport = new SMTPTransport(session, unusedUrlName);
    // If the password is non-null, SMTP tries to do AUTH LOGIN.
    final String emptyPassword = "";
    transport.connect(host, port, userEmail, emptyPassword);
    
    Message message = new MimeMessage(session);
    message.setFrom(new InternetAddress(""));
    message.setRecipients(
            Message.RecipientType.TO,
            InternetAddress.parse("")
    );
    message.setSubject("Testing OAUTH office 365");
    message.setText("Dear Mail Crawler,"
            + "\n\n Please do not spam my email!");

    Transport.send(message);

    System.out.println("Done");
    
    return transport;
  }


  public int getMessageCount() {
		int messageCount = 0;
		try {
			messageCount = folder.getMessageCount();
		} catch (MessagingException me) {
			me.printStackTrace();
		}
		return messageCount;
	}
  public Message[] getMessages() throws MessagingException {
		return folder.getMessages();
	}

public OAuth2Authenticator() {
	super();
	// TODO Auto-generated constructor stub
}
  public void getMail(IMAPStore imapStore)
  {
	  try {
		folder= imapStore.getFolder("Inbox");
		folder.open(Folder.READ_WRITE);
	} catch (MessagingException e3) {
		// TODO Auto-generated catch block
		e3.printStackTrace();
	}
	  int messageCount = getMessageCount();

		//just for tutorial purpose
		if (messageCount > 5)
			messageCount = 40;
		Message[] messages;
		try {
			messages = getMessages();
		
		for (int i = 0; i < messages.length; i++) {
			String subject = "";
			int msgID = 0;
			try {
				if (messages[i].getSubject() != null)
					try {
						subject = messages[i].getSubject();
						msgID =messages[i].getMessageNumber();
						long uid = ((UIDFolder) messages[i].getFolder()).getUID(messages[i]);
						System.out.println("subject: " + subject );
						System.out.println("uid "+uid +" msgID:"+msgID);
						
					} catch (MessagingException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
			} catch (MessagingException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			try {
				Address[] fromAddress = messages[i].getFrom();
				System.out.println("from: " + fromAddress[0].toString());
			} catch (MessagingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
	}} catch (MessagingException e2) {
		// TODO Auto-generated catch block
		e2.printStackTrace();
	}
  }

  
  
}
