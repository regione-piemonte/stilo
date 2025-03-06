package it.eng.hybrid.module.jpedal.ui.popup;

import it.eng.hybrid.module.jpedal.messages.MessageConstants;
import it.eng.hybrid.module.jpedal.messages.Messages;
import it.eng.hybrid.module.jpedal.preferences.ConfigConstants;
import it.eng.hybrid.module.jpedal.preferences.PreferenceManager;
import it.eng.hybrid.module.jpedal.ui.JPedalApplication;
import it.eng.hybrid.module.jpedal.ui.SwingGUI;
import it.eng.hybrid.module.jpedal.util.FileFilterer;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import org.apache.log4j.Logger;

import net.sf.jsignpdf.types.RenderMode;

public class PreferenceFirmaMarcaPanel extends GenericPanel {
	
	public final static Logger logger = Logger.getLogger(PreferenceFirmaMarcaPanel.class);
	
	private static final long serialVersionUID = 1L;

	private int widthPanel = 400;
	private int heightPanel = 400;
	
	private JFrame windowDialog;
	
//	private JLabel appendModeLabel;
//	private JLabel acro6LayerLabel;
	private JLabel renderModeLabel;
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
    
//	private JCheckBox appendMode;
//	private JCheckBox acro6Layer;
//	private JComboBox hashAlgorithm;
	private JComboBox renderMode;
//	private JComboBox certifyMode;
	private JTextField l2FontSizeLayer;
	private JTextField l2TextLayer;
//	private JTextField l4TextLayer;
	private JTextField imgPath;
	private JTextField bgImgPath;
	private JTextField bgImgScale;
//	private JTextField outputFileName;
	private JTextField tsaUrl;
	private JCheckBox tsaAuth;
	private JTextField tsaUser;
	private JTextField tsaPassword;
	private JButton browseImgButton;
	private JButton browseBgImgButton;
	private JCheckBox caCheckCertificato;
	private JCheckBox crlCheckCertificato;
	private JCheckBox caCheckFirme;
	private JCheckBox crlCheckFirme;
    
	private JButton save; 
	private JButton cancel; 
	
	public PreferenceFirmaMarcaPanel() {
		super();
	}
	
	public PreferenceFirmaMarcaPanel(int widthPanel, int heightPanel) {
		super();
		this.widthPanel = widthPanel;
		this.heightPanel = heightPanel;
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
		
		final PreferenceManager preferenceManager = gui.getCommonValues().getPreferenceManager();
		
		windowDialog.getContentPane().setLayout( new BorderLayout() );
		windowDialog.getContentPane().add( this,BorderLayout.CENTER );
		windowDialog.pack();
		windowDialog.setSize( widthPanel, heightPanel );

        windowDialog.setTitle( Messages.getMessage( MessageConstants.PANEL_PREFERENCE_FIRMAMARCA_TITLE ) );
		
        GridBagConstraints c1 = new GridBagConstraints();
        setLayout(new GridBagLayout());

        tabs = new JTabbedPane();
        
        JPanel pannelloFirma = addPannelloOpzioniFirma( preferenceManager );
        aggiungiPannelloTabs( creaPannelloTab(preferenceManager, pannelloFirma), Messages.getMessage( MessageConstants.PANEL_PREFERENCE_FIRMAMARCA_TABS_FIRMA_TITLE ) );
		 
        JPanel pannelloMarcatura = addPannelloOpzioniMarcatura( preferenceManager );
        aggiungiPannelloTabs( creaPannelloTab(preferenceManager, pannelloMarcatura), Messages.getMessage( MessageConstants.PANEL_PREFERENCE_FIRMAMARCA_TABS_MARCA_TITLE ) );
		
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
		
		save = aggiungiBottone( buttonPanel, save, "save", Messages.getMessage( MessageConstants.PANEL_BUTTON_SAVE ), Messages.getMessage( MessageConstants.PANEL_BUTTON_SAVE_TOOLTIP ), true, cButton, 0, 0, 1, 5, 5);
		save.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				salvaConfigurazione( preferenceManager );
				windowDialog.setVisible(false);
			}
		});
		
		getRootPane().setDefaultButton(save);
		
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

	private void salvaConfigurazione(PreferenceManager preferenceManager){
		try {
			if( renderMode.getSelectedItem()!=null )
				PreferenceManager.saveProp( ConfigConstants.FIRMA_PROPERTY_RENDERMODE, renderMode.getSelectedItem() );
			if( l2TextLayer.getText()!=null )
				PreferenceManager.saveProp( ConfigConstants.FIRMA_PROPERTY_L2TEXT, l2TextLayer.getText() );
			if( l2FontSizeLayer.getText()!=null )
				PreferenceManager.saveProp( ConfigConstants.FIRMA_PROPERTY_L2TEXTFONTSIZE, l2FontSizeLayer.getText() );
//			if( l4TextLayer.getText()!=null )
//				PreferenceManager.saveProp( ConfigConstants.FIRMA_PROPERTY_L4TEXT, l4TextLayer.getText() );
//			if( certifyMode.getSelectedItem()!=null )
//				PreferenceManager.saveProp( ConfigConstants.FIRMA_PROPERTY_CERTIFYMODE, certifyMode.getSelectedItem() );
//			if( hashAlgorithm.getSelectedItem()!=null )
//				PreferenceManager.saveProp( ConfigConstants.FIRMA_PROPERTY_HASHALGORITHM, hashAlgorithm.getSelectedItem() );
//			PreferenceManager.saveProp( ConfigConstants.FIRMA_PROPERTY_APPENDMODE, appendMode.isSelected() );
//			PreferenceManager.saveProp( ConfigConstants.FIRMA_PROPERTY_ACRO6LAYER, acro6Layer.isSelected() );
			if( imgPath.getText()!=null )
				PreferenceManager.saveProp( ConfigConstants.FIRMA_PROPERTY_IMGPATH, imgPath.getText() );
			if( bgImgPath.getText()!=null )
				PreferenceManager.saveProp( ConfigConstants.FIRMA_PROPERTY_BGIMGPATH, bgImgPath.getText() );
			if( bgImgScale.getText()!=null )
				PreferenceManager.saveProp( ConfigConstants.FIRMA_PROPERTY_BGIMGSCALE, bgImgScale.getText() );
			PreferenceManager.saveProp( ConfigConstants.FIRMA_PROPERTY_ENABLECACHECKCERTIFICATO, caCheckCertificato.isSelected() );
			PreferenceManager.saveProp( ConfigConstants.FIRMA_PROPERTY_ENABLECRLCHECKCERTIFICATO, crlCheckCertificato.isSelected() );
			PreferenceManager.saveProp( ConfigConstants.FIRMA_PROPERTY_ENABLECACHECK, caCheckFirme.isSelected() );
			PreferenceManager.saveProp( ConfigConstants.FIRMA_PROPERTY_ENABLECRLCHECK, crlCheckFirme.isSelected() );

			
			if( tsaUrl.getText()!=null )
				PreferenceManager.saveProp( ConfigConstants.FIRMA_PROPERTY_TSAURL, tsaUrl.getText() );
			PreferenceManager.saveProp( ConfigConstants.FIRMA_PROPERTY_TSAAUTH, tsaAuth.isSelected() );
			if( tsaUser.getText()!=null )
				PreferenceManager.saveProp( ConfigConstants.FIRMA_PROPERTY_TSAUSER, tsaUser.getText() );
			if( tsaPassword.getText()!=null )
				PreferenceManager.saveProp( ConfigConstants.FIRMA_PROPERTY_TSAPASSWORD, tsaPassword.getText() );
			
			preferenceManager.reinitConfig();
			
			JOptionPane.showMessageDialog(this,
					Messages.getMessage( MessageConstants.MSG_FIRMAMARCA_SAVE_SUCCESS ),
					"",
					JOptionPane.INFORMATION_MESSAGE );
		} catch (Exception e) {
			logger.info("Errore ", e);
			JOptionPane.showMessageDialog(this,
					Messages.getMessage( MessageConstants.MSG_FIRMAMARCA_ERROR_GENERIC_SAVE ),
					"",
					JOptionPane.ERROR_MESSAGE );
		}
	}
	
	private JPanel addPannelloOpzioniFirma(PreferenceManager preferenceManager){
		int margineCampi = 5;
		JPanel panelFirma = new JPanel(new GridBagLayout());
		GridBagConstraints cFirma = new GridBagConstraints();
		int y=0;
		
//		appendModeLabel = aggiungiLabel( panelFirma, appendModeLabel, "appendModeLabel", Messages.getMessage( MessageConstants.PANEL_PREFERENCE_FIRMAMARCA_FIELD_APPEND), cFirma, 0, y, 1, margineCampi);
		
//		boolean appendModeProperty=false;
//		try {
//			appendModeProperty = preferenceManager.getConfiguration().getBoolean( ConfigConstants.FIRMA_PROPERTY_APPENDMODE );
//		} catch (Exception e2) {
//		}
//		appendMode = aggiungiCheckBox( panelFirma, appendMode, "appendMode", "", appendModeProperty, true, SwingConstants.LEFT, cFirma, 1, y++, 1, margineCampi );
		
//		acro6LayerLabel = aggiungiLabel( panelFirma, acro6LayerLabel, "acro6LayerLabel", Messages.getMessage( MessageConstants.PANEL_PREFERENCE_FIRMAMARCA_FIELD_ACRO6LAYER ), cFirma, 0, y, 1, margineCampi);
//		
//		boolean acro6LayerProperty=false;
//		try {
//			acro6LayerProperty = preferenceManager.getConfiguration().getBoolean( ConfigConstants.FIRMA_PROPERTY_ACRO6LAYER );
//		} catch (Exception e2) {
//		}
//		acro6Layer = aggiungiCheckBox( panelFirma, acro6Layer, "acro6Layer", "", acro6LayerProperty, true, SwingConstants.LEFT, cFirma, 1, y++, 1, margineCampi);
					
		renderModeLabel = aggiungiLabel( panelFirma, renderModeLabel, "renderModeLabel", Messages.getMessage( MessageConstants.PANEL_PREFERENCE_FIRMAMARCA_FIELD_RENDERMODE ), 
				cFirma, 0, y, 1, 5, margineCampi, GridBagConstraints.LINE_START);
		
		String renderModeProperty ="";
		try {
			renderModeProperty = preferenceManager.getConfiguration().getString( ConfigConstants.FIRMA_PROPERTY_RENDERMODE );
		} catch (Exception e2) {
		}
		
		RenderMode[] renders = RenderMode.values();
		String[] tipiRenderMode = new String[renders.length];
		for(int i=0;i<renders.length;i++){
			tipiRenderMode[i] = renders[i].getRenderKey();
		}
		renderMode = new JComboBox(tipiRenderMode);
		aggiungiCombo( panelFirma, renderMode, tipiRenderMode, "renderMode", renderModeProperty, true, cFirma, 1, y++, 1, 5, margineCampi, GridBagConstraints.LINE_START );
		renderMode.addActionListener( new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if( ((String)renderMode.getSelectedItem()).equalsIgnoreCase( RenderMode.DESCRIPTION_ONLY.getRenderKey() ) || 
						((String)renderMode.getSelectedItem()).equalsIgnoreCase( RenderMode.SIGNAME_AND_DESCRIPTION.getRenderKey() )	
						){
					imgPath.setVisible(false);
					imgPathLabel.setVisible(false);
					bgImgPath.setVisible(false);
					bgImgPathLabel.setVisible(false);
					bgImgScale.setVisible(false);
					bgImgScaleLabel.setVisible(false);
					browseBgImgButton.setVisible(false);
					browseImgButton.setVisible(false);
				} else {
					imgPath.setVisible(true);
					imgPathLabel.setVisible(true);
					bgImgPath.setVisible(true);
					bgImgPathLabel.setVisible(true);
					bgImgScale.setVisible(true);
					bgImgScaleLabel.setVisible(true);
					browseBgImgButton.setVisible(true);
					browseImgButton.setVisible(true);
				}
			}
		});
		
		l2TextLayerLabel = aggiungiLabel( panelFirma, l2TextLayerLabel, "l2TextLayerLabel", Messages.getMessage( MessageConstants.PANEL_PREFERENCE_FIRMAMARCA_FIELD_L2FONTLAYER ), 
				cFirma, 0, y, 1, 5, margineCampi, GridBagConstraints.LINE_START );
		
		String l2TextLayerProperty ="";
		try {
			l2TextLayerProperty = preferenceManager.getConfiguration().getString( ConfigConstants.FIRMA_PROPERTY_L2TEXT );
			if( l2TextLayerProperty!=null && l2TextLayerProperty.contains("$dataCorrente")){
				Date dataCorrente = new Date();
				SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
				String dataCorrenteString = formatter.format( dataCorrente );
				l2TextLayerProperty = l2TextLayerProperty.replace("$dataCorrente", dataCorrenteString );
			}
		} catch (Exception e) {
			logger.info("Errore ", e );
		}
		l2TextLayer = aggiungiFieldTesto( panelFirma, l2TextLayer, 30, "l2TextLayer", l2TextLayerProperty, true, cFirma, 1, y++, 1, 5, margineCampi, GridBagConstraints.LINE_START );
		
		l2FontSizeLabel = aggiungiLabel( panelFirma, l2FontSizeLabel, "l2FontSizeLabel", Messages.getMessage( MessageConstants.PANEL_PREFERENCE_FIRMAMARCA_FIELD_L2FONTSIZELAYER ), 
				cFirma, 0, y, 1, 5, margineCampi, GridBagConstraints.LINE_START );
		
		String l2FontSizeLayerProperty ="";
		try {
			l2FontSizeLayerProperty = preferenceManager.getConfiguration().getString( ConfigConstants.FIRMA_PROPERTY_L2TEXTFONTSIZE );
		} catch (Exception e2) {
		}
		l2FontSizeLayer = aggiungiFieldTesto( panelFirma, l2FontSizeLayer, 30, "l2FontSizeLayer", l2FontSizeLayerProperty, true, cFirma, 1, y++, 1, 5, margineCampi, GridBagConstraints.LINE_START );
		
		
//		l4TextLayerLabel = aggiungiLabel( panelFirma, l4TextLayerLabel, "l4TextLayerLabel", Messages.getMessage( MessageConstants.PANEL_PREFERENCE_FIRMAMARCA_FIELD_L4FONTLAYER ), cFirma, 0, y, 1, margineCampi);
//		
//		String l4TextLayerProperty ="";
//		try {
//			l4TextLayerProperty = preferenceManager.getConfiguration().getString( ConfigConstants.FIRMA_PROPERTY_L4TEXT );
//		} catch (Exception e2) {
//		}
//		l4TextLayer = aggiungiFieldTesto( panelFirma, l4TextLayer, 30, "l4TextLayer", l4TextLayerProperty, true, cFirma, 1, y++, 1, margineCampi);
		
//		hashAlgorithmLabel = aggiungiLabel( panelFirma, hashAlgorithmLabel, "hashAlgorithmLabel", Messages.getMessage( MessageConstants.PANEL_PREFERENCE_FIRMAMARCA_FIELD_HASHALGORITHM ), cFirma, 0, y, 1, margineCampi);
//		
//		String hashAlgorithmProperty ="";
//		try {
//			hashAlgorithmProperty = preferenceManager.getConfiguration().getString( ConfigConstants.FIRMA_PROPERTY_HASHALGORITHM );
//		} catch (Exception e2) {
//		}
//		HashAlgorithm[] hashAlgorithms = HashAlgorithm.values();
//		String[] tipiHashAlgorithms = new String[hashAlgorithms.length];
//		for(int i=0;i<hashAlgorithms.length;i++){
//			tipiHashAlgorithms[i] = hashAlgorithms[i].getAlgorithmName();
//		}
//		hashAlgorithm = new JComboBox(tipiHashAlgorithms);
//		aggiungiCombo( panelFirma, hashAlgorithm, tipiHashAlgorithms, "hashAlgorithm", hashAlgorithmProperty, true, cFirma, 1, y++, 1, margineCampi );
		
//		certifyModeLabel = aggiungiLabel( panelFirma, certifyModeLabel, "certifyModeLabel", Messages.getMessage( MessageConstants.PANEL_PREFERENCE_FIRMAMARCA_FIELD_CERTIFYMODE ), cFirma, 0, y, 1, margineCampi);
//		
//		String certifyModeProperty ="";
//		try {
//			certifyModeProperty = preferenceManager.getConfiguration().getString( ConfigConstants.FIRMA_PROPERTY_CERTIFYMODE );
//		} catch (Exception e2) {
//		}
//		CertificationLevel[] certificationLevels = CertificationLevel.values();
//		String[] tipiCertificationLevel = new String[certificationLevels.length];
//		for(int i=0;i<certificationLevels.length;i++){
//			tipiCertificationLevel[i] = certificationLevels[i].getKey();
//		}
//		certifyMode = new JComboBox(tipiCertificationLevel);
//		aggiungiCombo( panelFirma, certifyMode, tipiCertificationLevel, "certifyMode", certifyModeProperty, true, cFirma, 1, y++, 1, margineCampi );
		
		imgPathLabel = aggiungiLabel( panelFirma, imgPathLabel, "imgPathLabel", Messages.getMessage( MessageConstants.PANEL_PREFERENCE_FIRMAMARCA_FIELD_IMGPATH ), 
				cFirma, 0, y, 1, 5, margineCampi, GridBagConstraints.LINE_START );
		
		String imgPathProperty ="";
		try {
			imgPathProperty = preferenceManager.getConfiguration().getString( ConfigConstants.FIRMA_PROPERTY_IMGPATH );
		} catch (Exception e2) {
		}
		imgPath = aggiungiFieldTesto( panelFirma, imgPath, 30, "imgPath", imgPathProperty, true, cFirma, 1, y, 1, 5, margineCampi, GridBagConstraints.LINE_START );
		
		browseImgButton = aggiungiBottone( panelFirma, browseImgButton, "browseImgButton", Messages.getMessage( MessageConstants.PANEL_BUTTON_BROWSE), Messages.getMessage( MessageConstants.PANEL_BUTTON_BROWSE_TOOLTIP ), true, cFirma, 2, y++, 1, 5, margineCampi );
		browseImgButton.addActionListener( new ActionListener() {
			public void actionPerformed( ActionEvent e ){
				JFileChooser chooser = new JFileChooser("");
				chooser.addChoosableFileFilter(new FileFilterer(new String[]{"jpg", "jpeg"}, "Jpg (*.jpg)"));
				chooser.addChoosableFileFilter(new FileFilterer(new String[]{"gif"}, "Gif (*.gif)"));
				chooser.addChoosableFileFilter(new FileFilterer(new String[]{"png"}, "Png (*.png)"));
				chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
				int state = chooser.showSaveDialog(null);

				File file = chooser.getSelectedFile();
				if (file != null && state == JFileChooser.APPROVE_OPTION) {
					if(file.exists()) {
						imgPath.setText(file.getAbsolutePath());
					}
					else if(file.isDirectory()) {
						JOptionPane.showMessageDialog(null,
								Messages.getMessage("PdfSigner.NoFileSelected"),
								Messages.getMessage("PdfViewerGeneralError.message"),
								JOptionPane.ERROR_MESSAGE);			
						
					}
					else {
						
					}
				}
			}
		} );
		
		bgImgPathLabel = aggiungiLabel( panelFirma, bgImgPathLabel, "bgImgPathLabel", Messages.getMessage( MessageConstants.PANEL_PREFERENCE_FIRMAMARCA_FIELD_BGIMGPATH ), 
				cFirma, 0, y, 1, 5, margineCampi, GridBagConstraints.LINE_START );
		
		String bgImgPathProperty ="";
		try {
			bgImgPathProperty = preferenceManager.getConfiguration().getString( ConfigConstants.FIRMA_PROPERTY_BGIMGPATH );
		} catch (Exception e2) {
		}
		bgImgPath = aggiungiFieldTesto( panelFirma, bgImgPath, 30, "bgImgPath", bgImgPathProperty, true, cFirma, 1, y, 1, 5, margineCampi, GridBagConstraints.LINE_START );
		
		browseBgImgButton = aggiungiBottone( panelFirma, browseBgImgButton, "browseBgImgButton", Messages.getMessage( MessageConstants.PANEL_BUTTON_BROWSE), Messages.getMessage( MessageConstants.PANEL_BUTTON_BROWSE_TOOLTIP), true, cFirma, 2, y++, 1, 5, margineCampi );
		browseBgImgButton.addActionListener( new ActionListener() {
			public void actionPerformed( ActionEvent e ){
				JFileChooser chooser = new JFileChooser("");
				chooser.addChoosableFileFilter(new FileFilterer(new String[]{"jpg", "jpeg"}, "Jpg (*.jpg)"));
				chooser.addChoosableFileFilter(new FileFilterer(new String[]{"gif"}, "Gif (*.gif)"));
				chooser.addChoosableFileFilter(new FileFilterer(new String[]{"png"}, "Png (*.png)"));
				chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
				int state = chooser.showSaveDialog(null);

				File file = chooser.getSelectedFile();
				if (file != null && state == JFileChooser.APPROVE_OPTION) {
					if(file.exists()) {
						bgImgPath.setText(file.getAbsolutePath());
					}
					else if(file.isDirectory()) {
						JOptionPane.showMessageDialog(null,
								Messages.getMessage("PdfSigner.NoFileSelected"),
								Messages.getMessage("PdfViewerGeneralError.message"),
								JOptionPane.ERROR_MESSAGE);			
						
					}
					else {
						
					}
				}
			}
		} );
		
		bgImgScaleLabel = aggiungiLabel( panelFirma, bgImgScaleLabel, "bgImgScaleLabel", Messages.getMessage( MessageConstants.PANEL_PREFERENCE_FIRMAMARCA_FIELD_BGIMGSCALE ), 
				cFirma, 0, y, 1, 5, margineCampi, GridBagConstraints.LINE_START );
		
		String bgImgScaleProperty ="";
		try {
			bgImgScaleProperty = preferenceManager.getConfiguration().getString( ConfigConstants.FIRMA_PROPERTY_BGIMGSCALE );
		} catch (Exception e2) {
		}
		bgImgScale = aggiungiFieldTesto( panelFirma, bgImgScale, 30, "bgImgScale", bgImgScaleProperty, true, cFirma, 1, y++, 1, 5, margineCampi, GridBagConstraints.LINE_START );
		
		caCheckCertificatoLabel = aggiungiLabel( panelFirma, caCheckCertificatoLabel, "caCheckCertificatoLabel", Messages.getMessage( MessageConstants.PANEL_PREFERENCE_FIRMAMARCA_FIELD_ENABLECACHECKCERTIFICATO ), 
				cFirma, 0, y, 1, 5, margineCampi, GridBagConstraints.LINE_START );
		boolean enablecaCheckCertificato = false;
		try {
			enablecaCheckCertificato = preferenceManager.getConfiguration().getBoolean( ConfigConstants.FIRMA_PROPERTY_ENABLECACHECKCERTIFICATO );
		} catch (Exception e2) {
			e2.printStackTrace();
		}
		caCheckCertificato = aggiungiCheckBox( panelFirma, caCheckCertificato, "caCheckCertificato", "", enablecaCheckCertificato, true, SwingConstants.LEFT,
				cFirma, 1, y++, 1, 5, margineCampi, GridBagConstraints.LINE_START );
		
		crlCheckCertificatoLabel = aggiungiLabel( panelFirma, crlCheckCertificatoLabel, "crlCheckCertificatoLabel", Messages.getMessage( MessageConstants.PANEL_PREFERENCE_FIRMAMARCA_FIELD_ENABLECRLCHECKCERTIFICATO ), 
				cFirma, 0, y, 1, 5, margineCampi, GridBagConstraints.LINE_START );
		boolean enablecrlCheckCertificato = false;
		try {
			enablecrlCheckCertificato = preferenceManager.getConfiguration().getBoolean( ConfigConstants.FIRMA_PROPERTY_ENABLECRLCHECKCERTIFICATO );
		} catch (Exception e2) {
			e2.printStackTrace();
		}
		crlCheckCertificato = aggiungiCheckBox( panelFirma, crlCheckCertificato, "crlCheckCertificato", "", enablecrlCheckCertificato, true, SwingConstants.LEFT,
				cFirma, 1, y++, 1, 5, margineCampi, GridBagConstraints.LINE_START );
		
		caCheckFirmeLabel = aggiungiLabel( panelFirma, caCheckFirmeLabel, "caCheckFirmeLabel", Messages.getMessage( MessageConstants.PANEL_PREFERENCE_FIRMAMARCA_FIELD_ENABLECACHECKFIRME ), 
				cFirma, 0, y, 1, 5, margineCampi, GridBagConstraints.LINE_START );
		boolean enablecaCheckFirme = false;
		try {
			enablecaCheckFirme = preferenceManager.getConfiguration().getBoolean( ConfigConstants.FIRMA_PROPERTY_ENABLECACHECK );
		} catch (Exception e2) {
		}
		caCheckFirme = aggiungiCheckBox( panelFirma, caCheckFirme, "caCheckFirme", "", enablecaCheckFirme, true, SwingConstants.LEFT,
				cFirma, 1, y++, 1, 5, margineCampi, GridBagConstraints.LINE_START );
		
		crlCheckFirmeLabel = aggiungiLabel( panelFirma, crlCheckFirmeLabel, "crlCheckFirmeLabel", Messages.getMessage( MessageConstants.PANEL_PREFERENCE_FIRMAMARCA_FIELD_ENABLECRLCHECKFIRME ), 
				cFirma, 0, y, 1, 5, margineCampi, GridBagConstraints.LINE_START );
		boolean enablecrlCheckFirme = false;
		try {
			enablecrlCheckFirme = preferenceManager.getConfiguration().getBoolean( ConfigConstants.FIRMA_PROPERTY_ENABLECRLCHECK );
		} catch (Exception e2) {
		}
		crlCheckFirme = aggiungiCheckBox( panelFirma, crlCheckFirme, "crlCheckFirme", "", enablecrlCheckFirme, true, SwingConstants.LEFT,
				cFirma, 1, y++, 1, 5, margineCampi, GridBagConstraints.LINE_START );
		
		
		if( renderModeProperty.equalsIgnoreCase( RenderMode.DESCRIPTION_ONLY.getRenderKey() ) || 
				renderModeProperty.equalsIgnoreCase( RenderMode.SIGNAME_AND_DESCRIPTION.getRenderKey() )	
				){
			imgPath.setVisible(false);
			imgPathLabel.setVisible(false);
			bgImgPath.setVisible(false);
			bgImgPathLabel.setVisible(false);
			bgImgScale.setVisible(false);
			bgImgScaleLabel.setVisible(false);
			browseBgImgButton.setVisible(false);
			browseImgButton.setVisible(false);
		} else {
			imgPath.setVisible(true);
			imgPathLabel.setVisible(true);
			bgImgPath.setVisible(true);
			bgImgPathLabel.setVisible(true);
			bgImgScale.setVisible(true);
			bgImgScaleLabel.setVisible(true);
			browseBgImgButton.setVisible(true);
			browseImgButton.setVisible(true);
		}
		
		return panelFirma;
	}

	private JPanel addPannelloOpzioniMarcatura(PreferenceManager preferenceManager){
		int margineCampi = 5;
		JPanel panelMarcatura = new JPanel(new GridBagLayout());
		GridBagConstraints cMarcatura = new GridBagConstraints();
		int y=0;
	
		tsaUrlLabel = aggiungiLabel( panelMarcatura, tsaUrlLabel, "tsaUrlLabel", Messages.getMessage( MessageConstants.PANEL_PREFERENCE_FIRMAMARCA_FIELD_TSAURL ), 
				cMarcatura, 0, y, 1, 5, margineCampi, GridBagConstraints.LINE_START );
		
		String tsaUrlProperty ="";
		try {
			tsaUrlProperty = preferenceManager.getConfiguration().getString( ConfigConstants.FIRMA_PROPERTY_TSAURL );
		} catch (Exception e2) {
		}
		tsaUrl = aggiungiFieldTesto( panelMarcatura, tsaUrl, 30, "tsaUrl", tsaUrlProperty, true, cMarcatura, 1, y++, 1,5,  margineCampi, GridBagConstraints.LINE_START );
		
		tsaAuthLabel = aggiungiLabel( panelMarcatura, tsaAuthLabel, "tsaAuthLabel", Messages.getMessage( MessageConstants.PANEL_PREFERENCE_FIRMAMARCA_FIELD_TSAAUTH ), 
				cMarcatura, 0, y, 1, 5, margineCampi, GridBagConstraints.LINE_START);
		
		boolean tsaAuthProperty=false;
		try {
			tsaAuthProperty = preferenceManager.getConfiguration().getBoolean( ConfigConstants.FIRMA_PROPERTY_TSAAUTH );
		} catch (Exception e2) {
		}
		tsaAuth = aggiungiCheckBox( panelMarcatura, tsaAuth, "tsaAuth", "", tsaAuthProperty, true, SwingConstants.LEFT, cMarcatura, 1, y++, 1, 5, margineCampi, GridBagConstraints.LINE_START );
		
		tsaUserLabel = aggiungiLabel( panelMarcatura, tsaUserLabel, "tsaUserLabel", Messages.getMessage( MessageConstants.PANEL_PREFERENCE_FIRMAMARCA_FIELD_TSAUSER ), 
				cMarcatura, 0, y, 1, 5, margineCampi, GridBagConstraints.LINE_START);
		
		String tsaUserProperty ="";
		try {
			tsaUserProperty = preferenceManager.getConfiguration().getString( ConfigConstants.FIRMA_PROPERTY_TSAUSER );
		} catch (Exception e2) {
		}
		tsaUser = aggiungiFieldTesto( panelMarcatura, tsaUser, 30, "tsaUser", tsaUserProperty, true, cMarcatura, 1, y++, 1, 5, margineCampi, GridBagConstraints.LINE_START );
		
		tsaPasswordLabel = aggiungiLabel( panelMarcatura, tsaPasswordLabel, "tsaPasswordLabel", Messages.getMessage( MessageConstants.PANEL_PREFERENCE_FIRMAMARCA_FIELD_TSAPASSWORD ), 
				cMarcatura, 0, y, 1, 5, margineCampi, GridBagConstraints.LINE_START );
		
		String tsaPasswordProperty ="";
		try {
			tsaPasswordProperty = preferenceManager.getConfiguration().getString( ConfigConstants.FIRMA_PROPERTY_TSAPASSWORD );
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
		
}