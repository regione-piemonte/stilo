package it.eng.areas.hybrid.module.siss.ui;

import it.eng.areas.hybrid.module.siss.bean.FileBean;
import it.eng.areas.hybrid.module.siss.bean.HashFileBean;
import it.eng.areas.hybrid.module.siss.fileOutputProvider.FileOutputProvider;
import it.eng.areas.hybrid.module.siss.messages.MessageKeys;
import it.eng.areas.hybrid.module.siss.messages.Messages;
import it.eng.areas.hybrid.module.siss.preferences.PreferenceKeys;
import it.eng.areas.hybrid.module.siss.preferences.PreferenceManager;
import it.eng.areas.hybrid.module.siss.thread.CardPresentThread;
import it.eng.areas.hybrid.module.siss.thread.ConfigurationThread;
import it.eng.areas.hybrid.module.siss.thread.SignerThread;
import it.eng.areas.hybrid.module.siss.util.FileUtility;
import it.eng.areas.hybrid.module.siss.util.SignerType;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.smartcardio.CardTerminal;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import org.apache.log4j.Logger;

public class PanelSign extends JPanel implements ActionListener {

	public final static Logger logger = Logger.getLogger(PanelSign.class);
	
	private static final long serialVersionUID = 1L;
	
	private JPanel lJPanelPrincipal = null;
	
	//bottoni
	private JPanel bottonieraPanel;
	private JButton jButtonSigner = null;
	private JButton jButtonSignAndMark = null;
		
	private JPasswordField jPin = null;
	private JComboBox jcombocardreader = null;
	private JProgressBar jProgressBar = null;
	
	private JLabel labelOS = null;
	
	private JLabel labelreader = null;
	private JLabel labelcard = null;
	
	private JLabel labelModalitaFirma = null;
	private JComboBox modalitaFirma = null;
	private JComboBox tipoFirma = null;
	private JCheckBox marcaSoloNonMarcate = null;
	
	private CardPresentThread cardPresentThread;  
	private SissApplication appl;
		
	//per test inserisco la seleziona manuale del file
	private JPanel panelFile=null;
	private JPanel signFilesPanel=null;
	private JScrollPane scrollFilePanel = null; 
	
	private JLabel labelFile=null;
	
	private JTextArea textArea;
	
	private final String FIRMA = "firma";
	private final String FIRMAEMARCA = "firmaEmarca";
	
	private List<FileBean> listFilesToSign;
	
	public void setThreadControl(CardPresentThread thread){
		cardPresentThread = thread;
	}
		
	/**
	 * This is the default constructor
	 */
	public PanelSign(SissApplication appl) {
		super(new BorderLayout());
		this.appl = appl;
		initialize();
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		logger.info("Panel Sign intialize START");
		
		lJPanelPrincipal = new JPanel();
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
		
		c.gridy=y++;
		c.anchor=GridBagConstraints.CENTER;
		c.insets = new Insets(10,10,10,10);
		lJPanelPrincipal.add( createPanelloAttivita(), c );
				
		this.add(lJPanelPrincipal, BorderLayout.NORTH);
		
		//Carico i provider e controllo le carte e il sistema operativo presente
		ConfigurationThread thread = new ConfigurationThread( getJProgressBar(), 
				(SissApplication)appl, getJPin(), getJButtonSigner(), this );
		thread.start();
		
		((SissApplication)appl).getJTabbedPane().setEnabled(false);
		
		logger.info("Panel Sign intialize END");
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
		logger.info("firmeDisponibili=" + firmeDisponibili.length );
		tipoFirma = new JComboBox( firmeDisponibili );
		String tipoFirmaDefault = PreferenceManager.getString( PreferenceKeys.PROPERTY_SIGN_ENVELOPE_TYPE, "" );
		logger.info("tipoFirma=" + tipoFirmaDefault );
		int indiceSelezionato = 0;
		for( int i = 0; i<firmeDisponibili.length; i++){
			if( firmeDisponibili[i].equalsIgnoreCase( tipoFirmaDefault ) )
				indiceSelezionato = i;
		}
		tipoFirma.setSelectedIndex( indiceSelezionato );
		tipoFirma.addActionListener( new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
//				if( ((String)tipoFirma.getSelectedItem()).equalsIgnoreCase( SignerType.PDF.name() ) 
//						||  ((String)tipoFirma.getSelectedItem()).equalsIgnoreCase( SignerType.XAdES.name() )){
//					modalitaFirma.setVisible( false );
//					labelModalitaFirma.setVisible( false );
//				} else {
					modalitaFirma.setVisible( true );
					labelModalitaFirma.setVisible( true );
//				}
				
				if (appl.getFileBeanList() != null && appl.getFileBeanList().size() > 0) {
					Iterator<FileBean> itr = appl.getFileBeanList().iterator();
					while (itr.hasNext()) {
						FileBean bean = itr.next();
						String beanFileName = bean.getFileName();
						logger.info("Elaborazione file " + bean.getFile() );
						bean.setRootFileDirectory( bean.getFile().getParentFile() );
						
						if(FileUtility.isPdf( bean.getFile() )){
							logger.info("Il file da firmare è PDF");
							bean.setIsPdf( true);
						} else {
							logger.info("Il file da firmare non è PDF");
							bean.setIsPdf( false );
						}
//						
//						try{
//							bean.setTipoBusta( Factory.isSigned( bean.getFile() ) );
//							logger.info("Tipo di busta del file: "+ bean.getTipoBusta());
//						} catch(Exception e1){
//							logger.info("Errore " + e1.getMessage() );
//							///this.file = null;
//						}

						//verifico se va fatta la conversione in pdf
						String tipoBustaSelezionata = (String)tipoFirma.getSelectedItem();
						logger.info( "Tipo di firma selezionata: " + tipoBustaSelezionata );
						
						bean = FileUtility.valorizzaFile( tipoBustaSelezionata, bean.getTipoBusta()!=null, bean );
						if( bean!=null ){
							if( bean.getIsFirmato() )
								appl.setFileFirmatoPresente(true);
							appl.setFileTuttiFirmati( bean.getIsFirmato() && appl.isFileTuttiFirmati() );
							//fileBeanList.add( bean );
							
						} else {
							itr.remove();
							textArea.append( beanFileName + " ignorato a causa di un errore nell'acquisizione\n");
						}
						
					}
				}
				
				showFiles();
			}
		});
		
		c1.gridx = 1;
		c1.gridy = 0;
		panel1.add( tipoFirma, c1 );
		
		
		labelModalitaFirma = new JLabel( Messages.getMessage( MessageKeys.PANEL_PREFERENCE_FIRMAMARCA_FIELD_ENVELOPEMERGE ) );
		labelModalitaFirma.setVisible( false );
		c1.gridx = 0;
		c1.gridy = 1;
		panel1.add( labelModalitaFirma, c1 );
		String[] modalitaDisponibili = PreferenceManager.getStringArray( PreferenceKeys.PROPERTY_SIGN_ENVELOPE_MERGE_OPTIONS );
		modalitaFirma = new JComboBox( modalitaDisponibili );
		String modalitaDefault = PreferenceManager.getString( PreferenceKeys.PROPERTY_SIGN_ENVELOPE_MERGE , "" );
		logger.info("modalitaFirma=" + modalitaDefault );
		indiceSelezionato = 0;
		for( int i = 0; i<modalitaDisponibili.length; i++){
			if( modalitaDisponibili[i].equalsIgnoreCase( modalitaDefault ) )
				indiceSelezionato = i;
		}
		modalitaFirma.setSelectedIndex( indiceSelezionato );
		c1.gridx = 1;
		c1.gridy = 1;
		panel1.add( modalitaFirma, c1 );
		
//		if( ((String)tipoFirma.getSelectedItem()).equalsIgnoreCase( SignerType.PDF.name() ) || 
//				((String)tipoFirma.getSelectedItem()).equalsIgnoreCase( SignerType.XAdES.name() )){
//			modalitaFirma.setVisible( false );
//			labelModalitaFirma.setVisible( false );
//		} else {
			modalitaFirma.setVisible( true );
			labelModalitaFirma.setVisible( true );
//		}
		
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
		//firmaPanel.add( createPinPanel(), c );
		
		c.gridy=2;
		firmaPanel.add( createCardReaderPanel(), c );
		
		c.gridy=3;
		c.insets = new Insets(10, 5, 10, 5);
		firmaPanel.add( createBottonieraPanel(), c);
		
		firmaPanel.setBorder( BorderFactory.createLineBorder( Color.black ) );
		
		return firmaPanel;
	}
	
	private JPanel createPanelloAttivita(){
		JPanel attivitaPanel = new JPanel( new GridBagLayout() );
		GridBagConstraints c = new GridBagConstraints();
		c.insets = new Insets(10, 10, 2, 10);
		c.gridy=0;
		c.anchor=GridBagConstraints.CENTER;
		
		textArea = new JTextArea(5,25);
		//textArea.setVisible(false);
		attivitaPanel.add( textArea, c);
		
		return attivitaPanel;
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
		bottonieraPanel.add(getJButtonSigner(),BorderLayout.CENTER);
		bottonieraPanel.add(getJButtonSignAndMark(),BorderLayout.CENTER);
		bottonieraPanel.add(getJProgressBar());

		jButtonSigner.setVisible(false);
		jButtonSigner.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		
		return bottonieraPanel;
	}
	
	private JPanel createFilePanel(){
		panelFile = new JPanel( new GridBagLayout() );
		GridBagConstraints c = new GridBagConstraints();
		c.weightx = 1.0;
		c.weighty = 1.0;
		c.insets = new Insets(5, 0, 0, 0);
		
		signFilesPanel = new JPanel(new GridBagLayout());
		signFilesPanel.setBackground(Color.WHITE);
		
		scrollFilePanel = new JScrollPane(signFilesPanel,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED ,JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scrollFilePanel.setMinimumSize(new Dimension(300, 70));
		scrollFilePanel.setPreferredSize(new Dimension(300, 70));
		
		c.insets = new Insets(10, 0, 10, 0);
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 0;
		c.gridy = 0;
		c.gridwidth=2;
		panelFile.add( scrollFilePanel, c );
		
		labelFile = new JLabel( );
		c.gridx = 0;
		c.gridy = 1;
		c.insets = new Insets(5, 0, 5, 10);
		c.gridwidth=2;
		panelFile.add( labelFile, c );
		
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
		}else{
			showEnabledButtons(false);
			jPin.setVisible(true);
			labelcard.setVisible(true);
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
//		jButtonFirmaManuale.setVisible(true);
		
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
	
	
	
	public void endSignerUpload() {
		//JSObject.getWindow((SmartCard)card).eval("endObjectSigner();");
	}


	public void showFiles(){
		GridBagConstraints c = new GridBagConstraints();
		c.insets = new Insets(3, 10, 3, 10);
		if( appl.getFileBeanList() !=null && !appl.getFileBeanList().isEmpty() ){
			signFilesPanel.removeAll();
			for(int i=0;i<appl.getFileBeanList().size();i++ ){
				FileBean bean = appl.getFileBeanList().get(i);
				if( bean!=null ){
					JLabel labelNomeFile = new JLabel();
					if( bean.getFileNameConvertito()!=null)
						labelNomeFile.setText( bean.getFileNameConvertito() );
					else
						labelNomeFile.setText( bean.getFileName() );
					
					c.gridx = 0;
					c.gridy = i;
					c.weightx = 1.0;
					c.weighty = 1.0;
					c.anchor=GridBagConstraints.LINE_START;
					signFilesPanel.add(labelNomeFile, c);
					
				}
			}
		} else	if( !appl.getHashfilebean().isEmpty() ){
			labelFile.setText( Messages.getMessage( MessageKeys.PANEL_SIGN_LABEL_HASHFILES ) );
		} else {
			labelFile.setText( Messages.getMessage( MessageKeys.PANEL_SIGN_LABEL_FILESELECTION_KO ) );
		}
		
		if( appl.getFileBeanList()!=null && !appl.getFileBeanList().isEmpty() ){
			labelFile.setVisible( false );
			scrollFilePanel.setVisible( true );
			signFilesPanel.setVisible( true );
			bottonieraPanel.setVisible( true );
		} else if( appl.getHashfilebean()!=null && !appl.getHashfilebean().isEmpty() ){
			labelFile.setVisible( true );
			scrollFilePanel.setVisible( false );
			signFilesPanel.setVisible( true );
			bottonieraPanel.setVisible( true );
		} else  {
			labelFile.setVisible( true );
			scrollFilePanel.setVisible( false );
			bottonieraPanel.setVisible( false );
		}
		
		boolean showFileList = false;
		try {
			showFileList = PreferenceManager.getBoolean( PreferenceKeys.PROPERTY_SIGN_SHOWLISTFILE);
			logger.info( "Proprietà " + PreferenceKeys.PROPERTY_SIGN_SHOWLISTFILE + ": " + showFileList);
		} catch (Exception e) {
			logger.info("Errore nel recupero della proprietà " + PreferenceKeys.PROPERTY_LOGFILEENABLED );
		}
		if( !showFileList ){
			scrollFilePanel.setVisible( false );
		} 
	}
	
	/**
	 * ascolta i bottoni di firma abilitati
	 */
	public void actionPerformed(ActionEvent e) {
		try{
			String command=e.getActionCommand();
			logger.info("command:"+command);
			//verifico se hai premuto il bottone seleziona file
			
			if((appl.getFileBeanList()==null || appl.getFileBeanList().isEmpty()) && appl.getHashfilebean().isEmpty() ){
				JOptionPane.showMessageDialog( appl.getJTabbedPane(), "seleziona un file", "",JOptionPane.ERROR_MESSAGE );
				return;
			}
			
			textArea.setVisible(true);
			
			boolean timemark=false;
			if(command.equals( FIRMAEMARCA ) ){
				 timemark=true;
			} 
			
//			if( jPin.getPassword()==null || new String(jPin.getPassword()).length()==0){
//				JOptionPane.showMessageDialog( card.getJTabbedPane(), 
//						Messages.getMessage( MessageKeys.MSG_FIRMAMARCA_ERROR_PINNULL ),
//						Messages.getMessage( MessageKeys.MSG_ERROR ), JOptionPane.ERROR_MESSAGE );
//				return;
//			}
			
			//clono le liste per passarle (dopo aver eventualmente tolto oggetti) al processo di firma  
			listFilesToSign = new ArrayList<FileBean>();
			
			//verifico se posso fare la firma congiunta
			String envMerge = null;
//			if( ((String)tipoFirma.getSelectedItem()).equalsIgnoreCase( SignerType.PDF.name() )){
//				envMerge = SignatureMerge.CONGIUNTA.value();
//			} else {
				envMerge = (String)modalitaFirma.getSelectedItem();
//			}
			Iterator<FileBean> i = appl.getFileBeanList().iterator();
			while (i.hasNext()) {
				FileBean bean = i.next();
				
//				boolean congiunta = bean.getTipoBusta()!=null && envMerge.equalsIgnoreCase( SignatureMerge.CONGIUNTA.value() );
//				logger.info("Il file è firmato ed è stata richiesta la modalità congiunta?  " + congiunta );
//								 
//					if( congiunta ){
//						bean.setFirmaCongiuntaRequired( true );
//						logger.info("firma congiunta verifica firme attuali attiva invio il file al WS");
//						//verifica se le firme sono valide chiamando il ws altrimenti chiedi all'utente 
//						//che occorre fare una firma verticale
//						boolean caCheckFirme = PreferenceManager.getBoolean( PreferenceKeys.PROPERTY_SIGN_CACHECKENABLED );
//						boolean crlCheckFirme = PreferenceManager.getBoolean( PreferenceKeys.PROPERTY_SIGN_CRLCHECKENABLED );
//						Date dataRif = null;
//						try {
//							String dataRifString = PreferenceManager.getString( PreferenceKeys.PROPERTY_SIGN_DATARIFERIMENTOVERIFICA );
//							logger.info( "dataRifString: " + dataRifString );
//							String dataRifFormato = PreferenceManager.getString( PreferenceKeys.PROPERTY_SIGN_DATARIFERIMENTOVERIFICA_FORMATO );
//							logger.info( "dataRifFormato: " + dataRifFormato );
//							if( dataRifFormato!=null && dataRifString!=null ){
//								SimpleDateFormat sdf = new SimpleDateFormat( dataRifFormato );
//								dataRif = sdf.parse( dataRifString );
//							}
//							
//						} catch (Exception e1) {
//							logger.info("Errore nel recupero delle proprietà " + PreferenceKeys.PROPERTY_SIGN_DATARIFERIMENTOVERIFICA, e1 );
//						}
//					
//						try{
//							FileOperationResponse risposta=WSClientUtils.verificaFirme( caCheckFirme, crlCheckFirme, dataRif, bean.getFile());
//							if(risposta!=null && !ResponseUtils.isResponseOK(risposta)){
//								//prendo la risposta alla verifica
//								if(risposta.getGenericError()!=null){
//									throw new Exception("si sono verificati errori nell'invocazione del servizio di verifica:"+risposta.getGenericError());
//								}
//								if(risposta.getFileoperationResponse()!=null && risposta.getFileoperationResponse().getFileOperationResults()!=null){
//									it.eng.fileOperation.clientws.Response.FileoperationResponse.FileOperationResults foresults=new it.eng.fileOperation.clientws.Response.FileoperationResponse.FileOperationResults();
//									foresults.getFileOperationResult().addAll(risposta.getFileoperationResponse().getFileOperationResults().getFileOperationResult());	 
//									ResponseSigVerify resp= ResponseUtils.getResponse(foresults, ResponseSigVerify.class);
//									if(resp==null){
//										JOptionPane.showMessageDialog(card.getJTabbedPane(), Messages.getMessage(MessageKeys.MSG_FIRMAMARCA_ERROR_GENERIC ),
//												Messages.getMessage(MessageKeys.MSG_FIRMAMARCA_ERROR_GENERIC ) , JOptionPane.ERROR_MESSAGE);
//										return;
//									}
//									String[] options = new String[] { Messages.getMessage( MessageKeys.PANEL_BUTTON_SI ), Messages.getMessage( MessageKeys.PANEL_BUTTON_NO ), Messages.getMessage( MessageKeys.PANEL_BUTTON_CKECKRESULT ) };
//									int choice=OptionSignDialog.createDialog( card.getPanelsign(), 
//											WSClientUtils.processHierarchy(resp.getSigVerifyResult()), 
//											Messages.getMessage( MessageKeys.MSG_FIRMAMARCA_ERROR_JOINTSIGNNOTPOSSIBLE, null, bean.getFileName() ), 
//											Messages.getMessage( MessageKeys.PANEL_BUTTON_CKECKRESULT ), 
//											JOptionPane.QUESTION_MESSAGE, JOptionPane.PLAIN_MESSAGE,null, options, options[1]);
//									if(choice==JOptionPane.OK_OPTION){
//										logger.info("Imposto la modalità firma verticale");
//										congiunta = false;
//										bean.setFirmaCongiuntaRequired( false );
//										listFilesToSign.add( bean );
//									}else{
//										logger.info("Firma annullata");
//										//return;
//										textArea.append("Firma annullata per il file " + bean.getFileName() + "\n");
//										
//									}
//								}
//							}
//						} catch (Exception e1) {
//							logger.info("Errore", e1 );
//							textArea.append("Firma non eseguita per il file " + bean.getFileName() + " a causa di errori\n");
//							i.remove();
//						}
//					} else {
						bean.setFirmaCongiuntaRequired( false );
						listFilesToSign.add( bean );
//					}
				
			}
			
			//Recupero lo slot selezionato
			int slot = jcombocardreader.getSelectedIndex();
			
			SignerThread thread = new SignerThread( this,
					appl.getHashfilebean(), listFilesToSign,
					SignerType.valueOf((String)tipoFirma.getSelectedItem()),
					(String)modalitaFirma.getSelectedItem(),
					jProgressBar, timemark);
			 
			//Avvio il thread di firma
			thread.start();
			
			
			
		} catch(Exception er){
			er.printStackTrace();
			JOptionPane.showMessageDialog(appl.getJTabbedPane(),
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
		
		appl.getJTabbedPane().setEnabled(false);
		appl.getPreferenceMenu().setEnabled(false);
	}
	
	public void signStopped(boolean esitoFirma){
		jProgressBar.setVisible(false);
		showEnabledButtons(true);
		jPin.setVisible(true);
		jcombocardreader.setEnabled(true);
		
		appl.getJTabbedPane().setEnabled(true);
		appl.getPreferenceMenu().setEnabled(true);
		
		if( esitoFirma )
			appl.closeFrame();
	}
	
	public void showMessageDialog( String message, String title, int messageType){
		JOptionPane.showMessageDialog( appl.getJTabbedPane(), message, title, messageType );
	}
	
	public void gestisciEccezione(String errorMessage){
		JOptionPane.showMessageDialog( appl.getJTabbedPane(),"Errore di firma! "+ errorMessage, "Errore", JOptionPane.ERROR_MESSAGE);
		jProgressBar.setVisible(false);
		jProgressBar.setValue(0);
		jProgressBar.setString("");
		appl.getJTabbedPane().setEnabled(true);
		appl.getPanelsign().showEnabledButtons(true);
		appl.getPanelsign().getJPin().setVisible(true);
	}
	
	public void saveOutputFiles(){
		FileOutputProvider fop = appl.getFileOutputProvider();
		logger.info("Vado a chiamare il file output " + fop);
		if(fop!=null ){
			if( listFilesToSign!=null && !listFilesToSign.isEmpty() ){
				for(FileBean bean : listFilesToSign ){
					File fileInput = bean.getFile();
					InputStream in;
					try {
						in = new FileInputStream( bean.getOutputFile() );
					} catch (FileNotFoundException e2) {
						e2.printStackTrace();
						return;
					}
					logger.info("Effettuo la chiamata");
					try {
						if( bean.getFileNameConvertito()!=null ){
							logger.info("Utilizzo il nome convertito " + bean.getFileNameConvertito() );
							fop.saveOutputFile(bean.getIdFile(), in, bean.getFileNameConvertito(), (String)getTipoFirma().getSelectedItem());
						} else if(PreferenceManager.getString( PreferenceKeys.PROPERTY_FILENAME )!=null){
							logger.info("Utilizzo il nome dalla preference " + PreferenceManager.getString( PreferenceKeys.PROPERTY_FILENAME ) );
							fop.saveOutputFile(bean.getIdFile(), in, PreferenceManager.getString( PreferenceKeys.PROPERTY_FILENAME ), (String)getTipoFirma().getSelectedItem());
						} else if( bean.getFileName()!=null ) {
							logger.info("Utilizzo il nome del file " + bean.getFileName() );
							fop.saveOutputFile(bean.getIdFile(), in, bean.getFileName(), (String)getTipoFirma().getSelectedItem());
						}else {
							logger.info("Utilizzo il nome del file " + fileInput.getName() );
							fop.saveOutputFile(bean.getIdFile(), in, fileInput.getName(), (String)getTipoFirma().getSelectedItem());
						}
						
					} catch (FileNotFoundException e1) {
						e1.printStackTrace();
						logger.info("Errore - " + e1.getMessage());
					} catch (Exception e) {
						e.printStackTrace();
					}
				} 
			} else {
				List<HashFileBean> hashfilebean = appl.getHashfilebean();
				if( hashfilebean!=null && !hashfilebean.isEmpty() ) {
					int numOK = 0;
					int numKO = 0;
					boolean esito = false;
					for(HashFileBean hash: hashfilebean ){
						try {
							esito = fop.saveOutputFile(null, null, hash.getFileName(), 
									(String)getTipoFirma().getSelectedItem(), hash.getId(), hash.getSignedBean(), hash.getFirmatario(), hash.getTempFilePath(), hash.getVersione() );
							if( esito )
								numOK++;
							else
								numKO++;
						} catch (Exception e) {
							e.printStackTrace();
							numKO++;
						}
					}
					
					//JOptionPane.showMessageDialog( card.getJTabbedPane(),Messages.getMessage( MessageKeys.MSG_OPFINISH, numOK, numKO ),"", JOptionPane.INFORMATION_MESSAGE);
					showMessageDialog( Messages.getMessage( MessageKeys.MSG_OPFINISH, numOK, numKO ),"", JOptionPane.INFORMATION_MESSAGE );
				
				}
			}
		}
	}
		
	public void showEnabledButtons(boolean visible){
		jButtonSigner.setVisible(visible);
		jButtonSignAndMark.setVisible(visible);

	}
	
	public void showConfigData(){
		if( !appl.getHashfilebean().isEmpty()){
			tipoFirma.setSelectedItem( SignerType.CAdES_BES );
			tipoFirma.setEnabled( false );
			
			//modalitaFirma.setSelectedItem( SignatureMerge.VERTICALE.value() );
			modalitaFirma.setEnabled( false );
		} else {
			modalitaFirma.setEnabled( true );
		}
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

	public JTextArea getTextArea() {
		return textArea;
	}

	public void setTextArea(JTextArea textArea) {
		this.textArea = textArea;
	}

	public SissApplication getApplication() {
		return appl;
	}
	
	
}  
