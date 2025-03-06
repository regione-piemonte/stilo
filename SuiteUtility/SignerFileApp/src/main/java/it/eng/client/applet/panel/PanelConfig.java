package it.eng.client.applet.panel;

import it.eng.client.applet.SmartCard;
import it.eng.client.applet.WidgetFactory;
import it.eng.client.applet.bean.ThemeBean;
import it.eng.client.applet.config.IConfigWidget;
import it.eng.client.applet.config.JComboConfigWidget;
import it.eng.client.applet.i18N.Messages;
import it.eng.client.applet.operation.AbstractSigner;
import it.eng.client.applet.util.PreferenceManager;
import it.eng.common.LogWriter;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.GridLayout;
import java.awt.Rectangle;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;

import com.nilo.plaf.nimrod.NimRODLookAndFeel;

public class PanelConfig extends JPanel {

	private static final long serialVersionUID = 1L;
//	private JLabel jLabelDigest = null;
//	private JTextField jTextFieldDigest = null;
//	private JLabel jLabelSign = null;
//	private JComboBox jComboBoxType = null;
	private JLabel jLabelLookFeel = null;
	private JComboConfigWidget jComboBoxLookFeel = null;
	
	 
	
	private SmartCard card;
	private JProgressBar jProgressBar = null;
	private List<JLabel> labels=new ArrayList<JLabel>();
	private List<IConfigWidget> widgets= new ArrayList<IConfigWidget>();
	private static final int WIDGET_HEIGTH=40;
	 
	/**
	 * This is the default constructor
	 */
	public PanelConfig(SmartCard card) {
		super(new BorderLayout());
		initialize();
		setSize(200, 100);
		this.card = card;
 
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		JPanel lJPanel = new JPanel(new BorderLayout());
		LogWriter.writeLog("Panel config initalize START");
		Iterator<String> keys=null;
		try {
			 
			keys = PreferenceManager.getMetaConfig().getKeys("widget");
			while (keys!=null && keys.hasNext()){
				String key=keys.next();
				 
				IConfigWidget aWidget= WidgetFactory.buildWidget(key);
				if(aWidget!=null){
					JLabel label = new JLabel();
					label.setText(Messages.getMessage(key));
					label.setToolTipText(Messages.getMessage(key));
					label.setLabelFor((Component)aWidget);
//					label.setPreferredSize(new Dimension(10,10));
					labels.add(label);
//					((Component)aWidget).setPreferredSize(new Dimension(10, 10));
					widgets.add(aWidget);
					
				}
			}
		} catch (Exception e) {
			// TODO laucnhe error
			e.printStackTrace();
		}
		JPanel labelPanel = new JPanel(new GridLayout(widgets.size(), 1));
		JPanel fieldPanel = new JPanel(new GridLayout(widgets.size(), 1));
		lJPanel.add(labelPanel, BorderLayout.WEST);
		lJPanel.add(fieldPanel, BorderLayout.CENTER);
//		this.setPreferredSize(new Dimension(300, 50));
		//this.setLayout(null);
		//pannello per la griglia delle variabili di conf
		
		for (int i = 0; i < widgets.size(); i++) {
			JLabel lLabel = labels.get(i);
			Component lComponent = (Component)widgets.get(i);
			lLabel.setLabelFor(lComponent);
			labelPanel.add(lLabel);
			fieldPanel.add(lComponent);
		}
		add(lJPanel, BorderLayout.NORTH);
		if (true) return;
//		JPanel pannelGrid = new JPanel();
//		pannelGrid.setLayout(new GridLayout(0, 2,2,2));
//		
//		//aggiungouno scroll pane 
//		JScrollPane scrollPane=new JScrollPane(pannelGrid);
//		 
//		GridBagLayout gridbag = new GridBagLayout() ;
//		GridBagConstraints c = new GridBagConstraints() ;
//		this.setLayout(gridbag);
//		c.gridx = 0;
//		c.gridy = 0;
//		c.fill = GridBagConstraints.BOTH ;
//		c.weightx = 1.0 ;
//		c.weighty = 1.0 ;
//		
//		gridbag.setConstraints(scrollPane,c) ;
////		scrollPane.getViewport().setSize(300, 170);
////		scrollPane.setAutoscrolls(true);
//		
////		scrollPane.getViewport().add( new JTextArea("test"));
//		 
// 		
//		pannelGrid.setVisible(true);
//		pannelGrid.setBorder(new LineBorder(new Color(0, 0, 0), 2));
//		//bottone per salvare le preference sul client
//		JButton save= new JButton(Messages.getMessage( PreferenceKeys.PROPERTY_SAVE_PREFERENCE_ENABLED ) );
//		save.addActionListener(new java.awt.event.ActionListener() {
//			public void actionPerformed(java.awt.event.ActionEvent e) {
//				//salvo le prefernce 
//				for (int i = 0; i < widgets.size(); i++) {
//					WidgetInfo winfo=widgets.get(i).getWidgetInfo();
//					try {
//						if(winfo!=null && winfo.getPropValue()!=null && !winfo.getPropValue().equals(""))
//							PreferenceManager.saveProp(winfo.getPropValue(), widgets.get(i).getValue());
//					} catch (Exception e1) {
//						// TODO Auto-generated catch block
//						e1.printStackTrace();
//						JOptionPane.showMessageDialog(card.getJTabbedPane(),"Errore Salvataggio Preference!"+e1.getMessage(),"Errore", JOptionPane.ERROR_MESSAGE);
//					}
//				}
//				//reinit config
//				PreferenceManager.reinitConfig();
//				
//				JOptionPane.showMessageDialog(card.getJTabbedPane(),Messages.getMessage(MessageKeys.MSG_PREF_SAVED),Messages.getMessage(MessageKeys.MSG_PREF_TITLE), JOptionPane.INFORMATION_MESSAGE);
//			}
//		});	
//		//pannelGrid.add(save);
//		 
//		c.fill = GridBagConstraints.NONE;
////	    c.ipady = 10;      //make this component tall
//	    c.weightx = 0.01;
//	    c.weighty = 0.01;
//	    c.gridwidth = 1;
//	    c.anchor = GridBagConstraints.PAGE_END; //bottom of space
//	    c.insets = new Insets(10,10,10,10);  //top padding
//	    c.gridx = 0;
//	    c.gridy = 1;
//		this.add(save,c);
//		this.add( scrollPane);
//		
//		scrollPane.setVisible(true);
//	    LogWriter.writeLog("Panel config initalize END");
	}

	 
	 

	/**
	 * This method initializes jComboBoxLookFeel	
	 * 	
	 * @return javax.swing.JComboBox	
	 */
	private JComboBox getJComboBoxLookFeel() {
		if (jComboBoxLookFeel == null) {
			jComboBoxLookFeel = new JComboConfigWidget();
			jComboBoxLookFeel.setBounds(new Rectangle(100, 100, 181, 21));
			jComboBoxLookFeel.addItemListener(new java.awt.event.ItemListener() {
				public void itemStateChanged(java.awt.event.ItemEvent e) {
					ThemeBean theme = ((ThemeBean)jComboBoxLookFeel.getSelectedItem());
					try {
						
						NimRODLookAndFeel.setCurrentTheme(theme.getLook());
						UIManager.setLookAndFeel(card.getLookFeel());
						SwingUtilities.updateComponentTreeUI(card.getMainComponent().getContentPane());
						
						//Salvo sul file la nuova configurazione
						File configdirectory = new File(AbstractSigner.dir+File.separator+AbstractSigner.cryptoConfig);
						File conffile = new File(configdirectory+File.separator+"crypto.config");
						if(!configdirectory.exists()){
							configdirectory.mkdirs();
						}
						
						//Copio il file di configurazione se non esiste
						if(!conffile.exists()){
							FileUtils.writeByteArrayToFile(conffile, IOUtils.toByteArray(this.getClass().getResourceAsStream("/it/eng/client/lookfeel/crypto.config")));
						}
						
						//Recupero la configurazione
						Properties prop = new Properties();
						prop.load(new FileInputStream(conffile));
						prop.setProperty("template", "it/eng/client/lookfeel/"+theme.toString()+".theme");
						FileOutputStream out = new FileOutputStream(conffile);
						prop.store(out, "Configurazione");
						out.flush();
						out.close();
					} catch (Exception e1) {
						e1.printStackTrace();
					}
				}
			});
			
		}
		return jComboBoxLookFeel;
	}
 
	/**
	 * This method initializes jProgressBar	
	 * 	
	 * @return javax.swing.JProgressBar	
	 */
	private JProgressBar getJProgressBar() {
		if (jProgressBar == null) {
			jProgressBar = new JProgressBar();
			jProgressBar.setBounds(new Rectangle(20, 60, 261, 21));
			jProgressBar.setVisible(false);
		}
		return jProgressBar;
	}

}  //  @jve:decl-index=0:visual-constraint="10,10"
