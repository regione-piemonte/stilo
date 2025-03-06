package it.eng.areas.hybrid.module.cryptoLight.ui;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URI;
import java.net.URISyntaxException;
import java.security.Security;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.ResourceBundle;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;

import org.apache.log4j.Logger;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

import it.eng.areas.hybrid.module.cryptoLight.SignerMultiClientModule;
import it.eng.areas.hybrid.module.cryptoLight.cnProvider.CommonNameProvider;
import it.eng.areas.hybrid.module.cryptoLight.inputFileProvider.FileInputProvider;
import it.eng.areas.hybrid.module.cryptoLight.inputFileProvider.FileInputResponse;
import it.eng.areas.hybrid.module.cryptoLight.outputFileProvider.FileOutputProvider;
import it.eng.areas.hybrid.module.cryptoLight.sign.FileBean;
import it.eng.areas.hybrid.module.cryptoLight.sign.HashFileBean;
import it.eng.areas.hybrid.module.cryptoLight.tools.Factory;
import it.eng.areas.hybrid.module.cryptoLight.util.FileUtility;
import it.eng.areas.hybrid.module.cryptoLight.util.MessageKeys;
import it.eng.areas.hybrid.module.cryptoLight.util.Messages;
import it.eng.areas.hybrid.module.cryptoLight.util.PreferenceKeys;
import it.eng.areas.hybrid.module.cryptoLight.util.PreferenceManager;
import it.eng.areas.hybrid.module.util.json.JSONArray;
import it.eng.proxyselector.frame.ProxyFrame;

public class SignApplication extends JFrame {

	public final static Logger logger = Logger.getLogger(SignApplication.class);

	public static boolean DEBUGENABLED = false;
	public static String VERSIONE = "3";

	private List<FileBean> fileBeanList = new ArrayList<FileBean>();
	private List<HashFileBean> hashfilebean = new ArrayList<HashFileBean>();

	private boolean fileFirmatoPresente = false;
	private boolean fileTuttiFirmati = true;

	public enum DialogButton {
		OK, CANCEL
	};

	private static final long serialVersionUID = 1L;

	private JPasswordField pinField = null;
	private JCheckBox holdPinField = null;
	private JPanel buttonsPanel = null;
	private JButton okButton = null;
	private JButton cancelButton = null;

	private DialogButton dialogResult;

	private JTabbedPane jTabbedPane = null;
	private JScrollPane jScrollPane = null;
	private JPanel jContentPane = null;
	private JPanel menuPanel = null;
	private JMenu preferenceMenu;
	private JMenu invioLogMenu;
	public String[] tabconfig;
	private PanelSign panelsign = null;

	private SignerMultiClientModule module;

	private FileOutputProvider fileOutputProvider = null;
	private CommonNameProvider commonNameProvider = null;
	private FileInputProvider fileInputProvider = null;

	/**
	 * @param owner
	 */
	public SignApplication(SignerMultiClientModule module, JSONArray parameters) {
		// super(owner);
		this.module = module;

		writeSystemProperty();

		// init della config
		PreferenceManager.initConfig(parameters);
		try {
			Messages.setBundle(ResourceBundle.getBundle("messages"));
		} catch (Exception e) {

		}

		try {
			DEBUGENABLED = PreferenceManager.getBoolean(PreferenceKeys.PROPERTY_DEBUGENABLED);
			logger.info("Propriet� " + PreferenceKeys.PROPERTY_DEBUGENABLED + ": " + DEBUGENABLED);
		} catch (Exception e) {
			logger.info("Errore nel recupero della propriet� " + PreferenceKeys.PROPERTY_DEBUGENABLED);
		}

		initialize();

		boolean initOK = initializeFileInputProvider();

		if (initOK) {

			/*
			 * Determino i file da firmare
			 */
			FileInputResponse fileInputProviderResponse;
			try {
				fileInputProviderResponse = fileInputProvider.getFileInputResponse();
				List<FileBean> fileBeanListInput = fileInputProviderResponse.getFileBeanList();
				if (fileBeanListInput != null && fileBeanListInput.size() > 0) {
					for (FileBean bean : fileBeanListInput) {
						String beanFileName = bean.getFileName();
						bean = buildFileInfo(bean);
						if (bean != null) {
							if (bean.getIsFirmato() != null && bean.getIsFirmato())
								fileFirmatoPresente = true;
							fileTuttiFirmati = bean.getIsFirmato() && fileTuttiFirmati;
							fileBeanList.add(bean);
						} else {
							panelsign.getTextArea().append(beanFileName + " ignorato a causa di un errore nell'acquisizione\n");
						}
					}
				} else {
					List<HashFileBean> hashFiles = fileInputProviderResponse.getHashfilebean();
					// logger.info("FILES " + hashFiles);
					if (hashFiles != null) {
						for (int i = 0; i < hashFiles.size(); i++) {
							HashFileBean fileBean = hashFiles.get(i);
							hashfilebean.add(fileBean);
						}
						logger.info("Ricevute " + hashFiles.size() + " impronte da firmare");
					}
				}

				panelsign.showFiles();
				panelsign.showConfigData();
			} catch (Exception e) {
				logger.info("Errore: ", e);
				// JOptionPane.showMessageDialog(mainComponent.getContentPane(),"Errore nel caricamento dei files","Errore", JOptionPane.ERROR_MESSAGE);
				// return;
			}

			String fileOutputProviderClass = null;
			try {
				fileOutputProviderClass = PreferenceManager.getString(PreferenceKeys.PROPERTY_FILEOUTPUT_PROVIDER);
				logger.info("Verr� utilizzato il fileOutputProvider " + fileOutputProviderClass);
			} catch (Exception e) {
				logger.info("Errore - Non � stato possibile recuperare il fileOutputProvider da utilizzare", e);
			}
			if (fileOutputProviderClass == null) {
				logger.info("Errore - Non � stato configurato il fileOutputProvider da utilizzare");
				// return;
			} else {
				try {
					Class cls = Class.forName(fileOutputProviderClass);
					fileOutputProvider = (FileOutputProvider) cls.newInstance();
					fileOutputProvider.saveOutputParameter();
				} catch (InstantiationException e) {
					logger.info("Errore nel recupero del fileOutputProvider: ", e);
					return;
				} catch (IllegalAccessException e) {
					logger.info("Errore nel recupero del fileOutputProvider: ", e);
					return;
				} catch (ClassNotFoundException e) {
					logger.info("Errore nel recupero del fileOutputProvider: ", e);
					return;
				} catch (Exception e) {
					logger.info("Errore nel salvataggio dei parametri del fileOutputProvider: ", e);
					return;
				}
			}

			String commonNameProviderClass = null;
			try {
				commonNameProviderClass = PreferenceManager.getString(PreferenceKeys.PROPERTY_COMMON_NAME_PROVIDER);
				if (commonNameProviderClass != null && !commonNameProviderClass.trim().equals("")) {
					Class lClass = Class.forName(PreferenceManager.getString(PreferenceKeys.PROPERTY_COMMON_NAME_PROVIDER));
					commonNameProvider = (CommonNameProvider) lClass.newInstance();
					if (commonNameProvider != null)
						commonNameProvider.saveOutputParameter();
					// module.setSignResult(fileOutputProvider.getCallBackAskForClose());
				}
			} catch (Exception e) {
				logger.info("Errore nel recupero del commonNameProvider: ", e);
				return;
			}
		}

		Security.addProvider(new BouncyCastleProvider());
	}

	/**
	 * Inizializzo il file input provider in base a quanto � stato fornito come input
	 * @return true se l'inizializzazione � avvenuta correttamente, false altrimenti.
	 */
	private boolean initializeFileInputProvider() {
		boolean initOK = true;
		try {
			String fileInputProviderClass = null;
			try {
				fileInputProviderClass = PreferenceManager.getString(PreferenceKeys.PROPERTY_FILEINPUT_PROVIDER);
				logger.info("Verra' utilizzato il fileInputProvider " + fileInputProviderClass);
			} catch (Exception e) {
				logger.info("Errore - Non e' stato possibile recuperare il fileInputProvider da utilizzare", e);
				initOK = false;
			}

			if (fileInputProviderClass == null) {
				logger.info("Errore - fileInputProvider non configurato.");
				initOK = false;
			}

			if (initOK) {
				fileInputProvider = null;
				try {
					Class cls = Class.forName(fileInputProviderClass);
					fileInputProvider = (FileInputProvider) cls.newInstance();
				} catch (InstantiationException e) {
					logger.info("Errore nel recupero del fileInputProvider: ", e);
					initOK = false;
				} catch (IllegalAccessException e) {
					logger.info("Errore nel recupero del fileInputProvider: ", e);
					initOK = false;
				} catch (ClassNotFoundException e) {
					logger.info("Errore nel recupero del fileInputProvider: ", e);
					initOK = false;
				}
			}
			return true; // Inizializzazione avvenuta correttamente
		} catch (Exception e) {
			logger.info("Errore nel recupero del fileInputProvider: ", e);
			return false; // Inizializzazione non avvenuta correttamente
		}
	}
	
	/**
	 * Ritorno il valore del file input provider
	 * @return il valore del file input provider
	 */
	public FileInputProvider getFileInputProvider()
	{
		return fileInputProvider;
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
			;
			if (menuConfigurazioniEnabled) {
				preferenceMenu = new JMenu(Messages.getMessage(MessageKeys.MENU_PREFERENCE_TITLE));

				boolean configurazioniFirmaMarcaEnabled = PreferenceManager.getBoolean(PreferenceKeys.PREFERENCEFIRMAMARCAPANEL_ENABLED);
				if (configurazioniFirmaMarcaEnabled) {
					JMenuItem firmaMarca = new JMenuItem(Messages.getMessage(MessageKeys.MENU_PREFERENCE_FIRMAMARCA_TITLE));
					firmaMarca.addActionListener(new ActionListener() {

						@Override
						public void actionPerformed(ActionEvent e) {
							int preferenceFirmaMarcaPanelWidth = PreferenceManager.getInt(PreferenceKeys.PREFERENCEFIRMAMARCAPANEL_WIDTH, 400);
							int preferenceFirmaMarcaPanelHeight = PreferenceManager.getInt(PreferenceKeys.PREFERENCEFIRMAMARCAPANEL_HEIGHT, 400);
							new PreferenceFirmaMarcaPanel(preferenceFirmaMarcaPanelWidth, preferenceFirmaMarcaPanelHeight).show(panelsign);
						}
					});
					preferenceMenu.add(firmaMarca);
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
							String jSessionId = PreferenceManager.getString(PreferenceKeys.PROPERTY_JSESSIONID);
							URI uri;
							String domain = "";
							try {
								uri = new URI(testUrl);
								domain = uri.getHost();
								domain =  domain.startsWith("www.") ? domain.substring(4) : domain;
							} catch (URISyntaxException e1) {
								logger.error("Errore nel recupero del dominio", e1);
							}
							new ProxyFrame(testUrl, jSessionId, domain, "/").setVisible(true);
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
						new PanelLog(invioLogPanelWidth, invioLogPanelHeight).show(/* mainComponent.getContentPane() */);
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
						new InfoLog().show( /* mainComponent.getContentPane(), */ VERSIONE);
					}
				});
				infoMenu.add(info);
				menuBar.add(infoMenu);
			}
			menuPanel.add(menuBar);
		}
		return menuPanel;
	}

	public JTabbedPane getJTabbedPane() {
		if (jTabbedPane == null) {
			jTabbedPane = new JTabbedPane();
			for (String tabview : tabconfig) {
				TabType view = TabType.valueOf(tabview);
				switch (view) {
				case DRIVER:
					jTabbedPane.addTab(Messages.getMessage(MessageKeys.TAB_DRIVER_TITLE), new PanelDriver());
					break;
				case FIRMA:
					panelsign = new PanelSign(this);
					jTabbedPane.addTab(Messages.getMessage(MessageKeys.TAB_FIRMA_TITLE), panelsign);
					break;
				}
			}
		}
		return jTabbedPane;
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {

		tabconfig = PreferenceManager.getStringArray(PreferenceKeys.PROPERTY_TABS_CONFIG);

		this.setContentPane(getJContentPane());
		this.getRootPane().setDefaultButton(getOkButton());
	}

	private JCheckBox getHoldPingField() {
		if (holdPinField == null) {
			holdPinField = new JCheckBox();
			holdPinField.setText("Ricorda il pin");
			holdPinField.setSelected(true);
			holdPinField.setPreferredSize(new Dimension(120, 20));
		}
		return holdPinField;
	}

	/**
	 * This method initializes buttonsPanel
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getButtonsPanel() {
		if (buttonsPanel == null) {
			buttonsPanel = new JPanel();
			buttonsPanel.setLayout(new FlowLayout());
			buttonsPanel.add(getOkButton(), null);
			buttonsPanel.add(getCancelButton(), null);
		}
		return buttonsPanel;
	}

	/**
	 * This method initializes okButton
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getOkButton() {
		if (okButton == null) {
			okButton = new JButton();
			okButton.setText("Conferma");
			okButton.addActionListener(new java.awt.event.ActionListener() {

				public void actionPerformed(java.awt.event.ActionEvent e) {
					dialogResult = DialogButton.OK;
					setVisible(false);
				}
			});
		}
		return okButton;
	}

	/**
	 * This method initializes cancelButton
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getCancelButton() {
		if (cancelButton == null) {
			cancelButton = new JButton();
			cancelButton.setText("Annulla");
			cancelButton.setPreferredSize(new Dimension(96, 26));
			cancelButton.addActionListener(new java.awt.event.ActionListener() {

				public void actionPerformed(java.awt.event.ActionEvent e) {
					dialogResult = DialogButton.CANCEL;
					setVisible(false);
				}
			});
		}
		return cancelButton;
	}

	public char[] getPin() {
		return panelsign.getJPin().getPassword();
	}

	public DialogButton getDialogResult() {
		return dialogResult;
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

	public PanelSign getPanelsign() {
		return panelsign;
	}

	public FileBean buildFileInfo(FileBean bean) throws Exception {
		logger.info("Elaborazione file " + bean.getFile());
		bean.setRootFileDirectory(bean.getFile().getParentFile());

		if (FileUtility.isPdf(bean.getFile())) {
			logger.info("Il file da firmare � PDF");
			bean.setIsPdf(true);
		} else {
			logger.info("Il file da firmare non � PDF");
			bean.setIsPdf(false);
		}

		try {
			bean.setTipoBusta(Factory.isSigned(bean.getFile()));
			logger.info("Tipo di busta del file: " + bean.getTipoBusta());
		} catch (Exception e) {
			logger.info("Errore " + e.getMessage());
			/// this.file = null;
		}

		// verifico se va fatta la conversione in pdf
		String tipoBustaConfigurata = PreferenceManager.getString(PreferenceKeys.PROPERTY_SIGN_ENVELOPE_TYPE);
		logger.info("Tipo di firma configurata: " + tipoBustaConfigurata);

		bean = FileUtility.valorizzaFile(tipoBustaConfigurata, bean.getTipoBusta() != null, bean);
		return bean;
	}

	public void closeFrame(boolean esitoUploadFile) {
		
		logger.info("Metodo closeFrame con esitoUploadFile: " + esitoUploadFile);
		// Termino il thread di rilevamento dispositivo firma
		if (panelsign.getThreadControl() != null && panelsign.getThreadControl().isAlive()) {
			panelsign.getThreadControl().stopThread();
		}
		// Finalizzo il Pkcs11
		Factory.finalizePkcs(Factory.getLastPkcs11());
		if (!esitoUploadFile) {
			module.setCallBackArgs(null);
			forcedClose();
		} else {
			logger.info("strToInvokeCommonName " + panelsign.getCommonNameArgs());
			if (panelsign.getCommonNameArgs() != null) {
				module.setCommonNameResult(panelsign.getCommonNameArgs());
			}
			logger.info("CallBack " + panelsign.getCallBack());
			if (panelsign.getCallBack() != null) {
				module.setCallBackFunction(panelsign.getCallBack());
				if (panelsign.getCallBackArgs() != null && panelsign.getCallBackArgs().size() > 0) {
					List<String> callBackArgsList = panelsign.getCallBackArgs();
					String callBackArgsString = "";
					for (String callBackArgs : callBackArgsList) {
						callBackArgsString += callBackArgs + "#@#";
					}
					logger.debug("callBackArgsString: " + callBackArgsString);
					module.setCallBackArgs(callBackArgsString);
				}
			}
			logger.info("AutoClosePostSign: " + fileOutputProvider.getAutoClosePostSign());
			if (fileOutputProvider.getAutoClosePostSign()) {
				module.setCallBackClose(fileOutputProvider.getCallBackAskForClose());
				this.dispose();
			} 
		}
	}

	/*
	 * Definizione di un metodo per definire il comportamento in caso di click sulla x della finestra di Windows
	 */
	public void forcedClose() {
		logger.info("Chiusura forzata");
		// Termino il thread di rilevamento dispositivo firma
		if (panelsign.getThreadControl() != null && panelsign.getThreadControl().isAlive()) {
			panelsign.getThreadControl().stopThread();
		}
		// Finalizzo il Pkcs11
		Factory.finalizePkcs(Factory.getLastPkcs11());
		String callBackCancel = PreferenceManager.getString(PreferenceKeys.PROPERTY_CALLBACKCANCEL);
		module.setCallBackCancel(callBackCancel);
		logger.info("callBackCancel " + callBackCancel);
		this.dispose();
	}

	public List<FileBean> getFileBeanList() {
		return fileBeanList;
	}

	public List<HashFileBean> getHashfilebean() {
		return hashfilebean;
	}

	public boolean isFileFirmatoPresente() {
		return fileFirmatoPresente;
	}

	public FileOutputProvider getFileOutputProvider() {
		return fileOutputProvider;
	}

	public CommonNameProvider getCommonNameProvider() {
		return commonNameProvider;
	}

	public void setCommonNameProvider(CommonNameProvider commonNameProvider) {
		this.commonNameProvider = commonNameProvider;
	}

	public void setFileFirmatoPresente(boolean fileFirmatoPresente) {
		this.fileFirmatoPresente = fileFirmatoPresente;
	}

	public boolean isFileTuttiFirmati() {
		return fileTuttiFirmati;
	}

	public void setFileTuttiFirmati(boolean fileTuttiFirmati) {
		this.fileTuttiFirmati = fileTuttiFirmati;
	}

	private static void writeSystemProperty() {
		Properties props = System.getProperties();
		Iterator<Object> itr = props.keySet().iterator();
		while (itr.hasNext()) {
			String key = (String) itr.next();
			logger.info(key + " - " + props.getProperty(key));
		}
	}

} // @jve:decl-index=0:visual-constraint="10,10"
