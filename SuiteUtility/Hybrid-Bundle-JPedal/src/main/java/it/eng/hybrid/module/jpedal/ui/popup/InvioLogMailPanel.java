package it.eng.hybrid.module.jpedal.ui.popup;

import it.eng.hybrid.module.jpedal.messages.MessageConstants;
import it.eng.hybrid.module.jpedal.messages.Messages;
import it.eng.hybrid.module.jpedal.preferences.ConfigConstants;
import it.eng.hybrid.module.jpedal.preferences.PreferenceManager;
import it.eng.hybrid.module.jpedal.ui.SwingGUI;

import java.awt.BorderLayout;
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
import javax.swing.Box;
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

public class InvioLogMailPanel extends GenericPanel {

	public final static Logger logger = Logger.getLogger(InvioLogMailPanel.class);
	
	private static final long serialVersionUID = 1L;

	private int widthPanel = 400;
	private int heightPanel = 400;
	
	private JFrame windowDialog;
	
	private JButton close;
	private JButton sendMail; 
	
	private JLabel label, label1;
	
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
	
	public InvioLogMailPanel() {
		super();
	}
	
	public InvioLogMailPanel(int widthPanel, int heightPanel) {
		super();
		this.widthPanel = widthPanel;
		this.heightPanel = heightPanel;
	}
	
	public void show(SwingGUI swingGUI){

		windowDialog = new JFrame();

		//windowDialog.setModal(true);

        createPreferenceWindow(swingGUI);
		
        //windowDialog.setLocationRelativeTo(null);
        windowDialog.setVisible(true);
        windowDialog.setAlwaysOnTop(true);
	}

	private void createPreferenceWindow(final SwingGUI gui){
		
		final PreferenceManager preferenceManager = gui.getCommonValues().getPreferenceManager();
		
		initDatiInvio(preferenceManager);
		
		windowDialog.getContentPane().setLayout(new BorderLayout());
		windowDialog.getContentPane().add(this,BorderLayout.CENTER);
		windowDialog.pack();
		windowDialog.setSize( widthPanel, heightPanel );

		windowDialog.setTitle( Messages.getMessage( MessageConstants.PANEL_INVIA_LOG_TITLE ) );
		
        GridBagConstraints c1 = new GridBagConstraints();
        setLayout(new GridBagLayout());

        pane = new JPanel();
        JScrollPane scroll = new JScrollPane(pane);
        scroll.setBorder(BorderFactory.createEmptyBorder());
		pane.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		int y=0;

		label = aggiungiLabel(pane, label, "label", Messages.getMessage( MessageConstants.PANEL_INVIA_LOG_TEXT), c, 0, y++, 2, 5, 1, GridBagConstraints.LINE_START);
		label1 = aggiungiLabel(pane, label, "label1", Messages.getMessage( MessageConstants.PANEL_INVIA_LOG_TEXT1), c, 0, y++, 2, 5, 1, GridBagConstraints.LINE_START);
		
		mailToLabel = aggiungiLabel(pane, mailToLabel, "mailToLabel", Messages.getMessage( MessageConstants.PANEL_PREFERENCE_INVIOLOG_FIELD_MAILTO ), c, 0, y, 1, 5, 5, GridBagConstraints.LINE_START);
		
		mailToField = aggiungiFieldTesto( pane, mailToField, 20, "mailToField", mailTo, true, c, 1, y++, 1, 5, 2, GridBagConstraints.LINE_START );
		
		mailSubjectLabel = aggiungiLabel(pane, mailSubjectLabel, "mailSubjectLabel", Messages.getMessage( MessageConstants.PANEL_PREFERENCE_INVIOLOG_FIELD_MAILSUBJECT ), c, 0, y, 1, 5, 1, GridBagConstraints.LINE_START);
		
		mailSubjectField = aggiungiFieldTesto( pane, mailSubjectField, 20, "mailSubjectField", mailSubject, true, c, 1, y++, 1, 5, 2, GridBagConstraints.LINE_START );
		
		mailBodyLabel = aggiungiLabel(pane, mailBodyLabel, "mailBodyLabel", Messages.getMessage( MessageConstants.PANEL_PREFERENCE_INVIOLOG_FIELD_MAILBODY ), c, 0, y, 1, 5, 1, GridBagConstraints.LINE_START);
		
		mailBodyField = aggiungiFieldTextArea( pane, mailBodyField, 5, 20, "mailBodyField", mailBody, true, c, 1, y++, 1, 5, 2, GridBagConstraints.LINE_START );
		
		c1.gridx = 0;
		c1.gridy = 0;
		c1.insets = new Insets(1,5,10,5);
		add( pane, c1);
		
		close = new JButton( Messages.getMessage( MessageConstants.PANEL_BUTTON_CANCEL ) );
		close.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				windowDialog.setVisible(false);
			}
		});
		close.setToolTipText(Messages.getMessage( MessageConstants.PANEL_BUTTON_CANCEL_TOOLTIP ) );
		
		sendMail = new JButton( Messages.getMessage( MessageConstants.PANEL_INVIA_LOG_BUTTON_SENDMAIL ) );
		sendMail.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				try {
					if( inviaLog() ){
						JOptionPane.showMessageDialog(windowDialog,
								Messages.getMessage( MessageConstants.MSG_INVIOLOG_SUCCESS ),
								"",
								JOptionPane.INFORMATION_MESSAGE );
					}
				} catch (MessagingException e) {
					JOptionPane.showMessageDialog(windowDialog,
							Messages.getMessage( MessageConstants.MSG_INVIOLOG_ERROR_GENERIC ),
							"",
							JOptionPane.ERROR_MESSAGE );
				}
				windowDialog.setVisible(false);
			}
		});
		sendMail.setToolTipText( Messages.getMessage( MessageConstants.PANEL_INVIA_LOG_BUTTON_SENDMAIL_TOOLTIP ) );
		
		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new GridBagLayout());
		GridBagConstraints cButton = new GridBagConstraints();
		
		getRootPane().setDefaultButton( sendMail );
		buttonPanel.add(Box.createHorizontalGlue());
		
		cButton.gridx = 0;
		cButton.gridy = 0;
		cButton.insets = new Insets(5,5,15,5);
		buttonPanel.add(sendMail, cButton);
		cButton.gridx = 2;
		buttonPanel.add(close, cButton);
		
		c1.gridx = 0;
		c1.gridy = 1;
		c1.gridwidth = 2;
		c1.insets = new Insets(1,5,5,5);
		add( buttonPanel, c1);
	}
	
	private void initDatiInvio(PreferenceManager preferenceManager){
		
		try {
			smtpServer = preferenceManager.getConfiguration().getString( ConfigConstants.PROPERTY_MAIL_SMTPHOST );
		} catch (Exception e2) {
			logger.info("Errore ", e2);
		}
		try {
			useAuthentication = preferenceManager.getConfiguration().getBoolean( ConfigConstants.PROPERTY_MAIL_USEAUTHENTICATION );
		} catch (Exception e2) {
			logger.info("Errore ", e2);
		}
		try {
			usernameAccountInvio = preferenceManager.getConfiguration().getString( ConfigConstants.PROPERTY_MAIL_AUTHENTICATION_USER);
		} catch (Exception e2) {
			logger.info("Errore ", e2);
		}
		try {
			passwordAccountInvio = preferenceManager.getConfiguration().getString( ConfigConstants.PROPERTY_MAIL_AUTHENTICATION_PASSWORD );
		} catch (Exception e2) {
			logger.info("Errore ", e2);
		}
		try {
			mittente = preferenceManager.getConfiguration().getString( ConfigConstants.PROPERTY_MAIL_FROM );
		} catch (Exception e2) {
			logger.info("Errore ", e2);
		}
		try {
			mailTo = preferenceManager.getConfiguration().getString( ConfigConstants.PROPERTY_MAIL_TO );
		} catch (Exception e2) {
			logger.info("Errore ", e2);
		}
		try {
			mailSubject= preferenceManager.getConfiguration().getString( ConfigConstants.PROPERTY_MAIL_SUBJECT );
		} catch (Exception e2) {
			logger.info("Errore ", e2);
		}
		try {
			mailBody = preferenceManager.getConfiguration().getString( ConfigConstants.PROPERTY_MAIL_BODY );
		} catch (Exception e2) {
			logger.info("Errore ", e2);
		}
		try {
			attachmentName = preferenceManager.getConfiguration().getString( ConfigConstants.PROPERTY_MAIL_ATTACHMENTNAME );
		} catch (Exception e2) {
			logger.info("Errore ", e2);
		}
	}

	public void dispose() {
		
		this.removeAll();
		
		if(windowDialog!=null)
			windowDialog.removeAll();
		windowDialog=null;
		
		close=null;
		sendMail=null;
		
		label=null;
		label1=null;
		
		mailToLabel=null;
		mailToField=null;
		
		mailSubjectLabel=null;
		mailSubjectField=null;
		mailBodyLabel=null;
		mailBodyField=null;
		
	}
	
	private boolean inviaLog() throws MessagingException {
		
		Properties mailProperties = System.getProperties();
		if( smtpServer==null || smtpServer.equalsIgnoreCase("")){
			JOptionPane.showMessageDialog(this,"SMTP Non configurato", "Errore" ,JOptionPane.ERROR_MESSAGE);
			return false;
		}
			
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