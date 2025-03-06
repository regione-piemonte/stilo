package it.eng.hybrid.module.firmaCertificato.ui;

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

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;

import org.apache.log4j.Logger;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

import it.eng.areas.hybrid.module.util.json.JSONArray;
import it.eng.hybrid.module.firmaCertificato.FirmaCertificatoModule;
import it.eng.hybrid.module.firmaCertificato.bean.FileBean;
import it.eng.hybrid.module.firmaCertificato.bean.HashFileBean;
import it.eng.hybrid.module.firmaCertificato.cnProvider.CommonNameProvider;
import it.eng.hybrid.module.firmaCertificato.inputFileProvider.FileInputProvider;
import it.eng.hybrid.module.firmaCertificato.inputFileProvider.FileInputResponse;
import it.eng.hybrid.module.firmaCertificato.messages.MessageKeys;
import it.eng.hybrid.module.firmaCertificato.messages.Messages;
import it.eng.hybrid.module.firmaCertificato.outputFileProvider.FileOutputProvider;
import it.eng.hybrid.module.firmaCertificato.preferences.PreferenceKeys;
import it.eng.hybrid.module.firmaCertificato.preferences.PreferenceManager;
import it.eng.hybrid.module.firmaCertificato.tool.Factory;
import it.eng.hybrid.module.firmaCertificato.util.FileUtility;
import it.eng.proxyselector.frame.ProxyFrame;

public class FirmaCertificatoApplication extends JFrame {
	
	public final static Logger logger = Logger.getLogger(FirmaCertificatoApplication.class);

	private FirmaCertificatoModule module;
	
	public static boolean DEBUGENABLED = false;
	public static String VERSIONE = "3";

	private JScrollPane jScrollPane = null;
	private JPanel jContentPane = null;
	private JPanel menuPanel = null;
	private JMenu preferenceMenu;
	private JMenu invioLogMenu;
	private JTabbedPane jTabbedPane = null;

	public String[] tabconfig;
	private PanelSign panelsign = null;

	private FileOutputProvider fileOutputProvider = null;

	private CommonNameProvider commonNameProvider = null;

	private HashFileBean bean; 

	protected Object sincronizzato = new Object();

	private String baseurl = null;
	private String cookie = null;

	private List<FileBean> fileBeanList = new ArrayList<FileBean>();
	private List<HashFileBean> hashfilebean = new ArrayList<HashFileBean>();

	private boolean fileFirmatoPresente = false;
	private boolean fileTuttiFirmati = true;
	
	public enum DialogButton {
		OK, CANCEL
	};
	
	private DialogButton dialogResult;
	
	public FirmaCertificatoApplication(FirmaCertificatoModule module, JSONArray parameters) {
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
				FileInputProvider fileInputProvider = null;
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

				FileInputResponse fileInputProviderResponse;
				String pin = null;
				String alias = null;
				String metodoFirma = null;
				try {
					fileInputProviderResponse = fileInputProvider.getFileInputResponse();
					pin = fileInputProvider.getPin();
					alias = fileInputProvider.getAlias();
					metodoFirma = fileInputProvider.getMetodoFirma();
					
					panelsign.getjPin().setText(pin);
					panelsign.setAlias(alias);
					panelsign.setMetodoFirma(metodoFirma);
					
					
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
					
					panelsign.firmaAutomatica();
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
		} catch (Exception ex) {
			// JOptionPane.showMessageDialog(mainComponent.getContentPane(),"Impossibile leggere il file","Errore", JOptionPane.ERROR_MESSAGE);
			initOK = false;
		}

		Security.addProvider(new BouncyCastleProvider());
	}
	
	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
//		this.setSize(400, 500);
//		this.setMinimumSize(new Dimension(400, 500));

		tabconfig = PreferenceManager.getStringArray(PreferenceKeys.PROPERTY_TABS_CONFIG);

		this.setContentPane(getJContentPane());
	}
	
	/**
	 * This method initializes jContentPane
	 * 
	 * @return javax.swing.JPanel
	 */
	private JScrollPane getJContentPane(){
		
		if (jContentPane == null) {
			GridBagLayout gridLayout = new GridBagLayout();
			GridBagConstraints c = new GridBagConstraints();
			
			jContentPane = new JPanel();
			jContentPane.setLayout(gridLayout);

//			c.anchor=GridBagConstraints.LINE_START; 
			c.fill=GridBagConstraints.BOTH;
			c.anchor = GridBagConstraints.CENTER; 
		
			gridLayout.setConstraints(jContentPane, c);

			jScrollPane = new JScrollPane( jContentPane );

			c.gridy=0;
			c.gridx=0;

			c.anchor=GridBagConstraints.CENTER; 
			c.fill=GridBagConstraints.BOTH;

			c.gridx=0;
			c.weightx=1.0;
//			c.weighty=1.0;
			c.insets = new Insets(2,10,2,10);
			JPanel menu = getMenuPanel();
			jContentPane.add(menu, c);
			c.gridy=1;
			c.insets = new Insets(2,10,2,10);
			c.anchor=GridBagConstraints.LINE_START;

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
	public JTabbedPane getJTabbedPane(){
		if (jTabbedPane == null) {
			jTabbedPane = new JTabbedPane();
			for(String tabview:tabconfig){
				TabType view = TabType.valueOf(tabview);
				switch(view){
				case DRIVER:
					jTabbedPane.addTab( Messages.getMessage( MessageKeys.TAB_DRIVER_TITLE ), new PanelDriver());
					break;
				case FIRMA:
					panelsign = new PanelSign(this);
					jTabbedPane.addTab(Messages.getMessage( MessageKeys.TAB_FIRMA_TITLE ), panelsign);
					break;
				}
			}
		}
		return jTabbedPane;
	}

	public JPanel getMenuPanel() {
		if( menuPanel==null ){
			menuPanel = new JPanel();
			JMenuBar menuBar = new JMenuBar(  );

			boolean menuConfigurazioniEnabled = PreferenceManager.getBoolean( PreferenceKeys.MENU_CONFIGURAZIONI_ENABLED );
			if( menuConfigurazioniEnabled ){
				preferenceMenu = new JMenu( Messages.getMessage( MessageKeys.MENU_PREFERENCE_TITLE ) );

				boolean configurazioniFirmaMarcaEnabled = PreferenceManager.getBoolean( PreferenceKeys.PREFERENCEFIRMAMARCAPANEL_ENABLED );
				if( configurazioniFirmaMarcaEnabled ){
					JMenuItem firmaMarca = new JMenuItem( Messages.getMessage( MessageKeys.MENU_PREFERENCE_FIRMAMARCA_TITLE ) );
					firmaMarca.addActionListener( new ActionListener() {
						@Override
						public void actionPerformed(ActionEvent e) {
							int preferenceFirmaMarcaPanelWidth = PreferenceManager.getInt( PreferenceKeys.PREFERENCEFIRMAMARCAPANEL_WIDTH, 400 );
							int preferenceFirmaMarcaPanelHeight = PreferenceManager.getInt( PreferenceKeys.PREFERENCEFIRMAMARCAPANEL_HEIGHT, 400 );
							new PreferenceFirmaMarcaPanel(preferenceFirmaMarcaPanelWidth, preferenceFirmaMarcaPanelHeight).show( panelsign );
						}
					});
					preferenceMenu.add(firmaMarca);
				}
				boolean configurazioniLogEnabled = PreferenceManager.getBoolean( PreferenceKeys.PREFERENCEINVIOLOGPANEL_ENABLED );
				if( configurazioniLogEnabled ){
					JMenuItem log = new JMenuItem( Messages.getMessage( MessageKeys.MENU_PREFERENCE_LOG_TITLE ) );
					log.addActionListener( new ActionListener() {
						@Override
						public void actionPerformed(ActionEvent e) {
							int preferenceInvioLogPanelWidth = PreferenceManager.getInt( PreferenceKeys.PREFERENCEINVIOLOGPANEL_WIDTH, 400 );
							int preferenceInvioLogPanelHeight = PreferenceManager.getInt( PreferenceKeys.PREFERENCEINVIOLOGPANEL_HEIGHT, 400 );
							new PanelPreferenceLog(preferenceInvioLogPanelWidth, preferenceInvioLogPanelHeight).show( );
						}
					});
					preferenceMenu.add(log);
				}
				boolean configurazioniReteEnabled = PreferenceManager.getBoolean( PreferenceKeys.PREFERENCERETEPANEL_ENABLED );
				if( configurazioniReteEnabled ){
					JMenuItem rete = new JMenuItem( Messages.getMessage( MessageKeys.MENU_PREFERENCE_RETE_TITLE ) );
					rete.addActionListener( new ActionListener() {
						@Override
						public void actionPerformed(ActionEvent e) {
							String testUrl = PreferenceManager.getString( PreferenceKeys.PROPERTY_TEST_URL);
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
				menuBar.add( preferenceMenu );
			}

			boolean menuLogEnabled = PreferenceManager.getBoolean( PreferenceKeys.MENU_LOG_ENABLED );
			if( menuLogEnabled ){
				invioLogMenu = new JMenu( Messages.getMessage( MessageKeys.MENU_INVIOLOG_TITLE ) );
				JMenuItem invioLog = new JMenuItem( Messages.getMessage( MessageKeys.MENU_INVIOLOG_TITLE ) );
				invioLog.addActionListener( new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						int invioLogPanelWidth = PreferenceManager.getInt( PreferenceKeys.INVIOLOGPANEL_WIDTH, 400 );
						int invioLogPanelHeight = PreferenceManager.getInt( PreferenceKeys.INVIOLOGPANEL_HEIGHT, 400 );
						new PanelLog(invioLogPanelWidth, invioLogPanelHeight).show();
					}
				});
				invioLogMenu.add( invioLog );
				menuBar.add(invioLogMenu);
			}
			if( DEBUGENABLED ){
				JMenu infoMenu = new JMenu( Messages.getMessage( MessageKeys.MENU_INFO_TITLE ) );
				JMenuItem info = new JMenuItem( Messages.getMessage( MessageKeys.MENU_INFO_TITLE ) );
				info.addActionListener( new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						new InfoLog().show( VERSIONE );
					}
				});
				infoMenu.add( info );
				menuBar.add(infoMenu);
			}
			//			ExitMenu lExitMenu = new ExitMenu( Messages.getMessage( MessageKeys.MENU_EXIT_TITLE ) ){
			//				@Override
			//				protected boolean otherAction() {
			//					FileOutputProvider fop = getFileOutputProvider();
			//					File fileInput = getFile();
			//					if(fop!=null && fileInput!=null && getPanelsign().getOutputFile()!=null){
			//						InputStream in;
			//						try {
			//							in = new FileInputStream( getPanelsign().getOutputFile() );
			//							if( getFileNameConvertito()!=null ){
			//								logger.info("Utilizzo il nome convertito " + getFileNameConvertito() );
			//								fop.saveOutputFile(in, getFileNameConvertito(), (String)panelsign.getTipoFirma().getSelectedItem() );
			//							} else if(PreferenceManager.getString( PreferenceKeys.PROPERTY_FILENAME )!=null){
			//								logger.info("Utilizzo il nome dalla preference " + PreferenceManager.getString( PreferenceKeys.PROPERTY_FILENAME ) );
			//								fop.saveOutputFile(in, PreferenceManager.getString( PreferenceKeys.PROPERTY_FILENAME ), (String)panelsign.getTipoFirma().getSelectedItem());
			//							} else {
			//								logger.info("Utilizzo il nome del file " + fileInput.getName() );
			//								fop.saveOutputFile(in, fileInput.getName(), (String)panelsign.getTipoFirma().getSelectedItem());
			//							}
			//						} catch (FileNotFoundException e) {
			//							logger.info("Errore", e);
			//						} catch (Exception e) {
			//							logger.info("Errore", e);
			//						}
			//					}
			//					
			//					FileUtility.deleteTempDirectory( getRootFileDirectory() );
			//					return true;
			//				}
			//			};
			//			menuBar.add(lExitMenu );	
			menuPanel.add( menuBar );
		}
		return menuPanel;
	}
	
	public FileOutputProvider getFileOutputProvider() {
		return fileOutputProvider;
	}

	public void setFileOutputProvider(FileOutputProvider fileOutputProvider) {
		this.fileOutputProvider = fileOutputProvider;
	}

	public boolean isFileFirmatoPresente() {
		return fileFirmatoPresente;
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

	public CommonNameProvider getCommonNameProvider() {
		return commonNameProvider;
	}

	public void setCommonNameProvider(CommonNameProvider commonNameProvider) {
		this.commonNameProvider = commonNameProvider;
	}
	
	public List<FileBean> getFileBeanList() {
		return fileBeanList;
	}
	
	public List<HashFileBean> getHashfilebean() {
		return hashfilebean;
	}
	
	public PanelSign getPanelsign() {
		return panelsign;
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
	
	public DialogButton getDialogResult() {
		return dialogResult;
	}
	
	private static void writeSystemProperty() {
		Properties props = System.getProperties();
		Iterator<Object> itr = props.keySet().iterator();
		while (itr.hasNext()) {
			String key = (String) itr.next();
			logger.info(key + " - " + props.getProperty(key));
		}
	}
	
	public FileBean buildFileInfo(FileBean bean) throws Exception{
		logger.info("Elaborazione file " + bean.getFile() );
		bean.setRootFileDirectory( bean.getFile().getParentFile() );

		if(FileUtility.isPdf( bean.getFile() )){
			logger.info("Il file da firmare è PDF");
			bean.setIsPdf( true);
		} else {
			logger.info("Il file da firmare non è PDF");
			bean.setIsPdf( false );
		}

		//if( !bean.getIsFirmaPresente() ){
			try{
				bean.setTipoBusta( Factory.isSigned( bean.getFile() ) );
				logger.info("Tipo di busta del file: "+ bean.getTipoBusta());
			} catch(Exception e){
				logger.info("Errore " + e.getMessage() );
				///this.file = null;
			}
		//} else {
		//	logger.info("Non ricavo l'indicazione della firma presente nel file in quanto dato passato dal chiamante");
		//}

		//verifico se va fatta la conversione in pdf
		String tipoBustaConfigurata = PreferenceManager.getString( PreferenceKeys.PROPERTY_SIGN_ENVELOPE_TYPE );
		logger.info( "Tipo di firma configurata: " + tipoBustaConfigurata );

		bean = FileUtility.valorizzaFile( tipoBustaConfigurata, bean.getTipoBusta()!=null, bean );
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
		if( !esitoUploadFile ){
			module.setCallBackArgs(null);
			forcedClose();
		} else {
			logger.info("strToInvokeCommonName " + panelsign.getCommonNameArgs());
			if (panelsign.getCommonNameArgs() != null) {
				module.setCommonNameResult(panelsign.getCommonNameArgs());
			}
			logger.info("AutoClosePostSign: " + fileOutputProvider.getAutoClosePostSign() );
			if (fileOutputProvider.getAutoClosePostSign()) {
				//Prelevo i valori delle callback e dei parametri impostati all'interno del file output provider
				String callBackFunction = fileOutputProvider.getCallback();
				String callBackClose = fileOutputProvider.getCallBackAskForClose();
				if (callBackFunction != null) {
					//Stampa dei valori
					logger.info("callBackFunction: " + callBackFunction );
					//Imposto la funzione che dev'essere chiamata per inizializzare le variabili per l'upload del file
					module.setCallBackFunction(callBackFunction);
					List<String> callBackArgs = panelsign.getCallBackArgs();
					if (callBackArgs != null && callBackArgs.size() > 0) {
						List<String> callBackArgsList = callBackArgs;
						String callBackArgsString = "";
						for (String callBackArgsItem : callBackArgsList) {
							callBackArgsString += callBackArgsItem + "#@#";
						}
						//Imposto il parametro di ritorno (ovvero la stringa contente i nomi dei file)
						module.setCallBackArgs(callBackArgsString);
						logger.info("callBackArgs: " + callBackArgsString );
					}
				}
				if (callBackClose != null) {
					//Stampa dei valori
					logger.info("callBackClose: " + callBackClose );
					//Imposto il nome della callback da chiamare una volta terminato l'upload
					module.setCallBackClose(callBackClose);
					//Chiusura della finestra
					this.dispose();
				}
			}
		}
	}
	
	public char[] getPin() {
		return panelsign.getJPin().getPassword();
	}
	
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
}
