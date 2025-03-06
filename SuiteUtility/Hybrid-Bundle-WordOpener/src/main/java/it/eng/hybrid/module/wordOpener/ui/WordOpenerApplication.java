package it.eng.hybrid.module.wordOpener.ui;

import java.awt.FlowLayout;
import java.awt.Label;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import org.apache.log4j.Logger;

import it.eng.areas.hybrid.module.util.json.JSONArray;
import it.eng.areas.hybrid.module.util.json.JSONObject;
import it.eng.hybrid.module.wordOpener.WordModifyer;
import it.eng.hybrid.module.wordOpener.WordOpenerClientModule;
import it.eng.hybrid.module.wordOpener.config.WordOpenerConfigManager;
import it.eng.hybrid.module.wordOpener.error.ErrorManager;
import it.eng.hybrid.module.wordOpener.exception.FileNotSupportedException;
import it.eng.hybrid.module.wordOpener.exception.InitException;
import it.eng.hybrid.module.wordOpener.exception.UnableToEditFileException;
import it.eng.hybrid.module.wordOpener.exception.UnableToRetrieveFileException;
import it.eng.hybrid.module.wordOpener.fileProvider.FileProvider;
import it.eng.hybrid.module.wordOpener.fileProvider.impl.AurigaFileProvider;
import it.eng.hybrid.module.wordOpener.process.WordProcessWriteAndSave;
import it.eng.proxyselector.frame.ProxyFrame;

public class WordOpenerApplication extends JFrame implements ErrorManager {

	public final static Logger logger = Logger.getLogger(WordOpenerApplication.class);

	private WordOpenerClientModule module;

	Map<String, String> props = new HashMap<String, String>();

	public WordOpenerApplication(WordOpenerClientModule module, JSONArray parameters) {
		// super(owner);
		this.module = module;

		logger.debug("Costruttore di WordOpenerApplication ");

		JSONObject options = parameters.getJSONObject(0);
		Iterator optionsItr = options.keys();
		List<String> optionNames = new ArrayList<String>();
		while (optionsItr.hasNext()) {
			optionNames.add((String) optionsItr.next());

		}
		for (int i = 0; i < options.length(); i++) {
			props.put(optionNames.get(i), options.getString(optionNames.get(i)));
			logger.info("Proprieta' " + optionNames.get(i) + "=" + options.getString(optionNames.get(i)));
		}

		Integer width = Integer.valueOf(props.get("width"));
		Integer height = Integer.valueOf(props.get("height"));

		setJMenuBar(createMenuBar());

		JPanel mainPanel = getMainPanel();
		mainPanel.setSize(width, height);

		setContentPane(mainPanel);

		try {

			setSize(width, height);
			setVisible(true);
			validate();

			WordOpenerConfigManager.init(props);
			FileProvider fileProvider = new AurigaFileProvider();
			new WordModifyer(new WordProcessWriteAndSave(), fileProvider, this).openAndWait();
			closeFrame(fileProvider);
		} catch (UnableToEditFileException e) {
			logger.error("Errore: " + e.getMessage(), e);
			JOptionPane.showMessageDialog(this, "Impossibile editare il file " + e.getMessage(), "Attenzione", JOptionPane.ERROR_MESSAGE);
			// stop();
			// destroy();
			System.exit(-1);
		} catch (UnableToRetrieveFileException e) {
			logger.error("Errore: " + e.getMessage(), e);
			JOptionPane.showMessageDialog(this, "Impossibile recuperare il file " + e.getMessage(), "Attenzione", JOptionPane.ERROR_MESSAGE);
		} catch (FileNotSupportedException e) {
			logger.error("Errore: " + e.getMessage(), e);
			JOptionPane.showMessageDialog(this, "Impossibile modificare il file: " + e.getMessage(), "Attenzione", JOptionPane.ERROR_MESSAGE);
		} catch (InitException e) {
			e.printStackTrace();
		}
	}

	public void closeFrame(FileProvider fileProvider) {
		logger.info(WordOpenerConfigManager.CALLBACK + " : " + props.get(WordOpenerConfigManager.CALLBACK));
		if (props.get(WordOpenerConfigManager.CALLBACK) != null) {
			JSONObject jResult = new JSONObject();
			module.setResult(props.get(WordOpenerConfigManager.CALLBACK));

			String argFunction = fileProvider.getArgFunction();
			logger.debug("argFunction " + argFunction);
			List<String> results = new ArrayList<String>();
			results.add(props.get(WordOpenerConfigManager.CALLBACK));
			results.add(argFunction);
			module.setResults(results);
			// Per chiudere il frame (che sia visibile o meno)
			this.dispose();

			logger.debug("Chiusa la finestra");
		}
	}

	/**
	 * Crea la menu bar dell'applet
	 * 
	 * @return
	 */
	protected JMenuBar createMenuBar() {

		JMenuBar menuBar = new JMenuBar();
		JMenu mainMenu = new JMenu("File");

		// sub menu per la visualizzazione del pannello di configurazione della rete
		JMenuItem rete = new JMenuItem("Preferenze");
		rete.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					new ProxyFrame(WordOpenerConfigManager.getTestUrl()).setVisible(true);
				} catch (InitException e1) {
					// manageExcepion(e1);
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
		Label label = new Label("Apertura editor in corso...");
		main.add(label);
		layout.preferredLayoutSize(main);
		main.setLayout(layout);

		return main;
	}

	@Override
	public void manageError(String message) {
		logger.error("Errore: " + message);
		JOptionPane.showMessageDialog(this, "Impossibile salvare il file: " + message, "Attenzione", JOptionPane.ERROR_MESSAGE);
		// stop();
		// destroy();
		System.exit(-1);
	}

	@Override
	public void manageExcepion(Exception pException) {
		logger.error("Errore: " + pException.getMessage(), pException);
		JOptionPane.showMessageDialog(this, "Impossibile salvare il file " + pException.getMessage(), "Attenzione", JOptionPane.ERROR_MESSAGE);
		// stop();
		// destroy();
		System.exit(-1);
	}

	@Override
	public void justClose() {
		try {
			String callbackCancel = WordOpenerConfigManager.getCallBackCancel();
			if (callbackCancel != null && !callbackCancel.equals("")) {
				logger.info(WordOpenerConfigManager.CALLBACK_CANCEL + " : " + props.get(WordOpenerConfigManager.CALLBACK_CANCEL));
				if (props.get(WordOpenerConfigManager.CALLBACK_CANCEL) != null) {
					JSONObject jResult = new JSONObject();
					module.setResult(props.get(WordOpenerConfigManager.CALLBACK_CANCEL));

					this.dispose();
				}
			}
		} catch (Exception pException) {
			manageExcepion(pException);
		}
	}
}
