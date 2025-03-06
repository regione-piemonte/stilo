package it.eng.client.applet.model.listener;

import it.eng.client.applet.model.DriverTableModel;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;

import javax.swing.JTable;

public class DriverKeyListener implements KeyListener {


	public void keyPressed(KeyEvent evt) {
		if(KeyEvent.VK_DELETE == evt.getKeyCode()){ 
			int row = ((JTable)evt.getComponent()).getSelectedRow();
			//Cancello il file dal modello
			File file = ((DriverTableModel)((JTable)evt.getComponent()).getModel()).getDriver().remove(row);
			//Cancello il file fisicamente
			file.delete();
			//Aggiorno il modello
			((DriverTableModel)((JTable)evt.getComponent()).getModel()).fireTableDataChanged();
		}
	}

	
	public void keyReleased(KeyEvent evt) {
		// TODO Auto-generated method stub
		
	}


	public void keyTyped(KeyEvent evt) {
		// TODO Auto-generated method stub
	}

}
