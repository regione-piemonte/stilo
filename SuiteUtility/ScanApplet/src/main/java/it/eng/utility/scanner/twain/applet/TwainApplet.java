package it.eng.utility.scanner.twain.applet;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Panel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.awt.image.renderable.ParameterBlock;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Properties;
import java.util.Random;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;
import java.util.Vector;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.stream.ImageOutputStream;
import javax.media.jai.JAI;
import javax.media.jai.NullOpImage;
import javax.media.jai.OpImage;
import javax.media.jai.PlanarImage;
import javax.media.jai.RenderedOp;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JApplet;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.Border;

import org.apache.http.HttpEntity;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.CloseableHttpClient;

import com.itextpdf.text.Document;
import com.itextpdf.text.Image;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfAConformanceLevel;
import com.itextpdf.text.pdf.PdfAWriter;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfWriter;
import com.nilo.plaf.nimrod.NimRODLookAndFeel;
import com.nilo.plaf.nimrod.NimRODTheme;
import com.sun.media.jai.codec.FileSeekableStream;
import com.sun.media.jai.codec.ImageCodec;
import com.sun.media.jai.codec.ImageDecoder;
import com.sun.media.jai.codec.ImageEncoder;
import com.sun.media.jai.codec.SeekableStream;
import com.sun.media.jai.codec.TIFFEncodeParam;

import it.eng.proxyselector.frame.ProxyFrame;
import it.eng.proxyselector.http.ProxyDefaultHttpClient;
import it.eng.utility.scanner.twain.LogWriter;
import it.eng.utility.scanner.twain.PreferenceKeys;
import it.eng.utility.scanner.twain.PreferenceManager;
import it.eng.utility.scanner.twain.applet.enums.TipoScansioneEnum;
import it.eng.utility.scanner.twain.applet.panel.ImagePreviewFrame;
import it.eng.utility.scanner.twain.applet.panel.PanelLog;
import it.eng.utility.scanner.twain.applet.panel.PanelPreferenceLog;
import it.eng.utility.scanner.twain.applet.panel.PanelResolution;
import it.eng.utility.scanner.twain.message.MessageKeys;
import it.eng.utility.scanner.twain.message.Messages;
import netscape.javascript.JSObject;
import uk.co.mmscomputing.device.scanner.Scanner;
import uk.co.mmscomputing.device.scanner.ScannerDevice;
import uk.co.mmscomputing.device.scanner.ScannerIOMetadata;
import uk.co.mmscomputing.device.scanner.ScannerListener;
import uk.co.mmscomputing.device.twain.TwainBufferedImage;
import uk.co.mmscomputing.device.twain.TwainConstants;
import uk.co.mmscomputing.device.twain.TwainIOMetadata;
import uk.co.mmscomputing.device.twain.TwainImageInfo;
import uk.co.mmscomputing.device.twain.TwainSource;

public class TwainApplet extends JApplet implements ActionListener, ScannerListener, Constants {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1572995788752683005L;

	private String filenameCompl;

	private Properties scanningInfo;
	private String fileNameTemp;

	private Scanner scanner;

	private int numImage = 0;

	private String servletUpload = "";
	private String testUrl;

	private String contextPath = "";
	private String callBack = "";
	private String idSelected = "";

	private boolean started = false;
	private boolean imageSaved = true;

	private static final String logFile = System.getProperty("user.home") + File.separator + "log" + File.separator + "scanApplet.log";

	public static boolean DEBUGENABLED = false;

	private String tempFolder = System.getProperty("user.home");

	private Vector imgX = null;
	private Vector imgY = null;

	public String prefix_nomefile = "scans_temp_";
	public int len_prefix_nomefile = 11;

	public int max_connectiont_imeout = 0; // Disabilitato

	public static NimRODTheme nt;
	public static NimRODLookAndFeel nf;

	private JScrollPane jScrollPane = null;
	private JPanel jContentPane = null;
	private JPanel menuPanel = null;
	private JPanel mainPanel = null;

	private final String SELECTSCANNER = "selectScanner";
	private final String STARTSCANNER = "startScanner";
	private final String STOPSCANNER = "stopScanner";
	private final String DELETETEMP = "deleteTemp";
	private final String SHOWTEMP = "showTemp";
	private final String SELECTFOLDER = "selectFolder";
	private final String SELECTRESOLUTION = "selectResolution";

	private JMenu preferenceMenu;
	private JMenu invioLogMenu;
	private JTextArea ta;
	private JScrollPane sp;
	private JLabel tempFolderLabel;
	private JProgressBar aJProgressBar;
	private JButton acquireButton;
	private JButton closeSaveButton;
	private JButton deleteTempButton;
	private JButton showTempButton;

	private String callBackAskForClose;
	private String callbackRichiestaChiusura;
	private String callbackAnnullaChiusura;

	private String risoluzioneDefault;
	private boolean abilitaPDFA;
	private boolean abilitaPannImpEmbedded = false;

	private boolean errorOnUpload;

	public void init() {

		nt = new NimRODTheme("Default.theme");
		nf = new NimRODLookAndFeel();
		NimRODLookAndFeel.setCurrentTheme(nt);
		try {
			UIManager.setLookAndFeel(nf);
		} catch (UnsupportedLookAndFeelException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		Messages.setBundle(ResourceBundle.getBundle("messages"));

		PreferenceManager.initConfig(this);

		boolean logEnabled = PreferenceManager.enabled(PreferenceKeys.PROPERTY_LOGENABLED);

		if (logEnabled) {
			boolean logFileEnabled = false;
			try {
				logFileEnabled = PreferenceManager.getBoolean(PreferenceKeys.PROPERTY_LOGFILEENABLED);
				System.out.println("ProprietÃ  " + PreferenceKeys.PROPERTY_LOGFILEENABLED + ": " + logFileEnabled);
			} catch (Exception e) {
				LogWriter.writeLog("Errore nel recupero della proprietÃ  " + PreferenceKeys.PROPERTY_LOGFILEENABLED);
			}
			boolean logArrayEnabled = false;
			try {
				logArrayEnabled = PreferenceManager.getBoolean(PreferenceKeys.PROPERTY_LOGARRAYENABLED);
				System.out.println("ProprietÃ  " + PreferenceKeys.PROPERTY_LOGARRAYENABLED + ": " + logArrayEnabled);
			} catch (Exception e) {
				LogWriter.writeLog("Errore nel recupero della proprietÃ  " + PreferenceKeys.PROPERTY_LOGARRAYENABLED);
			}
			if (logFileEnabled) {
				File fileLog = new File(logFile);
				if (fileLog.exists())
					fileLog.delete();
				LogWriter.log_name = logFile;
			}
			if (logArrayEnabled) {
				LogWriter.logArray = new ArrayList<String>();
			}
			LogWriter.initLog();
		}

		try {
			DEBUGENABLED = PreferenceManager.getBoolean(PreferenceKeys.PROPERTY_DEBUGENABLED);
			System.out.println("ProprietÃ  " + PreferenceKeys.PROPERTY_DEBUGENABLED + ": " + DEBUGENABLED);
		} catch (Exception e) {
			LogWriter.writeLog("Errore nel recupero della proprietÃ  " + PreferenceKeys.PROPERTY_DEBUGENABLED);
		}

		// Parametri in ingresso
		servletUpload = getParameter("servletUpload") == null ? "" : getParameter("servletUpload");
		contextPath = getParameter("contextPath") == null ? "" : getParameter("contextPath");
		testUrl = getParameter("testUrl") == null ? "" : getParameter("testUrl");
		callBack = getParameter("callBack") == null ? "" : getParameter("callBack");
		idSelected = getParameter("idSelected") == null ? "" : getParameter("idSelected");
		callBackAskForClose = getParameter("callBackAskForClose") == null ? "" : getParameter("callBackAskForClose");
		callbackRichiestaChiusura = getParameter("callbackRichiestaChiusura") == null ? "" : getParameter("callbackRichiestaChiusura");
		callbackAnnullaChiusura = getParameter("callbackAnnullaChiusura") == null ? "" : getParameter("callbackAnnullaChiusura");
		risoluzioneDefault = getParameter("risoluzioneScanner") == null ? "200" : getParameter("risoluzioneScanner");
		abilitaPDFA = getParameter("abilitaPDFA") == null ? false : Boolean.parseBoolean(getParameter("abilitaPDFA"));
		LogWriter.writeLog("PDFA abilitato: " + abilitaPDFA, true);
		abilitaPannImpEmbedded = getParameter("abilitaPannImpEmbedded") == null ? false : Boolean.parseBoolean(getParameter("abilitaPannImpEmbedded"));
		LogWriter.writeLog("Pannello impostazioni embedded abilitato: " + abilitaPannImpEmbedded, true);

		LogWriter.writeLog("Abilitazione PDFA: " + abilitaPDFA);
		LogWriter.writeLog("Risoluzione default: " + risoluzioneDefault);
		try {
			PreferenceManager.saveProp(PreferenceKeys.PROPERTY_SCANNER_RESOLUTION_DEFAULT, risoluzioneDefault);
			PreferenceManager.reinitConfig();
			LogWriter.writeLog("Risoluzione di default impostata a " + risoluzioneDefault + " dpi");
		} catch (Exception e) {
			e.printStackTrace();
		}

		LogWriter.writeLog("callbackRichiestaChiusura vale " + callbackRichiestaChiusura);
		LogWriter.writeLog("callbackAnnullaChiusura vale " + callbackAnnullaChiusura);
		String tempFolder = PreferenceManager.getString(PreferenceKeys.PROPERTY_TEMPFOLDER);
		if (tempFolder == null || tempFolder.trim().equals("")) {
			tempFolder = System.getProperty("user.home");
		}
		LogWriter.writeLog("ProprietÃ  " + PreferenceKeys.PROPERTY_TEMPFOLDER + ": " + tempFolder);

		fileNameTemp = tempFolder + File.separator + prefix_nomefile + getRandomFileNameNoExt();

		scanningInfo = new Properties();

		imgX = new Vector();
		imgY = new Vector();

		// Verifica sulla correttezza del contextPath passato
		try {
			new URL(contextPath);
		} catch (MalformedURLException e) {
			LogWriter.writeLog("Il parametro 'contextPath' " + contextPath + " non Ã¨ corretto!", e);
		}

		int mainPanelWidth = PreferenceManager.getInt(PreferenceKeys.MAINPANEL_WIDTH, 400);
		int mainPanelHeight = PreferenceManager.getInt(PreferenceKeys.MAINPANEL_HEIGHT, 400);

		/*
		 * Nel caso dell'aeroporto di Roma non si devono prelevare i due valori delle dimensioni dal PreferenceManager ma si devono utilizzare dei valori fissi.
		 * Le due righe precedenti devono essere commentate e devono essere utilizzati i seguenti valori mainPanelWidth = 490; mainPanelHeight = 240; Dev'essere
		 * modificato anche il numero di righe presenti nella textarea
		 */
		LogWriter.writeLog("mainPanelWidth=" + mainPanelWidth + " mainPanelHeight=" + mainPanelHeight);
		this.setSize(mainPanelWidth, mainPanelHeight);

		setContentPane(getJContentPane());
		tempFolderLabel.setText(Messages.getMessage(MessageKeys.MSG_TEMPFOLDER) + ": " + tempFolder);

		pingIsClosed();
		// Se c'e' uno scanner di default, parte automaticamente
		try {
			scanner = Scanner.getDevice();

			if (scanner != null) {
				scanner.addListener(this);
				LogWriter.writeLog("Avvio scansione in corso, attendere.....", ta);
				scanner.acquire();
			}
		} catch (Exception ex) {
			LogWriter.writeLog("ERRORE - " + ex.getMessage(), true);
			ex.printStackTrace();
		}
	}

	private JScrollPane getJContentPane() {
		if (jContentPane == null) {
			GridBagLayout gridLayout = new GridBagLayout();
			GridBagConstraints c = new GridBagConstraints();

			jContentPane = new JPanel();
			jContentPane.setLayout(gridLayout);
			jScrollPane = new JScrollPane(jContentPane);

			c.gridy = 0;
			c.gridx = 0;
			c.anchor = GridBagConstraints.LINE_START;
			c.gridx = 0;
			c.weightx = 1.0;
			c.insets = new Insets(2, 10, 2, 10);
			JPanel menu = getMenuPanel();
			jContentPane.add(menu, c);
			c.gridy = 1;
			c.insets = new Insets(2, 10, 2, 10);
			c.anchor = GridBagConstraints.LINE_START;
			JPanel main = getMainPanel();
			jContentPane.add(main, c);
		}
		return jScrollPane;
	}

	private JPanel getMenuPanel() {
		if (menuPanel == null) {
			menuPanel = new JPanel();
			JMenuBar menuBar = new JMenuBar();
			preferenceMenu = new JMenu(Messages.getMessage(MessageKeys.MENU_PREFERENCE_TITLE));

			JMenuItem selezionaScanner = new JMenuItem(Messages.getMessage(MessageKeys.MENU_PREFERENCE_SELECTSCANNER_TITLE));
			selezionaScanner.setToolTipText(Messages.getMessage(MessageKeys.MENU_PREFERENCE_SELECTSCANNER_TOOLTIP));
			selezionaScanner.setActionCommand(SELECTSCANNER);
			selezionaScanner.addActionListener(this);
			preferenceMenu.add(selezionaScanner);

			if (abilitaPannImpEmbedded) {
				JMenuItem selezionaRisoluzione = new JMenuItem(Messages.getMessage(MessageKeys.MENU_PREFERENCE_SELECTRESOLUTION_TITLE));
				selezionaRisoluzione.setToolTipText(Messages.getMessage(MessageKeys.MENU_PREFERENCE_SELECTRESOLUTION_TOOLTIP));
				selezionaRisoluzione.setActionCommand(SELECTRESOLUTION);
				selezionaRisoluzione.addActionListener(this);
				preferenceMenu.add(selezionaRisoluzione);
			}

			JMenuItem tempFolder = new JMenuItem(Messages.getMessage(MessageKeys.MENU_PREFERENCE_SELECTFOLDER_TITLE));
			tempFolder.setActionCommand(SELECTFOLDER);
			tempFolder.setToolTipText(Messages.getMessage(MessageKeys.MENU_PREFERENCE_SELECTFOLDER_TOOLTIP));
			tempFolder.addActionListener(this);
			preferenceMenu.add(tempFolder);

			JMenuItem log = new JMenuItem(Messages.getMessage(MessageKeys.MENU_PREFERENCE_LOG_TITLE));
			log.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					int preferenceInvioLogPanelWidth = PreferenceManager.getInt(PreferenceKeys.PREFERENCEINVIOLOGPANEL_WIDTH, 400);
					int preferenceInvioLogPanelHeight = PreferenceManager.getInt(PreferenceKeys.PREFERENCEINVIOLOGPANEL_HEIGHT, 400);
					new PanelPreferenceLog(preferenceInvioLogPanelWidth, preferenceInvioLogPanelHeight).show(null);
				}
			});
			preferenceMenu.add(log);

			JMenuItem rete = new JMenuItem(Messages.getMessage(MessageKeys.MENU_PREFERENCE_RETE_TITLE));
			rete.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					new ProxyFrame(testUrl).setVisible(true);
				}
			});
			preferenceMenu.add(rete);
			menuBar.add(preferenceMenu);

			invioLogMenu = new JMenu(Messages.getMessage(MessageKeys.MENU_INVIOLOG_TITLE));
			JMenuItem invioLog = new JMenuItem(Messages.getMessage(MessageKeys.MENU_INVIOLOG_TITLE));
			invioLog.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					int invioLogPanelWidth = PreferenceManager.getInt(PreferenceKeys.INVIOLOGPANEL_WIDTH, 400);
					int invioLogPanelHeight = PreferenceManager.getInt(PreferenceKeys.INVIOLOGPANEL_HEIGHT, 400);
					new PanelLog(invioLogPanelWidth, invioLogPanelHeight).show(null);
				}
			});
			invioLogMenu.add(invioLog);
			menuBar.add(invioLogMenu);

			menuPanel.add(menuBar);
		}
		return menuPanel;
	}

	private JPanel getMainPanel() {
		if (mainPanel == null) {
			mainPanel = new JPanel(new GridBagLayout());
			GridBagConstraints c = new GridBagConstraints();
			c.gridx = 0;
			c.anchor = GridBagConstraints.CENTER;
			c.insets = new Insets(5, 0, 0, 0);
			c.gridy = 0;
			mainPanel.add(getButtonPanel(), c);

			c.anchor = GridBagConstraints.CENTER;
			c.insets = new Insets(10, 10, 0, 0);
			c.gridy = 1;
			aJProgressBar = new JProgressBar();
			aJProgressBar.setStringPainted(false);
			aJProgressBar.setIndeterminate(true);
			aJProgressBar.setVisible(false);
			aJProgressBar.setOpaque(true);
			aJProgressBar.setForeground(Color.GREEN);
			Border border = BorderFactory.createTitledBorder("Salvataggio in corso...");
			aJProgressBar.setBorder(border);
			mainPanel.add(aJProgressBar, c);

			c.gridy = 2;
			// Nel caso dell'aeroporto di Roma il valore di righe nella textarea dev'essere 6
			ta = new JTextArea(11, 40);
			ta.setForeground(Color.BLACK);
			ta.setBackground(Color.WHITE);
			sp = new JScrollPane(ta);
			mainPanel.add(sp, c);

			c.anchor = GridBagConstraints.LAST_LINE_START;
			c.insets = new Insets(10, 10, 0, 0);
			c.gridy = 3;
			tempFolderLabel = new JLabel();
			mainPanel.add(tempFolderLabel, c);

		}
		return mainPanel;
	}

	private Panel getButtonPanel() {
		Panel buttonPanel = new Panel();

		URL imageScannerURL = getClass().getResource("scanner.png");
		ImageIcon imageScanner = new ImageIcon(imageScannerURL);
		acquireButton = new JButton(imageScanner);
		acquireButton.addActionListener(this);
		acquireButton.setActionCommand(STARTSCANNER);
		acquireButton.setToolTipText(Messages.getMessage(MessageKeys.BUTTON_STARTSCANNER_TOOLTIP));
		buttonPanel.add(acquireButton);

		URL imageStopScannerURL = getClass().getResource("ok.png");
		ImageIcon imageStopScanner = new ImageIcon(imageStopScannerURL);
		closeSaveButton = new JButton(imageStopScanner);
		closeSaveButton.addActionListener(this);
		closeSaveButton.setActionCommand(STOPSCANNER);
		closeSaveButton.setEnabled(false);
		closeSaveButton.setToolTipText(Messages.getMessage(MessageKeys.BUTTON_STOPSCANNER_TOOLTIP));
		buttonPanel.add(closeSaveButton);

		URL imageDeleteTempURL = getClass().getResource("close.png");
		ImageIcon imageDeleteTemp = new ImageIcon(imageDeleteTempURL);
		deleteTempButton = new JButton(imageDeleteTemp);
		deleteTempButton.addActionListener(this);
		deleteTempButton.setActionCommand(DELETETEMP);
		deleteTempButton.setEnabled(false);
		deleteTempButton.setToolTipText(Messages.getMessage(MessageKeys.BUTTON_DELETETEMP_TOOLTIP));
		buttonPanel.add(deleteTempButton);

		URL imageShowTempURL = getClass().getResource("show.png");
		ImageIcon imageShowTemp = new ImageIcon(imageShowTempURL);
		java.awt.Image image = imageShowTemp.getImage(); // transform it
		java.awt.Image newimg = image.getScaledInstance(32, 32, java.awt.Image.SCALE_SMOOTH); // scale it the smooth way
		imageShowTemp = new ImageIcon(newimg);
		showTempButton = new JButton(imageShowTemp);
		showTempButton.addActionListener(this);
		showTempButton.setActionCommand(SHOWTEMP);
		showTempButton.setEnabled(false);
		showTempButton.setToolTipText(Messages.getMessage(MessageKeys.BUTTON_SHOWTEMP_TOOLTIP));
		buttonPanel.add(showTempButton);

		return buttonPanel;
	}

	@Override
	public void actionPerformed(ActionEvent evt) {

		String command = evt.getActionCommand();

		if (command.equalsIgnoreCase(STARTSCANNER)) {
			LogWriter.writeLog("Avvio scansione in corso, attendere.....", ta);

			try {
				scanner.acquire();
			} catch (Exception e) {
				LogWriter.writeLog("error in acquire: " + e.getMessage());
			}

		} else if (command.equalsIgnoreCase(SELECTSCANNER)) {
			try {
				scanner.select();
			} catch (Exception e) {
				LogWriter.writeLog("error in select: " + e.getMessage());
			}
		} else if (command.equalsIgnoreCase(DELETETEMP)) {
			int result = JOptionPane.showConfirmDialog(this, Messages.getMessage(MessageKeys.MSG_CONFIRM_DELETE_FILES), "", JOptionPane.YES_NO_OPTION);
			if (result == JOptionPane.YES_OPTION) {
				deleteAllTempFile();
				closeSaveButton.setEnabled(false);
				showTempButton.setEnabled(false);
				numImage = 0;
			}
		} else if (command.equalsIgnoreCase(STOPSCANNER)) {
			Worker lWorker = new Worker();
			lWorker.start();
			try {
				LogWriter.writeLog("Aspetto che finisca", true);
				lWorker.join();
			} catch (InterruptedException e) {

			}
			manageExit();
		} else if (command.equalsIgnoreCase(SELECTFOLDER)) {
			JFileChooser chooser = new JFileChooser();

			if (tempFolder != null) {
				chooser.setCurrentDirectory(new java.io.File(tempFolder));
			} else {
				chooser.setCurrentDirectory(new java.io.File("."));
			}

			chooser.setDialogTitle(Messages.getMessage(MessageKeys.MSG_TEMPFOLDER));
			chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
			chooser.addActionListener(this);
			chooser.setAcceptAllFileFilterUsed(false);
			if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
				tempFolder = chooser.getSelectedFile().getAbsolutePath();
				// updateScannerSettings("tempFolder", tempFolder);

				try {
					PreferenceManager.saveProp(PreferenceKeys.PROPERTY_TEMPFOLDER, tempFolder);
					PreferenceManager.reinitConfig();
				} catch (Exception e) {
					e.printStackTrace();
				}

				tempFolderLabel.setText(Messages.getMessage(MessageKeys.MSG_TEMPFOLDER) + ": " + tempFolder);
			} else {
				LogWriter.writeLog("No Selection ");
			}
		} else if (command.equalsIgnoreCase(SHOWTEMP)) {
			ArrayList<String> fileTemp = new ArrayList<String>();

			// Inserisco nella lista i vari nominativi delle immagini
			for (int indexPage = 0; indexPage < numImage; indexPage++) {

				fileTemp.add(getFilenameTemp(indexPage));
			}
			ImagePreviewFrame lImagePreviewFrame = new ImagePreviewFrame(fileTemp, 500, 687);

			try {
				lImagePreviewFrame.showImage();
			} catch (IOException e) {
				LogWriter.writeLog("Errore nella lettura dell'immagine " + fileTemp + ": " + e.getMessage(), e);
			}
		} else if (command.equalsIgnoreCase(SELECTRESOLUTION)) {
			new PanelResolution(400, 200).show(null);
		}
	}

	private void manageExit() {
		if (!errorOnUpload) {
			String lStrToInvoke = "javascript:" + callBackAskForClose + "();";
			LogWriter.writeLog("Invoco " + lStrToInvoke);
			if (callBackAskForClose != null && !callBackAskForClose.equals("")) {
				try {
					getAppletContext().showDocument(new URL(lStrToInvoke));
				} catch (MalformedURLException me) {
					StringWriter writer = new StringWriter();
					PrintWriter out = new PrintWriter(writer);
					me.printStackTrace(out);
					LogWriter.writeLog("errore : " + writer.toString());
				} catch (Throwable me) {
					StringWriter writer = new StringWriter();
					PrintWriter out = new PrintWriter(writer);
					me.printStackTrace(out);
					LogWriter.writeLog("errore : " + writer.toString());
				}
			}
		}

	}

	@Override
	public void update(ScannerIOMetadata.Type type, ScannerIOMetadata metadata) {
		TwainSource source = ((TwainIOMetadata) metadata).getSource();
		// Man mano che acquisisco le immagini, le aggiungo al vettore vImages
		if (type.equals(ScannerIOMetadata.ACQUIRED)) {
			try {
				LogWriter.writeLog("Faccio l'update per operation ACQUIRED", true);

				getInfoImage(metadata);

				BufferedImage image = metadata.getImage();
				if (abilitaPannImpEmbedded) {
					// Controllo se nelle impostazioni ho settato l'immagine in bianco e nero
					String scannerMode = PreferenceManager.getString(PreferenceKeys.PROPERTY_SCANNER_TIPOSCANSIONE_USER);
					if ((scannerMode != null) && (scannerMode.equalsIgnoreCase(TipoScansioneEnum.BLACKWHITE.getTipoScansioneCode()))) {
						LogWriter.writeLog("L'immagine viene salvata in bianco e nero");
						// Converto l'immagine in bianco e nero
						BufferedImage result = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_BYTE_GRAY);
						Graphics g = result.getGraphics();
						g.drawImage(image, 0, 0, null);
						g.dispose();
						image = result;
					}
				}

				createFileTemp(image, numImage);

				numImage++;

				LogWriter.writeLog("Aggiunta immagine N." + numImage, ta);
				closeSaveButton.setEnabled(true);
				deleteTempButton.setEnabled(true);
				showTempButton.setEnabled(true);
				imageSaved = false;
				// salva su file le properties
				String resolution_string = (String) ((TwainBufferedImage) metadata.getImage()).getProperty("resolution");
				// updateScannerSettings("resolution", resolution_string);
				PreferenceManager.saveProp(PreferenceKeys.PROPERTY_RESOLUTION, resolution_string);
				PreferenceManager.reinitConfig();
				scanningInfo.setProperty("IMG." + (numImage - 1) + ".RESOL", resolution_string);
				LogWriter.writeLog("resolution: " + resolution_string);
			} catch (Exception ex) {
				LogWriter.writeLog("Impossibile aggiungere immagine N." + numImage + ": " + ex.getMessage(), false, null, ex);
			}

		} else if (metadata.isState(TwainConstants.STATE_TRANSFERREADY)) {
			LogWriter.writeLog("Faccio l'update per operation STATE_TRANSFERREADY", true);
			try {
				// get pixelType
				TwainImageInfo imageInfo = new TwainImageInfo(source);
				imageInfo.get();
				System.out.println("pixel type = " + imageInfo.getPixelType());
				System.out.println("image info: " + imageInfo.toString());
				String pixelType_string = "" + imageInfo.getPixelType();
				// updateScannerSettings("pixelType", pixelType_string);
				PreferenceManager.saveProp(PreferenceKeys.PROPERTY_PIXELTYPE, pixelType_string);
				PreferenceManager.reinitConfig();

				scanningInfo.setProperty("IMG." + numImage + ".DPIX", "" + imageInfo.getXResolution());
				scanningInfo.setProperty("IMG." + numImage + ".DPIY", "" + imageInfo.getYResolution());
				scanningInfo.setProperty("IMG." + numImage + ".W", "" + imageInfo.getWidth());
				scanningInfo.setProperty("IMG." + numImage + ".H", "" + imageInfo.getHeight());

			} catch (Exception e) {
				System.out.println("3\b" + getClass().getName() + ".update:\n\tCannot retrieve image information.\n\t" + e);
			}
		} else if (type.equals(ScannerIOMetadata.NEGOTIATE)) {
			LogWriter.writeLog("Faccio l'update per operation NEGOTIATE", true);
			try {
				ScannerDevice device = metadata.getDevice();
				if (abilitaPannImpEmbedded) {
					device.setShowUserInterface(false);
					device.setShowProgressBar(true);
					String resolution_string = PreferenceManager.getString(PreferenceKeys.PROPERTY_SCANNER_RESOLUTION_USER);
					LogWriter.writeLog("Risoluzione impostata dall'utente: " + resolution_string);
					if (resolution_string != null) {
						device.setResolution(Double.parseDouble(resolution_string));
						LogWriter.writeLog("Risoluzione settata a " + resolution_string + " dpi");
					} else {
						LogWriter.writeLog("Uso la risoluzione di default");
						Double defaultResolution = PreferenceManager.getString(PreferenceKeys.PROPERTY_SCANNER_RESOLUTION_DEFAULT) == null ? 200
								: Double.parseDouble(PreferenceManager.getString((PreferenceKeys.PROPERTY_SCANNER_RESOLUTION_DEFAULT)));
						device.setResolution(defaultResolution);
						LogWriter.writeLog("Risoluzione settata a " + defaultResolution + " dpi");
					}
				} else {
					String resolution_string = PreferenceManager.getString(PreferenceKeys.PROPERTY_RESOLUTION);
					if (resolution_string != null) {
						device.setResolution(Double.parseDouble(resolution_string));
					}
				}
				String pixelType_string = PreferenceManager.getString(PreferenceKeys.PROPERTY_PIXELTYPE);
				if (pixelType_string != null) {
					System.out.println("state: " + source.getState());
					source.setCapability(TwainConstants.ICAP_PIXELTYPE, Integer.parseInt(pixelType_string));
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else if (type.equals(ScannerIOMetadata.EXCEPTION)) {
			LogWriter.writeLog("Faccio l'update per operation EXCEPTION", true);
			metadata.getException().printStackTrace();
		} else if (type.equals(ScannerIOMetadata.INFO)) {
			LogWriter.writeLog("Faccio l'update per operation INFO", true);
			LogWriter.writeLog("INFO=" + metadata.getInfo());
		}

		if (!started) {
			started = true;
			LogWriter.writeLog("ImageSaved settata a false", true);
		}
		// acquireButton.setEnabled( false );

	}

	private void startProgressBar() {
		closeSaveButton.setEnabled(false);
		preferenceMenu.setEnabled(false);
		invioLogMenu.setEnabled(false);
		aJProgressBar.setVisible(true);
	}

	private void stopProgressBar() {
		aJProgressBar.setVisible(false);
		preferenceMenu.setEnabled(true);
		invioLogMenu.setEnabled(true);
	}

	public boolean showConfirmOnClose() {
		LogWriter.writeLog("showConfirmOnClose.  imageSaved " + imageSaved);
		LogWriter.writeLog("ImageSaved vale " + imageSaved, true);
		return imageSaved;
	}

	public void pingIsClosed() {
		LogWriter.writeLog("comincio il ping verso l'applicativo");
		// callBackIsClosed
		Timer lTimer = new Timer();
		lTimer.schedule(new PingTimer(this), 0, 500);

	}

	class PingTimer extends TimerTask {

		private JApplet applet;

		public PingTimer(JApplet pJApplet) {
			applet = pJApplet;
		}

		@Override
		public void run() {
			LogWriter.writeLog("faccio il ping", false);
			if (callbackRichiestaChiusura != null && !callbackRichiestaChiusura.trim().equals("")) {
				LogWriter.writeLog("Chiamo " + callbackRichiestaChiusura, false);
				JSObject window = JSObject.getWindow(applet);

				Boolean richiestaChiusura = false;
				try {
					richiestaChiusura = (Boolean) window.eval(callbackRichiestaChiusura + "()");
					// richiestaChiusura = (Boolean) window.call(callbackRichiestaChiusura, new Object[]{}); //(Boolean)
					// window.eval(callbackRichiestaChiusura+"()");
					LogWriter.writeLog("risultato " + richiestaChiusura, false);
				} catch (Exception e) {
					e.printStackTrace();
				}
				if (richiestaChiusura) {
					LogWriter.writeLog("ImageSaved settata a " + imageSaved, true);
					if (!imageSaved) {
						LogWriter.writeLog("Hai richiesto la chiusura ma l'immagine non è salvata");
						cancel();
						askForClose();
					} else {
						cancel();
						String lStrToInvoke = "javascript:" + callBackAskForClose + "();";
						LogWriter.writeLog("Invoco " + lStrToInvoke);
						if (callBackAskForClose != null && !callBackAskForClose.equals("")) {
							try {
								getAppletContext().showDocument(new URL(lStrToInvoke));
							} catch (MalformedURLException me) {
								StringWriter writer = new StringWriter();
								PrintWriter out = new PrintWriter(writer);
								me.printStackTrace(out);
								LogWriter.writeLog("errore : " + writer.toString());
							}
						}
					}
				}
			}
		}

	}

	public void askForClose() {

		int value = JOptionPane.showConfirmDialog(SwingUtilities.windowForComponent(this),
				"Nessuna immagine è stata salvata sull'applicazione. Chiudendo perderai l'eventuale immagine acquisita. Chiudere?", "Attenzione",
				JOptionPane.YES_NO_OPTION);
		if (value == 0) {
			// Chiudo
			String lStrToInvoke = "javascript:" + callBackAskForClose + "();";
			LogWriter.writeLog("Invoco " + lStrToInvoke);
			if (callBackAskForClose != null && !callBackAskForClose.equals(""))
				try {
					getAppletContext().showDocument(new URL(lStrToInvoke));
				} catch (MalformedURLException me) {
					StringWriter writer = new StringWriter();
					PrintWriter out = new PrintWriter(writer);
					me.printStackTrace(out);
					LogWriter.writeLog("errore : " + writer.toString());
				}
		} else {
			JSObject window = JSObject.getWindow(this);
			Boolean annulla = (Boolean) window.eval(callbackAnnullaChiusura + "()");
			// Boolean annulla = (Boolean) window.call(callbackAnnullaChiusura, new Object[]{});
			LogWriter.writeLog("annulla : " + annulla);
			pingIsClosed();
		}
	}

	private void saveFile() {
		float dimImgX = 0;
		float dimImgY = 0;

		// LogWriter.writeLog("Salvataggio file...", ta);
		acquireButton.setEnabled(false);
		deleteTempButton.setEnabled(false);

		Document document = new Document();
		document.addAuthor("Author");
		document.addSubject("Subject");
		document.addLanguage("nl-nl");
		document.addCreationDate();

		int pages = 0;
		String fileTemp = null;

		try {
			// Salvataggio del file
			if (numImage > 0) {
				filenameCompl = fileNameTemp + ".pdf";
				LogWriter.writeLog("Salvataggio file " + filenameCompl, ta);

				Image img = null;

				try {
					PdfContentByte cb = null;
					if (abilitaPDFA) {
						PdfAWriter writer = PdfAWriter.getInstance(document, new FileOutputStream(filenameCompl), PdfAConformanceLevel.PDF_A_1B);
						writer.setTagged();
						writer.createXmpMetadata();
						writer.setCompressionLevel(9);
						dimImgX = inchToPoints(imgX.get(0));
						dimImgY = inchToPoints(imgY.get(0));
						document.setPageSize(new Rectangle(dimImgX, dimImgY));
						document.open();
						cb = writer.getDirectContent();
					} else {
						PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(filenameCompl));
						dimImgX = inchToPoints(imgX.get(0));
						dimImgY = inchToPoints(imgY.get(0));
						document.setPageSize(new Rectangle(dimImgX, dimImgY));
						document.open();
						cb = writer.getDirectContent();
					}

					scanningInfo.setProperty("NUMPAGES", String.valueOf(numImage));

					for (int k = 0; k < numImage; k++) {

						fileTemp = getFilenameTemp(k);
						img = Image.getInstance(fileTemp);

						if (img != null) {
							if (img.getScaledWidth() > dimImgX || img.getScaledHeight() > dimImgY) {
								img.scaleToFit(dimImgX, dimImgY);
							}

							img.setAbsolutePosition(0, 0);

							try {
								scanningInfo.setProperty("IMG." + k + ".H", "" + img.getHeight());
								scanningInfo.setProperty("IMG." + k + ".W", "" + img.getWidth());
							} catch (Throwable e) {
							}
							document.add(img);
							pages++;

							if (k < numImage - 1) {
								dimImgX = inchToPoints(imgX.get(k + 1));
								dimImgY = inchToPoints(imgY.get(k + 1));
								document.setPageSize(new Rectangle(inchToPoints(imgX.get(k + 1)), inchToPoints(imgY.get(k + 1))));
								document.newPage();
							}
						}
					}

					if (document.isOpen() && (pages > 0)) {
						document.close();
					}
				} catch (Exception ex) {
					LogWriter.writeLog("ERRORE=" + ex.getMessage());
					throw ex;
				} finally {
					if (document.isOpen() && (pages > 0)) {
						document.close();
					}
					img = null;
				}
				LogWriter.writeLog("Trasferimento del file sul server...", ta);
				// Trasferimento del file sul server
				String url = (contextPath.endsWith("/") ? contextPath : contextPath + "/") + (servletUpload.startsWith("/") ? servletUpload.substring(1) : servletUpload);
				LogWriter.writeLog("Inizio invio file a " + contextPath + servletUpload, true);
				upload(url, filenameCompl);
			} else {
				Thread.sleep(5000);
			}
		} catch (Exception e) {
			LogWriter.writeLog("ERRORE - " + e.getMessage());
			e.printStackTrace();
		}
		if (!errorOnUpload) {
			imageSaved = true;
			LogWriter.writeLog("ImageSaved settata a true", true);
			deleteAllTempFile();
			started = false;
			closeSaveButton.setEnabled(false);
			acquireButton.setEnabled(false);
			deleteTempButton.setEnabled(false);
			numImage = 0;
			LogWriter.writeLog("Fine salvataggio. Trasferimento del file sul server completato", ta);
		} else {
			imageSaved = false;
			closeSaveButton.setEnabled(true);
			acquireButton.setEnabled(true);
			deleteTempButton.setEnabled(true);
			LogWriter.writeLog("Errore nel trasferimento del file sul server", ta);
		}
		// LogWriter.writeLog("Fine salvataggio.", ta);
	}

	private void upload(String url, String fileName) {
		CloseableHttpClient lDefaultHttpClient = ProxyDefaultHttpClient.getClientToUse();
		CloseableHttpResponse response = null;
		File transferfile = new File(fileName);
		try {
			HttpPost request = new HttpPost(url);
			RequestConfig config = RequestConfig.custom().setExpectContinueEnabled(true).build();
			FileBody lFileBody = new FileBody(transferfile);
			StringBody lStringBodyIdSelected = new StringBody(idSelected != null ? idSelected : "", ContentType.TEXT_PLAIN);
			HttpEntity reqEntity = MultipartEntityBuilder.create().addPart("userfile", lFileBody).addPart("idSelected", lStringBodyIdSelected).build();
			LogWriter.writeLog("idSelected = " + idSelected);
			request.setEntity(reqEntity);
			request.setConfig(config);

			String result = null;
			BufferedReader br = null;
			response = lDefaultHttpClient.execute(request);
			System.out.println(response.getStatusLine());
			StringBuilder sb = new StringBuilder();
			String line;
			br = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
			while ((line = br.readLine()) != null) {
				sb.append(line);
			}
			result = sb.toString();
			LogWriter.writeLog(result);
			if (response.getStatusLine().getStatusCode() != 200)
				throw new IOException("Il server ha risposto: " + response.getStatusLine() + ": " + result);
			if (callBack != null && !callBack.equals("")) {
				String lStrToInvoke = "javascript:" + callBack + "('" + result + "');";
				getAppletContext().showDocument(new URL(lStrToInvoke));
			}
			errorOnUpload = false;
		} catch (Exception e) {
			errorOnUpload = true;
			LogWriter.writeLog("Errore", e);
			showError(e.getMessage());
		} finally {
			if (response != null) {
				try {
					response.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}

	}

	public static void showError(final String error) {
		SwingUtilities.invokeLater(new Runnable() {

			@Override
			public void run() {
				JOptionPane.showMessageDialog(null, error, "Errore", JOptionPane.ERROR_MESSAGE);
			}
		});
	}

	private String getRandomFileNameNoExt() {
		String filename = "abcde12345";

		try {
			Calendar cal = new GregorianCalendar();
			String anno = String.valueOf(cal.get(Calendar.YEAR));
			String mese = String.valueOf(cal.get(Calendar.MONTH) + 1);

			if (mese.length() < 2) {
				mese = "0" + mese;
			}

			String giorno = String.valueOf(cal.get(Calendar.DAY_OF_MONTH));

			if (giorno.length() < 2) {
				giorno = "0" + giorno;
			}

			String ore = String.valueOf(cal.get(Calendar.HOUR_OF_DAY));

			if (ore.length() < 2) {
				ore = "0" + ore;
			}

			String minuti = String.valueOf(cal.get(Calendar.MINUTE));

			if (minuti.length() < 2) {
				minuti = "0" + minuti;
			}

			String secondi = String.valueOf(cal.get(Calendar.SECOND));

			if (secondi.length() < 2) {
				secondi = "0" + secondi;
			}

			filename = anno + mese + giorno + ore + minuti + secondi + "_" + new Random().nextInt(10000);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return filename;
	}

	private String createFileTemp(RenderedImage riImage, int numImage) throws Exception {
		String ris = "";
		String filenameTemp = "";

		try {

			filenameTemp = getFilenameTemp(numImage);
			File f = new File(filenameTemp);
						
			ImageWriter jpgWriter = ImageIO.getImageWritersByFormatName("jpg").next();
			ImageWriteParam jpgWriteParam = jpgWriter.getDefaultWriteParam();
			jpgWriteParam.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
			float compressionQuality = 0.7f;
			try {
				compressionQuality = PreferenceManager.getFloat(PreferenceKeys.PROPERTY_COMPRESSION_QUALITY, 0.7f);
			} catch (Exception e) {
				LogWriter.writeLog("Errore nella conversione del parametro compressionQuality");
				LogWriter.writeLog("compressionQuality è stato settato a 0.7");
				LogWriter.writeLog("compressionQuality letto da PreferenceManager: " + PreferenceManager.getString(PreferenceKeys.PROPERTY_COMPRESSION_QUALITY));
				LogWriter.writeLog("", e);
			}
			jpgWriteParam.setCompressionQuality(compressionQuality);

			ImageOutputStream ios = ImageIO.createImageOutputStream(new FileOutputStream(f));
			jpgWriter.setOutput(ios);
			IIOImage outputImage = new IIOImage(riImage, null, null);
			jpgWriter.write(null, outputImage, jpgWriteParam);
			jpgWriter.dispose();
			
			ris = filenameTemp;

		} catch (Exception ex) {
			ex.printStackTrace();
			throw new Exception("ERRORE nella creazione del file temporaneo " + filenameTemp);
		}
		return ris;
	}

	private String getFilenameTemp(int numImage) {

		return (fileNameTemp + "_" + numImage + ".jpg");
	}

	private void deleteAllTempFile() {
		String tempFolder = PreferenceManager.getString(PreferenceKeys.PROPERTY_TEMPFOLDER);
		if (tempFolder == null || tempFolder.trim().equals("")) {
			tempFolder = System.getProperty("user.home");
		}
		LogWriter.writeLog("ProprietÃ  " + PreferenceKeys.PROPERTY_TEMPFOLDER + ": " + tempFolder);
		File dirtempFolder = new File(tempFolder);

		if (dirtempFolder.exists()) {

			LogWriter.writeLog("Inizio cancellazione file temporanei nella cartella   " + tempFolder, ta);

			File[] files = dirtempFolder.listFiles();

			for (int i = 0; i < files.length; i++) {

				String NomeFile = files[i].getName();

				// write("File trovato nella cartella : " + NomeFile , true);

				if (!files[i].isDirectory() && (NomeFile.length() > 10)) {
					if (NomeFile.substring(0, len_prefix_nomefile).equals(prefix_nomefile)) {

						// LogWriter.writeLog("Inizio Cancellazione file " + NomeFile, ta );

						if (files[i].delete()) {
							// delete file OK
							LogWriter.writeLog("Cancellazione file " + NomeFile + " eseguita con successo! ", ta);
						} else {
							// Failed to delete file
							LogWriter.writeLog("Cancellazione file " + NomeFile + " FALLITA !! ", ta);
						}
					}
					// else
					// {
					// write("File da non cancellare : " + NomeFile , true);
					// }
				}
			}
		} else {
			LogWriter.writeLog("Cartella temporanea inesistente : " + tempFolder);
		}

	}

	private void getInfoImage(ScannerIOMetadata metadata) {
		int risX = 0;
		int risY = 0;
		int widX = 0;
		int heiY = 0;

		if (metadata instanceof TwainIOMetadata) {
			try {
				((TwainIOMetadata) metadata).getSource();

				risX = Integer.parseInt("" + ((TwainBufferedImage) metadata.getImage()).getProperty("resolution"));
				risY = Integer.parseInt("" + ((TwainBufferedImage) metadata.getImage()).getProperty("resolution"));
				widX = metadata.getImage().getWidth();
				heiY = metadata.getImage().getHeight();

				System.out.println("risX: " + risX);
				System.out.println("risY: " + risY);

				imgX.add(new Double((new Double(widX)).doubleValue() / (new Double(risX)).doubleValue()));
				imgY.add(new Double((new Double(heiY)).doubleValue() / (new Double(risY)).doubleValue()));
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
		}
	}

	private float inchToPoints(Object dim) {
		Double dblDim = new Double(((Double) dim).doubleValue() * 72);

		return dblDim.floatValue();
	}

	private class Worker extends Thread {

		public void run() {
			startProgressBar();
			saveFile();
			stopProgressBar();
		}
	}

}
