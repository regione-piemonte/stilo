package it.eng.hybrid.module.stampaFiles.ui;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.List;
import java.util.ResourceBundle;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;

import org.apache.log4j.Logger;

import it.eng.areas.hybrid.module.util.json.JSONArray;
import it.eng.hybrid.module.stampaFiles.StampaFilesClientModule;
import it.eng.hybrid.module.stampaFiles.bean.FileBean;
import it.eng.hybrid.module.stampaFiles.inputFileProvider.FileInputProvider;
import it.eng.hybrid.module.stampaFiles.inputFileProvider.FileInputResponse;
import it.eng.hybrid.module.stampaFiles.messages.MessageKeys;
import it.eng.hybrid.module.stampaFiles.messages.Messages;
import it.eng.hybrid.module.stampaFiles.outputProvider.OutputProvider;
import it.eng.hybrid.module.stampaFiles.preferences.PreferenceKeys;
import it.eng.hybrid.module.stampaFiles.preferences.PreferenceManager;
import it.eng.proxyselector.frame.ProxyFrame;

/**
 * 
 * 
 *
 */
public class StampaFileApplication extends JFrame {

	private static final long serialVersionUID = 1L;

	public final static Logger logger = Logger.getLogger(StampaFileApplication.class);

	private static final String logFile = System.getProperty("user.home") + File.separator + "log" + File.separator + "stampaFileApplet.log";

	private JPanel jContentPane = null;
	private JScrollPane jScrollPane = null;
	private JPanel menuPanel = null;
	private JMenu preferenceMenu;
	private JMenu invioLogMenu;
	private JTabbedPane jTabbedPane = null;

	private PanelStampaFile panelStampa = null;

	private List<FileBean> fileBeanListInput;
	private OutputProvider outputProvider;

	private StampaFilesClientModule module;

	public StampaFileApplication(StampaFilesClientModule module, JSONArray parameters) {

		this.module = module;

		// init della config
		PreferenceManager.initConfig(parameters);

		init();
	}

	public void init() {

		try {
			Messages.setBundle(ResourceBundle.getBundle("messages"));
		} catch (Exception e) {
			logger.info("Exception " + e + " loading resource bundle.\n" + "Also check you have a file to support Locale=" + java.util.Locale.getDefault(), e);
		}

		// Recupero la configurazione
		// NimRODTheme nt = new NimRODTheme("it/eng/hybrid/module/stampaFiles/ui/Default.theme");
		// NimRODLookAndFeel nf = new NimRODLookAndFeel();
		// NimRODLookAndFeel.setCurrentTheme(nt);
		// try {
		// UIManager.setLookAndFeel(nf);
		// } catch (UnsupportedLookAndFeelException e1) {
		// // TODO Auto-generated catch block
		// e1.printStackTrace();
		// }

		int mainPanelWidth = PreferenceManager.getInt(PreferenceKeys.MAINPANEL_WIDTH, 400);
		int mainPanelHeight = PreferenceManager.getInt(PreferenceKeys.MAINPANEL_HEIGHT, 400);
		logger.info("StampaFileApplet - mainPanelWidth=" + mainPanelWidth + " mainPanelHeight=" + mainPanelHeight);
		this.setSize(mainPanelWidth, mainPanelHeight);

		try {
			String fileInputProviderClass = null;
			try {
				fileInputProviderClass = PreferenceManager.getString(PreferenceKeys.PROPERTY_FILEINPUT_PROVIDER);
				logger.info("Verra' utilizzato il fileInputProvider " + fileInputProviderClass);
			} catch (Exception e) {
				logger.info("Errore - Non e' stato possibile recuperare il fileInputProvider da utilizzare", e);
				return;
			}

			if (fileInputProviderClass == null) {
				logger.info("Errore - fileInputProvider non configurato.");
				return;
			}

			FileInputProvider fileInputProvider = null;
			try {
				Class cls = Class.forName(fileInputProviderClass);
				fileInputProvider = (FileInputProvider) cls.newInstance();
			} catch (InstantiationException e) {
				logger.info("Errore nel recupero del fileInputProvider: ", e);
				return;
			} catch (IllegalAccessException e) {
				logger.info("Errore nel recupero del fileInputProvider: ", e);
				return;
			} catch (ClassNotFoundException e) {
				logger.info("Errore nel recupero del fileInputProvider: ", e);
				return;
			}

			FileInputResponse fileInputProviderResponse;
			try {
				fileInputProviderResponse = fileInputProvider.getFileInputResponse();
				fileBeanListInput = fileInputProviderResponse.getFileBeanList();

			} catch (Exception e) {
				logger.info("Errore: ", e);
				JOptionPane.showMessageDialog(getContentPane(), "Errore nel caricamento dei files", "Errore", JOptionPane.ERROR_MESSAGE);
				return;
			}

			String outputProviderClass = null;
			try {
				outputProviderClass = PreferenceManager.getString(PreferenceKeys.PROPERTY_OUTPUT_PROVIDER);
				logger.info("Verra'� utilizzato l'outputProvider " + outputProviderClass);
			} catch (Exception e) {
				logger.info("Errore - Non e' stato possibile recuperare l'outputProvider da utilizzare", e);
			}
			if (outputProviderClass == null) {
				logger.info("Errore - Non e' stato configurato l'outputProvider da utilizzare");
				// return;
			} else {
				try {
					Class cls = Class.forName(outputProviderClass);
					outputProvider = (OutputProvider) cls.newInstance();
					outputProvider.saveOutputParameter();
				} catch (InstantiationException e) {
					logger.info("Errore nel recupero dell'outputProvider: ", e);
					return;
				} catch (IllegalAccessException e) {
					logger.info("Errore nel recupero dell'outputProvider: ", e);
					return;
				} catch (ClassNotFoundException e) {
					logger.info("Errore nel recupero dell'outputProvider: ", e);
					return;
				} catch (Exception e) {
					logger.info("Errore nel salvataggio dei parametri dell'outputProvider: ", e);
					return;
				}
			}

		} catch (Exception ex) {
			JOptionPane.showMessageDialog(getContentPane(), "Impossibile leggere il file", "Errore", JOptionPane.ERROR_MESSAGE);
		}

		boolean skipSceltaStampante = PreferenceManager.getBoolean(PreferenceKeys.PROPERTY_SKIP_SCELTA_STAMPANTE);
		logger.info("skipSceltaStampante: " + skipSceltaStampante);
		String stampanteDefault = PreferenceManager.getString(PreferenceKeys.PROPERTY_STAMPANTE_SELEZIONATA);
		logger.info("stampanteDefault: " + stampanteDefault);
		boolean test = stampanteDefault == null;
		logger.info("stampanteDefault: " + test);
		if (skipSceltaStampante && stampanteDefault != null && !stampanteDefault.equalsIgnoreCase("") && !stampanteDefault.equalsIgnoreCase("null")) {
			panelStampa = new PanelStampaFile(this);
			panelStampa.inviaInStampa();
		} else {
			panelStampa = new PanelStampaFile(this);
			panelStampa.init();
			boolean skipUserInterface = PreferenceManager.getBoolean(PreferenceKeys.PROPERTY_SKIP_USER_INTERFACE);
			logger.info("skipUserInterface: " + skipUserInterface);
			if (!skipUserInterface)
				setContentPane(getJContentPane());
		}
	}

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

	public JPanel getMenuPanel() {
		if (menuPanel == null) {
			menuPanel = new JPanel();
			JMenuBar menuBar = new JMenuBar();

			boolean menuConfigurazioniEnabled = PreferenceManager.getBoolean(PreferenceKeys.MENU_CONFIGURAZIONI_ENABLED);
			if (menuConfigurazioniEnabled) {
				preferenceMenu = new JMenu(Messages.getMessage(MessageKeys.MENU_PREFERENCE_TITLE));

				boolean configurazioniStampaEnabled = PreferenceManager.getBoolean(PreferenceKeys.PREFERENCESTAMPAPANEL_ENABLED);
				if (configurazioniStampaEnabled) {
					JMenuItem stampa = new JMenuItem(Messages.getMessage(MessageKeys.MENU_PREFERENCE_STAMPA_TITLE));
					stampa.addActionListener(new ActionListener() {

						@Override
						public void actionPerformed(ActionEvent e) {
							panelStampa.mostraProprietaStampante();
						}
					});
					preferenceMenu.add(stampa);
				}
				boolean configurazioniLogEnabled = PreferenceManager.getBoolean(PreferenceKeys.PREFERENCEINVIOLOGPANEL_ENABLED);
				if (configurazioniLogEnabled) {
					JMenuItem log = new JMenuItem(Messages.getMessage(MessageKeys.MENU_PREFERENCE_LOG_TITLE));
					log.addActionListener(new ActionListener() {

						@Override
						public void actionPerformed(ActionEvent e) {
							int preferenceInvioLogPanelWidth = PreferenceManager.getInt(PreferenceKeys.PREFERENCEINVIOLOGPANEL_WIDTH, 400);
							int preferenceInvioLogPanelHeight = PreferenceManager.getInt(PreferenceKeys.PREFERENCEINVIOLOGPANEL_HEIGHT, 400);
							new PanelPreferenceLog(preferenceInvioLogPanelWidth, preferenceInvioLogPanelHeight).show();
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
						new PanelLog(invioLogPanelWidth, invioLogPanelHeight).show();
					}
				});
				invioLogMenu.add(invioLog);
				menuBar.add(invioLogMenu);
			}
			// if( DEBUGENABLED ){
			// JMenu infoMenu = new JMenu( Messages.getMessage( MessageKeys.MENU_INFO_TITLE ) );
			// JMenuItem info = new JMenuItem( Messages.getMessage( MessageKeys.MENU_INFO_TITLE ) );
			// info.addActionListener( new ActionListener() {
			// @Override
			// public void actionPerformed(ActionEvent e) {
			// new InfoLog().show( mainComponent.getContentPane(), VERSIONE );
			// }
			// });
			// infoMenu.add( info );
			// menuBar.add(infoMenu);
			// }
			// ExitMenu lExitMenu = new ExitMenu( Messages.getMessage( MessageKeys.MENU_EXIT_TITLE ) ){
			// @Override
			// protected boolean otherAction() {
			// FileOutputProvider fop = getFileOutputProvider();
			// File fileInput = getFile();
			// if(fop!=null && fileInput!=null && getPanelsign().getOutputFile()!=null){
			// InputStream in;
			// try {
			// in = new FileInputStream( getPanelsign().getOutputFile() );
			// if( getFileNameConvertito()!=null ){
			// logger.info("Utilizzo il nome convertito " + getFileNameConvertito() );
			// fop.saveOutputFile(in, getFileNameConvertito(), (String)panelsign.getTipoFirma().getSelectedItem() );
			// } else if(PreferenceManager.getString( PreferenceKeys.PROPERTY_FILENAME )!=null){
			// logger.info("Utilizzo il nome dalla preference " + PreferenceManager.getString( PreferenceKeys.PROPERTY_FILENAME ) );
			// fop.saveOutputFile(in, PreferenceManager.getString( PreferenceKeys.PROPERTY_FILENAME ), (String)panelsign.getTipoFirma().getSelectedItem());
			// } else {
			// logger.info("Utilizzo il nome del file " + fileInput.getName() );
			// fop.saveOutputFile(in, fileInput.getName(), (String)panelsign.getTipoFirma().getSelectedItem());
			// }
			// } catch (FileNotFoundException e) {
			// logger.info("Errore", e);
			// } catch (Exception e) {
			// logger.info("Errore", e);
			// }
			// }
			//
			// FileUtility.deleteTempDirectory( getRootFileDirectory() );
			// return true;
			// }
			// };
			// menuBar.add(lExitMenu );
			menuPanel.add(menuBar);
		}
		return menuPanel;
	}

	/**
	 * This method initializes jTabbedPane
	 * 
	 * @return javax.swing.JTabbedPane
	 */
	public JTabbedPane getJTabbedPane() {
		if (jTabbedPane == null) {
			jTabbedPane = new JTabbedPane();
			jTabbedPane.addTab(Messages.getMessage(MessageKeys.TAB_STAMPA_TITLE), panelStampa);
		}
		return jTabbedPane;
	}

	public List<FileBean> getFileBeanListInput() {
		return fileBeanListInput;
	}

	public void setFileBeanListInput(List<FileBean> fileBeanListInput) {
		this.fileBeanListInput = fileBeanListInput;
	}

	public void closeFrame(String stampanteSelezionata) {
		logger.info("Entro nel metodo closeFrame ");

		if (outputProvider != null) {
			logger.info("AutoClosePostPrint: " + outputProvider.getAutoClosePostPrint());
			if (outputProvider.getAutoClosePostPrint()) {

				// Imposto i contenuti delle callback da chiamare
				module.setCallBackFunction(outputProvider.getCallBackAskForClose());
				module.setCallBackArgs(stampanteSelezionata);
			}
		}
	}
}
