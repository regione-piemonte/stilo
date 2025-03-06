package it.eng.areas.hybrid.module.siss.ui;

import it.eng.areas.hybrid.module.siss.messages.MessageKeys;
import it.eng.areas.hybrid.module.siss.messages.Messages;
import it.eng.areas.hybrid.module.siss.preferences.PreferenceKeys;
import it.eng.areas.hybrid.module.siss.preferences.PreferenceManager;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import org.apache.log4j.Logger;

public class PreferenceFirmaMarcaPanel extends GenericPanel {
	
	public final static Logger logger = Logger.getLogger(PreferenceFirmaMarcaPanel.class);
	
	private static final long serialVersionUID = 1L;

	private int widthPanel = 700;
	private int heightPanel = 600;
	
	private JFrame windowDialog;
	
//	private JLabel appendModeLabel;
//	private JLabel acro6LayerLabel;
	private JLabel renderModeLabel;
	private JLabel visibleSignatureLabel;
	private JLabel signPageLabel;
//	private JLabel certifyModeLabel;
	private JLabel l2FontSizeLabel;
	private JLabel l2TextLayerLabel;
//	private JLabel l4TextLayerLabel;
	private JLabel imgPathLabel;
	private JLabel bgImgPathLabel;
	private JLabel bgImgScaleLabel;
//	private JLabel hashAlgorithmLabel;
//	private JLabel outputFileNameLabel;
	private JLabel tsaUrlLabel;
	private JLabel tsaAuthLabel;
	private JLabel tsaUserLabel;
	private JLabel tsaPasswordLabel;
	private JLabel caCheckCertificatoLabel;
	private JLabel crlCheckCertificatoLabel;
	private JLabel caCheckFirmeLabel;
	private JLabel crlCheckFirmeLabel;
	private JLabel codeCheckLabel;
	private JLabel labelTipoFirma;
	private JLabel labelModalitaFirma;
	private JLabel labelMarcaSolo;
    
//	private JCheckBox appendMode;
//	private JCheckBox acro6Layer;
//	private JComboBox hashAlgorithm;
	private JComboBox renderMode;
//	private JComboBox certifyMode;
	private JTextField signPage;
	private JTextField l2FontSizeLayer;
	private JTextField l2TextLayer;
//	private JTextField l4TextLayer;
	private JTextField imgPath;
	private JTextField bgImgPath;
	private JTextField bgImgScale;
	private JCheckBox visibleSignature;
	private JComboBox tipoFirma;
	private JComboBox modalitaFirma;
	private JCheckBox marcaSoloNonMarcate;
//	private JTextField outputFileName;
	private JComboBox tsaUrl;
	private JCheckBox tsaAuth;
	private JTextField tsaUser;
	private JTextField tsaPassword;
	private JButton browseImgButton;
	private JButton browseBgImgButton;
	private JCheckBox caCheckCertificato;
	private JCheckBox crlCheckCertificato;
	private JCheckBox caCheckFirme;
	private JCheckBox crlCheckFirme;
	private JCheckBox codeCheck;
    
	private JButton save; 
	private JButton cancel; 
	
	private PanelSign panelSign;
	
	public PreferenceFirmaMarcaPanel() {
		super();
	}
	
	public PreferenceFirmaMarcaPanel(int widthPanel, int heightPanel) {
		super();
		this.widthPanel = widthPanel;
		this.heightPanel = heightPanel;
	}
	
	public void show(/*Container container,*/ PanelSign panelSign){

		this.panelSign = panelSign;
		
		windowDialog = new JFrame();
		//windowDialog.setModal(true);

        createPreferenceWindow();
		
        save.setEnabled(true);
		
        //windowDialog.setLocationRelativeTo( container );
        windowDialog.setVisible(true);
        windowDialog.setAlwaysOnTop(true);
	}

	private void createPreferenceWindow(){
		
		windowDialog.getContentPane().setLayout( new BorderLayout() );
		windowDialog.getContentPane().add( this,BorderLayout.CENTER );
		windowDialog.pack();
		windowDialog.setSize( widthPanel, heightPanel );

        windowDialog.setTitle( Messages.getMessage( MessageKeys.PANEL_PREFERENCE_FIRMAMARCA_TITLE ) );
		
        GridBagConstraints c1 = new GridBagConstraints();
        setLayout(new GridBagLayout());

        tabs = new JTabbedPane();
        
        JPanel pannelloFirma = addPannelloOpzioniFirma( );
        aggiungiPannelloTabs( creaPannelloTab(pannelloFirma), Messages.getMessage( MessageKeys.PANEL_PREFERENCE_FIRMAMARCA_TABS_FIRMA_TITLE ) );
		
        //JPanel pannelloFirmaPdf = addPannelloOpzioniFirmaPdf( );
//        if( !panelSign.getCard().getHashfilebean().isEmpty() ){
//        	pannelloFirmaPdf.setVisible(false);
//        } else {
//        	pannelloFirmaPdf.setVisible(true);
//        	aggiungiPannelloTabs( creaPannelloTab(pannelloFirmaPdf), Messages.getMessage( MessageKeys.PANEL_PREFERENCE_FIRMAMARCA_TABS_FIRMAPDF_TITLE ) );
//        }
        
        JPanel pannelloMarcatura = addPannelloOpzioniMarcatura(  );
        aggiungiPannelloTabs( creaPannelloTab(pannelloMarcatura), Messages.getMessage( MessageKeys.PANEL_PREFERENCE_FIRMAMARCA_TABS_MARCA_TITLE ) );
		
        c1.gridx = 0;
		c1.gridy = 0;
		c1.insets = new Insets(10,10,10,10);
		c1.fill = GridBagConstraints.HORIZONTAL;
		c1.weightx = c1.weighty = 1.0;
		add( tabs, c1 );

		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new GridBagLayout());
		GridBagConstraints cButton = new GridBagConstraints();
		
		buttonPanel.add(Box.createHorizontalGlue());
		
		save = aggiungiBottone( buttonPanel, save, "save", Messages.getMessage( MessageKeys.PANEL_BUTTON_SAVE ), Messages.getMessage( MessageKeys.PANEL_BUTTON_SAVE_TOOLTIP ), true, cButton, 0, 0, 1, 5, 5);
		save.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				salvaConfigurazione(  );
				if( tipoFirma.getSelectedItem()!=null )
					panelSign.getTipoFirma().setSelectedItem( tipoFirma.getSelectedItem() );
				if( modalitaFirma.getSelectedItem()!=null )
					panelSign.getModalitaFirma().setSelectedItem( modalitaFirma.getSelectedItem() );
				panelSign.getMarcaSoloNonMarcate().setSelected( marcaSoloNonMarcate.isSelected() );
				windowDialog.setVisible(false);
			}
		});
		
		getRootPane().setDefaultButton(save);
		
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

	private void salvaConfigurazione(){
		try {
			if( renderMode.getSelectedItem()!=null )
				PreferenceManager.saveProp( PreferenceKeys.PROPERTY_SIGN_PDF_RENDER_MODE, renderMode.getSelectedItem() );
			if( l2TextLayer.getText()!=null ){
				String testo = l2TextLayer.getText();
				String regExp = ".*[0-9]{2}/[0-9]{2}/[0-9]{4}.*";
				if( testo.matches( regExp ) ){
					String regExp1="[0-9]{2}/[0-9]{2}/[0-9]{4}";
					testo = testo.replaceAll(regExp1, "\\$dataCorrente");
				}
				PreferenceManager.saveProp( PreferenceKeys.PROPERTY_SIGN_PDF_L2TEXT, testo );
			}
			if( l2FontSizeLayer.getText()!=null )
				PreferenceManager.saveProp( PreferenceKeys.PROPERTY_SIGN_PDF_L2TEXTSIZE, l2FontSizeLayer.getText() );
			if( imgPath.getText()!=null )
				PreferenceManager.saveProp( PreferenceKeys.PROPERTY_SIGN_PDF_IMAGE_SIGN, imgPath.getText() );
			if( bgImgPath.getText()!=null )
				PreferenceManager.saveProp( PreferenceKeys.PROPERTY_SIGN_PDF_IMAGE_BG, bgImgPath.getText() );
			if( bgImgScale.getText()!=null )
				PreferenceManager.saveProp( PreferenceKeys.PROPERTY_SIGN_PDF_IMAGE_BG_SCALE, bgImgScale.getText() );
			PreferenceManager.saveProp( PreferenceKeys.PROPERTY_SIGN_PDF_VISIBLE_SIGNATURE, visibleSignature.isSelected() );
			if( signPage.getText()!=null )
				PreferenceManager.saveProp( PreferenceKeys.PROPERTY_SIGN_PDF_SIGNPAGE, signPage.getText() );
			
			if( modalitaFirma.getSelectedItem()!=null )
				PreferenceManager.saveProp( PreferenceKeys.PROPERTY_SIGN_ENVELOPE_MERGE, modalitaFirma.getSelectedItem() );
			if( tipoFirma.getSelectedItem()!=null )
				PreferenceManager.saveProp( PreferenceKeys.PROPERTY_SIGN_ENVELOPE_TYPE, tipoFirma.getSelectedItem() );
			PreferenceManager.saveProp( PreferenceKeys.PROPERTY_SIGN_MARCATURA_SOLO_NON_MARCATE, marcaSoloNonMarcate.isSelected() );
			PreferenceManager.saveProp( PreferenceKeys.PROPERTY_SIGN_CACHECKCERTIFICATEENABLED, caCheckCertificato.isSelected() );
			PreferenceManager.saveProp( PreferenceKeys.PROPERTY_SIGN_CRLCHECKCERTIFICATEENABLED, crlCheckCertificato.isSelected() );
			PreferenceManager.saveProp( PreferenceKeys.PROPERTY_SIGN_CACHECKENABLED, caCheckFirme.isSelected() );
			PreferenceManager.saveProp( PreferenceKeys.PROPERTY_SIGN_CRLCHECKENABLED, crlCheckFirme.isSelected() );
			
			if( tsaUrl.getSelectedItem()!=null )
				PreferenceManager.saveProp( PreferenceKeys.PROPERTY_SIGN_TSASERVER, tsaUrl.getSelectedItem() );
			PreferenceManager.saveProp( PreferenceKeys.PROPERTY_SIGN_TSAAUTH, tsaAuth.isSelected() );
			if( tsaUser.getText()!=null )
				PreferenceManager.saveProp( PreferenceKeys.PROPERTY_SIGN_TSAUSER, tsaUser.getText() );
			if( tsaPassword.getText()!=null )
				PreferenceManager.saveProp( PreferenceKeys.PROPERTY_SIGN_TSAPASSWORD, tsaPassword.getText() );
			
			PreferenceManager.reinitConfig();
			
			JOptionPane.showMessageDialog(this,
					Messages.getMessage( MessageKeys.MSG_FIRMAMARCA_SAVE_SUCCESS ),
					"",
					JOptionPane.INFORMATION_MESSAGE );
		} catch (Exception e) {
			logger.info("Errore ", e);
			JOptionPane.showMessageDialog(this,
					Messages.getMessage( MessageKeys.MSG_FIRMAMARCA_ERROR_GENERIC_SAVE ),
					"",
					JOptionPane.ERROR_MESSAGE );
		}
	}
	
	private JPanel addPannelloOpzioniFirma(){
		int margineCampi = 5;
		JPanel panelFirma = new JPanel(new GridBagLayout());
		GridBagConstraints cFirma = new GridBagConstraints();
		int y=0;
		
		labelTipoFirma = aggiungiLabel( panelFirma, labelTipoFirma, "labelTipoFirma", Messages.getMessage( MessageKeys.PANEL_PREFERENCE_FIRMAMARCA_FIELD_ENVELOPETYPE ),
				cFirma, 0, y, 1, 5, margineCampi, GridBagConstraints.LINE_START );
		
		String[] firmeDisponibili = PreferenceManager.getStringArray( PreferenceKeys.PROPERTY_SIGN_ENVELOPE_TYPE_OPTIONS );
		String tipoFirmaProperty="";
		try {
			tipoFirmaProperty = PreferenceManager.getString( PreferenceKeys.PROPERTY_SIGN_ENVELOPE_TYPE );
		} catch (Exception e2) {
			e2.printStackTrace();
		}
		tipoFirma = new JComboBox(firmeDisponibili);
		aggiungiCombo( panelFirma, tipoFirma, firmeDisponibili, "tipoFirma", tipoFirmaProperty, true, cFirma, 1, y++, 1, 5, margineCampi, GridBagConstraints.LINE_START );
		//if( !panelSign.getCard().getHashfilebean().isEmpty() ){	
			labelTipoFirma.setVisible( false );
			tipoFirma.setVisible( false );
		//}
			
		labelModalitaFirma = aggiungiLabel( panelFirma, labelModalitaFirma, "labelModalitaFirma", Messages.getMessage( MessageKeys.PANEL_PREFERENCE_FIRMAMARCA_FIELD_ENVELOPEMERGE ),
				cFirma, 0, y, 1, 5, margineCampi, GridBagConstraints.LINE_START );
		
		String[] modalitaDisponibili = PreferenceManager.getStringArray( PreferenceKeys.PROPERTY_SIGN_ENVELOPE_MERGE_OPTIONS );
		String modalitaFirmaProperty="";
		try {
			modalitaFirmaProperty = PreferenceManager.getString( PreferenceKeys.PROPERTY_SIGN_ENVELOPE_MERGE );
		} catch (Exception e2) {
			e2.printStackTrace();
		}
		modalitaFirma = new JComboBox(modalitaDisponibili);
		aggiungiCombo( panelFirma, modalitaFirma, modalitaDisponibili, "modalitaFirma", modalitaFirmaProperty, true, cFirma, 1, y++, 1, 5, margineCampi, GridBagConstraints.LINE_START );
		//if( !panelSign.getCard().getHashfilebean().isEmpty() ){	
			labelModalitaFirma.setVisible( false );
			modalitaFirma.setVisible( false );
		//}
		
		labelMarcaSolo = aggiungiLabel( panelFirma, labelMarcaSolo, "labelMarcaSolo", Messages.getMessage( MessageKeys.PANEL_PREFERENCE_FIRMAMARCA_FIELD_MARKONLYNOTTIMESTAMPED ),
				cFirma, 0, y, 1, 5, margineCampi, GridBagConstraints.LINE_START );
		boolean marcaSoloNonMarcateProperty = false;
		try {
			marcaSoloNonMarcateProperty = PreferenceManager.getBoolean( PreferenceKeys.PROPERTY_SIGN_MARCATURA_SOLO_NON_MARCATE );
		} catch (Exception e2) {
			e2.printStackTrace();
		}
		marcaSoloNonMarcate = aggiungiCheckBox( panelFirma, marcaSoloNonMarcate, "marcaSoloNonMarcate", "", marcaSoloNonMarcateProperty, true, SwingConstants.LEFT,
				cFirma, 1, y++, 1, 5, margineCampi, GridBagConstraints.LINE_START );
				
		caCheckCertificatoLabel = aggiungiLabel( panelFirma, caCheckCertificatoLabel, "caCheckCertificatoLabel", Messages.getMessage( MessageKeys.PANEL_PREFERENCE_FIRMAMARCA_FIELD_ENABLECACHECKCERTIFICATO ), 
				cFirma, 0, y, 1, 5, margineCampi, GridBagConstraints.LINE_START );
		boolean enablecaCheckCertificato = false;
		try {
			enablecaCheckCertificato = PreferenceManager.getBoolean( PreferenceKeys.PROPERTY_SIGN_CACHECKCERTIFICATEENABLED );
		} catch (Exception e2) {
			e2.printStackTrace();
		}
		caCheckCertificato = aggiungiCheckBox( panelFirma, caCheckCertificato, "caCheckCertificato", "", enablecaCheckCertificato, true, SwingConstants.LEFT,
				cFirma, 1, y++, 1, 5, margineCampi, GridBagConstraints.LINE_START );
		
		crlCheckCertificatoLabel = aggiungiLabel( panelFirma, crlCheckCertificatoLabel, "crlCheckCertificatoLabel", Messages.getMessage( MessageKeys.PANEL_PREFERENCE_FIRMAMARCA_FIELD_ENABLECRLCHECKCERTIFICATO ), 
				cFirma, 0, y, 1, 5, margineCampi, GridBagConstraints.LINE_START );
		boolean enablecrlCheckCertificato = false;
		try {
			enablecrlCheckCertificato = PreferenceManager.getBoolean( PreferenceKeys.PROPERTY_SIGN_CRLCHECKCERTIFICATEENABLED );
		} catch (Exception e2) {
			e2.printStackTrace();
		}
		crlCheckCertificato = aggiungiCheckBox( panelFirma, crlCheckCertificato, "crlCheckCertificato", "", enablecrlCheckCertificato, true, SwingConstants.LEFT,
				cFirma, 1, y++, 1, 5, margineCampi, GridBagConstraints.LINE_START );
		
		caCheckFirmeLabel = aggiungiLabel( panelFirma, caCheckFirmeLabel, "caCheckFirmeLabel", Messages.getMessage( MessageKeys.PANEL_PREFERENCE_FIRMAMARCA_FIELD_ENABLECACHECKFIRME ), 
				cFirma, 0, y, 1, 5, margineCampi, GridBagConstraints.LINE_START );
		boolean enablecaCheckFirme = false;
		try {
			enablecaCheckFirme = PreferenceManager.getBoolean( PreferenceKeys.PROPERTY_SIGN_CACHECKENABLED );
		} catch (Exception e2) {
		}
		caCheckFirme = aggiungiCheckBox( panelFirma, caCheckFirme, "caCheckFirme", "", enablecaCheckFirme, true, SwingConstants.LEFT,
				cFirma, 1, y++, 1, 5, margineCampi, GridBagConstraints.LINE_START );
		
		crlCheckFirmeLabel = aggiungiLabel( panelFirma, crlCheckFirmeLabel, "crlCheckFirmeLabel", Messages.getMessage( MessageKeys.PANEL_PREFERENCE_FIRMAMARCA_FIELD_ENABLECRLCHECKFIRME ), 
				cFirma, 0, y, 1, 5, margineCampi, GridBagConstraints.LINE_START );
		boolean enablecrlCheckFirme = false;
		try {
			enablecrlCheckFirme = PreferenceManager.getBoolean( PreferenceKeys.PROPERTY_SIGN_CRLCHECKENABLED );
		} catch (Exception e2) {
		}
		crlCheckFirme = aggiungiCheckBox( panelFirma, crlCheckFirme, "crlCheckFirme", "", enablecrlCheckFirme, true, SwingConstants.LEFT,
				cFirma, 1, y++, 1, 5, margineCampi, GridBagConstraints.LINE_START );
		
		codeCheckLabel = aggiungiLabel( panelFirma, codeCheckLabel, "codeCheckLabel", Messages.getMessage( MessageKeys.PANEL_PREFERENCE_FIRMAMARCA_FIELD_ENABLECODECHECK ), 
				cFirma, 0, y, 1, 5, margineCampi, GridBagConstraints.LINE_START );
		boolean enablecodeCheck= false;
		try {
			enablecodeCheck = PreferenceManager.getBoolean( PreferenceKeys.PROPERTY_SIGN_CODECHECKENABLED );
		} catch (Exception e2) {
		}
		codeCheck = aggiungiCheckBox( panelFirma, codeCheck, "codeCheck", "", enablecodeCheck, true, SwingConstants.LEFT,
				cFirma, 1, y++, 1, 5, margineCampi, GridBagConstraints.LINE_START );
		
		
		return panelFirma;
	}
	
//	private JPanel addPannelloOpzioniFirmaPdf(){
//		int margineCampi = 5;
//		
//		JPanel panelFirmaPdf = new JPanel(new GridBagLayout());
//		GridBagConstraints cFirmaPdf = new GridBagConstraints();
//		int y=0;
//		
//		visibleSignatureLabel = aggiungiLabel( panelFirmaPdf, visibleSignatureLabel, "visibleSignatureLabel", Messages.getMessage( MessageKeys.PANEL_PREFERENCE_FIRMAMARCA_FIELD_VISIBLESIGNATURE ), 
//				cFirmaPdf, 0, y, 1, 5, margineCampi, GridBagConstraints.LINE_START);
//		
//		boolean visibleSignatureProperty=false;
//		try {
//			visibleSignatureProperty = PreferenceManager.getBoolean( PreferenceKeys.PROPERTY_SIGN_PDF_VISIBLE_SIGNATURE );
//		} catch (Exception e2) {
//		}
//		visibleSignature = aggiungiCheckBox( panelFirmaPdf, visibleSignature, "visibleSignature", "", visibleSignatureProperty, true, SwingConstants.LEFT, cFirmaPdf, 1, y++, 1, margineCampi, margineCampi, GridBagConstraints.LINE_START );
//				
//		renderModeLabel = aggiungiLabel( panelFirmaPdf, renderModeLabel, "renderModeLabel", Messages.getMessage( MessageKeys.PANEL_PREFERENCE_FIRMAMARCA_FIELD_RENDERMODE ), 
//				cFirmaPdf, 0, y, 1, 5, margineCampi, GridBagConstraints.LINE_START);
//		
//		String renderModeProperty ="";
//		try {
//			renderModeProperty = PreferenceManager.getString( PreferenceKeys.PROPERTY_SIGN_PDF_RENDER_MODE );
//		} catch (Exception e2) {
//		}
//		
//		RenderMode[] renders = RenderMode.values();
//		String[] tipiRenderMode = new String[renders.length];
//		for(int i=0;i<renders.length;i++){
//			tipiRenderMode[i] = renders[i].getRenderKey();
//		}
//		renderMode = new JComboBox(tipiRenderMode);
//		aggiungiCombo( panelFirmaPdf, renderMode, tipiRenderMode, "renderMode", renderModeProperty, true, cFirmaPdf, 1, y++, 1, 5, margineCampi, GridBagConstraints.LINE_START );
//		renderMode.addActionListener( new ActionListener() {
//			@Override
//			public void actionPerformed(ActionEvent e) {
//				if( ((String)renderMode.getSelectedItem()).equalsIgnoreCase( RenderMode.DESCRIPTION_ONLY.getRenderKey() ) || 
//						((String)renderMode.getSelectedItem()).equalsIgnoreCase( RenderMode.SIGNAME_AND_DESCRIPTION.getRenderKey() )	
//						){
//					imgPath.setVisible(false);
//					imgPathLabel.setVisible(false);
//					bgImgPath.setVisible(false);
//					bgImgPathLabel.setVisible(false);
//					bgImgScale.setVisible(false);
//					bgImgScaleLabel.setVisible(false);
//					browseBgImgButton.setVisible(false);
//					browseImgButton.setVisible(false);
//				} else {
//					imgPath.setVisible(true);
//					imgPathLabel.setVisible(true);
//					bgImgPath.setVisible(true);
//					bgImgPathLabel.setVisible(true);
//					bgImgScale.setVisible(true);
//					bgImgScaleLabel.setVisible(true);
//					browseBgImgButton.setVisible(true);
//					browseImgButton.setVisible(true);
//				}
//			}
//		});
//		
//		l2TextLayerLabel = aggiungiLabel( panelFirmaPdf, l2TextLayerLabel, "l2TextLayerLabel", Messages.getMessage( MessageKeys.PANEL_PREFERENCE_FIRMAMARCA_FIELD_L2FONTLAYER ), 
//				cFirmaPdf, 0, y, 1, 5, margineCampi, GridBagConstraints.LINE_START );
//		
//		String l2TextLayerProperty ="";
//		try {
//			l2TextLayerProperty = PreferenceManager.getString( PreferenceKeys.PROPERTY_SIGN_PDF_L2TEXT );
//			if( l2TextLayerProperty!=null && l2TextLayerProperty.contains("$dataCorrente")){
//				Date dataCorrente = new Date();
//				SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
//				String dataCorrenteString = formatter.format( dataCorrente );
//				l2TextLayerProperty = l2TextLayerProperty.replace("$dataCorrente", dataCorrenteString );
//			}
//		} catch (Exception e) {
//			logger.info("Errore ", e );
//		}
//		l2TextLayer = aggiungiFieldTesto( panelFirmaPdf, l2TextLayer, 20, "l2TextLayer", l2TextLayerProperty, true, cFirmaPdf, 1, y++, 1, 5, margineCampi, GridBagConstraints.LINE_START );
//		
//		l2FontSizeLabel = aggiungiLabel( panelFirmaPdf, l2FontSizeLabel, "l2FontSizeLabel", Messages.getMessage( MessageKeys.PANEL_PREFERENCE_FIRMAMARCA_FIELD_L2FONTSIZELAYER ), 
//				cFirmaPdf, 0, y, 1, 5, margineCampi, GridBagConstraints.LINE_START );
//		
//		String l2FontSizeLayerProperty ="";
//		try {
//			l2FontSizeLayerProperty = PreferenceManager.getString( PreferenceKeys.PROPERTY_SIGN_PDF_L2TEXTSIZE );
//		} catch (Exception e2) {
//		}
//		l2FontSizeLayer = aggiungiFieldTesto( panelFirmaPdf, l2FontSizeLayer, 10, "l2FontSizeLayer", l2FontSizeLayerProperty, true, cFirmaPdf, 1, y++, 1, 5, margineCampi, GridBagConstraints.LINE_START );
//		
//		
//		imgPathLabel = aggiungiLabel( panelFirmaPdf, imgPathLabel, "imgPathLabel", Messages.getMessage( MessageKeys.PANEL_PREFERENCE_FIRMAMARCA_FIELD_IMGPATH ), 
//				cFirmaPdf, 0, y, 1, 5, margineCampi, GridBagConstraints.LINE_START );
//		
//		String imgPathProperty ="";
//		try {
//			imgPathProperty = PreferenceManager.getString( PreferenceKeys.PROPERTY_SIGN_PDF_IMAGE_SIGN );
//		} catch (Exception e2) {
//		}
//		imgPath = aggiungiFieldTesto( panelFirmaPdf, imgPath, 20, "imgPath", imgPathProperty, true, cFirmaPdf, 1, y, 1, 5, margineCampi, GridBagConstraints.LINE_START );
//		
//		browseImgButton = aggiungiBottone( panelFirmaPdf, browseImgButton, "browseImgButton", Messages.getMessage( MessageKeys.PANEL_BUTTON_BROWSE), 
//				Messages.getMessage( MessageKeys.PANEL_BUTTON_BROWSE_TOOLTIP ), true, cFirmaPdf, 2, y++, 1, 5, margineCampi );
//		browseImgButton.addActionListener( new ActionListener() {
//			public void actionPerformed( ActionEvent e ){
//				JFileChooser chooser = new JFileChooser("");
////				chooser.addChoosableFileFilter(new FileFiltere(new String[]{"jpg", "jpeg"}, "Jpg (*.jpg)"));
////				chooser.addChoosableFileFilter(new FileFilterer(new String[]{"gif"}, "Gif (*.gif)"));
////				chooser.addChoosableFileFilter(new FileFilterer(new String[]{"png"}, "Png (*.png)"));
//				chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
//				int state = chooser.showSaveDialog(null);
//
//				File file = chooser.getSelectedFile();
//				if (file != null && state == JFileChooser.APPROVE_OPTION) {
//					if(file.exists()) {
//						imgPath.setText(file.getAbsolutePath());
//					}
//					else if(file.isDirectory()) {
//						JOptionPane.showMessageDialog(null,
//								Messages.getMessage("PdfSigner.NoFileSelected"),
//								Messages.getMessage("PdfViewerGeneralError.message"),
//								JOptionPane.ERROR_MESSAGE);			
//					}
//					else {
//						
//					}
//				}
//			}
//		} );
//		
//		bgImgPathLabel = aggiungiLabel( panelFirmaPdf, bgImgPathLabel, "bgImgPathLabel", Messages.getMessage( MessageKeys.PANEL_PREFERENCE_FIRMAMARCA_FIELD_BGIMGPATH ), 
//				cFirmaPdf, 0, y, 1, 5, margineCampi, GridBagConstraints.LINE_START );
//		
//		String bgImgPathProperty ="";
//		try {
//			bgImgPathProperty = PreferenceManager.getString( PreferenceKeys.PROPERTY_SIGN_PDF_IMAGE_BG );
//		} catch (Exception e2) {
//		}
//		bgImgPath = aggiungiFieldTesto( panelFirmaPdf, bgImgPath, 20, "bgImgPath", bgImgPathProperty, true, cFirmaPdf, 1, y, 1, 5, margineCampi, GridBagConstraints.LINE_START );
//		
//		browseBgImgButton = aggiungiBottone( panelFirmaPdf, browseBgImgButton, "browseBgImgButton", Messages.getMessage( MessageKeys.PANEL_BUTTON_BROWSE), 
//				Messages.getMessage( MessageKeys.PANEL_BUTTON_BROWSE_TOOLTIP), true, cFirmaPdf, 2, y++, 1, 5, margineCampi );
//		browseBgImgButton.addActionListener( new ActionListener() {
//			public void actionPerformed( ActionEvent e ){
//				JFileChooser chooser = new JFileChooser("");
////				chooser.addChoosableFileFilter(new FileFilterer(new String[]{"jpg", "jpeg"}, "Jpg (*.jpg)"));
////				chooser.addChoosableFileFilter(new FileFilterer(new String[]{"gif"}, "Gif (*.gif)"));
////				chooser.addChoosableFileFilter(new FileFilterer(new String[]{"png"}, "Png (*.png)"));
//				chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
//				int state = chooser.showSaveDialog(null);
//
//				File file = chooser.getSelectedFile();
//				if (file != null && state == JFileChooser.APPROVE_OPTION) {
//					if(file.exists()) {
//						bgImgPath.setText(file.getAbsolutePath());
//					}
//					else if(file.isDirectory()) {
//						JOptionPane.showMessageDialog(null,
//								Messages.getMessage("PdfSigner.NoFileSelected"),
//								Messages.getMessage("PdfViewerGeneralError.message"),
//								JOptionPane.ERROR_MESSAGE);			
//						
//					}
//					else {
//						
//					}
//				}
//			}
//		} );
//		
//		bgImgScaleLabel = aggiungiLabel( panelFirmaPdf, bgImgScaleLabel, "bgImgScaleLabel", Messages.getMessage( MessageKeys.PANEL_PREFERENCE_FIRMAMARCA_FIELD_BGIMGSCALE ), 
//				cFirmaPdf, 0, y, 1, 5, margineCampi, GridBagConstraints.LINE_START );
//		
//		String bgImgScaleProperty ="";
//		try {
//			bgImgScaleProperty = PreferenceManager.getString( PreferenceKeys.PROPERTY_SIGN_PDF_IMAGE_BG_SCALE );
//		} catch (Exception e2) {
//		}
//		bgImgScale = aggiungiFieldTesto( panelFirmaPdf, bgImgScale, 10, "bgImgScale", bgImgScaleProperty, true, cFirmaPdf, 1, y++, 1, 5, margineCampi, GridBagConstraints.LINE_START );
//		
//		if( renderModeProperty.equalsIgnoreCase( RenderMode.DESCRIPTION_ONLY.getRenderKey() ) || 
//				renderModeProperty.equalsIgnoreCase( RenderMode.SIGNAME_AND_DESCRIPTION.getRenderKey() )	
//				){
//			imgPath.setVisible(false);
//			imgPathLabel.setVisible(false);
//			bgImgPath.setVisible(false);
//			bgImgPathLabel.setVisible(false);
//			bgImgScale.setVisible(false);
//			bgImgScaleLabel.setVisible(false);
//			browseBgImgButton.setVisible(false);
//			browseImgButton.setVisible(false);
//		} else {
//			imgPath.setVisible(true);
//			imgPathLabel.setVisible(true);
//			bgImgPath.setVisible(true);
//			bgImgPathLabel.setVisible(true);
//			bgImgScale.setVisible(true);
//			bgImgScaleLabel.setVisible(true);
//			browseBgImgButton.setVisible(true);
//			browseImgButton.setVisible(true);
//		}
//		
//		signPageLabel = aggiungiLabel( panelFirmaPdf, signPageLabel, "signPageLabel", Messages.getMessage( MessageKeys.PANEL_PREFERENCE_FIRMAMARCA_FIELD_SIGNPAGE ), 
//				cFirmaPdf, 0, y, 1, 5, margineCampi, GridBagConstraints.LINE_START);
//		
//		String signPageProperty ="";
//		try {
//			signPageProperty = PreferenceManager.getString( PreferenceKeys.PROPERTY_SIGN_PDF_SIGNPAGE );
//		} catch (Exception e2) {
//		}
//		signPage = aggiungiFieldTesto( panelFirmaPdf, signPage, 10, "signPage", signPageProperty, true, cFirmaPdf, 1, y++, 1, 5, margineCampi, GridBagConstraints.LINE_START );
//		
//		
//		return panelFirmaPdf;
//	}

	private JPanel addPannelloOpzioniMarcatura(){
		int margineCampi = 5;
		JPanel panelMarcatura = new JPanel(new GridBagLayout());
		GridBagConstraints cMarcatura = new GridBagConstraints();
		int y=0;
	
		tsaUrlLabel = aggiungiLabel( panelMarcatura, tsaUrlLabel, "tsaUrlLabel", Messages.getMessage( MessageKeys.PANEL_PREFERENCE_FIRMAMARCA_FIELD_TSAURL ), 
				cMarcatura, 0, y, 1, 5, margineCampi, GridBagConstraints.LINE_START );
		
		String[] serverTsaDisponibili = PreferenceManager.getStringArray( PreferenceKeys.PROPERTY_SIGN_TSASERVER_OPTIONS );
		String tsaUrlProperty ="";
		try {
			tsaUrlProperty = PreferenceManager.getString( PreferenceKeys.PROPERTY_SIGN_TSASERVER );
		} catch (Exception e2) {
		}
		tsaUrl = new JComboBox( serverTsaDisponibili );
		aggiungiCombo( panelMarcatura, tsaUrl, serverTsaDisponibili, "tsaUrl", tsaUrlProperty, true, cMarcatura, 1, y++, 1, 5, margineCampi, GridBagConstraints.LINE_START );
		
		tsaAuthLabel = aggiungiLabel( panelMarcatura, tsaAuthLabel, "tsaAuthLabel", Messages.getMessage( MessageKeys.PANEL_PREFERENCE_FIRMAMARCA_FIELD_TSAAUTH ), 
				cMarcatura, 0, y, 1, 5, margineCampi, GridBagConstraints.LINE_START);
		
		boolean tsaAuthProperty=false;
		try {
			tsaAuthProperty = PreferenceManager.getBoolean( PreferenceKeys.PROPERTY_SIGN_TSAAUTH );
		} catch (Exception e2) {
		}
		tsaAuth = aggiungiCheckBox( panelMarcatura, tsaAuth, "tsaAuth", "", tsaAuthProperty, true, SwingConstants.LEFT, cMarcatura, 1, y++, 1, 5, margineCampi, GridBagConstraints.LINE_START );
		
		
		tsaUserLabel = aggiungiLabel( panelMarcatura, tsaUserLabel, "tsaUserLabel", Messages.getMessage( MessageKeys.PANEL_PREFERENCE_FIRMAMARCA_FIELD_TSAUSER ), 
				cMarcatura, 0, y, 1, 5, margineCampi, GridBagConstraints.LINE_START);
		
		String tsaUserProperty ="";
		try {
			tsaUserProperty = PreferenceManager.getString( PreferenceKeys.PROPERTY_SIGN_TSAUSER );
		} catch (Exception e2) {
		}
		tsaUser = aggiungiFieldTesto( panelMarcatura, tsaUser, 30, "tsaUser", tsaUserProperty, true, cMarcatura, 1, y++, 1, 5, margineCampi, GridBagConstraints.LINE_START );
		
		tsaPasswordLabel = aggiungiLabel( panelMarcatura, tsaPasswordLabel, "tsaPasswordLabel", Messages.getMessage( MessageKeys.PANEL_PREFERENCE_FIRMAMARCA_FIELD_TSAPASSWORD ), 
				cMarcatura, 0, y, 1, 5, margineCampi, GridBagConstraints.LINE_START );
		
		String tsaPasswordProperty ="";
		try {
			tsaPasswordProperty = PreferenceManager.getString( PreferenceKeys.PROPERTY_SIGN_TSAPASSWORD );
		} catch (Exception e2) {
		}
		tsaPassword = aggiungiFieldTesto( panelMarcatura, tsaPassword, 30, "tsaPassword", tsaPasswordProperty, true, cMarcatura, 1, y++, 1, 5, margineCampi, GridBagConstraints.LINE_START );
		
		return panelMarcatura;
	}
	
	public void dispose() {
		
		this.removeAll();
		
		if(windowDialog!=null)
			windowDialog.removeAll();
		windowDialog=null;
		
//		appendModeLabel=null;
//		acro6LayerLabel=null;
		renderModeLabel=null;
//		certifyModeLabel=null;
		l2FontSizeLabel=null;
		l2TextLayerLabel=null;
//		l4TextLayerLabel=null;
		imgPathLabel=null;
		bgImgPathLabel=null;
		bgImgScaleLabel=null;
//		hashAlgorithmLabel=null;
//		outputFileNameLabel=null;
		tsaUrlLabel=null;
		tsaUserLabel=null;
		tsaPasswordLabel=null;
//		appendMode=null;
//		acro6Layer=null;
//		hashAlgorithm=null;
		renderMode=null;
//		certifyMode=null;
		l2FontSizeLayer=null;
		l2TextLayer=null;
//		l4TextLayer=null;
		imgPath=null;
		bgImgPath=null;
		bgImgScale=null;
//		outputFileName=null;
		tsaUrl=null;
		tsaUser=null;
		tsaPassword=null;
		browseImgButton=null;
		browseBgImgButton=null;

		save=null;
		cancel=null;
	}
	
	public static void main(String[] args) {
		String testo = "firmato in data 27/01/2014 da Anna";
		System.out.println(testo);
		String regExp =  ".*[0-9]{2}/[0-9]{2}/[0-9]{4}.*";
		if( testo.matches( regExp ) ){
			System.out.println("qui");
			String regExp1="[0-9]{2}/[0-9]{2}/[0-9]{4}";
			testo = testo.replaceAll(regExp1, "\\$dataCorrente");
		}
		System.out.println(testo);
		
	}
		
}