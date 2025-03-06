package it.eng.proxyselector.menu;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.MalformedURLException;
import java.net.URL;

import javax.swing.JApplet;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

public class ExitMenu extends JMenu {

	private static final long serialVersionUID = -711832183919203158L;

	public ExitMenu(String lStringLabel){
		super(lStringLabel);
		JMenuItem lJMenuItem = new JMenuItem(lStringLabel);

		lJMenuItem.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (otherAction()){
					JMenuItem lJMenuItem = (JMenuItem)e.getSource();
					JPopupMenu fromParent = (JPopupMenu)lJMenuItem.getParent();  
					JMenu lJMenu = (JMenu)fromParent.getInvoker(); 
					Container lContainer = lJMenu.getParent();
					while (!(lContainer instanceof JApplet)){
						lContainer = lContainer.getParent();
					}
					JApplet lApplet = (JApplet)lContainer;
					String callback = lApplet.getParameter("callbackToCall");
					String lStrToInvoke = "javascript:" + callback + "('prova');";
					System.out.println("Invoco " +lStrToInvoke);
					try {
						lApplet.getAppletContext().showDocument(new URL(lStrToInvoke));
					} catch (MalformedURLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					lApplet.stop();
					lApplet.destroy();
					System.exit(1);
				}
			}
		});
		//		setMaximumSize(new Dimension(50,100));
		add(lJMenuItem);
	}

	protected boolean otherAction(){
		return true;
	}
}
