package it.eng.hybrid.module.jpedal.ui.popup;

import it.eng.hybrid.module.jpedal.messages.MessageConstants;
import it.eng.hybrid.module.jpedal.messages.Messages;
import it.eng.hybrid.module.jpedal.pdf.PdfDecoder;
import it.eng.hybrid.module.jpedal.preferences.PreferenceManager;
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
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import org.apache.log4j.Logger;

public class PreferenceRetePanel extends GenericPanel {
	
	public final static Logger logger = Logger.getLogger(PreferenceRetePanel.class);
	
	private static final long serialVersionUID = 1L;

	JFrame windowDialog;
	
	JButton confirm = new JButton( Messages.getMessage( MessageConstants.PANEL_BUTTON_SAVE ) );
	JButton cancel = new JButton( Messages.getMessage( MessageConstants.PANEL_BUTTON_CLOSE ) );

//	private JLabel label;
//	private JTextField smtpHostField;
	
	public void show(SwingGUI swingGUI){

		windowDialog = new JFrame();
		//windowDialog.setModal(true);

        createPreferenceWindow(swingGUI);
		
        confirm.setEnabled(true);
		
       // windowDialog.setLocationRelativeTo(null);
        windowDialog.setVisible(true);
        windowDialog.setAlwaysOnTop(true);
	}

	private void createPreferenceWindow(final SwingGUI gui){
		
		PreferenceManager preferenceManager = gui.getCommonValues().getPreferenceManager();
		
		windowDialog.getContentPane().setLayout( new BorderLayout() );
		windowDialog.getContentPane().add( this,BorderLayout.CENTER );
		windowDialog.pack();
        if( PdfDecoder.isRunningOnMac )
        	windowDialog.setSize( 600, 475 );
        else
        	windowDialog.setSize( 450, 320 );

        windowDialog.setTitle( Messages.getMessage( MessageConstants.PANEL_PREFERENCE_RETE_TITLE ) );
		
        GridBagConstraints c1 = new GridBagConstraints();
        setLayout(new GridBagLayout());

        JPanel pane = new JPanel();
        JScrollPane scroll = new JScrollPane(pane);
        scroll.setBorder(BorderFactory.createEmptyBorder());
		pane.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		int y=0;

		//label = aggiungiLabel(pane, label, "label", Messages.getMessage( MessageConstants.PANEL_INVIA_LOG_TEXT), c, 0, y++, 2, 10);
		
		//smtpHostLabel = aggiungiLabel(pane, smtpHostLabel, "smtpHostLabel", Messages.getMessage( MessageConstants.PANEL_INVIA_LOG_FIELD_SMTPHOST ), c, 0, y, 1, 1);
			
//			String smtpHostProperty ="";
//			try {
//				smtpHostProperty = preferenceManager.getConfiguration().getString( ConfigConstants.PROPERTY_MAIL_SMTPHOST );
//			} catch (Exception e2) {
//				logger.info("Errore ", e2);
//			}
//			smtpHostField = aggiungiFieldTesto( pane, smtpHostField, 20, "smtpHostField", smtpHostProperty, true, c, 1, y++, 1);
//			
			
		c1.gridx = 0;
		c1.gridy = 0;
		c1.insets = new Insets(1,5,10,5);
		add( pane, c1);
		
		confirm.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				setPreferences(gui);
				windowDialog.setVisible(false);
			}
		});
		confirm.setToolTipText( Messages.getMessage( MessageConstants.PANEL_BUTTON_SAVE_TOOLTIP ) );
		
		cancel.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				windowDialog.setVisible(false);
			}
		});
		cancel.setToolTipText(Messages.getMessage( MessageConstants.PANEL_BUTTON_CLOSE_TOOLTIP ) );
		confirm.setPreferredSize(cancel.getPreferredSize());

		confirm.setEnabled(true);
		confirm.setVisible(true);

		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new GridBagLayout());
		GridBagConstraints cButton = new GridBagConstraints();
		
		cButton.gridx = 0;
		cButton.gridy = 0;
		cButton.insets = new Insets(5,5,15,5);
		buttonPanel.add(Box.createHorizontalGlue());
		buttonPanel.add(confirm, cButton);
		getRootPane().setDefaultButton(confirm);
		cButton.gridx = 1;
		buttonPanel.add(cancel, cButton);
		
		c1.gridx = 0;
		c1.gridy = 1;
		c1.gridwidth = 2;
		c1.insets = new Insets(1,5,5,5);
		add( buttonPanel, c1);
	}

	public void setPreferences(SwingGUI gui){
		try {
			//PreferenceManager.saveProp( ConfigConstants.PROPERTY_MAIL_AUTHENTICATION_PASSWORD, passwordAuthenticationField.getText());
			
			PreferenceManager preferenceManager = gui.getCommonValues().getPreferenceManager();
			preferenceManager.reinitConfig();
		} catch (Exception e) {
			logger.info("Errore ", e);
		}
	}

	public void dispose() {
		
		this.removeAll();
		
		if(windowDialog!=null)
			windowDialog.removeAll();
		windowDialog=null;
		
		confirm=null;
		cancel=null;
		//label=null;
	}
	
}