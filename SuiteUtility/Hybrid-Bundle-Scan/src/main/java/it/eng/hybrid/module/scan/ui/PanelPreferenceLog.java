package it.eng.hybrid.module.scan.ui;

import it.eng.hybrid.module.scan.messages.MessageKeys;
import it.eng.hybrid.module.scan.messages.Messages;
import it.eng.hybrid.module.scan.preferences.PreferenceKeys;
import it.eng.hybrid.module.scan.preferences.PreferenceManager;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import org.apache.log4j.Logger;

public class PanelPreferenceLog extends GenericPanel {

	public final static Logger logger = Logger.getLogger(PanelPreferenceLog.class);
	
	private static final long serialVersionUID = 1L;

	private int widthPanel = 400;
	private int heightPanel = 400;
	
	private JLabel smtpHostLabel;
	private JTextField smtpHostField;
	private JLabel mailToLabel;
	private JTextField mailToField;
	private JLabel mailFromLabel;
	private JTextField mailFromField;
	private JLabel mailSubjectLabel;
	private JTextField mailSubjectField;
	private JLabel mailBodyLabel;
	private JTextArea mailBodyField;
	private JLabel mailAttachmentNameLabel;
	private JTextField mailAttachmentNameField;
	private JLabel useAuthenticationLabel;
	private JCheckBox useAuthenticationField;
	private JLabel userAuthenticationLabel;
	private JTextField userAuthenticationField;
	private JLabel passwordAuthenticationLabel;
	private JPasswordField passwordAuthenticationField;
	
	private JButton save ;
	private JButton cancel; 
	
	public PanelPreferenceLog(int widthPanel, int heightPanel) {
		super();
		this.widthPanel = widthPanel;
		this.heightPanel = heightPanel;
	}
	
	public PanelPreferenceLog() {
		super();
	}
	
	public void show( ){

		windowDialog = new JFrame();
		//windowDialog.setModal(true);

		initialize();
		
		windowDialog.getContentPane().setLayout( new BorderLayout() );
		windowDialog.getContentPane().add( this, BorderLayout.CENTER );
		windowDialog.pack();
        windowDialog.setSize( widthPanel, heightPanel );
		 
        windowDialog.setTitle( Messages.getMessage( MessageKeys.PANEL_INVIA_LOG_TITLE ) );
        
       // windowDialog.setLocationRelativeTo( container );
        windowDialog.setVisible(true);
        windowDialog.setAlwaysOnTop(true);
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setPreferredSize(new Dimension(300, 170));
		
		GridBagConstraints c1 = new GridBagConstraints();
        setLayout(new GridBagLayout());
        
		pane = new JPanel();
        JScrollPane scroll = new JScrollPane(pane);
        scroll.setBorder(BorderFactory.createEmptyBorder());
		pane.setLayout(new GridBagLayout());
		
		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.HORIZONTAL;
		int y=0;
		
		smtpHostLabel = aggiungiLabel(pane, smtpHostLabel, "smtpHostLabel", Messages.getMessage( MessageKeys.PANEL_INVIA_LOG_FIELD_SMTPHOST ), c, 0, y, 1, 1, 2, GridBagConstraints.LINE_START);
		
		String smtpHostProperty ="";
		try {
			smtpHostProperty = PreferenceManager.getString( PreferenceKeys.PROPERTY_MAIL_SMTPHOST );
		} catch (Exception e2) {
			logger.info("Errore ", e2);
		}
		smtpHostField = aggiungiFieldTesto( pane, smtpHostField, 20, "smtpHostField", smtpHostProperty, true, c, 1, y++, 1, 2, 2, GridBagConstraints.LINE_START);
		
		useAuthenticationLabel = aggiungiLabel(pane, useAuthenticationLabel, "useAuthenticationLabel", Messages.getMessage( MessageKeys.PANEL_INVIA_LOG_FIELD_USEAUTHENTICATION ), c, 0, y, 1, 1, 2, GridBagConstraints.LINE_START );
		
		boolean useAuthenticationProperty = false;
		try {
			useAuthenticationProperty = PreferenceManager.getBoolean( PreferenceKeys.PROPERTY_MAIL_USEAUTHENTICATION );
		} catch (Exception e2) {
			logger.info("Errore ", e2);
		}
		useAuthenticationField = aggiungiCheckBox(pane, useAuthenticationField, "useAuthenticationField", "                                                          ", useAuthenticationProperty, true, SwingConstants.RIGHT, c, 1, y++, 1, 2, 2, GridBagConstraints.LINE_START);
		
		userAuthenticationLabel = aggiungiLabel(pane, userAuthenticationLabel, "userAuthenticationLabel", Messages.getMessage(MessageKeys.PANEL_INVIA_LOG_FIELD_USERAUTHENTICATION), c, 0, y, 1, 1, 2, GridBagConstraints.LINE_START);
		
		String userAuthenticationProperty ="";
		try {
			userAuthenticationProperty = PreferenceManager.getString( PreferenceKeys.PROPERTY_MAIL_AUTHENTICATION_USER);
		} catch (Exception e2) {
			logger.info("Errore ", e2);
		}
		userAuthenticationField = aggiungiFieldTesto( pane, userAuthenticationField, 20, "userAuthenticationField", userAuthenticationProperty, true, c, 1, y++, 1, 2, 2, GridBagConstraints.LINE_START);
		
		passwordAuthenticationLabel = aggiungiLabel(pane, passwordAuthenticationLabel, "passwordAuthenticationLabel", Messages.getMessage( MessageKeys.PANEL_INVIA_LOG_FIELD_PASSWORDAUTHENTICATION), c, 0, y, 1, 1, 2, GridBagConstraints.LINE_START);
		
		String passwordAuthenticationProperty ="";
		try {
			passwordAuthenticationProperty = PreferenceManager.getString( PreferenceKeys.PROPERTY_MAIL_AUTHENTICATION_PASSWORD );
		} catch (Exception e2) {
			logger.info("Errore ", e2);
		}
		passwordAuthenticationField = aggiungiFieldPassword( pane, passwordAuthenticationField, 20, "passwordAuthenticationField", passwordAuthenticationProperty, true, c, 1, y++, 1, 2, 2, GridBagConstraints.LINE_START);
		
		mailToLabel = aggiungiLabel(pane, mailToLabel, "mailToLabel", Messages.getMessage( MessageKeys.PANEL_INVIA_LOG_FIELD_MAILTO ), c, 0, y, 1, 1, 2, GridBagConstraints.LINE_START);
		
		String mailToProperty ="";
		try {
			mailToProperty = PreferenceManager.getString( PreferenceKeys.PROPERTY_MAIL_TO );
		} catch (Exception e2) {
			logger.info("Errore ", e2);
		}
		mailToField = aggiungiFieldTesto( pane, mailToField, 20, "mailToField", mailToProperty, true, c, 1, y++, 1, 2, 2, GridBagConstraints.LINE_START);
		
		
		mailFromLabel = aggiungiLabel(pane, mailFromLabel, "mailFromLabel", Messages.getMessage( MessageKeys.PANEL_INVIA_LOG_FIELD_MAILFROM), c, 0, y, 1, 1, 2, GridBagConstraints.LINE_START);
		
		String mailFromProperty ="";
		try {
			mailFromProperty = PreferenceManager.getString( PreferenceKeys.PROPERTY_MAIL_FROM );
		} catch (Exception e2) {
			logger.info("Errore ", e2);
		}
		mailFromField = aggiungiFieldTesto( pane, mailFromField, 20, "mailFromField", mailFromProperty, true, c, 1, y++, 1, 2, 2, GridBagConstraints.LINE_START);
		
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
		
		mailAttachmentNameLabel = aggiungiLabel(pane, mailAttachmentNameLabel, "mailAttachmentNameLabel", Messages.getMessage( MessageKeys.PANEL_INVIA_LOG_FIELD_MAILATTACHMENTNAME ), c, 0, y, 1, 1, 2, GridBagConstraints.LINE_START);
		
		String mailAttachmentNameProperty ="";
		try {
			mailAttachmentNameProperty = PreferenceManager.getString( PreferenceKeys.PROPERTY_MAIL_ATTACHMENTNAME );
		} catch (Exception e2) {
			logger.info("Errore ", e2);
		}
		mailAttachmentNameField = aggiungiFieldTesto( pane, mailAttachmentNameField, 20, "mailAttachmentNameField", mailAttachmentNameProperty, true, c, 1, y++, 1, 2, 2, GridBagConstraints.LINE_START);
	
		c1.gridx = 0;
		c1.gridy = 0;
		c1.insets = new Insets(1,5,10,5);
		add( pane, c1);
		
		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new GridBagLayout());
		GridBagConstraints cButton = new GridBagConstraints();
		
		save = aggiungiBottone( buttonPanel, save, "save", Messages.getMessage( MessageKeys.PANEL_BUTTON_SAVE ), Messages.getMessage( MessageKeys.PANEL_BUTTON_SAVE_TOOLTIP ), true, cButton, 0, 0, 1, 5, 5);
		save.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				salvaConfigurazione();
				windowDialog.setVisible(false);
			}
		});
		buttonPanel.add(save, cButton);
		
		cancel = aggiungiBottone( buttonPanel, cancel, "cancel", Messages.getMessage( MessageKeys.PANEL_BUTTON_ANNULLA ), 
				Messages.getMessage( MessageKeys.PANEL_BUTTON_ANNULLA_TOOLTIP ), true, cButton, 1, 0, 1, 5, 5);
		cancel.addActionListener(new ActionListener(){
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
	
	public void salvaConfigurazione(){
		try {
			PreferenceManager.saveProp( PreferenceKeys.PROPERTY_MAIL_FROM, mailFromField.getText());
			PreferenceManager.saveProp( PreferenceKeys.PROPERTY_MAIL_TO, mailToField.getText());
			PreferenceManager.saveProp( PreferenceKeys.PROPERTY_MAIL_SUBJECT, mailSubjectField.getText());
			PreferenceManager.saveProp( PreferenceKeys.PROPERTY_MAIL_BODY, mailBodyField.getText());
			PreferenceManager.saveProp( PreferenceKeys.PROPERTY_MAIL_ATTACHMENTNAME, mailAttachmentNameField.getText());
			PreferenceManager.saveProp( PreferenceKeys.PROPERTY_MAIL_SMTPHOST, smtpHostField.getText());
			PreferenceManager.saveProp( PreferenceKeys.PROPERTY_MAIL_USEAUTHENTICATION, useAuthenticationField.isSelected() );
			PreferenceManager.saveProp( PreferenceKeys.PROPERTY_MAIL_AUTHENTICATION_USER, userAuthenticationField.getText());
			PreferenceManager.saveProp( PreferenceKeys.PROPERTY_MAIL_AUTHENTICATION_PASSWORD, passwordAuthenticationField.getText());
			
			PreferenceManager.reinitConfig();
			
			JOptionPane.showMessageDialog(this,
					Messages.getMessage( MessageKeys.MSG_INVIOLOG_SAVE_SUCCESS ),
					"",
					JOptionPane.INFORMATION_MESSAGE );
			
		} catch (Exception e) {
			logger.info("Errore ", e);
			JOptionPane.showMessageDialog(this,
					Messages.getMessage( MessageKeys.MSG_INVIOLOG_ERROR_GENERIC_SAVE ),
					"",
					JOptionPane.ERROR_MESSAGE );
		}
	}

}  
