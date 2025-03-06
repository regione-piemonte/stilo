package it.eng.wordOpener.applet;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JApplet;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import org.apache.log4j.Logger;

import it.eng.proxyselector.frame.ProxyFrame;
import it.eng.wordOpener.WordModifyer;
import it.eng.wordOpener.configuration.WordOpenerConfigManager;
import it.eng.wordOpener.error.ErrorManager;
import it.eng.wordOpener.exception.FileNotSupportedException;
import it.eng.wordOpener.exception.InitException;
import it.eng.wordOpener.exception.UnableToEditFileException;
import it.eng.wordOpener.exception.UnableToRetrieveFileException;
import it.eng.wordOpener.exception.UnableToSaveFileException;
import it.eng.wordOpener.fileProvider.impl.AurigaFileProvider;
import it.eng.wordOpener.process.WordProcessWriteAndSave;
import netscape.javascript.JSObject;

public class WordOpenerApplet extends JApplet implements ErrorManager {

	private static final long serialVersionUID = 4023062881967759984L;
	
	private static Logger mLogger = Logger.getLogger(WordOpenerApplet.class);
	
	public WordOpenerApplet() {
		setName("Edit file with office");
		
	}
	
	@Override
	public void init() {
		
		Integer width = Integer.valueOf(getParameter("width"));
		Integer height = Integer.valueOf(getParameter("height"));

		setJMenuBar(createMenuBar());
		
		JPanel mainPanel = getMainPanel();
		mainPanel.setSize(width, height);
		
		setContentPane(mainPanel);
		
		try {

			setSize(width, height);
			validate();
			setVisible(true);
			WordOpenerConfigManager.init(this);
			new WordModifyer(new WordProcessWriteAndSave(), new AurigaFileProvider(),
					this).openAndWait();
		} catch (UnableToEditFileException e) {
			mLogger.error("Errore: " + e.getMessage(), e);
			JOptionPane.showMessageDialog(this, "Impossibile editare il file " + e.getMessage(), "Attenzione", JOptionPane.ERROR_MESSAGE);
			stop();
			destroy();
			System.exit(-1);
		} catch (UnableToRetrieveFileException e) {
			mLogger.error("Errore: " + e.getMessage(), e);
			JOptionPane.showMessageDialog(this, "Impossibile recuperare il file " + e.getMessage(), "Attenzione", JOptionPane.ERROR_MESSAGE);
		} catch (FileNotSupportedException e) {
			mLogger.error("Errore: " + e.getMessage(), e);
			JOptionPane.showMessageDialog(this, "Impossibile modificare il file: " + e.getMessage(), "Attenzione", JOptionPane.ERROR_MESSAGE);
		} catch (InitException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Crea la menu bar dell'applet
	 * @return
	 */
	protected JMenuBar createMenuBar() {
		
		JMenuBar menuBar = new JMenuBar();
		JMenu mainMenu = new JMenu("File");
		
		//sub menu per la visualizzazione del pannello di configurazione della rete
		JMenuItem rete = new JMenuItem("Preferenze");
		rete.addActionListener( new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					new ProxyFrame(WordOpenerConfigManager.getTestUrl()).setVisible(true);
				} catch (InitException e1) {
					manageExcepion(e1);
				}
			}
		});
		
		mainMenu.add(rete);
		menuBar.add(mainMenu);
		
		return menuBar;
		
	}
	
	protected JPanel getMainPanel() {

		JPanel main = new JPanel();
		FlowLayout layout = new FlowLayout();
		layout.preferredLayoutSize(main);
		main.setLayout(layout);
		
		return main;
	}

	@Override
	public void manageError(String message) {
		mLogger.error("Errore: " + message);
		JOptionPane.showMessageDialog(this, "Impossibile salvare il file: " + message, "Attenzione", JOptionPane.ERROR_MESSAGE);
		stop();
		destroy();
		System.exit(-1);
	}

	@Override
	public void manageExcepion(Exception pException) {
		mLogger.error("Errore: " + pException.getMessage(), pException);
		JOptionPane.showMessageDialog(this, "Impossibile salvare il file " + pException.getMessage(), "Attenzione", JOptionPane.ERROR_MESSAGE);
		stop();
		destroy();
		System.exit(-1);
	}
	
	@Override
	public void justClose() {
		try {
		String callback = WordOpenerConfigManager.getCallBackCancel();
		if (callback!=null && !callback.equals("")){
			String lStrToInvoke = "javascript:" + callback + "();";
			try {
			JSObject.getWindow(this).eval( lStrToInvoke );
			} catch (Exception e) {
				throw new UnableToSaveFileException("Browser non supportato: " + e.getMessage(), e);
			}
		}	
		} catch (Exception pException) {
			manageExcepion(pException);
		}
	}

}
