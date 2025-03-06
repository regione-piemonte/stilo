package it.eng.client.applet.panel;

import it.eng.client.applet.ISmartCard;

import java.awt.Rectangle;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.UIManager;

public class PanelFileSelect extends JPanel {

	private static final long serialVersionUID = 1L;
	private JButton jButtonSelect = null;
	private JTextField jTextFieldFileName = null;
	
	private ISmartCard card;
	
	/**
	 * This is the default constructor
	 */
	public PanelFileSelect(ISmartCard card) {
		super();
		initialize();
		this.card = card;
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setSize(300, 170);
		this.setLayout(null);
		this.add(getJButtonSelect(), null);
		this.add(getJTextFieldFileName(), null);
	}

	/**
	 * This method initializes jButtonSelect	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getJButtonSelect() {
		if (jButtonSelect == null) {
			jButtonSelect = new JButton();
			jButtonSelect.setBounds(new Rectangle(80, 80, 141, 21));
			jButtonSelect.setText("Seleziona");
			jButtonSelect.setToolTipText("Seleziona il file da firmare");
			jButtonSelect.setIcon(UIManager.getIcon( "Tree.openIcon"));
			jButtonSelect.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					JFileChooser chooser = new JFileChooser();
					//chooser.setFileFilter(new DllFilter());
					chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
					int i = chooser.showOpenDialog(PanelFileSelect.this);
					if(i == JFileChooser.APPROVE_OPTION){
						File file = chooser.getSelectedFile();
						
						jTextFieldFileName.setText(file.getAbsolutePath());
												
						//card.setUnsigned(file);
																
						//Apro il tab di firma
						card.selectedTab(1);
						
					}
				}
			});
		}
		return jButtonSelect;
	}

	/**
	 * This method initializes jTextFieldFileName	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getJTextFieldFileName() {
		if (jTextFieldFileName == null) {
			jTextFieldFileName = new JTextField();
			jTextFieldFileName.setBounds(new Rectangle(40, 40, 221, 21));
			jTextFieldFileName.setEditable(false);
			jTextFieldFileName.setEnabled(false);
		}
		return jTextFieldFileName;
	}

}
