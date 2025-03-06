package it.eng.hybrid.module.jpedal.ui.popup;

import it.eng.fileOperation.clientws.CodificaTimbro;
import it.eng.fileOperation.clientws.DigestAlgID;
import it.eng.fileOperation.clientws.PosizioneRispettoAlTimbro;
import it.eng.fileOperation.clientws.PosizioneTimbroNellaPagina;
import it.eng.fileOperation.clientws.TipoPagina;
import it.eng.fileOperation.clientws.TipoRotazione;
import it.eng.hybrid.module.jpedal.messages.MessageConstants;
import it.eng.hybrid.module.jpedal.messages.Messages;
import it.eng.hybrid.module.jpedal.preferences.ConfigConstants;
import it.eng.hybrid.module.jpedal.preferences.PreferenceManager;
import it.eng.hybrid.module.jpedal.ui.JPedalApplication;
import it.eng.hybrid.module.jpedal.ui.SwingGUI;
import it.eng.hybrid.module.jpedal.util.TimbroUtils;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import org.apache.log4j.Logger;

import com.itextpdf.text.pdf.BaseFont;


public class PreferenceTimbroPanel extends GenericPanel {
	
	public final static Logger logger = Logger.getLogger(PreferenceTimbroPanel.class);
	
	private static final long serialVersionUID = 1L;

	private int widthPanel = 400;
	private int heightPanel = 400;
	
	private JFrame windowDialog;
	
	private JButton save; 
	private JButton cancel; 

	private JLabel testoTimbroLabel;
	private JTextField testoTimbroField;
	private JLabel aggiungiDigestLabel;
	private JCheckBox aggiungiDigestField;
	private JLabel algoritmoDigestLabel;
	private JComboBox listaAlgoritmiDigest;
	private JLabel aggiungiDNLabel;
	private JCheckBox aggiungiDNField;
	private JLabel aggiungiDataLabel;
	private JCheckBox aggiungiDataField;
	private JLabel timbroSingoloLabel;
	private JCheckBox timbroSingoloField;
	private JLabel codificaLabel;
	private JComboBox listaCodifiche;
	private JLabel posizioneTimbroLabel;
	private JComboBox listaPosizioniTimbro;
	private JLabel rotazioneTimbroLabel;
	private JComboBox listaRotazioniTimbro;
	private JLabel testoIntestazioneLabel;
	private JTextField testoIntestazioneField;
	private JLabel posizioneIntestazioneLabel;
	private JComboBox listaPosizioniIntestazione;
	private JLabel posizioneTestoInChiaroLabel;
	private JComboBox listaPosizioniTestoInChiaro;
	private JLabel moreLinesLabel;
	private JCheckBox moreLinesField;
	private JLabel fontNameLabel;
	private JComboBox listaFonts;
	private JLabel fontSizeLabel;
	private JComboBox listaFontSizes;
	private JLabel tipoPaginaLabel;
	private JComboBox listaTipiPagina;
	private JCheckBox timbroPaginaCorrenteField;
	private JLabel paginaDaLabel;
	private JLabel paginaALabel;
    private JTextField paginaDaField;
    private JTextField paginaAField;
	    
	private JLabel labelErrore;
	
	private ActionListener AL2;
	private ActionListener AL3;
	private ActionListener AL4;
	private ActionListener AL5;
	
	private KeyAdapter KA1;
	private KeyAdapter KA2;
	
	private FocusListener FL1;
	private FocusListener FL2;
	
	public PreferenceTimbroPanel() {
		super();
	}
	
	public PreferenceTimbroPanel(int widthPanel, int heightPanel) {
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
		
		final PreferenceManager preferenceManager = gui.getCommonValues().getPreferenceManager();
		
		windowDialog.getContentPane().setLayout( new BorderLayout() );
		windowDialog.getContentPane().add( this, BorderLayout.CENTER );
		windowDialog.pack();
        windowDialog.setSize( widthPanel, heightPanel );
        
        windowDialog.setTitle( Messages.getMessage( MessageConstants.PANEL_PREFERENCE_TIMBRO_TITLE ) );
		
        GridBagConstraints c1 = new GridBagConstraints();
        setLayout(new GridBagLayout());

        tabs = new JTabbedPane();
        
		JPanel pannelloTesto = addPannelloOpzioniTesto( preferenceManager );
		JPanel pannelloTimbroSingolo = addPannelloOpzioniTimbroSingolo( preferenceManager );
		JPanel pannelloCodifica = addPannelloOpzioniCodifica( preferenceManager );
		JPanel pannelloPosizioniTimbro = addPannelloOpzioniPosizioniTimbro( preferenceManager );
		JPanel pannelloIntestazioniTimbro = addPannelloOpzioniIntestazioniTimbro( preferenceManager );
		JPanel pannelloTestoInChiaro = addPannelloOpzioniTestoInChiaro( preferenceManager );
		JPanel pannelloFont = addPannelloOpzioniFont( preferenceManager );
		JPanel pannelloPagine = addPannelloOpzioniPagine( preferenceManager );
				
		aggiungiPannelloTabs( creaPannelloTab(preferenceManager, pannelloTesto, pannelloTimbroSingolo, pannelloIntestazioniTimbro, pannelloTestoInChiaro), Messages.getMessage( MessageConstants.PANEL_PREFERENCE_TIMBRO_TABS_TESTI_TITLE ) );
		
		aggiungiPannelloTabs( creaPannelloTab(preferenceManager, pannelloCodifica, pannelloPosizioniTimbro, pannelloPagine, pannelloFont), Messages.getMessage( MessageConstants.PANEL_PREFERENCE_TIMBRO_TABS_CODIFICA_TITLE ) );
		
		aggiungiPannelloTabs( creaPannelloTab(preferenceManager, pannelloFont), Messages.getMessage( MessageConstants.PANEL_PREFERENCE_TIMBRO_TABS_ALTRE_OPZIONI_TITLE ) );
		
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

	private JPanel addPannelloOpzioniTesto(PreferenceManager preferenceManager){
		int margineCampi = 5;
		JPanel panelTesto = new JPanel(new GridBagLayout());
		GridBagConstraints cTesto = new GridBagConstraints();
		
		testoTimbroLabel = aggiungiLabel(panelTesto, testoTimbroLabel, "labelTesto", Messages.getMessage( MessageConstants.PANEL_PREFERENCE_TIMBRO_FIELD_TESTO), 
				cTesto, 0, 0, 1, 5, margineCampi, GridBagConstraints.LINE_START);
		
		boolean editable = true;
		String testoTimbro = null;
		try {
			testoTimbro = preferenceManager.getConfigurationChiamante().getString( ConfigConstants.TIMBRO_PROPERTY_TESTO );
			editable = false;
		} catch (Exception e2) {
		}
		if(testoTimbro==null) {
			try {
				testoTimbro = preferenceManager.getConfiguration().getString( ConfigConstants.TIMBRO_PROPERTY_TESTO );
			} catch (Exception e2) {
			}
			editable = true;
		}
		testoTimbroField = aggiungiFieldTesto( panelTesto, testoTimbroField, 30, "testoTimbro", testoTimbro, editable, cTesto, 1, 0, 5, 5, margineCampi, GridBagConstraints.LINE_START );
		
		Boolean addDigest=null;
		String addDigestString=null;
		try {
			addDigestString = preferenceManager.getConfigurationChiamante().getString( ConfigConstants.TIMBRO_PROPERTY_ADDDIGEST  );
			editable = false;
		} catch (Exception e2) {
		}
		if( addDigestString==null ) {
			try {
				addDigestString = preferenceManager.getConfiguration().getString( ConfigConstants.TIMBRO_PROPERTY_ADDDIGEST );
				editable = true;
			} catch (Exception e2) {
			}
		}
		addDigest = Boolean.valueOf( addDigestString );
		
		aggiungiDigestLabel = aggiungiLabel( panelTesto, aggiungiDigestLabel, "aggiungiDigestLabel", Messages.getMessage( MessageConstants.PANEL_PREFERENCE_TIMBRO_FIELD_ADDDIGEST ), 
				cTesto, 0, 2, 1, 5, margineCampi, GridBagConstraints.LINE_START );
		
		aggiungiDigestField = aggiungiCheckBox( panelTesto, aggiungiDigestField, "addDigest", "", addDigest, 
				editable, SwingConstants.LEFT, cTesto, 1, 2, 1, 5, margineCampi, GridBagConstraints.LINE_START);
		
		Boolean addDn=null;
		String addDnString=null;
		try {
			addDnString = preferenceManager.getConfigurationChiamante().getString( ConfigConstants.TIMBRO_PROPERTY_ADDDN  );
			editable = false;
		} catch (Exception e2) {
		}
		if( addDnString==null ) {
			try {
				addDnString = preferenceManager.getConfiguration().getString( ConfigConstants.TIMBRO_PROPERTY_ADDDN );
				editable = true;
			} catch (Exception e2) {
			}
		}
		addDn = Boolean.valueOf( addDnString );
		
		aggiungiDNLabel = aggiungiLabel( panelTesto, aggiungiDNLabel, "aggiungiDNLabel", Messages.getMessage( MessageConstants.PANEL_PREFERENCE_TIMBRO_FIELD_ADDDN ), 
				cTesto, 2, 2, 1, 5, margineCampi, GridBagConstraints.LINE_START );
		
		aggiungiDNField = aggiungiCheckBox( panelTesto, aggiungiDNField, "addDn", "", addDn, editable, SwingConstants.LEFT, 
				cTesto, 3, 2, 1, 5, margineCampi, GridBagConstraints.LINE_START);
		
//		boolean isFileFirmato = true;
//		logger.info("Il file da firmare è firmato? " + isFileFirmato );
//		if( isFileFirmato ){
//			aggiungiDNField.setVisible( true );
//		} else {
//			aggiungiDNField.setVisible( false );
//		}
				
		Boolean addData=false;
		String addDataString=null;
		try {
			addDataString = preferenceManager.getConfigurationChiamante().getString( ConfigConstants.TIMBRO_PROPERTY_ADDDATA  );
			editable = false;
		} catch (Exception e2) {
		}
		if( addDataString==null ) {
			try {
				addDataString = preferenceManager.getConfiguration().getString( ConfigConstants.TIMBRO_PROPERTY_ADDDATA );
				editable = true;
			} catch (Exception e2) {
			}
		}
		addData = Boolean.valueOf( addDataString );
		aggiungiDataLabel = aggiungiLabel( panelTesto, aggiungiDataLabel, "aggiungiDataLabel", Messages.getMessage( MessageConstants.PANEL_PREFERENCE_TIMBRO_FIELD_ADDDATA ), 
				cTesto, 4, 2, 1, 5, margineCampi, GridBagConstraints.LINE_START );
		
		aggiungiDataField = aggiungiCheckBox( panelTesto, aggiungiDataField, "addData", "", addData, editable, 
				SwingConstants.LEFT, cTesto, 5, 2, 1, 5, margineCampi, GridBagConstraints.LINE_START);
				
		algoritmoDigestLabel = aggiungiLabel( panelTesto, algoritmoDigestLabel, "algoritmoDigestLabel", Messages.getMessage( MessageConstants.PANEL_PREFERENCE_TIMBRO_FIELD_ALGORITMODIGEST ), 
				cTesto, 0, 3, 2, 5, margineCampi, GridBagConstraints.LINE_START ) ;
		
		DigestAlgID[] digestIds = DigestAlgID.values();
		String[] algoritmiSupportati = new String[ digestIds.length ];
		for(int i=0; i<digestIds.length;i++){
			algoritmiSupportati[i]=digestIds[i].value();
		}
		
		//editable = true;
		String algoritmo = null;
		try {
			algoritmo = preferenceManager.getConfigurationChiamante().getString( ConfigConstants.TIMBRO_PROPERTY_ALGORITMO_DIGEST  );
			editable = false;
		} catch (Exception e2) {
		}
		if(algoritmo==null) {
			try {
				algoritmo = preferenceManager.getConfiguration().getString( ConfigConstants.TIMBRO_PROPERTY_ALGORITMO_DIGEST );
			} catch (Exception e2) {
			}
			editable = true;
		}
		
		listaAlgoritmiDigest = new JComboBox(algoritmiSupportati);
		aggiungiCombo( panelTesto, listaAlgoritmiDigest, algoritmiSupportati, "algoritmiDigest", algoritmo, editable, cTesto, 2, 3, 2, 5, margineCampi, GridBagConstraints.LINE_START );
		
		return panelTesto;
	}
	
	private JPanel addPannelloOpzioniTimbroSingolo(PreferenceManager preferenceManager){
		int margineCampi = 5;
		JPanel panelTimbroSingolo = new JPanel(new GridBagLayout());
		GridBagConstraints cTimbroSingolo = new GridBagConstraints();
		Boolean timbroSingolo=null;
		String timbroSingoloString = null;
		boolean editable = false;
		try {
			timbroSingoloString = preferenceManager.getConfigurationChiamante().getString( ConfigConstants.TIMBRO_PROPERTY_TIMBROSINGOLO  );
			editable = false;
		} catch (Exception e2) {
		}
		if( timbroSingoloString==null ) {
			try {
				timbroSingoloString = preferenceManager.getConfiguration().getString( ConfigConstants.TIMBRO_PROPERTY_TIMBROSINGOLO  );
			} catch (Exception e2) {
			}
			editable = true;
		}
		timbroSingolo = Boolean.valueOf( timbroSingoloString );
		
		timbroSingoloLabel = aggiungiLabel( panelTimbroSingolo, timbroSingoloLabel, "timbroSingoloLabel", Messages.getMessage( MessageConstants.PANEL_PREFERENCE_TIMBRO_FIELD_TIMBROSINGOLO ), 
				cTimbroSingolo, 0, 0, 1, 5, margineCampi, GridBagConstraints.LINE_START ) ;
		
		timbroSingoloField = aggiungiCheckBox( panelTimbroSingolo, timbroSingoloField, "timbroSingolo", "", timbroSingolo, 
				editable, SwingConstants.LEFT, cTimbroSingolo, 1, 0, 1, 5, margineCampi, GridBagConstraints.LINE_START);
		
		return panelTimbroSingolo;
	}
	
	private JPanel addPannelloOpzioniCodifica(PreferenceManager preferenceManager){
		int margineCampi = 5;
		JPanel panelCodifica = new JPanel(new GridBagLayout());
		GridBagConstraints cCodifica = new GridBagConstraints();
		codificaLabel = aggiungiLabel(panelCodifica, codificaLabel, "codificaLabel", Messages.getMessage( MessageConstants.PANEL_PREFERENCE_TIMBRO_FIELD_CODIFICA ),
				cCodifica, 0, 0, 1, 5, margineCampi, GridBagConstraints.LINE_START);
		
		CodificaTimbro[] codifiche = CodificaTimbro.values();
		String[] codificheSupportate = new String[ codifiche.length ];
		for(int i=0; i<codifiche.length;i++){
			codificheSupportate[i]=codifiche[i].value();
		}
		String codifica = null;
		boolean editable = false;
		try {
			codifica = preferenceManager.getConfigurationChiamante().getString( ConfigConstants.TIMBRO_PROPERTY_CODIFICA  );
			editable = false;
		} catch (Exception e2) {
		}
		if( codifica==null ) {
			try {
				codifica = preferenceManager.getConfiguration().getString( ConfigConstants.TIMBRO_PROPERTY_CODIFICA  );
			} catch (Exception e2) {
			}
			editable = true;
		}
		listaCodifiche = new JComboBox( codificheSupportate );
		aggiungiCombo(panelCodifica, listaCodifiche, codificheSupportate, "codifica", codifica, editable, cCodifica, 1, 0, 1, 5, margineCampi, GridBagConstraints.LINE_START );
		
		return panelCodifica;
	}
	
	private JPanel addPannelloOpzioniPosizioniTimbro(PreferenceManager preferenceManager){
		int margineCampi = 5;
		JPanel panelPosizioneTimbro = new JPanel(new GridBagLayout());
		GridBagConstraints cPosizioneTimbro = new GridBagConstraints();
		posizioneTimbroLabel = aggiungiLabel(panelPosizioneTimbro, posizioneTimbroLabel, "posizioneTimbroLabel", Messages.getMessage( MessageConstants.PANEL_PREFERENCE_TIMBRO_FIELD_POSIZIONE ), 
				cPosizioneTimbro, 0, 0, 1, 5, margineCampi, GridBagConstraints.LINE_START);
		
		PosizioneTimbroNellaPagina[] posizioniTimbro = PosizioneTimbroNellaPagina.values();
		String[] posizioniTimbroSupportate = new String[ posizioniTimbro.length ];
		for(int i=0; i<posizioniTimbro.length;i++){
			posizioniTimbroSupportate[i]=posizioniTimbro[i].value();
		}
		String posizioneTimbro = null;
		boolean editable = false;
		try {
			posizioneTimbro = preferenceManager.getConfigurationChiamante().getString( ConfigConstants.TIMBRO_PROPERTY_POSIZIONE );
			editable = false;
		} catch (Exception e2) {
		}
		if( posizioneTimbro==null ) {
			try {
				posizioneTimbro = preferenceManager.getConfiguration().getString( ConfigConstants.TIMBRO_PROPERTY_POSIZIONE );
			} catch (Exception e2) {
			}
			editable = true;
		}
		listaPosizioniTimbro = new JComboBox(posizioniTimbroSupportate);
		aggiungiCombo(panelPosizioneTimbro, listaPosizioniTimbro, posizioniTimbroSupportate, "posizioniTimbro", posizioneTimbro, editable, cPosizioneTimbro, 1, 0, 1, 5, margineCampi, GridBagConstraints.LINE_START );
		
		rotazioneTimbroLabel = aggiungiLabel(panelPosizioneTimbro, rotazioneTimbroLabel, "rotazioneTimbroLabel", Messages.getMessage( MessageConstants.PANEL_PREFERENCE_TIMBRO_FIELD_ROTAZIONE ), 
				cPosizioneTimbro, 0, 2, 1, 5, margineCampi, GridBagConstraints.LINE_START);
		
		TipoRotazione[] rotazioniTimbro = TipoRotazione.values();
		String[] rotazioniSupportate = new String[ rotazioniTimbro.length ];
		for(int i=0; i<rotazioniTimbro.length;i++){
			rotazioniSupportate[i]=rotazioniTimbro[i].value();
		}
		String rotazioneTimbro = null;
		try {
			rotazioneTimbro = preferenceManager.getConfigurationChiamante().getString( ConfigConstants.TIMBRO_PROPERTY_ROTAZIONE );
			editable = false;
		} catch (Exception e2) {
		}
		if( rotazioneTimbro==null ) {
			try {
				rotazioneTimbro = preferenceManager.getConfiguration().getString( ConfigConstants.TIMBRO_PROPERTY_ROTAZIONE );
			} catch (Exception e2) {
			}
			editable = true;
		}
		listaRotazioniTimbro = new JComboBox( rotazioniSupportate );
		aggiungiCombo(panelPosizioneTimbro, listaRotazioniTimbro, rotazioniSupportate, "rotazioneTimbro", rotazioneTimbro, editable, cPosizioneTimbro, 1, 2, 1, 5, margineCampi, GridBagConstraints.LINE_START );
				
		return panelPosizioneTimbro;
	}
	
	private JPanel addPannelloOpzioniIntestazioniTimbro(PreferenceManager preferenceManager){
		int margineCampi = 5;
		JPanel panelIntestazioneTimbro = new JPanel(new GridBagLayout());
		GridBagConstraints cIntestazioneTimbro = new GridBagConstraints();
		boolean editable = false;
		testoIntestazioneLabel = aggiungiLabel( panelIntestazioneTimbro, testoIntestazioneLabel, "testoIntestazioneLabel", Messages.getMessage( MessageConstants.PANEL_PREFERENCE_TIMBRO_FIELD_TESTO_INTESTAZIONE ), 
				cIntestazioneTimbro, 0, 0, 2, 5, margineCampi, GridBagConstraints.LINE_START );
		
		String testoIntestazione = null;
		try {
			testoIntestazione = preferenceManager.getConfigurationChiamante().getString( ConfigConstants.TIMBRO_PROPERTY_TESTO_INTESTAZIONE );
			editable = false;
		} catch (Exception e2) {
		}
		if( testoIntestazione==null ) {
			try {
				testoIntestazione = preferenceManager.getConfiguration().getString( ConfigConstants.TIMBRO_PROPERTY_TESTO_INTESTAZIONE );
			} catch (Exception e2) {
			}
			editable = true;
		}
		testoIntestazioneField = aggiungiFieldTesto(panelIntestazioneTimbro, testoIntestazioneField, 22, "testoIntestazione", testoIntestazione, editable, cIntestazioneTimbro, 1, 0, 2, 5, margineCampi, GridBagConstraints.LINE_START );
		
		posizioneIntestazioneLabel = aggiungiLabel(panelIntestazioneTimbro, posizioneIntestazioneLabel, "posizioneIntestazioneLabel", Messages.getMessage( MessageConstants.PANEL_PREFERENCE_TIMBRO_FIELD_POSIZIONE_INTESTAZIONE ), 
				cIntestazioneTimbro, 0, 1, 1, 5, margineCampi, GridBagConstraints.LINE_START);
		
		PosizioneRispettoAlTimbro[] posizioniRispettoAlTimbro = PosizioneRispettoAlTimbro.values();
		String[] posizioniRispettoAlTimbroSupportate = new String[ posizioniRispettoAlTimbro.length + 1 ];
		posizioniRispettoAlTimbroSupportate[0]="--";
		for(int i=0; i<posizioniRispettoAlTimbro.length;i++){
			posizioniRispettoAlTimbroSupportate[i+1]=posizioniRispettoAlTimbro[i].value();
		}
		String posizioneIntestazione = null;
		try {
			posizioneIntestazione = preferenceManager.getConfigurationChiamante().getString( ConfigConstants.TIMBRO_PROPERTY_POSIZIONE_INTESTAZIONE);
			editable = false;
		} catch (Exception e2) {
		}
		if( posizioneIntestazione==null ) {
			try {
				posizioneIntestazione = preferenceManager.getConfiguration().getString( ConfigConstants.TIMBRO_PROPERTY_POSIZIONE_INTESTAZIONE);
			} catch (Exception e2) {
			}
			editable = true;
		}
		listaPosizioniIntestazione = new JComboBox(posizioniRispettoAlTimbroSupportate);
		aggiungiCombo(panelIntestazioneTimbro, listaPosizioniIntestazione, posizioniRispettoAlTimbroSupportate, "posizioniIntestazione", posizioneIntestazione, editable, cIntestazioneTimbro, 1, 1, 1, 5, margineCampi, GridBagConstraints.LINE_START );

		return panelIntestazioneTimbro;
	}
	
	private JPanel addPannelloOpzioniTestoInChiaro(PreferenceManager preferenceManager){
		int margineCampi = 5;
		JPanel panelTestoInChiaro = new JPanel(new GridBagLayout());
		GridBagConstraints cTestoInChiaro = new GridBagConstraints();
		posizioneTestoInChiaroLabel = aggiungiLabel(panelTestoInChiaro, posizioneTestoInChiaroLabel, "posizioneTestoInChiaroLabel", Messages.getMessage( MessageConstants.PANEL_PREFERENCE_TIMBRO_FIELD_POSIZIONE_TESTOINCHIARO ), 
				cTestoInChiaro, 0, 0, 1, 5, margineCampi, GridBagConstraints.LINE_START);
		
		PosizioneRispettoAlTimbro[] posizioniRispettoAlTimbro = PosizioneRispettoAlTimbro.values();
		String[] posizioniRispettoAlTimbroSupportate = new String[ posizioniRispettoAlTimbro.length + 1 ];
		posizioniRispettoAlTimbroSupportate[0]="--";
		for(int i=0; i<posizioniRispettoAlTimbro.length;i++){
			posizioniRispettoAlTimbroSupportate[i+1]=posizioniRispettoAlTimbro[i].value();
		}
		String posizioneTestoInChiaro = null;
		boolean editable = false;
		try {
			posizioneTestoInChiaro = preferenceManager.getConfigurationChiamante().getString( ConfigConstants.TIMBRO_PROPERTY_POSIZIONE_TESTO_IN_CHIARO );
			editable = false;
		} catch (Exception e2) {
		}
		if( posizioneTestoInChiaro==null ) {
			try {
				posizioneTestoInChiaro = preferenceManager.getConfiguration().getString( ConfigConstants.TIMBRO_PROPERTY_POSIZIONE_TESTO_IN_CHIARO );
			} catch (Exception e2) {
			}
			editable = true;
		}
		listaPosizioniTestoInChiaro = new JComboBox(posizioniRispettoAlTimbroSupportate);
		aggiungiCombo(panelTestoInChiaro, listaPosizioniTestoInChiaro, posizioniRispettoAlTimbroSupportate, "posizioniTestoInChiaro", posizioneTestoInChiaro, editable, cTestoInChiaro, 1, 0, 1, 5, margineCampi, GridBagConstraints.LINE_START );
		
		return panelTestoInChiaro;
	}
	
	private JPanel addPannelloOpzioniFont(PreferenceManager preferenceManager){
		int margineCampi = 5;
		
		JPanel panelFont = new JPanel(new GridBagLayout());
		GridBagConstraints cFont = new GridBagConstraints();
		
		Boolean moreLines=null;
		String moreLinesString = null;
		boolean editable = false;
		try {
			moreLinesString = preferenceManager.getConfigurationChiamante().getString( ConfigConstants.TIMBRO_PROPERTY_MORE_LINES );
			editable = false;
		} catch (Exception e2) {
		}
		if( moreLinesString==null ) {
			try {
				moreLinesString = preferenceManager.getConfiguration().getString( ConfigConstants.TIMBRO_PROPERTY_MORE_LINES );
			} catch (Exception e2) {
			}
			editable = true;
		}
		moreLines = Boolean.valueOf( moreLinesString );
		
		moreLinesLabel = aggiungiLabel( panelFont, moreLinesLabel, "moreLinesLabel", Messages.getMessage( MessageConstants.PANEL_PREFERENCE_TIMBRO_FIELD_MORELINES ), 
				cFont, 0, 0, 1, 5, margineCampi, GridBagConstraints.LINE_START);
		
		moreLinesField = aggiungiCheckBox( panelFont, moreLinesField, "moreLines", "", moreLines, editable, 
				SwingConstants.LEFT, cFont, 1, 0, 1, 5, margineCampi, GridBagConstraints.LINE_START);
				
		fontNameLabel = aggiungiLabel( panelFont, fontNameLabel, "fontNameLabel", Messages.getMessage( MessageConstants.PANEL_PREFERENCE_TIMBRO_FIELD_FONTNAME ), 
				cFont, 0, 1, 2, 5, margineCampi, GridBagConstraints.LINE_START);
		
		String[] fonts = { "--", BaseFont.COURIER, BaseFont.TIMES_ROMAN, BaseFont.HELVETICA};
		String fontName = null;
		try {
			fontName = preferenceManager.getConfigurationChiamante().getString( ConfigConstants.TIMBRO_PROPERTY_FONT_NAME);
			editable = false;
		} catch (Exception e2) {
		}
		if( fontName==null ) {
			try {
				fontName = preferenceManager.getConfiguration().getString( ConfigConstants.TIMBRO_PROPERTY_FONT_NAME);
			} catch (Exception e2) {
			}
			editable = true;
		}
		listaFonts = new JComboBox(fonts);
		aggiungiCombo( panelFont, listaFonts, fonts, "listaFonts", fontName, editable, cFont, 1, 1, 1, 5, margineCampi, GridBagConstraints.LINE_START );
		
		fontSizeLabel = aggiungiLabel( panelFont, fontSizeLabel, "fontSizeLabel", Messages.getMessage( MessageConstants.PANEL_PREFERENCE_TIMBRO_FIELD_FONTSIZE ), 
				cFont, 0, 2, 1, 5, margineCampi, GridBagConstraints.LINE_START);
		
		String[] fontSizes = { "--", "8", "9", "10", "11", "12"};
		String fontSizeS = "--";
		Integer fontSize = 0;
		try {
			fontSize = Integer.valueOf(preferenceManager.getConfigurationChiamante().getString( ConfigConstants.TIMBRO_PROPERTY_FONT_SIZE ) );
			editable = false;
		} catch (Exception e2) {
		}
		if( fontSize==0 ) {
			try {
				fontSize = Integer.valueOf(preferenceManager.getConfiguration().getString( ConfigConstants.TIMBRO_PROPERTY_FONT_SIZE ) );
				editable = true;
			} catch (Exception e2) {
			}
		}
		if( fontSize>0 )
			fontSizeS = String.valueOf( fontSize.toString() );
		
		listaFontSizes = new JComboBox(fontSizes);
		aggiungiCombo( panelFont, listaFontSizes, fontSizes, "listaFontSizes", fontSizeS, editable, cFont, 1, 2, 1, 5, margineCampi, GridBagConstraints.LINE_START );
		
		return panelFont;
	}
	
	private JPanel addPannelloOpzioniPagine(PreferenceManager preferenceManager){
		int margineCampi = 5;
		
		JPanel panelPagina = new JPanel(new GridBagLayout());
		GridBagConstraints cPagina = new GridBagConstraints();
		tipoPaginaLabel = aggiungiLabel(panelPagina, tipoPaginaLabel, "tipoPaginaLabel", Messages.getMessage( MessageConstants.PANEL_PREFERENCE_TIMBRO_FIELD_TIPOPAGINA ), 
				cPagina, 0, 0, 4, 5, margineCampi, GridBagConstraints.LINE_START );
		
		TipoPagina[] tipiPagina = TipoPagina.values();
		String[] tipiPaginaSupportati = new String[ tipiPagina.length + 1 ];
		tipiPaginaSupportati[0]="--";
		for(int i=0; i<tipiPagina.length;i++){
			tipiPaginaSupportati[i+1]=tipiPagina[i].value();
		}
		String tipoPagina = null;
		boolean editable = false;
		try {
			tipoPagina = preferenceManager.getConfigurationChiamante().getString( ConfigConstants.TIMBRO_PROPERTY_TIPO_PAGINA );
			editable = false;
		} catch (Exception e2) {
		}
		if( tipoPagina==null ) {
			try {
				tipoPagina = preferenceManager.getConfiguration().getString( ConfigConstants.TIMBRO_PROPERTY_TIPO_PAGINA );
			} catch (Exception e2) {
			}
			editable = true;
		}
		listaTipiPagina = new JComboBox(tipiPaginaSupportati);
		aggiungiCombo(panelPagina, listaTipiPagina, tipiPaginaSupportati, "tipiPagine", tipoPagina, editable, cPagina, 0, 1, 2, 5, margineCampi, GridBagConstraints.LINE_START );
		AL2 = new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				if (listaTipiPagina.getSelectedIndex() > 0 ){
					timbroPaginaCorrenteField.setSelected( false );
					paginaDaField.setText("");
					paginaAField.setText("");
					labelErrore.setText("");
				}
			}
		};
		listaTipiPagina.addActionListener(AL2);
		
		Boolean paginaCorrente=null;
		String paginaCorrenteString=null;
		try {
			paginaCorrenteString = preferenceManager.getConfigurationChiamante().getString( ConfigConstants.TIMBRO_PROPERTY_PAGINA_CORRENTE );
			editable = false;
		} catch (Exception e2) {
		}
		if( paginaCorrenteString==null ) {
			try {
				paginaCorrenteString = preferenceManager.getConfiguration().getString( ConfigConstants.TIMBRO_PROPERTY_PAGINA_CORRENTE );
			} catch (Exception e2) {
			}
			editable = true;
		}
		paginaCorrente = Boolean.valueOf( paginaCorrenteString );
		timbroPaginaCorrenteField = aggiungiCheckBox( panelPagina, timbroPaginaCorrenteField, "paginaCorrente", Messages.getMessage( MessageConstants.PANEL_PREFERENCE_TIMBRO_FIELD_PAGINACORRENTE ), paginaCorrente, 
				editable, SwingConstants.LEFT, cPagina, 2, 1, 2, 5, margineCampi, GridBagConstraints.LINE_START);
		
		AL3 = new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				if (timbroPaginaCorrenteField.isSelected() ) {
					paginaDaField.setText("");
					paginaAField.setText("");
					listaTipiPagina.setSelectedIndex(0);
					labelErrore.setText("");
				}
			}
		};
		timbroPaginaCorrenteField.addActionListener(AL3);
		
		paginaDaLabel = aggiungiLabel(panelPagina, paginaDaLabel, "paginaDaLabel", Messages.getMessage( MessageConstants.PANEL_PREFERENCE_TIMBRO_FIELD_PAGINADA ), 
				cPagina, 0, 3, 1, 5, margineCampi, GridBagConstraints.LINE_START);
		Integer paginaDa=0;
		String paginaDaS="";
		try {
			paginaDa = Integer.valueOf(preferenceManager.getConfigurationChiamante().getString( ConfigConstants.TIMBRO_PROPERTY_PAGINA_DA ) );
			editable = false;
		} catch (Exception e2) {
		}
		if( paginaDa==0 ) {
			try {
				paginaDa = Integer.valueOf(preferenceManager.getConfiguration().getString( ConfigConstants.TIMBRO_PROPERTY_PAGINA_DA ) );
				editable = true;
			} catch (Exception e2) {
			}
		}
		if( paginaDa>0 )
			paginaDaS = String.valueOf( paginaDa.toString() );
		
		paginaDaField = aggiungiFieldTesto( panelPagina, paginaDaField, 3, "paginaDaField", paginaDaS, editable, cPagina, 1, 3, 1, 5, margineCampi, GridBagConstraints.LINE_START );
		
		paginaALabel = aggiungiLabel(panelPagina, paginaALabel, "paginaALabel", Messages.getMessage( MessageConstants.PANEL_PREFERENCE_TIMBRO_FIELD_PAGINAA ), 
				cPagina, 2, 3, 1, 5, margineCampi, GridBagConstraints.LINE_START );
		Integer paginaA=0;
		String paginaAS="";
		try {
			paginaA = Integer.valueOf(preferenceManager.getConfigurationChiamante().getString( ConfigConstants.TIMBRO_PROPERTY_PAGINA_A ) );
			editable = false;
		} catch (Exception e2) {
		}
		if( paginaA==0 ) {
			try {
				paginaA = Integer.valueOf(preferenceManager.getConfiguration().getString( ConfigConstants.TIMBRO_PROPERTY_PAGINA_A ) );
				editable = true;
			} catch (Exception e2) {
			}
		}
		if( paginaA>0 )
			paginaAS = String.valueOf( paginaA.toString() );
		
		paginaAField = aggiungiFieldTesto( panelPagina, paginaAField, 3, "paginaAField", paginaAS, editable, cPagina, 3, 3, 1, 5, margineCampi, GridBagConstraints.LINE_START );
		labelErrore = aggiungiLabel( panelPagina, labelErrore, "labelErrore", "", cPagina, 0, 4, 4, 5, margineCampi, GridBagConstraints.LINE_START);
		KA1 = new KeyAdapter(){
			public void keyPressed(KeyEvent e) {
				char c = e.getKeyChar();
				if (c >= '0' && c <= '9') {
                		//paginaDaField.setEditable(true);
                		labelErrore.setText("");
                } else if  ( c != KeyEvent.VK_BACK_SPACE && 
				         c != KeyEvent.VK_DELETE && c != KeyEvent.VK_ENTER) {
                		paginaDaField.setText("");
                		labelErrore.setText("Campo di tipo numerico");
                }
			}
		};
		AL4 = new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				if ( paginaDaField.getText()!=null &&  !paginaDaField.getText().equalsIgnoreCase("")) {
					timbroPaginaCorrenteField.setSelected( false );
					listaTipiPagina.setSelectedIndex( 0 );
				}
				if ( paginaDaField.getText()!=null && !paginaDaField.getText().equalsIgnoreCase("") && 
						(paginaAField.getText()==null ||  paginaAField.getText().equalsIgnoreCase("") ) ) {
					labelErrore.setText("Campo \"A Pagina\" non valorizzato");
				}
			}
		};
		FL1 = new FocusListener(){
			public void focusGained(FocusEvent e) {
			}

			public void focusLost(FocusEvent e) {
				if ( paginaDaField.getText()!=null &&  !paginaDaField.getText().equalsIgnoreCase("")) {
					timbroPaginaCorrenteField.setSelected( false );
					listaTipiPagina.setSelectedIndex( 0 );
				}
				if ( paginaDaField.getText()!=null && !paginaDaField.getText().equalsIgnoreCase("") && 
						(paginaAField.getText()==null ||  paginaAField.getText().equalsIgnoreCase("") ) ) {
					labelErrore.setText( "Campo \"A Pagina\" non valorizzato" );
				}
			}
		};
		KA2 = new KeyAdapter(){
			public void keyPressed(KeyEvent e) {
				char c = e.getKeyChar();
				if (c >= '0' && c <= '9') {
                		labelErrore.setText("");
                } else if (c != KeyEvent.VK_BACK_SPACE && 
				         c != KeyEvent.VK_DELETE && c != KeyEvent.VK_ENTER) {
                		paginaAField.setText("");
                		labelErrore.setText("Campo di tipo numerico");
                }
			}
		};
		AL5 = new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				if ( paginaAField.getText()!=null &&  !paginaAField.getText().equalsIgnoreCase("") ) {
					timbroPaginaCorrenteField.setSelected( false );
					listaTipiPagina.setSelectedIndex( 0 );
				}
				if ( paginaAField.getText()!=null && !paginaAField.getText().equalsIgnoreCase("") && 
						(paginaDaField.getText()==null ||  paginaDaField.getText().equalsIgnoreCase("") ) ) {
					labelErrore.setText("Campo \"Da Pagina\" non valorizzato");
				}
			}
		};
		FL2 = new FocusListener(){
			public void focusGained(FocusEvent e) {
			}

			public void focusLost(FocusEvent e) {
				if ( paginaAField.getText()!=null &&  !paginaAField.getText().equalsIgnoreCase("") ) {
					timbroPaginaCorrenteField.setSelected( false );
					listaTipiPagina.setSelectedIndex( 0 );
				}
				if ( paginaAField.getText()!=null && !paginaAField.getText().equalsIgnoreCase("") && 
						(paginaDaField.getText()==null ||  paginaDaField.getText().equalsIgnoreCase("") ) ) {
					labelErrore.setText("Campo \"Da Pagina\" non valorizzato");
				}
			}
		};
		paginaDaField.addKeyListener(KA1);
		paginaAField.addKeyListener(KA2);
		paginaDaField.addFocusListener(FL1);
		paginaAField.addFocusListener(FL2);
		paginaDaField.addActionListener(AL4);
		paginaAField.addActionListener(AL5);
		
		return panelPagina;
	}
	
	
	
	private void salvaConfigurazione(PreferenceManager preferenceManager){
		
		PosizioneRispettoAlTimbro posizioneIntestazione = null;
		if( !((String)listaPosizioniIntestazione.getSelectedItem()).equalsIgnoreCase("--")) { 
			String posizioneSelezionata = (String)listaPosizioniIntestazione.getSelectedItem();
			posizioneIntestazione = PosizioneRispettoAlTimbro.fromValue( posizioneSelezionata );
		}
		PosizioneRispettoAlTimbro posizioneTestoInChiaro = null;
		if( !((String)listaPosizioniTestoInChiaro.getSelectedItem()).equalsIgnoreCase("--")) {
			String posizioneSelezionata = (String)listaPosizioniTestoInChiaro.getSelectedItem();
			posizioneTestoInChiaro = PosizioneRispettoAlTimbro.fromValue( posizioneSelezionata  );
		}
		
		if( controllaOpzioniTimbro(
				PosizioneTimbroNellaPagina.fromValue((String)listaPosizioniTimbro.getSelectedItem()), 
				TipoRotazione.fromValue((String)listaRotazioniTimbro.getSelectedItem()), 
				posizioneIntestazione, posizioneTestoInChiaro
			)){
			try {
				if( testoTimbroField.getText()!=null && testoTimbroField.isEditable() )
					PreferenceManager.saveProp( ConfigConstants.TIMBRO_PROPERTY_TESTO, testoTimbroField.getText());
				if( testoIntestazioneField.getText()!=null && testoIntestazioneField.isEditable() )
					PreferenceManager.saveProp( ConfigConstants.TIMBRO_PROPERTY_TESTO_INTESTAZIONE, testoIntestazioneField.getText());
				if( listaCodifiche.getSelectedItem()!=null && listaCodifiche.isEnabled())
					PreferenceManager.saveProp( ConfigConstants.TIMBRO_PROPERTY_CODIFICA, listaCodifiche.getSelectedItem() );
				if( listaPosizioniTimbro.getSelectedItem()!=null && listaPosizioniTimbro.isEnabled())
					PreferenceManager.saveProp( ConfigConstants.TIMBRO_PROPERTY_POSIZIONE, listaPosizioniTimbro.getSelectedItem() );
				if( listaRotazioniTimbro.getSelectedItem()!=null && listaRotazioniTimbro.isEnabled())
					PreferenceManager.saveProp( ConfigConstants.TIMBRO_PROPERTY_ROTAZIONE, listaRotazioniTimbro.getSelectedItem() );
				if( listaTipiPagina.getSelectedItem()!=null && listaTipiPagina.isEnabled())
					PreferenceManager.saveProp( ConfigConstants.TIMBRO_PROPERTY_TIPO_PAGINA, listaTipiPagina.getSelectedItem() );
				if( listaPosizioniIntestazione.getSelectedItem()!=null && listaPosizioniIntestazione.isEnabled())
					PreferenceManager.saveProp( ConfigConstants.TIMBRO_PROPERTY_POSIZIONE_INTESTAZIONE, listaPosizioniIntestazione.getSelectedItem() );
				if( listaPosizioniTestoInChiaro.getSelectedItem()!=null && listaPosizioniTestoInChiaro.isEnabled())
					PreferenceManager.saveProp( ConfigConstants.TIMBRO_PROPERTY_POSIZIONE_TESTO_IN_CHIARO, listaPosizioniTestoInChiaro.getSelectedItem() );
				if( listaFonts.getSelectedItem()!=null && listaFonts.isEnabled())
					PreferenceManager.saveProp( ConfigConstants.TIMBRO_PROPERTY_FONT_NAME, listaFonts.getSelectedItem() );
				if( listaFontSizes.getSelectedItem()!=null && listaFontSizes.isEnabled())
					PreferenceManager.saveProp( ConfigConstants.TIMBRO_PROPERTY_FONT_SIZE, listaFontSizes.getSelectedItem() );
				if( paginaDaField.getText()!=null && paginaDaField.isEditable())
					PreferenceManager.saveProp( ConfigConstants.TIMBRO_PROPERTY_PAGINA_DA, paginaDaField.getText() );
				if( paginaAField.getText()!=null && paginaAField.isEditable())
					PreferenceManager.saveProp( ConfigConstants.TIMBRO_PROPERTY_PAGINA_A, paginaAField.getText() );
				if( listaAlgoritmiDigest.getSelectedItem()!=null && listaAlgoritmiDigest.isEnabled())
					PreferenceManager.saveProp( ConfigConstants.TIMBRO_PROPERTY_ALGORITMO_DIGEST, listaAlgoritmiDigest.getSelectedItem() );
				if( timbroSingoloField.isEnabled())
					PreferenceManager.saveProp( ConfigConstants.TIMBRO_PROPERTY_TIMBROSINGOLO, timbroSingoloField.isSelected());
				if( timbroPaginaCorrenteField.isEnabled())
					PreferenceManager.saveProp( ConfigConstants.TIMBRO_PROPERTY_PAGINA_CORRENTE, timbroPaginaCorrenteField.isSelected());
				if( moreLinesField.isEnabled())
					PreferenceManager.saveProp( ConfigConstants.TIMBRO_PROPERTY_MORE_LINES, moreLinesField.isSelected());
				if( aggiungiDigestField.isEnabled())
					PreferenceManager.saveProp( ConfigConstants.TIMBRO_PROPERTY_ADDDIGEST, aggiungiDigestField.isSelected());
				if( aggiungiDataField.isEnabled())
					PreferenceManager.saveProp( ConfigConstants.TIMBRO_PROPERTY_ADDDATA, aggiungiDataField.isSelected());
				if( aggiungiDNField.isEnabled())
					PreferenceManager.saveProp( ConfigConstants.TIMBRO_PROPERTY_ADDDN, aggiungiDNField.isSelected());
				
				preferenceManager.reinitConfig();
				
				JOptionPane.showMessageDialog(this,
						Messages.getMessage( MessageConstants.MSG_TIMBRO_SAVE_SUCCESS ),
						"",//Messages.getMessage( MessageConstants.MSG_ERROR_GENERAL_TITLE ),
						JOptionPane.INFORMATION_MESSAGE );
			} catch (Exception e1) {
				e1.printStackTrace();
				JOptionPane.showMessageDialog(this,
					Messages.getMessage( MessageConstants.MSG_TIMBRO_ERROR_GENERIC_SAVE ),
					Messages.getMessage( MessageConstants.MSG_ERROR_GENERAL_TITLE ),
					JOptionPane.ERROR_MESSAGE);
			}
		}
	}
	
	private boolean controllaOpzioniTimbro(PosizioneTimbroNellaPagina posizioneTimbro, TipoRotazione rotazioneTimbro, PosizioneRispettoAlTimbro posizioneIntestazione, PosizioneRispettoAlTimbro posizioneTestoInChiaro){
		if( posizioneIntestazione!=null ){ 
			boolean posizioneTestoValida = TimbroUtils.validaPosizioneTesto(posizioneTimbro, rotazioneTimbro, posizioneIntestazione);
			if( !posizioneTestoValida ){
				JOptionPane.showMessageDialog(this,
						Messages.getMessage( MessageConstants.MSG_TIMBRO_ERROR_POSIZIONETESTO ),
						Messages.getMessage( MessageConstants.MSG_ERROR_GENERAL_TITLE ),
						JOptionPane.ERROR_MESSAGE);
				return false;
			}
		}
		if( posizioneTestoInChiaro!=null ){ 
			boolean posizioneTestoValida = TimbroUtils.validaPosizioneTesto(posizioneTimbro, rotazioneTimbro, posizioneTestoInChiaro );
			if( !posizioneTestoValida ){
				JOptionPane.showMessageDialog(this,
						Messages.getMessage( MessageConstants.MSG_TIMBRO_ERROR_POSIZIONETESTO ),
						Messages.getMessage( MessageConstants.MSG_ERROR_GENERAL_TITLE ),
						JOptionPane.ERROR_MESSAGE);
				return false;
			}
		}
		
		if( posizioneIntestazione!=null && posizioneTestoInChiaro!=null && posizioneIntestazione.equals(posizioneTestoInChiaro) ){
			JOptionPane.showMessageDialog(this,
					Messages.getMessage( MessageConstants.MSG_TIMBRO_ERROR_POSIZIONETESTI ),
					Messages.getMessage( MessageConstants.MSG_ERROR_GENERAL_TITLE ),
					JOptionPane.ERROR_MESSAGE);
			return false;
		}
		
		
		if( moreLinesField.isSelected() ){
			if( listaFontSizes.getSelectedIndex()>0 || listaFonts.getSelectedIndex()>0){
				JOptionPane.showMessageDialog(this,
						Messages.getMessage( MessageConstants.MSG_TIMBRO_FONTFIXED ),
						Messages.getMessage( MessageConstants.MSG_WARNING_TITLE ),
						JOptionPane.WARNING_MESSAGE);
			}
		}
		
		return true;
	}

	public void dispose() {

		this.removeAll();

		if(windowDialog!=null)
			windowDialog.removeAll();
		windowDialog=null;

		save=null;
		cancel=null;

		testoTimbroLabel=null;
		testoTimbroField=null;
		aggiungiDigestLabel=null;
		aggiungiDigestField=null;
		algoritmoDigestLabel=null;
		listaAlgoritmiDigest=null;
		aggiungiDNLabel=null;
		aggiungiDNField=null;
		aggiungiDataLabel=null;
		aggiungiDataField=null;
		timbroSingoloLabel=null;
		timbroSingoloField=null;
		codificaLabel=null;
		listaCodifiche=null;
		posizioneTimbroLabel=null;
		listaPosizioniTimbro=null;
		rotazioneTimbroLabel=null;
		listaRotazioniTimbro=null;
		testoIntestazioneLabel=null;
		testoIntestazioneField=null;
		posizioneIntestazioneLabel=null;
		listaPosizioniIntestazione=null;
		posizioneTestoInChiaroLabel=null;
		listaPosizioniTestoInChiaro=null;
		moreLinesLabel=null;
		moreLinesField=null;
		fontNameLabel=null;
		listaFonts=null;
		fontSizeLabel=null;
		listaFontSizes=null;
		tipoPaginaLabel=null;
		listaTipiPagina=null;
		timbroPaginaCorrenteField=null;
		paginaDaLabel=null;
		paginaALabel=null;
		paginaDaField=null;
		paginaAField=null;

		labelErrore=null;

		AL2=null;
		AL3=null;
		AL4=null;
		AL5=null;
		KA1=null;
		KA2=null;
		FL1=null;
		FL2=null;
	}
	
}