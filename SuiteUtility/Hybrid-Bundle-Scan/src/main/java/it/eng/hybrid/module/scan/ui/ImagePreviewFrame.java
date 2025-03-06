package it.eng.hybrid.module.scan.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import org.apache.log4j.Logger;

import it.eng.hybrid.module.scan.util.ImageImplement;
import it.eng.hybrid.module.scan.util.ImageResizer;

public class ImagePreviewFrame extends JFrame implements ActionListener {

	public final static Logger logger = Logger.getLogger(ImagePreviewFrame.class);

	private ArrayList<String> filename;
	private int width;
	private int height;
	private int numberPage;

	private Image actualImg;
	private ImageImplement panel;

	// Dichiarazione dei pulsanti
	private JButton rightButton;
	private JButton leftButton;

	public ImagePreviewFrame(ArrayList<String> pStrImageToShow, int width, int height, int numberPage) {
		super("Preview");
		filename = pStrImageToShow;
		this.width = width;
		this.height = height;
		this.numberPage = numberPage;
		setSize(new Dimension(this.width + 37, this.height + 40));
		getContentPane().setLayout(new BorderLayout());
	}

	public void showImage() throws IOException {

		JPanel buttonPanel = getButtonPanel();

		// Visualizzazione dell'immagine
		ImageResizer lImageResizer = new ImageResizer(filename.get(numberPage), width, height);
		actualImg = lImageResizer.getImageResized();
		panel = new ImageImplement(actualImg);
		panel.setBackground(Color.WHITE); // Setto lo sfondo

		// Settaggio del titolo del frame
		setTitle("Pagina " + (numberPage + 1) + " di " + filename.size());

		// Creo un oggetto per eseguire lo scrolling dell'immagine
		JScrollPane scrPane = new JScrollPane(panel, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

		/*
		 * Successivamente si è intercettato lo scorrimento della scrollbar in modo tale da eseguire il repaint. Se questo non venisse eseguito allora
		 * ridimensionando la finestra ed eseguendo lo scrolling comparirebbero delle righe non desiderate.
		 */
		scrPane.getViewport().addChangeListener(new ChangeListener() {

			@Override
			public void stateChanged(ChangeEvent e) {
				repaint();

			}
		});

		// Rimuovo i componenti presenti all'interno del pane per aggiornare l'immagine
		getContentPane().removeAll();
		getContentPane().add(buttonPanel, BorderLayout.NORTH);
		getContentPane().add(scrPane, BorderLayout.CENTER);

		setVisible(true);
		setResizable(true);

		scrPane.setBackground(Color.WHITE);
	}

	/*
	 * Metodo che crea e restituisce la griglia dei pulsanti per procedere alla successiva immagine o meno
	 */
	private JPanel getButtonPanel() {
		JPanel buttonPanel = new JPanel();
		// Visualizzazione della griglia dei pulsanti

		// Freccia sinistra
		URL imageLeftTempURL = getClass().getResource("arrow_left.png");
		ImageIcon imageLeftTemp = new ImageIcon(imageLeftTempURL);
		Image imageLeft = imageLeftTemp.getImage(); // transform it
		Image newimgLeft = imageLeft.getScaledInstance(20, 20, java.awt.Image.SCALE_SMOOTH); // scale it the smooth way
		imageLeftTemp = new ImageIcon(newimgLeft);
		leftButton = new JButton(imageLeftTemp);
		leftButton.addActionListener(this);
		leftButton.setActionCommand("LEFT");
		if (numberPage == 0) {
			// Sono alla prima immagine quindi non posso avere una precedente
			leftButton.setEnabled(false);
		} else {
			// Altrimenti rimane abilitato
			leftButton.setEnabled(true);
		}
		leftButton.setToolTipText("Immagine precedente");
		buttonPanel.add(leftButton);

		// Freccia destra
		URL imageRightTempURL = getClass().getResource("arrow_right.png");
		ImageIcon imageRightTemp = new ImageIcon(imageRightTempURL);
		Image imageRight = imageRightTemp.getImage(); // transform it
		Image newimgRight = imageRight.getScaledInstance(20, 20, java.awt.Image.SCALE_SMOOTH); // scale it the smooth way
		imageRightTemp = new ImageIcon(newimgRight);
		rightButton = new JButton(imageRightTemp);
		rightButton.addActionListener(this);
		rightButton.setActionCommand("RIGHT");
		if (numberPage >= filename.size() - 1) {
			// Sono arrivato all'ultima immagine e quindi disabilito il pulsante
			rightButton.setEnabled(false);
		} else {
			// Altrimenti rimane abilitato
			rightButton.setEnabled(true);
		}
		rightButton.setToolTipText("Immagine successiva");
		buttonPanel.add(rightButton);

		return buttonPanel;

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub

		if (e.getActionCommand() == "LEFT") {
			logger.debug("Pulsante sinistro premuto");
			numberPage--;
			try {
				showImage();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

		} else if (e.getActionCommand() == "RIGHT") {
			logger.debug("Pulsante destro premuto");

			numberPage++;
			try {
				showImage();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

		}

	}

	public void forcedClose() {
		this.dispose();
	}
}