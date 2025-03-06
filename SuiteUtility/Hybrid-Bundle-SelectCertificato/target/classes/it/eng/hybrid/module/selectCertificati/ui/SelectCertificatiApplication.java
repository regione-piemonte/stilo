package it.eng.hybrid.module.selectCertificati.ui;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URI;
import java.net.URISyntaxException;
import java.security.Security;
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
import it.eng.hybrid.module.selectCertificati.SelectCertificatiModule;
import it.eng.hybrid.module.selectCertificati.messages.MessageKeys;
import it.eng.hybrid.module.selectCertificati.messages.Messages;
import it.eng.hybrid.module.selectCertificati.preferences.PreferenceKeys;
import it.eng.hybrid.module.selectCertificati.preferences.PreferenceManager;
import it.eng.hybrid.module.selectCertificati.response.GenericResponse;
import it.eng.hybrid.module.selectCertificati.tools.Factory;
import it.eng.proxyselector.frame.ProxyFrame;

public class SelectCertificatiApplication extends JFrame {

	public final static Logger logger = Logger.getLogger(SelectCertificatiApplication.class);

	private SelectCertificatiModule module;

	public static boolean DEBUGENABLED = false;
	public static String VERSIONE = "3";

	private JScrollPane jScrollPane = null;
	private JPanel jContentPane = null;
	private JPanel menuPanel = null;
	private JMenu preferenceMenu;
	private JMenu invioLogMenu;
	private JTabbedPane jTabbedPane = null;

	public String[] tabconfig;
	private PanelCertificati panelCert = null;

	//private CommonNameProvider commonNameProvider = null;
	private GenericResponse response = null;
	
	protected Object sincronizzato = new Object();

	private String baseurl = null;
	private String cookie = null;

	public void addsigner(String str) {
		sincronizzato.notify();
	}
	
	public SelectCertificatiApplication(SelectCertificatiModule module, JSONArray parameters) {
		// super(owner);
		this.module = module;
		
		// init della config
		PreferenceManager.initConfig(parameters);
		try {
			Messages.setBundle(ResourceBundle.getBundle("messages"));
		} catch (Exception e) {

		}
		
		try {
			DEBUGENABLED = PreferenceManager.getBoolean( PreferenceKeys.PROPERTY_DEBUGENABLED);
			System.out.println("Proprieta� " + PreferenceKeys.PROPERTY_DEBUGENABLED + ": " + DEBUGENABLED );
		} catch (Exception e) {
			logger.info("Errore nel recupero della Proprieta� " + PreferenceKeys.PROPERTY_DEBUGENABLED );
		}

		initialize();
		
		String responseClass = null;
		try {
			responseClass = PreferenceManager.getString( PreferenceKeys.PROPERTY_OUTPUT_PROVIDER );
			logger.info("Verra'� utilizzato l'outputProvider " + responseClass);
		} catch (Exception e) {
			logger.info("Errore - Non e' stato possibile recuperare l'outputProvider da utilizzare", e );
		}
		if( responseClass==null ){
			logger.info("Errore - Non e' stato configurato l'outputProvider da utilizzare");
			return;
		} else {
			try {
				Class cls = Class.forName( responseClass );
				response = (GenericResponse) cls.newInstance();
				response.saveOutputParameter();
			} catch (InstantiationException e) {
				logger.info("Errore nel recupero dell'otputProvider: ", e );
				return;
			} catch (IllegalAccessException e) {
				logger.info("Errore nel recupero dell'outputProvider: ", e );
				return;
			}catch (ClassNotFoundException e) {
				logger.info("Errore nel recupero dell'outputProvider: ", e );
				return;
			} catch (Exception e) {
				logger.info("Errore nel salvataggio dei parametri dell'outputProvider: ", e );
				return;
			}
		}
		
		Security.addProvider(new BouncyCastleProvider());
		
	}

	private void initialize() {
//		this.setSize(400, 500);
//		this.setMinimumSize(new Dimension(400, 500));

		tabconfig = PreferenceManager.getStringArray(PreferenceKeys.PROPERTY_TABS_CONFIG);
		logger.info( PreferenceKeys.PROPERTY_TABS_CONFIG + "=" + tabconfig );
		
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
//				case DRIVER:
//					jTabbedPane.addTab( Messages.getMessage( MessageKeys.TAB_DRIVER_TITLE ), new PanelDriver());
//					break;
				case CERTIFICATI:
					panelCert = new PanelCertificati(this);
					jTabbedPane.addTab(Messages.getMessage( MessageKeys.TAB_CERTIFICATI_TITLE ), panelCert);
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
						new PanelLog(invioLogPanelWidth, invioLogPanelHeight).show( );
					}
				});
				invioLogMenu.add( invioLog );
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

	/**
	 * Metodo che viene richiamato per terminare l'esecuzione del modulo
	 * Vengono settati i vari parametri di ritorno e le callback da utilizzare
	 * @param pin
	 * @param firmatario
	 * @param alias
	 */
	public void closeFrame(String pin, String firmatario, String alias, String metodo){
		
		logger.info( "Entro nel metodo closeFrame " );
		logger.info("autoClosePostSearch: " + response.getAutoClosePostSearch() );
		
		// Termino il thread di rilevamento dispositivo firma
		if (panelCert.getThreadControl() != null && panelCert.getThreadControl().isAlive()) {
			panelCert.getThreadControl().stopThread();
		}
		// Finalizzo il Pkcs11
		Factory.finalizePkcs(Factory.getLastPkcs11());
		
		//Se deve chiudersi automaticamente una volta terminata la ricerca
		if( response.getAutoClosePostSearch() ){

				//Prelevo il valore della callback di chiusura 
				String callBackAskForClose = response.getCallBackAskForClose();
				logger.info("callBackAskForClose: " + callBackAskForClose );
				
				if (callBackAskForClose!=null && !callBackAskForClose.equals("")){
					
					//Creo la stringa dei parametri che devono essere passati indietro
					String callBackArgsString = "";
					callBackArgsString += pin + "#@#";
					callBackArgsString += firmatario + "#@#";
					callBackArgsString += alias + "#@#";
					callBackArgsString += metodo + "#@#";
					
					//Settaggio dei parametri di ritorno
					module.setCallBackChiusuraArg(callBackArgsString);
					
					//Settaggio della callback di chiusura
					module.setCallBackChiusura(callBackAskForClose);

					this.dispose();
				}
		}
	}
	
	/**
	 * Metodo che definisce il comportamento della finestra una volta che si forza la chiusura della
	 * stessa cliccando sulla x di sistema
	 */
	public void forcedClose() {
		logger.info("Chiusura forzata");
		// Termino il thread di rilevamento dispositivo firma
		if (panelCert.getThreadControl() != null && panelCert.getThreadControl().isAlive()) {
			panelCert.getThreadControl().stopThread();
		}
		// Finalizzo il Pkcs11
		Factory.finalizePkcs(Factory.getLastPkcs11());
		String callBackCancel = PreferenceManager.getString(PreferenceKeys.PROPERTY_CALLBACKCANCEL);
		module.setCallBackCancel(callBackCancel);

		this.dispose();
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

	public GenericResponse getResponse() {
		return response;
	}

	public void setResponse(GenericResponse response) {
		this.response = response;
	}


}
