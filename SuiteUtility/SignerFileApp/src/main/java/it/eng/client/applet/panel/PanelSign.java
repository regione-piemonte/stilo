package it.eng.client.applet.panel;

import it.eng.client.applet.ManualFile;

import it.eng.client.applet.OptionSignDialog;
import it.eng.client.applet.SmartCard;
import it.eng.client.applet.i18N.MessageKeys;
import it.eng.client.applet.i18N.Messages;
import it.eng.client.applet.operation.Factory;
import it.eng.client.applet.operation.ts.TimeStamperUtility;
import it.eng.client.applet.thread.CardPresentThread;
import it.eng.client.applet.thread.ConfigurationThread;
import it.eng.client.applet.thread.SignerThread;
import it.eng.client.applet.util.FileBean;
import it.eng.client.applet.util.FileUtility;
import it.eng.client.applet.util.PreferenceKeys;
import it.eng.client.applet.util.PreferenceManager;
import it.eng.client.applet.util.WSClientUtils;
import it.eng.common.LogWriter;
import it.eng.common.type.SignatureMerge;
import it.eng.common.type.SignerType;
import it.eng.cryptoutil.verify.util.ResponseUtils;
import it.eng.fileOperation.clientws.FileOperationResponse;
import it.eng.fileOperation.clientws.ResponseSigVerify;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileOutputStream;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.smartcardio.CardTerminal;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JProgressBar;
import javax.swing.border.LineBorder;

import org.apache.commons.io.FileUtils;

public class PanelSign extends JPanel implements ActionListener {

	private static final long serialVersionUID = 1L;
	
	private SmartCard card;
	
	//bottoni
	private JPanel bottonieraPanel;
	private JButton jButtonSigner = null;
	private JButton jButtonSignAndMark = null;
	private JButton jButtonMark = null;
	private JButton jButtonCounterSign=null;
	private JButton jButtonVerificaFirme=null;
	private JButton jButtonFirmaManuale;
		
	private JPasswordField jPin = null;
	private JComboBox jcombocardreader = null;
	private JProgressBar jProgressBar = null;
	
	private JLabel labelOS = null;
	
	private JLabel labelreader = null;
	private JLabel labelcard = null;
	
	private JLabel labelFile = null;
	
	private JLabel labelModalitaFirma = null;
	private JComboBox modalitaFirma = null;
	private JComboBox tipoFirma = null;
	private JCheckBox marcaSoloNonMarcate = null;
	
	private CardPresentThread cardPresentThread;  
		
	//per test inserisco la seleziona manuale del file
	private JLabel toSignFile=null;
	private JButton btnSelectFile=null;
	
	private File outputFile=null;
	
	private final String SELECTFILE = "selectFile";
	private final String VERIFICAFIRME = "verificaFirme";
	private final String FIRMA = "firma";
	private final String CONTROFIRMA = "controfirma";
	private final String FIRMAEMARCA = "firmaEmarca";
	private final String MARCA = "marca";
	
	public void setThreadControl(CardPresentThread thread){
		cardPresentThread = thread;
	}
		
	/**
	 * This is the default constructor
	 */
	public PanelSign(SmartCard card) {
		super(new BorderLayout());
		this.card = card;
		initialize();
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		LogWriter.writeLog("Panel Sign intialize START");
		
		JPanel lJPanelPrincipal = new JPanel();
		lJPanelPrincipal.setLayout( new GridBagLayout() );
		GridBagConstraints c = new GridBagConstraints();
		c.gridx=0;
		c.anchor=GridBagConstraints.LINE_START;
		c.gridx=0;
		c.weightx=1.0;
		c.insets = new Insets(2,10,2,10);
		int y=0;		
		
		c.gridy=y++;
		lJPanelPrincipal.add( createSOPanel(), c );
		
		c.gridy=y++;
		lJPanelPrincipal.add( createFilePanel(), c );
		
		c.gridy=y++;
		c.insets = new Insets(5,10,2,10);
		lJPanelPrincipal.add( createConfigPropertyPanel(), c );
		
		c.gridy=y++;
		c.anchor=GridBagConstraints.CENTER;
		c.insets = new Insets(10,10,10,10);
		lJPanelPrincipal.add( createFirmaPanel(), c );
				
		this.add(lJPanelPrincipal, BorderLayout.NORTH);
		
		//Carico i provider e controllo le carte e il sistema operativo presente
		ConfigurationThread thread = new ConfigurationThread( getJProgressBar(), 
				(SmartCard)card, getJPin(), getJButtonSigner(), this );
		thread.start();
		
		((SmartCard)card).getJTabbedPane().setEnabled(false);
		
		LogWriter.writeLog("Panel Sign intialize END");
	}
	
	private JPanel createSOPanel(){
		JPanel labelPanelLeftOS = new JPanel( new GridBagLayout() );
		GridBagConstraints c = new GridBagConstraints();
		c.anchor = GridBagConstraints.LINE_START;
		c.insets = new Insets(10,0,0,0);
		labelOS = new JLabel();
		Font newLabelFont = new Font( labelOS.getFont().getName(), Font.BOLD, labelOS.getFont().getSize() );
		labelOS.setFont( newLabelFont );
		labelOS.setText( Messages.getMessage( MessageKeys.PANEL_SIGN_LABEL_SO ) );
		c.gridx = 0;
		c.gridy = 0;
		labelPanelLeftOS.add( labelOS, c );
		return labelPanelLeftOS;
	}
	
	public void setSO(String osname){
		labelOS.setText( Messages.getMessage( MessageKeys.PANEL_SIGN_LABEL_SO ) + " " + osname );
	}

	private JPanel createConfigPropertyPanel(){
		JPanel lJPanel = new JPanel( new GridBagLayout() );
		GridBagConstraints c = new GridBagConstraints();
		c.anchor = GridBagConstraints.LINE_START;
		
		JPanel panel1 = new JPanel( new GridBagLayout() );
		GridBagConstraints c1 = new GridBagConstraints();
		c1.insets = new Insets(2,0,2,10);
		c1.anchor = GridBagConstraints.LINE_START;
		JLabel labelTipoFirma = new JLabel( Messages.getMessage( MessageKeys.PANEL_PREFERENCE_FIRMAMARCA_FIELD_ENVELOPETYPE ) );
		c1.gridx = 0;
		c1.gridy = 0;
		panel1.add( labelTipoFirma, c1 );
		String[] firmeDisponibili = PreferenceManager.getStringArray( PreferenceKeys.PROPERTY_SIGN_ENVELOPE_TYPE_OPTIONS );
		tipoFirma = new JComboBox( firmeDisponibili );
		String tipoFirmaDefault = PreferenceManager.getString( PreferenceKeys.PROPERTY_SIGN_ENVELOPE_TYPE, "" );
		LogWriter.writeLog("tipoFirma=" + tipoFirmaDefault );
		int indiceSelezionato = 0;
		for( int i = 0; i<firmeDisponibili.length; i++){
			if( firmeDisponibili[i].equalsIgnoreCase( tipoFirmaDefault ) )
				indiceSelezionato = i;
		}
		tipoFirma.setSelectedIndex( indiceSelezionato );
		tipoFirma.addActionListener( new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if( ((String)tipoFirma.getSelectedItem()).equalsIgnoreCase( SignerType.PDF.name() )){
					modalitaFirma.setVisible( false );
					labelModalitaFirma.setVisible( false );
				} else {
					modalitaFirma.setVisible( true );
					labelModalitaFirma.setVisible( true );
				}
				
				if( card.getFile()!=null && card.getFile().exists() ){
					FileBean fileBean = FileUtility.valorizzaFile( (String)tipoFirma.getSelectedItem(), card.isFirmato(), card.isPDF(), card.getFile(), card.getFileName() );
					card.setFile(fileBean.getFile() );
					if( fileBean.getFileNameConvertito()!=null )
						card.setFileNameConvertito( fileBean.getFileNameConvertito() );
					if( fileBean.getIsPdf()!=null )
						card.setPDF( fileBean.getIsPdf() );
					if( fileBean.getIsFirmato()!=null )
						card.setFirmato( fileBean.getIsFirmato() );
				}
				showFileLabel();
			}
		});
		c1.gridx = 1;
		c1.gridy = 0;
		panel1.add( tipoFirma, c1 );
		labelModalitaFirma = new JLabel( Messages.getMessage( MessageKeys.PANEL_PREFERENCE_FIRMAMARCA_FIELD_ENVELOPEMERGE ) );
		c1.gridx = 0;
		c1.gridy = 1;
		panel1.add( labelModalitaFirma, c1 );
		String[] modalitaDisponibili = PreferenceManager.getStringArray( PreferenceKeys.PROPERTY_SIGN_ENVELOPE_MERGE_OPTIONS );
		modalitaFirma = new JComboBox( modalitaDisponibili );
		String modalitaDefault = PreferenceManager.getString( PreferenceKeys.PROPERTY_SIGN_ENVELOPE_MERGE , "" );
		LogWriter.writeLog("modalitaFirma=" + modalitaDefault );
		indiceSelezionato = 0;
		for( int i = 0; i<firmeDisponibili.length; i++){
			if( modalitaDisponibili[i].equalsIgnoreCase( modalitaDefault ) )
				indiceSelezionato = i;
		}
		modalitaFirma.setSelectedIndex( indiceSelezionato );
		c1.gridx = 1;
		c1.gridy = 1;
		panel1.add( modalitaFirma, c1 );
		
		if( ((String)tipoFirma.getSelectedItem()).equalsIgnoreCase( SignerType.PDF.name() )){
			modalitaFirma.setVisible( false );
			labelModalitaFirma.setVisible( false );
		} else {
			modalitaFirma.setVisible( true );
			labelModalitaFirma.setVisible( true );
		}

		JPanel panel2 = new JPanel( new GridBagLayout() );
		GridBagConstraints c2 = new GridBagConstraints();
		JLabel labelFirmaSolo = new JLabel( Messages.getMessage( MessageKeys.PANEL_PREFERENCE_FIRMAMARCA_FIELD_MARKONLYNOTTIMESTAMPED ) );
		labelFirmaSolo.setVisible( false );
		c2.gridx = 0;
		c2.gridy = 0;
		panel2.add( labelFirmaSolo, c2 );
		marcaSoloNonMarcate = new JCheckBox();
		boolean firmaSoloDefault = PreferenceManager.getBoolean( PreferenceKeys.PROPERTY_SIGN_MARCATURA_SOLO_NON_MARCATE );
		marcaSoloNonMarcate.setSelected( firmaSoloDefault ); 
		marcaSoloNonMarcate.setVisible( false );
		c2.gridx = 1;
		c2.gridy = 0;
		panel2.add( marcaSoloNonMarcate, c2 );
		
		c.gridx = 0;
		c.gridy = 0;
		lJPanel.add( panel1, c);
		c.gridx = 0;
		c.gridy = 1;
		lJPanel.add( panel2, c);
		
		//lJPanel.setBorder( BorderFactory.createLineBorder( Color.black ) );
		
		return lJPanel;
	}
	
	private JPanel createFirmaPanel(){
		JPanel firmaPanel = new JPanel( new GridBagLayout() );
		GridBagConstraints c = new GridBagConstraints();
		c.insets = new Insets(10, 10, 2, 10);
		c.gridy=0;
		c.anchor=GridBagConstraints.CENTER;
		firmaPanel.add( createEmulationPanel(), c);
		
		c.insets = new Insets(2, 5, 2, 5);
		c.gridy=1;
		firmaPanel.add( createPinPanel(), c );
		
		c.gridy=2;
		firmaPanel.add( createCardReaderPanel(), c );
		
		c.gridy=3;
		c.insets = new Insets(10, 5, 10, 5);
		firmaPanel.add( createBottonieraPanel(), c);
		
		firmaPanel.setBorder( BorderFactory.createLineBorder( Color.black ) );
		
		return firmaPanel;
	}
	
	private JPanel createPinPanel(){
		JPanel lJPanelPin = new JPanel();
		JLabel lJLabelPin = new JLabel( Messages.getMessage( MessageKeys.PANEL_SIGN_LABEL_PIN ) );
		lJLabelPin.setLabelFor(jPin);
		lJPanelPin.add(lJLabelPin);
		lJPanelPin.add(getJPin());
		jPin.setVisible(true);
		jPin.setColumns(10);
		return lJPanelPin;
	}
	
	private JPanel createEmulationPanel(){
		JPanel labelPanelLeftEmulation = new JPanel( new GridLayout(1, 1) );
		JLabel labelEmulation = new JLabel();
		labelEmulation.setText( Messages.getMessage( MessageKeys.PANEL_SIGN_LABEL_EMULATIONENABLED ) );
		if( PreferenceManager.getBoolean( PreferenceKeys.PROPERTY_SIGN_EMULATIONMODE ) )
			labelEmulation.setVisible( true );
		else 
			labelEmulation.setVisible( false );
		labelPanelLeftEmulation.add( labelEmulation );
		return labelPanelLeftEmulation;
	}
	
	private JPanel createCardReaderPanel(){
		JPanel lJPanelCardReader = new JPanel( new GridBagLayout() );
		GridBagConstraints c = new GridBagConstraints();
		c.insets = new Insets(2, 0, 2, 0);
		c.gridx = 0;
		c.gridy = 0;
		labelcard = new JLabel();
		labelcard.setText( Messages.getMessage( MessageKeys.PANEL_SIGN_LABEL_CARDBUSY ) );
		labelcard.setLabelFor(getJcombocardreader());
		lJPanelCardReader.add( labelcard, c );
		labelcard.setVisible(false);
		c.gridx = 0;
		c.gridy = 1;
		lJPanelCardReader.add(getJcombocardreader(), c);
		jcombocardreader.setVisible(false);	
		labelreader = new JLabel();
		labelreader.setText( Messages.getMessage( MessageKeys.PANEL_SIGN_LABEL_NOSMARTCARDDETECTED ) );
		c.gridx = 0;
		c.gridy = 2;
		lJPanelCardReader.add( labelreader, c );
		return lJPanelCardReader;
	}
	
	private JPanel createBottonieraPanel(){
		//pannello per la bottoniera
		bottonieraPanel = new JPanel();
		
		bottonieraPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		//bottonieraPanel.setBorder(new LineBorder(new Color(0, 0, 0), 2));
		bottonieraPanel.add(getJButtonSigner(),BorderLayout.CENTER);
		Boolean markEnabled = PreferenceManager.getBoolean( PreferenceKeys.PROPERTY_MARK_ENABLED );
	    if (markEnabled) LogWriter.writeLog("Parametro markEnabled TRUE");
	    else LogWriter.writeLog("Parametro markEnabled FALSE");
		if( markEnabled){
			bottonieraPanel.add(getJButtonSignAndMark(),BorderLayout.CENTER);
			bottonieraPanel.add(getJButtonMarca(),BorderLayout.CENTER);
		}
		Boolean counterSignEnabled = PreferenceManager.getBoolean( PreferenceKeys.PROPERTY_COUNTERSIGN_ENABLED );
	    if (counterSignEnabled) LogWriter.writeLog("Parametro counterSignEnabled TRUE");
	    else LogWriter.writeLog("Parametro counterSignEnabled FALSE");
		if( counterSignEnabled){
			bottonieraPanel.add(getJButtonCounterSign(),BorderLayout.CENTER);
		}
		bottonieraPanel.add(getLblFirmaManuale(),BorderLayout.CENTER);
		bottonieraPanel.add(getJProgressBar());

		jButtonSigner.setVisible(false);
		jButtonSigner.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		jButtonFirmaManuale.setVisible(false);
		
		return bottonieraPanel;
	}
	
	private JPanel createFilePanel(){
		JPanel panelFile = new JPanel( new GridBagLayout() );
		GridBagConstraints c = new GridBagConstraints();
		c.gridx = 0;
		c.gridy = 0;
		c.insets = new Insets(5, 0, 0, 0);
		labelFile = new JLabel();
		panelFile.add( labelFile, c );
		boolean verificaFirmeEnabled = PreferenceManager.getBoolean( PreferenceKeys.PROPERTY_SIGN_VERIFICAFIRME_ENABLED );
		if (verificaFirmeEnabled)
			LogWriter.writeLog("Parametro verificaFirmeEnabled TRUE");
		else
			LogWriter.writeLog("Parametro verificaFirmeEnabled FALSE");
		if( verificaFirmeEnabled ){
			jButtonVerificaFirme = new JButton( Messages.getMessage( MessageKeys.PANEL_SIGN_BUTTON_VERIFICAFIRME ) );
			jButtonVerificaFirme.setActionCommand( VERIFICAFIRME );
			jButtonVerificaFirme.addActionListener( this );
			c.gridx = 1;
			c.gridy = 0;
			c.insets = new Insets(0, 20, 0, 10);
			panelFile.add(jButtonVerificaFirme, c);
		}
		
		c.gridx = 0;
		c.gridy = 1;
		toSignFile = new JLabel( Messages.getMessage( MessageKeys.PANEL_SIGN_LABEL_FILESELECTION ) );
		c.insets = new Insets(5, 0, 0, 0);
		panelFile.add( toSignFile, c );
		btnSelectFile = new JButton( Messages.getMessage( MessageKeys.PANEL_SIGN_BUTTON_SCEGLIFILE ) );
		btnSelectFile.setActionCommand( SELECTFILE );
		btnSelectFile.addActionListener(this);
		c.gridx = 1;
		c.gridy = 1;
		c.insets = new Insets(0, 20, 0, 10);
		panelFile.add( btnSelectFile, c );
		
		return panelFile;
	}
	
	public JLabel getLabelCard(){
		return labelcard;
	}
		
	public void setCardPresent(boolean ispresent, boolean emulation){
		if(ispresent || emulation){
			showEnabledButtons(true);
			jPin.setVisible(true);
			labelcard.setVisible(false);
			jButtonFirmaManuale.setVisible(false);
		}else{
			showEnabledButtons(false);
			jPin.setVisible(true);
			labelcard.setVisible(true);
			jButtonFirmaManuale.setVisible(true);
		}
		
	}
	

	/**
	 * This method initializes jButtonSigner	
	 * 	
	 * @return javax.swing.JButton	
	 */
	public JButton getJButtonSigner(){
		if (jButtonSigner == null) {
			URL picURL = getClass().getResource("buttonsigner.png");
			ImageIcon cup = new ImageIcon(picURL);
			jButtonSigner = new JButton(cup);
			jButtonSigner.setToolTipText( Messages.getMessage( MessageKeys.PANEL_SIGN_BUTTON_SIGN ) );
			jButtonSigner.setActionCommand( FIRMA );
			jButtonSigner.addActionListener(this);
		}
		return jButtonSigner;
	}
	
	public JButton getJButtonSignAndMark(){
		if (jButtonSignAndMark == null) {
			URL picURL = getClass().getResource("buttonSignEMarca.png");
			ImageIcon cup = new ImageIcon(picURL);
			jButtonSignAndMark = new JButton(cup);
			jButtonSignAndMark.setToolTipText( Messages.getMessage(MessageKeys.PANEL_SIGN_BUTTON_SIGNMARK ) );
			jButtonSignAndMark.setActionCommand( FIRMAEMARCA );
			jButtonSignAndMark.addActionListener(this);
		}
		return jButtonSignAndMark;
	}
	
	public JButton getJButtonMarca(){
		if (jButtonMark == null) {
			URL picURL = getClass().getResource("buttonMarca.png");
			ImageIcon cup = new ImageIcon(picURL);
			jButtonMark = new JButton(cup);
			jButtonMark.setToolTipText( Messages.getMessage( MessageKeys.PANEL_SIGN_BUTTON_MARK ) );
			jButtonMark.setActionCommand( MARCA );
			jButtonMark.addActionListener(this);
		}
		return jButtonMark;
	}
	
	public JButton getJButtonCounterSign(){
		if (jButtonCounterSign == null) {
			URL picURL = getClass().getResource("buttoncountersigner.png");
			ImageIcon cup = new ImageIcon(picURL);
			jButtonCounterSign = new JButton(cup);
			jButtonCounterSign.setToolTipText( Messages.getMessage( MessageKeys.PANEL_SIGN_BUTTON_COUNTERSIGN ) );
			jButtonCounterSign.setActionCommand( CONTROFIRMA );
			jButtonCounterSign.addActionListener(this);
		}
		return jButtonCounterSign;
	}
	
	
	/**
	 * This method initializes jPin	
	 * 	
	 * @return javax.swing.JPasswordField	
	 */
	public JPasswordField getJPin() {
		if (jPin == null) {
			jPin = new JPasswordField();
		}
		return jPin;
	}

	/**
	 * This method initializes jProgressBar	
	 * 	
	 * @return javax.swing.JProgressBar	
	 */
	private JProgressBar getJProgressBar() {
		if (jProgressBar == null) {
			jProgressBar = new JProgressBar();
			jProgressBar.setVisible(true);
		}
		return jProgressBar;
	}

	/**
	 * This method initializes jcombocardreader	
	 * 	
	 * @return javax.swing.JComboBox	
	 */
	public JComboBox getJcombocardreader() {
		if (jcombocardreader == null) {
			jcombocardreader = new JComboBox();
			jcombocardreader.addItemListener(new java.awt.event.ItemListener() {
				public void itemStateChanged(java.awt.event.ItemEvent e) {
					cardPresentThread.setCardName(jcombocardreader.getSelectedItem().toString());
				}
			});
		}
		return jcombocardreader;
	}
	
	public void clearReader(){
		try{
			jcombocardreader.removeAllItems();
		}catch(Exception e){
			e.printStackTrace();
		}
		//Nascondo la combo e visualizzo il messaggio di nessun lettore presente
		labelreader.setVisible(true);
		jcombocardreader.setVisible(false);
		
		//Visualizzo il bottone di firma manuale
		jButtonFirmaManuale.setVisible(true);
		
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
	private JButton getLblFirmaManuale() {
		if (jButtonFirmaManuale == null) {
			jButtonFirmaManuale = new JButton();
			jButtonFirmaManuale.setToolTipText( Messages.getMessage( MessageKeys.PANEL_SIGN_BUTTON_FIRMAOFFLINE ) );
			jButtonFirmaManuale.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent evt) {
						ManualFile mf = new ManualFile(card.getBaseurl(), card.getCookie(), card.getFile());
						mf.setVisible(true);
						
					}
			});
			jButtonFirmaManuale.setIcon(new ImageIcon(PanelSign.class.getResource("buttonsignermanual.png")));
			jButtonFirmaManuale.setCursor( Cursor.getPredefinedCursor( Cursor.HAND_CURSOR ) );
		}
		return jButtonFirmaManuale;
	}
	
	
	public void endSignerUpload() {
		//JSObject.getWindow((SmartCard)card).eval("endObjectSigner();");
	}

	public void showManualSign(){
		jButtonFirmaManuale.setVisible(true);
	}

	public void showFileLabel(){
		if( card.getFile()!=null && card.getFile().exists())
			labelFile.setText( Messages.getMessage( MessageKeys.PANEL_SIGN_LABEL_FILESELECTION_OK ) + " " + card.getFile().getName() );
		else
			labelFile.setText( Messages.getMessage( MessageKeys.PANEL_SIGN_LABEL_FILESELECTION_KO ) );
		
		if( (card.getFile()==null || !card.getFile().exists()) && PreferenceManager.enabled( PreferenceKeys.PROPERTY_SIGN_FILE_SELECTION_ENABLED ) ){
			btnSelectFile.setVisible(true);
			toSignFile.setVisible(true);
			labelFile.setVisible( false );
			bottonieraPanel.setVisible( false );
		} else {
			btnSelectFile.setVisible(false);
			toSignFile.setVisible(false);
			labelFile.setVisible( true );
			bottonieraPanel.setVisible( true );
		}
		
		if( card.isFirmato() ){
			if (jButtonVerificaFirme != null)
				jButtonVerificaFirme.setVisible(true);
		} else 
			if (jButtonVerificaFirme != null)
				jButtonVerificaFirme.setVisible(false);
	}
	
	/**
	 * ascolta i bottoni di firma abilitati
	 */
	public void actionPerformed(ActionEvent e) {
		try{
			String command=e.getActionCommand();
			LogWriter.writeLog("command:"+command);
			//verifico se hai premuto il bottone seleziona file
			if (command.equals( SELECTFILE ) ){
				JFileChooser filechooser = new JFileChooser();
				int returnVal = filechooser.showOpenDialog( this );
				if (returnVal == JFileChooser.APPROVE_OPTION) {
						File file=filechooser.getSelectedFile();
						if( file!=null && file.exists() ){
							if(FileUtility.isPdf(file)){
								LogWriter.writeLog("Il file da firmare è PDF");
								card.setPDF( true);
							} else {
								LogWriter.writeLog("Il file da firmare non è PDF");
								card.setPDF( false );
							}
							
							try{
								card.setTipoBusta( Factory.isSigned(file) );
								LogWriter.writeLog("Tipo di busta del file: "+card.getTipoBusta());
							} catch(Exception e1){
								LogWriter.writeLog("Errore " + e1.getMessage() );
								card.setFile( null );
							}

							//verifico se va fatta la conversione in pdf
							
							FileBean fileBean = FileUtility.valorizzaFile( (String)getTipoFirma().getSelectedItem() , card.getTipoBusta()!=null, card.isPDF(), file, card.getFileName());
							card.setFile( fileBean.getFile() );
							if( fileBean.getFileNameConvertito()!=null )
								card.setFileNameConvertito( fileBean.getFileNameConvertito() );
							if( fileBean.getIsPdf()!=null )
								card.setPDF( fileBean.getIsPdf() );
							if( fileBean.getIsFirmato()!=null )
								card.setFirmato( fileBean.getIsFirmato() );
						}
						showFileLabel();
				}
				return;
			}
			
			if(card.getFile()==null){
				JOptionPane.showMessageDialog( card.getJTabbedPane(), "seleziona un file", "",JOptionPane.ERROR_MESSAGE );
				return;
			}
			
			boolean timemark=false;
			boolean counterSign=false;
			if(command.equals( VERIFICAFIRME ) ){
				
				boolean caCheckFirme = PreferenceManager.getBoolean( PreferenceKeys.PROPERTY_SIGN_CACHECKENABLED );
				boolean crlCheckFirme = PreferenceManager.getBoolean( PreferenceKeys.PROPERTY_SIGN_CRLCHECKENABLED );
				Date dataRif = null;
				try {
					String dataRifString = PreferenceManager.getString( PreferenceKeys.PROPERTY_SIGN_DATARIFERIMENTOVERIFICA );
					LogWriter.writeLog( "Proprietà "+ PreferenceKeys.PROPERTY_SIGN_DATARIFERIMENTOVERIFICA + ": " + dataRifString );
					String dataRifFormato = PreferenceManager.getString( PreferenceKeys.PROPERTY_SIGN_DATARIFERIMENTOVERIFICA_FORMATO );
					LogWriter.writeLog( "Proprietà "+ PreferenceKeys.PROPERTY_SIGN_DATARIFERIMENTOVERIFICA_FORMATO + ": " + dataRifFormato );
					if( dataRifFormato!=null && dataRifString!=null ){
						SimpleDateFormat sdf = new SimpleDateFormat( dataRifFormato );
						dataRif = sdf.parse( dataRifString );
					}
				} catch (Exception e1) {
					LogWriter.writeLog("Errore nel recupero delle proprietà " + PreferenceKeys.PROPERTY_SIGN_DATARIFERIMENTOVERIFICA, e1 );
				}
				FileOperationResponse risposta=WSClientUtils.verificaFirme( caCheckFirme, crlCheckFirme, dataRif, card.getFile() );
				if(!ResponseUtils.isResponseOK(risposta)){
					//prendo la risposta alla verifica
					if(risposta.getGenericError()!=null){
						throw new Exception("si sono verificati errori nell'invocazione del servizio di verifica:"+risposta.getGenericError());
					}
					if(risposta.getFileoperationResponse()!=null && risposta.getFileoperationResponse().getFileOperationResults()!=null){
						it.eng.fileOperation.clientws.Response.FileoperationResponse.FileOperationResults foresults=new it.eng.fileOperation.clientws.Response.FileoperationResponse.FileOperationResults();
						foresults.getFileOperationResult().addAll(risposta.getFileoperationResponse().getFileOperationResults().getFileOperationResult());	 
						ResponseSigVerify resp= ResponseUtils.getResponse(foresults, ResponseSigVerify.class);
						if(resp==null){
							JOptionPane.showMessageDialog( card.getJTabbedPane(), Messages.getMessage(MessageKeys.MSG_FIRMAMARCA_ERROR_GENERIC ) , 
									Messages.getMessage(MessageKeys.MSG_FIRMAMARCA_ERROR_GENERIC ), JOptionPane.ERROR_MESSAGE);
							return;
						}
						String[] options = new String[] { Messages.getMessage( MessageKeys.PANEL_BUTTON_CKECKRESULT ) };
						int choice=OptionSignDialog.createDialog( card.getPanelsign(), WSClientUtils.processHierarchy( resp.getSigVerifyResult() ), 
								Messages.getMessage( MessageKeys.MSG_FIRMAMARCA_ERROR_VERIFICAFIRMA_SIGNATURENOTVALID ), Messages.getMessage( MessageKeys.PANEL_SIGN_BUTTON_VERIFICAFIRME ), JOptionPane.PLAIN_MESSAGE, 
								JOptionPane.PLAIN_MESSAGE,null, options, options[0]);
						if(choice==JOptionPane.CLOSED_OPTION){
							return;
						}
					}
				}
				
				return;
			} else if(command.equals( FIRMAEMARCA ) ){
				 timemark=true;
			} else if(command.equals( MARCA )){
				TimeStamperUtility.addMarca(this.card.getFile(), marcaSoloNonMarcate.isSelected() );
				JOptionPane.showMessageDialog(card.getJTabbedPane(), Messages.getMessage(MessageKeys.MSG_OPSUCCESS),"",JOptionPane.INFORMATION_MESSAGE);
				return ;
			} else if(command.equals( CONTROFIRMA ) ){
				counterSign=true;
			} 
			
			if( jPin.getPassword()==null || new String(jPin.getPassword()).length()==0){
				JOptionPane.showMessageDialog( card.getJTabbedPane(), 
						Messages.getMessage( MessageKeys.MSG_FIRMAMARCA_ERROR_PINNULL ),
						Messages.getMessage( MessageKeys.MSG_ERROR ), JOptionPane.ERROR_MESSAGE );
				return;
			}
			
			//verifico se posso fare al firma congiunta
			String envMerge = null;
			if( ((String)tipoFirma.getSelectedItem()).equalsIgnoreCase( SignerType.PDF.name() )){
				envMerge = SignatureMerge.CONGIUNTA.value();
			} else {
				envMerge = (String)modalitaFirma.getSelectedItem();
			}
			boolean congiunta = card.getTipoBusta()!=null && envMerge.equalsIgnoreCase( SignatureMerge.CONGIUNTA.value() );
			LogWriter.writeLog("Il file è firmato ed è stata richiesta la modalità congiunta?  " + congiunta );
			if( congiunta ){
				LogWriter.writeLog("firma congiunta verifica firme attuali attiva invio il file al WS");
				//verifica se le firme sono valide chiamando il ws altrimenti chiedi all'utente 
				//che occorre fare una firma verticale
				boolean caCheckFirme = PreferenceManager.getBoolean( PreferenceKeys.PROPERTY_SIGN_CACHECKENABLED );
				boolean crlCheckFirme = PreferenceManager.getBoolean( PreferenceKeys.PROPERTY_SIGN_CRLCHECKENABLED );
				Date dataRif = null;
				try {
					String dataRifString = PreferenceManager.getString( PreferenceKeys.PROPERTY_SIGN_DATARIFERIMENTOVERIFICA );
					LogWriter.writeLog( "dataRifString: " + dataRifString );
					String dataRifFormato = PreferenceManager.getString( PreferenceKeys.PROPERTY_SIGN_DATARIFERIMENTOVERIFICA_FORMATO );
					LogWriter.writeLog( "dataRifFormato: " + dataRifFormato );
					if( dataRifFormato!=null && dataRifString!=null ){
						SimpleDateFormat sdf = new SimpleDateFormat( dataRifFormato );
						dataRif = sdf.parse( dataRifString );
					}
					
				} catch (Exception e1) {
					LogWriter.writeLog("Errore nel recupero delle proprietà " + PreferenceKeys.PROPERTY_SIGN_DATARIFERIMENTOVERIFICA, e1 );
				}
				FileOperationResponse risposta=WSClientUtils.verificaFirme( caCheckFirme, crlCheckFirme, dataRif ,card.getFile());
				if(risposta!=null && !ResponseUtils.isResponseOK(risposta)){
					//prendo la risposta alla verifica
					if(risposta.getGenericError()!=null){
						throw new Exception("si sono verificati errori nell'invocazione del servizio di verifica:"+risposta.getGenericError());
					}
					if(risposta.getFileoperationResponse()!=null && risposta.getFileoperationResponse().getFileOperationResults()!=null){
						it.eng.fileOperation.clientws.Response.FileoperationResponse.FileOperationResults foresults=new it.eng.fileOperation.clientws.Response.FileoperationResponse.FileOperationResults();
						foresults.getFileOperationResult().addAll(risposta.getFileoperationResponse().getFileOperationResults().getFileOperationResult());	 
						ResponseSigVerify resp= ResponseUtils.getResponse(foresults, ResponseSigVerify.class);
						if(resp==null){
							JOptionPane.showMessageDialog(card.getJTabbedPane(), Messages.getMessage(MessageKeys.MSG_FIRMAMARCA_ERROR_GENERIC ),
									Messages.getMessage(MessageKeys.MSG_FIRMAMARCA_ERROR_GENERIC ) , JOptionPane.ERROR_MESSAGE);
							return;
						}
						String[] options = new String[] { Messages.getMessage( MessageKeys.PANEL_BUTTON_SI ), Messages.getMessage( MessageKeys.PANEL_BUTTON_NO ), Messages.getMessage( MessageKeys.PANEL_BUTTON_CKECKRESULT ) };
						int choice=OptionSignDialog.createDialog( card.getPanelsign(), 
								WSClientUtils.processHierarchy(resp.getSigVerifyResult()), 
								Messages.getMessage( MessageKeys.MSG_FIRMAMARCA_ERROR_JOINTSIGNNOTPOSSIBLE ), 
								Messages.getMessage( MessageKeys.PANEL_BUTTON_CKECKRESULT ), 
								JOptionPane.QUESTION_MESSAGE, JOptionPane.PLAIN_MESSAGE,null, options, options[1]);
						if(choice==JOptionPane.OK_OPTION){
							LogWriter.writeLog("Imposto la modalità firma verticale");
							congiunta = false;
						}else{
							LogWriter.writeLog("Firma annullata");
							return;
						}
					}
				}
			}
			showEnabledButtons( false );
			jPin.setVisible( false );
						
			//Stoppo il thread di controllo dei lettori
			cardPresentThread.stopThread();
			
			//Disabilito la combo
			jcombocardreader.setEnabled( false );
			
			//Recupero lo slot selezionato
			int slot = jcombocardreader.getSelectedIndex();
			
			String outputFileName = FileUtility.getOutputFileName( card.getFile(), (String)tipoFirma.getSelectedItem() );
			outputFile = new File( card.getFile().getParentFile() + "/output/" + outputFileName);
			LogWriter.writeLog("outputFile " + outputFile );
			FileOutputStream outputStream = FileUtils.openOutputStream( outputFile );	
						
			SignerThread thread = new SignerThread((SmartCard)card, 
					SignerType.valueOf((String)tipoFirma.getSelectedItem()),
					(String)modalitaFirma.getSelectedItem(),
					jPin.getPassword(), jProgressBar, slot, congiunta, timemark, counterSign,
					outputStream);
			 
			//Avvio il thread di firma
			thread.start();
			
			((SmartCard)card).getJTabbedPane().setEnabled(false);
			
		} catch(Exception er){
			JOptionPane.showMessageDialog(card.getJTabbedPane(),
					Messages.getMessage(MessageKeys.MSG_FIRMAMARCA_ERROR_GENERIC ) +er.getMessage(),
					Messages.getMessage(MessageKeys.MSG_ERROR), JOptionPane.ERROR_MESSAGE);
		}
		
	}
	
	public void signStarted(){
		showEnabledButtons( false );
		jPin.setVisible( false );
					
		//Stoppo il thread di controllo dei lettori
		cardPresentThread.stopThread();
		
		//Disabilito la combo
		jcombocardreader.setEnabled( false );
		
		card.getJTabbedPane().setEnabled(false);
		if (card.getPreferenceMenu() != null)
			card.getPreferenceMenu().setEnabled(false);
		if (card.getInvioLogMenu() != null)
			card.getInvioLogMenu().setEnabled(false);
	}
	
	public void signStopped(boolean esitoFirma){
		jProgressBar.setVisible(false);
		showEnabledButtons(true);
		jPin.setVisible(true);
		jcombocardreader.setEnabled(true);
		
		card.getJTabbedPane().setEnabled(true);
		if (card.getPreferenceMenu() != null)
			card.getPreferenceMenu().setEnabled(true);
		if (card.getInvioLogMenu() != null)
			card.getInvioLogMenu().setEnabled(true);

		if( esitoFirma )
			card.closeApplet();
	}
	
	public void showMessageDialog( String message, String title, int messageType){
		JOptionPane.showMessageDialog( card.getJTabbedPane(), message, title, messageType );
	}
	
	public void gestisciEccezione(String errorMessage){
		//showManualSign();
		JOptionPane.showMessageDialog( card.getJTabbedPane(),"Errore di firma! "+ errorMessage, "Errore", JOptionPane.ERROR_MESSAGE);
		jProgressBar.setVisible(false);
		jProgressBar.setValue(0);
		jProgressBar.setString("");
		card.getJTabbedPane().setEnabled(true);
		card.getPanelsign().showEnabledButtons(true);
		card.getPanelsign().getJPin().setVisible(true);
		card.getPanelsign().getJPin().setText("");
	}
		
	public void showEnabledButtons(boolean visible){
		//TODO il bottone marca deve essere attiva solo se il file è firmato
		// ma anche se è attiva la funzione di marcatura è impostata al url del servizio
		jButtonSigner.setVisible(visible);
		if( jButtonSignAndMark!=null )
			jButtonSignAndMark.setVisible(visible);
		//TODO la marca per i pdf non è supportata
		if(card.isFirmato() && !((String)tipoFirma.getSelectedItem()).equals(SignerType.PDF.name())){
			if( jButtonMark!=null )
				jButtonMark.setVisible(visible);
			if( jButtonCounterSign!=null )
				jButtonCounterSign.setVisible(visible);
		}else{
			if( jButtonMark!=null )
				jButtonMark.setVisible(false);
			if( jButtonCounterSign!=null )
				jButtonCounterSign.setVisible(false);
		}
	}
	
	public File getOutputFile() {
		return outputFile;
	}

	public void setOutputFile(File outputFile) {
		this.outputFile = outputFile;
	}

	public JComboBox getModalitaFirma() {
		return modalitaFirma;
	}

	public void setModalitaFirma(JComboBox modalitaFirma) {
		this.modalitaFirma = modalitaFirma;
	}

	public JComboBox getTipoFirma() {
		return tipoFirma;
	}

	public void setTipoFirma(JComboBox tipoFirma) {
		this.tipoFirma = tipoFirma;
	}

	public JCheckBox getMarcaSoloNonMarcate() {
		return marcaSoloNonMarcate;
	}

	public void setMarcaSoloNonMarcate(JCheckBox marcaSoloNonMarcate) {
		this.marcaSoloNonMarcate = marcaSoloNonMarcate;
	}
	
}  
