package it.eng.proxyselector.frame.button;

import it.eng.proxyselector.frame.ProxyFrame;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

public class ProxyButton extends JButton {

	public ProxyButton(){
		super("Impostazioni di rete");
		addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				new ProxyFrame("http://www.google.it").setVisible(true);
				
				
			}
		});
	}
}
