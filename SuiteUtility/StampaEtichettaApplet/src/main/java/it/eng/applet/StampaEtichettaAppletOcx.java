package it.eng.applet;

import java.awt.Rectangle;
import java.io.File;
import java.util.List;

import javax.swing.JApplet;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;

import org.apache.batik.util.XMLResourceDescriptor;
// import org.apache.log4j.Logger;

import it.eng.applet.configuration.ManagerConfiguration;
import it.eng.applet.configuration.ParameterBean;
import it.eng.applet.configuration.ParameterConfigurator;
import it.eng.applet.configuration.TestoBarcodeBean;
import it.eng.applet.configuration.bean.PdfPropertiesBean;
import it.eng.applet.configuration.bean.StampaEtichettaPropertiesBean;
import it.eng.applet.exception.InitException;
import it.eng.applet.util.EtichetteWriter;
import it.eng.applet.util.LogWriter;
import it.eng.applet.util.PdfWriter;
import it.eng.applet.util.PrintUtil;
import netscape.javascript.JSObject;

/**
 * Redistribuzione dell'applet di StampaEtichetta
 * 
 * @author Rametta
 *
 */
public class StampaEtichettaAppletOcx extends JApplet {

	// public final static Logger logger = Logger.getLogger(StampaEtichettaAppletOcx.class);
	private static final String logFile = System.getProperty("user.home") + File.separator + "log" + File.separator + "stampaEtichette.log";

	private static final long serialVersionUID = 1L;
	private JPanel pane = null;
	private JScrollPane scrolling = null;
	private JTextPane fileBox = null;

	private JSObject jso;

	private ParameterBean parameter;
	private StampaEtichettaPropertiesBean properties;
	private PdfPropertiesBean pdfProperties;

	public void init() {

		File fileLog = new File(logFile);
		if (fileLog.exists())
			fileLog.delete();
		LogWriter.log_name = logFile;

		try {
			initPanel();
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, new String(e.getMessage()), "Attenzione", JOptionPane.ERROR_MESSAGE);
			eseguiJavascript();
		}
		try {
			LogWriter.writeLog(XMLResourceDescriptor.getCSSParserClassName());
		} catch (Throwable e) {
			JOptionPane.showMessageDialog(null, new String(e.getMessage()), "Attenzione", JOptionPane.ERROR_MESSAGE);
			eseguiJavascript();
		}

		// init parametri
		inizializzoParametri();
		// init properties
		inizializzoProperties();

		// Recuper l'istanza del browser
		recuperoBrowser();

		try {
			PrintUtil.checkPrinterSelected(parameter);

			LogWriter.writeLog("tipo stampa " + parameter.getTipoStampa());
			switch (parameter.getTipoStampa()) {
			case PRN:
				gestisciPRN();
				break;
			case PDF:
				gestisciPDF();
				break;
			default:
				LogWriter.writeLog("Tipo di stampa non inizializzato");
				break;
			}
			eseguiJavascript();

		} catch (Throwable e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, new String(e.getMessage()), "Attenzione", JOptionPane.ERROR_MESSAGE);
			eseguiJavascript();
		}
	}

	private void eseguiJavascript() {
		LogWriter.writeLog("Eseguo " + parameter.getCallbackClose());
		if (jso != null && (!parameter.getCallbackClose().equals(""))) {
			try {
				jso.call(parameter.getCallbackClose(), new String[] {});
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
		stop();
		destroy();

		if (parameter.isAutoClose()) {
			System.exit(1);
		}
	}

	private void gestisciPRN() throws Exception {

		EtichetteWriter.print(properties, parameter);

		fileBox.setText("Stampa completata.");

	}

	@Override
	public void destroy() {

		System.exit(0);
	}

	private void gestisciPDF() throws Exception {

//		int numeroCopie = parameter.getNumeroCopie();
//
//		for (int i = 0; i < numeroCopie; i++) {
			try {
				printPdfLabel(pdfProperties, parameter);
			} catch (Exception e) {
				LogWriter.writeLog("Problema di comunicazione con la stampante: " + e.getMessage(), e);
				fileBox.setText(fileBox.getText() + "Selezionare la stampante desiderata");
			}

//		}
		// AdobeUtil.cleanAdobe(properties.getRunningExe());
	}

	protected void recuperoBrowser() {
		aggiornaLog("Inizio attivita'. Applet di stampa versione 2013.01.11");
		try {
			jso = JSObject.getWindow(this);
			aggiornaLog("Acquisito il controllo del JavaScript Object, questa applet e' in esecuzione in un browser.");
		} catch (Throwable e) {
			aggiornaLog("Esecuzione fuori dal browser... forse sta usando un visualizzatore di applet.");
		}
	}

	protected void inizializzoParametri() {
		try {
			parameter = ParameterConfigurator.getInstance(this).getParams();
		} catch (InitException e1) {
			e1.printStackTrace();
		}
	}

	protected void inizializzoProperties() {
		try {
			ManagerConfiguration.init(parameter);
			properties = ManagerConfiguration.getProperties();
			pdfProperties = ManagerConfiguration.getPdfProperties();
		} catch (Exception e1) {
			e1.printStackTrace();
		}
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
		fileBox.setText("La stampa e' in corso...");
		fileBox.setEditable(false);
		scrolling = new JScrollPane(fileBox);
		scrolling.setBounds(new Rectangle(16, 20, 468, 70));
		pane.add(scrolling);
		setContentPane(pane);
	}

	public void printPdfLabel(PdfPropertiesBean pdfPropertiesBean, ParameterBean pParameterBean) throws Exception {
		LogWriter.writeLog("Entro in printPdfLabel di StampaEtichetteApplication");
		String pdfFile = PdfWriter.writePdf(pdfPropertiesBean, parameter);
		LogWriter.writeLog("Stringa pdfFile: " + pdfFile);
		File lFile = new File(pdfFile);
		LogWriter.writeLog("Path del file generato: " + lFile.getPath());
		List<TestoBarcodeBean> listaTesti = parameter.getTesto();
		for (TestoBarcodeBean testoBarcodeBean : listaTesti) {
			LogWriter.writeLog("Conter" + testoBarcodeBean.getCounter() + ": " + testoBarcodeBean.getCounter());
			LogWriter.writeLog("Testo" + testoBarcodeBean.getCounter() + ": " + testoBarcodeBean.getTesto());
			LogWriter.writeLog("BarCode" + testoBarcodeBean.getCounter() + ": " + testoBarcodeBean.getBarcode());
		}
		PrintUtil.print(lFile, pdfPropertiesBean, parameter);
	}

	/**
	 * Funzione helper per aggionare il log di attivit� della applet. Dal momento che non ha senso loggare su un file (poich� con ogni probabilit� esso
	 * non sara accessibile in scrittura) viene effettuato il log sulla Console Java.
	 * 
	 * @param testo
	 */
	private void aggiornaLog(String testo) {
		LogWriter.writeLog("StampaApplet " + testo);
		System.out.flush();
	}

}
