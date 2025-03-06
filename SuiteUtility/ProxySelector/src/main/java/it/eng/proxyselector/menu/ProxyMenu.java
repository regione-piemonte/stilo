package it.eng.proxyselector.menu;

import it.eng.proxyselector.frame.ProxyFrame;
import it.eng.proxyselector.preferences.ProxyPreferences;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

public class ProxyMenu extends JMenuBar {
	
	
	
	public ProxyMenu(){
		super();
		ProxyPreferences.saveConfiguration(ProxyPreferences.getSavedConfiguration());
		JMenu menuOptionRete = new JMenu("Rete");

        JMenuItem lJMenuItemImpostazioni = new JMenuItem("Impostazioni");
        menuOptionRete.add(lJMenuItemImpostazioni);
        lJMenuItemImpostazioni.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				new ProxyFrame("http://www.google.it").setVisible(true);
				
				
			}
		});

        menuOptionRete.add(lJMenuItemImpostazioni);
        add(menuOptionRete);
	}
}
