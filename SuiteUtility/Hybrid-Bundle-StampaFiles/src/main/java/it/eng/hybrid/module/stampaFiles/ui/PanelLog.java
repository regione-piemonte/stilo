package it.eng.hybrid.module.stampaFiles.ui;

import it.eng.hybrid.module.stampaFiles.inputFileProvider.DirectUrlFileInputProvider;
import it.eng.hybrid.module.stampaFiles.messages.MessageKeys;
import it.eng.hybrid.module.stampaFiles.messages.Messages;
import it.eng.hybrid.module.stampaFiles.preferences.PreferenceKeys;
import it.eng.hybrid.module.stampaFiles.preferences.PreferenceManager;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.StringTokenizer;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import org.apache.log4j.Logger;

public class PanelLog extends GenericPanel {

	private static final long serialVersionUID = 1L;

	public final static Logger logger = Logger.getLogger(PanelLog.class);
	
	private int widthPanel = 400;
	private int heightPanel = 400;
	
	private JLabel label;
	private JLabel label1;
	private JLabel mailToLabel;
	private JTextField mailToField;
	private JLabel mailSubjectLabel;
	private JTextField mailSubjectField;
	private JLabel mailBodyLabel;
	private JTextArea mailBodyField;
	
	private String smtpServer;
	private boolean useAuthentication;
	private String usernameAccountInvio;
	private String passwordAccountInvio;
	private String attachmentName;
	private String mittente;
	private String mailTo;
	private String mailSubject;
	private String mailBody;
	
	private JButton sendMail;
	private JButton close;
	
	public PanelLog(int widthPanel, int heightPanel) {
		super();
		this.widthPanel = widthPanel;
		this.heightPanel = heightPanel;
	}
	
	public PanelLog() {
		super();
	}
	
	public void show(/* Container container*/){

		windowDialog = new JFrame();
		//windowDialog.setModal(true);

		initialize();
		
		windowDialog.getContentPane().setLayout( new BorderLayout() );
		windowDialog.getContentPane().add( this,BorderLayout.CENTER );
		windowDialog.pack();
        windowDialog.setSize( widthPanel, heightPanel );
		 
        windowDialog.setTitle( Messages.getMessage( MessageKeys.PANEL_INVIA_LOG_TITLE ) );
        
        //windowDialog.setLocationRelativeTo( container );
        windowDialog.setVisible(true);
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setPreferredSize(new Dimension(300, 170));
		
		initDatiInvio();
		
		GridBagConstraints c1 = new GridBagConstraints();
        setLayout(new GridBagLayout());
        
		pane = new JPanel();
        JScrollPane scroll = new JScrollPane(pane);
        scroll.setBorder(BorderFactory.createEmptyBorder());
		pane.setLayout(new GridBagLayout());
		
		GridBagConstraints c = new GridBagConstraints();
		int y=0;
		
		label = aggiungiLabel(pane, label, "label", Messages.getMessage( MessageKeys.PANEL_INVIA_LOG_TEXT), c, 0, y++, 2, 1, 2, GridBagConstraints.LINE_START);
		label1 = aggiungiLabel(pane, label1, "label1", Messages.getMessage( MessageKeys.PANEL_INVIA_LOG_TEXT1), c, 0, y++, 2, 1, 1, GridBagConstraints.LINE_START);
				
		mailToLabel = aggiungiLabel(pane, mailToLabel, "mailToLabel", Messages.getMessage( MessageKeys.PANEL_INVIA_LOG_FIELD_MAILTO ), c, 0, y, 1, 1, 2, GridBagConstraints.LINE_START);
			
		String mailToProperty ="";
		try {
			mailToProperty = PreferenceManager.getString( PreferenceKeys.PROPERTY_MAIL_TO );
		} catch (Exception e2) {
			logger.info("Errore ", e2);
		}
		mailToField = aggiungiFieldTesto( pane, mailToField, 20, "mailToField", mailToProperty, true, c, 1, y++, 1, 2, 2, GridBagConstraints.LINE_START);
		
			
		mailSubjectLabel = aggiungiLabel(pane, mailSubjectLabel, "mailSubjectLabel", Messages.getMessage( MessageKeys.PANEL_INVIA_LOG_FIELD_MAILSUBJECT ), c, 0, y, 1, 1, 2, GridBagConstraints.LINE_START);
			
		String mailSubjectProperty ="";
		try {
			mailSubjectProperty = PreferenceManager.getString( PreferenceKeys.PROPERTY_MAIL_SUBJECT );
		} catch (Exception e2) {
			logger.info("Errore ", e2);
		}
		mailSubjectField = aggiungiFieldTesto( pane, mailSubjectField, 20, "mailSubjectField", mailSubjectProperty, true, c, 1, y++, 1, 2, 2, GridBagConstraints.LINE_START);
		
		mailBodyLabel = aggiungiLabel(pane, mailBodyLabel, "mailBodyLabel", Messages.getMessage( MessageKeys.PANEL_INVIA_LOG_FIELD_MAILBODY ), c, 0, y, 1, 1, 2, GridBagConstraints.LINE_START);
		
		String mailBodyProperty ="";
		try {
			mailBodyProperty = PreferenceManager.getString( PreferenceKeys.PROPERTY_MAIL_BODY );
		} catch (Exception e2) {
			logger.info("Errore ", e2);
		}
		mailBodyField = aggiungiFieldTextArea( pane, mailBodyField, 5, 20, "mailBodyField", mailBodyProperty, true, c, 1, y++, 1, 2, 2, GridBagConstraints.LINE_START);
		
		c1.gridx = 0;
		c1.gridy = 0;
		c1.insets = new Insets(1,5,10,5);
		add( pane, c1);
		
		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new GridBagLayout());
		GridBagConstraints cButton = new GridBagConstraints();
		
		sendMail = aggiungiBottone( buttonPanel, sendMail, "sendMail", Messages.getMessage( MessageKeys.PANEL_INVIA_LOG_BUTTON_SENDMAIL ),
				Messages.getMessage( MessageKeys.PANEL_INVIA_LOG_BUTTON_SENDMAIL_TOOLTIP ), true, cButton, 0, 0, 1, 5, 5 );
		sendMail.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				try {
					if( inviaLog() ){
						JOptionPane.showMessageDialog(windowDialog,
								Messages.getMessage( MessageKeys.MSG_INVIOLOG_SUCCESS ),
								"",
								JOptionPane.INFORMATION_MESSAGE );
					}
				} catch (MessagingException e) {
					JOptionPane.showMessageDialog(windowDialog,
							Messages.getMessage( MessageKeys.MSG_INVIOLOG_ERROR_GENERIC ),
							"",
							JOptionPane.ERROR_MESSAGE );
				}
				windowDialog.setVisible(false);
			}
		});
		
		close = aggiungiBottone( buttonPanel, close, "sendMail", Messages.getMessage( MessageKeys.PANEL_BUTTON_ANNULLA ),
				Messages.getMessage( MessageKeys.PANEL_BUTTON_ANNULLA_TOOLTIP ), true, cButton, 1, 0, 1, 5, 5 );
		close.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				windowDialog.setVisible(false);
			}
		});
		
		c1.gridx = 0;
		c1.gridy = 1;
		c1.gridwidth = 2;
		c1.insets = new Insets(1,5,5,5);
		add( buttonPanel, c1);
	}
	
	private void initDatiInvio(){
		
		try {
			smtpServer = PreferenceManager.getString( PreferenceKeys.PROPERTY_MAIL_SMTPHOST );
		} catch (Exception e2) {
			logger.info("Errore ", e2);
		}
		try {
			useAuthentication = PreferenceManager.getBoolean( PreferenceKeys.PROPERTY_MAIL_USEAUTHENTICATION );
		} catch (Exception e2) {
			logger.info("Errore ", e2);
		}
		try {
			usernameAccountInvio = PreferenceManager.getString( PreferenceKeys.PROPERTY_MAIL_AUTHENTICATION_USER);
		} catch (Exception e2) {
			logger.info("Errore ", e2);
		}
		try {
			passwordAccountInvio = PreferenceManager.getString( PreferenceKeys.PROPERTY_MAIL_AUTHENTICATION_PASSWORD );
		} catch (Exception e2) {
			logger.info("Errore ", e2);
		}
		try {
			mittente = PreferenceManager.getString( PreferenceKeys.PROPERTY_MAIL_FROM );
		} catch (Exception e2) {
			logger.info("Errore ", e2);
		}
		try {
			mailTo = PreferenceManager.getString( PreferenceKeys.PROPERTY_MAIL_TO );
		} catch (Exception e2) {
			logger.info("Errore ", e2);
		}
		try {
			mailSubject= PreferenceManager.getString( PreferenceKeys.PROPERTY_MAIL_SUBJECT );
		} catch (Exception e2) {
			logger.info("Errore ", e2);
		}
		try {
			mailBody = PreferenceManager.getString( PreferenceKeys.PROPERTY_MAIL_BODY );
		} catch (Exception e2) {
			logger.info("Errore ", e2);
		}
		try {
			attachmentName = PreferenceManager.getString( PreferenceKeys.PROPERTY_MAIL_ATTACHMENTNAME );
		} catch (Exception e2) {
			logger.info("Errore ", e2);
		}
	}

	private boolean inviaLog() throws MessagingException {
		
		Properties mailProperties = System.getProperties();
		mailProperties.setProperty("mail.smtp.host", smtpServer );

		Session session;
		if( useAuthentication ){
			Authenticator auth = new SMTPAuthenticator();
			session = Session.getInstance(mailProperties, auth);
		} else {
			session = Session.getInstance(mailProperties, null);
		}
		MimeMessage message = new MimeMessage(session);
		try {
			logger.info("Mittente " + mittente );
			message.setFrom(new InternetAddress( mittente ));
			
			logger.info("Destinatario " + mailToField.getText() );
			String destinatariString =  mailToField.getText();
			StringTokenizer tokenizer = new StringTokenizer( destinatariString, ";");
			List <InternetAddress> destinatari = new ArrayList<InternetAddress>();
			while( tokenizer.hasMoreTokens() ){
				destinatari.add( new InternetAddress( tokenizer.nextToken() ) );
			}
			message.addRecipients(Message.RecipientType.TO, destinatari.toArray( new InternetAddress[destinatari.size()])  );
			
			logger.info("Oggetto " + mailSubjectField.getText() );
			message.setSubject( mailSubjectField.getText() );
			
			Multipart multipart = new MimeMultipart();
			
			MimeBodyPart messageBodyPart = new MimeBodyPart();
			StringBuffer stringBuf = new StringBuffer();
			logger.info("Testo " + mailBodyField.getText() );
			stringBuf.append( mailBodyField.getText() );
			
			messageBodyPart.setText( stringBuf.toString() );
			multipart.addBodyPart(messageBodyPart);
			
			message.setContent(multipart);
			Transport.send( message );
			logger.info("Messaggio email inviato");
			
			logger.info("Messaggio email inviato");
			return true;
			
		} catch (AddressException e) {
			throw e;
		} catch (MessagingException e) {
			throw e;
		}
		
	}

	private class SMTPAuthenticator extends javax.mail.Authenticator {
        public PasswordAuthentication getPasswordAuthentication() {
           return new PasswordAuthentication(usernameAccountInvio, passwordAccountInvio);
        }
    }
	
}  
