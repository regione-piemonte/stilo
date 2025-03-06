package it.eng.hybrid.module.jpedal.ui.popup;

import it.eng.hybrid.module.jpedal.messages.MessageConstants;
import it.eng.hybrid.module.jpedal.messages.Messages;
import it.eng.hybrid.module.jpedal.preferences.ConfigConstants;
import it.eng.hybrid.module.jpedal.preferences.PreferenceManager;
import it.eng.hybrid.module.jpedal.ui.JPedalApplication;
import it.eng.hybrid.module.jpedal.ui.SwingGUI;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.Box;
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

public class PreferenceInvioLogPanel extends GenericPanel {
	
	public final static Logger logger = Logger.getLogger(PreferenceInvioLogPanel.class);
	
	private static final long serialVersionUID = 1L;

	private int widthPanel = 400;
	private int heightPanel = 400;
	
	private JFrame windowDialog;
	
	private JButton save; 
	private JButton cancel; 

	private JLabel smtpHostLabel;
	private JTextField smtpHostField;
	private JLabel useAuthenticationLabel;
	private JCheckBox useAuthenticationField;
	private JLabel userAuthenticationLabel;
	private JTextField userAuthenticationField;
	private JLabel passwordAuthenticationLabel;
	private JPasswordField passwordAuthenticationField;
	private JLabel mailFromLabel;
	private JTextField mailFromField;
	private JLabel mailAttachmentNameLabel;
	private JTextField mailAttachmentNameField;
	private JLabel mailToLabel;
	private JTextField mailToField;
	private JLabel mailSubjectLabel;
	private JTextField mailSubjectField;
	private JLabel mailBodyLabel;
	private JTextArea mailBodyField;
	
	public PreferenceInvioLogPanel() {
		super();
	}
	
	public PreferenceInvioLogPanel(int widthPanel, int heightPanel) {
		super();
		this.widthPanel = widthPanel;
		this.heightPanel = heightPanel;
		logger.info("widthPanel " + widthPanel);
		logger.info("heightPanel " + heightPanel);
	}

	public void show(SwingGUI swingGUI){

		windowDialog = new JFrame();
		//windowDialog.setModal(true);

        createPreferenceWindow(swingGUI);
		
        save.setEnabled(true);
		
        //windowDialog.setLocationRelativeTo(null);
        windowDialog.setVisible(true);
        windowDialog.setAlwaysOnTop(true);
	}

	private void createPreferenceWindow(final SwingGUI gui){
		
		PreferenceManager preferenceManager = gui.getCommonValues().getPreferenceManager();
		
		windowDialog.getContentPane().setLayout( new BorderLayout() );
		windowDialog.getContentPane().add( this,BorderLayout.CENTER );
		windowDialog.pack();
        windowDialog.setSize( widthPanel, heightPanel );

        windowDialog.setTitle( Messages.getMessage( MessageConstants.PANEL_PREFERENCE_INVIOLOG_TITLE ) );
		
        GridBagConstraints c1 = new GridBagConstraints();
        setLayout(new GridBagLayout());

        pane = new JPanel();
        JScrollPane scroll = new JScrollPane(pane);
        scroll.setBorder(BorderFactory.createEmptyBorder());
		pane.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		int y=0;

		smtpHostLabel = aggiungiLabel(pane, smtpHostLabel, "smtpHostLabel", Messages.getMessage( MessageConstants.PANEL_PREFERENCE_INVIOLOG_FIELD_SMTPHOST ), 
				c, 0, y, 1, 5, 2, GridBagConstraints.LINE_START);
		
		String smtpHostProperty ="";
		try {
			smtpHostProperty = preferenceManager.getConfiguration().getString( ConfigConstants.PROPERTY_MAIL_SMTPHOST );
		} catch (Exception e2) {
			logger.info("Errore ", e2);
		}
		smtpHostField = aggiungiFieldTesto( pane, smtpHostField, 20, "smtpHostField", smtpHostProperty, true, c, 1, y++, 1, 5, 2, GridBagConstraints.LINE_START );
		
		useAuthenticationLabel = aggiungiLabel(pane, useAuthenticationLabel, "useAuthenticationLabel", Messages.getMessage( MessageConstants.PANEL_PREFERENCE_INVIOLOG_FIELD_USEAUTHENTICATION ), 
				c, 0, y, 1, 5, 2, GridBagConstraints.LINE_START);
		
		boolean useAuthenticationProperty = false;
		try {
			useAuthenticationProperty = preferenceManager.getConfiguration().getBoolean( ConfigConstants.PROPERTY_MAIL_USEAUTHENTICATION );
		} catch (Exception e2) {
			logger.info("Errore ", e2);
		}
		useAuthenticationField = aggiungiCheckBox(pane, useAuthenticationField, "useAuthenticationField", "", useAuthenticationProperty, 
				true, SwingConstants.LEFT, c, 1, y++, 1, 5, 2, GridBagConstraints.LINE_START);
		
		userAuthenticationLabel = aggiungiLabel(pane, userAuthenticationLabel, "userAuthenticationLabel", Messages.getMessage(MessageConstants.PANEL_PREFERENCE_INVIOLOG_FIELD_USERAUTHENTICATION), 
				c, 0, y, 1, 5, 2, GridBagConstraints.LINE_START);
		
		String userAuthenticationProperty ="";
		try {
			userAuthenticationProperty = preferenceManager.getConfiguration().getString( ConfigConstants.PROPERTY_MAIL_AUTHENTICATION_USER);
		} catch (Exception e2) {
			logger.info("Errore ", e2);
		}
		userAuthenticationField = aggiungiFieldTesto( pane, userAuthenticationField, 20, "userAuthenticationField", userAuthenticationProperty, true, c, 1, y++, 1, 5, 2, GridBagConstraints.LINE_START );
		
		passwordAuthenticationLabel = aggiungiLabel(pane, passwordAuthenticationLabel, "passwordAuthenticationLabel", Messages.getMessage( MessageConstants.PANEL_PREFERENCE_INVIOLOG_FIELD_PASSWORDAUTHENTICATION), 
				c, 0, y, 1, 5, 2, GridBagConstraints.LINE_START);
		
		String passwordAuthenticationProperty ="";
		try {
			passwordAuthenticationProperty = preferenceManager.getConfiguration().getString( ConfigConstants.PROPERTY_MAIL_AUTHENTICATION_PASSWORD );
		} catch (Exception e2) {
			logger.info("Errore ", e2);
		}
		passwordAuthenticationField = aggiungiFieldPassword( pane, passwordAuthenticationField, 20, "passwordAuthenticationField", passwordAuthenticationProperty, true, c, 1, y++, 1, 5, 2, GridBagConstraints.LINE_START);
		
		mailFromLabel = aggiungiLabel(pane, mailFromLabel, "mailFromLabel", Messages.getMessage( MessageConstants.PANEL_PREFERENCE_INVIOLOG_FIELD_MAILFROM), 
				c, 0, y, 1, 5, 2, GridBagConstraints.LINE_START);
		
		String mailFromProperty ="";
		try {
			mailFromProperty = preferenceManager.getConfiguration().getString( ConfigConstants.PROPERTY_MAIL_FROM );
		} catch (Exception e2) {
			logger.info("Errore ", e2);
		}
		mailFromField = aggiungiFieldTesto( pane, mailFromField, 20, "mailFromField", mailFromProperty, true, c, 1, y++, 1, 5, 2, GridBagConstraints.LINE_START );
		
		mailToLabel = aggiungiLabel(pane, mailToLabel, "mailToLabel", Messages.getMessage( MessageConstants.PANEL_PREFERENCE_INVIOLOG_FIELD_MAILTO ), 
				c, 0, y, 1, 5, 2, GridBagConstraints.LINE_START);
		
		String mailToProperty ="";
		try {
			mailToProperty = preferenceManager.getConfiguration().getString( ConfigConstants.PROPERTY_MAIL_TO );
		} catch (Exception e2) {
			logger.info("Errore ", e2);
		}
		mailToField = aggiungiFieldTesto( pane, mailToField, 20, "mailToField", mailToProperty, true, c, 1, y++, 1, 5, 2, GridBagConstraints.LINE_START );
				
		mailAttachmentNameLabel = aggiungiLabel(pane, mailAttachmentNameLabel, "mailAttachmentNameLabel", Messages.getMessage( MessageConstants.PANEL_PREFERENCE_INVIOLOG_FIELD_MAILATTACHMENTNAME ), 
				c, 0, y, 1, 5, 2, GridBagConstraints.LINE_START);
		
		String mailAttachmentNameProperty ="";
		try {
			mailAttachmentNameProperty = preferenceManager.getConfiguration().getString( ConfigConstants.PROPERTY_MAIL_ATTACHMENTNAME );
		} catch (Exception e2) {
			logger.info("Errore ", e2);
		}
		mailAttachmentNameField = aggiungiFieldTesto( pane, mailAttachmentNameField, 20, "mailAttachmentNameField", mailAttachmentNameProperty, true, c, 1, y++, 1, 5, 2, GridBagConstraints.LINE_START );
		
		mailSubjectLabel = aggiungiLabel(pane, mailSubjectLabel, "mailSubjectLabel", Messages.getMessage( MessageConstants.PANEL_PREFERENCE_INVIOLOG_FIELD_MAILSUBJECT ), 
				c, 0, y, 1, 5, 2, GridBagConstraints.LINE_START);
		
		String mailSubjectProperty ="";
		try {
			mailSubjectProperty = preferenceManager.getConfiguration().getString( ConfigConstants.PROPERTY_MAIL_SUBJECT );
		} catch (Exception e2) {
			logger.info("Errore ", e2);
		}
		mailSubjectField = aggiungiFieldTesto( pane, mailSubjectField, 20, "mailSubjectField", mailSubjectProperty, true, c, 1, y++, 1, 5, 2, GridBagConstraints.LINE_START );
		
		mailBodyLabel = aggiungiLabel(pane, mailBodyLabel, "mailBodyLabel", Messages.getMessage( MessageConstants.PANEL_PREFERENCE_INVIOLOG_FIELD_MAILBODY ), 
				c, 0, y, 1, 5, 1, GridBagConstraints.LINE_START);
		
		String mailBodyProperty ="";
		try {
			mailBodyProperty = preferenceManager.getConfiguration().getString( ConfigConstants.PROPERTY_MAIL_BODY );
		} catch (Exception e2) {
			logger.info("Errore ", e2);
		}
		mailBodyField = aggiungiFieldTextArea( pane, mailBodyField, 5, 20, "mailBodyField", mailBodyProperty, true, c, 1, y++, 1, 5, 2, GridBagConstraints.LINE_START );
				
		c1.gridx = 0;
		c1.gridy = 0;
		c1.insets = new Insets(1,5,10,5);
		add( pane, c1);
		
		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new GridBagLayout());
		GridBagConstraints cButton = new GridBagConstraints();
		
		buttonPanel.add(Box.createHorizontalGlue());
		
		save = aggiungiBottone( buttonPanel, save, "save", Messages.getMessage( MessageConstants.PANEL_BUTTON_SAVE ), Messages.getMessage( MessageConstants.PANEL_BUTTON_SAVE_TOOLTIP ), true, cButton, 0, 0, 1, 5, 5);
		save.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				salvaConfigurazione(gui);
				windowDialog.setVisible(false);
			}
		});
		
		getRootPane().setDefaultButton( save );
		
		cancel = aggiungiBottone( buttonPanel, cancel, "cancel", Messages.getMessage( MessageConstants.PANEL_BUTTON_CANCEL ), 
				Messages.getMessage( MessageConstants.PANEL_BUTTON_CANCEL_TOOLTIP ), true, cButton, 1, 0, 1, 5, 5);
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

	public void salvaConfigurazione(SwingGUI gui){
		try {
			PreferenceManager.saveProp( ConfigConstants.PROPERTY_MAIL_SMTPHOST, smtpHostField.getText());
			PreferenceManager.saveProp( ConfigConstants.PROPERTY_MAIL_USEAUTHENTICATION, useAuthenticationField.isSelected() );
			PreferenceManager.saveProp( ConfigConstants.PROPERTY_MAIL_AUTHENTICATION_USER, userAuthenticationField.getText());
			PreferenceManager.saveProp( ConfigConstants.PROPERTY_MAIL_AUTHENTICATION_PASSWORD, passwordAuthenticationField.getText());
			PreferenceManager.saveProp( ConfigConstants.PROPERTY_MAIL_FROM, mailFromField.getText());
			PreferenceManager.saveProp( ConfigConstants.PROPERTY_MAIL_ATTACHMENTNAME, mailAttachmentNameField.getText());
			PreferenceManager.saveProp( ConfigConstants.PROPERTY_MAIL_TO, mailToField.getText());
			PreferenceManager.saveProp( ConfigConstants.PROPERTY_MAIL_SUBJECT, mailSubjectField.getText());
			PreferenceManager.saveProp( ConfigConstants.PROPERTY_MAIL_BODY, mailBodyField.getText());
			
			PreferenceManager preferenceManager = gui.getCommonValues().getPreferenceManager();
			preferenceManager.reinitConfig();
			
			JOptionPane.showMessageDialog(this,
					Messages.getMessage( MessageConstants.MSG_INVIOLOG_SAVE_SUCCESS ),
					"",
					JOptionPane.INFORMATION_MESSAGE );
			
		} catch (Exception e) {
			logger.info("Errore ", e);
			JOptionPane.showMessageDialog(this,
					Messages.getMessage( MessageConstants.MSG_INVIOLOG_ERROR_GENERIC_SAVE ),
					"",
					JOptionPane.ERROR_MESSAGE );
		}
	}

	public void dispose() {
		
		this.removeAll();
		
		if(windowDialog!=null)
			windowDialog.removeAll();
		windowDialog=null;
		
		smtpHostLabel=null;
		smtpHostField=null;
		useAuthenticationLabel=null;
		useAuthenticationField=null;
		userAuthenticationLabel=null;
		userAuthenticationField=null;
		passwordAuthenticationLabel=null;
		passwordAuthenticationField=null;
		mailAttachmentNameLabel=null;
		mailAttachmentNameField=null;
		mailFromLabel=null;
		mailFromField=null;
		mailToLabel=null;
		mailToField=null;
		mailSubjectLabel=null;
		mailSubjectField=null;
		mailBodyLabel=null;
		mailBodyField=null;
		
		save=null;
		cancel=null;

	}
	
}