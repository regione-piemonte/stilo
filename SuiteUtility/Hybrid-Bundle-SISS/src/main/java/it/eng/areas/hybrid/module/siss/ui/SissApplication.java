package it.eng.areas.hybrid.module.siss.ui;

import it.eng.areas.hybrid.module.siss.SissMultiClientModule;
import it.eng.areas.hybrid.module.siss.bean.FileBean;
import it.eng.areas.hybrid.module.siss.bean.HashFileBean;
import it.eng.areas.hybrid.module.siss.fileInputProvider.FileInputProvider;
import it.eng.areas.hybrid.module.siss.fileInputProvider.FileInputResponse;
import it.eng.areas.hybrid.module.siss.fileOutputProvider.FileOutputProvider;
import it.eng.areas.hybrid.module.siss.messages.MessageKeys;
import it.eng.areas.hybrid.module.siss.messages.Messages;
import it.eng.areas.hybrid.module.siss.preferences.PreferenceKeys;
import it.eng.areas.hybrid.module.siss.preferences.PreferenceManager;
import it.eng.areas.hybrid.module.siss.util.FileUtility;
import it.eng.areas.hybrid.module.siss.util.TabType;
import it.eng.areas.hybrid.module.util.json.JSONArray;
import it.eng.areas.hybrid.module.util.json.JSONObject;
import it.eng.proxyselector.frame.ProxyFrame;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
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


public class SissApplication extends JFrame {
	
	public final static Logger logger = Logger.getLogger(SissApplication.class);
	
	private SissMultiClientModule module;
	
	public String[] tabconfig;
	
	private JScrollPane jScrollPane = null;
	private JPanel jContentPane = null;
	private JPanel menuPanel = null;
	private JMenu preferenceMenu;
	private JTabbedPane jTabbedPane = null;
	
	private PanelSign panelsign = null;
	
	private FileOutputProvider fileOutputProvider = null;
	private List<FileBean> fileBeanList = new ArrayList<FileBean>();
	private List<HashFileBean> hashfilebean = new ArrayList<HashFileBean>();
	
	private boolean fileFirmatoPresente = false;
	private boolean fileTuttiFirmati = true;
	
	/**
	 * @param owner
	 */
	public SissApplication(SissMultiClientModule module, JSONArray parameters) {
		//super(owner);
		this.module = module;
		
		hashfilebean = new ArrayList<HashFileBean>();
		
		PreferenceManager.initConfig(parameters);

		try{
			Messages.setBundle(ResourceBundle.getBundle("messages"));
		} catch(Exception e){
			logger.info("Exception "+e+" loading resource bundle.\n" +
					"Also check you have a file to support Locale="+java.util.Locale.getDefault(),e);
		}
		
		int mainPanelWidth = PreferenceManager.getInt( PreferenceKeys.MAINPANEL_WIDTH, 400 );
		int mainPanelHeight = PreferenceManager.getInt( PreferenceKeys.MAINPANEL_HEIGHT, 400 );
		logger.info("mainPanelWidth=" + mainPanelWidth + " mainPanelHeight=" + mainPanelHeight );
		this.setSize( mainPanelWidth, mainPanelHeight );
		
		tabconfig=PreferenceManager.getStringArray( PreferenceKeys.PROPERTY_TABS_CONFIG );
		logger.info( PreferenceKeys.PROPERTY_TABS_CONFIG + "=" + tabconfig );
		PreferenceManager.dumpActualConf();

		initialize();
		
		try{
			String fileInputProviderClass = null;
			try {
				fileInputProviderClass = PreferenceManager.getString( PreferenceKeys.PROPERTY_FILEINPUT_PROVIDER );
				logger.info("Verrà utilizzato il fileInputProvider " + fileInputProviderClass);
			} catch (Exception e) {
				logger.info("Errore - Non è stato possibile recuperare il fileInputProvider da utilizzare", e );
				return;
			}

			//FileInputProvider fileInputProvider = null;
			if( fileInputProviderClass==null ){
				logger.info("Errore - fileInputProvider non configurato.");
				return;
			}

			FileInputProvider fileInputProvider = null;
			try {
				Class cls = Class.forName(fileInputProviderClass);
				fileInputProvider = (FileInputProvider) cls.newInstance();
			} catch (InstantiationException e) {
				logger.error("Errore nel recupero del fileInputProvider: ", e );
				return;
			} catch (IllegalAccessException e) {
				logger.error("Errore nel recupero del fileInputProvider: ", e );
				return;
			} catch (ClassNotFoundException e) {
				logger.error("Errore nel recupero del fileInputProvider: ", e );
				return;
			}

			FileInputResponse fileInputProviderResponse;
			try {
				fileInputProviderResponse = fileInputProvider.getFileInputResponse();
				List<FileBean> fileBeanListInput = fileInputProviderResponse.getFileBeanList();
				if (fileBeanListInput != null && fileBeanListInput.size() > 0) {
					for(FileBean bean : fileBeanListInput){
						String beanFileName = bean.getFileName();
						bean = buildFileInfo( bean );
						if( bean!=null ){
							if( bean.getIsFirmato() )
								fileFirmatoPresente = true;
							fileTuttiFirmati = bean.getIsFirmato() && fileTuttiFirmati;
							fileBeanList.add( bean );
						} else {
							panelsign.getTextArea().append( beanFileName + " ignorato a causa di un errore nell'acquisizione\n");
						}
					}
				} else {
					List<HashFileBean> hashFiles = fileInputProviderResponse.getHashfilebean();
					logger.info("FILES " + hashFiles);
					if( hashFiles!=null ){
						for(int i=0;i<hashFiles.size();i++){
							HashFileBean fileBean = hashFiles.get( i );
							hashfilebean.add( fileBean );
						}
					}
				}
			
				panelsign.showFiles();
				panelsign.showConfigData();
			} catch (Exception e) {
				logger.error("Errore: " , e );
				JOptionPane.showMessageDialog(this,"Errore nel caricamento dei files","Errore", JOptionPane.ERROR_MESSAGE);
				return;
			}

			String fileOutputProviderClass = null;
			try {
				fileOutputProviderClass = PreferenceManager.getString( PreferenceKeys.PROPERTY_FILEOUTPUT_PROVIDER );
				logger.info("Verrà utilizzato il fileOutputProvider " + fileOutputProviderClass);
			} catch (Exception e) {
				logger.info("Errore - Non è stato possibile recuperare il fileOutputProvider da utilizzare", e );
			}
			if( fileOutputProviderClass==null ){
				logger.info("Errore - Non è stato configurato il fileOutputProvider da utilizzare");
				//return;
			} else {
				try {
					Class cls = Class.forName( fileOutputProviderClass );
					fileOutputProvider = (FileOutputProvider) cls.newInstance();
					fileOutputProvider.saveOutputParameter();
				} catch (InstantiationException e) {
					logger.error("Errore nel recupero del fileOutputProvider: ", e );
					return;
				} catch (IllegalAccessException e) {
					logger.error("Errore nel recupero del fileOutputProvider: ", e );
					return;
				}catch (ClassNotFoundException e) {
					logger.error("Errore nel recupero del fileOutputProvider: ", e );
					return;
				} catch (Exception e) {
					logger.error("Errore nel salvataggio dei parametri del fileOutputProvider: ", e );
					return;
				}
			}

		}catch(Exception ex){
			JOptionPane.showMessageDialog(this,"Impossibile leggere il file","Errore", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	private void initialize() {
		this.setSize(400, 500);
		this.setMinimumSize(new Dimension(400, 500));
		
		tabconfig=PreferenceManager.getStringArray( PreferenceKeys.PROPERTY_TABS_CONFIG );
		
		this.setContentPane(getJContentPane());
		//this.getRootPane().setDefaultButton(getOkButton());
	}
	
	private JScrollPane getJContentPane(){
		if (jContentPane == null) {
			GridBagLayout gridLayout = new GridBagLayout();
			GridBagConstraints c = new GridBagConstraints();
			
			jContentPane = new JPanel();
			jContentPane.setLayout(gridLayout);
			jScrollPane = new JScrollPane( jContentPane );
			
			c.gridy=0;
			c.gridx=0;
			c.anchor=GridBagConstraints.LINE_START;
			c.gridx=0;
			c.weightx=1.0;
			//c.weighty=1.0;
			c.insets = new Insets(2,10,2,10);
			JPanel menu = getMenuPanel();
			jContentPane.add(menu, c);
			c.gridy=1;
			c.insets = new Insets(2,10,2,10);
			c.anchor=GridBagConstraints.LINE_START;
			JTabbedPane tbs = getJTabbedPane();
			jContentPane.add(tbs, c);
		}
		return jScrollPane;
	}
	
	public JPanel getMenuPanel() {
		if( menuPanel==null ){
			menuPanel = new JPanel();
			JMenuBar menuBar = new JMenuBar(  );
			preferenceMenu = new JMenu( Messages.getMessage( MessageKeys.MENU_PREFERENCE_TITLE ) );
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
			JMenuItem rete = new JMenuItem( Messages.getMessage( MessageKeys.MENU_PREFERENCE_RETE_TITLE ) );
			rete.addActionListener( new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					String testUrl = PreferenceManager.getString( PreferenceKeys.PROPERTY_TEST_URL);
					new ProxyFrame(testUrl).setVisible(true);
				}
			});
			preferenceMenu.add(rete);
			menuBar.add( preferenceMenu );
	
			menuPanel.add( menuBar );
		}
		return menuPanel;
	}

	public JTabbedPane getJTabbedPane(){
		if (jTabbedPane == null) {
			jTabbedPane = new JTabbedPane();
			for(String tabview:tabconfig){
				TabType view = TabType.valueOf(tabview);
				switch(view){
					case FIRMA:
						panelsign = new PanelSign(this);
						jTabbedPane.addTab(Messages.getMessage( MessageKeys.TAB_FIRMA_TITLE ), panelsign);
						break;
				}
			}
		}
		return jTabbedPane;
	}
	
	public void closeFrame(){
		logger.info("AutoClosePostSign: " + fileOutputProvider.getAutoClosePostSign() );
		if( fileOutputProvider.getAutoClosePostSign() ){
			JSONObject jResult = new JSONObject();
			module.setSignResult(fileOutputProvider.getCallBackAskForClose());
			
			this.dispose();
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
		
//		try{
//			bean.setTipoBusta( Factory.isSigned( bean.getFile() ) );
//			logger.info("Tipo di busta del file: "+ bean.getTipoBusta());
//		} catch(Exception e){
//			logger.info("Errore " + e.getMessage() );
//			///this.file = null;
//		}

		//verifico se va fatta la conversione in pdf
		String tipoBustaConfigurata = PreferenceManager.getString( PreferenceKeys.PROPERTY_SIGN_ENVELOPE_TYPE );
		logger.info( "Tipo di firma configurata: " + tipoBustaConfigurata );
		
		bean = FileUtility.valorizzaFile( tipoBustaConfigurata, bean.getTipoBusta()!=null, bean );
		return bean;
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

	public void setFileFirmatoPresente(boolean fileFirmatoPresente) {
		this.fileFirmatoPresente = fileFirmatoPresente;
	}

	public boolean isFileTuttiFirmati() {
		return fileTuttiFirmati;
	}

	public void setFileTuttiFirmati(boolean fileTuttiFirmati) {
		this.fileTuttiFirmati = fileTuttiFirmati;
	}
	
	public JMenu getPreferenceMenu() {
		return preferenceMenu;
	}
	
	public FileOutputProvider getFileOutputProvider() {
		return fileOutputProvider;
	}

	public void setFileOutputProvider(FileOutputProvider fileOutputProvider) {
		this.fileOutputProvider = fileOutputProvider;
	}
	
	public PanelSign getPanelsign() {
		return panelsign;
	}
}  
