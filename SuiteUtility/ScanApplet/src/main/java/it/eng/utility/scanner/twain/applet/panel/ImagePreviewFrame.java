package it.eng.utility.scanner.twain.applet.panel;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import it.eng.utility.scanner.twain.applet.utility.ImageResizer;

public class ImagePreviewFrame extends JFrame {

	private ArrayList<String> filename;
	private int width;
	private int height;

	public ImagePreviewFrame(ArrayList<String> pStrImageToShow, int width, int height) {
		super("Preview");
		filename = pStrImageToShow;
		this.width = width;
		this.height = height;
	}

	public void showImage() throws IOException {
		ImageResizer lImageResizer = new ImageResizer(filename, width, height);
		ImageImplement panel = new ImageImplement(lImageResizer.getImageResized());
		panel.setBackground(Color.WHITE);
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
		getContentPane().add(scrPane, BorderLayout.CENTER);
		setVisible(true);
		setResizable(true);

		scrPane.setBackground(Color.WHITE);
		setSize(new Dimension(width + 37, height + 40));
	}
}