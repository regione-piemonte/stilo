package it.eng.hybrid.module.printerScanner.ui;

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
import it.eng.hybrid.module.printerScanner.PrinterScannerClientModule;
import it.eng.hybrid.module.printerScanner.listener.PrinterSelectedActionListener;

public class PrinterScannerApplication extends JFrame {

	public final static Logger logger = Logger.getLogger(PrinterScannerApplication.class);

	private PrinterScannerClientModule module;

	private static String CALLBACK = "callbackSelectPrinter";
	private static String CALLBACKCANCEL = "callBackCancel";
	private static String CALLBACKCANCELPRINTERSCANNER = "callbackCancelSelectPrinter";
	private final String ALTRA_STAMPANTE = "Altra stampante";
	private final String NOME_ALTRA_STAMPANTE = "Nome stampante: ";
	private ButtonGroup mButtonGroup;
	private JButton mSelezionaButton;
	private JPanel radioPanelPrinters;
	private JPanel mJPanelApplet;
	private JPanel buttonPanel;
	private JTextField nomeAltraStampanteTextField;
	private JLabel nomeAltraStampanteLabel;
	private JPanel altraStampantePanel;
	private JRadioButton lJRadioButton;
	private boolean isAltraStampante;
	
	private JLabel labelSelezionaStampante;	
	private JPanel panelLabel;

	private String callbackToExecute;
	private String stampanteSelezionata;

	private Map<String, String> props = new HashMap<String, String>();

	public PrinterScannerApplication(PrinterScannerClientModule module, JSONArray parameters) {
		// super(owner);
		this.module = module;

		setName("Seleziona la stampante");

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

	public void initPanel(java.util.List<String> printersAvailable, PrinterSelectedActionListener lPrinterSelectedActionListener, String stampanteDefault) {
		radioPanelPrinters = new JPanel(new GridLayout(0, 1));
		mButtonGroup = new ButtonGroup();
		
		boolean stampanteSelezionata = false;
		for (Iterator<String> i$ = printersAvailable.iterator(); i$.hasNext(); radioPanelPrinters.add(lJRadioButton)) {
			String lStrPrinter = (String) i$.next();
			lJRadioButton = new JRadioButton(lStrPrinter, false);
			lJRadioButton.setName(lStrPrinter);
			lJRadioButton.setActionCommand(PrinterSelectedActionListener.Comandi.PRINTER.getValue());
			lJRadioButton.addActionListener(lPrinterSelectedActionListener);
			if ((stampanteDefault != null) && stampanteDefault.equals(lStrPrinter)) {
				lJRadioButton.setSelected(true);
				setStampanteSelezionata(stampanteDefault);
				stampanteSelezionata = true;
			}
			mButtonGroup.add(lJRadioButton);
		}
//		// Aggiungo radio per altra stampante (commentato perche' non piu' utilizzato)
//		JRadioButton lJRadioButtonAltraStampante = new JRadioButton(ALTRA_STAMPANTE, false);
//		lJRadioButtonAltraStampante.setName(ALTRA_STAMPANTE);
//		lJRadioButtonAltraStampante.setActionCommand(PrinterSelectedActionListener.Comandi.PRINTER.getValue());
//		lJRadioButtonAltraStampante.addActionListener(lPrinterSelectedActionListener);
//		radioPanelPrinters.add(lJRadioButtonAltraStampante);
//		mButtonGroup.add(lJRadioButtonAltraStampante);

		int maxLength = Math.max(findMax(printersAvailable), 50);
		radioPanelPrinters.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 3));
		mJPanelApplet = new JPanel(new BorderLayout());
		
		//Aggiunta della label "Seleziona stampante desiderata"
		labelSelezionaStampante = new JLabel();
		labelSelezionaStampante.setText("Seleziona la stampante desiderata: ");
		//Creazione del pannello per la label
		panelLabel = new JPanel(new BorderLayout());
		panelLabel.add(labelSelezionaStampante); //Aggiunta della label al pannello
		mJPanelApplet.add(panelLabel, BorderLayout.NORTH);
		
		//Aggiunta della scrollbar con i rispettivi option
		JPanel panel = new JPanel(new BorderLayout());
		
		//Pannello per avere una ScrollBar per i RadioButton
		JScrollPane scrollPane = new JScrollPane(radioPanelPrinters);
		
		panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 5));
		panel.add(scrollPane, BorderLayout.CENTER);
//		panel.add(radioPanelPrinters, BorderLayout.CENTER);
//		panel.add(creaPanelNomeAltraStampante(), BorderLayout.SOUTH);
		mJPanelApplet.add(panel, BorderLayout.CENTER);

//		if ((!stampanteSelezionata) && (stampanteDefault != null) && (!stampanteDefault.equalsIgnoreCase(""))) {
//			lJRadioButtonAltraStampante.setSelected(true);
//			nomeAltraStampanteTextField.setText(stampanteDefault);
//			setStampanteSelezionata(stampanteDefault);
//			mostraAltraStampante();
//			stampanteSelezionata = true;
//		} else {
//			nascondiAltraStampante();
//		}
		

		
		
		mJPanelApplet.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
		buttonPanel = new JPanel();
		buttonPanel.setLayout(new BoxLayout(buttonPanel, 1));
		mSelezionaButton = new JButton("Seleziona");
		if (stampanteSelezionata) {
			mSelezionaButton.setEnabled(true);
		} else {
			mSelezionaButton.setEnabled(false);
		}
		mSelezionaButton.setAlignmentX(0.5F);
		mSelezionaButton.setActionCommand(PrinterSelectedActionListener.Comandi.SELEZIONA.getValue());
		mSelezionaButton.addActionListener(lPrinterSelectedActionListener);
		buttonPanel.add(mSelezionaButton);
		mJPanelApplet.add(buttonPanel, BorderLayout.SOUTH);
		setContentPane(mJPanelApplet);
		setSize(new Dimension(maxLength * 7 + 60, printersAvailable.size() * 30 + 80));
		radioPanelPrinters.setSize(radioPanelPrinters.getSize());
		validate();
	}

	private int findMax(java.util.List<String> printersAvailable) {
		int max = 0;
		Iterator<String> i$ = printersAvailable.iterator();
		do {
			if (!i$.hasNext()) {
				break;
			}
			String lStrPrinter = (String) i$.next();
			if (lStrPrinter.length() > max) {
				max = lStrPrinter.length();
			}
		} while (true);
		return max;
	}

	public void init() {
		PrinterSelectedActionListener lPrinterSelectedActionListener = new PrinterSelectedActionListener(this);
		logger.info("Listener pulsanti radio creato");
		List<String> printersAvailable = (new PrinterScanner()).printerAvailable();
		logger.info("Inizializza il pannello");
		initPanel(printersAvailable, lPrinterSelectedActionListener, getStampanteDefault());
		recuperaCallback();
	}

	private void recuperaCallback() {
		callbackToExecute = props.get(CALLBACK);
		logger.info("callBack da eseguire: " + callbackToExecute);
	}

	private String getStampanteDefault() {
		String stampanteDefault = props.get("stampanteSelezionata");
		logger.info("Stampante predefinita: " + stampanteDefault);
		return stampanteDefault;
	}

	// Metodo Vecchio 
//	public void enableSeleziona(String selected) {
//		logger.info((new StringBuilder()).append("Selezionata la stampante ").append(selected).toString());
//		if (selected.equals(ALTRA_STAMPANTE)) {
//			mostraAltraStampante();
//			setStampanteSelezionata(nomeAltraStampanteTextField.getText());
//			mSelezionaButton.setEnabled(true);
//		} else {
//			logger.info((new StringBuilder()).append("Selezionata la stampante ").append(selected).toString());
//			nascondiAltraStampante();
//			setStampanteSelezionata(selected);
//			mSelezionaButton.setEnabled(true);
//		}
//	}
	
	
	
	public void enableSeleziona(String selected) {
		logger.info((new StringBuilder()).append("Selezionata la stampante ").append(selected).toString());
		setStampanteSelezionata(selected);
		mSelezionaButton.setEnabled(true);
	}
	

	/**
	 * Questo metodo viene invocato dal listener associato alla pressione del tasto seleziona
	 */
	public void seleziona() {
		logger.info((new StringBuilder()).append("Setto sull'applicativo la stampante ").append(stampanteSelezionata).toString());
		if (!callbackToExecute.equals("")) {
			try {
				// logger.info((new StringBuilder()).append("Eseguo la chiamata").append(callbackToExecute).toString());
				logger.info((new StringBuilder()).append("Stampante selezionata: ").append(stampanteSelezionata).toString());
				logger.info((new StringBuilder()).append("Chiamata: ").append(callbackToExecute + "(" + stampanteSelezionata + ")").toString());
				module.setCallBackFunction(callbackToExecute);
				module.setCallBackArgs(stampanteSelezionata);
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

	public String getStampanteSelezionata() {
		return stampanteSelezionata;
	}

	public void setStampanteSelezionata(String stampanteSelezionata) {
		this.stampanteSelezionata = stampanteSelezionata;
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
		callbackToExecute = props.get(CALLBACKCANCELPRINTERSCANNER);
		module.setCallBackFunction(callbackToExecute);
		module.setCallBackArgs("Uscita forzata");
		module.setCallBackCancel(callbackToExecute);
		this.dispose();
	}

	private JPanel creaPanelNomeAltraStampante() {
		nomeAltraStampanteLabel = new JLabel(NOME_ALTRA_STAMPANTE, JLabel.LEFT);
		nomeAltraStampanteLabel.setBorder(BorderFactory.createEmptyBorder(6, 6, 0, 0));
		nomeAltraStampanteTextField = new JTextField();
		// Listen for changes in the text
		nomeAltraStampanteTextField.getDocument().addDocumentListener(new DocumentListener() {

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
				setStampanteSelezionata(nomeAltraStampanteTextField.getText());
			}
		});
		altraStampantePanel = new JPanel();
		altraStampantePanel.setLayout(new BorderLayout());
		altraStampantePanel.add(nomeAltraStampanteLabel, BorderLayout.WEST);
		altraStampantePanel.add(nomeAltraStampanteTextField, BorderLayout.CENTER);
		return altraStampantePanel;
	}

	private void nascondiAltraStampante() {
		isAltraStampante = false; //Seleziono che NON si tratta della option "Altra stampante"
		nomeAltraStampanteTextField.setVisible(false);
		nomeAltraStampanteLabel.setText(" ");
		// altraStampantePanel.setVisible(false);
	}

	private void mostraAltraStampante() {
		// altraStampantePanel.setEnabled(true);
		isAltraStampante = true; //Seleziono che si tratta della option "Altra stampante"
		nomeAltraStampanteTextField.setVisible(true);
		nomeAltraStampanteLabel.setText(NOME_ALTRA_STAMPANTE);
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
	public boolean isAltraStampante()
	{
		return isAltraStampante;
	}
}
