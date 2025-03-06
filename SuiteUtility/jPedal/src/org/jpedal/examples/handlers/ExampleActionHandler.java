package org.jpedal.examples.handlers;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.WindowConstants;

import org.jpedal.examples.viewer.Commands;
import org.jpedal.examples.viewer.Viewer;
import org.jpedal.examples.viewer.gui.SwingGUI;
import org.jpedal.external.JPedalActionHandler;
import org.jpedal.external.Options;

public class ExampleActionHandler extends JFrame {
	public static void main(String[] args) {
		new ExampleActionHandler();
	}

	public ExampleActionHandler() {
		/** add the Viewer component */
		Viewer viewer = new Viewer();
		viewer.setRootContainer(getContentPane());
		
		/** create a new JPedalActionHandler implementation */
		JPedalActionHandler helpAction = new JPedalActionHandler() {
			public void actionPerformed(SwingGUI currentGUI, Commands commands) {
				JOptionPane.showMessageDialog(currentGUI.getFrame(), 
						"Custom help dialog", "JPedal Help", JOptionPane.INFORMATION_MESSAGE);
			}
		};
		
		/** add the implementation to a Map, with its corresponding command, in this case Commands.HELP */
		Map actions = new HashMap();
		actions.put(Commands.HELP, helpAction);
		
		/** pass the map into the external handler */
		viewer.addExternalHandler(actions, Options.JPedalActionHandler);
		
		/** display the Viewer */
		displayViewer();
	}

	private void displayViewer() {
		Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
		int width = d.width / 2, height = d.height / 2;
		if (width < 700)
			width = 700;
		
		setSize(width, height);
		setLocationRelativeTo(null); //centre on screen
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		setVisible(true);
	}
}
