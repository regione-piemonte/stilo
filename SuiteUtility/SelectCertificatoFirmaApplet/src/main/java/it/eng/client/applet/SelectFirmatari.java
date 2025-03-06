package it.eng.client.applet;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.security.Security;
import java.util.ArrayList;
import java.util.Properties;

import javax.swing.JApplet;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.RootPaneContainer;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

import com.nilo.plaf.nimrod.NimRODLookAndFeel;
import com.nilo.plaf.nimrod.NimRODTheme;

import it.eng.client.applet.i18N.MessageKeys;
import it.eng.client.applet.i18N.Messages;
import it.eng.client.applet.operation.AbstractSigner;
import it.eng.client.applet.panel.InfoLog;
import it.eng.client.applet.panel.PanelCertificati;
import it.eng.client.applet.panel.PanelLog;
import it.eng.client.applet.panel.PanelPreferenceLog;
import it.eng.client.applet.response.GenericResponse;
import it.eng.client.applet.util.PreferenceKeys;
import it.eng.client.applet.util.PreferenceManager;
import it.eng.common.LogWriter;
import it.eng.common.type.TabType;
import it.eng.proxyselector.frame.ProxyFrame;
import it.eng.server.util.Encryptor;
import netscape.javascript.JSObject;

public class SelectFirmatari implements ISelectFirmatari {

	private static final String logFile = System.getProperty("user.home") + File.separator + "log" + File.separator + "selectFirmatari.log";
	public static boolean DEBUGENABLED = false;
	public static String VERSIONE = "3";

	private JScrollPane jScrollPane = null;
	private JPanel jContentPane = null;
	private JPanel menuPanel = null;
	private JMenu preferenceMenu;
	private JMenu invioLogMenu;
	private JTabbedPane jTabbedPane = null;

	public static NimRODLookAndFeel nf;
	public static NimRODTheme nt;
	public String[] tabconfig;
	private PanelCertificati panelCert = null;

	// private CommonNameProvider commonNameProvider = null;
	private GenericResponse response = null;

	protected Object sincronizzato = new Object();

	private String baseurl = null;
	private String cookie = null;

	// component principale applet frame altro
	private RootPaneContainer mainComponent;

	public void addsigner(String str) {
		sincronizzato.notify();
	}

	/**
	 * This is the SelectFirmatari default constructor
	 */
	public SelectFirmatari(RootPaneContainer comp) {
		super();

		this.mainComponent = comp;

		try {
			File configdirectory = new File(AbstractSigner.dir + File.separator + AbstractSigner.cryptoConfig);
			if (!configdirectory.exists()) {
				configdirectory.mkdirs();
			}

			File conffile = new File(AbstractSigner.dir + File.separator + AbstractSigner.cryptoConfigFile);
			// Copio il file di configurazione se non esiste
			if (!conffile.exists()) {
				FileUtils.writeByteArrayToFile(conffile, IOUtils.toByteArray(this.getClass().getResourceAsStream("/it/eng/client/lookfeel/crypto.config")));
			}

			// Recupero la configurazione
			Properties prop = new Properties();
			prop.load(new FileInputStream(conffile));
			String template = prop.getProperty("template");
			try {
				if (template != null && !template.equals("")) {
					nt = new NimRODTheme(template);
				} else {
					nt = new NimRODTheme("it/eng/client/lookfeel/Default.theme");
				}

				nf = new NimRODLookAndFeel();
				NimRODLookAndFeel.setCurrentTheme(nt);
				UIManager.setLookAndFeel(nf);
			} catch (UnsupportedLookAndFeelException e1) {
				e1.printStackTrace();
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	public void init() {

		boolean logEnabled = PreferenceManager.enabled(PreferenceKeys.PROPERTY_LOGENABLED);
		if (logEnabled) {
			boolean logFileEnabled = false;
			try {
				logFileEnabled = PreferenceManager.getBoolean(PreferenceKeys.PROPERTY_LOGFILEENABLED);
				System.out.println("Proprieta  " + PreferenceKeys.PROPERTY_LOGFILEENABLED + ": " + logFileEnabled);
			} catch (Exception e) {
				LogWriter.writeLog("Errore nel recupero della Proprieta  " + PreferenceKeys.PROPERTY_LOGFILEENABLED);
			}
			boolean logArrayEnabled = false;
			try {
				logArrayEnabled = PreferenceManager.getBoolean(PreferenceKeys.PROPERTY_LOGARRAYENABLED);
				System.out.println("Proprieta  " + PreferenceKeys.PROPERTY_LOGARRAYENABLED + ": " + logArrayEnabled);
			} catch (Exception e) {
				LogWriter.writeLog("Errore nel recupero della Proprieta  " + PreferenceKeys.PROPERTY_LOGARRAYENABLED);
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
			System.out.println("Proprieta  " + PreferenceKeys.PROPERTY_DEBUGENABLED + ": " + DEBUGENABLED);
		} catch (Exception e) {
			LogWriter.writeLog("Errore nel recupero della Proprieta  " + PreferenceKeys.PROPERTY_DEBUGENABLED);
		}

		tabconfig = PreferenceManager.getStringArray(PreferenceKeys.PROPERTY_TABS_CONFIG);
		LogWriter.writeLog(PreferenceKeys.PROPERTY_TABS_CONFIG + "=" + tabconfig);
		PreferenceManager.dumpActualConf();

		String responseClass = null;
		try {
			responseClass = PreferenceManager.getString(PreferenceKeys.PROPERTY_OUTPUT_PROVIDER);
			LogWriter.writeLog("Verra'  utilizzato l'outputProvider " + responseClass);
		} catch (Exception e) {
			LogWriter.writeLog("Errore - Non e' stato possibile recuperare l'outputProvider da utilizzare", e);
		}
		if (responseClass == null) {
			LogWriter.writeLog("Errore - Non e' stato configurato l'outputProvider da utilizzare");
			return;
		} else {
			try {
				Class cls = Class.forName(responseClass);
				response = (GenericResponse) cls.newInstance();
				response.saveOutputParameter(null);
			} catch (InstantiationException e) {
				LogWriter.writeLog("Errore nel recupero dell'otputProvider: ", e);
				return;
			} catch (IllegalAccessException e) {
				LogWriter.writeLog("Errore nel recupero dell'outputProvider: ", e);
				return;
			} catch (ClassNotFoundException e) {
				LogWriter.writeLog("Errore nel recupero dell'outputProvider: ", e);
				return;
			} catch (Exception e) {
				LogWriter.writeLog("Errore nel salvataggio dei parametri dell'outputProvider: ", e);
				return;
			}
		}

		this.mainComponent.setContentPane(getJContentPane());

		Security.addProvider(new BouncyCastleProvider());

	}

	// TODO Funzione inutilizzata?
	// public void close(){
	//
	// LogWriter.writeLog( "Entro nel metodo close " );
	//
	// if( mainComponent instanceof JApplet ){
	// JApplet lApplet = (JApplet)mainComponent;
	//
	// String callback = lApplet.getParameter("callbackToCall");
	// if (callback!=null ){
	// String lStrToInvoke = "javascript:" + callback + "('prova');";
	// System.out.println("Invoco " +lStrToInvoke);
	// try {
	// lApplet.getAppletContext().showDocument(new URL(lStrToInvoke));
	// } catch (MalformedURLException e1) {
	// // TODO Auto-generated catch block
	// e1.printStackTrace();
	// }
	// lApplet.stop();
	// lApplet.destroy();
	// System.exit(1);
	// }
	// }
	//
	// if( mainComponent instanceof JApplet )
	// ((JApplet)mainComponent).destroy();
	// else if( mainComponent instanceof JFrame )
	// ((JFrame)mainComponent).dispose();
	// }

	/**
	 * This method initializes jContentPane
	 * 
	 * @return javax.swing.JPanel
	 */
	private JScrollPane getJContentPane() {

		if (jContentPane == null) {
			GridBagLayout gridLayout = new GridBagLayout();
			GridBagConstraints c = new GridBagConstraints();

			jContentPane = new JPanel();
			jContentPane.setLayout(gridLayout);

			// c.anchor=GridBagConstraints.LINE_START;
			c.fill = GridBagConstraints.BOTH;
			c.anchor = GridBagConstraints.CENTER;

			gridLayout.setConstraints(jContentPane, c);

			jScrollPane = new JScrollPane(jContentPane);

			c.gridy = 0;
			c.gridx = 0;

			c.anchor = GridBagConstraints.CENTER;
			c.fill = GridBagConstraints.BOTH;

			c.gridx = 0;
			c.weightx = 1.0;
			// c.weighty=1.0;
			c.insets = new Insets(2, 10, 2, 10);
			JPanel menu = getMenuPanel();
			jContentPane.add(menu, c);
			c.gridy = 1;
			c.insets = new Insets(2, 10, 2, 10);
			c.anchor = GridBagConstraints.LINE_START;

			c.fill = GridBagConstraints.BOTH;
			JTabbedPane tbs = getJTabbedPane();
			jContentPane.add(tbs, c);
		}

		return jScrollPane;
	}

	/**
	 * This method initializes jTabbedPane
	 * 
	 * @return javax.swing.JTabbedPane
	 */
	public JTabbedPane getJTabbedPane() {
		if (jTabbedPane == null) {
			jTabbedPane = new JTabbedPane();
			for (String tabview : tabconfig) {
				TabType view = TabType.valueOf(tabview);
				switch (view) {
				// case DRIVER:
				// jTabbedPane.addTab( Messages.getMessage( MessageKeys.TAB_DRIVER_TITLE ), new PanelDriver());
				// break;
				case CERTIFICATI:
					panelCert = new PanelCertificati(this);
					jTabbedPane.addTab(Messages.getMessage(MessageKeys.TAB_CERTIFICATI_TITLE), panelCert);
					break;
				}
			}
		}
		return jTabbedPane;
	}

	public JPanel getMenuPanel() {
		if (menuPanel == null) {
			menuPanel = new JPanel();
			JMenuBar menuBar = new JMenuBar();

			boolean menuConfigurazioniEnabled = PreferenceManager.getBoolean(PreferenceKeys.MENU_CONFIGURAZIONI_ENABLED);
			if (menuConfigurazioniEnabled) {
				preferenceMenu = new JMenu(Messages.getMessage(MessageKeys.MENU_PREFERENCE_TITLE));

				boolean configurazioniLogEnabled = PreferenceManager.getBoolean(PreferenceKeys.PREFERENCEINVIOLOGPANEL_ENABLED);
				if (configurazioniLogEnabled) {
					JMenuItem log = new JMenuItem(Messages.getMessage(MessageKeys.MENU_PREFERENCE_LOG_TITLE));
					log.addActionListener(new ActionListener() {

						@Override
						public void actionPerformed(ActionEvent e) {
							int preferenceInvioLogPanelWidth = PreferenceManager.getInt(PreferenceKeys.PREFERENCEINVIOLOGPANEL_WIDTH, 400);
							int preferenceInvioLogPanelHeight = PreferenceManager.getInt(PreferenceKeys.PREFERENCEINVIOLOGPANEL_HEIGHT, 400);
							new PanelPreferenceLog(preferenceInvioLogPanelWidth, preferenceInvioLogPanelHeight).show(mainComponent.getContentPane());
						}
					});
					preferenceMenu.add(log);
				}
				boolean configurazioniReteEnabled = PreferenceManager.getBoolean(PreferenceKeys.PREFERENCERETEPANEL_ENABLED);
				if (configurazioniReteEnabled) {
					JMenuItem rete = new JMenuItem(Messages.getMessage(MessageKeys.MENU_PREFERENCE_RETE_TITLE));
					rete.addActionListener(new ActionListener() {

						@Override
						public void actionPerformed(ActionEvent e) {
							String testUrl = PreferenceManager.getString(PreferenceKeys.PROPERTY_TEST_URL);
							new ProxyFrame(testUrl).setVisible(true);
						}
					});
					preferenceMenu.add(rete);
				}
				menuBar.add(preferenceMenu);
			}

			boolean menuLogEnabled = PreferenceManager.getBoolean(PreferenceKeys.MENU_LOG_ENABLED);
			if (menuLogEnabled) {
				invioLogMenu = new JMenu(Messages.getMessage(MessageKeys.MENU_INVIOLOG_TITLE));
				JMenuItem invioLog = new JMenuItem(Messages.getMessage(MessageKeys.MENU_INVIOLOG_TITLE));
				invioLog.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent e) {
						int invioLogPanelWidth = PreferenceManager.getInt(PreferenceKeys.INVIOLOGPANEL_WIDTH, 400);
						int invioLogPanelHeight = PreferenceManager.getInt(PreferenceKeys.INVIOLOGPANEL_HEIGHT, 400);
						new PanelLog(invioLogPanelWidth, invioLogPanelHeight).show(mainComponent.getContentPane());
					}
				});
				invioLogMenu.add(invioLog);
				menuBar.add(invioLogMenu);
			}
			if (DEBUGENABLED) {
				JMenu infoMenu = new JMenu(Messages.getMessage(MessageKeys.MENU_INFO_TITLE));
				JMenuItem info = new JMenuItem(Messages.getMessage(MessageKeys.MENU_INFO_TITLE));
				info.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent e) {
						new InfoLog().show(mainComponent.getContentPane(), VERSIONE);
					}
				});
				infoMenu.add(info);
				menuBar.add(infoMenu);
			}

			menuPanel.add(menuBar);
		}
		return menuPanel;
	}

	/**
	 * Funzione che viene chiamata nel momento in cui si sta terminando l'esecuzione e si vuole ritornare (eseguendo la callback che è stata passata) al sistema
	 * chiamante I parametri in input sono i valori che corrispondono all'intestatario della chiavetta di firma
	 * 
	 * @param pin
	 * @param firmatario
	 * @param alias
	 */
	public void closeApplet(String pin, String firmatario, String alias, String metodo) {
		LogWriter.writeLog("Entro nel metodo closeApplet ");

		LogWriter.writeLog("autoClosePostSearch: " + response.getAutoClosePostSearch());
		if (response.getAutoClosePostSearch()) {
			if (mainComponent instanceof JApplet) {
				JApplet lApplet = (JApplet) mainComponent;

				String callBackAskForClose = response.getCallBackAskForClose();
				LogWriter.writeLog("callBackAskForClose: " + callBackAskForClose);
				if (callBackAskForClose != null && !callBackAskForClose.equals("")) {

					// Codifico il pin in modo tale da non inviare tale valore in chiaro
					LogWriter.writeLog("pin" + pin);
					pin = Encryptor.codificaConZero(pin);
					LogWriter.writeLog("pin" + pin);
					
					/*
					 * Creo la stringa js che dovrà essere richiamata pin, firmatario e alias sono i parametri che devono essere ritornati alla funzione js
					 */
					String lStrToInvoke = "javascript:" + callBackAskForClose + "(\"" + pin + "\",\"" + firmatario + "\",\"" + alias + "\",\"" + metodo + "\");";

					LogWriter.writeLog("Invoco " + lStrToInvoke);

					/*
					 * getWindow(): Returns a JSObject for the window containing the given applet. eval serve per richiamare una funzione javascript
					 * 
					 * Si richiama la funzione di callback indicata nella variabile callBackAskForClose che è stata passata come parametro
					 */
					JSObject.getWindow(lApplet).eval(lStrToInvoke);

					lApplet.stop();
					lApplet.destroy();
					System.exit(1);
				}
			}
		}
	}

	public JMenu getPreferenceMenu() {
		return preferenceMenu;
	}

	public JMenu getInvioLogMenu() {
		return invioLogMenu;
	}

	public void setMenuPanel(JPanel menuPanel) {
		this.menuPanel = menuPanel;
	}

	public String getBaseurl() {
		return baseurl;
	}

	public String getCookie() {
		return cookie;
	}

	public PanelCertificati getPanelCertificati() {
		return panelCert;
	}

	public void selectedTab(int index) {
		getJTabbedPane().setSelectedIndex(index);
	}

	public NimRODLookAndFeel getLookFeel() {
		return nf;
	}

	public RootPaneContainer getMainComponent() {
		return mainComponent;
	}

	public void setMainComponent(RootPaneContainer mainComponent) {
		this.mainComponent = mainComponent;
	}

	public GenericResponse getResponse() {
		return response;
	}

	public void setResponse(GenericResponse response) {
		this.response = response;
	}

}
