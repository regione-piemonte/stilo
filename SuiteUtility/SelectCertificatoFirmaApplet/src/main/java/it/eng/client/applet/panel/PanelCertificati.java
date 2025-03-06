package it.eng.client.applet.panel;

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
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.smartcardio.CardTerminal;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JProgressBar;

import it.eng.client.applet.SelectFirmatari;
import it.eng.client.applet.i18N.MessageKeys;
import it.eng.client.applet.i18N.Messages;
import it.eng.client.applet.thread.CardPresentThread;
import it.eng.client.applet.thread.CertificatiThread;
import it.eng.client.applet.thread.ConfigurationThread;
import it.eng.client.applet.util.PreferenceKeys;
import it.eng.client.applet.util.PreferenceManager;
import it.eng.common.LogWriter;
import it.eng.common.bean.Firmatario;

public class PanelCertificati extends JPanel implements ActionListener {

	private static final long serialVersionUID = 1L;

	private SelectFirmatari appl;
	private JPanel lJPanelPrincipal = null;
	private CardPresentThread cardPresentThread;
	private JPasswordField jPin = null;
	private JComboBox jcombocardreader = null;
	private JProgressBar jProgressBar = null;
	private JLabel labelOS = null;
	private JLabel labelreader = null;
	private JLabel labelcard = null;
	private JPanel bottonieraPanel;
	private JButton jButtonSigner = null;
	private JButton jButtonSignAndMark = null;
	private JButton jButtonMark = null;
	private JButton jButtonCounterSign = null;
	
	private JButton jButtonCert = null;
	private JButton jButtonSelezioneCertificato = null;
	private JComboBox jComboCertificati = null;
	private JLabel lJLabelPin = null;
	private JLabel lJLabelSelezionaCertificato = null;
	private String pin = null;
	private String firmatario = null;
	private String alias = null;
	private JLabel lJLabelRicercaInCorso = null;
	
	private final String FIRMA = "firma";
	private final String CONTROFIRMA = "controfirma";
	private final String FIRMAEMARCA = "firmaEmarca";
	private final String MARCA = "marca";
	
	/*
	 * Parametro che serve per identificare se c'è solo la firma o anche marca, etc.
	 * In questo modo si capisce se procedere direttamente con l'azione successiva o meno
	 */
	private boolean onlySignature;
	private boolean moltepliciFirmatariIndividuati = false;

	public void setThreadControl(CardPresentThread thread) {
		cardPresentThread = thread;
	}

	/**
	 * This is the default constructor
	 */
	public PanelCertificati(SelectFirmatari appl) {
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
		LogWriter.writeLog("Panel Sign intialize START");

		lJPanelPrincipal = new JPanel();
		lJPanelPrincipal.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.gridx = 0;
		c.anchor = GridBagConstraints.NORTH;

		c.fill = GridBagConstraints.BOTH;

		c.gridx = 0;
		c.weightx = 1.0;
		c.insets = new Insets(2, 10, 2, 10);
		int y = 0;

		c.gridy = y++;
		lJPanelPrincipal.add(createSOPanel(), c);

		c.gridy = y++;
		c.anchor = GridBagConstraints.CENTER;
		c.insets = new Insets(10, 10, 10, 10);
		lJPanelPrincipal.add(createMainPanel(), c);

		// c.gridy = y++;
		// c.anchor = GridBagConstraints.CENTER;
		// c.insets = new Insets(10, 10, 10, 10);
		// lJPanelPrincipal.add(createPanelloAttivita(), c);

		this.add(lJPanelPrincipal, BorderLayout.NORTH);

		// Carico i provider e controllo le carte e il sistema operativo presente
		ConfigurationThread thread = new ConfigurationThread(getJProgressBar(), (SelectFirmatari) appl, getJPin(), getJButtonCert(), this);
		thread.start();

		((SelectFirmatari) appl).getJTabbedPane().setEnabled(false);

		LogWriter.writeLog("Panel Sign intialize END");
	}

	private JPanel createSOPanel() {
		JPanel labelPanelLeftOS = new JPanel(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.anchor = GridBagConstraints.LINE_START;
		c.insets = new Insets(10, 0, 0, 0);
		labelOS = new JLabel();
		Font newLabelFont = new Font(labelOS.getFont().getName(), Font.BOLD, labelOS.getFont().getSize());
		labelOS.setFont(newLabelFont);
		labelOS.setText(Messages.getMessage(MessageKeys.PANEL_SIGN_LABEL_SO));
		c.gridx = 0;
		c.gridy = 0;
		labelPanelLeftOS.add(labelOS, c);
		return labelPanelLeftOS;
	}

	public void setSO(String osname) {
		labelOS.setText(Messages.getMessage(MessageKeys.PANEL_SIGN_LABEL_SO) + " " + osname);
	}

	private JPanel createMainPanel() {
		int y = 0;
		JPanel mainPanel = new JPanel(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.insets = new Insets(10, 10, 2, 10);
		c.gridy = y++;
		c.anchor = GridBagConstraints.CENTER;
		mainPanel.add(createEmulationPanel(), c);

		c.insets = new Insets(2, 5, 2, 5);
		c.gridy = y++;
		mainPanel.add(createPinPanel(), c);

		// Inserisco la label per la selezione del certificato
		c.gridy = y++;
		mainPanel.add(createLabelComboSelezioneCertificatoPanel(), c);

		// Inserisco la combo per la selezione del certificato
		c.gridy = y++;
		mainPanel.add(createComboSelezioneCertificatoPanel(), c);

		// Inserisco la label che indica che si sta eseguendo la ricerca dei certificati
		c.gridy = y++;
		mainPanel.add(createLabelRicercaInCorso(), c);

		c.gridy = y++;
		mainPanel.add(createCardReaderPanel(), c);

		c.gridy = y++;
		c.insets = new Insets(10, 5, 10, 5);
		mainPanel.add(createBottonieraPanel(), c);

		mainPanel.setBorder(BorderFactory.createLineBorder(Color.black));

		return mainPanel;
	}

	// private JPanel createPanelloAttivita() {
	// JPanel attivitaPanel = new JPanel(new GridBagLayout());
	// GridBagConstraints c = new GridBagConstraints();
	// c.insets = new Insets(10, 10, 2, 10);
	// c.gridy = 0;
	// c.anchor = GridBagConstraints.CENTER;
	//
	// return attivitaPanel;
	// }

	private JPanel createBottonieraPanel() {
		// pannello per la bottoniera
		bottonieraPanel = new JPanel();

		bottonieraPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		bottonieraPanel.add(getJButtonSigner(), BorderLayout.CENTER);
		jButtonSigner.setVisible(false);

		Boolean markEnabled = PreferenceManager.getBoolean(PreferenceKeys.PROPERTY_MARK_ENABLED);
		if (markEnabled) {
			bottonieraPanel.add(getJButtonSignAndMark(), BorderLayout.CENTER);
			jButtonSignAndMark.setVisible(false);
			bottonieraPanel.add(getJButtonMarca(), BorderLayout.CENTER);
			jButtonMark.setVisible(false);
		}
		Boolean counterSignEnabled = PreferenceManager.getBoolean(PreferenceKeys.PROPERTY_COUNTERSIGN_ENABLED);
		if (counterSignEnabled) {
			bottonieraPanel.add(getJButtonCounterSign(), BorderLayout.CENTER);
			jButtonCounterSign.setVisible(false);
		}
		
		bottonieraPanel.add(getJButtonCert(), BorderLayout.CENTER);
		bottonieraPanel.add(getJButtonSelezionaCertificatoMultiplo(), BorderLayout.CENTER);

		jButtonCert.setVisible(false);
		jButtonCert.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

		return bottonieraPanel;
	}

	public JButton getJButtonCert() {
		if (jButtonCert == null) {
			URL picURL = getClass().getResource("conferma.png");
			ImageIcon cup = new ImageIcon(picURL);
			jButtonCert = new JButton(cup);
			jButtonCert.setToolTipText(Messages.getMessage(MessageKeys.PANEL_BUTTON_OK));
			jButtonCert.setActionCommand("Cert");
			jButtonCert.addActionListener(this);
		}
		return jButtonCert;
	}

	/**
	 * Metodo che ritorna il pulsante per confermare la selezione del certificato presente all'interno della combobox
	 * 
	 * @return
	 */
	public JButton getJButtonSelezionaCertificatoMultiplo() {
		if (jButtonSelezioneCertificato == null) {
			URL picURL = getClass().getResource("conferma.png");
			ImageIcon cup = new ImageIcon(picURL);
			jButtonSelezioneCertificato = new JButton(cup);
			jButtonSelezioneCertificato.setToolTipText(Messages.getMessage(MessageKeys.PANEL_BUTTON_OK));
			jButtonSelezioneCertificato.setActionCommand("selezione_certificato");
			jButtonSelezioneCertificato.setVisible(false);
			jButtonSelezioneCertificato.addActionListener(this);
		}
		return jButtonSelezioneCertificato;
	}

	/**
	 * Metodo che crea la text per l'inserimento del pin
	 * 
	 * @return
	 */
	private JPanel createPinPanel() {
		JPanel lJPanelPin = new JPanel();
		lJLabelPin = new JLabel(Messages.getMessage(MessageKeys.PANEL_SIGN_LABEL_PIN));
		lJLabelPin.setLabelFor(jPin);
		lJPanelPin.add(lJLabelPin);
		lJPanelPin.add(getJPin());
		jPin.setVisible(true);
		jPin.setColumns(10);
		return lJPanelPin;
	}

	/**
	 * Metodo che crea la label per la selezione del certificato. Viene inserita all'interno di un pannello
	 * 
	 * @return
	 */
	private JPanel createLabelComboSelezioneCertificatoPanel() {

		JPanel lJPanelLabelComboSelezioneCertificati = new JPanel();

		// Label per la combo di selezione del certificato
		lJLabelSelezionaCertificato = new JLabel("Selezione del certificato");
		lJLabelSelezionaCertificato.setVisible(false);
		lJPanelLabelComboSelezioneCertificati.add(lJLabelSelezionaCertificato);

		return lJPanelLabelComboSelezioneCertificati;
	}

	/**
	 * Metodo che crea la combo per la selezione del certificato. Viene inserito all'interno di un pannello
	 * 
	 * @return
	 */
	private JPanel createComboSelezioneCertificatoPanel() {

		JPanel lJPanelComboSelezioneCertificati = new JPanel();
		// Aggiungo la combobox che permette di selezionare, se necessario, tra i vari certificati presenti

		// Creo la combo
		jComboCertificati = new JComboBox();

		// Rendo invisibile la combo (verrà abilitata solo in alcuni casi)
		jComboCertificati.setVisible(false);

		// Aggiungo la combo al pannello principale
		lJPanelComboSelezioneCertificati.add(jComboCertificati);

		return lJPanelComboSelezioneCertificati;
	}

	/**
	 * Metodo che crea la label che indica la ricerca di certificati in corso
	 * 
	 * @return
	 */
	private JPanel createLabelRicercaInCorso() {

		JPanel lJPanelLabelRicercaInCorso = new JPanel();

		// Label per la combo di selezione del certificato
		lJLabelRicercaInCorso = new JLabel("Ricerca dei certificati in corso...");
		lJLabelRicercaInCorso.setVisible(false);
		lJPanelLabelRicercaInCorso.add(lJLabelRicercaInCorso);

		return lJPanelLabelRicercaInCorso;
	}

	private JPanel createEmulationPanel() {
		JPanel labelPanelLeftEmulation = new JPanel(new GridLayout(1, 1));
		JLabel labelEmulation = new JLabel();
		labelEmulation.setText(Messages.getMessage(MessageKeys.PANEL_SIGN_LABEL_EMULATIONENABLED));
		if (PreferenceManager.getBoolean(PreferenceKeys.PROPERTY_SIGN_EMULATIONMODE))
			labelEmulation.setVisible(true);
		else
			labelEmulation.setVisible(false);
		labelPanelLeftEmulation.add(labelEmulation);
		return labelPanelLeftEmulation;
	}

	private JPanel createCardReaderPanel() {
		JPanel lJPanelCardReader = new JPanel(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.insets = new Insets(2, 0, 2, 0);
		c.gridx = 0;
		c.gridy = 0;
		labelcard = new JLabel();
		labelcard.setText(Messages.getMessage(MessageKeys.PANEL_SIGN_LABEL_CARDBUSY));
		labelcard.setLabelFor(getJcombocardreader());
		lJPanelCardReader.add(labelcard, c);
		labelcard.setVisible(false);
		c.gridx = 0;
		c.gridy = 1;
		lJPanelCardReader.add(getJcombocardreader(), c);
		jcombocardreader.setVisible(false);
		labelreader = new JLabel();
		labelreader.setText(Messages.getMessage(MessageKeys.PANEL_SIGN_LABEL_NOSMARTCARDDETECTED));
		c.gridx = 0;
		c.gridy = 2;
		lJPanelCardReader.add(labelreader, c);
		return lJPanelCardReader;
	}

	public JLabel getLabelCard() {
		return labelcard;
	}

	public void setCardPresent(boolean ispresent, boolean emulation) {
		if (ispresent || emulation) {
			jPin.setVisible(true);
			labelcard.setVisible(false);
		} else {
			jPin.setVisible(true);
			labelcard.setVisible(true);
		}

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

	public void clearReader() {
		try {
			jcombocardreader.removeAllItems();
		} catch (Exception e) {
			e.printStackTrace();
		}
		manageComponentVisibility("CLEAR_READER");
	}

	public void settingReader(List<CardTerminal> terminallist) {
		if (jcombocardreader.getItemCount() != terminallist.size()) {

			manageComponentVisibility("SETTING_READER");

			// Carico i valori
			jcombocardreader.removeAllItems();
			for (CardTerminal terminal : terminallist) {
				jcombocardreader.addItem(terminal.getName());
				jcombocardreader.setSelectedIndex(0);
			}
		}

	}

	public void endSignerUpload() {
		// JSObject.getWindow((SmartCard)card).eval("endObjectSigner();");
	}

	/**
	 * ascolta i bottoni di firma abilitati
	 */
	public void actionPerformed(ActionEvent e) {
		try {
			String command = e.getActionCommand();
			LogWriter.writeLog("command:" + command);

			if ("Cert".equals(command)) {
				// Ho premuto il pulsante presente dopo aver inserito il pin

				if (jPin.getPassword() == null || new String(jPin.getPassword()).length() == 0) {
					JOptionPane.showMessageDialog(appl.getJTabbedPane(), Messages.getMessage(MessageKeys.MSG_FIRMAMARCA_ERROR_PINNULL),
							Messages.getMessage(MessageKeys.MSG_ERROR), JOptionPane.ERROR_MESSAGE);
					return;
				}

				// Recupero lo slot selezionato
				int slot = jcombocardreader.getSelectedIndex();

				CertificatiThread thread = new CertificatiThread(this, jPin.getPassword(), jProgressBar, slot);

				// Avvio il thread di firma
				thread.start();
			} else if ("selezione_certificato".equals(command)) {
				// Premuto il pulsante per selezionare il certificato desiderato
				showMessageDialog("Certificato selezionato " + jComboCertificati.getSelectedItem().toString(), "", JOptionPane.INFORMATION_MESSAGE);

				String firmatario = jComboCertificati.getSelectedItem().toString().split("-")[0];
				String alias = jComboCertificati.getSelectedItem().toString().split("-")[1];

				showSignButton(new String(pin), firmatario, alias);
				// Ritorno alla callback i valori del certificato ottenuti
				//searchStopped(true, pin, firmatario, alias); // Ritorno indicando che si è terminata l'elaborazione
			} else if (command.equals(FIRMAEMARCA)) {
				searchStopped(true, pin, firmatario, alias, FIRMAEMARCA);
			} else if (command.equals(MARCA)) {
				searchStopped(true, pin, firmatario, alias, MARCA);
			} else if (command.equals(FIRMA)) {
				searchStopped(true, pin, firmatario, alias, FIRMA);
			} else if (command.equals(CONTROFIRMA)) {
				searchStopped(true, pin, firmatario, alias, CONTROFIRMA);
			}

		} catch (Exception er) {
			er.printStackTrace();
			JOptionPane.showMessageDialog(appl.getJTabbedPane(), Messages.getMessage(MessageKeys.MSG_FIRMAMARCA_ERROR_GENERIC) + er.getMessage(),
					Messages.getMessage(MessageKeys.MSG_ERROR), JOptionPane.ERROR_MESSAGE);
		}

	}

	public void showMessageDialog(String message, String title, int messageType) {
		JOptionPane.showMessageDialog(appl.getJTabbedPane(), message, title, messageType);
	}

	public void gestisciEccezione(String errorMessage) {
		// showManualSign();
		JOptionPane.showMessageDialog(appl.getJTabbedPane(), "Errore! " + errorMessage, "Errore", JOptionPane.ERROR_MESSAGE);

		// Gestisco la visibilita dei vari componenti
		manageComponentVisibility("EXCEPTION");

	}

	public void searchStarted() {

		// Gestisco la visibilita dei vari componenti
		manageComponentVisibility("START_SEARCH");

		// Stoppo il thread di controllo dei lettori
		cardPresentThread.stopThread();
	}

	/**
	 * Metodo che viene richiamato dal Thread che è stato avviato e che rappresenta la fine delle operazioni
	 * 
	 * @param esito
	 *            true se l'operazione è andata a buon fine, false altrimenti
	 */
	public void searchStopped(boolean esito, String pin, String firmatario, String alias, String metodo) {

		// Gestisco la visibilita dei vari componenti
		manageComponentVisibility("STOP_SEARCH");

		if (esito && pin != null && firmatario != null && alias != null) {
			// Chiamata del metodo che esegue la callback di ritorno
			appl.closeApplet(pin, firmatario, alias, metodo);

			// Visualizzo il messaggio indicando che l'acquisizione del certificato è avvenuto correttamente
			showMessageDialog(Messages.getMessage(MessageKeys.MSG_OPSUCCESS), "", JOptionPane.INFORMATION_MESSAGE);
		} else {
			// Visualizzo un messaggio di errore
			showMessageDialog(Messages.getMessage(MessageKeys.MSG_OPFAIL), "", JOptionPane.WARNING_MESSAGE);

			// Gestisco la visibilita dei vari componenti
			manageComponentVisibility("ERROR");
		}
	}

	public SelectFirmatari getAppl() {
		return appl;
	}

	public void showSignButton(String pin, String firmatario, String alias){
		this.pin = pin;
		this.firmatario = firmatario;
		this.alias = alias;
		
		onlySignature = true; //Inizializzazione ad ogni ciclo
		
		jComboCertificati.removeAllItems();

		// Popolo la combo con i dati che sono stati forniti
		jComboCertificati.addItem(firmatario + "-" + alias);
		jComboCertificati.setSelectedIndex(0);
		
		Boolean counterSignEnabled = PreferenceManager.getBoolean(PreferenceKeys.PROPERTY_COUNTERSIGN_ENABLED);
		if (counterSignEnabled) {
			onlySignature = false; //Non c'è solo la firma
			
			jButtonCounterSign.setVisible(true);
		}
		Boolean markEnabled = PreferenceManager.getBoolean(PreferenceKeys.PROPERTY_MARK_ENABLED);
		if (markEnabled) {
			onlySignature = false; //Non c'è solo la firma
			
			jButtonSignAndMark.setVisible(true);
			jButtonMark.setVisible(true);
		}
		jButtonSigner.setVisible(true);
		
		jButtonCert.setVisible(false);
		
		manageComponentVisibility("SHOW_BUTTON");

		/*
		 * Se c'è la possibilità di eseguire solamente la firma e non marca o altro allora procedo direttamente
		 * con l'operazione senza aspettare l'input dell'utente
		 */
		if(onlySignature && !moltepliciFirmatariIndividuati){
			searchStopped(true, pin, firmatario, alias, FIRMA);
		}
		
	}
	
	public void addComboCertificati(String pin, ArrayList<Firmatario> firmatari) {
		this.pin = pin;

		// Visualizzo il messaggio indicando che ci sono più certificati e che bisogna selezionarne uno
		showMessageDialog(Messages.getMessage(MessageKeys.MSG_MULTIPLI_CERTIFICATI_PRESENTI), "", JOptionPane.INFORMATION_MESSAGE);

		// Rimozione di eventuali altri item presenti
		jComboCertificati.removeAllItems();
		
		moltepliciFirmatariIndividuati = true;

		// Popolo la combo con i dati che sono stati forniti
		for (Firmatario firmatario : firmatari) {

			jComboCertificati.addItem(firmatario.getFirmatario() + "-" + firmatario.getAlias());
		}
		jComboCertificati.setSelectedIndex(0);

		// Gestisco la visibilità dei vari componenti
		manageComponentVisibility("ADD_COMBO_CERTIFICATI");

	}

	/**
	 * In base alla situazione in cui ci si trova si valuta cosa visualizzare come componenti e cosa no.
	 * 
	 * @param situation
	 *            situazione che si deve analizzare
	 */
	private void manageComponentVisibility(String situation) {
		if ("START_SEARCH".equals(situation)) {
			// Allora ci si trova nella situazione in cui si sta iniziando una ricerca

			// Non visualizzo la text per l'inserimento del pin
			if (jPin != null) {
				jPin.setVisible(false);
			}

			// Non visualizzo la label del pin
			if (lJLabelPin != null) {
				lJLabelPin.setVisible(false);
			}

			// Visualizzo la label che indica la ricerca in corso
			if (lJLabelRicercaInCorso != null) {
				lJLabelRicercaInCorso.setVisible(true);
			}

			// Disabilito il tabbed pane
			if (appl.getJTabbedPane() != null) {
				appl.getJTabbedPane().setEnabled(false);
			}

			// Disabilito i pulsanti del menu superiore
			if (appl.getPreferenceMenu() != null) {
				appl.getPreferenceMenu().setEnabled(false);
			}

			if (appl.getInvioLogMenu() != null) {
				appl.getInvioLogMenu().setEnabled(false);
			}

			// Disabilito il pulsante utilizzato per avviare la ricerca dei certificati
			if (jButtonCert != null) {
				jButtonCert.setVisible(false);
			}

			// Non visualizzo la combo della selezione del reader
			if (jcombocardreader != null) {
				jcombocardreader.setVisible(false);
			}
		} else if("SHOW_BUTTON".equals(situation)) {
			if (jPin != null) {
				jPin.setVisible(true);
				jPin.setEditable(false);
			}
			if (jButtonSelezioneCertificato != null) {
				jButtonSelezioneCertificato.setVisible(false);
			}

			if (jComboCertificati != null) {
				jComboCertificati.setVisible(true);
			}

			if (lJLabelSelezionaCertificato != null) {
				lJLabelSelezionaCertificato.setVisible(false);
			}

			if (appl.getJTabbedPane() != null) {
				appl.getJTabbedPane().setEnabled(true); // Riabilito il tabbed pane
			}

			// Riabilito il menu superiore
			if (appl.getPreferenceMenu() != null) {
				appl.getPreferenceMenu().setEnabled(true);
			}

			if (appl.getInvioLogMenu() != null) {
				appl.getInvioLogMenu().setEnabled(true);
			}

			// Non visualizzo la label che indica la ricerca in corso
			if (lJLabelRicercaInCorso != null) {
				lJLabelRicercaInCorso.setVisible(false);
			}
		} else if ("ADD_COMBO_CERTIFICATI".equals(situation)) {
			// Quando deve essere visualizzata la combo per la presenza di vari certificati

			// Abilito il pulsante per confermare la scelta del certificato effettuata
			if (jButtonSelezioneCertificato != null) {
				jButtonSelezioneCertificato.setVisible(true);
			}

			/*
			 * Abilito la label e la combo relativa alla scelta del firmatario nel caso di certificati multipli
			 */
			if (jComboCertificati != null) {
				jComboCertificati.setVisible(true);
			}

			if (lJLabelSelezionaCertificato != null) {
				lJLabelSelezionaCertificato.setVisible(true);
			}

			if (appl.getJTabbedPane() != null) {
				appl.getJTabbedPane().setEnabled(true); // Riabilito il tabbed pane
			}

			// Riabilito il menu superiore
			if (appl.getPreferenceMenu() != null) {
				appl.getPreferenceMenu().setEnabled(true);
			}

			if (appl.getInvioLogMenu() != null) {
				appl.getInvioLogMenu().setEnabled(true);
			}

			// Non visualizzo la label che indica la ricerca in corso
			if (lJLabelRicercaInCorso != null) {
				lJLabelRicercaInCorso.setVisible(false);
			}
		} else if ("STOP_SEARCH".equals(situation)) {
			// Ho terminato la ricerca

			// Non visualizzo la label che indica la ricerca in corso
			if (lJLabelRicercaInCorso != null) {
				lJLabelRicercaInCorso.setVisible(false);
			}

			// Riabilito il tabbed pane e i menu superiori
			if (appl.getJTabbedPane() != null) {
				appl.getJTabbedPane().setEnabled(true);
			}

			if (appl.getPreferenceMenu() != null) {
				appl.getPreferenceMenu().setEnabled(true);
			}

			if (appl.getInvioLogMenu() != null) {
				appl.getInvioLogMenu().setEnabled(true);
			}

			// Non visualizzo la label che indica la ricerca in corso
			if (lJLabelRicercaInCorso != null) {
				lJLabelRicercaInCorso.setVisible(false);
			}
		} else if ("ERROR".equals(situation)) {
			// Nel caso ci sia stato un errore abilito nuovamente gli elementi per l'inserimento delle informazioni

			// Abilito il pulsante per eseguire una nuova ricerca di certificati
			if (jButtonCert != null) {
				jButtonCert.setVisible(true);
			}

			// Riabilito la text per l'inserimento del pin e la combo per la selezione del reader
			if (jPin != null) {
				jPin.setVisible(true);
			}

			if (jcombocardreader != null) {
				jcombocardreader.setEnabled(true);
			}

		} else if ("EXCEPTION".equals(situation)) {

			// Nel caso sia stata generata un'eccezione, come ad esempio quando è stato inserito un pin non corretto

			if (appl.getJTabbedPane() != null) {
				appl.getJTabbedPane().setEnabled(true); // Visualizzo il tabbed pane
			}

			if (jPin != null) {
				jPin.setVisible(true); // Riabilito la text del pin
			}

			if (lJLabelPin != null) {
				lJLabelPin.setVisible(true); // Riabilito la label del pin
			}

			if (jcombocardreader != null) {
				jcombocardreader.setVisible(true); // Riabilito la combo della selezione del reader
			}

			// Abilito il pulsante per eseguire una nuova ricerca di certificati
			if (jButtonCert != null) {
				jButtonCert.setVisible(true);
			}

			// Riabilito il menu superiore
			if (appl.getPreferenceMenu() != null) {
				appl.getPreferenceMenu().setEnabled(true);
			}
			if (appl.getInvioLogMenu() != null) {
				appl.getInvioLogMenu().setEnabled(true);
			}

			// Non visualizzo la label che indica la ricerca in corso
			if (lJLabelRicercaInCorso != null) {
				lJLabelRicercaInCorso.setVisible(false);
			}

		} else if ("CLEAR_READER".equals(situation)) {
			// Nascondo la combo e visualizzo il messaggio di nessun lettore presente
			if (labelreader != null) {
				labelreader.setVisible(true);
			}

			if (jcombocardreader != null) {
				jcombocardreader.setVisible(false);
			}
		} else if ("SETTING_READER".equals(situation)) {
			if (labelreader != null) {
				labelreader.setVisible(false);
			}

			if (jcombocardreader != null) {
				jcombocardreader.setVisible(true);
			}
		}

	}
	
	public JButton getJButtonSigner() {
		if (jButtonSigner == null) {
			URL picURL = getClass().getResource("buttonsigner.png");
			ImageIcon cup = new ImageIcon(picURL);
			jButtonSigner = new JButton(cup);
			jButtonSigner.setToolTipText(Messages.getMessage(MessageKeys.PANEL_SIGN_BUTTON_SIGN));
			jButtonSigner.setActionCommand(FIRMA);
			jButtonSigner.addActionListener(this);
		}
		return jButtonSigner;
	}

	public JButton getJButtonSignAndMark() {
		if (jButtonSignAndMark == null) {
			URL picURL = getClass().getResource("buttonSignEMarca.png");
			ImageIcon cup = new ImageIcon(picURL);
			jButtonSignAndMark = new JButton(cup);
			jButtonSignAndMark.setToolTipText(Messages.getMessage(MessageKeys.PANEL_SIGN_BUTTON_SIGNMARK));
			jButtonSignAndMark.setActionCommand(FIRMAEMARCA);
			jButtonSignAndMark.addActionListener(this);
		}
		return jButtonSignAndMark;
	}

	public JButton getJButtonMarca() {
		if (jButtonMark == null) {
			URL picURL = getClass().getResource("buttonMarca.png");
			ImageIcon cup = new ImageIcon(picURL);
			jButtonMark = new JButton(cup);
			jButtonMark.setToolTipText(Messages.getMessage(MessageKeys.PANEL_SIGN_BUTTON_MARK));
			jButtonMark.setActionCommand(MARCA);
			jButtonMark.addActionListener(this);
		}
		return jButtonMark;
	}

	public JButton getJButtonCounterSign() {
		if (jButtonCounterSign == null) {
			URL picURL = getClass().getResource("buttoncountersigner.png");
			ImageIcon cup = new ImageIcon(picURL);
			jButtonCounterSign = new JButton(cup);
			jButtonCounterSign.setToolTipText(Messages.getMessage(MessageKeys.PANEL_SIGN_BUTTON_COUNTERSIGN));
			jButtonCounterSign.setActionCommand(CONTROFIRMA);
			jButtonCounterSign.addActionListener(this);
		}
		return jButtonCounterSign;
	}

}
