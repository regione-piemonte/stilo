package it.eng.client.applet;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.Security;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.swing.JApplet;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.RootPaneContainer;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.SerializationUtils;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

import com.nilo.plaf.nimrod.NimRODLookAndFeel;
import com.nilo.plaf.nimrod.NimRODTheme;

import it.eng.client.applet.cnProvider.CommonNameProvider;
import it.eng.client.applet.fileProvider.FileInputProvider;
import it.eng.client.applet.fileProvider.FileInputResponse;
import it.eng.client.applet.fileProvider.FileOutputProvider;
import it.eng.client.applet.i18N.MessageKeys;
import it.eng.client.applet.i18N.Messages;
import it.eng.client.applet.operation.AbstractSigner;
import it.eng.client.applet.operation.Factory;
import it.eng.client.applet.panel.InfoLog;
import it.eng.client.applet.panel.PanelDriver;
import it.eng.client.applet.panel.PanelLog;
import it.eng.client.applet.panel.PanelPreferenceLog;
import it.eng.client.applet.panel.PanelSign;
import it.eng.client.applet.panel.PreferenceFirmaMarcaPanel;
import it.eng.client.applet.util.FileUtility;
import it.eng.client.applet.util.PreferenceKeys;
import it.eng.client.applet.util.PreferenceManager;
import it.eng.common.LogWriter;
import it.eng.common.bean.FileBean;
import it.eng.common.bean.HashFileBean;
import it.eng.common.type.TabType;
import it.eng.proxyselector.frame.ProxyFrame;
import netscape.javascript.JSObject;

public class SmartCard implements ISmartCard{

	private static final String logFile = System.getProperty("user.home") + File.separator + "log" + File.separator + "signerAppletMulti.log";
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

	//component principale applet frame altro
	private RootPaneContainer mainComponent;

	public List<FileBean> getFileBeanList() {
		return fileBeanList;
	}

	/************ end *************/
	public List<HashFileBean> getHashfilebean() {
		return hashfilebean;
	}

	public void addsigner(String str) {
		this.bean = (HashFileBean)SerializationUtils.deserialize(Base64.decodeBase64(str));
		sincronizzato.notify();
	}

	public HashFileBean getBean() {
		return bean;
	}

	/**
	 * This is the xxx default constructor
	 */
	public SmartCard(RootPaneContainer comp){
		super();
		
		this.mainComponent=comp;
		
		hashfilebean = new ArrayList<HashFileBean>();
		try {
			File configdirectory = new File(AbstractSigner.dir+File.separator+AbstractSigner.cryptoConfig);
			if(!configdirectory.exists()){
				configdirectory.mkdirs();
			}

			File conffile = new File(AbstractSigner.dir+File.separator+AbstractSigner.cryptoConfigFile);
			//Copio il file di configurazione se non esiste
			if(!conffile.exists()){
				FileUtils.writeByteArrayToFile(conffile, IOUtils.toByteArray(this.getClass().getResourceAsStream("/it/eng/client/lookfeel/crypto.config")));
			}

			//Recupero la configurazione
			Properties prop = new Properties();
			prop.load(new FileInputStream(conffile));
			String template = prop.getProperty("template");				
			try {
				if(template!=null && !template.equals("")){
					nt = new NimRODTheme(template);
				}else{
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

		boolean logEnabled=PreferenceManager.enabled( PreferenceKeys.PROPERTY_LOGENABLED );
		if(logEnabled){
			boolean logFileEnabled = false;
			try {
				logFileEnabled = PreferenceManager.getBoolean( PreferenceKeys.PROPERTY_LOGFILEENABLED);
				System.out.println("Proprietà " + PreferenceKeys.PROPERTY_LOGFILEENABLED + ": " + logFileEnabled);
			} catch (Exception e) {
				LogWriter.writeLog("Errore nel recupero della Proprietà " + PreferenceKeys.PROPERTY_LOGFILEENABLED );
			}
			boolean logArrayEnabled = false;
			try {
				logArrayEnabled = PreferenceManager.getBoolean( PreferenceKeys.PROPERTY_LOGARRAYENABLED);
				System.out.println("Proprietà " + PreferenceKeys.PROPERTY_LOGARRAYENABLED + ": " + logArrayEnabled);
			} catch (Exception e) {
				LogWriter.writeLog("Errore nel recupero della Proprietà " + PreferenceKeys.PROPERTY_LOGARRAYENABLED );
			}
			if( logFileEnabled ){
				File fileLog = new File( logFile );
				if( fileLog.exists() ) 
					fileLog.delete();
				LogWriter.log_name=logFile;
			}
			if( logArrayEnabled ){
				LogWriter.logArray = new ArrayList<String>();
			}
			LogWriter.initLog();
		}

		try {
			DEBUGENABLED = PreferenceManager.getBoolean( PreferenceKeys.PROPERTY_DEBUGENABLED);
			System.out.println("Proprietà " + PreferenceKeys.PROPERTY_DEBUGENABLED + ": " + DEBUGENABLED );
		} catch (Exception e) {
			LogWriter.writeLog("Errore nel recupero della Proprietà " + PreferenceKeys.PROPERTY_DEBUGENABLED );
		}

		tabconfig=PreferenceManager.getStringArray( PreferenceKeys.PROPERTY_TABS_CONFIG );
		LogWriter.writeLog( PreferenceKeys.PROPERTY_TABS_CONFIG + "=" + tabconfig );
		PreferenceManager.dumpActualConf();
		
		this.mainComponent.setContentPane( getJContentPane() );
			
		try{
			String fileInputProviderClass = null;
			try {
				fileInputProviderClass = PreferenceManager.getString( PreferenceKeys.PROPERTY_FILEINPUT_PROVIDER );
				LogWriter.writeLog("Verra' utilizzato il fileInputProvider " + fileInputProviderClass);
			} catch (Exception e) {
				LogWriter.writeLog("Errore - Non e' stato possibile recuperare il fileInputProvider da utilizzare", e );
				return;
			}

			//FileInputProvider fileInputProvider = null;
			if( fileInputProviderClass==null ){
				LogWriter.writeLog("Errore - fileInputProvider non configurato.");
				return;
			}

			FileInputProvider fileInputProvider = null;
			try {
				Class cls = Class.forName(fileInputProviderClass);
				fileInputProvider = (FileInputProvider) cls.newInstance();
			} catch (InstantiationException e) {
				LogWriter.writeLog("Errore nel recupero del fileInputProvider: ", e );
				return;
			} catch (IllegalAccessException e) {
				LogWriter.writeLog("Errore nel recupero del fileInputProvider: ", e );
				return;
			} catch (ClassNotFoundException e) {
				LogWriter.writeLog("Errore nel recupero del fileInputProvider: ", e );
				return;
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
					for(FileBean bean : fileBeanListInput){
						String beanFileName = bean.getFileName();
						bean = buildFileInfo( bean );
						if( bean!=null ){
							if( bean.getIsFirmato()!=null && bean.getIsFirmato() )
								fileFirmatoPresente = true;
							fileTuttiFirmati = bean.getIsFirmato() && fileTuttiFirmati;
							fileBeanList.add( bean );
						} else {
							panelsign.getTextArea().append( beanFileName + " ignorato a causa di un errore nell'acquisizione\n");
						}
					}
				} else {
					List<HashFileBean> hashFiles = fileInputProviderResponse.getHashfilebean();
					//LogWriter.writeLog("FILES " + hashFiles);
					if( hashFiles!=null ){
						for(int i=0;i<hashFiles.size();i++){
							HashFileBean fileBean = hashFiles.get( i );
							hashfilebean.add( fileBean );
						}
						LogWriter.writeLog("Ricevute " + hashFiles.size() + " impronte da firmare" );
					}
				}

				panelsign.showFiles();
				panelsign.showConfigData();
				
				panelsign.firmaAutomatica();
			} catch (Exception e) {
				LogWriter.writeLog("Errore: " , e );
				JOptionPane.showMessageDialog(mainComponent.getContentPane(),"Errore nel caricamento dei files","Errore", JOptionPane.ERROR_MESSAGE);
				return;
			}

			String fileOutputProviderClass = null;
			try {
				fileOutputProviderClass = PreferenceManager.getString( PreferenceKeys.PROPERTY_FILEOUTPUT_PROVIDER );
				LogWriter.writeLog("Verrà utilizzato il fileOutputProvider " + fileOutputProviderClass);
			} catch (Exception e) {
				LogWriter.writeLog("Errore - Non è stato possibile recuperare il fileOutputProvider da utilizzare", e );
			}
			if( fileOutputProviderClass==null ){
				LogWriter.writeLog("Errore - Non è stato configurato il fileOutputProvider da utilizzare");
				//return;
			} else {
				try {
					Class cls = Class.forName( fileOutputProviderClass );
					fileOutputProvider = (FileOutputProvider) cls.newInstance();
					if( mainComponent instanceof JApplet )
						fileOutputProvider.saveOutputParameter((JApplet)mainComponent);
					else 
						fileOutputProvider.saveOutputParameter(null);
				} catch (InstantiationException e) {
					LogWriter.writeLog("Errore nel recupero del fileOutputProvider: ", e );
					return;
				} catch (IllegalAccessException e) {
					LogWriter.writeLog("Errore nel recupero del fileOutputProvider: ", e );
					return;
				}catch (ClassNotFoundException e) {
					LogWriter.writeLog("Errore nel recupero del fileOutputProvider: ", e );
					return;
				} catch (Exception e) {
					LogWriter.writeLog("Errore nel salvataggio dei parametri del fileOutputProvider: ", e );
					return;
				}
			}
			String commonNameProviderClass = null;
			try {
				commonNameProviderClass = PreferenceManager.getString( PreferenceKeys.PROPERTY_COMMON_NAME_PROVIDER );
				if (commonNameProviderClass != null && !commonNameProviderClass.trim().equals("")){
					Class lClass = Class.forName(PreferenceManager.getString(PreferenceKeys.PROPERTY_COMMON_NAME_PROVIDER));
					commonNameProvider = (CommonNameProvider) lClass.newInstance();
					if( mainComponent instanceof JApplet )
						commonNameProvider.saveOutputParameter((JApplet)mainComponent);
				}
			} catch (Exception e) {
				LogWriter.writeLog("Errore nel recupero del commonNameProvider: ", e );
				return;
			}

		}catch(Exception ex){
			JOptionPane.showMessageDialog(mainComponent.getContentPane(),"Impossibile leggere il file","Errore", JOptionPane.ERROR_MESSAGE);
		}
		Security.addProvider(new BouncyCastleProvider());

	}
	public void clearHashs(){
		hashfilebean.clear();
	}

	//non hash mode
	public FileBean buildFileInfo(FileBean bean) throws Exception{
		LogWriter.writeLog("Elaborazione file " + bean.getFile() );
		bean.setRootFileDirectory( bean.getFile().getParentFile() );

		if(FileUtility.isPdf( bean.getFile() )){
			LogWriter.writeLog("Il file da firmare è PDF");
			bean.setIsPdf( true);
		} else {
			LogWriter.writeLog("Il file da firmare non è PDF");
			bean.setIsPdf( false );
		}

		//if( !bean.getIsFirmaPresente() ){
			try{
				bean.setTipoBusta( Factory.isSigned( bean.getFile() ) );
				LogWriter.writeLog("Tipo di busta del file: "+ bean.getTipoBusta());
			} catch(Exception e){
				LogWriter.writeLog("Errore " + e.getMessage() );
				///this.file = null;
			}
		//} else {
		//	LogWriter.writeLog("Non ricavo l'indicazione della firma presente nel file in quanto dato passato dal chiamante");
		//}

		//verifico se va fatta la conversione in pdf
		String tipoBustaConfigurata = PreferenceManager.getString( PreferenceKeys.PROPERTY_SIGN_ENVELOPE_TYPE );
		LogWriter.writeLog( "Tipo di firma configurata: " + tipoBustaConfigurata );

		bean = FileUtility.valorizzaFile( tipoBustaConfigurata, bean.getTipoBusta()!=null, bean );
		return bean;
	}

	public void close(){
		
		LogWriter.writeLog( "Entro nel metodo close " );
		
		if( mainComponent instanceof JApplet ){
			JApplet lApplet = (JApplet)mainComponent;

			String callback = lApplet.getParameter("callbackToCall");
			if (callback!=null ){ 
				String lStrToInvoke = "javascript:" + callback + "('prova');";
				System.out.println("Invoco " +lStrToInvoke);
				try {
					lApplet.getAppletContext().showDocument(new URL(lStrToInvoke));
				} catch (MalformedURLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				lApplet.stop();
				lApplet.destroy();
				System.exit(1);
			}
		}
		
		if( mainComponent instanceof JApplet )
			((JApplet)mainComponent).destroy();
		else if( mainComponent instanceof JFrame )
			((JFrame)mainComponent).dispose();		
	}

	/**
	 * This method initializes jContentPane
	 * 
	 * @return javax.swing.JPanel
	 */
	private JScrollPane getJContentPane(){
		
		LogWriter.writeLog("Entro in getJContentPane ");
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
							new PreferenceFirmaMarcaPanel(preferenceFirmaMarcaPanelWidth, preferenceFirmaMarcaPanelHeight).show( mainComponent.getContentPane(), getPanelsign() );
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
							new PanelPreferenceLog(preferenceInvioLogPanelWidth, preferenceInvioLogPanelHeight).show( mainComponent.getContentPane() );
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
							new ProxyFrame(testUrl).setVisible(true);
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
						new PanelLog(invioLogPanelWidth, invioLogPanelHeight).show( mainComponent.getContentPane() );
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
						new InfoLog().show( mainComponent.getContentPane(), VERSIONE );
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
			//								LogWriter.writeLog("Utilizzo il nome convertito " + getFileNameConvertito() );
			//								fop.saveOutputFile(in, getFileNameConvertito(), (String)panelsign.getTipoFirma().getSelectedItem() );
			//							} else if(PreferenceManager.getString( PreferenceKeys.PROPERTY_FILENAME )!=null){
			//								LogWriter.writeLog("Utilizzo il nome dalla preference " + PreferenceManager.getString( PreferenceKeys.PROPERTY_FILENAME ) );
			//								fop.saveOutputFile(in, PreferenceManager.getString( PreferenceKeys.PROPERTY_FILENAME ), (String)panelsign.getTipoFirma().getSelectedItem());
			//							} else {
			//								LogWriter.writeLog("Utilizzo il nome del file " + fileInput.getName() );
			//								fop.saveOutputFile(in, fileInput.getName(), (String)panelsign.getTipoFirma().getSelectedItem());
			//							}
			//						} catch (FileNotFoundException e) {
			//							LogWriter.writeLog("Errore", e);
			//						} catch (Exception e) {
			//							LogWriter.writeLog("Errore", e);
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

	public void closeApplet(){
		
		LogWriter.writeLog( "Entro nel metodo closeApplet " );
		
		LogWriter.writeLog("AutoClosePostSign: " + fileOutputProvider.getAutoClosePostSign() );
		if( fileOutputProvider.getAutoClosePostSign() ){
			if( mainComponent instanceof JApplet ){
				JApplet lApplet = (JApplet)mainComponent;

				String callBackAskForClose = fileOutputProvider.getCallBackAskForClose();
				LogWriter.writeLog("callBackAskForClose: " + callBackAskForClose );
				if (callBackAskForClose!=null && !callBackAskForClose.equals("")){
					String lStrToInvoke = "javascript:" + callBackAskForClose + "();";
					LogWriter.writeLog("Invoco " +lStrToInvoke);
					JSObject.getWindow(lApplet).eval( lStrToInvoke );

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

	public void downloadfile(HashFileBean file){
	}


	public PanelSign getPanelsign() {
		return panelsign;
	}

	public void selectedTab(int index) {
		getJTabbedPane().setSelectedIndex(index);
	}


	public NimRODLookAndFeel getLookFeel() {
		return nf;
	}

	public FileOutputProvider getFileOutputProvider() {
		return fileOutputProvider;
	}

	public void setFileOutputProvider(FileOutputProvider fileOutputProvider) {
		this.fileOutputProvider = fileOutputProvider;
	}

	public RootPaneContainer getMainComponent() {
		return mainComponent;
	}

	public void setMainComponent(RootPaneContainer mainComponent) {
		this.mainComponent = mainComponent;
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



}
