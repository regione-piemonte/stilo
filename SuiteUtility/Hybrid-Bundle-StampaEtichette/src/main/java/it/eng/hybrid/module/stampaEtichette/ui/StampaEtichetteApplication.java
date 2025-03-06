package it.eng.hybrid.module.stampaEtichette.ui;

import java.awt.Rectangle;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;

import org.apache.batik.util.XMLResourceDescriptor;
import org.apache.log4j.Logger;

import it.eng.areas.hybrid.module.util.json.JSONArray;
import it.eng.areas.hybrid.module.util.json.JSONObject;
import it.eng.hybrid.module.stampaEtichette.StampaEtichetteClientModule;
import it.eng.hybrid.module.stampaEtichette.bean.ParameterBean;
import it.eng.hybrid.module.stampaEtichette.bean.PdfPropertiesBean;
import it.eng.hybrid.module.stampaEtichette.bean.StampaEtichettaPropertiesBean;
import it.eng.hybrid.module.stampaEtichette.bean.TestoBarcodeBean;
import it.eng.hybrid.module.stampaEtichette.config.ManagerConfiguration;
import it.eng.hybrid.module.stampaEtichette.config.ParameterConfigurator;
import it.eng.hybrid.module.stampaEtichette.exception.InitException;
import it.eng.hybrid.module.stampaEtichette.util.DirectSendComLpt;
import it.eng.hybrid.module.stampaEtichette.util.EtichetteWriter;
import it.eng.hybrid.module.stampaEtichette.util.PdfWriter;
import it.eng.hybrid.module.stampaEtichette.util.PrintUtil;

public class StampaEtichetteApplication extends JFrame {

	public final static Logger logger = Logger.getLogger(StampaEtichetteApplication.class);

	public static boolean DEBUGENABLED = false;
	public static String VERSIONE = "3";

	private StampaEtichetteClientModule module;

	private JPanel pane = null;
	private JScrollPane scrolling = null;
	private JTextPane fileBox = null;

	private ParameterBean parameter;
	private StampaEtichettaPropertiesBean properties;
	private PdfPropertiesBean pdfProperties;
	
	private DirectSendComLpt comLptDrv = null;

	private boolean errore = false;

	/**
	 * @param owner
	 */
	public StampaEtichetteApplication(StampaEtichetteClientModule module, JSONArray parameters) {
		// super(owner);
		this.module = module;

		JSONObject options = parameters.getJSONObject(0);
		Iterator optionsItr = options.keys();
		List<String> optionNames = new ArrayList<String>();
		while (optionsItr.hasNext()) {
			optionNames.add((String) optionsItr.next());
		}

		Map<String, String> props = new HashMap<String, String>();
		for (int i = 0; i < options.length(); i++) {
			props.put(optionNames.get(i), options.getString(optionNames.get(i)));
			logger.info("Proprieta' " + optionNames.get(i) + "=" + options.getString(optionNames.get(i)));
		}

		try {
			initPanel();
		} catch (Exception e) {
			logger.error("Errore");
			JOptionPane.showMessageDialog(null, new String(e.getMessage()), "Attenzione", JOptionPane.ERROR_MESSAGE);
			closeFrame();
		}
		try {
			logger.info(XMLResourceDescriptor.getCSSParserClassName());
		} catch (Throwable e) {
			logger.error("Errore ", e);

			JOptionPane.showMessageDialog(null, new String(e.getMessage()), "Attenzione", JOptionPane.ERROR_MESSAGE);
			closeFrame();
		}

		// init parametri
		inizializzoParametri(props);
		// init properties
		inizializzoProperties();
	}

	public void eseguiStampa() {
		try {
			logger.info("tipo stampa: " + parameter.getTipoStampa());
			switch (parameter.getTipoStampa()) {
			case PRN:
				PrintUtil.checkPrinterSelected(parameter);
				gestisciPRN();
				break;
			case PDF:
				PrintUtil.checkPrinterSelected(parameter);
				gestisciPDF();
				break;
			case PORT:
				gestisciPort();
				break;
			default:
				logger.info("Tipo di stampa non inizializzato");
				break;
			}
			logger.info("Chiamo il closeFrame");
			closeFrame();

		} catch (Throwable e) {
			logger.error(e.getMessage(), e);
			JOptionPane.showMessageDialog(this, new String(e.getMessage()), "Attenzione", JOptionPane.ERROR_MESSAGE);
			closeFrame();
		}
	}

	public void closeFrame() {
		logger.info("AutoClosePostSign: " + parameter.getCallbackClose());
		if (parameter.getCallbackClose() != null) {
			// Setto la callback di cui il module si trova in attesa del settaggio
			module.setResult(parameter.getCallbackClose());
		}
	}

	/*
	 * Il ciclo di vita dell'applicativo � cos� rapido che termina prima di aver visualizzato la schermata informativa. Per questo motivo � stato introdotto un
	 * ciclo nel modulo che attende qualche secondo e poi controlla, tramite questo metodo, se la finestra � stata visualizzata. Nel caso la finestra sia stata
	 * visualizzata il metodo la termina e rilascia le risorse.
	 */
	public boolean checkAndClose() {
		if (!errore) {
			if (this.isVisible()) {
				logger.info("Prima del dispose");
				this.dispose();
				return true;
			} else {
				return false;
			}
		}
		return false;
	}

	/*
	 * Definizione di un metodo per definire il comportamento in caso di click sulla x della finestra di Windows
	 */
	public void forcedClose() {
		logger.info("Chiusura forzata");
		logger.info("props.get(callBackCancel): " + parameter.getCallbackCancel());
		if (parameter.getCallbackCancel() != null) {
			// Settaggio della callback in caso di cancellazione
			module.setResult(parameter.getCallbackCancel());
		} else {
			logger.info("props.get(callBackCancel) e NULL");
		}
		this.dispose();
	}

	/**
	 * inizializza l'interfaccia utente non interattiva dell'applet
	 * 
	 * @throws Exception
	 */
	private void initPanel() throws Exception {
		pane = new JPanel();
		pane.setBounds(new Rectangle(0, 0, 500, 100));
		pane.setLayout(null);
		fileBox = new JTextPane();
		fileBox.setText(fileBox.getText() + "La stampa e' in corso...");
		fileBox.setEditable(false);
		scrolling = new JScrollPane(fileBox);
		scrolling.setBounds(new Rectangle(16, 20, 312, 80));
		pane.add(scrolling);
		this.setContentPane(pane);
	}

	protected void inizializzoParametri(Map<String, String> props) {
		try {
			parameter = ParameterConfigurator.getInstance(props).getParams();
			logger.info("getCallbackCancel in inizializzoParametri: " + parameter.getCallbackCancel());

		} catch (InitException e1) {
			e1.printStackTrace();
		}
	}

	protected void inizializzoProperties() {
		try {
			ManagerConfiguration.init(parameter);
			properties = ManagerConfiguration.getProperties();
			pdfProperties = ManagerConfiguration.getPdfProperties();
			logger.info("pdfProperties " + pdfProperties);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}

	private void gestisciPDF() throws Exception {
		try {
			printPdfLabel(pdfProperties, parameter);
		} catch (Exception e) {
			logger.error("Problema di comunicazione con la stampante: " + e.getMessage(), e);
			fileBox.setText(fileBox.getText() + "\nProblema di comunicazione con la stampante: " + e.getMessage());
			errore = true;
		}
	}

	private void gestisciPRN() throws Exception {

		fileBox.setText(fileBox.getText() + "Inizio operazione stampa");
		boolean esito = EtichetteWriter.print(properties, parameter);

		if (esito)
			fileBox.setText(fileBox.getText() + "\nStampa completata.");
		else {
			fileBox.setText(fileBox.getText() + "\nStampa non completata,\n si e' verificato un errore.");
			errore = true;
		}

	}
	
	private void gestisciPort() throws Exception {
		
		fileBox.setText("Invio stampa alla porta " + parameter.getNomePortaStampaPort() + " in corso");
//		InputStream in = StampaEtichetteClientModule.class.getResourceAsStream("libraries/win32com.dll");
//	    byte[] buffer = new byte[1024];
//	    int read = -1;
//	    File temp;			
//		temp = File.createTempFile("win32com", ".dll");
//	    FileOutputStream fos = new FileOutputStream(temp);
//	    while((read = in.read(buffer)) != -1) {
//	        fos.write(buffer, 0, read);
//	    }
//	    fos.close();
//	    in.close();
//	    System.load(temp.getAbsolutePath());
	    
	    
//	    System.loadLibrary("win32com");
//	    Runtime.getRuntime().load(temp.getAbsolutePath());
//	    Runtime.getRuntime().loadLibrary("win32com");
	    
	    
//		String command = parameter.getComandoTestStampaPort();
		String command = createSerialParallelCommand();
		command = parseSerialParallelCommand(command);
        String[] commands = command.split("%NEWCMD%");
        System.out.println(command.toString().replaceAll("%NEWCMD%", "\n"));
        this.comLptDrv = new DirectSendComLpt();
        this.comLptDrv.setSelectedPortName(parameter.getNomePortaStampaPort());
        this.comLptDrv.openConnection();
        if (parameter.getComandoTestStampaPort() != null && !"".equalsIgnoreCase(parameter.getComandoTestStampaPort())) {
        	this.comLptDrv.sendCommand(parseSerialParallelCommand(parameter.getComandoTestStampaPort()));	
        } else {
	        for (int i = 0; i < commands.length; i++) {
	        	if (commands[i] != null && !"".equalsIgnoreCase(commands[i])) {
	        		this.comLptDrv.sendCommand(commands[i]);
	        	}
	        }
        }
        this.comLptDrv.closeConnection();
	}
	
	private String createSerialParallelCommand() {
		String command = "";
		if (parameter.getTesto() != null && parameter.getTesto().size() >= 1) {
			for (TestoBarcodeBean  lTestoBarcodeBean : parameter.getTesto()) {
				// Sends control sequence  'Printer initializing'
				command += "%NEWCMD%CHR$(27)@";
				// Sends control code  'Clear line buffer'
				command += "CHR$(24)";
				// Sends control sequence  'Print start position 0'
				command += "CHR$(27)$CHR$(0)";
				// Inserisco la prima riga da stampare
				String testo = lTestoBarcodeBean.getTesto();
				if (testo != null) {
					String[] paragrafi = PrintUtil.splittaTesto(testo);
					if (paragrafi != null && paragrafi.length >= 1) {
						// Posso stampare solo 2 righe
						for (int i = 0; i < paragrafi.length && i < 2; i++) {
							String paragrafo = paragrafi[i]; 
							if (paragrafo != null) {
								// Posso stampare solo in upper case
								paragrafo = paragrafo.toUpperCase();
								// Qua metto eventuali formattazioni della riga
								command += paragrafo;
								// Posso stampare al massimo 2 righe
								if (i < (paragrafi.length - 1) && i == 0) {
									// Ho un'altra riga da stampare, aggiungo una riga a capo
									command += "CHR$(10)";
								}
							}
						}
//						command += "CHR$(27):1";
						command += "CHR$(12)";
					}
				}
			}
		}
		return command;
	}
	
	private String parseSerialParallelCommand(String origString) {
	    int currStartPOS = 0;
	    int currEndPOS = -1;
	    int asciiCode = -1;
	    while ((currStartPOS = origString.indexOf("CHR$(", currStartPOS)) != -1) {
	      currEndPOS = origString.indexOf(")", currStartPOS);
	      asciiCode = Integer.parseInt(origString.substring(currStartPOS + 5, currEndPOS));
	      origString = origString.substring(0, currStartPOS) + (char)asciiCode + origString.substring(currEndPOS + 1);
	    } 
	    return origString;
	}

	public void printPdfLabel(PdfPropertiesBean pdfPropertiesBean, ParameterBean pParameterBean) throws Exception {
		logger.debug("Entro in printPdfLabel di StampaEtichetteApplication");
		String pdfFile = PdfWriter.writePdf(pdfPropertiesBean, parameter);
		logger.debug("Stringa pdfFile: " + pdfFile);
		File lFile = new File(pdfFile);
		logger.debug("Path del file generato: " + lFile.getPath());
		List<TestoBarcodeBean> listaTesti = parameter.getTesto();
		for (TestoBarcodeBean testoBarcodeBean : listaTesti) {
			logger.debug("Conter" + testoBarcodeBean.getCounter() + ": " + testoBarcodeBean.getCounter());
			logger.debug("Testo" + testoBarcodeBean.getCounter() + ": " + testoBarcodeBean.getTesto());
			logger.debug("BarCode" + testoBarcodeBean.getCounter() + ": " + testoBarcodeBean.getBarcode());
		}
		PrintUtil.print(lFile, pdfPropertiesBean, parameter);
	}

} // @jve:decl-index=0:visual-constraint="10,10"
