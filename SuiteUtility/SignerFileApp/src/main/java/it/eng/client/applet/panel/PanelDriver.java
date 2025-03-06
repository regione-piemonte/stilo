package it.eng.client.applet.panel;

import it.eng.client.applet.model.DriverTableModel;
import it.eng.client.applet.model.listener.DriverKeyListener;
import it.eng.client.applet.operation.AbstractSigner;
import it.eng.common.DllFilter;
import it.eng.common.LogWriter;

import java.awt.Rectangle;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Properties;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.UIManager;

public class PanelDriver extends JPanel {

	private static final long serialVersionUID = 1L;
	private JButton jButtonAddDriver = null;
	private JScrollPane jScrollPane = null;
	private JTable jTableDriver = null;
	private DriverTableModel model = new DriverTableModel();
	
	
	private void reloadFile(){
		
		model.clear();
		
		//Carico i driver presenti
		File directory = new File(AbstractSigner.dir + File.separator + AbstractSigner.cryptodll);
		LogWriter.writeLog("Carico i files dalla directory " + directory );
		
		if(!directory.exists()){
			directory.mkdirs();
		}
		File[] drivers = directory.listFiles();
		LogWriter.writeLog("Trovati " + drivers.length + " files" );
		
		for(File driver:drivers){
			if(driver.isFile()){
				model.getDriver().add( driver );
			}
		}
		
		//Aggiungo i driver esterni
		File config = new File( AbstractSigner.dir + File.separator + AbstractSigner.cryptoConfigFile );
		LogWriter.writeLog("Controllo se il file esiste:" + config.getAbsolutePath() );
		if( config.exists() ){
			LogWriter.writeLog("Il file esiste");
			Properties prop = new Properties();
			try {
				prop.load(new FileInputStream(config));
				if(prop.containsKey(AbstractSigner.externalDllPath)){
					String[] externalDLL = prop.getProperty(AbstractSigner.externalDllPath).split(";");
					System.out.println("Carico le proprietà:"+externalDLL.length);
					for(String dll:externalDLL){
						File dllfile = new File(dll);
						System.out.println("Carico le proprietà:"+dllfile.getAbsolutePath());
						if(dllfile.exists()){
							model.getDriver().add(dllfile);
						}
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
				
			} 
		}
	}
	
	/**
	 * This is the default constructor
	 */
	public PanelDriver() {
		super();
		initialize();
		reloadFile();	
		
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setSize(300, 170);
		this.setLayout(null);
		this.add(getJButtonAddDriver(), null);
		this.add(getJScrollPane(), null);
		this.addComponentListener(new java.awt.event.ComponentAdapter() {
			public void componentShown(java.awt.event.ComponentEvent e) {
				//Ricarico i file 
				reloadFile();
			}
		});
	}

	/**
	 * This method initializes jButtonAddDriver	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getJButtonAddDriver() {
		if (jButtonAddDriver == null) {
			jButtonAddDriver = new JButton();
			jButtonAddDriver.setBounds(new Rectangle(20, 20, 116, 21));
			jButtonAddDriver.setText("Driver");
			jButtonAddDriver.setToolTipText("Aggiungi driver PKCS11");
			jButtonAddDriver.setIcon(UIManager.getIcon( "Tree.openIcon"));
			jButtonAddDriver.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					JFileChooser chooser = new JFileChooser();
					chooser.setFileFilter(new DllFilter());
					chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
					int i = chooser.showOpenDialog(PanelDriver.this);
					if(i == JFileChooser.APPROVE_OPTION){
						File file = chooser.getSelectedFile();
						try {
							//Inserisco il dirver nella file di properties
							File config = new File(AbstractSigner.dir+File.separator+AbstractSigner.cryptoConfig+File.separator+"crypto.config");
							if(config.exists()){
								Properties prop = new Properties();
								try {
									prop.load(new FileInputStream(config));
									String externalDLL = "";
									if(prop.containsKey(AbstractSigner.externalDllPath)){
										externalDLL = prop.getProperty(AbstractSigner.externalDllPath);
									}
									externalDLL += ";"+file.getAbsolutePath();
									prop.setProperty(AbstractSigner.externalDllPath, externalDLL);
									prop.store(new FileOutputStream(config), "Driver Esterni");
								} catch (Exception ex) {} 
							}
											
							//Lo carico nella tabella
							PanelDriver.this.model.getDriver().add(file);		
							PanelDriver.this.model.fireTableDataChanged();
							
							JOptionPane.showMessageDialog(PanelDriver.this,"Driver caricato con successo!","Informazione", JOptionPane.INFORMATION_MESSAGE);
						} catch (Exception er) {
							JOptionPane.showMessageDialog(PanelDriver.this,er.getMessage(),"Errore driver", JOptionPane.ERROR_MESSAGE);
						}						
					}
				}
			});
			
		}
		return jButtonAddDriver;
	}

	/**
	 * This method initializes jScrollPane	
	 * 	
	 * @return javax.swing.JScrollPane	
	 */
	private JScrollPane getJScrollPane() {
		if (jScrollPane == null) {
			jScrollPane = new JScrollPane();
			jScrollPane.setBounds(new Rectangle(20, 60, 261, 101));
			jScrollPane.setViewportView(getJTableDriver());
		}
		return jScrollPane;
	}

	/**
	 * This method initializes jTableDriver	
	 * 	
	 * @return javax.swing.JTable	
	 */
	private JTable getJTableDriver() {
		if (jTableDriver == null) {
			jTableDriver = new JTable();
			jTableDriver.setModel(model);
			jTableDriver.addKeyListener(new DriverKeyListener());
			
		}
		return jTableDriver;
	}

}  //  @jve:decl-index=0:visual-constraint="10,10"
