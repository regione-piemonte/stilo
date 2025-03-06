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
import it.eng.hybrid.module.jpedal.messages.MessageConstants;
import it.eng.hybrid.module.jpedal.messages.Messages;
import it.eng.hybrid.module.jpedal.objects.PdfPageData;
import it.eng.hybrid.module.jpedal.pdf.PdfDecoder;
import it.eng.hybrid.module.jpedal.pdf.SignData;
import it.eng.hybrid.module.jpedal.preferences.ConfigConstants;
import it.eng.hybrid.module.jpedal.preferences.PreferenceManager;
import it.eng.hybrid.module.jpedal.ui.popup.firma.Factory;
import it.eng.hybrid.module.jpedal.ui.popup.firma.VirtualCard;
import it.eng.hybrid.module.jpedal.util.ItextFunctions;
import it.eng.proxyselector.configuration.ProxyConfiguration;
import it.eng.proxyselector.http.ProxyDefaultHttpClient;

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
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import net.sf.jsignpdf.types.CertificationLevel;
import net.sf.jsignpdf.types.HashAlgorithm;
import net.sf.jsignpdf.types.RenderMode;

import org.apache.log4j.Logger;

/**This class implements the WizardPanelModel and in this
 * case contains the JPanels to be drawn as inner classes.
 * The methods in SignWizardModel are mainly concerned with
 * controlling what panels are next and whether they can be
 * currently reached.
 */
public class SignMarkWizardModel implements WizardPanelModel {	
	
	public final static Logger logger = Logger.getLogger(SignMarkWizardModel.class);
	
	//Each panel must have a unique String identifier 
	private static final String VISIBLE_SIGNATURE_PANEL = "1";
	private static final String OPTION_PANEL = "2";
	
	public static final String NO_FILE_SELECTED = Messages.getMessage("PdfSigner.NoFileSelected");
	
	private static final int MAXIMUM_PANELS = 2;
		
	private SignData signData;
	private PdfDecoder pdfDecoder;
	private String rootDir;
	private String pdfFile;
	private File pdfDaFirmare;
	private PreferenceManager preferenceManager;
	
	/*The JPanels in this wizard */
	private SignaturePanel signaturePanel;
	private OptionPanel optionsPanel;
	
	/*Maps the JPanels' ID to the panel*/
	private Map panels;
	
	/* The ID of the currently displayed panel*/
	private String currentPanel;
	
	private SignMarkWizardModel.OptionPanel.CardPresentThread cardPresentThread;
	
	/**
	 * @param signData Will contain all the information acquired from the user for signing a Pdf 
	 * @param pdfFile The path to the Pdf document to be signed.
	 */
	public SignMarkWizardModel(SignData signData, String pdfFile, String rootDir, PreferenceManager preferenceManager) {
		this.signData = signData;
		this.rootDir = rootDir;
		this.pdfFile = pdfFile;
		this.pdfDaFirmare = new File(pdfFile);
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
		optionsPanel = new OptionPanel();
	    signaturePanel = new SignaturePanel();
	    
	    panels.put(OPTION_PANEL, optionsPanel);
	    panels.put(VISIBLE_SIGNATURE_PANEL, signaturePanel);
	    
	    currentPanel = VISIBLE_SIGNATURE_PANEL;
	}
	
	/**
	 * A map of the JPanels the Wizard Dialog should contain.
	 * 
	 * @return The ID strings mapped to their corresponding JPanels
	 */
	public Map getJPanels(){
		return panels;
	}

	/**
	 * Advance to the next JPanel.
	 * 
	 * @return Unique identifier for the now current JPanel
	 */
	public String next() {
		updateSignData();
		
		if(currentPanel.equals(VISIBLE_SIGNATURE_PANEL)) {
			return currentPanel = OPTION_PANEL;
		}

		/* The following exception should never be thrown and is here to alerted me 
		 * should I create a trail of panels that is incorrect */
		throw new NullPointerException("Whoops! Tried to move to a nextID where there is no nextID to be had");
	}

	/**
	 * Set the current JPanel to the previous JPanel.
	 * 
	 * @return Unique identifier for the now current JPanel
	 */
	public String previous() {
		updateSignData();
		if(currentPanel.equals(OPTION_PANEL)) {
			return currentPanel = VISIBLE_SIGNATURE_PANEL;
		}

		throw new NullPointerException("Tried to move to get a previousID where there is no previous");
	}

	public boolean hasPrevious() {
		return !currentPanel.equals(VISIBLE_SIGNATURE_PANEL);
	}

	public String getStartPanelID() 	{
		return VISIBLE_SIGNATURE_PANEL;
	}
	
	public boolean isFinishPanel() {
		return currentPanel==OPTION_PANEL;
	}
	
	/**
	 * Indicates whether the next or finish button can be enabled.
	 * 
	 * @return true if the current panel can be advanced in its current state
	 */
	public boolean canAdvance() {
		if(currentPanel.equals(OPTION_PANEL)){
			return optionsPanel.canAdvance();
		}
		else {
			return true;
		}
	}
	
	/**
	 * Harvest user data from the currently displayed panel
	 */
	public void updateSignData(){
		if(currentPanel.equals(VISIBLE_SIGNATURE_PANEL)) {
			signaturePanel.collectData();
		}
		else if(currentPanel.equals(OPTION_PANEL)) {
			optionsPanel.collectData();
		}
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
	public void registerNextChangeListeners(ChangeListener wizard){
		optionsPanel.registerChange(wizard);
	}
	
	/**
	 * Same as the previous method but listens for key changes instead.
	 * 
	  @param wizard Listeners to enable/disable advance button 
	 */
	public void registerNextKeyListeners(KeyListener wizard){
		optionsPanel.registerNextKeyListeners(wizard);
	}
	
	/**
	 * To avoid memory leaks I want to close the decoder I opened in this
	 * class when ever the dialog is closed.  Also collects any last
	 * data.
	 */
	public void close() {
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
	
	private class OptionPanel extends GenericPanel {		
		
		private JPasswordField jPin = new JPasswordField();
		private JLabel labelPin = new JLabel();
		private JLabel labelreader = new JLabel();
		private JLabel labelcard = new JLabel();
		private JLabel labelOS = new JLabel();
		private JLabel labelEmulation = new JLabel();
		private JComboBox jcombocardreader = new JComboBox();
		private JProgressBar jProgressBar = new JProgressBar();
		private JLabel outputFileLabel = new JLabel();
		private JLabel currentOutputFilePath = new JLabel();
		private JButton browseOutputButton = new JButton();
		
		private JCheckBox cACheck;
		private JCheckBox cRLCheck;
		private JCheckBox enableOCSPCheck;
		
		private JLabel reasonLabel = new JLabel();
		private JTextField signerReasonArea =new JTextField();

		private JLabel locationLabel = new JLabel();
		private JTextField signerLocationField =new JTextField();
		
		private JLabel contactLabel = new JLabel();
		private JTextField signerContactField =new JTextField();
		
		private volatile boolean pinImpostato, readerPresente = false;
			
		public OptionPanel(){
			try	{
				init();
			} catch( Exception e ) {
				e.printStackTrace();
			}
		}
		
		private void init() {
			int margineDS = 20;
			String wsEndpoint=null;
			try {
				wsEndpoint=preferenceManager.getConfiguration().getString( ConfigConstants.PROPERTY_VERIFYCERT_WSENDPOINT );
			} catch (Exception e1) {
				logger.info("Errore nel recupero della proprietà " + ConfigConstants.PROPERTY_VERIFYCERT_WSENDPOINT );
			}
			
			setLayout(new BorderLayout());
	        add(new TitlePanel(Messages.getMessage( MessageConstants.PANEL_FIRMAMARCA_TITLE ) ), BorderLayout.NORTH);
			
	        pane = new JPanel(new GridBagLayout());
			pane.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));
			GridBagConstraints c = new GridBagConstraints();
			int y=0;
			
			labelEmulation = aggiungiLabel( pane, labelEmulation, "labelEmulation", Messages.getMessage( MessageConstants.PANEL_FIRMAMARCA_FIELD_EMULATION ), c, 0, y++, 4, margineDS, 10, GridBagConstraints.LINE_START );
			
			labelOS = aggiungiLabel( pane, labelOS, "labelOS", Messages.getMessage( MessageConstants.PANEL_FIRMAMARCA_FIELD_SO ), c, 0, y++, 4, margineDS, 5, GridBagConstraints.LINE_START );
			
			JPanel panelPin = new JPanel(new GridBagLayout());
			GridBagConstraints cPin = new GridBagConstraints();
			labelPin = aggiungiLabel( panelPin, labelPin, "labelPin", Messages.getMessage( MessageConstants.PANEL_FIRMAMARCA_FIELD_PIN ), cPin, 0, 0, 1, margineDS, 5, GridBagConstraints.CENTER );
			jPin = aggiungiFieldPassword( panelPin, jPin, 10, "jPin", "", true, cPin, 1, 0, 3, 0, 5, GridBagConstraints.CENTER );
			
			labelcard = aggiungiLabel( panelPin, labelcard, "labelcard", "carta occupata", cPin, 0, 1, 4, margineDS, 5, GridBagConstraints.CENTER );
			labelcard.setVisible(false);
			
			labelreader = aggiungiLabel( panelPin, labelreader, "labelreader", "nessun lettore presente", cPin, 0, 2, 4, margineDS, 5, GridBagConstraints.CENTER );
			
			jcombocardreader = new JComboBox();
			aggiungiCombo( panelPin, jcombocardreader, null, "jcombocardreader", "", true, cPin, 0, 3, 4, margineDS, 5, GridBagConstraints.CENTER );
			jcombocardreader.addItemListener(new java.awt.event.ItemListener() {
				public void itemStateChanged(java.awt.event.ItemEvent e) {
					cardPresentThread.setCardName(jcombocardreader.getSelectedItem().toString());
				} 
			});
			aggiungiPannello( panelPin, c, 0, y++, 5, GridBagConstraints.CENTER );
			
			outputFileLabel = aggiungiLabel( pane, outputFileLabel, "outputFileLabel", Messages.getMessage( MessageConstants.PANEL_FIRMAMARCA_FIELD_OUTPUTFILE ), c, 0, y, 1, margineDS, 5, GridBagConstraints.LINE_START);
			outputFileLabel.setVisible( false );
			
			currentOutputFilePath = aggiungiLabel( pane, currentOutputFilePath, "currentOutputFilePath", "", c, 1, y, 2, 0, 5, GridBagConstraints.LINE_START);
			currentOutputFilePath.setVisible( false );
			
			browseOutputButton = aggiungiBottone( pane, browseOutputButton, "browseOutputButton", Messages.getMessage( MessageConstants.PANEL_BUTTON_BROWSE ), 
					Messages.getMessage( MessageConstants.PANEL_BUTTON_BROWSE_TOOLTIP ), true, c, 3, y++, 1, 0, 5 );
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
							currentOutputFilePath.setText(NO_FILE_SELECTED);
							signData.setOutputFilePath(null);
						}
						else if(file.isDirectory()) {
							JOptionPane.showMessageDialog(null,
									Messages.getMessage("PdfSigner.NoFileSelected"),
									Messages.getMessage("PdfViewerGeneralError.message"),
									JOptionPane.ERROR_MESSAGE);			
							currentOutputFilePath.setText(NO_FILE_SELECTED);
						}
						else {
							currentOutputFilePath.setText(file.getAbsolutePath());
						}
					}
				}
			} ); 
			
			ConfigurationThread thread = new ConfigurationThread(jProgressBar, jPin,this);
			thread.start();
			
			JPanel panelCheck = new JPanel(new GridBagLayout());
			GridBagConstraints cCheck = new GridBagConstraints();
			
			boolean enablecaCheckCertificato = false;
			try {
				enablecaCheckCertificato = preferenceManager.getConfiguration().getBoolean( ConfigConstants.FIRMA_PROPERTY_ENABLECACHECKCERTIFICATO );
			} catch (Exception e2) {
			}
			cACheck = aggiungiCheckBox( panelCheck, cACheck, "cACheck", Messages.getMessage( MessageConstants.PANEL_FIRMAMARCA_FIELD_SKIPCA ), 
					enablecaCheckCertificato, true, SwingConstants.LEFT, cCheck, 0, 0, 2, margineDS, 10, GridBagConstraints.LINE_START);
			if( wsEndpoint!=null)
				cACheck.setVisible(true);
			else
				cACheck.setVisible(false);
			cACheck.setVisible( false );
			
			boolean enablecrlCheckCertificato = false;
			try {
				enablecrlCheckCertificato = preferenceManager.getConfiguration().getBoolean( ConfigConstants.FIRMA_PROPERTY_ENABLECRLCHECKCERTIFICATO );
			} catch (Exception e2) {
			}
			cRLCheck = aggiungiCheckBox( panelCheck, cRLCheck, "cRLCheck", Messages.getMessage( MessageConstants.PANEL_FIRMAMARCA_FIELD_SKIPCRL ), 
					enablecrlCheckCertificato, true, SwingConstants.LEFT, cCheck, 2, 0, 2, margineDS, 10, GridBagConstraints.LINE_START);
			
			if( wsEndpoint!=null)
				cRLCheck.setVisible( true );
			else
				cRLCheck.setVisible( false );
			cRLCheck.setVisible( false );
			
			enableOCSPCheck = aggiungiCheckBox( panelCheck, enableOCSPCheck, "skipCRLCheck", Messages.getMessage( MessageConstants.PANEL_FIRMAMARCA_FIELD_ENABLEOCSP ), 
					false, true, SwingConstants.LEFT, cCheck, 0, 1, 2, margineDS, 10, GridBagConstraints.LINE_START);
			enableOCSPCheck.setVisible( false);
			
			aggiungiPannello( panelCheck, c, 0, y++, 5, GridBagConstraints.CENTER );
			
			aggiungiRigaSeparazione( pane, c, 0, y++, 20, margineDS, 5);
			
			reasonLabel = aggiungiLabel( pane, reasonLabel, "reasonLabel", Messages.getMessage( MessageConstants.PANEL_FIRMAMARCA_FIELD_REASON ), c, 0, y, 1, margineDS, 5, GridBagConstraints.LINE_START);
			signerReasonArea = aggiungiFieldTesto(pane, signerReasonArea, 20, "signerReasonArea", "", true, c, 1, y++, 3, margineDS, 5, GridBagConstraints.LINE_START );
	
			locationLabel = aggiungiLabel( pane, locationLabel, "locationLabel", Messages.getMessage( MessageConstants.PANEL_FIRMAMARCA_FIELD_LOCATION ), c, 0, y, 1, margineDS, 5, GridBagConstraints.LINE_START);
			signerLocationField = aggiungiFieldTesto(pane, signerLocationField, 20, "signerLocationField", "", true, c, 1, y++, 3, margineDS, 5, GridBagConstraints.LINE_START );
			
			contactLabel = aggiungiLabel( pane, contactLabel, "contactLabel", Messages.getMessage( MessageConstants.PANEL_FIRMAMARCA_FIELD_CONTACT ), c, 0, y, 1, margineDS, 5, GridBagConstraints.LINE_START);
			signerContactField = aggiungiFieldTesto(pane, signerContactField, 20, "signerContactField", "", true, c, 1, y++, 3, margineDS, 5, GridBagConstraints.LINE_START );

			add( pane, BorderLayout.CENTER );
			
			add(new ProgressPanel(2), BorderLayout.SOUTH);
		
		}
		
		public void registerChange(ChangeListener e){
			
		}
		
		public void registerNextKeyListeners(KeyListener e){
			jPin.addKeyListener(e);
		}
		
		public boolean canAdvance(){
			if(jPin.getPassword()==null || jPin.getPassword().length==0){
				pinImpostato = false;
			} else 
				pinImpostato = true;
			if( labelreader.isVisible() )
				readerPresente = false;
			else 
				readerPresente = true;
			return pinImpostato && readerPresente;
		}	
		
		public void collectData()	{
			cardPresentThread.stopThread();
			jcombocardreader.setEnabled(false);
			
			int slot = jcombocardreader.getSelectedIndex();
			signData.setSlot( slot );
			logger.info("slot=" + slot );
			signData.setPin( jPin.getPassword() );
			logger.info("pin=" + jPin.getPassword() );

			if( currentOutputFilePath.getText()!=null && !currentOutputFilePath.getText().equalsIgnoreCase("") ){
				signData.setOutputFilePath( currentOutputFilePath.getText() );
			} else {
				String outputFileName ="_firmato";
				try {
					outputFileName = preferenceManager.getConfiguration().getString( ConfigConstants.FIRMA_PROPERTY_OUTPUTFILENAME );
				} catch (Exception e) {
					logger.info("Errore nel recupero della proprietà " + ConfigConstants.FIRMA_PROPERTY_OUTPUTFILENAME );
				}
				
				outputFileName = pdfDaFirmare.getName().replace(".pdf", outputFileName +".pdf");
						
				File outputDirectory = new File( rootDir + "/output/");
				if( !outputDirectory.exists() )
					outputDirectory.mkdir();
				
				String outputFilePath = outputDirectory.getAbsolutePath() + "/" + outputFileName;
				logger.info("OutputFilePath =" + outputFilePath );
				signData.setOutputFilePath( outputFilePath );
			}

			//if( cRLCheck.isVisible() )
				signData.setCheckCrl( cRLCheck.isSelected() );
			//else 
			//	signData.setCheckCrl( false );
			logger.info("SkipCrl=" + signData.isCheckCrl() );
			
			//if( cACheck.isVisible() )
				signData.setCheckCa( cACheck.isSelected() );
			//else 
			//	signData.setCheckCa( false );
			logger.info("SkipCa=" + signData.isCheckCa() );
			
			//signData.setOcspEnabled( enableOCSPCheck.isSelected() );
			signData.setOcspEnabled(false);
			logger.info("OcspEnabed=" + signData.isOcspEnabled() );

			if( signerLocationField.getText()!=null )
				signData.setLocation( signerLocationField.getText() );
			logger.info("Location=" + signData.getLocation() );
			
			if( signerReasonArea.getText()!=null )
				signData.setReason( signerReasonArea.getText() );
			logger.info("Reason=" + signData.getReason() );
			
			if( signerContactField.getText()!=null )
				signData.setContact( signerContactField.getText() );
			logger.info("Contact=" + signData.getContact() );
			
			String algoritmoHash = HashAlgorithm.SHA256.getAlgorithmName();
			try {
				algoritmoHash = preferenceManager.getConfiguration().getString( ConfigConstants.FIRMA_PROPERTY_HASHALGORITHM );
			} catch (Exception e) {
				logger.info("Errore nel recupero della proprietà " + ConfigConstants.FIRMA_PROPERTY_HASHALGORITHM );
			}
			signData.setHashAlgorithm( HashAlgorithm.fromValue( algoritmoHash ) );
			logger.info("Proprietà "+ ConfigConstants.FIRMA_PROPERTY_HASHALGORITHM +"=" + algoritmoHash );
			
			String certifyMode = CertificationLevel.NOT_CERTIFIED.getKey();
			try {
				certifyMode = preferenceManager.getConfiguration().getString( ConfigConstants.FIRMA_PROPERTY_CERTIFYMODE );
			} catch (Exception e) {
				logger.info("Errore nel recupero della proprietà " + ConfigConstants.FIRMA_PROPERTY_CERTIFYMODE );
			}
			signData.setCertifyMode( CertificationLevel.findCertificationCode( certifyMode ));
			logger.info("Proprietà "+ ConfigConstants.FIRMA_PROPERTY_CERTIFYMODE +"=" + certifyMode );
			
			logger.info("Abilito la marcatura");
			signData.setTimestamp( true );
			
			String tsaUrl="";
			try {
				tsaUrl = preferenceManager.getConfiguration().getString( ConfigConstants.FIRMA_PROPERTY_TSAURL );
			} catch (Exception e) {
				logger.info("Errore nel recupero della proprietà " + ConfigConstants.FIRMA_PROPERTY_TSAURL );
			}
			signData.setTsaUrl( tsaUrl );
			logger.info("Proprietà "+ ConfigConstants.FIRMA_PROPERTY_TSAURL +"=" + tsaUrl );
			
			boolean tsaAuth = false;
			try {
				tsaAuth = preferenceManager.getConfiguration().getBoolean( ConfigConstants.FIRMA_PROPERTY_TSAAUTH );
			} catch (Exception e1) {
				logger.info("Errore nel recupero della proprietà " + ConfigConstants.FIRMA_PROPERTY_TSAAUTH );
			}
			signData.setTsaServerAuthn( tsaAuth );
			
			String tsaUser="";
			try {
				tsaUser = preferenceManager.getConfiguration().getString( ConfigConstants.FIRMA_PROPERTY_TSAUSER );
			} catch (Exception e) {
				logger.info("Errore nel recupero della proprietà " + ConfigConstants.FIRMA_PROPERTY_TSAUSER );
			}
			signData.setTsaUser( tsaUser );
			logger.info("Proprietà "+ ConfigConstants.FIRMA_PROPERTY_TSAUSER +"=" + tsaUser );
			
			String tsaPassword="";
			try {
				tsaPassword = preferenceManager.getConfiguration().getString( ConfigConstants.FIRMA_PROPERTY_TSAPASSWORD );
			} catch (Exception e) {
				logger.info("Errore nel recupero della proprietà " + ConfigConstants.FIRMA_PROPERTY_TSAPASSWORD );
			}
			signData.setTsaPasswd(tsaPassword);
			logger.info("Proprietà "+ ConfigConstants.FIRMA_PROPERTY_TSAPASSWORD +"=" + tsaPassword );
			
			ProxyConfiguration lProxyConfiguration = ProxyDefaultHttpClient.getConfiguration();
			
			String proxyHost = lProxyConfiguration.getProxy();
			logger.info("Proprietà proxyHost=" + proxyHost );
			signData.setProxyHost( proxyHost );
			String proxyPort = lProxyConfiguration.getPort()+"";
			logger.info("Proprietà proxyPort=" + proxyPort );
			signData.setProxyPort(proxyPort);
			String proxyUser=lProxyConfiguration.getUsername();
			logger.info("Proprietà proxyUser=" + proxyUser );
			signData.setProxyUser( proxyUser );
			String proxyPassword=lProxyConfiguration.getPassword()!=null?new String(lProxyConfiguration.getPassword()):"";
			logger.info("Proprietà proxyPassword=" + proxyPassword );
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
			private OptionPanel sign;

			public ConfigurationThread(JProgressBar bar,JPasswordField pin,OptionPanel panelsign) {
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
					sign.labelOS.setText( sign.labelOS.getText() + ":" + osname );
					
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

			public CardPresentThread(OptionPanel panelsign,CardTerminals cardterminals){
				this.sign = panelsign;
				this.cardterminals = cardterminals;
				String emulationString;
				try {
					emulationString = preferenceManager.getConfiguration().getString( ConfigConstants.PROPERTY_EMULATION );
					logger.info("emulation " + emulationString );
					this.emulation = Boolean.valueOf( emulationString );
					logger.info("emulation " + emulation );
					if( this.emulation ){
						labelEmulation.setVisible( true );
					} else {
						labelEmulation.setVisible( false );
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			
			private OptionPanel sign;
			private CardTerminals cardterminals = null;	
			private boolean loop = true;
			private String cardname = null;
			private  boolean emulation=false;//emulatore
			
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
	
   private class SignaturePanel extends GenericPanel {
		
    	private JCheckBox visibleCheck; 
		private JCheckBox appendSignCheck; 
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
		
		public SignaturePanel() 	{        
			int margineDS = 20;
			try {
				previewImage = pdfDecoder.getPageAsImage(currentPage);
			}
			catch (Exception e) {
                //tell user and log
                logger.info("Exception: "+e.getMessage());
			}
			
			int y = 0;
			setLayout(new BorderLayout());
			add(new TitlePanel( Messages.getMessage( MessageConstants.PANEL_FIRMAMARCA_TITLE ) ) , BorderLayout.NORTH);

			pane = new JPanel();
			pane.setLayout(new GridBagLayout());

			GridBagConstraints c = new GridBagConstraints();
			
			appendSignCheck = aggiungiCheckBox( pane, appendSignCheck, "appendSignCheck", Messages.getMessage( MessageConstants.PANEL_FIRMAMARCA_FIELD_APPEND ), 
					false, true, SwingConstants.LEFT, c, 0, y++, 2, margineDS, 2, GridBagConstraints.CENTER);
			appendSignCheck.setVisible( false );
			
			visibleCheck = aggiungiCheckBox( pane, visibleCheck, "visibleCheck", Messages.getMessage( MessageConstants.PANEL_FIRMAMARCA_FIELD_VISIBLESIGNATURE ), 
					true, true, SwingConstants.LEFT, c, 0, y++, 2, margineDS, 10, GridBagConstraints.CENTER);
			visibleCheck.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					sigPreviewComp.repaint();
					if (pdfDecoder.getPageCount()>1)
					    pageSlider.setEnabled(visibleCheck.isSelected());
					comboDisplayMode.setEnabled(visibleCheck.isSelected());
				}
				
			});
			
			aggiungiRigaSeparazione( pane, c, 0, y++, 2, margineDS, 5);
			
			c.gridx = 0;
			c.gridy = y++;
			c.gridwidth = 2;
			c.insets = new Insets(0,0,0,0);
			c.fill = GridBagConstraints.HORIZONTAL; 
			pane.add(previewPanel(), c);
			
			aggiungiRigaSeparazione( pane, c, 0, y++, 2, margineDS, 10);
			
			add(pane, BorderLayout.CENTER );
				
			add(new ProgressPanel(1), BorderLayout.SOUTH);
			
		}
		
		public void collectData() {
			boolean isFileFirmato = ItextFunctions.isFileFirmato( pdfFile );
			signData.setFirmato(isFileFirmato);
			if( !isFileFirmato )
				signData.setAppend( false );
			else {
//				Boolean appendMode=true;
//				try {
//					appendMode = Boolean.parseBoolean(preferenceManager.getConfiguration().getString( ConfigConstants.FIRMA_PROPERTY_APPENDMODE ));
//				} catch (Exception e) {
//					logger.info("Errore nel recupero della proprietà " + ConfigConstants.FIRMA_PROPERTY_APPENDMODE );
//				}
//				signData.setAppend(appendMode);
				signData.setAppend(true);
			}
			logger.info("Imposto la modalità append " + signData.isAppendMode() );
			
//			Boolean acro6Layers=false;
//			try {
//				acro6Layers = Boolean.parseBoolean(preferenceManager.getConfiguration().getString( ConfigConstants.FIRMA_PROPERTY_ACRO6LAYER ));
//			} catch (Exception e) {
//				logger.info("Errore nel recupero della proprietà " + ConfigConstants.FIRMA_PROPERTY_ACRO6LAYER );
//			}
//			signData.setAcro6Layers( acro6Layers );
//			logger.info("Imposto la proprietà " + ConfigConstants.FIRMA_PROPERTY_ACRO6LAYER + "=" + signData.isAcro6Layers() );
			
			String renderMode=RenderMode.DESCRIPTION_ONLY.getRenderKey();
			try {
				renderMode = preferenceManager.getConfiguration().getString( ConfigConstants.FIRMA_PROPERTY_RENDERMODE );
			} catch (Exception e) {
				logger.info("Errore nel recupero della proprietà " + ConfigConstants.FIRMA_PROPERTY_RENDERMODE );
			}
			signData.setRenderMode( RenderMode.fromValue( renderMode ) );
			logger.info("Imposto la proprietà " + ConfigConstants.FIRMA_PROPERTY_RENDERMODE + "=" + signData.getRenderMode() );
			
			signData.setVisibleSignature(visibleCheck.isSelected());
			
			if(visibleCheck.isSelected()) {
				
				Float l2TextFontSize=10.0f;
				try {
					l2TextFontSize = Float.parseFloat(preferenceManager.getConfiguration().getString( ConfigConstants.FIRMA_PROPERTY_L2TEXTFONTSIZE ));
				} catch (Exception e) {
					logger.info("Errore nel recupero della proprietà " + ConfigConstants.FIRMA_PROPERTY_L2TEXTFONTSIZE );
				}
				signData.setL2TextFontSize(l2TextFontSize);
				logger.info("Imposto la proprietà " + ConfigConstants.FIRMA_PROPERTY_L2TEXTFONTSIZE + "=" + signData.getL2TextFontSize() );
				
				String l2Text="";
				try {
					l2Text = preferenceManager.getConfiguration().getString( ConfigConstants.FIRMA_PROPERTY_L2TEXT );
					Date dataCorrente = new Date();
					SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
					String dataCorrenteString = formatter.format( dataCorrente );
					l2Text = l2Text.replace("$dataCorrente", dataCorrenteString );
				} catch (Exception e) {
					logger.info("Errore nel recupero della proprietà " + ConfigConstants.FIRMA_PROPERTY_L2TEXT );
				}
				signData.setL2Text(l2Text);
				logger.info("Imposto la proprietà " + ConfigConstants.FIRMA_PROPERTY_L2TEXT + "=" + signData.getL2Text() );
				
				String l4Text="";
				try {
					l4Text = preferenceManager.getConfiguration().getString( ConfigConstants.FIRMA_PROPERTY_L4TEXT );
				} catch (Exception e) {
					logger.info("Errore nel recupero della proprietà " + ConfigConstants.FIRMA_PROPERTY_L4TEXT );
				}
				signData.setL4Text( l4Text );
				logger.info("Imposto la proprietà " + ConfigConstants.FIRMA_PROPERTY_L4TEXT + "=" + signData.getL4Text() );
				
				if( signData.getRenderMode()==RenderMode.GRAPHIC_AND_DESCRIPTION || signData.getRenderMode()==RenderMode.GRAPHIC ){
					String imgPath="";
					try {
						imgPath = preferenceManager.getConfiguration().getString( ConfigConstants.FIRMA_PROPERTY_IMGPATH );
					} catch (Exception e) {
						logger.info("Errore nel recupero della proprietà " + ConfigConstants.FIRMA_PROPERTY_IMGPATH );
					}
					signData.setImgPath(imgPath);
					logger.info("Imposto la proprietà " + ConfigConstants.FIRMA_PROPERTY_IMGPATH + "=" + signData.getImgPath() );
					
					String bgImgPath="";
					try {
						bgImgPath = preferenceManager.getConfiguration().getString( ConfigConstants.FIRMA_PROPERTY_BGIMGPATH );
					} catch (Exception e) {
						logger.info("Errore nel recupero della proprietà " + ConfigConstants.FIRMA_PROPERTY_BGIMGPATH );
					}
					signData.setBgImgPath(bgImgPath);
					logger.info("Imposto la proprietà " + ConfigConstants.FIRMA_PROPERTY_BGIMGPATH + "=" + signData.getBgImgPath() );
					
					Float bgImgScale=100.0f;
					try {
						bgImgScale = Float.parseFloat(preferenceManager.getConfiguration().getString( ConfigConstants.FIRMA_PROPERTY_BGIMGSCALE ));
					} catch (Exception e) {
						logger.info("Errore nel recupero della proprietà " + ConfigConstants.FIRMA_PROPERTY_BGIMGSCALE );
					}
					signData.setBgImgScale(bgImgScale);
					logger.info("Imposto la proprietà " + ConfigConstants.FIRMA_PROPERTY_BGIMGSCALE + "=" + signData.getBgImgScale() );
				}
				
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
				logger.info("Imposto le coordinate di firma a x1=" + x1 + " x2=" + x2 + " y1=" + y1 + " y2=" + y2 );
				signData.setSignPage(currentPage);
			} else {
				signData.setSignPage(1);
			}
		}
	
		private JPanel previewPanel() {
			JPanel result = new JPanel(new BorderLayout());
											
			sigPreviewComp = new JComponent() {			
	            public void paintComponent(Graphics g){
	                sigPreview(g);
	            }
	        };
			sigPreviewComp.setPreferredSize(new Dimension(200,200));
			sigPreviewComp.setToolTipText( MessageConstants.PANEL_FIRMAMARCA_PREVIEW_TOOLTIP );
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
				pageNumberLabel = new JLabel ( Messages.getMessage( MessageConstants.PANEL_FIRMAMARCA_PAGENUMBER ) + ' ' + currentPage);
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
                				pageNumberLabel.setText( Messages.getMessage( MessageConstants.PANEL_FIRMAMARCA_PAGENUMBER ) + ' ' + currentPage);
                			}
                			catch (Exception ex) {
                                //tell user and log
                                logger.info("Exception: "+ex.getMessage());
                			}
                        }
					}
				});
				result.add(pageSlider, BorderLayout.SOUTH);		
				pageSlider.setEnabled(true);
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
			JLabel progressLabel = new JLabel(  Messages.getMessage( MessageConstants.MSG_STEP_TITLE1 ) + current + " " +
					Messages.getMessage( MessageConstants.MSG_STEP_TITLE2 ) + " " + MAXIMUM_PANELS );
			progressLabel.setAlignmentX( RIGHT_ALIGNMENT );
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

