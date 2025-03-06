package it.eng.client.applet.panel;

import it.eng.client.applet.util.PreferenceManager;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JSeparator;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class GenericPanel extends JPanel {

	private static final long serialVersionUID = 1L;

	protected JDialog windowDialog;
	
	protected JPanel pane;
	protected JTabbedPane tabs;
	
	protected JLabel aggiungiLabel(JPanel panel,JLabel label, String name, String text, GridBagConstraints c, int x, int y, int width, int marginDS, int marginSupInf, int anchor){
		label = new JLabel();
		label.setText(text);		
		label.setName(name);		
		
		c.gridx = x;
		c.gridy = y;
		c.gridwidth = width;
		c.anchor = anchor; //GridBagConstraints.LINE_START;
		c.insets = new Insets( marginSupInf, marginDS, marginSupInf, marginDS);
		c.weightx = c.weighty = 1.0;
		panel.add(label, c);
		return label;
	}
	
	protected JTextField aggiungiFieldTesto(JPanel panel, JTextField field, int size,  String name, String text, boolean editable, GridBagConstraints c, int x, int y, int width, int marginDS, int marginSupInf, int anchor){
		field=new JTextField(size);
		if( text!=null)
			field.setText( text );
		field.setEditable( editable);
		field.setName(name);
		
		c.gridx = x;
		c.gridy = y;
		c.gridwidth = width;
		c.anchor = anchor;//GridBagConstraints.LINE_START;
		c.weightx = c.weighty = 1.0;
		c.insets = new Insets( marginSupInf, marginDS, marginSupInf, marginDS );
		panel.add(field, c);
		return field;
	}
	
	protected JTextArea aggiungiFieldTextArea(JPanel panel, JTextArea field, int rows, int cols,  String name, String text, boolean editable, GridBagConstraints c, int x, int y, int width, int marginDS, int marginSupInf, int anchor){
		field=new JTextArea(rows, cols);
		field.setBorder( BorderFactory.createLineBorder(Color.gray) );
		if( text!=null)
			field.setText( text );
		field.setEditable( editable);
		field.setName(name);
		
		c.gridx = x;
		c.gridy = y;
		c.gridwidth = width;
		c.anchor = anchor;//GridBagConstraints.LINE_START;
		c.insets = new Insets( marginSupInf, marginDS, marginSupInf, marginDS);
		c.weightx = c.weighty = 1.0;
		panel.add(field, c);
		return field;
	}
	
	protected JPasswordField aggiungiFieldPassword(JPanel panel, JPasswordField field, int size,  String name, String text, boolean editable, GridBagConstraints c, int x, int y, int width, int marginDS, int marginSupInf, int anchor){
		field=new JPasswordField(size);
		//field.setBorder( BorderFactory.createLineBorder(Color.black) );
		if( text!=null)
			field.setText( text );
		field.setEditable( editable);
		field.setName(name);
		
		c.gridx = x;
		c.gridy = y;
		c.gridwidth = width;
		c.anchor = anchor;//GridBagConstraints.LINE_START;
		c.insets = new Insets( marginSupInf, marginDS, marginSupInf, marginDS);
		c.weightx = c.weighty = 1.0;
		panel.add(field, c);
		return field;
	}
	
	protected JCheckBox aggiungiCheckBox(JPanel panel, JCheckBox checkBox, String name, String text, boolean selected, boolean enabled, 
			int textPosition, GridBagConstraints c, int x, int y, int width,  int marginDS, int marginSupInf, int anchor){
		checkBox=new JCheckBox();
		//checkBox.setBorder( BorderFactory.createLineBorder(Color.black) );
		checkBox.setName( name );
		checkBox.setSelected(selected);
		checkBox.setEnabled( enabled );
		checkBox.setText(text);
		checkBox.setHorizontalTextPosition( textPosition );
		c.gridx = x;
		c.gridy = y;
		c.gridwidth = width;
		c.anchor = anchor;//GridBagConstraints.LINE_START;
		c.weightx = c.weighty = 1.0;
		c.insets = new Insets( marginSupInf, marginDS, marginSupInf, marginDS );
		panel.add( checkBox, c );
		return checkBox;
	}
	
	protected void aggiungiCombo(JPanel panel, JComboBox combo, String[] comboValues, String name, String text, boolean editable, GridBagConstraints c, int x, int y, int width,  int marginDS, int marginSupInf, int anchor){
		combo.setName( name );
		int indiceSelezionato = 0;
		if( comboValues!=null){
			for( int i = 0; i<comboValues.length; i++){
				if( comboValues[i].equalsIgnoreCase( text ) )
					indiceSelezionato = i;
			}
			combo.setSelectedIndex(indiceSelezionato);
		}
		
		combo.setEnabled( editable);
		c.gridx = x;
		c.gridy = y;
		c.gridwidth = width;
		c.anchor = anchor;//GridBagConstraints.LINE_START;
		c.weightx = c.weighty = 1.0;
		c.insets = new Insets( marginSupInf, marginDS, marginSupInf, marginDS );
		panel.add(combo, c);
	}
	
	protected void aggiungiRigaSeparazione(JPanel panel, GridBagConstraints c, int x, int y, int width, int marginDS, int marginSupInf){
		c.gridx = 0;
		c.gridy = y++;
		c.fill = GridBagConstraints.HORIZONTAL; 
		c.insets = new Insets( marginSupInf, marginDS, marginSupInf, marginDS );
		c.gridwidth = width;
		pane.add(new JSeparator(SwingConstants.HORIZONTAL), c);
	}
	
	protected JButton aggiungiBottone(JPanel panel, JButton button, String name, String text, String tooltip, boolean editable, GridBagConstraints c, int x, int y, int width, int marginDS, int marginSupInf){
		button = new JButton();
		button.setName( name );
		button.setText(text);
		button.setToolTipText( tooltip );
		button.setEnabled( editable);
		c.gridx = x;
		c.gridy = y;
		c.gridwidth = width;
		//c.anchor = GridBagConstraints.LINE_START;
		//c.weightx = c.weighty = 1.0;
		c.insets = new Insets( marginSupInf, marginDS, marginSupInf, marginDS );
		panel.add(button, c);
		return button;
	}
	
	protected void aggiungiPannello(JPanel panel, GridBagConstraints c, int x, int y, int width, int anchor){
		c.gridx = x;
		c.gridy = y;
		c.gridwidth = width;
		c.anchor = anchor;//GridBagConstraints.FIRST_LINE_START;
		c.insets = new Insets(5,5,5,5);
		c.weightx = c.weighty = 1.0;
		pane.add(panel, c);
	}
	
	protected void aggiungiPannello(JPanel panel, JPanel panelContainer, GridBagConstraints c, int x, int y, int width, int anchor, int marginDS, int marginSupInf){
		c.gridx = x;
		c.gridy = y;
		c.gridwidth = width;
		c.anchor = anchor;
		c.insets = new Insets(marginSupInf, marginDS, marginSupInf, marginDS);
		c.weightx = c.weighty = 1.0;
		panelContainer.add(panel, c);
	}
	
	protected JPanel creaPannelloTab(JPanel... pannelli){
		JPanel pannello = new JPanel();
		pannello.setLayout( new GridBagLayout() );
		GridBagConstraints cPannello = new GridBagConstraints();
		cPannello.gridx = 0;
		cPannello.anchor = GridBagConstraints.FIRST_LINE_START;
		cPannello.weightx = cPannello.weighty = 1.0;
		cPannello.insets = new Insets(0,10,0,10); 
		int y=0;
		for(JPanel panel : pannelli){
			cPannello.gridy = y;
			pannello.add( panel, cPannello );
			y++;
		}
		
		return pannello;
	}
	
	protected void aggiungiPannelloTabs(JPanel panel, String title){
		tabs.add(title, panel);
	}

}  
