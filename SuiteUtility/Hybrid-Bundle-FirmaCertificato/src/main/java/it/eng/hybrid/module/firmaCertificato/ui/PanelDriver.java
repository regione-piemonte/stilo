package it.eng.hybrid.module.firmaCertificato.ui;



import java.awt.Rectangle;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.UIManager;


public class PanelDriver extends JPanel {

	private static final long serialVersionUID = 1L;
	private JButton jButtonAddDriver = null;
	private JScrollPane jScrollPane = null;
	private JTable jTableDriver = null;

	/**
	 * This is the default constructor
	 */
	public PanelDriver() {
		super();
		initialize();
		//reloadFile();	

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
				//reloadFile();
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
		}
		return jTableDriver;
	}

}  //  @jve:decl-index=0:visual-constraint="10,10"
