package it.eng.client.applet;

import it.eng.client.applet.i18N.MessageKeys;
import it.eng.client.applet.i18N.Messages;

import java.awt.Component;
import java.awt.Dialog;
import java.awt.Frame;
import java.awt.HeadlessException;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.Icon;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;

public class OptionSignDialog{

	public static int createDialog(Component parentComponent,DefaultMutableTreeNode rootNode,
			Object message, String title, int optionType, int messageType,
			Icon icon, String[] options, String defaultOption)	throws HeadlessException {


		final JOptionPane optionPane = new JOptionPane(message, optionType, messageType, null, options, defaultOption);

		final JDialog dialog;
		if (parentComponent instanceof Frame) {
			dialog = new JDialog((Frame)parentComponent, title, true);
		} else if (parentComponent instanceof Dialog){
			dialog = new JDialog((Dialog)parentComponent, title, true);
		}else{
			dialog=new JDialog((Dialog) null, title, true);
		}
		//		dialog.setTitle(title);
		//		dialog.setModal(true);
		if(parentComponent!=null){
			dialog.setLocationRelativeTo(parentComponent);
		}
		dialog.setContentPane(optionPane);
		//aggiungo il panelo di sotto invisibile
		final JScrollPane jscr=new JScrollPane( new JTree(rootNode));
		jscr.setVisible(false);
		dialog.add(jscr);

		optionPane.addPropertyChangeListener(
			new PropertyChangeListener() {
				public void propertyChange(PropertyChangeEvent e) {
					String prop = e.getPropertyName();

					if (dialog.isVisible() 
							&& (e.getSource() == optionPane)
							&& (prop.equals(JOptionPane.VALUE_PROPERTY))) {
						if(optionPane.getValue().equals( Messages.getMessage( MessageKeys.PANEL_BUTTON_CKECKRESULT ) ) ){
							//change label and sho panel
							jscr.setVisible(!jscr.isVisible());
							//reinit to unintialized
							dialog.pack();
							optionPane.setValue(JOptionPane.UNINITIALIZED_VALUE);

						}
						if(optionPane.getValue().equals(Messages.getMessage( MessageKeys.PANEL_BUTTON_SI ) ) 
								|| optionPane.getValue().equals(Messages.getMessage( MessageKeys.PANEL_BUTTON_NO ) ) ){
							dialog.setVisible(false);
						}
					}
				}
			});
		dialog.pack();
		dialog.setVisible(true);
		dialog.dispose();
		Object selectedValue = optionPane.getValue();

		if(selectedValue == null)
			return JOptionPane.CLOSED_OPTION;
		if(optionPane.getOptions() == null) {
			if(selectedValue instanceof Integer)
				return ((Integer)selectedValue).intValue();
			return JOptionPane.CLOSED_OPTION;
		}
		for(int counter = 0, maxCounter = options.length;
				counter < maxCounter; counter++) {
			if(options[counter].equals(selectedValue))
				return counter;
		}
		return JOptionPane.CLOSED_OPTION;
	}

}