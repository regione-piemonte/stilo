package it.eng.client.applet.panel;

import it.eng.client.applet.i18N.MessageKeys;
import it.eng.client.applet.i18N.Messages;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class InfoLog extends GenericPanel {

	private static final long serialVersionUID = 1L;

	private JLabel label;
	
	private JButton close;
	
	public void show( Container container, String versione){

		windowDialog = new JDialog();
		windowDialog.setModal(true);

		initialize( versione );
		
		windowDialog.getContentPane().setLayout( new BorderLayout() );
		windowDialog.getContentPane().add( this,BorderLayout.CENTER );
		windowDialog.pack();
        windowDialog.setSize( 500, 400 );
		 
        windowDialog.setTitle( Messages.getMessage( MessageKeys.PANEL_INFO_TITLE ) );
        
        windowDialog.setLocationRelativeTo( container );
        windowDialog.setVisible(true);
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize(String versione) {
		this.setPreferredSize(new Dimension(300, 170));
		
		GridBagConstraints c1 = new GridBagConstraints();
        setLayout(new GridBagLayout());
        
		pane = new JPanel();
        JScrollPane scroll = new JScrollPane(pane);
        scroll.setBorder(BorderFactory.createEmptyBorder());
		pane.setLayout(new GridBagLayout());
		
		GridBagConstraints c = new GridBagConstraints();
		int y=0;
		
		label = aggiungiLabel(pane, label, "label", Messages.getMessage( MessageKeys.PANEL_INFO_TEXT) + versione, c, 0, y++, 2, 1, 2, GridBagConstraints.LINE_START);
		
		c1.gridx = 0;
		c1.gridy = 0;
		c1.insets = new Insets(1,5,10,5);
		add( pane, c1);
		
		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new GridBagLayout());
		GridBagConstraints cButton = new GridBagConstraints();
		
		close = aggiungiBottone( buttonPanel, close, "sendMail", Messages.getMessage( MessageKeys.PANEL_BUTTON_CLOSE ),
				Messages.getMessage( MessageKeys.PANEL_BUTTON_CLOSE_TOOLTIP ), true, cButton, 1, 0, 1, 5, 5 );
		close.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				windowDialog.setVisible(false);
			}
		});
		
		c1.gridx = 0;
		c1.gridy = 1;
		c1.gridwidth = 2;
		c1.insets = new Insets(1,5,5,5);
		add( buttonPanel, c1);
	}
	
	
}  
