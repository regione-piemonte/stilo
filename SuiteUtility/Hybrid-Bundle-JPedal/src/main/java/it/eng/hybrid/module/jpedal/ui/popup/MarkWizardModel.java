/**
* ===========================================
* Java Pdf Extraction Decoding Access Library
* ===========================================
*
* Project Info:  http://www.jpedal.org
* (C) Copyright 1997-2008, IDRsolutions and Contributors.
*
* 	This file is part of JPedal
*
    This library is free software; you can redistribute it and/or
    modify it under the terms of the GNU Lesser General Public
    License as published by the Free Software Foundation; either
    version 2.1 of the License, or (at your option) any later version.

    This library is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
    Lesser General Public License for more details.

    You should have received a copy of the GNU Lesser General Public
    License along with this library; if not, write to the Free Software
    Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA


*
* ---------------------
* SignMArkWizardModel.java
* ---------------------
*/

package it.eng.hybrid.module.jpedal.ui.popup;

import it.eng.hybrid.module.jpedal.acroforms.AcroRenderer;
import it.eng.hybrid.module.jpedal.exception.PdfException;
import it.eng.hybrid.module.jpedal.messages.Messages;
import it.eng.hybrid.module.jpedal.objects.PdfPageData;
import it.eng.hybrid.module.jpedal.pdf.PdfDecoder;
import it.eng.hybrid.module.jpedal.pdf.SignData;
import it.eng.hybrid.module.jpedal.preferences.PreferenceManager;
import it.eng.hybrid.module.jpedal.ui.JPedalApplication;
import it.eng.hybrid.module.jpedal.ui.popup.firma.Factory;
import it.eng.hybrid.module.jpedal.ui.popup.firma.VirtualCard;
import it.eng.hybrid.module.jpedal.util.ItextFunctions;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.smartcardio.CardTerminal;
import javax.smartcardio.CardTerminals;
import javax.smartcardio.TerminalFactory;
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JProgressBar;
import javax.swing.JRadioButton;
import javax.swing.JSeparator;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import org.apache.log4j.Logger;

import net.sf.jsignpdf.types.CertificationLevel;
import net.sf.jsignpdf.types.HashAlgorithm;
import net.sf.jsignpdf.types.RenderMode;

/**This class implements the WizardPanelModel and in this
 * case contains the JPanels to be drawn as inner classes.
 * The methods in SignWizardModel are mainly concerned with
 * controlling what panels are next and whether they can be
 * currently reached.
 */
public class MarkWizardModel implements WizardPanelModel {	
	
	public final static Logger logger = Logger.getLogger(MarkWizardModel.class);
	
	//Each panel must have a unique String identifier 
	private static final String KEYSTORE_PANEL = "3";
	private static final String VISIBLE_SIGNATURE_PANEL = "6";
	
	private static final String MODE_SELECT = "0";
	private static final String ENCRYPTION_PANEL = "5";
	
	public static final String NO_FILE_SELECTED = Messages.getMessage("PdfSigner.NoFileSelected");
	
	private static final int MAXIMUM_PANELS = 2;
		
	private SignData signData;
	private PdfDecoder pdfDecoder;
	private String rootDir;
	private String pdfFile;
	
	/*The JPanels in this wizard */
	private ModeSelect modeSelect;
	private KeystorePanel keystorePanel;
	private EncryptionPanel encryptionPanel;
	private SignaturePanel signaturePanel;
	private PreferenceManager preferenceManager;
	
	/*Maps the JPanels' ID to the panel*/
	private Map panels;
	
	/* The ID of the currently displayed panel*/
	private String currentPanel;
	
	/**
	 * @param signData Will contain all the information acquired from the user for signing a Pdf 
	 * @param pdfFile The path to the Pdf document to be signed.
	 */
	public MarkWizardModel(SignData signData, String pdfFile, String rootDir, PreferenceManager preferenceManager) 
	{
		this.signData = signData;
		this.rootDir = rootDir;
		this.pdfFile = pdfFile;
		this.preferenceManager = preferenceManager;
		
		pdfDecoder = new PdfDecoder();
		try {
			pdfDecoder.openPdfFile(pdfFile);
		}
		catch (Exception e) {
            e.printStackTrace();
		}
		
		if(pdfDecoder.isEncrypted()) {
			String password =System.getProperty("org.jpedal.password");
			if(password != null) {
				try {
					pdfDecoder.setEncryptionPassword(password);
				} catch (PdfException e) {
					e.printStackTrace();
				}
			}
		}
		/*JPanel contents vary depending on whether the Pdf has bee previously signed.*/
		//testForSignedPDF();
		
		panels = new HashMap();
	    modeSelect = new ModeSelect();
	    keystorePanel = new KeystorePanel();
	    encryptionPanel = new EncryptionPanel();
	    signaturePanel = new SignaturePanel();
	    
	    panels.put(VISIBLE_SIGNATURE_PANEL, signaturePanel);
	    panels.put(KEYSTORE_PANEL, keystorePanel);
//	    panels.put(MODE_SELECT, modeSelect);
//	    panels.put(ENCRYPTION_PANEL, encryptionPanel);
	    
	    currentPanel = VISIBLE_SIGNATURE_PANEL;
	}
	
	/**
	 * A map of the JPanels the Wizard Dialog should contain.
	 * 
	 * @return The ID strings mapped to their corresponding JPanels
	 */
	public Map getJPanels()
	{
		return panels;
	}

	/**
	 * Advance to the next JPanel.
	 * 
	 * @return Unique identifier for the now current JPanel
	 */
	public String next()
	{
		updateSignData();
		System.out.println("currentPanel " + currentPanel);
		
		if(currentPanel.equals(VISIBLE_SIGNATURE_PANEL)) {
			return currentPanel = KEYSTORE_PANEL;
		}
//		else if(currentPanel.equals(MODE_SELECT)) {
//			if(!signData.isKeystoreSign()) {
//				return currentPanel = PFX_PANEL;
//			}
//			else {
//				return currentPanel = KEYSTORE_PANEL;
//			}	
//		}
//		else if(currentPanel.equals(PFX_PANEL)) {
//			return currentPanel = VISIBLE_SIGNATURE_PANEL;
//		}
//		else if(currentPanel.equals(KEYSTORE_PANEL)) {
//			return currentPanel = VISIBLE_SIGNATURE_PANEL;
//		}			
//		else if(currentPanel.equals(ENCRYPTION_PANEL)) {
//			return currentPanel = COMMON_PANEL;
//		}
		/* The following exception should never be thrown and is here to alerted me 
		 * should I create a trail of panels that is incorrect */
		throw new NullPointerException("Whoops! Tried to move to a nextID where there is no nextID to be had");
	}

	/**
	 * Set the current JPanel to the previous JPanel.
	 * 
	 * @return Unique identifier for the now current JPanel
	 */
	public String previous()
	{
		updateSignData();
		if(currentPanel.equals(KEYSTORE_PANEL)) {
			return currentPanel = VISIBLE_SIGNATURE_PANEL;
		}
//		if(currentPanel.equals(PFX_PANEL) || currentPanel.equals(KEYSTORE_PANEL)) {
//			return currentPanel = MODE_SELECT;
//		}
//		else if(currentPanel.equals(ENCRYPTION_PANEL)) {
//			return currentPanel = VISIBLE_SIGNATURE_PANEL;
//		}
//		else if(currentPanel.equals(VISIBLE_SIGNATURE_PANEL)) {
//			if(signData.isKeystoreSign()) {
//			    return currentPanel = KEYSTORE_PANEL;
//			}
//			else {
//				return currentPanel = PFX_PANEL;
//		    }
//		}
//		else if(currentPanel.equals(COMMON_PANEL)){
//            return currentPanel = ENCRYPTION_PANEL;
//		}

		throw new NullPointerException("Tried to move to get a previousID where there is no previous");
	}

	public boolean hasPrevious()
	{
		return !currentPanel.equals(VISIBLE_SIGNATURE_PANEL);
	}

	public String getStartPanelID()
	{
		return VISIBLE_SIGNATURE_PANEL;
	}
	
	public boolean isFinishPanel()
	{
		return currentPanel==KEYSTORE_PANEL;
	}
	
	/**
	 * Indicates whether the next or finish button can be enabled.
	 * 
	 * @return true if the current panel can be advanced in its current state
	 */
	public boolean canAdvance()
	{
		if(currentPanel.equals(KEYSTORE_PANEL)){
			return keystorePanel.canAdvance();
		}
//		else if(currentPanel.equals(COMMON_PANEL)) {
//			return commonPanel.canFinish();
//		}
//		else if(currentPanel.equals(PFX_PANEL)){
//		    return pFXPanel.canAdvance();
//		}
//		else if(currentPanel.equals(ENCRYPTION_PANEL)) {
//			return encryptionPanel.canAdvance();
//		}
		else {
			return true;
		}
	}
	
	/**
	 * Harvest user data from the currently displayed panel
	 */
	public void updateSignData()
	{
		if(currentPanel.equals(VISIBLE_SIGNATURE_PANEL)) {
			signaturePanel.collectData();
		}
		else if(currentPanel.equals(KEYSTORE_PANEL)) {
			keystorePanel.collectData();
		}
//		else if(currentPanel.equals(PFX_PANEL)) {
//			pFXPanel.collectData();
//		}
//		else if(currentPanel.equals(COMMON_PANEL)) {
//			commonPanel.collectData();
//		}
//		else if(currentPanel.equals(ENCRYPTION_PANEL)) {
//			encryptionPanel.collectData();
//		}
//		else if(currentPanel.equals(MODE_SELECT)) {
//			modeSelect.collectData();
//		}	
		else {
 			/*Should never be throw, here to indicate if I've made a mistake in the flow of the JPanels */
			throw new NullPointerException("Tried to update a panel which doesnt exist");
		}
	}
	
	/**
	 * When an event is triggered with one of the registered panels
	 * the wizard will call back this class and check if the panel can be advanced.
	 * 
	 * @param wizard Listeners to enable/disable advance button 
	 */
	public void registerNextChangeListeners(ChangeListener wizard)
	{
		keystorePanel.registerChange(wizard);
		encryptionPanel.registerChange(wizard);
	}
	
	/**
	 * Same as the previous method but listens for key changes instead.
	 * 
	  @param wizard Listeners to enable/disable advance button 
	 */
	public void registerNextKeyListeners(KeyListener wizard)
	{
		keystorePanel.registerNextKeyListeners(wizard);
		encryptionPanel.registerNextKeyListeners(wizard);
	}
	
	/**
	 * To avoid memory leaks I want to close the decoder I opened in this
	 * class when ever the dialog is closed.  Also collects any last
	 * data.
	 */
	public void close()
	{
		updateSignData();
		pdfDecoder.closePdfFile();
	}
	
	/**
	 * Don't want to corrupt any Pdf files so a check is performed
	 * to find whether a signature should be appended to the document
	 * or created fresh. 
	 */
	private void testForSignedPDF(){
		signData.setAppend(false);
		
		for(int page = 1; page<=pdfDecoder.getPageCount(); page++) {
		    try {
				pdfDecoder.decodePage(page);
				pdfDecoder.waitForDecodingToFinish();
		        AcroRenderer currentFormRenderer = pdfDecoder.getFormRenderer();
		        Iterator signatureObjects = currentFormRenderer.getSignatureObjects();
		        if(signatureObjects!=null) {
		        	signData.setAppend(true);
		        	break;
		        }
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	private boolean isPdfSigned() {
        return signData.isAppendMode();
	}
	
    private class ModeSelect extends JPanel 
	{ 
		private String selfString = Messages.getMessage("PdfSigner.HaveKeystore");
		private String otherString = Messages.getMessage("PdfSigner.HavePfx");
		private int y = 0;
		
		private JRadioButton selfButton = new JRadioButton(selfString);
		private String[] certifyOptions = {Messages.getMessage("PdfSigner.NotCertified"), Messages.getMessage("PdfSigner.NoChangesAllowed"), Messages.getMessage("PdfSigner.FormFilling"), Messages.getMessage("PdfSigner.FormFillingAndAnnotations")};	
		private JComboBox certifyCombo = new JComboBox(certifyOptions);
		
		public ModeSelect()
		{
			if(!signData.isAppendMode()) {
				certifyCombo = new JComboBox(certifyOptions);
			}
			else {
				String[] s = {"Not Allowed..."};
				certifyCombo = new JComboBox(s);
			}
			setLayout(new BorderLayout());
	        add(new TitlePanel(Messages.getMessage("PdfSigner.SelectSigningMode")), BorderLayout.NORTH);
						
			JPanel optionPanel = new JPanel();		
			optionPanel.setLayout(new GridBagLayout());
			//buttons.setAlignmentX(Component.CENTER_ALIGNMENT);
			GridBagConstraints c = new GridBagConstraints();
			
			
		    selfButton.setActionCommand(selfString);
		    //selfButton.setAlignmentX(Component.LEFT_ALIGNMENT);
		    c.gridx = 0;
		    c.gridy = y;
			c.anchor = GridBagConstraints.FIRST_LINE_START; //Has no effect
			c.fill = GridBagConstraints.HORIZONTAL; 
		    c.insets = new Insets(10,0,20,0);
		    selfButton.setFont(new Font("Dialog", Font.BOLD, 12));
		    optionPanel.add(selfButton,c);

		    JRadioButton otherButton = new JRadioButton(otherString);
		    otherButton.setActionCommand(otherString);
		    //otherButton.setAlignmentX(Component.LEFT_ALIGNMENT);
		    otherButton.setSelected(true);
		    signData.setSignMode(false);
		    c = new GridBagConstraints();
		    c.gridx = 0;
		    c.gridy = ++y;
		    c.fill = GridBagConstraints.HORIZONTAL; 
		    otherButton.setFont(new Font("Dialog", Font.BOLD, 12));
		    optionPanel.add(otherButton, c);
		    
		    c = new GridBagConstraints();
		    c.gridx = 0;
		    c.gridy = ++y;
		    c.fill = GridBagConstraints.HORIZONTAL; 
		    c.insets = new Insets(30,0,30,0);
		    optionPanel.add(new JSeparator(SwingConstants.HORIZONTAL), c);
		    	    
		    JLabel certifyLabel = new JLabel(Messages.getMessage("PdfSigner.CertificationAuthor"));
		    certifyLabel.setFont(new Font("Dialog", Font.BOLD, 12));
		    c = new GridBagConstraints();
		    c.gridx = 0;
		    c.gridy = ++y;
		    c.fill = GridBagConstraints.CENTER;
		    optionPanel.add(certifyLabel, c);
		    
		    c = new GridBagConstraints();
		    c.fill = GridBagConstraints.HORIZONTAL; 
		    c.gridx = 0;
		    c.gridy = ++y;
		    c.insets = new Insets(10,0,0,0);
			c.anchor = GridBagConstraints.PAGE_END;	
			certifyCombo.setEnabled(!isPdfSigned());
			certifyCombo.setSelectedIndex(0);
			certifyCombo.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					String mode = (String) certifyCombo.getSelectedItem();
				
				}
			});
		    optionPanel.add(certifyCombo, c);
		    
		    if(isPdfSigned()) {
		    	certifyCombo.setToolTipText(Messages.getMessage("PdfSigner.NotPermittedOnSigned"));
		    }
		    
		    add(optionPanel,BorderLayout.CENTER);
		    
		    ButtonGroup group = new ButtonGroup();
		    group.add(selfButton);
		    group.add(otherButton);
		    		    
		    add(new ProgressPanel(1), BorderLayout.SOUTH);
		}
		
		public void collectData()
		{
			signData.setSignMode(selfButton.isSelected());
		//	signData.setCertifyMode(certifyMode);
		}
	}
		
	private class KeystorePanel extends JPanel {		
		
		public final static String PROPERTY_HASHALGORITHM="firma.hashAlgorithm" ;
		public final static String PROPERTY_CERTIFYMODE="firma.certifyMode" ;
		public final static String PROPERTY_OUTPUTFILENAME="firma.outputFileName" ;
		public final static String PROPERTY_TSAURL="firma.tsaUrl" ;
		public final static String PROPERTY_PROXY_HOST="proxy.host" ;
		public final static String PROPERTY_PROXY_PORT="proxy.port" ;
		public final static String PROPERTY_PROXY_USER="proxy.user" ;
		public final static String PROPERTY_PROXY_PASSWORD="proxy.password" ;
		
		private JPasswordField jPin = new JPasswordField();
		private JLabel labelPin = new JLabel();
		private JLabel labelreader = new JLabel();
		private JLabel labelcard = new JLabel();
		private JLabel labelOS = new JLabel();
		private CardPresentThread cardPresentThread; 
		private JComboBox jcombocardreader = new JComboBox();
		private JProgressBar jProgressBar = new JProgressBar();
		private JLabel outputFileLabel = new JLabel();
		private JLabel currentOutputFilePath = new JLabel();
		private JButton browseOutputButton = new JButton();
		
		private JCheckBox enableCACheck = new JCheckBox(Messages.getMessage("PdfSigner.enableCACheck"));
		private JCheckBox enableCRLCheck = new JCheckBox(Messages.getMessage("PdfSigner.enableCRLCheck"));
		private JCheckBox enableOCSPCheck = new JCheckBox(Messages.getMessage("PdfSigner.enableOCSPCheck"));
		
		private JLabel reasonLabel = new JLabel();
		private JTextField signerReasonArea =new JTextField();

		private JLabel locationLabel = new JLabel();
		private JTextField signerLocationField =new JTextField();
		
		private JLabel contactLabel = new JLabel();
		private JTextField signerContactField =new JTextField();
		
		private volatile boolean canAdvance, storePassAdvance, aliasAdvance, aliasPassAdvance = false;
			
		public KeystorePanel()
		{
			try	{
				init();
			}
			catch( Exception e ) {
				e.printStackTrace();
			}
		}
		
		private void init() {
			setLayout(new BorderLayout());
	        add(new TitlePanel(Messages.getMessage("PdfSigner.KeyStoreMode")), BorderLayout.NORTH);
			
			JPanel inputPanel = new JPanel(new GridBagLayout());
			inputPanel.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));
			GridBagConstraints c = new GridBagConstraints();
			
			int y=0;
			
			labelOS.setText("sistema operativo");
			labelOS.setVisible( true );
			c.fill = GridBagConstraints.HORIZONTAL; 
			c.gridx = 0;
			c.gridy = y++;
			c.gridwidth = 4;
			c.insets = new Insets(0,20,0,10);
			inputPanel.add( labelOS, c );
			
			labelPin.setText("PIN");
			labelPin.setVisible( true );
			c.gridx = 0;
			c.gridy = y;
			c.gridwidth = 1;
			c.insets = new Insets(10,20,0,10);
			inputPanel.add( labelPin, c );
			
			c.gridx = 1;
			c.gridy = y++;
			c.gridwidth = 3;
			c.insets = new Insets(10,20,0,10);
			inputPanel.add( jPin, c );
			
			labelcard.setText("carta occupata");
			labelcard.setVisible(false);
			c.gridx = 0;
			c.gridy = y++;
			c.gridwidth = 4;
			c.insets = new Insets(10,20,0,10);
			inputPanel.add( labelcard, c );
			
			labelreader.setText("nessun lettore presente");
			c.gridx = 0;
			c.gridy = y++;
			c.gridwidth = 4;
			c.insets = new Insets(10,20,0,10);
			inputPanel.add( labelreader, c );
			
			jcombocardreader.addItemListener(new java.awt.event.ItemListener() {
				public void itemStateChanged(java.awt.event.ItemEvent e) {
					cardPresentThread.setCardName(jcombocardreader.getSelectedItem().toString());
				}
			});
			
			c.fill = GridBagConstraints.HORIZONTAL; 
			c.gridx = 0;
			c.gridy = y++;
			c.gridwidth = 4;
			c.insets = new Insets(10,20,0,10);
			inputPanel.add( jcombocardreader, c );
			
//			c.gridx = 0;
//			c.gridy = y++;
//			c.gridwidth = 4;
//			c.insets = new Insets(10,20,0,10);
//			inputPanel.add(new JSeparator(SwingConstants.HORIZONTAL), c);
			
			outputFileLabel.setText(Messages.getMessage("PdfSigner.OutputFile"));
			outputFileLabel.setFont(new java.awt.Font( "Dialog", Font.BOLD, 14 ));
			c.gridx = 0;
			c.gridy = y;
			c.gridwidth = 1;
			c.insets = new Insets(10,20,0,10);
			outputFileLabel.setVisible( false );
			inputPanel.add(outputFileLabel, c);

			c.gridx = 1;
			c.gridy = y;
			c.insets = new Insets(10,0,0,0);
			c.gridwidth = 2;
			currentOutputFilePath.setPreferredSize(new Dimension(100, 20));
			currentOutputFilePath.setVisible( false );
			inputPanel.add(currentOutputFilePath, c);
			
			browseOutputButton.setText(Messages.getMessage("PdfViewerOption.Browse"));
			browseOutputButton.setVisible( false );
			browseOutputButton.addActionListener( new ActionListener() {
				public void actionPerformed( ActionEvent e ){
					JFileChooser chooser = new JFileChooser(rootDir);
					int state = chooser.showSaveDialog(null);

					File file = chooser.getSelectedFile();

					if (file != null && state == JFileChooser.APPROVE_OPTION) {
						if(file.exists()) {
							JOptionPane.showMessageDialog(null,
									Messages.getMessage("PdfSigner.PleaseChooseAnotherFile"),
									Messages.getMessage("PdfViewerGeneralError.message"),
									JOptionPane.ERROR_MESSAGE);
							canAdvance = false;
							currentOutputFilePath.setText(NO_FILE_SELECTED);
							signData.setOutputFilePath(null);
						}
						else if(file.isDirectory()) {
							JOptionPane.showMessageDialog(null,
									Messages.getMessage("PdfSigner.NoFileSelected"),
									Messages.getMessage("PdfViewerGeneralError.message"),
									JOptionPane.ERROR_MESSAGE);			
							canAdvance = false;
							currentOutputFilePath.setText(NO_FILE_SELECTED);
						}
						else {
							currentOutputFilePath.setText(file.getAbsolutePath());
							canAdvance = true;
						}
					}
				}
			} );
			c.fill = GridBagConstraints.HORIZONTAL; 
			c.gridx = 3;
			c.gridy = y++;
			c.gridwidth = 1;
			c.insets = new Insets(5,25,0,25);
			c.anchor = GridBagConstraints.LAST_LINE_END;
			inputPanel.add(browseOutputButton, c);  
			
			ConfigurationThread thread = new ConfigurationThread(jProgressBar, jPin,this);
			thread.start();
			
			c.gridx = 0;
			c.gridy = y++;
			c.gridwidth = 4;
			c.insets = new Insets(10,20,0,10);
			inputPanel.add(new JSeparator(SwingConstants.HORIZONTAL), c);
			
			c.gridx = 0;
			c.gridy = y;
			c.gridwidth = 2;
			c.insets = new Insets(10,20,0,10);
			inputPanel.add(enableCACheck, c);
			
			c.gridx = 2;
			c.gridy = y++;
			c.gridwidth = 2;
			c.insets = new Insets(10,20,0,10);
			inputPanel.add(enableCRLCheck, c);
			
			c.gridx = 0;
			c.gridy = y++;
			c.gridwidth = 2;
			c.insets = new Insets(10,20,0,10);
			inputPanel.add(enableOCSPCheck, c);
			
			c.gridx = 0;
			c.gridy = y++;
			c.gridwidth = 4;
			c.insets = new Insets(10,20,0,10);
			inputPanel.add(new JSeparator(SwingConstants.HORIZONTAL), c);
			
			reasonLabel.setText(Messages.getMessage("PdfSigner.Reason") + ':');
			reasonLabel.setFont(new java.awt.Font( "Dialog", Font.BOLD, 14 ));
			c.anchor = GridBagConstraints.FIRST_LINE_START; //Has no effect
			c.fill = GridBagConstraints.HORIZONTAL; 
			c.gridx = 0;
			c.gridy = y;
			c.gridwidth = 1;
			c.insets = new Insets(10,20,0,10);
			inputPanel.add(reasonLabel, c);

			c.gridx = 1;
			c.gridy = y++;
			c.gridwidth = 3;
			c.insets = new Insets(10,20,0,10);
			signerReasonArea.setPreferredSize(new Dimension(200,20));
			inputPanel.add(signerReasonArea, c);

			//Location
			locationLabel.setText(Messages.getMessage("PdfSigner.Location")+ ':');
			locationLabel.setFont(new java.awt.Font( "Dialog", Font.BOLD, 14 ));
			c.fill = GridBagConstraints.HORIZONTAL; 
			c.gridx = 0;
			c.gridy = y;
			c.gridwidth = 1;
			c.insets = new Insets(10,20,0,10);
			inputPanel.add(locationLabel, c);

			c.fill = GridBagConstraints.HORIZONTAL; 
			c.gridx = 1;
			c.gridy = y++;
			c.gridwidth = 3;
			c.insets = new Insets(10,20,0,10);
			signerLocationField.setPreferredSize(new Dimension(200,20));
			inputPanel.add(signerLocationField, c);

			//Contact
			contactLabel.setText(Messages.getMessage("PdfSigner.Contact")+ ':');
			contactLabel.setFont(new java.awt.Font( "Dialog", Font.BOLD, 14 ));
			c.fill = GridBagConstraints.HORIZONTAL; 
			c.gridx = 0;
			c.gridy = y;
			c.gridwidth = 1;
			c.insets = new Insets(10,20,0,10);
			inputPanel.add(contactLabel, c);

			c.fill = GridBagConstraints.HORIZONTAL; 
			c.gridx = 1;
			c.gridy = y++;
			c.gridwidth = 3;
			c.insets = new Insets(10,20,0,10);
			signerLocationField.setPreferredSize(new Dimension(200,20));
			inputPanel.add(signerContactField, c);
	
			add(inputPanel, BorderLayout.CENTER);
			
			add(new ProgressPanel(2), BorderLayout.SOUTH);
		
			//disabilitare pannello ((SmartCard)card).getJTabbedPan
		}
		
		public void registerChange(ChangeListener e)
		{
			//browseKeyStoreButton.addChangeListener(e);
		}
		
		public void registerNextKeyListeners(KeyListener e)
		{
//			passwordKeyStoreField.addKeyListener(e);
//			aliasNameField.addKeyListener(e);
//			aliasPasswordField.addKeyListener(e);
		}
		
		public boolean canAdvance()
		{
			return true; //storeAdvance && storePassAdvance && aliasAdvance && aliasPassAdvance;
		}	
		
		public void collectData()	{
			cardPresentThread.stopThread();
			jcombocardreader.setEnabled(false);
			int slot = jcombocardreader.getSelectedIndex();
			signData.setSlot( slot );
			signData.setPin( jPin.getPassword() );

			if( currentOutputFilePath.getText()!=null && !currentOutputFilePath.getText().equalsIgnoreCase("") ){
				signData.setOutputFilePath( currentOutputFilePath.getText() );
			} else {
				String outputFilePathName ="";
				try {
					outputFilePathName = preferenceManager.getConfiguration().getString( PROPERTY_OUTPUTFILENAME );
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				String outputFilePath = pdfFile.replace(".pdf", outputFilePathName+".pdf");
				signData.setOutputFilePath( outputFilePath );
			}

			signData.setOcspEnabled( enableOCSPCheck.isSelected() );
			

			if( signerLocationField.getText()!=null )
				signData.setLocation( signerLocationField.getText() );
			if( signerReasonArea.getText()!=null )
				signData.setReason( signerReasonArea.getText() );
			if( signerContactField.getText()!=null )
				signData.setContact( signerContactField.getText() );

			String algoritmoHash="SHA-256";
			try {
				algoritmoHash = preferenceManager.getConfiguration().getString( PROPERTY_HASHALGORITHM );
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			signData.setHashAlgorithm( HashAlgorithm.fromValue( algoritmoHash ) );
			String certifyMode="certificationLevel.notCertified";
			try {
				certifyMode = preferenceManager.getConfiguration().getString( PROPERTY_CERTIFYMODE );
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			signData.setCertifyMode( CertificationLevel.findCertificationCode( certifyMode ));
			
			System.out.println("Abilito la marcatura");
			signData.setTimestamp( true );
			
			String tsaUrl="";
			try {
				tsaUrl = preferenceManager.getConfiguration().getString( PROPERTY_TSAURL );
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			signData.setTsaUrl( tsaUrl );
			
			String proxyHost="";
			try {
				proxyHost = preferenceManager.getConfiguration().getString( PROPERTY_PROXY_HOST );
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			signData.setProxyHost(proxyHost);
			
			
			String proxyPort="";
			try {
				proxyPort = preferenceManager.getConfiguration().getString( PROPERTY_PROXY_PORT );
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			signData.setProxyPort(proxyPort);
			
			String proxyUser="";
			try {
				proxyUser = preferenceManager.getConfiguration().getString( PROPERTY_PROXY_USER );
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			signData.setProxyUser( proxyUser );
			
			String proxyPassword="";
			try {
				proxyPassword = preferenceManager.getConfiguration().getString( PROPERTY_PROXY_PASSWORD );
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			signData.setProxyPassword( proxyPassword );
		}
		
		public void settingReader(List<CardTerminal> terminallist){
			if(jcombocardreader.getItemCount()!=terminallist.size()){
				labelreader.setVisible(false);
				jcombocardreader.setVisible(true);
				//Carico i valori
				jcombocardreader.removeAllItems();
				for(CardTerminal terminal:terminallist){
					jcombocardreader.addItem(terminal.getName());
					jcombocardreader.setSelectedIndex(0);
				}
			}
		}
		
		public void setThreadControl(CardPresentThread thread){
			cardPresentThread = thread;
		}
		
		public void setCardPresent(boolean ispresent){
			if(ispresent){
				jPin.setVisible(true);
				labelcard.setVisible(false);
			}else{
				jPin.setVisible(false);
				labelcard.setVisible(true);
			}
		}
		
		public void clearReader(){
			try{
				jcombocardreader.removeAllItems();
			}catch(Exception e){
				e.printStackTrace();
			}
			labelreader.setVisible(true);
			jcombocardreader.setVisible(false);
		}
		
		/* */
		private class ConfigurationThread extends Thread {
			private JProgressBar bar;
			private JPasswordField pin;
			private KeystorePanel sign;

			public ConfigurationThread(JProgressBar bar,JPasswordField pin,KeystorePanel panelsign) {
				this.bar = bar; 
				this.pin = pin;
				this.sign = panelsign;
			}
			@Override
			public void run() {
				bar.setVisible(true);
				bar.setStringPainted(true);
				try {
					Factory fact = new Factory();
					fact.copyProvider(bar, preferenceManager);
				
					String osname = System.getProperty("os.name");
					sign.labelOS.setText(osname);
					
					TerminalFactory factory = TerminalFactory.getDefault();
					CardTerminals terminale =  factory.terminals();
					
					CardPresentThread cardpresetnthread = new CardPresentThread(sign, terminale);
					cardpresetnthread.start();
				
					sign.setThreadControl(cardpresetnthread);
		 
				} catch (Exception e) {
					e.printStackTrace();
				}
				bar.setVisible(false);
				bar.setStringPainted(false);
				 
				pin.setVisible(true);
			}	
		}
		
		
		/* */
		private class CardPresentThread extends Thread {

			public CardPresentThread(KeystorePanel panelsign,CardTerminals cardterminals){
				this.sign = panelsign;
				this.cardterminals = cardterminals;
			}
			
			private KeystorePanel sign;
			private CardTerminals cardterminals = null;	
			private boolean loop = true;
			private String cardname = null;
			private  boolean emulation=true;//emulatore
			
			public void stopThread(){
				this.loop = false;
			}

			public void setCardName(String cardname){
				this.cardname = cardname;
			}
			
			@Override
			public void run(){
				while(loop){
					try{
						List<CardTerminal> listcardterminal = new ArrayList<CardTerminal>();
						if(emulation){
							//emulazione terminale sw per test 
							//sarebbe piu' corettto fare un provider 
							listcardterminal.add(new VirtualCard());
							sign.settingReader(listcardterminal);
							sign.setCardPresent(true);
						}else{

							//Recupero le carte
						 listcardterminal =cardterminals.list();

							//Setto le carte presenti
							sign.settingReader(listcardterminal);

							//controllo se ne ho una settata
							if(cardname != null){
								CardTerminal terminal = cardterminals.getTerminal(cardname);
								if(terminal.isCardPresent()){
									sign.setCardPresent(true);
								}else{
									sign.setCardPresent(false);
								}
							}
						}
					}catch (Exception e) {
						 
						sign.clearReader();
						sign.setCardPresent(false);
				 
						
					}finally{
						try{
							Thread.sleep(1000);
						}catch(Exception e){}
					}
				}
			}
		}
	}
	
   private class EncryptionPanel extends JPanel
	{	
		private JCheckBox encryptionCheck = new JCheckBox("Encrypt"); 
		private JCheckBox allowPrinting = new JCheckBox("Allow Printing");
		private JCheckBox allowModifyContent = new JCheckBox("Allow Content Modification");
		private JCheckBox allowCopy = new JCheckBox("Allow Copy");
		private JCheckBox allowModifyAnnotation = new JCheckBox("Allow Annotation Modification");
		private JCheckBox allowFillIn = new JCheckBox("Allow Fill In");
		private JCheckBox allowScreenReader = new JCheckBox("Allow Screen Reader");
		private JCheckBox allowAssembly = new JCheckBox("Allow Assembly");
		private JCheckBox allowDegradedPrinting = new JCheckBox("Allow Degraded Printing");
		private JPasswordField userPassword = new JPasswordField();
		private JPasswordField ownerPassword = new JPasswordField();
		private JCheckBox flatten = new JCheckBox("Flatten PDF");	
		
		private JCheckBox visiblePassUserCheck = new JCheckBox();
		private JCheckBox visiblePassOwnerCheck = new JCheckBox();
		private boolean ownerAdvance = false;
		private volatile boolean canAdvance = true;
		
		public EncryptionPanel()
		{
			int y = 0;
			setLayout(new BorderLayout());
			add(new TitlePanel(Messages.getMessage("PdfSigner.EncryptionOptions")), BorderLayout.NORTH);

			JPanel optionPanel = new JPanel();		
			optionPanel.setLayout(new GridBagLayout());

			GridBagConstraints c = new GridBagConstraints();
			c.gridx = 0;
			c.gridy = y;
			c.fill = GridBagConstraints.HORIZONTAL; 
			c.anchor = GridBagConstraints.PAGE_START;
			//encryptionCheck.setFont(new Font("Dialog", Font.BOLD, 12));
			encryptionCheck.setEnabled(!isPdfSigned());
			encryptionCheck.addActionListener(new ActionListener () {
				public void actionPerformed(ActionEvent e) {
					canAdvance = !encryptionCheck.isSelected() || ownerAdvance;
				}
			});

			optionPanel.add(encryptionCheck, c);
			encryptionCheck.setSelected(false);

			c = new GridBagConstraints();
			c.gridx = 2;
			c.gridy = y;
			c.fill = GridBagConstraints.HORIZONTAL; 
			c.anchor = GridBagConstraints.FIRST_LINE_END;
			flatten.setEnabled(!isPdfSigned());
			optionPanel.add(flatten, c);
			
			c = new GridBagConstraints();
			c.gridx = 0;
			c.gridy = ++y;
			c.gridwidth = 3;
			c.fill = GridBagConstraints.HORIZONTAL; 
			optionPanel.add(new JSeparator(SwingConstants.HORIZONTAL), c);

	        //Encryption Options.			
			allowPrinting.setEnabled(false);
			c = new GridBagConstraints();
			c.gridx = 0;
			c.gridy = ++y;
			c.fill = GridBagConstraints.HORIZONTAL; 
			optionPanel.add(allowPrinting, c);
			allowModifyContent.setEnabled(false);
			c = new GridBagConstraints();
			c.gridx = 1;
			c.gridy = y;
			c.fill = GridBagConstraints.HORIZONTAL; 
			c.gridwidth = 2;
			optionPanel.add(allowModifyContent, c);
			
			allowCopy.setEnabled(false);
			c = new GridBagConstraints();
			c.gridx = 0;
			c.gridy = ++y;
			c.fill = GridBagConstraints.HORIZONTAL; 
			optionPanel.add(allowCopy, c);
			allowModifyAnnotation.setEnabled(false);
			c = new GridBagConstraints();
			c.gridx = 1;
			c.gridy = y;
			c.fill = GridBagConstraints.HORIZONTAL; 
			c.gridwidth = 2;
			optionPanel.add(allowModifyAnnotation, c);
			
			allowFillIn.setEnabled(false);
			c = new GridBagConstraints();
			c.gridx = 0;
			c.gridy = ++y;
			c.fill = GridBagConstraints.HORIZONTAL; 
			optionPanel.add(allowFillIn, c);
			allowScreenReader.setEnabled(false);
			c = new GridBagConstraints();
			c.gridx = 1;
			c.gridy = y;
			c.fill = GridBagConstraints.HORIZONTAL; 
			c.gridwidth = 2;
			optionPanel.add(allowScreenReader, c);
			
			allowAssembly.setEnabled(false);
			c = new GridBagConstraints();
			c.gridx = 0;
			c.gridy = ++y;
			c.fill = GridBagConstraints.HORIZONTAL; 
			optionPanel.add(allowAssembly, c);
			allowDegradedPrinting.setEnabled(false);
			c = new GridBagConstraints();
			c.gridx = 1;
			c.gridy = y;
			c.fill = GridBagConstraints.HORIZONTAL; 
			c.gridwidth = 2;
			optionPanel.add(allowDegradedPrinting, c);
			
			c = new GridBagConstraints();
			c.gridx = 0;
			c.gridy = ++y;
			c.gridwidth = 3;
			c.fill = GridBagConstraints.HORIZONTAL; 
			optionPanel.add(new JSeparator(SwingConstants.HORIZONTAL), c);
			
			c = new GridBagConstraints();
			c.gridx = 0;
			c.gridy = ++y;
			c.fill = GridBagConstraints.HORIZONTAL; 
			c.insets= new Insets(5,0,0,0);
			optionPanel.add(new JLabel(Messages.getMessage("PdfSigner.UserPassword")), c);
			c = new GridBagConstraints();
			c.gridx = 1;
			c.gridy = y;
			c.fill = GridBagConstraints.HORIZONTAL; 
			c.insets= new Insets(5,0,0,0);
			userPassword.setEnabled(false);
			userPassword.setPreferredSize(new Dimension(100,20));
			userPassword.addKeyListener(new KeyListener () {
				public void keyReleased(KeyEvent e) {
                    
				}
				
				public void keyPressed(KeyEvent e) {
					
				}
				
				public void keyTyped(KeyEvent e) {
					ownerAdvance = true;
					canAdvance = true;
				}
			});
			optionPanel.add(userPassword, c);
			
			c = new GridBagConstraints();
			c.fill = GridBagConstraints.HORIZONTAL; 
			c.gridx = 2;
			c.gridy = y;
			c.insets = new Insets(0,0,0,0);
			visiblePassUserCheck.setToolTipText(Messages.getMessage("PdfSigner.ShowPassword"));
			visiblePassUserCheck.addActionListener(new ActionListener() {
				private char defaultChar;
				
				public void actionPerformed(ActionEvent e) {
					if(visiblePassUserCheck.isSelected()) {
						defaultChar = userPassword.getEchoChar();
						userPassword.setEchoChar((char) 0);
					}
					else {
						userPassword.setEchoChar(defaultChar);
					}
				}
			});
			visiblePassUserCheck.setEnabled(false);
			optionPanel.add(visiblePassUserCheck, c);
			
			c = new GridBagConstraints();
			c.gridx = 0;
			c.gridy = ++y;
			c.fill = GridBagConstraints.HORIZONTAL; 
			c.insets= new Insets(5,0,0,0);
			optionPanel.add(new JLabel(Messages.getMessage("PdfSigner.OwnerPassword")), c);
			c = new GridBagConstraints();
			c.gridx = 1;
			c.gridy = y;
			c.fill = GridBagConstraints.HORIZONTAL; 
			c.insets= new Insets(5,0,0,0);
			ownerPassword.setEnabled(false);
			ownerPassword.setPreferredSize(new Dimension(100,20));
			optionPanel.add(ownerPassword, c);
			
			c = new GridBagConstraints();
			c.fill = GridBagConstraints.HORIZONTAL; 
			c.gridx = 2;
			c.gridy = y;
			c.insets = new Insets(0,0,0,0);
			visiblePassOwnerCheck.setToolTipText(Messages.getMessage("PdfSigner.ShowPassword"));
			visiblePassOwnerCheck.addActionListener(new ActionListener() {
				private char defaultChar;
				
				public void actionPerformed(ActionEvent e) {
					if(visiblePassOwnerCheck.isSelected()) {
						defaultChar = ownerPassword.getEchoChar();
						ownerPassword.setEchoChar((char) 0);
					}
					else {
						ownerPassword.setEchoChar(defaultChar);
					}
				}
			});
			visiblePassOwnerCheck.setEnabled(false);
			optionPanel.add(visiblePassOwnerCheck, c);
						
			if(isPdfSigned()) {
				c = new GridBagConstraints();
				c.fill = GridBagConstraints.HORIZONTAL; 
				c.gridx = 0;
				c.gridy = ++y;
				c.gridwidth = 3;
				c.insets = new Insets(25,0,0,0);
				JLabel notAvailable = new JLabel(Messages.getMessage("PdfSigner.DisabledSigned"), JLabel.CENTER);
				notAvailable.setForeground(Color.red);
				optionPanel.add(notAvailable, c);
			}
			
			encryptionCheck.addItemListener(new ItemListener() {
				public void itemStateChanged(ItemEvent e) {
					boolean enable = e.getStateChange()== ItemEvent.SELECTED;
					allowPrinting.setEnabled(enable);
					allowModifyContent.setEnabled(enable);
					allowCopy.setEnabled(enable);
					allowModifyAnnotation.setEnabled(enable);
					allowFillIn.setEnabled(enable);
					allowScreenReader.setEnabled(enable);
					allowAssembly.setEnabled(enable);
					allowDegradedPrinting.setEnabled(enable); 
					userPassword.setEnabled(enable); 
					ownerPassword.setEnabled(enable); 	
					visiblePassUserCheck.setEnabled(enable);
					visiblePassOwnerCheck.setEnabled(enable);	
				}
			});
			
			add(optionPanel, BorderLayout.CENTER);
			add(new ProgressPanel(4), BorderLayout.SOUTH);
		}
					
		public void registerChange(ChangeListener wizard) {
			encryptionCheck.addChangeListener(wizard);
		}

		public void registerNextKeyListeners(KeyListener wizard) {
			userPassword.addKeyListener(wizard);
		}
		
		public boolean canAdvance()
		{
			return canAdvance;
		}

		public void collectData()
		{
			signData.setFlatten(flatten.isSelected());
			signData.setEncrypt(encryptionCheck.isSelected());
			if(encryptionCheck.isSelected()) {
				signData.setEncryptUserPass(userPassword.getPassword());
				signData.setEncryptOwnerPass(ownerPassword.getPassword());
				
				int result = 0;
				
				if(allowPrinting.isSelected()) result |= ItextFunctions.ALLOW_PRINTING;
				if(allowModifyContent.isSelected()) result |= ItextFunctions.ALLOW_MODIFY_CONTENTS;
				if(allowCopy.isSelected()) result |= ItextFunctions.ALLOW_COPY;
				if(allowModifyAnnotation.isSelected()) result |= ItextFunctions.ALLOW_MODIFY_ANNOTATIONS;
				if(allowFillIn.isSelected()) result |= ItextFunctions.ALLOW_FILL_IN;
				if(allowScreenReader.isSelected()) result |= ItextFunctions.ALLOW_SCREENREADERS;
				if(allowAssembly.isSelected()) result |= ItextFunctions.ALLOW_ASSEMBLY;
				if(allowDegradedPrinting.isSelected()) result |= ItextFunctions.ALLOW_DEGRADED_PRINTING;
				
				signData.setEncryptPermissions(result);
			}
		}
	}
	
    private class SignaturePanel extends JPanel {
		
    	public final static String PROPERTY_RENDERMODE="firma.renderMode" ;
    	public final static String PROPERTY_APPENDMODE="firma.appendMode" ;
    	public final static String PROPERTY_ACRO6LAYER="firma.acro6Layer" ;	
    	public final static String PROPERTY_L2TEXT="firma.l2Text" ;
    	public final static String PROPERTY_L4TEXT="firma.l4Text" ;
    	public final static String PROPERTY_L2TEXTFONTSIZE="firma.l2TextFontSize" ;
    	public final static String PROPERTY_IMGPATH="firma.imgPath" ;
    	public final static String PROPERTY_BGIMGPATH="firma.bgImgPath" ;
    	public final static String PROPERTY_BGIMGSCALE="firma.bgImgScale" ;
    	
    	private JCheckBox visibleCheck = new JCheckBox(Messages.getMessage("PdfSigner.VisibleSignature")); 
		private JCheckBox appendSignCheck = new JCheckBox(Messages.getMessage("PdfSigner.AppendSignature")); 
		private JComponent sigPreviewComp;
		private JSlider pageSlider;
		private JLabel pageNumberLabel;
		JLabel labelDisplayMode = new JLabel();
		JComboBox comboDisplayMode = new JComboBox();
		
		private int currentPage = 1;
		private Point signRectOrigin;
		private Point signRectEnd;
		private int offsetX, offsetY;
		
		private float scale;
		private int previewWidth, previewHeight;
		private volatile boolean drawRect = false;
		private boolean signAreaUndefined = true;
		
		private BufferedImage previewImage;
		
		public SignaturePanel() 
		{        
			try {
				previewImage = pdfDecoder.getPageAsImage(currentPage);
			}
			catch (Exception e) {
                //tell user and log
                logger.info("Exception: "+e.getMessage());
			}
			
			int y = 0;
			setLayout(new BorderLayout());
			add(new TitlePanel(Messages.getMessage("PdfViewerMenu.options") + ' ' +
					Messages.getMessage("PdfSigner.Signature")) , BorderLayout.NORTH);

			JPanel optionPanel = new JPanel();		
			optionPanel.setLayout(new GridBagLayout());

			GridBagConstraints c = new GridBagConstraints();
			c.gridx = 0;
			c.gridy = y++;
			c.insets = new Insets(5,0,0,0);
			c.fill = GridBagConstraints.HORIZONTAL; 
			c.gridwidth = 2;
			c.anchor = GridBagConstraints.PAGE_START;
			appendSignCheck.setVisible( false );
			optionPanel.add(appendSignCheck, c);
			
			c.gridx = 0;
			c.gridy = y++;
			c.insets = new Insets(5,0,0,0);
			c.fill = GridBagConstraints.HORIZONTAL; 
			c.gridwidth = 2;
			visibleCheck.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					sigPreviewComp.repaint();
					if (pdfDecoder.getPageCount()>1)
					    pageSlider.setEnabled(visibleCheck.isSelected());
					comboDisplayMode.setEnabled(visibleCheck.isSelected());
				}
				
			});
			optionPanel.add(visibleCheck, c);
			
			c.gridx = 0;
			c.gridy = y++;
			c.fill = GridBagConstraints.HORIZONTAL; 
			c.insets = new Insets(10,0,10,0);
			c.gridwidth = 2;
			optionPanel.add(new JSeparator(SwingConstants.HORIZONTAL), c);
			
			c.gridx = 0;
			c.gridy = y++;
			c.gridwidth = 2;
			c.insets = new Insets(0,0,0,0);
			c.fill = GridBagConstraints.HORIZONTAL; 
			optionPanel.add(previewPanel(), c);
			
			c.gridx = 0;
			c.gridy = y++;
			c.fill = GridBagConstraints.HORIZONTAL; 
			c.insets = new Insets(10,0,10,0);
			c.gridwidth = 2;
			optionPanel.add(new JSeparator(SwingConstants.HORIZONTAL), c);
			
//			c.gridx = 0;
//			c.gridy = y;
//			c.gridwidth = 1;
//			c.fill = GridBagConstraints.HORIZONTAL; 
//			labelDisplayMode.setLabelFor(comboDisplayMode);
//			labelDisplayMode.setText("Display");
//		    optionPanel.add(labelDisplayMode, c);
//
//		    c.gridx = 1;
//			c.gridy = y++;
//			c.fill = GridBagConstraints.HORIZONTAL;
//			c.gridwidth = 1;
//		    comboDisplayMode.setModel(new DefaultComboBoxModel(RenderMode.values()));
//		    comboDisplayMode.setPreferredSize(new java.awt.Dimension(200, 20));
//		    comboDisplayMode.setEnabled(false);
//		    optionPanel.add(comboDisplayMode, c);
				
			add(optionPanel, BorderLayout.CENTER);
			
			add(new ProgressPanel(1), BorderLayout.SOUTH);
			
		}
		
		public void collectData() {
			//signData.setAppend(appendSignCheck.isSelected());
			Boolean appendMode=true;
			try {
				appendMode = Boolean.parseBoolean(preferenceManager.getConfiguration().getString( PROPERTY_APPENDMODE ));
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			signData.setAppend(appendMode);
			
//			Boolean acro6Layers=false;
//			try {
//				acro6Layers = Boolean.parseBoolean(preferenceManager.getConfiguration().getString( PROPERTY_ACRO6LAYER ));
//			} catch (Exception e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//			signData.setAcro6Layers(acro6Layers);
			
			String renderMode="render.descriptionOnly";
			try {
				renderMode = preferenceManager.getConfiguration().getString( PROPERTY_RENDERMODE );
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			signData.setRenderMode( RenderMode.fromValue( renderMode ) );
			
			Float l2TextFontSize=10.0f;
			try {
				l2TextFontSize = Float.parseFloat(preferenceManager.getConfiguration().getString( PROPERTY_L2TEXTFONTSIZE ));
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			signData.setL2TextFontSize(l2TextFontSize);
			
			String l2Text="";
			try {
				l2Text = preferenceManager.getConfiguration().getString( PROPERTY_L2TEXT );
				Date dataCorrente = new Date();
				SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
				String dataCorrenteString = formatter.format( dataCorrente );
				l2Text = l2Text.replace("$dataCorrente", dataCorrenteString );
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			signData.setL2Text(l2Text);
			
			String l4Text="";
			try {
				l4Text = preferenceManager.getConfiguration().getString( PROPERTY_L4TEXT );
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			signData.setL4Text( l4Text );
			
			if( signData.getRenderMode()==RenderMode.GRAPHIC_AND_DESCRIPTION){
				String imgPath="";
				try {
					imgPath = preferenceManager.getConfiguration().getString( PROPERTY_IMGPATH );
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				signData.setImgPath(imgPath);
				String bgImgPath="";
				try {
					bgImgPath = preferenceManager.getConfiguration().getString( PROPERTY_BGIMGPATH );
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				signData.setBgImgPath(bgImgPath);
				Float bgImgScale=10.0f;
				try {
					bgImgScale = Float.parseFloat(preferenceManager.getConfiguration().getString( PROPERTY_BGIMGSCALE ));
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				signData.setBgImgScale(bgImgScale);
			}
			
			
			signData.setVisibleSignature(visibleCheck.isSelected());
			if(visibleCheck.isSelected()) {
				int height = previewImage.getHeight();
                int x1 = (int) ((signRectOrigin.getX() - offsetX) / scale);
                int y1 = (int) ( height - ((signRectOrigin.getY() - offsetY) / scale));
				int x2 = (int) ((signRectEnd.getX() - offsetX) / scale);
				int y2 = (int) (height - ((signRectEnd.getY() - offsetY) / scale));
				
				PdfPageData pageData = pdfDecoder.getPdfPageData();
				int cropX = pageData.getCropBoxX(currentPage);
				int cropY = pageData.getCropBoxY(currentPage);
				x1 += cropX;
				y1 += cropY;
				x2 += cropX;
				y2 += cropY;
				
				signData.setRectangle(x1,y1,x2,y2);
				signData.setSignPage(currentPage);
			} else {
				signData.setSignPage(1);
			}
		}
	
		private JPanel previewPanel() 
		{
			JPanel result = new JPanel(new BorderLayout());
											
			sigPreviewComp = new JComponent() {			
	            public void paintComponent(Graphics g){
	                sigPreview(g);
	            }
	        };
			sigPreviewComp.setPreferredSize(new Dimension(200,200));
			sigPreviewComp.setToolTipText(Messages.getMessage("PdfSigner.ClickAndDrag"));
	        sigPreviewComp.addMouseListener(new MouseListener() {

				public void mouseClicked(MouseEvent e) {
				}

				public void mouseEntered(MouseEvent e) {
				}

				public void mouseExited(MouseEvent e) {
				}

				public void mousePressed(MouseEvent e) {
					if(visibleCheck.isSelected()) {
						signRectOrigin.setLocation(e.getX(), e.getY());
						drawRect = true;
						
						Thread rect = new Thread(signAreaThread());
						rect.start();
					}
				}

				public void mouseReleased(MouseEvent e) {
					if(visibleCheck.isSelected()) {
					    drawRect = false;
					    sigPreviewComp.repaint();
					}
				}
         
			});
			
			result.add(sigPreviewComp, BorderLayout.CENTER);
			
			//Add a slider if there is more than one page
			if(pdfDecoder.getPageCount()>1) {
				pageNumberLabel = new JLabel (Messages.getMessage("PdfSigner.PageNumber") + ' ' + currentPage);
				pageNumberLabel.setHorizontalAlignment(SwingConstants.CENTER);
				result.add(pageNumberLabel, BorderLayout.NORTH);
				
				pageSlider = new JSlider(JSlider.HORIZONTAL, 1, pdfDecoder.getPageCount(), currentPage);
				pageSlider.setMajorTickSpacing(pdfDecoder.getPageCount() - 1);
				pageSlider.setPaintLabels(true);

				pageSlider.addChangeListener(new ChangeListener() {
					public void stateChanged(ChangeEvent e)
					{
                        if(pageSlider.getValueIsAdjusting()) {
                        	currentPage = pageSlider.getValue();
                			try {
                				previewImage = pdfDecoder.getPageAsImage(currentPage);
                				sigPreviewComp.repaint();
                				pageNumberLabel.setText(Messages.getMessage("PdfSigner.PageNumber") + ' ' + currentPage);
                			}
                			catch (Exception ex) {
                                //tell user and log
                                logger.info("Exception: "+ex.getMessage());
                			}
                        }
					}
				});
				result.add(pageSlider, BorderLayout.SOUTH);		
				pageSlider.setEnabled(false);
			}

			
			return result;
		}
		
		private void sigPreview(Graphics g) 
		{
			int panelWidth = sigPreviewComp.getWidth();
			int panelHeight = sigPreviewComp.getHeight();
			previewWidth = previewImage.getWidth();
			previewHeight = previewImage.getHeight();

			scale = (previewWidth>previewHeight) ? (float) panelWidth / previewWidth : (float) panelHeight / previewHeight; 

			previewWidth *= scale;
			previewHeight *= scale;
			offsetX = (panelWidth - previewWidth) / 2;
			offsetY =  (panelHeight - previewHeight) / 2;


			g.drawImage(previewImage, offsetX , offsetY, previewWidth, previewHeight, null);   

			if(visibleCheck.isSelected()) {
				g.clipRect(offsetX, offsetY, previewWidth, previewHeight);
				drawSignBox(g);
			}
		}
		
		private void drawSignBox(Graphics g)
		{
			if(signAreaUndefined) {
				PdfPageData pageData = pdfDecoder.getPdfPageData();
				signRectOrigin = new Point(offsetX,offsetY);
				signRectEnd = new Point((int) (pageData.getCropBoxWidth(currentPage) * scale) - 1 + offsetX,
						                (int) (pageData.getCropBoxHeight(currentPage) * scale) - 1 + offsetY);
				signAreaUndefined = false;
			}
			int xO = (int) signRectOrigin.getX();
			int yO = (int) signRectOrigin.getY();
			int xE = (int) signRectEnd.getX(); 	
			int yE = (int) signRectEnd.getY();
		    if(xO>xE) {
		    	int temp = xE;
		    	xE = xO;
		    	xO = temp;
		    }
		    if(yO>yE) {
		    	int temp = yO;
		    	yO = yE;
		    	yE = temp;
		    }
			
			g.drawRect(xO, yO, xE - xO, yE - yO);
			g.drawLine(xO, yO, xE, yE);
			g.drawLine(xO, yE, xE, yO);
		}
		
		private Runnable signAreaThread() {
			return new Runnable() {
				public void run() 
				{
					Point origin = sigPreviewComp.getLocationOnScreen();
					
				    while(drawRect) {
				    	try {
				    	    Thread.sleep(100);
				    	}
				    	catch (Exception e) {
                            //tell user and log
                            logger.info("Exception: "+e.getMessage());
				    	}				    	
				    	double x = MouseInfo.getPointerInfo().getLocation().getX() - origin.getX();
				    	double y = MouseInfo.getPointerInfo().getLocation().getY() - origin.getY();
				    					    	
				    	signRectEnd.setLocation(x, y);					    
				    	sigPreviewComp.repaint();
				    }
				}
			};
		}
	}
		
    private static class ProgressPanel extends JPanel
	{
		public ProgressPanel(int current)
		{
			setBorder(new EtchedBorder());
			JLabel progressLabel = new JLabel("Step " + current + " of " + MAXIMUM_PANELS);
			progressLabel.setAlignmentX(RIGHT_ALIGNMENT);
		    add(progressLabel);
	    }
	}
	
	private static class TitlePanel extends JPanel
	{	
		public TitlePanel(String title)
		{
	        setBackground(Color.gray);
	        setBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED));
	        
	        JLabel textLabel = new JLabel();
	        textLabel.setBackground(Color.gray);
	        textLabel.setFont(new Font("Dialog", Font.BOLD, 14));
	        textLabel.setText(title);
	        textLabel.setBorder(new EmptyBorder(new Insets(10, 10, 10, 10)));
	        textLabel.setOpaque(true);
	        add(textLabel);
		}
	}
	
}

