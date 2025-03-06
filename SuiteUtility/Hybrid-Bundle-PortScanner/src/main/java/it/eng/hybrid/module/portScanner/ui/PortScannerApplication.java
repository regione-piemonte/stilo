package it.eng.hybrid.module.portScanner.ui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import org.apache.log4j.Logger;

import it.eng.areas.hybrid.module.util.json.JSONArray;
import it.eng.areas.hybrid.module.util.json.JSONException;
import it.eng.areas.hybrid.module.util.json.JSONObject;
import it.eng.hybrid.module.portScanner.PortScannerClientModule;
import it.eng.hybrid.module.portScanner.listener.PortSelectedActionListener;

public class PortScannerApplication extends JFrame {

	public final static Logger logger = Logger.getLogger(PortScannerApplication.class);

	private PortScannerClientModule module;

	private static String CALLBACK = "callbackSelectPort";
	private static String CALLBACKCANCEL = "callBackCancel";
	private static String CALLBACKCANCELPORTSCANNER = "callbackCancelSelectPort";
	private final String ALTRA_PORTA = "Altra porta";
	private final String NOME_ALTRA_PORTA = "Nome porta: ";
	private ButtonGroup mButtonGroup;
	private JButton mSelezionaButton;
	private JPanel radioPanelPorts;
	private JPanel mJPanelApplet;
	private JPanel buttonPanel;
	private JTextField nomeAltraPortaTextField;
	private JLabel nomeAltraPortaLabel;
	private JPanel altraPortaPanel;
	private JRadioButton lJRadioButton;
	private boolean isAltraPorta;
	
	private JLabel labelSelezionaPorta;	
	private JPanel panelLabel;

	private String callbackToExecute;
	private String portaSelezionata;

	private static Map<String, String> props = new HashMap<String, String>();

	public PortScannerApplication(PortScannerClientModule module, JSONArray parameters) {
		// super(owner);
		this.module = module;

		setName("Seleziona la porta della timbratrice cartacea");

		JSONObject options = parameters.getJSONObject(0);
		Iterator optionsItr = options.keys();
		List<String> optionNames = new ArrayList<String>();
		while (optionsItr.hasNext()) {
			optionNames.add((String) optionsItr.next());

		}
		for (int i = 0; i < options.length(); i++) {

			String tmpOptionNames = optionNames.get(i);
			String tmpOptionValue = null;
			try {
				tmpOptionValue = options.getString(optionNames.get(i));
			} catch (JSONException je) {
				logger.debug("Valore nullo: " + je.getMessage());
				tmpOptionValue = "";
			}

			props.put(tmpOptionNames, tmpOptionValue);
			// logger.info("Proprieta' " + optionNames.get(i) + "=" + options.getString(optionNames.get(i) == null ? "" : optionNames.get(i)));
		}

		init();

	}

	public void initPanel(java.util.List<String> portsAvailable, PortSelectedActionListener lPortSelectedActionListener, String portaDefault) {
		radioPanelPorts = new JPanel(new GridLayout(0, 1));
		mButtonGroup = new ButtonGroup();
		
		boolean portaSelezionata = false;
		for (Iterator<String> i$ = portsAvailable.iterator(); i$.hasNext(); radioPanelPorts.add(lJRadioButton)) {
			String lStrPort = (String) i$.next();
			lJRadioButton = new JRadioButton(lStrPort, false);
			lJRadioButton.setName(lStrPort);
			lJRadioButton.setActionCommand(PortSelectedActionListener.Comandi.PORT.getValue());
			lJRadioButton.addActionListener(lPortSelectedActionListener);
			if ((portaDefault != null) && portaDefault.equals(lStrPort)) {
				lJRadioButton.setSelected(true);
				setPortaSelezionata(portaDefault);
				portaSelezionata = true;
			}
			mButtonGroup.add(lJRadioButton);
		}
		
		int maxLength = Math.max(findMax(portsAvailable), 50);
		radioPanelPorts.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 3));
		mJPanelApplet = new JPanel(new BorderLayout());
		
		//Aggiunta della label "Seleziona stampante desiderata"
		labelSelezionaPorta = new JLabel();
		labelSelezionaPorta.setText("Seleziona la porta alla quale è collegata la timbratrice: ");
		//Creazione del pannello per la label
		panelLabel = new JPanel(new BorderLayout());
		panelLabel.add(labelSelezionaPorta); //Aggiunta della label al pannello
		mJPanelApplet.add(panelLabel, BorderLayout.NORTH);
		
		//Aggiunta della scrollbar con i rispettivi option
		JPanel panel = new JPanel(new BorderLayout());
		
		//Pannello per avere una ScrollBar per i RadioButton
		JScrollPane scrollPane = new JScrollPane(radioPanelPorts);
		panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 5));
		panel.add(scrollPane, BorderLayout.CENTER);
		mJPanelApplet.add(panel, BorderLayout.CENTER);		
		mJPanelApplet.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
		buttonPanel = new JPanel();
		buttonPanel.setLayout(new BoxLayout(buttonPanel, 1));
		mSelezionaButton = new JButton("Seleziona");
		if (portaSelezionata) {
			mSelezionaButton.setEnabled(true);
		} else {
			mSelezionaButton.setEnabled(false);
		}
		mSelezionaButton.setAlignmentX(0.5F);
		mSelezionaButton.setActionCommand(PortSelectedActionListener.Comandi.SELEZIONA.getValue());
		mSelezionaButton.addActionListener(lPortSelectedActionListener);
		buttonPanel.add(mSelezionaButton);
		mJPanelApplet.add(buttonPanel, BorderLayout.SOUTH);
		setContentPane(mJPanelApplet);
		setSize(new Dimension(maxLength * 7 + 60, portsAvailable.size() * 30 + 80));
		radioPanelPorts.setSize(radioPanelPorts.getSize());
		validate();
	}

	private int findMax(java.util.List<String> portsAvailable) {
		int max = 0;
		Iterator<String> i$ = portsAvailable.iterator();
		do {
			if (!i$.hasNext()) {
				break;
			}
			String lStrPort = (String) i$.next();
			if (lStrPort.length() > max) {
				max = lStrPort.length();
			}
		} while (true);
		return max;
	}

	public void init() {
		PortSelectedActionListener lPortSelectedActionListener = new PortSelectedActionListener(this);
		logger.info("Listener pulsanti radio creato");
		List<String> portsAvailable = (new PortScanner()).portAvailable();
		logger.info("Inizializza il pannello");
		initPanel(portsAvailable, lPortSelectedActionListener, getPortaDefault());
		recuperaCallback();
	}

	private void recuperaCallback() {
		callbackToExecute = props.get(CALLBACK);
		logger.info("callBack da eseguire: " + callbackToExecute);
	}

	private String getPortaDefault() {
		String portaDefault = props.get("portaSelezionata");
		logger.info("Porta predefinita: " + portaDefault);
		return portaDefault;
	}	
	
	public void enableSeleziona(String selected) {
		logger.info((new StringBuilder()).append("Selezionata la porta ").append(selected).toString());
		setPortaSelezionata(selected);
		mSelezionaButton.setEnabled(true);
	}
	

	/**
	 * Questo metodo viene invocato dal listener associato alla pressione del tasto seleziona
	 */
	public void seleziona() {
		logger.info((new StringBuilder()).append("Setto sull'applicativo la porta ").append(portaSelezionata).toString());
		if (!callbackToExecute.equals("")) {
			try {
				// logger.info((new StringBuilder()).append("Eseguo la chiamata").append(callbackToExecute).toString());
				logger.info((new StringBuilder()).append("Stampante selezionata: ").append(portaSelezionata).toString());
				logger.info((new StringBuilder()).append("Chiamata: ").append(callbackToExecute + "(" + portaSelezionata + ")").toString());
				module.setCallBackFunction(callbackToExecute);
				module.setCallBackArgs(portaSelezionata);
				// JSObject.getWindow(this).eval(callbackToExecute+"(\""+stampanteSelezionata+"\")");
				// logger.info((new StringBuilder()).append("Chiamata eseguita"));
			} catch (Exception ex) {
				logger.error((new StringBuilder()).append("Errore in esecuzione: ").append(ex.getMessage()).toString(), ex);
			}
		} else {
			logger.info((new StringBuilder()).append("JsonObject vuoto!!!"));
		}
		closeFrame();
	}

	public String getPortaSelezionata() {
		return portaSelezionata;
	}

	public void setPortaSelezionata(String portaSelezionata) {
		this.portaSelezionata = portaSelezionata;
	}

	public void closeFrame() {
		logger.info("Chiusura Frame");
		this.dispose();
	}

	/*
	 * Definizione di un metodo per definire il comportamento in caso di click sulla x della finestra di Windows
	 */
	public void forcedClose() {
		logger.info("Chiusura forzata");
		callbackToExecute = props.get(CALLBACKCANCELPORTSCANNER);
		module.setCallBackFunction(callbackToExecute);
		module.setCallBackArgs("Uscita forzata");
		module.setCallBackCancel(callbackToExecute);
		this.dispose();
	}

	private JPanel creaPanelNomeAltraStampante() {
		nomeAltraPortaLabel = new JLabel(NOME_ALTRA_PORTA, JLabel.LEFT);
		nomeAltraPortaLabel.setBorder(BorderFactory.createEmptyBorder(6, 6, 0, 0));
		nomeAltraPortaTextField = new JTextField();
		// Listen for changes in the text
		nomeAltraPortaTextField.getDocument().addDocumentListener(new DocumentListener() {

			public void changedUpdate(DocumentEvent e) {
				select();
			}

			public void removeUpdate(DocumentEvent e) {
				select();
			}

			public void insertUpdate(DocumentEvent e) {
				select();
			}

			public void select() {
				setPortaSelezionata(nomeAltraPortaTextField.getText());
			}
		});
		altraPortaPanel = new JPanel();
		altraPortaPanel.setLayout(new BorderLayout());
		altraPortaPanel.add(nomeAltraPortaLabel, BorderLayout.WEST);
		altraPortaPanel.add(nomeAltraPortaTextField, BorderLayout.CENTER);
		return altraPortaPanel;
	}

	private void nascondiAltraPorta() {
		isAltraPorta = false; //Seleziono che NON si tratta della option "Altra stampante"
		nomeAltraPortaTextField.setVisible(false);
		nomeAltraPortaLabel.setText(" ");
		// altraStampantePanel.setVisible(false);
	}

	private void mostraAltraPorta() {
		// altraStampantePanel.setEnabled(true);
		isAltraPorta = true; //Seleziono che si tratta della option "Altra stampante"
		nomeAltraPortaTextField.setVisible(true);
		nomeAltraPortaLabel.setText(NOME_ALTRA_PORTA);
	}

	public void setCallbackAndClose() {
		module.setCallBackCancel("callBackCancel");
		closeFrame();
	}
	
	
	/**
	 * Controllo il fatto di aver selezionato o meno "Altra stampante"
	 * 
	 * Il flag viene modificato nel momento in cui si visualizza la text per l'inserimento 
	 * di un'altra stampante o quando si nasconde.
	 * 
	 * @return true se ho selezionato "Altra stampante", false altrimenti
	 */
	public boolean isAltraPorta() {
		return isAltraPorta;
	}

	public static Map<String, String> getProps() {
		return props;
	}
	
}
